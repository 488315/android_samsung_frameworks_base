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
import com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.shared.model.CameraLaunchSourceModel;
import com.android.systemui.keyguard.shared.model.CameraLaunchType;
import com.android.systemui.keyguard.shared.model.DozeStateModel;
import com.android.systemui.keyguard.shared.model.DozeTransitionModel;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.kotlin.Quint;
import com.android.systemui.util.kotlin.Utils;
import com.android.systemui.wallpapers.data.repository.WallpaperFocalAreaRepository;
import javax.inject.Provider;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
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
                KeyguardInteractor keyguardInteractor = this.f$0;
                Edge.StateToState stateToStateM = KeyguardInteractor$$ExternalSyntheticOutline0.m(Edge.Companion, KeyguardState.LOCKSCREEN, KeyguardState.AOD);
                String str = KeyguardTransitionInteractor.TAG;
                return FlowKt.stateIn(FlowKt.combineTransform(keyguardInteractor._notificationPlaceholderBounds, keyguardInteractor.keyguardTransitionInteractor.isInTransition(stateToStateM, null), ((ShadeRepositoryImpl) shadeRepository).isShadeLayoutWide, ((ConfigurationInteractorImpl) configurationInteractor).dimensionPixelSize(R.dimen.keyguard_split_shade_top_margin), new KeyguardInteractor$notificationContainerBounds$2$1(null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), new NotificationContainerBounds(0.0f, 0.0f, false, 7, null));
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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        Boolean boolValueOf = Boolean.valueOf(((DozeTransitionModel) obj).to == DozeStateModel.DOZE_PULSING);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        Flow flow2 = keyguardRepositoryImpl.isDreamingWithOverlay;
        this.isDreamingWithOverlay = flow2;
        ChannelLimitedFlowMerge channelLimitedFlowMergeMerge = FlowKt.merge(keyguardRepositoryImpl.isDreaming, flow2);
        SharingStarted.Companion companion = SharingStarted.Companion;
        companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        Boolean bool = Boolean.FALSE;
        this.isDreaming = FlowKt.stateIn(channelLimitedFlowMergeMerge, coroutineScope, startedEagerly, bool);
        this.isDreamingAny = keyguardRepositoryImpl.isDreaming;
        final KeyguardInteractor$special$$inlined$filter$1 keyguardInteractor$special$$inlined$filter$1 = new KeyguardInteractor$special$$inlined$filter$1(keyguardRepositoryImpl.onCameraLaunchDetected);
        this.onCameraLaunchDetected = keyguardInteractor$special$$inlined$filter$1;
        this.showDismissibleKeyguard = FlowKt.asStateFlow(keyguardRepositoryImpl.showDismissibleKeyguard);
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(FlowKt.transformLatest(flow, new KeyguardInteractor$special$$inlined$flatMapLatest$2(null, this)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), bool);
        this.isAbleToDream = readonlyStateFlowStateIn;
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
        this.alternateBouncerShowing = com.android.systemui.util.kotlin.FlowKt.sample(keyguardBouncerRepositoryImpl.alternateBouncerVisible, readonlyStateFlowStateIn, new KeyguardInteractor$alternateBouncerShowing$1(null));
        this.statusBarState = keyguardRepositoryImpl.statusBarState;
        this.biometricUnlockState = keyguardRepositoryImpl.biometricUnlockState;
        final FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(stateFlowImpl, stateFlowImpl3, new KeyguardInteractor$isKeyguardVisible$1(null));
        this.isKeyguardVisible = flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        final Flow flow3 = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$2

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        if (((CameraLaunchSourceModel) obj).type == CameraLaunchType.POWER_DOUBLE_TAP) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = keyguardInteractor$special$$inlined$filter$1.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        Flow flow4 = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$2

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        KeyguardInteractor.SecureCameraRelatedEventType secureCameraRelatedEventType = KeyguardInteractor.SecureCameraRelatedEventType.SecureCameraLaunched;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(secureCameraRelatedEventType, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flow3.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        final Flow flow5 = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$3

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        if (((Boolean) obj).booleanValue()) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        Flow flow6 = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$3

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        ((Boolean) obj).getClass();
                        KeyguardInteractor.SecureCameraRelatedEventType secureCameraRelatedEventType = KeyguardInteractor.SecureCameraRelatedEventType.KeyguardBecameVisible;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(secureCameraRelatedEventType, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flow5.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        final Flow flow7 = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$4

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        if (((Boolean) obj).booleanValue()) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        final ChannelLimitedFlowMerge channelLimitedFlowMergeMerge2 = FlowKt.merge(flow4, flow6, new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$4

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        ((Boolean) obj).getClass();
                        KeyguardInteractor.SecureCameraRelatedEventType secureCameraRelatedEventType = KeyguardInteractor.SecureCameraRelatedEventType.PrimaryBouncerBecameVisible;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(secureCameraRelatedEventType, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flow7.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        });
        this.isSecureCameraActive = FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardInteractor$isSecureCameraActive$8(null), new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$5

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    boolean z;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        int i3 = KeyguardInteractor.WhenMappings.$EnumSwitchMapping$0[((KeyguardInteractor.SecureCameraRelatedEventType) obj).ordinal()];
                        if (i3 != 1) {
                            z = false;
                            if (i3 != 2 && i3 != 3) {
                                throw new NoWhenBranchMatchedException();
                            }
                        } else {
                            z = true;
                        }
                        Boolean boolValueOf = Boolean.valueOf(z);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(boolValueOf, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = channelLimitedFlowMergeMerge2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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
        final Flow flowSample = companion2.sample(readonlyStateFlow2, readonlyStateFlow3, keyguardTransitionInteractor.transitionState, stateFlowImpl2, keyguardTransitionInteractor.isFinishedIn(KeyguardState.GLANCEABLE_HUB));
        this.dismissAlpha = FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new KeyguardInteractor$dismissAlpha$3(null), new SafeFlow(new KeyguardInteractor$special$$inlined$transform$1(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$filter$5

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        if (!((TransitionStep) ((Quint) obj).component3()).transitionState.isTransitioning()) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowSample.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, null, this))));
        this.keyguardTranslationY = FlowKt.stateIn(FlowKt.transformLatest(((ConfigurationInteractorImpl) configurationInteractor).dimensionPixelSize(R.dimen.keyguard_translate_distance_on_swipe_up), new KeyguardInteractor$special$$inlined$flatMapLatest$3(null, shadeRepository, this)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Float.valueOf(0.0f));
        this.animateDozingTransitions$delegate = LazyKt__LazyJVMKt.lazy(new KeyguardInteractor$$ExternalSyntheticLambda2(provider, this));
        final ReadonlyStateFlow readonlyStateFlow4 = keyguardRepositoryImpl2.isAodAvailable;
        this.asleepKeyguardState = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$special$$inlined$map$6

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        KeyguardState keyguardState = ((Boolean) obj).booleanValue() ? KeyguardState.AOD : KeyguardState.DOZING;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(keyguardState, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlow4.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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

    public final void setDismissActionForDex(KeyguardViewMediatorHelperImpl.AnonymousClass2 anonymousClass2) {
        ((KeyguardBouncerRepositoryImpl) this.bouncerRepository).bouncerDismissActionModelForDex = anonymousClass2 != null ? new BouncerDismissActionModel(null, null, anonymousClass2) : null;
    }

    public final void showKeyguard() {
        FromGoneTransitionInteractor fromGoneTransitionInteractor = (FromGoneTransitionInteractor) this.fromGoneTransitionInteractor.get();
        fromGoneTransitionInteractor.getClass();
        CoroutineTracingKt.launchTraced$default(fromGoneTransitionInteractor.scope, null, null, new FromGoneTransitionInteractor$showKeyguard$1(fromGoneTransitionInteractor, null), 6);
    }
}
