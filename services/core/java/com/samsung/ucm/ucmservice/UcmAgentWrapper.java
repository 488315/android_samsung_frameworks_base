package com.samsung.ucm.ucmservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Log;

import com.samsung.android.knox.ucm.plugin.agent.IUcmAgentService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public final class UcmAgentWrapper {
    public static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
    public final ComponentName componentName;
    public AgentInfo info;
    public final IUcmAgentManagerDeleteDelegate mAgentDeleteDelegate;
    public final Context mContext;
    public final AnonymousClass1 mHandler;
    public IUcmAgentService mUcmAgentService;
    public boolean mBound = false;
    public final Queue mEventBoxQueue = new LinkedList();
    public final AnonymousClass2 mConnection =
            new ServiceConnection() { // from class: com.samsung.ucm.ucmservice.UcmAgentWrapper.2
                @Override // android.content.ServiceConnection
                public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    String packageName;
                    Log.d("UcmAgentWrapper", "onServiceConnected " + componentName);
                    removeMessages(4);
                    UcmAgentWrapper.this.mUcmAgentService =
                            IUcmAgentService.Stub.asInterface(iBinder);
                    ComponentName componentName2 = UcmAgentWrapper.this.componentName;
                    if (componentName2 == null
                            || (packageName = componentName2.getPackageName()) == null
                            || packageName.isEmpty()) {
                        return;
                    }
                    Log.d("UcmAgentWrapper", "notify binding done : ".concat(packageName));
                    Intent intent =
                            new Intent("com.samsung.android.knox.intent.action.UCM_REFRESH_DONE");
                    intent.putExtra("PackageName", packageName);
                    UcmAgentWrapper.this.mContext.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
                    while (!UcmAgentWrapper.this.mEventBoxQueue.isEmpty()) {
                        EventBox eventBox =
                                (EventBox)
                                        ((LinkedList) UcmAgentWrapper.this.mEventBoxQueue).poll();
                        if (eventBox != null) {
                            try {
                                UcmAgentWrapper ucmAgentWrapper = UcmAgentWrapper.this;
                                int i = eventBox.eventId;
                                Bundle bundle = eventBox.eventData;
                                IUcmAgentService iUcmAgentService =
                                        ucmAgentWrapper.mUcmAgentService;
                                if (iUcmAgentService != null) {
                                    iUcmAgentService.notifyChange(i, bundle);
                                }
                            } catch (RemoteException e) {
                                Log.e(
                                        "UcmAgentWrapper",
                                        "Noti was not sent cause binding, send notifyChange : "
                                                + e);
                                e.printStackTrace();
                            }
                        }
                    }
                }

                @Override // android.content.ServiceConnection
                public final void onServiceDisconnected(ComponentName componentName) {
                    Log.d("UcmAgentWrapper", "onServiceDisconnected " + componentName);
                    UcmAgentWrapper ucmAgentWrapper = UcmAgentWrapper.this;
                    ucmAgentWrapper.mUcmAgentService = null;
                    if (ucmAgentWrapper.mBound) {
                        Log.d("UcmAgentWrapper", "scheduleRestart");
                        ucmAgentWrapper.mHandler.removeMessages(4);
                        ucmAgentWrapper.mHandler.sendEmptyMessageDelayed(4, 5000L);
                    }
                }
            };

    public final class AgentInfo {
        public byte[] AID;
        public String agentId;
        public int authMaxCnt;
        public int authMode;
        public String configuratorList;
        public String enabledSCP;
        public int enabledWrap;
        public boolean enforceManagement;
        public String id;
        public boolean isDetachable;
        public boolean isGeneratePasswordAvailable;
        public boolean isHardwareBacked;
        public boolean isManageable;
        public boolean isODESupport;
        public boolean isPUKSupported;
        public boolean isReadOnly;
        public boolean isSupportBiometricForUCM;
        public boolean isSupportChangePin;
        public boolean isSupportChangePinWithPassword;
        public String packageName;
        public int pinMaxLength;
        public int pinMinLength;
        public int pukMaxLength;
        public int pukMinLength;
        public boolean reqUserVerification;
        public int serviceUid;
        public String storageType;
        public String summary;
        public boolean supportPinConfiguration;
        public boolean supportSign;
        public String title;
        public String vendorId;

        public final String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("system unique id:" + this.id);
            stringBuffer.append(", agentId:" + this.agentId);
            stringBuffer.append(", summary:" + this.summary);
            stringBuffer.append(", title:" + this.title);
            stringBuffer.append(", vendorId:" + this.vendorId);
            stringBuffer.append(", isDetachable:" + this.isDetachable);
            stringBuffer.append(", reqUserVerification:" + this.reqUserVerification);
            stringBuffer.append(", iconResId:0");
            stringBuffer.append(", isHardwareBacked:" + this.isHardwareBacked);
            stringBuffer.append(", pinMinLength:" + this.pinMinLength);
            stringBuffer.append(", pinMaxLength:" + this.pinMaxLength);
            stringBuffer.append(", authMaxCnt:" + this.authMaxCnt);
            stringBuffer.append(", authMode:" + this.authMode);
            stringBuffer.append(
                    ", isGeneratePasswordAvailable:" + this.isGeneratePasswordAvailable);
            stringBuffer.append(", isODESupport:" + this.isODESupport);
            stringBuffer.append(", storageType:" + this.storageType);
            stringBuffer.append(", enabledSCP:" + this.enabledSCP);
            stringBuffer.append(", enabledWrap:" + this.enabledWrap);
            byte[] bArr = this.AID;
            if (bArr != null) {
                try {
                    stringBuffer.append(", AID:".concat(new String(bArr, "UTF-8")));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                stringBuffer.append(", AID: null");
            }
            stringBuffer.append(", isManageable:" + this.isManageable);
            stringBuffer.append(", enforceManagement:" + this.enforceManagement);
            stringBuffer.append(", configuratorList:" + this.configuratorList);
            stringBuffer.append(", serviceUid:" + this.serviceUid);
            stringBuffer.append(", packageName:" + this.packageName);
            stringBuffer.append(", isPUKSupported:" + this.isPUKSupported);
            stringBuffer.append(", supportSign:" + this.supportSign);
            stringBuffer.append(", isSupportChangePin:" + this.isSupportChangePin);
            stringBuffer.append(
                    ", isSupportChangePinWithPassword:" + this.isSupportChangePinWithPassword);
            stringBuffer.append(", supportPinConfiguration:" + this.supportPinConfiguration);
            stringBuffer.append(", isSupportBiometricForUCM:" + this.isSupportBiometricForUCM);
            return stringBuffer.toString();
        }
    }

    public final class EventBox {
        public Bundle eventData;
        public int eventId;
    }

    public UcmAgentWrapper(
            Context context,
            IUcmAgentManagerDeleteDelegate iUcmAgentManagerDeleteDelegate,
            ComponentName componentName) {
        this.mContext = context;
        this.mAgentDeleteDelegate = iUcmAgentManagerDeleteDelegate;
        this.componentName = componentName;
        try {
            Log.d("UcmAgentWrapper", "createHandler. enter");
            HandlerThread handlerThread = new HandlerThread("UcmAgentWrapperHandlerThread");
            handlerThread.start();
            this.mHandler =
                    new Handler(
                            handlerThread
                                    .getLooper()) { // from class:
                                                    // com.samsung.ucm.ucmservice.UcmAgentWrapper.1
                        @Override // android.os.Handler
                        public final void handleMessage(Message message) {
                            if (message.what != 4) {
                                return;
                            }
                            UcmAgentWrapper ucmAgentWrapper = UcmAgentWrapper.this;
                            ucmAgentWrapper.unbind();
                            Log.d("UcmAgentWrapper", "MSG_RESTART_TIMEOUT");
                            UcmServiceAgentManager ucmServiceAgentManager =
                                    (UcmServiceAgentManager) ucmAgentWrapper.mAgentDeleteDelegate;
                            ucmServiceAgentManager.getClass();
                            Log.i("UcmService.UcmAgentManager", "deletAndRefreshAgents()");
                            Log.i(
                                    "UcmService.UcmAgentManager",
                                    "deletAndRefreshAgents() remove " + ucmAgentWrapper);
                            ((ArrayList) ucmServiceAgentManager.getActiveAgentList())
                                    .remove(ucmAgentWrapper);
                            ucmServiceAgentManager.refreshAgentList();
                        }
                    };
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:145:0x033a, code lost:

       if (r8 == null) goto L161;
    */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x033c, code lost:

       r8.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x0343, code lost:

       if (r8 == null) goto L161;
    */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x0340, code lost:

       if (r8 == null) goto L161;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.samsung.ucm.ucmservice.UcmAgentWrapper.AgentInfo getAgentInfo(
            android.content.pm.ResolveInfo r11, android.content.Context r12) {
        /*
            Method dump skipped, instructions count: 891
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.ucm.ucmservice.UcmAgentWrapper.getAgentInfo(android.content.pm.ResolveInfo,"
                    + " android.content.Context):com.samsung.ucm.ucmservice.UcmAgentWrapper$AgentInfo");
    }

    public static byte[] hexStringToByteArray(String str) {
        if (DEBUG) {
            Log.d("UcmAgentWrapper", "hexStringToByteArray : ".concat(str));
        }
        if (str.isEmpty()) {
            Log.d("UcmAgentWrapper", "Input value is empty");
            return null;
        }
        int length = str.length();
        byte[] bArr = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bArr[i / 2] =
                    (byte)
                            (Character.digit(str.charAt(i + 1), 16)
                                    + (Character.digit(str.charAt(i), 16) << 4));
        }
        return bArr;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof UcmAgentWrapper) {
            return this.componentName.equals(((UcmAgentWrapper) obj).componentName);
        }
        return false;
    }

    public final int hashCode() {
        return this.componentName.hashCode() * 31;
    }

    public final void initialize(ResolveInfo resolveInfo, UserHandle userHandle) {
        ComponentName componentName;
        if (resolveInfo.serviceInfo == null) {
            componentName = null;
        } else {
            ServiceInfo serviceInfo = resolveInfo.serviceInfo;
            componentName = new ComponentName(serviceInfo.packageName, serviceInfo.name);
        }
        Log.d("UcmAgentWrapper", "initialize " + componentName + " user: " + userHandle);
        this.info = getAgentInfo(resolveInfo, this.mContext);
        Intent intent = new Intent();
        intent.setComponent(componentName);
        Log.d("UcmAgentWrapper", "scheduleRestart");
        removeMessages(4);
        sendEmptyMessageDelayed(4, 5000L);
        Context context = this.mContext;
        if (context != null) {
            this.mBound = context.bindServiceAsUser(intent, this.mConnection, 1, userHandle);
        }
        if (this.mBound) {
            return;
        }
        Log.e("UcmAgentWrapper", "not able to bind " + componentName);
    }

    public final boolean isServiceBound() {
        return this.mUcmAgentService != null;
    }

    public final Bundle saw(Bundle bundle) {
        IUcmAgentService iUcmAgentService = this.mUcmAgentService;
        if (iUcmAgentService != null) {
            return iUcmAgentService.saw(bundle);
        }
        return null;
    }

    public final void unbind() {
        if (!this.mBound) {
            Log.e("UcmAgentWrapper", "it is not bound anymore");
            return;
        }
        Log.d("UcmAgentWrapper", "unbind ");
        try {
            this.mContext.unbindService(this.mConnection);
        } catch (Exception e) {
            Log.e("UcmAgentWrapper", "unbind. Exception [" + e.toString() + "]");
        }
        this.mBound = false;
        this.mUcmAgentService = null;
    }
}
