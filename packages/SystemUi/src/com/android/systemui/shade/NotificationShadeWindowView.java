package com.android.systemui.shade;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.session.MediaSessionLegacyHelper;
import android.net.Uri;
import android.os.Bundle;
import android.os.Trace;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.ActionMode;
import android.view.InputDevice;
import android.view.InputQueue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import com.android.internal.util.ArrayUtils;
import com.android.internal.view.FloatingActionMode;
import com.android.internal.widget.floatingtoolbar.FloatingToolbar;
import com.android.keyguard.KeyguardPresentationDisabler;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R$styleable;
import com.android.systemui.classifier.FalsingCollectorImpl;
import com.android.systemui.classifier.FalsingCollectorImpl$$ExternalSyntheticLambda0;
import com.android.systemui.classifier.FalsingDataProvider;
import com.android.systemui.compose.ComposeFacade;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.shade.NotificationShadeWindowViewController;
import com.android.systemui.shade.SecPanelConfigurationBellTower;
import com.android.systemui.statusbar.NotificationInsetsController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.DozeScrimController;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import com.android.systemui.statusbar.phone.PhoneStatusBarViewController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class NotificationShadeWindowView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mBouncerShowing;
    public final C24451 mFakeWindow;
    public ActionMode mFloatingActionMode;
    public View mFloatingActionModeOriginatingView;
    public FloatingToolbar mFloatingToolbar;
    public NotificationShadeWindowView$$ExternalSyntheticLambda1 mFloatingToolbarPreDrawListener;
    public NotificationShadeWindowViewController.C24461 mInteractionEventHandler;
    public NotificationInsetsController mLayoutInsetProvider;
    public int mLeftInset;
    public int mRightInset;
    public IntConsumer mVisibilityChangedListener;
    public IntConsumer mWindowVisibilityChangedListener;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ActionModeCallback2Wrapper extends ActionMode.Callback2 {
        public final ActionMode.Callback mWrapped;

        public ActionModeCallback2Wrapper(ActionMode.Callback callback) {
            this.mWrapped = callback;
        }

        @Override // android.view.ActionMode.Callback
        public final boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.mWrapped.onActionItemClicked(actionMode, menuItem);
        }

        @Override // android.view.ActionMode.Callback
        public final boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return this.mWrapped.onCreateActionMode(actionMode, menu);
        }

        @Override // android.view.ActionMode.Callback
        public final void onDestroyActionMode(ActionMode actionMode) {
            this.mWrapped.onDestroyActionMode(actionMode);
            NotificationShadeWindowView notificationShadeWindowView = NotificationShadeWindowView.this;
            if (actionMode == notificationShadeWindowView.mFloatingActionMode) {
                notificationShadeWindowView.cleanupFloatingActionModeViews();
                NotificationShadeWindowView.this.mFloatingActionMode = null;
            }
            NotificationShadeWindowView.this.requestFitSystemWindows();
        }

        @Override // android.view.ActionMode.Callback2
        public final void onGetContentRect(ActionMode actionMode, View view, Rect rect) {
            ActionMode.Callback callback = this.mWrapped;
            if (callback instanceof ActionMode.Callback2) {
                ((ActionMode.Callback2) callback).onGetContentRect(actionMode, view, rect);
            } else {
                super.onGetContentRect(actionMode, view, rect);
            }
        }

        @Override // android.view.ActionMode.Callback
        public final boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            NotificationShadeWindowView.this.requestFitSystemWindows();
            return this.mWrapped.onPrepareActionMode(actionMode, menu);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class LayoutParams extends FrameLayout.LayoutParams {
        public final boolean ignoreRightInset;

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.StatusBarWindowView_Layout);
            this.ignoreRightInset = obtainStyledAttributes.getBoolean(0, false);
            obtainStyledAttributes.recycle();
        }
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.shade.NotificationShadeWindowView$1] */
    public NotificationShadeWindowView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRightInset = 0;
        this.mLeftInset = 0;
        this.mFakeWindow = new Window(((FrameLayout) this).mContext) { // from class: com.android.systemui.shade.NotificationShadeWindowView.1
            @Override // android.view.Window
            public final View getCurrentFocus() {
                return null;
            }

            @Override // android.view.Window
            public final View getDecorView() {
                return NotificationShadeWindowView.this;
            }

            @Override // android.view.Window
            public final WindowInsetsController getInsetsController() {
                return null;
            }

            @Override // android.view.Window
            public final LayoutInflater getLayoutInflater() {
                return null;
            }

            @Override // android.view.Window
            public final int getNavigationBarColor() {
                return 0;
            }

            @Override // android.view.Window
            public final int getStatusBarColor() {
                return 0;
            }

            @Override // android.view.Window
            public final int getVolumeControlStream() {
                return 0;
            }

            @Override // android.view.Window
            public final boolean isFloating() {
                return false;
            }

            @Override // android.view.Window
            public final boolean isShortcutKey(int i, KeyEvent keyEvent) {
                return false;
            }

            @Override // android.view.Window
            public final View peekDecorView() {
                return null;
            }

            @Override // android.view.Window
            public final boolean performContextMenuIdentifierAction(int i, int i2) {
                return false;
            }

            @Override // android.view.Window
            public final boolean performPanelIdentifierAction(int i, int i2, int i3) {
                return false;
            }

            @Override // android.view.Window
            public final boolean performPanelShortcut(int i, int i2, KeyEvent keyEvent, int i3) {
                return false;
            }

            @Override // android.view.Window
            public final Bundle saveHierarchyState() {
                return null;
            }

            @Override // android.view.Window
            public final void setContentView(int i) {
            }

            @Override // android.view.Window
            public final boolean superDispatchGenericMotionEvent(MotionEvent motionEvent) {
                return false;
            }

            @Override // android.view.Window
            public final boolean superDispatchKeyEvent(KeyEvent keyEvent) {
                return false;
            }

            @Override // android.view.Window
            public final boolean superDispatchKeyShortcutEvent(KeyEvent keyEvent) {
                return false;
            }

            @Override // android.view.Window
            public final boolean superDispatchTouchEvent(MotionEvent motionEvent) {
                return false;
            }

            @Override // android.view.Window
            public final boolean superDispatchTrackballEvent(MotionEvent motionEvent) {
                return false;
            }

            @Override // android.view.Window
            public final void setContentView(View view) {
            }

            @Override // android.view.Window
            public final void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
            }

            @Override // android.view.Window
            public final void closePanel(int i) {
            }

            @Override // android.view.Window
            public final void invalidatePanelMenu(int i) {
            }

            @Override // android.view.Window
            public final void onConfigurationChanged(Configuration configuration) {
            }

            public final void onPictureInPictureModeChanged(boolean z) {
            }

            @Override // android.view.Window
            public final void restoreHierarchyState(Bundle bundle) {
            }

            @Override // android.view.Window
            public final void setBackgroundDrawable(Drawable drawable) {
            }

            @Override // android.view.Window
            public final void setDecorCaptionShade(int i) {
            }

            @Override // android.view.Window
            public final void setNavigationBarColor(int i) {
            }

            @Override // android.view.Window
            public final void setResizingCaptionDrawable(Drawable drawable) {
            }

            @Override // android.view.Window
            public final void setStatusBarColor(int i) {
            }

            @Override // android.view.Window
            public final void setTitle(CharSequence charSequence) {
            }

            @Override // android.view.Window
            public final void setTitleColor(int i) {
            }

            @Override // android.view.Window
            public final void setVolumeControlStream(int i) {
            }

            @Override // android.view.Window
            public final void takeInputQueue(InputQueue.Callback callback) {
            }

            @Override // android.view.Window
            public final void takeKeyEvents(boolean z) {
            }

            @Override // android.view.Window
            public final void takeSurface(SurfaceHolder.Callback2 callback2) {
            }

            public final void alwaysReadCloseOnTouchAttr() {
            }

            public final void clearContentView() {
            }

            @Override // android.view.Window
            public final void closeAllPanels() {
            }

            @Override // android.view.Window
            public final void onActive() {
            }

            public final void onMultiWindowModeChanged() {
            }

            @Override // android.view.Window
            public final void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
            }

            @Override // android.view.Window
            public final void openPanel(int i, KeyEvent keyEvent) {
            }

            @Override // android.view.Window
            public final void setChildDrawable(int i, Drawable drawable) {
            }

            @Override // android.view.Window
            public final void setChildInt(int i, int i2) {
            }

            @Override // android.view.Window
            public final void setFeatureDrawable(int i, Drawable drawable) {
            }

            @Override // android.view.Window
            public final void setFeatureDrawableAlpha(int i, int i2) {
            }

            @Override // android.view.Window
            public final void setFeatureDrawableResource(int i, int i2) {
            }

            @Override // android.view.Window
            public final void setFeatureDrawableUri(int i, Uri uri) {
            }

            @Override // android.view.Window
            public final void setFeatureInt(int i, int i2) {
            }

            @Override // android.view.Window
            public final void togglePanel(int i, KeyEvent keyEvent) {
            }
        };
        setMotionEventSplittingEnabled(false);
    }

    public final void applyBouncerMargins() {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getLayoutParams() instanceof LayoutParams) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (!layoutParams.ignoreRightInset && (((FrameLayout.LayoutParams) layoutParams).rightMargin != 0 || ((FrameLayout.LayoutParams) layoutParams).leftMargin != 0)) {
                    ((FrameLayout.LayoutParams) layoutParams).rightMargin = 0;
                    ((FrameLayout.LayoutParams) layoutParams).leftMargin = 0;
                    childAt.requestLayout();
                }
            }
        }
    }

    public final void cleanupFloatingActionModeViews() {
        FloatingToolbar floatingToolbar = this.mFloatingToolbar;
        if (floatingToolbar != null) {
            floatingToolbar.dismiss();
            this.mFloatingToolbar = null;
        }
        View view = this.mFloatingActionModeOriginatingView;
        if (view != null) {
            if (this.mFloatingToolbarPreDrawListener != null) {
                view.getViewTreeObserver().removeOnPreDrawListener(this.mFloatingToolbarPreDrawListener);
                this.mFloatingToolbarPreDrawListener = null;
            }
            this.mFloatingActionModeOriginatingView = null;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        NotificationShadeWindowViewController.C24461 c24461 = this.mInteractionEventHandler;
        c24461.getClass();
        int action = motionEvent.getAction();
        NotificationShadeWindowViewController notificationShadeWindowViewController = NotificationShadeWindowViewController.this;
        if (((StatusBarStateControllerImpl) notificationShadeWindowViewController.mStatusBarStateController).mState == 1 && action == 7) {
            ((CentralSurfacesImpl) notificationShadeWindowViewController.mService).userActivity();
        }
        return super.dispatchHoverEvent(motionEvent);
    }

    /* JADX WARN: Removed duplicated region for block: B:112:0x0250  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x01f4  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01d8  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01f2  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x01fe  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean dispatchKeyEvent(final KeyEvent keyEvent) {
        boolean z;
        int keyCode;
        boolean z2;
        boolean z3;
        int[] iArr;
        int i;
        NotificationShadeWindowViewController notificationShadeWindowViewController = NotificationShadeWindowViewController.this;
        KeyguardPresentationDisabler keyguardPresentationDisabler = notificationShadeWindowViewController.mPresentationDisabler;
        if (!keyguardPresentationDisabler.mKeyEnabled) {
            long currentTimeMillis = System.currentTimeMillis();
            int[] iArr2 = KeyguardPresentationDisabler.KEYS;
            int i2 = 0;
            boolean z4 = false;
            while (true) {
                iArr = keyguardPresentationDisabler.mDownCount;
                if (i2 >= 2) {
                    break;
                }
                int i3 = iArr2[i2];
                if (i3 == keyEvent.getKeyCode() && keyEvent.getAction() == 0) {
                    long j = keyguardPresentationDisabler.mLastDownTime;
                    if ((j == 0 || currentTimeMillis - j <= 30) && (i = i3 - iArr2[0]) >= 0) {
                        keyguardPresentationDisabler.mLastDownTime = currentTimeMillis;
                        iArr[i] = iArr[i] + 1;
                        z4 = true;
                    }
                }
                i2++;
            }
            if (z4) {
                int i4 = 0;
                int i5 = 0;
                for (int i6 : iArr) {
                    if (i6 > 0) {
                        i4++;
                        i5 += i6;
                    }
                }
                if (i4 == 2) {
                    if (i5 == 2) {
                        keyguardPresentationDisabler.mVibratorHelper.vibrate(5);
                        Log.d("KeyguardDisplayManager", "keys were pressed to disable KeyguardPresentation");
                        keyguardPresentationDisabler.mKeyEnabled = true;
                    } else {
                        Arrays.fill(iArr, 0);
                        keyguardPresentationDisabler.mLastDownTime = 0L;
                    }
                }
            } else {
                Arrays.fill(iArr, 0);
                keyguardPresentationDisabler.mLastDownTime = 0L;
            }
        }
        final CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) notificationShadeWindowViewController.mService;
        if (!centralSurfacesImpl.interceptMediaKey(keyEvent)) {
            centralSurfacesImpl.getClass();
            boolean z5 = ArrayUtils.indexOf(CentralSurfacesImpl.IGNORED_EXT_KEYCODE, Integer.valueOf(keyEvent.getKeyCode())) == -1;
            LogUtil.m223d("CentralSurfaces", "interceptRestKey isRestKey=%s event=%s, mState=%d, mDozing=%s", Boolean.valueOf(z5), keyEvent, Integer.valueOf(centralSurfacesImpl.mState), Boolean.valueOf(centralSurfacesImpl.mDozing));
            if (centralSurfacesImpl.mState == 1) {
                StatusBarKeyguardViewManager statusBarKeyguardViewManager = centralSurfacesImpl.mStatusBarKeyguardViewManager;
                if (!statusBarKeyguardViewManager.isBouncerShowing() && z5) {
                    final boolean[] zArr = {false};
                    if (keyEvent.getAction() == 0) {
                        if (centralSurfacesImpl.mIsKeyDownInDozing == null) {
                            centralSurfacesImpl.mIsKeyDownInDozing = Boolean.valueOf(centralSurfacesImpl.mDozing);
                        }
                        if (centralSurfacesImpl.mDozing) {
                            centralSurfacesImpl.mKeyUpCountInDozing = 0;
                        }
                        if (keyEvent.getRepeatCount() > 0 && centralSurfacesImpl.mIsKeyDownInDozing.booleanValue() && !centralSurfacesImpl.mDozing && centralSurfacesImpl.mKeyUpCountInDozing > 0) {
                            centralSurfacesImpl.mKeyUpCountInDozing = 0;
                        }
                    }
                    if (keyEvent.getAction() == 1 && keyEvent.getRepeatCount() == 0) {
                        int i7 = centralSurfacesImpl.mDisplayLifecycle.mPreviousState;
                        Boolean bool = centralSurfacesImpl.mIsKeyDownInDozing;
                        if ((bool == null && i7 != 1) || !(bool == null || bool.equals(Boolean.valueOf(centralSurfacesImpl.mDozing)))) {
                            Log.d("CentralSurfaces", "interceptRestKey : reset state");
                            centralSurfacesImpl.mKeyUpCountInDozing = 0;
                            centralSurfacesImpl.mIsKeyDownInDozing = null;
                        } else if (centralSurfacesImpl.mDozing && centralSurfacesImpl.mKeyUpCountInDozing == 0) {
                            Log.d("CentralSurfaces", "interceptRestKey : ignore screen on ACTION_UP");
                            centralSurfacesImpl.mKeyUpCountInDozing++;
                            centralSurfacesImpl.mIsKeyDownInDozing = null;
                        }
                        z3 = true;
                        if (!z3) {
                            z = false;
                            if (!z || super.dispatchKeyEvent(keyEvent)) {
                                return true;
                            }
                            NotificationShadeWindowViewController.C24461 c24461 = this.mInteractionEventHandler;
                            c24461.getClass();
                            boolean z6 = keyEvent.getAction() != 0;
                            keyCode = keyEvent.getKeyCode();
                            NotificationShadeWindowViewController notificationShadeWindowViewController2 = NotificationShadeWindowViewController.this;
                            if (keyCode == 4) {
                                if (keyCode == 62) {
                                    if (!z6) {
                                        CentralSurfacesImpl centralSurfacesImpl2 = (CentralSurfacesImpl) notificationShadeWindowViewController2.mService;
                                        if (centralSurfacesImpl2.mDeviceInteractive && centralSurfacesImpl2.mState != 0) {
                                            z2 = true;
                                            ((ShadeControllerImpl) centralSurfacesImpl2.mShadeController).animateCollapsePanels(1.0f, 0, true, false);
                                        }
                                    }
                                    return false;
                                }
                                if (keyCode != 82) {
                                    if ((keyCode == 24 || keyCode == 25) && ((StatusBarStateControllerImpl) notificationShadeWindowViewController2.mStatusBarStateController).mIsDozing) {
                                        z2 = true;
                                        MediaSessionLegacyHelper.getHelper(notificationShadeWindowViewController2.mView.getContext()).sendVolumeKeyEvent(keyEvent, VideoPlayer.MEDIA_ERROR_SYSTEM, true);
                                    }
                                } else if (!z6) {
                                    return ((CentralSurfacesImpl) notificationShadeWindowViewController2.mService).onMenuPressed();
                                }
                                return false;
                            }
                            z2 = true;
                            if (!z6) {
                                ((CentralSurfacesImpl) notificationShadeWindowViewController2.mService).onBackPressed();
                            }
                            return z2;
                        }
                    }
                    InputDevice device = keyEvent.getDevice();
                    boolean z7 = device != null && device.isExternal();
                    boolean z8 = ((SettingsHelper) Dependency.get(SettingsHelper.class)).mItemLists.get("sidesync_source_connect").getIntValue() != 0;
                    if (keyEvent.getAction() == 1 && (!centralSurfacesImpl.mDozing || centralSurfacesImpl.mKeyUpCountInDozing > 0)) {
                        centralSurfacesImpl.mIsKeyDownInDozing = null;
                        if (z7 || z8) {
                            LogUtil.m223d("CentralSurfaces", "interceptRestKey isExt=%s, sideSync=%s", Boolean.valueOf(z7), Boolean.valueOf(z8));
                            Optional.ofNullable((InputMethodManager) centralSurfacesImpl.mContext.getSystemService("input_method")).ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda26
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    CentralSurfacesImpl centralSurfacesImpl3 = CentralSurfacesImpl.this;
                                    boolean[] zArr2 = zArr;
                                    KeyEvent keyEvent2 = keyEvent;
                                    centralSurfacesImpl3.getClass();
                                    if (((InputMethodManager) obj).isAccessoryKeyboardState() == 0) {
                                        return;
                                    }
                                    zArr2[0] = true;
                                    if (keyEvent2.isCanceled()) {
                                        return;
                                    }
                                    KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_KEYBOARD);
                                    Log.d("CentralSurfaces", "interceptRestKey call dismiss");
                                    centralSurfacesImpl3.mActivityStarter.executeRunnableDismissingKeyguard(null, null, true, false, true);
                                }
                            });
                        }
                    }
                    if (!z7 && !z8 && centralSurfacesImpl.mState == 1) {
                        boolean interceptRestKey = statusBarKeyguardViewManager.interceptRestKey(keyEvent);
                        zArr[0] = interceptRestKey;
                        if (interceptRestKey && keyEvent.getAction() == 0) {
                            centralSurfacesImpl.mKeyguardViewMediatorCallback.userActivity();
                        }
                    }
                    z3 = zArr[0];
                    if (!z3) {
                    }
                }
            }
            z3 = false;
            if (!z3) {
            }
        }
        z = true;
        if (!z) {
            return true;
        }
        NotificationShadeWindowViewController.C24461 c244612 = this.mInteractionEventHandler;
        c244612.getClass();
        if (keyEvent.getAction() != 0) {
        }
        keyCode = keyEvent.getKeyCode();
        NotificationShadeWindowViewController notificationShadeWindowViewController22 = NotificationShadeWindowViewController.this;
        if (keyCode == 4) {
        }
        return z2;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchKeyEventPreIme(KeyEvent keyEvent) {
        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) NotificationShadeWindowViewController.this.mService;
        centralSurfacesImpl.getClass();
        if (keyEvent.getKeyCode() == 4 && (!(LsRune.SECURITY_CAPTURED_BLUR && centralSurfacesImpl.mBouncerShowing) && centralSurfacesImpl.mState == 1 && centralSurfacesImpl.mStatusBarKeyguardViewManager.dispatchBackKeyEventPreIme())) {
            return centralSurfacesImpl.onBackPressed();
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:75:0x013d  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x014d  */
    /* JADX WARN: Type inference failed for: r15v1, types: [android.view.View] */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        Boolean bool;
        NotificationShadeWindowViewController notificationShadeWindowViewController = NotificationShadeWindowViewController.this;
        if (notificationShadeWindowViewController.mStatusBarViewController == null) {
            Log.w("NotifShadeWindowVC", "Ignoring touch while statusBarView not yet set.");
            bool = Boolean.FALSE;
        } else {
            boolean z = motionEvent.getActionMasked() == 0;
            boolean z2 = motionEvent.getActionMasked() == 1;
            boolean z3 = motionEvent.getActionMasked() == 3;
            boolean z4 = notificationShadeWindowViewController.mExpandingBelowNotch;
            if (z2 || z3) {
                notificationShadeWindowViewController.mExpandingBelowNotch = false;
            }
            CentralSurfaces centralSurfaces = notificationShadeWindowViewController.mService;
            if (z3 || !((CentralSurfacesImpl) centralSurfaces).shouldIgnoreTouch()) {
                if (z) {
                    notificationShadeWindowViewController.mTouchActive = true;
                    notificationShadeWindowViewController.mTouchCancelled = false;
                    notificationShadeWindowViewController.mDownEvent = motionEvent;
                } else if (motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 3) {
                    notificationShadeWindowViewController.mTouchActive = false;
                    notificationShadeWindowViewController.mDownEvent = null;
                }
                boolean z5 = notificationShadeWindowViewController.mTouchCancelled;
                NotificationShadeWindowView notificationShadeWindowView = notificationShadeWindowViewController.mView;
                if (z5 || notificationShadeWindowViewController.mExpandAnimationRunning) {
                    if (notificationShadeWindowView.getVisibility() == 0 || motionEvent.getActionMasked() != 3) {
                        bool = Boolean.FALSE;
                    }
                    bool = null;
                } else if (notificationShadeWindowViewController.mKeyguardUnlockAnimationController.playingCannedUnlockAnimation) {
                    bool = null;
                } else if (notificationShadeWindowViewController.mIsOcclusionTransitionRunning) {
                    bool = Boolean.FALSE;
                } else {
                    ((FalsingCollectorImpl) notificationShadeWindowViewController.mFalsingCollector).onTouchEvent(motionEvent);
                    if (notificationShadeWindowViewController.mStatusBarKeyguardViewManager.dispatchTouchEvent(motionEvent)) {
                        bool = Boolean.TRUE;
                    } else {
                        View view = notificationShadeWindowViewController.mBrightnessMirror;
                        if (view != null && view.getVisibility() == 0 && motionEvent.getActionMasked() == 5) {
                            bool = Boolean.FALSE;
                        } else {
                            if (z) {
                                NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationShadeWindowViewController.mNotificationStackScrollLayoutController;
                                NotificationGuts notificationGuts = notificationStackScrollLayoutController.mNotificationGutsManager.mNotificationGutsExposed;
                                NotificationMenuRowPlugin currentMenuRow = notificationStackScrollLayoutController.mSwipeHelper.getCurrentMenuRow();
                                ?? r15 = notificationStackScrollLayoutController.mSwipeHelper.mTranslatingParentView;
                                if (notificationGuts == null || notificationGuts.mGutsContent.isLeavebehind()) {
                                    notificationGuts = (currentMenuRow == null || !currentMenuRow.isMenuVisible() || r15 == 0) ? null : r15;
                                }
                                if (notificationGuts != null && !NotificationSwipeHelper.isTouchInView(notificationGuts, motionEvent)) {
                                    notificationStackScrollLayoutController.mNotificationGutsManager.closeAndSaveGuts(false, false, true, false);
                                    notificationStackScrollLayoutController.mSwipeHelper.resetExposedMenuView(true, true);
                                }
                            }
                            if (((StatusBarStateControllerImpl) notificationShadeWindowViewController.mStatusBarStateController).mIsDozing) {
                                DozeScrimController dozeScrimController = ((CentralSurfacesImpl) centralSurfaces).mDozeScrimController;
                                dozeScrimController.mHandler.removeCallbacks(dozeScrimController.mPulseOut);
                            }
                            notificationShadeWindowViewController.mLockIconViewController.getClass();
                            if (LsRune.SECURITY_SIM_PERM_DISABLED && notificationShadeWindowViewController.mKeyguardUpdateMonitor.isIccBlockedPermanently()) {
                                notificationShadeWindowViewController.mExpandingBelowNotch = false;
                            } else {
                                boolean isStatusBarHidden = ((KnoxStateMonitorImpl) notificationShadeWindowViewController.mPanelBlockExpandingHelper.mKnoxStateMonitor).isStatusBarHidden();
                                if (isStatusBarHidden) {
                                    Log.d("SecPanelBlockExpandingHelper", "KnoxStateMonitor.isStatusBarHidden() is true");
                                }
                                if (isStatusBarHidden) {
                                    notificationShadeWindowViewController.mExpandingBelowNotch = false;
                                } else {
                                    if (z && motionEvent.getY() >= notificationShadeWindowView.getBottom()) {
                                        notificationShadeWindowViewController.mExpandingBelowNotch = true;
                                        z4 = true;
                                    }
                                    if (!z4) {
                                        bool = Boolean.valueOf(((PhoneStatusBarView) notificationShadeWindowViewController.mStatusBarViewController.mView).dispatchTouchEvent(motionEvent));
                                    } else if (!notificationShadeWindowViewController.mIsTrackingBarGesture && z && notificationShadeWindowViewController.mNotificationPanelViewController.isFullyCollapsed()) {
                                        float rawX = motionEvent.getRawX();
                                        float rawY = motionEvent.getRawY();
                                        PhoneStatusBarViewController phoneStatusBarViewController = notificationShadeWindowViewController.mStatusBarViewController;
                                        View view2 = phoneStatusBarViewController.mView;
                                        phoneStatusBarViewController.viewUtil.getClass();
                                        int i = view2.getLocationOnScreen()[0];
                                        int i2 = view2.getLocationOnScreen()[1];
                                        if (((float) i) <= rawX && rawX <= ((float) (view2.getWidth() + i)) && ((float) i2) <= rawY && rawY <= ((float) (view2.getHeight() + i2))) {
                                            if (notificationShadeWindowViewController.mStatusBarWindowStateController.windowState == 0) {
                                                notificationShadeWindowViewController.mIsTrackingBarGesture = true;
                                                bool = Boolean.valueOf(((PhoneStatusBarView) notificationShadeWindowViewController.mStatusBarViewController.mView).dispatchTouchEvent(motionEvent));
                                            } else {
                                                bool = Boolean.TRUE;
                                            }
                                        }
                                        bool = null;
                                    } else {
                                        if (notificationShadeWindowViewController.mIsTrackingBarGesture) {
                                            boolean dispatchTouchEvent = ((PhoneStatusBarView) notificationShadeWindowViewController.mStatusBarViewController.mView).dispatchTouchEvent(motionEvent);
                                            if (z2 || z3) {
                                                notificationShadeWindowViewController.mIsTrackingBarGesture = false;
                                            }
                                            bool = Boolean.valueOf(dispatchTouchEvent);
                                        }
                                        bool = null;
                                    }
                                }
                            }
                            z4 = false;
                            if (!z4) {
                            }
                        }
                    }
                }
            } else {
                bool = Boolean.FALSE;
            }
        }
        Boolean valueOf = Boolean.valueOf(bool != null ? bool.booleanValue() : super.dispatchTouchEvent(motionEvent));
        FalsingCollectorImpl falsingCollectorImpl = (FalsingCollectorImpl) NotificationShadeWindowViewController.this.mFalsingCollector;
        FalsingDataProvider falsingDataProvider = falsingCollectorImpl.mFalsingDataProvider;
        Objects.requireNonNull(falsingDataProvider);
        falsingCollectorImpl.mMainExecutor.executeDelayed(100L, new FalsingCollectorImpl$$ExternalSyntheticLambda0(falsingDataProvider));
        return valueOf.booleanValue();
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        Insets insetsIgnoringVisibility = windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());
        boolean z = true;
        if (getFitsSystemWindows()) {
            if (insetsIgnoringVisibility.top == getPaddingTop() && insetsIgnoringVisibility.bottom == getPaddingBottom()) {
                z = false;
            }
            if (z) {
                setPadding(0, 0, 0, 0);
            }
        } else {
            if (getPaddingLeft() == 0 && getPaddingRight() == 0 && getPaddingTop() == 0 && getPaddingBottom() == 0) {
                z = false;
            }
            if (z) {
                setPadding(0, 0, 0, 0);
            }
        }
        this.mLeftInset = 0;
        this.mRightInset = 0;
        Pair pair = this.mLayoutInsetProvider.getinsets(windowInsets, getRootWindowInsets().getDisplayCutout());
        this.mLeftInset = ((Integer) pair.first).intValue();
        this.mRightInset = ((Integer) pair.second).intValue();
        if (LsRune.SECURITY_CAPTURED_BLUR && this.mBouncerShowing) {
            applyBouncerMargins();
        } else {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                if (childAt.getLayoutParams() instanceof LayoutParams) {
                    LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                    if (!layoutParams.ignoreRightInset) {
                        int i2 = ((FrameLayout.LayoutParams) layoutParams).rightMargin;
                        int i3 = this.mRightInset;
                        if (i2 != i3 || ((FrameLayout.LayoutParams) layoutParams).leftMargin != this.mLeftInset) {
                            ((FrameLayout.LayoutParams) layoutParams).rightMargin = i3;
                            ((FrameLayout.LayoutParams) layoutParams).leftMargin = this.mLeftInset;
                            childAt.requestLayout();
                        }
                    }
                }
            }
        }
        return windowInsets;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        setWillNotDraw(true);
        ComposeFacade.INSTANCE.getClass();
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        SecPanelConfigurationBellTower secPanelConfigurationBellTower = ((SecPanelPolicy) Dependency.get(SecPanelPolicy.class)).mPanelConfigurationBellTower;
        SecPanelConfigurationBellTower.PanelConfigurationWrapper panelConfigurationWrapper = secPanelConfigurationBellTower.mTmpConfiguration;
        panelConfigurationWrapper.setConfiguration(configuration);
        secPanelConfigurationBellTower.printConfigurationStateLog("New Vs." + panelConfigurationWrapper.mSeqNumber, "newOri:" + configuration.orientation);
        SecPanelConfigurationBellTower.PanelConfigurationWrapper panelConfigurationWrapper2 = secPanelConfigurationBellTower.mViewConfiguration;
        if (panelConfigurationWrapper2.mSeqNumber >= panelConfigurationWrapper.mSeqNumber) {
            return;
        }
        panelConfigurationWrapper2.setConfiguration(panelConfigurationWrapper.mConfiguration);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ComposeFacade.INSTANCE.getClass();
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (LsRune.SECURITY_SIM_PERM_DISABLED && ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).isSimDisabledPermanently() && LsRune.LOCKUI_BOTTOM_USIM_TEXT && !this.mInteractionEventHandler.isTouchableArea(motionEvent)) {
            return true;
        }
        NotificationShadeWindowViewController notificationShadeWindowViewController = NotificationShadeWindowViewController.this;
        if (!notificationShadeWindowViewController.mStatusBarKeyguardViewManager.shouldInterceptTouchEvent()) {
            notificationShadeWindowViewController.mLockIconViewController.getClass();
            int actionMasked = motionEvent.getActionMasked();
            NotificationPanelViewController notificationPanelViewController = notificationShadeWindowViewController.mNotificationPanelViewController;
            SysuiStatusBarStateController sysuiStatusBarStateController = notificationShadeWindowViewController.mStatusBarStateController;
            if (actionMasked == 0 && ((StatusBarStateControllerImpl) sysuiStatusBarStateController).mState == 1) {
                notificationShadeWindowViewController.mTouchedOnEmptyArea = notificationPanelViewController.isTouchOnEmptyArea(motionEvent);
            }
            if (motionEvent.getActionMasked() == 0 && ((StatusBarStateControllerImpl) sysuiStatusBarStateController).mState == 1) {
                notificationShadeWindowViewController.mPluginLockTouchArea = notificationPanelViewController.isInLockStarContainer(motionEvent);
            }
            if (motionEvent.getActionMasked() == 0 && ((StatusBarStateControllerImpl) sysuiStatusBarStateController).mState == 1) {
                notificationShadeWindowViewController.mSecKeyguardStatusViewTouchArea = notificationPanelViewController.isInFaceWidgetContainer(motionEvent);
            }
            if (notificationPanelViewController.isFullyExpanded() && notificationShadeWindowViewController.mDragDownHelper.dragDownCallback.m196x229f8ab3(null) && !notificationShadeWindowViewController.mTouchedOnEmptyArea && !notificationShadeWindowViewController.mPluginLockTouchArea && !notificationShadeWindowViewController.mSecKeyguardStatusViewTouchArea) {
                CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) notificationShadeWindowViewController.mService;
                if (!centralSurfacesImpl.mBouncerShowing && !((StatusBarStateControllerImpl) sysuiStatusBarStateController).mIsDozing) {
                    if (!(centralSurfacesImpl.mIsDlsOverlay && centralSurfacesImpl.mState == 1)) {
                        r1 = notificationShadeWindowViewController.mDragDownHelper.onInterceptTouchEvent(motionEvent);
                    }
                }
            }
            r1 = false;
        }
        if (!r1) {
            r1 = super.onInterceptTouchEvent(motionEvent);
        }
        if (r1) {
            NotificationShadeWindowViewController.C24461 c24461 = this.mInteractionEventHandler;
            c24461.getClass();
            MotionEvent obtain = MotionEvent.obtain(motionEvent);
            obtain.setAction(3);
            NotificationShadeWindowViewController notificationShadeWindowViewController2 = NotificationShadeWindowViewController.this;
            notificationShadeWindowViewController2.mStackScrollLayout.onInterceptTouchEvent(obtain);
            notificationShadeWindowViewController2.mNotificationPanelViewController.mTouchHandler.onInterceptTouchEvent(obtain);
            obtain.recycle();
        }
        return r1;
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        Trace.beginSection("NotificationShadeWindowView#onMeasure");
        super.onMeasure(i, i2);
        Trace.endSection();
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0068  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (LsRune.SECURITY_SIM_PERM_DISABLED && ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).isSimDisabledPermanently() && LsRune.LOCKUI_BOTTOM_USIM_TEXT && !this.mInteractionEventHandler.isTouchableArea(motionEvent)) {
            return true;
        }
        NotificationShadeWindowViewController notificationShadeWindowViewController = NotificationShadeWindowViewController.this;
        boolean z = ((StatusBarStateControllerImpl) notificationShadeWindowViewController.mStatusBarStateController).mIsDozing ? !((CentralSurfacesImpl) notificationShadeWindowViewController.mService).isPulsing() : false;
        if (!notificationShadeWindowViewController.mStatusBarKeyguardViewManager.onTouch(motionEvent)) {
            if (notificationShadeWindowViewController.mDragDownHelper.dragDownCallback.m196x229f8ab3(null) || notificationShadeWindowViewController.mDragDownHelper.isDraggingDown) {
                if (!notificationShadeWindowViewController.mDragDownHelper.onTouchEvent(motionEvent) && !z) {
                    z = false;
                }
            }
            if (!z) {
                z = super.onTouchEvent(motionEvent);
            }
            if (!z) {
                NotificationShadeWindowViewController.C24461 c24461 = this.mInteractionEventHandler;
                c24461.getClass();
                int actionMasked = motionEvent.getActionMasked();
                if (actionMasked == 1 || actionMasked == 3) {
                    ((CentralSurfacesImpl) NotificationShadeWindowViewController.this.mService).setInteracting(1, false);
                }
            }
            return z;
        }
        z = true;
        if (!z) {
        }
        if (!z) {
        }
        return z;
    }

    @Override // android.view.View
    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).setFocusForBiometrics(1, z);
    }

    @Override // android.view.View
    public void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        IntConsumer intConsumer = this.mVisibilityChangedListener;
        if (intConsumer != null) {
            intConsumer.accept(i);
        }
        IntConsumer intConsumer2 = this.mWindowVisibilityChangedListener;
        if (intConsumer2 != null) {
            intConsumer2.accept(i);
        }
        SecPanelPolicy secPanelPolicy = (SecPanelPolicy) Dependency.get(SecPanelPolicy.class);
        Consumer consumer = new Consumer() { // from class: com.android.systemui.shade.NotificationShadeWindowView$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NotificationShadeWindowView notificationShadeWindowView = NotificationShadeWindowView.this;
                int i2 = NotificationShadeWindowView.$r8$clinit;
                notificationShadeWindowView.getRootView().dispatchConfigurationChanged((Configuration) obj);
            }
        };
        final SecPanelConfigurationBellTower secPanelConfigurationBellTower = secPanelPolicy.mPanelConfigurationBellTower;
        secPanelConfigurationBellTower.mWindowVisibility = i;
        secPanelConfigurationBellTower.mDispatchConfigurationChangeConsumer = consumer;
        secPanelConfigurationBellTower.printConfigurationStateLog("onWindowViewVisibilityChanged", "");
        secPanelConfigurationBellTower.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.shade.SecPanelConfigurationBellTower$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SecPanelConfigurationBellTower secPanelConfigurationBellTower2 = SecPanelConfigurationBellTower.this;
                if (secPanelConfigurationBellTower2.mWindowVisibility == 0 && secPanelConfigurationBellTower2.shouldRingBell()) {
                    secPanelConfigurationBellTower2.ringConfigurationBell();
                }
            }
        }, 32L);
    }

    @Override // android.view.View, android.view.ViewParent
    public final void requestLayout() {
        Trace.instant(4096L, "NotificationShadeWindowView#requestLayout");
        super.requestLayout();
    }

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.shade.NotificationShadeWindowView$$ExternalSyntheticLambda1] */
    @Override // android.view.ViewGroup, android.view.ViewParent
    public final ActionMode startActionModeForChild(View view, ActionMode.Callback callback, int i) {
        if (i != 1) {
            return super.startActionModeForChild(view, callback, i);
        }
        ActionModeCallback2Wrapper actionModeCallback2Wrapper = new ActionModeCallback2Wrapper(callback);
        ActionMode actionMode = this.mFloatingActionMode;
        if (actionMode != null) {
            actionMode.finish();
        }
        cleanupFloatingActionModeViews();
        this.mFloatingToolbar = new FloatingToolbar(this.mFakeWindow);
        final FloatingActionMode floatingActionMode = new FloatingActionMode(((FrameLayout) this).mContext, actionModeCallback2Wrapper, view, this.mFloatingToolbar);
        this.mFloatingActionModeOriginatingView = view;
        this.mFloatingToolbarPreDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.shade.NotificationShadeWindowView$$ExternalSyntheticLambda1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                FloatingActionMode floatingActionMode2 = floatingActionMode;
                int i2 = NotificationShadeWindowView.$r8$clinit;
                floatingActionMode2.updateViewLocationInWindow();
                return true;
            }
        };
        if (!actionModeCallback2Wrapper.onCreateActionMode(floatingActionMode, floatingActionMode.getMenu())) {
            return null;
        }
        this.mFloatingActionMode = floatingActionMode;
        floatingActionMode.invalidate();
        this.mFloatingActionModeOriginatingView.getViewTreeObserver().addOnPreDrawListener(this.mFloatingToolbarPreDrawListener);
        return floatingActionMode;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final FrameLayout.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -1);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }
}
