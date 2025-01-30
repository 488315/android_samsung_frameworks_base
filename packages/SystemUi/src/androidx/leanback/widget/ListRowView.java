package androidx.leanback.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ListRowView extends LinearLayout {
    public ListRowView(Context context) {
        this(context, null);
    }

    public ListRowView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ListRowView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater.from(context).inflate(R.layout.lb_list_row, this);
        ((HorizontalGridView) findViewById(R.id.row_content)).mHasFixedSize = false;
        setOrientation(1);
        setDescendantFocusability(262144);
    }
}
