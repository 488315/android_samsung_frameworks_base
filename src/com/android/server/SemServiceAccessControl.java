package com.android.server;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Binder;
import android.os.Build;
import android.os.Process;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.util.Log;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes6.dex */
public class SemServiceAccessControl {
    private static final boolean DEBUG;
    private static final int SECURE_CONTAINNER_UID_PREFIX = 100000;
    private static final String TAG = "SEC_ESE_ServiceAccessControl";
    private Context mContext;
    private AllowList mCosPatchAllowList;
    private AllowList mFactoryAllowList;
    private AllowList mFactoryResetAllowList;
    private AllowList mGrdmAllowList;
    private AllowList mHWParamAllowList;
    private AllowList mJavaPkgAllowList;
    private AllowList mLccmAllowList;
    private AllowList mSKMSCardAllowList;
    private AllowList mScpKmAllowList;
    private int RET_ERR_NOT_SUPPORTED = -148;
    private boolean isDAFileExist = false;
    private String teeSigData = null;
    private String teeListData = null;

    public enum PackageList {
        MJavaPkgList,
        MScpKmPkgList,
        MGrdmPkgList,
        MSKMSCardPkgList,
        MLccmPkgList,
        MFactoryPkgList,
        MHWParamPkgList,
        MCosPatchPkgList,
        MFactoryResetList
    }

    public native int ICCCcheckDeviceStatus();

    static {
        DEBUG = "eng".equals(Build.TYPE) || "userdebug".equals(Build.TYPE);
    }

    public SemServiceAccessControl(Context context) {
        this.mContext = context;
        Log.i(TAG, "SemServiceAccessControl");
    }

    private static final class AllowList {
        private static final int UID_NEED_TO_SET = -1001;
        private static final int UID_NONE = -1000;
        HashMap<String, ArrayList<Integer>> allowMap = new HashMap<>();

        public void add(String str) {
            add(str, -1000);
        }

        public void add(String str, String str2) {
            try {
                add(str, ((Integer) Process.class.getField(str2).get(null)).intValue());
            } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException unused) {
                add(str, -1001);
            }
        }

        public void add(String str, int i) {
            ArrayList<Integer> orDefault = this.allowMap.getOrDefault(str, new ArrayList<>());
            orDefault.add(Integer.valueOf(i));
            this.allowMap.put(str, orDefault);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void remove(String str) {
            this.allowMap.remove(str);
        }

        public boolean match(String str, int i) {
            ArrayList<Integer> arrayList = this.allowMap.get(str);
            if (arrayList == null) {
                return false;
            }
            if (arrayList.contains(-1001)) {
                Log.e(SemServiceAccessControl.TAG, str + "'s UID need to set in android.os.Process!");
                return false;
            }
            if (arrayList.contains(-1000)) {
                return true;
            }
            return arrayList.contains(Integer.valueOf(i));
        }
    }

    private void setScpKmAllowedPackages() {
        AllowList allowList = new AllowList();
        this.mScpKmAllowList = allowList;
        if (DEBUG) {
            allowList.add("com.sec.security.scpKmTest", 1000);
            this.mScpKmAllowList.add("com.samsung.android.tzv", 1000);
        }
    }

    private void setLccmAllowedPackages() {
        AllowList allowList = new AllowList();
        this.mLccmAllowList = allowList;
        allowList.add("com.skms.android.agent");
        this.mLccmAllowList.add("com.skms.android.agent:remote");
        this.mLccmAllowList.add("com.samsung.android.ese");
    }

    private void setSKMSCardAllowedPackages() {
        AllowList allowList = new AllowList();
        this.mSKMSCardAllowList = allowList;
        allowList.add("com.android.nfc");
        this.mSKMSCardAllowList.add("com.samsung.android.nfc");
        this.mSKMSCardAllowList.add("com.skms.android.agent");
        this.mSKMSCardAllowList.add("com.skms.android.agent:remote");
        this.mSKMSCardAllowList.add("com.samsung.android.ese");
    }

