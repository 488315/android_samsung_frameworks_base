package com.android.server.vr;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.INotificationManager;
import android.app.NotificationManager;
import android.app.Vr2dDisplayProperties;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PackageTagsList;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.vr.IPersistentVrStateCallbacks;
import android.service.vr.IVrListener;
import android.service.vr.IVrManager;
import android.service.vr.IVrStateCallbacks;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.content.PackageMonitor;
import com.android.internal.util.DumpUtils;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.PinnerService$$ExternalSyntheticOutline0;
import com.android.server.SystemConfig;
import com.android.server.SystemService;
import com.android.server.input.KeyboardMetricsCollector;
import com.android.server.utils.ManagedApplicationService;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.WindowManagerInternal;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class VrManagerService extends SystemService implements ActivityTaskManagerInternal.ScreenObserver {
    public static final AnonymousClass3 sBinderChecker = new AnonymousClass3();
    public boolean mBootsToVr;
    public EnabledComponentsObserver mComponentObserver;
    public Context mContext;
    public ManagedApplicationService mCurrentVrCompositorService;
    public ComponentName mCurrentVrModeComponent;
    public int mCurrentVrModeUser;
    public ManagedApplicationService mCurrentVrService;
    public ComponentName mDefaultVrService;
    public final AnonymousClass1 mEventCallback;
    public final AnonymousClass2 mHandler;
    public final Object mLock;
    public boolean mLogLimitHit;
    public final ArrayDeque mLoggingDeque;
    public final NotificationAccessManager mNotifAccessManager;
    public final IBinder mOverlayToken;
    public VrState mPendingState;
    public boolean mPersistentVrModeEnabled;
    public final RemoteCallbackList mPersistentVrStateRemoteCallbacks;
    public boolean mRunning2dInVr;
    public boolean mStandby;
    public int mSystemSleepFlags;
    public boolean mUseStandbyToExitVrMode;
    public boolean mUserUnlocked;
    public Vr2dDisplay mVr2dDisplay;
    public int mVrAppProcessId;
    public final AnonymousClass4 mVrManager;
    public boolean mVrModeAllowed;
    public boolean mVrModeEnabled;
    public final RemoteCallbackList mVrStateRemoteCallbacks;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.vr.VrManagerService$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public AnonymousClass1() {
        }

        public final void onServiceEvent(ManagedApplicationService.LogEvent logEvent) {
            ComponentName componentName;
            int i;
            VrManagerService.this.logEvent(logEvent);
            synchronized (VrManagerService.this.mLock) {
                try {
                    ManagedApplicationService managedApplicationService = VrManagerService.this.mCurrentVrService;
                    componentName = managedApplicationService == null ? null : managedApplicationService.mComponent;
                    if (componentName != null && componentName.equals(logEvent.component) && ((i = logEvent.event) == 2 || i == 3)) {
                        VrManagerService.this.callFocusedActivityChangedLocked();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (VrManagerService.this.mBootsToVr || logEvent.event != 4) {
                return;
            }
            if (componentName == null || componentName.equals(logEvent.component)) {
                Slog.e("VrManagerService", "VrListenerSevice has died permanently, leaving system VR mode.");
                VrManagerService.this.setPersistentVrModeEnabled(false);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.vr.VrManagerService$3, reason: invalid class name */
    public final class AnonymousClass3 {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.vr.VrManagerService$6, reason: invalid class name */
    public final class AnonymousClass6 {
        public final /* synthetic */ boolean val$b;
        public final /* synthetic */ ComponentName val$c;
        public final /* synthetic */ int val$pid;

        public AnonymousClass6(int i, ComponentName componentName, boolean z) {
            this.val$c = componentName;
            this.val$b = z;
            this.val$pid = i;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalService {
        public LocalService() {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NotificationAccessManager {
        public final SparseArray mAllowedPackages = new SparseArray();
        public final ArrayMap mNotificationAccessPackageToUserId = new ArrayMap();

        public NotificationAccessManager() {
        }

        public final void update(Collection collection) {
            VrManagerService vrManagerService;
            int currentUser = ActivityManager.getCurrentUser();
            ArraySet arraySet = (ArraySet) this.mAllowedPackages.get(currentUser);
            if (arraySet == null) {
                arraySet = new ArraySet();
            }
            int size = this.mNotificationAccessPackageToUserId.size() - 1;
            while (true) {
                vrManagerService = VrManagerService.this;
                if (size < 0) {
                    break;
                }
                int intValue = ((Integer) this.mNotificationAccessPackageToUserId.valueAt(size)).intValue();
                if (intValue != currentUser) {
                    String str = (String) this.mNotificationAccessPackageToUserId.keyAt(size);
                    VrManagerService.m1038$$Nest$mrevokeNotificationListenerAccess(vrManagerService, str, intValue);
                    NotificationManager notificationManager = (NotificationManager) vrManagerService.mContext.getSystemService(NotificationManager.class);
                    notificationManager.removeAutomaticZenRules(str);
                    notificationManager.setNotificationPolicyAccessGranted(str, false);
                    VrManagerService.m1037$$Nest$mrevokeCoarseLocationPermissionIfNeeded(vrManagerService, str, intValue);
                    this.mNotificationAccessPackageToUserId.removeAt(size);
                }
                size--;
            }
            Iterator it = arraySet.iterator();
            while (it.hasNext()) {
                String str2 = (String) it.next();
                if (!((ArraySet) collection).contains(str2)) {
                    VrManagerService.m1038$$Nest$mrevokeNotificationListenerAccess(vrManagerService, str2, currentUser);
                    NotificationManager notificationManager2 = (NotificationManager) vrManagerService.mContext.getSystemService(NotificationManager.class);
                    notificationManager2.removeAutomaticZenRules(str2);
                    notificationManager2.setNotificationPolicyAccessGranted(str2, false);
                    VrManagerService.m1037$$Nest$mrevokeCoarseLocationPermissionIfNeeded(vrManagerService, str2, currentUser);
                    this.mNotificationAccessPackageToUserId.remove(str2);
                }
            }
            Iterator it2 = ((ArraySet) collection).iterator();
            while (it2.hasNext()) {
                String str3 = (String) it2.next();
                if (!arraySet.contains(str3)) {
                    ((NotificationManager) vrManagerService.mContext.getSystemService(NotificationManager.class)).setNotificationPolicyAccessGranted(str3, true);
                    NotificationManager notificationManager3 = (NotificationManager) vrManagerService.mContext.getSystemService(NotificationManager.class);
                    Iterator it3 = EnabledComponentsObserver.loadComponentNames(vrManagerService.mContext.getPackageManager(), currentUser, "android.service.notification.NotificationListenerService", "android.permission.BIND_NOTIFICATION_LISTENER_SERVICE").iterator();
                    while (it3.hasNext()) {
                        ComponentName componentName = (ComponentName) it3.next();
                        if (Objects.equals(componentName.getPackageName(), str3)) {
                            try {
                                notificationManager3.setNotificationListenerAccessGrantedForUser(componentName, currentUser, true);
                            } catch (Exception e) {
                                Slog.w("VrManagerService", "Could not grant NLS access to package " + str3, e);
                            }
                        }
                    }
                    if ((vrManagerService.mContext.getPackageManager().getPermissionFlags("android.permission.ACCESS_COARSE_LOCATION", str3, new UserHandle(currentUser)) & 3) == 0) {
                        try {
                            vrManagerService.mContext.getPackageManager().grantRuntimePermission(str3, "android.permission.ACCESS_COARSE_LOCATION", new UserHandle(currentUser));
                        } catch (IllegalArgumentException unused) {
                            PinnerService$$ExternalSyntheticOutline0.m("Could not grant coarse location permission, package ", str3, " was removed.", "VrManagerService");
                        }
                    }
                    this.mNotificationAccessPackageToUserId.put(str3, Integer.valueOf(currentUser));
                }
            }
            arraySet.clear();
            arraySet.addAll(collection);
            this.mAllowedPackages.put(currentUser, arraySet);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingEvent implements ManagedApplicationService.LogFormattable {
        public final long timestamp = System.currentTimeMillis();
        public final String what;

        public SettingEvent(String str) {
            this.what = str;
        }

        @Override // com.android.server.utils.ManagedApplicationService.LogFormattable
        public final String toLogString(SimpleDateFormat simpleDateFormat) {
            return simpleDateFormat.format(new Date(this.timestamp)) + "   " + this.what;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class VrState implements ManagedApplicationService.LogFormattable {
        public final ComponentName callingPackage;
        public final boolean enabled;
        public final int processId;
        public final boolean running2dInVr;
        public final ComponentName targetPackageName;
        public final long timestamp = System.currentTimeMillis();
        public final int userId;

        public VrState(boolean z, boolean z2, ComponentName componentName, int i, int i2, ComponentName componentName2) {
            this.enabled = z;
            this.running2dInVr = z2;
            this.userId = i;
            this.processId = i2;
            this.targetPackageName = componentName;
            this.callingPackage = componentName2;
        }

        public VrState(boolean z, boolean z2, ComponentName componentName, int i, int i2, ComponentName componentName2, int i3) {
            this.enabled = z;
            this.running2dInVr = z2;
            this.userId = i;
            this.processId = i2;
            this.targetPackageName = componentName;
            this.callingPackage = componentName2;
        }

        @Override // com.android.server.utils.ManagedApplicationService.LogFormattable
        public final String toLogString(SimpleDateFormat simpleDateFormat) {
            StringBuilder sb = new StringBuilder(simpleDateFormat.format(new Date(this.timestamp)));
            sb.append("  State changed to:  ");
            boolean z = this.enabled;
            sb.append(z ? "ENABLED" : "DISABLED");
            sb.append("\n");
            if (z) {
                sb.append("  User=");
                sb.append(this.userId);
                sb.append("\n  Current VR Activity=");
                ComponentName componentName = this.callingPackage;
                String str = KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG;
                sb.append(componentName == null ? KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG : componentName.flattenToString());
                sb.append("\n  Bound VrListenerService=");
                ComponentName componentName2 = this.targetPackageName;
                if (componentName2 != null) {
                    str = componentName2.flattenToString();
                }
                sb.append(str);
                sb.append("\n");
            }
            return sb.toString();
        }
    }

    /* renamed from: -$$Nest$menforceCallerPermissionAnyOf, reason: not valid java name */
    public static void m1036$$Nest$menforceCallerPermissionAnyOf(VrManagerService vrManagerService, String[] strArr) {
        vrManagerService.getClass();
        for (String str : strArr) {
            if (vrManagerService.mContext.checkCallingOrSelfPermission(str) == 0) {
                return;
            }
        }
        throw new SecurityException("Caller does not hold at least one of the permissions: " + Arrays.toString(strArr));
    }

    /* renamed from: -$$Nest$mrevokeCoarseLocationPermissionIfNeeded, reason: not valid java name */
    public static void m1037$$Nest$mrevokeCoarseLocationPermissionIfNeeded(VrManagerService vrManagerService, String str, int i) {
        if ((vrManagerService.mContext.getPackageManager().getPermissionFlags("android.permission.ACCESS_COARSE_LOCATION", str, new UserHandle(i)) & 3) != 0) {
            return;
        }
        try {
            vrManagerService.mContext.getPackageManager().revokeRuntimePermission(str, "android.permission.ACCESS_COARSE_LOCATION", new UserHandle(i));
        } catch (IllegalArgumentException unused) {
            PinnerService$$ExternalSyntheticOutline0.m("Could not revoke coarse location permission, package ", str, " was removed.", "VrManagerService");
        }
    }

    /* renamed from: -$$Nest$mrevokeNotificationListenerAccess, reason: not valid java name */
    public static void m1038$$Nest$mrevokeNotificationListenerAccess(VrManagerService vrManagerService, String str, int i) {
        NotificationManager notificationManager = (NotificationManager) vrManagerService.mContext.getSystemService(NotificationManager.class);
        for (ComponentName componentName : notificationManager.getEnabledNotificationListeners(i)) {
            if (componentName != null && componentName.getPackageName().equals(str)) {
                notificationManager.setNotificationListenerAccessGrantedForUser(componentName, i, false);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r2v10, types: [com.android.server.vr.VrManagerService$4] */
    /* JADX WARN: Type inference failed for: r2v9, types: [com.android.server.vr.VrManagerService$2] */
    public VrManagerService(Context context) {
        super(context);
        this.mLock = new Object();
        this.mOverlayToken = new Binder();
        this.mVrStateRemoteCallbacks = new RemoteCallbackList();
        this.mPersistentVrStateRemoteCallbacks = new RemoteCallbackList();
        this.mLoggingDeque = new ArrayDeque(64);
        this.mNotifAccessManager = new NotificationAccessManager();
        this.mSystemSleepFlags = 5;
        this.mEventCallback = new AnonymousClass1();
        this.mHandler = new Handler() { // from class: com.android.server.vr.VrManagerService.2
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                boolean z;
                int i = message.what;
                if (i == 0) {
                    z = message.arg1 == 1;
                    int beginBroadcast = VrManagerService.this.mVrStateRemoteCallbacks.beginBroadcast();
                    while (beginBroadcast > 0) {
                        beginBroadcast--;
                        try {
                            VrManagerService.this.mVrStateRemoteCallbacks.getBroadcastItem(beginBroadcast).onVrStateChanged(z);
                        } catch (RemoteException unused) {
                        }
                    }
                    VrManagerService.this.mVrStateRemoteCallbacks.finishBroadcast();
                    return;
                }
                if (i == 1) {
                    synchronized (VrManagerService.this.mLock) {
                        VrManagerService vrManagerService = VrManagerService.this;
                        if (vrManagerService.mVrModeAllowed) {
                            vrManagerService.consumeAndApplyPendingStateLocked(true);
                        }
                    }
                    return;
                }
                if (i != 2) {
                    throw new IllegalStateException("Unknown message type: " + message.what);
                }
                z = message.arg1 == 1;
                int beginBroadcast2 = VrManagerService.this.mPersistentVrStateRemoteCallbacks.beginBroadcast();
                while (beginBroadcast2 > 0) {
                    beginBroadcast2--;
                    try {
                        VrManagerService.this.mPersistentVrStateRemoteCallbacks.getBroadcastItem(beginBroadcast2).onPersistentVrStateChanged(z);
                    } catch (RemoteException unused2) {
                    }
                }
                VrManagerService.this.mPersistentVrStateRemoteCallbacks.finishBroadcast();
            }
        };
        this.mVrManager = new IVrManager.Stub() { // from class: com.android.server.vr.VrManagerService.4
            public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
                ArraySet arraySet;
                if (DumpUtils.checkDumpPermission(VrManagerService.this.mContext, "VrManagerService", printWriter)) {
                    printWriter.println("********* Dump of VrManagerService *********");
                    printWriter.println("VR mode is currently: ".concat(VrManagerService.this.mVrModeAllowed ? "allowed" : "disallowed"));
                    printWriter.println("Persistent VR mode is currently: ".concat(VrManagerService.this.mPersistentVrModeEnabled ? "enabled" : "disabled"));
                    StringBuilder sb = new StringBuilder("Currently bound VR listener service: ");
                    ManagedApplicationService managedApplicationService = VrManagerService.this.mCurrentVrService;
                    StringBuilder m = BinaryTransparencyService$$ExternalSyntheticOutline0.m(printWriter, managedApplicationService == null ? KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG : managedApplicationService.mComponent.flattenToString(), "Currently bound VR compositor service: ", sb);
                    ManagedApplicationService managedApplicationService2 = VrManagerService.this.mCurrentVrCompositorService;
                    m.append(managedApplicationService2 == null ? KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG : managedApplicationService2.mComponent.flattenToString());
                    printWriter.println(m.toString());
                    printWriter.println("Previous state transitions:\n");
                    VrManagerService vrManagerService = VrManagerService.this;
                    vrManagerService.getClass();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
                    synchronized (vrManagerService.mLoggingDeque) {
                        try {
                            if (vrManagerService.mLoggingDeque.size() == 0) {
                                printWriter.print("  ");
                                printWriter.println(KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG);
                            }
                            if (vrManagerService.mLogLimitHit) {
                                printWriter.println("...");
                            }
                            Iterator it = vrManagerService.mLoggingDeque.iterator();
                            while (it.hasNext()) {
                                printWriter.println(((ManagedApplicationService.LogFormattable) it.next()).toLogString(simpleDateFormat));
                            }
                        } finally {
                        }
                    }
                    printWriter.println("\n\nRemote Callbacks:");
                    int beginBroadcast = VrManagerService.this.mVrStateRemoteCallbacks.beginBroadcast();
                    while (true) {
                        int i = beginBroadcast - 1;
                        if (beginBroadcast <= 0) {
                            break;
                        }
                        printWriter.print("  ");
                        printWriter.print(VrManagerService.this.mVrStateRemoteCallbacks.getBroadcastItem(i));
                        if (i > 0) {
                            printWriter.println(",");
                        }
                        beginBroadcast = i;
                    }
                    VrManagerService.this.mVrStateRemoteCallbacks.finishBroadcast();
                    printWriter.println("\n\nPersistent Vr State Remote Callbacks:");
                    int beginBroadcast2 = VrManagerService.this.mPersistentVrStateRemoteCallbacks.beginBroadcast();
                    while (true) {
                        int i2 = beginBroadcast2 - 1;
                        if (beginBroadcast2 <= 0) {
                            break;
                        }
                        printWriter.print("  ");
                        printWriter.print(VrManagerService.this.mPersistentVrStateRemoteCallbacks.getBroadcastItem(i2));
                        if (i2 > 0) {
                            printWriter.println(",");
                        }
                        beginBroadcast2 = i2;
                    }
                    VrManagerService.this.mPersistentVrStateRemoteCallbacks.finishBroadcast();
                    printWriter.println("\n");
                    printWriter.println("Installed VrListenerService components:");
                    VrManagerService vrManagerService2 = VrManagerService.this;
                    int i3 = vrManagerService2.mCurrentVrModeUser;
                    EnabledComponentsObserver enabledComponentsObserver = vrManagerService2.mComponentObserver;
                    synchronized (enabledComponentsObserver.mLock) {
                        try {
                            arraySet = (ArraySet) enabledComponentsObserver.mInstalledSet.get(i3);
                            if (arraySet == null) {
                                arraySet = new ArraySet();
                            }
                        } finally {
                        }
                    }
                    if (arraySet.size() == 0) {
                        printWriter.println(KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG);
                    } else {
                        Iterator it2 = arraySet.iterator();
                        while (it2.hasNext()) {
                            ComponentName componentName = (ComponentName) it2.next();
                            printWriter.print("  ");
                            printWriter.println(componentName.flattenToString());
                        }
                    }
                    printWriter.println("Enabled VrListenerService components:");
                    ArraySet enabled = VrManagerService.this.mComponentObserver.getEnabled(i3);
                    if (enabled.size() == 0) {
                        printWriter.println(KeyboardMetricsCollector.DEFAULT_LANGUAGE_TAG);
                    } else {
                        Iterator it3 = enabled.iterator();
                        while (it3.hasNext()) {
                            ComponentName componentName2 = (ComponentName) it3.next();
                            printWriter.print("  ");
                            printWriter.println(componentName2.flattenToString());
                        }
                    }
                    printWriter.println("\n");
                    printWriter.println("********* End of VrManagerService Dump *********");
                }
            }

            public final boolean getPersistentVrModeEnabled() {
                boolean z;
                VrManagerService.m1036$$Nest$menforceCallerPermissionAnyOf(VrManagerService.this, new String[]{"android.permission.ACCESS_VR_MANAGER", "android.permission.ACCESS_VR_STATE"});
                VrManagerService vrManagerService = VrManagerService.this;
                synchronized (vrManagerService.mLock) {
                    z = vrManagerService.mPersistentVrModeEnabled;
                }
                return z;
            }

            public final int getVr2dDisplayId() {
                Vr2dDisplay vr2dDisplay = VrManagerService.this.mVr2dDisplay;
                int i = -1;
                if (vr2dDisplay != null) {
                    synchronized (vr2dDisplay.mVdLock) {
                        try {
                            VirtualDisplay virtualDisplay = vr2dDisplay.mVirtualDisplay;
                            if (virtualDisplay != null) {
                                i = virtualDisplay.getDisplay().getDisplayId();
                            }
                        } finally {
                        }
                    }
                } else {
                    Slog.w("VrManagerService", "Vr2dDisplay is null!");
                }
                return i;
            }

            public final boolean getVrModeState() {
                boolean z;
                VrManagerService.m1036$$Nest$menforceCallerPermissionAnyOf(VrManagerService.this, new String[]{"android.permission.ACCESS_VR_MANAGER", "android.permission.ACCESS_VR_STATE"});
                VrManagerService vrManagerService = VrManagerService.this;
                synchronized (vrManagerService.mLock) {
                    z = vrManagerService.mVrModeEnabled;
                }
                return z;
            }

            public final void registerListener(IVrStateCallbacks iVrStateCallbacks) {
                VrManagerService.m1036$$Nest$menforceCallerPermissionAnyOf(VrManagerService.this, new String[]{"android.permission.ACCESS_VR_MANAGER", "android.permission.ACCESS_VR_STATE"});
                if (iVrStateCallbacks == null) {
                    throw new IllegalArgumentException("Callback binder object is null.");
                }
                VrManagerService.this.mVrStateRemoteCallbacks.register(iVrStateCallbacks);
            }

            public final void registerPersistentVrStateListener(IPersistentVrStateCallbacks iPersistentVrStateCallbacks) {
                VrManagerService.m1036$$Nest$menforceCallerPermissionAnyOf(VrManagerService.this, new String[]{"android.permission.ACCESS_VR_MANAGER", "android.permission.ACCESS_VR_STATE"});
                if (iPersistentVrStateCallbacks == null) {
                    throw new IllegalArgumentException("Callback binder object is null.");
                }
                VrManagerService.this.mPersistentVrStateRemoteCallbacks.register(iPersistentVrStateCallbacks);
            }

            public final void setAndBindCompositor(String str) {
                VrManagerService.m1036$$Nest$menforceCallerPermissionAnyOf(VrManagerService.this, new String[]{"android.permission.RESTRICTED_VR_ACCESS"});
                VrManagerService vrManagerService = VrManagerService.this;
                ComponentName unflattenFromString = str == null ? null : ComponentName.unflattenFromString(str);
                vrManagerService.getClass();
                int callingUserId = UserHandle.getCallingUserId();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    synchronized (vrManagerService.mLock) {
                        vrManagerService.updateCompositorServiceLocked(callingUserId, unflattenFromString);
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            public final void setPersistentVrModeEnabled(boolean z) {
                VrManagerService.m1036$$Nest$menforceCallerPermissionAnyOf(VrManagerService.this, new String[]{"android.permission.RESTRICTED_VR_ACCESS"});
                VrManagerService.this.setPersistentVrModeEnabled(z);
            }

            public final void setStandbyEnabled(boolean z) {
                VrManagerService.m1036$$Nest$menforceCallerPermissionAnyOf(VrManagerService.this, new String[]{"android.permission.ACCESS_VR_MANAGER"});
                VrManagerService vrManagerService = VrManagerService.this;
                synchronized (vrManagerService.mLock) {
                    try {
                        if (!vrManagerService.mBootsToVr) {
                            Slog.e("VrManagerService", "Attempting to set standby mode on a non-standalone device");
                        } else {
                            vrManagerService.mStandby = z;
                            vrManagerService.updateVrModeAllowedLocked();
                        }
                    } finally {
                    }
                }
            }

            public final void setVr2dDisplayProperties(Vr2dDisplayProperties vr2dDisplayProperties) {
                VrManagerService.m1036$$Nest$menforceCallerPermissionAnyOf(VrManagerService.this, new String[]{"android.permission.RESTRICTED_VR_ACCESS"});
                VrManagerService vrManagerService = VrManagerService.this;
                vrManagerService.getClass();
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    Vr2dDisplay vr2dDisplay = vrManagerService.mVr2dDisplay;
                    if (vr2dDisplay != null) {
                        vr2dDisplay.setVirtualDisplayProperties(vr2dDisplayProperties);
                    } else {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        Slog.w("VrManagerService", "Vr2dDisplay is null!");
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }

            public final void unregisterListener(IVrStateCallbacks iVrStateCallbacks) {
                VrManagerService.m1036$$Nest$menforceCallerPermissionAnyOf(VrManagerService.this, new String[]{"android.permission.ACCESS_VR_MANAGER", "android.permission.ACCESS_VR_STATE"});
                if (iVrStateCallbacks == null) {
                    throw new IllegalArgumentException("Callback binder object is null.");
                }
                VrManagerService.this.mVrStateRemoteCallbacks.unregister(iVrStateCallbacks);
            }

            public final void unregisterPersistentVrStateListener(IPersistentVrStateCallbacks iPersistentVrStateCallbacks) {
                VrManagerService.m1036$$Nest$menforceCallerPermissionAnyOf(VrManagerService.this, new String[]{"android.permission.ACCESS_VR_MANAGER", "android.permission.ACCESS_VR_STATE"});
                if (iPersistentVrStateCallbacks == null) {
                    throw new IllegalArgumentException("Callback binder object is null.");
                }
                VrManagerService.this.mPersistentVrStateRemoteCallbacks.unregister(iPersistentVrStateCallbacks);
            }
        };
    }

    private static native void initializeNative();

    private static native void setVrModeNative(boolean z);

    public final void callFocusedActivityChangedLocked() {
        IVrListener iVrListener;
        ComponentName componentName = this.mCurrentVrModeComponent;
        boolean z = this.mRunning2dInVr;
        int i = this.mVrAppProcessId;
        ManagedApplicationService managedApplicationService = this.mCurrentVrService;
        AnonymousClass6 anonymousClass6 = new AnonymousClass6(i, componentName, z);
        synchronized (managedApplicationService.mLock) {
            try {
                iVrListener = managedApplicationService.mBoundInterface;
                if (iVrListener == null) {
                    managedApplicationService.mPendingEvent = anonymousClass6;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (iVrListener != null) {
            try {
                iVrListener.focusedActivityChanged(componentName, z, i);
            } catch (RemoteException | RuntimeException e) {
                Slog.e("ManagedApplicationService", "Received exception from user service: ", e);
            }
        }
    }

    public final void consumeAndApplyPendingStateLocked(boolean z) {
        VrState vrState = this.mPendingState;
        if (vrState == null) {
            if (z) {
                updateCurrentVrServiceLocked(false, false, null, 0, -1, null);
            }
        } else {
            updateCurrentVrServiceLocked(vrState.enabled, vrState.running2dInVr, vrState.targetPackageName, vrState.userId, vrState.processId, vrState.callingPackage);
            this.mPendingState = null;
        }
    }

    public final void createAndConnectService(int i, ComponentName componentName) {
        ManagedApplicationService managedApplicationService = new ManagedApplicationService(this.mContext, componentName, i, 17043474, "android.settings.VR_LISTENER_SETTINGS", sBinderChecker, this.mBootsToVr ? 1 : 2, this.mHandler, this.mEventCallback);
        this.mCurrentVrService = managedApplicationService;
        managedApplicationService.connect();
        Slog.i("VrManagerService", "Connecting " + componentName + " for user " + i);
    }

    public final void logEvent(ManagedApplicationService.LogFormattable logFormattable) {
        synchronized (this.mLoggingDeque) {
            try {
                if (this.mLoggingDeque.size() == 64) {
                    this.mLoggingDeque.removeFirst();
                    this.mLogLimitHit = true;
                }
                this.mLoggingDeque.add(logFormattable);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
    public final void onAwakeStateChanged(boolean z) {
        setSystemState(1, z);
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 500) {
            ((ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class)).registerScreenObserver(this);
            INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
            synchronized (this.mLock) {
                Looper mainLooper = Looper.getMainLooper();
                Handler handler = new Handler(mainLooper);
                ArrayList arrayList = new ArrayList();
                arrayList.add(this);
                Context context = this.mContext;
                Object obj = this.mLock;
                SettingsObserver settingsObserver = new SettingsObserver(context, handler, Settings.Secure.getUriFor("enabled_vr_listeners"));
                final EnabledComponentsObserver enabledComponentsObserver = new EnabledComponentsObserver(context, obj, arrayList);
                new PackageMonitor() { // from class: com.android.server.vr.EnabledComponentsObserver.1
                    public AnonymousClass1() {
                    }

                    public final boolean onHandleForceStop(Intent intent, String[] strArr, int i2, boolean z) {
                        EnabledComponentsObserver.this.rebuildAll();
                        return super.onHandleForceStop(intent, strArr, i2, z);
                    }

                    public final void onPackageDisappeared(String str, int i2) {
                        EnabledComponentsObserver.this.rebuildAll();
                    }

                    public final void onPackageModified(String str) {
                        EnabledComponentsObserver.this.rebuildAll();
                    }

                    public final void onSomePackagesChanged() {
                        EnabledComponentsObserver.this.rebuildAll();
                    }
                }.register(context, mainLooper, UserHandle.ALL, true);
                ((ArraySet) settingsObserver.mSettingsListeners).add(enabledComponentsObserver);
                this.mComponentObserver = enabledComponentsObserver;
                enabledComponentsObserver.rebuildAll();
            }
            ArraySet arraySet = SystemConfig.getInstance().mDefaultVrComponents;
            if (arraySet.size() > 0) {
                this.mDefaultVrService = (ComponentName) arraySet.valueAt(0);
            } else {
                Slog.i("VrManagerService", "No default vr listener service found.");
            }
            DisplayManager displayManager = (DisplayManager) getContext().getSystemService("display");
            Vr2dDisplay vr2dDisplay = new Vr2dDisplay(displayManager, (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class), this.mVrManager);
            this.mVr2dDisplay = vr2dDisplay;
            getContext();
            boolean z = this.mBootsToVr;
            IVrManager iVrManager = vr2dDisplay.mVrManager;
            if (iVrManager != null) {
                try {
                    iVrManager.registerPersistentVrStateListener(vr2dDisplay.mVrStateCallbacks);
                } catch (RemoteException e) {
                    Log.e("Vr2dDisplay", "Could not register VR State listener.", e);
                }
            }
            vr2dDisplay.mBootsToVr = z;
            if (z) {
                vr2dDisplay.updateVirtualDisplay();
            }
            getContext().registerReceiver(new BroadcastReceiver() { // from class: com.android.server.vr.VrManagerService.5
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context2, Intent intent) {
                    if ("android.intent.action.USER_UNLOCKED".equals(intent.getAction())) {
                        VrManagerService vrManagerService = VrManagerService.this;
                        synchronized (vrManagerService.mLock) {
                            vrManagerService.mUserUnlocked = true;
                            vrManagerService.updateVrModeAllowedLocked();
                        }
                    }
                }
            }, BatteryService$$ExternalSyntheticOutline0.m("android.intent.action.USER_UNLOCKED"));
        }
    }

    @Override // com.android.server.wm.ActivityTaskManagerInternal.ScreenObserver
    public final void onKeyguardStateChanged(boolean z) {
        setSystemState(4, !z);
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        synchronized (this.mLock) {
            initializeNative();
            this.mContext = getContext();
        }
        boolean z = false;
        boolean z2 = SystemProperties.getBoolean("ro.boot.vr", false);
        this.mBootsToVr = z2;
        if (z2 && SystemProperties.getBoolean("persist.vr.use_standby_to_exit_vr_mode", true)) {
            z = true;
        }
        this.mUseStandbyToExitVrMode = z;
        publishLocalService(LocalService.class, new LocalService());
        publishBinderService("vrmanager", asBinder());
    }

    @Override // com.android.server.SystemService
    public final void onUserStarting(SystemService.TargetUser targetUser) {
        synchronized (this.mLock) {
            this.mComponentObserver.rebuildAll();
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserStopped(SystemService.TargetUser targetUser) {
        synchronized (this.mLock) {
            this.mComponentObserver.rebuildAll();
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserStopping(SystemService.TargetUser targetUser) {
        synchronized (this.mLock) {
            this.mComponentObserver.rebuildAll();
        }
    }

    @Override // com.android.server.SystemService
    public final void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        FgThread.getHandler().post(new Runnable() { // from class: com.android.server.vr.VrManagerService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                VrManagerService vrManagerService = VrManagerService.this;
                synchronized (vrManagerService.mLock) {
                    vrManagerService.mComponentObserver.rebuildAll();
                }
            }
        });
    }

    public final void setPersistentModeAndNotifyListenersLocked(boolean z) {
        if (this.mPersistentVrModeEnabled == z) {
            return;
        }
        String concat = "Persistent VR mode ".concat(z ? "enabled" : "disabled");
        Slog.i("VrManagerService", concat);
        logEvent(new SettingEvent(concat));
        this.mPersistentVrModeEnabled = z;
        AnonymousClass2 anonymousClass2 = this.mHandler;
        anonymousClass2.sendMessage(anonymousClass2.obtainMessage(2, z ? 1 : 0, 0));
    }

    public final void setPersistentVrModeEnabled(boolean z) {
        synchronized (this.mLock) {
            try {
                setPersistentModeAndNotifyListenersLocked(z);
                if (!z) {
                    setVrMode(false, null, 0, -1, null);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setSystemState(int i, boolean z) {
        synchronized (this.mLock) {
            try {
                int i2 = this.mSystemSleepFlags;
                if (z) {
                    this.mSystemSleepFlags = i | i2;
                } else {
                    this.mSystemSleepFlags = (~i) & i2;
                }
                if (i2 != this.mSystemSleepFlags) {
                    updateVrModeAllowedLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x001f A[Catch: all -> 0x0010, TryCatch #0 {all -> 0x0010, blocks: (B:33:0x0009, B:7:0x0016, B:11:0x001f, B:12:0x0025, B:14:0x0038, B:15:0x003a, B:19:0x003e, B:21:0x0042, B:23:0x0046, B:24:0x004d, B:25:0x004f, B:27:0x0051, B:28:0x0067), top: B:32:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0038 A[Catch: all -> 0x0010, TryCatch #0 {all -> 0x0010, blocks: (B:33:0x0009, B:7:0x0016, B:11:0x001f, B:12:0x0025, B:14:0x0038, B:15:0x003a, B:19:0x003e, B:21:0x0042, B:23:0x0046, B:24:0x004d, B:25:0x004f, B:27:0x0051, B:28:0x0067), top: B:32:0x0009 }] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setVrMode(boolean r17, android.content.ComponentName r18, int r19, int r20, android.content.ComponentName r21) {
        /*
            r16 = this;
            r0 = r16
            java.lang.Object r8 = r0.mLock
            monitor-enter(r8)
            r1 = 0
            r2 = 1
            if (r17 != 0) goto L13
            boolean r3 = r0.mPersistentVrModeEnabled     // Catch: java.lang.Throwable -> L10
            if (r3 == 0) goto Le
            goto L13
        Le:
            r3 = r1
            goto L14
        L10:
            r0 = move-exception
            goto L69
        L13:
            r3 = r2
        L14:
            if (r17 != 0) goto L1c
            boolean r4 = r0.mPersistentVrModeEnabled     // Catch: java.lang.Throwable -> L10
            if (r4 == 0) goto L1c
            r4 = r2
            goto L1d
        L1c:
            r4 = r1
        L1d:
            if (r4 == 0) goto L23
            android.content.ComponentName r1 = r0.mDefaultVrService     // Catch: java.lang.Throwable -> L10
            r5 = r1
            goto L25
        L23:
            r5 = r18
        L25:
            com.android.server.vr.VrManagerService$VrState r1 = new com.android.server.vr.VrManagerService$VrState     // Catch: java.lang.Throwable -> L10
            r9 = r1
            r10 = r3
            r11 = r4
            r12 = r5
            r13 = r19
            r14 = r20
            r15 = r21
            r9.<init>(r10, r11, r12, r13, r14, r15)     // Catch: java.lang.Throwable -> L10
            boolean r6 = r0.mVrModeAllowed     // Catch: java.lang.Throwable -> L10
            if (r6 != 0) goto L3c
            r0.mPendingState = r1     // Catch: java.lang.Throwable -> L10
            monitor-exit(r8)     // Catch: java.lang.Throwable -> L10
            return
        L3c:
            if (r3 != 0) goto L51
            com.android.server.utils.ManagedApplicationService r6 = r0.mCurrentVrService     // Catch: java.lang.Throwable -> L10
            if (r6 == 0) goto L51
            com.android.server.vr.VrManagerService$VrState r3 = r0.mPendingState     // Catch: java.lang.Throwable -> L10
            if (r3 != 0) goto L4d
            com.android.server.vr.VrManagerService$2 r3 = r0.mHandler     // Catch: java.lang.Throwable -> L10
            r4 = 300(0x12c, double:1.48E-321)
            r3.sendEmptyMessageDelayed(r2, r4)     // Catch: java.lang.Throwable -> L10
        L4d:
            r0.mPendingState = r1     // Catch: java.lang.Throwable -> L10
            monitor-exit(r8)     // Catch: java.lang.Throwable -> L10
            return
        L51:
            com.android.server.vr.VrManagerService$2 r1 = r0.mHandler     // Catch: java.lang.Throwable -> L10
            r1.removeMessages(r2)     // Catch: java.lang.Throwable -> L10
            r1 = 0
            r0.mPendingState = r1     // Catch: java.lang.Throwable -> L10
            r1 = r16
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r19
            r6 = r20
            r7 = r21
            r1.updateCurrentVrServiceLocked(r2, r3, r4, r5, r6, r7)     // Catch: java.lang.Throwable -> L10
            monitor-exit(r8)     // Catch: java.lang.Throwable -> L10
            return
        L69:
            monitor-exit(r8)     // Catch: java.lang.Throwable -> L10
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.vr.VrManagerService.setVrMode(boolean, android.content.ComponentName, int, int, android.content.ComponentName):void");
    }

    public final void updateCompositorServiceLocked(int i, ComponentName componentName) {
        ManagedApplicationService managedApplicationService = this.mCurrentVrCompositorService;
        if (managedApplicationService != null && (!Objects.equals(managedApplicationService.mComponent, componentName) || managedApplicationService.mUserId != i)) {
            managedApplicationService.disconnect();
            Slog.i("VrManagerService", "Disconnecting compositor service: " + this.mCurrentVrCompositorService.mComponent);
            this.mCurrentVrCompositorService = null;
        }
        if (componentName == null || this.mCurrentVrCompositorService != null) {
            return;
        }
        Slog.i("VrManagerService", "Connecting compositor service: " + componentName);
        ManagedApplicationService managedApplicationService2 = new ManagedApplicationService(this.mContext, componentName, i, 0, null, null, this.mBootsToVr ? 1 : 3, this.mHandler, this.mEventCallback);
        this.mCurrentVrCompositorService = managedApplicationService2;
        managedApplicationService2.connect();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00e4 A[Catch: all -> 0x0035, TryCatch #0 {all -> 0x0035, blocks: (B:3:0x0012, B:7:0x0020, B:13:0x002a, B:15:0x002e, B:16:0x0039, B:19:0x0041, B:22:0x004a, B:26:0x0069, B:28:0x006d, B:31:0x00e4, B:33:0x00f0, B:36:0x00f5, B:38:0x0101, B:39:0x0106, B:41:0x010a, B:42:0x0112, B:45:0x011d, B:47:0x0122, B:50:0x012d, B:52:0x0129, B:57:0x00e8, B:60:0x009a, B:62:0x009e, B:64:0x00a6, B:67:0x00ab, B:69:0x00dd), top: B:2:0x0012 }] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0101 A[Catch: all -> 0x0035, TryCatch #0 {all -> 0x0035, blocks: (B:3:0x0012, B:7:0x0020, B:13:0x002a, B:15:0x002e, B:16:0x0039, B:19:0x0041, B:22:0x004a, B:26:0x0069, B:28:0x006d, B:31:0x00e4, B:33:0x00f0, B:36:0x00f5, B:38:0x0101, B:39:0x0106, B:41:0x010a, B:42:0x0112, B:45:0x011d, B:47:0x0122, B:50:0x012d, B:52:0x0129, B:57:0x00e8, B:60:0x009a, B:62:0x009e, B:64:0x00a6, B:67:0x00ab, B:69:0x00dd), top: B:2:0x0012 }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x010a A[Catch: all -> 0x0035, TryCatch #0 {all -> 0x0035, blocks: (B:3:0x0012, B:7:0x0020, B:13:0x002a, B:15:0x002e, B:16:0x0039, B:19:0x0041, B:22:0x004a, B:26:0x0069, B:28:0x006d, B:31:0x00e4, B:33:0x00f0, B:36:0x00f5, B:38:0x0101, B:39:0x0106, B:41:0x010a, B:42:0x0112, B:45:0x011d, B:47:0x0122, B:50:0x012d, B:52:0x0129, B:57:0x00e8, B:60:0x009a, B:62:0x009e, B:64:0x00a6, B:67:0x00ab, B:69:0x00dd), top: B:2:0x0012 }] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x011b A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0122 A[Catch: all -> 0x0035, TryCatch #0 {all -> 0x0035, blocks: (B:3:0x0012, B:7:0x0020, B:13:0x002a, B:15:0x002e, B:16:0x0039, B:19:0x0041, B:22:0x004a, B:26:0x0069, B:28:0x006d, B:31:0x00e4, B:33:0x00f0, B:36:0x00f5, B:38:0x0101, B:39:0x0106, B:41:0x010a, B:42:0x0112, B:45:0x011d, B:47:0x0122, B:50:0x012d, B:52:0x0129, B:57:0x00e8, B:60:0x009a, B:62:0x009e, B:64:0x00a6, B:67:0x00ab, B:69:0x00dd), top: B:2:0x0012 }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0105  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateCurrentVrServiceLocked(boolean r23, boolean r24, android.content.ComponentName r25, int r26, int r27, android.content.ComponentName r28) {
        /*
            Method dump skipped, instructions count: 338
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.vr.VrManagerService.updateCurrentVrServiceLocked(boolean, boolean, android.content.ComponentName, int, int, android.content.ComponentName):void");
    }

    public final void updateDependentAppOpsLocked(int i, int i2, String str, String str2) {
        if (Objects.equals(str, str2)) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AppOpsManager appOpsManager = (AppOpsManager) getContext().getSystemService(AppOpsManager.class);
            if (i2 != i) {
                appOpsManager.setUserRestrictionForUser(24, false, this.mOverlayToken, null, i2);
            }
            appOpsManager.setUserRestrictionForUser(24, this.mVrModeEnabled, this.mOverlayToken, str != null ? new PackageTagsList.Builder(1).add(str).build() : null, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void updateVrModeAllowedLocked() {
        ManagedApplicationService managedApplicationService;
        boolean z = this.mBootsToVr;
        boolean z2 = (this.mSystemSleepFlags == 7 || (z && this.mUseStandbyToExitVrMode)) && this.mUserUnlocked && !(this.mStandby && this.mUseStandbyToExitVrMode);
        if (this.mVrModeAllowed != z2) {
            this.mVrModeAllowed = z2;
            if (!z2) {
                setPersistentModeAndNotifyListenersLocked(false);
                boolean z3 = this.mVrModeEnabled;
                this.mPendingState = (!z3 || (managedApplicationService = this.mCurrentVrService) == null) ? null : new VrState(z3, this.mRunning2dInVr, managedApplicationService.mComponent, managedApplicationService.mUserId, this.mVrAppProcessId, this.mCurrentVrModeComponent);
                updateCurrentVrServiceLocked(false, false, null, 0, -1, null);
                return;
            }
            if (z) {
                setPersistentVrModeEnabled(true);
            }
            if (!this.mBootsToVr || this.mVrModeEnabled) {
                return;
            }
            setVrMode(true, this.mDefaultVrService, 0, -1, null);
        }
    }
}
