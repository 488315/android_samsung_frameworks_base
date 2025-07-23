package com.samsung.android.sdk.sfe.font;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
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

/* loaded from: classes6.dex */
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
    private static final String sOverrideFont = SemCscFeature.getInstance().getString("CscFeature_SetupWizard_ConfigStepSequenceType");
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
            boolean z = DEBUG;
            if (z) {
                Log.d(TAG, "setFontConfig start");
            }
            setFontConfig(mParser);
            mSetFontConfigFinished = true;
            if (z) {
                Log.d(TAG, "setFontConfig done");
            }
        }
    }

    public static boolean isSetConfigFinished() {
        return mSetFontConfigFinished;
    }

    private FontConfig getFontConfig() {
        String str = SystemProperties.get("ro.csc.sales_code");
        String concat = "/system/etc".concat((str.equals("MYM") || str.equals("BKD") || str.equals("BNG") || str.equals("BCK")) ? "/fonts_additional.xml" : "/fonts.xml");
        try {
            return FontListParser.parse(concat, SYSTEM_FONT_DIRECTORY, null, null, null, 0L, 0);
        } catch (Exception unused) {
            Log.e(TAG, concat + " does not exist on this system");
            return null;
        }
    }

    public String getSystemFontName(String str, boolean z, boolean z2) {
        synchronized (mMutex) {
            FontConfig.Font font = null;
            if (mParser == null) {
                if (DEBUG) {
                    Log.d(TAG, "getSystemFontName() - Parser is null");
                }
                return null;
            }
            Log.d(TAG, "getSystemFontName fontFamily = " + str + ", isItalic = " + z2 + ", isBold = " + z);
            int i = z ? 700 : 400;
            for (FontConfig.FontFamily fontFamily : mParser.getFamilies()) {
                if (fontFamily.getName() == null) {
                    Log.w(TAG, "getSystemFontName - family.getName() is NULL - Skip.");
                } else if (fontFamily.getName().equals(str)) {
                    FontConfig.Font[] fonts = fontFamily.getFonts();
                    int length = fonts.length;
                    for (int i2 = 0; i2 < length; i2++) {
                        font = fonts[i2];
                        if (font.getWeight() == i && font.isItalic() == z2) {
                            break;
                        }
                    }
                    return font.getFile().getAbsolutePath();
                }
            }
            for (FontConfig.Alias alias : mParser.getAliases()) {
                if (alias.getWeight() != 0) {
                    for (FontConfig.FontFamily fontFamily2 : mParser.getFamilies()) {
                        if (fontFamily2.getName() != null && fontFamily2.getName().equals(alias.getOriginal())) {
                            for (FontConfig.Font font2 : fontFamily2.getFonts()) {
                                if (font2.getWeight() == alias.getWeight() && font2.isItalic() == z2) {
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
        File file = new File("/data/app_fonts/");
        if (file.isDirectory() && file.list() != null && file.list().length == 0) {
            return "default";
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(OWNER_SANS_LOC_PATH);
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                try {
                    String readLine = bufferedReader.readLine();
                    bufferedReader.close();
                    fileInputStream.close();
                    return readLine;
                } finally {
                }
            } finally {
            }
        } catch (IOException unused) {
            return "default";
        }
    }

    public String getFontNameFlipFont(Context context) {
        if (context == null) {
            return null;
        }
        String[] split = getFullFlipFont(context).split("#");
        if (split.length < 2) {
            if (split[0].endsWith("default")) {
                return "default";
            }
            return null;
        }
        return split[1];
    }

    public String getFontPathFlipFont(Context context) {
        return getFullFlipFont(context).split("#")[0];
    }

    public String getFlipFontPath(Context context) {
        File file = new File(OWNER_SANS_LOC_PATH);
        if (!file.exists()) {
            String str = sOverrideFont;
            if (TextUtils.isEmpty(str) || !str.contains(OVERRIDE_TB)) {
                return null;
            }
            mFlipFontPath = "/system/fonts/ArialNarrow-Regular.ttf";
            return "/system/fonts/ArialNarrow-Regular.ttf";
        }
        long lastModified = file.lastModified();
        if (lastModified == mLastSystemFontChangedTime) {
            if (DEBUG) {
                Log.d(TAG, "System font not changed. -> flipFontPath = " + mFlipFontPath);
            }
            return mFlipFontPath;
        }
        String fontPathFlipFont = getFontPathFlipFont(context);
        String substring = fontPathFlipFont.substring(fontPathFlipFont.lastIndexOf("/") + 1);
        Log.d(TAG, "getFlipFontPath - strFontPath = " + fontPathFlipFont + ", strPackageName = " + substring);
        if (fontPathFlipFont.endsWith("default")) {
            String str2 = sOverrideFont;
            if (!TextUtils.isEmpty(str2) && str2.contains(OVERRIDE_TB)) {
                mFlipFontPath = "/system/fonts/ArialNarrow-Regular.ttf";
                return "/system/fonts/ArialNarrow-Regular.ttf";
            }
            mFlipFontPath = null;
            return null;
        }
        String str3 = fontPathFlipFont + "/DroidSans.ttf";
        if (DEBUG) {
            Log.d(TAG, "getFlipFontPath - DroidSans path: " + str3);
        }
        String str4 = FONT_PACKAGE + substring;
        File file2 = new File(str3);
        if (!file2.exists()) {
            String flipFontFromPackage = getFlipFontFromPackage(context, str4, getFontNameFlipFont(context));
            if (flipFontFromPackage == null) {
                return null;
            }
            mFlipFontPath = flipFontFromPackage;
            mLastSystemFontChangedTime = lastModified;
            return flipFontFromPackage;
        }
        String str5 = str4.toLowerCase() + MediaMetrics.SEPARATOR + getFontNameFlipFont(context) + ".ttf";
        insertFontData(str5, readFile(file2));
        mFlipFontPath = str5;
        mLastSystemFontChangedTime = lastModified;
        return str5;
    }

    private String getFlipFontFromPackage(Context context, String str, String str2) {
        String lowerCase = str.toLowerCase();
        String str3 = FONT_DIRECTORY + str2 + ".ttf";
        String str4 = lowerCase + MediaMetrics.SEPARATOR + str2 + ".ttf";
        if (DEBUG) {
            Log.d(TAG, "getFlipFontFromPakage : Application pakage name = " + lowerCase + " , font name = " + str2);
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(lowerCase, 128);
            applicationInfo.publicSourceDir = applicationInfo.sourceDir;
            InputStream open = packageManager.getResourcesForApplication(applicationInfo).getAssets().open(str3);
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            insertFontData(str4, bArr);
            return str4;
        } catch (Exception e) {
            e.printStackTrace();
            try {
                InputStream openInputStream = context.getContentResolver().openInputStream(Uri.parse(SecContentProviderURI.CONTENT + lowerCase + "/fonts/" + str2 + ".ttf"));
                try {
                    byte[] bArr2 = new byte[openInputStream.available()];
                    openInputStream.read(bArr2);
                    insertFontData(str4, bArr2);
                    if (openInputStream != null) {
                        openInputStream.close();
                    }
                    return str4;
                } finally {
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                return null;
            }
        }
    }

    private byte[] readFile(File file) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    byte[] bArr = new byte[4096];
                    while (true) {
                        int read = fileInputStream.read(bArr);
                        if (read != -1) {
                            byteArrayOutputStream.write(bArr, 0, read);
                        } else {
                            byte[] byteArray = byteArrayOutputStream.toByteArray();
                            byteArrayOutputStream.close();
                            fileInputStream.close();
                            return byteArray;
                        }
                    }
                } finally {
                }
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
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

    private static void insertFontData(String str, byte[] bArr) {
        if (SFFontManager_InsertFontData(str, bArr)) {
            return;
        }
        throwUncheckedException(SFError.getError());
    }

    private static void setFontConfig(FontConfig fontConfig) {
        if (SFFontManager_SetFontConfig(fontConfig)) {
            return;
        }
        throwUncheckedException(SFError.getError());
    }

    private static void throwUncheckedException(int i) {
        SFError.ThrowUncheckedException(i);
    }
}