    private void setFactoryAllowedPackages() {
        AllowList allowList = new AllowList();
        this.mFactoryAllowList = allowList;
        allowList.add("com.sem.factoryapp");
        this.mFactoryAllowList.add("com.skms.android.agent");
        this.mFactoryAllowList.add("com.skms.android.agent:remote");
        this.mFactoryAllowList.add("com.samsung.android.ese");
        this.mFactoryAllowList.add("com.android.nfc");
        this.mFactoryAllowList.add("com.samsung.android.nfc");
        this.mFactoryAllowList.add("com.salab.act");
        this.mFactoryAllowList.add("com.samsung.euicc.firmware");
    }

    private void setHWParamAllowedPackages() {
        AllowList allowList = new AllowList();
        this.mHWParamAllowList = allowList;
        allowList.add("com.sem.factoryapp");
        this.mHWParamAllowList.add("com.skms.android.agent");
        this.mHWParamAllowList.add("com.skms.android.agent:remote");
        this.mHWParamAllowList.add("com.samsung.android.ese");
    }

    private void setCosPatchAllowedPacakges() {
        AllowList allowList = new AllowList();
        this.mCosPatchAllowList = allowList;
        allowList.add("com.skms.android.agent");
        this.mCosPatchAllowList.add("com.skms.android.agent:remote");
        this.mCosPatchAllowList.add("com.samsung.android.ese");
        this.mCosPatchAllowList.add("com.samsung.euicc.firmware");
        this.mCosPatchAllowList.add("com.samsung.euicc");
        if (DEBUG) {
            this.mCosPatchAllowList.add("com.nxp.id.cas.jcoppatch.spinative");
            this.mCosPatchAllowList.add("com.nxp.ese.cosupdate");
            this.mCosPatchAllowList.add("com.nxp.id.ese.osupdate");
            this.mCosPatchAllowList.add("com.gemalto.handsetdev.ese.cosupdate.skmsagent");
            this.mCosPatchAllowList.add("com.gemalto.ese.cosupdateinterface");
            this.mCosPatchAllowList.add("com.gemalto.handsetdev.ese.cosupdate");
            this.mCosPatchAllowList.add("com.gemalto.handsetdev.ese.cosupdate.skmstresstestsagent");
            this.mCosPatchAllowList.add("com.gemalto.handsetdev.gtoscriptrunner");
            this.mCosPatchAllowList.add("com.sec.security.ese.proxy");
        }
    }

    private void setFactoryResetAllowedPackages() {
        AllowList allowList = new AllowList();
        this.mFactoryResetAllowList = allowList;
        allowList.add("com.sem.factoryapp");
        this.mFactoryResetAllowList.add("com.skms.android.agent");
        this.mFactoryResetAllowList.add("com.skms.android.agent:remote");
        this.mFactoryResetAllowList.add("com.samsung.android.ese");
    }

