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

import com.samsung.android.ims.settings.SemImsProfile;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
    private final ArrayMap<SemImsRegistrationListener, ImsRegistrationListenerDelegate>
            mRegListeners = new ArrayMap<>();
    private final ArrayMap<SemEpdgListener, SemEpdgListenerDelegate> mEpdgListeners =
            new ArrayMap<>();
    private final ArrayMap<SemSimMobilityStatusListener, SimMobilityStatusListenerDelegate>
            mSimMobilityStatusListeners = new ArrayMap<>();
    private BroadcastReceiver mRestartReceiver = null;
    DmConfigEventRelay mEventRelay = null;
    SemImsDmConfigListener.Stub mEventProxy =
            new SemImsDmConfigListener
                    .Stub() { // from class: com.samsung.android.ims.SemImsManager.2
                @Override // com.samsung.android.ims.SemImsDmConfigListener
                public void onChangeDmValue(String uri, boolean state) throws RemoteException {
                    if (SemImsManager.this.mEventRelay == null) {
                        Log.d(
                                "semImsManager["
                                        + SemImsManager.this.mPhoneId
                                        + NavigationBarInflaterView.SIZE_MOD_END,
                                "no listener for SemImsDmConfigListener");
                        throw new RemoteException();
                    }
                    Log.d(
                            "semImsManager["
                                    + SemImsManager.this.mPhoneId
                                    + NavigationBarInflaterView.SIZE_MOD_END,
                            "mEventRelay : " + SemImsManager.this.mEventRelay);
                    SemImsManager.this.mEventRelay.onChangeDmValue(uri, state);
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

    public SemImsManager(Context context, ImsServiceConnectionListener listener, int phoneId) {
        this.mContext = null;
        this.mListener = null;
        this.mPhoneId = 0;
        this.mContext = context;
        this.mListener = listener;
        this.mPhoneId = phoneId;
    }

    public SemImsService getImsService() {
        return SemImsService.Stub.asInterface(getSystemService(SERVICE_NAME));
    }

    private IBinder getSystemService(String name) {
        Object result;
        try {
            Class sm = Class.forName("android.os.ServiceManager");
            Method getService = sm.getMethod("getService", String.class);
            result = getService.invoke(sm, name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
        }
        if (result != null) {
            IBinder binder = (IBinder) result;
            return binder;
        }
        Log.d(LOG_TAG, "Failed to getService " + name);
        return null;
    }

    public void registerPreviousListeners(SemImsService imsService) {
        Log.d(
                LOG_TAG,
                "registerPreviousListeners:  mRegListeners: "
                        + this.mRegListeners.size()
                        + " mEpdgListeners: "
                        + this.mEpdgListeners.size());
        for (SemImsRegistrationListener tempListener : this.mRegListeners.keySet()) {
            registerImsRegistrationListener(tempListener);
        }
        for (SemEpdgListener epdgListener : this.mEpdgListeners.keySet()) {
            registerEpdgListener(epdgListener);
        }
        for (SemSimMobilityStatusListener tempListener2 :
                this.mSimMobilityStatusListeners.keySet()) {
            registerSimMobilityStatusListener(tempListener2);
        }
    }

    public void connectService() {
        if (this.mRestartReceiver == null) {
            Log.d(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "Register Receiver for Restart");
            this.mRestartReceiver =
                    new BroadcastReceiver() { // from class: com.samsung.android.ims.SemImsManager.1
                        @Override // android.content.BroadcastReceiver
                        public void onReceive(Context context, Intent intent) {
                            Log.d(
                                    "semImsManager["
                                            + SemImsManager.this.mPhoneId
                                            + NavigationBarInflaterView.SIZE_MOD_END,
                                    "onReceive " + intent.getAction());
                            if (TextUtils.equals(
                                    intent.getAction(),
                                    SemImsManager.INTENT_ACTION_IMSSERVICE_RESTART)) {
                                SemImsService imsService = SemImsManager.this.getImsService();
                                if (imsService == null) {
                                    Log.e(
                                            "semImsManager["
                                                    + SemImsManager.this.mPhoneId
                                                    + NavigationBarInflaterView.SIZE_MOD_END,
                                            "ImsService not found, this should not happen!");
                                    return;
                                }
                                SemImsManager.this.registerPreviousListeners(
                                        SemImsManager.this.getImsService());
                                if (SemImsManager.this.mListener != null) {
                                    SemImsManager.this.mListener.onConnected();
                                }
                            }
                        }
                    };
            IntentFilter filter = new IntentFilter();
            filter.addAction(INTENT_ACTION_IMSSERVICE_RESTART);
            this.mContext.registerReceiver(this.mRestartReceiver, filter);
        }
        SemImsService imsService = getImsService();
        if (imsService != null && this.mListener != null) {
            this.mListener.onConnected();
        }
    }

    public void disconnectService() {
        if (this.mRestartReceiver != null) {
            try {
                this.mContext.unregisterReceiver(this.mRestartReceiver);
            } catch (IllegalArgumentException e) {
                Log.e(
                        "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                        "unregisterReceiver " + e.getMessage());
            }
            this.mRestartReceiver = null;
        }
        if (this.mListener != null) {
            this.mListener.onDisconnected();
        }
    }

    public SemImsRegistration[] getRegistrationInfo() {
        Log.d(
                "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                "getRegistrationInfo");
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "getRegistrationInfo: Not initialized.");
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

    public boolean isRcsEnabled(boolean needAutoConfigCheck) {
        Log.d(
                "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                "isRcsEnabled: ");
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.d(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "isRcsEnabled: not connected.");
            return false;
        }
        try {
            return imsService.isRcsEnabled(needAutoConfigCheck, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void enableIpme(boolean enable) {
        Log.d(
                "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                "enableIpme: " + enable);
        enableRcs(enable);
    }

    public boolean isIpmeEnabled() {
        Log.d(
                "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                "isIpmeEnabled");
        return isRcsEnabled();
    }

    public void sendTryRegister() {
        Log.d(
                "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                "sendTryRegister");
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "sendTryRegister: Not initialized.");
            return;
        }
        try {
            imsService.sendTryRegisterByPhoneId(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void enableRcs(boolean enable) {
        Log.d(
                "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                "enableRcs: " + enable);
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "enableRcs: Not initialized.");
            return;
        }
        try {
            imsService.enableRcsByPhoneId(enable, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void registerImsRegistrationListener(SemImsRegistrationListener listener) {
        Log.d(
                "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                "registerImsRegistrationListener");
        if (listener == null) {
            Log.d(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "registerImsRegistrationListener : listener is null");
            return;
        }
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "Not initialized.");
            return;
        }
        if (this.mRegListeners.containsKey(listener)) {
            unregisterImsRegistrationListener(listener);
        }
        ImsRegistrationListenerDelegate callback = new ImsRegistrationListenerDelegate(listener);
        try {
            String token =
                    imsService.registerImsRegistrationListenerForSlot(callback, this.mPhoneId);
            if (!TextUtils.isEmpty(token)) {
                callback.mToken = token;
                this.mRegListeners.put(listener, callback);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unregisterImsRegistrationListener(SemImsRegistrationListener listener) {
        Log.d(
                "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                "unregisterImsRegistrationListener");
        if (listener == null) {
            Log.d(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "unregisterImsRegistrationListener : listener is null");
            return;
        }
        ImsRegistrationListenerDelegate delegate = this.mRegListeners.remove(listener);
        if (delegate == null) {
            Log.d(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "unregisterImsRegistrationListener : cannot find the listener");
            return;
        }
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "Not initialized.");
            return;
        }
        try {
            imsService.unregisterImsRegistrationListenerForSlot(delegate.mToken, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public boolean registerEpdgListener(SemEpdgListener listener) {
        Log.d(LOG_TAG, "registerEpdgListener");
        if (listener == null) {
            Log.e(LOG_TAG, "registerEpdgListener listener null");
            return false;
        }
        if (this.mEpdgListeners.containsKey(listener)) {
            unRegisterEpdgListener(listener);
        }
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(LOG_TAG, "Not initialized.");
            return false;
        }
        SemEpdgListenerDelegate callback = new SemEpdgListenerDelegate(listener);
        try {
            String token = imsService.registerEpdgListener(callback);
            if (!TextUtils.isEmpty(token)) {
                callback.mToken = token;
                this.mEpdgListeners.put(listener, callback);
                return true;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void registerSimMobilityStatusListener(SemSimMobilityStatusListener listener) {
        if (listener == null) {
            Log.e(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "registerSimMobilityStatusListener : wrong instance");
            return;
        }
        if (this.mSimMobilityStatusListeners.containsKey(listener)) {
            unregisterSimMobilityStatusListener(listener);
        }
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "Not initialized.");
            return;
        }
        SimMobilityStatusListenerDelegate callback =
                new SimMobilityStatusListenerDelegate(listener);
        try {
            String token = imsService.registerSimMobilityStatusListener(callback, this.mPhoneId);
            if (!TextUtils.isEmpty(token)) {
                callback.mToken = token;
                this.mSimMobilityStatusListeners.put(listener, callback);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unregisterSimMobilityStatusListener(SemSimMobilityStatusListener listener) {
        if (listener == null) {
            Log.d(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "unregisterSimMobilityStatusListener : listener is null");
            return;
        }
        SimMobilityStatusListenerDelegate delegate =
                this.mSimMobilityStatusListeners.remove(listener);
        if (delegate == null) {
            Log.d(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "unregisterSimMobilityStatusListener : cannot find the listener");
            return;
        }
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "Not initialized.");
            return;
        }
        try {
            imsService.unregisterSimMobilityStatusListener(delegate.mToken, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public boolean unRegisterEpdgListener(SemEpdgListener listener) {
        Log.d(LOG_TAG, "unRegisterEpdgListener");
        if (listener == null) {
            return false;
        }
        SemEpdgListenerDelegate delegate = this.mEpdgListeners.remove(listener);
        if (delegate == null) {
            Log.d(LOG_TAG, "unRegisterEpdgListener : cannot find the listener");
            return false;
        }
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(LOG_TAG, "Not initialized.");
            return false;
        }
        try {
            imsService.unRegisterEpdgListener(delegate.mToken);
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public SemImsRegistration getRegistrationInfoByServiceType(String serviceType) {
        Log.d(
                "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                "getRegistrationInfoByServiceType");
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "getRegistrationInfoByServiceType: Not initialized.");
            return null;
        }
        try {
            return imsService.getRegistrationInfoByServiceType(serviceType, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public SemImsRegistration getImsRegistration() {
        Log.d(
                "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                "getImsRegistration");
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "getImsRegistration: Not initialized.");
            return null;
        }
        try {
            return imsService.getRegistrationInfoByServiceType("volte", this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ContentValues getConfigValues(String[] fields) {
        Log.d(
                "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                "getConfigValues");
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "Not initialized.");
            return null;
        }
        try {
            return imsService.getConfigValues(fields, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isSimMobilityActivated() {
        Log.d(
                "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                "isSimMobilityActivated:");
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.d(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "isSimMobilityActivated: not connected.");
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
        Log.d(
                "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                "isForbidden");
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "Not initialized.");
            return false;
        }
        try {
            return imsService.isForbiddenByPhoneId(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isServiceAvailable(String service) {
        Log.d(
                "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                "isServiceAvailable: " + service);
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.d(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "isServiceAvailable: not connected.");
            return false;
        }
        try {
            return imsService.isServiceAvailable(service, -1, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isNonVerifiedMno() {
        Log.i(
                "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                "isNonVerifiedMno");
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.i(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "isNonVerifiedMno: not connected.");
            return false;
        }
        try {
            return imsService.isNonVerifiedMno(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isServiceAvailable(String service, int rat) {
        Log.d(
                "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                "isServiceAvailable: " + service + ", " + rat);
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.d(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "isServiceAvailable: not connected.");
            return false;
        }
        try {
            return imsService.isServiceAvailable(service, rat, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isImsFeatureEnabled(String feature, int network)
            throws IllegalArgumentException {
        Log.d(
                "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                "isImsFeatureEnabled: " + feature + ", " + network);
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.d(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "isImsFeatureEnabled: not connected.");
            return false;
        }
        if (!SemImsProfile.ImsFeature.isValidImsFeature(feature)) {
            throw new IllegalArgumentException("invalid feature : " + feature);
        }
        try {
            return imsService.isServiceAvailable(feature, network, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getRcsProfileType() {
        Log.d(
                "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                "getRcsProfileType");
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "Not initialized.");
            return "";
        }
        try {
            String rcsProfile = imsService.getRcsProfileType(this.mPhoneId);
            return rcsProfile;
        } catch (RemoteException e) {
            Log.e(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "fail to get profile");
            return "";
        }
    }

    public boolean isVoLteAvailable() {
        Log.d(
                "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                "isVoLteAvailable");
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.d(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "isVoLteAvailable : not connected.");
            return false;
        }
        try {
            return imsService.isVoLteAvailable(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isImsFeatureProvisioned(String feature) throws IllegalArgumentException {
        Log.d(
                "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                "isImsFeatureProvisioned: " + feature);
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.d(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "isImsFeatureProvisioned: not connected.");
            return false;
        }
        if (!SemImsProfile.ImsFeature.isValidImsFeature(feature)) {
            throw new IllegalArgumentException("invalid feature : " + feature);
        }
        try {
            return imsService.getBooleanConfig(feature, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getImsVersion() {
        return IMS_PLATFORM_VERSION;
    }

    public static int getImsApiVersion() {
        return 2;
    }

    public void registerDmValueListener(DmConfigEventRelay listener) {
        if (listener == null) {
            Log.e(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "listener is null");
            return;
        }
        this.mEventRelay = listener;
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "Not initialized.");
            return;
        }
        try {
            imsService.registerDmValueListener(this.mEventProxy);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unregisterDmValueListener(DmConfigEventRelay listener) {
        this.mEventRelay = null;
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "Not initialized.");
            return;
        }
        try {
            imsService.unregisterDmValueListener(this.mEventProxy);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public SemImsProfile[] getCurrentProfile(int phoneId) {
        Log.d(
                "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                "getCurrentProfile");
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "Not initialized.");
            return null;
        }
        try {
            SemImsProfile[] profiles = imsService.getCurrentProfileForSlot(phoneId);
            return profiles;
        } catch (RemoteException e) {
            Log.e(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "fail to get profiles");
            return null;
        }
    }

    public void setRttMode(int mode) {
        Log.d(
                "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                "setRttMode: " + mode);
        SemImsService imsService = getImsService();
        if (imsService == null) {
            Log.d(
                    "semImsManager[" + this.mPhoneId + NavigationBarInflaterView.SIZE_MOD_END,
                    "setRttMode: not connected.");
            return;
        }
        try {
            imsService.setRttMode(this.mPhoneId, mode);
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

        public ImsRegistrationListenerDelegate(SemImsRegistrationListener listener) {
            this.mListener = new WeakReference<>(listener);
        }

        public SemImsRegistrationListener getListener() {
            if (this.mListener != null) {
                return this.mListener.get();
            }
            return null;
        }

        @Override // com.samsung.android.ims.SemImsRegiListener
        public void onRegistered(SemImsRegistration reg) throws RemoteException {
            SemImsRegistrationListener imsRegistrationListener = getListener();
            if (imsRegistrationListener != null) {
                imsRegistrationListener.onRegistered(reg);
            }
        }

        @Override // com.samsung.android.ims.SemImsRegiListener
        public void onDeregistered(
                SemImsRegistration reg, SemImsRegistrationError registrationError)
                throws RemoteException {
            SemImsRegistrationListener imsRegistrationListener = getListener();
            if (imsRegistrationListener != null) {
                imsRegistrationListener.onDeregistered(reg, registrationError);
            }
        }
    }

    private static class SemEpdgListenerDelegate extends ISemEpdgListener.Stub {
        private WeakReference<SemEpdgListener> mListener;
        String mToken = null;

        public SemEpdgListenerDelegate(SemEpdgListener listener) {
            this.mListener = new WeakReference<>(listener);
        }

        public SemEpdgListener getListener() {
            if (this.mListener != null) {
                return this.mListener.get();
            }
            return null;
        }

        @Override // com.samsung.android.ims.ISemEpdgListener
        public void onEpdgAvailable(int phoneId, boolean isAvailable, int wifiState) {
            Log.d(SemImsManager.LOG_TAG, "onEpdgAvailable phoneId " + phoneId);
            SemEpdgListener epdgListener = getListener();
            if (epdgListener != null) {
                epdgListener.onEpdgAvailable(phoneId, isAvailable, wifiState);
            }
        }

        @Override // com.samsung.android.ims.ISemEpdgListener
        public void onHandoverResult(int phoneId, int isL2WHandover, int result, String apnType) {
            Log.d(SemImsManager.LOG_TAG, "onHandoverResult.phoneId " + phoneId);
            SemEpdgListener epdgListener = getListener();
            if (epdgListener != null) {
                epdgListener.onHandoverResult(phoneId, isL2WHandover, result, apnType);
            }
        }

        @Override // com.samsung.android.ims.ISemEpdgListener
        public void onIpsecConnection(
                int phoneId, String apnType, int ikeError, int throttleCount) {
            Log.d(SemImsManager.LOG_TAG, "onIpsecConnection phoneId " + phoneId);
            SemEpdgListener epdgListener = getListener();
            if (epdgListener != null) {
                epdgListener.onIpsecConnection(phoneId, apnType, ikeError, throttleCount);
            }
        }

        @Override // com.samsung.android.ims.ISemEpdgListener
        public void onIpsecDisconnection(int phoneId, String apnType) {
            Log.d(SemImsManager.LOG_TAG, "onIpsecDisconnection phoneId " + phoneId);
            SemEpdgListener epdgListener = getListener();
            if (epdgListener != null) {
                epdgListener.onIpsecDisconnection(phoneId, apnType);
            }
        }

        @Override // com.samsung.android.ims.ISemEpdgListener
        public void onEpdgShowPopup(int phoneId, int popupType) {
            Log.d(SemImsManager.LOG_TAG, "onEpdgShowPopup phoneId " + phoneId);
            SemEpdgListener epdgListener = getListener();
            if (epdgListener != null) {
                epdgListener.onEpdgShowPopup(phoneId, popupType);
            }
        }
    }

    private static class SimMobilityStatusListenerDelegate extends SemSimMobStatusListener.Stub {
        private WeakReference<SemSimMobilityStatusListener> mListener;
        String mToken = null;

        public SimMobilityStatusListenerDelegate(SemSimMobilityStatusListener listener) {
            this.mListener = new WeakReference<>(listener);
        }

        public SemSimMobilityStatusListener getListener() {
            if (this.mListener != null) {
                return this.mListener.get();
            }
            return null;
        }

        @Override // com.samsung.android.ims.SemSimMobStatusListener
        public void onSimMobilityStateChanged(boolean event) throws RemoteException {
            SemSimMobilityStatusListener simMobilityStatusListener = getListener();
            if (simMobilityStatusListener != null) {
                simMobilityStatusListener.onSimMobilityStateChanged(event);
            }
        }
    }
}
