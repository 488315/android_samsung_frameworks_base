package com.android.systemui.statusbar.pipeline.wifi.data.repository.prod;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.util.Log;
import androidx.lifecycle.Lifecycle;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import com.android.wifitrackerlib.WifiEntry;
import com.android.wifitrackerlib.WifiPickerTracker;
import java.util.ArrayList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes3.dex */
final class WifiRepositoryImpl$wifiPickerTrackerInfo$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $applicationContext;
    final /* synthetic */ Ref$ObjectRef<WifiRepositoryImpl.WifiPickerTrackerInfo> $current;
    final /* synthetic */ WifiRepositoryImpl $this_run;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiRepositoryImpl$wifiPickerTrackerInfo$2$1(WifiRepositoryImpl wifiRepositoryImpl, Context context, Ref$ObjectRef<WifiRepositoryImpl.WifiPickerTrackerInfo> ref$ObjectRef, Continuation continuation) {
        super(2, continuation);
        this.$this_run = wifiRepositoryImpl;
        this.$applicationContext = context;
        this.$current = ref$ObjectRef;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WifiRepositoryImpl$wifiPickerTrackerInfo$2$1 wifiRepositoryImpl$wifiPickerTrackerInfo$2$1 = new WifiRepositoryImpl$wifiPickerTrackerInfo$2$1(this.$this_run, this.$applicationContext, this.$current, continuation);
        wifiRepositoryImpl$wifiPickerTrackerInfo$2$1.L$0 = obj;
        return wifiRepositoryImpl$wifiPickerTrackerInfo$2$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WifiRepositoryImpl$wifiPickerTrackerInfo$2$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final WifiRepositoryImpl wifiRepositoryImpl = this.$this_run;
            final Ref$ObjectRef<WifiRepositoryImpl.WifiPickerTrackerInfo> ref$ObjectRef = this.$current;
            WifiPickerTracker.WifiPickerTrackerCallback wifiPickerTrackerCallback = new WifiPickerTracker.WifiPickerTrackerCallback() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$wifiPickerTrackerInfo$2$1$callback$1
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r6v2, types: [T, com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$WifiPickerTrackerInfo, java.lang.Object] */
                public static void send$default(WifiRepositoryImpl$wifiPickerTrackerInfo$2$1$callback$1 wifiRepositoryImpl$wifiPickerTrackerInfo$2$1$callback$1, int i2, boolean z, WifiNetworkModel wifiNetworkModel, List list, int i3) {
                    if ((i3 & 1) != 0) {
                        i2 = ((WifiRepositoryImpl.WifiPickerTrackerInfo) ref$ObjectRef.element).state;
                    }
                    if ((i3 & 2) != 0) {
                        z = ((WifiRepositoryImpl.WifiPickerTrackerInfo) ref$ObjectRef.element).isDefault;
                    }
                    if ((i3 & 4) != 0) {
                        wifiNetworkModel = ((WifiRepositoryImpl.WifiPickerTrackerInfo) ref$ObjectRef.element).primaryNetwork;
                    }
                    if ((i3 & 8) != 0) {
                        list = ((WifiRepositoryImpl.WifiPickerTrackerInfo) ref$ObjectRef.element).secondaryNetworks;
                    }
                    wifiRepositoryImpl$wifiPickerTrackerInfo$2$1$callback$1.getClass();
                    ?? wifiPickerTrackerInfo = new WifiRepositoryImpl.WifiPickerTrackerInfo(i2, z, wifiNetworkModel, list);
                    ref$ObjectRef.element = wifiPickerTrackerInfo;
                    ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(wifiPickerTrackerInfo);
                }

                /* JADX WARN: Removed duplicated region for block: B:58:0x0122  */
                @Override // com.android.wifitrackerlib.WifiPickerTracker.WifiPickerTrackerCallback
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void onWifiEntriesChanged() {
                    WifiNetworkModel wifiNetworkModel;
                    String str;
                    WifiRepositoryImpl wifiRepositoryImpl2 = wifiRepositoryImpl;
                    WifiPickerTracker wifiPickerTracker = wifiRepositoryImpl2.wifiPickerTracker;
                    WifiEntry mergedCarrierEntry = wifiPickerTracker != null ? wifiPickerTracker.getMergedCarrierEntry() : null;
                    if (mergedCarrierEntry == null || !mergedCarrierEntry.isDefaultNetwork()) {
                        mergedCarrierEntry = wifiPickerTracker != null ? wifiPickerTracker.mConnectedWifiEntry : null;
                    }
                    wifiRepositoryImpl2.getClass();
                    LogLevel logLevel = LogLevel.DEBUG;
                    WifiRepositoryImpl$$ExternalSyntheticLambda3 wifiRepositoryImpl$$ExternalSyntheticLambda3 = new WifiRepositoryImpl$$ExternalSyntheticLambda3(4);
                    LogBuffer logBuffer = wifiRepositoryImpl2.inputLogger;
                    LogMessage logMessageObtain = logBuffer.obtain("WifiRepo", logLevel, wifiRepositoryImpl$$ExternalSyntheticLambda3, null);
                    ((LogMessageImpl) logMessageObtain).str1 = String.valueOf(mergedCarrierEntry);
                    logBuffer.commit(logMessageObtain);
                    WifiPickerTracker wifiPickerTracker2 = wifiRepositoryImpl2.wifiPickerTracker;
                    Iterable arrayList = wifiPickerTracker2 != null ? new ArrayList(wifiPickerTracker2.mActiveWifiEntries) : EmptyList.INSTANCE;
                    ArrayList arrayList2 = new ArrayList();
                    for (Object obj2 : arrayList) {
                        WifiEntry wifiEntry = (WifiEntry) obj2;
                        if (!Intrinsics.areEqual(wifiEntry, mergedCarrierEntry) && !wifiEntry.isPrimaryNetwork()) {
                            arrayList2.add(obj2);
                        }
                    }
                    ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList2, 10));
                    int size = arrayList2.size();
                    int i2 = 0;
                    while (i2 < size) {
                        Object obj3 = arrayList2.get(i2);
                        i2++;
                        WifiEntry wifiEntry2 = (WifiEntry) obj3;
                        wifiEntry2.getClass();
                        arrayList3.add(wifiRepositoryImpl2.toWifiNetworkModel(wifiEntry2));
                    }
                    if (mergedCarrierEntry == null) {
                        wifiRepositoryImpl2._wifiConnectivityTestReported.updateState(null, Boolean.FALSE);
                    }
                    WifiInfo connectionInfo = wifiRepositoryImpl2.wifiManager.getConnectionInfo();
                    LogLevel logLevel2 = LogLevel.DEBUG;
                    WifiRepositoryImpl$$ExternalSyntheticLambda3 wifiRepositoryImpl$$ExternalSyntheticLambda32 = new WifiRepositoryImpl$$ExternalSyntheticLambda3(1);
                    LogBuffer logBuffer2 = wifiRepositoryImpl2.inputLogger;
                    LogMessage logMessageObtain2 = logBuffer2.obtain("WifiRepo", logLevel2, wifiRepositoryImpl$$ExternalSyntheticLambda32, null);
                    ((LogMessageImpl) logMessageObtain2).str1 = String.valueOf(connectionInfo);
                    logBuffer2.commit(logMessageObtain2);
                    if (mergedCarrierEntry != null && mergedCarrierEntry.semIsEphemeral() && !mergedCarrierEntry.isDefaultNetwork() && connectionInfo != null) {
                        List<WifiConfiguration> privilegedConfiguredNetworks = wifiRepositoryImpl2.wifiManager.getPrivilegedConfiguredNetworks();
                        String ssid = connectionInfo.getSSID();
                        String bssid = connectionInfo.getBSSID();
                        for (WifiConfiguration wifiConfiguration : privilegedConfiguredNetworks) {
                            if (Intrinsics.areEqual(wifiConfiguration.SSID, ssid) && (str = wifiConfiguration.BSSID) != null && bssid != null && str.equals(bssid) && wifiConfiguration.fromWifiNetworkSpecifier) {
                                Log.d("WifiRepo", "Wifi Specifier : default network is mobile");
                                return;
                            }
                        }
                    }
                    if (mergedCarrierEntry == null) {
                        WifiRepositoryImpl.Companion.getClass();
                        wifiNetworkModel = WifiRepositoryImpl.WIFI_NETWORK_DEFAULT;
                    } else {
                        wifiNetworkModel = !mergedCarrierEntry.isPrimaryNetwork() ? WifiRepositoryImpl.WIFI_NETWORK_DEFAULT : wifiRepositoryImpl2.toWifiNetworkModel(mergedCarrierEntry);
                        if (wifiNetworkModel == null) {
                        }
                    }
                    send$default(this, 0, mergedCarrierEntry != null ? mergedCarrierEntry.isDefaultNetwork() : false, wifiNetworkModel, arrayList3, 1);
                }

                @Override // com.android.wifitrackerlib.BaseWifiTracker.BaseWifiTrackerCallback
                public final void onWifiStateChanged() {
                    Integer numValueOf;
                    WifiRepositoryImpl wifiRepositoryImpl2 = wifiRepositoryImpl;
                    WifiPickerTracker wifiPickerTracker = wifiRepositoryImpl2.wifiPickerTracker;
                    if (wifiPickerTracker != null) {
                        if (wifiPickerTracker.mWifiState == 4) {
                            wifiPickerTracker.mWifiState = wifiPickerTracker.mWifiManager.getWifiState();
                        }
                        numValueOf = Integer.valueOf(wifiPickerTracker.mWifiState);
                    } else {
                        numValueOf = null;
                    }
                    wifiRepositoryImpl2.getClass();
                    LogLevel logLevel = LogLevel.DEBUG;
                    WifiRepositoryImpl$$ExternalSyntheticLambda3 wifiRepositoryImpl$$ExternalSyntheticLambda3 = new WifiRepositoryImpl$$ExternalSyntheticLambda3(5);
                    LogBuffer logBuffer = wifiRepositoryImpl2.inputLogger;
                    LogMessage logMessageObtain = logBuffer.obtain("WifiRepo", logLevel, wifiRepositoryImpl$$ExternalSyntheticLambda3, null);
                    ((LogMessageImpl) logMessageObtain).int1 = numValueOf != null ? numValueOf.intValue() : -1;
                    logBuffer.commit(logMessageObtain);
                    send$default(this, numValueOf != null ? numValueOf.intValue() : 1, false, null, null, 14);
                }
            };
            WifiRepositoryImpl wifiRepositoryImpl2 = this.$this_run;
            WifiPickerTracker wifiPickerTrackerCreate = wifiRepositoryImpl2.wifiPickerTrackerFactory.create(wifiRepositoryImpl2.lifecycle, wifiPickerTrackerCallback, "WifiRepository", false);
            if (wifiPickerTrackerCreate != null) {
                wifiPickerTrackerCreate.mIsScanningDisabled = true;
                wifiPickerTrackerCreate.mInjector.mVerboseLoggingDisabledOverride = true;
            }
            wifiRepositoryImpl2.wifiPickerTracker = wifiPickerTrackerCreate;
            final WifiRepositoryImpl wifiRepositoryImpl3 = this.$this_run;
            wifiRepositoryImpl3.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$wifiPickerTrackerInfo$2$1.2
                @Override // java.lang.Runnable
                public final void run() {
                    wifiRepositoryImpl3.lifecycle.setCurrentState(Lifecycle.State.STARTED);
                }
            });
            final WifiRepositoryImpl wifiRepositoryImpl4 = this.$this_run;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$wifiPickerTrackerInfo$2$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    final WifiRepositoryImpl wifiRepositoryImpl5 = wifiRepositoryImpl4;
                    wifiRepositoryImpl5.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$wifiPickerTrackerInfo$2$1$3$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            wifiRepositoryImpl5.lifecycle.setCurrentState(Lifecycle.State.CREATED);
                        }
                    });
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
