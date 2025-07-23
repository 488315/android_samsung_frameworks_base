package com.samsung.android.sesl.transparentvideo.renderer.gl;

import android.opengl.GLES30;
import java.nio.IntBuffer;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        IntBuffer allocate = IntBuffer.allocate(1);
        GLES30.glGenVertexArrays(1, allocate);
        int i = 0;
        int i2 = allocate.get(0);
        this.vaoId = i2;
        GLES30.glBindVertexArray(i2);
        ArrayList arrayList = this.attributes;
        int size = arrayList.size();
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Attribute) obj).create();
        }
    }

    @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject
    public final void dispose() {
        ArrayList arrayList = this.attributes;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Attribute) obj).dispose();
        }
        IntBuffer allocate = IntBuffer.allocate(1);
        allocate.put(this.vaoId);
        allocate.rewind();
        GLES30.glDeleteVertexArrays(1, allocate);
    }
}
