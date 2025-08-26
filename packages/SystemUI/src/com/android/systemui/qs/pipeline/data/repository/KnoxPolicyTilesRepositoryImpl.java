package com.android.systemui.qs.pipeline.data.repository;

import android.content.res.Resources;
import android.os.Build;
import com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;

/* loaded from: classes2.dex */
public final class KnoxPolicyTilesRepositoryImpl implements KnoxPolicyTilesRepository {
    public static final Companion Companion = new Companion(null);
    public final ReadonlyStateFlow action;
    public final ReadonlyStateFlow knoxBlockedTiles;
    public final KnoxStateMonitor knoxStateMonitor;
    public final ReadonlyStateFlow knoxUnavailableTiles;
    public final Resources resources;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final List access$toTileList(Companion companion, List list, Resources resources) {
            companion.getClass();
            List<String> list2 = list;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            for (String str : list2) {
                TileSpec.Companion companion2 = TileSpec.Companion;
                TileNameConverter.INSTANCE.getClass();
                String tileSpec = TileNameConverter.toTileSpec(resources, str);
                companion2.getClass();
                arrayList.add(TileSpec.Companion.create(tileSpec));
            }
            return arrayList;
        }

        private Companion() {
        }
    }

    public interface KnoxAction {

        public final class BlockTile implements KnoxAction {
            public final List items;

            public BlockTile(List<String> list) {
                this.items = list;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof BlockTile) && Intrinsics.areEqual(this.items, ((BlockTile) obj).items);
            }

            public final int hashCode() {
                return this.items.hashCode();
            }

            public final String toString() {
                return LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0.m("BlockTile(items=", this.items, ")");
            }
        }

        public final class Initialize implements KnoxAction {
            public static final Initialize INSTANCE = new Initialize();

            private Initialize() {
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof Initialize);
            }

            public final int hashCode() {
                return 66440736;
            }

            public final String toString() {
                return "Initialize";
            }
        }

        public final class UnavailableTile implements KnoxAction {
            public final List items;

            public UnavailableTile(List<String> list) {
                this.items = list;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof UnavailableTile) && Intrinsics.areEqual(this.items, ((UnavailableTile) obj).items);
            }

            public final int hashCode() {
                return this.items.hashCode();
            }

            public final String toString() {
                return LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0.m("UnavailableTile(items=", this.items, ")");
            }
        }

        public final class UpdateEnable implements KnoxAction {
            public final boolean enable;

            public UpdateEnable(boolean z) {
                this.enable = z;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof UpdateEnable) && this.enable == ((UpdateEnable) obj).enable;
            }

            public final int hashCode() {
                return Boolean.hashCode(this.enable);
            }

            public final String toString() {
                return MoveResult$$ExternalSyntheticOutline0.m(new StringBuilder("UpdateEnable(enable="), this.enable, ")");
            }
        }
    }

    static {
        boolean z = Build.IS_DEBUGGABLE;
    }

    public KnoxPolicyTilesRepositoryImpl(KnoxStateMonitor knoxStateMonitor, Resources resources, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.knoxStateMonitor = knoxStateMonitor;
        this.resources = resources;
        Flow flowFlowOn = FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new KnoxPolicyTilesRepositoryImpl$action$1(this, null)), coroutineDispatcher);
        SharingStarted.Companion companion = SharingStarted.Companion;
        final ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flowFlowOn, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), KnoxAction.Initialize.INSTANCE);
        this.action = readonlyStateFlowStateIn;
        final Flow flow = new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$1

            /* renamed from: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$1$2$1, reason: invalid class name */
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
                        KnoxPolicyTilesRepositoryImpl.KnoxAction knoxAction = (KnoxPolicyTilesRepositoryImpl.KnoxAction) obj;
                        if ((knoxAction instanceof KnoxPolicyTilesRepositoryImpl.KnoxAction.BlockTile) || (knoxAction instanceof KnoxPolicyTilesRepositoryImpl.KnoxAction.Initialize)) {
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
                Object objCollect = readonlyStateFlowStateIn.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        Flow flowFlowOn2 = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$1

            /* renamed from: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ KnoxPolicyTilesRepositoryImpl this$0;

                /* renamed from: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, KnoxPolicyTilesRepositoryImpl knoxPolicyTilesRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = knoxPolicyTilesRepositoryImpl;
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
                        KnoxPolicyTilesRepositoryImpl.Companion companion = KnoxPolicyTilesRepositoryImpl.Companion;
                        KnoxPolicyTilesRepositoryImpl knoxPolicyTilesRepositoryImpl = this.this$0;
                        List quickPanelItems = ((KnoxStateMonitorImpl) knoxPolicyTilesRepositoryImpl.knoxStateMonitor).getQuickPanelItems();
                        if (quickPanelItems == null) {
                            quickPanelItems = EmptyList.INSTANCE;
                        }
                        List listAccess$toTileList = KnoxPolicyTilesRepositoryImpl.Companion.access$toTileList(KnoxPolicyTilesRepositoryImpl.Companion, quickPanelItems, knoxPolicyTilesRepositoryImpl.resources);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(listAccess$toTileList, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineDispatcher);
        StartedWhileSubscribed startedWhileSubscribedWhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        List quickPanelItems = ((KnoxStateMonitorImpl) knoxStateMonitor).getQuickPanelItems();
        quickPanelItems = quickPanelItems == null ? EmptyList.INSTANCE : quickPanelItems;
        Companion companion2 = Companion;
        this.knoxBlockedTiles = FlowKt.stateIn(flowFlowOn2, coroutineScope, startedWhileSubscribedWhileSubscribed$default, Companion.access$toTileList(companion2, quickPanelItems, resources));
        final Flow flow2 = new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$2

            /* renamed from: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$2$2$1, reason: invalid class name */
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
                        KnoxPolicyTilesRepositoryImpl.KnoxAction knoxAction = (KnoxPolicyTilesRepositoryImpl.KnoxAction) obj;
                        if ((knoxAction instanceof KnoxPolicyTilesRepositoryImpl.KnoxAction.UnavailableTile) || (knoxAction instanceof KnoxPolicyTilesRepositoryImpl.KnoxAction.Initialize)) {
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
                Object objCollect = readonlyStateFlowStateIn.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        Flow flowFlowOn3 = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$2

            /* renamed from: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ KnoxPolicyTilesRepositoryImpl this$0;

                /* renamed from: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, KnoxPolicyTilesRepositoryImpl knoxPolicyTilesRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = knoxPolicyTilesRepositoryImpl;
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
                        KnoxPolicyTilesRepositoryImpl.Companion companion = KnoxPolicyTilesRepositoryImpl.Companion;
                        KnoxPolicyTilesRepositoryImpl knoxPolicyTilesRepositoryImpl = this.this$0;
                        List quickPanelUnavailableButtons = ((KnoxStateMonitorImpl) knoxPolicyTilesRepositoryImpl.knoxStateMonitor).getQuickPanelUnavailableButtons();
                        if (quickPanelUnavailableButtons == null) {
                            quickPanelUnavailableButtons = EmptyList.INSTANCE;
                        }
                        List listAccess$toTileList = KnoxPolicyTilesRepositoryImpl.Companion.access$toTileList(KnoxPolicyTilesRepositoryImpl.Companion, quickPanelUnavailableButtons, knoxPolicyTilesRepositoryImpl.resources);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(listAccess$toTileList, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flow2.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineDispatcher);
        StartedWhileSubscribed startedWhileSubscribedWhileSubscribed$default2 = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        List quickPanelUnavailableButtons = ((KnoxStateMonitorImpl) knoxStateMonitor).getQuickPanelUnavailableButtons();
        this.knoxUnavailableTiles = FlowKt.stateIn(flowFlowOn3, coroutineScope, startedWhileSubscribedWhileSubscribed$default2, Companion.access$toTileList(companion2, quickPanelUnavailableButtons == null ? EmptyList.INSTANCE : quickPanelUnavailableButtons, resources));
        final Flow flow3 = new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$3

            /* renamed from: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$3$2$1, reason: invalid class name */
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
                        KnoxPolicyTilesRepositoryImpl.KnoxAction knoxAction = (KnoxPolicyTilesRepositoryImpl.KnoxAction) obj;
                        if ((knoxAction instanceof KnoxPolicyTilesRepositoryImpl.KnoxAction.UpdateEnable) || (knoxAction instanceof KnoxPolicyTilesRepositoryImpl.KnoxAction.Initialize)) {
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
                Object objCollect = readonlyStateFlowStateIn.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        FlowKt.stateIn(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$3

            /* renamed from: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ KnoxPolicyTilesRepositoryImpl this$0;

                /* renamed from: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$3$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, KnoxPolicyTilesRepositoryImpl knoxPolicyTilesRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = knoxPolicyTilesRepositoryImpl;
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
                        Boolean boolValueOf = Boolean.valueOf(((KnoxStateMonitorImpl) this.this$0.knoxStateMonitor).isBrightnessControllerEnabled());
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
                Object objCollect = flow3.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineDispatcher), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.valueOf(((KnoxStateMonitorImpl) knoxStateMonitor).isBrightnessControllerEnabled()));
    }
}
