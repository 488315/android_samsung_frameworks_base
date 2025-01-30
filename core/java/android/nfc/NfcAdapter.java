package android.nfc;

import android.annotation.SystemApi;
import android.app.Activity;
import android.app.ActivityThread;
import android.app.OnActivityPausedListener;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.IntentFilter;
import android.content.p002pm.PackageManager;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.p009os.Bundle;
import android.p009os.Handler;
import android.p009os.IBinder;
import android.p009os.Looper;
import android.p009os.Process;
import android.p009os.RemoteException;
import android.p009os.UserHandle;
import android.sec.enterprise.auditlog.AuditEvents;
import android.util.Log;
import android.widget.Toast;
import com.android.internal.C4337R;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public final class NfcAdapter {
  public static final String ACTION_ADAPTER_RW_P2P_STATE_CHANGED =
      "com.felicanetworks.nfc.action.ADAPTER_RW_P2P_STATE_CHANGED";
  public static final String ACTION_ADAPTER_STATE_CHANGED =
      "android.nfc.action.ADAPTER_STATE_CHANGED";
  public static final String ACTION_ADAPTER_STATE_CHANGE_READER =
      "android.nfc.action.ADAPTER_STATE_CHANGE_READER";
  public static final String ACTION_HANDOVER_TRANSFER_DONE =
      "android.nfc.action.HANDOVER_TRANSFER_DONE";
  public static final String ACTION_HANDOVER_TRANSFER_STARTED =
      "android.nfc.action.HANDOVER_TRANSFER_STARTED";
  public static final String ACTION_NDEF_DISCOVERED = "android.nfc.action.NDEF_DISCOVERED";
  public static final String ACTION_PREFERRED_PAYMENT_CHANGED =
      "android.nfc.action.PREFERRED_PAYMENT_CHANGED";
  public static final String ACTION_REQUIRE_UNLOCK_FOR_NFC =
      "android.nfc.action.REQUIRE_UNLOCK_FOR_NFC";
  public static final String ACTION_TAG_DISCOVERED = "android.nfc.action.TAG_DISCOVERED";
  public static final String ACTION_TAG_LEFT_FIELD = "android.nfc.action.TAG_LOST";
  public static final String ACTION_TECH_DISCOVERED = "android.nfc.action.TECH_DISCOVERED";
  public static final String ACTION_TRANSACTION_DETECTED =
      "android.nfc.action.TRANSACTION_DETECTED";
  public static final String EXTRA_ADAPTER_RW_P2P_STATE =
      "com.felicanetworks.nfc.extra.ADAPTER_RW_P2P_STATE";
  public static final String EXTRA_ADAPTER_STATE = "android.nfc.extra.ADAPTER_STATE";
  public static final String EXTRA_AID = "android.nfc.extra.AID";
  public static final String EXTRA_DATA = "android.nfc.extra.DATA";
  public static final String EXTRA_HANDOVER_TRANSFER_STATUS =
      "android.nfc.extra.HANDOVER_TRANSFER_STATUS";
  public static final String EXTRA_HANDOVER_TRANSFER_URI =
      "android.nfc.extra.HANDOVER_TRANSFER_URI";
  public static final String EXTRA_ID = "android.nfc.extra.ID";
  public static final String EXTRA_NDEF_MESSAGES = "android.nfc.extra.NDEF_MESSAGES";
  public static final String EXTRA_PREFERRED_PAYMENT_CHANGED_REASON =
      "android.nfc.extra.PREFERRED_PAYMENT_CHANGED_REASON";
  public static final String EXTRA_READER_PRESENCE_CHECK_DELAY = "presence";
  public static final String EXTRA_SECURE_ELEMENT_NAME = "android.nfc.extra.SECURE_ELEMENT_NAME";
  public static final String EXTRA_TAG = "android.nfc.extra.TAG";

  @SystemApi public static final int FLAG_NDEF_PUSH_NO_CONFIRM = 1;
  public static final int FLAG_READER_NFC_A = 1;
  public static final int FLAG_READER_NFC_B = 2;
  public static final int FLAG_READER_NFC_BARCODE = 16;
  public static final int FLAG_READER_NFC_F = 4;
  public static final int FLAG_READER_NFC_V = 8;
  public static final int FLAG_READER_NO_PLATFORM_SOUNDS = 256;
  public static final int FLAG_READER_SKIP_NDEF_CHECK = 128;
  public static final int HANDOVER_TRANSFER_STATUS_FAILURE = 1;
  public static final int HANDOVER_TRANSFER_STATUS_SUCCESS = 0;
  private static final int MDM_REJECT_TYPE_DISABLE = 1;
  private static final int MDM_REJECT_TYPE_ENABLE = 0;
  public static final int PREFERRED_PAYMENT_CHANGED = 2;
  public static final int PREFERRED_PAYMENT_LOADED = 1;
  public static final int PREFERRED_PAYMENT_UPDATED = 3;
  public static final int SEM_FLAG_DISCOVERY_TECHNOLOGY_DISABLE = 0;
  public static final int SEM_FLAG_DISCOVERY_TECHNOLOGY_KEEP_CURRENT = -1;
  public static final int SEM_STATE_CARD_MODE_ON = 5;
  public static final int STATE_OFF = 1;
  public static final int STATE_ON = 3;
  public static final int STATE_RW_P2P_OFF = 11;
  public static final int STATE_RW_P2P_ON = 13;
  public static final int STATE_RW_P2P_TURNING_OFF = 14;
  public static final int STATE_RW_P2P_TURNING_ON = 12;
  public static final int STATE_TURNING_OFF = 4;
  public static final int STATE_TURNING_ON = 2;
  static final String TAG = "NFC";

  @SystemApi public static final int TAG_INTENT_APP_PREF_RESULT_PACKAGE_NOT_FOUND = -1;

  @SystemApi public static final int TAG_INTENT_APP_PREF_RESULT_SUCCESS = 0;

  @SystemApi public static final int TAG_INTENT_APP_PREF_RESULT_UNAVAILABLE = -2;
  public static final int TECHNOLOGY_MASK_A = 1;
  public static final int TECHNOLOGY_MASK_B = 2;
  public static final int TECHNOLOGY_MASK_F = 4;
  static INfcCardEmulation sCardEmulationService;
  static boolean sHasCeFeature;
  static boolean sHasNfcFeature;
  static boolean sIsInitialized = false;
  static HashMap<Context, NfcAdapter> sNfcAdapters = new HashMap<>();
  static INfcFCardEmulation sNfcFCardEmulationService;
  static NfcAdapter sNullContextNfcAdapter;
  static INfcAdapter sService;
  static NfcServiceManager.ServiceRegisterer sServiceRegisterer;
  static INfcTag sTagService;
  final Context mContext;
  private Cursor mDevSettingCr;
  private Cursor mRestrictionCr;
  private Uri mUri;
  OnActivityPausedListener mForegroundDispatchListener =
      new OnActivityPausedListener() { // from class: android.nfc.NfcAdapter.1
        @Override // android.app.OnActivityPausedListener
        public void onPaused(Activity activity) {
          NfcAdapter.this.disableForegroundDispatchInternal(activity, true);
        }
      };
  final NfcActivityManager mNfcActivityManager = new NfcActivityManager(this);
  final HashMap<NfcUnlockHandler, INfcUnlockHandler> mNfcUnlockHandlers = new HashMap<>();
  ITagRemovedCallback mTagRemovedListener = null;
  final Object mLock = new Object();
  private final NfcControllerAlwaysOnListener mControllerAlwaysOnListener =
      new NfcControllerAlwaysOnListener(getService());

  @SystemApi
  public interface ControllerAlwaysOnListener {
    void onControllerAlwaysOnChanged(boolean z);
  }

  @Deprecated
  public interface CreateBeamUrisCallback {
    Uri[] createBeamUris(NfcEvent nfcEvent);
  }

  @Deprecated
  public interface CreateNdefMessageCallback {
    NdefMessage createNdefMessage(NfcEvent nfcEvent);
  }

  @SystemApi
  public interface NfcUnlockHandler {
    boolean onUnlockAttempted(Tag tag);
  }

  @Deprecated
  public interface OnNdefPushCompleteCallback {
    void onNdefPushComplete(NfcEvent nfcEvent);
  }

  public interface OnTagRemovedListener {
    void onTagRemoved();
  }

  public interface ReaderCallback {
    void onTagDiscovered(Tag tag);
  }

  @Retention(RetentionPolicy.SOURCE)
  public @interface TagIntentAppPreferenceResult {}

  public List<String> getSupportedOffHostSecureElements() {
    if (this.mContext == null) {
      throw new UnsupportedOperationException(
          "You need a context on NfcAdapter to use the  getSupportedOffHostSecureElements APIs");
    }
    List<String> offHostSE = new ArrayList<>();
    PackageManager pm = this.mContext.getPackageManager();
    if (pm == null) {
      Log.m96e(TAG, "Cannot get package manager, assuming no off-host CE feature");
      return offHostSE;
    }
    if (pm.hasSystemFeature(PackageManager.FEATURE_NFC_OFF_HOST_CARD_EMULATION_UICC)) {
      offHostSE.add("SIM");
    }
    if (pm.hasSystemFeature(PackageManager.FEATURE_NFC_OFF_HOST_CARD_EMULATION_ESE)) {
      offHostSE.add("eSE");
    }
    return offHostSE;
  }

  /* JADX WARN: Removed duplicated region for block: B:32:0x0067 A[Catch: all -> 0x00f4, TryCatch #2 {, blocks: (B:5:0x0005, B:7:0x0009, B:8:0x0011, B:12:0x0015, B:14:0x0019, B:16:0x002e, B:18:0x0036, B:20:0x003e, B:24:0x004a, B:28:0x0053, B:29:0x0060, B:30:0x0061, B:32:0x0067, B:34:0x0075, B:52:0x0079, B:36:0x008e, B:40:0x0092, B:43:0x009b, B:46:0x00a5, B:47:0x00b1, B:38:0x00c0, B:49:0x00b3, B:50:0x00bf, B:55:0x0081, B:56:0x008d, B:57:0x00c3, B:58:0x00cf, B:59:0x00d0, B:60:0x00dc, B:62:0x00dd, B:64:0x00e7), top: B:3:0x0003, inners: #0, #1, #3 }] */
  /* JADX WARN: Removed duplicated region for block: B:59:0x00d0 A[Catch: all -> 0x00f4, TryCatch #2 {, blocks: (B:5:0x0005, B:7:0x0009, B:8:0x0011, B:12:0x0015, B:14:0x0019, B:16:0x002e, B:18:0x0036, B:20:0x003e, B:24:0x004a, B:28:0x0053, B:29:0x0060, B:30:0x0061, B:32:0x0067, B:34:0x0075, B:52:0x0079, B:36:0x008e, B:40:0x0092, B:43:0x009b, B:46:0x00a5, B:47:0x00b1, B:38:0x00c0, B:49:0x00b3, B:50:0x00bf, B:55:0x0081, B:56:0x008d, B:57:0x00c3, B:58:0x00cf, B:59:0x00d0, B:60:0x00dc, B:62:0x00dd, B:64:0x00e7), top: B:3:0x0003, inners: #0, #1, #3 }] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public static synchronized NfcAdapter getNfcAdapter(Context context) {
    boolean z;
    NfcServiceManager manager;
    synchronized (NfcAdapter.class) {
      if (context == null) {
        if (sNullContextNfcAdapter == null) {
          sNullContextNfcAdapter = new NfcAdapter(null);
        }
        return sNullContextNfcAdapter;
      }
      if (!sIsInitialized) {
        PackageManager pm = context.getPackageManager();
        sHasNfcFeature = pm.hasSystemFeature(PackageManager.FEATURE_NFC);
        if (!pm.hasSystemFeature("android.hardware.nfc.hce")
            && !pm.hasSystemFeature(PackageManager.FEATURE_NFC_HOST_CARD_EMULATION_NFCF)
            && !pm.hasSystemFeature(PackageManager.FEATURE_NFC_OFF_HOST_CARD_EMULATION_UICC)
            && !pm.hasSystemFeature(PackageManager.FEATURE_NFC_OFF_HOST_CARD_EMULATION_ESE)) {
          z = false;
          sHasCeFeature = z;
          if (!sHasNfcFeature && !z) {
            Log.m100v(TAG, "this device does not have NFC support");
            throw new UnsupportedOperationException();
          }
          manager = NfcFrameworkInitializer.getNfcServiceManager();
          if (manager != null) {
            Log.m96e(TAG, "NfcServiceManager is null");
            throw new UnsupportedOperationException();
          }
          sServiceRegisterer = manager.getNfcManagerServiceRegisterer();
          INfcAdapter serviceInterface = getServiceInterface();
          sService = serviceInterface;
          if (serviceInterface == null) {
            Log.m96e(TAG, "could not retrieve NFC service");
            throw new UnsupportedOperationException();
          }
          if (sHasNfcFeature) {
            try {
              sTagService = serviceInterface.getNfcTagInterface();
            } catch (RemoteException e) {
              Log.m96e(TAG, "could not retrieve NFC Tag service");
              throw new UnsupportedOperationException();
            }
          }
          if (sHasCeFeature) {
            try {
              sNfcFCardEmulationService = sService.getNfcFCardEmulationInterface();
              try {
                sCardEmulationService = sService.getNfcCardEmulationInterface();
              } catch (RemoteException e2) {
                Log.m96e(TAG, "could not retrieve card emulation service");
                throw new UnsupportedOperationException();
              }
            } catch (RemoteException e3) {
              Log.m96e(TAG, "could not retrieve NFC-F card emulation service");
              throw new UnsupportedOperationException();
            }
          }
          sIsInitialized = true;
        }
        z = true;
        sHasCeFeature = z;
        if (!sHasNfcFeature) {
          Log.m100v(TAG, "this device does not have NFC support");
          throw new UnsupportedOperationException();
        }
        manager = NfcFrameworkInitializer.getNfcServiceManager();
        if (manager != null) {}
      }
      NfcAdapter adapter = sNfcAdapters.get(context);
      if (adapter == null) {
        adapter = new NfcAdapter(context);
        sNfcAdapters.put(context, adapter);
      }
      return adapter;
    }
  }

  private static INfcAdapter getServiceInterface() {
    IBinder b = sServiceRegisterer.get();
    if (b == null) {
      return null;
    }
    return INfcAdapter.Stub.asInterface(b);
  }

  public static NfcAdapter getDefaultAdapter(Context context) {
    if (context == null) {
      throw new IllegalArgumentException("context cannot be null");
    }
    Context context2 = context.getApplicationContext();
    if (context2 == null) {
      throw new IllegalArgumentException(
          "context not associated with any application (using a mock context?)");
    }
    if (sIsInitialized && sServiceRegisterer.tryGet() == null) {
      synchronized (NfcAdapter.class) {
        if (sIsInitialized) {
          sIsInitialized = false;
        }
      }
      return null;
    }
    NfcManager manager = (NfcManager) context2.getSystemService("nfc");
    if (manager == null) {
      return null;
    }
    manager.bindNfcService(context2);
    return manager.getDefaultAdapter();
  }

  @Deprecated
  public static NfcAdapter getDefaultAdapter() {
    Log.m103w(
        TAG,
        "WARNING: NfcAdapter.getDefaultAdapter() is deprecated, use"
            + " NfcAdapter.getDefaultAdapter(Context) instead",
        new Exception());
    return getNfcAdapter(null);
  }

  NfcAdapter(Context context) {
    this.mContext = context;
  }

  public Context getContext() {
    return this.mContext;
  }

  public INfcAdapter getService() {
    isEnabled();
    return sService;
  }

  public INfcTag getTagService() {
    isEnabled();
    return sTagService;
  }

  public INfcCardEmulation getCardEmulationService() {
    isEnabled();
    return sCardEmulationService;
  }

  public INfcFCardEmulation getNfcFCardEmulationService() {
    isEnabled();
    return sNfcFCardEmulationService;
  }

  public INfcDta getNfcDtaInterface() {
    Context context = this.mContext;
    if (context == null) {
      throw new UnsupportedOperationException(
          "You need a context on NfcAdapter to use the  NFC extras APIs");
    }
    try {
      return sService.getNfcDtaInterface(context.getPackageName());
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      INfcAdapter iNfcAdapter = sService;
      if (iNfcAdapter == null) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return null;
      }
      try {
        return iNfcAdapter.getNfcDtaInterface(this.mContext.getPackageName());
      } catch (RemoteException e2) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return null;
      }
    }
  }

  public void attemptDeadServiceRecovery(Exception e) {
    Log.m97e(TAG, "NFC service dead - attempting to recover", e);
    INfcAdapter service = getServiceInterface();
    if (service == null) {
      Log.m96e(TAG, "could not retrieve NFC service during service recovery");
      return;
    }
    sService = service;
    try {
      sTagService = service.getNfcTagInterface();
      try {
        sCardEmulationService = service.getNfcCardEmulationInterface();
      } catch (RemoteException e2) {
        Log.m96e(TAG, "could not retrieve NFC card emulation service during service recovery");
      }
      try {
        sNfcFCardEmulationService = service.getNfcFCardEmulationInterface();
      } catch (RemoteException e3) {
        Log.m96e(TAG, "could not retrieve NFC-F card emulation service during service recovery");
      }
    } catch (RemoteException e4) {
      Log.m96e(TAG, "could not retrieve NFC tag service during service recovery");
    }
  }

  public boolean isEnabled() {
    try {
      return sService.getState() == 3;
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      INfcAdapter iNfcAdapter = sService;
      if (iNfcAdapter == null) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
      try {
        return iNfcAdapter.getState() == 3;
      } catch (RemoteException e2) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
    }
  }

  public boolean isUnlocked() {
    try {
      if (sService.getState() != 5) {
        if (sService.getState() != 3) {
          return false;
        }
      }
      return true;
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      return false;
    }
  }

  public int getAdapterState() {
    try {
      return sService.getState();
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      INfcAdapter iNfcAdapter = sService;
      if (iNfcAdapter == null) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return 1;
      }
      try {
        return iNfcAdapter.getState();
      } catch (RemoteException e2) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return 1;
      }
    }
  }

  @SystemApi
  public boolean enable() {
    try {
      if (!isSupportKnoxMdm() || isNFCStateChangeAllowed(0)) {
        Log.m94d(TAG, "myUID is " + Process.myUid());
        if (1000 == Process.myUid()) {
          ArrayList<String> callHistory = new ArrayList<>();
          for (StackTraceElement st : new Exception().getStackTrace()) {
            callHistory.add(st.toString());
          }
          String[] callStrings = (String[]) callHistory.toArray(new String[callHistory.size()]);
          sService.storeEnableHistory(callStrings);
        }
        return sService.enable();
      }
      Log.m96e(TAG, "EDM : nfc policy disabled");
      return false;
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      INfcAdapter iNfcAdapter = sService;
      if (iNfcAdapter == null) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
      try {
        return iNfcAdapter.enable();
      } catch (RemoteException e2) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
    }
  }

  @SystemApi
  public boolean disable() {
    try {
      if (isSupportKnoxMdm() && !isNFCStateChangeAllowed(1)) {
        Log.m96e(TAG, "EDM : nfc policy disabled");
        return false;
      }
      return sService.disable(true);
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      INfcAdapter iNfcAdapter = sService;
      if (iNfcAdapter == null) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
      try {
        return iNfcAdapter.disable(true);
      } catch (RemoteException e2) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
    }
  }

  @SystemApi
  public boolean disable(boolean persist) {
    try {
      return sService.disable(persist);
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      INfcAdapter iNfcAdapter = sService;
      if (iNfcAdapter == null) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
      try {
        return iNfcAdapter.disable(persist);
      } catch (RemoteException e2) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
    }
  }

  public void pausePolling(int timeoutInMs) {
    try {
      sService.pausePolling(timeoutInMs);
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
    }
  }

  public void resumePolling() {
    try {
      sService.resumePolling();
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
    }
  }

  @Deprecated
  public void setBeamPushUris(Uri[] uris, Activity activity) {
    synchronized (NfcAdapter.class) {
      if (!sHasNfcFeature) {
        throw new UnsupportedOperationException();
      }
    }
  }

  @Deprecated
  public void setBeamPushUrisCallback(CreateBeamUrisCallback callback, Activity activity) {
    synchronized (NfcAdapter.class) {
      if (!sHasNfcFeature) {
        throw new UnsupportedOperationException();
      }
    }
  }

  @Deprecated
  public void setNdefPushMessage(NdefMessage message, Activity activity, Activity... activities) {
    synchronized (NfcAdapter.class) {
      if (!sHasNfcFeature) {
        throw new UnsupportedOperationException();
      }
    }
  }

  @SystemApi
  public void setNdefPushMessage(NdefMessage message, Activity activity, int flags) {
    synchronized (NfcAdapter.class) {
      if (!sHasNfcFeature) {
        throw new UnsupportedOperationException();
      }
    }
  }

  @Deprecated
  public void setNdefPushMessageCallback(
      CreateNdefMessageCallback callback, Activity activity, Activity... activities) {
    synchronized (NfcAdapter.class) {
      if (!sHasNfcFeature) {
        throw new UnsupportedOperationException();
      }
    }
  }

  @Deprecated
  public void setOnNdefPushCompleteCallback(
      OnNdefPushCompleteCallback callback, Activity activity, Activity... activities) {
    synchronized (NfcAdapter.class) {
      if (!sHasNfcFeature) {
        throw new UnsupportedOperationException();
      }
    }
  }

  public void enableForegroundDispatch(
      Activity activity, PendingIntent intent, IntentFilter[] filters, String[][] techLists) {
    synchronized (NfcAdapter.class) {
      if (!sHasNfcFeature) {
        throw new UnsupportedOperationException();
      }
    }
    if (activity == null || intent == null) {
      throw new NullPointerException();
    }
    if (!activity.isResumed()) {
      throw new IllegalStateException(
          "Foreground dispatch can only be enabled when your activity is resumed");
    }
    TechListParcel parcel = null;
    if (techLists != null) {
      try {
        if (techLists.length > 0) {
          parcel = new TechListParcel(techLists);
        }
      } catch (RemoteException e) {
        attemptDeadServiceRecovery(e);
        return;
      }
    }
    ActivityThread.currentActivityThread()
        .registerOnActivityPausedListener(activity, this.mForegroundDispatchListener);
    sService.setForegroundDispatch(intent, filters, parcel);
  }

  public void disableForegroundDispatch(Activity activity) {
    synchronized (NfcAdapter.class) {
      if (!sHasNfcFeature) {
        throw new UnsupportedOperationException();
      }
    }
    ActivityThread.currentActivityThread()
        .unregisterOnActivityPausedListener(activity, this.mForegroundDispatchListener);
    disableForegroundDispatchInternal(activity, false);
  }

  void disableForegroundDispatchInternal(Activity activity, boolean force) {
    try {
      sService.setForegroundDispatch(null, null, null);
      if (!force && !activity.isResumed()) {
        throw new IllegalStateException(
            "You must disable foreground dispatching while your activity is still resumed");
      }
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
    }
  }

  public void enableReaderMode(
      Activity activity, ReaderCallback callback, int flags, Bundle extras) {
    synchronized (NfcAdapter.class) {
      if (!sHasNfcFeature) {
        throw new UnsupportedOperationException();
      }
    }
    if (isSupportKnoxContainer() && !isNFCAllowed(UserHandle.myUserId())) {
      Log.m98i(TAG, " enable reader mode is disabled due to Knox restriction");
    } else {
      this.mNfcActivityManager.enableReaderMode(activity, callback, flags, extras);
    }
  }

  public void enableListenMode(Activity activity, int flags) {
    Log.m98i(TAG, "SPay : enableListenMode");
    if (isSupportKnoxContainer() && !isNFCAllowed(UserHandle.myUserId())) {
      Log.m98i(TAG, " enable reader mode is disabled due to Knox restriction");
    } else {
      this.mNfcActivityManager.setDiscoveryTech(activity, -1, flags);
    }
  }

  public void disableListenMode(Activity activity) {
    Log.m98i(TAG, "SPay : disableListenMode");
    this.mNfcActivityManager.unsetDiscoveryTech(activity);
  }

  public void setDiscoveryTech(Activity activity, int pollTech, int listenTech) {
    Log.m98i(TAG, "SPay : setDiscoveryTech");
    if (isSupportKnoxContainer() && !isNFCAllowed(UserHandle.myUserId())) {
      Log.m98i(TAG, "setDiscoveryTech is disabled due to Knox restriction");
    } else {
      this.mNfcActivityManager.setDiscoveryTech(activity, pollTech, listenTech);
    }
  }

  public void unsetDiscoveryTech(Activity activity) {
    Log.m98i(TAG, "SPay : unsetDiscoveryTech");
    this.mNfcActivityManager.unsetDiscoveryTech(activity);
  }

  public void changeRouting(
      Activity activity, String proto, String tech, List<ComponentName> services) {
    Log.m98i(TAG, "SPay : changeRouting");
    this.mNfcActivityManager.changeRouting(UserHandle.myUserId(), activity, proto, tech, services);
  }

  public void disableReaderMode(Activity activity) {
    synchronized (NfcAdapter.class) {
      if (!sHasNfcFeature) {
        throw new UnsupportedOperationException();
      }
    }
    this.mNfcActivityManager.disableReaderMode(activity);
  }

  @Deprecated
  public boolean invokeBeam(Activity activity) {
    synchronized (NfcAdapter.class) {
      if (!sHasNfcFeature) {
        throw new UnsupportedOperationException();
      }
    }
    return false;
  }

  @Deprecated
  public void enableForegroundNdefPush(Activity activity, NdefMessage message) {
    synchronized (NfcAdapter.class) {
      if (!sHasNfcFeature) {
        throw new UnsupportedOperationException();
      }
    }
  }

  @Deprecated
  public void disableForegroundNdefPush(Activity activity) {
    synchronized (NfcAdapter.class) {
      if (!sHasNfcFeature) {
        throw new UnsupportedOperationException();
      }
    }
  }

  @SystemApi
  public boolean enableSecureNfc(boolean enable) {
    if (!sHasNfcFeature && !sHasCeFeature) {
      throw new UnsupportedOperationException();
    }
    try {
      return sService.setNfcSecure(enable);
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      INfcAdapter iNfcAdapter = sService;
      if (iNfcAdapter == null) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
      try {
        return iNfcAdapter.setNfcSecure(enable);
      } catch (RemoteException e2) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
    }
  }

  public boolean isSecureNfcSupported() {
    if (!sHasNfcFeature && !sHasCeFeature) {
      throw new UnsupportedOperationException();
    }
    try {
      return sService.deviceSupportsNfcSecure();
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      INfcAdapter iNfcAdapter = sService;
      if (iNfcAdapter == null) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
      try {
        return iNfcAdapter.deviceSupportsNfcSecure();
      } catch (RemoteException e2) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
    }
  }

  public NfcAntennaInfo getNfcAntennaInfo() {
    if (!sHasNfcFeature && !sHasCeFeature) {
      throw new UnsupportedOperationException();
    }
    try {
      return sService.getNfcAntennaInfo();
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      INfcAdapter iNfcAdapter = sService;
      if (iNfcAdapter == null) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return null;
      }
      try {
        return iNfcAdapter.getNfcAntennaInfo();
      } catch (RemoteException e2) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return null;
      }
    }
  }

  public boolean isSecureNfcEnabled() {
    if (!sHasNfcFeature && !sHasCeFeature) {
      throw new UnsupportedOperationException();
    }
    try {
      return sService.isNfcSecureEnabled();
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      INfcAdapter iNfcAdapter = sService;
      if (iNfcAdapter == null) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
      try {
        return iNfcAdapter.isNfcSecureEnabled();
      } catch (RemoteException e2) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
    }
  }

  public boolean enableReaderOption(boolean z) {
    if (!sHasNfcFeature) {
      throw new UnsupportedOperationException();
    }
    if (isSupportKnoxMdm() && !isNFCStateChangeAllowed(!z ? 1 : 0)) {
      Log.m96e(TAG, "EDM : nfc policy disabled");
      return false;
    }
    try {
      return sService.enableReaderOption(z);
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      INfcAdapter iNfcAdapter = sService;
      if (iNfcAdapter == null) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
      try {
        return iNfcAdapter.enableReaderOption(z);
      } catch (RemoteException e2) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
    }
  }

  public boolean deviceSupportsReaderOption() {
    if (!sHasNfcFeature) {
      throw new UnsupportedOperationException();
    }
    try {
      return sService.deviceSupportsReaderOption();
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      INfcAdapter iNfcAdapter = sService;
      if (iNfcAdapter == null) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
      try {
        return iNfcAdapter.deviceSupportsReaderOption();
      } catch (RemoteException e2) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
    }
  }

  public boolean isReaderOptionEnabled() {
    if (!sHasNfcFeature) {
      throw new UnsupportedOperationException();
    }
    try {
      return sService.isReaderOptionEnabled();
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      INfcAdapter iNfcAdapter = sService;
      if (iNfcAdapter == null) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
      try {
        return iNfcAdapter.isReaderOptionEnabled();
      } catch (RemoteException e2) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
    }
  }

  @SystemApi
  public boolean enableNdefPush() {
    return false;
  }

  @SystemApi
  public boolean disableNdefPush() {
    return false;
  }

  @Deprecated
  public boolean isNdefPushEnabled() {
    synchronized (NfcAdapter.class) {
      if (!sHasNfcFeature) {
        throw new UnsupportedOperationException();
      }
    }
    return false;
  }

  public boolean ignore(
      Tag tag,
      int debounceMs,
      final OnTagRemovedListener tagRemovedListener,
      final Handler handler) {
    ITagRemovedCallback.Stub iListener = null;
    if (tagRemovedListener != null) {
      iListener =
          new ITagRemovedCallback.Stub() { // from class: android.nfc.NfcAdapter.2
            @Override // android.nfc.ITagRemovedCallback
            public void onTagRemoved() throws RemoteException {
              Handler handler2 = handler;
              if (handler2 != null) {
                handler2.post(
                    new Runnable() { // from class: android.nfc.NfcAdapter.2.1
                      @Override // java.lang.Runnable
                      public void run() {
                        tagRemovedListener.onTagRemoved();
                      }
                    });
              } else {
                tagRemovedListener.onTagRemoved();
              }
              synchronized (NfcAdapter.this.mLock) {
                NfcAdapter.this.mTagRemovedListener = null;
              }
            }
          };
    }
    synchronized (this.mLock) {
      this.mTagRemovedListener = iListener;
    }
    try {
      return sService.ignore(tag.getServiceHandle(), debounceMs, iListener);
    } catch (RemoteException e) {
      return false;
    }
  }

  public void dispatch(Tag tag) {
    if (tag == null) {
      throw new NullPointerException("tag cannot be null");
    }
    try {
      sService.dispatch(tag);
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
    }
  }

  private boolean isAndroidBeamAllowed(boolean showMsg) {
    Cursor cursor;
    Cursor cursor2;
    if (isSupportKnoxMdm()) {
      try {
        Log.m96e(TAG, "isAndroidBeamAllowed - Begin");
        this.mUri = Uri.parse("content://com.sec.knox.provider/RestrictionPolicy1");
        String[] selectionArgs = {String.valueOf(showMsg)};
        Log.m98i(TAG, "showMsg is " + selectionArgs[0]);
        Cursor query =
            this.mContext
                .getContentResolver()
                .query(this.mUri, null, "isAndroidBeamAllowed", selectionArgs, null);
        this.mRestrictionCr = query;
        if (query == null) {
          return true;
        }
        query.moveToFirst();
        try {
          try {
            cursor2 = this.mRestrictionCr;
          } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
            cursor = this.mRestrictionCr;
          }
          if (cursor2.getString(cursor2.getColumnIndex("isAndroidBeamAllowed")).equals("false")) {
            Log.m96e(TAG, "AndroidBeam is not allowed");
            return false;
          }
          cursor = this.mRestrictionCr;
          cursor.close();
        } finally {
          this.mRestrictionCr.close();
        }
      } catch (Exception e2) {
        Log.m96e(TAG, "Failed to talk to Restriction Policy");
      }
    }
    return true;
  }

  private void auditLog(boolean flag, String msg) {
    if (isSupportKnoxMdm()) {
      Uri uri = Uri.parse("content://com.sec.knox.provider/AuditLog");
      ContentValues cv = new ContentValues();
      cv.put("severity", (Integer) 5);
      cv.put("group", (Integer) 5);
      cv.put("outcome", Boolean.valueOf(flag));
      cv.put("uid", Integer.valueOf(Process.myPid()));
      cv.put("component", TAG);
      cv.put("message", msg);
      this.mContext.getContentResolver().insert(uri, cv);
    }
  }

  private boolean isNFCStateChangeAllowed(int rejectType) {
    Cursor cursor;
    Cursor cursor2;
    Cursor cursor3;
    Cursor cursor4;
    if (isSupportKnoxMdm()) {
      try {
        Log.m96e(TAG, "isNFCStateChangeAllowed - Begin");
        this.mUri = Uri.parse("content://com.sec.knox.provider/RestrictionPolicy3");
        Cursor query =
            this.mContext
                .getContentResolver()
                .query(this.mUri, null, "isSettingsChangesAllowed", new String[] {"true"}, null);
        this.mDevSettingCr = query;
        if (query != null) {
          query.moveToFirst();
          try {
            try {
              cursor4 = this.mDevSettingCr;
            } catch (CursorIndexOutOfBoundsException e) {
              e.printStackTrace();
              cursor3 = this.mDevSettingCr;
            }
            if (cursor4
                .getString(cursor4.getColumnIndex("isSettingsChangesAllowed"))
                .equals("false")) {
              Log.m96e(TAG, "Restriction policy block settings change");
              auditLog(
                  false,
                  rejectType == 0
                      ? "Nfc policy: Enabling NFC failed. Reason: isSettingsChangesAllowed is false"
                      : "Nfc policy: Disabling NFC failed. Reason: isSettingsChangesAllowed is"
                            + " false");
              return false;
            }
            cursor3 = this.mDevSettingCr;
            cursor3.close();
          } finally {
          }
        }
        this.mUri = Uri.parse("content://com.sec.knox.provider2/MiscPolicy");
        Cursor query2 =
            this.mContext
                .getContentResolver()
                .query(this.mUri, null, "isNFCStateChangeAllowed", null, null);
        this.mDevSettingCr = query2;
        if (query2 != null) {
          query2.moveToFirst();
          try {
            try {
              cursor2 = this.mDevSettingCr;
            } finally {
            }
          } catch (CursorIndexOutOfBoundsException e2) {
            e2.printStackTrace();
            cursor = this.mDevSettingCr;
          }
          if (cursor2
              .getString(cursor2.getColumnIndex("isNFCStateChangeAllowed"))
              .equals("false")) {
            new Handler(Looper.getMainLooper())
                .post(
                    new Runnable() { // from class: android.nfc.NfcAdapter.3
                      @Override // java.lang.Runnable
                      public void run() {
                        Toast.makeText(
                                NfcAdapter.this.mContext,
                                NfcAdapter.this.mContext.getString(
                                    C4337R.string.allow_settings_changes),
                                0)
                            .show();
                      }
                    });
            Log.m96e(TAG, "Restriction policy block NFC state change");
            auditLog(
                false,
                rejectType == 0
                    ? AuditEvents.AUDIT_ENABLING_NFC_POLICY_ENFORCEMENT
                    : AuditEvents.AUDIT_DISABLING_NFC_POLICY_ENFORCEMENT);
            return false;
          }
          cursor = this.mDevSettingCr;
          cursor.close();
        }
      } catch (Exception e3) {
        Log.m96e(TAG, "Failed to talk to Misc Policy");
      }
    }
    return true;
  }

  @SystemApi
  public boolean addNfcUnlockHandler(
      final NfcUnlockHandler unlockHandler, String[] tagTechnologies) {
    synchronized (NfcAdapter.class) {
      if (!sHasNfcFeature) {
        throw new UnsupportedOperationException();
      }
    }
    if (tagTechnologies.length == 0) {
      return false;
    }
    try {
      synchronized (this.mLock) {
        if (this.mNfcUnlockHandlers.containsKey(unlockHandler)) {
          sService.removeNfcUnlockHandler(this.mNfcUnlockHandlers.get(unlockHandler));
          this.mNfcUnlockHandlers.remove(unlockHandler);
        }
        INfcUnlockHandler.Stub iHandler =
            new INfcUnlockHandler.Stub() { // from class: android.nfc.NfcAdapter.4
              @Override // android.nfc.INfcUnlockHandler
              public boolean onUnlockAttempted(Tag tag) throws RemoteException {
                return unlockHandler.onUnlockAttempted(tag);
              }
            };
        sService.addNfcUnlockHandler(iHandler, Tag.getTechCodesFromStrings(tagTechnologies));
        this.mNfcUnlockHandlers.put(unlockHandler, iHandler);
      }
      return true;
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      return false;
    } catch (IllegalArgumentException e2) {
      Log.m97e(TAG, "Unable to register LockscreenDispatch", e2);
      return false;
    }
  }

  @SystemApi
  public boolean removeNfcUnlockHandler(NfcUnlockHandler unlockHandler) {
    synchronized (NfcAdapter.class) {
      if (!sHasNfcFeature) {
        throw new UnsupportedOperationException();
      }
    }
    try {
      synchronized (this.mLock) {
        if (this.mNfcUnlockHandlers.containsKey(unlockHandler)) {
          sService.removeNfcUnlockHandler(this.mNfcUnlockHandlers.remove(unlockHandler));
        }
      }
      return true;
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      return false;
    }
  }

  public INfcAdapterExtras getNfcAdapterExtrasInterface() {
    Context context = this.mContext;
    if (context == null) {
      throw new UnsupportedOperationException(
          "You need a context on NfcAdapter to use the  NFC extras APIs");
    }
    try {
      return sService.getNfcAdapterExtrasInterface(context.getPackageName());
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      INfcAdapter iNfcAdapter = sService;
      if (iNfcAdapter == null) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return null;
      }
      try {
        return iNfcAdapter.getNfcAdapterExtrasInterface(this.mContext.getPackageName());
      } catch (RemoteException e2) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return null;
      }
    }
  }

  void enforceResumed(Activity activity) {
    if (!activity.isResumed()) {
      throw new IllegalStateException("API cannot be called while activity is paused");
    }
  }

  int getSdkVersion() {
    Context context = this.mContext;
    if (context == null) {
      return 9;
    }
    return context.getApplicationInfo().targetSdkVersion;
  }

  @SystemApi
  public boolean setControllerAlwaysOn(boolean value) {
    if (!sHasNfcFeature && !sHasCeFeature) {
      throw new UnsupportedOperationException();
    }
    try {
      return sService.setControllerAlwaysOn(value);
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      INfcAdapter iNfcAdapter = sService;
      if (iNfcAdapter == null) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
      try {
        return iNfcAdapter.setControllerAlwaysOn(value);
      } catch (RemoteException e2) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
    }
  }

  @SystemApi
  public boolean isControllerAlwaysOn() {
    try {
      return sService.isControllerAlwaysOn();
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      INfcAdapter iNfcAdapter = sService;
      if (iNfcAdapter == null) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
      try {
        return iNfcAdapter.isControllerAlwaysOn();
      } catch (RemoteException e2) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
    }
  }

  @SystemApi
  public boolean isControllerAlwaysOnSupported() {
    if (!sHasNfcFeature && !sHasCeFeature) {
      throw new UnsupportedOperationException();
    }
    try {
      return sService.isControllerAlwaysOnSupported();
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      INfcAdapter iNfcAdapter = sService;
      if (iNfcAdapter == null) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
      try {
        return iNfcAdapter.isControllerAlwaysOnSupported();
      } catch (RemoteException e2) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
    }
  }

  @SystemApi
  public void registerControllerAlwaysOnListener(
      Executor executor, ControllerAlwaysOnListener listener) {
    this.mControllerAlwaysOnListener.register(executor, listener);
  }

  @SystemApi
  public void unregisterControllerAlwaysOnListener(ControllerAlwaysOnListener listener) {
    this.mControllerAlwaysOnListener.unregister(listener);
  }

  @SystemApi
  public int setTagIntentAppPreferenceForUser(int userId, String pkg, boolean allow) {
    Objects.requireNonNull(pkg, "pkg cannot be null");
    if (!isTagIntentAppPreferenceSupported()) {
      Log.m96e(TAG, "TagIntentAppPreference is not supported");
      throw new UnsupportedOperationException();
    }
    try {
      return sService.setTagIntentAppPreferenceForUser(userId, pkg, allow);
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      if (sService == null) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
      }
      try {
        return sService.setTagIntentAppPreferenceForUser(userId, pkg, allow);
      } catch (RemoteException e2) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return -2;
      }
    }
  }

  @SystemApi
  public Map<String, Boolean> getTagIntentAppPreferenceForUser(int userId) {
    if (!isTagIntentAppPreferenceSupported()) {
      Log.m96e(TAG, "TagIntentAppPreference is not supported");
      throw new UnsupportedOperationException();
    }
    try {
      Map<String, Boolean> result = sService.getTagIntentAppPreferenceForUser(userId);
      return result;
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      INfcAdapter iNfcAdapter = sService;
      if (iNfcAdapter == null) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return Collections.emptyMap();
      }
      try {
        Map<String, Boolean> result2 = iNfcAdapter.getTagIntentAppPreferenceForUser(userId);
        return result2;
      } catch (RemoteException e2) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return Collections.emptyMap();
      }
    }
  }

  @SystemApi
  public boolean isTagIntentAppPreferenceSupported() {
    if (!sHasNfcFeature) {
      throw new UnsupportedOperationException();
    }
    try {
      return sService.isTagIntentAppPreferenceSupported();
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      INfcAdapter iNfcAdapter = sService;
      if (iNfcAdapter == null) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
      try {
        return iNfcAdapter.isTagIntentAppPreferenceSupported();
      } catch (RemoteException e2) {
        Log.m96e(TAG, "Failed to recover NFC Service.");
        return false;
      }
    }
  }

  public void enableDisableSeTestMode(String SE, boolean enable) throws IOException {
    try {
      sService.enableDisableSeTestMode(SE, enable);
    } catch (RemoteException e) {
      Log.m97e(TAG, "Fail to enableDisableSeTestMode", e);
      throw new IOException("Fail to enableDisableSeTestMode");
    }
  }

  public void setDefaultRoutingDestination(String SE) throws IOException {
    try {
      sService.setDefaultRoutingDestination(SE);
    } catch (RemoteException e) {
      Log.m97e(TAG, "Fail to set default routing destination", e);
      throw new IOException("Fail to set default routing destination");
    }
  }

  public String getDefaultRoutingDestination() throws IOException {
    try {
      String se = sService.getDefaultRoutingDestination();
      return se;
    } catch (RemoteException e) {
      Log.m97e(TAG, "Fail to get default routing destination", e);
      throw new IOException("Fail to get default routing destination");
    }
  }

  public byte[] apcCommand(int cmd, byte[] param) throws IOException {
    try {
      return sService.apcCommand(cmd, param);
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      throw new IOException("Fail apcCommand");
    }
  }

  public boolean isAllEnabled() {
    try {
      if (sService.getState() != 5) {
        if (sService.getState() != 3) {
          return false;
        }
      }
      return true;
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      return false;
    }
  }

  public byte[] startCoverAuth() throws IOException {
    try {
      return sService.startCoverAuth();
    } catch (RemoteException e) {
      Log.m96e(TAG, "Failed to transmit authentication data");
      attemptDeadServiceRecovery(e);
      throw new IOException("Failed to transmit authentication data");
    }
  }

  public byte[] transceiveAuthData(byte[] data) throws IOException {
    try {
      return sService.transceiveAuthData(data);
    } catch (RemoteException e) {
      Log.m96e(TAG, "Failed to transmit authentication data");
      attemptDeadServiceRecovery(e);
      throw new IOException("Failed to transmit authentication data");
    }
  }

  public boolean stopCoverAuth() throws IOException {
    try {
      return sService.stopCoverAuth();
    } catch (RemoteException e) {
      Log.m96e(TAG, "Failed to stop authentication");
      attemptDeadServiceRecovery(e);
      throw new IOException("Failed to stop authentication");
    }
  }

  public int getSeSupportedTech() throws IOException {
    try {
      return sService.getSeSupportedTech();
    } catch (RemoteException e) {
      Log.m96e(TAG, "Failed to get Tech information");
      attemptDeadServiceRecovery(e);
      throw new IOException("Failed to get Tech information");
    }
  }

  public String getPhoneNumber() {
    try {
      return sService.getPhoneNumber();
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      return "";
    }
  }

  public boolean writeFelicaLockState(String fileDirectory, String fileName, String value) {
    try {
      return sService.writeFelicaLockState(fileDirectory, fileName, value);
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      return false;
    }
  }

  public void copyUtilityFiles() {
    try {
      sService.copyUtilityFiles();
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
    }
  }

  public void networkReset() throws IOException {
    try {
      sService.NetworkResetAtt();
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      Log.m97e(TAG, "Fail to networkReset", e);
      throw new IOException("Fail to networkReset");
    }
  }

  public boolean semEnable() {
    return enable();
  }

  public boolean semDisable() {
    return disable();
  }

  public int semGetAdapterState() {
    return getAdapterState();
  }

  public void semSetDiscoveryTechnology(Activity activity, int pollTech, int listenTech) {
    Log.m98i(TAG, "SPay : setDiscoveryTech");
    if (isSupportKnoxContainer() && !isNFCAllowed(UserHandle.myUserId())) {
      Log.m98i(TAG, "setDiscoveryTech is disabled due to Knox restriction");
    } else {
      this.mNfcActivityManager.setDiscoveryTech(activity, pollTech, listenTech);
    }
  }

  public void semUnsetDiscoveryTechnology(Activity activity) {
    Log.m98i(TAG, "SPay : unsetDiscoveryTech");
    this.mNfcActivityManager.unsetDiscoveryTech(activity);
  }

  public void semChangeRouting(
      Activity activity, String proto, String tech, List<ComponentName> services) {
    Log.m98i(TAG, "SPay : changeRouting");
    this.mNfcActivityManager.changeRouting(UserHandle.myUserId(), activity, proto, tech, services);
  }

  public int semGetSecureElementSupportedTechnology() throws IOException {
    try {
      return sService.getSeSupportedTech();
    } catch (RemoteException e) {
      Log.m96e(TAG, "Failed to get Tech information");
      attemptDeadServiceRecovery(e);
      throw new IOException("Failed to get Tech information");
    }
  }

  public byte[] semStartLedCoverMode() {
    try {
      return sService.StartLedCover();
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      byte[] bytes = {3};
      return bytes;
    }
  }

  public byte[] semTransceiveDataWithLedCover(byte[] data) {
    try {
      return sService.TransceiveLedCover(data);
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      byte[] bytes = {3};
      return bytes;
    }
  }

  public boolean semStopLedCoverMode() {
    try {
      return sService.StopLedCover();
    } catch (RemoteException e) {
      attemptDeadServiceRecovery(e);
      return false;
    }
  }

  public boolean semSetWirelessChargeEnabled(boolean enable) {
    try {
      return sService.SetWirelessChargeEnabled(enable);
    } catch (RemoteException e) {
      return false;
    }
  }

  public boolean semEnableNdefPush() {
    return enableNdefPush();
  }

  public boolean semDisableNdefPush() {
    return disableNdefPush();
  }

  public boolean semEnableReaderMode() {
    return enableReaderOption(true);
  }

  public boolean semDisableReaderMode() {
    return enableReaderOption(false);
  }

  private boolean isNFCAllowed(int userId) {
    try {
      return sService.isNFCAllowed(userId);
    } catch (RemoteException e) {
      Log.m96e(TAG, "Failed to call isNFCAllowed: " + e.getMessage());
      return false;
    }
  }

  private boolean isSupportKnoxContainer() {
    return true;
  }

  private boolean isSupportKnoxMdm() {
    return true;
  }
}
