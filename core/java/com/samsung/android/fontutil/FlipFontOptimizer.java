package com.samsung.android.fontutil;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.content.Context;
import android.content.p002pm.ApplicationInfo;
import android.content.p002pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.p009os.Binder;
import android.p009os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import com.android.internal.content.NativeLibraryHelper;
import com.samsung.android.feature.SemCscFeature;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class FlipFontOptimizer {
    private static final String FONT_PACKAGE = "com.monotype.android.font.";
    private static final String TAG = "FlipFontOptimizer";
    private Context mContext = null;
    private TypefaceFinder mTypefaceFinder = null;

    public void setFlipfont(Context context) {
        String sOverrideFont = SemCscFeature.getInstance().getString("CscFeature_Common_ConfigSpecialEdition");
        String currentFontName = Typeface.getFontNameFlipFont();
        if (sOverrideFont.contains("ThomBrowne") && currentFontName.equalsIgnoreCase("default")) {
            setFlipfont(context, "ArialNarrowProRegular");
        }
    }

    private void setFlipfont(Context context, String fontName) {
        this.mContext = context;
        FontWriter fontWriter = new FontWriter();
        PackageManager packageManager = this.mContext.getPackageManager();
        String[] installedFontList = getFontString();
        if (installedFontList == null) {
            Log.m98i(TAG, "changeFont():Installed font list is null");
            return;
        }
        int i = 0;
        while (true) {
            if (i >= installedFontList.length) {
                i = 0;
                break;
            } else if (!installedFontList[i].equalsIgnoreCase(fontName)) {
                i++;
            } else {
                int index = i;
                Log.m98i(TAG, "index" + index);
                break;
            }
        }
        if (fontName.equalsIgnoreCase("default")) {
            fontWriter.writeLoc("default#default");
            savePreferences(installedFontList[i], i);
            Log.m98i(TAG, "default font is selected..." + i);
            return;
        }
        SemTypeface sansTypeface = this.mTypefaceFinder.findMatchingTypefaceByName(fontName);
        if (sansTypeface == null) {
            Log.m98i(TAG, "change font failed");
            return;
        }
        String apkPath = sansTypeface.getFontPackageName();
        if (apkPath != null && !apkPath.startsWith(FONT_PACKAGE)) {
            return;
        }
        String selectedFont = sansTypeface.getTypefaceFilename();
        String fontdir = selectedFont.replaceAll(".xml", "");
        File fontDir = fontWriter.createFontDirectory(fontdir.replaceAll(" ", NativeLibraryHelper.CLEAR_ABI_OVERRIDE));
        if (fontDir == null) {
            Log.m96e(TAG, "create fontDir object is null ");
            return;
        }
        long token = Binder.clearCallingIdentity();
        Iterator<TypefaceFile> it = sansTypeface.mSansFonts.iterator();
        while (it.hasNext()) {
            TypefaceFile typefaceFile = it.next();
            try {
                Resources res = packageManager.getResourcesForApplication(apkPath);
                AssetManager assetManager = res.getAssets();
                Iterator<TypefaceFile> it2 = it;
                InputStream in = assetManager.open("fonts/" + typefaceFile.getFileName());
                fontWriter.copyFontFile(fontDir, in, typefaceFile.getDroidName());
                in.close();
                it = it2;
            } catch (Exception e) {
                Log.m98i(TAG, "Cannot copy FontFile from AssetManager, " + e.getMessage());
                Binder.restoreCallingIdentity(token);
                return;
            }
        }
        Binder.restoreCallingIdentity(token);
        fontWriter.writeLoc(fontDir.getAbsolutePath() + "#" + fontName);
        savePreferences(sansTypeface.getTypefaceFilename(), i);
        try {
            IActivityManager am = ActivityManager.getService();
            Configuration config = am.getGlobalConfiguration();
            config.FlipFont = Math.abs(fontName.hashCode()) + 1;
            am.updateConfiguration(config);
        } catch (RemoteException e2) {
            Log.m96e(TAG, "Cannot update Configuration : " + e2.getMessage());
        }
        ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
        List<ActivityManager.RunningTaskInfo> allTasks = activityManager.getRunningTasks(50);
        for (ActivityManager.RunningTaskInfo aTask : allTasks) {
            String s = aTask.baseActivity.getPackageName();
            if (s.compareTo("com.android.settings") != 0 && s.compareTo("com.sec.android.app.SecSetupWizard") != 0 && s.compareTo("com.samsung.music") != 0 && s.compareTo("com.sec.android.app.music") != 0) {
                if (s.compareTo("com.infraware.polarisoffice") == 0 || s.compareTo("com.infraware.polarisoffice4") == 0 || s.compareTo("com.infraware.polarisviewer4") == 0 || s.compareTo("com.infraware.PolarisOfficeStdForTablet") == 0 || s.compareTo("com.infraware.polarisviewer5tablet") == 0 || s.compareTo("com.infraware.polarisoffice5tablet") == 0 || s.compareTo("com.infraware.polarisoffice4.document") == 0 || s.compareTo("com.infraware.polarisoffice5") == 0 || s.compareTo("com.infraware.polarisoffice5.document") == 0 || s.compareTo("com.infraware.polarisviewer5") == 0 || s.compareTo("com.infraware.polarisviewer5.document") == 0) {
                    activityManager.forceStopPackage(s);
                } else if (s.compareTo("com.sec.android.app.camera") == 0) {
                    Log.m94d(TAG, "com.sec.android.app.camera == 0");
                } else {
                    activityManager.restartPackage(s);
                }
            }
        }
    }

    private void savePreferences(String fontName, int position) {
        long token = Binder.clearCallingIdentity();
        Settings.Global.putInt(this.mContext.getContentResolver(), "flip_font_style", position);
        Binder.restoreCallingIdentity(token);
    }

    private String[] getFontString() {
        ArrayList fontNames = getFontsVector();
        if (fontNames == null) {
            return null;
        }
        String[] retString = new String[fontNames.size()];
        for (int i = 0; i < fontNames.size(); i++) {
            retString[i] = (String) fontNames.get(i);
        }
        return retString;
    }

    private ArrayList getFontsVector() {
        this.mTypefaceFinder = new TypefaceFinder();
        ArrayList fontPackageNames = new ArrayList();
        ArrayList fontNames = new ArrayList();
        ArrayList typefaceFiles = new ArrayList();
        long token = Binder.clearCallingIdentity();
        try {
            PackageManager packageManager = this.mContext.getPackageManager();
            int i = 128;
            List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(128);
            for (ApplicationInfo installedAppInfo : installedApplications) {
                String fontPackageName = installedAppInfo.packageName;
                if (fontPackageName.startsWith(FONT_PACKAGE)) {
                    ApplicationInfo appInfo = packageManager.getApplicationInfo(fontPackageName, i);
                    appInfo.publicSourceDir = appInfo.sourceDir;
                    Resources res = packageManager.getResourcesForApplication(appInfo);
                    AssetManager fontAssetManager = res.getAssets();
                    this.mTypefaceFinder.findTypefaces(this.mContext, fontAssetManager, fontPackageName);
                }
                i = 128;
            }
            this.mTypefaceFinder.getSansEntries(this.mContext, packageManager, fontNames, typefaceFiles, fontPackageNames);
            return fontNames;
        } catch (Exception e) {
            Log.m96e(TAG, "Cannot make a Installed Font List : " + e.getMessage());
            return null;
        } finally {
            Binder.restoreCallingIdentity(token);
        }
    }
}
