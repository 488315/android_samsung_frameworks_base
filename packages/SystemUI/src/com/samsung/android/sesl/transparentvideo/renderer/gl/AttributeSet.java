package com.samsung.android.sesl.transparentvideo.renderer.gl;

import android.opengl.GLES30;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes4.dex */
public final class AttributeSet implements IGLObject {
    public int vaoId = -1;
    public final ArrayList attributes = new ArrayList();

    @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject
    public final void bind() {
        GLES30.glBindVertexArray(this.vaoId);
    }

    @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject
    public final void create() {
        IntBuffer intBufferAllocate = IntBuffer.allocate(1);
        GLES30.glGenVertexArrays(1, intBufferAllocate);
        int i = intBufferAllocate.get(0);
        this.vaoId = i;
        GLES30.glBindVertexArray(i);
        Iterator it = this.attributes.iterator();
        while (it.hasNext()) {
            ((Attribute) it.next()).create();
        }
    }

    @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject
    public final void dispose() {
        Iterator it = this.attributes.iterator();
        while (it.hasNext()) {
            ((Attribute) it.next()).dispose();
        }
        IntBuffer intBufferAllocate = IntBuffer.allocate(1);
        intBufferAllocate.put(this.vaoId);
        intBufferAllocate.rewind();
        GLES30.glDeleteVertexArrays(1, intBufferAllocate);
    }
}
