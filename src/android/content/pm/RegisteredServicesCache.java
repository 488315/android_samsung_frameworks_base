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
import android.system.ErrnoException;
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
import com.google.android.collect.Lists;
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
            ?? OpenRead = 0;
            OpenRead = 0;
            userServices = new UserServices<>();
            this.mUserServices.put(i, userServices);
            if (z && this.mSerializerAndParser != null && (user = getUser(i)) != null) {
                AtomicFile atomicFileCreateFileForUser = createFileForUser(user.id);
                if (atomicFileCreateFileForUser.getBaseFile().exists()) {
                    try {
                        try {
                            OpenRead = atomicFileCreateFileForUser.openRead();
                            readPersistentServicesLocked(OpenRead);
                            autoCloseable = OpenRead;
                        } catch (Exception e) {
                            Log.w(TAG, "Error reading persistent services for user " + user.id, e);
                            autoCloseable = OpenRead;
                        }
                    } finally {
                        IoUtils.closeQuietly((AutoCloseable) OpenRead);
                    }
                }
            }
        }
        return userServices;
    }

    public RegisteredServicesCache(Context context, String str, String str2, String str3, XmlSerializerAndParser<V> xmlSerializerAndParser) {
        this(new Injector(context), str, str2, str3, xmlSerializerAndParser);
    }

    public RegisteredServicesCache(Injector<V> injector, String str, String str2, String str3, XmlSerializerAndParser<V> xmlSerializerAndParser) throws IOException {
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
        boolean zIsCore = UserHandle.isCore(Process.myUid());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        if (zIsCore) {
            intentFilter.setPriority(1000);
        }
        Handler backgroundHandler = injector.getBackgroundHandler();
        this.mBackgroundHandler = backgroundHandler;
        context.registerReceiverAsUser(broadcastReceiver, UserHandle.ALL, intentFilter, null, backgroundHandler);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_AVAILABLE);
        intentFilter2.addAction(Intent.ACTION_EXTERNAL_APPLICATIONS_UNAVAILABLE);
        if (zIsCore) {
            intentFilter2.setPriority(1000);
        }
        context.registerReceiver(broadcastReceiver2, intentFilter2, null, backgroundHandler);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("android.intent.action.USER_REMOVED");
        if (zIsCore) {
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
            UserServices<V> userServicesFindOrCreateUserLocked = findOrCreateUserLocked(i);
            if (userServicesFindOrCreateUserLocked.services != null) {
                printWriter.println("RegisteredServicesCache: " + userServicesFindOrCreateUserLocked.services.size() + " services");
                Iterator<ServiceInfo<V>> it = userServicesFindOrCreateUserLocked.services.values().iterator();
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
                RegisteredServicesCache.lambda$notifyListener$0(registeredServicesCacheListener, v, i, z);
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
            UserServices<V> userServicesFindOrCreateUserLocked = findOrCreateUserLocked(i);
            if (userServicesFindOrCreateUserLocked.services == null) {
                generateServicesMap(null, i);
            }
            serviceInfo = userServicesFindOrCreateUserLocked.services.get(v);
        }
        return serviceInfo;
    }

    public Collection<ServiceInfo<V>> getAllServices(int i) {
        Collection<ServiceInfo<V>> collectionUnmodifiableCollection;
        synchronized (this.mServicesLock) {
            UserServices<V> userServicesFindOrCreateUserLocked = findOrCreateUserLocked(i);
            if (userServicesFindOrCreateUserLocked.services == null) {
                generateServicesMap(null, i);
            }
            collectionUnmodifiableCollection = Collections.unmodifiableCollection(new ArrayList(userServicesFindOrCreateUserLocked.services.values()));
        }
        return collectionUnmodifiableCollection;
    }

    public void updateServices(int i) {
        ApplicationInfo applicationInfoAsUser;
        synchronized (this.mServicesLock) {
            UserServices<V> userServicesFindOrCreateUserLocked = findOrCreateUserLocked(i);
            if (userServicesFindOrCreateUserLocked.services == null) {
                return;
            }
            IntArray intArray = null;
            for (ServiceInfo serviceInfo : new ArrayList(userServicesFindOrCreateUserLocked.services.values())) {
                long j = serviceInfo.componentInfo.applicationInfo.versionCode;
                try {
                    applicationInfoAsUser = this.mContext.getPackageManager().getApplicationInfoAsUser(serviceInfo.componentInfo.packageName, 0, i);
                } catch (PackageManager.NameNotFoundException unused) {
                    applicationInfoAsUser = null;
                }
                if (applicationInfoAsUser == null || applicationInfoAsUser.versionCode != j) {
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:90:0x005c A[EXC_TOP_SPLITTER, PHI: r7
      0x005c: PHI (r7v17 long) = (r7v13 long), (r7v15 long), (r7v15 long) binds: [B:6:0x002b, B:12:0x0050, B:14:0x0056] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void generateServicesMap(int[] iArr, int i) {
        ServiceInfo serviceInfoFromServiceCache;
        ArrayList arrayList = new ArrayList();
        List<ResolveInfo> listQueryIntentServices = queryIntentServices(i);
        PackageManager packageManager = this.mContext.getPackageManager();
        for (ResolveInfo resolveInfo : listQueryIntentServices) {
            android.content.pm.ServiceInfo serviceInfo = resolveInfo.serviceInfo;
            ComponentName componentName = serviceInfo.getComponentName();
            long j = -1;
            if (Flags.optimizeParsingInRegisteredServicesCache()) {
                try {
                    j = packageManager.getPackageInfoAsUser(serviceInfo.packageName, 786432, i).lastUpdateTime;
                } catch (PackageManager.NameNotFoundException | SecurityException e) {
                    Slog.d(TAG, "Fail to get the PackageInfo in generateServicesMap: " + e);
                }
                if (j >= 0 && (serviceInfoFromServiceCache = getServiceInfoFromServiceCache(i, componentName, j)) != null) {
                    arrayList.add(serviceInfoFromServiceCache);
                } else {
                    try {
                        ServiceInfo<V> serviceInfo2 = parseServiceInfo(resolveInfo, j);
                        if (serviceInfo2 == null) {
                            Log.w(TAG, "Unable to load service info " + resolveInfo.toString());
                        } else {
                            arrayList.add(serviceInfo2);
                            if (Flags.optimizeParsingInRegisteredServicesCache()) {
                                synchronized (this.mUserIdToServiceInfoCaches) {
                                    this.mUserIdToServiceInfoCaches.add(i, componentName, serviceInfo2);
                                }
                            } else {
                                continue;
                            }
                        }
                    } catch (IOException | XmlPullParserException e2) {
                        Log.w(TAG, "Unable to load service info " + resolveInfo.toString(), e2);
                    }
                }
            }
        }
        if (Flags.optimizeParsingInRegisteredServicesCache()) {
            synchronized (this.mUserIdToServiceInfoCaches) {
                if (this.mUserIdToServiceInfoCaches.numElementsForKey(i) > 0) {
                    Integer numValueOf = Integer.valueOf(i);
                    this.mBackgroundHandler.removeCallbacksAndEqualMessages(numValueOf);
                    this.mBackgroundHandler.postDelayed(new ClearServiceInfoCachesTimeoutRunnable(i), numValueOf, 30000L);
                }
            }
        }
        synchronized (this.mServicesLock) {
            UserServices userServicesFindOrCreateUserLocked = findOrCreateUserLocked(i);
            boolean z = userServicesFindOrCreateUserLocked.services == null;
            if (z) {
                userServicesFindOrCreateUserLocked.services = Maps.newHashMap();
            }
            Iterator it = arrayList.iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                ServiceInfo<V> serviceInfo3 = (ServiceInfo) it.next();
                Integer num = userServicesFindOrCreateUserLocked.persistentServices.get(serviceInfo3.type);
                if (num == null) {
                    userServicesFindOrCreateUserLocked.services.put(serviceInfo3.type, serviceInfo3);
                    userServicesFindOrCreateUserLocked.persistentServices.put(serviceInfo3.type, Integer.valueOf(serviceInfo3.uid));
                    if (!userServicesFindOrCreateUserLocked.mPersistentServicesFileDidNotExist || !z) {
                        notifyListener(serviceInfo3.type, i, false);
                    }
                } else if (num.intValue() == serviceInfo3.uid) {
                    userServicesFindOrCreateUserLocked.services.put(serviceInfo3.type, serviceInfo3);
                } else if (inSystemImage(serviceInfo3.uid) || !containsTypeAndUid(arrayList, serviceInfo3.type, num.intValue())) {
                    userServicesFindOrCreateUserLocked.services.put(serviceInfo3.type, serviceInfo3);
                    userServicesFindOrCreateUserLocked.persistentServices.put(serviceInfo3.type, Integer.valueOf(serviceInfo3.uid));
                    notifyListener(serviceInfo3.type, i, false);
                }
                z2 = true;
            }
            ArrayList arrayListNewArrayList = Lists.newArrayList();
            for (V v : userServicesFindOrCreateUserLocked.persistentServices.keySet()) {
                if (!containsType(arrayList, v) && containsUid(iArr, userServicesFindOrCreateUserLocked.persistentServices.get(v).intValue())) {
                    arrayListNewArrayList.add(v);
                }
            }
            Iterator it2 = arrayListNewArrayList.iterator();
            while (it2.hasNext()) {
                Object next = it2.next();
                userServicesFindOrCreateUserLocked.persistentServices.remove(next);
                userServicesFindOrCreateUserLocked.services.remove(next);
                notifyListener(next, i, true);
                z2 = true;
            }
            if (z2) {
                onServicesChangedLocked(i);
                writePersistentServicesLocked(userServicesFindOrCreateUserLocked, i);
            }
        }
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

    protected ServiceInfo<V> parseServiceInfo(ResolveInfo resolveInfo, long j) throws Throwable {
        Throwable th;
        XmlResourceParser xmlResourceParserLoadXmlMetaData;
        int next;
        android.content.pm.ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        ComponentName componentName = new ComponentName(serviceInfo.packageName, serviceInfo.name);
        PackageManager packageManager = this.mContext.getPackageManager();
        XmlResourceParser xmlResourceParser = null;
        try {
            try {
                xmlResourceParserLoadXmlMetaData = serviceInfo.loadXmlMetaData(packageManager, this.mMetaDataName);
            } catch (PackageManager.NameNotFoundException unused) {
            }
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            if (xmlResourceParserLoadXmlMetaData == null) {
                throw new XmlPullParserException("No " + this.mMetaDataName + " meta-data");
            }
            AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xmlResourceParserLoadXmlMetaData);
            do {
                next = xmlResourceParserLoadXmlMetaData.next();
                if (next == 1) {
                    break;
                }
            } while (next != 2);
            if (!this.mAttributesName.equals(xmlResourceParserLoadXmlMetaData.getName())) {
                throw new XmlPullParserException("Meta-data does not start with " + this.mAttributesName + " tag");
            }
            V serviceAttributes = parseServiceAttributes(packageManager.getResourcesForApplication(serviceInfo.applicationInfo), serviceInfo.packageName, attributeSetAsAttributeSet);
            if (serviceAttributes == null) {
                if (xmlResourceParserLoadXmlMetaData != null) {
                    xmlResourceParserLoadXmlMetaData.close();
                }
                return null;
            }
            ServiceInfo<V> serviceInfo2 = new ServiceInfo<>(serviceAttributes, serviceInfo, componentName, j);
            if (xmlResourceParserLoadXmlMetaData != null) {
                xmlResourceParserLoadXmlMetaData.close();
            }
            return serviceInfo2;
        } catch (PackageManager.NameNotFoundException unused2) {
            xmlResourceParser = xmlResourceParserLoadXmlMetaData;
            throw new XmlPullParserException("Unable to load resources for pacakge " + serviceInfo.packageName);
        } catch (Throwable th3) {
            th = th3;
            xmlResourceParser = xmlResourceParserLoadXmlMetaData;
            if (xmlResourceParser != null) {
                xmlResourceParser.close();
                throw th;
            }
            throw th;
        }
    }

    private void readPersistentServicesLocked(InputStream inputStream) throws XmlPullParserException, IOException, ErrnoException {
        TypedXmlPullParser typedXmlPullParserResolvePullParser = Xml.resolvePullParser(inputStream);
        for (int eventType = typedXmlPullParserResolvePullParser.getEventType(); eventType != 2 && eventType != 1; eventType = typedXmlPullParserResolvePullParser.next()) {
        }
        if ("services".equals(typedXmlPullParserResolvePullParser.getName())) {
            int next = typedXmlPullParserResolvePullParser.next();
            do {
                if (next == 2 && typedXmlPullParserResolvePullParser.getDepth() == 2 && "service".equals(typedXmlPullParserResolvePullParser.getName())) {
                    V vCreateFromXml = this.mSerializerAndParser.createFromXml(typedXmlPullParserResolvePullParser);
                    if (vCreateFromXml == null) {
                        return;
                    }
                    int attributeInt = typedXmlPullParserResolvePullParser.getAttributeInt(null, "uid");
                    findOrCreateUserLocked(UserHandle.getUserId(attributeInt), false).persistentServices.put(vCreateFromXml, Integer.valueOf(attributeInt));
                }
                next = typedXmlPullParserResolvePullParser.next();
            } while (next != 1);
        }
    }

    private void migrateIfNecessaryLocked() throws IOException {
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
            FileInputStream fileInputStreamOpenRead = null;
            try {
                try {
                    fileInputStreamOpenRead = atomicFile.openRead();
                    this.mUserServices.clear();
                    readPersistentServicesLocked(fileInputStreamOpenRead);
                } finally {
                    IoUtils.closeQuietly(fileInputStreamOpenRead);
                }
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
        }
    }

    private void writePersistentServicesLocked(UserServices<V> userServices, int i) throws IOException {
        if (this.mSerializerAndParser == null) {
            return;
        }
        AtomicFile atomicFileCreateFileForUser = createFileForUser(i);
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStreamStartWrite = atomicFileCreateFileForUser.startWrite();
            try {
                TypedXmlSerializer typedXmlSerializerResolveSerializer = Xml.resolveSerializer(fileOutputStreamStartWrite);
                typedXmlSerializerResolveSerializer.startDocument(null, true);
                typedXmlSerializerResolveSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                typedXmlSerializerResolveSerializer.startTag(null, "services");
                for (Map.Entry<V, Integer> entry : userServices.persistentServices.entrySet()) {
                    typedXmlSerializerResolveSerializer.startTag(null, "service");
                    typedXmlSerializerResolveSerializer.attributeInt(null, "uid", entry.getValue().intValue());
                    this.mSerializerAndParser.writeAsXml((XmlSerializerAndParser<V>) entry.getKey(), typedXmlSerializerResolveSerializer);
                    typedXmlSerializerResolveSerializer.endTag(null, "service");
                }
                typedXmlSerializerResolveSerializer.endTag(null, "services");
                typedXmlSerializerResolveSerializer.endDocument();
                atomicFileCreateFileForUser.finishWrite(fileOutputStreamStartWrite);
            } catch (IOException e) {
                e = e;
                fileOutputStream = fileOutputStreamStartWrite;
                Log.w(TAG, "Error writing accounts", e);
                if (fileOutputStream != null) {
                    atomicFileCreateFileForUser.failWrite(fileOutputStream);
                }
            }
        } catch (IOException e2) {
            e = e2;
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
