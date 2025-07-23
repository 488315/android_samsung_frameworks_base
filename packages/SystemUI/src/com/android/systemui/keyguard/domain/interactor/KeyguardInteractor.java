package com.android.systemui.keyguard.domain.interactor;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.R;
import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepository;
import com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl;
import com.android.systemui.bouncer.shared.model.BouncerDismissActionModel;
import com.android.systemui.common.shared.model.NotificationContainerBounds;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$handleSetPendingIntentAfterUnlock$2;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.shared.model.CameraLaunchType;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.kotlin.Utils;
import com.android.systemui.wallpapers.data.repository.WallpaperFocalAreaRepository;
import javax.inject.Provider;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KeyguardInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _notificationPlaceholderBounds = StateFlowKt.MutableStateFlow(new NotificationContainerBounds(0.0f, 0.0f, false, 7, null));
    public final Flow alternateBouncerShowing;
    public final ReadonlyStateFlow ambientIndicationVisible;
    public final Lazy animateDozingTransitions$delegate;
    public final ReadonlyStateFlow asleepKeyguardState;
    public final ReadonlyStateFlow biometricUnlockState;
    public final KeyguardBouncerRepository bouncerRepository;
    public final Flow dismissAlpha;
    public final Flow dozeAmount;
    public final ReadonlyStateFlow dozeTimeTick;
    public final Flow dozeTransitionModel;
    public final Provider fromAlternateBouncerTransitionInteractor;
    public final Provider fromGoneTransitionInteractor;
    public final Provider fromLockscreenTransitionInteractor;
    public final Provider fromOccludedTransitionInteractor;
    public final ReadonlyStateFlow isAbleToDream;
    public final ReadonlyStateFlow isAodAvailable;
    public final ReadonlyStateFlow isDozing;
    public final ReadonlyStateFlow isDreaming;
    public final StateFlowImpl isDreamingAny;
    public final Flow isDreamingWithOverlay;
    public final StateFlowImpl isKeyguardDismissible;
    public final ReadonlyStateFlow isKeyguardGoingAway;
    public final StateFlowImpl isKeyguardOccluded;
    public final StateFlowImpl isKeyguardShowing;
    public final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 isKeyguardVisible;
    public final KeyguardInteractor$special$$inlined$map$1 isPulsing;
    public final Flow isSecureCameraActive;
    public final KeyguardTransitionInteractor keyguardTransitionInteractor;
    public final ReadonlyStateFlow keyguardTranslationY;
    public final ReadonlyStateFlow lastRootViewTapPosition;
    public final Lazy notificationContainerBounds$delegate;
    public final KeyguardInteractor$special$$inlined$filter$1 onCameraLaunchDetected;
    public final ReadonlyStateFlow panelAlpha;
    public final ReadonlyStateFlow primaryBouncerShowing;
    public final KeyguardRepository repository;
    public final ReadonlyStateFlow showDismissibleKeyguard;
    public final ReadonlyStateFlow statusBarState;
    public final Lazy topClippingBounds$delegate;
    public final WallpaperFocalAreaRepository wallpaperFocalAreaRepository;
    public final StateFlowImpl zoomOut;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class SecureCameraRelatedEventType {
        public static final /* synthetic */ SecureCameraRelatedEventType[] $VALUES;
        public static final SecureCameraRelatedEventType KeyguardBecameVisible;
        public static final SecureCameraRelatedEventType PrimaryBouncerBecameVisible;
        public static final SecureCameraRelatedEventType SecureCameraLaunched;

        static {
            SecureCameraRelatedEventType secureCameraRelatedEventType = new SecureCameraRelatedEventType("KeyguardBecameVisible", 0);
            KeyguardBecameVisible = secureCameraRelatedEventType;
            SecureCameraRelatedEventType secureCameraRelatedEventType2 = new SecureCameraRelatedEventType("PrimaryBouncerBecameVisible", 1);
            PrimaryBouncerBecameVisible = secureCameraRelatedEventType2;
            SecureCameraRelatedEventType secureCameraRelatedEventType3 = new SecureCameraRelatedEventType("SecureCameraLaunched", 2);
            SecureCameraLaunched = secureCameraRelatedEventType3;
            SecureCameraRelatedEventType[] secureCameraRelatedEventTypeArr = {secureCameraRelatedEventType, secureCameraRelatedEventType2, secureCameraRelatedEventType3};
            $VALUES = secureCameraRelatedEventTypeArr;
            EnumEntriesKt.enumEntries(secureCameraRelatedEventTypeArr);
        }

        private SecureCameraRelatedEventType(String str, int i) {
        }

        public static SecureCameraRelatedEventType valueOf(String str) {
            return (SecureCameraRelatedEventType) Enum.valueOf(SecureCameraRelatedEventType.class, str);
        }

        public static SecureCameraRelatedEventType[] values() {
            return (SecureCameraRelatedEventType[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;
        public static final /* synthetic */ int[] $EnumSwitchMapping$2;

        static {
            int[] iArr = new int[SecureCameraRelatedEventType.values().length];
            try {
                iArr[SecureCameraRelatedEventType.SecureCameraLaunched.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SecureCameraRelatedEventType.KeyguardBecameVisible.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[SecureCameraRelatedEventType.PrimaryBouncerBecameVisible.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[CameraLaunchType.values().length];
            try {
                iArr2[CameraLaunchType.WIGGLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[CameraLaunchType.POWER_DOUBLE_TAP.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[CameraLaunchType.LIFT_TRIGGER.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[CameraLaunchType.QUICK_AFFORDANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$1 = iArr2;
            int[] iArr3 = new int[KeyguardState.values().length];
            try {
                iArr3[KeyguardState.LOCKSCREEN.ordinal()] = 1;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr3[KeyguardState.OCCLUDED.ordinal()] = 2;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr3[KeyguardState.ALTERNATE_BOUNCER.ordinal()] = 3;
            } catch (NoSuchFieldError unused10) {
            }
            $EnumSwitchMapping$2 = iArr3;
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r9v1, types: [com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$1] */
    public KeyguardInteractor(KeyguardRepository keyguardRepository, KeyguardBouncerRepository keyguardBouncerRepository, WallpaperFocalAreaRepository wallpaperFocalAreaRepository, final ConfigurationInteractor configurationInteractor, final ShadeRepository shadeRepository, KeyguardTransitionInteractor keyguardTransitionInteractor, Provider provider, Provider provider2, Provider provider3, Provider provider4, Provider provider5, final CoroutineScope coroutineScope) {
        this.repository = keyguardRepository;
        this.bouncerRepository = keyguardBouncerRepository;
        this.wallpaperFocalAreaRepository = wallpaperFocalAreaRepository;
        this.keyguardTransitionInteractor = keyguardTransitionInteractor;
        this.fromGoneTransitionInteractor = provider2;
        this.fromLockscreenTransitionInteractor = provider3;
        this.fromOccludedTransitionInteractor = provider4;
        this.fromAlternateBouncerTransitionInteractor = provider5;
        this.notificationContainerBounds$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i = KeyguardInteractor.$r8$clinit;
                RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                int i2 = SceneContainerFlag.$r8$clinit;
                KeyguardInteractor keyguardInteractor = KeyguardInteractor.this;
                Edge.StateToState m = KeyguardInteractor$$ExternalSyntheticOutline0.m(Edge.Companion, KeyguardState.LOCKSCREEN, KeyguardState.AOD);
                String str = KeyguardTransitionInteractor.TAG;
                return FlowKt.stateIn(FlowKt.combineTransform(keyguardInteractor._notificationPlaceholderBounds, keyguardInteractor.keyguardTransitionInteractor.isInTransition(m, null), ((ShadeRepositoryImpl) shadeRepository).isShadeLayoutWide, ((ConfigurationInteractorImpl) configurationInteractor).dimensionPixelSize(R.dimen.keyguard_split_shade_top_margin), new KeyguardInteractor$notificationContainerBounds$2$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), new NotificationContainerBounds(0.0f, 0.0f, false, 7, null));
            }
        });
        KeyguardRepositoryImpl keyguardRepositoryImpl = (KeyguardRepositoryImpl) keyguardRepository;
        this.isDozing = keyguardRepositoryImpl.isDozing;
        this.dozeTimeTick = keyguardRepositoryImpl.dozeTimeTick;
        this.isAodAvailable = keyguardRepositoryImpl.isAodAvailable;
        keyguardRepositoryImpl.getClass();
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        int i = SceneContainerFlag.$r8$clinit;
        this.dozeAmount = keyguardRepositoryImpl._preSceneLinearDozeAmount;
        final Flow flow = keyguardRepositoryImpl.dozeTransitionModel;
        this.dozeTransitionModel = flow;
        this.isPulsing = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

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
        Flow flow2 = keyguardRepositoryImpl.isDreamingWithOverlay;
        this.isDreamingWithOverlay = flow2;
        ChannelLimitedFlowMerge merge = FlowKt.merge(keyguardRepositoryImpl.isDreaming, flow2);
        SharingStarted.Companion companion = SharingStarted.Companion;
        companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        Boolean bool = Boolean.FALSE;
        this.isDreaming = FlowKt.stateIn(merge, coroutineScope, startedEagerly, bool);
        this.isDreamingAny = keyguardRepositoryImpl.isDreaming;
        final KeyguardInteractor$special$$inlined$filter$1 keyguardInteractor$special$$inlined$filter$1 = new KeyguardInteractor$special$$inlined$filter$1(keyguardRepositoryImpl.onCameraLaunchDetected);
        this.onCameraLaunchDetected = keyguardInteractor$special$$inlined$filter$1;
        this.showDismissibleKeyguard = FlowKt.asStateFlow(keyguardRepositoryImpl.showDismissibleKeyguard);
        ReadonlyStateFlow stateIn = FlowKt.stateIn(FlowKt.transformLatest(flow, new KeyguardInteractor$special$$inlined$flatMapLatest$2(null, this)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.isAbleToDream = stateIn;
        StateFlowImpl stateFlowImpl = keyguardRepositoryImpl.isKeyguardShowing;
        this.isKeyguardShowing = stateFlowImpl;
        StateFlowImpl stateFlowImpl2 = keyguardRepositoryImpl.isKeyguardDismissible;
        this.isKeyguardDismissible = stateFlowImpl2;
        StateFlowImpl stateFlowImpl3 = keyguardRepositoryImpl.isKeyguardOccluded;
        this.isKeyguardOccluded = stateFlowImpl3;
        this.isKeyguardGoingAway = FlowKt.asStateFlow(keyguardRepositoryImpl.isKeyguardGoingAway);
        this.topClippingBounds$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardInteractor$$ExternalSyntheticLambda2(this));
        this.lastRootViewTapPosition = FlowKt.asStateFlow(keyguardRepositoryImpl.lastRootViewTapPosition);
        this.ambientIndicationVisible = FlowKt.asStateFlow(keyguardRepositoryImpl.ambientIndicationVisible);
        KeyguardBouncerRepositoryImpl keyguardBouncerRepositoryImpl = (KeyguardBouncerRepositoryImpl) keyguardBouncerRepository;
        final ReadonlyStateFlow readonlyStateFlow = keyguardBouncerRepositoryImpl.primaryBouncerShow;
        this.primaryBouncerShowing = readonlyStateFlow;
        this.alternateBouncerShowing = com.android.systemui.util.kotlin.FlowKt.sample(keyguardBouncerRepositoryImpl.alternateBouncerVisible, stateIn, new KeyguardInteractor$alternateBouncerShowing$1(null));
        this.statusBarState = keyguardRepositoryImpl.statusBarState;
        this.biometricUnlockState = keyguardRepositoryImpl.biometricUnlockState;
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateFlowImpl, stateFlowImpl3, new KeyguardInteractor$isKeyguardVisible$1(null));
        this.isKeyguardVisible = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        final Flow flow3 = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$2$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$2$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L46
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        com.android.systemui.keyguard.shared.model.CameraLaunchSourceModel r6 = (com.android.systemui.keyguard.shared.model.CameraLaunchSourceModel) r6
                        com.android.systemui.keyguard.shared.model.CameraLaunchType r6 = r6.type
                        com.android.systemui.keyguard.shared.model.CameraLaunchType r2 = com.android.systemui.keyguard.shared.model.CameraLaunchType.POWER_DOUBLE_TAP
                        if (r6 != r2) goto L46
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        Flow flow4 = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L41
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.keyguard.shared.model.CameraLaunchSourceModel r5 = (com.android.systemui.keyguard.shared.model.CameraLaunchSourceModel) r5
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$SecureCameraRelatedEventType r5 = com.android.systemui.keyguard.domain.interactor.KeyguardInteractor.SecureCameraRelatedEventType.SecureCameraLaunched
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final Flow flow5 = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$3$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$3$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$3$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L46
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        java.lang.Boolean r6 = (java.lang.Boolean) r6
                        boolean r6 = r6.booleanValue()
                        if (r6 == 0) goto L46
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        Flow flow6 = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$3$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$3$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L44
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        r5.getClass()
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$SecureCameraRelatedEventType r5 = com.android.systemui.keyguard.domain.interactor.KeyguardInteractor.SecureCameraRelatedEventType.KeyguardBecameVisible
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L44
                        return r1
                    L44:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final Flow flow7 = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$4

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$4$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$4$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$4$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$4$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L46
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        java.lang.Boolean r6 = (java.lang.Boolean) r6
                        boolean r6 = r6.booleanValue()
                        if (r6 == 0) goto L46
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final ChannelLimitedFlowMerge merge2 = FlowKt.merge(flow4, flow6, new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$4

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$4$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$4$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$4$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$4$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L44
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        r5.getClass()
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$SecureCameraRelatedEventType r5 = com.android.systemui.keyguard.domain.interactor.KeyguardInteractor.SecureCameraRelatedEventType.PrimaryBouncerBecameVisible
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L44
                        return r1
                    L44:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        this.isSecureCameraActive = FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardInteractor$isSecureCameraActive$8(null), new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$5

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$5$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$5.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$5$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$5.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$5$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$5$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L5c
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$SecureCameraRelatedEventType r5 = (com.android.systemui.keyguard.domain.interactor.KeyguardInteractor.SecureCameraRelatedEventType) r5
                        int[] r6 = com.android.systemui.keyguard.domain.interactor.KeyguardInteractor.WhenMappings.$EnumSwitchMapping$0
                        int r5 = r5.ordinal()
                        r5 = r6[r5]
                        if (r5 == r3) goto L4c
                        r6 = 2
                        r2 = 0
                        if (r5 == r6) goto L4d
                        r6 = 3
                        if (r5 != r6) goto L46
                        goto L4d
                    L46:
                        kotlin.NoWhenBranchMatchedException r4 = new kotlin.NoWhenBranchMatchedException
                        r4.<init>()
                        throw r4
                    L4c:
                        r2 = r3
                    L4d:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r2)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L5c
                        return r1
                    L5c:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$5.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }));
        keyguardRepository.getClass();
        KeyguardRepositoryImpl keyguardRepositoryImpl2 = (KeyguardRepositoryImpl) keyguardRepository;
        this.panelAlpha = FlowKt.asStateFlow(keyguardRepositoryImpl2.panelAlpha);
        this.zoomOut = keyguardRepositoryImpl2.zoomOut;
        Utils.Companion companion2 = Utils.Companion;
        ReadonlyStateFlow readonlyStateFlow2 = ((ShadeRepositoryImpl) shadeRepository).legacyShadeExpansion;
        ReadonlyStateFlow readonlyStateFlow3 = keyguardTransitionInteractor.currentKeyguardState;
        SceneKey sceneKey = Scenes.Communal;
        final Flow sample = companion2.sample(readonlyStateFlow2, readonlyStateFlow3, keyguardTransitionInteractor.transitionState, stateFlowImpl2, keyguardTransitionInteractor.isFinishedIn(KeyguardState.GLANCEABLE_HUB));
        this.dismissAlpha = FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardInteractor$dismissAlpha$3(null), new SafeFlow(new KeyguardInteractor$special$$inlined$transform$1(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$5

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$5$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$5.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$5$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$5.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$5$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$5$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4e
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        com.android.systemui.util.kotlin.Quint r6 = (com.android.systemui.util.kotlin.Quint) r6
                        java.lang.Object r6 = r6.component3()
                        com.android.systemui.keyguard.shared.model.TransitionStep r6 = (com.android.systemui.keyguard.shared.model.TransitionStep) r6
                        com.android.systemui.keyguard.shared.model.TransitionState r6 = r6.transitionState
                        boolean r6 = r6.isTransitioning()
                        if (r6 != 0) goto L4e
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4e
                        return r1
                    L4e:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$5.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, null, this))));
        this.keyguardTranslationY = FlowKt.stateIn(FlowKt.transformLatest(((ConfigurationInteractorImpl) configurationInteractor).dimensionPixelSize(R.dimen.keyguard_translate_distance_on_swipe_up), new KeyguardInteractor$special$$inlined$flatMapLatest$3(null, shadeRepository, this)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Float.valueOf(0.0f));
        this.animateDozingTransitions$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardInteractor$$ExternalSyntheticLambda2(provider, this));
        final ReadonlyStateFlow readonlyStateFlow4 = keyguardRepositoryImpl2.isAodAvailable;
        this.asleepKeyguardState = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$6

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$6$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$6$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$6.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$6$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$6.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$6$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$6$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4a
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Boolean r5 = (java.lang.Boolean) r5
                        boolean r5 = r5.booleanValue()
                        if (r5 == 0) goto L3d
                        com.android.systemui.keyguard.shared.model.KeyguardState r5 = com.android.systemui.keyguard.shared.model.KeyguardState.AOD
                        goto L3f
                    L3d:
                        com.android.systemui.keyguard.shared.model.KeyguardState r5 = com.android.systemui.keyguard.shared.model.KeyguardState.DOZING
                    L3f:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4a
                        return r1
                    L4a:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$6.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, startedEagerly, KeyguardState.DOZING);
    }

    public static CameraLaunchType cameraLaunchSourceIntToType(int i) {
        if (i == 0) {
            return CameraLaunchType.WIGGLE;
        }
        if (i == 1) {
            return CameraLaunchType.POWER_DOUBLE_TAP;
        }
        if (i == 2) {
            return CameraLaunchType.LIFT_TRIGGER;
        }
        if (i == 3) {
            return CameraLaunchType.QUICK_AFFORDANCE;
        }
        throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Invalid CameraLaunchType int value: "));
    }

    public static int cameraLaunchSourceModelToInt(CameraLaunchType cameraLaunchType) {
        int i = WhenMappings.$EnumSwitchMapping$1[cameraLaunchType.ordinal()];
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
        throw new IllegalArgumentException("Invalid CameraLaunchType type: " + cameraLaunchType);
    }

    public final void dismissKeyguard() {
        int i = WhenMappings.$EnumSwitchMapping$2[((TransitionStep) this.keyguardTransitionInteractor.transitionState.$$delegate_0.getValue()).to.ordinal()];
        if (i == 1) {
            FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor = (FromLockscreenTransitionInteractor) this.fromLockscreenTransitionInteractor.get();
            fromLockscreenTransitionInteractor.getClass();
            CoroutineTracingKt.launchTraced$default(fromLockscreenTransitionInteractor.scope, null, null, new FromLockscreenTransitionInteractor$dismissKeyguard$1(fromLockscreenTransitionInteractor, null), 6);
            return;
        }
        if (i == 2) {
            FromOccludedTransitionInteractor fromOccludedTransitionInteractor = (FromOccludedTransitionInteractor) this.fromOccludedTransitionInteractor.get();
            fromOccludedTransitionInteractor.getClass();
            CoroutineTracingKt.launchTraced$default(fromOccludedTransitionInteractor.scope, null, null, new FromOccludedTransitionInteractor$dismissFromOccluded$1(fromOccludedTransitionInteractor, null), 7);
            return;
        }
        if (i != 3) {
            return;
        }
        FromAlternateBouncerTransitionInteractor fromAlternateBouncerTransitionInteractor = (FromAlternateBouncerTransitionInteractor) this.fromAlternateBouncerTransitionInteractor.get();
        fromAlternateBouncerTransitionInteractor.getClass();
        CoroutineTracingKt.launchTraced$default(fromAlternateBouncerTransitionInteractor.scope, null, null, new FromAlternateBouncerTransitionInteractor$dismissAlternateBouncer$1(fromAlternateBouncerTransitionInteractor, null), 7);
    }

    public final boolean isKeyguardShowing() {
        return ((KeyguardStateControllerImpl) ((KeyguardRepositoryImpl) this.repository).keyguardStateController).mShowing;
    }

    public final void setDismissActionForDex(KeyguardViewMediatorHelperImpl$handleSetPendingIntentAfterUnlock$2 keyguardViewMediatorHelperImpl$handleSetPendingIntentAfterUnlock$2) {
        ((KeyguardBouncerRepositoryImpl) this.bouncerRepository).bouncerDismissActionModelForDex = keyguardViewMediatorHelperImpl$handleSetPendingIntentAfterUnlock$2 != null ? new BouncerDismissActionModel(null, null, keyguardViewMediatorHelperImpl$handleSetPendingIntentAfterUnlock$2) : null;
    }

    public final void showKeyguard() {
        FromGoneTransitionInteractor fromGoneTransitionInteractor = (FromGoneTransitionInteractor) this.fromGoneTransitionInteractor.get();
        fromGoneTransitionInteractor.getClass();
        CoroutineTracingKt.launchTraced$default(fromGoneTransitionInteractor.scope, null, null, new FromGoneTransitionInteractor$showKeyguard$1(fromGoneTransitionInteractor, null), 6);
    }
}
