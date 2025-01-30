package com.android.systemui.p016qs.external;

import android.app.compat.CompatChanges;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.quicksettings.IQSService;
import android.service.quicksettings.IQSTileService;
import android.util.ArraySet;
import android.util.Log;
import android.widget.RemoteViews;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.p016qs.tileimpl.SQSTileImpl;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TileLifecycleManager extends BroadcastReceiver implements IQSTileService, ServiceConnection, IBinder.DeathRecipient {
    public final int mBindRetryDelay;
    public int mBindTryCount;
    public final AtomicBoolean mBound;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public TileChangeListener mChangeListener;
    public IBinder mClickBinder;
    public final Context mContext;
    public final DelayableExecutor mExecutor;
    public final Handler mHandler;
    public boolean mHasPendingBind;
    public final Intent mIntent;
    public final AtomicBoolean mIsBound;
    public boolean mIsDestroyed;
    public boolean mListening;
    public final PackageManagerAdapter mPackageManagerAdapter;
    public final AtomicBoolean mPackageReceiverRegistered;
    public final Set mQueuedMessages;
    public final TileServices mServices;
    public boolean mToggleState;
    public final IBinder mToken;
    public final AtomicBoolean mUnbindImmediate;
    public final UserHandle mUser;
    public final AtomicBoolean mUserReceiverRegistered;
    public QSTileServiceWrapper mWrapper;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Factory {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface TileChangeListener {
    }

    public TileLifecycleManager(Handler handler, Context context, IQSService iQSService, PackageManagerAdapter packageManagerAdapter, BroadcastDispatcher broadcastDispatcher, Intent intent, UserHandle userHandle, DelayableExecutor delayableExecutor) {
        Binder binder = new Binder();
        this.mToken = binder;
        this.mQueuedMessages = new ArraySet();
        this.mBindRetryDelay = 1000;
        this.mBound = new AtomicBoolean(false);
        this.mPackageReceiverRegistered = new AtomicBoolean(false);
        this.mUserReceiverRegistered = new AtomicBoolean(false);
        this.mUnbindImmediate = new AtomicBoolean(false);
        this.mIsBound = new AtomicBoolean(false);
        this.mIsDestroyed = false;
        this.mContext = context;
        this.mHandler = handler;
        this.mIntent = intent;
        this.mServices = (TileServices) iQSService;
        intent.putExtra("service", iQSService.asBinder());
        intent.putExtra("token", binder);
        this.mUser = userHandle;
        this.mExecutor = delayableExecutor;
        this.mPackageManagerAdapter = packageManagerAdapter;
        this.mBroadcastDispatcher = broadcastDispatcher;
        Log.d("TileLifecycleManager", "Creating " + intent + " " + userHandle);
    }

    public final IBinder asBinder() {
        QSTileServiceWrapper qSTileServiceWrapper = this.mWrapper;
        if (qSTileServiceWrapper != null) {
            return qSTileServiceWrapper.mService.asBinder();
        }
        return null;
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        Log.d("TileLifecycleManager", "binderDeath");
        handleDeath();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0071 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean checkComponentState() {
        boolean z;
        boolean z2;
        ServiceInfo serviceInfo;
        String packageName = this.mIntent.getComponent().getPackageName();
        try {
            this.mPackageManagerAdapter.mPackageManager.getPackageInfoAsUser(packageName, 0, this.mUser.getIdentifier());
            z = true;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("TileLifecycleManager", "Package not available: " + packageName, e);
            z = false;
        }
        if (z) {
            this.mIntent.getComponent().getPackageName();
            try {
                serviceInfo = this.mPackageManagerAdapter.mIPackageManager.getServiceInfo(this.mIntent.getComponent(), 0, this.mUser.getIdentifier());
                if (serviceInfo == null) {
                    Log.d("TileLifecycleManager", "Can't find component " + this.mIntent.getComponent());
                }
            } catch (RemoteException unused) {
            }
            if (serviceInfo != null) {
                z2 = true;
                if (z2) {
                    return true;
                }
            }
            z2 = false;
            if (z2) {
            }
        }
        startPackageListening();
        return false;
    }

    public final void freeWrapper() {
        QSTileServiceWrapper qSTileServiceWrapper = this.mWrapper;
        if (qSTileServiceWrapper != null) {
            try {
                qSTileServiceWrapper.mService.asBinder().unlinkToDeath(this, 0);
            } catch (NoSuchElementException unused) {
                Log.w("TileLifecycleManager", "Trying to unlink not linked recipient for component" + this.mIntent.getComponent().flattenToShortString());
            }
            this.mWrapper = null;
        }
    }

    public final ComponentName getComponent() {
        return this.mIntent.getComponent();
    }

    public final void handleDeath() {
        if (this.mWrapper == null) {
            return;
        }
        freeWrapper();
        if (this.mIsBound.get()) {
            try {
                Log.w("TileLifecycleManager", "Unbinding service.. it is dead " + this.mIntent);
                this.mContext.unbindService(this);
            } catch (Exception unused) {
            }
            this.mIsBound.set(false);
        }
        if (this.mBound.get()) {
            Log.d("TileLifecycleManager", "handleDeath");
            if (checkComponentState()) {
                this.mExecutor.executeDelayed(this.mBindRetryDelay, new TileLifecycleManager$$ExternalSyntheticLambda1(this, 0));
            }
        }
    }

    public final void onClick(IBinder iBinder) {
        boolean z;
        Log.d("TileLifecycleManager", "onClick " + iBinder + " " + this.mUser);
        QSTileServiceWrapper qSTileServiceWrapper = this.mWrapper;
        if (qSTileServiceWrapper != null) {
            qSTileServiceWrapper.getClass();
            try {
                qSTileServiceWrapper.mService.onClick(iBinder);
                z = true;
            } catch (Exception e) {
                Log.d("IQSTileServiceWrapper", "Caught exception from TileService", e);
                z = false;
            }
            if (z) {
                return;
            }
        }
        this.mClickBinder = iBinder;
        queueMessage(2);
        handleDeath();
        this.mServices.recalculateBindAllowance();
    }

    @Override // android.content.ServiceConnection
    public final void onNullBinding(ComponentName componentName) {
        ((ExecutorImpl) this.mExecutor).execute(new TileLifecycleManager$$ExternalSyntheticLambda0(this, false));
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        TileChangeListener tileChangeListener;
        Log.d("TileLifecycleManager", "onReceive: " + intent);
        if ("android.intent.action.USER_UNLOCKED".equals(intent.getAction()) || Objects.equals(intent.getData().getEncodedSchemeSpecificPart(), this.mIntent.getComponent().getPackageName())) {
            if ("android.intent.action.PACKAGE_CHANGED".equals(intent.getAction()) && (tileChangeListener = this.mChangeListener) != null) {
                this.mIntent.getComponent();
                CustomTile customTile = (CustomTile) tileChangeListener;
                customTile.getClass();
                ((SQSTileImpl) customTile).mHandler.post(new CustomTile$$ExternalSyntheticLambda2(customTile, 3));
            }
            stopPackageListening();
            synchronized (this.mQueuedMessages) {
                if (((ArraySet) this.mQueuedMessages).contains(5)) {
                    this.mBound.set(true);
                }
            }
            ((ExecutorImpl) this.mExecutor).execute(new TileLifecycleManager$$ExternalSyntheticLambda1(this, 1));
        }
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        ArraySet arraySet;
        Log.d("TileLifecycleManager", "onServiceConnected " + componentName);
        stopPackageListening();
        this.mBindTryCount = 0;
        QSTileServiceWrapper qSTileServiceWrapper = new QSTileServiceWrapper(IQSTileService.Stub.asInterface(iBinder));
        try {
            iBinder.linkToDeath(this, 0);
        } catch (RemoteException unused) {
        }
        this.mHasPendingBind = false;
        this.mWrapper = qSTileServiceWrapper;
        synchronized (this.mQueuedMessages) {
            arraySet = new ArraySet(this.mQueuedMessages);
            ((ArraySet) this.mQueuedMessages).clear();
        }
        if (arraySet.contains(0)) {
            Log.d("TileLifecycleManager", "Handling pending onAdded");
            onTileAdded();
        }
        if (this.mListening) {
            Log.d("TileLifecycleManager", "Handling pending onStartListening");
            onStartListening();
        }
        if (arraySet.contains(2)) {
            Log.d("TileLifecycleManager", "Handling pending onClick");
            if (this.mListening) {
                onClick(this.mClickBinder);
            } else {
                Log.w("TileLifecycleManager", "Managed to get click on non-listening state...");
            }
        }
        if (arraySet.contains(4)) {
            Log.d("TileLifecycleManager", "Handling pending semSetToggleButtonChecked");
            if (this.mListening) {
                semSetToggleButtonChecked(this.mToggleState);
            } else {
                Log.w("TileLifecycleManager", "Managed to get click on non-listening state...");
            }
        }
        if (arraySet.contains(3)) {
            Log.d("TileLifecycleManager", "Handling pending onUnlockComplete");
            if (this.mListening) {
                onUnlockComplete();
            } else {
                Log.w("TileLifecycleManager", "Managed to get unlock on non-listening state...");
            }
        }
        if (arraySet.contains(1)) {
            Log.d("TileLifecycleManager", "Handling pending onRemoved");
            if (this.mListening) {
                Log.w("TileLifecycleManager", "Managed to get remove in listening state...");
                onStopListening();
            }
            onTileRemoved();
        }
        if (arraySet.contains(5)) {
            Log.d("TileLifecycleManager", "Handling pending refresh detail info");
            this.mServices.refreshDetailInfo(getComponent());
        }
        ((ExecutorImpl) this.mExecutor).execute(new TileLifecycleManager$$ExternalSyntheticLambda1(this, 3));
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        Log.d("TileLifecycleManager", "onServiceDisconnected " + componentName);
        handleDeath();
    }

    public final void onStartListening() {
        Log.d("TileLifecycleManager", "onStartListening  " + getComponent());
        boolean z = true;
        this.mListening = true;
        QSTileServiceWrapper qSTileServiceWrapper = this.mWrapper;
        if (qSTileServiceWrapper != null) {
            qSTileServiceWrapper.getClass();
            try {
                qSTileServiceWrapper.mService.onStartListening();
            } catch (Exception e) {
                Log.d("IQSTileServiceWrapper", "Caught exception from TileService", e);
                z = false;
            }
            if (z) {
                return;
            }
            handleDeath();
        }
    }

    public final void onStopListening() {
        Log.d("TileLifecycleManager", "onStopListening  " + getComponent());
        boolean z = false;
        this.mListening = false;
        QSTileServiceWrapper qSTileServiceWrapper = this.mWrapper;
        if (qSTileServiceWrapper != null) {
            qSTileServiceWrapper.getClass();
            try {
                qSTileServiceWrapper.mService.onStopListening();
                z = true;
            } catch (Exception e) {
                Log.d("IQSTileServiceWrapper", "Caught exception from TileService", e);
            }
            if (z) {
                return;
            }
            handleDeath();
        }
    }

    public final void onTileAdded() {
        boolean z;
        Log.d("TileLifecycleManager", "onTileAdded  " + getComponent());
        QSTileServiceWrapper qSTileServiceWrapper = this.mWrapper;
        if (qSTileServiceWrapper != null) {
            qSTileServiceWrapper.getClass();
            try {
                qSTileServiceWrapper.mService.onTileAdded();
                z = true;
            } catch (Exception e) {
                Log.d("IQSTileServiceWrapper", "Caught exception from TileService", e);
                z = false;
            }
            if (z) {
                return;
            }
        }
        queueMessage(0);
        handleDeath();
    }

    public final void onTileRemoved() {
        boolean z;
        Log.d("TileLifecycleManager", "onTileRemoved  " + getComponent());
        QSTileServiceWrapper qSTileServiceWrapper = this.mWrapper;
        if (qSTileServiceWrapper != null) {
            qSTileServiceWrapper.getClass();
            try {
                qSTileServiceWrapper.mService.onTileRemoved();
                z = true;
            } catch (Exception e) {
                Log.d("IQSTileServiceWrapper", "Caught exception from TileService", e);
                z = false;
            }
            if (z) {
                return;
            }
        }
        queueMessage(1);
        handleDeath();
    }

    public final void onUnlockComplete() {
        boolean z;
        Log.d("TileLifecycleManager", "onUnlockComplete");
        QSTileServiceWrapper qSTileServiceWrapper = this.mWrapper;
        if (qSTileServiceWrapper != null) {
            qSTileServiceWrapper.getClass();
            try {
                qSTileServiceWrapper.mService.onUnlockComplete();
                z = true;
            } catch (Exception e) {
                Log.d("IQSTileServiceWrapper", "Caught exception from TileService", e);
                z = false;
            }
            if (z) {
                return;
            }
        }
        queueMessage(3);
        handleDeath();
    }

    public final void queueMessage(int i) {
        synchronized (this.mQueuedMessages) {
            ((ArraySet) this.mQueuedMessages).add(Integer.valueOf(i));
        }
    }

    public final void refreshDetailInfo() {
        if (this.mWrapper != null) {
            this.mServices.refreshDetailInfo(getComponent());
        } else {
            queueMessage(5);
        }
    }

    public final RemoteViews semGetDetailView() {
        Log.d("TileLifecycleManager", "semGetDetailView");
        QSTileServiceWrapper qSTileServiceWrapper = this.mWrapper;
        if (qSTileServiceWrapper == null) {
            handleDeath();
            return null;
        }
        qSTileServiceWrapper.getClass();
        try {
            return qSTileServiceWrapper.mService.semGetDetailView();
        } catch (Exception e) {
            Log.d("IQSTileServiceWrapper", "Caught exception from TileService", e);
            return null;
        }
    }

    public final CharSequence semGetDetailViewSettingButtonName() {
        Log.d("TileLifecycleManager", "semGetDetailViewSettingButtonName");
        QSTileServiceWrapper qSTileServiceWrapper = this.mWrapper;
        if (qSTileServiceWrapper == null) {
            handleDeath();
            return null;
        }
        qSTileServiceWrapper.getClass();
        try {
            return qSTileServiceWrapper.mService.semGetDetailViewSettingButtonName();
        } catch (Exception e) {
            Log.d("IQSTileServiceWrapper", "Caught exception from TileService", e);
            return null;
        }
    }

    public final CharSequence semGetDetailViewTitle() {
        Log.d("TileLifecycleManager", "semGetDetailViewTitle");
        QSTileServiceWrapper qSTileServiceWrapper = this.mWrapper;
        if (qSTileServiceWrapper == null) {
            handleDeath();
            return null;
        }
        qSTileServiceWrapper.getClass();
        try {
            return qSTileServiceWrapper.mService.semGetDetailViewTitle();
        } catch (Exception e) {
            Log.d("IQSTileServiceWrapper", "Caught exception from TileService", e);
            return null;
        }
    }

    public final Intent semGetSettingsIntent() {
        Log.d("TileLifecycleManager", "semGetSettingsIntent");
        QSTileServiceWrapper qSTileServiceWrapper = this.mWrapper;
        if (qSTileServiceWrapper == null) {
            handleDeath();
            return null;
        }
        qSTileServiceWrapper.getClass();
        try {
            return qSTileServiceWrapper.mService.semGetSettingsIntent();
        } catch (Exception e) {
            Log.d("IQSTileServiceWrapper", "Caught exception from TileService", e);
            return null;
        }
    }

    public final boolean semIsToggleButtonChecked() {
        Log.d("TileLifecycleManager", "semIsToggleButtonChecked");
        QSTileServiceWrapper qSTileServiceWrapper = this.mWrapper;
        if (qSTileServiceWrapper == null) {
            handleDeath();
            return false;
        }
        qSTileServiceWrapper.getClass();
        try {
            return qSTileServiceWrapper.mService.semIsToggleButtonChecked();
        } catch (Exception e) {
            Log.d("IQSTileServiceWrapper", "Caught exception from TileService", e);
            return false;
        }
    }

    public final boolean semIsToggleButtonExists() {
        Log.d("TileLifecycleManager", "semIsToggleButtonExists");
        QSTileServiceWrapper qSTileServiceWrapper = this.mWrapper;
        if (qSTileServiceWrapper == null) {
            handleDeath();
            return false;
        }
        qSTileServiceWrapper.getClass();
        try {
            return qSTileServiceWrapper.mService.semIsToggleButtonExists();
        } catch (Exception e) {
            Log.d("IQSTileServiceWrapper", "Caught exception from TileService", e);
            return false;
        }
    }

    public final void semSetToggleButtonChecked(boolean z) {
        boolean z2;
        Log.d("TileLifecycleManager", "semSetToggleButtonChecked");
        QSTileServiceWrapper qSTileServiceWrapper = this.mWrapper;
        if (qSTileServiceWrapper != null) {
            qSTileServiceWrapper.getClass();
            try {
                qSTileServiceWrapper.mService.semSetToggleButtonChecked(z);
                z2 = true;
            } catch (Exception e) {
                Log.d("IQSTileServiceWrapper", "Caught exception from TileService", e);
                z2 = false;
            }
            if (z2) {
                return;
            }
        }
        this.mToggleState = z;
        queueMessage(4);
        handleDeath();
    }

    public final void setBindService(boolean z) {
        if (this.mBound.get() && this.mUnbindImmediate.get()) {
            this.mUnbindImmediate.set(false);
            return;
        }
        this.mBound.set(z);
        if (!z) {
            Log.d("TileLifecycleManager", "Unbinding service " + this.mIntent + " " + this.mUser);
            this.mBindTryCount = 0;
            freeWrapper();
            if (this.mIsBound.get()) {
                try {
                    this.mContext.unbindService(this);
                } catch (Exception e) {
                    Log.e("TileLifecycleManager", "Failed to unbind service " + this.mIntent.getComponent().flattenToShortString(), e);
                }
                this.mIsBound.set(false);
                this.mHasPendingBind = false;
                return;
            }
            return;
        }
        if (this.mIsDestroyed) {
            Log.w("TileLifecycleManager", "Do not bind to service.. It is already destroyed " + this.mIntent);
            return;
        }
        if (this.mBindTryCount == 5) {
            startPackageListening();
            return;
        }
        if (checkComponentState()) {
            Log.d("TileLifecycleManager", "Binding service " + this.mIntent + " " + this.mUser);
            this.mBindTryCount = this.mBindTryCount + 1;
            try {
                this.mIsBound.set(CompatChanges.isChangeEnabled(241766793L, this.mIntent.getComponent().getPackageName(), this.mUser) ? this.mContext.bindServiceAsUser(this.mIntent, this, 33554433, this.mUser) : this.mContext.bindServiceAsUser(this.mIntent, this, 34603009, this.mUser));
                if (!this.mIsBound.get()) {
                    this.mContext.unbindService(this);
                }
                if (this.mIsBound.get()) {
                    this.mHasPendingBind = true;
                }
            } catch (SecurityException e2) {
                Log.e("TileLifecycleManager", "Failed to bind to service", e2);
                this.mIsBound.set(false);
                this.mHasPendingBind = false;
            }
        }
    }

    public final void startPackageListening() {
        Log.d("TileLifecycleManager", "startPackageListening");
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme("package");
        try {
            this.mPackageReceiverRegistered.set(true);
            this.mContext.registerReceiverAsUser(this, this.mUser, intentFilter, null, this.mHandler, 2);
        } catch (Exception e) {
            this.mPackageReceiverRegistered.set(false);
            Log.e("TileLifecycleManager", "Could not register package receiver", e);
        }
        IntentFilter intentFilter2 = new IntentFilter("android.intent.action.USER_UNLOCKED");
        try {
            this.mUserReceiverRegistered.set(true);
            this.mBroadcastDispatcher.registerReceiverWithHandler(this, intentFilter2, this.mHandler, this.mUser);
        } catch (Exception e2) {
            this.mUserReceiverRegistered.set(false);
            Log.e("TileLifecycleManager", "Could not register unlock receiver", e2);
        }
    }

    public final void stopPackageListening() {
        Log.d("TileLifecycleManager", "stopPackageListening");
        if (this.mUserReceiverRegistered.compareAndSet(true, false)) {
            this.mBroadcastDispatcher.unregisterReceiver(this);
        }
        if (this.mPackageReceiverRegistered.compareAndSet(true, false)) {
            this.mContext.unregisterReceiver(this);
        }
    }
}
