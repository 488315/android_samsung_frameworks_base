package com.android.server.knox.dar.ddar.ta;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.ParcelFileDescriptor;
import android.os.ServiceManager;

import com.android.server.UserspaceRebootLogger$$ExternalSyntheticOutline0;
import com.android.server.knox.dar.ddar.DDLog;

import com.samsung.android.knox.dar.ddar.proxy.IProxyAgentService;

import vendor.samsung.hardware.tlc.ddar.ISehDdar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class TAProxy extends IProxyAgentService {
    public static TAProxy mInstance;
    public ISehDdar iSehDdar;
    public Map mTAMap;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TAInfo {
        public int maxSendCmdSize;
        public TZNative ta;
        public int taId;
        public String taProcessName;
        public String taRootName;
        public String taTechnology;
    }

    public final boolean loadTARequest(Bundle bundle) {
        boolean z = false;
        DDLog.d("TAProxy", "loadTARequest called", new Object[0]);
        int i = bundle.getInt("TA_ID");
        ParcelFileDescriptor parcelFileDescriptor =
                (ParcelFileDescriptor) bundle.getParcelable("TA_FD");
        long j = bundle.getLong("TA_FD_OFFSET");
        long j2 = bundle.getLong("TA_FD_SIZE");
        DDLog.d("TAProxy", "TAProxy::loadTA", new Object[0]);
        int fd = parcelFileDescriptor != null ? parcelFileDescriptor.getFd() : -1;
        StringBuilder m =
                UserspaceRebootLogger$$ExternalSyntheticOutline0.m(fd, "TA fd=", j, " offset=");
        m.append(" size=");
        m.append(j2);
        DDLog.d("TAProxy", m.toString(), new Object[0]);
        try {
            TAInfo tAInfo = (TAInfo) ((HashMap) this.mTAMap).get(Integer.valueOf(i));
            try {
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (tAInfo == null) {
                DDLog.d("TAProxy", "TAProxy::loadTA failed. TAInfo is null on Map.", new Object[0]);
                if (parcelFileDescriptor != null) {
                    parcelFileDescriptor.close();
                }
                return z;
            }
            z = tAInfo.ta.loadTA(fd, j, j2);
            if (z) {
                updateServiceHolder(true);
            }
            if (parcelFileDescriptor != null) {
                parcelFileDescriptor.close();
            }
            return z;
            return z;
        } catch (Throwable th) {
            if (parcelFileDescriptor != null) {
                try {
                    parcelFileDescriptor.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x006c, code lost:

       if (r7.equals("SETUP_TA") != false) goto L25;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Bundle onMessage(int r6, java.lang.String r7, android.os.Bundle r8) {
        /*
            r5 = this;
            java.lang.String r6 = "onMessage() "
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            java.lang.String r2 = "TAProxy"
            java.lang.String r3 = "enforceCallingUser"
            com.android.server.knox.dar.ddar.DDLog.d(r2, r3, r1)
            int r1 = android.os.Binder.getCallingUid()
            int r3 = android.os.UserHandle.getAppId(r1)
            r4 = 5250(0x1482, float:7.357E-42)
            if (r3 != r4) goto L1b
            goto L25
        L1b:
            int r1 = android.os.UserHandle.getAppId(r1)
            int r3 = android.os.Process.myUid()
            if (r1 != r3) goto L9f
        L25:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> L50
            r1.<init>(r6)     // Catch: java.lang.Exception -> L50
            r1.append(r7)     // Catch: java.lang.Exception -> L50
            java.lang.String r6 = r1.toString()     // Catch: java.lang.Exception -> L50
            java.lang.Object[] r1 = new java.lang.Object[r0]     // Catch: java.lang.Exception -> L50
            com.android.server.knox.dar.ddar.DDLog.d(r2, r6, r1)     // Catch: java.lang.Exception -> L50
            android.os.Bundle r6 = new android.os.Bundle     // Catch: java.lang.Exception -> L50
            r6.<init>()     // Catch: java.lang.Exception -> L50
            int r1 = r7.hashCode()     // Catch: java.lang.Exception -> L50
            r2 = 2
            r3 = 3
            r4 = 1
            switch(r1) {
                case -2076560497: goto L66;
                case 540100525: goto L5c;
                case 1054654566: goto L52;
                case 1663958331: goto L46;
                default: goto L45;
            }     // Catch: java.lang.Exception -> L50
        L45:
            goto L6f
        L46:
            java.lang.String r0 = "PROCESS_COMMAND"
            boolean r7 = r7.equals(r0)     // Catch: java.lang.Exception -> L50
            if (r7 == 0) goto L6f
            r0 = r3
            goto L70
        L50:
            r5 = move-exception
            goto L9a
        L52:
            java.lang.String r0 = "LOAD_TA"
            boolean r7 = r7.equals(r0)     // Catch: java.lang.Exception -> L50
            if (r7 == 0) goto L6f
            r0 = r4
            goto L70
        L5c:
            java.lang.String r0 = "UNLOAD_TA"
            boolean r7 = r7.equals(r0)     // Catch: java.lang.Exception -> L50
            if (r7 == 0) goto L6f
            r0 = r2
            goto L70
        L66:
            java.lang.String r1 = "SETUP_TA"
            boolean r7 = r7.equals(r1)     // Catch: java.lang.Exception -> L50
            if (r7 == 0) goto L6f
            goto L70
        L6f:
            r0 = -1
        L70:
            java.lang.String r7 = "dual_dar_response"
            if (r0 == 0) goto L93
            if (r0 == r4) goto L8b
            if (r0 == r2) goto L84
            if (r0 == r3) goto L7c
            goto L99
        L7c:
            android.os.Bundle r5 = r5.processCommandRequest(r8)     // Catch: java.lang.Exception -> L50
            r6.putBundle(r7, r5)     // Catch: java.lang.Exception -> L50
            goto L99
        L84:
            r5.unloadTARequest(r8)     // Catch: java.lang.Exception -> L50
            r6.putBoolean(r7, r4)     // Catch: java.lang.Exception -> L50
            goto L99
        L8b:
            boolean r5 = r5.loadTARequest(r8)     // Catch: java.lang.Exception -> L50
            r6.putBoolean(r7, r5)     // Catch: java.lang.Exception -> L50
            goto L99
        L93:
            r5.setupTARequest(r8)     // Catch: java.lang.Exception -> L50
            r6.putBoolean(r7, r4)     // Catch: java.lang.Exception -> L50
        L99:
            return r6
        L9a:
            r5.printStackTrace()
            r5 = 0
            return r5
        L9f:
            java.lang.SecurityException r5 = new java.lang.SecurityException
            java.lang.String r6 = "Can only be called by system user"
            r5.<init>(r6)
            throw r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.server.knox.dar.ddar.ta.TAProxy.onMessage(int,"
                    + " java.lang.String, android.os.Bundle):android.os.Bundle");
    }

    public final Bundle processCommandRequest(Bundle bundle) {
        DDLog.d("TAProxy", "processCommandRequest called", new Object[0]);
        int i = bundle.getInt("TA_ID");
        Bundle bundle2 = new Bundle();
        TACommandRequest tACommandRequest = new TACommandRequest();
        tACommandRequest.init(
                bundle.getInt("TA_VERSION"),
                bundle.getByteArray("TA_MAGICNUM"),
                bundle.getInt("TA_CMD_ID"),
                bundle.getByteArray("TA_CMD_DATA"));
        DDLog.d(
                "TAProxy",
                "TAProxy::processTACommand: request = "
                        + tACommandRequest
                        + "; request.mCommandId = "
                        + tACommandRequest.mCommandId
                        + "; this.mTAId = "
                        + i,
                new Object[0]);
        TACommandResponse processTACommand =
                ((TAInfo) ((HashMap) this.mTAMap).get(Integer.valueOf(i)))
                        .ta.processTACommand(tACommandRequest);
        if (processTACommand != null) {
            bundle2.putInt("TA_RESP_CODE", processTACommand.mResponseCode);
            bundle2.putString("TA_ERROR_MSG", processTACommand.mErrorMsg);
            bundle2.putByteArray("TA_RESPDATA", processTACommand.mResponse);
        }
        return bundle2;
    }

    public final void setupTARequest(Bundle bundle) {
        DDLog.d("TAProxy", "setupTARequest called", new Object[0]);
        ArrayList parcelableArrayList = bundle.getParcelableArrayList("ALL_TAS");
        for (int i = 0; i < parcelableArrayList.size(); i++) {
            Bundle bundle2 = (Bundle) parcelableArrayList.get(i);
            int i2 = bundle2.getInt("TA_ID");
            if (!((HashMap) this.mTAMap).containsKey(Integer.valueOf(i2))) {
                TAInfo tAInfo = new TAInfo();
                tAInfo.taId = i2;
                tAInfo.taTechnology = bundle2.getString("TA_TECHNOLOGY");
                tAInfo.taRootName = bundle2.getString("TA_ROOT_NAME");
                tAInfo.taProcessName = bundle2.getString("TA_PROCESS_NAME");
                tAInfo.maxSendCmdSize = bundle2.getInt("TA_SEND_CMD_SIZE");
                int i3 = bundle2.getInt("TA_RECV_RESP_SIZE");
                int i4 = tAInfo.taId;
                String str = tAInfo.taTechnology;
                String str2 = tAInfo.taRootName;
                String str3 = tAInfo.taProcessName;
                int i5 = tAInfo.maxSendCmdSize;
                TZNative tZNative = new TZNative();
                DDLog.d(
                        "DualDAR:TZNative",
                        VibrationParam$1$$ExternalSyntheticOutline0.m(
                                i4, "TZNative constructor: taId = "),
                        new Object[0]);
                tZNative.mTAId = i4;
                tZNative.mDDARTZNativePtr_ = 0L;
                tZNative.mSendBufSize = i5;
                tZNative.mRecvBufSize = i3;
                tZNative.mTATechnology = str;
                tZNative.mRootName = str2;
                tZNative.mProcessName = str3;
                tZNative.mIsLoaded = false;
                tAInfo.ta = tZNative;
                ((HashMap) this.mTAMap).put(Integer.valueOf(tAInfo.taId), tAInfo);
            }
        }
        DDLog.d("TAProxy", "setupTARequest completed", new Object[0]);
    }

    public final void unloadTARequest(Bundle bundle) {
        DDLog.d("TAProxy", "unloadTARequest called", new Object[0]);
        int i = bundle.getInt("TA_ID");
        DDLog.d("TAProxy", "TAProxy::unloadTA", new Object[0]);
        TAInfo tAInfo = (TAInfo) ((HashMap) this.mTAMap).get(Integer.valueOf(i));
        if (tAInfo == null) {
            DDLog.d("TAProxy", "TAProxy::unloadTA failed. TAInfo is null on Map.", new Object[0]);
            return;
        }
        TZNative tZNative = tAInfo.ta;
        tZNative.getClass();
        synchronized (TZNative.class) {
            if (tZNative.mDDARTZNativePtr_ != 0 && tZNative.mIsLoaded) {
                tZNative.mIsLoaded = false;
                tZNative.nativeDDARDestroyTLCommunicationContext();
                tZNative.mDDARTZNativePtr_ = 0L;
                DDLog.d("DualDAR:TZNative", "TZNative::unloadTA called", new Object[0]);
            }
            DDLog.e(
                    "DualDAR:TZNative",
                    "TZNative::unloadTA called for TA that is not loaded. Call Ignored: ta loaded: "
                            + tZNative.mIsLoaded,
                    new Object[0]);
        }
        ((HashMap) this.mTAMap).remove(Integer.valueOf(i));
        if (((HashMap) this.mTAMap).size() <= 0) {
            updateServiceHolder(false);
        }
    }

    public final void updateServiceHolder(boolean z) {
        try {
            if (Integer.parseInt("34") >= 34) {
                boolean isDeclared =
                        ServiceManager.isDeclared(
                                "vendor.samsung.hardware.tlc.ddar.ISehDdar/default");
                DDLog.d(
                        "TAProxy",
                        "updateServiceHolder: " + isDeclared + ", " + z + ", " + this.iSehDdar,
                        new Object[0]);
                if (isDeclared) {
                    ISehDdar iSehDdar = null;
                    if (!z) {
                        this.iSehDdar = null;
                        return;
                    }
                    ISehDdar iSehDdar2 = this.iSehDdar;
                    if (iSehDdar2 == null) {
                        IBinder waitForService =
                                ServiceManager.waitForService(
                                        "vendor.samsung.hardware.tlc.ddar.ISehDdar/default");
                        int i = ISehDdar.Stub.$r8$clinit;
                        if (waitForService != null) {
                            IInterface queryLocalInterface =
                                    waitForService.queryLocalInterface(ISehDdar.DESCRIPTOR);
                            if (queryLocalInterface == null
                                    || !(queryLocalInterface instanceof ISehDdar)) {
                                ISehDdar.Stub.Proxy proxy = new ISehDdar.Stub.Proxy();
                                proxy.mRemote = waitForService;
                                iSehDdar = proxy;
                            } else {
                                iSehDdar = (ISehDdar) queryLocalInterface;
                            }
                        }
                        iSehDdar2 = iSehDdar;
                    }
                    this.iSehDdar = iSehDdar2;
                }
            }
        } catch (Exception e) {
            DDLog.e("TAProxy", "updateServiceHolder failed: " + e, new Object[0]);
        }
    }
}
