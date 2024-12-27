package com.android.systemui.qs.external;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.service.quicksettings.IQSService;
import android.service.quicksettings.Tile;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseArrayMap;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.StatusBarIcon;
import com.android.systemui.Flags;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.external.TileLifecycleManager;
import com.android.systemui.qs.pipeline.data.repository.CustomTileAddedRepository;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.StatusBarIconHolder;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.phone.ui.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.inject.Provider;

public final class TileServices extends IQSService.Stub {
    public static final boolean DEBUG = !DeviceType.isShipBuild();
    public static final TileServices$$ExternalSyntheticLambda2 SERVICE_SORT = new TileServices$$ExternalSyntheticLambda2();
    public final DelayableExecutor mBackgroundExecutor;
    public final AnonymousClass5 mBootCompleteReceiver;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Context mContext;
    public final CustomTileAddedRepository mCustomTileAddedRepository;
    public final Provider mHandlerProvider;
    public final QSHost mHost;
    public boolean mIsBootCompleted;
    public final KeyguardStateController mKeyguardStateController;
    public final Handler mMainHandler;
    public final PanelInteractor mPanelInteractor;
    public final AnonymousClass2 mRequestListeningCallback;
    public final StatusBarIconController mStatusBarIconController;
    public final TileLifecycleManager.Factory mTileLifecycleManagerFactory;
    public final AnonymousClass3 mTileUpdateReceiver;
    public final AnonymousClass4 mUninstallReceiver;
    public boolean mUninstallReceiverRegistered;
    public final UserTracker mUserTracker;
    public final ArrayMap mServices = new ArrayMap();
    public final SparseArrayMap mTiles = new SparseArrayMap();
    public final ArrayMap mTokenMap = new ArrayMap();
    public final int mMaxBound = 5;

    /* renamed from: com.android.systemui.qs.external.TileServices$2, reason: invalid class name */
    public final class AnonymousClass2 implements CommandQueue.Callbacks {
        public AnonymousClass2() {
        }

        @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
        public final void requestTileServiceListeningState(ComponentName componentName) {
            boolean z = TileServices.DEBUG;
            TileServices tileServices = TileServices.this;
            tileServices.getClass();
            tileServices.mMainHandler.post(new TileServices$$ExternalSyntheticLambda0(this, componentName));
        }
    }

    /* renamed from: -$$Nest$mrequestListening, reason: not valid java name */
    public static void m2072$$Nest$mrequestListening(TileServices tileServices, ComponentName componentName) {
        if (DEBUG) {
            tileServices.getClass();
            Log.d("TileServices", "requestListening " + componentName);
        }
        synchronized (tileServices.mServices) {
            try {
                int userId = ((UserTrackerImpl) tileServices.mUserTracker).getUserId();
                CustomTileInterface tileForUserAndComponent = tileServices.getTileForUserAndComponent(userId, componentName);
                if (tileForUserAndComponent == null) {
                    Log.d("TileServices", "Couldn't find tile for " + componentName + "(" + userId + ")");
                    return;
                }
                TileServiceManager tileServiceManager = (TileServiceManager) tileServices.mServices.get(tileForUserAndComponent);
                if (tileServiceManager == null) {
                    Log.e("TileServices", "No TileServiceManager found in requestListening for tile " + tileForUserAndComponent.getTileSpec());
                } else if (tileServiceManager.isActiveTile()) {
                    if (tileForUserAndComponent.isInitialized()) {
                        tileServiceManager.setBindRequested(true);
                        Flags.FEATURE_FLAGS.getClass();
                        tileServiceManager.mListeningFromRequest.set(true);
                        tileServiceManager.mStateManager.onStartListening();
                    } else {
                        Log.w("TileServices", "requestListening :: customtile is not initialized");
                    }
                }
            } finally {
            }
        }
    }

