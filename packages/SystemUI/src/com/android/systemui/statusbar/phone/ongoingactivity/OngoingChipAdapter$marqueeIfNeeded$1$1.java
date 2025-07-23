package com.android.systemui.statusbar.phone.ongoingactivity;

import android.util.Log;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingChipAdapter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class OngoingChipAdapter$marqueeIfNeeded$1$1 {
    public final /* synthetic */ OngoingChipAdapter.MarqueeTextView $marqueeTextView;
    public final /* synthetic */ OngoingChipAdapter this$0;

    public OngoingChipAdapter$marqueeIfNeeded$1$1(OngoingChipAdapter.MarqueeTextView marqueeTextView, OngoingChipAdapter ongoingChipAdapter) {
        this.$marqueeTextView = marqueeTextView;
        this.this$0 = ongoingChipAdapter;
    }

    public final void onMarqueeEnd() {
        OngoingChipAdapter.MarqueeTextView marqueeTextView = this.$marqueeTextView;
        boolean isSelected = marqueeTextView.isSelected();
        OngoingChipAdapter ongoingChipAdapter = this.this$0;
        if (isSelected) {
            Log.i(ongoingChipAdapter.TAG, "onMarqueeEnd");
            marqueeTextView.setSelected(false);
        }
        if (ongoingChipAdapter.marqueeState == OngoingChipAdapter.MarqueeState.WAIT_FINISH) {
            Log.i(ongoingChipAdapter.TAG, "onMarqueeEnd. WAIT_FINISH. --> call notifyDataSetChanged");
            ongoingChipAdapter.notifyDataSetChanged();
        }
        ongoingChipAdapter.marqueeState = OngoingChipAdapter.MarqueeState.INIT;
    }
}
