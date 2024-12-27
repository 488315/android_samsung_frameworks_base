package com.android.server.om.wallpapertheme;

import android.content.Context;
import android.content.om.IOverlayManager;
import android.content.om.OverlayIdentifier;
import android.content.om.OverlayInfo;
import android.content.om.OverlayManagerTransaction;
import android.content.om.wallpapertheme.ThemeUtil;
import android.os.Binder;
import android.os.FileUtils;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.om.OverlayManagerSettingsHelper;
import com.android.server.om.OverlayPolicyManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SemWallpaperThemeManagerWrapper {
    public static SemWallpaperThemeManagerWrapper instance;
    public static Context mContext;
    public static final OverlayIdentifier sColorThemeOverlayId = new OverlayIdentifier("android", "SemWT_G_MonetPalette");
    public static int sGlobalColorThemeSeq = 0;
    public boolean mIsSuccessLoadMetadata;
    public boolean mIsThemeParkApplied;
    public OverlayManagerSettingsHelper mSettings;
    public SemWallpaperThemeManager mWallpaperThemeManager;

    public static void deleteFile(File file) {
        if (file.exists()) {
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                for (File file2 : listFiles) {
                    if (file2.isDirectory()) {
                        deleteFile(file2);
                    } else {
                        file2.delete();
                    }
                }
            }
            file.delete();
        }
    }

    public static synchronized SemWallpaperThemeManagerWrapper getInstance(Context context, OverlayManagerSettingsHelper overlayManagerSettingsHelper) {
        SemWallpaperThemeManagerWrapper semWallpaperThemeManagerWrapper;
        synchronized (SemWallpaperThemeManagerWrapper.class) {
            mContext = context;
            if (instance == null) {
                SemWallpaperThemeManagerWrapper semWallpaperThemeManagerWrapper2 = new SemWallpaperThemeManagerWrapper();
                semWallpaperThemeManagerWrapper2.mIsSuccessLoadMetadata = false;
                semWallpaperThemeManagerWrapper2.mIsThemeParkApplied = false;
                if (SemWallpaperThemeManager.sInstance == null) {
                    SemWallpaperThemeManager.sInstance = new SemWallpaperThemeManager(context);
                }
                SemWallpaperThemeManager semWallpaperThemeManager = SemWallpaperThemeManager.sInstance;
                semWallpaperThemeManagerWrapper2.mWallpaperThemeManager = semWallpaperThemeManager;
                SemWallpaperThemeOverlayPolicy semWallpaperThemeOverlayPolicy = new SemWallpaperThemeOverlayPolicy(semWallpaperThemeManager);
                synchronized (OverlayPolicyManager.mLock) {
                    ((ArrayList) OverlayPolicyManager.policies).add(semWallpaperThemeOverlayPolicy);
                }
                instance = semWallpaperThemeManagerWrapper2;
                semWallpaperThemeManagerWrapper2.mSettings = overlayManagerSettingsHelper;
            }
            semWallpaperThemeManagerWrapper = instance;
        }
        return semWallpaperThemeManagerWrapper;
    }

    public static void increaseColorThemeSeq() {
        int i = sGlobalColorThemeSeq + 1;
        sGlobalColorThemeSeq = i;
        sGlobalColorThemeSeq = Math.max(i, 1);
        SystemProperties.set("debug.wallpaper.theme.seq", "" + sGlobalColorThemeSeq);
    }

    public final void applyWallpaperColor(List list, List list2, boolean z, boolean z2) {
        SemWallpaperThemeManager semWallpaperThemeManager = this.mWallpaperThemeManager;
        int callingUid = Binder.getCallingUid();
        if (mContext.getPackageManager().checkSignatures(1000, callingUid) != 0) {
            ThemeUtil.saveSWTLog("SWT_WTM_Wrapper", "applyWallpaperColor called by abnormal uid : " + callingUid);
            return;
        }
        Log.i("SWT_WTM_Wrapper", "invoked applyWallpaperColor, isGray=" + z + ", colorSS=" + list + ", colorGG=" + list2);
        try {
            try {
                Trace.traceBegin(8192L, "SWT:applyWallpaperColors, wallpaperColors:" + list);
                initTemplateMetadataIfNeeded();
                if (!z2 && this.mIsThemeParkApplied && semWallpaperThemeManager.setTemplateAsColorTheme()) {
                    this.mIsThemeParkApplied = false;
                }
                if (list != null) {
                    long uptimeMillis = SystemClock.uptimeMillis();
                    semWallpaperThemeManager.mPalette.setPalette(list, list2, z);
                    OverlayManagerTransaction.Builder builder = new OverlayManagerTransaction.Builder();
                    semWallpaperThemeManager.registerMonetOverlay(builder);
                    semWallpaperThemeManager.registerThemeOverlay(builder);
                    semWallpaperThemeManager.enableMonetOverlay(builder);
                    semWallpaperThemeManager.enableThemeOverlay(builder);
                    unregisterThemeParkOverlays(builder);
                    try {
                        increaseColorThemeSeq();
                        long uptimeMillis2 = SystemClock.uptimeMillis();
                        IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay")).commit(builder.build());
                        semWallpaperThemeManager.saveWallpaperThemeColor();
                        semWallpaperThemeManager.saveWallpaperThemeState(1);
                        ThemeUtil.saveSWTLog("SWT_WTM_Wrapper", "[Enable] commit OverlayManagerTransaction, dur1:" + (uptimeMillis2 - uptimeMillis) + ", dur2:" + (SystemClock.uptimeMillis() - uptimeMillis2));
                    } catch (Exception e) {
                        ThemeUtil.saveSWTLog("SWT_WTM_Wrapper", "FAILED at commit, e=" + e);
                        semWallpaperThemeManager.mCurrentMonetOverlays = null;
                        semWallpaperThemeManager.mCurrentThemeOverlays = null;
                    }
                    if (!z2) {
                        File file = new File("/data/overlays/themepark/state_applied.txt");
                        if (file.exists()) {
                            file.delete();
                        }
                    }
                } else {
                    semWallpaperThemeManager.mPalette.setPalette(list, list2, z);
                    OverlayManagerTransaction.Builder builder2 = new OverlayManagerTransaction.Builder();
                    semWallpaperThemeManager.disableMonetOverlay(builder2);
                    semWallpaperThemeManager.disableThemeOverlay(builder2);
                    unregisterThemeParkOverlays(builder2);
                    try {
                        increaseColorThemeSeq();
                        long uptimeMillis3 = SystemClock.uptimeMillis();
                        IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay")).commit(builder2.build());
                        semWallpaperThemeManager.saveWallpaperThemeColor();
                        semWallpaperThemeManager.saveWallpaperThemeState(0);
                        ThemeUtil.saveSWTLog("SWT_WTM_Wrapper", "[Disable] commit OverlayManagerTransaction, dur:" + (SystemClock.uptimeMillis() - uptimeMillis3));
                    } catch (Exception e2) {
                        ThemeUtil.saveSWTLog("SWT_WTM_Wrapper", "FAILED at commit, e=" + e2);
                        semWallpaperThemeManager.mCurrentMonetOverlays = null;
                        semWallpaperThemeManager.mCurrentThemeOverlays = null;
                    }
                    File file2 = new File("/data/overlays/themepark/state_applied.txt");
                    if (file2.exists()) {
                        file2.delete();
                    }
                }
            } catch (Exception e3) {
                Log.e("SWT_WTM_Wrapper", "FAILED at applyWallpaperColor, e=" + e3);
                semWallpaperThemeManager.mCurrentMonetOverlays = null;
                semWallpaperThemeManager.mCurrentThemeOverlays = null;
            }
        } finally {
            Trace.traceEnd(8192L);
        }
    }

    public final int getColorThemeState() {
        OverlayInfo nullableOverlayInfo = this.mSettings.mSettings.getNullableOverlayInfo(sColorThemeOverlayId, 0);
        if (nullableOverlayInfo != null) {
            nullableOverlayInfo.isEnabled();
        }
        if (nullableOverlayInfo == null) {
            return -1;
        }
        return nullableOverlayInfo.isEnabled() ? 1 : 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x00d9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void initTemplateMetadataIfNeeded() {
        /*
            Method dump skipped, instructions count: 289
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.om.wallpapertheme.SemWallpaperThemeManagerWrapper.initTemplateMetadataIfNeeded():void");
    }

    public final OverlayManagerTransaction initWallpaperTheme() {
        SemWallpaperThemeManager semWallpaperThemeManager = this.mWallpaperThemeManager;
        try {
            long uptimeMillis = SystemClock.uptimeMillis();
            if (getColorThemeState() != 1) {
                return null;
            }
            initTemplateMetadataIfNeeded();
            Slog.d("SWT_WTM_Wrapper", "init color theme metadata has finished : " + (SystemClock.uptimeMillis() - uptimeMillis) + "ms");
            semWallpaperThemeManager.getClass();
            List readLastPalette = SemWallpaperThemeManager.readLastPalette();
            if (!((ArrayList) readLastPalette).isEmpty()) {
                Slog.i("SWT_WTM_Wrapper", "restore palette overlays");
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                semWallpaperThemeManager.mPalette.setPalette(arrayList, arrayList2, SemWallpaperThemeManager.splitPalette(readLastPalette, arrayList, arrayList2));
            }
            if (!SemWallpaperThemeUtils.isFotaUpgrade(mContext)) {
                return null;
            }
            OverlayManagerTransaction.Builder builder = new OverlayManagerTransaction.Builder();
            semWallpaperThemeManager.restoreMonetOverlay(builder, this.mSettings.mSettings.getNullableOverlayInfo(new OverlayIdentifier("android", "MonetPalette"), 0));
            Slog.i("SWT_WTM_Wrapper", "regenerate color theme overlays");
            semWallpaperThemeManager.registerThemeOverlay(builder);
            semWallpaperThemeManager.enableThemeOverlay(builder);
            Set allIdentifiersAndBaseCodePaths = this.mSettings.mSettings.getAllIdentifiersAndBaseCodePaths();
            ArrayList arrayList3 = new ArrayList();
            Iterator it = ((ArraySet) allIdentifiersAndBaseCodePaths).iterator();
            while (it.hasNext()) {
                Pair pair = (Pair) it.next();
                if (((OverlayIdentifier) pair.first).getOverlayName() != null && ((OverlayIdentifier) pair.first).getOverlayName().startsWith("SemWT_")) {
                    arrayList3.add((OverlayIdentifier) pair.first);
                }
            }
            if (!arrayList3.isEmpty()) {
                Slog.i("SWT_WTM_Wrapper", "unregister unnecessary color theme overlays for fota upgrade");
                semWallpaperThemeManager.unregisterNotExistedOverlay(builder, arrayList3);
            }
            try {
                File file = new File("/data/overlays/wallpapertheme/");
                if (file.exists()) {
                    FileUtils.setPermissions(file, 511, -1, -1);
                    Log.i("SWT_ThemeManager", "success to change color theme directory permissions");
                }
            } catch (Exception e) {
                Log.w("SWT_ThemeManager", "failed setPermissionsDirIfExisted, e:" + e);
            }
            SemWallpaperThemeUtils.updateFotaUpgradeStatus(mContext);
            Slog.d("SWT_WTM_Wrapper", "restoring color theme has finished, ready : " + (SystemClock.uptimeMillis() - uptimeMillis) + "ms");
            return builder.build();
        } catch (Exception e2) {
            BootReceiver$$ExternalSyntheticOutline0.m(e2, "failed initWallpaperTheme, wallpaper theming will not working, ex = ", "SWT_WTM_Wrapper");
            return null;
        }
    }

    public final void unregisterThemeParkOverlays(OverlayManagerTransaction.Builder builder) {
        Set allIdentifiersAndBaseCodePaths = this.mSettings.mSettings.getAllIdentifiersAndBaseCodePaths();
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArraySet) allIdentifiersAndBaseCodePaths).iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            if (((OverlayIdentifier) pair.first).getOverlayName() != null && ((OverlayIdentifier) pair.first).getOverlayName().startsWith("ThemePark_")) {
                arrayList.add((OverlayIdentifier) pair.first);
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        SemWallpaperThemeManager semWallpaperThemeManager = this.mWallpaperThemeManager;
        semWallpaperThemeManager.getClass();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            builder.unregisterFabricatedOverlay((OverlayIdentifier) it2.next());
        }
        semWallpaperThemeManager.saveThemeParkSingleThemeState();
        Log.i("SWT_ThemeManager", "[ThemePark FRRO] unregister ThemePark overlay : " + arrayList);
    }
}
