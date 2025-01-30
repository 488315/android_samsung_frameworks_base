package com.samsung.android.server.pm.monetization;

import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ILauncherApps;
import android.content.pm.IPackageManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.util.Xml;
import com.android.internal.util.Preconditions;
import com.android.server.LocalServices;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.pm.PackageManagerServiceUtils;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.samsung.android.rune.PMRune;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class MonetizationUtils {
    public static MonetizationUtils sInstance;
    public final Context mContext;
    public ILauncherApps mLauncherApps;
    public IPackageManager mPackageManager;
    public PackageManagerInternal mPackageManagerInternal;
    public String mPath;
    public String mSalesCode;
    public ArraySet mPreloadAppsForBadge = new ArraySet();
    public ArraySet mPreloadAppsLaunched = new ArraySet();
    public ArraySet mGalaxyStoreAppsForBadge = new ArraySet();
    public AtomicBoolean mGalaxyStoreBadgeEnabled = new AtomicBoolean("1".equals(SystemProperties.get("persist.galaxy_store.badge.feature")));
    public final Object mBadgeLock = new Object();
    public final String TAG = "Monetization";
    public final String DISABLE_GALAXY_STORE_BADGE = "0";
    public final String ENABLE_GALAXY_STORE_BADGE = "1";
    public final String DISABLE_GALAXY_STORE_BADGE_FOR_APP = "2";
    public final String TRUECALLER_PKGNAME = "com.truecaller";
    public boolean mIsTruecallerSettingsUpdated = true;

    public MonetizationUtils(Context context) {
        this.mPath = "";
        this.mContext = context;
        String str = SystemProperties.get("ro.csc.sales_code");
        this.mSalesCode = str;
        if ("INS".equals(str) || "INU".equals(this.mSalesCode) || "SUP".equals(this.mSalesCode)) {
            this.mPath = "/prism/etc/carriers/" + this.mSalesCode + "/sysconfig/monetization-badge-apps.xml";
        }
    }

    public static MonetizationUtils getInstance(Context context) {
        MonetizationUtils monetizationUtils;
        synchronized (MonetizationUtils.class) {
            if (sInstance == null) {
                sInstance = new MonetizationUtils(context);
            }
            monetizationUtils = sInstance;
        }
        return monetizationUtils;
    }

    public boolean isGalaxyStoreFeatureEnabled() {
        return this.mGalaxyStoreBadgeEnabled.get();
    }

    public void sendChangePackageIconInfo(String str, int[] iArr) {
        for (int i : iArr) {
            try {
                this.mLauncherApps.changePackageIcon(str, i);
            } catch (Exception e) {
                PackageManagerServiceUtils.logCriticalInfo(5, "Monetization Exception: " + Log.getStackTraceString(e));
            }
        }
    }

    public void updateSettingsForMonetization(String str, boolean z, boolean z2, boolean z3) {
        try {
            synchronized (this.mBadgeLock) {
                try {
                    if (z2) {
                        if (!this.mPreloadAppsLaunched.add(str)) {
                            return;
                        }
                    } else if (z3) {
                        if (!this.mGalaxyStoreAppsForBadge.add(str)) {
                            return;
                        }
                    } else if (!this.mGalaxyStoreAppsForBadge.remove(str)) {
                        return;
                    }
                    if (z) {
                        writeSettingsForMonetization(z2);
                    }
                } finally {
                }
            }
        } catch (Exception e) {
            PackageManagerServiceUtils.logCriticalInfo(5, "Monetization Exception: " + Log.getStackTraceString(e));
        }
    }

    public final void writeSettingsForMonetization(boolean z) {
        StringBuilder sb = new StringBuilder();
        Iterator it = (z ? this.mPreloadAppsLaunched : this.mGalaxyStoreAppsForBadge).iterator();
        while (it.hasNext()) {
            sb.append((String) it.next());
            sb.append(KnoxVpnFirewallHelper.DELIMITER);
        }
        if (z) {
            Settings.Global.putString(this.mContext.getContentResolver(), "MONETIZATION_PACKAGES", sb.toString());
        } else {
            Settings.System.putString(this.mContext.getContentResolver(), "galaxy_app_store_india_nudge_packages", sb.toString());
        }
    }

    public final void readSettingsForMonetization(String str, boolean z) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        for (String str2 : str.split(KnoxVpnFirewallHelper.DELIMITER)) {
            updateSettingsForMonetization(str2, false, z, true);
        }
    }

    public boolean isMonetizedPreloadApp(String str) {
        boolean contains;
        synchronized (this.mBadgeLock) {
            contains = this.mPreloadAppsForBadge.contains(str);
        }
        return contains;
    }

    public final void resetSettingsForMonetization(boolean z) {
        synchronized (this.mBadgeLock) {
            if (z) {
                this.mPreloadAppsLaunched.clear();
            } else {
                this.mGalaxyStoreAppsForBadge.clear();
            }
        }
    }

    public void initializeSettingsForMonetization(boolean z, boolean z2) {
        try {
            synchronized (this.mBadgeLock) {
                this.mLauncherApps = ILauncherApps.Stub.asInterface(ServiceManager.getService("launcherapps"));
                this.mPackageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
                this.mPackageManager = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
                String parsedPackagesList = getParsedPackagesList();
                StringBuilder sb = new StringBuilder();
                sb.append("Monetization Package List: ");
                sb.append(parsedPackagesList != null ? parsedPackagesList : "NULL");
                Log.i("Monetization", sb.toString());
                readSettingsForMonetization(Settings.Global.getString(this.mContext.getContentResolver(), "MONETIZATION_PACKAGES"), true);
                if (this.mGalaxyStoreBadgeEnabled.get()) {
                    readSettingsForMonetization(Settings.System.getString(this.mContext.getContentResolver(), "galaxy_app_store_india_nudge_packages"), false);
                }
                if (!TextUtils.isEmpty(parsedPackagesList)) {
                    boolean z3 = false;
                    for (String str : parsedPackagesList.split(KnoxVpnFirewallHelper.DELIMITER)) {
                        int indexOf = str.indexOf(PackageManagerShellCommandDataLoader.STDIN_PATH);
                        if (indexOf != -1) {
                            str = str.substring(0, indexOf);
                        }
                        this.mPreloadAppsForBadge.add(str);
                        if (z && wasPackageEverLaunched(str, 0)) {
                            Log.i("Monetization", "Monetized pkg: " + str + " launched for user");
                            updateSettingsForMonetization(str, false, true, true);
                            z3 = true;
                        }
                        if ("com.truecaller".equals(str) && z2) {
                            modifyAppState(str, 4);
                            this.mIsTruecallerSettingsUpdated = false;
                        }
                    }
                    if (z3) {
                        writeSettingsForMonetization(true);
                    }
                } else if ("SUP".equals(this.mSalesCode) && z2) {
                    modifyAppState("com.truecaller", 4);
                    this.mIsTruecallerSettingsUpdated = false;
                }
            }
        } catch (Exception e) {
            PackageManagerServiceUtils.logCriticalInfo(5, "Monetization Exception: " + Log.getStackTraceString(e));
        }
    }

    public final String getParsedPackagesList() {
        if (this.mPath.isEmpty()) {
            return null;
        }
        File file = new File(this.mPath);
        if (!file.exists()) {
            Log.d("Monetization", "No packages to monetization badge.");
            return null;
        }
        XmlPullParser newPullParser = Xml.newPullParser();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                newPullParser.setInput(fileInputStream, null);
                String parseMonetizedPackages = parseMonetizedPackages(newPullParser);
                fileInputStream.close();
                return parseMonetizedPackages;
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (FileNotFoundException e) {
            Log.e("Monetization", "Failed to parse monetized packages. FileNotFoundException " + e);
            return null;
        } catch (IOException e2) {
            Log.e("Monetization", "Failed to parse monetized packages. IOException " + e2);
            return null;
        } catch (XmlPullParserException e3) {
            Log.e("Monetization", "Failed to parse monetized packages. XmlPullParserException " + e3);
            return null;
        }
    }

    public final String parseMonetizedPackages(XmlPullParser xmlPullParser) {
        xmlPullParser.next();
        int depth = xmlPullParser.getDepth();
        String str = "";
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                String name = xmlPullParser.getName();
                name.hashCode();
                if (name.equals("packages")) {
                    str = xmlPullParser.getAttributeValue(null, "name");
                    Log.i("Monetization", "monetized packageList: " + str);
                    TextUtils.isEmpty(str);
                } else {
                    Log.i("Monetization", "Invalid element name: " + name);
                }
            }
        }
        return str;
    }

    public final void modifyAppState(String str, int i) {
        try {
            this.mPackageManager.setApplicationEnabledSetting(str, i, 0, 0, "monetization");
        } catch (RemoteException | IllegalArgumentException unused) {
        }
    }

    public void updateState() {
        if (this.mIsTruecallerSettingsUpdated) {
            return;
        }
        modifyAppState("com.truecaller", 0);
        this.mIsTruecallerSettingsUpdated = true;
    }

    public final boolean wasPackageEverLaunched(String str, int i) {
        try {
            return this.mPackageManagerInternal.wasPackageEverLaunched(str, i);
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    public void dumpMonetizationBadgeState(PrintWriter printWriter, String[] strArr) {
        String str;
        int i = 0;
        while (i < strArr.length && (str = strArr[i]) != null && str.length() > 0 && str.charAt(0) == '-') {
            i++;
            if ("--proto".equals(str)) {
                return;
            }
        }
        synchronized (this.mBadgeLock) {
            printWriter.println();
            printWriter.println("Monetization app list:");
            Iterator it = this.mPreloadAppsForBadge.iterator();
            while (it.hasNext()) {
                printWriter.println((String) it.next());
            }
            printWriter.println();
            printWriter.println("Monetization apps launched list:");
            Iterator it2 = this.mPreloadAppsLaunched.iterator();
            while (it2.hasNext()) {
                printWriter.println((String) it2.next());
            }
            printWriter.println();
            printWriter.println("GalaxyStore badge feature state: " + this.mGalaxyStoreBadgeEnabled.get());
            if (this.mGalaxyStoreBadgeEnabled.get()) {
                printWriter.println();
                printWriter.println("GalaxyStore monetized apps: ");
                Iterator it3 = this.mGalaxyStoreAppsForBadge.iterator();
                while (it3.hasNext()) {
                    printWriter.println((String) it3.next());
                }
            }
        }
    }

    public void changeMonetizationBadgeState(String str, String str2) {
        Log.i("Monetization", "changeMonetizationBadgeState value:" + str + " PackageName: " + str2);
        if (!PMRune.PM_BADGE_ON_MONETIZED_APP_SUPPORTED || this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SECURE_SETTINGS") != 0) {
            throw new SecurityException("changeMonetizationBadgeState can only be run by the system or feature not supported");
        }
        Preconditions.checkNotNull(str, "value cannot be null");
        str.hashCode();
        switch (str) {
            case "0":
                if (this.mGalaxyStoreBadgeEnabled.get()) {
                    this.mGalaxyStoreBadgeEnabled.set(false);
                    resetSettingsForMonetization(false);
                    SystemProperties.set("persist.galaxy_store.badge.feature", str);
                    return;
                }
                return;
            case "1":
                if (!this.mGalaxyStoreBadgeEnabled.get()) {
                    this.mGalaxyStoreBadgeEnabled.set(true);
                    SystemProperties.set("persist.galaxy_store.badge.feature", str);
                }
                Preconditions.checkNotNull(str2, "packageName cannot be null");
                updateSettingsForMonetization(str2, false, false, true);
                return;
            case "2":
                Preconditions.checkNotNull(str2, "packageName cannot be null");
                updateSettingsForMonetization(str2, false, false, false);
                return;
            default:
                Log.i("Monetization", "Unknown parameter passed to change badge state");
                return;
        }
    }

    public boolean shouldAppSupportBadgeIcon(String str) {
        boolean z = false;
        if (!PMRune.PM_BADGE_ON_MONETIZED_APP_SUPPORTED) {
            return false;
        }
        synchronized (this.mBadgeLock) {
            if ((isMonetizedPreloadApp(str) && !this.mPreloadAppsLaunched.contains(str)) || (this.mGalaxyStoreBadgeEnabled.get() && this.mGalaxyStoreAppsForBadge.contains(str))) {
                z = true;
            }
        }
        return z;
    }
}
