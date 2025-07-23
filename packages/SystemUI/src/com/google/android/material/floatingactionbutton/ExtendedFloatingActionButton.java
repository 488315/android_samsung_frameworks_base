package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.animation.MotionSpec;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ExtendedFloatingActionButton extends MaterialButton implements CoordinatorLayout.AttachedBehavior {
    public static final AnonymousClass7 HEIGHT;
    public static final AnonymousClass9 PADDING_END;
    public static final AnonymousClass8 PADDING_START;
    public static final AnonymousClass6 WIDTH;
    public int animState;
    public final ExtendedFloatingActionButtonBehavior behavior;
    public final int collapsedSize;
    public final ChangeSizeStrategy extendStrategy;
    public int extendedPaddingEnd;
    public int extendedPaddingStart;
    public final HideStrategy hideStrategy;
    public boolean isExtended;
    public boolean isTransforming;
    public int originalHeight;
    public ColorStateList originalTextCsl;
    public int originalWidth;
    public final ShowStrategy showStrategy;
    public final ChangeSizeStrategy shrinkStrategy;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ChangeSizeStrategy extends BaseMotionStrategy {
        public final boolean extending;
        public final Size size;

        public ChangeSizeStrategy(AnimatorTracker animatorTracker, Size size, boolean z) {
            super(ExtendedFloatingActionButton.this, animatorTracker);
            this.size = size;
            this.extending = z;
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy, com.google.android.material.floatingactionbutton.MotionStrategy
        public final AnimatorSet createAnimator() {
            MotionSpec motionSpec = this.motionSpec;
            if (motionSpec == null) {
                if (this.defaultMotionSpec == null) {
                    this.defaultMotionSpec = MotionSpec.createFromResource(getDefaultMotionSpecResource(), this.context);
                }
                motionSpec = this.defaultMotionSpec;
                motionSpec.getClass();
            }
            boolean hasPropertyValues = motionSpec.hasPropertyValues("width");
            Size size = this.size;
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            if (hasPropertyValues) {
                PropertyValuesHolder[] propertyValues = motionSpec.getPropertyValues("width");
                propertyValues[0].setFloatValues(extendedFloatingActionButton.getWidth(), size.getWidth());
                motionSpec.setPropertyValues("width", propertyValues);
            }
            if (motionSpec.hasPropertyValues("height")) {
                PropertyValuesHolder[] propertyValues2 = motionSpec.getPropertyValues("height");
                propertyValues2[0].setFloatValues(extendedFloatingActionButton.getHeight(), size.getHeight());
                motionSpec.setPropertyValues("height", propertyValues2);
            }
            if (motionSpec.hasPropertyValues("paddingStart")) {
                PropertyValuesHolder[] propertyValues3 = motionSpec.getPropertyValues("paddingStart");
                PropertyValuesHolder propertyValuesHolder = propertyValues3[0];
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                propertyValuesHolder.setFloatValues(extendedFloatingActionButton.getPaddingStart(), size.getPaddingStart());
                motionSpec.setPropertyValues("paddingStart", propertyValues3);
            }
            if (motionSpec.hasPropertyValues("paddingEnd")) {
                PropertyValuesHolder[] propertyValues4 = motionSpec.getPropertyValues("paddingEnd");
                PropertyValuesHolder propertyValuesHolder2 = propertyValues4[0];
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                propertyValuesHolder2.setFloatValues(extendedFloatingActionButton.getPaddingEnd(), size.getPaddingEnd());
                motionSpec.setPropertyValues("paddingEnd", propertyValues4);
            }
            if (motionSpec.hasPropertyValues("labelOpacity")) {
                PropertyValuesHolder[] propertyValues5 = motionSpec.getPropertyValues("labelOpacity");
                boolean z = this.extending;
                propertyValues5[0].setFloatValues(z ? 0.0f : 1.0f, z ? 1.0f : 0.0f);
                motionSpec.setPropertyValues("labelOpacity", propertyValues5);
            }
            return createAnimator(motionSpec);
        }

        @Override // com.google.android.material.floatingactionbutton.MotionStrategy
        public final int getDefaultMotionSpecResource() {
            return this.extending ? R.animator.mtrl_extended_fab_change_size_expand_motion_spec : R.animator.mtrl_extended_fab_change_size_collapse_motion_spec;
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy, com.google.android.material.floatingactionbutton.MotionStrategy
        public final void onAnimationEnd() {
            super.onAnimationEnd();
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            extendedFloatingActionButton.isTransforming = false;
            extendedFloatingActionButton.setHorizontallyScrolling(false);
            ViewGroup.LayoutParams layoutParams = extendedFloatingActionButton.getLayoutParams();
            if (layoutParams == null) {
                return;
            }
            Size size = this.size;
            layoutParams.width = size.getLayoutParams().width;
            layoutParams.height = size.getLayoutParams().height;
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy, com.google.android.material.floatingactionbutton.MotionStrategy
        public final void onAnimationStart(Animator animator) {
            super.onAnimationStart(animator);
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            extendedFloatingActionButton.isExtended = this.extending;
            extendedFloatingActionButton.isTransforming = true;
            extendedFloatingActionButton.setHorizontallyScrolling(true);
        }

        @Override // com.google.android.material.floatingactionbutton.MotionStrategy
        public final void performNow() {
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            boolean z = this.extending;
            extendedFloatingActionButton.isExtended = z;
            ViewGroup.LayoutParams layoutParams = extendedFloatingActionButton.getLayoutParams();
            if (layoutParams == null) {
                return;
            }
            if (!z) {
                extendedFloatingActionButton.originalWidth = layoutParams.width;
                extendedFloatingActionButton.originalHeight = layoutParams.height;
            }
            Size size = this.size;
            layoutParams.width = size.getLayoutParams().width;
            layoutParams.height = size.getLayoutParams().height;
            int paddingStart = size.getPaddingStart();
            int paddingTop = extendedFloatingActionButton.getPaddingTop();
            int paddingEnd = size.getPaddingEnd();
            int paddingBottom = extendedFloatingActionButton.getPaddingBottom();
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            extendedFloatingActionButton.setPaddingRelative(paddingStart, paddingTop, paddingEnd, paddingBottom);
            extendedFloatingActionButton.requestLayout();
        }

        @Override // com.google.android.material.floatingactionbutton.MotionStrategy
        public final boolean shouldCancel() {
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            return this.extending == extendedFloatingActionButton.isExtended || extendedFloatingActionButton.icon == null || TextUtils.isEmpty(extendedFloatingActionButton.getText());
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class HideStrategy extends BaseMotionStrategy {
        public boolean isCancelled;

        public HideStrategy(AnimatorTracker animatorTracker) {
            super(ExtendedFloatingActionButton.this, animatorTracker);
        }

        @Override // com.google.android.material.floatingactionbutton.MotionStrategy
        public final int getDefaultMotionSpecResource() {
            return R.animator.mtrl_extended_fab_hide_motion_spec;
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy, com.google.android.material.floatingactionbutton.MotionStrategy
        public final void onAnimationCancel() {
            super.onAnimationCancel();
            this.isCancelled = true;
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy, com.google.android.material.floatingactionbutton.MotionStrategy
        public final void onAnimationEnd() {
            super.onAnimationEnd();
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            extendedFloatingActionButton.animState = 0;
            if (this.isCancelled) {
                return;
            }
            extendedFloatingActionButton.setVisibility(8);
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy, com.google.android.material.floatingactionbutton.MotionStrategy
        public final void onAnimationStart(Animator animator) {
            super.onAnimationStart(animator);
            this.isCancelled = false;
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            extendedFloatingActionButton.setVisibility(0);
            extendedFloatingActionButton.animState = 1;
        }

        @Override // com.google.android.material.floatingactionbutton.MotionStrategy
        public final void performNow() {
            ExtendedFloatingActionButton.this.setVisibility(8);
        }

        @Override // com.google.android.material.floatingactionbutton.MotionStrategy
        public final boolean shouldCancel() {
            AnonymousClass6 anonymousClass6 = ExtendedFloatingActionButton.WIDTH;
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            if (extendedFloatingActionButton.getVisibility() == 0) {
                if (extendedFloatingActionButton.animState != 1) {
                    return false;
                }
            } else if (extendedFloatingActionButton.animState == 2) {
                return false;
            }
            return true;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class OnChangedCallback {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ShowStrategy extends BaseMotionStrategy {
        public ShowStrategy(AnimatorTracker animatorTracker) {
            super(ExtendedFloatingActionButton.this, animatorTracker);
        }

        @Override // com.google.android.material.floatingactionbutton.MotionStrategy
        public final int getDefaultMotionSpecResource() {
            return R.animator.mtrl_extended_fab_show_motion_spec;
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy, com.google.android.material.floatingactionbutton.MotionStrategy
        public final void onAnimationEnd() {
            super.onAnimationEnd();
            ExtendedFloatingActionButton.this.animState = 0;
        }

        @Override // com.google.android.material.floatingactionbutton.BaseMotionStrategy, com.google.android.material.floatingactionbutton.MotionStrategy
        public final void onAnimationStart(Animator animator) {
            super.onAnimationStart(animator);
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            extendedFloatingActionButton.setVisibility(0);
            extendedFloatingActionButton.animState = 2;
        }

        @Override // com.google.android.material.floatingactionbutton.MotionStrategy
        public final void performNow() {
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            extendedFloatingActionButton.setVisibility(0);
            extendedFloatingActionButton.setAlpha(1.0f);
            extendedFloatingActionButton.setScaleY(1.0f);
            extendedFloatingActionButton.setScaleX(1.0f);
        }

        @Override // com.google.android.material.floatingactionbutton.MotionStrategy
        public final boolean shouldCancel() {
            AnonymousClass6 anonymousClass6 = ExtendedFloatingActionButton.WIDTH;
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            if (extendedFloatingActionButton.getVisibility() != 0) {
                if (extendedFloatingActionButton.animState != 2) {
                    return false;
                }
            } else if (extendedFloatingActionButton.animState == 1) {
                return false;
            }
            return true;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Size {
        int getHeight();

        ViewGroup.LayoutParams getLayoutParams();

        int getPaddingEnd();

        int getPaddingStart();

        int getWidth();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$6] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$7] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$8] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$9] */
    static {
        Class<Float> cls = Float.class;
        WIDTH = new Property(cls, "width") { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.6
            @Override // android.util.Property
            public final Object get(Object obj) {
                return Float.valueOf(((View) obj).getLayoutParams().width);
            }

            @Override // android.util.Property
            public final void set(Object obj, Object obj2) {
                View view = (View) obj;
                view.getLayoutParams().width = ((Float) obj2).intValue();
                view.requestLayout();
            }
        };
        HEIGHT = new Property(cls, "height") { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.7
            @Override // android.util.Property
            public final Object get(Object obj) {
                return Float.valueOf(((View) obj).getLayoutParams().height);
            }

            @Override // android.util.Property
            public final void set(Object obj, Object obj2) {
                View view = (View) obj;
                view.getLayoutParams().height = ((Float) obj2).intValue();
                view.requestLayout();
            }
        };
        PADDING_START = new Property(cls, "paddingStart") { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.8
            @Override // android.util.Property
            public final Object get(Object obj) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                return Float.valueOf(((View) obj).getPaddingStart());
            }

            @Override // android.util.Property
            public final void set(Object obj, Object obj2) {
                View view = (View) obj;
                int intValue = ((Float) obj2).intValue();
                int paddingTop = view.getPaddingTop();
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                view.setPaddingRelative(intValue, paddingTop, view.getPaddingEnd(), view.getPaddingBottom());
            }
        };
        PADDING_END = new Property(cls, "paddingEnd") { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.9
            @Override // android.util.Property
            public final Object get(Object obj) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                return Float.valueOf(((View) obj).getPaddingEnd());
            }

            @Override // android.util.Property
            public final void set(Object obj, Object obj2) {
                View view = (View) obj;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                view.setPaddingRelative(view.getPaddingStart(), view.getPaddingTop(), ((Float) obj2).intValue(), view.getPaddingBottom());
            }
        };
    }

    public ExtendedFloatingActionButton(Context context) {
        this(context, null);
    }

    public static void access$400(ExtendedFloatingActionButton extendedFloatingActionButton, int i, final OnChangedCallback onChangedCallback) {
        final BaseMotionStrategy baseMotionStrategy;
        if (i == 0) {
            baseMotionStrategy = extendedFloatingActionButton.showStrategy;
        } else if (i == 1) {
            baseMotionStrategy = extendedFloatingActionButton.hideStrategy;
        } else if (i == 2) {
            baseMotionStrategy = extendedFloatingActionButton.shrinkStrategy;
        } else {
            if (i != 3) {
                throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Unknown strategy type: "));
            }
            baseMotionStrategy = extendedFloatingActionButton.extendStrategy;
        }
        if (baseMotionStrategy.shouldCancel()) {
            return;
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (!extendedFloatingActionButton.isLaidOut()) {
            extendedFloatingActionButton.getVisibility();
        } else if (!extendedFloatingActionButton.isInEditMode()) {
            if (i == 2) {
                ViewGroup.LayoutParams layoutParams = extendedFloatingActionButton.getLayoutParams();
                if (layoutParams != null) {
                    extendedFloatingActionButton.originalWidth = layoutParams.width;
                    extendedFloatingActionButton.originalHeight = layoutParams.height;
                } else {
                    extendedFloatingActionButton.originalWidth = extendedFloatingActionButton.getWidth();
                    extendedFloatingActionButton.originalHeight = extendedFloatingActionButton.getHeight();
                }
            }
            int i2 = 0;
            extendedFloatingActionButton.measure(0, 0);
            AnimatorSet createAnimator = baseMotionStrategy.createAnimator();
            createAnimator.addListener(new AnimatorListenerAdapter(extendedFloatingActionButton) { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.5
                public boolean cancelled;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                    this.cancelled = true;
                    baseMotionStrategy.onAnimationCancel();
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    baseMotionStrategy.onAnimationEnd();
                    if (this.cancelled) {
                        return;
                    }
                    baseMotionStrategy.getClass();
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    baseMotionStrategy.onAnimationStart(animator);
                    this.cancelled = false;
                }
            });
            ArrayList arrayList = baseMotionStrategy.listeners;
            int size = arrayList.size();
            while (i2 < size) {
                Object obj = arrayList.get(i2);
                i2++;
                createAnimator.addListener((Animator.AnimatorListener) obj);
            }
            createAnimator.start();
            return;
        }
        baseMotionStrategy.performNow();
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.AttachedBehavior
    public final CoordinatorLayout.Behavior getBehavior() {
        return this.behavior;
    }

    public int getCollapsedSize() {
        int i = this.collapsedSize;
        if (i >= 0) {
            return i;
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        return (Math.min(getPaddingStart(), getPaddingEnd()) * 2) + this.iconSize;
    }

    @Override // com.google.android.material.button.MaterialButton, androidx.appcompat.widget.AppCompatButton, android.widget.TextView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.isExtended && TextUtils.isEmpty(getText()) && this.icon != null) {
            this.isExtended = false;
            this.shrinkStrategy.performNow();
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final void setPadding(int i, int i2, int i3, int i4) {
        super.setPadding(i, i2, i3, i4);
        if (!this.isExtended || this.isTransforming) {
            return;
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        this.extendedPaddingStart = getPaddingStart();
        this.extendedPaddingEnd = getPaddingEnd();
    }

    @Override // android.widget.TextView, android.view.View
    public final void setPaddingRelative(int i, int i2, int i3, int i4) {
        super.setPaddingRelative(i, i2, i3, i4);
        if (!this.isExtended || this.isTransforming) {
            return;
        }
        this.extendedPaddingStart = i;
        this.extendedPaddingEnd = i3;
    }

    @Override // android.widget.TextView
    public final void setTextColor(int i) {
        super.setTextColor(i);
        this.originalTextCsl = getTextColors();
    }

    public final void silentlyUpdateTextColor(ColorStateList colorStateList) {
        super.setTextColor(colorStateList);
    }

    public ExtendedFloatingActionButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.extendedFloatingActionButtonStyle);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ExtendedFloatingActionButtonBehavior<T extends ExtendedFloatingActionButton> extends CoordinatorLayout.Behavior {
        public final boolean autoHideEnabled;
        public final boolean autoShrinkEnabled;
        public OnChangedCallback internalAutoHideCallback;
        public OnChangedCallback internalAutoShrinkCallback;
        public Rect tmpRect;

        public ExtendedFloatingActionButtonBehavior() {
            this.autoHideEnabled = false;
            this.autoShrinkEnabled = true;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final /* bridge */ /* synthetic */ boolean getInsetDodgeRect(Rect rect, View view) {
            return false;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final void onAttachedToLayoutParams(CoordinatorLayout.LayoutParams layoutParams) {
            if (layoutParams.dodgeInsetEdges == 0) {
                layoutParams.dodgeInsetEdges = 80;
            }
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
            ExtendedFloatingActionButton extendedFloatingActionButton = (ExtendedFloatingActionButton) view;
            if (view2 instanceof AppBarLayout) {
                updateFabVisibilityForAppBarLayout(coordinatorLayout, (AppBarLayout) view2, extendedFloatingActionButton);
            } else {
                ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
                if (layoutParams instanceof CoordinatorLayout.LayoutParams ? ((CoordinatorLayout.LayoutParams) layoutParams).mBehavior instanceof BottomSheetBehavior : false) {
                    updateFabVisibilityForBottomSheet(view2, extendedFloatingActionButton);
                }
            }
            return false;
        }

        @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view, int i) {
            ExtendedFloatingActionButton extendedFloatingActionButton = (ExtendedFloatingActionButton) view;
            List dependencies = coordinatorLayout.getDependencies(extendedFloatingActionButton);
            int size = dependencies.size();
            for (int i2 = 0; i2 < size; i2++) {
                View view2 = (View) dependencies.get(i2);
                if (!(view2 instanceof AppBarLayout)) {
                    ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
                    if ((layoutParams instanceof CoordinatorLayout.LayoutParams ? ((CoordinatorLayout.LayoutParams) layoutParams).mBehavior instanceof BottomSheetBehavior : false) && updateFabVisibilityForBottomSheet(view2, extendedFloatingActionButton)) {
                        break;
                    }
                } else {
                    if (updateFabVisibilityForAppBarLayout(coordinatorLayout, (AppBarLayout) view2, extendedFloatingActionButton)) {
                        break;
                    }
                }
            }
            coordinatorLayout.onLayoutChild(extendedFloatingActionButton, i);
            return true;
        }

        public void setInternalAutoHideCallback(OnChangedCallback onChangedCallback) {
            this.internalAutoHideCallback = onChangedCallback;
        }

        public void setInternalAutoShrinkCallback(OnChangedCallback onChangedCallback) {
            this.internalAutoShrinkCallback = onChangedCallback;
        }

        public final boolean updateFabVisibilityForAppBarLayout(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, ExtendedFloatingActionButton extendedFloatingActionButton) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) extendedFloatingActionButton.getLayoutParams();
            if ((!this.autoHideEnabled && !this.autoShrinkEnabled) || layoutParams.mAnchorId != appBarLayout.getId()) {
                return false;
            }
            if (this.tmpRect == null) {
                this.tmpRect = new Rect();
            }
            Rect rect = this.tmpRect;
            DescendantOffsetUtils.getDescendantRect(coordinatorLayout, appBarLayout, rect);
            if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
                boolean z = this.autoShrinkEnabled;
                ExtendedFloatingActionButton.access$400(extendedFloatingActionButton, z ? 2 : 1, z ? this.internalAutoShrinkCallback : this.internalAutoHideCallback);
            } else {
                boolean z2 = this.autoShrinkEnabled;
                ExtendedFloatingActionButton.access$400(extendedFloatingActionButton, z2 ? 3 : 0, z2 ? this.internalAutoShrinkCallback : this.internalAutoHideCallback);
            }
            return true;
        }

        public final boolean updateFabVisibilityForBottomSheet(View view, ExtendedFloatingActionButton extendedFloatingActionButton) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) extendedFloatingActionButton.getLayoutParams();
            if ((!this.autoHideEnabled && !this.autoShrinkEnabled) || layoutParams.mAnchorId != view.getId()) {
                return false;
            }
            if (view.getTop() < (extendedFloatingActionButton.getHeight() / 2) + ((ViewGroup.MarginLayoutParams) ((CoordinatorLayout.LayoutParams) extendedFloatingActionButton.getLayoutParams())).topMargin) {
                boolean z = this.autoShrinkEnabled;
                ExtendedFloatingActionButton.access$400(extendedFloatingActionButton, z ? 2 : 1, z ? this.internalAutoShrinkCallback : this.internalAutoHideCallback);
            } else {
                boolean z2 = this.autoShrinkEnabled;
                ExtendedFloatingActionButton.access$400(extendedFloatingActionButton, z2 ? 3 : 0, z2 ? this.internalAutoShrinkCallback : this.internalAutoHideCallback);
            }
            return true;
        }

        public ExtendedFloatingActionButtonBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ExtendedFloatingActionButton_Behavior_Layout);
            this.autoHideEnabled = obtainStyledAttributes.getBoolean(0, false);
            this.autoShrinkEnabled = obtainStyledAttributes.getBoolean(1, true);
            obtainStyledAttributes.recycle();
        }
    }

    public ExtendedFloatingActionButton(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, R.style.Widget_MaterialComponents_ExtendedFloatingActionButton_Icon), attributeSet, i);
        this.animState = 0;
        AnimatorTracker animatorTracker = new AnimatorTracker();
        ShowStrategy showStrategy = new ShowStrategy(animatorTracker);
        this.showStrategy = showStrategy;
        HideStrategy hideStrategy = new HideStrategy(animatorTracker);
        this.hideStrategy = hideStrategy;
        this.isExtended = true;
        this.isTransforming = false;
        Context context2 = getContext();
        this.behavior = new ExtendedFloatingActionButtonBehavior(context2, attributeSet);
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R$styleable.ExtendedFloatingActionButton, i, R.style.Widget_MaterialComponents_ExtendedFloatingActionButton_Icon, new int[0]);
        MotionSpec createFromAttribute = MotionSpec.createFromAttribute(context2, obtainStyledAttributes, 5);
        MotionSpec createFromAttribute2 = MotionSpec.createFromAttribute(context2, obtainStyledAttributes, 4);
        MotionSpec createFromAttribute3 = MotionSpec.createFromAttribute(context2, obtainStyledAttributes, 2);
        MotionSpec createFromAttribute4 = MotionSpec.createFromAttribute(context2, obtainStyledAttributes, 6);
        this.collapsedSize = obtainStyledAttributes.getDimensionPixelSize(0, -1);
        int i2 = obtainStyledAttributes.getInt(3, 1);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        this.extendedPaddingStart = getPaddingStart();
        this.extendedPaddingEnd = getPaddingEnd();
        AnimatorTracker animatorTracker2 = new AnimatorTracker();
        final Size size = new Size() { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.2
            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getHeight() {
                return ExtendedFloatingActionButton.this.getMeasuredHeight();
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final ViewGroup.LayoutParams getLayoutParams() {
                return new ViewGroup.LayoutParams(-2, -2);
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getPaddingEnd() {
                return ExtendedFloatingActionButton.this.extendedPaddingEnd;
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getPaddingStart() {
                return ExtendedFloatingActionButton.this.extendedPaddingStart;
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getWidth() {
                ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
                return (extendedFloatingActionButton.getMeasuredWidth() - (((extendedFloatingActionButton.getCollapsedSize() - extendedFloatingActionButton.iconSize) / 2) * 2)) + extendedFloatingActionButton.extendedPaddingStart + extendedFloatingActionButton.extendedPaddingEnd;
            }
        };
        final Size size2 = new Size() { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.3
            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getHeight() {
                ViewGroup.MarginLayoutParams marginLayoutParams;
                ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
                int i3 = extendedFloatingActionButton.originalHeight;
                Size size3 = size;
                if (i3 != -1) {
                    return (i3 == 0 || i3 == -2) ? size3.getHeight() : i3;
                }
                if (!(extendedFloatingActionButton.getParent() instanceof View)) {
                    return size3.getHeight();
                }
                View view = (View) extendedFloatingActionButton.getParent();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams == null || layoutParams.height != -2) {
                    return (view.getHeight() - ((!(extendedFloatingActionButton.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) || (marginLayoutParams = (ViewGroup.MarginLayoutParams) extendedFloatingActionButton.getLayoutParams()) == null) ? 0 : marginLayoutParams.topMargin + marginLayoutParams.bottomMargin)) - (view.getPaddingBottom() + view.getPaddingTop());
                }
                return size3.getHeight();
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final ViewGroup.LayoutParams getLayoutParams() {
                int i3 = ExtendedFloatingActionButton.this.originalHeight;
                if (i3 == 0) {
                    i3 = -2;
                }
                return new ViewGroup.LayoutParams(-1, i3);
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getPaddingEnd() {
                return ExtendedFloatingActionButton.this.extendedPaddingEnd;
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getPaddingStart() {
                return ExtendedFloatingActionButton.this.extendedPaddingStart;
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getWidth() {
                ViewGroup.MarginLayoutParams marginLayoutParams;
                ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
                boolean z = extendedFloatingActionButton.getParent() instanceof View;
                Size size3 = size;
                if (!z) {
                    return size3.getWidth();
                }
                View view = (View) extendedFloatingActionButton.getParent();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams == null || layoutParams.width != -2) {
                    return (view.getWidth() - ((!(extendedFloatingActionButton.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) || (marginLayoutParams = (ViewGroup.MarginLayoutParams) extendedFloatingActionButton.getLayoutParams()) == null) ? 0 : marginLayoutParams.leftMargin + marginLayoutParams.rightMargin)) - (view.getPaddingRight() + view.getPaddingLeft());
                }
                return size3.getWidth();
            }
        };
        Size size3 = new Size() { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.4
            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getHeight() {
                int i3 = ExtendedFloatingActionButton.this.originalHeight;
                return i3 == -1 ? size2.getHeight() : (i3 == 0 || i3 == -2) ? size.getHeight() : i3;
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final ViewGroup.LayoutParams getLayoutParams() {
                ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
                int i3 = extendedFloatingActionButton.originalWidth;
                if (i3 == 0) {
                    i3 = -2;
                }
                int i4 = extendedFloatingActionButton.originalHeight;
                return new ViewGroup.LayoutParams(i3, i4 != 0 ? i4 : -2);
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getPaddingEnd() {
                return ExtendedFloatingActionButton.this.extendedPaddingEnd;
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getPaddingStart() {
                return ExtendedFloatingActionButton.this.extendedPaddingStart;
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getWidth() {
                int i3 = ExtendedFloatingActionButton.this.originalWidth;
                return i3 == -1 ? size2.getWidth() : (i3 == 0 || i3 == -2) ? size.getWidth() : i3;
            }
        };
        boolean z = true;
        if (i2 != 1) {
            size = i2 != 2 ? size3 : size2;
            z = true;
        }
        ChangeSizeStrategy changeSizeStrategy = new ChangeSizeStrategy(animatorTracker2, size, z);
        this.extendStrategy = changeSizeStrategy;
        ChangeSizeStrategy changeSizeStrategy2 = new ChangeSizeStrategy(animatorTracker2, new Size() { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.1
            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getHeight() {
                return ExtendedFloatingActionButton.this.getCollapsedSize();
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final ViewGroup.LayoutParams getLayoutParams() {
                ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
                return new ViewGroup.LayoutParams(extendedFloatingActionButton.getCollapsedSize(), extendedFloatingActionButton.getCollapsedSize());
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getPaddingEnd() {
                ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
                return (extendedFloatingActionButton.getCollapsedSize() - extendedFloatingActionButton.iconSize) / 2;
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getPaddingStart() {
                ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
                return (extendedFloatingActionButton.getCollapsedSize() - extendedFloatingActionButton.iconSize) / 2;
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getWidth() {
                return ExtendedFloatingActionButton.this.getCollapsedSize();
            }
        }, false);
        this.shrinkStrategy = changeSizeStrategy2;
        showStrategy.motionSpec = createFromAttribute;
        hideStrategy.motionSpec = createFromAttribute2;
        changeSizeStrategy.motionSpec = createFromAttribute3;
        changeSizeStrategy2.motionSpec = createFromAttribute4;
        obtainStyledAttributes.recycle();
        setShapeAppearanceModel(ShapeAppearanceModel.builder(context2, attributeSet, i, R.style.Widget_MaterialComponents_ExtendedFloatingActionButton_Icon, ShapeAppearanceModel.PILL).build());
        this.originalTextCsl = getTextColors();
    }

    @Override // android.widget.TextView
    public final void setTextColor(ColorStateList colorStateList) {
        super.setTextColor(colorStateList);
        this.originalTextCsl = getTextColors();
    }
}
