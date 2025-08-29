package com.android.wm.shell.pip.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.RemoteAction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
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
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SystemWindows;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipMediaController;
import com.android.wm.shell.common.pip.PipUiEventLogger;
import com.android.wm.shell.common.pip.PipUtils;
import com.android.wm.shell.pip.PipTaskOrganizer;
import com.android.wm.shell.pip.phone.PhonePipMenuController;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.animation.Interpolators;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import kotlin.collections.EmptyList;

/* loaded from: classes3.dex */
public class PipMenuView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AccessibilityManager mAccessibilityManager;
    public final List mActions;
    public final LinearLayout mActionsGroup;
    public boolean mAllowMenuTimeout;
    public boolean mAllowTouches;
    public final Drawable mBackgroundDrawable;
    public final int mBetweenActionPaddingLand;
    public RemoteAction mCloseAction;
    public final Context mContextForUser;
    public final PhonePipMenuController mController;
    public boolean mDidLastShowMenuResize;
    public final View mDismissButton;
    public final int mDismissFadeOutDurationMs;
    public final View mEnterSplitButton;
    public final Drawable mEnterSplitIconLR;
    public final Drawable mEnterSplitIconTB;
    public final View mExpandButton;
    public final PipMenuView$$ExternalSyntheticLambda0 mHideMenuRunnable;
    public boolean mIsExpanding;
    public final ShellExecutor mMainExecutor;
    public final Handler mMainHandler;
    public final AnonymousClass1 mMenuBgUpdateListener;
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

    /* renamed from: $r8$lambda$kh7O-2Rmd0Daiu_MbPcAw5t76mc, reason: not valid java name */
    public static void m3271$r8$lambda$kh7O2Rmd0Daiu_MbPcAw5t76mc(PipMenuView pipMenuView, View view) {
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
    public static void m3272$$Nest$mnotifyMenuStateChangeFinish(PipMenuView pipMenuView, final int i) {
        StringBuilder sb = new StringBuilder("notifyMenuStateChangeFinish: ");
        ViewPager$$ExternalSyntheticOutline0.m(sb, pipMenuView.mMenuState, "->", i, ", Callers=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(5, "PipMenuView", sb);
        pipMenuView.mMenuState = i;
        PhonePipMenuController phonePipMenuController = pipMenuView.mController;
        if (i != phonePipMenuController.mMenuState) {
            phonePipMenuController.mListeners.forEach(new Consumer() { // from class: com.android.wm.shell.pip.phone.PhonePipMenuController$$ExternalSyntheticLambda1
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
        if (i != 0) {
            systemWindows.setShellRootAccessibilityWindow(phonePipMenuController.mPipMenuView);
        } else {
            systemWindows.setShellRootAccessibilityWindow(null);
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.wm.shell.pip.phone.PipMenuView$1] */
    public PipMenuView(Context context, PhonePipMenuController phonePipMenuController, ShellExecutor shellExecutor, Handler handler, PipUiEventLogger pipUiEventLogger, Optional<SplitScreenController> optional) {
        super(context, null, 0);
        this.mAllowMenuTimeout = true;
        this.mAllowTouches = true;
        this.mActions = new ArrayList();
        this.mMenuBgUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.pip.phone.PipMenuView.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                PipMenuView.this.mBackgroundDrawable.setAlpha((int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * 0.54f * 255.0f));
            }
        };
        this.mHideMenuRunnable = new PipMenuView$$ExternalSyntheticLambda0(this, 0);
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
        if (CoreRune.MW_PIP_DISABLE_ROUNDED_CORNER) {
            this.mBackgroundDrawable = ((FrameLayout) this).mContext.getDrawable(R.drawable.pip_menu_background_no_corner);
        } else {
            this.mBackgroundDrawable = ((FrameLayout) this).mContext.getDrawable(R.drawable.pip_menu_background);
        }
        this.mBackgroundDrawable.setAlpha(0);
        View viewFindViewById = findViewById(R.id.background);
        this.mViewRoot = viewFindViewById;
        viewFindViewById.setBackground(this.mBackgroundDrawable);
        View viewFindViewById2 = findViewById(R.id.menu_container);
        this.mMenuContainer = viewFindViewById2;
        viewFindViewById2.setAlpha(0.0f);
        View viewFindViewById3 = findViewById(R.id.top_end_container);
        this.mTopEndContainer = viewFindViewById3;
        View viewFindViewById4 = findViewById(R.id.settings);
        this.mSettingsButton = viewFindViewById4;
        viewFindViewById4.setAlpha(0.0f);
        final int i = 0;
        viewFindViewById4.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda1
            public final /* synthetic */ PipMenuView f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                final int i2 = 1;
                final int i3 = 0;
                int i4 = i;
                PipMenuView pipMenuView = this.f$0;
                switch (i4) {
                    case 0:
                        PipMenuView.m3271$r8$lambda$kh7O2Rmd0Daiu_MbPcAw5t76mc(pipMenuView, view);
                        break;
                    case 1:
                        int i5 = PipMenuView.$r8$clinit;
                        Log.d("PipMenuView", "dismissPip");
                        if (pipMenuView.mMenuState != 0) {
                            pipMenuView.mController.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda2(0));
                            pipMenuView.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_TAP_TO_REMOVE);
                            break;
                        }
                        break;
                    case 2:
                        int i6 = PipMenuView.$r8$clinit;
                        if (view.getAlpha() != 0.0f) {
                            Log.d("PipMenuView", "expandPip");
                            final PhonePipMenuController phonePipMenuController2 = pipMenuView.mController;
                            Objects.requireNonNull(phonePipMenuController2);
                            pipMenuView.hideMenu(new Runnable() { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda10
                                @Override // java.lang.Runnable
                                public final void run() {
                                    int i7 = i3;
                                    PhonePipMenuController phonePipMenuController3 = phonePipMenuController2;
                                    switch (i7) {
                                        case 0:
                                            phonePipMenuController3.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda2(3));
                                            break;
                                        default:
                                            phonePipMenuController3.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda2(2));
                                            break;
                                    }
                                }
                            }, false, true, 1);
                            pipMenuView.mIsExpanding = true;
                            pipMenuView.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_EXPAND_TO_FULLSCREEN);
                            break;
                        }
                        break;
                    default:
                        if (pipMenuView.mEnterSplitButton.getAlpha() != 0.0f) {
                            final PhonePipMenuController phonePipMenuController3 = pipMenuView.mController;
                            Objects.requireNonNull(phonePipMenuController3);
                            pipMenuView.hideMenu(new Runnable() { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda10
                                @Override // java.lang.Runnable
                                public final void run() {
                                    int i7 = i2;
                                    PhonePipMenuController phonePipMenuController32 = phonePipMenuController3;
                                    switch (i7) {
                                        case 0:
                                            phonePipMenuController32.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda2(3));
                                            break;
                                        default:
                                            phonePipMenuController32.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda2(2));
                                            break;
                                    }
                                }
                            }, false, true, 1);
                            break;
                        }
                        break;
                }
            }
        });
        View viewFindViewById5 = findViewById(R.id.dismiss);
        this.mDismissButton = viewFindViewById5;
        viewFindViewById5.setAlpha(0.0f);
        final int i2 = 1;
        viewFindViewById5.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda1
            public final /* synthetic */ PipMenuView f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                final int i22 = 1;
                final int i3 = 0;
                int i4 = i2;
                PipMenuView pipMenuView = this.f$0;
                switch (i4) {
                    case 0:
                        PipMenuView.m3271$r8$lambda$kh7O2Rmd0Daiu_MbPcAw5t76mc(pipMenuView, view);
                        break;
                    case 1:
                        int i5 = PipMenuView.$r8$clinit;
                        Log.d("PipMenuView", "dismissPip");
                        if (pipMenuView.mMenuState != 0) {
                            pipMenuView.mController.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda2(0));
                            pipMenuView.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_TAP_TO_REMOVE);
                            break;
                        }
                        break;
                    case 2:
                        int i6 = PipMenuView.$r8$clinit;
                        if (view.getAlpha() != 0.0f) {
                            Log.d("PipMenuView", "expandPip");
                            final PhonePipMenuController phonePipMenuController2 = pipMenuView.mController;
                            Objects.requireNonNull(phonePipMenuController2);
                            pipMenuView.hideMenu(new Runnable() { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda10
                                @Override // java.lang.Runnable
                                public final void run() {
                                    int i7 = i3;
                                    PhonePipMenuController phonePipMenuController32 = phonePipMenuController2;
                                    switch (i7) {
                                        case 0:
                                            phonePipMenuController32.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda2(3));
                                            break;
                                        default:
                                            phonePipMenuController32.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda2(2));
                                            break;
                                    }
                                }
                            }, false, true, 1);
                            pipMenuView.mIsExpanding = true;
                            pipMenuView.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_EXPAND_TO_FULLSCREEN);
                            break;
                        }
                        break;
                    default:
                        if (pipMenuView.mEnterSplitButton.getAlpha() != 0.0f) {
                            final PhonePipMenuController phonePipMenuController3 = pipMenuView.mController;
                            Objects.requireNonNull(phonePipMenuController3);
                            pipMenuView.hideMenu(new Runnable() { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda10
                                @Override // java.lang.Runnable
                                public final void run() {
                                    int i7 = i22;
                                    PhonePipMenuController phonePipMenuController32 = phonePipMenuController3;
                                    switch (i7) {
                                        case 0:
                                            phonePipMenuController32.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda2(3));
                                            break;
                                        default:
                                            phonePipMenuController32.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda2(2));
                                            break;
                                    }
                                }
                            }, false, true, 1);
                            break;
                        }
                        break;
                }
            }
        });
        View viewFindViewById6 = findViewById(R.id.expand);
        this.mExpandButton = viewFindViewById6;
        viewFindViewById6.setAlpha(0.0f);
        final int i3 = 2;
        viewFindViewById6.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda1
            public final /* synthetic */ PipMenuView f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                final int i22 = 1;
                final int i32 = 0;
                int i4 = i3;
                PipMenuView pipMenuView = this.f$0;
                switch (i4) {
                    case 0:
                        PipMenuView.m3271$r8$lambda$kh7O2Rmd0Daiu_MbPcAw5t76mc(pipMenuView, view);
                        break;
                    case 1:
                        int i5 = PipMenuView.$r8$clinit;
                        Log.d("PipMenuView", "dismissPip");
                        if (pipMenuView.mMenuState != 0) {
                            pipMenuView.mController.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda2(0));
                            pipMenuView.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_TAP_TO_REMOVE);
                            break;
                        }
                        break;
                    case 2:
                        int i6 = PipMenuView.$r8$clinit;
                        if (view.getAlpha() != 0.0f) {
                            Log.d("PipMenuView", "expandPip");
                            final PhonePipMenuController phonePipMenuController2 = pipMenuView.mController;
                            Objects.requireNonNull(phonePipMenuController2);
                            pipMenuView.hideMenu(new Runnable() { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda10
                                @Override // java.lang.Runnable
                                public final void run() {
                                    int i7 = i32;
                                    PhonePipMenuController phonePipMenuController32 = phonePipMenuController2;
                                    switch (i7) {
                                        case 0:
                                            phonePipMenuController32.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda2(3));
                                            break;
                                        default:
                                            phonePipMenuController32.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda2(2));
                                            break;
                                    }
                                }
                            }, false, true, 1);
                            pipMenuView.mIsExpanding = true;
                            pipMenuView.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_EXPAND_TO_FULLSCREEN);
                            break;
                        }
                        break;
                    default:
                        if (pipMenuView.mEnterSplitButton.getAlpha() != 0.0f) {
                            final PhonePipMenuController phonePipMenuController3 = pipMenuView.mController;
                            Objects.requireNonNull(phonePipMenuController3);
                            pipMenuView.hideMenu(new Runnable() { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda10
                                @Override // java.lang.Runnable
                                public final void run() {
                                    int i7 = i22;
                                    PhonePipMenuController phonePipMenuController32 = phonePipMenuController3;
                                    switch (i7) {
                                        case 0:
                                            phonePipMenuController32.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda2(3));
                                            break;
                                        default:
                                            phonePipMenuController32.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda2(2));
                                            break;
                                    }
                                }
                            }, false, true, 1);
                            break;
                        }
                        break;
                }
            }
        });
        View viewFindViewById7 = findViewById(R.id.enter_split);
        this.mEnterSplitButton = viewFindViewById7;
        this.mEnterSplitIconLR = ((FrameLayout) this).mContext.getDrawable(R.drawable.mw_pip_btn_splitview_lr_mtrl);
        this.mEnterSplitIconTB = ((FrameLayout) this).mContext.getDrawable(R.drawable.mw_pip_btn_splitview_tb_mtrl);
        updateEnterSplitButtonIcon();
        viewFindViewById7.setAlpha(0.0f);
        final int i4 = 3;
        viewFindViewById7.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda1
            public final /* synthetic */ PipMenuView f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                final int i22 = 1;
                final int i32 = 0;
                int i42 = i4;
                PipMenuView pipMenuView = this.f$0;
                switch (i42) {
                    case 0:
                        PipMenuView.m3271$r8$lambda$kh7O2Rmd0Daiu_MbPcAw5t76mc(pipMenuView, view);
                        break;
                    case 1:
                        int i5 = PipMenuView.$r8$clinit;
                        Log.d("PipMenuView", "dismissPip");
                        if (pipMenuView.mMenuState != 0) {
                            pipMenuView.mController.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda2(0));
                            pipMenuView.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_TAP_TO_REMOVE);
                            break;
                        }
                        break;
                    case 2:
                        int i6 = PipMenuView.$r8$clinit;
                        if (view.getAlpha() != 0.0f) {
                            Log.d("PipMenuView", "expandPip");
                            final PhonePipMenuController phonePipMenuController2 = pipMenuView.mController;
                            Objects.requireNonNull(phonePipMenuController2);
                            pipMenuView.hideMenu(new Runnable() { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda10
                                @Override // java.lang.Runnable
                                public final void run() {
                                    int i7 = i32;
                                    PhonePipMenuController phonePipMenuController32 = phonePipMenuController2;
                                    switch (i7) {
                                        case 0:
                                            phonePipMenuController32.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda2(3));
                                            break;
                                        default:
                                            phonePipMenuController32.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda2(2));
                                            break;
                                    }
                                }
                            }, false, true, 1);
                            pipMenuView.mIsExpanding = true;
                            pipMenuView.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_EXPAND_TO_FULLSCREEN);
                            break;
                        }
                        break;
                    default:
                        if (pipMenuView.mEnterSplitButton.getAlpha() != 0.0f) {
                            final PhonePipMenuController phonePipMenuController3 = pipMenuView.mController;
                            Objects.requireNonNull(phonePipMenuController3);
                            pipMenuView.hideMenu(new Runnable() { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda10
                                @Override // java.lang.Runnable
                                public final void run() {
                                    int i7 = i22;
                                    PhonePipMenuController phonePipMenuController32 = phonePipMenuController3;
                                    switch (i7) {
                                        case 0:
                                            phonePipMenuController32.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda2(3));
                                            break;
                                        default:
                                            phonePipMenuController32.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda2(2));
                                            break;
                                    }
                                }
                            }, false, true, 1);
                            break;
                        }
                        break;
                }
            }
        });
        viewFindViewById7.setEnabled(false);
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
        View viewFindViewById8 = findViewById(R.id.resize_handle);
        pipMenuIconsAlgorithm.mViewRoot = (ViewGroup) viewFindViewById;
        pipMenuIconsAlgorithm.mTopEndContainer = (ViewGroup) viewFindViewById3;
        pipMenuIconsAlgorithm.mDragHandle = viewFindViewById8;
        pipMenuIconsAlgorithm.mEnterSplitButton = viewFindViewById7;
        pipMenuIconsAlgorithm.mSettingsButton = viewFindViewById4;
        pipMenuIconsAlgorithm.mDismissButton = viewFindViewById5;
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
                    if (pipMenuView.mMenuState != 1) {
                        pipMenuView.mController.mListeners.forEach(new PhonePipMenuController$$ExternalSyntheticLambda2(1));
                    }
                }
                return super.performAccessibilityAction(view, i5, bundle);
            }
        });
        int currentUser = ActivityManager.getCurrentUser();
        if (((FrameLayout) this).mContext.getUserId() == currentUser) {
            this.mContextForUser = ((FrameLayout) this).mContext;
            return;
        }
        try {
            Context context2 = ((FrameLayout) this).mContext;
            this.mContextForUser = context2.createPackageContextAsUser(context2.getPackageName(), 4, new UserHandle(currentUser));
        } catch (PackageManager.NameNotFoundException e) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[3]) {
                ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 6852099901306191849L, 20, "PipMenuView", Long.valueOf(((FrameLayout) this).mContext.getUserId()), Long.valueOf(currentUser), String.valueOf(e));
            }
            this.mContextForUser = ((FrameLayout) this).mContext;
        }
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

    public final void hideMenu(final Runnable runnable, final boolean z, boolean z2, int i) {
        long j;
        if (this.mMenuState != 0) {
            ((HandlerExecutor) this.mMainExecutor).removeCallbacks(this.mHideMenuRunnable);
            if (z) {
                notifyMenuStateChangeStart(0, z2, null);
            }
            this.mMenuContainerAnimator = new AnimatorSet();
            View view = this.mMenuContainer;
            Property property = View.ALPHA;
            ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, (Property<View, Float>) property, view.getAlpha(), 0.0f);
            objectAnimatorOfFloat.addUpdateListener(this.mMenuBgUpdateListener);
            View view2 = this.mSettingsButton;
            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view2, (Property<View, Float>) property, view2.getAlpha(), 0.0f);
            View view3 = this.mDismissButton;
            ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(view3, (Property<View, Float>) property, view3.getAlpha(), 0.0f);
            Log.d("PipMenuView", "hideMenu() MenuState=" + this.mMenuState + " notifyMenuVisibility=" + z + " resize=" + z2 + " callers=" + Debug.getCallers(5));
            View view4 = this.mExpandButton;
            ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(view4, (Property<View, Float>) property, view4.getAlpha(), 0.0f);
            View view5 = this.mEnterSplitButton;
            if (view5 != null) {
                this.mMenuContainerAnimator.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2, objectAnimatorOfFloat4, objectAnimatorOfFloat3, ObjectAnimator.ofFloat(view5, (Property<View, Float>) property, view5.getAlpha(), 0.0f));
            } else {
                this.mMenuContainerAnimator.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2, objectAnimatorOfFloat4, objectAnimatorOfFloat3);
            }
            this.mMenuContainerAnimator.setInterpolator(Interpolators.ALPHA_OUT);
            AnimatorSet animatorSet = this.mMenuContainerAnimator;
            if (i == 0) {
                j = 0;
            } else if (i == 1) {
                j = 125;
            } else {
                if (i != 2) {
                    throw new IllegalStateException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Invalid animation type "));
                }
                j = this.mDismissFadeOutDurationMs;
            }
            animatorSet.setDuration(j);
            this.mMenuContainerAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.pip.phone.PipMenuView.4
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    PipMenuView.this.setVisibility(8);
                    if (z) {
                        PipMenuView.m3272$$Nest$mnotifyMenuStateChangeFinish(PipMenuView.this, 0);
                    }
                    Runnable runnable2 = runnable;
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }
            });
            this.mMenuContainerAnimator.start();
        }
    }

    public final void hideMenu$1() {
        hideMenu(null, true, this.mDidLastShowMenuResize, 1);
    }

    public final void notifyMenuStateChangeStart(final int i, final boolean z, final PipMenuView$$ExternalSyntheticLambda0 pipMenuView$$ExternalSyntheticLambda0) {
        PhonePipMenuController phonePipMenuController = this.mController;
        StringBuilder sb = new StringBuilder("onMenuStateChangeStart() mMenuState=");
        ViewPager$$ExternalSyntheticOutline0.m(sb, phonePipMenuController.mMenuState, " menuState=", i, " resize=");
        sb.append(z);
        sb.append(" callers=\n");
        sb.append(Debug.getCallers(5, "    "));
        Log.d("PhonePipMenuController", sb.toString());
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -5553439997639442530L, 0, "PhonePipMenuController", String.valueOf(phonePipMenuController.mMenuState), String.valueOf(i), String.valueOf(z), String.valueOf(Debug.getCallers(5, "    ")));
        }
        if (i != phonePipMenuController.mMenuState) {
            phonePipMenuController.mListeners.forEach(new Consumer() { // from class: com.android.wm.shell.pip.phone.PhonePipMenuController$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) throws Resources.NotFoundException {
                    int rotation;
                    int i2 = i;
                    boolean z2 = z;
                    PipMenuView$$ExternalSyntheticLambda0 pipMenuView$$ExternalSyntheticLambda02 = pipMenuView$$ExternalSyntheticLambda0;
                    PipTouchHandler pipTouchHandler = PipTouchHandler.this;
                    int i3 = pipTouchHandler.mMenuState;
                    if (i3 != i2 || z2) {
                        if (i2 == 1 && i3 != 1) {
                            if (z2) {
                                pipTouchHandler.animateToNormalSize(pipMenuView$$ExternalSyntheticLambda02);
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
                                if ((pipBoundsState.getBounds().width() == rect.width() && pipBoundsState.getBounds().height() == rect.height()) || pipBoundsState.mMotionBoundsState.isInMotion()) {
                                    Log.d("PipTouchHandler", "onPipMenuStateChangeStart: skip animateToUnexpandedState");
                                } else {
                                    pipTouchHandler.animateToUnexpandedState(pipTouchHandler.mPipResizeGestureHandler.mUserResizeBounds);
                                }
                            }
                        }
                    }
                }
            });
            PhonePipMenuController.AnonymousClass1 anonymousClass1 = phonePipMenuController.mMediaActionListener;
            PipMediaController pipMediaController = phonePipMenuController.mMediaController;
            if (i != 1) {
                pipMediaController.getClass();
                anonymousClass1.onMediaActionsChanged(EmptyList.INSTANCE);
                pipMediaController.mActionListeners.remove(anonymousClass1);
            } else if (!pipMediaController.mActionListeners.contains(anonymousClass1)) {
                pipMediaController.mActionListeners.add(anonymousClass1);
                anonymousClass1.onMediaActionsChanged(pipMediaController.getMediaActions());
            }
            try {
                WindowManagerGlobal.getWindowSession().grantEmbeddedWindowFocus((IWindow) null, phonePipMenuController.mSystemWindows.getFocusGrantToken(phonePipMenuController.mPipMenuView), (phonePipMenuController.mIsImeVisible || i == 0) ? false : true);
            } catch (RemoteException e) {
                Log.e("PhonePipMenuController", "Unable to update focus as menu appears/disappears", e);
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[4]) {
                    ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 961883302679884611L, 0, "PhonePipMenuController", String.valueOf(e));
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
        ((HandlerExecutor) this.mMainExecutor).executeDelayed(this.mHideMenuRunnable, recommendedTimeoutMillis);
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

    public final void showMenu(Rect rect, boolean z, boolean z2, boolean z3) {
        int i = 2;
        final int i2 = 1;
        char c = 1;
        this.mAllowMenuTimeout = true;
        this.mDidLastShowMenuResize = z;
        if (this.mMenuState == 1) {
            repostDelayedHide(2000);
            return;
        }
        if (rect.width() / rect.height() <= 1.0f) {
            this.mSettingsButton.getLayoutParams().width = this.mPipActionSize;
            this.mSettingsButton.setBackground(((FrameLayout) this).mContext.getDrawable(R.drawable.mw_pip_btn_settings_inset_ripple_port));
            View view = this.mSettingsButton;
            int i3 = this.mPipMenuPadding;
            int i4 = this.mPipMenuPaddingTop;
            view.setPadding(i3, i4, i3 / 2, i4);
            this.mExpandButton.getLayoutParams().width = this.mPipActionSizePortWidth;
            this.mExpandButton.setBackground(((FrameLayout) this).mContext.getDrawable(R.drawable.mw_pip_btn_expand_inset_ripple_port));
            View view2 = this.mExpandButton;
            int i5 = this.mPipMenuPadding / 2;
            int i6 = this.mPipMenuPaddingTop;
            view2.setPadding(i5, i6, i5, i6);
        } else {
            this.mSettingsButton.getLayoutParams().width = this.mPipActionSizeLandWidth;
            this.mSettingsButton.setBackground(((FrameLayout) this).mContext.getDrawable(R.drawable.mw_common_button_ripple));
            View view3 = this.mSettingsButton;
            int i7 = this.mPipMenuPadding;
            int i8 = this.mPipMenuPaddingTop;
            view3.setPadding(i7, i8, i7, i8);
            this.mExpandButton.getLayoutParams().width = this.mPipActionSize;
            this.mExpandButton.setBackground(((FrameLayout) this).mContext.getDrawable(R.drawable.mw_pip_btn_expand_inset_ripple));
            View view4 = this.mExpandButton;
            int i9 = this.mPipMenuPadding;
            int i10 = this.mPipMenuPaddingTop;
            view4.setPadding(i9, i10, i9 / 2, i10);
        }
        this.mTopEndContainer.requestLayout();
        if (this.mEnterSplitButton != null) {
            updateEnterSplitButtonIcon();
            this.mEnterSplitButton.setEnabled(z3);
        }
        this.mAllowTouches = !z;
        ((HandlerExecutor) this.mMainExecutor).removeCallbacks(this.mHideMenuRunnable);
        AnimatorSet animatorSet = this.mMenuContainerAnimator;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        this.mMenuContainerAnimator = new AnimatorSet();
        View view5 = this.mMenuContainer;
        Property property = View.ALPHA;
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view5, (Property<View, Float>) property, view5.getAlpha(), 1.0f);
        objectAnimatorOfFloat.addUpdateListener(this.mMenuBgUpdateListener);
        View view6 = this.mSettingsButton;
        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(view6, (Property<View, Float>) property, view6.getAlpha(), 1.0f);
        View view7 = this.mDismissButton;
        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(view7, (Property<View, Float>) property, view7.getAlpha(), 1.0f);
        View view8 = this.mExpandButton;
        ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(view8, (Property<View, Float>) property, view8.getAlpha(), 1.0f);
        View view9 = this.mEnterSplitButton;
        if (view9 != null) {
            this.mMenuContainerAnimator.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2, objectAnimatorOfFloat4, objectAnimatorOfFloat3, ObjectAnimator.ofFloat(view9, (Property<View, Float>) property, view9.getAlpha(), z3 ? 1.0f : 0.0f));
        } else {
            this.mMenuContainerAnimator.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat2, objectAnimatorOfFloat4, objectAnimatorOfFloat3);
        }
        this.mMenuContainerAnimator.setInterpolator(Interpolators.ALPHA_IN);
        this.mMenuContainerAnimator.setDuration(125L);
        AnimatorSet animatorSet2 = this.mMenuContainerAnimator;
        final char c2 = c == true ? 1 : 0;
        animatorSet2.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.pip.phone.PipMenuView.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                PipMenuView.this.mAllowTouches = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                PipMenuView pipMenuView = PipMenuView.this;
                pipMenuView.mAllowTouches = true;
                PipMenuView.m3272$$Nest$mnotifyMenuStateChangeFinish(pipMenuView, i2);
                if (c2) {
                    PipMenuView.this.repostDelayedHide(3000);
                }
            }
        });
        if (z2) {
            notifyMenuStateChangeStart(1, z, new PipMenuView$$ExternalSyntheticLambda0(this, i));
        } else {
            notifyMenuStateChangeStart(1, z, null);
            setVisibility(0);
            this.mMenuContainerAnimator.start();
        }
        updateActionViews(1, rect);
    }

    public final void updateActionViews(int i, Rect rect) {
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.expand_container);
        ViewGroup viewGroup2 = (ViewGroup) findViewById(R.id.actions_container);
        viewGroup2.setOnTouchListener(new PipMenuView$$ExternalSyntheticLambda5());
        viewGroup.setVisibility(i == 1 ? 0 : 4);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
        if (((ArrayList) this.mActions).isEmpty() || i == 0) {
            viewGroup2.setVisibility(4);
            layoutParams.topMargin = 0;
            layoutParams.bottomMargin = 0;
        } else {
            viewGroup2.setVisibility(0);
            if (this.mActionsGroup != null) {
                LayoutInflater layoutInflaterFrom = LayoutInflater.from(((FrameLayout) this).mContext);
                while (this.mActionsGroup.getChildCount() < ((ArrayList) this.mActions).size()) {
                    this.mActionsGroup.addView((PipMenuActionView) layoutInflaterFrom.inflate(R.layout.pip_menu_action, (ViewGroup) this.mActionsGroup, false));
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
                        remoteAction.getIcon().loadDrawableAsync(this.mContextForUser, new Icon.OnDrawableLoadedListener() { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda6
                            @Override // android.graphics.drawable.Icon.OnDrawableLoadedListener
                            public final void onDrawableLoaded(Drawable drawable) {
                                PipMenuActionView pipMenuActionView2 = pipMenuActionView;
                                int i5 = PipMenuView.$r8$clinit;
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
                        pipMenuActionView.setOnClickListener(new View.OnClickListener() { // from class: com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda7
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) throws PendingIntent.CanceledException {
                                PipMenuView pipMenuView = this.f$0;
                                RemoteAction remoteAction3 = remoteAction;
                                boolean z3 = z2;
                                int i5 = PipMenuView.$r8$clinit;
                                pipMenuView.getClass();
                                try {
                                    remoteAction3.getActionIntent().send();
                                } catch (PendingIntent.CanceledException e) {
                                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[3]) {
                                        ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 4958524292074978807L, 0, "PipMenuView", String.valueOf(e));
                                    }
                                }
                                if (z3) {
                                    pipMenuView.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_CUSTOM_CLOSE);
                                    pipMenuView.mAllowTouches = false;
                                    ((HandlerExecutor) pipMenuView.mMainExecutor).executeDelayed(new PipMenuView$$ExternalSyntheticLambda0(pipMenuView, 1), pipMenuView.mPipForceCloseDelay);
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
        if (!MultiWindowManager.getInstance().supportMultiSplitAppMinimumSize() || MultiWindowUtils.isInSubDisplay(((FrameLayout) this).mContext) ? ((FrameLayout) this).mContext.getResources().getConfiguration().orientation != 1 : (!splitScreenController.isSplitScreenVisible() && PipTaskOrganizer.isHomeVisible()) || splitScreenController.getSplitDivision() != 1) {
            ((ImageButton) this.mEnterSplitButton).setImageDrawable(this.mEnterSplitIconLR);
        } else {
            ((ImageButton) this.mEnterSplitButton).setImageDrawable(this.mEnterSplitIconTB);
        }
    }
}
