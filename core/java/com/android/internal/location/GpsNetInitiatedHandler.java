package com.android.internal.location;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.INetInitiatedListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.telephony.PreciseCallState;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.telephony.emergency.EmergencyNumber;
import android.util.Log;
import com.android.internal.C4337R;
import com.android.internal.app.NetInitiatedActivity;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.telephony.GsmAlphabet;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public class GpsNetInitiatedHandler {
  private static final int CALL_CONNECTED = 2;
  private static final int CALL_DISCONNECTED = 1;
  private static final int EMERGENCY_CALL_STATE_ACTIVE = 2;
  private static final int EMERGENCY_CALL_STATE_CONNECTING = 1;
  private static final int EMERGENCY_CALL_STATE_ECBM = 3;
  private static final int EMERGENCY_CALL_STATE_NONE = 0;
  public static final int GPS_ENC_NONE = 0;
  public static final int GPS_ENC_SUPL_GSM_DEFAULT = 1;
  public static final int GPS_ENC_SUPL_UCS2 = 3;
  public static final int GPS_ENC_SUPL_UTF8 = 2;
  public static final int GPS_ENC_UNKNOWN = -1;
  public static final int GPS_NI_NEED_NOTIFY = 1;
  public static final int GPS_NI_NEED_VERIFY = 2;
  public static final int GPS_NI_PRIVACY_OVERRIDE = 4;
  public static final int GPS_NI_RESPONSE_ACCEPT = 1;
  public static final int GPS_NI_RESPONSE_DENY = 2;
  public static final int GPS_NI_RESPONSE_IGNORE = 4;
  public static final int GPS_NI_RESPONSE_NORESP = 3;
  public static final int GPS_NI_TYPE_EMERGENCY_SUPL = 4;
  public static final int GPS_NI_TYPE_UMTS_CTRL_PLANE = 3;
  public static final int GPS_NI_TYPE_UMTS_SUPL = 2;
  public static final int GPS_NI_TYPE_VOICE = 1;
  private static final String INTENT_CALL_STATE = "com.samsung.rcs.CALL_STATE_CHANGED";
  private static final String INTENT_ECBM_CHANGED =
      "android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED";
  public static final String NI_EXTRA_CMD_NOTIF_ID = "notif_id";
  public static final String NI_EXTRA_CMD_RESPONSE = "response";
  public static final String NI_INTENT_KEY_DEFAULT_RESPONSE = "default_resp";
  public static final String NI_INTENT_KEY_MESSAGE = "message";
  public static final String NI_INTENT_KEY_NOTIF_ID = "notif_id";
  public static final String NI_INTENT_KEY_TIMEOUT = "timeout";
  public static final String NI_INTENT_KEY_TITLE = "title";
  public static final String NI_RESPONSE_EXTRA_CMD = "send_ni_response";
  private final BroadcastReceiver mBroadcastReciever;
  private final Context mContext;
  private final EmergencyCallCallback mEmergencyCallCallback;
  private final EmergencyCallListener mEmergencyCallListener;
  private int mEmergencyCallState;
  private int mEmergencySubId;
  private volatile boolean mIsInEmergencyCall;
  private volatile boolean mIsSuplEsEnabled;
  private final boolean mIzatServiceEnabled;
  private final LocationManager mLocationManager;
  private final INetInitiatedListener mNetInitiatedListener;
  private Notification.Builder mNiNotificationBuilder;
  private volatile int mNumOfEmergencyCalls;
  private final TelephonyManager mTelephonyManager;
  private static final String TAG = "GpsNetInitiatedHandler";
  private static final boolean DEBUG = Log.isLoggable(TAG, 3);
  private static boolean mIsHexInput = true;
  private boolean mPlaySounds = false;
  private boolean mPopupImmediately = true;
  private volatile boolean mIsLocationEnabled = false;
  private volatile long mCallEndElapsedRealtimeMillis = 0;
  private volatile long mEmergencyExtensionMillis = 0;

  public interface EmergencyCallCallback {
    void onEmergencyCallEnd();

    void onEmergencyCallStart(int i);
  }

  public static class GpsNiNotification {
    public int defaultResponse;
    public boolean needNotify;
    public boolean needVerify;
    public int niType;
    public int notificationId;
    public boolean privacyOverride;
    public String requestorId;
    public int requestorIdEncoding;
    public String text;
    public int textEncoding;
    public int timeout;
  }

  private class EmergencyCallListener extends TelephonyCallback
      implements TelephonyCallback.OutgoingEmergencyCallListener,
          TelephonyCallback.CallStateListener,
          TelephonyCallback.PreciseCallStateListener {
    private EmergencyCallListener() {}

    @Override // android.telephony.TelephonyCallback.OutgoingEmergencyCallListener
    public void onOutgoingEmergencyCall(EmergencyNumber placedEmergencyNumber, int subscriptionId) {
      if (GpsNetInitiatedHandler.this.mIzatServiceEnabled) {
        GpsNetInitiatedHandler.this.mIsInEmergencyCall = true;
        if (GpsNetInitiatedHandler.DEBUG) {
          Log.m94d(
              GpsNetInitiatedHandler.TAG,
              "onOutgoingEmergencyCall(): inEmergency = "
                  + GpsNetInitiatedHandler.this.getInEmergency());
        }
        GpsNetInitiatedHandler.this.mEmergencyCallCallback.onEmergencyCallStart(subscriptionId);
        return;
      }
      GpsNetInitiatedHandler.this.mEmergencySubId = subscriptionId;
      GpsNetInitiatedHandler.this.mEmergencyCallState = 1;
    }

    @Override // android.telephony.TelephonyCallback.CallStateListener
    public void onCallStateChanged(int state) {
      if (GpsNetInitiatedHandler.DEBUG) {
        Log.m94d(GpsNetInitiatedHandler.TAG, "onCallStateChanged(): state is " + state);
      }
      if (state == 0) {
        if (GpsNetInitiatedHandler.this.mIzatServiceEnabled) {
          if (GpsNetInitiatedHandler.this.mIsInEmergencyCall) {
            GpsNetInitiatedHandler.this.mCallEndElapsedRealtimeMillis =
                SystemClock.elapsedRealtime();
            GpsNetInitiatedHandler.this.mIsInEmergencyCall = false;
            GpsNetInitiatedHandler.this.mEmergencyCallCallback.onEmergencyCallEnd();
            return;
          }
          return;
        }
        if (!GpsNetInitiatedHandler.this.mIsInEmergencyCall) {
          if (GpsNetInitiatedHandler.this.mEmergencyCallState == 1) {
            GpsNetInitiatedHandler.this.mEmergencyCallState = 0;
            return;
          }
          return;
        }
        GpsNetInitiatedHandler.this.mCallEndElapsedRealtimeMillis = SystemClock.elapsedRealtime();
        GpsNetInitiatedHandler.this.mIsInEmergencyCall = false;
        GpsNetInitiatedHandler.this.mNumOfEmergencyCalls = 0;
        if (!GpsNetInitiatedHandler.this.mTelephonyManager.getEmergencyCallbackMode()
            && GpsNetInitiatedHandler.this.mEmergencyCallState != 3) {
          Log.m94d(GpsNetInitiatedHandler.TAG, "emergency mode is off.");
          GpsNetInitiatedHandler.this.setEmergencyState(false);
          GpsNetInitiatedHandler.this.mEmergencyCallState = 0;
        } else {
          GpsNetInitiatedHandler.this.mEmergencyCallState = 3;
        }
        GpsNetInitiatedHandler.this.mEmergencyCallCallback.onEmergencyCallEnd();
        return;
      }
      if (state == 2
          && !GpsNetInitiatedHandler.this.mIzatServiceEnabled
          && GpsNetInitiatedHandler.this.mEmergencyCallState == 1) {
        GpsNetInitiatedHandler.this.mIsInEmergencyCall = true;
        GpsNetInitiatedHandler.this.mEmergencyCallState = 2;
        if (!GpsNetInitiatedHandler.this.mTelephonyManager.getEmergencyCallbackMode()) {
          Log.m94d(GpsNetInitiatedHandler.TAG, "emergency mode is on.");
          GpsNetInitiatedHandler.this.setEmergencyState(true);
          GpsNetInitiatedHandler.this.mEmergencyCallCallback.onEmergencyCallStart(
              GpsNetInitiatedHandler.this.mEmergencySubId);
        }
      }
    }

    @Override // android.telephony.TelephonyCallback.PreciseCallStateListener
    public void onPreciseCallStateChanged(PreciseCallState callState) {
      if (!GpsNetInitiatedHandler.this.mIzatServiceEnabled) {
        int fgCallState = callState.getForegroundCallState();
        if (GpsNetInitiatedHandler.DEBUG) {
          Log.m94d(
              GpsNetInitiatedHandler.TAG,
              "onPreciseCallStateChanged. fgCallState = " + fgCallState);
        }
        if (GpsNetInitiatedHandler.this.isEmergencyCallActive(fgCallState)) {
          GpsNetInitiatedHandler.this.mIsInEmergencyCall = true;
          GpsNetInitiatedHandler.this.mEmergencyCallState = 2;
          if (GpsNetInitiatedHandler.DEBUG) {
            Log.m94d(GpsNetInitiatedHandler.TAG, "Emergency call is ACTIVE.");
          }
          if (!GpsNetInitiatedHandler.this.mTelephonyManager.getEmergencyCallbackMode()) {
            Log.m94d(GpsNetInitiatedHandler.TAG, "emergency mode is on.");
            GpsNetInitiatedHandler.this.setEmergencyState(true);
            GpsNetInitiatedHandler.this.mEmergencyCallCallback.onEmergencyCallStart(
                GpsNetInitiatedHandler.this.mEmergencySubId);
          }
        }
      }
    }
  }

  public GpsNetInitiatedHandler(
      Context context,
      INetInitiatedListener netInitiatedListener,
      EmergencyCallCallback emergencyCallCallback,
      boolean isSuplEsEnabled) {
    boolean equals = "qcom".equals(Build.HARDWARE);
    this.mIzatServiceEnabled = equals;
    this.mNumOfEmergencyCalls = 0;
    this.mEmergencyCallState = 0;
    this.mEmergencySubId = 0;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class:
          // com.android.internal.location.GpsNetInitiatedHandler.1
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
              return;
            }
            if (action.equals("android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED")) {
              if (intent.getBooleanExtra(TelephonyManager.EXTRA_PHONE_IN_ECM_STATE, false)) {
                if (!GpsNetInitiatedHandler.this.mIsInEmergencyCall
                    && GpsNetInitiatedHandler.this.mEmergencyCallState != 3) {
                  Log.m94d(GpsNetInitiatedHandler.TAG, "emergency mode is on because ECBM is on.");
                  GpsNetInitiatedHandler.this.setEmergencyState(true);
                }
                GpsNetInitiatedHandler.this.mEmergencyCallState = 3;
                return;
              }
              if (GpsNetInitiatedHandler.this.mEmergencyCallState == 3) {
                Log.m94d(GpsNetInitiatedHandler.TAG, "emergency mode is off because ECBM is off");
                GpsNetInitiatedHandler.this.setEmergencyState(false);
                GpsNetInitiatedHandler.this.mEmergencyCallState = 0;
                return;
              }
              return;
            }
            if (action.equals(GpsNetInitiatedHandler.INTENT_CALL_STATE)) {
              String phoneNumber = intent.getStringExtra("EXTRA_TEL_NUMBER");
              try {
                if (GpsNetInitiatedHandler.this.mTelephonyManager.isEmergencyNumber(phoneNumber)) {
                  int callState = intent.getIntExtra("EXTRA_CALL_EVENT", 1);
                  if (callState == 1) {
                    if (GpsNetInitiatedHandler.this.mNumOfEmergencyCalls > 0) {
                      GpsNetInitiatedHandler.this.mNumOfEmergencyCalls--;
                    }
                    if (GpsNetInitiatedHandler.this.mIsInEmergencyCall
                        && GpsNetInitiatedHandler.this.mNumOfEmergencyCalls == 0) {
                      if (GpsNetInitiatedHandler.DEBUG) {
                        Log.m94d(GpsNetInitiatedHandler.TAG, "emergency call is disconnected");
                      }
                      GpsNetInitiatedHandler.this.mCallEndElapsedRealtimeMillis =
                          SystemClock.elapsedRealtime();
                      GpsNetInitiatedHandler.this.mIsInEmergencyCall = false;
                      if (!GpsNetInitiatedHandler.this.mTelephonyManager.getEmergencyCallbackMode()
                          && GpsNetInitiatedHandler.this.mEmergencyCallState != 3) {
                        Log.m94d(GpsNetInitiatedHandler.TAG, "emergency mode is off.");
                        GpsNetInitiatedHandler.this.setEmergencyState(false);
                        GpsNetInitiatedHandler.this.mEmergencyCallState = 0;
                      } else {
                        GpsNetInitiatedHandler.this.mEmergencyCallState = 3;
                      }
                      GpsNetInitiatedHandler.this.mEmergencyCallCallback.onEmergencyCallEnd();
                      return;
                    }
                    if (GpsNetInitiatedHandler.this.mNumOfEmergencyCalls <= 0
                        || !GpsNetInitiatedHandler.DEBUG) {
                      return;
                    }
                    Log.m94d(
                        GpsNetInitiatedHandler.TAG,
                        "additional emergency call is disconnected, the number of remaining call is"
                            + " "
                            + GpsNetInitiatedHandler.this.mNumOfEmergencyCalls);
                    return;
                  }
                  if (callState == 2) {
                    GpsNetInitiatedHandler.this.mNumOfEmergencyCalls++;
                  }
                }
              } catch (IllegalStateException e) {
                Log.m97e(
                    "SystemEmergencyHelper", "isEmergencyNumber throws IllegalStateException:", e);
              }
            }
          }
        };
    this.mBroadcastReciever = broadcastReceiver;
    EmergencyCallListener emergencyCallListener = new EmergencyCallListener();
    this.mEmergencyCallListener = emergencyCallListener;
    this.mContext = context;
    if (netInitiatedListener == null) {
      throw new IllegalArgumentException("netInitiatedListener is null");
    }
    this.mNetInitiatedListener = netInitiatedListener;
    this.mEmergencyCallCallback = emergencyCallCallback;
    setSuplEsEnabled(isSuplEsEnabled);
    this.mLocationManager = (LocationManager) context.getSystemService("location");
    updateLocationMode();
    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
    this.mTelephonyManager = telephonyManager;
    telephonyManager.registerTelephonyCallback(context.getMainExecutor(), emergencyCallListener);
    BroadcastReceiver broadcastReceiver2 =
        new BroadcastReceiver() { // from class:
                                  // com.android.internal.location.GpsNetInitiatedHandler.2
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            String action = intent.getAction();
            if (action.equals(LocationManager.MODE_CHANGED_ACTION)) {
              GpsNetInitiatedHandler.this.updateLocationMode();
              if (GpsNetInitiatedHandler.DEBUG) {
                Log.m94d(
                    GpsNetInitiatedHandler.TAG,
                    "location enabled :" + GpsNetInitiatedHandler.this.getLocationEnabled());
              }
            }
          }
        };
    context.registerReceiver(
        broadcastReceiver2, new IntentFilter(LocationManager.MODE_CHANGED_ACTION));
    if (!equals) {
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction(INTENT_CALL_STATE);
      intentFilter.addAction("android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED");
      context.registerReceiver(broadcastReceiver, intentFilter);
    }
  }

  public void setSuplEsEnabled(boolean isEnabled) {
    this.mIsSuplEsEnabled = isEnabled;
  }

  public boolean getSuplEsEnabled() {
    return this.mIsSuplEsEnabled;
  }

  public void updateLocationMode() {
    this.mIsLocationEnabled = this.mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
  }

  public boolean getLocationEnabled() {
    return this.mIsLocationEnabled;
  }

  public boolean getInEmergency() {
    return getInEmergency(this.mEmergencyExtensionMillis);
  }

  public boolean getInEmergency(long emergencyExtensionMillis) {
    boolean isInEmergencyExtension =
        this.mCallEndElapsedRealtimeMillis > 0
            && SystemClock.elapsedRealtime() - this.mCallEndElapsedRealtimeMillis
                < emergencyExtensionMillis;
    boolean isInEmergencyCallback = this.mTelephonyManager.getEmergencyCallbackMode();
    boolean isInEmergencySmsMode = this.mTelephonyManager.isInEmergencySmsMode();
    return this.mIsInEmergencyCall
        || isInEmergencyCallback
        || isInEmergencyExtension
        || isInEmergencySmsMode;
  }

  public void setEmergencyExtensionSeconds(int emergencyExtensionSeconds) {
    this.mEmergencyExtensionMillis = TimeUnit.SECONDS.toMillis(emergencyExtensionSeconds);
  }

  public void handleNiNotification(GpsNiNotification notif) {
    if (DEBUG) {
      Log.m94d(
          TAG,
          "in handleNiNotification () : notificationId: "
              + notif.notificationId
              + " requestorId: "
              + notif.requestorId
              + " text: "
              + notif.text
              + " mIsSuplEsEnabled"
              + getSuplEsEnabled()
              + " mIsLocationEnabled"
              + getLocationEnabled());
    }
    if (getSuplEsEnabled()) {
      handleNiInEs(notif);
    } else {
      handleNi(notif);
    }
  }

  private void handleNi(GpsNiNotification notif) {
    if (DEBUG) {
      Log.m94d(
          TAG,
          "in handleNi () : needNotify: "
              + notif.needNotify
              + " needVerify: "
              + notif.needVerify
              + " privacyOverride: "
              + notif.privacyOverride
              + " mPopupImmediately: "
              + this.mPopupImmediately
              + " mInEmergency: "
              + getInEmergency());
    }
    if (!getLocationEnabled() && !getInEmergency()) {
      try {
        this.mNetInitiatedListener.sendNiResponse(notif.notificationId, 4);
      } catch (RemoteException e) {
        Log.m96e(TAG, "RemoteException in sendNiResponse");
      }
    }
    if (notif.needNotify) {
      if (notif.needVerify && this.mPopupImmediately) {
        openNiDialog(notif);
      } else {
        setNiNotification(notif);
      }
    }
    if (!notif.needVerify || notif.privacyOverride) {
      try {
        this.mNetInitiatedListener.sendNiResponse(notif.notificationId, 1);
      } catch (RemoteException e2) {
        Log.m96e(TAG, "RemoteException in sendNiResponse");
      }
    }
  }

  private void handleNiInEs(GpsNiNotification notif) {
    if (DEBUG) {
      Log.m94d(
          TAG,
          "in handleNiInEs () : niType: "
              + notif.niType
              + " notificationId: "
              + notif.notificationId);
    }
    boolean isNiTypeES = notif.niType == 4;
    if (isNiTypeES != getInEmergency()) {
      try {
        this.mNetInitiatedListener.sendNiResponse(notif.notificationId, 4);
        return;
      } catch (RemoteException e) {
        Log.m96e(TAG, "RemoteException in sendNiResponse");
        return;
      }
    }
    handleNi(notif);
  }

  private synchronized void setNiNotification(GpsNiNotification notif) {
    NotificationManager notificationManager =
        (NotificationManager) this.mContext.getSystemService("notification");
    if (notificationManager == null) {
      return;
    }
    String title = getNotifTitle(notif, this.mContext);
    String message = getNotifMessage(notif, this.mContext);
    if (DEBUG) {
      Log.m94d(
          TAG,
          "setNiNotification, notifyId: "
              + notif.notificationId
              + ", title: "
              + title
              + ", message: "
              + message);
    }
    if (this.mNiNotificationBuilder == null) {
      this.mNiNotificationBuilder =
          new Notification.Builder(this.mContext, SystemNotificationChannels.NETWORK_ALERTS)
              .setSmallIcon(C4337R.drawable.stat_sys_gps_on)
              .setWhen(0L)
              .setOngoing(true)
              .setAutoCancel(true)
              .setColor(this.mContext.getColor(17170460));
    }
    if (this.mPlaySounds) {
      this.mNiNotificationBuilder.setDefaults(1);
    } else {
      this.mNiNotificationBuilder.setDefaults(0);
    }
    this.mNiNotificationBuilder
        .setTicker(getNotifTicker(notif, this.mContext))
        .setContentTitle(title)
        .setContentText(message);
    notificationManager.notifyAsUser(
        null, notif.notificationId, this.mNiNotificationBuilder.build(), UserHandle.ALL);
  }

  private void openNiDialog(GpsNiNotification notif) {
    Intent intent = getDlgIntent(notif);
    if (DEBUG) {
      Log.m94d(
          TAG,
          "openNiDialog, notifyId: "
              + notif.notificationId
              + ", requestorId: "
              + notif.requestorId
              + ", text: "
              + notif.text);
    }
    this.mContext.startActivity(intent);
  }

  private Intent getDlgIntent(GpsNiNotification notif) {
    Intent intent = new Intent();
    String title = getDialogTitle(notif, this.mContext);
    String message = getDialogMessage(notif, this.mContext);
    intent.setFlags(268468224);
    intent.setClass(this.mContext, NetInitiatedActivity.class);
    intent.putExtra("notif_id", notif.notificationId);
    intent.putExtra("title", title);
    intent.putExtra("message", message);
    intent.putExtra(NI_INTENT_KEY_TIMEOUT, notif.timeout);
    intent.putExtra(NI_INTENT_KEY_DEFAULT_RESPONSE, notif.defaultResponse);
    if (DEBUG) {
      Log.m94d(
          TAG,
          "generateIntent, title: "
              + title
              + ", message: "
              + message
              + ", timeout: "
              + notif.timeout);
    }
    return intent;
  }

  /* JADX INFO: Access modifiers changed from: private */
  public boolean isEmergencyCallActive(int callState) {
    return this.mEmergencyCallState == 1 && (callState == 3 || callState == 4);
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void setEmergencyState(boolean eState) {
    Bundle extras = new Bundle();
    if (eState) {
      extras.putString("config_string", "EMERGENCY_STATE=TRUE");
    } else {
      extras.putString("config_string", "EMERGENCY_STATE=FALSE");
    }
    this.mLocationManager.sendExtraCommand(
        LocationManager.GPS_PROVIDER, "set_emergency_state", extras);
  }

  static byte[] stringToByteArray(String original, boolean isHex) {
    int length = original.length();
    if (isHex) {
      length /= 2;
    }
    byte[] output = new byte[length];
    if (isHex) {
      for (int i = 0; i < length; i++) {
        output[i] = (byte) Integer.parseInt(original.substring(i * 2, (i * 2) + 2), 16);
      }
    } else {
      for (int i2 = 0; i2 < length; i2++) {
        output[i2] = (byte) original.charAt(i2);
      }
    }
    return output;
  }

  static String decodeGSMPackedString(byte[] input) {
    int lengthBytes = input.length;
    int lengthSeptets = (lengthBytes * 8) / 7;
    if (lengthBytes % 7 == 0 && lengthBytes > 0 && (input[lengthBytes - 1] >> 1) == 0) {
      lengthSeptets--;
    }
    String decoded = GsmAlphabet.gsm7BitPackedToString(input, 0, lengthSeptets);
    if (decoded == null) {
      Log.m96e(TAG, "Decoding of GSM packed string failed");
      return "";
    }
    return decoded;
  }

  static String decodeUTF8String(byte[] input) {
    try {
      String decoded = new String(input, "UTF-8");
      return decoded;
    } catch (UnsupportedEncodingException e) {
      throw new AssertionError();
    }
  }

  static String decodeUCS2String(byte[] input) {
    try {
      String decoded = new String(input, "UTF-16");
      return decoded;
    } catch (UnsupportedEncodingException e) {
      throw new AssertionError();
    }
  }

  private static String decodeString(String original, boolean isHex, int coding) {
    if (coding == 0 || coding == -1) {
      return original;
    }
    byte[] input = stringToByteArray(original, isHex);
    switch (coding) {
      case 1:
        return decodeGSMPackedString(input);
      case 2:
        return decodeUTF8String(input);
      case 3:
        return decodeUCS2String(input);
      default:
        Log.m96e(TAG, "Unknown encoding " + coding + " for NI text " + original);
        return original;
    }
  }

  private static String getNotifTicker(GpsNiNotification notif, Context context) {
    String ticker =
        String.format(
            context.getString(C4337R.string.gpsNotifTicker),
            decodeString(notif.requestorId, mIsHexInput, notif.requestorIdEncoding),
            decodeString(notif.text, mIsHexInput, notif.textEncoding));
    return ticker;
  }

  private static String getNotifTitle(GpsNiNotification notif, Context context) {
    String title = String.format(context.getString(C4337R.string.gpsNotifTitle), new Object[0]);
    return title;
  }

  private static String getNotifMessage(GpsNiNotification notif, Context context) {
    String message =
        String.format(
            context.getString(C4337R.string.gpsNotifMessage),
            decodeString(notif.requestorId, mIsHexInput, notif.requestorIdEncoding),
            decodeString(notif.text, mIsHexInput, notif.textEncoding));
    return message;
  }

  public static String getDialogTitle(GpsNiNotification notif, Context context) {
    return getNotifTitle(notif, context);
  }

  private static String getDialogMessage(GpsNiNotification notif, Context context) {
    return getNotifMessage(notif, context);
  }
}
