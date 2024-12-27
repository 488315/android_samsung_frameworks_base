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
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.settings.SecureSettings;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DreamTile extends QSTileImpl {
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final IDreamManager mDreamManager;
    public final boolean mDreamOnlyEnabledForDockUser;
    public final AnonymousClass3 mDreamSettingObserver;
    public final boolean mDreamSupported;
    public final AnonymousClass2 mEnabledSettingObserver;
    public final QSTile.Icon mIconDocked;
    public final QSTile.Icon mIconUndocked;
    public boolean mIsDocked;
    public final AnonymousClass1 mReceiver;
    public final UserTrackerImpl mUserTracker;

    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.qs.tiles.DreamTile$1] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.systemui.qs.tiles.DreamTile$2] */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.android.systemui.qs.tiles.DreamTile$3] */
    public DreamTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, IDreamManager iDreamManager, SecureSettings secureSettings, BroadcastDispatcher broadcastDispatcher, UserTracker userTracker, boolean z, boolean z2) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mIconDocked = QSTileImpl.ResourceIcon.get(R.drawable.ic_qs_screen_saver);
        this.mIconUndocked = QSTileImpl.ResourceIcon.get(R.drawable.ic_qs_screen_saver_undocked);
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
            public final void handleValueChanged(int i) {
                DreamTile.this.refreshState(null);
            }
        };
        this.mDreamSettingObserver = new UserSettingObserver(secureSettings, this.mHandler, "screensaver_components", userTrackerImpl.getUserId()) { // from class: com.android.systemui.qs.tiles.DreamTile.3
            @Override // com.android.systemui.qs.UserSettingObserver
            public final void handleValueChanged(int i) {
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
        setListening(z);
        setListening(z);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        booleanState.label = getTileLabel();
        ComponentName activeDream = getActiveDream();
        boolean z = false;
        CharSequence charSequence = null;
        if (activeDream != null) {
            PackageManager packageManager = this.mContext.getPackageManager();
            try {
                ServiceInfo serviceInfo = packageManager.getServiceInfo(activeDream, 0);
                if (serviceInfo != null) {
                    charSequence = serviceInfo.loadLabel(packageManager);
                }
            } catch (PackageManager.NameNotFoundException unused) {
            }
        }
        booleanState.secondaryLabel = charSequence;
        booleanState.contentDescription = getContentDescription(charSequence);
        booleanState.icon = this.mIsDocked ? this.mIconDocked : this.mIconUndocked;
        if (getActiveDream() != null) {
            if (getValue() == 1) {
                try {
                    z = this.mDreamManager.isDreaming();
                } catch (RemoteException e) {
                    Log.e("QSDream", "Can't check if dreaming", e);
                }
                booleanState.state = z ? 2 : 1;
                return;
            }
        }
        booleanState.state = 0;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final boolean isAvailable() {
        return Build.isDebuggable() && this.mDreamSupported && (!this.mDreamOnlyEnabledForDockUser || this.mUserTracker.getUserInfo().isMain());
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }
}
