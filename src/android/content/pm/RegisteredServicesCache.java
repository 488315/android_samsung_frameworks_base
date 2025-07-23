package android.content.pm;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Environment;
import android.os.Handler;
import android.os.Process;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.AtomicFile;
import android.util.AttributeSet;
import android.util.IntArray;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseArrayMap;
import android.util.Xml;
import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.ArrayUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.google.android.collect.Maps;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public abstract class RegisteredServicesCache<V> {
    private static final boolean DEBUG = false;
    protected static final String REGISTERED_SERVICES_DIR = "registered_services";
    static final long SERVICE_INFO_CACHES_TIMEOUT_MILLIS = 30000;
    private static final String TAG = "PackageManager";
    private final String mAttributesName;
    private final Handler mBackgroundHandler;
    public final Context mContext;
    private final BroadcastReceiver mExternalReceiver;
    private Handler mHandler;
    private final String mInterfaceName;
    private RegisteredServicesCacheListener<V> mListener;
    private final String mMetaDataName;
    private final BroadcastReceiver mPackageReceiver;
    private final XmlSerializerAndParser<V> mSerializerAndParser;
    protected final Object mServicesLock;
    private final SparseArrayMap<ComponentName, ServiceInfo<V>> mUserIdToServiceInfoCaches;
    private final BroadcastReceiver mUserRemovedReceiver;
    private final SparseArray<UserServices<V>> mUserServices;

    protected void onServicesChangedLocked(int i) {
    }

    public abstract V parseServiceAttributes(Resources resources, String str, AttributeSet attributeSet);

    private static class UserServices<V> {
        boolean mBindInstantServiceAllowed;
        boolean mPersistentServicesFileDidNotExist;
        final Map<V, Integer> persistentServices;
        Map<V, ServiceInfo<V>> services;

        private UserServices() {
            this.persistentServices = Maps.newHashMap();
            this.services = null;
            this.mPersistentServicesFileDidNotExist = true;
            this.mBindInstantServiceAllowed = false;
        }
    }

    private UserServices<V> findOrCreateUserLocked(int i) {
        return findOrCreateUserLocked(i, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.content.pm.RegisteredServicesCache-IA] */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r2v2 */
    /* JADX WARN: Type inference failed for: r2v4, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r2v5 */
    private UserServices<V> findOrCreateUserLocked(int i, boolean z) {
        UserInfo user;
        AutoCloseable autoCloseable;
        UserServices<V> userServices = this.mUserServices.get(i);
        if (userServices == null) {
            ?? r2 = 0;
            r2 = 0;
            userServices = new UserServices<>();
            this.mUserServices.put(i, userServices);
            if (z && this.mSerializerAndParser != null && (user = getUser(i)) != null) {
                AtomicFile createFileForUser = createFileForUser(user.id);
                if (createFileForUser.getBaseFile().exists()) {
                    try {
                        try {
                            r2 = createFileForUser.openRead();
                            readPersistentServicesLocked(r2);
                            autoCloseable = r2;
                        } catch (Exception e) {
                            Log.w(TAG, "Error reading persistent services for user " + user.id, e);
                            autoCloseable = r2;
                        }
                    } finally {
                        IoUtils.closeQuietly((AutoCloseable) r2);
                    }
                }
            }
        }
        return userServices;
    }

    public RegisteredServicesCache(Context context, String str, String str2, String str3, XmlSerializerAndParser<V> xmlSerializerAndParser) {
        this(new Injector(context), str, str2, str3, xmlSerializerAndParser);
    }

    public RegisteredServicesCache(Injector<V> injector, String str, String str2, String str3, XmlSerializerAndParser<V> xmlSerializerAndParser) {
        this.mServicesLock = new Object();
        this.mUserServices = new SparseArray<>(2);
        this.mUserIdToServiceInfoCaches = new SparseArrayMap<>();
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: android.content.pm.RegisteredServicesCache.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                int intExtra = intent.getIntExtra(Intent.EXTRA_UID, -1);
                if (intExtra != -1) {
                    RegisteredServicesCache.this.handlePackageEvent(intent, UserHandle.getUserId(intExtra));
                }
            }
        };
        this.mPackageReceiver = broadcastReceiver;
        BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() { // from class: android.content.pm.RegisteredServicesCache.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                RegisteredServicesCache.this.handlePackageEvent(intent, 0);
            }
        };
        this.mExternalReceiver = broadcastReceiver2;
        BroadcastReceiver broadcastReceiver3 = new BroadcastReceiver() { // from class: android.content.pm.RegisteredServicesCache.3
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                RegisteredServicesCache.this.onUserRemoved(intent.getIntExtra("android.intent.extra.user_handle", -1));
            }
        };
        this.mUserRemovedReceiver = broadcastReceiver3;
        Context context = injector.getContext();
        this.mContext = context;
        this.mInterfaceName = str;
        this.mMetaDataName = str2;
        this.mAttributesName = str3;
        this.mSerializerAndParser = xmlSerializerAndParser;
        migrateIfNecessaryLocked();
        boolean isCore = UserHandle.isCore(Process.myUid());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        if (isCore) {
            intentFilter.setPriority(1000);
        }
        Handler backgroundHandler = injector.getBackgroundHandler();
        this.mBackgroundHandler = backgroundHandler;
        context.registerReceiverAsUser(broadcastReceiver, UserHandle.ALL, intentFilter, null, backgroundHandler);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE);
        intentFilter2.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE);
        if (isCore) {
            intentFilter2.setPriority(1000);
        }
        context.registerReceiver(broadcastReceiver2, intentFilter2, null, backgroundHandler);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("android.intent.action.USER_REMOVED");
        if (isCore) {
            intentFilter3.setPriority(1000);
        }
        context.registerReceiver(broadcastReceiver3, intentFilter3, null, backgroundHandler);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handlePackageEvent(Intent intent, int i) {
        int[] intArrayExtra;
        String action = intent.getAction();
        boolean z = "android.intent.action.PACKAGE_REMOVED".equals(action) || Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE.equals(action);
        boolean booleanExtra = intent.getBooleanExtra(Intent.EXTRA_REPLACING, false);
        if (z && booleanExtra) {
            return;
        }
        if (Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE.equals(action) || Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE.equals(action)) {
            intArrayExtra = intent.getIntArrayExtra(Intent.EXTRA_CHANGED_UID_LIST);
        } else {
            int intExtra = intent.getIntExtra(Intent.EXTRA_UID, -1);
            intArrayExtra = intExtra > 0 ? new int[]{intExtra} : null;
        }
        generateServicesMap(intArrayExtra, i);
    }

    public void invalidateCache(int i) {
        synchronized (this.mServicesLock) {
            findOrCreateUserLocked(i).services = null;
            onServicesChangedLocked(i);
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr, int i) {
        synchronized (this.mServicesLock) {
            UserServices<V> findOrCreateUserLocked = findOrCreateUserLocked(i);
            if (findOrCreateUserLocked.services != null) {
                printWriter.println("RegisteredServicesCache: " + findOrCreateUserLocked.services.size() + " services");
                Iterator<ServiceInfo<V>> it = findOrCreateUserLocked.services.values().iterator();
                while (it.hasNext()) {
                    printWriter.println("  " + it.next());
                }
            } else {
                printWriter.println("RegisteredServicesCache: services not loaded");
            }
        }
    }

    public RegisteredServicesCacheListener<V> getListener() {
        RegisteredServicesCacheListener<V> registeredServicesCacheListener;
        synchronized (this) {
            registeredServicesCacheListener = this.mListener;
        }
        return registeredServicesCacheListener;
    }

    public void setListener(RegisteredServicesCacheListener<V> registeredServicesCacheListener, Handler handler) {
        if (handler == null) {
            handler = BackgroundThread.getHandler();
        }
        synchronized (this) {
            this.mHandler = handler;
            this.mListener = registeredServicesCacheListener;
        }
    }

    private void notifyListener(final V v, final int i, final boolean z) {
        final RegisteredServicesCacheListener<V> registeredServicesCacheListener;
        Handler handler;
        synchronized (this) {
            registeredServicesCacheListener = this.mListener;
            handler = this.mHandler;
        }
        if (registeredServicesCacheListener == null) {
            return;
        }
        handler.post(new Runnable() { // from class: android.content.pm.RegisteredServicesCache$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                RegisteredServicesCache.lambda$notifyListener$0(RegisteredServicesCacheListener.this, v, i, z);
            }
        });
    }

    static /* synthetic */ void lambda$notifyListener$0(RegisteredServicesCacheListener registeredServicesCacheListener, Object obj, int i, boolean z) {
        try {
            registeredServicesCacheListener.onServiceChanged(obj, i, z);
        } catch (Throwable th) {
            Slog.wtf(TAG, "Exception from onServiceChanged", th);
        }
    }

    public static class ServiceInfo<V> {
        public final ComponentInfo componentInfo;
        public final ComponentName componentName;
        public final long lastUpdateTime;
        public final V type;
        public final int uid;

        public ServiceInfo(V v, ComponentInfo componentInfo, ComponentName componentName, long j) {
            this.type = v;
            this.componentInfo = componentInfo;
            this.componentName = componentName;
            this.uid = componentInfo != null ? componentInfo.applicationInfo.uid : -1;
            this.lastUpdateTime = j;
        }

        public String toString() {
            return "ServiceInfo: " + this.type + ", " + this.componentName + ", uid " + this.uid + ", lastUpdateTime " + this.lastUpdateTime;
        }
    }

    public ServiceInfo<V> getServiceInfo(V v, int i) {
        ServiceInfo<V> serviceInfo;
        synchronized (this.mServicesLock) {
            UserServices<V> findOrCreateUserLocked = findOrCreateUserLocked(i);
            if (findOrCreateUserLocked.services == null) {
                generateServicesMap(null, i);
            }
            serviceInfo = findOrCreateUserLocked.services.get(v);
        }
        return serviceInfo;
    }

    public Collection<ServiceInfo<V>> getAllServices(int i) {
        Collection<ServiceInfo<V>> unmodifiableCollection;
        synchronized (this.mServicesLock) {
            UserServices<V> findOrCreateUserLocked = findOrCreateUserLocked(i);
            if (findOrCreateUserLocked.services == null) {
                generateServicesMap(null, i);
            }
            unmodifiableCollection = Collections.unmodifiableCollection(new ArrayList(findOrCreateUserLocked.services.values()));
        }
        return unmodifiableCollection;
    }

    public void updateServices(int i) {
        ApplicationInfo applicationInfo;
        synchronized (this.mServicesLock) {
            UserServices<V> findOrCreateUserLocked = findOrCreateUserLocked(i);
            if (findOrCreateUserLocked.services == null) {
                return;
            }
            IntArray intArray = null;
            for (ServiceInfo serviceInfo : new ArrayList(findOrCreateUserLocked.services.values())) {
                long j = serviceInfo.componentInfo.applicationInfo.versionCode;
                try {
                    applicationInfo = this.mContext.getPackageManager().getApplicationInfoAsUser(serviceInfo.componentInfo.packageName, 0, i);
                } catch (PackageManager.NameNotFoundException unused) {
                    applicationInfo = null;
                }
                if (applicationInfo == null || applicationInfo.versionCode != j) {
                    if (intArray == null) {
                        intArray = new IntArray();
                    }
                    intArray.add(serviceInfo.uid);
                }
            }
            if (intArray == null || intArray.size() <= 0) {
                return;
            }
            generateServicesMap(intArray.toArray(), i);
        }
    }

    public boolean getBindInstantServiceAllowed(int i) {
        boolean z;
        this.mContext.enforceCallingOrSelfPermission(Manifest.permission.MANAGE_BIND_INSTANT_SERVICE, "getBindInstantServiceAllowed");
        synchronized (this.mServicesLock) {
            z = findOrCreateUserLocked(i).mBindInstantServiceAllowed;
        }
        return z;
    }

    public void setBindInstantServiceAllowed(int i, boolean z) {
        this.mContext.enforceCallingOrSelfPermission(Manifest.permission.MANAGE_BIND_INSTANT_SERVICE, "setBindInstantServiceAllowed");
        synchronized (this.mServicesLock) {
            findOrCreateUserLocked(i).mBindInstantServiceAllowed = z;
        }
    }

    protected boolean inSystemImage(int i) {
        String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i);
        if (packagesForUid != null) {
            for (String str : packagesForUid) {
                try {
                    if ((this.mContext.getPackageManager().getPackageInfo(str, 0).applicationInfo.flags & 1) != 0) {
                        return true;
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                }
            }
        }
        return false;
    }

    protected List<ResolveInfo> queryIntentServices(int i) {
        int i2;
        PackageManager packageManager = this.mContext.getPackageManager();
        synchronized (this.mServicesLock) {
            i2 = findOrCreateUserLocked(i).mBindInstantServiceAllowed ? 9175168 : 786560;
        }
        return packageManager.queryIntentServicesAsUser(new Intent(this.mInterfaceName), i2, i);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(7:4|(4:36|37|38|(4:42|43|44|22))|6|7|(3:30|31|32)(3:9|10|(6:12|13|88|19|20|21)(1:29))|22|2) */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0093, code lost:
    
        r4 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0094, code lost:
    
        android.util.Log.w(android.content.pm.RegisteredServicesCache.TAG, "Unable to load service info " + r3.toString(), r4);
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void generateServicesMap(int[] r13, int r14) {
        /*
            Method dump skipped, instructions count: 458
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.content.pm.RegisteredServicesCache.generateServicesMap(int[], int):void");
    }

    private boolean containsUid(int[] iArr, int i) {
        return iArr == null || ArrayUtils.contains(iArr, i);
    }

    private boolean containsType(ArrayList<ServiceInfo<V>> arrayList, V v) {
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (arrayList.get(i).type.equals(v)) {
                return true;
            }
        }
        return false;
    }

    private boolean containsTypeAndUid(ArrayList<ServiceInfo<V>> arrayList, V v, int i) {
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            ServiceInfo<V> serviceInfo = arrayList.get(i2);
            if (serviceInfo.type.equals(v) && serviceInfo.uid == i) {
                return true;
            }
        }
        return false;
    }

    protected ServiceInfo<V> parseServiceInfo(ResolveInfo resolveInfo, long j) throws XmlPullParserException, IOException {
        Throwable th;
        int next;
        android.content.pm.ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        ComponentName componentName = new ComponentName(serviceInfo.packageName, serviceInfo.name);
        PackageManager packageManager = this.mContext.getPackageManager();
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                XmlResourceParser loadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, this.mMetaDataName);
                try {
                    if (loadXmlMetaData == null) {
                        throw new XmlPullParserException("No " + this.mMetaDataName + " meta-data");
                    }
                    AttributeSet asAttributeSet = Xml.asAttributeSet(loadXmlMetaData);
                    do {
                        next = loadXmlMetaData.next();
                        if (next == 1) {
                            break;
                        }
                    } while (next != 2);
                    if (!this.mAttributesName.equals(loadXmlMetaData.getName())) {
                        throw new XmlPullParserException("Meta-data does not start with " + this.mAttributesName + " tag");
                    }
                    V parseServiceAttributes = parseServiceAttributes(packageManager.getResourcesForApplication(serviceInfo.applicationInfo), serviceInfo.packageName, asAttributeSet);
                    if (parseServiceAttributes == null) {
                        if (loadXmlMetaData != null) {
                            loadXmlMetaData.close();
                        }
                        return null;
                    }
                    ServiceInfo<V> serviceInfo2 = new ServiceInfo<>(parseServiceAttributes, serviceInfo, componentName, j);
                    if (loadXmlMetaData != null) {
                        loadXmlMetaData.close();
                    }
                    return serviceInfo2;
                } catch (PackageManager.NameNotFoundException unused) {
                    xmlResourceParser = loadXmlMetaData;
                    throw new XmlPullParserException("Unable to load resources for pacakge " + serviceInfo.packageName);
                } catch (Throwable th2) {
                    th = th2;
                    xmlResourceParser = loadXmlMetaData;
                    if (xmlResourceParser != null) {
                        xmlResourceParser.close();
                        throw th;
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (PackageManager.NameNotFoundException unused2) {
        }
    }

    private void readPersistentServicesLocked(InputStream inputStream) throws XmlPullParserException, IOException {
        TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(inputStream);
        for (int eventType = resolvePullParser.getEventType(); eventType != 2 && eventType != 1; eventType = resolvePullParser.next()) {
        }
        if ("services".equals(resolvePullParser.getName())) {
            int next = resolvePullParser.next();
            do {
                if (next == 2 && resolvePullParser.getDepth() == 2 && "service".equals(resolvePullParser.getName())) {
                    V createFromXml = this.mSerializerAndParser.createFromXml(resolvePullParser);
                    if (createFromXml == null) {
                        return;
                    }
                    int attributeInt = resolvePullParser.getAttributeInt(null, "uid");
                    findOrCreateUserLocked(UserHandle.getUserId(attributeInt), false).persistentServices.put(createFromXml, Integer.valueOf(attributeInt));
                }
                next = resolvePullParser.next();
            } while (next != 1);
        }
    }

    private void migrateIfNecessaryLocked() {
        if (this.mSerializerAndParser == null) {
            return;
        }
        File file = new File(new File(getDataDirectory(), "system"), REGISTERED_SERVICES_DIR);
        AtomicFile atomicFile = new AtomicFile(new File(file, this.mInterfaceName + ".xml"));
        if (atomicFile.getBaseFile().exists()) {
            File file2 = new File(file, this.mInterfaceName + ".xml.migrated");
            if (file2.exists()) {
                return;
            }
            FileInputStream fileInputStream = null;
            try {
                try {
                    fileInputStream = atomicFile.openRead();
                    this.mUserServices.clear();
                    readPersistentServicesLocked(fileInputStream);
                } catch (Exception e) {
                    Log.w(TAG, "Error reading persistent services, starting from scratch", e);
                }
                try {
                    for (UserInfo userInfo : getUsers()) {
                        UserServices<V> userServices = this.mUserServices.get(userInfo.id);
                        if (userServices != null) {
                            writePersistentServicesLocked(userServices, userInfo.id);
                        }
                    }
                    file2.createNewFile();
                } catch (Exception e2) {
                    Log.w(TAG, "Migration failed", e2);
                }
                this.mUserServices.clear();
            } finally {
                IoUtils.closeQuietly(fileInputStream);
            }
        }
    }

    private void writePersistentServicesLocked(UserServices<V> userServices, int i) {
        FileOutputStream startWrite;
        if (this.mSerializerAndParser == null) {
            return;
        }
        AtomicFile createFileForUser = createFileForUser(i);
        FileOutputStream fileOutputStream = null;
        try {
            startWrite = createFileForUser.startWrite();
        } catch (IOException e) {
            e = e;
        }
        try {
            TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
            resolveSerializer.startDocument(null, true);
            resolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
            resolveSerializer.startTag(null, "services");
            for (Map.Entry<V, Integer> entry : userServices.persistentServices.entrySet()) {
                resolveSerializer.startTag(null, "service");
                resolveSerializer.attributeInt(null, "uid", entry.getValue().intValue());
                this.mSerializerAndParser.writeAsXml((XmlSerializerAndParser<V>) entry.getKey(), resolveSerializer);
                resolveSerializer.endTag(null, "service");
            }
            resolveSerializer.endTag(null, "services");
            resolveSerializer.endDocument();
            createFileForUser.finishWrite(startWrite);
        } catch (IOException e2) {
            e = e2;
            fileOutputStream = startWrite;
            Log.w(TAG, "Error writing accounts", e);
            if (fileOutputStream != null) {
                createFileForUser.failWrite(fileOutputStream);
            }
        }
    }

    protected void onUserRemoved(int i) {
        synchronized (this.mServicesLock) {
            this.mUserServices.remove(i);
        }
        if (Flags.optimizeParsingInRegisteredServicesCache()) {
            synchronized (this.mUserIdToServiceInfoCaches) {
                this.mUserIdToServiceInfoCaches.delete(i);
            }
        }
    }

    protected List<UserInfo> getUsers() {
        return UserManager.get(this.mContext).getAliveUsers();
    }

    protected UserInfo getUser(int i) {
        return UserManager.get(this.mContext).getUserInfo(i);
    }

    private AtomicFile createFileForUser(int i) {
        return new AtomicFile(new File(getUserSystemDirectory(i), "registered_services/" + this.mInterfaceName + ".xml"));
    }

    protected File getUserSystemDirectory(int i) {
        return Environment.getUserSystemDirectory(i);
    }

    protected File getDataDirectory() {
        return Environment.getDataDirectory();
    }

    protected Map<V, Integer> getPersistentServices(int i) {
        return findOrCreateUserLocked(i).persistentServices;
    }

    public void unregisterReceivers() {
        this.mContext.unregisterReceiver(this.mPackageReceiver);
        this.mContext.unregisterReceiver(this.mExternalReceiver);
        this.mContext.unregisterReceiver(this.mUserRemovedReceiver);
    }

    private ServiceInfo<V> getServiceInfoFromServiceCache(int i, ComponentName componentName, long j) {
        synchronized (this.mUserIdToServiceInfoCaches) {
            ServiceInfo<V> serviceInfo = this.mUserIdToServiceInfoCaches.get(i, componentName);
            if (serviceInfo == null || serviceInfo.lastUpdateTime != j) {
                return null;
            }
            return serviceInfo;
        }
    }

    public static class Injector<V> {
        private final Context mContext;

        public Injector(Context context) {
            this.mContext = context;
        }

        public Context getContext() {
            return this.mContext;
        }

        public Handler getBackgroundHandler() {
            return BackgroundThread.getHandler();
        }
    }

    class ClearServiceInfoCachesTimeoutRunnable implements Runnable {
        final int mUserId;

        ClearServiceInfoCachesTimeoutRunnable(int i) {
            this.mUserId = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (RegisteredServicesCache.this.mUserIdToServiceInfoCaches) {
                RegisteredServicesCache.this.mUserIdToServiceInfoCaches.delete(this.mUserId);
            }
        }
    }
}
