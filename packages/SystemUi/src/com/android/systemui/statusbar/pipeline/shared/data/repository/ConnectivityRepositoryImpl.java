package com.android.systemui.statusbar.pipeline.shared.data.repository;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.vcn.VcnTransportInfo;
import android.net.wifi.WifiInfo;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.pipeline.shared.ConnectivityInputLogger;
import com.android.systemui.statusbar.pipeline.shared.data.model.ConnectivitySlot;
import com.android.systemui.statusbar.pipeline.shared.data.model.ConnectivitySlots;
import com.android.systemui.statusbar.pipeline.shared.data.model.DefaultConnectionModel;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl;
import com.android.systemui.tuner.TunerService;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.nexus.video.VideoPlayer;
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
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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

    public ConnectivityRepositoryImpl(ConnectivityManager connectivityManager, ConnectivitySlots connectivitySlots, Context context, DumpManager dumpManager, ConnectivityInputLogger connectivityInputLogger, CoroutineScope coroutineScope, TunerService tunerService) {
        this.connectivityManager = connectivityManager;
        this.connectivitySlots = connectivitySlots;
        dumpManager.registerNormalDumpable("ConnectivityRepository", this);
        Set access$toSlotSet = Companion.access$toSlotSet(Companion, Arrays.asList(context.getResources().getStringArray(DEFAULT_HIDDEN_ICONS_RESOURCE)), connectivitySlots);
        this.defaultHiddenIcons = access$toSlotSet;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        ConnectivityRepositoryImpl$forceHiddenSlots$1 connectivityRepositoryImpl$forceHiddenSlots$1 = new ConnectivityRepositoryImpl$forceHiddenSlots$1(tunerService, connectivityInputLogger, this, null);
        conflatedCallbackFlow.getClass();
        Flow conflatedCallbackFlow2 = ConflatedCallbackFlow.conflatedCallbackFlow(connectivityRepositoryImpl$forceHiddenSlots$1);
        SharingStarted.Companion companion = SharingStarted.Companion;
        this.forceHiddenSlots = FlowKt.stateIn(conflatedCallbackFlow2, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), access$toSlotSet);
        final ReadonlySharedFlow shareIn = FlowKt.shareIn(ConflatedCallbackFlow.conflatedCallbackFlow(new ConnectivityRepositoryImpl$defaultNetworkCapabilities$1(this, connectivityInputLogger, null)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion), 0);
        this.defaultNetworkCapabilities = shareIn;
        FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1 flowKt__TransformKt$onEach$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$special$$inlined$map$1$2 */
            public final class C33432 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$special$$inlined$map$1$2", m277f = "ConnectivityRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C33432.this.emit(null, this);
                    }
                }

                public C33432(FlowCollector flowCollector) {
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
                                NetworkCapabilities networkCapabilities = (NetworkCapabilities) obj;
                                Integer num = null;
                                if (networkCapabilities != null) {
                                    VcnTransportInfo transportInfo = networkCapabilities.getTransportInfo();
                                    VcnTransportInfo vcnTransportInfo = transportInfo instanceof VcnTransportInfo ? transportInfo : null;
                                    Integer num2 = vcnTransportInfo != null ? new Integer(vcnTransportInfo.getSubId()) : null;
                                    if (num2 == null || num2.intValue() != -1) {
                                        num = num2;
                                    }
                                }
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
                Object collect = Flow.this.collect(new C33432(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), new ConnectivityRepositoryImpl$vcnSubId$2(connectivityInputLogger, null));
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        this.vcnSubId = FlowKt.stateIn(flowKt__TransformKt$onEach$$inlined$unsafeTransform$1, coroutineScope, startedEagerly, null);
        this.defaultConnections = FlowKt.stateIn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$special$$inlined$map$2

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            /* renamed from: com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$special$$inlined$map$2$2 */
            public final class C33442 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ ConnectivityRepositoryImpl this$0;

                /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
                @DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl$special$$inlined$map$2$2", m277f = "ConnectivityRepository.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_getLockScreenShortcut}, m279m = "emit")
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
                        this.label |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                        return C33442.this.emit(null, this);
                    }
                }

                public C33442(FlowCollector flowCollector, ConnectivityRepositoryImpl connectivityRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = connectivityRepositoryImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0034  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    DefaultConnectionModel defaultConnectionModel;
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
                                NetworkCapabilities networkCapabilities = (NetworkCapabilities) obj;
                                boolean z = false;
                                if (networkCapabilities == null) {
                                    defaultConnectionModel = new DefaultConnectionModel(new DefaultConnectionModel.Wifi(false), new DefaultConnectionModel.Mobile(false), new DefaultConnectionModel.CarrierMerged(false), new DefaultConnectionModel.Ethernet(false), new DefaultConnectionModel.BTTether(false), false);
                                } else {
                                    ConnectivityRepositoryImpl.Companion companion = ConnectivityRepositoryImpl.Companion;
                                    ConnectivityManager connectivityManager = this.this$0.connectivityManager;
                                    companion.getClass();
                                    WifiInfo mainOrUnderlyingWifiInfo = ConnectivityRepositoryImpl.Companion.getMainOrUnderlyingWifiInfo(networkCapabilities, connectivityManager);
                                    boolean z2 = networkCapabilities.hasTransport(1) || mainOrUnderlyingWifiInfo != null;
                                    boolean hasTransport = networkCapabilities.hasTransport(0);
                                    if (mainOrUnderlyingWifiInfo != null && mainOrUnderlyingWifiInfo.isCarrierMerged()) {
                                        z = true;
                                    }
                                    defaultConnectionModel = new DefaultConnectionModel(new DefaultConnectionModel.Wifi(z2), new DefaultConnectionModel.Mobile(hasTransport), new DefaultConnectionModel.CarrierMerged(z), new DefaultConnectionModel.Ethernet(networkCapabilities.hasTransport(3)), new DefaultConnectionModel.BTTether(networkCapabilities.hasTransport(2)), networkCapabilities.hasCapability(16));
                                }
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(defaultConnectionModel, anonymousClass1) == coroutineSingletons) {
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
                Object collect = Flow.this.collect(new C33442(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), new ConnectivityRepositoryImpl$defaultConnections$2(connectivityInputLogger, null)), coroutineScope, startedEagerly, new DefaultConnectionModel(null, null, null, null, null, false, 63, null));
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("defaultHiddenIcons=" + this.defaultHiddenIcons);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final Set access$toSlotSet(Companion companion, List list, ConnectivitySlots connectivitySlots) {
            companion.getClass();
            ArrayList arrayList = new ArrayList();
            for (Object obj : list) {
                if (!StringsKt__StringsJVMKt.isBlank((String) obj)) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = new ArrayList();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ConnectivitySlot connectivitySlot = (ConnectivitySlot) connectivitySlots.slotByName.get((String) it.next());
                if (connectivitySlot != null) {
                    arrayList2.add(connectivitySlot);
                }
            }
            return CollectionsKt___CollectionsKt.toSet(arrayList2);
        }

        public static WifiInfo getMainOrUnderlyingWifiInfo(NetworkCapabilities networkCapabilities, ConnectivityManager connectivityManager) {
            WifiInfo wifiInfo;
            WifiInfo mainWifiInfo = getMainWifiInfo(networkCapabilities);
            if (mainWifiInfo != null) {
                return mainWifiInfo;
            }
            if (!networkCapabilities.hasTransport(0)) {
                return mainWifiInfo;
            }
            List underlyingNetworks = networkCapabilities.getUnderlyingNetworks();
            if (underlyingNetworks == null) {
                return null;
            }
            Iterator it = underlyingNetworks.iterator();
            while (it.hasNext()) {
                NetworkCapabilities networkCapabilities2 = connectivityManager.getNetworkCapabilities((Network) it.next());
                if (networkCapabilities2 != null) {
                    ConnectivityRepositoryImpl.Companion.getClass();
                    wifiInfo = getMainWifiInfo(networkCapabilities2);
                } else {
                    wifiInfo = null;
                }
                if (wifiInfo != null) {
                    return wifiInfo;
                }
            }
            return null;
        }

        public static WifiInfo getMainWifiInfo(NetworkCapabilities networkCapabilities) {
            if (!(networkCapabilities.hasTransport(0) || networkCapabilities.hasTransport(1))) {
                return null;
            }
            VcnTransportInfo transportInfo = networkCapabilities.getTransportInfo();
            if (transportInfo instanceof VcnTransportInfo) {
                return transportInfo.getWifiInfo();
            }
            if (transportInfo instanceof WifiInfo) {
                return (WifiInfo) transportInfo;
            }
            return null;
        }

        /* renamed from: getDEFAULT_HIDDEN_ICONS_RESOURCE$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
        public static /* synthetic */ void m209x3a92d07a() {
        }

        /* renamed from: getHIDDEN_ICONS_TUNABLE_KEY$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
        public static /* synthetic */ void m210xe94ff5c9() {
        }
    }
}
