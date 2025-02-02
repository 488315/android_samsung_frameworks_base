package androidx.appcompat.app;

import android.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import androidx.appcompat.R$styleable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.view.ActionBarPolicy;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.ViewPropertyAnimatorCompatSet;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.ActionBarContainer;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.appcompat.widget.DecorToolbar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.core.view.ViewPropertyAnimatorUpdateListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class WindowDecorActionBar extends ActionBar implements ActionBarOverlayLayout.ActionBarVisibilityCallback {
    private static final Interpolator sHideInterpolator = new AccelerateInterpolator();
    private static final Interpolator sShowInterpolator = new DecelerateInterpolator();
    ActionModeImpl mActionMode;
    ActionBarContainer mContainerView;
    boolean mContentAnimations;
    View mContentView;
    Context mContext;
    ActionBarContextView mContextView;
    private int mCurWindowVisibility;
    ViewPropertyAnimatorCompatSet mCurrentShowAnim;
    DecorToolbar mDecorToolbar;
    ActionModeImpl mDeferredDestroyActionMode;
    ActionMode.Callback mDeferredModeDestroyCallback;
    private boolean mDisplayHomeAsUpSet;
    private boolean mHasEmbeddedTabs;
    boolean mHiddenBySystem;
    final ViewPropertyAnimatorListener mHideListener;
    boolean mHideOnContentScroll;
    private boolean mLastMenuVisibility;
    private ArrayList<ActionBar.OnMenuVisibilityListener> mMenuVisibilityListeners;
    private boolean mNowShowing;
    ActionBarOverlayLayout mOverlayLayout;
    private boolean mShowHideAnimationEnabled;
    final ViewPropertyAnimatorListener mShowListener;
    private boolean mShowingForMode;
    private Context mThemedContext;
    final ViewPropertyAnimatorUpdateListener mUpdateListener;

    /* renamed from: androidx.appcompat.app.WindowDecorActionBar$1 */
    final class C00211 extends ViewPropertyAnimatorListenerAdapter {
        C00211() {
        }

        @Override // androidx.core.view.ViewPropertyAnimatorListener
        public final void onAnimationEnd() {
            View view;
            WindowDecorActionBar windowDecorActionBar = WindowDecorActionBar.this;
            if (windowDecorActionBar.mContentAnimations && (view = windowDecorActionBar.mContentView) != null) {
                view.setTranslationY(0.0f);
                windowDecorActionBar.mContainerView.setTranslationY(0.0f);
            }
            windowDecorActionBar.mContainerView.setVisibility(8);
            windowDecorActionBar.mContainerView.setTransitioning(false);
            windowDecorActionBar.mCurrentShowAnim = null;
            ActionMode.Callback callback = windowDecorActionBar.mDeferredModeDestroyCallback;
            if (callback != null) {
                callback.onDestroyActionMode(windowDecorActionBar.mDeferredDestroyActionMode);
                windowDecorActionBar.mDeferredDestroyActionMode = null;
                windowDecorActionBar.mDeferredModeDestroyCallback = null;
            }
            ActionBarOverlayLayout actionBarOverlayLayout = windowDecorActionBar.mOverlayLayout;
            if (actionBarOverlayLayout != null) {
                ViewCompat.requestApplyInsets(actionBarOverlayLayout);
            }
        }
    }

    /* renamed from: androidx.appcompat.app.WindowDecorActionBar$2 */
    final class C00222 extends ViewPropertyAnimatorListenerAdapter {
        C00222() {
        }

        @Override // androidx.core.view.ViewPropertyAnimatorListener
        public final void onAnimationEnd() {
            WindowDecorActionBar windowDecorActionBar = WindowDecorActionBar.this;
            windowDecorActionBar.mCurrentShowAnim = null;
            windowDecorActionBar.mContainerView.requestLayout();
        }
    }

    public class ActionModeImpl extends ActionMode implements MenuBuilder.Callback {
        private final Context mActionModeContext;
        private ActionMode.Callback mCallback;
        private WeakReference<View> mCustomView;
        private final MenuBuilder mMenu;

        public ActionModeImpl(Context context, ActionMode.Callback callback) {
            this.mActionModeContext = context;
            this.mCallback = callback;
            MenuBuilder menuBuilder = new MenuBuilder(context);
            menuBuilder.setDefaultShowAsAction();
            this.mMenu = menuBuilder;
            menuBuilder.setCallback(this);
        }

        public final boolean dispatchOnCreate() {
            MenuBuilder menuBuilder = this.mMenu;
            menuBuilder.stopDispatchingItemsChanged();
            try {
                return this.mCallback.onCreateActionMode(this, menuBuilder);
            } finally {
                menuBuilder.startDispatchingItemsChanged();
            }
        }

        @Override // androidx.appcompat.view.ActionMode
        public final void finish() {
            WindowDecorActionBar windowDecorActionBar = WindowDecorActionBar.this;
            if (windowDecorActionBar.mActionMode != this) {
                return;
            }
            if (!windowDecorActionBar.mHiddenBySystem) {
                this.mCallback.onDestroyActionMode(this);
            } else {
                windowDecorActionBar.mDeferredDestroyActionMode = this;
                windowDecorActionBar.mDeferredModeDestroyCallback = this.mCallback;
            }
            this.mCallback = null;
            windowDecorActionBar.animateToMode(false);
            windowDecorActionBar.mContextView.closeMode();
            windowDecorActionBar.mOverlayLayout.setHideOnContentScrollEnabled(windowDecorActionBar.mHideOnContentScroll);
            windowDecorActionBar.mActionMode = null;
        }

        @Override // androidx.appcompat.view.ActionMode
        public final View getCustomView() {
            WeakReference<View> weakReference = this.mCustomView;
            if (weakReference != null) {
                return weakReference.get();
            }
            return null;
        }

        @Override // androidx.appcompat.view.ActionMode
        public final MenuBuilder getMenu() {
            return this.mMenu;
        }

        @Override // androidx.appcompat.view.ActionMode
        public final MenuInflater getMenuInflater() {
            return new SupportMenuInflater(this.mActionModeContext);
        }

        @Override // androidx.appcompat.view.ActionMode
        public final CharSequence getSubtitle() {
            return WindowDecorActionBar.this.mContextView.getSubtitle();
        }

        @Override // androidx.appcompat.view.ActionMode
        public final CharSequence getTitle() {
            return WindowDecorActionBar.this.mContextView.getTitle();
        }

        @Override // androidx.appcompat.view.ActionMode
        public final void invalidate() {
            if (WindowDecorActionBar.this.mActionMode != this) {
                return;
            }
            MenuBuilder menuBuilder = this.mMenu;
            menuBuilder.stopDispatchingItemsChanged();
            try {
                this.mCallback.onPrepareActionMode(this, menuBuilder);
            } finally {
                menuBuilder.startDispatchingItemsChanged();
            }
        }

        @Override // androidx.appcompat.view.ActionMode
        public final boolean isTitleOptional() {
            return WindowDecorActionBar.this.mContextView.isTitleOptional();
        }

        @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
        public final boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            ActionMode.Callback callback = this.mCallback;
            if (callback != null) {
                return callback.onActionItemClicked(this, menuItem);
            }
            return false;
        }

        @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
        public final void onMenuModeChange(MenuBuilder menuBuilder) {
            if (this.mCallback == null) {
                return;
            }
            invalidate();
            WindowDecorActionBar.this.mContextView.showOverflowMenu();
        }

        @Override // androidx.appcompat.view.ActionMode
        public final void setCustomView(View view) {
            WindowDecorActionBar.this.mContextView.setCustomView(view);
            this.mCustomView = new WeakReference<>(view);
        }

        @Override // androidx.appcompat.view.ActionMode
        public final void setSubtitle(CharSequence charSequence) {
            WindowDecorActionBar.this.mContextView.setSubtitle(charSequence);
        }

        @Override // androidx.appcompat.view.ActionMode
        public final void setTitle(CharSequence charSequence) {
            WindowDecorActionBar.this.mContextView.setTitle(charSequence);
        }

        @Override // androidx.appcompat.view.ActionMode
        public final void setTitleOptionalHint(boolean z) {
            super.setTitleOptionalHint(z);
            WindowDecorActionBar.this.mContextView.setTitleOptional(z);
        }

        @Override // androidx.appcompat.view.ActionMode
        public final void setSubtitle(int i) {
            setSubtitle(WindowDecorActionBar.this.mContext.getResources().getString(i));
        }

        @Override // androidx.appcompat.view.ActionMode
        public final void setTitle(int i) {
            setTitle(WindowDecorActionBar.this.mContext.getResources().getString(i));
        }
    }

    public WindowDecorActionBar(Activity activity, boolean z) {
        new ArrayList();
        this.mMenuVisibilityListeners = new ArrayList<>();
        this.mCurWindowVisibility = 0;
        this.mContentAnimations = true;
        this.mNowShowing = true;
        this.mHideListener = new C00211();
        this.mShowListener = new C00222();
        this.mUpdateListener = new ViewPropertyAnimatorUpdateListener() { // from class: androidx.appcompat.app.WindowDecorActionBar.3
            @Override // androidx.core.view.ViewPropertyAnimatorUpdateListener
            public final void onAnimationUpdate() {
                ((View) WindowDecorActionBar.this.mContainerView.getParent()).invalidate();
            }
        };
        View decorView = activity.getWindow().getDecorView();
        init(decorView);
        if (z) {
            return;
        }
        this.mContentView = decorView.findViewById(R.id.content);
    }

    private void init(View view) {
        DecorToolbar wrapper;
        ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) view.findViewById(com.samsung.android.biometrics.app.setting.R.id.decor_content_parent);
        this.mOverlayLayout = actionBarOverlayLayout;
        if (actionBarOverlayLayout != null) {
            actionBarOverlayLayout.setActionBarVisibilityCallback(this);
        }
        KeyEvent.Callback findViewById = view.findViewById(com.samsung.android.biometrics.app.setting.R.id.action_bar);
        if (findViewById instanceof DecorToolbar) {
            wrapper = (DecorToolbar) findViewById;
        } else {
            if (!(findViewById instanceof Toolbar)) {
                throw new IllegalStateException("Can't make a decor toolbar out of ".concat(findViewById != null ? findViewById.getClass().getSimpleName() : "null"));
            }
            wrapper = ((Toolbar) findViewById).getWrapper();
        }
        this.mDecorToolbar = wrapper;
        this.mContextView = (ActionBarContextView) view.findViewById(com.samsung.android.biometrics.app.setting.R.id.action_context_bar);
        ActionBarContainer actionBarContainer = (ActionBarContainer) view.findViewById(com.samsung.android.biometrics.app.setting.R.id.action_bar_container);
        this.mContainerView = actionBarContainer;
        DecorToolbar decorToolbar = this.mDecorToolbar;
        if (decorToolbar == null || this.mContextView == null || actionBarContainer == null) {
            throw new IllegalStateException("WindowDecorActionBar can only be used with a compatible window decor layout");
        }
        this.mContext = decorToolbar.getContext();
        if ((this.mDecorToolbar.getDisplayOptions() & 4) != 0) {
            this.mDisplayHomeAsUpSet = true;
        }
        ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(this.mContext);
        actionBarPolicy.enableHomeButtonByDefault();
        this.mDecorToolbar.setHomeButtonEnabled();
        setHasEmbeddedTabs(actionBarPolicy.hasEmbeddedTabs());
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(null, R$styleable.ActionBar, com.samsung.android.biometrics.app.setting.R.attr.actionBarStyle, 0);
        if (obtainStyledAttributes.getBoolean(14, false)) {
            if (!this.mOverlayLayout.isInOverlayMode()) {
                throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll");
            }
            this.mHideOnContentScroll = true;
            this.mOverlayLayout.setHideOnContentScrollEnabled(true);
        }
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(12, 0);
        if (dimensionPixelSize != 0) {
            ViewCompat.setElevation(this.mContainerView, dimensionPixelSize);
        }
        obtainStyledAttributes.recycle();
    }

    private void setHasEmbeddedTabs(boolean z) {
        this.mHasEmbeddedTabs = z;
        if (z) {
            this.mContainerView.setTabContainer(null);
            this.mDecorToolbar.setEmbeddedTabView();
        } else {
            this.mDecorToolbar.setEmbeddedTabView();
            this.mContainerView.setTabContainer(null);
        }
        this.mDecorToolbar.getNavigationMode();
        DecorToolbar decorToolbar = this.mDecorToolbar;
        boolean z2 = this.mHasEmbeddedTabs;
        decorToolbar.setCollapsible(false);
        ActionBarOverlayLayout actionBarOverlayLayout = this.mOverlayLayout;
        boolean z3 = this.mHasEmbeddedTabs;
        actionBarOverlayLayout.setHasNonEmbeddedTabs(false);
    }

    private void updateVisibility(boolean z) {
        View view;
        View view2;
        View view3;
        boolean z2 = this.mShowingForMode || !this.mHiddenBySystem;
        ViewPropertyAnimatorUpdateListener viewPropertyAnimatorUpdateListener = this.mUpdateListener;
        if (!z2) {
            if (this.mNowShowing) {
                this.mNowShowing = false;
                ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = this.mCurrentShowAnim;
                if (viewPropertyAnimatorCompatSet != null) {
                    viewPropertyAnimatorCompatSet.cancel();
                }
                int i = this.mCurWindowVisibility;
                ViewPropertyAnimatorListener viewPropertyAnimatorListener = this.mHideListener;
                if (i != 0 || (!this.mShowHideAnimationEnabled && !z)) {
                    ((C00211) viewPropertyAnimatorListener).onAnimationEnd();
                    return;
                }
                this.mContainerView.setAlpha(1.0f);
                this.mContainerView.setTransitioning(true);
                ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet2 = new ViewPropertyAnimatorCompatSet();
                float f = -this.mContainerView.getHeight();
                if (z) {
                    this.mContainerView.getLocationInWindow(new int[]{0, 0});
                    f -= r8[1];
                }
                ViewPropertyAnimatorCompat animate = ViewCompat.animate(this.mContainerView);
                animate.translationY(f);
                animate.setUpdateListener(viewPropertyAnimatorUpdateListener);
                viewPropertyAnimatorCompatSet2.play(animate);
                if (this.mContentAnimations && (view = this.mContentView) != null) {
                    ViewPropertyAnimatorCompat animate2 = ViewCompat.animate(view);
                    animate2.translationY(f);
                    viewPropertyAnimatorCompatSet2.play(animate2);
                }
                viewPropertyAnimatorCompatSet2.setInterpolator(sHideInterpolator);
                viewPropertyAnimatorCompatSet2.setDuration();
                viewPropertyAnimatorCompatSet2.setListener((ViewPropertyAnimatorListenerAdapter) viewPropertyAnimatorListener);
                this.mCurrentShowAnim = viewPropertyAnimatorCompatSet2;
                viewPropertyAnimatorCompatSet2.start();
                return;
            }
            return;
        }
        if (this.mNowShowing) {
            return;
        }
        this.mNowShowing = true;
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet3 = this.mCurrentShowAnim;
        if (viewPropertyAnimatorCompatSet3 != null) {
            viewPropertyAnimatorCompatSet3.cancel();
        }
        this.mContainerView.setVisibility(0);
        int i2 = this.mCurWindowVisibility;
        ViewPropertyAnimatorListener viewPropertyAnimatorListener2 = this.mShowListener;
        if (i2 == 0 && (this.mShowHideAnimationEnabled || z)) {
            this.mContainerView.setTranslationY(0.0f);
            float f2 = -this.mContainerView.getHeight();
            if (z) {
                this.mContainerView.getLocationInWindow(new int[]{0, 0});
                f2 -= r8[1];
            }
            this.mContainerView.setTranslationY(f2);
            ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet4 = new ViewPropertyAnimatorCompatSet();
            ViewPropertyAnimatorCompat animate3 = ViewCompat.animate(this.mContainerView);
            animate3.translationY(0.0f);
            animate3.setUpdateListener(viewPropertyAnimatorUpdateListener);
            viewPropertyAnimatorCompatSet4.play(animate3);
            if (this.mContentAnimations && (view3 = this.mContentView) != null) {
                view3.setTranslationY(f2);
                ViewPropertyAnimatorCompat animate4 = ViewCompat.animate(this.mContentView);
                animate4.translationY(0.0f);
                viewPropertyAnimatorCompatSet4.play(animate4);
            }
            viewPropertyAnimatorCompatSet4.setInterpolator(sShowInterpolator);
            viewPropertyAnimatorCompatSet4.setDuration();
            viewPropertyAnimatorCompatSet4.setListener((ViewPropertyAnimatorListenerAdapter) viewPropertyAnimatorListener2);
            this.mCurrentShowAnim = viewPropertyAnimatorCompatSet4;
            viewPropertyAnimatorCompatSet4.start();
        } else {
            this.mContainerView.setAlpha(1.0f);
            this.mContainerView.setTranslationY(0.0f);
            if (this.mContentAnimations && (view2 = this.mContentView) != null) {
                view2.setTranslationY(0.0f);
            }
            ((C00222) viewPropertyAnimatorListener2).onAnimationEnd();
        }
        ActionBarOverlayLayout actionBarOverlayLayout = this.mOverlayLayout;
        if (actionBarOverlayLayout != null) {
            ViewCompat.requestApplyInsets(actionBarOverlayLayout);
        }
    }

    public final void animateToMode(boolean z) {
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat;
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2;
        if (z) {
            if (!this.mShowingForMode) {
                this.mShowingForMode = true;
                ActionBarOverlayLayout actionBarOverlayLayout = this.mOverlayLayout;
                if (actionBarOverlayLayout != null) {
                    actionBarOverlayLayout.setShowingForActionMode(true);
                }
                updateVisibility(false);
            }
        } else if (this.mShowingForMode) {
            this.mShowingForMode = false;
            ActionBarOverlayLayout actionBarOverlayLayout2 = this.mOverlayLayout;
            if (actionBarOverlayLayout2 != null) {
                actionBarOverlayLayout2.setShowingForActionMode(false);
            }
            updateVisibility(false);
        }
        if (!ViewCompat.isLaidOut(this.mContainerView)) {
            if (z) {
                this.mDecorToolbar.setVisibility(4);
                this.mContextView.setVisibility(0);
                return;
            } else {
                this.mDecorToolbar.setVisibility(0);
                this.mContextView.setVisibility(8);
                return;
            }
        }
        if (z) {
            viewPropertyAnimatorCompat = this.mDecorToolbar.setupAnimatorToVisibility(4, 100L);
            viewPropertyAnimatorCompat2 = this.mContextView.setupAnimatorToVisibility(0, 200L);
        } else {
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat3 = this.mDecorToolbar.setupAnimatorToVisibility(0, 200L);
            viewPropertyAnimatorCompat = this.mContextView.setupAnimatorToVisibility(8, 100L);
            viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat3;
        }
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = new ViewPropertyAnimatorCompatSet();
        viewPropertyAnimatorCompatSet.playSequentially(viewPropertyAnimatorCompat, viewPropertyAnimatorCompat2);
        viewPropertyAnimatorCompatSet.start();
    }

    public final void dispatchMenuVisibilityChanged(boolean z) {
        if (z == this.mLastMenuVisibility) {
            return;
        }
        this.mLastMenuVisibility = z;
        int size = this.mMenuVisibilityListeners.size();
        for (int i = 0; i < size; i++) {
            this.mMenuVisibilityListeners.get(i).onMenuVisibilityChanged();
        }
    }

    public final void enableContentAnimations(boolean z) {
        this.mContentAnimations = z;
    }

    public final Context getThemedContext() {
        if (this.mThemedContext == null) {
            TypedValue typedValue = new TypedValue();
            this.mContext.getTheme().resolveAttribute(com.samsung.android.biometrics.app.setting.R.attr.actionBarWidgetTheme, typedValue, true);
            int i = typedValue.resourceId;
            if (i != 0) {
                this.mThemedContext = new ContextThemeWrapper(this.mContext, i);
            } else {
                this.mThemedContext = this.mContext;
            }
        }
        return this.mThemedContext;
    }

    public final void hideForSystem() {
        if (this.mHiddenBySystem) {
            return;
        }
        this.mHiddenBySystem = true;
        updateVisibility(true);
    }

    public final void onConfigurationChanged() {
        setHasEmbeddedTabs(ActionBarPolicy.get(this.mContext).hasEmbeddedTabs());
    }

    public final void onContentScrollStarted() {
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = this.mCurrentShowAnim;
        if (viewPropertyAnimatorCompatSet != null) {
            viewPropertyAnimatorCompatSet.cancel();
            this.mCurrentShowAnim = null;
        }
    }

    public final void onWindowVisibilityChanged(int i) {
        this.mCurWindowVisibility = i;
    }

    public final void setDefaultDisplayHomeAsUpEnabled(boolean z) {
        if (this.mDisplayHomeAsUpSet) {
            return;
        }
        int i = z ? 4 : 0;
        int displayOptions = this.mDecorToolbar.getDisplayOptions();
        this.mDisplayHomeAsUpSet = true;
        this.mDecorToolbar.setDisplayOptions((i & 4) | (displayOptions & (-5)));
    }

    public final void setShowHideAnimationEnabled(boolean z) {
        ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet;
        this.mShowHideAnimationEnabled = z;
        if (z || (viewPropertyAnimatorCompatSet = this.mCurrentShowAnim) == null) {
            return;
        }
        viewPropertyAnimatorCompatSet.cancel();
    }

    public final void showForSystem() {
        if (this.mHiddenBySystem) {
            this.mHiddenBySystem = false;
            updateVisibility(true);
        }
    }

    public WindowDecorActionBar(Dialog dialog) {
        new ArrayList();
        this.mMenuVisibilityListeners = new ArrayList<>();
        this.mCurWindowVisibility = 0;
        this.mContentAnimations = true;
        this.mNowShowing = true;
        this.mHideListener = new C00211();
        this.mShowListener = new C00222();
        this.mUpdateListener = new ViewPropertyAnimatorUpdateListener() { // from class: androidx.appcompat.app.WindowDecorActionBar.3
            @Override // androidx.core.view.ViewPropertyAnimatorUpdateListener
            public final void onAnimationUpdate() {
                ((View) WindowDecorActionBar.this.mContainerView.getParent()).invalidate();
            }
        };
        init(dialog.getWindow().getDecorView());
    }
}
