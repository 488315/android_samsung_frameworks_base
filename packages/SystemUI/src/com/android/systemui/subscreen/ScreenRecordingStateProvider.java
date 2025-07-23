package com.android.systemui.subscreen;

import android.content.Context;
import com.android.systemui.plugins.qs.QSTile;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ScreenRecordingStateProvider implements QSTile.Callback {
    public QSTile.State mTileState;

    public ScreenRecordingStateProvider(Context context) {
    }

    @Override // com.android.systemui.plugins.qs.QSTile.Callback
    public final void onStateChanged(QSTile.State state) {
        this.mTileState = state;
    }
}
