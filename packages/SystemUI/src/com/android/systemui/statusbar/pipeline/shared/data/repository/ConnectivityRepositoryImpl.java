package com.android.systemui.statusbar.pipeline.shared.data.repository;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
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
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.ResultKt;
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
        Set setAccess$toSlotSet = Companion.access$toSlotSet(Companion, Arrays.asList(context.getResources().getStringArray(DEFAULT_HIDDEN_ICONS_RESOURCE)), connectivitySlots);
        this.defaultHiddenIcons = setAccess$toSlotSet;
        Flow flowConflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new ConnectivityRepositoryImpl$forceHiddenSlots$1(tunerService, connectivityInputLogger, this, null));
        SharingStarted.Companion companion = SharingStarted.Companion;
        this.forceHiddenSlots = FlowKt.stateIn(flowConflatedCallbackFlow, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), setAccess$toSlotSet);
        final ReadonlySharedFlow readonlySharedFlowShareIn = FlowKt.shareIn(FlowConflatedKt.conflatedCallbackFlow(new ConnectivityRepositoryImpl$defaultNetworkCapabilities$1(this, connectivityInputLogger, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), 0);
        this.defaultNetworkCapabilities = readonlySharedFlowShareIn;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$special$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int subIdFromVcnCaps;
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
                        NetworkCapabilities networkCapabilities = (NetworkCapabilities) obj;
                        Integer num = null;
                        if (networkCapabilities != null && (subIdFromVcnCaps = VcnUtils.getSubIdFromVcnCaps(this.this$0.connectivityManager, networkCapabilities)) != -1) {
                            num = new Integer(subIdFromVcnCaps);
                        }
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
                Object objCollect = readonlySharedFlowShareIn.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), new ConnectivityRepositoryImpl$vcnSubId$2(connectivityInputLogger, null));
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        this.vcnSubId = FlowKt.stateIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, coroutineScope, startedEagerly, null);
        this.defaultConnections = FlowKt.stateIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$special$$inlined$map$2

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    DefaultConnectionModel defaultConnectionModel;
                    WifiInfo mainWifiInfo;
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
                        NetworkCapabilities networkCapabilities = (NetworkCapabilities) obj;
                        boolean z = false;
                        if (networkCapabilities == null) {
                            defaultConnectionModel = new DefaultConnectionModel(new DefaultConnectionModel.Wifi(false), new DefaultConnectionModel.Mobile(false), new DefaultConnectionModel.CarrierMerged(false), new DefaultConnectionModel.Ethernet(false), new DefaultConnectionModel.BTTether(false), false);
                        } else {
                            ConnectivityRepositoryImpl.Companion companion = ConnectivityRepositoryImpl.Companion;
                            ConnectivityManager connectivityManager = this.this$0.connectivityManager;
                            companion.getClass();
                            WifiInfo mainWifiInfo2 = ConnectivityRepositoryImpl.Companion.getMainWifiInfo(networkCapabilities, connectivityManager);
                            if (mainWifiInfo2 == null) {
                                networkCapabilities.hasTransport(0);
                                List underlyingNetworks = networkCapabilities.getUnderlyingNetworks();
                                if (underlyingNetworks != null) {
                                    Iterator it = underlyingNetworks.iterator();
                                    while (it.hasNext()) {
                                        NetworkCapabilities networkCapabilities2 = connectivityManager.getNetworkCapabilities((Network) it.next());
                                        if (networkCapabilities2 != null) {
                                            ConnectivityRepositoryImpl.Companion.getClass();
                                            mainWifiInfo = ConnectivityRepositoryImpl.Companion.getMainWifiInfo(networkCapabilities2, connectivityManager);
                                        } else {
                                            mainWifiInfo = null;
                                        }
                                        if (mainWifiInfo != null) {
                                            mainWifiInfo2 = mainWifiInfo;
                                            break;
                                        }
                                    }
                                    mainWifiInfo2 = null;
                                } else {
                                    mainWifiInfo2 = null;
                                }
                            }
                            boolean z2 = networkCapabilities.hasTransport(1) || mainWifiInfo2 != null;
                            boolean zHasTransport = networkCapabilities.hasTransport(0);
                            if (mainWifiInfo2 != null && mainWifiInfo2.isCarrierMerged()) {
                                z = true;
                            }
                            defaultConnectionModel = new DefaultConnectionModel(new DefaultConnectionModel.Wifi(z2), new DefaultConnectionModel.Mobile(zHasTransport), new DefaultConnectionModel.CarrierMerged(z), new DefaultConnectionModel.Ethernet(networkCapabilities.hasTransport(3)), new DefaultConnectionModel.BTTether(networkCapabilities.hasTransport(2)), networkCapabilities.hasCapability(16));
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(defaultConnectionModel, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = readonlySharedFlowShareIn.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), new ConnectivityRepositoryImpl$defaultConnections$2(connectivityInputLogger, null)), coroutineScope, startedEagerly, new DefaultConnectionModel(null, null, null, null, null, false, 63, null));
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("defaultHiddenIcons=" + this.defaultHiddenIcons);
    }
}
