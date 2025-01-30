package com.samsung.android.server.pm.install;

import android.app.AppGlobals;
import android.content.Context;
import android.content.Intent;
import android.content.pm.IPackageManager;
import android.content.pm.InstallSourceInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageParser;
import android.content.pm.Signature;
import android.content.pm.SuspendDialogInfo;
import android.content.pm.UserInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import android.util.Slog;
import com.android.server.pm.PackageSetting;
import com.android.server.utils.WatchedArrayMap;
import com.samsung.android.core.pm.install.UnknownSourceAppBlockActivity;
import com.samsung.android.core.pm.install.UnknownSourceConfirmActivity;
import com.samsung.android.core.pm.install.UnknownSourcePhishingActivity;
import com.samsung.android.feature.SemCscFeature;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* loaded from: classes2.dex */
public class UnknownSourceAppManager {
    public final Handler mHandler;
    public final PackageSettingsDelegator mSettingsDelegator;
    public final Object mUnknownLock = new Object();
    public HashSet mUnknownSourceAppSet = new HashSet();
    public final PmConfigParser mPmConfigParser = new PmConfigParser();
    public final IPackageManager mPackageManager = AppGlobals.getPackageManager();
    public AbstractASKSHelper mASKSManagerHelper = getASKSManagerHelper();

    public abstract class AbstractASKSHelper {
        public abstract int checkUnknownSourcePackage(String str, String[] strArr, String[] strArr2, String str2, Signature[] signatureArr, String str3, String str4, String str5, int i, String str6, String str7, int i2);

        public abstract boolean isTrustedStore(String str, int i);
    }

    public final boolean isAppBlockType(int i) {
        return i == 150 || i == 151;
    }

    public final boolean isPhishingType(int i) {
        return i >= 120 && i <= 124;
    }

    public UnknownSourceAppManager(Handler handler, PackageSettingsDelegator packageSettingsDelegator) {
        this.mSettingsDelegator = packageSettingsDelegator;
        this.mHandler = handler;
    }

    public void initUnknownSourceAppSettingsLPr() {
        if (loadListFromFile() && pruneUnknownSourcePackages(new Function() { // from class: com.samsung.android.server.pm.install.UnknownSourceAppManager$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Boolean lambda$initUnknownSourceAppSettingsLPr$0;
                lambda$initUnknownSourceAppSettingsLPr$0 = UnknownSourceAppManager.this.lambda$initUnknownSourceAppSettingsLPr$0((String) obj);
                return lambda$initUnknownSourceAppSettingsLPr$0;
            }
        })) {
            lambda$writeUnknownPackageXmlAsync$2();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$initUnknownSourceAppSettingsLPr$0(String str) {
        return Boolean.valueOf(isObsoleteUnknownSourcePackageLPr(str));
    }

    public void onUnknownSourceSessionFinished(boolean z, String str) {
        if (z) {
            addUnknownSourceApp(str);
        } else {
            removeUnknownSourceApp(str);
        }
    }

    public void addUnknownSourceApp(String str) {
        boolean add;
        Log.d("UnknownSourceAppManager", "addUnknownSourceApp : " + str);
        synchronized (this.mUnknownLock) {
            add = this.mUnknownSourceAppSet.add(str);
        }
        if (add) {
            writeUnknownPackageXmlAsync();
        }
    }

    public void removeUnknownSourceApp(String str) {
        boolean remove;
        Log.d("UnknownSourceAppManager", "removeUnknownSourceApp : " + str);
        synchronized (this.mUnknownLock) {
            remove = this.mUnknownSourceAppSet.contains(str) ? this.mUnknownSourceAppSet.remove(str) : false;
        }
        if (remove) {
            writeUnknownPackageXmlAsync();
        }
    }

    public boolean isUnknownSourcePackage(String str) {
        boolean contains;
        synchronized (this.mUnknownLock) {
            contains = this.mUnknownSourceAppSet.contains(str);
        }
        return contains;
    }

    public boolean pruneUnknownSourcePackages(final Function function) {
        boolean removeIf;
        synchronized (this.mUnknownLock) {
            removeIf = this.mUnknownSourceAppSet.removeIf(new Predicate() { // from class: com.samsung.android.server.pm.install.UnknownSourceAppManager$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$pruneUnknownSourcePackages$1;
                    lambda$pruneUnknownSourcePackages$1 = UnknownSourceAppManager.lambda$pruneUnknownSourcePackages$1(function, (String) obj);
                    return lambda$pruneUnknownSourcePackages$1;
                }
            });
        }
        return removeIf;
    }

