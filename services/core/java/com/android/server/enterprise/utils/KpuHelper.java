package com.android.server.enterprise.utils;

import android.app.AppGlobals;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.os.Binder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;

import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.PersonaManagerAdapter;

import com.samsung.android.knox.SemPersonaManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class KpuHelper {
    public static KpuHelper sInstance;
    public final Context mContext;
    public String mKpuPackageName;

    public KpuHelper(Context context) {
        this.mContext = context;
    }

    public static KpuHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new KpuHelper(context);
        }
        return sInstance;
    }

    public static boolean isKpuPermissionGranted(int i, String str) {
        try {
            if (AppGlobals.getPackageManager()
                            .checkPermission(
                                    "com.samsung.android.knox.permission.KNOX_KPU_INTERNAL", str, i)
                    == 0) {
                return true;
            }
            Log.d("KpuHelper", "Caller " + str + " does not have KPU permission");
            return false;
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public final String getKpuPackageName() {
        String str = this.mKpuPackageName;
        return str != null ? str : "com.samsung.android.knox.kpu";
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x0015, code lost:

       if (android.app.AppGlobals.getPackageManager().checkPermission("com.samsung.android.knox.permission.KNOX_KPU_INTERNAL", r5, r4) != 0) goto L10;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isCallerValidKpu(int r4, java.lang.String r5) {
        /*
            r3 = this;
            java.lang.String r0 = "com.samsung.android.knox.kpu"
            boolean r0 = r0.equals(r5)
            r1 = 0
            if (r0 != 0) goto L1d
            android.content.pm.IPackageManager r0 = android.app.AppGlobals.getPackageManager()
            java.lang.String r2 = "com.samsung.android.knox.permission.KNOX_KPU_INTERNAL"
            int r0 = r0.checkPermission(r2, r5, r4)     // Catch: android.os.RemoteException -> L18
            if (r0 == 0) goto L1d
            goto L1c
        L18:
            r3 = move-exception
            r3.printStackTrace()
        L1c:
            return r1
        L1d:
            boolean r5 = r3.isKpuPlatformSigned(r4, r5)
            if (r5 == 0) goto L4c
            r5 = 1
            if (r4 != 0) goto L35
            boolean r3 = r3.isDoPresent()
            if (r3 == 0) goto L2d
            return r5
        L2d:
            java.lang.SecurityException r3 = new java.lang.SecurityException
            java.lang.String r4 = "KPU cannot apply policies in user 0 space if DO is not present on device.."
            r3.<init>(r4)
            throw r3
        L35:
            java.util.HashMap r3 = com.android.server.enterprise.adapter.AdapterRegistry.mAdapterHandles
            java.lang.Class<com.android.server.enterprise.adapter.IPersonaManagerAdapter> r0 = com.android.server.enterprise.adapter.IPersonaManagerAdapter.class
            java.lang.Object r3 = r3.get(r0)
            com.android.server.enterprise.adapter.IPersonaManagerAdapter r3 = (com.android.server.enterprise.adapter.IPersonaManagerAdapter) r3
            com.android.server.enterprise.adapterlayer.PersonaManagerAdapter r3 = (com.android.server.enterprise.adapterlayer.PersonaManagerAdapter) r3
            r3.getClass()
            boolean r3 = com.samsung.android.knox.SemPersonaManager.isKnoxId(r4)
            if (r3 == 0) goto L4b
            return r5
        L4b:
            return r1
        L4c:
            java.lang.SecurityException r3 = new java.lang.SecurityException
            java.lang.String r4 = "KPU app does not have a valid signature.."
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.utils.KpuHelper.isCallerValidKpu(int,"
                    + " java.lang.String):boolean");
    }

    public final boolean isDoPresent() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class))
                            .getDeviceOwnerComponentOnAnyUser()
                    != null;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("KpuHelper", "Failed to retrieve DO on device");
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isKpuPlatformSigned(int i, String str) {
        IPackageManager packageManager = AppGlobals.getPackageManager();
        try {
            if (this.mContext
                    .getPackageManager()
                    .hasSystemFeature("com.samsung.feature.samsung_experience_mobile_lite")) {
                if (Utils.comparePackageSignature(
                        i,
                        this.mContext,
                        str,
                        "308204d4308203bca003020102020900d20995a79c0daad6300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303632323132323531325a170d3338313130373132323531325a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100c986384a3e1f2fb206670e78ef232215c0d26f45a22728db99a44da11c35ac33a71fe071c4a2d6825a9b4c88b333ed96f3c5e6c666d60f3ee94c490885abcf8dc660f707aabc77ead3e2d0d8aee8108c15cd260f2e85042c28d2f292daa3c6da0c7bf2391db7841aade8fdf0c9d0defcf77124e6d2de0a9e0d2da746c3670e4ffcdc85b701bb4744861b96ff7311da3603c5a10336e55ffa34b4353eedc85f51015e1518c67e309e39f87639ff178107f109cd18411a6077f26964b6e63f8a70b9619db04306a323c1a1d23af867e19f14f570ffe573d0e3a0c2b30632aaec3173380994be1e341e3a90bd2e4b615481f46db39ea83816448ec35feb1735c1f3020103a382010b30820107301d0603551d0e04160414932c3af70b627a0c7610b5a0e7427d6cfaea3f1e3081d70603551d230481cf3081cc8014932c3af70b627a0c7610b5a0e7427d6cfaea3f1ea181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900d20995a79c0daad6300c0603551d13040530030101ff300d06092a864886f70d01010505000382010100329601fe40e036a4a86cc5d49dd8c1b5415998e72637538b0d430369ac51530f63aace8c019a1a66616a2f1bb2c5fabd6f313261f380e3471623f053d9e3c53f5fd6d1965d7b000e4dc244c1b27e2fe9a323ff077f52c4675e86247aa801187137e30c9bbf01c567a4299db4bf0b25b7d7107a7b81ee102f72ff47950164e26752e114c42f8b9d2a42e7308897ec640ea1924ed13abbe9d120912b62f4926493a86db94c0b46f44c6161d58c2f648164890c512dfb28d42c855bf470dbee2dab6960cad04e81f71525ded46cdd0f359f99c460db9f007d96ce83b4b218ac2d82c48f12608d469733f05a3375594669ccbf8a495544d6c5701e9369c08c810158")) {
                    return true;
                }
            } else if (packageManager.checkSignatures("android", str, i) == 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean isUidValidKpu(int i, int i2, boolean z) {
        this.mKpuPackageName = null;
        String nameForUid = this.mContext.getPackageManager().getNameForUid(i);
        if (nameForUid == null) {
            return false;
        }
        if (!"com.samsung.android.knox.kpu".equals(nameForUid)
                && !isKpuPermissionGranted(UserHandle.getUserId(i), nameForUid)) {
            return false;
        }
        if (!isKpuPlatformSigned(UserHandle.getUserId(i), nameForUid)) {
            throw new SecurityException("KPU app does not have a valid signature..");
        }
        int userId = UserHandle.getUserId(i);
        if (!z && userId != i2) {
            throw new SecurityException(
                    "KPU userId does not match with ContextInfo.mContainerId..");
        }
        if (userId == 0) {
            if (!isDoPresent()) {
                throw new SecurityException(
                        "KPU cannot apply policies in user 0 space if DO is not present on"
                            + " device..");
            }
            this.mKpuPackageName = nameForUid;
            return true;
        }
        ((PersonaManagerAdapter)
                        ((IPersonaManagerAdapter)
                                AdapterRegistry.mAdapterHandles.get(IPersonaManagerAdapter.class)))
                .getClass();
        if (!SemPersonaManager.isKnoxId(userId)) {
            return false;
        }
        ((PersonaManagerAdapter)
                        ((IPersonaManagerAdapter)
                                AdapterRegistry.mAdapterHandles.get(IPersonaManagerAdapter.class)))
                .getClass();
        if (SemPersonaManager.isSecureFolderId(userId)) {
            throw new SecurityException(
                    "KPU must be inside PO to be able to call container or parent APIs..");
        }
        this.mKpuPackageName = nameForUid;
        return true;
    }
}
