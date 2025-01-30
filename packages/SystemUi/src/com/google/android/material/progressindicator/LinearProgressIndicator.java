package com.google.android.material.progressindicator;

import android.content.Context;
import android.util.AttributeSet;
import androidx.core.view.ViewCompat;
import com.android.systemui.R;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LinearProgressIndicator extends BaseProgressIndicator {
    public static final /* synthetic */ int $r8$clinit = 0;

    public LinearProgressIndicator(Context context) {
        this(context, null);
    }

    @Override // com.google.android.material.progressindicator.BaseProgressIndicator
    public final BaseProgressIndicatorSpec createSpec(Context context, AttributeSet attributeSet) {
        return new LinearProgressIndicatorSpec(context, attributeSet);
    }

    @Override // android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        BaseProgressIndicatorSpec baseProgressIndicatorSpec = this.spec;
        LinearProgressIndicatorSpec linearProgressIndicatorSpec = (LinearProgressIndicatorSpec) baseProgressIndicatorSpec;
        boolean z2 = true;
        if (((LinearProgressIndicatorSpec) baseProgressIndicatorSpec).indicatorDirection != 1) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if ((ViewCompat.Api17Impl.getLayoutDirection(this) != 1 || ((LinearProgressIndicatorSpec) this.spec).indicatorDirection != 2) && (ViewCompat.Api17Impl.getLayoutDirection(this) != 0 || ((LinearProgressIndicatorSpec) this.spec).indicatorDirection != 3)) {
                z2 = false;
            }
        }
        linearProgressIndicatorSpec.drawHorizontallyInverse = z2;
    }

    @Override // android.widget.ProgressBar, android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        int paddingRight = i - (getPaddingRight() + getPaddingLeft());
        int paddingBottom = i2 - (getPaddingBottom() + getPaddingTop());
        IndeterminateDrawable indeterminateDrawable = getIndeterminateDrawable();
        if (indeterminateDrawable != null) {
            indeterminateDrawable.setBounds(0, 0, paddingRight, paddingBottom);
        }
        DeterminateDrawable progressDrawable = getProgressDrawable();
        if (progressDrawable != null) {
            progressDrawable.setBounds(0, 0, paddingRight, paddingBottom);
        }
    }

    @Override // com.google.android.material.progressindicator.BaseProgressIndicator
    public final void setProgressCompat(int i, boolean z) {
        BaseProgressIndicatorSpec baseProgressIndicatorSpec = this.spec;
        if (baseProgressIndicatorSpec != null && ((LinearProgressIndicatorSpec) baseProgressIndicatorSpec).indeterminateAnimationType == 0 && isIndeterminate()) {
            return;
        }
        super.setProgressCompat(i, z);
    }

    public LinearProgressIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.linearProgressIndicatorStyle);
    }

    public LinearProgressIndicator(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, 2132019115);
        IndeterminateAnimatorDelegate linearIndeterminateDisjointAnimatorDelegate;
        Context context2 = getContext();
        LinearProgressIndicatorSpec linearProgressIndicatorSpec = (LinearProgressIndicatorSpec) this.spec;
        LinearDrawingDelegate linearDrawingDelegate = new LinearDrawingDelegate(linearProgressIndicatorSpec);
        if (linearProgressIndicatorSpec.indeterminateAnimationType == 0) {
            linearIndeterminateDisjointAnimatorDelegate = new LinearIndeterminateContiguousAnimatorDelegate(linearProgressIndicatorSpec);
        } else {
            linearIndeterminateDisjointAnimatorDelegate = new LinearIndeterminateDisjointAnimatorDelegate(context2, linearProgressIndicatorSpec);
        }
        setIndeterminateDrawable(new IndeterminateDrawable(context2, linearProgressIndicatorSpec, linearDrawingDelegate, linearIndeterminateDisjointAnimatorDelegate));
        Context context3 = getContext();
        LinearProgressIndicatorSpec linearProgressIndicatorSpec2 = (LinearProgressIndicatorSpec) this.spec;
        setProgressDrawable(new DeterminateDrawable(context3, linearProgressIndicatorSpec2, new LinearDrawingDelegate(linearProgressIndicatorSpec2)));
    }
}
