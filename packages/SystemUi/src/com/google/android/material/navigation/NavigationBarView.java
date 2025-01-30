package com.google.android.material.navigation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.view.ViewCompat;
import androidx.customview.view.AbsSavedState;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.RelativeCornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class NavigationBarView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final int mMaxItemCount;
    public final C43211 mSelectedCallback;
    public final NavigationBarMenu menu;
    public SupportMenuInflater menuInflater;
    public final NavigationBarMenuView menuView;
    public final NavigationBarPresenter presenter;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator() { // from class: com.google.android.material.navigation.NavigationBarView.SavedState.1
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
        public Bundle menuPresenterState;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.mSuperState, i);
            parcel.writeBundle(this.menuPresenterState);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.menuPresenterState = parcel.readBundle(classLoader == null ? SavedState.class.getClassLoader() : classLoader);
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.google.android.material.navigation.NavigationBarView$1] */
    public NavigationBarView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, i2), attributeSet, i);
        MaterialShapeDrawable materialShapeDrawable;
        MaterialShapeDrawable materialShapeDrawable2;
        this.mSelectedCallback = new MenuBuilder.Callback() { // from class: com.google.android.material.navigation.NavigationBarView.1
            @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
            public final boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
                int i3 = NavigationBarView.$r8$clinit;
                NavigationBarView navigationBarView = NavigationBarView.this;
                navigationBarView.getClass();
                navigationBarView.getClass();
                return false;
            }

            @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
            public final void onMenuModeChange(MenuBuilder menuBuilder) {
            }
        };
        Context context2 = getContext();
        TintTypedArray obtainTintedStyledAttributes = ThemeEnforcement.obtainTintedStyledAttributes(context2, attributeSet, R$styleable.NavigationBarView, i, i2, 10, 9, 14);
        NavigationBarMenu navigationBarMenu = new NavigationBarMenu(context2, getClass(), getMaxItemCount());
        this.menu = navigationBarMenu;
        NavigationBarMenuView createNavigationBarMenuView = createNavigationBarMenuView(context2);
        this.menuView = createNavigationBarMenuView;
        NavigationBarPresenter navigationBarPresenter = new NavigationBarPresenter(context2);
        this.presenter = navigationBarPresenter;
        int maxItemCount = getMaxItemCount();
        this.mMaxItemCount = maxItemCount;
        createNavigationBarMenuView.mMaxItemCount = maxItemCount;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        createNavigationBarMenuView.setLayoutParams(layoutParams);
        TypedArray typedArray = obtainTintedStyledAttributes.mWrapped;
        int integer = typedArray.getInteger(15, 3);
        createNavigationBarMenuView.mViewType = integer;
        navigationBarPresenter.menuView = createNavigationBarMenuView;
        navigationBarPresenter.f460id = 1;
        createNavigationBarMenuView.presenter = navigationBarPresenter;
        navigationBarMenu.addMenuPresenter(navigationBarPresenter, navigationBarMenu.mContext);
        navigationBarPresenter.initForMenu(getContext(), navigationBarMenu);
        if (obtainTintedStyledAttributes.hasValue(5)) {
            createNavigationBarMenuView.setIconTintList(obtainTintedStyledAttributes.getColorStateList(5));
        } else {
            createNavigationBarMenuView.setIconTintList(createNavigationBarMenuView.createDefaultColorStateList());
        }
        createNavigationBarMenuView.setItemIconSize(obtainTintedStyledAttributes.getDimensionPixelSize(4, getResources().getDimensionPixelSize(R.dimen.sesl_navigation_bar_icon_size)));
        if (obtainTintedStyledAttributes.hasValue(10)) {
            int resourceId = obtainTintedStyledAttributes.getResourceId(10, 0);
            createNavigationBarMenuView.itemTextAppearanceInactive = resourceId;
            NavigationBarItemView[] navigationBarItemViewArr = createNavigationBarMenuView.buttons;
            if (navigationBarItemViewArr != null) {
                for (NavigationBarItemView navigationBarItemView : navigationBarItemViewArr) {
                    if (navigationBarItemView == null) {
                        break;
                    }
                    navigationBarItemView.setTextAppearanceInactive(resourceId);
                    ColorStateList colorStateList = createNavigationBarMenuView.itemTextColorFromUser;
                    if (colorStateList != null) {
                        navigationBarItemView.setTextColor(colorStateList);
                    }
                }
            }
            NavigationBarItemView navigationBarItemView2 = createNavigationBarMenuView.mOverflowButton;
            if (navigationBarItemView2 != null) {
                navigationBarItemView2.setTextAppearanceInactive(resourceId);
                ColorStateList colorStateList2 = createNavigationBarMenuView.itemTextColorFromUser;
                if (colorStateList2 != null) {
                    createNavigationBarMenuView.mOverflowButton.setTextColor(colorStateList2);
                }
            }
        }
        if (obtainTintedStyledAttributes.hasValue(14)) {
            int resourceId2 = obtainTintedStyledAttributes.getResourceId(14, 0);
            NavigationBarMenuView navigationBarMenuView = this.menuView;
            navigationBarMenuView.mSeslLabelTextAppearance = resourceId2;
            NavigationBarItemView[] navigationBarItemViewArr2 = navigationBarMenuView.buttons;
            if (navigationBarItemViewArr2 != null) {
                for (NavigationBarItemView navigationBarItemView3 : navigationBarItemViewArr2) {
                    if (navigationBarItemView3 == null) {
                        break;
                    }
                    navigationBarItemView3.setTextAppearanceInactive(resourceId2);
                    ColorStateList colorStateList3 = navigationBarMenuView.itemTextColorFromUser;
                    if (colorStateList3 != null) {
                        navigationBarItemView3.setTextColor(colorStateList3);
                    }
                }
            }
            NavigationBarItemView navigationBarItemView4 = navigationBarMenuView.mOverflowButton;
            if (navigationBarItemView4 != null) {
                navigationBarItemView4.setTextAppearanceInactive(resourceId2);
                ColorStateList colorStateList4 = navigationBarMenuView.itemTextColorFromUser;
                if (colorStateList4 != null) {
                    navigationBarMenuView.mOverflowButton.setTextColor(colorStateList4);
                }
            }
        }
        if (obtainTintedStyledAttributes.hasValue(9)) {
            int resourceId3 = obtainTintedStyledAttributes.getResourceId(9, 0);
            NavigationBarMenuView navigationBarMenuView2 = this.menuView;
            navigationBarMenuView2.itemTextAppearanceActive = resourceId3;
            NavigationBarItemView[] navigationBarItemViewArr3 = navigationBarMenuView2.buttons;
            if (navigationBarItemViewArr3 != null) {
                for (NavigationBarItemView navigationBarItemView5 : navigationBarItemViewArr3) {
                    if (navigationBarItemView5 == null) {
                        break;
                    }
                    navigationBarItemView5.setTextAppearanceActive(resourceId3);
                    ColorStateList colorStateList5 = navigationBarMenuView2.itemTextColorFromUser;
                    if (colorStateList5 != null) {
                        navigationBarItemView5.setTextColor(colorStateList5);
                    }
                }
            }
            NavigationBarItemView navigationBarItemView6 = navigationBarMenuView2.mOverflowButton;
            if (navigationBarItemView6 != null && navigationBarMenuView2.itemTextColorFromUser != null) {
                navigationBarItemView6.setTextAppearanceActive(resourceId3);
                navigationBarMenuView2.mOverflowButton.setTextColor(navigationBarMenuView2.itemTextColorFromUser);
            }
        }
        if (obtainTintedStyledAttributes.hasValue(11)) {
            ColorStateList colorStateList6 = obtainTintedStyledAttributes.getColorStateList(11);
            NavigationBarMenuView navigationBarMenuView3 = this.menuView;
            navigationBarMenuView3.itemTextColorFromUser = colorStateList6;
            NavigationBarItemView[] navigationBarItemViewArr4 = navigationBarMenuView3.buttons;
            if (navigationBarItemViewArr4 != null) {
                for (NavigationBarItemView navigationBarItemView7 : navigationBarItemViewArr4) {
                    if (navigationBarItemView7 == null) {
                        break;
                    }
                    navigationBarItemView7.setTextColor(colorStateList6);
                }
            }
            NavigationBarItemView navigationBarItemView8 = navigationBarMenuView3.mOverflowButton;
            if (navigationBarItemView8 != null) {
                navigationBarItemView8.setTextColor(colorStateList6);
                navigationBarMenuView3.setOverflowSpanColor(0, true);
            }
        }
        Drawable background = getBackground();
        if (background instanceof ColorDrawable) {
            this.menuView.mSBBTextColorDrawable = (ColorDrawable) background;
        }
        if (getBackground() == null || (getBackground() instanceof ColorDrawable)) {
            MaterialShapeDrawable materialShapeDrawable3 = new MaterialShapeDrawable();
            Drawable background2 = getBackground();
            if (background2 instanceof ColorDrawable) {
                materialShapeDrawable3.setFillColor(ColorStateList.valueOf(((ColorDrawable) background2).getColor()));
            }
            materialShapeDrawable3.initializeElevationOverlay(context2);
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.setBackground(this, materialShapeDrawable3);
        }
        if (obtainTintedStyledAttributes.hasValue(7)) {
            int dimensionPixelSize = obtainTintedStyledAttributes.getDimensionPixelSize(7, 0);
            NavigationBarItemView[] navigationBarItemViewArr5 = this.menuView.buttons;
            if (navigationBarItemViewArr5 != null) {
                for (NavigationBarItemView navigationBarItemView9 : navigationBarItemViewArr5) {
                    if (navigationBarItemView9.itemPaddingTop != dimensionPixelSize) {
                        navigationBarItemView9.itemPaddingTop = dimensionPixelSize;
                        MenuItemImpl menuItemImpl = navigationBarItemView9.itemData;
                        if (menuItemImpl != null) {
                            navigationBarItemView9.setChecked(menuItemImpl.isChecked());
                        }
                    }
                }
            }
        }
        if (obtainTintedStyledAttributes.hasValue(6)) {
            int dimensionPixelSize2 = obtainTintedStyledAttributes.getDimensionPixelSize(6, 0);
            NavigationBarItemView[] navigationBarItemViewArr6 = this.menuView.buttons;
            if (navigationBarItemViewArr6 != null) {
                for (NavigationBarItemView navigationBarItemView10 : navigationBarItemViewArr6) {
                    if (navigationBarItemView10.itemPaddingBottom != dimensionPixelSize2) {
                        navigationBarItemView10.itemPaddingBottom = dimensionPixelSize2;
                        MenuItemImpl menuItemImpl2 = navigationBarItemView10.itemData;
                        if (menuItemImpl2 != null) {
                            navigationBarItemView10.setChecked(menuItemImpl2.isChecked());
                        }
                    }
                }
            }
        }
        if (obtainTintedStyledAttributes.hasValue(1)) {
            setElevation(obtainTintedStyledAttributes.getDimensionPixelSize(1, 0));
        }
        getBackground().mutate().setTintList(MaterialResources.getColorStateList(context2, obtainTintedStyledAttributes, 0));
        int integer2 = typedArray.getInteger(12, -1);
        NavigationBarMenuView navigationBarMenuView4 = this.menuView;
        if (navigationBarMenuView4.labelVisibilityMode != integer2) {
            navigationBarMenuView4.labelVisibilityMode = integer2;
            this.presenter.updateMenuView(false);
        }
        int resourceId4 = obtainTintedStyledAttributes.getResourceId(3, 0);
        if (resourceId4 != 0) {
            NavigationBarMenuView navigationBarMenuView5 = this.menuView;
            navigationBarMenuView5.itemBackgroundRes = resourceId4;
            NavigationBarItemView[] navigationBarItemViewArr7 = navigationBarMenuView5.buttons;
            if (navigationBarItemViewArr7 != null) {
                for (NavigationBarItemView navigationBarItemView11 : navigationBarItemViewArr7) {
                    if (navigationBarItemView11 == null) {
                        break;
                    }
                    navigationBarItemView11.setItemBackground(resourceId4);
                }
            }
            NavigationBarItemView navigationBarItemView12 = navigationBarMenuView5.mOverflowButton;
            if (navigationBarItemView12 != null) {
                navigationBarItemView12.setItemBackground(resourceId4);
            }
        } else {
            ColorStateList colorStateList7 = MaterialResources.getColorStateList(context2, obtainTintedStyledAttributes, 8);
            NavigationBarItemView[] navigationBarItemViewArr8 = this.menuView.buttons;
            if (navigationBarItemViewArr8 != null) {
                for (NavigationBarItemView navigationBarItemView13 : navigationBarItemViewArr8) {
                    navigationBarItemView13.itemRippleColor = colorStateList7;
                    navigationBarItemView13.refreshItemBackground();
                }
            }
        }
        int resourceId5 = obtainTintedStyledAttributes.getResourceId(2, 0);
        if (resourceId5 != 0) {
            NavigationBarItemView[] navigationBarItemViewArr9 = this.menuView.buttons;
            if (navigationBarItemViewArr9 != null) {
                for (NavigationBarItemView navigationBarItemView14 : navigationBarItemViewArr9) {
                    navigationBarItemView14.activeIndicatorEnabled = true;
                    navigationBarItemView14.refreshItemBackground();
                    View view = navigationBarItemView14.activeIndicatorView;
                    if (view != null) {
                        view.setVisibility(0);
                        navigationBarItemView14.requestLayout();
                    }
                }
            }
            TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(resourceId5, R$styleable.NavigationBarActiveIndicator);
            int dimensionPixelSize3 = obtainStyledAttributes.getDimensionPixelSize(1, 0);
            NavigationBarItemView[] navigationBarItemViewArr10 = this.menuView.buttons;
            if (navigationBarItemViewArr10 != null) {
                for (NavigationBarItemView navigationBarItemView15 : navigationBarItemViewArr10) {
                    navigationBarItemView15.activeIndicatorDesiredWidth = dimensionPixelSize3;
                    navigationBarItemView15.updateActiveIndicatorLayoutParams(navigationBarItemView15.getWidth());
                }
            }
            int dimensionPixelSize4 = obtainStyledAttributes.getDimensionPixelSize(0, 0);
            NavigationBarItemView[] navigationBarItemViewArr11 = this.menuView.buttons;
            if (navigationBarItemViewArr11 != null) {
                for (NavigationBarItemView navigationBarItemView16 : navigationBarItemViewArr11) {
                    navigationBarItemView16.activeIndicatorDesiredHeight = dimensionPixelSize4;
                    navigationBarItemView16.updateActiveIndicatorLayoutParams(navigationBarItemView16.getWidth());
                }
            }
            int dimensionPixelOffset = obtainStyledAttributes.getDimensionPixelOffset(3, 0);
            NavigationBarItemView[] navigationBarItemViewArr12 = this.menuView.buttons;
            if (navigationBarItemViewArr12 != null) {
                for (NavigationBarItemView navigationBarItemView17 : navigationBarItemViewArr12) {
                    navigationBarItemView17.activeIndicatorMarginHorizontal = dimensionPixelOffset;
                    navigationBarItemView17.updateActiveIndicatorLayoutParams(navigationBarItemView17.getWidth());
                }
            }
            ColorStateList colorStateList8 = MaterialResources.getColorStateList(context2, obtainStyledAttributes, 2);
            NavigationBarMenuView navigationBarMenuView6 = this.menuView;
            navigationBarMenuView6.itemActiveIndicatorColor = colorStateList8;
            NavigationBarItemView[] navigationBarItemViewArr13 = navigationBarMenuView6.buttons;
            if (navigationBarItemViewArr13 != null) {
                for (NavigationBarItemView navigationBarItemView18 : navigationBarItemViewArr13) {
                    if (navigationBarMenuView6.itemActiveIndicatorShapeAppearance == null || navigationBarMenuView6.itemActiveIndicatorColor == null) {
                        materialShapeDrawable2 = null;
                    } else {
                        materialShapeDrawable2 = new MaterialShapeDrawable(navigationBarMenuView6.itemActiveIndicatorShapeAppearance);
                        materialShapeDrawable2.setFillColor(navigationBarMenuView6.itemActiveIndicatorColor);
                    }
                    View view2 = navigationBarItemView18.activeIndicatorView;
                    if (view2 != null) {
                        view2.setBackgroundDrawable(materialShapeDrawable2);
                        navigationBarItemView18.refreshItemBackground();
                    }
                }
            }
            int resourceId6 = obtainStyledAttributes.getResourceId(4, 0);
            RelativeCornerSize relativeCornerSize = ShapeAppearanceModel.PILL;
            ShapeAppearanceModel build = ShapeAppearanceModel.builder(context2, resourceId6, 0, new AbsoluteCornerSize(0)).build();
            NavigationBarMenuView navigationBarMenuView7 = this.menuView;
            navigationBarMenuView7.itemActiveIndicatorShapeAppearance = build;
            NavigationBarItemView[] navigationBarItemViewArr14 = navigationBarMenuView7.buttons;
            if (navigationBarItemViewArr14 != null) {
                for (NavigationBarItemView navigationBarItemView19 : navigationBarItemViewArr14) {
                    if (navigationBarMenuView7.itemActiveIndicatorShapeAppearance == null || navigationBarMenuView7.itemActiveIndicatorColor == null) {
                        materialShapeDrawable = null;
                    } else {
                        materialShapeDrawable = new MaterialShapeDrawable(navigationBarMenuView7.itemActiveIndicatorShapeAppearance);
                        materialShapeDrawable.setFillColor(navigationBarMenuView7.itemActiveIndicatorColor);
                    }
                    View view3 = navigationBarItemView19.activeIndicatorView;
                    if (view3 != null) {
                        view3.setBackgroundDrawable(materialShapeDrawable);
                        navigationBarItemView19.refreshItemBackground();
                    }
                }
            }
            obtainStyledAttributes.recycle();
        }
        if (obtainTintedStyledAttributes.hasValue(13)) {
            int resourceId7 = obtainTintedStyledAttributes.getResourceId(13, 0);
            this.presenter.updateSuspended = true;
            if (this.menuInflater == null) {
                this.menuInflater = new SupportMenuInflater(getContext());
            }
            this.menuInflater.inflate(resourceId7, this.menu);
            NavigationBarPresenter navigationBarPresenter2 = this.presenter;
            navigationBarPresenter2.updateSuspended = false;
            navigationBarPresenter2.updateMenuView(true);
        }
        obtainTintedStyledAttributes.recycle();
        addView(this.menuView);
        NavigationBarMenu navigationBarMenu2 = this.menu;
        C43211 c43211 = this.mSelectedCallback;
        navigationBarMenu2.mCallback = c43211;
        NavigationBarMenuView navigationBarMenuView8 = this.menuView;
        navigationBarMenuView8.mSelectedCallback = c43211;
        int i3 = navigationBarMenuView8.mVisibleItemCount;
        if (integer == 3 || i3 != this.mMaxItemCount) {
            int dimensionPixelSize5 = getResources().getDimensionPixelSize(R.dimen.sesl_navigation_bar_icon_mode_padding_horizontal);
            setPadding(dimensionPixelSize5, getPaddingTop(), dimensionPixelSize5, getPaddingBottom());
        } else {
            int dimensionPixelSize6 = getResources().getDimensionPixelSize(R.dimen.sesl_navigation_bar_icon_mode_min_padding_horizontal);
            setPadding(dimensionPixelSize6, getPaddingTop(), dimensionPixelSize6, getPaddingBottom());
        }
    }

    public abstract NavigationBarMenuView createNavigationBarMenuView(Context context);

    public abstract int getMaxItemCount();

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this);
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.mSuperState);
        this.menu.restorePresenterStates(savedState.menuPresenterState);
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        Bundle bundle = new Bundle();
        savedState.menuPresenterState = bundle;
        this.menu.savePresenterStates(bundle);
        return savedState;
    }

    @Override // android.view.View
    public final void setElevation(float f) {
        super.setElevation(f);
        MaterialShapeUtils.setElevation(this, f);
    }
}
