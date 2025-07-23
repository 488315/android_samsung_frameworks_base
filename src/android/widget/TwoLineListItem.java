package android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.android.internal.R;

@Deprecated
/* loaded from: classes5.dex */
public class TwoLineListItem extends RelativeLayout {
    private TextView mText1;
    private TextView mText2;

    public TwoLineListItem(Context context) {
        this(context, null, 0);
    }

    public TwoLineListItem(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TwoLineListItem(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public TwoLineListItem(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TwoLineListItem, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.TwoLineListItem, attributeSet, obtainStyledAttributes, i, i2);
        obtainStyledAttributes.recycle();
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mText1 = (TextView) findViewById(16908308);
        this.mText2 = (TextView) findViewById(16908309);
    }

    public TextView getText1() {
        return this.mText1;
    }

    public TextView getText2() {
        return this.mText2;
    }

    @Override // android.widget.RelativeLayout, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return TwoLineListItem.class.getName();
    }
}