    public void setAllowedPackages() {
        this.mJavaPkgAllowList = new AllowList();
        setScpKmAllowedPackages();
        setLccmAllowedPackages();
        setSKMSCardAllowedPackages();
        setFactoryAllowedPackages();
        setCosPatchAllowedPacakges();
        setHWParamAllowedPackages();
        setFactoryResetAllowedPackages();
        this.mJavaPkgAllowList.add("system", 1000);
        this.mJavaPkgAllowList.add("com.sec.factory", 1000);
        this.mJavaPkgAllowList.add("com.sem.factoryapp", 1000);
        this.mJavaPkgAllowList.add("com.sec.facatfunction", 1000);
        this.mJavaPkgAllowList.add("com.sec.facuifunction", 1000);
        this.mJavaPkgAllowList.add("com.android.nfc");
        this.mJavaPkgAllowList.add("com.samsung.android.nfc");
        this.mJavaPkgAllowList.add("com.skms.android.agent");
        this.mJavaPkgAllowList.add("com.skms.android.agent:remote");
        this.mJavaPkgAllowList.add("com.samsung.android.ese");
        this.mJavaPkgAllowList.add("com.sec.android.app.felicatest", 1027);
        this.mJavaPkgAllowList.add("com.sec.nfc.felicalocktest");
        this.mJavaPkgAllowList.add("com.samsung.euicc.firmware", 1000);
        this.mJavaPkgAllowList.add("com.samsung.android.app.telephonyui", 1000);
        this.mJavaPkgAllowList.add("com.samsung.euicc", 1000);
        if (DEBUG) {
            this.mJavaPkgAllowList.add("com.sec.security.ese.proxy");
            this.mJavaPkgAllowList.add("com.sem.android.applettest");
            this.mJavaPkgAllowList.add("com.security.ese.unitteset");
            this.mJavaPkgAllowList.add("com.sec.ese.test", 1000);
            this.mJavaPkgAllowList.add("com.sec.ese.service.test", 1000);
            this.mJavaPkgAllowList.add("com.samsung.euicc.lpaClient", 1000);
            this.mJavaPkgAllowList.add("com.samsung.sem.stresstest", 1000);
            this.mJavaPkgAllowList.add("com.samsung.mobile_stresstest", 1000);
            this.mJavaPkgAllowList.add("com.samsung.wear_stresstest", 1000);
            this.mJavaPkgAllowList.add(".S_N_SPI", 1000);
            this.mJavaPkgAllowList.add(".S_Extended", 1000);
            this.mJavaPkgAllowList.add(".S_Extended_TA", 1000);
            this.mJavaPkgAllowList.add(".S_N_ECHO", 1000);
            this.mJavaPkgAllowList.add(".S_N_VCM", 1000);
            this.mJavaPkgAllowList.add(".S_appletcheck", 1000);
            this.mJavaPkgAllowList.add("com.nxp.id.cas.jrcpspisn110");
            this.mJavaPkgAllowList.add("com.nxp.id.cas.jrcpspi8052");
            this.mJavaPkgAllowList.add("com.nxp.id.cas.jcoppatch.spinative");
            this.mJavaPkgAllowList.add("com.nxp.ese.cosupdate");
            this.mJavaPkgAllowList.add("com.nxp.id.ese.osupdate");
            this.mJavaPkgAllowList.add("com.nxp.id.cas.semstest");
            this.mJavaPkgAllowList.add("com.nxp.sems.channel");
            this.mJavaPkgAllowList.add("com.nxp.id.cas.esetest");
            this.mJavaPkgAllowList.add("com.gemalto.handsetdev.ese.cosupdate.skmsagent");
            this.mJavaPkgAllowList.add("com.gemalto.ese.cosupdateinterface");
            this.mJavaPkgAllowList.add("com.gemalto.handsetdev.ese.cosupdate");
            this.mJavaPkgAllowList.add("com.gemalto.handsetdev.ese.cosupdate.skmstresstestsagent");
            this.mJavaPkgAllowList.add("com.gemalto.handsetdev.gtoscriptrunner");
            this.mJavaPkgAllowList.add("com.gemalto.handset.esetool");
            this.mJavaPkgAllowList.add("com.samsung.android.tzv", 1000);
        }
    }

    public void setGrdmAllowedPackages() {
        AllowList allowList = new AllowList();
        this.mGrdmAllowList = allowList;
        allowList.add("system", 1000);
        this.mGrdmAllowList.add("com.sem.factoryapp", 1000);
        if (DEBUG) {
            this.mGrdmAllowList.add("com.sec.security.grdmTest", 1000);
        }
    }

    public void addAllowedPackage(String str, int i, PackageList packageList) {
        switch (packageList.ordinal()) {
            case 1:
                this.mScpKmAllowList.add(str, i);
                break;
            case 2:
            default:
                this.mJavaPkgAllowList.add(str, i);
                break;
            case 3:
                this.mSKMSCardAllowList.add(str, i);
                break;
            case 4:
                this.mLccmAllowList.add(str, i);
                break;
            case 5:
                this.mFactoryAllowList.add(str, i);
                break;
            case 6:
                this.mHWParamAllowList.add(str, i);
                break;
            case 7:
                this.mCosPatchAllowList.add(str, i);
                break;
            case 8:
                this.mFactoryResetAllowList.add(str, i);
                break;
        }
    }

