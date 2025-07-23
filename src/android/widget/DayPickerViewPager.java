package android.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import com.android.internal.widget.ViewPager;
import java.util.ArrayList;
import java.util.function.Predicate;

/* loaded from: classes5.dex */
class DayPickerViewPager extends ViewPager {
    private final ArrayList<View> mMatchParentChildren;

    public DayPickerViewPager(Context context) {
        this(context, null);
    }

    public DayPickerViewPager(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DayPickerViewPager(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public DayPickerViewPager(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mMatchParentChildren = new ArrayList<>(1);
    }

    @Override // com.android.internal.widget.ViewPager, android.view.View
    protected void onMeasure(int i, int i2) {
        int childMeasureSpec;
        int childMeasureSpec2;
        populate();
        int childCount = getChildCount();
        boolean z = (View.MeasureSpec.getMode(i) == 1073741824 && View.MeasureSpec.getMode(i2) == 1073741824) ? false : true;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (childAt.getVisibility() != 8) {
                measureChild(childAt, i, i2);
                ViewPager.LayoutParams layoutParams = (ViewPager.LayoutParams) childAt.getLayoutParams();
                i3 = Math.max(i3, childAt.getMeasuredWidth());
                i4 = Math.max(i4, childAt.getMeasuredHeight());
                i5 = combineMeasuredStates(i5, childAt.getMeasuredState());
                if (z && (layoutParams.width == -1 || layoutParams.height == -1)) {
                    this.mMatchParentChildren.add(childAt);
                }
            }
        }
        int paddingLeft = i3 + getPaddingLeft() + getPaddingRight();
        int max = Math.max(i4 + getPaddingTop() + getPaddingBottom(), getSuggestedMinimumHeight());
        int max2 = Math.max(paddingLeft, getSuggestedMinimumWidth());
        Drawable foreground = getForeground();
        if (foreground != null) {
            max = Math.max(max, foreground.getMinimumHeight());
            max2 = Math.max(max2, foreground.getMinimumWidth());
        }
        setMeasuredDimension(resolveSizeAndState(max2, i, i5), resolveSizeAndState(max, i2, i5 << 16));
        int size = this.mMatchParentChildren.size();
        if (size > 1) {
            for (int i7 = 0; i7 < size; i7++) {
                View view = this.mMatchParentChildren.get(i7);
                ViewPager.LayoutParams layoutParams2 = (ViewPager.LayoutParams) view.getLayoutParams();
                if (layoutParams2.width == -1) {
                    childMeasureSpec = View.MeasureSpec.makeMeasureSpec((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), 1073741824);
                } else {
                    childMeasureSpec = getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight(), layoutParams2.width);
                }
                if (layoutParams2.height == -1) {
                    childMeasureSpec2 = View.MeasureSpec.makeMeasureSpec((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), 1073741824);
                } else {
                    childMeasureSpec2 = getChildMeasureSpec(i2, getPaddingTop() + getPaddingBottom(), layoutParams2.height);
                }
                view.measure(childMeasureSpec, childMeasureSpec2);
            }
        }
        this.mMatchParentChildren.clear();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected <T extends View> T findViewByPredicateTraversal(Predicate<View> predicate, View view) {
        T t;
        T t2;
        if (predicate.test(this)) {
            return this;
        }
        SimpleMonthView view2 = ((DayPickerPagerAdapter) getAdapter()).getView(getCurrent());
        if (view2 != view && view2 != null && (t2 = (T) view2.findViewByPredicate(predicate)) != null) {
            return t2;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt != view && childAt != view2 && (t = (T) childAt.findViewByPredicate(predicate)) != null) {
                return t;
            }
        }
        return null;
    }
}
