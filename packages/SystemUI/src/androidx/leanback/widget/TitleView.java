package androidx.leanback.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class TitleView extends FrameLayout {
    public TitleView(Context context) {
        this(context, null);
    }

    public TitleView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.browseTitleViewStyle);
    }

    public TitleView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        new TitleViewAdapter(this) { // from class: androidx.leanback.widget.TitleView.1
        };
        View inflate = LayoutInflater.from(context).inflate(R.layout.lb_title_view, this);
        setClipToPadding(false);
        setClipChildren(false);
    }
}