    public void removeAllowedPackage(String str, PackageList packageList) {
        switch (packageList.ordinal()) {
            case 1:
                this.mScpKmAllowList.remove(str);
                break;
            case 2:
            default:
                this.mJavaPkgAllowList.remove(str);
                break;
            case 3:
                this.mSKMSCardAllowList.remove(str);
                break;
            case 4:
                this.mLccmAllowList.remove(str);
                break;
            case 5:
                this.mFactoryAllowList.remove(str);
                break;
            case 6:
                this.mHWParamAllowList.remove(str);
                break;
            case 7:
                this.mCosPatchAllowList.remove(str);
                break;
            case 8:
                this.mFactoryResetAllowList.remove(str);
                break;
        }
    }

    public boolean hasAccessPermission(PackageList packageList) {
        switch (packageList.ordinal()) {
            case 1:
                setDAScpkmList();
                return hasAccessPermission(this.mScpKmAllowList);
            case 2:
                return hasAccessPermission(this.mGrdmAllowList);
            case 3:
                return hasAccessPermission(this.mSKMSCardAllowList);
            case 4:
                return hasAccessPermission(this.mLccmAllowList);
            case 5:
                return hasAccessPermission(this.mFactoryAllowList);
            case 6:
                return hasAccessPermission(this.mHWParamAllowList);
            case 7:
                return hasAccessPermission(this.mCosPatchAllowList);
            case 8:
                return hasAccessPermission(this.mFactoryResetAllowList);
            default:
                return hasAccessPermission(this.mJavaPkgAllowList);
        }
    }

    private boolean hasAccessPermission(AllowList allowList) {
        String packageName = getPackageName();
        int callingUid = Binder.getCallingUid();
        boolean match = allowList.match(packageName, callingUid % 100000);
        if (!match) {
            Log.e(TAG, "Permission denied. Package name = [" + packageName + "], UID = [" + callingUid + NavigationBarInflaterView.SIZE_MOD_END);
            return match;
        }
        if (callingUid >= 100000) {
            Log.i(TAG, "Requested package name = [" + packageName + "], called from secure container");
            return match;
        }
        Log.i(TAG, "Requested package name = [" + packageName + NavigationBarInflaterView.SIZE_MOD_END);
        return match;
    }

