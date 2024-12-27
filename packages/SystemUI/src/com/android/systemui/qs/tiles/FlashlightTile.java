package com.android.systemui.qs.tiles;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.widget.Toast;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qp.SubscreenQSControllerContract$FlashLightView;
import com.android.systemui.qp.SubscreenQsPanelController;
import com.android.systemui.qp.flashlight.SubroomFlashLightSettingsActivity;
import com.android.systemui.qp.flashlight.SubscreenFlashLightController;
import com.android.systemui.qp.util.SubscreenUtil;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.qs.tiles.detail.FlashlightDetailAdapter;
import com.android.systemui.qs.tiles.detail.FlashlightDetailAdapter.AnonymousClass2;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;
import com.android.systemui.statusbar.policy.SecFlashlightControllerImpl;
import com.android.systemui.util.SettingsHelper;
import com.sec.ims.IMSParameter;
import java.util.ArrayList;

public class FlashlightTile extends SQSTileImpl implements FlashlightController.FlashlightListener {
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final FlashlightDetailAdapter mDetailAdapter;
    public final QSTileImpl.AnimationIcon mDisable;
    public final DisplayLifecycle mDisplayLifecycle;
    public final QSTileImpl.AnimationIcon mEnable;
    public final FlashlightController mFlashlightController;
    public boolean mIsLowBattery;
    public boolean mListening;
    public final AnonymousClass1 mReceiver;
    public final SecFlashlightControllerImpl mSecFlashlightController;
    private SettingsHelper mSettingsHelper;
    public final Context mSubscreenContext;
    public final SubscreenFlashLightController mSubscreenFlashlightController;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v4, types: [android.content.BroadcastReceiver, com.android.systemui.qs.tiles.FlashlightTile$1] */
    public FlashlightTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, FlashlightController flashlightController, SettingsHelper settingsHelper, BroadcastDispatcher broadcastDispatcher, DisplayLifecycle displayLifecycle) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_flashlight);
        this.mEnable = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_flashlight_on, R.drawable.quick_panel_icon_flashlight_on_030);
        this.mDisable = new QSTileImpl.AnimationIcon(R.drawable.quick_panel_icon_flashlight_off, R.drawable.quick_panel_icon_flashlight_off_030);
        this.mIsLowBattery = false;
        this.mListening = false;
        ?? r3 = new BroadcastReceiver() { // from class: com.android.systemui.qs.tiles.FlashlightTile.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if ("com.sec.android.systemui.action.FLASHLIGHT_OFF".equals(action) || "android.intent.action.ACTION_SHUTDOWN".equals(action)) {
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("onReceive : ", action, FlashlightTile.this.TAG);
                    if (((FlashlightControllerImpl) FlashlightTile.this.mFlashlightController).isEnabled()) {
                        FlashlightTile flashlightTile = FlashlightTile.this;
                        flashlightTile.getClass();
                        ((FlashlightControllerImpl) flashlightTile.mFlashlightController).setFlashlight(false);
                        return;
                    }
                    return;
                }
                if ("android.intent.action.BATTERY_CHANGED".equals(action)) {
                    int intExtra = intent.getIntExtra(ActionResults.RESULT_SET_VOLUME_SUCCESS, 0);
                    int intExtra2 = intent.getIntExtra(IMSParameter.CALL.STATUS, 1);
                    if (intExtra > 5 || intExtra2 == 2) {
                        FlashlightTile.this.mIsLowBattery = false;
                    } else {
                        FlashlightTile flashlightTile2 = FlashlightTile.this;
                        flashlightTile2.mIsLowBattery = true;
                        boolean isEmergencyMode = flashlightTile2.mSettingsHelper.isEmergencyMode();
                        if (((FlashlightControllerImpl) FlashlightTile.this.mFlashlightController).isEnabled() && !isEmergencyMode) {
                            FlashlightTile flashlightTile3 = FlashlightTile.this;
                            flashlightTile3.showWarningMessage(flashlightTile3.mContext.getString(R.string.flash_light_turn_off_by_low_battery));
                            FlashlightTile flashlightTile4 = FlashlightTile.this;
                            flashlightTile4.getClass();
                            ((FlashlightControllerImpl) flashlightTile4.mFlashlightController).setFlashlight(false);
                        }
                    }
                    FlashlightTile flashlightTile5 = FlashlightTile.this;
                    flashlightTile5.mSecFlashlightController.mIsLowBattery = flashlightTile5.mIsLowBattery;
                }
            }
        };
        this.mReceiver = r3;
        this.mFlashlightController = flashlightController;
        flashlightController.observe(((QSTileImpl) this).mLifecycle, this);
        SecFlashlightControllerImpl secFlashlightControllerImpl = ((FlashlightControllerImpl) flashlightController).mSecFlashlightController;
        this.mSecFlashlightController = secFlashlightControllerImpl;
        this.mSettingsHelper = settingsHelper;
        this.mDetailAdapter = new FlashlightDetailAdapter(this, flashlightController, secFlashlightControllerImpl, this.mContext, (QSTile.BooleanState) this.mState, this.mSettingsHelper, this.mIsLowBattery, this.mUiHandler);
        this.mBroadcastDispatcher = broadcastDispatcher;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("com.sec.android.systemui.action.FLASHLIGHT_OFF");
        broadcastDispatcher.registerReceiver(intentFilter, r3);
        if (QpRune.QUICK_SUBSCREEN_PANEL) {
            Context context = ((SubscreenQsPanelController) Dependency.sDependency.getDependencyInner(SubscreenQsPanelController.class)).mContext;
            this.mSubscreenContext = context;
            this.mSubscreenFlashlightController = SubscreenFlashLightController.getInstance(context);
            this.mDisplayLifecycle = displayLifecycle;
        }
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
        return 119;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final ArrayList getSearchWords() {
        ArrayList searchWords = super.getSearchWords();
        if (searchWords != null) {
            for (String str : this.mContext.getResources().getStringArray(R.array.quick_settings_flashlight_proper_noun_search_keywords)) {
                searchWords.add(str);
            }
        }
        return searchWords;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.sec_quick_settings_flashlight_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(final Expandable expandable) {
        DisplayLifecycle displayLifecycle;
        SubscreenFlashLightController subscreenFlashLightController;
        SubscreenQSControllerContract$FlashLightView subscreenQSControllerContract$FlashLightView;
        boolean z;
        Handler handler = this.mUiHandler;
        FlashlightController flashlightController = this.mFlashlightController;
        if (flashlightController != null && ((FlashlightControllerImpl) flashlightController).ensureCameraID() == null) {
            Log.d(this.TAG, "init camera id from handleClick");
            handler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.tiles.FlashlightTile$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FlashlightTile.this.handleClick(expandable);
                }
            }, 200L);
            return;
        }
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isFlashlightTileBlocked()) {
            return;
        }
        if ((flashlightController != null && !((FlashlightControllerImpl) flashlightController).isAvailable()) || ((QSTile.BooleanState) this.mState).state == 0) {
            SecFlashlightControllerImpl secFlashlightControllerImpl = this.mSecFlashlightController;
            synchronized (secFlashlightControllerImpl) {
                z = secFlashlightControllerImpl.mIsThermalRestricted;
            }
            if (z) {
                showWarningMessage(this.mContext.getString(R.string.unable_to_turn_on_by_high_temperature));
                return;
            } else {
                secFlashlightControllerImpl.showUnavailableMessage();
                return;
            }
        }
        if (this.mIsLowBattery) {
            showWarningMessage(this.mContext.getString(R.string.flash_light_disabled_by_low_battery));
            return;
        }
        if (ActivityManager.isUserAMonkey()) {
            return;
        }
        if (!QpRune.QUICK_SUBSCREEN_PANEL || (displayLifecycle = this.mDisplayLifecycle) == null || displayLifecycle.mIsFolderOpened || (subscreenFlashLightController = this.mSubscreenFlashlightController) == null) {
            boolean z2 = !((QSTile.BooleanState) this.mState).value;
            refreshState(Boolean.valueOf(z2));
            if (flashlightController != null) {
                ((FlashlightControllerImpl) flashlightController).setFlashlight(z2);
                return;
            }
            return;
        }
        if ((flashlightController == null || !((FlashlightControllerImpl) flashlightController).isEnabled()) && ((subscreenQSControllerContract$FlashLightView = subscreenFlashLightController.mFlashLightPresentationView) == null || ((SubroomFlashLightSettingsActivity) subscreenQSControllerContract$FlashLightView).getActivityState() == 0)) {
            subscreenFlashLightController.startFlashActivity();
            return;
        }
        ((SubscreenUtil) Dependency.sDependency.getDependencyInner(SubscreenUtil.class)).closeSubscreenPanel();
        if (flashlightController != null) {
            ((FlashlightControllerImpl) flashlightController).setFlashlight(false);
        }
        handler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.FlashlightTile$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                FlashlightTile.this.mSubscreenFlashlightController.finishFlashLightActivity();
            }
        });
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        super.handleDestroy();
        Log.d(this.TAG, "handleDestroy : ");
        this.mBroadcastDispatcher.unregisterReceiver(this.mReceiver);
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSecondaryClick(Expandable expandable) {
        boolean z;
        if (((FlashlightControllerImpl) this.mFlashlightController).isAvailable()) {
            if (this.mIsLowBattery) {
                showWarningMessage(this.mContext.getString(R.string.flash_light_disabled_by_low_battery));
                return;
            } else if (QpRune.QUICK_TILE_FLASHLIGHT_INTENSITY) {
                showDetail(true);
                return;
            } else {
                handleClick(expandable);
                return;
            }
        }
        SecFlashlightControllerImpl secFlashlightControllerImpl = this.mSecFlashlightController;
        synchronized (secFlashlightControllerImpl) {
            z = secFlashlightControllerImpl.mIsThermalRestricted;
        }
        if (z) {
            showWarningMessage(this.mContext.getString(R.string.unable_to_turn_on_by_high_temperature));
        } else {
            secFlashlightControllerImpl.showUnavailableMessage();
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        if (z) {
            ((FlashlightControllerImpl) this.mFlashlightController).ensureCameraID();
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        if (obj instanceof Boolean) {
            boolean booleanValue = ((Boolean) obj).booleanValue();
            if (booleanValue == booleanState.value) {
                return;
            } else {
                booleanState.value = booleanValue;
            }
        } else {
            booleanState.value = ((FlashlightControllerImpl) this.mFlashlightController).isEnabled();
        }
        booleanState.label = this.mHost.getContext().getString(R.string.sec_quick_settings_flashlight_label);
        booleanState.dualTarget = true;
        boolean z = booleanState.value;
        booleanState.state = z ? 2 : 1;
        QSTileImpl.AnimationIcon animationIcon = this.mDisable;
        QSTileImpl.AnimationIcon animationIcon2 = this.mEnable;
        booleanState.icon = z ? animationIcon2 : animationIcon;
        if (!z) {
            animationIcon = animationIcon2;
        }
        booleanState.nextIcon = animationIcon;
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final boolean isAvailable() {
        if (((FlashlightControllerImpl) this.mFlashlightController).mHasFlashlight) {
            if (!this.mHost.shouldBeHiddenByKnox(this.mTileSpec)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    @Override // com.android.systemui.statusbar.policy.FlashlightController.FlashlightListener
    public final void onFlashlightAvailabilityChanged(boolean z) {
        refreshState(null);
    }

    @Override // com.android.systemui.statusbar.policy.FlashlightController.FlashlightListener
    public final void onFlashlightChanged(boolean z) {
        refreshState(Boolean.valueOf(z));
        FlashlightDetailAdapter flashlightDetailAdapter = this.mDetailAdapter;
        if (flashlightDetailAdapter != null) {
            fireToggleStateChanged(z);
            flashlightDetailAdapter.mUiHandler.post(flashlightDetailAdapter.new AnonymousClass2(z));
        }
    }

    @Override // com.android.systemui.statusbar.policy.FlashlightController.FlashlightListener
    public final void onFlashlightError() {
        refreshState(Boolean.FALSE);
    }

    public final void showWarningMessage(CharSequence charSequence) {
        DisplayLifecycle displayLifecycle;
        boolean z = QpRune.QUICK_SUBSCREEN_PANEL;
        if (z) {
            Toast.makeText((!z || (displayLifecycle = this.mDisplayLifecycle) == null || displayLifecycle.mIsFolderOpened) ? this.mContext : this.mSubscreenContext, charSequence, 0).show();
        } else {
            Toast.makeText(this.mContext, charSequence, 0).show();
        }
    }
}
