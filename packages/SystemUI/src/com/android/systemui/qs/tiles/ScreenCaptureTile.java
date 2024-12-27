package com.android.systemui.qs.tiles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.animation.Expandable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QsEventLogger;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.qs.tileimpl.SQSTileImpl;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ScreenCaptureTile extends SQSTileImpl {
    public final BroadcastDispatcher mBroadcastDispatcher;
    public boolean mNeedDoScreenCapture;
    public final AnonymousClass1 mReceiver;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v2, types: [android.content.BroadcastReceiver, com.android.systemui.qs.tiles.ScreenCaptureTile$1] */
    public ScreenCaptureTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, BroadcastDispatcher broadcastDispatcher, PanelInteractor panelInteractor) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mNeedDoScreenCapture = false;
        ?? r1 = new BroadcastReceiver() { // from class: com.android.systemui.qs.tiles.ScreenCaptureTile.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Log.d(ScreenCaptureTile.this.TAG, "action:" + action);
                String str = "doScreenCapture";
                if ("com.samsung.systemui.statusbar.COLLAPSED".equals(action)) {
                    final ScreenCaptureTile screenCaptureTile = ScreenCaptureTile.this;
                    if (screenCaptureTile.mNeedDoScreenCapture) {
                        final Intent intent2 = new Intent("com.samsung.android.capture.ScreenshotExecutor");
                        intent2.putExtra("capturedOrigin", 3);
                        new Thread(str) { // from class: com.android.systemui.qs.tiles.ScreenCaptureTile.2
                            @Override // java.lang.Thread, java.lang.Runnable
                            public final void run() {
                                try {
                                    DisplayMetrics displayMetrics = ScreenCaptureTile.this.mContext.getResources().getDisplayMetrics();
                                    Thread.sleep(((float) displayMetrics.widthPixels) / displayMetrics.density > 457.0f ? 370 : 100);
                                    ScreenCaptureTile.this.mContext.sendBroadcast(intent2, "com.samsung.permission.CAPTURE");
                                    Log.d(ScreenCaptureTile.this.TAG, "doScreenCapture Send com.samsung.android.capture.ScreenshotExecutor");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                        ScreenCaptureTile.this.mNeedDoScreenCapture = false;
                        return;
                    }
                    return;
                }
                if ("com.samsung.systemui.statusbar.EXPANDED".equals(action) && ScreenCaptureTile.this.mNeedDoScreenCapture && ((KeyguardStateControllerImpl) ((KeyguardStateController) Dependency.sDependency.getDependencyInner(KeyguardStateController.class))).mShowing) {
                    final ScreenCaptureTile screenCaptureTile2 = ScreenCaptureTile.this;
                    screenCaptureTile2.getClass();
                    final Intent intent3 = new Intent("com.samsung.android.capture.ScreenshotExecutor");
                    intent3.putExtra("capturedOrigin", 3);
                    new Thread(str) { // from class: com.android.systemui.qs.tiles.ScreenCaptureTile.2
                        @Override // java.lang.Thread, java.lang.Runnable
                        public final void run() {
                            try {
                                DisplayMetrics displayMetrics = ScreenCaptureTile.this.mContext.getResources().getDisplayMetrics();
                                Thread.sleep(((float) displayMetrics.widthPixels) / displayMetrics.density > 457.0f ? 370 : 100);
                                ScreenCaptureTile.this.mContext.sendBroadcast(intent3, "com.samsung.permission.CAPTURE");
                                Log.d(ScreenCaptureTile.this.TAG, "doScreenCapture Send com.samsung.android.capture.ScreenshotExecutor");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    ScreenCaptureTile.this.mNeedDoScreenCapture = false;
                }
            }
        };
        this.mReceiver = r1;
        this.mBroadcastDispatcher = broadcastDispatcher;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.systemui.statusbar.COLLAPSED");
        intentFilter.addAction("com.samsung.systemui.statusbar.EXPANDED");
        broadcastDispatcher.registerReceiver(intentFilter, r1);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        Intent intent = new Intent();
        if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_SCREEN_RECORDER")) {
            intent.setClassName("com.samsung.android.app.smartcapture", "com.samsung.android.app.settings.SettingsActivity");
            return intent;
        }
        intent.setClassName("com.samsung.android.app.smartcapture", "com.samsung.android.app.settings.SettingsAliasActivity");
        return intent;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public final int getMetricsCategory() {
        return 112;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_screen_capture_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleClick(Expandable expandable) {
        Log.d(this.TAG, "handleClick : " + ((QSTile.BooleanState) this.mState).value);
        this.mNeedDoScreenCapture = true;
        ((CommandQueue) Dependency.sDependency.getDependencyInner(CommandQueue.class)).animateCollapsePanels();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        super.handleDestroy();
        this.mBroadcastDispatcher.unregisterReceiver(this.mReceiver);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        booleanState.value = false;
        booleanState.state = 1;
        booleanState.dualTarget = true;
        booleanState.label = this.mContext.getString(R.string.quick_settings_screen_capture_label);
        booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.quick_panel_icon_smart_capture);
        booleanState.contentDescription = this.mContext.getString(R.string.quick_settings_screen_capture_label);
    }

    @Override // com.android.systemui.qs.tileimpl.SQSTileImpl, com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile, com.android.systemui.plugins.qs.LockQSTile
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }
}
