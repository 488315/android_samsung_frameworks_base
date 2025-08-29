package com.android.systemui.education.domain.interactor;

import android.os.SystemProperties;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import com.android.systemui.contextualeducation.GestureType;
import com.android.systemui.education.ContextualEducationMetricsLogger;
import com.android.systemui.education.data.model.EduDeviceConnectionTime;
import com.android.systemui.education.data.model.GestureEduModel;
import com.android.systemui.education.data.repository.UserContextualEducationRepository;
import com.android.systemui.education.data.repository.UserContextualEducationRepository$readEduDeviceConnectionTime$$inlined$map$1;
import com.android.systemui.education.shared.model.EducationInfo;
import com.android.systemui.education.shared.model.EducationUiType;
import com.android.systemui.inputdevice.data.model.UserDeviceConnectionStatus;
import com.android.systemui.inputdevice.data.repository.UserInputDeviceRepository;
import com.android.systemui.inputdevice.tutorial.data.repository.DeviceType;
import com.android.systemui.inputdevice.tutorial.data.repository.TutorialSchedulerRepository;
import com.android.systemui.recents.LauncherProxyService;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class KeyboardTouchpadEduInteractor implements CoreStartable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long initialDelayDuration;
    public static final long minIntervalBetweenEdu;
    public static final long usageSessionDuration;
    public final StateFlowImpl _educationTriggered;
    public final CoroutineScope backgroundScope;
    public final Clock clock;
    public final ContextualEducationInteractor contextualEducationInteractor;
    public final ReadonlyStateFlow educationTriggered;
    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$2 gestureModelMap;
    public final LauncherProxyService launcherProxyService;
    public final ContextualEducationMetricsLogger metricsLogger;
    public final Flow statsUpdateRequests;
    public final TutorialSchedulerRepository tutorialRepository;
    public final UserInputDeviceRepository userInputDeviceRepository;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* renamed from: access$getDurationForConfig-hgUFU34, reason: not valid java name */
        public static final long m2578access$getDurationForConfighgUFU34(Companion companion, String str, long j) {
            Duration.Companion companion2 = Duration.Companion;
            DurationUnit durationUnit = DurationUnit.SECONDS;
            return DurationKt.toDuration(SystemProperties.getLong(str, Duration.m3464toLongimpl(j, durationUnit)), durationUnit);
        }

        private Companion() {
        }
    }

    public final class StatsUpdateRequest {
        public final GestureType gestureType;
        public final boolean isTrackpadGesture;

        public StatsUpdateRequest(boolean z, GestureType gestureType) {
            this.isTrackpadGesture = z;
            this.gestureType = gestureType;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof StatsUpdateRequest)) {
                return false;
            }
            StatsUpdateRequest statsUpdateRequest = (StatsUpdateRequest) obj;
            return this.isTrackpadGesture == statsUpdateRequest.isTrackpadGesture && this.gestureType == statsUpdateRequest.gestureType;
        }

        public final int hashCode() {
            return this.gestureType.hashCode() + (Boolean.hashCode(this.isTrackpadGesture) * 31);
        }

        public final String toString() {
            return "StatsUpdateRequest(isTrackpadGesture=" + this.isTrackpadGesture + ", gestureType=" + this.gestureType + ")";
        }
    }

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[DeviceType.values().length];
            try {
                iArr[DeviceType.KEYBOARD.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DeviceType.TOUCHPAD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[GestureType.values().length];
            try {
                iArr2[GestureType.ALL_APPS.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    /* renamed from: com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$hasInitialDelayElapsed$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
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
            KeyboardTouchpadEduInteractor keyboardTouchpadEduInteractor = KeyboardTouchpadEduInteractor.this;
            int i = KeyboardTouchpadEduInteractor.$r8$clinit;
            return keyboardTouchpadEduInteractor.hasInitialDelayElapsed(null, this);
        }
    }

    /* renamed from: com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$isMinIntervalForToastEduElapsed$1, reason: invalid class name and case insensitive filesystem */
    final class C08651 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C08651(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            KeyboardTouchpadEduInteractor keyboardTouchpadEduInteractor = KeyboardTouchpadEduInteractor.this;
            int i = KeyboardTouchpadEduInteractor.$r8$clinit;
            return keyboardTouchpadEduInteractor.isMinIntervalForToastEduElapsed(null, this);
        }
    }

    /* renamed from: com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$isTargetDeviceConnected$1, reason: invalid class name and case insensitive filesystem */
    final class C08661 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public C08661(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            KeyboardTouchpadEduInteractor keyboardTouchpadEduInteractor = KeyboardTouchpadEduInteractor.this;
            int i = KeyboardTouchpadEduInteractor.$r8$clinit;
            return keyboardTouchpadEduInteractor.isTargetDeviceConnected(null, this);
        }
    }

    /* renamed from: com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$start$1, reason: invalid class name and case insensitive filesystem */
    final class C08671 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$start$1$2, reason: invalid class name */
        public final class AnonymousClass2 implements FlowCollector {
            public final /* synthetic */ KeyboardTouchpadEduInteractor this$0;

            public AnonymousClass2(KeyboardTouchpadEduInteractor keyboardTouchpadEduInteractor) {
                this.this$0 = keyboardTouchpadEduInteractor;
            }

            /* JADX WARN: Code restructure failed: missing block: B:28:0x0092, code lost:
            
                if (r0 == r6) goto L63;
             */
            /* JADX WARN: Code restructure failed: missing block: B:62:0x010b, code lost:
            
                if (r2 != r6) goto L64;
             */
            /* JADX WARN: Removed duplicated region for block: B:21:0x0074  */
            /* JADX WARN: Removed duplicated region for block: B:56:0x00dc  */
            /* JADX WARN: Removed duplicated region for block: B:57:0x00df  */
            /* JADX WARN: Removed duplicated region for block: B:61:0x0109  */
            /* JADX WARN: Removed duplicated region for block: B:7:0x001b  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object emit(GestureEduModel gestureEduModel, Continuation continuation) {
                KeyboardTouchpadEduInteractor$start$1$2$emit$1 keyboardTouchpadEduInteractor$start$1$2$emit$1;
                boolean z;
                EducationUiType educationUiType;
                Object objUpdateGestureEduModel;
                Instant instant;
                AnonymousClass2 anonymousClass2 = this;
                GestureEduModel gestureEduModel2 = gestureEduModel;
                int i = 4;
                int i2 = 2;
                if (continuation instanceof KeyboardTouchpadEduInteractor$start$1$2$emit$1) {
                    keyboardTouchpadEduInteractor$start$1$2$emit$1 = (KeyboardTouchpadEduInteractor$start$1$2$emit$1) continuation;
                    int i3 = keyboardTouchpadEduInteractor$start$1$2$emit$1.label;
                    if ((i3 & Integer.MIN_VALUE) != 0) {
                        keyboardTouchpadEduInteractor$start$1$2$emit$1.label = i3 - Integer.MIN_VALUE;
                    } else {
                        keyboardTouchpadEduInteractor$start$1$2$emit$1 = new KeyboardTouchpadEduInteractor$start$1$2$emit$1(anonymousClass2, continuation);
                    }
                }
                Object obj = keyboardTouchpadEduInteractor$start$1$2$emit$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i4 = keyboardTouchpadEduInteractor$start$1$2$emit$1.label;
                if (i4 == 0) {
                    ResultKt.throwOnFailure(obj);
                    int i5 = KeyboardTouchpadEduInteractor.$r8$clinit;
                    KeyboardTouchpadEduInteractor keyboardTouchpadEduInteractor = anonymousClass2.this$0;
                    keyboardTouchpadEduInteractor.getClass();
                    Instant instant2 = gestureEduModel2.usageSessionStartTime;
                    if (instant2 != null) {
                        Instant instantPlusSeconds = instant2.plusSeconds(Duration.m3464toLongimpl(KeyboardTouchpadEduInteractor.usageSessionDuration, DurationUnit.SECONDS));
                        boolean zIsBefore = instantPlusSeconds != null ? instantPlusSeconds.isBefore(keyboardTouchpadEduInteractor.clock.instant()) : false;
                        ContextualEducationInteractor contextualEducationInteractor = keyboardTouchpadEduInteractor.contextualEducationInteractor;
                        GestureType gestureType = gestureEduModel2.gestureType;
                        if (zIsBefore) {
                            keyboardTouchpadEduInteractor$start$1$2$emit$1.label = 1;
                            contextualEducationInteractor.getClass();
                            Object objUpdateGestureEduModel2 = ((UserContextualEducationRepository) contextualEducationInteractor.repository).updateGestureEduModel(gestureType, new ContextualEducationInteractor$$ExternalSyntheticLambda1(contextualEducationInteractor, 4), keyboardTouchpadEduInteractor$start$1$2$emit$1);
                            if (objUpdateGestureEduModel2 != coroutineSingletons) {
                                objUpdateGestureEduModel2 = Unit.INSTANCE;
                            }
                        } else {
                            int i6 = gestureEduModel2.educationShownCount;
                            boolean z2 = i6 < 2;
                            boolean z3 = gestureEduModel2.lastShortcutTriggeredTime == null;
                            boolean z4 = gestureEduModel2.signalCount >= 2;
                            if (i6 != 1 || (instant = gestureEduModel2.lastEducationTime) == null) {
                                z = z4;
                            } else {
                                z = z4;
                                Instant instantPlusSeconds2 = instant.plusSeconds(Duration.m3464toLongimpl(KeyboardTouchpadEduInteractor.minIntervalBetweenEdu, DurationUnit.SECONDS));
                                boolean zIsBefore2 = instantPlusSeconds2 != null ? instantPlusSeconds2.isBefore(keyboardTouchpadEduInteractor.clock.instant()) : true;
                                if (z2 && z3 && z && zIsBefore2) {
                                    educationUiType = i6 <= 0 ? EducationUiType.Notification : EducationUiType.Toast;
                                    keyboardTouchpadEduInteractor._educationTriggered.updateState(null, new EducationInfo(gestureType, educationUiType, gestureEduModel2.userId));
                                    keyboardTouchpadEduInteractor$start$1$2$emit$1.L$0 = anonymousClass2;
                                    keyboardTouchpadEduInteractor$start$1$2$emit$1.L$1 = gestureEduModel2;
                                    keyboardTouchpadEduInteractor$start$1$2$emit$1.L$2 = educationUiType;
                                    keyboardTouchpadEduInteractor$start$1$2$emit$1.label = 2;
                                    contextualEducationInteractor.getClass();
                                    objUpdateGestureEduModel = ((UserContextualEducationRepository) contextualEducationInteractor.repository).updateGestureEduModel(gestureType, new ContextualEducationInteractor$$ExternalSyntheticLambda1(contextualEducationInteractor, 2), keyboardTouchpadEduInteractor$start$1$2$emit$1);
                                    if (objUpdateGestureEduModel != coroutineSingletons) {
                                        objUpdateGestureEduModel = Unit.INSTANCE;
                                    }
                                }
                            }
                            if (z2) {
                                if (i6 <= 0) {
                                }
                                keyboardTouchpadEduInteractor._educationTriggered.updateState(null, new EducationInfo(gestureType, educationUiType, gestureEduModel2.userId));
                                keyboardTouchpadEduInteractor$start$1$2$emit$1.L$0 = anonymousClass2;
                                keyboardTouchpadEduInteractor$start$1$2$emit$1.L$1 = gestureEduModel2;
                                keyboardTouchpadEduInteractor$start$1$2$emit$1.L$2 = educationUiType;
                                keyboardTouchpadEduInteractor$start$1$2$emit$1.label = 2;
                                contextualEducationInteractor.getClass();
                                objUpdateGestureEduModel = ((UserContextualEducationRepository) contextualEducationInteractor.repository).updateGestureEduModel(gestureType, new ContextualEducationInteractor$$ExternalSyntheticLambda1(contextualEducationInteractor, 2), keyboardTouchpadEduInteractor$start$1$2$emit$1);
                                if (objUpdateGestureEduModel != coroutineSingletons) {
                                }
                            }
                        }
                        return coroutineSingletons;
                    }
                } else {
                    if (i4 == 1) {
                        ResultKt.throwOnFailure(obj);
                        return Unit.INSTANCE;
                    }
                    if (i4 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    EducationUiType educationUiType2 = (EducationUiType) keyboardTouchpadEduInteractor$start$1$2$emit$1.L$2;
                    gestureEduModel2 = (GestureEduModel) keyboardTouchpadEduInteractor$start$1$2$emit$1.L$1;
                    AnonymousClass2 anonymousClass22 = (AnonymousClass2) keyboardTouchpadEduInteractor$start$1$2$emit$1.L$0;
                    ResultKt.throwOnFailure(obj);
                    educationUiType = educationUiType2;
                    anonymousClass2 = anonymousClass22;
                    ContextualEducationMetricsLogger contextualEducationMetricsLogger = anonymousClass2.this$0.metricsLogger;
                    GestureType gestureType2 = gestureEduModel2.gestureType;
                    contextualEducationMetricsLogger.getClass();
                    int i7 = ContextualEducationMetricsLogger.WhenMappings.$EnumSwitchMapping$0[gestureType2.ordinal()];
                    if (i7 == 1) {
                        i = 1;
                    } else if (i7 == 2) {
                        i = 2;
                    } else if (i7 == 3) {
                        i = 3;
                    } else if (i7 != 4) {
                        throw new NoWhenBranchMatchedException();
                    }
                    int i8 = ContextualEducationMetricsLogger.WhenMappings.$EnumSwitchMapping$1[educationUiType.ordinal()];
                    if (i8 == 1) {
                        i2 = 1;
                    } else if (i8 != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    SysUiStatsLog.write(971, i2, i);
                }
                return Unit.INSTANCE;
            }
        }

        public C08671(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyboardTouchpadEduInteractor.this.new C08671(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08671) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                KeyboardTouchpadEduInteractor keyboardTouchpadEduInteractor = KeyboardTouchpadEduInteractor.this;
                ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest = FlowKt.transformLatest(keyboardTouchpadEduInteractor.contextualEducationInteractor.eduDeviceConnectionTimeFlow, new KeyboardTouchpadEduInteractor$start$1$invokeSuspend$$inlined$flatMapLatest$1(null, keyboardTouchpadEduInteractor));
                AnonymousClass2 anonymousClass2 = new AnonymousClass2(KeyboardTouchpadEduInteractor.this);
                this.label = 1;
                if (channelFlowTransformLatestTransformLatest.collect(anonymousClass2, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$start$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$start$2$1, reason: invalid class name */
        public final class AnonymousClass1 implements FlowCollector {
            public final /* synthetic */ KeyboardTouchpadEduInteractor this$0;

            public AnonymousClass1(KeyboardTouchpadEduInteractor keyboardTouchpadEduInteractor) {
                this.this$0 = keyboardTouchpadEduInteractor;
            }

            /* JADX WARN: Code restructure failed: missing block: B:27:0x0080, code lost:
            
                if (r5 == r1) goto L28;
             */
            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object emit(UserDeviceConnectionStatus userDeviceConnectionStatus, Continuation continuation) {
                KeyboardTouchpadEduInteractor$start$2$1$emit$1 keyboardTouchpadEduInteractor$start$2$1$emit$1;
                if (continuation instanceof KeyboardTouchpadEduInteractor$start$2$1$emit$1) {
                    keyboardTouchpadEduInteractor$start$2$1$emit$1 = (KeyboardTouchpadEduInteractor$start$2$1$emit$1) continuation;
                    int i = keyboardTouchpadEduInteractor$start$2$1$emit$1.label;
                    if ((i & Integer.MIN_VALUE) != 0) {
                        keyboardTouchpadEduInteractor$start$2$1$emit$1.label = i - Integer.MIN_VALUE;
                    } else {
                        keyboardTouchpadEduInteractor$start$2$1$emit$1 = new KeyboardTouchpadEduInteractor$start$2$1$emit$1(this, continuation);
                    }
                }
                Object objFirst = keyboardTouchpadEduInteractor$start$2$1$emit$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i2 = keyboardTouchpadEduInteractor$start$2$1$emit$1.label;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(objFirst);
                    if (userDeviceConnectionStatus.isConnected) {
                        ContextualEducationInteractor contextualEducationInteractor = this.this$0.contextualEducationInteractor;
                        keyboardTouchpadEduInteractor$start$2$1$emit$1.L$0 = this;
                        keyboardTouchpadEduInteractor$start$2$1$emit$1.label = 1;
                        UserContextualEducationRepository userContextualEducationRepository = (UserContextualEducationRepository) contextualEducationInteractor.repository;
                        objFirst = FlowKt.first(new UserContextualEducationRepository$readEduDeviceConnectionTime$$inlined$map$1(userContextualEducationRepository.prefData, userContextualEducationRepository), keyboardTouchpadEduInteractor$start$2$1$emit$1);
                        if (objFirst != coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    }
                    return Unit.INSTANCE;
                }
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(objFirst);
                    return Unit.INSTANCE;
                }
                this = (AnonymousClass1) keyboardTouchpadEduInteractor$start$2$1$emit$1.L$0;
                ResultKt.throwOnFailure(objFirst);
                if (((EduDeviceConnectionTime) objFirst).touchpadFirstConnectionTime == null) {
                    ContextualEducationInteractor contextualEducationInteractor2 = this.this$0.contextualEducationInteractor;
                    keyboardTouchpadEduInteractor$start$2$1$emit$1.L$0 = null;
                    keyboardTouchpadEduInteractor$start$2$1$emit$1.label = 2;
                    contextualEducationInteractor2.getClass();
                    Object objUpdateEduDeviceConnectionTime = ((UserContextualEducationRepository) contextualEducationInteractor2.repository).updateEduDeviceConnectionTime(new ContextualEducationInteractor$$ExternalSyntheticLambda1(contextualEducationInteractor2, 0), keyboardTouchpadEduInteractor$start$2$1$emit$1);
                    if (objUpdateEduDeviceConnectionTime != coroutineSingletons) {
                        objUpdateEduDeviceConnectionTime = Unit.INSTANCE;
                    }
                }
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyboardTouchpadEduInteractor.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                KeyboardTouchpadEduInteractor keyboardTouchpadEduInteractor = KeyboardTouchpadEduInteractor.this;
                Flow flow = keyboardTouchpadEduInteractor.userInputDeviceRepository.isAnyTouchpadConnectedForUser;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(keyboardTouchpadEduInteractor);
                this.label = 1;
                if (flow.collect(anonymousClass1, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$start$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$start$3$1, reason: invalid class name */
        public final class AnonymousClass1 implements FlowCollector {
            public final /* synthetic */ KeyboardTouchpadEduInteractor this$0;

            public AnonymousClass1(KeyboardTouchpadEduInteractor keyboardTouchpadEduInteractor) {
                this.this$0 = keyboardTouchpadEduInteractor;
            }

            /* JADX WARN: Code restructure failed: missing block: B:27:0x0080, code lost:
            
                if (r5 == r1) goto L28;
             */
            /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
            @Override // kotlinx.coroutines.flow.FlowCollector
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object emit(UserDeviceConnectionStatus userDeviceConnectionStatus, Continuation continuation) {
                KeyboardTouchpadEduInteractor$start$3$1$emit$1 keyboardTouchpadEduInteractor$start$3$1$emit$1;
                if (continuation instanceof KeyboardTouchpadEduInteractor$start$3$1$emit$1) {
                    keyboardTouchpadEduInteractor$start$3$1$emit$1 = (KeyboardTouchpadEduInteractor$start$3$1$emit$1) continuation;
                    int i = keyboardTouchpadEduInteractor$start$3$1$emit$1.label;
                    if ((i & Integer.MIN_VALUE) != 0) {
                        keyboardTouchpadEduInteractor$start$3$1$emit$1.label = i - Integer.MIN_VALUE;
                    } else {
                        keyboardTouchpadEduInteractor$start$3$1$emit$1 = new KeyboardTouchpadEduInteractor$start$3$1$emit$1(this, continuation);
                    }
                }
                Object objFirst = keyboardTouchpadEduInteractor$start$3$1$emit$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                int i2 = keyboardTouchpadEduInteractor$start$3$1$emit$1.label;
                if (i2 == 0) {
                    ResultKt.throwOnFailure(objFirst);
                    if (userDeviceConnectionStatus.isConnected) {
                        ContextualEducationInteractor contextualEducationInteractor = this.this$0.contextualEducationInteractor;
                        keyboardTouchpadEduInteractor$start$3$1$emit$1.L$0 = this;
                        keyboardTouchpadEduInteractor$start$3$1$emit$1.label = 1;
                        UserContextualEducationRepository userContextualEducationRepository = (UserContextualEducationRepository) contextualEducationInteractor.repository;
                        objFirst = FlowKt.first(new UserContextualEducationRepository$readEduDeviceConnectionTime$$inlined$map$1(userContextualEducationRepository.prefData, userContextualEducationRepository), keyboardTouchpadEduInteractor$start$3$1$emit$1);
                        if (objFirst != coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    }
                    return Unit.INSTANCE;
                }
                if (i2 != 1) {
                    if (i2 != 2) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    ResultKt.throwOnFailure(objFirst);
                    return Unit.INSTANCE;
                }
                this = (AnonymousClass1) keyboardTouchpadEduInteractor$start$3$1$emit$1.L$0;
                ResultKt.throwOnFailure(objFirst);
                if (((EduDeviceConnectionTime) objFirst).keyboardFirstConnectionTime == null) {
                    ContextualEducationInteractor contextualEducationInteractor2 = this.this$0.contextualEducationInteractor;
                    keyboardTouchpadEduInteractor$start$3$1$emit$1.L$0 = null;
                    keyboardTouchpadEduInteractor$start$3$1$emit$1.label = 2;
                    contextualEducationInteractor2.getClass();
                    Object objUpdateEduDeviceConnectionTime = ((UserContextualEducationRepository) contextualEducationInteractor2.repository).updateEduDeviceConnectionTime(new ContextualEducationInteractor$$ExternalSyntheticLambda1(contextualEducationInteractor2, 1), keyboardTouchpadEduInteractor$start$3$1$emit$1);
                    if (objUpdateEduDeviceConnectionTime != coroutineSingletons) {
                        objUpdateEduDeviceConnectionTime = Unit.INSTANCE;
                    }
                }
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyboardTouchpadEduInteractor.this.new AnonymousClass3(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                KeyboardTouchpadEduInteractor keyboardTouchpadEduInteractor = KeyboardTouchpadEduInteractor.this;
                Flow flow = keyboardTouchpadEduInteractor.userInputDeviceRepository.isAnyKeyboardConnectedForUser;
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(keyboardTouchpadEduInteractor);
                this.label = 1;
                if (flow.collect(anonymousClass1, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$start$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass4(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyboardTouchpadEduInteractor.this.new AnonymousClass4(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final KeyboardTouchpadEduInteractor keyboardTouchpadEduInteractor = KeyboardTouchpadEduInteractor.this;
                Flow flow = keyboardTouchpadEduInteractor.contextualEducationInteractor.keyboardShortcutTriggered;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor.start.4.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        ContextualEducationInteractor contextualEducationInteractor = keyboardTouchpadEduInteractor.contextualEducationInteractor;
                        contextualEducationInteractor.getClass();
                        Object objUpdateGestureEduModel = ((UserContextualEducationRepository) contextualEducationInteractor.repository).updateGestureEduModel((GestureType) obj2, new ContextualEducationInteractor$$ExternalSyntheticLambda1(contextualEducationInteractor, 3), continuation);
                        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                        if (objUpdateGestureEduModel != coroutineSingletons2) {
                            objUpdateGestureEduModel = Unit.INSTANCE;
                        }
                        return objUpdateGestureEduModel == coroutineSingletons2 ? objUpdateGestureEduModel : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flow.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor$start$5, reason: invalid class name */
    final class AnonymousClass5 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass5(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return KeyboardTouchpadEduInteractor.this.new AnonymousClass5(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass5) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final KeyboardTouchpadEduInteractor keyboardTouchpadEduInteractor = KeyboardTouchpadEduInteractor.this;
                Flow flow = keyboardTouchpadEduInteractor.statsUpdateRequests;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.education.domain.interactor.KeyboardTouchpadEduInteractor.start.5.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        StatsUpdateRequest statsUpdateRequest = (StatsUpdateRequest) obj2;
                        boolean z = statsUpdateRequest.isTrackpadGesture;
                        KeyboardTouchpadEduInteractor keyboardTouchpadEduInteractor2 = keyboardTouchpadEduInteractor;
                        GestureType gestureType = statsUpdateRequest.gestureType;
                        if (!z) {
                            Object objAccess$incrementSignalCount = KeyboardTouchpadEduInteractor.access$incrementSignalCount(keyboardTouchpadEduInteractor2, gestureType, continuation);
                            return objAccess$incrementSignalCount == CoroutineSingletons.COROUTINE_SUSPENDED ? objAccess$incrementSignalCount : Unit.INSTANCE;
                        }
                        ContextualEducationInteractor contextualEducationInteractor = keyboardTouchpadEduInteractor2.contextualEducationInteractor;
                        contextualEducationInteractor.getClass();
                        Object objUpdateGestureEduModel = ((UserContextualEducationRepository) contextualEducationInteractor.repository).updateGestureEduModel(gestureType, new ContextualEducationInteractor$$ExternalSyntheticLambda1(contextualEducationInteractor, 3), continuation);
                        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                        if (objUpdateGestureEduModel != coroutineSingletons2) {
                            objUpdateGestureEduModel = Unit.INSTANCE;
                        }
                        return objUpdateGestureEduModel == coroutineSingletons2 ? objUpdateGestureEduModel : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flow.collect(flowCollector, this) == coroutineSingletons) {
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

    static {
        Companion companion = new Companion(null);
        Duration.Companion companion2 = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.DAYS;
        usageSessionDuration = Companion.m2578access$getDurationForConfighgUFU34(companion, "persist.contextual_edu.usage_session_sec", DurationKt.toDuration(3, durationUnit));
        minIntervalBetweenEdu = Companion.m2578access$getDurationForConfighgUFU34(companion, "persist.contextual_edu.edu_interval_sec", DurationKt.toDuration(7, durationUnit));
        initialDelayDuration = Companion.m2578access$getDurationForConfighgUFU34(companion, "persist.contextual_edu.initial_delay_sec", DurationKt.toDuration(7, durationUnit));
    }

    public KeyboardTouchpadEduInteractor(CoroutineScope coroutineScope, ContextualEducationInteractor contextualEducationInteractor, UserInputDeviceRepository userInputDeviceRepository, TutorialSchedulerRepository tutorialSchedulerRepository, LauncherProxyService launcherProxyService, ContextualEducationMetricsLogger contextualEducationMetricsLogger, Clock clock) {
        this.backgroundScope = coroutineScope;
        this.contextualEducationInteractor = contextualEducationInteractor;
        this.userInputDeviceRepository = userInputDeviceRepository;
        this.tutorialRepository = tutorialSchedulerRepository;
        this.launcherProxyService = launcherProxyService;
        this.metricsLogger = contextualEducationMetricsLogger;
        this.clock = clock;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this._educationTriggered = stateFlowImplMutableStateFlow;
        this.educationTriggered = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        this.statsUpdateRequests = FlowConflatedKt.conflatedCallbackFlow(new KeyboardTouchpadEduInteractor$statsUpdateRequests$1(this, null));
        this.gestureModelMap = FlowKt.combine(contextualEducationInteractor.backGestureModelFlow, contextualEducationInteractor.homeGestureModelFlow, contextualEducationInteractor.overviewGestureModelFlow, contextualEducationInteractor.allAppsGestureModelFlow, new KeyboardTouchpadEduInteractor$gestureModelMap$1(null));
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:0x00e0, code lost:
    
        if (r9 == r1) goto L45;
     */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$incrementSignalCount(KeyboardTouchpadEduInteractor keyboardTouchpadEduInteractor, GestureType gestureType, Continuation continuation) {
        KeyboardTouchpadEduInteractor$incrementSignalCount$1 keyboardTouchpadEduInteractor$incrementSignalCount$1;
        DeviceType deviceType;
        Object objIsTargetDeviceConnected;
        KeyboardTouchpadEduInteractor keyboardTouchpadEduInteractor2;
        GestureType gestureType2;
        keyboardTouchpadEduInteractor.getClass();
        if (continuation instanceof KeyboardTouchpadEduInteractor$incrementSignalCount$1) {
            keyboardTouchpadEduInteractor$incrementSignalCount$1 = (KeyboardTouchpadEduInteractor$incrementSignalCount$1) continuation;
            int i = keyboardTouchpadEduInteractor$incrementSignalCount$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                keyboardTouchpadEduInteractor$incrementSignalCount$1.label = i - Integer.MIN_VALUE;
            } else {
                keyboardTouchpadEduInteractor$incrementSignalCount$1 = new KeyboardTouchpadEduInteractor$incrementSignalCount$1(keyboardTouchpadEduInteractor, continuation);
            }
        }
        Object objHasInitialDelayElapsed = keyboardTouchpadEduInteractor$incrementSignalCount$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = keyboardTouchpadEduInteractor$incrementSignalCount$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objHasInitialDelayElapsed);
            deviceType = WhenMappings.$EnumSwitchMapping$1[gestureType.ordinal()] == 1 ? DeviceType.KEYBOARD : DeviceType.TOUCHPAD;
            keyboardTouchpadEduInteractor$incrementSignalCount$1.L$0 = keyboardTouchpadEduInteractor;
            keyboardTouchpadEduInteractor$incrementSignalCount$1.L$1 = gestureType;
            keyboardTouchpadEduInteractor$incrementSignalCount$1.L$2 = deviceType;
            keyboardTouchpadEduInteractor$incrementSignalCount$1.label = 1;
            objIsTargetDeviceConnected = keyboardTouchpadEduInteractor.isTargetDeviceConnected(deviceType, keyboardTouchpadEduInteractor$incrementSignalCount$1);
            if (objIsTargetDeviceConnected != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 == 2) {
                gestureType2 = (GestureType) keyboardTouchpadEduInteractor$incrementSignalCount$1.L$1;
                keyboardTouchpadEduInteractor2 = (KeyboardTouchpadEduInteractor) keyboardTouchpadEduInteractor$incrementSignalCount$1.L$0;
                ResultKt.throwOnFailure(objHasInitialDelayElapsed);
                if (((Boolean) objHasInitialDelayElapsed).booleanValue()) {
                    keyboardTouchpadEduInteractor$incrementSignalCount$1.L$0 = keyboardTouchpadEduInteractor2;
                    keyboardTouchpadEduInteractor$incrementSignalCount$1.L$1 = gestureType2;
                    keyboardTouchpadEduInteractor$incrementSignalCount$1.label = 3;
                    objHasInitialDelayElapsed = keyboardTouchpadEduInteractor2.isMinIntervalForToastEduElapsed(gestureType2, keyboardTouchpadEduInteractor$incrementSignalCount$1);
                    if (objHasInitialDelayElapsed != coroutineSingletons) {
                        if (((Boolean) objHasInitialDelayElapsed).booleanValue()) {
                        }
                    }
                    return coroutineSingletons;
                }
                return Unit.INSTANCE;
            }
            if (i2 != 3) {
                if (i2 != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(objHasInitialDelayElapsed);
                return Unit.INSTANCE;
            }
            gestureType2 = (GestureType) keyboardTouchpadEduInteractor$incrementSignalCount$1.L$1;
            keyboardTouchpadEduInteractor2 = (KeyboardTouchpadEduInteractor) keyboardTouchpadEduInteractor$incrementSignalCount$1.L$0;
            ResultKt.throwOnFailure(objHasInitialDelayElapsed);
            if (((Boolean) objHasInitialDelayElapsed).booleanValue()) {
                ContextualEducationInteractor contextualEducationInteractor = keyboardTouchpadEduInteractor2.contextualEducationInteractor;
                keyboardTouchpadEduInteractor$incrementSignalCount$1.L$0 = null;
                keyboardTouchpadEduInteractor$incrementSignalCount$1.L$1 = null;
                keyboardTouchpadEduInteractor$incrementSignalCount$1.label = 4;
                contextualEducationInteractor.getClass();
                Object objUpdateGestureEduModel = ((UserContextualEducationRepository) contextualEducationInteractor.repository).updateGestureEduModel(gestureType2, new ContextualEducationInteractor$$ExternalSyntheticLambda1(contextualEducationInteractor, 5), keyboardTouchpadEduInteractor$incrementSignalCount$1);
                if (objUpdateGestureEduModel != coroutineSingletons) {
                    objUpdateGestureEduModel = Unit.INSTANCE;
                }
            }
            return Unit.INSTANCE;
        }
        DeviceType deviceType2 = (DeviceType) keyboardTouchpadEduInteractor$incrementSignalCount$1.L$2;
        gestureType = (GestureType) keyboardTouchpadEduInteractor$incrementSignalCount$1.L$1;
        KeyboardTouchpadEduInteractor keyboardTouchpadEduInteractor3 = (KeyboardTouchpadEduInteractor) keyboardTouchpadEduInteractor$incrementSignalCount$1.L$0;
        ResultKt.throwOnFailure(objHasInitialDelayElapsed);
        deviceType = deviceType2;
        keyboardTouchpadEduInteractor = keyboardTouchpadEduInteractor3;
        objIsTargetDeviceConnected = objHasInitialDelayElapsed;
        if (((Boolean) objIsTargetDeviceConnected).booleanValue()) {
            keyboardTouchpadEduInteractor$incrementSignalCount$1.L$0 = keyboardTouchpadEduInteractor;
            keyboardTouchpadEduInteractor$incrementSignalCount$1.L$1 = gestureType;
            keyboardTouchpadEduInteractor$incrementSignalCount$1.L$2 = null;
            keyboardTouchpadEduInteractor$incrementSignalCount$1.label = 2;
            objHasInitialDelayElapsed = keyboardTouchpadEduInteractor.hasInitialDelayElapsed(deviceType, keyboardTouchpadEduInteractor$incrementSignalCount$1);
            if (objHasInitialDelayElapsed != coroutineSingletons) {
                GestureType gestureType3 = gestureType;
                keyboardTouchpadEduInteractor2 = keyboardTouchpadEduInteractor;
                gestureType2 = gestureType3;
                if (((Boolean) objHasInitialDelayElapsed).booleanValue()) {
                }
            }
            return coroutineSingletons;
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0066, code lost:
    
        if (r7 == r1) goto L23;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object hasInitialDelayElapsed(DeviceType deviceType, ContinuationImpl continuationImpl) {
        AnonymousClass1 anonymousClass1;
        Instant instant;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object scheduledTutorialLaunchTime = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(scheduledTutorialLaunchTime);
            anonymousClass1.L$0 = this;
            anonymousClass1.L$1 = deviceType;
            anonymousClass1.label = 1;
            scheduledTutorialLaunchTime = this.tutorialRepository.getScheduledTutorialLaunchTime(deviceType, anonymousClass1);
            if (scheduledTutorialLaunchTime != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            this = (KeyboardTouchpadEduInteractor) anonymousClass1.L$0;
            ResultKt.throwOnFailure(scheduledTutorialLaunchTime);
            instant = (Instant) scheduledTutorialLaunchTime;
            if (instant == null) {
                return Boolean.FALSE;
            }
            return Boolean.valueOf(this.clock.instant().isAfter(instant.plusSeconds(Duration.m3464toLongimpl(initialDelayDuration, DurationUnit.SECONDS))));
        }
        deviceType = (DeviceType) anonymousClass1.L$1;
        this = (KeyboardTouchpadEduInteractor) anonymousClass1.L$0;
        ResultKt.throwOnFailure(scheduledTutorialLaunchTime);
        instant = (Instant) scheduledTutorialLaunchTime;
        if (instant == null) {
            TutorialSchedulerRepository tutorialSchedulerRepository = this.tutorialRepository;
            anonymousClass1.L$0 = this;
            anonymousClass1.L$1 = null;
            anonymousClass1.label = 2;
            scheduledTutorialLaunchTime = tutorialSchedulerRepository.getNotifiedTime(deviceType, anonymousClass1);
        }
        return Boolean.valueOf(this.clock.instant().isAfter(instant.plusSeconds(Duration.m3464toLongimpl(initialDelayDuration, DurationUnit.SECONDS))));
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object isMinIntervalForToastEduElapsed(GestureType gestureType, ContinuationImpl continuationImpl) {
        C08651 c08651;
        long j;
        if (continuationImpl instanceof C08651) {
            c08651 = (C08651) continuationImpl;
            int i = c08651.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08651.label = i - Integer.MIN_VALUE;
            } else {
                c08651 = new C08651(continuationImpl);
            }
        }
        Object objFirst = c08651.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08651.label;
        boolean zIsAfter = true;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objFirst);
            c08651.L$0 = this;
            c08651.L$1 = gestureType;
            c08651.label = 1;
            objFirst = FlowKt.first(this.gestureModelMap, c08651);
            if (objFirst == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            gestureType = (GestureType) c08651.L$1;
            this = (KeyboardTouchpadEduInteractor) c08651.L$0;
            ResultKt.throwOnFailure(objFirst);
        }
        Map map = (Map) objFirst;
        GestureEduModel gestureEduModel = (GestureEduModel) map.get(gestureType);
        if (gestureEduModel == null || gestureEduModel.educationShownCount != 0) {
            return Boolean.TRUE;
        }
        Collection collectionValues = map.values();
        ArrayList arrayList = new ArrayList();
        for (Object obj : collectionValues) {
            if (((GestureEduModel) obj).educationShownCount == 1) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i3 = 0;
        int i4 = 0;
        while (i4 < size) {
            Object obj2 = arrayList.get(i4);
            i4++;
            Instant instant = ((GestureEduModel) obj2).lastEducationTime;
            if (instant != null) {
                arrayList2.add(instant);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        int size2 = arrayList2.size();
        while (true) {
            j = usageSessionDuration;
            if (i3 >= size2) {
                break;
            }
            Object obj3 = arrayList2.get(i3);
            i3++;
            if (((Instant) obj3).compareTo(this.clock.instant().minusSeconds(Duration.m3464toLongimpl(j, DurationUnit.SECONDS))) >= 0) {
                arrayList3.add(obj3);
            }
        }
        if (arrayList3.size() >= 2) {
            Instant instant2 = (Instant) CollectionsKt___CollectionsKt.maxOrNull((Iterable) arrayList3);
            zIsAfter = this.clock.instant().isAfter(instant2 != null ? instant2.plusSeconds(Duration.m3464toLongimpl(j, DurationUnit.SECONDS)) : null);
        }
        return Boolean.valueOf(zIsAfter);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x004f, code lost:
    
        if (r7 == r1) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0065, code lost:
    
        if (r7 == r1) goto L27;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object isTargetDeviceConnected(DeviceType deviceType, ContinuationImpl continuationImpl) {
        C08661 c08661;
        boolean z;
        if (continuationImpl instanceof C08661) {
            c08661 = (C08661) continuationImpl;
            int i = c08661.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c08661.label = i - Integer.MIN_VALUE;
            } else {
                c08661 = new C08661(continuationImpl);
            }
        }
        Object objFirst = c08661.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c08661.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objFirst);
            int i3 = WhenMappings.$EnumSwitchMapping$0[deviceType.ordinal()];
            UserInputDeviceRepository userInputDeviceRepository = this.userInputDeviceRepository;
            if (i3 == 1) {
                Flow flow = userInputDeviceRepository.isAnyKeyboardConnectedForUser;
                c08661.label = 1;
                objFirst = FlowKt.first(flow, c08661);
            } else {
                if (i3 != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                Flow flow2 = userInputDeviceRepository.isAnyTouchpadConnectedForUser;
                c08661.label = 2;
                objFirst = FlowKt.first(flow2, c08661);
            }
            return coroutineSingletons;
        }
        if (i2 == 1) {
            ResultKt.throwOnFailure(objFirst);
            z = ((UserDeviceConnectionStatus) objFirst).isConnected;
        } else {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objFirst);
            z = ((UserDeviceConnectionStatus) objFirst).isConnected;
        }
        return Boolean.valueOf(z);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        C08671 c08671 = new C08671(null);
        CoroutineScope coroutineScope = this.backgroundScope;
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, c08671, 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass2(null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass3(null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass4(null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new AnonymousClass5(null), 7);
    }
}
