package com.android.server.knox.dar.sdp.engine;

import android.app.ActivityManager;
import android.app.AppGlobals;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.os.Binder;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import com.android.server.knox.dar.DarManagerService;
import com.android.server.knox.dar.KeyProtector;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knox.sdp.core.SdpDomain;
import com.samsung.android.knox.sdp.core.SdpEngineInfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/* loaded from: classes2.dex */
public class SdpServiceKeeper {
    public static List mWhitelist = new ArrayList();
    public ActivityManager mAM;
    public Context mContext;
    public SdpPolicyDatabase mSdpPolicyDb;
    public UserManager mUM;
    public IPackageManager mPM = null;
    public final Object mSdpPolicyDbLock = new Object();
    public final Object mSdpPolicyMapLock = new Object();
    public Map mSdpPolicyMap = new HashMap();

    public SdpServiceKeeper(DarManagerService.Injector injector) {
        this.mContext = null;
        this.mUM = null;
        this.mAM = null;
        this.mSdpPolicyDb = null;
        Context context = injector.getContext();
        this.mContext = context;
        this.mSdpPolicyDb = new SdpPolicyDatabase(context);
        this.mUM = injector.getUserManager();
        this.mAM = injector.getActivityManager();
        initWhitelist();
    }

    public final SdpDomain getDefaultEngineOwner(String str) {
        return new SdpDomain(str, "system_server");
    }

    public final boolean policyExistsLocked(SdpEngineInfo sdpEngineInfo) {
        return this.mSdpPolicyMap.containsKey(sdpEngineInfo.getAlias());
    }

    public int addPolicy(Context context, int i, int i2, SdpEngineInfo sdpEngineInfo, List list) {
        SdpDomain sdpDomain;
        int putPolicyLocked;
        synchronized (this.mSdpPolicyMapLock) {
            if (policyExistsLocked(sdpEngineInfo)) {
                Log.e("SdpServiceKeeper", "addPolicy :: error, policy[" + sdpEngineInfo.getAlias() + "] already exists!");
                return -4;
            }
            if (sdpEngineInfo.isAndroidDefaultEngine()) {
                if (!isSystemServer(context, i, i2)) {
                    return -7;
                }
                sdpDomain = getDefaultEngineOwner(sdpEngineInfo.getAlias());
            } else {
                if (!sdpEngineInfo.isCustomEngine()) {
                    return -99;
                }
                sdpDomain = new SdpDomain(sdpEngineInfo.getAlias(), sdpEngineInfo.getPackageName());
            }
            SdpPolicy sdpPolicy = new SdpPolicy(sdpEngineInfo, sdpDomain, list);
            synchronized (this.mSdpPolicyDbLock) {
                putPolicyLocked = this.mSdpPolicyDb.putPolicyLocked(sdpEngineInfo, sdpPolicy);
            }
            if (putPolicyLocked == 0) {
                synchronized (this.mSdpPolicyMapLock) {
                    this.mSdpPolicyMap.put(sdpEngineInfo.getAlias(), sdpPolicy);
                }
            }
            return putPolicyLocked;
        }
    }