    public boolean SEAPIAccessPermission() {
        String packageName = getPackageName();
        int callingUid = Binder.getCallingUid();
        int callingUserId = UserHandle.getCallingUserId();
        int i = callingUid % 100000;
        boolean match = this.mJavaPkgAllowList.match(packageName, i);
        if (!match) {
            Log.e(TAG, "Permission denied. Package name = [" + packageName + "], UID = [" + callingUid + "], userId = [" + callingUserId + NavigationBarInflaterView.SIZE_MOD_END);
            return match;
        }
        if (callingUid >= 100000) {
            Log.i(TAG, "Requested package name = [" + packageName + "], called from secure container");
        } else {
            Log.i(TAG, "Requested package name = [" + packageName + NavigationBarInflaterView.SIZE_MOD_END);
        }
        if (i != 1000 && i != 2000 && i != 0) {
            Log.i(TAG, packageName + " does not use permitted uid, validate certificate, UID = [" + callingUid + "], userId = [" + callingUserId + NavigationBarInflaterView.SIZE_MOD_END);
            try {
                PackageInfo packageInfoAsUser = this.mContext.getPackageManager().getPackageInfoAsUser(packageName, 134217728, callingUserId);
                if (packageInfoAsUser != null) {
                    packageInfoAsUser.signingInfo.getSigningCertificateHistory();
                    Log.d(TAG, "Get signing cert success");
                    try {
                        r2 = null;
                        for (Signature signature : packageInfoAsUser.signingInfo.getApkContentsSigners()) {
                            Log.d(TAG, "getApkContentsSigners = " + signature.toCharsString());
                        }
                        if (!SystemProperties.get("ro.product_ship", "false").equals("true") || !SystemProperties.get("ro.system.build.tags", "test-keys").equals("release-keys")) {
                            Log.d(TAG, "Permission check skip, only check for ship with release-keys");
                            return true;
                        }
                        if (signature == null) {
                            Log.e(TAG, "failed to get signature");
                            return false;
                        }
                        if (signature.toCharsString().equalsIgnoreCase("30820411308202f9a003020102020900fd222d6fc87acde0300d06092a864886f70d010105050030819e310b3009060355040613024b523113301106035504080c0a536f7574684b6f7265613112301006035504070c095375776f6e43697479311b3019060355040a0c1253616d73756e67436f72706f726174696f6e310c300a060355040b0c03444d433114301206035504030c0b53616d73756e67436572743125302306092a864886f70d01090116166d2e73656375726974794073616d73756e672e636f6d3020170d3133303132343035323231305a180f32313132313233313035323231305a30819e310b3009060355040613024b523113301106035504080c0a536f7574684b6f7265613112301006035504070c095375776f6e43697479311b3019060355040a0c1253616d73756e67436f72706f726174696f6e310c300a060355040b0c03444d433114301206035504030c0b53616d73756e67436572743125302306092a864886f70d01090116166d2e73656375726974794073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100a2c51f56a1c8bf64ada0af152ced2344ac070b447efc85f1b69ce90fbc2b7a71257240c215eedbf7445c474fe34d62bc3035d79ba110859118f1200ecc9ae48b56400e187591272d59734e456d9dfd5a1f3227a30b9448bda84c2901b501295445e204ddb6f9f9e36b2560998f1764e446176fe5d83987220f8ed15106dc7c8ecb6798de45f5fbae54efe2b35a379631f545f84c98243aa4d92ef339330f954ad32e4e97aff69cbf68928484b03a8fa8eafdc8ff2a9801f249302d467b05f99a1680e4fb5b11624d5e53d67f09e86b82dd7305e3e483b12e3720fcccc2bc8857f13b6e1d60512074004f67d86241940eaba34afda2af3904b04913fa50f499f7020103a350304e301d0603551d0e04160414eef0f8211dccf6e442f3388889c9a3ea3ce0236c301f0603551d23041830168014eef0f8211dccf6e442f3388889c9a3ea3ce0236c300c0603551d13040530030101ff300d06092a864886f70d01010505000382010100395c7e7900c471e03fa9850905c6ab1edc5a8b7d43a16689d9bb1ec1a06513c4ea8f7471c6e474244174261cc151ae8d1a61019e0ed81fffee8afa1d01d85a32de796f4b46d0d5ddfcca7d1f90d523b54751f505a4e3b059569f24ba2564d72fbc4081533840f618c2993d935134d3c987605e032f6a12889af3190af1714a90f2a3476b8e0016ab45564bf10e611899babd86af33149ca6838b0a885c752ffe879f37997f262e819c62cf59caa794cfaaf8e3c462f5092a34264f0634316b13a67a644e104dc4070e8b6628a46f41da7e3c741f6edc21152f9f947dde6fe14b58f34e4d9e7abd103cb1ca9e09eb4fa5b553baa413329bd3919caca2d52e6d4b") || signature.toCharsString().equalsIgnoreCase("308204d4308203bca003020102020900d20995a79c0daad6300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303632323132323531325a170d3338313130373132323531325a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100c986384a3e1f2fb206670e78ef232215c0d26f45a22728db99a44da11c35ac33a71fe071c4a2d6825a9b4c88b333ed96f3c5e6c666d60f3ee94c490885abcf8dc660f707aabc77ead3e2d0d8aee8108c15cd260f2e85042c28d2f292daa3c6da0c7bf2391db7841aade8fdf0c9d0defcf77124e6d2de0a9e0d2da746c3670e4ffcdc85b701bb4744861b96ff7311da3603c5a10336e55ffa34b4353eedc85f51015e1518c67e309e39f87639ff178107f109cd18411a6077f26964b6e63f8a70b9619db04306a323c1a1d23af867e19f14f570ffe573d0e3a0c2b30632aaec3173380994be1e341e3a90bd2e4b615481f46db39ea83816448ec35feb1735c1f3020103a382010b30820107301d0603551d0e04160414932c3af70b627a0c7610b5a0e7427d6cfaea3f1e3081d70603551d230481cf3081cc8014932c3af70b627a0c7610b5a0e7427d6cfaea3f1ea181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900d20995a79c0daad6300c0603551d13040530030101ff300d06092a864886f70d01010505000382010100329601fe40e036a4a86cc5d49dd8c1b5415998e72637538b0d430369ac51530f63aace8c019a1a66616a2f1bb2c5fabd6f313261f380e3471623f053d9e3c53f5fd6d1965d7b000e4dc244c1b27e2fe9a323ff077f52c4675e86247aa801187137e30c9bbf01c567a4299db4bf0b25b7d7107a7b81ee102f72ff47950164e26752e114c42f8b9d2a42e7308897ec640ea1924ed13abbe9d120912b62f4926493a86db94c0b46f44c6161d58c2f648164890c512dfb28d42c855bf470dbee2dab6960cad04e81f71525ded46cdd0f359f99c460db9f007d96ce83b4b218ac2d82c48f12608d469733f05a3375594669ccbf8a495544d6c5701e9369c08c810158")) {
                            return true;
                        }
                        Log.e(TAG, "Permission denied. Unauthorized Signature.");
                        return false;
                    } catch (Throwable unused) {
                        Log.e(TAG, "Failed to get signatures");
                        return false;
                    }
                }
                Log.e(TAG, "Failed to get signing cert");
                return false;
            } catch (Exception e) {
                Log.e(TAG, "Failed to get PKG info");
                e.printStackTrace();
                return false;
            }
        }
        Log.i(TAG, packageName + " uses permitted uid, skip certificate validation");
        return match;
    }

