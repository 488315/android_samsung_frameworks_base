package com.android.wm.shell.pip.phone;

import android.app.RemoteAction;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Debug;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import android.util.Size;
import android.view.IWindow;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.R;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ImeListener;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SystemWindows;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import com.android.wm.shell.common.pip.PipMediaController;
import com.android.wm.shell.common.pip.PipMenuController;
import com.android.wm.shell.common.pip.PipUiEventLogger;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.splitscreen.SplitScreenController;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/* loaded from: classes3.dex */
public class PhonePipMenuController implements PipMenuController {
    public List mAppActions;
    public RemoteAction mCloseAction;
    public final Context mContext;
    public boolean mIsImeVisible;
    public int mLastDensityDpi;
    public Locale mLastLocale;
    public SurfaceControl mLeash;
    public final ShellExecutor mMainExecutor;
    public final Handler mMainHandler;
    public List mMediaActions;
    public final PipMediaController mMediaController;
    public int mMenuState;
    public final PipBoundsState mPipBoundsState;
    public PipMenuView mPipMenuView;
    public final PipUiEventLogger mPipUiEventLogger;
    public final Optional mSplitScreenController;
    public final SystemWindows mSystemWindows;
    public final ArrayList mListeners = new ArrayList();
    public boolean mIsPipMenuFocused = false;
    public final AnonymousClass1 mMediaActionListener = new AnonymousClass1();

    /* renamed from: com.android.wm.shell.pip.phone.PhonePipMenuController$1, reason: invalid class name */
    public class AnonymousClass1 implements PipMediaController.ActionListener {
        public AnonymousClass1() {
        }

        @Override // com.android.wm.shell.common.pip.PipMediaController.ActionListener
        public final void onMediaActionsChanged(List list) {
            ArrayList arrayList = new ArrayList(list);
            PhonePipMenuController phonePipMenuController = PhonePipMenuController.this;
            phonePipMenuController.mMediaActions = arrayList;
            phonePipMenuController.updateMenuActions$1();
        }
    }

    public PhonePipMenuController(Context context, PipBoundsState pipBoundsState, PipMediaController pipMediaController, SystemWindows systemWindows, DisplayController displayController, DisplayInsetsController displayInsetsController, PipDisplayLayoutState pipDisplayLayoutState, PipUiEventLogger pipUiEventLogger, ShellExecutor shellExecutor, Handler handler, Optional<SplitScreenController> optional) {
        this.mContext = context;
        this.mPipBoundsState = pipBoundsState;
        this.mMediaController = pipMediaController;
        this.mSystemWindows = systemWindows;
        this.mMainExecutor = shellExecutor;
        this.mMainHandler = handler;
        this.mPipUiEventLogger = pipUiEventLogger;
        this.mSplitScreenController = optional;
        int i = pipDisplayLayoutState.mDisplayId;
        displayInsetsController.addInsetsChangedListener(i, new ImeListener(displayController, i) { // from class: com.android.wm.shell.pip.phone.PhonePipMenuController.2
            @Override // com.android.wm.shell.common.ImeListener
            public final void onImeVisibilityChanged(boolean z, int i2) {
                PhonePipMenuController.this.mIsImeVisible = z;
            }
        });
    }

    @Override // com.android.wm.shell.common.pip.PipMenuController
    public final void attach(SurfaceControl surfaceControl) {
        this.mLeash = surfaceControl;
        attachPipMenuView();
    }