    public static /* synthetic */ boolean lambda$pruneUnknownSourcePackages$1(Function function, String str) {
        return ((Boolean) function.apply(str)).booleanValue();
    }

    /* renamed from: writeUnknownPackageXmlSync, reason: merged with bridge method [inline-methods] */
    public void lambda$writeUnknownPackageXmlAsync$2() {
        writeUnknownPackageXmlSync("/data/system/UnknownSourceAppList.xml");
    }

    public void writeUnknownPackageXmlSync(String str) {
        synchronized (this.mUnknownLock) {
            Log.d("UnknownSourceAppManager", "writeUnknownPackageXmlSync: " + this.mUnknownSourceAppSet.size());
            this.mPmConfigParser.writePackagesToXml(this.mUnknownSourceAppSet, str);
        }
    }

    public void writeUnknownPackageXmlAsync() {
        this.mHandler.post(new Runnable() { // from class: com.samsung.android.server.pm.install.UnknownSourceAppManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                UnknownSourceAppManager.this.lambda$writeUnknownPackageXmlAsync$2();
            }
        });
    }

    public boolean loadListFromFile() {
        return loadListFromFile("/data/system/UnknownSourceAppList.xml");
    }

    public boolean loadListFromFile(String str) {
        if (!new File(str).exists()) {
            return false;
        }
        synchronized (this.mUnknownLock) {
            this.mUnknownSourceAppSet.clear();
            this.mUnknownSourceAppSet.addAll(this.mPmConfigParser.parsePackages(str));
        }
        return true;
    }

    public final boolean isObsoleteUnknownSourcePackageLPr(String str) {
        InstallSourceInfo installSourceInfo;
        PackageSetting packageSetting = (PackageSetting) this.mSettingsDelegator.getPackagesLocked().get(str);
        try {
            installSourceInfo = this.mPackageManager.getInstallSourceInfo(str, UserHandle.getUserId(Binder.getCallingUid()));
        } catch (RemoteException e) {
            e.printStackTrace();
            installSourceInfo = null;
        }
        if (packageSetting == null || packageSetting.getPkg() == null || installSourceInfo == null) {
            return true;
        }
        return !isSideLoadingApp(installSourceInfo.getInitiatingPackageName());
    }

    public final boolean isSideLoadingApp(String str) {
        return "com.google.android.packageinstaller".equals(str) || ("CN".equalsIgnoreCase(SemCscFeature.getInstance().getString("CountryISO")) && "com.samsung.android.packageinstaller".equals(str));
    }

    public int checkUnknownSourcePackage(File file, PackageInstaller.SessionParams sessionParams, String str, String str2, String str3, Signature[] signatureArr, int i) {
        int i2;
        ArrayList arrayList;
        String str4;
        String str5;
        ArrayList arrayList2 = new ArrayList();
        if (file != null) {
            String absolutePath = file.getAbsolutePath();
            try {
                PackageParser.Package parsePackage = new PackageParser().parsePackage(file, 0);
                String str6 = parsePackage.packageName;
                for (int i3 = 0; i3 < parsePackage.services.size(); i3++) {
                    String str7 = ((PackageParser.Service) parsePackage.services.get(i3)).info.permission;
                    if (str7 != null) {
                        arrayList2.add(str7);
                    }
                }
                i2 = parsePackage.applicationInfo.targetSdkVersion;
                arrayList = parsePackage.requestedPermissions;
                str5 = absolutePath;
                str4 = str6;
            } catch (Exception unused) {
                Slog.d("UnknownSourceAppManager", "Exception while parsing " + file);
                return 0;
            }
        } else {
            i2 = -1;
            arrayList = null;
            str4 = null;
            str5 = null;
        }
        int i4 = i2;
        try {
            String[] strArr = new String[arrayList.size()];
            arrayList.toArray(strArr);
            String[] strArr2 = new String[arrayList2.size()];
            arrayList2.toArray(strArr2);
            Uri uri = sessionParams.referrerUri;
            String uri2 = uri != null ? uri.toString() : null;
            Uri uri3 = sessionParams.originatingUri;
            int checkUnknownSourcePackage = this.mASKSManagerHelper.checkUnknownSourcePackage(str4, strArr, strArr2, str5, signatureArr, str, str2, str3, i4, uri2, uri3 != null ? uri3.toString() : null, i);
            Slog.d("UnknownSourceAppManager", "checkUnknownSourcePackage: " + checkUnknownSourcePackage);
            return checkUnknownSourcePackage;
        } catch (Exception e) {
            Slog.d("UnknownSourceAppManager", "Exception during checkUnknownSourcePackage: " + e);
            return 0;
        }
    }

    public boolean isTrustedStore(String str, int i) {
        return this.mASKSManagerHelper.isTrustedStore(str, i);
    }

    public boolean needUnknownSourceConfirmForStore(String str, int i) {
        if (str == null || "com.sec.android.app.samsungapps".equals(str) || "com.android.vending".equals(str) || "com.android.shell".equals(str)) {
            return false;
        }
        if (isSideLoadingApp(str)) {
            return true;
        }
        if ("PrePackageInstaller".equals(str) && i == 1000) {
            return false;
        }
        return !isTrustedStore(str, UserHandle.getUserId(i));
    }

    public void startUnknownSourceConfirmDialog(final Context context, final int i, final int i2, final int i3) {
        this.mHandler.post(new Runnable() { // from class: com.samsung.android.server.pm.install.UnknownSourceAppManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                UnknownSourceAppManager.this.lambda$startUnknownSourceConfirmDialog$3(i, i2, context, i3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startUnknownSourceConfirmDialog$3(int i, int i2, Context context, int i3) {
        Slog.d("UnknownSourceAppManager", "startUnknownSourceConfirmDialog for session: " + i + ", type: " + i2);
        Intent intent = new Intent();
        intent.setFlags(268468224);
        intent.putExtra("android.content.pm.extra.SESSION_ID", i);
        if (isPhishingType(i2)) {
            intent.setClass(context, UnknownSourcePhishingActivity.class);
        } else if (isAppBlockType(i2)) {
            intent.setClass(context, UnknownSourceAppBlockActivity.class);
        } else {
            intent.setClass(context, UnknownSourceConfirmActivity.class);
        }
        intent.putExtra("android.content.pm.extra.unknown.installtype", i2);
        context.startActivityAsUser(intent, UserHandle.of(i3));
    }

    public final AbstractASKSHelper getASKSManagerHelper() {
        return new ASKSHelper();
    }

    public class Helper {
        public void suspendUnknownSourceAppsForAllUsers(Context context, String[] strArr, boolean z) {
            Iterator it = ((UserManager) context.getSystemService(UserManager.class)).getUsers().iterator();
            while (it.hasNext()) {
                suspendUnknownSourceAppsAsUser(context, strArr, z, ((UserInfo) it.next()).id);
            }
        }

        public void suspendUnknownSourceAppsAsUser(Context context, String[] strArr, boolean z, int i) {
            try {
                AppGlobals.getPackageManager().setPackagesSuspendedAsUser(strArr, z, (PersistableBundle) null, (PersistableBundle) null, new SuspendDialogInfo.Builder().setTitle(context.getString(17043083)).setMessage(context.getString(17043082)).build(), "system", i);
            } catch (RemoteException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    public class PackageSettingsDelegator {
        public final Supplier mGetPackagesLocked;

        public PackageSettingsDelegator(Supplier supplier) {
            this.mGetPackagesLocked = supplier;
        }

        public WatchedArrayMap getPackagesLocked() {
            return (WatchedArrayMap) this.mGetPackagesLocked.get();
        }
    }
}
