package com.google.android.material.navigation;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.internal.NavigationMenuPresenter;
import com.google.android.material.internal.NavigationMenuPresenter.NavigationMenuAdapter;
import com.google.android.material.internal.NavigationMenuPresenter.NavigationMenuViewAccessibilityDelegate;
import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.internal.ScrimInsetsFrameLayout;
import com.google.android.material.internal.SeslContextUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.RelativeCornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class NavigationView extends ScrimInsetsFrameLayout {
    public static final int[] CHECKED_STATE_SET = {R.attr.state_checked};
    public static final int[] DISABLED_STATE_SET = {-16842910};
    public boolean bottomInsetScrimEnabled;
    public final int drawerLayoutCornerSize;
    public final int layoutGravity;
    public final int maxWidth;
    public final NavigationMenu menu;
    public SupportMenuInflater menuInflater;
    public ViewTreeObserverOnGlobalLayoutListenerC43242 onGlobalLayoutListener;
    public final NavigationMenuPresenter presenter;
    public final RectF shapeClipBounds;
    public Path shapeClipPath;
    public final int[] tmpLocation;
    public boolean topInsetScrimEnabled;

    public NavigationView(Context context) {
        this(context, null);
    }

    public final ColorStateList createDefaultColorStateList(int i) {
        TypedValue typedValue = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(i, typedValue, true)) {
            return null;
        }
        ColorStateList colorStateList = ContextCompat.getColorStateList(typedValue.resourceId, getContext());
        if (!getContext().getTheme().resolveAttribute(com.android.systemui.R.attr.colorPrimary, typedValue, true)) {
            return null;
        }
        int i2 = typedValue.data;
        int defaultColor = colorStateList.getDefaultColor();
        int[] iArr = DISABLED_STATE_SET;
        return new ColorStateList(new int[][]{iArr, CHECKED_STATE_SET, FrameLayout.EMPTY_STATE_SET}, new int[]{colorStateList.getColorForState(iArr, defaultColor), i2, defaultColor});
    }

    public final Drawable createDefaultItemDrawable(TintTypedArray tintTypedArray, ColorStateList colorStateList) {
        int resourceId = tintTypedArray.getResourceId(17, 0);
        int resourceId2 = tintTypedArray.getResourceId(18, 0);
        Context context = getContext();
        RelativeCornerSize relativeCornerSize = ShapeAppearanceModel.PILL;
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(ShapeAppearanceModel.builder(context, resourceId, resourceId2, new AbsoluteCornerSize(0)).build());
        materialShapeDrawable.setFillColor(colorStateList);
        return new InsetDrawable((Drawable) materialShapeDrawable, tintTypedArray.getDimensionPixelSize(22, 0), tintTypedArray.getDimensionPixelSize(23, 0), tintTypedArray.getDimensionPixelSize(21, 0), tintTypedArray.getDimensionPixelSize(20, 0));
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        if (this.shapeClipPath == null) {
            super.dispatchDraw(canvas);
            return;
        }
        int save = canvas.save();
        canvas.clipPath(this.shapeClipPath);
        super.dispatchDraw(canvas);
        canvas.restoreToCount(save);
    }

    @Override // com.google.android.material.internal.ScrimInsetsFrameLayout, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this);
    }

    @Override // com.google.android.material.internal.ScrimInsetsFrameLayout, android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this.onGlobalLayoutListener);
    }

    @Override // com.google.android.material.internal.ScrimInsetsFrameLayout
    public final void onInsetsChanged(WindowInsetsCompat windowInsetsCompat) {
        NavigationMenuPresenter navigationMenuPresenter = this.presenter;
        navigationMenuPresenter.getClass();
        int systemWindowInsetTop = windowInsetsCompat.getSystemWindowInsetTop();
        if (navigationMenuPresenter.paddingTopDefault != systemWindowInsetTop) {
            navigationMenuPresenter.paddingTopDefault = systemWindowInsetTop;
            int i = (navigationMenuPresenter.headerLayout.getChildCount() == 0 && navigationMenuPresenter.isBehindStatusBar) ? navigationMenuPresenter.paddingTopDefault : 0;
            NavigationMenuView navigationMenuView = navigationMenuPresenter.menuView;
            navigationMenuView.setPadding(0, i, 0, navigationMenuView.getPaddingBottom());
        }
        NavigationMenuView navigationMenuView2 = navigationMenuPresenter.menuView;
        navigationMenuView2.setPadding(0, navigationMenuView2.getPaddingTop(), 0, windowInsetsCompat.getSystemWindowInsetBottom());
        ViewCompat.dispatchApplyWindowInsets(navigationMenuPresenter.headerLayout, windowInsetsCompat);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        if (mode == Integer.MIN_VALUE) {
            i = View.MeasureSpec.makeMeasureSpec(Math.min(View.MeasureSpec.getSize(i), this.maxWidth), VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        } else if (mode == 0) {
            i = View.MeasureSpec.makeMeasureSpec(this.maxWidth, VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        }
        super.onMeasure(i, i2);
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.mSuperState);
        this.menu.restorePresenterStates(savedState.menuState);
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        Bundle bundle = new Bundle();
        savedState.menuState = bundle;
        this.menu.savePresenterStates(bundle);
        return savedState;
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (!(getParent() instanceof DrawerLayout) || this.drawerLayoutCornerSize <= 0 || !(getBackground() instanceof MaterialShapeDrawable)) {
            this.shapeClipPath = null;
            this.shapeClipBounds.setEmpty();
            return;
        }
        MaterialShapeDrawable materialShapeDrawable = (MaterialShapeDrawable) getBackground();
        ShapeAppearanceModel shapeAppearanceModel = materialShapeDrawable.drawableState.shapeAppearanceModel;
        shapeAppearanceModel.getClass();
        ShapeAppearanceModel.Builder builder = new ShapeAppearanceModel.Builder(shapeAppearanceModel);
        int i5 = this.layoutGravity;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (Gravity.getAbsoluteGravity(i5, ViewCompat.Api17Impl.getLayoutDirection(this)) == 3) {
            builder.setTopRightCornerSize(this.drawerLayoutCornerSize);
            builder.setBottomRightCornerSize(this.drawerLayoutCornerSize);
        } else {
            builder.setTopLeftCornerSize(this.drawerLayoutCornerSize);
            builder.setBottomLeftCornerSize(this.drawerLayoutCornerSize);
        }
        materialShapeDrawable.setShapeAppearanceModel(builder.build());
        if (this.shapeClipPath == null) {
            this.shapeClipPath = new Path();
        }
        this.shapeClipPath.reset();
        this.shapeClipBounds.set(0.0f, 0.0f, i, i2);
        ShapeAppearancePathProvider shapeAppearancePathProvider = ShapeAppearancePathProvider.Lazy.INSTANCE;
        MaterialShapeDrawable.MaterialShapeDrawableState materialShapeDrawableState = materialShapeDrawable.drawableState;
        shapeAppearancePathProvider.calculatePath(materialShapeDrawableState.shapeAppearanceModel, materialShapeDrawableState.interpolation, this.shapeClipBounds, null, this.shapeClipPath);
        invalidate();
    }

    @Override // android.view.View
    public final void setElevation(float f) {
        super.setElevation(f);
        MaterialShapeUtils.setElevation(this, f);
    }

    @Override // android.view.View
    public final void setOverScrollMode(int i) {
        super.setOverScrollMode(i);
        NavigationMenuPresenter navigationMenuPresenter = this.presenter;
        if (navigationMenuPresenter != null) {
            navigationMenuPresenter.overScrollMode = i;
            NavigationMenuView navigationMenuView = navigationMenuPresenter.menuView;
            if (navigationMenuView != null) {
                navigationMenuView.setOverScrollMode(i);
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator() { // from class: com.google.android.material.navigation.NavigationView.SavedState.1
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
                return new SavedState(parcel, null);
            }
        };
        public Bundle menuState;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.menuState = parcel.readBundle(classLoader);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.mSuperState, i);
            parcel.writeBundle(this.menuState);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public NavigationView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.android.systemui.R.attr.navigationViewStyle);
    }

    /* JADX WARN: Type inference failed for: r1v6, types: [com.google.android.material.navigation.NavigationView$2] */
    public NavigationView(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 2132018825), attributeSet, i);
        ColorStateList createDefaultColorStateList;
        int dimensionPixelSize;
        NavigationMenuPresenter navigationMenuPresenter = new NavigationMenuPresenter();
        this.presenter = navigationMenuPresenter;
        this.tmpLocation = new int[2];
        this.topInsetScrimEnabled = true;
        this.bottomInsetScrimEnabled = true;
        this.layoutGravity = 0;
        this.drawerLayoutCornerSize = 0;
        this.shapeClipBounds = new RectF();
        Context context2 = getContext();
        NavigationMenu navigationMenu = new NavigationMenu(context2);
        this.menu = navigationMenu;
        TintTypedArray obtainTintedStyledAttributes = ThemeEnforcement.obtainTintedStyledAttributes(context2, attributeSet, R$styleable.NavigationView, i, 2132018825, new int[0]);
        if (obtainTintedStyledAttributes.hasValue(1)) {
            Drawable drawable = obtainTintedStyledAttributes.getDrawable(1);
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.setBackground(this, drawable);
        }
        this.drawerLayoutCornerSize = obtainTintedStyledAttributes.getDimensionPixelSize(7, 0);
        this.layoutGravity = obtainTintedStyledAttributes.getInt(0, 0);
        if (getBackground() == null || (getBackground() instanceof ColorDrawable)) {
            ShapeAppearanceModel build = ShapeAppearanceModel.builder(context2, attributeSet, i, 2132018825).build();
            Drawable background = getBackground();
            MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(build);
            if (background instanceof ColorDrawable) {
                materialShapeDrawable.setFillColor(ColorStateList.valueOf(((ColorDrawable) background).getColor()));
            }
            materialShapeDrawable.initializeElevationOverlay(context2);
            WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.setBackground(this, materialShapeDrawable);
        }
        if (obtainTintedStyledAttributes.hasValue(8)) {
            setElevation(obtainTintedStyledAttributes.getDimensionPixelSize(8, 0));
        }
        setFitsSystemWindows(obtainTintedStyledAttributes.getBoolean(2, false));
        this.maxWidth = obtainTintedStyledAttributes.getDimensionPixelSize(3, 0);
        ColorStateList colorStateList = obtainTintedStyledAttributes.hasValue(30) ? obtainTintedStyledAttributes.getColorStateList(30) : null;
        int resourceId = obtainTintedStyledAttributes.hasValue(33) ? obtainTintedStyledAttributes.getResourceId(33, 0) : 0;
        if (resourceId == 0 && colorStateList == null) {
            colorStateList = createDefaultColorStateList(R.attr.textColorSecondary);
        }
        if (obtainTintedStyledAttributes.hasValue(14)) {
            createDefaultColorStateList = obtainTintedStyledAttributes.getColorStateList(14);
        } else {
            createDefaultColorStateList = createDefaultColorStateList(R.attr.textColorSecondary);
        }
        int resourceId2 = obtainTintedStyledAttributes.hasValue(24) ? obtainTintedStyledAttributes.getResourceId(24, 0) : 0;
        if (obtainTintedStyledAttributes.hasValue(13) && navigationMenuPresenter.itemIconSize != (dimensionPixelSize = obtainTintedStyledAttributes.getDimensionPixelSize(13, 0))) {
            navigationMenuPresenter.itemIconSize = dimensionPixelSize;
            navigationMenuPresenter.hasCustomItemIconSize = true;
            navigationMenuPresenter.updateMenuView(false);
        }
        ColorStateList colorStateList2 = obtainTintedStyledAttributes.hasValue(25) ? obtainTintedStyledAttributes.getColorStateList(25) : null;
        if (resourceId2 == 0 && colorStateList2 == null) {
            colorStateList2 = createDefaultColorStateList(R.attr.textColorPrimary);
        }
        Drawable drawable2 = obtainTintedStyledAttributes.getDrawable(10);
        if (drawable2 == null) {
            if (obtainTintedStyledAttributes.hasValue(17) || obtainTintedStyledAttributes.hasValue(18)) {
                drawable2 = createDefaultItemDrawable(obtainTintedStyledAttributes, MaterialResources.getColorStateList(getContext(), obtainTintedStyledAttributes, 19));
                ColorStateList colorStateList3 = MaterialResources.getColorStateList(context2, obtainTintedStyledAttributes, 16);
                if (colorStateList3 != null) {
                    navigationMenuPresenter.itemForeground = new RippleDrawable(colorStateList3, null, createDefaultItemDrawable(obtainTintedStyledAttributes, null));
                    navigationMenuPresenter.updateMenuView(false);
                }
            }
        }
        if (obtainTintedStyledAttributes.hasValue(11)) {
            navigationMenuPresenter.itemHorizontalPadding = obtainTintedStyledAttributes.getDimensionPixelSize(11, 0);
            navigationMenuPresenter.updateMenuView(false);
        }
        if (obtainTintedStyledAttributes.hasValue(26)) {
            navigationMenuPresenter.itemVerticalPadding = obtainTintedStyledAttributes.getDimensionPixelSize(26, 0);
            navigationMenuPresenter.updateMenuView(false);
        }
        navigationMenuPresenter.dividerInsetStart = obtainTintedStyledAttributes.getDimensionPixelSize(6, 0);
        navigationMenuPresenter.updateMenuView(false);
        navigationMenuPresenter.dividerInsetEnd = obtainTintedStyledAttributes.getDimensionPixelSize(5, 0);
        navigationMenuPresenter.updateMenuView(false);
        navigationMenuPresenter.subheaderInsetStart = obtainTintedStyledAttributes.getDimensionPixelSize(32, 0);
        navigationMenuPresenter.updateMenuView(false);
        navigationMenuPresenter.subheaderInsetStart = obtainTintedStyledAttributes.getDimensionPixelSize(31, 0);
        navigationMenuPresenter.updateMenuView(false);
        this.topInsetScrimEnabled = obtainTintedStyledAttributes.getBoolean(34, this.topInsetScrimEnabled);
        this.bottomInsetScrimEnabled = obtainTintedStyledAttributes.getBoolean(4, this.bottomInsetScrimEnabled);
        int dimensionPixelSize2 = obtainTintedStyledAttributes.getDimensionPixelSize(12, 0);
        navigationMenuPresenter.itemMaxLines = obtainTintedStyledAttributes.getInt(15, 1);
        navigationMenuPresenter.updateMenuView(false);
        navigationMenu.mCallback = new MenuBuilder.Callback() { // from class: com.google.android.material.navigation.NavigationView.1
            @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
            public final boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
                NavigationView.this.getClass();
                return false;
            }

            @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
            public final void onMenuModeChange(MenuBuilder menuBuilder) {
            }
        };
        navigationMenuPresenter.f459id = 1;
        navigationMenuPresenter.initForMenu(context2, navigationMenu);
        if (resourceId != 0) {
            navigationMenuPresenter.subheaderTextAppearance = resourceId;
            navigationMenuPresenter.updateMenuView(false);
        }
        navigationMenuPresenter.subheaderColor = colorStateList;
        navigationMenuPresenter.updateMenuView(false);
        navigationMenuPresenter.iconTintList = createDefaultColorStateList;
        navigationMenuPresenter.updateMenuView(false);
        int overScrollMode = getOverScrollMode();
        navigationMenuPresenter.overScrollMode = overScrollMode;
        NavigationMenuView navigationMenuView = navigationMenuPresenter.menuView;
        if (navigationMenuView != null) {
            navigationMenuView.setOverScrollMode(overScrollMode);
        }
        if (resourceId2 != 0) {
            navigationMenuPresenter.textAppearance = resourceId2;
            navigationMenuPresenter.updateMenuView(false);
        }
        navigationMenuPresenter.textColor = colorStateList2;
        navigationMenuPresenter.updateMenuView(false);
        navigationMenuPresenter.itemBackground = drawable2;
        navigationMenuPresenter.updateMenuView(false);
        navigationMenuPresenter.itemIconPadding = dimensionPixelSize2;
        navigationMenuPresenter.updateMenuView(false);
        navigationMenu.addMenuPresenter(navigationMenuPresenter, navigationMenu.mContext);
        if (navigationMenuPresenter.menuView == null) {
            NavigationMenuView navigationMenuView2 = (NavigationMenuView) navigationMenuPresenter.layoutInflater.inflate(com.android.systemui.R.layout.design_navigation_menu, (ViewGroup) this, false);
            navigationMenuPresenter.menuView = navigationMenuView2;
            NavigationMenuPresenter.NavigationMenuViewAccessibilityDelegate navigationMenuViewAccessibilityDelegate = navigationMenuPresenter.new NavigationMenuViewAccessibilityDelegate(navigationMenuPresenter.menuView);
            navigationMenuView2.mAccessibilityDelegate = navigationMenuViewAccessibilityDelegate;
            ViewCompat.setAccessibilityDelegate(navigationMenuView2, navigationMenuViewAccessibilityDelegate);
            if (navigationMenuPresenter.adapter == null) {
                navigationMenuPresenter.adapter = navigationMenuPresenter.new NavigationMenuAdapter();
            }
            int i2 = navigationMenuPresenter.overScrollMode;
            if (i2 != -1) {
                navigationMenuPresenter.menuView.setOverScrollMode(i2);
            }
            navigationMenuPresenter.headerLayout = (LinearLayout) navigationMenuPresenter.layoutInflater.inflate(com.android.systemui.R.layout.design_navigation_item_header, (ViewGroup) navigationMenuPresenter.menuView, false);
            navigationMenuPresenter.menuView.setAdapter(navigationMenuPresenter.adapter);
        }
        addView(navigationMenuPresenter.menuView);
        if (obtainTintedStyledAttributes.hasValue(27)) {
            int resourceId3 = obtainTintedStyledAttributes.getResourceId(27, 0);
            NavigationMenuPresenter.NavigationMenuAdapter navigationMenuAdapter = navigationMenuPresenter.adapter;
            if (navigationMenuAdapter != null) {
                navigationMenuAdapter.updateSuspended = true;
            }
            if (this.menuInflater == null) {
                this.menuInflater = new SupportMenuInflater(getContext());
            }
            this.menuInflater.inflate(resourceId3, navigationMenu);
            NavigationMenuPresenter.NavigationMenuAdapter navigationMenuAdapter2 = navigationMenuPresenter.adapter;
            if (navigationMenuAdapter2 != null) {
                navigationMenuAdapter2.updateSuspended = false;
            }
            navigationMenuPresenter.updateMenuView(false);
        }
        if (obtainTintedStyledAttributes.hasValue(9)) {
            navigationMenuPresenter.headerLayout.addView(navigationMenuPresenter.layoutInflater.inflate(obtainTintedStyledAttributes.getResourceId(9, 0), (ViewGroup) navigationMenuPresenter.headerLayout, false));
            NavigationMenuView navigationMenuView3 = navigationMenuPresenter.menuView;
            navigationMenuView3.setPadding(0, 0, 0, navigationMenuView3.getPaddingBottom());
        }
        obtainTintedStyledAttributes.recycle();
        this.onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.google.android.material.navigation.NavigationView.2
            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
            public final void onGlobalLayout() {
                NavigationView navigationView = NavigationView.this;
                navigationView.getLocationOnScreen(navigationView.tmpLocation);
                NavigationView navigationView2 = NavigationView.this;
                boolean z = navigationView2.tmpLocation[1] == 0;
                NavigationMenuPresenter navigationMenuPresenter2 = navigationView2.presenter;
                if (navigationMenuPresenter2.isBehindStatusBar != z) {
                    navigationMenuPresenter2.isBehindStatusBar = z;
                    int i3 = (navigationMenuPresenter2.headerLayout.getChildCount() == 0 && navigationMenuPresenter2.isBehindStatusBar) ? navigationMenuPresenter2.paddingTopDefault : 0;
                    NavigationMenuView navigationMenuView4 = navigationMenuPresenter2.menuView;
                    navigationMenuView4.setPadding(0, i3, 0, navigationMenuView4.getPaddingBottom());
                }
                NavigationView navigationView3 = NavigationView.this;
                navigationView3.drawTopInsetForeground = z && navigationView3.topInsetScrimEnabled;
                Activity activity = SeslContextUtils.getActivity(navigationView3.getContext());
                if (activity != null) {
                    boolean z2 = activity.findViewById(R.id.content).getHeight() == NavigationView.this.getHeight();
                    boolean z3 = Color.alpha(activity.getWindow().getNavigationBarColor()) != 0;
                    NavigationView navigationView4 = NavigationView.this;
                    navigationView4.drawBottomInsetForeground = z2 && z3 && navigationView4.bottomInsetScrimEnabled;
                }
            }
        };
        getViewTreeObserver().addOnGlobalLayoutListener(this.onGlobalLayoutListener);
    }
}
