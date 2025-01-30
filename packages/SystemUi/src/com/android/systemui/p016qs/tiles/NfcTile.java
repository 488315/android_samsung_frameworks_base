package com.android.systemui.p016qs.tiles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.p016qs.QSHost;
import com.android.systemui.p016qs.QsEventLogger;
import com.android.systemui.p016qs.logging.QSLogger;
import com.android.systemui.p016qs.tileimpl.QSTileImpl;
import com.android.systemui.p016qs.tileimpl.SQSTileImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p013qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NfcTile extends SQSTileImpl {
    public NfcAdapter mAdapter;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final QSTile.Icon mIcon;
    public final C22601 mNfcReceiver;

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.qs.tiles.NfcTile$1] */
    public NfcTile(QSHost qSHost, QsEventLogger qsEventLogger, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, BroadcastDispatcher broadcastDispatcher) {
        super(qSHost, qsEventLogger, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mIcon = QSTileImpl.ResourceIcon.get(R.drawable.ic_qs_nfc);
        this.mNfcReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.tiles.NfcTile.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                NfcTile.this.refreshState(null);
            }
        };
        this.mBroadcastDispatcher = broadcastDispatcher;
    }

    public final NfcAdapter getAdapter() {
        if (this.mAdapter == null) {
            try {
                this.mAdapter = NfcAdapter.getDefaultAdapter(this.mContext);
            } catch (UnsupportedOperationException unused) {
                this.mAdapter = null;
            }
        }
        return this.mAdapter;
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final Intent getLongClickIntent() {
        return new Intent("android.settings.NFC_SETTINGS");
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final int getMetricsCategory() {
        return 800;
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final CharSequence getTileLabel() {
        return this.mContext.getString(R.string.quick_settings_nfc_label);
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleClick(View view) {
        if (getAdapter() == null) {
            return;
        }
        if (getAdapter().isEnabled()) {
            getAdapter().disable();
        } else {
            getAdapter().enable();
        }
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl, com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleSetListening(boolean z) {
        super.handleSetListening(z);
        C22601 c22601 = this.mNfcReceiver;
        BroadcastDispatcher broadcastDispatcher = this.mBroadcastDispatcher;
        if (z) {
            broadcastDispatcher.registerReceiver(new IntentFilter("android.nfc.action.ADAPTER_STATE_CHANGED"), c22601);
        } else {
            broadcastDispatcher.unregisterReceiver(c22601);
        }
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleUpdateState(QSTile.State state, Object obj) {
        QSTile.BooleanState booleanState = (QSTile.BooleanState) state;
        int i = 1;
        booleanState.value = getAdapter() != null && getAdapter().isEnabled();
        if (getAdapter() == null) {
            i = 0;
        } else if (booleanState.value) {
            i = 2;
        }
        booleanState.state = i;
        booleanState.icon = this.mIcon;
        booleanState.label = this.mContext.getString(R.string.quick_settings_nfc_label);
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
        booleanState.contentDescription = booleanState.label;
    }

    @Override // com.android.systemui.p016qs.tileimpl.SQSTileImpl, com.android.systemui.p016qs.tileimpl.QSTileImpl, com.android.systemui.plugins.p013qs.QSTile
    public final boolean isAvailable() {
        Context context = this.mContext;
        if (context.getString(R.string.quick_settings_tiles_stock).contains("nfc")) {
            return context.getPackageManager().hasSystemFeature("android.hardware.nfc");
        }
        return false;
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final QSTile.State newTileState() {
        return new QSTile.BooleanState();
    }

    @Override // com.android.systemui.p016qs.tileimpl.QSTileImpl
    public final void handleUserSwitch(int i) {
    }
}
