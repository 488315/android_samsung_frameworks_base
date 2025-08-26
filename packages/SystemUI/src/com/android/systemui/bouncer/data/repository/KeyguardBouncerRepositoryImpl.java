package com.android.systemui.bouncer.data.repository;

import android.os.Build;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.bouncer.shared.model.BouncerDismissActionModel;
import com.android.systemui.bouncer.shared.model.BouncerShowMessageModel;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.util.time.SystemClock;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes.dex */
public class KeyguardBouncerRepositoryImpl implements KeyguardBouncerRepository {
    public final StateFlowImpl _alternateBouncerVisible;
    public final StateFlowImpl _isBackButtonEnabled;
    public final StateFlowImpl _keyguardAuthenticatedBiometrics;
    public final SharedFlowImpl _keyguardAuthenticatedPrimaryAuth;
    public final StateFlowImpl _keyguardPosition;
    public final StateFlowImpl _lastShownSecurityMode;
    public final StateFlowImpl _panelExpansionAmount;
    public final StateFlowImpl _primaryBouncerInflate;
    public final StateFlowImpl _primaryBouncerScrimmed;
    public final StateFlowImpl _primaryBouncerShow;
    public final StateFlowImpl _primaryBouncerShowingSoon;
    public final StateFlowImpl _primaryBouncerStartingToHide;
    public final StateFlowImpl _primaryBouncerUpdating;
    public final SharedFlowImpl _reset;
    public final StateFlowImpl _resourceUpdateRequests;
    public final StateFlowImpl _showMessage;
    public final SharedFlowImpl _userRequestedBouncerWhenAlreadyAuthenticated;
    public final ReadonlyStateFlow alternateBouncerVisible;
    public BouncerDismissActionModel bouncerDismissActionModelForDex;
    public final SystemClock clock;
    public final ReadonlyStateFlow isBackButtonEnabled;
    public final ReadonlyStateFlow keyguardAuthenticatedBiometrics;
    public final ReadonlySharedFlow keyguardAuthenticatedPrimaryAuth;
    public final ReadonlyStateFlow keyguardPosition;
    public final ReadonlyStateFlow lastShownSecurityMode;
    public final ReadonlyStateFlow panelExpansionAmount;
    public final ReadonlyStateFlow primaryBouncerInflate;
    public final ReadonlyStateFlow primaryBouncerScrimmed;
    public final ReadonlyStateFlow primaryBouncerShow;
    public final ReadonlyStateFlow primaryBouncerShowingSoon;
    public final SharedFlowImpl primaryBouncerStartingDisappearAnimation;
    public final ReadonlyStateFlow primaryBouncerStartingToHide;
    public final ReadonlyStateFlow primaryBouncerUpdating;
    public final ReadonlySharedFlow reset;
    public final ReadonlyStateFlow resourceUpdateRequests;
    public final ReadonlyStateFlow showMessage;
    public final ReadonlySharedFlow userRequestedBouncerWhenAlreadyAuthenticated;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardBouncerRepositoryImpl(SystemClock systemClock, CoroutineScope coroutineScope, TableLogBuffer tableLogBuffer) {
        this.clock = systemClock;
        Boolean bool = Boolean.FALSE;
        StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(bool);
        this._primaryBouncerShow = stateFlowImplMutableStateFlow;
        ReadonlyStateFlow readonlyStateFlowAsStateFlow = FlowKt.asStateFlow(stateFlowImplMutableStateFlow);
        this.primaryBouncerShow = readonlyStateFlowAsStateFlow;
        StateFlowImpl stateFlowImplMutableStateFlow2 = StateFlowKt.MutableStateFlow(bool);
        this._primaryBouncerShowingSoon = stateFlowImplMutableStateFlow2;
        ReadonlyStateFlow readonlyStateFlowAsStateFlow2 = FlowKt.asStateFlow(stateFlowImplMutableStateFlow2);
        this.primaryBouncerShowingSoon = readonlyStateFlowAsStateFlow2;
        StateFlowImpl stateFlowImplMutableStateFlow3 = StateFlowKt.MutableStateFlow(bool);
        this._primaryBouncerStartingToHide = stateFlowImplMutableStateFlow3;
        ReadonlyStateFlow readonlyStateFlowAsStateFlow3 = FlowKt.asStateFlow(stateFlowImplMutableStateFlow3);
        this.primaryBouncerStartingToHide = readonlyStateFlowAsStateFlow3;
        final SharedFlowImpl sharedFlowImplMutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(1, 2, null, 4);
        this.primaryBouncerStartingDisappearAnimation = sharedFlowImplMutableSharedFlow$default;
        StateFlowImpl stateFlowImplMutableStateFlow4 = StateFlowKt.MutableStateFlow(bool);
        this._primaryBouncerScrimmed = stateFlowImplMutableStateFlow4;
        ReadonlyStateFlow readonlyStateFlowAsStateFlow4 = FlowKt.asStateFlow(stateFlowImplMutableStateFlow4);
        this.primaryBouncerScrimmed = readonlyStateFlowAsStateFlow4;
        StateFlowImpl stateFlowImplMutableStateFlow5 = StateFlowKt.MutableStateFlow(Float.valueOf(1.0f));
        this._panelExpansionAmount = stateFlowImplMutableStateFlow5;
        final ReadonlyStateFlow readonlyStateFlowAsStateFlow5 = FlowKt.asStateFlow(stateFlowImplMutableStateFlow5);
        this.panelExpansionAmount = readonlyStateFlowAsStateFlow5;
        StateFlowImpl stateFlowImplMutableStateFlow6 = StateFlowKt.MutableStateFlow(null);
        this._keyguardPosition = stateFlowImplMutableStateFlow6;
        ReadonlyStateFlow readonlyStateFlowAsStateFlow6 = FlowKt.asStateFlow(stateFlowImplMutableStateFlow6);
        this.keyguardPosition = readonlyStateFlowAsStateFlow6;
        StateFlowImpl stateFlowImplMutableStateFlow7 = StateFlowKt.MutableStateFlow(null);
        this._isBackButtonEnabled = stateFlowImplMutableStateFlow7;
        ReadonlyStateFlow readonlyStateFlowAsStateFlow7 = FlowKt.asStateFlow(stateFlowImplMutableStateFlow7);
        this.isBackButtonEnabled = readonlyStateFlowAsStateFlow7;
        StateFlowImpl stateFlowImplMutableStateFlow8 = StateFlowKt.MutableStateFlow(null);
        this._keyguardAuthenticatedBiometrics = stateFlowImplMutableStateFlow8;
        this.keyguardAuthenticatedBiometrics = FlowKt.asStateFlow(stateFlowImplMutableStateFlow8);
        SharedFlowImpl sharedFlowImplMutableSharedFlow$default2 = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._keyguardAuthenticatedPrimaryAuth = sharedFlowImplMutableSharedFlow$default2;
        this.keyguardAuthenticatedPrimaryAuth = FlowKt.asSharedFlow(sharedFlowImplMutableSharedFlow$default2);
        SharedFlowImpl sharedFlowImplMutableSharedFlow$default3 = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._userRequestedBouncerWhenAlreadyAuthenticated = sharedFlowImplMutableSharedFlow$default3;
        this.userRequestedBouncerWhenAlreadyAuthenticated = FlowKt.asSharedFlow(sharedFlowImplMutableSharedFlow$default3);
        StateFlowImpl stateFlowImplMutableStateFlow9 = StateFlowKt.MutableStateFlow(null);
        this._showMessage = stateFlowImplMutableStateFlow9;
        final ReadonlyStateFlow readonlyStateFlowAsStateFlow8 = FlowKt.asStateFlow(stateFlowImplMutableStateFlow9);
        this.showMessage = readonlyStateFlowAsStateFlow8;
        StateFlowImpl stateFlowImplMutableStateFlow10 = StateFlowKt.MutableStateFlow(KeyguardSecurityModel.SecurityMode.Invalid);
        this._lastShownSecurityMode = stateFlowImplMutableStateFlow10;
        final ReadonlyStateFlow readonlyStateFlowAsStateFlow9 = FlowKt.asStateFlow(stateFlowImplMutableStateFlow10);
        this.lastShownSecurityMode = readonlyStateFlowAsStateFlow9;
        StateFlowImpl stateFlowImplMutableStateFlow11 = StateFlowKt.MutableStateFlow(bool);
        this._resourceUpdateRequests = stateFlowImplMutableStateFlow11;
        ReadonlyStateFlow readonlyStateFlowAsStateFlow10 = FlowKt.asStateFlow(stateFlowImplMutableStateFlow11);
        this.resourceUpdateRequests = readonlyStateFlowAsStateFlow10;
        StateFlowImpl stateFlowImplMutableStateFlow12 = StateFlowKt.MutableStateFlow(bool);
        this._alternateBouncerVisible = stateFlowImplMutableStateFlow12;
        ReadonlyStateFlow readonlyStateFlowAsStateFlow11 = FlowKt.asStateFlow(stateFlowImplMutableStateFlow12);
        this.alternateBouncerVisible = readonlyStateFlowAsStateFlow11;
        StateFlowImpl stateFlowImplMutableStateFlow13 = StateFlowKt.MutableStateFlow(bool);
        this._primaryBouncerInflate = stateFlowImplMutableStateFlow13;
        this.primaryBouncerInflate = FlowKt.asStateFlow(stateFlowImplMutableStateFlow13);
        StateFlowImpl stateFlowImplMutableStateFlow14 = StateFlowKt.MutableStateFlow(bool);
        this._primaryBouncerUpdating = stateFlowImplMutableStateFlow14;
        this.primaryBouncerUpdating = FlowKt.asStateFlow(stateFlowImplMutableStateFlow14);
        SharedFlowImpl sharedFlowImplMutableSharedFlow$default4 = SharedFlowKt.MutableSharedFlow$default(1, 0, null, 6);
        this._reset = sharedFlowImplMutableSharedFlow$default4;
        this.reset = FlowKt.asSharedFlow(sharedFlowImplMutableSharedFlow$default4);
        if (Build.IS_DEBUGGABLE) {
            FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(DiffableKt.logDiffsForTable((Flow) readonlyStateFlowAsStateFlow, tableLogBuffer, "", "PrimaryBouncerShow", false), new KeyguardBouncerRepositoryImpl$setUpLogging$1(null)), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow) readonlyStateFlowAsStateFlow2, tableLogBuffer, "", "PrimaryBouncerShowingSoon", false), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow) readonlyStateFlowAsStateFlow3, tableLogBuffer, "", "PrimaryBouncerStartingToHide", false), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable(new Flow() { // from class: com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$1

                /* renamed from: com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$1$2$1, reason: invalid class name */
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
                            Boolean boolValueOf = Boolean.valueOf(((Runnable) obj) != null);
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
                    Object objCollect = sharedFlowImplMutableSharedFlow$default.collect(new AnonymousClass2(flowCollector), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            }, tableLogBuffer, "", "PrimaryBouncerStartingDisappearAnimation", false), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow) readonlyStateFlowAsStateFlow4, tableLogBuffer, "", "PrimaryBouncerScrimmed", false), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable(new Flow() { // from class: com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$2

                /* renamed from: com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$2$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$2$2$1, reason: invalid class name */
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
                            Integer num = new Integer((int) (((Number) obj).floatValue() * 1000));
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
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
                    Object objCollect = readonlyStateFlowAsStateFlow5.collect(new AnonymousClass2(flowCollector), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            }, tableLogBuffer, "", "PanelExpansionAmountMillis", -1), coroutineScope);
            final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(readonlyStateFlowAsStateFlow6);
            FlowKt.launchIn(DiffableKt.logDiffsForTable(new Flow() { // from class: com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$3

                /* renamed from: com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$3$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$3$2$1, reason: invalid class name */
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
                            Integer num = new Integer((int) ((Number) obj).floatValue());
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(num, anonymousClass1) == coroutineSingletons) {
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
                    Object objCollect = flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(new AnonymousClass2(flowCollector), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            }, tableLogBuffer, "", "KeyguardPosition", -1), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow) new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(readonlyStateFlowAsStateFlow7), tableLogBuffer, "", "IsBackButtonEnabled", false), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable(new Flow() { // from class: com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$4

                /* renamed from: com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$4$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$4$2$1, reason: invalid class name */
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
                            BouncerShowMessageModel bouncerShowMessageModel = (BouncerShowMessageModel) obj;
                            String str = bouncerShowMessageModel != null ? bouncerShowMessageModel.message : null;
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(str, anonymousClass1) == coroutineSingletons) {
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
                    Object objCollect = readonlyStateFlowAsStateFlow8.collect(new AnonymousClass2(flowCollector), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            }, tableLogBuffer, "", "ShowMessage", (String) null), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow) readonlyStateFlowAsStateFlow10, tableLogBuffer, "", "ResourceUpdateRequests", false), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow) readonlyStateFlowAsStateFlow11, tableLogBuffer, "", "AlternateBouncerVisible", false), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable(new Flow() { // from class: com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$5

                /* renamed from: com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$5$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$5$2$1, reason: invalid class name */
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
                            String strName = ((KeyguardSecurityModel.SecurityMode) obj).name();
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(strName, anonymousClass1) == coroutineSingletons) {
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
                    Object objCollect = readonlyStateFlowAsStateFlow9.collect(new AnonymousClass2(flowCollector), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            }, tableLogBuffer, "", "lastShownSecurityMode", (String) null), coroutineScope);
        }
    }

    public final boolean isPrimaryBouncerStartingDisappearAnimation() {
        List replayCache = this.primaryBouncerStartingDisappearAnimation.getReplayCache();
        return (replayCache.isEmpty() || CollectionsKt___CollectionsKt.last(replayCache) == null) ? false : true;
    }
}
