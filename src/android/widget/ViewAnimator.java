package android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import com.android.internal.R;

/* loaded from: classes5.dex */
public class ViewAnimator extends FrameLayout {
    boolean mAnimateFirstTime;
    boolean mFirstTime;
    Animation mInAnimation;
    Animation mOutAnimation;
    int mWhichChild;

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<ViewAnimator> {
        private int mAnimateFirstViewId;
        private int mInAnimationId;
        private int mOutAnimationId;
        private boolean mPropertiesMapped = false;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mAnimateFirstViewId = propertyMapper.mapBoolean("animateFirstView", 16843477);
            this.mInAnimationId = propertyMapper.mapObject("inAnimation", 16843127);
            this.mOutAnimationId = propertyMapper.mapObject("outAnimation", 16843128);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(ViewAnimator viewAnimator, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mAnimateFirstViewId, viewAnimator.getAnimateFirstView());
            propertyReader.readObject(this.mInAnimationId, viewAnimator.getInAnimation());
            propertyReader.readObject(this.mOutAnimationId, viewAnimator.getOutAnimation());
        }
    }

    public ViewAnimator(Context context) {
        super(context);
        this.mWhichChild = 0;
        this.mFirstTime = true;
        this.mAnimateFirstTime = true;
        initViewAnimator(context, null);
    }

    public ViewAnimator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mWhichChild = 0;
        this.mFirstTime = true;
        this.mAnimateFirstTime = true;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ViewAnimator);
        saveAttributeDataForStyleable(context, R.styleable.ViewAnimator, attributeSet, typedArrayObtainStyledAttributes, 0, 0);
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(0, 0);
        if (resourceId > 0) {
            setInAnimation(context, resourceId);
        }
        int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(1, 0);
        if (resourceId2 > 0) {
            setOutAnimation(context, resourceId2);
        }
        setAnimateFirstView(typedArrayObtainStyledAttributes.getBoolean(2, true));
        typedArrayObtainStyledAttributes.recycle();
        initViewAnimator(context, attributeSet);
    }

    private void initViewAnimator(Context context, AttributeSet attributeSet) {
        if (attributeSet == null) {
            this.mMeasureAllChildren = true;
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.FrameLayout);
        saveAttributeDataForStyleable(context, R.styleable.FrameLayout, attributeSet, typedArrayObtainStyledAttributes, 0, 0);
        setMeasureAllChildren(typedArrayObtainStyledAttributes.getBoolean(0, true));
        typedArrayObtainStyledAttributes.recycle();
    }

    @RemotableViewMethod
    public void setDisplayedChild(int i) {
        this.mWhichChild = i;
        if (i >= getChildCount()) {
            this.mWhichChild = 0;
        } else if (i < 0) {
            this.mWhichChild = getChildCount() - 1;
        }
        boolean z = getFocusedChild() != null;
        showOnly(this.mWhichChild);
        if (z) {
            requestFocus(2);
        }
    }

    public int getDisplayedChild() {
        return this.mWhichChild;
    }

    @RemotableViewMethod
    public void showNext() {
        setDisplayedChild(this.mWhichChild + 1);
    }

    @RemotableViewMethod
    public void showPrevious() {
        setDisplayedChild(this.mWhichChild - 1);
    }

    void showOnly(int i, boolean z) {
        Animation animation;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (i2 == i) {
                if (z && (animation = this.mInAnimation) != null) {
                    childAt.startAnimation(animation);
                }
                childAt.setVisibility(0);
                this.mFirstTime = false;
            } else {
                if (z && this.mOutAnimation != null && childAt.getVisibility() == 0) {
                    childAt.startAnimation(this.mOutAnimation);
                } else if (childAt.getAnimation() == this.mInAnimation) {
                    childAt.clearAnimation();
                }
                childAt.setVisibility(8);
            }
        }
    }

    void showOnly(int i) {
        showOnly(i, !this.mFirstTime || this.mAnimateFirstTime);
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        int i2;
        super.addView(view, i, layoutParams);
        if (getChildCount() == 1) {
            view.setVisibility(0);
        } else {
            view.setVisibility(8);
        }
        if (i < 0 || (i2 = this.mWhichChild) < i) {
            return;
        }
        setDisplayedChild(i2 + 1);
    }

    @Override // android.view.ViewGroup
    public void removeAllViews() {
        super.removeAllViews();
        this.mWhichChild = 0;
        this.mFirstTime = true;
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void removeView(View view) {
        int iIndexOfChild = indexOfChild(view);
        if (iIndexOfChild >= 0) {
            removeViewAt(iIndexOfChild);
        }
    }

    @Override // android.view.ViewGroup
    public void removeViewAt(int i) {
        super.removeViewAt(i);
        int childCount = getChildCount();
        if (childCount == 0) {
            this.mWhichChild = 0;
            this.mFirstTime = true;
            return;
        }
        int i2 = this.mWhichChild;
        if (i2 >= childCount) {
            setDisplayedChild(childCount - 1);
        } else if (i2 == i) {
            setDisplayedChild(i2);
        }
    }

    @Override // android.view.ViewGroup
    public void removeViewInLayout(View view) {
        removeView(view);
    }

    @Override // android.view.ViewGroup
    public void removeViews(int i, int i2) {
        super.removeViews(i, i2);
        if (getChildCount() == 0) {
            this.mWhichChild = 0;
            this.mFirstTime = true;
            return;
        }
        int i3 = this.mWhichChild;
        if (i3 < i || i3 >= i + i2) {
            return;
        }
        setDisplayedChild(i3);
    }

    @Override // android.view.ViewGroup
    public void removeViewsInLayout(int i, int i2) {
        removeViews(i, i2);
    }

    public View getCurrentView() {
        return getChildAt(this.mWhichChild);
    }

    public Animation getInAnimation() {
        return this.mInAnimation;
    }

    public void setInAnimation(Animation animation) {
        this.mInAnimation = animation;
    }

    public Animation getOutAnimation() {
        return this.mOutAnimation;
    }

    public void setOutAnimation(Animation animation) {
        this.mOutAnimation = animation;
    }

    public void setInAnimation(Context context, int i) {
        setInAnimation(AnimationUtils.loadAnimation(context, i));
    }

    public void setOutAnimation(Context context, int i) {
        setOutAnimation(AnimationUtils.loadAnimation(context, i));
    }

    public boolean getAnimateFirstView() {
        return this.mAnimateFirstTime;
    }

    public void setAnimateFirstView(boolean z) {
        this.mAnimateFirstTime = z;
    }

    @Override // android.view.View
    public int getBaseline() {
        return getCurrentView() != null ? getCurrentView().getBaseline() : super.getBaseline();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return ViewAnimator.class.getName();
    }
}
