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

import java.util.ArrayList;
import java.util.Comparator;

@RemoteViews.RemoteView
public class NotificationActionListLayout extends LinearLayout {
    public static final Comparator<TextViewInfo> MEASURE_ORDER_COMPARATOR =
            new Comparator() { // from class:
                               // com.android.internal.widget.NotificationActionListLayout$$ExternalSyntheticLambda0
                @Override // java.util.Comparator
                public final int compare(Object obj, Object obj2) {
                    return NotificationActionListLayout.lambda$static$0(
                            (NotificationActionListLayout.TextViewInfo) obj,
                            (NotificationActionListLayout.TextViewInfo) obj2);
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

    public NotificationActionListLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NotificationActionListLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public NotificationActionListLayout(
            Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mTotalWidth = 0;
        this.mExtraStartPadding = 0;
        this.mMeasureOrderTextViews = new ArrayList<>();
        this.mMeasureOrderOther = new ArrayList<>();
        this.mCollapsibleIndentDimen = R.dimen.notification_actions_padding_start;
        int[] attrIds = {16842927};
        TypedArray ta = context.obtainStyledAttributes(attrs, attrIds, defStyleAttr, defStyleRes);
        this.mGravity = ta.getInt(0, 0);
        ta.recycle();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isPriority(View actionView) {
        return (actionView instanceof EmphasizedNotificationButton)
                && ((EmphasizedNotificationButton) actionView).isPriority();
    }

    private void countAndRebuildMeasureOrder() {
        int numChildren = getChildCount();
        int textViews = 0;
        int otherViews = 0;
        this.mNumNotGoneChildren = 0;
        this.mNumPriorityChildren = 0;
        for (int i = 0; i < numChildren; i++) {
            View c = getChildAt(i);
            if (c instanceof TextView) {
                textViews++;
            } else {
                otherViews++;
            }
            if (c.getVisibility() != 8) {
                this.mNumNotGoneChildren++;
                if (isPriority(c)) {
                    this.mNumPriorityChildren++;
                }
            }
        }
        int i2 = 0;
        if (textViews != this.mMeasureOrderTextViews.size()
                || otherViews != this.mMeasureOrderOther.size()) {
            i2 = 1;
        }
        if (i2 == 0) {
            int size = this.mMeasureOrderTextViews.size();
            int i3 = 0;
            while (true) {
                if (i3 >= size) {
                    break;
                }
                if (!this.mMeasureOrderTextViews.get(i3).needsRebuild()) {
                    i3++;
                } else {
                    i2 = 1;
                    break;
                }
            }
        }
        if (i2 != 0) {
            rebuildMeasureOrder(textViews, otherViews);
        }
    }

    private int measureAndReturnEvenlyDividedWidth(int heightMeasureSpec, int innerWidth) {
        int numChildren = getChildCount();
        int childMarginSum = 0;
        for (int i = 0; i < numChildren; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != 8) {
                ViewGroup.MarginLayoutParams lp =
                        (ViewGroup.MarginLayoutParams) child.getLayoutParams();
                childMarginSum += lp.leftMargin + lp.rightMargin;
            }
        }
        int i2 = innerWidth - childMarginSum;
        int childWidth = i2 / this.mNumNotGoneChildren;
        int childWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(childWidth, 1073741824);
        Log.v(
                TAG,
                "measuring evenly divided width: numChildren = "
                        + numChildren
                        + ", innerWidth = "
                        + innerWidth
                        + "px, childMarginSum = "
                        + childMarginSum
                        + "px, innerWidthMinusChildMargins = "
                        + i2
                        + "px, childWidth = "
                        + childWidth
                        + "px, childWidthMeasureSpec = "
                        + View.MeasureSpec.toString(childWidthMeasureSpec));
        for (int i3 = 0; i3 < numChildren; i3++) {
            View child2 = getChildAt(i3);
            if (child2.getVisibility() != 8) {
                child2.measure(childWidthMeasureSpec, heightMeasureSpec);
            }
        }
        return innerWidth;
    }

    private int measureAndGetUsedWidth(
            int widthMeasureSpec,
            int heightMeasureSpec,
            int innerWidth,
            boolean collapsePriorityActions) {
        int i;
        View c;
        boolean isPriority;
        int usedWidthForChild;
        int maxPriorityWidth;
        int numChildren;
        int numChildren2 = getChildCount();
        boolean constrained = View.MeasureSpec.getMode(widthMeasureSpec) != 0;
        int otherSize = this.mMeasureOrderOther.size();
        int maxPriorityWidth2 = 0;
        int usedWidth = 0;
        int measuredChildren = 0;
        int measuredPriorityChildren = 0;
        int i2 = 0;
        while (i2 < numChildren2) {
            if (i2 < otherSize) {
                View c2 = this.mMeasureOrderOther.get(i2);
                c = c2;
                isPriority = false;
            } else {
                TextViewInfo info = this.mMeasureOrderTextViews.get(i2 - otherSize);
                View c3 = info.mTextView;
                c = c3;
                isPriority = info.mIsPriority;
            }
            if (c.getVisibility() == 8) {
                numChildren = numChildren2;
            } else {
                ViewGroup.MarginLayoutParams lp =
                        (ViewGroup.MarginLayoutParams) c.getLayoutParams();
                int usedWidthForChild2 = usedWidth;
                if (!constrained) {
                    usedWidthForChild = usedWidthForChild2;
                    maxPriorityWidth = maxPriorityWidth2;
                } else {
                    int availableWidth = innerWidth - usedWidth;
                    int unmeasuredChildren = this.mNumNotGoneChildren - measuredChildren;
                    int maxWidthForChild = availableWidth / unmeasuredChildren;
                    if (isPriority && collapsePriorityActions) {
                        if (maxPriorityWidth2 == 0) {
                            maxPriorityWidth2 =
                                    getResources()
                                            .getDimensionPixelSize(
                                                    R.dimen
                                                            .notification_actions_collapsed_priority_width);
                        }
                        int usedWidthForChild3 = lp.leftMargin;
                        maxWidthForChild = usedWidthForChild3 + maxPriorityWidth2 + lp.rightMargin;
                    } else if (isPriority) {
                        int unmeasuredPriorityChildren =
                                this.mNumPriorityChildren - measuredPriorityChildren;
                        int unmeasuredOtherChildren =
                                unmeasuredChildren - unmeasuredPriorityChildren;
                        int widthReservedForOtherChildren =
                                (innerWidth * unmeasuredOtherChildren) / 4;
                        int widthAvailableForPriority =
                                availableWidth - widthReservedForOtherChildren;
                        maxWidthForChild = widthAvailableForPriority / unmeasuredPriorityChildren;
                    }
                    usedWidthForChild = innerWidth - maxWidthForChild;
                    maxPriorityWidth = maxPriorityWidth2;
                }
                numChildren = numChildren2;
                measureChildWithMargins(
                        c, widthMeasureSpec, usedWidthForChild, heightMeasureSpec, 0);
                usedWidth += c.getMeasuredWidth() + lp.rightMargin + lp.leftMargin;
                measuredChildren++;
                if (!isPriority) {
                    maxPriorityWidth2 = maxPriorityWidth;
                } else {
                    measuredPriorityChildren++;
                    maxPriorityWidth2 = maxPriorityWidth;
                }
            }
            i2++;
            numChildren2 = numChildren;
        }
        int collapsibleIndent =
                this.mCollapsibleIndentDimen == 0
                        ? 0
                        : getResources().getDimensionPixelOffset(this.mCollapsibleIndentDimen);
        if (innerWidth - usedWidth > collapsibleIndent) {
            this.mExtraStartPadding = collapsibleIndent;
            i = 0;
        } else {
            i = 0;
            this.mExtraStartPadding = 0;
        }
        if (this.mEmphasizedMode) {
            this.mExtraStartPadding = i;
        }
        return usedWidth;
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int usedWidth;
        countAndRebuildMeasureOrder();
        int innerWidth =
                (View.MeasureSpec.getSize(widthMeasureSpec) - this.mPaddingLeft)
                        - this.mPaddingRight;
        if (this.mEvenlyDividedMode) {
            usedWidth = measureAndReturnEvenlyDividedWidth(heightMeasureSpec, innerWidth);
        } else {
            usedWidth =
                    measureAndGetUsedWidth(widthMeasureSpec, heightMeasureSpec, innerWidth, false);
            if (this.mNumPriorityChildren != 0 && usedWidth >= innerWidth) {
                usedWidth =
                        measureAndGetUsedWidth(
                                widthMeasureSpec, heightMeasureSpec, innerWidth, true);
            }
        }
        this.mTotalWidth =
                this.mPaddingRight + usedWidth + this.mPaddingLeft + this.mExtraStartPadding;
        setMeasuredDimension(
                resolveSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                resolveSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    private void rebuildMeasureOrder(int capacityText, int capacityOther) {
        clearMeasureOrder();
        this.mMeasureOrderTextViews.ensureCapacity(capacityText);
        this.mMeasureOrderOther.ensureCapacity(capacityOther);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View c = getChildAt(i);
            if ((c instanceof TextView) && ((TextView) c).getText().length() > 0) {
                this.mMeasureOrderTextViews.add(new TextViewInfo((TextView) c));
            } else {
                this.mMeasureOrderOther.add(c);
            }
        }
        this.mMeasureOrderTextViews.sort(MEASURE_ORDER_COMPARATOR);
    }

    private void clearMeasureOrder() {
        this.mMeasureOrderOther.clear();
        this.mMeasureOrderTextViews.clear();
    }

    @Override // android.view.ViewGroup
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        clearMeasureOrder();
        if (child.getBackground() instanceof RippleDrawable) {
            ((RippleDrawable) child.getBackground()).setForceSoftware(true);
        }
    }

    @Override // android.view.ViewGroup
    public void onViewRemoved(View child) {
        super.onViewRemoved(child);
        clearMeasureOrder();
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childLeft;
        boolean isLayoutRtl;
        int paddingTop;
        NotificationActionListLayout notificationActionListLayout = this;
        boolean isLayoutRtl2 = isLayoutRtl();
        int paddingTop2 = notificationActionListLayout.mPaddingTop;
        boolean centerAligned = (notificationActionListLayout.mGravity & 1) != 0;
        if (centerAligned) {
            childLeft =
                    ((notificationActionListLayout.mPaddingLeft + left) + ((right - left) / 2))
                            - (notificationActionListLayout.mTotalWidth / 2);
        } else {
            int childLeft2 = notificationActionListLayout.mPaddingLeft;
            int absoluteGravity = Gravity.getAbsoluteGravity(Gravity.START, getLayoutDirection());
            if (absoluteGravity == 5) {
                childLeft =
                        childLeft2 + ((right - left) - notificationActionListLayout.mTotalWidth);
            } else {
                childLeft = childLeft2 + notificationActionListLayout.mExtraStartPadding;
            }
        }
        int absoluteGravity2 = bottom - top;
        int innerHeight =
                (absoluteGravity2 - paddingTop2) - notificationActionListLayout.mPaddingBottom;
        int count = getChildCount();
        int start = 0;
        int dir = 1;
        if (isLayoutRtl2) {
            start = count - 1;
            dir = -1;
            childLeft = 0;
        }
        int i = 0;
        while (i < count) {
            int childIndex = (dir * i) + start;
            View child = notificationActionListLayout.getChildAt(childIndex);
            if (child.getVisibility() == 8) {
                isLayoutRtl = isLayoutRtl2;
                paddingTop = paddingTop2;
            } else {
                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                ViewGroup.MarginLayoutParams lp =
                        (ViewGroup.MarginLayoutParams) child.getLayoutParams();
                int childTop =
                        ((paddingTop2 + ((innerHeight - childHeight) / 2)) + lp.topMargin)
                                - lp.bottomMargin;
                isLayoutRtl = isLayoutRtl2;
                int childLeft3 = childLeft + lp.leftMargin;
                paddingTop = paddingTop2;
                child.layout(childLeft3, childTop, childLeft3 + childWidth, childTop + childHeight);
                childLeft = childLeft3 + lp.rightMargin + childWidth;
            }
            i++;
            notificationActionListLayout = this;
            isLayoutRtl2 = isLayoutRtl;
            paddingTop2 = paddingTop;
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mDefaultPaddingBottom = getPaddingBottom();
        this.mDefaultPaddingTop = getPaddingTop();
        updateHeights();
    }

    private void updateHeights() {
        int inset = getResources().getDimensionPixelSize(R.dimen.button_inset_vertical_material);
        this.mEmphasizedPaddingTop =
                getResources().getDimensionPixelSize(R.dimen.notification_content_margin) - inset;
        this.mEmphasizedPaddingBottom =
                getResources().getDimensionPixelSize(R.dimen.notification_content_margin_end)
                        - inset;
        this.mEmphasizedHeight =
                this.mEmphasizedPaddingTop
                        + this.mEmphasizedPaddingBottom
                        + getResources()
                                .getDimensionPixelSize(
                                        R.dimen.notification_action_emphasized_height);
        this.mRegularHeight =
                getResources().getDimensionPixelSize(R.dimen.notification_action_list_height);
    }

    @RemotableViewMethod
    public void setCollapsibleIndentDimen(int collapsibleIndentDimen) {
        if (this.mCollapsibleIndentDimen != collapsibleIndentDimen) {
            this.mCollapsibleIndentDimen = collapsibleIndentDimen;
            requestLayout();
        }
    }

    @RemotableViewMethod
    public void setEvenlyDividedMode(boolean evenlyDividedMode) {
        if (evenlyDividedMode && !Flags.evenlyDividedCallStyleActionLayout()) {
            Log.e(
                    TAG,
                    "setEvenlyDividedMode(true) called with new action layout disabled; leaving"
                        + " evenly divided mode disabled");
        } else if (evenlyDividedMode != this.mEvenlyDividedMode) {
            Log.v(TAG, "evenlyDividedMode changed to " + evenlyDividedMode + "; requesting layout");
            this.mEvenlyDividedMode = evenlyDividedMode;
            requestLayout();
        }
    }

    @RemotableViewMethod
    public void setEmphasizedMode(boolean emphasizedMode) {
        int height;
        this.mEmphasizedMode = emphasizedMode;
        setShowDividers(this.mEmphasizedMode ? 0 : 2);
        if (emphasizedMode) {
            setPaddingRelative(
                    getPaddingStart(),
                    this.mEmphasizedPaddingTop,
                    getPaddingEnd(),
                    this.mEmphasizedPaddingBottom);
            setMinimumHeight(this.mEmphasizedHeight);
            height = -2;
        } else {
            int height2 = getPaddingStart();
            setPaddingRelative(
                    height2, this.mDefaultPaddingTop, getPaddingEnd(), this.mDefaultPaddingBottom);
            height = this.mRegularHeight;
        }
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = height;
        setLayoutParams(layoutParams);
    }

    public boolean isEmphasizedMode() {
        return this.mEmphasizedMode;
    }

    public int getExtraMeasureHeight() {
        if (this.mEmphasizedMode) {
            return this.mEmphasizedHeight - this.mRegularHeight;
        }
        return 0;
    }

    static /* synthetic */ int lambda$static$0(TextViewInfo a, TextViewInfo b) {
        int priorityComparison = -Boolean.compare(a.mIsPriority, b.mIsPriority);
        if (priorityComparison != 0) {
            return priorityComparison;
        }
        return Integer.compare(a.mTextLength, b.mTextLength);
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
            return (this.mTextView.getText().length() == this.mTextLength
                            && NotificationActionListLayout.isPriority(this.mTextView)
                                    == this.mIsPriority)
                    ? false
                    : true;
        }
    }
}
