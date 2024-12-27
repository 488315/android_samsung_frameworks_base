package com.android.server.appbinding;

import android.app.AppGlobals;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageManager;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.carrier.ICarrierMessagingClientService;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseBooleanArray;

import com.android.internal.os.BackgroundThread;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.DumpUtils;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.am.PersistentConnection;
import com.android.server.appbinding.finders.CarrierMessagingClientServiceFinder;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class AppBindingService extends Binder {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ArrayList mApps;
    public final ArrayList mConnections;
    public AppBindingConstants mConstants;
    public final Context mContext;
    public final Handler mHandler;
    public final IPackageManager mIPackageManager;
    public final Injector mInjector;
    final BroadcastReceiver mPackageUserMonitor;
    public final AnonymousClass1 mSettingsObserver;
    public final Object mLock = new Object();
    public final SparseBooleanArray mRunningUsers = new SparseBooleanArray(2);

    public final class AppServiceConnection extends PersistentConnection {
        public final AppBindingConstants mConstants;
        public final CarrierMessagingClientServiceFinder mFinder;

        public AppServiceConnection(
                Context context,
                int i,
                AppBindingConstants appBindingConstants,
                Handler handler,
                CarrierMessagingClientServiceFinder carrierMessagingClientServiceFinder,
                ComponentName componentName) {
            super(
                    "AppBindingService",
                    context,
                    handler,
                    i,
                    componentName,
                    appBindingConstants.SERVICE_RECONNECT_BACKOFF_SEC,
                    appBindingConstants.SERVICE_RECONNECT_BACKOFF_INCREASE,
                    appBindingConstants.SERVICE_RECONNECT_MAX_BACKOFF_SEC,
                    appBindingConstants.SERVICE_STABLE_CONNECTION_THRESHOLD_SEC);
            this.mFinder = carrierMessagingClientServiceFinder;
            this.mConstants = appBindingConstants;
        }

        @Override // com.android.server.am.PersistentConnection
        public final Object asInterface(IBinder iBinder) {
            this.mFinder.getClass();
            return ICarrierMessagingClientService.Stub.asInterface(iBinder);
        }

        @Override // com.android.server.am.PersistentConnection
        public final int getBindFlags() {
            this.mFinder.getClass();
            return this.mConstants.SMS_APP_BIND_FLAGS;
        }
    }

    public final class Injector {}

    public final class Lifecycle extends SystemService {
        public final AppBindingService mService;

        public Lifecycle(Context context) {
            super(context);
            Injector injector = new Injector();
            this.mService = new AppBindingService(injector, context);
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            AppBindingService appBindingService = this.mService;
            int i2 = AppBindingService.$r8$clinit;
            appBindingService.getClass();
            if (i != 550) {
                if (i != 600) {
                    return;
                }
                synchronized (appBindingService.mLock) {
                    appBindingService.forAllAppsLocked(
                            new AppBindingService$$ExternalSyntheticLambda2());
                }
                return;
            }
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
            intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
            intentFilter.addDataScheme("package");
            Context context = appBindingService.mContext;
            BroadcastReceiver broadcastReceiver = appBindingService.mPackageUserMonitor;
            UserHandle userHandle = UserHandle.ALL;
            context.registerReceiverAsUser(
                    broadcastReceiver, userHandle, intentFilter, null, appBindingService.mHandler);
            appBindingService.mContext.registerReceiverAsUser(
                    appBindingService.mPackageUserMonitor,
                    userHandle,
                    BatteryService$$ExternalSyntheticOutline0.m(
                            "android.intent.action.USER_REMOVED"),
                    null,
                    appBindingService.mHandler);
            appBindingService
                    .mContext
                    .getContentResolver()
                    .registerContentObserver(
                            Settings.Global.getUriFor("app_binding_constants"),
                            false,
                            appBindingService.mSettingsObserver);
            appBindingService.refreshConstants();
        }

        @Override // com.android.server.SystemService
        public final void onStart() {
            publishBinderService("app_binding", this.mService);
        }

        @Override // com.android.server.SystemService
        public final void onUserStarting(SystemService.TargetUser targetUser) {
            AppBindingService appBindingService = this.mService;
            int userIdentifier = targetUser.getUserIdentifier();
            synchronized (appBindingService.mLock) {
                appBindingService.mRunningUsers.append(userIdentifier, true);
                appBindingService.bindServicesLocked(userIdentifier, null);
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserStopping(SystemService.TargetUser targetUser) {
            AppBindingService appBindingService = this.mService;
            int userIdentifier = targetUser.getUserIdentifier();
            synchronized (appBindingService.mLock) {
                appBindingService.unbindServicesLocked(userIdentifier, null);
                appBindingService.mRunningUsers.delete(userIdentifier);
            }
        }

        @Override // com.android.server.SystemService
        public final void onUserUnlocking(SystemService.TargetUser targetUser) {
            AppBindingService appBindingService = this.mService;
            int userIdentifier = targetUser.getUserIdentifier();
            synchronized (appBindingService.mLock) {
                appBindingService.bindServicesLocked(userIdentifier, null);
            }
        }
    }

    /* renamed from: -$$Nest$mhandlePackageAddedReplacing, reason: not valid java name */
    public static void m227$$Nest$mhandlePackageAddedReplacing(
            AppBindingService appBindingService, String str, int i) {
        CarrierMessagingClientServiceFinder carrierMessagingClientServiceFinder;
        synchronized (appBindingService.mLock) {
            int i2 = 0;
            while (true) {
                try {
                    if (i2 >= appBindingService.mApps.size()) {
                        carrierMessagingClientServiceFinder = null;
                        break;
                    } else {
                        carrierMessagingClientServiceFinder =
                                (CarrierMessagingClientServiceFinder)
                                        appBindingService.mApps.get(i2);
                        if (!str.equals(
                                (String)
                                        CollectionUtils.firstOrNull(
                                                carrierMessagingClientServiceFinder.mRoleManager
                                                        .getRoleHoldersAsUser(
                                                                "android.app.role.SMS",
                                                                UserHandle.of(i))))) {
                            i2++;
                        }
                    }
                } finally {
                }
            }
            if (carrierMessagingClientServiceFinder != null) {
                appBindingService.unbindServicesLocked(i, carrierMessagingClientServiceFinder);
                appBindingService.bindServicesLocked(i, carrierMessagingClientServiceFinder);
            }
        }
    }

    public AppBindingService(Injector injector, Context context) {
        ArrayList arrayList = new ArrayList();
        this.mApps = arrayList;
        this.mConnections = new ArrayList();
        this.mSettingsObserver =
                new ContentObserver() { // from class:
                                        // com.android.server.appbinding.AppBindingService.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        AppBindingService.this.refreshConstants();
                    }
                };
        this.mPackageUserMonitor =
                new BroadcastReceiver() { // from class:
                                          // com.android.server.appbinding.AppBindingService.2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        final int intExtra =
                                intent.getIntExtra("android.intent.extra.user_handle", -10000);
                        if (intExtra == -10000) {
                            Slog.w(
                                    "AppBindingService",
                                    "Intent broadcast does not contain user handle: " + intent);
                            return;
                        }
                        String action = intent.getAction();
                        if ("android.intent.action.USER_REMOVED".equals(action)) {
                            AppBindingService appBindingService = AppBindingService.this;
                            synchronized (appBindingService.mLock) {
                                appBindingService.forAllAppsLocked(
                                        new Consumer() { // from class:
                                                         // com.android.server.appbinding.AppBindingService$$ExternalSyntheticLambda4
                                            @Override // java.util.function.Consumer
                                            public final void accept(Object obj) {
                                                int i = intExtra;
                                                CarrierMessagingClientServiceFinder
                                                        carrierMessagingClientServiceFinder =
                                                                (CarrierMessagingClientServiceFinder)
                                                                        obj;
                                                synchronized (
                                                        carrierMessagingClientServiceFinder.mLock) {
                                                    carrierMessagingClientServiceFinder
                                                            .mTargetPackages.delete(i);
                                                    carrierMessagingClientServiceFinder
                                                            .mTargetServices.delete(i);
                                                    carrierMessagingClientServiceFinder
                                                            .mLastMessages.delete(i);
                                                }
                                            }
                                        });
                                appBindingService.mRunningUsers.delete(intExtra);
                            }
                            return;
                        }
                        Uri data = intent.getData();
                        String schemeSpecificPart =
                                data != null ? data.getSchemeSpecificPart() : null;
                        if (schemeSpecificPart == null) {
                            Slog.w(
                                    "AppBindingService",
                                    "Intent broadcast does not contain package name: " + intent);
                            return;
                        }
                        boolean booleanExtra =
                                intent.getBooleanExtra("android.intent.extra.REPLACING", false);
                        action.getClass();
                        if (action.equals("android.intent.action.PACKAGE_CHANGED")) {
                            AppBindingService.m227$$Nest$mhandlePackageAddedReplacing(
                                    AppBindingService.this, schemeSpecificPart, intExtra);
                        } else if (action.equals("android.intent.action.PACKAGE_ADDED")
                                && booleanExtra) {
                            AppBindingService.m227$$Nest$mhandlePackageAddedReplacing(
                                    AppBindingService.this, schemeSpecificPart, intExtra);
                        }
                    }
                };
        this.mInjector = injector;
        this.mContext = context;
        this.mIPackageManager = AppGlobals.getPackageManager();
        this.mHandler = BackgroundThread.getHandler();
        arrayList.add(
                new CarrierMessagingClientServiceFinder(
                        context,
                        new BiConsumer() { // from class:
                                           // com.android.server.appbinding.AppBindingService$$ExternalSyntheticLambda0
                            @Override // java.util.function.BiConsumer
                            public final void accept(Object obj, Object obj2) {
                                AppBindingService appBindingService = AppBindingService.this;
                                CarrierMessagingClientServiceFinder
                                        carrierMessagingClientServiceFinder =
                                                (CarrierMessagingClientServiceFinder) obj;
                                int intValue = ((Integer) obj2).intValue();
                                synchronized (appBindingService.mLock) {
                                    carrierMessagingClientServiceFinder.getClass();
                                    appBindingService.unbindServicesLocked(
                                            intValue, carrierMessagingClientServiceFinder);
                                    appBindingService.bindServicesLocked(
                                            intValue, carrierMessagingClientServiceFinder);
                                }
                            }
                        }));
        this.mConstants = new AppBindingConstants("");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void bindServicesLocked(
            int r17,
            com.android.server.appbinding.finders.CarrierMessagingClientServiceFinder r18) {
        /*
            Method dump skipped, instructions count: 309
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.appbinding.AppBindingService.bindServicesLocked(int,"
                    + " com.android.server.appbinding.finders.CarrierMessagingClientServiceFinder):void");
    }

    @Override // android.os.Binder
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "AppBindingService", printWriter)) {
            if (strArr.length > 0 && "-s".equals(strArr[0])) {
                dumpSimple(printWriter);
                return;
            }
            synchronized (this.mLock) {
                try {
                    this.mConstants.dump(printWriter);
                    printWriter.println();
                    printWriter.print("  Running users:");
                    for (int i = 0; i < this.mRunningUsers.size(); i++) {
                        if (this.mRunningUsers.valueAt(i)) {
                            printWriter.print(" ");
                            printWriter.print(this.mRunningUsers.keyAt(i));
                        }
                    }
                    printWriter.println();
                    printWriter.println("  Connections:");
                    for (int i2 = 0; i2 < this.mConnections.size(); i2++) {
                        AppServiceConnection appServiceConnection =
                                (AppServiceConnection) this.mConnections.get(i2);
                        printWriter.print("    App type: ");
                        appServiceConnection.mFinder.getClass();
                        printWriter.print("[Default SMS app]");
                        printWriter.println();
                        appServiceConnection.dump("      ", printWriter);
                    }
                    if (this.mConnections.size() == 0) {
                        printWriter.println("    None:");
                    }
                    printWriter.println();
                    printWriter.println("  Finders:");
                    forAllAppsLocked(
                            new AppBindingService$$ExternalSyntheticLambda1(0, printWriter));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void dumpSimple(PrintWriter printWriter) {
        boolean z;
        boolean z2;
        int i;
        synchronized (this.mLock) {
            for (int i2 = 0; i2 < this.mConnections.size(); i2++) {
                try {
                    AppServiceConnection appServiceConnection =
                            (AppServiceConnection) this.mConnections.get(i2);
                    printWriter.print("conn,");
                    appServiceConnection.mFinder.getClass();
                    printWriter.print("[Default SMS app]");
                    printWriter.print(",");
                    printWriter.print(appServiceConnection.mUserId);
                    printWriter.print(",");
                    printWriter.print(appServiceConnection.mComponentName.getPackageName());
                    printWriter.print(",");
                    printWriter.print(appServiceConnection.mComponentName.getClassName());
                    printWriter.print(",");
                    synchronized (appServiceConnection.mLock) {
                        z = appServiceConnection.mBound;
                    }
                    printWriter.print(z ? "bound" : "not-bound");
                    printWriter.print(",");
                    synchronized (appServiceConnection.mLock) {
                        z2 = appServiceConnection.mIsConnected;
                    }
                    printWriter.print(z2 ? "connected" : "not-connected");
                    printWriter.print(",#con=");
                    synchronized (appServiceConnection.mLock) {
                        i = appServiceConnection.mNumConnected;
                    }
                    printWriter.print(i);
                    printWriter.print(",#dis=");
                    printWriter.print(appServiceConnection.getNumDisconnected());
                    printWriter.print(",#died=");
                    printWriter.print(appServiceConnection.getNumBindingDied());
                    printWriter.print(",backoff=");
                    printWriter.print(appServiceConnection.getNextBackoffMs());
                    printWriter.println();
                } catch (Throwable th) {
                    throw th;
                }
            }
            forAllAppsLocked(new AppBindingService$$ExternalSyntheticLambda1(1, printWriter));
        }
    }

    public final void forAllAppsLocked(Consumer consumer) {
        for (int i = 0; i < this.mApps.size(); i++) {
            consumer.accept((CarrierMessagingClientServiceFinder) this.mApps.get(i));
        }
    }

    public final void refreshConstants() {
        Injector injector = this.mInjector;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        injector.getClass();
        String string = Settings.Global.getString(contentResolver, "app_binding_constants");
        synchronized (this.mLock) {
            try {
                if (TextUtils.equals(this.mConstants.sourceSettings, string)) {
                    return;
                }
                Slog.i("AppBindingService", "Updating constants with: " + string);
                this.mConstants = new AppBindingConstants(string);
                for (int i = 0; i < this.mRunningUsers.size(); i++) {
                    if (this.mRunningUsers.valueAt(i)) {
                        int keyAt = this.mRunningUsers.keyAt(i);
                        unbindServicesLocked(keyAt, null);
                        bindServicesLocked(keyAt, null);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unbindServicesLocked(
            int i, CarrierMessagingClientServiceFinder carrierMessagingClientServiceFinder) {
        for (int size = this.mConnections.size() - 1; size >= 0; size--) {
            AppServiceConnection appServiceConnection =
                    (AppServiceConnection) this.mConnections.get(size);
            if (appServiceConnection.mUserId == i
                    && (carrierMessagingClientServiceFinder == null
                            || appServiceConnection.mFinder
                                    == carrierMessagingClientServiceFinder)) {
                this.mConnections.remove(size);
                appServiceConnection.unbind();
            }
        }
    }
}
