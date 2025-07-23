package com.android.systemui.inputdevice.tutorial.domain.interactor;

import com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class TutorialSchedulerInteractor$updateLaunchInfo$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ TutorialSchedulerInteractor.TutorialType $tutorialType;
    int label;
    final /* synthetic */ TutorialSchedulerInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TutorialSchedulerInteractor$updateLaunchInfo$1(TutorialSchedulerInteractor.TutorialType tutorialType, TutorialSchedulerInteractor tutorialSchedulerInteractor, Continuation continuation) {
        super(2, continuation);
        this.$tutorialType = tutorialType;
        this.this$0 = tutorialSchedulerInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new TutorialSchedulerInteractor$updateLaunchInfo$1(this.$tutorialType, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TutorialSchedulerInteractor$updateLaunchInfo$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0095, code lost:
    
        if (r7 == r0) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0097, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x005a, code lost:
    
        if (r8 == r0) goto L29;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r7.label
            java.lang.String r2 = "_LAUNCHED_TIME"
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L1f
            if (r1 == r4) goto L1b
            if (r1 != r3) goto L13
            kotlin.ResultKt.throwOnFailure(r8)
            goto L98
        L13:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L1b:
            kotlin.ResultKt.throwOnFailure(r8)
            goto L5d
        L1f:
            kotlin.ResultKt.throwOnFailure(r8)
            com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$TutorialType r8 = r7.$tutorialType
            com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$TutorialType r1 = com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor.TutorialType.KEYBOARD
            if (r8 == r1) goto L2c
            com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$TutorialType r1 = com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor.TutorialType.BOTH
            if (r8 != r1) goto L5d
        L2c:
            com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor r8 = r7.this$0
            com.android.systemui.inputdevice.tutorial.data.repository.TutorialSchedulerRepository r8 = r8.repo
            com.android.systemui.inputdevice.tutorial.data.repository.DeviceType r1 = com.android.systemui.inputdevice.tutorial.data.repository.DeviceType.KEYBOARD
            java.time.Instant r5 = java.time.Instant.now()
            r7.label = r4
            r8.getClass()
            java.lang.String r1 = r1.name()
            java.lang.String r1 = androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0.m(r1, r2)
            androidx.datastore.preferences.core.Preferences$Key r4 = new androidx.datastore.preferences.core.Preferences$Key
            r4.<init>(r1)
            long r5 = r5.getEpochSecond()
            java.lang.Long r1 = new java.lang.Long
            r1.<init>(r5)
            java.lang.Object r8 = r8.updateData(r4, r1, r7)
            if (r8 != r0) goto L58
            goto L5a
        L58:
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
        L5a:
            if (r8 != r0) goto L5d
            goto L97
        L5d:
            com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$TutorialType r8 = r7.$tutorialType
            com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$TutorialType r1 = com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor.TutorialType.TOUCHPAD
            if (r8 == r1) goto L67
            com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$TutorialType r1 = com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor.TutorialType.BOTH
            if (r8 != r1) goto L98
        L67:
            com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor r8 = r7.this$0
            com.android.systemui.inputdevice.tutorial.data.repository.TutorialSchedulerRepository r8 = r8.repo
            com.android.systemui.inputdevice.tutorial.data.repository.DeviceType r1 = com.android.systemui.inputdevice.tutorial.data.repository.DeviceType.TOUCHPAD
            java.time.Instant r4 = java.time.Instant.now()
            r7.label = r3
            r8.getClass()
            java.lang.String r1 = r1.name()
            java.lang.String r1 = androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0.m(r1, r2)
            androidx.datastore.preferences.core.Preferences$Key r2 = new androidx.datastore.preferences.core.Preferences$Key
            r2.<init>(r1)
            long r3 = r4.getEpochSecond()
            java.lang.Long r1 = new java.lang.Long
            r1.<init>(r3)
            java.lang.Object r7 = r8.updateData(r2, r1, r7)
            if (r7 != r0) goto L93
            goto L95
        L93:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
        L95:
            if (r7 != r0) goto L98
        L97:
            return r0
        L98:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.inputdevice.tutorial.domain.interactor.TutorialSchedulerInteractor$updateLaunchInfo$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
