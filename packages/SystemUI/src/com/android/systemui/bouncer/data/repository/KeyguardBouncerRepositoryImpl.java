package com.android.systemui.bouncer.data.repository;

import android.os.Build;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.bouncer.shared.model.BouncerDismissActionModel;
import com.android.systemui.log.table.DiffableKt;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.util.time.SystemClock;
import kotlin.Unit;
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

public final class KeyguardBouncerRepositoryImpl implements KeyguardBouncerRepository {
    public final StateFlowImpl _alternateBouncerUIAvailable;
    public final StateFlowImpl _alternateBouncerVisible;
    public final StateFlowImpl _isBackButtonEnabled;
    public final StateFlowImpl _keyguardAuthenticatedBiometrics;
    public final SharedFlowImpl _keyguardAuthenticatedPrimaryAuth;
    public final StateFlowImpl _keyguardPosition;
    public final StateFlowImpl _lastShownSecurityMode;
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
    public final SharedFlowImpl _userRequestedBouncerWhenAlreadyAuthenticated;
    public final ReadonlyStateFlow alternateBouncerUIAvailable;
    public final ReadonlyStateFlow alternateBouncerVisible;
    public BouncerDismissActionModel bouncerDismissActionModel;
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
    public final ReadonlyStateFlow primaryBouncerStartingDisappearAnimation;
    public final ReadonlyStateFlow primaryBouncerStartingToHide;
    public final ReadonlyStateFlow primaryBouncerUpdating;
    public final ReadonlyStateFlow resourceUpdateRequests;
    public final ReadonlyStateFlow showMessage;
    public final ReadonlySharedFlow userRequestedBouncerWhenAlreadyAuthenticated;

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
        StateFlowImpl MutableStateFlow7 = StateFlowKt.MutableStateFlow(null);
        this._keyguardPosition = MutableStateFlow7;
        ReadonlyStateFlow asStateFlow7 = FlowKt.asStateFlow(MutableStateFlow7);
        this.keyguardPosition = asStateFlow7;
        StateFlowImpl MutableStateFlow8 = StateFlowKt.MutableStateFlow(null);
        this._isBackButtonEnabled = MutableStateFlow8;
        ReadonlyStateFlow asStateFlow8 = FlowKt.asStateFlow(MutableStateFlow8);
        this.isBackButtonEnabled = asStateFlow8;
        StateFlowImpl MutableStateFlow9 = StateFlowKt.MutableStateFlow(null);
        this._keyguardAuthenticatedBiometrics = MutableStateFlow9;
        this.keyguardAuthenticatedBiometrics = FlowKt.asStateFlow(MutableStateFlow9);
        this.keyguardAuthenticatedPrimaryAuth = FlowKt.asSharedFlow(SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7));
        this.userRequestedBouncerWhenAlreadyAuthenticated = FlowKt.asSharedFlow(SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7));
        StateFlowImpl MutableStateFlow10 = StateFlowKt.MutableStateFlow(null);
        this._showMessage = MutableStateFlow10;
        final ReadonlyStateFlow asStateFlow9 = FlowKt.asStateFlow(MutableStateFlow10);
        this.showMessage = asStateFlow9;
        StateFlowImpl MutableStateFlow11 = StateFlowKt.MutableStateFlow(KeyguardSecurityModel.SecurityMode.Invalid);
        this._lastShownSecurityMode = MutableStateFlow11;
        final ReadonlyStateFlow asStateFlow10 = FlowKt.asStateFlow(MutableStateFlow11);
        this.lastShownSecurityMode = asStateFlow10;
        StateFlowImpl MutableStateFlow12 = StateFlowKt.MutableStateFlow(bool);
        this._resourceUpdateRequests = MutableStateFlow12;
        ReadonlyStateFlow asStateFlow11 = FlowKt.asStateFlow(MutableStateFlow12);
        this.resourceUpdateRequests = asStateFlow11;
        StateFlowImpl MutableStateFlow13 = StateFlowKt.MutableStateFlow(bool);
        this._alternateBouncerVisible = MutableStateFlow13;
        ReadonlyStateFlow asStateFlow12 = FlowKt.asStateFlow(MutableStateFlow13);
        this.alternateBouncerVisible = asStateFlow12;
        ReadonlyStateFlow asStateFlow13 = FlowKt.asStateFlow(StateFlowKt.MutableStateFlow(bool));
        StateFlowImpl MutableStateFlow14 = StateFlowKt.MutableStateFlow(bool);
        this._primaryBouncerInflate = MutableStateFlow14;
        this.primaryBouncerInflate = FlowKt.asStateFlow(MutableStateFlow14);
        StateFlowImpl MutableStateFlow15 = StateFlowKt.MutableStateFlow(bool);
        this._primaryBouncerUpdating = MutableStateFlow15;
        this.primaryBouncerUpdating = FlowKt.asStateFlow(MutableStateFlow15);
        if (Build.IS_DEBUGGABLE) {
            FlowKt.launchIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(DiffableKt.logDiffsForTable((Flow) asStateFlow, tableLogBuffer, "", "PrimaryBouncerShow", false), new KeyguardBouncerRepositoryImpl$setUpLogging$1(null)), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow) asStateFlow2, tableLogBuffer, "", "PrimaryBouncerShowingSoon", false), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow) asStateFlow3, tableLogBuffer, "", "PrimaryBouncerStartingToHide", false), coroutineScope);
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

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                        /*
                            r4 = this;
                            boolean r0 = r6 instanceof com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$1$2$1 r0 = (com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$1$2$1 r0 = new com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$1$2$1
                            r0.<init>(r6)
                        L18:
                            java.lang.Object r6 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r6)
                            goto L48
                        L27:
                            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                            r4.<init>(r5)
                            throw r4
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r6)
                            java.lang.Runnable r5 = (java.lang.Runnable) r5
                            if (r5 == 0) goto L38
                            r5 = r3
                            goto L39
                        L38:
                            r5 = 0
                        L39:
                            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                            java.lang.Object r4 = r4.emit(r5, r0)
                            if (r4 != r1) goto L48
                            return r1
                        L48:
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            }, tableLogBuffer, "", "PrimaryBouncerStartingDisappearAnimation", false), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow) asStateFlow5, tableLogBuffer, "", "PrimaryBouncerScrimmed", false), coroutineScope);
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

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                        /*
                            r4 = this;
                            boolean r0 = r6 instanceof com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$2.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$2$2$1 r0 = (com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$2$2$1 r0 = new com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$2$2$1
                            r0.<init>(r6)
                        L18:
                            java.lang.Object r6 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r6)
                            goto L4d
                        L27:
                            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                            r4.<init>(r5)
                            throw r4
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r6)
                            java.lang.Number r5 = (java.lang.Number) r5
                            float r5 = r5.floatValue()
                            r6 = 1000(0x3e8, float:1.401E-42)
                            float r6 = (float) r6
                            float r5 = r5 * r6
                            int r5 = (int) r5
                            java.lang.Integer r6 = new java.lang.Integer
                            r6.<init>(r5)
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                            java.lang.Object r4 = r4.emit(r6, r0)
                            if (r4 != r1) goto L4d
                            return r1
                        L4d:
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            }, tableLogBuffer, "", "PanelExpansionAmountMillis", -1), coroutineScope);
            final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(asStateFlow7);
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

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                        /*
                            r4 = this;
                            boolean r0 = r6 instanceof com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$3.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$3$2$1 r0 = (com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$3$2$1 r0 = new com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$3$2$1
                            r0.<init>(r6)
                        L18:
                            java.lang.Object r6 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r6)
                            goto L49
                        L27:
                            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                            r4.<init>(r5)
                            throw r4
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r6)
                            java.lang.Number r5 = (java.lang.Number) r5
                            float r5 = r5.floatValue()
                            int r5 = (int) r5
                            java.lang.Integer r6 = new java.lang.Integer
                            r6.<init>(r5)
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                            java.lang.Object r4 = r4.emit(r6, r0)
                            if (r4 != r1) goto L49
                            return r1
                        L49:
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            }, tableLogBuffer, "", "KeyguardPosition", -1), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow) new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(asStateFlow8), tableLogBuffer, "", "IsBackButtonEnabled", false), coroutineScope);
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

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                        /*
                            r4 = this;
                            boolean r0 = r6 instanceof com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$4.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$4$2$1 r0 = (com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$4$2$1 r0 = new com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$4$2$1
                            r0.<init>(r6)
                        L18:
                            java.lang.Object r6 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r6)
                            goto L45
                        L27:
                            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                            r4.<init>(r5)
                            throw r4
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r6)
                            com.android.systemui.bouncer.shared.model.BouncerShowMessageModel r5 = (com.android.systemui.bouncer.shared.model.BouncerShowMessageModel) r5
                            if (r5 == 0) goto L39
                            java.lang.String r5 = r5.message
                            goto L3a
                        L39:
                            r5 = 0
                        L3a:
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                            java.lang.Object r4 = r4.emit(r5, r0)
                            if (r4 != r1) goto L45
                            return r1
                        L45:
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            }, tableLogBuffer, "ShowMessage", (String) null), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow) asStateFlow11, tableLogBuffer, "", "ResourceUpdateRequests", false), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow) asStateFlow13, tableLogBuffer, "", "IsAlternateBouncerUIAvailable", false), coroutineScope);
            FlowKt.launchIn(DiffableKt.logDiffsForTable((Flow) asStateFlow12, tableLogBuffer, "", "AlternateBouncerVisible", false), coroutineScope);
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

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                        /*
                            r4 = this;
                            boolean r0 = r6 instanceof com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$5.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$5$2$1 r0 = (com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$5.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$5$2$1 r0 = new com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$5$2$1
                            r0.<init>(r6)
                        L18:
                            java.lang.Object r6 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r6)
                            goto L43
                        L27:
                            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                            r4.<init>(r5)
                            throw r4
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r6)
                            com.android.keyguard.KeyguardSecurityModel$SecurityMode r5 = (com.android.keyguard.KeyguardSecurityModel.SecurityMode) r5
                            java.lang.String r5 = r5.name()
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                            java.lang.Object r4 = r4.emit(r5, r0)
                            if (r4 != r1) goto L43
                            return r1
                        L43:
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.data.repository.KeyguardBouncerRepositoryImpl$setUpLogging$$inlined$map$5.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            }, tableLogBuffer, "lastShownSecurityMode", (String) null), coroutineScope);
        }
    }
}