    public TileServices(QSHost qSHost, Provider provider, BroadcastDispatcher broadcastDispatcher, UserTracker userTracker, KeyguardStateController keyguardStateController, CommandQueue commandQueue, StatusBarIconController statusBarIconController, PanelInteractor panelInteractor, TileLifecycleManager.Factory factory, CustomTileAddedRepository customTileAddedRepository, DelayableExecutor delayableExecutor) {
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.external.TileServices.3
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                Log.d("TileServices", "mTileUpdateReceiver : " + intent.getAction());
                TileServices.this.mMainHandler.post(new Runnable() { // from class: com.android.systemui.qs.external.TileServices.3.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ArrayList arrayList;
                        synchronized (TileServices.this.mServices) {
                            arrayList = new ArrayList(TileServices.this.mServices.values());
                        }
                        int size = arrayList.size();
                        for (int i = 0; i < size; i++) {
                            TileServiceManager tileServiceManager = (TileServiceManager) arrayList.get(i);
                            if (tileServiceManager.mIsSecCustomTile) {
                                tileServiceManager.setBindRequested(true);
                                try {
                                    tileServiceManager.mStateManager.onStartListening();
                                } catch (RemoteException unused) {
                                }
                            }
                        }
                    }
                });
            }
        };
        this.mUninstallReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.external.TileServices.4
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                List<ResolveInfo> list;
                Log.d("TileServices", "mUninstallReceiver onReceive = " + intent);
                String encodedSchemeSpecificPart = intent.getData().getEncodedSchemeSpecificPart();
                boolean booleanExtra = intent.getBooleanExtra("android.intent.extra.REPLACING", false);
                if (booleanExtra) {
                    Intent intent2 = new Intent("android.service.quicksettings.action.QS_TILE");
                    intent2.setPackage(encodedSchemeSpecificPart);
                    list = context.getPackageManager().queryIntentServicesAsUser(intent2, 0, ((UserTrackerImpl) TileServices.this.mUserTracker).getUserId());
                } else {
                    list = null;
                }
                Iterator it = TileServices.this.mServices.values().iterator();
                while (it.hasNext()) {
                    ComponentName component = ((TileServiceManager) it.next()).mStateManager.mIntent.getComponent();
                    if (Objects.equals(encodedSchemeSpecificPart, component.getPackageName())) {
                        Log.d("TileServices", "component = " + component + ", pkgTileServices = " + list);
                        if (booleanExtra) {
                            for (ResolveInfo resolveInfo : list) {
                                if (Objects.equals(resolveInfo.serviceInfo.packageName, component.getPackageName()) && Objects.equals(resolveInfo.serviceInfo.name, component.getClassName())) {
                                    NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m("mUninstallReceiver shouldBeContinue = ", component, "TileServices");
                                    CustomTileInterface tileForUserAndComponent = TileServices.this.getTileForUserAndComponent(((UserTrackerImpl) TileServices.this.mUserTracker).getUserId(), component);
                                    if (tileForUserAndComponent != null) {
                                        tileForUserAndComponent.refreshMetaInfo();
                                        if (tileForUserAndComponent.isSecActiveTile()) {
                                            TileServices.m2072$$Nest$mrequestListening(TileServices.this, component);
                                        }
                                    }
                                }
                            }
                        }
                        TileServices.this.mHost.removeTile(CustomTile.toSpec(component));
                    }
                }
            }
        };
        BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() { // from class: com.android.systemui.qs.external.TileServices.5
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                Log.d("TileServices", "mBootCompleteReceiver : " + intent.getAction());
                TileServices.this.mMainHandler.post(new Runnable() { // from class: com.android.systemui.qs.external.TileServices.5.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        final ArrayList arrayList = new ArrayList();
                        synchronized (TileServices.this.mServices) {
                            TileServices.this.mTiles.forEach(new Consumer() { // from class: com.android.systemui.qs.external.TileServices$5$1$$ExternalSyntheticLambda0
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    arrayList.add((CustomTileInterface) obj);
                                }
                            });
                        }
                        int size = arrayList.size();
                        for (int i = 0; i < size; i++) {
                            ((CustomTileInterface) arrayList.get(i)).lazyInitialize();
                        }
                        TileServices.this.mIsBootCompleted = true;
                    }
                });
            }
        };
        this.mHost = qSHost;
        this.mKeyguardStateController = keyguardStateController;
        this.mContext = qSHost.getContext();
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mHandlerProvider = provider;
        this.mMainHandler = (Handler) provider.get();
        this.mUserTracker = userTracker;
        this.mStatusBarIconController = statusBarIconController;
        commandQueue.addCallback((CommandQueue.Callbacks) anonymousClass2);
        this.mPanelInteractor = panelInteractor;
        this.mTileLifecycleManagerFactory = factory;
        this.mCustomTileAddedRepository = customTileAddedRepository;
        this.mBackgroundExecutor = delayableExecutor;
        broadcastDispatcher.registerReceiver(new IntentFilter("android.intent.action.LOCALE_CHANGED"), broadcastReceiver);
        boolean equals = "1".equals(SystemProperties.get("sys.boot_completed"));
        this.mIsBootCompleted = equals;
        if (equals) {
            return;
        }
        broadcastDispatcher.registerReceiver(new IntentFilter("com.samsung.intent.action.LAZY_BOOT_COMPLETE"), broadcastReceiver2);
    }

    public final void freeService(CustomTileInterface customTileInterface, TileServiceManager tileServiceManager) {
        synchronized (this.mServices) {
            try {
                if (DEBUG) {
                    Log.d("TileServices", "freeService" + customTileInterface);
                }
                tileServiceManager.setBindAllowed(false);
                tileServiceManager.handleDestroy();
                this.mServices.remove(customTileInterface);
                this.mTokenMap.remove(tileServiceManager.mStateManager.mToken);
                this.mTiles.delete(customTileInterface.getUser(), customTileInterface.getComponent());
                this.mMainHandler.post(new TileServices$$ExternalSyntheticLambda0(this, customTileInterface.getComponent().getClassName()));
                if (this.mServices.size() == 0 && this.mUninstallReceiverRegistered) {
                    this.mBroadcastDispatcher.unregisterReceiver(this.mUninstallReceiver);
                    this.mUninstallReceiverRegistered = false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Tile getTile(IBinder iBinder) {
        CustomTileInterface tileForToken = getTileForToken(iBinder);
        if (tileForToken != null) {
            verifyCaller(tileForToken);
            return tileForToken.getQsTile();
        }
        StringBuilder sb = new StringBuilder("Tile for token ");
        sb.append(iBinder);
        sb.append("not found. Tiles in map: ");
        final StringBuilder sb2 = new StringBuilder("[");
        synchronized (this.mServices) {
            this.mTokenMap.forEach(new BiConsumer() { // from class: com.android.systemui.qs.external.TileServices$$ExternalSyntheticLambda1
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    StringBuilder sb3 = sb2;
                    CustomTileInterface customTileInterface = (CustomTileInterface) obj2;
                    sb3.append(((IBinder) obj).toString());
                    sb3.append(":");
                    sb3.append(customTileInterface.getComponent().flattenToShortString());
                    sb3.append(":");
                    sb3.append(customTileInterface.getUser());
                    sb3.append(",");
                }
            });
        }
        sb2.append("]");
        sb.append(sb2.toString());
        Log.e("TileServices", sb.toString());
        return null;
    }

    public final CustomTileInterface getTileForToken(IBinder iBinder) {
        CustomTileInterface customTileInterface;
        synchronized (this.mServices) {
            customTileInterface = (CustomTileInterface) this.mTokenMap.get(iBinder);
        }
        return customTileInterface;
    }

    public final CustomTileInterface getTileForUserAndComponent(int i, ComponentName componentName) {
        CustomTileInterface customTileInterface;
        synchronized (this.mServices) {
            customTileInterface = (CustomTileInterface) this.mTiles.get(i, componentName);
        }
        return customTileInterface;
    }

    public final TileServiceManager getTileWrapper(CustomTileInterface customTileInterface) {
        TileServiceManager tileServiceManager;
        ComponentName component = customTileInterface.getComponent();
        int user = customTileInterface.getUser();
        TileServiceManager tileServiceManager2 = new TileServiceManager(this, (Handler) this.mHandlerProvider.get(), component, this.mUserTracker, this.mTileLifecycleManagerFactory, this.mCustomTileAddedRepository);
        if (DEBUG) {
            NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m("getTileWrapper ", component, "TileServices");
        }
        synchronized (this.mServices) {
            try {
                CustomTileInterface customTileInterface2 = (CustomTileInterface) this.mTiles.get(user, component);
                if (customTileInterface2 != null && (tileServiceManager = (TileServiceManager) this.mServices.get(customTileInterface2)) != null) {
                    tileServiceManager.setBindAllowed(false);
                    tileServiceManager.handleDestroy();
                    this.mServices.remove(customTileInterface2);
                    this.mTokenMap.remove(tileServiceManager.mStateManager.mToken);
                }
                this.mServices.put(customTileInterface, tileServiceManager2);
                this.mTiles.add(user, component, customTileInterface);
                this.mTokenMap.put(tileServiceManager2.mStateManager.mToken, customTileInterface);
                if (!this.mUninstallReceiverRegistered) {
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
                    intentFilter.addDataScheme("package");
                    this.mBroadcastDispatcher.registerReceiver(this.mUninstallReceiver, intentFilter, null, ((UserTrackerImpl) this.mUserTracker).getUserHandle());
                    this.mUninstallReceiverRegistered = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        tileServiceManager2.mStarted = true;
        TileLifecycleManager tileLifecycleManager = tileServiceManager2.mStateManager;
        ComponentName component2 = tileLifecycleManager.mIntent.getComponent();
        int identifier = tileLifecycleManager.mUser.getIdentifier();
        CustomTileAddedRepository customTileAddedRepository = tileServiceManager2.mCustomTileAddedRepository;
        if (!customTileAddedRepository.isTileAdded(identifier, component2)) {
            customTileAddedRepository.setTileAdded(component2, true, identifier);
            tileLifecycleManager.onTileAdded();
            tileLifecycleManager.mExecutor.execute(new TileLifecycleManager$$ExternalSyntheticLambda0(tileLifecycleManager, 3));
        }
        return tileServiceManager2;
    }

    public final boolean isLocked() {
        return ((KeyguardStateControllerImpl) this.mKeyguardStateController).mShowing;
    }

    public final boolean isSecure() {
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        return ((KeyguardStateControllerImpl) keyguardStateController).mSecure && ((KeyguardStateControllerImpl) keyguardStateController).mShowing;
    }

    public final void onDialogHidden(IBinder iBinder) {
        CustomTileInterface tileForToken = getTileForToken(iBinder);
        if (tileForToken != null) {
            verifyCaller(tileForToken);
            TileServiceManager tileServiceManager = (TileServiceManager) this.mServices.get(tileForToken);
            Objects.requireNonNull(tileServiceManager);
            tileServiceManager.mShowingDialog = false;
            tileForToken.onDialogHidden();
        }
    }

    public final void onShowDialog(IBinder iBinder) {
        CustomTileInterface tileForToken = getTileForToken(iBinder);
        if (tileForToken != null) {
            verifyCaller(tileForToken);
            tileForToken.onDialogShown();
            this.mPanelInteractor.collapsePanels();
            TileServiceManager tileServiceManager = (TileServiceManager) this.mServices.get(tileForToken);
            Objects.requireNonNull(tileServiceManager);
            tileServiceManager.mShowingDialog = true;
        }
    }

    public final void onStartActivity(IBinder iBinder) {
        CustomTileInterface tileForToken = getTileForToken(iBinder);
        if (tileForToken != null) {
            verifyCaller(tileForToken);
            this.mPanelInteractor.forceCollapsePanels();
        }
    }

    public final void onStartSuccessful(IBinder iBinder) {
        CustomTileInterface tileForToken = getTileForToken(iBinder);
        if (tileForToken != null) {
            verifyCaller(tileForToken);
            if (DEBUG) {
                Log.d("TileServices", "onStartSuccessful " + iBinder);
            }
            synchronized (this.mServices) {
                TileServiceManager tileServiceManager = (TileServiceManager) this.mServices.get(tileForToken);
                if (tileServiceManager != null && tileServiceManager.mStarted) {
                    tileServiceManager.mPendingBind = false;
                    tileForToken.refreshState();
                    return;
                }
                Log.e("TileServices", "TileServiceManager not started for " + tileForToken.getComponent(), new IllegalStateException());
            }
        }
    }

    public final void recalculateBindAllowance() {
        ArrayList arrayList;
        synchronized (this.mServices) {
            arrayList = new ArrayList(this.mServices.values());
        }
        int size = arrayList.size();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < size; i++) {
            if (((TileServiceManager) arrayList.get(i)).mIsSecCustomTile) {
                ((TileServiceManager) arrayList.get(i)).setBindAllowed(true);
            } else {
                arrayList2.add((TileServiceManager) arrayList.get(i));
            }
        }
        int size2 = arrayList2.size();
        if (size2 > this.mMaxBound) {
            long currentTimeMillis = System.currentTimeMillis();
            for (int i2 = 0; i2 < size2; i2++) {
                TileServiceManager tileServiceManager = (TileServiceManager) arrayList2.get(i2);
                if (tileServiceManager.mStateManager.hasPendingClick()) {
                    tileServiceManager.mPriority = Integer.MAX_VALUE;
                } else if (tileServiceManager.mWaitingUnlock) {
                    tileServiceManager.mPriority = Integer.MAX_VALUE;
                    Log.w("TileServiceManager", "calculateBindPriority : mWaitingUnlock");
                } else if (tileServiceManager.mShowingDialog) {
                    tileServiceManager.mPriority = 2147483646;
                } else if (tileServiceManager.mJustBound) {
                    tileServiceManager.mPriority = 2147483645;
                } else if (tileServiceManager.mBindRequested) {
                    long j = currentTimeMillis - tileServiceManager.mLastUpdate;
                    if (j > 2147483644) {
                        tileServiceManager.mPriority = 2147483644;
                    } else {
                        tileServiceManager.mPriority = (int) j;
                    }
                } else {
                    tileServiceManager.mPriority = Integer.MIN_VALUE;
                }
            }
            Collections.sort(arrayList2, SERVICE_SORT);
        }
        int i3 = 0;
        while (i3 < this.mMaxBound && i3 < size2) {
            ((TileServiceManager) arrayList2.get(i3)).setBindAllowed(true);
            i3++;
        }
        while (i3 < size2) {
            ((TileServiceManager) arrayList2.get(i3)).setBindAllowed(false);
            i3++;
        }
    }

    public final void refreshDetailInfo(ComponentName componentName) {
        CustomTileInterface tileForUserAndComponent = getTileForUserAndComponent(((UserTrackerImpl) this.mUserTracker).getUserId(), componentName);
        if (tileForUserAndComponent == null) {
            NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m("Couldn't find tile for ", componentName, "TileServices");
        } else {
            tileForUserAndComponent.refreshDetailInfo();
        }
    }

    public final void semFireToggleStateChanged(IBinder iBinder, boolean z, boolean z2) {
        CustomTileInterface tileForToken = getTileForToken(iBinder);
        Log.d("TileServices", "semFireToggleStateChanged : customTile =  " + tileForToken);
        Log.d("TileServices", "semFireToggleStateChanged : state =  " + z + ", enabled = " + z2);
        if (tileForToken != null) {
            verifyCaller(tileForToken);
            tileForToken.setToggleEnabledState(z2);
            tileForToken.fireToggleStateChanged(z);
        }
    }

    public final void semUpdateDetailView(IBinder iBinder) {
        CustomTileInterface tileForToken = getTileForToken(iBinder);
        Log.d("TileServices", "semUpdateDetailView : customTile =  " + tileForToken);
        if (tileForToken != null) {
            verifyCaller(tileForToken);
        }
    }

    public final void startActivity(IBinder iBinder, PendingIntent pendingIntent) {
        startActivity(getTileForToken(iBinder), pendingIntent);
    }

    public final void startUnlockAndRun(IBinder iBinder) {
        CustomTileInterface tileForToken = getTileForToken(iBinder);
        if (tileForToken != null) {
            verifyCaller(tileForToken);
            tileForToken.startUnlockAndRun();
            ((TileServiceManager) this.mServices.get(tileForToken)).setWaitingUnlockState(true);
        }
    }

    public final void updateQsTile(Tile tile, IBinder iBinder) {
        CustomTileInterface tileForToken = getTileForToken(iBinder);
        if (tileForToken != null) {
            int verifyCaller = verifyCaller(tileForToken);
            synchronized (this.mServices) {
                TileServiceManager tileServiceManager = (TileServiceManager) this.mServices.get(tileForToken);
                if (tileServiceManager != null && tileServiceManager.mStarted) {
                    tileServiceManager.mPendingBind = false;
                    tileServiceManager.mLastUpdate = System.currentTimeMillis();
                    if (tileServiceManager.mBound && tileServiceManager.isActiveTile() && !tileServiceManager.mIsTileListening) {
                        tileServiceManager.mStateManager.onStopListening();
                        tileServiceManager.setBindRequested(false);
                    }
                    tileServiceManager.mServices.recalculateBindAllowance();
                    tileForToken.updateTileState(tile, verifyCaller);
                    tileForToken.refreshState();
                    return;
                }
                Log.e("TileServices", "TileServiceManager not started for " + tileForToken.getComponent(), new IllegalStateException());
            }
        }
    }

    public final void updateStatusIcon(IBinder iBinder, Icon icon, String str) {
        CustomTileInterface tileForToken = getTileForToken(iBinder);
        if (tileForToken != null) {
            verifyCaller(tileForToken);
            try {
                ComponentName component = tileForToken.getComponent();
                String packageName = component.getPackageName();
                UserHandle callingUserHandle = IQSService.Stub.getCallingUserHandle();
                if (this.mContext.getPackageManager().getPackageInfoAsUser(packageName, 0, callingUserHandle.getIdentifier()).applicationInfo.isSystemApp()) {
                    final StatusBarIcon statusBarIcon = icon != null ? new StatusBarIcon(callingUserHandle, packageName, icon, 0, 0, str, StatusBarIcon.Type.SystemIcon) : null;
                    final String className = component.getClassName();
                    this.mMainHandler.post(new Runnable() { // from class: com.android.systemui.qs.external.TileServices.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            StatusBarIconController statusBarIconController = TileServices.this.mStatusBarIconController;
                            String str2 = className;
                            StatusBarIcon statusBarIcon2 = statusBarIcon;
                            StatusBarIconControllerImpl statusBarIconControllerImpl = (StatusBarIconControllerImpl) statusBarIconController;
                            if (statusBarIcon2 == null) {
                                statusBarIconControllerImpl.removeAllIconsForSlot(str2);
                                return;
                            }
                            statusBarIconControllerImpl.getClass();
                            StatusBarIconHolder.Companion.getClass();
                            StatusBarIconHolder statusBarIconHolder = new StatusBarIconHolder(null);
                            statusBarIconHolder.icon = statusBarIcon2;
                            statusBarIconControllerImpl.setIcon(str2, statusBarIconHolder);
                        }
                    });
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
    }

    public final int verifyCaller(CustomTileInterface customTileInterface) {
        try {
            int packageUidAsUser = this.mContext.getPackageManager().getPackageUidAsUser(customTileInterface.getComponent().getPackageName(), Binder.getCallingUserHandle().getIdentifier());
            if (Binder.getCallingUid() == packageUidAsUser) {
                return packageUidAsUser;
            }
            throw new SecurityException("Component outside caller's uid");
        } catch (PackageManager.NameNotFoundException e) {
            throw new SecurityException(e);
        }
    }

    public void startActivity(CustomTileInterface customTileInterface, PendingIntent pendingIntent) {
        if (customTileInterface != null) {
            verifyCaller(customTileInterface);
            customTileInterface.startActivityAndCollapse(pendingIntent);
        }
    }
}
