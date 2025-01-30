package com.android.server;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.os.Binder;
import android.os.Environment;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import com.android.internal.util.jobs.XmlUtils;
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

/* loaded from: classes.dex */
public class ServiceKeeper extends DefaultHandler {
    public static String TAG = "ServiceKeeper";
    public static ArrayList filteredAPIs = null;
    public static boolean isActive = false;
    public static ASKSManagerService mASKS;
    public static IPackageManager mPm;
    public static HashSet openMethodCache;
    public static ServiceKeeper serviceKeeper;
    public static Hashtable serviceTable;
    public static SKLogger mSKLog = SKLogger.getLogger();
    public static final File AUTHORIZE_POLICY_FILE = new File(Environment.getVendorDirectory(), "/etc/selinux/sk/authorize.xml");
    public static Object loadFilesLockObject = new Object();

    public ServiceKeeper() {
        mPm = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        mASKS = (ASKSManagerService) ServiceManager.getService("asks");
    }

    public static synchronized ServiceKeeper getServiceKeeper() {
        ServiceKeeper serviceKeeper2;
        synchronized (ServiceKeeper.class) {
            if (serviceKeeper == null) {
                serviceKeeper = new ServiceKeeper();
            }
            serviceKeeper2 = serviceKeeper;
        }
        return serviceKeeper2;
    }

    public static synchronized HashSet getOpenMethodCache() {
        HashSet hashSet;
        synchronized (ServiceKeeper.class) {
            if (openMethodCache == null) {
                openMethodCache = new HashSet();
            }
            hashSet = openMethodCache;
        }
        return hashSet;
    }

    public static synchronized Hashtable getServiceTable() {
        Hashtable hashtable;
        synchronized (ServiceKeeper.class) {
            if (serviceTable == null) {
                serviceTable = new Hashtable();
            }
            hashtable = serviceTable;
        }
        return hashtable;
    }

