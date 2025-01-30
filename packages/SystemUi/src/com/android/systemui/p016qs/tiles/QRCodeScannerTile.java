package com.android.systemui.p016qs.tiles;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.p016qs.QSHost;
import com.android.systemui.p016qs.QsEventLogger;
import com.android.systemui.p016qs.logging.QSLogger;
import com.android.systemui.p016qs.tileimpl.QSTileImpl;
import com.android.systemui.p016qs.tileimpl.SQSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p013qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qrcodescanner.controller.QRCodeScannerController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class QRCodeScannerTile extends SQSTileImpl {
    public final C22621 mCallback;
    public final CharSequence mLabel;
    public final QRCodeScannerController mQRCodeScannerController;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.qs.tiles.QRCodeScannerTile$1, java.lang.Object] */
    public QRCodeScannerTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, QRCodeScannerController qRCodeScannerController) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mLabel = this.mContext.getString(R.string.qr_code_scanner_title);
        ?? r1 = new QRCodeScannerController.Callback() { // from class: com.android.systemui.qs.tiles.QRCodeScannerTile.1
            @Override // com.android.systemui.qrcodescanner.controller.QRCodeScannerController.Callback
            public final void onQRCodeScannerActivityChanged() {
                QRCodeScannerTile.this.refreshState(null);
            }
        };
        this.mCallback = r1;
        this.mQRCodeScannerController = qRCodeScannerController;
        qRCodeScannerController.observe(((QSTileImpl) this).mLifecycle, r1);
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return null;
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final CharSequence getTileLabel() {
        return this.mLabel;
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleClick(View view) {
        Intent intent = this.mQRCodeScannerController.mIntent;
        if (intent == null) {
            Log.e("QRCodeScanner", "Expected a non-null intent");
        } else {
            this.mActivityStarter.startActivity(intent, true, (ActivityLaunchAnimator.Controller) (view == null ? null : ActivityLaunchAnimator.Controller.fromView(view, 32)), true);
        }
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleDestroy() {
        super.handleDestroy();
        this.mQRCodeScannerController.unregisterQRCodeScannerChangeObservers(0);
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl, com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleInitialize() {
        this.mQRCodeScannerController.registerQRCodeScannerChangeObservers(0);
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        Context context = this.mContext;
        String string = context.getString(R.string.qr_code_scanner_title);
        booleanState.label = string;
        booleanState.contentDescription = string;
        booleanState.icon = QSTileImpl.ResourceIcon.get(R.drawable.ic_qr_code_scanner);
        boolean isAbleToOpenCameraApp = this.mQRCodeScannerController.isAbleToOpenCameraApp();
        booleanState.state = isAbleToOpenCameraApp ? 1 : 0;
        booleanState.secondaryLabel = !isAbleToOpenCameraApp ? context.getString(R.string.qr_code_scanner_updating_secondary_label) : null;
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl, com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final boolean isAvailable() {
        return this.mQRCodeScannerController.isCameraAvailable();
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        QSTile.BooleanState booleanState = new QSTile.BooleanState();
        booleanState.handlesLongClick = false;
        return booleanState;
    }
}
