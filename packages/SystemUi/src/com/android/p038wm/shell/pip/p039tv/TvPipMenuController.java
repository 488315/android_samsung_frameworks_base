package com.android.p038wm.shell.pip.p039tv;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Insets;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.view.IWindow;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.animation.Interpolator;
import android.window.SurfaceSyncGroup;
import com.android.internal.widget.RecyclerView;
import com.android.p038wm.shell.common.SystemWindows;
import com.android.p038wm.shell.pip.PipMenuController;
import com.android.p038wm.shell.pip.p039tv.TvPipMenuView;
import com.android.p038wm.shell.protolog.ShellProtoLogCache;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.protolog.ShellProtoLogImpl;
import com.android.systemui.R;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TvPipMenuController implements PipMenuController, TvPipMenuView.Listener {
    public final Context mContext;
    public Delegate mDelegate;
    public SurfaceControl mLeash;
    public final Handler mMainHandler;
    public boolean mMenuIsFocused;
    public TvPipBackgroundView mPipBackgroundView;
    public TvPipMenuView mPipMenuView;
    public final SystemWindows mSystemWindows;
    public TvPipActionsProvider mTvPipActionsProvider;
    public final TvPipBoundsState mTvPipBoundsState;
    public int mCurrentMenuMode = 0;
    public int mPrevMenuMode = 0;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Delegate {
    }

    public TvPipMenuController(Context context, TvPipBoundsState tvPipBoundsState, SystemWindows systemWindows, Handler handler) {
        this.mContext = context;
        this.mTvPipBoundsState = tvPipBoundsState;
        this.mSystemWindows = systemWindows;
        this.mMainHandler = handler;
        context.registerReceiverForAllUsers(new BroadcastReceiver() { // from class: com.android.wm.shell.pip.tv.TvPipMenuController.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                TvPipMenuController.this.closeMenu();
            }
        }, new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"), null, handler, 2);
    }

    public static String getMenuModeString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? "Unknown" : "MODE_ALL_ACTIONS_MENU" : "MODE_MOVE_MENU" : "MODE_NO_MENU";
    }

    @Override // com.android.p038wm.shell.pip.PipMenuController
    public final void attach(SurfaceControl surfaceControl) {
        if (this.mDelegate == null) {
            throw new IllegalStateException("Delegate is not set.");
        }
        this.mLeash = surfaceControl;
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1946363811, 0, "%s: attachPipMenu()", "TvPipMenuController");
        }
        TvPipMenuView tvPipMenuView = this.mPipMenuView;
        SystemWindows systemWindows = this.mSystemWindows;
        if (tvPipMenuView != null) {
            if (tvPipMenuView != null) {
                ((SurfaceControlViewHost) systemWindows.mViewRoots.remove(tvPipMenuView)).release();
                this.mPipMenuView = null;
            }
            TvPipBackgroundView tvPipBackgroundView = this.mPipBackgroundView;
            if (tvPipBackgroundView != null) {
                ((SurfaceControlViewHost) systemWindows.mViewRoots.remove(tvPipBackgroundView)).release();
                this.mPipBackgroundView = null;
            }
        }
        TvPipBackgroundView createTvPipBackgroundView = createTvPipBackgroundView();
        this.mPipBackgroundView = createTvPipBackgroundView;
        final int i = -1;
        createTvPipBackgroundView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.wm.shell.pip.tv.TvPipMenuController.2
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                view.getViewRootImpl().addSurfaceChangedCallback(TvPipMenuController.this.new PipMenuSurfaceChangedCallback(view, i));
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
            }
        });
        TvPipBackgroundView tvPipBackgroundView2 = this.mPipBackgroundView;
        Context context = this.mContext;
        WindowManager.LayoutParams pipMenuLayoutParams = PipMenuController.getPipMenuLayoutParams(context, 0, 0, "PipBackgroundView");
        pipMenuLayoutParams.alpha = 0.0f;
        final int i2 = 1;
        systemWindows.addView(tvPipBackgroundView2, pipMenuLayoutParams, 1);
        if (this.mTvPipActionsProvider != null) {
            TvPipMenuView createTvPipMenuView = createTvPipMenuView();
            this.mPipMenuView = createTvPipMenuView;
            createTvPipMenuView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.wm.shell.pip.tv.TvPipMenuController.2
                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewAttachedToWindow(View view) {
                    view.getViewRootImpl().addSurfaceChangedCallback(TvPipMenuController.this.new PipMenuSurfaceChangedCallback(view, i2));
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewDetachedFromWindow(View view) {
                }
            });
            TvPipMenuView tvPipMenuView2 = this.mPipMenuView;
            WindowManager.LayoutParams pipMenuLayoutParams2 = PipMenuController.getPipMenuLayoutParams(context, 0, 0, "PipMenuView");
            pipMenuLayoutParams2.alpha = 0.0f;
            systemWindows.addView(tvPipMenuView2, pipMenuLayoutParams2, 1);
        } else if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m230e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 79218209, 0, "%s: Actions provider is not set", "TvPipMenuController");
        }
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.pip_menu_edu_text_view_height);
        int i3 = -context.getResources().getDimensionPixelSize(R.dimen.pip_menu_border_width);
        Insets of = Insets.of(i3, i3, i3, i3);
        TvPipBoundsState tvPipBoundsState = this.mTvPipBoundsState;
        tvPipBoundsState.mPipMenuPermanentDecorInsets = of;
        tvPipBoundsState.mPipMenuTemporaryDecorInsets = Insets.of(0, 0, 0, -dimensionPixelSize);
    }

    public final Rect calculateMenuSurfaceBounds(Rect rect) {
        TvPipMenuView tvPipMenuView = this.mPipMenuView;
        tvPipMenuView.getClass();
        Rect rect2 = new Rect(rect);
        int i = -tvPipMenuView.mPipMenuOuterSpace;
        rect2.inset(i, i);
        int i2 = rect2.bottom;
        TvPipMenuEduTextDrawer tvPipMenuEduTextDrawer = tvPipMenuView.mEduTextDrawer;
        rect2.bottom = i2 + (tvPipMenuEduTextDrawer.getVisibility() == 8 ? 0 : tvPipMenuEduTextDrawer.getHeight());
        return rect2;
    }

    public final void closeMenu() {
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 917920067, 0, "%s: closeMenu()", "TvPipMenuController");
        }
        switchToMenuMode(0, false);
    }

    public TvPipBackgroundView createTvPipBackgroundView() {
        return new TvPipBackgroundView(this.mContext);
    }

    public TvPipMenuView createTvPipMenuView() {
        return new TvPipMenuView(this.mContext, this.mMainHandler, this, this.mTvPipActionsProvider);
    }

    @Override // com.android.p038wm.shell.pip.PipMenuController
    public final void detach() {
        closeMenu();
        TvPipMenuView tvPipMenuView = this.mPipMenuView;
        SystemWindows systemWindows = this.mSystemWindows;
        if (tvPipMenuView != null) {
            ((SurfaceControlViewHost) systemWindows.mViewRoots.remove(tvPipMenuView)).release();
            this.mPipMenuView = null;
        }
        TvPipBackgroundView tvPipBackgroundView = this.mPipBackgroundView;
        if (tvPipBackgroundView != null) {
            ((SurfaceControlViewHost) systemWindows.mViewRoots.remove(tvPipBackgroundView)).release();
            this.mPipBackgroundView = null;
        }
        this.mLeash = null;
    }

    public boolean isInAllActionsMode() {
        return this.mCurrentMenuMode == 2;
    }

    public boolean isInMoveMode() {
        return this.mCurrentMenuMode == 1;
    }

    public final boolean isMenuAttached() {
        TvPipBackgroundView tvPipBackgroundView;
        TvPipMenuView tvPipMenuView = this.mPipMenuView;
        boolean z = (tvPipMenuView == null || tvPipMenuView.getViewRootImpl() == null || (tvPipBackgroundView = this.mPipBackgroundView) == null || tvPipBackgroundView.getViewRootImpl() == null) ? false : true;
        if (!z && ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1532239579, 0, "%s: the menu surfaces are not attached.", "TvPipMenuController");
        }
        return z;
    }

    public boolean isMenuOpen() {
        return this.mCurrentMenuMode != 0;
    }

    @Override // com.android.p038wm.shell.pip.PipMenuController
    public final boolean isMenuVisible() {
        return true;
    }

    @Override // com.android.p038wm.shell.pip.PipMenuController
    public final void movePipMenu(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, Rect rect, float f) {
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1653713784, 0, "%s: movePipMenu: %s, alpha %s", "TvPipMenuController", String.valueOf(rect.toShortString()), String.valueOf(f));
        }
        if (rect.isEmpty()) {
            if (transaction == null && ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1869408059, 0, "%s: no transaction given", "TvPipMenuController");
                return;
            }
            return;
        }
        if (isMenuAttached()) {
            TvPipMenuView tvPipMenuView = this.mPipMenuView;
            SystemWindows systemWindows = this.mSystemWindows;
            SurfaceControl viewSurface = systemWindows.getViewSurface(tvPipMenuView);
            SurfaceControl viewSurface2 = systemWindows.getViewSurface(this.mPipBackgroundView);
            Rect calculateMenuSurfaceBounds = calculateMenuSurfaceBounds(rect);
            if (transaction == null) {
                transaction = new SurfaceControl.Transaction();
            }
            transaction.setPosition(viewSurface, calculateMenuSurfaceBounds.left, calculateMenuSurfaceBounds.top);
            transaction.setPosition(viewSurface2, calculateMenuSurfaceBounds.left, calculateMenuSurfaceBounds.top);
            if (f != -1.0f) {
                transaction.setAlpha(viewSurface, f);
                transaction.setAlpha(viewSurface2, f);
            }
            SurfaceSyncGroup surfaceSyncGroup = new SurfaceSyncGroup("TvPip");
            surfaceSyncGroup.add(this.mPipMenuView.getRootSurfaceControl(), (Runnable) null);
            surfaceSyncGroup.add(this.mPipBackgroundView.getRootSurfaceControl(), (Runnable) null);
            updateMenuBounds(rect);
            surfaceSyncGroup.addTransaction(transaction);
            surfaceSyncGroup.markSyncReady();
        }
    }

    public final boolean onExitMoveMode() {
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 335161156, 0, "%s: onExitMoveMode - mCurrentMenuMode=%s", "TvPipMenuController", String.valueOf(getMenuModeString()));
        }
        int i = this.mCurrentMenuMode;
        if (isInMoveMode()) {
            switchToMenuMode(this.mPrevMenuMode, false);
        }
        return i == 1;
    }

    public final boolean onPipMovement(int i) {
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -488642852, 0, "%s: onPipMovement - mCurrentMenuMode=%s", "TvPipMenuController", String.valueOf(getMenuModeString()));
        }
        if (isInMoveMode()) {
            ((TvPipController) this.mDelegate).movePip(i);
        }
        return isInMoveMode();
    }

    @Override // com.android.p038wm.shell.pip.PipMenuController
    public final void resizePipMenu(Rect rect, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 835205025, 0, "%s: resizePipMenu: %s", "TvPipMenuController", String.valueOf(rect.toShortString()));
        }
        if (!rect.isEmpty() && isMenuAttached()) {
            TvPipMenuView tvPipMenuView = this.mPipMenuView;
            SystemWindows systemWindows = this.mSystemWindows;
            SurfaceControl viewSurface = systemWindows.getViewSurface(tvPipMenuView);
            SurfaceControl viewSurface2 = systemWindows.getViewSurface(this.mPipBackgroundView);
            Rect calculateMenuSurfaceBounds = calculateMenuSurfaceBounds(rect);
            transaction.setWindowCrop(viewSurface, calculateMenuSurfaceBounds.width(), calculateMenuSurfaceBounds.height());
            transaction.setWindowCrop(viewSurface2, calculateMenuSurfaceBounds.width(), calculateMenuSurfaceBounds.height());
            SurfaceSyncGroup surfaceSyncGroup = new SurfaceSyncGroup("TvPip");
            surfaceSyncGroup.add(this.mPipMenuView.getRootSurfaceControl(), (Runnable) null);
            surfaceSyncGroup.add(this.mPipBackgroundView.getRootSurfaceControl(), (Runnable) null);
            updateMenuBounds(rect);
            surfaceSyncGroup.addTransaction(transaction);
            surfaceSyncGroup.markSyncReady();
        }
    }

    public final void switchToMenuMode(int i, boolean z) {
        this.mPrevMenuMode = this.mCurrentMenuMode;
        this.mCurrentMenuMode = i;
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m231i(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1749640165, "%s: switchToMenuMode: setting mCurrentMenuMode=%s, mPrevMenuMode=%s", "TvPipMenuController", String.valueOf(getMenuModeString()), getMenuModeString(this.mPrevMenuMode));
        }
        TvPipMenuView tvPipMenuView = this.mPipMenuView;
        if (tvPipMenuView != null && this.mPipBackgroundView != null) {
            tvPipMenuView.mCurrentPipGravity = this.mTvPipBoundsState.mTvPipGravity;
            if (tvPipMenuView.mCurrentMenuMode == 1) {
                tvPipMenuView.showMovementHints();
            }
            final TvPipMenuView tvPipMenuView2 = this.mPipMenuView;
            int i2 = this.mCurrentMenuMode;
            tvPipMenuView2.getClass();
            if (i2 == 0) {
                if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                    ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 2076292245, 0, "%s: hideAllUserControls()", "TvPipMenuView");
                }
                if (tvPipMenuView2.mCurrentMenuMode != 0) {
                    tvPipMenuView2.setMenuButtonsVisible(false);
                    tvPipMenuView2.hideMovementHints();
                    tvPipMenuView2.mMenuFrameView.setActivated(false);
                    tvPipMenuView2.animateAlphaTo(tvPipMenuView2.mDimLayer, 0.0f);
                }
            } else if (i2 == 1) {
                if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                    ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1351326290, 0, "%s: showMoveMenu()", "TvPipMenuView");
                }
                if (tvPipMenuView2.mCurrentMenuMode != 1) {
                    tvPipMenuView2.showMovementHints();
                    tvPipMenuView2.setMenuButtonsVisible(false);
                    tvPipMenuView2.mMenuFrameView.setActivated(true);
                    tvPipMenuView2.animateAlphaTo(tvPipMenuView2.mDimLayer, tvPipMenuView2.mA11yManager.isEnabled() ? 1.0f : 0.0f);
                    tvPipMenuView2.mEduTextDrawer.closeIfNeeded();
                }
            } else {
                if (i2 != 2) {
                    throw new IllegalArgumentException("Unknown TV PiP menu mode: ".concat(getMenuModeString(tvPipMenuView2.mCurrentMenuMode)));
                }
                if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                    ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1683724188, 12, "%s: showAllActionsMenu(), resetMenu %b", "TvPipMenuView", Boolean.valueOf(z));
                }
                if (z) {
                    View focusedChild = tvPipMenuView2.mActionButtonsRecyclerView.getFocusedChild();
                    if (focusedChild != null) {
                        focusedChild.clearFocus();
                    }
                    tvPipMenuView2.mButtonLayoutManager.scrollToPosition(0);
                    tvPipMenuView2.mActionButtonsRecyclerView.post(new Runnable() { // from class: com.android.wm.shell.pip.tv.TvPipMenuView$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            TvPipMenuView.this.refocusButton(0);
                        }
                    });
                }
                if (tvPipMenuView2.mCurrentMenuMode != 2) {
                    tvPipMenuView2.setMenuButtonsVisible(true);
                    tvPipMenuView2.hideMovementHints();
                    tvPipMenuView2.mMenuFrameView.setActivated(true);
                    tvPipMenuView2.animateAlphaTo(tvPipMenuView2.mDimLayer, 1.0f);
                    tvPipMenuView2.mEduTextDrawer.closeIfNeeded();
                    if (tvPipMenuView2.mCurrentMenuMode == 1) {
                        TvPipActionsProvider tvPipActionsProvider = tvPipMenuView2.mTvPipActionsProvider;
                        int i3 = 0;
                        while (true) {
                            ArrayList arrayList = (ArrayList) tvPipActionsProvider.mActionsList;
                            if (i3 >= arrayList.size()) {
                                i3 = -1;
                                break;
                            } else if (((TvPipAction) arrayList.get(i3)).mActionType == 2) {
                                break;
                            } else {
                                i3++;
                            }
                        }
                        tvPipMenuView2.refocusButton(i3);
                    }
                }
            }
            tvPipMenuView2.mCurrentMenuMode = i2;
            TvPipBackgroundView tvPipBackgroundView = this.mPipBackgroundView;
            int i4 = this.mCurrentMenuMode;
            tvPipBackgroundView.getClass();
            if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1967192097, 0, "%s: transitionToMenuMode(), old menu mode = %s, new menu mode = %s", "TvPipBackgroundView", getMenuModeString(tvPipBackgroundView.mCurrentMenuMode), getMenuModeString(i4));
            }
            int i5 = tvPipBackgroundView.mCurrentMenuMode;
            if (i5 != i4) {
                int i6 = tvPipBackgroundView.mElevationNoMenu;
                Interpolator interpolator = TvPipInterpolators.ENTER;
                if (i4 == 0) {
                    interpolator = TvPipInterpolators.EXIT;
                } else if (i4 == 1) {
                    i6 = tvPipBackgroundView.mElevationMoveMenu;
                } else {
                    if (i4 != 2) {
                        throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Unknown TV PiP menu mode: ", i4));
                    }
                    i6 = tvPipBackgroundView.mElevationAllActionsMenu;
                    if (i5 == 1) {
                        interpolator = TvPipInterpolators.EXIT;
                    }
                }
                tvPipBackgroundView.mBackgroundView.animate().translationZ(i6).setInterpolator(interpolator).setDuration(tvPipBackgroundView.mPipMenuFadeAnimationDuration).start();
                tvPipBackgroundView.mCurrentMenuMode = i4;
            }
            boolean z2 = this.mCurrentMenuMode != 0;
            if (this.mMenuIsFocused != z2) {
                if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                    ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 2108850553, 12, "%s: grantWindowFocus(%b)", "TvPipMenuController", Boolean.valueOf(z2));
                }
                try {
                    WindowManagerGlobal.getWindowSession().grantEmbeddedWindowFocus((IWindow) null, this.mSystemWindows.getFocusGrantToken(this.mPipMenuView), z2);
                } catch (Exception e) {
                    if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                        ShellProtoLogImpl.m230e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -245959633, 0, "%s: Unable to update focus, %s", "TvPipMenuController", String.valueOf(e));
                    }
                }
            }
        }
        int i7 = this.mPrevMenuMode;
        if (i7 == this.mCurrentMenuMode || this.mDelegate == null) {
            return;
        }
        if (i7 == 1 || isInMoveMode()) {
            ((TvPipController) this.mDelegate).updatePinnedStackBounds();
        }
        if (this.mCurrentMenuMode == 0) {
            TvPipController tvPipController = (TvPipController) this.mDelegate;
            tvPipController.getClass();
            if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 171349657, 0, "%s: closeMenu(), state before=%s", "TvPipController", TvPipController.stateToName(tvPipController.mState));
            }
            tvPipController.setState(1);
            tvPipController.updatePinnedStackBounds();
        }
    }

    @Override // com.android.p038wm.shell.pip.PipMenuController
    public final void updateMenuBounds(Rect rect) {
        ViewGroup.LayoutParams layoutParams;
        if (isMenuAttached()) {
            Rect calculateMenuSurfaceBounds = calculateMenuSurfaceBounds(rect);
            if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 857925722, 0, "%s: updateMenuBounds: %s", "TvPipMenuController", String.valueOf(calculateMenuSurfaceBounds.toShortString()));
            }
            TvPipBackgroundView tvPipBackgroundView = this.mPipBackgroundView;
            int width = calculateMenuSurfaceBounds.width();
            int height = calculateMenuSurfaceBounds.height();
            Context context = this.mContext;
            WindowManager.LayoutParams pipMenuLayoutParams = PipMenuController.getPipMenuLayoutParams(context, width, height, "PipBackgroundView");
            SystemWindows systemWindows = this.mSystemWindows;
            systemWindows.updateViewLayout(tvPipBackgroundView, pipMenuLayoutParams);
            systemWindows.updateViewLayout(this.mPipMenuView, PipMenuController.getPipMenuLayoutParams(context, calculateMenuSurfaceBounds.width(), calculateMenuSurfaceBounds.height(), "PipMenuView"));
            TvPipMenuView tvPipMenuView = this.mPipMenuView;
            if (tvPipMenuView != null) {
                if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                    ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -408772810, 0, "%s: updateLayout, width: %s, height: %s", "TvPipMenuView", String.valueOf(rect.width()), String.valueOf(rect.height()));
                }
                if (rect.equals(tvPipMenuView.mCurrentPipBounds)) {
                    return;
                }
                tvPipMenuView.mCurrentPipBounds.set(rect);
                if (tvPipMenuView.mPipFrameView.getVisibility() == 0 && (layoutParams = tvPipMenuView.mPipFrameView.getLayoutParams()) != null) {
                    layoutParams.width = (tvPipMenuView.mPipMenuBorderWidth * 2) + tvPipMenuView.mCurrentPipBounds.width();
                    layoutParams.height = (tvPipMenuView.mPipMenuBorderWidth * 2) + tvPipMenuView.mCurrentPipBounds.height();
                    tvPipMenuView.mPipFrameView.setLayoutParams(layoutParams);
                }
                ViewGroup.LayoutParams layoutParams2 = tvPipMenuView.mPipView.getLayoutParams();
                if (layoutParams2 != null) {
                    layoutParams2.width = tvPipMenuView.mCurrentPipBounds.width();
                    layoutParams2.height = tvPipMenuView.mCurrentPipBounds.height();
                    tvPipMenuView.mPipView.setLayoutParams(layoutParams2);
                }
                View focusedChild = tvPipMenuView.mActionButtonsRecyclerView.getFocusedChild();
                if (focusedChild != null) {
                    RecyclerView recyclerView = tvPipMenuView.mActionButtonsRecyclerView;
                    recyclerView.scrollToPosition(recyclerView.getChildLayoutPosition(focusedChild));
                }
            }
        }
    }

    public String getMenuModeString() {
        return getMenuModeString(this.mCurrentMenuMode);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PipMenuSurfaceChangedCallback implements ViewRootImpl.SurfaceChangedCallback {
        public final View mView;
        public final int mZOrder;

        public PipMenuSurfaceChangedCallback(View view, int i) {
            this.mView = view;
            this.mZOrder = i;
        }

        public final void surfaceCreated(SurfaceControl.Transaction transaction) {
            TvPipMenuController tvPipMenuController = TvPipMenuController.this;
            SurfaceControl viewSurface = tvPipMenuController.mSystemWindows.getViewSurface(this.mView);
            if (viewSurface != null) {
                transaction.setRelativeLayer(viewSurface, TvPipMenuController.this.mLeash, this.mZOrder);
            }
        }

        public final void surfaceReplaced(SurfaceControl.Transaction transaction) {
        }

        public final void surfaceDestroyed() {
        }
    }
}
