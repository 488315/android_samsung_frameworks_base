package com.android.systemui.statusbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import com.android.app.animation.Interpolators;
import com.android.internal.policy.SystemBarUtils;
import com.android.keyguard.BouncerPanelExpansionCalculator;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.ShelfToolTipManager;
import com.android.systemui.animation.ShadeInterpolation;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.transition.LargeScreenShadeInterpolator;
import com.android.systemui.statusbar.notification.ColorUpdateLogger;
import com.android.systemui.statusbar.notification.PhysicsPropertyAnimator;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationView;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.NotificationBackgroundView;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.notification.shared.NotificationIconContainerRefactor;
import com.android.systemui.statusbar.notification.shared.NotificationMinimalism;
import com.android.systemui.statusbar.notification.shelf.NotificationShelfBackgroundView;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.notification.stack.ExpandableViewState;
import com.android.systemui.statusbar.notification.stack.NotificationRoundnessManager;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm;
import com.android.systemui.statusbar.notification.stack.ViewState;
import com.android.systemui.statusbar.phone.NotificationIconContainer;
import com.android.systemui.statusbar.phone.SecShelfNotificationIconContainer;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.widget.SemTipPopup;
import java.io.PrintWriter;
import java.util.ArrayList;

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
    public int mPopOverHeight;
    public NotificationRoundnessManager mRoundnessManager;
    public int mScrollFastThreshold;
    private SecShelfNotificationIconContainer mShelfIcons;
    public NotificationShelfManager mShelfManager;
    public boolean mShowNotificationShelf;
    public int mStatusBarState;
    public static final Interpolator ICON_ALPHA_INTERPOLATOR = new PathInterpolator(0.6f, 0.0f, 0.6f, 0.0f);
    public static final SourceType$Companion$from$1 BASE_VALUE = SourceType.from("BaseValue");
    public static final SourceType$Companion$from$1 SHELF_SCROLL = SourceType.from("ShelfScroll");

    public class ShelfState extends ExpandableViewState {
        public ExpandableView firstViewInShelf;
        public boolean hasItemsInStableShelf;

        public ShelfState() {
        }

        @Override // com.android.systemui.statusbar.notification.stack.ExpandableViewState, com.android.systemui.statusbar.notification.stack.ViewState
        public final void animateTo(View view, AnimationProperties animationProperties) {
            NotificationShelf notificationShelf = NotificationShelf.this;
            if (notificationShelf.mShowNotificationShelf) {
                if (notificationShelf.mStatusBarState == 2 && (!NotiRune.NOTI_STYLE_POP_OVER_SHELF || !((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet() || notificationShelf.mAmbientState.mLastVisibleBackgroundChild == null)) {
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
                Float fValueOf = Float.valueOf(iconState.iconAppearAmount);
                panelScreenShotLogger.getClass();
                PanelScreenShotLogger.addLogItem(arrayList, "iconAppearAmount", fValueOf);
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

    /* JADX WARN: Removed duplicated region for block: B:107:0x01a4  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0115  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public float getAmountInShelf(int i, ExpandableView expandableView, boolean z, boolean z2, boolean z3, float f) {
        float fConstrain;
        float fMin;
        NotificationIconContainer.IconState iconState;
        View shelfTransformationTarget;
        float translationY = expandableView.getTranslationY();
        int iMin = expandableView.mActualHeight + this.mPaddingBetweenElements;
        float translationY2 = expandableView.getShelfTransformationTarget() == null ? expandableView.getTranslationY() : (expandableView.getTranslationY() + expandableView.getRelativeTopPadding(r2)) - expandableView.getShelfIcon().getTop();
        float fMin2 = Math.min((iMin + translationY) - translationY2, getHeight());
        if (z3) {
            iMin = Math.min(iMin, expandableView.getMinHeight(false) - getHeight());
            if (!this.mAmbientState.isOnKeyguard$1() || expandableView.getMinHeight(false) != getHeight()) {
                fMin2 = Math.min(fMin2, expandableView.getMinHeight(false) - getHeight());
            }
        }
        float f2 = iMin;
        if (translationY + f2 < f || (!this.mAmbientState.mShadeExpanded && (expandableView.isPinned() || expandableView.isHeadsUpAnimatingAway()))) {
            fConstrain = 0.0f;
        } else {
            if (translationY < f && Math.abs(translationY - f) > 0.001f) {
                float f3 = f - translationY;
                fMin = 1.0f - Math.min(1.0f, f3 / f2);
                fConstrain = 1.0f - MathUtils.constrain(z3 ? f3 / (translationY2 - translationY) : (f - translationY2) / fMin2, 0.0f, 1.0f);
                StatusBarIconView shelfIcon = expandableView.getShelfIcon();
                SecShelfNotificationIconContainer secShelfNotificationIconContainer = this.mShelfIcons;
                iconState = secShelfNotificationIconContainer != null ? null : (NotificationIconContainer.IconState) secShelfNotificationIconContainer.mIconStates.get(shelfIcon);
                if (iconState != null) {
                    float f4 = (fConstrain > 0.5f || ((shelfTransformationTarget = expandableView.getShelfTransformationTarget()) != null && ((expandableView.getTranslationY() + 0.0f) + ((float) expandableView.getRelativeTopPadding(shelfTransformationTarget))) + ((float) shelfTransformationTarget.getHeight()) >= getTranslationY() - ((float) this.mPaddingBetweenElements))) ? 1.0f : 0.0f;
                    if (fConstrain == f4) {
                        iconState.noAnimations = (z || z2) && !z3;
                    }
                    if (!z3) {
                        if (z) {
                            iconState.cancelAnimations(shelfIcon);
                            iconState.noAnimations = true;
                        } else if (z2) {
                            ViewState.AnonymousClass1 anonymousClass1 = ViewState.NO_NEW_ANIMATIONS;
                            if (!ViewState.isAnimating(shelfIcon, PhysicsPropertyAnimator.TAG_ANIMATOR_TRANSLATION_Y)) {
                            }
                        }
                    }
                    if (!this.mAmbientState.isHiddenAtAll() || expandableView.mInShelf) {
                        iconState.needsCannedAnimation = iconState.clampedAppearAmount != f4;
                    } else {
                        fConstrain = this.mAmbientState.isFullyHidden() ? 1.0f : 0.0f;
                    }
                    iconState.clampedAppearAmount = f4;
                    if (expandableView instanceof ExpandableNotificationRow) {
                        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
                        StatusBarIconView shelfIcon2 = expandableNotificationRow.getShelfIcon();
                        SecShelfNotificationIconContainer secShelfNotificationIconContainer2 = this.mShelfIcons;
                        NotificationIconContainer.IconState iconState2 = secShelfNotificationIconContainer2 != null ? (NotificationIconContainer.IconState) secShelfNotificationIconContainer2.mIconStates.get(shelfIcon2) : null;
                        if (iconState2 != null) {
                            iconState2.setAlpha(((PathInterpolator) ICON_ALPHA_INTERPOLATOR).getInterpolation(fConstrain));
                            if ((!expandableNotificationRow.mDrawingAppearAnimation || expandableNotificationRow.mInShelf) && ((!expandableNotificationRow.mIsMinimized || !this.mShelfIcons.mIsShowingOverflowDot || iconState2.visibleState == 1) && ((fConstrain != 0.0f || ViewState.isAnimating(shelfIcon2)) && !expandableNotificationRow.isAboveShelf() && !expandableNotificationRow.showingPulsing()))) {
                                float translationZ = expandableNotificationRow.getTranslationZ();
                                this.mAmbientState.getClass();
                                boolean z4 = translationZ > ((float) 0);
                                iconState2.hidden = z4;
                                iconState2.iconAppearAmount = z4 ? 0.0f : fConstrain;
                                iconState2.setXTranslation((this.mShelfIcons.getWidth() / 2.0f) - (this.mShelfIcons.mIconSize / 2.0f));
                                if (expandableNotificationRow.mInShelf && !expandableNotificationRow.mTransformingInShelf) {
                                    iconState2.iconAppearAmount = 1.0f;
                                    iconState2.setAlpha(1.0f);
                                    iconState2.hidden = false;
                                }
                                if (this.mAmbientState.isOnKeyguard$1()) {
                                    iconState2.noAnimations = true;
                                }
                            }
                        }
                    }
                }
                return fMin;
            }
            fConstrain = 1.0f;
        }
        fMin = fConstrain;
        StatusBarIconView shelfIcon3 = expandableView.getShelfIcon();
        SecShelfNotificationIconContainer secShelfNotificationIconContainer3 = this.mShelfIcons;
        if (secShelfNotificationIconContainer3 != null) {
        }
        if (iconState != null) {
        }
        return fMin;
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
    public final void onConfigurationChanged(Configuration configuration) throws Resources.NotFoundException {
        super.onConfigurationChanged(configuration);
        updateResources$3();
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, android.view.View
    public void onFinishInflate() throws Resources.NotFoundException {
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

    /* JADX WARN: Removed duplicated region for block: B:105:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x01dc  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x028b  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x014e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateAppearance() {
        boolean z;
        float f;
        int i;
        boolean z2;
        int i2;
        boolean z3;
        int height;
        float f2;
        boolean z4 = true;
        if (this.mShowNotificationShelf) {
            this.mShelfIcons.resetViewStates();
            float translationY = getTranslationY();
            AmbientState ambientState = this.mAmbientState;
            ExpandableView expandableView = ambientState.mLastVisibleBackgroundChild;
            this.mNotGoneIndex = -1;
            boolean z5 = ambientState.mCurrentScrollVelocity > ((float) this.mScrollFastThreshold) || (ambientState.mExpansionChanging && Math.abs(ambientState.mExpandingVelocity) > ((float) this.mScrollFastThreshold));
            AmbientState ambientState2 = this.mAmbientState;
            boolean z6 = ambientState2.mExpansionChanging && !ambientState2.mPanelTracking;
            int i3 = 0;
            ActivatableNotificationView activatableNotificationView = null;
            int i4 = 0;
            boolean z7 = false;
            int iMax = 0;
            float f3 = 0.0f;
            while (i3 < this.mHostLayout.getChildCount()) {
                ExpandableView expandableView2 = (ExpandableView) this.mHostLayout.getChildAt(i3);
                if (!expandableView2.needsClippingToShelf() || expandableView2.getVisibility() == 8) {
                    i = i3;
                    z2 = z4;
                    activatableNotificationView = activatableNotificationView;
                    i4 = i4;
                } else {
                    boolean z8 = (expandableView2.isSummaryWithChildren() && (expandableView2 instanceof ExpandableNotificationRow) && this.mAmbientState.isOnKeyguard$1() && ((ExpandableNotificationRow) expandableView2).mHasUserChangedExpansion) ? z4 : z7;
                    if (((ValueAnimator) expandableView2.getTag(ViewState.TAG_ANIMATOR_TRANSLATION_Z)) == null) {
                        expandableView2.getTranslationZ();
                    } else {
                        ((Float) expandableView2.getTag(ViewState.TAG_END_TRANSLATION_Z)).getClass();
                    }
                    boolean zIsPinned = expandableView2.isPinned();
                    if (this.mShelfManager.statusBarState == z4 && expandableView2 == expandableView) {
                        i2 = i4;
                        z3 = z4;
                    } else {
                        i2 = i4;
                        z3 = false;
                    }
                    float translationY2 = expandableView2.getTranslationY();
                    z2 = z4;
                    float translationY3 = getTranslationY() - this.mPaddingBetweenElements;
                    ShelfState shelfState = (ShelfState) this.mViewState;
                    boolean z9 = expandableView2 instanceof ActivatableNotificationView;
                    int i5 = i3;
                    if (z9 && this.mAmbientState.isOnKeyguard$1()) {
                        ActivatableNotificationView activatableNotificationView2 = (ActivatableNotificationView) expandableView2;
                        if (z3 && activatableNotificationView2.mDrawingAppearAnimation) {
                            translationY3 = shelfState.mYTranslation - this.mPaddingBetweenElements;
                        }
                    }
                    ActivatableNotificationView activatableNotificationView3 = activatableNotificationView;
                    int i6 = i2;
                    float amountInShelf = getAmountInShelf(i5, expandableView2, z5, z6, z3, translationY3);
                    if (zIsPinned || (z3 && !expandableView2.mInShelf)) {
                        height = getHeight();
                    } else {
                        if (this.mAmbientState.isOnKeyguard$1()) {
                            shelfState.getClass();
                            if (ViewState.isAnimating(this) && translationY != shelfState.mYTranslation) {
                                height = getHeight();
                            }
                        }
                        f2 = translationY - this.mPaddingBetweenElements;
                        iMax = Math.max(updateNotificationClipHeight(expandableView2, f2, i6), iMax);
                        if (expandableView2 instanceof ExpandableNotificationRow) {
                            i4 = i6;
                        } else {
                            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView2;
                            f3 += amountInShelf;
                            expandableNotificationRow.calculateBgColor(false, false);
                            if (i6 != 0 || !zIsPinned) {
                                expandableNotificationRow.setAboveShelf(false);
                            }
                            i4 = i6 + 1;
                        }
                        if (z9) {
                            i = i5;
                            z7 = z8;
                            activatableNotificationView = activatableNotificationView3;
                        } else {
                            activatableNotificationView = (ActivatableNotificationView) expandableView2;
                            boolean z10 = (this.mAmbientState.isOnKeyguard$1() || this.mAmbientState.mShadeExpanded || !(activatableNotificationView instanceof ExpandableNotificationRow) || !((ExpandableNotificationRow) activatableNotificationView).mIsHeadsUp) ? false : z2;
                            AmbientState ambientState3 = this.mAmbientState;
                            boolean z11 = (ambientState3.mShadeExpanded && activatableNotificationView == ambientState3.getTrackedHeadsUpRow()) ? z2 : false;
                            if (translationY2 < translationY) {
                                NotificationRoundnessManager notificationRoundnessManager = this.mRoundnessManager;
                                if (activatableNotificationView == notificationRoundnessManager.mSwipedView || activatableNotificationView == notificationRoundnessManager.mViewBeforeSwipedView || activatableNotificationView == notificationRoundnessManager.mViewAfterSwipedView || z10 || z11 || activatableNotificationView.isAboveShelf()) {
                                    i = i5;
                                    z7 = z8;
                                } else {
                                    AmbientState ambientState4 = this.mAmbientState;
                                    if (!ambientState4.mPulsing && !ambientState4.mDozing) {
                                        float f4 = translationY2 + activatableNotificationView.mActualHeight;
                                        float f5 = this.mCornerAnimationDistance * ambientState4.mExpansionFraction;
                                        float f6 = translationY - f5;
                                        float fSaturate = translationY2 >= f6 ? MathUtils.saturate((translationY2 - f6) / f5) : 0.0f;
                                        SourceType$Companion$from$1 sourceType$Companion$from$1 = SHELF_SCROLL;
                                        i = i5;
                                        activatableNotificationView.requestTopRoundness(fSaturate, sourceType$Companion$from$1, false);
                                        activatableNotificationView.requestBottomRoundness(f4 >= f6 ? MathUtils.saturate((f4 - f6) / f5) : 0.0f, sourceType$Companion$from$1, false);
                                    }
                                    z7 = z8;
                                }
                            }
                        }
                    }
                    f2 = height + translationY;
                    iMax = Math.max(updateNotificationClipHeight(expandableView2, f2, i6), iMax);
                    if (expandableView2 instanceof ExpandableNotificationRow) {
                    }
                    if (z9) {
                    }
                }
                i3 = i + 1;
                z4 = z2;
            }
            ActivatableNotificationView activatableNotificationView4 = activatableNotificationView;
            boolean z12 = z4;
            int i7 = i4;
            for (int i8 = 0; i8 < this.mHostLayout.getTransientViewCount(); i8++) {
                View transientView = this.mHostLayout.getTransientView(i8);
                if (transientView instanceof ExpandableView) {
                    updateNotificationClipHeight((ExpandableView) transientView, getTranslationY(), -1);
                }
            }
            setClipTopAmount(iMax);
            if (this.mViewState.hidden || iMax >= getHeight() || !this.mShowNotificationShelf || ((f3 < 1.0f && this.mAmbientState.isOnKeyguard$1() && !this.mAmbientState.mDragDownOnKeyguard) || (f3 == 1.0f && activatableNotificationView4 != null && activatableNotificationView4.mDrawingAppearAnimation && this.mAmbientState.isOnKeyguard$1()))) {
                z = z12;
            } else {
                AmbientState ambientState5 = this.mAmbientState;
                if (!ambientState5.mQsEditMode) {
                    SceneContainerFlag.isUnexpectedlyInLegacyMode();
                    if (ambientState5.mQsExpansionFraction != 1.0f) {
                        AmbientState ambientState6 = this.mAmbientState;
                        if (!ambientState6.mDozing && this.mViewState.mAlpha != 0.0f && (!ambientState6.isOnKeyguard$1() || ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isNotificationAsCard() || this.mAmbientState.mDragDownOnKeyguard || f3 <= 0.0f)) {
                            z = false;
                        }
                    }
                }
            }
            float interpolation = ((PathInterpolator) Interpolators.STANDARD).getInterpolation(this.mAmbientState.mFractionToShade);
            if (this.mAmbientState.isOnKeyguard$1()) {
                float fMin = MathUtils.min(f3, this.mMaxIconsOnLockscreen + 1);
                SecShelfNotificationIconContainer secShelfNotificationIconContainer = this.mShelfIcons;
                secShelfNotificationIconContainer.getClass();
                if (fMin == 0.0f) {
                    f = 0.0f;
                } else {
                    int i9 = NotificationIconContainerRefactor.$r8$clinit;
                    float actualPaddingStart = secShelfNotificationIconContainer.getActualPaddingStart() + (MathUtils.min(fMin, secShelfNotificationIconContainer.mMaxIconsOnLockscreen + 1) * secShelfNotificationIconContainer.mIconSize);
                    float paddingEnd = secShelfNotificationIconContainer.mActualPaddingEnd;
                    if (paddingEnd == -2.1474836E9f) {
                        paddingEnd = secShelfNotificationIconContainer.getPaddingEnd();
                    }
                    f = paddingEnd + actualPaddingStart;
                }
                MathUtils.lerp(f, getWidth(), interpolation);
            }
            setVisibility(z ? 4 : 0);
            this.mShelfIcons.calculateIconXTranslations();
            this.mShelfIcons.applyIconStates();
            SecShelfNotificationIconContainer secShelfNotificationIconContainer2 = this.mShelfIcons;
            ArrayList arrayList = secShelfNotificationIconContainer2.mBgViews;
            int size = arrayList.size();
            int width = 0;
            int i10 = 0;
            while (i10 < size) {
                Object obj = arrayList.get(i10);
                i10++;
                View view = (View) obj;
                width = (((NotificationIconContainer.IconState) secShelfNotificationIconContainer2.mIconStates.get(view)).visibleState == z12 ? secShelfNotificationIconContainer2.mDotWidth + secShelfNotificationIconContainer2.mPaddingForDot : view.getWidth()) + width;
                z12 = true;
            }
            this.mBackgroundNormal.mBgWidth = (secShelfNotificationIconContainer2.getPaddingStart() * 2) + ((secShelfNotificationIconContainer2.mBgViews.size() - 1) * secShelfNotificationIconContainer2.mPaddingBetweenIcons) + width;
            for (int i11 = 0; i11 < this.mHostLayout.getChildCount(); i11++) {
                ExpandableView expandableView3 = (ExpandableView) this.mHostLayout.getChildAt(i11);
                if (expandableView3 instanceof ExpandableNotificationRow) {
                    final ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) expandableView3;
                    if (expandableView3.getVisibility() != 8) {
                        int i12 = NotificationBundleUi.$r8$clinit;
                        final StatusBarIconView statusBarIconView = expandableNotificationRow2.getEntryLegacy().mIcons.mShelfIcon;
                        ViewState.AnonymousClass1 anonymousClass1 = ViewState.NO_NEW_ANIMATIONS;
                        boolean z13 = ViewState.isAnimating(statusBarIconView, PhysicsPropertyAnimator.TAG_ANIMATOR_TRANSLATION_Y) && !this.mAmbientState.mDozing;
                        boolean z14 = statusBarIconView.getTag(R.id.continuous_clipping_tag) != null;
                        if (z13 && !z14) {
                            final ViewTreeObserver viewTreeObserver = statusBarIconView.getViewTreeObserver();
                            final ViewTreeObserver.OnPreDrawListener onPreDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.statusbar.NotificationShelf.1
                                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                                public final boolean onPreDraw() {
                                    StatusBarIconView statusBarIconView2 = statusBarIconView;
                                    ViewState.AnonymousClass1 anonymousClass12 = ViewState.NO_NEW_ANIMATIONS;
                                    if (!ViewState.isAnimating(statusBarIconView2, PhysicsPropertyAnimator.TAG_ANIMATOR_TRANSLATION_Y)) {
                                        if (viewTreeObserver.isAlive()) {
                                            viewTreeObserver.removeOnPreDrawListener(this);
                                        }
                                        StatusBarIconView statusBarIconView3 = statusBarIconView;
                                        Interpolator interpolator = NotificationShelf.ICON_ALPHA_INTERPOLATOR;
                                        statusBarIconView3.setTag(R.id.continuous_clipping_tag, null);
                                        return true;
                                    }
                                    NotificationShelf notificationShelf = NotificationShelf.this;
                                    ExpandableNotificationRow expandableNotificationRow3 = expandableNotificationRow2;
                                    Interpolator interpolator2 = NotificationShelf.ICON_ALPHA_INTERPOLATOR;
                                    notificationShelf.getClass();
                                    float translationY4 = expandableNotificationRow3.getTranslationY();
                                    if (notificationShelf.mClipTopAmount != 0) {
                                        translationY4 = Math.max(translationY4, notificationShelf.getTranslationY() + notificationShelf.mClipTopAmount);
                                    }
                                    int i13 = NotificationBundleUi.$r8$clinit;
                                    StatusBarIconView statusBarIconView4 = expandableNotificationRow3.getEntryLegacy().mIcons.mShelfIcon;
                                    float translationY5 = statusBarIconView4.getTranslationY() + notificationShelf.getTranslationY() + statusBarIconView4.getTop();
                                    if (translationY5 >= translationY4 || notificationShelf.mAmbientState.isFullyHidden()) {
                                        statusBarIconView4.setClipBounds(null);
                                        return true;
                                    }
                                    int i14 = (int) (translationY4 - translationY5);
                                    statusBarIconView4.setClipBounds(new Rect(0, i14, statusBarIconView4.getWidth(), Math.max(i14, statusBarIconView4.getHeight())));
                                    return true;
                                }
                            };
                            viewTreeObserver.addOnPreDrawListener(onPreDrawListener);
                            statusBarIconView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener(this) { // from class: com.android.systemui.statusbar.NotificationShelf.2
                                @Override // android.view.View.OnAttachStateChangeListener
                                public final void onViewDetachedFromWindow(View view2) {
                                    if (view2 == statusBarIconView) {
                                        if (viewTreeObserver.isAlive()) {
                                            viewTreeObserver.removeOnPreDrawListener(onPreDrawListener);
                                        }
                                        StatusBarIconView statusBarIconView2 = statusBarIconView;
                                        Interpolator interpolator = NotificationShelf.ICON_ALPHA_INTERPOLATOR;
                                        statusBarIconView2.setTag(R.id.continuous_clipping_tag, null);
                                    }
                                }

                                @Override // android.view.View.OnAttachStateChangeListener
                                public final void onViewAttachedToWindow(View view2) {
                                }
                            });
                            statusBarIconView.setTag(R.id.continuous_clipping_tag, onPreDrawListener);
                        }
                    }
                }
            }
            boolean z15 = (!this.mAmbientState.isOnKeyguard$1() || this.mAmbientState.mDragDownOnKeyguard) ? true : (this.mAmbientState.isOnKeyguard$1() && z && ((ShelfState) this.mViewState).hasItemsInStableShelf && z7) ? false : z;
            if (this.mHideBackground != z15) {
                this.mHideBackground = z15;
                updateBackground();
                if (!this.mCustomOutline) {
                    setOutlineProvider(needsOutline() ? this.mProvider : null);
                }
            }
            if (this.mNotGoneIndex == -1) {
                this.mNotGoneIndex = i7;
            }
        }
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
        boolean zShowingPulsing = true;
        boolean z = (expandableView.isPinned() || expandableView.isHeadsUpAnimatingAway()) && !this.mAmbientState.isDozingAndNotPulsing(expandableView);
        if (!this.mAmbientState.isPulseExpanding()) {
            zShowingPulsing = expandableView.showingPulsing();
        } else if (i != 0) {
            zShowingPulsing = false;
        }
        if (!z || this.mAmbientState.mShadeExpanded) {
            if (translationY <= f || zShowingPulsing) {
                expandableView.setClipBottomAmount(0);
            } else {
                expandableView.setClipBottomAmount(this.mEnableNotificationClipping ? (int) (translationY - f) : 0);
            }
        }
        if (zShowingPulsing) {
            return (int) (translationY - getTranslationY());
        }
        return 0;
    }

    public final void updateResources$3() throws Resources.NotFoundException {
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
        if (NotiRune.NOTI_STYLE_POP_OVER_SHELF) {
            this.mPopOverHeight = resources.getDimensionPixelSize(R.dimen.sec_notification_stack_min_height_tablet);
        }
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
            int iIndexOf = stackScrollAlgorithmState.visibleChildren.indexOf(stackScrollAlgorithmState.firstViewInShelf);
            if (this.mAmbientState.mExpansionChanging && stackScrollAlgorithmState.firstViewInShelf != null && iIndexOf > 0 && ((ExpandableView) stackScrollAlgorithmState.visibleChildren.get(iIndexOf - 1)).mViewState.hidden) {
                shelfState.hidden = true;
            }
        } else if (!this.mAmbientState.mShadeExpanded) {
            shelfState.hidden = true;
            shelfState.location = 64;
            shelfState.hasItemsInStableShelf = false;
        }
        int i2 = SceneContainerFlag.$r8$clinit;
        float stackY = ambientState.getStackY() + ambientState.mStackHeight;
        if (!NotiRune.NOTI_STYLE_POP_OVER_SHELF || !((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet()) {
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
                return;
            }
            return;
        }
        if (ambientState.isOnKeyguard$1()) {
            if (ambientState.mFractionToShade == 0.0f) {
                shelfState.setYTranslation((stackY - shelfState.height) + 0.0f);
                return;
            } else if (expandableView == null) {
                shelfState.setYTranslation(((this.mPopOverHeight + this.mAmbientState.mLargeScreenShadeHeaderHeight) - MathUtils.lerp(shelfState.height, this.mShelfManager.getPanelShelfHeight(), this.mAmbientState.mFractionToShade)) + 0.0f);
                return;
            } else {
                shelfState.setYTranslation((Math.max(stackY, this.mPopOverHeight + this.mAmbientState.mLargeScreenShadeHeaderHeight) - MathUtils.lerp(shelfState.height, this.mShelfManager.getPanelShelfHeight(), this.mAmbientState.mFractionToShade)) + 0.0f);
                return;
            }
        }
        float f4 = ambientState.mExpansionFraction;
        if (f4 == 0.0f) {
            shelfState.setYTranslation((stackY - shelfState.height) + 0.0f);
            return;
        }
        if (f4 < 1.0f) {
            SceneContainerFlag.isUnexpectedlyInLegacyMode();
            if (ambientState.mQsExpansionFraction != 0.0f) {
                SceneContainerFlag.isUnexpectedlyInLegacyMode();
                if (ambientState.mQsExpansionFraction != 1.0f) {
                    shelfState.setYTranslation(((ambientState.mQsPanelHeight + this.mAmbientState.mLargeScreenShadeHeaderHeight) - getResources().getDimensionPixelSize(R.dimen.qs_icon_size)) - shelfState.height);
                    return;
                }
            }
            shelfState.setYTranslation(Math.max(stackY, this.mPopOverHeight + this.mAmbientState.mLargeScreenShadeHeaderHeight) - shelfState.height);
            return;
        }
        SceneContainerFlag.isUnexpectedlyInLegacyMode();
        if (ambientState.mQsExpansionFraction == 0.0f) {
            shelfState.setYTranslation(Math.max(stackY, this.mPopOverHeight + this.mAmbientState.mLargeScreenShadeHeaderHeight) - shelfState.height);
            return;
        }
        SceneContainerFlag.isUnexpectedlyInLegacyMode();
        if (ambientState.mQsExpansionFraction < 1.0f) {
            shelfState.setYTranslation(((ambientState.mQsPanelHeight + this.mAmbientState.mLargeScreenShadeHeaderHeight) - getResources().getDimensionPixelSize(R.dimen.qs_icon_size)) - shelfState.height);
        } else {
            shelfState.setYTranslation(DeviceState.getDisplayHeight(((FrameLayout) this).mContext));
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
