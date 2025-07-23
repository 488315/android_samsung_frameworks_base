package com.samsung.android.sesl.transparentvideo.renderer.gl;

import android.opengl.GLES30;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.samsung.android.sesl.transparentvideo.renderer.gl.Texture;
import java.nio.IntBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class FrameBuffer implements IGLObject {
    public Texture colorAttachment;
    public int frameBufferId;
    public int height;
    public boolean sizeUpdated;
    public int width;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject
    public final void bind() {
        updateColorAttachmentIfNeeded();
        GLES30.glBindFramebuffer(36160, this.frameBufferId);
        GLES30.glViewport(0, 0, this.width, this.height);
    }

    @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject
    public final void create() {
        IntBuffer allocate = IntBuffer.allocate(1);
        GLES30.glGenFramebuffers(1, allocate);
        this.frameBufferId = allocate.get(0);
        updateColorAttachmentIfNeeded();
    }

    @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject
    public final void dispose() {
        Texture texture = this.colorAttachment;
        if (texture != null) {
            texture.dispose();
        }
        this.colorAttachment = null;
        IntBuffer allocate = IntBuffer.allocate(1);
        allocate.put(this.frameBufferId);
        allocate.rewind();
        GLES30.glDeleteFramebuffers(1, allocate);
    }

    public final void updateColorAttachmentIfNeeded() {
        if (this.sizeUpdated) {
            Texture texture = this.colorAttachment;
            if (texture != null) {
                texture.dispose();
            }
            GLES30.glBindFramebuffer(36160, this.frameBufferId);
            Texture.Companion companion = Texture.Companion;
            int i = this.width;
            int i2 = this.height;
            companion.getClass();
            Texture texture2 = new Texture(null, null, null, Integer.valueOf(i), Integer.valueOf(i2), false, false, 64, null);
            texture2.create();
            texture2.bind();
            GLES30.glFramebufferTexture2D(36160, 36064, 3553, texture2.id, 0);
            this.colorAttachment = texture2;
            int glCheckFramebufferStatus = GLES30.glCheckFramebufferStatus(36160);
            if (glCheckFramebufferStatus != 36053) {
                ClockEventController$$ExternalSyntheticOutline0.m(glCheckFramebufferStatus, "Framebuffer is not complete error: ", "FrameBuffer");
            } else {
                this.sizeUpdated = false;
            }
        }
    }
}
