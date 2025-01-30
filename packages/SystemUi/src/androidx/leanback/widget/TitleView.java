package androidx.leanback.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
