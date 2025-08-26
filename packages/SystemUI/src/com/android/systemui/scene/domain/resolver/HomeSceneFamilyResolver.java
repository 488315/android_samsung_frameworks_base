package com.android.systemui.scene.domain.resolver;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.scene.shared.model.SceneFamilies;
import com.android.systemui.scene.shared.model.Scenes;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.internal.CombineKt;

/* loaded from: classes2.dex */
public final class HomeSceneFamilyResolver implements SceneResolver {
    public static final Set homeScenes;
    public final ReadonlyStateFlow resolvedScene;
    public final SceneKey targetFamily = SceneFamilies.Home;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        homeScenes = ArraysKt___ArraysKt.toSet(new SceneKey[]{Scenes.Gone, Scenes.Lockscreen, Scenes.Dream});
    }

    public HomeSceneFamilyResolver(CoroutineScope coroutineScope, DeviceEntryInteractor deviceEntryInteractor, KeyguardInteractor keyguardInteractor, KeyguardEnabledInteractor keyguardEnabledInteractor) {
        ReadonlyStateFlow readonlyStateFlow = keyguardEnabledInteractor.isKeyguardEnabled;
        StateFlow stateFlow = (StateFlow) deviceEntryInteractor.canSwipeToEnter$delegate.getValue();
        Flow flow = keyguardInteractor.isDreamingWithOverlay;
        ReadonlyStateFlow readonlyStateFlow2 = deviceEntryInteractor.isDeviceEntered;
        ReadonlyStateFlow readonlyStateFlow3 = deviceEntryInteractor.isUnlocked;
        final Flow[] flowArr = {readonlyStateFlow, stateFlow, readonlyStateFlow2, readonlyStateFlow3, flow, keyguardInteractor.isAbleToDream};
        Flow flow2 = new Flow() { // from class: com.android.systemui.scene.domain.resolver.HomeSceneFamilyResolver$special$$inlined$combine$1

            /* renamed from: com.android.systemui.scene.domain.resolver.HomeSceneFamilyResolver$special$$inlined$combine$1$3, reason: invalid class name */
            public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                private /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                int label;
                final /* synthetic */ HomeSceneFamilyResolver receiver$inlined;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass3(Continuation continuation, HomeSceneFamilyResolver homeSceneFamilyResolver) {
                    super(3, continuation);
                    this.receiver$inlined = homeSceneFamilyResolver;
                }

                @Override // kotlin.jvm.functions.Function3
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3, this.receiver$inlined);
                    anonymousClass3.L$0 = (FlowCollector) obj;
                    anonymousClass3.L$1 = (Object[]) obj2;
                    return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Object[] objArr = (Object[]) this.L$1;
                        Object obj2 = objArr[0];
                        Object obj3 = objArr[1];
                        Object obj4 = objArr[2];
                        Object obj5 = objArr[3];
                        Object obj6 = objArr[4];
                        boolean zBooleanValue = ((Boolean) objArr[5]).booleanValue();
                        boolean zBooleanValue2 = ((Boolean) obj6).booleanValue();
                        boolean zBooleanValue3 = ((Boolean) obj5).booleanValue();
                        boolean zBooleanValue4 = ((Boolean) obj4).booleanValue();
                        boolean zBooleanValue5 = ((Boolean) obj2).booleanValue();
                        HomeSceneFamilyResolver homeSceneFamilyResolver = this.receiver$inlined;
                        Set set = HomeSceneFamilyResolver.homeScenes;
                        homeSceneFamilyResolver.getClass();
                        SceneKey sceneKeyHomeScene = HomeSceneFamilyResolver.homeScene(zBooleanValue5, (Boolean) obj3, zBooleanValue4, zBooleanValue3, zBooleanValue2, zBooleanValue);
                        this.label = 1;
                        if (flowCollector.emit(sceneKeyHomeScene, this) == coroutineSingletons) {
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

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                final Flow[] flowArr2 = flowArr;
                Object objCombineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.scene.domain.resolver.HomeSceneFamilyResolver$special$$inlined$combine$1.2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        return new Object[flowArr2.length];
                    }
                }, new AnonymousClass3(null, this), flowCollector, continuation);
                return objCombineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? objCombineInternal : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion.getClass();
        this.resolvedScene = FlowKt.stateIn(flow2, coroutineScope, SharingStarted.Companion.Eagerly, homeScene(((Boolean) keyguardEnabledInteractor.isKeyguardEnabled.$$delegate_0.getValue()).booleanValue(), (Boolean) ((StateFlow) deviceEntryInteractor.canSwipeToEnter$delegate.getValue()).getValue(), ((Boolean) readonlyStateFlow2.$$delegate_0.getValue()).booleanValue(), ((Boolean) readonlyStateFlow3.$$delegate_0.getValue()).booleanValue(), false, false));
    }

    public static SceneKey homeScene(boolean z, Boolean bool, boolean z2, boolean z3, boolean z4, boolean z5) {
        return (z4 && z5) ? Scenes.Dream : !z ? Scenes.Gone : Intrinsics.areEqual(bool, Boolean.TRUE) ? Scenes.Lockscreen : !z2 ? Scenes.Lockscreen : !z3 ? Scenes.Lockscreen : Scenes.Gone;
    }
}
