package com.android.server.location.gnss;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.hardware.input.InputManager;
import android.location.GnssStatus;
import android.location.Location;
import android.location.LocationResult;
import android.location.provider.ProviderRequest;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileObserver;
import android.os.IDeviceIdleController;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityNr;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoNr;
import android.telephony.CellInfoWcdma;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.display.DisplayPowerController2;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.location.gnss.hal.GnssNative;
import com.android.server.location.gnss.sec.CarrierConfig;
import com.android.server.location.gnss.sec.GnssConstants;
import com.android.server.location.gnss.sec.GnssHalStatus;
import com.android.server.location.gnss.sec.LppeFusedLocationHelper;
import com.android.server.location.gnss.sec.SuplInitHandler;
import com.android.server.location.injector.Injector;
import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.feature.SemFloatingFeature;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

/* loaded from: classes2.dex */
public class GnssLocationProviderSec extends GnssLocationProvider {
  public static final Uri PREFERAPN_NO_UPDATE_URI_USING_SUBID =
      Uri.parse("content://telephony/carriers/preferapn_no_update/subId/");
  public static boolean isWlanApConnected = false;
  public boolean isIssueTrackerIntentReceived;
  public boolean isSehRefLocation;
  public int ktPositionMode;
  public final BroadcastReceiver mBroadcastReceiverSec;
  public CarrierConfig mCarrierConfig;
  public TelephonyManager.CellInfoCallback mCellInfoCb;
  public ConnectivityManager mConnectivityManager;
  public final String[] mConstellationString;
  public CtsRestrictModeFileObserver mCtsRestrictModeFileObserver;
  public ToneGenerator mFocusToneGenerator;
  public boolean mIsKtGps;
  public boolean mIsSKApplicationFramework;
  public boolean mIsSetAutoSuplServer;
  public String mKTSuplServerHost;
  public int mKTSuplServerPort;
  public int mLidState;
  public LppeFusedLocationHelper mLppeFusedLocationHelper;
  public Properties mPropertiesNsFlp;
  public Properties mPropertiesSecgps;
  public int mServerTypeI;
  public HashMap mSimInformationHashMap;
  public SuplInitHandler mSuplInitHandler;
  public TelephonyManager mTelephonyManager;
  public int prevCpAgpsMask;
  public int prevLppMask;
  public int prevNrLppMask;

  public final void handleSimStateChanged(Intent intent) {
    String stringExtra = intent.getStringExtra("ss");
    Log.d("GnssLocationProvider_ex", "SIM_STATE_CHANGED received : " + stringExtra);
    setSimOperatorToCarrierConfig();
    if ("LOADED".equals(stringExtra)) {
      handleSimStateIccLoaded();
    } else {
      handleSimStateIccNotLoaded();
    }
  }

  public final void handleSimStateIccLoaded() {
    if (this.mGnssVendorConfig.isIzatServiceEnabled()) {
      setSuplServerSec();
      sendInitialLidState();
    } else {
      setSuplServerSec();
      sendExtraConfigurationString();
    }
  }

  public final void handleSimStateIccNotLoaded() {
    if (this.mGnssVendorConfig.isIzatServiceEnabled() || !this.mCarrierConfig.isUSAMarket()) {
      return;
    }
    sendExtraConfigurationString();
    setLppBitMask(this.mSimSlotId);
  }

  public final void setLppBitMask(int i) {
    int i2;
    int lppBitmask = getLppBitmask(i);
    SimInformationForDsds simInformationWithSlotId = getSimInformationWithSlotId(i);
    if (simInformationWithSlotId != null) {
      String simOperatorFromSimInfo = simInformationWithSlotId.getSimOperatorFromSimInfo();
      if ("".equals(simOperatorFromSimInfo) || simOperatorFromSimInfo == null) {
        Log.d("GnssLocationProvider_ex", "Invalid simOperator, set as default sim operator");
        this.mCarrierConfig.setSimOperator(this.mTelephonyManager.getSimOperator());
      } else {
        Log.d("GnssLocationProvider_ex", "setLppBitmask. simOperator=" + simOperatorFromSimInfo);
        this.mCarrierConfig.setSimOperator(simOperatorFromSimInfo);
      }
    }
    if (this.mCarrierConfig.isUSAMarket()) {
      lppBitmask = getLppBitmaskForUsaMarket();
      i2 = getNrLppBitmask(i);
    } else {
      if (this.mCarrierConfig.isCanadaMarket()) {
        lppBitmask = getLppBitmaskForCanadaMarket();
      } else if (this.mCarrierConfig.isKoreaMarket()) {
        lppBitmask = getLppBitmaskForKoreaMarket();
      } else if (this.mCarrierConfig.isNoOperator() && lppBitmask == -1) {
        Log.d("GnssLocationProvider_ex", "No Operator LPP Disable");
        lppBitmask = 0;
      }
      i2 = -1;
    }
    Log.d(
        "GnssLocationProvider_ex",
        "prevLppMask=" + this.prevLppMask + ", curLppMask=" + lppBitmask);
    if (lppBitmask > this.prevLppMask && lppBitmask >= 0) {
      this.prevLppMask = lppBitmask;
      setLppSupport(lppBitmask);
    }
    if (i2 <= this.prevNrLppMask || i2 < 0) {
      return;
    }
    this.prevNrLppMask = i2;
    setNrLppSupport(i2);
  }

  public final void sendInitialLidState() {
    InputManager inputManager = (InputManager) this.mContext.getSystemService(InputManager.class);
    if (inputManager == null) {
      return;
    }
    int semGetLidState = inputManager.semGetLidState();
    this.mLidState = semGetLidState;
    if (semGetLidState > -1) {
      Log.d(
          "GnssLocationProvider_ex",
          "Folderable phone - simstate change. lidstate " + this.mLidState);
      sendLidState(this.mLidState);
    }
  }

  public final void handleIssueTrackerOnOff(Intent intent) {
    Bundle extras = intent.getExtras();
    if (!this.isIssueTrackerIntentReceived && extras != null) {
      setIssueTrackerEnabled(extras);
    } else {
      Log.d(
          "GnssLocationProvider_ex",
          "INTENT_ISSUE_TRACKER_ONOFF intent has been ignored because it's processed only once"
              + " after booting.");
    }
  }

  public final void setIssueTrackerEnabled(Bundle bundle) {
    boolean z = bundle.getBoolean("ONOFF", false);
    Log.d("GnssLocationProvider_ex", "INTENT_ISSUE_TRACKER_ONOFF enabled = " + z);
    if (z) {
      SystemProperties.set("dev.gnss.silentloggingIssueTracker", "ON");
    } else {
      SystemProperties.set("dev.gnss.silentloggingIssueTracker", "OFF");
    }
    this.isIssueTrackerIntentReceived = true;
  }

  public final void handleEmergencySmsOverIms() {
    if (this.mGnssVendorConfig.isIzatServiceEnabled()) {
      postWithWakeLockHeld(
          new Runnable() { // from class:
                           // com.android.server.location.gnss.GnssLocationProviderSec$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
              GnssLocationProviderSec.this.lambda$handleEmergencySmsOverIms$0();
            }
          });
    } else {
      this.mGnssNative.gnssConfigurationUpdateSec("EMERGENCY_STATE=SMS");
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$handleEmergencySmsOverIms$0() {
    lambda$updateSuplServerConfiguration$3("EMERGENCY_SMS=1");
  }

  public final void handleNetworkStateChanged(Intent intent) {
    NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra("networkInfo");
    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
      if (isWlanApConnected) {
        return;
      }
      Log.d("GnssLocationProvider_ex", "WIFI NetworkInfo: " + networkInfo);
      isWlanApConnected = true;
      return;
    }
    if (isWlanApConnected) {
      Log.d("GnssLocationProvider_ex", "WIFI is DISCONNECTED.");
      isWlanApConnected = false;
    }
  }

  public final void handleCarrierStateChanged(Intent intent) {
    String stringExtra = intent.getStringExtra("com.samsung.carrier.extra.CARRIER_STATE");
    int intExtra = intent.getIntExtra("com.samsung.carrier.extra.CARRIER_PHONE_ID", -1);
    Log.d("GnssLocationProvider_ex", "slotid=" + intExtra + " carrier state=" + stringExtra);
    if (this.mGnssVendorConfig.isIzatServiceEnabled()) {
      if (("UPDATED".equals(stringExtra) || "LOADED".equals(stringExtra)) && intExtra >= 0) {
        this.mGnssNative.gnssConfigurationUpdateSec("CARRIER_STATE_CHANGED=" + intExtra);
        return;
      }
      return;
    }
    if ("LOADED".equals(stringExtra)) {
      int subscriptionId = SubscriptionManager.getSubscriptionId(intExtra);
      Log.d(
          "GnssLocationProvider_ex",
          "Sim subId from slotId. subId=" + subscriptionId + ", slotId=" + intExtra);
      if (SubscriptionManager.isValidSubscriptionId(subscriptionId)) {
        Log.d("GnssLocationProvider_ex", "CarrierFeature LOADED. it's sub Id=" + subscriptionId);
        SimInformationForDsds simInformationForDsds =
            (SimInformationForDsds)
                this.mSimInformationHashMap.get(Integer.valueOf(subscriptionId));
        if (simInformationForDsds != null) {
          simInformationForDsds.setSimSlotId(intExtra);
          Log.d(
              "GnssLocationProvider_ex",
              "Set Sim slotID to SimInformationForDSDS, slotId="
                  + intExtra
                  + "SimInfo="
                  + simInformationForDsds.toString());
        } else {
          Log.d("GnssLocationProvider_ex", "SimInformation was null");
        }
      }
      setCpAgpsProfile(intExtra);
      setLppBitMask(intExtra);
    }
  }

