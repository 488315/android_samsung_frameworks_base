package com.android.app.displaylib;

import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Trace;
import android.util.Log;
import android.view.Display;
import com.android.app.displaylib.DisplayEvent;
import com.android.app.displaylib.DisplayRepositoryImpl;
import com.android.app.tracing.FlowTracing;
import com.android.app.tracing.TraceStateLogger;
import com.android.app.tracing.TraceUtilsKt;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptySet;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes.dex */
public final class DisplayRepositoryImpl implements DisplayRepository {
    public static final Companion Companion = new Companion(null);
    public static final boolean DEBUG = Log.isLoggable("DisplayRepository", 3);
    public final StateFlowImpl _ignoredDisplayIds;
    public final Flow allDisplayEvents;
    public final ReadonlyStateFlow connectedDisplayIds;
    public final Lazy defaultDisplay$delegate;
    public final Flow defaultDisplayOff;
    public final ChannelFlowTransformLatest displayAdditionEvent;
    public final DisplayRepositoryImpl$special$$inlined$map$1 displayChangeEvent;
    public final ReadonlyStateFlow displayIds;
    public final DisplayManager displayManager;
    public final DisplayRepositoryImpl$special$$inlined$map$2 displayRemovalEvent;
    public final ReadonlyStateFlow displays;
    public final ReadonlyStateFlow enabledDisplayIds;
    public final ReadonlyStateFlow enabledDisplays;
    public final Set initialDisplayIds;
    public final Set initialDisplays;
    public final Flow pendingDisplay;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$map$2] */
    /* JADX WARN: Type inference failed for: r5v1, types: [com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$map$1] */
    public DisplayRepositoryImpl(DisplayManager displayManager, Handler handler, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.displayManager = displayManager;
        final Flow flowFlowOn = FlowKt.flowOn(debugLog(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new DisplayRepositoryImpl$allDisplayEvents$2(null), FlowKt.buffer$default(FlowKt.callbackFlow(new DisplayRepositoryImpl$allDisplayEvents$1(this, handler, null)), -1, 2)), "allDisplayEvents"), coroutineDispatcher);
        this.allDisplayEvents = flowFlowOn;
        final Flow flow = new Flow() { // from class: com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$filterIsInstance$1

            /* renamed from: com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$filterIsInstance$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$filterIsInstance$1$2$1, reason: invalid class name */
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
                        if (obj instanceof DisplayEvent.Changed) {
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
                Object objCollect = flowFlowOn.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.displayChangeEvent = new Flow() { // from class: com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$map$1

            /* renamed from: com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        Integer num = new Integer(((DisplayEvent.Changed) obj).displayId);
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
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        final Flow flow2 = new Flow() { // from class: com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$filterIsInstance$2

            /* renamed from: com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$filterIsInstance$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$filterIsInstance$2$2$1, reason: invalid class name */
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
                        if (obj instanceof DisplayEvent.Removed) {
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
                Object objCollect = flowFlowOn.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.displayRemovalEvent = new Flow() { // from class: com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$map$2

            /* renamed from: com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        Integer num = new Integer(((DisplayEvent.Removed) obj).displayId);
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
                Object objCollect = flow2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("DisplayRepository#initialDisplays");
        }
        try {
            Display[] displays = displayManager.getDisplays();
            Set set = (displays == null || (set = ArraysKt___ArraysKt.toSet(displays)) == null) ? EmptySet.INSTANCE : set;
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            this.initialDisplays = set;
            Set set2 = set;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(set2, 10));
            Iterator it = set2.iterator();
            while (it.hasNext()) {
                arrayList.add(Integer.valueOf(((Display) it.next()).getDisplayId()));
            }
            Set set3 = CollectionsKt___CollectionsKt.toSet(arrayList);
            this.initialDisplayIds = set3;
            Flow flowDebugLog = debugLog(FlowKt.distinctUntilChanged(new FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1(set3, this.allDisplayEvents, new DisplayRepositoryImpl$enabledDisplayIds$1(null))), "enabledDisplayIds");
            SharingStarted.Companion companion = SharingStarted.Companion;
            ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flowDebugLog, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), set3);
            this.enabledDisplayIds = readonlyStateFlowStateIn;
            this.defaultDisplay$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.app.displaylib.DisplayRepositoryImpl$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    DisplayRepositoryImpl.Companion companion2 = DisplayRepositoryImpl.Companion;
                    Display displayFromDisplayManager = this.f$0.getDisplayFromDisplayManager(0);
                    if (displayFromDisplayManager != null) {
                        return displayFromDisplayManager;
                    }
                    throw new IllegalStateException("Unable to get default display.");
                }
            });
            Function1 function1 = new Function1() { // from class: com.android.app.displaylib.DisplayRepositoryImpl$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj) {
                    int iIntValue = ((Integer) obj).intValue();
                    DisplayRepositoryImpl.Companion companion2 = DisplayRepositoryImpl.Companion;
                    return this.f$0.getDisplayFromDisplayManager(iIntValue);
                }
            };
            EmptySet emptySet = EmptySet.INSTANCE;
            final DisplayRepositoryImpl$mapElementsLazily$State displayRepositoryImpl$mapElementsLazily$State = new DisplayRepositoryImpl$mapElementsLazily$State(emptySet, MapsKt__MapsKt.emptyMap(), emptySet);
            final FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1 flowKt__TransformKt$runningFold$$inlined$unsafeFlow$1 = new FlowKt__TransformKt$runningFold$$inlined$unsafeFlow$1(displayRepositoryImpl$mapElementsLazily$State, readonlyStateFlowStateIn, new DisplayRepositoryImpl$mapElementsLazily$1(function1, null));
            final Flow flow3 = new Flow() { // from class: com.android.app.displaylib.DisplayRepositoryImpl$mapElementsLazily$$inlined$filter$1

                /* renamed from: com.android.app.displaylib.DisplayRepositoryImpl$mapElementsLazily$$inlined$filter$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ DisplayRepositoryImpl$mapElementsLazily$State $emptyInitialState$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.app.displaylib.DisplayRepositoryImpl$mapElementsLazily$$inlined$filter$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, DisplayRepositoryImpl$mapElementsLazily$State displayRepositoryImpl$mapElementsLazily$State) {
                        this.$this_unsafeFlow = flowCollector;
                        this.$emptyInitialState$inlined = displayRepositoryImpl$mapElementsLazily$State;
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
                            if (!Intrinsics.areEqual((DisplayRepositoryImpl$mapElementsLazily$State) obj, this.$emptyInitialState$inlined)) {
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
                    Object objCollect = flowKt__TransformKt$runningFold$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, displayRepositoryImpl$mapElementsLazily$State), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            };
            ReadonlyStateFlow readonlyStateFlowStateIn2 = FlowKt.stateIn(debugLog(FlowKt.flowOn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(new Flow() { // from class: com.android.app.displaylib.DisplayRepositoryImpl$mapElementsLazily$$inlined$map$1

                /* renamed from: com.android.app.displaylib.DisplayRepositoryImpl$mapElementsLazily$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.app.displaylib.DisplayRepositoryImpl$mapElementsLazily$$inlined$map$1$2$1, reason: invalid class name */
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
                            Set set = ((DisplayRepositoryImpl$mapElementsLazily$State) obj).resultSet;
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(set, anonymousClass1) == coroutineSingletons) {
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
            }, new DisplayRepositoryImpl$enabledDisplays$2(null)), coroutineDispatcher), "enabledDisplays"), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), this.initialDisplays);
            this.enabledDisplays = readonlyStateFlowStateIn2;
            this.displays = readonlyStateFlowStateIn2;
            this.displayIds = readonlyStateFlowStateIn;
            this.displayAdditionEvent = FlowKt.transformLatest(new SafeFlow(new DisplayRepositoryKt$pairwiseBy$1(readonlyStateFlowStateIn2, new DisplayRepositoryImpl$displayAdditionEvent$1(null), null)), new DisplayRepositoryImpl$special$$inlined$flatMapLatest$1(null));
            StateFlowImpl stateFlowImplMutableStateFlow = StateFlowKt.MutableStateFlow(emptySet);
            this._ignoredDisplayIds = stateFlowImplMutableStateFlow;
            Flow flowDebugLog2 = debugLog(stateFlowImplMutableStateFlow, "ignoredDisplayIds");
            final ReadonlyStateFlow readonlyStateFlowStateIn3 = FlowKt.stateIn(debugLog(FlowKt.distinctUntilChanged(FlowKt.buffer$default(FlowKt.callbackFlow(new DisplayRepositoryImpl$connectedDisplayIds$1(this, handler, null)), -1, 2)), "connectedDisplayIds"), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), emptySet);
            this.connectedDisplayIds = readonlyStateFlowStateIn3;
            final Flow flowDebugLog3 = debugLog(FlowKt.combine(readonlyStateFlowStateIn, debugLog(FlowKt.flowOn(new Flow() { // from class: com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$map$3

                /* renamed from: com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$map$3$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ DisplayRepositoryImpl this$0;

                    /* renamed from: com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$map$3$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, DisplayRepositoryImpl displayRepositoryImpl) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = displayRepositoryImpl;
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
                            Set set = (Set) obj;
                            boolean zIsEnabled = Trace.isEnabled();
                            if (zIsEnabled) {
                                TraceUtilsKt.beginSlice("DisplayRepository#filteringExternalDisplays");
                            }
                            try {
                                ArrayList arrayList = new ArrayList();
                                for (Object obj3 : set) {
                                    int iIntValue = ((Number) obj3).intValue();
                                    DisplayRepositoryImpl displayRepositoryImpl = this.this$0;
                                    DisplayRepositoryImpl.Companion companion = DisplayRepositoryImpl.Companion;
                                    displayRepositoryImpl.getClass();
                                    zIsEnabled = Trace.isEnabled();
                                    if (zIsEnabled) {
                                        TraceUtilsKt.beginSlice("DisplayRepository#getDisplayType");
                                    }
                                    try {
                                        Display display = displayRepositoryImpl.displayManager.getDisplay(iIntValue);
                                        Integer numValueOf = display != null ? Integer.valueOf(display.getType()) : null;
                                        if (zIsEnabled) {
                                            TraceUtilsKt.endSlice();
                                        }
                                        if (numValueOf != null && numValueOf.intValue() == 2) {
                                            arrayList.add(obj3);
                                        }
                                    } finally {
                                        if (zIsEnabled) {
                                            TraceUtilsKt.endSlice();
                                        }
                                    }
                                }
                                Set set2 = CollectionsKt___CollectionsKt.toSet(arrayList);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(set2, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } catch (Throwable th) {
                                if (zIsEnabled) {
                                    TraceUtilsKt.endSlice();
                                }
                                throw th;
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
                    Object objCollect = readonlyStateFlowStateIn3.collect(new AnonymousClass2(flowCollector, this), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            }, coroutineDispatcher), "connectedExternalDisplayIds"), flowDebugLog2, new DisplayRepositoryImpl$pendingDisplayIds$1(null)), "allPendingDisplayIds");
            final Flow flowDebugLog4 = debugLog(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$map$4

                /* renamed from: com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$map$4$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$map$4$2$1, reason: invalid class name */
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
                            Comparable comparableMaxOrNull = CollectionsKt___CollectionsKt.maxOrNull((Iterable) obj);
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(comparableMaxOrNull, anonymousClass1) == coroutineSingletons) {
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
                    Object objCollect = flowDebugLog3.collect(new AnonymousClass2(flowCollector), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            }), "pendingDisplayId");
            this.pendingDisplay = debugLog(new Flow() { // from class: com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$map$5

                /* renamed from: com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$map$5$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ DisplayRepositoryImpl this$0;

                    /* renamed from: com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$map$5$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, DisplayRepositoryImpl displayRepositoryImpl) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = displayRepositoryImpl;
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
                            Integer num = (Integer) obj;
                            DisplayRepositoryImpl$pendingDisplay$1$1 displayRepositoryImpl$pendingDisplay$1$1 = num != null ? new DisplayRepositoryImpl$pendingDisplay$1$1(num.intValue(), this.this$0) : null;
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(displayRepositoryImpl$pendingDisplay$1$1, anonymousClass1) == coroutineSingletons) {
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
                    Object objCollect = flowDebugLog4.collect(new AnonymousClass2(flowCollector, this), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            }, "pendingDisplay");
            final DisplayRepositoryImpl$special$$inlined$map$1 displayRepositoryImpl$special$$inlined$map$1 = this.displayChangeEvent;
            final Flow flow4 = new Flow() { // from class: com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$filter$1

                /* renamed from: com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$filter$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$filter$1$2$1, reason: invalid class name */
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
                            if (((Number) obj).intValue() == 0) {
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
                    Object objCollect = displayRepositoryImpl$special$$inlined$map$1.collect(new AnonymousClass2(flowCollector), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            };
            this.defaultDisplayOff = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$map$6

                /* renamed from: com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$map$6$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ DisplayRepositoryImpl this$0;

                    /* renamed from: com.android.app.displaylib.DisplayRepositoryImpl$special$$inlined$map$6$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, DisplayRepositoryImpl displayRepositoryImpl) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = displayRepositoryImpl;
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
                            ((Number) obj).intValue();
                            Boolean boolValueOf = Boolean.valueOf(((Display) this.this$0.defaultDisplay$delegate.getValue()).getState() == 1);
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
                    Object objCollect = flow4.collect(new AnonymousClass2(flowCollector, this), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                }
            });
        } catch (Throwable th) {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    public static Flow debugLog(Flow flow, String str) {
        if (!DEBUG) {
            return flow;
        }
        return new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowTracing.traceEmissionCount$default(FlowTracing.INSTANCE, flow, str), new DisplayRepositoryImpl$debugLog$$inlined$traceEach$default$1(new TraceStateLogger(str, false, false, true, 6, null), null));
    }

    @Override // com.android.app.displaylib.DisplayRepository
    public final Flow getDefaultDisplayOff() {
        return this.defaultDisplayOff;
    }

    @Override // com.android.app.displaylib.DisplayRepository
    public final Display getDisplay(int i) {
        Object next;
        Iterator it = ((Iterable) this.displays.$$delegate_0.getValue()).iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (((Display) next).getDisplayId() == i) {
                break;
            }
        }
        return (Display) next;
    }

    @Override // com.android.app.displaylib.DisplayRepository
    public final Flow getDisplayAdditionEvent() {
        return this.displayAdditionEvent;
    }

    @Override // com.android.app.displaylib.DisplayRepository
    public final Flow getDisplayChangeEvent() {
        return this.displayChangeEvent;
    }

    public final Display getDisplayFromDisplayManager(int i) {
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("DisplayRepository#getDisplay");
        }
        try {
            return this.displayManager.getDisplay(i);
        } finally {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        }
    }

    @Override // com.android.app.displaylib.DisplayRepository
    public final StateFlow getDisplayIds() {
        return this.displayIds;
    }

    @Override // com.android.app.displaylib.DisplayRepository
    public final Flow getDisplayRemovalEvent() {
        return this.displayRemovalEvent;
    }

    @Override // com.android.app.displaylib.DisplayRepository
    public final StateFlow getDisplays() {
        return this.displays;
    }

    @Override // com.android.app.displaylib.DisplayRepository
    public final Flow getPendingDisplay() {
        return this.pendingDisplay;
    }
}
