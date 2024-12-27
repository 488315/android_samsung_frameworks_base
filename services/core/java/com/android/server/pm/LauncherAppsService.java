package com.android.server.pm;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityOptions;
import android.app.AppGlobals;
import android.app.AppOpsManager;
import android.app.IApplicationThread;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyCache;
import android.app.admin.DevicePolicyManager;
import android.app.role.RoleManager;
import android.app.usage.UsageStatsManagerInternal;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.PermissionChecker;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ILauncherApps;
import android.content.pm.IOnAppsChangedListener;
import android.content.pm.IPackageInstallerCallback;
import android.content.pm.IPackageLoadingProgressCallback;
import android.content.pm.IPackageManager;
import android.content.pm.IShortcutChangeCallback;
import android.content.pm.IncrementalStatesInfo;
import android.content.pm.LauncherActivityInfoInternal;
import android.content.pm.LauncherApps;
import android.content.pm.LauncherUserInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.PackageManagerInternal$InstalledLoadingProgressCallback$LoadingProgressCallbackBinder;
import android.content.pm.ParceledListSlice;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutQueryWrapper;
import android.content.pm.ShortcutServiceInternal;
import android.content.pm.UserInfo;
import android.content.pm.UserProperties;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IInterface;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.ShellCommand;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.window.IDumpCallback;
import com.android.internal.content.PackageMonitor;
import com.android.internal.hidden_from_bootclasspath.android.os.Flags;
import com.android.internal.infra.AndroidFuture;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.SizedInputStream;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.ambientcontext.AmbientContextManagerPerUserService$$ExternalSyntheticOutline0;
import com.android.server.pm.LauncherAppsService;
import com.android.server.pm.LauncherAppsService.LauncherAppsImpl.PackageLoadingProgressCallback;
import com.android.server.pm.PackageInstallerService;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.pm.pkg.ArchiveState;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.usage.AppTimeLimitController;
import com.android.server.usage.UsageStatsService;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import java.io.DataInputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class LauncherAppsService extends SystemService {
    public static final Set WM_TRACE_FILE_PERMISSIONS = Set.of(PosixFilePermission.OWNER_WRITE, PosixFilePermission.GROUP_READ, PosixFilePermission.OTHERS_READ, PosixFilePermission.OWNER_READ);
    public final LauncherAppsImpl mLauncherAppsImpl;

    public final class BroadcastCookie {
        public final int callingPid;
        public final int callingUid;
        public final String packageName;
        public final UserHandle user;

        public BroadcastCookie(int i, int i2, UserHandle userHandle, String str) {
            this.user = userHandle;
            this.packageName = str;
            this.callingUid = i2;
            this.callingPid = i;
        }
    }

    public class LauncherAppsImpl extends ILauncherApps.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final ActivityManagerInternal mActivityManagerInternal;
        public final ActivityTaskManagerInternal mActivityTaskManagerInternal;
        public final AppOpsManager mAppOpsManager;
        public final Handler mCallbackHandler;
        public final Context mContext;
        public final DevicePolicyManager mDpm;
        public final RemoteCallbackList mDumpCallbacks;
        public final IPackageManager mIPM;
        public final LocalService mInternal;
        public boolean mIsWatchingPackageBroadcasts;
        public final ExecutorService mOnDumpExecutor;
        public PackageInstallerService mPackageInstallerService;
        public final PackageManagerInternal mPackageManagerInternal;
        public final MyPackageMonitor mPackageMonitor;
        public final ArrayMap mRegisteredListenersForDump;
        public final RoleManager mRoleManager;
        public final ShortcutChangeHandler mShortcutChangeHandler;
        public final ShortcutServiceInternal mShortcutServiceInternal;
        public final UserManager mUm;
        public final UsageStatsManagerInternal mUsageStatsManagerInternal;
        public final UserManagerInternal mUserManagerInternal;
        public final PackageCallbackList mListeners = new PackageCallbackList();
        public final PackageRemovedListener mPackageRemovedListener = new PackageRemovedListener();

        public final class LauncherAppsShellCommand extends ShellCommand {
            public LauncherAppsShellCommand() {
            }

            public final int onCommand(String str) {
                if (!"dump-view-hierarchies".equals(str)) {
                    return handleDefaultCommands(str);
                }
                try {
                    final ZipOutputStream zipOutputStream = new ZipOutputStream(getRawOutputStream());
                    try {
                        LauncherAppsImpl launcherAppsImpl = LauncherAppsImpl.this;
                        BiConsumer biConsumer = new BiConsumer() { // from class: com.android.server.pm.LauncherAppsService$LauncherAppsImpl$LauncherAppsShellCommand$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                LauncherAppsService.LauncherAppsImpl.LauncherAppsShellCommand launcherAppsShellCommand = LauncherAppsService.LauncherAppsImpl.LauncherAppsShellCommand.this;
                                ZipOutputStream zipOutputStream2 = zipOutputStream;
                                String str2 = (String) obj;
                                InputStream inputStream = (InputStream) obj2;
                                launcherAppsShellCommand.getClass();
                                try {
                                    zipOutputStream2.putNextEntry(new ZipEntry("FS" + str2));
                                    LauncherAppsService.LauncherAppsImpl launcherAppsImpl2 = LauncherAppsService.LauncherAppsImpl.this;
                                    int i = LauncherAppsService.LauncherAppsImpl.$r8$clinit;
                                    launcherAppsImpl2.getClass();
                                    new SizedInputStream(new DataInputStream(inputStream), r1.readInt()).transferTo(zipOutputStream2);
                                    zipOutputStream2.closeEntry();
                                } catch (IOException e) {
                                    PrintWriter errPrintWriter = launcherAppsShellCommand.getErrPrintWriter();
                                    StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Failed to output ", str2, " data to shell: ");
                                    m.append(e.getMessage());
                                    errPrintWriter.write(m.toString());
                                }
                            }
                        };
                        int i = LauncherAppsImpl.$r8$clinit;
                        launcherAppsImpl.forEachViewCaptureWindow(biConsumer);
                        zipOutputStream.close();
                        return 0;
                    } finally {
                    }
                } catch (IOException e) {
                    getErrPrintWriter().write("Failed to create or close zip output stream: " + e.getMessage());
                    return 0;
                }
            }

            public final void onHelp() {
                PrintWriter outPrintWriter = getOutPrintWriter();
                outPrintWriter.println("Usage: cmd launcherapps COMMAND [options ...]");
                outPrintWriter.println();
                outPrintWriter.println("cmd launcherapps dump-view-hierarchies");
                outPrintWriter.println("    Output captured view hierarchies. Files will be generated in ");
                outPrintWriter.println("    `/data/misc/wmtrace/`. After pulling the data to your device,");
                outPrintWriter.println("     you can upload / visualize it at `go/winscope`.");
                outPrintWriter.println();
            }
        }

        public final class LocalService {
            public LocalService() {
            }

            public final boolean startShortcut(int i, int i2, String str, String str2, String str3, Bundle bundle, int i3) {
                int userId = UserHandle.getUserId(i);
                int i4 = LauncherAppsImpl.$r8$clinit;
                return LauncherAppsImpl.this.startShortcutInner(i, i2, userId, str, str2, null, str3, null, bundle, i3);
            }
        }

        public final class MyPackageMonitor extends PackageMonitor implements ShortcutServiceInternal.ShortcutChangeListener {
            public MyPackageMonitor() {
            }

            public final void onPackageAdded(String str, int i) {
                UserHandle userHandle = new UserHandle(getChangingUserId());
                int beginBroadcast = LauncherAppsImpl.this.mListeners.beginBroadcast();
                Slog.d("LauncherAppsService", "Calling onPackageAdded: " + str + ", uid: " + i);
                int i2 = 0;
                while (i2 < beginBroadcast) {
                    try {
                        IOnAppsChangedListener broadcastItem = LauncherAppsImpl.this.mListeners.getBroadcastItem(i2);
                        BroadcastCookie broadcastCookie = (BroadcastCookie) LauncherAppsImpl.this.mListeners.getBroadcastCookie(i2);
                        boolean isEnabledProfileOf = LauncherAppsImpl.this.isEnabledProfileOf(userHandle, broadcastCookie, "onPackageAdded");
                        int i3 = broadcastCookie.callingPid;
                        int i4 = beginBroadcast;
                        String str2 = broadcastCookie.packageName;
                        if (!isEnabledProfileOf) {
                            Slog.d("LauncherAppsService", "Skip onPackageAdded(not enabled): " + str + ", listener: " + str2 + "(" + broadcastCookie.user + ", " + i3 + ")");
                        } else if (LauncherAppsImpl.this.isPackageVisibleToListener(userHandle, broadcastCookie, str)) {
                            try {
                                Slog.d("LauncherAppsService", "Calling onPackageAdded: " + str + ", listener: " + str2 + "(" + broadcastCookie.user + ", " + i3 + ")");
                                broadcastItem.onPackageAdded(userHandle, str);
                            } catch (RemoteException e) {
                                Slog.d("LauncherAppsService", "Callback failed ", e);
                            }
                        } else {
                            Slog.d("LauncherAppsService", "Skip onPackageAdded(not visible): " + str + ", listener: " + str2 + "(" + broadcastCookie.user + ", " + i3 + ")");
                        }
                        i2++;
                        beginBroadcast = i4;
                    } catch (Throwable th) {
                        LauncherAppsImpl.this.mListeners.finishBroadcast();
                        throw th;
                    }
                }
                LauncherAppsImpl.this.mListeners.finishBroadcast();
                super.onPackageAdded(str, i);
                LauncherAppsImpl launcherAppsImpl = LauncherAppsImpl.this;
                launcherAppsImpl.mPackageManagerInternal.registerInstalledLoadingProgressCallback(str, launcherAppsImpl.new PackageLoadingProgressCallback(str, userHandle), userHandle.getIdentifier());
            }

            public final void onPackageChanged(String str, UserHandle userHandle) {
                int beginBroadcast = LauncherAppsImpl.this.mListeners.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    try {
                        IOnAppsChangedListener broadcastItem = LauncherAppsImpl.this.mListeners.getBroadcastItem(i);
                        BroadcastCookie broadcastCookie = (BroadcastCookie) LauncherAppsImpl.this.mListeners.getBroadcastCookie(i);
                        if (LauncherAppsImpl.this.isEnabledProfileOf(userHandle, broadcastCookie, "onPackageModified") && LauncherAppsImpl.this.isPackageVisibleToListener(userHandle, broadcastCookie, str)) {
                            try {
                                broadcastItem.onPackageChanged(userHandle, str);
                            } catch (RemoteException e) {
                                Slog.d("LauncherAppsService", "Callback failed ", e);
                            }
                        }
                    } finally {
                        LauncherAppsImpl.this.mListeners.finishBroadcast();
                    }
                }
            }

            public final void onPackageModified(String str) {
                onPackageChanged(str, new UserHandle(getChangingUserId()));
                super.onPackageModified(str);
            }

            public final void onPackageStateChanged(String str, int i) {
                onPackageChanged(str, new UserHandle(getChangingUserId()));
                super.onPackageStateChanged(str, i);
            }

            public final void onPackagesAvailable(String[] strArr) {
                UserHandle userHandle = new UserHandle(getChangingUserId());
                int beginBroadcast = LauncherAppsImpl.this.mListeners.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    try {
                        IOnAppsChangedListener broadcastItem = LauncherAppsImpl.this.mListeners.getBroadcastItem(i);
                        BroadcastCookie broadcastCookie = (BroadcastCookie) LauncherAppsImpl.this.mListeners.getBroadcastCookie(i);
                        if (LauncherAppsImpl.this.isEnabledProfileOf(userHandle, broadcastCookie, "onPackagesAvailable")) {
                            String[] m754$$Nest$mgetFilteredPackageNames = LauncherAppsImpl.m754$$Nest$mgetFilteredPackageNames(LauncherAppsImpl.this, strArr, broadcastCookie, userHandle);
                            if (!ArrayUtils.isEmpty(m754$$Nest$mgetFilteredPackageNames)) {
                                try {
                                    broadcastItem.onPackagesAvailable(userHandle, m754$$Nest$mgetFilteredPackageNames, isReplacing());
                                } catch (RemoteException e) {
                                    Slog.d("LauncherAppsService", "Callback failed ", e);
                                }
                            }
                        }
                    } catch (Throwable th) {
                        LauncherAppsImpl.this.mListeners.finishBroadcast();
                        throw th;
                    }
                }
                LauncherAppsImpl.this.mListeners.finishBroadcast();
                super.onPackagesAvailable(strArr);
            }

            public final void onPackagesSuspended(String[] strArr) {
                UserHandle userHandle = new UserHandle(getChangingUserId());
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                for (String str : strArr) {
                    Bundle suspendedPackageLauncherExtras = LauncherAppsImpl.this.mPackageManagerInternal.getSuspendedPackageLauncherExtras(userHandle.getIdentifier(), str);
                    if (suspendedPackageLauncherExtras != null) {
                        arrayList.add(new Pair(str, suspendedPackageLauncherExtras));
                    } else {
                        arrayList2.add(str);
                    }
                }
                String[] strArr2 = (String[]) arrayList2.toArray(new String[arrayList2.size()]);
                int beginBroadcast = LauncherAppsImpl.this.mListeners.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    try {
                        IOnAppsChangedListener broadcastItem = LauncherAppsImpl.this.mListeners.getBroadcastItem(i);
                        BroadcastCookie broadcastCookie = (BroadcastCookie) LauncherAppsImpl.this.mListeners.getBroadcastCookie(i);
                        if (LauncherAppsImpl.this.isEnabledProfileOf(userHandle, broadcastCookie, "onPackagesSuspended")) {
                            String[] m754$$Nest$mgetFilteredPackageNames = LauncherAppsImpl.m754$$Nest$mgetFilteredPackageNames(LauncherAppsImpl.this, strArr2, broadcastCookie, userHandle);
                            try {
                                if (!ArrayUtils.isEmpty(m754$$Nest$mgetFilteredPackageNames)) {
                                    broadcastItem.onPackagesSuspended(userHandle, m754$$Nest$mgetFilteredPackageNames, (Bundle) null);
                                }
                                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                                    Pair pair = (Pair) arrayList.get(i2);
                                    if (LauncherAppsImpl.this.isPackageVisibleToListener(userHandle, broadcastCookie, (String) pair.first)) {
                                        broadcastItem.onPackagesSuspended(userHandle, new String[]{(String) pair.first}, (Bundle) pair.second);
                                    }
                                }
                            } catch (RemoteException e) {
                                Slog.d("LauncherAppsService", "Callback failed ", e);
                            }
                        }
                    } catch (Throwable th) {
                        LauncherAppsImpl.this.mListeners.finishBroadcast();
                        throw th;
                    }
                }
                LauncherAppsImpl.this.mListeners.finishBroadcast();
            }

            public final void onPackagesUnavailable(String[] strArr) {
                UserHandle userHandle = new UserHandle(getChangingUserId());
                int beginBroadcast = LauncherAppsImpl.this.mListeners.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    try {
                        IOnAppsChangedListener broadcastItem = LauncherAppsImpl.this.mListeners.getBroadcastItem(i);
                        BroadcastCookie broadcastCookie = (BroadcastCookie) LauncherAppsImpl.this.mListeners.getBroadcastCookie(i);
                        if (LauncherAppsImpl.this.isEnabledProfileOf(userHandle, broadcastCookie, "onPackagesUnavailable")) {
                            String[] m754$$Nest$mgetFilteredPackageNames = LauncherAppsImpl.m754$$Nest$mgetFilteredPackageNames(LauncherAppsImpl.this, strArr, broadcastCookie, userHandle);
                            if (!ArrayUtils.isEmpty(m754$$Nest$mgetFilteredPackageNames)) {
                                try {
                                    broadcastItem.onPackagesUnavailable(userHandle, m754$$Nest$mgetFilteredPackageNames, isReplacing());
                                } catch (RemoteException e) {
                                    Slog.d("LauncherAppsService", "Callback failed ", e);
                                }
                            }
                        }
                    } catch (Throwable th) {
                        LauncherAppsImpl.this.mListeners.finishBroadcast();
                        throw th;
                    }
                }
                LauncherAppsImpl.this.mListeners.finishBroadcast();
                super.onPackagesUnavailable(strArr);
            }

            public final void onPackagesUnsuspended(String[] strArr) {
                UserHandle userHandle = new UserHandle(getChangingUserId());
                int beginBroadcast = LauncherAppsImpl.this.mListeners.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    try {
                        IOnAppsChangedListener broadcastItem = LauncherAppsImpl.this.mListeners.getBroadcastItem(i);
                        BroadcastCookie broadcastCookie = (BroadcastCookie) LauncherAppsImpl.this.mListeners.getBroadcastCookie(i);
                        if (LauncherAppsImpl.this.isEnabledProfileOf(userHandle, broadcastCookie, "onPackagesUnsuspended")) {
                            String[] m754$$Nest$mgetFilteredPackageNames = LauncherAppsImpl.m754$$Nest$mgetFilteredPackageNames(LauncherAppsImpl.this, strArr, broadcastCookie, userHandle);
                            if (!ArrayUtils.isEmpty(m754$$Nest$mgetFilteredPackageNames)) {
                                try {
                                    broadcastItem.onPackagesUnsuspended(userHandle, m754$$Nest$mgetFilteredPackageNames);
                                } catch (RemoteException e) {
                                    Slog.d("LauncherAppsService", "Callback failed ", e);
                                }
                            }
                        }
                    } catch (Throwable th) {
                        LauncherAppsImpl.this.mListeners.finishBroadcast();
                        throw th;
                    }
                }
                LauncherAppsImpl.this.mListeners.finishBroadcast();
                super.onPackagesUnsuspended(strArr);
            }

            public final void onShortcutChanged(final String str, final int i) {
                LauncherAppsImpl.this.postToPackageMonitorHandler(new Runnable() { // from class: com.android.server.pm.LauncherAppsService$LauncherAppsImpl$MyPackageMonitor$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        String str2;
                        LauncherAppsService.LauncherAppsImpl launcherAppsImpl;
                        int i2;
                        int i3;
                        int i4;
                        UserHandle userHandle;
                        ParceledListSlice parceledListSlice;
                        LauncherAppsService.LauncherAppsImpl.MyPackageMonitor myPackageMonitor = LauncherAppsService.LauncherAppsImpl.MyPackageMonitor.this;
                        String str3 = str;
                        int i5 = i;
                        String str4 = "LauncherAppsService";
                        int beginBroadcast = LauncherAppsService.LauncherAppsImpl.this.mListeners.beginBroadcast();
                        try {
                            try {
                                UserHandle of = UserHandle.of(i5);
                                int i6 = 0;
                                while (i6 < beginBroadcast) {
                                    IOnAppsChangedListener broadcastItem = LauncherAppsService.LauncherAppsImpl.this.mListeners.getBroadcastItem(i6);
                                    LauncherAppsService.BroadcastCookie broadcastCookie = (LauncherAppsService.BroadcastCookie) LauncherAppsService.LauncherAppsImpl.this.mListeners.getBroadcastCookie(i6);
                                    if (LauncherAppsService.LauncherAppsImpl.this.isEnabledProfileOf(of, broadcastCookie, "onShortcutChanged") && LauncherAppsService.LauncherAppsImpl.this.isPackageVisibleToListener(of, broadcastCookie, str3)) {
                                        int identifier = broadcastCookie.user.getIdentifier();
                                        if (LauncherAppsService.LauncherAppsImpl.this.mShortcutServiceInternal.hasShortcutHostPermission(identifier, broadcastCookie.packageName, broadcastCookie.callingPid, broadcastCookie.callingUid)) {
                                            i2 = i6;
                                            UserHandle userHandle2 = of;
                                            i3 = beginBroadcast;
                                            String str5 = str4;
                                            i4 = i5;
                                            try {
                                                try {
                                                    parceledListSlice = new ParceledListSlice(LauncherAppsService.LauncherAppsImpl.this.mShortcutServiceInternal.getShortcuts(identifier, broadcastCookie.packageName, 0L, str3, (List) null, (List) null, (ComponentName) null, 1055, i5, broadcastCookie.callingPid, broadcastCookie.callingUid));
                                                    userHandle = userHandle2;
                                                } catch (RemoteException e) {
                                                    e = e;
                                                    userHandle = userHandle2;
                                                }
                                                try {
                                                    broadcastItem.onShortcutChanged(userHandle, str3, parceledListSlice);
                                                    str2 = str5;
                                                } catch (RemoteException e2) {
                                                    e = e2;
                                                    str2 = str5;
                                                    try {
                                                        Slog.d(str2, "Callback failed ", e);
                                                        i6 = i2 + 1;
                                                        of = userHandle;
                                                        str4 = str2;
                                                        beginBroadcast = i3;
                                                        i5 = i4;
                                                    } catch (RuntimeException e3) {
                                                        e = e3;
                                                        Log.w(str2, e.getMessage(), e);
                                                        launcherAppsImpl = LauncherAppsService.LauncherAppsImpl.this;
                                                        launcherAppsImpl.mListeners.finishBroadcast();
                                                    }
                                                }
                                                i6 = i2 + 1;
                                                of = userHandle;
                                                str4 = str2;
                                                beginBroadcast = i3;
                                                i5 = i4;
                                            } catch (RuntimeException e4) {
                                                e = e4;
                                                str2 = str5;
                                            }
                                        }
                                    }
                                    i2 = i6;
                                    userHandle = of;
                                    i3 = beginBroadcast;
                                    str2 = str4;
                                    i4 = i5;
                                    i6 = i2 + 1;
                                    of = userHandle;
                                    str4 = str2;
                                    beginBroadcast = i3;
                                    i5 = i4;
                                }
                                launcherAppsImpl = LauncherAppsService.LauncherAppsImpl.this;
                            } catch (Throwable th) {
                                LauncherAppsService.LauncherAppsImpl.this.mListeners.finishBroadcast();
                                throw th;
                            }
                        } catch (RuntimeException e5) {
                            e = e5;
                            str2 = str4;
                        }
                        launcherAppsImpl.mListeners.finishBroadcast();
                    }
                });
            }
        }

        public final class PackageCallbackList extends RemoteCallbackList {
            public PackageCallbackList() {
            }

            @Override // android.os.RemoteCallbackList
            public final void onCallbackDied(IInterface iInterface, Object obj) {
                synchronized (LauncherAppsImpl.this.mListeners) {
                    try {
                        if (LauncherAppsImpl.this.mRegisteredListenersForDump.containsKey(iInterface)) {
                            LauncherAppsImpl.this.mRegisteredListenersForDump.remove(iInterface);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                LauncherAppsImpl launcherAppsImpl = LauncherAppsImpl.this;
                synchronized (launcherAppsImpl.mListeners) {
                    if (launcherAppsImpl.mListeners.getRegisteredCallbackCount() == 0 && launcherAppsImpl.mIsWatchingPackageBroadcasts) {
                        launcherAppsImpl.mContext.unregisterReceiver(launcherAppsImpl.mPackageRemovedListener);
                        launcherAppsImpl.mPackageMonitor.unregister();
                        launcherAppsImpl.mIsWatchingPackageBroadcasts = false;
                    }
                }
            }
        }

        public final class PackageLoadingProgressCallback {
            public final PackageManagerInternal$InstalledLoadingProgressCallback$LoadingProgressCallbackBinder mBinder;
            public final Executor mExecutor;
            public final String mPackageName;
            public final UserHandle mUser;

            public PackageLoadingProgressCallback(String str, UserHandle userHandle) {
                Handler handler = LauncherAppsImpl.this.mCallbackHandler;
                this.mBinder = new IPackageLoadingProgressCallback.Stub() { // from class: android.content.pm.PackageManagerInternal$InstalledLoadingProgressCallback$LoadingProgressCallbackBinder
                    public final void onPackageLoadingProgressChanged(float f) {
                        LauncherAppsService.LauncherAppsImpl.PackageLoadingProgressCallback packageLoadingProgressCallback = LauncherAppsService.LauncherAppsImpl.PackageLoadingProgressCallback.this;
                        packageLoadingProgressCallback.mExecutor.execute(PooledLambda.obtainRunnable(new PackageManagerInternal$InstalledLoadingProgressCallback$LoadingProgressCallbackBinder$$ExternalSyntheticLambda0(), packageLoadingProgressCallback, Float.valueOf(f)).recycleOnUse());
                    }
                };
                this.mExecutor = new HandlerExecutor(handler == null ? new Handler(Looper.getMainLooper()) : handler);
                this.mPackageName = str;
                this.mUser = userHandle;
            }

            public final void onLoadingProgressChanged(float f) {
                String str = this.mPackageName;
                LauncherAppsImpl launcherAppsImpl = LauncherAppsImpl.this;
                int beginBroadcast = launcherAppsImpl.mListeners.beginBroadcast();
                for (int i = 0; i < beginBroadcast; i++) {
                    try {
                        IOnAppsChangedListener broadcastItem = launcherAppsImpl.mListeners.getBroadcastItem(i);
                        BroadcastCookie broadcastCookie = (BroadcastCookie) launcherAppsImpl.mListeners.getBroadcastCookie(i);
                        if (launcherAppsImpl.isEnabledProfileOf(this.mUser, broadcastCookie, "onLoadingProgressChanged") && launcherAppsImpl.isPackageVisibleToListener(this.mUser, broadcastCookie, str)) {
                            try {
                                broadcastItem.onPackageLoadingProgressChanged(this.mUser, str, f);
                            } catch (RemoteException e) {
                                Slog.d("LauncherAppsService", "Callback failed ", e);
                            }
                        }
                    } finally {
                        launcherAppsImpl.mListeners.finishBroadcast();
                    }
                }
            }
        }

        public final class PackageRemovedListener extends BroadcastReceiver {
            public PackageRemovedListener() {
            }

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                int i;
                PackageRemovedListener packageRemovedListener = this;
                int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -10000);
                if (intExtra == -10000) {
                    Slog.w("LauncherAppsService", "Intent broadcast does not contain user handle: " + intent);
                    return;
                }
                if ("android.intent.action.PACKAGE_REMOVED_INTERNAL".equals(intent.getAction())) {
                    Uri data = intent.getData();
                    String schemeSpecificPart = data != null ? data.getSchemeSpecificPart() : null;
                    int[] intArrayExtra = intent.getIntArrayExtra("android.intent.extra.VISIBILITY_ALLOW_LIST");
                    StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Calling onPackageRemoved: ", schemeSpecificPart, ", EXTRA_REPLACING: ");
                    int i2 = 0;
                    m.append(intent.getBooleanExtra("android.intent.extra.REPLACING", false));
                    Slog.d("LauncherAppsService", m.toString());
                    if (schemeSpecificPart == null || intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                        return;
                    }
                    UserHandle userHandle = new UserHandle(intExtra);
                    int beginBroadcast = LauncherAppsImpl.this.mListeners.beginBroadcast();
                    while (i2 < beginBroadcast) {
                        try {
                            IOnAppsChangedListener broadcastItem = LauncherAppsImpl.this.mListeners.getBroadcastItem(i2);
                            BroadcastCookie broadcastCookie = (BroadcastCookie) LauncherAppsImpl.this.mListeners.getBroadcastCookie(i2);
                            boolean isEnabledProfileOf = LauncherAppsImpl.this.isEnabledProfileOf(userHandle, broadcastCookie, "onPackageRemoved");
                            int i3 = broadcastCookie.callingUid;
                            int i4 = beginBroadcast;
                            int i5 = broadcastCookie.callingPid;
                            String str = broadcastCookie.packageName;
                            if (isEnabledProfileOf) {
                                int appId = UserHandle.getAppId(i3);
                                i = i2;
                                if (intArrayExtra != null && appId >= 10000 && Arrays.binarySearch(intArrayExtra, appId) <= -1) {
                                    Slog.d("LauncherAppsService", "Skip onPackageRemoved(not allowed): " + schemeSpecificPart + ", listener: " + str + "(" + broadcastCookie.user + ", " + i5 + ", " + i3 + ")");
                                }
                                try {
                                    Slog.d("LauncherAppsService", "Calling onPackageRemoved: " + schemeSpecificPart + ", listener: " + str + "(" + broadcastCookie.user + ", " + i5 + ")");
                                    broadcastItem.onPackageRemoved(userHandle, schemeSpecificPart);
                                } catch (RemoteException e) {
                                    Slog.d("LauncherAppsService", "Callback failed ", e);
                                }
                            } else {
                                Slog.d("LauncherAppsService", "Skip onPackageRemoved(not enabled): " + schemeSpecificPart + ", listener: " + str + "(" + broadcastCookie.user + ", " + i5 + ", " + i3 + ")");
                                i = i2;
                            }
                            i2 = i + 1;
                            packageRemovedListener = this;
                            beginBroadcast = i4;
                        } catch (Throwable th) {
                            LauncherAppsImpl.this.mListeners.finishBroadcast();
                            throw th;
                        }
                    }
                    LauncherAppsImpl.this.mListeners.finishBroadcast();
                }
            }
        }

        public final class ShortcutChangeHandler implements LauncherApps.ShortcutChangeCallback {
            public final RemoteCallbackList mCallbacks = new RemoteCallbackList();
            public final UserManagerInternal mUserManagerInternal;

            public ShortcutChangeHandler(UserManagerInternal userManagerInternal) {
                this.mUserManagerInternal = userManagerInternal;
            }

            public final void onShortcutEvent(String str, List list, UserHandle userHandle, boolean z) {
                int i;
                int beginBroadcast = this.mCallbacks.beginBroadcast();
                boolean z2 = 0;
                int i2 = 0;
                while (i2 < beginBroadcast) {
                    IShortcutChangeCallback broadcastItem = this.mCallbacks.getBroadcastItem(i2);
                    Pair pair = (Pair) this.mCallbacks.getBroadcastCookie(i2);
                    UserHandle userHandle2 = (UserHandle) pair.second;
                    ArrayList arrayList = null;
                    if (userHandle2 != null) {
                        if (!(userHandle == userHandle2 ? true : this.mUserManagerInternal.isProfileAccessible(userHandle2.getIdentifier(), userHandle.getIdentifier(), null, z2))) {
                            i = beginBroadcast;
                            i2++;
                            beginBroadcast = i;
                            z2 = 0;
                        }
                    }
                    ShortcutQueryWrapper shortcutQueryWrapper = (ShortcutQueryWrapper) pair.first;
                    long changedSince = shortcutQueryWrapper.getChangedSince();
                    String str2 = shortcutQueryWrapper.getPackage();
                    List shortcutIds = shortcutQueryWrapper.getShortcutIds();
                    List locusIds = shortcutQueryWrapper.getLocusIds();
                    ComponentName activity = shortcutQueryWrapper.getActivity();
                    int queryFlags = shortcutQueryWrapper.getQueryFlags();
                    if (str2 == null || str2.equals(str)) {
                        arrayList = new ArrayList();
                        int i3 = ((queryFlags & 2) != 0 ? true : z2 ? 2 : z2) | ((queryFlags & 1) != 0 ? 1 : z2) | ((queryFlags & 8) != 0 ? true : z2 ? 32 : z2) | ((queryFlags & 16) == 0 ? z2 : true ? 1610629120 : z2);
                        int i4 = z2;
                        while (i4 < list.size()) {
                            ShortcutInfo shortcutInfo = (ShortcutInfo) list.get(i4);
                            int i5 = beginBroadcast;
                            if ((activity == null || activity.equals(shortcutInfo.getActivity())) && ((changedSince == 0 || changedSince <= shortcutInfo.getLastChangedTimestamp()) && ((shortcutIds == null || shortcutIds.contains(shortcutInfo.getId())) && ((locusIds == null || locusIds.contains(shortcutInfo.getLocusId())) && (z || (shortcutInfo.getFlags() & i3) != 0))))) {
                                arrayList.add(shortcutInfo);
                            }
                            i4++;
                            beginBroadcast = i5;
                        }
                    }
                    i = beginBroadcast;
                    if (!CollectionUtils.isEmpty(arrayList)) {
                        if (z) {
                            try {
                                broadcastItem.onShortcutsRemoved(str, arrayList, userHandle);
                            } catch (RemoteException unused) {
                            }
                        } else {
                            broadcastItem.onShortcutsAddedOrUpdated(str, arrayList, userHandle);
                        }
                    }
                    i2++;
                    beginBroadcast = i;
                    z2 = 0;
                }
                this.mCallbacks.finishBroadcast();
            }

            public final void onShortcutsAddedOrUpdated(String str, List list, UserHandle userHandle) {
                onShortcutEvent(str, list, userHandle, false);
            }

            public final void onShortcutsRemoved(String str, List list, UserHandle userHandle) {
                onShortcutEvent(str, list, userHandle, true);
            }
        }

        /* renamed from: $r8$lambda$lNRUt_M-XrgBrBdVdi8x0Dx4VDQ, reason: not valid java name */
        public static void m753$r8$lambda$lNRUt_MXrgBrBdVdi8x0Dx4VDQ(LauncherAppsImpl launcherAppsImpl, String str, InputStream inputStream) {
            launcherAppsImpl.getClass();
            Path path = Paths.get(str, new String[0]);
            try {
                OutputStream newOutputStream = Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                try {
                    new SizedInputStream(new DataInputStream(inputStream), r1.readInt()).transferTo(newOutputStream);
                    Files.setPosixFilePermissions(path, LauncherAppsService.WM_TRACE_FILE_PERMISSIONS);
                    if (newOutputStream != null) {
                        newOutputStream.close();
                    }
                } finally {
                }
            } catch (IOException e) {
                Log.d("LauncherAppsService", "failed to write data to " + str + " in wmtrace dir", e);
            }
        }

        /* renamed from: -$$Nest$mgetFilteredPackageNames, reason: not valid java name */
        public static String[] m754$$Nest$mgetFilteredPackageNames(LauncherAppsImpl launcherAppsImpl, String[] strArr, BroadcastCookie broadcastCookie, UserHandle userHandle) {
            launcherAppsImpl.getClass();
            ArrayList arrayList = new ArrayList();
            for (String str : strArr) {
                if (launcherAppsImpl.isPackageVisibleToListener(userHandle, broadcastCookie, str)) {
                    arrayList.add(str);
                }
            }
            return (String[]) arrayList.toArray(new String[arrayList.size()]);
        }

        public LauncherAppsImpl(Context context) {
            MyPackageMonitor myPackageMonitor = new MyPackageMonitor();
            this.mPackageMonitor = myPackageMonitor;
            this.mIsWatchingPackageBroadcasts = false;
            this.mOnDumpExecutor = Executors.newSingleThreadExecutor();
            this.mDumpCallbacks = new RemoteCallbackList();
            this.mRegisteredListenersForDump = new ArrayMap();
            this.mContext = context;
            this.mIPM = AppGlobals.getPackageManager();
            this.mUm = (UserManager) context.getSystemService("user");
            this.mRoleManager = (RoleManager) context.getSystemService(RoleManager.class);
            UserManagerInternal userManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
            Objects.requireNonNull(userManagerInternal);
            this.mUserManagerInternal = userManagerInternal;
            UsageStatsManagerInternal usageStatsManagerInternal = (UsageStatsManagerInternal) LocalServices.getService(UsageStatsManagerInternal.class);
            Objects.requireNonNull(usageStatsManagerInternal);
            this.mUsageStatsManagerInternal = usageStatsManagerInternal;
            ActivityManagerInternal activityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
            Objects.requireNonNull(activityManagerInternal);
            this.mActivityManagerInternal = activityManagerInternal;
            ActivityTaskManagerInternal activityTaskManagerInternal = (ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class);
            Objects.requireNonNull(activityTaskManagerInternal);
            this.mActivityTaskManagerInternal = activityTaskManagerInternal;
            ShortcutServiceInternal shortcutServiceInternal = (ShortcutServiceInternal) LocalServices.getService(ShortcutServiceInternal.class);
            Objects.requireNonNull(shortcutServiceInternal);
            this.mShortcutServiceInternal = shortcutServiceInternal;
            PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
            Objects.requireNonNull(packageManagerInternal);
            this.mPackageManagerInternal = packageManagerInternal;
            this.mAppOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
            shortcutServiceInternal.addListener(myPackageMonitor);
            ShortcutChangeHandler shortcutChangeHandler = new ShortcutChangeHandler(userManagerInternal);
            this.mShortcutChangeHandler = shortcutChangeHandler;
            shortcutServiceInternal.addShortcutChangeCallback(shortcutChangeHandler);
            this.mCallbackHandler = BackgroundThread.getHandler();
            this.mDpm = (DevicePolicyManager) context.getSystemService("device_policy");
            this.mInternal = new LocalService();
        }

        public static Intent buildMarketPackageInfoIntent(String str, String str2, String str3) {
            return new Intent("android.intent.action.VIEW").setData(new Uri.Builder().scheme("market").authority("details").appendQueryParameter("id", str).build()).putExtra("android.intent.extra.REFERRER", new Uri.Builder().scheme("android-app").authority(str3).build()).setPackage(str2).setFlags(268435456);
        }

        public static Bundle getActivityOptionsForLauncher(Bundle bundle) {
            if (bundle == null) {
                return ActivityOptions.makeBasic().setPendingIntentBackgroundActivityStartMode(1).toBundle();
            }
            ActivityOptions fromBundle = ActivityOptions.fromBundle(bundle);
            return fromBundle.getPendingIntentBackgroundActivityStartMode() == 0 ? fromBundle.setPendingIntentBackgroundActivityStartMode(1).toBundle() : bundle;
        }

        public final void addOnAppsChangedListener(String str, IOnAppsChangedListener iOnAppsChangedListener) {
            verifyCallingPackage(str);
            synchronized (this.mListeners) {
                try {
                    if (this.mListeners.getRegisteredCallbackCount() == 0) {
                        startWatchingPackageBroadcasts();
                    }
                    this.mListeners.unregister(iOnAppsChangedListener);
                    this.mListeners.register(iOnAppsChangedListener, new BroadcastCookie(injectBinderCallingPid(), injectBinderCallingUid(), UserHandle.of(getCallingUserId()), str));
                    Date date = new Date(System.currentTimeMillis());
                    this.mRegisteredListenersForDump.put(iOnAppsChangedListener, "Package: " + str + " Pid: " + Binder.getCallingPid() + " Uid: " + Binder.getCallingUid() + " UserHandle: " + Binder.getCallingUserHandle().getIdentifier() + " Time: " + date.toString());
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final IntentSender buildAppMarketIntentSenderForUser(UserHandle userHandle) {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.APP_MARKET");
            intent.setFlags(268468224);
            PendingIntent activityAsUser = PendingIntent.getActivityAsUser(this.mContext, 0, intent, 201326592, null, userHandle);
            if (activityAsUser == null) {
                return null;
            }
            return activityAsUser.getIntentSender();
        }

        public final void cacheShortcuts(String str, String str2, List list, UserHandle userHandle, int i) {
            ensureStrictAccessShortcutsPermission(str);
            if (canAccessProfile(userHandle.getIdentifier(), "Cannot cache shortcuts")) {
                ShortcutServiceInternal shortcutServiceInternal = this.mShortcutServiceInternal;
                int callingUserId = getCallingUserId();
                int identifier = userHandle.getIdentifier();
                int i2 = i == 0 ? EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION : i == 1 ? 1073741824 : i == 2 ? 536870912 : 0;
                Preconditions.checkArgumentPositive(i2, "Invalid cache owner");
                shortcutServiceInternal.cacheShortcuts(callingUserId, str, str2, list, identifier, i2);
            }
        }

        public final boolean canAccessHiddenProfile(int i, int i2) {
            if (!Flags.allowPrivateProfile() || !android.multiuser.Flags.enableHidingProfiles() || !android.multiuser.Flags.enableLauncherAppsHiddenProfileChecks() || !android.multiuser.Flags.enablePermissionToAccessHiddenProfiles() || !android.multiuser.Flags.enablePrivateSpaceFeatures()) {
                return true;
            }
            long injectClearCallingIdentity = injectClearCallingIdentity();
            try {
                AndroidPackage androidPackage = this.mPackageManagerInternal.getPackage(i);
                if (androidPackage == null) {
                    return false;
                }
                if (this.mContext.checkPermission("android.permission.ACCESS_HIDDEN_PROFILES_FULL", i2, i) == 0) {
                    return true;
                }
                if (!DeviceConfig.getBoolean("multiuser", "allow_3p_launchers_access_via_launcher_apps_apis", true)) {
                    return false;
                }
                if (this.mRoleManager.getRoleHoldersAsUser("android.app.role.HOME", UserHandle.getUserHandleForUid(i)).contains(androidPackage.getPackageName())) {
                    return this.mContext.checkPermission("android.permission.ACCESS_HIDDEN_PROFILES", i2, i) == 0;
                }
                return false;
            } finally {
                injectRestoreCallingIdentity(injectClearCallingIdentity);
            }
        }

        public final boolean canAccessProfile(int i, String str) {
            int injectBinderCallingUid = injectBinderCallingUid();
            int userId = UserHandle.getUserId(injectBinderCallingUid());
            int injectBinderCallingPid = injectBinderCallingPid();
            if (i == userId) {
                return true;
            }
            if (this.mUserManagerInternal.getUserInfo(i) != null) {
                if (injectHasInteractAcrossUsersFullPermission(injectBinderCallingPid, injectBinderCallingUid)) {
                    return true;
                }
                long injectClearCallingIdentity = injectClearCallingIdentity();
                try {
                    UserInfo userInfo = this.mUm.getUserInfo(userId);
                    if (userInfo == null || !userInfo.isProfile()) {
                        injectRestoreCallingIdentity(injectClearCallingIdentity);
                        if (!isHiddenProfile(UserHandle.of(i)) || canAccessHiddenProfile(injectBinderCallingUid, injectBinderCallingPid)) {
                            return this.mUserManagerInternal.isProfileAccessible(userId, i, str, true);
                        }
                    } else {
                        Slog.w("LauncherAppsService", str + " for another profile " + i + " from " + userId + " not allowed");
                    }
                } finally {
                    injectRestoreCallingIdentity(injectClearCallingIdentity);
                }
            }
            return false;
        }

        public final void changePackageIcon(final String str, int i) {
            Preconditions.checkNotNull(str, "packageName cannot be null");
            final UserHandle userHandle = new UserHandle(i);
            this.mCallbackHandler.post(new Runnable() { // from class: com.android.server.pm.LauncherAppsService$LauncherAppsImpl$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    LauncherAppsService.LauncherAppsImpl launcherAppsImpl = LauncherAppsService.LauncherAppsImpl.this;
                    launcherAppsImpl.mPackageMonitor.onPackageChanged(str, userHandle);
                }
            });
        }

        public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            super.dump(fileDescriptor, printWriter, strArr);
            forEachViewCaptureWindow(new LauncherAppsService$LauncherAppsImpl$$ExternalSyntheticLambda1(this));
            if (DumpUtils.checkDumpAndUsageStatsPermission(this.mContext, "LauncherAppsService", printWriter)) {
                synchronized (this.mListeners) {
                    try {
                        printWriter.println("Registered Listeners:");
                        Iterator it = this.mRegisteredListenersForDump.values().iterator();
                        while (it.hasNext()) {
                            printWriter.println("  " + ((String) it.next()));
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }

        public final void ensureShortcutPermission(String str) {
            int injectBinderCallingUid = injectBinderCallingUid();
            int injectBinderCallingPid = injectBinderCallingPid();
            verifyCallingPackage(str, injectBinderCallingUid);
            if (!this.mShortcutServiceInternal.hasShortcutHostPermission(UserHandle.getUserId(injectBinderCallingUid), str, injectBinderCallingPid, injectBinderCallingUid)) {
                throw new SecurityException("Caller can't access shortcut information");
            }
        }

        public final void ensureStrictAccessShortcutsPermission(String str) {
            verifyCallingPackage(str);
            if (!injectHasAccessShortcutsPermission(injectBinderCallingPid(), injectBinderCallingUid())) {
                throw new SecurityException("Caller can't access shortcut information");
            }
        }

        public final void forEachViewCaptureWindow(final BiConsumer biConsumer) {
            try {
                this.mOnDumpExecutor.submit(new Runnable() { // from class: com.android.server.pm.LauncherAppsService$LauncherAppsImpl$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        LauncherAppsService.LauncherAppsImpl launcherAppsImpl = LauncherAppsService.LauncherAppsImpl.this;
                        BiConsumer biConsumer2 = biConsumer;
                        int i = LauncherAppsService.LauncherAppsImpl.$r8$clinit;
                        launcherAppsImpl.getClass();
                        try {
                            for (int beginBroadcast = launcherAppsImpl.mDumpCallbacks.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
                                String str = "/data/misc/wmtrace/" + ((String) launcherAppsImpl.mDumpCallbacks.getBroadcastCookie(beginBroadcast)) + "_" + beginBroadcast + ".vc";
                                try {
                                    ParcelFileDescriptor[] createPipe = ParcelFileDescriptor.createPipe();
                                    launcherAppsImpl.mDumpCallbacks.getBroadcastItem(beginBroadcast).onDump(createPipe[1]);
                                    ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new ParcelFileDescriptor.AutoCloseInputStream(createPipe[0]);
                                    biConsumer2.accept(str, autoCloseInputStream);
                                    autoCloseInputStream.close();
                                } catch (Exception e) {
                                    Log.d("LauncherAppsService", "failed to pipe view capture data", e);
                                }
                            }
                        } finally {
                            launcherAppsImpl.mDumpCallbacks.finishBroadcast();
                        }
                    }
                }).get();
            } catch (InterruptedException | ExecutionException e) {
                Log.e("LauncherAppsService", "background work was interrupted", e);
            }
        }

        public final List generateLauncherActivitiesForArchivedApp(String str, UserHandle userHandle) {
            ?? of;
            if (!canAccessProfile(userHandle.getIdentifier(), "Cannot retrieve activities")) {
                return List.of();
            }
            if (str == null) {
                List installedApplications = ((PackageManagerService.PackageManagerInternalImpl) this.mPackageManagerInternal).mService.snapshotComputer().getInstalledApplications(userHandle.getIdentifier(), injectBinderCallingUid(), true, 4294967296L);
                of = new ArrayList();
                for (int i = 0; i < installedApplications.size(); i++) {
                    ApplicationInfo applicationInfo = (ApplicationInfo) installedApplications.get(i);
                    if (applicationInfo != null && applicationInfo.isArchived) {
                        of.add(applicationInfo);
                    }
                }
            } else {
                ApplicationInfo applicationInfo2 = (ApplicationInfo) Binder.withCleanCallingIdentity(new LauncherAppsService$LauncherAppsImpl$$ExternalSyntheticLambda2(this, str, injectBinderCallingUid(), userHandle));
                of = (applicationInfo2 == null || !applicationInfo2.isArchived) ? Collections.EMPTY_LIST : List.of(applicationInfo2);
            }
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < of.size(); i2++) {
                ApplicationInfo applicationInfo3 = (ApplicationInfo) of.get(i2);
                PackageStateInternal packageStateInternal = this.mPackageManagerInternal.getPackageStateInternal(applicationInfo3.packageName);
                if (packageStateInternal != null) {
                    ArchiveState archiveState = packageStateInternal.getUserStateOrDefault(userHandle.getIdentifier()).getArchiveState();
                    if (archiveState == null) {
                        Slog.w("LauncherAppsService", TextUtils.formatSimple("Expected package: %s to be archived but missing ArchiveState in PackageState.", new Object[]{applicationInfo3.packageName}));
                    } else {
                        List list = archiveState.mActivityInfos;
                        for (int i3 = 0; i3 < list.size(); i3++) {
                            ArchiveState.ArchiveActivityInfo archiveActivityInfo = (ArchiveState.ArchiveActivityInfo) list.get(i3);
                            ActivityInfo activityInfo = new ActivityInfo();
                            activityInfo.isArchived = applicationInfo3.isArchived;
                            activityInfo.applicationInfo = applicationInfo3;
                            activityInfo.packageName = archiveActivityInfo.mOriginalComponentName.getPackageName();
                            activityInfo.name = archiveActivityInfo.mOriginalComponentName.getClassName();
                            activityInfo.nonLocalizedLabel = archiveActivityInfo.mTitle;
                            arrayList.add(new LauncherActivityInfoInternal(activityInfo, new IncrementalStatesInfo(false, FullScreenMagnificationGestureHandler.MAX_SCALE, 0L), userHandle));
                        }
                    }
                }
            }
            return arrayList;
        }

        public final PendingIntent getActivityLaunchIntent(String str, ComponentName componentName, UserHandle userHandle) {
            if (this.mContext.checkPermission("android.permission.START_TASKS_FROM_RECENTS", injectBinderCallingPid(), injectBinderCallingUid()) != 0) {
                throw new SecurityException("Permission START_TASKS_FROM_RECENTS required");
            }
            if (!canAccessProfile(userHandle.getIdentifier(), "Cannot start activity")) {
                throw new ActivityNotFoundException("Activity could not be found");
            }
            Intent mainActivityLaunchIntent = getMainActivityLaunchIntent(componentName, userHandle, false);
            if (mainActivityLaunchIntent == null) {
                throw new SecurityException(AmbientContextManagerPerUserService$$ExternalSyntheticOutline0.m(componentName, "Attempt to launch activity without  category Intent.CATEGORY_LAUNCHER "));
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return PendingIntent.getActivityAsUser(this.mContext, 0, mainActivityLaunchIntent, 33554432, null, userHandle);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final Map getActivityOverrides(String str, int i) {
            UserHandle userHandle;
            ensureShortcutPermission(str);
            int callingUid = Binder.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                ArrayMap arrayMap = new ArrayMap();
                Iterator it = this.mUm.getProfiles(i).iterator();
                while (true) {
                    if (!it.hasNext()) {
                        userHandle = null;
                        break;
                    }
                    UserInfo userInfo = (UserInfo) it.next();
                    if (userInfo.isManagedProfile()) {
                        userHandle = userInfo.getUserHandle();
                        break;
                    }
                }
                if (userHandle == null) {
                    return arrayMap;
                }
                for (Map.Entry entry : DevicePolicyCache.getInstance().getLauncherShortcutOverrides().entrySet()) {
                    ArrayList arrayList = (ArrayList) queryIntentLauncherActivities(new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setPackage((String) entry.getValue()), callingUid, userHandle);
                    if (!arrayList.isEmpty()) {
                        arrayMap.put((String) entry.getKey(), (LauncherActivityInfoInternal) arrayList.get(0));
                    }
                }
                return arrayMap;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final ParceledListSlice getAllSessions(String str) {
            verifyCallingPackage(str);
            ArrayList arrayList = new ArrayList();
            final int callingUid = Binder.getCallingUid();
            int[] profileIdsExcludingHidden = !canAccessHiddenProfile(callingUid, Binder.getCallingPid()) ? this.mUm.getProfileIdsExcludingHidden(getCallingUserId(), true) : this.mUm.getEnabledProfileIds(getCallingUserId());
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                for (int i : profileIdsExcludingHidden) {
                    arrayList.addAll(getPackageInstallerService().getAllSessions(i).getList());
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
                arrayList.removeIf(new Predicate() { // from class: com.android.server.pm.LauncherAppsService$LauncherAppsImpl$$ExternalSyntheticLambda5
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        LauncherAppsService.LauncherAppsImpl launcherAppsImpl = LauncherAppsService.LauncherAppsImpl.this;
                        int i2 = callingUid;
                        PackageInstaller.SessionInfo sessionInfo = (PackageInstaller.SessionInfo) obj;
                        int i3 = LauncherAppsService.LauncherAppsImpl.$r8$clinit;
                        launcherAppsImpl.getClass();
                        return (sessionInfo == null || i2 == sessionInfo.getInstallerUid() || launcherAppsImpl.mPackageManagerInternal.canQueryPackage(i2, sessionInfo.getAppPackageName())) ? false : true;
                    }
                });
                return new ParceledListSlice(arrayList);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final android.content.IntentSender getAppMarketActivityIntent(java.lang.String r13, java.lang.String r14, android.os.UserHandle r15) {
            /*
                r12 = this;
                java.lang.String r0 = "Couldn't find installer for "
                int r1 = r15.getIdentifier()
                java.lang.String r2 = "Can't access AppMarketActivity for another user"
                boolean r1 = r12.canAccessProfile(r1, r2)
                r2 = 0
                if (r1 != 0) goto L10
                return r2
            L10:
                int r1 = r12.getCallingUserId()
                long r3 = android.os.Binder.clearCallingIdentity()
                if (r14 != 0) goto L25
                android.content.IntentSender r12 = r12.buildAppMarketIntentSenderForUser(r15)     // Catch: java.lang.Throwable -> L22
                android.os.Binder.restoreCallingIdentity(r3)
                return r12
            L22:
                r12 = move-exception
                goto L9d
            L25:
                android.content.pm.IPackageManager r5 = r12.mIPM     // Catch: java.lang.Throwable -> L22 android.os.RemoteException -> L34
                android.content.pm.InstallSourceInfo r1 = r5.getInstallSourceInfo(r14, r1)     // Catch: java.lang.Throwable -> L22 android.os.RemoteException -> L34
                if (r1 != 0) goto L2f
            L2d:
                r0 = r2
                goto L3f
            L2f:
                java.lang.String r0 = r1.getInstallingPackageName()     // Catch: java.lang.Throwable -> L22 android.os.RemoteException -> L34
                goto L3f
            L34:
                r1 = move-exception
                java.lang.String r0 = r0.concat(r14)     // Catch: java.lang.Throwable -> L22
                java.lang.String r5 = "LauncherAppsService"
                android.util.Slog.e(r5, r0, r1)     // Catch: java.lang.Throwable -> L22
                goto L2d
            L3f:
                if (r0 == 0) goto L95
                android.content.pm.PackageManagerInternal r1 = r12.mPackageManagerInternal     // Catch: java.lang.Throwable -> L22
                int r5 = r15.getIdentifier()     // Catch: java.lang.Throwable -> L22
                r6 = 0
                int r1 = r1.getPackageUid(r0, r6, r5)     // Catch: java.lang.Throwable -> L22
                if (r1 >= 0) goto L50
                goto L95
            L50:
                android.content.Intent r13 = buildMarketPackageInfoIntent(r14, r0, r13)     // Catch: java.lang.Throwable -> L22
                android.content.pm.PackageManagerInternal r5 = r12.mPackageManagerInternal     // Catch: java.lang.Throwable -> L22
                android.content.Context r14 = r12.mContext     // Catch: java.lang.Throwable -> L22
                android.content.ContentResolver r14 = r14.getContentResolver()     // Catch: java.lang.Throwable -> L22
                java.lang.String r7 = r13.resolveTypeIfNeeded(r14)     // Catch: java.lang.Throwable -> L22
                int r10 = android.os.Process.myUid()     // Catch: java.lang.Throwable -> L22
                int r11 = r15.getIdentifier()     // Catch: java.lang.Throwable -> L22
                r8 = 131072(0x20000, double:6.47582E-319)
                r6 = r13
                java.util.List r14 = r5.queryIntentActivities(r6, r7, r8, r10, r11)     // Catch: java.lang.Throwable -> L22
                boolean r14 = r14.isEmpty()     // Catch: java.lang.Throwable -> L22
                if (r14 == 0) goto L7e
                android.content.IntentSender r12 = r12.buildAppMarketIntentSenderForUser(r15)     // Catch: java.lang.Throwable -> L22
                android.os.Binder.restoreCallingIdentity(r3)
                return r12
            L7e:
                android.content.Context r5 = r12.mContext     // Catch: java.lang.Throwable -> L22
                r6 = 0
                r8 = 201326592(0xc000000, float:9.8607613E-32)
                r9 = 0
                r7 = r13
                r10 = r15
                android.app.PendingIntent r12 = android.app.PendingIntent.getActivityAsUser(r5, r6, r7, r8, r9, r10)     // Catch: java.lang.Throwable -> L22
                if (r12 != 0) goto L8d
                goto L91
            L8d:
                android.content.IntentSender r2 = r12.getIntentSender()     // Catch: java.lang.Throwable -> L22
            L91:
                android.os.Binder.restoreCallingIdentity(r3)
                return r2
            L95:
                android.content.IntentSender r12 = r12.buildAppMarketIntentSenderForUser(r15)     // Catch: java.lang.Throwable -> L22
                android.os.Binder.restoreCallingIdentity(r3)
                return r12
            L9d:
                android.os.Binder.restoreCallingIdentity(r3)
                throw r12
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.LauncherAppsService.LauncherAppsImpl.getAppMarketActivityIntent(java.lang.String, java.lang.String, android.os.UserHandle):android.content.IntentSender");
        }

        public final LauncherApps.AppUsageLimit getAppUsageLimit(String str, String str2, UserHandle userHandle) {
            UsageStatsManagerInternal.AppUsageLimitData appUsageLimitData;
            verifyCallingPackage(str);
            if (!canAccessProfile(userHandle.getIdentifier(), "Cannot access usage limit")) {
                return null;
            }
            if (!this.mActivityTaskManagerInternal.isCallerRecents(Binder.getCallingUid())) {
                throw new SecurityException("Caller is not the recents app");
            }
            AppTimeLimitController appTimeLimitController = UsageStatsService.this.mAppTimeLimit;
            synchronized (appTimeLimitController.mLock) {
                try {
                    ArrayList arrayList = (ArrayList) appTimeLimitController.getOrCreateUserDataLocked(userHandle.getIdentifier()).observedMap.get(str2);
                    if (arrayList != null && !arrayList.isEmpty()) {
                        ArraySet arraySet = new ArraySet();
                        for (int i = 0; i < arrayList.size(); i++) {
                            if (arrayList.get(i) instanceof AppTimeLimitController.AppUsageLimitGroup) {
                                AppTimeLimitController.AppUsageLimitGroup appUsageLimitGroup = (AppTimeLimitController.AppUsageLimitGroup) arrayList.get(i);
                                int i2 = 0;
                                while (true) {
                                    String[] strArr = appUsageLimitGroup.mObserved;
                                    if (i2 >= strArr.length) {
                                        break;
                                    }
                                    if (strArr[i2].equals(str2)) {
                                        arraySet.add(appUsageLimitGroup);
                                        break;
                                    }
                                    i2++;
                                }
                            }
                        }
                        if (arraySet.isEmpty()) {
                            appUsageLimitData = null;
                        } else {
                            AppTimeLimitController.AppUsageLimitGroup appUsageLimitGroup2 = (AppTimeLimitController.AppUsageLimitGroup) arraySet.valueAt(0);
                            for (int i3 = 1; i3 < arraySet.size(); i3++) {
                                AppTimeLimitController.AppUsageLimitGroup appUsageLimitGroup3 = (AppTimeLimitController.AppUsageLimitGroup) arraySet.valueAt(i3);
                                if (appUsageLimitGroup3.getUsageRemaining() < appUsageLimitGroup2.getUsageRemaining()) {
                                    appUsageLimitGroup2 = appUsageLimitGroup3;
                                }
                            }
                            appUsageLimitData = new UsageStatsManagerInternal.AppUsageLimitData(appUsageLimitGroup2.mTimeLimitMs, appUsageLimitGroup2.getUsageRemaining());
                        }
                    }
                    appUsageLimitData = null;
                } finally {
                }
            }
            if (appUsageLimitData == null) {
                return null;
            }
            return new LauncherApps.AppUsageLimit(appUsageLimitData.mTotalUsageLimit, appUsageLimitData.mUsageRemaining);
        }

        public final ApplicationInfo getApplicationInfo(String str, String str2, int i, UserHandle userHandle) {
            if (!canAccessProfile(userHandle.getIdentifier(), "Cannot check package")) {
                return null;
            }
            int injectBinderCallingUid = injectBinderCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                return this.mPackageManagerInternal.getApplicationInfo(injectBinderCallingUid, userHandle.getIdentifier(), i, str2);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final int getCallingUserId() {
            return UserHandle.getUserId(injectBinderCallingUid());
        }

        public final LauncherActivityInfoInternal getHiddenAppActivityInfo(int i, UserHandle userHandle, String str) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(str, PackageManager.APP_DETAILS_ACTIVITY_CLASS_NAME));
            ArrayList arrayList = (ArrayList) queryIntentLauncherActivities(intent, i, userHandle);
            if (arrayList.size() > 0) {
                return (LauncherActivityInfoInternal) arrayList.get(0);
            }
            return null;
        }

        public final ParceledListSlice getLauncherActivities(String str, String str2, UserHandle userHandle) {
            LauncherActivityInfoInternal hiddenAppActivityInfo;
            LauncherActivityInfoInternal hiddenAppActivityInfo2;
            ParceledListSlice queryActivitiesForUser = queryActivitiesForUser(new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setPackage(str2), userHandle);
            PorterDuffColorFilter porterDuffColorFilter = PackageArchiver.OPACITY_LAYER_FILTER;
            if (com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags.archiving()) {
                List generateLauncherActivitiesForArchivedApp = generateLauncherActivitiesForArchivedApp(str2, userHandle);
                if (!generateLauncherActivitiesForArchivedApp.isEmpty()) {
                    if (queryActivitiesForUser == null) {
                        queryActivitiesForUser = new ParceledListSlice(generateLauncherActivitiesForArchivedApp);
                    } else {
                        List list = queryActivitiesForUser.getList();
                        list.addAll(generateLauncherActivitiesForArchivedApp);
                        queryActivitiesForUser = new ParceledListSlice(list);
                    }
                }
            }
            if (Settings.Global.getInt(this.mContext.getContentResolver(), "show_hidden_icon_apps_enabled", 1) == 0) {
                return queryActivitiesForUser;
            }
            if (queryActivitiesForUser == null) {
                return null;
            }
            int injectBinderCallingUid = injectBinderCallingUid();
            long injectClearCallingIdentity = injectClearCallingIdentity();
            try {
                if (!this.mUm.getUserInfo(userHandle.getIdentifier()).isManagedProfile() && !this.mUm.getUserInfo(userHandle.getIdentifier()).isCloneProfile()) {
                    if (this.mDpm.getDeviceOwnerComponentOnAnyUser() != null) {
                        injectRestoreCallingIdentity(injectClearCallingIdentity);
                        return queryActivitiesForUser;
                    }
                    ArrayList arrayList = new ArrayList(queryActivitiesForUser.getList());
                    if (str2 != null) {
                        if (arrayList.size() > 0) {
                            injectRestoreCallingIdentity(injectClearCallingIdentity);
                            return queryActivitiesForUser;
                        }
                        if (shouldShowSyntheticActivity(userHandle, this.mPackageManagerInternal.getApplicationInfo(injectBinderCallingUid, userHandle.getIdentifier(), 0L, str2)) && (hiddenAppActivityInfo2 = getHiddenAppActivityInfo(injectBinderCallingUid, userHandle, str2)) != null) {
                            arrayList.add(hiddenAppActivityInfo2);
                        }
                        ParceledListSlice parceledListSlice = new ParceledListSlice(arrayList);
                        injectRestoreCallingIdentity(injectClearCallingIdentity);
                        return parceledListSlice;
                    }
                    HashSet hashSet = new HashSet();
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        hashSet.add(((LauncherActivityInfoInternal) it.next()).getActivityInfo().packageName);
                    }
                    for (ApplicationInfo applicationInfo : this.mPackageManagerInternal.getInstalledApplications(userHandle.getIdentifier(), injectBinderCallingUid, 0L)) {
                        if (!hashSet.contains(applicationInfo.packageName) && shouldShowSyntheticActivity(userHandle, applicationInfo) && (hiddenAppActivityInfo = getHiddenAppActivityInfo(injectBinderCallingUid, userHandle, applicationInfo.packageName)) != null) {
                            arrayList.add(hiddenAppActivityInfo);
                        }
                    }
                    ParceledListSlice parceledListSlice2 = new ParceledListSlice(arrayList);
                    injectRestoreCallingIdentity(injectClearCallingIdentity);
                    return parceledListSlice2;
                }
                return queryActivitiesForUser;
            } finally {
                injectRestoreCallingIdentity(injectClearCallingIdentity);
            }
        }

        public final LauncherUserInfo getLauncherUserInfo(UserHandle userHandle) {
            if (!canAccessProfile(userHandle.getIdentifier(), "Can't access LauncherUserInfo for another user")) {
                return null;
            }
            long injectClearCallingIdentity = injectClearCallingIdentity();
            try {
                return this.mUserManagerInternal.getLauncherUserInfo(userHandle.getIdentifier());
            } finally {
                injectRestoreCallingIdentity(injectClearCallingIdentity);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x0089, code lost:
        
            if (r3 != false) goto L26;
         */
        /* JADX WARN: Code restructure failed: missing block: B:15:0x008b, code lost:
        
            if (r15 == false) goto L26;
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x008d, code lost:
        
            r15 = com.android.server.pm.PackageArchiver.OPACITY_LAYER_FILTER;
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x0093, code lost:
        
            if (com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags.archiving() == false) goto L26;
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x0099, code lost:
        
            if (getMatchingArchivedAppActivityInfo(r13, r14) == null) goto L26;
         */
        /* JADX WARN: Code restructure failed: missing block: B:20:0x009b, code lost:
        
            r8.setPackage(null);
            r8.setComponent(r13);
         */
        /* JADX WARN: Code restructure failed: missing block: B:21:0x00a3, code lost:
        
            if (r0 != false) goto L30;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x00a5, code lost:
        
            android.os.Binder.restoreCallingIdentity(r9);
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x00a8, code lost:
        
            return null;
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x00a9, code lost:
        
            android.os.Binder.restoreCallingIdentity(r9);
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x00ac, code lost:
        
            return r8;
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x00a2, code lost:
        
            r0 = r3;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final android.content.Intent getMainActivityLaunchIntent(android.content.ComponentName r13, android.os.UserHandle r14, boolean r15) {
            /*
                r12 = this;
                r0 = 1
                android.content.Intent r8 = new android.content.Intent
                java.lang.String r1 = "android.intent.action.MAIN"
                r8.<init>(r1)
                java.lang.String r1 = "android.intent.category.LAUNCHER"
                r8.addCategory(r1)
                r1 = 270532608(0x10200000, float:3.1554436E-29)
                r8.addFlags(r1)
                java.lang.String r1 = r13.getPackageName()
                r8.setPackage(r1)
                int r6 = r12.injectBinderCallingUid()
                long r9 = android.os.Binder.clearCallingIdentity()
                android.content.pm.PackageManagerInternal r1 = r12.mPackageManagerInternal     // Catch: java.lang.Throwable -> L6e
                android.content.Context r2 = r12.mContext     // Catch: java.lang.Throwable -> L6e
                android.content.ContentResolver r2 = r2.getContentResolver()     // Catch: java.lang.Throwable -> L6e
                java.lang.String r3 = r8.resolveTypeIfNeeded(r2)     // Catch: java.lang.Throwable -> L6e
                int r7 = r14.getIdentifier()     // Catch: java.lang.Throwable -> L6e
                r4 = 786432(0xc0000, double:3.88549E-318)
                r2 = r8
                java.util.List r1 = r1.queryIntentActivities(r2, r3, r4, r6, r7)     // Catch: java.lang.Throwable -> L6e
                int r2 = r1.size()     // Catch: java.lang.Throwable -> L6e
                r3 = 0
                r4 = r3
            L3f:
                r5 = 0
                if (r4 >= r2) goto L89
                java.lang.Object r6 = r1.get(r4)     // Catch: java.lang.Throwable -> L6e
                android.content.pm.ResolveInfo r6 = (android.content.pm.ResolveInfo) r6     // Catch: java.lang.Throwable -> L6e
                android.content.pm.ActivityInfo r6 = r6.activityInfo     // Catch: java.lang.Throwable -> L6e
                java.lang.String r7 = r6.packageName     // Catch: java.lang.Throwable -> L6e
                java.lang.String r11 = r13.getPackageName()     // Catch: java.lang.Throwable -> L6e
                boolean r7 = r7.equals(r11)     // Catch: java.lang.Throwable -> L6e
                if (r7 == 0) goto L87
                java.lang.String r7 = r6.name     // Catch: java.lang.Throwable -> L6e
                java.lang.String r11 = r13.getClassName()     // Catch: java.lang.Throwable -> L6e
                boolean r7 = r7.equals(r11)     // Catch: java.lang.Throwable -> L6e
                if (r7 == 0) goto L87
                boolean r1 = r6.exported     // Catch: java.lang.Throwable -> L6e
                if (r1 == 0) goto L70
                r8.setPackage(r5)     // Catch: java.lang.Throwable -> L6e
                r8.setComponent(r13)     // Catch: java.lang.Throwable -> L6e
                r3 = r0
                goto L89
            L6e:
                r12 = move-exception
                goto Lad
            L70:
                java.lang.SecurityException r12 = new java.lang.SecurityException     // Catch: java.lang.Throwable -> L6e
                java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L6e
                r14.<init>()     // Catch: java.lang.Throwable -> L6e
                java.lang.String r15 = "Cannot launch non-exported components "
                r14.append(r15)     // Catch: java.lang.Throwable -> L6e
                r14.append(r13)     // Catch: java.lang.Throwable -> L6e
                java.lang.String r13 = r14.toString()     // Catch: java.lang.Throwable -> L6e
                r12.<init>(r13)     // Catch: java.lang.Throwable -> L6e
                throw r12     // Catch: java.lang.Throwable -> L6e
            L87:
                int r4 = r4 + r0
                goto L3f
            L89:
                if (r3 != 0) goto La2
                if (r15 == 0) goto La2
                android.graphics.PorterDuffColorFilter r15 = com.android.server.pm.PackageArchiver.OPACITY_LAYER_FILTER     // Catch: java.lang.Throwable -> L6e
                boolean r15 = com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags.archiving()     // Catch: java.lang.Throwable -> L6e
                if (r15 == 0) goto La2
                android.content.pm.LauncherActivityInfoInternal r12 = r12.getMatchingArchivedAppActivityInfo(r13, r14)     // Catch: java.lang.Throwable -> L6e
                if (r12 == 0) goto La2
                r8.setPackage(r5)     // Catch: java.lang.Throwable -> L6e
                r8.setComponent(r13)     // Catch: java.lang.Throwable -> L6e
                goto La3
            La2:
                r0 = r3
            La3:
                if (r0 != 0) goto La9
                android.os.Binder.restoreCallingIdentity(r9)
                return r5
            La9:
                android.os.Binder.restoreCallingIdentity(r9)
                return r8
            Lad:
                android.os.Binder.restoreCallingIdentity(r9)
                throw r12
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.LauncherAppsService.LauncherAppsImpl.getMainActivityLaunchIntent(android.content.ComponentName, android.os.UserHandle, boolean):android.content.Intent");
        }

        public final LauncherActivityInfoInternal getMatchingArchivedAppActivityInfo(ComponentName componentName, UserHandle userHandle) {
            List generateLauncherActivitiesForArchivedApp = generateLauncherActivitiesForArchivedApp(componentName.getPackageName(), userHandle);
            if (generateLauncherActivitiesForArchivedApp.isEmpty()) {
                return null;
            }
            for (int i = 0; i < generateLauncherActivitiesForArchivedApp.size(); i++) {
                if (((LauncherActivityInfoInternal) generateLauncherActivitiesForArchivedApp.get(i)).getComponentName().equals(componentName)) {
                    return (LauncherActivityInfoInternal) generateLauncherActivitiesForArchivedApp.get(i);
                }
            }
            Slog.w("LauncherAppsService", TextUtils.formatSimple("Expected archived app component name: %s is not available!", new Object[]{componentName}));
            return null;
        }

        public final PackageInstallerService getPackageInstallerService() {
            if (this.mPackageInstallerService == null) {
                try {
                    this.mPackageInstallerService = ServiceManager.getService("package").getPackageInstaller();
                } catch (RemoteException e) {
                    Slog.wtf("LauncherAppsService", "Error getting IPackageInstaller", e);
                }
            }
            return this.mPackageInstallerService;
        }

        public final List getPreInstalledSystemPackages(UserHandle userHandle) {
            if (!canAccessProfile(userHandle.getIdentifier(), "Can't access preinstalled packages for another user")) {
                return new ArrayList();
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Set preInstallableSystemPackages = this.mUm.getPreInstallableSystemPackages(this.mUm.getUserInfo(userHandle.getIdentifier()).userType);
                return preInstallableSystemPackages == null ? new ArrayList() : List.copyOf(preInstallableSystemPackages);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final IntentSender getPrivateSpaceSettingsIntent() {
            IntentSender intentSender = null;
            if (!canAccessHiddenProfile(ILauncherApps.Stub.getCallingUid(), ILauncherApps.Stub.getCallingPid())) {
                Slog.e("LauncherAppsService", "Caller cannot access hidden profiles");
                return null;
            }
            int callingUserId = getCallingUserId();
            int callingUid = ILauncherApps.Stub.getCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Intent intent = new Intent("com.android.settings.action.OPEN_PRIVATE_SPACE_SETTINGS");
                intent.setFlags(268468224);
                if (this.mPackageManagerInternal.queryIntentActivities(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), 1048576L, callingUid, callingUserId).isEmpty()) {
                    return null;
                }
                PendingIntent activityAsUser = PendingIntent.getActivityAsUser(this.mContext, 0, intent, 201326592, null, UserHandle.of(callingUserId));
                if (activityAsUser != null) {
                    intentSender = activityAsUser.getIntentSender();
                }
                return intentSender;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final ParceledListSlice getShortcutConfigActivities(String str, String str2, UserHandle userHandle) {
            if (this.mShortcutServiceInternal.areShortcutsSupportedOnHomeScreen(userHandle.getIdentifier())) {
                return queryActivitiesForUser(new Intent("android.intent.action.CREATE_SHORTCUT").setPackage(str2), userHandle);
            }
            return null;
        }

        public final IntentSender getShortcutConfigActivityIntent(String str, final ComponentName componentName, UserHandle userHandle) {
            ensureShortcutPermission(str);
            IntentSender intentSender = null;
            if (!canAccessProfile(userHandle.getIdentifier(), "Cannot check package")) {
                return null;
            }
            Objects.requireNonNull(componentName);
            int injectBinderCallingUid = injectBinderCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                Intent intent = new Intent("android.intent.action.CREATE_SHORTCUT").setPackage(componentName.getPackageName());
                if (!this.mPackageManagerInternal.queryIntentActivities(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), 786432L, injectBinderCallingUid, userHandle.getIdentifier()).stream().anyMatch(new Predicate() { // from class: com.android.server.pm.LauncherAppsService$LauncherAppsImpl$$ExternalSyntheticLambda6
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        ComponentName componentName2 = componentName;
                        int i = LauncherAppsService.LauncherAppsImpl.$r8$clinit;
                        return componentName2.getClassName().equals(((ResolveInfo) obj).activityInfo.name);
                    }
                })) {
                    return null;
                }
                PendingIntent activityAsUser = PendingIntent.getActivityAsUser(this.mContext, 0, new Intent("android.intent.action.CREATE_SHORTCUT").setComponent(componentName), 1409286144, null, userHandle);
                if (activityAsUser != null) {
                    intentSender = activityAsUser.getIntentSender();
                }
                return intentSender;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final ParcelFileDescriptor getShortcutIconFd(String str, String str2, String str3, int i) {
            ensureShortcutPermission(str);
            if (!canAccessProfile(i, "Cannot access shortcuts")) {
                return null;
            }
            AndroidFuture androidFuture = new AndroidFuture();
            this.mShortcutServiceInternal.getShortcutIconFdAsync(getCallingUserId(), str, str2, str3, i, androidFuture);
            try {
                return (ParcelFileDescriptor) androidFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        public final int getShortcutIconResId(String str, String str2, String str3, int i) {
            ensureShortcutPermission(str);
            if (canAccessProfile(i, "Cannot access shortcuts")) {
                return this.mShortcutServiceInternal.getShortcutIconResId(getCallingUserId(), str, str2, str3, i);
            }
            return 0;
        }

        public final String getShortcutIconUri(String str, String str2, String str3, int i) {
            ensureShortcutPermission(str);
            if (!canAccessProfile(i, "Cannot access shortcuts")) {
                return null;
            }
            AndroidFuture androidFuture = new AndroidFuture();
            this.mShortcutServiceInternal.getShortcutIconUriAsync(getCallingUserId(), str, str2, str3, i, androidFuture);
            try {
                return (String) androidFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        public final PendingIntent getShortcutIntent(String str, String str2, String str3, Bundle bundle, UserHandle userHandle) {
            Objects.requireNonNull(str);
            Objects.requireNonNull(str2);
            Objects.requireNonNull(str3);
            Objects.requireNonNull(userHandle);
            ensureShortcutPermission(str);
            if (!canAccessProfile(userHandle.getIdentifier(), "Cannot get shortcuts")) {
                return null;
            }
            AndroidFuture androidFuture = new AndroidFuture();
            this.mShortcutServiceInternal.createShortcutIntentsAsync(getCallingUserId(), str, str2, str3, userHandle.getIdentifier(), injectBinderCallingPid(), injectBinderCallingUid(), androidFuture);
            try {
                Intent[] intentArr = (Intent[]) androidFuture.get();
                if (intentArr != null && intentArr.length != 0) {
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        return injectCreatePendingIntent(0, intentArr, 201326592, bundle, str2, this.mPackageManagerInternal.getPackageUid(str2, 268435456L, userHandle.getIdentifier()));
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                }
            } catch (InterruptedException | ExecutionException unused) {
            }
            return null;
        }

        public final ParceledListSlice getShortcuts(String str, ShortcutQueryWrapper shortcutQueryWrapper, UserHandle userHandle) {
            ensureShortcutPermission(str);
            if (!canAccessProfile(userHandle.getIdentifier(), "Cannot get shortcuts")) {
                StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("return empty shortcuts because callingPackage ", str, " cannot access user ");
                m.append(userHandle.getIdentifier());
                Log.e("LauncherAppsService", m.toString());
                return new ParceledListSlice(Collections.EMPTY_LIST);
            }
            long changedSince = shortcutQueryWrapper.getChangedSince();
            String str2 = shortcutQueryWrapper.getPackage();
            List shortcutIds = shortcutQueryWrapper.getShortcutIds();
            List locusIds = shortcutQueryWrapper.getLocusIds();
            ComponentName activity = shortcutQueryWrapper.getActivity();
            int queryFlags = shortcutQueryWrapper.getQueryFlags();
            if (shortcutIds != null && str2 == null) {
                throw new IllegalArgumentException("To query by shortcut ID, package name must also be set");
            }
            if (locusIds != null && str2 == null) {
                throw new IllegalArgumentException("To query by locus ID, package name must also be set");
            }
            if ((shortcutQueryWrapper.getQueryFlags() & 2048) != 0) {
                ensureStrictAccessShortcutsPermission(str);
            }
            return new ParceledListSlice(this.mShortcutServiceInternal.getShortcuts(getCallingUserId(), str, changedSince, str2, shortcutIds, locusIds, activity, queryFlags, userHandle.getIdentifier(), injectBinderCallingPid(), injectBinderCallingUid()));
        }

        public final void getShortcutsAsync(String str, ShortcutQueryWrapper shortcutQueryWrapper, UserHandle userHandle, AndroidFuture androidFuture) {
            ensureShortcutPermission(str);
            if (!canAccessProfile(userHandle.getIdentifier(), "Cannot get shortcuts")) {
                androidFuture.complete(Collections.EMPTY_LIST);
                return;
            }
            long changedSince = shortcutQueryWrapper.getChangedSince();
            String str2 = shortcutQueryWrapper.getPackage();
            List shortcutIds = shortcutQueryWrapper.getShortcutIds();
            List locusIds = shortcutQueryWrapper.getLocusIds();
            ComponentName activity = shortcutQueryWrapper.getActivity();
            int queryFlags = shortcutQueryWrapper.getQueryFlags();
            if (shortcutIds != null && str2 == null) {
                throw new IllegalArgumentException("To query by shortcut ID, package name must also be set");
            }
            if (locusIds != null && str2 == null) {
                throw new IllegalArgumentException("To query by locus ID, package name must also be set");
            }
            if ((shortcutQueryWrapper.getQueryFlags() & 2048) != 0) {
                ensureStrictAccessShortcutsPermission(str);
            }
            this.mShortcutServiceInternal.getShortcutsAsync(getCallingUserId(), str, changedSince, str2, shortcutIds, locusIds, activity, queryFlags, userHandle.getIdentifier(), injectBinderCallingPid(), injectBinderCallingUid(), androidFuture);
        }

        public final Bundle getSuspendedPackageLauncherExtras(String str, UserHandle userHandle) {
            int injectBinderCallingUid = injectBinderCallingUid();
            int identifier = userHandle.getIdentifier();
            if (canAccessProfile(identifier, "Cannot get launcher extras") && !this.mPackageManagerInternal.filterAppAccess(injectBinderCallingUid, identifier, str, true)) {
                return this.mPackageManagerInternal.getSuspendedPackageLauncherExtras(identifier, str);
            }
            return null;
        }

        public final List getUserProfiles() {
            int[] profileIdsExcludingHidden = !canAccessHiddenProfile(ILauncherApps.Stub.getCallingUid(), ILauncherApps.Stub.getCallingPid()) ? this.mUm.getProfileIdsExcludingHidden(getCallingUserId(), true) : this.mUm.getEnabledProfileIds(getCallingUserId());
            ArrayList arrayList = new ArrayList(profileIdsExcludingHidden.length);
            for (int i : profileIdsExcludingHidden) {
                arrayList.add(UserHandle.of(i));
            }
            return arrayList;
        }

        public final boolean hasShortcutHostPermission(String str) {
            verifyCallingPackage(str);
            return this.mShortcutServiceInternal.hasShortcutHostPermission(getCallingUserId(), str, injectBinderCallingPid(), injectBinderCallingUid());
        }

        public int injectBinderCallingPid() {
            return ILauncherApps.Stub.getCallingPid();
        }

        public int injectBinderCallingUid() {
            return ILauncherApps.Stub.getCallingUid();
        }

        public long injectClearCallingIdentity() {
            return Binder.clearCallingIdentity();
        }

        public PendingIntent injectCreatePendingIntent(int i, Intent[] intentArr, int i2, Bundle bundle, String str, int i3) {
            return this.mActivityManagerInternal.getPendingIntentActivityAsApp(i, intentArr, i2, (Bundle) null, str, i3);
        }

        public boolean injectHasAccessShortcutsPermission(int i, int i2) {
            return this.mContext.checkPermission("android.permission.ACCESS_SHORTCUTS", i, i2) == 0;
        }

        public boolean injectHasInteractAcrossUsersFullPermission(int i, int i2) {
            return this.mContext.checkPermission("android.permission.INTERACT_ACROSS_USERS_FULL", i, i2) == 0;
        }

        public void injectRestoreCallingIdentity(long j) {
            Binder.restoreCallingIdentity(j);
        }

        /* JADX WARN: Code restructure failed: missing block: B:35:0x0089, code lost:
        
            if (r10.isEnabled() != false) goto L37;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean isActivityEnabled(java.lang.String r11, android.content.ComponentName r12, android.os.UserHandle r13) {
            /*
                r10 = this;
                r11 = 1
                int r0 = r13.getIdentifier()
                java.lang.String r1 = "Cannot check component"
                boolean r0 = r10.canAccessProfile(r0, r1)
                r1 = 0
                if (r0 != 0) goto Lf
                return r1
            Lf:
                android.graphics.PorterDuffColorFilter r0 = com.android.server.pm.PackageArchiver.OPACITY_LAYER_FILTER
                boolean r0 = com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags.archiving()
                if (r0 == 0) goto L48
                if (r12 == 0) goto L48
                java.lang.String r0 = r12.getPackageName()
                if (r0 == 0) goto L48
                java.lang.String r0 = r12.getPackageName()
                java.util.List r0 = r10.generateLauncherActivitiesForArchivedApp(r0, r13)
                boolean r2 = r0.isEmpty()
                if (r2 != 0) goto L48
                r10 = r1
            L2e:
                int r13 = r0.size()
                if (r10 >= r13) goto L47
                java.lang.Object r13 = r0.get(r10)
                android.content.pm.LauncherActivityInfoInternal r13 = (android.content.pm.LauncherActivityInfoInternal) r13
                android.content.ComponentName r13 = r13.getComponentName()
                boolean r13 = r13.equals(r12)
                if (r13 == 0) goto L45
                return r11
            L45:
                int r10 = r10 + r11
                goto L2e
            L47:
                return r1
            L48:
                int r3 = r10.injectBinderCallingUid()
                android.content.pm.PackageManagerInternal r0 = r10.mPackageManagerInternal
                int r2 = r13.getIdentifier()
                com.android.server.pm.PackageManagerService$PackageManagerInternalImpl r0 = (com.android.server.pm.PackageManagerService.PackageManagerInternalImpl) r0
                com.android.server.pm.PackageManagerService r0 = r0.mService
                com.android.server.pm.Computer r0 = r0.snapshotComputer()
                int r0 = r0.getComponentEnabledSettingInternal(r3, r2, r12)
                if (r0 == r11) goto L98
                r2 = 2
                if (r0 == r2) goto L97
                r2 = 3
                if (r0 == r2) goto L97
                r2 = 4
                if (r0 == r2) goto L97
                long r8 = android.os.Binder.clearCallingIdentity()
                android.content.pm.PackageManagerInternal r10 = r10.mPackageManagerInternal     // Catch: java.lang.Throwable -> L8c
                int r4 = r13.getIdentifier()     // Catch: java.lang.Throwable -> L8c
                com.android.server.pm.PackageManagerService$PackageManagerInternalImpl r10 = (com.android.server.pm.PackageManagerService.PackageManagerInternalImpl) r10     // Catch: java.lang.Throwable -> L8c
                com.android.server.pm.PackageManagerService r10 = r10.mService     // Catch: java.lang.Throwable -> L8c
                com.android.server.pm.Computer r2 = r10.snapshotComputer()     // Catch: java.lang.Throwable -> L8c
                r5 = 786432(0xc0000, double:3.88549E-318)
                r7 = r12
                android.content.pm.ActivityInfo r10 = r2.getActivityInfoInternal(r3, r4, r5, r7)     // Catch: java.lang.Throwable -> L8c
                if (r10 == 0) goto L8e
                boolean r10 = r10.isEnabled()     // Catch: java.lang.Throwable -> L8c
                if (r10 == 0) goto L8e
                goto L8f
            L8c:
                r10 = move-exception
                goto L93
            L8e:
                r11 = r1
            L8f:
                android.os.Binder.restoreCallingIdentity(r8)
                return r11
            L93:
                android.os.Binder.restoreCallingIdentity(r8)
                throw r10
            L97:
                return r1
            L98:
                long r8 = android.os.Binder.clearCallingIdentity()
                android.content.pm.PackageManagerInternal r10 = r10.mPackageManagerInternal     // Catch: java.lang.Throwable -> Lce
                int r4 = r13.getIdentifier()     // Catch: java.lang.Throwable -> Lce
                com.android.server.pm.PackageManagerService$PackageManagerInternalImpl r10 = (com.android.server.pm.PackageManagerService.PackageManagerInternalImpl) r10     // Catch: java.lang.Throwable -> Lce
                com.android.server.pm.PackageManagerService r10 = r10.mService     // Catch: java.lang.Throwable -> Lce
                com.android.server.pm.Computer r2 = r10.snapshotComputer()     // Catch: java.lang.Throwable -> Lce
                r5 = 786432(0xc0000, double:3.88549E-318)
                r7 = r12
                android.content.pm.ActivityInfo r10 = r2.getActivityInfoInternal(r3, r4, r5, r7)     // Catch: java.lang.Throwable -> Lce
                if (r10 != 0) goto Ld0
                java.lang.String r10 = "LauncherAppsService"
                java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lce
                r11.<init>()     // Catch: java.lang.Throwable -> Lce
                r11.append(r12)     // Catch: java.lang.Throwable -> Lce
                java.lang.String r12 = " was enabled, but now it's removed."
                r11.append(r12)     // Catch: java.lang.Throwable -> Lce
                java.lang.String r11 = r11.toString()     // Catch: java.lang.Throwable -> Lce
                android.util.Slog.w(r10, r11)     // Catch: java.lang.Throwable -> Lce
                android.os.Binder.restoreCallingIdentity(r8)
                return r1
            Lce:
                r10 = move-exception
                goto Ld4
            Ld0:
                android.os.Binder.restoreCallingIdentity(r8)
                return r11
            Ld4:
                android.os.Binder.restoreCallingIdentity(r8)
                throw r10
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.LauncherAppsService.LauncherAppsImpl.isActivityEnabled(java.lang.String, android.content.ComponentName, android.os.UserHandle):boolean");
        }

        public final boolean isEnabledProfileOf(UserHandle userHandle, BroadcastCookie broadcastCookie, String str) {
            if (!isHiddenProfile(userHandle) || canAccessHiddenProfile(broadcastCookie.callingUid, broadcastCookie.callingPid)) {
                return this.mUserManagerInternal.isProfileAccessible(broadcastCookie.user.getIdentifier(), userHandle.getIdentifier(), str, false);
            }
            return false;
        }

        public final boolean isHiddenProfile(UserHandle userHandle) {
            if (!android.multiuser.Flags.enableLauncherAppsHiddenProfileChecks()) {
                return false;
            }
            long injectClearCallingIdentity = injectClearCallingIdentity();
            try {
                UserProperties userProperties = this.mUm.getUserProperties(userHandle);
                if (userProperties == null) {
                    return false;
                }
                return userProperties.getProfileApiVisibility() == 1;
            } catch (IllegalArgumentException unused) {
                return false;
            } finally {
                injectRestoreCallingIdentity(injectClearCallingIdentity);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:16:0x003e, code lost:
        
            if (r9.isArchived != false) goto L20;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean isPackageEnabled(java.lang.String r10, java.lang.String r11, android.os.UserHandle r12) {
            /*
                r9 = this;
                int r10 = r12.getIdentifier()
                java.lang.String r0 = "Cannot check package"
                boolean r10 = r9.canAccessProfile(r10, r0)
                r0 = 0
                if (r10 != 0) goto Le
                return r0
            Le:
                int r2 = r9.injectBinderCallingUid()
                long r7 = android.os.Binder.clearCallingIdentity()
                android.graphics.PorterDuffColorFilter r10 = com.android.server.pm.PackageArchiver.OPACITY_LAYER_FILTER     // Catch: java.lang.Throwable -> L41
                boolean r10 = com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags.archiving()     // Catch: java.lang.Throwable -> L41
                if (r10 == 0) goto L25
                r3 = 4832624640(0x1200c0000, double:2.387633814E-314)
            L23:
                r4 = r3
                goto L29
            L25:
                r3 = 537657344(0x200c0000, double:2.65638023E-315)
                goto L23
            L29:
                android.content.pm.PackageManagerInternal r1 = r9.mPackageManagerInternal     // Catch: java.lang.Throwable -> L41
                int r3 = r12.getIdentifier()     // Catch: java.lang.Throwable -> L41
                r6 = r11
                android.content.pm.PackageInfo r9 = r1.getPackageInfo(r2, r3, r4, r6)     // Catch: java.lang.Throwable -> L41
                if (r9 == 0) goto L44
                android.content.pm.ApplicationInfo r9 = r9.applicationInfo     // Catch: java.lang.Throwable -> L41
                boolean r10 = r9.enabled     // Catch: java.lang.Throwable -> L41
                if (r10 != 0) goto L43
                boolean r9 = r9.isArchived     // Catch: java.lang.Throwable -> L41
                if (r9 == 0) goto L44
                goto L43
            L41:
                r9 = move-exception
                goto L48
            L43:
                r0 = 1
            L44:
                android.os.Binder.restoreCallingIdentity(r7)
                return r0
            L48:
                android.os.Binder.restoreCallingIdentity(r7)
                throw r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.LauncherAppsService.LauncherAppsImpl.isPackageEnabled(java.lang.String, java.lang.String, android.os.UserHandle):boolean");
        }

        public final boolean isPackageVisibleToListener(UserHandle userHandle, BroadcastCookie broadcastCookie, String str) {
            return !this.mPackageManagerInternal.filterAppAccess(broadcastCookie.callingUid, userHandle.getIdentifier(), str, false);
        }

        public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
            int injectBinderCallingUid = injectBinderCallingUid();
            if (injectBinderCallingUid != 2000 && injectBinderCallingUid != 0) {
                throw new SecurityException("Caller must be shell");
            }
            long injectClearCallingIdentity = injectClearCallingIdentity();
            try {
                int exec = new LauncherAppsShellCommand().exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
                if (resultReceiver != null) {
                    resultReceiver.send(exec, null);
                }
            } finally {
                injectRestoreCallingIdentity(injectClearCallingIdentity);
            }
        }

        public final void pinShortcuts(String str, String str2, List list, UserHandle userHandle) {
            if (this.mShortcutServiceInternal.areShortcutsSupportedOnHomeScreen(userHandle.getIdentifier())) {
                ensureShortcutPermission(str);
            } else {
                ensureStrictAccessShortcutsPermission(str);
            }
            ensureShortcutPermission(str);
            if (canAccessProfile(userHandle.getIdentifier(), "Cannot pin shortcuts")) {
                this.mShortcutServiceInternal.pinShortcuts(getCallingUserId(), str, str2, list, userHandle.getIdentifier());
            }
        }

        public void postToPackageMonitorHandler(Runnable runnable) {
            this.mCallbackHandler.post(runnable);
        }

        public final ParceledListSlice queryActivitiesForUser(Intent intent, UserHandle userHandle) {
            if (!canAccessProfile(userHandle.getIdentifier(), "Cannot retrieve activities")) {
                return null;
            }
            int injectBinderCallingUid = injectBinderCallingUid();
            long injectClearCallingIdentity = injectClearCallingIdentity();
            try {
                return new ParceledListSlice(queryIntentLauncherActivities(intent, injectBinderCallingUid, userHandle));
            } finally {
                injectRestoreCallingIdentity(injectClearCallingIdentity);
            }
        }

        public final List queryIntentLauncherActivities(Intent intent, int i, UserHandle userHandle) {
            IncrementalStatesInfo incrementalStatesInfo;
            List queryIntentActivities = this.mPackageManagerInternal.queryIntentActivities(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), 786432L, i, userHandle.getIdentifier());
            int size = queryIntentActivities.size();
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < size; i2++) {
                ResolveInfo resolveInfo = (ResolveInfo) queryIntentActivities.get(i2);
                String str = resolveInfo.activityInfo.packageName;
                if (str != null && (incrementalStatesInfo = this.mPackageManagerInternal.getIncrementalStatesInfo(i, userHandle.getIdentifier(), str)) != null) {
                    arrayList.add(new LauncherActivityInfoInternal(resolveInfo.activityInfo, incrementalStatesInfo, userHandle));
                }
            }
            return arrayList;
        }

        public final void registerDumpCallback(IDumpCallback iDumpCallback) {
            if (PermissionChecker.checkCallingOrSelfPermissionForPreflight(this.mContext, "android.permission.READ_FRAME_BUFFER") != 0) {
                Log.w("LauncherAppsService", "caller lacks permissions to registerDumpCallback");
            } else {
                this.mDumpCallbacks.register(iDumpCallback, this.mContext.getPackageManager().getNameForUid(Binder.getCallingUid()));
            }
        }

        public final void registerPackageInstallerCallback(String str, IPackageInstallerCallback iPackageInstallerCallback) {
            verifyCallingPackage(str);
            final BroadcastCookie broadcastCookie = new BroadcastCookie(ILauncherApps.Stub.getCallingPid(), ILauncherApps.Stub.getCallingUid(), new UserHandle(getCallingUserId()), str);
            PackageInstallerService packageInstallerService = getPackageInstallerService();
            IntPredicate intPredicate = new IntPredicate() { // from class: com.android.server.pm.LauncherAppsService$LauncherAppsImpl$$ExternalSyntheticLambda4
                @Override // java.util.function.IntPredicate
                public final boolean test(int i) {
                    LauncherAppsService.LauncherAppsImpl launcherAppsImpl = LauncherAppsService.LauncherAppsImpl.this;
                    LauncherAppsService.BroadcastCookie broadcastCookie2 = broadcastCookie;
                    int i2 = LauncherAppsService.LauncherAppsImpl.$r8$clinit;
                    launcherAppsImpl.getClass();
                    return launcherAppsImpl.isEnabledProfileOf(new UserHandle(i), broadcastCookie2, "shouldReceiveEvent");
                }
            };
            PackageInstallerService.Callbacks callbacks = packageInstallerService.mCallbacks;
            callbacks.mCallbacks.register(iPackageInstallerCallback, new PackageInstallerService.BroadcastCookie(Binder.getCallingUid(), intPredicate));
        }

        public final void registerShortcutChangeCallback(String str, ShortcutQueryWrapper shortcutQueryWrapper, IShortcutChangeCallback iShortcutChangeCallback) {
            ensureShortcutPermission(str);
            if (shortcutQueryWrapper.getShortcutIds() != null && shortcutQueryWrapper.getPackage() == null) {
                throw new IllegalArgumentException("To query by shortcut ID, package name must also be set");
            }
            if (shortcutQueryWrapper.getLocusIds() != null && shortcutQueryWrapper.getPackage() == null) {
                throw new IllegalArgumentException("To query by locus ID, package name must also be set");
            }
            UserHandle of = UserHandle.of(UserHandle.getUserId(injectBinderCallingUid()));
            if (injectHasInteractAcrossUsersFullPermission(injectBinderCallingPid(), injectBinderCallingUid())) {
                of = null;
            }
            ShortcutChangeHandler shortcutChangeHandler = this.mShortcutChangeHandler;
            synchronized (shortcutChangeHandler) {
                shortcutChangeHandler.mCallbacks.unregister(iShortcutChangeCallback);
                shortcutChangeHandler.mCallbacks.register(iShortcutChangeCallback, new Pair(shortcutQueryWrapper, of));
            }
        }

        public final void removeOnAppsChangedListener(IOnAppsChangedListener iOnAppsChangedListener) {
            synchronized (this.mListeners) {
                try {
                    this.mListeners.unregister(iOnAppsChangedListener);
                    if (this.mListeners.getRegisteredCallbackCount() == 0 && this.mIsWatchingPackageBroadcasts) {
                        this.mContext.unregisterReceiver(this.mPackageRemovedListener);
                        this.mPackageMonitor.unregister();
                        this.mIsWatchingPackageBroadcasts = false;
                    }
                    if (this.mRegisteredListenersForDump.containsKey(iOnAppsChangedListener)) {
                        this.mRegisteredListenersForDump.remove(iOnAppsChangedListener);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final LauncherActivityInfoInternal resolveLauncherActivityInternal(String str, ComponentName componentName, UserHandle userHandle) {
            if (!canAccessProfile(userHandle.getIdentifier(), "Cannot resolve activity") || componentName == null || componentName.getPackageName() == null) {
                return null;
            }
            int injectBinderCallingUid = injectBinderCallingUid();
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                PackageManagerInternal packageManagerInternal = this.mPackageManagerInternal;
                ActivityInfo activityInfoInternal = ((PackageManagerService.PackageManagerInternalImpl) packageManagerInternal).mService.snapshotComputer().getActivityInfoInternal(injectBinderCallingUid, userHandle.getIdentifier(), 786432L, componentName);
                if (activityInfoInternal == null) {
                    PorterDuffColorFilter porterDuffColorFilter = PackageArchiver.OPACITY_LAYER_FILTER;
                    if (com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags.archiving()) {
                        return getMatchingArchivedAppActivityInfo(componentName, userHandle);
                    }
                    return null;
                }
                IncrementalStatesInfo incrementalStatesInfo = this.mPackageManagerInternal.getIncrementalStatesInfo(injectBinderCallingUid, userHandle.getIdentifier(), componentName.getPackageName());
                if (incrementalStatesInfo == null) {
                    return null;
                }
                return new LauncherActivityInfoInternal(activityInfoInternal, incrementalStatesInfo, userHandle);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void saveViewCaptureData() {
            if (PermissionChecker.checkCallingOrSelfPermissionForPreflight(this.mContext, "android.permission.READ_FRAME_BUFFER") == 0) {
                forEachViewCaptureWindow(new LauncherAppsService$LauncherAppsImpl$$ExternalSyntheticLambda1(this));
            } else {
                Log.w("LauncherAppsService", "caller lacks permissions to save view capture data");
            }
        }

        public final void setArchiveCompatibilityOptions(final boolean z, final boolean z2) {
            final int callingUid = Binder.getCallingUid();
            Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: com.android.server.pm.LauncherAppsService$LauncherAppsImpl$$ExternalSyntheticLambda0
                public final void runOrThrow() {
                    LauncherAppsService.LauncherAppsImpl launcherAppsImpl = LauncherAppsService.LauncherAppsImpl.this;
                    int i = callingUid;
                    boolean z3 = z;
                    boolean z4 = z2;
                    launcherAppsImpl.mAppOpsManager.setUidMode(145, i, !z3 ? 1 : 0);
                    launcherAppsImpl.mAppOpsManager.setUidMode(146, i, !z4 ? 1 : 0);
                }
            });
        }

        public final boolean shouldHideFromSuggestions(String str, UserHandle userHandle) {
            int identifier = userHandle.getIdentifier();
            if (!canAccessProfile(identifier, "cannot get shouldHideFromSuggestions")) {
                return false;
            }
            PorterDuffColorFilter porterDuffColorFilter = PackageArchiver.OPACITY_LAYER_FILTER;
            if (com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags.archiving() && str != null) {
                ApplicationInfo applicationInfo = (ApplicationInfo) Binder.withCleanCallingIdentity(new LauncherAppsService$LauncherAppsImpl$$ExternalSyntheticLambda2(this, str, injectBinderCallingUid(), userHandle));
                if (!((applicationInfo == null || !applicationInfo.isArchived) ? Collections.EMPTY_LIST : List.of(applicationInfo)).isEmpty()) {
                    return true;
                }
            }
            if (this.mPackageManagerInternal.filterAppAccess(Binder.getCallingUid(), identifier, str, true)) {
                return false;
            }
            PackageStateInternal packageStateInternal = ((PackageManagerService.PackageManagerInternalImpl) this.mPackageManagerInternal).getPackageStateInternal(str);
            return ((packageStateInternal == null ? 0 : packageStateInternal.getUserStateOrDefault(identifier).getDistractionFlags()) & 1) != 0;
        }

        public final boolean shouldShowSyntheticActivity(UserHandle userHandle, ApplicationInfo applicationInfo) {
            ComponentName profileOwnerAsUser;
            if (applicationInfo == null || applicationInfo.isSystemApp() || applicationInfo.isUpdatedSystemApp()) {
                return false;
            }
            String str = applicationInfo.packageName;
            List profiles = this.mUm.getProfiles(userHandle.getIdentifier());
            for (int i = 0; i < profiles.size(); i++) {
                UserInfo userInfo = (UserInfo) profiles.get(i);
                if (userInfo.isManagedProfile() && (profileOwnerAsUser = this.mDpm.getProfileOwnerAsUser(userInfo.getUserHandle())) != null && profileOwnerAsUser.getPackageName().equals(str)) {
                    return false;
                }
            }
            if (this.mPackageManagerInternal.getPackage(applicationInfo.packageName) == null || !(!ArrayUtils.isEmpty(r10.getRequestedPermissions()))) {
                return false;
            }
            String str2 = applicationInfo.packageName;
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setPackage(str2);
            List queryIntentActivities = this.mPackageManagerInternal.queryIntentActivities(intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), 512L, Binder.getCallingUid(), getCallingUserId());
            int size = queryIntentActivities.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (((ResolveInfo) queryIntentActivities.get(i2)).activityInfo.enabled) {
                    return true;
                }
            }
            return false;
        }

        public final void showAppDetailsAsUser(IApplicationThread iApplicationThread, String str, String str2, ComponentName componentName, Rect rect, Bundle bundle, UserHandle userHandle) {
            int i;
            if (canAccessProfile(userHandle.getIdentifier(), "Cannot show app details")) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    String packageName = componentName.getPackageName();
                    try {
                        i = this.mContext.getPackageManager().getApplicationInfo(packageName, 4194304).uid;
                    } catch (PackageManager.NameNotFoundException e) {
                        Log.d("LauncherAppsService", "package not found: " + e);
                        i = -1;
                    }
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.fromParts("package", packageName, null));
                    intent.putExtra("uId", i);
                    intent.setFlags(268468224);
                    intent.setSourceBounds(rect);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    this.mActivityTaskManagerInternal.startActivityAsUser(iApplicationThread, str, str2, intent, null, 268435456, getActivityOptionsForLauncher(bundle), userHandle.getIdentifier());
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                }
            }
        }

        public final void startActivityAsUser(IApplicationThread iApplicationThread, String str, String str2, ComponentName componentName, Rect rect, Bundle bundle, UserHandle userHandle) {
            if (canAccessProfile(userHandle.getIdentifier(), "Cannot start activity")) {
                Intent mainActivityLaunchIntent = getMainActivityLaunchIntent(componentName, userHandle, true);
                if (mainActivityLaunchIntent == null) {
                    throw new SecurityException(AmbientContextManagerPerUserService$$ExternalSyntheticOutline0.m(componentName, "Attempt to launch activity without  category Intent.CATEGORY_LAUNCHER "));
                }
                mainActivityLaunchIntent.setSourceBounds(rect);
                this.mActivityTaskManagerInternal.startActivityAsUser(iApplicationThread, str, str2, mainActivityLaunchIntent, null, 268435456, getActivityOptionsForLauncher(bundle), userHandle.getIdentifier());
            }
        }

        public final void startSessionDetailsActivityAsUser(IApplicationThread iApplicationThread, String str, String str2, PackageInstaller.SessionInfo sessionInfo, Rect rect, Bundle bundle, UserHandle userHandle) {
            int identifier = userHandle.getIdentifier();
            if (canAccessProfile(identifier, "Cannot start details activity")) {
                Intent putExtra = new Intent("android.intent.action.VIEW").setData(new Uri.Builder().scheme("market").authority("details").appendQueryParameter("id", sessionInfo.appPackageName).build()).putExtra("android.intent.extra.REFERRER", new Uri.Builder().scheme("android-app").authority(str).build());
                putExtra.setSourceBounds(rect);
                if (sessionInfo.isUnarchival() && "android".equals(str)) {
                    putExtra.setPackage(sessionInfo.installerPackageName);
                }
                this.mActivityTaskManagerInternal.startActivityAsUser(iApplicationThread, str, str2, putExtra, null, 268435456, getActivityOptionsForLauncher(bundle), identifier);
            }
        }

        public final boolean startShortcut(String str, String str2, String str3, String str4, Rect rect, Bundle bundle, int i) {
            return startShortcutInner(injectBinderCallingUid(), injectBinderCallingPid(), UserHandle.getUserId(injectBinderCallingUid()), str, str2, str3, str4, rect, bundle, i);
        }

        public final boolean startShortcutInner(int i, int i2, int i3, String str, String str2, String str3, String str4, Rect rect, Bundle bundle, int i4) {
            Bundle bundle2;
            verifyCallingPackage(str, i);
            if (!canAccessProfile(i4, "Cannot start activity")) {
                return false;
            }
            if (!this.mShortcutServiceInternal.isPinnedByCaller(i3, str, str2, str4, i4)) {
                verifyCallingPackage(str, i);
                if (!this.mShortcutServiceInternal.hasShortcutHostPermission(UserHandle.getUserId(i), str, i2, i)) {
                    throw new SecurityException("Caller can't access shortcut information");
                }
            }
            AndroidFuture androidFuture = new AndroidFuture();
            this.mShortcutServiceInternal.createShortcutIntentsAsync(getCallingUserId(), str, str2, str4, i4, injectBinderCallingPid(), injectBinderCallingUid(), androidFuture);
            try {
                Intent[] intentArr = (Intent[]) androidFuture.get();
                if (intentArr == null || intentArr.length == 0) {
                    return false;
                }
                ActivityOptions fromBundle = ActivityOptions.fromBundle(bundle);
                if (fromBundle != null) {
                    if (fromBundle.isApplyActivityFlagsForBubbles()) {
                        intentArr[0].addFlags(524288);
                        intentArr[0].addFlags(134217728);
                    }
                    if (fromBundle.isApplyMultipleTaskFlagForShortcut()) {
                        intentArr[0].addFlags(134217728);
                    }
                    if (fromBundle.isApplyNoUserActionFlagForShortcut()) {
                        intentArr[0].addFlags(262144);
                    }
                }
                intentArr[0].addFlags(268435456);
                intentArr[0].setSourceBounds(rect);
                String shortcutStartingThemeResName = this.mShortcutServiceInternal.getShortcutStartingThemeResName(i3, str, str2, str4, i4);
                if (shortcutStartingThemeResName == null || shortcutStartingThemeResName.isEmpty()) {
                    bundle2 = bundle;
                } else {
                    bundle2 = bundle == null ? new Bundle() : bundle;
                    bundle2.putString("android.activity.splashScreenTheme", shortcutStartingThemeResName);
                }
                int startActivitiesAsPackage = this.mActivityTaskManagerInternal.startActivitiesAsPackage(str2, str3, i4, intentArr, getActivityOptionsForLauncher(bundle2));
                if (ActivityManager.isStartResultSuccessful(startActivitiesAsPackage)) {
                    return true;
                }
                Log.e("LauncherAppsService", "Couldn't start activity, code=" + startActivitiesAsPackage);
                return false;
            } catch (InterruptedException | SecurityException | ExecutionException unused) {
                return false;
            }
        }

        public final void startWatchingPackageBroadcasts() {
            if (this.mIsWatchingPackageBroadcasts) {
                return;
            }
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED_INTERNAL");
            intentFilter.addDataScheme("package");
            Context context = this.mContext;
            PackageRemovedListener packageRemovedListener = this.mPackageRemovedListener;
            UserHandle userHandle = UserHandle.ALL;
            context.registerReceiverAsUser(packageRemovedListener, userHandle, intentFilter, null, this.mCallbackHandler);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mPackageMonitor.register(this.mContext, userHandle, this.mCallbackHandler);
                Binder.restoreCallingIdentity(clearCallingIdentity);
                this.mIsWatchingPackageBroadcasts = true;
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }

        public final void unRegisterDumpCallback(IDumpCallback iDumpCallback) {
            if (PermissionChecker.checkCallingOrSelfPermissionForPreflight(this.mContext, "android.permission.READ_FRAME_BUFFER") == 0) {
                this.mDumpCallbacks.unregister(iDumpCallback);
            } else {
                Log.w("LauncherAppsService", "caller lacks permissions to unRegisterDumpCallback");
            }
        }

        public final void uncacheShortcuts(String str, String str2, List list, UserHandle userHandle, int i) {
            ensureStrictAccessShortcutsPermission(str);
            if (canAccessProfile(userHandle.getIdentifier(), "Cannot uncache shortcuts")) {
                ShortcutServiceInternal shortcutServiceInternal = this.mShortcutServiceInternal;
                int callingUserId = getCallingUserId();
                int identifier = userHandle.getIdentifier();
                int i2 = i == 0 ? EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION : i == 1 ? 1073741824 : i == 2 ? 536870912 : 0;
                Preconditions.checkArgumentPositive(i2, "Invalid cache owner");
                shortcutServiceInternal.uncacheShortcuts(callingUserId, str, str2, list, identifier, i2);
            }
        }

        public final void unregisterShortcutChangeCallback(String str, IShortcutChangeCallback iShortcutChangeCallback) {
            ensureShortcutPermission(str);
            ShortcutChangeHandler shortcutChangeHandler = this.mShortcutChangeHandler;
            synchronized (shortcutChangeHandler) {
                shortcutChangeHandler.mCallbacks.unregister(iShortcutChangeCallback);
            }
        }

        public final void verifyCallingPackage(String str) {
            verifyCallingPackage(str, injectBinderCallingUid());
        }

        public void verifyCallingPackage(String str, int i) {
            int i2;
            try {
                i2 = this.mIPM.getPackageUid(str, 794624L, UserHandle.getUserId(i));
            } catch (RemoteException unused) {
                i2 = -1;
            }
            if (i2 < 0) {
                StorageManagerService$$ExternalSyntheticOutline0.m("Package not found: ", str, "LauncherAppsService");
            }
            if (i2 != i) {
                throw new SecurityException("Calling package name mismatch");
            }
        }
    }

    public LauncherAppsService(Context context) {
        super(context);
        this.mLauncherAppsImpl = new LauncherAppsImpl(context);
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        final ?? r1 = this.mLauncherAppsImpl;
        publishBinderService("launcherapps", r1);
        List<UserHandle> userProfiles = r1.mUm.getUserProfiles();
        if (userProfiles != null) {
            for (final UserHandle userHandle : userProfiles) {
                r1.mPackageManagerInternal.forEachInstalledPackage(userHandle.getIdentifier(), new Consumer() { // from class: com.android.server.pm.LauncherAppsService$LauncherAppsImpl$$ExternalSyntheticLambda8
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        LauncherAppsService.LauncherAppsImpl launcherAppsImpl = LauncherAppsService.LauncherAppsImpl.this;
                        UserHandle userHandle2 = userHandle;
                        int i = LauncherAppsService.LauncherAppsImpl.$r8$clinit;
                        launcherAppsImpl.getClass();
                        String packageName = ((AndroidPackage) obj).getPackageName();
                        IncrementalStatesInfo incrementalStatesInfo = launcherAppsImpl.mPackageManagerInternal.getIncrementalStatesInfo(Process.myUid(), userHandle2.getIdentifier(), packageName);
                        if (incrementalStatesInfo == null || !incrementalStatesInfo.isLoading()) {
                            return;
                        }
                        launcherAppsImpl.mPackageManagerInternal.registerInstalledLoadingProgressCallback(packageName, launcherAppsImpl.new PackageLoadingProgressCallback(packageName, userHandle2), userHandle2.getIdentifier());
                    }
                });
            }
        }
        LocalServices.addService(LauncherAppsImpl.LocalService.class, r1.mInternal);
    }
}
