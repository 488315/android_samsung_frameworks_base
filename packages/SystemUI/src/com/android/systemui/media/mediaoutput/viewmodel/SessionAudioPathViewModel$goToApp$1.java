package com.android.systemui.media.mediaoutput.viewmodel;

import android.app.PendingIntent;
import android.content.Intent;
import androidx.lifecycle.SavedStateHandle;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.plugins.ActivityStarter;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class SessionAudioPathViewModel$goToApp$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Intent $intent;
    int label;
    final /* synthetic */ SessionAudioPathViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SessionAudioPathViewModel$goToApp$1(SessionAudioPathViewModel sessionAudioPathViewModel, Intent intent, Continuation continuation) {
        super(2, continuation);
        this.this$0 = sessionAudioPathViewModel;
        this.$intent = intent;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SessionAudioPathViewModel$goToApp$1(this.this$0, this.$intent, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SessionAudioPathViewModel$goToApp$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        PendingIntent activity = PendingIntent.getActivity(this.this$0.context, 0, this.$intent, 67108864);
        SavedStateHandle savedStateHandle = this.this$0.savedStateHandle;
        if (savedStateHandle != null ? Intrinsics.areEqual(savedStateHandle.get("isCover"), Boolean.TRUE) : false) {
            ((PluginAODManager) this.this$0.pluginAODManagerProvider.get()).showCoverToast(activity, this.$intent);
        } else {
            ((ActivityStarter) this.this$0.activityStarterProvider.get()).postStartActivityDismissingKeyguard(activity, true);
        }
        return Unit.INSTANCE;
    }
}
