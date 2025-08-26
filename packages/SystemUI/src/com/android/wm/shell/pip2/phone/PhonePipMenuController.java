package com.android.wm.shell.pip2.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.RemoteAction;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Property;
import android.util.Size;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.WindowManager;
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
import com.android.wm.shell.pip2.phone.PipTransitionState;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.animation.Interpolators;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PhonePipMenuController implements PipMenuController, PipTransitionState.PipTransitionStateChangedListener {
    public List mAppActions;
    public RemoteAction mCloseAction;
    public final Context mContext;
    public boolean mIsImeVisible;
    public SurfaceControl mLeash;
    public final ShellExecutor mMainExecutor;
    public final Handler mMainHandler;
    public List mMediaActions;
    public final PipMediaController mMediaController;
    public int mMenuState;
    public final PipBoundsState mPipBoundsState;
    public final PipDisplayLayoutState mPipDisplayLayoutState;
    public PipMenuView mPipMenuView;
    public final PipTransitionState mPipTransitionState;
    public final PipUiEventLogger mPipUiEventLogger;
    public final SystemWindows mSystemWindows;
    public final ArrayList mListeners = new ArrayList();
    public final AnonymousClass1 mMediaActionListener = new AnonymousClass1();

    /* renamed from: com.android.wm.shell.pip2.phone.PhonePipMenuController$1, reason: invalid class name */
    public class AnonymousClass1 implements PipMediaController.ActionListener {
        public AnonymousClass1() {
        }

        @Override // com.android.wm.shell.common.pip.PipMediaController.ActionListener
        public final void onMediaActionsChanged(List list) {
            ArrayList arrayList = new ArrayList(list);
            PhonePipMenuController phonePipMenuController = PhonePipMenuController.this;
            phonePipMenuController.mMediaActions = arrayList;
            phonePipMenuController.updateMenuActions$2();
        }
    }

    /* renamed from: com.android.wm.shell.pip2.phone.PhonePipMenuController$2, reason: invalid class name */
    public class AnonymousClass2 {
        public AnonymousClass2() {
        }
    }

    public PhonePipMenuController(Context context, PipBoundsState pipBoundsState, PipMediaController pipMediaController, SystemWindows systemWindows, PipUiEventLogger pipUiEventLogger, PipTaskListener pipTaskListener, PipTransitionState pipTransitionState, DisplayController displayController, DisplayInsetsController displayInsetsController, PipDisplayLayoutState pipDisplayLayoutState, ShellExecutor shellExecutor, Handler handler) {
        this.mContext = context;
        this.mPipBoundsState = pipBoundsState;
        this.mMediaController = pipMediaController;
        this.mSystemWindows = systemWindows;
        this.mPipTransitionState = pipTransitionState;
        this.mPipDisplayLayoutState = pipDisplayLayoutState;
        this.mMainExecutor = shellExecutor;
        this.mMainHandler = handler;
        this.mPipUiEventLogger = pipUiEventLogger;
        pipTransitionState.addPipTransitionStateChangedListener(this);
        PipBoundsState.OnPipComponentChangedListener onPipComponentChangedListener = new PipBoundsState.OnPipComponentChangedListener() { // from class: com.android.wm.shell.pip2.phone.PhonePipMenuController$$ExternalSyntheticLambda0
            @Override // com.android.wm.shell.common.pip.PipBoundsState.OnPipComponentChangedListener
            public final void onPipComponentChanged() {
                PhonePipMenuController phonePipMenuController = this.f$0;
                List list = phonePipMenuController.mAppActions;
                if (list != null) {
                    list.clear();
                }
                phonePipMenuController.mCloseAction = null;
            }
        };
        if (!((ArrayList) pipBoundsState.mOnPipComponentChangedListeners).contains(onPipComponentChangedListener)) {
            ((ArrayList) pipBoundsState.mOnPipComponentChangedListeners).add(onPipComponentChangedListener);
        }
        AnonymousClass2 anonymousClass2 = new AnonymousClass2();
        if (!((ArrayList) pipTaskListener.mPipParamsChangedListeners).contains(anonymousClass2)) {
            ((ArrayList) pipTaskListener.mPipParamsChangedListeners).add(anonymousClass2);
        }
        int i = pipDisplayLayoutState.mDisplayId;
        displayInsetsController.addInsetsChangedListener(i, new ImeListener(displayController, i) { // from class: com.android.wm.shell.pip2.phone.PhonePipMenuController.3
            @Override // com.android.wm.shell.common.ImeListener
            public final void onImeVisibilityChanged(boolean z, int i2) {
                PhonePipMenuController.this.mIsImeVisible = z;
            }
        });
    }

    @Override // com.android.wm.shell.common.pip.PipMenuController
    public final void attach(SurfaceControl surfaceControl) {
        this.mLeash = surfaceControl;
        PipMenuView pipMenuView = this.mPipMenuView;
        SystemWindows systemWindows = this.mSystemWindows;
        if (pipMenuView != null && pipMenuView != null) {
            ((SurfaceControlViewHost) systemWindows.mViewRoots.remove(pipMenuView)).release();
            this.mPipMenuView = null;
        }
        PipMenuView pipMenuView2 = new PipMenuView(this.mContext, this, this.mMainExecutor, this.mMainHandler, this.mPipUiEventLogger);
        this.mPipMenuView = pipMenuView2;
        pipMenuView2.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.wm.shell.pip2.phone.PhonePipMenuController.4
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                view.getViewRootImpl().addSurfaceChangedCallback(new ViewRootImpl.SurfaceChangedCallback() { // from class: com.android.wm.shell.pip2.phone.PhonePipMenuController.4.1
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
        systemWindows.addView(this.mPipMenuView, PipMenuController.getPipMenuLayoutParams(this.mContext, 0, 0), this.mPipDisplayLayoutState.mDisplayId);
        if (this.mMenuState != 0) {
            systemWindows.setShellRootAccessibilityWindow(this.mPipMenuView);
        } else {
            systemWindows.setShellRootAccessibilityWindow(null);
        }
        updateMenuActions$2();
    }

    public final boolean checkPipMenuState() {
        PipMenuView pipMenuView = this.mPipMenuView;
        if (pipMenuView != null && pipMenuView.getViewRootImpl() != null) {
            return true;
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 7716599426178299392L, 0, "PhonePipMenuController");
        }
        return false;
    }

    @Override // com.android.wm.shell.common.pip.PipMenuController
    public final void detach() {
        hideMenu();
        PipMenuView pipMenuView = this.mPipMenuView;
        if (pipMenuView != null) {
            ((SurfaceControlViewHost) this.mSystemWindows.mViewRoots.remove(pipMenuView)).release();
            this.mPipMenuView = null;
        }
        this.mLeash = null;
    }

    public final Size getEstimatedMinMenuSize() throws Resources.NotFoundException {
        PipMenuView pipMenuView = this.mPipMenuView;
        if (pipMenuView == null) {
            return null;
        }
        return new Size(Math.max(2, ((ArrayList) pipMenuView.mActions).size()) * pipMenuView.getResources().getDimensionPixelSize(R.dimen.pip_action_size), pipMenuView.getResources().getDimensionPixelSize(R.dimen.pip_expand_container_edge_margin) + pipMenuView.getResources().getDimensionPixelSize(R.dimen.pip_action_padding) + pipMenuView.getResources().getDimensionPixelSize(R.dimen.pip_expand_action_size));
    }

    public final void hideMenu() {
        if (isMenuVisible()) {
            PipMenuView pipMenuView = this.mPipMenuView;
            pipMenuView.hideMenu(null, true, pipMenuView.mDidLastShowMenuResize, 1);
        }
    }

    @Override // com.android.wm.shell.common.pip.PipMenuController
    public final boolean isMenuVisible() {
        return (this.mPipMenuView == null || this.mMenuState == 0) ? false : true;
    }

    @Override // com.android.wm.shell.common.pip.PipMenuController
    public final void movePipMenu(Rect rect, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        if (rect.isEmpty() || !checkPipMenuState() || surfaceControl == null || transaction == null) {
            return;
        }
        transaction.apply();
    }

    @Override // com.android.wm.shell.pip2.phone.PipTransitionState.PipTransitionStateChangedListener
    public final void onPipTransitionStateChanged(int i, int i2, Bundle bundle) {
        if (i2 == 3) {
            attach(this.mPipTransitionState.mPinnedTaskLeash);
            return;
        }
        if (i2 == 4) {
            hideMenu();
            return;
        }
        if (i2 == 5) {
            hideMenu();
        } else if (i2 == 6) {
            hideMenu();
        } else {
            if (i2 != 8) {
                return;
            }
            detach();
        }
    }

    @Override // com.android.wm.shell.common.pip.PipMenuController
    public final void resizePipMenu(Rect rect, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        if (rect.isEmpty() || !checkPipMenuState() || surfaceControl == null) {
            return;
        }
        transaction.apply();
    }

    public final void showMenuInternal(final int i, Rect rect, final boolean z, boolean z2, boolean z3) {
        int i2 = 1;
        if (checkPipMenuState()) {
            movePipMenu(rect, null, null);
            updateMenuBounds(rect);
            final PipMenuView pipMenuView = this.mPipMenuView;
            pipMenuView.mAllowMenuTimeout = z;
            pipMenuView.mDidLastShowMenuResize = z2;
            int i3 = pipMenuView.mMenuState;
            if (i3 == i) {
                if (z) {
                    pipMenuView.repostDelayedHide(2000);
                    return;
                }
                return;
            }
            pipMenuView.mAllowTouches = !(z2 && (i3 == 1 || i == 1));
            ((HandlerExecutor) pipMenuView.mMainExecutor).removeCallbacks(pipMenuView.mHideMenuRunnable);
            AnimatorSet animatorSet = pipMenuView.mMenuContainerAnimator;
            if (animatorSet != null) {
                animatorSet.cancel();
            }
            pipMenuView.mMenuContainerAnimator = new AnimatorSet();
            View view = pipMenuView.mMenuContainer;
            Property property = View.ALPHA;
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, view.getAlpha(), 1.0f);
            objectAnimatorOfFloat.addUpdateListener(pipMenuView.mMenuBgUpdateListener);
            View view2 = pipMenuView.mSettingsButton;
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view2, (Property<View, Float>) property, view2.getAlpha(), 1.0f);
            View view3 = pipMenuView.mDismissButton;
            ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(view3, (Property<View, Float>) property, view3.getAlpha(), 1.0f);
            if (i == 1) {
                pipMenuView.mMenuContainerAnimator.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2, objectAnimatorOfFloat3);
            }
            pipMenuView.mMenuContainerAnimator.setInterpolator(Interpolators.ALPHA_IN);
            pipMenuView.mMenuContainerAnimator.setDuration(125L);
            pipMenuView.mMenuContainerAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.pip2.phone.PipMenuView.3
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationCancel(Animator animator) {
                    PipMenuView.this.mAllowTouches = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    PipMenuView pipMenuView2 = PipMenuView.this;
                    pipMenuView2.mAllowTouches = true;
                    int i4 = i;
                    pipMenuView2.mMenuState = i4;
                    PhonePipMenuController phonePipMenuController = pipMenuView2.mController;
                    if (i4 != phonePipMenuController.mMenuState) {
                        phonePipMenuController.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda3(i4));
                    }
                    phonePipMenuController.mMenuState = i4;
                    SystemWindows systemWindows = phonePipMenuController.mSystemWindows;
                    if (i4 != 0) {
                        systemWindows.setShellRootAccessibilityWindow(phonePipMenuController.mPipMenuView);
                    } else {
                        systemWindows.setShellRootAccessibilityWindow(null);
                    }
                    if (z) {
                        PipMenuView.this.repostDelayedHide(3500);
                    }
                }
            });
            if (z3) {
                pipMenuView.notifyMenuStateChangeStart(i, z2, new PipMenuView$$ExternalSyntheticLambda0(pipMenuView, i2));
            } else {
                pipMenuView.notifyMenuStateChangeStart(i, z2, null);
                pipMenuView.setVisibility(0);
                pipMenuView.mMenuContainerAnimator.start();
            }
            pipMenuView.updateActionViews(i, rect);
        }
    }

    public final void updateMenuActions$2() {
        PipMenuView pipMenuView = this.mPipMenuView;
        if (pipMenuView != null) {
            Rect bounds = this.mPipBoundsState.getBounds();
            List list = this.mAppActions;
            List list2 = (list == null || list.size() <= 0) ? this.mMediaActions : this.mAppActions;
            RemoteAction remoteAction = this.mCloseAction;
            ((ArrayList) pipMenuView.mActions).clear();
            if (list2 != null && !list2.isEmpty()) {
                ((ArrayList) pipMenuView.mActions).addAll(list2);
            }
            pipMenuView.mCloseAction = remoteAction;
            int i = pipMenuView.mMenuState;
            if (i == 1) {
                pipMenuView.updateActionViews(i, bounds);
            }
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
    }

    public final void hideMenu(int i) {
        if (isMenuVisible()) {
            this.mPipMenuView.hideMenu(null, true, false, i);
        }
    }
}
