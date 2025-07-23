package com.android.systemui.qs.tiles;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
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
import com.android.systemui.qs.flags.QsInCompose;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.screenrecord.data.model.ScreenRecordModel;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ScreenRecordTile extends QSTileImpl implements RecordingController.RecordingStateChangeCallback {
    public final RecordingController mController;
    public final DialogTransitionAnimator mDialogTransitionAnimator;
    public final KeyguardDismissUtil mKeyguardDismissUtil;
    public final KeyguardStateController mKeyguardStateController;
    public final MediaProjectionMetricsLogger mMediaProjectionMetricsLogger;
    public long mMillisUntilFinished;
    public final PanelInteractor mPanelInteractor;
    public final UserContextProvider mUserContextProvider;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        this.mKeyguardDismissUtil = keyguardDismissUtil;
        this.mKeyguardStateController = keyguardStateController;
        this.mDialogTransitionAnimator = dialogTransitionAnimator;
        this.mPanelInteractor = panelInteractor;
        this.mMediaProjectionMetricsLogger = mediaProjectionMetricsLogger;
        this.mUserContextProvider = userContextProvider;
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public final boolean getDetailsViewModel(Consumer consumer) {
        handleClick(new ScreenRecordTile$$ExternalSyntheticLambda0(this, consumer, 0));
        return true;
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
    public final void handleClick(Expandable expandable) {
        handleClick(new ScreenRecordTile$$ExternalSyntheticLambda0(this, expandable, 2));
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
        int i = booleanState.value ? R.drawable.qs_screen_record_icon_on : R.drawable.qs_screen_record_icon_off;
        int i2 = QsInCompose.$r8$clinit;
        booleanState.icon = QSTileImpl.ResourceIcon.get(i);
        booleanState.forceExpandIcon = booleanState.state == 1;
        booleanState.expandedAccessibilityClassName = Button.class.getName();
        if (isRecording) {
            booleanState.secondaryLabel = this.mContext.getString(R.string.quick_settings_screen_record_stop);
            booleanState.expandedAccessibilityClassName = Switch.class.getName();
        } else if (z) {
            ScreenRecordModel.Starting.Companion companion = ScreenRecordModel.Starting.Companion;
            long j = this.mMillisUntilFinished;
            companion.getClass();
            booleanState.secondaryLabel = String.format("%d...", Integer.valueOf((int) Math.floorDiv(j + 500, 1000)));
        } else {
            booleanState.secondaryLabel = this.mContext.getString(R.string.quick_settings_screen_record_start);
        }
        booleanState.contentDescription = TextUtils.isEmpty(booleanState.secondaryLabel) ? booleanState.label : TextUtils.concat(booleanState.label, ", ", booleanState.secondaryLabel);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        QSTile.BooleanState booleanState = new QSTile.BooleanState();
        booleanState.label = this.mContext.getString(R.string.quick_settings_screen_record_label);
        booleanState.handlesLongClick = false;
        return booleanState;
    }

    public final void handleClick(Runnable runnable) {
        RecordingController recordingController = this.mController;
        if (recordingController.mIsStarting) {
            Log.d("ScreenRecordTile", "Cancelling countdown");
            recordingController.cancelCountdown$1();
        } else if (recordingController.isRecording()) {
            recordingController.stopRecording(5);
        } else {
            this.mUiHandler.post(runnable);
        }
        refreshState(null);
    }
}