    public static boolean authorizeLoadProcedure() {
        synchronized (loadFilesLockObject) {
            if (isTableActive()) {
                return true;
            }
            filteredAPIs = new ArrayList();
            return loadFromAuthorizeFile(AUTHORIZE_POLICY_FILE.getPath());
        }
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
            mSKLog.logAll(TAG, "loadFromAuthorizeFile occurs exception" + e.getMessage());
            return false;
        }
    }

    public static boolean createAuthorizationTables(Element element) {
        NodeList elementsByTagName = element.getElementsByTagName("service");
        serviceTable = getServiceTable();
        openMethodCache = getOpenMethodCache();
        if (elementsByTagName.getLength() > 0) {
            for (int i = 0; i < elementsByTagName.getLength(); i++) {
                Element element2 = (Element) elementsByTagName.item(i);
                ServiceObject serviceObject = new ServiceObject();
                String nodeValue = element2.getAttributes().getNamedItem("name").getNodeValue();
                if ("FilteredAPIs".equals(nodeValue)) {
                    NodeList elementsByTagName2 = element2.getElementsByTagName("method");
                    if (elementsByTagName2.getLength() > 0) {
                        for (int i2 = 0; i2 < elementsByTagName2.getLength(); i2++) {
                            if (elementsByTagName2.item(i2).getNodeType() == 1 && elementsByTagName2.item(i2).getParentNode().isSameNode(element2)) {
                                filteredAPIs.add(elementsByTagName2.item(i2).getAttributes().getNamedItem("name").getNodeValue());
                            }
                        }
                    }
                } else {
                    NodeList elementsByTagName3 = element2.getElementsByTagName("seinfo");
                    if (elementsByTagName3.getLength() > 0) {
                        serviceObject.isSterileService = false;
                        for (int i3 = 0; i3 < elementsByTagName3.getLength(); i3++) {
                            if (elementsByTagName3.item(i3).getNodeType() == 1 && elementsByTagName3.item(i3).getParentNode().isSameNode(element2)) {
                                serviceObject.servicePermissions.seinfos.add(elementsByTagName3.item(i3).getAttributes().getNamedItem("value").getNodeValue());
                            }
                        }
                    }
                    NodeList elementsByTagName4 = element2.getElementsByTagName("package");
                    if (elementsByTagName4.getLength() > 0) {
                        serviceObject.isSterileService = false;
                        for (int i4 = 0; i4 < elementsByTagName4.getLength(); i4++) {
                            if (elementsByTagName4.item(i4).getNodeType() == 1 && elementsByTagName4.item(i4).getParentNode().isSameNode(element2)) {
                                serviceObject.servicePermissions.packages.add(new PackageObject(elementsByTagName4.item(i4).getAttributes().getNamedItem("name").getNodeValue(), elementsByTagName4.item(i4).getAttributes().getNamedItem("seinfo").getNodeValue()));
                            }
                        }
                    }
                    NodeList elementsByTagName5 = element2.getElementsByTagName("method");
                    if (elementsByTagName5.getLength() > 0) {
                        serviceObject.isSterileService = false;
                        processMethodsUnderService(elementsByTagName5, serviceObject, nodeValue);
                    }
                    if (serviceTable.get(nodeValue) != null) {
                        return false;
                    }
                    serviceTable.put(nodeValue, serviceObject);
                }
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [org.w3c.dom.NodeList] */
    /* JADX WARN: Type inference failed for: r11v5 */
    /* JADX WARN: Type inference failed for: r11v6, types: [int] */
    /* JADX WARN: Type inference failed for: r11v8 */
    public static boolean processMethodsUnderService(NodeList nodeList, ServiceObject serviceObject, String str) {
        boolean z = false;
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            MethodPermissionPackage methodPermissionPackage = new MethodPermissionPackage();
            String nodeValue = element.getAttributes().getNamedItem("name").getNodeValue();
            ?? elementsByTagName = element.getElementsByTagName("seinfo");
            if (elementsByTagName.getLength() > 0) {
                methodPermissionPackage.isSterileMethod = z;
                for (?? r11 = z; r11 < elementsByTagName.getLength(); r11++) {
                    methodPermissionPackage.seinfos.add(elementsByTagName.item(r11).getAttributes().getNamedItem("value").getNodeValue());
                    if (elementsByTagName.item(r11).getAttributes().getNamedItem("value").getNodeValue().equals("_open")) {
                        if (SEAMSPolicy.DEBUG) {
                            mSKLog.logAll(TAG, "Adding open method entry - " + str + XmlUtils.STRING_ARRAY_SEPARATOR + nodeValue);
                        }
                        openMethodCache.add(str + XmlUtils.STRING_ARRAY_SEPARATOR + nodeValue);
                    }
                }
            }
            NodeList elementsByTagName2 = element.getElementsByTagName("package");
            if (elementsByTagName2.getLength() > 0) {
                methodPermissionPackage.isSterileMethod = false;
                for (int i2 = 0; i2 < elementsByTagName2.getLength(); i2++) {
                    methodPermissionPackage.packages.add(new PackageObject(elementsByTagName2.item(i2).getAttributes().getNamedItem("name").getNodeValue(), elementsByTagName2.item(i2).getAttributes().getNamedItem("seinfo").getNodeValue()));
                }
            }
            if (serviceObject.serviceMethods.get(nodeValue) != null) {
                return false;
            }
            z = false;
            serviceObject.serviceMethods.put(nodeValue, methodPermissionPackage);
        }
        return true;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(7:29|(3:53|54|(7:56|(1:58)(1:61)|59|32|33|34|(6:36|37|38|(1:45)|40|(2:42|43)(1:44))(1:49)))|31|32|33|34|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0041, code lost:
    
        if (com.android.server.ServiceKeeper.openMethodCache.contains(r21 + com.android.internal.util.jobs.XmlUtils.STRING_ARRAY_SEPARATOR + r22) != false) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0142, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0143, code lost:
    
        r12 = "Checking the permission : occurs exception";
        r17 = r11;
        r11 = r16;
     */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:44:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0140 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int isAuthorized(Context context, int i, int i2, String str, String str2) {
        String str3;
        char c;
        String str4;
        if (str2 == null || str == null) {
            mSKLog.logAll(TAG, "Checking the permission : service is null or method is null");
            return -1;
        }
        try {
        } catch (Exception e) {
            mSKLog.logAll(TAG, "Exception in getting from openCache" + e.getMessage(), e);
        }
        if (Binder.getCallingUid() != 0 && i != Process.myPid()) {
            HashSet hashSet = openMethodCache;
            if (hashSet != null && !hashSet.isEmpty()) {
            }
            if (!isActive) {
                mSKLog.logAll(TAG, "The Service Keeper isn't prepared");
                return -1;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            String packageName = getPackageName(context, i);
            if (packageName == null) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                if (!isFilterAPI(str2)) {
                    mSKLog.logAll(TAG, "Checking the permission : Package Name is returned with null : Exiting : service =  " + str + " method = " + str2 + " uid = " + i2 + "pid = " + i);
                }
                return -1;
            }
            String seinfo = getSeinfo(packageName, i2);
            if (seinfo != null) {
                try {
                    if (seinfo.contains(XmlUtils.STRING_ARRAY_SEPARATOR)) {
                        String[] split = seinfo.split(XmlUtils.STRING_ARRAY_SEPARATOR);
                        if (SEAMSPolicy.DEBUG) {
                            SKLogger sKLogger = mSKLog;
                            String str5 = TAG;
                            StringBuilder sb = new StringBuilder();
                            str3 = " method = ";
                            sb.append("Checking the permission : pkgName = ");
                            sb.append(packageName);
                            sb.append(", seInfo = ");
                            sb.append(seinfo);
                            sb.append(", orgSeinfo[0] = ");
                            sb.append(split[0]);
                            sKLogger.logAll(str5, sb.toString());
                            c = 0;
                        } else {
                            str3 = " method = ";
                            c = 0;
                        }
                        seinfo = split[c];
                        str4 = seinfo;
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        if (!checkForServiceAuthorization(str, str4, packageName)) {
                            return 0;
                        }
                        String str6 = "Checking the permission : occurs exception";
                        String str7 = str4;
                        String str8 = str3;
                        try {
                            if (checkForMethodAuthorization(context, i2, str, str4, packageName, str2)) {
                                return 0;
                            }
                        } catch (Exception e2) {
                            e = e2;
                            mSKLog.logAll(TAG, str6 + e.getMessage());
                            if (!isFilterAPI(str2)) {
                            }
                        }
                        if (!isFilterAPI(str2)) {
                            return -1;
                        }
                        mSKLog.logAll(TAG, "Checking the permission : No match found : Exiting : service =  " + str + str8 + str2 + " uid = " + i2 + "pid = " + i + " pkgName = " + packageName + " seinfo = " + str7);
                        return -1;
                    }
                } catch (Exception e3) {
                    mSKLog.logAll(TAG, "Checking the permission : occurs exception" + e3.getMessage());
                    return -1;
                }
            }
            str3 = " method = ";
            str4 = seinfo;
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (!checkForServiceAuthorization(str, str4, packageName)) {
            }
        }
        return 0;
    }

    public static boolean isFilterAPI(String str) {
        for (int i = 0; i < filteredAPIs.size(); i++) {
            try {
                if (((String) filteredAPIs.get(i)).equals(str)) {
                    return true;
                }
            } catch (Exception e) {
                mSKLog.logAll(TAG, "Checking the filtered api : occurs errors." + e.getMessage());
            }
        }
        return false;
    }

    public static boolean isTableActive() {
        return isActive;
    }

    public static boolean checkForMethodAuthorization(Context context, int i, String str, String str2, String str3, String str4) {
        Hashtable hashtable = serviceTable;
        if (hashtable == null) {
            mSKLog.logAll(TAG, "Checking the method : ServiceTable is Null: service =  " + str + " method = " + str4 + " seInfo = " + str2 + " packageName = " + str3);
            return false;
        }
        if (hashtable.get(str) == null) {
            if (SEAMSPolicy.DEBUG) {
                mSKLog.logAll(TAG, "Checking the method: Service is not in service keeper: service =  " + str + " method = " + str4 + " seInfo = " + str2 + " packageName = " + str3);
            }
            return false;
        }
        ServiceObject serviceObject = (ServiceObject) serviceTable.get(str);
        if (serviceObject.isSterileService) {
            if (SEAMSPolicy.DEBUG) {
                mSKLog.logAll(TAG, "Checking the method: Service is sterile : service =  " + str + " method = " + str4 + " seInfo = " + str2 + " packageName = " + str3);
            }
            return false;
        }
        Hashtable hashtable2 = serviceObject.serviceMethods;
        if (hashtable2.get(str4) == null) {
            if (SEAMSPolicy.DEBUG) {
                mSKLog.logAll(TAG, "Checking the method: Method not in service: service =  " + str + " method = " + str4 + " seInfo = " + str2 + " packageName = " + str3);
            }
            return false;
        }
        MethodPermissionPackage methodPermissionPackage = (MethodPermissionPackage) hashtable2.get(str4);
        if (methodPermissionPackage.isSterileMethod) {
            if (SEAMSPolicy.DEBUG) {
                mSKLog.logAll(TAG, "Checking the method: Method is sterile: service =  " + str + " method = " + str4 + " seInfo = " + str2 + " packageName = " + str3);
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
                    mSKLog.logAll(TAG, "Checking the method: Package Name Match: service =  " + str + " method = " + str4 + " seInfo = " + str2 + " packageName = " + str3);
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
        if (serviceObject.servicePermissions.seinfos.contains(str2)) {
            return true;
        }
        Iterator it = serviceObject.servicePermissions.packages.iterator();
        while (it.hasNext()) {
            PackageObject packageObject = (PackageObject) it.next();
            if (packageObject.packageName.equals(str3) && packageObject.seinfo.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    public static String getSeinfo(String str, int i) {
        String str2 = null;
        try {
            str2 = mPm.getApplicationInfo(str, 0L, UserHandle.getUserId(i)).seInfo;
            byte[] sEInfo = mASKS.getSEInfo(str);
            if (sEInfo == null) {
                return str2;
            }
            String str3 = new String(sEInfo);
            try {
                if (SEAMSPolicy.DEBUG) {
                    mSKLog.logAll(TAG, "seinfo of " + str + " is changed by AASA");
                }
                return str3;
            } catch (RemoteException unused) {
                str2 = str3;
                mSKLog.logAll(TAG, "get App Info: failed");
                return str2;
            } catch (Exception e) {
                e = e;
                str2 = str3;
                mSKLog.logAll(TAG, "occurs exception" + e.getMessage());
                return str2;
            }
        } catch (RemoteException unused2) {
        } catch (Exception e2) {
            e = e2;
        }
    }

    public static String getPackageName(Context context, int i) {
        if (i == Process.myPid()) {
            return "android";
        }
        try {
            return ((ActivityManager) context.getSystemService("activity")).getPackageFromAppProcesses(i);
        } catch (Exception e) {
            mSKLog.logAll(TAG, "occurs exception" + e.getMessage());
            return null;
        }
    }
}
