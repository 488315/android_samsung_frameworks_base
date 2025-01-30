package com.android.systemui.volume;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SegmentedButtons extends LinearLayout {
    public Object mSelectedValue;
    public static final Typeface REGULAR = Typeface.create("sans-serif", 0);
    public static final Typeface MEDIUM = Typeface.create("sans-serif-medium", 0);

    public SegmentedButtons(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        new View.OnClickListener() { // from class: com.android.systemui.volume.SegmentedButtons.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SegmentedButtons segmentedButtons = SegmentedButtons.this;
                Object tag = view.getTag();
                if (Objects.equals(tag, segmentedButtons.mSelectedValue)) {
                    return;
                }
                segmentedButtons.mSelectedValue = tag;
                for (int i = 0; i < segmentedButtons.getChildCount(); i++) {
                    TextView textView = (TextView) segmentedButtons.getChildAt(i);
                    boolean equals = Objects.equals(segmentedButtons.mSelectedValue, textView.getTag());
                    textView.setSelected(equals);
                    textView.setTypeface(equals ? SegmentedButtons.MEDIUM : SegmentedButtons.REGULAR);
                }
            }
        };
        LayoutInflater.from(context);
        setOrientation(0);
        new ConfigurableTexts(context);
    }
}
