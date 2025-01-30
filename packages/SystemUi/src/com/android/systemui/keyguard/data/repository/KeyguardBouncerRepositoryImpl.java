package com.android.systemui.keyguard.data.repository;

import android.os.Build;
import com.android.systemui.keyguard.shared.model.BouncerShowMessageModel;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.util.time.SystemClock;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardBouncerRepositoryImpl implements KeyguardBouncerRepository {
    public final StateFlowImpl _alternateBouncerUIAvailable;
    public final StateFlowImpl _alternateBouncerVisible;
    public final StateFlowImpl _isBackButtonEnabled;
    public final StateFlowImpl _keyguardAuthenticated;
    public final StateFlowImpl _keyguardPosition;
    public final StateFlowImpl _panelExpansionAmount;
    public final StateFlowImpl _primaryBouncerDisappearAnimation;
    public final StateFlowImpl _primaryBouncerInflate;
    public final StateFlowImpl _primaryBouncerScrimmed;
    public final StateFlowImpl _primaryBouncerShow;
    public final StateFlowImpl _primaryBouncerShowingSoon;
    public final StateFlowImpl _primaryBouncerStartingToHide;
    public final StateFlowImpl _primaryBouncerUpdating;
    public final StateFlowImpl _resourceUpdateRequests;
    public final StateFlowImpl _showMessage;
    public final StateFlowImpl _sideFpsShowing;
    public final ReadonlyStateFlow alternateBouncerUIAvailable;
    public final ReadonlyStateFlow alternateBouncerVisible;
    public final SystemClock clock;
    public final ReadonlyStateFlow isBackButtonEnabled;
    public final ReadonlyStateFlow keyguardAuthenticated;
    public final ReadonlyStateFlow keyguardPosition;
    public long lastAlternateBouncerVisibleTime;
    public final ReadonlyStateFlow panelExpansionAmount;
    public final ReadonlyStateFlow primaryBouncerInflate;
    public final ReadonlyStateFlow primaryBouncerScrimmed;
    public final ReadonlyStateFlow primaryBouncerShow;
    public final ReadonlyStateFlow primaryBouncerShowingSoon;
    public final ReadonlyStateFlow primaryBouncerStartingDisappearAnimation;
    public final ReadonlyStateFlow primaryBouncerStartingToHide;
    public final ReadonlyStateFlow primaryBouncerUpdating;
    public final ReadonlyStateFlow resourceUpdateRequests;
    public final ReadonlyStateFlow showMessage;
    public final ReadonlyStateFlow sideFpsShowing;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardBouncerRepositoryImpl(SystemClock systemClock, CoroutineScope coroutineScope, TableLogBuffer tableLogBuffer) {
        this.clock = systemClock;
        Boolean bool = Boolean.FALSE;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._primaryBouncerShow = MutableStateFlow;
        ReadonlyStateFlow asStateFlow = FlowKt.asStateFlow(MutableStateFlow);
        this.primaryBouncerShow = asStateFlow;
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(bool);
        this._primaryBouncerShowingSoon = MutableStateFlow2;
        ReadonlyStateFlow asStateFlow2 = FlowKt.asStateFlow(MutableStateFlow2);
        this.primaryBouncerShowingSoon = asStateFlow2;
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(bool);
        this._primaryBouncerStartingToHide = MutableStateFlow3;
        ReadonlyStateFlow asStateFlow3 = FlowKt.asStateFlow(MutableStateFlow3);
        this.primaryBouncerStartingToHide = asStateFlow3;
        StateFlowImpl MutableStateFlow4 = StateFlowKt.MutableStateFlow(null);
        this._primaryBouncerDisappearAnimation = MutableStateFlow4;
        final ReadonlyStateFlow asStateFlow4 = FlowKt.asStateFlow(MutableStateFlow4);
        this.primaryBouncerStartingDisappearAnimation = asStateFlow4;
        StateFlowImpl MutableStateFlow5 = StateFlowKt.MutableStateFlow(bool);
        this._primaryBouncerScrimmed = MutableStateFlow5;
        ReadonlyStateFlow asStateFlow5 = FlowKt.asStateFlow(MutableStateFlow5);
        this.primaryBouncerScrimmed = asStateFlow5;
        StateFlowImpl MutableStateFlow6 = StateFlowKt.MutableStateFlow(Float.valueOf(1.0f));
        this._panelExpansionAmount = MutableStateFlow6;
        final ReadonlyStateFlow asStateFlow6 = FlowKt.asStateFlow(MutableStateFlow6);
        this.panelExpansionAmount = asStateFlow6;
        StateFlowImpl MutableStateFlow7 = StateFlowKt.MutableStateFlow(Float.valueOf(0.0f));
        this._keyguardPosition = MutableStateFlow7;
        final ReadonlyStateFlow asStateFlow7 = FlowKt.asStateFlow(MutableStateFlow7);
        this.keyguardPosition = asStateFlow7;
        StateFlowImpl MutableStateFlow8 = StateFlowKt.MutableStateFlow(null);
        this._isBackButtonEnabled = MutableStateFlow8;
        ReadonlyStateFlow asStateFlow8 = FlowKt.asStateFlow(MutableStateFlow8);
        this.isBackButtonEnabled = asStateFlow8;
        StateFlowImpl MutableStateFlow9 = StateFlowKt.MutableStateFlow(null);
        this._keyguardAuthenticated = MutableStateFlow9;
        this.keyguardAuthenticated = FlowKt.asStateFlow(MutableStateFlow9);
        StateFlowImpl MutableStateFlow10 = StateFlowKt.MutableStateFlow(null);
        this._showMessage = MutableStateFlow10;
        final ReadonlyStateFlow asStateFlow9 = FlowKt.asStateFlow(MutableStateFlow10);
        this.showMessage = asStateFlow9;
        StateFlowImpl MutableStateFlow11 = StateFlowKt.MutableStateFlow(bool);
        this._resourceUpdateRequests = MutableStateFlow11;
        ReadonlyStateFlow asStateFlow10 = FlowKt.asStateFlow(MutableStateFlow11);
        this.resourceUpdateRequests = asStateFlow10;
        StateFlowImpl MutableStateFlow12 = StateFlowKt.MutableStateFlow(bool);
        this._alternateBouncerVisible = MutableStateFlow12;
        this.alternateBouncerVisible = FlowKt.asStateFlow(MutableStateFlow12);
        this.lastAlternateBouncerVisibleTime = -1L;
        StateFlowImpl MutableStateFlow13 = StateFlowKt.MutableStateFlow(bool);
        this._alternateBouncerUIAvailable = MutableStateFlow13;
        ReadonlyStateFlow asStateFlow11 = FlowKt.asStateFlow(MutableStateFlow13);
        this.alternateBouncerUIAvailable = asStateFlow11;
        StateFlowImpl MutableStateFlow14 = StateFlowKt.MutableStateFlow(bool);
        this._sideFpsShowing = MutableStateFlow14;
        ReadonlyStateFlow asStateFlow12 = FlowKt.asStateFlow(MutableStateFlow14);
        this.sideFpsShowing = asStateFlow12;
        StateFlowImpl MutableStateFlow15 = StateFlowKt.MutableStateFlow(bool);
        this._primaryBouncerInflate = MutableStateFlow15;
        this.primaryBouncerInflate = FlowKt.asStateFlow(MutableStateFlow15);
        StateFlowImpl MutableStateFlow16 = StateFlowKt.MutableStateFlow(bool);
        this._primaryBouncerUpdating = MutableStateFlow16;
        this.primaryBouncerUpdating = FlowKt.asStateFlow(MutableStateFlow16);
        if (Build.IS_DEBUGGABLE) {
            FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(DiffableKt.logDiffsForTable((Flow) asStateFlow, tableLogBuffer, "", "PrimaryBouncerShow", false), new KeyguardBouncerRepositoryImpl$setUpLogging$1(null)), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow) asStateFlow2, tableLogBuffer, "", "PrimaryBouncerShowingSoon", false), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow) asStateFlow3, tableLogBuffer, "", "PrimaryBouncerStartingToHide", false), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable(new Flow() { // from class: com.android.systemui.keyguard.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$1

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$1$2 */
                public final class C16042 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                    @DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$1$2", m277f = "KeyguardBouncerRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                    /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$1$2$1, reason: invalid class name */
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
                            this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                            return C16042.this.emit(null, this);
                        }
                    }

                    public C16042(FlowCollector flowCollector) {
                        this.$this_unsafeFlow = flowCollector;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Object obj, Continuation continuation) {
                        AnonymousClass1 anonymousClass1;
                        int i;
                        if (continuation instanceof AnonymousClass1) {
                            anonymousClass1 = (AnonymousClass1) continuation;
                            int i2 = anonymousClass1.label;
                            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                                anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                                Object obj2 = anonymousClass1.result;
                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                i = anonymousClass1.label;
                                if (i != 0) {
                                    ResultKt.throwOnFailure(obj2);
                                    Boolean valueOf = Boolean.valueOf(((Runnable) obj) != null);
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(valueOf, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                } else {
                                    if (i != 1) {
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj2);
                                }
                                return Unit.INSTANCE;
                            }
                        }
                        anonymousClass1 = new AnonymousClass1(continuation);
                        Object obj22 = anonymousClass1.result;
                        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                        i = anonymousClass1.label;
                        if (i != 0) {
                        }
                        return Unit.INSTANCE;
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new C16042(flowCollector), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            }, tableLogBuffer, "", "PrimaryBouncerStartingDisappearAnimation", false), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow) asStateFlow5, tableLogBuffer, "", "PrimaryBouncerScrimmed", false), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable(new Flow() { // from class: com.android.systemui.keyguard.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$2

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$2$2 */
                public final class C16052 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                    @DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$2$2", m277f = "KeyguardBouncerRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                    /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$2$2$1, reason: invalid class name */
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
                            this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                            return C16052.this.emit(null, this);
                        }
                    }

                    public C16052(FlowCollector flowCollector) {
                        this.$this_unsafeFlow = flowCollector;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Object obj, Continuation continuation) {
                        AnonymousClass1 anonymousClass1;
                        int i;
                        if (continuation instanceof AnonymousClass1) {
                            anonymousClass1 = (AnonymousClass1) continuation;
                            int i2 = anonymousClass1.label;
                            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                                anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                                Object obj2 = anonymousClass1.result;
                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                i = anonymousClass1.label;
                                if (i != 0) {
                                    ResultKt.throwOnFailure(obj2);
                                    Integer num = new Integer((int) (((Number) obj).floatValue() * 1000));
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                } else {
                                    if (i != 1) {
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj2);
                                }
                                return Unit.INSTANCE;
                            }
                        }
                        anonymousClass1 = new AnonymousClass1(continuation);
                        Object obj22 = anonymousClass1.result;
                        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                        i = anonymousClass1.label;
                        if (i != 0) {
                        }
                        return Unit.INSTANCE;
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new C16052(flowCollector), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            }, tableLogBuffer, "", "PanelExpansionAmountMillis", -1), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable(new Flow() { // from class: com.android.systemui.keyguard.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$3

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$3$2 */
                public final class C16062 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                    @DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$3$2", m277f = "KeyguardBouncerRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                    /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$3$2$1, reason: invalid class name */
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
                            this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                            return C16062.this.emit(null, this);
                        }
                    }

                    public C16062(FlowCollector flowCollector) {
                        this.$this_unsafeFlow = flowCollector;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Object obj, Continuation continuation) {
                        AnonymousClass1 anonymousClass1;
                        int i;
                        if (continuation instanceof AnonymousClass1) {
                            anonymousClass1 = (AnonymousClass1) continuation;
                            int i2 = anonymousClass1.label;
                            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                                anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                                Object obj2 = anonymousClass1.result;
                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                i = anonymousClass1.label;
                                if (i != 0) {
                                    ResultKt.throwOnFailure(obj2);
                                    Integer num = new Integer((int) ((Number) obj).floatValue());
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                } else {
                                    if (i != 1) {
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj2);
                                }
                                return Unit.INSTANCE;
                            }
                        }
                        anonymousClass1 = new AnonymousClass1(continuation);
                        Object obj22 = anonymousClass1.result;
                        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                        i = anonymousClass1.label;
                        if (i != 0) {
                        }
                        return Unit.INSTANCE;
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new C16062(flowCollector), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            }, tableLogBuffer, "", "KeyguardPosition", -1), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow) new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(asStateFlow8), tableLogBuffer, "", "IsBackButtonEnabled", false), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable(new Flow() { // from class: com.android.systemui.keyguard.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$4

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$4$2 */
                public final class C16072 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                    @DebugMetadata(m276c = "com.android.systemui.keyguard.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$4$2", m277f = "KeyguardBouncerRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
                    /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$4$2$1, reason: invalid class name */
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
                            this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                            return C16072.this.emit(null, this);
                        }
                    }

                    public C16072(FlowCollector flowCollector) {
                        this.$this_unsafeFlow = flowCollector;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Object obj, Continuation continuation) {
                        AnonymousClass1 anonymousClass1;
                        int i;
                        if (continuation instanceof AnonymousClass1) {
                            anonymousClass1 = (AnonymousClass1) continuation;
                            int i2 = anonymousClass1.label;
                            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                                anonymousClass1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                                Object obj2 = anonymousClass1.result;
                                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                                i = anonymousClass1.label;
                                if (i != 0) {
                                    ResultKt.throwOnFailure(obj2);
                                    BouncerShowMessageModel bouncerShowMessageModel = (BouncerShowMessageModel) obj;
                                    String str = bouncerShowMessageModel != null ? bouncerShowMessageModel.message : null;
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(str, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                } else {
                                    if (i != 1) {
                                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                    }
                                    ResultKt.throwOnFailure(obj2);
                                }
                                return Unit.INSTANCE;
                            }
                        }
                        anonymousClass1 = new AnonymousClass1(continuation);
                        Object obj22 = anonymousClass1.result;
                        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
                        i = anonymousClass1.label;
                        if (i != 0) {
                        }
                        return Unit.INSTANCE;
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new C16072(flowCollector), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            }, tableLogBuffer, "ShowMessage", (String) null), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow) asStateFlow10, tableLogBuffer, "", "ResourceUpdateRequests", false), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow) asStateFlow11, tableLogBuffer, "", "IsAlternateBouncerUIAvailable", false), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow) asStateFlow12, tableLogBuffer, "", "isSideFpsShowing", false), coroutineScope);
        }
    }

    public final void setAlternateVisible() {
        this.lastAlternateBouncerVisibleTime = -1L;
        this._alternateBouncerVisible.setValue(Boolean.FALSE);
    }
}
