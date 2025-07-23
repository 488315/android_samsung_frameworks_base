package com.android.systemui.statusbar;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import com.android.internal.policy.SystemBarUtils;
import com.android.keyguard.BouncerPanelExpansionCalculator;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.ShelfToolTipManager;
import com.android.systemui.animation.ShadeInterpolation;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.transition.LargeScreenShadeInterpolator;
import com.android.systemui.statusbar.notification.ColorUpdateLogger;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationView;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.NotificationBackgroundView;
import com.android.systemui.statusbar.notification.shared.NotificationMinimalism;
import com.android.systemui.statusbar.notification.shelf.NotificationShelfBackgroundView;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.notification.stack.ExpandableViewState;
import com.android.systemui.statusbar.notification.stack.NotificationRoundnessManager;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm;
import com.android.systemui.statusbar.phone.NotificationIconContainer;
import com.android.systemui.statusbar.phone.SecShelfNotificationIconContainer;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.samsung.android.widget.SemTipPopup;
import java.io.PrintWriter;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class NotificationShelf extends ActivatableNotificationView implements StatusBarStateController.StateListener, PanelScreenShotLogger.LogProvider, AmbientState.KeyguardNotiExpandListener {
    public float mActualWidth;
    public AmbientState mAmbientState;
    public boolean mAnimationsEnabled;
    public NotificationShelfBackgroundView mBackgroundNormal;
    public boolean mCanInteract;
    public final Rect mClipRect;
    public float mCornerAnimationDistance;
    public boolean mEnableNotificationClipping;
    public boolean mHasItemsInStableShelf;
    public boolean mHideBackground;
    public NotificationStackScrollLayout mHostLayout;
    public int mIndexOfFirstViewInShelf;
    public boolean mInteractive;
    public int mMaxIconsOnLockscreen;
    public int mNotGoneIndex;
    public int mPaddingBetweenElements;
    public NotificationRoundnessManager mRoundnessManager;
    public int mScrollFastThreshold;
    private SecShelfNotificationIconContainer mShelfIcons;
    public NotificationShelfManager mShelfManager;
    public boolean mShowNotificationShelf;
    public int mStatusBarState;
    public static final Interpolator ICON_ALPHA_INTERPOLATOR = new PathInterpolator(0.6f, 0.0f, 0.6f, 0.0f);
    public static final SourceType$Companion$from$1 BASE_VALUE = SourceType.from("BaseValue");
    public static final SourceType$Companion$from$1 SHELF_SCROLL = SourceType.from("ShelfScroll");

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ShelfState extends ExpandableViewState {
        public ExpandableView firstViewInShelf;
        public boolean hasItemsInStableShelf;

        public ShelfState() {
        }

        @Override // com.android.systemui.statusbar.notification.stack.ExpandableViewState, com.android.systemui.statusbar.notification.stack.ViewState
        public final void animateTo(View view, AnimationProperties animationProperties) {
            NotificationShelf notificationShelf = NotificationShelf.this;
            if (notificationShelf.mShowNotificationShelf) {
                if (notificationShelf.mStatusBarState == 2 && (!QpRune.QUICK_PANEL_CODE_FOR_POP_OVER || !((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet() || notificationShelf.mAmbientState.mLastVisibleBackgroundChild == null)) {
                    notificationShelf.setTranslationY(notificationShelf.mViewState.mYTranslation);
                }
                super.animateTo(view, animationProperties);
                notificationShelf.mIndexOfFirstViewInShelf = notificationShelf.mHostLayout.indexOfChild(this.firstViewInShelf);
                notificationShelf.updateAppearance();
                boolean z = this.hasItemsInStableShelf;
                if (notificationShelf.mHasItemsInStableShelf != z) {
                    notificationShelf.mHasItemsInStableShelf = z;
                    notificationShelf.updateInteractiveness();
                }
                notificationShelf.mShelfIcons.setAnimationsEnabled(notificationShelf.mAnimationsEnabled);
            }
        }

        @Override // com.android.systemui.statusbar.notification.stack.ExpandableViewState, com.android.systemui.statusbar.notification.stack.ViewState
        public final void applyToView(View view) {
            NotificationShelf notificationShelf = NotificationShelf.this;
            if (notificationShelf.mShowNotificationShelf) {
                super.applyToView(view);
                notificationShelf.mIndexOfFirstViewInShelf = notificationShelf.mHostLayout.indexOfChild(this.firstViewInShelf);
                notificationShelf.updateAppearance();
                boolean z = this.hasItemsInStableShelf;
                if (notificationShelf.mHasItemsInStableShelf != z) {
                    notificationShelf.mHasItemsInStableShelf = z;
                    notificationShelf.updateInteractiveness();
                }
                notificationShelf.mShelfIcons.setAnimationsEnabled(notificationShelf.mAnimationsEnabled);
                if (notificationShelf.mAmbientState.mShadeExpanded) {
                    ShelfToolTipManager shelfToolTipManager = (ShelfToolTipManager) Dependency.sDependency.getDependencyInner(ShelfToolTipManager.class);
                    if (shelfToolTipManager.mNotiSettingTip == null || shelfToolTipManager.mOrientation == shelfToolTipManager.mContext.getResources().getConfiguration().orientation || !shelfToolTipManager.needsToShow()) {
                        return;
                    }
                    shelfToolTipManager.releaseToolTip(false);
                    shelfToolTipManager.mOrientation = shelfToolTipManager.mContext.getResources().getConfiguration().orientation;
                    shelfToolTipManager.getToolTip();
                    SemTipPopup semTipPopup = shelfToolTipManager.mNotiSettingTip;
                    if (semTipPopup != null) {
                        semTipPopup.show(1);
                    }
                }
            }
        }
    }

    public NotificationShelf(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAnimationsEnabled = true;
        this.mClipRect = new Rect();
        this.mIndexOfFirstViewInShelf = -1;
        this.mActualWidth = -1.0f;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final ExpandableViewState createExpandableViewState() {
        return new ShelfState();
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, com.android.systemui.statusbar.notification.row.ExpandableOutlineView, com.android.systemui.statusbar.notification.row.ExpandableView, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        super.dump(DumpUtilsKt.asIndenting(printWriter), strArr);
    }

    @Override // com.android.systemui.logging.PanelScreenShotLogger.LogProvider
    public final ArrayList gatherState() {
        ArrayList arrayList = new ArrayList();
        PanelScreenShotLogger.INSTANCE.getClass();
        PanelScreenShotLogger.addHeaderLine("NotificationShelf", arrayList);
        ShelfState shelfState = (ShelfState) this.mViewState;
        if (shelfState != null) {
            PanelScreenShotLogger.addLogItem(arrayList, "hasItemsInStableShelf", Boolean.valueOf(shelfState.hasItemsInStableShelf));
        }
        PanelScreenShotLogger.addHeaderLine("NotificationShelfIcon", arrayList);
        for (int i = 0; i < this.mHostLayout.getChildCount(); i++) {
            StatusBarIconView shelfIcon = ((ExpandableView) this.mHostLayout.getChildAt(i)).getShelfIcon();
            SecShelfNotificationIconContainer secShelfNotificationIconContainer = this.mShelfIcons;
            NotificationIconContainer.IconState iconState = secShelfNotificationIconContainer == null ? null : (NotificationIconContainer.IconState) secShelfNotificationIconContainer.mIconStates.get(shelfIcon);
            if (iconState != null) {
                PanelScreenShotLogger panelScreenShotLogger = PanelScreenShotLogger.INSTANCE;
                Float valueOf = Float.valueOf(iconState.iconAppearAmount);
                panelScreenShotLogger.getClass();
                PanelScreenShotLogger.addLogItem(arrayList, "iconAppearAmount", valueOf);
                PanelScreenShotLogger.addLogItem(arrayList, "clampedAppearAmount", Float.valueOf(iconState.clampedAppearAmount));
                PanelScreenShotLogger.addLogItem(arrayList, "visibleState", Integer.valueOf(iconState.visibleState));
                PanelScreenShotLogger.addLogItem(arrayList, "justAdded", Boolean.valueOf(iconState.justAdded));
                PanelScreenShotLogger.addLogItem(arrayList, "needsCannedAnimation", Boolean.valueOf(iconState.needsCannedAnimation));
                PanelScreenShotLogger.addLogItem(arrayList, "iconColor", Integer.valueOf(iconState.iconColor));
                PanelScreenShotLogger.addLogItem(arrayList, "noAnimations", Boolean.valueOf(iconState.noAnimations));
                PanelScreenShotLogger.addLogItem(arrayList, "method", Integer.valueOf(this.mNotGoneIndex));
                arrayList.add("\n");
            }
        }
        return arrayList;
    }

    public final int getActualWidth() {
        float f = this.mActualWidth;
        return f > -1.0f ? (int) f : getWidth();
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x0113, code lost:
    
        if (com.android.systemui.statusbar.notification.stack.ViewState.isAnimating(r0, com.android.systemui.statusbar.notification.PhysicsPropertyAnimator.TAG_ANIMATOR_TRANSLATION_Y) == false) goto L62;
     */
    /* JADX WARN: Removed duplicated region for block: B:107:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01aa  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01d8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public float getAmountInShelf(int r10, com.android.systemui.statusbar.notification.row.ExpandableView r11, boolean r12, boolean r13, boolean r14, float r15) {
        /*
            Method dump skipped, instructions count: 475
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.NotificationShelf.getAmountInShelf(int, com.android.systemui.statusbar.notification.row.ExpandableView, boolean, boolean, boolean, float):float");
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final void getBoundsOnScreen(Rect rect, boolean z) {
        super.getBoundsOnScreen(rect, z);
        int actualWidth = getActualWidth();
        int i = NotificationMinimalism.$r8$clinit;
        if (isLayoutRtl()) {
            rect.left = rect.right - actualWidth;
        } else {
            rect.right = rect.left + actualWidth;
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView
    public final View getContentView() {
        return this.mShelfIcons;
    }

    public final SecShelfNotificationIconContainer getShelfIcons() {
        return this.mShelfIcons;
    }

    public float getShelfLeftBound() {
        if (isAlignedToRight()) {
            return getWidth() - getActualWidth();
        }
        return 0.0f;
    }

    public float getShelfRightBound() {
        return isAlignedToRight() ? getWidth() : getActualWidth();
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final boolean hasNoContentHeight() {
        return !(this.mShelfManager.statusBarState != 1);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView
    public final boolean hideBackground() {
        return this.mHideBackground;
    }

    public boolean isAlignedToEnd() {
        int i = NotificationMinimalism.$r8$clinit;
        return false;
    }

    public boolean isAlignedToRight() {
        return isLayoutRtl() ^ isAlignedToEnd();
    }

    public boolean isXInView(float f, float f2, float f3, float f4) {
        return f3 - f2 <= f && f < f4 + f2;
    }

    public boolean isYInView(float f, float f2, float f3, float f4) {
        return f3 - f2 <= f && f < f4 + f2;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableOutlineView
    public final boolean needsOutline() {
        return !this.mHideBackground && super.needsOutline();
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView, android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateResources$3();
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        SecShelfNotificationIconContainer secShelfNotificationIconContainer = (SecShelfNotificationIconContainer) findViewById(R.id.content);
        this.mShelfIcons = secShelfNotificationIconContainer;
        secShelfNotificationIconContainer.setClipChildren(false);
        this.mShelfIcons.setClipToPadding(false);
        this.mBackgroundNormal = (NotificationShelfBackgroundView) super.mBackgroundNormal;
        this.mClipToActualHeight = false;
        updateClipping$1();
        setClipChildren(false);
        setClipToPadding(false);
        this.mShelfIcons.mIsStaticLayout = false;
        requestRoundness(1.0f, 1.0f, BASE_VALUE, false);
        updateResources$3();
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        if (this.mInteractive) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND);
            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, getContext().getString(R.string.accessibility_overflow_action)));
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, com.android.systemui.statusbar.notification.row.ExpandableView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        int i5 = getResources().getDisplayMetrics().heightPixels;
        this.mClipRect.set(0, -i5, getWidth(), i5);
        SecShelfNotificationIconContainer secShelfNotificationIconContainer = this.mShelfIcons;
        if (secShelfNotificationIconContainer != null) {
            secShelfNotificationIconContainer.setClipBounds(this.mClipRect);
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public final void onStateChanged(int i) {
        this.mStatusBarState = i;
        NotificationShelfManager notificationShelfManager = this.mShelfManager;
        notificationShelfManager.statusBarState = i;
        notificationShelfManager.updateShelfLayout();
        updateInteractiveness();
        updateIconsPaddingEnd();
        this.mShelfManager.updateShelfTextArea();
    }

    @Override // android.view.View
    public final boolean performAccessibilityAction(int i, Bundle bundle) {
        return i == 262144 ? super.performAccessibilityAction(16, bundle) : super.performAccessibilityAction(i, bundle);
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final boolean pointInView(float f, float f2, float f3) {
        int i = NotificationMinimalism.$r8$clinit;
        float width = getWidth();
        float actualWidth = getActualWidth();
        float f4 = isLayoutRtl() ? width - actualWidth : 0.0f;
        if (!isLayoutRtl()) {
            width = actualWidth;
        }
        return isXInView(f, f3, f4, width) && isYInView(f2, f3, (float) this.mClipTopAmount, (float) this.mActualHeight);
    }

    public void setActualWidth(float f) {
        int i = (int) f;
        NotificationBackgroundView notificationBackgroundView = super.mBackgroundNormal;
        if (notificationBackgroundView != null) {
            notificationBackgroundView.mActualWidth = i;
        }
        int i2 = NotificationMinimalism.$r8$clinit;
        if (this.mShelfIcons != null) {
            isAlignedToEnd();
            this.mShelfIcons.mActualLayoutWidth = i;
        }
        this.mActualWidth = f;
    }

    public final void setAnimationsEnabled(boolean z) {
        this.mAnimationsEnabled = z;
        if (z) {
            return;
        }
        this.mShelfIcons.setAnimationsEnabled(false);
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, com.android.systemui.statusbar.notification.row.ExpandableView
    public final void setFakeShadowIntensity(int i, float f, float f2, int i2) {
        if (!this.mHasItemsInStableShelf) {
            f = 0.0f;
        }
        super.setFakeShadowIntensity(i, f, f2, i2);
    }

    @Override // android.view.View
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(" (hideBackground=");
        sb.append(this.mHideBackground);
        sb.append(" notGoneIndex=");
        sb.append(this.mNotGoneIndex);
        sb.append(" hasItemsInStableShelf=");
        sb.append(this.mHasItemsInStableShelf);
        sb.append(" interactive=");
        sb.append(this.mInteractive);
        sb.append(" animationsEnabled=");
        sb.append(this.mAnimationsEnabled);
        sb.append(" showNotificationShelf=");
        sb.append(this.mShowNotificationShelf);
        sb.append(" indexOfFirstViewInShelf=");
        return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.mIndexOfFirstViewInShelf, ')');
    }

    /* JADX WARN: Removed duplicated region for block: B:111:0x01dc  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0296  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x02d7  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x02f3  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x033b  */
    /* JADX WARN: Removed duplicated region for block: B:227:0x03c7  */
    /* JADX WARN: Removed duplicated region for block: B:236:0x03e3  */
    /* JADX WARN: Removed duplicated region for block: B:239:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:242:0x02d9  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x014e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateAppearance() {
        /*
            Method dump skipped, instructions count: 998
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.NotificationShelf.updateAppearance():void");
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView
    public final void updateBackgroundColors() {
        super.updateBackgroundColors();
        ColorUpdateLogger.Companion.getClass();
    }

    public final void updateIconsPaddingEnd() {
        if (this.mStatusBarState == 1) {
            this.mShelfIcons.mActualPaddingEnd = r2.getPaddingEnd();
            return;
        }
        NotificationShelfManager notificationShelfManager = this.mShelfManager;
        if (notificationShelfManager != null) {
            SecShelfNotificationIconContainer secShelfNotificationIconContainer = this.mShelfIcons;
            notificationShelfManager.mShelfTextArea.getClass();
            secShelfNotificationIconContainer.mActualPaddingEnd = r1.getWidth() + notificationShelfManager.mIconContainerPaddingEnd;
        }
    }

    public final void updateInteractiveness() {
        this.mInteractive = this.mCanInteract && this.mHasItemsInStableShelf;
        if (!this.mAmbientState.isOnKeyguard$1()) {
            this.mInteractive = true;
        }
        setClickable(this.mInteractive);
        setFocusable(this.mInteractive);
        setImportantForAccessibility(1);
    }

    public final int updateNotificationClipHeight(ExpandableView expandableView, float f, int i) {
        float translationY = expandableView.getTranslationY() + expandableView.mActualHeight;
        boolean z = true;
        boolean z2 = (expandableView.isPinned() || expandableView.isHeadsUpAnimatingAway()) && !this.mAmbientState.isDozingAndNotPulsing(expandableView);
        if (!this.mAmbientState.isPulseExpanding()) {
            z = expandableView.showingPulsing();
        } else if (i != 0) {
            z = false;
        }
        if (!z2 || this.mAmbientState.mShadeExpanded) {
            if (translationY <= f || z) {
                expandableView.setClipBottomAmount(0);
            } else {
                expandableView.setClipBottomAmount(this.mEnableNotificationClipping ? (int) (translationY - f) : 0);
            }
        }
        if (z) {
            return (int) (translationY - getTranslationY());
        }
        return 0;
    }

    public final void updateResources$3() {
        Resources resources = getResources();
        SystemBarUtils.getStatusBarHeight(((FrameLayout) this).mContext);
        this.mPaddingBetweenElements = resources.getDimensionPixelSize(R.dimen.notification_divider_height);
        this.mMaxIconsOnLockscreen = resources.getInteger(R.integer.max_notif_icons_on_lockscreen);
        this.mShelfIcons.setPadding(resources.getDimensionPixelOffset(R.dimen.notification_shelf_icon_container_padding_start), 0, resources.getDimensionPixelOffset(R.dimen.notification_shelf_icon_container_padding_end), 0);
        this.mScrollFastThreshold = resources.getDimensionPixelOffset(R.dimen.scroll_fast_threshold);
        this.mShowNotificationShelf = resources.getBoolean(R.bool.config_showNotificationShelf);
        this.mCornerAnimationDistance = resources.getDimensionPixelSize(R.dimen.notification_corner_animation_distance);
        this.mEnableNotificationClipping = resources.getBoolean(R.bool.notification_enable_clipping);
        this.mShelfIcons.mOverrideIconColor = true;
        if (!this.mShowNotificationShelf) {
            setVisibility(8);
        }
        NotificationShelfManager notificationShelfManager = this.mShelfManager;
        if (notificationShelfManager != null) {
            notificationShelfManager.mIconContainerPaddingEnd = notificationShelfManager.context.getResources().getDimensionPixelSize(R.dimen.notification_shelf_icon_container_padding_end);
        }
        updateIconsPaddingEnd();
    }

    public final void updateState(StackScrollAlgorithm.StackScrollAlgorithmState stackScrollAlgorithmState, AmbientState ambientState) {
        float f;
        ExpandableView expandableView = ambientState.mLastVisibleBackgroundChild;
        ShelfState shelfState = (ShelfState) this.mViewState;
        LargeScreenShadeInterpolator largeScreenShadeInterpolator = ambientState.mLargeScreenShadeInterpolator;
        if (expandableView == null) {
            if (ambientState.mExpansionChanging && !ambientState.isOnKeyguard$1()) {
                float f2 = ambientState.mExpansionFraction;
                StatusBarKeyguardViewManager statusBarKeyguardViewManager = ambientState.mStatusBarKeyguardViewManager;
                if (statusBarKeyguardViewManager != null && statusBarKeyguardViewManager.isPrimaryBouncerInTransit()) {
                    shelfState.setAlpha(BouncerPanelExpansionCalculator.aboutToShowBouncerProgress(f2));
                } else if (ambientState.mIsSmallScreen) {
                    shelfState.setAlpha(ShadeInterpolation.getContentAlpha(f2));
                } else {
                    shelfState.setAlpha(largeScreenShadeInterpolator.getNotificationContentAlpha(f2));
                }
            } else if (ambientState.mDragDownOnKeyguard) {
                shelfState.setAlpha(ShadeInterpolation.getNotifContentAlpha(ambientState.mFractionToShade));
            } else {
                shelfState.setAlpha(1.0f - ambientState.mHideAmount);
            }
        }
        if (this.mShowNotificationShelf && expandableView != null) {
            ExpandableViewState expandableViewState = expandableView.mViewState;
            if (expandableViewState == null) {
                return;
            }
            shelfState.copyFrom(expandableViewState);
            shelfState.height = getHeight();
            shelfState.setZTranslation(0);
            shelfState.clipTopAmount = 0;
            if (ambientState.mExpansionChanging && !ambientState.isOnKeyguard$1()) {
                float f3 = ambientState.mExpansionFraction;
                StatusBarKeyguardViewManager statusBarKeyguardViewManager2 = ambientState.mStatusBarKeyguardViewManager;
                if (statusBarKeyguardViewManager2 != null && statusBarKeyguardViewManager2.isPrimaryBouncerInTransit()) {
                    shelfState.setAlpha(BouncerPanelExpansionCalculator.aboutToShowBouncerProgress(f3));
                } else if (ambientState.mIsSmallScreen) {
                    shelfState.setAlpha(ShadeInterpolation.getContentAlpha(f3));
                } else {
                    shelfState.setAlpha(largeScreenShadeInterpolator.getNotificationContentAlpha(f3));
                }
            } else if (ambientState.mDragDownOnKeyguard) {
                shelfState.setAlpha(ShadeInterpolation.getNotifContentAlpha(ambientState.mFractionToShade));
            } else if (this.mStatusBarState == 2 || ambientState.mPluginLockMode != 1) {
                shelfState.setAlpha(1.0f - ambientState.mHideAmount);
            } else {
                shelfState.setAlpha(0.0f);
            }
            shelfState.hideSensitive = false;
            shelfState.setXTranslation(getTranslationX());
            shelfState.hasItemsInStableShelf = expandableViewState.inShelf;
            shelfState.firstViewInShelf = stackScrollAlgorithmState.firstViewInShelf;
            int i = this.mNotGoneIndex;
            if (i != -1) {
                shelfState.notGoneIndex = Math.min(shelfState.notGoneIndex, i);
            }
            if (!shelfState.hasItemsInStableShelf && ambientState.isOnKeyguard$1() && !this.mAmbientState.mDragDownOnKeyguard) {
                if ((getTranslationY() - expandableViewState.mYTranslation) / Math.min(Math.min(getHeight() * 1.5f, expandableView.mActualHeight + this.mPaddingBetweenElements), expandableView.getMinHeight(false) - getHeight()) < 0.5f) {
                    shelfState.hasItemsInStableShelf = true;
                }
            }
            shelfState.hidden = !this.mAmbientState.mShadeExpanded;
            int indexOf = stackScrollAlgorithmState.visibleChildren.indexOf(stackScrollAlgorithmState.firstViewInShelf);
            if (this.mAmbientState.mExpansionChanging && stackScrollAlgorithmState.firstViewInShelf != null && indexOf > 0 && ((ExpandableView) stackScrollAlgorithmState.visibleChildren.get(indexOf - 1)).mViewState.hidden) {
                shelfState.hidden = true;
            }
        } else if (!this.mAmbientState.mShadeExpanded) {
            shelfState.hidden = true;
            shelfState.location = 64;
            shelfState.hasItemsInStableShelf = false;
        }
        int i2 = SceneContainerFlag.$r8$clinit;
        float stackY = ambientState.getStackY() + ambientState.mStackHeight;
        if (QpRune.QUICK_PANEL_CODE_FOR_POP_OVER && ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet() && expandableView != null) {
            if (shelfState.hidden) {
                shelfState.setYTranslation(stackY + this.mPaddingBetweenElements);
                return;
            } else {
                shelfState.setYTranslation(stackY - shelfState.height);
                return;
            }
        }
        ambientState.getInnerHeight$1();
        ambientState.getTopPadding();
        if (getResources().getConfiguration().orientation == 2 && ambientState.mNotificationScrimTop + shelfState.height > stackY) {
            shelfState.hidden = true;
        }
        if (expandableView == null && ambientState.isOnKeyguard$1()) {
            shelfState.hidden = true;
            shelfState.setYTranslation(stackY + this.mPaddingBetweenElements);
            return;
        }
        if (ambientState.isOnKeyguard$1() && !this.mAmbientState.mDragDownOnKeyguard) {
            shelfState.setYTranslation((stackY - shelfState.height) + ambientState.mExtraTopInsetForFullShadeTransition);
            return;
        }
        float displayHeight = DeviceState.getDisplayHeight(((FrameLayout) this).mContext);
        float notificationBottomPadding = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).resourcePickHelper.getTargetPicker().getNotificationBottomPadding(((FrameLayout) this).mContext) + ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getNavBarHeight(((FrameLayout) this).mContext) + shelfState.height;
        SceneContainerFlag.isUnexpectedlyInLegacyMode();
        if (ambientState.mQsExpansionFraction < 0.8f) {
            f = 0.0f;
        } else {
            SceneContainerFlag.isUnexpectedlyInLegacyMode();
            f = (ambientState.mQsExpansionFraction - 0.8f) / 0.2f;
        }
        shelfState.setYTranslation(displayHeight - ((1.0f - f) * notificationBottomPadding));
        if (ambientState.mDozeAmount == 1.0f) {
            shelfState.setYTranslation(0.0f);
        }
    }

    public NotificationShelf(Context context, AttributeSet attributeSet, boolean z) {
        super(context, attributeSet);
        this.mAnimationsEnabled = true;
        this.mClipRect = new Rect();
        this.mIndexOfFirstViewInShelf = -1;
        this.mActualWidth = -1.0f;
        this.mShowNotificationShelf = z;
        PanelScreenShotLogger.INSTANCE.addLogProvider("NotificationShelf", this);
    }
}
