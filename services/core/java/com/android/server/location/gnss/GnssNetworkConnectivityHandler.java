package com.android.server.location.gnss;

import android.content.Context;
import android.database.Cursor;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.location.flags.Flags;
import android.net.ConnectivityManager;
import android.net.LinkAddress;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.provider.Telephony;
import android.telephony.PhoneStateListener;
import android.telephony.PreciseCallState;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.internal.location.GpsNetInitiatedHandler;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.location.gnss.sec.CarrierConfig;
import com.android.server.location.gnss.sec.GnssHalStatus;
import com.android.server.location.gnss.sec.GnssVendorConfig;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

class GnssNetworkConnectivityHandler {
    public InetAddress mAGpsDataConnectionIpAddr;
    public int mAGpsDataConnectionState;
    public int mAGpsType;
    public final CarrierConfig mCarrierConfig;
    public final ConnectivityManager mConnMgr;
    public final Context mContext;
    public final AnonymousClass3 mEmergencyConnectivityCallback;
    public final GnssLocationProvider$$ExternalSyntheticLambda9 mGnssNetworkListener;
    public final GnssVendorConfig mGnssVendorConfig;
    public final Handler mHandler;
    public boolean mIsConnectEmergencyNetwork;
    public AnonymousClass2 mNetworkConnectivityCallback;
    public final GpsNetInitiatedHandler mNiHandler;
    public final AnonymousClass1 mOnSubscriptionsChangeListener;
    public HashMap mPhoneStateListeners;
    public final Object mSuplConnectionReleaseOnTimeoutToken;
    public AnonymousClass3 mSuplConnectivityCallback;
    public final PowerManager.WakeLock mWakeLock;
    public static final boolean VERBOSE = Log.isLoggable("GnssNetworkConnectivityHandler", 2);
    public static final long SUPL_CONNECTION_TIMEOUT_MILLIS = TimeUnit.MINUTES.toMillis(1);
    public final HashMap mAvailableNetworkAttributes = new HashMap(5);
    public int mActiveSubId = -1;

    /* renamed from: com.android.server.location.gnss.GnssNetworkConnectivityHandler$3, reason: invalid class name */
    public final class AnonymousClass3 extends ConnectivityManager.NetworkCallback {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ GnssNetworkConnectivityHandler this$0;

