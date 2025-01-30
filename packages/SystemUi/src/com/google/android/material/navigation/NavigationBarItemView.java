package com.google.android.material.navigation;

import android.R;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.StateSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.R$styleable;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.content.ContextCompat;
import androidx.core.view.PointerIconCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.ripple.RippleUtils;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class NavigationBarItemView extends FrameLayout implements MenuView.ItemView {
    public static final ActiveIndicatorTransform ACTIVE_INDICATOR_LABELED_TRANSFORM;
    public static final ActiveIndicatorUnlabeledTransform ACTIVE_INDICATOR_UNLABELED_TRANSFORM;
    public static final int[] CHECKED_STATE_SET = {R.attr.state_checked};
    public final String TAG;
    public ValueAnimator activeIndicatorAnimator;
    public int activeIndicatorDesiredHeight;
    public int activeIndicatorDesiredWidth;
    public boolean activeIndicatorEnabled;
    public int activeIndicatorMarginHorizontal;
    public float activeIndicatorProgress;
    public boolean activeIndicatorResizeable;
    public ActiveIndicatorTransform activeIndicatorTransform;
    public final View activeIndicatorView;
    public BadgeDrawable badgeDrawable;
    public int defaultMargin;
    public final ImageView icon;
    public final FrameLayout iconContainer;
    public ColorStateList iconTint;
    public boolean initialized;
    public boolean isShifting;
    public Drawable itemBackground;
    public MenuItemImpl itemData;
    public int itemPaddingBottom;
    public int itemPaddingTop;
    public ColorStateList itemRippleColor;
    public final ViewGroup labelGroup;
    public int labelVisibilityMode;
    public final TextView largeLabel;
    public int mBadgeType;
    public boolean mIsBadgeNumberless;
    public SpannableStringBuilder mLabelImgSpan;
    public int mLargeLabelAppearance;
    public int mSmallLabelAppearance;
    public final int mViewType;
    public Drawable originalIconDrawable;
    public float scaleDownFactor;
    public float scaleUpFactor;
    public float shiftAmount;
    public final TextView smallLabel;
    public Drawable wrappedIconDrawable;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class ActiveIndicatorTransform {
        private ActiveIndicatorTransform() {
        }

        public float calculateScaleY(float f, float f2) {
            return 1.0f;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ActiveIndicatorUnlabeledTransform extends ActiveIndicatorTransform {
        private ActiveIndicatorUnlabeledTransform() {
            super();
        }

        @Override // com.google.android.material.navigation.NavigationBarItemView.ActiveIndicatorTransform
        public final float calculateScaleY(float f, float f2) {
            TimeInterpolator timeInterpolator = AnimationUtils.LINEAR_INTERPOLATOR;
            return (f * 0.6f) + 0.4f;
        }
    }

    static {
        ACTIVE_INDICATOR_LABELED_TRANSFORM = new ActiveIndicatorTransform();
        ACTIVE_INDICATOR_UNLABELED_TRANSFORM = new ActiveIndicatorUnlabeledTransform();
    }

    public NavigationBarItemView(Context context) {
        this(context, null, 1);
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:8:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void setTextAppearanceWithoutFontScaling(int i, TextView textView) {
        int round;
        textView.setTextAppearance(i);
        Context context = textView.getContext();
        if (i != 0) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(i, R$styleable.TextAppearance);
            TypedValue typedValue = new TypedValue();
            boolean value = obtainStyledAttributes.getValue(0, typedValue);
            obtainStyledAttributes.recycle();
            if (value) {
                round = typedValue.getComplexUnit() == 2 ? Math.round(TypedValue.complexToFloat(typedValue.data) * context.getResources().getDisplayMetrics().density) : TypedValue.complexToDimensionPixelSize(typedValue.data, context.getResources().getDisplayMetrics());
                if (round == 0) {
                    textView.setTextSize(0, round);
                    return;
                }
                return;
            }
        }
        round = 0;
        if (round == 0) {
        }
    }

    public static void setViewScaleValues(float f, float f2, int i, View view) {
        view.setScaleX(f);
        view.setScaleY(f2);
        view.setVisibility(i);
    }

    public static void setViewTopMarginAndGravity(View view, int i, int i2) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        layoutParams.topMargin = i;
        layoutParams.bottomMargin = i;
        layoutParams.gravity = i2;
        view.setLayoutParams(layoutParams);
    }

    public static void updateViewPaddingBottom(View view, int i) {
        view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), i);
    }

    public final void calculateTextScaleFactors(float f, float f2) {
        if (f2 == 0.0f || f == 0.0f) {
            Log.e(this.TAG, "LabelSize is invalid");
            this.scaleUpFactor = 1.0f;
            this.scaleDownFactor = 1.0f;
            this.shiftAmount = 0.0f;
            return;
        }
        this.shiftAmount = f - f2;
        float f3 = (f2 * 1.0f) / f;
        this.scaleUpFactor = f3;
        this.scaleDownFactor = (f * 1.0f) / f2;
        if (f3 >= Float.MAX_VALUE || f3 <= -3.4028235E38f) {
            Log.e(this.TAG, "scaleUpFactor is invalid");
            this.scaleUpFactor = 1.0f;
            this.shiftAmount = 0.0f;
        }
        float f4 = this.scaleDownFactor;
        if (f4 >= Float.MAX_VALUE || f4 <= -3.4028235E38f) {
            Log.e(this.TAG, "scaleDownFactor is invalid");
            this.scaleDownFactor = 1.0f;
            this.shiftAmount = 0.0f;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        FrameLayout frameLayout = this.iconContainer;
        if (frameLayout != null && this.activeIndicatorEnabled) {
            frameLayout.dispatchTouchEvent(motionEvent);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public final View getIconOrContainer() {
        FrameLayout frameLayout = this.iconContainer;
        return frameLayout != null ? frameLayout : this.icon;
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public final MenuItemImpl getItemData() {
        return this.itemData;
    }

    public abstract int getItemLayoutResId();

    @Override // android.view.View
    public final int getSuggestedMinimumHeight() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.labelGroup.getLayoutParams();
        BadgeDrawable badgeDrawable = this.badgeDrawable;
        int minimumHeight = badgeDrawable != null ? badgeDrawable.getMinimumHeight() / 2 : 0;
        return this.labelGroup.getMeasuredHeight() + this.icon.getMeasuredWidth() + Math.max(minimumHeight, ((FrameLayout.LayoutParams) getIconOrContainer().getLayoutParams()).topMargin) + minimumHeight + layoutParams.topMargin + layoutParams.bottomMargin;
    }

    @Override // android.view.View
    public final int getSuggestedMinimumWidth() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.labelGroup.getLayoutParams();
        int measuredWidth = this.labelGroup.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
        BadgeDrawable badgeDrawable = this.badgeDrawable;
        int minimumWidth = badgeDrawable == null ? 0 : badgeDrawable.getMinimumWidth() - this.badgeDrawable.state.currentState.horizontalOffsetWithoutText.intValue();
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) getIconOrContainer().getLayoutParams();
        return Math.max(Math.max(minimumWidth, layoutParams2.rightMargin) + this.icon.getMeasuredWidth() + Math.max(minimumWidth, layoutParams2.leftMargin), measuredWidth);
    }

    @Override // androidx.appcompat.view.menu.MenuView.ItemView
    public final void initialize(MenuItemImpl menuItemImpl) {
        this.itemData = menuItemImpl;
        menuItemImpl.getClass();
        refreshDrawableState();
        setChecked(menuItemImpl.isChecked());
        setEnabled(menuItemImpl.isEnabled());
        Drawable icon = menuItemImpl.getIcon();
        if (icon != this.originalIconDrawable) {
            this.originalIconDrawable = icon;
            if (icon != null) {
                Drawable.ConstantState constantState = icon.getConstantState();
                if (constantState != null) {
                    icon = constantState.newDrawable();
                }
                icon = icon.mutate();
                this.wrappedIconDrawable = icon;
                ColorStateList colorStateList = this.iconTint;
                if (colorStateList != null) {
                    icon.setTintList(colorStateList);
                }
            }
            this.icon.setImageDrawable(icon);
        }
        CharSequence charSequence = menuItemImpl.mTitle;
        this.smallLabel.setText(charSequence);
        this.largeLabel.setText(charSequence);
        if (TextUtils.isEmpty(charSequence)) {
            this.smallLabel.setVisibility(8);
            this.largeLabel.setVisibility(8);
        }
        MenuItemImpl menuItemImpl2 = this.itemData;
        if (menuItemImpl2 == null || TextUtils.isEmpty(menuItemImpl2.mContentDescription)) {
            setContentDescription(charSequence);
        }
        MenuItemImpl menuItemImpl3 = this.itemData;
        setTooltipText(menuItemImpl3 != null ? menuItemImpl3.mTooltipText : null);
        setId(menuItemImpl.mId);
        if (!TextUtils.isEmpty(menuItemImpl.mContentDescription)) {
            setContentDescription(menuItemImpl.mContentDescription);
        }
        setTooltipText(menuItemImpl.mTooltipText);
        String str = menuItemImpl.mBadgeText;
        this.mBadgeType = (str == null || str.equals("") || str.isEmpty()) ? 1 : menuItemImpl.mId == com.android.systemui.R.id.bottom_overflow ? 0 : 2;
        this.initialized = true;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        setLargeTextSize(this.mLargeLabelAppearance, this.largeLabel);
        setLargeTextSize(this.mSmallLabelAppearance, this.smallLabel);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        MenuItemImpl menuItemImpl = this.itemData;
        if (menuItemImpl != null && menuItemImpl.isCheckable() && this.itemData.isChecked()) {
            FrameLayout.mergeDrawableStates(onCreateDrawableState, CHECKED_STATE_SET);
        }
        return onCreateDrawableState;
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0117  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        BadgeDrawable badgeDrawable;
        Context context;
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (this.itemData != null && (badgeDrawable = this.badgeDrawable) != null && badgeDrawable.isVisible()) {
            MenuItemImpl menuItemImpl = this.itemData;
            CharSequence charSequence = menuItemImpl.mTitle;
            if (!TextUtils.isEmpty(menuItemImpl.mContentDescription)) {
                charSequence = this.itemData.mContentDescription;
            }
            StringBuilder sb = new StringBuilder();
            sb.append((Object) charSequence);
            sb.append(", ");
            BadgeDrawable badgeDrawable2 = this.badgeDrawable;
            Object obj = null;
            if (badgeDrawable2.isVisible()) {
                if (!badgeDrawable2.hasNumber()) {
                    obj = badgeDrawable2.state.currentState.contentDescriptionNumberless;
                } else if (badgeDrawable2.state.currentState.contentDescriptionQuantityStrings != 0 && (context = (Context) badgeDrawable2.contextRef.get()) != null) {
                    int number = badgeDrawable2.getNumber();
                    int i = badgeDrawable2.maxBadgeNumber;
                    obj = number <= i ? context.getResources().getQuantityString(badgeDrawable2.state.currentState.contentDescriptionQuantityStrings, badgeDrawable2.getNumber(), Integer.valueOf(badgeDrawable2.getNumber())) : context.getString(badgeDrawable2.state.currentState.contentDescriptionExceedsMaxBadgeNumberRes, Integer.valueOf(i));
                }
            }
            sb.append(obj);
            accessibilityNodeInfo.setContentDescription(sb.toString());
        }
        TextView textView = (TextView) findViewById(com.android.systemui.R.id.notifications_badge);
        if (this.itemData != null && textView != null && textView.getVisibility() == 0 && textView.getWidth() > 0) {
            CharSequence charSequence2 = this.itemData.mTitle;
            String charSequence3 = charSequence2.toString();
            if (TextUtils.isEmpty(this.itemData.mContentDescription)) {
                int i2 = this.mBadgeType;
                if (i2 != 0) {
                    boolean z = true;
                    if (i2 == 1) {
                        charSequence3 = ((Object) charSequence2) + " , " + getResources().getString(com.android.systemui.R.string.mtrl_badge_numberless_content_description);
                    } else if (i2 == 2) {
                        String charSequence4 = textView.getText().toString();
                        if (charSequence4 != null) {
                            try {
                                Integer.parseInt(charSequence4);
                            } catch (NumberFormatException unused) {
                            }
                            if (z) {
                                charSequence3 = this.mIsBadgeNumberless ? ((Object) charSequence2) + " , " + getResources().getString(com.android.systemui.R.string.mtrl_exceed_max_badge_number_content_description, 999) : ((Object) charSequence2) + " , " + getResources().getString(com.android.systemui.R.string.sesl_material_badge_description);
                            } else {
                                int parseInt = Integer.parseInt(charSequence4);
                                charSequence3 = ((Object) charSequence2) + " , " + getResources().getQuantityString(com.android.systemui.R.plurals.mtrl_badge_content_description, parseInt, Integer.valueOf(parseInt));
                            }
                        }
                        z = false;
                        if (z) {
                        }
                    }
                } else {
                    charSequence3 = ((Object) charSequence2) + " , " + getResources().getString(com.android.systemui.R.string.sesl_material_badge_description);
                }
            } else {
                charSequence3 = this.itemData.mContentDescription.toString();
            }
            accessibilityNodeInfo.setContentDescription(charSequence3);
        }
        AccessibilityNodeInfoCompat wrap = AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo);
        ViewGroup viewGroup = (ViewGroup) getParent();
        int indexOfChild = viewGroup.indexOfChild(this);
        int i3 = 0;
        for (int i4 = 0; i4 < indexOfChild; i4++) {
            View childAt = viewGroup.getChildAt(i4);
            if ((childAt instanceof NavigationBarItemView) && childAt.getVisibility() == 0) {
                i3++;
            }
        }
        wrap.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(false, 0, 1, i3, 1, isSelected()));
        if (isSelected()) {
            wrap.setClickable(false);
            wrap.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK);
        }
        accessibilityNodeInfo.setClassName(Button.class.getName());
    }

    @Override // android.view.View
    public final void onSizeChanged(final int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        post(new Runnable() { // from class: com.google.android.material.navigation.NavigationBarItemView.2
            @Override // java.lang.Runnable
            public final void run() {
                NavigationBarItemView navigationBarItemView = NavigationBarItemView.this;
                int i5 = i;
                int[] iArr = NavigationBarItemView.CHECKED_STATE_SET;
                navigationBarItemView.updateActiveIndicatorLayoutParams(i5);
            }
        });
    }

    public final void refreshItemBackground() {
        Drawable drawable = this.itemBackground;
        RippleDrawable rippleDrawable = null;
        boolean z = true;
        if (this.itemRippleColor != null) {
            View view = this.activeIndicatorView;
            Drawable background = view == null ? null : view.getBackground();
            if (this.activeIndicatorEnabled) {
                View view2 = this.activeIndicatorView;
                if ((view2 == null ? null : view2.getBackground()) != null && this.iconContainer != null && background != null) {
                    rippleDrawable = new RippleDrawable(RippleUtils.sanitizeRippleDrawableColor(this.itemRippleColor), null, background);
                    z = false;
                }
            }
            if (drawable == null) {
                ColorStateList colorStateList = this.itemRippleColor;
                int[] iArr = RippleUtils.SELECTED_STATE_SET;
                int colorForState = RippleUtils.getColorForState(colorStateList, RippleUtils.SELECTED_PRESSED_STATE_SET);
                int[] iArr2 = RippleUtils.FOCUSED_STATE_SET;
                drawable = new RippleDrawable(new ColorStateList(new int[][]{iArr, iArr2, StateSet.NOTHING}, new int[]{colorForState, RippleUtils.getColorForState(colorStateList, iArr2), RippleUtils.getColorForState(colorStateList, RippleUtils.PRESSED_STATE_SET)}), null, null);
            }
        }
        FrameLayout frameLayout = this.iconContainer;
        if (frameLayout != null) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.setBackground(frameLayout, rippleDrawable);
        }
        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.setBackground(this, drawable);
        setDefaultFocusHighlightEnabled(z);
    }

    public final void seslSetLabelTextAppearance(int i) {
        this.mLargeLabelAppearance = i;
        this.mSmallLabelAppearance = i;
        this.smallLabel.setTextAppearance(i);
        calculateTextScaleFactors(this.smallLabel.getTextSize(), this.largeLabel.getTextSize());
        setLargeTextSize(this.mLargeLabelAppearance, this.largeLabel);
        setLargeTextSize(this.mSmallLabelAppearance, this.smallLabel);
    }

    public final void setActiveIndicatorProgress(float f, float f2) {
        View view = this.activeIndicatorView;
        if (view != null) {
            ActiveIndicatorTransform activeIndicatorTransform = this.activeIndicatorTransform;
            activeIndicatorTransform.getClass();
            TimeInterpolator timeInterpolator = AnimationUtils.LINEAR_INTERPOLATOR;
            view.setScaleX((0.6f * f) + 0.4f);
            view.setScaleY(activeIndicatorTransform.calculateScaleY(f, f2));
            view.setAlpha(AnimationUtils.lerp(0.0f, 1.0f, f2 == 0.0f ? 0.8f : 0.0f, f2 == 0.0f ? 1.0f : 0.2f, f));
        }
        this.activeIndicatorProgress = f;
    }

    public final void setBadge(BadgeDrawable badgeDrawable) {
        BadgeDrawable badgeDrawable2 = this.badgeDrawable;
        if (badgeDrawable2 == badgeDrawable) {
            return;
        }
        if ((badgeDrawable2 != null) && this.icon != null) {
            Log.w("NavigationBar", "Multiple badges shouldn't be attached to one item.");
            ImageView imageView = this.icon;
            if (this.badgeDrawable != null) {
                if (imageView != null) {
                    setClipChildren(true);
                    setClipToPadding(true);
                    BadgeDrawable badgeDrawable3 = this.badgeDrawable;
                    if (badgeDrawable3 != null) {
                        if (badgeDrawable3.getCustomBadgeParent() != null) {
                            badgeDrawable3.getCustomBadgeParent().setForeground(null);
                        } else {
                            imageView.getOverlay().remove(badgeDrawable3);
                        }
                    }
                }
                this.badgeDrawable = null;
            }
        }
        this.badgeDrawable = badgeDrawable;
        ImageView imageView2 = this.icon;
        if (imageView2 != null) {
            if (badgeDrawable != null) {
                setClipChildren(false);
                setClipToPadding(false);
                BadgeDrawable badgeDrawable4 = this.badgeDrawable;
                Rect rect = new Rect();
                imageView2.getDrawingRect(rect);
                badgeDrawable4.setBounds(rect);
                badgeDrawable4.updateBadgeCoordinates(imageView2, null);
                if (badgeDrawable4.getCustomBadgeParent() != null) {
                    badgeDrawable4.getCustomBadgeParent().setForeground(badgeDrawable4);
                } else {
                    imageView2.getOverlay().add(badgeDrawable4);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x015f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setChecked(boolean z) {
        int i;
        this.largeLabel.setPivotX(r0.getWidth() / 2);
        this.largeLabel.setPivotY(r0.getBaseline());
        this.smallLabel.setPivotX(r0.getWidth() / 2);
        this.smallLabel.setPivotY(r0.getBaseline());
        if (this.mViewType != 3) {
            this.defaultMargin = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_navigation_bar_icon_inset);
        }
        this.itemPaddingTop = this.defaultMargin;
        final float f = z ? 1.0f : 0.0f;
        if (this.activeIndicatorEnabled && this.initialized) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (ViewCompat.Api19Impl.isAttachedToWindow(this)) {
                ValueAnimator valueAnimator = this.activeIndicatorAnimator;
                if (valueAnimator != null) {
                    valueAnimator.cancel();
                    this.activeIndicatorAnimator = null;
                }
                ValueAnimator ofFloat = ValueAnimator.ofFloat(this.activeIndicatorProgress, f);
                this.activeIndicatorAnimator = ofFloat;
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.navigation.NavigationBarItemView.3
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                        NavigationBarItemView navigationBarItemView = NavigationBarItemView.this;
                        float f2 = f;
                        int[] iArr = NavigationBarItemView.CHECKED_STATE_SET;
                        navigationBarItemView.setActiveIndicatorProgress(floatValue, f2);
                    }
                });
                this.activeIndicatorAnimator.setInterpolator(MotionUtils.resolveThemeInterpolator(getContext(), com.android.systemui.R.attr.motionEasingEmphasizedInterpolator, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
                ValueAnimator valueAnimator2 = this.activeIndicatorAnimator;
                Context context = getContext();
                int integer = getResources().getInteger(com.android.systemui.R.integer.material_motion_duration_long_1);
                TypedValue resolve = MaterialAttributes.resolve(com.android.systemui.R.attr.motionDurationLong2, context);
                if (resolve != null && resolve.type == 16) {
                    integer = resolve.data;
                }
                valueAnimator2.setDuration(integer);
                this.activeIndicatorAnimator.start();
                i = this.labelVisibilityMode;
                if (i == -1) {
                    if (i == 0) {
                        if (z) {
                            setViewTopMarginAndGravity(getIconOrContainer(), this.itemPaddingTop, 49);
                            updateViewPaddingBottom(this.labelGroup, this.itemPaddingBottom);
                            this.largeLabel.setVisibility(0);
                            setViewScaleValues(1.0f, 1.0f, 0, this.largeLabel);
                        } else {
                            setViewTopMarginAndGravity(getIconOrContainer(), this.itemPaddingTop, 17);
                            updateViewPaddingBottom(this.labelGroup, 0);
                            this.largeLabel.setVisibility(4);
                            setViewScaleValues(0.5f, 0.5f, 4, this.largeLabel);
                        }
                        this.smallLabel.setVisibility(4);
                    } else if (i == 1) {
                        updateViewPaddingBottom(this.labelGroup, this.itemPaddingBottom);
                        if (z) {
                            setViewTopMarginAndGravity(getIconOrContainer(), (int) (this.itemPaddingTop + this.shiftAmount), 49);
                            setViewScaleValues(1.0f, 1.0f, 0, this.largeLabel);
                            TextView textView = this.smallLabel;
                            float f2 = this.scaleUpFactor;
                            setViewScaleValues(f2, f2, 4, textView);
                        } else {
                            setViewTopMarginAndGravity(getIconOrContainer(), this.itemPaddingTop, 49);
                            TextView textView2 = this.largeLabel;
                            float f3 = this.scaleDownFactor;
                            setViewScaleValues(f3, f3, 4, textView2);
                            setViewScaleValues(1.0f, 1.0f, 0, this.smallLabel);
                        }
                    } else if (i == 2) {
                        setViewTopMarginAndGravity(getIconOrContainer(), this.itemPaddingTop, 17);
                        this.largeLabel.setVisibility(8);
                        this.smallLabel.setVisibility(8);
                    }
                } else if (this.isShifting) {
                    if (z) {
                        setViewTopMarginAndGravity(getIconOrContainer(), this.itemPaddingTop, 49);
                        updateViewPaddingBottom(this.labelGroup, this.itemPaddingBottom);
                        this.largeLabel.setVisibility(0);
                        setViewScaleValues(1.0f, 1.0f, 0, this.largeLabel);
                    } else {
                        setViewTopMarginAndGravity(getIconOrContainer(), this.itemPaddingTop, 17);
                        updateViewPaddingBottom(this.labelGroup, 0);
                        this.largeLabel.setVisibility(4);
                        setViewScaleValues(0.5f, 0.5f, 4, this.largeLabel);
                    }
                    this.smallLabel.setVisibility(4);
                } else {
                    updateViewPaddingBottom(this.labelGroup, this.itemPaddingBottom);
                    if (z) {
                        setViewTopMarginAndGravity(getIconOrContainer(), (int) (this.itemPaddingTop + this.shiftAmount), 49);
                        setViewScaleValues(1.0f, 1.0f, 4, this.largeLabel);
                        TextView textView3 = this.smallLabel;
                        float f4 = this.scaleUpFactor;
                        setViewScaleValues(f4, f4, 0, textView3);
                    } else {
                        setViewTopMarginAndGravity(getIconOrContainer(), this.itemPaddingTop, 49);
                        TextView textView4 = this.largeLabel;
                        float f5 = this.scaleDownFactor;
                        setViewScaleValues(f5, f5, 4, textView4);
                        setViewScaleValues(1.0f, 1.0f, 0, this.smallLabel);
                    }
                }
                refreshDrawableState();
            }
        }
        setActiveIndicatorProgress(f, f);
        i = this.labelVisibilityMode;
        if (i == -1) {
        }
        refreshDrawableState();
    }

    @Override // android.view.View
    public final void setEnabled(boolean z) {
        super.setEnabled(z);
        this.smallLabel.setEnabled(z);
        this.largeLabel.setEnabled(z);
        this.icon.setEnabled(z);
        if (!z) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api24Impl.setPointerIcon(this, null);
        } else {
            PointerIconCompat systemIcon = PointerIconCompat.getSystemIcon(getContext());
            WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api24Impl.setPointerIcon(this, systemIcon.mPointerIcon);
        }
    }

    public final void setIconSize(int i) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.icon.getLayoutParams();
        layoutParams.width = i;
        layoutParams.height = i;
        this.icon.setLayoutParams(layoutParams);
    }

    public final void setIconTintList(ColorStateList colorStateList) {
        Drawable drawable;
        this.iconTint = colorStateList;
        MenuItemImpl menuItemImpl = this.itemData;
        if ((menuItemImpl == null && this.wrappedIconDrawable == null) || menuItemImpl == null || (drawable = this.wrappedIconDrawable) == null) {
            return;
        }
        drawable.setTintList(colorStateList);
        this.wrappedIconDrawable.invalidateSelf();
    }

    public final void setItemBackground(int i) {
        Drawable drawable;
        if (i == 0) {
            drawable = null;
        } else {
            Context context = getContext();
            Object obj = ContextCompat.sLock;
            drawable = context.getDrawable(i);
        }
        if (drawable != null && drawable.getConstantState() != null) {
            drawable = drawable.getConstantState().newDrawable().mutate();
        }
        this.itemBackground = drawable;
        refreshItemBackground();
    }

    public final void setLabelVisibilityMode(int i) {
        if (this.labelVisibilityMode != i) {
            this.labelVisibilityMode = i;
            if (this.activeIndicatorResizeable && i == 2) {
                this.activeIndicatorTransform = ACTIVE_INDICATOR_UNLABELED_TRANSFORM;
            } else {
                this.activeIndicatorTransform = ACTIVE_INDICATOR_LABELED_TRANSFORM;
            }
            updateActiveIndicatorLayoutParams(getWidth());
            MenuItemImpl menuItemImpl = this.itemData;
            if (menuItemImpl != null) {
                setChecked(menuItemImpl.isChecked());
            }
        }
    }

    public final void setLargeTextSize(int i, TextView textView) {
        if (textView != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(i, R$styleable.TextAppearance);
            TypedValue peekValue = obtainStyledAttributes.peekValue(0);
            obtainStyledAttributes.recycle();
            textView.setTextSize(1, Math.min(getResources().getConfiguration().fontScale, 1.3f) * TypedValue.complexToFloat(peekValue.data));
        }
    }

    public final void setTextAppearanceActive(int i) {
        setTextAppearanceWithoutFontScaling(i, this.largeLabel);
        calculateTextScaleFactors(this.smallLabel.getTextSize(), this.largeLabel.getTextSize());
    }

    public final void setTextAppearanceInactive(int i) {
        setTextAppearanceWithoutFontScaling(i, this.smallLabel);
        calculateTextScaleFactors(this.smallLabel.getTextSize(), this.largeLabel.getTextSize());
    }

    public final void setTextColor(ColorStateList colorStateList) {
        if (colorStateList != null) {
            this.smallLabel.setTextColor(colorStateList);
            this.largeLabel.setTextColor(colorStateList);
        }
    }

    public final void updateActiveIndicatorLayoutParams(int i) {
        if (this.activeIndicatorView == null) {
            return;
        }
        int min = Math.min(this.activeIndicatorDesiredWidth, i - (this.activeIndicatorMarginHorizontal * 2));
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.activeIndicatorView.getLayoutParams();
        layoutParams.height = this.activeIndicatorResizeable && this.labelVisibilityMode == 2 ? min : this.activeIndicatorDesiredHeight;
        layoutParams.width = min;
        this.activeIndicatorView.setLayoutParams(layoutParams);
    }

    public NavigationBarItemView(Context context, int i) {
        this(context, null, i);
    }

    public NavigationBarItemView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, 0, i);
    }

    public NavigationBarItemView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i);
        this.TAG = "NavigationBarItemView";
        this.initialized = false;
        this.activeIndicatorTransform = ACTIVE_INDICATOR_LABELED_TRANSFORM;
        this.activeIndicatorProgress = 0.0f;
        this.activeIndicatorEnabled = false;
        this.activeIndicatorDesiredWidth = 0;
        this.activeIndicatorDesiredHeight = 0;
        this.activeIndicatorResizeable = false;
        this.activeIndicatorMarginHorizontal = 0;
        this.mBadgeType = 1;
        this.mViewType = i2;
        LayoutInflater.from(context).inflate(getItemLayoutResId(), (ViewGroup) this, true);
        this.iconContainer = (FrameLayout) findViewById(com.android.systemui.R.id.navigation_bar_item_icon_container);
        this.activeIndicatorView = findViewById(com.android.systemui.R.id.navigation_bar_item_active_indicator_view);
        ImageView imageView = (ImageView) findViewById(com.android.systemui.R.id.navigation_bar_item_icon_view);
        this.icon = imageView;
        ViewGroup viewGroup = (ViewGroup) findViewById(com.android.systemui.R.id.navigation_bar_item_labels_group);
        this.labelGroup = viewGroup;
        TextView textView = (TextView) findViewById(com.android.systemui.R.id.navigation_bar_item_small_label_view);
        this.smallLabel = textView;
        TextView textView2 = (TextView) findViewById(com.android.systemui.R.id.navigation_bar_item_large_label_view);
        this.largeLabel = textView2;
        setBackgroundResource(com.android.systemui.R.drawable.mtrl_navigation_bar_item_background);
        this.itemPaddingTop = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.mtrl_navigation_bar_item_default_margin);
        this.itemPaddingBottom = viewGroup.getPaddingBottom();
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.setImportantForAccessibility(textView, 2);
        ViewCompat.Api16Impl.setImportantForAccessibility(textView2, 2);
        setFocusable(true);
        calculateTextScaleFactors(textView.getTextSize(), textView2.getTextSize());
        if (imageView != null) {
            imageView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.google.android.material.navigation.NavigationBarItemView.1
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
                    if (NavigationBarItemView.this.icon.getVisibility() == 0) {
                        NavigationBarItemView navigationBarItemView = NavigationBarItemView.this;
                        ImageView imageView2 = navigationBarItemView.icon;
                        BadgeDrawable badgeDrawable = navigationBarItemView.badgeDrawable;
                        if (badgeDrawable != null) {
                            Rect rect = new Rect();
                            imageView2.getDrawingRect(rect);
                            badgeDrawable.setBounds(rect);
                            badgeDrawable.updateBadgeCoordinates(imageView2, null);
                        }
                    }
                }
            });
        }
        ViewCompat.setAccessibilityDelegate(this, null);
    }
}
