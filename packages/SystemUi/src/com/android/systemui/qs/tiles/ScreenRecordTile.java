package com.android.systemui.qs.tiles;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.screenrecord.ScreenRecordDialog;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScreenRecordTile extends SQSTileImpl implements RecordingController.RecordingStateChangeCallback {
    public final RecordingController mController;
    public final DialogLaunchAnimator mDialogLaunchAnimator;
    public final FeatureFlags mFlags;
    public final KeyguardDismissUtil mKeyguardDismissUtil;
    public final KeyguardStateController mKeyguardStateController;
    public long mMillisUntilFinished;
    public final PanelInteractor mPanelInteractor;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public ScreenRecordTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, FeatureFlags featureFlags, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, RecordingController recordingController, KeyguardDismissUtil keyguardDismissUtil, KeyguardStateController keyguardStateController, DialogLaunchAnimator dialogLaunchAnimator, PanelInteractor panelInteractor) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        Callback callback = new Callback(this, 0);
        this.mMillisUntilFinished = 0L;
        this.mController = recordingController;
        recordingController.getClass();
        recordingController.observe(((QSTileImpl) this).mLifecycle, callback);
        this.mFlags = featureFlags;
        this.mKeyguardDismissUtil = keyguardDismissUtil;
        this.mKeyguardStateController = keyguardStateController;
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
        this.mPanelInteractor = panelInteractor;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_screen_record_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(final View view) {
        boolean z;
        RecordingController recordingController = this.mController;
        if (recordingController.mIsStarting) {
            Log.d("ScreenRecordTile", "Cancelling countdown");
            RecordingController.CountDownTimerC23213 countDownTimerC23213 = recordingController.mCountDownTimer;
            if (countDownTimerC23213 != null) {
                countDownTimerC23213.cancel();
            } else {
                Log.e("RecordingController", "Timer was null");
            }
            recordingController.mIsStarting = false;
            Iterator it = recordingController.mListeners.iterator();
            while (it.hasNext()) {
                ((RecordingController.RecordingStateChangeCallback) it.next()).onCountdownEnd();
            }
        } else {
            synchronized (recordingController) {
                z = recordingController.mIsRecording;
            }
            if (z) {
                recordingController.stopRecording();
            } else {
                this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        final boolean z2;
                        final ScreenRecordTile screenRecordTile = ScreenRecordTile.this;
                        final View view2 = view;
                        if (view2 == null) {
                            screenRecordTile.getClass();
                        } else if (!((KeyguardStateControllerImpl) screenRecordTile.mKeyguardStateController).mShowing) {
                            z2 = true;
                            Runnable runnable = new Runnable() { // from class: com.android.systemui.qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    ScreenRecordTile screenRecordTile2 = ScreenRecordTile.this;
                                    screenRecordTile2.mDialogLaunchAnimator.disableAllCurrentDialogsExitAnimations();
                                    screenRecordTile2.mPanelInteractor.collapsePanels();
                                }
                            };
                            Context context = screenRecordTile.mContext;
                            DialogLaunchAnimator dialogLaunchAnimator = screenRecordTile.mDialogLaunchAnimator;
                            ActivityStarter activityStarter = screenRecordTile.mActivityStarter;
                            RecordingController recordingController2 = screenRecordTile.mController;
                            recordingController2.getClass();
                            Flags flags = Flags.INSTANCE;
                            recordingController2.mFlags.getClass();
                            FeatureFlags featureFlags = screenRecordTile.mFlags;
                            featureFlags.getClass();
                            final ScreenRecordDialog screenRecordDialog = new ScreenRecordDialog(context, recordingController2, activityStarter, recordingController2.mUserContextProvider, featureFlags, dialogLaunchAnimator, runnable);
                            screenRecordTile.mKeyguardDismissUtil.executeWhenUnlocked(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda2
                                @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                                public final boolean onDismiss() {
                                    ScreenRecordTile screenRecordTile2 = ScreenRecordTile.this;
                                    screenRecordTile2.getClass();
                                    Dialog dialog = screenRecordDialog;
                                    if (!z2) {
                                        dialog.show();
                                        return false;
                                    }
                                    DialogCuj dialogCuj = new DialogCuj(58, "screen_record");
                                    View view3 = view2;
                                    DialogLaunchAnimator dialogLaunchAnimator2 = screenRecordTile2.mDialogLaunchAnimator;
                                    dialogLaunchAnimator2.getClass();
                                    DialogLaunchAnimator.showFromView$default(dialogLaunchAnimator2, dialog, view3, dialogCuj, false, 8);
                                    return false;
                                }
                            }, false, true);
                        }
                        z2 = false;
                        Runnable runnable2 = new Runnable() { // from class: com.android.systemui.qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda1
                            @Override // java.lang.Runnable
                            public final void run() {
                                ScreenRecordTile screenRecordTile2 = ScreenRecordTile.this;
                                screenRecordTile2.mDialogLaunchAnimator.disableAllCurrentDialogsExitAnimations();
                                screenRecordTile2.mPanelInteractor.collapsePanels();
                            }
                        };
                        Context context2 = screenRecordTile.mContext;
                        DialogLaunchAnimator dialogLaunchAnimator2 = screenRecordTile.mDialogLaunchAnimator;
                        ActivityStarter activityStarter2 = screenRecordTile.mActivityStarter;
                        RecordingController recordingController22 = screenRecordTile.mController;
                        recordingController22.getClass();
                        Flags flags2 = Flags.INSTANCE;
                        recordingController22.mFlags.getClass();
                        FeatureFlags featureFlags2 = screenRecordTile.mFlags;
                        featureFlags2.getClass();
                        final ScreenRecordDialog screenRecordDialog2 = new ScreenRecordDialog(context2, recordingController22, activityStarter2, recordingController22.mUserContextProvider, featureFlags2, dialogLaunchAnimator2, runnable2);
                        screenRecordTile.mKeyguardDismissUtil.executeWhenUnlocked(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda2
                            @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                            public final boolean onDismiss() {
                                ScreenRecordTile screenRecordTile2 = ScreenRecordTile.this;
                                screenRecordTile2.getClass();
                                Dialog dialog = screenRecordDialog2;
                                if (!z2) {
                                    dialog.show();
                                    return false;
                                }
                                DialogCuj dialogCuj = new DialogCuj(58, "screen_record");
                                View view3 = view2;
                                DialogLaunchAnimator dialogLaunchAnimator22 = screenRecordTile2.mDialogLaunchAnimator;
                                dialogLaunchAnimator22.getClass();
                                DialogLaunchAnimator.showFromView$default(dialogLaunchAnimator22, dialog, view3, dialogCuj, false, 8);
                                return false;
                            }
                        }, false, true);
                    }
                });
            }
        }
        refreshState(null);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        boolean z;
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        RecordingController recordingController = this.mController;
        boolean z2 = recordingController.mIsStarting;
        synchronized (recordingController) {
            z = recordingController.mIsRecording;
        }
        booleanState.value = z || z2;
        booleanState.state = (z || z2) ? 2 : 1;
        Context context = this.mContext;
        booleanState.label = context.getString(R.string.quick_settings_screen_record_label);
        booleanState.icon = QSTileImpl.ResourceIcon.get(booleanState.value ? R.drawable.qs_screen_record_icon_on : R.drawable.qs_screen_record_icon_off);
        booleanState.forceExpandIcon = booleanState.state == 1;
        if (z) {
            booleanState.secondaryLabel = context.getString(R.string.quick_settings_screen_record_stop);
        } else if (z2) {
            booleanState.secondaryLabel = String.format("%d...", Integer.valueOf((int) Math.floorDiv(this.mMillisUntilFinished + 500, 1000)));
        } else {
            booleanState.secondaryLabel = context.getString(R.string.quick_settings_screen_record_start);
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
