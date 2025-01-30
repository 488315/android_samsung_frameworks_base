package com.sec.ims;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.core.widget.NestedScrollView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.sec.ims.IEpdgListener;
import com.sec.ims.IImsDmConfigListener;
import com.sec.ims.IImsService;
import com.sec.ims.cmc.CmcCallInfo;
import com.sec.ims.p052ft.IImsOngoingFtEventListener;
import com.sec.ims.p053im.IImSessionListener;
import com.sec.ims.settings.ImsProfile;
import com.sec.ims.settings.RcsConfigurationReader;
import com.sec.ims.util.IMSLog;
import com.sec.ims.volte2.IImsVideoListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class ImsManager {
    private static final int IMS_API_VERSION = 2;
    private static final int IMS_PLATFORM_VERSION = 60400;
    public static final String INTENT_ACTION_IMSSERVICE_RESTART = "com.sec.ims.imsmanager.RESTART";
    public static final String INTENT_ACTION_RCS_ENABLE = "android.intent.action.RCS_ENABLE";
    public static final String INTENT_PARAM_IPME_ENABLE = "IPME_ENABLE";
    public static final String INTENT_PARAM_RCS_ENABLE = "RCS_ENABLE";
    public static final String INTENT_PARAM_RCS_ENABLE_TYPE = "action_type";
    public static final String INTENT_VALUE_RCS_ENABLE_TYPE_ALL_RCS = "ALL_RCS";
    public static final String INTENT_VALUE_RCS_ENABLE_TYPE_IPME = "IPME";
    static final String LOG_TAG = "legacyImsManager";
    private static final String SERVICE_NAME = "secims";
    public static final String VOLTE = "volte";
    private final ArrayMap<IAutoConfigurationListener, String> mAutoConfigurationListener;
    private final ArrayMap<IImsRegistrationListener, String> mCmcRegListeners;
    private final Context mContext;
    private final ArrayMap<IDialogEventListener, String> mDialogListeners;
    private final ArrayMap<IEpdgListener, String> mEpdgListeners;
    IImsDmConfigListener.Stub mEventProxy;
    DmConfigEventRelay mEventRelay;
    private final ArrayMap<IImSessionListener, String> mImSessionListeners;
    private ConnectionListener mListener;
    private final ArrayMap<IImsOngoingFtEventListener, String> mOngoingFtEventListeners;
    private int mPhoneId;
    private final ArrayMap<IImsRegistrationListener, String> mRegListeners;
    private BroadcastReceiver mRestartReceiver;
    private final ArrayMap<IRttEventListener, String> mRttListeners;
    private boolean mServiceBound;
    private final ArrayMap<ISimMobilityStatusListener, String> mSimMobilityStatusListeners;
    private final ArrayMap<IImsVideoListener, String> mVideoListeners;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface ConnectionListener {
        void onConnected();

        void onDisconnected();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface DmConfigEventRelay {
        void onChangeDmValue(String str, boolean z);
    }

    public ImsManager() {
        this.mListener = null;
        this.mServiceBound = false;
        this.mRegListeners = new ArrayMap<>();
        this.mEpdgListeners = new ArrayMap<>();
        this.mDialogListeners = new ArrayMap<>();
        this.mVideoListeners = new ArrayMap<>();
        this.mImSessionListeners = new ArrayMap<>();
        this.mOngoingFtEventListeners = new ArrayMap<>();
        this.mRttListeners = new ArrayMap<>();
        this.mAutoConfigurationListener = new ArrayMap<>();
        this.mSimMobilityStatusListeners = new ArrayMap<>();
        this.mCmcRegListeners = new ArrayMap<>();
        this.mRestartReceiver = null;
        this.mPhoneId = 0;
        this.mEventRelay = null;
        this.mEventProxy = new IImsDmConfigListener.Stub() { // from class: com.sec.ims.ImsManager.2
            @Override // com.sec.ims.IImsDmConfigListener
            public void onChangeDmValue(String str, boolean z) {
                if (ImsManager.this.mEventRelay == null) {
                    Log.d("legacyImsManager[" + ImsManager.this.mPhoneId + "]", "no listener for IImsDmConfigListener");
                    throw new RemoteException();
                }
                Log.d("legacyImsManager[" + ImsManager.this.mPhoneId + "]", "mEventRelay : " + ImsManager.this.mEventRelay);
                ImsManager.this.mEventRelay.onChangeDmValue(str, z);
            }
        };
        this.mContext = null;
        this.mListener = null;
    }

    public static int getImsApiVersion() {
        Log.d(LOG_TAG, "Current IMS API Version is 2");
        return 2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public IImsService getImsService() {
        return IImsService.Stub.asInterface(getSystemService(SERVICE_NAME));
    }

    public static int getImsVersion() {
        Log.d(LOG_TAG, "Current IMS Platform Version is 60400");
        return IMS_PLATFORM_VERSION;
    }

    private IBinder getSystemService(String str) {
        try {
            Class<?> cls = Class.forName("android.os.ServiceManager");
            Method method = cls.getMethod("getService", String.class);
            if (method == null) {
                Log.d(LOG_TAG, "Failed to reflect method getService");
                return null;
            }
            Object invoke = method.invoke(cls, str);
            if (invoke != null) {
                return (IBinder) invoke;
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
    public void onConnectService(IImsService iImsService) {
        if (this.mListener == null || iImsService == null) {
            return;
        }
        registerPreviousListeners(iImsService);
        this.mListener.onConnected();
    }

    private void registerPreviousListeners(IImsService iImsService) {
        synchronized (this) {
            Log.d(LOG_TAG, "registerPreviousListeners:  mRegListeners:" + this.mRegListeners.size() + " mDialogListeners:" + this.mDialogListeners.size() + " mVideoListeners:" + this.mVideoListeners.size() + " mImSessionListeners:" + this.mImSessionListeners.size() + " mOngoingFtEventListeners:" + this.mOngoingFtEventListeners.size() + " mAutoConfigurationListener:" + this.mAutoConfigurationListener.size() + " mSimMobilityStatusListeners:" + this.mSimMobilityStatusListeners.size() + " mEpdgListeners:" + this.mEpdgListeners.size() + " mCmcRegListeners:" + this.mCmcRegListeners.size());
            try {
                for (IImsRegistrationListener iImsRegistrationListener : this.mRegListeners.keySet()) {
                    String registerImsRegistrationListenerForSlot = iImsService.registerImsRegistrationListenerForSlot(iImsRegistrationListener, this.mPhoneId);
                    if (!TextUtils.isEmpty(registerImsRegistrationListenerForSlot)) {
                        this.mRegListeners.put(iImsRegistrationListener, registerImsRegistrationListenerForSlot);
                    }
                }
                for (IDialogEventListener iDialogEventListener : this.mDialogListeners.keySet()) {
                    String registerDialogEventListenerByToken = iImsService.registerDialogEventListenerByToken(this.mPhoneId, iDialogEventListener);
                    if (!TextUtils.isEmpty(registerDialogEventListenerByToken)) {
                        this.mDialogListeners.put(iDialogEventListener, registerDialogEventListenerByToken);
                    }
                }
                for (IImSessionListener iImSessionListener : this.mImSessionListeners.keySet()) {
                    String registerImSessionListener = iImsService.registerImSessionListener(iImSessionListener);
                    if (!TextUtils.isEmpty(registerImSessionListener)) {
                        this.mImSessionListeners.put(iImSessionListener, registerImSessionListener);
                    }
                }
                for (IImsOngoingFtEventListener iImsOngoingFtEventListener : this.mOngoingFtEventListeners.keySet()) {
                    String registerImsOngoingFtListener = iImsService.registerImsOngoingFtListener(iImsOngoingFtEventListener);
                    if (!TextUtils.isEmpty(registerImsOngoingFtListener)) {
                        this.mOngoingFtEventListeners.put(iImsOngoingFtEventListener, registerImsOngoingFtListener);
                    }
                }
                for (IAutoConfigurationListener iAutoConfigurationListener : this.mAutoConfigurationListener.keySet()) {
                    String registerAutoConfigurationListener = iImsService.registerAutoConfigurationListener(iAutoConfigurationListener, this.mPhoneId);
                    if (!TextUtils.isEmpty(registerAutoConfigurationListener)) {
                        this.mAutoConfigurationListener.put(iAutoConfigurationListener, registerAutoConfigurationListener);
                    }
                }
                for (ISimMobilityStatusListener iSimMobilityStatusListener : this.mSimMobilityStatusListeners.keySet()) {
                    String registerSimMobilityStatusListenerByPhoneId = iImsService.registerSimMobilityStatusListenerByPhoneId(iSimMobilityStatusListener, this.mPhoneId);
                    if (!TextUtils.isEmpty(registerSimMobilityStatusListenerByPhoneId)) {
                        this.mSimMobilityStatusListeners.put(iSimMobilityStatusListener, registerSimMobilityStatusListenerByPhoneId);
                    }
                }
                for (IEpdgListener iEpdgListener : this.mEpdgListeners.keySet()) {
                    String registerEpdgListener = iImsService.registerEpdgListener(iEpdgListener);
                    if (!TextUtils.isEmpty(registerEpdgListener)) {
                        this.mEpdgListeners.put(iEpdgListener, registerEpdgListener);
                    }
                }
                for (IImsRegistrationListener iImsRegistrationListener2 : this.mCmcRegListeners.keySet()) {
                    String registerCmcRegistrationListenerForSlot = iImsService.registerCmcRegistrationListenerForSlot(iImsRegistrationListener2, this.mPhoneId);
                    if (!TextUtils.isEmpty(registerCmcRegistrationListenerForSlot)) {
                        this.mCmcRegListeners.put(iImsRegistrationListener2, registerCmcRegistrationListenerForSlot);
                    }
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void changeEPDGAudioPath(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("changeEPDGAudioPath: ", i, ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]"));
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return;
        }
        try {
            imsService.changeAudioPathForSlot(this.mPhoneId, i);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void connectService() {
        if (this.mRestartReceiver == null) {
            Log.d("legacyImsManager[" + this.mPhoneId + "]", "Register Receiver for Restart");
            this.mRestartReceiver = new BroadcastReceiver() { // from class: com.sec.ims.ImsManager.1
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    Log.d("legacyImsManager[" + ImsManager.this.mPhoneId + "]", "onReceive " + intent.getAction());
                    if (TextUtils.equals(intent.getAction(), ImsManager.INTENT_ACTION_IMSSERVICE_RESTART)) {
                        ImsManager.this.onConnectService(ImsManager.this.getImsService());
                    }
                }
            };
            this.mContext.registerReceiver(this.mRestartReceiver, AppCompatDelegateImpl$$ExternalSyntheticOutline0.m5m(INTENT_ACTION_IMSSERVICE_RESTART));
        }
        onConnectService(getImsService());
    }

    public void deregisterAdhocProfile(int i) {
        NestedScrollView$$ExternalSyntheticOutline0.m34m("deregisterAdhocProfile: id ", i, ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]"));
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "deregisterAdhocProfile: Not initialized.");
            return;
        }
        try {
            imsService.deregisterAdhocProfileByPhoneId(i, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void deregisterProfile(List<Integer> list, boolean z) {
        IImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(LOG_TAG, "Not initialized.");
            return;
        }
        try {
            imsService.deregisterProfileByPhoneId(list, z, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void disconnectService() {
        BroadcastReceiver broadcastReceiver = this.mRestartReceiver;
        if (broadcastReceiver != null) {
            try {
                this.mContext.unregisterReceiver(broadcastReceiver);
            } catch (IllegalArgumentException e) {
                Log.e(ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]"), "unregisterReceiver " + e.getMessage());
            }
            this.mRestartReceiver = null;
        }
        ConnectionListener connectionListener = this.mListener;
        if (connectionListener != null) {
            connectionListener.onDisconnected();
        }
    }

    public void doDump() {
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return;
        }
        try {
            imsService.dump();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public void enableIpme(boolean z) {
        Log.d(ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]"), "enableIpme: " + z);
        enableRcs(z);
    }

    @Deprecated
    public void enableRcs(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("enableRcs: ", z, ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]"));
        try {
            getImsService().enableRcsByPhoneId(z, this.mPhoneId);
        } catch (RemoteException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void enableService(String str, boolean z) {
        Log.d(ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]"), "enableService: " + str + " " + z);
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "enableService: not connected.");
            return;
        }
        try {
            imsService.enableServiceByPhoneId(str, z, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    public void enableVoLte(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("enableVoLte: ", z, LOG_TAG);
        IImsService imsService = getImsService();
        if (imsService == null) {
            Log.d(LOG_TAG, "enableVoLte: not connected.");
            return;
        }
        try {
            imsService.enableVoLte(z);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void finishDmConfig(int i) {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "finishDmConfig");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return;
        }
        try {
            imsService.finishDmConfig(i, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public int[] getCallCount() {
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return null;
        }
        try {
            return imsService.getCallCount(-1);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public CmcCallInfo getCmcCallInfo() {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getCmcCallInfo");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getCmcCallInfo: Not initialized.");
            return null;
        }
        try {
            return imsService.getCmcCallInfo();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ContentValues getConfigValues(String[] strArr) {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getConfigValues");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return null;
        }
        try {
            return imsService.getConfigValues(strArr, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ImsProfile[] getCurrentProfile() {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getCurrentProfile");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return null;
        }
        try {
            return imsService.getCurrentProfile();
        } catch (RemoteException unused) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "fail to get profiles");
            return null;
        }
    }

    public int getEpsFbCallCount(int i) {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getEpsFbCallCount");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return -1;
        }
        try {
            return imsService.getEpsFbCallCount(-1);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public DialogEvent getLastDialogEvent() {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getLastDialogEvent");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return null;
        }
        try {
            return imsService.getLastDialogEvent(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getNrSaCallCount(int i) {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getNrSaCallCount");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return -1;
        }
        try {
            return imsService.getNrSaCallCount(-1);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int getPhoneId() {
        return this.mPhoneId;
    }

    public String getRcsProfileType() {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getRcsProfileType");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return "";
        }
        try {
            return imsService.getRcsProfileType(this.mPhoneId);
        } catch (RemoteException unused) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "fail to get profile");
            return "";
        }
    }

    public ImsRegistration[] getRegistrationInfo() {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getRegistrationInfo");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getRegistrationInfo: Not initialized.");
            return null;
        }
        try {
            return imsService.getRegistrationInfoByPhoneId(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ImsRegistration getRegistrationInfoByServiceType(String str) {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getRegistrationInfoByServiceType");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getRegistrationInfoByServiceType: Not initialized.");
            return null;
        }
        try {
            return imsService.getRegistrationInfoByServiceType(str, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getRttMode() {
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return 0;
        }
        try {
            return imsService.getRttMode(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getVideocallType() {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getVideocallType");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return 1;
        }
        try {
            return imsService.getVideocallType();
        } catch (RemoteException e) {
            e.printStackTrace();
            return 1;
        }
    }

    public boolean hasCrossSimImsService(int i) {
        IImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(LOG_TAG, "Not initialized.");
            return false;
        }
        try {
            return imsService.hasCrossSimImsService(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasVoLteSim() {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "hasVoLteSim");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return false;
        }
        try {
            return imsService.hasVoLteSimByPhoneId(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isCmcEmergencyCallSupported() {
        Log.d(LOG_TAG, "isCmcEmergencyCallSupported");
        IImsService imsService = getImsService();
        if (imsService != null) {
            return imsService.isCmcEmergencyCallSupported(this.mPhoneId);
        }
        Log.d(LOG_TAG, "isCmcEmergencyCallSupported: Not initialized.");
        throw new RemoteException();
    }

    public boolean isCmcEmergencyNumber(String str) {
        Log.d(LOG_TAG, "isCmcEmergencyNumber");
        IImsService imsService = getImsService();
        if (imsService == null) {
            Log.d(LOG_TAG, "isCmcEmergencyNumber: Not initialized.");
            return false;
        }
        try {
            return imsService.isCmcEmergencyNumber(str, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isCmcPotentialEmergencyNumber(String str) {
        Log.d(LOG_TAG, "isCmcPotentialEmergencyNumber");
        IImsService imsService = getImsService();
        if (imsService == null) {
            Log.d(LOG_TAG, "isCmcPotentialEmergencyNumber: Not initialized.");
            return false;
        }
        try {
            return imsService.isCmcPotentialEmergencyNumber(str, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isCrossSimCallingRegistered(int i) {
        IImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(LOG_TAG, "Not initialized.");
            return false;
        }
        try {
            return imsService.isCrossSimCallingRegistered(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isCrossSimCallingSupported() {
        IImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(LOG_TAG, "Not initialized.");
            return false;
        }
        try {
            return imsService.isCrossSimCallingSupported();
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isCrossSimCallingSupportedByPhoneId(int i) {
        IImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(LOG_TAG, "Not initialized.");
            return false;
        }
        try {
            return imsService.isCrossSimCallingSupportedByPhoneId(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isCrossSimPermanentBlocked() {
        IImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(LOG_TAG, "Not initialized.");
            return false;
        }
        try {
            return imsService.isCrossSimPermanentBlocked(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isForbidden() {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "isForbidden");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return false;
        }
        try {
            return imsService.isForbiddenByPhoneId(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Deprecated
    public boolean isIpmeEnabled() {
        Log.d("legacyImsManager[" + this.mPhoneId + "]", "isIpmeEnabled");
        return isRcsEnabled();
    }

    public boolean isQSSSuccessAuthAndLogin() {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "isQSSSuccessAuthAndLogin");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "isQSSSuccessAuthAndLogin: Not initialized.");
            return false;
        }
        try {
            return imsService.isQSSSuccessAuthAndLogin(this.mPhoneId);
        } catch (RemoteException | ClassCastException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Deprecated
    public boolean isRcsEnabled() {
        return isRcsEnabled(true);
    }

    public boolean isRttCall(int i) {
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return false;
        }
        try {
            return imsService.isRttCall(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isServiceAvailable(String str) {
        AbstractC0000x2c234b15.m3m("isServiceAvailable: ", str, ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]"));
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "isServiceAvailable: not connected.");
            return false;
        }
        try {
            return imsService.isServiceAvailable(str, -1, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isServiceEnabled(String str) {
        Cursor query = this.mContext.getContentResolver().query(Uri.parse("content://com.sec.ims.settings/imsswitch").buildUpon().fragment("simslot" + this.mPhoneId).build(), new String[]{str}, null, null, null);
        if (query != null) {
            try {
                if (query.getCount() != 0) {
                    try {
                        if (query.moveToFirst()) {
                            String string = query.getString(query.getColumnIndexOrThrow("name"));
                            int i = query.getInt(query.getColumnIndexOrThrow("enabled"));
                            r1 = i == 1;
                            Log.d(LOG_TAG, "isServiceEnabled: " + string + " " + i);
                        }
                    } catch (IllegalArgumentException unused) {
                        Log.d(LOG_TAG, "isServiceEnabled: false due to IllegalArgumentException");
                    }
                    query.close();
                    return r1;
                }
            } catch (Throwable th) {
                if (query != null) {
                    try {
                        query.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        Log.d(LOG_TAG, "isServiceEnabled: not found");
        if (query != null) {
            query.close();
        }
        return false;
    }

    public boolean isSupportVoWiFiDisable5GSA() {
        Log.d(LOG_TAG, "isSupportVoWiFiDisable5GSA");
        IImsService imsService = getImsService();
        if (imsService == null) {
            Log.d(LOG_TAG, "isSupportVoWiFiDisable5GSA: Not initialized.");
            return false;
        }
        try {
            return imsService.isSupportVoWiFiDisable5GSA(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isVoLteEnabled() {
        Cursor query = this.mContext.getContentResolver().query(Uri.parse("content://com.sec.ims.settings/imsswitch").buildUpon().fragment("simslot" + Integer.toString(this.mPhoneId)).build(), new String[]{"volte"}, null, null, null);
        if (query != null) {
            try {
                if (query.getCount() != 0) {
                    try {
                        if (query.moveToFirst()) {
                            String string = query.getString(query.getColumnIndexOrThrow("name"));
                            int i = query.getInt(query.getColumnIndexOrThrow("enabled"));
                            r4 = i == 1;
                            Log.d("legacyImsManager[" + this.mPhoneId + "]", "isVoLteEnabled: " + string + " " + i);
                        }
                    } catch (IllegalArgumentException unused) {
                        Log.d("legacyImsManager[" + this.mPhoneId + "]", "isVoLteEnabled: false due to IllegalArgumentException");
                    }
                    query.close();
                    return r4;
                }
            } catch (Throwable th) {
                if (query != null) {
                    try {
                        query.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        Log.d("legacyImsManager[" + this.mPhoneId + "]", "isVoLteEnabled: not found");
        if (query != null) {
            query.close();
        }
        return false;
    }

    public boolean isVolteEnabledFromNetwork() {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "isVolteEnabledFromNetwork");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "isVolteEnabledFromNetwork: Not initialized.");
            return false;
        }
        try {
            return imsService.isVolteEnabledFromNetwork(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isVolteSupportECT() {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "isVolteSupportECT");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "isVolteSupportECT: Not initialized.");
            return false;
        }
        try {
            return imsService.isVolteSupportEctByPhoneId(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int registerAdhocProfile(ImsProfile imsProfile) {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "registerAdhocProfile");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "registerAdhocProfile: Not initialized.");
            return -1;
        }
        try {
            return imsService.registerAdhocProfileByPhoneId(imsProfile, this.mPhoneId);
        } catch (RemoteException | ClassCastException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void registerAutoConfigurationListener(IAutoConfigurationListener iAutoConfigurationListener) {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "registerAutoConfigurationListener");
        if (iAutoConfigurationListener == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "listener is null.");
            return;
        }
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            this.mAutoConfigurationListener.put(iAutoConfigurationListener, "");
            return;
        }
        try {
            String registerAutoConfigurationListener = imsService.registerAutoConfigurationListener(iAutoConfigurationListener, this.mPhoneId);
            if (TextUtils.isEmpty(registerAutoConfigurationListener)) {
                return;
            }
            this.mAutoConfigurationListener.put(iAutoConfigurationListener, registerAutoConfigurationListener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public synchronized void registerCmcRegistrationListener(IImsRegistrationListener iImsRegistrationListener) {
        Log.d("legacyImsManager[" + this.mPhoneId + "]", "registerCmcRegistrationListener");
        if (iImsRegistrationListener == null) {
            Log.e("legacyImsManager[" + this.mPhoneId + "]", "listener is null.");
            return;
        }
        IImsService imsService = getImsService();
        if (imsService == null) {
            Log.e("legacyImsManager[" + this.mPhoneId + "]", "Not initialized.");
            this.mCmcRegListeners.put(iImsRegistrationListener, "");
            return;
        }
        try {
            String registerCmcRegistrationListenerForSlot = imsService.registerCmcRegistrationListenerForSlot(iImsRegistrationListener, this.mPhoneId);
            if (!TextUtils.isEmpty(registerCmcRegistrationListenerForSlot)) {
                this.mCmcRegListeners.put(iImsRegistrationListener, registerCmcRegistrationListenerForSlot);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public synchronized void registerDialogEventListener(IDialogEventListener iDialogEventListener) {
        Log.d("legacyImsManager[" + this.mPhoneId + "]", "registerDialogEventListener");
        if (iDialogEventListener == null) {
            Log.e("legacyImsManager[" + this.mPhoneId + "]", "listener is null.");
            return;
        }
        IImsService imsService = getImsService();
        if (imsService == null) {
            Log.e("legacyImsManager[" + this.mPhoneId + "]", "Not initialized.");
            this.mDialogListeners.put(iDialogEventListener, "");
            return;
        }
        try {
            String registerDialogEventListenerByToken = imsService.registerDialogEventListenerByToken(this.mPhoneId, iDialogEventListener);
            if (!TextUtils.isEmpty(registerDialogEventListenerByToken)) {
                this.mDialogListeners.put(iDialogEventListener, registerDialogEventListenerByToken);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void registerDmValueListener(DmConfigEventRelay dmConfigEventRelay) {
        if (dmConfigEventRelay == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "listener is null");
            return;
        }
        this.mEventRelay = dmConfigEventRelay;
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return;
        }
        try {
            imsService.registerDmValueListener(this.mEventProxy);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public synchronized void registerEpdgListener(IEpdgListener iEpdgListener) {
        Log.d(LOG_TAG, "registerEpdgListener");
        if (iEpdgListener == null) {
            Log.e("legacyImsManager[" + this.mPhoneId + "]", "listener is null.");
            return;
        }
        IImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(LOG_TAG, "Not initialized.");
            this.mEpdgListeners.put(iEpdgListener, "");
            return;
        }
        try {
            String registerEpdgListener = imsService.registerEpdgListener(iEpdgListener);
            if (!TextUtils.isEmpty(registerEpdgListener)) {
                this.mEpdgListeners.put(iEpdgListener, registerEpdgListener);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void registerImSessionListener(IImSessionListener iImSessionListener) {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "registerImSessionListener");
        if (iImSessionListener == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "listener is null.");
            return;
        }
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            this.mImSessionListeners.put(iImSessionListener, "");
            return;
        }
        try {
            String registerImSessionListener = imsService.registerImSessionListener(iImSessionListener);
            if (TextUtils.isEmpty(registerImSessionListener)) {
                return;
            }
            this.mImSessionListeners.put(iImSessionListener, registerImSessionListener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void registerImsOngoingFtEventListener(IImsOngoingFtEventListener iImsOngoingFtEventListener) {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "registerImsOngoingFtEventListener");
        if (iImsOngoingFtEventListener == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "registerImsOngoingFtEventListener : wrong instance or null");
            return;
        }
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            this.mOngoingFtEventListeners.put(iImsOngoingFtEventListener, "");
            return;
        }
        try {
            String registerImsOngoingFtListener = imsService.registerImsOngoingFtListener(iImsOngoingFtEventListener);
            if (TextUtils.isEmpty(registerImsOngoingFtListener)) {
                return;
            }
            this.mOngoingFtEventListeners.put(iImsOngoingFtEventListener, registerImsOngoingFtListener);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public synchronized void registerImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) {
        Log.d("legacyImsManager[" + this.mPhoneId + "]", "registerImsRegistrationListener");
        if (iImsRegistrationListener == null) {
            Log.e("legacyImsManager[" + this.mPhoneId + "]", "listener is null.");
            return;
        }
        if (getImsService() != null) {
            registerImsRegistrationListener(iImsRegistrationListener, -1);
            return;
        }
        Log.e("legacyImsManager[" + this.mPhoneId + "]", "Not initialized.");
        this.mRegListeners.put(iImsRegistrationListener, "");
    }

    public void registerProfile(List<Integer> list) {
        IImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(LOG_TAG, "Not initialized.");
            return;
        }
        try {
            imsService.registerProfileByPhoneId(list, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public synchronized void registerRttEventListener(IRttEventListener iRttEventListener) {
        Log.d("legacyImsManager[" + this.mPhoneId + "]", "registerDialogEventListener");
        if (iRttEventListener == null) {
            Log.e("legacyImsManager[" + this.mPhoneId + "]", "listener is null.");
            return;
        }
        IImsService imsService = getImsService();
        if (imsService == null) {
            Log.e("legacyImsManager[" + this.mPhoneId + "]", "Not initialized.");
            this.mRttListeners.put(iRttEventListener, "");
            return;
        }
        try {
            String registerRttEventListener = imsService.registerRttEventListener(this.mPhoneId, iRttEventListener);
            if (!TextUtils.isEmpty(registerRttEventListener)) {
                this.mRttListeners.put(iRttEventListener, registerRttEventListener);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public synchronized void registerSimMobilityStatusListener(ISimMobilityStatusListener iSimMobilityStatusListener) {
        Log.d("legacyImsManager[" + this.mPhoneId + "]", "registerSimMobilityStatusListener");
        if (iSimMobilityStatusListener == null) {
            Log.e("legacyImsManager[" + this.mPhoneId + "]", "listener is null.");
            return;
        }
        if (getImsService() != null) {
            registerSimMobilityStatusListener(iSimMobilityStatusListener, -1);
            return;
        }
        Log.e("legacyImsManager[" + this.mPhoneId + "]", "Not initialized.");
        this.mSimMobilityStatusListeners.put(iSimMobilityStatusListener, "");
    }

    public void sendDeregister(int i) {
        ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "sendDeregister");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "sendDeregister");
            return;
        }
        try {
            imsService.sendDeregister(i, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void sendIidToken(String str) {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "sendIidToken");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return;
        }
        try {
            imsService.sendIidToken(str, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void sendMsisdnNumber(String str) {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "sendMsisdnNumber");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return;
        }
        try {
            imsService.sendMsisdnNumber(str, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void sendRttMessage(String str) {
        AbstractC0000x2c234b15.m3m("sendRttMessage: ", str, ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]"));
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "setRttMode: not connected.");
            return;
        }
        try {
            imsService.sendRttMessage(str);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void sendRttSessionModifyRequest(int i, boolean z) {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "sendRttSessionModifyRequest: ");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "sendRttSessionModifyRequest: not connected.");
            return;
        }
        try {
            imsService.sendRttSessionModifyRequest(i, z);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void sendRttSessionModifyResponse(int i, boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("sendRttSessionModifyResponse: ", z, ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]"));
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "sendRttSessionModifyResponse: not connected.");
            return;
        }
        try {
            imsService.sendRttSessionModifyResponse(i, z);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void sendTryRegister() {
        ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "sendTryRegister");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "sendTryRegister: Not initialized.");
            return;
        }
        try {
            imsService.sendTryRegisterByPhoneId(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void sendTryRegisterCms(int i) {
        Log.e("legacyImsManager[" + i + "]", "sendTryRegisterCms");
        IImsService imsService = getImsService();
        if (imsService == null) {
            Log.e("legacyImsManager[" + i + "]", "sendTryRegisterCms: Not initialized.");
            return;
        }
        try {
            imsService.sendTryRegisterCms(i);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void sendUpdateRegister() {
        ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "sendInitialRegister");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "sendInitialRegister");
            return;
        }
        try {
            ImsRegistration[] registrationInfoByPhoneId = imsService.getRegistrationInfoByPhoneId(this.mPhoneId);
            if (registrationInfoByPhoneId != null && registrationInfoByPhoneId.length != 0) {
                for (ImsRegistration imsRegistration : registrationInfoByPhoneId) {
                    if (imsRegistration.hasService("mmtel") && imsRegistration.getImsProfile().getPdnType() == 11) {
                        imsService.forcedUpdateRegistrationByPhoneId(imsRegistration.getImsProfile(), this.mPhoneId);
                        return;
                    }
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void sendVerificationCode(String str) {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "sendVerificationCode");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return;
        }
        try {
            imsService.sendVerificationCode(str, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public int setActiveMsisdn(String str, String str2) {
        Log.d(ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]"), "setActiveMsisdn: msisdn " + IMSLog.checker(str) + " service " + str2);
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "setActiveMsisdn: Not initialized.");
            return -1;
        }
        try {
            return imsService.setActiveMsisdn(this.mPhoneId, str, str2);
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void setAutomaticMode(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("setAutomaticMode: ", z, ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]"));
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "setAutomaticMode: not connected.");
            return;
        }
        try {
            imsService.setAutomaticMode(this.mPhoneId, z);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setCrossSimPermanentBlocked(boolean z) {
        IImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(LOG_TAG, "Not initialized.");
            return;
        }
        try {
            imsService.setCrossSimPermanentBlocked(this.mPhoneId, z);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setEmergencyPdnInfo(String str, String[] strArr, String str2) {
        Log.e(ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]"), "setEmergencyPdnInfo: intfName " + str);
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "setEmergencyPdnInfo: Not initialized.");
            return;
        }
        try {
            imsService.setEmergencyPdnInfo(str, strArr, str2, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setNrInterworkingMode(int i) {
        IImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(LOG_TAG, "Not initialized.");
            return;
        }
        try {
            imsService.setNrInterworkingMode(this.mPhoneId, i);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setRttMode(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("setRttMode: ", i, ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]"));
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "setRttMode: not connected.");
            return;
        }
        try {
            imsService.setRttMode(this.mPhoneId, i);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public boolean setVideocallType(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m10m("setVideocallType: ", i, ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]"));
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return false;
        }
        try {
            return imsService.setVideocallType(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int startDmConfig() {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "startDmConfig");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return 0;
        }
        try {
            return imsService.startDmConfig(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int startLocalRingBackTone(int i, int i2, int i3) {
        RecyclerView$$ExternalSyntheticOutline0.m46m(GridLayoutManager$$ExternalSyntheticOutline0.m45m("startLocalRingBackTone: ", i, ", ", i2, ", "), i3, ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]"));
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return -1;
        }
        try {
            return imsService.startLocalRingBackTone(i, i2, i3);
        } catch (RemoteException e) {
            Log.e(ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]"), "startLocalRingBackTone has Error " + e.getMessage());
            return -1;
        }
    }

    public int stopLocalRingBackTone() {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "stopLocalRingBackTone:");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return -1;
        }
        try {
            return imsService.stopLocalRingBackTone();
        } catch (RemoteException e) {
            Log.e(ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]"), "stopLocalRingBackTone has Error " + e.getMessage());
            return -1;
        }
    }

    public void suspendRegister(boolean z) {
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "SuspendRegi Error. ImsService null.");
            return;
        }
        try {
            imsService.suspendRegister(z, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void transferCall(String str, String str2) {
        Log.d(ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]"), "transferCall msisdn : " + IMSLog.checker(str) + ", dialogId : " + str2);
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return;
        }
        try {
            imsService.transferCall(str, str2);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void triggerAutoConfigurationForApp(int i) {
        Log.d(LOG_TAG, "triggerAutoConfigurationForApp");
        IImsService imsService = getImsService();
        if (imsService == null) {
            Log.e(LOG_TAG, "Not initialized.");
            return;
        }
        try {
            imsService.triggerAutoConfigurationForApp(i);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public synchronized void unRegisterEpdgListener(IEpdgListener iEpdgListener) {
        Log.d(LOG_TAG, "unRegisterEpdgListener");
        if (iEpdgListener == null) {
            Log.e("legacyImsManager[" + this.mPhoneId + "]", "listener is null.");
            return;
        }
        String remove = this.mEpdgListeners.remove(iEpdgListener);
        IImsService imsService = getImsService();
        if (imsService == null || remove == null) {
            Log.e(LOG_TAG, "Not initialized or token null.");
            return;
        }
        try {
            imsService.unRegisterEpdgListener(remove);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unregisterAutoConfigurationListener(IAutoConfigurationListener iAutoConfigurationListener) {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "unregisterAutoConfigurationListener");
        if (iAutoConfigurationListener == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "listener is null.");
            return;
        }
        String remove = this.mAutoConfigurationListener.remove(iAutoConfigurationListener);
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized or token null.");
            return;
        }
        if (remove == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "listener is null.");
            return;
        }
        try {
            imsService.unregisterAutoConfigurationListener(remove, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public synchronized void unregisterCmcRegistrationListener(IImsRegistrationListener iImsRegistrationListener) {
        Log.d("legacyImsManager[" + this.mPhoneId + "]", "unregisterCmcRegistrationListener");
        if (iImsRegistrationListener == null) {
            Log.e("legacyImsManager[" + this.mPhoneId + "]", "listener is null.");
            return;
        }
        String remove = this.mCmcRegListeners.remove(iImsRegistrationListener);
        IImsService imsService = getImsService();
        if (imsService != null && remove != null) {
            try {
                imsService.unregisterCmcRegistrationListenerForSlot(remove, this.mPhoneId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("legacyImsManager[" + this.mPhoneId + "]", "Not initialized or token null.");
        }
    }

    public synchronized void unregisterDialogEventListener(IDialogEventListener iDialogEventListener) {
        Log.d("legacyImsManager[" + this.mPhoneId + "]", "unregisterDialogEventListener");
        if (iDialogEventListener == null) {
            Log.e("legacyImsManager[" + this.mPhoneId + "]", "listener is null.");
            return;
        }
        String remove = this.mDialogListeners.remove(iDialogEventListener);
        IImsService imsService = getImsService();
        if (imsService != null && remove != null) {
            try {
                imsService.unregisterDialogEventListenerByToken(this.mPhoneId, remove);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("legacyImsManager[" + this.mPhoneId + "]", "Not initialized or token null.");
        }
    }

    public void unregisterDmValueListener(DmConfigEventRelay dmConfigEventRelay) {
        this.mEventRelay = null;
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return;
        }
        try {
            imsService.unregisterDmValueListener(this.mEventProxy);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unregisterImSessionListener(IImSessionListener iImSessionListener) {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "unregisterImSessionListener");
        if (iImSessionListener == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "listener is null.");
            return;
        }
        String remove = this.mImSessionListeners.remove(iImSessionListener);
        IImsService imsService = getImsService();
        if (imsService == null || remove == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized or token null.");
            return;
        }
        try {
            imsService.unregisterImSessionListener(remove);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unregisterImsOngoingFtEventListener(IImsOngoingFtEventListener iImsOngoingFtEventListener) {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "unregisterImsOngoingFtEventListener");
        if (iImsOngoingFtEventListener == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "listener is null.");
            return;
        }
        String remove = this.mOngoingFtEventListeners.remove(iImsOngoingFtEventListener);
        IImsService imsService = getImsService();
        if (imsService == null || remove == null) {
            Log.e(LOG_TAG, "Not initialized or token null.");
            return;
        }
        try {
            imsService.unregisterImsOngoingFtListener(remove);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public synchronized void unregisterImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener) {
        Log.d("legacyImsManager[" + this.mPhoneId + "]", "unregisterImsRegistrationListener");
        if (iImsRegistrationListener == null) {
            Log.e("legacyImsManager[" + this.mPhoneId + "]", "listener is null.");
            return;
        }
        if (getImsService() != null) {
            unregisterImsRegistrationListener(iImsRegistrationListener, -1);
            return;
        }
        Log.e("legacyImsManager[" + this.mPhoneId + "]", "Not initialized.");
        this.mRegListeners.remove(iImsRegistrationListener);
    }

    public synchronized void unregisterRttEventListener(IRttEventListener iRttEventListener) {
        Log.d("legacyImsManager[" + this.mPhoneId + "]", "unregisterDialogEventListener");
        if (iRttEventListener == null) {
            Log.e("legacyImsManager[" + this.mPhoneId + "]", "listener is null.");
            return;
        }
        String remove = this.mRttListeners.remove(iRttEventListener);
        IImsService imsService = getImsService();
        if (imsService != null && remove != null) {
            try {
                imsService.unregisterRttEventListener(this.mPhoneId, remove);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("legacyImsManager[" + this.mPhoneId + "]", "Not initialized or token null.");
        }
    }

    public synchronized void unregisterSimMobilityStatusListener(ISimMobilityStatusListener iSimMobilityStatusListener) {
        Log.d("legacyImsManager[" + this.mPhoneId + "]", "unregisterSimMobilityStatusListener");
        if (iSimMobilityStatusListener == null) {
            Log.e("legacyImsManager[" + this.mPhoneId + "]", "listener is null.");
            return;
        }
        if (getImsService() != null) {
            unregisterSimMobilityStatusListener(iSimMobilityStatusListener, -1);
            return;
        }
        Log.e("legacyImsManager[" + this.mPhoneId + "]", "Not initialized.");
        this.mSimMobilityStatusListeners.remove(iSimMobilityStatusListener);
    }

    public boolean updateConfigValues(ContentValues contentValues) {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "updateConfigValues");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return false;
        }
        try {
            return imsService.updateConfigValues(contentValues, -1, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int updateRegistration(ImsProfile imsProfile) {
        Log.d(ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]"), "updateRegistration: profile " + imsProfile.getName());
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "updateRegistration: Not initialized.");
            return -1;
        }
        try {
            return imsService.updateRegistration(imsProfile, this.mPhoneId);
        } catch (RemoteException | ClassCastException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x003c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x003d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean isRcsEnabled(boolean z) {
        boolean z2;
        int i;
        boolean z3;
        RcsConfigurationReader rcsConfigurationReader = new RcsConfigurationReader(this.mContext);
        try {
        } catch (Settings.SettingNotFoundException e) {
            Log.d("legacyImsManager[" + this.mPhoneId + "]", "isRcsEnabled: rcs_user_setting is not exist.");
            e.printStackTrace();
        }
        if (Settings.System.getInt(this.mContext.getContentResolver(), "rcs_user_setting") == 1) {
            z2 = true;
            if (z) {
                return z2;
            }
            try {
                i = rcsConfigurationReader.getInt(RcsConfigurationReader.CONFIG_VERSION);
                try {
                    z3 = "true".equals(rcsConfigurationReader.getString(RcsConfigurationReader.AUTOCONFIG_COMPLETED));
                    try {
                        Log.d("legacyImsManager[" + this.mPhoneId + "]", "isRcsEnabled: version " + i + " autoConfigComplete " + z3);
                    } catch (IllegalStateException e2) {
                        e = e2;
                        Log.d("legacyImsManager[" + this.mPhoneId + "]", "isRcsEnabled: AutoConfiguration is not completed.");
                        e.printStackTrace();
                        if (z2) {
                        }
                    }
                } catch (IllegalStateException e3) {
                    e = e3;
                    z3 = false;
                }
            } catch (IllegalStateException e4) {
                e = e4;
                i = 0;
                z3 = false;
            }
            return !z2 && (!z3 || i > 0);
        }
        z2 = false;
        if (z) {
        }
    }

    public synchronized void registerImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener, int i) {
        Log.d("legacyImsManager[" + this.mPhoneId + "]", "registerImsRegistrationListener");
        if (iImsRegistrationListener == null) {
            Log.e("legacyImsManager[" + this.mPhoneId + "]", "listener is null.");
            return;
        }
        IImsService imsService = getImsService();
        if (imsService == null) {
            Log.e("legacyImsManager[" + this.mPhoneId + "]", "Not initialized.");
            this.mRegListeners.put(iImsRegistrationListener, "");
            return;
        }
        try {
            String registerImsRegistrationListenerForSlot = imsService.registerImsRegistrationListenerForSlot(iImsRegistrationListener, i);
            if (!TextUtils.isEmpty(registerImsRegistrationListenerForSlot)) {
                this.mRegListeners.put(iImsRegistrationListener, registerImsRegistrationListenerForSlot);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public synchronized void registerSimMobilityStatusListener(ISimMobilityStatusListener iSimMobilityStatusListener, int i) {
        Log.d("legacyImsManager[" + this.mPhoneId + "]", "registerSimMobilityStatusListener");
        if (iSimMobilityStatusListener == null) {
            Log.e("legacyImsManager[" + this.mPhoneId + "]", "listener is null.");
            return;
        }
        IImsService imsService = getImsService();
        if (imsService == null) {
            Log.e("legacyImsManager[" + this.mPhoneId + "]", "Not initialized.");
            this.mSimMobilityStatusListeners.put(iSimMobilityStatusListener, "");
            return;
        }
        try {
            String registerSimMobilityStatusListenerByPhoneId = imsService.registerSimMobilityStatusListenerByPhoneId(iSimMobilityStatusListener, i);
            if (!TextUtils.isEmpty(registerSimMobilityStatusListenerByPhoneId)) {
                this.mSimMobilityStatusListeners.put(iSimMobilityStatusListener, registerSimMobilityStatusListenerByPhoneId);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public synchronized void unregisterImsRegistrationListener(IImsRegistrationListener iImsRegistrationListener, int i) {
        if (iImsRegistrationListener == null) {
            Log.e("legacyImsManager[" + i + "]", "listener is null.");
            return;
        }
        String remove = this.mRegListeners.remove(iImsRegistrationListener);
        IImsService imsService = getImsService();
        if (imsService != null && remove != null) {
            try {
                imsService.unregisterImsRegistrationListenerForSlot(remove, i);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("legacyImsManager[" + i + "]", "Not initialized or token null.");
        }
    }

    public synchronized void unregisterSimMobilityStatusListener(ISimMobilityStatusListener iSimMobilityStatusListener, int i) {
        if (iSimMobilityStatusListener == null) {
            Log.e("legacyImsManager[" + i + "]", "listener is null.");
            return;
        }
        String remove = this.mSimMobilityStatusListeners.remove(iSimMobilityStatusListener);
        IImsService imsService = getImsService();
        if (imsService != null && remove != null) {
            try {
                imsService.unregisterSimMobilityStatusListenerByPhoneId(remove, i);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("legacyImsManager[" + i + "]", "Not initialized or token null.");
        }
    }

    public int[] getCallCount(int i) {
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return null;
        }
        try {
            return imsService.getCallCount(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ImsManager(Context context, ConnectionListener connectionListener) {
        this.mListener = null;
        this.mServiceBound = false;
        this.mRegListeners = new ArrayMap<>();
        this.mEpdgListeners = new ArrayMap<>();
        this.mDialogListeners = new ArrayMap<>();
        this.mVideoListeners = new ArrayMap<>();
        this.mImSessionListeners = new ArrayMap<>();
        this.mOngoingFtEventListeners = new ArrayMap<>();
        this.mRttListeners = new ArrayMap<>();
        this.mAutoConfigurationListener = new ArrayMap<>();
        this.mSimMobilityStatusListeners = new ArrayMap<>();
        this.mCmcRegListeners = new ArrayMap<>();
        this.mRestartReceiver = null;
        this.mPhoneId = 0;
        this.mEventRelay = null;
        this.mEventProxy = new IImsDmConfigListener.Stub() { // from class: com.sec.ims.ImsManager.2
            @Override // com.sec.ims.IImsDmConfigListener
            public void onChangeDmValue(String str, boolean z) {
                if (ImsManager.this.mEventRelay == null) {
                    Log.d("legacyImsManager[" + ImsManager.this.mPhoneId + "]", "no listener for IImsDmConfigListener");
                    throw new RemoteException();
                }
                Log.d("legacyImsManager[" + ImsManager.this.mPhoneId + "]", "mEventRelay : " + ImsManager.this.mEventRelay);
                ImsManager.this.mEventRelay.onChangeDmValue(str, z);
            }
        };
        this.mContext = context;
        this.mListener = connectionListener;
        this.mPhoneId = 0;
    }

    public boolean updateConfigValues(ContentValues contentValues, int i) {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "updateConfigValues");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return false;
        }
        try {
            return imsService.updateConfigValues(contentValues, i, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ImsProfile[] getCurrentProfile(int i) {
        ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getCurrentProfile");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return null;
        }
        try {
            return imsService.getCurrentProfileForSlot(i);
        } catch (RemoteException unused) {
            ImsManager$$ExternalSyntheticOutline0.m$1(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "fail to get profiles");
            return null;
        }
    }

    public boolean isServiceAvailable(String str, int i) {
        Log.d(ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]"), "isServiceAvailable: " + str + ", " + i);
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m273m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "isServiceAvailable: not connected.");
            return false;
        }
        try {
            return imsService.isServiceAvailable(str, i, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ImsManager(Context context, ConnectionListener connectionListener, int i) {
        this.mListener = null;
        this.mServiceBound = false;
        this.mRegListeners = new ArrayMap<>();
        this.mEpdgListeners = new ArrayMap<>();
        this.mDialogListeners = new ArrayMap<>();
        this.mVideoListeners = new ArrayMap<>();
        this.mImSessionListeners = new ArrayMap<>();
        this.mOngoingFtEventListeners = new ArrayMap<>();
        this.mRttListeners = new ArrayMap<>();
        this.mAutoConfigurationListener = new ArrayMap<>();
        this.mSimMobilityStatusListeners = new ArrayMap<>();
        this.mCmcRegListeners = new ArrayMap<>();
        this.mRestartReceiver = null;
        this.mPhoneId = 0;
        this.mEventRelay = null;
        this.mEventProxy = new IImsDmConfigListener.Stub() { // from class: com.sec.ims.ImsManager.2
            @Override // com.sec.ims.IImsDmConfigListener
            public void onChangeDmValue(String str, boolean z) {
                if (ImsManager.this.mEventRelay == null) {
                    Log.d("legacyImsManager[" + ImsManager.this.mPhoneId + "]", "no listener for IImsDmConfigListener");
                    throw new RemoteException();
                }
                Log.d("legacyImsManager[" + ImsManager.this.mPhoneId + "]", "mEventRelay : " + ImsManager.this.mEventRelay);
                ImsManager.this.mEventRelay.onChangeDmValue(str, z);
            }
        };
        this.mContext = context;
        this.mListener = connectionListener;
        this.mPhoneId = i;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class EpdgListener extends IEpdgListener.Stub {
        @Override // com.sec.ims.IEpdgListener
        public void onEpdgDeregister(int i) {
        }

        @Override // com.sec.ims.IEpdgListener
        public void onEpdgReleaseCall(int i) {
        }

        @Override // com.sec.ims.IEpdgListener
        public void onEpdgHandoverEnableChanged(int i, boolean z) {
        }

        @Override // com.sec.ims.IEpdgListener
        public void onEpdgIpsecDisconnection(int i, String str) {
        }

        @Override // com.sec.ims.IEpdgListener
        public void onEpdgRegister(int i, boolean z) {
        }

        @Override // com.sec.ims.IEpdgListener
        public void onEpdgShowPopup(int i, int i2) {
        }

        @Override // com.sec.ims.IEpdgListener
        public void onEpdgAvailable(int i, int i2, int i3) {
        }

        @Override // com.sec.ims.IEpdgListener
        public void onEpdgHandoverResult(int i, int i2, int i3, String str) {
        }

        @Override // com.sec.ims.IEpdgListener
        public void onEpdgIpsecConnection(int i, String str, int i2, int i3) {
        }
    }
}