    public final boolean isSystemServer(Context context, int i, int i2) {
        String str;
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : safe(this.mAM.getRunningAppProcesses())) {
            if (runningAppProcessInfo.pid == i && runningAppProcessInfo.uid == 1000 && (str = runningAppProcessInfo.processName) != null && str.matches("system")) {
                return true;
            }
        }
        return false;
    }

    public static List safe(List list) {
        return list == null ? Collections.EMPTY_LIST : list;
    }

    public SdpPolicy loadPolicy(SdpEngineInfo sdpEngineInfo) {
        SdpPolicy policyLocked;
        synchronized (this.mSdpPolicyDbLock) {
            policyLocked = this.mSdpPolicyDb.getPolicyLocked(sdpEngineInfo);
        }
        if (policyLocked == null) {
            return null;
        }
        Log.d("SdpServiceKeeper", "loadPolicy :: " + policyLocked.toString());
        synchronized (this.mSdpPolicyMapLock) {
            this.mSdpPolicyMap.put(sdpEngineInfo.getAlias(), policyLocked);
        }
        return policyLocked;
    }

    public int removePolicy(Context context, int i, int i2, SdpEngineInfo sdpEngineInfo) {
        synchronized (this.mSdpPolicyDbLock) {
            this.mSdpPolicyDb.removePolicyLocked(sdpEngineInfo);
        }
        synchronized (this.mSdpPolicyMapLock) {
            this.mSdpPolicyMap.remove(sdpEngineInfo.getAlias());
        }
        return 0;
    }

    public final int updatePolicy(SdpEngineInfo sdpEngineInfo, SdpPolicy sdpPolicy) {
        int putPolicyLocked;
        synchronized (this.mSdpPolicyDbLock) {
            putPolicyLocked = this.mSdpPolicyDb.putPolicyLocked(sdpEngineInfo, sdpPolicy);
        }
        if (putPolicyLocked == 0) {
            synchronized (this.mSdpPolicyMapLock) {
                this.mSdpPolicyMap.put(sdpEngineInfo.getAlias(), sdpPolicy);
            }
        }
        return putPolicyLocked;
    }

    public int addPrivilegedApp(Context context, int i, int i2, SdpEngineInfo sdpEngineInfo, SdpDomain sdpDomain) {
        SdpPolicy sdpPolicyLocked;
        int i3;
        if (!isEngineOwner(context, i, i2, sdpEngineInfo)) {
            return -7;
        }
        synchronized (this.mSdpPolicyMapLock) {
            sdpPolicyLocked = getSdpPolicyLocked(sdpEngineInfo.getAlias());
            i3 = (sdpPolicyLocked == null || !sdpPolicyLocked.addPrivilegedApp(sdpDomain)) ? -99 : 0;
        }
        return i3 == 0 ? updatePolicy(sdpEngineInfo, sdpPolicyLocked) : i3;
    }

    public int removePrivilegedApp(Context context, int i, int i2, SdpEngineInfo sdpEngineInfo, SdpDomain sdpDomain) {
        SdpPolicy sdpPolicyLocked;
        int i3;
        if (!isEngineOwner(context, i, i2, sdpEngineInfo)) {
            return -7;
        }
        synchronized (this.mSdpPolicyMapLock) {
            sdpPolicyLocked = getSdpPolicyLocked(sdpEngineInfo.getAlias());
            i3 = (sdpPolicyLocked == null || !sdpPolicyLocked.removePrivilegedApp(sdpDomain)) ? -99 : 0;
        }
        return i3 == 0 ? updatePolicy(sdpEngineInfo, sdpPolicyLocked) : i3;
    }

    public final SdpPolicy getSdpPolicyLocked(String str) {
        if (this.mSdpPolicyMap.containsKey(str)) {
            return (SdpPolicy) this.mSdpPolicyMap.get(str);
        }
        return null;
    }

    public final String getPackageName(Context context, int i, int i2) {
        String str;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                str = ((ActivityManager) this.mContext.getSystemService("activity")).getPackageFromAppProcesses(i);
            } catch (Exception e) {
                Log.e("SdpServiceKeeper", "getPackageName exception: " + e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                str = null;
            }
            return str;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final IPackageManager getIPackageManager() {
        IPackageManager iPackageManager = this.mPM;
        if (iPackageManager != null) {
            return iPackageManager;
        }
        IPackageManager packageManager = AppGlobals.getPackageManager();
        this.mPM = packageManager;
        return packageManager;
    }

    public final boolean isSystemApp(String str, int i) {
        Signature[] signatureArr;
        if (str == null) {
            return false;
        }
        try {
            if (this.mPM != null) {
                PackageInfo packageInfo = getIPackageManager().getPackageInfo(str, 64L, i);
                PackageInfo packageInfo2 = getIPackageManager().getPackageInfo("android", 64L, i);
                if (packageInfo == null || (signatureArr = packageInfo.signatures) == null || packageInfo2 == null) {
                    return false;
                }
                return packageInfo2.signatures[0].equals(signatureArr[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public final boolean isWhitelisted(String str) {
        if (str == null) {
            return false;
        }
        Iterator it = mWhitelist.iterator();
        while (it.hasNext()) {
            if (str.equals((String) it.next())) {
                return true;
            }
        }
        return false;
    }

    public final void initWhitelist() {
        mWhitelist.add("com.samsung.android.email.provider");
        mWhitelist.add("com.samsung.android.spay");
        mWhitelist.add(KnoxCustomManagerService.SBROWSER_CHAMELEON_PACKAGE_NAME);
    }

    public final boolean checkPermission(Context context) {
        return context != null && enforceCallingPermission(context, "com.samsung.android.knox.permission.KNOX_SENSITIVE_DATA_PROTECTION");
    }

    public final boolean enforceCallingPermission(Context context, String str) {
        try {
            context.enforceCallingPermission(str, null);
            return true;
        } catch (SecurityException unused) {
            return false;
        }
    }

    public boolean isSystemComponent(Context context, int i, int i2, SdpEngineInfo sdpEngineInfo) {
        return isSystemServer(context, i, i2) || isSystemApp(getPackageName(context, i, i2), sdpEngineInfo.getUserId());
    }

    public boolean isLicensed(Context context, int i, int i2) {
        String packageName = getPackageName(context, i, i2);
        int userId = UserHandle.getUserId(i2);
        Log.d("SdpServiceKeeper", String.format("Check permission { Package : %s, PID : %d, UID : %d, UserId : %d }", packageName, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(userId)));
        if (isWhitelisted(packageName)) {
            Log.d("SdpServiceKeeper", "Identified as whitelisted");
            return true;
        }
        if (isSystemApp(packageName, userId)) {
            Log.d("SdpServiceKeeper", "Identified as system app");
            return true;
        }
        boolean checkPermission = checkPermission(context);
        if (!checkPermission) {
            Log.e("SdpServiceKeeper", "License activation required");
        }
        return checkPermission;
    }

    public boolean isEngineOwner(Context context, int i, int i2, SdpEngineInfo sdpEngineInfo) {
        SdpPolicy sdpPolicyLocked;
        String packageName = getPackageName(context, i, i2);
        if (isSystemServer(context, i, i2) || isSystemApp(packageName, UserHandle.getUserId(i2))) {
            return true;
        }
        if (!sdpEngineInfo.isAndroidDefaultEngine() && sdpEngineInfo.isCustomEngine()) {
            synchronized (this.mSdpPolicyMapLock) {
                sdpPolicyLocked = getSdpPolicyLocked(sdpEngineInfo.getAlias());
            }
            if (sdpPolicyLocked == null) {
                Log.e("SdpServiceKeeper", "can't find policy for " + sdpEngineInfo.getAlias());
                return false;
            }
            try {
                String packageName2 = sdpPolicyLocked.getOwner().getPackageName();
                if (packageName2 != null && packageName != null) {
                    if (packageName.matches(packageName2)) {
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean isPrivileged(Context context, int i, int i2, SdpEngineInfo sdpEngineInfo) {
        String packageName = getPackageName(context, i, i2);
        if (!sdpEngineInfo.isAndroidDefaultEngine() && sdpEngineInfo.isCustomEngine()) {
            synchronized (this.mSdpPolicyMapLock) {
                SdpPolicy sdpPolicyLocked = getSdpPolicyLocked(sdpEngineInfo.getAlias());
                if (sdpPolicyLocked == null) {
                    return false;
                }
                try {
                    List privilegedApps = sdpPolicyLocked.getPrivilegedApps();
                    if (packageName != null && privilegedApps != null) {
                        Iterator it = privilegedApps.iterator();
                        while (it.hasNext()) {
                            if (packageName.matches(((SdpDomain) it.next()).getPackageName())) {
                                return true;
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public class SdpPolicyDatabase {
        public SdpPolicyDatabase(Context context) {
        }

        public final File getPolicyXmlFile(SdpEngineInfo sdpEngineInfo) {
            return new File("/data/system/users/" + sdpEngineInfo.getId() + "/sdp_engine_policy_" + sdpEngineInfo.getAlias() + ".xml");
        }

        public final SdpPolicy getPolicyLocked(SdpEngineInfo sdpEngineInfo) {
            File file;
            PolicyXmlHandler policyXmlHandler = new PolicyXmlHandler(sdpEngineInfo);
            byte[] loadHash = loadHash(sdpEngineInfo);
            try {
                file = getPolicyXmlFile(sdpEngineInfo);
            } catch (IOException e) {
                e.printStackTrace();
                file = null;
            }
            if (file == null || loadHash == null) {
                Log.e("SdpServiceKeeper", "No policy file or its hash");
                return null;
            }
            byte[] generateHash = generateHash(generateSalt(sdpEngineInfo), file);
            if (generateHash != null) {
                Log.d("SdpServiceKeeper", "loadPolicy :: generated hash : " + byteToHex(generateHash));
                Log.d("SdpServiceKeeper", "loadPolicy :: stored hash : " + byteToHex(loadHash));
            }
            if (!Arrays.equals(generateHash, loadHash)) {
                Log.e("SdpServiceKeeper", "Policy file tempered!");
                return null;
            }
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    parseXml(fileInputStream, policyXmlHandler);
                    fileInputStream.close();
                    return policyXmlHandler.getSdpPolicy();
                } finally {
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                Log.e("SdpServiceKeeper", "engine_poilcy xml io failed");
                return null;
            }
        }

        public final int putPolicyLocked(SdpEngineInfo sdpEngineInfo, SdpPolicy sdpPolicy) {
            try {
                File policyXmlFile = getPolicyXmlFile(sdpEngineInfo);
                try {
                    Document newDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
                    Element createElement = newDocument.createElement("engine_policy");
                    newDocument.appendChild(createElement);
                    Element createElement2 = newDocument.createElement("owner");
                    Attr createAttribute = newDocument.createAttribute("alias");
                    Attr createAttribute2 = newDocument.createAttribute("pkg_name");
                    createAttribute.setValue(sdpEngineInfo.getAlias());
                    createElement2.setAttributeNode(createAttribute);
                    createAttribute2.setValue(sdpPolicy.getOwner().getPackageName());
                    createElement2.setAttributeNode(createAttribute2);
                    createElement.appendChild(createElement2);
                    List<SdpDomain> privilegedApps = sdpPolicy.getPrivilegedApps();
                    if (privilegedApps != null && !privilegedApps.isEmpty()) {
                        for (SdpDomain sdpDomain : privilegedApps) {
                            Element createElement3 = newDocument.createElement("privileged_app");
                            Attr createAttribute3 = newDocument.createAttribute("alias");
                            Attr createAttribute4 = newDocument.createAttribute("pkg_name");
                            createAttribute3.setValue(sdpEngineInfo.getAlias());
                            createElement3.setAttributeNode(createAttribute3);
                            createAttribute4.setValue(sdpDomain.getPackageName());
                            createElement3.setAttributeNode(createAttribute4);
                            createElement.appendChild(createElement3);
                        }
                    }
                    Transformer newTransformer = TransformerFactory.newInstance().newTransformer();
                    newTransformer.setOutputProperty("indent", "yes");
                    newTransformer.transform(new DOMSource(newDocument), new StreamResult(policyXmlFile));
                    if (saveHash(sdpEngineInfo, policyXmlFile)) {
                        Log.e("SdpServiceKeeper", "putPolicyLocked :: SUCCESS");
                        return 0;
                    }
                    Log.e("SdpServiceKeeper", "can't store hash to key protector");
                    return -99;
                } catch (ParserConfigurationException | TransformerException e) {
                    e.printStackTrace();
                    Log.e("SdpServiceKeeper", " putPolicyLocked failed");
                    return -99;
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                Log.e("SdpServiceKeeper", " putPolicyLocked failed");
                return -12;
            }
        }

        public final void removePolicyLocked(SdpEngineInfo sdpEngineInfo) {
            try {
                if (!getPolicyXmlFile(sdpEngineInfo).delete()) {
                    Log.e("SdpServiceKeeper", "Couldn't remove policy file!");
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                if (!KeyProtector.getInstance().delete(sdpEngineInfo.getAlias() + "_sdp_policy_hash", sdpEngineInfo.getId())) {
                    Log.e("SdpServiceKeeper", "removePolicyLocked failure");
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("SdpServiceKeeper", " removePolicyLocked  failed");
            }
        }

        public boolean parseXml(InputStream inputStream, DefaultHandler defaultHandler) {
            try {
                XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
                xMLReader.setContentHandler(defaultHandler);
                xMLReader.parse(new InputSource(inputStream));
                return true;
            } catch (IOException | SAXException e) {
                e.printStackTrace();
                return false;
            } catch (ParserConfigurationException e2) {
                e2.printStackTrace();
                return true;
            }
        }

        public class PolicyXmlHandler extends DefaultHandler {
            public String attrAlias;
            public String attrPkgName;
            public SdpEngineInfo mInfo;
            public Stack elementStack = new Stack();
            public List privilegedApps = new ArrayList();
            public SdpDomain owner = null;

            public PolicyXmlHandler(SdpEngineInfo sdpEngineInfo) {
                this.mInfo = null;
                this.mInfo = sdpEngineInfo;
            }

            public final SdpPolicy getSdpPolicy() {
                return new SdpPolicy(this.mInfo, this.owner, this.privilegedApps);
            }

            @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
            public void startDocument() {
                Log.d("SdpServiceKeeper", "start document   : ");
            }

            @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
            public void endDocument() {
                Log.d("SdpServiceKeeper", "end document     : ");
            }

            @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
            public void startElement(String str, String str2, String str3, Attributes attributes) {
                this.elementStack.push(str3);
                Log.d("SdpServiceKeeper", "start element    : " + str3);
                if (str3.equals("privileged_app") || str3.equals("owner")) {
                    Log.d("SdpServiceKeeper", " attribte alias: " + attributes.getValue("alias"));
                    Log.d("SdpServiceKeeper", " attribte pkg_name: " + attributes.getValue("pkg_name"));
                    this.attrAlias = attributes.getValue("alias");
                    this.attrPkgName = attributes.getValue("pkg_name");
                }
            }

            @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
            public void endElement(String str, String str2, String str3) {
                this.elementStack.pop();
                Log.d("SdpServiceKeeper", "end element      : " + str3);
                if (str3.equals("privileged_app")) {
                    if (this.mInfo.getAlias() != null && this.attrAlias.equals(this.mInfo.getAlias())) {
                        this.privilegedApps.add(new SdpDomain(this.attrAlias, this.attrPkgName));
                    }
                } else if (str3.equals("owner") && this.mInfo.getAlias() != null && this.attrAlias.equals(this.mInfo.getAlias())) {
                    this.owner = new SdpDomain(this.attrAlias, this.attrPkgName);
                }
                this.attrAlias = null;
                this.attrPkgName = null;
            }

            @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
            public void characters(char[] cArr, int i, int i2) {
                Log.d("SdpServiceKeeper", "start characters : " + new String(cArr, i, i2));
            }
        }

        /* JADX WARN: Not initialized variable reg: 3, insn: 0x004c: MOVE (r1 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:55:0x004c */
        public final byte[] generateHash(byte[] bArr, File file) {
            FileInputStream fileInputStream;
            FileInputStream fileInputStream2;
            MessageDigest messageDigest;
            FileInputStream fileInputStream3 = null;
            try {
            } catch (Throwable th) {
                th = th;
                fileInputStream3 = fileInputStream;
            }
            try {
                try {
                    messageDigest = MessageDigest.getInstance("SHA-256");
                    fileInputStream2 = new FileInputStream(file);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
                try {
                    byte[] bArr2 = new byte[1024];
                    while (true) {
                        int read = fileInputStream2.read(bArr2);
                        if (read == -1) {
                            break;
                        }
                        messageDigest.update(bArr2, 0, read);
                    }
                    byte[] digest = messageDigest.digest();
                    if (digest.length > 0) {
                        Log.d("SdpServiceKeeper", byteToHex(digest));
                    }
                    try {
                        fileInputStream2.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    return digest;
                } catch (FileNotFoundException e3) {
                    e = e3;
                    Log.w("SdpServiceKeeper", "File Not found...");
                    e.printStackTrace();
                    if (fileInputStream2 != null) {
                        fileInputStream2.close();
                    }
                    return null;
                } catch (NoSuchAlgorithmException unused) {
                    Log.w("SdpServiceKeeper", "Failed to hash : missing algorithm: SHA-256");
                    if (fileInputStream2 != null) {
                        fileInputStream2.close();
                    }
                    return null;
                } catch (Exception e4) {
                    e = e4;
                    e.printStackTrace();
                    if (fileInputStream2 != null) {
                        fileInputStream2.close();
                    }
                    return null;
                }
            } catch (FileNotFoundException e5) {
                e = e5;
                fileInputStream2 = null;
            } catch (NoSuchAlgorithmException unused2) {
                fileInputStream2 = null;
            } catch (Exception e6) {
                e = e6;
                fileInputStream2 = null;
            } catch (Throwable th2) {
                th = th2;
                if (fileInputStream3 != null) {
                    try {
                        fileInputStream3.close();
                    } catch (Exception e7) {
                        e7.printStackTrace();
                    }
                }
                throw th;
            }
        }

        public final boolean saveHash(SdpEngineInfo sdpEngineInfo, File file) {
            byte[] generateHash = generateHash(generateSalt(sdpEngineInfo), file);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            boolean protect = KeyProtector.getInstance().protect(generateHash, sdpEngineInfo.getAlias() + "_sdp_policy_hash", sdpEngineInfo.getId());
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return protect;
        }

        public final byte[] loadHash(SdpEngineInfo sdpEngineInfo) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            byte[] release = KeyProtector.getInstance().release(sdpEngineInfo.getAlias() + "_sdp_policy_hash", sdpEngineInfo.getId());
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return release;
        }

        public final byte[] generateSalt(SdpEngineInfo sdpEngineInfo) {
            byte[] bArr = new byte[32];
            Arrays.fill(bArr, (byte) 0);
            return bArr;
        }

        public final String byteToHex(byte[] bArr) {
            StringBuilder sb = new StringBuilder();
            for (byte b : bArr) {
                sb.append(String.format("%02X", Byte.valueOf(b)));
            }
            return sb.toString();
        }
    }
}
