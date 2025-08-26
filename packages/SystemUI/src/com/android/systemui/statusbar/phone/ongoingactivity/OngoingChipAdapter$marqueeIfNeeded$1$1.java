package com.android.systemui.statusbar.phone.ongoingactivity;

import android.util.Log;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingChipAdapter;

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
        boolean zIsSelected = marqueeTextView.isSelected();
        OngoingChipAdapter ongoingChipAdapter = this.this$0;
        if (zIsSelected) {
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
