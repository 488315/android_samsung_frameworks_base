package com.android.systemui.tuner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.widget.ScrollView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class AutoScrollView extends ScrollView {
    public AutoScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.View
    public final boolean onDragEvent(DragEvent dragEvent) {
        if (dragEvent.getAction() == 2) {
            int y = (int) dragEvent.getY();
            int height = getHeight();
            int i = (int) (height * 0.1f);
            if (y < i) {
                scrollBy(0, y - i);
            } else if (y > height - i) {
                scrollBy(0, (y - height) + i);
            }
        }
        return false;
    }
}
