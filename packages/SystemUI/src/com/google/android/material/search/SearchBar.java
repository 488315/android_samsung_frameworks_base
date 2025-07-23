package com.google.android.material.search;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityManagerCompat$TouchExplorationStateChangeListener;
import androidx.core.view.accessibility.AccessibilityManagerCompat$TouchExplorationStateChangeListenerWrapper;
import androidx.customview.view.AbsSavedState;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.ViewUtilsLollipop;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ToolbarUtils;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SearchBar extends Toolbar {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AccessibilityManager accessibilityManager;
    public final MaterialShapeDrawable backgroundShape;
    public View centerView;
    public final boolean defaultMarginsEnabled;
    public final Drawable defaultNavigationIcon;
    public final boolean defaultScrollFlagsEnabled;
    public final boolean forceDefaultNavigationOnClickListener;
    public final boolean layoutInflated;
    public int menuResId;
    public final Integer navigationIconTint;
    public Drawable originalNavigationIconBackground;
    public final SearchBarAnimationHelper searchBarAnimationHelper;
    public final TextView textView;
    public final boolean tintNavigationIcon;
    public final SearchBar$$ExternalSyntheticLambda0 touchExplorationStateChangeListener;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator() { // from class: com.google.android.material.search.SearchBar.SavedState.1
            @Override // android.os.Parcelable.ClassLoaderCreator
            public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }

            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }
        };
        public String text;

        public SavedState(Parcel parcel) {
            this(parcel, null);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeString(this.text);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.text = parcel.readString();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public SearchBar(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (this.layoutInflated && this.centerView == null && !(view instanceof ActionMenuView)) {
            this.centerView = view;
            view.setAlpha(0.0f);
        }
        super.addView(view, i, layoutParams);
    }

    @Override // androidx.appcompat.widget.Toolbar
    public final void inflateMenu(int i) {
        MenuBuilder menu = getMenu();
        if (menu != null) {
            menu.stopDispatchingItemsChanged();
        }
        super.inflateMenu(i);
        this.menuResId = i;
        if (menu != null) {
            menu.startDispatchingItemsChanged();
        }
    }

    @Override // androidx.appcompat.widget.Toolbar, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this, this.backgroundShape);
        if (this.defaultMarginsEnabled && (getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
            Resources resources = getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.m3_searchbar_margin_horizontal);
            int dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.m3_searchbar_margin_vertical);
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
            int i = marginLayoutParams.leftMargin;
            if (i == 0) {
                i = dimensionPixelSize;
            }
            marginLayoutParams.leftMargin = i;
            int i2 = marginLayoutParams.topMargin;
            if (i2 == 0) {
                i2 = dimensionPixelSize2;
            }
            marginLayoutParams.topMargin = i2;
            int i3 = marginLayoutParams.rightMargin;
            if (i3 != 0) {
                dimensionPixelSize = i3;
            }
            marginLayoutParams.rightMargin = dimensionPixelSize;
            int i4 = marginLayoutParams.bottomMargin;
            if (i4 != 0) {
                dimensionPixelSize2 = i4;
            }
            marginLayoutParams.bottomMargin = dimensionPixelSize2;
        }
        if (getLayoutParams() instanceof AppBarLayout.LayoutParams) {
            AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) getLayoutParams();
            if (this.defaultScrollFlagsEnabled) {
                if (layoutParams.scrollFlags == 0) {
                    layoutParams.scrollFlags = 53;
                }
            } else if (layoutParams.scrollFlags == 53) {
                layoutParams.scrollFlags = 0;
            }
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(EditText.class.getCanonicalName());
        accessibilityNodeInfo.setEditable(isEnabled());
        CharSequence text = this.textView.getText();
        boolean isEmpty = TextUtils.isEmpty(text);
        accessibilityNodeInfo.setHintText(this.textView.getHint());
        accessibilityNodeInfo.setShowingHintText(isEmpty);
        if (isEmpty) {
            text = this.textView.getHint();
        }
        accessibilityNodeInfo.setText(text);
    }

    @Override // androidx.appcompat.widget.Toolbar, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        View view = this.centerView;
        if (view != null) {
            int measuredWidth = view.getMeasuredWidth();
            int measuredWidth2 = (getMeasuredWidth() / 2) - (measuredWidth / 2);
            int i5 = measuredWidth + measuredWidth2;
            int measuredHeight = this.centerView.getMeasuredHeight();
            int measuredHeight2 = (getMeasuredHeight() / 2) - (measuredHeight / 2);
            int i6 = measuredHeight + measuredHeight2;
            View view2 = this.centerView;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (getLayoutDirection() == 1) {
                view2.layout(getMeasuredWidth() - i5, measuredHeight2, getMeasuredWidth() - measuredWidth2, i6);
            } else {
                view2.layout(measuredWidth2, measuredHeight2, i5, i6);
            }
        }
        setHandwritingBoundsInsets();
    }

    @Override // androidx.appcompat.widget.Toolbar, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        View view = this.centerView;
        if (view != null) {
            view.measure(i, i2);
        }
    }

    @Override // androidx.appcompat.widget.Toolbar, android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.mSuperState);
        this.textView.setText(savedState.text);
    }

    @Override // androidx.appcompat.widget.Toolbar, android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        CharSequence text = this.textView.getText();
        savedState.text = text == null ? null : text.toString();
        return savedState;
    }

    @Override // android.view.View
    public final void setElevation(float f) {
        super.setElevation(f);
        MaterialShapeDrawable materialShapeDrawable = this.backgroundShape;
        if (materialShapeDrawable != null) {
            materialShapeDrawable.setElevation(f);
        }
    }

    public final void setHandwritingBoundsInsets() {
        boolean z = getLayoutDirection() == 1;
        ImageButton navigationIconButton = ToolbarUtils.getNavigationIconButton(this);
        int width = (navigationIconButton == null || !navigationIconButton.isClickable()) ? 0 : z ? getWidth() - navigationIconButton.getLeft() : navigationIconButton.getRight();
        ActionMenuView actionMenuView = ToolbarUtils.getActionMenuView(this);
        int right = actionMenuView != null ? z ? actionMenuView.getRight() : getWidth() - actionMenuView.getLeft() : 0;
        float f = -(z ? right : width);
        if (!z) {
            width = right;
        }
        setHandwritingBoundsOffsets(f, 0.0f, -width, 0.0f);
    }

    @Override // androidx.appcompat.widget.Toolbar
    public final void setNavigationIcon(Drawable drawable) {
        int color;
        if (this.tintNavigationIcon && drawable != null) {
            Integer num = this.navigationIconTint;
            if (num != null) {
                color = num.intValue();
            } else {
                color = MaterialColors.getColor(this, drawable == this.defaultNavigationIcon ? R.attr.colorOnSurfaceVariant : R.attr.colorOnSurface);
            }
            drawable = drawable.mutate();
            drawable.setTint(color);
        }
        super.setNavigationIcon(drawable);
    }

    public final void setNavigationIconDecorative(boolean z) {
        ImageButton navigationIconButton = ToolbarUtils.getNavigationIconButton(this);
        if (navigationIconButton == null) {
            return;
        }
        navigationIconButton.setClickable(!z);
        navigationIconButton.setFocusable(!z);
        Drawable background = navigationIconButton.getBackground();
        if (background != null) {
            this.originalNavigationIconBackground = background;
        }
        navigationIconButton.setBackgroundDrawable(z ? null : this.originalNavigationIconBackground);
        setHandwritingBoundsInsets();
    }

    @Override // androidx.appcompat.widget.Toolbar
    public final void setNavigationOnClickListener(View.OnClickListener onClickListener) {
        if (this.forceDefaultNavigationOnClickListener) {
            return;
        }
        super.setNavigationOnClickListener(onClickListener);
        setNavigationIconDecorative(false);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ScrollingViewBehavior extends AppBarLayout.ScrollingViewBehavior {
        public boolean initialized;

        public ScrollingViewBehavior() {
            this.initialized = false;
        }

        @Override // com.google.android.material.appbar.AppBarLayout.ScrollingViewBehavior, androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
        public final boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view, View view2) {
            super.onDependentViewChanged(coordinatorLayout, view, view2);
            if (!this.initialized && (view2 instanceof AppBarLayout)) {
                this.initialized = true;
                AppBarLayout appBarLayout = (AppBarLayout) view2;
                appBarLayout.setBackgroundColor(0);
                ViewUtilsLollipop.setDefaultAppBarLayoutStateListAnimator(0.0f, appBarLayout);
            }
            return false;
        }

        public ScrollingViewBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.initialized = false;
        }
    }

    public SearchBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.materialSearchBarStyle);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.google.android.material.search.SearchBar$$ExternalSyntheticLambda0] */
    public SearchBar(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, R.style.Widget_Material3_SearchBar), attributeSet, i);
        this.menuResId = -1;
        this.touchExplorationStateChangeListener = new AccessibilityManagerCompat$TouchExplorationStateChangeListener() { // from class: com.google.android.material.search.SearchBar$$ExternalSyntheticLambda0
            @Override // androidx.core.view.accessibility.AccessibilityManagerCompat$TouchExplorationStateChangeListener
            public final void onTouchExplorationStateChanged(boolean z) {
                int i2 = SearchBar.$r8$clinit;
                SearchBar.this.setFocusableInTouchMode(z);
            }
        };
        Context context2 = getContext();
        if (attributeSet != null) {
            if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", UniversalCredentialUtil.AGENT_TITLE) == null) {
                if (attributeSet.getAttributeValue("http://schemas.android.com/apk/res-auto", "subtitle") != null) {
                    throw new UnsupportedOperationException("SearchBar does not support subtitle. Use hint or text instead.");
                }
            } else {
                throw new UnsupportedOperationException("SearchBar does not support title. Use hint or text instead.");
            }
        }
        Drawable drawable = AppCompatResources.getDrawable(R.drawable.ic_search_black_24, context2);
        this.defaultNavigationIcon = drawable;
        this.searchBarAnimationHelper = new SearchBarAnimationHelper();
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, R$styleable.SearchBar, i, R.style.Widget_Material3_SearchBar, new int[0]);
        ShapeAppearanceModel build = ShapeAppearanceModel.builder(context2, attributeSet, i, R.style.Widget_Material3_SearchBar).build();
        int color = obtainStyledAttributes.getColor(3, 0);
        float dimension = obtainStyledAttributes.getDimension(6, 0.0f);
        this.defaultMarginsEnabled = obtainStyledAttributes.getBoolean(4, true);
        this.defaultScrollFlagsEnabled = obtainStyledAttributes.getBoolean(5, true);
        boolean z = obtainStyledAttributes.getBoolean(8, false);
        this.forceDefaultNavigationOnClickListener = obtainStyledAttributes.getBoolean(7, false);
        this.tintNavigationIcon = obtainStyledAttributes.getBoolean(12, true);
        if (obtainStyledAttributes.hasValue(9)) {
            this.navigationIconTint = Integer.valueOf(obtainStyledAttributes.getColor(9, -1));
        }
        int resourceId = obtainStyledAttributes.getResourceId(0, -1);
        String string = obtainStyledAttributes.getString(1);
        String string2 = obtainStyledAttributes.getString(2);
        float dimension2 = obtainStyledAttributes.getDimension(11, -1.0f);
        int color2 = obtainStyledAttributes.getColor(10, 0);
        obtainStyledAttributes.recycle();
        if (!z) {
            setNavigationIcon(getNavigationIcon() != null ? getNavigationIcon() : drawable);
            setNavigationIconDecorative(true);
        }
        setClickable(true);
        setFocusable(true);
        LayoutInflater.from(context2).inflate(R.layout.mtrl_search_bar, this);
        this.layoutInflated = true;
        TextView textView = (TextView) findViewById(R.id.open_search_bar_text_view);
        this.textView = textView;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api21Impl.setElevation(this, dimension);
        if (resourceId != -1) {
            textView.setTextAppearance(resourceId);
        }
        textView.setText(string);
        textView.setHint(string2);
        if (getNavigationIcon() == null) {
            ((ViewGroup.MarginLayoutParams) textView.getLayoutParams()).setMarginStart(getResources().getDimensionPixelSize(R.dimen.m3_searchbar_text_margin_start_no_navigation_icon));
        }
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(build);
        this.backgroundShape = materialShapeDrawable;
        materialShapeDrawable.initializeElevationOverlay(getContext());
        this.backgroundShape.setElevation(dimension);
        if (dimension2 >= 0.0f) {
            MaterialShapeDrawable materialShapeDrawable2 = this.backgroundShape;
            materialShapeDrawable2.drawableState.strokeWidth = dimension2;
            materialShapeDrawable2.invalidateSelf();
            materialShapeDrawable2.setStrokeColor(ColorStateList.valueOf(color2));
        }
        int color3 = MaterialColors.getColor(this, R.attr.colorControlHighlight);
        this.backgroundShape.setFillColor(ColorStateList.valueOf(color));
        ColorStateList valueOf = ColorStateList.valueOf(color3);
        MaterialShapeDrawable materialShapeDrawable3 = this.backgroundShape;
        setBackground(new RippleDrawable(valueOf, materialShapeDrawable3, materialShapeDrawable3));
        AccessibilityManager accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        this.accessibilityManager = accessibilityManager;
        if (accessibilityManager != null) {
            if (accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled()) {
                setFocusableInTouchMode(true);
            }
            addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.google.android.material.search.SearchBar.1
                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewAttachedToWindow(View view) {
                    SearchBar searchBar = SearchBar.this;
                    searchBar.accessibilityManager.addTouchExplorationStateChangeListener(new AccessibilityManagerCompat$TouchExplorationStateChangeListenerWrapper(searchBar.touchExplorationStateChangeListener));
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewDetachedFromWindow(View view) {
                    SearchBar searchBar = SearchBar.this;
                    searchBar.accessibilityManager.removeTouchExplorationStateChangeListener(new AccessibilityManagerCompat$TouchExplorationStateChangeListenerWrapper(searchBar.touchExplorationStateChangeListener));
                }
            });
        }
    }

    @Override // androidx.appcompat.widget.Toolbar
    public final void setSubtitle(CharSequence charSequence) {
    }

    @Override // androidx.appcompat.widget.Toolbar
    public final void setTitle(CharSequence charSequence) {
    }
}
