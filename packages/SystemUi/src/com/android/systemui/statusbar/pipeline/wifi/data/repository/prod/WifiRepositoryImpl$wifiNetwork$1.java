package com.android.systemui.statusbar.pipeline.wifi.data.repository.prod;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import com.android.systemui.statusbar.pipeline.shared.LoggerHelper;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl;
import com.android.systemui.statusbar.pipeline.wifi.shared.WifiInputLogger;
import com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$wifiNetwork$1", m277f = "WifiRepositoryImpl.kt", m278l = {268}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class WifiRepositoryImpl$wifiNetwork$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ ConnectivityManager $connectivityManager;
    final /* synthetic */ WifiInputLogger $logger;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ WifiRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WifiRepositoryImpl$wifiNetwork$1(ConnectivityManager connectivityManager, WifiInputLogger wifiInputLogger, WifiRepositoryImpl wifiRepositoryImpl, Continuation<? super WifiRepositoryImpl$wifiNetwork$1> continuation) {
        super(2, continuation);
        this.$connectivityManager = connectivityManager;
        this.$logger = wifiInputLogger;
        this.this$0 = wifiRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WifiRepositoryImpl$wifiNetwork$1 wifiRepositoryImpl$wifiNetwork$1 = new WifiRepositoryImpl$wifiNetwork$1(this.$connectivityManager, this.$logger, this.this$0, continuation);
        wifiRepositoryImpl$wifiNetwork$1.L$0 = obj;
        return wifiRepositoryImpl$wifiNetwork$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WifiRepositoryImpl$wifiNetwork$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v2, types: [T, com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel$Inactive] */
    /* JADX WARN: Type inference failed for: r1v3, types: [android.net.ConnectivityManager$NetworkCallback, com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$wifiNetwork$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
            WifiRepositoryImpl.Companion.getClass();
            ref$ObjectRef.element = WifiRepositoryImpl.WIFI_NETWORK_DEFAULT;
            final WifiInputLogger wifiInputLogger = this.$logger;
            final WifiRepositoryImpl wifiRepositoryImpl = this.this$0;
            final ConnectivityManager connectivityManager = this.$connectivityManager;
            final ?? r1 = new ConnectivityManager.NetworkCallback() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$wifiNetwork$1$callback$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Removed duplicated region for block: B:81:0x0141  */
                /* JADX WARN: Removed duplicated region for block: B:87:0x016b  */
                @Override // android.net.ConnectivityManager.NetworkCallback
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                    boolean z;
                    boolean z2;
                    String str;
                    WifiInputLogger wifiInputLogger2 = WifiInputLogger.this;
                    wifiInputLogger2.getClass();
                    LoggerHelper.INSTANCE.getClass();
                    LoggerHelper.logOnCapabilitiesChanged(wifiInputLogger2.buffer, "WifiInputLog", network, networkCapabilities, false);
                    wifiRepositoryImpl.wifiNetworkChangeEvents.tryEmit(Unit.INSTANCE);
                    ConnectivityRepositoryImpl.Companion companion = ConnectivityRepositoryImpl.Companion;
                    ConnectivityManager connectivityManager2 = connectivityManager;
                    companion.getClass();
                    WifiInfo mainOrUnderlyingWifiInfo = ConnectivityRepositoryImpl.Companion.getMainOrUnderlyingWifiInfo(networkCapabilities, connectivityManager2);
                    if (mainOrUnderlyingWifiInfo != null && mainOrUnderlyingWifiInfo.isEphemeral() && !((Boolean) wifiRepositoryImpl.isWifiDefault.getValue()).booleanValue()) {
                        List privilegedConfiguredNetworks = wifiRepositoryImpl.wifiManager.getPrivilegedConfiguredNetworks();
                        String ssid = mainOrUnderlyingWifiInfo.getSSID();
                        String bssid = mainOrUnderlyingWifiInfo.getBSSID();
                        Iterator it = privilegedConfiguredNetworks.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                z2 = false;
                                break;
                            }
                            WifiConfiguration wifiConfiguration = (WifiConfiguration) it.next();
                            if (Intrinsics.areEqual(wifiConfiguration.SSID, ssid) && (str = wifiConfiguration.BSSID) != null && bssid != null && Intrinsics.areEqual(str, bssid) && wifiConfiguration.fromWifiNetworkSpecifier) {
                                z2 = true;
                                break;
                            }
                        }
                        if (z2) {
                            Log.d(wifiRepositoryImpl.TAG, "Wifi Specifier : default network is mobile");
                            return;
                        }
                    }
                    if (mainOrUnderlyingWifiInfo != null && mainOrUnderlyingWifiInfo.isPrimary()) {
                        WifiRepositoryImpl.Companion companion2 = WifiRepositoryImpl.Companion;
                        WifiRepositoryImpl wifiRepositoryImpl2 = wifiRepositoryImpl;
                        WifiManager wifiManager = wifiRepositoryImpl2.wifiManager;
                        int intValue = ((Number) wifiRepositoryImpl2._wifiReceivedInetCondition.getValue()).intValue();
                        WifiRepositoryImpl wifiRepositoryImpl3 = wifiRepositoryImpl;
                        wifiRepositoryImpl3.getClass();
                        if (mainOrUnderlyingWifiInfo != null) {
                            List<ScanResult.InformationElement> informationElements = mainOrUnderlyingWifiInfo.getInformationElements();
                            String str2 = wifiRepositoryImpl3.TAG;
                            if (informationElements != null) {
                                boolean z3 = false;
                                for (ScanResult.InformationElement informationElement : informationElements) {
                                    int id = informationElement.getId();
                                    if (id == wifiRepositoryImpl3.EID_VSA) {
                                        try {
                                            int i2 = wifiRepositoryImpl3.BAND_5_GHZ_START_FREQ;
                                            int i3 = wifiRepositoryImpl3.BAND_5_GHZ_END_FREQ;
                                            int frequency = mainOrUnderlyingWifiInfo.getFrequency();
                                            if (i2 <= frequency && frequency <= i3) {
                                                ByteBuffer order = informationElement.getBytes().order(ByteOrder.LITTLE_ENDIAN);
                                                if (order.getInt() == wifiRepositoryImpl3.KTT_VSI_VSD_OUI) {
                                                    int remaining = order.remaining();
                                                    byte[] bArr = new byte[remaining];
                                                    order.get(bArr);
                                                    if (remaining > 24 && bArr[24] == wifiRepositoryImpl3.KT_VSI_VSD_26) {
                                                        z3 = true;
                                                    }
                                                }
                                            }
                                        } catch (BufferUnderflowException unused) {
                                            Log.e(str2, mainOrUnderlyingWifiInfo.getSSID() + " BufferUnderflowException ie:" + id);
                                        }
                                    }
                                }
                                z = z3;
                                companion2.getClass();
                                T invalid = !mainOrUnderlyingWifiInfo.isCarrierMerged() ? mainOrUnderlyingWifiInfo.getSubscriptionId() == -1 ? new WifiNetworkModel.Invalid("Wifi network was carrier merged but had invalid sub ID") : new WifiNetworkModel.CarrierMerged(network.getNetId(), mainOrUnderlyingWifiInfo.getSubscriptionId(), wifiManager.calculateSignalLevel(mainOrUnderlyingWifiInfo.getRssi()), wifiManager.getMaxSignalLevel() + 1) : new WifiNetworkModel.Active(network.getNetId(), networkCapabilities.hasCapability(16), wifiManager.calculateSignalLevel(mainOrUnderlyingWifiInfo.getRssi()), mainOrUnderlyingWifiInfo.getSSID(), mainOrUnderlyingWifiInfo.isPasspointAp(), mainOrUnderlyingWifiInfo.isOsuAp(), mainOrUnderlyingWifiInfo.getPasspointProviderFriendlyName(), mainOrUnderlyingWifiInfo.getWifiStandard(), mainOrUnderlyingWifiInfo.getFrequency(), intValue, mainOrUnderlyingWifiInfo.getNetworkId(), z);
                                ref$ObjectRef.element = invalid;
                                ((ChannelCoroutine) producerScope).mo2872trySendJP2dKIU(invalid);
                            }
                            Log.d(str2, "not exist current network's InformationElement");
                        }
                        z = false;
                        companion2.getClass();
                        if (!mainOrUnderlyingWifiInfo.isCarrierMerged()) {
                        }
                        ref$ObjectRef.element = invalid;
                        ((ChannelCoroutine) producerScope).mo2872trySendJP2dKIU(invalid);
                    }
                }

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r4v2, types: [T, com.android.systemui.statusbar.pipeline.wifi.shared.model.WifiNetworkModel$Inactive, java.lang.Object] */
                @Override // android.net.ConnectivityManager.NetworkCallback
                public final void onLost(Network network) {
                    WifiInputLogger wifiInputLogger2 = WifiInputLogger.this;
                    wifiInputLogger2.getClass();
                    LoggerHelper.INSTANCE.getClass();
                    LoggerHelper.logOnLost(wifiInputLogger2.buffer, "WifiInputLog", network, false);
                    wifiRepositoryImpl.wifiNetworkChangeEvents.tryEmit(Unit.INSTANCE);
                    WifiNetworkModel wifiNetworkModel = (WifiNetworkModel) ref$ObjectRef.element;
                    if (((wifiNetworkModel instanceof WifiNetworkModel.Active) && ((WifiNetworkModel.Active) wifiNetworkModel).networkId == network.getNetId()) || ((wifiNetworkModel instanceof WifiNetworkModel.CarrierMerged) && ((WifiNetworkModel.CarrierMerged) wifiNetworkModel).networkId == network.getNetId())) {
                        ?? r4 = WifiNetworkModel.Inactive.INSTANCE;
                        ref$ObjectRef.element = r4;
                        wifiRepositoryImpl._wifiConnectivityTestReported.setValue(Boolean.FALSE);
                        ((ChannelCoroutine) producerScope).mo2872trySendJP2dKIU(r4);
                    }
                }
            };
            this.$connectivityManager.registerNetworkCallback(WifiRepositoryImpl.WIFI_NETWORK_CALLBACK_REQUEST, (ConnectivityManager.NetworkCallback) r1);
            final ConnectivityManager connectivityManager2 = this.$connectivityManager;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl$wifiNetwork$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    connectivityManager2.unregisterNetworkCallback(r1);
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
