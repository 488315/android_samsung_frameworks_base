package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DeviceEntryBackgroundViewModel$special$$inlined$flatMapLatest$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ AlternateBouncerToAodTransitionViewModel $alternateBouncerToAodTransitionViewModel$inlined;
    final /* synthetic */ AlternateBouncerToDozingTransitionViewModel $alternateBouncerToDozingTransitionViewModel$inlined;
    final /* synthetic */ AodToLockscreenTransitionViewModel $aodToLockscreenTransitionViewModel$inlined;
    final /* synthetic */ DozingToLockscreenTransitionViewModel $dozingToLockscreenTransitionViewModel$inlined;
    final /* synthetic */ DreamingToAodTransitionViewModel $dreamingToAodTransitionViewModel$inlined;
    final /* synthetic */ DreamingToLockscreenTransitionViewModel $dreamingToLockscreenTransitionViewModel$inlined;
    final /* synthetic */ GlanceableHubToAodTransitionViewModel $glanceableHubToAodTransitionViewModel$inlined;
    final /* synthetic */ GlanceableHubToLockscreenTransitionViewModel $glanceableHubToLockscreenTransitionViewModel$inlined;
    final /* synthetic */ GoneToAodTransitionViewModel $goneToAodTransitionViewModel$inlined;
    final /* synthetic */ GoneToDozingTransitionViewModel $goneToDozingTransitionViewModel$inlined;
    final /* synthetic */ GoneToLockscreenTransitionViewModel $goneToLockscreenTransitionViewModel$inlined;
    final /* synthetic */ KeyguardTransitionInteractor $keyguardTransitionInteractor$inlined;
    final /* synthetic */ LockscreenToAodTransitionViewModel $lockscreenToAodTransitionViewModel$inlined;
    final /* synthetic */ LockscreenToDozingTransitionViewModel $lockscreenToDozingTransitionViewModel$inlined;
    final /* synthetic */ OccludedToAodTransitionViewModel $occludedToAodTransitionViewModel$inlined;
    final /* synthetic */ OccludedToDozingTransitionViewModel $occludedToDozingTransitionViewModel$inlined;
    final /* synthetic */ OccludedToLockscreenTransitionViewModel $occludedToLockscreenTransitionViewModel$inlined;
    final /* synthetic */ OffToLockscreenTransitionViewModel $offToLockscreenTransitionViewModel$inlined;
    final /* synthetic */ PrimaryBouncerToAodTransitionViewModel $primaryBouncerToAodTransitionViewModel$inlined;
    final /* synthetic */ PrimaryBouncerToDozingTransitionViewModel $primaryBouncerToDozingTransitionViewModel$inlined;
    final /* synthetic */ PrimaryBouncerToLockscreenTransitionViewModel $primaryBouncerToLockscreenTransitionViewModel$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceEntryBackgroundViewModel$special$$inlined$flatMapLatest$2(Continuation continuation, AlternateBouncerToAodTransitionViewModel alternateBouncerToAodTransitionViewModel, AlternateBouncerToDozingTransitionViewModel alternateBouncerToDozingTransitionViewModel, AodToLockscreenTransitionViewModel aodToLockscreenTransitionViewModel, DozingToLockscreenTransitionViewModel dozingToLockscreenTransitionViewModel, DreamingToAodTransitionViewModel dreamingToAodTransitionViewModel, DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel, GoneToAodTransitionViewModel goneToAodTransitionViewModel, GoneToDozingTransitionViewModel goneToDozingTransitionViewModel, GoneToLockscreenTransitionViewModel goneToLockscreenTransitionViewModel, LockscreenToAodTransitionViewModel lockscreenToAodTransitionViewModel, OccludedToAodTransitionViewModel occludedToAodTransitionViewModel, OccludedToDozingTransitionViewModel occludedToDozingTransitionViewModel, OccludedToLockscreenTransitionViewModel occludedToLockscreenTransitionViewModel, OffToLockscreenTransitionViewModel offToLockscreenTransitionViewModel, PrimaryBouncerToAodTransitionViewModel primaryBouncerToAodTransitionViewModel, PrimaryBouncerToDozingTransitionViewModel primaryBouncerToDozingTransitionViewModel, PrimaryBouncerToLockscreenTransitionViewModel primaryBouncerToLockscreenTransitionViewModel, LockscreenToDozingTransitionViewModel lockscreenToDozingTransitionViewModel, GlanceableHubToAodTransitionViewModel glanceableHubToAodTransitionViewModel, GlanceableHubToLockscreenTransitionViewModel glanceableHubToLockscreenTransitionViewModel, KeyguardTransitionInteractor keyguardTransitionInteractor) {
        super(3, continuation);
        this.$alternateBouncerToAodTransitionViewModel$inlined = alternateBouncerToAodTransitionViewModel;
        this.$alternateBouncerToDozingTransitionViewModel$inlined = alternateBouncerToDozingTransitionViewModel;
        this.$aodToLockscreenTransitionViewModel$inlined = aodToLockscreenTransitionViewModel;
        this.$dozingToLockscreenTransitionViewModel$inlined = dozingToLockscreenTransitionViewModel;
        this.$dreamingToAodTransitionViewModel$inlined = dreamingToAodTransitionViewModel;
        this.$dreamingToLockscreenTransitionViewModel$inlined = dreamingToLockscreenTransitionViewModel;
        this.$goneToAodTransitionViewModel$inlined = goneToAodTransitionViewModel;
        this.$goneToDozingTransitionViewModel$inlined = goneToDozingTransitionViewModel;
        this.$goneToLockscreenTransitionViewModel$inlined = goneToLockscreenTransitionViewModel;
        this.$lockscreenToAodTransitionViewModel$inlined = lockscreenToAodTransitionViewModel;
        this.$occludedToAodTransitionViewModel$inlined = occludedToAodTransitionViewModel;
        this.$occludedToDozingTransitionViewModel$inlined = occludedToDozingTransitionViewModel;
        this.$occludedToLockscreenTransitionViewModel$inlined = occludedToLockscreenTransitionViewModel;
        this.$offToLockscreenTransitionViewModel$inlined = offToLockscreenTransitionViewModel;
        this.$primaryBouncerToAodTransitionViewModel$inlined = primaryBouncerToAodTransitionViewModel;
        this.$primaryBouncerToDozingTransitionViewModel$inlined = primaryBouncerToDozingTransitionViewModel;
        this.$primaryBouncerToLockscreenTransitionViewModel$inlined = primaryBouncerToLockscreenTransitionViewModel;
        this.$lockscreenToDozingTransitionViewModel$inlined = lockscreenToDozingTransitionViewModel;
        this.$glanceableHubToAodTransitionViewModel$inlined = glanceableHubToAodTransitionViewModel;
        this.$glanceableHubToLockscreenTransitionViewModel$inlined = glanceableHubToLockscreenTransitionViewModel;
        this.$keyguardTransitionInteractor$inlined = keyguardTransitionInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        DeviceEntryBackgroundViewModel$special$$inlined$flatMapLatest$2 deviceEntryBackgroundViewModel$special$$inlined$flatMapLatest$2 = new DeviceEntryBackgroundViewModel$special$$inlined$flatMapLatest$2((Continuation) obj3, this.$alternateBouncerToAodTransitionViewModel$inlined, this.$alternateBouncerToDozingTransitionViewModel$inlined, this.$aodToLockscreenTransitionViewModel$inlined, this.$dozingToLockscreenTransitionViewModel$inlined, this.$dreamingToAodTransitionViewModel$inlined, this.$dreamingToLockscreenTransitionViewModel$inlined, this.$goneToAodTransitionViewModel$inlined, this.$goneToDozingTransitionViewModel$inlined, this.$goneToLockscreenTransitionViewModel$inlined, this.$lockscreenToAodTransitionViewModel$inlined, this.$occludedToAodTransitionViewModel$inlined, this.$occludedToDozingTransitionViewModel$inlined, this.$occludedToLockscreenTransitionViewModel$inlined, this.$offToLockscreenTransitionViewModel$inlined, this.$primaryBouncerToAodTransitionViewModel$inlined, this.$primaryBouncerToDozingTransitionViewModel$inlined, this.$primaryBouncerToLockscreenTransitionViewModel$inlined, this.$lockscreenToDozingTransitionViewModel$inlined, this.$glanceableHubToAodTransitionViewModel$inlined, this.$glanceableHubToLockscreenTransitionViewModel$inlined, this.$keyguardTransitionInteractor$inlined);
        deviceEntryBackgroundViewModel$special$$inlined$flatMapLatest$2.L$0 = (FlowCollector) obj;
        deviceEntryBackgroundViewModel$special$$inlined$flatMapLatest$2.L$1 = obj2;
        return deviceEntryBackgroundViewModel$special$$inlined$flatMapLatest$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new DeviceEntryBackgroundViewModel$alpha$1$1(this.$keyguardTransitionInteractor$inlined, null), FlowKt.merge(ArraysKt___ArraysKt.toSet(new Flow[]{this.$alternateBouncerToAodTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$alternateBouncerToDozingTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$aodToLockscreenTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$dozingToLockscreenTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$dreamingToAodTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$dreamingToLockscreenTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$goneToAodTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$goneToDozingTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$goneToLockscreenTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$lockscreenToAodTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$occludedToAodTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$occludedToDozingTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$occludedToLockscreenTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$offToLockscreenTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$primaryBouncerToAodTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$primaryBouncerToDozingTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$primaryBouncerToLockscreenTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$lockscreenToDozingTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$glanceableHubToAodTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha, this.$glanceableHubToLockscreenTransitionViewModel$inlined.deviceEntryBackgroundViewAlpha})));
            } else {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new Float(0.0f));
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, this) == coroutineSingletons) {
                return coroutineSingletons;
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
