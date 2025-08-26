package com.android.systemui.subscreen;

import android.content.Context;
import com.android.systemui.plugins.qs.QSTile;

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
