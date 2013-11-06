/*
Copyright (c) 2013 Cloudbase.io Limited

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except 
in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License 
is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express 
or implied. See the License for the specific language governing permissions and limitations under 
the License.
*/
package com.cloudbase;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.entity.AbstractHttpEntity;

public class CBMultipartEntity extends AbstractHttpEntity implements Cloneable {
	/* package */ static final String CRLF = "\r\n";    //$NON-NLS-1$
    
    private List<CBPart> parts = new ArrayList<CBPart>();
    
    private CBBoundary boundary;
    
    public CBMultipartEntity(String boundaryStr) {
        super();
        boundary = new CBBoundary(boundaryStr);
        setContentType("multipart/form-data; boundary=\"" + boundary.getBoundary() + '"');  //$NON-NLS-1$
    }
    
    public CBMultipartEntity() {
        this(null);
    }
    
    public void addPart(CBPart part) {
        parts.add(part);
    }
    
    public boolean isRepeatable() {
        return true;
    }

    public long getContentLength() {
        long result = 0;
        for (CBPart part : parts) {
            result += part.getContentLength(boundary);
        }
        result += boundary.getClosingBoundary().length;
        return result;
    }
    
    /**
     * Returns <code>null</code> since it's not designed to be used for server responses.
     */
    public InputStream getContent() throws IOException {
        return null;
    }
    
    public void writeTo(final OutputStream out) throws IOException {
        if (out == null) {
            throw new IllegalArgumentException("Output stream may not be null");    //$NON-NLS-1$
        }
        for (CBPart part : parts) {
            part.writeTo(out, boundary);
        }
        out.write(boundary.getClosingBoundary());
        out.flush();
    }

    /**
     * Tells that this entity is not streaming.
     *
     * @return <code>false</code>
     */
    public boolean isStreaming() {
        return false;
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("MultipartEntity does not support cloning"); //$NON-NLS-1$ // TODO
    }
}