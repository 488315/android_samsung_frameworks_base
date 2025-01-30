package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
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
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ExtendedFloatingActionButton extends MaterialButton implements CoordinatorLayout.AttachedBehavior {
    public static final C42855 HEIGHT;
    public static final C42877 PADDING_END;
    public static final C42866 PADDING_START;
    public static final C42844 WIDTH;
    public int animState;
    public final ExtendedFloatingActionButtonBehavior behavior;
    public final int collapsedSize;
    public final ChangeSizeStrategy extendStrategy;
    public int extendedPaddingEnd;
    public int extendedPaddingStart;
    public final HideStrategy hideStrategy;
    public boolean isExtended;
    public boolean isTransforming;
    public ColorStateList originalTextCsl;
    public final ShowStrategy showStrategy;
    public final ChangeSizeStrategy shrinkStrategy;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract class OnChangedCallback {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Size {
        int getHeight();

        ViewGroup.LayoutParams getLayoutParams();

        int getPaddingEnd();

        int getPaddingStart();

        int getWidth();
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$4] */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$5] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$6] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton$7] */
    static {
        Class<Float> cls = Float.class;
        WIDTH = new Property(cls, "width") { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.4
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
        HEIGHT = new Property(cls, "height") { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.5
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
        PADDING_START = new Property(cls, "paddingStart") { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.6
            @Override // android.util.Property
            public final Object get(Object obj) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                return Float.valueOf(ViewCompat.Api17Impl.getPaddingStart((View) obj));
            }

            @Override // android.util.Property
            public final void set(Object obj, Object obj2) {
                View view = (View) obj;
                int intValue = ((Float) obj2).intValue();
                int paddingTop = view.getPaddingTop();
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api17Impl.setPaddingRelative(view, intValue, paddingTop, ViewCompat.Api17Impl.getPaddingEnd(view), view.getPaddingBottom());
            }
        };
        PADDING_END = new Property(cls, "paddingEnd") { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.7
            @Override // android.util.Property
            public final Object get(Object obj) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                return Float.valueOf(ViewCompat.Api17Impl.getPaddingEnd((View) obj));
            }

            @Override // android.util.Property
            public final void set(Object obj, Object obj2) {
                View view = (View) obj;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api17Impl.setPaddingRelative(view, ViewCompat.Api17Impl.getPaddingStart(view), view.getPaddingTop(), ((Float) obj2).intValue(), view.getPaddingBottom());
            }
        };
    }

    public ExtendedFloatingActionButton(Context context) {
        this(context, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void access$400(ExtendedFloatingActionButton extendedFloatingActionButton, final BaseMotionStrategy baseMotionStrategy, final OnChangedCallback onChangedCallback) {
        boolean z;
        if (baseMotionStrategy.shouldCancel()) {
            return;
        }
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (!ViewCompat.Api19Impl.isLaidOut(extendedFloatingActionButton)) {
            extendedFloatingActionButton.getVisibility();
        } else if (!extendedFloatingActionButton.isInEditMode()) {
            z = true;
            if (z) {
                baseMotionStrategy.performNow();
                baseMotionStrategy.onChange();
                return;
            }
            extendedFloatingActionButton.measure(0, 0);
            AnimatorSet createAnimator = baseMotionStrategy.createAnimator();
            createAnimator.addListener(new AnimatorListenerAdapter(extendedFloatingActionButton) { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.3
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
                    baseMotionStrategy.onChange();
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    baseMotionStrategy.onAnimationStart(animator);
                    this.cancelled = false;
                }
            });
            Iterator it = baseMotionStrategy.listeners.iterator();
            while (it.hasNext()) {
                createAnimator.addListener((Animator.AnimatorListener) it.next());
            }
            createAnimator.start();
            return;
        }
        z = false;
        if (z) {
        }
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
        return (Math.min(ViewCompat.Api17Impl.getPaddingStart(this), ViewCompat.Api17Impl.getPaddingEnd(this)) * 2) + this.iconSize;
    }

    @Override // com.google.android.material.button.MaterialButton, android.widget.TextView, android.view.View
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
        this.extendedPaddingStart = ViewCompat.Api17Impl.getPaddingStart(this);
        this.extendedPaddingEnd = ViewCompat.Api17Impl.getPaddingEnd(this);
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class ExtendedFloatingActionButtonBehavior<T extends ExtendedFloatingActionButton> extends CoordinatorLayout.Behavior<T> {
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
        public final /* bridge */ /* synthetic */ boolean getInsetDodgeRect(View view, Rect rect) {
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
            boolean z = this.autoHideEnabled;
            boolean z2 = this.autoShrinkEnabled;
            if (!((z || z2) && layoutParams.mAnchorId == appBarLayout.getId())) {
                return false;
            }
            if (this.tmpRect == null) {
                this.tmpRect = new Rect();
            }
            Rect rect = this.tmpRect;
            DescendantOffsetUtils.getDescendantRect(coordinatorLayout, appBarLayout, rect);
            if (rect.bottom <= appBarLayout.getMinimumHeightForVisibleOverlappingContent()) {
                ExtendedFloatingActionButton.access$400(extendedFloatingActionButton, z2 ? extendedFloatingActionButton.shrinkStrategy : extendedFloatingActionButton.hideStrategy, z2 ? this.internalAutoShrinkCallback : this.internalAutoHideCallback);
            } else {
                ExtendedFloatingActionButton.access$400(extendedFloatingActionButton, z2 ? extendedFloatingActionButton.extendStrategy : extendedFloatingActionButton.showStrategy, z2 ? this.internalAutoShrinkCallback : this.internalAutoHideCallback);
            }
            return true;
        }

        public final boolean updateFabVisibilityForBottomSheet(View view, ExtendedFloatingActionButton extendedFloatingActionButton) {
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) extendedFloatingActionButton.getLayoutParams();
            boolean z = this.autoHideEnabled;
            boolean z2 = this.autoShrinkEnabled;
            if (!((z || z2) && layoutParams.mAnchorId == view.getId())) {
                return false;
            }
            if (view.getTop() < (extendedFloatingActionButton.getHeight() / 2) + ((ViewGroup.MarginLayoutParams) ((CoordinatorLayout.LayoutParams) extendedFloatingActionButton.getLayoutParams())).topMargin) {
                ExtendedFloatingActionButton.access$400(extendedFloatingActionButton, z2 ? extendedFloatingActionButton.shrinkStrategy : extendedFloatingActionButton.hideStrategy, z2 ? this.internalAutoShrinkCallback : this.internalAutoHideCallback);
            } else {
                ExtendedFloatingActionButton.access$400(extendedFloatingActionButton, z2 ? extendedFloatingActionButton.extendStrategy : extendedFloatingActionButton.showStrategy, z2 ? this.internalAutoShrinkCallback : this.internalAutoHideCallback);
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
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 2132019112), attributeSet, i);
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
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R$styleable.ExtendedFloatingActionButton, i, 2132019112, new int[0]);
        MotionSpec createFromAttribute = MotionSpec.createFromAttribute(context2, obtainStyledAttributes, 4);
        MotionSpec createFromAttribute2 = MotionSpec.createFromAttribute(context2, obtainStyledAttributes, 3);
        MotionSpec createFromAttribute3 = MotionSpec.createFromAttribute(context2, obtainStyledAttributes, 2);
        MotionSpec createFromAttribute4 = MotionSpec.createFromAttribute(context2, obtainStyledAttributes, 5);
        this.collapsedSize = obtainStyledAttributes.getDimensionPixelSize(0, -1);
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        this.extendedPaddingStart = ViewCompat.Api17Impl.getPaddingStart(this);
        this.extendedPaddingEnd = ViewCompat.Api17Impl.getPaddingEnd(this);
        AnimatorTracker animatorTracker2 = new AnimatorTracker();
        ChangeSizeStrategy changeSizeStrategy = new ChangeSizeStrategy(animatorTracker2, new Size() { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.1
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
        }, true);
        this.extendStrategy = changeSizeStrategy;
        ChangeSizeStrategy changeSizeStrategy2 = new ChangeSizeStrategy(animatorTracker2, new Size() { // from class: com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.2
            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final int getHeight() {
                return ExtendedFloatingActionButton.this.getCollapsedSize();
            }

            @Override // com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.Size
            public final ViewGroup.LayoutParams getLayoutParams() {
                return new ViewGroup.LayoutParams(getWidth(), getHeight());
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
        setShapeAppearanceModel(ShapeAppearanceModel.builder(context2, attributeSet, i, 2132019112, ShapeAppearanceModel.PILL).build());
        this.originalTextCsl = getTextColors();
    }

    @Override // android.widget.TextView
    public final void setTextColor(ColorStateList colorStateList) {
        super.setTextColor(colorStateList);
        this.originalTextCsl = getTextColors();
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ChangeSizeStrategy extends BaseMotionStrategy {
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
                propertyValuesHolder.setFloatValues(ViewCompat.Api17Impl.getPaddingStart(extendedFloatingActionButton), size.getPaddingStart());
                motionSpec.setPropertyValues("paddingStart", propertyValues3);
            }
            if (motionSpec.hasPropertyValues("paddingEnd")) {
                PropertyValuesHolder[] propertyValues4 = motionSpec.getPropertyValues("paddingEnd");
                PropertyValuesHolder propertyValuesHolder2 = propertyValues4[0];
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                propertyValuesHolder2.setFloatValues(ViewCompat.Api17Impl.getPaddingEnd(extendedFloatingActionButton), size.getPaddingEnd());
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
            boolean z = this.extending;
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            extendedFloatingActionButton.isExtended = z;
            extendedFloatingActionButton.isTransforming = true;
            extendedFloatingActionButton.setHorizontallyScrolling(true);
        }

        @Override // com.google.android.material.floatingactionbutton.MotionStrategy
        public final void performNow() {
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            extendedFloatingActionButton.isExtended = this.extending;
            ViewGroup.LayoutParams layoutParams = extendedFloatingActionButton.getLayoutParams();
            if (layoutParams == null) {
                return;
            }
            Size size = this.size;
            layoutParams.width = size.getLayoutParams().width;
            layoutParams.height = size.getLayoutParams().height;
            int paddingStart = size.getPaddingStart();
            int paddingTop = extendedFloatingActionButton.getPaddingTop();
            int paddingEnd = size.getPaddingEnd();
            int paddingBottom = extendedFloatingActionButton.getPaddingBottom();
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api17Impl.setPaddingRelative(extendedFloatingActionButton, paddingStart, paddingTop, paddingEnd, paddingBottom);
            extendedFloatingActionButton.requestLayout();
        }

        @Override // com.google.android.material.floatingactionbutton.MotionStrategy
        public final boolean shouldCancel() {
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            return this.extending == extendedFloatingActionButton.isExtended || extendedFloatingActionButton.icon == null || TextUtils.isEmpty(extendedFloatingActionButton.getText());
        }

        @Override // com.google.android.material.floatingactionbutton.MotionStrategy
        public final void onChange() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class HideStrategy extends BaseMotionStrategy {
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
            C42844 c42844 = ExtendedFloatingActionButton.WIDTH;
            ExtendedFloatingActionButton extendedFloatingActionButton = ExtendedFloatingActionButton.this;
            if (extendedFloatingActionButton.getVisibility() == 0) {
                if (extendedFloatingActionButton.animState == 1) {
                    return true;
                }
            } else if (extendedFloatingActionButton.animState != 2) {
                return true;
            }
            return false;
        }

        @Override // com.google.android.material.floatingactionbutton.MotionStrategy
        public final void onChange() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ShowStrategy extends BaseMotionStrategy {
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
            C42844 c42844 = ExtendedFloatingActionButton.WIDTH;
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

        @Override // com.google.android.material.floatingactionbutton.MotionStrategy
        public final void onChange() {
        }
    }
}
