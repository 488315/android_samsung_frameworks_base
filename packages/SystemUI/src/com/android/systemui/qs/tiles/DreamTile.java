package com.android.systemui.qs.tiles;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.service.dreams.IDreamManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.UserSettingObserver;
import com.android.systemui.qs.flags.QsInCompose;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.settings.SecureSettings;

/* loaded from: classes2.dex */
public class DreamTile extends QSTileImpl {
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final IDreamManager mDreamManager;
    public final boolean mDreamOnlyEnabledForDockUser;
    final UserSettingObserver mDreamSettingObserver;
    public final boolean mDreamSupported;
    final UserSettingObserver mEnabledSettingObserver;
    public boolean mIsDocked;
    public final AnonymousClass1 mReceiver;
    public final UserTrackerImpl mUserTracker;

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.qs.tiles.DreamTile$1] */
    public DreamTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, IDreamManager iDreamManager, SecureSettings secureSettings, BroadcastDispatcher broadcastDispatcher, UserTracker userTracker, boolean z, boolean z2) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mIsDocked = false;
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.tiles.DreamTile.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                if ("android.intent.action.DOCK_EVENT".equals(intent.getAction())) {
                    DreamTile.this.mIsDocked = intent.getIntExtra("android.intent.extra.DOCK_STATE", -1) != 0;
                }
                DreamTile.this.refreshState(null);
            }
        };
        this.mDreamManager = iDreamManager;
        this.mBroadcastDispatcher = broadcastDispatcher;
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) userTracker;
        this.mEnabledSettingObserver = new UserSettingObserver(secureSettings, this.mHandler, "screensaver_enabled", userTrackerImpl.getUserId()) { // from class: com.android.systemui.qs.tiles.DreamTile.2
            @Override // com.android.systemui.qs.UserSettingObserver
            public final void handleValueChanged(int i, boolean z3) {
                DreamTile.this.refreshState(null);
            }
        };
        this.mDreamSettingObserver = new UserSettingObserver(secureSettings, this.mHandler, "screensaver_components", userTrackerImpl.getUserId()) { // from class: com.android.systemui.qs.tiles.DreamTile.3
            @Override // com.android.systemui.qs.UserSettingObserver
            public final void handleValueChanged(int i, boolean z3) {
                DreamTile.this.refreshState(null);
            }
        };
        this.mUserTracker = userTrackerImpl;
        this.mDreamSupported = z;
        this.mDreamOnlyEnabledForDockUser = z2;
    }

    public final ComponentName getActiveDream() {
        try {
            ComponentName[] dreamComponentsForUser = this.mDreamManager.getDreamComponentsForUser(this.mUserTracker.getUserId());
            if (dreamComponentsForUser == null || dreamComponentsForUser.length <= 0) {
                return null;
            }
            return dreamComponentsForUser[0];
        } catch (RemoteException e) {
            Log.w(this.TAG, "Failed to get active dream", e);
            return null;
        }
    }

    public CharSequence getContentDescription(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return getTileLabel();
        }
        return ((Object) getTileLabel()) + ", " + ((Object) charSequence);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return new Intent("android.settings.DREAM_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_screensaver_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(Expandable expandable) {
        try {
            if (this.mDreamManager.isDreaming()) {
                this.mDreamManager.awaken();
            } else {
                this.mDreamManager.dream();
            }
        } catch (RemoteException e) {
            Log.e("QSDream", "Can't dream", e);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        AnonymousClass1 anonymousClass1 = this.mReceiver;
        BroadcastDispatcher broadcastDispatcher = this.mBroadcastDispatcher;
        if (z) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.DREAMING_STARTED");
            intentFilter.addAction("android.intent.action.DREAMING_STOPPED");
            intentFilter.addAction("android.intent.action.DOCK_EVENT");
            broadcastDispatcher.registerReceiver(intentFilter, anonymousClass1);
        } else {
            broadcastDispatcher.unregisterReceiver(anonymousClass1);
        }
        this.mEnabledSettingObserver.setListening(z);
        this.mDreamSettingObserver.setListening(z);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x005f  */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleUpdateState(QSTile.State state, Object obj) throws PackageManager.NameNotFoundException {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        booleanState.label = getTileLabel();
        ComponentName activeDream = getActiveDream();
        boolean zIsDreaming = false;
        CharSequence charSequenceLoadLabel = null;
        if (activeDream != null) {
            PackageManager packageManager = this.mContext.getPackageManager();
            try {
                ServiceInfo serviceInfo = packageManager.getServiceInfo(activeDream, 0);
                if (serviceInfo != null) {
                    charSequenceLoadLabel = serviceInfo.loadLabel(packageManager);
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        booleanState.secondaryLabel = charSequenceLoadLabel;
        booleanState.contentDescription = getContentDescription(charSequenceLoadLabel);
        int i = this.mIsDocked ? R.drawable.ic_qs_screen_saver : R.drawable.ic_qs_screen_saver_undocked;
        int i2 = QsInCompose.$r8$clinit;
        booleanState.icon = QSTileImpl.ResourceIcon.get(i);
        if (getActiveDream() != null) {
            if (this.mEnabledSettingObserver.getValue() == 1) {
                try {
                    zIsDreaming = this.mDreamManager.isDreaming();
                } catch (RemoteException e) {
                    Log.e("QSDream", "Can't check if dreaming", e);
                }
                booleanState.state = zIsDreaming ? 2 : 1;
            } else {
                booleanState.state = 0;
            }
        }
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUserSwitch(int i) {
        handleRefreshState(null);
        this.mDreamSettingObserver.setUserId(i);
        this.mEnabledSettingObserver.setUserId(i);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final boolean isAvailable() {
        if (Build.isDebuggable() && this.mDreamSupported) {
            return !this.mDreamOnlyEnabledForDockUser || this.mUserTracker.getUserInfo().isMain();
        }
        return false;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }
}
