package com.android.systemui.qs.tiles;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.notification.ZenModeConfig;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.Toast;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardFMMViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.notification.EnableZenModeDialog;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.UserSettingObserver;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.qs.tiles.detail.DndDetailAdapter;
import com.android.systemui.qs.tiles.dialog.QSZenModeDialogMetricsLogger;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import java.util.Calendar;

public final class DndTile extends SQSTileImpl {
    public static final Intent DND_SETTINGS;
    public static final int mZenOneHourSession;
    public final ZenModeController mController;
    public final DndDetailAdapter mDetailAdapter;
    public int mDndMenuSelectedItem;
    public String mDndMenuSummary;
    public final GlobalSettings mGlobalSettings;
    public boolean mIsSettingsUpdated;
    public int mLastDndDurationSelected;
    public boolean mListening;
    public final PanelInteractor mPanelInteractor;
    public final AnonymousClass3 mPrefListener;
    public int mPreviousSetZenDuration;
    public final AnonymousClass1 mSettingZenDuration;
    public final AnonymousClass2 mSettingsObserver;
    public final SharedPreferences mSharedPreferences;
    public final AnonymousClass4 mZenCallback;

    static {
        new Intent("android.settings.ZEN_MODE_SETTINGS");
        new Intent("android.settings.ZEN_MODE_PRIORITY_SETTINGS");
        DND_SETTINGS = new Intent().setComponent(new ComponentName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$ZenModeSettingsActivity")).setAction("android.intent.action.MAIN");
        mZenOneHourSession = 60;
    }

    public DndTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, ZenModeController zenModeController, SharedPreferences sharedPreferences, SecureSettings secureSettings, DialogTransitionAnimator dialogTransitionAnimator, PanelInteractor panelInteractor, GlobalSettings globalSettings) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mDndMenuSelectedItem = 0;
        this.mLastDndDurationSelected = -1;
        this.mIsSettingsUpdated = false;
        this.mPreviousSetZenDuration = -2;
        ?? r3 = new ContentObserver(this.mUiHandler) { // from class: com.android.systemui.qs.tiles.DndTile.2
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                boolean z2;
                super.onChange(z, uri);
                int value = getValue();
                RecyclerView$$ExternalSyntheticOutline0.m(DndTile.this.mPreviousSetZenDuration, DndTile.this.TAG, KeyguardFMMViewController$$ExternalSyntheticOutline0.m("ZEN_MODE: onChange = ", value, ",currentZen = ", z, ",previousZen = "));
                DndTile dndTile = DndTile.this;
                int i = dndTile.mPreviousSetZenDuration;
                if (i == -2 || (z2 = dndTile.mIsSettingsUpdated)) {
                    dndTile.mPreviousSetZenDuration = value;
                } else if (!z2 && i != value) {
                    Settings.Secure.putInt(dndTile.mContext.getContentResolver(), "zen_duration", DndTile.this.mPreviousSetZenDuration);
                }
                DndTile.this.mIsSettingsUpdated = true;
            }
        };
        this.mSettingsObserver = r3;
        this.mPrefListener = new SharedPreferences.OnSharedPreferenceChangeListener() { // from class: com.android.systemui.qs.tiles.DndTile.3
            @Override // android.content.SharedPreferences.OnSharedPreferenceChangeListener
            public final void onSharedPreferenceChanged(SharedPreferences sharedPreferences2, String str) {
                if ("DndTileCombinedIcon".equals(str) || "DndTileVisible".equals(str)) {
                    DndTile.this.refreshState(null);
                }
            }
        };
        ZenModeController.Callback callback = new ZenModeController.Callback() { // from class: com.android.systemui.qs.tiles.DndTile.4
            @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
            public final void onZenChanged(int i) {
                Intent intent = DndTile.DND_SETTINGS;
                DndTile dndTile = DndTile.this;
                Log.d(dndTile.TAG, "Zen changed to " + i + ". Requesting refresh of tile.");
                dndTile.refreshState(Integer.valueOf(i));
            }
        };
        this.mController = zenModeController;
        this.mSharedPreferences = sharedPreferences;
        zenModeController.observe(((QSTileImpl) this).mLifecycle, callback);
        this.mSettingZenDuration = new UserSettingObserver(secureSettings, this.mUiHandler, "zen_duration", this.mHost.getUserId()) { // from class: com.android.systemui.qs.tiles.DndTile.1
            @Override // com.android.systemui.qs.UserSettingObserver
            public final void handleValueChanged(int i) {
                DndTile.this.refreshState(null);
            }
        };
        this.mGlobalSettings = globalSettings;
        globalSettings.registerContentObserverSync("zen_mode", (ContentObserver) r3);
        this.mDetailAdapter = new DndDetailAdapter(this, this.mContext, (QSTile.BooleanState) this.mState, zenModeController, globalSettings, r3, ((SQSTileImpl) this).mHandler);
        this.mPanelInteractor = panelInteractor;
        new QSZenModeDialogMetricsLogger(this.mContext);
    }

    public static String getStringFromMillis(Context context, long j) {
        if (j < 0 || j >= 1440) {
            return DateFormat.getTimeFormat(context).format(Long.valueOf(j));
        }
        Calendar calendar = Calendar.getInstance();
        int i = (int) j;
        calendar.set(11, i / 60);
        calendar.set(12, i % 60);
        return DateFormat.getTimeFormat(context).format(calendar.getTime());
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
        return 118;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_dnd_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(Expandable expandable) {
        ZenModeControllerImpl zenModeControllerImpl = (ZenModeControllerImpl) this.mController;
        if (zenModeControllerImpl.mUserManager.hasUserRestriction("no_adjust_volume", UserHandle.of(zenModeControllerImpl.mUserId))) {
            this.mPanelInteractor.collapsePanels();
            Context context = this.mContext;
            Toast.makeText(context, context.getString(android.R.string.indeterminate_progress_60), 1).show();
            return;
        }
        QSTile.BooleanState booleanState = (QSTile.BooleanState) this.mState;
        if (!booleanState.value && booleanState.state == 2) {
            booleanState.value = ((ZenModeControllerImpl) this.mController).mZenMode != 0;
            Log.d(this.TAG, "handleClick refresh value ");
        }
        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("handleClick ="), ((QSTile.BooleanState) this.mState).value, this.TAG);
        boolean z = !((QSTile.BooleanState) this.mState).value;
        ZenModeController zenModeController = this.mController;
        String str = this.TAG;
        if (z) {
            int i = Settings.Global.getInt(this.mHost.getUserContext().getContentResolver(), "zen_duration", 0);
            if (i == -1) {
                final ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(this.mContext, android.R.style.Theme.DeviceDefault.Settings);
                this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.DndTile$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        DndTile dndTile = DndTile.this;
                        ContextThemeWrapper contextThemeWrapper2 = contextThemeWrapper;
                        dndTile.getClass();
                        final AlertDialog createDialog = new EnableZenModeDialog(contextThemeWrapper2).createDialog();
                        createDialog.getWindow().setType(2009);
                        SystemUIDialog.setShowForAllUsers(createDialog);
                        SystemUIDialog.registerDismissListener(createDialog);
                        SystemUIDialog.setWindowOnTop(createDialog, true);
                        SystemUIDialog.setDialogSize(createDialog);
                        dndTile.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.DndTile$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                createDialog.show();
                            }
                        });
                        dndTile.mPanelInteractor.collapsePanels();
                    }
                });
            } else if (i != 0) {
                ((ZenModeControllerImpl) zenModeController).setZen(1, ZenModeConfig.toTimeCondition(this.mContext, i, ActivityManager.getCurrentUser(), true).id, str);
            } else {
                ((ZenModeControllerImpl) zenModeController).setZen(1, null, str);
            }
        } else {
            ((ZenModeControllerImpl) zenModeController).setZen(0, null, str);
        }
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("setZen state: ", str, z);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        super.handleDestroy();
        setListening(false);
        this.mGlobalSettings.unregisterContentObserverSync(this.mSettingsObserver);
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSecondaryClick(Expandable expandable) {
        showDetail(true);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        AnonymousClass3 anonymousClass3 = this.mPrefListener;
        if (z) {
            Prefs.get(this.mContext).registerOnSharedPreferenceChangeListener(anonymousClass3);
        } else {
            Prefs.get(this.mContext).unregisterOnSharedPreferenceChangeListener(anonymousClass3);
        }
        setListening(z);
        GlobalSettings globalSettings = this.mGlobalSettings;
        AnonymousClass2 anonymousClass2 = this.mSettingsObserver;
        if (z) {
            globalSettings.unregisterContentObserverSync(anonymousClass2);
        } else {
            globalSettings.registerContentObserverSync("zen_mode", anonymousClass2);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        ZenModeController zenModeController = this.mController;
        if (zenModeController == null) {
            return;
        }
        int intValue = obj instanceof Integer ? ((Integer) obj).intValue() : ((ZenModeControllerImpl) zenModeController).mZenMode;
        Log.d(this.TAG, "handleUpdateState zen " + intValue + "  state = " + booleanState);
        boolean z = intValue != 0;
        boolean z2 = booleanState.value;
        booleanState.dualTarget = true;
        booleanState.value = z;
        booleanState.state = z ? 2 : 1;
        booleanState.label = this.mContext.getString(R.string.quick_settings_sec_dnd_label);
        booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_donot_disturb);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUserSwitch(int i) {
        handleRefreshState(null);
        setUserId(i);
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final boolean isAvailable() {
        if (this.mSharedPreferences.getBoolean("DndTileVisible", false)) {
            return !this.mHost.shouldBeHiddenByKnox(this.mTileSpec);
        }
        return false;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }
}
