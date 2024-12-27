package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

public final class DeviceEntryBackgroundViewModel$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ AlternateBouncerToAodTransitionViewModel $alternateBouncerToAodTransitionViewModel$inlined;
    final /* synthetic */ AlternateBouncerToDozingTransitionViewModel $alternateBouncerToDozingTransitionViewModel$inlined;
    final /* synthetic */ AodToLockscreenTransitionViewModel $aodToLockscreenTransitionViewModel$inlined;
    final /* synthetic */ DozingToLockscreenTransitionViewModel $dozingToLockscreenTransitionViewModel$inlined;
    final /* synthetic */ DreamingToAodTransitionViewModel $dreamingToAodTransitionViewModel$inlined;
    final /* synthetic */ DreamingToLockscreenTransitionViewModel $dreamingToLockscreenTransitionViewModel$inlined;
    final /* synthetic */ GoneToAodTransitionViewModel $goneToAodTransitionViewModel$inlined;
    final /* synthetic */ GoneToDozingTransitionViewModel $goneToDozingTransitionViewModel$inlined;
    final /* synthetic */ GoneToLockscreenTransitionViewModel $goneToLockscreenTransitionViewModel$inlined;
    final /* synthetic */ KeyguardTransitionInteractor $keyguardTransitionInteractor$inlined;
    final /* synthetic */ LockscreenToAodTransitionViewModel $lockscreenToAodTransitionViewModel$inlined;
    final /* synthetic */ LockscreenToDozingTransitionViewModel $lockscreenToDozingTransitionViewModel$inlined;
    final /* synthetic */ OccludedToAodTransitionViewModel $occludedToAodTransitionViewModel$inlined;
    final /* synthetic */ OccludedToLockscreenTransitionViewModel $occludedToLockscreenTransitionViewModel$inlined;
    final /* synthetic */ PrimaryBouncerToAodTransitionViewModel $primaryBouncerToAodTransitionViewModel$inlined;
    final /* synthetic */ PrimaryBouncerToDozingTransitionViewModel $primaryBouncerToDozingTransitionViewModel$inlined;
    final /* synthetic */ PrimaryBouncerToLockscreenTransitionViewModel $primaryBouncerToLockscreenTransitionViewModel$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryBackgroundViewModel$special$$inlined$flatMapLatest$2(Continuation continuation, LockscreenToAodTransitionViewModel lockscreenToAodTransitionViewModel, AodToLockscreenTransitionViewModel aodToLockscreenTransitionViewModel, GoneToAodTransitionViewModel goneToAodTransitionViewModel, PrimaryBouncerToAodTransitionViewModel primaryBouncerToAodTransitionViewModel, OccludedToAodTransitionViewModel occludedToAodTransitionViewModel, OccludedToLockscreenTransitionViewModel occludedToLockscreenTransitionViewModel, DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel, AlternateBouncerToAodTransitionViewModel alternateBouncerToAodTransitionViewModel, GoneToLockscreenTransitionViewModel goneToLockscreenTransitionViewModel, GoneToDozingTransitionViewModel goneToDozingTransitionViewModel, PrimaryBouncerToDozingTransitionViewModel primaryBouncerToDozingTransitionViewModel, LockscreenToDozingTransitionViewModel lockscreenToDozingTransitionViewModel, DozingToLockscreenTransitionViewModel dozingToLockscreenTransitionViewModel, AlternateBouncerToDozingTransitionViewModel alternateBouncerToDozingTransitionViewModel, DreamingToAodTransitionViewModel dreamingToAodTransitionViewModel, PrimaryBouncerToLockscreenTransitionViewModel primaryBouncerToLockscreenTransitionViewModel, KeyguardTransitionInteractor keyguardTransitionInteractor) {
        super(3, continuation);
        this.$lockscreenToAodTransitionViewModel$inlined = lockscreenToAodTransitionViewModel;
        this.$aodToLockscreenTransitionViewModel$inlined = aodToLockscreenTransitionViewModel;
        this.$goneToAodTransitionViewModel$inlined = goneToAodTransitionViewModel;
        this.$primaryBouncerToAodTransitionViewModel$inlined = primaryBouncerToAodTransitionViewModel;
        this.$occludedToAodTransitionViewModel$inlined = occludedToAodTransitionViewModel;
        this.$occludedToLockscreenTransitionViewModel$inlined = occludedToLockscreenTransitionViewModel;
        this.$dreamingToLockscreenTransitionViewModel$inlined = dreamingToLockscreenTransitionViewModel;
        this.$alternateBouncerToAodTransitionViewModel$inlined = alternateBouncerToAodTransitionViewModel;
        this.$goneToLockscreenTransitionViewModel$inlined = goneToLockscreenTransitionViewModel;
        this.$goneToDozingTransitionViewModel$inlined = goneToDozingTransitionViewModel;
        this.$primaryBouncerToDozingTransitionViewModel$inlined = primaryBouncerToDozingTransitionViewModel;
        this.$lockscreenToDozingTransitionViewModel$inlined = lockscreenToDozingTransitionViewModel;
        this.$dozingToLockscreenTransitionViewModel$inlined = dozingToLockscreenTransitionViewModel;
        this.$alternateBouncerToDozingTransitionViewModel$inlined = alternateBouncerToDozingTransitionViewModel;
        this.$dreamingToAodTransitionViewModel$inlined = dreamingToAodTransitionViewModel;
        this.$primaryBouncerToLockscreenTransitionViewModel$inlined = primaryBouncerToLockscreenTransitionViewModel;
        this.$keyguardTransitionInteractor$inlined = keyguardTransitionInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DeviceEntryBackgroundViewModel$special$$inlined$flatMapLatest$2 deviceEntryBackgroundViewModel$special$$inlined$flatMapLatest$2 = new DeviceEntryBackgroundViewModel$special$$inlined$flatMapLatest$2((Continuation) obj3, this.$lockscreenToAodTransitionViewModel$inlined, this.$aodToLockscreenTransitionViewModel$inlined, this.$goneToAodTransitionViewModel$inlined, this.$primaryBouncerToAodTransitionViewModel$inlined, this.$occludedToAodTransitionViewModel$inlined, this.$occludedToLockscreenTransitionViewModel$inlined, this.$dreamingToLockscreenTransitionViewModel$inlined, this.$alternateBouncerToAodTransitionViewModel$inlined, this.$goneToLockscreenTransitionViewModel$inlined, this.$goneToDozingTransitionViewModel$inlined, this.$primaryBouncerToDozingTransitionViewModel$inlined, this.$lockscreenToDozingTransitionViewModel$inlined, this.$dozingToLockscreenTransitionViewModel$inlined, this.$alternateBouncerToDozingTransitionViewModel$inlined, this.$dreamingToAodTransitionViewModel$inlined, this.$primaryBouncerToLockscreenTransitionViewModel$inlined, this.$keyguardTransitionInteractor$inlined);
        deviceEntryBackgroundViewModel$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        deviceEntryBackgroundViewModel$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return deviceEntryBackgroundViewModel$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons;
        FlowCollector flowCollector;
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector2 = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                coroutineSingletons = coroutineSingletons2;
                flowCollector = flowCollector2;
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new DeviceEntryBackgroundViewModel$alpha$1$1(this.$keyguardTransitionInteractor$inlined, null), FlowKt.merge(SetsKt__SetsKt.setOf(this.$lockscreenToAodTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$aodToLockscreenTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$goneToAodTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$primaryBouncerToAodTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$occludedToAodTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$occludedToLockscreenTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$dreamingToLockscreenTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$alternateBouncerToAodTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$goneToLockscreenTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$goneToDozingTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$primaryBouncerToDozingTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$lockscreenToDozingTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$dozingToLockscreenTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$alternateBouncerToDozingTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$dreamingToAodTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$primaryBouncerToLockscreenTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha)));
            } else {
                coroutineSingletons = coroutineSingletons2;
                flowCollector = flowCollector2;
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new Float(0.0f));
            }
            this.label = 1;
            CoroutineSingletons coroutineSingletons3 = coroutineSingletons;
            if (FlowKt.emitAll(this, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, flowCollector) == coroutineSingletons3) {
                return coroutineSingletons3;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
