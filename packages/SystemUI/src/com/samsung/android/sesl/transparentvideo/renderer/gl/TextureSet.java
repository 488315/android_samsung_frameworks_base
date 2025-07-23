package com.samsung.android.sesl.transparentvideo.renderer.gl;

import android.opengl.GLES30;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        ArrayList arrayList = this.textures;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Texture) obj).create();
        }
    }

    @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject
    public final void dispose() {
        ArrayList arrayList = this.textures;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ((Texture) obj).dispose();
        }
    }
}
