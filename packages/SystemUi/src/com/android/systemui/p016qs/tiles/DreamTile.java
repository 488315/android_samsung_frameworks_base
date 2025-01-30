package com.android.systemui.p016qs.tiles;

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
import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.p016qs.QSHost;
import com.android.systemui.p016qs.QsEventLogger;
import com.android.systemui.p016qs.SettingObserver;
import com.android.systemui.p016qs.logging.QSLogger;
import com.android.systemui.p016qs.tileimpl.QSTileImpl;
import com.android.systemui.p016qs.tileimpl.SQSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p013qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.settings.SecureSettings;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DreamTile extends SQSTileImpl {
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final IDreamManager mDreamManager;
    public final boolean mDreamOnlyEnabledForDockUser;
    public final C22443 mDreamSettingObserver;
    public final boolean mDreamSupported;
    public final C22432 mEnabledSettingObserver;
    public final QSTile.Icon mIconDocked;
    public final QSTile.Icon mIconUndocked;
    public boolean mIsDocked;
    public final C22421 mReceiver;
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
        this.mEnabledSettingObserver = new SettingObserver(secureSettings, ((SQSTileImpl) this).mHandler, "screensaver_enabled", userTrackerImpl.getUserId()) { // from class: com.android.systemui.qs.tiles.DreamTile.2
            @Override // com.android.systemui.p016qs.SettingObserver
            public final void handleValueChanged(int i, boolean z3) {
                DreamTile.this.refreshState(null);
            }
        };
        this.mDreamSettingObserver = new SettingObserver(secureSettings, ((SQSTileImpl) this).mHandler, "screensaver_components", userTrackerImpl.getUserId()) { // from class: com.android.systemui.qs.tiles.DreamTile.3
            @Override // com.android.systemui.p016qs.SettingObserver
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
            Log.w("DreamTile", "Failed to get active dream", e);
            return null;
        }
    }

    public CharSequence getContentDescription(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            return getTileLabel();
        }
        return ((Object) getTileLabel()) + ", " + ((Object) charSequence);
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return new Intent("android.settings.DREAM_SETTINGS");
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_screensaver_label);
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleClick(View view) {
        IDreamManager iDreamManager = this.mDreamManager;
        try {
            if (iDreamManager.isDreaming()) {
                iDreamManager.awaken();
            } else {
                iDreamManager.dream();
            }
        } catch (RemoteException e) {
            Log.e("QSDream", "Can't dream", e);
        }
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl, com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleLongClick(View view) {
        try {
            this.mDreamManager.awaken();
        } catch (RemoteException e) {
            Log.e("QSDream", "Can't awaken", e);
        }
        super.handleLongClick(view);
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl, com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        C22421 c22421 = this.mReceiver;
        BroadcastDispatcher broadcastDispatcher = this.mBroadcastDispatcher;
        if (z) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.DREAMING_STARTED");
            intentFilter.addAction("android.intent.action.DREAMING_STOPPED");
            intentFilter.addAction("android.intent.action.DOCK_EVENT");
            broadcastDispatcher.registerReceiver(intentFilter, c22421);
        } else {
            broadcastDispatcher.unregisterReceiver(c22421);
        }
        setListening(z);
        setListening(z);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0030  */
    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleUpdateState(QSTile.State state, Object obj) {
        CharSequence charSequence;
        ServiceInfo serviceInfo;
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        booleanState.label = getTileLabel();
        ComponentName activeDream = getActiveDream();
        boolean z = false;
        if (activeDream != null) {
            PackageManager packageManager = this.mContext.getPackageManager();
            try {
                serviceInfo = packageManager.getServiceInfo(activeDream, 0);
            } catch (PackageManager.NameNotFoundException unused) {
            }
            if (serviceInfo != null) {
                charSequence = serviceInfo.loadLabel(packageManager);
                booleanState.secondaryLabel = charSequence;
                booleanState.contentDescription = getContentDescription(charSequence);
                booleanState.icon = !this.mIsDocked ? this.mIconDocked : this.mIconUndocked;
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
        }
        charSequence = null;
        booleanState.secondaryLabel = charSequence;
        booleanState.contentDescription = getContentDescription(charSequence);
        booleanState.icon = !this.mIsDocked ? this.mIconDocked : this.mIconUndocked;
        if (getActiveDream() != null) {
        }
        booleanState.state = 0;
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl, com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final boolean isAvailable() {
        return Build.isDebuggable() && this.mDreamSupported && (!this.mDreamOnlyEnabledForDockUser || this.mUserTracker.getUserInfo().isMain());
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }
}
