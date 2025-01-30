package com.google.android.setupcompat.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.FooterActionButton;
import com.google.android.setupcompat.util.Logger;
import java.util.ArrayList;
import java.util.Collections;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ButtonBarLayout extends LinearLayout {
    public static final Logger LOG = new Logger((Class<?>) ButtonBarLayout.class);
    public int originalPaddingLeft;
    public int originalPaddingRight;
    public boolean stacked;

    public ButtonBarLayout(Context context) {
        super(context);
        this.stacked = false;
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int i3;
        boolean z;
        int size = View.MeasureSpec.getSize(i);
        boolean z2 = false;
        setStacked(false);
        boolean z3 = true;
        if (View.MeasureSpec.getMode(i) == 1073741824) {
            i3 = View.MeasureSpec.makeMeasureSpec(0, 0);
            z = true;
        } else {
            i3 = i;
            z = false;
        }
        super.onMeasure(i3, i2);
        Context context = getContext();
        int childCount = getChildCount();
        int i4 = 0;
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if ((childAt instanceof FooterActionButton) && ((FooterActionButton) childAt).isPrimaryButtonStyle) {
                i4++;
            }
        }
        if (i4 == 2 && context.getResources().getConfiguration().smallestScreenWidthDp >= 600 && PartnerConfigHelper.shouldApplyExtendedPartnerConfig(context)) {
            z2 = true;
        }
        if (z2 || getMeasuredWidth() <= size) {
            z3 = z;
        } else {
            setStacked(true);
        }
        if (z3) {
            super.onMeasure(i, i2);
        }
    }

    public final void setStacked(boolean z) {
        if (this.stacked == z) {
            return;
        }
        this.stacked = z;
        int childCount = getChildCount();
        int i = 0;
        boolean z2 = false;
        int i2 = 0;
        while (true) {
            if (i >= childCount) {
                break;
            }
            View childAt = getChildAt(i);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
            if (z) {
                childAt.setTag(R.id.suc_customization_original_weight, Float.valueOf(layoutParams.weight));
                layoutParams.weight = 0.0f;
                layoutParams.leftMargin = 0;
            } else {
                Float f = (Float) childAt.getTag(R.id.suc_customization_original_weight);
                if (f != null) {
                    layoutParams.weight = f.floatValue();
                    z2 = z2;
                } else {
                    z2 = true;
                }
                if ((childAt instanceof FooterActionButton) && ((FooterActionButton) childAt).isPrimaryButtonStyle) {
                    i2++;
                }
            }
            childAt.setLayoutParams(layoutParams);
            i++;
            z2 = z2;
        }
        setOrientation(z ? 1 : 0);
        if (z2) {
            LOG.m239w("Reorder the FooterActionButtons in the container");
            boolean z3 = i2 <= 1;
            ArrayList arrayList = new ArrayList();
            if (z3) {
                arrayList.addAll(Collections.nCopies(3, null));
            }
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt2 = getChildAt(i3);
                if (z3) {
                    boolean z4 = childAt2 instanceof FooterActionButton;
                    if (z4 && ((FooterActionButton) childAt2).isPrimaryButtonStyle) {
                        arrayList.set(2, childAt2);
                    } else if (z4) {
                        arrayList.set(0, childAt2);
                    } else {
                        arrayList.set(1, childAt2);
                    }
                } else if (childAt2 instanceof FooterActionButton) {
                    arrayList.add(getChildAt(i3));
                } else {
                    arrayList.add(1, childAt2);
                }
            }
            for (int i4 = 0; i4 < childCount; i4++) {
                View view = (View) arrayList.get(i4);
                if (view != null) {
                    bringChildToFront(view);
                }
            }
        } else {
            for (int i5 = childCount - 1; i5 >= 0; i5--) {
                bringChildToFront(getChildAt(i5));
            }
        }
        if (!z) {
            setPadding(this.originalPaddingLeft, getPaddingTop(), this.originalPaddingRight, getPaddingBottom());
            return;
        }
        this.originalPaddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        this.originalPaddingRight = paddingRight;
        int max = Math.max(this.originalPaddingLeft, paddingRight);
        setPadding(max, getPaddingTop(), max, getPaddingBottom());
    }

    public ButtonBarLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.stacked = false;
    }
}
