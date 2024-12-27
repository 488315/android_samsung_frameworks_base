package com.samsung.android.sdk.sfe.font;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.FontListParser;
import android.media.MediaMetrics;
import android.net.Uri;
import android.os.SystemProperties;
import android.sec.enterprise.content.SecContentProviderURI;
import android.text.FontConfig;
import android.text.TextUtils;
import android.util.Log;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.sdk.sfe.SFEffect;
import com.samsung.android.sdk.sfe.util.SFError;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FontManager {
    private static final String DROIDSANS = "DroidSans.ttf";
    private static final String FONT_DIRECTORY = "fonts/";
    private static final String FONT_PACKAGE = "com.monotype.android.font.";
    private static final String OVERRIDE_TB = "ThomBrowne";
    private static final String OWNER_SANS_LOC_PATH = "/data/app_fonts/0/sans.loc";
    private static final String SANS_LOC_POST = "/sans.loc";
    private static final String SANS_LOC_PRE = "/data/app_fonts/";
    private static final String SYSTEM_FONT_DIRECTORY = "/system/fonts/";
    private static final String TAG = "SFFontManager";
    private static String mFlipFontPath;
    private static final boolean DEBUG = SFEffect.DEBUG;
    private static final String sOverrideFont =
            SemCscFeature.getInstance().getString("CscFeature_SetupWizard_ConfigStepSequenceType");
    private static FontConfig mParser = null;
    private static boolean mSetFontConfigFinished = false;
    private static long mLastSystemFontChangedTime = 0;
    private static final Object mMutex = new Object();

    private static native boolean SFFontManager_InsertFontData(String str, byte[] bArr);

    private static native boolean SFFontManager_SetFontConfig(FontConfig fontConfig);

    public FontManager() {
        synchronized (mMutex) {
            mSetFontConfigFinished = false;
            mParser = getFontConfig();
            if (DEBUG) {
                Log.d(TAG, "setFontConfig start");
            }
            setFontConfig(mParser);
            mSetFontConfigFinished = true;
            if (DEBUG) {
                Log.d(TAG, "setFontConfig done");
            }
        }
    }

    public static boolean isSetConfigFinished() {
        return mSetFontConfigFinished;
    }

    private FontConfig getFontConfig() {
        String fontXmlName = "/fonts.xml";
        String sales_code = SystemProperties.get("ro.csc.sales_code");
        if (sales_code.equals("MYM")
                || sales_code.equals("BKD")
                || sales_code.equals("BNG")
                || sales_code.equals("BCK")) {
            fontXmlName = "/fonts_additional.xml";
        }
        String fontXmlPath = "/system/etc" + fontXmlName;
        try {
            FontConfig parser =
                    FontListParser.parse(fontXmlPath, "/system/fonts/", null, null, null, 0L, 0);
            return parser;
        } catch (Exception e) {
            Log.e(TAG, fontXmlPath + " does not exist on this system");
            return null;
        }
    }

    public String getSystemFontName(String fontFamily, boolean isBold, boolean isItalic) {
        synchronized (mMutex) {
            if (mParser == null) {
                if (DEBUG) {
                    Log.d(TAG, "getSystemFontName() - Parser is null");
                }
                return null;
            }
            Log.d(
                    TAG,
                    "getSystemFontName fontFamily = "
                            + fontFamily
                            + ", isItalic = "
                            + isItalic
                            + ", isBold = "
                            + isBold);
            int weight = isBold ? 700 : 400;
            for (FontConfig.FontFamily family : mParser.getFamilies()) {
                if (family.getName() != null) {
                    if (family.getName().equals(fontFamily)) {
                        FontConfig.Font font = null;
                        for (FontConfig.Font f : family.getFonts()) {
                            font = f;
                            if (f.getWeight() == weight && f.isItalic() == isItalic) {
                                break;
                            }
                        }
                        return font.getFile().getAbsolutePath();
                    }
                } else {
                    Log.w(TAG, "getSystemFontName - family.getName() is NULL - Skip.");
                }
            }
            for (FontConfig.Alias alias : mParser.getAliases()) {
                if (alias.getWeight() != 0) {
                    for (FontConfig.FontFamily family2 : mParser.getFamilies()) {
                        if (family2.getName() != null
                                && family2.getName().equals(alias.getOriginal())) {
                            for (FontConfig.Font font2 : family2.getFonts()) {
                                if (font2.getWeight() == alias.getWeight()
                                        && font2.isItalic() == isItalic) {
                                    return font2.getFile().getAbsolutePath();
                                }
                            }
                        }
                    }
                }
            }
            return null;
        }
    }

    public String getFullFlipFont(Context context) {
        if (context == null) {
            return null;
        }
        File mtFontsDir = new File("/data/app_fonts/");
        if (mtFontsDir.isDirectory()
                && mtFontsDir.list() != null
                && mtFontsDir.list().length == 0) {
            return "default";
        }
        try {
            FileInputStream fis = new FileInputStream(OWNER_SANS_LOC_PATH);
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                try {
                    String systemFont = br.readLine();
                    br.close();
                    fis.close();
                    return systemFont;
                } finally {
                }
            } finally {
            }
        } catch (IOException e) {
            return "default";
        }
    }

    public String getFontNameFlipFont(Context context) {
        if (context == null) {
            return null;
        }
        String sx = getFullFlipFont(context);
        String[] parts = sx.split("#");
        if (parts.length < 2) {
            if (parts[0].endsWith("default")) {
                return "default";
            }
            return null;
        }
        return parts[1];
    }

    public String getFontPathFlipFont(Context context) {
        String sx = getFullFlipFont(context);
        String[] parts = sx.split("#");
        return parts[0];
    }

    public String getFlipFontPath(Context context) {
        File file = new File(OWNER_SANS_LOC_PATH);
        if (!file.exists()) {
            if (TextUtils.isEmpty(sOverrideFont) || !sOverrideFont.contains(OVERRIDE_TB)) {
                return null;
            }
            mFlipFontPath = "/system/fonts/ArialNarrow-Regular.ttf";
            return mFlipFontPath;
        }
        long timeSansLocFile = file.lastModified();
        if (timeSansLocFile == mLastSystemFontChangedTime) {
            if (DEBUG) {
                Log.d(TAG, "System font not changed. -> flipFontPath = " + mFlipFontPath);
            }
            return mFlipFontPath;
        }
        String strFontPath = getFontPathFlipFont(context);
        String strPackageName = strFontPath.substring(strFontPath.lastIndexOf("/") + 1);
        Log.d(
                TAG,
                "getFlipFontPath - strFontPath = "
                        + strFontPath
                        + ", strPackageName = "
                        + strPackageName);
        if (!strFontPath.endsWith("default")) {
            String strFontPath2 = strFontPath + "/" + DROIDSANS;
            if (DEBUG) {
                Log.d(TAG, "getFlipFontPath - DroidSans path: " + strFontPath2);
            }
            String strPackageName2 = FONT_PACKAGE + strPackageName;
            File fontFile = new File(strFontPath2);
            if (!fontFile.exists()) {
                String strFontName = getFontNameFlipFont(context);
                String flipFontPath = getFlipFontFromPackage(context, strPackageName2, strFontName);
                if (flipFontPath == null) {
                    return null;
                }
                mFlipFontPath = flipFontPath;
                mLastSystemFontChangedTime = timeSansLocFile;
                return mFlipFontPath;
            }
            String fontName =
                    strPackageName2.toLowerCase()
                            + MediaMetrics.SEPARATOR
                            + getFontNameFlipFont(context)
                            + ".ttf";
            insertFontData(fontName, readFile(fontFile));
            mFlipFontPath = fontName;
            mLastSystemFontChangedTime = timeSansLocFile;
            return mFlipFontPath;
        }
        if (!TextUtils.isEmpty(sOverrideFont) && sOverrideFont.contains(OVERRIDE_TB)) {
            mFlipFontPath = "/system/fonts/ArialNarrow-Regular.ttf";
            return mFlipFontPath;
        }
        mFlipFontPath = null;
        return null;
    }

    private String getFlipFontFromPackage(
            Context context, String strPackageName, String strFontName) {
        String packageName = strPackageName.toLowerCase();
        String assetFontPath = FONT_DIRECTORY + strFontName + ".ttf";
        String fontName = packageName + MediaMetrics.SEPARATOR + strFontName + ".ttf";
        if (DEBUG) {
            Log.d(
                    TAG,
                    "getFlipFontFromPakage : Application pakage name = "
                            + packageName
                            + " , font name = "
                            + strFontName);
        }
        try {
            PackageManager mPackageManager = context.getPackageManager();
            ApplicationInfo appInfo = mPackageManager.getApplicationInfo(packageName, 128);
            appInfo.publicSourceDir = appInfo.sourceDir;
            Resources res = mPackageManager.getResourcesForApplication(appInfo);
            AssetManager assetManager = res.getAssets();
            InputStream in = assetManager.open(assetFontPath);
            byte[] fileBytes = new byte[in.available()];
            in.read(fileBytes);
            in.close();
            insertFontData(fontName, fileBytes);
            return fontName;
        } catch (Exception ex) {
            ex.printStackTrace();
            Uri uriFont =
                    Uri.parse(
                            SecContentProviderURI.CONTENT
                                    + packageName
                                    + "/fonts/"
                                    + strFontName
                                    + ".ttf");
            try {
                InputStream isFont = context.getContentResolver().openInputStream(uriFont);
                try {
                    byte[] fileBytes2 = new byte[isFont.available()];
                    isFont.read(fileBytes2);
                    insertFontData(fontName, fileBytes2);
                    if (isFont != null) {
                        isFont.close();
                    }
                    return fontName;
                } finally {
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private byte[] readFile(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            try {
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                try {
                    byte[] buffer = new byte[4096];
                    while (true) {
                        int read = fis.read(buffer);
                        if (read != -1) {
                            output.write(buffer, 0, read);
                        } else {
                            byte[] byteArray = output.toByteArray();
                            output.close();
                            fis.close();
                            return byteArray;
                        }
                    }
                } finally {
                }
            } catch (Throwable th) {
                try {
                    fis.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e);
            return null;
        } catch (IOException e2) {
            Log.d(TAG, "Exception reading file: " + e2);
            return null;
        }
    }

    private static void insertFontData(String path, byte[] buf) {
        if (!SFFontManager_InsertFontData(path, buf)) {
            throwUncheckedException(SFError.getError());
        }
    }

    private static void setFontConfig(FontConfig fc) {
        if (!SFFontManager_SetFontConfig(fc)) {
            throwUncheckedException(SFError.getError());
        }
    }

    private static void throwUncheckedException(int errno) {
        SFError.ThrowUncheckedException(errno);
    }
}
