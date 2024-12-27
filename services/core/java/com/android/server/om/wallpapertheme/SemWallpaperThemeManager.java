package com.android.server.om.wallpapertheme;

import android.app.AppGlobals;
import android.content.ContentResolver;
import android.content.Context;
import android.content.om.FabricatedOverlay;
import android.content.om.OverlayIdentifier;
import android.content.om.OverlayInfo;
import android.content.om.OverlayManagerTransaction;
import android.content.om.WallpaperThemeConstants;
import android.content.om.WallpaperThemeUtils;
import android.content.om.wallpapertheme.MetaDataManager;
import android.content.om.wallpapertheme.TemplateManager;
import android.content.om.wallpapertheme.ThemePalette;
import android.content.om.wallpapertheme.ThemeUtil;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.ParceledListSlice;
import android.os.BadParcelableException;
import android.os.Handler;
import android.os.SystemClock;
import android.os.Trace;
import android.provider.Settings;
import android.util.Log;

import com.android.server.pm.UserManagerService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SemWallpaperThemeManager {
    public static SemWallpaperThemeManager sInstance;
    public final Context mContext;
    public ArrayList mCurrentMonetOverlays;
    public ArrayList mCurrentThemeOverlays;
    public final Handler mExceptionHandler;
    public final MetaDataManager mMetaDataManager;
    public final OverlayGenerator mOverlayGenerator;
    public final IPackageManager mPackageManager;
    public final ThemePalette mPalette;
    public final TemplateManager mTemplateManager;
    public final UserManagerService mUserManager;

    public SemWallpaperThemeManager(Context context) {
        ThemePalette themePalette = new ThemePalette();
        this.mPalette = themePalette;
        MetaDataManager metaDataManager = new MetaDataManager();
        this.mMetaDataManager = metaDataManager;
        TemplateManager templateManager = new TemplateManager(metaDataManager, themePalette);
        this.mTemplateManager = templateManager;
        OverlayGenerator overlayGenerator = new OverlayGenerator();
        overlayGenerator.mMetaDataManager = metaDataManager;
        overlayGenerator.mTemplateManager = templateManager;
        overlayGenerator.mThemePalette = themePalette;
        this.mOverlayGenerator = overlayGenerator;
        this.mExceptionHandler = new Handler();
        this.mContext = context;
        this.mPackageManager = AppGlobals.getPackageManager();
        this.mUserManager = UserManagerService.getInstance();
    }

    public static List readLastPalette() {
        ArrayList arrayList = new ArrayList();
        File file = new File("/data/overlays/wallpapertheme/last_palette.txt");
        if (file.exists()) {
            BufferedReader bufferedReader = null;
            try {
                BufferedReader bufferedReader2 =
                        new BufferedReader(
                                new InputStreamReader(new FileInputStream(file), "UTF-8"));
                while (true) {
                    try {
                        String readLine = bufferedReader2.readLine();
                        if (readLine == null) {
                            break;
                        }
                        arrayList.add(Integer.valueOf(Integer.parseInt(readLine.trim())));
                    } catch (Exception e) {
                        e = e;
                        bufferedReader = bufferedReader2;
                        e.printStackTrace();
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                        }
                        return arrayList;
                    }
                }
                bufferedReader2.close();
            } catch (Exception e3) {
                e = e3;
            }
        }
        return arrayList;
    }

    public static boolean splitPalette(List list, List list2, List list3) {
        ArrayList arrayList = (ArrayList) list;
        if (arrayList.size() > 65) {
            list2.addAll(arrayList.subList(0, 65));
            list3.addAll(arrayList.subList(65, 130));
            return ((Integer) arrayList.get(130)).intValue() == 1;
        }
        list2.addAll(list);
        list3.addAll(list);
        return false;
    }

    public final void disableMonetOverlay(OverlayManagerTransaction.Builder builder) {
        try {
            Trace.traceBegin(8192L, "SWT:disableMonetOverlay");
            for (int i : this.mUserManager.getUserIds()) {
                builder.setEnabled(
                        new OverlayIdentifier("android", "SemWT_MonetPalette"), false, i);
                builder.setEnabled(
                        new OverlayIdentifier("android", "SemWT_G_MonetPalette"), false, i);
            }
            Log.i("SWT_ThemeManager", "disable palette theme overlay");
            Trace.traceEnd(8192L);
        } catch (Throwable th) {
            Trace.traceEnd(8192L);
            throw th;
        }
    }

    public final void disableThemeOverlay(OverlayManagerTransaction.Builder builder) {
        try {
            Trace.traceBegin(8192L, "SWT:disableThemeOverlay");
            if (this.mCurrentThemeOverlays == null) {
                generateThemeOverlay();
            }
            int[] userIds = this.mUserManager.getUserIds();
            Iterator it = this.mCurrentThemeOverlays.iterator();
            while (it.hasNext()) {
                FabricatedOverlay fabricatedOverlay = (FabricatedOverlay) it.next();
                for (int i : userIds) {
                    builder.setEnabled(fabricatedOverlay.getIdentifier(), false, i);
                }
            }
            Log.i("SWT_ThemeManager", "disable color theme overlay");
            Trace.traceEnd(8192L);
        } catch (Throwable th) {
            Trace.traceEnd(8192L);
            throw th;
        }
    }

    public final void enableMonetOverlay(OverlayManagerTransaction.Builder builder) {
        try {
            Trace.traceBegin(8192L, "SWT:enableMonetOverlay");
            if (this.mCurrentMonetOverlays == null && this.mPalette.getPaletteSS() != null) {
                generateMonetOverlay();
            }
            for (int i : this.mUserManager.getUserIds()) {
                builder.setEnabled(new OverlayIdentifier("android", "SemWT_MonetPalette"), true, i);
                builder.setEnabled(
                        new OverlayIdentifier("android", "SemWT_G_MonetPalette"), true, i);
            }
            Log.i("SWT_ThemeManager", "enable palette theme overlay");
            Trace.traceEnd(8192L);
        } catch (Throwable th) {
            Trace.traceEnd(8192L);
            throw th;
        }
    }

    public final void enableThemeOverlay(OverlayManagerTransaction.Builder builder) {
        try {
            Trace.traceBegin(8192L, "SWT:enableThemeOverlay");
            if (this.mCurrentThemeOverlays == null) {
                generateThemeOverlay();
            }
            int[] userIds = this.mUserManager.getUserIds();
            Iterator it = this.mCurrentThemeOverlays.iterator();
            while (it.hasNext()) {
                FabricatedOverlay fabricatedOverlay = (FabricatedOverlay) it.next();
                for (int i : userIds) {
                    builder.setEnabled(fabricatedOverlay.getIdentifier(), true, i);
                }
            }
            Log.i("SWT_ThemeManager", "enable color theme overlay");
            Trace.traceEnd(8192L);
        } catch (Throwable th) {
            Trace.traceEnd(8192L);
            throw th;
        }
    }

    public final void generateMonetOverlay() {
        try {
            Trace.traceBegin(8192L, "SWT:generateMonetOverlay");
            Log.i("SWT_ThemeManager", "generate palette theme overlay");
            this.mCurrentMonetOverlays = this.mOverlayGenerator.createMonetOverlay();
            this.mPalette.writeLastPalette();
        } finally {
            Trace.traceEnd(8192L);
        }
    }

    public final void generateThemeOverlay() {
        try {
            Trace.traceBegin(8192L, "SWT:generateThemeOverlay");
            Log.i("SWT_ThemeManager", "generate color theme overlay");
            this.mCurrentThemeOverlays = this.mOverlayGenerator.createThemeOverlays();
        } finally {
            Trace.traceEnd(8192L);
        }
    }

    public final void registerMonetOverlay(OverlayManagerTransaction.Builder builder) {
        try {
            Trace.traceBegin(8192L, "SWT:registerMonetOverlay");
            generateMonetOverlay();
            Iterator it = this.mCurrentMonetOverlays.iterator();
            while (it.hasNext()) {
                builder.registerFabricatedOverlay((FabricatedOverlay) it.next());
            }
            Log.i("SWT_ThemeManager", "register palette theme overlay");
            Trace.traceEnd(8192L);
        } catch (Throwable th) {
            Trace.traceEnd(8192L);
            throw th;
        }
    }

    public final void registerThemeOverlay(OverlayManagerTransaction.Builder builder) {
        try {
            Trace.traceBegin(8192L, "SWT:registerThemeOverlay");
            generateThemeOverlay();
            Iterator it = this.mCurrentThemeOverlays.iterator();
            while (it.hasNext()) {
                builder.registerFabricatedOverlay((FabricatedOverlay) it.next());
            }
            Log.i("SWT_ThemeManager", "register color theme overlay");
            Trace.traceEnd(8192L);
        } catch (Throwable th) {
            Trace.traceEnd(8192L);
            throw th;
        }
    }

    public final void restoreMonetOverlay(
            OverlayManagerTransaction.Builder builder, OverlayInfo overlayInfo) {
        try {
            Trace.traceBegin(8192L, "SWT:restoreMonetOverlay");
            if (overlayInfo != null) {
                builder.unregisterFabricatedOverlay(
                        new OverlayIdentifier("android", "MonetPalette"));
            }
            ArrayList createMonetOverlay = this.mOverlayGenerator.createMonetOverlay();
            this.mCurrentMonetOverlays = createMonetOverlay;
            Iterator it = createMonetOverlay.iterator();
            while (it.hasNext()) {
                builder.registerFabricatedOverlay((FabricatedOverlay) it.next());
            }
            for (int i : this.mUserManager.getUserIds()) {
                builder.setEnabled(new OverlayIdentifier("android", "SemWT_MonetPalette"), true, i);
                builder.setEnabled(
                        new OverlayIdentifier("android", "SemWT_G_MonetPalette"), true, i);
            }
            Trace.traceEnd(8192L);
        } catch (Throwable th) {
            Trace.traceEnd(8192L);
            throw th;
        }
    }

    public final void saveThemeParkSingleThemeState() {
        try {
            for (int i : this.mUserManager.getUserIds()) {
                Settings.System.putIntForUser(
                        this.mContext.getContentResolver(), "themepark_singletheme_state", 0, i);
            }
        } catch (Exception e) {
            Log.i(
                    "SWT_ThemeManager",
                    "[ThemePark FRRO] saveThemeParkSingleThemeState posted delay, due: " + e);
            this.mExceptionHandler.postDelayed(
                    new SemWallpaperThemeManager$$ExternalSyntheticLambda0(this), 1000L);
        }
    }

    public final void saveWallpaperThemeColor() {
        String str;
        String str2;
        boolean z;
        if (this.mPalette.getPaletteSS() == null || this.mPalette.getPaletteGG() == null) {
            str = "";
            str2 = "";
            z = false;
        } else {
            str = this.mPalette.getPaletteSS().toString();
            str2 = this.mPalette.getPaletteGG().toString();
            z = this.mPalette.mIsGray;
        }
        try {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            for (int i : this.mUserManager.getUserIds()) {
                Settings.System.putStringForUser(contentResolver, "wallpapertheme_color", str, i);
                Settings.System.putStringForUser(
                        contentResolver, "wallpapertheme_color_for_g", str2, i);
                Settings.System.putStringForUser(
                        contentResolver, "wallpapertheme_color_isgray", z ? "1" : "0", i);
            }
        } catch (Exception e) {
            Log.i("SWT_ThemeManager", "saveWallpaperThemeColor posted delay, due: " + e);
            this.mExceptionHandler.postDelayed(
                    new Runnable() { // from class:
                                     // com.android.server.om.wallpapertheme.SemWallpaperThemeManager$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            SemWallpaperThemeManager.this.saveWallpaperThemeColor();
                            Log.i("SWT_ThemeManager", "saveWallpaperThemeColor done");
                        }
                    },
                    1000L);
        }
    }

    public final void saveWallpaperThemeState(int i) {
        try {
            for (int i2 : this.mUserManager.getUserIds()) {
                Settings.System.putIntForUser(
                        this.mContext.getContentResolver(), "wallpapertheme_state", i, i2);
            }
        } catch (Exception e) {
            Log.i("SWT_ThemeManager", "saveWallpaperThemeState posted delay, due: " + e);
            this.mExceptionHandler.postDelayed(
                    new SemWallpaperThemeManager$$ExternalSyntheticLambda0(this, i, 1), 1000L);
        }
    }

    public final boolean setTemplateAsColorTheme() {
        boolean z;
        try {
            this.mTemplateManager.loadStaticTemplate(this.mContext);
            z = true;
        } catch (Exception e) {
            ThemeUtil.saveSWTLog("SWT_ThemeManager", "loading template, error = " + e);
            z = false;
        }
        try {
            ParceledListSlice installedPackages =
                    this.mPackageManager.getInstalledPackages(8832L, 0);
            if (installedPackages == null) {
                ThemeUtil.saveSWTLog(
                        "SWT_ThemeManager",
                        "Couldn't get package list properly, stop setTemplateAsColorTheme");
                return false;
            }
            for (PackageInfo packageInfo : installedPackages.getList()) {
                if (WallpaperThemeUtils.hasWallpaperThemeTemplate(packageInfo.applicationInfo)) {
                    this.mTemplateManager.update(packageInfo.applicationInfo);
                }
            }
            return z;
        } catch (BadParcelableException e2) {
            ThemeUtil.saveSWTLog(
                    "SWT_ThemeManager",
                    "Couldn't get package list properly, stop setTemplateAsColorTheme. ex = " + e2);
            return false;
        } catch (Exception e3) {
            ThemeUtil.saveSWTLog("SWT_ThemeManager", "setTemplateAsColorTheme, ex = " + e3);
            return false;
        }
    }

    public final void syncWallpaperThemeStateForUser(int i) {
        try {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            int i2 = Settings.System.getInt(contentResolver, "wallpapertheme_state", -1);
            if (i2 == 1) {
                String string = Settings.System.getString(contentResolver, "wallpapertheme_color");
                String string2 =
                        Settings.System.getString(contentResolver, "wallpapertheme_color_for_g");
                String string3 =
                        Settings.System.getString(contentResolver, "wallpapertheme_color_isgray");
                Settings.System.putIntForUser(contentResolver, "wallpapertheme_state", i2, i);
                if (string != null && string2 != null && string3 != null) {
                    Settings.System.putStringForUser(
                            contentResolver, "wallpapertheme_color", string, i);
                    Settings.System.putStringForUser(
                            contentResolver, "wallpapertheme_color_for_g", string2, i);
                    Settings.System.putStringForUser(
                            contentResolver, "wallpapertheme_color_isgray", string3, i);
                }
                Log.i(
                        "SWT_ThemeManager",
                        "syncWallpaperThemeStateForUser for user:" + i + ", state:" + i2);
            }
        } catch (Exception e) {
            Log.i("SWT_ThemeManager", "syncWallpaperThemeStateForUser posted delay, due: " + e);
            this.mExceptionHandler.postDelayed(
                    new SemWallpaperThemeManager$$ExternalSyntheticLambda0(this, i, 2), 1000L);
        }
    }

    public final void unregisterNotExistedOverlay(
            OverlayManagerTransaction.Builder builder, ArrayList arrayList) {
        try {
            Trace.traceBegin(8192L, "SWT:unregisterNotExistedOverlay");
            long uptimeMillis = SystemClock.uptimeMillis();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                OverlayIdentifier overlayIdentifier = (OverlayIdentifier) it.next();
                String overlayName = overlayIdentifier.getOverlayName();
                if (overlayName == null
                        || (!overlayName.equals("SemWT_MonetPalette")
                                && !overlayName.equals("SemWT_G_MonetPalette"))) {
                    Iterator it2 = this.mCurrentThemeOverlays.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            ThemeUtil.saveSWTLog(
                                    "SWT_ThemeManager",
                                    "unregister not existed overlay : " + overlayName);
                            builder.unregisterFabricatedOverlay(overlayIdentifier);
                            break;
                        }
                        if (((FabricatedOverlay) it2.next())
                                .getIdentifier()
                                .equals(overlayIdentifier)) {
                            break;
                        }
                    }
                }
            }
            Log.i(
                    "SWT_ThemeManager",
                    "unregisterNotExistedOverlay, dur:"
                            + (SystemClock.uptimeMillis() - uptimeMillis));
            Trace.traceEnd(8192L);
        } catch (Throwable th) {
            Trace.traceEnd(8192L);
            throw th;
        }
    }

    public final void updateTemplateMetadataFromPkg(PackageInfo packageInfo) {
        try {
            if (packageInfo != null) {
                try {
                } catch (Exception e) {
                    ThemeUtil.saveSWTLog(
                            "SWT_ThemeManager",
                            "updateTemplateMetadataFromPkg, packageName = "
                                    + packageInfo.packageName
                                    + ", ex = "
                                    + e);
                }
                if (packageInfo.applicationInfo != null) {
                    Trace.traceBegin(
                            8192L,
                            "SWT:updateTemplateMetadataFromPkg, pkg:" + packageInfo.packageName);
                    ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                    String str = packageInfo.packageName;
                    if (WallpaperThemeUtils.hasWallpaperThemeNotSupport(applicationInfo)) {
                        WallpaperThemeConstants.colorThemingDisableList.add(str);
                        return;
                    }
                    WallpaperThemeConstants.colorThemingDisableList.remove(str);
                    if (WallpaperThemeUtils.hasWallpaperThemeTemplate(applicationInfo)) {
                        Log.i("SWT_ThemeManager", "Update installed package's template : " + str);
                        this.mTemplateManager.update(applicationInfo);
                    }
                    if (WallpaperThemeUtils.hasWallpaperThemeMeta(applicationInfo)) {
                        Log.i("SWT_ThemeManager", "Update installed package's metadata : " + str);
                        this.mMetaDataManager.update(applicationInfo);
                    }
                }
            }
        } finally {
            Trace.traceEnd(8192L);
        }
    }

    public final void updateThemeOverlay(
            OverlayManagerTransaction.Builder builder, String str, int i) {
        FabricatedOverlay fabricatedOverlay;
        try {
            Trace.traceBegin(8192L, "SWT:updateThemeOverlay : " + str);
            if (i != -1) {
                OverlayGenerator overlayGenerator = this.mOverlayGenerator;
                Iterator it = overlayGenerator.mMetaDataManager.getPackageList().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        fabricatedOverlay = null;
                        break;
                    }
                    MetaDataManager.Package r5 = (MetaDataManager.Package) it.next();
                    if (str.equals(r5.getPackageName())) {
                        fabricatedOverlay = overlayGenerator.createOverlayForPkg(r5);
                        break;
                    }
                }
                Log.i(
                        "SWT_ThemeManager",
                        "update color theme overlay, pkg = "
                                + str
                                + ", overlay = "
                                + fabricatedOverlay);
                if (fabricatedOverlay != null) {
                    builder.registerFabricatedOverlay(fabricatedOverlay);
                    int i2 = 0;
                    if (i == 1) {
                        for (int i3 : this.mUserManager.getUserIds()) {
                            builder.setEnabled(fabricatedOverlay.getIdentifier(), true, i3);
                        }
                    }
                    if (this.mCurrentThemeOverlays != null) {
                        String str2 = "SemWT_" + str;
                        while (true) {
                            if (i2 >= this.mCurrentThemeOverlays.size()) {
                                break;
                            }
                            if (str2.equals(
                                    ((FabricatedOverlay) this.mCurrentThemeOverlays.get(i2))
                                            .getIdentifier()
                                            .getOverlayName())) {
                                this.mCurrentThemeOverlays.remove(i2);
                                Log.i(
                                        "SWT_ThemeManager",
                                        "Remove old one from array, pkg = " + str);
                                break;
                            }
                            i2++;
                        }
                        this.mCurrentThemeOverlays.add(fabricatedOverlay);
                    }
                }
            }
            Trace.traceEnd(8192L);
        } catch (Throwable th) {
            Trace.traceEnd(8192L);
            throw th;
        }
    }
}
