package com.android.systemui.qs.tiles;

import android.R;
import android.content.Context;
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
import android.widget.Toast;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.animation.Expandable;
import com.android.systemui.knox.EdmMonitor;
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
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.settings.SecureSettings;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public class SRotationLockTile extends SQSTileImpl implements BatteryController.BatteryStateChangeCallback, QsResetSettingsManager.DemoResetSettingsApplier, QsResetSettingsManager.ResetSettingsApplier {
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
    public static String m2921$$Nest$mgetBackupData(SRotationLockTile sRotationLockTile, boolean z) {
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
        MoveResult$$ExternalSyntheticOutline0.m(sb, str, "::TAG::autorotate_homescreen::", str3, "::TAG::autorotate_lockscreen::");
        sb.append(str4);
        sb.append("::TAG::autorotate_voicecallscreen::");
        sb.append(str2);
        Log.d("SRotationLockTile", "getBackupData: " + sb.toString());
        return sb.toString();
    }

    /* renamed from: -$$Nest$msetRestoreData, reason: not valid java name */
    public static void m2922$$Nest$msetRestoreData(SRotationLockTile sRotationLockTile, String str) {
        sRotationLockTile.getClass();
        String[] strArrSplit = str.split("::");
        Log.d("SRotationLockTile", "restoreData: ".concat(str));
        if (strArrSplit.length > 1) {
            if (strArrSplit[0].equals("autorotate_rotationlock")) {
                String str2 = strArrSplit[1];
                if (str2 == null) {
                    Log.w("SRotationLockTile", "restoredRotationLock is null");
                    return;
                } else {
                    sRotationLockTile.mController.setRotationLocked("SRotationLockTile #setRestoreData", str2.equals("true"));
                }
            }
            if (strArrSplit[0].equals("autorotate_homescreen")) {
                String str3 = strArrSplit[1];
                if (str3 == null) {
                    Log.w("SRotationLockTile", "restoredHomeScreenSetting is null");
                    return;
                }
                sRotationLockTile.mSettingsHelper.setHomeScreenRotationAllowed(str3.equals("true"));
            }
            if (strArrSplit[0].equals("autorotate_lockscreen")) {
                String str4 = strArrSplit[1];
                if (str4 == null) {
                    Log.w("SRotationLockTile", "restoredLockScreenSetting is null");
                    return;
                }
                sRotationLockTile.mSettingsHelper.setLockScreenRotationAllowed(str4.equals("true"));
            }
            if (strArrSplit[0].equals("autorotate_voicecallscreen")) {
                String str5 = strArrSplit[1];
                if (str5 == null) {
                    Log.w("SRotationLockTile", "restoredCallScreenRotateSetting is null");
                } else {
                    sRotationLockTile.mSettingsHelper.setCallScreenRotationAllowed(str5.equals("true"));
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v8, types: [com.android.systemui.qs.tiles.SRotationLockTile$4, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v1, types: [com.android.systemui.qs.tiles.SRotationLockTile$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r6v3, types: [com.android.systemui.qs.tiles.SRotationLockTile$2] */
    public SRotationLockTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, Resources resources, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, SettingsHelper settingsHelper, RotationLockController rotationLockController, SensorPrivacyManager sensorPrivacyManager, BatteryController batteryController, SecureSettings secureSettings, PluginLockMediator pluginLockMediator) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        QSTileImpl.ResourceIcon.get(R.drawable.item_background_material_light);
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
        ?? r4 = new RotationLockController.RotationLockControllerCallback() { // from class: com.android.systemui.qs.tiles.SRotationLockTile.4
            @Override // com.android.systemui.statusbar.policy.RotationLockController.RotationLockControllerCallback
            public final void onRotationLockStateChanged(boolean z, boolean z2) {
                SRotationLockTile.this.refreshState(Boolean.valueOf(z));
            }
        };
        this.mCallback = r4;
        this.mSensorPrivacyChangedListener = new SensorPrivacyManager.OnSensorPrivacyChangedListener() { // from class: com.android.systemui.qs.tiles.SRotationLockTile$$ExternalSyntheticLambda0
            public final void onSensorPrivacyChanged(int i, boolean z) {
                this.f$0.refreshState(null);
            }
        };
        this.mController = rotationLockController;
        rotationLockController.getClass();
        rotationLockController.observe(((QSTileImpl) this).mLifecycle, r4);
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
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            this.mRotationLockTilePrefEditor = editorEdit;
            if (QpRune.QUICK_TILE_ROTATION_MANUAL) {
                editorEdit.putBoolean(SystemUIAnalytics.STATUS_ROTATION_DETAIL_NAVIGATION_BAR_ROTATE_SUGGESTION_ENABLED, settingsHelper.isNavigationBarRotateSuggestionEnabled());
            }
            editorEdit.putBoolean(SystemUIAnalytics.STATUS_ROTATION_DETAIL_HOME_SCREEN_SWITCH, settingsHelper.isHomeScreenRotationAllowed());
            editorEdit.putBoolean(SystemUIAnalytics.STATUS_ROTATION_DETAIL_LOCK_SCREEN_SWITCH, settingsHelper.isLockScreenRotationAllowed());
            editorEdit.putBoolean(SystemUIAnalytics.STATUS_ROTATION_DETAIL_CALL_SCREEN_SWITCH, settingsHelper.isCallScreenRotationAllowed());
            editorEdit.commit();
        }
        this.mDetailAdapter = new RotationLockDetailAdapter(this.mContext, ((SQSTileImpl) this).mHandler, settingsHelper, rotationLockController, pluginLockMediator, this.mRotationLockTilePrefEditor, this);
        if (!QpRune.QUICK_TABLET) {
            ((QSBackupRestoreManager) Dependency.sDependency.getDependencyInner(QSBackupRestoreManager.class)).addCallback("AutoRotate", new QSBackupRestoreManager.Callback() { // from class: com.android.systemui.qs.tiles.SRotationLockTile.3
                @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
                public final boolean isValidDB() {
                    SRotationLockTile.this.getClass();
                    return true;
                }

                @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
                public final String onBackup(boolean z) {
                    return SRotationLockTile.m2921$$Nest$mgetBackupData(SRotationLockTile.this, z);
                }

                @Override // com.android.systemui.qs.QSBackupRestoreManager.Callback
                public final void onRestore(String str) {
                    SRotationLockTile.m2922$$Nest$msetRestoreData(SRotationLockTile.this, str);
                }
            });
        }
        ((QsResetSettingsManager) Dependency.sDependency.getDependencyInner(QsResetSettingsManager.class)).registerDemoApplier(this);
        ((QsResetSettingsManager) Dependency.sDependency.getDependencyInner(QsResetSettingsManager.class)).registerApplier(this);
    }

    public static boolean isScreenRotationSupported() {
        return !((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isFoldNarrow();
    }

    @Override // com.android.systemui.util.QsResetSettingsManager.DemoResetSettingsApplier
    public final void applyDemoResetSetting() {
        Log.d("SRotationLockTile", "Rotation switch applyDemoResetSetting");
        Settings.System.putInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_ACCELEROMETER_ROTATION, 1);
    }

    @Override // com.android.systemui.util.QsResetSettingsManager.ResetSettingsApplier
    public final void applyResetSetting() {
        Log.d("SRotationLockTile", "Rotation switch applyResetSetting");
        Settings.System.putInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_ACCELEROMETER_ROTATION, 1);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final void destroy() {
        super.destroy();
        this.mSettingsHelper.unregisterCallback(this.mSettingsCallback);
        if (!QpRune.QUICK_TABLET) {
            ((QSBackupRestoreManager) Dependency.sDependency.getDependencyInner(QSBackupRestoreManager.class)).removeCallback("AutoRotate");
        }
        ((QsResetSettingsManager) Dependency.sDependency.getDependencyInner(QsResetSettingsManager.class)).unregisterDemoApplier(this);
        ((QsResetSettingsManager) Dependency.sDependency.getDependencyInner(QsResetSettingsManager.class)).unregisterApplier(this);
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
        EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mEdmMonitor;
        if (edmMonitor != null) {
            Context context = edmMonitor.knoxStateMonitor.mContext;
            if (!edmMonitor.mSettingsChangesAllowed) {
                super.showItPolicyToast();
                return;
            }
        }
        if (!isScreenRotationSupported()) {
            showToast$1();
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
        EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mEdmMonitor;
        if (edmMonitor != null) {
            Context context = edmMonitor.knoxStateMonitor.mContext;
            if (!edmMonitor.mSettingsChangesAllowed) {
                super.showItPolicyToast();
                return;
            }
        }
        if (isScreenRotationSupported()) {
            showDetail$1(true);
        } else {
            showToast$1();
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        setListening(z);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        Context context;
        int i;
        String strM;
        PackageManager packageManager;
        String rotationResolverPackageName;
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        RotationLockController rotationLockController = this.mController;
        boolean zIsRotationLocked = rotationLockController.isRotationLocked();
        boolean z = ((BatteryControllerImpl) this.mBatteryController).mPowerSave;
        boolean zIsSensorPrivacyEnabled = this.mPrivacyManager.isSensorPrivacyEnabled(2);
        if (!z && !zIsSensorPrivacyEnabled && (rotationResolverPackageName = (packageManager = this.mContext.getPackageManager()).getRotationResolverPackageName()) != null && packageManager.checkPermission("android.permission.CAMERA", rotationResolverPackageName) == 0) {
            rotationLockController.isCameraRotationEnabled();
        }
        booleanState.value = !zIsRotationLocked;
        booleanState.dualTarget = true;
        Resources resources = this.mResources;
        int rotationLockOrientation = rotationLockController.getRotationLockOrientation();
        boolean z2 = rotationLockOrientation != 0 ? rotationLockOrientation != 2 : resources.getConfiguration().orientation != 2;
        Context context2 = this.mContext;
        int i2 = com.android.systemui.R.string.sec_quick_settings_rotation_unlocked_label;
        String string = context2.getString(com.android.systemui.R.string.sec_quick_settings_rotation_unlocked_label);
        if (zIsRotationLocked) {
            i2 = z2 ? com.android.systemui.R.string.sec_quick_settings_rotation_locked_portrait_label : com.android.systemui.R.string.sec_quick_settings_rotation_locked_landscape_label;
            strM = this.mContext.getString(i2) + ", " + string;
            booleanState.icon = z2 ? this.mAutoToPortrait : this.mAutoToLandscape;
        } else {
            if (this.mResources.getConfiguration().orientation == 1) {
                context = this.mContext;
                i = com.android.systemui.R.string.quick_settings_rotation_lock_set_to_portrait;
            } else {
                context = this.mContext;
                i = com.android.systemui.R.string.quick_settings_rotation_lock_set_to_landscape;
            }
            strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(string, ", ", context.getString(i));
            booleanState.icon = z2 ? this.mPortraitToAuto : this.mLandscapeToAuto;
        }
        booleanState.label = this.mContext.getString(i2);
        if (isScreenRotationSupported()) {
            booleanState.state = booleanState.value ? 2 : 1;
        } else {
            booleanState.state = 0;
        }
        booleanState.contentDescription = strM;
        this.mRotationLocked = zIsRotationLocked;
        StringBuilder sb = new StringBuilder(" mRotationLocked: ");
        sb.append(this.mRotationLocked);
        sb.append(" handleUpdateState: ");
        CarrierTextManager$$ExternalSyntheticOutline0.m(sb, booleanState.value, " orientation = ", z2, "SRotationLockTile");
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
        Resources resources = this.mContext.getResources();
        int rotationLockOrientation = this.mController.getRotationLockOrientation();
        boolean z = false;
        if (rotationLockOrientation != 0 ? rotationLockOrientation != 2 : resources.getConfiguration().orientation != 2) {
            z = true;
        }
        this.mHost.sendTileStatusLog(getTileMapValue() == 1 ? "On" : z ? "portrait" : "landscape", super.getTileMapKey());
    }

    public final void showToast$1() {
        Context context = this.mContext;
        Toast.makeText(context, context.getResources().getString(com.android.systemui.R.string.sec_quick_settings_can_use_auto_rotate), 0).show();
    }
}
