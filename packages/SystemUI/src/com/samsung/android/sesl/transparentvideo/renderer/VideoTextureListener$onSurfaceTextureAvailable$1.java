package com.samsung.android.sesl.transparentvideo.renderer;

import android.graphics.SurfaceTexture;
import android.opengl.GLES30;
import android.util.Log;
import com.samsung.android.sesl.transparentvideo.renderer.egl.EGLHandler;
import com.samsung.android.sesl.transparentvideo.renderer.gl.Attribute;
import com.samsung.android.sesl.transparentvideo.renderer.gl.AttributeSet;
import com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject;
import com.samsung.android.sesl.transparentvideo.renderer.gl.Program;
import com.samsung.android.sesl.transparentvideo.renderer.gl.Shader;
import com.samsung.android.sesl.transparentvideo.renderer.gl.Texture;
import com.samsung.android.sesl.transparentvideo.renderer.gl.TextureSet;
import com.samsung.android.sesl.transparentvideo.renderer.gl.Uniform;
import com.samsung.android.sesl.transparentvideo.renderer.gl.utils.DataType;
import com.samsung.android.sesl.transparentvideo.renderer.gl.utils.Mesh;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
final class VideoTextureListener$onSurfaceTextureAvailable$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $height;
    final /* synthetic */ SurfaceTexture $surfaceTexture;
    final /* synthetic */ int $width;
    int label;
    final /* synthetic */ VideoTextureListener this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VideoTextureListener$onSurfaceTextureAvailable$1(VideoTextureListener videoTextureListener, SurfaceTexture surfaceTexture, int i, int i2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = videoTextureListener;
        this.$surfaceTexture = surfaceTexture;
        this.$width = i;
        this.$height = i2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new VideoTextureListener$onSurfaceTextureAvailable$1(this.this$0, this.$surfaceTexture, this.$width, this.$height, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VideoTextureListener$onSurfaceTextureAvailable$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        VideoTextureListener videoTextureListener = this.this$0;
        if (videoTextureListener.eglHandler == null) {
            EGLHandler eGLHandler = new EGLHandler(this.$surfaceTexture);
            VideoTextureListener videoTextureListener2 = this.this$0;
            int i = this.$width;
            int i2 = this.$height;
            eGLHandler.makeCurrent();
            TransparentVideoRenderer transparentVideoRenderer = videoTextureListener2.renderer;
            transparentVideoRenderer.getClass();
            Log.d("TransparentVideoRenderer", "onSurfaceCreated()");
            transparentVideoRenderer.program = new Program(new Shader(Shader.TYPE.VERTEX, "#version 320 es\n    precision highp float;\n    \n    in vec2 aPos;\n    in vec2 aUv;\n    \n    out vec2 vUv;\n    \n    void main() {\n        gl_Position = vec4(aPos, 0.0, 1.0);\n        vUv = aUv;\n    }\n    "), new Shader(Shader.TYPE.FRAGMENT, "#version 320 es\n    #extension GL_OES_EGL_image_external_essl3 : require\n    precision highp float;\n    \n    uniform samplerExternalOES videoTexture;\n    uniform vec2 uTexelSize;\n    \n    uniform float uReinforcedEdgeAmount; \n    \n    in vec2 vUv;\n    out vec4 FragColor;\n    \n    const int MLT = 1;\n    const int MRT = 2;\n    const int MLB = 3;\n    const int MRB = 4;\n    \n    float getEdgeDelta(vec2 tuv) {\n        /* adjacent texel array for texel[4]\n        036\n        147\n        258\n        */\n        float thickness = 2.0;\n        vec3 c[9];\n        for (int i = 0; i < 3; ++i) for (int j = 0; j < 3; ++j) {\n            vec2 uv = tuv + thickness * vec2(i-1,j-1) * uTexelSize;\n            c[3*i + j] = texture(videoTexture, uv).rgb;\n        }\n    \n        vec3 dx = 2.0 * abs(c[7] - c[1]) + abs(c[2] - c[6]) + abs(c[8] - c[0]);\n        vec3 dy = 2.0 * abs(c[3] - c[5]) + abs(c[6] - c[8]) + abs(c[0] - c[2]);\n        float delta = length(0.25 * (dx + dy) * 0.5);\n        return clamp(delta, 0.0, 1.0);\n    }\n    \n    float getAlphaOf(int cellId, vec2 nuv) {\n        // to protect interpolation on edge texel on alpha area, clamp uv with texel size.\n        // since the nuv is multiplied, use double size of texel as well.\n        // further, resolution of height is stretched to 25%.  \n        vec2 borderSize = vec2(uTexelSize.x * 2.0, uTexelSize.y * 2.0 * 1.25);\n        vec2 tuv = vec2(clamp(nuv, borderSize, 1.0 - borderSize));\n        vec2 alphaUv = vec2(tuv.x / 4.0, 1.0 / 1.25 + tuv.y * 0.25 / 1.25);\n\n        for (int i = MLT; i < cellId; i++) {\n            alphaUv.x += 0.25;\n        }\n\n        return length(texture(videoTexture, alphaUv).rgb) / sqrt(3.0);\n    }\n    \n    float getAlpha(vec2 uv) {\n        if (uv.x < 0.5) {\n            if (uv.y > 0.5) { // RT         \n                vec2 nUv = vec2(uv.x * 2.0, (uv.y - 0.5) * 2.0);\n                return getAlphaOf(MRT, nUv);\n            } else { // LT\n                vec2 nUv = vec2(uv.x * 2.0, uv.y * 2.0);\n                return getAlphaOf(MLT, nUv);\n            }\n        }\n        else {\n            if (uv.y > 0.5) { // RB\n                vec2 nUv = vec2((uv.x - 0.5) * 2.0, (uv.y - 0.5) * 2.0);\n                return getAlphaOf(MRB, nUv);            \n            } else { // LB\n                vec2 nUv = vec2((uv.x - 0.5) * 2.0, uv.y * 2.0);\n                return getAlphaOf(MLB, nUv);  \n            }\n        }\n    }\n    \n    vec4 getArgb() {\n        vec2 rgbUv = vec2(vUv.x, vUv.y * (1.0 / 1.25));\n        float alpha = getAlpha(vUv);\n        if (uReinforcedEdgeAmount > 0.001) {\n            // alpha data have a loss when downscaled-encoding.\n            // to recover this loss, alpha can be adjusted manually.\n            // getting edge from rgb, not alpha. this is hacky, but this can improve the quality.\n            float edgeDelta = getEdgeDelta(rgbUv);\n            alpha *= 1.0 + uReinforcedEdgeAmount * edgeDelta;\n        }\n        return vec4(texture(videoTexture, rgbUv).rgb, alpha);\n    }\n    \n    void main() {\n        FragColor = getArgb();\n    }\n    "));
            Program program = transparentVideoRenderer.program;
            if (program == null) {
                program = null;
            }
            DataType dataType = DataType.VEC2;
            Mesh.Companion.getClass();
            Attribute attribute = new Attribute(program, dataType, "aPos", Mesh.QUAD_2D.data);
            Program program2 = transparentVideoRenderer.program;
            if (program2 == null) {
                program2 = null;
            }
            Attribute[] attributeArr = {attribute, new Attribute(program2, dataType, "aUv", Mesh.QUAD_2D_UV.data)};
            AttributeSet attributeSet = transparentVideoRenderer.attribSet;
            attributeSet.attributes.clear();
            for (int i3 = 0; i3 < 2; i3++) {
                attributeSet.attributes.add(attributeArr[i3]);
            }
            TextureSet textureSet = transparentVideoRenderer.textureSet;
            textureSet.textures.clear();
            Program program3 = transparentVideoRenderer.program;
            Texture texture = new Texture(program3 == null ? null : program3, "videoTexture", null, null, null, true, false);
            texture.texParams.put(10242, 33071);
            texture.texParams.put(10243, 33071);
            texture.texParams.put(10241, 9729);
            texture.texParams.put(10240, 9729);
            textureSet.textures.add(texture);
            transparentVideoRenderer.videoTexture = texture;
            Program program4 = transparentVideoRenderer.program;
            if (program4 == null) {
                program4 = null;
            }
            transparentVideoRenderer.uTexelSize = new Uniform(program4, dataType, "uTexelSize", new float[]{0.0f, 0.0f}, 0, 16, null);
            Program program5 = transparentVideoRenderer.program;
            Uniform uniform = new Uniform(program5 == null ? null : program5, DataType.FLOAT, "uReinforcedEdgeAmount", Float.valueOf(transparentVideoRenderer.reinforcedEdgeAmount), 0, 16, null);
            transparentVideoRenderer.uReinforcedEdgeAmount = uniform;
            Program program6 = transparentVideoRenderer.program;
            Program program7 = program6 == null ? null : program6;
            Uniform uniform2 = transparentVideoRenderer.uTexelSize;
            List asList = Arrays.asList(transparentVideoRenderer.fbo, program7, transparentVideoRenderer.attribSet, transparentVideoRenderer.textureSet, uniform2 == null ? null : uniform2, uniform);
            transparentVideoRenderer.glObj = asList;
            if (asList == null) {
                asList = null;
            }
            Iterator it = asList.iterator();
            while (it.hasNext()) {
                ((IGLObject) it.next()).create();
            }
            List list = transparentVideoRenderer.glObj;
            if (list == null) {
                list = null;
            }
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                ((IGLObject) it2.next()).bind();
            }
            transparentVideoRenderer.connectVideoSurfaceTexture();
            GLES30.glEnable(3042);
            GLES30.glBlendFunc(770, 771);
            TexturingRenderpass texturingRenderpass = transparentVideoRenderer.texturingRenderpass;
            texturingRenderpass.getClass();
            texturingRenderpass.program = new Program(new Shader(Shader.TYPE.VERTEX, "#version 320 es\n    precision highp float;\n    \n    in vec2 aPos;\n    in vec2 aUv;\n    \n    out vec2 vUv;\n    \n    void main() {\n        gl_Position = vec4(aPos, 0.0, 1.0);\n        vUv = aUv;\n    }\n    "), new Shader(Shader.TYPE.FRAGMENT, "#version 320 es\n    precision highp float;\n    \n    uniform sampler2D tex;\n    \n    in vec2 vUv;\n    out vec4 FragColor;\n    \n    void main() {\n        FragColor = texture(tex, vUv);\n    }\n    "));
            AttributeSet attributeSet2 = new AttributeSet();
            Program program8 = texturingRenderpass.program;
            if (program8 == null) {
                program8 = null;
            }
            DataType dataType2 = DataType.VEC2;
            Mesh.Companion.getClass();
            Attribute attribute2 = new Attribute(program8, dataType2, "aPos", Mesh.QUAD_2D.data);
            Program program9 = texturingRenderpass.program;
            if (program9 == null) {
                program9 = null;
            }
            Attribute[] attributeArr2 = {attribute2, new Attribute(program9, dataType2, "aUv", texturingRenderpass.flipY ? Mesh.QUAD_2D_UV_FLIP.data : Mesh.QUAD_2D_UV.data)};
            attributeSet2.attributes.clear();
            for (int i4 = 0; i4 < 2; i4++) {
                attributeSet2.attributes.add(attributeArr2[i4]);
            }
            Program program10 = texturingRenderpass.program;
            if (program10 == null) {
                program10 = null;
            }
            List asList2 = Arrays.asList(program10, attributeSet2);
            texturingRenderpass.glObj = asList2;
            if (asList2 == null) {
                asList2 = null;
            }
            Iterator it3 = asList2.iterator();
            while (it3.hasNext()) {
                ((IGLObject) it3.next()).create();
            }
            List list2 = texturingRenderpass.glObj;
            Iterator it4 = (list2 == null ? null : list2).iterator();
            while (it4.hasNext()) {
                ((IGLObject) it4.next()).bind();
            }
            transparentVideoRenderer.onSurfaceChanged(i, i2);
            videoTextureListener.eglHandler = eGLHandler;
        }
        VideoTextureListener videoTextureListener3 = this.this$0;
        EGLHandler eGLHandler2 = videoTextureListener3.eglHandler;
        if (eGLHandler2 != null) {
            TransparentVideoRenderer transparentVideoRenderer2 = videoTextureListener3.renderer;
            eGLHandler2.makeCurrent();
            if (transparentVideoRenderer2.onDraw()) {
                eGLHandler2.egl.eglSwapBuffers(eGLHandler2.eglDisplay, eGLHandler2.eglSurface);
            }
        }
        return Unit.INSTANCE;
    }
}
