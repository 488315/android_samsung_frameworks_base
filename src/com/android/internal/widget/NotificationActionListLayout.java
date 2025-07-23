package com.android.internal.widget;

import android.app.Flags;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.RippleDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.widget.NotificationActionListLayout;
import java.util.ArrayList;
import java.util.Comparator;

@RemoteViews.RemoteView
/* loaded from: classes6.dex */
public class NotificationActionListLayout extends LinearLayout {
    public static final Comparator<TextViewInfo> MEASURE_ORDER_COMPARATOR = new Comparator() { // from class: com.android.internal.widget.NotificationActionListLayout$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return NotificationActionListLayout.lambda$static$0((NotificationActionListLayout.TextViewInfo) obj, (NotificationActionListLayout.TextViewInfo) obj2);
        }
    };
    private static final String TAG = "NotificationActionListLayout";
    private int mCollapsibleIndentDimen;
    private int mDefaultPaddingBottom;
    private int mDefaultPaddingTop;
    private int mEmphasizedHeight;
    private boolean mEmphasizedMode;
    private int mEmphasizedPaddingBottom;
    private int mEmphasizedPaddingTop;
    private boolean mEvenlyDividedMode;
    private int mExtraStartPadding;
    private final int mGravity;
    private ArrayList<View> mMeasureOrderOther;
    private ArrayList<TextViewInfo> mMeasureOrderTextViews;
    int mNumNotGoneChildren;
    int mNumPriorityChildren;
    private int mRegularHeight;
    private int mTotalWidth;

    public NotificationActionListLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NotificationActionListLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public NotificationActionListLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTotalWidth = 0;
        this.mExtraStartPadding = 0;
        this.mMeasureOrderTextViews = new ArrayList<>();
        this.mMeasureOrderOther = new ArrayList<>();
        this.mCollapsibleIndentDimen = Flags.notificationsRedesignTemplates() ? R.dimen.notification_2025_actions_margin_start : R.dimen.notification_actions_padding_start;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{16842927}, i, i2);
        this.mGravity = obtainStyledAttributes.getInt(0, 0);
        obtainStyledAttributes.recycle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isPriority(View view) {
        return (view instanceof EmphasizedNotificationButton) && ((EmphasizedNotificationButton) view).isPriority();
    }

    private void countAndRebuildMeasureOrder() {
        boolean z;
        int childCount = getChildCount();
        this.mNumNotGoneChildren = 0;
        this.mNumPriorityChildren = 0;
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            z = true;
            if (i >= childCount) {
                break;
            }
            View childAt = getChildAt(i);
            if (childAt instanceof TextView) {
                i2++;
            } else {
                i3++;
            }
            if (childAt.getVisibility() != 8) {
                this.mNumNotGoneChildren++;
                if (isPriority(childAt)) {
                    this.mNumPriorityChildren++;
                }
            }
            i++;
        }
        boolean z2 = (i2 == this.mMeasureOrderTextViews.size() && i3 == this.mMeasureOrderOther.size()) ? false : true;
        if (!z2) {
            int size = this.mMeasureOrderTextViews.size();
            for (int i4 = 0; i4 < size; i4++) {
                if (this.mMeasureOrderTextViews.get(i4).needsRebuild()) {
                    break;
                }
            }
        }
        z = z2;
        if (z) {
            rebuildMeasureOrder(i2, i3);
        }
    }

    private int measureAndReturnEvenlyDividedWidth(int i, int i2) {
        int childCount = getChildCount();
        int i3 = 0;
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            if (childAt.getVisibility() != 8) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
                i3 += marginLayoutParams.leftMargin + marginLayoutParams.rightMargin;
            }
        }
        int i5 = i2 - i3;
        int i6 = i5 / this.mNumNotGoneChildren;
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i6, 1073741824);
        Log.v(TAG, "measuring evenly divided width: numChildren = " + childCount + ", innerWidth = " + i2 + "px, childMarginSum = " + i3 + "px, innerWidthMinusChildMargins = " + i5 + "px, childWidth = " + i6 + "px, childWidthMeasureSpec = " + View.MeasureSpec.toString(makeMeasureSpec));
        for (int i7 = 0; i7 < childCount; i7++) {
            View childAt2 = getChildAt(i7);
            if (childAt2.getVisibility() != 8) {
                childAt2.measure(makeMeasureSpec, i);
            }
        }
        return i2;
    }

    private int measureAndGetUsedWidth(int i, int i2, int i3, boolean z) {
        boolean z2;
        View view;
        int i4;
        int childCount = getChildCount();
        boolean z3 = View.MeasureSpec.getMode(i) != 0;
        int size = this.mMeasureOrderOther.size();
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        for (int i9 = 0; i9 < childCount; i9++) {
            if (i9 < size) {
                view = this.mMeasureOrderOther.get(i9);
                z2 = false;
            } else {
                TextViewInfo textViewInfo = this.mMeasureOrderTextViews.get(i9 - size);
                TextView textView = textViewInfo.mTextView;
                z2 = textViewInfo.mIsPriority;
                view = textView;
            }
            if (view.getVisibility() != 8) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                if (z3) {
                    int i10 = i3 - i6;
                    int i11 = this.mNumNotGoneChildren - i7;
                    int i12 = i10 / i11;
                    if (z2 && z) {
                        if (i5 == 0) {
                            i5 = getResources().getDimensionPixelSize(R.dimen.notification_actions_collapsed_priority_width);
                        }
                        i12 = marginLayoutParams.leftMargin + i5 + marginLayoutParams.rightMargin;
                    } else if (z2) {
                        int i13 = this.mNumPriorityChildren - i8;
                        i12 = (i10 - (((i11 - i13) * i3) / 4)) / i13;
                    }
                    i4 = i3 - i12;
                } else {
                    i4 = i6;
                }
                int i14 = i5;
                View view2 = view;
                measureChildWithMargins(view2, i, i4, i2, 0);
                i6 += view2.getMeasuredWidth() + marginLayoutParams.rightMargin + marginLayoutParams.leftMargin;
                i7++;
                if (z2) {
                    i8++;
                }
                i5 = i14;
            }
        }
        int dimensionPixelOffset = this.mCollapsibleIndentDimen == 0 ? 0 : getResources().getDimensionPixelOffset(this.mCollapsibleIndentDimen);
        if (i3 - i6 > dimensionPixelOffset) {
            this.mExtraStartPadding = dimensionPixelOffset;
        } else {
            this.mExtraStartPadding = 0;
        }
        if (this.mEmphasizedMode) {
            this.mExtraStartPadding = 0;
        }
        return i6;
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int measureAndGetUsedWidth;
        countAndRebuildMeasureOrder();
        int size = (View.MeasureSpec.getSize(i) - this.mPaddingLeft) - this.mPaddingRight;
        if (this.mEvenlyDividedMode) {
            measureAndGetUsedWidth = measureAndReturnEvenlyDividedWidth(i2, size);
        } else {
            int measureAndGetUsedWidth2 = measureAndGetUsedWidth(i, i2, size, false);
            measureAndGetUsedWidth = (this.mNumPriorityChildren == 0 || measureAndGetUsedWidth2 < size) ? measureAndGetUsedWidth2 : measureAndGetUsedWidth(i, i2, size, true);
        }
        this.mTotalWidth = measureAndGetUsedWidth + this.mPaddingRight + this.mPaddingLeft + this.mExtraStartPadding;
        setMeasuredDimension(resolveSize(getSuggestedMinimumWidth(), i), resolveSize(getSuggestedMinimumHeight(), i2));
    }

    private void rebuildMeasureOrder(int i, int i2) {
        clearMeasureOrder();
        this.mMeasureOrderTextViews.ensureCapacity(i);
        this.mMeasureOrderOther.ensureCapacity(i2);
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (childAt instanceof TextView) {
                TextView textView = (TextView) childAt;
                if (textView.getText().length() > 0) {
                    this.mMeasureOrderTextViews.add(new TextViewInfo(textView));
                }
            }
            this.mMeasureOrderOther.add(childAt);
        }
        this.mMeasureOrderTextViews.sort(MEASURE_ORDER_COMPARATOR);
    }

    private void clearMeasureOrder() {
        this.mMeasureOrderOther.clear();
        this.mMeasureOrderTextViews.clear();
    }

    @Override // android.view.ViewGroup
    public void onViewAdded(View view) {
        super.onViewAdded(view);
        clearMeasureOrder();
        if (view.getBackground() instanceof RippleDrawable) {
            ((RippleDrawable) view.getBackground()).setForceSoftware(true);
        }
    }

    @Override // android.view.ViewGroup
    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        clearMeasureOrder();
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        boolean isLayoutRtl = isLayoutRtl();
        int i7 = this.mPaddingTop;
        int i8 = 1;
        if ((this.mGravity & 1) != 0) {
            i5 = ((this.mPaddingLeft + i) + ((i3 - i) / 2)) - (this.mTotalWidth / 2);
        } else {
            int i9 = this.mPaddingLeft;
            if (Gravity.getAbsoluteGravity(Gravity.START, getLayoutDirection()) == 5) {
                i5 = i9 + ((i3 - i) - this.mTotalWidth);
            } else {
                i5 = i9 + this.mExtraStartPadding;
            }
        }
        int i10 = ((i4 - i2) - i7) - this.mPaddingBottom;
        int childCount = getChildCount();
        if (isLayoutRtl) {
            i6 = childCount - 1;
            i8 = -1;
            i5 = 0;
        } else {
            i6 = 0;
        }
        for (int i11 = 0; i11 < childCount; i11++) {
            View childAt = getChildAt((i8 * i11) + i6);
            if (childAt.getVisibility() != 8) {
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
                int i12 = ((((i10 - measuredHeight) / 2) + i7) + marginLayoutParams.topMargin) - marginLayoutParams.bottomMargin;
                int i13 = i5 + marginLayoutParams.leftMargin;
                childAt.layout(i13, i12, i13 + measuredWidth, measuredHeight + i12);
                i5 = i13 + measuredWidth + marginLayoutParams.rightMargin;
            }
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (Flags.notificationsRedesignTemplates()) {
            return;
        }
        this.mDefaultPaddingBottom = getPaddingBottom();
        this.mDefaultPaddingTop = getPaddingTop();
        updateHeights();
    }

    private void updateHeights() {
        if (Flags.notificationsRedesignTemplates()) {
            return;
        }
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.button_inset_vertical_material);
        this.mEmphasizedPaddingTop = getResources().getDimensionPixelSize(R.dimen.notification_content_margin) - dimensionPixelSize;
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.notification_content_margin_end) - dimensionPixelSize;
        this.mEmphasizedPaddingBottom = dimensionPixelSize2;
        this.mEmphasizedHeight = this.mEmphasizedPaddingTop + dimensionPixelSize2 + getResources().getDimensionPixelSize(R.dimen.notification_action_emphasized_height);
        this.mRegularHeight = getResources().getDimensionPixelSize(R.dimen.notification_action_list_height);
    }

    @RemotableViewMethod
    public void setCollapsibleIndentDimen(int i) {
        if (this.mCollapsibleIndentDimen != i) {
            this.mCollapsibleIndentDimen = i;
            requestLayout();
        }
    }

    @RemotableViewMethod
    public void setEvenlyDividedMode(boolean z) {
        if (z && !Flags.evenlyDividedCallStyleActionLayout()) {
            Log.e(TAG, "setEvenlyDividedMode(true) called with new action layout disabled; leaving evenly divided mode disabled");
            return;
        }
        if (z == this.mEvenlyDividedMode) {
            return;
        }
        Log.v(TAG, "evenlyDividedMode changed to " + z + "; requesting layout");
        this.mEvenlyDividedMode = z;
        requestLayout();
    }

    @RemotableViewMethod
    public void setEmphasizedMode(boolean z) {
        int i;
        if (Flags.notificationsRedesignTemplates()) {
            return;
        }
        this.mEmphasizedMode = z;
        setShowDividers(z ? 0 : 2);
        if (z) {
            setPaddingRelative(getPaddingStart(), this.mEmphasizedPaddingTop, getPaddingEnd(), this.mEmphasizedPaddingBottom);
            setMinimumHeight(this.mEmphasizedHeight);
            i = -2;
        } else {
            setPaddingRelative(getPaddingStart(), this.mDefaultPaddingTop, getPaddingEnd(), this.mDefaultPaddingBottom);
            i = this.mRegularHeight;
        }
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = i;
        setLayoutParams(layoutParams);
    }

    public boolean isEmphasizedMode() {
        return this.mEmphasizedMode;
    }

    public int getExtraMeasureHeight() {
        if (Flags.notificationsRedesignTemplates() || !this.mEmphasizedMode) {
            return 0;
        }
        return this.mEmphasizedHeight - this.mRegularHeight;
    }

    static /* synthetic */ int lambda$static$0(TextViewInfo textViewInfo, TextViewInfo textViewInfo2) {
        int i = -Boolean.compare(textViewInfo.mIsPriority, textViewInfo2.mIsPriority);
        return i != 0 ? i : Integer.compare(textViewInfo.mTextLength, textViewInfo2.mTextLength);
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class TextViewInfo {
        final boolean mIsPriority;
        final int mTextLength;
        final TextView mTextView;

        TextViewInfo(TextView textView) {
            this.mIsPriority = NotificationActionListLayout.isPriority(textView);
            this.mTextLength = textView.getText().length();
            this.mTextView = textView;
        }

        boolean needsRebuild() {
            return (this.mTextView.getText().length() == this.mTextLength && NotificationActionListLayout.isPriority(this.mTextView) == this.mIsPriority) ? false : true;
        }
    }
}