  public GnssLocationProviderSec(Context context, GnssNative gnssNative, GnssMetrics gnssMetrics) {
    super(context, gnssNative, gnssMetrics);
    this.mIsSKApplicationFramework = false;
    this.mIsKtGps = false;
    this.ktPositionMode = 1;
    this.mIsSetAutoSuplServer = false;
    this.mServerTypeI = -1;
    this.prevCpAgpsMask = -1;
    this.prevLppMask = -1;
    this.prevNrLppMask = -1;
    this.mConstellationString =
        new String[] {"Unknown", "GPS", "SBAS", "GLONASS", "QZSS", "BEIDOU", "GALILEO", "NAVIC"};
    this.mFocusToneGenerator = null;
    this.isSehRefLocation = true;
    this.mCtsRestrictModeFileObserver = null;
    this.mBroadcastReceiverSec =
        new BroadcastReceiver() { // from class:
                                  // com.android.server.location.gnss.GnssLocationProviderSec.1
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            String action = intent.getAction();
            Log.d("GnssLocationProvider_ex", "receive broadcast intent, action: " + action);
            if (action == null) {}
            switch (action) {
              case "com.samsung.carrier.action.CARRIER_CHANGED":
                GnssLocationProviderSec.this.handleCarrierStateChanged(intent);
                break;
              case "android.provider.Telephony.WAP_PUSH_RECEIVED":
                GnssLocationProviderSec.this.mSuplInitHandler.handleWapPushReceived(intent);
                break;
              case "android.net.wifi.STATE_CHANGE":
                GnssLocationProviderSec.this.handleNetworkStateChanged(intent);
                break;
              case "android.intent.action.SIM_STATE_CHANGED":
                GnssLocationProviderSec.this.handleSimStateChanged(intent);
                break;
              case "com.samsung.intent.action.EMERGENCY_SMS_OVER_IMS":
                GnssLocationProviderSec.this.handleEmergencySmsOverIms();
                break;
              case "com.samsung.ims.action.IMS_REGISTRATION":
                if (GnssLocationProviderSec.this.mGnssVendorConfig.isIzatServiceEnabled()
                    && !GnssLocationProviderSec.this.mCarrierConfig.isKoreaMarket()) {
                  GnssLocationProviderSec.this.mSuplInitHandler.updateImsState(intent);
                  break;
                }
                break;
              case "com.sec.android.ISSUE_TRACKER_ONOFF":
                GnssLocationProviderSec.this.handleIssueTrackerOnOff(intent);
                break;
              case "android.intent.action.DATA_SMS_RECEIVED":
                GnssLocationProviderSec.this.mSuplInitHandler.handleDataSmsReceived(intent);
                break;
            }
          }
        };
    this.mSimInformationHashMap = new HashMap();
    this.mConnectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
    this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
  }

  public GnssLocationProviderSec(
      Context context, Injector injector, GnssNative gnssNative, GnssMetrics gnssMetrics) {
    super(context, injector, gnssNative, gnssMetrics);
    this.mIsSKApplicationFramework = false;
    this.mIsKtGps = false;
    this.ktPositionMode = 1;
    this.mIsSetAutoSuplServer = false;
    this.mServerTypeI = -1;
    this.prevCpAgpsMask = -1;
    this.prevLppMask = -1;
    this.prevNrLppMask = -1;
    this.mConstellationString =
        new String[] {"Unknown", "GPS", "SBAS", "GLONASS", "QZSS", "BEIDOU", "GALILEO", "NAVIC"};
    this.mFocusToneGenerator = null;
    this.isSehRefLocation = true;
    this.mCtsRestrictModeFileObserver = null;
    this.mBroadcastReceiverSec =
        new BroadcastReceiver() { // from class:
                                  // com.android.server.location.gnss.GnssLocationProviderSec.1
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            String action = intent.getAction();
            Log.d("GnssLocationProvider_ex", "receive broadcast intent, action: " + action);
            if (action == null) {}
            switch (action) {
              case "com.samsung.carrier.action.CARRIER_CHANGED":
                GnssLocationProviderSec.this.handleCarrierStateChanged(intent);
                break;
              case "android.provider.Telephony.WAP_PUSH_RECEIVED":
                GnssLocationProviderSec.this.mSuplInitHandler.handleWapPushReceived(intent);
                break;
              case "android.net.wifi.STATE_CHANGE":
                GnssLocationProviderSec.this.handleNetworkStateChanged(intent);
                break;
              case "android.intent.action.SIM_STATE_CHANGED":
                GnssLocationProviderSec.this.handleSimStateChanged(intent);
                break;
              case "com.samsung.intent.action.EMERGENCY_SMS_OVER_IMS":
                GnssLocationProviderSec.this.handleEmergencySmsOverIms();
                break;
              case "com.samsung.ims.action.IMS_REGISTRATION":
                if (GnssLocationProviderSec.this.mGnssVendorConfig.isIzatServiceEnabled()
                    && !GnssLocationProviderSec.this.mCarrierConfig.isKoreaMarket()) {
                  GnssLocationProviderSec.this.mSuplInitHandler.updateImsState(intent);
                  break;
                }
                break;
              case "com.sec.android.ISSUE_TRACKER_ONOFF":
                GnssLocationProviderSec.this.handleIssueTrackerOnOff(intent);
                break;
              case "android.intent.action.DATA_SMS_RECEIVED":
                GnssLocationProviderSec.this.mSuplInitHandler.handleDataSmsReceived(intent);
                break;
            }
          }
        };
    this.mSimInformationHashMap = new HashMap();
    Log.d("GnssLocationProvider_ex", "Constructor");
    initGnssLocationProviderSec();
    this.mSuplInitHandler = new SuplInitHandler(context, this, gnssNative);
    initBroadcastReceiver();
  }

  public void initGnssLocationProviderSec() {
    Log.d("GnssLocationProvider_ex", "init_GnssLocationProviderSec()");
    this.mPropertiesSecgps = new Properties();
    this.mPropertiesNsFlp = new Properties();
    this.mConnectivityManager =
        (ConnectivityManager) this.mContext.getSystemService("connectivity");
    this.mTelephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
    setSalesCodeToCarrierConfig();
    if (!this.mGnssVendorConfig.isIzatServiceEnabled()) {
      this.mLppeFusedLocationHelper =
          new LppeFusedLocationHelper(this.mContext, this.mGnssNative, this.mHandler.getLooper());
    }
    checkAutoConfigEnabled();
    if (this.mGnssVendorConfig.isIzatServiceEnabled()) {
      CtsRestrictModeFileObserver ctsRestrictModeFileObserver =
          new CtsRestrictModeFileObserver(
              new File("/sys/class/sensors/ssc_core/operation_mode"), 2);
      this.mCtsRestrictModeFileObserver = ctsRestrictModeFileObserver;
      ctsRestrictModeFileObserver.startWatching();
      registerLidStateChangedListener();
    }
    this.mCellInfoCb =
        new TelephonyManager
            .CellInfoCallback() { // from class:
                                  // com.android.server.location.gnss.GnssLocationProviderSec.2
          @Override // android.telephony.TelephonyManager.CellInfoCallback
          public void onCellInfo(List list) {
            if (list == null) {
              return;
            }
            Log.d("GnssLocationProvider_ex", "CellInfo updated. cellInfo size = " + list.size());
            int typeFromNetworkType = GnssLocationProviderSec.this.getTypeFromNetworkType();
            if (typeFromNetworkType == 0) {
              return;
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
              CellInfo cellInfo = (CellInfo) it.next();
              if (cellInfo.isRegistered()) {
                Log.d("GnssLocationProvider_ex", "registered cell found");
                if (cellInfo.getCellIdentity().getMccString() == null
                    || cellInfo.getCellIdentity().getMncString() == null) {
                  return;
                }
                GnssLocationProviderSec.this.setReferenceLocation(typeFromNetworkType, cellInfo);
                return;
              }
            }
          }
        };
  }

  public final void setSalesCodeToCarrierConfig() {
    String strCarrierFeature =
        getStrCarrierFeature(this.mSimSlotId, "CarrierFeature_GPS_ConfigAgpsSetting");
    if (strCarrierFeature == null || strCarrierFeature.length() == 0) {
      strCarrierFeature = SystemProperties.get("ro.csc.sales_code");
    }
    CarrierConfig carrierConfig = CarrierConfig.getInstance();
    this.mCarrierConfig = carrierConfig;
    carrierConfig.setSalesCode(strCarrierFeature);
    this.mCarrierConfig.setDeviceMode(SystemProperties.get("ro.build.characteristics"));
  }

  public final void checkAutoConfigEnabled() {
    String property =
        getPropertiesFromFile("/data/system/gps/secgps.conf").getProperty("SERVER_TYPE");
    if (property != null) {
      this.mServerTypeI = Integer.parseInt(property);
      Log.d("GnssLocationProvider_ex", "Auto Config in AngryGPS : " + this.mServerTypeI);
      return;
    }
    Log.d("GnssLocationProvider_ex", "No params for SERVER_TYPE in AngryGPS.");
  }

  public final Properties getPropertiesFromFile(String str) {
    Properties properties = new Properties();
    try {
      FileInputStream fileInputStream = new FileInputStream(new File(str));
      try {
        properties.load(fileInputStream);
        fileInputStream.close();
      } catch (Throwable th) {
        try {
          fileInputStream.close();
        } catch (Throwable th2) {
          th.addSuppressed(th2);
        }
        throw th;
      }
    } catch (FileNotFoundException unused) {
      Log.w("GnssLocationProvider_ex", "Could not open configuration file " + str);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException unused2) {
      Log.w(
          "GnssLocationProvider_ex",
          "Could not load configuration file " + str + "due to IllegalArgumentException");
    } catch (SecurityException unused3) {
      Log.w("GnssLocationProvider_ex", "Could not access configuration file " + str);
    }
    return properties;
  }

  public final void registerLidStateChangedListener() {
    InputManager inputManager = (InputManager) this.mContext.getSystemService(InputManager.class);
    if (inputManager != null) {
      int semGetLidState = inputManager.semGetLidState();
      this.mLidState = semGetLidState;
      if (semGetLidState > -1) {
        Log.d("GnssLocationProvider_ex", "Foldable phone");
        inputManager.semRegisterOnLidStateChangedListener(
            new InputLidStateChangedListener(), this.mHandler);
      }
    }
  }

  public final int getTypeFromNetworkType() {
    int dataNetworkType = this.mTelephonyManager.getDataNetworkType();
    if (dataNetworkType == 3
        || dataNetworkType == 8
        || dataNetworkType == 9
        || dataNetworkType == 10
        || dataNetworkType == 15) {
      return 2;
    }
    if (dataNetworkType == 13) {
      return 4;
    }
    if (dataNetworkType == 20) {
      return 8;
    }
    if (dataNetworkType == 18) {
      return getTypeForIWLanNetwork();
    }
    Log.d("GnssLocationProvider_ex", "networkType is not IWLAN and type = 1");
    return 1;
  }

  public final int getTypeForIWLanNetwork() {
    int voiceNetworkType = this.mTelephonyManager.getVoiceNetworkType();
    if (voiceNetworkType == 3
        || voiceNetworkType == 8
        || voiceNetworkType == 9
        || voiceNetworkType == 10
        || voiceNetworkType == 15) {
      Log.d(
          "GnssLocationProvider_ex", "networkType = IWLAN, VoiceNetworkType = " + voiceNetworkType);
      return 2;
    }
    if (voiceNetworkType == 13) {
      Log.d("GnssLocationProvider_ex", "networkType = IWLAN, VoiceNetworkType = LTE");
      return 4;
    }
    if (this.mCarrierConfig.isUsaVerizon()) {
      return 0;
    }
    Log.d(
        "GnssLocationProvider_ex",
        "networkType = IWLAN, VoiceNetworkType is not LTE & HSDPA, type =  " + voiceNetworkType);
    return 1;
  }

  public final void setReferenceLocation(int i, CellInfo cellInfo) {
    int parseInt = Integer.parseInt(cellInfo.getCellIdentity().getMccString());
    int parseInt2 = Integer.parseInt(cellInfo.getCellIdentity().getMncString());
    if (cellInfo instanceof CellInfoGsm) {
      setGsmReferenceLocation(i, (CellInfoGsm) cellInfo, parseInt, parseInt2);
      return;
    }
    if (cellInfo instanceof CellInfoWcdma) {
      setWcdmaReferenceLocation(i, (CellInfoWcdma) cellInfo, parseInt, parseInt2);
      return;
    }
    if (cellInfo instanceof CellInfoLte) {
      setLteReferenceLocation(i, (CellInfoLte) cellInfo, parseInt, parseInt2);
    } else if (cellInfo instanceof CellInfoNr) {
      setNrReferenceLocation(i, cellInfo, parseInt, parseInt2);
    } else if (cellInfo instanceof CellInfoCdma) {
      setCdmaReferenceLocation(i, (CellInfoCdma) cellInfo, parseInt, parseInt2);
    }
  }

  public final void setGsmReferenceLocation(int i, CellInfoGsm cellInfoGsm, int i2, int i3) {
    CellIdentityGsm cellIdentity = cellInfoGsm.getCellIdentity();
    int lac = cellIdentity.getLac();
    int cid = cellIdentity.getCid();
    setAgpsReferenceLocationCellId(i, i2, i3, lac, Integer.MAX_VALUE, 0, cid, cid);
  }

  public final void setWcdmaReferenceLocation(int i, CellInfoWcdma cellInfoWcdma, int i2, int i3) {
    CellIdentityWcdma cellIdentity = cellInfoWcdma.getCellIdentity();
    int lac = cellIdentity.getLac();
    int cid = cellIdentity.getCid();
    setAgpsReferenceLocationCellId(i, i2, i3, lac, cellIdentity.getPsc(), 0, cid, cid);
  }

  public final void setLteReferenceLocation(int i, CellInfoLte cellInfoLte, int i2, int i3) {
    int i4;
    CellIdentityLte cellIdentity = cellInfoLte.getCellIdentity();
    int tac = cellIdentity.getTac();
    int ci = cellIdentity.getCi();
    long j = ci;
    int pci = cellIdentity.getPci();
    if (i == 4
        || !this.mCarrierConfig.isUsaVerizon()
        || this.mGnssVendorConfig.isIzatServiceEnabled()) {
      i4 = i;
    } else {
      Log.d("GnssLocationProvider_ex", "correct network type to 4 from " + i);
      i4 = 4;
    }
    setAgpsReferenceLocationCellId(i4, i2, i3, tac, pci, 0, ci, j);
  }

  public final void setNrReferenceLocation(int i, CellInfo cellInfo, int i2, int i3) {
    int i4;
    CellIdentityNr cellIdentityNr = (CellIdentityNr) cellInfo.getCellIdentity();
    int tac = cellIdentityNr.getTac();
    long nci = cellIdentityNr.getNci();
    int pci = cellIdentityNr.getPci();
    int nrarfcn = cellIdentityNr.getNrarfcn();
    if (i == 8
        || !this.mCarrierConfig.isUsaVerizon()
        || this.mGnssVendorConfig.isIzatServiceEnabled()) {
      i4 = i;
    } else {
      Log.d("GnssLocationProvider_ex", "correct network type to 8 from " + i);
      i4 = 8;
    }
    setAgpsReferenceLocationCellId(i4, i2, i3, tac, pci, nrarfcn, 0, nci);
  }

  public final void setCdmaReferenceLocation(int i, CellInfoCdma cellInfoCdma, int i2, int i3) {
    cellInfoCdma.getCellIdentity();
    setAgpsReferenceLocationCellId(i, i2, i3, 0, 0, 0, 0, 0);
  }

  public final void setAgpsReferenceLocationCellId(
      int i, int i2, int i3, int i4, int i5, int i6, int i7, long j) {
    if (this.isSehRefLocation) {
      Log.d("GnssLocationProvider_ex", "setAgpsReferenceLocationCellId");
      this.mGnssNative.setAgpsReferenceLocationCellId(i, i2, i3, i4, j, i5, i6);
    } else {
      Log.d("GnssLocationProvider_ex", "setRefLocation through IAGnss");
      this.mGnssNative.setAgpsReferenceLocationCellId(i, i2, i3, i4, i7, 0, i5, i6);
    }
  }

  public final void initBroadcastReceiver() {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.DATA_SMS_RECEIVED");
    intentFilter.addDataScheme("sms");
    intentFilter.addDataAuthority("localhost", "7275");
    this.mContext.registerReceiver(this.mBroadcastReceiverSec, intentFilter);
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
    intentFilter3.addAction("android.intent.action.SIM_STATE_CHANGED");
    intentFilter3.addAction("com.sec.android.ISSUE_TRACKER_ONOFF");
    intentFilter3.addAction("com.samsung.carrier.action.CARRIER_CHANGED");
    this.mContext.registerReceiver(this.mBroadcastReceiverSec, intentFilter3);
    if (this.mGnssVendorConfig.isIzatServiceEnabled()) {
      IntentFilter intentFilter4 = new IntentFilter();
      intentFilter4.setPriority(1000);
      intentFilter4.addAction("com.samsung.ims.action.IMS_REGISTRATION");
      this.mContext.registerReceiver(this.mBroadcastReceiverSec, intentFilter4);
    }
    IntentFilter intentFilter5 = new IntentFilter();
    intentFilter5.setPriority(1000);
    intentFilter5.addAction("com.samsung.intent.action.EMERGENCY_SMS_OVER_IMS");
    this.mContext.registerReceiver(this.mBroadcastReceiverSec, intentFilter5);
  }

  @Override // com.android.server.location.gnss.GnssLocationProvider
  public void handleEnableSec() {
    sendExtraConfigurationString();
  }

  public String getAutoSuplUrl() {
    int i;
    String simOperator = this.mTelephonyManager.getSimOperator();
    if (simOperator == null) {
      return null;
    }
    int i2 = 0;
    try {
      i = Integer.parseInt(simOperator.substring(0, 3));
    } catch (IndexOutOfBoundsException | NumberFormatException e) {
      e.printStackTrace();
      i = 0;
    }
    try {
      i2 = Integer.parseInt(simOperator.substring(3));
    } catch (IndexOutOfBoundsException | NumberFormatException e2) {
      e2.printStackTrace();
    }
    return getUrlStringBuilder(i, i2).toString();
  }

  public final StringBuilder getUrlStringBuilder(int i, int i2) {
    StringBuilder sb = new StringBuilder();
    sb.append("h-slp.mnc");
    sb.append(String.format(Locale.getDefault(), "%03d", Integer.valueOf(i2)));
    sb.append(".mcc");
    sb.append(String.format(Locale.getDefault(), "%03d", Integer.valueOf(i)));
    sb.append(".pub.3gppnetwork.org");
    Log.d("GnssLocationProvider_ex", "makeAutoSuplUrl :" + ((Object) sb));
    return sb;
  }

  public final void updateSKApplicationFrameworkEnabled(boolean z) {
    this.mIsSKApplicationFramework = z;
    Log.d("GnssLocationProvider_ex", "setSKAFEnable : " + this.mIsSKApplicationFramework);
  }

  public final boolean isSKApplicationFrameworkEnabled() {
    Log.d("GnssLocationProvider_ex", "getSKAFEnable : " + this.mIsSKApplicationFramework);
    return this.mIsSKApplicationFramework;
  }

  public void setSktSuplVer() {
    Log.d("GnssLocationProvider_ex", "setSktSuplVer : " + SystemProperties.get("ro.product.name"));
    Log.d("GnssLocationProvider_ex", "AGPS_SUPL_VER_2_0_1_AGNSS,  server = wpde.nate.com");
    updateSuplServerConfiguration(4, "wpde.nate.com", 7275, 0, 0, 15);
  }

  public final void updateKTSuplServerEnabled(boolean z) {
    this.mIsKtGps = z;
    Log.d("GnssLocationProvider_ex", "mIsKtGps : " + this.mIsKtGps);
  }

  public final boolean isKTSuplServerEnabled() {
    Log.d("GnssLocationProvider_ex", "mIsKtGps : " + this.mIsKtGps);
    return this.mIsKtGps;
  }

  public final void setDcmSuplIot(boolean z) {
    if (z) {
      Log.d("GnssLocationProvider_ex", "Docomo SUPL IOT test = true server = dcm-supl.com");
      updateSuplServerConfiguration(3, "dcm-supl.com", 7275, 1, 1, 15);
    } else {
      Log.d("GnssLocationProvider_ex", "Docomo SUPL IOT test = false");
    }
  }

  public final void generateBeep() {
    Log.d("GnssLocationProvider_ex", "generateBeep Method call");
    int streamVolume = ((AudioManager) this.mContext.getSystemService("audio")).getStreamVolume(1);
    Log.e("GnssLocationProvider_ex", "generateBeep: Volume Level:" + streamVolume);
    if (streamVolume >= 0 && streamVolume <= 7) {
      r3 = streamVolume != 7 ? streamVolume * 14 : 100;
      Log.e("GnssLocationProvider_ex", "generateBeep: valid Volume:" + r3);
    } else {
      Log.e(
          "GnssLocationProvider_ex", "Error getting current volume: Setting volume as max volume");
    }
    ToneGenerator toneGenerator = this.mFocusToneGenerator;
    if (toneGenerator != null) {
      toneGenerator.release();
      this.mFocusToneGenerator = null;
    }
    ToneGenerator toneGenerator2 = new ToneGenerator(1, r3);
    this.mFocusToneGenerator = toneGenerator2;
    toneGenerator2.startTone(28, 500);
  }

  public final void updateKoreanOperatorsSuplSetting() {
    Log.d("GnssLocationProvider_ex", "setKoreanOperatorsSuplSetting()");
    if (isSKApplicationFrameworkEnabled()) {
      Log.d("GnssLocationProvider_ex", " SKT GPS mode : SUPL 2.0.1 AGNSS");
      setSktSuplVer();
      SystemProperties.set("sys.sktgps", "1");
    } else {
      if (isKTSuplServerEnabled()) {
        Log.d("GnssLocationProvider_ex", " KT GPS mode : SUPL2.0 AGNSS");
        updateSuplServerConfiguration(3, this.mKTSuplServerHost, this.mKTSuplServerPort, 0, 0, 15);
        return;
      }
      Log.d("GnssLocationProvider_ex", "setKoreanOperatorsSuplSetting() : Google SUPL 2.0 AGNSS");
      if (!this.mGnssVendorConfig.isIzatServiceEnabled()) {
        this.mGnssNative.gnssConfigurationUpdateSec("ENABLE_SUPL_AGNSS_BEIDOU=FALSE");
        this.mGnssNative.gnssConfigurationUpdateSec("ENABLE_SUPL_AGNSS_GALILEO=FALSE");
      }
      updateSuplServerConfiguration(3, "supl.google.com", 7275, 1, 0, 15);
    }
  }

  public static boolean shouldSupportNfwLocPolicy() {
    return SystemProperties.getInt("ro.product.first_api_level", 0) > 28;
  }

  public int getPositionModeForCTC() {
    Log.d("GnssLocationProvider_ex", "extCTCSelectPositionMode()");
    Log.d(
        "GnssLocationProvider_ex",
        "locationModeState :"
            + Settings.Secure.getInt(this.mContext.getContentResolver(), "location_mode", 0));
    NetworkInfo activeNetworkInfo = this.mConnectivityManager.getActiveNetworkInfo();
    NetworkInfo networkInfo = this.mConnectivityManager.getNetworkInfo(1);
    String[] split =
        SystemProperties.get("gsm.network.type", "").split(XmlUtils.STRING_ARRAY_SEPARATOR, 0);
    Log.d("GnssLocationProvider_ex", "Network Type : " + split[0]);
    String gpsCurrentApn = getGpsCurrentApn(this.mContext);
    boolean z =
        "LTE".equals(split[0])
            || "CTWAP".equals(gpsCurrentApn)
            || "CTLTE".equals(gpsCurrentApn)
            || "CTNET".equals(gpsCurrentApn);
    if (activeNetworkInfo != null && activeNetworkInfo.isRoaming()) {
      Log.d("GnssLocationProvider_ex", "Roaming, Start Standalone GPS");
      return 0;
    }
    if (networkInfo != null && networkInfo.getType() == 1 && networkInfo.isConnected()) {
      Log.d("GnssLocationProvider_ex", "This is WIFI, Start Standalone GPS");
      return 0;
    }
    if (z) {
      if (this.mGnssNative.getCapabilities().hasMsb()) {
        Log.d("GnssLocationProvider_ex", "Start Tracking Mode : MS-Based");
        return 1;
      }
      Log.d("GnssLocationProvider_ex", "No MS-A, Ms-B capabilities");
      return 0;
    }
    Log.d("GnssLocationProvider_ex", "It's not CTC AGPS APN : " + gpsCurrentApn);
    return 0;
  }

  public int getPositionModeForChn() {
    Log.d("GnssLocationProvider_ex", "extChnSelectPositionMode() start");
    if (this.mCarrierConfig == null) {
      this.mCarrierConfig = CarrierConfig.getInstance();
    }
    if (this.mCarrierConfig.isChinaMobile()) {
      boolean cmccPsdsEnabled = getCmccPsdsEnabled();
      if (this.mTelephonyManager.getNetworkType() == 13 || cmccPsdsEnabled) {
        Log.d("GnssLocationProvider_ex", "extChnSelectPositionMode() postionMode == STANDALONE");
        updatePsdsEnabled(0);
        return 0;
      }
      Log.d("GnssLocationProvider_ex", "extChnSelectPositionMode() postionMode == MS_BASED");
      updatePsdsEnabled(1);
      return 1;
    }
    if (!this.mCarrierConfig.isChinaOpen()
        && !this.mCarrierConfig.isChinaUnicom()
        && !this.mCarrierConfig.isChinaTdOpen()) {
      return 0;
    }
    Log.d(
        "GnssLocationProvider_ex", "extChnSelectPositionMode, set with STANDALONE for CU and OPEN");
    return 0;
  }

  public boolean getCmccPsdsEnabled() {
    if (!this.mCarrierConfig.isChinaMobile()) {
      return false;
    }
    int i = Settings.System.getInt(this.mContext.getContentResolver(), "agps_function_switch", 1);
    Log.d("GnssLocationProvider_ex", "getCmccPsdsEnabled, isAgpsSwitchMode : " + i);
    if (i != 1) {
      if (i == 2) {
        Log.d("GnssLocationProvider_ex", "getCmccPsdsEnabled, AGPS setting : All Network");
        return false;
      }
      Log.d("GnssLocationProvider_ex", "getCmccPsdsEnabled, AGPS setting : AGPS OFF");
      return true;
    }
    ConnectivityManager connectivityManager = this.mConnectivityManager;
    if (connectivityManager != null) {
      NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
      if (activeNetworkInfo != null && activeNetworkInfo.isRoaming()) {
        Log.d(
            "GnssLocationProvider_ex",
            "getCmccPsdsEnabled : AGPS setting : Home network, info.isRoaming() == true");
        return true;
      }
      Log.d(
          "GnssLocationProvider_ex",
          "getCmccPsdsEnabled : AGPS setting : Home network, info.isRoaming() == false");
      return false;
    }
    Log.d("GnssLocationProvider_ex", "getCmccPsdsEnabled : AGPS setting : Home network, PS error");
    return true;
  }

  public final String getGpsCurrentApn(Context context) {
    int defaultSubscriptionId;
    Log.d("GnssLocationProvider_ex", "getGpsCurrentApn");
    String str = "";
    if (TelephonyManager.getDefault().getPhoneCount() > 1) {
      Log.d("GnssLocationProvider_ex", "This model uses multisim.");
      String str2 = SystemProperties.get("gsm.sim.state", "0,0").split(",")[0];
      Log.d("GnssLocationProvider_ex", "Slot1 Card Status : " + str2);
      if ("ABSENT".equals(str2)) {
        Log.d("GnssLocationProvider_ex", "Slot1 is empty. No need to check apn. return APN null.");
        return "";
      }
    }
    ContentResolver contentResolver = context.getContentResolver();
    int[] subId = SubscriptionManager.getSubId(0);
    if (subId != null && subId.length > 0) {
      defaultSubscriptionId = subId[0];
    } else {
      defaultSubscriptionId = SubscriptionManager.getDefaultSubscriptionId();
      Log.e("GnssLocationProvider_ex", "subID is null or 0 length, so get DefaultSubId!!");
    }
    Log.d("GnssLocationProvider_ex", "getSubId from simSlot1, SubId = " + defaultSubscriptionId);
    Cursor query =
        contentResolver.query(
            Uri.parse(PREFERAPN_NO_UPDATE_URI_USING_SUBID.toString() + defaultSubscriptionId),
            new String[] {"apn"},
            null,
            null,
            null);
    if (query == null) {
      return "";
    }
    Log.d("GnssLocationProvider_ex", "[getCurrentApn] cursor.count() = " + query.getCount());
    try {
      if (query.moveToFirst() && query.getString(0) != null) {
        str = query.getString(0).toUpperCase();
      }
      Log.d("GnssLocationProvider_ex", "[getCurrentApn] get apn = " + str);
      return str;
    } finally {
      query.close();
    }
  }

  @Override // com.android.server.location.gnss.GnssLocationProvider
  public void changeNlpAccuracyInForce(Location location) {
    if (this.mCarrierConfig.isChinaCarrier()) {
      if (this.mGnssVendorConfig.isIzatServiceEnabled()
          && location.hasAccuracy()
          && location.getAccuracy() < 1000.0f) {
        location.setAccuracy(1000.0f);
      }
      Log.d("GnssLocationProvider_ex", "changeNlpAccuracyInForce.");
    }
  }

  public final String getHardwareFactors() {
    Log.d("GnssLocationProvider_ex", "getHardwareFactors");
    File file = new File("/vendor/etc/gnss/hardware_factors.conf");
    if (!checkIfFileExists(file)) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
      try {
        String str = Build.MODEL;
        Log.d("GnssLocationProvider_ex", "model = " + str);
        while (true) {
          String readLine = bufferedReader.readLine();
          if (readLine == null) {
            break;
          }
          sb.append(getConfigValuesFromLine(readLine, str));
        }
        bufferedReader.close();
      } finally {
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    Log.d("GnssLocationProvider_ex", "HW_FEATURES:\n" + ((Object) sb));
    return sb.toString();
  }

  /* JADX WARN: Removed duplicated region for block: B:11:? A[RETURN, SYNTHETIC] */
  /* JADX WARN: Removed duplicated region for block: B:8:0x0049  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final String getConfigValuesFromLine(String str, String str2) {
    String str3;
    if (str.contains("?")) {
      String substring = str2.substring(0, str2.length() - 1);
      if (str.startsWith(substring)) {
        StringBuilder sb = new StringBuilder();
        sb.append(str.replace(substring + "?_", ""));
        sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        str3 = sb.toString();
        if (str.startsWith(str2)) {
          return str3;
        }
        return str.replace(str2 + "_", "") + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE;
      }
    }
    str3 = "";
    if (str.startsWith(str2)) {}
  }

  public final void setCscParameters(Bundle bundle) {
    boolean z;
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
      Log.w(
          "GnssLocationProvider_ex",
          " could not delete cscgps.conf file : /data/system/gps/cscgps.conf");
    }
    if ("TRUE".equals(bundle.getString("is_empty", "TRUE"))) {
      Log.d("GnssLocationProvider_ex", "extras data is empty. do not update SUPL config.");
    } else {
      Properties properties = new Properties();
      String string = bundle.getString("operation_mode", "");
      if ("MSBASED".equals(string)) {
        setPropertiesMsBased(bundle, properties, string);
        z = true;
      } else {
        z = false;
      }
      properties.setProperty("CSC_SUPL_SUPLSERVERFROMCSC", z ? "1" : "0");
      storePropertiesToFile(
          "/data/system/gps",
          "/data/system/gps/cscgps.conf",
          properties,
          "Saved CSC GPS Information");
    }
    setSuplServerSec();
  }

  public final void setPropertiesMsBased(Bundle bundle, Properties properties, String str) {
    String string = bundle.getString("hslp_addr", "supl.google.com");
    int i = bundle.getInt("hslp_port", 7275);
    int i2 = bundle.getInt("ssl", 1);
    int i3 = bundle.getInt("ssl_cert", 15);
    int i4 = bundle.getInt("supl_ver", 2);
    Log.e(
        "GnssLocationProvider_ex",
        "[SUPL CSC] suplAddress "
            + string
            + " suplVersion "
            + i4
            + " suplPort "
            + i
            + " suplSslMode "
            + i2);
    properties.setProperty("CSC_SUPL_OPMODE", str);
    properties.setProperty("CSC_SUPL_HOST", string);
    properties.setProperty("CSC_SUPL_VER", Integer.toString(i4));
    properties.setProperty("CSC_SUPL_PORT", Integer.toString(i));
    properties.setProperty("CSC_SUPL_SSL", Integer.toString(i2));
    properties.setProperty("CSC_SUPL_CERT", Integer.toString(i3));
  }

  public int getLppBitmask(int i) {
    int intCarrierFeature = getIntCarrierFeature(i, "CarrierFeature_GPS_ConfigLppBitmask");
    Log.d(
        "GnssLocationProvider_ex",
        "CarrierFeature value LPPe Capability = "
            + getLppCapabilityString(intCarrierFeature)
            + ", slotID = "
            + i);
    return intCarrierFeature;
  }

  public int getLppBitmaskForUsaMarket() {
    if (this.mCarrierConfig == null) {
      this.mCarrierConfig = CarrierConfig.getInstance();
    }
    if (this.mCarrierConfig.isTabletDevice()) {
      return 0;
    }
    int i = this.mCarrierConfig.isOTDOASupportMarket() ? 7 : 5;
    if (this.mCarrierConfig.isLppeSupportMarket()) {
      i |= 16;
    }
    if (this.mCarrierConfig.isUsaVerizon()) {
      i |= 64;
    }
    Log.d("GnssLocationProvider_ex", "(US market) LPPe Capability = " + getLppCapabilityString(i));
    return i;
  }

  public int getLppBitmaskForCanadaMarket() {
    if (this.mCarrierConfig == null) {
      this.mCarrierConfig = CarrierConfig.getInstance();
    }
    if (this.mCarrierConfig.isTabletDevice()) {
      return 0;
    }
    Log.d(
        "GnssLocationProvider_ex",
        "(Canada Market) LPPe Capability = " + getLppCapabilityString(5));
    return 5;
  }

  public int getLppBitmaskForKoreaMarket() {
    if (this.mCarrierConfig == null) {
      this.mCarrierConfig = CarrierConfig.getInstance();
    }
    if (this.mCarrierConfig.isKoreaSktSim() || this.mCarrierConfig.isKoreaLguSim()) {
      Log.d("GnssLocationProvider_ex", "(KOR SKT,LGU) LPPe Capability = []");
      return 0;
    }
    if (!this.mCarrierConfig.isKoreaKttSim()) {
      return 0;
    }
    Log.d("GnssLocationProvider_ex", "(KOR KTT) LPPe Capability = " + getLppCapabilityString(87));
    return 87;
  }

  public final String getLppCapabilityString(int i) {
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

  public final void setLppSupport(int i) {
    if (this.mGnssVendorConfig.isIzatServiceEnabled()) {
      return;
    }
    Log.d("GnssLocationProvider_ex", "setLppSupport(" + i + ")");
    if (!this.mGpsEnabled && this.mGnssVendorConfig.isBroadcomGnss()) {
      this.mGnssNative.init();
      Log.d("GnssLocationProvider_ex", "native_init");
    }
    updateLppSupportConfiguration(i);
    if (this.mGpsEnabled || !this.mGnssVendorConfig.isBroadcomGnss()) {
      return;
    }
    this.mGnssNative.cleanup();
    Log.d("GnssLocationProvider_ex", "native_cleanup");
    this.mGnssNative.initLocationOff();
  }

  public final int getNrLppBitmask(int i) {
    int intCarrierFeature = getIntCarrierFeature(i, "CarrierFeature_GPS_ConfigNrLppBitmask");
    Log.d("GnssLocationProvider_ex", "NR LPP Profile = " + intCarrierFeature);
    return intCarrierFeature;
  }

  public final void setNrLppSupport(int i) {
    if (this.mGnssVendorConfig.isIzatServiceEnabled()) {
      return;
    }
    Log.d("GnssLocationProvider_ex", "setNrLppSupport(" + i + ")");
    if (!this.mGpsEnabled && this.mGnssVendorConfig.isBroadcomGnss()) {
      this.mGnssNative.init();
      Log.d("GnssLocationProvider_ex", "native_init");
    }
    updateNrLppSupportConfiguration(i);
    if (this.mGpsEnabled || !this.mGnssVendorConfig.isBroadcomGnss()) {
      return;
    }
    this.mGnssNative.cleanup();
    Log.d("GnssLocationProvider_ex", "native_cleanup ");
    this.mGnssNative.initLocationOff();
  }

  @Override // com.android.server.location.gnss.hal.GnssNative.NotificationCallbacks
  public boolean isInEmergencySessionSEC() {
    return this.mNIHandler.getInEmergency(0L);
  }

  public final void setSimOperatorToCarrierConfig() {
    SubscriptionManager from = SubscriptionManager.from(this.mContext);
    this.mSimInformationHashMap.clear();
    List<SubscriptionInfo> activeSubscriptionInfoList = from.getActiveSubscriptionInfoList();
    if (activeSubscriptionInfoList != null) {
      Iterator<SubscriptionInfo> it = activeSubscriptionInfoList.iterator();
      while (it.hasNext()) {
        int subscriptionId = it.next().getSubscriptionId();
        TelephonyManager createForSubscriptionId =
            this.mTelephonyManager.createForSubscriptionId(subscriptionId);
        String simOperator = createForSubscriptionId.getSimOperator(subscriptionId);
        this.mCarrierConfig.setSimOperator(simOperator);
        Log.d(
            "GnssLocationProvider_ex",
            "SubscriptionId is "
                + subscriptionId
                + ", SimOperatorName = "
                + createForSubscriptionId.getSimOperatorName(subscriptionId)
                + ", SimOperator = "
                + simOperator);
        this.mSimInformationHashMap.put(
            Integer.valueOf(subscriptionId),
            new SimInformationForDsds(
                subscriptionId,
                simOperator,
                this.mCarrierConfig.getConfigMap(),
                createForSubscriptionId));
      }
    }
  }

  public class SimInformationForDsds {
    public HashMap mConfigMap;
    public TelephonyManager mPhone;
    public String mSimOperator;
    public int mSlotId = -1;
    public int mSubId;

    public TelephonyManager getPhoneFromSimInfo() {
      return this.mPhone;
    }

    public String getSimOperatorFromSimInfo() {
      return this.mSimOperator;
    }

    public int getSlotIdFromSimInformation() {
      return this.mSlotId;
    }

    public void setSimSlotId(int i) {
      this.mSlotId = i;
    }

    public SimInformationForDsds(
        int i, String str, HashMap hashMap, TelephonyManager telephonyManager) {
      this.mSubId = i;
      this.mSimOperator = str;
      this.mConfigMap = hashMap;
      this.mPhone = telephonyManager;
    }
  }

  public SimInformationForDsds getSimInformationWithSlotId(int i) {
    for (Map.Entry entry : this.mSimInformationHashMap.entrySet()) {
      if (((SimInformationForDsds) entry.getValue()).getSlotIdFromSimInformation() == i) {
        return (SimInformationForDsds) entry.getValue();
      }
    }
    return null;
  }

  @Override // com.android.server.location.gnss.GnssLocationProvider
  public void handleReportSvStatusSec(GnssStatus gnssStatus) {
    printSvStatus(gnssStatus);
  }

  public final void printSvStatus(GnssStatus gnssStatus) {
    StringBuilder sb = new StringBuilder();
    int i = 0;
    int i2 = 0;
    for (int i3 = 0; i3 < gnssStatus.getSatelliteCount(); i3++) {
      if (gnssStatus.getCn0DbHz(i3) > DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
        buildSingleSvInfoMessage(gnssStatus, sb, i3);
        if (i2 % 6 == 5) {
          sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        }
        i2++;
        if (gnssStatus.usedInFix(i3)) {
          i++;
        }
      }
    }
    Log.d(
        "GnssLocationProvider_ex",
        "SV Count : " + i + " / " + i2 + "      (PRN, Constellation, SNR, Used)");
    if (i2 != 0) {
      Log.d("GnssLocationProvider_ex", sb.toString());
    }
  }

  public final void buildSingleSvInfoMessage(GnssStatus gnssStatus, StringBuilder sb, int i) {
    int constellationType = gnssStatus.getConstellationType(i);
    sb.append("(");
    sb.append(gnssStatus.getSvid(i));
    sb.append(", ");
    sb.append(getConstellationTypeString(constellationType));
    sb.append(", ");
    sb.append(String.format("%.1f", Float.valueOf(gnssStatus.getCn0DbHz(i))));
    sb.append(", ");
    sb.append(gnssStatus.usedInFix(i) ? 1 : 0);
    sb.append(") ");
  }

  public final String getConstellationTypeString(int i) {
    String[] strArr = this.mConstellationString;
    if (i >= strArr.length || i < 0) {
      return strArr[0];
    }
    return strArr[i];
  }

  @Override // com.android.server.location.gnss.GnssLocationProvider
  public void setXtraDownloadedTime() {
    Log.d("GnssLocationProvider_ex", "setXtraDownloadedTime()");
    SystemProperties.set("persist.sys.xtra_time", Long.toString(System.currentTimeMillis()));
  }

  public final void setAutoConfigSuplServer() {
    String autoSuplUrl = getAutoSuplUrl();
    if (autoSuplUrl != null) {
      updateSuplServerConfiguration(2, autoSuplUrl, 7275, 1, 0, 15);
      this.mIsSetAutoSuplServer = true;
    }
  }

  /* JADX WARN: Code restructure failed: missing block: B:12:0x002e, code lost:

     if (java.lang.Integer.parseInt(r1) == 1) goto L16;
  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final boolean updateSuplServerFromCSC() {
    boolean z;
    Log.d("GnssLocationProvider_ex", "updateSuplServerFromCSC");
    if (!checkIfFileExists(new File("/data/system/gps/cscgps.conf"))) {
      return false;
    }
    Properties propertiesFromFile = getPropertiesFromFile("/data/system/gps/cscgps.conf");
    String property = propertiesFromFile.getProperty("CSC_SUPL_SUPLSERVERFROMCSC");
    if (property != null && property.length() != 0) {
      try {
        z = true;
      } catch (NumberFormatException e) {
        e.printStackTrace();
      }
    }
    z = false;
    if (z) {
      return setSuplServerWithProperties(propertiesFromFile);
    }
    return false;
  }

  public final boolean checkIfFileExists(File file) {
    try {
      return file.exists();
    } catch (SecurityException e) {
      e.printStackTrace();
      return false;
    }
  }

  public final boolean setSuplServerWithProperties(Properties properties) {
    try {
      updateSuplServerConfiguration(
          Integer.parseInt(properties.getProperty("CSC_SUPL_VER", "1")),
          properties.getProperty("CSC_SUPL_HOST"),
          Integer.parseInt(properties.getProperty("CSC_SUPL_PORT", "7276")),
          Integer.parseInt(properties.getProperty("CSC_SUPL_SSL", "0")),
          0,
          Integer.parseInt(properties.getProperty("CSC_SUPL_CERT", "0")));
      return true;
    } catch (NumberFormatException e) {
      e.printStackTrace();
      return false;
    }
  }

  @Override // com.android.server.location.gnss.GnssLocationProvider
  public void setSuplServerSec() {
    if (updateSuplServerFromCSC() || this.mGnssVendorConfig.isIzatServiceEnabled()) {
      return;
    }
    if (this.mGnssVendorConfig.isUnisocGnss() && this.mGnssConfiguration.isWifiOnlyModel()) {
      setSuplServerForUnisocWifiOnly();
      return;
    }
    if (this.mCarrierConfig.isTmbSuplServerRequired()) {
      updateSuplServerConfiguration(8, "supl.geo.t-mobile.com", 7275, 1, 0, 15);
      return;
    }
    if (this.mCarrierConfig.isGoogleServerAgpsOnlyRequired()) {
      updateSuplServerConfiguration(8, "supl.google.com", 7275, 1, 0, 15);
      return;
    }
    if (this.mCarrierConfig.getCarrier() == CarrierConfig.Carrier.JPN_DCM) {
      setDcmSuplServer();
      return;
    }
    if (this.mCarrierConfig.getCarrier() == CarrierConfig.Carrier.JPN_KDI) {
      updateSuplServerConfiguration(2, "location2.kddi.ne.jp", 7275, 1, 0, 15);
      return;
    }
    if (this.mCarrierConfig.getCarrier() == CarrierConfig.Carrier.JPN_RKT) {
      updateSuplServerConfiguration(3, "supl.rm-n.jp", 7275, 1, 0, 15);
      return;
    }
    if (this.mCarrierConfig.isChcSuplRequired()) {
      setChcSuplServer();
      return;
    }
    if (this.mCarrierConfig.isUneSuplRequired()) {
      updateSuplServerConfiguration(2, "supl.attmex.mx", 7275, 1, 0, 15);
    } else if (this.mCarrierConfig.getCarrier() == CarrierConfig.Carrier.ARG_UFN) {
      setAutoConfigSuplServer();
    } else {
      updateSuplServerConfiguration(3, "supl.google.com", 7275, 1, 0, 15);
    }
  }

  public final void setDcmSuplServer() {
    String str = SystemProperties.get("dcm_supl_iot", "false");
    Log.d("GnssLocationProvider_ex", "Set supl server for JPN_DCM, iot value:" + str);
    if ("true".equals(str)) {
      updateSuplServerConfiguration(3, "dcm-supl.com", 7275, 1, 1, 0);
    } else {
      updateSuplServerConfiguration(3, "supl.google.com", 7275, 1, 0, 15);
    }
  }

  public final void setChcSuplServer() {
    updateSuplServerConfiguration(1, "221.176.0.55", 7275, 1, 0, 15);
  }

  public final void setSuplServerForUnisocWifiOnly() {
    updateSuplServerConfiguration(3, "supl.google.com", 7275, 1, 0, 15);
  }

  @Override // com.android.server.location.gnss.GnssLocationProvider,
            // com.android.server.location.provider.AbstractLocationProvider
  public void onExtraCommand(int i, int i2, String str, Bundle bundle) {
    super.onExtraCommand(i, i2, str, bundle);
    if ("set_csc_parameters".equals(str)) {
      setCscParameters(bundle);
      return;
    }
    if ("com.skt.intent.action.AGPS".equals(str)) {
      handleSktAgpsCommand(bundle);
      return;
    }
    if ("setOllehServer".equals(str)) {
      handleKtSuplServerCommand(bundle);
      return;
    }
    if ("setNativeServer".equals(str)) {
      Log.d("GnssLocationProvider_ex", "setNativeServer");
      updateKTSuplServerEnabled(false);
      if (this.mGnssVendorConfig.isIzatServiceEnabled()) {
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
      Log.d("GnssLocationProvider_ex", "setMode ktPositionMode :" + this.ktPositionMode);
      return;
    }
    if ("getMode".equals(str)) {
      Log.d("GnssLocationProvider_ex", "getMode ktPositionMode :" + this.ktPositionMode);
      bundle.putInt("mode", this.ktPositionMode);
      return;
    }
    if ("set_use_udp".equals(str)) {
      this.mSuplInitHandler.handleUseUdpCommand(bundle);
      return;
    }
    if ("set_dcm_iot".equals(str)) {
      Log.d("GnssLocationProvider_ex", "set_dcm_iot : " + bundle.getBoolean("dcm_iot"));
      setDcmSuplIot(bundle.getBoolean("dcm_iot"));
      return;
    }
    if ("set_lpp_support".equals(str)) {
      setLppSupport(bundle.getInt("set_lpp"));
      return;
    }
    if ("setSecGpsConf".equals(str)) {
      Log.d("GnssLocationProvider_ex", "setSecGpsConf");
      setSecGpsConf(bundle);
      return;
    }
    if ("deleteSecGpsConf".equals(str)) {
      Log.d("GnssLocationProvider_ex", "deleteSecGpsConf");
      deleteSecGpsConf();
      return;
    }
    if ("gnss_configuration".equals(str)) {
      String property = this.mPropertiesSecgps.getProperty("USE_SECGPS_CONF");
      if (this.mGnssVendorConfig.isIzatServiceEnabled() || !"1".equals(property)) {
        return;
      }
      String string = bundle.getString("config_string", "");
      if ("".equals(string)) {
        return;
      }
      this.mGnssNative.gnssConfigurationUpdateSec(string);
      return;
    }
    if ("update_last_location".equals(str)) {
      Location location = (Location) bundle.getParcelable("last_location");
      if (location == null) {
        Log.w("GnssLocationProvider_ex", "Invalid last location info");
        return;
      } else {
        Log.v("GnssLocationProvider_ex", "Update last location directly to LMS !!!!!!!!!!!!, ");
        reportLocation(LocationResult.wrap(new Location[] {location}).validate());
        return;
      }
    }
    if ("gnss_configuration_from_nsflp".equals(str)) {
      String string2 = bundle.getString("config_string", "");
      if ("".equals(string2)) {
        return;
      }
      this.mGnssNative.gnssConfigurationUpdateSec(string2);
      Scanner scanner = new Scanner(string2);
      while (scanner.hasNextLine()) {
        try {
          String nextLine = scanner.nextLine();
          if (nextLine != null && nextLine.length() > 0) {
            handleEachLineForConfigString(nextLine);
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
      handleIssueTrackerCommand(bundle);
    } else {
      if ("set_emergency_state".equals(str) && !this.mGnssVendorConfig.isIzatServiceEnabled()) {
        String string3 = bundle.getString("config_string", "");
        if ("".equals(string3)) {
          return;
        }
        this.mGnssNative.gnssConfigurationUpdateSec(string3);
        return;
      }
      Log.w("GnssLocationProvider_ex", "onExtraCommand: unknown command " + str);
    }
  }

  public final void handleSktAgpsCommand(Bundle bundle) {
    String string = bundle.getString("opType");
    String string2 = bundle.getString("cmdType");
    if ("on".equals(string2)) {
      if ("msAssisted".equals(string) || "msBased".equals(string)) {
        updateSKApplicationFrameworkEnabled(true);
      }
    } else if ("off".equals(string2)) {
      updateSKApplicationFrameworkEnabled(false);
      SystemProperties.set("sys.sktgps", "0");
    }
    if (this.mGnssVendorConfig.isIzatServiceEnabled()) {
      updateKoreanOperatorsSuplSetting();
    }
  }

  public final void handleKtSuplServerCommand(Bundle bundle) {
    String string = bundle.getString("host");
    int i = bundle.getInt("port");
    updateKTSuplServerEnabled(true);
    this.mKTSuplServerHost = string;
    this.mKTSuplServerPort = i;
    if (this.mGnssVendorConfig.isIzatServiceEnabled()) {
      updateKoreanOperatorsSuplSetting();
    }
    Log.d(
        "GnssLocationProvider_ex",
        "host :" + this.mKTSuplServerHost + " port =" + this.mKTSuplServerPort);
  }

  public final void handleEachLineForConfigString(String str) {
    Properties properties;
    String[] split = str.split("=");
    if (!isPropertyArrayValid(split) || (properties = this.mPropertiesNsFlp) == null) {
      return;
    }
    properties.setProperty(split[0], split[1]);
  }

  public final boolean isPropertyArrayValid(String[] strArr) {
    String str;
    String str2;
    return strArr.length > 1
        && (str = strArr[0]) != null
        && str.length() > 0
        && (str2 = strArr[1]) != null
        && str2.length() > 0;
  }

  public final void handleIssueTrackerCommand(Bundle bundle) {
    boolean z = bundle.getBoolean("ONOFF", false);
    Log.d(
        "GnssLocationProvider_ex",
        "extraCommand - com.sec.android.ISSUE_TRACKER_ONOFF, enabled = " + z);
    if (z) {
      SystemProperties.set("dev.gnss.silentloggingIssueTracker", "ON");
    } else {
      SystemProperties.set("dev.gnss.silentloggingIssueTracker", "OFF");
    }
  }

  public void sendExtraConfigurationString() {
    HashMap configMap = this.mCarrierConfig.getConfigMap();
    if (this.mGnssVendorConfig.isUnisocGnss() && this.mGnssConfiguration.isWifiOnlyModel()) {
      configMap.put("SUPL_USE_APN", "FALSE");
    }
    setCpAgpsProfile(this.mSimSlotId);
    StringBuilder convertHashMapToStringBuilder = convertHashMapToStringBuilder(configMap);
    convertHashMapToStringBuilder.append(getHardwareFactors());
    appendNsFlpConfigString(convertHashMapToStringBuilder);
    this.mGnssNative.gnssConfigurationUpdateSec(convertHashMapToStringBuilder.toString());
  }

  public final StringBuilder convertHashMapToStringBuilder(HashMap hashMap) {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry entry : hashMap.entrySet()) {
      String str = (String) entry.getKey();
      String str2 = (String) entry.getValue();
      Log.d("GnssLocationProvider_ex", str + "=" + str2);
      sb.append(str);
      sb.append("=");
      sb.append(str2);
      sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    }
    return sb;
  }

  public final void appendNsFlpConfigString(StringBuilder sb) {
    Properties properties = this.mPropertiesNsFlp;
    if (properties == null || properties.isEmpty()) {
      return;
    }
    StringWriter stringWriter = new StringWriter();
    try {
      this.mPropertiesNsFlp.store(stringWriter, "");
      sb.append(stringWriter.toString());
    } catch (IOException unused) {
      Log.e("GnssLocationProvider_ex", "could not store to writer");
    }
  }

  public final void setCpAgpsProfile(int i) {
    if (this.mCarrierConfig.isUSAMarket() || this.mCarrierConfig.isCanadaMarket()) {
      return;
    }
    int cpAgpsProfile = getCpAgpsProfile(i);
    Log.d(
        "GnssLocationProvider_ex",
        "AGPS Mask prev=" + this.prevCpAgpsMask + ", current=" + cpAgpsProfile);
    if (cpAgpsProfile <= this.prevCpAgpsMask || cpAgpsProfile < 0) {
      return;
    }
    Log.d("GnssLocationProvider_ex", "Set AGPS=" + cpAgpsProfile);
    this.prevCpAgpsMask = cpAgpsProfile;
    this.mGnssNative.gnssConfigurationUpdateSec(
        "CP_AGPS_ENABLE_PROFILE=" + Integer.toString(cpAgpsProfile));
  }

  public final int getCpAgpsProfile(int i) {
    return getBooleanCarrierFeature(i, "CarrierFeature_GPS_SupportEnableAgps").booleanValue()
        ? 1
        : 0;
  }

  public final void setSecGpsConf(Bundle bundle) {
    if (bundle == null) {
      return;
    }
    this.mPropertiesSecgps.setProperty("USE_SECGPS_CONF", "1");
    setPropertiesForSecGps(bundle);
    storePropertiesToFile(
        "/data/system/gps",
        "/data/system/gps/secgps.conf",
        this.mPropertiesSecgps,
        "SECGPS Configuration");
    sendSecGpsConfigToHal();
  }

  public final void setPropertiesForSecGps(Bundle bundle) {
    Set<String> keySet = bundle.keySet();
    int size = keySet.size();
    String[] strArr = new String[size];
    keySet.toArray(strArr);
    for (int i = 0; i < size; i++) {
      String str = strArr[i];
      if (str != null) {
        this.mPropertiesSecgps.setProperty(str, bundle.getString(str));
      }
    }
  }

  public final void storePropertiesToFile(
      String str, String str2, Properties properties, String str3) {
    try {
      createDirectories(str);
      File createFile = createFile(str2);
      setFilePermissions(createFile);
      storeFile(str2, properties, str3, createFile);
    } catch (IOException | SecurityException e) {
      e.printStackTrace();
    }
  }

  public final void createDirectories(String str) {
    File file = new File(str);
    if (file.exists() || file.mkdirs()) {
      return;
    }
    Log.e("GnssLocationProvider_ex", "Directory " + str + " creation failed.");
  }

  public final File createFile(String str) {
    File file = new File(str);
    if (!file.exists()) {
      Log.e(
          "GnssLocationProvider_ex",
          str + " file doesn't exist. create result = " + file.createNewFile());
    }
    return file;
  }

  public final void setFilePermissions(File file) {
    if (!file.setReadable(true, false)) {
      Log.e("GnssLocationProvider_ex", "file.setReadable() failed.");
    }
    if (file.setWritable(true, false)) {
      return;
    }
    Log.e("GnssLocationProvider_ex", "file.setWritable() failed.");
  }

  public final void storeFile(String str, Properties properties, String str2, File file) {
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(file);
      try {
        properties.store(fileOutputStream, str2);
        Log.d("GnssLocationProvider_ex", "Saved: " + str);
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

  public final void sendSecGpsConfigToHal() {
    final StringWriter stringWriter = new StringWriter();
    try {
      this.mPropertiesSecgps.store(stringWriter, "SECGPS Configuration");
    } catch (IOException unused) {
      Log.e("GnssLocationProvider_ex", "could not store to writer");
    }
    if (!this.mGnssVendorConfig.isIzatServiceEnabled()) {
      this.mGnssNative.gnssConfigurationUpdateSec(stringWriter.toString());
    } else {
      postWithWakeLockHeld(
          new Runnable() { // from class:
                           // com.android.server.location.gnss.GnssLocationProviderSec$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
              GnssLocationProviderSec.this.lambda$sendSecGpsConfigToHal$1(stringWriter);
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$sendSecGpsConfigToHal$1(StringWriter stringWriter) {
    lambda$updateSuplServerConfiguration$3(stringWriter.toString());
  }

  public final void deleteSecGpsConf() {
    this.mPropertiesSecgps.setProperty("USE_SECGPS_CONF", "0");
    File file = new File("/data/system/gps/secgps.conf");
    try {
      if (file.exists() && file.delete()) {
        Log.d("GnssLocationProvider_ex", "secgps.conf deleted.");
      }
    } catch (SecurityException unused) {
      Log.e(
          "GnssLocationProvider_ex",
          " could not access secgps.conf file : /data/system/gps/secgps.conf");
    }
    deleteSecGpsConfigAtHal();
  }

  public final void deleteSecGpsConfigAtHal() {
    if (!this.mGnssVendorConfig.isIzatServiceEnabled()) {
      this.mGnssNative.gnssConfigurationUpdateSec("USE_SECGPS_CONF=0");
    } else {
      postWithWakeLockHeld(
          new Runnable() { // from class:
                           // com.android.server.location.gnss.GnssLocationProviderSec$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
              GnssLocationProviderSec.this.lambda$deleteSecGpsConfigAtHal$2();
            }
          });
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$deleteSecGpsConfigAtHal$2() {
    lambda$updateSuplServerConfiguration$3("USE_SECGPS_CONF=0");
  }

  @Override // com.android.server.location.gnss.GnssLocationProvider
  public void updateSuplServerForNewSISession() {
    if (this.mSuplInitHandler.getNiSessionStarted()) {
      Log.d("GnssLocationProvider_ex", "NISession flag set to false");
      this.mSuplInitHandler.setNiSessionStarted(false);
    }
    int defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
    if (SubscriptionManager.isValidSubscriptionId(defaultDataSubscriptionId)) {
      Log.d("GnssLocationProvider_ex", "Set SubIdForSuplNi to DD subID");
      this.mSuplInitHandler.setSubIdForSuplNi(defaultDataSubscriptionId);
    }
    this.mTelephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
    if (this.mGnssVendorConfig.isIzatServiceEnabled() || !this.mCarrierConfig.isKoreaMarket()) {
      return;
    }
    updateKoreanOperatorsSuplSetting();
  }

  @Override // com.android.server.location.gnss.GnssLocationProvider
  public int getPositionModeSec(int i, ProviderRequest providerRequest) {
    if (isGpsEnabled() && !GnssNative.isSupported()) {
      Log.e("GnssLocationProvider_ex", "HIDL service may have been died. reset it.");
      this.mGnssNative.init();
    }
    if (this.mGnssVendorConfig.isUnisocGnss() && this.mGnssConfiguration.isWifiOnlyModel()) {
      if (i != 1 || isWlanApConnected) {
        return i;
      }
      Log.d("GnssLocationProvider_ex", "Data status WIFI disconneted");
      return 0;
    }
    if (this.mCarrierConfig.isChinaCarrier()) {
      i = getPositionModeForChn();
      Log.d("GnssLocationProvider_ex", "CHN startNavigating mPositionMode: " + i);
    }
    if (networkStateNeedsModeStandalone()) {
      return 0;
    }
    if (this.mCarrierConfig.isUsaCdmaMarket()) {
      if (this.mTelephonyManager.getNetworkType() == 13) {
        Log.d("GnssLocationProvider_ex", "LTE. mPositionMode is set to MSBASED");
        i = 1;
      } else {
        Log.d("GnssLocationProvider_ex", "not LTE. mPositionMode is changed to STANDALONE");
        i = 0;
      }
    }
    if (this.mGnssConfiguration.isWifiOnlyModel()) {
      Log.e(
          "GnssLocationProvider_ex",
          "mPositionMode set to GPS_POSITION_MODE_STANDALONE because of WiFi only model.");
      i = 0;
    }
    if (this.mCarrierConfig.isChinaTelecom()) {
      i = getPositionModeForCTC();
      Log.d("GnssLocationProvider_ex", "CHN_CTC startNavigating mPositionMode: " + i);
    }
    if (this.mCarrierConfig.isJapanDocomo()) {
      boolean z =
          Settings.System.getInt(this.mContext.getContentResolver(), "gps_noti_sound_enabled", 0)
              != 0;
      Log.d("GnssLocationProvider_ex", "GPS noti sound enabled : " + z);
      if (z) {
        generateBeep();
      }
    }
    if (this.mCarrierConfig.isJapanJcom() || this.mCarrierConfig.isJapanUQMobile()) {
      Log.d("GnssLocationProvider_ex", "KDDI MVNO JCOM/UQM setPositionmode on StartNavigating0");
      i = 0;
    }
    if (this.mTelephonyManager.getSimOperator().length() != 0
        || (this.mGnssVendorConfig.isUnisocGnss()
            && !(this.mGnssVendorConfig.isUnisocGnss()
                && this.mCarrierConfig.shouldSupportSuplUseApnLatinMarket()))) {
      return i;
    }
    Log.d("GnssLocationProvider_ex", "SIM card absent. force set position_mode to standalone");
    return 0;
  }

  @Override // com.android.server.location.gnss.GnssLocationProvider
  public void startNavigatingSec() {
    if (this.mGnssVendorConfig.isIzatServiceEnabled()
        || this.mGnssVendorConfig.isMtkGnss()
        || this.mGnssVendorConfig.isBroadcomGnss()
        || this.mGnssVendorConfig.isUnisocGnss()) {
      return;
    }
    this.mVSFilter.init();
  }

  @Override // com.android.server.location.gnss.GnssLocationProvider
  public void stopNavigatingSec() {
    if (this.mCarrierConfig.isKoreaMarket()) {
      if (isSKApplicationFrameworkEnabled()) {
        updateSKApplicationFrameworkEnabled(false);
        Log.d("GnssLocationProvider_ex", "stopNavigating : isSKAF changed");
        SystemProperties.set("sys.sktgps", "0");
        if (this.mGnssVendorConfig.isIzatServiceEnabled()) {
          updateKoreanOperatorsSuplSetting();
        }
      }
      if (isKTSuplServerEnabled()) {
        updateKTSuplServerEnabled(false);
        Log.d("GnssLocationProvider_ex", "mIsKtGps is changed");
        if (this.mGnssVendorConfig.isIzatServiceEnabled()) {
          updateKoreanOperatorsSuplSetting();
          return;
        }
        return;
      }
      return;
    }
    if (!this.mCarrierConfig.isJapanDocomo() || this.mFocusToneGenerator == null) {
      return;
    }
    Log.d("GnssLocationProvider_ex", "release ToneGenerator");
    this.mFocusToneGenerator.release();
    this.mFocusToneGenerator = null;
  }

  public final boolean networkStateNeedsModeStandalone() {
    if (isWlanApConnected) {
      Log.d("GnssLocationProvider_ex", "Data status WIFI connected");
      if (!this.mCarrierConfig.isJapanDocomo()
          && (!this.mGnssVendorConfig.isUnisocGnss()
              || this.mCarrierConfig.shouldSupportSuplUseApnLatinMarket())) {
        return true;
      }
      Log.d("GnssLocationProvider_ex", "The vendor Not set to Standalone");
      return false;
    }
    if (!this.mNetworkConnectivityHandler.isDataNetworkConnected()) {
      return true;
    }
    Log.d(
        "GnssLocationProvider_ex",
        "Data network status " + this.mNetworkConnectivityHandler.isDataNetworkConnected());
    NetworkInfo activeNetworkInfo = this.mConnectivityManager.getActiveNetworkInfo();
    if (activeNetworkInfo == null || !activeNetworkInfo.isRoaming()) {
      return false;
    }
    Log.d("GnssLocationProvider_ex", "Roaming status " + activeNetworkInfo.isRoaming());
    return true;
  }

  public void updateSuplServerConfiguration(int i, String str, int i2, int i3, int i4, int i5) {
    final String str2 =
        "AGPS_TYPE=3\nSUPL_VERSION="
            + i
            + "\nSUPL_HOST="
            + str
            + "\nSUPL_PORT="
            + i2
            + "\nSUPL_SSL="
            + i3
            + "\nSSL_VER="
            + i4
            + "\nSSL_TYPE="
            + i5;
    Log.d(
        "GnssLocationProvider_ex",
        "Update SUPL Configuration["
            + i
            + "|"
            + str
            + "|"
            + i2
            + "|"
            + i3
            + "|"
            + i4
            + "|"
            + i5
            + "]");
    if (!this.mGnssVendorConfig.isIzatServiceEnabled()) {
      this.mGnssNative.gnssConfigurationUpdateSec(str2);
    } else {
      postWithWakeLockHeld(
          new Runnable() { // from class:
                           // com.android.server.location.gnss.GnssLocationProviderSec$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              GnssLocationProviderSec.this.lambda$updateSuplServerConfiguration$3(str2);
            }
          });
    }
    this.mNSLocationProviderHelper.updateSuplAddress(str);
  }

  public final void updatePsdsEnabled(final int i) {
    Log.d("GnssLocationProvider_ex", "configurationUpdate_xtraEnable, XTRA_ENABLE=" + i);
    if (!this.mGnssVendorConfig.isIzatServiceEnabled()) {
      this.mGnssNative.gnssConfigurationUpdateSec("XTRA_ENABLE=" + i);
      return;
    }
    postWithWakeLockHeld(
        new Runnable() { // from class:
                         // com.android.server.location.gnss.GnssLocationProviderSec$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            GnssLocationProviderSec.this.lambda$updatePsdsEnabled$4(i);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$updatePsdsEnabled$4(int i) {
    lambda$updateSuplServerConfiguration$3("XTRA_ENABLE=" + i);
  }

  public final void updateLppSupportConfiguration(int i) {
    if (this.mGnssVendorConfig.isIzatServiceEnabled()) {
      return;
    }
    Log.d("GnssLocationProvider_ex", "configurationUpdate_setLppSupport, LPP_PROFILE=" + i);
    this.mGnssNative.gnssConfigurationUpdateSec("LPP_PROFILE=" + i);
  }

  public final void updateNrLppSupportConfiguration(int i) {
    if (this.mGnssVendorConfig.isIzatServiceEnabled()) {
      return;
    }
    Log.d("GnssLocationProvider_ex", "configurationUpdate_setNrLppSupport, NR_LPP_PROFILE=" + i);
    this.mGnssNative.gnssConfigurationUpdateSec("NR_LPP_PROFILE=" + i);
  }

  @Override // com.android.server.location.gnss.GnssLocationProvider,
            // com.android.server.location.gnss.hal.GnssNative.LocationRequestCallbacks
  public void onRequestRefLocation() {
    Log.d("GnssLocationProvider_ex", "onRequestRefLocation");
    this.isSehRefLocation = false;
    requestRefLocationSec();
  }

  @Override // com.android.server.location.gnss.hal.GnssNative.LocationRequestCallbacks
  public void onRequestRefLocationSec() {
    Log.d("GnssLocationProvider_ex", "onRequestRefLocationSec");
    this.isSehRefLocation = true;
    requestRefLocationSec();
  }

  public final void requestRefLocationSec() {
    Log.d("GnssLocationProvider_ex", "requestRefLocationSec");
    if (this.mSuplInitHandler.getNiSessionStarted()) {
      SimInformationForDsds simInformationForDsds =
          (SimInformationForDsds)
              this.mSimInformationHashMap.get(Integer.valueOf(SuplInitHandler.getSubIdForSuplNi()));
      if (simInformationForDsds == null || simInformationForDsds.getPhoneFromSimInfo() == null) {
        return;
      }
      this.mTelephonyManager = simInformationForDsds.getPhoneFromSimInfo();
      simInformationForDsds
          .getPhoneFromSimInfo()
          .requestCellInfoUpdate(this.mContext.getMainExecutor(), this.mCellInfoCb);
      return;
    }
    TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
    if (telephonyManager.getAllCellInfo() != null) {
      telephonyManager.requestCellInfoUpdate(this.mContext.getMainExecutor(), this.mCellInfoCb);
    } else {
      Log.e("GnssLocationProvider_ex", "Error getting cell location info.");
    }
  }

  @Override // com.android.server.location.gnss.GnssLocationProvider,
            // com.android.server.location.gnss.hal.GnssNative.AGpsCallbacks
  public void onRequestSetID(int i) {
    int defaultDataSubscriptionId;
    String str;
    Log.d("GnssLocationProvider_ex", "onRequestSetId");
    TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
    SuplInitHandler suplInitHandler = this.mSuplInitHandler;
    if (suplInitHandler != null && suplInitHandler.getNiSessionStarted()) {
      defaultDataSubscriptionId = SuplInitHandler.getSubIdForSuplNi();
    } else {
      defaultDataSubscriptionId = SubscriptionManager.getDefaultDataSubscriptionId();
    }
    if (SubscriptionManager.isValidSubscriptionId(defaultDataSubscriptionId)) {
      telephonyManager = telephonyManager.createForSubscriptionId(defaultDataSubscriptionId);
    }
    int i2 = 0;
    GnssHalStatus gnssHalStatus = null;
    if ((i & 1) == 1) {
      str = telephonyManager.getSubscriberId();
      if (str != null) {
        i2 = 1;
      }
    } else if ((i & 2) == 2) {
      str = telephonyManager.getLine1Number();
      if (str != null) {
        i2 = 2;
      }
    } else {
      str = null;
    }
    if (i2 == 0
        && !this.mGnssVendorConfig.isIzatServiceEnabled()
        && this.mCarrierConfig.isUsaVerizon()) {
      str = telephonyManager.getImei();
    }
    if (this.mGnssVendorConfig.isLsiGnss()) {
      gnssHalStatus = new GnssHalStatus();
      gnssHalStatus.triggerCheckingHalStatus();
    }
    GnssNative gnssNative = this.mGnssNative;
    if (str == null) {
      str = "";
    }
    gnssNative.setAgpsSetId(i2, str);
    if (gnssHalStatus != null) {
      gnssHalStatus.updateHalStatusChecked(true);
    }
  }

  public final ArrayList getCarrierFeatureString() {
    ArrayList arrayList = new ArrayList();
    int i = SystemProperties.getInt("ro.multisim.simslotcount", 1);
    for (int i2 = 0; i2 < i; i2++) {
      arrayList.add(
          "\n Sim slot ID  :  "
              + i2
              + "\n AGPS Setting  :  "
              + getStrCarrierFeature(i2, "CarrierFeature_GPS_ConfigAgpsSetting")
              + "\n SUPL Address  :  "
              + getStrCarrierFeature(i2, "CarrierFeature_GPS_ConfigSuplHost")
              + "\n SUPL version  :  "
              + getIntCarrierFeature(i2, "CarrierFeature_GPS_ConfigSuplVersion")
              + "\n AGPS Mode  :  "
              + getIntCarrierFeature(i2, "CarrierFeature_GPS_ConfigAgpsMode")
              + "\n AGNSS Protocol  :  "
              + getIntCarrierFeature(i2, "CarrierFeature_GPS_ConfigAgnssProtocol")
              + "\n LPPeCP  :  "
              + getIntCarrierFeature(i2, "CarrierFeature_GPS_ConfigLppeCp")
              + "\n LPPeUP  :  "
              + getIntCarrierFeature(i2, "CarrierFeature_GPS_ConfigLppeUp")
              + "\n ES Extension Sec  :  "
              + getIntCarrierFeature(i2, "CarrierFeature_GPS_ConfigEsExtensionSec")
              + "\n Exception mask for Agnss  :  "
              + getIntCarrierFeature(i2, "CarrierFeature_GPS_ConfigExceptionMaskForAGNSS")
              + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    }
    return arrayList;
  }

  public final String getSecgpsConfiguration() {
    Properties propertiesFromFile = getPropertiesFromFile("/data/system/gps/secgps.conf");
    return "\n Time out  :  "
        + propertiesFromFile.getProperty("TIMEOUT")
        + "\n AGPS Mode :  "
        + propertiesFromFile.getProperty("AGPS_MODE")
        + "\n LPPe CP  :  "
        + propertiesFromFile.getProperty("LPPE_CP_TECHNOLOGY")
        + "\n LPPe UP  :  "
        + propertiesFromFile.getProperty("LPPE_UP_TECHNOLOGY")
        + "\n XTRA Enable  :  "
        + propertiesFromFile.getProperty("ENABLE_XTRA")
        + "\n SSL Enable :  "
        + propertiesFromFile.getProperty("SSL")
        + "\n Opeartion Mode  :  "
        + propertiesFromFile.getProperty("OPERATION_MODE")
        + "\n Start Mode :  "
        + propertiesFromFile.getProperty("START_MODE")
        + "\n Agnss Protocol  :  "
        + propertiesFromFile.getProperty("AGNSS_PROTOCOL")
        + "\n SUPL Port :  "
        + propertiesFromFile.getProperty("SUPL_PORT")
        + "\n SUPL Host  :  "
        + propertiesFromFile.getProperty("SUPL_HOST")
        + "\n SUPL Version   :  "
        + propertiesFromFile.getProperty("SUPL_VERSION")
        + "\n LPP Profile :  "
        + propertiesFromFile.getProperty("LPP_PROFILE")
        + "\n Enable L5  :  "
        + propertiesFromFile.getProperty("ENABLE_L5")
        + "\n Enable L5 TIS  :  "
        + propertiesFromFile.getProperty("ENABLE_L5_TIS")
        + "\n Spirent  :  "
        + propertiesFromFile.getProperty("SPIRENT")
        + "\n Week Number  :  "
        + propertiesFromFile.getProperty("WEEK_NUMBER")
        + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE;
  }

  @Override // com.android.server.location.gnss.GnssLocationProvider
  public void dumpSec(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    if (this.mGnssVendorConfig.isIzatServiceEnabled()) {
      printWriter.println("============ GPS Carrier Feature State ============");
      printWriter.println(getCarrierFeatureString() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      File file = new File("/data/system/gps/secgps.conf");
      printWriter.println("============ GPS SECGPS CONFIGURATION State ============");
      if (file.exists()) {
        printWriter.println(getSecgpsConfiguration() + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
      } else {
        printWriter.println(" There is no secgps.conf file !!!!!");
      }
      printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    }
  }

  @Override // com.android.server.location.gnss.GnssLocationProvider
  /* renamed from: gnssConfigurationUpdateSec, reason: merged with bridge method [inline-methods] */
  public void lambda$updateSuplServerConfiguration$3(String str) {
    Log.d("GnssLocationProvider_ex", "gnssConfigurationUpdateSec");
    this.mGnssNative.gnssConfigurationUpdateSec(str);
  }

  public class CtsRestrictModeFileObserver extends FileObserver {
    public CtsRestrictModeFileObserver(File file, int i) {
      super(file, i);
    }

    @Override // android.os.FileObserver
    public void onEvent(int i, String str) {
      Log.d("GnssLocationProvider_ex", "CTS restrict mode : event =" + i);
      if (i == 2) {
        Log.d("GnssLocationProvider_ex", "CTS restrict mode : file modified!");
        secCheckCtsRestrictMode();
      }
    }

    public final void secCheckCtsRestrictMode() {
      final String readRestrictModeFromFile =
          readRestrictModeFromFile(new File("/sys/class/sensors/ssc_core/operation_mode"));
      if (readRestrictModeFromFile == null) {
        return;
      }
      Log.w(
          "GnssLocationProvider_ex",
          "CTS sensorservice restrict_mode = " + readRestrictModeFromFile);
      if ("restrict".equals(readRestrictModeFromFile)
          || "normal".equals(readRestrictModeFromFile)) {
        GnssLocationProviderSec.this.postWithWakeLockHeld(
            new Runnable() { // from class:
                             // com.android.server.location.gnss.GnssLocationProviderSec$CtsRestrictModeFileObserver$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                GnssLocationProviderSec.CtsRestrictModeFileObserver.this
                    .lambda$secCheckCtsRestrictMode$0(readRestrictModeFromFile);
              }
            });
      }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$secCheckCtsRestrictMode$0(String str) {
      GnssLocationProviderSec.this.lambda$updateSuplServerConfiguration$3(
          "CTS_RESTRICTMODE=" + str);
    }

    public final String readRestrictModeFromFile(File file) {
      String str = "";
      try {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
          InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
          try {
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            try {
              str = bufferedReader.readLine();
              bufferedReader.close();
              inputStreamReader.close();
              fileInputStream.close();
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
      } catch (FileNotFoundException unused) {
        Log.w(
            "GnssLocationProvider_ex",
            "Could not open File /sys/class/sensors/ssc_core/operation_mode");
      } catch (IOException e) {
        e.printStackTrace();
      } catch (IllegalArgumentException unused2) {
        Log.w(
            "GnssLocationProvider_ex",
            "Could not load file /sys/class/sensors/ssc_core/operation_modedue to"
                + " IllegalArgumentException");
      } catch (SecurityException unused3) {
        Log.w(
            "GnssLocationProvider_ex",
            "Could not access file /sys/class/sensors/ssc_core/operation_mode");
      }
      return str;
    }
  }

  @Override // com.android.server.location.gnss.GnssLocationProvider
  public boolean isDeviceBasedHybridSupported() {
    return hasLppeDbhCapability("CarrierFeature_GPS_ConfigLppeCp")
        || hasLppeDbhCapability("CarrierFeature_GPS_ConfigLppeUp");
  }

  public final boolean hasLppeDbhCapability(String str) {
    return getIntCarrierFeature(this.mSimSlotId, str) > 0
        && (getIntCarrierFeature(this.mSimSlotId, str) & 1) > 0;
  }

  public final int getIntCarrierFeature(int i, String str) {
    return SemCarrierFeature.getInstance().getInt(i, str, -1, false);
  }

  public final String getStrCarrierFeature(int i, String str) {
    return SemCarrierFeature.getInstance().getString(i, str, "", false);
  }

  public final Boolean getBooleanCarrierFeature(int i, String str) {
    return Boolean.valueOf(SemCarrierFeature.getInstance().getBoolean(i, str, false, false));
  }

  @Override // com.android.server.location.gnss.GnssLocationProvider
  public boolean isExtraCommandAllowed(int i) {
    String property = this.mPropertiesSecgps.getProperty("ALLOW_EXTRA_COMMAND");
    if (property != null && property.equals("1")) {
      return true;
    }
    String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i);
    if (packagesForUid == null) {
      return false;
    }
    for (String str : packagesForUid) {
      if (checkAllowedPackage(str)) {
        return true;
      }
    }
    return false;
  }

  public final boolean checkAllowedPackage(String str) {
    for (String str2 : GnssConstants.EXTRA_COMMAND_APPROVED_APPS) {
      if (str2.equals(str)) {
        this.isExtraCommandFromAllowedAppRequest = true;
        return true;
      }
    }
    return false;
  }

  public class InputLidStateChangedListener implements InputManager.SemOnLidStateChangedListener {
    public InputLidStateChangedListener() {}

    public void onLidStateChanged(long j, int i) {
      GnssLocationProviderSec.this.mLidState = i;
      if (GnssLocationProviderSec.this.mLidState >= 0) {
        Log.d(
            "GnssLocationProvider_ex",
            "onLidStateChanged " + GnssLocationProviderSec.this.mLidState);
        GnssLocationProviderSec gnssLocationProviderSec = GnssLocationProviderSec.this;
        gnssLocationProviderSec.sendLidState(gnssLocationProviderSec.mLidState);
      }
    }
  }

  public final void sendLidState(final int i) {
    int i2 =
        SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_GPS_CONFIG_FOLD_CALIBRATED");
    if (i2 <= 0) {
      return;
    }
    if (i == 0) {
      i = 1;
    } else if (i == 1) {
      i = i2;
    }
    Log.d("GnssLocationProvider_ex", "sendLidState " + i);
    postWithWakeLockHeld(
        new Runnable() { // from class:
                         // com.android.server.location.gnss.GnssLocationProviderSec$$ExternalSyntheticLambda4
          @Override // java.lang.Runnable
          public final void run() {
            GnssLocationProviderSec.this.lambda$sendLidState$5(i);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$sendLidState$5(int i) {
    lambda$updateSuplServerConfiguration$3("LID_STATE=" + i);
  }

  @Override // com.android.server.location.gnss.GnssLocationProvider
  public boolean isKOREmergency(boolean z) {
    return this.mGnssVendorConfig.isIzatServiceEnabled()
        && z
        && this.mCarrierConfig.isKoreaMarket()
        && isDeviceBasedHybridSupported();
  }

  @Override // com.android.server.location.gnss.GnssLocationProvider
  public void releaseDozeMode() {
    IDeviceIdleController asInterface =
        IDeviceIdleController.Stub.asInterface(ServiceManager.getService("deviceidle"));
    boolean isDeviceIdleMode =
        ((PowerManager) this.mContext.getSystemService("power")).isDeviceIdleMode();
    Log.d("GnssLocationProvider_ex", "Device Idle Mode=" + isDeviceIdleMode);
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
}
