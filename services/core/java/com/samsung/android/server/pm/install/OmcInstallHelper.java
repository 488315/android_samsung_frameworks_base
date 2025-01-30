package com.samsung.android.server.pm.install;

import android.content.Context;
import android.os.Environment;
import android.os.FileUtils;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.pm.PackageManagerServiceUtils;
import java.io.File;

/* loaded from: classes2.dex */
public class OmcInstallHelper {
    public Context mContext;
    public boolean mNeedsOmcInit;
    public boolean mNeedsTssInit;

    public OmcInstallHelper(Context context) {
        this.mContext = context;
    }

    public void setOmcAndTssInit() {
        this.mNeedsOmcInit = needsOmcInit();
        this.mNeedsTssInit = needsTssInit();
    }

    public void deleteContentsIfNeeded(File file) {
        if (file == null || !needsOmcOrTssInit()) {
            return;
        }
        PackageManagerServiceUtils.logCriticalInfo(5, "Clear package cache by omcboot or tssboot");
        FileUtils.deleteContents(file);
    }

    public boolean needsOmcOrTssInit() {
        return this.mNeedsOmcInit || this.mNeedsTssInit;
    }

    public final boolean needsOmcInit() {
        String str = SystemProperties.get("persist.sys.prev_salescode");
        String str2 = SystemProperties.get("ro.csc.sales_code");
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str)) {
            String upperCase = str2.trim().toUpperCase();
            String upperCase2 = str.split(",")[0].trim().toUpperCase();
            Log.d("PackageManager", "Sales code, current: " + upperCase + ", prev: " + upperCase2);
            if (!TextUtils.isEmpty(upperCase2) && !TextUtils.isEmpty(upperCase)) {
                return !upperCase.equals(upperCase2);
            }
        }
        return false;
    }

    public final boolean needsTssInit() {
        if (!supportTss() || !isTssActivated()) {
            return false;
        }
        return !this.mContext.createDeviceProtectedStorageContext().getSharedPreferences(new File(Environment.getDataSystemDirectory(), "samsung_pm_settings.xml"), 0).getBoolean("pref_tss_initialized", false);
    }

    public void writeTssSettings() {
        if (this.mNeedsTssInit) {
            this.mContext.createDeviceProtectedStorageContext().getSharedPreferences(new File(Environment.getDataSystemDirectory(), "samsung_pm_settings.xml"), 0).edit().putBoolean("pref_tss_initialized", this.mNeedsTssInit).apply();
        }
    }

    public void waitToReadAIDwhenTssAndNonActivated() {
        if (supportTss() && !isTssPropertyInitialized()) {
            Slog.i("PackageManager", "wait to read AID before system scan");
            long uptimeMillis = SystemClock.uptimeMillis();
            for (int i = FrameworkStatsLog.f446x58c6c32; i >= 0 && !isTssPropertyInitialized(); i--) {
                try {
                    Thread.sleep(20L);
                } catch (InterruptedException unused) {
                }
            }
            Slog.i("PackageManager", "waitToReadAID took " + (SystemClock.uptimeMillis() - uptimeMillis) + "ms");
        }
    }

    public final boolean supportTss() {
        return SystemProperties.getBoolean("mdc.singlesku", false);
    }

    public final boolean isTssPropertyInitialized() {
        return !TextUtils.isEmpty(SystemProperties.get("mdc.singlesku.activated"));
    }

    public final boolean isTssActivated() {
        return SystemProperties.getBoolean("mdc.singlesku.activated", false);
    }
}
