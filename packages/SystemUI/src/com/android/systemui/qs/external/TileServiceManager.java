package com.android.systemui.qs.external;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SemSystemProperties;
import android.provider.DeviceConfig;
import android.util.Log;
import com.android.systemui.qs.pipeline.data.repository.CustomTileAddedRepository;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public final class TileServiceManager {
    static final String PREFS_FILE = "CustomTileModes";
    public boolean mBindAllowed;
    public boolean mBindRequested;
    public boolean mBound;
    public final CustomTileAddedRepository mCustomTileAddedRepository;
    public final Handler mHandler;
    public final boolean mIsChinaModel;
    public boolean mIsSecCustomTile;
    public boolean mIsTileListening;
    public boolean mJustBound;
    final Runnable mJustBoundOver;
    public long mLastUpdate;
    public final AtomicBoolean mListeningFromRequest;
    public boolean mPendingBind;
    public int mPriority;
    public final TileServices mServices;
    public boolean mShowingDialog;
    public boolean mStarted;
    public final TileLifecycleManager mStateManager;
    public final AnonymousClass1 mStopWaitingUnlock;
    public final AnonymousClass2 mUnbind;
    public final UserTracker mUserTracker;
    public boolean mWaitingUnlock;

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TileServiceManager(com.android.systemui.qs.external.TileServices r7, android.os.Handler r8, android.content.ComponentName r9, com.android.systemui.settings.UserTracker r10, com.android.systemui.qs.external.TileLifecycleManager.Factory r11, com.android.systemui.qs.pipeline.data.repository.CustomTileAddedRepository r12) {
        /*
            r6 = this;
            android.content.Intent r0 = new android.content.Intent
            java.lang.String r1 = "android.service.quicksettings.action.QS_TILE"
            r0.<init>(r1)
            android.content.Intent r9 = r0.setComponent(r9)
            r3 = r10
            com.android.systemui.settings.UserTrackerImpl r3 = (com.android.systemui.settings.UserTrackerImpl) r3
            android.os.UserHandle r10 = r3.getUserHandle()
            com.android.systemui.qs.external.TileLifecycleManager r5 = r11.create(r9, r10)
            r0 = r6
            r1 = r7
            r2 = r8
            r4 = r12
            r0.<init>(r1, r2, r3, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.qs.external.TileServiceManager.<init>(com.android.systemui.qs.external.TileServices, android.os.Handler, android.content.ComponentName, com.android.systemui.settings.UserTracker, com.android.systemui.qs.external.TileLifecycleManager$Factory, com.android.systemui.qs.pipeline.data.repository.CustomTileAddedRepository):void");
    }

    public final void bindService() {
        boolean z = this.mBound;
        TileLifecycleManager tileLifecycleManager = this.mStateManager;
        if (z && !tileLifecycleManager.mPackageReceiverRegistered.get()) {
            Log.e("TileServiceManager", "Service already bound");
            return;
        }
        this.mPendingBind = true;
        this.mBound = true;
        this.mJustBound = true;
        this.mHandler.postDelayed(this.mJustBoundOver, 5000L);
        tileLifecycleManager.mExecutor.execute(new TileLifecycleManager$$ExternalSyntheticLambda12(tileLifecycleManager, true));
    }

    public final void handleDestroy() {
        setBindAllowed(false);
        TileLifecycleManager tileLifecycleManager = this.mStateManager;
        tileLifecycleManager.getClass();
        Log.d("TileLifecycleManager", "handleDestroy");
        if (tileLifecycleManager.mPackageReceiverRegistered.get() || tileLifecycleManager.mUserReceiverRegistered.get()) {
            tileLifecycleManager.stopPackageListening();
        }
        tileLifecycleManager.mIsDestroyed = true;
        tileLifecycleManager.mChangeListener = null;
        TileLifecycleManager$$ExternalSyntheticLambda8 tileLifecycleManager$$ExternalSyntheticLambda8 = tileLifecycleManager.mDeviceConfigChangedListener;
        if (tileLifecycleManager$$ExternalSyntheticLambda8 != null) {
            DeviceConfig.removeOnPropertiesChangedListener(tileLifecycleManager$$ExternalSyntheticLambda8);
        }
    }

    public final boolean hasPendingBind() {
        return this.mPendingBind && this.mStateManager.mHasPendingBind;
    }

    public final boolean isActiveTile() {
        TileLifecycleManager tileLifecycleManager = this.mStateManager;
        tileLifecycleManager.getClass();
        try {
            PackageManagerAdapter packageManagerAdapter = tileLifecycleManager.mPackageManagerAdapter;
            ServiceInfo serviceInfo = packageManagerAdapter.mPackageManager.getServiceInfo(tileLifecycleManager.mIntent.getComponent(), 794752);
            Bundle bundle = serviceInfo.metaData;
            if (bundle == null) {
                return false;
            }
            int i = bundle.getInt("android.service.quicksettings.SEM_ACTIVE_TILE_SUPPORT_SEM_PLATFORM_VER", 0);
            if (i == 0 || i > Build.VERSION.SEM_PLATFORM_INT) {
                if (!serviceInfo.metaData.getBoolean("android.service.quicksettings.ACTIVE_TILE", false)) {
                    return false;
                }
            }
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public final boolean isToggleableTile() {
        TileLifecycleManager tileLifecycleManager = this.mStateManager;
        tileLifecycleManager.getClass();
        try {
            Bundle bundle = tileLifecycleManager.mPackageManagerAdapter.mPackageManager.getServiceInfo(tileLifecycleManager.mIntent.getComponent(), 794752).metaData;
            if (bundle != null) {
                return bundle.getBoolean("android.service.quicksettings.TOGGLEABLE_TILE", false);
            }
            return false;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public final void setBindAllowed(boolean z) {
        boolean z2;
        if (this.mBindAllowed == z) {
            return;
        }
        this.mBindAllowed = z;
        if (z || !(z2 = this.mBound)) {
            if (z && this.mBindRequested && !this.mBound) {
                bindService();
                return;
            }
            return;
        }
        if (!z2) {
            Log.e("TileServiceManager", "Service not bound");
            return;
        }
        this.mBound = false;
        this.mJustBound = false;
        TileLifecycleManager tileLifecycleManager = this.mStateManager;
        tileLifecycleManager.mExecutor.execute(new TileLifecycleManager$$ExternalSyntheticLambda12(tileLifecycleManager, false));
    }

    public final void setBindRequested(boolean z) {
        if (this.mBindRequested == z) {
            return;
        }
        this.mBindRequested = z;
        boolean z2 = this.mBindAllowed;
        AnonymousClass2 anonymousClass2 = this.mUnbind;
        Handler handler = this.mHandler;
        if (z2 && z) {
            handler.removeCallbacks(anonymousClass2);
        }
        if (this.mBindAllowed && this.mBindRequested && (!this.mBound || this.mStateManager.mPackageReceiverRegistered.get())) {
            bindService();
        } else {
            this.mServices.recalculateBindAllowance();
        }
        if (!this.mBound || this.mBindRequested) {
            return;
        }
        if (!hasPendingBind()) {
            handler.postDelayed(anonymousClass2, this.mIsChinaModel ? 10000L : 30000L);
        } else {
            Log.w("TileServiceManager", "unbind early due to pendingBind state");
            handler.postDelayed(anonymousClass2, 3000L);
        }
    }

    public final void setWaitingUnlockState(boolean z) {
        this.mWaitingUnlock = z;
        Log.w("TileServiceManager", "setWaitingUnlockState : waitingUnlock = " + z);
        Handler handler = this.mHandler;
        AnonymousClass1 anonymousClass1 = this.mStopWaitingUnlock;
        handler.removeCallbacks(anonymousClass1);
        AnonymousClass2 anonymousClass2 = this.mUnbind;
        if (z) {
            handler.removeCallbacks(anonymousClass2);
            handler.postDelayed(anonymousClass1, 10000L);
            return;
        }
        this.mServices.recalculateBindAllowance();
        if (!this.mBound || this.mBindRequested) {
            return;
        }
        handler.postDelayed(anonymousClass2, this.mIsChinaModel ? 10000L : 30000L);
    }

    public TileServiceManager(TileServices tileServices, Handler handler, UserTracker userTracker, CustomTileAddedRepository customTileAddedRepository, TileLifecycleManager tileLifecycleManager) {
        this.mPendingBind = true;
        this.mStarted = false;
        this.mListeningFromRequest = new AtomicBoolean(false);
        this.mStopWaitingUnlock = new Runnable() { // from class: com.android.systemui.qs.external.TileServiceManager.1
            @Override // java.lang.Runnable
            public final void run() {
                TileServiceManager.this.mWaitingUnlock = false;
                Log.w("TileServiceManager", "mStopWaitingUnlock : ");
                TileServiceManager.this.mServices.recalculateBindAllowance();
                TileServiceManager tileServiceManager = TileServiceManager.this;
                if (!tileServiceManager.mBound || tileServiceManager.mBindRequested) {
                    return;
                }
                tileServiceManager.mHandler.postDelayed(tileServiceManager.mUnbind, tileServiceManager.mIsChinaModel ? 10000L : 30000L);
            }
        };
        this.mUnbind = new Runnable() { // from class: com.android.systemui.qs.external.TileServiceManager.2
            @Override // java.lang.Runnable
            public final void run() {
                TileServiceManager tileServiceManager = TileServiceManager.this;
                boolean z = tileServiceManager.mBound;
                if (!z || tileServiceManager.mBindRequested) {
                    return;
                }
                if (!z) {
                    Log.e("TileServiceManager", "Service not bound");
                    return;
                }
                tileServiceManager.mBound = false;
                tileServiceManager.mJustBound = false;
                TileLifecycleManager tileLifecycleManager2 = tileServiceManager.mStateManager;
                tileLifecycleManager2.mExecutor.execute(new TileLifecycleManager$$ExternalSyntheticLambda12(tileLifecycleManager2, false));
            }
        };
        this.mJustBoundOver = new Runnable() { // from class: com.android.systemui.qs.external.TileServiceManager.3
            @Override // java.lang.Runnable
            public final void run() {
                TileServiceManager tileServiceManager = TileServiceManager.this;
                tileServiceManager.mJustBound = false;
                tileServiceManager.mServices.recalculateBindAllowance();
            }
        };
        new BroadcastReceiver() { // from class: com.android.systemui.qs.external.TileServiceManager.4
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                if ("android.intent.action.PACKAGE_REMOVED".equals(intent.getAction())) {
                    String encodedSchemeSpecificPart = intent.getData().getEncodedSchemeSpecificPart();
                    ComponentName component = TileServiceManager.this.mStateManager.mIntent.getComponent();
                    if (Objects.equals(encodedSchemeSpecificPart, component.getPackageName())) {
                        if (intent.getBooleanExtra("android.intent.extra.REPLACING", false)) {
                            Intent intent2 = new Intent("android.service.quicksettings.action.QS_TILE");
                            intent2.setPackage(encodedSchemeSpecificPart);
                            for (ResolveInfo resolveInfo : context.getPackageManager().queryIntentServicesAsUser(intent2, 0, ((UserTrackerImpl) TileServiceManager.this.mUserTracker).getUserId())) {
                                if (Objects.equals(resolveInfo.serviceInfo.packageName, component.getPackageName()) && Objects.equals(resolveInfo.serviceInfo.name, component.getClassName())) {
                                    return;
                                }
                            }
                        }
                        TileServiceManager.this.mServices.mHost.removeTile(CustomTile.toSpec(component));
                    }
                }
            }
        };
        this.mServices = tileServices;
        this.mHandler = handler;
        this.mStateManager = tileLifecycleManager;
        this.mUserTracker = userTracker;
        this.mCustomTileAddedRepository = customTileAddedRepository;
        this.mIsChinaModel = "CHINA".equalsIgnoreCase(SemSystemProperties.getCountryCode());
    }
}
