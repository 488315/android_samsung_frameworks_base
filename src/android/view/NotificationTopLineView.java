package android.view;

import android.app.Flags;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Trace;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import com.android.internal.R;
import java.util.HashSet;
import java.util.Set;

@RemoteViews.RemoteView
/* loaded from: classes4.dex */
public class NotificationTopLineView extends ViewGroup {
    private View mAppName;
    private final int mChildHideWidth;
    private final int mChildMinWidth;
    private View mFeedbackIcon;
    private View.OnClickListener mFeedbackListener;
    private final int mGravityY;
    private View mHeaderText;
    private View mHeaderTextDivider;
    private int mHeaderTextMarginEnd;
    private int mMaxAscent;
    private int mMaxDescent;
    private final OverflowAdjuster mOverflowAdjuster;
    private View mSecondaryHeaderText;
    private View mSecondaryHeaderTextDivider;
    private View mTime;
    private View mTitle;
    private HeaderTouchListener mTouchListener;
    private Set<View> mViewsToDisappear;

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public NotificationTopLineView(Context context) {
        this(context, null);
    }

    public NotificationTopLineView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NotificationTopLineView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public NotificationTopLineView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mOverflowAdjuster = new OverflowAdjuster();
        this.mTouchListener = new HeaderTouchListener();
        this.mViewsToDisappear = new HashSet();
        Resources resources = getResources();
        this.mChildMinWidth = resources.getDimensionPixelSize(R.dimen.notification_header_shrink_min_width);
        this.mChildHideWidth = resources.getDimensionPixelSize(R.dimen.notification_header_shrink_hide_width);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{16842927}, i, i2);
        int i3 = obtainStyledAttributes.getInt(0, 0);
        obtainStyledAttributes.recycle();
        if ((i3 & 80) == 80) {
            this.mGravityY = 80;
        } else if ((i3 & 48) == 48) {
            this.mGravityY = 48;
        } else {
            this.mGravityY = 16;
        }
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mAppName = findViewById(R.id.app_name_text);
        this.mTitle = findViewById(16908310);
        this.mHeaderText = findViewById(R.id.header_text);
        this.mHeaderTextDivider = findViewById(R.id.header_text_divider);
        this.mSecondaryHeaderText = findViewById(R.id.header_text_secondary);
        this.mSecondaryHeaderTextDivider = findViewById(R.id.header_text_secondary_divider);
        this.mFeedbackIcon = findViewById(R.id.feedback);
        this.mTime = findViewById(R.id.time);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int makeMeasureSpec;
        Trace.beginSection("NotificationTopLineView#onMeasure");
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        boolean z = View.MeasureSpec.getMode(i2) == Integer.MIN_VALUE;
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE);
        if (Flags.notificationsRedesignTemplates()) {
            makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        } else {
            makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(size2, Integer.MIN_VALUE);
        }
        int paddingStart = getPaddingStart();
        this.mMaxAscent = -1;
        this.mMaxDescent = -1;
        int i3 = -1;
        for (int i4 = 0; i4 < getChildCount(); i4++) {
            View childAt = getChildAt(i4);
            if (childAt.getVisibility() != 8) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
                childAt.measure(getChildMeasureSpec(makeMeasureSpec2, marginLayoutParams.leftMargin + marginLayoutParams.rightMargin, marginLayoutParams.width), getChildMeasureSpec(makeMeasureSpec, marginLayoutParams.topMargin + marginLayoutParams.bottomMargin, marginLayoutParams.height));
                paddingStart += marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + childAt.getMeasuredWidth();
                int baseline = childAt.getBaseline();
                int measuredHeight = childAt.getMeasuredHeight();
                if (baseline != -1) {
                    this.mMaxAscent = Math.max(this.mMaxAscent, baseline);
                    this.mMaxDescent = Math.max(this.mMaxDescent, measuredHeight - baseline);
                }
                i3 = Math.max(i3, measuredHeight);
            }
        }
        this.mViewsToDisappear.clear();
        int max = Math.max(this.mHeaderTextMarginEnd, getPaddingEnd());
        if (paddingStart > size - max) {
            this.mOverflowAdjuster.resetForOverflow((paddingStart - size) + max, makeMeasureSpec).adjust(this.mAppName, null, this.mChildMinWidth).adjust(this.mHeaderText, this.mHeaderTextDivider, this.mChildMinWidth).adjust(this.mSecondaryHeaderText, this.mSecondaryHeaderTextDivider, 0).adjust(this.mTitle, null, this.mChildMinWidth).adjust(this.mTime, null, this.mChildMinWidth).adjust(this.mHeaderText, this.mHeaderTextDivider, 0).finish();
        }
        if (z) {
            size2 = i3;
        }
        setMeasuredDimension(size, size2);
        Trace.endSection();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        boolean z2 = getLayoutDirection() == 1;
        int width = getWidth();
        int paddingStart = getPaddingStart();
        int childCount = getChildCount();
        int i7 = i4 - i2;
        int i8 = (i7 - this.mPaddingTop) - this.mPaddingBottom;
        int i9 = this.mPaddingTop;
        int i10 = this.mMaxAscent;
        int i11 = i9 + ((i8 - (this.mMaxDescent + i10)) / 2) + i10;
        for (int i12 = 0; i12 < childCount; i12++) {
            View childAt = getChildAt(i12);
            if (childAt.getVisibility() != 8) {
                int measuredHeight = childAt.getMeasuredHeight();
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
                int baseline = childAt.getBaseline();
                int i13 = this.mGravityY;
                if (i13 != 16) {
                    if (i13 == 48) {
                        i6 = this.mPaddingTop + marginLayoutParams.topMargin;
                        if (baseline != -1) {
                            i5 = i6 + (this.mMaxAscent - baseline);
                        }
                        i5 = i6;
                    } else if (i13 == 80) {
                        i6 = ((i7 - this.mPaddingBottom) - measuredHeight) - marginLayoutParams.bottomMargin;
                        if (baseline != -1) {
                            i5 = i6 - (this.mMaxDescent - (measuredHeight - baseline));
                        }
                        i5 = i6;
                    } else {
                        i5 = this.mPaddingTop;
                    }
                } else if (baseline != -1) {
                    int i14 = i8 - measuredHeight;
                    i5 = i14 > 0 ? i11 - baseline : this.mPaddingTop + (i14 / 2);
                } else {
                    i5 = ((this.mPaddingTop + ((i8 - measuredHeight) / 2)) + marginLayoutParams.topMargin) - marginLayoutParams.bottomMargin;
                }
                if (this.mViewsToDisappear.contains(childAt)) {
                    childAt.layout(paddingStart, i5, paddingStart, measuredHeight + i5);
                } else {
                    int marginStart = paddingStart + marginLayoutParams.getMarginStart();
                    int measuredWidth = childAt.getMeasuredWidth() + marginStart;
                    int i15 = z2 ? width - measuredWidth : marginStart;
                    int i16 = z2 ? width - marginStart : measuredWidth;
                    int marginEnd = measuredWidth + marginLayoutParams.getMarginEnd();
                    childAt.layout(i15, i5, i16, measuredHeight + i5);
                    paddingStart = marginEnd;
                }
            }
        }
        updateTouchListener();
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new ViewGroup.MarginLayoutParams(getContext(), attributeSet);
    }

    private void updateTouchListener() {
        if (this.mFeedbackListener == null) {
            setOnTouchListener(null);
        } else {
            setOnTouchListener(this.mTouchListener);
            this.mTouchListener.bindTouchRects();
        }
    }

    public void setFeedbackOnClickListener(View.OnClickListener onClickListener) {
        this.mFeedbackListener = onClickListener;
        this.mFeedbackIcon.setOnClickListener(onClickListener);
        updateTouchListener();
    }

    public void setHeaderTextMarginEnd(int i) {
        if (this.mHeaderTextMarginEnd != i) {
            this.mHeaderTextMarginEnd = i;
            requestLayout();
        }
    }

    public int getHeaderTextMarginEnd() {
        return this.mHeaderTextMarginEnd;
    }

    public void setPaddingStart(int i) {
        setPaddingRelative(i, getPaddingTop(), getPaddingEnd(), getPaddingBottom());
    }

    private class HeaderTouchListener implements View.OnTouchListener {
        private float mDownX;
        private float mDownY;
        private Rect mFeedbackRect;
        private int mTouchSlop;
        private boolean mTrackGesture;

        HeaderTouchListener() {
        }

        public void bindTouchRects() {
            this.mFeedbackRect = getRectAroundView(NotificationTopLineView.this.mFeedbackIcon);
            this.mTouchSlop = ViewConfiguration.get(NotificationTopLineView.this.getContext()).getScaledTouchSlop();
        }

        private Rect getRectAroundView(View view) {
            float f = NotificationTopLineView.this.getResources().getDisplayMetrics().density * 48.0f;
            float max = Math.max(f, view.getWidth());
            float max2 = Math.max(f, view.getHeight());
            Rect rect = new Rect();
            if (view.getVisibility() == 8) {
                view = NotificationTopLineView.this.getFirstChildNotGone();
                rect.left = (int) (view.getLeft() - (max / 2.0f));
            } else {
                rect.left = (int) (((view.getLeft() + view.getRight()) / 2.0f) - (max / 2.0f));
            }
            rect.top = (int) (((view.getTop() + view.getBottom()) / 2.0f) - (max2 / 2.0f));
            rect.bottom = (int) (rect.top + max2);
            rect.right = (int) (rect.left + max);
            return rect;
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            int actionMasked = motionEvent.getActionMasked() & 255;
            if (actionMasked == 0) {
                this.mTrackGesture = false;
                if (isInside(x, y)) {
                    this.mDownX = x;
                    this.mDownY = y;
                    this.mTrackGesture = true;
                    return true;
                }
            } else if (actionMasked != 1) {
                if (actionMasked == 2 && this.mTrackGesture && (Math.abs(this.mDownX - x) > this.mTouchSlop || Math.abs(this.mDownY - y) > this.mTouchSlop)) {
                    this.mTrackGesture = false;
                }
            } else if (this.mTrackGesture && onTouchUp(x, y, this.mDownX, this.mDownY)) {
                return true;
            }
            return this.mTrackGesture;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean onTouchUp(float f, float f2, float f3, float f4) {
            if (!NotificationTopLineView.this.mFeedbackIcon.isVisibleToUser()) {
                return false;
            }
            if (!this.mFeedbackRect.contains((int) f, (int) f2) && !this.mFeedbackRect.contains((int) f3, (int) f4)) {
                return false;
            }
            NotificationTopLineView.this.mFeedbackIcon.performClick();
            return true;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean isInside(float f, float f2) {
            return this.mFeedbackRect.contains((int) f, (int) f2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public View getFirstChildNotGone() {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() != 8) {
                return childAt;
            }
        }
        return this;
    }

    public boolean isTitlePresent() {
        return this.mTitle != null;
    }

    public boolean isInTouchRect(float f, float f2) {
        if (this.mFeedbackListener == null) {
            return false;
        }
        return this.mTouchListener.isInside(f, f2);
    }

    public boolean onTouchUp(float f, float f2, float f3, float f4) {
        if (this.mFeedbackListener == null) {
            return false;
        }
        return this.mTouchListener.onTouchUp(f, f2, f3, f4);
    }

    private final class OverflowAdjuster {
        private int mHeightSpec;
        private int mOverflow;
        private View mRegrowView;

        private OverflowAdjuster() {
        }

        OverflowAdjuster resetForOverflow(int i, int i2) {
            this.mOverflow = i;
            this.mHeightSpec = i2;
            this.mRegrowView = null;
            return this;
        }

        OverflowAdjuster adjust(View view, View view2, int i) {
            int measuredWidth;
            View view3;
            View view4;
            if (this.mOverflow > 0 && view != null && view.getVisibility() != 8 && (measuredWidth = view.getMeasuredWidth()) > i) {
                int max = Math.max(i, measuredWidth - this.mOverflow);
                if (i == 0 && max < NotificationTopLineView.this.mChildHideWidth && (view4 = this.mRegrowView) != null && view4 != view) {
                    max = 0;
                }
                view.measure(View.MeasureSpec.makeMeasureSpec(max, Integer.MIN_VALUE), this.mHeightSpec);
                this.mOverflow -= measuredWidth - max;
                if (max == 0) {
                    NotificationTopLineView.this.mViewsToDisappear.add(view);
                    this.mOverflow -= getHorizontalMargins(view);
                    if (view2 != null && view2.getVisibility() != 8) {
                        NotificationTopLineView.this.mViewsToDisappear.add(view2);
                        int measuredWidth2 = view2.getMeasuredWidth();
                        view2.measure(View.MeasureSpec.makeMeasureSpec(0, Integer.MIN_VALUE), this.mHeightSpec);
                        this.mOverflow -= measuredWidth2 + getHorizontalMargins(view2);
                    }
                }
                if (this.mOverflow < 0 && (view3 = this.mRegrowView) != null) {
                    this.mRegrowView.measure(View.MeasureSpec.makeMeasureSpec(view3.getMeasuredWidth() - this.mOverflow, Integer.MIN_VALUE), this.mHeightSpec);
                    finish();
                    return this;
                }
                if (max != 0) {
                    this.mRegrowView = view;
                }
            }
            return this;
        }

        void finish() {
            resetForOverflow(0, 0);
        }

        private int getHorizontalMargins(View view) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            return marginLayoutParams.getMarginStart() + marginLayoutParams.getMarginEnd();
        }
    }
}
