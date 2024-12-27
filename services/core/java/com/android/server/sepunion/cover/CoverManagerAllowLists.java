package com.android.server.sepunion.cover;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Binder;
import android.os.Build;
import android.os.UserHandle;
import android.util.Base64;
import android.util.SparseArray;
import com.samsung.android.sepunion.Log;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class CoverManagerAllowLists {
    public HashMap mAllowList;
    public ArrayList mPrefixPackage;
    public SparseArray mSignaturesMap;

    public static String getPackageForPid(Context context, int i) {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            if (runningAppProcessInfo.pid == i) {
                return runningAppProcessInfo.processName;
            }
        }
        return null;
    }

    public final boolean isAllowedToUse(Context context, int i, int i2) {
        int i3;
        String str;
        if (context.getPackageManager().checkSignatures(1000, i) == 0) {
            return true;
        }
        String packageForPid = getPackageForPid(context, i2);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        UserHandle userHandleForUid = UserHandle.getUserHandleForUid(i);
        try {
            context = context.createPackageContextAsUser(context.getPackageName(), 4, userHandleForUid);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("CoverManager_CoverManagerAllowLists", "Error creating user context", e);
        }
        PackageInfo packageInfo = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            packageInfo = packageManager.getPackageInfo(packageForPid, 64);
            i3 = packageManager.getPackageUidAsUser(packageForPid, userHandleForUid.getIdentifier());
        } catch (PackageManager.NameNotFoundException unused) {
            Log.d("CoverManager_CoverManagerAllowLists", "Package " + packageForPid + " not found for user!");
            i3 = -1;
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        if (i3 != i) {
            Log.d("CoverManager_CoverManagerAllowLists", "isAllowedToUse : pkg does not match uid");
            return false;
        }
        if (packageInfo == null) {
            Log.d("CoverManager_CoverManagerAllowLists", "isAllowedToUse : pkgInfo is null");
            return false;
        }
        if ((packageInfo.applicationInfo.flags & 129) != 0 || "eng".equals(Build.TYPE)) {
            return true;
        }
        Signature[] signatureArr = packageInfo.signatures;
        if (isMatchedSignature(signatureArr, 7)) {
            return true;
        }
        if (packageForPid != null) {
            try {
                packageForPid = Base64.encodeToString(packageForPid.getBytes(StandardCharsets.UTF_8), 2);
                Iterator it = this.mPrefixPackage.iterator();
                while (it.hasNext()) {
                    str = (String) it.next();
                    if (packageForPid.startsWith(str)) {
                        break;
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        str = packageForPid;
        Integer num = (Integer) this.mAllowList.get(str);
        if (num != null) {
            return isMatchedSignature(signatureArr, num.intValue());
        }
        Log.d("CoverManager_CoverManagerAllowLists", "isAllowedToUse : cover manager allow lists does not include this App : " + str);
        return false;
    }

    public final boolean isMatchedSignature(Signature[] signatureArr, int i) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < 14; i2++) {
            int i3 = 1 << i2;
            if ((i & i3) != 0) {
                arrayList.add((Signature) this.mSignaturesMap.get(i3));
            }
        }
        if (signatureArr != null) {
            for (Signature signature : signatureArr) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    Signature signature2 = (Signature) it.next();
                    if (signature2 != null && signature2.equals(signature)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
