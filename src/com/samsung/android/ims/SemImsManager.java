package com.samsung.android.ims;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import com.samsung.android.ims.ISemEpdgListener;
import com.samsung.android.ims.SemImsDmConfigListener;
import com.samsung.android.ims.SemImsRegiListener;
import com.samsung.android.ims.SemImsService;
import com.samsung.android.ims.SemSimMobStatusListener;
import com.samsung.android.ims.settings.SemImsProfile;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class SemImsManager {
    public static final int HANDOVER_FAIL = 0;
    public static final int HANDOVER_L2W = 1;
    public static final int HANDOVER_SUCCESS = 1;
    public static final int HANDOVER_W2L = 0;
    private static final int IMS_API_VERSION = 2;
    private static final int IMS_PLATFORM_VERSION = 60400;
    private static final String INTENT_ACTION_IMSSERVICE_RESTART = "com.sec.ims.imsmanager.RESTART";
    private static final String LOG_TAG = "semImsManager";
    private static final String SERVICE_NAME = "ImsBase";
    public static final int WIFI_CONNECTED = 1;
    public static final int WIFI_DISCONNECTED = 0;
    private Context mContext;
    private ImsServiceConnectionListener mListener;
    private int mPhoneId;
    private final ArrayMap<SemImsRegistrationListener, ImsRegistrationListenerDelegate> mRegListeners = new ArrayMap<>();
    private final ArrayMap<SemEpdgListener, SemEpdgListenerDelegate> mEpdgListeners = new ArrayMap<>();
    private final ArrayMap<SemSimMobilityStatusListener, SimMobilityStatusListenerDelegate> mSimMobilityStatusListeners = new ArrayMap<>();
    private BroadcastReceiver mRestartReceiver = null;
    DmConfigEventRelay mEventRelay = null;
    SemImsDmConfigListener.Stub mEventProxy = new SemImsDmConfigListener.Stub() { // from class: com.samsung.android.ims.SemImsManager.2
        @Override // com.samsung.android.ims.SemImsDmConfigListener
        public void onChangeDmValue(String str, boolean z) throws RemoteException {
            if (SemImsManager.this.mEventRelay == null) {
                Log.d("semImsManager[" + SemImsManager.this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "no listener for SemImsDmConfigListener");
                throw new RemoteException();
            }
            Log.d("semImsManager[" + SemImsManager.this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "mEventRelay : " + SemImsManager.this.mEventRelay);
            SemImsManager.this.mEventRelay.onChangeDmValue(str, z);
        }
    };

    public static class ApnType {
        public static final String CBS = "cbs";
        public static final String EMERGENCY = "emergency";
        public static final String IMS = "ims";
        public static final String INTERNET = "default";
        public static final String MMS = "mms";
        public static final String XCAP = "xcap";
    }

    public interface DmConfigEventRelay {
        void onChangeDmValue(String str, boolean z);
    }

    public static class EpdgPopUpTypes {
        public static final int CANNOT_SWITCH_TO_WIFI = 1;
        public static final int LOW_WIFI_SIGNAL = 2;
        public static final int TURN_OFF_MOBILE_DATA = 3;
        public static final int WFC_DROP_WARNING_NOTI = 4;
    }

    public static class IkeErrors {
        public static final int AUTHENTICATION_FAILED = 24;
        public static final int INTERNAL_ADDRESS_FAILURE = 36;
        public static final int INVALID_SYNTAX = 7;
        public static final int MAX_CONNECTION_REACHED = 8193;
        public static final int NETWORK_TOO_BUSY = 10000;
        public static final int NO_PROPOSAL_CHOSEN = 14;
        public static final int NO_SUBSCRIPTION = 9000;
        public static final int PDN_CONNECTION_REJECTION = 8192;
        public static final int TEMPORARY_FAILURE = 43;
    }

    public static class ImsReason {
        public static final int ACTIVE_CALL_ON_ANOTHER_SOFTPHONE = 3007;
        public static final int ADDRESS_INCOMPLETE = 484;
        public static final int ALTERNATIVE_SERVICES = 380;
        public static final int BAD_EXTENSION = 420;
        public static final int BUSY_HERE = 486;
        public static final int CALL_END_CALL_NW_HANDOVER = 1107;
        public static final int CALL_FORBIDDEN = 2001;
        public static final int CALL_FORBIDDEN_RSN_EXPIRED = 2302;
        public static final int CALL_FORBIDDEN_RSN_GROUP_CALL_SERVICE_UNAVAILABLE = 2303;
        public static final int CALL_FORBIDDEN_RSN_OUTGOING_CALLS_IMPOSSIBLE = 2305;
        public static final int CALL_FORBIDDEN_RSN_TEMPORARY_DISABILITY = 2304;
        public static final int CALL_HAS_BEEN_TRANSFERRED_TO_ANOTHER_DEVICE = 3002;
        public static final int CALL_INVITE_TIMEOUT = 1114;
        public static final int CALL_SESSION_TIMEOUT = 1103;
        public static final int CANCEL_CALL_COMPLETED_ELSEWHERE = 3001;
        public static final int CANCEL_SERVICE_NOT_ALLOWED_IN_THIS_LOCATION = 3004;
        public static final int CODE_ANSWERED_ELSEWHERE = 1014;
        public static final int CODE_CALL_END_CAUSE_CALL_PULL = 1016;
        public static final int CODE_MEDIA_NO_DATA = 402;
        public static final int CODE_SIP_BAD_ADDRESS = 337;
        public static final int CODE_SIP_BUSY = 338;
        public static final int CODE_SIP_CLIENT_ERROR = 342;
        public static final int CODE_SIP_FORBIDDEN = 332;
        public static final int CODE_SIP_NOT_ACCEPTABLE = 340;
        public static final int CODE_SIP_NOT_FOUND = 333;
        public static final int CODE_SIP_NOT_SUPPORTED = 334;
        public static final int CODE_SIP_REQUEST_CANCELLED = 339;
        public static final int CODE_SIP_REQUEST_TIMEOUT = 335;
        public static final int CODE_SIP_SERVER_INTERNAL_ERROR = 351;
        public static final int CODE_SIP_SERVER_TIMEOUT = 353;
        public static final int CODE_SIP_SERVICE_UNAVAILABLE = 352;
        public static final int CODE_SIP_USER_REJECTED = 361;
        public static final int CODE_WIFI_LOST = 1407;
        public static final int DATA_CONNECTION_LOST = 1701;
        public static final int DECLINE = 603;
        public static final int DOES_NOT_EXIST_ANYWHERE = 604;
        public static final int EMERGENCY_CALLS_OVER_WIFI_NOT_ALLOWED = 3008;
        public static final int FORBIDDEN_MULTI_CALL_LIMITATION = 2510;
        public static final int FORBIDDEN_SERVICE_NOT_ALLOWED_IN_THIS_LOCATION = 3003;
        public static final int LINE_IN_USE_ON_OTHER_DEVICE = 2413;
        public static final int MAKECALL_REG_FAILURE_GENERAL = 2005;
        public static final int MAKECALL_REG_FAILURE_REG_403 = 2003;
        public static final int MAKECALL_REG_FAILURE_REG_423 = 2004;
        public static final int MAKECALL_REG_FAILURE_TIMER_F = 2002;
        public static final int METHOD_NOT_ALLOWED = 405;
        public static final int NETWORK_UNREACHABLE = 2102;
        public static final int NOT_ACCEPTABLE = 406;
        public static final int NOT_ACCEPTABLE2 = 606;
        public static final int NOT_ACCEPTABLE_HERE = 488;
        public static final int NOT_FOUND = 404;
        public static final int OTHER_SECONDARY_DEVICE_IN_USE = 3006;
        public static final int PULLED_BY_ANOTHER_DEVICE = 2506;
        public static final int REQUEST_TERMINATED = 487;
        public static final int REQUEST_TIMEOUT = 408;
        public static final int RTP_TIME_OUT = 1401;
        public static final int SERVER_INTERNAL_ERROR = 500;
        public static final int SERVER_TIME_OUT = 504;
        public static final int SERVICE_UNAVAILABLE = 503;
        public static final int SIMULTANEOUS_CALL_LIMIT_HAS_ALREADY_BEEN_REACHED = 3005;
        public static final int TEMPORARILY_UNAVAILABLE = 480;
        public static final int UNSUPPORTED_MEDIA_TYPE = 415;
        public static final int UNSUPPORTED_URI_SCHEME = 416;
        public static final int WIFI_CONNECTION_LOST = 1703;
    }

    public interface ImsServiceConnectionListener {
        void onConnected();

        void onDisconnected();
    }

    public static int getImsApiVersion() {
        return 2;
    }

    public static int getImsVersion() {
        return IMS_PLATFORM_VERSION;
    }

    public SemImsManager(Context context, ImsServiceConnectionListener imsServiceConnectionListener, int i) {
        this.mContext = null;
        this.mListener = null;
        this.mPhoneId = 0;
        this.mContext = context;
        this.mListener = imsServiceConnectionListener;
        this.mPhoneId = i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SemImsService getImsService() {
        return SemImsService.Stub.asInterface(getSystemService(SERVICE_NAME));
    }

    private IBinder getSystemService(String str) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        try {
            Class<?> cls = Class.forName("android.os.ServiceManager");
            Object objInvoke = cls.getMethod("getService", String.class).invoke(cls, str);
            if (objInvoke != null) {
                return (IBinder) objInvoke;
            }
            Log.d(LOG_TAG, "Failed to getService " + str);
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            return null;
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
            return null;
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void registerPreviousListeners(SemImsService semImsService) {
        Log.d(LOG_TAG, "registerPreviousListeners:  mRegListeners: " + this.mRegListeners.size() + " mEpdgListeners: " + this.mEpdgListeners.size());
        Iterator<SemImsRegistrationListener> it = this.mRegListeners.keySet().iterator();
        while (it.hasNext()) {
            registerImsRegistrationListener(it.next());
        }
        Iterator<SemEpdgListener> it2 = this.mEpdgListeners.keySet().iterator();
        while (it2.hasNext()) {
            registerEpdgListener(it2.next());
        }
        Iterator<SemSimMobilityStatusListener> it3 = this.mSimMobilityStatusListeners.keySet().iterator();
        while (it3.hasNext()) {
            registerSimMobilityStatusListener(it3.next());
        }
    }

    public void connectService() {
        ImsServiceConnectionListener imsServiceConnectionListener;
        if (this.mRestartReceiver == null) {
            Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "Register Receiver for Restart");
            this.mRestartReceiver = new BroadcastReceiver() { // from class: com.samsung.android.ims.SemImsManager.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    Log.d("semImsManager[" + SemImsManager.this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "onReceive " + intent.getAction());
                    if (TextUtils.equals(intent.getAction(), SemImsManager.INTENT_ACTION_IMSSERVICE_RESTART)) {
                        if (SemImsManager.this.getImsService() == null) {
                            Log.e("semImsManager[" + SemImsManager.this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "ImsService not found, this should not happen!");
                            return;
                        }
                        SemImsManager semImsManager = SemImsManager.this;
                        semImsManager.registerPreviousListeners(semImsManager.getImsService());
                        if (SemImsManager.this.mListener != null) {
                            SemImsManager.this.mListener.onConnected();
                        }
                    }
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(INTENT_ACTION_IMSSERVICE_RESTART);
            this.mContext.registerReceiver(this.mRestartReceiver, intentFilter);
        }
        if (getImsService() == null || (imsServiceConnectionListener = this.mListener) == null) {
            return;
        }
        imsServiceConnectionListener.onConnected();
    }

    public void disconnectService() {
        BroadcastReceiver broadcastReceiver = this.mRestartReceiver;
        if (broadcastReceiver != null) {
            try {
                this.mContext.unregisterReceiver(broadcastReceiver);
            } catch (IllegalArgumentException e) {
                Log.e("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "unregisterReceiver " + e.getMessage());
            }
            this.mRestartReceiver = null;
        }
        ImsServiceConnectionListener imsServiceConnectionListener = this.mListener;
        if (imsServiceConnectionListener != null) {
            imsServiceConnectionListener.onDisconnected();
        }
    }

    public SemImsRegistration[] getRegistrationInfo() {
        Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "getRegistrationInfo");
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "getRegistrationInfo: Not initialized.");
            return null;
        }
        try {
            return imsService.getRegistrationInfoByPhoneId(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isRcsEnabled() {
        return isRcsEnabled(true);
    }

    public boolean isRcsEnabled(boolean z) {
        Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "isRcsEnabled: ");
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "isRcsEnabled: not connected.");
            return false;
        }
        try {
            return imsService.isRcsEnabled(z, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void enableIpme(boolean z) {
        Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "enableIpme: " + z);
        enableRcs(z);
    }

    public boolean isIpmeEnabled() {
        Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "isIpmeEnabled");
        return isRcsEnabled();
    }

    public void sendTryRegister() {
        Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "sendTryRegister");
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "sendTryRegister: Not initialized.");
            return;
        }
        try {
            imsService.sendTryRegisterByPhoneId(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void enableRcs(boolean z) {
        Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "enableRcs: " + z);
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "enableRcs: Not initialized.");
            return;
        }
        try {
            imsService.enableRcsByPhoneId(z, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void registerImsRegistrationListener(SemImsRegistrationListener semImsRegistrationListener) {
        Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "registerImsRegistrationListener");
        if (semImsRegistrationListener == null) {
            Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "registerImsRegistrationListener : listener is null");
            return;
        }
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "Not initialized.");
            return;
        }
        if (this.mRegListeners.containsKey(semImsRegistrationListener)) {
            unregisterImsRegistrationListener(semImsRegistrationListener);
        }
        ImsRegistrationListenerDelegate imsRegistrationListenerDelegate = new ImsRegistrationListenerDelegate(semImsRegistrationListener);
        try {
            String strRegisterImsRegistrationListenerForSlot = imsService.registerImsRegistrationListenerForSlot(imsRegistrationListenerDelegate, this.mPhoneId);
            if (TextUtils.isEmpty(strRegisterImsRegistrationListenerForSlot)) {
                return;
            }
            imsRegistrationListenerDelegate.mToken = strRegisterImsRegistrationListenerForSlot;
            this.mRegListeners.put(semImsRegistrationListener, imsRegistrationListenerDelegate);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unregisterImsRegistrationListener(SemImsRegistrationListener semImsRegistrationListener) {
        Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "unregisterImsRegistrationListener");
        if (semImsRegistrationListener == null) {
            Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "unregisterImsRegistrationListener : listener is null");
            return;
        }
        ImsRegistrationListenerDelegate imsRegistrationListenerDelegateRemove = this.mRegListeners.remove(semImsRegistrationListener);
        if (imsRegistrationListenerDelegateRemove == null) {
            Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "unregisterImsRegistrationListener : cannot find the listener");
            return;
        }
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "Not initialized.");
            return;
        }
        try {
            imsService.unregisterImsRegistrationListenerForSlot(imsRegistrationListenerDelegateRemove.mToken, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public boolean registerEpdgListener(SemEpdgListener semEpdgListener) {
        Log.d(LOG_TAG, "registerEpdgListener");
        if (semEpdgListener == null) {
            Log.e(LOG_TAG, "registerEpdgListener listener null");
            return false;
        }
        if (this.mEpdgListeners.containsKey(semEpdgListener)) {
            unRegisterEpdgListener(semEpdgListener);
        }
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(LOG_TAG, "Not initialized.");
            return false;
        }
        SemEpdgListenerDelegate semEpdgListenerDelegate = new SemEpdgListenerDelegate(semEpdgListener);
        try {
            String strRegisterEpdgListener = imsService.registerEpdgListener(semEpdgListenerDelegate);
            if (!TextUtils.isEmpty(strRegisterEpdgListener)) {
                semEpdgListenerDelegate.mToken = strRegisterEpdgListener;
                this.mEpdgListeners.put(semEpdgListener, semEpdgListenerDelegate);
                return true;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void registerSimMobilityStatusListener(SemSimMobilityStatusListener semSimMobilityStatusListener) {
        if (semSimMobilityStatusListener == null) {
            Log.e("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "registerSimMobilityStatusListener : wrong instance");
            return;
        }
        if (this.mSimMobilityStatusListeners.containsKey(semSimMobilityStatusListener)) {
            unregisterSimMobilityStatusListener(semSimMobilityStatusListener);
        }
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "Not initialized.");
            return;
        }
        SimMobilityStatusListenerDelegate simMobilityStatusListenerDelegate = new SimMobilityStatusListenerDelegate(semSimMobilityStatusListener);
        try {
            String strRegisterSimMobilityStatusListener = imsService.registerSimMobilityStatusListener(simMobilityStatusListenerDelegate, this.mPhoneId);
            if (TextUtils.isEmpty(strRegisterSimMobilityStatusListener)) {
                return;
            }
            simMobilityStatusListenerDelegate.mToken = strRegisterSimMobilityStatusListener;
            this.mSimMobilityStatusListeners.put(semSimMobilityStatusListener, simMobilityStatusListenerDelegate);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unregisterSimMobilityStatusListener(SemSimMobilityStatusListener semSimMobilityStatusListener) {
        if (semSimMobilityStatusListener == null) {
            Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "unregisterSimMobilityStatusListener : listener is null");
            return;
        }
        SimMobilityStatusListenerDelegate simMobilityStatusListenerDelegateRemove = this.mSimMobilityStatusListeners.remove(semSimMobilityStatusListener);
        if (simMobilityStatusListenerDelegateRemove == null) {
            Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "unregisterSimMobilityStatusListener : cannot find the listener");
            return;
        }
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "Not initialized.");
            return;
        }
        try {
            imsService.unregisterSimMobilityStatusListener(simMobilityStatusListenerDelegateRemove.mToken, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public boolean unRegisterEpdgListener(SemEpdgListener semEpdgListener) {
        Log.d(LOG_TAG, "unRegisterEpdgListener");
        if (semEpdgListener == null) {
            return false;
        }
        SemEpdgListenerDelegate semEpdgListenerDelegateRemove = this.mEpdgListeners.remove(semEpdgListener);
        if (semEpdgListenerDelegateRemove == null) {
            Log.d(LOG_TAG, "unRegisterEpdgListener : cannot find the listener");
            return false;
        }
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(LOG_TAG, "Not initialized.");
            return false;
        }
        try {
            imsService.unRegisterEpdgListener(semEpdgListenerDelegateRemove.mToken);
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public SemImsRegistration getRegistrationInfoByServiceType(String str) {
        Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "getRegistrationInfoByServiceType");
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "getRegistrationInfoByServiceType: Not initialized.");
            return null;
        }
        try {
            return imsService.getRegistrationInfoByServiceType(str, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public SemImsRegistration getImsRegistration() {
        Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "getImsRegistration");
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "getImsRegistration: Not initialized.");
            return null;
        }
        try {
            return imsService.getRegistrationInfoByServiceType("volte", this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ContentValues getConfigValues(String[] strArr) {
        Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "getConfigValues");
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "Not initialized.");
            return null;
        }
        try {
            return imsService.getConfigValues(strArr, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isSimMobilityActivated() {
        Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "isSimMobilityActivated:");
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "isSimMobilityActivated: not connected.");
            return false;
        }
        try {
            return imsService.isSimMobilityActivated(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isForbidden() {
        Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "isForbidden");
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "Not initialized.");
            return false;
        }
        try {
            return imsService.isForbiddenByPhoneId(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isServiceAvailable(String str) {
        Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "isServiceAvailable: " + str);
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "isServiceAvailable: not connected.");
            return false;
        }
        try {
            return imsService.isServiceAvailable(str, -1, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isNonVerifiedMno() {
        Log.i("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "isNonVerifiedMno");
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.i("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "isNonVerifiedMno: not connected.");
            return false;
        }
        try {
            return imsService.isNonVerifiedMno(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isServiceAvailable(String str, int i) {
        Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "isServiceAvailable: " + str + ", " + i);
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "isServiceAvailable: not connected.");
            return false;
        }
        try {
            return imsService.isServiceAvailable(str, i, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isImsFeatureEnabled(String str, int i) throws IllegalArgumentException {
        Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "isImsFeatureEnabled: " + str + ", " + i);
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "isImsFeatureEnabled: not connected.");
            return false;
        }
        if (!SemImsProfile.ImsFeature.isValidImsFeature(str)) {
            throw new IllegalArgumentException("invalid feature : " + str);
        }
        try {
            return imsService.isServiceAvailable(str, i, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getRcsProfileType() {
        Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "getRcsProfileType");
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "Not initialized.");
            return "";
        }
        try {
            return imsService.getRcsProfileType(this.mPhoneId);
        } catch (RemoteException unused) {
            Log.e("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "fail to get profile");
            return "";
        }
    }

    public boolean isVoLteAvailable() {
        Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "isVoLteAvailable");
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "isVoLteAvailable : not connected.");
            return false;
        }
        try {
            return imsService.isVoLteAvailable(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isImsFeatureProvisioned(String str) throws IllegalArgumentException {
        Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "isImsFeatureProvisioned: " + str);
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "isImsFeatureProvisioned: not connected.");
            return false;
        }
        if (!SemImsProfile.ImsFeature.isValidImsFeature(str)) {
            throw new IllegalArgumentException("invalid feature : " + str);
        }
        try {
            return imsService.getBooleanConfig(str, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void registerDmValueListener(DmConfigEventRelay dmConfigEventRelay) {
        if (dmConfigEventRelay != null) {
            this.mEventRelay = dmConfigEventRelay;
            SemImsService imsService = getImsService();
            if (imsService == null) {
                Log.e("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "Not initialized.");
                return;
            }
            try {
                imsService.registerDmValueListener(this.mEventProxy);
                return;
            } catch (RemoteException e) {
                e.printStackTrace();
                return;
            }
        }
        Log.e("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "listener is null");
    }

    public void unregisterDmValueListener(DmConfigEventRelay dmConfigEventRelay) {
        this.mEventRelay = null;
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "Not initialized.");
            return;
        }
        try {
            imsService.unregisterDmValueListener(this.mEventProxy);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public SemImsProfile[] getCurrentProfile(int i) {
        Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "getCurrentProfile");
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "Not initialized.");
            return null;
        }
        try {
            return imsService.getCurrentProfileForSlot(i);
        } catch (RemoteException unused) {
            Log.e("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "fail to get profiles");
            return null;
        }
    }

    public void setRttMode(int i) {
        Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "setRttMode: " + i);
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.d("semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END, "setRttMode: not connected.");
            return;
        }
        try {
            imsService.setRttMode(this.mPhoneId, i);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public boolean isCrossSimCallingRegistered() {
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(LOG_TAG, "Not initialized.");
            return false;
        }
        try {
            return imsService.isCrossSimCallingRegistered(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasCrossSimCallingSupport() {
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(LOG_TAG, "Not initialized.");
            return false;
        }
        try {
            return imsService.hasCrossSimCallingSupport(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static class ImsRegistrationListenerDelegate extends SemImsRegiListener.Stub {
        private WeakReference<SemImsRegistrationListener> mListener;
        String mToken = null;

        public ImsRegistrationListenerDelegate(SemImsRegistrationListener semImsRegistrationListener) {
            this.mListener = new WeakReference<>(semImsRegistrationListener);
        }

        public SemImsRegistrationListener getListener() {
            WeakReference<SemImsRegistrationListener> weakReference = this.mListener;
            if (weakReference != null) {
                return weakReference.get();
            }
            return null;
        }

        @Override // com.samsung.android.ims.SemImsRegiListener
        public void onRegistered(SemImsRegistration semImsRegistration) throws RemoteException {
            SemImsRegistrationListener listener = getListener();
            if (listener != null) {
                listener.onRegistered(semImsRegistration);
            }
        }

        @Override // com.samsung.android.ims.SemImsRegiListener
        public void onDeregistered(SemImsRegistration semImsRegistration, SemImsRegistrationError semImsRegistrationError) throws RemoteException {
            SemImsRegistrationListener listener = getListener();
            if (listener != null) {
                listener.onDeregistered(semImsRegistration, semImsRegistrationError);
            }
        }
    }

    private static class SemEpdgListenerDelegate extends ISemEpdgListener.Stub {
        private WeakReference<SemEpdgListener> mListener;
        String mToken = null;

        public SemEpdgListenerDelegate(SemEpdgListener semEpdgListener) {
            this.mListener = new WeakReference<>(semEpdgListener);
        }

        public SemEpdgListener getListener() {
            WeakReference<SemEpdgListener> weakReference = this.mListener;
            if (weakReference != null) {
                return weakReference.get();
            }
            return null;
        }

        @Override // com.samsung.android.ims.ISemEpdgListener
        public void onEpdgAvailable(int i, boolean z, int i2) {
            Log.d(SemImsManager.LOG_TAG, "onEpdgAvailable phoneId " + i);
            SemEpdgListener listener = getListener();
            if (listener != null) {
                listener.onEpdgAvailable(i, z, i2);
            }
        }

        @Override // com.samsung.android.ims.ISemEpdgListener
        public void onHandoverResult(int i, int i2, int i3, String str) {
            Log.d(SemImsManager.LOG_TAG, "onHandoverResult.phoneId " + i);
            SemEpdgListener listener = getListener();
            if (listener != null) {
                listener.onHandoverResult(i, i2, i3, str);
            }
        }

        @Override // com.samsung.android.ims.ISemEpdgListener
        public void onIpsecConnection(int i, String str, int i2, int i3) {
            Log.d(SemImsManager.LOG_TAG, "onIpsecConnection phoneId " + i);
            SemEpdgListener listener = getListener();
            if (listener != null) {
                listener.onIpsecConnection(i, str, i2, i3);
            }
        }

        @Override // com.samsung.android.ims.ISemEpdgListener
        public void onIpsecDisconnection(int i, String str) {
            Log.d(SemImsManager.LOG_TAG, "onIpsecDisconnection phoneId " + i);
            SemEpdgListener listener = getListener();
            if (listener != null) {
                listener.onIpsecDisconnection(i, str);
            }
        }

        @Override // com.samsung.android.ims.ISemEpdgListener
        public void onEpdgShowPopup(int i, int i2) {
            Log.d(SemImsManager.LOG_TAG, "onEpdgShowPopup phoneId " + i);
            SemEpdgListener listener = getListener();
            if (listener != null) {
                listener.onEpdgShowPopup(i, i2);
            }
        }
    }

    private static class SimMobilityStatusListenerDelegate extends SemSimMobStatusListener.Stub {
        private WeakReference<SemSimMobilityStatusListener> mListener;
        String mToken = null;

        public SimMobilityStatusListenerDelegate(SemSimMobilityStatusListener semSimMobilityStatusListener) {
            this.mListener = new WeakReference<>(semSimMobilityStatusListener);
        }

        public SemSimMobilityStatusListener getListener() {
            WeakReference<SemSimMobilityStatusListener> weakReference = this.mListener;
            if (weakReference != null) {
                return weakReference.get();
            }
            return null;
        }

        @Override // com.samsung.android.ims.SemSimMobStatusListener
        public void onSimMobilityStateChanged(boolean z) throws RemoteException {
            SemSimMobilityStatusListener listener = getListener();
            if (listener != null) {
                listener.onSimMobilityStateChanged(z);
            }
        }
    }
}