        public /* synthetic */ AnonymousClass3(GnssNetworkConnectivityHandler gnssNetworkConnectivityHandler, int i) {
            this.$r8$classId = i;
            this.this$0 = gnssNetworkConnectivityHandler;
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(Network network) {
            switch (this.$r8$classId) {
                case 1:
                    Log.d("GnssNetworkConnectivityHandler", "EmergencyNetwork : onAvailable");
                    this.this$0.mIsConnectEmergencyNetwork = true;
                    break;
                default:
                    super.onAvailable(network);
                    break;
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
            switch (this.$r8$classId) {
                case 0:
                    Log.d("GnssNetworkConnectivityHandler", "SUPL network connection available.");
                    GnssNetworkConnectivityHandler.m640$$Nest$mhandleSuplConnectionAvailable(this.this$0, network, linkProperties);
                    break;
                default:
                    super.onLinkPropertiesChanged(network, linkProperties);
                    break;
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLost(Network network) {
            switch (this.$r8$classId) {
                case 0:
                    Log.i("GnssNetworkConnectivityHandler", "SUPL network connection lost.");
                    GnssNetworkConnectivityHandler gnssNetworkConnectivityHandler = this.this$0;
                    boolean z = GnssNetworkConnectivityHandler.VERBOSE;
                    gnssNetworkConnectivityHandler.handleReleaseSuplConnection(2);
                    break;
                default:
                    Log.d("GnssNetworkConnectivityHandler", "EmergencyNetwork : onLost");
                    this.this$0.mIsConnectEmergencyNetwork = false;
                    break;
            }
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onUnavailable() {
            switch (this.$r8$classId) {
                case 0:
                    Log.i("GnssNetworkConnectivityHandler", "SUPL network connection request timed out.");
                    GnssNetworkConnectivityHandler gnssNetworkConnectivityHandler = this.this$0;
                    boolean z = GnssNetworkConnectivityHandler.VERBOSE;
                    gnssNetworkConnectivityHandler.handleReleaseSuplConnection(5);
                    break;
                default:
                    Log.d("GnssNetworkConnectivityHandler", "mEmergencyNetwork : onUnavailable");
                    this.this$0.mIsConnectEmergencyNetwork = false;
                    break;
            }
        }
    }

    public final class NetworkAttributes {
        public String mApn;
        public NetworkCapabilities mCapabilities;
        public int mType;
    }

    public final class SubIdPhoneStateListener extends PhoneStateListener {
        public final Integer mSubId;

        public SubIdPhoneStateListener(Integer num) {
            this.mSubId = num;
        }

        public final void onPreciseCallStateChanged(PreciseCallState preciseCallState) {
            if (1 == preciseCallState.getForegroundCallState() || 3 == preciseCallState.getForegroundCallState()) {
                GnssNetworkConnectivityHandler.this.mActiveSubId = this.mSubId.intValue();
                GestureWakeup$$ExternalSyntheticOutline0.m(new StringBuilder("mActiveSubId: "), GnssNetworkConnectivityHandler.this.mActiveSubId, "GnssNetworkConnectivityHandler");
            }
        }
    }

    /* renamed from: -$$Nest$mhandleSuplConnectionAvailable, reason: not valid java name */
    public static void m640$$Nest$mhandleSuplConnectionAvailable(GnssNetworkConnectivityHandler gnssNetworkConnectivityHandler, Network network, LinkProperties linkProperties) {
        CarrierConfig.Carrier carrier;
        int i;
        TelephonyManager createForSubscriptionId;
        NetworkInfo networkInfo = gnssNetworkConnectivityHandler.mConnMgr.getNetworkInfo(network);
        String extraInfo = networkInfo != null ? networkInfo.getExtraInfo() : null;
        Log.d("GnssNetworkConnectivityHandler", String.format("handleSuplConnectionAvailable: state=%s, suplNetwork=%s, info=%s", gnssNetworkConnectivityHandler.agpsDataConnStateAsString(), network, networkInfo));
        if (gnssNetworkConnectivityHandler.mAGpsDataConnectionState == 1) {
            if (extraInfo == null) {
                extraInfo = "dummy-apn";
            }
            InetAddress inetAddress = gnssNetworkConnectivityHandler.mAGpsDataConnectionIpAddr;
            int i2 = 3;
            if (inetAddress != null) {
                if (gnssNetworkConnectivityHandler.mConnMgr.requestRouteToHostAddress(3, inetAddress)) {
                    Log.d("GnssNetworkConnectivityHandler", "Successfully requested route to host: " + gnssNetworkConnectivityHandler.mAGpsDataConnectionIpAddr);
                } else {
                    Log.e("GnssNetworkConnectivityHandler", "Error requesting route to host: " + gnssNetworkConnectivityHandler.mAGpsDataConnectionIpAddr);
                }
            }
            gnssNetworkConnectivityHandler.getLinkIpType(linkProperties);
            gnssNetworkConnectivityHandler.mGnssVendorConfig.getClass();
            if (GnssVendorConfig.isIzatServiceEnabled() && ((carrier = gnssNetworkConnectivityHandler.mCarrierConfig.mCarrier) == CarrierConfig.Carrier.USA_TMO || carrier == CarrierConfig.Carrier.USA_XAA || carrier == CarrierConfig.Carrier.USA_XAG || carrier == CarrierConfig.Carrier.USA_TFN || carrier == CarrierConfig.Carrier.USA_TFO || carrier == CarrierConfig.Carrier.USA_TMK || carrier == CarrierConfig.Carrier.USA_DSH)) {
                Log.d("GnssNetworkConnectivityHandler", "Set IpType for TMB SUPL connection");
                Handler handler = gnssNetworkConnectivityHandler.mHandler;
                if (handler == null || Looper.myLooper() != handler.getLooper()) {
                    throw new IllegalStateException("This method must run on the Handler thread.");
                }
                TelephonyManager telephonyManager = (TelephonyManager) gnssNetworkConnectivityHandler.mContext.getSystemService("phone");
                if (gnssNetworkConnectivityHandler.mNiHandler.getInEmergency() && (i = gnssNetworkConnectivityHandler.mActiveSubId) >= 0 && (createForSubscriptionId = telephonyManager.createForSubscriptionId(i)) != null) {
                    telephonyManager = createForSubscriptionId;
                }
                ServiceState serviceState = telephonyManager.getServiceState();
                try {
                    Cursor query = gnssNetworkConnectivityHandler.mContext.getContentResolver().query(Telephony.Carriers.CONTENT_URI, new String[]{(serviceState == null || !serviceState.getDataRoamingFromRegistration()) ? "protocol" : "roaming_protocol"}, (telephonyManager.getNetworkType() == 0 && 3 == gnssNetworkConnectivityHandler.mAGpsType) ? XmlUtils$$ExternalSyntheticOutline0.m("type like '%emergency%' and apn = '", extraInfo, "' and carrier_enabled = 1") : XmlUtils$$ExternalSyntheticOutline0.m("current = 1 and apn = '", extraInfo, "' and carrier_enabled = 1"), null, "name ASC");
                    if (query != null) {
                        try {
                            if (query.moveToFirst()) {
                                int translateToApnIpTypeSec = translateToApnIpTypeSec(query.getString(0), extraInfo);
                                query.close();
                                i2 = translateToApnIpTypeSec;
                            }
                        } finally {
                        }
                    }
                    Log.e("GnssNetworkConnectivityHandler", "No entry found in query for APN: ".concat(extraInfo));
                    if (query != null) {
                        query.close();
                    }
                } catch (Exception e) {
                    Log.e("GnssNetworkConnectivityHandler", "Error encountered on APN query for: ".concat(extraInfo), e);
                }
            } else {
                i2 = gnssNetworkConnectivityHandler.getLinkIpType(linkProperties);
            }
            NetworkScoreService$$ExternalSyntheticOutline0.m(i2, "native_agps_data_conn_open: mAgpsApn=", extraInfo, ", mApnIpType=", "GnssNetworkConnectivityHandler");
            gnssNetworkConnectivityHandler.native_agps_data_conn_open(network.getNetworkHandle(), extraInfo, i2);
            gnssNetworkConnectivityHandler.mAGpsDataConnectionState = 2;
        }
    }

    /* renamed from: -$$Nest$mhandleUpdateNetworkState, reason: not valid java name */
    public static void m641$$Nest$mhandleUpdateNetworkState(GnssNetworkConnectivityHandler gnssNetworkConnectivityHandler, Network network, boolean z, NetworkCapabilities networkCapabilities) {
        boolean z2;
        NetworkAttributes networkAttributes;
        GnssHalStatus gnssHalStatus;
        TelephonyManager telephonyManager = (TelephonyManager) gnssNetworkConnectivityHandler.mContext.getSystemService(TelephonyManager.class);
        if (telephonyManager != null) {
            z2 = z && telephonyManager.getDataEnabled();
        } else {
            z2 = false;
        }
        if (z) {
            NetworkAttributes networkAttributes2 = (NetworkAttributes) gnssNetworkConnectivityHandler.mAvailableNetworkAttributes.get(network);
            if (networkAttributes2 != null) {
                networkAttributes2.mCapabilities = networkCapabilities;
            } else {
                networkAttributes2 = new NetworkAttributes();
                networkAttributes2.mType = -1;
                networkAttributes2.mCapabilities = networkCapabilities;
                NetworkInfo networkInfo = gnssNetworkConnectivityHandler.mConnMgr.getNetworkInfo(network);
                if (networkInfo != null) {
                    networkAttributes2.mApn = networkInfo.getExtraInfo();
                    networkAttributes2.mType = networkInfo.getType();
                }
                gnssNetworkConnectivityHandler.mAvailableNetworkAttributes.put(network, networkAttributes2);
            }
            networkAttributes = networkAttributes2;
        } else {
            networkAttributes = (NetworkAttributes) gnssNetworkConnectivityHandler.mAvailableNetworkAttributes.remove(network);
        }
        String str = networkAttributes.mApn;
        int i = networkAttributes.mType;
        NetworkCapabilities networkCapabilities2 = networkAttributes.mCapabilities;
        String agpsDataConnStateAsString = gnssNetworkConnectivityHandler.agpsDataConnStateAsString();
        Boolean valueOf = Boolean.valueOf(z);
        short s = networkCapabilities2.hasCapability(18) ? (short) 2 : (short) 0;
        if (networkCapabilities2.hasCapability(11)) {
            s = (short) (s | 1);
        }
        Log.i("GnssNetworkConnectivityHandler", String.format("updateNetworkState, state=%s, connected=%s, network=%s, capabilityFlags=%d, availableNetworkCount: %d", agpsDataConnStateAsString, valueOf, network, Short.valueOf(s), Integer.valueOf(gnssNetworkConnectivityHandler.mAvailableNetworkAttributes.size())));
        gnssNetworkConnectivityHandler.mGnssVendorConfig.getClass();
        if (GnssVendorConfig.isLsiGnss()) {
            gnssHalStatus = new GnssHalStatus();
            gnssHalStatus.triggerCheckingHalStatus(3000L);
        } else {
            gnssHalStatus = null;
        }
        GnssHalStatus gnssHalStatus2 = gnssHalStatus;
        if (native_is_agps_ril_supported()) {
            boolean hasTransport = true ^ networkCapabilities2.hasTransport(18);
            if (str == null) {
                str = "";
            }
            String str2 = str;
            long networkHandle = network.getNetworkHandle();
            short s2 = networkCapabilities2.hasCapability(18) ? (short) 2 : (short) 0;
            gnssNetworkConnectivityHandler.native_update_network_state(z, i, hasTransport, z2, str2, networkHandle, networkCapabilities2.hasCapability(11) ? (short) (s2 | 1) : s2);
        } else {
            Log.d("GnssNetworkConnectivityHandler", "Skipped network state update because GPS HAL AGPS-RIL is not  supported");
        }
        if (gnssHalStatus2 != null) {
            gnssHalStatus2.updateHalStatusChecked();
        }
    }

    public GnssNetworkConnectivityHandler(Context context, GnssLocationProvider$$ExternalSyntheticLambda9 gnssLocationProvider$$ExternalSyntheticLambda9, Looper looper, GpsNetInitiatedHandler gpsNetInitiatedHandler) {
        GnssVendorConfig gnssVendorConfig = GnssVendorConfig.getInstance();
        this.mGnssVendorConfig = gnssVendorConfig;
        CarrierConfig carrierConfig = CarrierConfig.getInstance();
        this.mCarrierConfig = carrierConfig;
        this.mIsConnectEmergencyNetwork = true;
        this.mSuplConnectionReleaseOnTimeoutToken = new Object();
        SubscriptionManager.OnSubscriptionsChangedListener onSubscriptionsChangedListener = new SubscriptionManager.OnSubscriptionsChangedListener() { // from class: com.android.server.location.gnss.GnssNetworkConnectivityHandler.1
            @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
            public final void onSubscriptionsChanged() {
                TelephonyManager createForSubscriptionId;
                GnssNetworkConnectivityHandler gnssNetworkConnectivityHandler = GnssNetworkConnectivityHandler.this;
                if (gnssNetworkConnectivityHandler.mPhoneStateListeners == null) {
                    gnssNetworkConnectivityHandler.mPhoneStateListeners = new HashMap(2, 1.0f);
                }
                SubscriptionManager subscriptionManager = (SubscriptionManager) GnssNetworkConnectivityHandler.this.mContext.getSystemService(SubscriptionManager.class);
                TelephonyManager telephonyManager = (TelephonyManager) GnssNetworkConnectivityHandler.this.mContext.getSystemService(TelephonyManager.class);
                if (subscriptionManager == null || telephonyManager == null) {
                    return;
                }
                List<SubscriptionInfo> activeSubscriptionInfoList = subscriptionManager.createForAllUserProfiles().getActiveSubscriptionInfoList();
                HashSet hashSet = new HashSet();
                if (activeSubscriptionInfoList != null) {
                    Log.d("GnssNetworkConnectivityHandler", "Active Sub List size: " + activeSubscriptionInfoList.size());
                    for (SubscriptionInfo subscriptionInfo : activeSubscriptionInfoList) {
                        hashSet.add(Integer.valueOf(subscriptionInfo.getSubscriptionId()));
                        if (!GnssNetworkConnectivityHandler.this.mPhoneStateListeners.containsKey(Integer.valueOf(subscriptionInfo.getSubscriptionId())) && (createForSubscriptionId = telephonyManager.createForSubscriptionId(subscriptionInfo.getSubscriptionId())) != null) {
                            Log.d("GnssNetworkConnectivityHandler", "Listener sub" + subscriptionInfo.getSubscriptionId());
                            SubIdPhoneStateListener subIdPhoneStateListener = GnssNetworkConnectivityHandler.this.new SubIdPhoneStateListener(Integer.valueOf(subscriptionInfo.getSubscriptionId()));
                            GnssNetworkConnectivityHandler.this.mPhoneStateListeners.put(Integer.valueOf(subscriptionInfo.getSubscriptionId()), subIdPhoneStateListener);
                            createForSubscriptionId.listen(subIdPhoneStateListener, 2048);
                        }
                    }
                }
                Iterator it = GnssNetworkConnectivityHandler.this.mPhoneStateListeners.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    if (!hashSet.contains(entry.getKey())) {
                        TelephonyManager createForSubscriptionId2 = telephonyManager.createForSubscriptionId(((Integer) entry.getKey()).intValue());
                        if (createForSubscriptionId2 != null) {
                            Log.d("GnssNetworkConnectivityHandler", "unregister listener sub " + entry.getKey());
                            createForSubscriptionId2.listen((PhoneStateListener) entry.getValue(), 0);
                            it.remove();
                        } else {
                            Log.e("GnssNetworkConnectivityHandler", "Telephony Manager for Sub " + entry.getKey() + " null");
                        }
                    }
                }
                if (hashSet.contains(Integer.valueOf(GnssNetworkConnectivityHandler.this.mActiveSubId))) {
                    return;
                }
                GnssNetworkConnectivityHandler.this.mActiveSubId = -1;
            }
        };
        this.mContext = context;
        this.mGnssNetworkListener = gnssLocationProvider$$ExternalSyntheticLambda9;
        SubscriptionManager subscriptionManager = (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
        if (subscriptionManager != null) {
            if (Flags.subscriptionsChangedListenerThread()) {
                subscriptionManager.addOnSubscriptionsChangedListener(FgThread.getExecutor(), onSubscriptionsChangedListener);
            } else {
                subscriptionManager.addOnSubscriptionsChangedListener(onSubscriptionsChangedListener);
            }
        }
        this.mWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(1, "GnssNetworkConnectivityHandler");
        this.mHandler = new Handler(looper);
        this.mNiHandler = gpsNetInitiatedHandler;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        this.mConnMgr = connectivityManager;
        this.mSuplConnectivityCallback = null;
        gnssVendorConfig.getClass();
        if (GnssVendorConfig.isIzatServiceEnabled()) {
            return;
        }
        if (carrierConfig.isCanadaMarket() || carrierConfig.isUSAMarket()) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this, 1);
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            builder.addCapability(10);
            connectivityManager.registerNetworkCallback(builder.build(), anonymousClass3);
            this.mIsConnectEmergencyNetwork = false;
        }
    }

    public static String agpsDataConnStatusAsString(int i) {
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? i != 5 ? BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "<Unknown>(", ")") : "FAILED" : "DONE" : "CONNECTED" : "RELEASE" : "REQUEST";
    }

    private native void native_agps_data_conn_closed();

    private native void native_agps_data_conn_failed();

    private native void native_agps_data_conn_open(long j, String str, int i);

    private static native boolean native_is_agps_ril_supported();

    private native void native_update_network_state(boolean z, int i, boolean z2, boolean z3, String str, long j, short s);

    public static int translateToApnIpTypeSec(String str, String str2) {
        if ("IP".equals(str)) {
            return 1;
        }
        if ("IPV6".equals(str)) {
            return 2;
        }
        if ("IPV4V6".equals(str)) {
            return 3;
        }
        Log.e("GnssNetworkConnectivityHandler", "Unknown IP Protocol: " + str + ", for APN: " + str2);
        return 3;
    }

    public final String agpsDataConnStateAsString() {
        int i = this.mAGpsDataConnectionState;
        if (i == 0) {
            return "CLOSED";
        }
        if (i == 1) {
            return "OPENING";
        }
        if (i == 2) {
            return "OPEN";
        }
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mAGpsDataConnectionState, new StringBuilder("<Unknown>("), ")");
    }

