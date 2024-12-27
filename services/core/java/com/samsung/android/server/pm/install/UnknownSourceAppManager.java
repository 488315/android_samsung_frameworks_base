package com.samsung.android.server.pm.install;

import android.app.AppGlobals;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.content.pm.SuspendDialogInfo;
import android.content.pm.UserInfo;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.UserManager;
import android.util.Log;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.pm.PackageManagerService$$ExternalSyntheticLambda55;
import com.samsung.android.feature.SemCscFeature;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UnknownSourceAppManager {
    public final Handler mHandler;
    public final PackageSettingsDelegator mSettingsDelegator;
    public final Object mUnknownLock = new Object();
    public final HashSet mUnknownSourceAppSet = new HashSet();
    public final PmConfigParser mPmConfigParser = new PmConfigParser();
    public final IPackageManager mPackageManager = AppGlobals.getPackageManager();
    public final ASKSHelper mASKSManagerHelper = new ASKSHelper();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Helper {
        public static void suspendUnknownSourceAppsForAllUsers(Context context, String[] strArr, boolean z) {
            Iterator it = ((UserManager) context.getSystemService(UserManager.class)).getUsers().iterator();
            while (it.hasNext()) {
                int i = ((UserInfo) it.next()).id;
                try {
                    AppGlobals.getPackageManager().setPackagesSuspendedAsUser(strArr, z, (PersistableBundle) null, (PersistableBundle) null, new SuspendDialogInfo.Builder().setTitle(context.getString(17043303)).setMessage(context.getString(17043302)).build(), 0, "system", i, i);
                } catch (RemoteException | IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackageSettingsDelegator {
        public final Supplier mGetPackagesLocked;

        public PackageSettingsDelegator(PackageManagerService$$ExternalSyntheticLambda55 packageManagerService$$ExternalSyntheticLambda55) {
            this.mGetPackagesLocked = packageManagerService$$ExternalSyntheticLambda55;
        }
    }

    public UnknownSourceAppManager(Handler handler, PackageSettingsDelegator packageSettingsDelegator) {
        this.mSettingsDelegator = packageSettingsDelegator;
        this.mHandler = handler;
    }

    public static boolean isSideLoadingApp(String str) {
        return "com.google.android.packageinstaller".equals(str) || ("CN".equalsIgnoreCase(SemCscFeature.getInstance().getString("CountryISO")) && "com.samsung.android.packageinstaller".equals(str));
    }

    public boolean loadListFromFile(String str) {
        if (!BatteryService$$ExternalSyntheticOutline0.m45m(str)) {
            return false;
        }
        synchronized (this.mUnknownLock) {
            this.mUnknownSourceAppSet.clear();
            HashSet hashSet = this.mUnknownSourceAppSet;
            this.mPmConfigParser.getClass();
            hashSet.addAll(PmConfigParser.parsePackages(str));
        }
        return true;
    }

    public void writeUnknownPackageXmlSync(String str) {
        synchronized (this.mUnknownLock) {
            Log.d("UnknownSourceAppManager", "writeUnknownPackageXmlSync: " + this.mUnknownSourceAppSet.size());
            PmConfigParser pmConfigParser = this.mPmConfigParser;
            HashSet hashSet = this.mUnknownSourceAppSet;
            pmConfigParser.getClass();
            PmConfigParser.writePackagesToXml(str, hashSet);
        }
    }
}
