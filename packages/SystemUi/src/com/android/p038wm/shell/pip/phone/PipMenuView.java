package com.android.p038wm.shell.pip.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.PendingIntent;
import android.app.RemoteAction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.util.Pair;
import android.util.Property;
import android.view.IWindow;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.picker.adapter.layoutmanager.AutoFitGridLayoutManager$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.p038wm.shell.animation.Interpolators;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.common.SystemWindows;
import com.android.p038wm.shell.pip.PipBoundsState;
import com.android.p038wm.shell.pip.PipMediaController;
import com.android.p038wm.shell.pip.PipUiEventLogger;
import com.android.p038wm.shell.pip.PipUtils;
import com.android.p038wm.shell.pip.phone.PhonePipMenuController;
import com.android.p038wm.shell.protolog.ShellProtoLogCache;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.protolog.ShellProtoLogImpl;
import com.android.p038wm.shell.splitscreen.SplitScreenController;
import com.android.systemui.R;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PipMenuView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AccessibilityManager mAccessibilityManager;
    public final List mActions;
    public final LinearLayout mActionsGroup;
    public boolean mAllowMenuTimeout;
    public boolean mAllowTouches;
    public final Drawable mBackgroundDrawable;
    public final int mBetweenActionPaddingLand;
    public RemoteAction mCloseAction;
    public final PhonePipMenuController mController;
    public boolean mDidLastShowMenuResize;
    public final View mDismissButton;
    public final int mDismissFadeOutDurationMs;
    public final View mEnterSplitButton;
    public final Drawable mEnterSplitIconLR;
    public final Drawable mEnterSplitIconTB;
    public final View mExpandButton;
    public boolean mFocusedTaskAllowSplitScreen;
    public final PipMenuView$$ExternalSyntheticLambda0 mHideMenuRunnable;
    public boolean mIsExpanding;
    public final ShellExecutor mMainExecutor;
    public final Handler mMainHandler;
    public final C40601 mMenuBgUpdateListener;
    public final View mMenuContainer;
    public AnimatorSet mMenuContainerAnimator;
    public int mMenuState;
    public final int mPipActionSize;
    public final int mPipActionSizeLandWidth;
    public final int mPipActionSizePortWidth;
    public final int mPipForceCloseDelay;
    public final PipMenuIconsAlgorithm mPipMenuIconsAlgorithm;
    public final int mPipMenuPadding;
    public final int mPipMenuPaddingTop;
    public final PipUiEventLogger mPipUiEventLogger;
    public final View mSettingsButton;
    public final Optional mSplitScreenControllerOptional;
    public final View mTopEndContainer;
    public final View mViewRoot;

    /* renamed from: $r8$lambda$61Zm6ZTuSMPEly2Mxu4fU-qPcDU, reason: not valid java name */
    public static void m2752$r8$lambda$61Zm6ZTuSMPEly2Mxu4fUqPcDU(PipMenuView pipMenuView, View view) {
        pipMenuView.getClass();
        if (view.getAlpha() != 0.0f) {
            Log.d("PipMenuView", "showSettings");
            Pair topPipActivity = PipUtils.getTopPipActivity(((FrameLayout) pipMenuView).mContext);
            if (topPipActivity.first != null) {
                Intent intent = new Intent("android.settings.PICTURE_IN_PICTURE_SETTINGS", Uri.fromParts("package", ((ComponentName) topPipActivity.first).getPackageName(), null));
                intent.setFlags(268468224);
                ((FrameLayout) pipMenuView).mContext.startActivityAsUser(intent, UserHandle.of(((Integer) topPipActivity.second).intValue()));
                pipMenuView.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_SHOW_SETTINGS);
            }
        }
    }

    /* renamed from: -$$Nest$mnotifyMenuStateChangeFinish, reason: not valid java name */
    public static void m2753$$Nest$mnotifyMenuStateChangeFinish(PipMenuView pipMenuView, final int i) {
        StringBuilder sb = new StringBuilder("notifyMenuStateChangeFinish: ");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(sb, pipMenuView.mMenuState, "->", i, ", Callers=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m78m(5, sb, "PipMenuView");
        pipMenuView.mMenuState = i;
        PhonePipMenuController phonePipMenuController = pipMenuView.mController;
        if (i != phonePipMenuController.mMenuState) {
            phonePipMenuController.mListeners.forEach(new Consumer() { // from class: com.android.wm.shell.pip.phone.PhonePipMenuController$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i2 = i;
                    PipTouchHandler pipTouchHandler = PipTouchHandler.this;
                    pipTouchHandler.mMenuState = i2;
                    pipTouchHandler.updateMovementBounds();
                    pipTouchHandler.onRegistrationChanged(i2 == 0);
                    PipUiEventLogger pipUiEventLogger = pipTouchHandler.mPipUiEventLogger;
                    if (i2 == 0) {
                        pipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_HIDE_MENU);
                    } else if (i2 == 1) {
                        pipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_SHOW_MENU);
                    }
                }
            });
        }
        phonePipMenuController.mMenuState = i;
        SystemWindows systemWindows = phonePipMenuController.mSystemWindows;
        if (i == 0) {
            SystemWindows.PerDisplay perDisplay = (SystemWindows.PerDisplay) systemWindows.mPerDisplay.get(0);
            if (perDisplay == null) {
                return;
            }
            perDisplay.setShellRootAccessibilityWindow(null, 1);
            return;
        }
        PipMenuView pipMenuView2 = phonePipMenuController.mPipMenuView;
        SystemWindows.PerDisplay perDisplay2 = (SystemWindows.PerDisplay) systemWindows.mPerDisplay.get(0);
        if (perDisplay2 == null) {
            return;
        }
        perDisplay2.setShellRootAccessibilityWindow(pipMenuView2, 1);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.wm.shell.pip.phone.PipMenuView$1] */
    public PipMenuView(Context context, PhonePipMenuController phonePipMenuController, ShellExecutor shellExecutor, Handler handler, Optional<SplitScreenController> optional, PipUiEventLogger pipUiEventLogger) {
        super(context, null, 0);
        final int i = 0;
        final int i2 = 1;
        this.mAllowMenuTimeout = true;
        this.mAllowTouches = true;
        this.mActions = new ArrayList();
        this.mMenuBgUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.pip.phone.PipMenuView.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                PipMenuView.this.mBackgroundDrawable.setAlpha((int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * 0.3f * 255.0f));
            }
        };
        this.mHideMenuRunnable = new PipMenuView$$ExternalSyntheticLambda0(this, i);
        this.mIsExpanding = false;
        ((FrameLayout) this).mContext = context;
        this.mController = phonePipMenuController;
        this.mMainExecutor = shellExecutor;
        this.mMainHandler = handler;
        this.mSplitScreenControllerOptional = optional;
        this.mPipUiEventLogger = pipUiEventLogger;
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        FrameLayout.inflate(context, R.layout.sem_pip_menu, this);
        this.mPipForceCloseDelay = context.getResources().getInteger(R.integer.config_pipForceCloseDelay);
        if (CoreRune.MW_PIP_DISABLE_ROUND_CORNER) {
            this.mBackgroundDrawable = ((FrameLayout) this).mContext.getDrawable(R.drawable.pip_menu_background_no_corner);
        } else {
            this.mBackgroundDrawable = ((FrameLayout) this).mContext.getDrawable(R.drawable.pip_menu_background);
        }
        this.mBackgroundDrawable.setAlpha(0);
        View findViewById = findViewById(R.id.background);
        this.mViewRoot = findViewById;
        findViewById.setBackground(this.mBackgroundDrawable);
        View findViewById2 = findViewById(R.id.menu_container);
        this.mMenuContainer = findViewById2;
        findViewById2.setAlpha(0.0f);
        View findViewById3 = findViewById(R.id.top_end_container);
        this.mTopEndContainer = findViewById3;
        View findViewById4 = findViewById(R.id.settings);
        this.mSettingsButton = findViewById4;
        findViewById4.setAlpha(0.0f);
        findViewById4.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda1
            public final /* synthetic */ PipMenuView f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r0v1, types: [com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda5] */
            /* JADX WARN: Type inference failed for: r0v3, types: [com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda5] */
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                final int i3 = 0;
                final int i4 = 1;
                switch (i) {
                    case 0:
                        PipMenuView.m2752$r8$lambda$61Zm6ZTuSMPEly2Mxu4fUqPcDU(this.f$0, view);
                        break;
                    case 1:
                        PipMenuView pipMenuView = this.f$0;
                        pipMenuView.getClass();
                        Log.d("PipMenuView", "dismissPip");
                        if (pipMenuView.mMenuState != 0) {
                            pipMenuView.mController.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda1(0));
                            pipMenuView.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_TAP_TO_REMOVE);
                            break;
                        }
                        break;
                    case 2:
                        PipMenuView pipMenuView2 = this.f$0;
                        pipMenuView2.getClass();
                        if (view.getAlpha() != 0.0f) {
                            Log.d("PipMenuView", "expandPip");
                            final PhonePipMenuController phonePipMenuController2 = pipMenuView2.mController;
                            Objects.requireNonNull(phonePipMenuController2);
                            pipMenuView2.hideMenu(1, new Runnable() { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda5
                                @Override // java.lang.Runnable
                                public final void run() {
                                    switch (i4) {
                                        case 0:
                                            phonePipMenuController2.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda1(3));
                                            break;
                                        default:
                                            phonePipMenuController2.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda1(2));
                                            break;
                                    }
                                }
                            }, false, true);
                            pipMenuView2.mIsExpanding = true;
                            pipMenuView2.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_EXPAND_TO_FULLSCREEN);
                            break;
                        }
                        break;
                    default:
                        PipMenuView pipMenuView3 = this.f$0;
                        if (pipMenuView3.mEnterSplitButton.getAlpha() != 0.0f) {
                            final PhonePipMenuController phonePipMenuController3 = pipMenuView3.mController;
                            Objects.requireNonNull(phonePipMenuController3);
                            pipMenuView3.hideMenu(1, new Runnable() { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda5
                                @Override // java.lang.Runnable
                                public final void run() {
                                    switch (i3) {
                                        case 0:
                                            phonePipMenuController3.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda1(3));
                                            break;
                                        default:
                                            phonePipMenuController3.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda1(2));
                                            break;
                                    }
                                }
                            }, false, true);
                            break;
                        }
                        break;
                }
            }
        });
        View findViewById5 = findViewById(R.id.dismiss);
        this.mDismissButton = findViewById5;
        findViewById5.setAlpha(0.0f);
        findViewById5.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda1
            public final /* synthetic */ PipMenuView f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r0v1, types: [com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda5] */
            /* JADX WARN: Type inference failed for: r0v3, types: [com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda5] */
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                final int i3 = 0;
                final int i4 = 1;
                switch (i2) {
                    case 0:
                        PipMenuView.m2752$r8$lambda$61Zm6ZTuSMPEly2Mxu4fUqPcDU(this.f$0, view);
                        break;
                    case 1:
                        PipMenuView pipMenuView = this.f$0;
                        pipMenuView.getClass();
                        Log.d("PipMenuView", "dismissPip");
                        if (pipMenuView.mMenuState != 0) {
                            pipMenuView.mController.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda1(0));
                            pipMenuView.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_TAP_TO_REMOVE);
                            break;
                        }
                        break;
                    case 2:
                        PipMenuView pipMenuView2 = this.f$0;
                        pipMenuView2.getClass();
                        if (view.getAlpha() != 0.0f) {
                            Log.d("PipMenuView", "expandPip");
                            final PhonePipMenuController phonePipMenuController2 = pipMenuView2.mController;
                            Objects.requireNonNull(phonePipMenuController2);
                            pipMenuView2.hideMenu(1, new Runnable() { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda5
                                @Override // java.lang.Runnable
                                public final void run() {
                                    switch (i4) {
                                        case 0:
                                            phonePipMenuController2.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda1(3));
                                            break;
                                        default:
                                            phonePipMenuController2.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda1(2));
                                            break;
                                    }
                                }
                            }, false, true);
                            pipMenuView2.mIsExpanding = true;
                            pipMenuView2.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_EXPAND_TO_FULLSCREEN);
                            break;
                        }
                        break;
                    default:
                        PipMenuView pipMenuView3 = this.f$0;
                        if (pipMenuView3.mEnterSplitButton.getAlpha() != 0.0f) {
                            final PhonePipMenuController phonePipMenuController3 = pipMenuView3.mController;
                            Objects.requireNonNull(phonePipMenuController3);
                            pipMenuView3.hideMenu(1, new Runnable() { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda5
                                @Override // java.lang.Runnable
                                public final void run() {
                                    switch (i3) {
                                        case 0:
                                            phonePipMenuController3.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda1(3));
                                            break;
                                        default:
                                            phonePipMenuController3.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda1(2));
                                            break;
                                    }
                                }
                            }, false, true);
                            break;
                        }
                        break;
                }
            }
        });
        View findViewById6 = findViewById(R.id.expand);
        this.mExpandButton = findViewById6;
        findViewById6.setAlpha(0.0f);
        final int i3 = 2;
        findViewById6.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda1
            public final /* synthetic */ PipMenuView f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r0v1, types: [com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda5] */
            /* JADX WARN: Type inference failed for: r0v3, types: [com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda5] */
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                final int i32 = 0;
                final int i4 = 1;
                switch (i3) {
                    case 0:
                        PipMenuView.m2752$r8$lambda$61Zm6ZTuSMPEly2Mxu4fUqPcDU(this.f$0, view);
                        break;
                    case 1:
                        PipMenuView pipMenuView = this.f$0;
                        pipMenuView.getClass();
                        Log.d("PipMenuView", "dismissPip");
                        if (pipMenuView.mMenuState != 0) {
                            pipMenuView.mController.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda1(0));
                            pipMenuView.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_TAP_TO_REMOVE);
                            break;
                        }
                        break;
                    case 2:
                        PipMenuView pipMenuView2 = this.f$0;
                        pipMenuView2.getClass();
                        if (view.getAlpha() != 0.0f) {
                            Log.d("PipMenuView", "expandPip");
                            final PhonePipMenuController phonePipMenuController2 = pipMenuView2.mController;
                            Objects.requireNonNull(phonePipMenuController2);
                            pipMenuView2.hideMenu(1, new Runnable() { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda5
                                @Override // java.lang.Runnable
                                public final void run() {
                                    switch (i4) {
                                        case 0:
                                            phonePipMenuController2.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda1(3));
                                            break;
                                        default:
                                            phonePipMenuController2.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda1(2));
                                            break;
                                    }
                                }
                            }, false, true);
                            pipMenuView2.mIsExpanding = true;
                            pipMenuView2.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_EXPAND_TO_FULLSCREEN);
                            break;
                        }
                        break;
                    default:
                        PipMenuView pipMenuView3 = this.f$0;
                        if (pipMenuView3.mEnterSplitButton.getAlpha() != 0.0f) {
                            final PhonePipMenuController phonePipMenuController3 = pipMenuView3.mController;
                            Objects.requireNonNull(phonePipMenuController3);
                            pipMenuView3.hideMenu(1, new Runnable() { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda5
                                @Override // java.lang.Runnable
                                public final void run() {
                                    switch (i32) {
                                        case 0:
                                            phonePipMenuController3.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda1(3));
                                            break;
                                        default:
                                            phonePipMenuController3.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda1(2));
                                            break;
                                    }
                                }
                            }, false, true);
                            break;
                        }
                        break;
                }
            }
        });
        View findViewById7 = findViewById(R.id.enter_split);
        this.mEnterSplitButton = findViewById7;
        this.mEnterSplitIconLR = ((FrameLayout) this).mContext.getDrawable(R.drawable.mw_pip_btn_splitview_lr_mtrl);
        this.mEnterSplitIconTB = ((FrameLayout) this).mContext.getDrawable(R.drawable.mw_pip_btn_splitview_tb_mtrl);
        updateEnterSplitButtonIcon();
        findViewById7.setAlpha(0.0f);
        final int i4 = 3;
        findViewById7.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda1
            public final /* synthetic */ PipMenuView f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r0v1, types: [com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda5] */
            /* JADX WARN: Type inference failed for: r0v3, types: [com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda5] */
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                final int i32 = 0;
                final int i42 = 1;
                switch (i4) {
                    case 0:
                        PipMenuView.m2752$r8$lambda$61Zm6ZTuSMPEly2Mxu4fUqPcDU(this.f$0, view);
                        break;
                    case 1:
                        PipMenuView pipMenuView = this.f$0;
                        pipMenuView.getClass();
                        Log.d("PipMenuView", "dismissPip");
                        if (pipMenuView.mMenuState != 0) {
                            pipMenuView.mController.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda1(0));
                            pipMenuView.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_TAP_TO_REMOVE);
                            break;
                        }
                        break;
                    case 2:
                        PipMenuView pipMenuView2 = this.f$0;
                        pipMenuView2.getClass();
                        if (view.getAlpha() != 0.0f) {
                            Log.d("PipMenuView", "expandPip");
                            final PhonePipMenuController phonePipMenuController2 = pipMenuView2.mController;
                            Objects.requireNonNull(phonePipMenuController2);
                            pipMenuView2.hideMenu(1, new Runnable() { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda5
                                @Override // java.lang.Runnable
                                public final void run() {
                                    switch (i42) {
                                        case 0:
                                            phonePipMenuController2.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda1(3));
                                            break;
                                        default:
                                            phonePipMenuController2.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda1(2));
                                            break;
                                    }
                                }
                            }, false, true);
                            pipMenuView2.mIsExpanding = true;
                            pipMenuView2.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_EXPAND_TO_FULLSCREEN);
                            break;
                        }
                        break;
                    default:
                        PipMenuView pipMenuView3 = this.f$0;
                        if (pipMenuView3.mEnterSplitButton.getAlpha() != 0.0f) {
                            final PhonePipMenuController phonePipMenuController3 = pipMenuView3.mController;
                            Objects.requireNonNull(phonePipMenuController3);
                            pipMenuView3.hideMenu(1, new Runnable() { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda5
                                @Override // java.lang.Runnable
                                public final void run() {
                                    switch (i32) {
                                        case 0:
                                            phonePipMenuController3.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda1(3));
                                            break;
                                        default:
                                            phonePipMenuController3.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda1(2));
                                            break;
                                    }
                                }
                            }, false, true);
                            break;
                        }
                        break;
                }
            }
        });
        findViewById7.setEnabled(false);
        if (!((FrameLayout) this).mContext.getResources().getBoolean(R.bool.config_pipEnableEnterSplitButton)) {
            findViewById7.setVisibility(8);
        }
        this.mPipActionSize = getResources().getDimensionPixelSize(R.dimen.pip_action_size);
        this.mPipActionSizePortWidth = getResources().getDimensionPixelSize(R.dimen.pip_action_size_port_width);
        this.mPipActionSizeLandWidth = getResources().getDimensionPixelSize(R.dimen.pip_action_size_land_width);
        this.mPipMenuPadding = getResources().getDimensionPixelSize(R.dimen.pip_menu_padding);
        this.mPipMenuPaddingTop = getResources().getDimensionPixelSize(R.dimen.pip_menu_padding_top);
        findViewById(R.id.resize_handle).setAlpha(0.0f);
        this.mActionsGroup = (LinearLayout) findViewById(R.id.actions_group);
        this.mBetweenActionPaddingLand = getResources().getDimensionPixelSize(R.dimen.pip_between_action_padding_land);
        PipMenuIconsAlgorithm pipMenuIconsAlgorithm = new PipMenuIconsAlgorithm(((FrameLayout) this).mContext);
        this.mPipMenuIconsAlgorithm = pipMenuIconsAlgorithm;
        View findViewById8 = findViewById(R.id.resize_handle);
        pipMenuIconsAlgorithm.mViewRoot = (ViewGroup) findViewById;
        pipMenuIconsAlgorithm.mTopEndContainer = (ViewGroup) findViewById3;
        pipMenuIconsAlgorithm.mDragHandle = findViewById8;
        pipMenuIconsAlgorithm.mEnterSplitButton = findViewById7;
        pipMenuIconsAlgorithm.mSettingsButton = findViewById4;
        pipMenuIconsAlgorithm.mDismissButton = findViewById5;
        this.mDismissFadeOutDurationMs = context.getResources().getInteger(R.integer.config_pipExitAnimationDuration);
        setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.wm.shell.pip.phone.PipMenuView.2
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, PipMenuView.this.getResources().getString(R.string.pip_menu_title)));
            }

            @Override // android.view.View.AccessibilityDelegate
            public final boolean performAccessibilityAction(View view, int i5, Bundle bundle) {
                if (i5 == 16) {
                    PipMenuView pipMenuView = PipMenuView.this;
                    int i6 = 1;
                    if (pipMenuView.mMenuState != 1) {
                        pipMenuView.mController.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda1(i6));
                    }
                }
                return super.performAccessibilityAction(view, i5, bundle);
            }
        });
    }

    @Override // android.view.View
    public final boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        if (this.mAllowMenuTimeout) {
            repostDelayedHide(2000);
        }
        return super.dispatchGenericMotionEvent(motionEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0 || motionEvent.getAction() == 1) {
            Log.d("PipMenuView", "dispatchTouchEvent action=" + motionEvent.getAction() + " mAllowTouches=" + this.mAllowTouches + " x=" + motionEvent.getRawX() + " y=" + motionEvent.getRawY());
        }
        if (!this.mAllowTouches) {
            return false;
        }
        if (this.mAllowMenuTimeout) {
            repostDelayedHide(2000);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public final void hideMenu(int i, final PipMenuView$$ExternalSyntheticLambda5 pipMenuView$$ExternalSyntheticLambda5, final boolean z, boolean z2) {
        long j;
        if (this.mMenuState != 0) {
            ((HandlerExecutor) this.mMainExecutor).removeCallbacks(this.mHideMenuRunnable);
            if (z) {
                notifyMenuStateChangeStart(0, z2, null);
            }
            this.mMenuContainerAnimator = new AnimatorSet();
            View view = this.mMenuContainer;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) View.ALPHA, view.getAlpha(), 0.0f);
            ofFloat.addUpdateListener(this.mMenuBgUpdateListener);
            View view2 = this.mSettingsButton;
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view2, (Property<View, Float>) View.ALPHA, view2.getAlpha(), 0.0f);
            View view3 = this.mDismissButton;
            ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view3, (Property<View, Float>) View.ALPHA, view3.getAlpha(), 0.0f);
            View view4 = this.mEnterSplitButton;
            ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(view4, (Property<View, Float>) View.ALPHA, view4.getAlpha(), 0.0f);
            Log.d("PipMenuView", "hideMenu() MenuState=" + this.mMenuState + " notifyMenuVisibility=" + z + " resize=" + z2 + " callers=" + Debug.getCallers(5));
            View view5 = this.mExpandButton;
            this.mMenuContainerAnimator.playTogether(ofFloat, ofFloat2, ObjectAnimator.ofFloat(view5, (Property<View, Float>) View.ALPHA, view5.getAlpha(), 0.0f), ofFloat3, ofFloat4);
            this.mMenuContainerAnimator.setInterpolator(Interpolators.ALPHA_OUT);
            AnimatorSet animatorSet = this.mMenuContainerAnimator;
            if (i == 0) {
                j = 0;
            } else if (i == 1) {
                j = 125;
            } else {
                if (i != 2) {
                    throw new IllegalStateException(AbstractC0000x2c234b15.m0m("Invalid animation type ", i));
                }
                j = this.mDismissFadeOutDurationMs;
            }
            animatorSet.setDuration(j);
            this.mMenuContainerAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.pip.phone.PipMenuView.4
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    PipMenuView.this.setVisibility(8);
                    if (z) {
                        PipMenuView.m2753$$Nest$mnotifyMenuStateChangeFinish(PipMenuView.this, 0);
                    }
                    Runnable runnable = pipMenuView$$ExternalSyntheticLambda5;
                    if (runnable != null) {
                        runnable.run();
                    }
                }
            });
            this.mMenuContainerAnimator.start();
        }
    }

    public final void hideMenu$1() {
        hideMenu(1, null, true, this.mDidLastShowMenuResize);
    }

    public final void notifyMenuStateChangeStart(final int i, final boolean z, final PipMenuView$$ExternalSyntheticLambda0 pipMenuView$$ExternalSyntheticLambda0) {
        PhonePipMenuController phonePipMenuController = this.mController;
        StringBuilder sb = new StringBuilder("onMenuStateChangeStart() mMenuState=");
        AutoFitGridLayoutManager$$ExternalSyntheticOutline0.m40m(sb, phonePipMenuController.mMenuState, " menuState=", i, " resize=");
        sb.append(z);
        sb.append(" callers=\n");
        sb.append(Debug.getCallers(5, "    "));
        Log.d("PhonePipMenuController", sb.toString());
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -576176134, 0, "%s: onMenuStateChangeStart() mMenuState=%s menuState=%s resize=%s callers=\n%s", "PhonePipMenuController", String.valueOf(phonePipMenuController.mMenuState), String.valueOf(i), String.valueOf(z), String.valueOf(Debug.getCallers(5, "    ")));
        }
        if (i != phonePipMenuController.mMenuState) {
            phonePipMenuController.mListeners.forEach(new Consumer() { // from class: com.android.wm.shell.pip.phone.PhonePipMenuController$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int rotation;
                    int i2 = i;
                    boolean z2 = z;
                    Runnable runnable = pipMenuView$$ExternalSyntheticLambda0;
                    PipTouchHandler pipTouchHandler = PipTouchHandler.this;
                    int i3 = pipTouchHandler.mMenuState;
                    if (i3 != i2 || z2) {
                        if (i2 == 1 && i3 != 1) {
                            if (z2) {
                                pipTouchHandler.animateToNormalSize(runnable);
                                return;
                            }
                            return;
                        }
                        if (i2 == 0 && i3 == 1) {
                            if (!z2 || pipTouchHandler.mPipResizeGestureHandler.mAllowGesture) {
                                pipTouchHandler.mSavedSnapFraction = -1.0f;
                                return;
                            }
                            if (pipTouchHandler.mDeferResizeToNormalBoundsUntilRotation == -1 && pipTouchHandler.mDisplayRotation != (rotation = pipTouchHandler.mContext.getDisplay().getRotation())) {
                                pipTouchHandler.mDeferResizeToNormalBoundsUntilRotation = rotation;
                            }
                            if (pipTouchHandler.mDeferResizeToNormalBoundsUntilRotation == -1) {
                                Rect rect = pipTouchHandler.mPipResizeGestureHandler.mUserResizeBounds;
                                PipBoundsState pipBoundsState = pipTouchHandler.mPipBoundsState;
                                if ((pipBoundsState.getBounds().width() == rect.width() && pipBoundsState.getBounds().height() == rect.height()) || (!pipBoundsState.mMotionBoundsState.mBoundsInMotion.isEmpty())) {
                                    Log.d("PipTouchHandler", "onPipMenuStateChangeStart: skip animateToUnexpandedState");
                                } else {
                                    pipTouchHandler.animateToUnexpandedState(pipTouchHandler.mPipResizeGestureHandler.mUserResizeBounds);
                                }
                            }
                        }
                    }
                }
            });
            PhonePipMenuController.C40481 c40481 = phonePipMenuController.mMediaActionListener;
            PipMediaController pipMediaController = phonePipMenuController.mMediaController;
            if (i == 1) {
                ArrayList arrayList = pipMediaController.mActionListeners;
                if (!arrayList.contains(c40481)) {
                    arrayList.add(c40481);
                    c40481.onMediaActionsChanged(pipMediaController.getMediaActions());
                }
            } else {
                pipMediaController.getClass();
                c40481.onMediaActionsChanged(Collections.emptyList());
                pipMediaController.mActionListeners.remove(c40481);
            }
            try {
                WindowManagerGlobal.getWindowSession().grantEmbeddedWindowFocus((IWindow) null, phonePipMenuController.mSystemWindows.getFocusGrantToken(phonePipMenuController.mPipMenuView), i != 0);
            } catch (RemoteException e) {
                Log.e("PhonePipMenuController", "Unable to update focus as menu appears/disappears", e);
                if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                    ShellProtoLogImpl.m230e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1614285461, 0, "%s: Unable to update focus as menu appears/disappears, %s", "PhonePipMenuController", String.valueOf(e));
                }
            }
            phonePipMenuController.mIsPipMenuFocused = i != 0;
        }
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public final boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i != 111) {
            return super.onKeyUp(i, keyEvent);
        }
        hideMenu$1();
        return true;
    }

    public final void repostDelayedHide(int i) {
        int recommendedTimeoutMillis = this.mAccessibilityManager.getRecommendedTimeoutMillis(i, 5);
        ((HandlerExecutor) this.mMainExecutor).removeCallbacks(this.mHideMenuRunnable);
        ((HandlerExecutor) this.mMainExecutor).executeDelayed(recommendedTimeoutMillis, this.mHideMenuRunnable);
    }

    public final void setActions(Rect rect, List list, RemoteAction remoteAction) {
        ((ArrayList) this.mActions).clear();
        if (list != null && !list.isEmpty()) {
            ((ArrayList) this.mActions).addAll(list);
        }
        this.mCloseAction = remoteAction;
        int i = this.mMenuState;
        if (i == 1) {
            updateActionViews(i, rect);
        }
        if (((ArrayList) this.mActions).isEmpty()) {
            Log.d("PipMenuView", "setActions, mActions=" + this.mActions + " caller=" + Debug.getCallers(7));
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public final boolean shouldDelayChildPressedState() {
        return true;
    }

    public final void showMenu(final int i, Rect rect, final boolean z, boolean z2, boolean z3, boolean z4) {
        this.mAllowMenuTimeout = z;
        this.mDidLastShowMenuResize = z2;
        boolean z5 = ((FrameLayout) this).mContext.getResources().getBoolean(R.bool.config_pipEnableEnterSplitButton);
        if (this.mMenuState == i) {
            if (z) {
                repostDelayedHide(2000);
                return;
            }
            return;
        }
        if (rect.width() / rect.height() <= 1.0f) {
            this.mSettingsButton.getLayoutParams().width = this.mPipActionSize;
            this.mSettingsButton.setBackground(((FrameLayout) this).mContext.getDrawable(R.drawable.mw_pip_btn_settings_inset_ripple_port));
            View view = this.mSettingsButton;
            int i2 = this.mPipMenuPadding;
            int i3 = this.mPipMenuPaddingTop;
            view.setPadding(i2, i3, i2 / 2, i3);
            this.mExpandButton.getLayoutParams().width = this.mPipActionSizePortWidth;
            this.mExpandButton.setBackground(((FrameLayout) this).mContext.getDrawable(R.drawable.mw_pip_btn_expand_inset_ripple_port));
            View view2 = this.mExpandButton;
            int i4 = this.mPipMenuPadding / 2;
            int i5 = this.mPipMenuPaddingTop;
            view2.setPadding(i4, i5, i4, i5);
        } else {
            this.mSettingsButton.getLayoutParams().width = this.mPipActionSizeLandWidth;
            this.mSettingsButton.setBackground(((FrameLayout) this).mContext.getDrawable(R.drawable.mw_common_button_ripple));
            View view3 = this.mSettingsButton;
            int i6 = this.mPipMenuPadding;
            int i7 = this.mPipMenuPaddingTop;
            view3.setPadding(i6, i7, i6, i7);
            this.mExpandButton.getLayoutParams().width = this.mPipActionSize;
            this.mExpandButton.setBackground(((FrameLayout) this).mContext.getDrawable(R.drawable.mw_pip_btn_expand_inset_ripple));
            View view4 = this.mExpandButton;
            int i8 = this.mPipMenuPadding;
            int i9 = this.mPipMenuPaddingTop;
            view4.setPadding(i8, i9, i8 / 2, i9);
        }
        this.mTopEndContainer.requestLayout();
        updateEnterSplitButtonIcon();
        this.mEnterSplitButton.setEnabled(z4);
        int i10 = 1;
        this.mAllowTouches = !(z2 && (this.mMenuState == 1 || i == 1));
        ((HandlerExecutor) this.mMainExecutor).removeCallbacks(this.mHideMenuRunnable);
        AnimatorSet animatorSet = this.mMenuContainerAnimator;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        this.mMenuContainerAnimator = new AnimatorSet();
        View view5 = this.mMenuContainer;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view5, (Property<View, Float>) View.ALPHA, view5.getAlpha(), 1.0f);
        ofFloat.addUpdateListener(this.mMenuBgUpdateListener);
        View view6 = this.mSettingsButton;
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view6, (Property<View, Float>) View.ALPHA, view6.getAlpha(), 1.0f);
        View view7 = this.mDismissButton;
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view7, (Property<View, Float>) View.ALPHA, view7.getAlpha(), 1.0f);
        View view8 = this.mEnterSplitButton;
        Property property = View.ALPHA;
        float[] fArr = new float[2];
        fArr[0] = view8.getAlpha();
        fArr[1] = (z5 && this.mFocusedTaskAllowSplitScreen && z4) ? 1.0f : 0.0f;
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(view8, (Property<View, Float>) property, fArr);
        if (i == 1) {
            View view9 = this.mExpandButton;
            this.mMenuContainerAnimator.playTogether(ofFloat, ofFloat2, ObjectAnimator.ofFloat(view9, (Property<View, Float>) View.ALPHA, view9.getAlpha(), 1.0f), ofFloat3, ofFloat4);
        } else {
            View view10 = this.mExpandButton;
            this.mMenuContainerAnimator.playTogether(ofFloat2, ObjectAnimator.ofFloat(view10, (Property<View, Float>) View.ALPHA, view10.getAlpha(), 1.0f), ofFloat3, ofFloat4);
        }
        this.mMenuContainerAnimator.setInterpolator(Interpolators.ALPHA_IN);
        this.mMenuContainerAnimator.setDuration(125L);
        this.mMenuContainerAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.pip.phone.PipMenuView.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                PipMenuView.this.mAllowTouches = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                PipMenuView pipMenuView = PipMenuView.this;
                pipMenuView.mAllowTouches = true;
                PipMenuView.m2753$$Nest$mnotifyMenuStateChangeFinish(pipMenuView, i);
                if (z) {
                    PipMenuView.this.repostDelayedHide(3000);
                }
            }
        });
        if (z3) {
            notifyMenuStateChangeStart(i, z2, new PipMenuView$$ExternalSyntheticLambda0(this, i10));
        } else {
            notifyMenuStateChangeStart(i, z2, null);
            setVisibility(0);
            this.mMenuContainerAnimator.start();
        }
        updateActionViews(i, rect);
    }

    public final void updateActionViews(int i, Rect rect) {
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.expand_container);
        ViewGroup viewGroup2 = (ViewGroup) findViewById(R.id.actions_container);
        viewGroup2.setOnTouchListener(new PipMenuView$$ExternalSyntheticLambda2());
        viewGroup.setVisibility(i == 1 ? 0 : 4);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
        if (((ArrayList) this.mActions).isEmpty() || i == 0) {
            viewGroup2.setVisibility(4);
            layoutParams.topMargin = 0;
            layoutParams.bottomMargin = 0;
        } else {
            viewGroup2.setVisibility(0);
            if (this.mActionsGroup != null) {
                LayoutInflater from = LayoutInflater.from(((FrameLayout) this).mContext);
                while (this.mActionsGroup.getChildCount() < ((ArrayList) this.mActions).size()) {
                    this.mActionsGroup.addView((PipMenuActionView) from.inflate(R.layout.pip_menu_action, (ViewGroup) this.mActionsGroup, false));
                }
                int i2 = 0;
                while (true) {
                    int i3 = 8;
                    if (i2 >= this.mActionsGroup.getChildCount()) {
                        break;
                    }
                    View childAt = this.mActionsGroup.getChildAt(i2);
                    if (i2 < ((ArrayList) this.mActions).size()) {
                        i3 = 0;
                    }
                    childAt.setVisibility(i3);
                    i2++;
                }
                boolean z = rect.width() > rect.height();
                int i4 = 0;
                while (i4 < ((ArrayList) this.mActions).size()) {
                    final RemoteAction remoteAction = (RemoteAction) ((ArrayList) this.mActions).get(i4);
                    final PipMenuActionView pipMenuActionView = (PipMenuActionView) this.mActionsGroup.getChildAt(i4);
                    RemoteAction remoteAction2 = this.mCloseAction;
                    final boolean z2 = remoteAction2 != null && Objects.equals(remoteAction2.getActionIntent(), remoteAction.getActionIntent());
                    int type = remoteAction.getIcon().getType();
                    if (type == 4 || type == 6) {
                        pipMenuActionView.mImageView.setImageDrawable(null);
                    } else {
                        remoteAction.getIcon().loadDrawableAsync(((FrameLayout) this).mContext, new Icon.OnDrawableLoadedListener() { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda3
                            @Override // android.graphics.drawable.Icon.OnDrawableLoadedListener
                            public final void onDrawableLoaded(Drawable drawable) {
                                PipMenuActionView pipMenuActionView2 = PipMenuActionView.this;
                                if (drawable != null) {
                                    drawable.setTint(-1);
                                    pipMenuActionView2.mImageView.setImageDrawable(drawable);
                                }
                            }
                        }, this.mMainHandler);
                    }
                    pipMenuActionView.mCustomCloseBackground.setVisibility(z2 ? 0 : 8);
                    pipMenuActionView.setContentDescription(remoteAction.getContentDescription());
                    if (remoteAction.isEnabled()) {
                        pipMenuActionView.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda4
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                PipMenuView pipMenuView = PipMenuView.this;
                                RemoteAction remoteAction3 = remoteAction;
                                boolean z3 = z2;
                                pipMenuView.getClass();
                                try {
                                    remoteAction3.getActionIntent().send();
                                } catch (PendingIntent.CanceledException e) {
                                    if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                                        ShellProtoLogImpl.m233w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1636860907, 0, "%s: Failed to send action, %s", "PipMenuView", String.valueOf(e));
                                    }
                                }
                                if (z3) {
                                    pipMenuView.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_CUSTOM_CLOSE);
                                    pipMenuView.mAllowTouches = false;
                                    ((HandlerExecutor) pipMenuView.mMainExecutor).executeDelayed(pipMenuView.mPipForceCloseDelay, new PipMenuView$$ExternalSyntheticLambda0(pipMenuView, 2));
                                }
                            }
                        });
                    }
                    pipMenuActionView.setEnabled(remoteAction.isEnabled());
                    pipMenuActionView.setAlpha(remoteAction.isEnabled() ? 1.0f : 0.54f);
                    ((LinearLayout.LayoutParams) pipMenuActionView.getLayoutParams()).leftMargin = (!z || i4 <= 0) ? 0 : this.mBetweenActionPaddingLand;
                    i4++;
                }
            }
        }
        viewGroup.requestLayout();
    }

    public final void updateEnterSplitButtonIcon() {
        if (!this.mSplitScreenControllerOptional.isPresent() || this.mEnterSplitButton == null || this.mEnterSplitIconLR == null || this.mEnterSplitIconTB == null) {
            return;
        }
        SplitScreenController splitScreenController = (SplitScreenController) this.mSplitScreenControllerOptional.get();
        splitScreenController.getClass();
        boolean z = false;
        if (!MultiWindowManager.getInstance().supportMultiSplitAppMinimumSize() || MultiWindowUtils.isInSubDisplay(((FrameLayout) this).mContext) ? ((FrameLayout) this).mContext.getResources().getConfiguration().orientation == 1 : !(!splitScreenController.isSplitScreenVisible() || splitScreenController.getSplitDivision() != 1)) {
            z = true;
        }
        if (z) {
            ((ImageButton) this.mEnterSplitButton).setImageDrawable(this.mEnterSplitIconTB);
        } else {
            ((ImageButton) this.mEnterSplitButton).setImageDrawable(this.mEnterSplitIconLR);
        }
    }
}
