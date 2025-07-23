package com.samsung.android.knox.sdp.core;

import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.dar.IDarManagerService;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SdpEngine {
    private static final String CLASS_NAME = "SdpEngine";
    private static final String TAG = "SdpEngine";
    private static SdpEngine _instance;
    private IDarManagerService mService = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
    private final ContextInfo mContextInfo = new ContextInfo(Binder.getCallingUid());

    private SdpEngine() {
    }

    private static void enforcePermission() throws SdpException {
        IDarManagerService asInterface = IDarManagerService.Stub.asInterface(ServiceManager.getService("dar"));
        if (asInterface != null) {
            try {
                if (asInterface.isLicensed() == 0) {
                } else {
                    throw new SdpException(-9);
                }
            } catch (RemoteException e) {
                Log.e("SdpEngine", "Failed to talk with sdp service...", e);
            }
        }
    }

    public static SdpEngine getInstance() throws SdpException {
        enforcePermission();
        if (_instance == null) {
            _instance = new SdpEngine();
        }
        return _instance;
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x001c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void addEngine(com.samsung.android.knox.sdp.core.SdpCreationParam r3, java.lang.String r4, java.lang.String r5) throws com.samsung.android.knox.sdp.core.SdpException {
        /*
            r2 = this;
            com.samsung.android.knox.ContextInfo r0 = r2.mContextInfo
            java.lang.String r1 = "SdpEngine.addEngine"
            com.samsung.android.knox.license.EnterpriseLicenseManager.log(r0, r1)
            com.samsung.android.knox.dar.IDarManagerService r2 = r2.mService
            java.lang.String r0 = "SdpEngine"
            if (r2 == 0) goto L18
            int r2 = r2.addEngine(r3, r4, r5)     // Catch: android.os.RemoteException -> L12
            goto L1a
        L12:
            r2 = move-exception
            java.lang.String r3 = "Failed to call addEngine"
            android.util.Log.e(r0, r3, r2)
        L18:
            r2 = -13
        L1a:
            if (r2 != 0) goto L1d
            return
        L1d:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "addEngine failed "
            r3.<init>(r4)
            r3.append(r2)
            java.lang.String r3 = r3.toString()
            android.util.Log.e(r0, r3)
            com.samsung.android.knox.sdp.core.SdpException r3 = new com.samsung.android.knox.sdp.core.SdpException
            r3.<init>(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.sdp.core.SdpEngine.addEngine(com.samsung.android.knox.sdp.core.SdpCreationParam, java.lang.String, java.lang.String):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0015 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void allow(java.lang.String r2, java.lang.String r3) throws com.samsung.android.knox.sdp.core.SdpException {
        /*
            r1 = this;
            com.samsung.android.knox.dar.IDarManagerService r1 = r1.mService
            java.lang.String r0 = "SdpEngine"
            if (r1 == 0) goto L11
            int r1 = r1.allow(r2, r3)     // Catch: android.os.RemoteException -> Lb
            goto L13
        Lb:
            r1 = move-exception
            java.lang.String r2 = "Failed to call allow"
            android.util.Log.e(r0, r2, r1)
        L11:
            r1 = -13
        L13:
            if (r1 != 0) goto L16
            return
        L16:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "allow failed "
            r2.<init>(r3)
            r2.append(r1)
            java.lang.String r2 = r2.toString()
            android.util.Log.e(r0, r2)
            com.samsung.android.knox.sdp.core.SdpException r2 = new com.samsung.android.knox.sdp.core.SdpException
            r2.<init>(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.sdp.core.SdpEngine.allow(java.lang.String, java.lang.String):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0015 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void disallow(java.lang.String r2, java.lang.String r3) throws com.samsung.android.knox.sdp.core.SdpException {
        /*
            r1 = this;
            com.samsung.android.knox.dar.IDarManagerService r1 = r1.mService
            java.lang.String r0 = "SdpEngine"
            if (r1 == 0) goto L11
            int r1 = r1.disallow(r2, r3)     // Catch: android.os.RemoteException -> Lb
            goto L13
        Lb:
            r1 = move-exception
            java.lang.String r2 = "Failed to call disallow"
            android.util.Log.e(r0, r2, r1)
        L11:
            r1 = -13
        L13:
            if (r1 != 0) goto L16
            return
        L16:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "disallow failed "
            r2.<init>(r3)
            r2.append(r1)
            java.lang.String r2 = r2.toString()
            android.util.Log.e(r0, r2)
            com.samsung.android.knox.sdp.core.SdpException r2 = new com.samsung.android.knox.sdp.core.SdpException
            r2.<init>(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.sdp.core.SdpEngine.disallow(java.lang.String, java.lang.String):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0015 A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0017 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean exists(java.lang.String r2) {
        /*
            r1 = this;
            com.samsung.android.knox.dar.IDarManagerService r1 = r1.mService
            if (r1 == 0) goto L11
            int r1 = r1.exists(r2)     // Catch: android.os.RemoteException -> L9
            goto L12
        L9:
            r1 = move-exception
            java.lang.String r2 = "SdpEngine"
            java.lang.String r0 = "Failed to call exists"
            android.util.Log.e(r2, r0, r1)
        L11:
            r1 = -5
        L12:
            r2 = -4
            if (r1 != r2) goto L17
            r1 = 1
            goto L18
        L17:
            r1 = 0
        L18:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.sdp.core.SdpEngine.exists(java.lang.String):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x001c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void lock(java.lang.String r3) throws com.samsung.android.knox.sdp.core.SdpException {
        /*
            r2 = this;
            com.samsung.android.knox.ContextInfo r0 = r2.mContextInfo
            java.lang.String r1 = "SdpEngine.lock"
            com.samsung.android.knox.license.EnterpriseLicenseManager.log(r0, r1)
            com.samsung.android.knox.dar.IDarManagerService r2 = r2.mService
            java.lang.String r0 = "SdpEngine"
            if (r2 == 0) goto L18
            int r2 = r2.lock(r3)     // Catch: android.os.RemoteException -> L12
            goto L1a
        L12:
            r2 = move-exception
            java.lang.String r3 = "Failed to call lock"
            android.util.Log.e(r0, r3, r2)
        L18:
            r2 = -13
        L1a:
            if (r2 != 0) goto L1d
            return
        L1d:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r1 = "lock failed "
            r3.<init>(r1)
            r3.append(r2)
            java.lang.String r3 = r3.toString()
            android.util.Log.e(r0, r3)
            com.samsung.android.knox.sdp.core.SdpException r3 = new com.samsung.android.knox.sdp.core.SdpException
            r3.<init>(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.sdp.core.SdpEngine.lock(java.lang.String):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x0015 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void migrate(java.lang.String r3) throws com.samsung.android.knox.sdp.core.SdpException {
        /*
            r2 = this;
            com.samsung.android.knox.dar.IDarManagerService r2 = r2.mService
            java.lang.String r0 = "SdpEngine"
            if (r2 == 0) goto L11
            int r2 = r2.migrate(r3)     // Catch: android.os.RemoteException -> Lb
            goto L13
        Lb:
            r2 = move-exception
            java.lang.String r3 = "Failed to call migrate"
            android.util.Log.e(r0, r3, r2)
        L11:
            r2 = -13
        L13:
            if (r2 != 0) goto L16
            return
        L16:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r1 = "migrate failed "
            r3.<init>(r1)
            r3.append(r2)
            java.lang.String r3 = r3.toString()
            android.util.Log.e(r0, r3)
            com.samsung.android.knox.sdp.core.SdpException r3 = new com.samsung.android.knox.sdp.core.SdpException
            r3.<init>(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.sdp.core.SdpEngine.migrate(java.lang.String):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x001c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void removeEngine(java.lang.String r3) throws com.samsung.android.knox.sdp.core.SdpException {
        /*
            r2 = this;
            com.samsung.android.knox.ContextInfo r0 = r2.mContextInfo
            java.lang.String r1 = "SdpEngine.removeEngine"
            com.samsung.android.knox.license.EnterpriseLicenseManager.log(r0, r1)
            com.samsung.android.knox.dar.IDarManagerService r2 = r2.mService
            java.lang.String r0 = "SdpEngine"
            if (r2 == 0) goto L18
            int r2 = r2.removeEngine(r3)     // Catch: android.os.RemoteException -> L12
            goto L1a
        L12:
            r2 = move-exception
            java.lang.String r3 = "Failed to call removeEngine"
            android.util.Log.e(r0, r3, r2)
        L18:
            r2 = -13
        L1a:
            if (r2 != 0) goto L1d
            return
        L1d:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r1 = "removeEngine failed "
            r3.<init>(r1)
            r3.append(r2)
            java.lang.String r3 = r3.toString()
            android.util.Log.e(r0, r3)
            com.samsung.android.knox.sdp.core.SdpException r3 = new com.samsung.android.knox.sdp.core.SdpException
            r3.<init>(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.sdp.core.SdpEngine.removeEngine(java.lang.String):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x001c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void resetPassword(java.lang.String r3, java.lang.String r4, java.lang.String r5) throws com.samsung.android.knox.sdp.core.SdpException {
        /*
            r2 = this;
            com.samsung.android.knox.ContextInfo r0 = r2.mContextInfo
            java.lang.String r1 = "SdpEngine.resetPassword"
            com.samsung.android.knox.license.EnterpriseLicenseManager.log(r0, r1)
            com.samsung.android.knox.dar.IDarManagerService r2 = r2.mService
            java.lang.String r0 = "SdpEngine"
            if (r2 == 0) goto L18
            int r2 = r2.resetPassword(r3, r4, r5)     // Catch: android.os.RemoteException -> L12
            goto L1a
        L12:
            r2 = move-exception
            java.lang.String r3 = "Failed to call resetPassword"
            android.util.Log.e(r0, r3, r2)
        L18:
            r2 = -13
        L1a:
            if (r2 != 0) goto L1d
            return
        L1d:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "resetPassword failed "
            r3.<init>(r4)
            r3.append(r2)
            java.lang.String r3 = r3.toString()
            android.util.Log.e(r0, r3)
            com.samsung.android.knox.sdp.core.SdpException r3 = new com.samsung.android.knox.sdp.core.SdpException
            r3.<init>(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.sdp.core.SdpEngine.resetPassword(java.lang.String, java.lang.String, java.lang.String):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x001c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void setPassword(java.lang.String r3, java.lang.String r4) throws com.samsung.android.knox.sdp.core.SdpException {
        /*
            r2 = this;
            com.samsung.android.knox.ContextInfo r0 = r2.mContextInfo
            java.lang.String r1 = "SdpEngine.setPassword"
            com.samsung.android.knox.license.EnterpriseLicenseManager.log(r0, r1)
            com.samsung.android.knox.dar.IDarManagerService r2 = r2.mService
            java.lang.String r0 = "SdpEngine"
            if (r2 == 0) goto L18
            int r2 = r2.setPassword(r3, r4)     // Catch: android.os.RemoteException -> L12
            goto L1a
        L12:
            r2 = move-exception
            java.lang.String r3 = "Failed to call setPassword"
            android.util.Log.e(r0, r3, r2)
        L18:
            r2 = -13
        L1a:
            if (r2 != 0) goto L1d
            return
        L1d:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "setPassword failed "
            r3.<init>(r4)
            r3.append(r2)
            java.lang.String r3 = r3.toString()
            android.util.Log.e(r0, r3)
            com.samsung.android.knox.sdp.core.SdpException r3 = new com.samsung.android.knox.sdp.core.SdpException
            r3.<init>(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.sdp.core.SdpEngine.setPassword(java.lang.String, java.lang.String):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0030 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:5:0x001c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void unlock(java.lang.String r3, java.lang.String r4) throws com.samsung.android.knox.sdp.core.SdpException {
        /*
            r2 = this;
            com.samsung.android.knox.ContextInfo r0 = r2.mContextInfo
            java.lang.String r1 = "SdpEngine.unlock"
            com.samsung.android.knox.license.EnterpriseLicenseManager.log(r0, r1)
            com.samsung.android.knox.dar.IDarManagerService r2 = r2.mService
            java.lang.String r0 = "SdpEngine"
            if (r2 == 0) goto L18
            int r2 = r2.unlock(r3, r4)     // Catch: android.os.RemoteException -> L12
            goto L1a
        L12:
            r2 = move-exception
            java.lang.String r3 = "Failed to call unlock"
            android.util.Log.e(r0, r3, r2)
        L18:
            r2 = -13
        L1a:
            if (r2 == 0) goto L30
            java.lang.String r3 = "unlock failed "
            com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0.m(r2, r3, r0)
            if (r2 <= 0) goto L2a
            com.samsung.android.knox.sdp.core.SdpException r3 = new com.samsung.android.knox.sdp.core.SdpException
            r4 = -8
            r3.<init>(r4, r2)
            throw r3
        L2a:
            com.samsung.android.knox.sdp.core.SdpException r3 = new com.samsung.android.knox.sdp.core.SdpException
            r3.<init>(r2)
            throw r3
        L30:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.knox.sdp.core.SdpEngine.unlock(java.lang.String, java.lang.String):void");
    }
}
