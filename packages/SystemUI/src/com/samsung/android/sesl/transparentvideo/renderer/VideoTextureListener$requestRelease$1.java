package com.samsung.android.sesl.transparentvideo.renderer;

import android.graphics.SurfaceTexture;
import android.util.Log;
import com.samsung.android.sesl.transparentvideo.renderer.egl.EGLHandler;
import com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject;
import java.util.Iterator;
import java.util.List;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLSurface;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
final class VideoTextureListener$requestRelease$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $forceReleaseSurfaceTexture;
    int label;
    final /* synthetic */ VideoTextureListener this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VideoTextureListener$requestRelease$1(VideoTextureListener videoTextureListener, boolean z, Continuation continuation) {
        super(2, continuation);
        this.this$0 = videoTextureListener;
        this.$forceReleaseSurfaceTexture = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new VideoTextureListener$requestRelease$1(this.this$0, this.$forceReleaseSurfaceTexture, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VideoTextureListener$requestRelease$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        SurfaceTexture surfaceTexture;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        VideoTextureListener videoTextureListener = this.this$0;
        EGLHandler eGLHandler = videoTextureListener.eglHandler;
        if (eGLHandler != null) {
            eGLHandler.makeCurrent();
            TransparentVideoRenderer transparentVideoRenderer = videoTextureListener.renderer;
            transparentVideoRenderer.getClass();
            Log.d("TransparentVideoRenderer", "onSurfaceDestroyed()");
            List list = transparentVideoRenderer.glObj;
            if (list == null) {
                list = null;
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ((IGLObject) it.next()).dispose();
            }
            List list2 = transparentVideoRenderer.texturingRenderpass.glObj;
            if (list2 == null) {
                list2 = null;
            }
            Iterator it2 = list2.iterator();
            while (it2.hasNext()) {
                ((IGLObject) it2.next()).dispose();
            }
            Log.i("EGLHandler", "Destroy egl context");
            EGLSurface eGLSurface = eGLHandler.eglSurface;
            EGLSurface eGLSurface2 = EGL10.EGL_NO_SURFACE;
            if (eGLSurface != eGLSurface2) {
                eGLHandler.egl.eglMakeCurrent(eGLHandler.eglDisplay, eGLSurface2, eGLSurface2, EGL10.EGL_NO_CONTEXT);
                eGLHandler.egl.eglDestroySurface(eGLHandler.eglDisplay, eGLHandler.eglSurface);
            }
            eGLHandler.egl.eglDestroyContext(eGLHandler.eglDisplay, eGLHandler.eglContext);
            eGLHandler.egl.eglTerminate(eGLHandler.eglDisplay);
        }
        VideoTextureListener videoTextureListener2 = this.this$0;
        videoTextureListener2.eglHandler = null;
        if (this.$forceReleaseSurfaceTexture && ((surfaceTexture = videoTextureListener2.surfaceTexture) == null || !surfaceTexture.isReleased())) {
            SurfaceTexture surfaceTexture2 = this.this$0.surfaceTexture;
            if (surfaceTexture2 != null) {
                surfaceTexture2.release();
            }
            this.this$0.surfaceTexture = null;
        }
        return Unit.INSTANCE;
    }
}
