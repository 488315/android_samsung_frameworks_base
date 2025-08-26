package com.samsung.android.sesl.transparentvideo.renderer.gl;

import android.opengl.GLES30;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.samsung.android.sesl.transparentvideo.renderer.gl.Texture;
import java.nio.IntBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class FrameBuffer implements IGLObject {
    public Texture colorAttachment;
    public int frameBufferId;
    public int height;
    public boolean sizeUpdated;
    public int width;

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
        IntBuffer intBufferAllocate = IntBuffer.allocate(1);
        GLES30.glGenFramebuffers(1, intBufferAllocate);
        this.frameBufferId = intBufferAllocate.get(0);
        updateColorAttachmentIfNeeded();
    }

    @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject
    public final void dispose() {
        Texture texture = this.colorAttachment;
        if (texture != null) {
            texture.dispose();
        }
        this.colorAttachment = null;
        IntBuffer intBufferAllocate = IntBuffer.allocate(1);
        intBufferAllocate.put(this.frameBufferId);
        intBufferAllocate.rewind();
        GLES30.glDeleteFramebuffers(1, intBufferAllocate);
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
            int iGlCheckFramebufferStatus = GLES30.glCheckFramebufferStatus(36160);
            if (iGlCheckFramebufferStatus != 36053) {
                ClockEventController$$ExternalSyntheticOutline0.m(iGlCheckFramebufferStatus, "Framebuffer is not complete error: ", "FrameBuffer");
            } else {
                this.sizeUpdated = false;
            }
        }
    }
}
