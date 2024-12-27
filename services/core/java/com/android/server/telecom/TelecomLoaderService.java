package com.android.server.telecom;

import android.app.role.OnRoleHoldersChangedListener;
import android.app.role.RoleManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.telecom.DefaultDialerManager;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.SubscriptionManager;
import android.util.IntArray;
import android.util.Slog;

import com.android.internal.telecom.ITelecomLoader;
import com.android.internal.telecom.ITelecomService;
import com.android.internal.telephony.SmsApplication;
import com.android.server.DeviceIdleInternal;
import com.android.server.LocalServices;
import com.android.server.SystemService;
import com.android.server.pm.UserManagerService;
import com.android.server.pm.permission.DefaultPermissionGrantPolicy;
import com.android.server.pm.permission.LegacyPermissionManagerInternal$PackagesProvider;
import com.android.server.pm.permission.LegacyPermissionManagerService;

import com.samsung.android.telecom.SamsungTelecomServiceConnection;

import java.util.ArrayList;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TelecomLoaderService extends SystemService {
    public static final ComponentName SERVICE_COMPONENT =
            new ComponentName(
                    "com.android.server.telecom",
                    "com.android.server.telecom.components.TelecomService");
    public final Context mContext;
    public IntArray mDefaultSimCallManagerRequests;
    public final Object mLock;
    public final SamsungTelecomServiceConnection mSamsungTelecomServiceConnection;
    public TelecomServiceConnection mServiceConnection;
    public InternalServiceRepository mServiceRepo;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TelecomServiceConnection implements ServiceConnection {
        public TelecomServiceConnection() {}

        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            PhoneAccountHandle simCallManager;
            try {
                ITelecomService createTelecomService =
                        ITelecomLoader.Stub.asInterface(iBinder)
                                .createTelecomService(TelecomLoaderService.this.mServiceRepo);
                SmsApplication.getDefaultMmsApplication(TelecomLoaderService.this.mContext, false);
                ServiceManager.addService("telecom", createTelecomService.asBinder());
                synchronized (TelecomLoaderService.this.mLock) {
                    try {
                        LegacyPermissionManagerService.Internal internal =
                                (LegacyPermissionManagerService.Internal)
                                        LocalServices.getService(
                                                LegacyPermissionManagerService.Internal.class);
                        TelecomLoaderService telecomLoaderService = TelecomLoaderService.this;
                        if (telecomLoaderService.mDefaultSimCallManagerRequests != null
                                && (simCallManager =
                                                ((TelecomManager)
                                                                telecomLoaderService.mContext
                                                                        .getSystemService(
                                                                                "telecom"))
                                                        .getSimCallManager())
                                        != null) {
                            int size =
                                    TelecomLoaderService.this.mDefaultSimCallManagerRequests.size();
                            String packageName = simCallManager.getComponentName().getPackageName();
                            for (int i = size - 1; i >= 0; i--) {
                                int i2 =
                                        TelecomLoaderService.this.mDefaultSimCallManagerRequests
                                                .get(i);
                                TelecomLoaderService.this.mDefaultSimCallManagerRequests.remove(i);
                                DefaultPermissionGrantPolicy defaultPermissionGrantPolicy =
                                        LegacyPermissionManagerService.this
                                                .mDefaultPermissionGrantPolicy;
                                defaultPermissionGrantPolicy
                                        .grantDefaultPermissionsToDefaultSimCallManager(
                                                defaultPermissionGrantPolicy.NO_PM_CACHE,
                                                packageName,
                                                i2);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } catch (RemoteException unused) {
                Slog.w("TelecomLoaderService", "Failed linking to death.");
            }
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            TelecomLoaderService.this.connectToTelecom();
        }
    }

    public TelecomLoaderService(Context context) {
        super(context);
        Object obj = new Object();
        this.mLock = obj;
        this.mContext = context;
        LegacyPermissionManagerService.Internal internal =
                (LegacyPermissionManagerService.Internal)
                        LocalServices.getService(LegacyPermissionManagerService.Internal.class);
        final int i = 0;
        LegacyPermissionManagerInternal$PackagesProvider
                legacyPermissionManagerInternal$PackagesProvider =
                        new LegacyPermissionManagerInternal$PackagesProvider(
                                this) { // from class:
                                        // com.android.server.telecom.TelecomLoaderService$$ExternalSyntheticLambda0
                            public final /* synthetic */ TelecomLoaderService f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal$PackagesProvider
                            public final String[] getPackages(int i2) {
                                int i3 = i;
                                TelecomLoaderService telecomLoaderService = this.f$0;
                                switch (i3) {
                                    case 0:
                                        synchronized (telecomLoaderService.mLock) {
                                            try {
                                                if (telecomLoaderService.mServiceConnection
                                                        == null) {
                                                    return null;
                                                }
                                                ComponentName defaultSmsApplication =
                                                        SmsApplication.getDefaultSmsApplication(
                                                                telecomLoaderService.mContext,
                                                                true);
                                                if (defaultSmsApplication != null) {
                                                    return new String[] {
                                                        defaultSmsApplication.getPackageName()
                                                    };
                                                }
                                                return null;
                                            } finally {
                                            }
                                        }
                                    case 1:
                                        synchronized (telecomLoaderService.mLock) {
                                            try {
                                                if (telecomLoaderService.mServiceConnection
                                                        == null) {
                                                    return null;
                                                }
                                                String defaultDialerApplication =
                                                        DefaultDialerManager
                                                                .getDefaultDialerApplication(
                                                                        telecomLoaderService
                                                                                .mContext);
                                                if (defaultDialerApplication != null) {
                                                    return new String[] {defaultDialerApplication};
                                                }
                                                return null;
                                            } finally {
                                            }
                                        }
                                    default:
                                        synchronized (telecomLoaderService.mLock) {
                                            try {
                                                if (telecomLoaderService.mServiceConnection
                                                        == null) {
                                                    if (telecomLoaderService
                                                                    .mDefaultSimCallManagerRequests
                                                            == null) {
                                                        telecomLoaderService
                                                                        .mDefaultSimCallManagerRequests =
                                                                new IntArray();
                                                    }
                                                    telecomLoaderService
                                                            .mDefaultSimCallManagerRequests.add(i2);
                                                    return null;
                                                }
                                                SubscriptionManager subscriptionManager =
                                                        (SubscriptionManager)
                                                                telecomLoaderService.mContext
                                                                        .getSystemService(
                                                                                SubscriptionManager
                                                                                        .class);
                                                if (subscriptionManager == null) {
                                                    return null;
                                                }
                                                TelecomManager telecomManager =
                                                        (TelecomManager)
                                                                telecomLoaderService.mContext
                                                                        .getSystemService(
                                                                                "telecom");
                                                ArrayList arrayList = new ArrayList();
                                                for (int i4 :
                                                        subscriptionManager
                                                                .getActiveSubscriptionIdList()) {
                                                    PhoneAccountHandle
                                                            simCallManagerForSubscription =
                                                                    telecomManager
                                                                            .getSimCallManagerForSubscription(
                                                                                    i4);
                                                    if (simCallManagerForSubscription != null) {
                                                        arrayList.add(
                                                                simCallManagerForSubscription
                                                                        .getComponentName()
                                                                        .getPackageName());
                                                    }
                                                }
                                                return (String[]) arrayList.toArray(new String[0]);
                                            } finally {
                                            }
                                        }
                                }
                            }
                        };
        DefaultPermissionGrantPolicy defaultPermissionGrantPolicy =
                LegacyPermissionManagerService.this.mDefaultPermissionGrantPolicy;
        synchronized (defaultPermissionGrantPolicy.mLock) {
            defaultPermissionGrantPolicy.mSmsAppPackagesProvider =
                    legacyPermissionManagerInternal$PackagesProvider;
        }
        final int i2 = 1;
        LegacyPermissionManagerInternal$PackagesProvider
                legacyPermissionManagerInternal$PackagesProvider2 =
                        new LegacyPermissionManagerInternal$PackagesProvider(
                                this) { // from class:
                                        // com.android.server.telecom.TelecomLoaderService$$ExternalSyntheticLambda0
                            public final /* synthetic */ TelecomLoaderService f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal$PackagesProvider
                            public final String[] getPackages(int i22) {
                                int i3 = i2;
                                TelecomLoaderService telecomLoaderService = this.f$0;
                                switch (i3) {
                                    case 0:
                                        synchronized (telecomLoaderService.mLock) {
                                            try {
                                                if (telecomLoaderService.mServiceConnection
                                                        == null) {
                                                    return null;
                                                }
                                                ComponentName defaultSmsApplication =
                                                        SmsApplication.getDefaultSmsApplication(
                                                                telecomLoaderService.mContext,
                                                                true);
                                                if (defaultSmsApplication != null) {
                                                    return new String[] {
                                                        defaultSmsApplication.getPackageName()
                                                    };
                                                }
                                                return null;
                                            } finally {
                                            }
                                        }
                                    case 1:
                                        synchronized (telecomLoaderService.mLock) {
                                            try {
                                                if (telecomLoaderService.mServiceConnection
                                                        == null) {
                                                    return null;
                                                }
                                                String defaultDialerApplication =
                                                        DefaultDialerManager
                                                                .getDefaultDialerApplication(
                                                                        telecomLoaderService
                                                                                .mContext);
                                                if (defaultDialerApplication != null) {
                                                    return new String[] {defaultDialerApplication};
                                                }
                                                return null;
                                            } finally {
                                            }
                                        }
                                    default:
                                        synchronized (telecomLoaderService.mLock) {
                                            try {
                                                if (telecomLoaderService.mServiceConnection
                                                        == null) {
                                                    if (telecomLoaderService
                                                                    .mDefaultSimCallManagerRequests
                                                            == null) {
                                                        telecomLoaderService
                                                                        .mDefaultSimCallManagerRequests =
                                                                new IntArray();
                                                    }
                                                    telecomLoaderService
                                                            .mDefaultSimCallManagerRequests.add(
                                                            i22);
                                                    return null;
                                                }
                                                SubscriptionManager subscriptionManager =
                                                        (SubscriptionManager)
                                                                telecomLoaderService.mContext
                                                                        .getSystemService(
                                                                                SubscriptionManager
                                                                                        .class);
                                                if (subscriptionManager == null) {
                                                    return null;
                                                }
                                                TelecomManager telecomManager =
                                                        (TelecomManager)
                                                                telecomLoaderService.mContext
                                                                        .getSystemService(
                                                                                "telecom");
                                                ArrayList arrayList = new ArrayList();
                                                for (int i4 :
                                                        subscriptionManager
                                                                .getActiveSubscriptionIdList()) {
                                                    PhoneAccountHandle
                                                            simCallManagerForSubscription =
                                                                    telecomManager
                                                                            .getSimCallManagerForSubscription(
                                                                                    i4);
                                                    if (simCallManagerForSubscription != null) {
                                                        arrayList.add(
                                                                simCallManagerForSubscription
                                                                        .getComponentName()
                                                                        .getPackageName());
                                                    }
                                                }
                                                return (String[]) arrayList.toArray(new String[0]);
                                            } finally {
                                            }
                                        }
                                }
                            }
                        };
        DefaultPermissionGrantPolicy defaultPermissionGrantPolicy2 =
                LegacyPermissionManagerService.this.mDefaultPermissionGrantPolicy;
        synchronized (defaultPermissionGrantPolicy2.mLock) {
            defaultPermissionGrantPolicy2.mDialerAppPackagesProvider =
                    legacyPermissionManagerInternal$PackagesProvider2;
        }
        final int i3 = 2;
        LegacyPermissionManagerInternal$PackagesProvider
                legacyPermissionManagerInternal$PackagesProvider3 =
                        new LegacyPermissionManagerInternal$PackagesProvider(
                                this) { // from class:
                                        // com.android.server.telecom.TelecomLoaderService$$ExternalSyntheticLambda0
                            public final /* synthetic */ TelecomLoaderService f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // com.android.server.pm.permission.LegacyPermissionManagerInternal$PackagesProvider
                            public final String[] getPackages(int i22) {
                                int i32 = i3;
                                TelecomLoaderService telecomLoaderService = this.f$0;
                                switch (i32) {
                                    case 0:
                                        synchronized (telecomLoaderService.mLock) {
                                            try {
                                                if (telecomLoaderService.mServiceConnection
                                                        == null) {
                                                    return null;
                                                }
                                                ComponentName defaultSmsApplication =
                                                        SmsApplication.getDefaultSmsApplication(
                                                                telecomLoaderService.mContext,
                                                                true);
                                                if (defaultSmsApplication != null) {
                                                    return new String[] {
                                                        defaultSmsApplication.getPackageName()
                                                    };
                                                }
                                                return null;
                                            } finally {
                                            }
                                        }
                                    case 1:
                                        synchronized (telecomLoaderService.mLock) {
                                            try {
                                                if (telecomLoaderService.mServiceConnection
                                                        == null) {
                                                    return null;
                                                }
                                                String defaultDialerApplication =
                                                        DefaultDialerManager
                                                                .getDefaultDialerApplication(
                                                                        telecomLoaderService
                                                                                .mContext);
                                                if (defaultDialerApplication != null) {
                                                    return new String[] {defaultDialerApplication};
                                                }
                                                return null;
                                            } finally {
                                            }
                                        }
                                    default:
                                        synchronized (telecomLoaderService.mLock) {
                                            try {
                                                if (telecomLoaderService.mServiceConnection
                                                        == null) {
                                                    if (telecomLoaderService
                                                                    .mDefaultSimCallManagerRequests
                                                            == null) {
                                                        telecomLoaderService
                                                                        .mDefaultSimCallManagerRequests =
                                                                new IntArray();
                                                    }
                                                    telecomLoaderService
                                                            .mDefaultSimCallManagerRequests.add(
                                                            i22);
                                                    return null;
                                                }
                                                SubscriptionManager subscriptionManager =
                                                        (SubscriptionManager)
                                                                telecomLoaderService.mContext
                                                                        .getSystemService(
                                                                                SubscriptionManager
                                                                                        .class);
                                                if (subscriptionManager == null) {
                                                    return null;
                                                }
                                                TelecomManager telecomManager =
                                                        (TelecomManager)
                                                                telecomLoaderService.mContext
                                                                        .getSystemService(
                                                                                "telecom");
                                                ArrayList arrayList = new ArrayList();
                                                for (int i4 :
                                                        subscriptionManager
                                                                .getActiveSubscriptionIdList()) {
                                                    PhoneAccountHandle
                                                            simCallManagerForSubscription =
                                                                    telecomManager
                                                                            .getSimCallManagerForSubscription(
                                                                                    i4);
                                                    if (simCallManagerForSubscription != null) {
                                                        arrayList.add(
                                                                simCallManagerForSubscription
                                                                        .getComponentName()
                                                                        .getPackageName());
                                                    }
                                                }
                                                return (String[]) arrayList.toArray(new String[0]);
                                            } finally {
                                            }
                                        }
                                }
                            }
                        };
        DefaultPermissionGrantPolicy defaultPermissionGrantPolicy3 =
                LegacyPermissionManagerService.this.mDefaultPermissionGrantPolicy;
        synchronized (defaultPermissionGrantPolicy3.mLock) {
            defaultPermissionGrantPolicy3.mSimCallManagerPackagesProvider =
                    legacyPermissionManagerInternal$PackagesProvider3;
        }
        this.mSamsungTelecomServiceConnection = new SamsungTelecomServiceConnection(context, obj);
    }

    public final void connectToTelecom() {
        synchronized (this.mLock) {
            try {
                TelecomServiceConnection telecomServiceConnection = this.mServiceConnection;
                if (telecomServiceConnection != null) {
                    this.mContext.unbindService(telecomServiceConnection);
                    this.mServiceConnection = null;
                }
                TelecomServiceConnection telecomServiceConnection2 = new TelecomServiceConnection();
                Intent intent = new Intent("com.android.ITelecomService");
                intent.setComponent(SERVICE_COMPONENT);
                if (this.mContext.bindServiceAsUser(
                        intent, telecomServiceConnection2, 67108929, UserHandle.SYSTEM)) {
                    this.mServiceConnection = telecomServiceConnection2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 550) {
            RoleManager roleManager =
                    (RoleManager) this.mContext.getSystemService(RoleManager.class);
            Executor mainExecutor = this.mContext.getMainExecutor();
            OnRoleHoldersChangedListener onRoleHoldersChangedListener =
                    new OnRoleHoldersChangedListener() { // from class:
                                                         // com.android.server.telecom.TelecomLoaderService$$ExternalSyntheticLambda3
                        public final void onRoleHoldersChanged(String str, UserHandle userHandle) {
                            TelecomLoaderService telecomLoaderService = TelecomLoaderService.this;
                            telecomLoaderService.getClass();
                            telecomLoaderService.updateSimCallManagerPermissions(
                                    userHandle.getIdentifier());
                        }
                    };
            UserHandle userHandle = UserHandle.ALL;
            roleManager.addOnRoleHoldersChangedListenerAsUser(
                    mainExecutor, onRoleHoldersChangedListener, userHandle);
            this.mContext.registerReceiverAsUser(
                    new BroadcastReceiver() { // from class:
                                              // com.android.server.telecom.TelecomLoaderService.1
                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            if (intent.getAction()
                                    .equals("android.telephony.action.CARRIER_CONFIG_CHANGED")) {
                                for (int i2 : UserManagerService.getInstance().getUserIds()) {
                                    TelecomLoaderService.this.updateSimCallManagerPermissions(i2);
                                }
                            }
                        }
                    },
                    userHandle,
                    new IntentFilter("android.telephony.action.CARRIER_CONFIG_CHANGED"),
                    null,
                    null);
            this.mServiceRepo =
                    new InternalServiceRepository(
                            (DeviceIdleInternal) getLocalService(DeviceIdleInternal.class));
            connectToTelecom();
            this.mSamsungTelecomServiceConnection.connectToSamsungTelecom();
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {}

    public final void updateSimCallManagerPermissions(int i) {
        LegacyPermissionManagerService.Internal internal =
                (LegacyPermissionManagerService.Internal)
                        LocalServices.getService(LegacyPermissionManagerService.Internal.class);
        PhoneAccountHandle simCallManager =
                ((TelecomManager) this.mContext.getSystemService("telecom")).getSimCallManager(i);
        if (simCallManager != null) {
            Slog.i("TelecomLoaderService", "updating sim call manager permissions for userId:" + i);
            String packageName = simCallManager.getComponentName().getPackageName();
            DefaultPermissionGrantPolicy defaultPermissionGrantPolicy =
                    LegacyPermissionManagerService.this.mDefaultPermissionGrantPolicy;
            defaultPermissionGrantPolicy.grantDefaultPermissionsToDefaultSimCallManager(
                    defaultPermissionGrantPolicy.NO_PM_CACHE, packageName, i);
        }
    }
}
