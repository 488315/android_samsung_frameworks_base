package com.android.server.enterprise.utils;

import android.app.ActivityManager;
import android.app.AppGlobals;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Process;
import android.os.RemoteException;
import android.os.SemSystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.am.OomAdjuster$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseDeviceManagerService;
import com.android.server.enterprise.EnterpriseDeviceManagerServiceImpl;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.adapter.AdapterRegistry;
import com.android.server.enterprise.adapter.IPersonaManagerAdapter;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;
import com.android.server.enterprise.adapterlayer.PersonaManagerAdapter;
import com.android.server.enterprise.storage.EdmStorageProvider;

import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class Utils {
    public static final char[] HEX_DIGITS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };
    public static final String[] standardPermissions = {
        "com.samsung.android.knox.permission.KNOX_APN",
        "com.samsung.android.knox.permission.KNOX_INVENTORY",
        "com.samsung.android.knox.permission.KNOX_APP_MGMT",
        "com.samsung.android.knox.permission.KNOX_KIOSK_MODE",
        "com.samsung.android.knox.permission.KNOX_BLUETOOTH",
        "com.samsung.android.knox.permission.KNOX_LDAP",
        "com.samsung.android.knox.permission.KNOX_BLUETOOTH_SECUREMODE",
        "com.samsung.android.knox.permission.KNOX_LOCATION",
        "com.samsung.android.knox.permission.KNOX_BROWSER_PROXY",
        "com.samsung.android.knox.permission.KNOX_LOCKSCREEN",
        "com.samsung.android.knox.permission.KNOX_BROWSER_SETTINGS",
        "com.samsung.android.knox.permission.KNOX_MULTI_USER_MGMT",
        "com.samsung.android.knox.permission.KNOX_CERT_PROVISIONING",
        "com.samsung.android.knox.permission.KNOX_PHONE_RESTRICTION",
        "com.samsung.android.knox.permission.KNOX_CLIPBOARD",
        "com.samsung.android.knox.permission.KNOX_DATE_TIME",
        "com.samsung.android.knox.permission.KNOX_REMOTE_CONTROL",
        KnoxCustomManagerService.KNOX_DEX_PERMISSION,
        "com.samsung.android.knox.permission.KNOX_RESTRICTION_MGMT",
        "com.samsung.android.knox.permission.KNOX_EMAIL",
        "com.samsung.android.knox.permission.KNOX_ROAMING",
        "com.samsung.android.knox.permission.KNOX_ENTERPRISE_DEVICE_ADMIN",
        "com.samsung.android.knox.permission.KNOX_SECURITY",
        "com.samsung.android.knox.permission.KNOX_EXCHANGE",
        "com.samsung.android.knox.permission.KNOX_SENSITIVE_DATA_PROTECTION",
        "com.samsung.android.knox.permission.KNOX_FIREWALL",
        "com.samsung.android.knox.permission.KNOX_SPDCONTROL",
        "com.samsung.android.knox.permission.KNOX_GEOFENCING",
        "com.samsung.android.knox.permission.KNOX_VPN",
        "com.samsung.android.knox.permission.KNOX_GLOBALPROXY",
        "com.samsung.android.knox.permission.KNOX_WIFI",
        "com.samsung.android.knox.permission.KNOX_HW_CONTROL",
        "com.samsung.android.knox.permission.KNOX_ENHANCED_ATTESTATION"
    };
    public static final String[] premiumPermissions = {
        "com.samsung.android.knox.permission.KNOX_ADVANCED_APP_MGMT",
        "com.samsung.android.knox.permission.KNOX_DLP_MGMT",
        "com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION",
        "com.samsung.android.knox.permission.KNOX_EBILLING",
        "com.samsung.android.knox.permission.KNOX_ADVANCED_SECURITY",
        "com.samsung.android.knox.permission.KNOX_NPA",
        "com.samsung.android.knox.permission.KNOX_AUDIT_LOG",
        "com.samsung.android.knox.permission.KNOX_SEAMS_MGMT",
        "com.samsung.android.knox.permission.KNOX_CCM_KEYSTORE",
        "com.samsung.android.knox.permission.KNOX_TIMA_KEYSTORE",
        "com.samsung.android.knox.permission.KNOX_CERTIFICATE",
        "com.samsung.android.knox.permission.KNOX_TIMA_KEYSTORE_PER_APP",
        "com.samsung.android.knox.permission.KNOX_CERTIFICATE_ENROLLMENT",
        "com.samsung.android.knox.permission.KNOX_UCM_MGMT",
        "com.samsung.android.knox.permission.KNOX_CONTAINER",
        "com.samsung.android.knox.permission.KNOX_VPN_CONTAINER",
        "com.samsung.android.knox.permission.KNOX_CONTAINER_RCP",
        "com.samsung.android.knox.permission.KNOX_VPN_GENERIC",
        "com.samsung.android.knox.permission.KNOX_MOBILE_THREAT_DEFENSE",
        "com.samsung.android.knox.permission.KNOX_DEVICE_CONFIGURATION",
        "com.samsung.android.knox.permission.KNOX_HDM",
        "com.samsung.android.knox.permission.KNOX_CRITICAL_COMMUNICATIONS"
    };
    public static final String[] customPermissions = {
        "com.samsung.android.knox.permission.CUSTOM_PROKIOSK",
        "com.samsung.android.knox.permission.CUSTOM_SETTING",
        "com.samsung.android.knox.permission.CUSTOM_SYSTEM",
        KnoxCustomManagerService.KNOX_CUSTOM_DEX_PERMISSION
    };
    public static final int MY_PID = Process.myPid();

    /* JADX WARN: Removed duplicated region for block: B:15:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0073  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean comparePackageSignature(
            int r7, android.content.Context r8, java.lang.String r9, java.lang.String r10) {
        /*
            r0 = 0
            if (r10 == 0) goto L8a
            if (r9 != 0) goto L7
            goto L8a
        L7:
            long r1 = android.os.Binder.clearCallingIdentity()
            r3 = 134217728(0x8000000, float:3.85186E-34)
            r4 = 64
            r5 = 0
            if (r7 != 0) goto L2c
            android.content.pm.PackageManager r7 = r8.getPackageManager()     // Catch: java.lang.Throwable -> L24 android.content.pm.PackageManager.NameNotFoundException -> L29
            android.content.pm.PackageInfo r7 = r7.getPackageInfo(r9, r4)     // Catch: java.lang.Throwable -> L24 android.content.pm.PackageManager.NameNotFoundException -> L29
            android.content.pm.PackageManager r8 = r8.getPackageManager()     // Catch: java.lang.Throwable -> L24 android.content.pm.PackageManager.NameNotFoundException -> L27
            android.content.pm.PackageInfo r8 = r8.getPackageInfo(r9, r3)     // Catch: java.lang.Throwable -> L24 android.content.pm.PackageManager.NameNotFoundException -> L27
            r5 = r8
            goto L3e
        L24:
            r7 = move-exception
            goto L86
        L27:
            r8 = move-exception
            goto L44
        L29:
            r8 = move-exception
            r7 = r5
            goto L44
        L2c:
            android.content.pm.PackageManager r6 = r8.getPackageManager()     // Catch: java.lang.Throwable -> L24 android.content.pm.PackageManager.NameNotFoundException -> L29
            android.content.pm.PackageInfo r4 = r6.getPackageInfoAsUser(r9, r4, r7)     // Catch: java.lang.Throwable -> L24 android.content.pm.PackageManager.NameNotFoundException -> L29
            android.content.pm.PackageManager r8 = r8.getPackageManager()     // Catch: java.lang.Throwable -> L24 android.content.pm.PackageManager.NameNotFoundException -> L42
            android.content.pm.PackageInfo r7 = r8.getPackageInfoAsUser(r9, r3, r7)     // Catch: java.lang.Throwable -> L24 android.content.pm.PackageManager.NameNotFoundException -> L42
            r5 = r7
            r7 = r4
        L3e:
            android.os.Binder.restoreCallingIdentity(r1)
            goto L48
        L42:
            r8 = move-exception
            r7 = r4
        L44:
            r8.printStackTrace()     // Catch: java.lang.Throwable -> L24
            goto L3e
        L48:
            r8 = 1
            if (r7 == 0) goto L65
            android.content.pm.Signature[] r7 = r7.signatures
            if (r7 == 0) goto L65
            int r9 = r7.length
            r1 = r0
        L51:
            if (r1 >= r9) goto L65
            r2 = r7[r1]
            if (r2 == 0) goto L62
            java.lang.String r2 = r2.toCharsString()
            boolean r2 = r10.equals(r2)
            if (r2 == 0) goto L62
            return r8
        L62:
            int r1 = r1 + 1
            goto L51
        L65:
            if (r5 == 0) goto L85
            android.content.pm.SigningInfo r7 = r5.signingInfo
            if (r7 == 0) goto L85
            android.content.pm.Signature[] r7 = r7.getApkContentsSigners()
            int r9 = r7.length
            r1 = r0
        L71:
            if (r1 >= r9) goto L85
            r2 = r7[r1]
            if (r2 == 0) goto L82
            java.lang.String r2 = r2.toCharsString()
            boolean r2 = r10.equals(r2)
            if (r2 == 0) goto L82
            return r8
        L82:
            int r1 = r1 + 1
            goto L71
        L85:
            return r0
        L86:
            android.os.Binder.restoreCallingIdentity(r1)
            throw r7
        L8a:
            java.lang.String r7 = "EnterpriseUtils"
            java.lang.String r8 = "Invalid arguments"
            android.util.Log.d(r7, r8)
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.utils.Utils.comparePackageSignature(int,"
                    + " android.content.Context, java.lang.String, java.lang.String):boolean");
    }

    public static boolean compareSystemSignature(Context context, Signature[] signatureArr) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo("android", 64);
            if (packageInfo == null) {
                Log.e("EnterpriseUtils", "Could not Read package info");
            }
            if (packageInfo != null && packageInfo.signatures != null && signatureArr != null) {
                HashSet hashSet = new HashSet();
                for (Signature signature : packageInfo.signatures) {
                    hashSet.add(signature);
                }
                HashSet hashSet2 = new HashSet();
                for (Signature signature2 : signatureArr) {
                    hashSet2.add(signature2);
                }
                if (hashSet.equals(hashSet2)) {
                    return true;
                }
            }
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List convertStringToList(String str) {
        return !TextUtils.isEmpty(str)
                ? new ArrayList(Arrays.asList(str.split(",")))
                : new ArrayList();
    }

    public static Context createContextAsUser(Context context, String str, int i, int i2) {
        Log.d("EnterpriseUtils", "Creating context as user " + i2);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                return context.createPackageContextAsUser(str, i, new UserHandle(i2));
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(
                        "EnterpriseUtils",
                        "Package name " + str + " could not be found! " + e.getMessage());
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return null;
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static Object deserializeObject(byte[] bArr) {
        try {
            ObjectInputStream objectInputStream =
                    new ObjectInputStream(new ByteArrayInputStream(bArr));
            Object readObject = objectInputStream.readObject();
            objectInputStream.close();
            return readObject;
        } catch (IOException | ClassNotFoundException unused) {
            return null;
        }
    }

    public static int getAdminUidForEthernetOnly(EdmStorageProvider edmStorageProvider) {
        int adminByField =
                edmStorageProvider.getAdminByField(
                        "DEX_POLICY", "ethernetOnlyEnabled", Integer.toString(1));
        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(
                adminByField, "getAdminUidForEthernetOnly - ", "EnterpriseUtils");
        return adminByField;
    }

    public static ArrayList getAllUsers(Context context) {
        UserManager userManager = (UserManager) context.getSystemService("user");
        ArrayList arrayList = new ArrayList();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Iterator it = userManager.getUsers().iterator();
                while (it.hasNext()) {
                    arrayList.add(
                            Integer.valueOf(
                                    ((UserInfo) it.next()).getUserHandle().getIdentifier()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return arrayList;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static ArrayList getApplicationPubKeyMD5(Context context, String str) {
        if (str == null || context == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        int callingUid = Binder.getCallingUid();
        ArrayList arrayList2 = new ArrayList();
        PackageManagerAdapter packageManagerAdapter = PackageManagerAdapter.getInstance(context);
        int userId = UserHandle.getUserId(callingUid);
        packageManagerAdapter.getClass();
        PackageInfo packageInfo = PackageManagerAdapter.getPackageInfo(64, userId, str);
        if (packageInfo != null && packageInfo.signatures != null) {
            int i = 0;
            while (true) {
                Signature[] signatureArr = packageInfo.signatures;
                if (i >= signatureArr.length) {
                    break;
                }
                byte[] encodedPubKey = getEncodedPubKey(signatureArr[i]);
                if (encodedPubKey != null) {
                    arrayList2.add(Base64.encodeToString(encodedPubKey, 11));
                }
                i++;
            }
        }
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            byte[] bytes = ((String) it.next()).getBytes();
            byte[] digest = bytes != null ? MessageDigest.getInstance("MD5").digest(bytes) : null;
            if (digest != null) {
                arrayList.add(Base64.encodeToString(digest, 11));
            }
        }
        return arrayList;
    }

    public static ArrayList getApplicationSignaturesSHA256(Context context, String str) {
        byte[] digest;
        if (context == null || str == null) {
            return null;
        }
        int callingUid = Binder.getCallingUid();
        ArrayList arrayList = new ArrayList();
        PackageManagerAdapter packageManagerAdapter = PackageManagerAdapter.getInstance(context);
        int userId = UserHandle.getUserId(callingUid);
        packageManagerAdapter.getClass();
        PackageInfo packageInfo = PackageManagerAdapter.getPackageInfo(64, userId, str);
        if (packageInfo != null && packageInfo.signatures != null) {
            int i = 0;
            while (true) {
                Signature[] signatureArr = packageInfo.signatures;
                if (i >= signatureArr.length) {
                    break;
                }
                byte[] encodedPubKey = getEncodedPubKey(signatureArr[i]);
                if (encodedPubKey != null
                        && (digest = MessageDigest.getInstance("SHA-256").digest(encodedPubKey))
                                != null) {
                    StringBuilder sb = new StringBuilder(digest.length * 2);
                    for (int i2 = 0; i2 < digest.length; i2++) {
                        String hexString = Integer.toHexString(digest[i2]);
                        int length = hexString.length();
                        if (length == 1) {
                            hexString = "0".concat(hexString);
                        }
                        if (length > 2) {
                            hexString = hexString.substring(length - 2, length);
                        }
                        sb.append(hexString.toUpperCase());
                        if (i2 < digest.length - 1) {
                            sb.append(':');
                        }
                    }
                    arrayList.add(sb.toString());
                }
                i++;
            }
        }
        return arrayList;
    }

    public static int getCallingOrCurrentUserId(ContextInfo contextInfo) {
        if (contextInfo == null) {
            contextInfo = new ContextInfo(Binder.getCallingUid());
        }
        if (contextInfo.mParent) {
            return 0;
        }
        IPersonaManagerAdapter iPersonaManagerAdapter =
                (IPersonaManagerAdapter)
                        AdapterRegistry.mAdapterHandles.get(IPersonaManagerAdapter.class);
        int i = contextInfo.mContainerId;
        ((PersonaManagerAdapter) iPersonaManagerAdapter).getClass();
        if (SemPersonaManager.isKnoxId(i)) {
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("getCallingOrCurrentUserId(): move: cxtInfo.mContainerId = "),
                    contextInfo.mContainerId,
                    "EnterpriseUtils");
            return contextInfo.mContainerId;
        }
        if (MY_PID != Binder.getCallingPid()) {
            return UserHandle.getUserId(contextInfo.mCallerUid);
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return ActivityManager.getCurrentUser();
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public static int getCallingOrUserUid(ContextInfo contextInfo) {
        if (contextInfo == null) {
            return Binder.getCallingUid();
        }
        if (contextInfo.mParent) {
            int i = EnterpriseDeviceManagerService.$r8$clinit;
            return ((EnterpriseDeviceManagerServiceImpl)
                            ((EnterpriseDeviceManagerService) EnterpriseService.sEdmsInstance))
                    .mPseudoAdminUid;
        }
        IPersonaManagerAdapter iPersonaManagerAdapter =
                (IPersonaManagerAdapter)
                        AdapterRegistry.mAdapterHandles.get(IPersonaManagerAdapter.class);
        int i2 = contextInfo.mContainerId;
        ((PersonaManagerAdapter) iPersonaManagerAdapter).getClass();
        return SemPersonaManager.isKnoxId(i2)
                ? UserHandle.getUid(contextInfo.mContainerId, contextInfo.mCallerUid)
                : contextInfo.mCallerUid;
    }

    public static byte[] getEncodedPubKey(Signature signature) {
        Certificate generateCertificate;
        PublicKey publicKey;
        if (signature == null
                || (generateCertificate =
                                CertificateFactory.getInstance("X.509")
                                        .generateCertificate(
                                                new ByteArrayInputStream(signature.toByteArray())))
                        == null
                || (publicKey = generateCertificate.getPublicKey()) == null) {
            return null;
        }
        return publicKey.getEncoded();
    }

    public static int getProxyAdminOwnerUid(EdmStorageProvider edmStorageProvider, int i) {
        Integer asInteger;
        ContentValues contentValues = new ContentValues();
        contentValues.put("proxyUid", Integer.valueOf(i));
        ArrayList arrayList =
                (ArrayList)
                        edmStorageProvider.getValues(
                                "PROXY_ADMIN_INFO", new String[] {"adminUid"}, contentValues);
        return (arrayList.isEmpty()
                        || (asInteger = ((ContentValues) arrayList.get(0)).getAsInteger("adminUid"))
                                == null)
                ? i
                : asInteger.intValue();
    }

    public static boolean hasPermission(Context context, String str, String[] strArr) {
        for (String str2 : strArr) {
            if (context != null
                    && str != null
                    && !str.isEmpty()
                    && str2 != null
                    && !str2.isEmpty()) {
                IPackageManager packageManager = AppGlobals.getPackageManager();
                int callingUid = Binder.getCallingUid();
                if (packageManager != null) {
                    try {
                        if (packageManager.checkPermission(
                                        str2, str, UserHandle.getUserId(callingUid))
                                == 0) {
                            return true;
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    continue;
                }
            }
        }
        return false;
    }

    public static boolean isDexActivated(Context context) {
        SemDesktopModeManager semDesktopModeManager =
                (SemDesktopModeManager) context.getSystemService("desktopmode");
        return semDesktopModeManager != null
                && semDesktopModeManager.getDesktopModeState().enabled == 4;
    }

    public static boolean isEthernetOnlyApplied(EdmStorageProvider edmStorageProvider) {
        boolean z = false;
        try {
            Iterator it =
                    edmStorageProvider
                            .getBooleanListAsUser(0, "DEX_POLICY", "ethernetOnlyApplied")
                            .iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (((Boolean) it.next()).booleanValue()) {
                    z = true;
                    break;
                }
            }
        } catch (Exception e) {
            OomAdjuster$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("isEthernetOnlyApplied : failed "), "EnterpriseUtils");
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                "isEthernetOnlyApplied - ", "EnterpriseUtils", z);
        return z;
    }

    public static boolean isPlatformSignedApp(Context context, String str, int i) {
        PackageInfo packageInfo;
        if (str == null) {
            return false;
        }
        try {
            PackageManagerAdapter.getInstance(context).getClass();
            packageInfo = PackageManagerAdapter.getPackageInfo(64, i, str);
        } catch (Exception e) {
            e.printStackTrace();
            packageInfo = null;
        }
        if (packageInfo == null) {
            Log.d("EnterpriseUtils", "Couldnt get Package Info for package ".concat(str));
            return false;
        }
        try {
            return compareSystemSignature(context, packageInfo.signatures);
        } catch (RemoteException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static String readPropertyValue(String str) {
        String readLine;
        if (!BatteryService$$ExternalSyntheticOutline0.m45m("/data/system/SimCard.dat")) {
            Log.d("EnterpriseUtils", "File Not Found : /data/system/SimCard.dat");
            return null;
        }
        try {
            FileReader fileReader = new FileReader("/data/system/SimCard.dat");
            BufferedReader bufferedReader = new BufferedReader(fileReader, 512);
            do {
                try {
                    try {
                        readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            try {
                                fileReader.close();
                                bufferedReader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                    } catch (Throwable th) {
                        try {
                            fileReader.close();
                            bufferedReader.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        throw th;
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                    try {
                        fileReader.close();
                        bufferedReader.close();
                    } catch (IOException e4) {
                        e4.printStackTrace();
                    }
                    return null;
                }
            } while (!readLine.split("=")[0].equals(str));
            String[] split = readLine.split("=");
            String str2 = split.length == 2 ? split[1] : "";
            String str3 = str2.equals("null") ? null : str2;
            try {
                fileReader.close();
                bufferedReader.close();
            } catch (IOException e5) {
                e5.printStackTrace();
            }
            return str3;
        } catch (FileNotFoundException e6) {
            e6.printStackTrace();
            Log.e("EnterpriseUtils", "File access error /data/system/SimCard.dat");
            return null;
        }
    }

    public static byte[] serializeObject(Object obj) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            objectOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setSystemProperty(String str, String str2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SemSystemProperties.set(str, str2);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00d5 A[Catch: IOException -> 0x00d1, TRY_LEAVE, TryCatch #9 {IOException -> 0x00d1, blocks: (B:53:0x00cd, B:43:0x00d5), top: B:52:0x00cd }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:51:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00cd A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00ed A[Catch: IOException -> 0x00e9, TRY_LEAVE, TryCatch #0 {IOException -> 0x00e9, blocks: (B:68:0x00e5, B:58:0x00ed), top: B:67:0x00e5 }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:66:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00e5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void writePropertyValue(
            java.lang.String r11, java.lang.String r12, java.lang.String r13) {
        /*
            Method dump skipped, instructions count: 250
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.enterprise.utils.Utils.writePropertyValue(java.lang.String,"
                    + " java.lang.String, java.lang.String):void");
    }
}
