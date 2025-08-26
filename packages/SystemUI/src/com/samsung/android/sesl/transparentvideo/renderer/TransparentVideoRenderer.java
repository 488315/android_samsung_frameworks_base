package com.samsung.android.sesl.transparentvideo.renderer;

import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.util.Log;
import android.util.Size;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.sesl.transparentvideo.renderer.gl.AttributeSet;
import com.samsung.android.sesl.transparentvideo.renderer.gl.FrameBuffer;
import com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject;
import com.samsung.android.sesl.transparentvideo.renderer.gl.Program;
import com.samsung.android.sesl.transparentvideo.renderer.gl.Texture;
import com.samsung.android.sesl.transparentvideo.renderer.gl.TextureSet;
import com.samsung.android.sesl.transparentvideo.renderer.gl.Uniform;
import com.samsung.android.sesl.transparentvideo.renderer.gl.utils.GLBuffer;
import com.samsung.android.sesl.transparentvideo.renderer.gl.utils.Mesh;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class TransparentVideoRenderer {
    public final AttributeSet attribSet;
    public final AtomicBoolean clearRequested;
    public final FrameBuffer fbo;
    public List glObj;
    public boolean isVideoFrameAvailable;
    public final Consumer onConnectTextureExternalOES;
    public Program program;
    public final float reinforcedEdgeAmount;
    public Size surfaceSize;
    public final TextureSet textureSet;
    public final TexturingRenderpass texturingRenderpass;
    public Uniform uReinforcedEdgeAmount;
    public Uniform uTexelSize;
    public Texture videoTexture;
    public Size videoTextureResolution;

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

    public TransparentVideoRenderer(float f, Consumer<SurfaceTexture> consumer) {
        this.reinforcedEdgeAmount = f;
        this.onConnectTextureExternalOES = consumer;
        this.fbo = new FrameBuffer();
        this.attribSet = new AttributeSet();
        this.textureSet = new TextureSet();
        this.texturingRenderpass = new TexturingRenderpass(false, 1, null);
        this.clearRequested = new AtomicBoolean(false);
    }

    public final void connectVideoSurfaceTexture() {
        Texture texture = this.videoTexture;
        if (texture != null) {
            if (texture == null) {
                texture = null;
            }
            if (texture.surfaceTexture != null) {
                Log.d("TransparentVideoRenderer", "connectVideoSurfaceTexture with current surface texture");
                Texture texture2 = this.videoTexture;
                SurfaceTexture surfaceTexture = (texture2 != null ? texture2 : null).surfaceTexture;
                if (surfaceTexture != null) {
                    this.onConnectTextureExternalOES.accept(surfaceTexture);
                    return;
                }
                return;
            }
        }
        Log.d("TransparentVideoRenderer", "video texture is not created yet for connection");
    }

    public final boolean onDraw() {
        if (this.clearRequested.get()) {
            Log.d("TransparentVideoRenderer", "Clearing surface in onDraw() because clearRequested is true");
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GLES20.glClear(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT);
            return true;
        }
        if (!this.isVideoFrameAvailable) {
            return false;
        }
        Size size = this.videoTextureResolution;
        if (size == null) {
            Log.d("TransparentVideoRenderer", "video resolution is empty.");
            return false;
        }
        if (this.surfaceSize == null) {
            Log.d("TransparentVideoRenderer", "no surface size.");
            return false;
        }
        Size size2 = new Size(size.getWidth(), (int) (size.getHeight() * 0.8f));
        int width = size2.getWidth();
        int height = size2.getHeight();
        FrameBuffer frameBuffer = this.fbo;
        int i = frameBuffer.width;
        int i2 = frameBuffer.height;
        frameBuffer.width = width;
        frameBuffer.height = height;
        if (i != width || i2 != height) {
            frameBuffer.sizeUpdated = true;
        }
        Uniform uniform = this.uTexelSize;
        if (uniform == null) {
            uniform = null;
        }
        float[] fArr = {1.0f / size.getWidth(), 1.0f / size.getHeight()};
        GLBuffer gLBuffer = uniform.buffer;
        gLBuffer.dataUpdater.mo781invoke(fArr);
        gLBuffer.dataBuffer.rewind();
        Uniform uniform2 = this.uReinforcedEdgeAmount;
        if (uniform2 == null) {
            uniform2 = null;
        }
        Float fValueOf = Float.valueOf(this.reinforcedEdgeAmount);
        GLBuffer gLBuffer2 = uniform2.buffer;
        gLBuffer2.dataUpdater.mo781invoke(fValueOf);
        gLBuffer2.dataBuffer.rewind();
        GLES30.glDisable(3042);
        List list = this.glObj;
        if (list == null) {
            list = null;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            ((IGLObject) it.next()).bind();
        }
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES30.glClear(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT);
        Mesh.Companion.getClass();
        Mesh mesh = Mesh.QUAD_2D;
        GLES30.glDrawArrays(mesh.order, 0, mesh.data.length / mesh.dimension);
        GLES30.glBindFramebuffer(36160, 0);
        Size size3 = this.surfaceSize;
        if (size3 != null) {
            GLES20.glViewport(0, 0, size3.getWidth(), size3.getHeight());
        }
        Texture texture = frameBuffer.colorAttachment;
        TexturingRenderpass texturingRenderpass = this.texturingRenderpass;
        texturingRenderpass.texture = texture;
        texturingRenderpass.useMipmapping = !Intrinsics.areEqual(this.surfaceSize, size2);
        Texture texture2 = texturingRenderpass.texture;
        if (texture2 != null) {
            Program program = texturingRenderpass.program;
            if (program == null) {
                program = null;
            }
            Texture.Companion companion = Texture.Companion;
            texture2.program = program;
            texture2.name = "tex";
            texture2.useMipmap = false;
            texture2.location = GLES30.glGetUniformLocation(program.id, "tex");
            texture2.isUpdated = true;
            texture2.useMipmap = texturingRenderpass.useMipmapping;
            List list2 = texturingRenderpass.glObj;
            Iterator it2 = (list2 != null ? list2 : null).iterator();
            while (it2.hasNext()) {
                ((IGLObject) it2.next()).bind();
            }
            TextureSet textureSet = new TextureSet();
            textureSet.textures.add(texture2);
            textureSet.bind();
        }
        GLES30.glEnable(3042);
        GLES30.glBlendFunc(770, 771);
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        GLES30.glClear(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT);
        Mesh.Companion.getClass();
        Mesh mesh2 = Mesh.QUAD_2D;
        GLES30.glDrawArrays(mesh2.order, 0, mesh2.data.length / mesh2.dimension);
        return true;
    }

    public final void onSurfaceChanged(int i, int i2) {
        Log.d("TransparentVideoRenderer", MutableVectorKt$$ExternalSyntheticOutline0.m(i, i2, "onSurfaceChanged(", ", ", ")"));
        this.surfaceSize = new Size(i, i2);
        GLES20.glViewport(0, 0, i, i2);
    }

    public /* synthetic */ TransparentVideoRenderer(float f, Consumer consumer, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 1.0f : f, consumer);
    }
}
