package com.android.server.asks;

import android.app.AppGlobals;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ASKSManager;
import android.content.pm.IASKSManager;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.hardware.usb.V1_1.PortStatus_1_1$$ExternalSyntheticOutline0;
import android.net.ConnectivityManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.Slog;
import android.util.Xml;
import android.util.jar.StrictJarFile;

import com.android.internal.pm.parsing.pkg.AndroidPackageHidden;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.FastXmlSerializer;
import com.android.internal.util.XmlUtils;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageManagerServiceUtils;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.server.pm.install.UnknownSourceAppManager;
import com.samsung.android.service.EngineeringMode.EngineeringModeManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ASKSManagerService extends IASKSManager.Stub {
    public static String mASKSDeltaPolicyVersion = "20241029";
    public static String mASKSPolicyVersion = "00000000";
    public boolean ASKS_UNKNOWN_LIMIT_CALLPEM;
    public final String CA_CERT_PATH;
    public final String CA_CERT_SYSTEM_PATH;
    public final String EE_CERT_FILE;
    public final String ROOT_CERT_FILE;
    public final int TYPE_MOBILE;
    public final int TYPE_WIFI;
    public InstalledAppInfo installedAppInfoToStore;
    public boolean isFirstTime;
    public final HashMap mASKSStates;
    public final Context mContext;
    public final AtomicFile mFile;
    public PackageManagerInternal mPackageManagerInternal;
    public final HashMap mSessions;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.asks.ASKSManagerService$1, reason: invalid class name */
    public final class AnonymousClass1 implements FilenameFilter {
        @Override // java.io.FilenameFilter
        public final boolean accept(File file, String str) {
            return (str.startsWith("ASKS") && !str.contains("ROOT"))
                    || str.startsWith("RPAB")
                    || "ADP.xml".equals(str)
                    || "protection_list.xml".equals(str);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ASKSSession {
        public int emMode;
        public boolean isASKSTarget;
        public String mCAKeyIndex;
        public String mCertName;
        public String mCodePath;
        public Deletable mDeletable;
        public String mPackageDigest;
        public String mPackageName;
        public String mPackageNameHash;
        public Restrict mRestrict;
        public RUFSContainer mRufsContainer;
        public Signature[] mSignature;
        public String mTokenName;
        public String mVersion;

        public final void clear() {
            this.mPackageName = null;
            this.mVersion = null;
            this.mPackageNameHash = "";
            this.mPackageDigest = "";
            this.mCodePath = "";
            this.mTokenName = "";
            this.mCertName = "";
            this.mCAKeyIndex = "";
            this.mSignature = null;
            this.isASKSTarget = false;
            this.mRestrict = null;
            this.emMode = -1;
            this.mDeletable = null;
            this.mRufsContainer = null;
        }

        public final void setDeletable(Deletable deletable) {
            this.mDeletable = deletable;
        }

        public final void setEMMode(int i) {
            this.emMode = i;
        }

        public final void setRestrict(Restrict restrict) {
            this.mRestrict = restrict;
        }

        public final void setRufsContainer(RUFSContainer rUFSContainer) {
            this.mRufsContainer = rUFSContainer;
        }

        public final void setVersion(String str) {
            this.mVersion = str;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("version = ");
            sb.append(this.mVersion);
            if (this.mRestrict != null) {
                sb.append(", restrict = ");
                sb.append(this.mRestrict.toString());
            }
            sb.append(", em mode = ");
            sb.append(String.valueOf(this.emMode));
            if (this.mDeletable != null) {
                sb.append(", deletable = ");
                sb.append(this.mDeletable.toString());
            }
            return sb.toString();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ASKSState {
        public Restrict restrict = null;
        public int emMode = -1;
        public Deletable deletable = null;

        public final String toString() {
            StringBuffer stringBuffer = new StringBuffer("ASKSState info {");
            if (this.restrict != null) {
                stringBuffer.append("restrict = ");
                stringBuffer.append(this.restrict.toString());
            }
            if (this.emMode != -1) {
                stringBuffer.append(" emmode = ");
                stringBuffer.append(String.valueOf(this.emMode));
            }
            if (this.deletable != null) {
                stringBuffer.append(" deletable = ");
                stringBuffer.append(this.deletable.toString());
            }
            stringBuffer.append("}");
            return stringBuffer.toString();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CURPARAM {
        public String baseCodePath;
        public String domain;
        public String downloadUrl;
        public String hashDomain;
        public String initiatingPackageName;
        public String originatingPackageName;
        public String packageName;
        public String[] permList;
        public String pkgNameHash;
        public String pkgSigHash;
        public String referralUrl;
        public int sdkVersion;
        public String[] servicePermList;
        public String sigHashValue;
        public Signature[] signatures;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CURSTATUS {
        public final String target_model;
        public int totalList;
        public String totalListString;
        public boolean isLocUrlCase = false;
        public boolean isLocZipCase = false;
        public boolean isLocWebCase = false;
        public boolean isLocAccessibilityCase = false;
        public boolean isIP = false;
        public boolean isDevDevice = false;
        public boolean isValidZip = true;
        public boolean isCheckZipFormat = false;
        public boolean isAllowSelfUpdate = false;
        public boolean isBlockSelfUpdateWithPEM = false;
        public boolean isLimitOnlyKorMCC = false;
        public boolean isTabletExcepted = false;
        public boolean isCheckRequestInstallPEM = false;
        public boolean hasReqInstallPEM = false;

        public CURSTATUS(String str) {
            this.target_model = str;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Deletable {
        public String mDatelimit;
        public String mVersion;

        public final void setDateLimit(String str) {
            this.mDatelimit = str;
        }

        public final void setVersion(String str) {
            this.mVersion = str;
        }

        public final String toString() {
            return "version = " + this.mVersion + ", datelimit = " + this.mDatelimit;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Restrict {
        public String mDatelimit;
        public String mFrom = null;
        public ArrayList mPermissionList;
        public String mType;
        public String mVersion;

        public final String toString() {
            StringBuilder sb = new StringBuilder("version = ");
            sb.append(this.mVersion);
            sb.append(", type = ");
            sb.append(this.mType);
            sb.append(", datelimit = ");
            sb.append(this.mDatelimit);
            sb.append(", from = ");
            sb.append(this.mFrom);
            if ("REVOKE".equals(this.mType)) {
                sb.append(", pm = ");
                for (int i = 0; i < this.mPermissionList.size(); i++) {
                    sb.append((String) this.mPermissionList.get(i));
                    sb.append("|");
                }
            }
            return sb.toString();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SafeInstallSAInfo {
        public final HashMap customDimensionMap;
        public final String eventName;
        public Long eventValue;

        public SafeInstallSAInfo(String str, Long l, HashMap hashMap) {
            this.eventName = str;
            this.eventValue = l;
            this.customDimensionMap = hashMap;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:124:0x0480  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0472 A[Catch: IOException -> 0x0429, TRY_ENTER, TryCatch #21 {IOException -> 0x0429, blocks: (B:121:0x0422, B:145:0x045c, B:147:0x0461, B:137:0x0472, B:139:0x0477), top: B:120:0x0422 }] */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0477 A[Catch: IOException -> 0x0429, TRY_LEAVE, TryCatch #21 {IOException -> 0x0429, blocks: (B:121:0x0422, B:145:0x045c, B:147:0x0461, B:137:0x0472, B:139:0x0477), top: B:120:0x0422 }] */
    /* JADX WARN: Removed duplicated region for block: B:145:0x045c A[Catch: IOException -> 0x0429, TRY_ENTER, TryCatch #21 {IOException -> 0x0429, blocks: (B:121:0x0422, B:145:0x045c, B:147:0x0461, B:137:0x0472, B:139:0x0477), top: B:120:0x0422 }] */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0461 A[Catch: IOException -> 0x0429, TRY_LEAVE, TryCatch #21 {IOException -> 0x0429, blocks: (B:121:0x0422, B:145:0x045c, B:147:0x0461, B:137:0x0472, B:139:0x0477), top: B:120:0x0422 }] */
    /* JADX WARN: Removed duplicated region for block: B:153:0x049c A[Catch: IOException -> 0x0498, TRY_LEAVE, TryCatch #17 {IOException -> 0x0498, blocks: (B:162:0x0494, B:153:0x049c), top: B:161:0x0494 }] */
    /* JADX WARN: Removed duplicated region for block: B:160:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0494 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0150 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ASKSManagerService(android.content.Context r20) {
        /*
            Method dump skipped, instructions count: 1334
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.asks.ASKSManagerService.<init>(android.content.Context):void");
    }

    public static void checkAttributeValue(XmlPullParser xmlPullParser, HashMap hashMap) {
        if (xmlPullParser.getAttributeValue(null, "name") == null
                || xmlPullParser.getAttributeValue(null, "execute") == null
                || xmlPullParser.getAttributeValue(null, "overlay") == null
                || xmlPullParser.getAttributeValue(null, "requestInstallerZip") == null
                || xmlPullParser.getAttributeValue(null, "initType") == null) {
            return;
        }
        String attributeValue = xmlPullParser.getAttributeValue(null, "name");
        if ("block".equals(xmlPullParser.getAttributeValue(null, "execute"))) {
            putInstalledList("blockExecute", attributeValue, hashMap);
        } else if ("allow".equals(xmlPullParser.getAttributeValue(null, "execute"))) {
            putInstalledList("allowExecute", attributeValue, hashMap);
        }
        if ("block".equals(xmlPullParser.getAttributeValue(null, "overlay"))) {
            putInstalledList("overlay", attributeValue, hashMap);
        }
        if ("true".equals(xmlPullParser.getAttributeValue(null, "requestInstallerZip"))) {
            putInstalledList("requestInstallerZip", attributeValue, hashMap);
        }
        if (!attributeValue.isEmpty()) {
            putInstalledList("isInstalledList", attributeValue, hashMap);
        }
        if (!xmlPullParser.getAttributeValue(null, "initType").isEmpty()) {
            putInstalledList(
                    "initType",
                    attributeValue + "," + xmlPullParser.getAttributeValue(null, "initType"),
                    hashMap);
        }
        if ("true".equals(xmlPullParser.getAttributeValue(null, "accessibility"))) {
            putInstalledList("accessibility", attributeValue, hashMap);
        }
        if ("true".equals(xmlPullParser.getAttributeValue(null, "hasReqInstallPEM"))) {
            putInstalledList("hasReqInstallPEM", attributeValue, hashMap);
        }
        if (xmlPullParser.getAttributeValue(null, "initPkg") != null
                && !xmlPullParser.getAttributeValue(null, "initPkg").isEmpty()) {
            putInstalledList(
                    "initPkg",
                    attributeValue + "," + xmlPullParser.getAttributeValue(null, "initPkg"),
                    hashMap);
        }
        if (xmlPullParser.getAttributeValue(null, "installAuthority") != null) {
            putInstalledList(
                    "installAuthority",
                    attributeValue
                            + ","
                            + xmlPullParser.getAttributeValue(null, "installAuthority")
                            + ","
                            + xmlPullParser.getAttributeValue(null, "installAuthorityDate"),
                    hashMap);
        }
    }

    public static int checkListForASKS(int i, String str, String str2) {
        HashMap hashMap = new HashMap();
        getASKSDataFromXML(i, hashMap);
        int i2 = -1;
        if (!hashMap.containsKey(str)) {
            return -1;
        }
        if (str2 != null) {
            ArrayList arrayList = (ArrayList) hashMap.get(str);
            if (arrayList != null && arrayList.contains(str2)) {
                i2 = arrayList.indexOf(str2);
            }
            if ((i != 9 && i != 14 && i != 15) || arrayList == null || !arrayList.contains("ALL")) {
                return i2;
            }
        }
        return 0;
    }

    public static HashMap checkPolicyFileWithDelta(ArrayList arrayList, int i) {
        int i2;
        int i3;
        HashMap hashMap = new HashMap();
        try {
            i3 = Integer.parseInt(mASKSPolicyVersion);
            i2 = Integer.parseInt(mASKSDeltaPolicyVersion);
        } catch (NumberFormatException unused) {
            i2 = 0;
            i3 = 1;
        }
        if (isDevDevice()) {
            ASKSManagerService$$ExternalSyntheticOutline0.m(
                    i3, i2, "base: ", ", delta: ", "PackageInformation");
        }
        if (i3 < i2) {
            if (isDevDevice()) {
                Slog.d("PackageInformation", "add delta data first.");
            }
            getUnknownAppsDataFromXML(i, arrayList, hashMap, true);
        }
        if (isDevDevice()) {
            Slog.d("PackageInformation", "add base data.");
        }
        getUnknownAppsDataFromXML(i, arrayList, hashMap, false);
        return hashMap;
    }

    public static RETVALUE checkTarget(
            CURPARAM curparam,
            Signature[] signatureArr,
            HashMap hashMap,
            String str,
            int i,
            CURSTATUS curstatus,
            String[] strArr,
            boolean z) {
        String str2;
        RETVALUE retvalue = new RETVALUE();
        retvalue.set(4, 0, 0, 0, "", "", null);
        StringBuilder sb = new StringBuilder(" checkTarget sign BEFORE status:");
        sb.append(retvalue.status);
        sb.append(" SA:");
        sb.append(retvalue.SA);
        sb.append(" policy=");
        SystemServiceManager$$ExternalSyntheticOutline0.m(
                sb, retvalue.policy, "PackageInformation");
        if (signatureArr != null) {
            int i2 = 0;
            while (true) {
                if (i2 >= signatureArr.length) {
                    break;
                }
                try {
                    str2 = getSigHash(signatureArr[i2]);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    str2 = null;
                }
                if (str2 == null || !hashMap.containsKey(str2)) {
                    i2++;
                } else {
                    UnknownStore unknownStore = (UnknownStore) hashMap.get(str2);
                    unknownStore.PKGNAME = curparam.packageName;
                    unknownStore.SIGHASH = curparam.sigHashValue;
                    unknownStore.PKGSIGHASH = curparam.pkgSigHash;
                    unknownStore.BASE_CODE_PATH = curparam.baseCodePath;
                    unknownStore.checkPolicy(curparam.pkgNameHash, retvalue);
                    if (retvalue.status == 1) {
                        try {
                            String convertToHex = convertToHex(getApkFileHashBytes(str));
                            if (convertToHex != null && !convertToHex.equals("null")) {
                                unknownStore.checkPolicyWithAppHash(
                                        curparam.pkgNameHash, convertToHex, retvalue);
                            }
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    if (!z && retvalue.status == 2) {
                        unknownStore.checkPolicyWithPEM(
                                strArr, i, curstatus.isLocUrlCase, retvalue);
                    }
                    if (retvalue.status == 0) {
                        return retvalue;
                    }
                }
            }
        }
        if (hashMap.containsKey("ALL")) {
            UnknownStore unknownStore2 = (UnknownStore) hashMap.get("ALL");
            unknownStore2.PKGNAME = curparam.packageName;
            unknownStore2.SIGHASH = curparam.sigHashValue;
            unknownStore2.PKGSIGHASH = curparam.pkgSigHash;
            unknownStore2.BASE_CODE_PATH = curparam.baseCodePath;
            unknownStore2.checkPolicy(curparam.pkgNameHash, retvalue);
            if (retvalue.status == 1) {
                try {
                    String convertToHex2 = convertToHex(getApkFileHashBytes(str));
                    if (convertToHex2 != null && !convertToHex2.equals("null")) {
                        unknownStore2.checkPolicyWithAppHash(
                                curparam.pkgNameHash, convertToHex2, retvalue);
                    }
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            if (!z && retvalue.status == 2) {
                unknownStore2.checkPolicyWithPEM(strArr, i, curstatus.isLocUrlCase, retvalue);
            }
        }
        return retvalue;
    }

    public static String convertItoS(int i) {
        return i != 0
                ? i != 1
                        ? (i == 100 || i == 101)
                                ? "warning"
                                : i != 140 ? i != 150 ? "except" : "block1" : "warning1"
                        : "block"
                : "except";
    }

    public static String convertMillsToString(long j) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        return simpleDateFormat.format(calendar.getTime());
    }

    public static int convertStoI(String str) {
        if (str != null) {
            switch (str) {
                case "block1":
                    return 150;
                case "except":
                    break;
                case "block":
                    return 1;
                case "warning1":
                    return 140;
                case "warning":
                    return isKorProject() ? 100 : 101;
                default:
                    if (!str.startsWith("block")) {
                        str.startsWith("warning");
                        break;
                    }
                    break;
            }
        }
        return 0;
    }

    public static String convertToHex(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        if (bArr == null) {
            return "null";
        }
        for (byte b : bArr) {
            int i = (b >>> 4) & 15;
            int i2 = 0;
            while (true) {
                sb.append((char) ((i < 0 || i > 9) ? i + 87 : i + 48));
                i = b & 15;
                int i3 = i2 + 1;
                if (i2 >= 1) {
                    break;
                }
                i2 = i3;
            }
        }
        return sb.toString();
    }

    public static void deleteFile(String str) {
        File file = new File(str);
        if (!file.exists()) {
            Slog.w("PackageInformation", file.getName() + " is does not exist");
            return;
        }
        if (file.delete()) {
            Slog.i("PackageInformation", "delete File : " + file.getName() + " success");
            return;
        }
        Slog.e("PackageInformation", "delete File : " + file.getName() + " fail");
    }

    public static void enforceSystemOrRoot(String str) {
        int callingUid = Binder.getCallingUid();
        if (Binder.getCallingPid() == Process.myPid()
                || callingUid == 0
                || callingUid == 1000
                || callingUid % 10000 == 1000) {
            return;
        }
        throw new SecurityException(callingUid + " : " + str);
    }

    public static boolean enforceSystemOrRoot() {
        int callingUid = Binder.getCallingUid();
        return Binder.getCallingPid() == Process.myPid()
                || callingUid == 0
                || callingUid == 1000
                || callingUid % 10000 == 1000;
    }

    public static byte[] findCertificateIndex(ASKSSession aSKSSession, byte[] bArr) {
        try {
            XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
            newPullParser.setInput(new ByteArrayInputStream(bArr), null);
            try {
                HashMap hashMap = new HashMap();
                hashMap.put("INDEX", "NONE");
                parseXMLNew$1(newPullParser, hashMap);
                String str = (String) hashMap.get("INDEX");
                Slog.i("AASA_ASKSManager", "index : " + str);
                if ("0.0".equals(str)) {
                    Slog.d("AASA_ASKSManager", "ENG Cert Index");
                } else {
                    String[] split = str.split("\\.");
                    String replaceAll = aSKSSession.mTokenName.replaceAll("[^0-9]", "");
                    aSKSSession.mCAKeyIndex = split[0];
                    Slog.d(
                            "AASA_ASKSManager",
                            "mTokenName : "
                                    + aSKSSession.mTokenName
                                    + " SignerVersion : "
                                    + replaceAll);
                    if ("".equals(replaceAll)) {
                        replaceAll = "1";
                    }
                    if (!replaceAll.equals(split[1])) {
                        Slog.d("AASA_ASKSManager", "Signer Cert File is not matched with index!");
                        return "21".getBytes();
                    }
                    if (checkListForASKS(12, "SIGNER", split[1]) != -1) {
                        Slog.d("AASA_ASKSManager", "SIGNER is in CRL");
                        return "21".getBytes();
                    }
                    if (checkListForASKS(12, "INTER", split[0]) != -1) {
                        Slog.d("AASA_ASKSManager", "INTER is in CRL");
                        return "21".getBytes();
                    }
                }
                return null;
            } catch (IOException e) {
                Slog.i("AASA_ASKSManager", " " + e.toString());
                return "21".getBytes();
            }
        } catch (XmlPullParserException e2) {
            Slog.i("AASA_ASKSManager", " " + e2.toString());
            return "21".getBytes();
        }
    }

    public static String get3rdTargetNodeName(String str) {
        File file = new File("/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_3RDPARTY_INSTALLER.xml");
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            if (!file.getParentFile().mkdir()) {
                Slog.e("PackageInformation", "failed to created folder related 3RDPARTY");
                return null;
            }
            file.getParentFile().setReadable(true, false);
        }
        if (!file.exists()) {
            return null;
        }
        try {
            FileReader fileReader = new FileReader(file, Charset.defaultCharset());
            try {
                XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
                newPullParser.setInput(fileReader);
                String str2 = null;
                for (int eventType = newPullParser.getEventType();
                        eventType != 1;
                        eventType = newPullParser.next()) {
                    if (eventType == 2) {
                        String name = newPullParser.getName();
                        if (name != null && name.equals("package")) {
                            str2 = newPullParser.getAttributeValue(null, "name");
                        } else if (name != null && name.equals("policy")) {
                            if (str2 != null && str2.equals(str)) {
                                String attributeValue =
                                        newPullParser.getAttributeValue(null, "name");
                                Slog.i(
                                        "PackageInformation",
                                        "3rdtargetPolicy:: : " + attributeValue);
                                fileReader.close();
                                return attributeValue;
                            }
                            str2 = null;
                        }
                    }
                }
                fileReader.close();
                return null;
            } catch (IOException e) {
                try {
                    fileReader.close();
                } catch (IOException unused) {
                }
                e.printStackTrace();
                return null;
            } catch (XmlPullParserException e2) {
                try {
                    fileReader.close();
                } catch (IOException unused2) {
                }
                e2.printStackTrace();
                return null;
            }
        } catch (FileNotFoundException e3) {
            e3.printStackTrace();
            return null;
        } catch (IOException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    public static void getASKSDataFromXML(int i, HashMap hashMap) {
        String str;
        ArrayList arrayList = new ArrayList();
        if (i != 46) {
            switch (i) {
                case 9:
                    arrayList.add("HASHVALUE");
                    arrayList.add("HASH");
                    str = "/data/system/.aasa/AASApolicy/ASKSB.xml";
                    break;
                case 10:
                    arrayList.add("HASHVALUE");
                    arrayList.add("UID");
                    str = "/data/system/.aasa/AASApolicy/ASKSP.xml";
                    break;
                case 11:
                    arrayList.add("STORE");
                    arrayList.add("DUMMY");
                    str = "/data/system/.aasa/AASApolicy/ASKSTS.xml";
                    break;
                case 12:
                    arrayList.add("CERT");
                    arrayList.add("NUM");
                    str = "/data/system/.aasa/AASApolicy/ASKSC.xml";
                    break;
                case 13:
                    arrayList.add("STORE");
                    arrayList.add("DUMMY");
                    str = "/data/system/.aasa/AASApolicy/ASKSK.xml";
                    break;
                case 14:
                    arrayList.add("HASHVALUE");
                    arrayList.add("HASH");
                    str = "/data/system/.aasa/AASApolicy/ASKSHB.xml";
                    break;
                case 15:
                    arrayList.add("package");
                    arrayList.add("digest");
                    str = "/data/system/.aasa/AASApolicy/protection_list.xml";
                    break;
                default:
                    str = "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_TARGETDEVICE.xml";
                    switch (i) {
                        case 22:
                            arrayList.add("package");
                            arrayList.add("CERT");
                            str = "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_PRE_INSTALLER_H.xml";
                            break;
                        case 23:
                            arrayList.add("TARGET");
                            arrayList.add("DEVICE");
                            break;
                        case 24:
                            arrayList.add("CERTTARGET");
                            arrayList.add("DEVICE");
                            break;
                        case 25:
                            arrayList.add("ZIPTARGET");
                            arrayList.add("DEVICE");
                            break;
                        case 26:
                            arrayList.add("ZIPCERTTARGET");
                            arrayList.add("DEVICE");
                            break;
                        default:
                            switch (i) {
                                case 32:
                                    arrayList.add("TARGET");
                                    arrayList.add("PEMLIST");
                                    str = "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_PEMLIST.xml";
                                    break;
                                case 33:
                                    arrayList.add("TARGETZIP");
                                    arrayList.add("PEMLIST");
                                    str = "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_PEMLIST.xml";
                                    break;
                                case 34:
                                    arrayList.add("package");
                                    arrayList.add("policy");
                                    str =
                                            "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_3RDPARTY_INSTALLER.xml";
                                    break;
                                case 35:
                                    arrayList.add("package");
                                    arrayList.add("CERT");
                                    str =
                                            "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_TRUSTEDSTORE.xml";
                                    break;
                                case 36:
                                    arrayList.add("package");
                                    arrayList.add("CERT");
                                    str =
                                            "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_SIMPLETRUSTEDSTORE.xml";
                                    break;
                                case 37:
                                    arrayList.add("REGIONAL");
                                    arrayList.add("DEVICE");
                                    break;
                                case 38:
                                    arrayList.add("config");
                                    arrayList.add("value");
                                    str = "/data/system/.aasa/AASApolicy/ASKS_SPAM_CONFIG.xml";
                                    break;
                                case 39:
                                    arrayList.add("contents");
                                    arrayList.add("pid");
                                    str = "/data/system/.aasa/AASApolicy/ASKS_BLOCK_URL_LIST.xml";
                                    break;
                                case 40:
                                    arrayList.add("contents");
                                    arrayList.add("pid");
                                    str = "/data/system/.aasa/AASApolicy/ASKS_BLOCK_NUM_LIST.xml";
                                    break;
                                case 41:
                                    arrayList.add("package");
                                    arrayList.add("CERT");
                                    str = "/data/system/.aasa/AASApolicy/RPAB.xml";
                                    break;
                                case 42:
                                    arrayList.add("package");
                                    arrayList.add("CERT");
                                    str = "/data/system/.aasa/AASApolicy/RPAB1.xml";
                                    break;
                                case 43:
                                    arrayList.add("package");
                                    arrayList.add("CERT");
                                    str = "/data/system/.aasa/AASApolicy/RPAB2.xml";
                                    break;
                                case 44:
                                    arrayList.add("TARGET");
                                    arrayList.add("VALUE");
                                    str = "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_DEVPARAM.xml";
                                    break;
                            }
                            return;
                    }
            }
        } else {
            arrayList.add("package");
            arrayList.add("CERT");
            str = "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_PREINSTALLER_GLOBAL.xml";
        }
        File file = new File(str);
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdir();
            file.getParentFile().setReadable(true, false);
        }
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                try {
                    XmlPullParser newPullParser = Xml.newPullParser();
                    newPullParser.setInput(fileReader);
                    String str2 = "";
                    ArrayList arrayList2 = null;
                    for (int eventType = newPullParser.getEventType();
                            eventType != 1;
                            eventType = newPullParser.next()) {
                        String name = newPullParser.getName();
                        if (eventType != 2) {
                            if (eventType == 3 && ((String) arrayList.get(0)).equals(name)) {
                                hashMap.put(str2, arrayList2);
                            }
                        } else if (((String) arrayList.get(0)).equals(name)) {
                            if (newPullParser.getAttributeValue(0) != null) {
                                str2 = newPullParser.getAttributeValue(0);
                            }
                            arrayList2 = new ArrayList();
                        } else if (arrayList.contains(name)
                                && newPullParser.getAttributeValue(0) != null
                                && arrayList2 != null) {
                            arrayList2.add(newPullParser.getAttributeValue(0));
                        }
                    }
                    fileReader.close();
                } catch (IOException e) {
                    try {
                        fileReader.close();
                    } catch (IOException unused) {
                    }
                    e.printStackTrace();
                } catch (XmlPullParserException e2) {
                    try {
                        fileReader.close();
                    } catch (IOException unused2) {
                    }
                    e2.printStackTrace();
                }
            } catch (FileNotFoundException e3) {
                e3.printStackTrace();
            }
        }
    }

    public static String getASKSPolicyVersion(String str, String str2) {
        HashMap hashMap = new HashMap();
        hashMap.put("ASKS_FILE", new String[] {"<asks version=\"", "\""});
        hashMap.put("ASKS_RULE_FILE", new String[] {"<VERSION value=\"", "\"/>"});
        hashMap.put("ASKS_DELTA", new String[] {"<safeinstall delta=\"", "\""});
        try {
            String[] split =
                    new String(Files.readAllBytes(Paths.get(str2, new String[0])))
                            .split(((String[]) hashMap.get(str))[0]);
            if (split.length <= 1) {
                return "00000000";
            }
            String[] split2 = split[1].split(((String[]) hashMap.get(str))[1]);
            return Integer.parseInt(split2[0]) > 0 ? split2[0] : "00000000";
        } catch (IOException e) {
            e.printStackTrace();
            return "00000000";
        } catch (NumberFormatException unused) {
            HeimdAllFsService$$ExternalSyntheticOutline0.m(
                    "Policy version is wrong : ", str2, "ASKSManager");
            return "00000000";
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:87:0x0123 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getAdvancedHash(java.lang.String r11) {
        /*
            Method dump skipped, instructions count: 300
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.asks.ASKSManagerService.getAdvancedHash(java.lang.String):java.lang.String");
    }

    /* JADX WARN: Not initialized variable reg: 3, insn: 0x002a: MOVE (r0 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:34:0x002a */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0058 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] getApkFileHashBytes(java.lang.String r5) {
        /*
            r0 = 0
            java.lang.String r1 = "SHA-256"
            java.security.MessageDigest r1 = java.security.MessageDigest.getInstance(r1)     // Catch: java.security.NoSuchAlgorithmException -> L5d
            java.io.File r2 = new java.io.File
            r2.<init>(r5)
            if (r1 == 0) goto L5c
            boolean r5 = r2.exists()
            if (r5 == 0) goto L5c
            r5 = 4096(0x1000, float:5.74E-42)
            byte[] r5 = new byte[r5]     // Catch: java.lang.Throwable -> L36 java.io.IOException -> L38
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L36 java.io.IOException -> L38
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L36 java.io.IOException -> L38
        L1d:
            int r2 = r3.read(r5)     // Catch: java.lang.Throwable -> L29 java.io.IOException -> L2c
            r4 = -1
            if (r2 == r4) goto L2e
            r4 = 0
            r1.update(r5, r4, r2)     // Catch: java.lang.Throwable -> L29 java.io.IOException -> L2c
            goto L1d
        L29:
            r5 = move-exception
            r0 = r3
            goto L56
        L2c:
            r5 = move-exception
            goto L3a
        L2e:
            byte[] r0 = r1.digest()     // Catch: java.lang.Throwable -> L29 java.io.IOException -> L2c
            r3.close()     // Catch: java.io.IOException -> L5c
            goto L5c
        L36:
            r5 = move-exception
            goto L56
        L38:
            r5 = move-exception
            r3 = r0
        L3a:
            java.lang.String r1 = "AASA_ASKSManager"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L29
            r2.<init>()     // Catch: java.lang.Throwable -> L29
            java.lang.String r4 = " ERROR: getApkFileHash:"
            r2.append(r4)     // Catch: java.lang.Throwable -> L29
            r2.append(r5)     // Catch: java.lang.Throwable -> L29
            java.lang.String r5 = r2.toString()     // Catch: java.lang.Throwable -> L29
            android.util.Slog.e(r1, r5)     // Catch: java.lang.Throwable -> L29
            if (r3 == 0) goto L55
            r3.close()     // Catch: java.io.IOException -> L55
        L55:
            return r0
        L56:
            if (r0 == 0) goto L5b
            r0.close()     // Catch: java.io.IOException -> L5b
        L5b:
            throw r5
        L5c:
            return r0
        L5d:
            r5 = move-exception
            r5.printStackTrace()
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.asks.ASKSManagerService.getApkFileHashBytes(java.lang.String):byte[]");
    }

    public static void getDataByDevice(String str, HashMap hashMap) {
        String str2;
        BufferedReader bufferedReader = null;
        try {
            try {
                File file = new File(str);
                if (!file.exists()) {
                    Slog.i("APKFromUnknownSource", str.concat(" does not exist."));
                } else if (file.length() < 10000) {
                    FileReader fileReader = new FileReader(str);
                    Slog.i("APKFromUnknownSource", "size = " + file.length() + " :" + str);
                    BufferedReader bufferedReader2 = new BufferedReader(fileReader);
                    while (true) {
                        try {
                            String readLine = bufferedReader2.readLine();
                            if (readLine == null) {
                                break;
                            }
                            String[] split = readLine.split(",");
                            if (split != null) {
                                if (split.length != 1) {
                                    if (split.length != 2) {
                                        hashMap.clear();
                                        break;
                                    }
                                    str2 = split[1];
                                } else {
                                    str2 = "noCert";
                                }
                                if (!hashMap.containsKey(split[0])) {
                                    ArrayList arrayList = new ArrayList();
                                    arrayList.add(str2);
                                    hashMap.put(split[0], arrayList);
                                }
                            }
                        } catch (IOException e) {
                            e = e;
                            bufferedReader = bufferedReader2;
                            e.printStackTrace();
                            if (bufferedReader == null) {
                                return;
                            }
                            bufferedReader.close();
                        } catch (Throwable th) {
                            th = th;
                            bufferedReader = bufferedReader2;
                            if (bufferedReader != null) {
                                try {
                                    bufferedReader.close();
                                } catch (IOException unused) {
                                }
                            }
                            throw th;
                        }
                    }
                    fileReader.close();
                    bufferedReader = bufferedReader2;
                } else if (file.delete()) {
                    Slog.i("ASKSManager", "BigSize File is deleted");
                } else {
                    Slog.e("ASKSManager", "BigSize file is not deleted");
                }
                if (bufferedReader == null) {
                    return;
                }
            } catch (IOException e2) {
                e = e2;
            }
            try {
                bufferedReader.close();
            } catch (IOException unused2) {
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static ArrayList getInstalledAppsDataFromXML(String str, HashMap hashMap) {
        FileReader fileReader;
        HashMap hashMap2 = new HashMap();
        ArrayList arrayList = new ArrayList();
        File file = new File("/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_INSTALLED_INFO_LIST.xml");
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            if (!file.getParentFile().mkdir()) {
                Slog.e("PackageInformation", "failed to created folder related INFOLIST");
                return null;
            }
            file.getParentFile().setReadable(true, false);
        }
        if (!file.exists()) {
            return null;
        }
        try {
            fileReader = new FileReader(file, Charset.defaultCharset());
            try {
                XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
                newPullParser.setInput(fileReader);
                for (int eventType = newPullParser.getEventType();
                        eventType != 1;
                        eventType = newPullParser.next()) {
                    if (eventType == 2) {
                        String name = newPullParser.getName();
                        if (str == null) {
                            InstalledAppInfo installedAppInfo = new InstalledAppInfo();
                            installedAppInfo.set(
                                    newPullParser.getAttributeValue(null, "name"),
                                    newPullParser.getAttributeValue(null, "signature"),
                                    newPullParser.getAttributeValue(null, "execute"),
                                    newPullParser.getAttributeValue(null, "overlay"),
                                    newPullParser.getAttributeValue(null, "requestInstallerZip"),
                                    newPullParser.getAttributeValue(null, "initType"),
                                    newPullParser.getAttributeValue(null, "accessibility"),
                                    newPullParser.getAttributeValue(null, "hasReqInstallPEM"),
                                    newPullParser.getAttributeValue(null, "installAuthority"),
                                    newPullParser.getAttributeValue(null, "installAuthorityDate"));
                            installedAppInfo.initPkg =
                                    newPullParser.getAttributeValue(null, "initPkg");
                            hashMap.put(
                                    newPullParser.getAttributeValue(null, "name"),
                                    installedAppInfo);
                        } else if (name != null && name.equals("package")) {
                            checkAttributeValue(newPullParser, hashMap2);
                        }
                    }
                }
                fileReader.close();
                return hashMap2.containsKey(str) ? (ArrayList) hashMap2.get(str) : arrayList;
            } catch (IOException | XmlPullParserException e) {
                e = e;
                e.printStackTrace();
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                return null;
            }
        } catch (IOException | XmlPullParserException e3) {
            e = e3;
            fileReader = null;
        }
    }

    public static String getInstalledPolicy(String str, String str2) {
        String str3;
        ArrayList installedAppsDataFromXML = getInstalledAppsDataFromXML("installAuthority", null);
        if (installedAppsDataFromXML == null) {
            return "none";
        }
        Iterator it = installedAppsDataFromXML.iterator();
        while (it.hasNext()) {
            String[] split = ((String) it.next()).split(",");
            if (split != null
                    && split.length == 3
                    && (str.equals(split[0]) || (str2 != null && str2.equals(split[0])))) {
                String str4 = split[1];
                if (str4 == null || (str3 = split[2]) == null) {
                    return "none";
                }
                if (!str4.equals("none") && !str3.equals("none")) {
                    try {
                        String str5 = "";
                        if (Integer.parseInt(str3) >= Integer.parseInt(getTrustedToday())) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("[Token] exist Parents policy ");
                            if (isDevDevice()) {
                                str5 = " installedTokenPolicy :" + str4;
                            }
                            sb.append(str5);
                            Slog.i("PackageInformation", sb.toString());
                            return str4;
                        }
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("[Token] exist Parents policy : but...expiration date");
                        if (isDevDevice()) {
                            str5 = " installedTokenPolicyDate :" + str3;
                        }
                        sb2.append(str5);
                        Slog.i("PackageInformation", sb2.toString());
                        return "none";
                    } catch (NumberFormatException e) {
                        Slog.e("PackageInformation", e.getMessage());
                    }
                }
                return str4;
            }
        }
        return "none";
    }

    public static String getSHA256(String str) {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(str.getBytes("ISO-8859-1"), 0, str.length());
        return convertToHex(messageDigest.digest());
    }

    public static String getSHA256ForPkgName(String str) {
        try {
            return getSHA256(str + "AASAASKS");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException unused) {
            return "";
        }
    }

    public static String getSigHash(Signature signature) {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(signature.toByteArray());
        return convertToHex(messageDigest.digest());
    }

    public static ArrayList getTargetNodeName(String str) {
        ArrayList arrayList = new ArrayList();
        File file = new File("/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_TARGETDEVICE.xml");
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            if (!file.getParentFile().mkdir()) {
                Slog.e("PackageInformation", "failed to created folder related TAGETDEVICE");
                return null;
            }
            file.getParentFile().setReadable(true, false);
        }
        if (!file.exists()) {
            return null;
        }
        try {
            FileReader fileReader = new FileReader(file, Charset.defaultCharset());
            try {
                XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
                newPullParser.setInput(fileReader);
                for (int eventType = newPullParser.getEventType();
                        eventType != 1;
                        eventType = newPullParser.next()) {
                    if (eventType == 2) {
                        String name = newPullParser.getName();
                        if (!name.equals("DEVICE")) {
                            if (!name.equals("LIST")) {
                                if (!name.equals("TARGET")) {
                                    if (!name.equals("CERTTARGET")) {
                                        if (!name.equals("ZIPTARGET")) {
                                            if (!name.equals("ZIPCERTTARGET")) {
                                                String attributeValue =
                                                        newPullParser.getAttributeValue(
                                                                null, "value");
                                                if (attributeValue != null) {
                                                    if (!attributeValue.equals(str)
                                                            && !"ALL".equals(attributeValue)) {}
                                                    if (!arrayList.contains(name)) {
                                                        arrayList.add(name);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                fileReader.close();
                return arrayList;
            } catch (IOException e) {
                try {
                    fileReader.close();
                } catch (IOException unused) {
                }
                e.printStackTrace();
                return null;
            } catch (XmlPullParserException e2) {
                try {
                    fileReader.close();
                } catch (IOException unused2) {
                }
                e2.printStackTrace();
                return null;
            }
        } catch (FileNotFoundException e3) {
            e3.printStackTrace();
            return null;
        } catch (IOException e4) {
            e4.printStackTrace();
            return null;
        }
    }

    public static byte[] getTokenContents(byte[] bArr) {
        int i;
        byte[] bArr2 = new byte[7];
        int i2 = 512;
        int i3 = 0;
        while (true) {
            byte b = bArr[i2];
            if (b == 44) {
                byte[] bArr3 = new byte[i3];
                System.arraycopy(bArr2, 0, bArr3, 0, i3);
                try {
                    i = Integer.parseInt(new String(bArr3));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    i = 0;
                }
                byte[] bArr4 = new byte[i];
                System.arraycopy(bArr, i3 + 513, bArr4, 0, i);
                return bArr4;
            }
            if (i3 >= 5) {
                return "22".getBytes();
            }
            bArr2[i3] = b;
            i2++;
            i3++;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x003d, code lost:

       if (r3 == null) goto L26;
    */
    /* JADX WARN: Not initialized variable reg: 3, insn: 0x002a: MOVE (r1 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:37:0x002a */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0049 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String[] getTrustedFile() {
        /*
            java.lang.String r0 = "AASA_ASKSManager_SECURETIME"
            java.lang.String r1 = "getTrustedFile : "
            android.util.Slog.d(r0, r1)
            r0 = 3
            r1 = 0
            java.io.FileReader r2 = new java.io.FileReader     // Catch: java.lang.Throwable -> L35 java.io.IOException -> L37
            java.lang.String r3 = "/data/system/.aasa/trustedTime"
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L35 java.io.IOException -> L37
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch: java.lang.Throwable -> L35 java.io.IOException -> L37
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L35 java.io.IOException -> L37
            r4 = r1
        L17:
            java.lang.String r5 = r3.readLine()     // Catch: java.lang.Throwable -> L29 java.io.IOException -> L2c
            if (r5 == 0) goto L2e
            java.lang.String r6 = ","
            java.lang.String[] r4 = r5.split(r6)     // Catch: java.lang.Throwable -> L29 java.io.IOException -> L2c
            if (r4 == 0) goto L17
            int r5 = r4.length     // Catch: java.lang.Throwable -> L29 java.io.IOException -> L2c
            if (r5 != r0) goto L17
            goto L2e
        L29:
            r0 = move-exception
            r1 = r3
            goto L47
        L2c:
            r2 = move-exception
            goto L3a
        L2e:
            r2.close()     // Catch: java.lang.Throwable -> L29 java.io.IOException -> L2c
        L31:
            r3.close()     // Catch: java.io.IOException -> L40
            goto L40
        L35:
            r0 = move-exception
            goto L47
        L37:
            r2 = move-exception
            r3 = r1
            r4 = r3
        L3a:
            r2.printStackTrace()     // Catch: java.lang.Throwable -> L29
            if (r3 == 0) goto L40
            goto L31
        L40:
            if (r4 == 0) goto L46
            int r2 = r4.length
            if (r2 != r0) goto L46
            return r4
        L46:
            return r1
        L47:
            if (r1 == 0) goto L4c
            r1.close()     // Catch: java.io.IOException -> L4c
        L4c:
            throw r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.asks.ASKSManagerService.getTrustedFile():java.lang.String[]");
    }

    public static String getTrustedToday() {
        String str = SystemProperties.get("security.ASKS.time_value", "00000000");
        if (str != null && !str.equals("00000000")) {
            return str;
        }
        if (!hasTrustedTime()) {
            return convertMillsToString(System.currentTimeMillis());
        }
        String[] trustedFile = getTrustedFile();
        if (trustedFile == null || trustedFile.length != 3) {
            return convertMillsToString(System.currentTimeMillis());
        }
        String convertMillsToString =
                convertMillsToString(
                        SystemClock.elapsedRealtime()
                                + (Long.parseLong(trustedFile[1])
                                        - Long.parseLong(trustedFile[2])));
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                "getElapsedToday : ", convertMillsToString, "AASA_ASKSManager_SECURETIME");
        return convertMillsToString;
    }

    /* JADX WARN: Removed duplicated region for block: B:120:0x0280 A[Catch: IOException -> 0x0214, XmlPullParserException -> 0x0217, TryCatch #8 {IOException -> 0x0214, XmlPullParserException -> 0x0217, blocks: (B:63:0x04e7, B:100:0x01f7, B:102:0x01fd, B:104:0x0206, B:106:0x020c, B:109:0x0224, B:110:0x0226, B:114:0x0241, B:116:0x0261, B:120:0x0280, B:124:0x029f, B:126:0x02a7, B:128:0x02ae, B:130:0x02b4, B:133:0x02ce, B:135:0x02de, B:137:0x02e7, B:139:0x02f2, B:142:0x0306, B:145:0x0315, B:152:0x0361, B:154:0x0367, B:156:0x038f, B:160:0x03ae, B:164:0x03cb, B:168:0x03e5, B:198:0x03eb, B:171:0x0409, B:173:0x0413, B:180:0x043d, B:182:0x0446, B:184:0x0451, B:187:0x045d, B:192:0x049b, B:202:0x03f2, B:203:0x03d6, B:207:0x03b5, B:210:0x03be, B:214:0x0398, B:217:0x03a1, B:226:0x0329, B:231:0x02ba, B:232:0x02bf, B:234:0x0289, B:237:0x0292, B:241:0x026a, B:244:0x0273, B:249:0x021a, B:258:0x04fe), top: B:62:0x04e7, inners: #2, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:124:0x029f A[Catch: IOException -> 0x0214, XmlPullParserException -> 0x0217, TryCatch #8 {IOException -> 0x0214, XmlPullParserException -> 0x0217, blocks: (B:63:0x04e7, B:100:0x01f7, B:102:0x01fd, B:104:0x0206, B:106:0x020c, B:109:0x0224, B:110:0x0226, B:114:0x0241, B:116:0x0261, B:120:0x0280, B:124:0x029f, B:126:0x02a7, B:128:0x02ae, B:130:0x02b4, B:133:0x02ce, B:135:0x02de, B:137:0x02e7, B:139:0x02f2, B:142:0x0306, B:145:0x0315, B:152:0x0361, B:154:0x0367, B:156:0x038f, B:160:0x03ae, B:164:0x03cb, B:168:0x03e5, B:198:0x03eb, B:171:0x0409, B:173:0x0413, B:180:0x043d, B:182:0x0446, B:184:0x0451, B:187:0x045d, B:192:0x049b, B:202:0x03f2, B:203:0x03d6, B:207:0x03b5, B:210:0x03be, B:214:0x0398, B:217:0x03a1, B:226:0x0329, B:231:0x02ba, B:232:0x02bf, B:234:0x0289, B:237:0x0292, B:241:0x026a, B:244:0x0273, B:249:0x021a, B:258:0x04fe), top: B:62:0x04e7, inners: #2, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:133:0x02ce A[Catch: IOException -> 0x0214, XmlPullParserException -> 0x0217, TryCatch #8 {IOException -> 0x0214, XmlPullParserException -> 0x0217, blocks: (B:63:0x04e7, B:100:0x01f7, B:102:0x01fd, B:104:0x0206, B:106:0x020c, B:109:0x0224, B:110:0x0226, B:114:0x0241, B:116:0x0261, B:120:0x0280, B:124:0x029f, B:126:0x02a7, B:128:0x02ae, B:130:0x02b4, B:133:0x02ce, B:135:0x02de, B:137:0x02e7, B:139:0x02f2, B:142:0x0306, B:145:0x0315, B:152:0x0361, B:154:0x0367, B:156:0x038f, B:160:0x03ae, B:164:0x03cb, B:168:0x03e5, B:198:0x03eb, B:171:0x0409, B:173:0x0413, B:180:0x043d, B:182:0x0446, B:184:0x0451, B:187:0x045d, B:192:0x049b, B:202:0x03f2, B:203:0x03d6, B:207:0x03b5, B:210:0x03be, B:214:0x0398, B:217:0x03a1, B:226:0x0329, B:231:0x02ba, B:232:0x02bf, B:234:0x0289, B:237:0x0292, B:241:0x026a, B:244:0x0273, B:249:0x021a, B:258:0x04fe), top: B:62:0x04e7, inners: #2, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:160:0x03ae A[Catch: IOException -> 0x0214, XmlPullParserException -> 0x0217, TryCatch #8 {IOException -> 0x0214, XmlPullParserException -> 0x0217, blocks: (B:63:0x04e7, B:100:0x01f7, B:102:0x01fd, B:104:0x0206, B:106:0x020c, B:109:0x0224, B:110:0x0226, B:114:0x0241, B:116:0x0261, B:120:0x0280, B:124:0x029f, B:126:0x02a7, B:128:0x02ae, B:130:0x02b4, B:133:0x02ce, B:135:0x02de, B:137:0x02e7, B:139:0x02f2, B:142:0x0306, B:145:0x0315, B:152:0x0361, B:154:0x0367, B:156:0x038f, B:160:0x03ae, B:164:0x03cb, B:168:0x03e5, B:198:0x03eb, B:171:0x0409, B:173:0x0413, B:180:0x043d, B:182:0x0446, B:184:0x0451, B:187:0x045d, B:192:0x049b, B:202:0x03f2, B:203:0x03d6, B:207:0x03b5, B:210:0x03be, B:214:0x0398, B:217:0x03a1, B:226:0x0329, B:231:0x02ba, B:232:0x02bf, B:234:0x0289, B:237:0x0292, B:241:0x026a, B:244:0x0273, B:249:0x021a, B:258:0x04fe), top: B:62:0x04e7, inners: #2, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:164:0x03cb A[Catch: IOException -> 0x0214, XmlPullParserException -> 0x0217, TryCatch #8 {IOException -> 0x0214, XmlPullParserException -> 0x0217, blocks: (B:63:0x04e7, B:100:0x01f7, B:102:0x01fd, B:104:0x0206, B:106:0x020c, B:109:0x0224, B:110:0x0226, B:114:0x0241, B:116:0x0261, B:120:0x0280, B:124:0x029f, B:126:0x02a7, B:128:0x02ae, B:130:0x02b4, B:133:0x02ce, B:135:0x02de, B:137:0x02e7, B:139:0x02f2, B:142:0x0306, B:145:0x0315, B:152:0x0361, B:154:0x0367, B:156:0x038f, B:160:0x03ae, B:164:0x03cb, B:168:0x03e5, B:198:0x03eb, B:171:0x0409, B:173:0x0413, B:180:0x043d, B:182:0x0446, B:184:0x0451, B:187:0x045d, B:192:0x049b, B:202:0x03f2, B:203:0x03d6, B:207:0x03b5, B:210:0x03be, B:214:0x0398, B:217:0x03a1, B:226:0x0329, B:231:0x02ba, B:232:0x02bf, B:234:0x0289, B:237:0x0292, B:241:0x026a, B:244:0x0273, B:249:0x021a, B:258:0x04fe), top: B:62:0x04e7, inners: #2, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:168:0x03e5 A[Catch: IOException -> 0x0214, XmlPullParserException -> 0x0217, TRY_LEAVE, TryCatch #8 {IOException -> 0x0214, XmlPullParserException -> 0x0217, blocks: (B:63:0x04e7, B:100:0x01f7, B:102:0x01fd, B:104:0x0206, B:106:0x020c, B:109:0x0224, B:110:0x0226, B:114:0x0241, B:116:0x0261, B:120:0x0280, B:124:0x029f, B:126:0x02a7, B:128:0x02ae, B:130:0x02b4, B:133:0x02ce, B:135:0x02de, B:137:0x02e7, B:139:0x02f2, B:142:0x0306, B:145:0x0315, B:152:0x0361, B:154:0x0367, B:156:0x038f, B:160:0x03ae, B:164:0x03cb, B:168:0x03e5, B:198:0x03eb, B:171:0x0409, B:173:0x0413, B:180:0x043d, B:182:0x0446, B:184:0x0451, B:187:0x045d, B:192:0x049b, B:202:0x03f2, B:203:0x03d6, B:207:0x03b5, B:210:0x03be, B:214:0x0398, B:217:0x03a1, B:226:0x0329, B:231:0x02ba, B:232:0x02bf, B:234:0x0289, B:237:0x0292, B:241:0x026a, B:244:0x0273, B:249:0x021a, B:258:0x04fe), top: B:62:0x04e7, inners: #2, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:170:0x0407  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0413 A[Catch: IOException -> 0x0214, XmlPullParserException -> 0x0217, TryCatch #8 {IOException -> 0x0214, XmlPullParserException -> 0x0217, blocks: (B:63:0x04e7, B:100:0x01f7, B:102:0x01fd, B:104:0x0206, B:106:0x020c, B:109:0x0224, B:110:0x0226, B:114:0x0241, B:116:0x0261, B:120:0x0280, B:124:0x029f, B:126:0x02a7, B:128:0x02ae, B:130:0x02b4, B:133:0x02ce, B:135:0x02de, B:137:0x02e7, B:139:0x02f2, B:142:0x0306, B:145:0x0315, B:152:0x0361, B:154:0x0367, B:156:0x038f, B:160:0x03ae, B:164:0x03cb, B:168:0x03e5, B:198:0x03eb, B:171:0x0409, B:173:0x0413, B:180:0x043d, B:182:0x0446, B:184:0x0451, B:187:0x045d, B:192:0x049b, B:202:0x03f2, B:203:0x03d6, B:207:0x03b5, B:210:0x03be, B:214:0x0398, B:217:0x03a1, B:226:0x0329, B:231:0x02ba, B:232:0x02bf, B:234:0x0289, B:237:0x0292, B:241:0x026a, B:244:0x0273, B:249:0x021a, B:258:0x04fe), top: B:62:0x04e7, inners: #2, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:179:0x043b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:196:0x0421  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x03eb A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void getUnknownAppsDataFromXML(
            int r52, java.util.ArrayList r53, java.util.HashMap r54, boolean r55) {
        /*
            Method dump skipped, instructions count: 1310
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.asks.ASKSManagerService.getUnknownAppsDataFromXML(int,"
                    + " java.util.ArrayList, java.util.HashMap, boolean):void");
    }

    public static boolean hasTrustedTime() {
        return BatteryService$$ExternalSyntheticOutline0.m45m("/data/system/.aasa/trustedTime");
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x00c6, code lost:

       if (r5 == null) goto L48;
    */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00b0, code lost:

       r5.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00ae, code lost:

       if (r5 == null) goto L48;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isASKSToken(
            com.android.server.asks.ASKSManagerService.ASKSSession r12, java.lang.String r13) {
        /*
            java.lang.String r0 = " ERROR: AASA_ASKSIsToken "
            java.lang.String r1 = "AASA_ASKSManager"
            java.lang.String r2 = "META-INF"
            java.lang.String r3 = "SEC-INF"
            r4 = 0
            r5 = 0
            android.util.jar.StrictJarFile r6 = new android.util.jar.StrictJarFile     // Catch: java.lang.Throwable -> L96 java.lang.SecurityException -> L98 java.io.IOException -> L9a
            r7 = 1
            r6.<init>(r13, r4, r7)     // Catch: java.lang.Throwable -> L96 java.lang.SecurityException -> L98 java.io.IOException -> L9a
            java.util.Iterator r5 = r6.iterator()     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
        L14:
            boolean r8 = r5.hasNext()     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            if (r8 == 0) goto L92
            java.lang.Object r8 = r5.next()     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            java.util.zip.ZipEntry r8 = (java.util.zip.ZipEntry) r8     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            java.lang.String r8 = r8.getName()     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            boolean r9 = r8.startsWith(r3)     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            java.lang.String r10 = "buildConfirm.crt"
            java.lang.String r11 = "buildinfo"
            if (r9 == 0) goto L5f
            boolean r9 = r8.contains(r11)     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            if (r9 == 0) goto L5f
            r12.mCodePath = r13     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            r12.mTokenName = r8     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            r13.<init>()     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            r13.append(r3)     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            java.lang.String r2 = java.io.File.separator     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            r13.append(r2)     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            r13.append(r10)     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            java.lang.String r13 = r13.toString()     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            r12.mCertName = r13     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
        L50:
            r4 = r7
            goto L92
        L52:
            r5 = r6
            goto Lca
        L55:
            r5 = r6
            goto L9c
        L57:
            r5 = r6
            goto Lb4
        L59:
            r12 = move-exception
            goto L52
        L5b:
            r12 = move-exception
            goto L55
        L5d:
            r12 = move-exception
            goto L57
        L5f:
            boolean r9 = r8.startsWith(r2)     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            if (r9 == 0) goto L14
            boolean r9 = r8.contains(r3)     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            if (r9 == 0) goto L14
            boolean r9 = r8.contains(r11)     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            if (r9 == 0) goto L14
            r12.mCodePath = r13     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            r12.mTokenName = r8     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            r13.<init>()     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            r13.append(r2)     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            java.lang.String r2 = java.io.File.separator     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            r13.append(r2)     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            r13.append(r3)     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            r13.append(r2)     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            r13.append(r10)     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            java.lang.String r13 = r13.toString()     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            r12.mCertName = r13     // Catch: java.lang.Throwable -> L59 java.lang.SecurityException -> L5b java.io.IOException -> L5d
            goto L50
        L92:
            r6.close()
            goto Lc9
        L96:
            r12 = move-exception
            goto Lca
        L98:
            r12 = move-exception
            goto L9c
        L9a:
            r12 = move-exception
            goto Lb4
        L9c:
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L96
            r13.<init>()     // Catch: java.lang.Throwable -> L96
            r13.append(r0)     // Catch: java.lang.Throwable -> L96
            r13.append(r12)     // Catch: java.lang.Throwable -> L96
            java.lang.String r12 = r13.toString()     // Catch: java.lang.Throwable -> L96
            android.util.Slog.i(r1, r12)     // Catch: java.lang.Throwable -> L96
            if (r5 == 0) goto Lc9
        Lb0:
            r5.close()
            goto Lc9
        Lb4:
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L96
            r13.<init>()     // Catch: java.lang.Throwable -> L96
            r13.append(r0)     // Catch: java.lang.Throwable -> L96
            r13.append(r12)     // Catch: java.lang.Throwable -> L96
            java.lang.String r12 = r13.toString()     // Catch: java.lang.Throwable -> L96
            android.util.Slog.i(r1, r12)     // Catch: java.lang.Throwable -> L96
            if (r5 == 0) goto Lc9
            goto Lb0
        Lc9:
            return r4
        Lca:
            if (r5 == 0) goto Lcf
            r5.close()
        Lcf:
            throw r12
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.asks.ASKSManagerService.isASKSToken(com.android.server.asks.ASKSManagerService$ASKSSession,"
                    + " java.lang.String):boolean");
    }

    public static boolean isAutoTimeEnabled(Context context) {
        Slog.d("AASA_ASKSManager_SECURETIME", "isAutoTimeEnabled : ");
        boolean z = false;
        if (context == null) {
            Slog.d("AASA_ASKSManager_SECURETIME", "context is null. ");
            return false;
        }
        try {
            if (Settings.Global.getInt(context.getContentResolver(), "auto_time") == 1) {
                Slog.d("AASA_ASKSManager_SECURETIME", "isAutoTimeEnabled : ON");
                z = true;
            } else {
                Slog.d("AASA_ASKSManager_SECURETIME", "isAutoTimeEnabled : OFF");
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return z;
    }

    public static boolean isDevDevice() {
        return "0x1".equals(SystemProperties.get("ro.boot.em.status"));
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    public static boolean isKorProject() {
        Slog.i(
                "PackageInformation",
                "CountryISO : " + SemCscFeature.getInstance().getString("CountryISO"));
        return "KR".equalsIgnoreCase(SemCscFeature.getInstance().getString("CountryISO"));
    }

    public static int isSignatureMatched(String str, Signature[] signatureArr) {
        int i;
        String[] strArr = {
            "308204d4308203bca003020102020900d20995a79c0daad6300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303632323132323531325a170d3338313130373132323531325a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100c986384a3e1f2fb206670e78ef232215c0d26f45a22728db99a44da11c35ac33a71fe071c4a2d6825a9b4c88b333ed96f3c5e6c666d60f3ee94c490885abcf8dc660f707aabc77ead3e2d0d8aee8108c15cd260f2e85042c28d2f292daa3c6da0c7bf2391db7841aade8fdf0c9d0defcf77124e6d2de0a9e0d2da746c3670e4ffcdc85b701bb4744861b96ff7311da3603c5a10336e55ffa34b4353eedc85f51015e1518c67e309e39f87639ff178107f109cd18411a6077f26964b6e63f8a70b9619db04306a323c1a1d23af867e19f14f570ffe573d0e3a0c2b30632aaec3173380994be1e341e3a90bd2e4b615481f46db39ea83816448ec35feb1735c1f3020103a382010b30820107301d0603551d0e04160414932c3af70b627a0c7610b5a0e7427d6cfaea3f1e3081d70603551d230481cf3081cc8014932c3af70b627a0c7610b5a0e7427d6cfaea3f1ea181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900d20995a79c0daad6300c0603551d13040530030101ff300d06092a864886f70d01010505000382010100329601fe40e036a4a86cc5d49dd8c1b5415998e72637538b0d430369ac51530f63aace8c019a1a66616a2f1bb2c5fabd6f313261f380e3471623f053d9e3c53f5fd6d1965d7b000e4dc244c1b27e2fe9a323ff077f52c4675e86247aa801187137e30c9bbf01c567a4299db4bf0b25b7d7107a7b81ee102f72ff47950164e26752e114c42f8b9d2a42e7308897ec640ea1924ed13abbe9d120912b62f4926493a86db94c0b46f44c6161d58c2f648164890c512dfb28d42c855bf470dbee2dab6960cad04e81f71525ded46cdd0f359f99c460db9f007d96ce83b4b218ac2d82c48f12608d469733f05a3375594669ccbf8a495544d6c5701e9369c08c810158",
            "308204d4308203bca003020102020900e5eff0a8f66d92b3300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303632323132323531335a170d3338313130373132323531335a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100e9f1edb42423201dce62e68f2159ed8ea766b43a43d348754841b72e9678ce6b03d06d31532d88f2ef2d5ba39a028de0857983cd321f5b7786c2d3699df4c0b40c8d856f147c5dc54b9d1d671d1a51b5c5364da36fc5b0fe825afb513ec7a2db862c48a6046c43c3b71a1e275155f6c30aed2a68326ac327f60160d427cf55b617230907a84edbff21cc256c628a16f15d55d49138cdf2606504e1591196ed0bdc25b7cc4f67b33fb29ec4dbb13dbe6f3467a0871a49e620067755e6f095c3bd84f8b7d1e66a8c6d1e5150f7fa9d95475dc7061a321aaf9c686b09be23ccc59b35011c6823ffd5874d8fa2a1e5d276ee5aa381187e26112c7d5562703b36210b020103a382010b30820107301d0603551d0e041604145b115b23db35655f9f77f78756961006eebe3a9e3081d70603551d230481cf3081cc80145b115b23db35655f9f77f78756961006eebe3a9ea181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900e5eff0a8f66d92b3300c0603551d13040530030101ff300d06092a864886f70d0101050500038201010039c91877eb09c2c84445443673c77a1219c5c02e6552fa2fbad0d736bc5ab6ebaf0375e520fe9799403ecb71659b23afda1475a34ef4b2e1ffcba8d7ff385c21cb6482540bce3837e6234fd4f7dd576d7fcfe9cfa925509f772c494e1569fe44e6fcd4122e483c2caa2c639566dbcfe85ed7818d5431e73154ad453289fb56b607643919cf534fbeefbdc2009c7fcb5f9b1fa97490462363fa4bedc5e0b9d157e448e6d0e7cfa31f1a2faa9378d03c8d1163d3803bc69bf24ec77ce7d559abcaf8d345494abf0e3276f0ebd2aa08e4f4f6f5aaea4bc523d8cc8e2c9200ba551dd3d4e15d5921303ca9333f42f992ddb70c2958e776c12d7e3b7bd74222eb5c7a",
            "308204d4308203bca003020102020900f3a752a8cbb7ac6a300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303732373132323632335a170d3338313231323132323632335a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100bd20d27f5127981cf0855e43e64d8018b92401ff0b4b241eeb54c4fb0e84dcf94cf8da888e34c1c370bc437f77880819f3a9894019f05d5514bc3d20d17e968167d85990fa1a44b9e79aa1da9681dc8d2c39b98b3b257918748c6f5bb9126330d72fdc26065e717f1a5c27c8b075f1a8d7325f7eb2d57ee34d93d76a5c529d2e0789392793c68c8f5090c4d2d093190b3279943550e2f5c864118e84d6c6c6bc67815148db8752e4bf69a9ca729ca4704d966e8dd591506dfc9dd9c8c33bdc7bf58660df6be3b45753983a092c3a4ae899d1f2253017ba606a5b1dda2f5511fcf530ea43c7dc05ff1621d305f12a37148e72078aaf644dadc98f3b6789cb6655020103a382010b30820107301d0603551d0e041604142fa3167aab7de1f13b4edef062fa715c0609f0bf3081d70603551d230481cf3081cc80142fa3167aab7de1f13b4edef062fa715c0609f0bfa181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900f3a752a8cbb7ac6a300c0603551d13040530030101ff300d06092a864886f70d01010505000382010100498ed96cbc503fb1b72402dcb8ba364d8aa11dc5b9a7e191d200af4051272519b3099eba16e538044f086a1e36710abf2980efb437b6a9bebfab93417c068ea18cbfdeb8570fca73951684c674eb33c4240e236928ba1197d6b385c40454c3980f6f764131149dbba80756b7b18c5951a8630a6692fdb30227b431175f793a6e39479e8ad8b4b4beca6faabf9fc243b9be47447229524487f5f04cf6661ec818a3756221360bfeee3ccaec9a6dc67694b791a80957b28f11f15fd81eaeb361e4c9f907d3ceb4176f9947b513f8cd89d77044adae7c7f631f27a2e40a8d655a9c73515c796b17a39d0e9de675d62bf785c1e0d65a937c65aadacf788b2dfc14e2",
            "308204d4308203bca003020102020900b830e7f5ede090a8300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303632323132323531325a170d3338313130373132323531325a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d003082010802820101009a280ff8cebd5954fbac141d450be91a980a6597b379cb64a19bc4ab39aecb5f06fe2599d3767bb0c27e3e8ac3846cf0b80c09817f8d22be8a55418a068c6983958ffc233a99cd793bc468b0bda139b87ff1550e5ce184647214a1fa4fe2121a0ecdbb1cd33c644c06e7b70455ff097a4f8c51eca2ebefb4602b5d8bb6ed811ec959c1e99e8f353667703563c3c3277bbbd872fe7fa84bd8041efa98d32bb35c44d9c55aa8e766da065176722103fdb63677392c94bd20f5a5ac5c780046bc729a2eec3575a05ddb39836235c8c939f95493aa8f32dd7e7016392716219f0c5fe48874f283af0c217b4c08536b5df7bc302c9e2af08db61ecb49a198c7c4bd2b020103a382010b30820107301d0603551d0e041604144d2270829d5cf4a65bf55a756224bea659c2dfda3081d70603551d230481cf3081cc80144d2270829d5cf4a65bf55a756224bea659c2dfdaa181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900b830e7f5ede090a8300c0603551d13040530030101ff300d06092a864886f70d01010505000382010100751ea54edeb751de01436db8009352bee64209020fe40641ac09d0016c807fd89258aca374299520e30bc79e77a161c98ddb8ccfc9c8184969114e4478d1b1b374a97e52e07e056dd6b6de5b063c12203e55e284d1de58af2fc6e43c198857b87ac9a472633b8a1cd7e6ebc4e2d675b680d1844d86ab7569129d24e2bcf10cddb2e66c85c1335a3d6479749152058a27135440b795bf509d78009fbda18a6c0cb31b741f79a4ac189d44fd04f65887bb9d950cc2b6f43275e71900fba03b06a9ab9ecd58af0f8c2e0b3569197b043da0601563b0af26a0f52c4b7e834c7ccf5dec4d330d8fd0a049360cd3d9ef0bff09b9812c9ba406c8a6650688b0919a040b",
            "30820411308202f9a003020102020900fd222d6fc87acde0300d06092a864886f70d010105050030819e310b3009060355040613024b523113301106035504080c0a536f7574684b6f7265613112301006035504070c095375776f6e43697479311b3019060355040a0c1253616d73756e67436f72706f726174696f6e310c300a060355040b0c03444d433114301206035504030c0b53616d73756e67436572743125302306092a864886f70d01090116166d2e73656375726974794073616d73756e672e636f6d3020170d3133303132343035323231305a180f32313132313233313035323231305a30819e310b3009060355040613024b523113301106035504080c0a536f7574684b6f7265613112301006035504070c095375776f6e43697479311b3019060355040a0c1253616d73756e67436f72706f726174696f6e310c300a060355040b0c03444d433114301206035504030c0b53616d73756e67436572743125302306092a864886f70d01090116166d2e73656375726974794073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100a2c51f56a1c8bf64ada0af152ced2344ac070b447efc85f1b69ce90fbc2b7a71257240c215eedbf7445c474fe34d62bc3035d79ba110859118f1200ecc9ae48b56400e187591272d59734e456d9dfd5a1f3227a30b9448bda84c2901b501295445e204ddb6f9f9e36b2560998f1764e446176fe5d83987220f8ed15106dc7c8ecb6798de45f5fbae54efe2b35a379631f545f84c98243aa4d92ef339330f954ad32e4e97aff69cbf68928484b03a8fa8eafdc8ff2a9801f249302d467b05f99a1680e4fb5b11624d5e53d67f09e86b82dd7305e3e483b12e3720fcccc2bc8857f13b6e1d60512074004f67d86241940eaba34afda2af3904b04913fa50f499f7020103a350304e301d0603551d0e04160414eef0f8211dccf6e442f3388889c9a3ea3ce0236c301f0603551d23041830168014eef0f8211dccf6e442f3388889c9a3ea3ce0236c300c0603551d13040530030101ff300d06092a864886f70d01010505000382010100395c7e7900c471e03fa9850905c6ab1edc5a8b7d43a16689d9bb1ec1a06513c4ea8f7471c6e474244174261cc151ae8d1a61019e0ed81fffee8afa1d01d85a32de796f4b46d0d5ddfcca7d1f90d523b54751f505a4e3b059569f24ba2564d72fbc4081533840f618c2993d935134d3c987605e032f6a12889af3190af1714a90f2a3476b8e0016ab45564bf10e611899babd86af33149ca6838b0a885c752ffe879f37997f262e819c62cf59caa794cfaaf8e3c462f5092a34264f0634316b13a67a644e104dc4070e8b6628a46f41da7e3c741f6edc21152f9f947dde6fe14b58f34e4d9e7abd103cb1ca9e09eb4fa5b553baa413329bd3919caca2d52e6d4b",
            "308204d4308203bca003020102020900b161f3869153be27300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303632323132323531325a170d3338313130373132323531325a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100e93d8694c493d50a6224a473d70ddcecd84a2f40ac48bb8206c83a09a94f2db98aaa34f9fcc343b91a87c61254c3a43b0caed03cd839a63037253ea77d949a284dd0b44ebfbabbc2cea838213609d9a5813e88863210ee62c0c0e415611aa7f938ad2bc627c147ac6cf558002028d2e38b1d31aba794867717ddcfcadbeeac6bd345a7bf6433e52cfc93a2157cb048298bd33bf30c143b777e3f074897bcf3b5b181316b678256fd3accf64e88160b0781efd90711ef4acae86848d87e1c10a1747e780c48bcb378a7b437e0405ec54ed7e22c4dbc39f8b03ab1d5eeb7cf4804455fbcab35afb775d79e8f4c4fa4da00b2ce48c991fd94020f7ad089fba13003020103a382010b30820107301d0603551d0e04160414b58d96dcf0127466098625e3ffb03a4f8d0654743081d70603551d230481cf3081cc8014b58d96dcf0127466098625e3ffb03a4f8d065474a181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900b161f3869153be27300c0603551d13040530030101ff300d06092a864886f70d0101050500038201010091327721aa614451a785e200349ce2f402049371943001266827c29abdf975dc7b3e6eaa02c41a07b445bb9de0bc43ce25c3c98928a94ff67ad81eec822cbd083ae686cd7126860655adb8d6a6228cf1f7a4a196699669c05b506efa1fca2cad1a150cabd01380e56bb1842651b4ff33bcb619b3c6e65a10cfd99350ea777c3866135523c1bece17f59fba76a2eb429453f7a2a9e6a6cc9e62e5f4b56706ba4c74cb86975aa865bead2209787b33261b9fa222a7117b1724ea3217ad680fd0408c5634278fbdfca0e32b16dc1a6cc245e931cbe84fc7cccdaa7778459e3003a082662ac6d84d485dd368e0eb4c2c9019420c82d1cd0fbd6fcc097353b059baea",
            "308204d4308203bca003020102020900e49d6da353f759af300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3039303630393135353934365a170d3336313032353135353934365a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d003082010802820101009ba004179d8018ab0fa3ab3c804899c2ecb6d66784225ae99936b71fd7f059969bb2076b8f2b9d7a5c20d0622e0a766de9602e3e8d60d9d335bdeab78100188f734b4678c7369c2e764913c8f43eede582827b8d1dc679c8fd0f0d0605fc6b87d331e2544bf11790b2a55c3a13463ec4cd35a931ad40dc687f116f1d6ba79eb63a01f96d107b1b166ddacb6d2fe8ac618217dabe6b69d4d9e692ab1970bb4346fd4860586e8387ef7682b07a428bc8036db143079bc37c8830e5a8c3d690f6b0cef5596ed80a9830f2e61c055894be1c2a7b3048602ef6df0e51073e06f0d55177f6aeb96b91b3b4c66b8b6e5b32bbe2afe46f45b0f48300a6ac9f9de1c500b7020103a382010b30820107301d0603551d0e041604149b6890fb4274c2e32d6c5daea2fac4dd0756529a3081d70603551d230481cf3081cc80149b6890fb4274c2e32d6c5daea2fac4dd0756529aa181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900e49d6da353f759af300c0603551d13040530030101ff300d06092a864886f70d010105050003820101001a76d67e729785f9f22015d9eb9d1998f2d8ce5bc147f65060d58f2f29004a592dd065b651e8d746cf050f3389b1632970d1334e9bce20b43a77a18b6226be0da0a4ab4420dd734dcdd0e049c4f07cf45f3faee8ac90332c14b1f7c4e4f55866a8e3aa71ad1814b5c591e07085dadbe15544ef9bc9591b2c75b373ca9214f8a49acd18ccf061b484c3cd1448bb2af149694d58a53d4c6878b8e06c12e214e2847117ef95348eca3acaa3fffecd7924cb1dd67251eaee14b01870cae92a4238cecac4cda5ba2a2640055303e98e62121a9e49ac0dfcde32b28606f3fc613709fe5ab8aefea4ed53a310c4c9dac7f90242d55697b5690ade195f5253da947f2eaa",
            "3082041e30820306a003020102020601670c2687f3300d06092a864886f70d01010505003081c4310b3009060355040613024b52311a30180603550408131152657075626c6963206f66204b6f726561311330110603550407130a5375776f6e204369747931253023060355040a131c53616d73756e6720456c656374726f6e69637320436f2e204c74642e31273025060355040b131e4d6f62696c6520436f6d6d756e69636174696f6e7320427573696e657373313430320603550403132b53616d73756e6720506c6174666f726d204b657920666f72206e6f6e2d47616c61787920646576696365733020170d3138313131333038313833315a180f32313138313131323135303030305a3081c4310b3009060355040613024b52311a30180603550408131152657075626c6963206f66204b6f726561311330110603550407130a5375776f6e204369747931253023060355040a131c53616d73756e6720456c656374726f6e69637320436f2e204c74642e31273025060355040b131e4d6f62696c6520436f6d6d756e69636174696f6e7320427573696e657373313430320603550403132b53616d73756e6720506c6174666f726d204b657920666f72206e6f6e2d47616c617879206465766963657330820122300d06092a864886f70d01010105000382010f003082010a0282010100f7049ae9aa6c64c27ebcf799f32bd9118c2870a54b4c9cd200aa33d2f332903b2a6430c608aac3038b65f644d7a82127ec187099beb680c71d3593d2522f94c894c018fb8fb08d3282bea8feafe902ce1a11da806d63366f514b97c6e286221537f758ece2bcb0b2278c4ae9217ff1c078ddb9401ce490f07557b50f6ddbbe43aacae52849a5e465010af4bdf13eae532771f6c8dc370fe715988d615e67dff7870bd4393490d17ab71584dbe7eb549df5b402fb7f0b4db5cc86e4a818601a183fe94a4a2bafd29367507f131490ac3e4e38c61f9f86c82cf2b583656b95139ce4e46c3ce04d9a9587316a47062ced72e186d546bcc39896491ad3242bb658b70203010001a3123010300e0603551d0f0101ff0404030205a0300d06092a864886f70d010105050003820101003347014ea4d8c43a387b28331fd3ba02a4aee7b9ecd340bce8e517c21ee6cc0e295d999ac5e68352ca59f30b82aa2c0736715cc20710338c34beacec99ba7a153cead3ec03640f6b764dcfa0fabfa4df5972b7abbecf532238ae1a1e2b404379f065c4ea8d148f60eb6f51c783b82b28bc97cc4486bfb08f9bba956323044b67d4fefb560c44fa18aeb397c0d87841295de021be9599396a0e734d2ec69dde9b70545db7aa106901437f07dc6d26f99d97b83380bad7b42536a47742935fe143684d8f31f07df44a7c274eaa33ba51863dbe57a1bc66cb988a97ed17f0f86e596c03a511391ec72dc4c79c039657d8b4b4ddd8a2910fa4872a3935d93a6947ad",
            "3082041830820300a003020102020601670c27ef2d300d06092a864886f70d01010505003081c1310b3009060355040613024b52311a30180603550408131152657075626c6963206f66204b6f726561311330110603550407130a5375776f6e204369747931253023060355040a131c53616d73756e6720456c656374726f6e69637320436f2e204c74642e31273025060355040b131e4d6f62696c6520436f6d6d756e69636174696f6e7320427573696e6573733131302f0603550403132853616d73756e67204d65646961204b657920666f72206e6f6e2d47616c61787920646576696365733020170d3138313131333038323030325a180f32313138313131323135303030305a3081c1310b3009060355040613024b52311a30180603550408131152657075626c6963206f66204b6f726561311330110603550407130a5375776f6e204369747931253023060355040a131c53616d73756e6720456c656374726f6e69637320436f2e204c74642e31273025060355040b131e4d6f62696c6520436f6d6d756e69636174696f6e7320427573696e6573733131302f0603550403132853616d73756e67204d65646961204b657920666f72206e6f6e2d47616c617879206465766963657330820122300d06092a864886f70d01010105000382010f003082010a0282010100bb60b4487c7c006073d54adf1e85ee3352f323e7ed751880b7ff99313fa6e4d94236cbc474aad528bfdc5a1a2ba33bdbd17996439ab3746b8bfd243852429c2c036a0d634e2ee2774ae92dede65430698e77368be3fbe640d842a445fe57118111e479ed018142157095b17dd146e689e049e5182931347113c38391c3cec258ca6b675f5bdb4158de58a64c0f37fb86e0f4517d879eb265fc44ee33aca2f1185b74f23e4a48c8a7eb8941055d374c485ca0ae5adb04607e9aedf43d3ae7e15f3e0ef6f05a922c3925fa11488371f94a3847f7cefbbf5fbcf18416f21171b946c6be5acbbe9e55bf610fa333b4d1e6d0c0278bba1817cd70aa1beefb73756fb90203010001a3123010300e0603551d0f0101ff0404030205a0300d06092a864886f70d0101050500038201010057f1b2b239f9551f4de9fb5afd88b0b7bc67d37bf9bfe8748583d35d14c9291355322e896bbb66d0d56c9708215fad9c40e9398620ea3b1e4641a5883a88472f852cc36afa88b695d5a7af408d5eb583bd4cec9452d0f901b6c38e1f97b55325b596e742fade940391b44d8f19352e8a543fe1c89ad600a8ba32373b1d84fb1b8d34e7541337254fdc9716b2adcfed7105f713ec4fc98c4eee56f7ffa2d2355e16161e2f276a075eda15cc2cdba93c6a49907ad01463cc752708051b8d87001028a6869187589425d3a8992cb9044a7c4d5e3e74a270f6bd1ebf57fd3afb82ab74399a40db820103ea361f7e87b172302ce14b29527bde67c01f4b71832c8665",
            "3082041a30820302a003020102020601670c278709300d06092a864886f70d01010505003081c2310b3009060355040613024b52311a30180603550408131152657075626c6963206f66204b6f726561311330110603550407130a5375776f6e204369747931253023060355040a131c53616d73756e6720456c656374726f6e69637320436f2e204c74642e31273025060355040b131e4d6f62696c6520436f6d6d756e69636174696f6e7320427573696e657373313230300603550403132953616d73756e6720536861726564204b657920666f72206e6f6e2d47616c61787920646576696365733020170d3138313131333038313933365a180f32313138313131323135303030305a3081c2310b3009060355040613024b52311a30180603550408131152657075626c6963206f66204b6f726561311330110603550407130a5375776f6e204369747931253023060355040a131c53616d73756e6720456c656374726f6e69637320436f2e204c74642e31273025060355040b131e4d6f62696c6520436f6d6d756e69636174696f6e7320427573696e657373313230300603550403132953616d73756e6720536861726564204b657920666f72206e6f6e2d47616c617879206465766963657330820122300d06092a864886f70d01010105000382010f003082010a02820101009fce256105db13cb1ec14f133d799cf889bf7c29cb8a1a8e8ba1d618a03e01b6705901e7fe2d012b3ad2cfdcad80a2718b4fb09f2d0ef0142cea5fd17afbddb4a1e7d2c99f2a1650ca17faedae9cbc5c13561e723b9ae120f55109aa992d57d2ba7e3c495620e5957c7c75c2ade6d03c5b204ceb460754ccdcd5791267f46283f37923ce3d828ee78a8702770a6356824086c956e403048059d8d07797b1b3d2671f8134b97bcdc009ce0fde7f9fda53d9175440309920838bb7dd129189322cd47851f2be587d288a38af2c32bf1024d9b7e265009db694d6d24d40576eb777b0b3713ac24cbbf1cf0534e565ce5030503c842e43438ca27557b209f475337d0203010001a3123010300e0603551d0f0101ff0404030205a0300d06092a864886f70d010105050003820101009bddfa8de87f1d9e7467e0251ca54441f6a68f4f3fc84b0fe273ffd7f01598df91b61b5bd61b14d1ecaa633d20c96b950797432e85f144d2cc04b59770e7ec912ffd59573dcc79d438ef04ed81ea98f09c8b4a2f1e7701dcac789ab33c2a2b39d026b72f3bcff9c29bdfbe34edd6be30ac6b050c10e259d4ed99b6efb4c9d0c32020f842e74984fd00bc59bb32e28ca5f32e052e19fa30859da473a402539bf58d87140edc935792f5e2da4a017e71304fbc3a20f25129a19f7f3ff3e6e1c75a6c1cf489d13e80d8a86fc8b6dd879088c4272d4bbd069b4a43bb61210b066c5280293aa580751337b24fda13553d7294b5916433e730a021520330236639e89c"
        };
        if (signatureArr != null) {
            i = -1;
            for (int i2 = 0; i2 < signatureArr.length; i2++) {
                if (signatureArr[i2] != null) {
                    int i3 = 0;
                    while (true) {
                        if (i3 >= 10) {
                            break;
                        }
                        if (strArr[i3].compareToIgnoreCase(
                                        signatureArr[i2].toCharsString().toString())
                                == 0) {
                            i = i3;
                            break;
                        }
                        i3++;
                    }
                }
            }
        } else {
            i = -1;
        }
        if (i == -1
                && BatteryService$$ExternalSyntheticOutline0.m45m(
                        "/data/system/.aasa/AASApolicy/ASKSK.xml")
                && signatureArr != null) {
            for (Signature signature : signatureArr) {
                if (signature != null) {
                    String str2 = signature.toCharsString().toString();
                    if (checkListForASKS(13, str2, null) != -1) {
                        Slog.i(
                                "AASA_ASKSManager",
                                " pkg:" + str + " signValue is same with " + str2);
                        return 10;
                    }
                }
            }
        }
        return i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x009d, code lost:

       if (r5 == null) goto L47;
    */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00a0, code lost:

       return r3;
    */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x008a, code lost:

       r5.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0088, code lost:

       if (r5 == null) goto L47;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isValidZipFormat(java.lang.String r7) {
        /*
            java.lang.String r0 = "AASA_ASKSManager"
            r1 = 1
            if (r7 == 0) goto Lac
            boolean r2 = isDevDevice()
            if (r2 == 0) goto L10
            java.lang.String r2 = "PackageInformation"
            android.util.Slog.d(r2, r7)
        L10:
            int r2 = r7.length()
            int r2 = r2 + (-4)
            java.lang.String r2 = r7.substring(r2)
            java.lang.String r2 = r2.toLowerCase()
            java.lang.String r3 = ".apk"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L27
            return r1
        L27:
            r2 = 0
            r3 = 0
            java.util.zip.ZipFile r4 = new java.util.zip.ZipFile     // Catch: java.lang.Throwable -> L75 java.io.IOException -> L78 java.util.zip.ZipException -> L7a
            r4.<init>(r7)     // Catch: java.lang.Throwable -> L75 java.io.IOException -> L78 java.util.zip.ZipException -> L7a
            java.util.zip.ZipInputStream r5 = new java.util.zip.ZipInputStream     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71 java.util.zip.ZipException -> L73
            java.io.FileInputStream r6 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71 java.util.zip.ZipException -> L73
            r6.<init>(r7)     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71 java.util.zip.ZipException -> L73
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L6e java.io.IOException -> L71 java.util.zip.ZipException -> L73
            java.util.zip.ZipEntry r7 = r5.getNextEntry()     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 java.util.zip.ZipException -> L62
            if (r7 != 0) goto L40
            r2 = r3
            goto L41
        L40:
            r2 = r1
        L41:
            r6 = 15
        L43:
            if (r2 == 0) goto L64
            if (r7 == 0) goto L64
            if (r6 == 0) goto L64
            r4.getInputStream(r7)     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 java.util.zip.ZipException -> L62
            r7.getCrc()     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 java.util.zip.ZipException -> L62
            r7.getCompressedSize()     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 java.util.zip.ZipException -> L62
            r7.getName()     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 java.util.zip.ZipException -> L62
            java.util.zip.ZipEntry r7 = r5.getNextEntry()     // Catch: java.lang.Throwable -> L5c java.io.IOException -> L60 java.util.zip.ZipException -> L62
            int r6 = r6 + (-1)
            goto L43
        L5c:
            r7 = move-exception
        L5d:
            r2 = r4
            goto La1
        L60:
            r2 = r4
            goto L7c
        L62:
            r2 = r4
            goto L91
        L64:
            r4.close()     // Catch: java.io.IOException -> L68
            goto L69
        L68:
            r2 = r1
        L69:
            r5.close()     // Catch: java.io.IOException -> La0
            r1 = r2
            goto La0
        L6e:
            r7 = move-exception
            r5 = r2
            goto L5d
        L71:
            r5 = r2
            goto L60
        L73:
            r5 = r2
            goto L62
        L75:
            r7 = move-exception
            r5 = r2
            goto La1
        L78:
            r5 = r2
            goto L7c
        L7a:
            r5 = r2
            goto L91
        L7c:
            java.lang.String r7 = "Non-Valid Format[2]"
            android.util.Log.e(r0, r7)     // Catch: java.lang.Throwable -> L8f
            if (r2 == 0) goto L88
            r2.close()     // Catch: java.io.IOException -> L87
            goto L88
        L87:
            r3 = r1
        L88:
            if (r5 == 0) goto L8d
        L8a:
            r5.close()     // Catch: java.io.IOException -> La0
        L8d:
            r1 = r3
            goto La0
        L8f:
            r7 = move-exception
            goto La1
        L91:
            java.lang.String r7 = "Non-Valid Format[1]"
            android.util.Log.e(r0, r7)     // Catch: java.lang.Throwable -> L8f
            if (r2 == 0) goto L9d
            r2.close()     // Catch: java.io.IOException -> L9c
            goto L9d
        L9c:
            r3 = r1
        L9d:
            if (r5 == 0) goto L8d
            goto L8a
        La0:
            return r1
        La1:
            if (r2 == 0) goto La6
            r2.close()     // Catch: java.io.IOException -> La6
        La6:
            if (r5 == 0) goto Lab
            r5.close()     // Catch: java.io.IOException -> Lab
        Lab:
            throw r7
        Lac:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.asks.ASKSManagerService.isValidZipFormat(java.lang.String):boolean");
    }

    public static boolean isVersionGreaterThan(String str, String str2) {
        try {
            return Integer.parseInt(str2) > Integer.parseInt(str);
        } catch (NumberFormatException e) {
            Slog.w("ASKSManager", "Policy version is wrong.");
            e.printStackTrace();
            return false;
        }
    }

    public static void loadCertificates(
            StrictJarFile strictJarFile, ZipEntry zipEntry, MessageDigest messageDigest) {
        InputStream inputStream = null;
        try {
            try {
                inputStream = strictJarFile.getInputStream(zipEntry);
                byte[] bArr = new byte[4096];
                if (inputStream != null) {
                    while (true) {
                        int read = inputStream.read(bArr, 0, 4096);
                        if (read == -1) {
                            break;
                        } else {
                            messageDigest.update(bArr, 0, read);
                        }
                    }
                    inputStream.close();
                }
                strictJarFile.getCertificates(zipEntry);
            } catch (IOException e) {
                Slog.e("AASA_ASKSManager", "loadCert(md) : TinyAASA + No IO " + e.toString());
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (RuntimeException e2) {
                Slog.e("AASA_ASKSManager", "loadCert(md) : TinyAASA + No RUN " + e2.toString());
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        } catch (IOException unused) {
        }
    }

    public static void loadCertificates(
            StrictJarFile strictJarFile, ZipEntry zipEntry, byte[] bArr) {
        InputStream inputStream = null;
        try {
            try {
                inputStream = strictJarFile.getInputStream(zipEntry);
                if (inputStream != null) {
                    while (inputStream.read(bArr, 0, bArr.length) != -1) {}
                    inputStream.close();
                }
                strictJarFile.getCertificates(zipEntry);
            } catch (IOException e) {
                Slog.i("AASA_ASKSManager", "loadCert(B) : No IO " + e.toString());
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (RuntimeException e2) {
                Slog.i("AASA_ASKSManager", "loadCert(B) : No RUN " + e2.toString());
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        } catch (IOException unused) {
        }
    }

    public static List parsePackages(XmlPullParser xmlPullParser) {
        int depth = xmlPullParser.getDepth();
        ArrayList arrayList = new ArrayList();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4 && xmlPullParser.getName().equals("package")) {
                String attributeValue = xmlPullParser.getAttributeValue(null, "name");
                if (!TextUtils.isEmpty(attributeValue) && !arrayList.contains(attributeValue)) {
                    arrayList.add(attributeValue);
                }
            }
        }
        return arrayList;
    }

    public static void parseXMLNew(XmlPullParser xmlPullParser, HashMap hashMap) {
        parseXMLNew$1(xmlPullParser, hashMap);
    }

    public static void parseXMLNew$1(XmlPullParser xmlPullParser, HashMap hashMap) {
        String name;
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2
                    && (name = xmlPullParser.getName()) != null
                    && hashMap.containsKey(name)) {
                if (xmlPullParser.getAttributeCount() != 1) {
                    Slog.e("AASA_ASKSManager", "this is an exceptional case");
                }
                hashMap.replace(name, xmlPullParser.getAttributeValue(0));
            }
            eventType = xmlPullParser.next();
        }
    }

    public static void putInstalledList(String str, String str2, HashMap hashMap) {
        ArrayList arrayList = (ArrayList) hashMap.get(str);
        if (arrayList == null || arrayList.isEmpty()) {
            arrayList = PortStatus_1_1$$ExternalSyntheticOutline0.m(str2);
        } else {
            arrayList.add(str2);
        }
        hashMap.put(str, arrayList);
    }

    public static void readRestrictPermissions(XmlPullParser xmlPullParser, ArrayList arrayList) {
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3
                    && next != 4
                    && "permission".equalsIgnoreCase(xmlPullParser.getName())
                    && xmlPullParser.getAttributeValue(null, "value") != null) {
                arrayList.add(xmlPullParser.getAttributeValue(null, "value"));
            }
        }
        arrayList.size();
    }

    public static void readRestrictRule(
            XmlPullParser xmlPullParser, Restrict restrict, String str) {
        ArrayList arrayList = null;
        String attributeValue = xmlPullParser.getAttributeValue(null, "type");
        String attributeValue2 = xmlPullParser.getAttributeValue(null, "datelimit");
        if (str == null) {
            str = xmlPullParser.getAttributeValue(null, "version");
        }
        String str2 = restrict.mFrom;
        if (str2 == null) {
            str2 = xmlPullParser.getAttributeValue(null, "from");
        }
        if (((attributeValue == null) || (str == null))
                || attributeValue2 == null
                || str2 == null) {
            return;
        }
        if ("REVOKE".equals(attributeValue)) {
            arrayList = new ArrayList();
            readRestrictPermissions(xmlPullParser, arrayList);
        }
        restrict.mVersion = str;
        restrict.mType = attributeValue;
        restrict.mDatelimit = attributeValue2;
        restrict.mFrom = str2;
        restrict.mPermissionList = arrayList;
    }

    public static void setDataToDeviceForInstalledUnknownList(List list) {
        File file;
        XmlSerializer newSerializer = Xml.newSerializer();
        FileOutputStream fileOutputStream = null;
        try {
            try {
                try {
                    file =
                            new File(
                                    "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_INSTALLED_INFO_LIST.xml");
                } catch (Exception e) {
                    e = e;
                }
                if (!file.createNewFile()) {
                    Slog.e("PackageInformation", "failed to created file related INFOLIST");
                    return;
                }
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                try {
                    newSerializer.setOutput(fileOutputStream2, "UTF-8");
                    newSerializer.startDocument(null, Boolean.TRUE);
                    newSerializer.setFeature(
                            "http://xmlpull.org/v1/doc/features.html#indent-output", true);
                    newSerializer.startTag(null, "LIST");
                    if (list != null) {
                        int i = 0;
                        while (true) {
                            ArrayList arrayList = (ArrayList) list;
                            if (i >= arrayList.size()) {
                                break;
                            }
                            newSerializer.startTag(null, "package");
                            newSerializer.attribute(
                                    null, "name", ((InstalledAppInfo) arrayList.get(i)).name);
                            newSerializer.attribute(
                                    null,
                                    "signature",
                                    ((InstalledAppInfo) arrayList.get(i)).signature);
                            newSerializer.attribute(
                                    null, "execute", ((InstalledAppInfo) arrayList.get(i)).execute);
                            newSerializer.attribute(
                                    null, "overlay", ((InstalledAppInfo) arrayList.get(i)).overlay);
                            newSerializer.attribute(
                                    null,
                                    "requestInstallerZip",
                                    ((InstalledAppInfo) arrayList.get(i)).requestInstallerZip);
                            newSerializer.attribute(
                                    null,
                                    "initType",
                                    ((InstalledAppInfo) arrayList.get(i)).initType);
                            newSerializer.attribute(
                                    null,
                                    "accessibility",
                                    ((InstalledAppInfo) arrayList.get(i)).accessibility);
                            newSerializer.attribute(
                                    null,
                                    "hasReqInstallPEM",
                                    ((InstalledAppInfo) arrayList.get(i)).hasReqInstallPEM);
                            newSerializer.attribute(
                                    null, "initPkg", ((InstalledAppInfo) arrayList.get(i)).initPkg);
                            newSerializer.attribute(
                                    null,
                                    "installAuthority",
                                    ((InstalledAppInfo) arrayList.get(i)).installAuthority);
                            newSerializer.attribute(
                                    null,
                                    "installAuthorityDate",
                                    ((InstalledAppInfo) arrayList.get(i)).installAuthorityDate);
                            newSerializer.endTag(null, "package");
                            i++;
                        }
                    }
                    newSerializer.endTag(null, "LIST");
                    newSerializer.endDocument();
                    newSerializer.flush();
                    fileOutputStream2.close();
                    fileOutputStream2.close();
                } catch (Exception e2) {
                    e = e2;
                    fileOutputStream = fileOutputStream2;
                    e.printStackTrace();
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                } catch (Throwable th) {
                    th = th;
                    fileOutputStream = fileOutputStream2;
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e4) {
            e4.printStackTrace();
        }
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:84:0x01fe -> B:16:0x0219). Please report as a decompilation issue!!! */
    public static void setDataToDeviceForModifyUnknownApp(
            int i, InstalledAppInfo installedAppInfo) {
        Throwable th;
        FileInputStream fileInputStream;
        Element element;
        NodeList nodeList;
        Element element2;
        String str;
        String str2;
        FileInputStream fileInputStream2 = null;
        try {
            try {
                try {
                    DocumentBuilder newDocumentBuilder =
                            DocumentBuilderFactory.newInstance().newDocumentBuilder();
                    FileInputStream fileInputStream3 =
                            new FileInputStream(
                                    new File(
                                            "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_INSTALLED_INFO_LIST.xml"));
                    try {
                        InputSource inputSource =
                                new InputSource(new InputStreamReader(fileInputStream3, "UTF-8"));
                        inputSource.setEncoding("UTF-8");
                        Document parse = newDocumentBuilder.parse(inputSource);
                        Element documentElement = parse.getDocumentElement();
                        try {
                            if (documentElement != null) {
                                String str3 = "installAuthority";
                                fileInputStream = fileInputStream3;
                                String str4 = "package";
                                String str5 = "PackageInformation";
                                if (i == 1) {
                                    Element createElement = parse.createElement("package");
                                    createElement.setAttribute("name", installedAppInfo.name);
                                    createElement.setAttribute(
                                            "signature", installedAppInfo.signature);
                                    createElement.setAttribute("execute", installedAppInfo.execute);
                                    createElement.setAttribute("overlay", installedAppInfo.overlay);
                                    createElement.setAttribute(
                                            "requestInstallerZip",
                                            installedAppInfo.requestInstallerZip);
                                    createElement.setAttribute(
                                            "initType", installedAppInfo.initType);
                                    createElement.setAttribute(
                                            "accessibility", installedAppInfo.accessibility);
                                    createElement.setAttribute(
                                            "hasReqInstallPEM", installedAppInfo.hasReqInstallPEM);
                                    createElement.setAttribute("initPkg", installedAppInfo.initPkg);
                                    createElement.setAttribute(
                                            "installAuthority", installedAppInfo.installAuthority);
                                    createElement.setAttribute(
                                            "installAuthorityDate",
                                            installedAppInfo.installAuthorityDate);
                                    element = documentElement;
                                    element.appendChild(createElement);
                                } else {
                                    element = documentElement;
                                    if (i == 2) {
                                        NodeList childNodes = element.getChildNodes();
                                        Element element3 = element;
                                        int i2 = 0;
                                        while (true) {
                                            if (i2 >= childNodes.getLength()) {
                                                element = element3;
                                                break;
                                            }
                                            String str6 = str3;
                                            if (childNodes.item(i2).getNodeType() == 1) {
                                                Element element4 = (Element) childNodes.item(i2);
                                                nodeList = childNodes;
                                                if (element4.getAttribute("name")
                                                                .equals(installedAppInfo.name)
                                                        && element4.getAttribute("signature")
                                                                .equals(
                                                                        installedAppInfo
                                                                                .signature)) {
                                                    Element createElement2 =
                                                            parse.createElement(str4);
                                                    if (createElement2 != null) {
                                                        createElement2.setAttribute(
                                                                "name", installedAppInfo.name);
                                                        createElement2.setAttribute(
                                                                "signature",
                                                                installedAppInfo.signature);
                                                        createElement2.setAttribute(
                                                                "execute",
                                                                installedAppInfo.execute);
                                                        createElement2.setAttribute(
                                                                "overlay",
                                                                installedAppInfo.overlay);
                                                        createElement2.setAttribute(
                                                                "requestInstallerZip",
                                                                installedAppInfo
                                                                        .requestInstallerZip);
                                                        createElement2.setAttribute(
                                                                "initType",
                                                                installedAppInfo.initType);
                                                        createElement2.setAttribute(
                                                                "accessibility",
                                                                installedAppInfo.accessibility);
                                                        createElement2.setAttribute(
                                                                "hasReqInstallPEM",
                                                                installedAppInfo.hasReqInstallPEM);
                                                        createElement2.setAttribute(
                                                                "initPkg",
                                                                installedAppInfo.initPkg);
                                                        createElement2.setAttribute(
                                                                str6,
                                                                installedAppInfo.installAuthority);
                                                        createElement2.setAttribute(
                                                                "installAuthorityDate",
                                                                installedAppInfo
                                                                        .installAuthorityDate);
                                                        Element element5 = element3;
                                                        element5.replaceChild(
                                                                createElement2, element4);
                                                        element = element5;
                                                        break;
                                                    }
                                                    element2 = element3;
                                                    str3 = str6;
                                                    str = str4;
                                                    str2 = str5;
                                                    Slog.e(str2, "Element tempNode is null");
                                                    i2++;
                                                    childNodes = nodeList;
                                                    str5 = str2;
                                                    str4 = str;
                                                    element3 = element2;
                                                }
                                            } else {
                                                nodeList = childNodes;
                                            }
                                            element2 = element3;
                                            str3 = str6;
                                            str = str4;
                                            str2 = str5;
                                            i2++;
                                            childNodes = nodeList;
                                            str5 = str2;
                                            str4 = str;
                                            element3 = element2;
                                        }
                                    } else if (i == 3) {
                                        NodeList childNodes2 = element.getChildNodes();
                                        int i3 = 0;
                                        while (true) {
                                            if (i3 >= childNodes2.getLength()) {
                                                break;
                                            }
                                            if (childNodes2.item(i3).getNodeType() == 1) {
                                                Element element6 = (Element) childNodes2.item(i3);
                                                if (element6.getAttribute("name") != null
                                                        && element6.getAttribute("name")
                                                                .equals(installedAppInfo.name)) {
                                                    Node previousSibling =
                                                            element6.getPreviousSibling();
                                                    if (previousSibling != null
                                                            && previousSibling.getNodeType() == 3
                                                            && previousSibling
                                                                            .getNodeValue()
                                                                            .trim()
                                                                            .length()
                                                                    == 0) {
                                                        element.removeChild(previousSibling);
                                                    }
                                                    element.removeChild(element6);
                                                }
                                            }
                                            i3++;
                                        }
                                    }
                                }
                                element.normalize();
                                Transformer newTransformer =
                                        TransformerFactory.newInstance().newTransformer();
                                newTransformer.setOutputProperty(
                                        "{http://xml.apache.org/xslt}indent-amount", "4");
                                newTransformer.setOutputProperty("indent", "yes");
                                newTransformer.setOutputProperty("encoding", "UTF-8");
                                newTransformer.transform(
                                        new DOMSource(parse),
                                        new StreamResult(
                                                "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_INSTALLED_INFO_LIST.xml"));
                            } else {
                                fileInputStream = fileInputStream3;
                                Slog.e("PackageInformation", "Element root is null");
                            }
                            fileInputStream.close();
                        } catch (Exception e) {
                            e = e;
                            fileInputStream2 = fileInputStream3;
                            e.printStackTrace();
                            if (fileInputStream2 != null) {
                                fileInputStream2.close();
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            th = th;
                            fileInputStream2 = fileInputStream3;
                            if (fileInputStream2 == null) {
                                throw th;
                            }
                            try {
                                fileInputStream2.close();
                                throw th;
                            } catch (IOException e2) {
                                e2.printStackTrace();
                                throw th;
                            }
                        }
                    } catch (Exception e3) {
                        e = e3;
                    } catch (Throwable th3) {
                        th = th3;
                    }
                } catch (Throwable th4) {
                    th = th4;
                }
            } catch (Exception e4) {
                e = e4;
            }
        } catch (IOException e5) {
            e5.printStackTrace();
        }
    }

    public static int setSafeInstallResult(int i) {
        if (i == 0) {
            return i;
        }
        if (i == 130) {
            if (isKorProject()) {
                return i;
            }
            return 0;
        }
        if (isKorProject()) {
            return i;
        }
        Slog.i("PackageInformation", "SafeInstallResult(): This is V OS global project.");
        return 101;
    }

    public static SafeInstallSAInfo setSafeInstallSAInfo(
            CURPARAM curparam,
            RETVALUE retvalue,
            CURSTATUS curstatus,
            String[] strArr,
            String str,
            String str2,
            String str3,
            String str4,
            String str5,
            String str6,
            String str7,
            boolean z) {
        HashMap hashMap = new HashMap();
        hashMap.put("packageName", str);
        hashMap.put("signature", curparam.sigHashValue);
        hashMap.put("initiatingPackageName", str2);
        hashMap.put("originatingPackageName", str3);
        hashMap.put("url", str4);
        hashMap.put("asksVersion", mASKSPolicyVersion);
        hashMap.put("policyFile", curstatus.totalListString);
        hashMap.put("policy", str5);
        hashMap.put("tagName", retvalue.tagName);
        MORERULES morerules = retvalue.morerules;
        if (morerules != null) {
            hashMap.put(
                    "moreRulesRandomPackage", String.valueOf(morerules.result_moreRule_RandomPkg));
            hashMap.put(
                    "moreRulesMalformed",
                    String.valueOf(retvalue.morerules.result_moreRule_Malformed));
            hashMap.put("moreRulesRank", String.valueOf(retvalue.morerules.result_moreRule_RANK));
        }
        if (!isValidZipFormat(str6)) {
            hashMap.put("malformed", "malformed");
        }
        hashMap.put("carrierID", str7);
        hashMap.put("koreaTarget", String.valueOf(z));
        String[] strArr2 = new String[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            strArr2[i] = strArr[i].replace("android.permission.", "");
        }
        hashMap.put("permissionList", String.join(",", strArr2));
        return new SafeInstallSAInfo(retvalue.eventNameForSA, Long.valueOf(retvalue.SA), hashMap);
    }

    public static void setTrustTimeByToken(String str) {
        long j;
        try {
            j = new SimpleDateFormat("yyyyMMdd").parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            j = -1;
        }
        setTrustedFile(3, j, SystemClock.elapsedRealtime());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void setTrustedFile(int i, long j, long j2) {
        Slog.d("AASA_ASKSManager_SECURETIME", "setTrustedFile : ");
        SystemProperties.set("security.ASKS.time_value", convertMillsToString(j));
        PrintWriter printWriter = null;
        PrintWriter printWriter2 = null;
        try {
            try {
                PrintWriter printWriter3 = new PrintWriter("/data/system/.aasa/trustedTime");
                try {
                    StringBuilder sb = new StringBuilder("");
                    sb.append(i);
                    sb.append(",");
                    sb.append(j);
                    sb.append(",");
                    sb.append(j2);
                    printWriter3.println(sb.toString());
                    printWriter3.flush();
                    printWriter3.close();
                    printWriter3.close();
                    printWriter = sb;
                } catch (IOException e) {
                    e = e;
                    printWriter2 = printWriter3;
                    Slog.d("AASA_ASKSManager_SECURETIME", "setTrustedTime() " + e);
                    printWriter = printWriter2;
                    if (printWriter2 != null) {
                        printWriter2.close();
                        printWriter = printWriter2;
                    }
                } catch (Throwable th) {
                    th = th;
                    printWriter = printWriter3;
                    if (printWriter != null) {
                        printWriter.close();
                    }
                    throw th;
                }
            } catch (IOException e2) {
                e = e2;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public static void updateSmsFilterFeatures() {
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        getASKSDataFromXML(40, hashMap2);
        getASKSDataFromXML(39, hashMap);
        boolean z = true;
        SystemProperties.set(
                "security.ASKS.smsfilter_enable",
                String.valueOf((hashMap2.size() == 0 && hashMap.size() == 0) ? false : true));
        String str = SystemProperties.get("ro.product.model", "Unknown");
        HashMap hashMap3 = new HashMap();
        getASKSDataFromXML(38, hashMap3);
        if (!hashMap3.containsKey("target_model")
                || !isKorProject()
                || (!((ArrayList) hashMap3.get("target_model")).contains("ALL")
                        && !((ArrayList) hashMap3.get("target_model")).contains(str))) {
            z = false;
        }
        SystemProperties.set("security.ASKS.smsfilter_target", String.valueOf(z));
    }

    public static void updateTrustedFile() {
        String[] trustedFile;
        Slog.d("AASA_ASKSManager_SECURETIME", "updateTrustedFile : ");
        if (hasTrustedTime()
                && (trustedFile = getTrustedFile()) != null
                && trustedFile.length == 3) {
            int parseInt = Integer.parseInt(trustedFile[0]);
            long parseLong = Long.parseLong(trustedFile[1]);
            long parseLong2 = Long.parseLong(trustedFile[2]);
            long elapsedRealtime = SystemClock.elapsedRealtime();
            setTrustedFile(parseInt, (parseLong - parseLong2) + elapsedRealtime, elapsedRealtime);
        }
    }

    public static void writeBlockApkList(String str) {
        PrintWriter printWriter = null;
        try {
            try {
                PrintWriter fastPrintWriter =
                        new FastPrintWriter(
                                new FileOutputStream(
                                        new File("/data/system/.aasa/blockedList.log"), false));
                try {
                    fastPrintWriter.println(str);
                    fastPrintWriter.close();
                    Slog.i("AASA_ASKSManager", "writeBlockApkList() Success");
                    fastPrintWriter.close();
                } catch (IOException e) {
                    e = e;
                    printWriter = fastPrintWriter;
                    Slog.i("AASA_ASKSManager", "writeBlockApkList() Fail " + e);
                    if (printWriter != null) {
                        printWriter.close();
                    }
                } catch (Throwable th) {
                    th = th;
                    printWriter = fastPrintWriter;
                    if (printWriter != null) {
                        printWriter.close();
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (IOException e2) {
            e = e2;
        }
    }

    public final void SAreport(
            RETVALUE retvalue,
            CURSTATUS curstatus,
            CURPARAM curparam,
            SafeInstallSAInfo safeInstallSAInfo) {
        boolean z;
        String str;
        String[] split;
        FileOutputStream fileOutputStream;
        if (safeInstallSAInfo == null) {
            Slog.e("PackageInformation", "abnormal SAreport");
            return;
        }
        ArrayList installedAppsDataFromXML = getInstalledAppsDataFromXML("isInstalledList", null);
        if (installedAppsDataFromXML == null
                || !installedAppsDataFromXML.contains(curparam.packageName)) {
            HashMap hashMap = new HashMap();
            getDataByDevice(
                    "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_SA_REPORTED_NEW.xml", hashMap);
            if (hashMap.containsKey(curparam.packageName + curparam.sigHashValue)) {
                return;
            }
            String str2 = curparam.packageName + curparam.sigHashValue;
            File file = new File("/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_SA_REPORTED_NEW.xml");
            if (file.length() < 10000) {
                Slog.i(
                        "APKFromUnknownSource",
                        "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_SA_REPORTED_NEW.xml adding.");
                z = true;
            } else {
                Slog.i(
                        "APKFromUnknownSource",
                        "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_SA_REPORTED_NEW.xml init..");
                z = false;
            }
            try {
                fileOutputStream = new FileOutputStream(file, z);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                FastPrintWriter fastPrintWriter = new FastPrintWriter(fileOutputStream);
                try {
                    fastPrintWriter.println(str2);
                    fastPrintWriter.flush();
                    fastPrintWriter.close();
                    fastPrintWriter.close();
                    fileOutputStream.close();
                    if (!curstatus.isValidZip) {
                        safeInstallSAInfo.eventValue = 3050L;
                        retvalue.policy = 150;
                    }
                    if (curstatus.isLocWebCase
                            && (str = curparam.referralUrl) != null
                            && (split = str.split("_")) != null
                            && split.length > 1
                            && "K".equals(split[1])) {
                        safeInstallSAInfo.customDimensionMap.put("3rdParty", "kakao");
                    }
                    setSafeInstallSALog(safeInstallSAInfo);
                } finally {
                }
            } finally {
            }
        }
    }

    public final void addUnknownAppList(
            String str,
            Signature[] signatureArr,
            RETVALUE retvalue,
            String str2,
            boolean z,
            boolean z2,
            boolean z3,
            String str3,
            String str4) {
        try {
            InstalledAppInfo installedAppInfo = new InstalledAppInfo();
            installedAppInfo.set(
                    str,
                    getSigHash(signatureArr[0]),
                    retvalue.isExecute == 505 ? "allow" : "block",
                    str2.equals("except") ? "allow" : "block",
                    z ? "true" : "false",
                    str2,
                    z2 ? "true" : "false",
                    z3 ? "true" : "false",
                    str3,
                    str4);
            this.installedAppInfoToStore = installedAppInfo;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public final void applyExecutePolicy() {
        if (this.mContext != null) {
            ArrayList installedAppsDataFromXML = getInstalledAppsDataFromXML("blockExecute", null);
            ArrayList installedAppsDataFromXML2 = getInstalledAppsDataFromXML("allowExecute", null);
            if (installedAppsDataFromXML != null && !installedAppsDataFromXML.isEmpty()) {
                int size = installedAppsDataFromXML.size();
                String[] strArr = new String[size];
                for (int i = 0; i < size; i++) {
                    strArr[i] = (String) installedAppsDataFromXML.get(i);
                    if (isDevDevice()) {
                        BootReceiver$$ExternalSyntheticOutline0.m(
                                new StringBuilder("B::"), strArr[i], "PackageInformation");
                    }
                }
                UnknownSourceAppManager.Helper.suspendUnknownSourceAppsForAllUsers(
                        this.mContext, strArr, true);
            }
            if (installedAppsDataFromXML2 == null || installedAppsDataFromXML2.isEmpty()) {
                return;
            }
            int size2 = installedAppsDataFromXML2.size();
            String[] strArr2 = new String[size2];
            for (int i2 = 0; i2 < size2; i2++) {
                strArr2[i2] = (String) installedAppsDataFromXML2.get(i2);
                if (isDevDevice()) {
                    BootReceiver$$ExternalSyntheticOutline0.m(
                            new StringBuilder("A::"), strArr2[i2], "PackageInformation");
                }
            }
            UnknownSourceAppManager.Helper.suspendUnknownSourceAppsForAllUsers(
                    this.mContext, strArr2, false);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0136  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean applyScpmPolicy() {
        /*
            Method dump skipped, instructions count: 322
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.asks.ASKSManagerService.applyScpmPolicy():boolean");
    }

    public final boolean applyScpmPolicyFromService(String str) {
        enforceSystemOrRoot("Only the system can claim applyScpmPolicyFromApp");
        if (!applyScpmPolicy()) {
            return false;
        }
        Slog.i("PackageInformation", "success to apply Scpm Policy.");
        setSafeInstallSAInfoForUpdatePolicy(str, mASKSPolicyVersion);
        refreshInstalledUnknownList_NEW();
        applyExecutePolicy();
        updateSmsFilterFeatures();
        return true;
    }

    public final String[] checkASKSTarget(int i) {
        int i2;
        if (enforceSystemOrRoot()) {
            Slog.i("AASA_ASKSManager", " checkASKSTarget type:" + i);
            ArrayList arrayList = new ArrayList();
            HashMap hashMap = new HashMap();
            ArrayMap packageStates =
                    ((PackageManagerService.PackageManagerInternalImpl) getPackageManagerInternal())
                            .mService
                            .snapshotComputer()
                            .getPackageStates();
            getASKSDataFromXML(9, hashMap);
            if (hashMap.size() != 0 && packageStates != null) {
                Iterator it = packageStates.values().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    AndroidPackageHidden androidPackage =
                            ((PackageStateInternal) it.next()).getAndroidPackage();
                    if (androidPackage != null) {
                        AndroidPackageHidden androidPackageHidden = androidPackage;
                        int i3 = 1;
                        i2 =
                                (androidPackageHidden.toAppInfoWithoutState().privateFlags & 8) == 0
                                        ? (androidPackageHidden.toAppInfoWithoutState().flags & 1)
                                                        != 0
                                                ? 0
                                                : 1
                                        : 0;
                        if (androidPackageHidden.toAppInfoWithoutState().isUpdatedSystemApp()) {
                            Slog.i(
                                    "AASA_ASKSManager",
                                    "isUpdatedSystemApp:" + androidPackage.getPackageName());
                        } else {
                            i3 = i2;
                        }
                        if (i3 != 0
                                && isSignatureMatched(
                                                androidPackage.getPackageName(),
                                                androidPackage.getSigningDetails().getSignatures())
                                        != -1) {
                            String sHA256ForPkgName =
                                    getSHA256ForPkgName(androidPackage.getPackageName());
                            if (hashMap.containsKey(sHA256ForPkgName)) {
                                Slog.e(
                                        "AASA_ASKSManager",
                                        "checkDevice Target app :"
                                                + androidPackage.getPackageName()
                                                + " ::"
                                                + sHA256ForPkgName);
                                String str = "";
                                try {
                                    byte[] apkFileHashBytes =
                                            getApkFileHashBytes(androidPackage.getBaseApkPath());
                                    if (apkFileHashBytes != null) {
                                        str = getSHA256ForPkgName(convertToHex(apkFileHashBytes));
                                    }
                                } catch (IOException unused) {
                                }
                                if (((ArrayList) hashMap.get(sHA256ForPkgName)).contains(str)
                                        && !arrayList.contains(androidPackage.getPackageName())) {
                                    Slog.e(
                                            "AASA_ASKSManager",
                                            androidPackage.getPackageName() + " is in Blist");
                                    arrayList.add(androidPackage.getPackageName());
                                }
                            }
                        }
                    }
                }
                if (arrayList.size() != 0) {
                    String[] strArr = new String[arrayList.size()];
                    while (i2 < arrayList.size()) {
                        strArr[i2] = (String) arrayList.get(i2);
                        StringBuilder m =
                                BatteryService$$ExternalSyntheticOutline0.m(
                                        i2, "return value[", "]:");
                        m.append(strArr[i2]);
                        Slog.e("AASA_ASKSManager", m.toString());
                        i2++;
                    }
                    return strArr;
                }
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x00f9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final byte[] checkCertificateChaining(
            com.android.server.asks.ASKSManagerService.ASKSSession r13,
            byte[] r14,
            java.security.cert.X509Certificate r15) {
        /*
            Method dump skipped, instructions count: 262
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.asks.ASKSManagerService.checkCertificateChaining(com.android.server.asks.ASKSManagerService$ASKSSession,"
                    + " byte[], java.security.cert.X509Certificate):byte[]");
    }

    public final void checkCurStatus(
            CURSTATUS curstatus, CURPARAM curparam, ArrayList arrayList, boolean z) {
        ArrayList installedAppsDataFromXML;
        String str;
        String str2;
        ArrayList installedAppsDataFromXML2;
        String str3;
        String str4;
        ArrayList installedAppsDataFromXML3;
        String str5;
        String str6;
        String str7;
        URI uri;
        String str8;
        boolean z2;
        curstatus.isDevDevice = isDevDevice();
        curparam.pkgNameHash = getSHA256ForPkgName(curparam.packageName);
        if (arrayList.contains("ALLOWSELFUPDATE") && z) {
            curstatus.isAllowSelfUpdate = true;
        }
        if (arrayList.contains("BLOCKSELFUPDATEwithPEM") && z) {
            curstatus.isBlockSelfUpdateWithPEM = true;
        }
        if (arrayList.contains("MALFORMED")) {
            curstatus.isCheckZipFormat = true;
        }
        boolean z3 = false;
        if (arrayList.contains("CALLPEMLIMIT")) {
            this.ASKS_UNKNOWN_LIMIT_CALLPEM = true;
        } else {
            this.ASKS_UNKNOWN_LIMIT_CALLPEM = false;
        }
        if (arrayList.contains("MCCKORONLY")) {
            curstatus.isLimitOnlyKorMCC = true;
        }
        if (arrayList.contains("TABLETEXCEPT")) {
            curstatus.isTabletExcepted = true;
        }
        if (arrayList.contains("REQUEST_INSTALL")) {
            curstatus.isCheckRequestInstallPEM = true;
        }
        if (z) {
            curstatus.isLocZipCase = true;
            Slog.i("PackageInformation", "zip case:" + curstatus.isLocZipCase + " by self update");
        }
        String str9 = curparam.referralUrl;
        if (str9 != null && str9.contains("WEB")) {
            curstatus.isLocWebCase = true;
            HeimdAllFsService$$ExternalSyntheticOutline0.m(
                    "PackageInformation",
                    new StringBuilder("This is Web case:"),
                    curstatus.isLocWebCase);
        }
        if (str9 != null && "ZIP".equals(str9)) {
            curstatus.isLocZipCase = true;
            HeimdAllFsService$$ExternalSyntheticOutline0.m(
                    "PackageInformation",
                    new StringBuilder("This is zip case:"),
                    curstatus.isLocZipCase);
        }
        String str10 = curparam.downloadUrl;
        if (str10 != null) {
            curstatus.isLocUrlCase = true;
            if (str10.startsWith("HTTPS")) {
                str10 =
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                "http", str10.substring(5));
            } else if (str10.startsWith("HTTP")) {
                str10 =
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                "http", str10.substring(4));
            } else if (str10.startsWith("http://www")) {
                str10 =
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                "http://", str10.substring(11));
            } else if (!str10.startsWith("http") && !str10.startsWith("https")) {
                if (str10.startsWith("www")) {
                    str10 = str10.substring(4);
                }
                str10 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("http://", str10);
            }
            try {
                uri = new URI(str10);
            } catch (URISyntaxException e) {
                e.printStackTrace();
                uri = null;
            }
            if (uri == null || (str8 = uri.getHost()) == null) {
                str8 = null;
            } else if (str8.startsWith("www")) {
                str8 = str8.substring(4);
            }
            curparam.domain = str8;
            if (str8 != null) {
                try {
                    z2 =
                            str8.matches(
                                    "(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])");
                } catch (Exception unused) {
                    z2 = false;
                }
                if (z2) {
                    Slog.i("PackageInformation", "IP:".concat(str8));
                } else {
                    Slog.i("PackageInformation", "Not IP:".concat(str8));
                }
            } else {
                z2 = false;
            }
            curstatus.isIP = z2;
            curparam.hashDomain = getSHA256ForPkgName(curparam.domain);
        }
        Signature[] signatureArr = curparam.signatures;
        if (signatureArr != null && signatureArr.length > 0) {
            try {
                curparam.sigHashValue = getSigHash(signatureArr[0]);
            } catch (NoSuchAlgorithmException e2) {
                curparam.sigHashValue = null;
                e2.printStackTrace();
            }
        }
        if (curparam.sigHashValue != null) {
            curparam.pkgSigHash = getSHA256ForPkgName(curparam.pkgNameHash + curparam.sigHashValue);
            if (isDevDevice()) {
                BootReceiver$$ExternalSyntheticOutline0.m(
                        new StringBuilder("pkgSigHash::"),
                        curparam.pkgSigHash,
                        "PackageInformation");
            }
        } else {
            curparam.pkgSigHash = null;
        }
        if (curparam.permList != null) {
            curstatus.hasReqInstallPEM = false;
            int i = 0;
            while (true) {
                String[] strArr = curparam.permList;
                if (i >= strArr.length) {
                    break;
                }
                if (strArr[i].equals("android.permission.REQUEST_INSTALL_PACKAGES")) {
                    curstatus.hasReqInstallPEM = true;
                    break;
                }
                i++;
            }
        }
        if (curparam.servicePermList != null) {
            int i2 = 0;
            while (true) {
                String[] strArr2 = curparam.servicePermList;
                if (i2 >= strArr2.length) {
                    break;
                }
                if (strArr2[i2].equals("android.permission.BIND_ACCESSIBILITY_SERVICE")) {
                    curstatus.isLocAccessibilityCase = true;
                    break;
                }
                i2++;
            }
        }
        ArrayList installedAppsDataFromXML4 = getInstalledAppsDataFromXML("initType", null);
        if (installedAppsDataFromXML4 != null) {
            for (int i3 = 0; i3 < installedAppsDataFromXML4.size(); i3++) {
                String[] split = ((String) installedAppsDataFromXML4.get(i3)).split(",");
                String str11 = curparam.initiatingPackageName;
                if ((str11 != null && split[0].equals(str11))
                        || ((str7 = curparam.originatingPackageName) != null
                                && split[0].equals(str7))) {
                    if (!split[1].equals("except")) {
                        StringBuilder sb = new StringBuilder("installer:");
                        sb.append(curparam.initiatingPackageName);
                        sb.append(" :: ");
                        DeviceIdleController$$ExternalSyntheticOutline0.m(
                                sb, curparam.originatingPackageName, "PackageInformation");
                        z3 = true;
                    }
                }
            }
        }
        if (!curstatus.isLocZipCase
                && z3
                && (installedAppsDataFromXML3 =
                                getInstalledAppsDataFromXML("requestInstallerZip", null))
                        != null
                && (((str5 = curparam.initiatingPackageName) != null
                                && installedAppsDataFromXML3.contains(str5))
                        || ((str6 = curparam.originatingPackageName) != null
                                && installedAppsDataFromXML3.contains(str6)))) {
            curstatus.isLocZipCase = true;
        }
        if (!curstatus.isLocZipCase
                && z3
                && (installedAppsDataFromXML2 = getInstalledAppsDataFromXML("accessibility", null))
                        != null
                && (((str3 = curparam.initiatingPackageName) != null
                                && installedAppsDataFromXML2.contains(str3))
                        || ((str4 = curparam.originatingPackageName) != null
                                && installedAppsDataFromXML2.contains(str4)))) {
            curstatus.isLocZipCase = true;
        }
        if (!curstatus.isLocAccessibilityCase
                && curstatus.isCheckRequestInstallPEM
                && z3
                && (installedAppsDataFromXML =
                                getInstalledAppsDataFromXML("hasReqInstallPEM", null))
                        != null
                && (((str = curparam.initiatingPackageName) != null
                                && installedAppsDataFromXML.contains(str))
                        || ((str2 = curparam.originatingPackageName) != null
                                && installedAppsDataFromXML.contains(str2)))) {
            curstatus.isLocAccessibilityCase = true;
        }
        curstatus.totalList = 28;
        boolean z4 = curstatus.isLocZipCase;
        String str12 = curstatus.target_model;
        if (z4 || curstatus.isLocAccessibilityCase) {
            HashMap hashMap = new HashMap();
            getASKSDataFromXML(26, hashMap);
            if (curstatus.isLocZipCase) {
                Slog.i("PackageInformation", "changed By zip");
            } else if (curstatus.isLocAccessibilityCase) {
                Slog.i("PackageInformation", "changed By A11Y");
            }
            if (hashMap.containsKey("ALL") || hashMap.containsKey(str12)) {
                curstatus.totalList = 30;
            }
        } else if (curstatus.isIP) {
            Slog.i("PackageInformation", "changed By IP");
            HashMap hashMap2 = new HashMap();
            getASKSDataFromXML(26, hashMap2);
            if (hashMap2.containsKey("ALL") || hashMap2.containsKey(str12)) {
                curstatus.totalList = 31;
            }
        }
        int i4 = curstatus.totalList;
        if (i4 == 30) {
            curstatus.totalListString = "TOTALLIST_A11Y";
        } else if (i4 != 31) {
            curstatus.totalListString = "TOTALLIST";
        } else {
            curstatus.totalListString = "TOTALLIST_WEB";
        }
    }

    public final void checkDeletableListForASKS() {
        if (!enforceSystemOrRoot()) {
            Slog.e("AASA_ASKSManager_DELETABLE", "ERROR::: Unknown caller");
            return;
        }
        String trustedToday = getTrustedToday();
        HashMap hashMap = (HashMap) this.mASKSStates.clone();
        for (Map.Entry entry : hashMap.entrySet()) {
            ASKSState aSKSState = (ASKSState) hashMap.get(entry.getKey());
            Deletable deletable = aSKSState.deletable;
            if (deletable != null) {
                String str = deletable.mDatelimit;
                if (trustedToday != null && str != null) {
                    try {
                        if (Integer.parseInt(trustedToday) > Integer.parseInt(str)) {
                            AndroidPackage androidPackage =
                                    getPackageManagerInternal().getPackage((String) entry.getKey());
                            aSKSState.deletable = null;
                            if (androidPackage == null
                                    || !androidPackage.getBaseApkPath().startsWith("/data")) {
                                Slog.i(
                                        "AASA_ASKSManager_DELETABLE",
                                        "does not found delete target - "
                                                + ((String) entry.getKey()));
                            } else {
                                try {
                                    PackageManagerServiceUtils.logCriticalInfo(
                                            4,
                                            "a app deleted by the restricted policy. the date is"
                                                + " expired ["
                                                    + ((String) entry.getKey())
                                                    + "]");
                                    AppGlobals.getPackageManager()
                                            .deletePackageAsUser(
                                                    (String) entry.getKey(),
                                                    -1,
                                                    (IPackageDeleteObserver) null,
                                                    this.mContext.getUserId(),
                                                    0);
                                } catch (RemoteException unused) {
                                }
                            }
                            writeState();
                        }
                    } catch (NumberFormatException unused2) {
                        Slog.d("AASA_ASKSManager_DELETABLE", "NumberFormatException ::");
                    }
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0190  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void checkExistUnknownAppList() {
        /*
            Method dump skipped, instructions count: 430
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.asks.ASKSManagerService.checkExistUnknownAppList():void");
    }

    public final boolean checkFollowingLegitimateWay(String str, int i) {
        enforceSystemOrRoot("Only the system can claim checkFollowingLegitimateWay");
        if (this.mSessions.containsKey(str)) {
            return true;
        }
        Slog.i("ASKSManager", str + " has not followed legitimate way");
        return false;
    }

    public final String checkIfSuspiciousValue(String str, String str2, boolean z, Map map) {
        enforceSystemOrRoot("Only the system and sub system can claim checkIfTargetFromManager()");
        String str3 = null;
        if (!new File(str2).exists()) {
            return null;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(str2));
            try {
                XmlPullParser newPullParser = Xml.newPullParser();
                newPullParser.setFeature(
                        "http://xmlpull.org/v1/doc/features.html#process-namespaces", false);
                newPullParser.setInput(fileInputStream, null);
                int eventType = newPullParser.getEventType();
                String str4 = "";
                String str5 = "";
                while (true) {
                    if (eventType == 1) {
                        break;
                    }
                    String name = newPullParser.getName();
                    if (eventType == 2) {
                        if (name != null && name.equalsIgnoreCase("contents")) {
                            str4 = newPullParser.getAttributeValue(null, "value");
                            String attributeValue = newPullParser.getAttributeValue(null, "type");
                            if (attributeValue == null) {
                                attributeValue = "block";
                            }
                            str5 = attributeValue;
                        } else if (name != null && name.equalsIgnoreCase("pId") && str4 != null) {
                            if (z) {
                                if (str.contains(str4)) {
                                    str3 = newPullParser.getAttributeValue(null, "value");
                                    map.put("type", str5);
                                    map.put("contents", str4);
                                    break;
                                }
                            } else if (str4.equals(str)) {
                                str3 = newPullParser.getAttributeValue(null, "value");
                                map.put("type", str5);
                                map.put("contents", str4);
                                break;
                            }
                        }
                    }
                    eventType = newPullParser.next();
                }
                fileInputStream.close();
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str3;
    }

    /* JADX WARN: Removed duplicated region for block: B:76:0x01bc A[Catch: IOException -> 0x01bf, TRY_LEAVE, TryCatch #19 {IOException -> 0x01bf, blocks: (B:81:0x01b7, B:76:0x01bc), top: B:80:0x01b7 }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01b7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01c8 A[Catch: IOException -> 0x01cb, TRY_LEAVE, TryCatch #15 {IOException -> 0x01cb, blocks: (B:91:0x01c3, B:86:0x01c8), top: B:90:0x01c3 }] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x01c3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final byte[] checkIntegrityNew(
            com.android.server.asks.ASKSManagerService.ASKSSession r18,
            int r19,
            byte[] r20,
            byte[] r21) {
        /*
            Method dump skipped, instructions count: 460
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.asks.ASKSManagerService.checkIntegrityNew(com.android.server.asks.ASKSManagerService$ASKSSession,"
                    + " int, byte[], byte[]):byte[]");
    }

    public final int checkNetworkConnection(Context context) {
        Slog.d("AASA_ASKSManager_SECURETIME", "checkNetworkConnection : ");
        if (context == null) {
            Slog.d("AASA_ASKSManager_SECURETIME", "context is null. ");
            return 0;
        }
        NetworkInfo activeNetworkInfo =
                ((ConnectivityManager) context.getSystemService("connectivity"))
                        .getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            if (activeNetworkInfo.getType() == 1) {
                return this.TYPE_WIFI;
            }
            if (activeNetworkInfo.getType() == 0) {
                return this.TYPE_MOBILE;
            }
        }
        return 0;
    }

    public final int checkRestrictedPermission(String str, String str2) {
        Restrict restrict;
        ArrayList arrayList;
        ASKSState aSKSState = (ASKSState) this.mASKSStates.get(str);
        if (aSKSState == null || (restrict = aSKSState.restrict) == null) {
            return 0;
        }
        String str3 = restrict.mDatelimit;
        String trustedToday = getTrustedToday();
        return (trustedToday == null
                        || str3 == null
                        || Integer.parseInt(trustedToday) <= Integer.parseInt(str3)
                        || (arrayList = restrict.mPermissionList) == null
                        || !arrayList.contains(str2))
                ? 0
                : 4;
    }

    public final byte[] checkRootCertificate(byte[] bArr, X509Certificate x509Certificate) {
        CertificateFactory certificateFactory;
        FileInputStream fileInputStream;
        Slog.i("PackageInformation", "[Token] checkRootCertificate start ");
        FileInputStream fileInputStream2 = null;
        try {
            certificateFactory = CertificateFactory.getInstance("x.509");
            fileInputStream = new FileInputStream(this.ROOT_CERT_FILE);
        } catch (Exception unused) {
        }
        try {
            try {
                X509Certificate x509Certificate2 =
                        (X509Certificate) certificateFactory.generateCertificate(fileInputStream);
                x509Certificate.verify(x509Certificate2.getPublicKey());
                Slog.i("PackageInformation", "[Token] Signer authentication has been confirmed.");
                try {
                    x509Certificate2.verify(x509Certificate2.getPublicKey());
                    Slog.i("AASA_ASKSManager", "[Token] rootCert is verificated!");
                    fileInputStream.close();
                    return bArr;
                } catch (Exception e) {
                    Slog.i("AASA_ASKSManager", "[Token] ERROR: rootCert is not verified " + e);
                    fileInputStream.close();
                    return "22".getBytes();
                }
            } catch (Exception e2) {
                Slog.i(
                        "PackageInformation",
                        "[Token] ERROR: SignerCert is not verified by RootCert " + e2);
                fileInputStream.close();
                return "22".getBytes();
            }
        } catch (Exception unused2) {
            fileInputStream2 = fileInputStream;
            Slog.e("AASA_ASKSManager", "Token is NOT verificated in CheckCRL!");
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            return "22".getBytes();
        }
    }

    public final int checkSecurityEnabled() {
        return 128;
    }

    /*  JADX ERROR: Types fix failed
    jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
    	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
    	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
    	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:96)
    */
    public final int checkUnknownSourcePackage(
            java.lang.String r48,
            java.lang.String[] r49,
            java.lang.String[] r50,
            java.lang.String r51,
            android.content.pm.Signature[] r52,
            java.lang.String r53,
            java.lang.String r54,
            java.lang.String r55,
            int r56,
            java.lang.String r57,
            java.lang.String r58,
            int r59,
            java.lang.String r60,
            java.lang.String r61) {
        /*
            Method dump skipped, instructions count: 3640
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.asks.ASKSManagerService.checkUnknownSourcePackage(java.lang.String,"
                    + " java.lang.String[], java.lang.String[], java.lang.String,"
                    + " android.content.pm.Signature[], java.lang.String, java.lang.String,"
                    + " java.lang.String, int, java.lang.String, java.lang.String, int,"
                    + " java.lang.String, java.lang.String):int");
    }

    public final void clearASKSruleForRemovedPackage(String str) {
        enforceSystemOrRoot("Only the system can claim clearASKSruleForRemovedPackage");
        if (((ASKSState) this.mASKSStates.get(str)) != null) {
            this.mASKSStates.remove(str);
            writeState();
        }
        InstalledAppInfo installedAppInfo = new InstalledAppInfo();
        installedAppInfo.set(str, null, null, null, null, null, null, null, null, null);
        setDataToDeviceForModifyUnknownApp(3, installedAppInfo);
        HashMap hashMap = new HashMap();
        getDataByDevice("/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_SA_REPORTED_NEW.xml", hashMap);
        if (hashMap.containsKey(str)) {
            hashMap.remove(str);
            int size = hashMap.size();
            HermesService$3$$ExternalSyntheticOutline0.m(
                    size, "clearPackageFromFile() : count:", "PackageInformation");
            if (size > 100 || (r12 = hashMap.entrySet().iterator()) == null) {
                return;
            }
            try {
                File file =
                        new File("/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_SA_REPORTED_NEW.xml");
                if (file.exists()) {
                    if (file.delete()) {
                        Slog.i("ASKSManager", "File is deleted");
                    } else {
                        Slog.e("ASKSManager", "File is not deleted");
                    }
                }
                FastPrintWriter fastPrintWriter =
                        new FastPrintWriter(new FileOutputStream(file, false));
                for (Map.Entry entry : hashMap.entrySet()) {
                    String str2 = (String) entry.getKey();
                    ArrayList arrayList = (ArrayList) entry.getValue();
                    if (arrayList != null && arrayList.size() == 1) {
                        if ("noCert".equals(arrayList.get(0))) {
                            fastPrintWriter.println(str2);
                            Slog.i(
                                    "PackageInformation",
                                    "clearPackageFromFile() : adding  :: pkg =" + str2);
                        } else {
                            fastPrintWriter.println(str2 + "," + ((String) arrayList.get(0)));
                            StringBuilder sb = new StringBuilder();
                            sb.append("clearPackageFromFile() : adding  :: pkg =");
                            sb.append(str2);
                            Slog.i("PackageInformation", sb.toString());
                        }
                    }
                    size--;
                    if (size == 0) {
                        break;
                    }
                }
                fastPrintWriter.flush();
                fastPrintWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public final void compareAttributeValue(
            UnknownStore unknownStore, InstalledAppInfo installedAppInfo) {
        ArrayList installedAppsDataFromXML;
        String str;
        HashMap hashMap;
        ArrayList arrayList = unknownStore.exceptPkgName;
        String sHA256ForPkgName = getSHA256ForPkgName(installedAppInfo.name);
        String str2 = installedAppInfo.signature;
        if (sHA256ForPkgName == null
                || sHA256ForPkgName.isEmpty()
                || str2 == null
                || str2.isEmpty()) {
            Slog.e("PackageInformation", "pkgNameHash is NULL!!");
            return;
        }
        if (arrayList == null
                || !(arrayList.contains(sHA256ForPkgName) || arrayList.contains(str2))) {
            if ("allow".equals(installedAppInfo.overlay)) {
                installedAppInfo.overlay = "block";
                setDataToDeviceForModifyUnknownApp(2, installedAppInfo);
            }
        } else if ("block".equals(installedAppInfo.overlay)) {
            installedAppInfo.overlay = "allow";
            setDataToDeviceForModifyUnknownApp(2, installedAppInfo);
        }
        ArrayList arrayList2 = unknownStore.executeBlockPkgName;
        if (arrayList2 == null
                || !(arrayList2.contains(sHA256ForPkgName) || arrayList2.contains(str2))) {
            if ("block".equals(installedAppInfo.execute)) {
                installedAppInfo.execute = "allow";
                setDataToDeviceForModifyUnknownApp(2, installedAppInfo);
            }
        } else if ("allow".equals(installedAppInfo.execute)) {
            installedAppInfo.execute = "block";
            setDataToDeviceForModifyUnknownApp(2, installedAppInfo);
            HashMap hashMap2 = unknownStore.certPolicies;
            if (((hashMap2 != null && !hashMap2.containsKey(sHA256ForPkgName))
                            || unknownStore.certPolicies == null)
                    && (hashMap = unknownStore.certPolicies) != null
                    && hashMap.containsKey(sHA256ForPkgName)
                    && ((HashMap) unknownStore.certPolicies.get(sHA256ForPkgName))
                            .containsKey("ALL")) {
                int i =
                        ((PKGINFO)
                                        ((HashMap) unknownStore.certPolicies.get(sHA256ForPkgName))
                                                .get("ALL"))
                                .SA;
                HashMap hashMap3 = new HashMap();
                hashMap3.put("packageName", installedAppInfo.name);
                hashMap3.put("signature", installedAppInfo.signature);
                setSafeInstallSALog(
                        new SafeInstallSAInfo("executeBlock", Long.valueOf(i), hashMap3));
            }
        }
        HashMap hashMap4 = unknownStore.unknownAppsList;
        if (hashMap4 == null
                || !hashMap4.containsKey(str2)
                || (installedAppsDataFromXML = getInstalledAppsDataFromXML("initType", null))
                        == null) {
            return;
        }
        for (int i2 = 0; i2 < installedAppsDataFromXML.size(); i2++) {
            String[] split = ((String) installedAppsDataFromXML.get(i2)).split(",");
            String str3 = split[0];
            if (str3 != null && split[1] != null) {
                String sHA256ForPkgName2 = getSHA256ForPkgName(str3);
                String str4 = split[1];
                if (sHA256ForPkgName.equals(sHA256ForPkgName2)
                        && (str = (String) hashMap4.get(str2)) != null
                        && !str.equals(str4)) {
                    if (isDevDevice()) {
                        Slog.w(
                                "PackageInformation",
                                split[0]
                                        + "'s policy was changed from "
                                        + installedAppInfo.initType
                                        + " to "
                                        + str);
                    }
                    installedAppInfo.initType = str;
                    setDataToDeviceForModifyUnknownApp(2, installedAppInfo);
                }
            }
        }
    }

    public final List getIMEIList() {
        TelephonyManager telephonyManager =
                (TelephonyManager) this.mContext.getSystemService("phone");
        ArrayList arrayList = new ArrayList();
        if (telephonyManager != null) {
            int phoneCount = telephonyManager.getPhoneCount();
            if (phoneCount > 0) {
                for (int i = 0; i < phoneCount; i++) {
                    if (telephonyManager.getImei(i) != null) {
                        arrayList.add(getSHA256ForPkgName(telephonyManager.getImei(i)));
                        Slog.d("ASKSManager", "ASKSI added list");
                    }
                }
            } else {
                String imei = telephonyManager.getImei();
                if (imei == null && (imei = telephonyManager.getDeviceId()) == null) {
                    imei = "INVALID_IMEI";
                }
                Slog.d("ASKSManager", "ASKSI added list 2");
                arrayList.add(getSHA256ForPkgName(imei));
            }
        }
        return arrayList;
    }

    public final PackageManagerInternal getPackageManagerInternal() {
        if (this.mPackageManagerInternal == null) {
            this.mPackageManagerInternal =
                    (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        }
        return this.mPackageManagerInternal;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0044, code lost:

       r0 = r6.getAttributeValue(null, "value");
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getPolicyVersion(java.lang.String r6) {
        /*
            r5 = this;
            java.lang.String r5 = "Only the system and sub system can claim getPolicyVersion()"
            enforceSystemOrRoot(r5)
            java.io.File r5 = new java.io.File
            r5.<init>(r6)
            boolean r5 = r5.exists()
            java.lang.String r0 = "00000000"
            if (r5 != 0) goto L13
            return r0
        L13:
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch: java.lang.Exception -> L57
            java.io.File r1 = new java.io.File     // Catch: java.lang.Exception -> L57
            r1.<init>(r6)     // Catch: java.lang.Exception -> L57
            r5.<init>(r1)     // Catch: java.lang.Exception -> L57
            org.xmlpull.v1.XmlPullParser r6 = android.util.Xml.newPullParser()     // Catch: java.lang.Throwable -> L4c
            java.lang.String r1 = "http://xmlpull.org/v1/doc/features.html#process-namespaces"
            r2 = 0
            r6.setFeature(r1, r2)     // Catch: java.lang.Throwable -> L4c
            r1 = 0
            r6.setInput(r5, r1)     // Catch: java.lang.Throwable -> L4c
            int r2 = r6.getEventType()     // Catch: java.lang.Throwable -> L4c
        L30:
            r3 = 1
            if (r2 == r3) goto L53
            java.lang.String r3 = r6.getName()     // Catch: java.lang.Throwable -> L4c
            r4 = 2
            if (r2 != r4) goto L4e
            if (r3 == 0) goto L4e
            java.lang.String r2 = "VERSION"
            boolean r2 = r3.equalsIgnoreCase(r2)     // Catch: java.lang.Throwable -> L4c
            if (r2 == 0) goto L4e
            java.lang.String r2 = "value"
            java.lang.String r0 = r6.getAttributeValue(r1, r2)     // Catch: java.lang.Throwable -> L4c
            goto L53
        L4c:
            r6 = move-exception
            goto L59
        L4e:
            int r2 = r6.next()     // Catch: java.lang.Throwable -> L4c
            goto L30
        L53:
            r5.close()     // Catch: java.lang.Exception -> L57
            goto L65
        L57:
            r5 = move-exception
            goto L62
        L59:
            r5.close()     // Catch: java.lang.Throwable -> L5d
            goto L61
        L5d:
            r5 = move-exception
            r6.addSuppressed(r5)     // Catch: java.lang.Exception -> L57
        L61:
            throw r6     // Catch: java.lang.Exception -> L57
        L62:
            r5.printStackTrace()
        L65:
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.asks.ASKSManagerService.getPolicyVersion(java.lang.String):java.lang.String");
    }

    public final byte[] getSEInfo(String str) {
        enforceSystemOrRoot("Only the system can claim getSEInfo");
        byte[] bytes = "aasa_blocked".getBytes();
        ASKSState aSKSState = (ASKSState) this.mASKSStates.get(str);
        if (str != null && aSKSState != null) {
            Restrict restrict = aSKSState.restrict;
            int i = aSKSState.emMode;
            if (restrict != null && "DENY".equals(restrict.mType)) {
                String str2 = restrict.mDatelimit;
                String trustedToday = getTrustedToday();
                if (trustedToday != null
                        && str2 != null
                        && Integer.parseInt(trustedToday) > Integer.parseInt(str2)) {
                    return bytes;
                }
            }
            if (i != -1) {
                EngineeringModeManager engineeringModeManager =
                        new EngineeringModeManager(this.mContext);
                if (engineeringModeManager.isConnected()
                        && engineeringModeManager.getStatus(i) == 1) {
                    return null;
                }
                return bytes;
            }
        }
        return null;
    }

    public final String getSigByPackage(int i, String str) {
        PackageInfo packageInfoAsUser;
        SigningInfo signingInfo;
        Signature[] apkContentsSigners;
        try {
            if (this.mContext.getPackageManager() == null
                    || (packageInfoAsUser =
                                    this.mContext
                                            .getPackageManager()
                                            .getPackageInfoAsUser(str, 134217728, i))
                            == null
                    || (signingInfo = packageInfoAsUser.signingInfo) == null
                    || (apkContentsSigners = signingInfo.getApkContentsSigners()) == null
                    || apkContentsSigners.length < 1) {
                return null;
            }
            return getSigHash(apkContentsSigners[0]);
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e(
                    "PackageInformation",
                    " Abnormal case : initiatingPackageName can not be modified " + e);
            return null;
        } catch (NoSuchAlgorithmException e2) {
            Slog.e("PackageInformation", " Abnormal case : NoSuchAlgorithmException " + e2);
            return null;
        }
    }

    public final String getUNvalueForASKS() {
        enforceSystemOrRoot("Only the system can claim getUNvalueForASKS");
        if ("0x1".equals(SystemProperties.get("ro.boot.em.status"))) {
            return SystemProperties.get("ro.serialno", "none");
        }
        return null;
    }

    public final List getUnknownAppList() {
        enforceSystemOrRoot("Only the system can claim isUnknownApps");
        if (!this.ASKS_UNKNOWN_LIMIT_CALLPEM) {
            Slog.i("PackageInformation", "LIMIT_CALLPEM disabled");
            return null;
        }
        Slog.i("PackageInformation", "checking limitCallPem..");
        ArrayList installedAppsDataFromXML = getInstalledAppsDataFromXML("overlay", null);
        if (installedAppsDataFromXML == null || installedAppsDataFromXML.isEmpty()) {
            Slog.e("PackageInformation", "getUnknownAppList : installedUnknownList is null");
            return null;
        }
        if (isDevDevice()) {
            Slog.i(
                    "PackageInformation",
                    "getUnknownAppList : " + installedAppsDataFromXML.toString());
        }
        return installedAppsDataFromXML;
    }

    public final boolean isSimpleTrustore(int i, String str, boolean z) {
        HashMap hashMap = new HashMap();
        if (z) {
            Slog.i("RAMPARTPackageInformation", "Simple TS  :" + str + " " + i);
            if ("CN".equalsIgnoreCase(SemCscFeature.getInstance().getString("CountryISO"))) {
                getASKSDataFromXML(42, hashMap);
            } else {
                getASKSDataFromXML(41, hashMap);
            }
            if (hashMap.size() == 0) {
                Slog.e("RAMPARTPackageInformation", "Simple TS  : list does not exist");
                return false;
            }
        } else {
            String sigByPackage = getSigByPackage(i, str);
            if (sigByPackage == null) {
                sigByPackage = "null";
            }
            StringBuilder m =
                    StorageManagerService$$ExternalSyntheticOutline0.m(
                            i, "Simple TS : ", str, ":", ":");
            if (!isDevDevice()) {
                sigByPackage = "";
            }
            m.append(sigByPackage);
            Slog.i("PackageInformation", m.toString());
            ArrayList targetNodeName =
                    getTargetNodeName(SystemProperties.get("ro.product.model", "Unknown"));
            if (targetNodeName == null || !targetNodeName.contains("SIMPLETRUSTEDSTORE")) {
                Slog.e("PackageInformation", "no target of Simple TS ");
                return false;
            }
            getASKSDataFromXML(36, hashMap);
        }
        return isTrustedStoreCheck("PackageInformation", hashMap, str, i);
    }

    public final boolean isSuspiciousMsgTarget(String str) {
        enforceSystemOrRoot("Only the system and sub system can claim isTargetDevice()");
        boolean z = false;
        if (!new File("/data/system/.aasa/AASApolicy/ASKS_SPAM_CONFIG.xml").exists()) {
            Slog.i("ASKSManager", "setConfig does not exist.");
            return false;
        }
        try {
            FileInputStream fileInputStream =
                    new FileInputStream(
                            new File("/data/system/.aasa/AASApolicy/ASKS_SPAM_CONFIG.xml"));
            try {
                XmlPullParser newPullParser = Xml.newPullParser();
                newPullParser.setFeature(
                        "http://xmlpull.org/v1/doc/features.html#process-namespaces", false);
                newPullParser.setInput(fileInputStream, null);
                String str2 = null;
                for (int eventType = newPullParser.getEventType();
                        eventType != 1;
                        eventType = newPullParser.next()) {
                    String name = newPullParser.getName();
                    if (eventType == 2) {
                        if (name == null || !name.equalsIgnoreCase("config")) {
                            if (name != null
                                    && name.equalsIgnoreCase("value")
                                    && str2 != null
                                    && str2.equals("target_model")
                                    && (newPullParser.getAttributeValue(null, "name").equals(str)
                                            || newPullParser
                                                    .getAttributeValue(null, "name")
                                                    .equals("ALL"))) {
                                z = true;
                                break;
                            }
                        } else {
                            str2 = newPullParser.getAttributeValue(null, "name");
                        }
                    }
                }
                fileInputStream.close();
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return z;
    }

    public final boolean isTargetPackage(String str, ArrayList arrayList, ASKSSession aSKSSession) {
        Signature[] signatures;
        AndroidPackage androidPackage = getPackageManagerInternal().getPackage(str);
        if (androidPackage != null) {
            signatures = androidPackage.getSigningDetails().getSignatures();
        } else {
            if (aSKSSession == null || !str.equals(aSKSSession.mPackageName)) {
                return false;
            }
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    "isTargetPackage() : There is no information of ",
                    str,
                    ", check current session.",
                    "AASA_ASKSManager_RESTRICTED");
            signatures = aSKSSession.mSignature;
        }
        if (signatures != null && signatures.length > 0) {
            for (Signature signature : signatures) {
                String charsString = signature.toCharsString();
                for (int i = 0; i < arrayList.size(); i++) {
                    if (charsString.equalsIgnoreCase((String) arrayList.get(i))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final boolean isTrustedSelfUpdate(String str, String[] strArr) {
        PackageInfo packageInfo;
        ArrayList arrayList;
        Slog.i("PackageInformation", "check selfupdate..");
        HashMap hashMap = new HashMap();
        ArrayList arrayList2 = new ArrayList();
        getASKSDataFromXML(32, hashMap);
        if (hashMap.containsKey("CHECK")
                && (arrayList = (ArrayList) hashMap.get("CHECK")) != null) {
            for (int i = 0; i < strArr.length; i++) {
                if (arrayList.contains(strArr[i])) {
                    arrayList2.add(strArr[i]);
                    if (isDevDevice()) {
                        DeviceIdleController$$ExternalSyntheticOutline0.m(
                                new StringBuilder("adding pem:"), strArr[i], "PackageInformation");
                    }
                }
            }
        }
        if (arrayList2.size() <= 0) {
            return true;
        }
        Slog.i("PackageInformation", "check change of pem");
        try {
            Context context = this.mContext;
            if (context == null
                    || context.getPackageManager() == null
                    || (packageInfo = this.mContext.getPackageManager().getPackageInfo(str, 4096))
                            == null) {
                return true;
            }
            if (packageInfo.requestedPermissions == null) {
                Slog.i("PackageInformation", "self requestedPermissions is null");
                return true;
            }
            for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                String str2 = (String) arrayList2.get(i2);
                int i3 = 0;
                int i4 = 0;
                while (true) {
                    if (i3 >= packageInfo.requestedPermissions.length) {
                        break;
                    }
                    if (isDevDevice()) {
                        Slog.i(
                                "PackageInformation",
                                "permission:" + packageInfo.requestedPermissions[i3]);
                    }
                    if (!str2.equals(packageInfo.requestedPermissions[i3])) {
                        i4++;
                        i3++;
                    } else if (isDevDevice()) {
                        Slog.i("PackageInformation", "installed app already has " + str2);
                    } else {
                        Slog.i(
                                "PackageInformation",
                                "The target perm is included in the installed app.");
                    }
                }
                if (i4 == packageInfo.requestedPermissions.length) {
                    if (isDevDevice()) {
                        Slog.i(
                                "PackageInformation",
                                "installed app does not have " + str2 + " :" + i4);
                    } else {
                        Slog.i(
                                "PackageInformation",
                                "The installed app doesn't have target permission.");
                    }
                    return false;
                }
            }
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Slog.i("PackageInformation", "self :" + e);
            return true;
        }
    }

    public final boolean isTrustedStore(String str, int i) {
        HashMap hashMap = new HashMap();
        String str2 = "PackageInformation";
        HermesService$3$$ExternalSyntheticOutline0.m(i, "userId :", "PackageInformation");
        if (Settings.Secure.getInt(
                        this.mContext.getContentResolver(), "rampart_blocked_unknown_apps", 0)
                == 1) {
            if ("CN".equalsIgnoreCase(SemCscFeature.getInstance().getString("CountryISO"))) {
                getASKSDataFromXML(42, hashMap);
            } else {
                getASKSDataFromXML(41, hashMap);
            }
            str2 = "RAMPARTPackageInformation";
        } else {
            ArrayList targetNodeName =
                    getTargetNodeName(SystemProperties.get("ro.product.model", "Unknown"));
            if (targetNodeName == null || !targetNodeName.contains("TRUSTEDSTORE")) {
                Slog.e("PackageInformation", "skip TS..");
                return true;
            }
            getASKSDataFromXML(35, hashMap);
        }
        if (hashMap.size() >= 1) {
            return isTrustedStoreCheck(str2, hashMap, str, i);
        }
        Slog.i(str2, "skip TS due to non policy");
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0029  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isTrustedStoreCheck(
            java.lang.String r7, java.util.HashMap r8, java.lang.String r9, int r10) {
        /*
            Method dump skipped, instructions count: 374
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.asks.ASKSManagerService.isTrustedStoreCheck(java.lang.String,"
                    + " java.util.HashMap, java.lang.String, int):boolean");
    }

    public final boolean isUnknownApps(String str, Signature[] signatureArr) {
        enforceSystemOrRoot("Only the system can claim isUnknownApps");
        if (!this.ASKS_UNKNOWN_LIMIT_CALLPEM) {
            return false;
        }
        Slog.i("PackageInformation", "checking limitCallPem....(endCalling)");
        Slog.i("PackageInformation", "isUnknownApp " + str);
        if (str == null || str.isEmpty() || signatureArr == null) {
            Slog.e("PackageInformation", "packageName or hashedSignature is null!!");
            return false;
        }
        HashMap hashMap = new HashMap();
        getInstalledAppsDataFromXML(null, hashMap);
        if (hashMap.isEmpty()) {
            Slog.e("PackageInformation", "installedList is null");
            return false;
        }
        ArrayList installedAppsDataFromXML = getInstalledAppsDataFromXML("overlay", null);
        if (installedAppsDataFromXML == null || installedAppsDataFromXML.isEmpty()) {
            Slog.e("PackageInformation", "overlayList is null");
            return false;
        }
        if (!installedAppsDataFromXML.contains(str)) {
            Slog.e("PackageInformation", "packageName is not exist in overlayList");
        } else if (hashMap.containsKey(str)) {
            try {
                String sigHash = getSigHash(signatureArr[0]);
                if (hashMap.size() > 0
                        && sigHash.equals(((InstalledAppInfo) hashMap.get(str)).signature)) {
                    Slog.i("PackageInformation", "isUnknownApp is true");
                    return true;
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        } else {
            Slog.e("PackageInformation", "packageName is not exist in installedUnknownList");
        }
        return false;
    }

    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        try {
            return super.onTransact(i, parcel, parcel2, i2);
        } catch (RuntimeException e) {
            if (!(e instanceof SecurityException) && !(e instanceof IllegalArgumentException)) {
                Slog.wtf("ASKSManager", "ASKS Manager Crash", e);
            }
            throw e;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:131:0x0147  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0152  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void postASKSsetup(java.lang.String r18, java.lang.String r19, int r20) {
        /*
            Method dump skipped, instructions count: 860
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.asks.ASKSManagerService.postASKSsetup(java.lang.String,"
                    + " java.lang.String, int):void");
    }

    public final void printCurInfo(CURSTATUS curstatus, CURPARAM curparam) {
        Slog.i("PackageInformation", "pkg:" + curparam.packageName);
        Slog.i("PackageInformation", "-- initiating :" + curparam.initiatingPackageName);
        Slog.i("PackageInformation", "-- originating :" + curparam.originatingPackageName);
        StringBuilder sb = new StringBuilder("-- sdkVersion :");
        int i = curparam.sdkVersion;
        sb.append(i);
        Slog.i("PackageInformation", sb.toString());
        Slog.i("PackageInformation", "-- ASKS Version : " + mASKSPolicyVersion);
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                new StringBuilder("-- device "), curstatus.target_model, "PackageInformation");
        if (curstatus.isDevDevice) {
            if (curstatus.isCheckZipFormat) {
                Slog.d("PackageInformation", "enable CheckZipFormat");
            } else {
                Slog.d("PackageInformation", "disable CheckZipFormat");
            }
            if (this.ASKS_UNKNOWN_LIMIT_CALLPEM) {
                Slog.d("PackageInformation", "enable limitCallPem");
            } else {
                Slog.d("PackageInformation", "disable limitCallPem");
            }
            if (curstatus.isLimitOnlyKorMCC) {
                Slog.d("PackageInformation", "enable isOnlyKorMCC");
            } else {
                Slog.d("PackageInformation", "disable isOnlyKorMCC");
            }
            if (curstatus.isTabletExcepted) {
                Slog.d("PackageInformation", "enable Mobile Option");
            } else {
                Slog.d("PackageInformation", "disable Mobile Option");
            }
            if (curstatus.isLocUrlCase) {
                Slog.d("PackageInformation", "-- download Url :" + curparam.downloadUrl);
                Slog.d("PackageInformation", "-- Domain :" + curparam.domain);
                Slog.d("PackageInformation", "-- IP :" + curstatus.isIP);
                BootReceiver$$ExternalSyntheticOutline0.m(
                        new StringBuilder("-- DH :"), curparam.hashDomain, "PackageInformation");
            }
            BootReceiver$$ExternalSyntheticOutline0.m(
                    new StringBuilder("-- referral Url : "),
                    curparam.referralUrl,
                    "PackageInformation");
            int i2 = 0;
            while (true) {
                Signature[] signatureArr = curparam.signatures;
                String str = null;
                if (i2 >= signatureArr.length) {
                    break;
                }
                try {
                    str = getSigHash(signatureArr[i2]);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                StringBuilder sb2 = new StringBuilder("DEBUG pkg:");
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        i2, curparam.packageName, " sig [", "]::", sb2);
                sb2.append(str);
                Slog.d("PackageInformation", sb2.toString());
                i2++;
            }
            StringBuilder sb3 = new StringBuilder("DEBUG pkg:");
            sb3.append(curparam.packageName);
            sb3.append(" pkgNameHash::");
            BootReceiver$$ExternalSyntheticOutline0.m(
                    sb3, curparam.pkgNameHash, "PackageInformation");
            try {
                String convertToHex = convertToHex(getApkFileHashBytes(curparam.baseCodePath));
                if (!convertToHex.equals("null")) {
                    Slog.d(
                            "PackageInformation",
                            "DEBUG pkg:" + curparam.packageName + " apkFileHash::" + convertToHex);
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            Slog.d("PackageInformation", "DEBUG pkg:" + curparam.packageName + " api::" + i);
            for (int i3 = 0; i3 < curparam.permList.length; i3++) {
                Slog.d("PackageInformation", "DEBUG pem:" + curparam.permList[i3]);
            }
            if (curparam.servicePermList != null) {
                for (int i4 = 0; i4 < curparam.servicePermList.length; i4++) {
                    Slog.d(
                            "PackageInformation",
                            "DEBUG servicePem:" + curparam.servicePermList[i4]);
                }
            }
            ArrayList installedAppsDataFromXML =
                    getInstalledAppsDataFromXML("isInstalledList", null);
            if (installedAppsDataFromXML != null) {
                Slog.d(
                        "PackageInformation",
                        "DEBUG isInstalledList " + installedAppsDataFromXML.toString());
            }
            ArrayList installedAppsDataFromXML2 =
                    getInstalledAppsDataFromXML("requestInstallerZip", null);
            if (installedAppsDataFromXML2 != null) {
                Slog.d(
                        "PackageInformation",
                        "DEBUG requestInstallerZip " + installedAppsDataFromXML2.toString());
            }
            ArrayList installedAppsDataFromXML3 = getInstalledAppsDataFromXML("overlay", null);
            if (installedAppsDataFromXML3 != null) {
                Slog.d(
                        "PackageInformation",
                        "DEBUG overlay " + installedAppsDataFromXML3.toString());
            }
            ArrayList installedAppsDataFromXML4 = getInstalledAppsDataFromXML("blockExecute", null);
            if (installedAppsDataFromXML4 != null) {
                Slog.d(
                        "PackageInformation",
                        "DEBUG blockExecute " + installedAppsDataFromXML4.toString());
            }
            ArrayList installedAppsDataFromXML5 = getInstalledAppsDataFromXML("allowExecute", null);
            if (installedAppsDataFromXML5 != null) {
                Slog.d(
                        "PackageInformation",
                        "DEBUG allowExecute " + installedAppsDataFromXML5.toString());
            }
            ArrayList installedAppsDataFromXML6 = getInstalledAppsDataFromXML("initType", null);
            if (installedAppsDataFromXML6 != null) {
                Slog.d(
                        "PackageInformation",
                        "DEBUG initType " + installedAppsDataFromXML6.toString());
            }
            ArrayList installedAppsDataFromXML7 =
                    getInstalledAppsDataFromXML("accessibility", null);
            if (installedAppsDataFromXML7 != null) {
                Slog.d(
                        "PackageInformation",
                        "DEBUG accessibility " + installedAppsDataFromXML7.toString());
            }
            ArrayList installedAppsDataFromXML8 =
                    getInstalledAppsDataFromXML("hasReqInstallPEM", null);
            if (installedAppsDataFromXML8 != null) {
                Slog.d(
                        "PackageInformation",
                        "DEBUG hasReqInstallPEM " + installedAppsDataFromXML8.toString());
            }
        }
    }

    public final String readASKSFiles(String str, String str2) {
        enforceSystemOrRoot("Only the system can claim readASKSFiles");
        File file = new File(str, str2);
        if (!file.exists()) {
            return "No file exists";
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    for (int read = fileInputStream.read();
                            read != -1;
                            read = fileInputStream.read()) {
                        byteArrayOutputStream.write(read);
                    }
                    String byteArrayOutputStream2 =
                            byteArrayOutputStream.toString(Charset.defaultCharset());
                    byteArrayOutputStream.close();
                    fileInputStream.close();
                    return byteArrayOutputStream2;
                } finally {
                }
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public final void readPackage(XmlPullParser xmlPullParser) {
        String attributeValue = xmlPullParser.getAttributeValue(null, "name");
        this.mASKSStates.put(attributeValue, new ASKSState());
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && xmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                String name = xmlPullParser.getName();
                if (name.equals("restrict")) {
                    ASKSState aSKSState = (ASKSState) this.mASKSStates.get(attributeValue);
                    if (aSKSState != null) {
                        Restrict restrict = new Restrict();
                        readRestrictRule(xmlPullParser, restrict, null);
                        aSKSState.restrict = restrict;
                    }
                } else if (name.equals("emmode")) {
                    ASKSState aSKSState2 = (ASKSState) this.mASKSStates.get(attributeValue);
                    if (aSKSState2 != null) {
                        aSKSState2.emMode =
                                Integer.decode(xmlPullParser.getAttributeValue(null, "value"))
                                        .intValue();
                    }
                } else if (name.equals("delete")) {
                    ASKSState aSKSState3 = (ASKSState) this.mASKSStates.get(attributeValue);
                    if (aSKSState3 != null) {
                        String attributeValue2 = xmlPullParser.getAttributeValue(null, "version");
                        String attributeValue3 = xmlPullParser.getAttributeValue(null, "datelimit");
                        if (attributeValue2 != null && attributeValue3 != null) {
                            Deletable deletable = new Deletable();
                            deletable.mVersion = attributeValue2;
                            deletable.mDatelimit = attributeValue3;
                            aSKSState3.deletable = deletable;
                        }
                    }
                } else {
                    Slog.w(
                            "ASKSManager",
                            "Unknown element under <pkg>: " + xmlPullParser.getName());
                    XmlUtils.skipCurrentTag(xmlPullParser);
                }
            }
        }
    }

    public final void refreshInstalledUnknownList_NEW() {
        HashMap hashMap = new HashMap();
        getInstalledAppsDataFromXML(null, hashMap);
        if (hashMap.isEmpty()) {
            Slog.w("PackageInformation", "installedUnknownList is null");
            return;
        }
        ArrayList targetNodeName =
                getTargetNodeName(SystemProperties.get("ro.product.model", "Unknown"));
        if (targetNodeName != null && targetNodeName.size() > 0) {
            HashMap checkPolicyFileWithDelta = checkPolicyFileWithDelta(targetNodeName, 27);
            for (Map.Entry entry : hashMap.entrySet()) {
                InstalledAppInfo installedAppInfo = (InstalledAppInfo) entry.getValue();
                String str = (String) entry.getKey();
                if (installedAppInfo != null && str != null) {
                    String sHA256ForPkgName = getSHA256ForPkgName(str);
                    if (checkPolicyFileWithDelta.containsKey(installedAppInfo.signature)) {
                        StringBuilder sb = new StringBuilder("try to check ");
                        sb.append(str);
                        DeviceIdleController$$ExternalSyntheticOutline0.m(
                                sb,
                                isDevDevice()
                                        ? ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                                " hash:", sHA256ForPkgName)
                                        : "",
                                "PackageInformation");
                        UnknownStore unknownStore =
                                (UnknownStore)
                                        checkPolicyFileWithDelta.get(installedAppInfo.signature);
                        if (unknownStore != null) {
                            compareAttributeValue(unknownStore, installedAppInfo);
                        }
                    } else if (checkPolicyFileWithDelta.containsKey("ALL")) {
                        StringBuilder sb2 = new StringBuilder("try to check(ALL) ");
                        sb2.append(str);
                        sb2.append(
                                isDevDevice()
                                        ? ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                                                " hash:", sHA256ForPkgName)
                                        : "");
                        Slog.i("PackageInformation", sb2.toString());
                        UnknownStore unknownStore2 =
                                (UnknownStore) checkPolicyFileWithDelta.get("ALL");
                        if (unknownStore2 != null) {
                            compareAttributeValue(unknownStore2, installedAppInfo);
                        }
                    }
                }
            }
        }
        if (isDevDevice()) {
            Slog.d("PackageInformation", "ASKS Unknown List  NEW: " + hashMap.keySet().toString());
        }
        if ("true".equals(SystemProperties.get("ro.build.official.release", "false"))) {
            ArrayList installedAppsDataFromXML =
                    getInstalledAppsDataFromXML("isInstalledList", null);
            InstalledAppInfo installedAppInfo2 = new InstalledAppInfo();
            if (installedAppsDataFromXML == null || installedAppsDataFromXML.isEmpty()) {
                return;
            }
            int i = 0;
            while (i < installedAppsDataFromXML.size()) {
                String str2 = (String) installedAppsDataFromXML.get(i);
                int i2 = i;
                installedAppInfo2.set(str2, null, null, null, null, null, null, null, null, null);
                try {
                    this.mContext.getPackageManager().getPackageInfo(str2, 134217728);
                } catch (PackageManager.NameNotFoundException e) {
                    Slog.e("PackageInformation", "ERROR:: Abnormal App : " + e);
                    setDataToDeviceForModifyUnknownApp(3, installedAppInfo2);
                }
                i = i2 + 1;
            }
        }
    }

    public final void setASKSPolicyVersion(String str) {
        enforceSystemOrRoot("Only the system can claim setASKSPolicyVersion");
        try {
            if (Integer.parseInt(str)
                    > Integer.parseInt(SystemProperties.get("security.ASKS.policy_version"))) {
                SystemProperties.set("security.ASKS.policy_version", str);
            }
        } catch (NumberFormatException e) {
            Slog.d("AASA_ASKSManager", "setASKSPolicyVersion() : Numberformat exception " + e);
        }
    }

    public final void setSafeInstallSAInfoForTrustStore(
            String str, String str2, String str3, String str4) {
        HashMap hashMap = new HashMap();
        hashMap.put("packageName", str);
        hashMap.put("signature", str2);
        hashMap.put("policy", "block");
        hashMap.put("module", "AutoBlocker");
        hashMap.put("initiatingPackageName", str3);
        hashMap.put("originatingPackageName", str4);
        setSafeInstallSALog(new SafeInstallSAInfo("ts_sts", 0L, hashMap));
    }

    public final void setSafeInstallSAInfoForUpdatePolicy(String str, String str2) {
        HashMap hashMap = new HashMap();
        hashMap.put("using", str);
        hashMap.put("asksVersion", str2);
        setSafeInstallSALog(new SafeInstallSAInfo("updatePolicy", 9014L, hashMap));
    }

    public final void setSafeInstallSALog(SafeInstallSAInfo safeInstallSAInfo) {
        Bundle m142m =
                AccountManagerService$$ExternalSyntheticOutline0.m142m(
                        "tracking_id", "4B5-399-9751101");
        String str = safeInstallSAInfo.eventName;
        if (str == null) {
            str = "";
        }
        m142m.putString(LauncherConfigurationInternal.KEY_FEATURE_INT, str);
        m142m.putString("pkg_name", "com.samsung.aasaservice");
        m142m.putString("type", "ev");
        Long l = safeInstallSAInfo.eventValue;
        m142m.putString("value", l != null ? String.valueOf(l) : "");
        try {
            HashMap hashMap = safeInstallSAInfo.customDimensionMap;
            if (hashMap != null) {
                m142m.putSerializable("dimension", hashMap);
            }
        } catch (ClassCastException e) {
            Slog.e("PackageInformation", "ClassCastException: " + e.getMessage());
        }
        try {
            final Intent intent = new Intent();
            intent.setAction("com.sec.android.diagmonagent.intent.USE_APP_FEATURE_SURVEY");
            intent.setPackage("com.sec.android.diagmonagent");
            intent.putExtras(m142m);
            if (this.mContext != null) {
                new Handler(Looper.getMainLooper())
                        .post(
                                new Runnable() { // from class:
                                                 // com.android.server.asks.ASKSManagerService$$ExternalSyntheticLambda1
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        ASKSManagerService aSKSManagerService =
                                                ASKSManagerService.this;
                                        aSKSManagerService.mContext.sendBroadcast(intent);
                                    }
                                });
            } else {
                Slog.e("PackageInformation", "Context is null, failed to send SA");
            }
        } catch (Exception e2) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    e2, new StringBuilder("Exception: "), "PackageInformation");
        }
    }

    public final void setTrustTimebyStatusChanged() {
        String[] trustedFile;
        Slog.d("AASA_ASKSManager_SECURETIME", "setTrustTimebyStatusChanged : ");
        if (this.mContext == null) {
            Slog.d("AASA_ASKSManager_SECURETIME", "mContext is null. ");
            return;
        }
        if (hasTrustedTime()
                && (trustedFile = getTrustedFile()) != null
                && trustedFile.length == 3
                && trustedFile[0].equals("0")) {
            updateTrustedFile();
            return;
        }
        int checkNetworkConnection = checkNetworkConnection(this.mContext);
        if (!isAutoTimeEnabled(this.mContext) || checkNetworkConnection <= 0) {
            updateTrustedFile();
        } else {
            setTrustedFile(0, System.currentTimeMillis(), SystemClock.elapsedRealtime());
        }
    }

    public final void systemReady() {
        enforceSystemOrRoot("Only the system can claim the system is ready");
        checkDeletableListForASKS();
        SystemProperties.set("security.ASKS.rufs_enable", String.valueOf(true));
        updateSmsFilterFeatures();
        long j = SystemProperties.getLong("ro.build.date.utc", -1L) * 1000;
        if (j < 0) {
            SystemProperties.set("security.ASKS.expiration_date", Long.toString(j));
            return;
        }
        Date date = new Date(j);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, 3);
        SystemProperties.set(
                "security.ASKS.expiration_date",
                new SimpleDateFormat("yyyyMMdd").format(calendar.getTime()));
    }

    /* JADX WARN: Code restructure failed: missing block: B:126:0x00c4, code lost:

       if (r13 == 4) goto L39;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean updateRestrictRule(
            com.android.server.asks.ASKSManagerService.ASKSSession r17) {
        /*
            Method dump skipped, instructions count: 578
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.asks.ASKSManagerService.updateRestrictRule(com.android.server.asks.ASKSManagerService$ASKSSession):boolean");
    }

    public final void updateRestrictedTargetPackages() {
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = (HashMap) this.mASKSStates.clone();
        for (Map.Entry entry : hashMap2.entrySet()) {
            ASKSState aSKSState = (ASKSState) hashMap2.get(entry.getKey());
            if (aSKSState.restrict != null) {
                hashMap.put((String) entry.getKey(), aSKSState.restrict.mType);
            } else if (aSKSState.emMode != -1) {
                hashMap.put((String) entry.getKey(), "DENY");
            }
        }
        ASKSManager.updateRestrictedTargetPackages(hashMap);
    }

    public final void updateTokenValue(
            String str, CURPARAM curparam, CURSTATUS curstatus, boolean z) {
        HashMap hashMap = new HashMap();
        getInstalledAppsDataFromXML(null, hashMap);
        if (hashMap.containsKey(str)) {
            InstalledAppInfo installedAppInfo = (InstalledAppInfo) hashMap.get(str);
            int i = installedAppInfo.execute.equalsIgnoreCase("block") ? 504 : 505;
            String str2 = installedAppInfo.installAuthority;
            if (str2 != null) {
                if (str2.equalsIgnoreCase("none")) {
                    Slog.i("PackageInformation", "[Token] not updateTokenValue : default ");
                    return;
                }
                Slog.i("PackageInformation", "[Token] updateTokenValue isGlobal : " + z);
                RETVALUE retvalue = new RETVALUE();
                retvalue.set(4, 0, 0, i, "", "", null);
                if (!z) {
                    Slog.i("PackageInformation", "[Token] updateTokenValue kor: setInfo ");
                    addUnknownAppList(
                            curparam.packageName,
                            curparam.signatures,
                            retvalue,
                            installedAppInfo.initType,
                            curstatus.isLocZipCase,
                            curstatus.isLocAccessibilityCase,
                            curstatus.hasReqInstallPEM,
                            "none",
                            "none");
                    return;
                }
                Slog.i(
                        "PackageInformation",
                        "[Token] updateTokenValue global: "
                                + installedAppInfo.installAuthority
                                + " isGlobal : "
                                + z
                                + " pem : "
                                + curstatus.hasReqInstallPEM
                                + "packagenName : "
                                + curparam.packageName);
                InstalledAppInfo installedAppInfo2 = new InstalledAppInfo();
                if (this.installedAppInfoToStore != null) {
                    Slog.i("PackageInformation", "[Token] updateTokenValue global getInfo ");
                    InstalledAppInfo installedAppInfo3 = this.installedAppInfoToStore;
                    installedAppInfo3.installAuthority = "none";
                    installedAppInfo3.installAuthorityDate = "none";
                    return;
                }
                Slog.i("PackageInformation", "[Token] updateTokenValue global: setInfo ");
                installedAppInfo2.set(
                        str,
                        installedAppInfo.signature,
                        "allow",
                        "allow",
                        "true",
                        installedAppInfo.initType,
                        "true",
                        installedAppInfo.hasReqInstallPEM,
                        "none",
                        "none");
                installedAppInfo2.initPkg = installedAppInfo.initPkg;
                this.installedAppInfoToStore = installedAppInfo2;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:265:0x01c4, code lost:

       if (com.android.server.pm.PackageManagerServiceUtils.compareSignatures(r5.getSigningDetails(), r3) == 0) goto L70;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0414 A[Catch: all -> 0x031e, IOException -> 0x0322, XmlPullParserException -> 0x0325, TryCatch #15 {all -> 0x031e, blocks: (B:91:0x02da, B:92:0x02e5, B:98:0x02f8, B:141:0x030d, B:143:0x0317, B:144:0x0328, B:148:0x0332, B:151:0x0341, B:152:0x0347, B:154:0x034d, B:155:0x0354, B:157:0x035d, B:158:0x0364, B:160:0x036d, B:162:0x0372, B:167:0x0381, B:168:0x038c, B:170:0x0395, B:104:0x03a4, B:132:0x03b2, B:135:0x03b9, B:137:0x03c6, B:138:0x03df, B:106:0x03e7, B:120:0x0414, B:124:0x0434, B:125:0x0439, B:127:0x03fd, B:174:0x0465, B:181:0x046e), top: B:90:0x02da }] */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0432  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x03c6 A[Catch: all -> 0x031e, IOException -> 0x0322, XmlPullParserException -> 0x0325, TryCatch #15 {all -> 0x031e, blocks: (B:91:0x02da, B:92:0x02e5, B:98:0x02f8, B:141:0x030d, B:143:0x0317, B:144:0x0328, B:148:0x0332, B:151:0x0341, B:152:0x0347, B:154:0x034d, B:155:0x0354, B:157:0x035d, B:158:0x0364, B:160:0x036d, B:162:0x0372, B:167:0x0381, B:168:0x038c, B:170:0x0395, B:104:0x03a4, B:132:0x03b2, B:135:0x03b9, B:137:0x03c6, B:138:0x03df, B:106:0x03e7, B:120:0x0414, B:124:0x0434, B:125:0x0439, B:127:0x03fd, B:174:0x0465, B:181:0x046e), top: B:90:0x02da }] */
    /* JADX WARN: Removed duplicated region for block: B:138:0x03df A[Catch: all -> 0x031e, IOException -> 0x0322, XmlPullParserException -> 0x0325, TryCatch #15 {all -> 0x031e, blocks: (B:91:0x02da, B:92:0x02e5, B:98:0x02f8, B:141:0x030d, B:143:0x0317, B:144:0x0328, B:148:0x0332, B:151:0x0341, B:152:0x0347, B:154:0x034d, B:155:0x0354, B:157:0x035d, B:158:0x0364, B:160:0x036d, B:162:0x0372, B:167:0x0381, B:168:0x038c, B:170:0x0395, B:104:0x03a4, B:132:0x03b2, B:135:0x03b9, B:137:0x03c6, B:138:0x03df, B:106:0x03e7, B:120:0x0414, B:124:0x0434, B:125:0x0439, B:127:0x03fd, B:174:0x0465, B:181:0x046e), top: B:90:0x02da }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0543  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x02a1  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x02c3  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0483  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x048d  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x02cf A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r10v13 */
    /* JADX WARN: Type inference failed for: r10v14 */
    /* JADX WARN: Type inference failed for: r10v15 */
    /* JADX WARN: Type inference failed for: r10v16 */
    /* JADX WARN: Type inference failed for: r10v17 */
    /* JADX WARN: Type inference failed for: r10v19 */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v21 */
    /* JADX WARN: Type inference failed for: r10v3 */
    /* JADX WARN: Type inference failed for: r10v4 */
    /* JADX WARN: Type inference failed for: r2v30 */
    /* JADX WARN: Type inference failed for: r2v31 */
    /* JADX WARN: Type inference failed for: r2v38 */
    /* JADX WARN: Type inference failed for: r2v61 */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v50 */
    /* JADX WARN: Type inference failed for: r6v58 */
    /* JADX WARN: Type inference failed for: r6v61 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int verifyASKStokenForPackage(
            java.lang.String r20,
            java.lang.String r21,
            long r22,
            android.content.pm.Signature[] r24,
            java.lang.String r25,
            java.lang.String r26,
            boolean r27) {
        /*
            Method dump skipped, instructions count: 1369
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.asks.ASKSManagerService.verifyASKStokenForPackage(java.lang.String,"
                    + " java.lang.String, long, android.content.pm.Signature[], java.lang.String,"
                    + " java.lang.String, boolean):int");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(12:3|4|5|(2:6|7)|(3:437|438|(7:440|10|11|(1:13)(1:426)|14|15|(2:17|18)(2:20|(5:417|418|419|420|422)(9:24|25|26|27|28|29|30|31|(1:33)(13:34|35|(2:37|(2:39|(1:41)(1:42))(1:389))(1:(2:391|(1:(2:397|398)(1:396))(1:399))(1:400))|43|(2:46|44)|47|48|(1:388)(1:54)|55|(1:387)(2:59|(1:386)(1:63))|64|65|(21:376|(2:385|72)|73|74|(1:358)(1:78)|79|(1:357)(2:83|(5:85|86|(2:350|351)(2:89|(3:91|(2:93|(2:95|(3:97|98|99)(4:245|(3:248|(2:251|252)(1:250)|246)|254|99))(2:255|(3:257|(3:260|(3:263|98|99)(1:262)|258)|264)(0)))(4:265|(2:266|(2:268|(2:271|272)(1:270))(2:300|301))|273|(2:275|(2:277|(2:279|99)(3:280|(3:283|(2:286|287)(1:285)|281)|288))(2:289|(3:291|(3:294|(4:297|298|98|99)(1:296)|292)|299)(0)))(0))|(1:101))(2:302|(6:304|(2:305|(2:307|(2:310|311)(1:309))(2:348|349))|312|(2:314|(2:316|(3:318|(3:321|(4:324|325|98|99)(1:323)|319)|326)(2:327|(2:328|(1:335)(2:330|(2:333|334)(1:332)))))(2:336|(3:338|(3:341|(4:344|345|98|99)(1:343)|339)|346)(0)))(0)|347|(0))(0)))|253|(0))(1:356))|102|(1:244)(6:106|107|108|(1:110)|111|112)|113|(4:117|118|119|(2:121|122)(2:123|(2:125|126)))|129|130|(3:134|135|(2:137|(1:142)(1:141))(7:143|144|145|146|(1:156)(1:150)|(1:154)|155))|168|169|(3:173|174|175)|179|180|(4:184|185|(1:235)(2:189|(1:234)(2:193|(1:232)(13:197|198|199|200|201|202|203|204|205|(1:213)|214|(1:222)|223)))|233)|(2:237|238)(2:239|240))(2:69|(24:71|72|73|74|(1:76)|358|79|(1:81)|357|102|(1:104)|244|113|(5:115|117|118|119|(0)(0))|129|130|(4:132|134|135|(0)(0))|168|169|(4:171|173|174|175)|179|180|(6:182|184|185|(1:187)|235|233)|(0)(0))(25:359|360|(1:362)(2:363|(2:369|(1:375)(2:373|374))(2:367|368))|73|74|(0)|358|79|(0)|357|102|(0)|244|113|(0)|129|130|(0)|168|169|(0)|179|180|(0)|(0)(0))))))))|9|10|11|(0)(0)|14|15|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:427:0x009e, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:428:0x009f, code lost:

       r3 = r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:430:0x00e7, code lost:

       if (r13 == null) goto L38;
    */
    /* JADX WARN: Code restructure failed: missing block: B:431:0x00d0, code lost:

       r15 = null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:432:0x00cd, code lost:

       r13.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:433:0x009b, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:434:0x009c, code lost:

       r3 = r0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:436:0x00cb, code lost:

       if (r13 == null) goto L38;
    */
    /* JADX WARN: Removed duplicated region for block: B:101:0x04b4 A[Catch: XmlPullParserException -> 0x017c, NumberFormatException -> 0x0182, IOException -> 0x0186, TRY_ENTER, TryCatch #4 {NumberFormatException -> 0x0182, blocks: (B:29:0x0115, B:31:0x0126, B:34:0x012d, B:42:0x014b, B:43:0x021e, B:44:0x0227, B:46:0x022d, B:48:0x0237, B:50:0x0256, B:52:0x0260, B:54:0x026d, B:55:0x0284, B:57:0x028a, B:59:0x0294, B:61:0x029a, B:63:0x02a4, B:64:0x02d2, B:67:0x02de, B:69:0x02e8, B:72:0x0348, B:74:0x034b, B:76:0x0351, B:78:0x035b, B:79:0x0369, B:81:0x036f, B:85:0x037c, B:89:0x038a, B:91:0x03af, B:93:0x03b5, B:95:0x03bd, B:101:0x04b4, B:102:0x04c7, B:104:0x04cd, B:106:0x04d7, B:113:0x0527, B:115:0x052d, B:117:0x0537, B:127:0x0582, B:130:0x058a, B:132:0x0590, B:134:0x059a, B:137:0x05a8, B:139:0x05d3, B:141:0x05dd, B:142:0x05ea, B:143:0x05f4, B:145:0x0603, B:146:0x0607, B:148:0x060d, B:150:0x0617, B:152:0x062d, B:154:0x0637, B:155:0x0654, B:156:0x0621, B:169:0x065e, B:171:0x0664, B:173:0x066e, B:177:0x069e, B:180:0x06a6, B:182:0x06ac, B:184:0x06b6, B:187:0x06c0, B:189:0x06ca, B:191:0x06d2, B:193:0x06dc, B:195:0x06e4, B:197:0x06ee, B:199:0x070d, B:201:0x0712, B:205:0x073b, B:207:0x0741, B:209:0x074b, B:211:0x0753, B:213:0x075d, B:214:0x076f, B:216:0x0777, B:218:0x0781, B:220:0x0789, B:222:0x0793, B:223:0x07a5, B:226:0x0731, B:232:0x07af, B:233:0x07b4, B:234:0x07b7, B:235:0x07bd, B:237:0x07c5, B:239:0x07cc, B:242:0x0515, B:244:0x051d, B:246:0x03c2, B:248:0x03c5, B:250:0x03cf, B:255:0x03d5, B:258:0x03df, B:260:0x03e2, B:262:0x03eb, B:266:0x03ef, B:268:0x03f2, B:275:0x0404, B:277:0x040d, B:281:0x0412, B:283:0x0415, B:285:0x041f, B:289:0x0422, B:292:0x042c, B:294:0x042f, B:296:0x0438, B:270:0x03fd, B:302:0x043b, B:305:0x0446, B:307:0x0449, B:314:0x045b, B:316:0x0464, B:319:0x0469, B:321:0x046c, B:323:0x0476, B:328:0x047a, B:330:0x047d, B:332:0x0487, B:336:0x048c, B:339:0x0496, B:341:0x0499, B:343:0x04a3, B:309:0x0454, B:351:0x04ac, B:356:0x04bc, B:359:0x02f5, B:362:0x02ff, B:363:0x0303, B:365:0x0309, B:367:0x030d, B:369:0x0315, B:371:0x031b, B:373:0x031f, B:375:0x0327, B:376:0x032d, B:385:0x0343, B:386:0x02aa, B:387:0x02cf, B:389:0x018c, B:391:0x01be, B:394:0x01f1, B:397:0x01fa, B:400:0x0204), top: B:28:0x0115, outer: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:104:0x04cd A[Catch: XmlPullParserException -> 0x017c, NumberFormatException -> 0x0182, IOException -> 0x0186, TryCatch #4 {NumberFormatException -> 0x0182, blocks: (B:29:0x0115, B:31:0x0126, B:34:0x012d, B:42:0x014b, B:43:0x021e, B:44:0x0227, B:46:0x022d, B:48:0x0237, B:50:0x0256, B:52:0x0260, B:54:0x026d, B:55:0x0284, B:57:0x028a, B:59:0x0294, B:61:0x029a, B:63:0x02a4, B:64:0x02d2, B:67:0x02de, B:69:0x02e8, B:72:0x0348, B:74:0x034b, B:76:0x0351, B:78:0x035b, B:79:0x0369, B:81:0x036f, B:85:0x037c, B:89:0x038a, B:91:0x03af, B:93:0x03b5, B:95:0x03bd, B:101:0x04b4, B:102:0x04c7, B:104:0x04cd, B:106:0x04d7, B:113:0x0527, B:115:0x052d, B:117:0x0537, B:127:0x0582, B:130:0x058a, B:132:0x0590, B:134:0x059a, B:137:0x05a8, B:139:0x05d3, B:141:0x05dd, B:142:0x05ea, B:143:0x05f4, B:145:0x0603, B:146:0x0607, B:148:0x060d, B:150:0x0617, B:152:0x062d, B:154:0x0637, B:155:0x0654, B:156:0x0621, B:169:0x065e, B:171:0x0664, B:173:0x066e, B:177:0x069e, B:180:0x06a6, B:182:0x06ac, B:184:0x06b6, B:187:0x06c0, B:189:0x06ca, B:191:0x06d2, B:193:0x06dc, B:195:0x06e4, B:197:0x06ee, B:199:0x070d, B:201:0x0712, B:205:0x073b, B:207:0x0741, B:209:0x074b, B:211:0x0753, B:213:0x075d, B:214:0x076f, B:216:0x0777, B:218:0x0781, B:220:0x0789, B:222:0x0793, B:223:0x07a5, B:226:0x0731, B:232:0x07af, B:233:0x07b4, B:234:0x07b7, B:235:0x07bd, B:237:0x07c5, B:239:0x07cc, B:242:0x0515, B:244:0x051d, B:246:0x03c2, B:248:0x03c5, B:250:0x03cf, B:255:0x03d5, B:258:0x03df, B:260:0x03e2, B:262:0x03eb, B:266:0x03ef, B:268:0x03f2, B:275:0x0404, B:277:0x040d, B:281:0x0412, B:283:0x0415, B:285:0x041f, B:289:0x0422, B:292:0x042c, B:294:0x042f, B:296:0x0438, B:270:0x03fd, B:302:0x043b, B:305:0x0446, B:307:0x0449, B:314:0x045b, B:316:0x0464, B:319:0x0469, B:321:0x046c, B:323:0x0476, B:328:0x047a, B:330:0x047d, B:332:0x0487, B:336:0x048c, B:339:0x0496, B:341:0x0499, B:343:0x04a3, B:309:0x0454, B:351:0x04ac, B:356:0x04bc, B:359:0x02f5, B:362:0x02ff, B:363:0x0303, B:365:0x0309, B:367:0x030d, B:369:0x0315, B:371:0x031b, B:373:0x031f, B:375:0x0327, B:376:0x032d, B:385:0x0343, B:386:0x02aa, B:387:0x02cf, B:389:0x018c, B:391:0x01be, B:394:0x01f1, B:397:0x01fa, B:400:0x0204), top: B:28:0x0115, outer: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:115:0x052d A[Catch: XmlPullParserException -> 0x017c, NumberFormatException -> 0x0182, IOException -> 0x0186, TryCatch #4 {NumberFormatException -> 0x0182, blocks: (B:29:0x0115, B:31:0x0126, B:34:0x012d, B:42:0x014b, B:43:0x021e, B:44:0x0227, B:46:0x022d, B:48:0x0237, B:50:0x0256, B:52:0x0260, B:54:0x026d, B:55:0x0284, B:57:0x028a, B:59:0x0294, B:61:0x029a, B:63:0x02a4, B:64:0x02d2, B:67:0x02de, B:69:0x02e8, B:72:0x0348, B:74:0x034b, B:76:0x0351, B:78:0x035b, B:79:0x0369, B:81:0x036f, B:85:0x037c, B:89:0x038a, B:91:0x03af, B:93:0x03b5, B:95:0x03bd, B:101:0x04b4, B:102:0x04c7, B:104:0x04cd, B:106:0x04d7, B:113:0x0527, B:115:0x052d, B:117:0x0537, B:127:0x0582, B:130:0x058a, B:132:0x0590, B:134:0x059a, B:137:0x05a8, B:139:0x05d3, B:141:0x05dd, B:142:0x05ea, B:143:0x05f4, B:145:0x0603, B:146:0x0607, B:148:0x060d, B:150:0x0617, B:152:0x062d, B:154:0x0637, B:155:0x0654, B:156:0x0621, B:169:0x065e, B:171:0x0664, B:173:0x066e, B:177:0x069e, B:180:0x06a6, B:182:0x06ac, B:184:0x06b6, B:187:0x06c0, B:189:0x06ca, B:191:0x06d2, B:193:0x06dc, B:195:0x06e4, B:197:0x06ee, B:199:0x070d, B:201:0x0712, B:205:0x073b, B:207:0x0741, B:209:0x074b, B:211:0x0753, B:213:0x075d, B:214:0x076f, B:216:0x0777, B:218:0x0781, B:220:0x0789, B:222:0x0793, B:223:0x07a5, B:226:0x0731, B:232:0x07af, B:233:0x07b4, B:234:0x07b7, B:235:0x07bd, B:237:0x07c5, B:239:0x07cc, B:242:0x0515, B:244:0x051d, B:246:0x03c2, B:248:0x03c5, B:250:0x03cf, B:255:0x03d5, B:258:0x03df, B:260:0x03e2, B:262:0x03eb, B:266:0x03ef, B:268:0x03f2, B:275:0x0404, B:277:0x040d, B:281:0x0412, B:283:0x0415, B:285:0x041f, B:289:0x0422, B:292:0x042c, B:294:0x042f, B:296:0x0438, B:270:0x03fd, B:302:0x043b, B:305:0x0446, B:307:0x0449, B:314:0x045b, B:316:0x0464, B:319:0x0469, B:321:0x046c, B:323:0x0476, B:328:0x047a, B:330:0x047d, B:332:0x0487, B:336:0x048c, B:339:0x0496, B:341:0x0499, B:343:0x04a3, B:309:0x0454, B:351:0x04ac, B:356:0x04bc, B:359:0x02f5, B:362:0x02ff, B:363:0x0303, B:365:0x0309, B:367:0x030d, B:369:0x0315, B:371:0x031b, B:373:0x031f, B:375:0x0327, B:376:0x032d, B:385:0x0343, B:386:0x02aa, B:387:0x02cf, B:389:0x018c, B:391:0x01be, B:394:0x01f1, B:397:0x01fa, B:400:0x0204), top: B:28:0x0115, outer: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0563 A[Catch: XmlPullParserException -> 0x017c, IOException -> 0x0186, NumberFormatException -> 0x0582, TryCatch #3 {NumberFormatException -> 0x0582, blocks: (B:119:0x0551, B:121:0x0563, B:123:0x056a, B:125:0x0578), top: B:118:0x0551 }] */
    /* JADX WARN: Removed duplicated region for block: B:123:0x056a A[Catch: XmlPullParserException -> 0x017c, IOException -> 0x0186, NumberFormatException -> 0x0582, TryCatch #3 {NumberFormatException -> 0x0582, blocks: (B:119:0x0551, B:121:0x0563, B:123:0x056a, B:125:0x0578), top: B:118:0x0551 }] */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0590 A[Catch: XmlPullParserException -> 0x017c, NumberFormatException -> 0x0182, IOException -> 0x0186, TryCatch #4 {NumberFormatException -> 0x0182, blocks: (B:29:0x0115, B:31:0x0126, B:34:0x012d, B:42:0x014b, B:43:0x021e, B:44:0x0227, B:46:0x022d, B:48:0x0237, B:50:0x0256, B:52:0x0260, B:54:0x026d, B:55:0x0284, B:57:0x028a, B:59:0x0294, B:61:0x029a, B:63:0x02a4, B:64:0x02d2, B:67:0x02de, B:69:0x02e8, B:72:0x0348, B:74:0x034b, B:76:0x0351, B:78:0x035b, B:79:0x0369, B:81:0x036f, B:85:0x037c, B:89:0x038a, B:91:0x03af, B:93:0x03b5, B:95:0x03bd, B:101:0x04b4, B:102:0x04c7, B:104:0x04cd, B:106:0x04d7, B:113:0x0527, B:115:0x052d, B:117:0x0537, B:127:0x0582, B:130:0x058a, B:132:0x0590, B:134:0x059a, B:137:0x05a8, B:139:0x05d3, B:141:0x05dd, B:142:0x05ea, B:143:0x05f4, B:145:0x0603, B:146:0x0607, B:148:0x060d, B:150:0x0617, B:152:0x062d, B:154:0x0637, B:155:0x0654, B:156:0x0621, B:169:0x065e, B:171:0x0664, B:173:0x066e, B:177:0x069e, B:180:0x06a6, B:182:0x06ac, B:184:0x06b6, B:187:0x06c0, B:189:0x06ca, B:191:0x06d2, B:193:0x06dc, B:195:0x06e4, B:197:0x06ee, B:199:0x070d, B:201:0x0712, B:205:0x073b, B:207:0x0741, B:209:0x074b, B:211:0x0753, B:213:0x075d, B:214:0x076f, B:216:0x0777, B:218:0x0781, B:220:0x0789, B:222:0x0793, B:223:0x07a5, B:226:0x0731, B:232:0x07af, B:233:0x07b4, B:234:0x07b7, B:235:0x07bd, B:237:0x07c5, B:239:0x07cc, B:242:0x0515, B:244:0x051d, B:246:0x03c2, B:248:0x03c5, B:250:0x03cf, B:255:0x03d5, B:258:0x03df, B:260:0x03e2, B:262:0x03eb, B:266:0x03ef, B:268:0x03f2, B:275:0x0404, B:277:0x040d, B:281:0x0412, B:283:0x0415, B:285:0x041f, B:289:0x0422, B:292:0x042c, B:294:0x042f, B:296:0x0438, B:270:0x03fd, B:302:0x043b, B:305:0x0446, B:307:0x0449, B:314:0x045b, B:316:0x0464, B:319:0x0469, B:321:0x046c, B:323:0x0476, B:328:0x047a, B:330:0x047d, B:332:0x0487, B:336:0x048c, B:339:0x0496, B:341:0x0499, B:343:0x04a3, B:309:0x0454, B:351:0x04ac, B:356:0x04bc, B:359:0x02f5, B:362:0x02ff, B:363:0x0303, B:365:0x0309, B:367:0x030d, B:369:0x0315, B:371:0x031b, B:373:0x031f, B:375:0x0327, B:376:0x032d, B:385:0x0343, B:386:0x02aa, B:387:0x02cf, B:389:0x018c, B:391:0x01be, B:394:0x01f1, B:397:0x01fa, B:400:0x0204), top: B:28:0x0115, outer: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:137:0x05a8 A[Catch: XmlPullParserException -> 0x017c, NumberFormatException -> 0x0182, IOException -> 0x0186, TRY_ENTER, TryCatch #4 {NumberFormatException -> 0x0182, blocks: (B:29:0x0115, B:31:0x0126, B:34:0x012d, B:42:0x014b, B:43:0x021e, B:44:0x0227, B:46:0x022d, B:48:0x0237, B:50:0x0256, B:52:0x0260, B:54:0x026d, B:55:0x0284, B:57:0x028a, B:59:0x0294, B:61:0x029a, B:63:0x02a4, B:64:0x02d2, B:67:0x02de, B:69:0x02e8, B:72:0x0348, B:74:0x034b, B:76:0x0351, B:78:0x035b, B:79:0x0369, B:81:0x036f, B:85:0x037c, B:89:0x038a, B:91:0x03af, B:93:0x03b5, B:95:0x03bd, B:101:0x04b4, B:102:0x04c7, B:104:0x04cd, B:106:0x04d7, B:113:0x0527, B:115:0x052d, B:117:0x0537, B:127:0x0582, B:130:0x058a, B:132:0x0590, B:134:0x059a, B:137:0x05a8, B:139:0x05d3, B:141:0x05dd, B:142:0x05ea, B:143:0x05f4, B:145:0x0603, B:146:0x0607, B:148:0x060d, B:150:0x0617, B:152:0x062d, B:154:0x0637, B:155:0x0654, B:156:0x0621, B:169:0x065e, B:171:0x0664, B:173:0x066e, B:177:0x069e, B:180:0x06a6, B:182:0x06ac, B:184:0x06b6, B:187:0x06c0, B:189:0x06ca, B:191:0x06d2, B:193:0x06dc, B:195:0x06e4, B:197:0x06ee, B:199:0x070d, B:201:0x0712, B:205:0x073b, B:207:0x0741, B:209:0x074b, B:211:0x0753, B:213:0x075d, B:214:0x076f, B:216:0x0777, B:218:0x0781, B:220:0x0789, B:222:0x0793, B:223:0x07a5, B:226:0x0731, B:232:0x07af, B:233:0x07b4, B:234:0x07b7, B:235:0x07bd, B:237:0x07c5, B:239:0x07cc, B:242:0x0515, B:244:0x051d, B:246:0x03c2, B:248:0x03c5, B:250:0x03cf, B:255:0x03d5, B:258:0x03df, B:260:0x03e2, B:262:0x03eb, B:266:0x03ef, B:268:0x03f2, B:275:0x0404, B:277:0x040d, B:281:0x0412, B:283:0x0415, B:285:0x041f, B:289:0x0422, B:292:0x042c, B:294:0x042f, B:296:0x0438, B:270:0x03fd, B:302:0x043b, B:305:0x0446, B:307:0x0449, B:314:0x045b, B:316:0x0464, B:319:0x0469, B:321:0x046c, B:323:0x0476, B:328:0x047a, B:330:0x047d, B:332:0x0487, B:336:0x048c, B:339:0x0496, B:341:0x0499, B:343:0x04a3, B:309:0x0454, B:351:0x04ac, B:356:0x04bc, B:359:0x02f5, B:362:0x02ff, B:363:0x0303, B:365:0x0309, B:367:0x030d, B:369:0x0315, B:371:0x031b, B:373:0x031f, B:375:0x0327, B:376:0x032d, B:385:0x0343, B:386:0x02aa, B:387:0x02cf, B:389:0x018c, B:391:0x01be, B:394:0x01f1, B:397:0x01fa, B:400:0x0204), top: B:28:0x0115, outer: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0088 A[Catch: all -> 0x0072, SecurityException -> 0x009b, IOException -> 0x009e, TRY_LEAVE, TryCatch #12 {all -> 0x0072, blocks: (B:438:0x0068, B:11:0x0080, B:13:0x0088, B:435:0x00bc, B:429:0x00d8), top: B:2:0x005b }] */
    /* JADX WARN: Removed duplicated region for block: B:143:0x05f4 A[Catch: XmlPullParserException -> 0x017c, NumberFormatException -> 0x0182, IOException -> 0x0186, TRY_LEAVE, TryCatch #4 {NumberFormatException -> 0x0182, blocks: (B:29:0x0115, B:31:0x0126, B:34:0x012d, B:42:0x014b, B:43:0x021e, B:44:0x0227, B:46:0x022d, B:48:0x0237, B:50:0x0256, B:52:0x0260, B:54:0x026d, B:55:0x0284, B:57:0x028a, B:59:0x0294, B:61:0x029a, B:63:0x02a4, B:64:0x02d2, B:67:0x02de, B:69:0x02e8, B:72:0x0348, B:74:0x034b, B:76:0x0351, B:78:0x035b, B:79:0x0369, B:81:0x036f, B:85:0x037c, B:89:0x038a, B:91:0x03af, B:93:0x03b5, B:95:0x03bd, B:101:0x04b4, B:102:0x04c7, B:104:0x04cd, B:106:0x04d7, B:113:0x0527, B:115:0x052d, B:117:0x0537, B:127:0x0582, B:130:0x058a, B:132:0x0590, B:134:0x059a, B:137:0x05a8, B:139:0x05d3, B:141:0x05dd, B:142:0x05ea, B:143:0x05f4, B:145:0x0603, B:146:0x0607, B:148:0x060d, B:150:0x0617, B:152:0x062d, B:154:0x0637, B:155:0x0654, B:156:0x0621, B:169:0x065e, B:171:0x0664, B:173:0x066e, B:177:0x069e, B:180:0x06a6, B:182:0x06ac, B:184:0x06b6, B:187:0x06c0, B:189:0x06ca, B:191:0x06d2, B:193:0x06dc, B:195:0x06e4, B:197:0x06ee, B:199:0x070d, B:201:0x0712, B:205:0x073b, B:207:0x0741, B:209:0x074b, B:211:0x0753, B:213:0x075d, B:214:0x076f, B:216:0x0777, B:218:0x0781, B:220:0x0789, B:222:0x0793, B:223:0x07a5, B:226:0x0731, B:232:0x07af, B:233:0x07b4, B:234:0x07b7, B:235:0x07bd, B:237:0x07c5, B:239:0x07cc, B:242:0x0515, B:244:0x051d, B:246:0x03c2, B:248:0x03c5, B:250:0x03cf, B:255:0x03d5, B:258:0x03df, B:260:0x03e2, B:262:0x03eb, B:266:0x03ef, B:268:0x03f2, B:275:0x0404, B:277:0x040d, B:281:0x0412, B:283:0x0415, B:285:0x041f, B:289:0x0422, B:292:0x042c, B:294:0x042f, B:296:0x0438, B:270:0x03fd, B:302:0x043b, B:305:0x0446, B:307:0x0449, B:314:0x045b, B:316:0x0464, B:319:0x0469, B:321:0x046c, B:323:0x0476, B:328:0x047a, B:330:0x047d, B:332:0x0487, B:336:0x048c, B:339:0x0496, B:341:0x0499, B:343:0x04a3, B:309:0x0454, B:351:0x04ac, B:356:0x04bc, B:359:0x02f5, B:362:0x02ff, B:363:0x0303, B:365:0x0309, B:367:0x030d, B:369:0x0315, B:371:0x031b, B:373:0x031f, B:375:0x0327, B:376:0x032d, B:385:0x0343, B:386:0x02aa, B:387:0x02cf, B:389:0x018c, B:391:0x01be, B:394:0x01f1, B:397:0x01fa, B:400:0x0204), top: B:28:0x0115, outer: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0664 A[Catch: XmlPullParserException -> 0x017c, NumberFormatException -> 0x0182, IOException -> 0x0186, TryCatch #4 {NumberFormatException -> 0x0182, blocks: (B:29:0x0115, B:31:0x0126, B:34:0x012d, B:42:0x014b, B:43:0x021e, B:44:0x0227, B:46:0x022d, B:48:0x0237, B:50:0x0256, B:52:0x0260, B:54:0x026d, B:55:0x0284, B:57:0x028a, B:59:0x0294, B:61:0x029a, B:63:0x02a4, B:64:0x02d2, B:67:0x02de, B:69:0x02e8, B:72:0x0348, B:74:0x034b, B:76:0x0351, B:78:0x035b, B:79:0x0369, B:81:0x036f, B:85:0x037c, B:89:0x038a, B:91:0x03af, B:93:0x03b5, B:95:0x03bd, B:101:0x04b4, B:102:0x04c7, B:104:0x04cd, B:106:0x04d7, B:113:0x0527, B:115:0x052d, B:117:0x0537, B:127:0x0582, B:130:0x058a, B:132:0x0590, B:134:0x059a, B:137:0x05a8, B:139:0x05d3, B:141:0x05dd, B:142:0x05ea, B:143:0x05f4, B:145:0x0603, B:146:0x0607, B:148:0x060d, B:150:0x0617, B:152:0x062d, B:154:0x0637, B:155:0x0654, B:156:0x0621, B:169:0x065e, B:171:0x0664, B:173:0x066e, B:177:0x069e, B:180:0x06a6, B:182:0x06ac, B:184:0x06b6, B:187:0x06c0, B:189:0x06ca, B:191:0x06d2, B:193:0x06dc, B:195:0x06e4, B:197:0x06ee, B:199:0x070d, B:201:0x0712, B:205:0x073b, B:207:0x0741, B:209:0x074b, B:211:0x0753, B:213:0x075d, B:214:0x076f, B:216:0x0777, B:218:0x0781, B:220:0x0789, B:222:0x0793, B:223:0x07a5, B:226:0x0731, B:232:0x07af, B:233:0x07b4, B:234:0x07b7, B:235:0x07bd, B:237:0x07c5, B:239:0x07cc, B:242:0x0515, B:244:0x051d, B:246:0x03c2, B:248:0x03c5, B:250:0x03cf, B:255:0x03d5, B:258:0x03df, B:260:0x03e2, B:262:0x03eb, B:266:0x03ef, B:268:0x03f2, B:275:0x0404, B:277:0x040d, B:281:0x0412, B:283:0x0415, B:285:0x041f, B:289:0x0422, B:292:0x042c, B:294:0x042f, B:296:0x0438, B:270:0x03fd, B:302:0x043b, B:305:0x0446, B:307:0x0449, B:314:0x045b, B:316:0x0464, B:319:0x0469, B:321:0x046c, B:323:0x0476, B:328:0x047a, B:330:0x047d, B:332:0x0487, B:336:0x048c, B:339:0x0496, B:341:0x0499, B:343:0x04a3, B:309:0x0454, B:351:0x04ac, B:356:0x04bc, B:359:0x02f5, B:362:0x02ff, B:363:0x0303, B:365:0x0309, B:367:0x030d, B:369:0x0315, B:371:0x031b, B:373:0x031f, B:375:0x0327, B:376:0x032d, B:385:0x0343, B:386:0x02aa, B:387:0x02cf, B:389:0x018c, B:391:0x01be, B:394:0x01f1, B:397:0x01fa, B:400:0x0204), top: B:28:0x0115, outer: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x06ac A[Catch: XmlPullParserException -> 0x017c, NumberFormatException -> 0x0182, IOException -> 0x0186, TryCatch #4 {NumberFormatException -> 0x0182, blocks: (B:29:0x0115, B:31:0x0126, B:34:0x012d, B:42:0x014b, B:43:0x021e, B:44:0x0227, B:46:0x022d, B:48:0x0237, B:50:0x0256, B:52:0x0260, B:54:0x026d, B:55:0x0284, B:57:0x028a, B:59:0x0294, B:61:0x029a, B:63:0x02a4, B:64:0x02d2, B:67:0x02de, B:69:0x02e8, B:72:0x0348, B:74:0x034b, B:76:0x0351, B:78:0x035b, B:79:0x0369, B:81:0x036f, B:85:0x037c, B:89:0x038a, B:91:0x03af, B:93:0x03b5, B:95:0x03bd, B:101:0x04b4, B:102:0x04c7, B:104:0x04cd, B:106:0x04d7, B:113:0x0527, B:115:0x052d, B:117:0x0537, B:127:0x0582, B:130:0x058a, B:132:0x0590, B:134:0x059a, B:137:0x05a8, B:139:0x05d3, B:141:0x05dd, B:142:0x05ea, B:143:0x05f4, B:145:0x0603, B:146:0x0607, B:148:0x060d, B:150:0x0617, B:152:0x062d, B:154:0x0637, B:155:0x0654, B:156:0x0621, B:169:0x065e, B:171:0x0664, B:173:0x066e, B:177:0x069e, B:180:0x06a6, B:182:0x06ac, B:184:0x06b6, B:187:0x06c0, B:189:0x06ca, B:191:0x06d2, B:193:0x06dc, B:195:0x06e4, B:197:0x06ee, B:199:0x070d, B:201:0x0712, B:205:0x073b, B:207:0x0741, B:209:0x074b, B:211:0x0753, B:213:0x075d, B:214:0x076f, B:216:0x0777, B:218:0x0781, B:220:0x0789, B:222:0x0793, B:223:0x07a5, B:226:0x0731, B:232:0x07af, B:233:0x07b4, B:234:0x07b7, B:235:0x07bd, B:237:0x07c5, B:239:0x07cc, B:242:0x0515, B:244:0x051d, B:246:0x03c2, B:248:0x03c5, B:250:0x03cf, B:255:0x03d5, B:258:0x03df, B:260:0x03e2, B:262:0x03eb, B:266:0x03ef, B:268:0x03f2, B:275:0x0404, B:277:0x040d, B:281:0x0412, B:283:0x0415, B:285:0x041f, B:289:0x0422, B:292:0x042c, B:294:0x042f, B:296:0x0438, B:270:0x03fd, B:302:0x043b, B:305:0x0446, B:307:0x0449, B:314:0x045b, B:316:0x0464, B:319:0x0469, B:321:0x046c, B:323:0x0476, B:328:0x047a, B:330:0x047d, B:332:0x0487, B:336:0x048c, B:339:0x0496, B:341:0x0499, B:343:0x04a3, B:309:0x0454, B:351:0x04ac, B:356:0x04bc, B:359:0x02f5, B:362:0x02ff, B:363:0x0303, B:365:0x0309, B:367:0x030d, B:369:0x0315, B:371:0x031b, B:373:0x031f, B:375:0x0327, B:376:0x032d, B:385:0x0343, B:386:0x02aa, B:387:0x02cf, B:389:0x018c, B:391:0x01be, B:394:0x01f1, B:397:0x01fa, B:400:0x0204), top: B:28:0x0115, outer: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:237:0x07c5 A[Catch: XmlPullParserException -> 0x017c, NumberFormatException -> 0x0182, IOException -> 0x0186, TryCatch #4 {NumberFormatException -> 0x0182, blocks: (B:29:0x0115, B:31:0x0126, B:34:0x012d, B:42:0x014b, B:43:0x021e, B:44:0x0227, B:46:0x022d, B:48:0x0237, B:50:0x0256, B:52:0x0260, B:54:0x026d, B:55:0x0284, B:57:0x028a, B:59:0x0294, B:61:0x029a, B:63:0x02a4, B:64:0x02d2, B:67:0x02de, B:69:0x02e8, B:72:0x0348, B:74:0x034b, B:76:0x0351, B:78:0x035b, B:79:0x0369, B:81:0x036f, B:85:0x037c, B:89:0x038a, B:91:0x03af, B:93:0x03b5, B:95:0x03bd, B:101:0x04b4, B:102:0x04c7, B:104:0x04cd, B:106:0x04d7, B:113:0x0527, B:115:0x052d, B:117:0x0537, B:127:0x0582, B:130:0x058a, B:132:0x0590, B:134:0x059a, B:137:0x05a8, B:139:0x05d3, B:141:0x05dd, B:142:0x05ea, B:143:0x05f4, B:145:0x0603, B:146:0x0607, B:148:0x060d, B:150:0x0617, B:152:0x062d, B:154:0x0637, B:155:0x0654, B:156:0x0621, B:169:0x065e, B:171:0x0664, B:173:0x066e, B:177:0x069e, B:180:0x06a6, B:182:0x06ac, B:184:0x06b6, B:187:0x06c0, B:189:0x06ca, B:191:0x06d2, B:193:0x06dc, B:195:0x06e4, B:197:0x06ee, B:199:0x070d, B:201:0x0712, B:205:0x073b, B:207:0x0741, B:209:0x074b, B:211:0x0753, B:213:0x075d, B:214:0x076f, B:216:0x0777, B:218:0x0781, B:220:0x0789, B:222:0x0793, B:223:0x07a5, B:226:0x0731, B:232:0x07af, B:233:0x07b4, B:234:0x07b7, B:235:0x07bd, B:237:0x07c5, B:239:0x07cc, B:242:0x0515, B:244:0x051d, B:246:0x03c2, B:248:0x03c5, B:250:0x03cf, B:255:0x03d5, B:258:0x03df, B:260:0x03e2, B:262:0x03eb, B:266:0x03ef, B:268:0x03f2, B:275:0x0404, B:277:0x040d, B:281:0x0412, B:283:0x0415, B:285:0x041f, B:289:0x0422, B:292:0x042c, B:294:0x042f, B:296:0x0438, B:270:0x03fd, B:302:0x043b, B:305:0x0446, B:307:0x0449, B:314:0x045b, B:316:0x0464, B:319:0x0469, B:321:0x046c, B:323:0x0476, B:328:0x047a, B:330:0x047d, B:332:0x0487, B:336:0x048c, B:339:0x0496, B:341:0x0499, B:343:0x04a3, B:309:0x0454, B:351:0x04ac, B:356:0x04bc, B:359:0x02f5, B:362:0x02ff, B:363:0x0303, B:365:0x0309, B:367:0x030d, B:369:0x0315, B:371:0x031b, B:373:0x031f, B:375:0x0327, B:376:0x032d, B:385:0x0343, B:386:0x02aa, B:387:0x02cf, B:389:0x018c, B:391:0x01be, B:394:0x01f1, B:397:0x01fa, B:400:0x0204), top: B:28:0x0115, outer: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:239:0x07cc A[Catch: XmlPullParserException -> 0x017c, NumberFormatException -> 0x0182, IOException -> 0x0186, TRY_LEAVE, TryCatch #4 {NumberFormatException -> 0x0182, blocks: (B:29:0x0115, B:31:0x0126, B:34:0x012d, B:42:0x014b, B:43:0x021e, B:44:0x0227, B:46:0x022d, B:48:0x0237, B:50:0x0256, B:52:0x0260, B:54:0x026d, B:55:0x0284, B:57:0x028a, B:59:0x0294, B:61:0x029a, B:63:0x02a4, B:64:0x02d2, B:67:0x02de, B:69:0x02e8, B:72:0x0348, B:74:0x034b, B:76:0x0351, B:78:0x035b, B:79:0x0369, B:81:0x036f, B:85:0x037c, B:89:0x038a, B:91:0x03af, B:93:0x03b5, B:95:0x03bd, B:101:0x04b4, B:102:0x04c7, B:104:0x04cd, B:106:0x04d7, B:113:0x0527, B:115:0x052d, B:117:0x0537, B:127:0x0582, B:130:0x058a, B:132:0x0590, B:134:0x059a, B:137:0x05a8, B:139:0x05d3, B:141:0x05dd, B:142:0x05ea, B:143:0x05f4, B:145:0x0603, B:146:0x0607, B:148:0x060d, B:150:0x0617, B:152:0x062d, B:154:0x0637, B:155:0x0654, B:156:0x0621, B:169:0x065e, B:171:0x0664, B:173:0x066e, B:177:0x069e, B:180:0x06a6, B:182:0x06ac, B:184:0x06b6, B:187:0x06c0, B:189:0x06ca, B:191:0x06d2, B:193:0x06dc, B:195:0x06e4, B:197:0x06ee, B:199:0x070d, B:201:0x0712, B:205:0x073b, B:207:0x0741, B:209:0x074b, B:211:0x0753, B:213:0x075d, B:214:0x076f, B:216:0x0777, B:218:0x0781, B:220:0x0789, B:222:0x0793, B:223:0x07a5, B:226:0x0731, B:232:0x07af, B:233:0x07b4, B:234:0x07b7, B:235:0x07bd, B:237:0x07c5, B:239:0x07cc, B:242:0x0515, B:244:0x051d, B:246:0x03c2, B:248:0x03c5, B:250:0x03cf, B:255:0x03d5, B:258:0x03df, B:260:0x03e2, B:262:0x03eb, B:266:0x03ef, B:268:0x03f2, B:275:0x0404, B:277:0x040d, B:281:0x0412, B:283:0x0415, B:285:0x041f, B:289:0x0422, B:292:0x042c, B:294:0x042f, B:296:0x0438, B:270:0x03fd, B:302:0x043b, B:305:0x0446, B:307:0x0449, B:314:0x045b, B:316:0x0464, B:319:0x0469, B:321:0x046c, B:323:0x0476, B:328:0x047a, B:330:0x047d, B:332:0x0487, B:336:0x048c, B:339:0x0496, B:341:0x0499, B:343:0x04a3, B:309:0x0454, B:351:0x04ac, B:356:0x04bc, B:359:0x02f5, B:362:0x02ff, B:363:0x0303, B:365:0x0309, B:367:0x030d, B:369:0x0315, B:371:0x031b, B:373:0x031f, B:375:0x0327, B:376:0x032d, B:385:0x0343, B:386:0x02aa, B:387:0x02cf, B:389:0x018c, B:391:0x01be, B:394:0x01f1, B:397:0x01fa, B:400:0x0204), top: B:28:0x0115, outer: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:426:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0351 A[Catch: XmlPullParserException -> 0x017c, NumberFormatException -> 0x0182, IOException -> 0x0186, TryCatch #4 {NumberFormatException -> 0x0182, blocks: (B:29:0x0115, B:31:0x0126, B:34:0x012d, B:42:0x014b, B:43:0x021e, B:44:0x0227, B:46:0x022d, B:48:0x0237, B:50:0x0256, B:52:0x0260, B:54:0x026d, B:55:0x0284, B:57:0x028a, B:59:0x0294, B:61:0x029a, B:63:0x02a4, B:64:0x02d2, B:67:0x02de, B:69:0x02e8, B:72:0x0348, B:74:0x034b, B:76:0x0351, B:78:0x035b, B:79:0x0369, B:81:0x036f, B:85:0x037c, B:89:0x038a, B:91:0x03af, B:93:0x03b5, B:95:0x03bd, B:101:0x04b4, B:102:0x04c7, B:104:0x04cd, B:106:0x04d7, B:113:0x0527, B:115:0x052d, B:117:0x0537, B:127:0x0582, B:130:0x058a, B:132:0x0590, B:134:0x059a, B:137:0x05a8, B:139:0x05d3, B:141:0x05dd, B:142:0x05ea, B:143:0x05f4, B:145:0x0603, B:146:0x0607, B:148:0x060d, B:150:0x0617, B:152:0x062d, B:154:0x0637, B:155:0x0654, B:156:0x0621, B:169:0x065e, B:171:0x0664, B:173:0x066e, B:177:0x069e, B:180:0x06a6, B:182:0x06ac, B:184:0x06b6, B:187:0x06c0, B:189:0x06ca, B:191:0x06d2, B:193:0x06dc, B:195:0x06e4, B:197:0x06ee, B:199:0x070d, B:201:0x0712, B:205:0x073b, B:207:0x0741, B:209:0x074b, B:211:0x0753, B:213:0x075d, B:214:0x076f, B:216:0x0777, B:218:0x0781, B:220:0x0789, B:222:0x0793, B:223:0x07a5, B:226:0x0731, B:232:0x07af, B:233:0x07b4, B:234:0x07b7, B:235:0x07bd, B:237:0x07c5, B:239:0x07cc, B:242:0x0515, B:244:0x051d, B:246:0x03c2, B:248:0x03c5, B:250:0x03cf, B:255:0x03d5, B:258:0x03df, B:260:0x03e2, B:262:0x03eb, B:266:0x03ef, B:268:0x03f2, B:275:0x0404, B:277:0x040d, B:281:0x0412, B:283:0x0415, B:285:0x041f, B:289:0x0422, B:292:0x042c, B:294:0x042f, B:296:0x0438, B:270:0x03fd, B:302:0x043b, B:305:0x0446, B:307:0x0449, B:314:0x045b, B:316:0x0464, B:319:0x0469, B:321:0x046c, B:323:0x0476, B:328:0x047a, B:330:0x047d, B:332:0x0487, B:336:0x048c, B:339:0x0496, B:341:0x0499, B:343:0x04a3, B:309:0x0454, B:351:0x04ac, B:356:0x04bc, B:359:0x02f5, B:362:0x02ff, B:363:0x0303, B:365:0x0309, B:367:0x030d, B:369:0x0315, B:371:0x031b, B:373:0x031f, B:375:0x0327, B:376:0x032d, B:385:0x0343, B:386:0x02aa, B:387:0x02cf, B:389:0x018c, B:391:0x01be, B:394:0x01f1, B:397:0x01fa, B:400:0x0204), top: B:28:0x0115, outer: #8 }] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x036f A[Catch: XmlPullParserException -> 0x017c, NumberFormatException -> 0x0182, IOException -> 0x0186, TryCatch #4 {NumberFormatException -> 0x0182, blocks: (B:29:0x0115, B:31:0x0126, B:34:0x012d, B:42:0x014b, B:43:0x021e, B:44:0x0227, B:46:0x022d, B:48:0x0237, B:50:0x0256, B:52:0x0260, B:54:0x026d, B:55:0x0284, B:57:0x028a, B:59:0x0294, B:61:0x029a, B:63:0x02a4, B:64:0x02d2, B:67:0x02de, B:69:0x02e8, B:72:0x0348, B:74:0x034b, B:76:0x0351, B:78:0x035b, B:79:0x0369, B:81:0x036f, B:85:0x037c, B:89:0x038a, B:91:0x03af, B:93:0x03b5, B:95:0x03bd, B:101:0x04b4, B:102:0x04c7, B:104:0x04cd, B:106:0x04d7, B:113:0x0527, B:115:0x052d, B:117:0x0537, B:127:0x0582, B:130:0x058a, B:132:0x0590, B:134:0x059a, B:137:0x05a8, B:139:0x05d3, B:141:0x05dd, B:142:0x05ea, B:143:0x05f4, B:145:0x0603, B:146:0x0607, B:148:0x060d, B:150:0x0617, B:152:0x062d, B:154:0x0637, B:155:0x0654, B:156:0x0621, B:169:0x065e, B:171:0x0664, B:173:0x066e, B:177:0x069e, B:180:0x06a6, B:182:0x06ac, B:184:0x06b6, B:187:0x06c0, B:189:0x06ca, B:191:0x06d2, B:193:0x06dc, B:195:0x06e4, B:197:0x06ee, B:199:0x070d, B:201:0x0712, B:205:0x073b, B:207:0x0741, B:209:0x074b, B:211:0x0753, B:213:0x075d, B:214:0x076f, B:216:0x0777, B:218:0x0781, B:220:0x0789, B:222:0x0793, B:223:0x07a5, B:226:0x0731, B:232:0x07af, B:233:0x07b4, B:234:0x07b7, B:235:0x07bd, B:237:0x07c5, B:239:0x07cc, B:242:0x0515, B:244:0x051d, B:246:0x03c2, B:248:0x03c5, B:250:0x03cf, B:255:0x03d5, B:258:0x03df, B:260:0x03e2, B:262:0x03eb, B:266:0x03ef, B:268:0x03f2, B:275:0x0404, B:277:0x040d, B:281:0x0412, B:283:0x0415, B:285:0x041f, B:289:0x0422, B:292:0x042c, B:294:0x042f, B:296:0x0438, B:270:0x03fd, B:302:0x043b, B:305:0x0446, B:307:0x0449, B:314:0x045b, B:316:0x0464, B:319:0x0469, B:321:0x046c, B:323:0x0476, B:328:0x047a, B:330:0x047d, B:332:0x0487, B:336:0x048c, B:339:0x0496, B:341:0x0499, B:343:0x04a3, B:309:0x0454, B:351:0x04ac, B:356:0x04bc, B:359:0x02f5, B:362:0x02ff, B:363:0x0303, B:365:0x0309, B:367:0x030d, B:369:0x0315, B:371:0x031b, B:373:0x031f, B:375:0x0327, B:376:0x032d, B:385:0x0343, B:386:0x02aa, B:387:0x02cf, B:389:0x018c, B:391:0x01be, B:394:0x01f1, B:397:0x01fa, B:400:0x0204), top: B:28:0x0115, outer: #8 }] */
    /* JADX WARN: Type inference failed for: r3v10 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* JADX WARN: Unreachable blocks removed: 2, instructions: 3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int verifyToken(
            com.android.server.asks.ASKSManagerService.ASKSSession r52,
            java.lang.String r53,
            java.lang.String r54,
            boolean r55,
            int r56) {
        /*
            Method dump skipped, instructions count: 2122
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.asks.ASKSManagerService.verifyToken(com.android.server.asks.ASKSManagerService$ASKSSession,"
                    + " java.lang.String, java.lang.String, boolean, int):int");
    }

    public final void writeState() {
        FileOutputStream startWrite;
        synchronized (this.mFile) {
            FileOutputStream fileOutputStream = null;
            try {
                try {
                    startWrite = this.mFile.startWrite();
                } catch (IOException e) {
                    e = e;
                }
                try {
                    try {
                        HashMap hashMap = (HashMap) this.mASKSStates.clone();
                        try {
                            FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
                            fastXmlSerializer.setOutput(startWrite, StandardCharsets.UTF_8.name());
                            fastXmlSerializer.startDocument((String) null, Boolean.TRUE);
                            fastXmlSerializer.startTag((String) null, "safeinstall");
                            fastXmlSerializer.attribute(
                                    (String) null, "delta", mASKSDeltaPolicyVersion);
                            fastXmlSerializer.endTag((String) null, "safeinstall");
                            fastXmlSerializer.startTag((String) null, "asks");
                            fastXmlSerializer.attribute(
                                    (String) null, "version", mASKSPolicyVersion);
                            for (String str : hashMap.keySet()) {
                                ASKSState aSKSState = (ASKSState) hashMap.get(str);
                                if (aSKSState != null) {
                                    if (aSKSState.restrict == null
                                            && aSKSState.emMode == -1
                                            && aSKSState.deletable == null) {}
                                    fastXmlSerializer.startTag((String) null, "package");
                                    fastXmlSerializer.attribute((String) null, "name", str);
                                    Restrict restrict = aSKSState.restrict;
                                    if (restrict != null) {
                                        fastXmlSerializer.startTag((String) null, "restrict");
                                        fastXmlSerializer.attribute(
                                                (String) null, "version", restrict.mVersion);
                                        fastXmlSerializer.attribute(
                                                (String) null, "type", restrict.mType);
                                        fastXmlSerializer.attribute(
                                                (String) null, "datelimit", restrict.mDatelimit);
                                        fastXmlSerializer.attribute(
                                                (String) null, "from", restrict.mFrom);
                                        if ("REVOKE".equals(restrict.mType)) {
                                            Iterator it = restrict.mPermissionList.iterator();
                                            while (it.hasNext()) {
                                                String str2 = (String) it.next();
                                                fastXmlSerializer.startTag(
                                                        (String) null, "permission");
                                                fastXmlSerializer.attribute(
                                                        (String) null, "value", str2);
                                                fastXmlSerializer.endTag(
                                                        (String) null, "permission");
                                            }
                                        }
                                        fastXmlSerializer.endTag((String) null, "restrict");
                                    }
                                    int i = aSKSState.emMode;
                                    if (i != -1) {
                                        String hexString = Integer.toHexString(i);
                                        fastXmlSerializer.startTag((String) null, "emmode");
                                        fastXmlSerializer.attribute(
                                                (String) null, "value", "0x" + hexString);
                                        fastXmlSerializer.endTag((String) null, "emmode");
                                    }
                                    Deletable deletable = aSKSState.deletable;
                                    if (deletable != null) {
                                        fastXmlSerializer.startTag((String) null, "delete");
                                        fastXmlSerializer.attribute(
                                                (String) null, "version", deletable.mVersion);
                                        fastXmlSerializer.attribute(
                                                (String) null, "datelimit", deletable.mDatelimit);
                                        fastXmlSerializer.endTag((String) null, "delete");
                                    }
                                    fastXmlSerializer.endTag((String) null, "package");
                                }
                            }
                            fastXmlSerializer.endTag((String) null, "asks");
                            fastXmlSerializer.endDocument();
                            this.mFile.finishWrite(startWrite);
                            SystemProperties.set(
                                    "security.ASKS.policy_version", mASKSPolicyVersion);
                        } catch (IOException e2) {
                            Slog.w(
                                    "AASA_ASKSManager",
                                    "Failed to write state, restoring backup",
                                    e2);
                            this.mFile.failWrite(startWrite);
                        }
                        if (startWrite != null) {
                            try {
                                startWrite.close();
                            } catch (IOException e3) {
                                e = e3;
                                e.printStackTrace();
                            }
                        }
                    } catch (Throwable th) {
                        th = th;
                        fileOutputStream = startWrite;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e4) {
                                e4.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (IOException e5) {
                    e = e5;
                    fileOutputStream = startWrite;
                    Slog.w("AASA_ASKSManager", "Failed to write state: " + e);
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e6) {
                            e = e6;
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
    }
}