    public final int getLinkIpType(LinkProperties linkProperties) {
        Handler handler = this.mHandler;
        if (handler == null || Looper.myLooper() != handler.getLooper()) {
            throw new IllegalStateException("This method must run on the Handler thread.");
        }
        Iterator<LinkAddress> it = linkProperties.getLinkAddresses().iterator();
        boolean z = false;
        boolean z2 = false;
        while (it.hasNext()) {
            InetAddress address = it.next().getAddress();
            if (address instanceof Inet4Address) {
                z = true;
            } else if (address instanceof Inet6Address) {
                z2 = true;
            }
            Log.d("GnssNetworkConnectivityHandler", "LinkAddress : " + address.toString());
        }
        if (z && z2) {
            return 3;
        }
        if (z) {
            return 1;
        }
        return z2 ? 2 : 0;
    }

    public final void handleReleaseSuplConnection(int i) {
        Log.d("GnssNetworkConnectivityHandler", "releaseSuplConnection, state=" + agpsDataConnStateAsString() + ", status=" + agpsDataConnStatusAsString(i));
        if (Flags.releaseSuplConnectionOnTimeout()) {
            this.mHandler.removeCallbacksAndMessages(this.mSuplConnectionReleaseOnTimeoutToken);
        }
        if (this.mAGpsDataConnectionState == 0) {
            return;
        }
        this.mAGpsDataConnectionState = 0;
        AnonymousClass3 anonymousClass3 = this.mSuplConnectivityCallback;
        if (anonymousClass3 != null) {
            this.mConnMgr.unregisterNetworkCallback(anonymousClass3);
            this.mSuplConnectivityCallback = null;
        }
        if (i == 2) {
            native_agps_data_conn_closed();
        } else if (i != 5) {
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(i, "Invalid status to release SUPL connection: ", "GnssNetworkConnectivityHandler");
        } else {
            native_agps_data_conn_failed();
        }
    }

