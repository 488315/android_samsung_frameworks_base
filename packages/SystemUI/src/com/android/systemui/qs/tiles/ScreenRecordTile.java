package com.android.systemui.qs.tiles;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.mediaprojection.MediaProjectionMetricsLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ScreenRecordTile extends QSTileImpl implements RecordingController.RecordingStateChangeCallback {
    public final RecordingController mController;
    public final DialogTransitionAnimator mDialogTransitionAnimator;
    public final FeatureFlags mFlags;
    public final KeyguardDismissUtil mKeyguardDismissUtil;
    public final KeyguardStateController mKeyguardStateController;
    public final MediaProjectionMetricsLogger mMediaProjectionMetricsLogger;
    public long mMillisUntilFinished;
    public final PanelInteractor mPanelInteractor;
    public final UserContextProvider mUserContextProvider;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Callback implements RecordingController.RecordingStateChangeCallback {
        public /* synthetic */ Callback(ScreenRecordTile screenRecordTile, int i) {
            this();
        }

        @Override // com.android.systemui.screenrecord.RecordingController.RecordingStateChangeCallback
        public final void onCountdown(long j) {
            ScreenRecordTile screenRecordTile = ScreenRecordTile.this;
            screenRecordTile.mMillisUntilFinished = j;
            screenRecordTile.refreshState(null);
        }

        @Override // com.android.systemui.screenrecord.RecordingController.RecordingStateChangeCallback
        public final void onCountdownEnd() {
            ScreenRecordTile.this.refreshState(null);
        }

        @Override // com.android.systemui.screenrecord.RecordingController.RecordingStateChangeCallback
        public final void onRecordingEnd() {
            ScreenRecordTile.this.refreshState(null);
        }

        @Override // com.android.systemui.screenrecord.RecordingController.RecordingStateChangeCallback
        public final void onRecordingStart() {
            ScreenRecordTile.this.refreshState(null);
        }

        private Callback() {
        }
    }

    public ScreenRecordTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, FeatureFlags featureFlags, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, RecordingController recordingController, KeyguardDismissUtil keyguardDismissUtil, KeyguardStateController keyguardStateController, DialogTransitionAnimator dialogTransitionAnimator, PanelInteractor panelInteractor, MediaProjectionMetricsLogger mediaProjectionMetricsLogger, UserContextProvider userContextProvider) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        Callback callback = new Callback(this, 0);
        this.mMillisUntilFinished = 0L;
        this.mController = recordingController;
        recordingController.getClass();
        recordingController.observe(this.mLifecycle, callback);
        this.mFlags = featureFlags;
        this.mKeyguardDismissUtil = keyguardDismissUtil;
        this.mKeyguardStateController = keyguardStateController;
        this.mDialogTransitionAnimator = dialogTransitionAnimator;
        this.mPanelInteractor = panelInteractor;
        this.mMediaProjectionMetricsLogger = mediaProjectionMetricsLogger;
        this.mUserContextProvider = userContextProvider;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_screen_record_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(final Expandable expandable) {
        RecordingController recordingController = this.mController;
        if (recordingController.mIsStarting) {
            Log.d("ScreenRecordTile", "Cancelling countdown");
            recordingController.cancelCountdown$1();
        } else if (recordingController.isRecording()) {
            recordingController.stopRecording$1();
        } else {
            this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    final boolean z;
                    final ScreenRecordTile screenRecordTile = ScreenRecordTile.this;
                    final Expandable expandable2 = expandable;
                    if (expandable2 == null) {
                        screenRecordTile.getClass();
                    } else if (!((KeyguardStateControllerImpl) screenRecordTile.mKeyguardStateController).mShowing) {
                        z = true;
                        Runnable runnable = new Runnable() { // from class: com.android.systemui.qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                ScreenRecordTile screenRecordTile2 = ScreenRecordTile.this;
                                screenRecordTile2.mDialogTransitionAnimator.disableAllCurrentDialogsExitAnimations();
                                screenRecordTile2.mPanelInteractor.collapsePanels();
                            }
                        };
                        Context context = screenRecordTile.mContext;
                        final SystemUIDialog createScreenRecordDialog = screenRecordTile.mController.createScreenRecordDialog(screenRecordTile.mFlags, runnable);
                        screenRecordTile.mKeyguardDismissUtil.executeWhenUnlocked(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda2
                            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                            public final boolean onDismiss() {
                                Dialog dialog = createScreenRecordDialog;
                                ScreenRecordTile screenRecordTile2 = ScreenRecordTile.this;
                                screenRecordTile2.getClass();
                                if (z) {
                                    DialogTransitionAnimator.Controller dialogTransitionController = expandable2.dialogTransitionController(new DialogCuj(58, "screen_record"));
                                    if (dialogTransitionController != null) {
                                        screenRecordTile2.mDialogTransitionAnimator.show(dialog, dialogTransitionController, true);
                                    } else {
                                        dialog.show();
                                    }
                                } else {
                                    dialog.show();
                                }
                                screenRecordTile2.mMediaProjectionMetricsLogger.notifyPermissionRequestDisplayed(((UserTrackerImpl) screenRecordTile2.mUserContextProvider).getUserContext().getUserId());
                                return false;
                            }
                        }, false, true);
                    }
                    z = false;
                    Runnable runnable2 = new Runnable() { // from class: com.android.systemui.qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ScreenRecordTile screenRecordTile2 = ScreenRecordTile.this;
                            screenRecordTile2.mDialogTransitionAnimator.disableAllCurrentDialogsExitAnimations();
                            screenRecordTile2.mPanelInteractor.collapsePanels();
                        }
                    };
                    Context context2 = screenRecordTile.mContext;
                    final SystemUIDialog createScreenRecordDialog2 = screenRecordTile.mController.createScreenRecordDialog(screenRecordTile.mFlags, runnable2);
                    screenRecordTile.mKeyguardDismissUtil.executeWhenUnlocked(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda2
                        @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                        public final boolean onDismiss() {
                            Dialog dialog = createScreenRecordDialog2;
                            ScreenRecordTile screenRecordTile2 = ScreenRecordTile.this;
                            screenRecordTile2.getClass();
                            if (z) {
                                DialogTransitionAnimator.Controller dialogTransitionController = expandable2.dialogTransitionController(new DialogCuj(58, "screen_record"));
                                if (dialogTransitionController != null) {
                                    screenRecordTile2.mDialogTransitionAnimator.show(dialog, dialogTransitionController, true);
                                } else {
                                    dialog.show();
                                }
                            } else {
                                dialog.show();
                            }
                            screenRecordTile2.mMediaProjectionMetricsLogger.notifyPermissionRequestDisplayed(((UserTrackerImpl) screenRecordTile2.mUserContextProvider).getUserContext().getUserId());
                            return false;
                        }
                    }, false, true);
                }
            });
        }
        refreshState(null);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        RecordingController recordingController = this.mController;
        boolean z = recordingController.mIsStarting;
        boolean isRecording = recordingController.isRecording();
        booleanState.value = isRecording || z;
        booleanState.state = (isRecording || z) ? 2 : 1;
        booleanState.label = this.mContext.getString(R.string.quick_settings_screen_record_label);
        booleanState.icon = QSTileImpl.ResourceIcon.get(booleanState.value ? R.drawable.qs_screen_record_icon_on : R.drawable.qs_screen_record_icon_off);
        booleanState.forceExpandIcon = booleanState.state == 1;
        if (isRecording) {
            booleanState.secondaryLabel = this.mContext.getString(R.string.quick_settings_screen_record_stop);
        } else if (z) {
            booleanState.secondaryLabel = String.format("%d...", Integer.valueOf((int) Math.floorDiv(this.mMillisUntilFinished + 500, 1000)));
        } else {
            booleanState.secondaryLabel = this.mContext.getString(R.string.quick_settings_screen_record_start);
        }
        booleanState.contentDescription = TextUtils.isEmpty(booleanState.secondaryLabel) ? booleanState.label : TextUtils.concat(booleanState.label, ", ", booleanState.secondaryLabel);
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        QSTile.BooleanState booleanState = new QSTile.BooleanState();
        booleanState.label = this.mContext.getString(R.string.quick_settings_screen_record_label);
        booleanState.handlesLongClick = false;
        return booleanState;
    }
}
