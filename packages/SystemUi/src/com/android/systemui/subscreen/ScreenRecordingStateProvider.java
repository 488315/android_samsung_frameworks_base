package com.android.systemui.subscreen;

import android.content.Context;
import com.android.systemui.log.SecPanelLogger;
import com.android.systemui.log.SecPanelLoggerImpl;
import com.android.systemui.plugins.p013qs.QSTile;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScreenRecordingStateProvider implements QSTile.Callback {
    public final SecPanelLogger mPanelLogger;
    public QSTile.State mTileState;

    public ScreenRecordingStateProvider(Context context, SecPanelLogger secPanelLogger) {
        this.mPanelLogger = secPanelLogger;
    }

    @Override // com.android.systemui.plugins.qs.QSTile.Callback
    public final void onStateChanged(QSTile.State state) {
        this.mTileState = state;
        StringBuilder sb = new StringBuilder("ScreenRecordingStateChanged active: ");
        sb.append(state.state == 2);
        ((SecPanelLoggerImpl) this.mPanelLogger).addCoverPanelStateLog(sb.toString());
    }
}
