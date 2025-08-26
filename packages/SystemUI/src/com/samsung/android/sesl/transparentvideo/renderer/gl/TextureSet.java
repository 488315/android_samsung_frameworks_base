package com.samsung.android.sesl.transparentvideo.renderer.gl;

import android.opengl.GLES30;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes4.dex */
public final class TextureSet implements IGLObject {
    public final ArrayList textures = new ArrayList();

    @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject
    public final void bind() {
        int size = this.textures.size();
        for (int i = 0; i < size; i++) {
            GLES30.glUniform1i(((Texture) this.textures.get(i)).location, i);
            GLES30.glActiveTexture(33984 + i);
            ((Texture) this.textures.get(i)).bind();
        }
    }

    @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject
    public final void create() {
        Iterator it = this.textures.iterator();
        while (it.hasNext()) {
            ((Texture) it.next()).create();
        }
    }

    @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject
    public final void dispose() {
        Iterator it = this.textures.iterator();
        while (it.hasNext()) {
            ((Texture) it.next()).dispose();
        }
    }
}
