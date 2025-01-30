package com.samsung.android.telecom;

import android.content.Context;
import android.net.Uri;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import android.p009os.UserHandle;
import android.telecom.PhoneAccount;
import android.util.Log;
import com.samsung.android.internal.telecom.ISamsungTelecomService;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* loaded from: classes5.dex */
public class SemTelecomManager {
  public static final String CALLER = "caller";
  public static final int CALL_FILTER_ALL = 3;
  public static final int CALL_FILTER_MANAGED = 2;
  public static final int CALL_FILTER_SELF_MANAGED = 1;
  public static final String CAUSE = "cause";
  public static final String EVENT_ID = "event_id";
  public static final int KEYCODE_INVALID = -1;
  public static final String KEY_CODE = "key_code";
  public static final String REASON = "reason";
  private static final String TAG = "SemTelecomManager";
  private static ISamsungTelecomService sSamsungTelecomService;
  private final Context mContext;
  private final ISamsungTelecomService mSamsungTelecomServiceOverride;
  private static final Object CACHE_LOCK = new Object();
  private static final DeathRecipient SERVICE_DEATH = new DeathRecipient();

  @Retention(RetentionPolicy.SOURCE)
  public @interface CallFilter {}

  public static final class Event {
    public static final String SEM_EVENT_ADD_CALL = "com.samsung.telecom.CALL_EVENT_ADD_CALL";
    public static final String SEM_EVENT_ANSWER = "com.samsung.telecom.Call.EVENT_ANSWER";
    public static final String SEM_EVENT_ANSWER_RINGING_CALL =
        "com.samsung.telecom.Call.EVENT_ANSWER_RINGING_CALL";
    public static final String SEM_EVENT_CALL_CONNECT_TIME =
        "com.samsung.telecom.CALL_EVENT_CALL_CONNECT_TIME";
    public static final String SEM_EVENT_CALL_CREATE_TIME =
        "com.samsung.telecom.CALL_EVENT_CALL_CREATE_TIME";
    public static final String SEM_EVENT_CHOSEN_PHONE_ID =
        "com.samsung.telecom.CALL_EVENT_CHOSEN_PHONE_ID";
    public static final String SEM_EVENT_END_CALL = "com.samsung.telecom.Call.EVENT_END_CALL";
    public static final String SEM_EVENT_END_CALL_FROM_TELECOM_MANAGER =
        "com.samsung.telecom.Call.EVENT_END_CALL_FROM_TELECOM_MANAGER";
    public static final String SEM_EVENT_LINE_CTRL = "com.samsung.telecom.Call.EVENT_LINE_CTRL";
    public static final String SEM_EVENT_OPEN_PLATFORM_CONTROL_UX =
        "com.samsung.telecom.Call.EVENT_OPEN_PLATFORM_CONTROL_UX";
    public static final String SEM_EVENT_PULL_CALL = "com.samsung.telecom.CALL_EVENT_PULL_CALL";
    public static final String SEM_EVENT_READ_CALLER_ID =
        "com.samsung.telecom.Call.EVENT_READ_CALLER_ID";
    public static final String SEM_EVENT_RESTART_RINGING_OR_CALL_WAITING =
        "com.samsung.telecom.Call.EVENT_RESTART_RINGING_OR_CALL_WAITING";
    public static final String SEM_EVENT_SET_ALERTING =
        "com.samsung.telecom.Call.EVENT_SET_ALERTING";
    public static final String SEM_EVENT_SET_ALERTING_TO_BLUETOOTH =
        "com.samsung.telecom.Call.EVENT_SET_ALERTING_TO_BLUETOOTH";
    public static final String SEM_EVENT_SET_ECHOLOCATE_CALL_STATE =
        "com.samsung.telecom.Call.EVENT_SET_ECHOLOCATE_CALL_STATE";
    public static final String SEM_EVENT_SET_ECHOLOCATE_UI_CALL_STATE =
        "com.samsung.telecom.Call.EVENT_SET_ECHOLOCATE_UI_CALL_STATE";
    public static final String SEM_EVENT_START_RINGTONE_IF_POSSIBLE =
        "com.samsung.telecom.Call.EVENT_START_RINGTONE_IF_POSSIBLE";
    public static final String SEM_EVENT_UPDATE_CALL =
        "com.samsung.server.telecom.event.UPDATE_CALL";
  }

