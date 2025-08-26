package android.app;

import android.animation.LayoutTransition;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.R;

@Deprecated
/* loaded from: classes.dex */
public class FragmentBreadCrumbs extends ViewGroup implements FragmentManager.OnBackStackChangedListener {
    private static final int DEFAULT_GRAVITY = 8388627;
    Activity mActivity;
    LinearLayout mContainer;
    private int mGravity;
    LayoutInflater mInflater;
    private int mLayoutResId;
    int mMaxVisible;
    private OnBreadCrumbClickListener mOnBreadCrumbClickListener;
    private View.OnClickListener mOnClickListener;
    private View.OnClickListener mParentClickListener;
    BackStackRecord mParentEntry;
    private int mTextColor;
    BackStackRecord mTopEntry;

    @Deprecated
    public interface OnBreadCrumbClickListener {
        boolean onBreadCrumbClick(FragmentManager.BackStackEntry backStackEntry, int i);
    }

    public FragmentBreadCrumbs(Context context) {
        this(context, null);
    }

    public FragmentBreadCrumbs(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.fragmentBreadCrumbsStyle);
    }

    public FragmentBreadCrumbs(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public FragmentBreadCrumbs(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mMaxVisible = -1;
        this.mOnClickListener = new View.OnClickListener() { // from class: android.app.FragmentBreadCrumbs.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (view.getTag() instanceof FragmentManager.BackStackEntry) {
                    FragmentManager.BackStackEntry backStackEntry = (FragmentManager.BackStackEntry) view.getTag();
                    if (backStackEntry == FragmentBreadCrumbs.this.mParentEntry) {
                        if (FragmentBreadCrumbs.this.mParentClickListener != null) {
                            FragmentBreadCrumbs.this.mParentClickListener.onClick(view);
                            return;
                        }
                        return;
                    }
                    if (FragmentBreadCrumbs.this.mOnBreadCrumbClickListener != null) {
                        if (FragmentBreadCrumbs.this.mOnBreadCrumbClickListener.onBreadCrumbClick(backStackEntry == FragmentBreadCrumbs.this.mTopEntry ? null : backStackEntry, 0)) {
                            return;
                        }
                    }
                    if (backStackEntry == FragmentBreadCrumbs.this.mTopEntry) {
                        FragmentBreadCrumbs.this.mActivity.getFragmentManager().popBackStack();
                    } else {
                        FragmentBreadCrumbs.this.mActivity.getFragmentManager().popBackStack(backStackEntry.getId(), 0);
                    }
                }
            }
        };
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FragmentBreadCrumbs, i, i2);
        this.mGravity = typedArrayObtainStyledAttributes.getInt(0, DEFAULT_GRAVITY);
        this.mLayoutResId = typedArrayObtainStyledAttributes.getResourceId(2, R.layout.fragment_bread_crumb_item);
        this.mTextColor = typedArrayObtainStyledAttributes.getColor(1, 0);
        typedArrayObtainStyledAttributes.recycle();
    }

    public void setActivity(Activity activity) throws Resources.NotFoundException {
        this.mActivity = activity;
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mInflater = layoutInflater;
        LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(R.layout.fragment_bread_crumbs, (ViewGroup) this, false);
        this.mContainer = linearLayout;
        addView(linearLayout);
        activity.getFragmentManager().addOnBackStackChangedListener(this);
        updateCrumbs();
        setLayoutTransition(new LayoutTransition());
    }

    public void setMaxVisible(int i) {
        if (i < 1) {
            throw new IllegalArgumentException("visibleCrumbs must be greater than zero");
        }
        this.mMaxVisible = i;
    }

    public void setParentTitle(CharSequence charSequence, CharSequence charSequence2, View.OnClickListener onClickListener) throws Resources.NotFoundException {
        this.mParentEntry = createBackStackEntry(charSequence, charSequence2);
        this.mParentClickListener = onClickListener;
        updateCrumbs();
    }

    public void setOnBreadCrumbClickListener(OnBreadCrumbClickListener onBreadCrumbClickListener) {
        this.mOnBreadCrumbClickListener = onBreadCrumbClickListener;
    }

    private BackStackRecord createBackStackEntry(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == null) {
            return null;
        }
        BackStackRecord backStackRecord = new BackStackRecord((FragmentManagerImpl) this.mActivity.getFragmentManager());
        backStackRecord.setBreadCrumbTitle(charSequence);
        backStackRecord.setBreadCrumbShortTitle(charSequence2);
        return backStackRecord;
    }

    public void setTitle(CharSequence charSequence, CharSequence charSequence2) throws Resources.NotFoundException {
        this.mTopEntry = createBackStackEntry(charSequence, charSequence2);
        updateCrumbs();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0067  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        int measuredWidth;
        int measuredWidth2;
        int i5;
        if (getChildCount() == 0) {
            return;
        }
        View childAt = getChildAt(0);
        int i6 = this.mPaddingTop;
        int measuredHeight = (this.mPaddingTop + childAt.getMeasuredHeight()) - this.mPaddingBottom;
        int absoluteGravity = Gravity.getAbsoluteGravity(this.mGravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK, getLayoutDirection());
        if (absoluteGravity == 1) {
            measuredWidth = this.mPaddingLeft + (((this.mRight - this.mLeft) - childAt.getMeasuredWidth()) / 2);
            measuredWidth2 = childAt.getMeasuredWidth();
        } else {
            if (absoluteGravity == 5) {
                i5 = (this.mRight - this.mLeft) - this.mPaddingRight;
                measuredWidth = i5 - childAt.getMeasuredWidth();
                if (measuredWidth < this.mPaddingLeft) {
                    measuredWidth = this.mPaddingLeft;
                }
                if (i5 > (this.mRight - this.mLeft) - this.mPaddingRight) {
                    i5 = (this.mRight - this.mLeft) - this.mPaddingRight;
                }
                childAt.layout(measuredWidth, i6, i5, measuredHeight);
            }
            measuredWidth = this.mPaddingLeft;
            measuredWidth2 = childAt.getMeasuredWidth();
        }
        i5 = measuredWidth2 + measuredWidth;
        if (measuredWidth < this.mPaddingLeft) {
        }
        if (i5 > (this.mRight - this.mLeft) - this.mPaddingRight) {
        }
        childAt.layout(measuredWidth, i6, i5, measuredHeight);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int childCount = getChildCount();
        int iMax = 0;
        int iMax2 = 0;
        int iCombineMeasuredStates = 0;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8) {
                measureChild(childAt, i, i2);
                iMax = Math.max(iMax, childAt.getMeasuredWidth());
                iMax2 = Math.max(iMax2, childAt.getMeasuredHeight());
                iCombineMeasuredStates = combineMeasuredStates(iCombineMeasuredStates, childAt.getMeasuredState());
            }
        }
        setMeasuredDimension(resolveSizeAndState(Math.max(iMax + this.mPaddingLeft + this.mPaddingRight, getSuggestedMinimumWidth()), i, iCombineMeasuredStates), resolveSizeAndState(Math.max(iMax2 + this.mPaddingTop + this.mPaddingBottom, getSuggestedMinimumHeight()), i2, iCombineMeasuredStates << 16));
    }

    @Override // android.app.FragmentManager.OnBackStackChangedListener
    public void onBackStackChanged() throws Resources.NotFoundException {
        updateCrumbs();
    }

    private int getPreEntryCount() {
        return (this.mTopEntry != null ? 1 : 0) + (this.mParentEntry == null ? 0 : 1);
    }

    private FragmentManager.BackStackEntry getPreEntry(int i) {
        BackStackRecord backStackRecord = this.mParentEntry;
        if (backStackRecord != null) {
            return i == 0 ? backStackRecord : this.mTopEntry;
        }
        return this.mTopEntry;
    }

    void updateCrumbs() throws Resources.NotFoundException {
        int i;
        FragmentManager.BackStackEntry backStackEntryAt;
        FragmentManager fragmentManager = this.mActivity.getFragmentManager();
        int backStackEntryCount = fragmentManager.getBackStackEntryCount();
        int preEntryCount = getPreEntryCount();
        int childCount = this.mContainer.getChildCount();
        int i2 = 0;
        while (true) {
            i = backStackEntryCount + preEntryCount;
            if (i2 >= i) {
                break;
            }
            if (i2 < preEntryCount) {
                backStackEntryAt = getPreEntry(i2);
            } else {
                backStackEntryAt = fragmentManager.getBackStackEntryAt(i2 - preEntryCount);
            }
            if (i2 < childCount && this.mContainer.getChildAt(i2).getTag() != backStackEntryAt) {
                for (int i3 = i2; i3 < childCount; i3++) {
                    this.mContainer.removeViewAt(i2);
                }
                childCount = i2;
            }
            if (i2 >= childCount) {
                View viewInflate = this.mInflater.inflate(this.mLayoutResId, (ViewGroup) this, false);
                TextView textView = (TextView) viewInflate.findViewById(16908310);
                textView.lambda$setTextAsync$0(backStackEntryAt.getBreadCrumbTitle());
                textView.setTag(backStackEntryAt);
                textView.setTextColor(this.mTextColor);
                if (i2 == 0) {
                    viewInflate.findViewById(R.id.left_icon).setVisibility(8);
                }
                this.mContainer.addView(viewInflate);
                textView.setOnClickListener(this.mOnClickListener);
            }
            i2++;
        }
        int childCount2 = this.mContainer.getChildCount();
        while (childCount2 > i) {
            this.mContainer.removeViewAt(childCount2 - 1);
            childCount2--;
        }
        int i4 = 0;
        while (i4 < childCount2) {
            View childAt = this.mContainer.getChildAt(i4);
            childAt.findViewById(16908310).setEnabled(i4 < childCount2 + (-1));
            int i5 = this.mMaxVisible;
            if (i5 > 0) {
                childAt.setVisibility(i4 < childCount2 - i5 ? 8 : 0);
                childAt.findViewById(R.id.left_icon).setVisibility((i4 <= childCount2 - this.mMaxVisible || i4 == 0) ? 8 : 0);
            }
            i4++;
        }
    }
}
