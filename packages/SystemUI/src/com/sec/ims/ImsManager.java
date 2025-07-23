package com.sec.ims;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import androidx.appcompat.app.AppCompatDelegateImpl$AutoBatteryNightModeManager$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticOutline0;
import com.sec.ims.IEpdgListener;
import com.sec.ims.IImsDmConfigListener;
import com.sec.ims.IImsService;
import com.sec.ims.cmc.CmcCallInfo;
import com.sec.ims.ft.IImsOngoingFtEventListener;
import com.sec.ims.im.IImSessionListener;
import com.sec.ims.settings.ImsProfile;
import com.sec.ims.util.IMSLog;
import com.sec.ims.volte2.IImsVideoListener;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
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
    private final ArrayMap<ISimMobilityStatusListener, String> mSimMobilityStatusListeners;
    private final ArrayMap<IImsVideoListener, String> mVideoListeners;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface ConnectionListener {
        void onConnected();

        void onDisconnected();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface DmConfigEventRelay {
        void onChangeDmValue(String str, boolean z);
    }

    public ImsManager() {
        this.mListener = null;
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
            public void onChangeDmValue(String str, boolean z) throws RemoteException {
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
            Object invoke = cls.getMethod("getService", String.class).invoke(cls, str);
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
            try {
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
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void changeEPDGAudioPath(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "changeEPDGAudioPath: ", ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("legacyImsManager[")));
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
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
            this.mContext.registerReceiver(this.mRestartReceiver, AppCompatDelegateImpl$AutoBatteryNightModeManager$$ExternalSyntheticOutline0.m(INTENT_ACTION_IMSSERVICE_RESTART), 2);
        }
        onConnectService(getImsService());
    }

    public void deregisterAdhocProfile(int i) {
        ClockEventController$$ExternalSyntheticOutline0.m(i, "deregisterAdhocProfile: id ", ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("legacyImsManager[")));
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "deregisterAdhocProfile: Not initialized.");
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
                Log.e(ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("legacyImsManager[")), "unregisterReceiver " + e.getMessage());
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
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
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
        Log.d(ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("legacyImsManager[")), "enableIpme: " + z);
        enableRcs(z);
    }

    @Deprecated
    public void enableRcs(boolean z) {
        EmergencyButtonController$$ExternalSyntheticOutline0.m("enableRcs: ", ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("legacyImsManager[")), z);
        try {
            getImsService().enableRcsByPhoneId(z, this.mPhoneId);
        } catch (RemoteException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void enableService(String str, boolean z) {
        Log.d(ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("legacyImsManager[")), "enableService: " + str + " " + z);
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "enableService: not connected.");
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
        EmergencyButtonController$$ExternalSyntheticOutline0.m("enableVoLte: ", LOG_TAG, z);
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

    public void finishDmConfig(int i) throws RemoteException {
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "finishDmConfig");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
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
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
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
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getCmcCallInfo");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getCmcCallInfo: Not initialized.");
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
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getConfigValues");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
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
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getCurrentProfile");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return null;
        }
        try {
            return imsService.getCurrentProfile();
        } catch (RemoteException unused) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "fail to get profiles");
            return null;
        }
    }

    public int getEpsFbCallCount(int i) {
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getEpsFbCallCount");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
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
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getLastDialogEvent");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return null;
        }
        try {
            return imsService.getLastDialogEvent(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public LastEndedImsCallInfo getLastEndedImsCallInfo(int i) {
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getLastEndedImsCallInfo");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return null;
        }
        try {
            return imsService.getLastEndedImsCallInfo(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getNrSaCallCount(int i) {
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getNrSaCallCount");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
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
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getRcsProfileType");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return "";
        }
        try {
            return imsService.getRcsProfileType(this.mPhoneId);
        } catch (RemoteException unused) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "fail to get profile");
            return "";
        }
    }

    public ImsRegistration[] getRegistrationInfo() {
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getRegistrationInfo");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getRegistrationInfo: Not initialized.");
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
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getRegistrationInfoByServiceType");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getRegistrationInfoByServiceType: Not initialized.");
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
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
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
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getVideocallType");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
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
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "hasVoLteSim");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return false;
        }
        try {
            return imsService.hasVoLteSimByPhoneId(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isCmcEmergencyCallSupported() throws RemoteException {
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
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "isForbidden");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
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

    public boolean isNonVerifiedMno(int i) {
        IImsService imsService = getImsService();
        if (imsService != null) {
            try {
                return imsService.isNonVerifiedMno(i);
            } catch (RemoteException e) {
                e.printStackTrace();
                return false;
            }
        }
        Log.e("legacyImsManager[" + i + "]", "Not initialized");
        return false;
    }

    public boolean isQSSSuccessAuthAndLogin() {
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "isQSSSuccessAuthAndLogin");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "isQSSSuccessAuthAndLogin: Not initialized.");
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
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
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
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("isServiceAvailable: ", str, ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("legacyImsManager[")));
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "isServiceAvailable: not connected.");
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
            } finally {
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
            } finally {
            }
        }
        Log.d("legacyImsManager[" + this.mPhoneId + "]", "isVoLteEnabled: not found");
        if (query != null) {
            query.close();
        }
        return false;
    }

    public boolean isVolteEnabledFromNetwork() {
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "isVolteEnabledFromNetwork");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "isVolteEnabledFromNetwork: Not initialized.");
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
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "isVolteSupportECT");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "isVolteSupportECT: Not initialized.");
            return false;
        }
        try {
            return imsService.isVolteSupportEctByPhoneId(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public CmcMediaRecorder newCmcMediaRecorder() throws IllegalStateException {
        IImsService imsService = getImsService();
        if (imsService != null) {
            return new CmcMediaRecorder(imsService, this.mPhoneId);
        }
        Log.d(LOG_TAG, "newCmcMediaRecorder: not connected.");
        return null;
    }

    public int registerAdhocProfile(ImsProfile imsProfile) {
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "registerAdhocProfile");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "registerAdhocProfile: Not initialized.");
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
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "registerAutoConfigurationListener");
        if (iAutoConfigurationListener == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "listener is null.");
            return;
        }
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
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
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "listener is null");
            return;
        }
        this.mEventRelay = dmConfigEventRelay;
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
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
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "registerImSessionListener");
        if (iImSessionListener == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "listener is null.");
            return;
        }
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
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
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "registerImsOngoingFtEventListener");
        if (iImsOngoingFtEventListener == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "registerImsOngoingFtEventListener : wrong instance or null");
            return;
        }
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
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
        CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "sendDeregister");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "sendDeregister");
            return;
        }
        try {
            imsService.sendDeregister(i, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void sendIidToken(String str) {
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "sendIidToken");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return;
        }
        try {
            imsService.sendIidToken(str, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void sendMsisdnNumber(String str) {
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "sendMsisdnNumber");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return;
        }
        try {
            imsService.sendMsisdnNumber(str, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void sendRttMessage(String str) {
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("sendRttMessage: ", str, ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("legacyImsManager[")));
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "setRttMode: not connected.");
            return;
        }
        try {
            imsService.sendRttMessage(str);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void sendRttSessionModifyRequest(int i, boolean z) {
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "sendRttSessionModifyRequest: ");
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "sendRttSessionModifyRequest: not connected.");
            return;
        }
        try {
            imsService.sendRttSessionModifyRequest(i, z);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void sendRttSessionModifyResponse(int i, boolean z) {
        EmergencyButtonController$$ExternalSyntheticOutline0.m("sendRttSessionModifyResponse: ", ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("legacyImsManager[")), z);
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "sendRttSessionModifyResponse: not connected.");
            return;
        }
        try {
            imsService.sendRttSessionModifyResponse(i, z);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void sendTryRegister() {
        CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "sendTryRegister");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "sendTryRegister: Not initialized.");
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
        CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "sendInitialRegister");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "sendInitialRegister");
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
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "sendVerificationCode");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return;
        }
        try {
            imsService.sendVerificationCode(str, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public int setActiveMsisdn(String str, String str2) {
        Log.d(ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("legacyImsManager[")), "setActiveMsisdn: msisdn " + IMSLog.checker(str) + " service " + str2);
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "setActiveMsisdn: Not initialized.");
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
        EmergencyButtonController$$ExternalSyntheticOutline0.m("setAutomaticMode: ", ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("legacyImsManager[")), z);
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "setAutomaticMode: not connected.");
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
        Log.e(ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("legacyImsManager[")), "setEmergencyPdnInfo: intfName " + str);
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "setEmergencyPdnInfo: Not initialized.");
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
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "setRttMode: ", ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("legacyImsManager[")));
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "setRttMode: not connected.");
            return;
        }
        try {
            imsService.setRttMode(this.mPhoneId, i);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public boolean setVideocallType(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "setVideocallType: ", ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("legacyImsManager[")));
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return false;
        }
        try {
            return imsService.setVideocallType(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int startDmConfig() throws RemoteException {
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "startDmConfig");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
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
        RecyclerView$$ExternalSyntheticOutline0.m(i3, ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("legacyImsManager[")), MutableObjectList$$ExternalSyntheticOutline0.m(i, i2, "startLocalRingBackTone: ", ", ", ", "));
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return -1;
        }
        try {
            return imsService.startLocalRingBackTone(i, i2, i3);
        } catch (RemoteException e) {
            Log.e(ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("legacyImsManager[")), "startLocalRingBackTone has Error " + e.getMessage());
            return -1;
        }
    }

    public int stopLocalRingBackTone() {
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "stopLocalRingBackTone:");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return -1;
        }
        try {
            return imsService.stopLocalRingBackTone();
        } catch (RemoteException e) {
            Log.e(ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("legacyImsManager[")), "stopLocalRingBackTone has Error " + e.getMessage());
            return -1;
        }
    }

    public void suspendRegister(boolean z) {
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "SuspendRegi Error. ImsService null.");
            return;
        }
        try {
            imsService.suspendRegister(z, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void transferCall(String str, String str2) {
        Log.d(ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("legacyImsManager[")), "transferCall msisdn : " + IMSLog.checker(str) + ", dialogId : " + str2);
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
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
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "unregisterAutoConfigurationListener");
        if (iAutoConfigurationListener == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "listener is null.");
            return;
        }
        String remove = this.mAutoConfigurationListener.remove(iAutoConfigurationListener);
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized or token null.");
            return;
        }
        if (remove == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "listener is null.");
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
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return;
        }
        try {
            imsService.unregisterDmValueListener(this.mEventProxy);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unregisterImSessionListener(IImSessionListener iImSessionListener) {
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "unregisterImSessionListener");
        if (iImSessionListener == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "listener is null.");
            return;
        }
        String remove = this.mImSessionListeners.remove(iImSessionListener);
        IImsService imsService = getImsService();
        if (imsService == null || remove == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized or token null.");
            return;
        }
        try {
            imsService.unregisterImSessionListener(remove);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unregisterImsOngoingFtEventListener(IImsOngoingFtEventListener iImsOngoingFtEventListener) {
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "unregisterImsOngoingFtEventListener");
        if (iImsOngoingFtEventListener == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "listener is null.");
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
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "updateConfigValues");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
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
        Log.d(ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("legacyImsManager[")), "updateRegistration: profile " + imsProfile.getName());
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "updateRegistration: Not initialized.");
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
    @java.lang.Deprecated
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean isRcsEnabled(boolean r10) {
        /*
            r9 = this;
            java.lang.String r0 = "]"
            java.lang.String r1 = "isRcsEnabled: version "
            java.lang.String r2 = "legacyImsManager["
            com.sec.ims.settings.RcsConfigurationReader r3 = new com.sec.ims.settings.RcsConfigurationReader
            android.content.Context r4 = r9.mContext
            r3.<init>(r4)
            r4 = 1
            r5 = 0
            android.content.Context r6 = r9.mContext     // Catch: android.provider.Settings.SettingNotFoundException -> L1f
            android.content.ContentResolver r6 = r6.getContentResolver()     // Catch: android.provider.Settings.SettingNotFoundException -> L1f
            java.lang.String r7 = "rcs_user_setting"
            int r6 = android.provider.Settings.System.getInt(r6, r7)     // Catch: android.provider.Settings.SettingNotFoundException -> L1f
            if (r6 != r4) goto L39
            r6 = r4
            goto L3a
        L1f:
            r6 = move-exception
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>(r2)
            int r8 = r9.mPhoneId
            r7.append(r8)
            r7.append(r0)
            java.lang.String r7 = r7.toString()
            java.lang.String r8 = "isRcsEnabled: rcs_user_setting is not exist."
            android.util.Log.d(r7, r8)
            r6.printStackTrace()
        L39:
            r6 = r5
        L3a:
            if (r10 != 0) goto L3d
            return r6
        L3d:
            java.lang.String r10 = "root/vers/version"
            int r10 = r3.getInt(r10)     // Catch: java.lang.IllegalStateException -> L7d
            java.lang.String r7 = "true"
            java.lang.String r8 = "info/completed"
            java.lang.String r3 = r3.getString(r8)     // Catch: java.lang.IllegalStateException -> L7a
            boolean r3 = r7.equals(r3)     // Catch: java.lang.IllegalStateException -> L7a
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch: java.lang.IllegalStateException -> L78
            r7.<init>(r2)     // Catch: java.lang.IllegalStateException -> L78
            int r8 = r9.mPhoneId     // Catch: java.lang.IllegalStateException -> L78
            r7.append(r8)     // Catch: java.lang.IllegalStateException -> L78
            r7.append(r0)     // Catch: java.lang.IllegalStateException -> L78
            java.lang.String r7 = r7.toString()     // Catch: java.lang.IllegalStateException -> L78
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.IllegalStateException -> L78
            r8.<init>(r1)     // Catch: java.lang.IllegalStateException -> L78
            r8.append(r10)     // Catch: java.lang.IllegalStateException -> L78
            java.lang.String r1 = " autoConfigComplete "
            r8.append(r1)     // Catch: java.lang.IllegalStateException -> L78
            r8.append(r3)     // Catch: java.lang.IllegalStateException -> L78
            java.lang.String r1 = r8.toString()     // Catch: java.lang.IllegalStateException -> L78
            android.util.Log.d(r7, r1)     // Catch: java.lang.IllegalStateException -> L78
            goto L99
        L78:
            r1 = move-exception
            goto L80
        L7a:
            r1 = move-exception
            r3 = r5
            goto L80
        L7d:
            r1 = move-exception
            r10 = r5
            r3 = r10
        L80:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>(r2)
            int r9 = r9.mPhoneId
            r7.append(r9)
            r7.append(r0)
            java.lang.String r9 = r7.toString()
            java.lang.String r0 = "isRcsEnabled: AutoConfiguration is not completed."
            android.util.Log.d(r9, r0)
            r1.printStackTrace()
        L99:
            if (r6 == 0) goto La0
            if (r3 == 0) goto La1
            if (r10 <= 0) goto La0
            goto La1
        La0:
            r4 = r5
        La1:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.sec.ims.ImsManager.isRcsEnabled(boolean):boolean");
    }

    public int[] getCallCount(int i) {
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return null;
        }
        try {
            return imsService.getCallCount(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
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

    public boolean updateConfigValues(ContentValues contentValues, int i) {
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "updateConfigValues");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return false;
        }
        try {
            return imsService.updateConfigValues(contentValues, i, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ImsManager(Context context, ConnectionListener connectionListener) {
        this.mListener = null;
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
            public void onChangeDmValue(String str, boolean z) throws RemoteException {
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

    public ImsProfile[] getCurrentProfile(int i) {
        ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "getCurrentProfile");
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "Not initialized.");
            return null;
        }
        try {
            return imsService.getCurrentProfileForSlot(i);
        } catch (RemoteException unused) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "fail to get profiles");
            return null;
        }
    }

    public boolean isServiceAvailable(String str, int i) {
        SecNotificationBlockManager$$ExternalSyntheticOutline0.m(i, "isServiceAvailable: ", str, ", ", ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("legacyImsManager[")));
        IImsService imsService = getImsService();
        if (imsService == null) {
            ImsManager$$ExternalSyntheticOutline0.m(new StringBuilder("legacyImsManager["), this.mPhoneId, "]", "isServiceAvailable: not connected.");
            return false;
        }
        try {
            return imsService.isServiceAvailable(str, i, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class EpdgListener extends IEpdgListener.Stub {
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

    public ImsManager(Context context, ConnectionListener connectionListener, int i) {
        this.mListener = null;
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
            public void onChangeDmValue(String str, boolean z) throws RemoteException {
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
}
