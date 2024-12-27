package com.android.systemui.screenshot;

import android.media.MediaPlayer;
import android.util.Log;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DeferredCoroutine;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.TimeoutCancellationException;
import kotlinx.coroutines.TimeoutKt;

final class ScreenshotSoundControllerImpl$releaseScreenshotSound$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ ScreenshotSoundControllerImpl this$0;

    /* renamed from: com.android.systemui.screenshot.ScreenshotSoundControllerImpl$releaseScreenshotSound$2$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;
        final /* synthetic */ ScreenshotSoundControllerImpl this$0;

        public AnonymousClass1(ScreenshotSoundControllerImpl screenshotSoundControllerImpl, Continuation continuation) {
            super(2, continuation);
            this.this$0 = screenshotSoundControllerImpl;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DeferredCoroutine deferredCoroutine = this.this$0.player;
                this.label = 1;
                obj = deferredCoroutine.awaitInternal(this);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            MediaPlayer mediaPlayer = (MediaPlayer) obj;
            if (mediaPlayer == null) {
                return null;
            }
            mediaPlayer.release();
            return Unit.INSTANCE;
        }
    }

    public ScreenshotSoundControllerImpl$releaseScreenshotSound$2(ScreenshotSoundControllerImpl screenshotSoundControllerImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = screenshotSoundControllerImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ScreenshotSoundControllerImpl$releaseScreenshotSound$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScreenshotSoundControllerImpl$releaseScreenshotSound$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        try {
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Duration.Companion companion = Duration.Companion;
                long duration = DurationKt.toDuration(1, DurationUnit.SECONDS);
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, null);
                this.label = 1;
                obj = TimeoutKt.withTimeout(DelayKt.m2547toDelayMillisLRDsOJo(duration), anonymousClass1, this);
                if (obj == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return obj;
        } catch (TimeoutCancellationException e) {
            this.this$0.player.cancel(null);
            return new Integer(Log.w("ScreenshotSoundControllerImpl", "Error releasing shutter sound", e));
        }
    }
}