    public final void attachPipMenuView() {
        PipMenuView pipMenuView = this.mPipMenuView;
        SystemWindows systemWindows = this.mSystemWindows;
        if (pipMenuView != null && pipMenuView != null) {
            ((SurfaceControlViewHost) systemWindows.mViewRoots.remove(pipMenuView)).release();
            this.mPipMenuView = null;
        }
        this.mLastDensityDpi = this.mContext.getResources().getConfiguration().densityDpi;
        this.mLastLocale = Locale.getDefault();
        PipMenuView pipMenuView2 = new PipMenuView(this.mContext, this, this.mMainExecutor, this.mMainHandler, this.mPipUiEventLogger, this.mSplitScreenController);
        this.mPipMenuView = pipMenuView2;
        pipMenuView2.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.wm.shell.pip.phone.PhonePipMenuController.3
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                view.getViewRootImpl().addSurfaceChangedCallback(new ViewRootImpl.SurfaceChangedCallback() { // from class: com.android.wm.shell.pip.phone.PhonePipMenuController.3.1
                    public final void surfaceCreated(SurfaceControl.Transaction transaction) {
                        PhonePipMenuController phonePipMenuController = PhonePipMenuController.this;
                        SurfaceControl viewSurface = phonePipMenuController.mSystemWindows.getViewSurface(phonePipMenuController.mPipMenuView);
                        if (viewSurface != null) {
                            transaction.reparent(viewSurface, PhonePipMenuController.this.mLeash);
                            transaction.setLayer(viewSurface, Integer.MAX_VALUE);
                        }
                    }

                    public final void surfaceReplaced(SurfaceControl.Transaction transaction) {
                    }

                    public final void surfaceDestroyed() {
                    }
                });
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
            }
        });
        systemWindows.addView(this.mPipMenuView, PipMenuController.getPipMenuLayoutParams(this.mContext, 0, 0), 0);
        if (this.mMenuState != 0) {
            systemWindows.setShellRootAccessibilityWindow(this.mPipMenuView);
        } else {
            systemWindows.setShellRootAccessibilityWindow(null);
        }
        this.mPipMenuView.setVisibility(8);
        updateMenuActions$1();
    }

    public final boolean checkPipMenuState$1() {
        PipMenuView pipMenuView = this.mPipMenuView;
        if (pipMenuView != null && pipMenuView.getViewRootImpl() != null) {
            return true;
        }
        Log.d("PhonePipMenuController", "Not going to move PiP, either menu or its parent is not created");
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1781819382020650963L, 0, "PhonePipMenuController");
        }
        return false;
    }

    @Override // com.android.wm.shell.common.pip.PipMenuController
    public final void detach() {
        hideMenu();
        PipMenuView pipMenuView = this.mPipMenuView;
        SystemWindows systemWindows = this.mSystemWindows;
        if (pipMenuView != null) {
            if (this.mIsPipMenuFocused) {
                Log.i("PhonePipMenuController", "clearWindowFocus()");
                this.mIsPipMenuFocused = false;
                try {
                    WindowManagerGlobal.getWindowSession().grantEmbeddedWindowFocus((IWindow) null, systemWindows.getFocusGrantToken(this.mPipMenuView), false);
                } catch (RemoteException e) {
                    Log.e("PhonePipMenuController", "Unable to update focus", e);
                }
            }
            List list = this.mAppActions;
            if (list != null && list.size() > 0) {
                this.mAppActions.clear();
            }
        }
        PipMenuView pipMenuView2 = this.mPipMenuView;
        if (pipMenuView2 != null) {
            ((SurfaceControlViewHost) systemWindows.mViewRoots.remove(pipMenuView2)).release();
            this.mPipMenuView = null;
        }
        this.mLeash = null;
    }

    public final Size getEstimatedMinMenuSize() throws Resources.NotFoundException {
        PipMenuView pipMenuView = this.mPipMenuView;
        if (pipMenuView == null) {
            return null;
        }
        return new Size(Math.max(Math.max(2, ((ArrayList) pipMenuView.mActions).size()) * pipMenuView.getResources().getDimensionPixelSize(R.dimen.pip_action_size), pipMenuView.getResources().getDimensionPixelSize(R.dimen.pip_min_width)), pipMenuView.getResources().getDimensionPixelSize(R.dimen.pip_expand_container_edge_margin) + pipMenuView.getResources().getDimensionPixelSize(R.dimen.pip_action_padding) + pipMenuView.getResources().getDimensionPixelSize(R.dimen.pip_expand_action_size));
    }

    public final void hideMenu() {
        if (isMenuVisible()) {
            this.mPipMenuView.hideMenu$1();
        }
    }

    @Override // com.android.wm.shell.common.pip.PipMenuController
    public final boolean isMenuVisible() {
        return (this.mPipMenuView == null || this.mMenuState == 0) ? false : true;
    }

    @Override // com.android.wm.shell.common.pip.PipMenuController
    public final void movePipMenu(Rect rect, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        if (rect.isEmpty() || !checkPipMenuState$1() || surfaceControl == null || transaction == null) {
            return;
        }
        transaction.apply();
    }

    public final void pokeMenu() {
        boolean zIsMenuVisible = isMenuVisible();
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 6426659562702778548L, 12, "PhonePipMenuController", Boolean.valueOf(zIsMenuVisible));
        }
        if (zIsMenuVisible) {
            PipMenuView pipMenuView = this.mPipMenuView;
            ((HandlerExecutor) pipMenuView.mMainExecutor).removeCallbacks(pipMenuView.mHideMenuRunnable);
        }
    }

    @Override // com.android.wm.shell.common.pip.PipMenuController
    public final void resizePipMenu(Rect rect, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        if (rect.isEmpty() || !checkPipMenuState$1() || surfaceControl == null) {
            return;
        }
        transaction.apply();
    }

    @Override // com.android.wm.shell.common.pip.PipMenuController
    public final void setSplitMenuEnabled(boolean z) {
        View view;
        PipMenuView pipMenuView = this.mPipMenuView;
        if (pipMenuView == null || (view = pipMenuView.mEnterSplitButton) == null) {
            return;
        }
        view.setEnabled(z);
        this.mPipMenuView.updateEnterSplitButtonIcon();
    }

    public final void showMenuInternal(Rect rect, boolean z, boolean z2, boolean z3) {
        Log.d("PhonePipMenuController", "showMenu() state=1 isMenuVisible=" + isMenuVisible() + " allowMenuTimeout=true willResizeMenu=" + z + " withDelay=" + z2 + " showResizeHandle=false stackBounds=" + rect + " callers=\n" + Debug.getCallers(5, "    "));
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -5370125133500631917L, 0, "PhonePipMenuController", String.valueOf(1), String.valueOf(isMenuVisible()), String.valueOf(true), String.valueOf(z), String.valueOf(z2), String.valueOf(false), String.valueOf(Debug.getCallers(5, "    ")));
        }
        if (checkPipMenuState$1()) {
            if (this.mPipMenuView.mIsExpanding) {
                Log.d("PhonePipMenuController", "showMenuInternal: skip, reason=expanding");
                return;
            }
            PipBoundsState pipBoundsState = this.mPipBoundsState;
            if (pipBoundsState.isStashed()) {
                Log.d("PhonePipMenuController", "showMenuInternal: skip, reason=stashed");
            } else {
                if (pipBoundsState.mMotionBoundsState.isInMotion()) {
                    Log.d("PhonePipMenuController", "showMenuInternal: skip, reason=in_motion");
                    return;
                }
                movePipMenu(rect, null, null);
                updateMenuBounds(rect);
                this.mPipMenuView.showMenu(rect, z, z2, z3);
            }
        }
    }

    public final void showMenuWithPossibleDelay(Rect rect, boolean z, boolean z2) {
        if (z) {
            boolean zIsMenuVisible = isMenuVisible();
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1487408910745847354L, 12, "PhonePipMenuController", Boolean.valueOf(zIsMenuVisible));
            }
            if (zIsMenuVisible) {
                PipMenuView pipMenuView = this.mPipMenuView;
                pipMenuView.mMenuContainer.setAlpha(0.0f);
                pipMenuView.mSettingsButton.setAlpha(0.0f);
                pipMenuView.mDismissButton.setAlpha(0.0f);
                View view = pipMenuView.mEnterSplitButton;
                if (view != null) {
                    view.setAlpha(0.0f);
                }
                pipMenuView.mExpandButton.setAlpha(0.0f);
            }
        }
        showMenuInternal(rect, z, z, z2);
    }

    public final void updateMenuActions$1() {
        PipMenuView pipMenuView = this.mPipMenuView;
        if (pipMenuView != null) {
            Rect bounds = this.mPipBoundsState.getBounds();
            List list = this.mAppActions;
            pipMenuView.setActions(bounds, (list == null || list.size() <= 0) ? this.mMediaActions : this.mAppActions, this.mCloseAction);
        }
    }

    @Override // com.android.wm.shell.common.pip.PipMenuController
    public final void updateMenuBounds(Rect rect) {
        PipMenuView pipMenuView = this.mPipMenuView;
        WindowManager.LayoutParams pipMenuLayoutParams = PipMenuController.getPipMenuLayoutParams(this.mContext, rect.width(), rect.height());
        SurfaceControlViewHost surfaceControlViewHost = (SurfaceControlViewHost) this.mSystemWindows.mViewRoots.get(pipMenuView);
        if (surfaceControlViewHost != null) {
            pipMenuView.setLayoutParams(pipMenuLayoutParams);
            surfaceControlViewHost.relayout(pipMenuLayoutParams);
        }
        if (isMenuVisible()) {
            this.mPipMenuView.mPipMenuIconsAlgorithm.getClass();
        }
    }

    public final void hideMenu(int i) {
        boolean zIsMenuVisible = isMenuVisible();
        Log.d("PhonePipMenuController", "hideMenu() state=" + this.mMenuState + " isMenuVisible=" + zIsMenuVisible + " animationType=" + i + " resize=false callers=\n" + Debug.getCallers(5, "    "));
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -5026655734799582529L, 0, "PhonePipMenuController", String.valueOf(this.mMenuState), String.valueOf(zIsMenuVisible), String.valueOf(i), String.valueOf(false), String.valueOf(Debug.getCallers(5, "    ")));
        }
        if (zIsMenuVisible) {
            this.mPipMenuView.hideMenu(null, true, false, i);
        }
    }
}
