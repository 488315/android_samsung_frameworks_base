package com.android.server.asks;

import android.R;
import android.app.AppGlobals;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ASKSManager;
import android.content.pm.IASKSManager;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.IInstalld;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.Log;
import android.util.Slog;
import android.util.Xml;
import android.util.jar.StrictJarFile;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.util.FastPrintWriter;
import com.android.internal.util.FastXmlSerializer;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.LocalServices;
import com.android.server.asks.ADPContainer;
import com.android.server.pm.PackageManagerServiceUtils;
import com.android.server.pm.parsing.pkg.AndroidPackageUtils;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.PackageStateInternal;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.server.pm.install.UnknownSourceAppManager;
import com.samsung.android.service.EngineeringMode.EngineeringModeManager;
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
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes.dex */
public class ASKSManagerService extends IASKSManager.Stub {
    public static String mASKSDeltaPolicyVersion = "20241029";
    public static String mASKSPolicyVersion = "00000000";
    public boolean ASKS_UNKNOWN_LIMIT_CALLPEM;
    public InstalledAppInfo installedAppInfoToStore;
    public final Context mContext;
    public AtomicFile mFile;
    public PackageManagerInternal mPackageManagerInternal;
    public volatile boolean mSystemReady;
    public final String ADP_VERSION = "3.1";
    public final String ADP_POLICY_VERSION = "20230510";
    public final HashMap mASKSStates = new HashMap();
    public final HashMap mSessions = new HashMap();
    public boolean isFirstTime = true;
    public final String TAG_AASA = "AASA_ASKSManager";
    public final String TAG_ADP = "AASA_ASKSManager_ADP";
    public final String TAG_DELETABLE = "AASA_ASKSManager_DELETABLE";
    public final String TAG_EM = "AASA_ASKSManager_EM";
    public final String TAG_RESTRICTED = "AASA_ASKSManager_RESTRICTED";
    public final String TAG_RUFS = "AASA_ASKSManager_RUFS";
    public final String TAG_SECURETIME = "AASA_ASKSManager_SECURETIME";
    public final String TAG_UNKNOWN = "PackageInformation";
    public final String mUserVaultName = "AASA";
    public final int ASKS_UNKNOWN_TRUSTEDSTORE = 35;
    public final String ASKS_UNKNOWN_INSTALLER = "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_INSTALLER_NEW.xml";
    public final String ASKS_UNKNOWN_INSTALLER_ZIP = "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_INSTALLER_ZIP_NEW.xml";
    public final String ASKS_UNKNOWN_SA_REPORTED = "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_SA_REPORTED_NEW.xml";
    public final int AASA_CASE = 0;
    public final int ASKS_CASE = 1;
    public final int ADP_CASE = 2;
    public String ROOT_CERT_FILE = "/system/etc/ASKS_ROOT_1.crt";
    public String CA_CERT_PATH = "/data/system/.aasa/AASApolicy/ASKS_INTER_";
    public String CA_CERT_SYSTEM_PATH = "/system/etc/ASKS_INTER_";
    public String EE_CERT_FILE = "/system/etc/ASKS_EDGE_1.crt";
    public int TYPE_WIFI = 1;
    public int TYPE_MOBILE = 2;
    public int TYPE_NOT_CONNECTED = 0;
    public final String RESTRICTED_FROM_TOKEN = "Token";
    public final String RESTRICTED_FROM_POLICY = "Policy";
    public final int PROPERTY_ASKS_VERSION_ID = 1;
    public final boolean DEBUG_MODE = false;
    public boolean DEBUG_MODE_FOR_DEVELOPMENT = false;

    public int checkSecurityEnabled() {
        return 128;
    }

    public final String convertItoS(int i) {
        if (i == 0) {
            return "except";
        }
        if (i != 1) {
            if (i == 100) {
                return "warning";
            }
            if (i == 101) {
                return "warning_dev";
            }
            switch (i) {
                case 110:
                    return "warning0";
                case 111:
                    return "warning1";
                case 112:
                    return "warning2";
                case 113:
                    return "warning3";
                case 114:
                    return "warning4";
                default:
                    switch (i) {
                    }
                    return "except";
            }
        }
        return "block";
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [android.os.IBinder, com.android.server.asks.ASKSManagerService] */
    public static ASKSManagerService main(Context context) {
        Slog.i("ASKSManager", "main starts");
        ?? aSKSManagerService = new ASKSManagerService(context);
        ServiceManager.addService("asks", (IBinder) aSKSManagerService);
        Slog.i("ASKSManager", "main ends");
        return aSKSManagerService;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(14:0|1|(1:3)(1:44)|4|(5:30|31|(1:35)|36|(9:38|39|7|(1:9)|(1:11)|12|13|14|(1:25)(2:18|(2:20|21)(2:23|24))))|6|7|(0)|(0)|12|13|14|(2:16|25)(1:26)|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0140, code lost:
    
        r8 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0141, code lost:
    
        r8.printStackTrace();
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0132  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ASKSManagerService(Context context) {
        boolean z;
        ArrayList targetNodeName;
        this.ASKS_UNKNOWN_LIMIT_CALLPEM = false;
        this.mContext = context;
        SystemProperties.set("security.ASKS.version", "8.4");
        SystemProperties.set("security.ASKS.time_value", "00000000");
        SystemProperties.set("security.ASKS.policy_version", mASKSPolicyVersion);
        SystemProperties.set("security.ASKS.delta_policy_version", mASKSDeltaPolicyVersion);
        SystemProperties.set("security.ADP.version", "3.1");
        SystemProperties.set("security.ADP.policy_version", "20230510");
        SystemProperties.set("security.ASKS.smsfilter_enable", "true");
        SystemProperties.set("security.ASKS.smsfilter_target", "false");
        if ("KR".equalsIgnoreCase(SemCscFeature.getInstance().getString("CountryISO"))) {
            SystemProperties.set("security.ASKS.safeinstall.enable", "true");
            Slog.i("ASKSManager", "This is KR project. Enable SafeInstall.");
        } else {
            SystemProperties.set("security.ASKS.safeinstall.enable", "false");
        }
        File file = new File("/data/system/.aasa/asks.xml");
        if (!file.exists()) {
            try {
                if (file.getParentFile() != null && !file.getParentFile().exists()) {
                    file.getParentFile().mkdir();
                }
                if (!new PolicyConvert().convert(mASKSPolicyVersion)) {
                    file.createNewFile();
                    z = true;
                    this.mFile = new AtomicFile(file, "asks");
                    readState();
                    if (copyASKSpolicyFromSystem()) {
                        updateRestrictRule(null);
                        z = true;
                    }
                    if (z) {
                        writeState();
                    }
                    checkExistUnknownAppList();
                    updateRestrictedTargetPackages();
                    targetNodeName = getTargetNodeName(SystemProperties.get("ro.product.model", "Unknown"));
                    if (targetNodeName != null || targetNodeName.size() <= 0) {
                    }
                    if (targetNodeName.contains("CALLPEMLIMIT")) {
                        this.ASKS_UNKNOWN_LIMIT_CALLPEM = true;
                        Slog.i("AASA_ASKSManager", "enable LIMIT_CALLPEM");
                        return;
                    } else {
                        this.ASKS_UNKNOWN_LIMIT_CALLPEM = false;
                        Slog.i("AASA_ASKSManager", "disable LIMIT_CALLPEM");
                        return;
                    }
                }
            } catch (IOException e) {
                throw new SecurityException("cannot create the file even it does not exist", e);
            }
        }
        z = false;
        this.mFile = new AtomicFile(file, "asks");
        readState();
        if (copyASKSpolicyFromSystem()) {
        }
        if (z) {
        }
        checkExistUnknownAppList();
        updateRestrictedTargetPackages();
        targetNodeName = getTargetNodeName(SystemProperties.get("ro.product.model", "Unknown"));
        if (targetNodeName != null) {
        }
    }

    public final boolean isSubSystemUid(int i) {
        return i % 10000 == 1000;
    }

    public final void enforceSystemOrRoot(String str) {
        int callingUid = Binder.getCallingUid();
        if (Binder.getCallingPid() == Process.myPid() || callingUid == 0 || callingUid == 1000 || isSubSystemUid(callingUid)) {
            return;
        }
        throw new SecurityException(callingUid + " : " + str);
    }

    public final boolean enforceSystemOrRoot() {
        int callingUid = Binder.getCallingUid();
        return Binder.getCallingPid() == Process.myPid() || callingUid == 0 || callingUid == 1000 || isSubSystemUid(callingUid);
    }

    public final PackageManagerInternal getPackageManagerInternal() {
        if (this.mPackageManagerInternal == null) {
            this.mPackageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        }
        return this.mPackageManagerInternal;
    }

    public void systemReady() {
        enforceSystemOrRoot("Only the system can claim the system is ready");
        this.mSystemReady = true;
        checkDeletableListForASKS();
        SystemProperties.set("security.ASKS.rufs_enable", String.valueOf(true));
        updateSmsFilterFeatures();
        setExpirationDate();
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        try {
            return super.onTransact(i, parcel, parcel2, i2);
        } catch (RuntimeException e) {
            if (!(e instanceof SecurityException) && !(e instanceof IllegalArgumentException)) {
                Slog.wtf("ASKSManager", "ASKS Manager Crash", e);
            }
            throw e;
        }
    }

    public int verifyASKStokenForPackage(String str, String str2, long j, Signature[] signatureArr, String str3, String str4, boolean z) {
        enforceSystemOrRoot("Only the system can claim verifyASKStokenForPackage");
        if (this.isFirstTime) {
            this.isFirstTime = false;
            readyForBooting(this.mContext);
        }
        Slog.i("AASA_ASKSManager", "ASKS_VERSION: 8.4 ::" + mASKSPolicyVersion);
        Slog.i("AASA_ASKSManager", "initiating = " + str4 + ", installer = " + str3);
        String str5 = null;
        if (checkListForASKS(19, str, null) != -1) {
            try {
                str5 = getAdvancedHash(str2);
            } catch (IOException unused) {
            }
            if (checkListForASKS(19, str, str5) != -1) {
                return -7;
            }
        }
        ASKSSession openSession = openSession(str);
        if (str5 != null) {
            openSession.setPkgDigest(str5);
        }
        openSession.setSignature(signatureArr);
        int isSignatureMatched = isSignatureMatched(str, signatureArr);
        if (isSignatureMatched == -1) {
            return -1;
        }
        openSession.setASKSTarget(true);
        int parsePackageForASKS = parsePackageForASKS(openSession, str, str2, j, str3, str4, isSignatureMatched, z);
        if (parsePackageForASKS != -1) {
            closeSession(openSession, str);
        }
        return parsePackageForASKS;
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x01e0  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0212  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x022b  */
    /* JADX WARN: Removed duplicated region for block: B:59:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x01e8  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x01cf  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void postASKSsetup(String str, String str2, int i) {
        boolean z;
        enforceSystemOrRoot("Only the system can claim postASKSsetup");
        boolean z2 = true;
        boolean z3 = false;
        boolean z4 = i == UserHandle.getUserId(Process.myUid());
        ASKSSession aSKSSession = null;
        if (this.mSessions.containsKey(str)) {
            ASKSSession aSKSSession2 = (ASKSSession) this.mSessions.get(str);
            if (aSKSSession2.getRufsContainer() != null) {
                RUFSContainer rufsContainer = aSKSSession2.getRufsContainer();
                if (rufsContainer.getHasRUFSToken()) {
                    RuleUpdateForSecurity ruleUpdateForSecurity = new RuleUpdateForSecurity(rufsContainer);
                    Slog.i("AASA_ASKSManager_RUFS", "CountryISO :" + SemCscFeature.getInstance().getString("CountryISO"));
                    if ("KR".equalsIgnoreCase(SemCscFeature.getInstance().getString("CountryISO"))) {
                        Slog.d("AASA_ASKSManager_RUFS", "RUFS Force Enable");
                        z = true;
                    } else {
                        z = SystemProperties.getBoolean("security.ASKS.rufs_enable", true);
                    }
                    if (z) {
                        Slog.i("AASA_ASKSManager_RUFS", "TRY.........");
                        if (ruleUpdateForSecurity.isUpdatePolicy(SystemProperties.get("security.ASKS.policy_version"))) {
                            Slog.i("AASA_ASKSManager_RUFS", "!!path:" + rufsContainer.getPolicyPath());
                            if (ruleUpdateForSecurity.updatePolicy(str2, true)) {
                                Slog.i("AASA_ASKSManager_RUFS", "policy update from " + SystemProperties.get("security.ASKS.policy_version"));
                                String policyVersion = rufsContainer.getPolicyVersion();
                                mASKSPolicyVersion = policyVersion;
                                SystemProperties.set("security.ASKS.policy_version", policyVersion);
                                refreshInstalledUnknownList_NEW();
                                applyExecutePolicy();
                                SystemProperties.get("ro.product.model", "null");
                                Slog.i("AASA_ASKSManager_RUFS", "policy update to   " + SystemProperties.get("security.ASKS.policy_version"));
                                setSamsungAnalyticsLog("9014", "STORE", mASKSPolicyVersion);
                                updateSmsFilterFeatures();
                                z3 = true;
                            }
                        }
                    } else {
                        Slog.i("AASA_ASKSManager_RUFS", "RUFS is disabled");
                    }
                }
            }
            if (updateRestrictRule(aSKSSession2)) {
                z3 = true;
            }
            if (this.mASKSStates.containsKey(str) && z4) {
                ASKSState aSKSState = (ASKSState) this.mASKSStates.get(str);
                Restrict restrict = aSKSSession2.getRestrict();
                Restrict restrict2 = aSKSState.getRestrict();
                if (restrict != null && "Token".equals(restrict.getFrom())) {
                    Slog.d("AASA_ASKSManager_RESTRICTED", "postASKSsetup() : new restricted rule(" + aSKSSession2.getPackageName() + ") is updated from Token.");
                    aSKSState.setRestrict(restrict);
                } else {
                    if (restrict == null && restrict2 != null && "Token".equals(restrict2.getFrom())) {
                        Slog.d("AASA_ASKSManager_RESTRICTED", "updateRestictRule() : There is no restricted rule (" + aSKSSession2.getPackageName() + ") from Token. remove and check policy.");
                        aSKSState.setRestrict(null);
                        updateRestrictRule(aSKSSession2);
                    }
                    if (this.DEBUG_MODE_FOR_DEVELOPMENT) {
                        Slog.d("AASA_ASKSManager_RESTRICTED", aSKSState.toString());
                    }
                    if (aSKSSession2.getDeletable() == null) {
                        if (aSKSState.getDeletable() != null) {
                            try {
                                if (Integer.parseInt(aSKSSession2.getDeletable().getVersion()) >= Integer.parseInt(aSKSState.getDeletable().getVersion())) {
                                    aSKSState.setDeletable(aSKSSession2.getDeletable());
                                }
                            } catch (NumberFormatException unused) {
                            }
                            if (aSKSSession2.getEMMode() == -1) {
                                aSKSState.setEMMode(aSKSSession2.getEMMode());
                            } else {
                                z2 = z3;
                            }
                        } else {
                            aSKSState.setDeletable(aSKSSession2.getDeletable());
                        }
                    } else {
                        if (aSKSState.getDeletable() != null) {
                            aSKSState.setDeletable(null);
                        }
                        if (aSKSSession2.getEMMode() == -1) {
                        }
                    }
                    z3 = true;
                    if (aSKSSession2.getEMMode() == -1) {
                    }
                }
                z3 = true;
                if (this.DEBUG_MODE_FOR_DEVELOPMENT) {
                }
                if (aSKSSession2.getDeletable() == null) {
                }
                z3 = true;
                if (aSKSSession2.getEMMode() == -1) {
                }
            } else if (aSKSSession2.isASKSTarget() && aSKSSession2.hasValue() && z4) {
                this.mASKSStates.put(str, new ASKSState(aSKSSession2));
            } else {
                aSKSSession = aSKSSession2;
            }
            aSKSSession = aSKSSession2;
            ComparisonBeforeSetData(getInstalledAppInfoToStore(), str);
            closeSession(aSKSSession, str);
            if (z2) {
                writeState();
                updateRestrictedTargetPackages();
            }
            checkDeletableListForASKS();
            if ("true".equals(SystemProperties.get("ro.build.official.developer"))) {
                return;
            }
            updateASKSNotification();
            return;
        }
        z2 = z3;
        ComparisonBeforeSetData(getInstalledAppInfoToStore(), str);
        closeSession(aSKSSession, str);
        if (z2) {
        }
        checkDeletableListForASKS();
        if ("true".equals(SystemProperties.get("ro.build.official.developer"))) {
        }
    }

    public final void ComparisonBeforeSetData(InstalledAppInfo installedAppInfo, String str) {
        if (installedAppInfo != null && str != null) {
            if (str.equals(installedAppInfo.name)) {
                ArrayList installedAppsDataFromXML = getInstalledAppsDataFromXML("isInstalledList", null);
                if (installedAppsDataFromXML == null || !installedAppsDataFromXML.contains(str)) {
                    Slog.i("PackageInformation", str + " is registered to info_list");
                    setDataToDeviceForModifyUnknownApp(1, installedAppInfo);
                    clearInstalledAppInfoToStore();
                    return;
                }
                return;
            }
            Slog.w("PackageInformation", str + " are different in info_list");
            return;
        }
        Slog.w("PackageInformation", "PackageInfo in info_list");
    }

    public final void clearPackageFromFile(String str, String str2) {
        HashMap hashMap = new HashMap();
        getDataByDevice(str, hashMap);
        if (hashMap.containsKey(str2)) {
            hashMap.remove(str2);
            int size = hashMap.size();
            Slog.i("PackageInformation", "clearPackageFromFile() : count:" + size);
            if (size > 100 || (r9 = hashMap.entrySet().iterator()) == null) {
                return;
            }
            try {
                File file = new File(str);
                if (file.exists()) {
                    if (file.delete()) {
                        Slog.i("ASKSManager", "File is deleted");
                    } else {
                        Slog.e("ASKSManager", "File is not deleted");
                    }
                }
                FastPrintWriter fastPrintWriter = new FastPrintWriter(new FileOutputStream(file, false));
                for (Map.Entry entry : hashMap.entrySet()) {
                    String str3 = (String) entry.getKey();
                    ArrayList arrayList = (ArrayList) entry.getValue();
                    if (arrayList != null && arrayList.size() == 1) {
                        if ("noCert".equals(arrayList.get(0))) {
                            fastPrintWriter.println(str3);
                            Slog.i("PackageInformation", "clearPackageFromFile() : adding  :: pkg =" + str3);
                        } else {
                            fastPrintWriter.println(str3 + "," + ((String) arrayList.get(0)));
                            StringBuilder sb = new StringBuilder();
                            sb.append("clearPackageFromFile() : adding  :: pkg =");
                            sb.append(str3);
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

    public void clearASKSruleForRemovedPackage(String str) {
        boolean z;
        enforceSystemOrRoot("Only the system can claim clearASKSruleForRemovedPackage");
        if (((ASKSState) this.mASKSStates.get(str)) != null) {
            this.mASKSStates.remove(str);
            z = true;
        } else {
            z = false;
        }
        if (z) {
            writeState();
        }
        InstalledAppInfo installedAppInfo = new InstalledAppInfo();
        installedAppInfo.set(str, null, null, null, null, null, null, null);
        setDataToDeviceForModifyUnknownApp(3, installedAppInfo);
        clearPackageFromFile("/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_SA_REPORTED_NEW.xml", str);
    }

    public int checkRestrictedPermission(String str, String str2) {
        Restrict restrict;
        ASKSState aSKSState = (ASKSState) this.mASKSStates.get(str);
        if (aSKSState == null || (restrict = aSKSState.getRestrict()) == null) {
            return 0;
        }
        String dateLimit = restrict.getDateLimit();
        String trustedToday = getTrustedToday();
        return (trustedToday == null || dateLimit == null || Integer.parseInt(trustedToday) <= Integer.parseInt(dateLimit) || restrict.getPermissionList() == null || !restrict.getPermissionList().contains(str2)) ? 0 : 4;
    }

    public byte[] getSEInfo(String str) {
        enforceSystemOrRoot("Only the system can claim getSEInfo");
        byte[] bytes = "aasa_blocked".getBytes();
        ASKSState aSKSState = (ASKSState) this.mASKSStates.get(str);
        if (str != null && aSKSState != null) {
            Restrict restrict = aSKSState.getRestrict();
            int eMMode = aSKSState.getEMMode();
            if (restrict != null && "DENY".equals(restrict.getType())) {
                String dateLimit = restrict.getDateLimit();
                String trustedToday = getTrustedToday();
                if (trustedToday != null && dateLimit != null && Integer.parseInt(trustedToday) > Integer.parseInt(dateLimit)) {
                    return bytes;
                }
            }
            if (eMMode != -1) {
                EngineeringModeManager engineeringModeManager = new EngineeringModeManager(this.mContext);
                if (engineeringModeManager.isConnected() && engineeringModeManager.getStatus(eMMode) == 1) {
                    return null;
                }
                return bytes;
            }
        }
        return null;
    }

    public List getIMEIList() {
        TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
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

    public boolean checkFollowingLegitimateWay(String str, int i) {
        enforceSystemOrRoot("Only the system can claim checkFollowingLegitimateWay");
        if (this.mSessions.containsKey(str)) {
            return true;
        }
        Slog.i("ASKSManager", str + " has not followed legitimate way");
        return false;
    }

    public void checkDeletableListForASKS() {
        if (enforceSystemOrRoot()) {
            String trustedToday = getTrustedToday();
            HashMap hashMap = (HashMap) this.mASKSStates.clone();
            for (Map.Entry entry : hashMap.entrySet()) {
                ASKSState aSKSState = (ASKSState) hashMap.get(entry.getKey());
                if (aSKSState.getDeletable() != null) {
                    String dateLimit = aSKSState.getDeletable().getDateLimit();
                    if (trustedToday != null && dateLimit != null) {
                        try {
                            if (Integer.parseInt(trustedToday) > Integer.parseInt(dateLimit)) {
                                AndroidPackage androidPackage = getPackageManagerInternal().getPackage((String) entry.getKey());
                                aSKSState.setDeletable(null);
                                if (androidPackage != null && androidPackage.getBaseApkPath().startsWith("/data")) {
                                    try {
                                        PackageManagerServiceUtils.logCriticalInfo(4, "a app deleted by the restricted policy. the date is expired [" + ((String) entry.getKey()) + "]");
                                        AppGlobals.getPackageManager().deletePackageAsUser((String) entry.getKey(), -1, (IPackageDeleteObserver) null, this.mContext.getUserId(), 0);
                                    } catch (RemoteException unused) {
                                    }
                                } else {
                                    Slog.i("AASA_ASKSManager_DELETABLE", "does not found delete target - " + ((String) entry.getKey()));
                                }
                                writeState();
                            }
                        } catch (NumberFormatException unused2) {
                            Slog.d("AASA_ASKSManager_DELETABLE", "NumberFormatException ::");
                        }
                    }
                }
            }
            return;
        }
        Slog.e("AASA_ASKSManager_DELETABLE", "ERROR::: Unknown caller");
    }

    public String getUNvalueForASKS() {
        enforceSystemOrRoot("Only the system can claim getUNvalueForASKS");
        if (!this.DEBUG_MODE_FOR_DEVELOPMENT && "0x1".equals(SystemProperties.get("ro.boot.em.status"))) {
            return SystemProperties.get("ro.serialno", "none");
        }
        return null;
    }

    public String[] checkASKSTarget(int i) {
        int i2;
        if (enforceSystemOrRoot()) {
            Slog.i("AASA_ASKSManager", " checkASKSTarget type:" + i);
            ArrayList arrayList = new ArrayList();
            HashMap hashMap = new HashMap();
            ArrayMap packageStates = getPackageManagerInternal().getPackageStates();
            getASKSDataFromXML(9, hashMap);
            if (hashMap.size() != 0 && packageStates != null) {
                Iterator it = packageStates.values().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    AndroidPackage androidPackage = ((PackageStateInternal) it.next()).getAndroidPackage();
                    if (androidPackage != null) {
                        int i3 = 1;
                        i2 = (AndroidPackageUtils.generateAppInfoWithoutState(androidPackage).privateFlags & 8) == 0 ? (AndroidPackageUtils.generateAppInfoWithoutState(androidPackage).flags & 1) != 0 ? 0 : 1 : 0;
                        if (AndroidPackageUtils.generateAppInfoWithoutState(androidPackage).isUpdatedSystemApp()) {
                            Slog.i("AASA_ASKSManager", "isUpdatedSystemApp:" + androidPackage.getPackageName());
                        } else {
                            i3 = i2;
                        }
                        if (i3 != 0 && isSignatureMatched(androidPackage.getPackageName(), androidPackage.getSigningDetails().getSignatures()) != -1) {
                            String sHA256ForPkgName = getSHA256ForPkgName(androidPackage.getPackageName());
                            if (hashMap.containsKey(sHA256ForPkgName)) {
                                Slog.e("AASA_ASKSManager", "checkDevice Target app :" + androidPackage.getPackageName() + " ::" + sHA256ForPkgName);
                                if (((ArrayList) hashMap.get(sHA256ForPkgName)).contains(getApkFileHash(androidPackage.getBaseApkPath())) && !arrayList.contains(androidPackage.getPackageName())) {
                                    Slog.e("AASA_ASKSManager", androidPackage.getPackageName() + " is in Blist");
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
                        Slog.e("AASA_ASKSManager", "return value[" + i2 + "]:" + strArr[i2]);
                        i2++;
                    }
                    return strArr;
                }
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00c7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r9v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v5, types: [java.io.BufferedReader] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void getDataByDevice(String str, HashMap hashMap) {
        Throwable th;
        BufferedReader bufferedReader;
        IOException e;
        String str2;
        BufferedReader bufferedReader2 = null;
        try {
            try {
                try {
                    File file = new File((String) str);
                    if (!file.exists()) {
                        Slog.i("APKFromUnknownSource", ((String) str) + " does not exist.");
                    } else if (file.length() < 10000) {
                        FileReader fileReader = new FileReader((String) str);
                        Slog.i("APKFromUnknownSource", "size = " + file.length() + " :" + ((String) str));
                        bufferedReader = new BufferedReader(fileReader);
                        while (true) {
                            try {
                                String readLine = bufferedReader.readLine();
                                if (readLine == null) {
                                    break;
                                }
                                String[] split = readLine.split(",");
                                if (split != null) {
                                    if (split.length == 1) {
                                        str2 = "noCert";
                                    } else if (split.length == 2) {
                                        str2 = split[1];
                                    } else if (hashMap != null) {
                                        hashMap.clear();
                                    }
                                    if (hashMap != null && !hashMap.containsKey(split[0])) {
                                        ArrayList arrayList = new ArrayList();
                                        arrayList.add(str2);
                                        hashMap.put(split[0], arrayList);
                                    }
                                }
                            } catch (IOException e2) {
                                e = e2;
                                e.printStackTrace();
                                if (bufferedReader != null) {
                                    bufferedReader.close();
                                }
                                return;
                            }
                        }
                        fileReader.close();
                        bufferedReader2 = bufferedReader;
                    } else if (file.delete()) {
                        Slog.i("ASKSManager", "BigSize File is deleted");
                    } else {
                        Slog.e("ASKSManager", "BigSize file is not deleted");
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (str != 0) {
                        try {
                            str.close();
                        } catch (IOException unused) {
                        }
                    }
                    throw th;
                }
            } catch (IOException e3) {
                bufferedReader = null;
                e = e3;
            } catch (Throwable th3) {
                str = 0;
                th = th3;
                if (str != 0) {
                }
                throw th;
            }
            if (bufferedReader2 != null) {
                bufferedReader2.close();
            }
        } catch (IOException unused2) {
        }
    }

    public final void setDataToDevice(String str, String str2, Signature[] signatureArr) {
        try {
            File file = new File(str);
            if (file.length() < 10000) {
                Slog.i("APKFromUnknownSource", str + " adding.");
                FastPrintWriter fastPrintWriter = new FastPrintWriter(new FileOutputStream(file, true));
                if (signatureArr != null && signatureArr.length >= 1) {
                    String sigHash = getSigHash(signatureArr[0]);
                    if (sigHash != null) {
                        fastPrintWriter.println(str2 + "," + sigHash);
                    }
                } else {
                    fastPrintWriter.println(str2);
                }
                fastPrintWriter.flush();
                fastPrintWriter.close();
                return;
            }
            Slog.i("APKFromUnknownSource", str + " init..");
            FastPrintWriter fastPrintWriter2 = new FastPrintWriter(new FileOutputStream(file, false));
            if (signatureArr != null && signatureArr.length >= 1) {
                String sigHash2 = getSigHash(signatureArr[0]);
                if (sigHash2 != null) {
                    fastPrintWriter2.println(str2 + "," + sigHash2);
                }
            } else {
                fastPrintWriter2.println(str2);
            }
            fastPrintWriter2.flush();
            fastPrintWriter2.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
        }
    }

    public final String getSigHash(Signature signature) {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(signature.toByteArray());
        return convertToHex(messageDigest.digest());
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x00e7 A[Catch: IOException -> 0x00e3, TRY_LEAVE, TryCatch #4 {IOException -> 0x00e3, blocks: (B:46:0x00df, B:39:0x00e7), top: B:45:0x00df }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00df A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String getScpmPolicyVersion(String str) {
        ZipInputStream zipInputStream;
        String str2 = "00000000";
        FileInputStream fileInputStream = null;
        try {
            try {
                FileInputStream fileInputStream2 = new FileInputStream(str);
                try {
                    zipInputStream = new ZipInputStream(fileInputStream2);
                    while (true) {
                        try {
                            try {
                                ZipEntry nextEntry = zipInputStream.getNextEntry();
                                if (nextEntry == null) {
                                    break;
                                }
                                if ("version.txt".equals(nextEntry.getName())) {
                                    byte[] bArr = new byte[8];
                                    byte[] bArr2 = new byte[2];
                                    zipInputStream.read(bArr, 0, 8);
                                    String str3 = new String(bArr);
                                    try {
                                        Slog.i("PackageInformation", "version : " + str3);
                                        zipInputStream.read(bArr2, 0, 2);
                                        String str4 = new String(bArr2);
                                        if (isDevDevice()) {
                                            Slog.d("PackageInformation", "tag : " + str4);
                                        }
                                        if ("_D".equals(str4)) {
                                            str2 = str3 + "_D";
                                        } else {
                                            str2 = str3 + "_B";
                                        }
                                        Slog.i("PackageInformation", "scpm policy version : " + str2);
                                    } catch (IOException e) {
                                        e = e;
                                        str2 = str3;
                                        fileInputStream = fileInputStream2;
                                        try {
                                            e.printStackTrace();
                                            if (fileInputStream != null) {
                                                fileInputStream.close();
                                            }
                                            if (zipInputStream != null) {
                                                zipInputStream.close();
                                            }
                                            return str2;
                                        } catch (Throwable th) {
                                            th = th;
                                            if (fileInputStream != null) {
                                                try {
                                                    fileInputStream.close();
                                                } catch (IOException e2) {
                                                    e2.printStackTrace();
                                                    throw th;
                                                }
                                            }
                                            if (zipInputStream != null) {
                                                zipInputStream.close();
                                            }
                                            throw th;
                                        }
                                    }
                                }
                                zipInputStream.closeEntry();
                            } catch (IOException e3) {
                                e = e3;
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            fileInputStream = fileInputStream2;
                            if (fileInputStream != null) {
                            }
                            if (zipInputStream != null) {
                            }
                            throw th;
                        }
                    }
                    fileInputStream2.close();
                    zipInputStream.close();
                } catch (IOException e4) {
                    e = e4;
                    zipInputStream = null;
                } catch (Throwable th3) {
                    th = th3;
                    zipInputStream = null;
                }
            } catch (IOException e5) {
                e5.printStackTrace();
            }
        } catch (IOException e6) {
            e = e6;
            zipInputStream = null;
        } catch (Throwable th4) {
            th = th4;
            zipInputStream = null;
        }
        return str2;
    }

    public final void deleteFile(String str) {
        File file = new File(str);
        if (file.exists()) {
            if (file.delete()) {
                Slog.i("PackageInformation", "delete File : " + file.getName() + " success");
                return;
            }
            Slog.e("PackageInformation", "delete File : " + file.getName() + " fail");
            return;
        }
        Slog.w("PackageInformation", file.getName() + " is does not exist");
    }

    public final void applyExecutePolicy() {
        if (this.mContext != null) {
            ArrayList installedAppsDataFromXML = getInstalledAppsDataFromXML("blockExecute", null);
            ArrayList installedAppsDataFromXML2 = getInstalledAppsDataFromXML("allowExecute", null);
            if (installedAppsDataFromXML != null && !installedAppsDataFromXML.isEmpty()) {
                UnknownSourceAppManager.Helper helper = new UnknownSourceAppManager.Helper();
                int size = installedAppsDataFromXML.size();
                String[] strArr = new String[size];
                for (int i = 0; i < size; i++) {
                    strArr[i] = (String) installedAppsDataFromXML.get(i);
                    if (isDevDevice()) {
                        Slog.d("PackageInformation", "B::" + strArr[i]);
                    }
                }
                helper.suspendUnknownSourceAppsForAllUsers(this.mContext, strArr, true);
            }
            if (installedAppsDataFromXML2 == null || installedAppsDataFromXML2.isEmpty()) {
                return;
            }
            UnknownSourceAppManager.Helper helper2 = new UnknownSourceAppManager.Helper();
            int size2 = installedAppsDataFromXML2.size();
            String[] strArr2 = new String[size2];
            for (int i2 = 0; i2 < size2; i2++) {
                strArr2[i2] = (String) installedAppsDataFromXML2.get(i2);
                if (isDevDevice()) {
                    Slog.d("PackageInformation", "A::" + strArr2[i2]);
                }
            }
            helper2.suspendUnknownSourceAppsForAllUsers(this.mContext, strArr2, false);
        }
    }

    public String checkIfSuspiciousValue(String str, String str2, boolean z, Map map) {
        enforceSystemOrRoot("Only the system and sub system can claim checkIfTargetFromManager()");
        String str3 = null;
        if (!new File(str2).exists()) {
            return null;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(str2));
            try {
                XmlPullParser newPullParser = Xml.newPullParser();
                newPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", false);
                newPullParser.setInput(fileInputStream, null);
                String str4 = "";
                String str5 = "";
                for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
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
                            try {
                                if (z) {
                                    if (str.contains(str4)) {
                                        str = newPullParser.getAttributeValue(null, "value");
                                        map.put("type", str5);
                                        map.put("contents", str4);
                                        str3 = str;
                                        break;
                                    }
                                } else if (str4.equals(str)) {
                                    str = newPullParser.getAttributeValue(null, "value");
                                    map.put("type", str5);
                                    map.put("contents", str4);
                                    str3 = str;
                                    break;
                                }
                            } catch (Throwable th) {
                                str3 = str;
                                th = th;
                                try {
                                    fileInputStream.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                                throw th;
                            }
                        }
                    }
                }
                fileInputStream.close();
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str3;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0043, code lost:
    
        r0 = r6.getAttributeValue(null, "value");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String getPolicyVersion(String str) {
        enforceSystemOrRoot("Only the system and sub system can claim getPolicyVersion()");
        String str2 = "00000000";
        if (!new File(str).exists()) {
            return "00000000";
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(str));
            try {
                XmlPullParser newPullParser = Xml.newPullParser();
                newPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", false);
                newPullParser.setInput(fileInputStream, null);
                int eventType = newPullParser.getEventType();
                while (true) {
                    if (eventType == 1) {
                        break;
                    }
                    String name = newPullParser.getName();
                    if (eventType == 2 && name != null && name.equalsIgnoreCase("VERSION")) {
                        break;
                    }
                    eventType = newPullParser.next();
                }
                fileInputStream.close();
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str2;
    }

    public boolean isSuspiciousMsgTarget(String str) {
        enforceSystemOrRoot("Only the system and sub system can claim isTargetDevice()");
        boolean z = false;
        if (!new File("/data/system/.aasa/AASApolicy/ASKS_SPAM_CONFIG.xml").exists()) {
            Slog.i("ASKSManager", "setConfig does not exist.");
            return false;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(new File("/data/system/.aasa/AASApolicy/ASKS_SPAM_CONFIG.xml"));
            try {
                XmlPullParser newPullParser = Xml.newPullParser();
                newPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", false);
                newPullParser.setInput(fileInputStream, null);
                String str2 = null;
                for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                    String name = newPullParser.getName();
                    if (eventType == 2) {
                        if (name != null && name.equalsIgnoreCase("config")) {
                            str2 = newPullParser.getAttributeValue(null, "name");
                        } else if (name != null && name.equalsIgnoreCase("value") && str2 != null && str2.equals("target_model") && (newPullParser.getAttributeValue(null, "name").equals(str) || newPullParser.getAttributeValue(null, "name").equals("ALL"))) {
                            z = true;
                            break;
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

    public void applyScpmPolicyFromApp() {
        applyScpmPolicyFromService("old");
    }

    public void applyScpmPolicyFromService(String str) {
        enforceSystemOrRoot("Only the system can claim applyScpmPolicyFromApp");
        if (applyScpmPolicy("/data/system/.aasa/ASKS.zip")) {
            Slog.i("PackageInformation", "success to apply Scpm Policy.");
            setSamsungAnalyticsLog("9014", str, mASKSPolicyVersion);
            refreshInstalledUnknownList_NEW();
            applyExecutePolicy();
            updateSmsFilterFeatures();
        }
    }

    public String readASKSFiles(String str, String str2) {
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
                    for (int read = fileInputStream.read(); read != -1; read = fileInputStream.read()) {
                        byteArrayOutputStream.write(read);
                    }
                    String byteArrayOutputStream2 = byteArrayOutputStream.toString(Charset.defaultCharset());
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

    public void setASKSPolicyVersion(String str) {
        enforceSystemOrRoot("Only the system can claim setASKSPolicyVersion");
        try {
            if (Integer.parseInt(str) > Integer.parseInt(SystemProperties.get("security.ASKS.policy_version"))) {
                SystemProperties.set("security.ASKS.policy_version", str);
            }
        } catch (NumberFormatException e) {
            Slog.d("AASA_ASKSManager", "setASKSPolicyVersion() : Numberformat exception " + e);
        }
    }

    public final boolean applyScpmPolicy(String str) {
        String str2;
        if (new File(str).exists()) {
            RUFSContainer rUFSContainer = new RUFSContainer();
            RuleUpdateForSecurity ruleUpdateForSecurity = new RuleUpdateForSecurity(rUFSContainer);
            String scpmPolicyVersion = getScpmPolicyVersion(str);
            String[] split = scpmPolicyVersion.split("_");
            if (split == null) {
                return false;
            }
            rUFSContainer.setHasRUFSToken(true);
            if (split.length == 2 && split[1].equals("D")) {
                str2 = SystemProperties.get("security.ASKS.delta_policy_version");
                rUFSContainer.setIsDelta(true);
                rUFSContainer.setDeltaPolicyVersion(split[0]);
                Slog.i("AASA_ASKSManager_RUFS", "try Delta policy update from " + SystemProperties.get("security.ASKS.delta_policy_version"));
            } else if (split.length == 2 && split[1].equals("B")) {
                str2 = SystemProperties.get("security.ASKS.policy_version");
                rUFSContainer.setIsDelta(false);
                rUFSContainer.setPolicyVersion(split[0]);
                Slog.i("AASA_ASKSManager_RUFS", "try Base policy update from " + SystemProperties.get("security.ASKS.policy_version"));
            } else {
                Slog.e("AASA_ASKSManager_RUFS", "abnormal version format. " + scpmPolicyVersion);
                return false;
            }
            if (!ruleUpdateForSecurity.isUpdatePolicy(str2) || !ruleUpdateForSecurity.updatePolicy(str, false)) {
                return false;
            }
            if (rUFSContainer.getIsDelta()) {
                String deltaPolicyVersion = rUFSContainer.getDeltaPolicyVersion();
                mASKSDeltaPolicyVersion = deltaPolicyVersion;
                SystemProperties.set("security.ASKS.delta_policy_version", deltaPolicyVersion);
                Slog.i("AASA_ASKSManager_RUFS", "policy(D) update to   " + SystemProperties.get("security.ASKS.delta_policy_version"));
            } else {
                String policyVersion = rUFSContainer.getPolicyVersion();
                mASKSPolicyVersion = policyVersion;
                SystemProperties.set("security.ASKS.policy_version", policyVersion);
                Slog.i("AASA_ASKSManager_RUFS", "policy(B) update to   " + SystemProperties.get("security.ASKS.policy_version"));
            }
            Slog.i("AASA_ASKSManager_RUFS", "adding delta version to asks.xml");
            writeState();
            return true;
        }
        Slog.i("PackageInformation", "SCPM file does not exist");
        return false;
    }

    public final void updateSmsFilterFeatures() {
        SystemProperties.set("security.ASKS.smsfilter_enable", String.valueOf(checkSmsFilterEnabled()));
        SystemProperties.set("security.ASKS.smsfilter_target", String.valueOf(checkIfSmsFilterTarget()));
    }

    public final boolean isIPaddress(String str) {
        boolean z = false;
        if (str != null) {
            try {
                z = str.matches("(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])");
            } catch (Exception unused) {
            }
            if (z) {
                Slog.i("PackageInformation", "IP:" + str);
            } else {
                Slog.i("PackageInformation", "Not IP:" + str);
            }
        }
        return z;
    }

    public final String getDomainName(String str) {
        URI uri;
        String host;
        if (str == null) {
            return null;
        }
        if (str.startsWith("HTTPS")) {
            str = "http" + str.substring(5);
        } else if (str.startsWith("HTTP")) {
            str = "http" + str.substring(4);
        } else if (str.startsWith("http://www")) {
            str = "http://" + str.substring(11);
        } else if (!str.startsWith("http") && !str.startsWith("https")) {
            if (str.startsWith("www")) {
                str = str.substring(4);
            }
            str = "http://" + str;
        }
        try {
            uri = new URI(str);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            uri = null;
        }
        if (uri == null || (host = uri.getHost()) == null) {
            return null;
        }
        return host.startsWith("www") ? host.substring(4) : host;
    }

    public final int convertStoI(String str) {
        if (str == null) {
            return 0;
        }
        switch (str) {
        }
        return 0;
    }

    public final String get3rdTargetNodeName(String str) {
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
                for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                    if (eventType == 2) {
                        String name = newPullParser.getName();
                        if (name != null && name.equals("package")) {
                            str2 = newPullParser.getAttributeValue(null, "name");
                        } else if (name != null && name.equals("policy")) {
                            if (str2 != null && str2.equals(str)) {
                                String attributeValue = newPullParser.getAttributeValue(null, "name");
                                Slog.i("PackageInformation", "3rdtargetPolicy:: : " + attributeValue);
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

    public final ArrayList getTargetNodeName(String str) {
        String attributeValue;
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
                for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                    if (eventType == 2) {
                        String name = newPullParser.getName();
                        if (!name.equals("DEVICE") && !name.equals("LIST") && !name.equals("TARGET") && !name.equals("CERTTARGET") && !name.equals("ZIPTARGET") && !name.equals("ZIPCERTTARGET") && (attributeValue = newPullParser.getAttributeValue(null, "value")) != null && ((attributeValue.equals(str) || "ALL".equals(attributeValue)) && !arrayList.contains(name))) {
                            arrayList.add(name);
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

    public String getPolicyFilePath(int i, boolean z) {
        String str = z ? "_DELTA" : "";
        if (i == 27) {
            return "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_TOTALLIST" + str + ".xml";
        }
        if (i == 28) {
            return "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_TOTALLIST_ZIP" + str + ".xml";
        }
        if (i == 33) {
            return "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_TOTALLIST_A11Y" + str + ".xml";
        }
        if (i == 34) {
            return "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_TOTALLIST_WEB" + str + ".xml";
        }
        if (i != 38) {
            return null;
        }
        return "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_TOTALLIST_COMMON" + str + ".xml";
    }

    /* JADX WARN: Can't wrap try/catch for region: R(19:98|(18:100|(1:102)(2:226|(1:228)(2:229|(1:231)))|(15:104|(1:106)(2:219|(1:221)(2:222|(1:224)))|(1:218)(4:110|111|(2:113|(1:115)(1:216))(1:217)|116)|(2:118|(14:120|(1:214)(2:124|(1:126)(9:213|129|130|131|132|(1:209)(3:135|136|(15:138|139|(13:141|(1:143)(2:201|(1:203)(2:204|(1:206)))|(11:145|(2:194|(1:196)(2:197|(1:199)))(1:147)|(9:149|(2:190|(1:192))(1:151)|(2:153|154)|(3:183|184|185)(1:156)|157|158|159|(2:161|(1:163)(1:164))|165)|193|(0)|(0)(0)|157|158|159|(0)|165)|200|(0)|193|(0)|(0)(0)|157|158|159|(0)|165)|207|(0)|200|(0)|193|(0)|(0)(0)|157|158|159|(0)|165)(1:208))|(1:182)(5:170|171|(3:173|(1:(1:176))(2:178|(1:180))|177)|181|177)|63|64))|127|128|129|130|131|132|(0)|209|(0)|182|63|64))|215|129|130|131|132|(0)|209|(0)|182|63|64)|225|(1:108)|218|(0)|215|129|130|131|132|(0)|209|(0)|182|63|64)|232|(0)|225|(0)|218|(0)|215|129|130|131|132|(0)|209|(0)|182|63|64) */
    /* JADX WARN: Code restructure failed: missing block: B:211:0x027d, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:212:0x027e, code lost:
    
        android.util.Slog.d(r13, "numberformat exception" + r0);
     */
    /* JADX WARN: Removed duplicated region for block: B:104:0x01d1 A[Catch: IOException -> 0x0472, XmlPullParserException -> 0x0475, TryCatch #14 {IOException -> 0x0472, XmlPullParserException -> 0x0475, blocks: (B:36:0x00a9, B:38:0x00af, B:41:0x00b7, B:43:0x00bd, B:68:0x00e0, B:71:0x00ec, B:72:0x010e, B:77:0x0119, B:80:0x014e, B:86:0x015d, B:88:0x0163, B:91:0x0175, B:100:0x01b2, B:104:0x01d1, B:108:0x01f0, B:113:0x01ff, B:115:0x0205, B:118:0x021f, B:120:0x022f, B:122:0x0238, B:124:0x0242, B:127:0x0258, B:130:0x026a, B:136:0x02ce, B:141:0x02fe, B:145:0x031d, B:149:0x033a, B:153:0x0352, B:184:0x035a, B:161:0x0383, B:168:0x03ae, B:189:0x0363, B:190:0x0343, B:194:0x0324, B:197:0x032d, B:201:0x0307, B:204:0x0310, B:212:0x027e, B:216:0x020b, B:217:0x0210, B:219:0x01da, B:222:0x01e3, B:226:0x01bb, B:229:0x01c4, B:240:0x016a), top: B:35:0x00a9, inners: #2, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:108:0x01f0 A[Catch: IOException -> 0x0472, XmlPullParserException -> 0x0475, TRY_LEAVE, TryCatch #14 {IOException -> 0x0472, XmlPullParserException -> 0x0475, blocks: (B:36:0x00a9, B:38:0x00af, B:41:0x00b7, B:43:0x00bd, B:68:0x00e0, B:71:0x00ec, B:72:0x010e, B:77:0x0119, B:80:0x014e, B:86:0x015d, B:88:0x0163, B:91:0x0175, B:100:0x01b2, B:104:0x01d1, B:108:0x01f0, B:113:0x01ff, B:115:0x0205, B:118:0x021f, B:120:0x022f, B:122:0x0238, B:124:0x0242, B:127:0x0258, B:130:0x026a, B:136:0x02ce, B:141:0x02fe, B:145:0x031d, B:149:0x033a, B:153:0x0352, B:184:0x035a, B:161:0x0383, B:168:0x03ae, B:189:0x0363, B:190:0x0343, B:194:0x0324, B:197:0x032d, B:201:0x0307, B:204:0x0310, B:212:0x027e, B:216:0x020b, B:217:0x0210, B:219:0x01da, B:222:0x01e3, B:226:0x01bb, B:229:0x01c4, B:240:0x016a), top: B:35:0x00a9, inners: #2, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:118:0x021f A[Catch: IOException -> 0x0472, XmlPullParserException -> 0x0475, TryCatch #14 {IOException -> 0x0472, XmlPullParserException -> 0x0475, blocks: (B:36:0x00a9, B:38:0x00af, B:41:0x00b7, B:43:0x00bd, B:68:0x00e0, B:71:0x00ec, B:72:0x010e, B:77:0x0119, B:80:0x014e, B:86:0x015d, B:88:0x0163, B:91:0x0175, B:100:0x01b2, B:104:0x01d1, B:108:0x01f0, B:113:0x01ff, B:115:0x0205, B:118:0x021f, B:120:0x022f, B:122:0x0238, B:124:0x0242, B:127:0x0258, B:130:0x026a, B:136:0x02ce, B:141:0x02fe, B:145:0x031d, B:149:0x033a, B:153:0x0352, B:184:0x035a, B:161:0x0383, B:168:0x03ae, B:189:0x0363, B:190:0x0343, B:194:0x0324, B:197:0x032d, B:201:0x0307, B:204:0x0310, B:212:0x027e, B:216:0x020b, B:217:0x0210, B:219:0x01da, B:222:0x01e3, B:226:0x01bb, B:229:0x01c4, B:240:0x016a), top: B:35:0x00a9, inners: #2, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:134:0x02c8 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:145:0x031d A[Catch: IOException -> 0x0472, XmlPullParserException -> 0x0475, TryCatch #14 {IOException -> 0x0472, XmlPullParserException -> 0x0475, blocks: (B:36:0x00a9, B:38:0x00af, B:41:0x00b7, B:43:0x00bd, B:68:0x00e0, B:71:0x00ec, B:72:0x010e, B:77:0x0119, B:80:0x014e, B:86:0x015d, B:88:0x0163, B:91:0x0175, B:100:0x01b2, B:104:0x01d1, B:108:0x01f0, B:113:0x01ff, B:115:0x0205, B:118:0x021f, B:120:0x022f, B:122:0x0238, B:124:0x0242, B:127:0x0258, B:130:0x026a, B:136:0x02ce, B:141:0x02fe, B:145:0x031d, B:149:0x033a, B:153:0x0352, B:184:0x035a, B:161:0x0383, B:168:0x03ae, B:189:0x0363, B:190:0x0343, B:194:0x0324, B:197:0x032d, B:201:0x0307, B:204:0x0310, B:212:0x027e, B:216:0x020b, B:217:0x0210, B:219:0x01da, B:222:0x01e3, B:226:0x01bb, B:229:0x01c4, B:240:0x016a), top: B:35:0x00a9, inners: #2, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:149:0x033a A[Catch: IOException -> 0x0472, XmlPullParserException -> 0x0475, TryCatch #14 {IOException -> 0x0472, XmlPullParserException -> 0x0475, blocks: (B:36:0x00a9, B:38:0x00af, B:41:0x00b7, B:43:0x00bd, B:68:0x00e0, B:71:0x00ec, B:72:0x010e, B:77:0x0119, B:80:0x014e, B:86:0x015d, B:88:0x0163, B:91:0x0175, B:100:0x01b2, B:104:0x01d1, B:108:0x01f0, B:113:0x01ff, B:115:0x0205, B:118:0x021f, B:120:0x022f, B:122:0x0238, B:124:0x0242, B:127:0x0258, B:130:0x026a, B:136:0x02ce, B:141:0x02fe, B:145:0x031d, B:149:0x033a, B:153:0x0352, B:184:0x035a, B:161:0x0383, B:168:0x03ae, B:189:0x0363, B:190:0x0343, B:194:0x0324, B:197:0x032d, B:201:0x0307, B:204:0x0310, B:212:0x027e, B:216:0x020b, B:217:0x0210, B:219:0x01da, B:222:0x01e3, B:226:0x01bb, B:229:0x01c4, B:240:0x016a), top: B:35:0x00a9, inners: #2, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0352 A[Catch: IOException -> 0x0472, XmlPullParserException -> 0x0475, TRY_LEAVE, TryCatch #14 {IOException -> 0x0472, XmlPullParserException -> 0x0475, blocks: (B:36:0x00a9, B:38:0x00af, B:41:0x00b7, B:43:0x00bd, B:68:0x00e0, B:71:0x00ec, B:72:0x010e, B:77:0x0119, B:80:0x014e, B:86:0x015d, B:88:0x0163, B:91:0x0175, B:100:0x01b2, B:104:0x01d1, B:108:0x01f0, B:113:0x01ff, B:115:0x0205, B:118:0x021f, B:120:0x022f, B:122:0x0238, B:124:0x0242, B:127:0x0258, B:130:0x026a, B:136:0x02ce, B:141:0x02fe, B:145:0x031d, B:149:0x033a, B:153:0x0352, B:184:0x035a, B:161:0x0383, B:168:0x03ae, B:189:0x0363, B:190:0x0343, B:194:0x0324, B:197:0x032d, B:201:0x0307, B:204:0x0310, B:212:0x027e, B:216:0x020b, B:217:0x0210, B:219:0x01da, B:222:0x01e3, B:226:0x01bb, B:229:0x01c4, B:240:0x016a), top: B:35:0x00a9, inners: #2, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0378  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x0383 A[Catch: IOException -> 0x0472, XmlPullParserException -> 0x0475, TRY_ENTER, TryCatch #14 {IOException -> 0x0472, XmlPullParserException -> 0x0475, blocks: (B:36:0x00a9, B:38:0x00af, B:41:0x00b7, B:43:0x00bd, B:68:0x00e0, B:71:0x00ec, B:72:0x010e, B:77:0x0119, B:80:0x014e, B:86:0x015d, B:88:0x0163, B:91:0x0175, B:100:0x01b2, B:104:0x01d1, B:108:0x01f0, B:113:0x01ff, B:115:0x0205, B:118:0x021f, B:120:0x022f, B:122:0x0238, B:124:0x0242, B:127:0x0258, B:130:0x026a, B:136:0x02ce, B:141:0x02fe, B:145:0x031d, B:149:0x033a, B:153:0x0352, B:184:0x035a, B:161:0x0383, B:168:0x03ae, B:189:0x0363, B:190:0x0343, B:194:0x0324, B:197:0x032d, B:201:0x0307, B:204:0x0310, B:212:0x027e, B:216:0x020b, B:217:0x0210, B:219:0x01da, B:222:0x01e3, B:226:0x01bb, B:229:0x01c4, B:240:0x016a), top: B:35:0x00a9, inners: #2, #7 }] */
    /* JADX WARN: Removed duplicated region for block: B:167:0x03ac A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:183:0x035a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final HashMap getUnknownAppsDataFromXML(int i, ArrayList arrayList, HashMap hashMap, boolean z) {
        FileReader fileReader;
        HashMap hashMap2;
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        ASKSManagerService aSKSManagerService;
        boolean z2;
        String str7;
        String str8;
        String str9;
        String str10;
        String str11;
        XmlPullParser xmlPullParser;
        String str12;
        String str13;
        int i2;
        String str14;
        int i3;
        String attributeValue;
        String attributeValue2;
        UnknownStore unknownStore;
        boolean z3;
        int i4;
        String str15;
        UnknownStore unknownStore2;
        ArrayList arrayList2 = arrayList;
        HashMap hashMap3 = hashMap;
        String str16 = "value";
        String policyFilePath = getPolicyFilePath(i, z);
        String str17 = null;
        if (policyFilePath == null || arrayList2 == null || hashMap3 == null) {
            return null;
        }
        File file = new File(policyFilePath);
        String str18 = "PackageInformation";
        int i5 = 1;
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            if (!file.getParentFile().mkdir()) {
                Slog.e("PackageInformation", "failed to created folder related TOTALLIST");
                return null;
            }
            file.getParentFile().setReadable(true, false);
        }
        if (!file.exists()) {
            return null;
        }
        try {
            FileReader fileReader2 = new FileReader(file, Charset.defaultCharset());
            try {
                XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
                newPullParser.setInput(fileReader2);
                int eventType = newPullParser.getEventType();
                String str19 = "";
                String str20 = "";
                int i6 = 0;
                boolean z4 = false;
                int i7 = 0;
                int i8 = 0;
                int i9 = 0;
                int i10 = 0;
                int i11 = 0;
                boolean z5 = false;
                UnknownStore unknownStore3 = null;
                String str21 = null;
                String str22 = null;
                String str23 = null;
                String str24 = null;
                int i12 = -1;
                int i13 = -1;
                int i14 = -1;
                int i15 = -1;
                int i16 = -1;
                while (eventType != i5) {
                    fileReader = fileReader2;
                    if (eventType == 2) {
                        String name = newPullParser.getName();
                        try {
                            String attributeValue3 = newPullParser.getAttributeValue(null, str16);
                            if (attributeValue3 != null) {
                                str23 = name + attributeValue3;
                            }
                            int i17 = i6;
                            boolean z6 = z4;
                            UnknownStore unknownStore4 = unknownStore3;
                            String str25 = str17;
                            int i18 = EndpointMonitorConst.TRACE_EVENT_SCHED_CLS_INGRESS;
                            int i19 = 503;
                            int i20 = 500;
                            if (name == null || !arrayList2.contains(name)) {
                                str = str16;
                                str2 = "applyPolicy";
                                str3 = "SA";
                                str4 = name;
                                str5 = "reported";
                                str6 = "block";
                                aSKSManagerService = this;
                                z2 = z6;
                            } else {
                                str4 = name;
                                try {
                                    attributeValue2 = newPullParser.getAttributeValue(null, str16);
                                    if (attributeValue2 != null) {
                                        if (hashMap3.containsKey(attributeValue2)) {
                                            unknownStore2 = (UnknownStore) hashMap3.get(attributeValue2);
                                        } else {
                                            unknownStore2 = new UnknownStore();
                                        }
                                        unknownStore = unknownStore2;
                                    } else {
                                        unknownStore = unknownStore4;
                                    }
                                    if (unknownStore != null) {
                                        unknownStore.setKey(attributeValue2);
                                    }
                                    str = str16;
                                } catch (IOException e) {
                                    e = e;
                                    hashMap2 = null;
                                } catch (XmlPullParserException e2) {
                                    e = e2;
                                    hashMap2 = null;
                                }
                                try {
                                    String attributeValue4 = newPullParser.getAttributeValue(null, "SA");
                                    str25 = attributeValue2;
                                    String attributeValue5 = newPullParser.getAttributeValue(null, "MIN");
                                    String attributeValue6 = newPullParser.getAttributeValue(null, "MAX");
                                    if (attributeValue4 == null || attributeValue5 == null || attributeValue6 == null) {
                                        str2 = "applyPolicy";
                                        str3 = "SA";
                                        str5 = "reported";
                                        str6 = "block";
                                        aSKSManagerService = this;
                                        unknownStore4 = unknownStore;
                                        z2 = true;
                                    } else {
                                        str3 = "SA";
                                        String attributeValue7 = newPullParser.getAttributeValue(null, "applyPolicy");
                                        str2 = "applyPolicy";
                                        String attributeValue8 = newPullParser.getAttributeValue(null, "reported");
                                        str5 = "reported";
                                        String attributeValue9 = newPullParser.getAttributeValue(null, "applyOption");
                                        String attributeValue10 = newPullParser.getAttributeValue(null, "moreRule");
                                        if (attributeValue7 != null) {
                                            if ("ALL".equals(attributeValue7)) {
                                                i13 = 501;
                                            } else if ("nonURL".equalsIgnoreCase(attributeValue7)) {
                                                i13 = 502;
                                            } else if ("URL".equalsIgnoreCase(attributeValue7)) {
                                                i13 = 503;
                                            }
                                            if (attributeValue8 != null) {
                                                if ("ALL".equals(attributeValue8)) {
                                                    i14 = 501;
                                                } else if ("nonURL".equalsIgnoreCase(attributeValue8)) {
                                                    i14 = 502;
                                                } else if ("URL".equalsIgnoreCase(attributeValue8)) {
                                                    i14 = 503;
                                                }
                                                if (attributeValue9 == null && "AND".equalsIgnoreCase(attributeValue9)) {
                                                    String attributeValue11 = newPullParser.getAttributeValue(null, "policy");
                                                    if (unknownStore != null) {
                                                        if (attributeValue11.startsWith("block")) {
                                                            unknownStore.addPermissionGroup(true);
                                                        } else {
                                                            unknownStore.addPermissionGroup(false);
                                                        }
                                                    } else {
                                                        Slog.e(str18, "store is null !!");
                                                    }
                                                    z5 = true;
                                                } else {
                                                    z5 = false;
                                                }
                                                if (attributeValue10 != null) {
                                                    String replace = attributeValue10.replace(" ", str19);
                                                    String[] split = replace.split("=");
                                                    if (split.length == 2) {
                                                        if (split[1].startsWith("block") || split[1].startsWith("except")) {
                                                            z3 = true;
                                                        } else if (split[1].startsWith("warning")) {
                                                            z3 = true;
                                                        } else {
                                                            str6 = "block";
                                                            i4 = -1;
                                                            z3 = true;
                                                            str15 = null;
                                                            aSKSManagerService = this;
                                                            i9 = Integer.parseInt(attributeValue5);
                                                            i10 = Integer.parseInt(attributeValue6);
                                                            i11 = Integer.parseInt(attributeValue4);
                                                            unknownStore4 = unknownStore;
                                                            z2 = z3;
                                                            i16 = i4;
                                                            str7 = str15;
                                                            if (z2 || str4 == null) {
                                                                str8 = str7;
                                                                str9 = str19;
                                                                str10 = "policy";
                                                                str11 = str4;
                                                            } else {
                                                                str11 = str4;
                                                                if (str11.equals("package")) {
                                                                    str21 = newPullParser.getAttributeValue(null, "name");
                                                                    String attributeValue12 = newPullParser.getAttributeValue(null, "policy");
                                                                    str9 = str19;
                                                                    String attributeValue13 = newPullParser.getAttributeValue(null, str3);
                                                                    String attributeValue14 = newPullParser.getAttributeValue(null, str2);
                                                                    str8 = str7;
                                                                    String attributeValue15 = newPullParser.getAttributeValue(null, str5);
                                                                    str10 = "policy";
                                                                    String attributeValue16 = newPullParser.getAttributeValue(null, "rgxUrl");
                                                                    if (attributeValue14 != null) {
                                                                        if ("ALL".equals(attributeValue14)) {
                                                                            i3 = 501;
                                                                        } else if ("nonURL".equalsIgnoreCase(attributeValue14)) {
                                                                            i3 = 502;
                                                                        } else if ("URL".equalsIgnoreCase(attributeValue14)) {
                                                                            i3 = 503;
                                                                        }
                                                                        if (attributeValue15 != null) {
                                                                            if (!"ALL".equals(attributeValue15)) {
                                                                                if ("nonURL".equalsIgnoreCase(attributeValue15)) {
                                                                                    i18 = 502;
                                                                                } else if ("URL".equalsIgnoreCase(attributeValue15)) {
                                                                                    i18 = 503;
                                                                                }
                                                                            }
                                                                            if (attributeValue16 != null) {
                                                                                if (!"DOMAIN".equalsIgnoreCase(attributeValue16)) {
                                                                                    if ("PKG".equalsIgnoreCase(attributeValue16)) {
                                                                                        i19 = 502;
                                                                                    }
                                                                                }
                                                                                if (attributeValue12 != null) {
                                                                                    i8 = aSKSManagerService.convertStoI(attributeValue12);
                                                                                }
                                                                                if (attributeValue13 == null) {
                                                                                    try {
                                                                                        i7 = Integer.parseInt(attributeValue13);
                                                                                    } catch (NumberFormatException e3) {
                                                                                        Slog.d(str18, "PKG SA numberformat exception" + e3);
                                                                                    }
                                                                                } else {
                                                                                    i7 = 500;
                                                                                }
                                                                                attributeValue = newPullParser.getAttributeValue(null, "execute");
                                                                                if (attributeValue != null) {
                                                                                    i20 = str6.equals(attributeValue) ? 504 : 505;
                                                                                }
                                                                                str24 = attributeValue12;
                                                                                i13 = i3;
                                                                                i14 = i18;
                                                                                i15 = i19;
                                                                                i12 = i20;
                                                                                i17 = 1;
                                                                            }
                                                                            i19 = 500;
                                                                            if (attributeValue12 != null) {
                                                                            }
                                                                            if (attributeValue13 == null) {
                                                                            }
                                                                            attributeValue = newPullParser.getAttributeValue(null, "execute");
                                                                            if (attributeValue != null) {
                                                                            }
                                                                            str24 = attributeValue12;
                                                                            i13 = i3;
                                                                            i14 = i18;
                                                                            i15 = i19;
                                                                            i12 = i20;
                                                                            i17 = 1;
                                                                        }
                                                                        i18 = 500;
                                                                        if (attributeValue16 != null) {
                                                                        }
                                                                        i19 = 500;
                                                                        if (attributeValue12 != null) {
                                                                        }
                                                                        if (attributeValue13 == null) {
                                                                        }
                                                                        attributeValue = newPullParser.getAttributeValue(null, "execute");
                                                                        if (attributeValue != null) {
                                                                        }
                                                                        str24 = attributeValue12;
                                                                        i13 = i3;
                                                                        i14 = i18;
                                                                        i15 = i19;
                                                                        i12 = i20;
                                                                        i17 = 1;
                                                                    }
                                                                    i3 = 500;
                                                                    if (attributeValue15 != null) {
                                                                    }
                                                                    i18 = 500;
                                                                    if (attributeValue16 != null) {
                                                                    }
                                                                    i19 = 500;
                                                                    if (attributeValue12 != null) {
                                                                    }
                                                                    if (attributeValue13 == null) {
                                                                    }
                                                                    attributeValue = newPullParser.getAttributeValue(null, "execute");
                                                                    if (attributeValue != null) {
                                                                    }
                                                                    str24 = attributeValue12;
                                                                    i13 = i3;
                                                                    i14 = i18;
                                                                    i15 = i19;
                                                                    i12 = i20;
                                                                    i17 = 1;
                                                                } else {
                                                                    str8 = str7;
                                                                    str9 = str19;
                                                                    str10 = "policy";
                                                                }
                                                            }
                                                            if (z2 || str11 == null || !str11.equals("pem")) {
                                                                xmlPullParser = newPullParser;
                                                                str12 = str18;
                                                                str13 = str9;
                                                                hashMap2 = null;
                                                                i2 = 1;
                                                                z4 = z2;
                                                                str22 = str8;
                                                                i6 = i17;
                                                                unknownStore3 = unknownStore4;
                                                                str17 = str25;
                                                                str20 = str11;
                                                            } else {
                                                                hashMap2 = null;
                                                                String attributeValue17 = newPullParser.getAttributeValue(null, "name");
                                                                String attributeValue18 = newPullParser.getAttributeValue(null, str10);
                                                                if (attributeValue18 != null) {
                                                                    int convertStoI = aSKSManagerService.convertStoI(attributeValue18);
                                                                    if (!attributeValue18.startsWith(str6)) {
                                                                        str14 = str11;
                                                                        xmlPullParser = newPullParser;
                                                                        str12 = str18;
                                                                        str13 = str9;
                                                                        i2 = 1;
                                                                        if (unknownStore4 != null) {
                                                                            unknownStore4.addPermission(attributeValue17, false, i11, i9, i10, convertStoI, i13, i14, z5, str8, i16, str23, z);
                                                                        }
                                                                    } else if (unknownStore4 != null) {
                                                                        str14 = str11;
                                                                        str13 = str9;
                                                                        xmlPullParser = newPullParser;
                                                                        i2 = 1;
                                                                        str12 = str18;
                                                                        unknownStore4.addPermission(attributeValue17, true, i11, i9, i10, convertStoI, i13, i14, z5, str8, i16, str23, z);
                                                                    }
                                                                    str21 = attributeValue17;
                                                                    z4 = z2;
                                                                    str22 = str8;
                                                                    unknownStore3 = unknownStore4;
                                                                    str17 = str25;
                                                                    str20 = str14;
                                                                    i6 = i2;
                                                                }
                                                                str14 = str11;
                                                                xmlPullParser = newPullParser;
                                                                str12 = str18;
                                                                str13 = str9;
                                                                i2 = 1;
                                                                str21 = attributeValue17;
                                                                z4 = z2;
                                                                str22 = str8;
                                                                unknownStore3 = unknownStore4;
                                                                str17 = str25;
                                                                str20 = str14;
                                                                i6 = i2;
                                                            }
                                                            eventType = xmlPullParser.next();
                                                            arrayList2 = arrayList;
                                                            hashMap3 = hashMap;
                                                            str19 = str13;
                                                            i5 = i2;
                                                            fileReader2 = fileReader;
                                                            newPullParser = xmlPullParser;
                                                            str18 = str12;
                                                            str16 = str;
                                                        }
                                                        str6 = "block";
                                                        aSKSManagerService = this;
                                                        i4 = aSKSManagerService.convertStoI(split[z3 ? 1 : 0]);
                                                        str15 = replace;
                                                        i9 = Integer.parseInt(attributeValue5);
                                                        i10 = Integer.parseInt(attributeValue6);
                                                        i11 = Integer.parseInt(attributeValue4);
                                                        unknownStore4 = unknownStore;
                                                        z2 = z3;
                                                        i16 = i4;
                                                        str7 = str15;
                                                        if (z2) {
                                                        }
                                                        str8 = str7;
                                                        str9 = str19;
                                                        str10 = "policy";
                                                        str11 = str4;
                                                        if (z2) {
                                                        }
                                                        xmlPullParser = newPullParser;
                                                        str12 = str18;
                                                        str13 = str9;
                                                        hashMap2 = null;
                                                        i2 = 1;
                                                        z4 = z2;
                                                        str22 = str8;
                                                        i6 = i17;
                                                        unknownStore3 = unknownStore4;
                                                        str17 = str25;
                                                        str20 = str11;
                                                        eventType = xmlPullParser.next();
                                                        arrayList2 = arrayList;
                                                        hashMap3 = hashMap;
                                                        str19 = str13;
                                                        i5 = i2;
                                                        fileReader2 = fileReader;
                                                        newPullParser = xmlPullParser;
                                                        str18 = str12;
                                                        str16 = str;
                                                    }
                                                }
                                                str6 = "block";
                                                z3 = true;
                                                aSKSManagerService = this;
                                                i4 = -1;
                                                str15 = null;
                                                i9 = Integer.parseInt(attributeValue5);
                                                i10 = Integer.parseInt(attributeValue6);
                                                i11 = Integer.parseInt(attributeValue4);
                                                unknownStore4 = unknownStore;
                                                z2 = z3;
                                                i16 = i4;
                                                str7 = str15;
                                                if (z2) {
                                                }
                                                str8 = str7;
                                                str9 = str19;
                                                str10 = "policy";
                                                str11 = str4;
                                                if (z2) {
                                                }
                                                xmlPullParser = newPullParser;
                                                str12 = str18;
                                                str13 = str9;
                                                hashMap2 = null;
                                                i2 = 1;
                                                z4 = z2;
                                                str22 = str8;
                                                i6 = i17;
                                                unknownStore3 = unknownStore4;
                                                str17 = str25;
                                                str20 = str11;
                                                eventType = xmlPullParser.next();
                                                arrayList2 = arrayList;
                                                hashMap3 = hashMap;
                                                str19 = str13;
                                                i5 = i2;
                                                fileReader2 = fileReader;
                                                newPullParser = xmlPullParser;
                                                str18 = str12;
                                                str16 = str;
                                            }
                                            i14 = 500;
                                            if (attributeValue9 == null) {
                                            }
                                            z5 = false;
                                            if (attributeValue10 != null) {
                                            }
                                            str6 = "block";
                                            z3 = true;
                                            aSKSManagerService = this;
                                            i4 = -1;
                                            str15 = null;
                                            i9 = Integer.parseInt(attributeValue5);
                                            i10 = Integer.parseInt(attributeValue6);
                                            i11 = Integer.parseInt(attributeValue4);
                                            unknownStore4 = unknownStore;
                                            z2 = z3;
                                            i16 = i4;
                                            str7 = str15;
                                            if (z2) {
                                            }
                                            str8 = str7;
                                            str9 = str19;
                                            str10 = "policy";
                                            str11 = str4;
                                            if (z2) {
                                            }
                                            xmlPullParser = newPullParser;
                                            str12 = str18;
                                            str13 = str9;
                                            hashMap2 = null;
                                            i2 = 1;
                                            z4 = z2;
                                            str22 = str8;
                                            i6 = i17;
                                            unknownStore3 = unknownStore4;
                                            str17 = str25;
                                            str20 = str11;
                                            eventType = xmlPullParser.next();
                                            arrayList2 = arrayList;
                                            hashMap3 = hashMap;
                                            str19 = str13;
                                            i5 = i2;
                                            fileReader2 = fileReader;
                                            newPullParser = xmlPullParser;
                                            str18 = str12;
                                            str16 = str;
                                        }
                                        i13 = 500;
                                        if (attributeValue8 != null) {
                                        }
                                        i14 = 500;
                                        if (attributeValue9 == null) {
                                        }
                                        z5 = false;
                                        if (attributeValue10 != null) {
                                        }
                                        str6 = "block";
                                        z3 = true;
                                        aSKSManagerService = this;
                                        i4 = -1;
                                        str15 = null;
                                        i9 = Integer.parseInt(attributeValue5);
                                        i10 = Integer.parseInt(attributeValue6);
                                        i11 = Integer.parseInt(attributeValue4);
                                        unknownStore4 = unknownStore;
                                        z2 = z3;
                                        i16 = i4;
                                        str7 = str15;
                                        if (z2) {
                                        }
                                        str8 = str7;
                                        str9 = str19;
                                        str10 = "policy";
                                        str11 = str4;
                                        if (z2) {
                                        }
                                        xmlPullParser = newPullParser;
                                        str12 = str18;
                                        str13 = str9;
                                        hashMap2 = null;
                                        i2 = 1;
                                        z4 = z2;
                                        str22 = str8;
                                        i6 = i17;
                                        unknownStore3 = unknownStore4;
                                        str17 = str25;
                                        str20 = str11;
                                        eventType = xmlPullParser.next();
                                        arrayList2 = arrayList;
                                        hashMap3 = hashMap;
                                        str19 = str13;
                                        i5 = i2;
                                        fileReader2 = fileReader;
                                        newPullParser = xmlPullParser;
                                        str18 = str12;
                                        str16 = str;
                                    }
                                } catch (IOException e4) {
                                    e = e4;
                                    hashMap2 = null;
                                    fileReader.close();
                                    e.printStackTrace();
                                    return hashMap2;
                                } catch (XmlPullParserException e5) {
                                    e = e5;
                                    hashMap2 = null;
                                    fileReader.close();
                                    e.printStackTrace();
                                    return hashMap2;
                                }
                            }
                            str7 = str22;
                            if (z2) {
                            }
                            str8 = str7;
                            str9 = str19;
                            str10 = "policy";
                            str11 = str4;
                            if (z2) {
                            }
                            xmlPullParser = newPullParser;
                            str12 = str18;
                            str13 = str9;
                            hashMap2 = null;
                            i2 = 1;
                            z4 = z2;
                            str22 = str8;
                            i6 = i17;
                            unknownStore3 = unknownStore4;
                            str17 = str25;
                            str20 = str11;
                            eventType = xmlPullParser.next();
                            arrayList2 = arrayList;
                            hashMap3 = hashMap;
                            str19 = str13;
                            i5 = i2;
                            fileReader2 = fileReader;
                            newPullParser = xmlPullParser;
                            str18 = str12;
                            str16 = str;
                        } catch (IOException e6) {
                            e = e6;
                            hashMap2 = null;
                        } catch (XmlPullParserException e7) {
                            e = e7;
                            hashMap2 = null;
                        }
                    } else if (eventType == 3) {
                        if (arrayList2.contains(newPullParser.getName()) && unknownStore3 != null) {
                            hashMap3.put(str17, unknownStore3);
                            str = str16;
                            str13 = str19;
                            xmlPullParser = newPullParser;
                            str12 = str18;
                            hashMap2 = null;
                            z4 = false;
                            str17 = null;
                            i9 = 0;
                            i10 = 0;
                            i11 = 0;
                            i2 = 1;
                            eventType = xmlPullParser.next();
                            arrayList2 = arrayList;
                            hashMap3 = hashMap;
                            str19 = str13;
                            i5 = i2;
                            fileReader2 = fileReader;
                            newPullParser = xmlPullParser;
                            str18 = str12;
                            str16 = str;
                        }
                        str = str16;
                        str13 = str19;
                        xmlPullParser = newPullParser;
                        str12 = str18;
                        hashMap2 = null;
                        i2 = 1;
                        eventType = xmlPullParser.next();
                        arrayList2 = arrayList;
                        hashMap3 = hashMap;
                        str19 = str13;
                        i5 = i2;
                        fileReader2 = fileReader;
                        newPullParser = xmlPullParser;
                        str18 = str12;
                        str16 = str;
                    } else {
                        try {
                            if (eventType == 4) {
                                if (i6 != 0 && z4 && str20 != null && str21 != null) {
                                    try {
                                        if (str20.equals("package")) {
                                            String text = newPullParser.getText();
                                            if (unknownStore3 != null && text != null && text.length() != 0) {
                                                unknownStore3.addCertPolicy(str21, i12, i7, i8, text, i13, i14, unknownStore3.getKey(), str24, i15);
                                                i12 = -1;
                                                i13 = -1;
                                                i14 = -1;
                                                i15 = -1;
                                                i7 = 0;
                                                i8 = 0;
                                            }
                                        }
                                    } catch (IOException e8) {
                                        e = e8;
                                        hashMap2 = null;
                                        try {
                                            fileReader.close();
                                        } catch (IOException unused) {
                                        }
                                        e.printStackTrace();
                                        return hashMap2;
                                    } catch (XmlPullParserException e9) {
                                        e = e9;
                                        hashMap2 = null;
                                        try {
                                            fileReader.close();
                                        } catch (IOException unused2) {
                                        }
                                        e.printStackTrace();
                                        return hashMap2;
                                    }
                                }
                                str = str16;
                                str13 = str19;
                                xmlPullParser = newPullParser;
                                str12 = str18;
                                hashMap2 = null;
                                i6 = 0;
                                i2 = 1;
                                eventType = xmlPullParser.next();
                                arrayList2 = arrayList;
                                hashMap3 = hashMap;
                                str19 = str13;
                                i5 = i2;
                                fileReader2 = fileReader;
                                newPullParser = xmlPullParser;
                                str18 = str12;
                                str16 = str;
                            }
                            eventType = xmlPullParser.next();
                            arrayList2 = arrayList;
                            hashMap3 = hashMap;
                            str19 = str13;
                            i5 = i2;
                            fileReader2 = fileReader;
                            newPullParser = xmlPullParser;
                            str18 = str12;
                            str16 = str;
                        } catch (IOException e10) {
                            e = e10;
                            fileReader.close();
                            e.printStackTrace();
                            return hashMap2;
                        } catch (XmlPullParserException e11) {
                            e = e11;
                            fileReader.close();
                            e.printStackTrace();
                            return hashMap2;
                        }
                        str = str16;
                        str13 = str19;
                        xmlPullParser = newPullParser;
                        str12 = str18;
                        hashMap2 = null;
                        i2 = 1;
                    }
                }
                fileReader = fileReader2;
                hashMap2 = null;
                fileReader.close();
                return hashMap;
            } catch (IOException e12) {
                e = e12;
                fileReader = fileReader2;
                hashMap2 = null;
            } catch (XmlPullParserException e13) {
                e = e13;
                fileReader = fileReader2;
                hashMap2 = null;
            }
        } catch (FileNotFoundException e14) {
            e14.printStackTrace();
            return null;
        } catch (IOException e15) {
            e15.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00d4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public RETVALUE checkTarget(CURPARAM curparam, Signature[] signatureArr, HashMap hashMap, String str, int i, CURSTATUS curstatus, String[] strArr, boolean z) {
        RETVALUE retvalue;
        int i2;
        String str2;
        RETVALUE retvalue2 = new RETVALUE();
        RETVALUE retvalue3 = retvalue2;
        retvalue2.set(4, 0, 0, 0, 0, 0);
        Slog.i("PackageInformation", " checkTarget sign BEFORE status:" + retvalue3.status + " SA:" + retvalue3.f1644SA + " policy=" + retvalue3.policy);
        if (signatureArr != null) {
            int i3 = 0;
            while (i3 < signatureArr.length) {
                try {
                    str2 = getSigHash(signatureArr[i3]);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    str2 = null;
                }
                if (str2 != null && hashMap.containsKey(str2)) {
                    UnknownStore unknownStore = (UnknownStore) hashMap.get(str2);
                    unknownStore.setPkgName(curparam.packageName);
                    unknownStore.setSigHash(curparam.sigHashValue);
                    unknownStore.setPkgSigHash(curparam.pkgSigHash);
                    unknownStore.setBaseCodePath(curparam.baseCodePath);
                    unknownStore.checkPolicy(curparam.pkgNameHash, retvalue3);
                    if (retvalue3.status == 1) {
                        try {
                            String convertToHex = convertToHex(getApkFileHashBytes(str));
                            if (convertToHex != null && !convertToHex.equals("null")) {
                                unknownStore.checkPolicyWithAppHash(curparam.pkgNameHash, convertToHex, retvalue3);
                            }
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                    }
                    if (z || retvalue3.status != 2) {
                        retvalue = retvalue3;
                        i2 = i;
                    } else {
                        retvalue = retvalue3;
                        i2 = i;
                        unknownStore.checkPolicyWithPEM(strArr, i2, curstatus.isLocUrlCase, retvalue);
                    }
                    if (retvalue.status == 0) {
                        return retvalue;
                    }
                    if (hashMap.containsKey("ALL")) {
                        UnknownStore unknownStore2 = (UnknownStore) hashMap.get("ALL");
                        unknownStore2.setPkgName(curparam.packageName);
                        unknownStore2.setSigHash(curparam.sigHashValue);
                        unknownStore2.setPkgSigHash(curparam.pkgSigHash);
                        unknownStore2.setBaseCodePath(curparam.baseCodePath);
                        unknownStore2.checkPolicy(curparam.pkgNameHash, retvalue);
                        if (retvalue.status == 1) {
                            try {
                                String convertToHex2 = convertToHex(getApkFileHashBytes(str));
                                if (convertToHex2 != null && !convertToHex2.equals("null")) {
                                    unknownStore2.checkPolicyWithAppHash(curparam.pkgNameHash, convertToHex2, retvalue);
                                }
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        if (!z && retvalue.status == 2) {
                            unknownStore2.checkPolicyWithPEM(strArr, i2, curstatus.isLocUrlCase, retvalue);
                        }
                    }
                    return retvalue;
                }
                i3++;
                retvalue3 = retvalue3;
            }
        }
        retvalue = retvalue3;
        i2 = i;
        if (hashMap.containsKey("ALL")) {
        }
        return retvalue;
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0093, code lost:
    
        if (r4 == null) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0095, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0099, code lost:
    
        return r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0083, code lost:
    
        if (r4 == null) goto L55;
     */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00a4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:68:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x009f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isValidZipFormat(String str) {
        ZipFile zipFile;
        ZipInputStream zipInputStream;
        Throwable th;
        if (str == null) {
            return true;
        }
        if (isDevDevice()) {
            Slog.d("PackageInformation", str);
        }
        if (!".apk".equals(str.substring(str.length() - 4).toLowerCase())) {
            return true;
        }
        ZipFile zipFile2 = null;
        boolean z = false;
        try {
            try {
                try {
                    zipFile = new ZipFile(str);
                } catch (IOException unused) {
                    return true;
                }
            } catch (ZipException unused2) {
                zipInputStream = null;
            } catch (IOException unused3) {
                zipInputStream = null;
            } catch (Throwable th2) {
                th = th2;
                zipFile = null;
                zipInputStream = null;
            }
        } catch (Throwable th3) {
            th = th3;
            zipFile = zipFile2;
        }
        try {
            zipInputStream = new ZipInputStream(new FileInputStream(str));
            try {
                ZipEntry nextEntry = zipInputStream.getNextEntry();
                boolean z2 = nextEntry != null;
                for (int i = 15; z2 && nextEntry != null && i != 0; i--) {
                    zipFile.getInputStream(nextEntry);
                    nextEntry.getCrc();
                    nextEntry.getCompressedSize();
                    nextEntry.getName();
                    nextEntry = zipInputStream.getNextEntry();
                }
                try {
                    zipFile.close();
                } catch (IOException unused4) {
                    z2 = true;
                }
                zipInputStream.close();
                return z2;
            } catch (ZipException unused5) {
                zipFile2 = zipFile;
                Log.e("AASA_ASKSManager", "Non-Valid Format[1]");
                if (zipFile2 != null) {
                    try {
                        zipFile2.close();
                    } catch (IOException unused6) {
                        z = true;
                    }
                }
            } catch (IOException unused7) {
                zipFile2 = zipFile;
                Log.e("AASA_ASKSManager", "Non-Valid Format[2]");
                if (zipFile2 != null) {
                    try {
                        zipFile2.close();
                    } catch (IOException unused8) {
                        z = true;
                    }
                }
            } catch (Throwable th4) {
                th = th4;
                if (zipFile != null) {
                    try {
                        zipFile.close();
                    } catch (IOException unused9) {
                    }
                }
                if (zipInputStream != null) {
                    throw th;
                }
                try {
                    zipInputStream.close();
                    throw th;
                } catch (IOException unused10) {
                    throw th;
                }
            }
        } catch (ZipException unused11) {
            zipInputStream = null;
        } catch (IOException unused12) {
            zipInputStream = null;
        } catch (Throwable th5) {
            th = th5;
            zipInputStream = null;
            th = th;
            if (zipFile != null) {
            }
            if (zipInputStream != null) {
            }
        }
    }

    public final String getSigByPackage(String str, int i) {
        PackageInfo packageInfoAsUser;
        SigningInfo signingInfo;
        Signature[] apkContentsSigners;
        try {
            if (this.mContext.getPackageManager() == null || (packageInfoAsUser = this.mContext.getPackageManager().getPackageInfoAsUser(str, 134217728, i)) == null || (signingInfo = packageInfoAsUser.signingInfo) == null || (apkContentsSigners = signingInfo.getApkContentsSigners()) == null || apkContentsSigners.length < 1) {
                return null;
            }
            return getSigHash(apkContentsSigners[0]);
        } catch (PackageManager.NameNotFoundException e) {
            Slog.e("PackageInformation", " Abnormal case : initiatingPackageName can not be modified " + e);
            return null;
        } catch (NoSuchAlgorithmException e2) {
            Slog.e("PackageInformation", " Abnormal case : NoSuchAlgorithmException " + e2);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0029  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isTrustedStoreCheck(String str, HashMap hashMap, String str2, int i) {
        ArrayList arrayList;
        boolean z;
        ArrayList arrayList2;
        String sHA256ForPkgName = getSHA256ForPkgName(str2);
        if (hashMap.containsKey(str2)) {
            arrayList = (ArrayList) hashMap.get(str2);
        } else if (hashMap.containsKey(sHA256ForPkgName)) {
            arrayList = (ArrayList) hashMap.get(sHA256ForPkgName);
        } else {
            arrayList = null;
            z = false;
            if (z) {
                if (hashMap.containsKey("ALL") && (arrayList2 = (ArrayList) hashMap.get("ALL")) != null) {
                    if (arrayList2.contains("ALL")) {
                        return true;
                    }
                    String sigByPackage = getSigByPackage(str2, i);
                    if (sigByPackage == null) {
                        Slog.i(str, "TS: ALL Unknown:" + str2);
                        return true;
                    }
                    if (arrayList2.contains(sigByPackage)) {
                        Slog.i(str, "TS: ALL " + str2 + XmlUtils.STRING_ARRAY_SEPARATOR + sigByPackage);
                        return true;
                    }
                    if (hashMap.containsKey("PERMISSION")) {
                        ArrayList arrayList3 = (ArrayList) hashMap.get("PERMISSION");
                        if (arrayList3 != null) {
                            try {
                                if (this.mContext.getPackageManager() != null) {
                                    PackageInfo packageInfoAsUser = this.mContext.getPackageManager().getPackageInfoAsUser(str2, IInstalld.FLAG_USE_QUOTA, i);
                                    if (packageInfoAsUser != null) {
                                        if (packageInfoAsUser.requestedPermissions != null) {
                                            int i2 = 0;
                                            while (true) {
                                                String[] strArr = packageInfoAsUser.requestedPermissions;
                                                if (i2 >= strArr.length) {
                                                    break;
                                                }
                                                if ((packageInfoAsUser.requestedPermissionsFlags[i2] & 2) != 0 && arrayList3.contains(strArr[i2])) {
                                                    if (isDevDevice()) {
                                                        Slog.i(str, "TS ALL Hit Permission:" + packageInfoAsUser.requestedPermissions[i2]);
                                                    }
                                                    return true;
                                                }
                                                i2++;
                                            }
                                        } else {
                                            Slog.e(str, "requestedPermissions is null");
                                        }
                                    } else {
                                        Slog.e(str, "PackageInfo is null");
                                    }
                                }
                            } catch (PackageManager.NameNotFoundException e) {
                                Slog.e(str, "Error : " + str2 + ":::::" + e);
                            }
                        }
                    } else {
                        Slog.i(str, "TS: ALL not Permission");
                    }
                    if (isDevDevice()) {
                        Slog.i(str, "TS: ALL not sig " + str2 + XmlUtils.STRING_ARRAY_SEPARATOR + sigByPackage);
                    } else {
                        Slog.i(str, "TS: ALL not sig " + str2);
                    }
                    return false;
                }
            } else if (arrayList != null) {
                if (arrayList.contains("ALL")) {
                    Slog.i(str, "TS Hit:" + str2);
                    return true;
                }
                String sigByPackage2 = getSigByPackage(str2, i);
                if ((arrayList.contains("null") && sigByPackage2 == null) || (sigByPackage2 != null && arrayList.contains(sigByPackage2))) {
                    Slog.i(str, "TS: Hit" + str2 + XmlUtils.STRING_ARRAY_SEPARATOR + sigByPackage2);
                    return true;
                }
                Slog.i(str, "TS: Hit not sig " + str2 + XmlUtils.STRING_ARRAY_SEPARATOR + sigByPackage2);
                return false;
            }
            Slog.e(str, "Check ALL case :" + str2);
            return false;
        }
        z = true;
        if (z) {
        }
        Slog.e(str, "Check ALL case :" + str2);
        return false;
    }

    public final boolean checkRampartFreePass(HashMap hashMap) {
        getASKSDataFromXML(45, hashMap);
        if (hashMap != null && hashMap.size() >= 1) {
            return true;
        }
        Slog.i("RAMPARTPackageInformation", "rampart: no superpass rule");
        return false;
    }

    public final boolean isRampartFreePass(String str, String str2, int i, HashMap hashMap) {
        boolean isTrustedStoreCheck = isTrustedStoreCheck("RAMPARTPackageInformation", hashMap, str2, i);
        Slog.i("RAMPARTPackageInformation", "rampart: superpass:" + isTrustedStoreCheck + " " + str2);
        if (isTrustedStoreCheck) {
            return isTrustedStoreCheck;
        }
        boolean isTrustedStoreCheck2 = isTrustedStoreCheck("RAMPARTPackageInformation", hashMap, str, i);
        Slog.i("RAMPARTPackageInformation", "rampart: superpass:" + isTrustedStoreCheck2 + " " + str);
        return isTrustedStoreCheck2;
    }

    public final boolean isSimpleTrustore(String str, int i, boolean z) {
        HashMap hashMap = new HashMap();
        if (z) {
            Slog.i("RAMPARTPackageInformation", "Simple TS : " + str + XmlUtils.STRING_ARRAY_SEPARATOR + i);
            if ("CN".equalsIgnoreCase(SemCscFeature.getInstance().getString("CountryISO"))) {
                getASKSDataFromXML(44, hashMap);
            } else {
                getASKSDataFromXML(43, hashMap);
            }
            if (hashMap.size() == 0) {
                Slog.e("RAMPARTPackageInformation", "Simple TS list does not exist");
                return false;
            }
        } else {
            String sigByPackage = getSigByPackage(str, i);
            if (sigByPackage == null) {
                sigByPackage = "null";
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Simple TS : ");
            sb.append(str);
            sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
            sb.append(i);
            sb.append(XmlUtils.STRING_ARRAY_SEPARATOR);
            if (!isDevDevice()) {
                sigByPackage = "";
            }
            sb.append(sigByPackage);
            Slog.i("PackageInformation", sb.toString());
            ArrayList targetNodeName = getTargetNodeName(SystemProperties.get("ro.product.model", "Unknown"));
            if (targetNodeName != null && targetNodeName.contains("SIMPLETRUSTEDSTORE")) {
                getASKSDataFromXML(41, hashMap);
            } else {
                Slog.e("PackageInformation", "no target of simple TS.");
                return false;
            }
        }
        return isTrustedStoreCheck("PackageInformation", hashMap, str, i);
    }

    public boolean isTrustedStore(String str, int i) {
        HashMap hashMap = new HashMap();
        String str2 = "PackageInformation";
        Slog.i("PackageInformation", "userId :" + i);
        if (Settings.Secure.getInt(this.mContext.getContentResolver(), "rampart_blocked_unknown_apps", 0) == 1) {
            if ("CN".equalsIgnoreCase(SemCscFeature.getInstance().getString("CountryISO"))) {
                getASKSDataFromXML(44, hashMap);
            } else {
                getASKSDataFromXML(43, hashMap);
            }
            str2 = "RAMPARTPackageInformation";
        } else {
            ArrayList targetNodeName = getTargetNodeName(SystemProperties.get("ro.product.model", "Unknown"));
            if (targetNodeName != null && targetNodeName.contains("TRUSTEDSTORE")) {
                getASKSDataFromXML(35, hashMap);
            } else {
                Slog.e("PackageInformation", "skip TS..");
                return true;
            }
        }
        if (hashMap.size() < 1) {
            Slog.i(str2, "skip TS due to non policy");
            return true;
        }
        return isTrustedStoreCheck(str2, hashMap, str, i);
    }

    public final boolean isDevDevice() {
        return "0x1".equals(SystemProperties.get("ro.boot.em.status"));
    }

    public final class CURSTATUS {
        public String target_model;
        public int totalList;
        public String totalListString;
        public boolean isLocUrlCase = false;
        public boolean isLocZipCase = false;
        public boolean isLocWebCase = false;
        public boolean isLocAccessibilityCase = false;
        public boolean isIP = false;
        public boolean isDevDevice = false;
        public boolean isTablet = false;
        public boolean isValidZip = true;
        public boolean isCheckZipFormat = false;
        public boolean isAllowSelfUpdate = false;
        public boolean isBlockSelfUpdateWithPEM = false;
        public boolean isLimitOnlyKorMCC = false;
        public boolean isTabletExcepted = false;
        public boolean isHitTargetDomain = false;
        public boolean isCheckRequestInstallPEM = false;
        public boolean hasReqInstallPEM = false;

        public CURSTATUS(String str) {
            this.target_model = str;
        }
    }

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
        public String saReportValue;
        public int sdkVersion;
        public String[] servicePermList;
        public String sigHashValue;
        public Signature[] signatures;
        public int userId;

        public CURPARAM(String str, String[] strArr, String[] strArr2, String str2, Signature[] signatureArr, String str3, String str4, int i, String str5, String str6, int i2) {
            this.packageName = str;
            this.permList = strArr;
            this.servicePermList = strArr2;
            this.baseCodePath = str2;
            this.signatures = signatureArr;
            this.initiatingPackageName = str3;
            this.originatingPackageName = str4;
            this.sdkVersion = i;
            this.referralUrl = str5;
            this.downloadUrl = str6;
            this.userId = i2;
        }
    }

    public final void checkCurStatus(CURSTATUS curstatus, CURPARAM curparam, ArrayList arrayList, boolean z) {
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
        boolean z2 = false;
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
        String str8 = curparam.referralUrl;
        if (str8 != null && str8.contains("WEB")) {
            curstatus.isLocWebCase = true;
            Slog.i("PackageInformation", "This is Web case:" + curstatus.isLocWebCase);
        }
        String str9 = curparam.referralUrl;
        if (str9 != null && "ZIP".equals(str9)) {
            curstatus.isLocZipCase = true;
            Slog.i("PackageInformation", "This is zip case:" + curstatus.isLocZipCase);
        }
        String str10 = curparam.downloadUrl;
        if (str10 != null) {
            curstatus.isLocUrlCase = true;
            String domainName = getDomainName(str10);
            curparam.domain = domainName;
            curstatus.isIP = isIPaddress(domainName);
            curparam.hashDomain = getSHA256ForPkgName(curparam.domain);
            curparam.saReportValue = curparam.downloadUrl;
        }
        Signature[] signatureArr = curparam.signatures;
        if (signatureArr != null && signatureArr.length > 0) {
            try {
                curparam.sigHashValue = getSigHash(signatureArr[0]);
            } catch (NoSuchAlgorithmException e) {
                curparam.sigHashValue = null;
                e.printStackTrace();
            }
        }
        if (curparam.sigHashValue != null) {
            curparam.pkgSigHash = getSHA256ForPkgName(curparam.pkgNameHash + curparam.sigHashValue);
            if (isDevDevice()) {
                Slog.d("PackageInformation", "pkgSigHash::" + curparam.pkgSigHash);
            }
        } else {
            curparam.pkgSigHash = null;
        }
        Slog.i("PackageInformation", "This is tablet device.");
        curstatus.isTablet = true;
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
                if ((str11 != null && split[0].equals(str11)) || ((str7 = curparam.originatingPackageName) != null && split[0].equals(str7))) {
                    if (!split[1].equals("except")) {
                        Slog.i("PackageInformation", "installer:" + curparam.initiatingPackageName + " :: " + curparam.originatingPackageName);
                        z2 = true;
                    }
                }
            }
        }
        if (!curstatus.isLocZipCase && z2 && (((installedAppsDataFromXML3 = getInstalledAppsDataFromXML("requestInstallerZip", null)) != null && (str6 = curparam.initiatingPackageName) != null && installedAppsDataFromXML3.contains(str6)) || ((str5 = curparam.originatingPackageName) != null && installedAppsDataFromXML3.contains(str5)))) {
            curstatus.isLocZipCase = true;
        }
        if (!curstatus.isLocZipCase && z2 && (installedAppsDataFromXML2 = getInstalledAppsDataFromXML("accessibility", null)) != null && (((str3 = curparam.initiatingPackageName) != null && installedAppsDataFromXML2.contains(str3)) || ((str4 = curparam.originatingPackageName) != null && installedAppsDataFromXML2.contains(str4)))) {
            curstatus.isLocZipCase = true;
        }
        if (!curstatus.isLocAccessibilityCase && curstatus.isCheckRequestInstallPEM && z2 && (((installedAppsDataFromXML = getInstalledAppsDataFromXML("hasReqInstallPEM", null)) != null && (str2 = curparam.initiatingPackageName) != null && installedAppsDataFromXML.contains(str2)) || ((str = curparam.originatingPackageName) != null && installedAppsDataFromXML.contains(str)))) {
            curstatus.isLocAccessibilityCase = true;
        }
        curstatus.totalList = 27;
        if (curstatus.isLocZipCase) {
            HashMap hashMap = new HashMap();
            getASKSDataFromXML(26, hashMap);
            Slog.i("PackageInformation", "changed By zip");
            if (hashMap.containsKey("ALL") || hashMap.containsKey(curstatus.target_model)) {
                curstatus.totalList = 28;
            }
        } else if (curstatus.isLocAccessibilityCase) {
            HashMap hashMap2 = new HashMap();
            getASKSDataFromXML(26, hashMap2);
            if (hashMap2.containsKey("ALL") || hashMap2.containsKey(curstatus.target_model)) {
                curstatus.totalList = 33;
            }
        } else if (curstatus.isIP) {
            Slog.i("PackageInformation", "changed By IP");
            HashMap hashMap3 = new HashMap();
            getASKSDataFromXML(26, hashMap3);
            if (hashMap3.containsKey("ALL") || hashMap3.containsKey(curstatus.target_model)) {
                curstatus.totalList = 34;
            }
        }
        int i4 = curstatus.totalList;
        if (27 == i4) {
            curstatus.totalListString = "TOTALLIST";
            return;
        }
        if (28 == i4) {
            curstatus.totalListString = "TOTALLIST_ZIP";
        } else if (33 == i4) {
            curstatus.totalListString = "TOTALLIST_A11Y";
        } else {
            curstatus.totalListString = "TOTALLIST_WEB";
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0032  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void SAreport(RETVALUE retvalue, CURSTATUS curstatus, CURPARAM curparam) {
        boolean z;
        String str;
        String str2;
        String[] split;
        if (retvalue.f1644SA == 0) {
            return;
        }
        ArrayList installedAppsDataFromXML = getInstalledAppsDataFromXML("isInstalledList", null);
        if (installedAppsDataFromXML == null || !installedAppsDataFromXML.contains(curparam.packageName)) {
            HashMap hashMap = new HashMap();
            getDataByDevice("/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_SA_REPORTED_NEW.xml", hashMap);
            if (!hashMap.containsKey(curparam.packageName)) {
                setDataToDevice("/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_SA_REPORTED_NEW.xml", curparam.packageName, null);
                z = true;
                if (z) {
                    return;
                }
                String num = Integer.toString(retvalue.f1644SA);
                if (!curstatus.isValidZip) {
                    retvalue.policy = 1;
                    num = "3050";
                }
                if (curstatus.isLocWebCase && (str2 = curparam.referralUrl) != null && (split = str2.split("_")) != null && split.length > 1) {
                    num = split[1] + num;
                }
                String str3 = curparam.initiatingPackageName;
                if (curparam.originatingPackageName != null) {
                    str = str3 + "^" + curparam.originatingPackageName;
                } else {
                    str = str3 + "^NA";
                }
                String str4 = curparam.packageName + "^" + curparam.sigHashValue + "^" + str;
                if (str4.length() >= 200) {
                    str4 = curparam.packageName + "^" + curparam.sigHashValue + "^NA^NA";
                }
                setSamsungAnalyticsLog(num, str4, curparam.saReportValue);
                return;
            }
        }
        z = false;
        if (z) {
        }
    }

    public final void printCurInfo(CURSTATUS curstatus, CURPARAM curparam) {
        Slog.i("PackageInformation", "pkg:" + curparam.packageName);
        Slog.i("PackageInformation", "-- initiating :" + curparam.initiatingPackageName);
        Slog.i("PackageInformation", "-- originating :" + curparam.originatingPackageName);
        Slog.i("PackageInformation", "-- sdkVersion :" + curparam.sdkVersion);
        Slog.i("PackageInformation", "-- ASKS Version : " + mASKSPolicyVersion);
        Slog.i("PackageInformation", "-- device " + curstatus.target_model);
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
                Slog.d("PackageInformation", "-- DH :" + curparam.hashDomain);
            }
            Slog.d("PackageInformation", "-- referral Url : " + curparam.referralUrl);
            int i = 0;
            while (true) {
                Signature[] signatureArr = curparam.signatures;
                String str = null;
                if (i >= signatureArr.length) {
                    break;
                }
                try {
                    str = getSigHash(signatureArr[i]);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                Slog.d("PackageInformation", "DEBUG pkg:" + curparam.packageName + " sig [" + i + "]::" + str);
                i++;
            }
            Slog.d("PackageInformation", "DEBUG pkg:" + curparam.packageName + " pkgNameHash::" + curparam.pkgNameHash);
            try {
                String convertToHex = convertToHex(getApkFileHashBytes(curparam.baseCodePath));
                if (!convertToHex.equals("null")) {
                    Slog.d("PackageInformation", "DEBUG pkg:" + curparam.packageName + " apkFileHash::" + convertToHex);
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            Slog.d("PackageInformation", "DEBUG pkg:" + curparam.packageName + " api::" + curparam.sdkVersion);
            for (int i2 = 0; i2 < curparam.permList.length; i2++) {
                Slog.d("PackageInformation", "DEBUG pem:" + curparam.permList[i2]);
            }
            if (curparam.servicePermList != null) {
                for (int i3 = 0; i3 < curparam.servicePermList.length; i3++) {
                    Slog.d("PackageInformation", "DEBUG servicePem:" + curparam.servicePermList[i3]);
                }
            }
            ArrayList installedAppsDataFromXML = getInstalledAppsDataFromXML("isInstalledList", null);
            if (installedAppsDataFromXML != null) {
                Slog.d("PackageInformation", "DEBUG isInstalledList " + installedAppsDataFromXML.toString());
            }
            ArrayList installedAppsDataFromXML2 = getInstalledAppsDataFromXML("requestInstallerZip", null);
            if (installedAppsDataFromXML2 != null) {
                Slog.d("PackageInformation", "DEBUG requestInstallerZip " + installedAppsDataFromXML2.toString());
            }
            ArrayList installedAppsDataFromXML3 = getInstalledAppsDataFromXML("overlay", null);
            if (installedAppsDataFromXML3 != null) {
                Slog.d("PackageInformation", "DEBUG overlay " + installedAppsDataFromXML3.toString());
            }
            ArrayList installedAppsDataFromXML4 = getInstalledAppsDataFromXML("blockExecute", null);
            if (installedAppsDataFromXML4 != null) {
                Slog.d("PackageInformation", "DEBUG blockExecute " + installedAppsDataFromXML4.toString());
            }
            ArrayList installedAppsDataFromXML5 = getInstalledAppsDataFromXML("allowExecute", null);
            if (installedAppsDataFromXML5 != null) {
                Slog.d("PackageInformation", "DEBUG allowExecute " + installedAppsDataFromXML5.toString());
            }
            ArrayList installedAppsDataFromXML6 = getInstalledAppsDataFromXML("initType", null);
            if (installedAppsDataFromXML6 != null) {
                Slog.d("PackageInformation", "DEBUG initType " + installedAppsDataFromXML6.toString());
            }
            ArrayList installedAppsDataFromXML7 = getInstalledAppsDataFromXML("accessibility", null);
            if (installedAppsDataFromXML7 != null) {
                Slog.d("PackageInformation", "DEBUG accessibility " + installedAppsDataFromXML7.toString());
            }
            ArrayList installedAppsDataFromXML8 = getInstalledAppsDataFromXML("hasReqInstallPEM", null);
            if (installedAppsDataFromXML8 != null) {
                Slog.d("PackageInformation", "DEBUG hasReqInstallPEM " + installedAppsDataFromXML8.toString());
            }
        }
    }

    public boolean isTrustedSelfUpdate(String str, String[] strArr) {
        PackageInfo packageInfo;
        ArrayList arrayList;
        Slog.i("PackageInformation", "check selfupdate..");
        HashMap hashMap = new HashMap();
        ArrayList arrayList2 = new ArrayList();
        getASKSDataFromXML(30, hashMap);
        if (hashMap.containsKey("CHECK") && (arrayList = (ArrayList) hashMap.get("CHECK")) != null) {
            for (int i = 0; i < strArr.length; i++) {
                if (arrayList.contains(strArr[i])) {
                    arrayList2.add(strArr[i]);
                    if (isDevDevice()) {
                        Slog.i("PackageInformation", "adding pem:" + strArr[i]);
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
            if (context == null || context.getPackageManager() == null || (packageInfo = this.mContext.getPackageManager().getPackageInfo(str, IInstalld.FLAG_USE_QUOTA)) == null) {
                return true;
            }
            if (packageInfo.requestedPermissions != null) {
                for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                    String str2 = (String) arrayList2.get(i2);
                    int i3 = 0;
                    int i4 = 0;
                    while (true) {
                        if (i3 >= packageInfo.requestedPermissions.length) {
                            break;
                        }
                        if (isDevDevice()) {
                            Slog.i("PackageInformation", "permission:" + packageInfo.requestedPermissions[i3]);
                        }
                        if (!str2.equals(packageInfo.requestedPermissions[i3])) {
                            i4++;
                            i3++;
                        } else if (isDevDevice()) {
                            Slog.i("PackageInformation", "installed app already has " + str2);
                        } else {
                            Slog.i("PackageInformation", "The target perm is included in the installed app.");
                        }
                    }
                    if (i4 == packageInfo.requestedPermissions.length) {
                        if (isDevDevice()) {
                            Slog.i("PackageInformation", "installed app does not have " + str2 + " :" + i4);
                        } else {
                            Slog.i("PackageInformation", "The installed app doesn't have target permission.");
                        }
                        return false;
                    }
                }
                return true;
            }
            Slog.i("PackageInformation", "self requestedPermissions is null");
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Slog.i("PackageInformation", "self :" + e);
            return true;
        }
    }

    public final void changeStatusForDev(CURPARAM curparam, CURSTATUS curstatus) {
        ArrayList arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3;
        ArrayList arrayList4;
        ArrayList arrayList5;
        ArrayList arrayList6;
        if (!isDevDevice()) {
            Slog.i("PackageInformation", "disable DevParm option.");
            return;
        }
        HashMap hashMap = new HashMap();
        getASKSDataFromXML(47, hashMap);
        Slog.d("PackageInformation", "changeStatusForDev");
        if (hashMap.containsKey("initiatingPackageName") && (arrayList6 = (ArrayList) hashMap.get("initiatingPackageName")) != null) {
            Slog.d("PackageInformation", "changeStatus[init]:" + curparam.initiatingPackageName + " is changed to " + ((String) arrayList6.get(0)));
            curparam.initiatingPackageName = (String) arrayList6.get(0);
        }
        if (hashMap.containsKey("originatingPackageName") && (arrayList5 = (ArrayList) hashMap.get("originatingPackageName")) != null) {
            Slog.d("PackageInformation", "changeStatus[orig]:" + curparam.originatingPackageName + " is changed to " + ((String) arrayList5.get(0)));
            curparam.originatingPackageName = (String) arrayList5.get(0);
        }
        if (hashMap.containsKey("downloadUrl") && (arrayList4 = (ArrayList) hashMap.get("downloadUrl")) != null) {
            Slog.d("PackageInformation", "changeStatus[downUrl]:" + curparam.downloadUrl + " is changed to " + ((String) arrayList4.get(0)));
            curparam.downloadUrl = (String) arrayList4.get(0);
        }
        if (hashMap.containsKey("packageName") && (arrayList3 = (ArrayList) hashMap.get("packageName")) != null) {
            Slog.d("PackageInformation", "changeStatus[PkgName]:" + curparam.packageName + " is changed to " + ((String) arrayList3.get(0)));
            curparam.packageName = (String) arrayList3.get(0);
        }
        if (hashMap.containsKey("permList") && (arrayList2 = (ArrayList) hashMap.get("permList")) != null) {
            String[] strArr = new String[arrayList2.size()];
            for (int i = 0; i < arrayList2.size(); i++) {
                Slog.d("PackageInformation", "changeStatus[Pem]:" + strArr[i] + " is changed to " + ((String) arrayList2.get(i)));
                strArr[i] = (String) arrayList2.get(i);
            }
            curparam.permList = strArr;
        }
        if (!hashMap.containsKey("servicePermList") || (arrayList = (ArrayList) hashMap.get("servicePermList")) == null) {
            return;
        }
        String[] strArr2 = new String[arrayList.size()];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            Slog.d("PackageInformation", "changeStatus[ServicePem]:" + strArr2[i2] + " is changed to " + ((String) arrayList.get(i2)));
            strArr2[i2] = (String) arrayList.get(i2);
        }
        curparam.servicePermList = strArr2;
    }

    public HashMap checkPolicyFileWithDelta(int i, ArrayList arrayList) {
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
            Slog.d("PackageInformation", "base: " + i3 + ", delta: " + i2);
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

    /* JADX WARN: Removed duplicated region for block: B:193:0x0578  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x05b4  */
    /* JADX WARN: Removed duplicated region for block: B:250:0x058e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int checkUnknownSourcePackage(String str, String[] strArr, String[] strArr2, String str2, Signature[] signatureArr, String str3, String str4, String str5, int i, String str6, String str7, int i2) {
        String str8;
        int i3;
        boolean z;
        String str9;
        boolean z2;
        ArrayList arrayList;
        CURPARAM curparam;
        Object obj;
        String str10;
        int i4;
        String str11;
        ArrayList arrayList2;
        int i5;
        String str12;
        ArrayList arrayList3;
        HashMap checkPolicyFileWithDelta;
        String convertItoS;
        boolean z3;
        enforceSystemOrRoot("Only the system can claim checkUnknownSourcePackage");
        if (str == null) {
            Slog.e("PackageInformation", "Adnormal case: Package Name is null !!!!");
            return 0;
        }
        int i6 = SystemProperties.getInt("ro.build.version.oneui", 0);
        boolean z4 = true;
        if (Settings.Secure.getInt(this.mContext.getContentResolver(), "rampart_blocked_unknown_apps", 0) == 1) {
            HashMap hashMap = new HashMap();
            if (checkRampartFreePass(hashMap) && isRampartFreePass(str3, str4, i2, hashMap)) {
                return 0;
            }
            if (i6 >= 60101) {
                if (str4 != null && str.equals(str4) && isSimpleTrustore(str4, i2, true)) {
                    return 0;
                }
                if (str.equals(str3)) {
                    Slog.i("RAMPARTPackageInformation", "self update -> block for rampart");
                    return 130;
                }
            } else if ((str.equals(str3) || (str4 != null && str.equals(str4))) && isSimpleTrustore(str4, i2, true)) {
                return 0;
            }
            Slog.e("RAMPARTPackageInformation", "" + str + "::" + str3 + "::" + str4);
            return 127;
        }
        String str13 = SystemProperties.get("ro.csc.countryiso_code");
        if (!(str13 != null && str13.equals("KR"))) {
            Slog.i("PackageInformation", "This is global Project. skip.");
            return 0;
        }
        if (signatureArr != null && str3 != null) {
            if (isSignatureMatched(str, signatureArr) != -1) {
                Slog.i("PackageInformation", "OEM SIGNED:" + str);
                return 0;
            }
            if (str4 != null && isSimpleTrustore(str4, i2, false)) {
                Slog.e("PackageInformation", "Simple TS:" + str + " From " + str4);
                return 0;
            }
            if (str3.equals(str)) {
                ArrayList installedAppsDataFromXML = getInstalledAppsDataFromXML("initType", null);
                if (installedAppsDataFromXML != null) {
                    z3 = true;
                    for (int i7 = 0; i7 < installedAppsDataFromXML.size(); i7++) {
                        String[] split = ((String) installedAppsDataFromXML.get(i7)).split(",");
                        if (split != null) {
                            if (split.length == 2 && split[0].equals(str)) {
                                z4 = true;
                                if (!split[1].equals("except")) {
                                    z3 = isTrustedSelfUpdate(str, strArr);
                                }
                            } else {
                                z4 = true;
                            }
                        }
                    }
                } else {
                    z3 = true;
                }
                if (z3) {
                    Slog.i("PackageInformation", "SELF UPDATE: " + str);
                    return 0;
                }
                z = z4;
            } else {
                z = false;
            }
            String str14 = SystemProperties.get("ro.product.model", "Unknown");
            ArrayList targetNodeName = getTargetNodeName(str14);
            if (targetNodeName != null && targetNodeName.size() > 0) {
                CURPARAM curparam2 = new CURPARAM(str, strArr, strArr2, str2, signatureArr, str3, str4, i, str6, str7, i2);
                CURSTATUS curstatus = new CURSTATUS(str14);
                changeStatusForDev(curparam2, curstatus);
                checkCurStatus(curstatus, curparam2, targetNodeName, z);
                ArrayList arrayList4 = new ArrayList();
                arrayList4.add("CERT");
                HashMap checkPolicyFileWithDelta2 = checkPolicyFileWithDelta(38, arrayList4);
                if (checkPolicyFileWithDelta2 == null || checkPolicyFileWithDelta2.isEmpty()) {
                    str9 = str;
                    z2 = z;
                    arrayList = targetNodeName;
                    curparam = curparam2;
                    obj = "except";
                    str10 = "::";
                    i4 = 1;
                    str11 = "PackageInformation";
                } else {
                    z2 = z;
                    arrayList = targetNodeName;
                    str9 = str;
                    curparam = curparam2;
                    RETVALUE checkTarget = checkTarget(curparam2, signatureArr, checkPolicyFileWithDelta2, str2, i, curstatus, curparam2.permList, true);
                    if (curstatus.isDevDevice) {
                        applyScpmPolicy("/data/system/.aasa/ASKS.zip");
                        refreshInstalledUnknownList_NEW();
                        applyExecutePolicy();
                    }
                    if (checkTarget != null && checkTarget.status == 0) {
                        String convertItoS2 = convertItoS(checkTarget.policy);
                        if (convertItoS2.startsWith("block")) {
                            SAreport(checkTarget, curstatus, curparam);
                            if (isDevDevice()) {
                                Slog.i("PackageInformation", "[FIRST BL]:" + str9 + "::" + curparam.pkgNameHash + "::" + curparam.sigHashValue + "::" + checkTarget.f1644SA);
                            }
                            if (z2) {
                                return 130;
                            }
                            if (curstatus.isTablet) {
                                return 1;
                            }
                            return checkTarget.policy;
                        }
                        obj = "except";
                        str10 = "::";
                        str11 = "PackageInformation";
                        i4 = 1;
                        if (convertItoS2.equals(obj)) {
                            SAreport(checkTarget, curstatus, curparam);
                            addUnknownAppList(str, signatureArr, checkTarget, convertItoS2, curstatus.isLocZipCase, curstatus.isLocAccessibilityCase, curstatus.hasReqInstallPEM);
                            if (!isDevDevice()) {
                                return 0;
                            }
                            Slog.i(str11, "[FIRST AL]:" + str9 + str10 + curparam.pkgNameHash + str10 + curparam.sigHashValue);
                            return 0;
                        }
                        if (curstatus.isAllowSelfUpdate) {
                            Slog.i(str11, str9 + " is selfupdated..");
                            return 0;
                        }
                    } else {
                        obj = "except";
                        str10 = "::";
                        str11 = "PackageInformation";
                        i4 = 1;
                        if (curstatus.isAllowSelfUpdate) {
                            Slog.i(str11, str9 + " is selfupdated..");
                            return 0;
                        }
                    }
                }
                printCurInfo(curstatus, curparam);
                if (curstatus.isLimitOnlyKorMCC) {
                    TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
                    if (telephonyManager != null) {
                        int carrierIdFromSimMccMnc = telephonyManager.getCarrierIdFromSimMccMnc();
                        String networkOperator = telephonyManager.getNetworkOperator();
                        if (carrierIdFromSimMccMnc != -1) {
                            if (!TextUtils.isEmpty(networkOperator)) {
                                if (networkOperator.startsWith("450")) {
                                    Slog.i(str11, " Keep checking by " + carrierIdFromSimMccMnc + " " + networkOperator);
                                } else {
                                    if (!curstatus.isDevDevice) {
                                        Slog.i(str11, " stop checking by " + carrierIdFromSimMccMnc + " " + networkOperator);
                                        return 0;
                                    }
                                    Slog.i(str11, " DevDevice, Keep checking by " + carrierIdFromSimMccMnc + " " + networkOperator);
                                }
                            } else {
                                Slog.i(str11, " Keep checking by no operator");
                            }
                        } else {
                            if (!curstatus.isDevDevice) {
                                Slog.i(str11, " stop checking by " + carrierIdFromSimMccMnc + " " + networkOperator);
                                return 0;
                            }
                            Slog.i(str11, " DevDevice, keep checking by " + carrierIdFromSimMccMnc + " " + networkOperator);
                        }
                    } else {
                        Slog.i(str11, "telephonyManager null");
                    }
                }
                if (curstatus.isTabletExcepted && curstatus.isTablet) {
                    if (curstatus.isDevDevice) {
                        Slog.i(str11, "keep checking since dev device even TabletExcepted");
                    } else {
                        Slog.i(str11, "by tablet");
                        return 0;
                    }
                }
                Slog.e(str11, "more checking...");
                ArrayList arrayList5 = arrayList;
                HashMap checkPolicyFileWithDelta3 = checkPolicyFileWithDelta(34, arrayList5);
                if (checkPolicyFileWithDelta3 == null || checkPolicyFileWithDelta3.isEmpty() || !checkPolicyFileWithDelta3.containsKey("ALL")) {
                    arrayList2 = arrayList5;
                    i5 = i4;
                } else {
                    RETVALUE retvalue = new RETVALUE();
                    retvalue.set(4, 0, 0, 0, 0, 0);
                    UnknownStore unknownStore = (UnknownStore) checkPolicyFileWithDelta3.get("ALL");
                    ArrayList regexDomainList = unknownStore.getRegexDomainList();
                    arrayList2 = arrayList5;
                    if (regexDomainList != null) {
                        int i8 = 0;
                        while (i8 < regexDomainList.size()) {
                            Slog.i(str11, "[D]:" + ((String) regexDomainList.get(i8)));
                            i8++;
                            regexDomainList = regexDomainList;
                        }
                    }
                    if (unknownStore.checkRegexTarget(curparam.domain, retvalue, true)) {
                        Slog.i(str11, "regex domain Hit");
                    } else if (unknownStore.checkRegexTarget(curparam.packageName, retvalue, false)) {
                        Slog.i(str11, "regex package Hit");
                    }
                    if (retvalue.status == 0) {
                        String convertItoS3 = convertItoS(retvalue.policy);
                        SAreport(retvalue, curstatus, curparam);
                        Slog.i(str11, "[DD]:" + str9 + str10 + curparam.pkgNameHash + str10 + curparam.sigHashValue + str10 + retvalue.policy + str10 + convertItoS3 + "::zipFormat:" + curstatus.isValidZip + str10 + retvalue.f1644SA);
                        if (convertItoS3.startsWith("block")) {
                            if (z2) {
                                return 130;
                            }
                            if (curstatus.isTablet) {
                                return 1;
                            }
                            return retvalue.policy;
                        }
                        if (convertItoS3.equals(obj)) {
                            addUnknownAppList(str, signatureArr, retvalue, convertItoS3, curstatus.isLocZipCase, curstatus.isLocAccessibilityCase, curstatus.hasReqInstallPEM);
                            return 0;
                        }
                    }
                    retvalue.set(4, 0, 0, 0, 0, 0);
                    unknownStore.checkDomain(curparam.domain, retvalue);
                    if (retvalue.status != 0 || (convertItoS = convertItoS(retvalue.policy)) == null) {
                        i5 = 1;
                    } else if (convertItoS.startsWith("block") || convertItoS.equals(obj)) {
                        SAreport(retvalue, curstatus, curparam);
                        Slog.i(str11, "[D]:" + str9 + str10 + curparam.pkgNameHash + str10 + curparam.sigHashValue + str10 + retvalue.policy + str10 + convertItoS + "::zipFormat:" + curstatus.isValidZip + str10 + retvalue.f1644SA);
                        if (convertItoS.startsWith("block")) {
                            if (z2) {
                                return 130;
                            }
                            if (curstatus.isTablet) {
                                return 1;
                            }
                            return retvalue.policy;
                        }
                        i5 = 1;
                        if (convertItoS.equals(obj)) {
                            addUnknownAppList(str, signatureArr, retvalue, convertItoS, curstatus.isLocZipCase, curstatus.isLocAccessibilityCase, curstatus.hasReqInstallPEM);
                            return 0;
                        }
                    } else {
                        str12 = str3;
                        i5 = 1;
                        if (get3rdTargetNodeName(str12) == null) {
                            arrayList3 = new ArrayList();
                            arrayList3.add("CERT");
                            arrayList3.add(get3rdTargetNodeName(str12));
                            Slog.e(str11, "third party case..");
                        } else if (get3rdTargetNodeName(str4) != null) {
                            arrayList3 = new ArrayList();
                            arrayList3.add("CERT");
                            arrayList3.add(get3rdTargetNodeName(str4));
                            Slog.e(str11, "third party case..(origin)");
                        } else {
                            arrayList3 = arrayList2;
                        }
                        if (isDevDevice()) {
                            Slog.d(str11, " total list[" + curstatus.totalListString + "]");
                        }
                        checkPolicyFileWithDelta = checkPolicyFileWithDelta(curstatus.totalList, arrayList3);
                        if (checkPolicyFileWithDelta != null || checkPolicyFileWithDelta.isEmpty()) {
                            str8 = str11;
                        } else {
                            Object obj2 = obj;
                            int i9 = i5;
                            str8 = str11;
                            RETVALUE checkTarget2 = checkTarget(curparam, signatureArr, checkPolicyFileWithDelta, str2, i, curstatus, curparam.permList, false);
                            if (checkTarget2 != null) {
                                String convertItoS4 = convertItoS(checkTarget2.policy);
                                if (convertItoS4.startsWith("warning") && curstatus.isCheckZipFormat) {
                                    curstatus.isValidZip = isValidZipFormat(str2);
                                }
                                SAreport(checkTarget2, curstatus, curparam);
                                if (isDevDevice()) {
                                    Slog.i(str8, "[RET]:" + str9 + str10 + curparam.pkgNameHash + str10 + curparam.sigHashValue + str10 + checkTarget2.policy + str10 + convertItoS4 + "::zipFormat:" + curstatus.isValidZip + str10 + checkTarget2.f1644SA);
                                } else {
                                    Slog.i(str8, "[RET]:" + str9 + str10 + checkTarget2.policy + str10 + convertItoS4 + str10 + checkTarget2.f1644SA);
                                }
                                if (convertItoS4.startsWith("warning")) {
                                    if (!curstatus.isValidZip) {
                                        Slog.i(str8, str9 + ":format:" + curstatus.isValidZip);
                                        if (z2) {
                                            return 130;
                                        }
                                        return i9;
                                    }
                                    if (curstatus.isBlockSelfUpdateWithPEM) {
                                        Slog.i(str8, str9 + " is selfupdated");
                                        return 130;
                                    }
                                    addUnknownAppList(str, signatureArr, checkTarget2, convertItoS4, curstatus.isLocZipCase, curstatus.isLocAccessibilityCase, curstatus.hasReqInstallPEM);
                                    if (z2) {
                                        return 0;
                                    }
                                    if (curstatus.isTablet) {
                                        return 100;
                                    }
                                    return checkTarget2.policy;
                                }
                                if (convertItoS4.startsWith("block")) {
                                    if (z2) {
                                        return 130;
                                    }
                                    return curstatus.isTablet ? i9 : checkTarget2.policy;
                                }
                                if (!convertItoS4.equals(obj2)) {
                                    return 0;
                                }
                                addUnknownAppList(str, signatureArr, checkTarget2, convertItoS4, curstatus.isLocZipCase, curstatus.isLocAccessibilityCase, curstatus.hasReqInstallPEM);
                                return 0;
                            }
                        }
                        i3 = 0;
                        Slog.e(str8, "Adnormal case: CHECK TAGET DEVICE");
                        return i3;
                    }
                }
                str12 = str3;
                if (get3rdTargetNodeName(str12) == null) {
                }
                if (isDevDevice()) {
                }
                checkPolicyFileWithDelta = checkPolicyFileWithDelta(curstatus.totalList, arrayList3);
                if (checkPolicyFileWithDelta != null) {
                }
                str8 = str11;
                i3 = 0;
                Slog.e(str8, "Adnormal case: CHECK TAGET DEVICE");
                return i3;
            }
        }
        str8 = "PackageInformation";
        i3 = 0;
        Slog.e(str8, "Adnormal case: CHECK TAGET DEVICE");
        return i3;
    }

    public List getUnknownAppList() {
        enforceSystemOrRoot("Only the system can claim isUnknownApps");
        if (!this.ASKS_UNKNOWN_LIMIT_CALLPEM) {
            return null;
        }
        Slog.i("PackageInformation", "checking limitCallPem..");
        ArrayList installedAppsDataFromXML = getInstalledAppsDataFromXML("overlay", null);
        if (installedAppsDataFromXML == null || installedAppsDataFromXML.isEmpty()) {
            Slog.e("PackageInformation", "getUnknownAppList : installedUnknownList is null");
            return null;
        }
        if (isDevDevice()) {
            Slog.i("PackageInformation", "getUnknownAppList : " + installedAppsDataFromXML.toString());
        }
        return installedAppsDataFromXML;
    }

    public boolean isUnknownApps(String str, Signature[] signatureArr) {
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
        if (installedAppsDataFromXML.contains(str)) {
            if (hashMap.containsKey(str)) {
                try {
                    String sigHash = getSigHash(signatureArr[0]);
                    if (hashMap.size() > 0 && sigHash.equals(((InstalledAppInfo) hashMap.get(str)).signature)) {
                        Slog.i("PackageInformation", "isUnknownApp is true");
                        return true;
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            } else {
                Slog.e("PackageInformation", "packageName is not exist in installedUnknownList");
            }
        } else {
            Slog.e("PackageInformation", "packageName is not exist in overlayList");
        }
        return false;
    }

    public void compareAttributeValue(UnknownStore unknownStore, InstalledAppInfo installedAppInfo) {
        ArrayList installedAppsDataFromXML;
        String str;
        HashMap hashMap;
        if (installedAppInfo != null) {
            ArrayList exceptList = unknownStore.getExceptList();
            String sHA256ForPkgName = getSHA256ForPkgName(installedAppInfo.name);
            String str2 = installedAppInfo.signature;
            if (sHA256ForPkgName != null && !sHA256ForPkgName.isEmpty() && str2 != null && !str2.isEmpty()) {
                if (exceptList != null && (exceptList.contains(sHA256ForPkgName) || exceptList.contains(str2))) {
                    if ("block".equals(installedAppInfo.overlay)) {
                        installedAppInfo.overlay = "allow";
                        setDataToDeviceForModifyUnknownApp(2, installedAppInfo);
                    }
                } else if ("allow".equals(installedAppInfo.overlay)) {
                    installedAppInfo.overlay = "block";
                    setDataToDeviceForModifyUnknownApp(2, installedAppInfo);
                }
                ArrayList excuteBlockList = unknownStore.getExcuteBlockList();
                if (excuteBlockList != null && (excuteBlockList.contains(sHA256ForPkgName) || excuteBlockList.contains(str2))) {
                    if ("allow".equals(installedAppInfo.execute)) {
                        installedAppInfo.execute = "block";
                        setDataToDeviceForModifyUnknownApp(2, installedAppInfo);
                        HashMap hashMap2 = unknownStore.certPolicies;
                        if (((hashMap2 != null && !hashMap2.containsKey(sHA256ForPkgName)) || unknownStore.certPolicies == null) && (hashMap = unknownStore.certPolicies) != null && hashMap.containsKey(sHA256ForPkgName) && ((HashMap) unknownStore.certPolicies.get(sHA256ForPkgName)).containsKey("ALL")) {
                            setSamsungAnalyticsLog(Integer.toString(((PKGINFO) ((HashMap) unknownStore.certPolicies.get(sHA256ForPkgName)).get("ALL")).getSA()), installedAppInfo.name + "^" + installedAppInfo.signature, "NA");
                        }
                    }
                } else if ("block".equals(installedAppInfo.execute)) {
                    installedAppInfo.execute = "allow";
                    setDataToDeviceForModifyUnknownApp(2, installedAppInfo);
                }
                HashMap unknownAppsList = unknownStore.getUnknownAppsList();
                if (unknownAppsList == null || !unknownAppsList.containsKey(str2) || (installedAppsDataFromXML = getInstalledAppsDataFromXML("initType", null)) == null) {
                    return;
                }
                for (int i = 0; i < installedAppsDataFromXML.size(); i++) {
                    String[] split = ((String) installedAppsDataFromXML.get(i)).split(",");
                    String str3 = split[0];
                    if (str3 != null && split[1] != null) {
                        String sHA256ForPkgName2 = getSHA256ForPkgName(str3);
                        String str4 = split[1];
                        if (sHA256ForPkgName.equals(sHA256ForPkgName2) && (str = (String) unknownAppsList.get(str2)) != null && !str.equals(str4)) {
                            if (isDevDevice()) {
                                Slog.w("PackageInformation", split[0] + "'s policy was changed from " + installedAppInfo.initType + " to " + str);
                            }
                            installedAppInfo.initType = str;
                            setDataToDeviceForModifyUnknownApp(2, installedAppInfo);
                        }
                    }
                }
                return;
            }
            Slog.e("PackageInformation", "pkgNameHash is NULL!!");
            return;
        }
        Slog.e("PackageInformation", "appInfo is NULL!!");
    }

    public final void refreshInstalledUnknownList_NEW() {
        HashMap checkPolicyFileWithDelta;
        HashMap hashMap = new HashMap();
        getInstalledAppsDataFromXML(null, hashMap);
        if (hashMap.isEmpty()) {
            Slog.w("PackageInformation", "installedUnknownList is null");
            return;
        }
        ArrayList targetNodeName = getTargetNodeName(SystemProperties.get("ro.product.model", "Unknown"));
        if (targetNodeName != null && targetNodeName.size() > 0 && (checkPolicyFileWithDelta = checkPolicyFileWithDelta(38, targetNodeName)) != null) {
            for (Map.Entry entry : hashMap.entrySet()) {
                InstalledAppInfo installedAppInfo = (InstalledAppInfo) entry.getValue();
                String str = (String) entry.getKey();
                if (installedAppInfo != null && str != null) {
                    String sHA256ForPkgName = getSHA256ForPkgName(str);
                    if (checkPolicyFileWithDelta.containsKey(installedAppInfo.signature)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("try to check ");
                        sb.append(str);
                        sb.append(isDevDevice() ? " hash:" + sHA256ForPkgName : "");
                        Slog.i("PackageInformation", sb.toString());
                        UnknownStore unknownStore = (UnknownStore) checkPolicyFileWithDelta.get(installedAppInfo.signature);
                        if (unknownStore != null) {
                            compareAttributeValue(unknownStore, installedAppInfo);
                        }
                    } else if (checkPolicyFileWithDelta.containsKey("ALL")) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("try to check(ALL) ");
                        sb2.append(str);
                        sb2.append(isDevDevice() ? " hash:" + sHA256ForPkgName : "");
                        Slog.i("PackageInformation", sb2.toString());
                        UnknownStore unknownStore2 = (UnknownStore) checkPolicyFileWithDelta.get("ALL");
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
            RemovedAbnormalApps();
        }
    }

    public final void RemovedAbnormalApps() {
        ArrayList installedAppsDataFromXML = getInstalledAppsDataFromXML("isInstalledList", null);
        InstalledAppInfo installedAppInfo = new InstalledAppInfo();
        if (installedAppsDataFromXML == null || installedAppsDataFromXML.isEmpty()) {
            return;
        }
        for (int i = 0; i < installedAppsDataFromXML.size(); i++) {
            String str = (String) installedAppsDataFromXML.get(i);
            installedAppInfo.set(str, null, null, null, null, null, null, null);
            try {
                this.mContext.getPackageManager().getPackageInfo(str, 134217728);
            } catch (PackageManager.NameNotFoundException e) {
                Slog.e("PackageInformation", "ERROR:: Abnormal App : " + e);
                setDataToDeviceForModifyUnknownApp(3, installedAppInfo);
            }
        }
    }

    public final void addUnknownAppList(String str, Signature[] signatureArr, RETVALUE retvalue, String str2, boolean z, boolean z2, boolean z3) {
        try {
            InstalledAppInfo installedAppInfo = new InstalledAppInfo();
            installedAppInfo.set(str, getSigHash(signatureArr[0]), retvalue.isExecute == 505 ? "allow" : "block", str2.equals("except") ? "allow" : "block", z ? "true" : "false", str2, z2 ? "true" : "false", z3 ? "true" : "false");
            setInstalledAppInfoToStore(installedAppInfo);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public final void setInstalledAppInfoToStore(InstalledAppInfo installedAppInfo) {
        this.installedAppInfoToStore = installedAppInfo;
    }

    public final InstalledAppInfo getInstalledAppInfoToStore() {
        return this.installedAppInfoToStore;
    }

    public final void clearInstalledAppInfoToStore() {
        this.installedAppInfoToStore = null;
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0183  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0191 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:78:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void checkExistUnknownAppList() {
        Throwable th;
        InputStream inputStream;
        if (new File("/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_INSTALLED_INFO_LIST.xml").exists()) {
            return;
        }
        Slog.i("PackageInformation", "info_list not exists");
        File file = new File("/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_INSTALLED_LIST.xml");
        char c = 0;
        if (file.exists()) {
            HashMap hashMap = new HashMap();
            getDataByDevice("/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_INSTALLED_LIST.xml", hashMap);
            ArrayList arrayList = new ArrayList(hashMap.keySet());
            ArrayList arrayList2 = new ArrayList();
            for (int i = 0; i < hashMap.size(); i++) {
                String str = (String) arrayList.get(i);
                InstalledAppInfo installedAppInfo = new InstalledAppInfo();
                if (hashMap.get(str) != null && !((ArrayList) hashMap.get(str)).isEmpty()) {
                    installedAppInfo.set(str, (String) ((ArrayList) hashMap.get(str)).get(0), "allow", "block", "false", "warning", "false", "false");
                    arrayList2.add(installedAppInfo);
                }
            }
            Slog.i("PackageInformation", "Installed Unknown app list : " + hashMap.toString());
            setDataToDeviceForInstalledUnknownList(arrayList2);
            refreshInstalledUnknownList_NEW();
            if (file.delete()) {
                Slog.i("PackageInformation", "installed_list File is deleted");
                return;
            } else {
                Slog.i("PackageInformation", "installed_list File is not deleted");
                return;
            }
        }
        File file2 = new File("/data/system/UnknownSourceAppList.xml");
        List arrayList3 = new ArrayList();
        InputStream inputStream2 = null;
        if (file2.exists()) {
            XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
            try {
                try {
                    inputStream = new FileInputStream(file2);
                    try {
                        try {
                            newPullParser.setInput(inputStream, null);
                            arrayList3 = parsePackages(newPullParser);
                            inputStream.close();
                        } catch (IOException e) {
                            e = e;
                            e.printStackTrace();
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            ArrayList arrayList4 = new ArrayList();
                            if (arrayList3 == null) {
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        inputStream2 = inputStream;
                        if (inputStream2 == null) {
                            try {
                                inputStream2.close();
                                throw th;
                            } catch (IOException e2) {
                                e2.printStackTrace();
                                throw th;
                            }
                        }
                        throw th;
                    }
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            } catch (IOException e4) {
                e = e4;
                inputStream = null;
            } catch (Throwable th3) {
                th = th3;
                if (inputStream2 == null) {
                }
            }
            ArrayList arrayList42 = new ArrayList();
            if (arrayList3 == null) {
                int i2 = 0;
                while (i2 < arrayList3.size()) {
                    String str2 = (String) arrayList3.get(i2);
                    try {
                        Signature[] apkContentsSigners = this.mContext.getPackageManager().getPackageInfo(str2, 134217728).signingInfo.getApkContentsSigners();
                        String sigHash = (apkContentsSigners == null || apkContentsSigners.length < 1) ? "" : getSigHash(apkContentsSigners[c]);
                        InstalledAppInfo installedAppInfo2 = new InstalledAppInfo();
                        installedAppInfo2.set(str2, sigHash, "allow", "block", "false", "warning", "false", "false");
                        arrayList42.add(installedAppInfo2);
                    } catch (PackageManager.NameNotFoundException unused) {
                        Slog.e("PackageInformation", "NameNotFoundException packageName : " + str2);
                    } catch (NoSuchAlgorithmException e5) {
                        e5.printStackTrace();
                    }
                    i2++;
                    c = 0;
                }
                Slog.i("PackageInformation", "Installed Unknown app list : " + arrayList42.toString());
                setDataToDeviceForInstalledUnknownList(arrayList42);
                refreshInstalledUnknownList_NEW();
                return;
            }
            setDataToDeviceForInstalledUnknownList(null);
            Slog.i("PackageInformation", "Create file info_list");
            return;
        }
        setDataToDeviceForInstalledUnknownList(null);
        Slog.i("PackageInformation", "Unknown app does not exist but create file info_list ");
    }

    public final ArrayList getInstalledAppsDataFromXML(String str, HashMap hashMap) {
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
                int eventType = newPullParser.getEventType();
                for (int i = 1; eventType != i; i = 1) {
                    if (eventType == 2) {
                        String name = newPullParser.getName();
                        if (str != null) {
                            if (name != null && name.equals("package")) {
                                checkAttributeValue(newPullParser, hashMap2);
                                eventType = newPullParser.next();
                            }
                        } else {
                            InstalledAppInfo installedAppInfo = new InstalledAppInfo();
                            installedAppInfo.set(newPullParser.getAttributeValue(null, "name"), newPullParser.getAttributeValue(null, "signature"), newPullParser.getAttributeValue(null, "execute"), newPullParser.getAttributeValue(null, "overlay"), newPullParser.getAttributeValue(null, "requestInstallerZip"), newPullParser.getAttributeValue(null, "initType"), newPullParser.getAttributeValue(null, "accessibility"), newPullParser.getAttributeValue(null, "hasReqInstallPEM"));
                            hashMap.put(newPullParser.getAttributeValue(null, "name"), installedAppInfo);
                            eventType = newPullParser.next();
                        }
                    }
                    eventType = newPullParser.next();
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

    public final void putInstalledList(String str, String str2, HashMap hashMap) {
        ArrayList arrayList = (ArrayList) hashMap.get(str);
        if (arrayList != null && !arrayList.isEmpty()) {
            arrayList.add(str2);
        } else {
            arrayList = new ArrayList();
            arrayList.add(str2);
        }
        hashMap.put(str, arrayList);
    }

    public final void checkAttributeValue(XmlPullParser xmlPullParser, HashMap hashMap) {
        if (xmlPullParser.getAttributeValue(null, "name") == null || xmlPullParser.getAttributeValue(null, "execute") == null || xmlPullParser.getAttributeValue(null, "overlay") == null || xmlPullParser.getAttributeValue(null, "requestInstallerZip") == null || xmlPullParser.getAttributeValue(null, "initType") == null) {
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
            putInstalledList("initType", attributeValue + "," + xmlPullParser.getAttributeValue(null, "initType"), hashMap);
        }
        if ("true".equals(xmlPullParser.getAttributeValue(null, "accessibility"))) {
            putInstalledList("accessibility", attributeValue, hashMap);
        }
        if ("true".equals(xmlPullParser.getAttributeValue(null, "hasReqInstallPEM"))) {
            putInstalledList("hasReqInstallPEM", attributeValue, hashMap);
        }
    }

    public final void setDataToDeviceForInstalledUnknownList(List list) {
        File file;
        XmlSerializer newSerializer = Xml.newSerializer();
        FileOutputStream fileOutputStream = null;
        try {
            try {
                try {
                    file = new File("/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_INSTALLED_INFO_LIST.xml");
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            } catch (Exception e2) {
                e = e2;
            }
            if (!file.createNewFile()) {
                Slog.e("PackageInformation", "failed to created file related INFOLIST");
                return;
            }
            FileOutputStream fileOutputStream2 = new FileOutputStream(file);
            try {
                newSerializer.setOutput(fileOutputStream2, "UTF-8");
                newSerializer.startDocument(null, Boolean.TRUE);
                newSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                newSerializer.startTag(null, "LIST");
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        newSerializer.startTag(null, "package");
                        newSerializer.attribute(null, "name", ((InstalledAppInfo) list.get(i)).name);
                        newSerializer.attribute(null, "signature", ((InstalledAppInfo) list.get(i)).signature);
                        newSerializer.attribute(null, "execute", ((InstalledAppInfo) list.get(i)).execute);
                        newSerializer.attribute(null, "overlay", ((InstalledAppInfo) list.get(i)).overlay);
                        newSerializer.attribute(null, "requestInstallerZip", ((InstalledAppInfo) list.get(i)).requestInstallerZip);
                        newSerializer.attribute(null, "initType", ((InstalledAppInfo) list.get(i)).initType);
                        newSerializer.attribute(null, "accessibility", ((InstalledAppInfo) list.get(i)).accessibility);
                        newSerializer.attribute(null, "hasReqInstallPEM", ((InstalledAppInfo) list.get(i)).hasReqInstallPEM);
                        newSerializer.endTag(null, "package");
                    }
                }
                newSerializer.endTag(null, "LIST");
                newSerializer.endDocument();
                newSerializer.flush();
                fileOutputStream2.close();
                fileOutputStream2.close();
            } catch (Exception e3) {
                e = e3;
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
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:79:0x01a3 -> B:15:0x01a7). Please report as a decompilation issue!!! */
    public final void setDataToDeviceForModifyUnknownApp(int i, InstalledAppInfo installedAppInfo) {
        FileInputStream fileInputStream;
        Document parse;
        Element documentElement;
        String str;
        String str2;
        NodeList nodeList;
        String str3 = "UTF-8";
        FileInputStream fileInputStream2 = null;
        FileInputStream fileInputStream3 = null;
        fileInputStream2 = null;
        try {
            try {
                try {
                    DocumentBuilder newDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                    FileInputStream fileInputStream4 = new FileInputStream(new File("/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_INSTALLED_INFO_LIST.xml"));
                    try {
                        InputSource inputSource = new InputSource(new InputStreamReader(fileInputStream4, "UTF-8"));
                        inputSource.setEncoding("UTF-8");
                        parse = newDocumentBuilder.parse(inputSource);
                        documentElement = parse.getDocumentElement();
                        fileInputStream = fileInputStream4;
                    } catch (Exception e) {
                        e = e;
                        fileInputStream = fileInputStream4;
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream = fileInputStream4;
                    }
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (IOException e3) {
            e3.printStackTrace();
            fileInputStream2 = fileInputStream2;
        }
        try {
        } catch (Exception e4) {
            e = e4;
            fileInputStream3 = fileInputStream;
            e.printStackTrace();
            fileInputStream2 = fileInputStream3;
            if (fileInputStream3 != null) {
                fileInputStream3.close();
                fileInputStream2 = fileInputStream3;
            }
        } catch (Throwable th3) {
            th = th3;
            fileInputStream2 = fileInputStream;
            Throwable th4 = th;
            if (fileInputStream2 != null) {
                try {
                    fileInputStream2.close();
                    throw th4;
                } catch (IOException e5) {
                    e5.printStackTrace();
                    throw th4;
                }
            }
            throw th4;
        }
        if (i == 1) {
            Element createElement = parse.createElement("package");
            createElement.setAttribute("name", installedAppInfo.name);
            createElement.setAttribute("signature", installedAppInfo.signature);
            createElement.setAttribute("execute", installedAppInfo.execute);
            createElement.setAttribute("overlay", installedAppInfo.overlay);
            createElement.setAttribute("requestInstallerZip", installedAppInfo.requestInstallerZip);
            createElement.setAttribute("initType", installedAppInfo.initType);
            createElement.setAttribute("accessibility", installedAppInfo.accessibility);
            createElement.setAttribute("hasReqInstallPEM", installedAppInfo.hasReqInstallPEM);
            documentElement.appendChild(createElement);
            str = "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_INSTALLED_INFO_LIST.xml";
        } else {
            if (i == 2) {
                NodeList childNodes = documentElement.getChildNodes();
                str = "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_INSTALLED_INFO_LIST.xml";
                int i2 = 0;
                while (i2 < childNodes.getLength()) {
                    str2 = str3;
                    if (childNodes.item(i2).getNodeType() == 1) {
                        Element element = (Element) childNodes.item(i2);
                        nodeList = childNodes;
                        if (element.getAttribute("name").equals(installedAppInfo.name) && element.getAttribute("signature").equals(installedAppInfo.signature)) {
                            Element createElement2 = parse.createElement("package");
                            createElement2.setAttribute("name", installedAppInfo.name);
                            createElement2.setAttribute("signature", installedAppInfo.signature);
                            createElement2.setAttribute("execute", installedAppInfo.execute);
                            createElement2.setAttribute("overlay", installedAppInfo.overlay);
                            createElement2.setAttribute("requestInstallerZip", installedAppInfo.requestInstallerZip);
                            createElement2.setAttribute("initType", installedAppInfo.initType);
                            createElement2.setAttribute("accessibility", installedAppInfo.accessibility);
                            createElement2.setAttribute("hasReqInstallPEM", installedAppInfo.hasReqInstallPEM);
                            documentElement.replaceChild(createElement2, element);
                            break;
                        }
                    } else {
                        nodeList = childNodes;
                    }
                    i2++;
                    childNodes = nodeList;
                    str3 = str2;
                }
            } else {
                str = "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_INSTALLED_INFO_LIST.xml";
                str2 = "UTF-8";
                if (i == 3) {
                    NodeList childNodes2 = documentElement.getChildNodes();
                    int i3 = 0;
                    while (true) {
                        if (i3 >= childNodes2.getLength()) {
                            break;
                        }
                        if (childNodes2.item(i3).getNodeType() == 1) {
                            Element element2 = (Element) childNodes2.item(i3);
                            if (element2.getAttribute("name").equals(installedAppInfo.name)) {
                                Node previousSibling = element2.getPreviousSibling();
                                if (previousSibling != null && previousSibling.getNodeType() == 3 && previousSibling.getNodeValue().trim().length() == 0) {
                                    documentElement.removeChild(previousSibling);
                                }
                                documentElement.removeChild(element2);
                            }
                        }
                        i3++;
                    }
                }
            }
            documentElement.normalize();
            Transformer newTransformer = TransformerFactory.newInstance().newTransformer();
            newTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            newTransformer.setOutputProperty("indent", "yes");
            newTransformer.setOutputProperty("encoding", str2);
            newTransformer.transform(new DOMSource(parse), new StreamResult(str));
            fileInputStream.close();
            fileInputStream2 = documentElement;
        }
        str2 = str3;
        documentElement.normalize();
        Transformer newTransformer2 = TransformerFactory.newInstance().newTransformer();
        newTransformer2.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        newTransformer2.setOutputProperty("indent", "yes");
        newTransformer2.setOutputProperty("encoding", str2);
        newTransformer2.transform(new DOMSource(parse), new StreamResult(str));
        fileInputStream.close();
        fileInputStream2 = documentElement;
    }

    public final List parsePackages(XmlPullParser xmlPullParser) {
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

    public final String getASKSPolicyVersion(String str, String str2) {
        HashMap hashMap = new HashMap();
        hashMap.put("ASKS_FILE", new String[]{"<asks version=\"", "\""});
        hashMap.put("ASKS_RULE_FILE", new String[]{"<VERSION value=\"", "\"/>"});
        hashMap.put("ASKS_DELTA", new String[]{"<safeinstall delta=\"", "\""});
        try {
            String[] split = new String(Files.readAllBytes(Paths.get(str2, new String[0]))).split(((String[]) hashMap.get(str))[0]);
            return split.length > 1 ? split[1].split(((String[]) hashMap.get(str))[1])[0] : "00000000";
        } catch (IOException e) {
            e.printStackTrace();
            return "00000000";
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x01cc  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01b7 A[Catch: IOException -> 0x01bb, TRY_ENTER, TryCatch #9 {IOException -> 0x01bb, blocks: (B:47:0x0163, B:72:0x019d, B:74:0x01a2, B:64:0x01b7, B:66:0x01bf), top: B:46:0x0163 }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x01bf A[Catch: IOException -> 0x01bb, TRY_LEAVE, TryCatch #9 {IOException -> 0x01bb, blocks: (B:47:0x0163, B:72:0x019d, B:74:0x01a2, B:64:0x01b7, B:66:0x01bf), top: B:46:0x0163 }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x019d A[Catch: IOException -> 0x01bb, TRY_ENTER, TryCatch #9 {IOException -> 0x01bb, blocks: (B:47:0x0163, B:72:0x019d, B:74:0x01a2, B:64:0x01b7, B:66:0x01bf), top: B:46:0x0163 }] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x01a2 A[Catch: IOException -> 0x01bb, TRY_LEAVE, TryCatch #9 {IOException -> 0x01bb, blocks: (B:47:0x0163, B:72:0x019d, B:74:0x01a2, B:64:0x01b7, B:66:0x01bf), top: B:46:0x0163 }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01f9 A[Catch: IOException -> 0x01f5, TRY_LEAVE, TryCatch #1 {IOException -> 0x01f5, blocks: (B:89:0x01f1, B:80:0x01f9), top: B:88:0x01f1 }] */
    /* JADX WARN: Removed duplicated region for block: B:87:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x01f1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean copyASKSpolicyFromSystem() {
        boolean z;
        String str;
        boolean z2;
        Throwable th;
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2;
        String str2 = "/system/etc/";
        String[] list = new File("/system/etc/").list(new FilenameFilter() { // from class: com.android.server.asks.ASKSManagerService.1
            @Override // java.io.FilenameFilter
            public boolean accept(File file, String str3) {
                return (str3.startsWith("ASKS") && !str3.contains("ROOT")) || str3.startsWith("RPAB") || "ADP.xml".equals(str3) || "protection_list.xml".equals(str3);
            }
        });
        boolean z3 = false;
        if (list == null) {
            Slog.e("AASA_ASKSManager", "There are no target file in /system/etc/");
            return false;
        }
        File file = new File("/data/system/.aasa/AASApolicy/");
        if (!file.exists()) {
            file.mkdirs();
        }
        if (new File("/data/system/.aasa/asks.xml").exists()) {
            Slog.d("AASA_ASKSManager", "ASKS file exists.");
            mASKSPolicyVersion = getASKSPolicyVersion("ASKS_FILE", "/data/system/.aasa/asks.xml");
            String aSKSPolicyVersion = getASKSPolicyVersion("ASKS_DELTA", "/data/system/.aasa/asks.xml");
            mASKSDeltaPolicyVersion = aSKSPolicyVersion;
            if ("00000000".equals(aSKSPolicyVersion)) {
                mASKSDeltaPolicyVersion = mASKSPolicyVersion;
            }
            Slog.d("AASA_ASKSManager", "policy version : " + mASKSPolicyVersion + "::" + mASKSDeltaPolicyVersion);
            SystemProperties.set("security.ASKS.policy_version", mASKSPolicyVersion);
            SystemProperties.set("security.ASKS.policy_version", mASKSDeltaPolicyVersion);
        }
        String str3 = mASKSPolicyVersion;
        int length = list.length;
        String str4 = str3;
        int i = 0;
        while (i < length) {
            String str5 = list[i];
            File file2 = new File(str2 + str5);
            File file3 = new File("/data/system/.aasa/AASApolicy/" + str5);
            if (file2.exists()) {
                String aSKSPolicyVersion2 = getASKSPolicyVersion("ASKS_RULE_FILE", file2.getPath());
                if (file3.exists()) {
                    Slog.d("AASA_ASKSManager", "There are already exist xml files in /.aasa/" + str5);
                    String aSKSPolicyVersion3 = getASKSPolicyVersion("ASKS_RULE_FILE", file3.getPath());
                    if (aSKSPolicyVersion3.length() < 8) {
                        aSKSPolicyVersion3 = "20" + aSKSPolicyVersion3;
                    }
                    if (Integer.parseInt(aSKSPolicyVersion3) > Integer.parseInt(aSKSPolicyVersion2)) {
                        Slog.d("AASA_ASKSManager", "Skip, Current " + str5 + " rule is latest version.");
                        if (str5.contains("RPAB") || Integer.parseInt(aSKSPolicyVersion3) <= Integer.parseInt(str4)) {
                            str = str2;
                        } else {
                            str = str2;
                            str4 = aSKSPolicyVersion3;
                        }
                        z2 = false;
                    }
                }
                byte[] bArr = new byte[IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES];
                try {
                    fileInputStream = new FileInputStream(file2);
                    try {
                        FileOutputStream fileOutputStream3 = new FileOutputStream(file3);
                        while (true) {
                            str = str2;
                            z2 = false;
                            try {
                                int read = fileInputStream.read(bArr, 0, IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES);
                                if (read != -1) {
                                    fileOutputStream3.write(bArr, 0, read);
                                    str2 = str;
                                } else {
                                    try {
                                        break;
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (FileNotFoundException e2) {
                                e = e2;
                                fileOutputStream2 = fileOutputStream3;
                                Slog.e("AASA_ASKSManager", "FileNotFoundException");
                                e.printStackTrace();
                                if (fileInputStream != null) {
                                    fileInputStream.close();
                                }
                                if (fileOutputStream2 != null) {
                                    fileOutputStream2.close();
                                }
                                if (!str5.contains("RPAB")) {
                                    str4 = aSKSPolicyVersion2;
                                }
                                Slog.d("AASA_ASKSManager", "there is xml file /system/etc/" + str5);
                                i++;
                                z3 = z2;
                                str2 = str;
                            } catch (IOException e3) {
                                e = e3;
                                fileOutputStream = fileOutputStream3;
                                try {
                                    Slog.e("AASA_ASKSManager", "IOException");
                                    e.printStackTrace();
                                    if (fileInputStream != null) {
                                        fileInputStream.close();
                                    }
                                    if (fileOutputStream != null) {
                                        fileOutputStream.close();
                                    }
                                    if (!str5.contains("RPAB")) {
                                    }
                                    Slog.d("AASA_ASKSManager", "there is xml file /system/etc/" + str5);
                                    i++;
                                    z3 = z2;
                                    str2 = str;
                                } catch (Throwable th2) {
                                    th = th2;
                                    if (fileInputStream != null) {
                                        try {
                                            fileInputStream.close();
                                        } catch (IOException e4) {
                                            e4.printStackTrace();
                                            throw th;
                                        }
                                    }
                                    if (fileOutputStream == null) {
                                        throw th;
                                    }
                                    fileOutputStream.close();
                                    throw th;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                fileOutputStream = fileOutputStream3;
                                if (fileInputStream != null) {
                                }
                                if (fileOutputStream == null) {
                                }
                            }
                        }
                        fileInputStream.close();
                        fileOutputStream3.close();
                    } catch (FileNotFoundException e5) {
                        e = e5;
                        str = str2;
                        z2 = false;
                        fileOutputStream2 = null;
                        Slog.e("AASA_ASKSManager", "FileNotFoundException");
                        e.printStackTrace();
                        if (fileInputStream != null) {
                        }
                        if (fileOutputStream2 != null) {
                        }
                        if (!str5.contains("RPAB")) {
                        }
                        Slog.d("AASA_ASKSManager", "there is xml file /system/etc/" + str5);
                        i++;
                        z3 = z2;
                        str2 = str;
                    } catch (IOException e6) {
                        e = e6;
                        str = str2;
                        z2 = false;
                        fileOutputStream = null;
                        Slog.e("AASA_ASKSManager", "IOException");
                        e.printStackTrace();
                        if (fileInputStream != null) {
                        }
                        if (fileOutputStream != null) {
                        }
                        if (!str5.contains("RPAB")) {
                        }
                        Slog.d("AASA_ASKSManager", "there is xml file /system/etc/" + str5);
                        i++;
                        z3 = z2;
                        str2 = str;
                    } catch (Throwable th4) {
                        th = th4;
                        fileOutputStream = null;
                        if (fileInputStream != null) {
                        }
                        if (fileOutputStream == null) {
                        }
                    }
                } catch (FileNotFoundException e7) {
                    e = e7;
                    str = str2;
                    z2 = false;
                    fileInputStream = null;
                } catch (IOException e8) {
                    e = e8;
                    str = str2;
                    z2 = false;
                    fileInputStream = null;
                } catch (Throwable th5) {
                    th = th5;
                    fileInputStream = null;
                }
                if (!str5.contains("RPAB") && Integer.parseInt(aSKSPolicyVersion2) > Integer.parseInt(str4)) {
                    str4 = aSKSPolicyVersion2;
                }
                Slog.d("AASA_ASKSManager", "there is xml file /system/etc/" + str5);
            } else {
                str = str2;
                z2 = z3;
                Slog.d("AASA_ASKSManager", "there is no xml file /system/etc/" + str5);
            }
            i++;
            z3 = z2;
            str2 = str;
        }
        boolean z4 = z3;
        if (Integer.parseInt(str4) > Integer.parseInt(mASKSPolicyVersion)) {
            mASKSPolicyVersion = str4;
            z = true;
        } else {
            z = z4;
        }
        deleteFile("/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_ALLOWLIST.xml");
        deleteFile("/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_BLOCKLIST.xml");
        deleteFile("/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_DANLIST.xml");
        deleteFile("/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_DANEXCEPTPKG.xml");
        deleteFile("/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_DANBLOCKPKG.xml");
        deleteFile("/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_PRE_INSTALLER.xml");
        return z;
    }

    public final boolean isTargetPackage(String str, ArrayList arrayList, ASKSSession aSKSSession) {
        Signature[] signatures;
        AndroidPackage androidPackage = getPackageManagerInternal().getPackage(str);
        if (androidPackage == null) {
            if (aSKSSession == null || str == null || !str.equals(aSKSSession.getPackageName())) {
                return false;
            }
            Slog.d("AASA_ASKSManager_RESTRICTED", "isTargetPackage() : There is no information of " + str + ", check current session.");
            signatures = aSKSSession.getSignature();
        } else {
            signatures = androidPackage.getSigningDetails().getSignatures();
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

    public final boolean isPlatformSigned(Signature[] signatureArr) {
        AndroidPackage androidPackage = getPackageManagerInternal().getPackage("android");
        if (androidPackage != null) {
            return PackageManagerServiceUtils.compareSignatures(androidPackage.getSigningDetails().getSignatures(), signatureArr) == 0;
        }
        Slog.e("AASA_ASKSManager", "cannot find android pkg");
        return false;
    }

    public final int isSignatureMatched(String str, Signature[] signatureArr) {
        boolean z = this.DEBUG_MODE_FOR_DEVELOPMENT;
        int i = z ? 11 : 10;
        String[] strArr = new String[i];
        strArr[0] = "308204d4308203bca003020102020900d20995a79c0daad6300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303632323132323531325a170d3338313130373132323531325a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100c986384a3e1f2fb206670e78ef232215c0d26f45a22728db99a44da11c35ac33a71fe071c4a2d6825a9b4c88b333ed96f3c5e6c666d60f3ee94c490885abcf8dc660f707aabc77ead3e2d0d8aee8108c15cd260f2e85042c28d2f292daa3c6da0c7bf2391db7841aade8fdf0c9d0defcf77124e6d2de0a9e0d2da746c3670e4ffcdc85b701bb4744861b96ff7311da3603c5a10336e55ffa34b4353eedc85f51015e1518c67e309e39f87639ff178107f109cd18411a6077f26964b6e63f8a70b9619db04306a323c1a1d23af867e19f14f570ffe573d0e3a0c2b30632aaec3173380994be1e341e3a90bd2e4b615481f46db39ea83816448ec35feb1735c1f3020103a382010b30820107301d0603551d0e04160414932c3af70b627a0c7610b5a0e7427d6cfaea3f1e3081d70603551d230481cf3081cc8014932c3af70b627a0c7610b5a0e7427d6cfaea3f1ea181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900d20995a79c0daad6300c0603551d13040530030101ff300d06092a864886f70d01010505000382010100329601fe40e036a4a86cc5d49dd8c1b5415998e72637538b0d430369ac51530f63aace8c019a1a66616a2f1bb2c5fabd6f313261f380e3471623f053d9e3c53f5fd6d1965d7b000e4dc244c1b27e2fe9a323ff077f52c4675e86247aa801187137e30c9bbf01c567a4299db4bf0b25b7d7107a7b81ee102f72ff47950164e26752e114c42f8b9d2a42e7308897ec640ea1924ed13abbe9d120912b62f4926493a86db94c0b46f44c6161d58c2f648164890c512dfb28d42c855bf470dbee2dab6960cad04e81f71525ded46cdd0f359f99c460db9f007d96ce83b4b218ac2d82c48f12608d469733f05a3375594669ccbf8a495544d6c5701e9369c08c810158";
        strArr[1] = "308204d4308203bca003020102020900e5eff0a8f66d92b3300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303632323132323531335a170d3338313130373132323531335a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100e9f1edb42423201dce62e68f2159ed8ea766b43a43d348754841b72e9678ce6b03d06d31532d88f2ef2d5ba39a028de0857983cd321f5b7786c2d3699df4c0b40c8d856f147c5dc54b9d1d671d1a51b5c5364da36fc5b0fe825afb513ec7a2db862c48a6046c43c3b71a1e275155f6c30aed2a68326ac327f60160d427cf55b617230907a84edbff21cc256c628a16f15d55d49138cdf2606504e1591196ed0bdc25b7cc4f67b33fb29ec4dbb13dbe6f3467a0871a49e620067755e6f095c3bd84f8b7d1e66a8c6d1e5150f7fa9d95475dc7061a321aaf9c686b09be23ccc59b35011c6823ffd5874d8fa2a1e5d276ee5aa381187e26112c7d5562703b36210b020103a382010b30820107301d0603551d0e041604145b115b23db35655f9f77f78756961006eebe3a9e3081d70603551d230481cf3081cc80145b115b23db35655f9f77f78756961006eebe3a9ea181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900e5eff0a8f66d92b3300c0603551d13040530030101ff300d06092a864886f70d0101050500038201010039c91877eb09c2c84445443673c77a1219c5c02e6552fa2fbad0d736bc5ab6ebaf0375e520fe9799403ecb71659b23afda1475a34ef4b2e1ffcba8d7ff385c21cb6482540bce3837e6234fd4f7dd576d7fcfe9cfa925509f772c494e1569fe44e6fcd4122e483c2caa2c639566dbcfe85ed7818d5431e73154ad453289fb56b607643919cf534fbeefbdc2009c7fcb5f9b1fa97490462363fa4bedc5e0b9d157e448e6d0e7cfa31f1a2faa9378d03c8d1163d3803bc69bf24ec77ce7d559abcaf8d345494abf0e3276f0ebd2aa08e4f4f6f5aaea4bc523d8cc8e2c9200ba551dd3d4e15d5921303ca9333f42f992ddb70c2958e776c12d7e3b7bd74222eb5c7a";
        strArr[2] = "308204d4308203bca003020102020900f3a752a8cbb7ac6a300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303732373132323632335a170d3338313231323132323632335a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100bd20d27f5127981cf0855e43e64d8018b92401ff0b4b241eeb54c4fb0e84dcf94cf8da888e34c1c370bc437f77880819f3a9894019f05d5514bc3d20d17e968167d85990fa1a44b9e79aa1da9681dc8d2c39b98b3b257918748c6f5bb9126330d72fdc26065e717f1a5c27c8b075f1a8d7325f7eb2d57ee34d93d76a5c529d2e0789392793c68c8f5090c4d2d093190b3279943550e2f5c864118e84d6c6c6bc67815148db8752e4bf69a9ca729ca4704d966e8dd591506dfc9dd9c8c33bdc7bf58660df6be3b45753983a092c3a4ae899d1f2253017ba606a5b1dda2f5511fcf530ea43c7dc05ff1621d305f12a37148e72078aaf644dadc98f3b6789cb6655020103a382010b30820107301d0603551d0e041604142fa3167aab7de1f13b4edef062fa715c0609f0bf3081d70603551d230481cf3081cc80142fa3167aab7de1f13b4edef062fa715c0609f0bfa181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900f3a752a8cbb7ac6a300c0603551d13040530030101ff300d06092a864886f70d01010505000382010100498ed96cbc503fb1b72402dcb8ba364d8aa11dc5b9a7e191d200af4051272519b3099eba16e538044f086a1e36710abf2980efb437b6a9bebfab93417c068ea18cbfdeb8570fca73951684c674eb33c4240e236928ba1197d6b385c40454c3980f6f764131149dbba80756b7b18c5951a8630a6692fdb30227b431175f793a6e39479e8ad8b4b4beca6faabf9fc243b9be47447229524487f5f04cf6661ec818a3756221360bfeee3ccaec9a6dc67694b791a80957b28f11f15fd81eaeb361e4c9f907d3ceb4176f9947b513f8cd89d77044adae7c7f631f27a2e40a8d655a9c73515c796b17a39d0e9de675d62bf785c1e0d65a937c65aadacf788b2dfc14e2";
        strArr[3] = "308204d4308203bca003020102020900b830e7f5ede090a8300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303632323132323531325a170d3338313130373132323531325a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d003082010802820101009a280ff8cebd5954fbac141d450be91a980a6597b379cb64a19bc4ab39aecb5f06fe2599d3767bb0c27e3e8ac3846cf0b80c09817f8d22be8a55418a068c6983958ffc233a99cd793bc468b0bda139b87ff1550e5ce184647214a1fa4fe2121a0ecdbb1cd33c644c06e7b70455ff097a4f8c51eca2ebefb4602b5d8bb6ed811ec959c1e99e8f353667703563c3c3277bbbd872fe7fa84bd8041efa98d32bb35c44d9c55aa8e766da065176722103fdb63677392c94bd20f5a5ac5c780046bc729a2eec3575a05ddb39836235c8c939f95493aa8f32dd7e7016392716219f0c5fe48874f283af0c217b4c08536b5df7bc302c9e2af08db61ecb49a198c7c4bd2b020103a382010b30820107301d0603551d0e041604144d2270829d5cf4a65bf55a756224bea659c2dfda3081d70603551d230481cf3081cc80144d2270829d5cf4a65bf55a756224bea659c2dfdaa181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900b830e7f5ede090a8300c0603551d13040530030101ff300d06092a864886f70d01010505000382010100751ea54edeb751de01436db8009352bee64209020fe40641ac09d0016c807fd89258aca374299520e30bc79e77a161c98ddb8ccfc9c8184969114e4478d1b1b374a97e52e07e056dd6b6de5b063c12203e55e284d1de58af2fc6e43c198857b87ac9a472633b8a1cd7e6ebc4e2d675b680d1844d86ab7569129d24e2bcf10cddb2e66c85c1335a3d6479749152058a27135440b795bf509d78009fbda18a6c0cb31b741f79a4ac189d44fd04f65887bb9d950cc2b6f43275e71900fba03b06a9ab9ecd58af0f8c2e0b3569197b043da0601563b0af26a0f52c4b7e834c7ccf5dec4d330d8fd0a049360cd3d9ef0bff09b9812c9ba406c8a6650688b0919a040b";
        strArr[4] = "30820411308202f9a003020102020900fd222d6fc87acde0300d06092a864886f70d010105050030819e310b3009060355040613024b523113301106035504080c0a536f7574684b6f7265613112301006035504070c095375776f6e43697479311b3019060355040a0c1253616d73756e67436f72706f726174696f6e310c300a060355040b0c03444d433114301206035504030c0b53616d73756e67436572743125302306092a864886f70d01090116166d2e73656375726974794073616d73756e672e636f6d3020170d3133303132343035323231305a180f32313132313233313035323231305a30819e310b3009060355040613024b523113301106035504080c0a536f7574684b6f7265613112301006035504070c095375776f6e43697479311b3019060355040a0c1253616d73756e67436f72706f726174696f6e310c300a060355040b0c03444d433114301206035504030c0b53616d73756e67436572743125302306092a864886f70d01090116166d2e73656375726974794073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100a2c51f56a1c8bf64ada0af152ced2344ac070b447efc85f1b69ce90fbc2b7a71257240c215eedbf7445c474fe34d62bc3035d79ba110859118f1200ecc9ae48b56400e187591272d59734e456d9dfd5a1f3227a30b9448bda84c2901b501295445e204ddb6f9f9e36b2560998f1764e446176fe5d83987220f8ed15106dc7c8ecb6798de45f5fbae54efe2b35a379631f545f84c98243aa4d92ef339330f954ad32e4e97aff69cbf68928484b03a8fa8eafdc8ff2a9801f249302d467b05f99a1680e4fb5b11624d5e53d67f09e86b82dd7305e3e483b12e3720fcccc2bc8857f13b6e1d60512074004f67d86241940eaba34afda2af3904b04913fa50f499f7020103a350304e301d0603551d0e04160414eef0f8211dccf6e442f3388889c9a3ea3ce0236c301f0603551d23041830168014eef0f8211dccf6e442f3388889c9a3ea3ce0236c300c0603551d13040530030101ff300d06092a864886f70d01010505000382010100395c7e7900c471e03fa9850905c6ab1edc5a8b7d43a16689d9bb1ec1a06513c4ea8f7471c6e474244174261cc151ae8d1a61019e0ed81fffee8afa1d01d85a32de796f4b46d0d5ddfcca7d1f90d523b54751f505a4e3b059569f24ba2564d72fbc4081533840f618c2993d935134d3c987605e032f6a12889af3190af1714a90f2a3476b8e0016ab45564bf10e611899babd86af33149ca6838b0a885c752ffe879f37997f262e819c62cf59caa794cfaaf8e3c462f5092a34264f0634316b13a67a644e104dc4070e8b6628a46f41da7e3c741f6edc21152f9f947dde6fe14b58f34e4d9e7abd103cb1ca9e09eb4fa5b553baa413329bd3919caca2d52e6d4b";
        strArr[5] = "308204d4308203bca003020102020900b161f3869153be27300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3131303632323132323531325a170d3338313130373132323531325a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d00308201080282010100e93d8694c493d50a6224a473d70ddcecd84a2f40ac48bb8206c83a09a94f2db98aaa34f9fcc343b91a87c61254c3a43b0caed03cd839a63037253ea77d949a284dd0b44ebfbabbc2cea838213609d9a5813e88863210ee62c0c0e415611aa7f938ad2bc627c147ac6cf558002028d2e38b1d31aba794867717ddcfcadbeeac6bd345a7bf6433e52cfc93a2157cb048298bd33bf30c143b777e3f074897bcf3b5b181316b678256fd3accf64e88160b0781efd90711ef4acae86848d87e1c10a1747e780c48bcb378a7b437e0405ec54ed7e22c4dbc39f8b03ab1d5eeb7cf4804455fbcab35afb775d79e8f4c4fa4da00b2ce48c991fd94020f7ad089fba13003020103a382010b30820107301d0603551d0e04160414b58d96dcf0127466098625e3ffb03a4f8d0654743081d70603551d230481cf3081cc8014b58d96dcf0127466098625e3ffb03a4f8d065474a181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900b161f3869153be27300c0603551d13040530030101ff300d06092a864886f70d0101050500038201010091327721aa614451a785e200349ce2f402049371943001266827c29abdf975dc7b3e6eaa02c41a07b445bb9de0bc43ce25c3c98928a94ff67ad81eec822cbd083ae686cd7126860655adb8d6a6228cf1f7a4a196699669c05b506efa1fca2cad1a150cabd01380e56bb1842651b4ff33bcb619b3c6e65a10cfd99350ea777c3866135523c1bece17f59fba76a2eb429453f7a2a9e6a6cc9e62e5f4b56706ba4c74cb86975aa865bead2209787b33261b9fa222a7117b1724ea3217ad680fd0408c5634278fbdfca0e32b16dc1a6cc245e931cbe84fc7cccdaa7778459e3003a082662ac6d84d485dd368e0eb4c2c9019420c82d1cd0fbd6fcc097353b059baea";
        strArr[6] = "308204d4308203bca003020102020900e49d6da353f759af300d06092a864886f70d01010505003081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d301e170d3039303630393135353934365a170d3336313032353135353934365a3081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d30820120300d06092a864886f70d01010105000382010d003082010802820101009ba004179d8018ab0fa3ab3c804899c2ecb6d66784225ae99936b71fd7f059969bb2076b8f2b9d7a5c20d0622e0a766de9602e3e8d60d9d335bdeab78100188f734b4678c7369c2e764913c8f43eede582827b8d1dc679c8fd0f0d0605fc6b87d331e2544bf11790b2a55c3a13463ec4cd35a931ad40dc687f116f1d6ba79eb63a01f96d107b1b166ddacb6d2fe8ac618217dabe6b69d4d9e692ab1970bb4346fd4860586e8387ef7682b07a428bc8036db143079bc37c8830e5a8c3d690f6b0cef5596ed80a9830f2e61c055894be1c2a7b3048602ef6df0e51073e06f0d55177f6aeb96b91b3b4c66b8b6e5b32bbe2afe46f45b0f48300a6ac9f9de1c500b7020103a382010b30820107301d0603551d0e041604149b6890fb4274c2e32d6c5daea2fac4dd0756529a3081d70603551d230481cf3081cc80149b6890fb4274c2e32d6c5daea2fac4dd0756529aa181a8a481a53081a2310b3009060355040613024b52311430120603550408130b536f757468204b6f726561311330110603550407130a5375776f6e2043697479311c301a060355040a131353616d73756e6720436f72706f726174696f6e310c300a060355040b1303444d43311530130603550403130c53616d73756e6720436572743125302306092a864886f70d0109011616616e64726f69642e6f734073616d73756e672e636f6d820900e49d6da353f759af300c0603551d13040530030101ff300d06092a864886f70d010105050003820101001a76d67e729785f9f22015d9eb9d1998f2d8ce5bc147f65060d58f2f29004a592dd065b651e8d746cf050f3389b1632970d1334e9bce20b43a77a18b6226be0da0a4ab4420dd734dcdd0e049c4f07cf45f3faee8ac90332c14b1f7c4e4f55866a8e3aa71ad1814b5c591e07085dadbe15544ef9bc9591b2c75b373ca9214f8a49acd18ccf061b484c3cd1448bb2af149694d58a53d4c6878b8e06c12e214e2847117ef95348eca3acaa3fffecd7924cb1dd67251eaee14b01870cae92a4238cecac4cda5ba2a2640055303e98e62121a9e49ac0dfcde32b28606f3fc613709fe5ab8aefea4ed53a310c4c9dac7f90242d55697b5690ade195f5253da947f2eaa";
        strArr[7] = "3082041e30820306a003020102020601670c2687f3300d06092a864886f70d01010505003081c4310b3009060355040613024b52311a30180603550408131152657075626c6963206f66204b6f726561311330110603550407130a5375776f6e204369747931253023060355040a131c53616d73756e6720456c656374726f6e69637320436f2e204c74642e31273025060355040b131e4d6f62696c6520436f6d6d756e69636174696f6e7320427573696e657373313430320603550403132b53616d73756e6720506c6174666f726d204b657920666f72206e6f6e2d47616c61787920646576696365733020170d3138313131333038313833315a180f32313138313131323135303030305a3081c4310b3009060355040613024b52311a30180603550408131152657075626c6963206f66204b6f726561311330110603550407130a5375776f6e204369747931253023060355040a131c53616d73756e6720456c656374726f6e69637320436f2e204c74642e31273025060355040b131e4d6f62696c6520436f6d6d756e69636174696f6e7320427573696e657373313430320603550403132b53616d73756e6720506c6174666f726d204b657920666f72206e6f6e2d47616c617879206465766963657330820122300d06092a864886f70d01010105000382010f003082010a0282010100f7049ae9aa6c64c27ebcf799f32bd9118c2870a54b4c9cd200aa33d2f332903b2a6430c608aac3038b65f644d7a82127ec187099beb680c71d3593d2522f94c894c018fb8fb08d3282bea8feafe902ce1a11da806d63366f514b97c6e286221537f758ece2bcb0b2278c4ae9217ff1c078ddb9401ce490f07557b50f6ddbbe43aacae52849a5e465010af4bdf13eae532771f6c8dc370fe715988d615e67dff7870bd4393490d17ab71584dbe7eb549df5b402fb7f0b4db5cc86e4a818601a183fe94a4a2bafd29367507f131490ac3e4e38c61f9f86c82cf2b583656b95139ce4e46c3ce04d9a9587316a47062ced72e186d546bcc39896491ad3242bb658b70203010001a3123010300e0603551d0f0101ff0404030205a0300d06092a864886f70d010105050003820101003347014ea4d8c43a387b28331fd3ba02a4aee7b9ecd340bce8e517c21ee6cc0e295d999ac5e68352ca59f30b82aa2c0736715cc20710338c34beacec99ba7a153cead3ec03640f6b764dcfa0fabfa4df5972b7abbecf532238ae1a1e2b404379f065c4ea8d148f60eb6f51c783b82b28bc97cc4486bfb08f9bba956323044b67d4fefb560c44fa18aeb397c0d87841295de021be9599396a0e734d2ec69dde9b70545db7aa106901437f07dc6d26f99d97b83380bad7b42536a47742935fe143684d8f31f07df44a7c274eaa33ba51863dbe57a1bc66cb988a97ed17f0f86e596c03a511391ec72dc4c79c039657d8b4b4ddd8a2910fa4872a3935d93a6947ad";
        strArr[8] = "3082041830820300a003020102020601670c27ef2d300d06092a864886f70d01010505003081c1310b3009060355040613024b52311a30180603550408131152657075626c6963206f66204b6f726561311330110603550407130a5375776f6e204369747931253023060355040a131c53616d73756e6720456c656374726f6e69637320436f2e204c74642e31273025060355040b131e4d6f62696c6520436f6d6d756e69636174696f6e7320427573696e6573733131302f0603550403132853616d73756e67204d65646961204b657920666f72206e6f6e2d47616c61787920646576696365733020170d3138313131333038323030325a180f32313138313131323135303030305a3081c1310b3009060355040613024b52311a30180603550408131152657075626c6963206f66204b6f726561311330110603550407130a5375776f6e204369747931253023060355040a131c53616d73756e6720456c656374726f6e69637320436f2e204c74642e31273025060355040b131e4d6f62696c6520436f6d6d756e69636174696f6e7320427573696e6573733131302f0603550403132853616d73756e67204d65646961204b657920666f72206e6f6e2d47616c617879206465766963657330820122300d06092a864886f70d01010105000382010f003082010a0282010100bb60b4487c7c006073d54adf1e85ee3352f323e7ed751880b7ff99313fa6e4d94236cbc474aad528bfdc5a1a2ba33bdbd17996439ab3746b8bfd243852429c2c036a0d634e2ee2774ae92dede65430698e77368be3fbe640d842a445fe57118111e479ed018142157095b17dd146e689e049e5182931347113c38391c3cec258ca6b675f5bdb4158de58a64c0f37fb86e0f4517d879eb265fc44ee33aca2f1185b74f23e4a48c8a7eb8941055d374c485ca0ae5adb04607e9aedf43d3ae7e15f3e0ef6f05a922c3925fa11488371f94a3847f7cefbbf5fbcf18416f21171b946c6be5acbbe9e55bf610fa333b4d1e6d0c0278bba1817cd70aa1beefb73756fb90203010001a3123010300e0603551d0f0101ff0404030205a0300d06092a864886f70d0101050500038201010057f1b2b239f9551f4de9fb5afd88b0b7bc67d37bf9bfe8748583d35d14c9291355322e896bbb66d0d56c9708215fad9c40e9398620ea3b1e4641a5883a88472f852cc36afa88b695d5a7af408d5eb583bd4cec9452d0f901b6c38e1f97b55325b596e742fade940391b44d8f19352e8a543fe1c89ad600a8ba32373b1d84fb1b8d34e7541337254fdc9716b2adcfed7105f713ec4fc98c4eee56f7ffa2d2355e16161e2f276a075eda15cc2cdba93c6a49907ad01463cc752708051b8d87001028a6869187589425d3a8992cb9044a7c4d5e3e74a270f6bd1ebf57fd3afb82ab74399a40db820103ea361f7e87b172302ce14b29527bde67c01f4b71832c8665";
        strArr[9] = "3082041a30820302a003020102020601670c278709300d06092a864886f70d01010505003081c2310b3009060355040613024b52311a30180603550408131152657075626c6963206f66204b6f726561311330110603550407130a5375776f6e204369747931253023060355040a131c53616d73756e6720456c656374726f6e69637320436f2e204c74642e31273025060355040b131e4d6f62696c6520436f6d6d756e69636174696f6e7320427573696e657373313230300603550403132953616d73756e6720536861726564204b657920666f72206e6f6e2d47616c61787920646576696365733020170d3138313131333038313933365a180f32313138313131323135303030305a3081c2310b3009060355040613024b52311a30180603550408131152657075626c6963206f66204b6f726561311330110603550407130a5375776f6e204369747931253023060355040a131c53616d73756e6720456c656374726f6e69637320436f2e204c74642e31273025060355040b131e4d6f62696c6520436f6d6d756e69636174696f6e7320427573696e657373313230300603550403132953616d73756e6720536861726564204b657920666f72206e6f6e2d47616c617879206465766963657330820122300d06092a864886f70d01010105000382010f003082010a02820101009fce256105db13cb1ec14f133d799cf889bf7c29cb8a1a8e8ba1d618a03e01b6705901e7fe2d012b3ad2cfdcad80a2718b4fb09f2d0ef0142cea5fd17afbddb4a1e7d2c99f2a1650ca17faedae9cbc5c13561e723b9ae120f55109aa992d57d2ba7e3c495620e5957c7c75c2ade6d03c5b204ceb460754ccdcd5791267f46283f37923ce3d828ee78a8702770a6356824086c956e403048059d8d07797b1b3d2671f8134b97bcdc009ce0fde7f9fda53d9175440309920838bb7dd129189322cd47851f2be587d288a38af2c32bf1024d9b7e265009db694d6d24d40576eb777b0b3713ac24cbbf1cf0534e565ce5030503c842e43438ca27557b209f475337d0203010001a3123010300e0603551d0f0101ff0404030205a0300d06092a864886f70d010105050003820101009bddfa8de87f1d9e7467e0251ca54441f6a68f4f3fc84b0fe273ffd7f01598df91b61b5bd61b14d1ecaa633d20c96b950797432e85f144d2cc04b59770e7ec912ffd59573dcc79d438ef04ed81ea98f09c8b4a2f1e7701dcac789ab33c2a2b39d026b72f3bcff9c29bdfbe34edd6be30ac6b050c10e259d4ed99b6efb4c9d0c32020f842e74984fd00bc59bb32e28ca5f32e052e19fa30859da473a402539bf58d87140edc935792f5e2da4a017e71304fbc3a20f25129a19f7f3ff3e6e1c75a6c1cf489d13e80d8a86fc8b6dd879088c4272d4bbd069b4a43bb61210b066c5280293aa580751337b24fda13553d7294b5916433e730a021520330236639e89c";
        if (z) {
            strArr[10] = "308204a830820390a003020102020900b3998086d056cffa300d06092a864886f70d0101040500308194310b3009060355040613025553311330110603550408130a43616c69666f726e6961311630140603550407130d4d6f756e7461696e20566965773110300e060355040a1307416e64726f69643110300e060355040b1307416e64726f69643110300e06035504031307416e64726f69643122302006092a864886f70d0109011613616e64726f696440616e64726f69642e636f6d301e170d3038303431353232343035305a170d3335303930313232343035305a308194310b3009060355040613025553311330110603550408130a43616c69666f726e6961311630140603550407130d4d6f756e7461696e20566965773110300e060355040a1307416e64726f69643110300e060355040b1307416e64726f69643110300e06035504031307416e64726f69643122302006092a864886f70d0109011613616e64726f696440616e64726f69642e636f6d30820120300d06092a864886f70d01010105000382010d003082010802820101009c780592ac0d5d381cdeaa65ecc8a6006e36480c6d7207b12011be50863aabe2b55d009adf7146d6f2202280c7cd4d7bdb26243b8a806c26b34b137523a49268224904dc01493e7c0acf1a05c874f69b037b60309d9074d24280e16bad2a8734361951eaf72a482d09b204b1875e12ac98c1aa773d6800b9eafde56d58bed8e8da16f9a360099c37a834a6dfedb7b6b44a049e07a269fccf2c5496f2cf36d64df90a3b8d8f34a3baab4cf53371ab27719b3ba58754ad0c53fc14e1db45d51e234fbbe93c9ba4edf9ce54261350ec535607bf69a2ff4aa07db5f7ea200d09a6c1b49e21402f89ed1190893aab5a9180f152e82f85a45753cf5fc19071c5eec827020103a381fc3081f9301d0603551d0e041604144fe4a0b3dd9cba29f71d7287c4e7c38f2086c2993081c90603551d230481c13081be80144fe4a0b3dd9cba29f71d7287c4e7c38f2086c299a1819aa48197308194310b3009060355040613025553311330110603550408130a43616c69666f726e6961311630140603550407130d4d6f756e7461696e20566965773110300e060355040a1307416e64726f69643110300e060355040b1307416e64726f69643110300e06035504031307416e64726f69643122302006092a864886f70d0109011613616e64726f696440616e64726f69642e636f6d820900b3998086d056cffa300c0603551d13040530030101ff300d06092a864886f70d01010405000382010100572551b8d93a1f73de0f6d469f86dad6701400293c88a0cd7cd778b73dafcc197fab76e6212e56c1c761cfc42fd733de52c50ae08814cefc0a3b5a1a4346054d829f1d82b42b2048bf88b5d14929ef85f60edd12d72d55657e22e3e85d04c831d613d19938bb8982247fa321256ba12d1d6a8f92ea1db1c373317ba0c037f0d1aff645aef224979fba6e7a14bc025c71b98138cef3ddfc059617cf24845cf7b40d6382f7275ed738495ab6e5931b9421765c491b72fb68e080dbdb58c2029d347c8b328ce43ef6a8b15533edfbe989bd6a48dd4b202eda94c6ab8dd5b8399203daae2ed446232e4fe9bd961394c6300e5138e3cfd285e6e4e483538cb8b1b357";
        }
        int i2 = -1;
        if (signatureArr != null) {
            for (int i3 = 0; i3 < signatureArr.length; i3++) {
                if (signatureArr[i3] != null) {
                    int i4 = 0;
                    while (true) {
                        if (i4 >= i) {
                            break;
                        }
                        if (strArr[i4].compareToIgnoreCase(signatureArr[i3].toCharsString().toString()) == 0) {
                            i2 = i4;
                            break;
                        }
                        i4++;
                    }
                }
            }
        }
        if (i2 == -1 && new File("/data/system/.aasa/AASApolicy/ASKSK.xml").exists() && signatureArr != null) {
            for (Signature signature : signatureArr) {
                if (signature != null) {
                    String str2 = signature.toCharsString().toString();
                    if (checkListForASKS(15, str2, null) != -1) {
                        Slog.i("AASA_ASKSManager", " pkg:" + str + " signValue is same with " + str2);
                        return 10;
                    }
                }
            }
        }
        return i2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:88:0x0108, code lost:
    
        if (checkListForASKS(10, r21, null) != (-1)) goto L41;
     */
    /* JADX WARN: Removed duplicated region for block: B:23:0x01c3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int parsePackageForASKS(ASKSSession aSKSSession, String str, String str2, long j, String str3, String str4, int i, boolean z) {
        HashMap hashMap;
        int i2;
        boolean z2;
        int i3;
        "0x1".equals(SystemProperties.get("ro.boot.em.status"));
        boolean equals = this.DEBUG_MODE_FOR_DEVELOPMENT ? true : "true".equals(SystemProperties.get("ro.build.official.release"));
        try {
            aSKSSession.setPkgNameHash(getSHA256ForPkgName(str));
            if ("".equals(aSKSSession.getPkgDigest())) {
                aSKSSession.setPkgDigest(getAdvancedHash(str2));
            }
        } catch (IOException unused) {
        }
        int i4 = -1;
        try {
            if (checkListForASKS(9, aSKSSession.getPkgNameHash(), aSKSSession.getPkgDigest()) != -1) {
                if (equals) {
                    return -7;
                }
                try {
                    Slog.e("AASA_ASKSManager", str + " is in BlackList, so fail to install");
                    Slog.e("AASA_ASKSManager", "anyway continue to install since this binary is not official");
                } catch (IOException e) {
                    e = e;
                    Slog.i("AASA_ASKSManager", "ERROR" + e.toString());
                    hashMap = new HashMap();
                    getADPDataFromXML(hashMap);
                    if (hashMap.containsKey(aSKSSession.getPkgNameHash())) {
                    }
                    Slog.i("AASA_ASKSManager", " " + str + " is installing..");
                    return i4;
                }
            }
            if (isASKSToken(aSKSSession, str2)) {
                Slog.i("AASA_ASKSManager", "checking initiating = " + str4 + ", installer = " + str3);
                if (str4 != null && !"com.android.shell".equals(str4) && checkListForASKS(12, str4, null) != -1) {
                    AndroidPackage androidPackage = getPackageManagerInternal().getPackage(str4);
                    if (androidPackage != null) {
                        if (isPlatformSigned(androidPackage.getSigningDetails().getSignatures())) {
                            z2 = true;
                        } else {
                            Slog.e("AASA_ASKSManager", "wrong installer - " + str4);
                            z2 = false;
                        }
                    } else if ("PrePackageInstaller".equals(str4)) {
                        z2 = true;
                    } else {
                        Slog.e("AASA_ASKSManager", "cannot found package information - " + str4);
                        z2 = false;
                    }
                }
                i2 = -1;
                try {
                    int verifyToken = verifyToken(aSKSSession, str, str3, z2, 1, i);
                    if (verifyToken == 0) {
                        Slog.i("AASA_ASKSManager", "AASA OK");
                    } else {
                        if (equals) {
                            int i5 = verifyToken % 10;
                            if (i5 == 1) {
                                i3 = -3004;
                            } else if (i5 == 2) {
                                i3 = -3002;
                            } else if (i5 == 4) {
                                i3 = -3000;
                            } else if (i5 == 5) {
                                i3 = -3001;
                            } else {
                                if (i5 != 6) {
                                    i4 = -7;
                                    Slog.e("AASA_ASKSManager", " " + str + ", returnValue : " + i4);
                                    return i4;
                                }
                                i3 = -3006;
                            }
                            i4 = i3;
                            Slog.e("AASA_ASKSManager", " " + str + ", returnValue : " + i4);
                            return i4;
                        }
                        Slog.e("AASA_ASKSManager", " " + str + " fail to install");
                        Slog.e("AASA_ASKSManager", "anyway continue to install since this binary is not official");
                    }
                } catch (IOException e2) {
                    e = e2;
                    i4 = -1;
                    Slog.i("AASA_ASKSManager", "ERROR" + e.toString());
                    hashMap = new HashMap();
                    getADPDataFromXML(hashMap);
                    if (hashMap.containsKey(aSKSSession.getPkgNameHash())) {
                    }
                    Slog.i("AASA_ASKSManager", " " + str + " is installing..");
                    return i4;
                }
            } else {
                i2 = -1;
                if (equals) {
                    return -3003;
                }
            }
            i4 = i2;
        } catch (IOException e3) {
            e = e3;
        }
        hashMap = new HashMap();
        getADPDataFromXML(hashMap);
        if (hashMap.containsKey(aSKSSession.getPkgNameHash())) {
            String valueOf = String.valueOf(j);
            Slog.i("AASA_ASKSManager_ADP", "target ADP - " + str + ", " + valueOf);
            ArrayList arrayList = (ArrayList) ADPOperation.filterADPPolicy(((ADPContainer) hashMap.get(aSKSSession.getPkgNameHash())).getADPPolicy(), ADPOperation.isSameCategoryByHashCode(valueOf));
            if (arrayList.size() != 0) {
                Slog.i("AASA_ASKSManager_ADP", "target list has size " + arrayList.size());
                Collections.sort(arrayList);
                ADPContainer.ADPPolicy aDPPolicy = (ADPContainer.ADPPolicy) arrayList.get(0);
                if (aDPPolicy != null) {
                    Slog.i("AASA_ASKSManager_ADP", "picked one adp policy");
                    if (!ADPOperation.isGreaterOrEqual(String.valueOf(j), aDPPolicy)) {
                        Slog.e("AASA_ASKSManager_ADP", "install fail, cannot back to the previous version");
                        return -3005;
                    }
                } else {
                    Slog.e("AASA_ASKSManager_ADP", "cannot get target policy");
                    return -3005;
                }
            }
            Slog.i("AASA_ASKSManager_ADP", "successs");
        }
        Slog.i("AASA_ASKSManager", " " + str + " is installing..");
        return i4;
    }

    public final int checkListForASKS(int i, String str, String str2) {
        HashMap hashMap = new HashMap();
        new ArrayList();
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
            if ((i != 9 && i != 18 && i != 19) || arrayList == null || !arrayList.contains("ALL")) {
                return i2;
            }
        }
        return 0;
    }

    public final boolean checkSmsFilterEnabled() {
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        getASKSDataFromXML(40, hashMap2);
        getASKSDataFromXML(39, hashMap);
        return (hashMap2.size() == 0 && hashMap.size() == 0) ? false : true;
    }

    public final boolean checkIfSmsFilterTarget() {
        boolean equalsIgnoreCase = "KR".equalsIgnoreCase(SemCscFeature.getInstance().getString("CountryISO"));
        String str = SystemProperties.get("ro.product.model", "Unknown");
        HashMap hashMap = new HashMap();
        getASKSDataFromXML(46, hashMap);
        if (hashMap.containsKey("target_model") && equalsIgnoreCase) {
            return ((ArrayList) hashMap.get("target_model")).contains("ALL") || ((ArrayList) hashMap.get("target_model")).contains(str);
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x014a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x012b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void getASKSDataFromXML(int i, HashMap hashMap) {
        File file;
        ArrayList arrayList = new ArrayList();
        String str = "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_PEMLIST.xml";
        switch (i) {
            case 9:
                arrayList.add("HASHVALUE");
                arrayList.add("HASH");
                str = "/data/system/.aasa/AASApolicy/ASKSB.xml";
                file = new File(str);
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
                            for (int eventType = newPullParser.getEventType(); eventType != 1; eventType = newPullParser.next()) {
                                String name = newPullParser.getName();
                                if (eventType == 2) {
                                    if (((String) arrayList.get(0)).equals(name)) {
                                        if (newPullParser.getAttributeValue(0) != null) {
                                            str2 = newPullParser.getAttributeValue(0);
                                        }
                                        arrayList2 = new ArrayList();
                                    } else if (arrayList.contains(name) && newPullParser.getAttributeValue(0) != null && arrayList2 != null) {
                                        arrayList2.add(newPullParser.getAttributeValue(0));
                                    }
                                } else if (eventType == 3 && ((String) arrayList.get(0)).equals(name) && hashMap != null) {
                                    hashMap.put(str2, arrayList2);
                                }
                            }
                            fileReader.close();
                            break;
                        } catch (IOException e) {
                            try {
                                fileReader.close();
                            } catch (IOException unused) {
                            }
                            e.printStackTrace();
                            return;
                        } catch (XmlPullParserException e2) {
                            try {
                                fileReader.close();
                            } catch (IOException unused2) {
                            }
                            e2.printStackTrace();
                            return;
                        }
                    } catch (FileNotFoundException e3) {
                        e3.printStackTrace();
                        return;
                    }
                }
                break;
            case 10:
                arrayList.add("HASHVALUE");
                arrayList.add("UID");
                str = "/data/system/.aasa/AASApolicy/ASKSP.xml";
                file = new File(str);
                if (file.getParentFile() != null) {
                    file.getParentFile().mkdir();
                    file.getParentFile().setReadable(true, false);
                    break;
                }
                if (file.exists()) {
                }
                break;
            case 12:
                arrayList.add("STORE");
                arrayList.add("DUMMY");
                str = "/data/system/.aasa/AASApolicy/ASKSTS.xml";
                file = new File(str);
                if (file.getParentFile() != null) {
                }
                if (file.exists()) {
                }
                break;
            case 14:
                arrayList.add("CERT");
                arrayList.add("NUM");
                str = "/data/system/.aasa/AASApolicy/ASKSC.xml";
                file = new File(str);
                if (file.getParentFile() != null) {
                }
                if (file.exists()) {
                }
                break;
            case 15:
                arrayList.add("STORE");
                arrayList.add("DUMMY");
                str = "/data/system/.aasa/AASApolicy/ASKSK.xml";
                file = new File(str);
                if (file.getParentFile() != null) {
                }
                if (file.exists()) {
                }
                break;
            case 18:
                arrayList.add("HASHVALUE");
                arrayList.add("HASH");
                str = "/data/system/.aasa/AASApolicy/ASKSHB.xml";
                file = new File(str);
                if (file.getParentFile() != null) {
                }
                if (file.exists()) {
                }
                break;
            case 19:
                arrayList.add("package");
                arrayList.add("digest");
                str = "/data/system/.aasa/AASApolicy/protection_list.xml";
                file = new File(str);
                if (file.getParentFile() != null) {
                }
                if (file.exists()) {
                }
                break;
            case 22:
                arrayList.add("package");
                arrayList.add("CERT");
                str = "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_PRE_INSTALLER_H.xml";
                file = new File(str);
                if (file.getParentFile() != null) {
                }
                if (file.exists()) {
                }
                break;
            case 23:
                arrayList.add("TARGET");
                arrayList.add("DEVICE");
                str = "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_TARGETDEVICE.xml";
                file = new File(str);
                if (file.getParentFile() != null) {
                }
                if (file.exists()) {
                }
                break;
            case 24:
                arrayList.add("CERTTARGET");
                arrayList.add("DEVICE");
                str = "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_TARGETDEVICE.xml";
                file = new File(str);
                if (file.getParentFile() != null) {
                }
                if (file.exists()) {
                }
                break;
            case 25:
                arrayList.add("ZIPTARGET");
                arrayList.add("DEVICE");
                str = "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_TARGETDEVICE.xml";
                file = new File(str);
                if (file.getParentFile() != null) {
                }
                if (file.exists()) {
                }
                break;
            case 26:
                arrayList.add("ZIPCERTTARGET");
                arrayList.add("DEVICE");
                str = "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_TARGETDEVICE.xml";
                file = new File(str);
                if (file.getParentFile() != null) {
                }
                if (file.exists()) {
                }
                break;
            case 29:
                arrayList.add("TARGETZIP");
                arrayList.add("PEMLIST");
                file = new File(str);
                if (file.getParentFile() != null) {
                }
                if (file.exists()) {
                }
                break;
            case 30:
                arrayList.add("TARGET");
                arrayList.add("PEMLIST");
                file = new File(str);
                if (file.getParentFile() != null) {
                }
                if (file.exists()) {
                }
                break;
            case 32:
                arrayList.add("package");
                arrayList.add("policy");
                str = "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_3RDPARTY_INSTALLER.xml";
                file = new File(str);
                if (file.getParentFile() != null) {
                }
                if (file.exists()) {
                }
                break;
            case 35:
                arrayList.add("package");
                arrayList.add("CERT");
                str = "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_TRUSTEDSTORE.xml";
                file = new File(str);
                if (file.getParentFile() != null) {
                }
                if (file.exists()) {
                }
                break;
            case 36:
                arrayList.add("REGIONAL");
                arrayList.add("DEVICE");
                str = "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_TARGETDEVICE.xml";
                file = new File(str);
                if (file.getParentFile() != null) {
                }
                if (file.exists()) {
                }
                break;
            case 39:
                arrayList.add("contents");
                arrayList.add("pid");
                str = "/data/system/.aasa/AASApolicy/ASKS_BLOCK_URL_LIST.xml";
                file = new File(str);
                if (file.getParentFile() != null) {
                }
                if (file.exists()) {
                }
                break;
            case 40:
                arrayList.add("contents");
                arrayList.add("pid");
                str = "/data/system/.aasa/AASApolicy/ASKS_BLOCK_NUM_LIST.xml";
                file = new File(str);
                if (file.getParentFile() != null) {
                }
                if (file.exists()) {
                }
                break;
            case 41:
                arrayList.add("package");
                arrayList.add("CERT");
                str = "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_SIMPLETRUSTEDSTORE.xml";
                file = new File(str);
                if (file.getParentFile() != null) {
                }
                if (file.exists()) {
                }
                break;
            case 43:
                arrayList.add("package");
                arrayList.add("CERT");
                str = "/data/system/.aasa/AASApolicy/RPAB.xml";
                file = new File(str);
                if (file.getParentFile() != null) {
                }
                if (file.exists()) {
                }
                break;
            case 44:
                arrayList.add("package");
                arrayList.add("CERT");
                str = "/data/system/.aasa/AASApolicy/RPAB1.xml";
                file = new File(str);
                if (file.getParentFile() != null) {
                }
                if (file.exists()) {
                }
                break;
            case 45:
                arrayList.add("package");
                arrayList.add("CERT");
                str = "/data/system/.aasa/AASApolicy/RPAB2.xml";
                file = new File(str);
                if (file.getParentFile() != null) {
                }
                if (file.exists()) {
                }
                break;
            case 46:
                arrayList.add("config");
                arrayList.add("value");
                str = "/data/system/.aasa/AASApolicy/ASKS_SPAM_CONFIG.xml";
                file = new File(str);
                if (file.getParentFile() != null) {
                }
                if (file.exists()) {
                }
                break;
            case 47:
                arrayList.add("TARGET");
                arrayList.add("VALUE");
                str = "/data/system/.aasa/AASApolicy/ASKS_UNKNOWN_DEVPARAM.xml";
                file = new File(str);
                if (file.getParentFile() != null) {
                }
                if (file.exists()) {
                }
                break;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x00c1, code lost:
    
        if (r4 == null) goto L43;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isASKSToken(ASKSSession aSKSSession, String str) {
        Iterator strictJarFile;
        boolean z = false;
        Iterator it = null;
        try {
            try {
                strictJarFile = new StrictJarFile(str, false, true);
            } catch (Throwable th) {
                th = th;
            }
        } catch (IOException e) {
            e = e;
        } catch (SecurityException e2) {
            e = e2;
        }
        try {
            it = strictJarFile.iterator();
            while (it.hasNext()) {
                String name = ((ZipEntry) it.next()).getName();
                if (name.startsWith("SEC-INF") && name.contains("buildinfo")) {
                    aSKSSession.setCodePath(str);
                    aSKSSession.setTokenName(name);
                    aSKSSession.setCertName("SEC-INF" + File.separator + "buildConfirm.crt");
                } else if (name.startsWith("META-INF") && name.contains("SEC-INF") && name.contains("buildinfo")) {
                    aSKSSession.setCodePath(str);
                    aSKSSession.setTokenName(name);
                    StringBuilder sb = new StringBuilder();
                    sb.append("META-INF");
                    String str2 = File.separator;
                    sb.append(str2);
                    sb.append("SEC-INF");
                    sb.append(str2);
                    sb.append("buildConfirm.crt");
                    aSKSSession.setCertName(sb.toString());
                }
                z = true;
            }
            strictJarFile.close();
        } catch (IOException e3) {
            e = e3;
            it = strictJarFile;
            Slog.i("AASA_ASKSManager", " ERROR: AASA_ASKSIsToken " + e);
        } catch (SecurityException e4) {
            e = e4;
            it = strictJarFile;
            Slog.i("AASA_ASKSManager", " ERROR: AASA_ASKSIsToken " + e);
            if (it != null) {
                it.close();
            }
            return z;
        } catch (Throwable th2) {
            th = th2;
            it = strictJarFile;
            if (it != null) {
                it.close();
            }
            throw th;
        }
        return z;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:0|1|(2:2|3)|(3:304|305|(6:307|6|7|(1:9)(1:292)|10|(2:12|13)(2:15|(4:284|285|286|288)(6:19|20|21|22|23|(1:25)(36:26|27|28|(3:30|31|(2:33|(1:35)(2:36|(3:38|39|40)(1:239)))(2:240|(2:242|40)))(2:255|(1:(5:259|260|261|262|263)(7:258|42|(3:43|44|(1:46)(1:47))|48|(1:54)|55|(4:235|(2:237|67)|68|(17:223|(2:234|74)|75|(1:206)(1:79)|80|(2:84|(2:86|(1:88))(1:89))|90|(3:202|(1:204)|205)(5:94|95|96|(1:98)|99)|100|(4:104|105|106|(2:108|109)(2:110|(2:112|113)))|116|(2:120|(2:122|(1:127)(1:126))(4:128|(1:138)(1:132)|(1:136)|137))|139|(3:143|144|145)|149|(3:153|(1:193)(2:157|(1:192)(2:161|(1:190)(8:165|166|167|168|(1:176)|177|(1:185)|186)))|191)|(2:195|196)(2:197|198))(22:72|(21:207|(1:209)(2:210|(2:216|(1:222)(2:220|221))(2:214|215))|75|(1:77)|206|80|(3:82|84|(0)(0))|90|(1:92)|202|(0)|205|100|(5:102|104|105|106|(0)(0))|116|(3:118|120|(0)(0))|139|(4:141|143|144|145)|149|(5:151|153|(1:155)|193|191)|(0)(0))|74|75|(0)|206|80|(0)|90|(0)|202|(0)|205|100|(0)|116|(0)|139|(0)|149|(0)|(0)(0)))(25:59|(2:61|(2:66|67)(1:65))|68|(1:70)|223|(6:225|227|229|231|234|74)|75|(0)|206|80|(0)|90|(0)|202|(0)|205|100|(0)|116|(0)|139|(0)|149|(0)|(0)(0)))))|41|42|(4:43|44|(0)(0)|46)|48|(3:50|52|54)|55|(1:57)|235|(0)|68|(0)|223|(0)|75|(0)|206|80|(0)|90|(0)|202|(0)|205|100|(0)|116|(0)|139|(0)|149|(0)|(0)(0))))))|5|6|7|(0)(0)|10|(0)(0)|(2:(0)|(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:293:0x0053, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:294:0x0054, code lost:
    
        r12 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:296:0x008d, code lost:
    
        if (r11 != null) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:299:0x0050, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:300:0x0051, code lost:
    
        r12 = r0;
     */
    /* JADX WARN: Not initialized variable reg: 11, insn: 0x07a8: MOVE (r10 I:??[OBJECT, ARRAY]) = (r11 I:??[OBJECT, ARRAY]), block:B:315:0x07a7 */
    /* JADX WARN: Removed duplicated region for block: B:102:0x046b A[Catch: NumberFormatException -> 0x072c, IOException -> 0x072e, XmlPullParserException -> 0x0772, TryCatch #3 {XmlPullParserException -> 0x0772, blocks: (B:42:0x0270, B:43:0x0279, B:46:0x0283, B:48:0x028f, B:50:0x02b4, B:52:0x02be, B:54:0x02cb, B:55:0x02e0, B:57:0x02e6, B:61:0x02f3, B:63:0x02f9, B:65:0x0303, B:66:0x0309, B:67:0x032b, B:68:0x0332, B:70:0x0338, B:72:0x0342, B:74:0x034e, B:75:0x03b5, B:77:0x03bb, B:79:0x03c5, B:80:0x03d0, B:82:0x03d6, B:86:0x03e3, B:88:0x03ef, B:89:0x03f7, B:90:0x03fd, B:92:0x0403, B:94:0x040d, B:96:0x0427, B:98:0x043c, B:99:0x0444, B:100:0x0463, B:102:0x046b, B:104:0x0475, B:106:0x048f, B:108:0x049f, B:110:0x04a7, B:112:0x04b5, B:114:0x04be, B:116:0x04c6, B:118:0x04ce, B:120:0x04d8, B:122:0x04e4, B:124:0x0511, B:126:0x051b, B:127:0x0529, B:128:0x0533, B:130:0x0551, B:132:0x055b, B:134:0x0572, B:136:0x057e, B:137:0x059c, B:138:0x0566, B:139:0x059f, B:141:0x05a5, B:143:0x05af, B:145:0x05cb, B:147:0x05dd, B:149:0x05e7, B:151:0x05ef, B:153:0x05f9, B:155:0x0601, B:157:0x060b, B:159:0x0613, B:161:0x061d, B:163:0x0625, B:165:0x062f, B:167:0x0658, B:168:0x0682, B:170:0x068a, B:172:0x0696, B:174:0x069e, B:176:0x06aa, B:177:0x06c0, B:179:0x06c8, B:181:0x06d4, B:183:0x06dc, B:185:0x06e8, B:186:0x06fe, B:189:0x0676, B:190:0x0702, B:191:0x0719, B:192:0x070a, B:193:0x0712, B:195:0x071d, B:197:0x0724, B:200:0x044c, B:204:0x045b, B:207:0x0351, B:209:0x035c, B:210:0x0362, B:212:0x036b, B:214:0x036f, B:216:0x0377, B:218:0x037f, B:220:0x0383, B:222:0x038b, B:225:0x0394, B:234:0x03af, B:249:0x0733, B:245:0x0754, B:262:0x01f5, B:258:0x0225), top: B:20:0x00a8 }] */
    /* JADX WARN: Removed duplicated region for block: B:108:0x049f A[Catch: NumberFormatException -> 0x04be, IOException -> 0x072e, XmlPullParserException -> 0x0772, TryCatch #3 {XmlPullParserException -> 0x0772, blocks: (B:42:0x0270, B:43:0x0279, B:46:0x0283, B:48:0x028f, B:50:0x02b4, B:52:0x02be, B:54:0x02cb, B:55:0x02e0, B:57:0x02e6, B:61:0x02f3, B:63:0x02f9, B:65:0x0303, B:66:0x0309, B:67:0x032b, B:68:0x0332, B:70:0x0338, B:72:0x0342, B:74:0x034e, B:75:0x03b5, B:77:0x03bb, B:79:0x03c5, B:80:0x03d0, B:82:0x03d6, B:86:0x03e3, B:88:0x03ef, B:89:0x03f7, B:90:0x03fd, B:92:0x0403, B:94:0x040d, B:96:0x0427, B:98:0x043c, B:99:0x0444, B:100:0x0463, B:102:0x046b, B:104:0x0475, B:106:0x048f, B:108:0x049f, B:110:0x04a7, B:112:0x04b5, B:114:0x04be, B:116:0x04c6, B:118:0x04ce, B:120:0x04d8, B:122:0x04e4, B:124:0x0511, B:126:0x051b, B:127:0x0529, B:128:0x0533, B:130:0x0551, B:132:0x055b, B:134:0x0572, B:136:0x057e, B:137:0x059c, B:138:0x0566, B:139:0x059f, B:141:0x05a5, B:143:0x05af, B:145:0x05cb, B:147:0x05dd, B:149:0x05e7, B:151:0x05ef, B:153:0x05f9, B:155:0x0601, B:157:0x060b, B:159:0x0613, B:161:0x061d, B:163:0x0625, B:165:0x062f, B:167:0x0658, B:168:0x0682, B:170:0x068a, B:172:0x0696, B:174:0x069e, B:176:0x06aa, B:177:0x06c0, B:179:0x06c8, B:181:0x06d4, B:183:0x06dc, B:185:0x06e8, B:186:0x06fe, B:189:0x0676, B:190:0x0702, B:191:0x0719, B:192:0x070a, B:193:0x0712, B:195:0x071d, B:197:0x0724, B:200:0x044c, B:204:0x045b, B:207:0x0351, B:209:0x035c, B:210:0x0362, B:212:0x036b, B:214:0x036f, B:216:0x0377, B:218:0x037f, B:220:0x0383, B:222:0x038b, B:225:0x0394, B:234:0x03af, B:249:0x0733, B:245:0x0754, B:262:0x01f5, B:258:0x0225), top: B:20:0x00a8 }] */
    /* JADX WARN: Removed duplicated region for block: B:110:0x04a7 A[Catch: NumberFormatException -> 0x04be, IOException -> 0x072e, XmlPullParserException -> 0x0772, TryCatch #3 {XmlPullParserException -> 0x0772, blocks: (B:42:0x0270, B:43:0x0279, B:46:0x0283, B:48:0x028f, B:50:0x02b4, B:52:0x02be, B:54:0x02cb, B:55:0x02e0, B:57:0x02e6, B:61:0x02f3, B:63:0x02f9, B:65:0x0303, B:66:0x0309, B:67:0x032b, B:68:0x0332, B:70:0x0338, B:72:0x0342, B:74:0x034e, B:75:0x03b5, B:77:0x03bb, B:79:0x03c5, B:80:0x03d0, B:82:0x03d6, B:86:0x03e3, B:88:0x03ef, B:89:0x03f7, B:90:0x03fd, B:92:0x0403, B:94:0x040d, B:96:0x0427, B:98:0x043c, B:99:0x0444, B:100:0x0463, B:102:0x046b, B:104:0x0475, B:106:0x048f, B:108:0x049f, B:110:0x04a7, B:112:0x04b5, B:114:0x04be, B:116:0x04c6, B:118:0x04ce, B:120:0x04d8, B:122:0x04e4, B:124:0x0511, B:126:0x051b, B:127:0x0529, B:128:0x0533, B:130:0x0551, B:132:0x055b, B:134:0x0572, B:136:0x057e, B:137:0x059c, B:138:0x0566, B:139:0x059f, B:141:0x05a5, B:143:0x05af, B:145:0x05cb, B:147:0x05dd, B:149:0x05e7, B:151:0x05ef, B:153:0x05f9, B:155:0x0601, B:157:0x060b, B:159:0x0613, B:161:0x061d, B:163:0x0625, B:165:0x062f, B:167:0x0658, B:168:0x0682, B:170:0x068a, B:172:0x0696, B:174:0x069e, B:176:0x06aa, B:177:0x06c0, B:179:0x06c8, B:181:0x06d4, B:183:0x06dc, B:185:0x06e8, B:186:0x06fe, B:189:0x0676, B:190:0x0702, B:191:0x0719, B:192:0x070a, B:193:0x0712, B:195:0x071d, B:197:0x0724, B:200:0x044c, B:204:0x045b, B:207:0x0351, B:209:0x035c, B:210:0x0362, B:212:0x036b, B:214:0x036f, B:216:0x0377, B:218:0x037f, B:220:0x0383, B:222:0x038b, B:225:0x0394, B:234:0x03af, B:249:0x0733, B:245:0x0754, B:262:0x01f5, B:258:0x0225), top: B:20:0x00a8 }] */
    /* JADX WARN: Removed duplicated region for block: B:118:0x04ce A[Catch: NumberFormatException -> 0x072c, IOException -> 0x072e, XmlPullParserException -> 0x0772, TryCatch #3 {XmlPullParserException -> 0x0772, blocks: (B:42:0x0270, B:43:0x0279, B:46:0x0283, B:48:0x028f, B:50:0x02b4, B:52:0x02be, B:54:0x02cb, B:55:0x02e0, B:57:0x02e6, B:61:0x02f3, B:63:0x02f9, B:65:0x0303, B:66:0x0309, B:67:0x032b, B:68:0x0332, B:70:0x0338, B:72:0x0342, B:74:0x034e, B:75:0x03b5, B:77:0x03bb, B:79:0x03c5, B:80:0x03d0, B:82:0x03d6, B:86:0x03e3, B:88:0x03ef, B:89:0x03f7, B:90:0x03fd, B:92:0x0403, B:94:0x040d, B:96:0x0427, B:98:0x043c, B:99:0x0444, B:100:0x0463, B:102:0x046b, B:104:0x0475, B:106:0x048f, B:108:0x049f, B:110:0x04a7, B:112:0x04b5, B:114:0x04be, B:116:0x04c6, B:118:0x04ce, B:120:0x04d8, B:122:0x04e4, B:124:0x0511, B:126:0x051b, B:127:0x0529, B:128:0x0533, B:130:0x0551, B:132:0x055b, B:134:0x0572, B:136:0x057e, B:137:0x059c, B:138:0x0566, B:139:0x059f, B:141:0x05a5, B:143:0x05af, B:145:0x05cb, B:147:0x05dd, B:149:0x05e7, B:151:0x05ef, B:153:0x05f9, B:155:0x0601, B:157:0x060b, B:159:0x0613, B:161:0x061d, B:163:0x0625, B:165:0x062f, B:167:0x0658, B:168:0x0682, B:170:0x068a, B:172:0x0696, B:174:0x069e, B:176:0x06aa, B:177:0x06c0, B:179:0x06c8, B:181:0x06d4, B:183:0x06dc, B:185:0x06e8, B:186:0x06fe, B:189:0x0676, B:190:0x0702, B:191:0x0719, B:192:0x070a, B:193:0x0712, B:195:0x071d, B:197:0x0724, B:200:0x044c, B:204:0x045b, B:207:0x0351, B:209:0x035c, B:210:0x0362, B:212:0x036b, B:214:0x036f, B:216:0x0377, B:218:0x037f, B:220:0x0383, B:222:0x038b, B:225:0x0394, B:234:0x03af, B:249:0x0733, B:245:0x0754, B:262:0x01f5, B:258:0x0225), top: B:20:0x00a8 }] */
    /* JADX WARN: Removed duplicated region for block: B:122:0x04e4 A[Catch: NumberFormatException -> 0x072c, IOException -> 0x072e, XmlPullParserException -> 0x0772, TryCatch #3 {XmlPullParserException -> 0x0772, blocks: (B:42:0x0270, B:43:0x0279, B:46:0x0283, B:48:0x028f, B:50:0x02b4, B:52:0x02be, B:54:0x02cb, B:55:0x02e0, B:57:0x02e6, B:61:0x02f3, B:63:0x02f9, B:65:0x0303, B:66:0x0309, B:67:0x032b, B:68:0x0332, B:70:0x0338, B:72:0x0342, B:74:0x034e, B:75:0x03b5, B:77:0x03bb, B:79:0x03c5, B:80:0x03d0, B:82:0x03d6, B:86:0x03e3, B:88:0x03ef, B:89:0x03f7, B:90:0x03fd, B:92:0x0403, B:94:0x040d, B:96:0x0427, B:98:0x043c, B:99:0x0444, B:100:0x0463, B:102:0x046b, B:104:0x0475, B:106:0x048f, B:108:0x049f, B:110:0x04a7, B:112:0x04b5, B:114:0x04be, B:116:0x04c6, B:118:0x04ce, B:120:0x04d8, B:122:0x04e4, B:124:0x0511, B:126:0x051b, B:127:0x0529, B:128:0x0533, B:130:0x0551, B:132:0x055b, B:134:0x0572, B:136:0x057e, B:137:0x059c, B:138:0x0566, B:139:0x059f, B:141:0x05a5, B:143:0x05af, B:145:0x05cb, B:147:0x05dd, B:149:0x05e7, B:151:0x05ef, B:153:0x05f9, B:155:0x0601, B:157:0x060b, B:159:0x0613, B:161:0x061d, B:163:0x0625, B:165:0x062f, B:167:0x0658, B:168:0x0682, B:170:0x068a, B:172:0x0696, B:174:0x069e, B:176:0x06aa, B:177:0x06c0, B:179:0x06c8, B:181:0x06d4, B:183:0x06dc, B:185:0x06e8, B:186:0x06fe, B:189:0x0676, B:190:0x0702, B:191:0x0719, B:192:0x070a, B:193:0x0712, B:195:0x071d, B:197:0x0724, B:200:0x044c, B:204:0x045b, B:207:0x0351, B:209:0x035c, B:210:0x0362, B:212:0x036b, B:214:0x036f, B:216:0x0377, B:218:0x037f, B:220:0x0383, B:222:0x038b, B:225:0x0394, B:234:0x03af, B:249:0x0733, B:245:0x0754, B:262:0x01f5, B:258:0x0225), top: B:20:0x00a8 }] */
    /* JADX WARN: Removed duplicated region for block: B:128:0x0533 A[Catch: NumberFormatException -> 0x072c, IOException -> 0x072e, XmlPullParserException -> 0x0772, TryCatch #3 {XmlPullParserException -> 0x0772, blocks: (B:42:0x0270, B:43:0x0279, B:46:0x0283, B:48:0x028f, B:50:0x02b4, B:52:0x02be, B:54:0x02cb, B:55:0x02e0, B:57:0x02e6, B:61:0x02f3, B:63:0x02f9, B:65:0x0303, B:66:0x0309, B:67:0x032b, B:68:0x0332, B:70:0x0338, B:72:0x0342, B:74:0x034e, B:75:0x03b5, B:77:0x03bb, B:79:0x03c5, B:80:0x03d0, B:82:0x03d6, B:86:0x03e3, B:88:0x03ef, B:89:0x03f7, B:90:0x03fd, B:92:0x0403, B:94:0x040d, B:96:0x0427, B:98:0x043c, B:99:0x0444, B:100:0x0463, B:102:0x046b, B:104:0x0475, B:106:0x048f, B:108:0x049f, B:110:0x04a7, B:112:0x04b5, B:114:0x04be, B:116:0x04c6, B:118:0x04ce, B:120:0x04d8, B:122:0x04e4, B:124:0x0511, B:126:0x051b, B:127:0x0529, B:128:0x0533, B:130:0x0551, B:132:0x055b, B:134:0x0572, B:136:0x057e, B:137:0x059c, B:138:0x0566, B:139:0x059f, B:141:0x05a5, B:143:0x05af, B:145:0x05cb, B:147:0x05dd, B:149:0x05e7, B:151:0x05ef, B:153:0x05f9, B:155:0x0601, B:157:0x060b, B:159:0x0613, B:161:0x061d, B:163:0x0625, B:165:0x062f, B:167:0x0658, B:168:0x0682, B:170:0x068a, B:172:0x0696, B:174:0x069e, B:176:0x06aa, B:177:0x06c0, B:179:0x06c8, B:181:0x06d4, B:183:0x06dc, B:185:0x06e8, B:186:0x06fe, B:189:0x0676, B:190:0x0702, B:191:0x0719, B:192:0x070a, B:193:0x0712, B:195:0x071d, B:197:0x0724, B:200:0x044c, B:204:0x045b, B:207:0x0351, B:209:0x035c, B:210:0x0362, B:212:0x036b, B:214:0x036f, B:216:0x0377, B:218:0x037f, B:220:0x0383, B:222:0x038b, B:225:0x0394, B:234:0x03af, B:249:0x0733, B:245:0x0754, B:262:0x01f5, B:258:0x0225), top: B:20:0x00a8 }] */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x05a5 A[Catch: NumberFormatException -> 0x072c, IOException -> 0x072e, XmlPullParserException -> 0x0772, TryCatch #3 {XmlPullParserException -> 0x0772, blocks: (B:42:0x0270, B:43:0x0279, B:46:0x0283, B:48:0x028f, B:50:0x02b4, B:52:0x02be, B:54:0x02cb, B:55:0x02e0, B:57:0x02e6, B:61:0x02f3, B:63:0x02f9, B:65:0x0303, B:66:0x0309, B:67:0x032b, B:68:0x0332, B:70:0x0338, B:72:0x0342, B:74:0x034e, B:75:0x03b5, B:77:0x03bb, B:79:0x03c5, B:80:0x03d0, B:82:0x03d6, B:86:0x03e3, B:88:0x03ef, B:89:0x03f7, B:90:0x03fd, B:92:0x0403, B:94:0x040d, B:96:0x0427, B:98:0x043c, B:99:0x0444, B:100:0x0463, B:102:0x046b, B:104:0x0475, B:106:0x048f, B:108:0x049f, B:110:0x04a7, B:112:0x04b5, B:114:0x04be, B:116:0x04c6, B:118:0x04ce, B:120:0x04d8, B:122:0x04e4, B:124:0x0511, B:126:0x051b, B:127:0x0529, B:128:0x0533, B:130:0x0551, B:132:0x055b, B:134:0x0572, B:136:0x057e, B:137:0x059c, B:138:0x0566, B:139:0x059f, B:141:0x05a5, B:143:0x05af, B:145:0x05cb, B:147:0x05dd, B:149:0x05e7, B:151:0x05ef, B:153:0x05f9, B:155:0x0601, B:157:0x060b, B:159:0x0613, B:161:0x061d, B:163:0x0625, B:165:0x062f, B:167:0x0658, B:168:0x0682, B:170:0x068a, B:172:0x0696, B:174:0x069e, B:176:0x06aa, B:177:0x06c0, B:179:0x06c8, B:181:0x06d4, B:183:0x06dc, B:185:0x06e8, B:186:0x06fe, B:189:0x0676, B:190:0x0702, B:191:0x0719, B:192:0x070a, B:193:0x0712, B:195:0x071d, B:197:0x0724, B:200:0x044c, B:204:0x045b, B:207:0x0351, B:209:0x035c, B:210:0x0362, B:212:0x036b, B:214:0x036f, B:216:0x0377, B:218:0x037f, B:220:0x0383, B:222:0x038b, B:225:0x0394, B:234:0x03af, B:249:0x0733, B:245:0x0754, B:262:0x01f5, B:258:0x0225), top: B:20:0x00a8 }] */
    /* JADX WARN: Removed duplicated region for block: B:151:0x05ef A[Catch: NumberFormatException -> 0x072c, IOException -> 0x072e, XmlPullParserException -> 0x0772, TryCatch #3 {XmlPullParserException -> 0x0772, blocks: (B:42:0x0270, B:43:0x0279, B:46:0x0283, B:48:0x028f, B:50:0x02b4, B:52:0x02be, B:54:0x02cb, B:55:0x02e0, B:57:0x02e6, B:61:0x02f3, B:63:0x02f9, B:65:0x0303, B:66:0x0309, B:67:0x032b, B:68:0x0332, B:70:0x0338, B:72:0x0342, B:74:0x034e, B:75:0x03b5, B:77:0x03bb, B:79:0x03c5, B:80:0x03d0, B:82:0x03d6, B:86:0x03e3, B:88:0x03ef, B:89:0x03f7, B:90:0x03fd, B:92:0x0403, B:94:0x040d, B:96:0x0427, B:98:0x043c, B:99:0x0444, B:100:0x0463, B:102:0x046b, B:104:0x0475, B:106:0x048f, B:108:0x049f, B:110:0x04a7, B:112:0x04b5, B:114:0x04be, B:116:0x04c6, B:118:0x04ce, B:120:0x04d8, B:122:0x04e4, B:124:0x0511, B:126:0x051b, B:127:0x0529, B:128:0x0533, B:130:0x0551, B:132:0x055b, B:134:0x0572, B:136:0x057e, B:137:0x059c, B:138:0x0566, B:139:0x059f, B:141:0x05a5, B:143:0x05af, B:145:0x05cb, B:147:0x05dd, B:149:0x05e7, B:151:0x05ef, B:153:0x05f9, B:155:0x0601, B:157:0x060b, B:159:0x0613, B:161:0x061d, B:163:0x0625, B:165:0x062f, B:167:0x0658, B:168:0x0682, B:170:0x068a, B:172:0x0696, B:174:0x069e, B:176:0x06aa, B:177:0x06c0, B:179:0x06c8, B:181:0x06d4, B:183:0x06dc, B:185:0x06e8, B:186:0x06fe, B:189:0x0676, B:190:0x0702, B:191:0x0719, B:192:0x070a, B:193:0x0712, B:195:0x071d, B:197:0x0724, B:200:0x044c, B:204:0x045b, B:207:0x0351, B:209:0x035c, B:210:0x0362, B:212:0x036b, B:214:0x036f, B:216:0x0377, B:218:0x037f, B:220:0x0383, B:222:0x038b, B:225:0x0394, B:234:0x03af, B:249:0x0733, B:245:0x0754, B:262:0x01f5, B:258:0x0225), top: B:20:0x00a8 }] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:195:0x071d A[Catch: NumberFormatException -> 0x072c, IOException -> 0x072e, XmlPullParserException -> 0x0772, TryCatch #3 {XmlPullParserException -> 0x0772, blocks: (B:42:0x0270, B:43:0x0279, B:46:0x0283, B:48:0x028f, B:50:0x02b4, B:52:0x02be, B:54:0x02cb, B:55:0x02e0, B:57:0x02e6, B:61:0x02f3, B:63:0x02f9, B:65:0x0303, B:66:0x0309, B:67:0x032b, B:68:0x0332, B:70:0x0338, B:72:0x0342, B:74:0x034e, B:75:0x03b5, B:77:0x03bb, B:79:0x03c5, B:80:0x03d0, B:82:0x03d6, B:86:0x03e3, B:88:0x03ef, B:89:0x03f7, B:90:0x03fd, B:92:0x0403, B:94:0x040d, B:96:0x0427, B:98:0x043c, B:99:0x0444, B:100:0x0463, B:102:0x046b, B:104:0x0475, B:106:0x048f, B:108:0x049f, B:110:0x04a7, B:112:0x04b5, B:114:0x04be, B:116:0x04c6, B:118:0x04ce, B:120:0x04d8, B:122:0x04e4, B:124:0x0511, B:126:0x051b, B:127:0x0529, B:128:0x0533, B:130:0x0551, B:132:0x055b, B:134:0x0572, B:136:0x057e, B:137:0x059c, B:138:0x0566, B:139:0x059f, B:141:0x05a5, B:143:0x05af, B:145:0x05cb, B:147:0x05dd, B:149:0x05e7, B:151:0x05ef, B:153:0x05f9, B:155:0x0601, B:157:0x060b, B:159:0x0613, B:161:0x061d, B:163:0x0625, B:165:0x062f, B:167:0x0658, B:168:0x0682, B:170:0x068a, B:172:0x0696, B:174:0x069e, B:176:0x06aa, B:177:0x06c0, B:179:0x06c8, B:181:0x06d4, B:183:0x06dc, B:185:0x06e8, B:186:0x06fe, B:189:0x0676, B:190:0x0702, B:191:0x0719, B:192:0x070a, B:193:0x0712, B:195:0x071d, B:197:0x0724, B:200:0x044c, B:204:0x045b, B:207:0x0351, B:209:0x035c, B:210:0x0362, B:212:0x036b, B:214:0x036f, B:216:0x0377, B:218:0x037f, B:220:0x0383, B:222:0x038b, B:225:0x0394, B:234:0x03af, B:249:0x0733, B:245:0x0754, B:262:0x01f5, B:258:0x0225), top: B:20:0x00a8 }] */
    /* JADX WARN: Removed duplicated region for block: B:197:0x0724 A[Catch: NumberFormatException -> 0x072c, IOException -> 0x072e, XmlPullParserException -> 0x0772, TRY_LEAVE, TryCatch #3 {XmlPullParserException -> 0x0772, blocks: (B:42:0x0270, B:43:0x0279, B:46:0x0283, B:48:0x028f, B:50:0x02b4, B:52:0x02be, B:54:0x02cb, B:55:0x02e0, B:57:0x02e6, B:61:0x02f3, B:63:0x02f9, B:65:0x0303, B:66:0x0309, B:67:0x032b, B:68:0x0332, B:70:0x0338, B:72:0x0342, B:74:0x034e, B:75:0x03b5, B:77:0x03bb, B:79:0x03c5, B:80:0x03d0, B:82:0x03d6, B:86:0x03e3, B:88:0x03ef, B:89:0x03f7, B:90:0x03fd, B:92:0x0403, B:94:0x040d, B:96:0x0427, B:98:0x043c, B:99:0x0444, B:100:0x0463, B:102:0x046b, B:104:0x0475, B:106:0x048f, B:108:0x049f, B:110:0x04a7, B:112:0x04b5, B:114:0x04be, B:116:0x04c6, B:118:0x04ce, B:120:0x04d8, B:122:0x04e4, B:124:0x0511, B:126:0x051b, B:127:0x0529, B:128:0x0533, B:130:0x0551, B:132:0x055b, B:134:0x0572, B:136:0x057e, B:137:0x059c, B:138:0x0566, B:139:0x059f, B:141:0x05a5, B:143:0x05af, B:145:0x05cb, B:147:0x05dd, B:149:0x05e7, B:151:0x05ef, B:153:0x05f9, B:155:0x0601, B:157:0x060b, B:159:0x0613, B:161:0x061d, B:163:0x0625, B:165:0x062f, B:167:0x0658, B:168:0x0682, B:170:0x068a, B:172:0x0696, B:174:0x069e, B:176:0x06aa, B:177:0x06c0, B:179:0x06c8, B:181:0x06d4, B:183:0x06dc, B:185:0x06e8, B:186:0x06fe, B:189:0x0676, B:190:0x0702, B:191:0x0719, B:192:0x070a, B:193:0x0712, B:195:0x071d, B:197:0x0724, B:200:0x044c, B:204:0x045b, B:207:0x0351, B:209:0x035c, B:210:0x0362, B:212:0x036b, B:214:0x036f, B:216:0x0377, B:218:0x037f, B:220:0x0383, B:222:0x038b, B:225:0x0394, B:234:0x03af, B:249:0x0733, B:245:0x0754, B:262:0x01f5, B:258:0x0225), top: B:20:0x00a8 }] */
    /* JADX WARN: Removed duplicated region for block: B:204:0x045b A[Catch: NumberFormatException -> 0x072c, IOException -> 0x072e, XmlPullParserException -> 0x0772, TryCatch #3 {XmlPullParserException -> 0x0772, blocks: (B:42:0x0270, B:43:0x0279, B:46:0x0283, B:48:0x028f, B:50:0x02b4, B:52:0x02be, B:54:0x02cb, B:55:0x02e0, B:57:0x02e6, B:61:0x02f3, B:63:0x02f9, B:65:0x0303, B:66:0x0309, B:67:0x032b, B:68:0x0332, B:70:0x0338, B:72:0x0342, B:74:0x034e, B:75:0x03b5, B:77:0x03bb, B:79:0x03c5, B:80:0x03d0, B:82:0x03d6, B:86:0x03e3, B:88:0x03ef, B:89:0x03f7, B:90:0x03fd, B:92:0x0403, B:94:0x040d, B:96:0x0427, B:98:0x043c, B:99:0x0444, B:100:0x0463, B:102:0x046b, B:104:0x0475, B:106:0x048f, B:108:0x049f, B:110:0x04a7, B:112:0x04b5, B:114:0x04be, B:116:0x04c6, B:118:0x04ce, B:120:0x04d8, B:122:0x04e4, B:124:0x0511, B:126:0x051b, B:127:0x0529, B:128:0x0533, B:130:0x0551, B:132:0x055b, B:134:0x0572, B:136:0x057e, B:137:0x059c, B:138:0x0566, B:139:0x059f, B:141:0x05a5, B:143:0x05af, B:145:0x05cb, B:147:0x05dd, B:149:0x05e7, B:151:0x05ef, B:153:0x05f9, B:155:0x0601, B:157:0x060b, B:159:0x0613, B:161:0x061d, B:163:0x0625, B:165:0x062f, B:167:0x0658, B:168:0x0682, B:170:0x068a, B:172:0x0696, B:174:0x069e, B:176:0x06aa, B:177:0x06c0, B:179:0x06c8, B:181:0x06d4, B:183:0x06dc, B:185:0x06e8, B:186:0x06fe, B:189:0x0676, B:190:0x0702, B:191:0x0719, B:192:0x070a, B:193:0x0712, B:195:0x071d, B:197:0x0724, B:200:0x044c, B:204:0x045b, B:207:0x0351, B:209:0x035c, B:210:0x0362, B:212:0x036b, B:214:0x036f, B:216:0x0377, B:218:0x037f, B:220:0x0383, B:222:0x038b, B:225:0x0394, B:234:0x03af, B:249:0x0733, B:245:0x0754, B:262:0x01f5, B:258:0x0225), top: B:20:0x00a8 }] */
    /* JADX WARN: Removed duplicated region for block: B:225:0x0394 A[Catch: NumberFormatException -> 0x072c, IOException -> 0x072e, XmlPullParserException -> 0x0772, TryCatch #3 {XmlPullParserException -> 0x0772, blocks: (B:42:0x0270, B:43:0x0279, B:46:0x0283, B:48:0x028f, B:50:0x02b4, B:52:0x02be, B:54:0x02cb, B:55:0x02e0, B:57:0x02e6, B:61:0x02f3, B:63:0x02f9, B:65:0x0303, B:66:0x0309, B:67:0x032b, B:68:0x0332, B:70:0x0338, B:72:0x0342, B:74:0x034e, B:75:0x03b5, B:77:0x03bb, B:79:0x03c5, B:80:0x03d0, B:82:0x03d6, B:86:0x03e3, B:88:0x03ef, B:89:0x03f7, B:90:0x03fd, B:92:0x0403, B:94:0x040d, B:96:0x0427, B:98:0x043c, B:99:0x0444, B:100:0x0463, B:102:0x046b, B:104:0x0475, B:106:0x048f, B:108:0x049f, B:110:0x04a7, B:112:0x04b5, B:114:0x04be, B:116:0x04c6, B:118:0x04ce, B:120:0x04d8, B:122:0x04e4, B:124:0x0511, B:126:0x051b, B:127:0x0529, B:128:0x0533, B:130:0x0551, B:132:0x055b, B:134:0x0572, B:136:0x057e, B:137:0x059c, B:138:0x0566, B:139:0x059f, B:141:0x05a5, B:143:0x05af, B:145:0x05cb, B:147:0x05dd, B:149:0x05e7, B:151:0x05ef, B:153:0x05f9, B:155:0x0601, B:157:0x060b, B:159:0x0613, B:161:0x061d, B:163:0x0625, B:165:0x062f, B:167:0x0658, B:168:0x0682, B:170:0x068a, B:172:0x0696, B:174:0x069e, B:176:0x06aa, B:177:0x06c0, B:179:0x06c8, B:181:0x06d4, B:183:0x06dc, B:185:0x06e8, B:186:0x06fe, B:189:0x0676, B:190:0x0702, B:191:0x0719, B:192:0x070a, B:193:0x0712, B:195:0x071d, B:197:0x0724, B:200:0x044c, B:204:0x045b, B:207:0x0351, B:209:0x035c, B:210:0x0362, B:212:0x036b, B:214:0x036f, B:216:0x0377, B:218:0x037f, B:220:0x0383, B:222:0x038b, B:225:0x0394, B:234:0x03af, B:249:0x0733, B:245:0x0754, B:262:0x01f5, B:258:0x0225), top: B:20:0x00a8 }] */
    /* JADX WARN: Removed duplicated region for block: B:237:0x0331  */
    /* JADX WARN: Removed duplicated region for block: B:292:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:303:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:317:0x07ab  */
    /* JADX WARN: Removed duplicated region for block: B:319:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0283 A[Catch: NumberFormatException -> 0x072c, IOException -> 0x072e, XmlPullParserException -> 0x0772, LOOP:0: B:43:0x0279->B:46:0x0283, LOOP_END, TRY_ENTER, TryCatch #3 {XmlPullParserException -> 0x0772, blocks: (B:42:0x0270, B:43:0x0279, B:46:0x0283, B:48:0x028f, B:50:0x02b4, B:52:0x02be, B:54:0x02cb, B:55:0x02e0, B:57:0x02e6, B:61:0x02f3, B:63:0x02f9, B:65:0x0303, B:66:0x0309, B:67:0x032b, B:68:0x0332, B:70:0x0338, B:72:0x0342, B:74:0x034e, B:75:0x03b5, B:77:0x03bb, B:79:0x03c5, B:80:0x03d0, B:82:0x03d6, B:86:0x03e3, B:88:0x03ef, B:89:0x03f7, B:90:0x03fd, B:92:0x0403, B:94:0x040d, B:96:0x0427, B:98:0x043c, B:99:0x0444, B:100:0x0463, B:102:0x046b, B:104:0x0475, B:106:0x048f, B:108:0x049f, B:110:0x04a7, B:112:0x04b5, B:114:0x04be, B:116:0x04c6, B:118:0x04ce, B:120:0x04d8, B:122:0x04e4, B:124:0x0511, B:126:0x051b, B:127:0x0529, B:128:0x0533, B:130:0x0551, B:132:0x055b, B:134:0x0572, B:136:0x057e, B:137:0x059c, B:138:0x0566, B:139:0x059f, B:141:0x05a5, B:143:0x05af, B:145:0x05cb, B:147:0x05dd, B:149:0x05e7, B:151:0x05ef, B:153:0x05f9, B:155:0x0601, B:157:0x060b, B:159:0x0613, B:161:0x061d, B:163:0x0625, B:165:0x062f, B:167:0x0658, B:168:0x0682, B:170:0x068a, B:172:0x0696, B:174:0x069e, B:176:0x06aa, B:177:0x06c0, B:179:0x06c8, B:181:0x06d4, B:183:0x06dc, B:185:0x06e8, B:186:0x06fe, B:189:0x0676, B:190:0x0702, B:191:0x0719, B:192:0x070a, B:193:0x0712, B:195:0x071d, B:197:0x0724, B:200:0x044c, B:204:0x045b, B:207:0x0351, B:209:0x035c, B:210:0x0362, B:212:0x036b, B:214:0x036f, B:216:0x0377, B:218:0x037f, B:220:0x0383, B:222:0x038b, B:225:0x0394, B:234:0x03af, B:249:0x0733, B:245:0x0754, B:262:0x01f5, B:258:0x0225), top: B:20:0x00a8 }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x028f A[EDGE_INSN: B:47:0x028f->B:48:0x028f BREAK  A[LOOP:0: B:43:0x0279->B:46:0x0283], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0338 A[Catch: NumberFormatException -> 0x072c, IOException -> 0x072e, XmlPullParserException -> 0x0772, TryCatch #3 {XmlPullParserException -> 0x0772, blocks: (B:42:0x0270, B:43:0x0279, B:46:0x0283, B:48:0x028f, B:50:0x02b4, B:52:0x02be, B:54:0x02cb, B:55:0x02e0, B:57:0x02e6, B:61:0x02f3, B:63:0x02f9, B:65:0x0303, B:66:0x0309, B:67:0x032b, B:68:0x0332, B:70:0x0338, B:72:0x0342, B:74:0x034e, B:75:0x03b5, B:77:0x03bb, B:79:0x03c5, B:80:0x03d0, B:82:0x03d6, B:86:0x03e3, B:88:0x03ef, B:89:0x03f7, B:90:0x03fd, B:92:0x0403, B:94:0x040d, B:96:0x0427, B:98:0x043c, B:99:0x0444, B:100:0x0463, B:102:0x046b, B:104:0x0475, B:106:0x048f, B:108:0x049f, B:110:0x04a7, B:112:0x04b5, B:114:0x04be, B:116:0x04c6, B:118:0x04ce, B:120:0x04d8, B:122:0x04e4, B:124:0x0511, B:126:0x051b, B:127:0x0529, B:128:0x0533, B:130:0x0551, B:132:0x055b, B:134:0x0572, B:136:0x057e, B:137:0x059c, B:138:0x0566, B:139:0x059f, B:141:0x05a5, B:143:0x05af, B:145:0x05cb, B:147:0x05dd, B:149:0x05e7, B:151:0x05ef, B:153:0x05f9, B:155:0x0601, B:157:0x060b, B:159:0x0613, B:161:0x061d, B:163:0x0625, B:165:0x062f, B:167:0x0658, B:168:0x0682, B:170:0x068a, B:172:0x0696, B:174:0x069e, B:176:0x06aa, B:177:0x06c0, B:179:0x06c8, B:181:0x06d4, B:183:0x06dc, B:185:0x06e8, B:186:0x06fe, B:189:0x0676, B:190:0x0702, B:191:0x0719, B:192:0x070a, B:193:0x0712, B:195:0x071d, B:197:0x0724, B:200:0x044c, B:204:0x045b, B:207:0x0351, B:209:0x035c, B:210:0x0362, B:212:0x036b, B:214:0x036f, B:216:0x0377, B:218:0x037f, B:220:0x0383, B:222:0x038b, B:225:0x0394, B:234:0x03af, B:249:0x0733, B:245:0x0754, B:262:0x01f5, B:258:0x0225), top: B:20:0x00a8 }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x03bb A[Catch: NumberFormatException -> 0x072c, IOException -> 0x072e, XmlPullParserException -> 0x0772, TryCatch #3 {XmlPullParserException -> 0x0772, blocks: (B:42:0x0270, B:43:0x0279, B:46:0x0283, B:48:0x028f, B:50:0x02b4, B:52:0x02be, B:54:0x02cb, B:55:0x02e0, B:57:0x02e6, B:61:0x02f3, B:63:0x02f9, B:65:0x0303, B:66:0x0309, B:67:0x032b, B:68:0x0332, B:70:0x0338, B:72:0x0342, B:74:0x034e, B:75:0x03b5, B:77:0x03bb, B:79:0x03c5, B:80:0x03d0, B:82:0x03d6, B:86:0x03e3, B:88:0x03ef, B:89:0x03f7, B:90:0x03fd, B:92:0x0403, B:94:0x040d, B:96:0x0427, B:98:0x043c, B:99:0x0444, B:100:0x0463, B:102:0x046b, B:104:0x0475, B:106:0x048f, B:108:0x049f, B:110:0x04a7, B:112:0x04b5, B:114:0x04be, B:116:0x04c6, B:118:0x04ce, B:120:0x04d8, B:122:0x04e4, B:124:0x0511, B:126:0x051b, B:127:0x0529, B:128:0x0533, B:130:0x0551, B:132:0x055b, B:134:0x0572, B:136:0x057e, B:137:0x059c, B:138:0x0566, B:139:0x059f, B:141:0x05a5, B:143:0x05af, B:145:0x05cb, B:147:0x05dd, B:149:0x05e7, B:151:0x05ef, B:153:0x05f9, B:155:0x0601, B:157:0x060b, B:159:0x0613, B:161:0x061d, B:163:0x0625, B:165:0x062f, B:167:0x0658, B:168:0x0682, B:170:0x068a, B:172:0x0696, B:174:0x069e, B:176:0x06aa, B:177:0x06c0, B:179:0x06c8, B:181:0x06d4, B:183:0x06dc, B:185:0x06e8, B:186:0x06fe, B:189:0x0676, B:190:0x0702, B:191:0x0719, B:192:0x070a, B:193:0x0712, B:195:0x071d, B:197:0x0724, B:200:0x044c, B:204:0x045b, B:207:0x0351, B:209:0x035c, B:210:0x0362, B:212:0x036b, B:214:0x036f, B:216:0x0377, B:218:0x037f, B:220:0x0383, B:222:0x038b, B:225:0x0394, B:234:0x03af, B:249:0x0733, B:245:0x0754, B:262:0x01f5, B:258:0x0225), top: B:20:0x00a8 }] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x03d6 A[Catch: NumberFormatException -> 0x072c, IOException -> 0x072e, XmlPullParserException -> 0x0772, TryCatch #3 {XmlPullParserException -> 0x0772, blocks: (B:42:0x0270, B:43:0x0279, B:46:0x0283, B:48:0x028f, B:50:0x02b4, B:52:0x02be, B:54:0x02cb, B:55:0x02e0, B:57:0x02e6, B:61:0x02f3, B:63:0x02f9, B:65:0x0303, B:66:0x0309, B:67:0x032b, B:68:0x0332, B:70:0x0338, B:72:0x0342, B:74:0x034e, B:75:0x03b5, B:77:0x03bb, B:79:0x03c5, B:80:0x03d0, B:82:0x03d6, B:86:0x03e3, B:88:0x03ef, B:89:0x03f7, B:90:0x03fd, B:92:0x0403, B:94:0x040d, B:96:0x0427, B:98:0x043c, B:99:0x0444, B:100:0x0463, B:102:0x046b, B:104:0x0475, B:106:0x048f, B:108:0x049f, B:110:0x04a7, B:112:0x04b5, B:114:0x04be, B:116:0x04c6, B:118:0x04ce, B:120:0x04d8, B:122:0x04e4, B:124:0x0511, B:126:0x051b, B:127:0x0529, B:128:0x0533, B:130:0x0551, B:132:0x055b, B:134:0x0572, B:136:0x057e, B:137:0x059c, B:138:0x0566, B:139:0x059f, B:141:0x05a5, B:143:0x05af, B:145:0x05cb, B:147:0x05dd, B:149:0x05e7, B:151:0x05ef, B:153:0x05f9, B:155:0x0601, B:157:0x060b, B:159:0x0613, B:161:0x061d, B:163:0x0625, B:165:0x062f, B:167:0x0658, B:168:0x0682, B:170:0x068a, B:172:0x0696, B:174:0x069e, B:176:0x06aa, B:177:0x06c0, B:179:0x06c8, B:181:0x06d4, B:183:0x06dc, B:185:0x06e8, B:186:0x06fe, B:189:0x0676, B:190:0x0702, B:191:0x0719, B:192:0x070a, B:193:0x0712, B:195:0x071d, B:197:0x0724, B:200:0x044c, B:204:0x045b, B:207:0x0351, B:209:0x035c, B:210:0x0362, B:212:0x036b, B:214:0x036f, B:216:0x0377, B:218:0x037f, B:220:0x0383, B:222:0x038b, B:225:0x0394, B:234:0x03af, B:249:0x0733, B:245:0x0754, B:262:0x01f5, B:258:0x0225), top: B:20:0x00a8 }] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x03e3 A[Catch: NumberFormatException -> 0x072c, IOException -> 0x072e, XmlPullParserException -> 0x0772, TryCatch #3 {XmlPullParserException -> 0x0772, blocks: (B:42:0x0270, B:43:0x0279, B:46:0x0283, B:48:0x028f, B:50:0x02b4, B:52:0x02be, B:54:0x02cb, B:55:0x02e0, B:57:0x02e6, B:61:0x02f3, B:63:0x02f9, B:65:0x0303, B:66:0x0309, B:67:0x032b, B:68:0x0332, B:70:0x0338, B:72:0x0342, B:74:0x034e, B:75:0x03b5, B:77:0x03bb, B:79:0x03c5, B:80:0x03d0, B:82:0x03d6, B:86:0x03e3, B:88:0x03ef, B:89:0x03f7, B:90:0x03fd, B:92:0x0403, B:94:0x040d, B:96:0x0427, B:98:0x043c, B:99:0x0444, B:100:0x0463, B:102:0x046b, B:104:0x0475, B:106:0x048f, B:108:0x049f, B:110:0x04a7, B:112:0x04b5, B:114:0x04be, B:116:0x04c6, B:118:0x04ce, B:120:0x04d8, B:122:0x04e4, B:124:0x0511, B:126:0x051b, B:127:0x0529, B:128:0x0533, B:130:0x0551, B:132:0x055b, B:134:0x0572, B:136:0x057e, B:137:0x059c, B:138:0x0566, B:139:0x059f, B:141:0x05a5, B:143:0x05af, B:145:0x05cb, B:147:0x05dd, B:149:0x05e7, B:151:0x05ef, B:153:0x05f9, B:155:0x0601, B:157:0x060b, B:159:0x0613, B:161:0x061d, B:163:0x0625, B:165:0x062f, B:167:0x0658, B:168:0x0682, B:170:0x068a, B:172:0x0696, B:174:0x069e, B:176:0x06aa, B:177:0x06c0, B:179:0x06c8, B:181:0x06d4, B:183:0x06dc, B:185:0x06e8, B:186:0x06fe, B:189:0x0676, B:190:0x0702, B:191:0x0719, B:192:0x070a, B:193:0x0712, B:195:0x071d, B:197:0x0724, B:200:0x044c, B:204:0x045b, B:207:0x0351, B:209:0x035c, B:210:0x0362, B:212:0x036b, B:214:0x036f, B:216:0x0377, B:218:0x037f, B:220:0x0383, B:222:0x038b, B:225:0x0394, B:234:0x03af, B:249:0x0733, B:245:0x0754, B:262:0x01f5, B:258:0x0225), top: B:20:0x00a8 }] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x03f7 A[Catch: NumberFormatException -> 0x072c, IOException -> 0x072e, XmlPullParserException -> 0x0772, TryCatch #3 {XmlPullParserException -> 0x0772, blocks: (B:42:0x0270, B:43:0x0279, B:46:0x0283, B:48:0x028f, B:50:0x02b4, B:52:0x02be, B:54:0x02cb, B:55:0x02e0, B:57:0x02e6, B:61:0x02f3, B:63:0x02f9, B:65:0x0303, B:66:0x0309, B:67:0x032b, B:68:0x0332, B:70:0x0338, B:72:0x0342, B:74:0x034e, B:75:0x03b5, B:77:0x03bb, B:79:0x03c5, B:80:0x03d0, B:82:0x03d6, B:86:0x03e3, B:88:0x03ef, B:89:0x03f7, B:90:0x03fd, B:92:0x0403, B:94:0x040d, B:96:0x0427, B:98:0x043c, B:99:0x0444, B:100:0x0463, B:102:0x046b, B:104:0x0475, B:106:0x048f, B:108:0x049f, B:110:0x04a7, B:112:0x04b5, B:114:0x04be, B:116:0x04c6, B:118:0x04ce, B:120:0x04d8, B:122:0x04e4, B:124:0x0511, B:126:0x051b, B:127:0x0529, B:128:0x0533, B:130:0x0551, B:132:0x055b, B:134:0x0572, B:136:0x057e, B:137:0x059c, B:138:0x0566, B:139:0x059f, B:141:0x05a5, B:143:0x05af, B:145:0x05cb, B:147:0x05dd, B:149:0x05e7, B:151:0x05ef, B:153:0x05f9, B:155:0x0601, B:157:0x060b, B:159:0x0613, B:161:0x061d, B:163:0x0625, B:165:0x062f, B:167:0x0658, B:168:0x0682, B:170:0x068a, B:172:0x0696, B:174:0x069e, B:176:0x06aa, B:177:0x06c0, B:179:0x06c8, B:181:0x06d4, B:183:0x06dc, B:185:0x06e8, B:186:0x06fe, B:189:0x0676, B:190:0x0702, B:191:0x0719, B:192:0x070a, B:193:0x0712, B:195:0x071d, B:197:0x0724, B:200:0x044c, B:204:0x045b, B:207:0x0351, B:209:0x035c, B:210:0x0362, B:212:0x036b, B:214:0x036f, B:216:0x0377, B:218:0x037f, B:220:0x0383, B:222:0x038b, B:225:0x0394, B:234:0x03af, B:249:0x0733, B:245:0x0754, B:262:0x01f5, B:258:0x0225), top: B:20:0x00a8 }] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0403 A[Catch: NumberFormatException -> 0x072c, IOException -> 0x072e, XmlPullParserException -> 0x0772, TryCatch #3 {XmlPullParserException -> 0x0772, blocks: (B:42:0x0270, B:43:0x0279, B:46:0x0283, B:48:0x028f, B:50:0x02b4, B:52:0x02be, B:54:0x02cb, B:55:0x02e0, B:57:0x02e6, B:61:0x02f3, B:63:0x02f9, B:65:0x0303, B:66:0x0309, B:67:0x032b, B:68:0x0332, B:70:0x0338, B:72:0x0342, B:74:0x034e, B:75:0x03b5, B:77:0x03bb, B:79:0x03c5, B:80:0x03d0, B:82:0x03d6, B:86:0x03e3, B:88:0x03ef, B:89:0x03f7, B:90:0x03fd, B:92:0x0403, B:94:0x040d, B:96:0x0427, B:98:0x043c, B:99:0x0444, B:100:0x0463, B:102:0x046b, B:104:0x0475, B:106:0x048f, B:108:0x049f, B:110:0x04a7, B:112:0x04b5, B:114:0x04be, B:116:0x04c6, B:118:0x04ce, B:120:0x04d8, B:122:0x04e4, B:124:0x0511, B:126:0x051b, B:127:0x0529, B:128:0x0533, B:130:0x0551, B:132:0x055b, B:134:0x0572, B:136:0x057e, B:137:0x059c, B:138:0x0566, B:139:0x059f, B:141:0x05a5, B:143:0x05af, B:145:0x05cb, B:147:0x05dd, B:149:0x05e7, B:151:0x05ef, B:153:0x05f9, B:155:0x0601, B:157:0x060b, B:159:0x0613, B:161:0x061d, B:163:0x0625, B:165:0x062f, B:167:0x0658, B:168:0x0682, B:170:0x068a, B:172:0x0696, B:174:0x069e, B:176:0x06aa, B:177:0x06c0, B:179:0x06c8, B:181:0x06d4, B:183:0x06dc, B:185:0x06e8, B:186:0x06fe, B:189:0x0676, B:190:0x0702, B:191:0x0719, B:192:0x070a, B:193:0x0712, B:195:0x071d, B:197:0x0724, B:200:0x044c, B:204:0x045b, B:207:0x0351, B:209:0x035c, B:210:0x0362, B:212:0x036b, B:214:0x036f, B:216:0x0377, B:218:0x037f, B:220:0x0383, B:222:0x038b, B:225:0x0394, B:234:0x03af, B:249:0x0733, B:245:0x0754, B:262:0x01f5, B:258:0x0225), top: B:20:0x00a8 }] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x003c A[Catch: SecurityException -> 0x0050, IOException -> 0x0053, all -> 0x07a6, TRY_LEAVE, TryCatch #2 {all -> 0x07a6, blocks: (B:305:0x001f, B:7:0x0032, B:9:0x003c, B:301:0x005e, B:295:0x0079), top: B:2:0x0018 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int verifyToken(ASKSSession aSKSSession, String str, String str2, boolean z, int i, int i2) {
        Throwable th;
        SecurityException securityException;
        boolean z2;
        StrictJarFile strictJarFile;
        IOException iOException;
        String str3;
        byte[] bArr;
        XmlPullParserException xmlPullParserException;
        String str4;
        NumberFormatException numberFormatException;
        IOException iOException2;
        String str5;
        ArrayList arrayList;
        Object obj;
        String str6;
        Object obj2;
        Object obj3;
        String str7;
        Object obj4;
        ASKSManagerService aSKSManagerService;
        Object obj5;
        Object obj6;
        HashMap hashMap;
        Iterator it;
        Object obj7;
        int i3;
        boolean z3;
        String str8;
        ASKSSession aSKSSession2;
        Object obj8;
        String str9;
        Object obj9;
        Object obj10;
        Restrict restrict;
        String str10;
        ZipEntry findEntry;
        Slog.i("AASA_ASKSManager", " AASA_VerifyToken START ");
        String codePath = aSKSSession.getCodePath();
        String str11 = null;
        try {
            try {
                strictJarFile = new StrictJarFile(codePath, false, true);
            } catch (Throwable th2) {
                th = th2;
                str11 = str3;
                if (str11 == null) {
                    str11.close();
                    throw th;
                }
                throw th;
            }
        } catch (IOException e) {
            iOException = e;
            z2 = false;
            strictJarFile = null;
        } catch (SecurityException e2) {
            securityException = e2;
            z2 = false;
            strictJarFile = null;
        } catch (Throwable th3) {
            th = th3;
            if (str11 == null) {
            }
        }
        if (!z) {
            try {
            } catch (IOException e3) {
                iOException = e3;
                z2 = false;
                Slog.i("AASA_ASKSManager", " ERROR: AASA_VerifyToken " + iOException);
            } catch (SecurityException e4) {
                securityException = e4;
                z2 = false;
                Slog.i("AASA_ASKSManager", " ERROR: AASA_VerifyToken " + securityException);
                if (strictJarFile != null) {
                    strictJarFile.close();
                }
                bArr = null;
                if (bArr != null) {
                }
            }
            if (codePath.startsWith("/data/")) {
                z2 = true;
                findEntry = strictJarFile.findEntry(aSKSSession.getTokenName());
                if (findEntry == null) {
                    byte[] bArr2 = new byte[(int) findEntry.getSize()];
                    loadCertificates(strictJarFile, findEntry, bArr2);
                    bArr = checkIntegrityNew(aSKSSession, i, bArr2);
                } else {
                    bArr = null;
                }
                strictJarFile.close();
                if (bArr != null) {
                    Slog.i("AASA_ASKSManager", " ERROR: plz check certification in the device - Fail to check integrity");
                    return 22;
                }
                if (bArr.length == 1 || bArr.length == 2) {
                    Slog.i("AASA_ASKSManager", " ERROR: plz check certification in the device - Fail to check integrity");
                    try {
                        return Integer.parseInt(new String(bArr));
                    } catch (NumberFormatException unused) {
                        return 0;
                    }
                }
                try {
                    try {
                        XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
                        newPullParser.setInput(new ByteArrayInputStream(bArr), null);
                        try {
                            String parseXMLNew = parseXMLNew(newPullParser, "MODE");
                            if (!isInteger(parseXMLNew)) {
                                return 21;
                            }
                            int parseInt = Integer.parseInt(parseXMLNew);
                            ArrayList arrayList2 = new ArrayList();
                            str4 = "CHECK";
                            byte[] bArr3 = bArr;
                            boolean z4 = z2;
                            try {
                                try {
                                    if (parseInt != 0) {
                                        str5 = "CHECK";
                                        try {
                                            if (parseInt == 1) {
                                                arrayList = arrayList2;
                                                if (i == 1) {
                                                    arrayList.addAll(Arrays.asList("PACKAGE", "DIGEST", "CREATE", "MODELS", "CARRIERS", "EXPIRED", "RESTRICT", "DATELIMIT", "CROSSDOWN", "RUFS", "BEFORE", "AFTER", str5, "EMMODE", "ADPMODELS", "ADPCARRIERS", "ASKSRNEWMODELS", "ASKSRNEWCARRIERS"));
                                                }
                                            } else {
                                                if (parseInt != 3) {
                                                    return 21;
                                                }
                                                if (i == 1) {
                                                    arrayList = arrayList2;
                                                    arrayList.addAll(Arrays.asList("PACKAGE", "DIGEST", "CREATE", "MODELS", "CARRIERS", "EXPIRED", "RESTRICT", "DATELIMIT", "CROSSDOWN", "RUFS", "BEFORE", "AFTER", str5, "EMMODE", "ADPMODELS", "ADPCARRIERS", "ASKSRNEWMODELS", "ASKSRNEWCARRIERS"));
                                                } else {
                                                    arrayList = arrayList2;
                                                }
                                            }
                                        } catch (IOException e5) {
                                            iOException2 = e5;
                                            str4 = "AASA_ASKSManager";
                                            Slog.i(str4, " " + iOException2.toString());
                                            iOException2.printStackTrace();
                                            return 21;
                                        } catch (NumberFormatException e6) {
                                            numberFormatException = e6;
                                            str4 = "AASA_ASKSManager";
                                            Slog.i(str4, " " + numberFormatException.toString());
                                            numberFormatException.printStackTrace();
                                            return 21;
                                        } catch (XmlPullParserException e7) {
                                            xmlPullParserException = e7;
                                            str11 = "AASA_ASKSManager";
                                            Slog.i(str11, " " + xmlPullParserException.toString());
                                            return 21;
                                        }
                                    } else {
                                        str5 = "CHECK";
                                        arrayList = arrayList2;
                                        if (i == 1) {
                                            if (z4) {
                                                try {
                                                    Slog.e("AASA_ASKSManager", "Violate security policy of MSTG. Package(" + str + ") is blocked. ");
                                                    Slog.i("AASA_ASKSManager", "installer::" + str2);
                                                    writeBlockApkList(str);
                                                    return 15;
                                                } catch (IOException e8) {
                                                    e = e8;
                                                    str4 = "AASA_ASKSManager";
                                                    iOException2 = e;
                                                    Slog.i(str4, " " + iOException2.toString());
                                                    iOException2.printStackTrace();
                                                    return 21;
                                                } catch (NumberFormatException e9) {
                                                    e = e9;
                                                    str4 = "AASA_ASKSManager";
                                                    numberFormatException = e;
                                                    Slog.i(str4, " " + numberFormatException.toString());
                                                    numberFormatException.printStackTrace();
                                                    return 21;
                                                } catch (XmlPullParserException e10) {
                                                    e = e10;
                                                    str11 = "AASA_ASKSManager";
                                                    xmlPullParserException = e;
                                                    Slog.i(str11, " " + xmlPullParserException.toString());
                                                    return 21;
                                                }
                                            }
                                            obj = "BEFORE";
                                            str6 = "AASA_ASKSManager";
                                            obj2 = "RESTRICT";
                                            obj3 = "AFTER";
                                            str7 = str;
                                            obj4 = "CARRIERS";
                                            aSKSManagerService = this;
                                            obj5 = "RUFS";
                                            StringBuilder sb = new StringBuilder();
                                            obj6 = "DATELIMIT";
                                            sb.append(" Token 0:");
                                            sb.append(str7);
                                            Slog.i(str6, sb.toString());
                                            arrayList.addAll(Arrays.asList("PACKAGE", "CREATE", "DIGEST", "CROSSDOWN"));
                                            hashMap = new HashMap();
                                            it = arrayList.iterator();
                                            while (true) {
                                                obj7 = obj2;
                                                if (it.hasNext()) {
                                                    break;
                                                }
                                                hashMap.put((String) it.next(), "NONE");
                                                obj2 = obj7;
                                            }
                                            XmlPullParser newPullParser2 = XmlPullParserFactory.newInstance().newPullParser();
                                            newPullParser2.setInput(new ByteArrayInputStream(bArr3), null);
                                            aSKSManagerService.parseXMLNew(newPullParser2, hashMap);
                                            String trustedToday = getTrustedToday();
                                            i3 = -1;
                                            if (hashMap.containsKey("PACKAGE") && !"NONE".equals(hashMap.get("PACKAGE")) && ((String) hashMap.get("PACKAGE")).equals(str7)) {
                                                Slog.i(str6, "OK:" + str7);
                                                i3 = 0;
                                            }
                                            if (hashMap.containsKey("DIGEST") || "NONE".equals(hashMap.get("DIGEST"))) {
                                                if (i == 1) {
                                                    i3--;
                                                }
                                                if (!hashMap.containsKey("CROSSDOWN") && !"NONE".equals(hashMap.get("CROSSDOWN"))) {
                                                    int parseInt2 = Integer.parseInt((String) hashMap.get("CROSSDOWN"));
                                                    if (parseInt2 >= 0) {
                                                        String str12 = SystemProperties.get("ro.build.2ndbrand", "false");
                                                        if (parseInt2 == 0) {
                                                            Slog.i(str6, "OK:CROSSDOWN");
                                                        } else {
                                                            if ("true".equals(str12) && (parseInt2 & 2) == 0) {
                                                                Slog.i(str6, "FAILED CROSSDOWN for sep lite");
                                                                return 146;
                                                            }
                                                            if ("false".equals(str12) && (parseInt2 & 1) == 0) {
                                                                Slog.i(str6, "FAILED CROSSDOWN for galaxy");
                                                                return FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__RESOLVER_SWITCH_TABS;
                                                            }
                                                            Slog.i(str6, "OK:CROSSDOWN");
                                                        }
                                                        if (hashMap.containsKey("MODELS")) {
                                                        }
                                                        z3 = false;
                                                        str8 = null;
                                                        if (hashMap.containsKey(obj4)) {
                                                            if (!z3) {
                                                            }
                                                        }
                                                        if (hashMap.containsKey("CREATE")) {
                                                        }
                                                        aSKSSession2 = aSKSSession;
                                                        obj8 = "EMMODE";
                                                        if (i == 1) {
                                                        }
                                                        str9 = null;
                                                        if (hashMap.containsKey("EXPIRED")) {
                                                            Slog.d(str6, "EXPIRED : " + ((String) hashMap.get("EXPIRED")));
                                                            str10 = (String) hashMap.get("EXPIRED");
                                                            if (Integer.parseInt(str9) <= Integer.parseInt(str10)) {
                                                            }
                                                        }
                                                        if (hashMap.containsKey(obj7)) {
                                                            if (!"DELETE".equals(hashMap.get(obj7))) {
                                                            }
                                                        }
                                                        if (hashMap.containsKey(obj8)) {
                                                            Slog.d("AASA_ASKSManager_EM", "EMMODE : " + ((String) hashMap.get(obj8)));
                                                            aSKSSession2.setEMMode(Integer.decode((String) hashMap.get(obj8)).intValue());
                                                        }
                                                        obj9 = obj5;
                                                        if (hashMap.containsKey(obj9)) {
                                                            obj10 = obj;
                                                            if (!hashMap.containsKey(obj10)) {
                                                            }
                                                            Slog.e("AASA_ASKSManager_RUFS", "ERROR: BEFORE in RUFS");
                                                            i3--;
                                                        }
                                                        if (i3 == 0) {
                                                        }
                                                    }
                                                    i3--;
                                                    if (hashMap.containsKey("MODELS")) {
                                                    }
                                                    z3 = false;
                                                    str8 = null;
                                                    if (hashMap.containsKey(obj4)) {
                                                    }
                                                    if (hashMap.containsKey("CREATE")) {
                                                    }
                                                    aSKSSession2 = aSKSSession;
                                                    obj8 = "EMMODE";
                                                    if (i == 1) {
                                                    }
                                                    str9 = null;
                                                    if (hashMap.containsKey("EXPIRED")) {
                                                    }
                                                    if (hashMap.containsKey(obj7)) {
                                                    }
                                                    if (hashMap.containsKey(obj8)) {
                                                    }
                                                    obj9 = obj5;
                                                    if (hashMap.containsKey(obj9)) {
                                                    }
                                                    if (i3 == 0) {
                                                    }
                                                } else {
                                                    if (i == 1 && "true".equals(SystemProperties.get("ro.build.2ndbrand")) && i2 != 1 && i2 != 2 && i2 != 4) {
                                                        Slog.e(str6, "Error : No value CROSSDOWN in 2ndbrand.");
                                                        i3--;
                                                    }
                                                    if (hashMap.containsKey("MODELS") || "NONE".equals(hashMap.get("MODELS"))) {
                                                        z3 = false;
                                                        str8 = null;
                                                    } else {
                                                        str8 = (String) hashMap.get("MODELS");
                                                        z3 = true;
                                                    }
                                                    if (hashMap.containsKey(obj4) && !"NONE".equals(hashMap.get(obj4))) {
                                                        if (!z3) {
                                                            if (!aSKSManagerService.checkTokenTarget(str8, (String) hashMap.get(obj4))) {
                                                                Slog.e(str6, "Error : CARRIERS");
                                                                i3--;
                                                            }
                                                        }
                                                    }
                                                    if (hashMap.containsKey("CREATE") || "NONE".equals(hashMap.get("CREATE"))) {
                                                        aSKSSession2 = aSKSSession;
                                                        obj8 = "EMMODE";
                                                        if (i == 1) {
                                                            Slog.e(str6, "Error : CREATE in asks case.");
                                                            i3--;
                                                        }
                                                        str9 = null;
                                                    } else {
                                                        Slog.d(str6, "CREATE : " + ((String) hashMap.get("CREATE")));
                                                        try {
                                                            str9 = (String) hashMap.get("CREATE");
                                                            if (Integer.valueOf(trustedToday).intValue() < Integer.parseInt(str9)) {
                                                                Slog.d(str6, "createDate is bigger than today.");
                                                                aSKSManagerService.setTrustTimeByToken(str9);
                                                            }
                                                            aSKSSession2 = aSKSSession;
                                                            obj8 = "EMMODE";
                                                            aSKSSession2.setVersion(str9);
                                                        } catch (NumberFormatException unused2) {
                                                            Slog.e(str6, "Error : CREATE-NumberFormatException");
                                                            return 21;
                                                        }
                                                    }
                                                    if (hashMap.containsKey("EXPIRED") && !"NONE".equals(hashMap.get("EXPIRED"))) {
                                                        Slog.d(str6, "EXPIRED : " + ((String) hashMap.get("EXPIRED")));
                                                        try {
                                                            str10 = (String) hashMap.get("EXPIRED");
                                                            if (Integer.parseInt(str9) <= Integer.parseInt(str10)) {
                                                                Slog.d(str6, "createDate is bigger than expiredDate.");
                                                                return 14;
                                                            }
                                                            if (Integer.valueOf(trustedToday).intValue() > Integer.parseInt(str10)) {
                                                                Slog.d(str6, "today Date is bigger than expiredDate.");
                                                                return 14;
                                                            }
                                                        } catch (NumberFormatException unused3) {
                                                            Slog.d(str6, "EXPIRED : NumberFormatException");
                                                            return 21;
                                                        }
                                                    }
                                                    if (hashMap.containsKey(obj7) && !"NONE".equals(hashMap.get(obj7))) {
                                                        if (!"DELETE".equals(hashMap.get(obj7))) {
                                                            Slog.d("AASA_ASKSManager_DELETABLE", "type : " + ((String) hashMap.get(obj7)));
                                                            Deletable deletable = new Deletable();
                                                            deletable.setVersion(str9);
                                                            Object obj11 = obj6;
                                                            if (hashMap.containsKey(obj11) && !"NONE".equals(hashMap.get(obj11))) {
                                                                deletable.setDateLimit((String) hashMap.get(obj11));
                                                                aSKSSession2.setDeletable(deletable);
                                                            } else {
                                                                Slog.e("AASA_ASKSManager_DELETABLE", "FAIL: DATELIMIT in deletable");
                                                                i3--;
                                                            }
                                                        } else {
                                                            Object obj12 = obj6;
                                                            Restrict restrict2 = new Restrict();
                                                            restrict2.setType((String) hashMap.get(obj7));
                                                            restrict2.setVersion(str9);
                                                            restrict2.setFrom("Token");
                                                            if (hashMap.containsKey(obj12) && !"NONE".equals(hashMap.get(obj12))) {
                                                                restrict2.setDateLimit((String) hashMap.get(obj12));
                                                                restrict = restrict2;
                                                            } else {
                                                                Slog.e("AASA_ASKSManager_RESTRICTED", "FAIL: DATELIMIT in restricted.");
                                                                i3--;
                                                                restrict = null;
                                                            }
                                                            if (restrict != null && "REVOKE".equals(restrict.getType())) {
                                                                ArrayList arrayList3 = new ArrayList();
                                                                XmlPullParser newPullParser3 = XmlPullParserFactory.newInstance().newPullParser();
                                                                newPullParser3.setInput(new ByteArrayInputStream(bArr3), null);
                                                                aSKSManagerService.readRestrictPermissions(newPullParser3, arrayList3);
                                                                restrict.setPermissionList(arrayList3);
                                                            }
                                                            aSKSSession2.setRestrict(restrict);
                                                        }
                                                    }
                                                    if (hashMap.containsKey(obj8) && !"NONE".equals(hashMap.get(obj8))) {
                                                        Slog.d("AASA_ASKSManager_EM", "EMMODE : " + ((String) hashMap.get(obj8)));
                                                        try {
                                                            aSKSSession2.setEMMode(Integer.decode((String) hashMap.get(obj8)).intValue());
                                                        } catch (NumberFormatException unused4) {
                                                            Slog.d("AASA_ASKSManager_EM", "EMMODE : NumberFormatException");
                                                            return 21;
                                                        }
                                                    }
                                                    obj9 = obj5;
                                                    if (hashMap.containsKey(obj9) && !"NONE".equals(hashMap.get(obj9))) {
                                                        obj10 = obj;
                                                        if (!hashMap.containsKey(obj10) && !"NONE".equals(hashMap.get(obj10))) {
                                                            Object obj13 = obj3;
                                                            if (hashMap.containsKey(obj13) && !"NONE".equals(hashMap.get(obj13))) {
                                                                String str13 = str5;
                                                                if (hashMap.containsKey(str13) && !"NONE".equals(hashMap.get(str13))) {
                                                                    String str14 = (String) hashMap.get(obj9);
                                                                    String str15 = (String) hashMap.get(obj10);
                                                                    String str16 = (String) hashMap.get(obj13);
                                                                    String str17 = (String) hashMap.get(str13);
                                                                    RUFSContainer rUFSContainer = new RUFSContainer();
                                                                    rUFSContainer.setPolicyVersion(str14);
                                                                    rUFSContainer.setDeltaPolicyVersion("0");
                                                                    rUFSContainer.setIsDelta(false);
                                                                    try {
                                                                        rUFSContainer.setSizeofZip(Long.parseLong(str15));
                                                                        rUFSContainer.setSizeofFiles(Long.parseLong(str16));
                                                                        rUFSContainer.setDigest(str17);
                                                                        rUFSContainer.setHasRUFSToken(true);
                                                                        Slog.i("AASA_ASKSManager_RUFS", " SET RUFS TOKEN True!");
                                                                    } catch (NumberFormatException unused5) {
                                                                        rUFSContainer.setHasRUFSToken(false);
                                                                        Slog.i("AASA_ASKSManager_RUFS", " SET RUFS TOKEN False!");
                                                                        i3--;
                                                                    }
                                                                    if (hashMap.containsKey("ADPMODELS") && !"NONE".equals(hashMap.get("ADPMODELS")) && hashMap.containsKey("ADPCARRIERS") && !"NONE".equals(hashMap.get("ADPCARRIERS"))) {
                                                                        String str18 = (String) hashMap.get("ADPMODELS");
                                                                        String str19 = (String) hashMap.get("ADPCARRIERS");
                                                                        rUFSContainer.setADPModels(str18);
                                                                        rUFSContainer.setADPCarriers(str19);
                                                                    }
                                                                    if (hashMap.containsKey("ASKSRNEWMODELS") && !"NONE".equals(hashMap.get("ASKSRNEWMODELS")) && hashMap.containsKey("ASKSRNEWCARRIERS") && !"NONE".equals(hashMap.get("ASKSRNEWCARRIERS"))) {
                                                                        String str20 = (String) hashMap.get("ASKSRNEWMODELS");
                                                                        String str21 = (String) hashMap.get("ASKSRNEWCARRIERS");
                                                                        rUFSContainer.setASKSRNEWModels(str20);
                                                                        rUFSContainer.setASKSRNEWCarriers(str21);
                                                                    }
                                                                    aSKSSession2.setRufsContainer(rUFSContainer);
                                                                } else {
                                                                    Slog.e("AASA_ASKSManager_RUFS", "ERROR: CHECK in RUFS");
                                                                }
                                                            } else {
                                                                Slog.e("AASA_ASKSManager_RUFS", "ERROR: AFTER in RUFS");
                                                            }
                                                        } else {
                                                            Slog.e("AASA_ASKSManager_RUFS", "ERROR: BEFORE in RUFS");
                                                        }
                                                        i3--;
                                                    }
                                                    if (i3 == 0) {
                                                        Slog.i(str6, " Pass ALL");
                                                        return 0;
                                                    }
                                                    Slog.i(str6, " Fail: auth");
                                                    return 221;
                                                }
                                            } else {
                                                if (i == 1) {
                                                    String pkgDigest = aSKSSession.getPkgDigest();
                                                    if (pkgDigest != null && pkgDigest.equals(hashMap.get("DIGEST"))) {
                                                        Slog.i(str6, "OK:HASH");
                                                    } else {
                                                        Slog.i(str6, "NOT OK:HASH : " + pkgDigest + " comp : " + ((String) hashMap.get("DIGEST")));
                                                        i3--;
                                                    }
                                                }
                                                if (!hashMap.containsKey("CROSSDOWN")) {
                                                }
                                                if (i == 1) {
                                                    Slog.e(str6, "Error : No value CROSSDOWN in 2ndbrand.");
                                                    i3--;
                                                }
                                                if (hashMap.containsKey("MODELS")) {
                                                }
                                                z3 = false;
                                                str8 = null;
                                                if (hashMap.containsKey(obj4)) {
                                                }
                                                if (hashMap.containsKey("CREATE")) {
                                                }
                                                aSKSSession2 = aSKSSession;
                                                obj8 = "EMMODE";
                                                if (i == 1) {
                                                }
                                                str9 = null;
                                                if (hashMap.containsKey("EXPIRED")) {
                                                }
                                                if (hashMap.containsKey(obj7)) {
                                                }
                                                if (hashMap.containsKey(obj8)) {
                                                }
                                                obj9 = obj5;
                                                if (hashMap.containsKey(obj9)) {
                                                }
                                                if (i3 == 0) {
                                                }
                                            }
                                        }
                                    }
                                    obj6 = "DATELIMIT";
                                    obj = "BEFORE";
                                    obj5 = "RUFS";
                                    str6 = "AASA_ASKSManager";
                                    obj2 = "RESTRICT";
                                    obj3 = "AFTER";
                                    str7 = str;
                                    obj4 = "CARRIERS";
                                    aSKSManagerService = this;
                                    hashMap = new HashMap();
                                    it = arrayList.iterator();
                                    while (true) {
                                        obj7 = obj2;
                                        if (it.hasNext()) {
                                        }
                                        hashMap.put((String) it.next(), "NONE");
                                        obj2 = obj7;
                                    }
                                    XmlPullParser newPullParser22 = XmlPullParserFactory.newInstance().newPullParser();
                                    newPullParser22.setInput(new ByteArrayInputStream(bArr3), null);
                                    aSKSManagerService.parseXMLNew(newPullParser22, hashMap);
                                    String trustedToday2 = getTrustedToday();
                                    i3 = -1;
                                    if (hashMap.containsKey("PACKAGE")) {
                                        Slog.i(str6, "OK:" + str7);
                                        i3 = 0;
                                    }
                                    if (hashMap.containsKey("DIGEST")) {
                                    }
                                    if (i == 1) {
                                    }
                                    if (!hashMap.containsKey("CROSSDOWN")) {
                                    }
                                    if (i == 1) {
                                    }
                                    if (hashMap.containsKey("MODELS")) {
                                    }
                                    z3 = false;
                                    str8 = null;
                                    if (hashMap.containsKey(obj4)) {
                                    }
                                    if (hashMap.containsKey("CREATE")) {
                                    }
                                    aSKSSession2 = aSKSSession;
                                    obj8 = "EMMODE";
                                    if (i == 1) {
                                    }
                                    str9 = null;
                                    if (hashMap.containsKey("EXPIRED")) {
                                    }
                                    if (hashMap.containsKey(obj7)) {
                                    }
                                    if (hashMap.containsKey(obj8)) {
                                    }
                                    obj9 = obj5;
                                    if (hashMap.containsKey(obj9)) {
                                    }
                                    if (i3 == 0) {
                                    }
                                } catch (IOException e11) {
                                    e = e11;
                                }
                            } catch (NumberFormatException e12) {
                                e = e12;
                            }
                        } catch (IOException e13) {
                            e = e13;
                            str4 = "AASA_ASKSManager";
                        } catch (NumberFormatException e14) {
                            e = e14;
                            str4 = "AASA_ASKSManager";
                        }
                    } catch (XmlPullParserException e15) {
                        e = e15;
                    }
                } catch (XmlPullParserException e16) {
                    e = e16;
                    str11 = "AASA_ASKSManager";
                }
            }
        }
        z2 = false;
        findEntry = strictJarFile.findEntry(aSKSSession.getTokenName());
        if (findEntry == null) {
        }
        strictJarFile.close();
        if (bArr != null) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00c8, code lost:
    
        if (r19 == 2) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00ca, code lost:
    
        if (r13 == null) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00cf, code lost:
    
        if (r5 == null) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00d1, code lost:
    
        r5.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00d4, code lost:
    
        android.util.Slog.e("AASA_ASKSManager", "this is not on the way to check integrity");
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00de, code lost:
    
        return "22".getBytes();
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00cc, code lost:
    
        r13.close();
     */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0133 A[Catch: IOException -> 0x0136, TRY_LEAVE, TryCatch #0 {IOException -> 0x0136, blocks: (B:78:0x012e, B:73:0x0133), top: B:77:0x012e }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x012e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final byte[] checkIntegrityNew(ASKSSession aSKSSession, int i, byte[] bArr) {
        InputStream inputStream;
        StrictJarFile strictJarFile;
        StrictJarFile strictJarFile2;
        if (bArr.length < 512) {
            return "22".getBytes();
        }
        StrictJarFile strictJarFile3 = null;
        try {
            try {
                byte[] bArr2 = new byte[512];
                System.arraycopy(bArr, 0, bArr2, 0, 512);
                byte[] tokenContents = getTokenContents(bArr, 512);
                if (tokenContents != null && tokenContents.length != 1) {
                    java.security.Signature signature = java.security.Signature.getInstance("SHA256WithRSAEncryption");
                    CertificateFactory certificateFactory = CertificateFactory.getInstance("x.509");
                    if (i == 1) {
                        strictJarFile2 = new StrictJarFile(aSKSSession.getCodePath(), false, true);
                        try {
                            ZipEntry findEntry = strictJarFile2.findEntry(aSKSSession.getCertName());
                            if (findEntry == null) {
                                Slog.i("AASA_ASKSManager", "Token Cert does not exist!");
                                strictJarFile2.close();
                                byte[] bytes = "21".getBytes();
                                try {
                                    strictJarFile2.close();
                                } catch (IOException unused) {
                                }
                                return bytes;
                            }
                            strictJarFile3 = strictJarFile2.getInputStream(findEntry);
                            byte[] findCertificateIndex = findCertificateIndex(aSKSSession, tokenContents);
                            if (findCertificateIndex != null) {
                                try {
                                    strictJarFile2.close();
                                    if (strictJarFile3 != null) {
                                        strictJarFile3.close();
                                    }
                                } catch (IOException unused2) {
                                }
                                return findCertificateIndex;
                            }
                        } catch (Exception e) {
                            e = e;
                            strictJarFile = null;
                            strictJarFile3 = strictJarFile2;
                            Slog.e("AASA_ASKSManager", "ERROR: checkIntegrity " + e);
                            byte[] bytes2 = "22".getBytes();
                            if (strictJarFile3 != null) {
                            }
                            if (strictJarFile != null) {
                            }
                            return bytes2;
                        } catch (Throwable th) {
                            th = th;
                            inputStream = null;
                            strictJarFile3 = strictJarFile2;
                            if (strictJarFile3 != null) {
                                try {
                                    strictJarFile3.close();
                                } catch (IOException unused3) {
                                    throw th;
                                }
                            }
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            throw th;
                        }
                    } else if (i != 2) {
                        strictJarFile2 = null;
                    } else {
                        if (!new File(this.EE_CERT_FILE).exists()) {
                            return null;
                        }
                        strictJarFile = new FileInputStream(new File(this.EE_CERT_FILE));
                        try {
                            byte[] findCertificateIndex2 = findCertificateIndex(aSKSSession, tokenContents);
                            if (findCertificateIndex2 != null) {
                                try {
                                    strictJarFile.close();
                                } catch (IOException unused4) {
                                }
                                return findCertificateIndex2;
                            }
                            strictJarFile2 = null;
                            strictJarFile3 = strictJarFile;
                        } catch (Exception e2) {
                            e = e2;
                            Slog.e("AASA_ASKSManager", "ERROR: checkIntegrity " + e);
                            byte[] bytes22 = "22".getBytes();
                            if (strictJarFile3 != null) {
                                try {
                                    strictJarFile3.close();
                                } catch (IOException unused5) {
                                    return bytes22;
                                }
                            }
                            if (strictJarFile != null) {
                                strictJarFile.close();
                            }
                            return bytes22;
                        }
                    }
                    X509Certificate x509Certificate = (X509Certificate) certificateFactory.generateCertificate(strictJarFile3);
                    if (strictJarFile2 != null) {
                        strictJarFile2.close();
                    }
                    if (strictJarFile3 != null) {
                        strictJarFile3.close();
                    }
                    signature.initVerify(x509Certificate);
                    signature.update(tokenContents, 0, tokenContents.length);
                    if (!signature.verify(bArr2) && !this.DEBUG_MODE_FOR_DEVELOPMENT) {
                        Slog.e("AASA_ASKSManager", "Token is NOT verificated in checkIntegrity!");
                        byte[] bytes3 = "22".getBytes();
                        if (strictJarFile2 != null) {
                            try {
                                strictJarFile2.close();
                            } catch (IOException unused6) {
                            }
                        }
                        if (strictJarFile3 != null) {
                            strictJarFile3.close();
                        }
                        return bytes3;
                    }
                    byte[] checkCertificateChaining = checkCertificateChaining(aSKSSession, tokenContents, x509Certificate);
                    if (strictJarFile2 != null) {
                        try {
                            strictJarFile2.close();
                        } catch (IOException unused7) {
                        }
                    }
                    if (strictJarFile3 != null) {
                        strictJarFile3.close();
                    }
                    return checkCertificateChaining;
                }
                return tokenContents;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e3) {
            e = e3;
            strictJarFile = null;
        } catch (Throwable th3) {
            th = th3;
            inputStream = null;
        }
    }

    public final byte[] getTokenContents(byte[] bArr, int i) {
        boolean z;
        int i2;
        byte[] bArr2 = new byte[7];
        int i3 = i;
        int i4 = 0;
        while (true) {
            byte b = bArr[i3];
            if (b == 44) {
                z = false;
                break;
            }
            if (i4 >= 5) {
                z = true;
                break;
            }
            bArr2[i4] = b;
            i3++;
            i4++;
        }
        if (z) {
            return "22".getBytes();
        }
        byte[] bArr3 = new byte[i4];
        System.arraycopy(bArr2, 0, bArr3, 0, i4);
        try {
            i2 = Integer.parseInt(new String(bArr3));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            i2 = 0;
        }
        byte[] bArr4 = new byte[i2];
        System.arraycopy(bArr, i + i4 + 1, bArr4, 0, i2);
        return bArr4;
    }

    public final byte[] findCertificateIndex(ASKSSession aSKSSession, byte[] bArr) {
        try {
            XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
            newPullParser.setInput(new ByteArrayInputStream(bArr), null);
            new ArrayList();
            try {
                String parseXMLNew = parseXMLNew(newPullParser, "INDEX");
                Slog.i("AASA_ASKSManager", "index : " + parseXMLNew);
                if ("0.0".equals(parseXMLNew)) {
                    Slog.d("AASA_ASKSManager", "ENG Cert Index");
                } else {
                    String[] split = parseXMLNew.split("\\.");
                    String replaceAll = aSKSSession.getTokenName().replaceAll("[^0-9]", "");
                    aSKSSession.setCAKeyIndex(split[0]);
                    Slog.d("AASA_ASKSManager", "mTokenName : " + aSKSSession.getTokenName() + " SignerVersion : " + replaceAll);
                    if ("".equals(replaceAll)) {
                        replaceAll = "1";
                    }
                    if (!replaceAll.equals(split[1])) {
                        Slog.d("AASA_ASKSManager", "Signer Cert File is not matched with index!");
                        return "21".getBytes();
                    }
                    if (checkListForASKS(14, "SIGNER", split[1]) != -1) {
                        Slog.d("AASA_ASKSManager", "SIGNER is in CRL");
                        return "21".getBytes();
                    }
                    if (checkListForASKS(14, "INTER", split[0]) != -1) {
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

    public final byte[] checkCertificateChaining(ASKSSession aSKSSession, byte[] bArr, X509Certificate x509Certificate) {
        FileInputStream fileInputStream;
        X509Certificate x509Certificate2;
        if (this.DEBUG_MODE_FOR_DEVELOPMENT) {
            return bArr;
        }
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("x.509");
            try {
                fileInputStream = new FileInputStream(this.CA_CERT_PATH + aSKSSession.getCAKeyIndex() + ".crt");
                x509Certificate2 = (X509Certificate) certificateFactory.generateCertificate(fileInputStream);
            } catch (Exception e) {
                Slog.e("AASA_ASKSManager", "Look at system File. " + e);
                fileInputStream = new FileInputStream(this.CA_CERT_SYSTEM_PATH + aSKSSession.getCAKeyIndex() + ".crt");
                x509Certificate2 = (X509Certificate) certificateFactory.generateCertificate(fileInputStream);
            }
            try {
                x509Certificate.verify(x509Certificate2.getPublicKey());
                Slog.i("AASA_ASKSManager", "signerCert is verificated!");
                fileInputStream.close();
                try {
                    X509Certificate x509Certificate3 = (X509Certificate) certificateFactory.generateCertificate(new FileInputStream(this.ROOT_CERT_FILE));
                    x509Certificate2.verify(x509Certificate3.getPublicKey());
                    Slog.i("AASA_ASKSManager", "CAcert is verificated!");
                    try {
                        x509Certificate3.verify(x509Certificate3.getPublicKey());
                        Slog.i("AASA_ASKSManager", "rootCert is verificated!");
                        return bArr;
                    } catch (Exception e2) {
                        Slog.i("AASA_ASKSManager", "ERROR: rootCert is not verified " + e2);
                        return "22".getBytes();
                    }
                } catch (Exception e3) {
                    Slog.i("AASA_ASKSManager", "ERROR: CACert is not verified by RootCert " + e3);
                    return "22".getBytes();
                }
            } catch (Exception e4) {
                Slog.i("AASA_ASKSManager", "ERROR: SignerCert is not verified by CACert " + e4);
                return "22".getBytes();
            }
        } catch (Exception unused) {
            Slog.e("AASA_ASKSManager", "Token is NOT verificated in CheckCRL!");
            return "22".getBytes();
        }
    }

    public final void parseXMLNew(XmlPullParser xmlPullParser, HashMap hashMap) {
        parseXMLNew(xmlPullParser, hashMap, null);
    }

    public final String parseXMLNew(XmlPullParser xmlPullParser, String str) {
        HashMap hashMap = new HashMap();
        hashMap.put(str, "NONE");
        parseXMLNew(xmlPullParser, hashMap, null);
        return (String) hashMap.get(str);
    }

    public final void parseXMLNew(XmlPullParser xmlPullParser, HashMap hashMap, ArrayList arrayList) {
        String name;
        int eventType = xmlPullParser.getEventType();
        while (eventType != 1) {
            if (eventType == 2 && (name = xmlPullParser.getName()) != null) {
                if (hashMap.containsKey(name)) {
                    if (xmlPullParser.getAttributeCount() != 1) {
                        Slog.e("AASA_ASKSManager", "this is an exceptional case");
                    }
                    hashMap.replace(name, xmlPullParser.getAttributeValue(0));
                } else if (arrayList != null && "PERMISSION".equals(name)) {
                    if (xmlPullParser.getAttributeCount() != 1) {
                        Slog.e("AASA_ASKSManager", "this is an exceptional case for permission");
                    }
                    arrayList.add(xmlPullParser.getAttributeValue(0));
                }
            }
            eventType = xmlPullParser.next();
        }
    }

    public final boolean checkTokenTarget(String str, String str2) {
        boolean z;
        boolean z2;
        if (str == null || str2 == null) {
            Slog.i("AASA_ASKSManager", "ERROR: checkTokenTarget input is null");
            return false;
        }
        String[] split = str.split(",");
        String[] split2 = str2.split(",");
        String str3 = SystemProperties.get("ro.product.model", "Unknown");
        String str4 = SystemProperties.get("ro.csc.sales_code");
        int i = 1;
        if ("ALL".equals(split[0])) {
            if (split.length != 1) {
                int i2 = 1;
                while (true) {
                    if (i2 >= split.length) {
                        z2 = true;
                        break;
                    }
                    if (split[i2].equals(str3)) {
                        z2 = false;
                        break;
                    }
                    i2++;
                }
                if (z2) {
                    if ("ALL".equals(split2[0])) {
                        if (split2.length != 1) {
                            while (i < split2.length) {
                                if (split2[i].equals(str4)) {
                                    return false;
                                }
                                i++;
                            }
                        }
                    } else if (!"ALL".equals(split2[0])) {
                        for (String str5 : split2) {
                            if (!str5.equals(str4)) {
                            }
                        }
                        return false;
                    }
                }
                return z2;
            }
            if ("ALL".equals(split2[0])) {
                if (split2.length != 1) {
                    for (int i3 = 1; i3 < split2.length; i3++) {
                        if (split2[i3].equals(str4)) {
                            return false;
                        }
                    }
                }
            } else {
                if ("ALL".equals(split2[0])) {
                    return false;
                }
                for (String str6 : split2) {
                    if (!str6.equals(str4)) {
                    }
                }
                return false;
            }
        } else {
            if ("ALL".equals(split[0])) {
                return false;
            }
            int i4 = 0;
            while (true) {
                if (i4 >= split.length) {
                    z = false;
                    break;
                }
                if (split[i4].equals(str3)) {
                    z = true;
                    break;
                }
                i4++;
            }
            if (z) {
                if ("ALL".equals(split2[0])) {
                    if (split2.length == 1) {
                        for (String str7 : split) {
                            if (!str7.equals(str3)) {
                            }
                        }
                        return false;
                    }
                    while (i < split2.length) {
                        if (split2[i].equals(str4)) {
                            return false;
                        }
                        i++;
                    }
                } else if (!"ALL".equals(split2[0])) {
                    for (String str8 : split2) {
                        if (!str8.equals(str4)) {
                        }
                    }
                    return false;
                }
            }
            return z;
        }
        return true;
    }

    public final boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    public final void writeBlockApkList(String str) {
        PrintWriter fastPrintWriter;
        PrintWriter printWriter = null;
        try {
            try {
                fastPrintWriter = new FastPrintWriter(new FileOutputStream(new File("/data/system/.aasa/blockedList.log"), false));
            } catch (Throwable th) {
                th = th;
            }
        } catch (IOException e) {
            e = e;
        }
        try {
            fastPrintWriter.println(str);
            fastPrintWriter.close();
            Slog.i("AASA_ASKSManager", "writeBlockApkList() Success");
            fastPrintWriter.close();
        } catch (IOException e2) {
            e = e2;
            printWriter = fastPrintWriter;
            Slog.i("AASA_ASKSManager", "writeBlockApkList() Fail " + e);
            if (printWriter != null) {
                printWriter.close();
            }
        } catch (Throwable th2) {
            th = th2;
            printWriter = fastPrintWriter;
            if (printWriter != null) {
                printWriter.close();
            }
            throw th;
        }
    }

    public final String getSHA256ForPkgName(String str) {
        try {
            return getSHA256(str + "AASAASKS");
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException unused) {
            return "";
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0058 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r4v1, types: [java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r4v8, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r4v9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final byte[] getApkFileHashBytes(String str) {
        Throwable th;
        FileInputStream fileInputStream;
        byte[] bArr = 0;
        bArr = 0;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            File file = new File(str);
            if (messageDigest != null && file.exists()) {
                try {
                    try {
                        byte[] bArr2 = new byte[IInstalld.FLAG_USE_QUOTA];
                        fileInputStream = new FileInputStream(file);
                        while (true) {
                            try {
                                int read = fileInputStream.read(bArr2);
                                if (read == -1) {
                                    break;
                                }
                                messageDigest.update(bArr2, 0, read);
                            } catch (IOException e) {
                                e = e;
                                Slog.e("AASA_ASKSManager", " ERROR: getApkFileHash:" + e);
                                if (fileInputStream != null) {
                                    try {
                                        fileInputStream.close();
                                    } catch (IOException unused) {
                                    }
                                }
                                return null;
                            }
                        }
                        bArr = messageDigest.digest();
                        try {
                            fileInputStream.close();
                        } catch (IOException unused2) {
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (0 != 0) {
                            try {
                                bArr.close();
                            } catch (IOException unused3) {
                            }
                        }
                        throw th;
                    }
                } catch (IOException e2) {
                    e = e2;
                    fileInputStream = null;
                } catch (Throwable th3) {
                    th = th3;
                    if (0 != 0) {
                    }
                    throw th;
                }
            }
            return bArr;
        } catch (NoSuchAlgorithmException e3) {
            e3.printStackTrace();
            return null;
        }
    }

    public final String getApkFileHash(String str) {
        try {
            byte[] apkFileHashBytes = getApkFileHashBytes(str);
            return apkFileHashBytes != null ? getSHA256ForPkgName(convertToHex(apkFileHashBytes)) : "";
        } catch (IOException unused) {
            return "";
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:86:0x0120 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String getAdvancedHash(String str) {
        MessageDigest messageDigest;
        StrictJarFile strictJarFile;
        int i;
        MessageDigest messageDigest2;
        StrictJarFile strictJarFile2 = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            messageDigest = null;
        }
        try {
            strictJarFile = new StrictJarFile(str, false, true);
            try {
                try {
                    ArrayList arrayList = new ArrayList();
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    Iterator it = strictJarFile.iterator();
                    loop0: while (true) {
                        i = 0;
                        while (it.hasNext()) {
                            i++;
                            ZipEntry zipEntry = (ZipEntry) it.next();
                            String name = zipEntry.getName();
                            if (!name.startsWith("META-INF/") && !name.startsWith("SEC-INF/") && !name.startsWith("token/")) {
                                loadCertificates(strictJarFile, zipEntry, messageDigest);
                                linkedHashMap.put(name, convertToHex(messageDigest.digest()));
                                if (i >= 50000) {
                                    break;
                                }
                            }
                        }
                        arrayList.add((LinkedHashMap) linkedHashMap.clone());
                        linkedHashMap.clear();
                    }
                    if (i != 0) {
                        arrayList.add((LinkedHashMap) linkedHashMap.clone());
                        linkedHashMap.clear();
                    }
                    strictJarFile.close();
                    try {
                        messageDigest2 = MessageDigest.getInstance("SHA-256");
                    } catch (NoSuchAlgorithmException e2) {
                        e2.printStackTrace();
                        messageDigest2 = null;
                    }
                    for (int i2 = 0; i2 < arrayList.size(); i2++) {
                        TreeMap treeMap = new TreeMap((Map) arrayList.get(i2));
                        Iterator it2 = treeMap.keySet().iterator();
                        while (it2.hasNext()) {
                            String str2 = (String) treeMap.get((String) it2.next());
                            try {
                                messageDigest2.update(str2.getBytes("ISO-8859-1"), 0, str2.length());
                            } catch (UnsupportedEncodingException e3) {
                                e3.printStackTrace();
                            }
                        }
                    }
                    String convertToHex = convertToHex(messageDigest2.digest());
                    Slog.i("AASA_ASKSManager", " advanced hash::" + convertToHex);
                    try {
                        strictJarFile.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                    return convertToHex;
                } catch (IOException e5) {
                    e = e5;
                    Slog.i("AASA_ASKSManager", " ERROR: AASA_VerifyToken check hash " + e);
                    if (strictJarFile != null) {
                        try {
                            strictJarFile.close();
                        } catch (IOException e6) {
                            e6.printStackTrace();
                        }
                    }
                    return null;
                }
            } catch (Throwable th) {
                th = th;
                strictJarFile2 = strictJarFile;
                if (strictJarFile2 != null) {
                    try {
                        strictJarFile2.close();
                    } catch (IOException e7) {
                        e7.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (IOException e8) {
            e = e8;
            strictJarFile = null;
        } catch (Throwable th2) {
            th = th2;
            if (strictJarFile2 != null) {
            }
            throw th;
        }
    }

    public final String getSHA256(String str) {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(str.getBytes("ISO-8859-1"), 0, str.length());
        return convertToHex(messageDigest.digest());
    }

    public final String convertToHex(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        if (bArr == null) {
            return "null";
        }
        for (byte b : bArr) {
            int i = (b >>> 4) & 15;
            int i2 = 0;
            while (true) {
                sb.append((char) ((i < 0 || i > 9) ? (i - 10) + 97 : i + 48));
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

    public final Certificate[] loadCertificates(StrictJarFile strictJarFile, ZipEntry zipEntry, MessageDigest messageDigest) {
        InputStream inputStream;
        try {
            if (zipEntry == null) {
                return null;
            }
            try {
                inputStream = strictJarFile.getInputStream(zipEntry);
            } catch (IOException e) {
                e = e;
                inputStream = null;
            } catch (RuntimeException e2) {
                e = e2;
                inputStream = null;
            }
            try {
                byte[] bArr = new byte[IInstalld.FLAG_USE_QUOTA];
                if (inputStream != null) {
                    while (true) {
                        int read = inputStream.read(bArr, 0, IInstalld.FLAG_USE_QUOTA);
                        if (read == -1) {
                            break;
                        }
                        messageDigest.update(bArr, 0, read);
                    }
                    inputStream.close();
                }
                return strictJarFile.getCertificates(zipEntry);
            } catch (IOException e3) {
                e = e3;
                Slog.e("AASA_ASKSManager", "loadCert(md) : TinyAASA + No IO " + e.toString());
                if (inputStream != null) {
                    inputStream.close();
                }
                return null;
            } catch (RuntimeException e4) {
                e = e4;
                Slog.e("AASA_ASKSManager", "loadCert(md) : TinyAASA + No RUN " + e.toString());
                if (inputStream != null) {
                    inputStream.close();
                }
                return null;
            }
        } catch (IOException unused) {
        }
    }

    public final Certificate[] loadCertificates(StrictJarFile strictJarFile, ZipEntry zipEntry, byte[] bArr) {
        InputStream inputStream;
        try {
            if (zipEntry != null) {
                try {
                    inputStream = strictJarFile.getInputStream(zipEntry);
                    if (inputStream != null) {
                        do {
                            try {
                            } catch (IOException e) {
                                e = e;
                                Slog.i("AASA_ASKSManager", "loadCert(B) : No IO " + e.toString());
                                if (inputStream != null) {
                                    inputStream.close();
                                }
                                return null;
                            } catch (RuntimeException e2) {
                                e = e2;
                                Slog.i("AASA_ASKSManager", "loadCert(B) : No RUN " + e.toString());
                                if (inputStream != null) {
                                    inputStream.close();
                                }
                                return null;
                            }
                        } while (inputStream.read(bArr, 0, bArr.length) != -1);
                        inputStream.close();
                    }
                    return strictJarFile.getCertificates(zipEntry);
                } catch (IOException e3) {
                    e = e3;
                    inputStream = null;
                } catch (RuntimeException e4) {
                    e = e4;
                    inputStream = null;
                }
            }
        } catch (IOException unused) {
        }
        return null;
    }

    public final void updateRestrictedTargetPackages() {
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = (HashMap) this.mASKSStates.clone();
        for (Map.Entry entry : hashMap2.entrySet()) {
            ASKSState aSKSState = (ASKSState) hashMap2.get(entry.getKey());
            if (aSKSState.getRestrict() != null) {
                hashMap.put((String) entry.getKey(), aSKSState.getRestrict().getType());
            } else if (aSKSState.getEMMode() != -1) {
                hashMap.put((String) entry.getKey(), "DENY");
            }
        }
        ASKSManager.updateRestrictedTargetPackages(hashMap);
    }

    public final boolean updateRestrictRule(ASKSSession aSKSSession) {
        HashMap hashMap = new HashMap();
        getRestrictDataFromXML(hashMap, aSKSSession);
        if (hashMap.isEmpty()) {
            Log.d("AASA_ASKSManager_RESTRICTED", "There is no restricted rule.");
        }
        HashMap hashMap2 = this.mASKSStates;
        boolean z = false;
        if (hashMap2 != null && !hashMap2.isEmpty()) {
            for (Map.Entry entry : this.mASKSStates.entrySet()) {
                String str = (String) entry.getKey();
                ASKSState aSKSState = (ASKSState) entry.getValue();
                if (hashMap.containsKey(str)) {
                    Restrict restrict = aSKSState.getRestrict();
                    Restrict restrict2 = (Restrict) hashMap.get(str);
                    Slog.d("AASA_ASKSManager_RESTRICTED", "updateRestrictRule() : new restricted rule exists.");
                    if (restrict != null && "Token".equals(restrict.getFrom())) {
                        Slog.d("AASA_ASKSManager_RESTRICTED", "updateRestictRule() : current restricted rule(" + str + ") is from Token. Skipped.");
                    } else if (restrict == null || ("Policy".equals(restrict.getFrom()) && Integer.parseInt(restrict2.getVersion()) > Integer.parseInt(restrict.getVersion()))) {
                        aSKSState.setRestrict(restrict2);
                        Slog.d("AASA_ASKSManager_RESTRICTED", "updateRestrictRule() : update restricted rule for " + ((String) entry.getKey()));
                        z = true;
                    }
                } else {
                    Slog.d("AASA_ASKSManager_RESTRICTED", "updateRestrictRule() : new restricted rule doesn't exists.");
                    if (aSKSState.getRestrict() != null && "Policy".equals(aSKSState.getRestrict().getFrom())) {
                        aSKSState.setRestrict(null);
                        Slog.d("AASA_ASKSManager_RESTRICTED", "updateRestrictRule() : remove restricted rule for " + ((String) entry.getKey()));
                        z = true;
                    } else if (aSKSState.getRestrict() != null && "Token".equals(aSKSState.getRestrict().getFrom())) {
                        Slog.d("AASA_ASKSManager_RESTRICTED", "updateRestictRule() : current restricted rule(" + ((String) entry.getKey()) + ") is from Token. not removed.");
                    }
                }
            }
        }
        for (Map.Entry entry2 : hashMap.entrySet()) {
            HashMap hashMap3 = this.mASKSStates;
            if (hashMap3 == null || !hashMap3.containsKey(entry2.getKey())) {
                Slog.d("AASA_ASKSManager_RESTRICTED", "updateRestrictRule() : new restricted rule for " + ((String) entry2.getKey()));
                ASKSState aSKSState2 = new ASKSState();
                aSKSState2.setRestrict((Restrict) entry2.getValue());
                this.mASKSStates.put((String) entry2.getKey(), aSKSState2);
                z = true;
            }
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00dc A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00d2 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void getRestrictDataFromXML(HashMap hashMap, ASKSSession aSKSSession) {
        int next;
        String str;
        Restrict restrict;
        HashMap hashMap2;
        String name;
        File file = new File("/data/system/.aasa/AASApolicy/ASKSRNEW.xml");
        if (!file.exists()) {
            Slog.e("AASA_ASKSManager_RESTRICTED", "There is no restict rule in system.");
            return;
        }
        try {
            XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
            newPullParser.setInput(new FileReader(file));
            do {
                next = newPullParser.next();
                if (next == 2) {
                    break;
                }
            } while (next != 1);
            ArrayList arrayList = new ArrayList();
            if ("VERSION".equals(newPullParser.getName())) {
                str = newPullParser.getAttributeValue(null, "value");
                restrict = null;
            } else {
                str = null;
                restrict = null;
            }
            String str2 = restrict;
            while (true) {
                int next2 = newPullParser.next();
                if (next2 == 1) {
                    break;
                }
                if (next2 == 3) {
                    if ("PACKAGE".equalsIgnoreCase(newPullParser.getName())) {
                        if (str2 == null || arrayList.size() == 0) {
                            Slog.e("AASA_ASKSManager_RESTRICTED", "The package information is wrong.");
                        } else {
                            if (isTargetPackage(str2, arrayList, aSKSSession)) {
                                if (restrict == null) {
                                    hashMap2 = hashMap;
                                    restrict = new Restrict(str, null, null, "Policy", null);
                                } else {
                                    hashMap2 = hashMap;
                                }
                                try {
                                    hashMap2.put(str2, restrict);
                                    Slog.d("AASA_ASKSManager_RESTRICTED", str2 + " : " + restrict.toString());
                                } catch (IOException e) {
                                    e = e;
                                    e.printStackTrace();
                                    hashMap.size();
                                } catch (XmlPullParserException e2) {
                                    e = e2;
                                    e.printStackTrace();
                                    hashMap.size();
                                }
                            }
                            arrayList.clear();
                        }
                        restrict = null;
                    } else {
                        name = newPullParser.getName();
                        if (!"PACKAGE".equalsIgnoreCase(name)) {
                            str2 = newPullParser.getAttributeValue(null, "name");
                        } else if ("CERT".equals(name)) {
                            arrayList.add(newPullParser.getAttributeValue(null, "value"));
                        } else if ("RESTRICT".equalsIgnoreCase(newPullParser.getName())) {
                            restrict = new Restrict();
                            restrict.setFrom("Policy");
                            readRestrictRule(newPullParser, restrict, str);
                        }
                    }
                } else if (next2 != 4) {
                    name = newPullParser.getName();
                    if (!"PACKAGE".equalsIgnoreCase(name)) {
                    }
                }
            }
        } catch (IOException e3) {
            e = e3;
        } catch (XmlPullParserException e4) {
            e = e4;
        }
        hashMap.size();
    }

    public final void readRestrict(XmlPullParser xmlPullParser, String str) {
        ASKSState aSKSState = (ASKSState) this.mASKSStates.get(str);
        if (aSKSState != null) {
            Restrict restrict = new Restrict();
            readRestrictRule(xmlPullParser, restrict, null);
            aSKSState.setRestrict(restrict);
        }
    }

    public final void readRestrictRule(XmlPullParser xmlPullParser, Restrict restrict, String str) {
        String from;
        ArrayList arrayList = null;
        String attributeValue = xmlPullParser.getAttributeValue(null, "type");
        String attributeValue2 = xmlPullParser.getAttributeValue(null, "datelimit");
        if (str == null) {
            str = xmlPullParser.getAttributeValue(null, "version");
        }
        if (restrict.getFrom() == null) {
            from = xmlPullParser.getAttributeValue(null, "from");
        } else {
            from = restrict.getFrom();
        }
        if (((attributeValue == null) || (str == null)) || attributeValue2 == null || from == null) {
            return;
        }
        if ("REVOKE".equals(attributeValue)) {
            arrayList = new ArrayList();
            readRestrictPermissions(xmlPullParser, arrayList);
        }
        restrict.setVersion(str);
        restrict.setType(attributeValue);
        restrict.setDateLimit(attributeValue2);
        restrict.setFrom(from);
        restrict.setPermissionList(arrayList);
    }

    public final void readRestrictPermissions(XmlPullParser xmlPullParser, ArrayList arrayList) {
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4 && "permission".equalsIgnoreCase(xmlPullParser.getName()) && xmlPullParser.getAttributeValue(null, "value") != null) {
                arrayList.add(xmlPullParser.getAttributeValue(null, "value"));
            }
        }
        arrayList.size();
    }

    /* JADX WARN: Removed duplicated region for block: B:92:0x00f8 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0073 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void getADPDataFromXML(HashMap hashMap) {
        FileReader fileReader;
        int next;
        boolean z;
        File file = new File("/data/system/.aasa/AASApolicy/ADP.xml");
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdir();
            file.getParentFile().setReadable(true, false);
        }
        if (!file.exists()) {
            Slog.e("AASA_ASKSManager_ADP", "file does not exist - /data/system/.aasa/AASApolicy/ADP.xml");
        }
        if (file.exists()) {
            try {
                fileReader = new FileReader(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                fileReader = null;
            }
            try {
                try {
                    try {
                        XmlPullParser newPullParser = XmlPullParserFactory.newInstance().newPullParser();
                        newPullParser.setInput(fileReader);
                        do {
                            next = newPullParser.next();
                            if (next == 2) {
                                break;
                            }
                        } while (next != 1);
                        ADPContainer.ADPContainerBuilder aDPContainerBuilder = new ADPContainer.ADPContainerBuilder();
                        ADPContainer.ADPPolicyBuilder aDPPolicyBuilder = new ADPContainer.ADPPolicyBuilder();
                        while (true) {
                            int next2 = newPullParser.next();
                            if (next2 == 1) {
                                break;
                            }
                            if (next2 != 3 && next2 != 4) {
                                String name = newPullParser.getName();
                                if ("HASHVALUE".equals(name)) {
                                    aDPContainerBuilder.set_pkgName(newPullParser.getAttributeValue(0));
                                } else if ("versionType".equals(name)) {
                                    try {
                                        aDPPolicyBuilder.set_versionType(Integer.valueOf(newPullParser.getAttributeValue(0)).intValue());
                                    } catch (NumberFormatException unused) {
                                        Slog.e("AASA_ASKSManager_ADP", "ERROR: does not match versionType");
                                    }
                                } else if ("pattern".equals(name)) {
                                    aDPPolicyBuilder.set_pattern(newPullParser.getAttributeValue(0));
                                } else if ("hashCode".equals(name)) {
                                    aDPPolicyBuilder.set_hashCode(newPullParser.getAttributeValue(0));
                                } else if ("sep".equals(name)) {
                                    try {
                                    } catch (NumberFormatException unused2) {
                                        Slog.e("AASA_ASKSManager_ADP", "NumberFormatExceptionn");
                                    }
                                    if (Build.VERSION.SEM_PLATFORM_INT >= Integer.valueOf(newPullParser.getAttributeValue(0)).intValue()) {
                                        z = false;
                                        if (!z) {
                                            aDPPolicyBuilder.flush();
                                        }
                                    }
                                    z = true;
                                    if (!z) {
                                    }
                                } else if ("format".equals(name)) {
                                    aDPPolicyBuilder.set_format(newPullParser.getAttributeValue(0));
                                }
                            } else if ("HASHVALUE".equals(newPullParser.getName())) {
                                ADPContainer createADPContainer = aDPContainerBuilder.createADPContainer();
                                if (createADPContainer == null) {
                                    Slog.e("AASA_ASKSManager_ADP", newPullParser.getName() + " does not make ADP object");
                                } else {
                                    hashMap.put(createADPContainer.getPackageName(), createADPContainer);
                                    aDPContainerBuilder.flush();
                                }
                            } else if ("pattern".equals(newPullParser.getName())) {
                                ADPContainer.ADPPolicy createADPPolicy = aDPPolicyBuilder.createADPPolicy();
                                if (createADPPolicy == null) {
                                    Slog.e("AASA_ASKSManager_ADP", newPullParser.getName() + " does not make ADPPolicy object");
                                } else {
                                    if (aDPContainerBuilder.get_ADPPolicy() == null) {
                                        aDPContainerBuilder.set_ADPPolicy();
                                    }
                                    aDPContainerBuilder.add_ADPPolicy(createADPPolicy);
                                    aDPPolicyBuilder.flush();
                                }
                            }
                        }
                    } catch (Throwable th) {
                        if (fileReader != null) {
                            try {
                                fileReader.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (IOException e3) {
                    e3.printStackTrace();
                    return;
                }
            } catch (IOException e4) {
                e4.printStackTrace();
                if (fileReader == null) {
                    return;
                } else {
                    fileReader.close();
                }
            } catch (XmlPullParserException e5) {
                e5.printStackTrace();
                if (fileReader == null) {
                    return;
                } else {
                    fileReader.close();
                }
            }
            if (fileReader != null) {
                fileReader.close();
            }
        }
    }

    public final void readEMMode(XmlPullParser xmlPullParser, String str) {
        ASKSState aSKSState = (ASKSState) this.mASKSStates.get(str);
        if (aSKSState != null) {
            aSKSState.setEMMode(Integer.decode(xmlPullParser.getAttributeValue(null, "value")).intValue());
        }
    }

    public final void readDeletable(XmlPullParser xmlPullParser, String str) {
        ASKSState aSKSState = (ASKSState) this.mASKSStates.get(str);
        if (aSKSState != null) {
            String attributeValue = xmlPullParser.getAttributeValue(null, "version");
            String attributeValue2 = xmlPullParser.getAttributeValue(null, "datelimit");
            if (attributeValue == null || attributeValue2 == null) {
                return;
            }
            aSKSState.setDeletable(new Deletable(attributeValue, attributeValue2));
        }
    }

    public final void readyForBooting(Context context) {
        String[] trustedFile;
        Slog.d("AASA_ASKSManager_SECURETIME", "readyForBooting : ");
        if (context == null) {
            Slog.d("AASA_ASKSManager_SECURETIME", "context is null. ");
            return;
        }
        int checkNetworkConnection = checkNetworkConnection(context);
        if (isAutoTimeEnabled(context) && checkNetworkConnection > 0) {
            setTrustedFile(0, System.currentTimeMillis(), SystemClock.elapsedRealtime());
        } else if (hasTrustedTime() && (trustedFile = getTrustedFile()) != null && trustedFile.length == 3) {
            int parseInt = Integer.parseInt(trustedFile[0]);
            setTrustedFile(parseInt < 2 ? 2 : parseInt, Long.parseLong(trustedFile[1]), SystemClock.elapsedRealtime());
        }
    }

    public final int checkNetworkConnection(Context context) {
        Slog.d("AASA_ASKSManager_SECURETIME", "checkNetworkConnection : ");
        if (context == null) {
            Slog.d("AASA_ASKSManager_SECURETIME", "context is null. ");
            return this.TYPE_NOT_CONNECTED;
        }
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null) {
            if (activeNetworkInfo.getType() == 1) {
                return this.TYPE_WIFI;
            }
            if (activeNetworkInfo.getType() == 0) {
                return this.TYPE_MOBILE;
            }
        }
        return this.TYPE_NOT_CONNECTED;
    }

    public final boolean isAutoTimeEnabled(Context context) {
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

    public void setTrustTimebyStatusChanged() {
        String[] trustedFile;
        Slog.d("AASA_ASKSManager_SECURETIME", "setTrustTimebyStatusChanged : ");
        if (this.mContext == null) {
            Slog.d("AASA_ASKSManager_SECURETIME", "mContext is null. ");
            return;
        }
        if (hasTrustedTime() && (trustedFile = getTrustedFile()) != null && trustedFile.length == 3 && trustedFile[0].equals("0")) {
            updateTrustedFile();
            return;
        }
        int checkNetworkConnection = checkNetworkConnection(this.mContext);
        if (isAutoTimeEnabled(this.mContext) && checkNetworkConnection > 0) {
            setTrustedFile(0, System.currentTimeMillis(), SystemClock.elapsedRealtime());
        } else {
            updateTrustedFile();
        }
    }

    public final void updateTrustedFile() {
        String[] trustedFile;
        Slog.d("AASA_ASKSManager_SECURETIME", "updateTrustedFile : ");
        if (hasTrustedTime() && (trustedFile = getTrustedFile()) != null && trustedFile.length == 3) {
            int parseInt = Integer.parseInt(trustedFile[0]);
            long parseLong = Long.parseLong(trustedFile[1]);
            long parseLong2 = Long.parseLong(trustedFile[2]);
            long elapsedRealtime = SystemClock.elapsedRealtime();
            setTrustedFile(parseInt, (parseLong - parseLong2) + elapsedRealtime, elapsedRealtime);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x006a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setTrustedFile(int i, long j, long j2) {
        PrintWriter printWriter;
        Throwable th;
        IOException e;
        Slog.d("AASA_ASKSManager_SECURETIME", "setTrustedFile : ");
        SystemProperties.set("security.ASKS.time_value", convertMillsToString(j));
        try {
            printWriter = new PrintWriter("/data/system/.aasa/trustedTime");
            try {
                try {
                    printWriter.println("" + i + "," + j + "," + j2);
                    printWriter.flush();
                    printWriter.close();
                } catch (IOException e2) {
                    e = e2;
                    Slog.d("AASA_ASKSManager_SECURETIME", "setTrustedTime() " + e);
                    if (printWriter == null) {
                        return;
                    }
                    printWriter.close();
                }
            } catch (Throwable th2) {
                th = th2;
                if (printWriter != null) {
                    printWriter.close();
                }
                throw th;
            }
        } catch (IOException e3) {
            printWriter = null;
            e = e3;
        } catch (Throwable th3) {
            printWriter = null;
            th = th3;
            if (printWriter != null) {
            }
            throw th;
        }
        printWriter.close();
    }

    public final boolean hasTrustedTime() {
        return new File("/data/system/.aasa/trustedTime").exists();
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0038, code lost:
    
        if (r2 == null) goto L23;
     */
    /* JADX WARN: Not initialized variable reg: 2, insn: 0x0043: MOVE (r0 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:35:0x0043 */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0046 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String[] getTrustedFile() {
        BufferedReader bufferedReader;
        String[] strArr;
        BufferedReader bufferedReader2;
        Slog.d("AASA_ASKSManager_SECURETIME", "getTrustedFile : ");
        BufferedReader bufferedReader3 = null;
        try {
            try {
                FileReader fileReader = new FileReader("/data/system/.aasa/trustedTime");
                bufferedReader = new BufferedReader(fileReader);
                strArr = null;
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null || ((strArr = readLine.split(",")) != null && strArr.length == 3)) {
                            break;
                        }
                    } catch (IOException e) {
                        e = e;
                        e.printStackTrace();
                    }
                }
                fileReader.close();
            } catch (Throwable th) {
                th = th;
                bufferedReader3 = bufferedReader2;
                if (bufferedReader3 != null) {
                    try {
                        bufferedReader3.close();
                    } catch (IOException unused) {
                    }
                }
                throw th;
            }
        } catch (IOException e2) {
            e = e2;
            bufferedReader = null;
            strArr = null;
        } catch (Throwable th2) {
            th = th2;
            if (bufferedReader3 != null) {
            }
            throw th;
        }
        try {
            bufferedReader.close();
        } catch (IOException unused2) {
        }
        if (strArr == null || strArr.length != 3) {
            return null;
        }
        return strArr;
    }

    public final void setTrustTimeByToken(String str) {
        setTrustedFile(3, convertStringToMills(str), SystemClock.elapsedRealtime());
    }

    public final String getTrustedToday() {
        String str = SystemProperties.get("security.ASKS.time_value", "00000000");
        return (str == null || str.equals("00000000")) ? getTrustedTodayInner() : str;
    }

    public final String getTrustedTodayInner() {
        if (!hasTrustedTime()) {
            return convertMillsToString(System.currentTimeMillis());
        }
        String[] trustedFile = getTrustedFile();
        if (trustedFile == null || trustedFile.length != 3) {
            return convertMillsToString(System.currentTimeMillis());
        }
        String convertMillsToString = convertMillsToString((Long.parseLong(trustedFile[1]) - Long.parseLong(trustedFile[2])) + SystemClock.elapsedRealtime());
        Slog.d("AASA_ASKSManager_SECURETIME", "getElapsedToday : " + convertMillsToString);
        return convertMillsToString;
    }

    public final void setExpirationDate() {
        long j = SystemProperties.getLong("ro.build.date.utc", -1L) * 1000;
        if (j < 0) {
            SystemProperties.set("security.ASKS.expiration_date", Long.toString(j));
            return;
        }
        Date date = new Date(j);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, 3);
        SystemProperties.set("security.ASKS.expiration_date", new SimpleDateFormat("yyyyMMdd").format(calendar.getTime()));
    }

    public final void setSamsungAnalyticsLog(String str, String str2, String str3) {
        try {
            File file = new File("/data/system/.aasa/SamsungAnalyticsLog");
            if (file.length() <= 500000) {
                FastPrintWriter fastPrintWriter = new FastPrintWriter(new FileOutputStream(file, true));
                fastPrintWriter.println(str + "," + str2 + "," + str3);
                fastPrintWriter.close();
            } else {
                FastPrintWriter fastPrintWriter2 = new FastPrintWriter(new FileOutputStream(file, false));
                fastPrintWriter2.println(str + "," + str2 + "," + str3);
                fastPrintWriter2.close();
            }
        } catch (IOException e) {
            Slog.i("AASA_ASKSManager", "setSamsungAnalyticsLog " + e);
        }
    }

    public final String convertMillsToString(long j) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        return simpleDateFormat.format(calendar.getTime());
    }

    public final long convertStringToMills(String str) {
        try {
            return new SimpleDateFormat("yyyyMMdd").parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1L;
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
                    readRestrict(xmlPullParser, attributeValue);
                } else if (name.equals("emmode")) {
                    readEMMode(xmlPullParser, attributeValue);
                } else if (name.equals("delete")) {
                    readDeletable(xmlPullParser, attributeValue);
                } else {
                    Slog.w("ASKSManager", "Unknown element under <pkg>: " + xmlPullParser.getName());
                    com.android.internal.util.XmlUtils.skipCurrentTag(xmlPullParser);
                }
            }
        }
    }

    public final void readState() {
        readStateInner();
    }

    public final void readStateInner() {
        XmlPullParser newPullParser;
        int next;
        synchronized (this.mFile) {
            synchronized (this) {
                try {
                    try {
                        FileInputStream openRead = this.mFile.openRead();
                        this.mASKSStates.clear();
                        try {
                            try {
                                try {
                                    try {
                                        try {
                                            try {
                                                newPullParser = Xml.newPullParser();
                                                newPullParser.setInput(openRead, StandardCharsets.UTF_8.name());
                                                do {
                                                    next = newPullParser.next();
                                                    if (next == 2) {
                                                        break;
                                                    }
                                                } while (next != 1);
                                            } catch (Throwable th) {
                                                this.mASKSStates.clear();
                                                try {
                                                    openRead.close();
                                                } catch (IOException unused) {
                                                }
                                                throw th;
                                            }
                                        } catch (XmlPullParserException e) {
                                            Slog.w("ASKSManager", "Failed parsing " + e);
                                            this.mASKSStates.clear();
                                        }
                                    } catch (NumberFormatException e2) {
                                        Slog.w("ASKSManager", "Failed parsing " + e2);
                                        this.mASKSStates.clear();
                                    }
                                } catch (IllegalStateException e3) {
                                    Slog.w("ASKSManager", "Failed parsing " + e3);
                                    this.mASKSStates.clear();
                                }
                            } catch (IndexOutOfBoundsException e4) {
                                Slog.w("ASKSManager", "Failed parsing " + e4);
                                this.mASKSStates.clear();
                            }
                        } catch (IOException e5) {
                            Slog.w("ASKSManager", "Failed parsing " + e5);
                            this.mASKSStates.clear();
                        } catch (NullPointerException e6) {
                            Slog.w("ASKSManager", "Failed parsing " + e6);
                            this.mASKSStates.clear();
                        }
                        if (next != 2) {
                            throw new IllegalStateException("no start tag found");
                        }
                        String attributeValue = newPullParser.getAttributeValue(null, "version");
                        if (attributeValue != null) {
                            mASKSPolicyVersion = attributeValue;
                            Slog.i("AASA_ASKSManager", "policyVersion :" + mASKSPolicyVersion + XmlUtils.STRING_ARRAY_SEPARATOR + mASKSDeltaPolicyVersion);
                            SystemProperties.set("security.ASKS.policy_version", mASKSPolicyVersion);
                        }
                        int depth = newPullParser.getDepth();
                        while (true) {
                            int next2 = newPullParser.next();
                            if (next2 != 1 && (next2 != 3 || newPullParser.getDepth() > depth)) {
                                if (next2 != 3 && next2 != 4) {
                                    if (newPullParser.getName().equalsIgnoreCase("PACKAGE")) {
                                        readPackage(newPullParser);
                                    } else {
                                        Slog.w("ASKSManager", "Unknown element under <asks>: " + newPullParser.getName());
                                        com.android.internal.util.XmlUtils.skipCurrentTag(newPullParser);
                                    }
                                }
                            }
                            try {
                                openRead.close();
                            } catch (IOException unused2) {
                            }
                        }
                    } catch (Throwable th2) {
                        throw th2;
                    }
                } catch (FileNotFoundException unused3) {
                    Slog.i("AASA_ASKSManager", "No existing asks rules " + this.mFile.getBaseFile() + "; starting empty");
                }
            }
        }
    }

    public final void writeState() {
        writeStateInner();
    }

    public final void writeStateInner() {
        FileOutputStream startWrite;
        synchronized (this.mFile) {
            FileOutputStream fileOutputStream = null;
            try {
                try {
                    startWrite = this.mFile.startWrite();
                } catch (IOException e) {
                    e = e;
                }
            } catch (Throwable th) {
                th = th;
            }
            try {
                try {
                    HashMap hashMap = (HashMap) this.mASKSStates.clone();
                    try {
                        FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
                        fastXmlSerializer.setOutput(startWrite, StandardCharsets.UTF_8.name());
                        fastXmlSerializer.startDocument(null, Boolean.TRUE);
                        fastXmlSerializer.startTag(null, "safeinstall");
                        fastXmlSerializer.attribute(null, "delta", mASKSDeltaPolicyVersion);
                        fastXmlSerializer.endTag(null, "safeinstall");
                        fastXmlSerializer.startTag(null, "asks");
                        fastXmlSerializer.attribute(null, "version", mASKSPolicyVersion);
                        for (String str : hashMap.keySet()) {
                            ASKSState aSKSState = (ASKSState) hashMap.get(str);
                            if (aSKSState != null && aSKSState.hasValue()) {
                                fastXmlSerializer.startTag(null, "package");
                                fastXmlSerializer.attribute(null, "name", str);
                                if (aSKSState.getRestrict() != null) {
                                    Restrict restrict = aSKSState.getRestrict();
                                    fastXmlSerializer.startTag(null, "restrict");
                                    fastXmlSerializer.attribute(null, "version", restrict.getVersion());
                                    fastXmlSerializer.attribute(null, "type", restrict.getType());
                                    fastXmlSerializer.attribute(null, "datelimit", restrict.getDateLimit());
                                    fastXmlSerializer.attribute(null, "from", restrict.getFrom());
                                    if ("REVOKE".equals(restrict.getType())) {
                                        Iterator it = restrict.getPermissionList().iterator();
                                        while (it.hasNext()) {
                                            String str2 = (String) it.next();
                                            fastXmlSerializer.startTag(null, "permission");
                                            fastXmlSerializer.attribute(null, "value", str2);
                                            fastXmlSerializer.endTag(null, "permission");
                                        }
                                    }
                                    fastXmlSerializer.endTag(null, "restrict");
                                }
                                if (aSKSState.getEMMode() != -1) {
                                    String hexString = Integer.toHexString(aSKSState.getEMMode());
                                    fastXmlSerializer.startTag(null, "emmode");
                                    fastXmlSerializer.attribute(null, "value", "0x" + hexString);
                                    fastXmlSerializer.endTag(null, "emmode");
                                }
                                if (aSKSState.getDeletable() != null) {
                                    Deletable deletable = aSKSState.getDeletable();
                                    fastXmlSerializer.startTag(null, "delete");
                                    fastXmlSerializer.attribute(null, "version", deletable.getVersion());
                                    fastXmlSerializer.attribute(null, "datelimit", deletable.getDateLimit());
                                    fastXmlSerializer.endTag(null, "delete");
                                }
                                fastXmlSerializer.endTag(null, "package");
                            }
                        }
                        fastXmlSerializer.endTag(null, "asks");
                        fastXmlSerializer.endDocument();
                        this.mFile.finishWrite(startWrite);
                        SystemProperties.set("security.ASKS.policy_version", mASKSPolicyVersion);
                    } catch (IOException e2) {
                        Slog.w("AASA_ASKSManager", "Failed to write state, restoring backup", e2);
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
                } catch (IOException e4) {
                    e = e4;
                    fileOutputStream = startWrite;
                    Slog.w("AASA_ASKSManager", "Failed to write state: " + e);
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e5) {
                            e = e5;
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                fileOutputStream = startWrite;
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e6) {
                        e6.printStackTrace();
                    }
                }
                throw th;
            }
        }
    }

    public final ASKSSession openSession(String str) {
        if (this.mSessions.containsKey(str)) {
            ASKSSession aSKSSession = (ASKSSession) this.mSessions.get(str);
            aSKSSession.clear();
            aSKSSession.setPackageName(str);
            return aSKSSession;
        }
        ASKSSession aSKSSession2 = new ASKSSession();
        aSKSSession2.setPackageName(str);
        this.mSessions.put(str, aSKSSession2);
        return aSKSSession2;
    }

    public final void closeSession(ASKSSession aSKSSession, String str) {
        if (aSKSSession != null) {
            aSKSSession.clear();
        }
        if (this.mSessions.containsKey(str)) {
            this.mSessions.remove(str);
        }
    }

    public final void updateASKSNotification() {
        NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService("notification");
        if (notificationManager == null) {
            return;
        }
        notificationManager.notifyAsUser(null, 1, new Notification.Builder(this.mContext, SystemNotificationChannels.ASKS).setSmallIcon(R.drawable.$progress_indeterminate_horizontal_material__46).setWhen(0L).setOngoing(true).setContentTitle("This is non-product binary").setVisibility(1).setContentText("This binary can be used until [" + SystemProperties.get("security.ASKS.expiration_date") + "]").build(), UserHandle.ALL);
    }

    public final class ASKSState {
        public Deletable deletable;
        public int emMode;
        public Restrict restrict;
        public int uid;

        public ASKSState() {
            this.uid = -1;
            this.restrict = null;
            this.emMode = -1;
            this.deletable = null;
        }

        public ASKSState(ASKSSession aSKSSession) {
            this.uid = -1;
            this.restrict = null;
            this.emMode = -1;
            this.deletable = null;
            this.restrict = aSKSSession.getRestrict();
            this.emMode = aSKSSession.getEMMode();
            this.deletable = aSKSSession.getDeletable();
        }

        public void setRestrict(Restrict restrict) {
            this.restrict = restrict;
        }

        public void setEMMode(int i) {
            this.emMode = i;
        }

        public void setDeletable(Deletable deletable) {
            this.deletable = deletable;
        }

        public Restrict getRestrict() {
            return this.restrict;
        }

        public int getEMMode() {
            return this.emMode;
        }

        public Deletable getDeletable() {
            return this.deletable;
        }

        public boolean hasValue() {
            return (this.restrict == null && this.emMode == -1 && this.deletable == null) ? false : true;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("ASKSState info {");
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

    public final class ASKSSession {
        public String mPackageName = null;
        public String mVersion = null;
        public String mPackageNameHash = "";
        public String mPackageDigest = "";
        public String mCodePath = "";
        public String mTokenName = "";
        public String mCertName = "";
        public String mCAKeyIndex = "";
        public Signature[] mSignature = null;
        public boolean isASKSTarget = false;
        public Restrict mRestrict = null;
        public int emMode = -1;
        public Deletable mDeletable = null;
        public RUFSContainer mRufsContainer = null;

        public void setPackageName(String str) {
            this.mPackageName = str;
        }

        public void setVersion(String str) {
            this.mVersion = str;
        }

        public void setPkgNameHash(String str) {
            this.mPackageNameHash = str;
        }

        public void setPkgDigest(String str) {
            this.mPackageDigest = str;
        }

        public void setCodePath(String str) {
            this.mCodePath = str;
        }

        public void setTokenName(String str) {
            this.mTokenName = str;
        }

        public void setCertName(String str) {
            this.mCertName = str;
        }

        public void setCAKeyIndex(String str) {
            this.mCAKeyIndex = str;
        }

        public void setSignature(Signature[] signatureArr) {
            this.mSignature = (Signature[]) signatureArr.clone();
        }

        public void setASKSTarget(boolean z) {
            this.isASKSTarget = z;
        }

        public void setRestrict(Restrict restrict) {
            this.mRestrict = restrict;
        }

        public void setEMMode(int i) {
            this.emMode = i;
        }

        public void setDeletable(Deletable deletable) {
            this.mDeletable = deletable;
        }

        public void setRufsContainer(RUFSContainer rUFSContainer) {
            this.mRufsContainer = rUFSContainer;
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public String getPkgNameHash() {
            return this.mPackageNameHash;
        }

        public String getPkgDigest() {
            return this.mPackageDigest;
        }

        public String getCodePath() {
            return this.mCodePath;
        }

        public String getTokenName() {
            return this.mTokenName;
        }

        public String getCertName() {
            return this.mCertName;
        }

        public String getCAKeyIndex() {
            return this.mCAKeyIndex;
        }

        public Signature[] getSignature() {
            return this.mSignature;
        }

        public boolean isASKSTarget() {
            return this.isASKSTarget;
        }

        public Restrict getRestrict() {
            return this.mRestrict;
        }

        public int getEMMode() {
            return this.emMode;
        }

        public Deletable getDeletable() {
            return this.mDeletable;
        }

        public RUFSContainer getRufsContainer() {
            return this.mRufsContainer;
        }

        public void clear() {
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

        public boolean hasValue() {
            return (this.mRestrict == null && this.emMode == -1 && this.mDeletable == null) ? false : true;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("version = ");
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

    public final class Restrict {
        public String mDatelimit;
        public String mFrom;
        public ArrayList mPermissionList;
        public String mType;
        public String mVersion;

        public Restrict() {
            this.mFrom = null;
        }

        public Restrict(String str, String str2, String str3, String str4, ArrayList arrayList) {
            this.mVersion = str;
            this.mType = str2;
            this.mDatelimit = str3;
            this.mFrom = str4;
            this.mPermissionList = arrayList;
        }

        public void setVersion(String str) {
            this.mVersion = str;
        }

        public void setType(String str) {
            this.mType = str;
        }

        public void setDateLimit(String str) {
            this.mDatelimit = str;
        }

        public void setFrom(String str) {
            this.mFrom = str;
        }

        public void setPermissionList(ArrayList arrayList) {
            this.mPermissionList = arrayList;
        }

        public String getVersion() {
            return this.mVersion;
        }

        public String getType() {
            return this.mType;
        }

        public String getDateLimit() {
            return this.mDatelimit;
        }

        public String getFrom() {
            return this.mFrom;
        }

        public ArrayList getPermissionList() {
            return this.mPermissionList;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("version = ");
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

    public final class Deletable {
        public String mDatelimit;
        public String mVersion;

        public Deletable() {
        }

        public Deletable(String str, String str2) {
            this.mVersion = str;
            this.mDatelimit = str2;
        }

        public void setVersion(String str) {
            this.mVersion = str;
        }

        public void setDateLimit(String str) {
            this.mDatelimit = str;
        }

        public String getVersion() {
            return this.mVersion;
        }

        public String getDateLimit() {
            return this.mDatelimit;
        }

        public String toString() {
            return "version = " + this.mVersion + ", datelimit = " + this.mDatelimit;
        }
    }
}
