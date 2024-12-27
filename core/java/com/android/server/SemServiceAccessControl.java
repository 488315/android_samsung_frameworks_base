package com.android.server;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Binder;
import android.os.Build;
import android.os.Process;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.security.keystore.KeyProperties;
import android.security.keystore2.AndroidKeyStoreSpi;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

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

        public void add(String name) {
            add(name, -1000);
        }

        public void add(String name, String uid_name) {
            try {
                add(name, ((Integer) Process.class.getField(uid_name).get(null)).intValue());
            } catch (IllegalAccessException
                    | IllegalArgumentException
                    | NoSuchFieldException
                    | SecurityException e) {
                add(name, -1001);
            }
        }

        public void add(String name, int uid) {
            ArrayList uidList = this.allowMap.getOrDefault(name, new ArrayList<>());
            uidList.add(Integer.valueOf(uid));
            this.allowMap.put(name, uidList);
        }

        public void remove(String name) {
            this.allowMap.remove(name);
        }

        public boolean match(String name, int uid) {
            ArrayList uidList = this.allowMap.get(name);
            if (uidList == null) {
                return false;
            }
            if (uidList.contains(-1001)) {
                Log.e(
                        SemServiceAccessControl.TAG,
                        name + "'s UID need to set in android.os.Process!");
                return false;
            }
            if (uidList.contains(-1000)) {
                return true;
            }
            return uidList.contains(Integer.valueOf(uid));
        }
    }

    private void setScpKmAllowedPackages() {
        this.mScpKmAllowList = new AllowList();
        if (DEBUG) {
            this.mScpKmAllowList.add("com.sec.security.scpKmTest", 1000);
            this.mScpKmAllowList.add("com.samsung.android.tzv", 1000);
        }
    }

    private void setLccmAllowedPackages() {
        this.mLccmAllowList = new AllowList();
        this.mLccmAllowList.add("com.skms.android.agent");
        this.mLccmAllowList.add("com.skms.android.agent:remote");
        this.mLccmAllowList.add("com.samsung.android.ese");
    }

    private void setSKMSCardAllowedPackages() {
        this.mSKMSCardAllowList = new AllowList();
        this.mSKMSCardAllowList.add("com.android.nfc");
        this.mSKMSCardAllowList.add("com.skms.android.agent");
        this.mSKMSCardAllowList.add("com.skms.android.agent:remote");
        this.mSKMSCardAllowList.add("com.samsung.android.ese");
    }

    private void setFactoryAllowedPackages() {
        this.mFactoryAllowList = new AllowList();
        this.mFactoryAllowList.add("com.sem.factoryapp");
        this.mFactoryAllowList.add("com.skms.android.agent");
        this.mFactoryAllowList.add("com.skms.android.agent:remote");
        this.mFactoryAllowList.add("com.samsung.android.ese");
        this.mFactoryAllowList.add("com.android.nfc");
        this.mFactoryAllowList.add("com.salab.act");
        this.mFactoryAllowList.add("com.samsung.euicc.firmware");
    }

    private void setHWParamAllowedPackages() {
        this.mHWParamAllowList = new AllowList();
        this.mHWParamAllowList.add("com.sem.factoryapp");
        this.mHWParamAllowList.add("com.skms.android.agent");
        this.mHWParamAllowList.add("com.skms.android.agent:remote");
        this.mHWParamAllowList.add("com.samsung.android.ese");
    }

    private void setCosPatchAllowedPacakges() {
        this.mCosPatchAllowList = new AllowList();
        this.mCosPatchAllowList.add("com.skms.android.agent");
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
        this.mFactoryResetAllowList = new AllowList();
        this.mFactoryResetAllowList.add("com.sem.factoryapp");
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
        this.mGrdmAllowList = new AllowList();
        this.mGrdmAllowList.add("system", 1000);
        this.mGrdmAllowList.add("com.sem.factoryapp", 1000);
        if (DEBUG) {
            this.mGrdmAllowList.add("com.sec.security.grdmTest", 1000);
        }
    }

    public void addAllowedPackage(String packageName, int uid, PackageList pkglist) {
        switch (pkglist.ordinal()) {
            case 1:
                this.mScpKmAllowList.add(packageName, uid);
                break;
            case 2:
            default:
                this.mJavaPkgAllowList.add(packageName, uid);
                break;
            case 3:
                this.mSKMSCardAllowList.add(packageName, uid);
                break;
            case 4:
                this.mLccmAllowList.add(packageName, uid);
                break;
            case 5:
                this.mFactoryAllowList.add(packageName, uid);
                break;
            case 6:
                this.mHWParamAllowList.add(packageName, uid);
                break;
            case 7:
                this.mCosPatchAllowList.add(packageName, uid);
                break;
            case 8:
                this.mFactoryResetAllowList.add(packageName, uid);
                break;
        }
    }

    public void removeAllowedPackage(String packageName, PackageList pkglist) {
        switch (pkglist.ordinal()) {
            case 1:
                this.mScpKmAllowList.remove(packageName);
                break;
            case 2:
            default:
                this.mJavaPkgAllowList.remove(packageName);
                break;
            case 3:
                this.mSKMSCardAllowList.remove(packageName);
                break;
            case 4:
                this.mLccmAllowList.remove(packageName);
                break;
            case 5:
                this.mFactoryAllowList.remove(packageName);
                break;
            case 6:
                this.mHWParamAllowList.remove(packageName);
                break;
            case 7:
                this.mCosPatchAllowList.remove(packageName);
                break;
            case 8:
                this.mFactoryResetAllowList.remove(packageName);
                break;
        }
    }

    public boolean hasAccessPermission(PackageList pkglist) {
        switch (pkglist.ordinal()) {
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
        int uid = Binder.getCallingUid();
        boolean ret = allowList.match(packageName, uid % 100000);
        if (ret) {
            if (uid < 100000) {
                Log.i(
                        TAG,
                        "Requested package name = ["
                                + packageName
                                + NavigationBarInflaterView.SIZE_MOD_END);
            } else {
                Log.i(
                        TAG,
                        "Requested package name = ["
                                + packageName
                                + "], called from secure container");
            }
        } else {
            Log.e(
                    TAG,
                    "Permission denied. Package name = ["
                            + packageName
                            + "], UID = ["
                            + uid
                            + NavigationBarInflaterView.SIZE_MOD_END);
        }
        return ret;
    }

    public boolean SEAPIAccessPermission() {
        String packageName = getPackageName();
        int uid = Binder.getCallingUid();
        int userId = UserHandle.getCallingUserId();
        boolean ret = this.mJavaPkgAllowList.match(packageName, uid % 100000);
        if (!ret) {
            Log.e(
                    TAG,
                    "Permission denied. Package name = ["
                            + packageName
                            + "], UID = ["
                            + uid
                            + "], userId = ["
                            + userId
                            + NavigationBarInflaterView.SIZE_MOD_END);
        } else {
            if (uid >= 100000) {
                Log.i(
                        TAG,
                        "Requested package name = ["
                                + packageName
                                + "], called from secure container");
            } else {
                Log.i(
                        TAG,
                        "Requested package name = ["
                                + packageName
                                + NavigationBarInflaterView.SIZE_MOD_END);
            }
            int checkUid = uid % 100000;
            if (checkUid != 1000 && checkUid != 2000 && checkUid != 0) {
                Log.i(
                        TAG,
                        packageName
                                + " does not use permitted uid, validate certificate, UID = ["
                                + uid
                                + "], userId = ["
                                + userId
                                + NavigationBarInflaterView.SIZE_MOD_END);
                Signature currentCert = null;
                int i = 0;
                try {
                    PackageInfo info =
                            this.mContext
                                    .getPackageManager()
                                    .getPackageInfoAsUser(packageName, 134217728, userId);
                    if (info != null) {
                        info.signingInfo.getSigningCertificateHistory();
                        Log.d(TAG, "Get signing cert success");
                        try {
                            Signature[] tmpSign = info.signingInfo.getApkContentsSigners();
                            int length = tmpSign.length;
                            while (i < length) {
                                Signature signature = tmpSign[i];
                                currentCert = signature;
                                Log.d(
                                        TAG,
                                        "getApkContentsSigners = " + currentCert.toCharsString());
                                i++;
                                tmpSign = tmpSign;
                            }
                            if (SystemProperties.get("ro.product_ship", "false").equals("true")
                                    && SystemProperties.get("ro.system.build.tags", "test-keys")
                                            .equals("release-keys")) {
                                if (currentCert != null) {
                                    if (currentCert
                                                    .toCharsString()
                                                    .equalsIgnoreCase(
                                                            "30820411308202f9a003020102020900fd222d6fc87acde0300d06092a864886f70d010105050030819e310b3009060355040613024b523113301106035504080c0a536f7574684b6f7265613112301006035504070c095375776f6e43697479311b3019060355040a0c1253616d73756e67436f72706f726174696f6e310c300a060355040b0c03444d433114301206035504030c0b53616d73756e67436572743125302306092a864886f70d01090116166d2e73656375726974794073616d73756e672e636f6d3020170d3133303132343035323231305a180f32313132313233313035323231305a30819e310b3009060355040613024b523113301106035504080c0a536f7574684b6f7265613112301006035504070c095375776f6e43697479311b3019060355040a0c1253616d73756e67436f72706f726174696f6e310c300a060355040b0c03444d433114301206035504030c0b53616d73756e67436572743125302306092a864886f70d01090116166d2e73656375726974794073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100a2c51f56a1c8bf64ada0af152ced2344ac070b447efc85f1b69ce90fbc2b7a71257240c215eedbf7445c474fe34d62bc3035d79ba110859118f1200ecc9ae48b56400e187591272d59734e456d9dfd5a1f3227a30b9448bda84c2901b501295445e204ddb6f9f9e36b2560998f1764e446176fe5d83987220f8ed15106dc7c8ecb6798de45f5fbae54efe2b35a379631f545f84c98243aa4d92ef339330f954ad32e4e97aff69cbf68928484b03a8fa8eafdc8ff2a9801f249302d467b05f99a1680e4fb5b11624d5e53d67f09e86b82dd7305e3e483b12e3720fcccc2bc8857f13b6e1d60512074004f67d86241940eaba34afda2af3904b04913fa50f499f7020103a350304e301d0603551d0e04160414eef0f8211dccf6e442f3388889c9a3ea3ce0236c301f0603551d23041830168014eef0f8211dccf6e442f3388889c9a3ea3ce0236c300c0603551d13040530030101ff300d06092a864886f70d01010505000382010100395c7e7900c471e03fa9850905c6ab1edc5a8b7d43a16689d9bb1ec1a06513c4ea8f7471c6e474244174261cc151ae8d1a61019e0ed81fffee8afa1d01d85a32de796f4b46d0d5ddfcca7d1f90d523b54751f505a4e3b059569f24ba2564d72fbc4081533840f618c2993d935134d3c987605e032f6a12889af3190af1714a90f2a3476b8e0016ab45564bf10e611899babd86af33149ca6838b0a885c752ffe879f37997f262e819c62cf59caa794cfaaf8e3c462f5092a34264f0634316b13a67a644e104dc4070e8b6628a46f41da7e3c741f6edc21152f9f947dde6fe14b58f34e4d9e7abd103cb1ca9e09eb4fa5b553baa413329bd3919caca2d52e6d4b")
                                            || currentCert
                                                    .toCharsString()
                                                    .equalsIgnoreCase(
                                                            "308204d4308203bca003020102020900d20995a79c0daad6300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303632323132323531325a170d3338313130373132323531325a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100c986384a3e1f2fb206670e78ef232215c0d26f45a22728db99a44da11c35ac33a71fe071c4a2d6825a9b4c88b333ed96f3c5e6c666d60f3ee94c490885abcf8dc660f707aabc77ead3e2d0d8aee8108c15cd260f2e85042c28d2f292daa3c6da0c7bf2391db7841aade8fdf0c9d0defcf77124e6d2de0a9e0d2da746c3670e4ffcdc85b701bb4744861b96ff7311da3603c5a10336e55ffa34b4353eedc85f51015e1518c67e309e39f87639ff178107f109cd18411a6077f26964b6e63f8a70b9619db04306a323c1a1d23af867e19f14f570ffe573d0e3a0c2b30632aaec3173380994be1e341e3a90bd2e4b615481f46db39ea83816448ec35feb1735c1f3020103a382010b30820107301d0603551d0e04160414932c3af70b627a0c7610b5a0e7427d6cfaea3f1e3081d70603551d230481cf3081cc8014932c3af70b627a0c7610b5a0e7427d6cfaea3f1ea181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900d20995a79c0daad6300c0603551d13040530030101ff300d06092a864886f70d01010505000382010100329601fe40e036a4a86cc5d49dd8c1b5415998e72637538b0d430369ac51530f63aace8c019a1a66616a2f1bb2c5fabd6f313261f380e3471623f053d9e3c53f5fd6d1965d7b000e4dc244c1b27e2fe9a323ff077f52c4675e86247aa801187137e30c9bbf01c567a4299db4bf0b25b7d7107a7b81ee102f72ff47950164e26752e114c42f8b9d2a42e7308897ec640ea1924ed13abbe9d120912b62f4926493a86db94c0b46f44c6161d58c2f648164890c512dfb28d42c855bf470dbee2dab6960cad04e81f71525ded46cdd0f359f99c460db9f007d96ce83b4b218ac2d82c48f12608d469733f05a3375594669ccbf8a495544d6c5701e9369c08c810158")) {
                                        return true;
                                    }
                                    Log.e(TAG, "Permission denied. Unauthorized Signature.");
                                    return false;
                                }
                                Log.e(TAG, "failed to get signature");
                                return false;
                            }
                            Log.d(
                                    TAG,
                                    "Permission check skip, only check for ship with release-keys");
                            return true;
                        } catch (Throwable th) {
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
        }
        return ret;
    }

    public String getPackageName() {
        try {
            ActivityManager am = (ActivityManager) this.mContext.getSystemService("activity");
            int pid = Binder.getCallingPid();
            if (am.getRunningAppProcesses() != null) {
                for (ActivityManager.RunningAppProcessInfo processInfo :
                        am.getRunningAppProcesses()) {
                    if (processInfo.pid == pid) {
                        if (processInfo.pkgList.length == 0) {
                            return processInfo.processName;
                        }
                        return processInfo.pkgList[0];
                    }
                }
            }
            Log.i(
                    TAG,
                    "There is no getRunningAppProcesses, try to get process name via cmdline, pid ="
                        + " ["
                            + pid
                            + NavigationBarInflaterView.SIZE_MOD_END);
            return getProcessNameViaCmdLine(pid);
        } catch (Exception e) {
            Log.e(TAG, "Error occurs on checking package name.");
            e.printStackTrace();
            return "";
        }
    }

    private String getProcessNameViaCmdLine(int pid) {
        File cmdline = new File("/proc/" + pid + "/cmdline");
        try {
            BufferedReader reader =
                    new BufferedReader(new FileReader(cmdline, StandardCharsets.UTF_8));
            try {
                String cmdLineStr = reader.readLine();
                if (cmdLineStr != null) {
                    byte[] cmdLineBytes = cmdLineStr.getBytes(StandardCharsets.UTF_8);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    for (int i = 0; i < cmdLineBytes.length; i++) {
                        if (cmdLineBytes[i] != 0) {
                            outputStream.write(cmdLineBytes[i]);
                        }
                    }
                    byte[] cmdLineBytesWithoutBom = outputStream.toByteArray();
                    String str = new String(cmdLineBytesWithoutBom, StandardCharsets.UTF_8);
                    reader.close();
                    return str;
                }
                reader.close();
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
        int ret = ICCCcheckDeviceStatus();
        if (this.RET_ERR_NOT_SUPPORTED == ret) {
            Log.d(TAG, "Not Supported!!");
            return true;
        }
        if (ret != 0) {
            Log.e(TAG, "ICCCcheckDeviceStatus Fail!!");
            return false;
        }
        Log.d(TAG, "End checkStatus!!");
        return true;
    }

    private String allowListDecrypt(String CIPHER_KEY, String CIPHER_AL, String MAC_AL) {
        try {
            try {
                Log.i(TAG, "Start S-AL");
                PrivateKey privateKey = null;
                try {
                    if (CIPHER_KEY != null && CIPHER_AL != null) {
                        if (MAC_AL != null) {
                            Cipher cipher =
                                    Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
                            byte[] byteEncrypted = Base64.decode(CIPHER_KEY.getBytes(), 2);
                            if (byteEncrypted == null) {
                                Log.e(TAG, "TS Data Error");
                                return null;
                            }
                            if (byteEncrypted.length < 64) {
                                Log.e(TAG, "TS Data Error : " + byteEncrypted.length);
                                return null;
                            }
                            try {
                                Log.d(TAG, "GK");
                                KeyStore keyStore = KeyStore.getInstance(AndroidKeyStoreSpi.NAME);
                                keyStore.load(null);
                                KeyStore.Entry entry = keyStore.getEntry("SEMALKEY", null);
                                if (entry instanceof KeyStore.PrivateKeyEntry) {
                                    privateKey = ((KeyStore.PrivateKeyEntry) entry).getPrivateKey();
                                }
                            } catch (Error e) {
                                Log.e(TAG, "GS Er " + e);
                            } catch (Exception e2) {
                                Log.e(TAG, "GS Ex " + e2);
                            }
                            cipher.init(2, privateKey);
                            byte[] keyByte = cipher.doFinal(byteEncrypted);
                            if (keyByte != null && keyByte.length >= 64) {
                                byte[] IVByte = new byte[16];
                                byte[] aesKeyByte = new byte[32];
                                byte[] macKeyByte = new byte[16];
                                System.arraycopy(keyByte, 0, IVByte, 0, 16);
                                System.arraycopy(keyByte, 16, aesKeyByte, 0, 32);
                                System.arraycopy(keyByte, 48, macKeyByte, 0, 16);
                                byte[] encALBytes = Base64.decode(CIPHER_AL.getBytes(), 2);
                                Cipher cipherAES = Cipher.getInstance("AES/CBC/PKCS5Padding");
                                SecretKeySpec keySpec = new SecretKeySpec(aesKeyByte, "AES");
                                IvParameterSpec ivParamSpec = new IvParameterSpec(IVByte);
                                cipherAES.init(2, keySpec, ivParamSpec);
                                if (encALBytes == null) {
                                    Log.e(TAG, "eAB Size Error");
                                    return null;
                                }
                                byte[] alByte = cipherAES.doFinal(encALBytes);
                                if (alByte == null) {
                                    Log.e(TAG, "LD Data Error");
                                    return null;
                                }
                                String alData = new String(alByte, StandardCharsets.UTF_8);
                                Log.i(TAG, "Start verify");
                                String macData = verifyHmac(macKeyByte, alData);
                                if (macData != null) {
                                    if (macData.equalsIgnoreCase(MAC_AL)) {
                                        Log.i(TAG, "MS");
                                        return alData;
                                    }
                                    Log.e(TAG, "MF");
                                    return null;
                                }
                                return null;
                            }
                            Log.e(TAG, "KB Size Error");
                            return null;
                        }
                    }
                    Log.e(TAG, "TS KData Error");
                    return null;
                } catch (Error e3) {
                    e = e3;
                    Log.d(TAG, "D-err " + e);
                    return null;
                } catch (IllegalBlockSizeException e4) {
                    Log.e(TAG, "D-e IBEx");
                    deleteALFile();
                    return null;
                } catch (Exception e5) {
                    e = e5;
                    Log.d(TAG, "D-e " + e);
                    return null;
                }
            } catch (Error e6) {
                e = e6;
            } catch (Exception e7) {
                e = e7;
            }
        } catch (IllegalBlockSizeException e8) {
        }
    }

    private String verifyHmac(byte[] macKey, String inputData) {
        try {
            if (macKey == null || inputData == null) {
                Log.e(TAG, "VM Data Error");
                return null;
            }
            SecretKeySpec secretKey =
                    new SecretKeySpec(macKey, KeyProperties.KEY_ALGORITHM_HMAC_SHA256);
            Mac hasher = Mac.getInstance(KeyProperties.KEY_ALGORITHM_HMAC_SHA256);
            hasher.init(secretKey);
            byte[] hash = hasher.doFinal(inputData.getBytes());
            if (hash == null) {
                return null;
            }
            String ret = Base64.encodeToString(hash, 2);
            return ret;
        } catch (Error e) {
            Log.e(TAG, "VM Er " + e);
            return null;
        } catch (Exception e2) {
            Log.e(TAG, "VM Ex " + e2);
            return null;
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void setDAScpkmList() {
        /*
            Method dump skipped, instructions count: 790
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.SemServiceAccessControl.setDAScpkmList():void");
    }

    private void deleteALFile() {
        String fileName;
        Log.d(TAG, "D-DA");
        try {
            PackageManager pm = this.mContext.getPackageManager();
            ApplicationInfo agentInfo = pm.getApplicationInfo("com.skms.android.agent", 0);
            String getFilePath = agentInfo.dataDir;
            String filePath = getFilePath + "/files/";
            File folder = new File(filePath);
            File[] listOfFiles = folder.listFiles();
            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    if (file.isFile()
                            && (fileName = file.getName()) != null
                            && fileName.startsWith("SEMAL_")) {
                        Log.d(TAG, "DA D " + fileName);
                        File targetFile = new File(filePath + fileName);
                        if (targetFile.exists()) {
                            targetFile.delete();
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
        if (this.teeSigData == null) {
            Log.e(TAG, "TS Data Error");
            return null;
        }
        byte[] teeSig = Base64.decode(this.teeSigData.getBytes(), 2);
        return teeSig;
    }

    public byte[] getScpkmTeeListData() {
        if (this.teeListData == null) {
            Log.e(TAG, "TL Data Error");
            return null;
        }
        byte[] teeList = Base64.decode(this.teeListData.getBytes(), 2);
        return teeList;
    }
}
