package com.android.systemui.accessibility.floatingmenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.MathUtils;
import android.util.Slog;
import android.util.StatsEvent;
import android.util.StatsLog;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.systemui.BasicRune;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public final class AccessibilityFloatingMenuView extends FrameLayout implements RecyclerView.OnItemTouchListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass4 mAccessibilityDelegate;
    public final AnonymousClass5 mAccessibilityFloatingReceiver;
    public final AccessibilityTargetAdapter mAdapter;
    public int mAlignment;
    final WindowManager.LayoutParams mCurrentLayoutParams;
    public int mDisplayHeight;
    public final Rect mDisplayInsetsRect;
    public int mDisplayWidth;
    public int mDownX;
    public int mDownY;
    final ValueAnimator mDragAnimator;
    public EditTooltipView mEditTooltipView;
    public final ValueAnimator mFadeOutAnimator;
    public float mFadeOutValue;
    public final GestureDetector mGestureDetector;
    public int mHandleFirstPositionY;
    public boolean mHasNavigationBarGesture;
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
    public int mNavigationBarHeight;
    public Optional mOnDragEndListener;
    public int mPadding;
    public final Position mPosition;
    public float mRadius;
    public int mRadiusType;
    public int mRelativeToPointerDownX;
    public int mRelativeToPointerDownY;
    int mShapeType;
    public int mSizeType;
    public float mSquareScaledTouchSlop;
    public final List mTargets;
    public int mTemporaryShapeType;
    public final Handler mUiHandler;
    public final VibratorHelper mVibratorHelper;
    public final WindowManager mWindowManager;

    /* renamed from: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$1, reason: invalid class name */
    public final class AnonymousClass1 extends AnimatorListenerAdapter {
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
            accessibilityFloatingMenuView2.mAlignment = AccessibilityFloatingMenuView.transformToAlignment(accessibilityFloatingMenuView2.mPosition.mPercentageX);
            AccessibilityFloatingMenuView accessibilityFloatingMenuView3 = AccessibilityFloatingMenuView.this;
            if (accessibilityFloatingMenuView3.mIsHideHandle) {
                accessibilityFloatingMenuView3.updateHideHandleLocationWith(accessibilityFloatingMenuView3.mPosition);
            } else {
                accessibilityFloatingMenuView3.updateLocationWith(accessibilityFloatingMenuView3.mPosition);
            }
            AccessibilityFloatingMenuView accessibilityFloatingMenuView4 = AccessibilityFloatingMenuView.this;
            int i = accessibilityFloatingMenuView4.mAlignment == 1 ? 0 : 2;
            accessibilityFloatingMenuView4.mRadiusType = i;
            int i2 = accessibilityFloatingMenuView4.mSizeType;
            ((ArrayList) accessibilityFloatingMenuView4.mTargets).size();
            accessibilityFloatingMenuView4.updateRadiusWith(i2, i);
            AccessibilityFloatingMenuView accessibilityFloatingMenuView5 = AccessibilityFloatingMenuView.this;
            int i3 = accessibilityFloatingMenuView5.getResources().getConfiguration().uiMode;
            int i4 = AccessibilityFloatingMenuView.this.mAlignment;
            int i5 = (i3 & 48) == 32 ? accessibilityFloatingMenuView5.mInset : 0;
            accessibilityFloatingMenuView5.setInset(i4 == 0 ? i5 : 0, i4 == 1 ? i5 : 0);
            AccessibilityFloatingMenuView.this.fadeOut();
            AccessibilityFloatingMenuView.this.mOnDragEndListener.ifPresent(new Consumer() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    Position position2 = AccessibilityFloatingMenuView.this.mPosition;
                    AccessibilityFloatingMenu accessibilityFloatingMenu = ((AccessibilityFloatingMenu$$ExternalSyntheticLambda0) obj).f$0;
                    accessibilityFloatingMenu.getClass();
                    float f = position2.mPercentageX;
                    float f2 = position2.mPercentageY;
                    int i6 = accessibilityFloatingMenu.mContext.getResources().getConfiguration().orientation;
                    StatsEvent.Builder newBuilder = StatsEvent.newBuilder();
                    newBuilder.setAtomId(393);
                    newBuilder.writeFloat(f);
                    newBuilder.writeFloat(f2);
                    newBuilder.writeInt(i6);
                    newBuilder.usePooledBuffer();
                    StatsLog.write(newBuilder.build());
                    Prefs.putString(accessibilityFloatingMenu.mContext, "AccessibilityFloatingMenuPosition", position2.toString());
                }
            });
        }
    }

    public AccessibilityFloatingMenuView(Context context, Position position) {
        this(context, position, new RecyclerView(context));
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

    public static int transformToAlignment(float f) {
        return f < 0.5f ? 0 : 1;
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

    public final int getMarginStartEndWith(Configuration configuration) {
        if (configuration == null || configuration.orientation != 1) {
            return 0;
        }
        return this.mMargin;
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
                layoutWidth = (this.mDisplayWidth - getLayoutWidth()) - getContext().getResources().getDimensionPixelSize(R.dimen.navigation_bar_size);
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
                dimensionPixelSize = (this.mDisplayWidth - this.mHideHandleWidth) - getContext().getResources().getDimensionPixelSize(R.dimen.navigation_bar_size);
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
        return -getMarginStartEndWith(this.mLastConfiguration);
    }

    public final int getMinWindowXForHandle() {
        return offsetForLeftNaviBar() ? this.mNavigationBarHeight : -getMarginStartEndWith(this.mLastConfiguration);
    }

    public final int getNavigationBarHeight() {
        if (getResources().getBoolean(android.R.bool.config_sms_decode_gsm_8bit_data)) {
            return getResources().getDimensionPixelSize(android.R.dimen.resolver_max_collapsed_height);
        }
        return 0;
    }

    public final int getWindowHeight() {
        int i = this.mDisplayHeight;
        int i2 = this.mMargin;
        return Math.min(i, Math.min(i - (i2 * 2), ((ArrayList) this.mTargets).size() * ((this.mPadding * 2) + this.mIconHeight)) + (i2 * 2));
    }

    public final int getWindowWidth() {
        return getLayoutWidth() + (getMarginStartEndWith(this.mLastConfiguration) * 2);
    }

    public boolean hasExceededMaxLayoutHeight() {
        return ((ArrayList) this.mTargets).size() * ((this.mPadding * 2) + this.mIconHeight) > this.mDisplayHeight - (this.mMargin * 2);
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
        return (this.mWindowManager.getDefaultDisplay().getRotation() != 3 || this.mHasNavigationBarGesture || isTablet()) ? false : true;
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

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0039, code lost:
    
        if ((android.util.MathUtils.sq(r0 - r7.mDownY) + android.util.MathUtils.sq(r8 - r9)) > r7.mSquareScaledTouchSlop) goto L12;
     */
    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onInterceptTouchEvent(androidx.recyclerview.widget.RecyclerView r8, android.view.MotionEvent r9) {
        /*
            Method dump skipped, instructions count: 579
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView.onInterceptTouchEvent(androidx.recyclerview.widget.RecyclerView, android.view.MotionEvent):boolean");
    }

    public final void onTargetsChanged(List list) {
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
                accessibilityFloatingMenuView.getClass();
                int action = motionEvent.getAction();
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                int marginStartEndWith = accessibilityFloatingMenuView.getMarginStartEndWith(accessibilityFloatingMenuView.mLastConfiguration);
                int i2 = accessibilityFloatingMenuView.mMargin;
                int layoutWidth = accessibilityFloatingMenuView.getLayoutWidth() + marginStartEndWith;
                int i3 = accessibilityFloatingMenuView.mMargin;
                Rect rect = new Rect(marginStartEndWith, i2, layoutWidth, Math.min(accessibilityFloatingMenuView.mDisplayHeight - (i3 * 2), ((ArrayList) accessibilityFloatingMenuView.mTargets).size() * ((accessibilityFloatingMenuView.mPadding * 2) + accessibilityFloatingMenuView.mIconHeight)) + i3);
                if (action == 0 && rect.contains(x, y)) {
                    accessibilityFloatingMenuView.mIsDownInEnlargedTouchArea = true;
                }
                if (!accessibilityFloatingMenuView.mIsDownInEnlargedTouchArea) {
                    return false;
                }
                if (action == 1 || action == 3) {
                    accessibilityFloatingMenuView.mIsDownInEnlargedTouchArea = false;
                }
                int i4 = accessibilityFloatingMenuView.mMargin;
                motionEvent.setLocation(x - i4, y - i4);
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
        post(new Runnable() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                AccessibilityFloatingMenuView accessibilityFloatingMenuView = AccessibilityFloatingMenuView.this;
                accessibilityFloatingMenuView.setSystemGestureExclusionRects(accessibilityFloatingMenuView.mIsShowing ? Collections.singletonList(rect) : Collections.emptyList());
            }
        });
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
                AccessibilityFloatingMenuView accessibilityFloatingMenuView = AccessibilityFloatingMenuView.this;
                int i3 = i;
                int i4 = i2;
                accessibilityFloatingMenuView.getClass();
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                if (accessibilityFloatingMenuView.mIsHideHandle) {
                    float f = 1.0f - floatValue;
                    WindowManager.LayoutParams layoutParams = accessibilityFloatingMenuView.mHideHandleLayoutParams;
                    layoutParams.x = (int) ((i3 * floatValue) + (layoutParams.x * f));
                    layoutParams.y = (int) ((floatValue * i4) + (f * layoutParams.y));
                    accessibilityFloatingMenuView.updateViewLayout(layoutParams);
                    return;
                }
                float f2 = 1.0f - floatValue;
                WindowManager.LayoutParams layoutParams2 = accessibilityFloatingMenuView.mCurrentLayoutParams;
                layoutParams2.x = (int) ((i3 * floatValue) + (layoutParams2.x * f2));
                layoutParams2.y = (int) ((floatValue * i4) + (f2 * layoutParams2.y));
                accessibilityFloatingMenuView.updateViewLayout(layoutParams2);
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
        Point point = new Point();
        Display defaultDisplay = ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getRealMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        point.x = i;
        point.y = displayMetrics.heightPixels;
        this.mDisplayWidth = i;
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
        this.mListView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda3
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
        this.mHideHandleLayoutParams.x = transformToAlignment(position.mPercentageX) == 1 ? getMaxWindowXForHandle() : getMinWindowXForHandle();
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
            r1 = 2131165255(0x7f070047, float:1.7944722E38)
            goto Ld
        La:
            r1 = 2131165238(0x7f070036, float:1.7944687E38)
        Ld:
            int r1 = r0.getDimensionPixelSize(r1)
            r5.mPadding = r1
            boolean r1 = com.android.internal.accessibility.util.AccessibilityUtils.isFoldedLargeCoverScreen()
            r2 = 2131165244(0x7f07003c, float:1.79447E38)
            r3 = 2131165257(0x7f070049, float:1.7944726E38)
            r4 = 9
            if (r1 == 0) goto L2b
            if (r6 != 0) goto L27
            r2 = 2131165253(0x7f070045, float:1.7944718E38)
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
            r2 = 2131165240(0x7f070038, float:1.7944692E38)
        L34:
            int r6 = r0.getDimensionPixelSize(r2)
            r5.mIconWidth = r6
            r5.mIconHeight = r6
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView.updateItemViewDimensionsWith(int):void");
    }

    public final void updateLocationWith(Position position) {
        this.mCurrentLayoutParams.x = transformToAlignment(position.mPercentageX) == 1 ? getMaxWindowX() : getMinWindowX();
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
            r2 = 2131165243(0x7f07003b, float:1.7944698E38)
            r3 = 2131165254(0x7f070046, float:1.794472E38)
            r4 = 9
            if (r1 == 0) goto L1c
            if (r6 != 0) goto L18
            r2 = 2131165252(0x7f070044, float:1.7944716E38)
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
            r2 = 2131165237(0x7f070035, float:1.7944685E38)
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

    public AccessibilityFloatingMenuView(Context context, Position position, RecyclerView recyclerView) {
        super(context);
        this.mIsDragging = false;
        this.mSizeType = 0;
        this.mShapeType = 0;
        this.mDisplayInsetsRect = new Rect();
        this.mImeInsetsRect = new Rect();
        this.mOnDragEndListener = Optional.empty();
        ArrayList arrayList = new ArrayList();
        this.mTargets = arrayList;
        this.mIsHideHandle = false;
        this.mHideHandleWidth = 0;
        this.mHideHandleHeight = 0;
        this.mHasNavigationBarGesture = false;
        this.mIsLongClicked = false;
        this.mIsRepeatVibrations = false;
        this.mIsSwipeForHandle = false;
        this.mHandleFirstPositionY = 0;
        this.mNavigationBarHeight = 0;
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
            public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                AccessibilityFloatingMenuView.this.fadeIn();
                AccessibilityFloatingMenuView accessibilityFloatingMenuView = AccessibilityFloatingMenuView.this;
                Rect rect = accessibilityFloatingMenuView.mIsHideHandle ? new Rect(0, 0, accessibilityFloatingMenuView.mDisplayWidth - accessibilityFloatingMenuView.mHideHandleWidth, accessibilityFloatingMenuView.mDisplayHeight - accessibilityFloatingMenuView.mHideHandleHeight) : new Rect(0, 0, accessibilityFloatingMenuView.mDisplayWidth - accessibilityFloatingMenuView.getWindowWidth(), accessibilityFloatingMenuView.mDisplayHeight - accessibilityFloatingMenuView.getWindowHeight());
                if (i == R.id.action_move_top_left) {
                    AccessibilityFloatingMenuView.this.setShapeType(0);
                    AccessibilityFloatingMenuView.this.snapToLocation(rect.left, rect.top);
                    AccessibilityFloatingMenuView accessibilityFloatingMenuView2 = AccessibilityFloatingMenuView.this;
                    accessibilityFloatingMenuView2.mListView.announceForAccessibility(accessibilityFloatingMenuView2.getContext().getString(R.string.accessibility_floating_button_action_move_top_left_feedback));
                    return true;
                }
                if (i == R.id.action_move_top_right) {
                    AccessibilityFloatingMenuView.this.setShapeType(0);
                    AccessibilityFloatingMenuView.this.snapToLocation(rect.right, rect.top);
                    AccessibilityFloatingMenuView accessibilityFloatingMenuView3 = AccessibilityFloatingMenuView.this;
                    accessibilityFloatingMenuView3.mListView.announceForAccessibility(accessibilityFloatingMenuView3.getContext().getString(R.string.accessibility_floating_button_action_move_top_right_feedback));
                    return true;
                }
                if (i == R.id.action_move_bottom_left) {
                    AccessibilityFloatingMenuView.this.setShapeType(0);
                    AccessibilityFloatingMenuView.this.snapToLocation(rect.left, rect.bottom);
                    AccessibilityFloatingMenuView accessibilityFloatingMenuView4 = AccessibilityFloatingMenuView.this;
                    accessibilityFloatingMenuView4.mListView.announceForAccessibility(accessibilityFloatingMenuView4.getContext().getString(R.string.accessibility_floating_button_action_move_bottom_left_feedback));
                    return true;
                }
                if (i == R.id.action_move_bottom_right) {
                    AccessibilityFloatingMenuView.this.setShapeType(0);
                    AccessibilityFloatingMenuView.this.snapToLocation(rect.right, rect.bottom);
                    AccessibilityFloatingMenuView accessibilityFloatingMenuView5 = AccessibilityFloatingMenuView.this;
                    accessibilityFloatingMenuView5.mListView.announceForAccessibility(accessibilityFloatingMenuView5.getContext().getString(R.string.accessibility_floating_button_action_move_bottom_right_feedback));
                    return true;
                }
                if (i != R.id.action_move_to_edge_and_hide) {
                    return super.performAccessibilityAction(view, i, bundle);
                }
                AccessibilityFloatingMenuView accessibilityFloatingMenuView6 = AccessibilityFloatingMenuView.this;
                accessibilityFloatingMenuView6.updateHideHandle((accessibilityFloatingMenuView6.mHideHandleHeight / 2) + accessibilityFloatingMenuView6.mCurrentLayoutParams.y);
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
                    accessibilityFloatingMenuView.updateHideHandle((accessibilityFloatingMenuView.mHideHandleHeight / 2) + accessibilityFloatingMenuView.mCurrentLayoutParams.y);
                    AccessibilityFloatingMenuView accessibilityFloatingMenuView2 = AccessibilityFloatingMenuView.this;
                    accessibilityFloatingMenuView2.updateViewLayout(accessibilityFloatingMenuView2.mHideHandleLayoutParams);
                }
            }
        };
        this.mListView = recyclerView;
        this.mWindowManager = (WindowManager) context.getSystemService(WindowManager.class);
        this.mLastConfiguration = new Configuration(getResources().getConfiguration());
        this.mAdapter = new AccessibilityTargetAdapter(arrayList);
        Looper myLooper = Looper.myLooper();
        Objects.requireNonNull(myLooper, "looper must not be null");
        this.mUiHandler = new Handler(myLooper);
        this.mPosition = position;
        int transformToAlignment = transformToAlignment(position.mPercentageX);
        this.mAlignment = transformToAlignment;
        this.mRadiusType = transformToAlignment == 1 ? 0 : 2;
        updateDimensions();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, 2024, 520, -3);
        layoutParams.receiveInsetsIgnoringZOrder = true;
        layoutParams.privateFlags |= QuickStepContract.SYSUI_STATE_DEVICE_DOZING;
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
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuView$$ExternalSyntheticLambda4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                AccessibilityFloatingMenuView accessibilityFloatingMenuView = AccessibilityFloatingMenuView.this;
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

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public final void onRequestDisallowInterceptTouchEvent(boolean z) {
    }

    @Override // android.view.View, androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public final void onTouchEvent(MotionEvent motionEvent) {
    }
}
