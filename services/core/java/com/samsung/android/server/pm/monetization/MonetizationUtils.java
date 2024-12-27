package com.samsung.android.server.pm.monetization;

import android.content.Context;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManagerInternal;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.util.Xml;

import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.pm.PackageManagerServiceUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class MonetizationUtils {
    public static MonetizationUtils sInstance;
    public final Context mContext;
    public IPackageManager mPackageManager;
    public PackageManagerInternal mPackageManagerInternal;
    public final String mPath;
    public final String mSalesCode;
    public final ArraySet mPreloadAppsForBadge = new ArraySet();
    public final ArraySet mPreloadAppsLaunched = new ArraySet();
    public final ArraySet mGalaxyStoreAppsForBadge = new ArraySet();
    public final AtomicBoolean mGalaxyStoreBadgeEnabled =
            new AtomicBoolean(
                    "1".equals(SystemProperties.get("persist.galaxy_store.badge.feature")));
    public final Object mBadgeLock = new Object();
    public boolean mIsTruecallerSettingsUpdated = true;

    public MonetizationUtils(Context context) {
        this.mPath = "";
        this.mContext = context;
        String str = SystemProperties.get("ro.csc.sales_code");
        this.mSalesCode = str;
        if ("INS".equals(str) || "INU".equals(str) || "SUP".equals(str)) {
            this.mPath =
                    XmlUtils$$ExternalSyntheticOutline0.m(
                            "/prism/etc/carriers/", str, "/sysconfig/monetization-badge-apps.xml");
        }
    }

    public static MonetizationUtils getInstance(Context context) {
        MonetizationUtils monetizationUtils;
        synchronized (MonetizationUtils.class) {
            try {
                if (sInstance == null) {
                    sInstance = new MonetizationUtils(context);
                }
                monetizationUtils = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return monetizationUtils;
    }

    public static String parseMonetizedPackages(XmlPullParser xmlPullParser) {
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
                name.getClass();
                if (name.equals("packages")) {
                    str = xmlPullParser.getAttributeValue(null, "name");
                    Log.i("Monetization", "monetized packageList: " + str);
                    TextUtils.isEmpty(str);
                } else {
                    Log.i("Monetization", "Invalid element name: ".concat(name));
                }
            }
        }
        return str;
    }

    public final String getParsedPackagesList() {
        String str = this.mPath;
        if (str.isEmpty()) {
            return null;
        }
        File file = new File(str);
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
            Log.e(
                    "Monetization",
                    "Failed to parse monetized packages. XmlPullParserException " + e3);
            return null;
        }
    }

    public final boolean isMonetizedPreloadApp(String str) {
        boolean contains;
        synchronized (this.mBadgeLock) {
            contains = this.mPreloadAppsForBadge.contains(str);
        }
        return contains;
    }

    public final void modifyAppState(int i, String str) {
        try {
            this.mPackageManager.setApplicationEnabledSetting(str, i, 0, 0, "monetization");
        } catch (RemoteException | IllegalArgumentException unused) {
        }
    }

    public final void updateSettingsForMonetization(String str, boolean z, boolean z2, boolean z3) {
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
            PackageManagerServiceUtils.logCriticalInfo(
                    5, "Monetization Exception: " + Log.getStackTraceString(e));
        }
    }

    public final void writeSettingsForMonetization(boolean z) {
        StringBuilder sb = new StringBuilder();
        Iterator it = (z ? this.mPreloadAppsLaunched : this.mGalaxyStoreAppsForBadge).iterator();
        while (it.hasNext()) {
            sb.append((String) it.next());
            sb.append(";");
        }
        if (z) {
            Settings.Global.putString(
                    this.mContext.getContentResolver(), "MONETIZATION_PACKAGES", sb.toString());
        } else {
            Settings.System.putString(
                    this.mContext.getContentResolver(),
                    "galaxy_app_store_india_nudge_packages",
                    sb.toString());
        }
    }
}