  public static final class Extra {
    public static final String SEM_EXTRA_AUTOMATIC_ANSWERING_MACHINE_MODE =
        "com.samsung.telecom.extra.AUTOMATIC_ANSWERING_MACHINE_MODE";
    public static final String SEM_EXTRA_CALL_BACKGROUND_SOUND =
        "com.samsung.telecom.extra.CALL_BACKGROUND_SOUND";
    public static final String SEM_EXTRA_CALL_BACK_PHONE_ACCOUNT_HANDLE =
        "com.samsung.telecom.extra.CALL_BACK_PHONE_ACCOUNT_HANDLE";
    public static final String SEM_EXTRA_CALL_COMPONENT_NAME =
        "com.samsung.telecom.extra.CALL_COMPONENT_NAME";
    public static final String SEM_EXTRA_CALL_CONNECT_ELAPSED_TIME_MILLIS =
        "com.samsung.telecom.extra.CALL_CONNECT_ELAPSED_TIME_MILLIS";
    public static final String SEM_EXTRA_CALL_CONNECT_TIME_MILLIS =
        "com.samsung.telecom.extra.CALL_CONNECT_TIME_MILLIS";
    public static final String SEM_EXTRA_CALL_CREATE_TIME_MILLIS =
        "com.samsung.telecom.extra.CALL_CREATE_TIME_MILLIS";
    public static final String SEM_EXTRA_CALL_ID = "com.samsung.server.telecom.extra.CALL_ID";
    public static final String SEM_EXTRA_CALL_LOG_INFO = "com.samsung.telecom.extra.CALL_LOG_INFO";
    public static final String SEM_EXTRA_CALL_PRESENTATION =
        "com.samsung.telecom.extra.CALL_PRESENTATION";
    public static final String SEM_EXTRA_CALL_REMINDER = "com.samsung.telecom.extra.CALL_REMINDER";
    public static final String SEM_EXTRA_CALL_STATE = "com.samsung.telecom.extra.CALL_STATE";
    public static final String SEM_EXTRA_CAPABILITY_RESPOND_VIA_TEXT =
        "com.samsung.telecom.extra.CAPABILITY_RESPOND_VIA_TEXT";
    public static final String SEM_EXTRA_CHOSEN_PHONE_ID =
        "com.samsung.telecom.extra.CHOSEN_PHONE_ID";
    public static final String SEM_EXTRA_CHOSEN_SUBSCRIPTION_ID =
        "com.samsung.telecom.extra.CHOSEN_SUBSCRIPTION_ID";
    public static final String SEM_EXTRA_CMC_PHONE_ACCOUNT =
        "com.samsung.telecom.extra.CMC_PHONE_ACCOUNT";
    public static final String SEM_EXTRA_CONNECTION_SERVICE_RINGING_RINGTONE =
        "com.samsung.telecom.extra.CONNECTION_SERVICE_RINGING_RINGTONE";
    public static final String SEM_EXTRA_CURRENT_TTY_MODE =
        "com.samsung.telecom.extra.CURRENT_TTY_MODE";
    public static final String SEM_EXTRA_ECHOLOCATE_ADDRESS =
        "com.samsung.telecom.extra.ECHOLOCATE_ADDRESS";
    public static final String SEM_EXTRA_ECHOLOCATE_DISCONNECT_CAUSE =
        "com.samsung.telecom.extra.ECHOLOCATE_DISCONNECT_CAUSE";
    public static final String SEM_EXTRA_ECHOLOCATE_NEW_STATE =
        "com.samsung.telecom.extra.ECHOLOCATE_NEW_STATE";
    public static final String SEM_EXTRA_ECHOLOCATE_TYPE =
        "com.samsung.telecom.extra.ECHOLOCATE_TYPE";
    public static final String SEM_EXTRA_ECHOLOCATE_UI_NUMBER =
        "com.samsung.telecom.extra.ECHOLOCATE_UI_NUMBER";
    public static final String SEM_EXTRA_ECHOLOCATE_UI_STATE =
        "com.samsung.telecom.extra.ECHOLOCATE_UI_STATE";
    public static final String SEM_EXTRA_FORCE_START_CALL_WITH_RTT =
        "com.samsung.telecom.extra.FORCE_START_CALL_WITH_RTT";
    public static final String SEM_EXTRA_IS_EXTERNAL_CALL =
        "com.samsung.telecom.extra.IS_EXTERNAL_CALL";
    public static final String SEM_EXTRA_IS_LINE_CTRL = "com.samsung.telecom.extra.IS_LINE_CTRL";
    public static final String SEM_EXTRA_IS_RTT_CALL = "com.samsung.telecom.extra.IS_RTT_CALL";
    public static final String SEM_EXTRA_IS_SILENCE = "com.samsung.telecom.extra.IS_SILENCE";
    public static final String SEM_EXTRA_MISSED_CALLS_NOTIFICATION =
        "com.samsung.telecom.extra.MISSED_CALLS_NOTIFICATION";
    public static final String SEM_EXTRA_NEW_IS_PLUGGED_IN =
        "com.samsung.telecom.extra.NEW_IS_PLUGGED_IN";
    public static final String SEM_EXTRA_NEW_OUTGOING_CALL =
        "com.samsung.telecom.extra.NEW_OUTGOING_CALL";
    public static final String SEM_EXTRA_NO_CHECK_IF_VIDEO_CAPABLE =
        "com.samsung.telecom.extra.NO_CHECK_IF_VIDEO_CAPABLE";
    public static final String SEM_EXTRA_NUMBER = "com.samsung.telecom.extra.NUMBER";
    public static final String SEM_EXTRA_OPEN_PLATFORM_GROUP_CALL_ID =
        "com.samsung.telecom.extra.OPEN_PLATFORM_GROUP_CALL_ID";
    public static final String SEM_EXTRA_OPEN_PLATFORM_GROUP_CALL_MAX_PARTICIPANTS =
        "com.samsung.telecom.extra.OPEN_PLATFORM_GROUP_CALL_MAX_PARTICIPANTS";
    public static final String SEM_EXTRA_OPEN_PLATFORM_GROUP_CALL_NAME =
        "com.samsung.telecom.extra.OPEN_PLATFORM_GROUP_CALL_NAME";
    public static final String SEM_EXTRA_OPEN_PLATFORM_GROUP_CALL_PARTICIPANTS =
        "com.samsung.telecom.extra.OPEN_PLATFORM_GROUP_CALL_PARTICIPANTS";
    public static final String SEM_EXTRA_OPEN_PLATFORM_GROUP_CALL_UNIQUE_ID =
        "com.samsung.telecom.extra.OPEN_PLATFORM_GROUP_CALL_UNIQUE_ID";
    public static final String SEM_EXTRA_OPEN_PLATFORM_PLAY_RINGTONE =
        "com.samsung.telecom.extra.OPEN_PLATFORM_PLAY_RINGTONE";
    public static final String SEM_EXTRA_OPEN_PLATFORM_SHOW_CALL_UI =
        "com.samsung.telecom.extra.OPEN_PLATFORM_SHOW_CALL_UI";
    public static final String SEM_EXTRA_OPEN_PLATFORM_START_GROUP_CALL_WITH_GROUP_ID =
        "com.samsung.telecom.extra.OPEN_PLATFORM_START_GROUP_CALL_WITH_GROUP_ID";
    public static final String SEM_EXTRA_OPEN_PLATFORM_START_GROUP_CALL_WITH_GROUP_NAME =
        "com.samsung.telecom.extra.OPEN_PLATFORM_START_GROUP_CALL_WITH_GROUP_NAME";
    public static final String SEM_EXTRA_OPEN_PLATFORM_START_GROUP_CALL_WITH_PARTICIPANTS =
        "com.samsung.telecom.extra.OPEN_PLATFORM_START_GROUP_CALL_WITH_PARTICIPANTS";
    public static final String SEM_EXTRA_OPEN_PLATFORM_SUPPORT =
        "com.samsung.telecom.extra.OPEN_PLATFORM_SUPPORT";
    public static final String SEM_EXTRA_OPEN_PLATFORM_SUPPORT_GROUP_CALL =
        "com.samsung.telecom.extra.OPEN_PLATFORM_SUPPORT_GROUP_CALL";
    public static final String SEM_EXTRA_ORIGINAL_CALL_INTENT =
        "com.samsung.telecom.extra.ORIGINAL_CALL_INTENT";
    public static final String SEM_EXTRA_PHONE_ACCOUNT = "com.samsung.telecom.extra.PHONE_ACCOUNT";
    public static final String SEM_EXTRA_PHONE_ID = "com.samsung.telecom.extra.PHONE_ID";
    public static final String SEM_EXTRA_PREFIX_DUAL_NUMBER =
        "com.samsung.telecom.extra.PREFIX_DUAL_NUMBER";
    public static final String SEM_EXTRA_PREFIX_TWO_PHONE_NUMBER =
        "com.samsung.telecom.extra.PREFIX_TWO_PHONE_NUMBER";
    public static final String SEM_EXTRA_RAD_DIAL_TO_HOME =
        "com.samsung.telecom.extra.RAD_DIAL_TO_HOME";
    public static final String SEM_EXTRA_RAD_DIAL_TO_LOCAL =
        "com.samsung.telecom.extra.RAD_DIAL_TO_LOCAL";
    public static final String SEM_EXTRA_RAD_ORIGINAL_NUMBER =
        "com.samsung.telecom.extra.RAD_ORIGINAL_NUMBER";
    public static final String SEM_EXTRA_READ_CALLER_ID_COUNT =
        "com.samsung.telecom.extra.READ_CALLER_ID_COUNT";
    public static final String SEM_EXTRA_READ_CALLER_ID_TEXT =
        "com.samsung.telecom.extra.READ_CALLER_ID_TEXT";
    public static final String SEM_EXTRA_SHOULD_LOG_DISCONNECTED_CALL =
        "com.samsung.telecom.extra.SHOULD_LOG_DISCONNECTED_CALL";
    public static final String SEM_EXTRA_SMART_CALL_PROFILE_TAG =
        "com.samsung.telecom.extra.SMART_CALL_PROFILE_TAG";
    public static final String SEM_EXTRA_START_CALL_WITH_INCOMING_CALL_ORIGIN =
        "com.samsung.telecom.extra.START_CALL_WITH_INCOMING_CALL_ORIGIN";
    public static final String SEM_EXTRA_START_CALL_WITH_OUTGOING_CALL_ORIGIN =
        "com.samsung.telecom.extra.START_CALL_WITH_OUTGOING_CALL_ORIGIN";
    public static final String SEM_EXTRA_START_CALL_WITH_TWO_PHONE_OPTION =
        "com.samsung.telecom.extra.START_CALL_WITH_TWO_PHONE_OPTION";
    public static final String SEM_EXTRA_START_RINGTONE_REASON =
        "com.samsung.telecom.extra.START_RINGTONE_REASON";
    public static final String SEM_EXTRA_START_VIDEO_CALL_WITHOUT_SPEAKER =
        "com.samsung.telecom.extra.START_VIDEO_CALL_WITHOUT_SPEAKER";
    public static final String SEM_EXTRA_SUBSCRIPTION_ID =
        "com.samsung.telecom.extra.SUBSCRIPTION_ID";
    public static final String SEM_EXTRA_TOUCH_STATE = "com.samsung.telecom.extra.TOUCH_STATE";
    public static final String SEM_EXTRA_TTY_PREFERRED = "com.samsung.telecom.extra.TTY_PREFERRED";
    public static final String SEM_EXTRA_TWO_PHONE_CALL =
        "com.samsung.telecom.extra.TWO_PHONE_CALL";
    public static final String SEM_EXTRA_TWO_PHONE_PREFIX_ADDED =
        "com.samsung.telecom.extra.TWO_PHONE_PREFIX_ADDED";
    public static final String SEM_EXTRA_VIDEO_STATE = "com.samsung.telecom.extra.VIDEO_STATE";
  }

