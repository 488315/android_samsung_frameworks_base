package com.sec.android.iaft;

import android.content.Context;

import java.util.HashMap;

public class IAFDHCDatabase {
    private static final int IAFDHCDBVersion = 5;
    private IAFDDiagnosis.IAFD_DATA mIafdHCDatabase;

    private IAFDHCDatabase() {
        this.mIafdHCDatabase = null;
    }

    private static class IAFDHCDatabaseHolder {
        private static final IAFDHCDatabase INSTANCE = new IAFDHCDatabase();

        private IAFDHCDatabaseHolder() {}
    }

    public static IAFDHCDatabase getInstance() {
        return IAFDHCDatabaseHolder.INSTANCE;
    }

    void init(Context context, String salesCode, boolean isCHN) {
        this.mIafdHCDatabase = new IAFDDiagnosis.IAFD_DATA();
        this.mIafdHCDatabase.controlInfo =
                new IAFDDiagnosis.IAFD_CONTROLINFO(
                        true, 2048, "at ", 1024, 1024, "#00 pc ", 256, 512);
        this.mIafdHCDatabase.controlInfo.setDBVersion(5);
        this.mIafdHCDatabase.hashMapJE_ClassNameTB = new HashMap<>();
        int i = 0 + 1;
        int i2 = i + 1;
        int i3 = i2 + 1;
        int i4 = i3 + 1;
        int i5 = i4 + 1;
        int i6 = i5 + 1;
        int i7 = i6 + 1;
        int i8 = i7 + 1;
        int i9 = i8 + 1;
        int i10 = i9 + 1;
        int i11 = i10 + 1;
        int i12 = i11 + 1;
        int i13 = i12 + 1;
        int i14 = i13 + 1;
        int i15 = i14 + 1;
        int i16 = i15 + 1;
        int i17 = i16 + 1;
        int i18 = i17 + 1;
        int i19 = i18 + 1;
        int i20 = i19 + 1;
        int i21 = i20 + 1;
        int i22 = i21 + 1;
        int i23 = i22 + 1;
        int i24 = i23 + 1;
        int i25 = i24 + 1;
        int i26 = i25 + 1;
        IAFDDiagnosis.IAFD_ENTITY[] jeCNTB = {
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    1,
                    true,
                    "ClassCastException",
                    null,
                    null,
                    0,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB),
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    28,
                    true,
                    "TransactionTooLargeException",
                    null,
                    "supportFlag>,<3",
                    i,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB),
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    2,
                    true,
                    "IndexOutOfBoundsException",
                    null,
                    null,
                    i2,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB),
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    2,
                    true,
                    "ArrayIndexOutOfBoundsException",
                    null,
                    null,
                    i3,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB),
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    2,
                    true,
                    "StringIndexOutOfBoundsException",
                    null,
                    null,
                    i4,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB),
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    7,
                    true,
                    "ClassNotFoundException",
                    null,
                    null,
                    i5,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB),
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    7,
                    true,
                    "NoClassDefFoundError",
                    null,
                    "supportFlag>,<3",
                    i6,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB),
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    8,
                    true,
                    "ArithmeticException",
                    null,
                    null,
                    i7,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB),
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    26,
                    true,
                    "JNI_ERR",
                    "",
                    null,
                    i8,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB),
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    12,
                    true,
                    "ExceptionInInitializerError",
                    null,
                    null,
                    i9,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB),
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    13,
                    true,
                    "InternalError",
                    null,
                    null,
                    i10,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB),
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    14,
                    true,
                    "StackOverflowError",
                    null,
                    null,
                    i11,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB),
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    15,
                    true,
                    "android.content.res.Resources$NotFoundException",
                    null,
                    null,
                    i12,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB),
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    20,
                    true,
                    "IllegalArgumentException",
                    null,
                    null,
                    i13,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB),
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    20,
                    true,
                    "InvalidParameterException",
                    null,
                    null,
                    i14,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB),
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    29,
                    true,
                    "SuperNotCalledException",
                    null,
                    null,
                    i15,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB),
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    4,
                    true,
                    "IllegalStateException",
                    null,
                    null,
                    i16,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB),
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    6,
                    true,
                    "NumberFormatException",
                    null,
                    null,
                    i17,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB),
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    9,
                    true,
                    "NoSuchMethodException",
                    null,
                    null,
                    i18,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB),
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    11,
                    true,
                    "PackageManager$NameNotFoundException",
                    null,
                    null,
                    i19,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB),
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    3,
                    true,
                    "ActivityNotFoundException",
                    null,
                    null,
                    i20,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB),
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    19,
                    true,
                    "MissingWebViewPackageException",
                    "libs>,<Failed to load WebView provider: No WebView installed",
                    "supportFlag>,<3",
                    i21,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB),
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    5,
                    true,
                    "SecurityException",
                    "libs>,<no longer supported>,<not exported from uid",
                    null,
                    i22,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB),
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    16,
                    true,
                    "InflateException",
                    null,
                    null,
                    i23,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB),
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    22,
                    true,
                    "invocationtargetexception",
                    null,
                    null,
                    i24,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB),
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    34,
                    true,
                    "SQLiteFullException",
                    "libs>,<database or disk is full",
                    "supportFlag>,<3",
                    i25,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB),
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    25,
                    true,
                    "OutOfMemoryError",
                    "32bit",
                    null,
                    i26,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB),
            new IAFDDiagnosis.IAFD_ENTITY(
                    2,
                    0,
                    true,
                    "NullPointerException",
                    SmLib_IafdConstant.KEY_PACKAGE_NAME,
                    null,
                    i26 + 1,
                    this.mIafdHCDatabase.hashMapJE_ClassNameTB)
        };
        this.mIafdHCDatabase.JE_ClassNameTB = jeCNTB;
        this.mIafdHCDatabase.controlInfo.setenableDetectAll32bitApp(false, null);
        this.mIafdHCDatabase.controlInfo.setreMovableAppPaths("/data/app/");
        this.mIafdHCDatabase.controlInfo.setwebView_pkgName("com.google.android.webview");
        this.mIafdHCDatabase.controlInfo.setCSCFilter(
                "0", "CHZ>,<CHN>,<CHM>,<CHU>,<CTC>,<CHC>,<BNZ>,<CBK", salesCode);
        this.mIafdHCDatabase.controlInfo.setWhiteList(
                "1",
                "android.app.stubs>,<com.android.cts>,<com.android.test>,<com.android.app1>,<com.android.app2>,<com.android.app3");
        this.mIafdHCDatabase.controlInfo.setIAFDDBControlFeature("1", "Repair>,<CHNONLY", isCHN);
        this.mIafdHCDatabase.controlInfo.sethashMapOfLinkForVocApp(
                "pairlinks>,<1400403000>,<null>,</tips->,<-321-0.html>,<zh>,<27_NoCheckUpdate_NoOneKey_onejump>,<1500170>,<1500170>,<34_NoCheckUpdate_NoOneKey_onejump>,<1490506>,<1490506>,<35_onekey_NoCheckUpdate>,<1500223>,<1500223");
        this.mIafdHCDatabase.controlInfo.sethashMapOfLinkForVocAppOnlyShow(
                "OnlyShowList>,<1400701000>,<com.csii.qujing.mobilebank>,<1614570>,<1614570>,<cn.com.nxy.mbank.hlj>,<1655654>,<1655654>,<com.nxy.mobilebank.hlj>,<1655654>,<1655654>,<com.nxy.mobilebank.hunan>,<1655659>,<1655659>,<cn.com.nxy.mbank.shanxi>,<1720535>,<1720535>,<com.cn.froad.mobileplatform>,<1720398>,<1720398>,<com.yitong.mbank.g>,<1720485>,<1720485>,<cn.com.nxy.mbank.hebei>,<1720498>,<1720498>,<com.sxnxs.mbank>,<1721309>,<1721309>,<io.github.nekoinverter.ehviewer>,<1721428>,<1721428>,<com.ss.android.ugc.aweme:;Only32bit>,<1720427>,<1720427>,<com.chinatelecom.chongqing.iworkhelp>,<1721484>,<1721484>,<com.mobile.boe>,<1721487>,<1721487>,<com.buybal.buybalpay.nxy.jkepay>,<1721497>,<1721497>,<com.citicbank.comb>,<1721512>,<1721512");
        IAFDDiagnosis.IAFD_ENTITY[] jedmTB = {
            new IAFDDiagnosis.IAFD_ENTITY(3, 10, true, "did not then call Service", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(3, 10, true, "Not allowed to", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(3, 23, true, "libjiagu.so", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(3, 23, true, "libDexHelper", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(3, 23, true, "libSecShell.so", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(3, 23, true, "Anonymous-DexFile@", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(3, 23, true, "com.secneo.apkwrapper", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(3, 25, true, "OutOfMemoryError", "32bit", null),
            new IAFDDiagnosis.IAFD_ENTITY(
                    3,
                    35,
                    true,
                    "Unable to get provider com.google.android.gsf.gservices.GservicesProvider",
                    "libs>,<android.provider.Settings",
                    "supportFlag>,<3"),
            new IAFDDiagnosis.IAFD_ENTITY(
                    3,
                    35,
                    true,
                    "Attempt to invoke interface method 'android.os.Bundle"
                        + " android.content.IContentProvider.call(android.content.AttributionSource,"
                        + " java.lang.String, java.lang.String, java.lang.String,"
                        + " android.os.Bundle)' on a null object reference",
                    "libs>,<android.provider.Settings",
                    "supportFlag>,<3"),
            new IAFDDiagnosis.IAFD_ENTITY(3, 17, true, "Unable to start activity", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(3, 18, true, "dlopen failed: library", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(
                    3,
                    19,
                    true,
                    "MissingWebViewPackageException",
                    "libs>,<Failed to load WebView provider: No WebView installed",
                    "supportFlag>,<3"),
            new IAFDDiagnosis.IAFD_ENTITY(
                    3,
                    27,
                    true,
                    "SecurityException",
                    "libs>,<has no access to content://media/external/images/media",
                    "supportFlag>,<3"),
            new IAFDDiagnosis.IAFD_ENTITY(
                    3,
                    27,
                    true,
                    "RecoverableSecurityException",
                    "libs>,<has no access to content://media/external/images/media",
                    "supportFlag>,<3"),
            new IAFDDiagnosis.IAFD_ENTITY(
                    3, 0, true, "NullPointerException", SmLib_IafdConstant.KEY_PACKAGE_NAME, null)
        };
        this.mIafdHCDatabase.JE_DetailMsgTB = jedmTB;
        IAFDDiagnosis.IAFD_ENTITY[] jecsTB = {
            new IAFDDiagnosis.IAFD_ENTITY(4, 23, true, "libjiagu.so", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(4, 23, true, "libDexHelper", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(4, 23, true, "libSecShell.so", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(4, 23, true, "Anonymous-DexFile@", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(4, 23, true, "com.secneo.apkwrapper", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(4, 18, true, "dlopen failed: library", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(
                    4,
                    19,
                    true,
                    "com.google.android.webview",
                    "libs>,<libmonochrome.so>,<libmonochrome_64.so>,<libwebviewchromium",
                    "supportFlag>,<3"),
            new IAFDDiagnosis.IAFD_ENTITY(
                    4,
                    19,
                    true,
                    "WebViewGoogle.apk",
                    "libs>,<libmonochrome.so>,<libmonochrome_64.so>,<libwebviewchromium",
                    "supportFlag>,<3"),
            new IAFDDiagnosis.IAFD_ENTITY(
                    4,
                    19,
                    true,
                    "com.google.android.trichromelibrary",
                    "libs>,<libmonochrome.so>,<libmonochrome_64.so>,<libwebviewchromium",
                    "supportFlag>,<3"),
            new IAFDDiagnosis.IAFD_ENTITY(
                    4,
                    19,
                    true,
                    "MissingWebViewPackageException",
                    "libs>,<Failed to load WebView provider: No WebView installed",
                    "supportFlag>,<3"),
            new IAFDDiagnosis.IAFD_ENTITY(
                    4, 19, true, "chromium-SystemWebViewGoogle", null, "supportFlag>,<3"),
            new IAFDDiagnosis.IAFD_ENTITY(
                    4, 19, true, "chromium-TrichromeWebViewGoogle", null, "supportFlag>,<3"),
            new IAFDDiagnosis.IAFD_ENTITY(
                    4, 34, true, "ENOSPC (No space left on device)", null, "supportFlag>,<3"),
            new IAFDDiagnosis.IAFD_ENTITY(
                    4, 0, true, "null pointer", SmLib_IafdConstant.KEY_PACKAGE_NAME, null),
            new IAFDDiagnosis.IAFD_ENTITY(
                    4, 24, true, "fault addr", SmLib_IafdConstant.KEY_PACKAGE_NAME, null)
        };
        this.mIafdHCDatabase.JE_CallStackTB = jecsTB;
        IAFDDiagnosis.IAFD_ENTITY[] necsTB = {
            new IAFDDiagnosis.IAFD_ENTITY(
                    5,
                    19,
                    true,
                    "com.google.android.webview",
                    "libs>,<libmonochrome.so>,<libmonochrome_64.so>,<libwebviewchromium",
                    "supportFlag>,<3"),
            new IAFDDiagnosis.IAFD_ENTITY(
                    5,
                    19,
                    true,
                    "WebViewGoogle.apk",
                    "libs>,<libmonochrome.so>,<libmonochrome_64.so>,<libwebviewchromium",
                    "supportFlag>,<3"),
            new IAFDDiagnosis.IAFD_ENTITY(
                    5,
                    19,
                    true,
                    "com.google.android.trichromelibrary",
                    "libs>,<libmonochrome.so>,<libmonochrome_64.so>,<libwebviewchromium",
                    "supportFlag>,<3"),
            new IAFDDiagnosis.IAFD_ENTITY(
                    5, 34, true, "ENOSPC (No space left on device)", null, "supportFlag>,<3"),
            new IAFDDiagnosis.IAFD_ENTITY(
                    5,
                    35,
                    true,
                    "Unable to get provider com.google.android.gsf.gservices.GservicesProvider",
                    "libs>,<android.provider.Settings",
                    "supportFlag>,<3")
        };
        this.mIafdHCDatabase.NE_CallStackTB = necsTB;
        IAFDDiagnosis.IAFD_ENTITY[] nehiTB = {
            new IAFDDiagnosis.IAFD_ENTITY(6, 23, true, "libjiagu.so", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(6, 23, true, "libDexHelper", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(6, 23, true, "libSecShell.so", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(6, 23, true, "Anonymous-DexFile@", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(6, 23, true, "com.secneo.apkwrapper", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(6, 14, true, "stack corruption", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(6, 14, true, "stack overflow", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(6, 21, true, "overflow_error", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(6, 25, true, "OutOfMemoryError", "32bit", null),
            new IAFDDiagnosis.IAFD_ENTITY(6, 25, true, "GL errors", "32bit", null),
            new IAFDDiagnosis.IAFD_ENTITY(6, 25, true, "GL_OUT_OF_MEMORY", "32bit", null),
            new IAFDDiagnosis.IAFD_ENTITY(6, 25, true, "bad_alloc", "32bit", null),
            new IAFDDiagnosis.IAFD_ENTITY(6, 26, true, "JNI DETECTED ERROR", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(6, 26, true, "JNI_ERR", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(
                    6, 7, true, "NoClassDefFoundError", null, "supportFlag>,<3"),
            new IAFDDiagnosis.IAFD_ENTITY(6, 20, true, "invalid_argument", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(6, 2, true, "out_of_range", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(6, 2, true, "length_error", null, null),
            new IAFDDiagnosis.IAFD_ENTITY(
                    6, 0, true, "null pointer", SmLib_IafdConstant.KEY_PACKAGE_NAME, null),
            new IAFDDiagnosis.IAFD_ENTITY(
                    6, 24, true, "fault addr", SmLib_IafdConstant.KEY_PACKAGE_NAME, null)
        };
        this.mIafdHCDatabase.NE_HeaderInfoTB = nehiTB;
    }

    public IAFDDiagnosis.IAFD_DATA getData() {
        return this.mIafdHCDatabase;
    }

    public int getHCDBversion() {
        return 5;
    }
}
