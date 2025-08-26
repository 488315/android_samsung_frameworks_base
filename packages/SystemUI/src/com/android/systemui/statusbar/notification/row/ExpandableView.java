package com.android.systemui.statusbar.notification.row;

import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.StatusBarIconView;
import com.android.systemui.statusbar.notification.PhysicsProperty;
import com.android.systemui.statusbar.notification.Roundable;
import com.android.systemui.statusbar.notification.RoundableState;
import com.android.systemui.statusbar.notification.stack.ExpandableViewState;
import com.android.systemui.statusbar.notification.stack.MagneticRowListener;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.ViewState;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public abstract class ExpandableView extends FrameLayout implements Dumpable, Roundable {
    public static final PhysicsProperty HEIGHT_PROPERTY = new PhysicsProperty(R.id.height_animator_tag, new FloatProperty("ActualHeight") { // from class: com.android.systemui.statusbar.notification.row.ExpandableView.1
        @Override // android.util.Property
        public final Float get(Object obj) {
            return Float.valueOf(((ExpandableView) ((View) obj)).mActualHeight);
        }

        @Override // android.util.FloatProperty
        public final void setValue(Object obj, float f) {
            ((ExpandableView) ((View) obj)).setActualHeight((int) f, true);
        }
    });
    public static final Rect mClipRect = new Rect();
    public int mActualHeight;
    public boolean mChangingPosition;
    public int mClipBottomAmount;
    public boolean mClipToActualHeight;
    public int mClipTopAmount;
    public int mContentShift;
    public float mExtraWidthForClipping;
    public boolean mInRemovalAnimation;
    public boolean mInShelf;
    public SpringAnimation mMagneticAnimator;
    public MagneticRowListener mMagneticRowListener;
    public final ArrayList mMatchParentViews;
    public int mMinimumHeightForClipping;
    public NotificationStackScrollLayout.AnonymousClass7 mOnHeightChangedListener;
    public RoundableState mRoundableState;
    public int mTouchSlop;
    public boolean mTransformingInShelf;
    public ViewGroup mTransientContainer;
    public final ExpandableViewState mViewState;
    public boolean mWillBeGone;

    /* renamed from: com.android.systemui.statusbar.notification.row.ExpandableView$2, reason: invalid class name */
    public class AnonymousClass2 implements MagneticRowListener {
        public AnonymousClass2() {
        }

        public final void cancelMagneticAnimations() {
            ExpandableView.this.mMagneticAnimator.cancel();
        }

        public final void setMagneticTranslation(float f, boolean z) {
            ExpandableView expandableView = ExpandableView.this;
            SpringAnimation springAnimation = expandableView.mMagneticAnimator;
            if (!springAnimation.mRunning) {
                expandableView.setTranslation(f);
                return;
            }
            if (!z) {
                springAnimation.animateToFinalPosition(f);
            } else if (Math.abs(expandableView.getTranslation() - f) > expandableView.mTouchSlop) {
                expandableView.mMagneticAnimator.animateToFinalPosition(f);
            } else {
                expandableView.mMagneticAnimator.cancel();
                expandableView.setTranslation(f);
            }
        }
    }

    public enum ClipSide {
        TOP,
        BOTTOM
    }

    public ExpandableView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRoundableState = null;
        this.mMinimumHeightForClipping = 0;
        this.mExtraWidthForClipping = 0.0f;
        this.mMatchParentViews = new ArrayList();
        this.mClipToActualHeight = true;
        this.mChangingPosition = false;
        this.mMagneticAnimator = new SpringAnimation(this, DynamicAnimation.TRANSLATION_X);
        this.mMagneticRowListener = new AnonymousClass2();
        this.mViewState = createExpandableViewState();
        this.mContentShift = getResources().getDimensionPixelSize(R.dimen.shelf_transform_content_shift);
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public boolean areChildrenExpanded() {
        return false;
    }

    public boolean canExpandableViewBeDismissed() {
        return false;
    }

    public ExpandableViewState createExpandableViewState() {
        return new ExpandableViewState();
    }

    public void dump(PrintWriter printWriter, final String[] strArr) {
        final IndentingPrintWriter indentingPrintWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        indentingPrintWriterAsIndenting.println(getClass().getSimpleName());
        DumpUtilsKt.withIncreasedIndent(indentingPrintWriterAsIndenting, new Runnable() { // from class: com.android.systemui.statusbar.notification.row.ExpandableView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ExpandableView expandableView = this.f$0;
                PrintWriter printWriter2 = indentingPrintWriterAsIndenting;
                String[] strArr2 = strArr;
                ExpandableViewState expandableViewState = expandableView.mViewState;
                if (expandableViewState == null) {
                    printWriter2.println("no viewState!!!");
                } else {
                    expandableViewState.dump(printWriter2, strArr2);
                    printWriter2.println();
                }
            }
        });
    }

    public void getBoundsOnScreen(Rect rect, boolean z) {
        super.getBoundsOnScreen(rect, z);
        if (getTranslationY() + getTop() < 0.0f) {
            rect.top = (int) (getTranslationY() + getTop() + rect.top);
        }
        int i = rect.top;
        rect.bottom = this.mActualHeight + i;
        rect.top = Math.max(0, this.mClipTopAmount) + i;
    }

    public int getCollapsedHeight() {
        return getHeight();
    }

    @Override // android.view.View
    public final void getDrawingRect(Rect rect) {
        super.getDrawingRect(rect);
        rect.left = (int) (getTranslationX() + rect.left);
        rect.right = (int) (getTranslationX() + rect.right);
        rect.bottom = (int) (getTranslationY() + rect.top + this.mActualHeight);
        rect.top = (int) (getTranslationY() + this.mClipTopAmount + rect.top);
    }

    public float getHeaderVisibleAmount() {
        return 1.0f;
    }

    public int getHeadsUpHeightWithoutHeader() {
        return getHeight();
    }

    public int getHeightWithoutLockscreenConstraints() {
        return getHeight();
    }

    public int getIntrinsicHeight() {
        return getHeight();
    }

    public int getMaxContentHeight() {
        return getHeight();
    }

    public int getMinHeight(boolean z) {
        return getHeight();
    }

    public float getOutlineAlpha() {
        return 0.0f;
    }

    public int getOutlineTranslation() {
        return 0;
    }

    public int getPinnedHeadsUpHeight() {
        return getIntrinsicHeight();
    }

    public final int getRelativeTopPadding(View view) {
        int top = 0;
        while (view.getParent() instanceof ViewGroup) {
            top += view.getTop();
            view = (View) view.getParent();
            if (view == this) {
                break;
            }
        }
        return top;
    }

    public RoundableState getRoundableState() {
        if (this.mRoundableState == null) {
            this.mRoundableState = new RoundableState(this, this, 0.0f);
        }
        return this.mRoundableState;
    }

    public StatusBarIconView getShelfIcon() {
        return null;
    }

    public View getShelfTransformationTarget() {
        return null;
    }

    public float getTranslation() {
        return getTranslationX();
    }

    public boolean hasExpandingChild() {
        return false;
    }

    public boolean hasNoContentHeight() {
        return false;
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return super.hasOverlappingRendering() && this.mActualHeight <= getHeight();
    }

    public boolean isAboveShelf() {
        return false;
    }

    public boolean isChildInGroup() {
        return false;
    }

    public boolean isContentExpandable() {
        return false;
    }

    public boolean isExpandAnimationRunning() {
        return false;
    }

    public boolean isGroupExpanded$1() {
        return false;
    }

    public boolean isGroupExpansionChanging() {
        return false;
    }

    public boolean isHeadsUpAnimatingAway() {
        return false;
    }

    public boolean isHeadsUpState() {
        return false;
    }

    public abstract boolean isInsignificant();

    public boolean isPinned() {
        return false;
    }

    public boolean isSummaryWithChildren() {
        return false;
    }

    public boolean isTransparent() {
        return false;
    }

    public boolean isUserGroupExpanded() {
        return false;
    }

    public boolean mustStayOnScreen() {
        return false;
    }

    public boolean needsClippingToShelf() {
        return !(this instanceof NotificationShelf);
    }

    public void notifyHeightChanged(boolean z) {
        NotificationStackScrollLayout.AnonymousClass7 anonymousClass7 = this.mOnHeightChangedListener;
        if (anonymousClass7 != null) {
            NotificationStackScrollLayout.this.onChildHeightChanged(this, z);
        }
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mContentShift = getResources().getDimensionPixelSize(R.dimen.shelf_transform_content_shift);
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        updateClipping$1();
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getSize(i2);
        int paddingEnd = getPaddingEnd() + getPaddingStart();
        int mode = View.MeasureSpec.getMode(i2);
        int iMin = Integer.MAX_VALUE;
        if (mode != 0 && size != 0) {
            iMin = Math.min(size, Integer.MAX_VALUE);
        }
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(iMin, Integer.MIN_VALUE);
        int childCount = getChildCount();
        int i3 = 0;
        int iMax = 0;
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            if (childAt.getVisibility() != 8) {
                ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                int i5 = layoutParams.height;
                if (i5 != -1) {
                    childAt.measure(FrameLayout.getChildMeasureSpec(i, paddingEnd, layoutParams.width), i5 >= 0 ? View.MeasureSpec.makeMeasureSpec(Math.min(i5, iMin), 1073741824) : iMakeMeasureSpec);
                    iMax = Math.max(iMax, childAt.getMeasuredHeight());
                } else {
                    this.mMatchParentViews.add(childAt);
                }
            }
        }
        if (mode != 1073741824) {
            size = Math.min(iMin, iMax);
        }
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(size, 1073741824);
        ArrayList arrayList = this.mMatchParentViews;
        int size2 = arrayList.size();
        while (i3 < size2) {
            Object obj = arrayList.get(i3);
            i3++;
            View view = (View) obj;
            view.measure(FrameLayout.getChildMeasureSpec(i, paddingEnd, view.getLayoutParams().width), iMakeMeasureSpec2);
        }
        this.mMatchParentViews.clear();
        setMeasuredDimension(View.MeasureSpec.getSize(i), size);
    }

    public void performAddAnimation(long j, long j2) {
        performAddAnimation(j, j2, false, false, null);
    }

    public abstract void performAddAnimation(long j, long j2, boolean z, boolean z2, Runnable runnable);

    public abstract long performRemoveAnimation(long j, long j2, float f, boolean z, boolean z2, Runnable runnable, Runnable runnable2, AnimatorListenerAdapter animatorListenerAdapter, ClipSide clipSide);

    public boolean pointInView(float f, float f2, float f3) {
        return f >= (-f3) && f2 >= ((float) Math.max(0, this.mClipTopAmount)) - f3 && f < ((float) (((FrameLayout) this).mRight - ((FrameLayout) this).mLeft)) + f3 && f2 < ((float) this.mActualHeight) + f3;
    }

    public void removeFromTransientContainer() {
        ViewGroup viewGroup = this.mTransientContainer;
        if (viewGroup == null) {
            return;
        }
        ViewParent parent = getParent();
        if (parent == viewGroup) {
            viewGroup.removeTransientView(this);
            this.mTransientContainer = null;
            return;
        }
        Log.w("ExpandableView", "Expandable view " + this + " has transient container " + viewGroup + " but different parent " + parent);
        this.mTransientContainer = null;
    }

    public final void removeFromTransientContainerForAdditionTo(ViewGroup viewGroup) {
        ViewParent parent = getParent();
        ViewGroup viewGroup2 = this.mTransientContainer;
        if (parent == null || parent == viewGroup) {
            removeFromTransientContainer();
            return;
        }
        if (viewGroup2 == null) {
            return;
        }
        if (viewGroup2 != parent) {
            throw new IllegalStateException("Expandable view " + this + " has transient container " + viewGroup2 + " but different parent " + parent);
        }
        Log.w("ExpandableView", "Removing view " + this + " from transient container " + viewGroup2 + " in preparation for moving to parent " + viewGroup);
        viewGroup2.removeTransientView(this);
        this.mTransientContainer = null;
    }

    public final void resetViewState$1() {
        this.mViewState.height = getIntrinsicHeight();
        int i = 0;
        this.mViewState.gone = getVisibility() == 8;
        this.mViewState.setAlpha(1.0f);
        ExpandableViewState expandableViewState = this.mViewState;
        expandableViewState.notGoneIndex = -1;
        expandableViewState.setXTranslation(getTranslationX());
        ExpandableViewState expandableViewState2 = this.mViewState;
        expandableViewState2.hidden = false;
        expandableViewState2.setScaleX(getScaleX());
        ExpandableViewState expandableViewState3 = this.mViewState;
        float scaleY = getScaleY();
        expandableViewState3.getClass();
        if (ViewState.isValidFloat("scaleY", scaleY)) {
            expandableViewState3.mScaleY = scaleY;
        }
        ExpandableViewState expandableViewState4 = this.mViewState;
        expandableViewState4.inShelf = false;
        expandableViewState4.headsUpIsVisible = false;
        if (this instanceof ExpandableNotificationRow) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this;
            List attachedChildren = expandableNotificationRow.getAttachedChildren();
            if (!expandableNotificationRow.mIsSummaryWithChildren || attachedChildren == null) {
                return;
            }
            ArrayList arrayList = (ArrayList) attachedChildren;
            int size = arrayList.size();
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((ExpandableNotificationRow) obj).resetViewState$1();
            }
        }
    }

    public void setActualHeight(int i, boolean z) {
        if (this.mActualHeight != i) {
            this.mActualHeight = i;
            updateClipping$1();
            if (z) {
                notifyHeightChanged(false);
            }
        }
    }

    public void setClipBottomAmount(int i) {
        this.mClipBottomAmount = i;
        updateClipping$1();
    }

    public void setClipTopAmount(int i) {
        this.mClipTopAmount = i;
        updateClipping$1();
    }

    @Override // android.view.View
    public final void setLayerType(int i, Paint paint) {
        if (i == 0 || hasOverlappingRendering()) {
            super.setLayerType(i, paint);
        }
    }

    public void setTranslation(float f) {
        setTranslationX(f);
    }

    public boolean shouldClipToActualHeight() {
        return true;
    }

    public boolean showingPulsing() {
        return false;
    }

    public void updateClipping$1() {
        if (!this.mClipToActualHeight || !shouldClipToActualHeight()) {
            setClipBounds(null);
            return;
        }
        int i = this.mClipTopAmount;
        int iMax = Math.max(Math.max(this.mActualHeight - this.mClipBottomAmount, i), this.mMinimumHeightForClipping);
        Rect rect = mClipRect;
        rect.set(Integer.MIN_VALUE, i, Integer.MAX_VALUE, iMax);
        setClipBounds(rect);
    }

    public void setActualHeightAnimating(boolean z) {
    }

    public void setDimmed(boolean z) {
    }

    public void setHideSensitiveForIntrinsicHeight(boolean z) {
    }

    public void cancelTranslationAnimations() {
    }

    public void markHeadsUpSeen() {
    }

    public void setHideSensitive(boolean z, boolean z2) {
    }

    public void setFakeShadowIntensity(int i, float f, float f2, int i2) {
    }
}