  public SemTelecomManager(Context context) {
    this(context, null);
  }

  public SemTelecomManager(Context context, ISamsungTelecomService samsungTelecomServiceImpl) {
    Context appContext = context.getApplicationContext();
    if (appContext != null
        && Objects.equals(context.getAttributionTag(), appContext.getAttributionTag())) {
      this.mContext = appContext;
    } else {
      this.mContext = context;
    }
    this.mSamsungTelecomServiceOverride = samsungTelecomServiceImpl;
  }

  @Deprecated(forRemoval = true, since = "14.0")
  public List<PhoneAccount> getAllowedSelfManagedPhoneAccounts() {
    return Collections.EMPTY_LIST;
  }

  @Deprecated
  public List<Bundle> getAllowedPhoneAccountInfo() {
    return Collections.EMPTY_LIST;
  }

  @Deprecated
  public List<Bundle> getAllowedPhoneAccountInfo(
      boolean includeRegisteredAccounts, boolean includeSimSubscriptionAccounts) {
    return Collections.EMPTY_LIST;
  }

  public List<SemPhoneAccount> getAllowedPhoneAccounts(
      boolean includeRegisteredAccounts, boolean includeSimSubscriptionAccounts) {
    ISamsungTelecomService service = getSamsungTelecomService();
    if (service != null) {
      try {
        return service
            .getAllowedPhoneAccounts(
                includeRegisteredAccounts,
                includeSimSubscriptionAccounts,
                this.mContext.getOpPackageName(),
                this.mContext.getAttributionTag())
            .getList();
      } catch (RemoteException e) {
        Log.m97e(TAG, "Error calling ISamsungTelecomService#getAllowedPhoneAccounts", e);
      }
    }
    return Collections.EMPTY_LIST;
  }

