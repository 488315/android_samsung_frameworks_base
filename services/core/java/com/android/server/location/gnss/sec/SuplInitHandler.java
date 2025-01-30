package com.android.server.location.gnss.sec;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.TrafficStats;
import android.net.util.NetworkConstants;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.location.LocationServiceThread;
import com.android.server.location.gnss.GnssLocationProviderSec;
import com.android.server.location.gnss.hal.GnssNative;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class SuplInitHandler {
    public static int mSubIdForSuplNi = -1;
    public CarrierConfig mCarrierConfig;
    public ConnectivityManager mConnectivityManager;
    public Context mContext;
    public final ConnectivityManager.NetworkCallback mEmergencyNetworkConnectivityCallback;
    public GnssNative mGnssNative;
    public GnssLocationProviderSec mGnssProvider;
    public GnssVendorConfig mGnssVendorConfig;
    public boolean mIsOpenUdpPort;
    public boolean mIsUdpListen;
    public TelephonyManager mTelephonyManager;
    public boolean[] mImsRegistered = {false, false, false};
    public boolean mNiSessionStarted = false;

    public SuplInitHandler(Context context, GnssLocationProviderSec gnssLocationProviderSec, GnssNative gnssNative) {
        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.android.server.location.gnss.sec.SuplInitHandler.1
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(Network network) {
                Log.d("SuplInitHandler", "mEmergencyNetworkConnectivityCallback : onAvailable");
                if (SuplInitHandler.this.mIsUdpListen) {
                    return;
                }
                SuplInitHandler.this.mIsUdpListen = true;
                if (!SuplInitHandler.this.mIsOpenUdpPort) {
                    SuplInitHandler.this.checkUDPSuplInit();
                } else {
                    Log.d("SuplInitHandler", "UDP port is already opened.");
                }
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(Network network) {
                Log.d("SuplInitHandler", "mEmergencyNetworkConnectivityCallback : onLost");
                SuplInitHandler.this.mIsUdpListen = false;
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onUnavailable() {
                Log.d("SuplInitHandler", "mEmergencyNetworkConnectivityCallback : onUnavailable");
                SuplInitHandler.this.mIsUdpListen = false;
            }
        };
        this.mEmergencyNetworkConnectivityCallback = networkCallback;
        this.mContext = context;
        this.mGnssNative = gnssNative;
        this.mGnssProvider = gnssLocationProviderSec;
        this.mGnssVendorConfig = GnssVendorConfig.getInstance();
        this.mCarrierConfig = CarrierConfig.getInstance();
        this.mConnectivityManager = (ConnectivityManager) this.mContext.getSystemService("connectivity");
        this.mTelephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
        if (this.mGnssVendorConfig.isIzatServiceEnabled() || !this.mCarrierConfig.isEPDNListenRequired()) {
            return;
        }
        Log.d("SuplInitHandler", "register EmergencyNetworkConnectivityCallback");
        NetworkRequest.Builder builder = new NetworkRequest.Builder();
        builder.addCapability(10);
        this.mConnectivityManager.registerNetworkCallback(builder.build(), networkCallback);
    }

    public static int getSubIdForSuplNi() {
        return mSubIdForSuplNi;
    }

    public void setSubIdForSuplNi(int i) {
        mSubIdForSuplNi = i;
    }

    public boolean getNiSessionStarted() {
        return this.mNiSessionStarted;
    }

    public void setNiSessionStarted(boolean z) {
        this.mNiSessionStarted = z;
    }

    public void handleDataSmsReceived(final Intent intent) {
        handleSuplInit(new Runnable() { // from class: com.android.server.location.gnss.sec.SuplInitHandler$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                SuplInitHandler.this.lambda$handleDataSmsReceived$0(intent);
            }
        }, intent.getIntExtra("android.telephony.extra.SLOT_INDEX", -1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleDataSmsReceived$0(Intent intent) {
        SmsMessage[] messagesFromIntent = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        if (messagesFromIntent == null) {
            Log.e("SuplInitHandler", "Message does not exist in the intent.");
            return;
        }
        for (SmsMessage smsMessage : messagesFromIntent) {
            if (smsMessage != null && smsMessage.mWrappedSmsMessage != null) {
                byte[] userData = smsMessage.getUserData();
                mSubIdForSuplNi = getSimSubIdFromIntent(intent);
                if (userData != null) {
                    Log.d("SuplInitHandler", "New NI received, Sim Sub id=" + mSubIdForSuplNi + ", SimOperator=" + this.mTelephonyManager.getSimOperator(mSubIdForSuplNi));
                    this.mCarrierConfig.setSimOperator(this.mTelephonyManager.getSimOperator(mSubIdForSuplNi));
                    setNiSessionStarted(true);
                    sendSmsSuplNiMessageToHal(userData);
                }
            }
        }
    }

    public void handleWapPushReceived(final Intent intent) {
        handleSuplInit(new Runnable() { // from class: com.android.server.location.gnss.sec.SuplInitHandler$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                SuplInitHandler.this.lambda$handleWapPushReceived$1(intent);
            }
        }, intent.getIntExtra("android.telephony.extra.SLOT_INDEX", -1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$handleWapPushReceived$1(Intent intent) {
        byte[] byteArrayExtra = intent.getByteArrayExtra("data");
        if (byteArrayExtra == null) {
            return;
        }
        mSubIdForSuplNi = getSimSubIdFromIntent(intent);
        Log.d("SuplInitHandler", "New NI received, Sim Sub id=" + mSubIdForSuplNi + ", SimOperator=" + this.mTelephonyManager.getSimOperator(mSubIdForSuplNi));
        this.mCarrierConfig.setSimOperator(this.mTelephonyManager.getSimOperator(mSubIdForSuplNi));
        setNiSessionStarted(true);
        sendWapSuplInitMessageToHal(byteArrayExtra);
    }

    public int getSimSubIdFromIntent(Intent intent) {
        if (intent.getExtras() == null) {
            return -1;
        }
        int i = intent.getExtras().getInt("subscription");
        if (SubscriptionManager.isValidSubscriptionId(i)) {
            return i;
        }
        return -1;
    }

    public final void handleSuplInit(Runnable runnable, int i) {
        if (this.mGnssVendorConfig.isIzatServiceEnabled()) {
            if (this.mCarrierConfig.isKoreaMarket()) {
                Log.d("SuplInitHandler", "handleSuplInit : slotId = " + i);
                this.mGnssNative.gnssConfigurationUpdateSec("NI_SUPL_SLOTID=" + i);
                runnable.run();
                return;
            }
            if (i < 0 || i > 2) {
                Log.d("SuplInitHandler", "SUPL INIT is rejected, slotId = " + i);
                return;
            }
            if (this.mImsRegistered[i]) {
                runnable.run();
                return;
            }
            return;
        }
        if (isSuplNiAvailable()) {
            runnable.run();
        }
    }

    public boolean isSuplNiAvailable() {
        Log.d("SuplInitHandler", "isSuplNiAvailable(), mGpsEnabled = " + this.mGnssProvider.isGpsEnabled());
        return this.mGnssProvider.isGpsEnabled() || this.mCarrierConfig.isSupportSuplNiWithSettingOff();
    }

    public final void sendSmsSuplNiMessageToHal(byte[] bArr) {
        if (!this.mGnssVendorConfig.isIzatServiceEnabled()) {
            updateConfigurationForSmsSuplInit();
            this.mGnssProvider.onRequestSetID(1);
        }
        this.mGnssNative.sendSuplNiMessage(bArr, bArr.length);
    }

    public final void sendWapSuplInitMessageToHal(byte[] bArr) {
        if (!this.mGnssVendorConfig.isIzatServiceEnabled()) {
            presetWapSuplInit();
            this.mGnssProvider.onRequestSetID(1);
        }
        this.mGnssNative.sendSuplNiMessage(bArr, bArr.length);
    }

    public void updateConfigurationForSmsSuplInit() {
        if (this.mCarrierConfig.isChinaSuplNiSupported()) {
            Log.d("SuplInitHandler", "checkSmsSuplInit : Sms Message for SUPL Init");
        }
        if (this.mCarrierConfig.isKorSuplNiSupported()) {
            updateSuplConfigurationForKorNi();
        }
    }

    public void presetWapSuplInit() {
        if (this.mCarrierConfig.isChinaSuplNiSupported()) {
            presetForChnNi();
        } else if (this.mCarrierConfig.isKorSuplNiSupported()) {
            updateSuplConfigurationForKorNi();
        }
    }

    public final void presetForChnNi() {
        Log.d("SuplInitHandler", "checkWapSuplInit : WapPush Message for SUPL Init");
        turnLcdOnForSuplNi();
        checkAgpsSwitchMode();
    }

    public final void turnLcdOnForSuplNi() {
        PowerManager.WakeLock wakeLock;
        PowerManager powerManager = (PowerManager) this.mContext.getSystemService("power");
        if (powerManager != null) {
            wakeLock = powerManager.newWakeLock(268435482, "New message notification LCD on");
            powerManager.userActivity(SystemClock.uptimeMillis(), false);
        } else {
            wakeLock = null;
        }
        if (wakeLock != null) {
            wakeLock.acquire(5000L);
        }
    }

    public final void checkAgpsSwitchMode() {
        int i = Settings.System.getInt(this.mContext.getContentResolver(), "agps_function_switch", 1);
        Log.d("SuplInitHandler", "checkWapSuplInit NI: isAgpsSwitchMode=AGPS_FUNCTION_SWITCH : " + i);
        if (i != 1) {
            if (i == 2) {
                Log.d("SuplInitHandler", "checkWapSuplInit NI :: agps on for all network. launch NI session");
                return;
            }
            Log.d("SuplInitHandler", "checkWapSuplInit NI : agps off isAgpsSwitchMode : " + i);
            return;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) this.mContext.getSystemService("connectivity");
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isRoaming()) {
                return;
            }
            Log.d("SuplInitHandler", "checkWapSuplInit NI : agps on only for home network info.isRoaming() == true ");
            return;
        }
        Log.d("SuplInitHandler", "checkWapSuplInit NI :: agps on only for home network. PS error.");
    }

    public void updateSuplConfigurationForKorNi() {
        Log.d("SuplInitHandler", "updateSuplConfigurationForNI()");
        setSuplConfiguration();
        if (this.mGnssVendorConfig.isIzatServiceEnabled()) {
            return;
        }
        setAgnssConstellation();
    }

    public final void setSuplConfiguration() {
        if (this.mCarrierConfig.isKoreaSktSim()) {
            this.mGnssNative.gnssConfigurationUpdateSec(convertHashMapToStringBuilder(this.mCarrierConfig.getConfigMap()).toString());
            this.mGnssProvider.setSktSuplVer();
        } else if (this.mCarrierConfig.isKoreaKttSim()) {
            this.mGnssNative.gnssConfigurationUpdateSec(convertHashMapToStringBuilder(this.mCarrierConfig.getConfigMap()).toString());
            this.mGnssProvider.updateSuplServerConfiguration(3, "221.148.242.107", 7275, 0, 0, 15);
        } else if (this.mCarrierConfig.isKoreaLguSim()) {
            this.mGnssNative.gnssConfigurationUpdateSec(convertHashMapToStringBuilder(this.mCarrierConfig.getConfigMap()).toString());
            this.mGnssProvider.updateSuplServerConfiguration(3, "e-slp.uplus.co.kr", 7275, 1, 0, 15);
        }
    }

    public final StringBuilder convertHashMapToStringBuilder(HashMap hashMap) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : hashMap.entrySet()) {
            String str = (String) entry.getKey();
            String str2 = (String) entry.getValue();
            Log.d("SuplInitHandler", str + "=" + str2);
            sb.append(str);
            sb.append("=");
            sb.append(str2);
            sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        }
        return sb;
    }

    public final void setAgnssConstellation() {
        if (this.mCarrierConfig.isKoreaSktSim() || this.mCarrierConfig.isKoreaKttSim()) {
            this.mGnssNative.gnssConfigurationUpdateSec("ENABLE_SUPL_AGNSS_BEIDOU=TRUE");
            this.mGnssNative.gnssConfigurationUpdateSec("ENABLE_SUPL_AGNSS_GALILEO=TRUE");
        } else if (this.mCarrierConfig.isKoreaLgu() || this.mCarrierConfig.isKoreaLguSim()) {
            this.mGnssNative.gnssConfigurationUpdateSec("ENABLE_SUPL_AGNSS_BEIDOU=FALSE");
            this.mGnssNative.gnssConfigurationUpdateSec("ENABLE_SUPL_AGNSS_GALILEO=FALSE");
        }
    }

    public void updateImsState(Intent intent) {
        int intExtra = intent.getIntExtra("PHONE_ID", -1);
        Log.d("SuplInitHandler", "IMS_REGISTRATION SLOT_ID = " + intExtra);
        if (intExtra < 0 || intExtra > 2) {
            return;
        }
        boolean booleanExtra = intent.getBooleanExtra("REGISTERED", false);
        String stringExtra = intent.getStringExtra("SERVICE");
        Log.d("SuplInitHandler", "IMS service capabilities : " + stringExtra);
        Log.d("SuplInitHandler", "IMS registration error code : " + intent.getIntExtra("SIP_ERROR", 0));
        if (stringExtra.contains("smsip")) {
            setImsRegistered(intExtra, booleanExtra);
        }
    }

    public final void setImsRegistered(int i, boolean z) {
        this.mImsRegistered[i] = z;
        Log.d("SuplInitHandler", "IMS smsip[" + i + "] registered " + z);
    }

    public void handleUseUdpCommand(Bundle bundle) {
        Log.d("SuplInitHandler", "set_use_udp : " + bundle.getInt("use_udp"));
        if (bundle.getInt("use_udp") == 1) {
            if (this.mIsUdpListen) {
                return;
            }
            this.mIsUdpListen = true;
            if (!this.mIsOpenUdpPort) {
                checkUDPSuplInit();
                return;
            } else {
                Log.d("SuplInitHandler", "UDP port is already opened.");
                return;
            }
        }
        this.mIsUdpListen = false;
    }

    public final void checkUDPSuplInit() {
        Log.d("SuplInitHandler", "start UDP socket");
        new Thread(new Runnable() { // from class: com.android.server.location.gnss.sec.SuplInitHandler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SuplInitHandler.this.lambda$checkUDPSuplInit$2();
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$checkUDPSuplInit$2() {
        TrafficStats.setThreadStatsTag((int) Thread.currentThread().getId());
        while (this.mIsUdpListen) {
            try {
                DatagramSocket datagramSocket = new DatagramSocket(7275);
                try {
                    this.mIsOpenUdpPort = true;
                    DatagramPacket datagramPacket = getDatagramPacket(datagramSocket);
                    Log.d("SuplInitHandler", "received data through 7275 UDP port");
                    if (datagramPacket.getLength() > 0) {
                        sendUdpSuplInitData(datagramPacket);
                        this.mIsOpenUdpPort = false;
                    }
                    datagramSocket.close();
                } catch (Throwable th) {
                    try {
                        datagramSocket.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (SocketTimeoutException unused) {
                this.mIsOpenUdpPort = false;
            } catch (IOException e) {
                e.printStackTrace();
                this.mIsOpenUdpPort = false;
            }
        }
        TrafficStats.clearThreadStatsTag();
    }

    public final DatagramPacket getDatagramPacket(DatagramSocket datagramSocket) {
        DatagramPacket datagramPacket = new DatagramPacket(new byte[NetworkConstants.ETHER_MTU], NetworkConstants.ETHER_MTU);
        datagramSocket.setSoTimeout(60000);
        datagramSocket.receive(datagramPacket);
        return datagramPacket;
    }

    public final void sendUdpSuplInitData(final DatagramPacket datagramPacket) {
        LocationServiceThread.getHandler().post(new Runnable() { // from class: com.android.server.location.gnss.sec.SuplInitHandler$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                SuplInitHandler.this.lambda$sendUdpSuplInitData$3(datagramPacket);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendUdpSuplInitData$3(DatagramPacket datagramPacket) {
        this.mGnssProvider.onRequestSetID(1);
        this.mGnssNative.sendSuplNiMessage(datagramPacket.getData(), datagramPacket.getLength());
    }
}
