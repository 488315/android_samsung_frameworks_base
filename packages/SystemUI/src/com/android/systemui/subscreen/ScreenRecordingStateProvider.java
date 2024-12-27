package com.android.systemui.subscreen;

import android.content.Context;
import com.android.systemui.plugins.qs.QSTile;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class ScreenRecordingStateProvider implements QSTile.Callback {
    public QSTile.State mTileState;

    public ScreenRecordingStateProvider(Context context) {
    }

    @Override // com.android.systemui.plugins.qs.QSTile.Callback
    public final void onStateChanged(QSTile.State state) {
        this.mTileState = state;
    }
}
