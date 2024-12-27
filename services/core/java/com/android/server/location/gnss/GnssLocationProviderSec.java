package com.android.server.location.gnss;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.input.InputManager;
import android.location.GnssStatus;
import android.location.Location;
import android.location.LocationConstants;
import android.location.LocationResult;
import android.media.ToneGenerator;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileObserver;
import android.os.Handler;
import android.os.IDeviceIdleController;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.ProcessList$$ExternalSyntheticOutline0;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.location.gnss.sec.CarrierConfig;
import com.android.server.location.gnss.sec.GnssConstants;
import com.android.server.location.gnss.sec.GnssVendorConfig;
import com.android.server.location.gnss.sec.LppeFusedLocationHelper;
import com.android.server.location.gnss.sec.SuplInitHandler;
import com.android.server.location.gnss.sec.SuplInitHandler$$ExternalSyntheticLambda0;
import com.android.server.location.gnss.sec.SuplInitHandler$$ExternalSyntheticLambda1;
import com.android.server.location.injector.Injector;
import com.android.server.location.nsflp.NSLocationProviderHelper;
import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knoxguard.service.utils.Constants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class GnssLocationProviderSec extends GnssLocationProvider {
    public static final Uri PREFERAPN_NO_UPDATE_URI_USING_SUBID = Uri.parse("content://telephony/carriers/preferapn_no_update/subId/");
    public static boolean isWlanApConnected = false;
    public boolean isIssueTrackerIntentReceived;
    public boolean isSehRefLocation;
    public int ktPositionMode;
    public final AnonymousClass1 mBroadcastReceiverSec;
    public CarrierConfig mCarrierConfig;
    public final AnonymousClass2 mCellInfoCb;
    public final ConnectivityManager mConnectivityManager;
    public final String[] mConstellationString;
    public boolean mEquipmentTestModeEnabled;
    public ToneGenerator mFocusToneGenerator;
    public boolean mIsKtGps;
    public boolean mIsSKApplicationFramework;
    public String mKTSuplServerHost;
    public int mKTSuplServerPort;
    public int mLidState;
    public final Properties mPropertiesNsFlp;
    public final Properties mPropertiesSecgps;
    public final int mServerTypeI;
    public final HashMap mSimInformationHashMap;
    public final SuplInitHandler mSuplInitHandler;
    public TelephonyManager mTelephonyManager;
    public int prevCpAgpsMask;
    public long prevLocationElapsedRealtimeNs;
    public int prevLppMask;
    public int prevNrLppMask;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CtsRestrictModeFileObserver extends FileObserver {
        public CtsRestrictModeFileObserver(File file) {
            super(file, 2);
        }

        @Override // android.os.FileObserver
        public final void onEvent(int i, String str) {
            FileInputStream fileInputStream;
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "CTS restrict mode : event =", "GnssLocationProvider_ex");
            if (i == 2) {
                Log.d("GnssLocationProvider_ex", "CTS restrict mode : file modified!");
                String str2 = "";
                try {
                    fileInputStream = new FileInputStream(new File("/sys/class/sensors/ssc_core/operation_mode"));
                } catch (FileNotFoundException unused) {
                    Log.w("GnssLocationProvider_ex", "Could not open File /sys/class/sensors/ssc_core/operation_mode");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException unused2) {
                    Log.w("GnssLocationProvider_ex", "Could not load file /sys/class/sensors/ssc_core/operation_modedue to IllegalArgumentException");
                } catch (SecurityException unused3) {
                    Log.w("GnssLocationProvider_ex", "Could not access file /sys/class/sensors/ssc_core/operation_mode");
                }
                try {
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    try {
                        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                        try {
                            str2 = bufferedReader.readLine();
                            bufferedReader.close();
                            inputStreamReader.close();
                            fileInputStream.close();
                            if (str2 == null) {
                                return;
                            }
                            Log.w("GnssLocationProvider_ex", "CTS sensorservice restrict_mode = ".concat(str2));
                            if ("restrict".equals(str2) || "normal".equals(str2)) {
                                GnssLocationProviderSec.this.postWithWakeLockHeld(new GnssLocationProviderSec$$ExternalSyntheticLambda0(2, this, str2));
                            }
                        } finally {
                        }
                    } finally {
                    }
                } catch (Throwable th) {
                    try {
                        fileInputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InputLidStateChangedListener implements InputManager.SemOnLidStateChangedListener {
        public InputLidStateChangedListener() {
        }

        public final void onLidStateChanged(long j, int i) {
            GnssLocationProviderSec.this.mLidState = i;
            if (i >= 0) {
                GestureWakeup$$ExternalSyntheticOutline0.m(new StringBuilder("onLidStateChanged "), GnssLocationProviderSec.this.mLidState, "GnssLocationProvider_ex");
                GnssLocationProviderSec gnssLocationProviderSec = GnssLocationProviderSec.this;
                gnssLocationProviderSec.sendLidState(gnssLocationProviderSec.mLidState);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SimInformationForDsds {
        public TelephonyManager mPhone;
        public String mSimOperator;
        public int mSlotId;
    }

    /* JADX WARN: Type inference failed for: r11v3, types: [com.android.server.location.gnss.GnssLocationProviderSec$1] */
    public GnssLocationProviderSec(Context context, GnssNative gnssNative, GnssMetrics gnssMetrics) {
        super(context, gnssNative, gnssMetrics);
        this.mIsSKApplicationFramework = false;
        this.mIsKtGps = false;
        this.ktPositionMode = 1;
        this.mServerTypeI = -1;
        this.prevCpAgpsMask = -1;
        this.prevLppMask = -1;
        this.prevNrLppMask = -1;
        this.mConstellationString = new String[]{"Unknown", "GPS", "SBAS", "GLONASS", "QZSS", "BEIDOU", "GALILEO", "NAVIC"};
        this.mFocusToneGenerator = null;
        this.isSehRefLocation = true;
        this.mEquipmentTestModeEnabled = false;
        this.prevLocationElapsedRealtimeNs = 0L;
        this.mBroadcastReceiverSec = new BroadcastReceiver() { // from class: com.android.server.location.gnss.GnssLocationProviderSec.1
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                char c;
                int i = 1;
                int i2 = 0;
                String action = intent.getAction();
                DualAppManagerService$$ExternalSyntheticOutline0.m("receive broadcast intent, action: ", action, "GnssLocationProvider_ex");
                if (action == null) {
                }
                switch (action.hashCode()) {
                    case -1721100884:
                        if (action.equals("com.samsung.carrier.action.CARRIER_CHANGED")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -873963303:
                        if (action.equals("android.provider.Telephony.WAP_PUSH_RECEIVED")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -343630553:
                        if (action.equals("android.net.wifi.STATE_CHANGE")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case -229777127:
                        if (action.equals(Constants.SIM_STATE_CHANGED)) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 602272191:
                        if (action.equals("com.samsung.intent.action.EMERGENCY_SMS_OVER_IMS")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1309122817:
                        if (action.equals("com.samsung.ims.action.IMS_REGISTRATION")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1949861786:
                        if (action.equals("com.sec.android.ISSUE_TRACKER_ONOFF")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2142067319:
                        if (action.equals("android.intent.action.DATA_SMS_RECEIVED")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        GnssLocationProviderSec gnssLocationProviderSec = GnssLocationProviderSec.this;
                        gnssLocationProviderSec.getClass();
                        String stringExtra = intent.getStringExtra("com.samsung.carrier.extra.CARRIER_STATE");
                        int intExtra = intent.getIntExtra("com.samsung.carrier.extra.CARRIER_PHONE_ID", -1);
                        Log.d("GnssLocationProvider_ex", "slotid=" + intExtra + " carrier state=" + stringExtra);
                        gnssLocationProviderSec.mGnssVendorConfig.getClass();
                        if (!GnssVendorConfig.isIzatServiceEnabled()) {
                            if ("LOADED".equals(stringExtra)) {
                                int subscriptionId = SubscriptionManager.getSubscriptionId(intExtra);
                                AccessibilityManagerService$$ExternalSyntheticOutline0.m(subscriptionId, intExtra, "Sim subId from slotId. subId=", ", slotId=", "GnssLocationProvider_ex");
                                if (SubscriptionManager.isValidSubscriptionId(subscriptionId)) {
                                    NetworkScorerAppManager$$ExternalSyntheticOutline0.m(subscriptionId, "CarrierFeature LOADED. it's sub Id=", "GnssLocationProvider_ex");
                                    SimInformationForDsds simInformationForDsds = (SimInformationForDsds) gnssLocationProviderSec.mSimInformationHashMap.get(Integer.valueOf(subscriptionId));
                                    if (simInformationForDsds != null) {
                                        simInformationForDsds.mSlotId = intExtra;
                                        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(intExtra, "Set Sim slotID to SimInformationForDSDS, slotId=", "SimInfo=");
                                        m.append(simInformationForDsds.toString());
                                        Log.d("GnssLocationProvider_ex", m.toString());
                                    } else {
                                        Log.d("GnssLocationProvider_ex", "SimInformation was null");
                                    }
                                }
                                gnssLocationProviderSec.setCpAgpsProfile(intExtra);
                                gnssLocationProviderSec.setLppBitMask(intExtra);
                                break;
                            }
                        } else if (("UPDATED".equals(stringExtra) || "LOADED".equals(stringExtra)) && intExtra >= 0) {
                            gnssLocationProviderSec.mGnssNative.gnssConfigurationUpdateSec(VibrationParam$1$$ExternalSyntheticOutline0.m(intExtra, "CARRIER_STATE_CHANGED="));
                            break;
                        }
                        break;
                    case 1:
                        SuplInitHandler suplInitHandler = GnssLocationProviderSec.this.mSuplInitHandler;
                        suplInitHandler.getClass();
                        suplInitHandler.handleSuplInit(new SuplInitHandler$$ExternalSyntheticLambda1(suplInitHandler, intent, i), intent.getIntExtra("android.telephony.extra.SLOT_INDEX", -1));
                        break;
                    case 2:
                        GnssLocationProviderSec.this.getClass();
                        NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
                        if (networkInfo.getState() != NetworkInfo.State.CONNECTED) {
                            if (GnssLocationProviderSec.isWlanApConnected) {
                                Log.d("GnssLocationProvider_ex", "WIFI is DISCONNECTED.");
                                GnssLocationProviderSec.isWlanApConnected = false;
                                break;
                            }
                        } else if (!GnssLocationProviderSec.isWlanApConnected) {
                            Log.d("GnssLocationProvider_ex", "WIFI NetworkInfo: " + networkInfo);
                            GnssLocationProviderSec.isWlanApConnected = true;
                            break;
                        }
                        break;
                    case 3:
                        GnssLocationProviderSec gnssLocationProviderSec2 = GnssLocationProviderSec.this;
                        gnssLocationProviderSec2.getClass();
                        String stringExtra2 = intent.getStringExtra("ss");
                        DualAppManagerService$$ExternalSyntheticOutline0.m("SIM_STATE_CHANGED received : ", stringExtra2, "GnssLocationProvider_ex");
                        SubscriptionManager from = SubscriptionManager.from(gnssLocationProviderSec2.mContext);
                        gnssLocationProviderSec2.mSimInformationHashMap.clear();
                        List<SubscriptionInfo> activeSubscriptionInfoList = from.getActiveSubscriptionInfoList();
                        if (activeSubscriptionInfoList != null) {
                            Iterator<SubscriptionInfo> it = activeSubscriptionInfoList.iterator();
                            while (it.hasNext()) {
                                int subscriptionId2 = it.next().getSubscriptionId();
                                TelephonyManager createForSubscriptionId = gnssLocationProviderSec2.mTelephonyManager.createForSubscriptionId(subscriptionId2);
                                String simOperator = createForSubscriptionId.getSimOperator(subscriptionId2);
                                CarrierConfig carrierConfig = gnssLocationProviderSec2.mCarrierConfig;
                                carrierConfig.mSimOperator = simOperator;
                                carrierConfig.mCarrier = carrierConfig.getCarrier();
                                StringBuilder m2 = BatteryService$$ExternalSyntheticOutline0.m(subscriptionId2, "SubscriptionId is ", ", SimOperatorName = ");
                                m2.append(createForSubscriptionId.getSimOperatorName(subscriptionId2));
                                m2.append(", SimOperator = ");
                                m2.append(simOperator);
                                Log.d("GnssLocationProvider_ex", m2.toString());
                                HashMap hashMap = gnssLocationProviderSec2.mSimInformationHashMap;
                                Integer valueOf = Integer.valueOf(subscriptionId2);
                                gnssLocationProviderSec2.mCarrierConfig.getConfigMap();
                                SimInformationForDsds simInformationForDsds2 = new SimInformationForDsds();
                                simInformationForDsds2.mSlotId = -1;
                                simInformationForDsds2.mSimOperator = simOperator;
                                simInformationForDsds2.mPhone = createForSubscriptionId;
                                hashMap.put(valueOf, simInformationForDsds2);
                            }
                        }
                        boolean equals = "LOADED".equals(stringExtra2);
                        GnssVendorConfig gnssVendorConfig = gnssLocationProviderSec2.mGnssVendorConfig;
                        if (!equals) {
                            gnssVendorConfig.getClass();
                            if (!GnssVendorConfig.isIzatServiceEnabled() && gnssLocationProviderSec2.mCarrierConfig.isUSAMarket()) {
                                gnssLocationProviderSec2.sendExtraConfigurationString();
                                gnssLocationProviderSec2.setLppBitMask(gnssLocationProviderSec2.mSimSlotId);
                                break;
                            }
                        } else {
                            gnssVendorConfig.getClass();
                            if (!GnssVendorConfig.isIzatServiceEnabled()) {
                                gnssLocationProviderSec2.setSuplServerSec();
                                gnssLocationProviderSec2.sendExtraConfigurationString();
                                break;
                            } else {
                                gnssLocationProviderSec2.setSuplServerSec();
                                InputManager inputManager = (InputManager) gnssLocationProviderSec2.mContext.getSystemService(InputManager.class);
                                if (inputManager != null) {
                                    int semGetLidState = inputManager.semGetLidState();
                                    gnssLocationProviderSec2.mLidState = semGetLidState;
                                    if (semGetLidState > -1) {
                                        GestureWakeup$$ExternalSyntheticOutline0.m(new StringBuilder("Folderable phone - simstate change. lidstate "), gnssLocationProviderSec2.mLidState, "GnssLocationProvider_ex");
                                        gnssLocationProviderSec2.sendLidState(gnssLocationProviderSec2.mLidState);
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                    case 4:
                        GnssLocationProviderSec gnssLocationProviderSec3 = GnssLocationProviderSec.this;
                        gnssLocationProviderSec3.mGnssVendorConfig.getClass();
                        if (!GnssVendorConfig.isIzatServiceEnabled()) {
                            gnssLocationProviderSec3.mGnssNative.gnssConfigurationUpdateSec("EMERGENCY_STATE=SMS");
                            break;
                        } else {
                            gnssLocationProviderSec3.postWithWakeLockHeld(new GnssLocationProviderSec$$ExternalSyntheticLambda3(gnssLocationProviderSec3, 0));
                            break;
                        }
                    case 5:
                        GnssLocationProviderSec.this.mGnssVendorConfig.getClass();
                        if (GnssVendorConfig.isIzatServiceEnabled() && !GnssLocationProviderSec.this.mCarrierConfig.isKoreaMarket()) {
                            SuplInitHandler suplInitHandler2 = GnssLocationProviderSec.this.mSuplInitHandler;
                            suplInitHandler2.getClass();
                            int intExtra2 = intent.getIntExtra("PHONE_ID", -1);
                            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(intExtra2, "IMS_REGISTRATION SLOT_ID = ", "SuplInitHandler");
                            if (intExtra2 >= 0 && intExtra2 <= 2) {
                                boolean booleanExtra = intent.getBooleanExtra("REGISTERED", false);
                                String stringExtra3 = intent.getStringExtra("SERVICE");
                                Log.d("SuplInitHandler", "IMS service capabilities : " + stringExtra3);
                                Log.d("SuplInitHandler", "IMS registration error code : " + intent.getIntExtra("SIP_ERROR", 0));
                                if (stringExtra3.contains("smsip")) {
                                    suplInitHandler2.mImsRegistered[intExtra2] = booleanExtra;
                                    Log.d("SuplInitHandler", "IMS smsip[" + intExtra2 + "] registered " + booleanExtra);
                                    break;
                                }
                            }
                        } else {
                            GnssLocationProviderSec.this.mGnssVendorConfig.getClass();
                            if (!GnssVendorConfig.isIzatServiceEnabled() && GnssLocationProviderSec.this.mCarrierConfig.isUsaVerizon()) {
                                GnssLocationProviderSec gnssLocationProviderSec4 = GnssLocationProviderSec.this;
                                SuplInitHandler suplInitHandler3 = gnssLocationProviderSec4.mSuplInitHandler;
                                boolean inEmergency = gnssLocationProviderSec4.mNIHandler.getInEmergency(0L);
                                suplInitHandler3.getClass();
                                if (intent.getBooleanExtra("VOWIFI", false)) {
                                    boolean booleanExtra2 = intent.getBooleanExtra("REGISTERED", false);
                                    GnssNative gnssNative2 = suplInitHandler3.mGnssNative;
                                    if (!booleanExtra2) {
                                        if (suplInitHandler3.isEmergencyVowifiRegistration) {
                                            Log.d("SuplInitHandler", "VoWIFI for emergency is deregistered ");
                                            suplInitHandler3.isEmergencyVowifiRegistration = false;
                                            gnssNative2.gnssConfigurationUpdateSec("VOWIFI_REGISTRATION=FALSE");
                                            break;
                                        }
                                    } else if (inEmergency && !suplInitHandler3.isEmergencyVowifiRegistration) {
                                        Log.d("SuplInitHandler", "VoWIFI for emergency is registered ");
                                        suplInitHandler3.isEmergencyVowifiRegistration = true;
                                        gnssNative2.gnssConfigurationUpdateSec("VOWIFI_REGISTRATION=TRUE");
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                    case 6:
                        GnssLocationProviderSec gnssLocationProviderSec5 = GnssLocationProviderSec.this;
                        gnssLocationProviderSec5.getClass();
                        Bundle extras = intent.getExtras();
                        if (!gnssLocationProviderSec5.isIssueTrackerIntentReceived && extras != null) {
                            boolean z = extras.getBoolean("ONOFF", false);
                            AccessibilityManagerService$$ExternalSyntheticOutline0.m("INTENT_ISSUE_TRACKER_ONOFF enabled = ", "GnssLocationProvider_ex", z);
                            if (z) {
                                SystemProperties.set("dev.gnss.silentloggingIssueTracker", "ON");
                            } else {
                                SystemProperties.set("dev.gnss.silentloggingIssueTracker", "OFF");
                            }
                            gnssLocationProviderSec5.isIssueTrackerIntentReceived = true;
                            break;
                        } else {
                            Log.d("GnssLocationProvider_ex", "INTENT_ISSUE_TRACKER_ONOFF intent has been ignored because it's processed only once after booting.");
                            break;
                        }
                    case 7:
                        SuplInitHandler suplInitHandler4 = GnssLocationProviderSec.this.mSuplInitHandler;
                        suplInitHandler4.getClass();
                        suplInitHandler4.handleSuplInit(new SuplInitHandler$$ExternalSyntheticLambda1(suplInitHandler4, intent, i2), intent.getIntExtra("android.telephony.extra.SLOT_INDEX", -1));
                        break;
                }
            }
        };
        this.mSimInformationHashMap = new HashMap();
        this.mConnectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v14, types: [com.android.server.location.gnss.GnssLocationProviderSec$2] */
    /* JADX WARN: Type inference failed for: r13v2, types: [android.content.BroadcastReceiver, com.android.server.location.gnss.GnssLocationProviderSec$1] */
    public GnssLocationProviderSec(Context context, Injector injector, GnssNative gnssNative, GnssMetrics gnssMetrics) {
        super(context, injector, gnssNative, gnssMetrics);
        this.mIsSKApplicationFramework = false;
        this.mIsKtGps = false;
        this.ktPositionMode = 1;
        this.mServerTypeI = -1;
        this.prevCpAgpsMask = -1;
        this.prevLppMask = -1;
        this.prevNrLppMask = -1;
        this.mConstellationString = new String[]{"Unknown", "GPS", "SBAS", "GLONASS", "QZSS", "BEIDOU", "GALILEO", "NAVIC"};
        this.mFocusToneGenerator = null;
        this.isSehRefLocation = true;
        this.mEquipmentTestModeEnabled = false;
        this.prevLocationElapsedRealtimeNs = 0L;
        ?? r13 = new BroadcastReceiver() { // from class: com.android.server.location.gnss.GnssLocationProviderSec.1
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                char c;
                int i = 1;
                int i2 = 0;
                String action = intent.getAction();
                DualAppManagerService$$ExternalSyntheticOutline0.m("receive broadcast intent, action: ", action, "GnssLocationProvider_ex");
                if (action == null) {
                }
                switch (action.hashCode()) {
                    case -1721100884:
                        if (action.equals("com.samsung.carrier.action.CARRIER_CHANGED")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -873963303:
                        if (action.equals("android.provider.Telephony.WAP_PUSH_RECEIVED")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -343630553:
                        if (action.equals("android.net.wifi.STATE_CHANGE")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case -229777127:
                        if (action.equals(Constants.SIM_STATE_CHANGED)) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 602272191:
                        if (action.equals("com.samsung.intent.action.EMERGENCY_SMS_OVER_IMS")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1309122817:
                        if (action.equals("com.samsung.ims.action.IMS_REGISTRATION")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1949861786:
                        if (action.equals("com.sec.android.ISSUE_TRACKER_ONOFF")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2142067319:
                        if (action.equals("android.intent.action.DATA_SMS_RECEIVED")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        GnssLocationProviderSec gnssLocationProviderSec = GnssLocationProviderSec.this;
                        gnssLocationProviderSec.getClass();
                        String stringExtra = intent.getStringExtra("com.samsung.carrier.extra.CARRIER_STATE");
                        int intExtra = intent.getIntExtra("com.samsung.carrier.extra.CARRIER_PHONE_ID", -1);
                        Log.d("GnssLocationProvider_ex", "slotid=" + intExtra + " carrier state=" + stringExtra);
                        gnssLocationProviderSec.mGnssVendorConfig.getClass();
                        if (!GnssVendorConfig.isIzatServiceEnabled()) {
                            if ("LOADED".equals(stringExtra)) {
                                int subscriptionId = SubscriptionManager.getSubscriptionId(intExtra);
                                AccessibilityManagerService$$ExternalSyntheticOutline0.m(subscriptionId, intExtra, "Sim subId from slotId. subId=", ", slotId=", "GnssLocationProvider_ex");
                                if (SubscriptionManager.isValidSubscriptionId(subscriptionId)) {
                                    NetworkScorerAppManager$$ExternalSyntheticOutline0.m(subscriptionId, "CarrierFeature LOADED. it's sub Id=", "GnssLocationProvider_ex");
                                    SimInformationForDsds simInformationForDsds = (SimInformationForDsds) gnssLocationProviderSec.mSimInformationHashMap.get(Integer.valueOf(subscriptionId));
                                    if (simInformationForDsds != null) {
                                        simInformationForDsds.mSlotId = intExtra;
                                        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(intExtra, "Set Sim slotID to SimInformationForDSDS, slotId=", "SimInfo=");
                                        m.append(simInformationForDsds.toString());
                                        Log.d("GnssLocationProvider_ex", m.toString());
                                    } else {
                                        Log.d("GnssLocationProvider_ex", "SimInformation was null");
                                    }
                                }
                                gnssLocationProviderSec.setCpAgpsProfile(intExtra);
                                gnssLocationProviderSec.setLppBitMask(intExtra);
                                break;
                            }
                        } else if (("UPDATED".equals(stringExtra) || "LOADED".equals(stringExtra)) && intExtra >= 0) {
                            gnssLocationProviderSec.mGnssNative.gnssConfigurationUpdateSec(VibrationParam$1$$ExternalSyntheticOutline0.m(intExtra, "CARRIER_STATE_CHANGED="));
                            break;
                        }
                        break;
                    case 1:
                        SuplInitHandler suplInitHandler = GnssLocationProviderSec.this.mSuplInitHandler;
                        suplInitHandler.getClass();
                        suplInitHandler.handleSuplInit(new SuplInitHandler$$ExternalSyntheticLambda1(suplInitHandler, intent, i), intent.getIntExtra("android.telephony.extra.SLOT_INDEX", -1));
                        break;
                    case 2:
                        GnssLocationProviderSec.this.getClass();
                        NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
                        if (networkInfo.getState() != NetworkInfo.State.CONNECTED) {
                            if (GnssLocationProviderSec.isWlanApConnected) {
                                Log.d("GnssLocationProvider_ex", "WIFI is DISCONNECTED.");
                                GnssLocationProviderSec.isWlanApConnected = false;
                                break;
                            }
                        } else if (!GnssLocationProviderSec.isWlanApConnected) {
                            Log.d("GnssLocationProvider_ex", "WIFI NetworkInfo: " + networkInfo);
                            GnssLocationProviderSec.isWlanApConnected = true;
                            break;
                        }
                        break;
                    case 3:
                        GnssLocationProviderSec gnssLocationProviderSec2 = GnssLocationProviderSec.this;
                        gnssLocationProviderSec2.getClass();
                        String stringExtra2 = intent.getStringExtra("ss");
                        DualAppManagerService$$ExternalSyntheticOutline0.m("SIM_STATE_CHANGED received : ", stringExtra2, "GnssLocationProvider_ex");
                        SubscriptionManager from = SubscriptionManager.from(gnssLocationProviderSec2.mContext);
                        gnssLocationProviderSec2.mSimInformationHashMap.clear();
                        List<SubscriptionInfo> activeSubscriptionInfoList = from.getActiveSubscriptionInfoList();
                        if (activeSubscriptionInfoList != null) {
                            Iterator<SubscriptionInfo> it = activeSubscriptionInfoList.iterator();
                            while (it.hasNext()) {
                                int subscriptionId2 = it.next().getSubscriptionId();
                                TelephonyManager createForSubscriptionId = gnssLocationProviderSec2.mTelephonyManager.createForSubscriptionId(subscriptionId2);
                                String simOperator = createForSubscriptionId.getSimOperator(subscriptionId2);
                                CarrierConfig carrierConfig = gnssLocationProviderSec2.mCarrierConfig;
                                carrierConfig.mSimOperator = simOperator;
                                carrierConfig.mCarrier = carrierConfig.getCarrier();
                                StringBuilder m2 = BatteryService$$ExternalSyntheticOutline0.m(subscriptionId2, "SubscriptionId is ", ", SimOperatorName = ");
                                m2.append(createForSubscriptionId.getSimOperatorName(subscriptionId2));
                                m2.append(", SimOperator = ");
                                m2.append(simOperator);
                                Log.d("GnssLocationProvider_ex", m2.toString());
                                HashMap hashMap = gnssLocationProviderSec2.mSimInformationHashMap;
                                Integer valueOf = Integer.valueOf(subscriptionId2);
                                gnssLocationProviderSec2.mCarrierConfig.getConfigMap();
                                SimInformationForDsds simInformationForDsds2 = new SimInformationForDsds();
                                simInformationForDsds2.mSlotId = -1;
                                simInformationForDsds2.mSimOperator = simOperator;
                                simInformationForDsds2.mPhone = createForSubscriptionId;
                                hashMap.put(valueOf, simInformationForDsds2);
                            }
                        }
                        boolean equals = "LOADED".equals(stringExtra2);
                        GnssVendorConfig gnssVendorConfig = gnssLocationProviderSec2.mGnssVendorConfig;
                        if (!equals) {
                            gnssVendorConfig.getClass();
                            if (!GnssVendorConfig.isIzatServiceEnabled() && gnssLocationProviderSec2.mCarrierConfig.isUSAMarket()) {
                                gnssLocationProviderSec2.sendExtraConfigurationString();
                                gnssLocationProviderSec2.setLppBitMask(gnssLocationProviderSec2.mSimSlotId);
                                break;
                            }
                        } else {
                            gnssVendorConfig.getClass();
                            if (!GnssVendorConfig.isIzatServiceEnabled()) {
                                gnssLocationProviderSec2.setSuplServerSec();
                                gnssLocationProviderSec2.sendExtraConfigurationString();
                                break;
                            } else {
                                gnssLocationProviderSec2.setSuplServerSec();
                                InputManager inputManager = (InputManager) gnssLocationProviderSec2.mContext.getSystemService(InputManager.class);
                                if (inputManager != null) {
                                    int semGetLidState = inputManager.semGetLidState();
                                    gnssLocationProviderSec2.mLidState = semGetLidState;
                                    if (semGetLidState > -1) {
                                        GestureWakeup$$ExternalSyntheticOutline0.m(new StringBuilder("Folderable phone - simstate change. lidstate "), gnssLocationProviderSec2.mLidState, "GnssLocationProvider_ex");
                                        gnssLocationProviderSec2.sendLidState(gnssLocationProviderSec2.mLidState);
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                    case 4:
                        GnssLocationProviderSec gnssLocationProviderSec3 = GnssLocationProviderSec.this;
                        gnssLocationProviderSec3.mGnssVendorConfig.getClass();
                        if (!GnssVendorConfig.isIzatServiceEnabled()) {
                            gnssLocationProviderSec3.mGnssNative.gnssConfigurationUpdateSec("EMERGENCY_STATE=SMS");
                            break;
                        } else {
                            gnssLocationProviderSec3.postWithWakeLockHeld(new GnssLocationProviderSec$$ExternalSyntheticLambda3(gnssLocationProviderSec3, 0));
                            break;
                        }
                    case 5:
                        GnssLocationProviderSec.this.mGnssVendorConfig.getClass();
                        if (GnssVendorConfig.isIzatServiceEnabled() && !GnssLocationProviderSec.this.mCarrierConfig.isKoreaMarket()) {
                            SuplInitHandler suplInitHandler2 = GnssLocationProviderSec.this.mSuplInitHandler;
                            suplInitHandler2.getClass();
                            int intExtra2 = intent.getIntExtra("PHONE_ID", -1);
                            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(intExtra2, "IMS_REGISTRATION SLOT_ID = ", "SuplInitHandler");
                            if (intExtra2 >= 0 && intExtra2 <= 2) {
                                boolean booleanExtra = intent.getBooleanExtra("REGISTERED", false);
                                String stringExtra3 = intent.getStringExtra("SERVICE");
                                Log.d("SuplInitHandler", "IMS service capabilities : " + stringExtra3);
                                Log.d("SuplInitHandler", "IMS registration error code : " + intent.getIntExtra("SIP_ERROR", 0));
                                if (stringExtra3.contains("smsip")) {
                                    suplInitHandler2.mImsRegistered[intExtra2] = booleanExtra;
                                    Log.d("SuplInitHandler", "IMS smsip[" + intExtra2 + "] registered " + booleanExtra);
                                    break;
                                }
                            }
                        } else {
                            GnssLocationProviderSec.this.mGnssVendorConfig.getClass();
                            if (!GnssVendorConfig.isIzatServiceEnabled() && GnssLocationProviderSec.this.mCarrierConfig.isUsaVerizon()) {
                                GnssLocationProviderSec gnssLocationProviderSec4 = GnssLocationProviderSec.this;
                                SuplInitHandler suplInitHandler3 = gnssLocationProviderSec4.mSuplInitHandler;
                                boolean inEmergency = gnssLocationProviderSec4.mNIHandler.getInEmergency(0L);
                                suplInitHandler3.getClass();
                                if (intent.getBooleanExtra("VOWIFI", false)) {
                                    boolean booleanExtra2 = intent.getBooleanExtra("REGISTERED", false);
                                    GnssNative gnssNative2 = suplInitHandler3.mGnssNative;
                                    if (!booleanExtra2) {
                                        if (suplInitHandler3.isEmergencyVowifiRegistration) {
                                            Log.d("SuplInitHandler", "VoWIFI for emergency is deregistered ");
                                            suplInitHandler3.isEmergencyVowifiRegistration = false;
                                            gnssNative2.gnssConfigurationUpdateSec("VOWIFI_REGISTRATION=FALSE");
                                            break;
                                        }
                                    } else if (inEmergency && !suplInitHandler3.isEmergencyVowifiRegistration) {
                                        Log.d("SuplInitHandler", "VoWIFI for emergency is registered ");
                                        suplInitHandler3.isEmergencyVowifiRegistration = true;
                                        gnssNative2.gnssConfigurationUpdateSec("VOWIFI_REGISTRATION=TRUE");
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                    case 6:
                        GnssLocationProviderSec gnssLocationProviderSec5 = GnssLocationProviderSec.this;
                        gnssLocationProviderSec5.getClass();
                        Bundle extras = intent.getExtras();
                        if (!gnssLocationProviderSec5.isIssueTrackerIntentReceived && extras != null) {
                            boolean z = extras.getBoolean("ONOFF", false);
                            AccessibilityManagerService$$ExternalSyntheticOutline0.m("INTENT_ISSUE_TRACKER_ONOFF enabled = ", "GnssLocationProvider_ex", z);
                            if (z) {
                                SystemProperties.set("dev.gnss.silentloggingIssueTracker", "ON");
                            } else {
                                SystemProperties.set("dev.gnss.silentloggingIssueTracker", "OFF");
                            }
                            gnssLocationProviderSec5.isIssueTrackerIntentReceived = true;
                            break;
                        } else {
                            Log.d("GnssLocationProvider_ex", "INTENT_ISSUE_TRACKER_ONOFF intent has been ignored because it's processed only once after booting.");
                            break;
                        }
                    case 7:
                        SuplInitHandler suplInitHandler4 = GnssLocationProviderSec.this.mSuplInitHandler;
                        suplInitHandler4.getClass();
                        suplInitHandler4.handleSuplInit(new SuplInitHandler$$ExternalSyntheticLambda1(suplInitHandler4, intent, i2), intent.getIntExtra("android.telephony.extra.SLOT_INDEX", -1));
                        break;
                }
            }
        };
        this.mBroadcastReceiverSec = r13;
        this.mSimInformationHashMap = new HashMap();
        Log.d("GnssLocationProvider_ex", "Constructor");
        Log.d("GnssLocationProvider_ex", "init_GnssLocationProviderSec()");
        this.mPropertiesSecgps = new Properties();
        this.mPropertiesNsFlp = new Properties();
        this.mConnectivityManager = (ConnectivityManager) this.mContext.getSystemService("connectivity");
        this.mTelephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
        String string = SemCarrierFeature.getInstance().getString(this.mSimSlotId, "CarrierFeature_GPS_ConfigAgpsSetting", "", false);
        string = (string == null || string.length() == 0) ? SystemProperties.get("ro.csc.sales_code") : string;
        CarrierConfig carrierConfig = CarrierConfig.getInstance();
        this.mCarrierConfig = carrierConfig;
        carrierConfig.mSalesCode = string;
        carrierConfig.mCarrier = carrierConfig.getCarrier();
        this.mCarrierConfig.mDeviceMode = SystemProperties.get("ro.build.characteristics");
        this.mGnssVendorConfig.getClass();
        boolean isIzatServiceEnabled = GnssVendorConfig.isIzatServiceEnabled();
        Handler handler = this.mHandler;
        if (!isIzatServiceEnabled) {
            new LppeFusedLocationHelper(this.mContext, this.mGnssNative, handler.getLooper());
        }
        Properties propertiesFromFile = getPropertiesFromFile("/data/system/gps/secgps.conf");
        if (!propertiesFromFile.isEmpty()) {
            String property = propertiesFromFile.getProperty("SERVER_TYPE");
            if (property == null) {
                Log.d("GnssLocationProvider_ex", "No params for SERVER_TYPE in AngryGPS.");
            } else {
                this.mServerTypeI = Integer.parseInt(property);
                GestureWakeup$$ExternalSyntheticOutline0.m(new StringBuilder("Auto Config in AngryGPS : "), this.mServerTypeI, "GnssLocationProvider_ex");
            }
            String property2 = propertiesFromFile.getProperty("EquipmentTestMode");
            if (property2 == null) {
                Log.d("GnssLocationProvider_ex", "EquipmentTestMode is false");
            } else {
                this.mEquipmentTestModeEnabled = Boolean.parseBoolean(property2);
                RCPManagerService$$ExternalSyntheticOutline0.m("GnssLocationProvider_ex", new StringBuilder("EquipmentTestMode is set to "), this.mEquipmentTestModeEnabled);
            }
        }
        if (GnssVendorConfig.isIzatServiceEnabled()) {
            new CtsRestrictModeFileObserver(new File("/sys/class/sensors/ssc_core/operation_mode")).startWatching();
            InputManager inputManager = (InputManager) this.mContext.getSystemService(InputManager.class);
            if (inputManager != null) {
                int semGetLidState = inputManager.semGetLidState();
                this.mLidState = semGetLidState;
                if (semGetLidState > -1) {
                    Log.d("GnssLocationProvider_ex", "Foldable phone");
                    inputManager.semRegisterOnLidStateChangedListener(new InputLidStateChangedListener(), handler);
                }
            }
        }
        this.mCellInfoCb = new TelephonyManager.CellInfoCallback() { // from class: com.android.server.location.gnss.GnssLocationProviderSec.2
            /* JADX WARN: Removed duplicated region for block: B:18:0x008a A[RETURN] */
            /* JADX WARN: Removed duplicated region for block: B:19:0x008b  */
            @Override // android.telephony.TelephonyManager.CellInfoCallback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onCellInfo(java.util.List r27) {
                /*
                    Method dump skipped, instructions count: 463
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.location.gnss.GnssLocationProviderSec.AnonymousClass2.onCellInfo(java.util.List):void");
            }
        };
        this.mSuplInitHandler = new SuplInitHandler(context, this, gnssNative);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.DATA_SMS_RECEIVED");
        intentFilter.addDataScheme("sms");
        intentFilter.addDataAuthority("localhost", "7275");
        this.mContext.registerReceiver(r13, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.provider.Telephony.WAP_PUSH_RECEIVED");
        try {
            intentFilter2.addDataType("application/vnd.omaloc-supl-init");
        } catch (IntentFilter.MalformedMimeTypeException unused) {
            Log.w("GnssLocationProvider_ex", "Malformed SUPL init mime type");
        }
        this.mContext.registerReceiver(this.mBroadcastReceiverSec, intentFilter2);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("android.net.wifi.STATE_CHANGE");
        intentFilter3.addAction(Constants.SIM_STATE_CHANGED);
        intentFilter3.addAction("com.sec.android.ISSUE_TRACKER_ONOFF");
        intentFilter3.addAction("com.samsung.carrier.action.CARRIER_CHANGED");
        this.mContext.registerReceiver(this.mBroadcastReceiverSec, intentFilter3);
        IntentFilter intentFilter4 = new IntentFilter();
        intentFilter4.setPriority(1000);
        intentFilter4.addAction("com.samsung.ims.action.IMS_REGISTRATION");
        this.mContext.registerReceiver(this.mBroadcastReceiverSec, intentFilter4);
        IntentFilter intentFilter5 = new IntentFilter();
        intentFilter5.setPriority(1000);
        intentFilter5.addAction("com.samsung.intent.action.EMERGENCY_SMS_OVER_IMS");
        this.mContext.registerReceiver(this.mBroadcastReceiverSec, intentFilter5);
    }

    public static void createDirectories() {
        File file = new File("/data/system/gps");
        if (file.exists() || file.mkdirs()) {
            return;
        }
        Log.e("GnssLocationProvider_ex", "Directory /data/system/gps creation failed.");
    }

    public static File createFile(String str) {
        File file = new File(str);
        if (!file.exists()) {
            Log.e("GnssLocationProvider_ex", str + " file doesn't exist. create result = " + file.createNewFile());
        }
        return file;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0044  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getConfigValuesFromLine(java.lang.String r5, java.lang.String r6) {
        /*
            java.lang.String r0 = "?"
            boolean r0 = r5.contains(r0)
            java.lang.String r1 = "\n"
            java.lang.String r2 = ""
            if (r0 == 0) goto L3d
            r0 = 1
            r3 = 0
            java.lang.String r0 = com.android.server.DropBoxManagerService$EntryFile$$ExternalSyntheticOutline0.m(r0, r3, r6)
            boolean r3 = r5.startsWith(r0)
            if (r3 == 0) goto L3d
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r0)
            java.lang.String r0 = "?_"
            r4.append(r0)
            java.lang.String r0 = r4.toString()
            java.lang.String r0 = r5.replace(r0, r2)
            r3.append(r0)
            r3.append(r1)
            java.lang.String r0 = r3.toString()
            goto L3e
        L3d:
            r0 = r2
        L3e:
            boolean r3 = r5.startsWith(r6)
            if (r3 == 0) goto L68
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r6)
            java.lang.String r6 = "_"
            r3.append(r6)
            java.lang.String r6 = r3.toString()
            java.lang.String r5 = r5.replace(r6, r2)
            r0.append(r5)
            r0.append(r1)
            java.lang.String r0 = r0.toString()
        L68:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.location.gnss.GnssLocationProviderSec.getConfigValuesFromLine(java.lang.String, java.lang.String):java.lang.String");
    }

    public static int getIntCarrierFeature(int i, String str) {
        return SemCarrierFeature.getInstance().getInt(i, str, -1, false);
    }

    public static String getLppCapabilityString(int i) {
        if (i == -1) {
            return "[]";
        }
        ArrayList arrayList = new ArrayList();
        if ((i & 1) == 1) {
            arrayList.add("AGNSS");
        }
        if ((i & 2) == 2) {
            arrayList.add("OTDOA");
        }
        if ((i & 4) == 4) {
            arrayList.add("ECID");
        }
        if ((i & 8) == 8) {
            arrayList.add("CONVENTIONAL_GPS");
        }
        if ((i & 16) == 16) {
            arrayList.add("LPP_EXTENSION_CP");
        }
        if ((i & 32) == 32) {
            arrayList.add("INTER_FREQ_OTDOA");
        }
        if ((i & 64) == 64) {
            arrayList.add("LPP_EXTENSION_UP");
        }
        if (arrayList.size() == 0) {
            arrayList.add("NONE");
        }
        return arrayList.toString();
    }

    public static Properties getPropertiesFromFile(String str) {
        FileInputStream fileInputStream;
        Properties properties = new Properties();
        try {
            fileInputStream = new FileInputStream(new File(str));
        } catch (FileNotFoundException unused) {
            Log.w("GnssLocationProvider_ex", "Could not open configuration file ".concat(str));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException unused2) {
            Log.w("GnssLocationProvider_ex", "Could not load configuration file " + str + "due to IllegalArgumentException");
        } catch (SecurityException unused3) {
            Log.w("GnssLocationProvider_ex", "Could not access configuration file ".concat(str));
        }
        try {
            properties.load(fileInputStream);
            fileInputStream.close();
            return properties;
        } catch (Throwable th) {
            try {
                fileInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public static void storeFile(String str, Properties properties, String str2, File file) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                properties.store(fileOutputStream, str2);
                Log.d("GnssLocationProvider_ex", "Saved: ".concat(str));
                fileOutputStream.close();
            } catch (Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException | ClassCastException e) {
            e.printStackTrace();
        }
    }

    public static void storePropertiesToFile(String str, Properties properties, String str2) {
        try {
            createDirectories();
            File createFile = createFile(str);
            if (!createFile.setReadable(true, false)) {
                Log.e("GnssLocationProvider_ex", "file.setReadable() failed.");
            }
            if (!createFile.setWritable(true, false)) {
                Log.e("GnssLocationProvider_ex", "file.setWritable() failed.");
            }
            storeFile(str, properties, str2, createFile);
        } catch (IOException | SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override // com.android.server.location.gnss.GnssLocationProvider
    public final void changeNlpAccuracyInForce(Location location) {
        if (this.mCarrierConfig.isChinaCarrier()) {
            this.mGnssVendorConfig.getClass();
            if (GnssVendorConfig.isIzatServiceEnabled() && location.hasAccuracy() && location.getAccuracy() < 1000.0f) {
                location.setAccuracy(1000.0f);
            }
            Log.d("GnssLocationProvider_ex", "changeNlpAccuracyInForce.");
        }
    }

    @Override // com.android.server.location.gnss.GnssLocationProvider
    public final void dumpSec(PrintWriter printWriter) {
        this.mGnssVendorConfig.getClass();
        if (GnssVendorConfig.isIzatServiceEnabled()) {
            printWriter.println("============ GPS Carrier Feature State ============");
            StringBuilder sb = new StringBuilder();
            ArrayList arrayList = new ArrayList();
            int i = SystemProperties.getInt("ro.multisim.simslotcount", 1);
            for (int i2 = 0; i2 < i; i2++) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i2, "\n Sim slot ID  :  ", "\n AGPS Setting  :  ");
                m.append(SemCarrierFeature.getInstance().getString(i2, "CarrierFeature_GPS_ConfigAgpsSetting", "", false));
                m.append("\n SUPL Address  :  ");
                m.append(SemCarrierFeature.getInstance().getString(i2, "CarrierFeature_GPS_ConfigSuplHost", "", false));
                m.append("\n SUPL version  :  ");
                m.append(getIntCarrierFeature(i2, "CarrierFeature_GPS_ConfigSuplVersion"));
                m.append("\n AGPS Mode  :  ");
                m.append(getIntCarrierFeature(i2, "CarrierFeature_GPS_ConfigAgpsMode"));
                m.append("\n AGNSS Protocol  :  ");
                m.append(getIntCarrierFeature(i2, "CarrierFeature_GPS_ConfigAgnssProtocol"));
                m.append("\n LPPeCP  :  ");
                m.append(getIntCarrierFeature(i2, "CarrierFeature_GPS_ConfigLppeCp"));
                m.append("\n LPPeUP  :  ");
                m.append(getIntCarrierFeature(i2, "CarrierFeature_GPS_ConfigLppeUp"));
                m.append("\n ES Extension Sec  :  ");
                m.append(getIntCarrierFeature(i2, "CarrierFeature_GPS_ConfigEsExtensionSec"));
                m.append("\n Exception mask for Agnss  :  ");
                m.append(getIntCarrierFeature(i2, "CarrierFeature_GPS_ConfigExceptionMaskForAGNSS"));
                m.append("\n");
                arrayList.add(m.toString());
            }
            sb.append(arrayList);
            sb.append("\n");
            printWriter.println(sb.toString());
            File file = new File("/data/system/gps/secgps.conf");
            printWriter.println("============ GPS SECGPS CONFIGURATION State ============");
            if (file.exists()) {
                StringBuilder sb2 = new StringBuilder();
                Properties propertiesFromFile = getPropertiesFromFile("/data/system/gps/secgps.conf");
                sb2.append("\n Time out  :  " + propertiesFromFile.getProperty("TIMEOUT") + "\n AGPS Mode :  " + propertiesFromFile.getProperty("AGPS_MODE") + "\n LPPe CP  :  " + propertiesFromFile.getProperty("LPPE_CP_TECHNOLOGY") + "\n LPPe UP  :  " + propertiesFromFile.getProperty("LPPE_UP_TECHNOLOGY") + "\n XTRA Enable  :  " + propertiesFromFile.getProperty("ENABLE_XTRA") + "\n SSL Enable :  " + propertiesFromFile.getProperty("SSL") + "\n Opeartion Mode  :  " + propertiesFromFile.getProperty("OPERATION_MODE") + "\n Start Mode :  " + propertiesFromFile.getProperty("START_MODE") + "\n Agnss Protocol  :  " + propertiesFromFile.getProperty("AGNSS_PROTOCOL") + "\n SUPL Port :  " + propertiesFromFile.getProperty("SUPL_PORT") + "\n SUPL Host  :  " + propertiesFromFile.getProperty("SUPL_HOST") + "\n SUPL Version   :  " + propertiesFromFile.getProperty("SUPL_VERSION") + "\n LPP Profile :  " + propertiesFromFile.getProperty("LPP_PROFILE") + "\n Enable L5  :  " + propertiesFromFile.getProperty("ENABLE_L5") + "\n Enable L5 TIS  :  " + propertiesFromFile.getProperty("ENABLE_L5_TIS") + "\n Spirent  :  " + propertiesFromFile.getProperty("SPIRENT") + "\n Week Number  :  " + propertiesFromFile.getProperty("WEEK_NUMBER") + "\n");
                sb2.append("\n");
                printWriter.println(sb2.toString());
            } else {
                printWriter.println(" There is no secgps.conf file !!!!!");
            }
            printWriter.println("\n");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x01ef  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x032a  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0348  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0357  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x036c  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x0381  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x0392  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x0427  */
    /* JADX WARN: Removed duplicated region for block: B:200:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x01c4  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x01e0  */
    @Override // com.android.server.location.gnss.GnssLocationProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getPositionModeSec(int r23) {
        /*
            Method dump skipped, instructions count: 1095
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.location.gnss.GnssLocationProviderSec.getPositionModeSec(int):int");
    }

    @Override // com.android.server.location.gnss.GnssLocationProvider
    public final void gnssConfigurationUpdateSec(String str) {
        Log.d("GnssLocationProvider_ex", "gnssConfigurationUpdateSec");
        this.mGnssNative.gnssConfigurationUpdateSec(str);
    }

    @Override // com.android.server.location.gnss.GnssLocationProvider
    public final void handleEnableSec() {
        sendExtraConfigurationString();
    }

    @Override // com.android.server.location.gnss.GnssLocationProvider
    public final void handleReportSvStatusSec(GnssStatus gnssStatus) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < gnssStatus.getSatelliteCount(); i3++) {
            if (gnssStatus.getCn0DbHz(i3) > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                int constellationType = gnssStatus.getConstellationType(i3);
                sb.append("(");
                sb.append(gnssStatus.getSvid(i3));
                sb.append(", ");
                String[] strArr = this.mConstellationString;
                sb.append((constellationType >= strArr.length || constellationType < 0) ? strArr[0] : strArr[constellationType]);
                sb.append(", ");
                sb.append(GnssMetrics.isL5Sv(gnssStatus.getCarrierFrequencyHz(i3)) ? "L5" : "L1");
                sb.append(", ");
                sb.append(String.format("%.1f", Float.valueOf(gnssStatus.getCn0DbHz(i3))));
                sb.append(", ");
                sb.append(gnssStatus.usedInFix(i3) ? 1 : 0);
                sb.append(") ");
                if (i2 % 6 == 5) {
                    sb.append("\n");
                }
                i2++;
                if (gnssStatus.usedInFix(i3)) {
                    i++;
                }
            }
        }
        Log.d("GnssLocationProvider_ex", DualAppManagerService$$ExternalSyntheticOutline0.m(i, i2, "SV Count : ", " / ", "      (PRN, Constellation, Type, SNR, Used)"));
        if (i2 != 0) {
            Log.d("GnssLocationProvider_ex", sb.toString());
        }
    }

    @Override // com.android.server.location.gnss.GnssLocationProvider
    public final boolean isDeviceBasedHybridSupported() {
        if (getIntCarrierFeature(this.mSimSlotId, "CarrierFeature_GPS_ConfigLppeCp") <= 0 || (getIntCarrierFeature(this.mSimSlotId, "CarrierFeature_GPS_ConfigLppeCp") & 1) <= 0) {
            return getIntCarrierFeature(this.mSimSlotId, "CarrierFeature_GPS_ConfigLppeUp") > 0 && (getIntCarrierFeature(this.mSimSlotId, "CarrierFeature_GPS_ConfigLppeUp") & 1) > 0;
        }
        return true;
    }

    @Override // com.android.server.location.gnss.GnssLocationProvider
    public final boolean isEquipmentTestModeEnabled() {
        return this.mEquipmentTestModeEnabled;
    }

    @Override // com.android.server.location.gnss.GnssLocationProvider
    public final boolean isExtraCommandAllowed(int i) {
        String property = this.mPropertiesSecgps.getProperty("ALLOW_EXTRA_COMMAND");
        if (property != null && property.equals("1")) {
            return true;
        }
        String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i);
        if (packagesForUid == null) {
            return false;
        }
        for (String str : packagesForUid) {
            String[] strArr = GnssConstants.EXTRA_COMMAND_APPROVED_APPS;
            for (int i2 = 0; i2 < 18; i2++) {
                if (strArr[i2].equals(str)) {
                    this.isExtraCommandFromAllowedAppRequest = true;
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.android.server.location.gnss.GnssLocationProvider
    public final boolean isKOREmergency(boolean z) {
        this.mGnssVendorConfig.getClass();
        return GnssVendorConfig.isIzatServiceEnabled() && z && this.mCarrierConfig.isKoreaMarket() && isDeviceBasedHybridSupported();
    }

    @Override // com.android.server.location.gnss.GnssLocationProvider, com.android.server.location.provider.AbstractLocationProvider
    public final void onExtraCommand(int i, String str, Bundle bundle, int i2) {
        String str2;
        String str3;
        Properties properties;
        super.onExtraCommand(i, str, bundle, i2);
        String str4 = "0";
        if ("set_csc_parameters".equals(str)) {
            Log.e("GnssLocationProvider_ex", "setCscParameters");
            if (bundle == null) {
                return;
            }
            File file = new File("/data/system/gps/cscgps.conf");
            try {
                if (file.exists()) {
                    Log.d("GnssLocationProvider_ex", " isDeleted :" + file.delete());
                }
            } catch (SecurityException unused) {
                Log.w("GnssLocationProvider_ex", " could not delete cscgps.conf file : /data/system/gps/cscgps.conf");
            }
            if ("TRUE".equals(bundle.getString("is_empty", "TRUE"))) {
                Log.d("GnssLocationProvider_ex", "extras data is empty. do not update SUPL config.");
            } else {
                Properties properties2 = new Properties();
                String string = bundle.getString("operation_mode", "");
                if ("MSBASED".equals(string)) {
                    String string2 = bundle.getString("hslp_addr", "supl.google.com");
                    int i3 = bundle.getInt("hslp_port", 7275);
                    int i4 = bundle.getInt("ssl", 1);
                    int i5 = bundle.getInt("ssl_cert", 15);
                    int i6 = bundle.getInt("supl_ver", 2);
                    StringBuilder m = StorageManagerService$$ExternalSyntheticOutline0.m(i6, "[SUPL CSC] suplAddress ", string2, " suplVersion ", " suplPort ");
                    m.append(i3);
                    m.append(" suplSslMode ");
                    m.append(i4);
                    Log.e("GnssLocationProvider_ex", m.toString());
                    properties2.setProperty("CSC_SUPL_OPMODE", string);
                    properties2.setProperty("CSC_SUPL_HOST", string2);
                    properties2.setProperty("CSC_SUPL_VER", Integer.toString(i6));
                    properties2.setProperty("CSC_SUPL_PORT", Integer.toString(i3));
                    properties2.setProperty("CSC_SUPL_SSL", Integer.toString(i4));
                    properties2.setProperty("CSC_SUPL_CERT", Integer.toString(i5));
                    str4 = "1";
                }
                properties2.setProperty("CSC_SUPL_SUPLSERVERFROMCSC", str4);
                storePropertiesToFile("/data/system/gps/cscgps.conf", properties2, "Saved CSC GPS Information");
            }
            setSuplServerSec();
            return;
        }
        boolean equals = "com.skt.intent.action.AGPS".equals(str);
        GnssVendorConfig gnssVendorConfig = this.mGnssVendorConfig;
        if (equals) {
            String string3 = bundle.getString("opType");
            String string4 = bundle.getString("cmdType");
            if ("on".equals(string4)) {
                if ("msAssisted".equals(string3) || "msBased".equals(string3)) {
                    updateSKApplicationFrameworkEnabled(true);
                }
            } else if ("off".equals(string4)) {
                updateSKApplicationFrameworkEnabled(false);
                SystemProperties.set("sys.sktgps", "0");
            }
            gnssVendorConfig.getClass();
            if (GnssVendorConfig.isIzatServiceEnabled()) {
                updateKoreanOperatorsSuplSetting();
                return;
            }
            return;
        }
        if ("setOllehServer".equals(str)) {
            String string5 = bundle.getString("host");
            int i7 = bundle.getInt("port");
            updateKTSuplServerEnabled(true);
            this.mKTSuplServerHost = string5;
            this.mKTSuplServerPort = i7;
            gnssVendorConfig.getClass();
            if (GnssVendorConfig.isIzatServiceEnabled()) {
                updateKoreanOperatorsSuplSetting();
            }
            StringBuilder sb = new StringBuilder("host :");
            sb.append(this.mKTSuplServerHost);
            sb.append(" port =");
            GestureWakeup$$ExternalSyntheticOutline0.m(sb, this.mKTSuplServerPort, "GnssLocationProvider_ex");
            return;
        }
        if ("setNativeServer".equals(str)) {
            Log.d("GnssLocationProvider_ex", "setNativeServer");
            updateKTSuplServerEnabled(false);
            gnssVendorConfig.getClass();
            if (GnssVendorConfig.isIzatServiceEnabled()) {
                updateKoreanOperatorsSuplSetting();
                return;
            }
            return;
        }
        if ("activateGPS".equals(str)) {
            Log.d("GnssLocationProvider_ex", "activateGPS is not supported");
            return;
        }
        if ("deactivateGPS".equals(str)) {
            Log.d("GnssLocationProvider_ex", "deactivateGPS is not supported");
            return;
        }
        if ("activateNLP".equals(str)) {
            Log.d("GnssLocationProvider_ex", "activateNLP is not supported");
            return;
        }
        if ("deactivateNLP".equals(str)) {
            Log.d("GnssLocationProvider_ex", "deactivateNLP is not supported");
            return;
        }
        if ("activateAGPS".equals(str)) {
            Log.d("GnssLocationProvider_ex", "activateAGPS");
            Settings.Global.putInt(this.mContext.getContentResolver(), "assisted_gps_enabled", 1);
            return;
        }
        if ("deactivateAGPS".equals(str)) {
            Log.d("GnssLocationProvider_ex", "deactivateAGPS");
            Settings.Global.putInt(this.mContext.getContentResolver(), "assisted_gps_enabled", 0);
            return;
        }
        if ("setMode".equals(str)) {
            this.ktPositionMode = bundle.getInt("mode");
            GestureWakeup$$ExternalSyntheticOutline0.m(new StringBuilder("setMode ktPositionMode :"), this.ktPositionMode, "GnssLocationProvider_ex");
            return;
        }
        if ("getMode".equals(str)) {
            GestureWakeup$$ExternalSyntheticOutline0.m(new StringBuilder("getMode ktPositionMode :"), this.ktPositionMode, "GnssLocationProvider_ex");
            bundle.putInt("mode", this.ktPositionMode);
            return;
        }
        if ("set_use_udp".equals(str)) {
            SuplInitHandler suplInitHandler = this.mSuplInitHandler;
            suplInitHandler.getClass();
            Log.d("SuplInitHandler", "set_use_udp : " + bundle.getInt("use_udp"));
            if (bundle.getInt("use_udp") != 1) {
                suplInitHandler.mIsUdpListen = false;
                return;
            }
            if (suplInitHandler.mIsUdpListen) {
                return;
            }
            suplInitHandler.mIsUdpListen = true;
            if (suplInitHandler.mIsOpenUdpPort) {
                Log.d("SuplInitHandler", "UDP port is already opened.");
                return;
            } else {
                Log.d("SuplInitHandler", "start UDP socket");
                new Thread(new SuplInitHandler$$ExternalSyntheticLambda0(suplInitHandler)).start();
                return;
            }
        }
        if ("set_dcm_iot".equals(str)) {
            Log.d("GnssLocationProvider_ex", "set_dcm_iot : " + bundle.getBoolean("dcm_iot"));
            if (!bundle.getBoolean("dcm_iot")) {
                Log.d("GnssLocationProvider_ex", "Docomo SUPL IOT test = false");
                return;
            } else {
                Log.d("GnssLocationProvider_ex", "Docomo SUPL IOT test = true server = dcm-supl.com");
                updateSuplServerConfiguration(9, 7275, 1, 1, 15, "dcm-supl.com");
                return;
            }
        }
        if ("set_lpp_support".equals(str)) {
            setLppSupport(bundle.getInt("set_lpp"));
            return;
        }
        boolean equals2 = "setSecGpsConf".equals(str);
        GnssNative gnssNative = this.mGnssNative;
        if (equals2) {
            Log.d("GnssLocationProvider_ex", "setSecGpsConf");
            if (bundle == null) {
                return;
            }
            this.mPropertiesSecgps.setProperty("USE_SECGPS_CONF", "1");
            Set<String> keySet = bundle.keySet();
            int size = keySet.size();
            String[] strArr = new String[size];
            keySet.toArray(strArr);
            for (int i8 = 0; i8 < size; i8++) {
                String str5 = strArr[i8];
                if (str5 != null) {
                    this.mPropertiesSecgps.setProperty(str5, bundle.getString(str5));
                }
            }
            storePropertiesToFile("/data/system/gps/secgps.conf", this.mPropertiesSecgps, "SECGPS Configuration");
            StringWriter stringWriter = new StringWriter();
            try {
                this.mPropertiesSecgps.store(stringWriter, "SECGPS Configuration");
            } catch (IOException unused2) {
                Log.e("GnssLocationProvider_ex", "could not store to writer");
            }
            gnssVendorConfig.getClass();
            if (GnssVendorConfig.isIzatServiceEnabled()) {
                postWithWakeLockHeld(new GnssLocationProviderSec$$ExternalSyntheticLambda0(1, this, stringWriter));
                return;
            } else {
                gnssNative.gnssConfigurationUpdateSec(stringWriter.toString());
                return;
            }
        }
        if ("deleteSecGpsConf".equals(str)) {
            Log.d("GnssLocationProvider_ex", "deleteSecGpsConf");
            this.mPropertiesSecgps.setProperty("USE_SECGPS_CONF", "0");
            File file2 = new File("/data/system/gps/secgps.conf");
            try {
                if (file2.exists() && file2.delete()) {
                    Log.d("GnssLocationProvider_ex", "secgps.conf deleted.");
                }
            } catch (SecurityException unused3) {
                Log.e("GnssLocationProvider_ex", " could not access secgps.conf file : /data/system/gps/secgps.conf");
            }
            gnssVendorConfig.getClass();
            if (GnssVendorConfig.isIzatServiceEnabled()) {
                postWithWakeLockHeld(new GnssLocationProviderSec$$ExternalSyntheticLambda3(this, 1));
                return;
            } else {
                gnssNative.gnssConfigurationUpdateSec("USE_SECGPS_CONF=0");
                return;
            }
        }
        if ("gnss_configuration".equals(str)) {
            String property = this.mPropertiesSecgps.getProperty("USE_SECGPS_CONF");
            gnssVendorConfig.getClass();
            if (GnssVendorConfig.isIzatServiceEnabled() || !"1".equals(property)) {
                return;
            }
            String string6 = bundle.getString("config_string", "");
            if ("".equals(string6)) {
                return;
            }
            gnssNative.gnssConfigurationUpdateSec(string6);
            return;
        }
        if ("update_last_location".equals(str)) {
            Location location = (Location) bundle.getParcelable("last_location");
            if (location == null) {
                Log.w("GnssLocationProvider_ex", "Invalid last location info");
                return;
            }
            Log.v("GnssLocationProvider_ex", "Update last location directly to LMS !!!!!!!!!!!!, ");
            try {
                reportLocation(LocationResult.wrap(new Location[]{location}).validate());
                return;
            } catch (LocationResult.BadLocationException e) {
                e.printStackTrace();
                return;
            }
        }
        if ("gnss_configuration_from_nsflp".equals(str)) {
            String string7 = bundle.getString("config_string", "");
            if ("".equals(string7)) {
                return;
            }
            gnssNative.gnssConfigurationUpdateSec(string7);
            Scanner scanner = new Scanner(string7);
            while (scanner.hasNextLine()) {
                try {
                    String nextLine = scanner.nextLine();
                    if (nextLine != null && nextLine.length() > 0) {
                        String[] split = nextLine.split("=");
                        if (split.length > 1 && (str2 = split[0]) != null && str2.length() > 0 && (str3 = split[1]) != null && str3.length() > 0 && (properties = this.mPropertiesNsFlp) != null) {
                            properties.setProperty(split[0], split[1]);
                        }
                    }
                } catch (Throwable th) {
                    try {
                        scanner.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            }
            scanner.close();
            return;
        }
        if ("com.sec.android.ISSUE_TRACKER_ONOFF".equals(str)) {
            if (bundle == null) {
                return;
            }
            boolean z = bundle.getBoolean("ONOFF", false);
            AccessibilityManagerService$$ExternalSyntheticOutline0.m("extraCommand - com.sec.android.ISSUE_TRACKER_ONOFF, enabled = ", "GnssLocationProvider_ex", z);
            if (z) {
                SystemProperties.set("dev.gnss.silentloggingIssueTracker", "ON");
                return;
            } else {
                SystemProperties.set("dev.gnss.silentloggingIssueTracker", "OFF");
                return;
            }
        }
        if ("set_emergency_state".equals(str)) {
            gnssVendorConfig.getClass();
            if (!GnssVendorConfig.isIzatServiceEnabled()) {
                String string8 = bundle.getString("config_string", "");
                if ("".equals(string8)) {
                    return;
                }
                gnssNative.gnssConfigurationUpdateSec(string8);
                return;
            }
        }
        if ("isEquipmentTestMode".equals(str)) {
            gnssVendorConfig.getClass();
            if (!GnssVendorConfig.isIzatServiceEnabled()) {
                if (bundle != null) {
                    this.mEquipmentTestModeEnabled = bundle.getBoolean("isEquipmentTestMode", false);
                    Log.w("GnssLocationProvider_ex", "isEquipmentTestMode : " + this.mEquipmentTestModeEnabled);
                    if (this.mEquipmentTestModeEnabled) {
                        Log.w("GnssLocationProvider_ex", "Platform NTP aiding/SGEE off");
                        gnssNative.gnssConfigurationUpdateSec("AIDING_USE_NTP=0\n");
                        return;
                    } else {
                        Log.w("GnssLocationProvider_ex", "EquipmentTestMode off");
                        gnssNative.gnssConfigurationUpdateSec("AIDING_USE_NTP=1\n");
                        return;
                    }
                }
                return;
            }
        }
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m("onExtraCommand: unknown command ", str, "GnssLocationProvider_ex");
    }

    @Override // com.android.server.location.gnss.GnssLocationProvider, com.android.server.location.gnss.hal.GnssNative.LocationRequestCallbacks
    public final void onRequestRefLocation() {
        Log.d("GnssLocationProvider_ex", "onRequestRefLocation");
        this.isSehRefLocation = false;
        requestRefLocationSec();
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x0037, code lost:
    
        if (r6 != null) goto L23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:13:0x003a, code lost:
    
        r2 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0044, code lost:
    
        if (r6 != null) goto L23;
     */
    @Override // com.android.server.location.gnss.GnssLocationProvider, com.android.server.location.gnss.hal.GnssNative.AGpsCallbacks
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onRequestSetID(int r6) {
        /*
            r5 = this;
            java.lang.String r0 = "GnssLocationProvider_ex"
            java.lang.String r1 = "onRequestSetId"
            android.util.Log.d(r0, r1)
            android.content.Context r0 = r5.mContext
            java.lang.String r1 = "phone"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.telephony.TelephonyManager r0 = (android.telephony.TelephonyManager) r0
            com.android.server.location.gnss.sec.SuplInitHandler r1 = r5.mSuplInitHandler
            if (r1 == 0) goto L1e
            boolean r1 = r1.mNiSessionStarted
            if (r1 == 0) goto L1e
            int r1 = com.android.server.location.gnss.sec.SuplInitHandler.mSubIdForSuplNi
            goto L22
        L1e:
            int r1 = android.telephony.SubscriptionManager.getDefaultDataSubscriptionId()
        L22:
            boolean r2 = android.telephony.SubscriptionManager.isValidSubscriptionId(r1)
            if (r2 == 0) goto L2c
            android.telephony.TelephonyManager r0 = r0.createForSubscriptionId(r1)
        L2c:
            r1 = r6 & 1
            r2 = 1
            r3 = 0
            r4 = 0
            if (r1 != r2) goto L3c
            java.lang.String r6 = r0.getSubscriberId()
            if (r6 == 0) goto L3a
            goto L49
        L3a:
            r2 = r3
            goto L49
        L3c:
            r2 = 2
            r6 = r6 & r2
            if (r6 != r2) goto L47
            java.lang.String r6 = r0.getLine1Number()
            if (r6 == 0) goto L3a
            goto L49
        L47:
            r2 = r3
            r6 = r4
        L49:
            com.android.server.location.gnss.sec.GnssVendorConfig r1 = r5.mGnssVendorConfig
            if (r2 != 0) goto L62
            r1.getClass()
            boolean r3 = com.android.server.location.gnss.sec.GnssVendorConfig.isIzatServiceEnabled()
            if (r3 != 0) goto L62
            com.android.server.location.gnss.sec.CarrierConfig r3 = r5.mCarrierConfig
            boolean r3 = r3.isUsaVerizon()
            if (r3 == 0) goto L62
            java.lang.String r6 = r0.getImei()
        L62:
            r1.getClass()
            boolean r0 = com.android.server.location.gnss.sec.GnssVendorConfig.isLsiGnss()
            if (r0 == 0) goto L75
            com.android.server.location.gnss.sec.GnssHalStatus r4 = new com.android.server.location.gnss.sec.GnssHalStatus
            r4.<init>()
            r0 = 3000(0xbb8, double:1.482E-320)
            r4.triggerCheckingHalStatus(r0)
        L75:
            if (r6 != 0) goto L79
            java.lang.String r6 = ""
        L79:
            com.android.server.location.gnss.hal.GnssNative r5 = r5.mGnssNative
            r5.setAgpsSetId(r2, r6)
            if (r4 == 0) goto L83
            r4.updateHalStatusChecked()
        L83:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.location.gnss.GnssLocationProviderSec.onRequestSetID(int):void");
    }

    @Override // com.android.server.location.gnss.GnssLocationProvider
    public final void releaseDozeMode() {
        IDeviceIdleController asInterface = IDeviceIdleController.Stub.asInterface(ServiceManager.getService("deviceidle"));
        boolean isDeviceIdleMode = ((PowerManager) this.mContext.getSystemService("power")).isDeviceIdleMode();
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("Device Idle Mode=", "GnssLocationProvider_ex", isDeviceIdleMode);
        if (!isDeviceIdleMode || asInterface == null) {
            return;
        }
        Log.d("GnssLocationProvider_ex", "Release doze mode when KOR emergency request");
        try {
            asInterface.exitIdle("gnss");
        } catch (RemoteException unused) {
            Log.w("GnssLocationProvider_ex", "Failed to release doze mode");
        }
    }

    public final void requestRefLocationSec() {
        TelephonyManager telephonyManager;
        Log.d("GnssLocationProvider_ex", "requestRefLocationSec");
        if (!this.mSuplInitHandler.mNiSessionStarted) {
            TelephonyManager telephonyManager2 = (TelephonyManager) this.mContext.getSystemService("phone");
            if (telephonyManager2.getAllCellInfo() != null) {
                telephonyManager2.requestCellInfoUpdate(this.mContext.getMainExecutor(), this.mCellInfoCb);
                return;
            } else {
                Log.e("GnssLocationProvider_ex", "Error getting cell location info.");
                return;
            }
        }
        SimInformationForDsds simInformationForDsds = (SimInformationForDsds) this.mSimInformationHashMap.get(Integer.valueOf(SuplInitHandler.mSubIdForSuplNi));
        if (simInformationForDsds == null || (telephonyManager = simInformationForDsds.mPhone) == null) {
            return;
        }
        this.mTelephonyManager = telephonyManager;
        telephonyManager.requestCellInfoUpdate(this.mContext.getMainExecutor(), this.mCellInfoCb);
    }

    @Override // com.android.server.location.gnss.GnssLocationProvider
    public final void secLocationValidate(Location location, long j) {
        if (location.hasAccuracy() && location.getAccuracy() < 0.5f) {
            Log.e("GnssLocationProvider_ex", "location must have reasonable accuracy," + location.getAccuracy());
            location.setAccuracy(0.5f);
            sendAssertMessage("WARNING:Invalid Accuracy");
        }
        if (location.getElapsedRealtimeNanos() > j) {
            Log.e("GnssLocationProvider_ex", "location must not have realtime in the future," + (location.getElapsedRealtimeNanos() - j));
            location.setElapsedRealtimeNanos(j);
            sendAssertMessage("WARNING:ElapsedRealtimeNanos is greater than system time");
        }
        if (this.prevLocationElapsedRealtimeNs > location.getElapsedRealtimeNanos()) {
            Log.e("GnssLocationProvider_ex", "location must have valid monotonically increasing realtime," + (this.prevLocationElapsedRealtimeNs - location.getElapsedRealtimeNanos()));
            location.setElapsedRealtimeNanos(j);
            sendAssertMessage("WARNING:ElapsedRealtimeNanos is less than previous time");
        }
        this.prevLocationElapsedRealtimeNs = location.getElapsedRealtimeNanos();
    }

    public final void sendAssertMessage(String str) {
        this.mNSConnectionHelper.onGnssEventUpdated(XmlUtils$$ExternalSyntheticOutline0.m("G,", new SimpleDateFormat("MM-dd-yyyy HH:mm:ss", Locale.getDefault()).format(new Date(System.currentTimeMillis())), ",UNKNOWN,UNKNOWN,UNKNOWN,", str, ";git_hash=115098c;"));
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x008c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void sendExtraConfigurationString() {
        /*
            Method dump skipped, instructions count: 275
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.location.gnss.GnssLocationProviderSec.sendExtraConfigurationString():void");
    }

    public final void sendLidState(int i) {
        int i2 = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_GPS_CONFIG_FOLD_CALIBRATED");
        if (i2 <= 0) {
            return;
        }
        if (i == 0) {
            i = 1;
        } else if (i == 1) {
            i = i2;
        }
        Log.d("GnssLocationProvider_ex", "sendLidState " + i);
        postWithWakeLockHeld(new GnssLocationProviderSec$$ExternalSyntheticLambda1(this, i, 1));
    }

    public final void setAgpsReferenceLocationCellId(int i, int i2, int i3, int i4, long j, int i5, int i6, int i7) {
        if (this.isSehRefLocation) {
            Log.d("GnssLocationProvider_ex", "setAgpsReferenceLocationCellId");
            this.mGnssNative.setAgpsReferenceLocationCellId(i, i2, i3, i4, j, i5, i6);
        } else {
            Log.d("GnssLocationProvider_ex", "setRefLocation through IAGnss");
            this.mGnssNative.setAgpsReferenceLocationCellId(i, i2, i3, i4, i7, 0, i5, i6);
        }
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [boolean] */
    public final void setCpAgpsProfile(int i) {
        if (this.mCarrierConfig.isUSAMarket() || this.mCarrierConfig.isCanadaMarket()) {
            return;
        }
        ?? r4 = SemCarrierFeature.getInstance().getBoolean(i, "CarrierFeature_GPS_SupportEnableAgps", false, false);
        Log.d("GnssLocationProvider_ex", "AGPS Mask prev=" + this.prevCpAgpsMask + ", current=" + (r4 == true ? 1 : 0));
        if (r4 <= this.prevCpAgpsMask || r4 < 0) {
            return;
        }
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(r4 == true ? 1 : 0, "Set AGPS=", "GnssLocationProvider_ex");
        this.prevCpAgpsMask = r4 == true ? 1 : 0;
        this.mGnssNative.gnssConfigurationUpdateSec(ProcessList$$ExternalSyntheticOutline0.m(new StringBuilder("CP_AGPS_ENABLE_PROFILE="), r4 == true ? 1 : 0));
    }

    public final void setLppBitMask(int i) {
        SimInformationForDsds simInformationForDsds;
        int i2;
        int intCarrierFeature = getIntCarrierFeature(i, "CarrierFeature_GPS_ConfigLppBitmask");
        Log.d("GnssLocationProvider_ex", "CarrierFeature value LPPe Capability = " + getLppCapabilityString(intCarrierFeature) + ", slotID = " + i);
        Iterator it = this.mSimInformationHashMap.entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                simInformationForDsds = null;
                break;
            }
            Map.Entry entry = (Map.Entry) it.next();
            if (((SimInformationForDsds) entry.getValue()).mSlotId == i) {
                simInformationForDsds = (SimInformationForDsds) entry.getValue();
                break;
            }
        }
        if (simInformationForDsds != null) {
            String str = simInformationForDsds.mSimOperator;
            if ("".equals(str) || str == null) {
                Log.d("GnssLocationProvider_ex", "Invalid simOperator, set as default sim operator");
                String simOperator = this.mTelephonyManager.getSimOperator();
                CarrierConfig carrierConfig = this.mCarrierConfig;
                carrierConfig.mSimOperator = simOperator;
                carrierConfig.mCarrier = carrierConfig.getCarrier();
            } else {
                Log.d("GnssLocationProvider_ex", "setLppBitmask. simOperator=".concat(str));
                CarrierConfig carrierConfig2 = this.mCarrierConfig;
                carrierConfig2.mSimOperator = str;
                carrierConfig2.mCarrier = carrierConfig2.getCarrier();
            }
        }
        if (this.mCarrierConfig.isUSAMarket()) {
            if (this.mCarrierConfig == null) {
                this.mCarrierConfig = CarrierConfig.getInstance();
            }
            String str2 = this.mCarrierConfig.mDeviceMode;
            if (str2 != null ? str2.toLowerCase().contains("tablet") : false) {
                intCarrierFeature = 0;
            } else {
                CarrierConfig carrierConfig3 = this.mCarrierConfig;
                boolean z = "VZW".equals(carrierConfig3.mSalesCode) || "USC".equals(carrierConfig3.mSalesCode) || "ACG".equals(carrierConfig3.mSalesCode) || "GCF".equals(carrierConfig3.mSalesCode);
                AccessibilityManagerService$$ExternalSyntheticOutline0.m("isOTDOASupportMarket : ", "CarrierConfigForGnss", z);
                int i3 = z ? 7 : 5;
                CarrierConfig carrierConfig4 = this.mCarrierConfig;
                boolean z2 = "TMB".equals(carrierConfig4.mSalesCode) || "ATT".equals(carrierConfig4.mSalesCode) || "AIO".equals(carrierConfig4.mSalesCode) || "XAU".equals(carrierConfig4.mSalesCode) || "XAA".equals(carrierConfig4.mSalesCode) || "XAR".equals(carrierConfig4.mSalesCode) || "XAG".equals(carrierConfig4.mSalesCode) || "TMK".equals(carrierConfig4.mSalesCode) || "DSH".equals(carrierConfig4.mSalesCode) || "DSA".equals(carrierConfig4.mSalesCode) || "DSG".equals(carrierConfig4.mSalesCode) || "TFO".equals(carrierConfig4.mSalesCode) || "TFA".equals(carrierConfig4.mSalesCode) || "VZW".equals(carrierConfig4.mSalesCode) || "SPR".equals(carrierConfig4.mSalesCode) || "VMU".equals(carrierConfig4.mSalesCode) || "BST".equals(carrierConfig4.mSalesCode) || "XAS".equals(carrierConfig4.mSalesCode) || "GCF".equals(carrierConfig4.mSalesCode);
                AccessibilityManagerService$$ExternalSyntheticOutline0.m("isLppeSupportMarket : ", "CarrierConfigForGnss", z2);
                if (z2) {
                    i3 |= 16;
                }
                if (this.mCarrierConfig.isUsaVerizon()) {
                    i3 |= 64;
                }
                Log.d("GnssLocationProvider_ex", "(US market) LPPe Capability = " + getLppCapabilityString(i3));
                intCarrierFeature = i3;
            }
            i2 = getIntCarrierFeature(i, "CarrierFeature_GPS_ConfigNrLppBitmask");
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i2, "NR LPP Profile = ", "GnssLocationProvider_ex");
        } else if (this.mCarrierConfig.isCanadaMarket()) {
            if (this.mCarrierConfig == null) {
                this.mCarrierConfig = CarrierConfig.getInstance();
            }
            String str3 = this.mCarrierConfig.mDeviceMode;
            if (!(str3 != null ? str3.toLowerCase().contains("tablet") : false)) {
                Log.d("GnssLocationProvider_ex", "(Canada Market) LPPe Capability = " + getLppCapabilityString(5));
                intCarrierFeature = 5;
                i2 = -1;
            }
            intCarrierFeature = 0;
            i2 = -1;
        } else {
            if (this.mCarrierConfig.isKoreaMarket()) {
                if (this.mCarrierConfig == null) {
                    this.mCarrierConfig = CarrierConfig.getInstance();
                }
                if (this.mCarrierConfig.isKoreaSktSim() || "45006".equals(this.mCarrierConfig.mSimOperator)) {
                    Log.d("GnssLocationProvider_ex", "(KOR SKT,LGU) LPPe Capability = []");
                } else if ("45008".equals(this.mCarrierConfig.mSimOperator)) {
                    StringBuilder sb = new StringBuilder("(KOR KTT) LPPe Capability = ");
                    intCarrierFeature = 87;
                    sb.append(getLppCapabilityString(87));
                    Log.d("GnssLocationProvider_ex", sb.toString());
                }
                intCarrierFeature = 0;
            } else if (this.mCarrierConfig.mCarrier == CarrierConfig.Carrier.NO_OPERATOR && intCarrierFeature == -1) {
                Log.d("GnssLocationProvider_ex", "No Operator LPP Disable");
                i2 = -1;
                intCarrierFeature = 0;
            }
            i2 = -1;
        }
        Log.d("GnssLocationProvider_ex", "prevLppMask=" + this.prevLppMask + ", curLppMask=" + intCarrierFeature);
        if (intCarrierFeature > this.prevLppMask && intCarrierFeature >= 0) {
            this.prevLppMask = intCarrierFeature;
            setLppSupport(intCarrierFeature);
        }
        if (i2 <= this.prevNrLppMask || i2 < 0) {
            return;
        }
        this.prevNrLppMask = i2;
        this.mGnssVendorConfig.getClass();
        if (GnssVendorConfig.isIzatServiceEnabled()) {
            return;
        }
        NetworkScoreService$$ExternalSyntheticOutline0.m(i2, "setNrLppSupport(", ")", "GnssLocationProvider_ex");
        boolean z3 = this.mGpsEnabled;
        GnssNative gnssNative = this.mGnssNative;
        if (!z3 && GnssVendorConfig.isBroadcomGnss()) {
            gnssNative.init();
            Log.d("GnssLocationProvider_ex", "native_init");
        }
        if (!GnssVendorConfig.isIzatServiceEnabled()) {
            Log.d("GnssLocationProvider_ex", "configurationUpdate_setNrLppSupport, NR_LPP_PROFILE=" + i2);
            gnssNative.gnssConfigurationUpdateSec("NR_LPP_PROFILE=" + i2);
        }
        if (this.mGpsEnabled || !GnssVendorConfig.isBroadcomGnss()) {
            return;
        }
        gnssNative.cleanup();
        Log.d("GnssLocationProvider_ex", "native_cleanup ");
        gnssNative.initLocationOff();
    }

    public final void setLppSupport(int i) {
        this.mGnssVendorConfig.getClass();
        if (GnssVendorConfig.isIzatServiceEnabled()) {
            return;
        }
        NetworkScoreService$$ExternalSyntheticOutline0.m(i, "setLppSupport(", ")", "GnssLocationProvider_ex");
        boolean z = this.mGpsEnabled;
        GnssNative gnssNative = this.mGnssNative;
        if (!z && GnssVendorConfig.isBroadcomGnss()) {
            gnssNative.init();
            Log.d("GnssLocationProvider_ex", "native_init");
        }
        if (!GnssVendorConfig.isIzatServiceEnabled()) {
            Log.d("GnssLocationProvider_ex", "configurationUpdate_setLppSupport, LPP_PROFILE=" + i);
            gnssNative.gnssConfigurationUpdateSec("LPP_PROFILE=" + i);
        }
        if (this.mGpsEnabled || !GnssVendorConfig.isBroadcomGnss()) {
            return;
        }
        gnssNative.cleanup();
        Log.d("GnssLocationProvider_ex", "native_cleanup");
        gnssNative.initLocationOff();
    }

    public final void setSktSuplVer() {
        Log.d("GnssLocationProvider_ex", "setSktSuplVer : " + SystemProperties.get("ro.product.name"));
        Log.d("GnssLocationProvider_ex", "AGPS_SUPL_VER_2_0_1_AGNSS,  server = wpde.nate.com");
        updateSuplServerConfiguration(4, 7275, 0, 0, 15, "wpde.nate.com");
    }

    @Override // com.android.server.location.gnss.GnssLocationProvider
    public final void setSuplServerSec() {
        int i;
        String sb;
        Properties propertiesFromFile;
        String property;
        Log.d("GnssLocationProvider_ex", "updateSuplServerFromCSC");
        try {
            if (new File("/data/system/gps/cscgps.conf").exists() && (property = (propertiesFromFile = getPropertiesFromFile("/data/system/gps/cscgps.conf")).getProperty("CSC_SUPL_SUPLSERVERFROMCSC")) != null && property.length() != 0) {
                try {
                    if (Integer.parseInt(property) == 1) {
                        try {
                            updateSuplServerConfiguration(Integer.parseInt(propertiesFromFile.getProperty("CSC_SUPL_VER", "1")), Integer.parseInt(propertiesFromFile.getProperty("CSC_SUPL_PORT", "7276")), Integer.parseInt(propertiesFromFile.getProperty("CSC_SUPL_SSL", "0")), 0, Integer.parseInt(propertiesFromFile.getProperty("CSC_SUPL_CERT", "0")), propertiesFromFile.getProperty("CSC_SUPL_HOST"));
                            return;
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (NumberFormatException e2) {
                    e2.printStackTrace();
                }
            }
        } catch (SecurityException e3) {
            e3.printStackTrace();
        }
        this.mGnssVendorConfig.getClass();
        if (GnssVendorConfig.isIzatServiceEnabled()) {
            return;
        }
        if (GnssVendorConfig.isUnisocGnss() && this.mGnssConfiguration.isWifiOnlyModel()) {
            updateSuplServerConfiguration(3, 7275, 1, 0, 15, "supl.google.com");
            return;
        }
        if (CarrierConfig.isTmbConfigRequired(this.mCarrierConfig.getCarrier())) {
            updateSuplServerConfiguration(8, 7275, 1, 0, 15, "supl.geo.t-mobile.com");
            return;
        }
        CarrierConfig.Carrier carrier = this.mCarrierConfig.getCarrier();
        if (carrier == CarrierConfig.Carrier.USA_ATT || carrier == CarrierConfig.Carrier.USA_XAR || carrier == CarrierConfig.Carrier.USA_DSA || carrier == CarrierConfig.Carrier.USA_DSG || carrier == CarrierConfig.Carrier.USA_SPR || carrier == CarrierConfig.Carrier.USA_XAS || carrier == CarrierConfig.Carrier.USA_BST || carrier == CarrierConfig.Carrier.USA_VMU || carrier == CarrierConfig.Carrier.USA_VZW || carrier == CarrierConfig.Carrier.USA_USC || carrier == CarrierConfig.Carrier.USA_ACG || CarrierConfig.isCanadaConfigRequired(carrier)) {
            updateSuplServerConfiguration(8, 7275, 1, 0, 15, "supl.google.com");
            return;
        }
        if (this.mCarrierConfig.getCarrier() == CarrierConfig.Carrier.JPN_DCM) {
            String str = SystemProperties.get("dcm_supl_iot", "false");
            Log.d("GnssLocationProvider_ex", "Set supl server for JPN_DCM, iot value:" + str);
            if ("true".equals(str)) {
                updateSuplServerConfiguration(9, 7275, 1, 1, 0, "dcm-supl.com");
                return;
            } else {
                updateSuplServerConfiguration(3, 7275, 1, 0, 15, "supl.google.com");
                return;
            }
        }
        if (this.mCarrierConfig.getCarrier() == CarrierConfig.Carrier.JPN_KDI) {
            updateSuplServerConfiguration(2, 7275, 1, 0, 15, "location2.kddi.ne.jp");
            return;
        }
        if (this.mCarrierConfig.getCarrier() == CarrierConfig.Carrier.JPN_RKT) {
            updateSuplServerConfiguration(3, 7275, 1, 0, 15, "supl.rm-n.jp");
            return;
        }
        CarrierConfig.Carrier carrier2 = this.mCarrierConfig.getCarrier();
        if (carrier2 == CarrierConfig.Carrier.CHN_CMC || carrier2 == CarrierConfig.Carrier.CHN_CHC) {
            updateSuplServerConfiguration(1, 7275, 1, 0, 15, "221.176.0.55");
            return;
        }
        CarrierConfig.Carrier carrier3 = this.mCarrierConfig.getCarrier();
        if (carrier3 == CarrierConfig.Carrier.MEX_MNX || carrier3 == CarrierConfig.Carrier.MEX_IUS || carrier3 == CarrierConfig.Carrier.MEX_UNE) {
            updateSuplServerConfiguration(2, 7275, 1, 0, 15, "supl.attmex.mx");
            return;
        }
        if (this.mCarrierConfig.getCarrier() != CarrierConfig.Carrier.ARG_UFN) {
            updateSuplServerConfiguration(3, 7275, 1, 0, 15, "supl.google.com");
            return;
        }
        String simOperator = this.mTelephonyManager.getSimOperator();
        if (simOperator == null) {
            sb = null;
        } else {
            int i2 = 0;
            try {
                i = Integer.parseInt(simOperator.substring(0, 3));
            } catch (IndexOutOfBoundsException | NumberFormatException e4) {
                e4.printStackTrace();
                i = 0;
            }
            try {
                i2 = Integer.parseInt(simOperator.substring(3));
            } catch (IndexOutOfBoundsException | NumberFormatException e5) {
                e5.printStackTrace();
            }
            StringBuilder sb2 = new StringBuilder("h-slp.mnc");
            sb2.append(String.format(Locale.getDefault(), "%03d", Integer.valueOf(i2)));
            sb2.append(".mcc");
            sb2.append(String.format(Locale.getDefault(), "%03d", Integer.valueOf(i)));
            sb2.append(".pub.3gppnetwork.org");
            Log.d("GnssLocationProvider_ex", "makeAutoSuplUrl :" + ((Object) sb2));
            sb = sb2.toString();
        }
        String str2 = sb;
        if (str2 != null) {
            updateSuplServerConfiguration(2, 7275, 1, 0, 15, str2);
        }
    }

    @Override // com.android.server.location.gnss.GnssLocationProvider
    public final void setXtraDownloadedTime() {
        Log.d("GnssLocationProvider_ex", "setXtraDownloadedTime()");
        SystemProperties.set("persist.sys.xtra_time", Long.toString(System.currentTimeMillis()));
    }

    @Override // com.android.server.location.gnss.GnssLocationProvider
    public final void startNavigatingSec() {
        this.mGnssVendorConfig.getClass();
        if (GnssVendorConfig.isIzatServiceEnabled() || BatteryService$$ExternalSyntheticOutline0.m45m("vendor/etc/gnss/mnl.prop") || GnssVendorConfig.isBroadcomGnss()) {
            return;
        }
        GnssVendorConfig.isUnisocGnss();
    }

    @Override // com.android.server.location.gnss.GnssLocationProvider
    public final void stopNavigatingSec() {
        if (!this.mCarrierConfig.isKoreaMarket()) {
            if (this.mCarrierConfig.mCarrier != CarrierConfig.Carrier.JPN_DCM || this.mFocusToneGenerator == null) {
                return;
            }
            Log.d("GnssLocationProvider_ex", "release ToneGenerator");
            this.mFocusToneGenerator.release();
            this.mFocusToneGenerator = null;
            return;
        }
        RCPManagerService$$ExternalSyntheticOutline0.m("GnssLocationProvider_ex", new StringBuilder("getSKAFEnable : "), this.mIsSKApplicationFramework);
        boolean z = this.mIsSKApplicationFramework;
        GnssVendorConfig gnssVendorConfig = this.mGnssVendorConfig;
        if (z) {
            updateSKApplicationFrameworkEnabled(false);
            Log.d("GnssLocationProvider_ex", "stopNavigating : isSKAF changed");
            SystemProperties.set("sys.sktgps", "0");
            gnssVendorConfig.getClass();
            if (GnssVendorConfig.isIzatServiceEnabled()) {
                updateKoreanOperatorsSuplSetting();
            }
        }
        RCPManagerService$$ExternalSyntheticOutline0.m("GnssLocationProvider_ex", new StringBuilder("mIsKtGps : "), this.mIsKtGps);
        if (this.mIsKtGps) {
            updateKTSuplServerEnabled(false);
            Log.d("GnssLocationProvider_ex", "mIsKtGps is changed");
            gnssVendorConfig.getClass();
            if (GnssVendorConfig.isIzatServiceEnabled()) {
                updateKoreanOperatorsSuplSetting();
            }
        }
    }

    public final void updateKTSuplServerEnabled(boolean z) {
        this.mIsKtGps = z;
        RCPManagerService$$ExternalSyntheticOutline0.m("GnssLocationProvider_ex", new StringBuilder("mIsKtGps : "), this.mIsKtGps);
    }

    public final void updateKoreanOperatorsSuplSetting() {
        Log.d("GnssLocationProvider_ex", "setKoreanOperatorsSuplSetting()");
        RCPManagerService$$ExternalSyntheticOutline0.m("GnssLocationProvider_ex", new StringBuilder("getSKAFEnable : "), this.mIsSKApplicationFramework);
        if (this.mIsSKApplicationFramework) {
            Log.d("GnssLocationProvider_ex", " SKT GPS mode : SUPL 2.0.1 AGNSS");
            setSktSuplVer();
            SystemProperties.set("sys.sktgps", "1");
            return;
        }
        RCPManagerService$$ExternalSyntheticOutline0.m("GnssLocationProvider_ex", new StringBuilder("mIsKtGps : "), this.mIsKtGps);
        if (this.mIsKtGps) {
            Log.d("GnssLocationProvider_ex", " KT GPS mode : SUPL2.0 AGNSS");
            updateSuplServerConfiguration(3, this.mKTSuplServerPort, 0, 0, 15, this.mKTSuplServerHost);
            return;
        }
        Log.d("GnssLocationProvider_ex", "setKoreanOperatorsSuplSetting() : Google SUPL 2.0 AGNSS");
        this.mGnssVendorConfig.getClass();
        if (!GnssVendorConfig.isIzatServiceEnabled()) {
            GnssNative gnssNative = this.mGnssNative;
            gnssNative.gnssConfigurationUpdateSec("ENABLE_SUPL_AGNSS_BEIDOU=FALSE");
            gnssNative.gnssConfigurationUpdateSec("ENABLE_SUPL_AGNSS_GALILEO=FALSE");
        }
        updateSuplServerConfiguration(3, 7275, 1, 0, 15, "supl.google.com");
    }

    public final void updatePsdsEnabled(int i) {
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i, "configurationUpdate_xtraEnable, XTRA_ENABLE=", "GnssLocationProvider_ex");
        this.mGnssVendorConfig.getClass();
        if (GnssVendorConfig.isIzatServiceEnabled()) {
            postWithWakeLockHeld(new GnssLocationProviderSec$$ExternalSyntheticLambda1(this, i, 0));
        } else {
            this.mGnssNative.gnssConfigurationUpdateSec(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "XTRA_ENABLE="));
        }
    }

    public final void updateSKApplicationFrameworkEnabled(boolean z) {
        this.mIsSKApplicationFramework = z;
        RCPManagerService$$ExternalSyntheticOutline0.m("GnssLocationProvider_ex", new StringBuilder("setSKAFEnable : "), this.mIsSKApplicationFramework);
    }

    public final void updateSuplServerConfiguration(int i, int i2, int i3, int i4, int i5, String str) {
        StringBuilder m = DirEncryptService$$ExternalSyntheticOutline0.m(i, "AGPS_TYPE=3\nSUPL_VERSION=", "\nSUPL_HOST=", str, "\nSUPL_PORT=");
        ServiceKeeper$$ExternalSyntheticOutline0.m(i2, i3, "\nSUPL_SSL=", "\nSSL_VER=", m);
        m.append(i4);
        m.append("\nSSL_TYPE=");
        m.append(i5);
        String sb = m.toString();
        StringBuilder m2 = DirEncryptService$$ExternalSyntheticOutline0.m(i, "Update SUPL Configuration[", "|", str, "|");
        ServiceKeeper$$ExternalSyntheticOutline0.m(i2, i3, "|", "|", m2);
        Log.d("GnssLocationProvider_ex", ActivityManagerService$$ExternalSyntheticOutline0.m(i4, i5, "|", "]", m2));
        this.mGnssVendorConfig.getClass();
        if (GnssVendorConfig.isIzatServiceEnabled()) {
            postWithWakeLockHeld(new GnssLocationProviderSec$$ExternalSyntheticLambda0(0, this, sb));
        } else {
            this.mGnssNative.gnssConfigurationUpdateSec(sb);
        }
        NSLocationProviderHelper nSLocationProviderHelper = this.mNSLocationProviderHelper;
        if (str == null) {
            nSLocationProviderHelper.getClass();
            return;
        }
        nSLocationProviderHelper.mSuplAddress = str;
        nSLocationProviderHelper.mNSConnectionHelper.onStateUpdated(LocationConstants.STATE_TYPE.SUPL_ADDRESS, AccountManagerService$$ExternalSyntheticOutline0.m142m("supl_hostname", str));
    }

    @Override // com.android.server.location.gnss.GnssLocationProvider
    public final void updateSuplServerForNewSISession() {
        SuplInitHandler suplInitHandler = this.mSuplInitHandler;
        if (suplInitHandler.mNiSessionStarted) {
            Log.d("GnssLocationProvider_ex", "NISession flag set to false");
            suplInitHandler.mNiSessionStarted = false;
        }
        int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
        if (SubscriptionManager.isValidSubscriptionId(defaultDataSubscriptionId)) {
            Log.d("GnssLocationProvider_ex", "Set SubIdForSuplNi to DD subID");
            SuplInitHandler.mSubIdForSuplNi = defaultDataSubscriptionId;
        }
        this.mTelephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
        this.mGnssVendorConfig.getClass();
        if (GnssVendorConfig.isIzatServiceEnabled() || !this.mCarrierConfig.isKoreaMarket()) {
            return;
        }
        updateKoreanOperatorsSuplSetting();
    }
}
