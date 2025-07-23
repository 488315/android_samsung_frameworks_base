package com.android.systemui.display.domain.interactor;

import android.companion.virtual.VirtualDeviceManager;
import com.android.systemui.display.data.repository.DeviceStateRepository;
import com.android.systemui.display.data.repository.DeviceStateRepositoryImpl;
import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.display.data.repository.DisplayRepositoryImpl;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ConnectedDisplayInteractorImpl implements ConnectedDisplayInteractor {
    public final Flow concurrentDisplaysInProgress;
    public final ConnectedDisplayInteractorImpl$special$$inlined$map$3 connectedDisplayAddition;
    public final Flow connectedDisplayState;
    public final Flow isExternalDesktopWindowing;
    public final ConnectedDisplayInteractorImpl$special$$inlined$map$4 pendingDisplay;
    public final VirtualDeviceManager virtualDeviceManager;

    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$4] */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$3] */
    public ConnectedDisplayInteractorImpl(VirtualDeviceManager virtualDeviceManager, KeyguardRepository keyguardRepository, DisplayRepository displayRepository, DeviceStateRepository deviceStateRepository, CoroutineDispatcher coroutineDispatcher) {
        this.virtualDeviceManager = virtualDeviceManager;
        DisplayRepositoryImpl displayRepositoryImpl = (DisplayRepositoryImpl) displayRepository;
        final StateFlow displays = displayRepositoryImpl.displayRepositoryFromLib.getDisplays();
        this.connectedDisplayState = FlowKt.distinctUntilChanged(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ ConnectedDisplayInteractorImpl this$0;

                /* renamed from: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, ConnectedDisplayInteractorImpl connectedDisplayInteractorImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = connectedDisplayInteractorImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r11, kotlin.coroutines.Continuation r12) {
                    /*
                        r10 = this;
                        boolean r0 = r12 instanceof com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r12
                        com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$1$2$1
                        r0.<init>(r12)
                    L18:
                        java.lang.Object r12 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r12)
                        goto Lcd
                    L28:
                        java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                        java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
                        r10.<init>(r11)
                        throw r10
                    L30:
                        kotlin.ResultKt.throwOnFailure(r12)
                        java.util.Set r11 = (java.util.Set) r11
                        java.lang.Iterable r11 = (java.lang.Iterable) r11
                        java.util.ArrayList r12 = new java.util.ArrayList
                        r12.<init>()
                        java.util.Iterator r2 = r11.iterator()
                    L40:
                        boolean r4 = r2.hasNext()
                        r5 = 2
                        com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl r6 = r10.this$0
                        if (r4 == 0) goto L5d
                        java.lang.Object r4 = r2.next()
                        r7 = r4
                        android.view.Display r7 = (android.view.Display) r7
                        r6.getClass()
                        int r6 = r7.getType()
                        if (r6 != r5) goto L40
                        r12.add(r4)
                        goto L40
                    L5d:
                        java.util.ArrayList r2 = new java.util.ArrayList
                        r2.<init>()
                        int r4 = r12.size()
                        r7 = 0
                    L67:
                        if (r7 >= r4) goto L80
                        java.lang.Object r8 = r12.get(r7)
                        int r7 = r7 + 1
                        r9 = r8
                        android.view.Display r9 = (android.view.Display) r9
                        r6.getClass()
                        int r9 = r9.getFlags()
                        r9 = r9 & r5
                        if (r9 == 0) goto L67
                        r2.add(r8)
                        goto L67
                    L80:
                        java.util.ArrayList r4 = new java.util.ArrayList
                        r4.<init>()
                        java.util.Iterator r11 = r11.iterator()
                    L89:
                        boolean r5 = r11.hasNext()
                        if (r5 == 0) goto La8
                        java.lang.Object r5 = r11.next()
                        r7 = r5
                        android.view.Display r7 = (android.view.Display) r7
                        android.companion.virtual.VirtualDeviceManager r8 = r6.virtualDeviceManager
                        if (r8 == 0) goto L89
                        int r7 = r7.getDisplayId()
                        boolean r7 = r8.isVirtualDeviceOwnedMirrorDisplay(r7)
                        if (r7 == 0) goto L89
                        r4.add(r5)
                        goto L89
                    La8:
                        boolean r11 = r12.isEmpty()
                        if (r11 == 0) goto Lb7
                        boolean r11 = r4.isEmpty()
                        if (r11 == 0) goto Lb7
                        com.android.systemui.display.domain.interactor.ConnectedDisplayInteractor$State r11 = com.android.systemui.display.domain.interactor.ConnectedDisplayInteractor.State.DISCONNECTED
                        goto Lc2
                    Lb7:
                        boolean r11 = r2.isEmpty()
                        if (r11 != 0) goto Lc0
                        com.android.systemui.display.domain.interactor.ConnectedDisplayInteractor$State r11 = com.android.systemui.display.domain.interactor.ConnectedDisplayInteractor.State.CONNECTED_SECURE
                        goto Lc2
                    Lc0:
                        com.android.systemui.display.domain.interactor.ConnectedDisplayInteractor$State r11 = com.android.systemui.display.domain.interactor.ConnectedDisplayInteractor.State.CONNECTED
                    Lc2:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r10 = r10.$this_unsafeFlow
                        java.lang.Object r10 = r10.emit(r11, r0)
                        if (r10 != r1) goto Lcd
                        return r1
                    Lcd:
                        kotlin.Unit r10 = kotlin.Unit.INSTANCE
                        return r10
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineDispatcher));
        com.android.app.displaylib.DisplayRepository displayRepository2 = displayRepositoryImpl.displayRepositoryFromLib;
        final StateFlow displays2 = displayRepository2.getDisplays();
        this.isExternalDesktopWindowing = FlowKt.distinctUntilChanged(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ ConnectedDisplayInteractorImpl this$0;

                /* renamed from: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, ConnectedDisplayInteractorImpl connectedDisplayInteractorImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = connectedDisplayInteractorImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
                    /*
                        r7 = this;
                        boolean r0 = r9 instanceof com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r9
                        com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$2$2$1 r0 = (com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$2$2$1 r0 = new com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$2$2$1
                        r0.<init>(r9)
                    L18:
                        java.lang.Object r9 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r9)
                        goto L80
                    L27:
                        java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                        java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                        r7.<init>(r8)
                        throw r7
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r9)
                        java.util.Set r8 = (java.util.Set) r8
                        java.lang.Iterable r8 = (java.lang.Iterable) r8
                        java.util.ArrayList r9 = new java.util.ArrayList
                        r9.<init>()
                        java.util.Iterator r8 = r8.iterator()
                    L3f:
                        boolean r2 = r8.hasNext()
                        if (r2 == 0) goto L6c
                        java.lang.Object r2 = r8.next()
                        r4 = r2
                        android.view.Display r4 = (android.view.Display) r4
                        com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl r5 = r7.this$0
                        r5.getClass()
                        int r5 = r4.getDisplayId()
                        if (r5 == 0) goto L3f
                        int r5 = r4.getDisplayId()
                        r6 = -1
                        if (r5 != r6) goto L5f
                        goto L3f
                    L5f:
                        r5 = 131072(0x20000, float:1.83671E-40)
                        int r4 = r4.getFlags()
                        r4 = r4 & r5
                        if (r4 == 0) goto L3f
                        r9.add(r2)
                        goto L3f
                    L6c:
                        boolean r8 = r9.isEmpty()
                        r8 = r8 ^ r3
                        java.lang.Boolean r8 = java.lang.Boolean.valueOf(r8)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r7 = r7.$this_unsafeFlow
                        java.lang.Object r7 = r7.emit(r8, r0)
                        if (r7 != r1) goto L80
                        return r1
                    L80:
                        kotlin.Unit r7 = kotlin.Unit.INSTANCE
                        return r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineDispatcher));
        final Flow displayAdditionEvent = displayRepository2.getDisplayAdditionEvent();
        final Flow flowOn = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ ConnectedDisplayInteractorImpl this$0;

                /* renamed from: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$filter$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, ConnectedDisplayInteractorImpl connectedDisplayInteractorImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = connectedDisplayInteractorImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$filter$1$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L5d
                    L27:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r8)
                        r8 = r7
                        android.view.Display r8 = (android.view.Display) r8
                        if (r8 == 0) goto L5d
                        com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl r2 = r6.this$0
                        r2.getClass()
                        int r4 = r8.getType()
                        r5 = 2
                        if (r4 != r5) goto L44
                        goto L52
                    L44:
                        android.companion.virtual.VirtualDeviceManager r2 = r2.virtualDeviceManager
                        if (r2 == 0) goto L5d
                        int r8 = r8.getDisplayId()
                        boolean r8 = r2.isVirtualDeviceOwnedMirrorDisplay(r8)
                        if (r8 == 0) goto L5d
                    L52:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                        java.lang.Object r6 = r6.emit(r7, r0)
                        if (r6 != r1) goto L5d
                        return r1
                    L5d:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineDispatcher);
        this.connectedDisplayAddition = new Flow() { // from class: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$3$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$3$2$1 r0 = (com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$3$2$1 r0 = new com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$3$2$1
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
                        android.view.Display r5 = (android.view.Display) r5
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final Flow distinctUntilChanged = FlowKt.distinctUntilChanged(displayRepository2.getPendingDisplay());
        this.pendingDisplay = new Flow() { // from class: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$4

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ ConnectedDisplayInteractorImpl this$0;

                /* renamed from: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$4$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, ConnectedDisplayInteractorImpl connectedDisplayInteractorImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = connectedDisplayInteractorImpl;
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
                        boolean r0 = r6 instanceof com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$4$2$1 r0 = (com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$4$2$1 r0 = new com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$4$2$1
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
                        com.android.app.displaylib.DisplayRepository$PendingDisplay r5 = (com.android.app.displaylib.DisplayRepository.PendingDisplay) r5
                        if (r5 == 0) goto L41
                        com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl r6 = r4.this$0
                        r6.getClass()
                        com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$toInteractorPendingDisplay$1 r6 = new com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$toInteractorPendingDisplay$1
                        r6.<init>(r5)
                        goto L42
                    L41:
                        r6 = 0
                    L42:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L4d
                        return r1
                    L4d:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        final ReadonlyStateFlow readonlyStateFlow = ((DeviceStateRepositoryImpl) deviceStateRepository).state;
        this.concurrentDisplaysInProgress = FlowKt.flowOn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$5

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$5$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$5$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$5.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$5$2$1 r0 = (com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$5.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$5$2$1 r0 = new com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$5$2$1
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
                        com.android.systemui.display.data.repository.DeviceStateRepository$DeviceState r5 = (com.android.systemui.display.data.repository.DeviceStateRepository.DeviceState) r5
                        com.android.systemui.display.data.repository.DeviceStateRepository$DeviceState r6 = com.android.systemui.display.data.repository.DeviceStateRepository.DeviceState.CONCURRENT_DISPLAY
                        if (r5 != r6) goto L3a
                        r5 = r3
                        goto L3b
                    L3a:
                        r5 = 0
                    L3b:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4a
                        return r1
                    L4a:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$5.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), coroutineDispatcher);
    }
}