    public String getPackageName() {
        try {
            ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
            int callingPid = Binder.getCallingPid();
            if (activityManager.getRunningAppProcesses() != null) {
                for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
                    if (runningAppProcessInfo.pid == callingPid) {
                        if (runningAppProcessInfo.pkgList.length == 0) {
                            return runningAppProcessInfo.processName;
                        }
                        return runningAppProcessInfo.pkgList[0];
                    }
                }
            }
            Log.i(TAG, "There is no getRunningAppProcesses, try to get process name via cmdline, pid = [" + callingPid + NavigationBarInflaterView.SIZE_MOD_END);
            return getProcessNameViaCmdLine(callingPid);
        } catch (Exception e) {
            Log.e(TAG, "Error occurs on checking package name.");
            e.printStackTrace();
            return "";
        }
    }

    private String getProcessNameViaCmdLine(int i) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("/proc/" + i + "/cmdline"), StandardCharsets.UTF_8));
            try {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    byte[] bytes = readLine.getBytes(StandardCharsets.UTF_8);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    for (byte b : bytes) {
                        if (b != 0) {
                            byteArrayOutputStream.write(b);
                        }
                    }
                    String str = new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
                    bufferedReader.close();
                    return str;
                }
                bufferedReader.close();
                return "";
            } finally {
            }
        } catch (Exception e) {
            Log.e(TAG, "Error occurs on read process name via cmdline.");
            e.printStackTrace();
            return "";
        }
    }

    public boolean checkStatus() {
        Log.d(TAG, "Start checkStatus!!");
        int ICCCcheckDeviceStatus = ICCCcheckDeviceStatus();
        if (this.RET_ERR_NOT_SUPPORTED == ICCCcheckDeviceStatus) {
            Log.d(TAG, "Not Supported!!");
            return true;
        }
        if (ICCCcheckDeviceStatus != 0) {
            Log.e(TAG, "ICCCcheckDeviceStatus Fail!!");
            return false;
        }
        Log.d(TAG, "End checkStatus!!");
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00c6 A[Catch: Error -> 0x0108, Exception -> 0x011b, IllegalBlockSizeException -> 0x012e, TryCatch #2 {IllegalBlockSizeException -> 0x012e, blocks: (B:3:0x0009, B:8:0x0016, B:10:0x0027, B:13:0x002d, B:15:0x0032, B:18:0x0043, B:20:0x005b, B:21:0x0084, B:23:0x008d, B:26:0x0091, B:28:0x00c6, B:30:0x00cc, B:32:0x00d2, B:34:0x00e4, B:36:0x00ea, B:38:0x00f0, B:40:0x00f6, B:42:0x00fc, B:47:0x0063, B:49:0x0074, B:50:0x0102), top: B:2:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00cc A[Catch: Error -> 0x0108, Exception -> 0x011b, IllegalBlockSizeException -> 0x012e, TryCatch #2 {IllegalBlockSizeException -> 0x012e, blocks: (B:3:0x0009, B:8:0x0016, B:10:0x0027, B:13:0x002d, B:15:0x0032, B:18:0x0043, B:20:0x005b, B:21:0x0084, B:23:0x008d, B:26:0x0091, B:28:0x00c6, B:30:0x00cc, B:32:0x00d2, B:34:0x00e4, B:36:0x00ea, B:38:0x00f0, B:40:0x00f6, B:42:0x00fc, B:47:0x0063, B:49:0x0074, B:50:0x0102), top: B:2:0x0009 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private java.lang.String allowListDecrypt(java.lang.String r10, java.lang.String r11, java.lang.String r12) {
        /*
            Method dump skipped, instructions count: 311
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.SemServiceAccessControl.allowListDecrypt(java.lang.String, java.lang.String, java.lang.String):java.lang.String");
    }

    private String verifyHmac(byte[] bArr, String str) {
        try {
            if (bArr == null || str == null) {
                Log.e(TAG, "VM Data Error");
                return null;
            }
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, KeyProperties.KEY_ALGORITHM_HMAC_SHA256);
            Mac mac = Mac.getInstance(KeyProperties.KEY_ALGORITHM_HMAC_SHA256);
            mac.init(secretKeySpec);
            byte[] doFinal = mac.doFinal(str.getBytes());
            if (doFinal == null) {
                return null;
            }
            return Base64.encodeToString(doFinal, 2);
        } catch (Error e) {
            Log.e(TAG, "VM Er " + e);
            return null;
        } catch (Exception e2) {
            Log.e(TAG, "VM Ex " + e2);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0193 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:78:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0189 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0216  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void setDAScpkmList() {
        /*
            Method dump skipped, instructions count: 593
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.SemServiceAccessControl.setDAScpkmList():void");
    }

    private void deleteALFile() {
        String name;
        Log.d(TAG, "D-DA");
        try {
            String str = this.mContext.getPackageManager().getApplicationInfo("com.skms.android.agent", 0).dataDir + "/files/";
            File[] listFiles = new File(str).listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    if (file.isFile() && (name = file.getName()) != null && name.startsWith("SEMAL_")) {
                        Log.d(TAG, "DA D " + name);
                        File file2 = new File(str + name);
                        if (file2.exists()) {
                            file2.delete();
                        }
                    }
                }
            }
        } catch (Error e) {
            Log.e(TAG, "D-DA er " + e);
        } catch (Exception e2) {
            Log.e(TAG, "D-DA ex " + e2);
        }
    }

    public boolean getScpkmDAFileSupport() {
        return this.isDAFileExist;
    }

    public byte[] getScpkmTeeSigData() {
        String str = this.teeSigData;
        if (str == null) {
            Log.e(TAG, "TS Data Error");
            return null;
        }
        return Base64.decode(str.getBytes(), 2);
    }

    public byte[] getScpkmTeeListData() {
        String str = this.teeListData;
        if (str == null) {
            Log.e(TAG, "TL Data Error");
            return null;
        }
        return Base64.decode(str.getBytes(), 2);
    }
}
