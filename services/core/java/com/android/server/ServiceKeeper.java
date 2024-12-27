package com.android.server;

import android.content.pm.IPackageManager;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.os.Environment;
import android.os.ServiceManager;
import com.android.server.asks.ASKSManagerService;
import com.samsung.android.knox.seams.SEAMSPolicy;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.helpers.DefaultHandler;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ServiceKeeper extends DefaultHandler {
    public static ArrayList filteredAPIs;
    public static boolean isActive;
    public static ASKSManagerService mASKS;
    public static IPackageManager mPm;
    public static HashSet openMethodCache;
    public static ServiceKeeper serviceKeeper;
    public static Hashtable serviceTable;
    public static final SKLogger mSKLog = SKLogger.getLogger();
    public static final File AUTHORIZE_POLICY_FILE = new File(Environment.getVendorDirectory(), "/etc/selinux/sk/authorize.xml");
    public static final Object loadFilesLockObject = new Object();

    public static boolean authorizeLoadProcedure() {
        synchronized (loadFilesLockObject) {
            try {
                if (isActive) {
                    return true;
                }
                filteredAPIs = new ArrayList();
                return loadFromAuthorizeFile(AUTHORIZE_POLICY_FILE.getPath());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static boolean checkForMethodAuthorization(String str, String str2, String str3, String str4) {
        Hashtable hashtable = serviceTable;
        if (hashtable == null) {
            SKLogger sKLogger = mSKLog;
            StringBuilder m = InitialConfiguration$$ExternalSyntheticOutline0.m("Checking the method : ServiceTable is Null: service =  ", str, " method = ", str4, " seInfo = ");
            m.append(str2);
            m.append(" packageName = ");
            m.append(str3);
            String sb = m.toString();
            sKLogger.getClass();
            SKLogger.logAll("ServiceKeeper", sb);
            return false;
        }
        if (hashtable.get(str) == null) {
            if (SEAMSPolicy.DEBUG) {
                SKLogger sKLogger2 = mSKLog;
                StringBuilder m2 = InitialConfiguration$$ExternalSyntheticOutline0.m("Checking the method: Service is not in service keeper: service =  ", str, " method = ", str4, " seInfo = ");
                m2.append(str2);
                m2.append(" packageName = ");
                m2.append(str3);
                String sb2 = m2.toString();
                sKLogger2.getClass();
                SKLogger.logAll("ServiceKeeper", sb2);
            }
            return false;
        }
        ServiceObject serviceObject = (ServiceObject) serviceTable.get(str);
        if (serviceObject.isSterileService) {
            if (SEAMSPolicy.DEBUG) {
                SKLogger sKLogger3 = mSKLog;
                StringBuilder m3 = InitialConfiguration$$ExternalSyntheticOutline0.m("Checking the method: Service is sterile : service =  ", str, " method = ", str4, " seInfo = ");
                m3.append(str2);
                m3.append(" packageName = ");
                m3.append(str3);
                String sb3 = m3.toString();
                sKLogger3.getClass();
                SKLogger.logAll("ServiceKeeper", sb3);
            }
            return false;
        }
        Hashtable hashtable2 = serviceObject.serviceMethods;
        if (hashtable2.get(str4) == null) {
            if (SEAMSPolicy.DEBUG) {
                SKLogger sKLogger4 = mSKLog;
                StringBuilder m4 = InitialConfiguration$$ExternalSyntheticOutline0.m("Checking the method: Method not in service: service =  ", str, " method = ", str4, " seInfo = ");
                m4.append(str2);
                m4.append(" packageName = ");
                m4.append(str3);
                String sb4 = m4.toString();
                sKLogger4.getClass();
                SKLogger.logAll("ServiceKeeper", sb4);
            }
            return false;
        }
        MethodPermissionPackage methodPermissionPackage = (MethodPermissionPackage) hashtable2.get(str4);
        if (methodPermissionPackage.isSterileMethod) {
            if (SEAMSPolicy.DEBUG) {
                SKLogger sKLogger5 = mSKLog;
                StringBuilder m5 = InitialConfiguration$$ExternalSyntheticOutline0.m("Checking the method: Method is sterile: service =  ", str, " method = ", str4, " seInfo = ");
                m5.append(str2);
                m5.append(" packageName = ");
                m5.append(str3);
                String sb5 = m5.toString();
                sKLogger5.getClass();
                SKLogger.logAll("ServiceKeeper", sb5);
            }
            return false;
        }
        if (methodPermissionPackage.seinfos.contains(str2)) {
            return true;
        }
        Iterator it = methodPermissionPackage.packages.iterator();
        while (it.hasNext()) {
            PackageObject packageObject = (PackageObject) it.next();
            if (packageObject.packageName.equals(str3) && packageObject.seinfo.equals(str2)) {
                if (SEAMSPolicy.DEBUG) {
                    SKLogger sKLogger6 = mSKLog;
                    StringBuilder m6 = InitialConfiguration$$ExternalSyntheticOutline0.m("Checking the method: Package Name Match: service =  ", str, " method = ", str4, " seInfo = ");
                    m6.append(str2);
                    m6.append(" packageName = ");
                    m6.append(str3);
                    String sb6 = m6.toString();
                    sKLogger6.getClass();
                    SKLogger.logAll("ServiceKeeper", sb6);
                }
                return true;
            }
        }
        return false;
    }

    public static boolean checkForServiceAuthorization(String str, String str2, String str3) {
        Hashtable hashtable = serviceTable;
        if (hashtable == null || hashtable.get(str) == null) {
            return false;
        }
        ServiceObject serviceObject = (ServiceObject) serviceTable.get(str);
        if (serviceObject.isSterileService) {
            return false;
        }
        PermissionPackage permissionPackage = serviceObject.servicePermissions;
        if (permissionPackage.seinfos.contains(str2)) {
            return true;
        }
        Iterator it = permissionPackage.packages.iterator();
        while (it.hasNext()) {
            PackageObject packageObject = (PackageObject) it.next();
            if (packageObject.packageName.equals(str3) && packageObject.seinfo.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0 */
    /* JADX WARN: Type inference failed for: r10v1, types: [int] */
    /* JADX WARN: Type inference failed for: r10v12 */
    /* JADX WARN: Type inference failed for: r14v0, types: [org.w3c.dom.NodeList] */
    /* JADX WARN: Type inference failed for: r15v1 */
    /* JADX WARN: Type inference failed for: r15v2, types: [int] */
    /* JADX WARN: Type inference failed for: r15v4 */
    /* JADX WARN: Type inference failed for: r5v4, types: [org.w3c.dom.NodeList] */
    /* JADX WARN: Type inference failed for: r6v6 */
    /* JADX WARN: Type inference failed for: r6v7, types: [int] */
    /* JADX WARN: Type inference failed for: r6v9 */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v26 */
    /* JADX WARN: Type inference failed for: r7v27 */
    /* JADX WARN: Type inference failed for: r7v4, types: [org.w3c.dom.NodeList] */
    /* JADX WARN: Type inference failed for: r7v6, types: [org.w3c.dom.NodeList] */
    /* JADX WARN: Type inference failed for: r7v9, types: [org.w3c.dom.NodeList] */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v4, types: [int] */
    /* JADX WARN: Type inference failed for: r8v6 */
    /* JADX WARN: Type inference failed for: r8v7, types: [int] */
    public static boolean createAuthorizationTables(Element element) {
        NodeList nodeList;
        Hashtable hashtable;
        HashSet hashSet;
        NodeList nodeList2;
        boolean z;
        Object obj;
        String nodeValue;
        Object obj2;
        try {
            nodeList = element.getElementsByTagName("service");
        } catch (NullPointerException e) {
            SKLogger sKLogger = mSKLog;
            String str = "Exception in getting service list" + e.getMessage();
            sKLogger.getClass();
            SKLogger.logAll(str, e);
            nodeList = null;
        }
        NodeList nodeList3 = nodeList;
        synchronized (ServiceKeeper.class) {
            try {
                if (serviceTable == null) {
                    serviceTable = new Hashtable();
                }
                hashtable = serviceTable;
            } catch (Throwable th) {
                throw th;
            }
        }
        serviceTable = hashtable;
        synchronized (ServiceKeeper.class) {
            try {
                if (openMethodCache == null) {
                    openMethodCache = new HashSet();
                }
                hashSet = openMethodCache;
            } catch (Throwable th2) {
                throw th2;
            }
        }
        openMethodCache = hashSet;
        boolean z2 = true;
        if (nodeList3 != null && nodeList3.getLength() > 0) {
            boolean z3 = false;
            int i = 0;
            while (i < nodeList3.getLength()) {
                Element element2 = (Element) nodeList3.item(i);
                ServiceObject serviceObject = new ServiceObject();
                serviceObject.servicePermissions = new PermissionPackage();
                serviceObject.serviceMethods = new Hashtable();
                serviceObject.isSterileService = z2;
                try {
                    String nodeValue2 = element2.getAttributes().getNamedItem("name").getNodeValue();
                    if ("FilteredAPIs".equals(nodeValue2)) {
                        ?? elementsByTagName = element2.getElementsByTagName("method");
                        if (elementsByTagName.getLength() > 0) {
                            for (?? r6 = z3; r6 < elementsByTagName.getLength(); r6++) {
                                if (elementsByTagName.item(r6).getNodeType() == z2 && elementsByTagName.item(r6).getParentNode().isSameNode(element2)) {
                                    filteredAPIs.add(elementsByTagName.item(r6).getAttributes().getNamedItem("name").getNodeValue());
                                }
                            }
                        }
                        nodeList2 = nodeList3;
                        z = z3;
                    } else {
                        ?? elementsByTagName2 = element2.getElementsByTagName("seinfo");
                        if (elementsByTagName2.getLength() > 0) {
                            serviceObject.isSterileService = z3;
                            for (?? r8 = z3; r8 < elementsByTagName2.getLength(); r8++) {
                                if (elementsByTagName2.item(r8).getNodeType() == z2 && elementsByTagName2.item(r8).getParentNode().isSameNode(element2)) {
                                    serviceObject.servicePermissions.seinfos.add(elementsByTagName2.item(r8).getAttributes().getNamedItem("value").getNodeValue());
                                }
                            }
                        }
                        ?? elementsByTagName3 = element2.getElementsByTagName("package");
                        if (elementsByTagName3.getLength() > 0) {
                            serviceObject.isSterileService = z3;
                            for (?? r82 = z3; r82 < elementsByTagName3.getLength(); r82++) {
                                if (elementsByTagName3.item(r82).getNodeType() == z2 && elementsByTagName3.item(r82).getParentNode().isSameNode(element2)) {
                                    serviceObject.servicePermissions.packages.add(new PackageObject(elementsByTagName3.item(r82).getAttributes().getNamedItem("name").getNodeValue(), elementsByTagName3.item(r82).getAttributes().getNamedItem("seinfo").getNodeValue()));
                                }
                            }
                        }
                        NodeList elementsByTagName4 = element2.getElementsByTagName("method");
                        if (elementsByTagName4.getLength() > 0) {
                            serviceObject.isSterileService = z3;
                            ?? r7 = elementsByTagName4;
                            for (?? r10 = z3; r10 < r7.getLength(); r10++) {
                                Element element3 = (Element) r7.item(r10);
                                MethodPermissionPackage methodPermissionPackage = new MethodPermissionPackage();
                                methodPermissionPackage.isSterileMethod = z2;
                                try {
                                    nodeValue = element3.getAttributes().getNamedItem("name").getNodeValue();
                                    ?? elementsByTagName5 = element3.getElementsByTagName("seinfo");
                                    if (elementsByTagName5 != 0 && elementsByTagName5.getLength() > 0) {
                                        methodPermissionPackage.isSterileMethod = z3;
                                        r7 = r7;
                                        for (?? r15 = z3; r15 < elementsByTagName5.getLength(); r15++) {
                                            NodeList nodeList4 = nodeList3;
                                            methodPermissionPackage.seinfos.add(elementsByTagName5.item(r15).getAttributes().getNamedItem("value").getNodeValue());
                                            if (elementsByTagName5.item(r15).getAttributes().getNamedItem("value").getNodeValue().equals("_open")) {
                                                if (SEAMSPolicy.DEBUG) {
                                                    obj2 = r7;
                                                    mSKLog.getClass();
                                                    SKLogger.logAll("ServiceKeeper", "Adding open method entry - " + nodeValue2 + ":" + nodeValue);
                                                } else {
                                                    obj2 = r7;
                                                }
                                                openMethodCache.add(nodeValue2 + ":" + nodeValue);
                                            } else {
                                                obj2 = r7;
                                            }
                                            nodeList3 = nodeList4;
                                            r7 = obj2;
                                        }
                                    }
                                    nodeList2 = nodeList3;
                                    obj = r7;
                                    NodeList elementsByTagName6 = element3.getElementsByTagName("package");
                                    if (elementsByTagName6 != null && elementsByTagName6.getLength() > 0) {
                                        methodPermissionPackage.isSterileMethod = false;
                                        for (int i2 = 0; i2 < elementsByTagName6.getLength(); i2++) {
                                            methodPermissionPackage.packages.add(new PackageObject(elementsByTagName6.item(i2).getAttributes().getNamedItem("name").getNodeValue(), elementsByTagName6.item(i2).getAttributes().getNamedItem("seinfo").getNodeValue()));
                                        }
                                    }
                                } catch (NullPointerException e2) {
                                    nodeList2 = nodeList3;
                                    obj = r7;
                                    SKLogger sKLogger2 = mSKLog;
                                    String str2 = "Exception in getting method name" + e2.getMessage();
                                    sKLogger2.getClass();
                                    SKLogger.logAll(str2, e2);
                                }
                                if (serviceObject.serviceMethods.get(nodeValue) != null) {
                                    break;
                                }
                                serviceObject.serviceMethods.put(nodeValue, methodPermissionPackage);
                                nodeList3 = nodeList2;
                                r7 = obj;
                                z2 = true;
                                z3 = false;
                            }
                        }
                        nodeList2 = nodeList3;
                        if (serviceTable.get(nodeValue2) != null) {
                            return false;
                        }
                        z = false;
                        serviceTable.put(nodeValue2, serviceObject);
                    }
                } catch (NullPointerException e3) {
                    nodeList2 = nodeList3;
                    z = z3;
                    SKLogger sKLogger3 = mSKLog;
                    String str3 = "Exception in getting service name" + e3.getMessage();
                    sKLogger3.getClass();
                    SKLogger.logAll(str3, e3);
                }
                i++;
                z3 = z;
                nodeList3 = nodeList2;
                z2 = true;
            }
        }
        return z2;
    }

    public static synchronized void getServiceKeeper() {
        synchronized (ServiceKeeper.class) {
            if (serviceKeeper == null) {
                ServiceKeeper serviceKeeper2 = new ServiceKeeper();
                mPm = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
                mASKS = (ASKSManagerService) ServiceManager.getService("asks");
                serviceKeeper = serviceKeeper2;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:58:0x01b2 A[Catch: Exception -> 0x01ba, TRY_LEAVE, TryCatch #2 {Exception -> 0x01ba, blocks: (B:56:0x01ac, B:58:0x01b2), top: B:55:0x01ac }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01b8 A[ORIG_RETURN, RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0150 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int isAuthorized(int r16, int r17, android.content.Context r18, java.lang.String r19, java.lang.String r20) {
        /*
            Method dump skipped, instructions count: 526
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.ServiceKeeper.isAuthorized(int, int, android.content.Context, java.lang.String, java.lang.String):int");
    }

    public static boolean isFilterAPI(String str) {
        for (int i = 0; i < filteredAPIs.size(); i++) {
            try {
                if (((String) filteredAPIs.get(i)).equals(str)) {
                    return true;
                }
            } catch (Exception e) {
                SKLogger sKLogger = mSKLog;
                String str2 = "Checking the filtered api : occurs errors." + e.getMessage();
                sKLogger.getClass();
                SKLogger.logAll("ServiceKeeper", str2);
            }
        }
        return false;
    }

    public static boolean loadFromAuthorizeFile(String str) {
        if (str == null) {
            return false;
        }
        try {
            Document parse = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(str));
            parse.getDocumentElement().normalize();
            if (!createAuthorizationTables((Element) parse.getElementsByTagName("policy").item(0))) {
                return false;
            }
            isActive = true;
            return true;
        } catch (Exception e) {
            SKLogger sKLogger = mSKLog;
            String str2 = "loadFromAuthorizeFile occurs exception" + e.getMessage();
            sKLogger.getClass();
            SKLogger.logAll("ServiceKeeper", str2);
            return false;
        }
    }
}
