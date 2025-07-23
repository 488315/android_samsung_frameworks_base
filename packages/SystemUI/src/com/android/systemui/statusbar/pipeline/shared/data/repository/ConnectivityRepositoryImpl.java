package com.android.systemui.statusbar.pipeline.shared.data.repository;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.TransportInfo;
import android.net.vcn.VcnTransportInfo;
import android.net.vcn.VcnUtils;
import android.net.wifi.WifiInfo;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.pipeline.shared.ConnectivityInputLogger;
import com.android.systemui.statusbar.pipeline.shared.data.model.ConnectivitySlot;
import com.android.systemui.statusbar.pipeline.shared.data.model.ConnectivitySlots;
import com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ConnectivityRepositoryImpl implements ConnectivityRepository, Dumpable {
    public static final Companion Companion = new Companion(null);
    public static final int DEFAULT_HIDDEN_ICONS_RESOURCE = R.array.config_statusBarIconsToExclude;
    public final ConnectivityManager connectivityManager;
    public final ConnectivitySlots connectivitySlots;
    public final ReadonlyStateFlow defaultConnections;
    public final Set defaultHiddenIcons;
    public final ReadonlySharedFlow defaultNetworkCapabilities;
    public final ReadonlyStateFlow forceHiddenSlots;
    public final ReadonlyStateFlow vcnSubId;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final Set access$toSlotSet(Companion companion, List list, ConnectivitySlots connectivitySlots) {
            companion.getClass();
            ArrayList arrayList = new ArrayList();
            for (Object obj : list) {
                if (!StringsKt__StringsKt.isBlank((String) obj)) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = new ArrayList();
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj2 = arrayList.get(i);
                i++;
                ConnectivitySlot connectivitySlot = (ConnectivitySlot) connectivitySlots.slotByName.get((String) obj2);
                if (connectivitySlot != null) {
                    arrayList2.add(connectivitySlot);
                }
            }
            return CollectionsKt___CollectionsKt.toSet(arrayList2);
        }

        public static WifiInfo getMainWifiInfo(NetworkCapabilities networkCapabilities, ConnectivityManager connectivityManager) {
            if (!networkCapabilities.hasTransport(0) && !networkCapabilities.hasTransport(1)) {
                return null;
            }
            TransportInfo transportInfo = networkCapabilities.getTransportInfo();
            if (transportInfo instanceof VcnTransportInfo) {
                return VcnUtils.getWifiInfoFromVcnCaps(connectivityManager, networkCapabilities);
            }
            if (transportInfo instanceof WifiInfo) {
                return (WifiInfo) transportInfo;
            }
            return null;
        }

        private Companion() {
        }

        public static /* synthetic */ void getDEFAULT_HIDDEN_ICONS_RESOURCE$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
        }

        public static /* synthetic */ void getHIDDEN_ICONS_TUNABLE_KEY$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
        }
    }

    public ConnectivityRepositoryImpl(ConnectivityManager connectivityManager, ConnectivitySlots connectivitySlots, Context context, DumpManager dumpManager, ConnectivityInputLogger connectivityInputLogger, CoroutineScope coroutineScope, TunerService tunerService) {
        this.connectivityManager = connectivityManager;
        this.connectivitySlots = connectivitySlots;
        dumpManager.registerNormalDumpable("ConnectivityRepository", this);
        Set access$toSlotSet = Companion.access$toSlotSet(Companion, Arrays.asList(context.getResources().getStringArray(DEFAULT_HIDDEN_ICONS_RESOURCE)), connectivitySlots);
        this.defaultHiddenIcons = access$toSlotSet;
        Flow conflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new ConnectivityRepositoryImpl$forceHiddenSlots$1(tunerService, connectivityInputLogger, this, null));
        SharingStarted.Companion companion = SharingStarted.Companion;
        this.forceHiddenSlots = FlowKt.stateIn(conflatedCallbackFlow, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), access$toSlotSet);
        final ReadonlySharedFlow shareIn = FlowKt.shareIn(FlowConflatedKt.conflatedCallbackFlow(new ConnectivityRepositoryImpl$defaultNetworkCapabilities$1(this, connectivityInputLogger, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 0);
        this.defaultNetworkCapabilities = shareIn;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ ConnectivityRepositoryImpl this$0;

                /* renamed from: com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, ConnectivityRepositoryImpl connectivityRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = connectivityRepositoryImpl;
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
                        boolean r0 = r6 instanceof com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$special$$inlined$map$1$2$1 r0 = (com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$special$$inlined$map$1$2$1 r0 = new com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L52
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        android.net.NetworkCapabilities r5 = (android.net.NetworkCapabilities) r5
                        r6 = 0
                        if (r5 == 0) goto L47
                        com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl r2 = r4.this$0
                        android.net.ConnectivityManager r2 = r2.connectivityManager
                        int r5 = android.net.vcn.VcnUtils.getSubIdFromVcnCaps(r2, r5)
                        r2 = -1
                        if (r5 == r2) goto L47
                        java.lang.Integer r6 = new java.lang.Integer
                        r6.<init>(r5)
                    L47:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L52
                        return r1
                    L52:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), new ConnectivityRepositoryImpl$vcnSubId$2(connectivityInputLogger, null));
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        this.vcnSubId = FlowKt.stateIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, coroutineScope, startedEagerly, null);
        this.defaultConnections = FlowKt.stateIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ ConnectivityRepositoryImpl this$0;

                /* renamed from: com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, ConnectivityRepositoryImpl connectivityRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = connectivityRepositoryImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r19, kotlin.coroutines.Continuation r20) {
                    /*
                        Method dump skipped, instructions count: 249
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), new ConnectivityRepositoryImpl$defaultConnections$2(connectivityInputLogger, null)), coroutineScope, startedEagerly, new DefaultConnectionModel(null, null, null, null, null, false, 63, null));
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("defaultHiddenIcons=" + this.defaultHiddenIcons);
    }
}
