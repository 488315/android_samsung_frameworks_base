package com.samsung.android.sesl.transparentvideo.renderer;

import com.samsung.android.sesl.transparentvideo.renderer.egl.EGLHandler;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
final class VideoTextureListener$onSurfaceTextureSizeChanged$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $height;
    final /* synthetic */ int $width;
    int label;
    final /* synthetic */ VideoTextureListener this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VideoTextureListener$onSurfaceTextureSizeChanged$1(VideoTextureListener videoTextureListener, int i, int i2, Continuation continuation) {
        super(2, continuation);
        this.this$0 = videoTextureListener;
        this.$width = i;
        this.$height = i2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new VideoTextureListener$onSurfaceTextureSizeChanged$1(this.this$0, this.$width, this.$height, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((VideoTextureListener$onSurfaceTextureSizeChanged$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        VideoTextureListener videoTextureListener = this.this$0;
        EGLHandler eGLHandler = videoTextureListener.eglHandler;
        if (eGLHandler != null) {
            int i = this.$width;
            int i2 = this.$height;
            eGLHandler.makeCurrent();
            TransparentVideoRenderer transparentVideoRenderer = videoTextureListener.renderer;
            transparentVideoRenderer.onSurfaceChanged(i, i2);
            if (transparentVideoRenderer.onDraw()) {
                eGLHandler.egl.eglSwapBuffers(eGLHandler.eglDisplay, eGLHandler.eglSurface);
            }
        }
        return Unit.INSTANCE;
    }
}