    public final boolean isDataNetworkConnected() {
        NetworkInfo activeNetworkInfo = this.mConnMgr.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public final void registerNetworkCallbacks() {
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        builder.addCapability(12);
        builder.addCapability(16);
        builder.removeCapability(15);
        NetworkRequest build = builder.build();
        ?? r1 = new ConnectivityManager.NetworkCallback() { // from class: com.android.server.location.gnss.GnssNetworkConnectivityHandler.2
            public final HashMap mAvailableNetworkCapabilities = new HashMap(5);
            public boolean isOnLostCallbackReceived = false;

            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                NetworkCapabilities networkCapabilities2 = (NetworkCapabilities) this.mAvailableNetworkCapabilities.get(network);
                if (networkCapabilities2 != null && networkCapabilities != null && networkCapabilities2.hasCapability(18) == networkCapabilities.hasCapability(18) && networkCapabilities2.hasCapability(11) == networkCapabilities.hasCapability(11)) {
                    if (GnssNetworkConnectivityHandler.VERBOSE) {
                        Log.v("GnssNetworkConnectivityHandler", "Relevant network capabilities unchanged. Capabilities: " + networkCapabilities);
                    }
                    GnssNetworkConnectivityHandler.this.mGnssVendorConfig.getClass();
                    if (GnssVendorConfig.isLsiGnss() && this.isOnLostCallbackReceived) {
                        GnssNetworkConnectivityHandler.m641$$Nest$mhandleUpdateNetworkState(GnssNetworkConnectivityHandler.this, network, true, networkCapabilities);
                        this.isOnLostCallbackReceived = false;
                        return;
                    }
                    return;
                }
                this.mAvailableNetworkCapabilities.put(network, networkCapabilities);
                Log.d("GnssNetworkConnectivityHandler", "Network connected/capabilities updated. Available networks count: " + this.mAvailableNetworkCapabilities.size());
                GnssLocationProvider gnssLocationProvider = GnssNetworkConnectivityHandler.this.mGnssNetworkListener.f$0;
                TimeDetectorNetworkTimeHelper timeDetectorNetworkTimeHelper = gnssLocationProvider.mNetworkTimeHelper;
                synchronized (timeDetectorNetworkTimeHelper) {
                    try {
                        if (!timeDetectorNetworkTimeHelper.mNetworkTimeInjected) {
                            timeDetectorNetworkTimeHelper.mEnvironment.requestImmediateTimeQueryCallback(timeDetectorNetworkTimeHelper, "onNetworkAvailable");
                        }
                    } finally {
                    }
                }
                if (gnssLocationProvider.mSupportsPsds) {
                    synchronized (gnssLocationProvider.mLock) {
                        try {
                            Iterator it = ((HashSet) gnssLocationProvider.mPendingDownloadPsdsTypes).iterator();
                            while (it.hasNext()) {
                                gnssLocationProvider.postWithWakeLockHeld(new GnssLocationProvider$$ExternalSyntheticLambda3(((Integer) it.next()).intValue(), 4, (GnssLocationProviderSec) gnssLocationProvider));
                            }
                            ((HashSet) gnssLocationProvider.mPendingDownloadPsdsTypes).clear();
                        } finally {
                        }
                    }
                }
                GnssNetworkConnectivityHandler.m641$$Nest$mhandleUpdateNetworkState(GnssNetworkConnectivityHandler.this, network, true, networkCapabilities);
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onLost(Network network) {
                if (this.mAvailableNetworkCapabilities.remove(network) == null) {
                    Log.w("GnssNetworkConnectivityHandler", "Incorrectly received network callback onLost() before onCapabilitiesChanged() for network: " + network);
                    return;
                }
                GnssNetworkConnectivityHandler.this.mGnssVendorConfig.getClass();
                if (GnssVendorConfig.isLsiGnss()) {
                    this.isOnLostCallbackReceived = true;
                }
                Log.i("GnssNetworkConnectivityHandler", "Network connection lost. Available networks count: " + this.mAvailableNetworkCapabilities.size());
                GnssNetworkConnectivityHandler.m641$$Nest$mhandleUpdateNetworkState(GnssNetworkConnectivityHandler.this, network, false, null);
            }
        };
        this.mNetworkConnectivityCallback = r1;
        this.mConnMgr.registerNetworkCallback(build, r1, this.mHandler);
    }
}
