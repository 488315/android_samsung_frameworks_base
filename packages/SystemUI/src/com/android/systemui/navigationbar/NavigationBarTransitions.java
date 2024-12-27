package com.android.systemui.navigationbar;

import android.content.Context;
import android.graphics.Canvas;
import android.util.SparseArray;
import android.view.View;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.navigationbar.buttons.ButtonDispatcher;
import com.android.systemui.navigationbar.buttons.ButtonInterface;
import com.android.systemui.navigationbar.store.EventTypeFactory;
import com.android.systemui.navigationbar.store.NavBarStateManager;
import com.android.systemui.navigationbar.store.NavBarStateManagerImpl;
import com.android.systemui.navigationbar.store.NavBarStore;
import com.android.systemui.navigationbar.store.NavBarStoreImpl;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.shared.navigationbar.RegionSamplingHelper;
import com.android.systemui.statusbar.phone.BarTransitions;
import com.android.systemui.statusbar.phone.LightBarTransitionsController;
import com.android.systemui.util.Utils;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class NavigationBarTransitions extends BarTransitions implements LightBarTransitionsController.DarkIntensityApplier {
    public final boolean mAllowAutoDimWallpaperNotVisible;
    public boolean mAutoDim;
    public final List mDarkIntensityListeners;
    public final DisplayTracker mDisplayTracker;
    public final LightBarTransitionsController mLightTransitionsController;
    public boolean mLightsOut;
    public boolean mLightsOutDisabled;
    public final List mListeners;
    public int mNavBarMode;
    public final NavBarStore mNavBarStore;
    public View mNavButtons;
    public final NavigationBarView mView;
    public boolean mWallpaperVisible;

    public interface DarkIntensityListener {
        void onDarkIntensity(float f);
    }

    public final class NavigationBarBackgroundDrawable extends BarTransitions.BarBackgroundDrawable {
        public final NavBarStateManager mNavBarStateManager;

        public NavigationBarBackgroundDrawable(Context context, int i) {
            super(context, i);
            this.mOpaque = context.getColor(R.color.light_navbar_background_opaque);
            this.mLightSemiTransparent = context.getColor(R.color.light_semi_transparent_navbar_background_color);
            this.mNavBarStateManager = ((NavBarStoreImpl) ((NavBarStore) Dependency.sDependency.getDependencyInner(NavBarStore.class))).getNavStateManager(context.getDisplayId());
            this.mUseFrame = false;
        }

        @Override // com.android.systemui.statusbar.phone.BarTransitions.BarBackgroundDrawable, android.graphics.drawable.Drawable
        public final void draw(Canvas canvas) {
            int i;
            if (BasicRune.NAVBAR_LIGHTBAR) {
                NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) this.mNavBarStateManager;
                boolean z = navBarStateManagerImpl.isGestureMode() || (i = navBarStateManagerImpl.states.transitionMode) == 4 || i == 3 || (i == 8 && !navBarStateManagerImpl.isNavigationBarUseThemeDefault());
                navBarStateManagerImpl.logNavBarStates(Boolean.valueOf(z), "canUseNavBarBackgroundFrame");
                this.mUseFrame = z;
            }
            super.draw(canvas);
        }

        @Override // com.android.systemui.statusbar.phone.BarTransitions.BarBackgroundDrawable
        public final void updateOpaqueColor(int i) {
            this.mOpaque = i;
            invalidateSelf();
        }
    }

    public NavigationBarTransitions(NavigationBarView navigationBarView, LightBarTransitionsController.Factory factory, DisplayTracker displayTracker) {
        super(navigationBarView, R.drawable.nav_background);
        this.mListeners = new ArrayList();
        this.mNavBarMode = 0;
        this.mLightsOutDisabled = false;
        this.mView = navigationBarView;
        this.mLightTransitionsController = factory.create(this);
        this.mDisplayTracker = displayTracker;
        this.mAllowAutoDimWallpaperNotVisible = navigationBarView.getContext().getResources().getBoolean(R.bool.config_navigation_bar_enable_auto_dim_no_visible_wallpaper);
        this.mDarkIntensityListeners = new ArrayList();
        boolean z = BasicRune.NAVBAR_LIGHTBAR;
        if (z) {
            NavigationBarBackgroundDrawable navigationBarBackgroundDrawable = new NavigationBarBackgroundDrawable(navigationBarView.getContext(), R.drawable.nav_background);
            this.mBarBackground = navigationBarBackgroundDrawable;
            navigationBarView.setBackground(navigationBarBackgroundDrawable);
        }
        navigationBarView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.navigationbar.NavigationBarTransitions$$ExternalSyntheticLambda0
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                NavigationBarTransitions navigationBarTransitions = NavigationBarTransitions.this;
                View view2 = navigationBarTransitions.mView.mCurrentView;
                if (view2 != null) {
                    navigationBarTransitions.mNavButtons = view2.findViewById(R.id.nav_buttons);
                    navigationBarTransitions.applyLightsOut(false, true);
                }
            }
        });
        View view = navigationBarView.mCurrentView;
        if (view != null) {
            this.mNavButtons = view.findViewById(R.id.nav_buttons);
        }
        if (z) {
            this.mNavBarStore = (NavBarStore) Dependency.sDependency.getDependencyInner(NavBarStore.class);
        }
    }

    @Override // com.android.systemui.statusbar.phone.LightBarTransitionsController.DarkIntensityApplier
    public final void applyDarkIntensity(float f) {
        NavigationBarView navigationBarView = this.mView;
        SparseArray sparseArray = navigationBarView.mButtonDispatchers;
        int size = sparseArray.size() - 1;
        while (true) {
            if (size < 0) {
                break;
            }
            ButtonDispatcher buttonDispatcher = (ButtonDispatcher) sparseArray.valueAt(size);
            buttonDispatcher.mDarkIntensity = Float.valueOf(f);
            int size2 = buttonDispatcher.mViews.size();
            for (int i = 0; i < size2; i++) {
                if (buttonDispatcher.mViews.get(i) instanceof ButtonInterface) {
                    ((ButtonInterface) buttonDispatcher.mViews.get(i)).setDarkIntensity(f);
                }
            }
            size--;
        }
        navigationBarView.mRotationButtonController.mRotationButton.mKeyButtonView.mRipple.setDarkIntensity(f);
        if (BasicRune.NAVBAR_REMOTEVIEW || BasicRune.NAVBAR_SETUP_WIZARD) {
            ((NavBarStoreImpl) this.mNavBarStore).handleEvent(this, new EventTypeFactory.EventType.OnUpdateDarkIntensity(f));
        }
        Iterator it = ((ArrayList) this.mDarkIntensityListeners).iterator();
        while (it.hasNext()) {
            ((DarkIntensityListener) it.next()).onDarkIntensity(f);
        }
        if (this.mAutoDim) {
            applyLightsOut(false, true);
        }
    }

    public final void applyLightsOut(boolean z, boolean z2) {
        boolean isLightsOut = isLightsOut(this.mMode);
        boolean z3 = BasicRune.NAVBAR_LIGHTBAR;
        if (z3 && this.mLightsOutDisabled) {
            return;
        }
        if (z2 || isLightsOut != this.mLightsOut) {
            this.mLightsOut = isLightsOut;
            View view = this.mNavButtons;
            if (view == null) {
                return;
            }
            view.animate().cancel();
            float f = isLightsOut ? BasicRune.NAVBAR_ENABLED ? 0.7f : (this.mLightTransitionsController.mDarkIntensity / 10.0f) + 0.6f : 1.0f;
            if (z) {
                this.mNavButtons.animate().alpha(f).setDuration(z3 ? 10 : isLightsOut ? 1500 : IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend).start();
            } else {
                this.mNavButtons.setAlpha(f);
            }
        }
    }

    @Override // com.android.systemui.statusbar.phone.LightBarTransitionsController.DarkIntensityApplier
    public final int getTintAnimationDuration() {
        if (!Utils.isGesturalModeOnDefaultDisplay(this.mView.getContext(), this.mDisplayTracker, this.mNavBarMode)) {
            return 120;
        }
        if (BasicRune.NAVBAR_ENABLED) {
            return 400;
        }
        return Math.max(1700, 400);
    }

    @Override // com.android.systemui.statusbar.phone.BarTransitions
    public final boolean isLightsOut(int i) {
        return super.isLightsOut(i) || (this.mAllowAutoDimWallpaperNotVisible && this.mAutoDim && !this.mWallpaperVisible && i != 5);
    }

    @Override // com.android.systemui.statusbar.phone.BarTransitions
    public final void onTransition(int i, int i2, boolean z) {
        applyModeBackground(i2, z);
        applyLightsOut(z, false);
        Iterator it = ((ArrayList) this.mListeners).iterator();
        while (it.hasNext()) {
            NavigationBar navigationBar = ((NavigationBar$$ExternalSyntheticLambda2) it.next()).f$0;
            RegionSamplingHelper regionSamplingHelper = navigationBar.mRegionSamplingHelper;
            if (i2 == 4) {
                regionSamplingHelper.stop();
                boolean z2 = BasicRune.NAVBAR_LIGHTBAR;
                NavigationBarTransitions navigationBarTransitions = navigationBar.mNavigationBarTransitions;
                if (z2) {
                    LightBarTransitionsController lightBarTransitionsController = navigationBarTransitions.mLightTransitionsController;
                    NavBarStateManagerImpl navBarStateManagerImpl = (NavBarStateManagerImpl) navigationBar.mNavBarStateManager;
                    int i3 = navBarStateManagerImpl.states.transitionMode;
                    lightBarTransitionsController.setIconsDark((i3 == 4 || i3 == 3 || (i3 == 8 && !navBarStateManagerImpl.isNavigationBarUseThemeDefault())) && !navBarStateManagerImpl.states.darkMode, true);
                } else {
                    navigationBarTransitions.mLightTransitionsController.setIconsDark(false, true);
                }
            } else {
                regionSamplingHelper.start(navigationBar.mSamplingBounds);
            }
        }
    }

    public final void setAutoDim(boolean z) {
        if ((z && Utils.isGesturalModeOnDefaultDisplay(this.mView.getContext(), this.mDisplayTracker, this.mNavBarMode)) || this.mAutoDim == z) {
            return;
        }
        this.mAutoDim = z;
        applyLightsOut(true, false);
    }
}
