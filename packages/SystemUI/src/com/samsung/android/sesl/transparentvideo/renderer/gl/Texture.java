package com.samsung.android.sesl.transparentvideo.renderer.gl;

import android.graphics.SurfaceTexture;
import android.opengl.GLES30;
import android.util.Log;
import java.nio.Buffer;
import java.nio.IntBuffer;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class Texture implements IGLObject {
    public static final Companion Companion = new Companion(null);
    public Buffer buffer;
    public Integer height;
    public int id;
    public boolean isUpdated;
    public int location;
    public String name;
    public Program program;
    public SurfaceTexture surfaceTexture;
    public final Map texParams;
    public final int texType;
    public boolean useMipmap;
    public Integer width;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public Texture(Program program, String str, Buffer buffer, Integer num, Integer num2, boolean z, boolean z2) {
        this.program = program;
        this.name = str;
        this.buffer = buffer;
        this.width = num;
        this.height = num2;
        this.useMipmap = z2;
        this.id = -1;
        this.location = -1;
        this.isUpdated = true;
        this.texType = z ? 36197 : 3553;
        this.texParams = MapsKt__MapsKt.mutableMapOf(new Pair(10242, 10497), new Pair(10243, 10497), new Pair(10241, 9729), new Pair(10240, 9729));
    }

    @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject
    public final void bind() {
        int i = this.id;
        int i2 = this.texType;
        GLES30.glBindTexture(i2, i);
        if (i2 != 36197) {
            if (this.isUpdated) {
                for (Map.Entry entry : ((LinkedHashMap) this.texParams).entrySet()) {
                    GLES30.glTexParameteri(i2, ((Number) entry.getKey()).intValue(), ((Number) entry.getValue()).intValue());
                }
                Integer num = this.width;
                int iIntValue = num != null ? num.intValue() : 0;
                Integer num2 = this.height;
                GLES30.glTexImage2D(this.texType, 0, 6408, iIntValue, num2 != null ? num2.intValue() : 0, 0, 6408, 5121, this.buffer);
                if (this.useMipmap) {
                    GLES30.glBindTexture(i2, this.id);
                    GLES30.glTexParameteri(i2, 10241, 9987);
                    GLES30.glTexParameteri(i2, 10240, 9729);
                    GLES30.glGenerateMipmap(i2);
                }
                this.isUpdated = false;
                return;
            }
            return;
        }
        SurfaceTexture surfaceTexture = this.surfaceTexture;
        if (surfaceTexture != null) {
            if (surfaceTexture.isReleased()) {
                Log.e("Texture", "surfaceTexture(" + this.surfaceTexture + ") has been released!");
            } else {
                surfaceTexture.updateTexImage();
            }
        }
        for (Map.Entry entry2 : ((LinkedHashMap) this.texParams).entrySet()) {
            GLES30.glTexParameteri(i2, ((Number) entry2.getKey()).intValue(), ((Number) entry2.getValue()).intValue());
        }
        if (this.useMipmap) {
            GLES30.glBindTexture(i2, this.id);
            GLES30.glTexParameteri(i2, 10241, 9987);
            GLES30.glTexParameteri(i2, 10240, 9729);
            GLES30.glGenerateMipmap(i2);
        }
    }

    @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject
    public final void create() {
        Program program = this.program;
        if (program != null) {
            this.location = GLES30.glGetUniformLocation(program.id, this.name);
        }
        IntBuffer intBufferAllocate = IntBuffer.allocate(1);
        GLES30.glGenTextures(1, intBufferAllocate);
        this.id = intBufferAllocate.get(0);
        if (this.texType == 36197) {
            this.surfaceTexture = new SurfaceTexture(this.id);
        }
        bind();
    }

    @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject
    public final void dispose() {
        this.buffer = null;
        this.width = 0;
        this.height = 0;
        this.isUpdated = true;
        SurfaceTexture surfaceTexture = this.surfaceTexture;
        if (surfaceTexture != null && !surfaceTexture.isReleased()) {
            surfaceTexture.release();
        }
        this.surfaceTexture = null;
        bind();
        IntBuffer intBufferAllocate = IntBuffer.allocate(1);
        intBufferAllocate.put(this.id);
        intBufferAllocate.rewind();
        GLES30.glDeleteTextures(1, intBufferAllocate);
    }

    public /* synthetic */ Texture(Program program, String str, Buffer buffer, Integer num, Integer num2, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : program, (i & 2) != 0 ? null : str, buffer, num, num2, (i & 32) != 0 ? true : z, (i & 64) != 0 ? false : z2);
    }
}
