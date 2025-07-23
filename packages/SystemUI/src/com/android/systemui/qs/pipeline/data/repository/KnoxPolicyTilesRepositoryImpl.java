package com.android.systemui.qs.pipeline.data.repository;

import android.content.res.Resources;
import android.os.Build;
import com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.qs.pipeline.shared.TileSpec;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.List;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class KnoxPolicyTilesRepositoryImpl implements KnoxPolicyTilesRepository {
    public static final Companion Companion = new Companion(null);
    public final ReadonlyStateFlow action;
    public final ReadonlyStateFlow knoxBlockedTiles;
    public final KnoxStateMonitor knoxStateMonitor;
    public final ReadonlyStateFlow knoxUnavailableTiles;
    public final Resources resources;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface KnoxAction {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        Flow flowOn = FlowKt.flowOn(FlowConflatedKt.conflatedCallbackFlow(new KnoxPolicyTilesRepositoryImpl$action$1(this, null)), coroutineDispatcher);
        SharingStarted.Companion companion = SharingStarted.Companion;
        final ReadonlyStateFlow stateIn = FlowKt.stateIn(flowOn, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), KnoxAction.Initialize.INSTANCE);
        this.action = stateIn;
        final Flow flow = new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        boolean r0 = r6 instanceof com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$1$2$1
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
                        r6 = r5
                        com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$KnoxAction r6 = (com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl.KnoxAction) r6
                        boolean r2 = r6 instanceof com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl.KnoxAction.BlockTile
                        if (r2 != 0) goto L3d
                        boolean r6 = r6 instanceof com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl.KnoxAction.Initialize
                        if (r6 == 0) goto L48
                    L3d:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        Flow flowOn2 = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        boolean r0 = r6 instanceof com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L57
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$KnoxAction r5 = (com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl.KnoxAction) r5
                        com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$Companion r5 = com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl.Companion
                        com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl r5 = r4.this$0
                        com.android.systemui.knox.KnoxStateMonitor r6 = r5.knoxStateMonitor
                        com.android.systemui.knox.KnoxStateMonitorImpl r6 = (com.android.systemui.knox.KnoxStateMonitorImpl) r6
                        java.util.List r6 = r6.getQuickPanelItems()
                        if (r6 != 0) goto L44
                        kotlin.collections.EmptyList r6 = kotlin.collections.EmptyList.INSTANCE
                    L44:
                        com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$Companion r2 = com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl.Companion
                        android.content.res.Resources r5 = r5.resources
                        java.util.List r5 = com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl.Companion.access$toTileList(r2, r6, r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L57
                        return r1
                    L57:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineDispatcher);
        StartedWhileSubscribed WhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        List quickPanelItems = ((KnoxStateMonitorImpl) knoxStateMonitor).getQuickPanelItems();
        quickPanelItems = quickPanelItems == null ? EmptyList.INSTANCE : quickPanelItems;
        Companion companion2 = Companion;
        this.knoxBlockedTiles = FlowKt.stateIn(flowOn2, coroutineScope, WhileSubscribed$default, Companion.access$toTileList(companion2, quickPanelItems, resources));
        final Flow flow2 = new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        boolean r0 = r6 instanceof com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$2$2$1 r0 = (com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$2$2$1 r0 = new com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$2$2$1
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
                        r6 = r5
                        com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$KnoxAction r6 = (com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl.KnoxAction) r6
                        boolean r2 = r6 instanceof com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl.KnoxAction.UnavailableTile
                        if (r2 != 0) goto L3d
                        boolean r6 = r6 instanceof com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl.KnoxAction.Initialize
                        if (r6 == 0) goto L48
                    L3d:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        Flow flowOn3 = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        boolean r0 = r6 instanceof com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$2$2$1 r0 = (com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$2$2$1 r0 = new com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L57
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$KnoxAction r5 = (com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl.KnoxAction) r5
                        com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$Companion r5 = com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl.Companion
                        com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl r5 = r4.this$0
                        com.android.systemui.knox.KnoxStateMonitor r6 = r5.knoxStateMonitor
                        com.android.systemui.knox.KnoxStateMonitorImpl r6 = (com.android.systemui.knox.KnoxStateMonitorImpl) r6
                        java.util.List r6 = r6.getQuickPanelUnavailableButtons()
                        if (r6 != 0) goto L44
                        kotlin.collections.EmptyList r6 = kotlin.collections.EmptyList.INSTANCE
                    L44:
                        com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$Companion r2 = com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl.Companion
                        android.content.res.Resources r5 = r5.resources
                        java.util.List r5 = com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl.Companion.access$toTileList(r2, r6, r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L57
                        return r1
                    L57:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineDispatcher);
        StartedWhileSubscribed WhileSubscribed$default2 = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        List quickPanelUnavailableButtons = ((KnoxStateMonitorImpl) knoxStateMonitor).getQuickPanelUnavailableButtons();
        this.knoxUnavailableTiles = FlowKt.stateIn(flowOn3, coroutineScope, WhileSubscribed$default2, Companion.access$toTileList(companion2, quickPanelUnavailableButtons == null ? EmptyList.INSTANCE : quickPanelUnavailableButtons, resources));
        final Flow flow3 = new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        boolean r0 = r6 instanceof com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$3$2$1 r0 = (com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$3$2$1 r0 = new com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$3$2$1
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
                        r6 = r5
                        com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$KnoxAction r6 = (com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl.KnoxAction) r6
                        boolean r2 = r6 instanceof com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl.KnoxAction.UpdateEnable
                        if (r2 != 0) goto L3d
                        boolean r6 = r6 instanceof com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl.KnoxAction.Initialize
                        if (r6 == 0) goto L48
                    L3d:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$filter$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        FlowKt.stateIn(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        boolean r0 = r6 instanceof com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$3$2$1 r0 = (com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$3$2$1 r0 = new com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$3$2$1
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
                        com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$KnoxAction r5 = (com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl.KnoxAction) r5
                        com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl r5 = r4.this$0
                        com.android.systemui.knox.KnoxStateMonitor r5 = r5.knoxStateMonitor
                        com.android.systemui.knox.KnoxStateMonitorImpl r5 = (com.android.systemui.knox.KnoxStateMonitorImpl) r5
                        boolean r5 = r5.isBrightnessControllerEnabled()
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4d
                        return r1
                    L4d:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.pipeline.data.repository.KnoxPolicyTilesRepositoryImpl$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineDispatcher), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Boolean.valueOf(((KnoxStateMonitorImpl) knoxStateMonitor).isBrightnessControllerEnabled()));
    }
}
