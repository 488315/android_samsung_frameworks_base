package com.android.systemui.display.domain.interactor;

import android.companion.virtual.VirtualDeviceManager;
import android.view.Display;
import com.android.app.displaylib.DisplayRepository;
import com.android.systemui.display.data.repository.DeviceStateRepository;
import com.android.systemui.display.data.repository.DeviceStateRepositoryImpl;
import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.display.data.repository.DisplayRepositoryImpl;
import com.android.systemui.display.domain.interactor.ConnectedDisplayInteractor;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import kotlin.ResultKt;
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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    ConnectedDisplayInteractorImpl connectedDisplayInteractorImpl;
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
                        Set set = (Set) obj;
                        ArrayList arrayList = new ArrayList();
                        Iterator it = set.iterator();
                        while (true) {
                            boolean zHasNext = it.hasNext();
                            connectedDisplayInteractorImpl = this.this$0;
                            if (!zHasNext) {
                                break;
                            }
                            Object next = it.next();
                            connectedDisplayInteractorImpl.getClass();
                            if (((Display) next).getType() == 2) {
                                arrayList.add(next);
                            }
                        }
                        ArrayList arrayList2 = new ArrayList();
                        int size = arrayList.size();
                        int i3 = 0;
                        while (i3 < size) {
                            Object obj3 = arrayList.get(i3);
                            i3++;
                            connectedDisplayInteractorImpl.getClass();
                            if ((((Display) obj3).getFlags() & 2) != 0) {
                                arrayList2.add(obj3);
                            }
                        }
                        ArrayList arrayList3 = new ArrayList();
                        for (Object obj4 : set) {
                            Display display = (Display) obj4;
                            VirtualDeviceManager virtualDeviceManager = connectedDisplayInteractorImpl.virtualDeviceManager;
                            if (virtualDeviceManager != null && virtualDeviceManager.isVirtualDeviceOwnedMirrorDisplay(display.getDisplayId())) {
                                arrayList3.add(obj4);
                            }
                        }
                        ConnectedDisplayInteractor.State state = (arrayList.isEmpty() && arrayList3.isEmpty()) ? ConnectedDisplayInteractor.State.DISCONNECTED : !arrayList2.isEmpty() ? ConnectedDisplayInteractor.State.CONNECTED_SECURE : ConnectedDisplayInteractor.State.CONNECTED;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(state, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = displays.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineDispatcher));
        com.android.app.displaylib.DisplayRepository displayRepository2 = displayRepositoryImpl.displayRepositoryFromLib;
        final StateFlow displays2 = displayRepository2.getDisplays();
        this.isExternalDesktopWindowing = FlowKt.distinctUntilChanged(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$2

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
                        ArrayList arrayList = new ArrayList();
                        for (Object obj3 : (Set) obj) {
                            Display display = (Display) obj3;
                            this.this$0.getClass();
                            if (display.getDisplayId() != 0 && display.getDisplayId() != -1 && (display.getFlags() & 131072) != 0) {
                                arrayList.add(obj3);
                            }
                        }
                        Boolean boolValueOf = Boolean.valueOf(!arrayList.isEmpty());
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
                Object objCollect = displays2.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineDispatcher));
        final Flow displayAdditionEvent = displayRepository2.getDisplayAdditionEvent();
        final Flow flowFlowOn = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$filter$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    VirtualDeviceManager virtualDeviceManager;
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
                        Display display = (Display) obj;
                        if (display != null) {
                            ConnectedDisplayInteractorImpl connectedDisplayInteractorImpl = this.this$0;
                            connectedDisplayInteractorImpl.getClass();
                            if (display.getType() == 2 || ((virtualDeviceManager = connectedDisplayInteractorImpl.virtualDeviceManager) != null && virtualDeviceManager.isVirtualDeviceOwnedMirrorDisplay(display.getDisplayId()))) {
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
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
                Object objCollect = displayAdditionEvent.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineDispatcher);
        this.connectedDisplayAddition = new Flow() { // from class: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$3

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
                        Unit unit = Unit.INSTANCE;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(unit, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowFlowOn.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        final Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(displayRepository2.getPendingDisplay());
        this.pendingDisplay = new Flow() { // from class: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$4

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    ConnectedDisplayInteractorImpl$toInteractorPendingDisplay$1 connectedDisplayInteractorImpl$toInteractorPendingDisplay$1;
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
                        DisplayRepository.PendingDisplay pendingDisplay = (DisplayRepository.PendingDisplay) obj;
                        if (pendingDisplay != null) {
                            this.this$0.getClass();
                            connectedDisplayInteractorImpl$toInteractorPendingDisplay$1 = new ConnectedDisplayInteractorImpl$toInteractorPendingDisplay$1(pendingDisplay);
                        } else {
                            connectedDisplayInteractorImpl$toInteractorPendingDisplay$1 = null;
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(connectedDisplayInteractorImpl$toInteractorPendingDisplay$1, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowDistinctUntilChanged.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        final ReadonlyStateFlow readonlyStateFlow = ((DeviceStateRepositoryImpl) deviceStateRepository).state;
        this.concurrentDisplaysInProgress = FlowKt.flowOn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$5

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
                        Boolean boolValueOf = Boolean.valueOf(((DeviceStateRepository.DeviceState) obj) == DeviceStateRepository.DeviceState.CONCURRENT_DISPLAY);
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
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), coroutineDispatcher);
    }
}
