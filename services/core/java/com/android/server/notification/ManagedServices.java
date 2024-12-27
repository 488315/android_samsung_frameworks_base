package com.android.server.notification;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.UserInfo;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IntArray;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseSetArray;
import android.util.proto.ProtoOutputStream;

import com.android.internal.util.XmlUtils;
import com.android.internal.util.function.TriPredicate;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.utils.TimingsTraceAndSlog;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class ManagedServices {
    public final boolean DEBUG;
    public final String TAG;
    public int mApprovalLevel;
    public final ArrayMap mApproved;
    public final Config mConfig;
    public final Context mContext;
    public final ArraySet mDefaultComponents;
    public final ArraySet mDefaultPackages;
    public final Object mDefaultsLock;
    public final ArraySet mEnabledServicesForCurrentProfiles;
    public final ArraySet mEnabledServicesPackageNames;
    public final Handler mHandler;
    public final ArrayMap mIsUserChanged;
    public final Object mMutex;
    public final IPackageManager mPm;
    public final ArrayList mServices;
    public final ArrayList mServicesBound;
    public final ArraySet mServicesRebinding;
    public final SparseSetArray mSnoozing;
    public final UserManager mUm;
    public boolean mUseXml;
    public final UserProfiles mUserProfiles;
    public final ArrayMap mUserSetServices;

    /* renamed from: com.android.server.notification.ManagedServices$1, reason: invalid class name */
    public final class AnonymousClass1 implements ServiceConnection {
        public ManagedServiceInfo mRemovedInfo;
        public IInterface mService;
        public final /* synthetic */ boolean val$isSystem;
        public final /* synthetic */ Pair val$servicesBindingTag;
        public final /* synthetic */ int val$targetSdkVersion;
        public final /* synthetic */ int val$uid;
        public final /* synthetic */ int val$userid;

        public AnonymousClass1(int i, Pair pair, boolean z, int i2, int i3) {
            this.val$userid = i;
            this.val$servicesBindingTag = pair;
            this.val$isSystem = z;
            this.val$targetSdkVersion = i2;
            this.val$uid = i3;
        }

        @Override // android.content.ServiceConnection
        public final void onBindingDied(final ComponentName componentName) {
            Slog.w(
                    ManagedServices.this.TAG,
                    this.val$userid
                            + " "
                            + ManagedServices.this.mConfig.caption
                            + " binding died: "
                            + componentName);
            synchronized (ManagedServices.this.mMutex) {
                try {
                    int size = ManagedServices.this.mServices.size() - 1;
                    while (true) {
                        if (size < 0) {
                            break;
                        }
                        ManagedServiceInfo managedServiceInfo =
                                (ManagedServiceInfo) ManagedServices.this.mServices.get(size);
                        if (managedServiceInfo.isSystem
                                && componentName.equals(managedServiceInfo.component)) {
                            this.mRemovedInfo = managedServiceInfo;
                            break;
                        }
                        size--;
                    }
                    ManagedServices.this.unbindService(this, componentName, this.val$userid);
                    if (ManagedServices.this.mServicesRebinding.contains(
                            this.val$servicesBindingTag)) {
                        Slog.v(
                                ManagedServices.this.TAG,
                                ManagedServices.this.mConfig.caption
                                        + " not rebinding in user "
                                        + this.val$userid
                                        + " as a previous rebind attempt was made: "
                                        + componentName);
                    } else {
                        ManagedServices.this.mServicesRebinding.add(this.val$servicesBindingTag);
                        Handler handler = ManagedServices.this.mHandler;
                        final int i = this.val$userid;
                        handler.postDelayed(
                                new Runnable() { // from class:
                                                 // com.android.server.notification.ManagedServices$1$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        ManagedServices.AnonymousClass1 anonymousClass1 =
                                                ManagedServices.AnonymousClass1.this;
                                        ComponentName componentName2 = componentName;
                                        int i2 = i;
                                        ManagedServices.ManagedServiceInfo managedServiceInfo2 =
                                                anonymousClass1.mRemovedInfo;
                                        if (managedServiceInfo2 == null
                                                || !managedServiceInfo2.isSystem) {
                                            ManagedServices.this.reregisterService(
                                                    componentName2, i2);
                                            return;
                                        }
                                        ManagedServices managedServices = ManagedServices.this;
                                        synchronized (managedServices.mMutex) {
                                            managedServices.registerServiceLocked(
                                                    i2, componentName2, true);
                                        }
                                    }
                                },
                                10000L);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        @Override // android.content.ServiceConnection
        public final void onNullBinding(ComponentName componentName) {
            Slog.v(
                    ManagedServices.this.TAG,
                    "onNullBinding() called with: name = [" + componentName + "]");
            ManagedServices.this.mContext.unbindService(this);
        }

        @Override // android.content.ServiceConnection
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onServiceConnected(
                android.content.ComponentName r14, android.os.IBinder r15) {
            /*
                r13 = this;
                com.android.server.notification.ManagedServices r0 = com.android.server.notification.ManagedServices.this
                java.lang.String r0 = r0.TAG
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                int r2 = r13.val$userid
                r1.append(r2)
                java.lang.String r2 = " "
                r1.append(r2)
                com.android.server.notification.ManagedServices r2 = com.android.server.notification.ManagedServices.this
                com.android.server.notification.ManagedServices$Config r2 = r2.mConfig
                java.lang.String r2 = r2.caption
                r1.append(r2)
                java.lang.String r2 = " service connected: "
                r1.append(r2)
                r1.append(r14)
                java.lang.String r1 = r1.toString()
                android.util.Slog.v(r0, r1)
                com.android.server.notification.ManagedServices r0 = com.android.server.notification.ManagedServices.this
                java.lang.Object r0 = r0.mMutex
                monitor-enter(r0)
                com.android.server.notification.ManagedServices r1 = com.android.server.notification.ManagedServices.this     // Catch: java.lang.Throwable -> L64
                android.util.ArraySet r1 = r1.mServicesRebinding     // Catch: java.lang.Throwable -> L64
                android.util.Pair r2 = r13.val$servicesBindingTag     // Catch: java.lang.Throwable -> L64
                r1.remove(r2)     // Catch: java.lang.Throwable -> L64
                r1 = 0
                r2 = 0
                com.android.server.notification.ManagedServices r3 = com.android.server.notification.ManagedServices.this     // Catch: java.lang.Throwable -> L64 android.os.RemoteException -> L69
                android.os.IInterface r6 = r3.asInterface(r15)     // Catch: java.lang.Throwable -> L64 android.os.RemoteException -> L69
                r13.mService = r6     // Catch: java.lang.Throwable -> L64 android.os.RemoteException -> L69
                com.android.server.notification.ManagedServices r5 = com.android.server.notification.ManagedServices.this     // Catch: java.lang.Throwable -> L64 android.os.RemoteException -> L69
                int r8 = r13.val$userid     // Catch: java.lang.Throwable -> L64 android.os.RemoteException -> L69
                boolean r9 = r13.val$isSystem     // Catch: java.lang.Throwable -> L64 android.os.RemoteException -> L69
                int r11 = r13.val$targetSdkVersion     // Catch: java.lang.Throwable -> L64 android.os.RemoteException -> L69
                int r12 = r13.val$uid     // Catch: java.lang.Throwable -> L64 android.os.RemoteException -> L69
                r5.getClass()     // Catch: java.lang.Throwable -> L64 android.os.RemoteException -> L69
                com.android.server.notification.ManagedServices$ManagedServiceInfo r3 = new com.android.server.notification.ManagedServices$ManagedServiceInfo     // Catch: java.lang.Throwable -> L64 android.os.RemoteException -> L69
                r4 = r3
                r7 = r14
                r10 = r13
                r4.<init>(r6, r7, r8, r9, r10, r11, r12)     // Catch: java.lang.Throwable -> L64 android.os.RemoteException -> L69
                r15.linkToDeath(r3, r1)     // Catch: java.lang.Throwable -> L64 android.os.RemoteException -> L66
                com.android.server.notification.ManagedServices r14 = com.android.server.notification.ManagedServices.this     // Catch: java.lang.Throwable -> L64 android.os.RemoteException -> L66
                java.util.ArrayList r14 = r14.mServices     // Catch: java.lang.Throwable -> L64 android.os.RemoteException -> L66
                boolean r1 = r14.add(r3)     // Catch: java.lang.Throwable -> L64 android.os.RemoteException -> L66
                goto L74
            L64:
                r13 = move-exception
                goto L7d
            L66:
                r14 = move-exception
                r2 = r3
                goto L6a
            L69:
                r14 = move-exception
            L6a:
                com.android.server.notification.ManagedServices r15 = com.android.server.notification.ManagedServices.this     // Catch: java.lang.Throwable -> L64
                java.lang.String r15 = r15.TAG     // Catch: java.lang.Throwable -> L64
                java.lang.String r3 = "Failed to linkToDeath, already dead"
                android.util.Slog.e(r15, r3, r14)     // Catch: java.lang.Throwable -> L64
                r3 = r2
            L74:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L64
                if (r1 == 0) goto L7c
                com.android.server.notification.ManagedServices r13 = com.android.server.notification.ManagedServices.this
                r13.onServiceAdded(r3)
            L7c:
                return
            L7d:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L64
                throw r13
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.notification.ManagedServices.AnonymousClass1.onServiceConnected(android.content.ComponentName,"
                        + " android.os.IBinder):void");
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            Slog.v(
                    ManagedServices.this.TAG,
                    this.val$userid
                            + " "
                            + ManagedServices.this.mConfig.caption
                            + " connection lost: "
                            + componentName);
        }
    }

    public final class Config {
        public String bindPermission;
        public String caption;
        public int clientLabel;
        public String secondarySettingName;
        public String secureSettingName;
        public String serviceInterface;
        public String settingsAction;
        public String xmlTag;
    }

    public final class ManagedServiceInfo implements IBinder.DeathRecipient {
        public final ComponentName component;
        public final ServiceConnection connection;
        public final boolean isSystem;
        public boolean isSystemUi;
        public final Pair mKey;
        public final IInterface service;
        public final int targetSdkVersion;
        public final int uid;
        public final int userid;

        public ManagedServiceInfo(
                IInterface iInterface,
                ComponentName componentName,
                int i,
                boolean z,
                ServiceConnection serviceConnection,
                int i2,
                int i3) {
            this.service = iInterface;
            this.component = componentName;
            this.userid = i;
            this.isSystem = z;
            this.connection = serviceConnection;
            this.targetSdkVersion = i2;
            this.uid = i3;
            this.mKey = Pair.create(componentName, Integer.valueOf(i));
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            ManagedServices managedServices = ManagedServices.this;
            if (managedServices.DEBUG) {
                Slog.d(managedServices.TAG, "binderDied");
            }
            ManagedServices.this.removeServiceImpl(this.service, this.userid);
        }

        public final void dumpDebug(
                ProtoOutputStream protoOutputStream, ManagedServices managedServices) {
            long start = protoOutputStream.start(2246267895812L);
            this.component.dumpDebug(protoOutputStream, 1146756268033L);
            protoOutputStream.write(1120986464258L, this.userid);
            protoOutputStream.write(1138166333443L, this.service.getClass().getName());
            protoOutputStream.write(1133871366148L, this.isSystem);
            protoOutputStream.write(1133871366149L, ManagedServices.this != managedServices);
            protoOutputStream.end(start);
        }

        public final boolean enabledAndUserMatches(int i) {
            boolean isNotificationListenerServicePermitted;
            if (!isEnabledForCurrentProfiles()) {
                return false;
            }
            int i2 = this.userid;
            if (i2 == -1 || this.isSystem || i == -1 || i == i2) {
                return true;
            }
            if (this.targetSdkVersion < 21
                    || !ManagedServices.this.mUserProfiles.isCurrentProfile(i)) {
                return false;
            }
            ManagedServices managedServices = ManagedServices.this;
            if (managedServices.mUserProfiles.isProfileUser(managedServices.mContext, i)) {
                DevicePolicyManager devicePolicyManager =
                        (DevicePolicyManager)
                                ManagedServices.this.mContext.getSystemService("device_policy");
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    isNotificationListenerServicePermitted =
                            devicePolicyManager.isNotificationListenerServicePermitted(
                                    this.component.getPackageName(), i);
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } else {
                isNotificationListenerServicePermitted = true;
            }
            return isNotificationListenerServicePermitted;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || ManagedServiceInfo.class != obj.getClass()) {
                return false;
            }
            ManagedServiceInfo managedServiceInfo = (ManagedServiceInfo) obj;
            return this.userid == managedServiceInfo.userid
                    && this.isSystem == managedServiceInfo.isSystem
                    && this.targetSdkVersion == managedServiceInfo.targetSdkVersion
                    && Objects.equals(this.service, managedServiceInfo.service)
                    && Objects.equals(this.component, managedServiceInfo.component)
                    && Objects.equals(this.connection, managedServiceInfo.connection);
        }

        public final int hashCode() {
            return Objects.hash(
                    this.service,
                    this.component,
                    Integer.valueOf(this.userid),
                    Boolean.valueOf(this.isSystem),
                    this.connection,
                    Integer.valueOf(this.targetSdkVersion));
        }

        public final boolean isEnabledForCurrentProfiles() {
            boolean contains;
            if (this.isSystem) {
                return true;
            }
            if (this.connection == null) {
                return false;
            }
            synchronized (ManagedServices.this.mMutex) {
                contains =
                        ManagedServices.this.mEnabledServicesForCurrentProfiles.contains(
                                this.component);
            }
            return contains;
        }

        public final boolean isSameUser(int i) {
            if (isEnabledForCurrentProfiles()) {
                return i == -1 || i == this.userid;
            }
            return false;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("ManagedServiceInfo[component=");
            sb.append(this.component);
            sb.append(",userid=");
            sb.append(this.userid);
            sb.append(",isSystem=");
            sb.append(this.isSystem);
            sb.append(",targetSdkVersion=");
            sb.append(this.targetSdkVersion);
            sb.append(",connection=");
            sb.append(this.connection == null ? null : "<connection>");
            sb.append(",service=");
            sb.append(this.service);
            sb.append(']');
            return sb.toString();
        }
    }

    public final class UserProfiles {
        public final SparseArray mCurrentProfiles = new SparseArray();

        public final IntArray getCurrentProfileIds() {
            IntArray intArray;
            synchronized (this.mCurrentProfiles) {
                try {
                    intArray = new IntArray(this.mCurrentProfiles.size());
                    int size = this.mCurrentProfiles.size();
                    for (int i = 0; i < size; i++) {
                        intArray.add(this.mCurrentProfiles.keyAt(i));
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return intArray;
        }

        public final boolean isCurrentProfile(int i) {
            boolean z;
            synchronized (this.mCurrentProfiles) {
                z = this.mCurrentProfiles.get(i) != null;
            }
            return z;
        }

        public final boolean isProfileUser(Context context, int i) {
            synchronized (this.mCurrentProfiles) {
                try {
                    UserInfo userInfo = (UserInfo) this.mCurrentProfiles.get(i);
                    if (userInfo == null) {
                        return false;
                    }
                    if (!NotificationManagerService.privateSpaceFlagsEnabled()) {
                        return userInfo.isManagedProfile() || userInfo.isCloneProfile();
                    }
                    if (userInfo.isProfile()) {
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            if (((UserManager) context.getSystemService(UserManager.class))
                                            .getProfileParent(userInfo.id)
                                    != null) {
                                r7 = true;
                            }
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                    return r7;
                } finally {
                }
            }
        }

        public final void updateCache(Context context) {
            UserManager userManager = (UserManager) context.getSystemService("user");
            if (userManager != null) {
                List<UserInfo> profiles = userManager.getProfiles(ActivityManager.getCurrentUser());
                synchronized (this.mCurrentProfiles) {
                    try {
                        this.mCurrentProfiles.clear();
                        for (UserInfo userInfo : profiles) {
                            this.mCurrentProfiles.put(userInfo.id, userInfo);
                        }
                    } finally {
                    }
                }
            }
        }
    }

    public ManagedServices(
            Context context,
            Object obj,
            UserProfiles userProfiles,
            IPackageManager iPackageManager) {
        String simpleName = getClass().getSimpleName();
        this.TAG = simpleName;
        this.DEBUG = Log.isLoggable(simpleName, 3);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mServices = new ArrayList();
        this.mServicesBound = new ArrayList();
        this.mServicesRebinding = new ArraySet();
        this.mDefaultsLock = new Object();
        this.mDefaultComponents = new ArraySet();
        this.mDefaultPackages = new ArraySet();
        this.mEnabledServicesForCurrentProfiles = new ArraySet();
        this.mEnabledServicesPackageNames = new ArraySet();
        this.mSnoozing = new SparseSetArray();
        this.mApproved = new ArrayMap();
        this.mUserSetServices = new ArrayMap();
        this.mIsUserChanged = new ArrayMap();
        this.mContext = context;
        this.mMutex = obj;
        this.mUserProfiles = userProfiles;
        this.mPm = iPackageManager;
        this.mConfig = getConfig();
        this.mApprovalLevel = 1;
        this.mUm = (UserManager) context.getSystemService("user");
    }

    public static String getPackageName(String str) {
        ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
        return unflattenFromString != null ? unflattenFromString.getPackageName() : str;
    }

    public static void populateComponentsToUnbind(
            boolean z, Set set, SparseArray sparseArray, SparseArray sparseArray2) {
        Iterator it = ((ArraySet) set).iterator();
        while (it.hasNext()) {
            ManagedServiceInfo managedServiceInfo = (ManagedServiceInfo) it.next();
            Set set2 = (Set) sparseArray.get(managedServiceInfo.userid);
            if (set2 != null && (z || !set2.contains(managedServiceInfo.component))) {
                Set set3 = (Set) sparseArray2.get(managedServiceInfo.userid, new ArraySet());
                set3.add(managedServiceInfo.component);
                sparseArray2.put(managedServiceInfo.userid, set3);
            }
        }
    }

    public void addApprovedList(int i, String str, String str2, boolean z) {
        if (TextUtils.isEmpty(str)) {
            str = "";
        }
        if (str2 == null) {
            str2 = str;
        }
        synchronized (this.mApproved) {
            try {
                ArrayMap arrayMap = (ArrayMap) this.mApproved.get(Integer.valueOf(i));
                if (arrayMap == null) {
                    arrayMap = new ArrayMap();
                    this.mApproved.put(Integer.valueOf(i), arrayMap);
                }
                ArraySet arraySet = (ArraySet) arrayMap.get(Boolean.valueOf(z));
                if (arraySet == null) {
                    arraySet = new ArraySet();
                    arrayMap.put(Boolean.valueOf(z), arraySet);
                }
                for (String str3 : str.split(":")) {
                    String approvedValue = getApprovedValue(str3);
                    if (approvedValue != null) {
                        arraySet.add(approvedValue);
                    }
                }
                ArraySet arraySet2 = (ArraySet) this.mUserSetServices.get(Integer.valueOf(i));
                if (arraySet2 == null) {
                    arraySet2 = new ArraySet();
                    this.mUserSetServices.put(Integer.valueOf(i), arraySet2);
                }
                for (String str4 : str2.split(":")) {
                    String approvedValue2 = getApprovedValue(str4);
                    if (approvedValue2 != null) {
                        arraySet2.add(approvedValue2);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void addDefaultComponentOrPackage(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        synchronized (this.mDefaultsLock) {
            try {
                if (this.mApprovalLevel == 0) {
                    this.mDefaultPackages.add(str);
                    return;
                }
                ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
                if (unflattenFromString == null || this.mApprovalLevel != 1) {
                    return;
                }
                this.mDefaultPackages.add(unflattenFromString.getPackageName());
                this.mDefaultComponents.add(unflattenFromString);
            } finally {
            }
        }
    }

    public abstract boolean allowRebindForParentUser();

    public abstract IInterface asInterface(IBinder iBinder);

    public final void checkNotNull(IInterface iInterface) {
        if (iInterface == null) {
            throw new IllegalArgumentException(
                    AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                            new StringBuilder(), this.mConfig.caption, " must not be null"));
        }
    }

    public final ManagedServiceInfo checkServiceTokenLocked(IInterface iInterface) {
        checkNotNull(iInterface);
        ManagedServiceInfo serviceFromTokenLocked = getServiceFromTokenLocked(iInterface);
        if (serviceFromTokenLocked != null) {
            return serviceFromTokenLocked;
        }
        throw new SecurityException(
                "Disallowed call from unknown "
                        + this.mConfig.caption
                        + ": "
                        + iInterface
                        + " "
                        + iInterface.getClass());
    }

    public void clearApprovedList() {}

    public final void dump(
            ProtoOutputStream protoOutputStream, NotificationManagerService.DumpFilter dumpFilter) {
        int i;
        protoOutputStream.write(1138166333441L, this.mConfig.caption);
        synchronized (this.mApproved) {
            try {
                int size = this.mApproved.size();
                int i2 = 0;
                while (true) {
                    long j = 2246267895810L;
                    if (i2 >= size) {
                        break;
                    }
                    int intValue = ((Integer) this.mApproved.keyAt(i2)).intValue();
                    ArrayMap arrayMap = (ArrayMap) this.mApproved.valueAt(i2);
                    if (arrayMap != null) {
                        int size2 = arrayMap.size();
                        int i3 = 0;
                        while (i3 < size2) {
                            boolean booleanValue = ((Boolean) arrayMap.keyAt(i3)).booleanValue();
                            ArraySet arraySet = (ArraySet) arrayMap.valueAt(i3);
                            if (arrayMap.size() > 0) {
                                i = i2;
                                long start = protoOutputStream.start(j);
                                Iterator it = arraySet.iterator();
                                while (it.hasNext()) {
                                    protoOutputStream.write(2237677961217L, (String) it.next());
                                }
                                protoOutputStream.write(1120986464258L, intValue);
                                protoOutputStream.write(1133871366147L, booleanValue);
                                protoOutputStream.end(start);
                            } else {
                                i = i2;
                            }
                            i3++;
                            i2 = i;
                            j = 2246267895810L;
                        }
                    }
                    i2++;
                }
            } finally {
            }
        }
        synchronized (this.mMutex) {
            try {
                Iterator it2 = this.mEnabledServicesForCurrentProfiles.iterator();
                while (it2.hasNext()) {
                    ComponentName componentName = (ComponentName) it2.next();
                    if (dumpFilter.matches(componentName)) {
                        componentName.dumpDebug(protoOutputStream, 2246267895811L);
                    }
                }
                Iterator it3 = this.mServices.iterator();
                while (it3.hasNext()) {
                    ManagedServiceInfo managedServiceInfo = (ManagedServiceInfo) it3.next();
                    if (dumpFilter.matches(managedServiceInfo.component)) {
                        managedServiceInfo.dumpDebug(protoOutputStream, this);
                    }
                }
            } finally {
            }
        }
        synchronized (this.mSnoozing) {
            for (int i4 = 0; i4 < this.mSnoozing.size(); i4++) {
                try {
                    long start2 = protoOutputStream.start(2246267895814L);
                    protoOutputStream.write(1120986464257L, this.mSnoozing.keyAt(i4));
                    Iterator it4 = this.mSnoozing.valuesAt(i4).iterator();
                    while (it4.hasNext()) {
                        ((ComponentName) it4.next()).dumpDebug(protoOutputStream, 2246267895810L);
                    }
                    protoOutputStream.end(start2);
                } finally {
                }
            }
        }
    }

    public void dump(PrintWriter printWriter, NotificationManagerService.DumpFilter dumpFilter) {
        int i;
        SparseSetArray sparseSetArray;
        Bundle bundle;
        ProxyManager$$ExternalSyntheticOutline0.m(
                printWriter, this.mConfig.caption, "s:", new StringBuilder("    Allowed "));
        synchronized (this.mApproved) {
            try {
                int size = this.mApproved.size();
                for (int i2 = 0; i2 < size; i2++) {
                    Integer num = (Integer) this.mApproved.keyAt(i2);
                    int intValue = num.intValue();
                    ArrayMap arrayMap = (ArrayMap) this.mApproved.valueAt(i2);
                    Boolean bool = (Boolean) this.mIsUserChanged.get(num);
                    if (arrayMap != null) {
                        int size2 = arrayMap.size();
                        for (int i3 = 0; i3 < size2; i3++) {
                            boolean booleanValue = ((Boolean) arrayMap.keyAt(i3)).booleanValue();
                            ArraySet arraySet = (ArraySet) arrayMap.valueAt(i3);
                            if (arrayMap.size() > 0) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("      ");
                                sb.append(String.join(":", arraySet));
                                sb.append(" (user: ");
                                sb.append(intValue);
                                sb.append(" isPrimary: ");
                                sb.append(booleanValue);
                                sb.append(bool == null ? "" : " isUserChanged: " + bool);
                                sb.append(")");
                                printWriter.println(sb.toString());
                            }
                        }
                    }
                }
                printWriter.println("    Has user set:");
                for (Integer num2 : this.mUserSetServices.keySet()) {
                    int intValue2 = num2.intValue();
                    if (this.mIsUserChanged.get(num2) == null) {
                        printWriter.println(
                                "      userId="
                                        + intValue2
                                        + " value="
                                        + this.mUserSetServices.get(num2));
                    }
                }
            } finally {
            }
        }
        synchronized (this.mMutex) {
            try {
                printWriter.println(
                        "    All "
                                + this.mConfig.caption
                                + "s ("
                                + this.mEnabledServicesForCurrentProfiles.size()
                                + ") enabled for current profiles:");
                Iterator it = this.mEnabledServicesForCurrentProfiles.iterator();
                while (it.hasNext()) {
                    ComponentName componentName = (ComponentName) it.next();
                    if (dumpFilter.matches(componentName)) {
                        printWriter.println("      " + componentName);
                    }
                }
                printWriter.println(
                        "    Live " + this.mConfig.caption + "s (" + this.mServices.size() + "):");
                Iterator it2 = this.mServices.iterator();
                while (it2.hasNext()) {
                    ManagedServiceInfo managedServiceInfo = (ManagedServiceInfo) it2.next();
                    if (dumpFilter.matches(managedServiceInfo.component)) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("      ");
                        sb2.append(managedServiceInfo.component);
                        sb2.append(" (user ");
                        sb2.append(managedServiceInfo.userid);
                        sb2.append("): ");
                        sb2.append(managedServiceInfo.service);
                        sb2.append(managedServiceInfo.isSystem ? " SYSTEM" : "");
                        sb2.append(ManagedServices.this != this ? " GUEST" : "");
                        printWriter.println(sb2.toString());
                    }
                }
            } finally {
            }
        }
        synchronized (this.mSnoozing) {
            sparseSetArray = new SparseSetArray(this.mSnoozing);
        }
        printWriter.println(
                "    Snoozed " + this.mConfig.caption + "s (" + sparseSetArray.size() + "):");
        for (i = 0; i < sparseSetArray.size(); i++) {
            printWriter.println("      User: " + sparseSetArray.keyAt(i));
            Iterator it3 = sparseSetArray.valuesAt(i).iterator();
            while (it3.hasNext()) {
                ComponentName componentName2 = (ComponentName) it3.next();
                ServiceInfo serviceInfo = getServiceInfo(sparseSetArray.keyAt(i), componentName2);
                StringBuilder sb3 = new StringBuilder("        ");
                sb3.append(componentName2.flattenToShortString());
                boolean z = true;
                if (serviceInfo != null
                        && (bundle = serviceInfo.metaData) != null
                        && bundle.containsKey(
                                "android.service.notification.default_autobind_listenerservice")) {
                    z =
                            serviceInfo.metaData.getBoolean(
                                    "android.service.notification.default_autobind_listenerservice",
                                    true);
                }
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        sb3, z ? "" : " (META_DATA_DEFAULT_AUTOBIND=false)", printWriter);
            }
        }
    }

    public abstract void ensureFilters(ServiceInfo serviceInfo, int i);

    public SparseArray getAllowedComponents(IntArray intArray) {
        ArraySet arraySet;
        int size = intArray.size();
        SparseArray sparseArray = new SparseArray();
        for (int i = 0; i < size; i++) {
            int i2 = intArray.get(i);
            synchronized (this.mApproved) {
                try {
                    ArrayMap arrayMap = (ArrayMap) this.mApproved.get(Integer.valueOf(i2));
                    if (arrayMap != null) {
                        int size2 = arrayMap.size();
                        for (int i3 = 0; i3 < size2; i3++) {
                            ArraySet arraySet2 = (ArraySet) sparseArray.get(i2);
                            if (arraySet2 == null) {
                                arraySet2 = new ArraySet();
                                sparseArray.put(i2, arraySet2);
                            }
                            ArraySet arraySet3 = (ArraySet) arrayMap.valueAt(i3);
                            if (arraySet3 == null || arraySet3.size() == 0) {
                                arraySet = new ArraySet();
                            } else {
                                arraySet = new ArraySet(arraySet3.size());
                                for (int i4 = 0; i4 < arraySet3.size(); i4++) {
                                    String str = (String) arraySet3.valueAt(i4);
                                    if (!TextUtils.isEmpty(str)) {
                                        ComponentName unflattenFromString =
                                                ComponentName.unflattenFromString(str);
                                        if (unflattenFromString != null) {
                                            arraySet.add(unflattenFromString);
                                        } else {
                                            arraySet.addAll(
                                                    (Collection)
                                                            queryPackageForServices(0, i2, str));
                                        }
                                    }
                                }
                            }
                            arraySet2.addAll(arraySet);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return sparseArray;
    }

    public final List getAllowedComponents(int i) {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mApproved) {
            try {
                ArrayMap arrayMap =
                        (ArrayMap) this.mApproved.getOrDefault(Integer.valueOf(i), new ArrayMap());
                for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                    ArraySet arraySet = (ArraySet) arrayMap.valueAt(i2);
                    for (int i3 = 0; i3 < arraySet.size(); i3++) {
                        ComponentName unflattenFromString =
                                ComponentName.unflattenFromString((String) arraySet.valueAt(i3));
                        if (unflattenFromString != null) {
                            arrayList.add(unflattenFromString);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public final List getAllowedPackages(int i) {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mApproved) {
            try {
                ArrayMap arrayMap =
                        (ArrayMap) this.mApproved.getOrDefault(Integer.valueOf(i), new ArrayMap());
                for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                    ArraySet arraySet = (ArraySet) arrayMap.valueAt(i2);
                    for (int i3 = 0; i3 < arraySet.size(); i3++) {
                        String packageName = getPackageName((String) arraySet.valueAt(i3));
                        if (!TextUtils.isEmpty(packageName)) {
                            arrayList.add(packageName);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public final String getApprovedValue(String str) {
        if (this.mApprovalLevel != 1) {
            return getPackageName(str);
        }
        if (ComponentName.unflattenFromString(str) != null) {
            return str;
        }
        return null;
    }

    public int getBindFlags() {
        return 83886081;
    }

    public abstract Config getConfig();

    public final Set getRemovableConnectedServices() {
        ArraySet arraySet = new ArraySet();
        Iterator it = this.mServices.iterator();
        while (it.hasNext()) {
            ManagedServiceInfo managedServiceInfo = (ManagedServiceInfo) it.next();
            if (!managedServiceInfo.isSystem && ManagedServices.this == this) {
                arraySet.add(managedServiceInfo);
            }
        }
        return arraySet;
    }

    public abstract String getRequiredPermission();

    public final ManagedServiceInfo getServiceFromTokenLocked(IInterface iInterface) {
        if (iInterface == null) {
            return null;
        }
        IBinder asBinder = iInterface.asBinder();
        synchronized (this.mMutex) {
            try {
                int size = this.mServices.size();
                for (int i = 0; i < size; i++) {
                    ManagedServiceInfo managedServiceInfo =
                            (ManagedServiceInfo) this.mServices.get(i);
                    if (managedServiceInfo.service.asBinder() == asBinder) {
                        return managedServiceInfo;
                    }
                }
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final ServiceInfo getServiceInfo(int i, ComponentName componentName) {
        try {
            return this.mPm.getServiceInfo(componentName, 786560L, i);
        } catch (RemoteException e) {
            e.rethrowFromSystemServer();
            return null;
        }
    }

    public final List getServices() {
        ArrayList arrayList;
        synchronized (this.mMutex) {
            arrayList = new ArrayList(this.mServices);
        }
        return arrayList;
    }

    public boolean isBound(ComponentName componentName, int i) {
        boolean contains;
        Pair create = Pair.create(componentName, Integer.valueOf(i));
        synchronized (this.mMutex) {
            contains = this.mServicesBound.contains(create);
        }
        return contains;
    }

    public final boolean isPackageAllowed(int i, String str) {
        if (str == null) {
            return false;
        }
        synchronized (this.mApproved) {
            try {
                ArrayMap arrayMap =
                        (ArrayMap) this.mApproved.getOrDefault(Integer.valueOf(i), new ArrayMap());
                for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                    Iterator it = ((ArraySet) arrayMap.valueAt(i2)).iterator();
                    while (it.hasNext()) {
                        String str2 = (String) it.next();
                        ComponentName unflattenFromString = ComponentName.unflattenFromString(str2);
                        if (unflattenFromString != null) {
                            if (str.equals(unflattenFromString.getPackageName())) {
                                return true;
                            }
                        } else if (str.equals(str2)) {
                            return true;
                        }
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isPackageOrComponentAllowed(int i, String str) {
        synchronized (this.mApproved) {
            try {
                ArrayMap arrayMap =
                        (ArrayMap) this.mApproved.getOrDefault(Integer.valueOf(i), new ArrayMap());
                for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                    if (((ArraySet) arrayMap.valueAt(i2)).contains(str)) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isSameUser(IInterface iInterface, int i) {
        checkNotNull(iInterface);
        synchronized (this.mMutex) {
            try {
                ManagedServiceInfo serviceFromTokenLocked = getServiceFromTokenLocked(iInterface);
                if (serviceFromTokenLocked == null) {
                    return false;
                }
                return serviceFromTokenLocked.isSameUser(i);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public boolean isValidEntry(int i, String str) {
        return !TextUtils.isEmpty(str)
                && queryPackageForServices(0, i, getPackageName(str)).size() > 0;
    }

    public abstract void loadDefaultsFromConfig();

    public final void migrateToXml() {
        for (UserInfo userInfo : this.mUm.getUsers()) {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            if (!TextUtils.isEmpty(getConfig().secureSettingName)) {
                String stringForUser =
                        Settings.Secure.getStringForUser(
                                contentResolver, getConfig().secureSettingName, userInfo.id);
                addApprovedList(userInfo.id, stringForUser, stringForUser, true);
            }
            if (!TextUtils.isEmpty(getConfig().secondarySettingName)) {
                String stringForUser2 =
                        Settings.Secure.getStringForUser(
                                contentResolver, getConfig().secondarySettingName, userInfo.id);
                addApprovedList(userInfo.id, stringForUser2, stringForUser2, false);
            }
        }
    }

    public void onPackagesChanged(boolean z, String[] strArr, int[] iArr) {
        boolean contains;
        int i;
        int i2;
        ComponentName unflattenFromString;
        boolean equals;
        int i3;
        int i4;
        String[] strArr2 = strArr;
        if (this.DEBUG) {
            synchronized (this.mMutex) {
                String str = this.TAG;
                StringBuilder sb = new StringBuilder("onPackagesChanged removingPackage=");
                sb.append(z);
                sb.append(" pkgList=");
                sb.append(strArr2 == null ? null : Arrays.asList(strArr));
                sb.append(" mEnabledServicesPackageNames=");
                sb.append(this.mEnabledServicesPackageNames);
                Slog.d(str, sb.toString());
            }
        }
        if (strArr2 == null || strArr2.length <= 0) {
            return;
        }
        int i5 = 1;
        if (z && iArr != null) {
            int min = Math.min(strArr2.length, iArr.length);
            int i6 = 0;
            while (i6 < min) {
                String str2 = strArr2[i6];
                int userId = UserHandle.getUserId(iArr[i6]);
                synchronized (this.mApproved) {
                    try {
                        ArrayMap arrayMap = (ArrayMap) this.mApproved.get(Integer.valueOf(userId));
                        if (arrayMap != null) {
                            int size = arrayMap.size();
                            int i7 = 0;
                            while (i7 < size) {
                                ArraySet arraySet = (ArraySet) arrayMap.valueAt(i7);
                                int size2 = arraySet.size() - i5;
                                while (size2 >= 0) {
                                    String str3 = (String) arraySet.valueAt(size2);
                                    if (TextUtils.equals(str2, getPackageName(str3))) {
                                        arraySet.removeAt(size2);
                                        if (this.DEBUG) {
                                            String str4 = this.TAG;
                                            StringBuilder sb2 = new StringBuilder();
                                            i4 = min;
                                            sb2.append("Removing ");
                                            sb2.append(str3);
                                            sb2.append(" from approved list; uninstalled");
                                            Slog.v(str4, sb2.toString());
                                            size2--;
                                            min = i4;
                                        }
                                    }
                                    i4 = min;
                                    size2--;
                                    min = i4;
                                }
                                i7++;
                                i5 = 1;
                            }
                        }
                        i3 = min;
                        ArraySet arraySet2 =
                                (ArraySet) this.mUserSetServices.get(Integer.valueOf(userId));
                        if (arraySet2 != null) {
                            for (int size3 = arraySet2.size() - 1; size3 >= 0; size3--) {
                                String str5 = (String) arraySet2.valueAt(size3);
                                if (TextUtils.equals(str2, getPackageName(str5))) {
                                    arraySet2.removeAt(size3);
                                    if (this.DEBUG) {
                                        Slog.v(
                                                this.TAG,
                                                "Removing "
                                                        + str5
                                                        + " from user-set list; uninstalled");
                                    }
                                }
                            }
                        }
                    } finally {
                    }
                }
                i6++;
                min = i3;
                i5 = 1;
            }
        }
        int length = strArr2.length;
        int i8 = 0;
        boolean z2 = false;
        while (i8 < length) {
            String str6 = strArr2[i8];
            synchronized (this.mMutex) {
                contains = this.mEnabledServicesPackageNames.contains(str6);
            }
            if (contains) {
                z2 = true;
            }
            if (iArr != null && iArr.length > 0) {
                int length2 = iArr.length;
                int i9 = 0;
                while (i9 < length2) {
                    int i10 = iArr[i9];
                    if (isPackageAllowed(UserHandle.getUserId(i10), str6)) {
                        int userId2 = UserHandle.getUserId(i10);
                        synchronized (this.mApproved) {
                            try {
                                ArrayMap arrayMap2 =
                                        (ArrayMap) this.mApproved.get(Integer.valueOf(userId2));
                                if (arrayMap2 == null) {
                                    i = length;
                                } else {
                                    for (int i11 = 0; i11 < arrayMap2.size(); i11++) {
                                        ArraySet arraySet3 = (ArraySet) arrayMap2.valueAt(i11);
                                        int size4 = arraySet3.size() - 1;
                                        while (size4 >= 0) {
                                            String str7 = (String) arraySet3.valueAt(size4);
                                            if (!TextUtils.equals(getPackageName(str7), str6)
                                                    || (unflattenFromString =
                                                                    ComponentName
                                                                            .unflattenFromString(
                                                                                    str7))
                                                            == null) {
                                                i2 = length;
                                            } else {
                                                ServiceInfo serviceInfo =
                                                        getServiceInfo(
                                                                userId2, unflattenFromString);
                                                if (serviceInfo == null) {
                                                    i2 = length;
                                                    equals = false;
                                                } else {
                                                    i2 = length;
                                                    equals =
                                                            this.mConfig.bindPermission.equals(
                                                                    serviceInfo.permission);
                                                }
                                                if (!equals) {
                                                    arraySet3.removeAt(size4);
                                                    if (this.DEBUG) {
                                                        Slog.v(
                                                                this.TAG,
                                                                "Removing "
                                                                        + str7
                                                                        + " from approved list; no"
                                                                        + " bind permission found "
                                                                        + this.mConfig
                                                                                .bindPermission);
                                                    }
                                                }
                                            }
                                            size4--;
                                            length = i2;
                                        }
                                    }
                                    i = length;
                                }
                            } finally {
                            }
                        }
                        z2 = true;
                    } else {
                        i = length;
                    }
                    i9++;
                    length = i;
                }
            }
            i8++;
            length = length;
            strArr2 = strArr;
        }
        if (z2) {
            rebindServices(-1, false);
        }
    }

    public abstract void onServiceAdded(ManagedServiceInfo managedServiceInfo);

    public abstract void onServiceRemovedLocked(ManagedServiceInfo managedServiceInfo);

    public final void onSettingRestored(int i, int i2, String str, String str2) {
        String join;
        String str3;
        if (this.mUseXml) {
            return;
        }
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                "Restored managed service setting: ", str, this.TAG);
        if (this.mConfig.secureSettingName.equals(str)
                || ((str3 = this.mConfig.secondarySettingName) != null && str3.equals(str))) {
            if (i < 26) {
                boolean equals = this.mConfig.secureSettingName.equals(str);
                synchronized (this.mApproved) {
                    join =
                            String.join(
                                    ":",
                                    (ArraySet)
                                            ((ArrayMap)
                                                            this.mApproved.getOrDefault(
                                                                    Integer.valueOf(i2),
                                                                    new ArrayMap()))
                                                    .getOrDefault(
                                                            Boolean.valueOf(equals),
                                                            new ArraySet()));
                }
                if (!TextUtils.isEmpty(join)) {
                    str2 =
                            !TextUtils.isEmpty(str2)
                                    ? AnyMotionDetector$$ExternalSyntheticOutline0.m(
                                            str2, ":", join)
                                    : join;
                }
            }
            if (this instanceof NotificationManagerService.NotificationListeners) {
                Settings.Secure.putStringForUser(this.mContext.getContentResolver(), str, str2, i2);
            }
            Iterator it = this.mUm.getUsers().iterator();
            while (it.hasNext()) {
                addApprovedList(
                        ((UserInfo) it.next()).id,
                        str2,
                        str2,
                        this.mConfig.secureSettingName.equals(str));
            }
            Slog.d(this.TAG, "Done loading approved values from settings");
            rebindServices(i2, false);
        }
    }

    public void onUserRemoved(int i) {
        HermesService$3$$ExternalSyntheticOutline0.m(
                i, "Removing approved services for removed user ", this.TAG);
        synchronized (this.mApproved) {
            this.mApproved.remove(Integer.valueOf(i));
        }
        synchronized (this.mSnoozing) {
            this.mSnoozing.remove(i);
        }
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog();
        timingsTraceAndSlog.traceBegin("ManagedServices.unbindUserServices" + i);
        unbindServicesImpl(i, false);
        timingsTraceAndSlog.traceEnd();
    }

    public void onUserSwitched(int i) {
        if (this.DEBUG) {
            Slog.d(this.TAG, VibrationParam$1$$ExternalSyntheticOutline0.m(i, "onUserSwitched u="));
        }
        unbindOtherUserServices(i);
        rebindServices(i, true);
    }

    public final void populateComponentsToBind(
            SparseArray sparseArray, IntArray intArray, SparseArray sparseArray2) {
        this.mEnabledServicesForCurrentProfiles.clear();
        this.mEnabledServicesPackageNames.clear();
        int size = intArray.size();
        for (int i = 0; i < size; i++) {
            int i2 = intArray.get(i);
            ArraySet arraySet = (ArraySet) sparseArray2.get(i2);
            if (arraySet == null) {
                sparseArray.put(i2, new ArraySet());
            } else {
                HashSet hashSet = new HashSet(arraySet);
                synchronized (this.mSnoozing) {
                    try {
                        ArraySet arraySet2 = this.mSnoozing.get(i2);
                        if (arraySet2 != null) {
                            hashSet.removeAll(arraySet2);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                sparseArray.put(i2, hashSet);
                this.mEnabledServicesForCurrentProfiles.addAll(arraySet);
                for (int i3 = 0; i3 < arraySet.size(); i3++) {
                    this.mEnabledServicesPackageNames.add(
                            ((ComponentName) arraySet.valueAt(i3)).getPackageName());
                }
            }
        }
    }

    public final ArraySet queryPackageForServices(int i, int i2, String str) {
        ArraySet arraySet = new ArraySet();
        PackageManager packageManager = this.mContext.getPackageManager();
        Config config = this.mConfig;
        Intent intent = new Intent(config.serviceInterface);
        if (!TextUtils.isEmpty(str)) {
            intent.setPackage(str);
        }
        List queryIntentServicesAsUser =
                packageManager.queryIntentServicesAsUser(intent, i | 132, i2);
        boolean z = this.DEBUG;
        String str2 = this.TAG;
        if (z) {
            Slog.v(str2, config.serviceInterface + " services: " + queryIntentServicesAsUser);
        }
        if (queryIntentServicesAsUser != null) {
            int size = queryIntentServicesAsUser.size();
            for (int i3 = 0; i3 < size; i3++) {
                ServiceInfo serviceInfo =
                        ((ResolveInfo) queryIntentServicesAsUser.get(i3)).serviceInfo;
                ComponentName componentName =
                        new ComponentName(serviceInfo.packageName, serviceInfo.name);
                if (config.bindPermission.equals(serviceInfo.permission)) {
                    arraySet.add(componentName);
                } else {
                    StringBuilder sb = new StringBuilder("Skipping ");
                    sb.append(config.caption);
                    sb.append(" service ");
                    sb.append(serviceInfo.packageName);
                    sb.append("/");
                    sb.append(serviceInfo.name);
                    sb.append(": it does not require the permission ");
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            sb, config.bindPermission, str2);
                }
            }
        }
        return arraySet;
    }

    public void readExtraTag(TypedXmlPullParser typedXmlPullParser, String str) {}

    public final void readXml(
            TypedXmlPullParser typedXmlPullParser, TriPredicate triPredicate, boolean z, int i) {
        String readStringAttribute = XmlUtils.readStringAttribute(typedXmlPullParser, "version");
        String readStringAttribute2 = XmlUtils.readStringAttribute(typedXmlPullParser, "defaults");
        if (!TextUtils.isEmpty(readStringAttribute2)) {
            String[] split = readStringAttribute2.split(":");
            synchronized (this.mDefaultsLock) {
                for (int i2 = 0; i2 < split.length; i2++) {
                    try {
                        if (!TextUtils.isEmpty(split[i2])) {
                            ComponentName unflattenFromString =
                                    ComponentName.unflattenFromString(split[i2]);
                            if (unflattenFromString != null) {
                                this.mDefaultPackages.add(unflattenFromString.getPackageName());
                                this.mDefaultComponents.add(unflattenFromString);
                            } else {
                                this.mDefaultPackages.add(split[i2]);
                            }
                        }
                    } finally {
                    }
                }
            }
        }
        boolean z2 = false;
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1) {
                break;
            }
            String name = typedXmlPullParser.getName();
            if (next == 3 && getConfig().xmlTag.equals(name)) {
                break;
            }
            if (next == 2) {
                if ("service_listing".equals(name)) {
                    BootReceiver$$ExternalSyntheticOutline0.m59m(
                            new StringBuilder("Read "),
                            this.mConfig.caption,
                            " permissions from xml",
                            this.TAG);
                    String readStringAttribute3 =
                            XmlUtils.readStringAttribute(typedXmlPullParser, "approved");
                    int attributeInt =
                            z ? i : typedXmlPullParser.getAttributeInt((String) null, "user", 0);
                    boolean attributeBoolean =
                            typedXmlPullParser.getAttributeBoolean((String) null, "primary", true);
                    String readStringAttribute4 =
                            XmlUtils.readStringAttribute(typedXmlPullParser, "user_changed");
                    String readStringAttribute5 =
                            XmlUtils.readStringAttribute(typedXmlPullParser, "user_set");
                    String readStringAttribute6 =
                            XmlUtils.readStringAttribute(typedXmlPullParser, "user_set_services");
                    if (!"4".equals(readStringAttribute)) {
                        if (readStringAttribute6 == null) {
                            if (readStringAttribute5 == null
                                    || !Boolean.valueOf(readStringAttribute5).booleanValue()) {
                                readStringAttribute6 = "";
                            } else {
                                synchronized (this.mApproved) {
                                    this.mIsUserChanged.put(
                                            Integer.valueOf(attributeInt), Boolean.TRUE);
                                }
                                readStringAttribute6 = readStringAttribute3;
                                z2 = false;
                            }
                        }
                        z2 = true;
                    } else if (readStringAttribute4 == null) {
                        readStringAttribute6 = TextUtils.emptyIfNull(readStringAttribute6);
                    } else {
                        synchronized (this.mApproved) {
                            this.mIsUserChanged.put(
                                    Integer.valueOf(attributeInt),
                                    Boolean.valueOf(readStringAttribute4));
                        }
                        readStringAttribute6 =
                                Boolean.valueOf(readStringAttribute4).booleanValue()
                                        ? readStringAttribute3
                                        : "";
                        if (z && readStringAttribute3.isEmpty()) {
                            clearApprovedList();
                        }
                    }
                    if (triPredicate == null
                            || triPredicate.test(
                                    getPackageName(readStringAttribute3),
                                    Integer.valueOf(attributeInt),
                                    getRequiredPermission())
                            || readStringAttribute3.isEmpty()) {
                        if (this.mUm.getUserInfo(attributeInt) != null) {
                            addApprovedList(
                                    attributeInt,
                                    readStringAttribute3,
                                    readStringAttribute6,
                                    attributeBoolean);
                        }
                        this.mUseXml = true;
                    }
                } else {
                    readExtraTag(typedXmlPullParser, name);
                }
            }
        }
        if (TextUtils.isEmpty(readStringAttribute)
                || "1".equals(readStringAttribute)
                || "2".equals(readStringAttribute)
                || "3".equals(readStringAttribute)) {
            upgradeDefaultsXmlVersion();
        }
        if (z2) {
            upgradeUserSet();
        }
        rebindServices(-1, false);
    }

    public final void rebindServices(int i, boolean z) {
        if (this.DEBUG) {
            Slog.d(this.TAG, "rebindServices " + z + " " + i);
        }
        IntArray currentProfileIds = this.mUserProfiles.getCurrentProfileIds();
        if (this.mUserProfiles.isProfileUser(this.mContext, i)) {
            allowRebindForParentUser();
        }
        SparseArray sparseArray = new SparseArray();
        SparseArray sparseArray2 = new SparseArray();
        synchronized (this.mMutex) {
            SparseArray allowedComponents = getAllowedComponents(currentProfileIds);
            Set removableConnectedServices = getRemovableConnectedServices();
            populateComponentsToBind(sparseArray, currentProfileIds, allowedComponents);
            populateComponentsToUnbind(z, removableConnectedServices, sparseArray, sparseArray2);
        }
        unbindFromServices(sparseArray2);
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
            int keyAt = sparseArray.keyAt(i2);
            for (ComponentName componentName : (Set) sparseArray.get(keyAt)) {
                ServiceInfo serviceInfo = getServiceInfo(keyAt, componentName);
                if (serviceInfo == null) {
                    Slog.w(
                            this.TAG,
                            "Not binding "
                                    + this.mConfig.caption
                                    + " service "
                                    + componentName
                                    + ": service not found");
                } else if (this.mConfig.bindPermission.equals(serviceInfo.permission)) {
                    Bundle bundle = serviceInfo.metaData;
                    boolean z2 = true;
                    if (!((bundle == null
                                    || !bundle.containsKey(
                                            "android.service.notification.default_autobind_listenerservice"))
                            ? true
                            : serviceInfo.metaData.getBoolean(
                                    "android.service.notification.default_autobind_listenerservice",
                                    true))) {
                        synchronized (this.mMutex) {
                            try {
                                if (!isBound(componentName, keyAt)
                                        && !this.mServicesRebinding.contains(
                                                Pair.create(
                                                        componentName, Integer.valueOf(keyAt)))) {
                                    z2 = false;
                                }
                            } finally {
                            }
                        }
                        if (!z2) {
                            synchronized (this.mSnoozing) {
                                Slog.d(
                                        this.TAG,
                                        "Not binding "
                                                + this.mConfig.caption
                                                + " service "
                                                + componentName
                                                + ": has META_DATA_DEFAULT_AUTOBIND = false");
                                this.mSnoozing.add(keyAt, componentName);
                            }
                        }
                    }
                    String str = this.TAG;
                    StringBuilder sb = new StringBuilder("enabling ");
                    AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                            keyAt, this.mConfig.caption, " for ", ": ", sb);
                    sb.append(componentName);
                    Slog.v(str, sb.toString());
                    registerService(serviceInfo, keyAt);
                } else {
                    String str2 = this.TAG;
                    StringBuilder sb2 = new StringBuilder("Not binding ");
                    sb2.append(this.mConfig.caption);
                    sb2.append(" service ");
                    sb2.append(componentName);
                    sb2.append(": it does not require the permission ");
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            sb2, this.mConfig.bindPermission, str2);
                }
            }
        }
    }

    public void registerService(ComponentName componentName, int i) {
        synchronized (this.mMutex) {
            registerServiceLocked(i, componentName, false);
        }
    }

    public void registerService(ServiceInfo serviceInfo, int i) {
        ensureFilters(serviceInfo, i);
        registerService(serviceInfo.getComponentName(), i);
    }

    public final ManagedServiceInfo registerServiceImpl(ManagedServiceInfo managedServiceInfo) {
        synchronized (this.mMutex) {
            try {
                try {
                    managedServiceInfo.service.asBinder().linkToDeath(managedServiceInfo, 0);
                    this.mServices.add(managedServiceInfo);
                } catch (RemoteException unused) {
                    return null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return managedServiceInfo;
    }

    public final void registerServiceLocked(int i, ComponentName componentName, boolean z) {
        Config config;
        ApplicationInfo applicationInfo;
        boolean z2 = this.DEBUG;
        String str = this.TAG;
        if (z2) {
            Slog.v(str, "registerService: " + componentName + " u=" + i);
        }
        Pair create = Pair.create(componentName, Integer.valueOf(i));
        if (this.mServicesBound.contains(create)) {
            Slog.v(str, "Not registering " + componentName + " is already bound");
            return;
        }
        this.mServicesBound.add(create);
        int size = this.mServices.size() - 1;
        while (true) {
            config = this.mConfig;
            if (size < 0) {
                break;
            }
            ManagedServiceInfo managedServiceInfo = (ManagedServiceInfo) this.mServices.get(size);
            if (componentName.equals(managedServiceInfo.component)
                    && managedServiceInfo.userid == i) {
                Slog.v(
                        str,
                        "    disconnecting old "
                                + config.caption
                                + ": "
                                + managedServiceInfo.service);
                removeServiceLocked(size);
                ServiceConnection serviceConnection = managedServiceInfo.connection;
                if (serviceConnection != null) {
                    unbindService(
                            serviceConnection,
                            managedServiceInfo.component,
                            managedServiceInfo.userid);
                }
            }
            size--;
        }
        Intent intent = new Intent(config.serviceInterface);
        intent.setComponent(componentName);
        intent.putExtra("android.intent.extra.client_label", config.clientLabel);
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setPendingIntentCreatorBackgroundActivityStartMode(2);
        intent.putExtra(
                "android.intent.extra.client_intent",
                PendingIntent.getActivity(
                        this.mContext,
                        0,
                        new Intent(config.settingsAction),
                        67108864,
                        makeBasic.toBundle()));
        try {
            applicationInfo =
                    this.mContext
                            .getPackageManager()
                            .getApplicationInfoAsUser(componentName.getPackageName(), 0, i);
        } catch (PackageManager.NameNotFoundException unused) {
            applicationInfo = null;
        }
        int i2 = applicationInfo != null ? applicationInfo.targetSdkVersion : 1;
        int i3 = applicationInfo != null ? applicationInfo.uid : -1;
        try {
            Slog.v(str, "binding: " + intent);
            if (this.mContext.bindServiceAsUser(
                    intent,
                    new AnonymousClass1(i, create, z, i2, i3),
                    getBindFlags(),
                    new UserHandle(i))) {
                return;
            }
            this.mServicesBound.remove(create);
            Slog.w(
                    str,
                    "Unable to bind " + config.caption + " service: " + intent + " in user " + i);
        } catch (SecurityException e) {
            this.mServicesBound.remove(create);
            Slog.e(str, "Unable to bind " + config.caption + " service: " + intent, e);
        }
    }

    public final ManagedServiceInfo removeServiceImpl(IInterface iInterface, int i) {
        ManagedServiceInfo managedServiceInfo;
        if (this.DEBUG) {
            Slog.d(this.TAG, "removeServiceImpl service=" + iInterface + " u=" + i);
        }
        synchronized (this.mMutex) {
            try {
                managedServiceInfo = null;
                for (int size = this.mServices.size() - 1; size >= 0; size--) {
                    ManagedServiceInfo managedServiceInfo2 =
                            (ManagedServiceInfo) this.mServices.get(size);
                    if (managedServiceInfo2.service.asBinder() == iInterface.asBinder()
                            && managedServiceInfo2.userid == i) {
                        Slog.d(
                                this.TAG,
                                "Removing active service " + managedServiceInfo2.component);
                        managedServiceInfo = removeServiceLocked(size);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return managedServiceInfo;
    }

    public final ManagedServiceInfo removeServiceLocked(int i) {
        ManagedServiceInfo managedServiceInfo = (ManagedServiceInfo) this.mServices.remove(i);
        onServiceRemovedLocked(managedServiceInfo);
        return managedServiceInfo;
    }

    public void reregisterService(ComponentName componentName, int i) {
        ServiceInfo serviceInfo;
        boolean z = false;
        if ((isPackageOrComponentAllowed(i, componentName.flattenToString())
                        || isPackageOrComponentAllowed(i, componentName.getPackageName()))
                && (serviceInfo = getServiceInfo(i, componentName)) != null) {
            z = this.mConfig.bindPermission.equals(serviceInfo.permission);
        }
        if (z) {
            registerService(componentName, i);
        }
    }

    public final ArrayMap resetComponents(int i, String str) {
        ArrayList arrayList;
        ArrayList arrayList2;
        boolean z;
        ArraySet arraySet = new ArraySet(getAllowedComponents(i));
        synchronized (this.mDefaultsLock) {
            try {
                arrayList = new ArrayList(this.mDefaultComponents.size());
                arrayList2 = new ArrayList(this.mDefaultComponents.size());
                for (int i2 = 0; i2 < this.mDefaultComponents.size() && arraySet.size() > 0; i2++) {
                    ComponentName componentName =
                            (ComponentName) this.mDefaultComponents.valueAt(i2);
                    if (str.equals(componentName.getPackageName())
                            && !arraySet.contains(componentName)) {
                        arrayList.add(componentName);
                    }
                }
                synchronized (this.mApproved) {
                    try {
                        ArrayMap arrayMap = (ArrayMap) this.mApproved.get(Integer.valueOf(i));
                        if (arrayMap != null) {
                            int size = arrayMap.size();
                            z = false;
                            for (int i3 = 0; i3 < size; i3++) {
                                ArraySet arraySet2 = (ArraySet) arrayMap.valueAt(i3);
                                for (int i4 = 0; i4 < arraySet.size(); i4++) {
                                    ComponentName componentName2 =
                                            (ComponentName) arraySet.valueAt(i4);
                                    if (str.equals(componentName2.getPackageName())
                                            && !this.mDefaultComponents.contains(componentName2)
                                            && arraySet2.remove(componentName2.flattenToString())) {
                                        arrayList2.add(componentName2);
                                        String approvedValue =
                                                getApprovedValue(componentName2.flattenToString());
                                        ArraySet arraySet3 =
                                                (ArraySet)
                                                        this.mUserSetServices.get(
                                                                Integer.valueOf(i));
                                        if (arraySet3 != null) {
                                            arraySet3.remove(approvedValue);
                                        }
                                        z = true;
                                    }
                                }
                                for (int i5 = 0; i5 < arrayList.size(); i5++) {
                                    z |=
                                            arraySet2.add(
                                                    ((ComponentName) arrayList.get(i5))
                                                            .flattenToString());
                                }
                            }
                        } else {
                            z = false;
                        }
                    } finally {
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (z) {
            rebindServices(-1, false);
        }
        ArrayMap arrayMap2 = new ArrayMap();
        arrayMap2.put(Boolean.TRUE, arrayList);
        arrayMap2.put(Boolean.FALSE, arrayList2);
        return arrayMap2;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setComponentState(int r4, android.content.ComponentName r5, boolean r6) {
        /*
            r3 = this;
            android.util.SparseSetArray r0 = r3.mSnoozing
            monitor-enter(r0)
            android.util.SparseSetArray r1 = r3.mSnoozing     // Catch: java.lang.Throwable -> Lf
            boolean r1 = r1.contains(r4, r5)     // Catch: java.lang.Throwable -> Lf
            r1 = r1 ^ 1
            if (r1 != r6) goto L12
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lf
            return
        Lf:
            r3 = move-exception
            goto L97
        L12:
            if (r6 == 0) goto L1a
            android.util.SparseSetArray r1 = r3.mSnoozing     // Catch: java.lang.Throwable -> Lf
            r1.remove(r4, r5)     // Catch: java.lang.Throwable -> Lf
            goto L1f
        L1a:
            android.util.SparseSetArray r1 = r3.mSnoozing     // Catch: java.lang.Throwable -> Lf
            r1.add(r4, r5)     // Catch: java.lang.Throwable -> Lf
        L1f:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lf
            java.lang.String r0 = r3.TAG
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            if (r6 == 0) goto L2c
            java.lang.String r2 = "Enabling "
            goto L2e
        L2c:
            java.lang.String r2 = "Disabling "
        L2e:
            r1.append(r2)
            java.lang.String r2 = "component "
            r1.append(r2)
            java.lang.String r2 = r5.flattenToShortString()
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Slog.d(r0, r1)
            java.lang.Object r1 = r3.mMutex
            monitor-enter(r1)
            if (r6 == 0) goto L90
            java.lang.String r6 = r5.flattenToString()     // Catch: java.lang.Throwable -> L8e
            boolean r6 = r3.isPackageOrComponentAllowed(r4, r6)     // Catch: java.lang.Throwable -> L8e
            r0 = 0
            if (r6 != 0) goto L60
            java.lang.String r6 = r5.getPackageName()     // Catch: java.lang.Throwable -> L8e
            boolean r6 = r3.isPackageOrComponentAllowed(r4, r6)     // Catch: java.lang.Throwable -> L8e
            if (r6 != 0) goto L60
        L5e:
            r6 = r0
            goto L71
        L60:
            android.content.pm.ServiceInfo r6 = r3.getServiceInfo(r4, r5)     // Catch: java.lang.Throwable -> L8e
            if (r6 != 0) goto L67
            goto L5e
        L67:
            com.android.server.notification.ManagedServices$Config r2 = r3.mConfig     // Catch: java.lang.Throwable -> L8e
            java.lang.String r2 = r2.bindPermission     // Catch: java.lang.Throwable -> L8e
            java.lang.String r6 = r6.permission     // Catch: java.lang.Throwable -> L8e
            boolean r6 = r2.equals(r6)     // Catch: java.lang.Throwable -> L8e
        L71:
            if (r6 == 0) goto L77
            r3.registerServiceLocked(r4, r5, r0)     // Catch: java.lang.Throwable -> L8e
            goto L93
        L77:
            java.lang.String r3 = r3.TAG     // Catch: java.lang.Throwable -> L8e
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L8e
            r4.<init>()     // Catch: java.lang.Throwable -> L8e
            r4.append(r5)     // Catch: java.lang.Throwable -> L8e
            java.lang.String r5 = " no longer has permission to be bound"
            r4.append(r5)     // Catch: java.lang.Throwable -> L8e
            java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L8e
            android.util.Slog.d(r3, r4)     // Catch: java.lang.Throwable -> L8e
            goto L93
        L8e:
            r3 = move-exception
            goto L95
        L90:
            r3.unregisterServiceLocked(r4, r5)     // Catch: java.lang.Throwable -> L8e
        L93:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L8e
            return
        L95:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L8e
            throw r3
        L97:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lf
            throw r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.notification.ManagedServices.setComponentState(int,"
                    + " android.content.ComponentName, boolean):void");
    }

    public void setPackageOrComponentEnabled(int i, String str, boolean z, boolean z2, boolean z3) {
        String str2 = this.TAG;
        StringBuilder sb = new StringBuilder();
        sb.append(z2 ? " Allowing " : "Disallowing ");
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                sb, this.mConfig.caption, " ", str, " (userSet: ");
        sb.append(z3);
        sb.append(")");
        Slog.i(str2, sb.toString());
        synchronized (this.mApproved) {
            try {
                ArrayMap arrayMap = (ArrayMap) this.mApproved.get(Integer.valueOf(i));
                if (arrayMap == null) {
                    arrayMap = new ArrayMap();
                    this.mApproved.put(Integer.valueOf(i), arrayMap);
                }
                ArraySet arraySet = (ArraySet) arrayMap.get(Boolean.valueOf(z));
                if (arraySet == null) {
                    arraySet = new ArraySet();
                    arrayMap.put(Boolean.valueOf(z), arraySet);
                }
                String approvedValue = getApprovedValue(str);
                if (approvedValue != null) {
                    if (z2) {
                        arraySet.add(approvedValue);
                    } else {
                        arraySet.remove(approvedValue);
                    }
                }
                ArraySet arraySet2 = (ArraySet) this.mUserSetServices.get(Integer.valueOf(i));
                if (arraySet2 == null) {
                    arraySet2 = new ArraySet();
                    this.mUserSetServices.put(Integer.valueOf(i), arraySet2);
                }
                if (z3) {
                    arraySet2.add(str);
                } else {
                    arraySet2.remove(str);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        rebindServices(i, false);
    }

    public final void unbindFromServices(SparseArray sparseArray) {
        for (int i = 0; i < sparseArray.size(); i++) {
            int keyAt = sparseArray.keyAt(i);
            for (ComponentName componentName : (Set) sparseArray.get(keyAt)) {
                StringBuilder sb = new StringBuilder("disabling ");
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        keyAt, this.mConfig.caption, " for user ", ": ", sb);
                sb.append(componentName);
                Slog.v(this.TAG, sb.toString());
                synchronized (this.mMutex) {
                    unregisterServiceLocked(keyAt, componentName);
                }
            }
        }
    }

    public void unbindOtherUserServices(int i) {
        TimingsTraceAndSlog timingsTraceAndSlog = new TimingsTraceAndSlog();
        timingsTraceAndSlog.traceBegin("ManagedServices.unbindOtherUserServices_current" + i);
        unbindServicesImpl(i, true);
        timingsTraceAndSlog.traceEnd();
    }

    public final void unbindService(
            ServiceConnection serviceConnection, ComponentName componentName, int i) {
        try {
            this.mContext.unbindService(serviceConnection);
        } catch (IllegalArgumentException e) {
            Slog.e(
                    this.TAG,
                    this.mConfig.caption + " " + componentName + " could not be unbound",
                    e);
        }
        synchronized (this.mMutex) {
            this.mServicesBound.remove(Pair.create(componentName, Integer.valueOf(i)));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x002b, code lost:

       if (r3.userid != r7) goto L32;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void unbindServicesImpl(int r7, boolean r8) {
        /*
            r6 = this;
            android.util.SparseArray r0 = new android.util.SparseArray
            r0.<init>()
            java.lang.Object r1 = r6.mMutex
            monitor-enter(r1)
            java.util.Set r2 = r6.getRemovableConnectedServices()     // Catch: java.lang.Throwable -> L25
            android.util.ArraySet r2 = (android.util.ArraySet) r2     // Catch: java.lang.Throwable -> L25
            java.util.Iterator r2 = r2.iterator()     // Catch: java.lang.Throwable -> L25
        L12:
            boolean r3 = r2.hasNext()     // Catch: java.lang.Throwable -> L25
            if (r3 == 0) goto L45
            java.lang.Object r3 = r2.next()     // Catch: java.lang.Throwable -> L25
            com.android.server.notification.ManagedServices$ManagedServiceInfo r3 = (com.android.server.notification.ManagedServices.ManagedServiceInfo) r3     // Catch: java.lang.Throwable -> L25
            if (r8 == 0) goto L27
            int r4 = r3.userid     // Catch: java.lang.Throwable -> L25
            if (r4 != r7) goto L2d
            goto L27
        L25:
            r6 = move-exception
            goto L4a
        L27:
            if (r8 != 0) goto L12
            int r4 = r3.userid     // Catch: java.lang.Throwable -> L25
            if (r4 != r7) goto L12
        L2d:
            int r4 = r3.userid     // Catch: java.lang.Throwable -> L25
            android.util.ArraySet r5 = new android.util.ArraySet     // Catch: java.lang.Throwable -> L25
            r5.<init>()     // Catch: java.lang.Throwable -> L25
            java.lang.Object r4 = r0.get(r4, r5)     // Catch: java.lang.Throwable -> L25
            java.util.Set r4 = (java.util.Set) r4     // Catch: java.lang.Throwable -> L25
            android.content.ComponentName r5 = r3.component     // Catch: java.lang.Throwable -> L25
            r4.add(r5)     // Catch: java.lang.Throwable -> L25
            int r3 = r3.userid     // Catch: java.lang.Throwable -> L25
            r0.put(r3, r4)     // Catch: java.lang.Throwable -> L25
            goto L12
        L45:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L25
            r6.unbindFromServices(r0)
            return
        L4a:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L25
            throw r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.notification.ManagedServices.unbindServicesImpl(int,"
                    + " boolean):void");
    }

    public final void unregisterServiceLocked(int i, ComponentName componentName) {
        for (int size = this.mServices.size() - 1; size >= 0; size--) {
            ManagedServiceInfo managedServiceInfo = (ManagedServiceInfo) this.mServices.get(size);
            if (componentName.equals(managedServiceInfo.component)
                    && managedServiceInfo.userid == i) {
                removeServiceLocked(size);
                ServiceConnection serviceConnection = managedServiceInfo.connection;
                if (serviceConnection != null) {
                    unbindService(
                            serviceConnection,
                            managedServiceInfo.component,
                            managedServiceInfo.userid);
                }
            }
        }
    }

    public final void upgradeDefaultsXmlVersion() {
        int size;
        int size2;
        synchronized (this.mDefaultsLock) {
            size = this.mDefaultComponents.size() + this.mDefaultPackages.size();
        }
        if (size == 0) {
            int i = 0;
            if (this.mApprovalLevel == 1) {
                List allowedComponents = getAllowedComponents(0);
                int i2 = 0;
                while (true) {
                    ArrayList arrayList = (ArrayList) allowedComponents;
                    if (i2 >= arrayList.size()) {
                        break;
                    }
                    addDefaultComponentOrPackage(
                            ((ComponentName) arrayList.get(i2)).flattenToString());
                    i2++;
                }
            }
            if (this.mApprovalLevel == 0) {
                List allowedPackages = getAllowedPackages(0);
                while (true) {
                    ArrayList arrayList2 = (ArrayList) allowedPackages;
                    if (i >= arrayList2.size()) {
                        break;
                    }
                    addDefaultComponentOrPackage((String) arrayList2.get(i));
                    i++;
                }
            }
        }
        synchronized (this.mDefaultsLock) {
            size2 = this.mDefaultComponents.size() + this.mDefaultPackages.size();
        }
        if (size2 == 0) {
            loadDefaultsFromConfig();
        }
    }

    public void upgradeUserSet() {}

    public void writeDefaults(TypedXmlSerializer typedXmlSerializer) {
        synchronized (this.mDefaultsLock) {
            try {
                ArrayList arrayList = new ArrayList(this.mDefaultComponents.size());
                for (int i = 0; i < this.mDefaultComponents.size(); i++) {
                    arrayList.add(
                            ((ComponentName) this.mDefaultComponents.valueAt(i)).flattenToString());
                }
                typedXmlSerializer.attribute(
                        (String) null, "defaults", String.join(":", arrayList));
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void writeExtraXmlTags(TypedXmlSerializer typedXmlSerializer) {}

    public final void writeXml(int i, TypedXmlSerializer typedXmlSerializer, boolean z) {
        int i2 = i;
        typedXmlSerializer.startTag((String) null, getConfig().xmlTag);
        typedXmlSerializer.attributeInt((String) null, "version", Integer.parseInt("4"));
        writeDefaults(typedXmlSerializer);
        if (z) {
            synchronized (this.mApproved) {
                try {
                    ArrayMap arrayMap = (ArrayMap) this.mApproved.get(Integer.valueOf(i));
                    if (arrayMap != null) {
                        for (int i3 = 0; i3 < arrayMap.size(); i3++) {
                            ArraySet arraySet = (ArraySet) arrayMap.valueAt(i3);
                            for (int size = arraySet.size() - 1; size >= 0; size--) {
                                String str = (String) arraySet.valueAt(size);
                                if (!isValidEntry(i2, str)) {
                                    arraySet.removeAt(size);
                                    Slog.v(
                                            this.TAG,
                                            "Removing "
                                                    + str
                                                    + " from approved list; no matching services"
                                                    + " found");
                                } else if (this.DEBUG) {
                                    Slog.v(
                                            this.TAG,
                                            "Keeping "
                                                    + str
                                                    + " on approved list; matching services found");
                                }
                            }
                        }
                    }
                } finally {
                }
            }
        }
        synchronized (this.mApproved) {
            try {
                int size2 = this.mApproved.size();
                int i4 = 0;
                while (i4 < size2) {
                    Integer num = (Integer) this.mApproved.keyAt(i4);
                    int intValue = num.intValue();
                    if (!z || intValue == i2) {
                        ArrayMap arrayMap2 = (ArrayMap) this.mApproved.valueAt(i4);
                        Boolean bool = (Boolean) this.mIsUserChanged.get(num);
                        if (arrayMap2 != null) {
                            int size3 = arrayMap2.size();
                            for (int i5 = 0; i5 < size3; i5++) {
                                boolean booleanValue =
                                        ((Boolean) arrayMap2.keyAt(i5)).booleanValue();
                                Set set = (Set) arrayMap2.valueAt(i5);
                                Set set2 = (Set) this.mUserSetServices.get(num);
                                if (set != null || set2 != null || bool != null) {
                                    String join = set == null ? "" : String.join(":", set);
                                    typedXmlSerializer.startTag((String) null, "service_listing");
                                    typedXmlSerializer.attribute((String) null, "approved", join);
                                    typedXmlSerializer.attributeInt(
                                            (String) null, "user", intValue);
                                    typedXmlSerializer.attributeBoolean(
                                            (String) null, "primary", booleanValue);
                                    if (bool != null) {
                                        typedXmlSerializer.attributeBoolean(
                                                (String) null, "user_changed", bool.booleanValue());
                                    } else if (set2 != null) {
                                        typedXmlSerializer.attribute(
                                                (String) null,
                                                "user_set_services",
                                                String.join(":", set2));
                                    }
                                    typedXmlSerializer.endTag((String) null, "service_listing");
                                    if (!z
                                            && booleanValue
                                            && (this
                                                    instanceof
                                                    NotificationManagerService
                                                            .NotificationListeners)) {
                                        Settings.Secure.putStringForUser(
                                                this.mContext.getContentResolver(),
                                                getConfig().secureSettingName,
                                                join,
                                                intValue);
                                    }
                                }
                            }
                        }
                    }
                    i4++;
                    i2 = i;
                }
            } finally {
            }
        }
        writeExtraXmlTags(typedXmlSerializer);
        typedXmlSerializer.endTag((String) null, getConfig().xmlTag);
    }
}
