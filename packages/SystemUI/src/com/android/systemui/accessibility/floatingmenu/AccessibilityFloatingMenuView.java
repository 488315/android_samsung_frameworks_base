package com.android.systemui.accessibility.floatingmenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.MathUtils;
import android.util.Slog;
import android.view.Display;
import android.view.GestureDetector;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.settingslib.bluetooth.HearingAidDeviceManager;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.util.SettingsHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AccessibilityFloatingMenuView extends FrameLayout implements RecyclerView.OnItemTouchListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass4 mAccessibilityDelegate;
    public final AnonymousClass5 mAccessibilityFloatingReceiver;
    public final AccessibilityTargetAdapter mAdapter;
    public int mAlignment;
    public final Context mContext;
    private final WindowManager.LayoutParams mCurrentLayoutParams;
    public int mDisplayHeight;
    public final Rect mDisplayInsetsRect;
    public int mDisplayWidth;
    public int mDownX;
    public int mDownY;
    private final ValueAnimator mDragAnimator;
    public EditTooltipView mEditTooltipView;
    public final ValueAnimator mFadeOutAnimator;
    public float mFadeOutValue;
    public final GestureDetector mGestureDetector;
    public int mHandleFirstPositionY;
    public boolean mHasNavigationBarGesture;
    public final AccessibilityFloatingMenuView$$ExternalSyntheticLambda3 mHearingDeviceStatusObserver;
    public final AccessibilityFloatingMenuView$$ExternalSyntheticLambda3 mHearingDeviceTargetIndexObserver;
    public int mHideHandleHeight;
    public WindowManager.LayoutParams mHideHandleLayoutParams;
    public int mHideHandleWidth;
    public int mIconHeight;
    public int mIconWidth;
    public final Rect mImeInsetsRect;
    public int mInset;
    public boolean mIsDownInEnlargedTouchArea;
    public boolean mIsDragging;
    public boolean mIsFadeEffectEnabled;
    public boolean mIsHideHandle;
    public boolean mIsLongClicked;
    public boolean mIsRepeatVibrations;
    public boolean mIsShowing;
    public boolean mIsSwipeForHandle;
    public final Configuration mLastConfiguration;
    public final RecyclerView mListView;
    public int mMargin;
    public int mMarginForCoverScreen;
    public final MenuViewModel mMenuViewModel;
    public int mNavigationBarHeight;
    public Optional mOnDragEndListener;
    public int mPadding;
    public final Position mPosition;
    public float mRadius;
    public int mRadiusType;
    public int mRelativeToPointerDownX;
    public int mRelativeToPointerDownY;
    private int mShapeType;
    public int mSizeType;
    public float mSquareScaledTouchSlop;
    public final List mTargets;
    public int mTemporaryShapeType;
    public final Handler mUiHandler;
    public final VibratorHelper mVibratorHelper;
    public final WindowManager mWindowManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$1, reason: invalid class name */
    public class AnonymousClass1 extends AnimatorListenerAdapter {
        public AnonymousClass1() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            AccessibilityFloatingMenuView accessibilityFloatingMenuView = AccessibilityFloatingMenuView.this;
            Position position = accessibilityFloatingMenuView.mPosition;
            float transformCurrentPercentageXToEdge = accessibilityFloatingMenuView.transformCurrentPercentageXToEdge();
            float calculateCurrentPercentageY = AccessibilityFloatingMenuView.this.calculateCurrentPercentageY();
            position.mPercentageX = transformCurrentPercentageXToEdge;
            position.mPercentageY = calculateCurrentPercentageY;
            AccessibilityFloatingMenuView accessibilityFloatingMenuView2 = AccessibilityFloatingMenuView.this;
            Position position2 = accessibilityFloatingMenuView2.mPosition;
            accessibilityFloatingMenuView2.mAlignment = position2.mPercentageX < 0.5f ? 0 : 1;
            if (accessibilityFloatingMenuView2.mIsHideHandle) {
                accessibilityFloatingMenuView2.updateHideHandleLocationWith(position2);
            } else {
                accessibilityFloatingMenuView2.updateLocationWith(position2);
            }
            AccessibilityFloatingMenuView accessibilityFloatingMenuView3 = AccessibilityFloatingMenuView.this;
            int i = accessibilityFloatingMenuView3.mAlignment == 1 ? 0 : 2;
            accessibilityFloatingMenuView3.mRadiusType = i;
            int i2 = accessibilityFloatingMenuView3.mSizeType;
            ((ArrayList) accessibilityFloatingMenuView3.mTargets).size();
            accessibilityFloatingMenuView3.updateRadiusWith(i2, i);
            AccessibilityFloatingMenuView accessibilityFloatingMenuView4 = AccessibilityFloatingMenuView.this;
            int i3 = accessibilityFloatingMenuView4.getResources().getConfiguration().uiMode;
            int i4 = AccessibilityFloatingMenuView.this.mAlignment;
            int i5 = (i3 & 48) == 32 ? accessibilityFloatingMenuView4.mInset : 0;
            accessibilityFloatingMenuView4.setInset(i4 == 0 ? i5 : 0, i4 == 1 ? i5 : 0);
            AccessibilityFloatingMenuView.this.fadeOut();
            AccessibilityFloatingMenuView.this.mOnDragEndListener.ifPresent(new Consumer() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    Prefs.putString(((AccessibilityFloatingMenu$$ExternalSyntheticLambda1) obj).f$0.mContext, "AccessibilityFloatingMenuPosition", AccessibilityFloatingMenuView.this.mPosition.toString());
                }
            });
        }
    }

    /* renamed from: $r8$lambda$hLzvQrXylWYdGjLfT3JG-YixoX0, reason: not valid java name */
    public static void m1002$r8$lambda$hLzvQrXylWYdGjLfT3JGYixoX0(AccessibilityFloatingMenuView accessibilityFloatingMenuView, int i, int i2, ValueAnimator valueAnimator) {
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        if (accessibilityFloatingMenuView.mIsHideHandle) {
            float f = 1.0f - floatValue;
            WindowManager.LayoutParams layoutParams = accessibilityFloatingMenuView.mHideHandleLayoutParams;
            layoutParams.x = (int) ((i * floatValue) + (layoutParams.x * f));
            layoutParams.y = (int) ((floatValue * i2) + (f * layoutParams.y));
            accessibilityFloatingMenuView.updateViewLayout(layoutParams);
            return;
        }
        float f2 = 1.0f - floatValue;
        WindowManager.LayoutParams layoutParams2 = accessibilityFloatingMenuView.mCurrentLayoutParams;
        layoutParams2.x = (int) ((i * floatValue) + (layoutParams2.x * f2));
        layoutParams2.y = (int) ((floatValue * i2) + (f2 * layoutParams2.y));
        accessibilityFloatingMenuView.updateViewLayout(layoutParams2);
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda3] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$4] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$5] */
    public AccessibilityFloatingMenuView(Context context, Position position, WindowManager windowManager, HearingAidDeviceManager hearingAidDeviceManager, MenuViewModel menuViewModel) {
        super(context);
        Display display;
        final int i = 0;
        this.mIsHideHandle = false;
        this.mHideHandleWidth = 0;
        this.mHideHandleHeight = 0;
        this.mDisplayInsetsRect = new Rect();
        this.mImeInsetsRect = new Rect();
        this.mTargets = new ArrayList();
        this.mHearingDeviceStatusObserver = new Observer(this) { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda3
            public final /* synthetic */ AccessibilityFloatingMenuView f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                int i2 = i;
                ((Integer) obj).getClass();
                switch (i2) {
                    case 0:
                        final AccessibilityFloatingMenuView accessibilityFloatingMenuView = this.f$0;
                        final int intValue = ((Integer) accessibilityFloatingMenuView.mMenuViewModel.mHearingDeviceStatusData.getValue()).intValue();
                        final int intValue2 = ((Integer) accessibilityFloatingMenuView.mMenuViewModel.mHearingDeviceTargetIndex.getValue()).intValue();
                        if (intValue2 >= 0) {
                            final int i3 = 1;
                            accessibilityFloatingMenuView.mContext.getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda9
                                @Override // java.lang.Runnable
                                public final void run() {
                                    switch (i3) {
                                        case 0:
                                            AccessibilityFloatingMenuView accessibilityFloatingMenuView2 = accessibilityFloatingMenuView;
                                            int i4 = intValue;
                                            int i5 = intValue2;
                                            AccessibilityTargetAdapter accessibilityTargetAdapter = accessibilityFloatingMenuView2.mAdapter;
                                            accessibilityTargetAdapter.mHearingDeviceStatus = i4;
                                            if (i5 >= 0) {
                                                accessibilityTargetAdapter.notifyItemChanged(i5, 1);
                                                break;
                                            }
                                            break;
                                        default:
                                            AccessibilityFloatingMenuView accessibilityFloatingMenuView3 = accessibilityFloatingMenuView;
                                            int i6 = intValue;
                                            int i7 = intValue2;
                                            AccessibilityTargetAdapter accessibilityTargetAdapter2 = accessibilityFloatingMenuView3.mAdapter;
                                            accessibilityTargetAdapter2.mHearingDeviceStatus = i6;
                                            if (i7 >= 0) {
                                                accessibilityTargetAdapter2.notifyItemChanged(i7, 1);
                                                break;
                                            }
                                            break;
                                    }
                                }
                            });
                            break;
                        }
                        break;
                    default:
                        final AccessibilityFloatingMenuView accessibilityFloatingMenuView2 = this.f$0;
                        final int intValue3 = ((Integer) accessibilityFloatingMenuView2.mMenuViewModel.mHearingDeviceStatusData.getValue()).intValue();
                        final int intValue4 = ((Integer) accessibilityFloatingMenuView2.mMenuViewModel.mHearingDeviceTargetIndex.getValue()).intValue();
                        if (intValue4 >= 0) {
                            final int i4 = 0;
                            accessibilityFloatingMenuView2.mContext.getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda9
                                @Override // java.lang.Runnable
                                public final void run() {
                                    switch (i4) {
                                        case 0:
                                            AccessibilityFloatingMenuView accessibilityFloatingMenuView22 = accessibilityFloatingMenuView2;
                                            int i42 = intValue3;
                                            int i5 = intValue4;
                                            AccessibilityTargetAdapter accessibilityTargetAdapter = accessibilityFloatingMenuView22.mAdapter;
                                            accessibilityTargetAdapter.mHearingDeviceStatus = i42;
                                            if (i5 >= 0) {
                                                accessibilityTargetAdapter.notifyItemChanged(i5, 1);
                                                break;
                                            }
                                            break;
                                        default:
                                            AccessibilityFloatingMenuView accessibilityFloatingMenuView3 = accessibilityFloatingMenuView2;
                                            int i6 = intValue3;
                                            int i7 = intValue4;
                                            AccessibilityTargetAdapter accessibilityTargetAdapter2 = accessibilityFloatingMenuView3.mAdapter;
                                            accessibilityTargetAdapter2.mHearingDeviceStatus = i6;
                                            if (i7 >= 0) {
                                                accessibilityTargetAdapter2.notifyItemChanged(i7, 1);
                                                break;
                                            }
                                            break;
                                    }
                                }
                            });
                            break;
                        }
                        break;
                }
            }
        };
        final int i2 = 1;
        this.mHearingDeviceTargetIndexObserver = new Observer(this) { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda3
            public final /* synthetic */ AccessibilityFloatingMenuView f$0;

            {
                this.f$0 = this;
            }

            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                int i22 = i2;
                ((Integer) obj).getClass();
                switch (i22) {
                    case 0:
                        final AccessibilityFloatingMenuView accessibilityFloatingMenuView = this.f$0;
                        final int intValue = ((Integer) accessibilityFloatingMenuView.mMenuViewModel.mHearingDeviceStatusData.getValue()).intValue();
                        final int intValue2 = ((Integer) accessibilityFloatingMenuView.mMenuViewModel.mHearingDeviceTargetIndex.getValue()).intValue();
                        if (intValue2 >= 0) {
                            final int i3 = 1;
                            accessibilityFloatingMenuView.mContext.getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda9
                                @Override // java.lang.Runnable
                                public final void run() {
                                    switch (i3) {
                                        case 0:
                                            AccessibilityFloatingMenuView accessibilityFloatingMenuView22 = accessibilityFloatingMenuView;
                                            int i42 = intValue;
                                            int i5 = intValue2;
                                            AccessibilityTargetAdapter accessibilityTargetAdapter = accessibilityFloatingMenuView22.mAdapter;
                                            accessibilityTargetAdapter.mHearingDeviceStatus = i42;
                                            if (i5 >= 0) {
                                                accessibilityTargetAdapter.notifyItemChanged(i5, 1);
                                                break;
                                            }
                                            break;
                                        default:
                                            AccessibilityFloatingMenuView accessibilityFloatingMenuView3 = accessibilityFloatingMenuView;
                                            int i6 = intValue;
                                            int i7 = intValue2;
                                            AccessibilityTargetAdapter accessibilityTargetAdapter2 = accessibilityFloatingMenuView3.mAdapter;
                                            accessibilityTargetAdapter2.mHearingDeviceStatus = i6;
                                            if (i7 >= 0) {
                                                accessibilityTargetAdapter2.notifyItemChanged(i7, 1);
                                                break;
                                            }
                                            break;
                                    }
                                }
                            });
                            break;
                        }
                        break;
                    default:
                        final AccessibilityFloatingMenuView accessibilityFloatingMenuView2 = this.f$0;
                        final int intValue3 = ((Integer) accessibilityFloatingMenuView2.mMenuViewModel.mHearingDeviceStatusData.getValue()).intValue();
                        final int intValue4 = ((Integer) accessibilityFloatingMenuView2.mMenuViewModel.mHearingDeviceTargetIndex.getValue()).intValue();
                        if (intValue4 >= 0) {
                            final int i4 = 0;
                            accessibilityFloatingMenuView2.mContext.getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda9
                                @Override // java.lang.Runnable
                                public final void run() {
                                    switch (i4) {
                                        case 0:
                                            AccessibilityFloatingMenuView accessibilityFloatingMenuView22 = accessibilityFloatingMenuView2;
                                            int i42 = intValue3;
                                            int i5 = intValue4;
                                            AccessibilityTargetAdapter accessibilityTargetAdapter = accessibilityFloatingMenuView22.mAdapter;
                                            accessibilityTargetAdapter.mHearingDeviceStatus = i42;
                                            if (i5 >= 0) {
                                                accessibilityTargetAdapter.notifyItemChanged(i5, 1);
                                                break;
                                            }
                                            break;
                                        default:
                                            AccessibilityFloatingMenuView accessibilityFloatingMenuView3 = accessibilityFloatingMenuView2;
                                            int i6 = intValue3;
                                            int i7 = intValue4;
                                            AccessibilityTargetAdapter accessibilityTargetAdapter2 = accessibilityFloatingMenuView3.mAdapter;
                                            accessibilityTargetAdapter2.mHearingDeviceStatus = i6;
                                            if (i7 >= 0) {
                                                accessibilityTargetAdapter2.notifyItemChanged(i7, 1);
                                                break;
                                            }
                                            break;
                                    }
                                }
                            });
                            break;
                        }
                        break;
                }
            }
        };
        this.mIsDragging = false;
        this.mHasNavigationBarGesture = false;
        this.mIsLongClicked = false;
        this.mIsRepeatVibrations = false;
        this.mIsSwipeForHandle = false;
        this.mSizeType = 0;
        this.mShapeType = 0;
        this.mHandleFirstPositionY = 0;
        this.mNavigationBarHeight = 0;
        this.mOnDragEndListener = Optional.empty();
        this.mAccessibilityDelegate = new View.AccessibilityDelegate() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView.4
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                Resources resources = AccessibilityFloatingMenuView.this.getContext().getResources();
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_top_left, resources.getString(R.string.accessibility_floating_button_action_move_top_left)));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_top_right, resources.getString(R.string.accessibility_floating_button_action_move_top_right)));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_bottom_left, resources.getString(R.string.accessibility_floating_button_action_move_bottom_left)));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_bottom_right, resources.getString(R.string.accessibility_floating_button_action_move_bottom_right)));
                if (AccessibilityFloatingMenuView.this.mIsHideHandle) {
                    return;
                }
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_to_edge_and_hide, resources.getString(R.string.accessibility_floating_button_action_move_to_edge_and_minimize)));
            }

            @Override // android.view.View.AccessibilityDelegate
            public final boolean performAccessibilityAction(View view, int i3, Bundle bundle) {
                AccessibilityFloatingMenuView.this.fadeIn();
                AccessibilityFloatingMenuView accessibilityFloatingMenuView = AccessibilityFloatingMenuView.this;
                Rect rect = accessibilityFloatingMenuView.mIsHideHandle ? new Rect(0, 0, accessibilityFloatingMenuView.mDisplayWidth - accessibilityFloatingMenuView.mHideHandleWidth, accessibilityFloatingMenuView.mDisplayHeight - accessibilityFloatingMenuView.mHideHandleHeight) : new Rect(0, 0, accessibilityFloatingMenuView.mDisplayWidth - accessibilityFloatingMenuView.getWindowWidth(), accessibilityFloatingMenuView.mDisplayHeight - accessibilityFloatingMenuView.getWindowHeight());
                if (i3 == R.id.action_move_top_left) {
                    AccessibilityFloatingMenuView.this.setShapeType(0);
                    AccessibilityFloatingMenuView.this.snapToLocation(rect.left, rect.top);
                    AccessibilityFloatingMenuView accessibilityFloatingMenuView2 = AccessibilityFloatingMenuView.this;
                    accessibilityFloatingMenuView2.mListView.announceForAccessibility(accessibilityFloatingMenuView2.getContext().getString(R.string.accessibility_floating_button_action_move_top_left_feedback));
                    return true;
                }
                if (i3 == R.id.action_move_top_right) {
                    AccessibilityFloatingMenuView.this.setShapeType(0);
                    AccessibilityFloatingMenuView.this.snapToLocation(rect.right, rect.top);
                    AccessibilityFloatingMenuView accessibilityFloatingMenuView3 = AccessibilityFloatingMenuView.this;
                    accessibilityFloatingMenuView3.mListView.announceForAccessibility(accessibilityFloatingMenuView3.getContext().getString(R.string.accessibility_floating_button_action_move_top_right_feedback));
                    return true;
                }
                if (i3 == R.id.action_move_bottom_left) {
                    AccessibilityFloatingMenuView.this.setShapeType(0);
                    AccessibilityFloatingMenuView.this.snapToLocation(rect.left, rect.bottom);
                    AccessibilityFloatingMenuView accessibilityFloatingMenuView4 = AccessibilityFloatingMenuView.this;
                    accessibilityFloatingMenuView4.mListView.announceForAccessibility(accessibilityFloatingMenuView4.getContext().getString(R.string.accessibility_floating_button_action_move_bottom_left_feedback));
                    return true;
                }
                if (i3 == R.id.action_move_bottom_right) {
                    AccessibilityFloatingMenuView.this.setShapeType(0);
                    AccessibilityFloatingMenuView.this.snapToLocation(rect.right, rect.bottom);
                    AccessibilityFloatingMenuView accessibilityFloatingMenuView5 = AccessibilityFloatingMenuView.this;
                    accessibilityFloatingMenuView5.mListView.announceForAccessibility(accessibilityFloatingMenuView5.getContext().getString(R.string.accessibility_floating_button_action_move_bottom_right_feedback));
                    return true;
                }
                if (i3 != R.id.action_move_to_edge_and_hide) {
                    return super.performAccessibilityAction(view, i3, bundle);
                }
                AccessibilityFloatingMenuView accessibilityFloatingMenuView6 = AccessibilityFloatingMenuView.this;
                accessibilityFloatingMenuView6.updateHideHandle((AccessibilityFloatingMenuView.this.mHideHandleHeight / 2) + accessibilityFloatingMenuView6.mCurrentLayoutParams.y);
                AccessibilityFloatingMenuView accessibilityFloatingMenuView7 = AccessibilityFloatingMenuView.this;
                accessibilityFloatingMenuView7.updateHideHandleLocationWith(accessibilityFloatingMenuView7.mPosition);
                AccessibilityFloatingMenuView accessibilityFloatingMenuView8 = AccessibilityFloatingMenuView.this;
                if (accessibilityFloatingMenuView8.mAlignment == 1) {
                    accessibilityFloatingMenuView8.mListView.announceForAccessibility(accessibilityFloatingMenuView8.getContext().getString(R.string.accessibility_floating_button_action_move_to_right_edge_and_minimize));
                } else {
                    accessibilityFloatingMenuView8.mListView.announceForAccessibility(accessibilityFloatingMenuView8.getContext().getString(R.string.accessibility_floating_button_action_move_to_left_edge_and_minimize));
                }
                return true;
            }
        };
        this.mAccessibilityFloatingReceiver = new BroadcastReceiver() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView.5
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                if ("com.android.systemui.accessibility.floatingmenu.SHOW".equals(intent.getAction())) {
                    AccessibilityFloatingMenuView accessibilityFloatingMenuView = AccessibilityFloatingMenuView.this;
                    if (accessibilityFloatingMenuView.mIsHideHandle) {
                        accessibilityFloatingMenuView.showFloatingButton(accessibilityFloatingMenuView.mRadiusType, false);
                        return;
                    }
                    accessibilityFloatingMenuView.updateHideHandle((AccessibilityFloatingMenuView.this.mHideHandleHeight / 2) + accessibilityFloatingMenuView.mCurrentLayoutParams.y);
                    AccessibilityFloatingMenuView accessibilityFloatingMenuView2 = AccessibilityFloatingMenuView.this;
                    accessibilityFloatingMenuView2.updateViewLayout(accessibilityFloatingMenuView2.mHideHandleLayoutParams);
                }
            }
        };
        this.mContext = context;
        this.mListView = new RecyclerView(context);
        this.mMenuViewModel = menuViewModel;
        if (AccessibilityUtils.isFoldedLargeCoverScreen()) {
            Display[] displays = ((DisplayManager) context.getSystemService("display")).getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
            int length = displays.length;
            int i3 = 0;
            while (true) {
                if (i3 >= length) {
                    display = null;
                    break;
                }
                display = displays[i3];
                if (display.getDisplayId() == 1) {
                    break;
                } else {
                    i3++;
                }
            }
            this.mWindowManager = display != null ? (WindowManager) this.mContext.createDisplayContext(display).getSystemService(WindowManager.class) : null;
        } else {
            this.mWindowManager = (WindowManager) context.getSystemService(WindowManager.class);
        }
        this.mIsHideHandle = Settings.Secure.getInt(this.mContext.getContentResolver(), "accessibility_floating_menu_icon_type", 0) == 9;
        this.mLastConfiguration = new Configuration(getResources().getConfiguration());
        this.mAdapter = new AccessibilityTargetAdapter(this.mTargets);
        Looper myLooper = Looper.myLooper();
        Objects.requireNonNull(myLooper, "looper must not be null");
        this.mUiHandler = new Handler(myLooper);
        this.mPosition = position;
        int i4 = position.mPercentageX < 0.5f ? 0 : 1;
        this.mAlignment = i4;
        this.mRadiusType = i4 == 1 ? 0 : 2;
        updateDimensions();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, 2024, 520, -3);
        layoutParams.receiveInsetsIgnoringZOrder = true;
        layoutParams.privateFlags |= 2097152;
        layoutParams.windowAnimations = android.R.style.Animation.Translucent;
        layoutParams.gravity = 8388659;
        layoutParams.x = this.mAlignment == 1 ? getMaxWindowX() : getMinWindowX();
        layoutParams.y = Math.max(0, ((int) (position.mPercentageY * getMaxWindowY())) - getInterval());
        layoutParams.accessibilityTitle = getResources().getString(R.string.accessibility_floating_button);
        layoutParams.setTitle("AccessibilityFloatingMenuView");
        this.mCurrentLayoutParams = layoutParams;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, this.mFadeOutValue);
        this.mFadeOutAnimator = ofFloat;
        ofFloat.setDuration(1000L);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                AccessibilityFloatingMenuView accessibilityFloatingMenuView = AccessibilityFloatingMenuView.this;
                int i5 = AccessibilityFloatingMenuView.$r8$clinit;
                accessibilityFloatingMenuView.getClass();
                accessibilityFloatingMenuView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mDragAnimator = ofFloat2;
        ofFloat2.setDuration(150L);
        ofFloat2.setInterpolator(new OvershootInterpolator());
        ofFloat2.addListener(new AnonymousClass1());
        initListView();
        this.mVibratorHelper = (VibratorHelper) Dependency.sDependency.getDependencyInner(VibratorHelper.class);
        this.mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView.2
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public final void onLongPress(MotionEvent motionEvent) {
                AccessibilityFloatingMenuView accessibilityFloatingMenuView = AccessibilityFloatingMenuView.this;
                accessibilityFloatingMenuView.mIsLongClicked = true;
                accessibilityFloatingMenuView.mListView.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
                AccessibilityFloatingMenuView.this.mEditTooltipView = new EditTooltipView(AccessibilityFloatingMenuView.this.getContext(), AccessibilityFloatingMenuView.this);
                EditTooltipView editTooltipView = AccessibilityFloatingMenuView.this.mEditTooltipView;
                if (editTooltipView.mIsShowing) {
                    return;
                }
                editTooltipView.mIsShowing = true;
                editTooltipView.updateTooltipView();
                editTooltipView.mWindowManager.addView(editTooltipView, editTooltipView.mCurrentLayoutParams);
            }
        });
    }

    public static boolean isFrontDisplay(Context context) {
        boolean z = context.getResources().getConfiguration().semDisplayDeviceType == 5;
        Slog.d("AccessibilityFloatingMenuView", "isFrontDisplay: " + z);
        return z;
    }

    public static boolean isTablet() {
        String str = SystemProperties.get("ro.build.characteristics");
        return str != null && str.contains("tablet");
    }

    public final float calculateCurrentPercentageY() {
        float f;
        float maxWindowY;
        if (this.mIsHideHandle) {
            f = this.mHideHandleLayoutParams.y;
            maxWindowY = this.mDisplayHeight - this.mHideHandleHeight;
        } else {
            f = this.mCurrentLayoutParams.y;
            maxWindowY = getMaxWindowY();
        }
        return f / maxWindowY;
    }

    public void fadeIn() {
        if (this.mIsFadeEffectEnabled) {
            this.mFadeOutAnimator.cancel();
            this.mUiHandler.removeCallbacksAndMessages(null);
            this.mUiHandler.post(new AccessibilityFloatingMenuView$$ExternalSyntheticLambda1(this, 1));
        }
    }

    public void fadeOut() {
        if (this.mIsFadeEffectEnabled) {
            this.mUiHandler.postDelayed(new AccessibilityFloatingMenuView$$ExternalSyntheticLambda1(this, 0), 3000L);
        }
    }

    public Rect getAvailableBounds() {
        return new Rect(0, 0, this.mDisplayWidth - getWindowWidth(), this.mDisplayHeight - getWindowHeight());
    }

    public final int getInterval() {
        int maxWindowY = (int) (this.mPosition.mPercentageY * getMaxWindowY());
        int i = this.mDisplayHeight - this.mImeInsetsRect.bottom;
        int windowHeight = getWindowHeight() + maxWindowY;
        if (windowHeight > i) {
            return windowHeight - i;
        }
        return 0;
    }

    public final int getLayoutWidth() {
        return (this.mPadding * 2) + this.mIconWidth;
    }

    public final int getMaxWindowX() {
        int layoutWidth;
        int dimensionPixelSize;
        if (offsetForLeftNaviBar()) {
            layoutWidth = (this.mDisplayWidth - getLayoutWidth()) - (getContext().getResources().getDimensionPixelSize(R.dimen.accessibility_floating_menu_large_padding) * 3);
            dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.accessibility_floating_menu_elevation) * 4;
        } else if ((BasicRune.NAVBAR_FOLDERBLE_TYPE_FOLD && !isFrontDisplay(getContext())) || isTablet() || AccessibilityUtils.isFoldedLargeCoverScreen()) {
            layoutWidth = this.mDisplayWidth;
            dimensionPixelSize = getLayoutWidth();
        } else {
            Configuration configuration = getContext().getResources().getConfiguration();
            if (configuration == null || configuration.orientation != 1) {
                int i = this.mContext.getResources().getDisplayMetrics().widthPixels;
                this.mDisplayWidth = i;
                layoutWidth = (i - getLayoutWidth()) - getContext().getResources().getDimensionPixelSize(R.dimen.navigation_bar_size);
                dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.accessibility_floating_menu_large_padding) * 3;
            } else {
                layoutWidth = this.mDisplayWidth;
                dimensionPixelSize = getLayoutWidth();
            }
        }
        return layoutWidth - dimensionPixelSize;
    }

    public final int getMaxWindowXForHandle() {
        int dimensionPixelSize;
        int dimensionPixelSize2;
        if (offsetForLeftNaviBar()) {
            dimensionPixelSize = this.mDisplayWidth;
            dimensionPixelSize2 = this.mHideHandleWidth;
        } else if ((BasicRune.NAVBAR_FOLDERBLE_TYPE_FOLD && !isFrontDisplay(getContext())) || isTablet() || AccessibilityUtils.isFoldedLargeCoverScreen()) {
            dimensionPixelSize = this.mDisplayWidth;
            dimensionPixelSize2 = this.mHideHandleWidth;
        } else {
            Configuration configuration = getContext().getResources().getConfiguration();
            if (configuration == null || configuration.orientation != 1) {
                int i = this.mContext.getResources().getDisplayMetrics().widthPixels;
                this.mDisplayWidth = i;
                dimensionPixelSize = (i - this.mHideHandleWidth) - getContext().getResources().getDimensionPixelSize(R.dimen.navigation_bar_size);
                dimensionPixelSize2 = getContext().getResources().getDimensionPixelSize(R.dimen.accessibility_floating_menu_large_padding) * 3;
            } else {
                dimensionPixelSize = this.mDisplayWidth;
                dimensionPixelSize2 = this.mHideHandleWidth;
            }
        }
        return dimensionPixelSize - dimensionPixelSize2;
    }

    public final int getMaxWindowY() {
        if (!AccessibilityUtils.isFoldedLargeCoverScreen()) {
            return this.mDisplayHeight - getWindowHeight();
        }
        int windowHeight = (this.mDisplayHeight - getWindowHeight()) - (this.mMarginForCoverScreen * 2);
        if (windowHeight < 0) {
            return 0;
        }
        return windowHeight;
    }

    public final int getMinWindowX() {
        Configuration configuration = this.mLastConfiguration;
        return -((configuration == null || configuration.orientation != 1) ? 0 : this.mMargin);
    }

    public final int getMinWindowXForHandle() {
        if (offsetForLeftNaviBar()) {
            return this.mNavigationBarHeight;
        }
        Configuration configuration = this.mLastConfiguration;
        return -((configuration == null || configuration.orientation != 1) ? 0 : this.mMargin);
    }

    public final int getNavigationBarHeight() {
        if (getResources().getBoolean(android.R.bool.config_swipeDisambiguation)) {
            return getResources().getDimensionPixelSize(android.R.dimen.seekbar_track_progress_height_material);
        }
        return 0;
    }

    public final int getWindowHeight() {
        int i = this.mDisplayHeight;
        int i2 = this.mMargin;
        return Math.min(i, Math.min(i - (i2 * 2), ((ArrayList) this.mTargets).size() * ((this.mPadding * 2) + this.mIconHeight)) + (i2 * 2));
    }

    public final Rect getWindowLocationOnScreen() {
        int i;
        int i2;
        if (this.mIsHideHandle) {
            WindowManager.LayoutParams layoutParams = this.mHideHandleLayoutParams;
            i = layoutParams.x;
            i2 = layoutParams.y;
        } else {
            WindowManager.LayoutParams layoutParams2 = this.mCurrentLayoutParams;
            i = layoutParams2.x;
            i2 = layoutParams2.y;
        }
        return new Rect(i, i2, getWindowWidth() + i, getWindowHeight() + i2);
    }

    public final int getWindowWidth() {
        Configuration configuration = this.mLastConfiguration;
        return getLayoutWidth() + (((configuration == null || configuration.orientation != 1) ? 0 : this.mMargin) * 2);
    }

    public boolean hasExceededMaxLayoutHeight() {
        return ((ArrayList) this.mTargets).size() * ((this.mPadding * 2) + this.mIconHeight) > this.mDisplayHeight - (this.mMargin * 2);
    }

    public final void hide$1() {
        if (this.mIsShowing) {
            this.mIsShowing = false;
            this.mDragAnimator.cancel();
            this.mWindowManager.removeView(this);
            setOnApplyWindowInsetsListener(null);
            setSystemGestureExclusion();
            getContext().unregisterReceiver(this.mAccessibilityFloatingReceiver);
            EditTooltipView editTooltipView = this.mEditTooltipView;
            if (editTooltipView != null) {
                editTooltipView.hide();
            }
            this.mMenuViewModel.mHearingDeviceStatusData.removeObserver(this.mHearingDeviceStatusObserver);
            this.mMenuViewModel.mHearingDeviceTargetIndex.removeObserver(this.mHearingDeviceTargetIndexObserver);
        }
    }

    public final void initListView() {
        Drawable drawable = getContext().getDrawable(R.drawable.accessibility_floating_menu_background);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
        Settings.Secure.putInt(getContext().getContentResolver(), "accessibility_floating_menu_icon_type", 0);
        if (this.mListView.getParent() != null) {
            ((ViewGroup) this.mListView.getParent()).removeView(this.mListView);
        }
        if (AccessibilityUtils.isFoldedLargeCoverScreen()) {
            int i = this.mMarginForCoverScreen;
            layoutParams.setMargins(0, i, 0, i);
        }
        this.mListView.setLayoutParams(layoutParams);
        this.mListView.setBackground(new InstantInsetLayerDrawable(new Drawable[]{drawable}));
        this.mListView.setAdapter(this.mAdapter);
        this.mListView.setLayoutManager(linearLayoutManager);
        this.mListView.mOnItemTouchListeners.add(this);
        this.mListView.animate().setInterpolator(new OvershootInterpolator());
        this.mListView.setAccessibilityDelegate(this.mAccessibilityDelegate);
        this.mListView.setClipToOutline(true);
        addView(this.mListView);
    }

    public final boolean isEdgeArea() {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.accessibility_floating_edge_area);
        int rotation = this.mWindowManager.getDefaultDisplay().getRotation();
        if (this.mIsHideHandle) {
            int i = this.mHideHandleLayoutParams.x;
            return i <= dimensionPixelSize || i >= (getMaxWindowXForHandle() + this.mHideHandleWidth) - dimensionPixelSize;
        }
        int layoutWidth = (this.mDisplayWidth - getLayoutWidth()) - dimensionPixelSize;
        if (rotation != 0 && getNavigationBarHeight() > 0) {
            layoutWidth -= getNavigationBarHeight();
        }
        int i2 = this.mCurrentLayoutParams.x;
        return i2 <= dimensionPixelSize || i2 >= layoutWidth;
    }

    public final boolean offsetForLeftNaviBar() {
        if (this.mHasNavigationBarGesture || isTablet()) {
            return false;
        }
        return (!BasicRune.NAVBAR_FOLDERBLE_TYPE_FOLD || isFrontDisplay(getContext())) && this.mWindowManager.getDefaultDisplay().getRotation() == 3;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mLastConfiguration.setTo(configuration);
        if ((configuration.diff(this.mLastConfiguration) & 4) != 0) {
            this.mCurrentLayoutParams.accessibilityTitle = getResources().getString(R.string.accessibility_floating_button);
        }
        updateDimensions();
        updateItemViewDimensionsWith(this.mSizeType);
        AccessibilityTargetAdapter accessibilityTargetAdapter = this.mAdapter;
        accessibilityTargetAdapter.mItemPadding = this.mPadding;
        accessibilityTargetAdapter.mIconWidthHeight = this.mIconWidth;
        accessibilityTargetAdapter.notifyDataSetChanged();
        ((GradientDrawable) ((InstantInsetLayerDrawable) this.mListView.getBackground()).getDrawable(0)).setColor(getResources().getColor(R.color.accessibility_floating_menu_background));
        if (this.mIsHideHandle) {
            ((LayerDrawable) ((InstantInsetLayerDrawable) this.mListView.getBackground()).getDrawable(1)).setColorFilter(getContext().getColor(R.color.accessibility_floating_menu_hide_icon), PorterDuff.Mode.SRC_ATOP);
        }
        if (this.mIsHideHandle) {
            updateHideHandleLocationWith(this.mPosition);
        } else {
            updateLocationWith(this.mPosition);
        }
        int i = this.mSizeType;
        int i2 = this.mRadiusType;
        ((ArrayList) this.mTargets).size();
        updateRadiusWith(i, i2);
        this.mListView.setOverScrollMode(hasExceededMaxLayoutHeight() ? 0 : 2);
        setSystemGestureExclusion();
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0038, code lost:
    
        if ((android.util.MathUtils.sq(r0 - r7.mDownY) + android.util.MathUtils.sq(r8 - r9)) > r7.mSquareScaledTouchSlop) goto L14;
     */
    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onInterceptTouchEvent(androidx.recyclerview.widget.RecyclerView r8, android.view.MotionEvent r9) {
        /*
            Method dump skipped, instructions count: 583
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView.onInterceptTouchEvent(androidx.recyclerview.widget.RecyclerView, android.view.MotionEvent):boolean");
    }

    public final void onTargetFeaturesChanged(List list) {
        fadeIn();
        ((ArrayList) this.mTargets).clear();
        ((ArrayList) this.mTargets).addAll(list);
        this.mAdapter.notifyDataSetChanged();
        int i = this.mSizeType;
        int i2 = this.mRadiusType;
        ((ArrayList) this.mTargets).size();
        updateRadiusWith(i, i2);
        this.mListView.setOverScrollMode(hasExceededMaxLayoutHeight() ? 0 : 2);
        if (!this.mIsHideHandle) {
            updateLocationWith(this.mPosition);
        }
        setSystemGestureExclusion();
        fadeOut();
    }

    public final void setInset(int i, int i2) {
        InstantInsetLayerDrawable instantInsetLayerDrawable = (InstantInsetLayerDrawable) this.mListView.getBackground();
        if (!this.mIsHideHandle) {
            if (instantInsetLayerDrawable.getLayerInsetLeft(0) == i && instantInsetLayerDrawable.getLayerInsetRight(0) == i2) {
                return;
            }
            instantInsetLayerDrawable.setLayerInset(0, i, 0, i2, 0);
            return;
        }
        int i3 = this.mRadiusType;
        if (i3 == 0) {
            int i4 = this.mMarginForCoverScreen;
            instantInsetLayerDrawable.setLayerInset(0, i4, i4, 0, i4);
            instantInsetLayerDrawable.setLayerInset(1, this.mMarginForCoverScreen, 0, 0, 0);
        } else if (i3 == 2) {
            int i5 = this.mMarginForCoverScreen;
            instantInsetLayerDrawable.setLayerInset(0, 0, i5, i5, i5);
            instantInsetLayerDrawable.setLayerInset(1, 0, 0, this.mMarginForCoverScreen, 0);
        }
    }

    public final void setRadius(float f, int i) {
        ((GradientDrawable) ((InstantInsetLayerDrawable) this.mListView.getBackground()).getDrawable(0)).setCornerRadii(i == 0 ? new float[]{f, f, 0.0f, 0.0f, 0.0f, 0.0f, f, f} : i == 2 ? new float[]{0.0f, 0.0f, f, f, f, f, 0.0f, 0.0f} : new float[]{f, f, f, f, f, f, f, f});
    }

    public final void setShapeType(int i) {
        fadeIn();
        this.mShapeType = i;
        setOnTouchListener(i == 0 ? null : new View.OnTouchListener() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda2
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                AccessibilityFloatingMenuView accessibilityFloatingMenuView = AccessibilityFloatingMenuView.this;
                int i2 = AccessibilityFloatingMenuView.$r8$clinit;
                accessibilityFloatingMenuView.getClass();
                int action = motionEvent.getAction();
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                Configuration configuration = accessibilityFloatingMenuView.mLastConfiguration;
                int i3 = (configuration == null || configuration.orientation != 1) ? 0 : accessibilityFloatingMenuView.mMargin;
                int i4 = accessibilityFloatingMenuView.mMargin;
                int layoutWidth = accessibilityFloatingMenuView.getLayoutWidth() + i3;
                int i5 = accessibilityFloatingMenuView.mMargin;
                Rect rect = new Rect(i3, i4, layoutWidth, Math.min(accessibilityFloatingMenuView.mDisplayHeight - (i5 * 2), ((ArrayList) accessibilityFloatingMenuView.mTargets).size() * ((accessibilityFloatingMenuView.mPadding * 2) + accessibilityFloatingMenuView.mIconHeight)) + i5);
                if (action == 0 && rect.contains(x, y)) {
                    accessibilityFloatingMenuView.mIsDownInEnlargedTouchArea = true;
                }
                if (!accessibilityFloatingMenuView.mIsDownInEnlargedTouchArea) {
                    return false;
                }
                if (action == 1 || action == 3) {
                    accessibilityFloatingMenuView.mIsDownInEnlargedTouchArea = false;
                }
                int i6 = accessibilityFloatingMenuView.mMargin;
                motionEvent.setLocation(x - i6, y - i6);
                return accessibilityFloatingMenuView.mListView.dispatchTouchEvent(motionEvent);
            }
        });
        fadeOut();
    }

    public final void setSizeType(int i) {
        if (this.mIsHideHandle) {
            updateItemViewDimensionsWith(i);
            updateHideHandle((this.mHideHandleHeight / 2) + this.mCurrentLayoutParams.y);
            updateHideHandleLocationWith(this.mPosition);
            return;
        }
        fadeIn();
        this.mSizeType = i;
        updateItemViewDimensionsWith(i);
        AccessibilityTargetAdapter accessibilityTargetAdapter = this.mAdapter;
        accessibilityTargetAdapter.mItemPadding = this.mPadding;
        accessibilityTargetAdapter.mIconWidthHeight = this.mIconWidth;
        accessibilityTargetAdapter.notifyDataSetChanged();
        int i2 = this.mRadiusType;
        ((ArrayList) this.mTargets).size();
        updateRadiusWith(i, i2);
        updateLocationWith(this.mPosition);
        this.mListView.setOverScrollMode(hasExceededMaxLayoutHeight() ? 0 : 2);
        setSystemGestureExclusion();
        fadeOut();
    }

    public final void setSystemGestureExclusion() {
        final Rect rect = new Rect(0, 0, getWindowWidth(), getWindowHeight());
        post(new Runnable() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                AccessibilityFloatingMenuView accessibilityFloatingMenuView = AccessibilityFloatingMenuView.this;
                accessibilityFloatingMenuView.setSystemGestureExclusionRects(accessibilityFloatingMenuView.mIsShowing ? Collections.singletonList(rect) : Collections.EMPTY_LIST);
            }
        });
    }

    public final void show() {
        if (this.mIsShowing) {
            return;
        }
        this.mIsShowing = true;
        this.mWindowManager.addView(this, this.mCurrentLayoutParams);
        setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda11
            @Override // android.view.View.OnApplyWindowInsetsListener
            public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                AccessibilityFloatingMenuView accessibilityFloatingMenuView = AccessibilityFloatingMenuView.this;
                WindowMetrics currentWindowMetrics = accessibilityFloatingMenuView.mWindowManager.getCurrentWindowMetrics();
                if (!currentWindowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout()).toRect().equals(accessibilityFloatingMenuView.mDisplayInsetsRect)) {
                    accessibilityFloatingMenuView.updateDisplaySizeWith(currentWindowMetrics);
                    if (accessibilityFloatingMenuView.mIsHideHandle) {
                        accessibilityFloatingMenuView.updateHideHandleLocationWith(accessibilityFloatingMenuView.mPosition);
                    } else {
                        accessibilityFloatingMenuView.updateLocationWith(accessibilityFloatingMenuView.mPosition);
                    }
                }
                Rect rect = currentWindowMetrics.getWindowInsets().getInsets(WindowInsets.Type.ime()).toRect();
                if (!rect.equals(accessibilityFloatingMenuView.mImeInsetsRect)) {
                    if (rect.left == 0 && rect.top == 0 && rect.right == 0 && rect.bottom == 0) {
                        accessibilityFloatingMenuView.mImeInsetsRect.setEmpty();
                    } else {
                        accessibilityFloatingMenuView.mImeInsetsRect.set(rect);
                    }
                    if (!accessibilityFloatingMenuView.mIsHideHandle) {
                        accessibilityFloatingMenuView.updateLocationWith(accessibilityFloatingMenuView.mPosition);
                    }
                }
                return windowInsets;
            }
        });
        setSystemGestureExclusion();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.systemui.accessibility.floatingmenu.SHOW");
        getContext().registerReceiver(this.mAccessibilityFloatingReceiver, intentFilter, 2);
        MenuViewModel menuViewModel = this.mMenuViewModel;
        MutableLiveData mutableLiveData = menuViewModel.mHearingDeviceStatusData;
        Objects.requireNonNull(mutableLiveData);
        HearingAidDeviceManager hearingAidDeviceManager = menuViewModel.mInfoRepository.mHearingAidDeviceManager;
        if (hearingAidDeviceManager != null) {
            if (!hearingAidDeviceManager.mInitialDevicesConnectionStatusUpdate) {
                hearingAidDeviceManager.updateDevicesConnectionStatus();
            }
            mutableLiveData.setValue(Integer.valueOf(hearingAidDeviceManager.mDevicesConnectionStatus));
        } else {
            mutableLiveData.setValue(-1);
        }
        mutableLiveData.observeForever(this.mHearingDeviceStatusObserver);
        this.mMenuViewModel.mHearingDeviceTargetIndex.observeForever(this.mHearingDeviceTargetIndexObserver);
    }

    public final void showFloatingButton(int i, boolean z) {
        this.mIsHideHandle = false;
        removeView(this.mListView);
        initListView();
        setSizeType(Settings.Secure.getInt(getContext().getContentResolver(), "accessibility_floating_menu_size", 9));
        if (z) {
            this.mCurrentLayoutParams.x = this.mHideHandleLayoutParams.x;
        } else {
            this.mCurrentLayoutParams.x = this.mAlignment == 1 ? getMaxWindowX() : getMinWindowX();
        }
        this.mCurrentLayoutParams.y = Math.min(this.mHideHandleLayoutParams.y, getMaxWindowY());
        updateViewLayout(this.mCurrentLayoutParams);
        int i2 = this.mSizeType;
        ((ArrayList) this.mTargets).size();
        updateRadiusWith(i2, i);
        this.mCurrentLayoutParams.accessibilityTitle = getResources().getString(R.string.accessibility_floating_button);
        if (isEdgeArea()) {
            int minWindowX = getMinWindowX();
            int maxWindowX = getMaxWindowX();
            WindowManager.LayoutParams layoutParams = this.mCurrentLayoutParams;
            if (layoutParams.x > (minWindowX + maxWindowX) / 2) {
                minWindowX = maxWindowX;
            }
            snapToLocation(minWindowX, layoutParams.y);
        }
        this.mListView.announceForAccessibility(getContext().getString(R.string.accessibility_floating_button_expanded));
    }

    public void snapToLocation(final int i, final int i2) {
        this.mDragAnimator.cancel();
        this.mDragAnimator.removeAllUpdateListeners();
        this.mDragAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                AccessibilityFloatingMenuView.m1002$r8$lambda$hLzvQrXylWYdGjLfT3JGYixoX0(AccessibilityFloatingMenuView.this, i, i2, valueAnimator);
            }
        });
        this.mDragAnimator.start();
    }

    public final float transformCurrentPercentageXToEdge() {
        float f;
        int maxWindowX;
        if (this.mIsHideHandle) {
            f = this.mHideHandleLayoutParams.x;
            maxWindowX = getMaxWindowXForHandle();
        } else {
            f = this.mCurrentLayoutParams.x;
            maxWindowX = getMaxWindowX();
        }
        return ((double) (f / ((float) maxWindowX))) < 0.5d ? 0.0f : 1.0f;
    }

    public final void updateDimensions() {
        Resources resources = getResources();
        updateDisplaySizeWith(this.mWindowManager.getCurrentWindowMetrics());
        this.mMargin = resources.getDimensionPixelSize(R.dimen.accessibility_floating_menu_margin);
        this.mMarginForCoverScreen = resources.getDimensionPixelSize(R.dimen.accessibility_floating_menu_margin_for_cover_screen);
        this.mInset = resources.getDimensionPixelSize(R.dimen.accessibility_floating_menu_stroke_inset);
        this.mSquareScaledTouchSlop = MathUtils.sq(ViewConfiguration.get(getContext()).getScaledTouchSlop());
        updateItemViewDimensionsWith(this.mSizeType);
        this.mNavigationBarHeight = getNavigationBarHeight();
        this.mHasNavigationBarGesture = Settings.Global.getInt(getContext().getContentResolver(), SettingsHelper.INDEX_NAVIGATION_BAR_GESTURE_WHILE_HIDDEN, 0) != 0;
    }

    public final void updateDisplaySizeWith(WindowMetrics windowMetrics) {
        Rect bounds = windowMetrics.getBounds();
        Insets insetsIgnoringVisibility = windowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
        this.mDisplayInsetsRect.set(insetsIgnoringVisibility.toRect());
        bounds.inset(insetsIgnoringVisibility);
        this.mDisplayWidth = bounds.width();
        this.mDisplayHeight = bounds.height();
    }

    public final void updateHideHandle(int i) {
        this.mIsHideHandle = true;
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.accessibility_floating_hide_handle_width) * 2;
        this.mHideHandleWidth = dimensionPixelSize;
        this.mHideHandleHeight = (this.mPadding * 2) + this.mIconHeight + dimensionPixelSize;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, 2024, 520, -3);
        layoutParams.windowAnimations = android.R.style.Animation.Translucent;
        layoutParams.gravity = 8388659;
        layoutParams.x = this.mAlignment == 1 ? getMaxWindowXForHandle() : getMinWindowXForHandle();
        int i2 = i - (this.mHideHandleHeight / 2);
        layoutParams.y = i2;
        this.mHandleFirstPositionY = i2;
        this.mHideHandleLayoutParams = layoutParams;
        Drawable drawable = getContext().getDrawable(R.drawable.accessibility_floating_hide_icon);
        Drawable drawable2 = getContext().getDrawable(R.drawable.accessibility_floating_menu_background);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(this.mHideHandleWidth, this.mHideHandleHeight);
        Settings.Secure.putInt(getContext().getContentResolver(), "accessibility_floating_menu_icon_type", 9);
        if (this.mListView.getParent() != null) {
            ((ViewGroup) this.mListView.getParent()).removeView(this.mListView);
        }
        this.mListView.setLayoutParams(layoutParams2);
        this.mListView.setBackground(new InstantInsetLayerDrawable(new Drawable[]{drawable2, drawable}));
        this.mListView.setContentDescription(getContext().getString(R.string.accessibility_floating_button));
        this.mListView.setAdapter(null);
        this.mListView.setLayoutManager(linearLayoutManager);
        this.mListView.setAccessibilityDelegate(this.mAccessibilityDelegate);
        this.mListView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AccessibilityFloatingMenuView accessibilityFloatingMenuView = AccessibilityFloatingMenuView.this;
                accessibilityFloatingMenuView.showFloatingButton(accessibilityFloatingMenuView.mRadiusType, false);
            }
        });
        int i3 = this.mSizeType;
        int i4 = this.mRadiusType;
        ((ArrayList) this.mTargets).size();
        updateRadiusWith(i3, i4);
        this.mHideHandleLayoutParams.accessibilityTitle = getResources().getString(R.string.accessibility_floating_button);
        setInset(0, 0);
        addView(this.mListView);
    }

    public final void updateHideHandleLocationWith(Position position) {
        this.mHideHandleLayoutParams.x = position.mPercentageX >= 0.5f ? getMaxWindowXForHandle() : getMinWindowXForHandle();
        if (this.mIsSwipeForHandle) {
            this.mHideHandleLayoutParams.y = this.mHandleFirstPositionY;
            Position position2 = this.mPosition;
            float transformCurrentPercentageXToEdge = transformCurrentPercentageXToEdge();
            float calculateCurrentPercentageY = calculateCurrentPercentageY();
            position2.mPercentageX = transformCurrentPercentageXToEdge;
            position2.mPercentageY = calculateCurrentPercentageY;
            this.mIsSwipeForHandle = false;
        } else {
            this.mHideHandleLayoutParams.y = Math.max(0, ((int) (position.mPercentageY * (this.mDisplayHeight - this.mHideHandleHeight))) - getInterval());
        }
        updateViewLayout(this.mHideHandleLayoutParams);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0027, code lost:
    
        if (r6 == 9) goto L11;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateItemViewDimensionsWith(int r6) {
        /*
            r5 = this;
            android.content.res.Resources r0 = r5.getResources()
            if (r6 != 0) goto La
            r1 = 2131165259(0x7f07004b, float:1.794473E38)
            goto Ld
        La:
            r1 = 2131165240(0x7f070038, float:1.7944692E38)
        Ld:
            int r1 = r0.getDimensionPixelSize(r1)
            r5.mPadding = r1
            boolean r1 = com.android.internal.accessibility.util.AccessibilityUtils.isFoldedLargeCoverScreen()
            r2 = 2131165247(0x7f07003f, float:1.7944706E38)
            r3 = 2131165261(0x7f07004d, float:1.7944734E38)
            r4 = 9
            if (r1 == 0) goto L2b
            if (r6 != 0) goto L27
            r2 = 2131165257(0x7f070049, float:1.7944726E38)
            goto L34
        L27:
            if (r6 != r4) goto L34
        L29:
            r2 = r3
            goto L34
        L2b:
            if (r6 != 0) goto L2e
            goto L29
        L2e:
            if (r6 != r4) goto L31
            goto L34
        L31:
            r2 = 2131165242(0x7f07003a, float:1.7944696E38)
        L34:
            int r6 = r0.getDimensionPixelSize(r2)
            r5.mIconWidth = r6
            r5.mIconHeight = r6
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView.updateItemViewDimensionsWith(int):void");
    }

    public final void updateLocationWith(Position position) {
        this.mCurrentLayoutParams.x = position.mPercentageX >= 0.5f ? getMaxWindowX() : getMinWindowX();
        this.mCurrentLayoutParams.y = Math.max(0, ((int) (position.mPercentageY * getMaxWindowY())) - getInterval());
        updateViewLayout(this.mCurrentLayoutParams);
    }

    public final void updateOpacityWith(float f, boolean z) {
        this.mIsFadeEffectEnabled = z;
        this.mFadeOutValue = f;
        this.mFadeOutAnimator.cancel();
        this.mFadeOutAnimator.setFloatValues(1.0f, this.mFadeOutValue);
        setAlpha(this.mIsFadeEffectEnabled ? this.mFadeOutValue : 1.0f);
        if (this.mIsFadeEffectEnabled) {
            return;
        }
        this.mUiHandler.removeCallbacksAndMessages(null);
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0018, code lost:
    
        if (r6 == 9) goto L7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateRadiusWith(int r6, int r7) {
        /*
            r5 = this;
            android.content.res.Resources r0 = r5.getResources()
            boolean r1 = com.android.internal.accessibility.util.AccessibilityUtils.isFoldedLargeCoverScreen()
            r2 = 2131165246(0x7f07003e, float:1.7944704E38)
            r3 = 2131165258(0x7f07004a, float:1.7944728E38)
            r4 = 9
            if (r1 == 0) goto L1c
            if (r6 != 0) goto L18
            r2 = 2131165256(0x7f070048, float:1.7944724E38)
            goto L25
        L18:
            if (r6 != r4) goto L25
        L1a:
            r2 = r3
            goto L25
        L1c:
            if (r6 != 0) goto L1f
            goto L1a
        L1f:
            if (r6 != r4) goto L22
            goto L25
        L22:
            r2 = 2131165239(0x7f070037, float:1.794469E38)
        L25:
            int r6 = r0.getDimensionPixelSize(r2)
            float r6 = (float) r6
            r5.mRadius = r6
            r5.setRadius(r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView.updateRadiusWith(int, int):void");
    }

    public final void updateViewLayout(final WindowManager.LayoutParams layoutParams) {
        if (!isAttachedToWindow()) {
            addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView.3
                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewAttachedToWindow(View view) {
                    AccessibilityFloatingMenuView accessibilityFloatingMenuView = AccessibilityFloatingMenuView.this;
                    WindowManager.LayoutParams layoutParams2 = layoutParams;
                    int i = AccessibilityFloatingMenuView.$r8$clinit;
                    accessibilityFloatingMenuView.updateViewLayout(layoutParams2);
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public final void onViewDetachedFromWindow(View view) {
                    AccessibilityFloatingMenuView accessibilityFloatingMenuView = AccessibilityFloatingMenuView.this;
                    int i = AccessibilityFloatingMenuView.$r8$clinit;
                    accessibilityFloatingMenuView.addOnAttachStateChangeListener(null);
                    Slog.d("AccessibilityFloatingMenuView", "removeViewAttachStateChangeListener called");
                }
            });
            Slog.d("AccessibilityFloatingMenuView", "addViewAttachStateChangeListener called");
            Slog.d("AccessibilityFloatingMenuView", "Debug callstack : ", new Exception());
        } else {
            try {
                this.mWindowManager.updateViewLayout(this, layoutParams);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public final void onRequestDisallowInterceptTouchEvent(boolean z) {
    }

    @Override // android.view.View, androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public final void onTouchEvent(MotionEvent motionEvent) {
    }
}
