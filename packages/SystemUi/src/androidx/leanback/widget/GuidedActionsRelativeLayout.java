package androidx.leanback.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.leanback.R$styleable;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
class GuidedActionsRelativeLayout extends RelativeLayout {
    public boolean mInOverride;
    public final float mKeyLinePercent;

    public GuidedActionsRelativeLayout(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.widget.RelativeLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mInOverride = false;
    }

    @Override // android.widget.RelativeLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        View findViewById;
        int size = View.MeasureSpec.getSize(i2);
        if (size > 0 && (findViewById = findViewById(R.id.guidedactions_sub_list)) != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) findViewById.getLayoutParams();
            if (marginLayoutParams.topMargin < 0 && !this.mInOverride) {
                this.mInOverride = true;
            }
            if (this.mInOverride) {
                marginLayoutParams.topMargin = (int) ((this.mKeyLinePercent * size) / 100.0f);
            }
        }
        super.onMeasure(i, i2);
    }

    public GuidedActionsRelativeLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public GuidedActionsRelativeLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mInOverride = false;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(R$styleable.LeanbackGuidedStepTheme);
        float f = obtainStyledAttributes.getFloat(45, 40.0f);
        obtainStyledAttributes.recycle();
        this.mKeyLinePercent = f;
    }
}
