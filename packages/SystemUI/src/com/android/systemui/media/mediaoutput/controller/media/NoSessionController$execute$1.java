package com.android.systemui.media.mediaoutput.controller.media;

import android.media.AudioManager;
import android.view.KeyEvent;
import com.android.systemui.media.mediaoutput.entity.MediaAction;
import com.android.systemui.media.mediaoutput.entity.NoMediaSessionInfo;
import java.util.Collections;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

final class NoSessionController$execute$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ long $action;
    int label;
    final /* synthetic */ NoSessionController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NoSessionController$execute$1(long j, NoSessionController noSessionController, Continuation continuation) {
        super(2, continuation);
        this.$action = j;
        this.this$0 = noSessionController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new NoSessionController$execute$1(this.$action, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NoSessionController$execute$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            long j = this.$action;
            if (j != 4) {
                if (j == -4) {
                    NoSessionController noSessionController = this.this$0;
                    NoMediaSessionInfo noMediaSessionInfo = noSessionController.mediaInfoCache;
                    MediaAction.Companion.getClass();
                    noSessionController.update(NoMediaSessionInfo.copy$default(noMediaSessionInfo, null, null, Collections.singletonList(MediaAction.play), null, null, 119));
                }
                return Unit.INSTANCE;
            }
            NoSessionController noSessionController2 = this.this$0;
            NoMediaSessionInfo noMediaSessionInfo2 = noSessionController2.mediaInfoCache;
            MediaAction.Companion.getClass();
            noSessionController2.update(NoMediaSessionInfo.copy$default(noMediaSessionInfo2, null, null, Collections.singletonList(MediaAction.buffering), null, null, 119));
            AudioManager audioManager = this.this$0.audioManager;
            audioManager.dispatchMediaKeyEvent(new KeyEvent(0, 126));
            audioManager.dispatchMediaKeyEvent(new KeyEvent(1, 126));
            this.label = 1;
            if (DelayKt.delay(5000L, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        NoSessionController noSessionController3 = this.this$0;
        NoMediaSessionInfo noMediaSessionInfo3 = noSessionController3.mediaInfoCache;
        MediaAction.Companion.getClass();
        noSessionController3.update(NoMediaSessionInfo.copy$default(noMediaSessionInfo3, null, null, Collections.singletonList(MediaAction.copy$default(MediaAction.play, -4L, false, 14)), null, null, 119));
        return Unit.INSTANCE;
    }
}
