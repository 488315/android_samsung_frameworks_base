package com.android.systemui.qs.tileimpl;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.animation.Expandable;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.DetailAdapter;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.SQSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.QsEventLoggerImpl;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;

public abstract class SQSTileImpl extends QSTileImpl implements SQSTile {
    public static final Object ARG_SHOW_TRANSIENT_ENABLING = new Object();
    public final SHandler mHandler;
    public final LifecycleRegistry mLifecycle;

    public final class SHandler extends QSTileImpl.H {
        protected static final int STALE = 11;

        public SHandler(Looper looper) {
            super(looper);
        }

        @Override // com.android.systemui.qs.tileimpl.QSTileImpl.H, android.os.Handler
        public final void handleMessage(Message message) {
            SQSTileImpl sQSTileImpl = SQSTileImpl.this;
            try {
                int i = message.what;
                int i2 = 0;
                boolean z = true;
                if (i == 13) {
                    if (message.arg1 == 0) {
                        z = false;
                    }
                    while (i2 < sQSTileImpl.mCallbacks.size()) {
                        if (sQSTileImpl.mCallbacks.valueAt(i2) instanceof SQSTile.SCallback) {
                            ((SQSTile.SCallback) sQSTileImpl.mCallbacks.valueAt(i2)).onScanStateChanged(z);
                        }
                        i2++;
                    }
                    return;
                }
                if (i == 99) {
                    Log.d(sQSTileImpl.TAG, "handleShowDetail from Handler is called:++++ ");
                    if (message.arg1 == 0) {
                        z = false;
                    }
                    String str = sQSTileImpl.TAG;
                    Log.d(str, "handleShowDetail is called:++++ ");
                    while (i2 < sQSTileImpl.mCallbacks.size()) {
                        Log.d(str, "onShowDetail(show) is called:++++ ");
                        if (sQSTileImpl.mCallbacks.valueAt(i2) instanceof SQSTile.SCallback) {
                            ((SQSTile.SCallback) sQSTileImpl.mCallbacks.valueAt(i2)).onShowDetail(z);
                        }
                        i2++;
                    }
                    return;
                }
                if (i == 103) {
                    int i3 = message.arg1;
                    int i4 = message.arg2;
                    while (i2 < sQSTileImpl.mCallbacks.size()) {
                        if (sQSTileImpl.mCallbacks.valueAt(i2) instanceof SQSTile.SCallback) {
                            ((SQSTile.SCallback) sQSTileImpl.mCallbacks.valueAt(i2)).onScrollToDetail(i3, i4);
                        }
                        i2++;
                    }
                    return;
                }
                if (i == 101) {
                    sQSTileImpl.mHandler.removeMessages(101);
                    while (i2 < sQSTileImpl.mCallbacks.size()) {
                        if (sQSTileImpl.mCallbacks.valueAt(i2) instanceof SQSTile.SCallback) {
                            ((SQSTile.SCallback) sQSTileImpl.mCallbacks.valueAt(i2)).onUpdateDetail();
                        }
                        i2++;
                    }
                    return;
                }
                if (i != 100) {
                    super.handleMessage(message);
                    return;
                }
                if (message.arg1 == 0) {
                    z = false;
                }
                while (i2 < sQSTileImpl.mCallbacks.size()) {
                    if (sQSTileImpl.mCallbacks.valueAt(i2) instanceof SQSTile.SCallback) {
                        ((SQSTile.SCallback) sQSTileImpl.mCallbacks.valueAt(i2)).onToggleStateChanged(z);
                    }
                    i2++;
                }
            } catch (Throwable th) {
                Log.w(sQSTileImpl.TAG, AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Error in ", null), th);
            }
        }
    }

    public SQSTileImpl(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mLifecycle = new LifecycleRegistry(this);
        this.mHandler = new SHandler(looper);
        ((QsEventLoggerImpl) qsEventLogger).sequence.newInstanceId();
        this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tileimpl.SQSTileImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SQSTileImpl.this.mLifecycle.setCurrentState(Lifecycle.State.CREATED);
            }
        });
    }

    public final void fireScanStateChanged(boolean z) {
        this.mHandler.obtainMessage(13, z ? 1 : 0, 0).sendToTarget();
    }

    public final void fireToggleStateChanged(boolean z) {
        this.mHandler.obtainMessage(100, z ? 1 : 0, 0).sendToTarget();
    }

    @Override // com.android.systemui.plugins.qs.QSTile
    public DetailAdapter getDetailAdapter() {
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleLongClick(Expandable expandable) {
        Intent longClickIntent = getLongClickIntent();
        if (longClickIntent != null) {
            this.mActivityStarter.postStartActivityDismissingKeyguard(longClickIntent, 0, expandable != null ? expandable.activityTransitionController(32) : null);
        } else {
            Log.d(this.TAG, "handleLongClick() : getLongClickIntent is null.");
            handleSecondaryClick(expandable);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSecondaryClick(Expandable expandable) {
        DetailAdapter detailAdapter = getDetailAdapter();
        String str = this.TAG;
        if (detailAdapter != null) {
            Log.d(str, "handleSecondaryClick showDetail(true) is called:++++");
            showDetail(true);
        } else {
            Log.d(str, "handleSecondaryClick normal click is called:++++ ");
            handleClick(expandable);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleStateChanged() {
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            ((QSTile.Callback) this.mCallbacks.valueAt(i)).onStateChanged(this.mState);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void initialize() {
        this.mHandler.sendEmptyMessage(12);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public boolean isAvailable() {
        return !this.mHost.shouldBeHiddenByKnox(this.mTileSpec);
    }

    public final void showDetail(boolean z) {
        Log.d(this.TAG, "showDetail is called:++++");
        this.mHandler.obtainMessage(99, z ? 1 : 0, 0).sendToTarget();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleInitialize() {
    }
}
