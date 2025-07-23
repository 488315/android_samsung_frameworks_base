package com.sec.ims;

import android.content.Context;
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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class CmcManager {
    static final String LOG_TAG = "CmcManager";
    private static final String SERVICE_NAME = "secims";
    private final ArrayMap<ICmcCallEventListener, String> mCmcCallEventListeners;
    private final ArrayMap<ICmcDialogListener, String> mCmcDialogListeners;
    private final ArrayMap<IImsRegistrationListener, String> mCmcRegListeners;
    private final Context mContext;
    private ConnectionListener mListener;
    private int mPhoneId;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        this.mContext = null;
        this.mListener = null;
    }

    private CmcCallCmdResult getCmcCallCmdResult(int i, int i2) {
        return CmcCallCmdResult.getBuilder().setCallId(i).setCmdResult(i2).build();
    }

    private IImsService getImsService() {
        return IImsService.Stub.asInterface(getSystemService(SERVICE_NAME));
    }

    private IBinder getSystemService(String str) {
        try {
            Class<?> cls = Class.forName("android.os.ServiceManager");
            Method method = cls.getMethod("getService", String.class);
            if (method == null) {
                Log.i(LOG_TAG, "Failed to reflect method getService");
                return null;
            }
            Object invoke = method.invoke(cls, str);
            if (invoke != null) {
                return (IBinder) invoke;
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

    private void onConnectService(IImsService iImsService) {
        if (this.mListener == null || iImsService == null) {
            return;
        }
        registerPreviousListeners(iImsService);
        this.mListener.onConnected();
    }

    private void registerPreviousListeners(IImsService iImsService) {
        synchronized (this) {
            try {
                Log.i(LOG_TAG, "registerPreviousListeners:  mCmcRegListeners:" + this.mCmcRegListeners.size() + " mCmcCallEventListeners:" + this.mCmcCallEventListeners.size() + " mCmcDialogListeners:" + this.mCmcDialogListeners.size());
                try {
                    for (IImsRegistrationListener iImsRegistrationListener : this.mCmcRegListeners.keySet()) {
                        String registerCmcRegistrationListenerForSlot = iImsService.registerCmcRegistrationListenerForSlot(iImsRegistrationListener, this.mPhoneId);
                        if (!TextUtils.isEmpty(registerCmcRegistrationListenerForSlot)) {
                            this.mCmcRegListeners.put(iImsRegistrationListener, registerCmcRegistrationListenerForSlot);
                        }
                    }
                    for (ICmcCallEventListener iCmcCallEventListener : this.mCmcCallEventListeners.keySet()) {
                        String registerCmcCallEventListenerForSlot = iImsService.registerCmcCallEventListenerForSlot(this.mPhoneId, iCmcCallEventListener);
                        if (!TextUtils.isEmpty(registerCmcCallEventListenerForSlot)) {
                            this.mCmcCallEventListeners.put(iCmcCallEventListener, registerCmcCallEventListenerForSlot);
                        }
                    }
                    for (ICmcDialogListener iCmcDialogListener : this.mCmcDialogListeners.keySet()) {
                        String registerCmcDialogListenerByToken = iImsService.registerCmcDialogListenerByToken(this.mPhoneId, iCmcDialogListener);
                        if (!TextUtils.isEmpty(registerCmcDialogListenerByToken)) {
                            this.mCmcDialogListeners.put(iCmcDialogListener, registerCmcDialogListenerByToken);
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
        onConnectService(getImsService());
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
            String registerCmcCallEventListenerForSlot = imsService.registerCmcCallEventListenerForSlot(this.mPhoneId, iCmcCallEventListener);
            if (!TextUtils.isEmpty(registerCmcCallEventListenerForSlot)) {
                this.mCmcCallEventListeners.put(iCmcCallEventListener, registerCmcCallEventListenerForSlot);
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
            String registerCmcDialogListenerByToken = imsService.registerCmcDialogListenerByToken(this.mPhoneId, iCmcDialogListener);
            if (!TextUtils.isEmpty(registerCmcDialogListenerByToken)) {
                this.mCmcDialogListeners.put(iCmcDialogListener, registerCmcDialogListenerByToken);
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
            String registerCmcRegistrationListenerForSlot = imsService.registerCmcRegistrationListenerForSlot(iImsRegistrationListener, this.mPhoneId);
            if (!TextUtils.isEmpty(registerCmcRegistrationListenerForSlot)) {
                this.mCmcRegListeners.put(iImsRegistrationListener, registerCmcRegistrationListenerForSlot);
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
        String remove = this.mCmcCallEventListeners.remove(iCmcCallEventListener);
        IImsService imsService = getImsService();
        if (imsService != null && remove != null) {
            try {
                imsService.unregisterCmcCallEventListenerForSlot(this.mPhoneId, remove);
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
        String remove = this.mCmcDialogListeners.remove(iCmcDialogListener);
        IImsService imsService = getImsService();
        if (imsService != null && remove != null) {
            try {
                imsService.unregisterCmcDialogListenerByToken(this.mPhoneId, remove);
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
        String remove = this.mCmcRegListeners.remove(iImsRegistrationListener);
        IImsService imsService = getImsService();
        if (imsService != null && remove != null) {
            try {
                imsService.unregisterCmcRegistrationListenerForSlot(remove, this.mPhoneId);
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
        this.mContext = context;
        this.mListener = connectionListener;
        this.mPhoneId = i;
    }
}