  @Deprecated
  public void addConferenceParticipants(List<Uri> participants, Bundle extras) {}

  public void silenceRinger() {
    ISamsungTelecomService service = getSamsungTelecomService();
    if (service != null) {
      try {
        service.silenceRinger(
            -1, new Bundle(), this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
      } catch (RemoteException e) {
        Log.m97e(TAG, "Error calling ISamsungTelecomService#silenceRinger", e);
      }
    }
  }

  public void silenceRinger(int keyCode) {
    ISamsungTelecomService service = getSamsungTelecomService();
    if (service != null) {
      try {
        service.silenceRinger(
            keyCode,
            new Bundle(),
            this.mContext.getOpPackageName(),
            this.mContext.getAttributionTag());
      } catch (RemoteException e) {
        Log.m97e(
            TAG, "Error calling ISamsungTelecomService#silenceRinger - keyCode : " + keyCode, e);
      }
    }
  }

  public void silenceRinger(Bundle reason) {
    Bundle bundle;
    ISamsungTelecomService service = getSamsungTelecomService();
    if (service != null) {
      if (reason != null) {
        bundle = reason;
      } else {
        try {
          bundle = new Bundle();
        } catch (RemoteException e) {
          Log.m97e(
              TAG, "Error calling ISamsungTelecomService#silenceRinger - reason : " + reason, e);
          return;
        }
      }
      service.silenceRinger(
          -1, bundle, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
    }
  }

  public void endCall() {
    ISamsungTelecomService service = getSamsungTelecomService();
    if (service != null) {
      try {
        service.endCall(
            -1, new Bundle(), this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
      } catch (RemoteException e) {
        Log.m97e(TAG, "Error calling ISamsungTelecomService#endCall", e);
      }
    }
  }

  public void endCall(int keyCode) {
    ISamsungTelecomService service = getSamsungTelecomService();
    if (service != null) {
      try {
        service.endCall(
            keyCode,
            new Bundle(),
            this.mContext.getOpPackageName(),
            this.mContext.getAttributionTag());
      } catch (RemoteException e) {
        Log.m97e(TAG, "Error calling ISamsungTelecomService#endCall - keyCode : " + keyCode, e);
      }
    }
  }

  public void endCall(Bundle reason) {
    Bundle bundle;
    ISamsungTelecomService service = getSamsungTelecomService();
    if (service != null) {
      if (reason != null) {
        bundle = reason;
      } else {
        try {
          bundle = new Bundle();
        } catch (RemoteException e) {
          Log.m97e(TAG, "Error calling ISamsungTelecomService#endCall - reason : " + reason, e);
          return;
        }
      }
      service.endCall(
          -1, bundle, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
    }
  }

  public void acceptRingingCall() {
    ISamsungTelecomService service = getSamsungTelecomService();
    if (service != null) {
      try {
        service.acceptRingingCall(
            -1, new Bundle(), this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
      } catch (RemoteException e) {
        Log.m97e(TAG, "Error calling ISamsungTelecomService#acceptRingingCall", e);
      }
    }
  }

  public void acceptRingingCall(int keyCode) {
    ISamsungTelecomService service = getSamsungTelecomService();
    if (service != null) {
      try {
        service.acceptRingingCall(
            keyCode,
            new Bundle(),
            this.mContext.getOpPackageName(),
            this.mContext.getAttributionTag());
      } catch (RemoteException e) {
        Log.m97e(
            TAG,
            "Error calling ISamsungTelecomService#acceptRingingCall - keyCode : " + keyCode,
            e);
      }
    }
  }

  public void acceptRingingCall(Bundle reason) {
    Bundle bundle;
    ISamsungTelecomService service = getSamsungTelecomService();
    if (service != null) {
      if (reason != null) {
        bundle = reason;
      } else {
        try {
          bundle = new Bundle();
        } catch (RemoteException e) {
          Log.m97e(
              TAG,
              "Error calling ISamsungTelecomService#acceptRingingCall - reason : " + reason,
              e);
          return;
        }
      }
      service.acceptRingingCall(
          -1, bundle, this.mContext.getOpPackageName(), this.mContext.getAttributionTag());
    }
  }

  public void acceptRingingCallWithVideoState(int videoState) {
    ISamsungTelecomService service = getSamsungTelecomService();
    if (service != null) {
      try {
        service.acceptRingingCallWithVideoState(
            videoState,
            -1,
            new Bundle(),
            this.mContext.getOpPackageName(),
            this.mContext.getAttributionTag());
      } catch (RemoteException e) {
        Log.m97e(TAG, "Error calling ISamsungTelecomService#acceptRingingCallWithVideoState", e);
      }
    }
  }

  public void acceptRingingCallWithVideoState(int videoState, int keyCode) {
    ISamsungTelecomService service = getSamsungTelecomService();
    if (service != null) {
      try {
        service.acceptRingingCallWithVideoState(
            videoState,
            keyCode,
            new Bundle(),
            this.mContext.getOpPackageName(),
            this.mContext.getAttributionTag());
      } catch (RemoteException e) {
        Log.m97e(
            TAG,
            "Error calling ISamsungTelecomService#acceptRingingCallWithVideoState - keyCode : "
                + keyCode,
            e);
      }
    }
  }

  public void acceptRingingCallWithVideoState(int videoState, Bundle reason) {
    Bundle bundle;
    ISamsungTelecomService service = getSamsungTelecomService();
    if (service != null) {
      if (reason != null) {
        bundle = reason;
      } else {
        try {
          bundle = new Bundle();
        } catch (RemoteException e) {
          Log.m97e(
              TAG,
              "Error calling ISamsungTelecomService#acceptRingingCallWithVideoState - reason : "
                  + reason,
              e);
          return;
        }
      }
      service.acceptRingingCallWithVideoState(
          videoState,
          -1,
          bundle,
          this.mContext.getOpPackageName(),
          this.mContext.getAttributionTag());
    }
  }

  public boolean isInCall(int callFilter, boolean includeExternalCall) {
    ISamsungTelecomService service = getSamsungTelecomService();
    if (service != null) {
      try {
        return service.isInCall(
            callFilter,
            includeExternalCall,
            this.mContext.getOpPackageName(),
            this.mContext.getAttributionTag());
      } catch (RemoteException e) {
        Log.m97e(TAG, "Error calling ISamsungTelecomService#isInCall", e);
        return false;
      }
    }
    return false;
  }

  public void showInCallScreen(boolean showDialpad, UserHandle callingUser) {
    ISamsungTelecomService service = getSamsungTelecomService();
    if (service != null) {
      try {
        service.showInCallScreen(
            showDialpad,
            callingUser,
            this.mContext.getOpPackageName(),
            this.mContext.getAttributionTag());
      } catch (RemoteException e) {
        Log.m97e(TAG, "Error calling ISamsungTelecomService#showCallScreen", e);
      }
    }
  }

  private ISamsungTelecomService getSamsungTelecomService() {
    ISamsungTelecomService iSamsungTelecomService = this.mSamsungTelecomServiceOverride;
    if (iSamsungTelecomService != null) {
      return iSamsungTelecomService;
    }
    if (sSamsungTelecomService == null) {
      ISamsungTelecomService temp =
          ISamsungTelecomService.Stub.asInterface(
              ServiceManager.getService(Context.SEM_TELECOM_SERVICE));
      synchronized (CACHE_LOCK) {
        if (sSamsungTelecomService == null && temp != null) {
          try {
            sSamsungTelecomService = temp;
            temp.asBinder().linkToDeath(SERVICE_DEATH, 0);
          } catch (Exception e) {
            sSamsungTelecomService = null;
          }
        }
      }
    }
    return sSamsungTelecomService;
  }

  private static class DeathRecipient implements IBinder.DeathRecipient {
    private DeathRecipient() {}

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
      SemTelecomManager.resetServiceCache();
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public static void resetServiceCache() {
    synchronized (CACHE_LOCK) {
      ISamsungTelecomService iSamsungTelecomService = sSamsungTelecomService;
      if (iSamsungTelecomService != null) {
        iSamsungTelecomService.asBinder().unlinkToDeath(SERVICE_DEATH, 0);
        sSamsungTelecomService = null;
      }
    }
  }

  public static boolean hasSamsungTelecomSystemFeature() {
    boolean hasSamsungTelecomSystemFeature = !isSupportHeadlessDevice();
    Log.m98i(TAG, "hasSamsungTelecomSystemFeature : " + hasSamsungTelecomSystemFeature);
    return hasSamsungTelecomSystemFeature;
  }

  private static boolean isSupportHeadlessDevice() {
    return false;
  }
}
