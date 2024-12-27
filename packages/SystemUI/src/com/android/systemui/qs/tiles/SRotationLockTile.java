package com.android.systemui.qs.tiles;

import android.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.hardware.SensorPrivacyManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.animation.Expandable;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.pluginlock.PluginLockMediator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSBackupRestoreManager;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.SettingObserver;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.qs.tiles.detail.RotationLockDetailAdapter;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.util.QsResetSettingsManager;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SRotationLockTile extends SQSTileImpl implements BatteryController.BatteryStateChangeCallback, QsResetSettingsManager.DemoResetSettingsApplier {
    public final QSTileImpl.AnimationIcon mAutoToLandscape;
    public final QSTileImpl.AnimationIcon mAutoToPortrait;
    public final BatteryController mBatteryController;
    public final AnonymousClass4 mCallback;
    public final RotationLockController mController;
    public final RotationLockDetailAdapter mDetailAdapter;
    public final QSTileImpl.AnimationIcon mLandscapeToAuto;
    public final PluginLockMediator mPluginLockMediator;
    public final QSTileImpl.AnimationIcon mPortraitToAuto;
    public final SensorPrivacyManager mPrivacyManager;
    public final Resources mResources;
    public final SharedPreferences.Editor mRotationLockTilePrefEditor;
    public boolean mRotationLocked;
    public final SRotationLockTile$$ExternalSyntheticLambda0 mSensorPrivacyChangedListener;
    public final AnonymousClass2 mSetting;
    private SettingsHelper.OnChangedCallback mSettingsCallback;
    private final SettingsHelper mSettingsHelper;
    public final QSTile.BooleanState mStateBeforeClick;

    /* renamed from: -$$Nest$mgetBackupData, reason: not valid java name */
    public static String m2082$$Nest$mgetBackupData(SRotationLockTile sRotationLockTile, boolean z) {
        String str;
        String str2;
        String str3;
        String str4;
        StringBuilder sb = new StringBuilder("TAG::autorotate_rotationlock::");
        if (z) {
            str = "" + sRotationLockTile.mController.isRotationLocked();
            str3 = "" + sRotationLockTile.mSettingsHelper.isHomeScreenRotationAllowed();
            str4 = "" + sRotationLockTile.mSettingsHelper.isLockScreenRotationAllowed();
            str2 = "" + sRotationLockTile.mSettingsHelper.isCallScreenRotationAllowed();
        } else {
            sRotationLockTile.getClass();
            str = null;
            str2 = null;
            str3 = null;
            str4 = null;
        }
        ConstraintWidget$$ExternalSyntheticOutline0.m(sb, str, "::TAG::autorotate_homescreen::", str3, "::TAG::autorotate_lockscreen::");
        sb.append(str4);
        sb.append("::TAG::autorotate_voicecallscreen::");
        sb.append(str2);
        Log.d("SRotationLockTile", "getBackupData: " + sb.toString());
        return sb.toString();
    }

    /* renamed from: -$$Nest$msetRestoreData, reason: not valid java name */
    public static void m2083$$Nest$msetRestoreData(SRotationLockTile sRotationLockTile, String str) {
        sRotationLockTile.getClass();
        String[] split = str.split("::");
        Log.d("SRotationLockTile", "restoreData: ".concat(str));
        if (split.length > 1) {
            if (split[0].equals("autorotate_rotationlock")) {
                String str2 = split[1];
                if (str2 == null) {
                    Log.w("SRotationLockTile", "restoredRotationLock is null");
                    return;
                } else {
                    sRotationLockTile.mController.setRotationLocked("SRotationLockTile #setRestoreData", str2.equals("true"));
                }
            }
            if (split[0].equals("autorotate_homescreen")) {
                String str3 = split[1];
                if (str3 == null) {
                    Log.w("SRotationLockTile", "restoredHomeScreenSetting is null");
                    return;
                }
                sRotationLockTile.mSettingsHelper.setHomeScreenRotationAllowed(str3.equals("true"));
            }
            if (split[0].equals("autorotate_lockscreen")) {
                String str4 = split[1];
                if (str4 == null) {
                    Log.w("SRotationLockTile", "restoredLockScreenSetting is null");
                    return;
                }
                sRotationLockTile.mSettingsHelper.setLockScreenRotationAllowed(str4.equals("true"));
            }
            if (split[0].equals("autorotate_voicecallscreen")) {
                String str5 = split[1];
                if (str5 == null) {
                    Log.w("SRotationLockTile", "restoredCallScreenRotateSetting is null");
                } else {
                    sRotationLockTile.mSettingsHelper.setCallScreenRotationAllowed(str5.equals("true"));
                }
            }
        }
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.qs.tiles.SRotationLockTile$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.systemui.qs.tiles.SRotationLockTile$2] */
    public SRotationLockTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, Resources resources, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, SettingsHelper settingsHelper, RotationLockController rotationLockController, SensorPrivacyManager sensorPrivacyManager, BatteryController batteryController, SecureSettings secureSettings, PluginLockMediator pluginLockMediator) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        QSTileImpl.ResourceIcon.get(R.drawable.ic_star_black_16dp);
        this.mAutoToPortrait = new QSTileImpl.AnimationIcon(com.android.systemui.R.drawable.quick_panel_icon_rotation_lock_auto_to_portrait, com.android.systemui.R.drawable.quick_panel_icon_rotation_lock_auto_to_portrait_020);
        this.mPortraitToAuto = new QSTileImpl.AnimationIcon(com.android.systemui.R.drawable.quick_panel_icon_rotation_lock_portrait_to_auto, com.android.systemui.R.drawable.quick_panel_icon_rotation_lock_portrait_to_auto_020);
        this.mAutoToLandscape = new QSTileImpl.AnimationIcon(com.android.systemui.R.drawable.quick_panel_icon_rotation_lock_auto_to_landscape, com.android.systemui.R.drawable.quick_panel_icon_rotation_lock_auto_to_landscape_020);
        this.mLandscapeToAuto = new QSTileImpl.AnimationIcon(com.android.systemui.R.drawable.quick_panel_icon_rotation_lock_landscape_to_auto, com.android.systemui.R.drawable.quick_panel_icon_rotation_lock_landscape_to_auto_020);
        this.mStateBeforeClick = new QSTile.BooleanState();
        Uri[] uriArr = {Settings.System.getUriFor(SettingsHelper.INDEX_ACCELEROMETER_ROTATION)};
        this.mSettingsCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.qs.tiles.SRotationLockTile.1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                if (uri != null && uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_ACCELEROMETER_ROTATION))) {
                    Log.d("SRotationLockTile", " OnChangedCallback : ");
                    SRotationLockTile.this.refreshState(null);
                }
            }
        };
        RotationLockController.RotationLockControllerCallback rotationLockControllerCallback = new RotationLockController.RotationLockControllerCallback() { // from class: com.android.systemui.qs.tiles.SRotationLockTile.4
            @Override // com.android.systemui.statusbar.policy.RotationLockController.RotationLockControllerCallback
            public final void onRotationLockStateChanged(boolean z, boolean z2) {
                SRotationLockTile.this.refreshState(Boolean.valueOf(z));
            }
        };
        this.mSensorPrivacyChangedListener = new SensorPrivacyManager.OnSensorPrivacyChangedListener() { // from class: com.android.systemui.qs.tiles.SRotationLockTile$$ExternalSyntheticLambda0
            public final void onSensorPrivacyChanged(int i, boolean z) {
                SRotationLockTile.this.refreshState(null);
            }
        };
        this.mController = rotationLockController;
        rotationLockController.getClass();
        rotationLockController.observe(((QSTileImpl) this).mLifecycle, rotationLockControllerCallback);
        this.mPrivacyManager = sensorPrivacyManager;
        this.mBatteryController = batteryController;
        this.mSettingsHelper = settingsHelper;
        this.mResources = resources;
        this.mPluginLockMediator = pluginLockMediator;
        this.mSetting = new SettingObserver(secureSettings, ((SQSTileImpl) this).mHandler, "camera_autorotate", qSHost.getUserContext().getUserId()) { // from class: com.android.systemui.qs.tiles.SRotationLockTile.2
            @Override // com.android.systemui.qs.SettingObserver
            public final void handleValueChanged(int i, boolean z) {
                SRotationLockTile.this.handleRefreshState(null);
            }
        };
        batteryController.observe(((QSTileImpl) this).mLifecycle, this);
        settingsHelper.registerCallback(this.mSettingsCallback, uriArr);
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences(SystemUIAnalytics.QUICK_PREF_NAME, 0);
        if (sharedPreferences != null) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            this.mRotationLockTilePrefEditor = edit;
            if (QpRune.QUICK_TILE_ROTATION_MANUAL) {
                edit.putBoolean(SystemUIAnalytics.STATUS_ROTATION_DETAIL_NAVIGATION_BAR_ROTATE_SUGGESTION_ENABLED, settingsHelper.isNavigationBarRotateSuggestionEnabled());
            }
            edit.putBoolean(SystemUIAnalytics.STATUS_ROTATION_DETAIL_HOME_SCREEN_SWITCH, settingsHelper.isHomeScreenRotationAllowed());
            edit.putBoolean(SystemUIAnalytics.STATUS_ROTATION_DETAIL_LOCK_SCREEN_SWITCH, settingsHelper.isLockScreenRotationAllowed());
            edit.putBoolean(SystemUIAnalytics.STATUS_ROTATION_DETAIL_CALL_SCREEN_SWITCH, settingsHelper.isCallScreenRotationAllowed());
            edit.commit();
        }
        this.mDetailAdapter = new RotationLockDetailAdapter(this.mContext, ((SQSTileImpl) this).mHandler, settingsHelper, rotationLockController, pluginLockMediator, this.mRotationLockTilePrefEditor, this);
        if (QpRune.QUICK_TABLET) {
            return;
        }
        ((QSBackupRestoreManager) Dependency.sDependency.getDependencyInner(QSBackupRestoreManager.class)).addCallback("AutoRotate", new QSBackupRestoreManager.Callback() { // from class: com.android.systemui.qs.tiles.SRotationLockTile.3
            @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
            public final boolean isValidDB() {
                SRotationLockTile.this.getClass();
                return true;
            }

            @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
            public final String onBackup(boolean z) {
                return SRotationLockTile.m2082$$Nest$mgetBackupData(SRotationLockTile.this, z);
            }

            @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
            public final void onRestore(String str) {
                SRotationLockTile.m2083$$Nest$msetRestoreData(SRotationLockTile.this, str);
            }
        });
    }

    @Override // com.android.systemui.util.QsResetSettingsManager.DemoResetSettingsApplier
    public final void applyDemoResetSetting() {
        Settings.System.putInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_ACCELEROMETER_ROTATION, 1);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final void destroy() {
        super.destroy();
        this.mSettingsHelper.unregisterCallback(this.mSettingsCallback);
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final DetailAdapter getDetailAdapter() {
        return this.mDetailAdapter;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 123;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final ArrayList getSearchWords() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.mContext.getString(com.android.systemui.R.string.sec_quick_settings_rotation_unlocked_label).trim().toLowerCase().replaceAll("\\n", " "));
        arrayList.add(this.mContext.getString(com.android.systemui.R.string.sec_quick_settings_rotation_locked_portrait_label).trim().toLowerCase());
        arrayList.add(this.mContext.getString(com.android.systemui.R.string.sec_quick_settings_rotation_locked_landscape_label).trim().toLowerCase());
        return arrayList;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final QSTile.State getState() {
        return (QSTile.BooleanState) this.mState;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return ((QSTile.BooleanState) this.mState).label;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(Expandable expandable) {
        Log.d("SRotationLockTile", " handleClick is called:++++ ");
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isRotationLockTileBlocked()) {
            super.showItPolicyToast();
            return;
        }
        if (((QSTile.BooleanState) this.mState).state == 0) {
            return;
        }
        RecyclerView$$ExternalSyntheticOutline0.m(((QSTile.BooleanState) this.mState).state, "SRotationLockTile", new StringBuilder("handleClick "));
        boolean z = this.mRotationLocked;
        this.mController.setRotationLocked("RotationLockTile#handleClick", !z);
        ((QSTile.BooleanState) this.mState).copyTo(this.mStateBeforeClick);
        refreshState(Boolean.valueOf(z));
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        super.handleDestroy();
        setListening(false);
        this.mPrivacyManager.removeSensorPrivacyListener(2, this.mSensorPrivacyChangedListener);
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleInitialize() {
        this.mPrivacyManager.addSensorPrivacyListener(2, this.mSensorPrivacyChangedListener);
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSecondaryClick(Expandable expandable) {
        Log.d("SRotationLockTile", " handleSecondaryClick is called:++++ ");
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isRotationLockTileBlocked()) {
            super.showItPolicyToast();
        } else {
            showDetail(true);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        setListening(z);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        int i;
        PackageManager packageManager;
        String rotationResolverPackageName;
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        RotationLockController rotationLockController = this.mController;
        boolean isRotationLocked = rotationLockController.isRotationLocked();
        boolean z = ((BatteryControllerImpl) this.mBatteryController).mPowerSave;
        boolean isSensorPrivacyEnabled = this.mPrivacyManager.isSensorPrivacyEnabled(2);
        if (!z && !isSensorPrivacyEnabled && (rotationResolverPackageName = (packageManager = this.mContext.getPackageManager()).getRotationResolverPackageName()) != null && packageManager.checkPermission("android.permission.CAMERA", rotationResolverPackageName) == 0) {
            rotationLockController.isCameraRotationEnabled();
        }
        booleanState.value = !isRotationLocked;
        booleanState.dualTarget = true;
        Resources resources = this.mResources;
        int rotationLockOrientation = rotationLockController.getRotationLockOrientation();
        boolean z2 = false;
        if (rotationLockOrientation != 0 ? rotationLockOrientation != 2 : resources.getConfiguration().orientation != 2) {
            z2 = true;
        }
        if (isRotationLocked) {
            i = z2 ? com.android.systemui.R.string.sec_quick_settings_rotation_locked_portrait_label : com.android.systemui.R.string.sec_quick_settings_rotation_locked_landscape_label;
            booleanState.icon = z2 ? this.mAutoToPortrait : this.mAutoToLandscape;
        } else {
            booleanState.icon = z2 ? this.mPortraitToAuto : this.mLandscapeToAuto;
            i = com.android.systemui.R.string.sec_quick_settings_rotation_unlocked_label;
        }
        booleanState.label = this.mContext.getString(i);
        booleanState.state = booleanState.value ? 2 : 1;
        booleanState.contentDescription = this.mContext.getString(i);
        this.mRotationLocked = isRotationLocked;
        StringBuilder sb = new StringBuilder(" mRotationLocked: ");
        sb.append(this.mRotationLocked);
        sb.append(" handleUpdateState: ");
        KeyguardCarrierViewController$$ExternalSyntheticOutline0.m(sb, booleanState.value, " orientation = ", z2, "SRotationLockTile");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUserSwitch(int i) {
        handleRefreshState(null);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    @Override // com.android.systemui.statusbar.policy.BatteryController.BatteryStateChangeCallback
    public final void onPowerSaveChanged(boolean z) {
        refreshState(null);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.SQSTile
    public final void sendTileStatusLog() {
        String tileMapKey = super.getTileMapKey();
        int i = QSTileHost.TilesMap.SID_TILE_STATE;
        this.mTilesMap.getClass();
        String id = QSTileHost.TilesMap.getId(i, tileMapKey);
        Resources resources = this.mContext.getResources();
        int rotationLockOrientation = this.mController.getRotationLockOrientation();
        boolean z = false;
        if (rotationLockOrientation != 0 ? rotationLockOrientation != 2 : resources.getConfiguration().orientation != 2) {
            z = true;
        }
        if (id != null) {
            this.mRotationLockTilePrefEditor.putString(id, getTileMapValue() == 1 ? "On" : z ? "portrait" : "landscape");
            this.mRotationLockTilePrefEditor.commit();
        }
    }
}
