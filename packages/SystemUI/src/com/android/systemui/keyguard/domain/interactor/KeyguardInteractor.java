package com.android.systemui.keyguard.domain.interactor;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.Flags;
import com.android.systemui.R;
import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepository;
import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.common.shared.model.NotificationContainerBounds;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.shared.model.CameraLaunchSourceModel;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.notification.stack.domain.interactor.SharedNotificationContainerInteractor;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.kotlin.Utils;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import javax.inject.Provider;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardInteractor {
    public final ChannelFlowTransformLatest _nonSplitShadeNotifciationPlaceholderBounds;
    public final StateFlowImpl _notificationPlaceholderBounds;
    public final Flow alternateBouncerShowing;
    public final ReadonlyStateFlow ambientIndicationVisible;
    public final Lazy animateDozingTransitions$delegate;
    public final StateFlow biometricUnlockState;
    public final Flow clockShouldBeCentered;
    public final CommandQueue commandQueue;
    public final Flow dismissAlpha;
    public final Flow dozeAmount;
    public final StateFlow dozeTimeTick;
    public final Flow dozeTransitionModel;
    public final Provider fromGoneTransitionInteractor;
    public final Provider fromLockscreenTransitionInteractor;
    public final ReadonlyStateFlow isAbleToDream;
    public final StateFlow isActiveDreamLockscreenHosted;
    public final StateFlow isAodAvailable;
    public final StateFlow isDozing;
    public final MutableStateFlow isDreaming;
    public final Flow isEncryptedOrLockdown;
    public final StateFlow isKeyguardDismissible;
    public final Flow isKeyguardGoingAway;
    public final Flow isKeyguardOccluded;
    public final Flow isKeyguardShowing;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isKeyguardVisible;
    public final KeyguardInteractor$special$$inlined$map$1 isPulsing;
    public final Lazy isSecureCameraActive$delegate;
    public final StateFlow keyguardAlpha;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final ReadonlyStateFlow keyguardTranslationY;
    public final ReadonlyStateFlow lastRootViewTapPosition;
    public final Lazy notificationContainerBounds$delegate;
    public final Flow onCameraLaunchDetected;
    public final ReadonlyStateFlow primaryBouncerShowing;
    public final KeyguardRepository repository;
    public final StateFlow statusBarState;
    public final Lazy topClippingBounds$delegate;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[CameraLaunchSourceModel.values().length];
            try {
                iArr[CameraLaunchSourceModel.WIGGLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[CameraLaunchSourceModel.POWER_DOUBLE_TAP.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[CameraLaunchSourceModel.LIFT_TRIGGER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[CameraLaunchSourceModel.QUICK_AFFORDANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r6v3, types: [com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$1] */
    public KeyguardInteractor(KeyguardRepository keyguardRepository, CommandQueue commandQueue, PowerInteractor powerInteractor, KeyguardBouncerRepository keyguardBouncerRepository, ConfigurationInteractor configurationInteractor, ShadeRepository shadeRepository, KeyguardTransitionInteractor keyguardTransitionInteractor, final Provider provider, Provider provider2, Provider provider3, final Provider provider4, final CoroutineScope coroutineScope) {
        this.repository = keyguardRepository;
        this.commandQueue = commandQueue;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.fromGoneTransitionInteractor = provider2;
        this.fromLockscreenTransitionInteractor = provider3;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(new NotificationContainerBounds(0.0f, 0.0f, false, 7, null));
        this._notificationPlaceholderBounds = MutableStateFlow;
        this._nonSplitShadeNotifciationPlaceholderBounds = FlowKt.transformLatest(com.android.systemui.util.kotlin.FlowKt.pairwise(MutableStateFlow), new KeyguardInteractor$special$$inlined$flatMapLatest$1(null, this));
        this.notificationContainerBounds$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$notificationContainerBounds$2

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$notificationContainerBounds$2$1, reason: invalid class name */
            final class AnonymousClass1 extends SuspendLambda implements Function4 {
                /* synthetic */ Object L$0;
                /* synthetic */ Object L$1;
                /* synthetic */ Object L$2;
                int label;

                public AnonymousClass1(Continuation continuation) {
                    super(4, continuation);
                }

                @Override // kotlin.jvm.functions.Function4
                public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1((Continuation) obj4);
                    anonymousClass1.L$0 = (NotificationContainerBounds) obj;
                    anonymousClass1.L$1 = (NotificationContainerBounds) obj2;
                    anonymousClass1.L$2 = (SharedNotificationContainerInteractor.ConfigurationBasedDimensions) obj3;
                    return anonymousClass1.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    NotificationContainerBounds notificationContainerBounds = (NotificationContainerBounds) this.L$0;
                    Flags.migrateClocksToBlueprint();
                    return notificationContainerBounds;
                }
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                int i = SceneContainerFlag.$r8$clinit;
                Flags.sceneContainer();
                KeyguardInteractor keyguardInteractor = KeyguardInteractor.this;
                return FlowKt.stateIn(FlowKt.combine(keyguardInteractor._notificationPlaceholderBounds, keyguardInteractor._nonSplitShadeNotifciationPlaceholderBounds, ((SharedNotificationContainerInteractor) provider4.get()).configurationBasedDimensions, new AnonymousClass1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), new NotificationContainerBounds(0.0f, 0.0f, false, 7, null));
            }
        });
        KeyguardRepositoryImpl keyguardRepositoryImpl = (KeyguardRepositoryImpl) keyguardRepository;
        this.dozeAmount = keyguardRepositoryImpl.linearDozeAmount;
        this.isDozing = keyguardRepositoryImpl.isDozing;
        this.dozeTimeTick = keyguardRepositoryImpl.dozeTimeTick;
        this.isAodAvailable = keyguardRepositoryImpl.isAodAvailable;
        final Flow flow = keyguardRepositoryImpl.dozeTransitionModel;
        this.dozeTransitionModel = flow;
        this.isPulsing = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4c
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.keyguard.shared.model.DozeTransitionModel r5 = (com.android.systemui.keyguard.shared.model.DozeTransitionModel) r5
                        com.android.systemui.keyguard.shared.model.DozeStateModel r5 = r5.to
                        com.android.systemui.keyguard.shared.model.DozeStateModel r6 = com.android.systemui.keyguard.shared.model.DozeStateModel.DOZE_PULSING
                        if (r5 != r6) goto L3c
                        r5 = r3
                        goto L3d
                    L3c:
                        r5 = 0
                    L3d:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4c
                        return r1
                    L4c:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        StateFlowImpl stateFlowImpl = keyguardRepositoryImpl.isDreaming;
        this.isDreaming = stateFlowImpl;
        Flow flow2 = keyguardRepositoryImpl.isDreamingWithOverlay;
        this.isActiveDreamLockscreenHosted = keyguardRepositoryImpl.isActiveDreamLockscreenHosted;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        KeyguardInteractor$onCameraLaunchDetected$1 keyguardInteractor$onCameraLaunchDetected$1 = new KeyguardInteractor$onCameraLaunchDetected$1(this, null);
        conflatedCallbackFlow.getClass();
        this.onCameraLaunchDetected = FlowConflatedKt.conflatedCallbackFlow(keyguardInteractor$onCameraLaunchDetected$1);
        Flow debounce = FlowKt.debounce(com.android.systemui.util.kotlin.FlowKt.sample(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(FlowKt.merge(stateFlowImpl, flow2), flow, new KeyguardInteractor$isAbleToDream$1(null)), powerInteractor.isAwake, new KeyguardInteractor$isAbleToDream$2(null)), 50L);
        SharingStarted.Companion companion = SharingStarted.Companion;
        ReadonlyStateFlow stateIn = FlowKt.stateIn(debounce, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.FALSE);
        this.isAbleToDream = stateIn;
        Flow flow3 = keyguardRepositoryImpl.isKeyguardShowing;
        this.isKeyguardShowing = flow3;
        ReadonlyStateFlow readonlyStateFlow = keyguardRepositoryImpl.isKeyguardDismissible;
        this.isKeyguardDismissible = readonlyStateFlow;
        Flow flow4 = keyguardRepositoryImpl.isKeyguardOccluded;
        this.isKeyguardOccluded = flow4;
        this.isKeyguardGoingAway = keyguardRepositoryImpl.isKeyguardGoingAway;
        this.topClippingBounds$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$topClippingBounds$2

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$topClippingBounds$2$1, reason: invalid class name */
            final class AnonymousClass1 extends SuspendLambda implements Function2 {
                private /* synthetic */ Object L$0;
                int label;

                public AnonymousClass1(Continuation continuation) {
                    super(2, continuation);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1(continuation);
                    anonymousClass1.L$0 = obj;
                    return anonymousClass1;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Float f = new Float(0.0f);
                        this.label = 1;
                        if (flowCollector.emit(f, this) == coroutineSingletons) {
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

            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Utils.Companion companion2 = Utils.Companion;
                KeyguardInteractor keyguardInteractor = KeyguardInteractor.this;
                StateFlowImpl stateFlowImpl2 = ((KeyguardRepositoryImpl) keyguardInteractor.repository).topClippingBounds;
                SceneKey sceneKey = Scenes.Bouncer;
                KeyguardState keyguardState = KeyguardState.GONE;
                KeyguardTransitionInteractor keyguardTransitionInteractor2 = keyguardInteractor.keyguardTransitionInteractor;
                keyguardTransitionInteractor2.getClass();
                Flags.sceneContainer();
                return FlowKt.distinctUntilChanged(companion2.sampleFilter(stateFlowImpl2, new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AnonymousClass1(null), keyguardTransitionInteractor2.transitionValue(keyguardState)), new Function1() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$topClippingBounds$2.2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return Boolean.valueOf(!(((Number) obj).floatValue() == 1.0f));
                    }
                }));
            }
        });
        this.lastRootViewTapPosition = FlowKt.asStateFlow(keyguardRepositoryImpl.lastRootViewTapPosition);
        this.ambientIndicationVisible = FlowKt.asStateFlow(keyguardRepositoryImpl.ambientIndicationVisible);
        KeyguardBouncerRepositoryImpl keyguardBouncerRepositoryImpl = (KeyguardBouncerRepositoryImpl) keyguardBouncerRepository;
        this.primaryBouncerShowing = keyguardBouncerRepositoryImpl.primaryBouncerShow;
        this.alternateBouncerShowing = com.android.systemui.util.kotlin.FlowKt.sample(keyguardBouncerRepositoryImpl.alternateBouncerVisible, stateIn, new KeyguardInteractor$alternateBouncerShowing$1(null));
        ReadonlyStateFlow readonlyStateFlow2 = keyguardRepositoryImpl.statusBarState;
        this.statusBarState = readonlyStateFlow2;
        this.biometricUnlockState = keyguardRepositoryImpl.biometricUnlockState;
        this.isKeyguardVisible = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(flow3, flow4, new KeyguardInteractor$isKeyguardVisible$1(null));
        this.isSecureCameraActive$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$isSecureCameraActive$2

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$isSecureCameraActive$2$1, reason: invalid class name */
            final class AnonymousClass1 extends SuspendLambda implements Function4 {
                /* synthetic */ Object L$0;
                /* synthetic */ boolean Z$0;
                /* synthetic */ boolean Z$1;
                int label;

                public AnonymousClass1(Continuation continuation) {
                    super(4, continuation);
                }

                @Override // kotlin.jvm.functions.Function4
                public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                    boolean booleanValue = ((Boolean) obj).booleanValue();
                    boolean booleanValue2 = ((Boolean) obj2).booleanValue();
                    AnonymousClass1 anonymousClass1 = new AnonymousClass1((Continuation) obj4);
                    anonymousClass1.Z$0 = booleanValue;
                    anonymousClass1.Z$1 = booleanValue2;
                    anonymousClass1.L$0 = (CameraLaunchSourceModel) obj3;
                    return anonymousClass1.invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    if (this.label != 0) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(obj);
                    boolean z = this.Z$0;
                    boolean z2 = this.Z$1;
                    CameraLaunchSourceModel cameraLaunchSourceModel = (CameraLaunchSourceModel) this.L$0;
                    boolean z3 = false;
                    if (!z && !z2 && cameraLaunchSourceModel == CameraLaunchSourceModel.POWER_DOUBLE_TAP) {
                        z3 = true;
                    }
                    return Boolean.valueOf(z3);
                }
            }

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$isSecureCameraActive$2$2, reason: invalid class name */
            final class AnonymousClass2 extends SuspendLambda implements Function2 {
                private /* synthetic */ Object L$0;
                int label;

                public AnonymousClass2(Continuation continuation) {
                    super(2, continuation);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    AnonymousClass2 anonymousClass2 = new AnonymousClass2(continuation);
                    anonymousClass2.L$0 = obj;
                    return anonymousClass2;
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i = this.label;
                    if (i == 0) {
                        ResultKt.throwOnFailure(obj);
                        FlowCollector flowCollector = (FlowCollector) this.L$0;
                        Boolean bool = Boolean.FALSE;
                        this.label = 1;
                        if (flowCollector.emit(bool, this) == coroutineSingletons) {
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

            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                KeyguardInteractor keyguardInteractor = KeyguardInteractor.this;
                FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = keyguardInteractor.isKeyguardVisible;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(null);
                return new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AnonymousClass2(null), FlowKt.combine(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, keyguardInteractor.primaryBouncerShowing, keyguardInteractor.onCameraLaunchDetected, anonymousClass1));
            }
        });
        this.keyguardAlpha = keyguardRepositoryImpl.keyguardAlpha;
        final Flow sample = Utils.Companion.sample(((ShadeRepositoryImpl) shadeRepository).legacyShadeExpansion, readonlyStateFlow2, keyguardTransitionInteractor.currentKeyguardState, keyguardTransitionInteractor.transitionState, readonlyStateFlow);
        this.dismissAlpha = FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardInteractor$dismissAlpha$3(null), new SafeFlow(new KeyguardInteractor$special$$inlined$transform$1(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4f
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        com.android.systemui.util.kotlin.Quint r6 = (com.android.systemui.util.kotlin.Quint) r6
                        java.lang.Object r6 = r6.component4()
                        com.android.systemui.keyguard.shared.model.TransitionStep r6 = (com.android.systemui.keyguard.shared.model.TransitionStep) r6
                        com.android.systemui.keyguard.shared.model.TransitionState r6 = r6.transitionState
                        boolean r6 = r6.isTransitioning()
                        r6 = r6 ^ r3
                        if (r6 == 0) goto L4f
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4f
                        return r1
                    L4f:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, null))));
        this.keyguardTranslationY = FlowKt.stateIn(FlowKt.transformLatest(configurationInteractor.dimensionPixelSize(R.dimen.keyguard_translate_distance_on_swipe_up), new KeyguardInteractor$special$$inlined$flatMapLatest$2(null, shadeRepository, this)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Float.valueOf(0.0f));
        this.clockShouldBeCentered = keyguardRepositoryImpl.clockShouldBeCentered;
        this.animateDozingTransitions$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$animateDozingTransitions$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Flags.sceneContainer();
                return ((KeyguardRepositoryImpl) this.repository).animateBottomAreaDozingTransitions;
            }
        });
        this.isEncryptedOrLockdown = keyguardRepositoryImpl.isEncryptedOrLockdown;
    }

    public static CameraLaunchSourceModel cameraLaunchSourceIntToModel(int i) {
        if (i == 0) {
            return CameraLaunchSourceModel.WIGGLE;
        }
        if (i == 1) {
            return CameraLaunchSourceModel.POWER_DOUBLE_TAP;
        }
        if (i == 2) {
            return CameraLaunchSourceModel.LIFT_TRIGGER;
        }
        if (i == 3) {
            return CameraLaunchSourceModel.QUICK_AFFORDANCE;
        }
        throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Invalid CameraLaunchSourceModel int value: "));
    }

    public static int cameraLaunchSourceModelToInt(CameraLaunchSourceModel cameraLaunchSourceModel) {
        int i = WhenMappings.$EnumSwitchMapping$0[cameraLaunchSourceModel.ordinal()];
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return 1;
        }
        if (i == 3) {
            return 2;
        }
        if (i == 4) {
            return 3;
        }
        throw new IllegalArgumentException("Invalid CameraLaunchSourceModel model: " + cameraLaunchSourceModel);
    }

    public final boolean isKeyguardShowing() {
        return ((KeyguardStateControllerImpl) ((KeyguardRepositoryImpl) this.repository).keyguardStateController).mShowing;
    }

    public final void showKeyguard() {
        FromGoneTransitionInteractor fromGoneTransitionInteractor = (FromGoneTransitionInteractor) this.fromGoneTransitionInteractor.get();
        fromGoneTransitionInteractor.getClass();
        BuildersKt.launch$default(fromGoneTransitionInteractor.scope, EmptyCoroutineContext.INSTANCE, null, new FromGoneTransitionInteractor$showKeyguard$$inlined$launch$default$1("FromGoneTransitionInteractor#showKeyguard", null, fromGoneTransitionInteractor), 2);
    }
}
