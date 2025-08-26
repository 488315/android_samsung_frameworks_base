package com.sec.ims;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.sec.ims.IImsService;
import com.sec.ims.cmc.CmcCallCmdInfo;
import com.sec.ims.cmc.CmcCallCmdResult;
import com.sec.ims.cmc.ICmcCallEventListener;
import com.sec.ims.cmc.ICmcDialogListener;
import com.sec.ims.util.IMSLog;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class CmcManager {
    private static final String INTENT_ACTION_IMSSERVICE_CLASS = "com.sec.internal.ims.imsservice.ImsService";
    private static final String INTENT_ACTION_IMSSERVICE_PACKAGE = "com.sec.imsservice";
    static final String LOG_TAG = "CmcManager";
    private static final String SERVICE_NAME = "secims";
    private final ArrayMap<ICmcCallEventListener, String> mCmcCallEventListeners;
    private final ArrayMap<ICmcDialogListener, String> mCmcDialogListeners;
    private final ArrayMap<IImsRegistrationListener, String> mCmcRegListeners;
    private final CmcServiceConnection mCmcServiceConnection;
    private final Context mContext;
    private ConnectionListener mListener;
    private int mPhoneId;

    class CmcServiceConnection implements ServiceConnection {
        private boolean bind;
        private IImsService service;

        public /* synthetic */ CmcServiceConnection(CmcManager cmcManager, int i) {
            this();
        }

        public boolean getBind() {
            return this.bind;
        }

        public IImsService getService() {
            return this.service;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(CmcManager.LOG_TAG, "connected : " + componentName);
            IImsService iImsServiceAsInterface = IImsService.Stub.asInterface(iBinder);
            this.service = iImsServiceAsInterface;
            this.bind = iImsServiceAsInterface != null;
            CmcManager.this.onConnectService(iImsServiceAsInterface);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(CmcManager.LOG_TAG, "disconnected : " + componentName);
            this.service = null;
            this.bind = false;
            CmcManager.this.onDisconnectService();
        }

        private CmcServiceConnection() {
            this.service = null;
            this.bind = false;
        }
    }

    public interface ConnectionListener {
        void onConnected();

        void onDisconnected();
    }

    public CmcManager() {
        this.mListener = null;
        this.mPhoneId = 0;
        this.mCmcRegListeners = new ArrayMap<>();
        this.mCmcCallEventListeners = new ArrayMap<>();
        this.mCmcDialogListeners = new ArrayMap<>();
        this.mCmcServiceConnection = new CmcServiceConnection(this, 0);
        this.mContext = null;
        this.mListener = null;
    }

    private void bindImsService() {
        if (this.mContext == null) {
            Log.i(LOG_TAG, "bind imsservice failed. context is null");
            return;
        }
        Log.i(LOG_TAG, "bindImsService bind:" + this.mCmcServiceConnection.getBind());
        Intent intent = new Intent();
        intent.setClassName("com.sec.imsservice", INTENT_ACTION_IMSSERVICE_CLASS);
        this.mContext.bindService(intent, this.mCmcServiceConnection, 3);
    }

    private CmcCallCmdResult getCmcCallCmdResult(int i, int i2) {
        return CmcCallCmdResult.getBuilder().setCallId(i).setCmdResult(i2).build();
    }

    private IImsService getImsService() {
        IImsService service = this.mCmcServiceConnection.getService();
        if (service != null) {
            return service;
        }
        IImsService iImsServiceAsInterface = IImsService.Stub.asInterface(getSystemService(SERVICE_NAME));
        Log.i(LOG_TAG, "imsService : " + iImsServiceAsInterface);
        return iImsServiceAsInterface;
    }

    private IBinder getSystemService(String str) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        try {
            Class<?> cls = Class.forName("android.os.ServiceManager");
            Method method = cls.getMethod("getService", String.class);
            if (method == null) {
                Log.i(LOG_TAG, "Failed to reflect method getService");
                return null;
            }
            Object objInvoke = method.invoke(cls, str);
            if (objInvoke != null) {
                return (IBinder) objInvoke;
            }
            Log.i(LOG_TAG, "Failed to getService " + str);
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

    /* JADX INFO: Access modifiers changed from: private */
    public void onDisconnectService() {
        ConnectionListener connectionListener = this.mListener;
        if (connectionListener != null) {
            connectionListener.onDisconnected();
        }
    }

    private void registerPreviousListeners(IImsService iImsService) {
        synchronized (this) {
            try {
                Log.i(LOG_TAG, "registerPreviousListeners:  mCmcRegListeners:" + this.mCmcRegListeners.size() + " mCmcCallEventListeners:" + this.mCmcCallEventListeners.size() + " mCmcDialogListeners:" + this.mCmcDialogListeners.size());
                try {
                    for (IImsRegistrationListener iImsRegistrationListener : this.mCmcRegListeners.keySet()) {
                        String strRegisterCmcRegistrationListenerForSlot = iImsService.registerCmcRegistrationListenerForSlot(iImsRegistrationListener, this.mPhoneId);
                        if (!TextUtils.isEmpty(strRegisterCmcRegistrationListenerForSlot)) {
                            this.mCmcRegListeners.put(iImsRegistrationListener, strRegisterCmcRegistrationListenerForSlot);
                        }
                    }
                    for (ICmcCallEventListener iCmcCallEventListener : this.mCmcCallEventListeners.keySet()) {
                        String strRegisterCmcCallEventListenerForSlot = iImsService.registerCmcCallEventListenerForSlot(this.mPhoneId, iCmcCallEventListener);
                        if (!TextUtils.isEmpty(strRegisterCmcCallEventListenerForSlot)) {
                            this.mCmcCallEventListeners.put(iCmcCallEventListener, strRegisterCmcCallEventListenerForSlot);
                        }
                    }
                    for (ICmcDialogListener iCmcDialogListener : this.mCmcDialogListeners.keySet()) {
                        String strRegisterCmcDialogListenerByToken = iImsService.registerCmcDialogListenerByToken(this.mPhoneId, iCmcDialogListener);
                        if (!TextUtils.isEmpty(strRegisterCmcDialogListenerByToken)) {
                            this.mCmcDialogListeners.put(iCmcDialogListener, strRegisterCmcDialogListenerByToken);
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

    public CmcCallCmdResult cmcAnswerCall(int i) {
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "cmcAnswerCall callId : ", ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("CmcManager[")));
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("CmcManager["), this.mPhoneId, "]", "Not initialized.");
            return getCmcCallCmdResult(i, 3);
        }
        CmcCallCmdResult cmcCallCmdResult = getCmcCallCmdResult(i, 2);
        try {
            return imsService.cmcAnswerCall(this.mPhoneId, i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return cmcCallCmdResult;
        }
    }

    public CmcCallCmdResult cmcEndCall(int i) {
        return cmcEndCall(i, 26);
    }

    public CmcCallCmdResult cmcHoldCall(int i) {
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "cmcHoldCall callId : ", ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("CmcManager[")));
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("CmcManager["), this.mPhoneId, "]", "Not initialized.");
            return getCmcCallCmdResult(i, 3);
        }
        CmcCallCmdResult cmcCallCmdResult = getCmcCallCmdResult(i, 2);
        try {
            return imsService.cmcHoldCall(this.mPhoneId, i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return cmcCallCmdResult;
        }
    }

    public CmcCallCmdResult cmcMakeCall(String str, CmcCallCmdInfo cmcCallCmdInfo) {
        Log.i(ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("CmcManager[")), "cmcMakeCall callee : " + IMSLog.checker(str) + " " + cmcCallCmdInfo);
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("CmcManager["), this.mPhoneId, "]", "Not initialized.");
            return getCmcCallCmdResult(-1, 3);
        }
        CmcCallCmdResult cmcCallCmdResult = getCmcCallCmdResult(-1, 2);
        try {
            return imsService.cmcMakeCall(this.mPhoneId, str, cmcCallCmdInfo);
        } catch (RemoteException e) {
            e.printStackTrace();
            return cmcCallCmdResult;
        }
    }

    public CmcCallCmdResult cmcPullCall(String str) {
        KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("cmcPullCall dialogId : ", str, ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("CmcManager[")));
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("CmcManager["), this.mPhoneId, "]", "Not initialized.");
            return getCmcCallCmdResult(-1, 3);
        }
        CmcCallCmdResult cmcCallCmdResult = getCmcCallCmdResult(-1, 2);
        try {
            return imsService.cmcPullCall(this.mPhoneId, str);
        } catch (RemoteException e) {
            e.printStackTrace();
            return cmcCallCmdResult;
        }
    }

    public CmcCallCmdResult cmcRejectCall(int i) {
        return cmcRejectCall(i, 3);
    }

    public CmcCallCmdResult cmcResumeCall(int i) {
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "cmcResumeCall callId : ", ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("CmcManager[")));
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("CmcManager["), this.mPhoneId, "]", "Not initialized.");
            return getCmcCallCmdResult(i, 3);
        }
        CmcCallCmdResult cmcCallCmdResult = getCmcCallCmdResult(i, 2);
        try {
            return imsService.cmcResumeCall(this.mPhoneId, i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return cmcCallCmdResult;
        }
    }

    public CmcCallCmdResult cmcSendDtmf(int i, char c) {
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "cmcSendDtmf callId : ", ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("CmcManager[")));
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("CmcManager["), this.mPhoneId, "]", "Not initialized.");
            return getCmcCallCmdResult(i, 3);
        }
        CmcCallCmdResult cmcCallCmdResult = getCmcCallCmdResult(i, 2);
        try {
            return imsService.cmcSendDtmf(this.mPhoneId, i, c);
        } catch (RemoteException e) {
            e.printStackTrace();
            return cmcCallCmdResult;
        }
    }

    public CmcCallCmdResult cmcStartDtmf(int i, char c) {
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "cmcStartDtmf callId : ", ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("CmcManager[")));
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("CmcManager["), this.mPhoneId, "]", "Not initialized.");
            return getCmcCallCmdResult(i, 3);
        }
        CmcCallCmdResult cmcCallCmdResult = getCmcCallCmdResult(i, 2);
        try {
            return imsService.cmcStartDtmf(this.mPhoneId, i, c);
        } catch (RemoteException e) {
            e.printStackTrace();
            return cmcCallCmdResult;
        }
    }

    public CmcCallCmdResult cmcStopDtmf(int i) {
        ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i, "cmcStopDtmf callId : ", ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("CmcManager[")));
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("CmcManager["), this.mPhoneId, "]", "Not initialized.");
            return getCmcCallCmdResult(i, 3);
        }
        CmcCallCmdResult cmcCallCmdResult = getCmcCallCmdResult(i, 2);
        try {
            return imsService.cmcStopDtmf(this.mPhoneId, i);
        } catch (RemoteException e) {
            e.printStackTrace();
            return cmcCallCmdResult;
        }
    }

    public void connectService() {
        bindImsService();
    }

    public boolean isCmcEmergencyCallSupported() {
        Log.i(LOG_TAG, "isCmcEmergencyCallSupported");
        IImsService imsService = getImsService();
        if (imsService == null) {
            Log.i(LOG_TAG, "isCmcEmergencyCallSupported: Not initialized.");
            return false;
        }
        try {
            return imsService.isCmcEmergencyCallSupported(this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isCmcEmergencyNumber(String str) {
        Log.i(LOG_TAG, "isCmcEmergencyNumber");
        IImsService imsService = getImsService();
        if (imsService == null) {
            Log.i(LOG_TAG, "isCmcEmergencyNumber: Not initialized.");
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
        Log.i(LOG_TAG, "isCmcPotentialEmergencyNumber");
        IImsService imsService = getImsService();
        if (imsService == null) {
            Log.i(LOG_TAG, "isCmcPotentialEmergencyNumber: Not initialized.");
            return false;
        }
        try {
            return imsService.isCmcPotentialEmergencyNumber(str, this.mPhoneId);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized void registerCmcCallEventListener(ICmcCallEventListener iCmcCallEventListener) {
        Log.i("CmcManager[" + this.mPhoneId + "]", "registerCmcCallEventListener");
        if (iCmcCallEventListener == null) {
            Log.e("CmcManager[" + this.mPhoneId + "]", "listener is null.");
            return;
        }
        IImsService imsService = getImsService();
        if (imsService == null) {
            Log.e("CmcManager[" + this.mPhoneId + "]", "Not initialized.");
            this.mCmcCallEventListeners.put(iCmcCallEventListener, "");
            return;
        }
        try {
            String strRegisterCmcCallEventListenerForSlot = imsService.registerCmcCallEventListenerForSlot(this.mPhoneId, iCmcCallEventListener);
            if (!TextUtils.isEmpty(strRegisterCmcCallEventListenerForSlot)) {
                this.mCmcCallEventListeners.put(iCmcCallEventListener, strRegisterCmcCallEventListenerForSlot);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public synchronized void registerCmcDialogListener(ICmcDialogListener iCmcDialogListener) {
        Log.i("CmcManager[" + this.mPhoneId + "]", "registerCmcDialogListener");
        if (iCmcDialogListener == null) {
            Log.e("CmcManager[" + this.mPhoneId + "]", "listener is null.");
            return;
        }
        IImsService imsService = getImsService();
        if (imsService == null) {
            Log.e("CmcManager[" + this.mPhoneId + "]", "Not initialized.");
            this.mCmcDialogListeners.put(iCmcDialogListener, "");
            return;
        }
        try {
            String strRegisterCmcDialogListenerByToken = imsService.registerCmcDialogListenerByToken(this.mPhoneId, iCmcDialogListener);
            if (!TextUtils.isEmpty(strRegisterCmcDialogListenerByToken)) {
                this.mCmcDialogListeners.put(iCmcDialogListener, strRegisterCmcDialogListenerByToken);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public synchronized void registerCmcRegistrationListener(IImsRegistrationListener iImsRegistrationListener) {
        Log.i("CmcManager[" + this.mPhoneId + "]", "registerCmcRegistrationListener");
        if (iImsRegistrationListener == null) {
            Log.e("CmcManager[" + this.mPhoneId + "]", "listener is null.");
            return;
        }
        IImsService imsService = getImsService();
        if (imsService == null) {
            Log.e("CmcManager[" + this.mPhoneId + "]", "Not initialized.");
            this.mCmcRegListeners.put(iImsRegistrationListener, "");
            return;
        }
        try {
            String strRegisterCmcRegistrationListenerForSlot = imsService.registerCmcRegistrationListenerForSlot(iImsRegistrationListener, this.mPhoneId);
            if (!TextUtils.isEmpty(strRegisterCmcRegistrationListenerForSlot)) {
                this.mCmcRegListeners.put(iImsRegistrationListener, strRegisterCmcRegistrationListenerForSlot);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public synchronized void unregisterCmcCallEventListener(ICmcCallEventListener iCmcCallEventListener) {
        Log.i("CmcManager[" + this.mPhoneId + "]", "unregisterCmcCallEventListener");
        if (iCmcCallEventListener == null) {
            Log.e("CmcManager[" + this.mPhoneId + "]", "listener is null.");
            return;
        }
        String strRemove = this.mCmcCallEventListeners.remove(iCmcCallEventListener);
        IImsService imsService = getImsService();
        if (imsService != null && strRemove != null) {
            try {
                imsService.unregisterCmcCallEventListenerForSlot(this.mPhoneId, strRemove);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("CmcManager[" + this.mPhoneId + "]", "Not initialized or token null.");
        }
    }

    public synchronized void unregisterCmcDialogListener(ICmcDialogListener iCmcDialogListener) {
        Log.i("CmcManager[" + this.mPhoneId + "]", "unregisterCmcDialogListener");
        if (iCmcDialogListener == null) {
            Log.e("CmcManager[" + this.mPhoneId + "]", "listener is null.");
            return;
        }
        String strRemove = this.mCmcDialogListeners.remove(iCmcDialogListener);
        IImsService imsService = getImsService();
        if (imsService != null && strRemove != null) {
            try {
                imsService.unregisterCmcDialogListenerByToken(this.mPhoneId, strRemove);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("CmcManager[" + this.mPhoneId + "]", "Not initialized or token null.");
        }
    }

    public synchronized void unregisterCmcRegistrationListener(IImsRegistrationListener iImsRegistrationListener) {
        Log.i("CmcManager[" + this.mPhoneId + "]", "unregisterCmcRegistrationListener");
        if (iImsRegistrationListener == null) {
            Log.e("CmcManager[" + this.mPhoneId + "]", "listener is null.");
            return;
        }
        String strRemove = this.mCmcRegListeners.remove(iImsRegistrationListener);
        IImsService imsService = getImsService();
        if (imsService != null && strRemove != null) {
            try {
                imsService.unregisterCmcRegistrationListenerForSlot(strRemove, this.mPhoneId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("CmcManager[" + this.mPhoneId + "]", "Not initialized or token null.");
        }
    }

    public CmcCallCmdResult cmcEndCall(int i, int i2) {
        Log.i(ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("CmcManager[")), "cmcEndCall callId : " + i + " endReason : " + i2);
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("CmcManager["), this.mPhoneId, "]", "Not initialized.");
            return getCmcCallCmdResult(i, 3);
        }
        CmcCallCmdResult cmcCallCmdResult = getCmcCallCmdResult(i, 2);
        try {
            return imsService.cmcEndCall(this.mPhoneId, i, i2);
        } catch (RemoteException e) {
            e.printStackTrace();
            return cmcCallCmdResult;
        }
    }

    public CmcCallCmdResult cmcRejectCall(int i, int i2) {
        Log.i(ReorderTile$$ExternalSyntheticOutline0.m(this.mPhoneId, "]", new StringBuilder("CmcManager[")), "cmcRejectCall callId : " + i + " rejectReason : " + i2);
        IImsService imsService = getImsService();
        if (imsService == null) {
            CmcManager$$ExternalSyntheticOutline0.m(new StringBuilder("CmcManager["), this.mPhoneId, "]", "Not initialized.");
            return getCmcCallCmdResult(i, 3);
        }
        CmcCallCmdResult cmcCallCmdResult = getCmcCallCmdResult(i, 2);
        try {
            return imsService.cmcRejectCall(this.mPhoneId, i, i2);
        } catch (RemoteException e) {
            e.printStackTrace();
            return cmcCallCmdResult;
        }
    }

    public CmcManager(Context context, ConnectionListener connectionListener, int i) {
        this.mListener = null;
        this.mPhoneId = 0;
        this.mCmcRegListeners = new ArrayMap<>();
        this.mCmcCallEventListeners = new ArrayMap<>();
        this.mCmcDialogListeners = new ArrayMap<>();
        this.mCmcServiceConnection = new CmcServiceConnection(this, 0);
        this.mContext = context;
        this.mListener = connectionListener;
        this.mPhoneId = i;
    }
}
