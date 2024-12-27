package com.android.systemui.scene.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Insets;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.DisplayCutout;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;
import com.android.compose.animation.ViewTreeSavedStateRegistryOwner;
import com.android.systemui.LsRune;
import com.android.systemui.compose.ComposeInitializer;
import com.android.systemui.lifecycle.ViewLifecycleOwner;
import com.android.systemui.res.R$styleable;
import com.android.systemui.statusbar.NotificationInsetsController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class WindowRootView extends FrameLayout {
    public NotificationInsetsController layoutInsetsController;
    public int leftInset;
    public boolean mBouncerShowing;
    public int rightInset;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class LayoutParams extends FrameLayout.LayoutParams {
        public final boolean ignoreRightInset;

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.StatusBarWindowView_Layout);
            this.ignoreRightInset = obtainStyledAttributes.getBoolean(0, false);
            obtainStyledAttributes.recycle();
        }
    }

    public WindowRootView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void applyBouncerMargins() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getLayoutParams() instanceof LayoutParams) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (!layoutParams.ignoreRightInset && (((FrameLayout.LayoutParams) layoutParams).rightMargin != 0 || ((FrameLayout.LayoutParams) layoutParams).leftMargin != 0)) {
                    layoutParams.setMargins(0, ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, 0, ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
                    childAt.requestLayout();
                }
            }
        }
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        Insets insetsIgnoringVisibility = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
        if (getFitsSystemWindows()) {
            if (insetsIgnoringVisibility.top != getPaddingTop() || insetsIgnoringVisibility.bottom != getPaddingBottom()) {
                setPadding(0, 0, 0, 0);
            }
        } else if (getPaddingLeft() != 0 || getPaddingRight() != 0 || getPaddingTop() != 0 || getPaddingBottom() != 0) {
            setPadding(0, 0, 0, 0);
        }
        this.leftInset = 0;
        this.rightInset = 0;
        DisplayCutout displayCutout = getRootWindowInsets().getDisplayCutout();
        NotificationInsetsController notificationInsetsController = this.layoutInsetsController;
        if (notificationInsetsController == null) {
            notificationInsetsController = null;
        }
        Pair pair = notificationInsetsController.getinsets(windowInsets, displayCutout);
        this.leftInset = ((Number) pair.first).intValue();
        this.rightInset = ((Number) pair.second).intValue();
        if (LsRune.SECURITY_CAPTURED_BLUR && this.mBouncerShowing) {
            applyBouncerMargins();
        } else {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                if (childAt.getLayoutParams() instanceof LayoutParams) {
                    LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                    if (!layoutParams.ignoreRightInset) {
                        int i2 = ((FrameLayout.LayoutParams) layoutParams).rightMargin;
                        int i3 = this.rightInset;
                        if (i2 != i3 || ((FrameLayout.LayoutParams) layoutParams).leftMargin != this.leftInset) {
                            layoutParams.setMargins(this.leftInset, ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, i3, ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
                            childAt.requestLayout();
                        }
                    }
                }
            }
        }
        return windowInsets;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Object parent = getParent();
        if (!(parent instanceof View) || ((View) parent).getId() == 16908290) {
            ComposeInitializer.INSTANCE.getClass();
            if (ViewTreeLifecycleOwner.get(this) != null) {
                throw new IllegalStateException(("root " + this + " already has a LifecycleOwner").toString());
            }
            Object parent2 = getParent();
            if ((parent2 instanceof View) && ((View) parent2).getId() != 16908290) {
                throw new IllegalStateException("ComposeInitializer.onAttachedToWindow(View) must be called on the content child.Outside of activities and dialogs, this is usually the top-most View of a window.".toString());
            }
            final ViewLifecycleOwner viewLifecycleOwner = new ViewLifecycleOwner(this);
            SavedStateRegistryOwner savedStateRegistryOwner = new SavedStateRegistryOwner() { // from class: com.android.systemui.compose.ComposeInitializer$onAttachedToWindow$savedStateRegistryOwner$1
                public final SavedStateRegistry savedStateRegistry;

                {
                    SavedStateRegistryController.Companion.getClass();
                    SavedStateRegistryController create = SavedStateRegistryController.Companion.create(this);
                    create.performRestore(null);
                    this.savedStateRegistry = create.savedStateRegistry;
                }

                @Override // androidx.lifecycle.LifecycleOwner
                public final Lifecycle getLifecycle() {
                    return ViewLifecycleOwner.this.registry;
                }

                @Override // androidx.savedstate.SavedStateRegistryOwner
                public final SavedStateRegistry getSavedStateRegistry() {
                    return this.savedStateRegistry;
                }
            };
            viewLifecycleOwner.onCreate();
            ViewTreeLifecycleOwner.set(this, viewLifecycleOwner);
            ViewTreeSavedStateRegistryOwner.INSTANCE.getClass();
            androidx.savedstate.ViewTreeSavedStateRegistryOwner.set(this, savedStateRegistryOwner);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Object parent = getParent();
        if (!(parent instanceof View) || ((View) parent).getId() == 16908290) {
            ComposeInitializer.INSTANCE.getClass();
            ((ViewLifecycleOwner) ViewTreeLifecycleOwner.get(this)).onDestroy();
            ViewTreeLifecycleOwner.set(this, null);
            ViewTreeSavedStateRegistryOwner.INSTANCE.getClass();
            androidx.savedstate.ViewTreeSavedStateRegistryOwner.set(this, null);
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final FrameLayout.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }
}
