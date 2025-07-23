package androidx.leanback.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RowHeaderView extends TextView {
    public RowHeaderView(Context context) {
        this(context, null);
    }

    public RowHeaderView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.rowHeaderStyle);
    }

    public RowHeaderView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
