package com.android.wm.shell.pip.phone;

import android.app.ActivityManager;
import android.app.RemoteAction;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Debug;
import android.os.Handler;
import android.os.RemoteException;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.util.Size;
import android.view.IWindow;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.WindowManagerGlobal;
import com.android.keyguard.KeyguardFaceListenModel$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SystemWindows;
import com.android.wm.shell.pip.PipBoundsState;
import com.android.wm.shell.pip.PipMediaController;
import com.android.wm.shell.pip.PipMenuController;
import com.android.wm.shell.pip.PipSurfaceTransactionHelper;
import com.android.wm.shell.pip.PipUiEventLogger;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PhonePipMenuController implements PipMenuController {
    public List mAppActions;
    public RemoteAction mCloseAction;
    public final Context mContext;
    public boolean mIsPipMenuFocused;
    public int mLastDensityDpi;
    public Locale mLastLocale;
    public SurfaceControl mLeash;
    public final ArrayList mListeners;
    public final ShellExecutor mMainExecutor;
    public final Handler mMainHandler;
    public final C40481 mMediaActionListener;
    public List mMediaActions;
    public final PipMediaController mMediaController;
    public int mMenuState;
    public final PipBoundsState mPipBoundsState;
    public PipMenuView mPipMenuView;
    public final PipUiEventLogger mPipUiEventLogger;
    public final Optional mSplitScreenController;
    public final SystemWindows mSystemWindows;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.pip.phone.PhonePipMenuController$1 */
    public final class C40481 implements PipMediaController.ActionListener {
        public C40481() {
        }

        @Override // com.android.wm.shell.pip.PipMediaController.ActionListener
        public final void onMediaActionsChanged(List list) {
            ArrayList arrayList = new ArrayList(list);
            PhonePipMenuController phonePipMenuController = PhonePipMenuController.this;
            phonePipMenuController.mMediaActions = arrayList;
            phonePipMenuController.updateMenuActions();
        }
    }

    public PhonePipMenuController(Context context, PipBoundsState pipBoundsState, PipMediaController pipMediaController, SystemWindows systemWindows, Optional<SplitScreenController> optional, PipUiEventLogger pipUiEventLogger, ShellExecutor shellExecutor, Handler handler) {
        new Matrix();
        new Rect();
        new RectF();
        new RectF();
        this.mListeners = new ArrayList();
        this.mIsPipMenuFocused = false;
        this.mMediaActionListener = new C40481();
        this.mContext = context;
        this.mPipBoundsState = pipBoundsState;
        this.mMediaController = pipMediaController;
        this.mSystemWindows = systemWindows;
        this.mMainExecutor = shellExecutor;
        this.mMainHandler = handler;
        this.mSplitScreenController = optional;
        this.mPipUiEventLogger = pipUiEventLogger;
        new PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory();
    }

    @Override // com.android.wm.shell.pip.PipMenuController
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
        Context context = this.mContext;
        this.mLastDensityDpi = context.getResources().getConfiguration().densityDpi;
        this.mLastLocale = Locale.getDefault();
        PipMenuView pipMenuView2 = new PipMenuView(this.mContext, this, this.mMainExecutor, this.mMainHandler, this.mSplitScreenController, this.mPipUiEventLogger);
        this.mPipMenuView = pipMenuView2;
        pipMenuView2.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.wm.shell.pip.phone.PhonePipMenuController.2
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                view.getViewRootImpl().addSurfaceChangedCallback(new ViewRootImpl.SurfaceChangedCallback() { // from class: com.android.wm.shell.pip.phone.PhonePipMenuController.2.1
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
        systemWindows.addView(this.mPipMenuView, PipMenuController.getPipMenuLayoutParams(context, 0, 0, "PipMenuView"), 1);
        if (this.mMenuState != 0) {
            PipMenuView pipMenuView3 = this.mPipMenuView;
            SystemWindows.PerDisplay perDisplay = (SystemWindows.PerDisplay) systemWindows.mPerDisplay.get(0);
            if (perDisplay != null) {
                perDisplay.setShellRootAccessibilityWindow(pipMenuView3, 1);
            }
        } else {
            SystemWindows.PerDisplay perDisplay2 = (SystemWindows.PerDisplay) systemWindows.mPerDisplay.get(0);
            if (perDisplay2 != null) {
                perDisplay2.setShellRootAccessibilityWindow(null, 1);
            }
        }
        this.mPipMenuView.setVisibility(8);
        updateMenuActions();
    }

    public final boolean checkPipMenuState() {
        PipMenuView pipMenuView = this.mPipMenuView;
        if (pipMenuView != null && pipMenuView.getViewRootImpl() != null) {
            return true;
        }
        Log.d("PhonePipMenuController", "Not going to move PiP, either menu or its parent is not created");
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -101893684, 0, "%s: Not going to move PiP, either menu or its parent is not created.", "PhonePipMenuController");
        }
        return false;
    }

    @Override // com.android.wm.shell.pip.PipMenuController
    public final void detach() {
        hideMenu();
        PipMenuView pipMenuView = this.mPipMenuView;
        SystemWindows systemWindows = this.mSystemWindows;
        if (pipMenuView != null) {
            boolean z = false;
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
                z = true;
            }
            if (z) {
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

    @Override // com.android.wm.shell.pip.PipMenuController
    public final void dismissPip() {
        this.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda1(0));
    }

    public final Size getEstimatedMinMenuSize() {
        PipMenuView pipMenuView = this.mPipMenuView;
        if (pipMenuView == null) {
            return null;
        }
        return new Size(Math.max(Math.max(2, pipMenuView.mActions.size()) * pipMenuView.getResources().getDimensionPixelSize(R.dimen.pip_action_size), pipMenuView.getResources().getDimensionPixelSize(R.dimen.pip_min_width)), pipMenuView.getResources().getDimensionPixelSize(R.dimen.pip_expand_container_edge_margin) + pipMenuView.getResources().getDimensionPixelSize(R.dimen.pip_action_padding) + pipMenuView.getResources().getDimensionPixelSize(R.dimen.pip_expand_action_size));
    }

    public final void hideMenu() {
        if (isMenuVisible()) {
            this.mPipMenuView.hideMenu$1();
        }
    }

    @Override // com.android.wm.shell.pip.PipMenuController
    public final boolean isMenuVisible() {
        return (this.mPipMenuView == null || this.mMenuState == 0) ? false : true;
    }

    @Override // com.android.wm.shell.pip.PipMenuController
    public final void movePipMenu(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, Rect rect, float f) {
        if (rect.isEmpty() || !checkPipMenuState() || surfaceControl == null || transaction == null) {
            return;
        }
        transaction.apply();
    }

    @Override // com.android.wm.shell.pip.PipMenuController
    public final void onFocusTaskChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
        boolean z;
        PipMenuView pipMenuView = this.mPipMenuView;
        if (pipMenuView != null) {
            boolean z2 = true;
            if (pipMenuView.mSplitScreenControllerOptional.isPresent()) {
                SplitScreenController splitScreenController = (SplitScreenController) pipMenuView.mSplitScreenControllerOptional.get();
                if (splitScreenController.isTaskInSplitScreen(runningTaskInfo.taskId) && splitScreenController.isSplitScreenVisible()) {
                    z = true;
                    if (!z && !runningTaskInfo.supportsMultiWindow) {
                        z2 = false;
                    }
                    pipMenuView.mFocusedTaskAllowSplitScreen = z2;
                }
            }
            z = false;
            if (!z) {
                z2 = false;
            }
            pipMenuView.mFocusedTaskAllowSplitScreen = z2;
        }
    }

    public final void pokeMenu() {
        boolean isMenuVisible = isMenuVisible();
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1959097851, 12, "%s: pokeMenu() isMenuVisible=%b", "PhonePipMenuController", Boolean.valueOf(isMenuVisible));
        }
        if (isMenuVisible) {
            PipMenuView pipMenuView = this.mPipMenuView;
            ((HandlerExecutor) pipMenuView.mMainExecutor).removeCallbacks(pipMenuView.mHideMenuRunnable);
        }
    }

    @Override // com.android.wm.shell.pip.PipMenuController
    public final void resizePipMenu(Rect rect, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        if (rect.isEmpty() || !checkPipMenuState() || surfaceControl == null) {
            return;
        }
        transaction.apply();
    }

    @Override // com.android.wm.shell.pip.PipMenuController
    public final void setSplitMenuEnabled(boolean z) {
        View view;
        PipMenuView pipMenuView = this.mPipMenuView;
        if (pipMenuView == null || (view = pipMenuView.mEnterSplitButton) == null) {
            return;
        }
        view.setEnabled(z);
        PipMenuView pipMenuView2 = this.mPipMenuView;
        pipMenuView2.mFocusedTaskAllowSplitScreen = z;
        pipMenuView2.updateEnterSplitButtonIcon();
    }

    public final void showMenu(int i, Rect rect, boolean z, boolean z2, boolean z3) {
        showMenuInternal(i, rect, z, z2, false, z3);
    }

    public final void showMenuInternal(int i, Rect rect, boolean z, boolean z2, boolean z3, boolean z4) {
        String str;
        StringBuilder m1m = AbstractC0000x2c234b15.m1m("showMenu() state=", i, " isMenuVisible=");
        m1m.append(isMenuVisible());
        m1m.append(" allowMenuTimeout=");
        m1m.append(z);
        m1m.append(" willResizeMenu=");
        KeyguardFaceListenModel$$ExternalSyntheticOutline0.m67m(m1m, z2, " withDelay=", z3, " showResizeHandle=false stackBounds=");
        m1m.append(rect);
        m1m.append(" callers=\n");
        m1m.append(Debug.getCallers(5, "    "));
        Log.d("PhonePipMenuController", m1m.toString());
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            str = "PhonePipMenuController";
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 824963507, 0, "%s: showMenu() state=%s isMenuVisible=%s allowMenuTimeout=%s willResizeMenu=%s withDelay=%s showResizeHandle=%s callers=\n%s", "PhonePipMenuController", String.valueOf(i), String.valueOf(isMenuVisible()), String.valueOf(z), String.valueOf(z2), String.valueOf(z3), String.valueOf(false), String.valueOf(Debug.getCallers(5, "    ")));
        } else {
            str = "PhonePipMenuController";
        }
        if (checkPipMenuState()) {
            if (this.mPipMenuView.mIsExpanding) {
                Log.d(str, "showMenuInternal: skip, reason=expanding");
                return;
            }
            String str2 = str;
            if (this.mPipBoundsState.isStashed()) {
                Log.d(str2, "showMenuInternal: skip, reason=stashed");
            } else {
                if (!r5.mMotionBoundsState.mBoundsInMotion.isEmpty()) {
                    Log.d(str2, "showMenuInternal: skip, reason=in_motion");
                    return;
                }
                movePipMenu(null, null, rect, -1.0f);
                updateMenuBounds(rect);
                this.mPipMenuView.showMenu(i, rect, z, z2, z3, z4);
            }
        }
    }

    public final void showMenuWithPossibleDelay(Rect rect, boolean z, boolean z2) {
        if (z) {
            boolean isMenuVisible = isMenuVisible();
            if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1955248346, 12, "%s: fadeOutMenu() isMenuVisible=%b", "PhonePipMenuController", Boolean.valueOf(isMenuVisible));
            }
            if (isMenuVisible) {
                PipMenuView pipMenuView = this.mPipMenuView;
                pipMenuView.mMenuContainer.setAlpha(0.0f);
                pipMenuView.mSettingsButton.setAlpha(0.0f);
                pipMenuView.mDismissButton.setAlpha(0.0f);
                pipMenuView.mEnterSplitButton.setAlpha(0.0f);
                pipMenuView.mExpandButton.setAlpha(0.0f);
            }
        }
        showMenuInternal(1, rect, true, z, z, z2);
    }

    public final void updateMenuActions() {
        PipMenuView pipMenuView = this.mPipMenuView;
        if (pipMenuView != null) {
            Rect bounds = this.mPipBoundsState.getBounds();
            List list = this.mAppActions;
            pipMenuView.setActions(bounds, list != null && list.size() > 0 ? this.mAppActions : this.mMediaActions, this.mCloseAction);
        }
    }

    @Override // com.android.wm.shell.pip.PipMenuController
    public final void updateMenuBounds(Rect rect) {
        this.mSystemWindows.updateViewLayout(this.mPipMenuView, PipMenuController.getPipMenuLayoutParams(this.mContext, rect.width(), rect.height(), "PipMenuView"));
        if (isMenuVisible()) {
            this.mPipMenuView.mPipMenuIconsAlgorithm.getClass();
        }
    }

    public final void hideMenu(int i) {
        boolean isMenuVisible = isMenuVisible();
        Log.d("PhonePipMenuController", "hideMenu() state=" + this.mMenuState + " isMenuVisible=" + isMenuVisible + " animationType=" + i + " resize=false callers=\n" + Debug.getCallers(5, "    "));
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 617001002, 0, "%s: hideMenu() state=%s isMenuVisible=%s animationType=%s resize=%s callers=\n%s", "PhonePipMenuController", String.valueOf(this.mMenuState), String.valueOf(isMenuVisible), String.valueOf(i), String.valueOf(false), String.valueOf(Debug.getCallers(5, "    ")));
        }
        if (isMenuVisible) {
            this.mPipMenuView.hideMenu(i, null, true, false);
        }
    }
}
