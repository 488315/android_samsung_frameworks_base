package com.android.systemui.statusbar;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import com.android.app.animation.Interpolators;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.ShelfToolTipManager;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationView;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.NotificationBackgroundView;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.notification.stack.ExpandableViewState;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.ViewState;
import com.android.systemui.statusbar.phone.NotificationIconContainer;
import com.android.systemui.statusbar.phone.SecShelfNotificationIconContainer;
import com.android.systemui.util.DumpUtilsKt;
import com.samsung.android.widget.SemTipPopup;
import java.io.PrintWriter;
import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class NotificationShelf extends ActivatableNotificationView implements StatusBarStateController.StateListener, PanelScreenShotLogger.LogProvider {
    public float mActualWidth;
    public AmbientState mAmbientState;
    public boolean mAnimationsEnabled;
    public boolean mCanInteract;
    public boolean mCanModifyColorOfNotifications;
    public final Rect mClipRect;
    public NotificationShelfController mController;
    public float mCornerAnimationDistance;
    public boolean mEnableNotificationClipping;
    public boolean mHasItemsInStableShelf;
    public boolean mHideBackground;
    public NotificationStackScrollLayoutController mHostLayoutController;
    public int mIndexOfFirstViewInShelf;
    public boolean mInteractive;
    public int mNotGoneIndex;
    public int mScrollFastThreshold;
    public boolean mSensitiveRevealAnimEndabled;
    public SecShelfNotificationIconContainer mShelfIcons;
    public NotificationShelfManager mShelfManager;
    public boolean mShelfRefactorFlagEnabled;
    public boolean mShowNotificationShelf;
    public int mStatusBarState;
    public static final Interpolator ICON_ALPHA_INTERPOLATOR = new PathInterpolator(0.6f, 0.0f, 0.6f, 0.0f);
    public static final SourceType$Companion$from$1 BASE_VALUE = SourceType.from("BaseValue");
    public static final SourceType$Companion$from$1 SHELF_SCROLL = SourceType.from("ShelfScroll");

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ShelfState extends ExpandableViewState {
        public ExpandableView firstViewInShelf;
        public boolean hasItemsInStableShelf;

        public ShelfState() {
        }

        @Override // com.android.systemui.statusbar.notification.stack.ExpandableViewState, com.android.systemui.statusbar.notification.stack.ViewState
        public final void animateTo(View view, AnimationProperties animationProperties) {
            NotificationShelf notificationShelf = NotificationShelf.this;
            if (notificationShelf.mShowNotificationShelf) {
                super.animateTo(view, animationProperties);
                ExpandableView expandableView = this.firstViewInShelf;
                if (notificationShelf.mShelfRefactorFlagEnabled) {
                    throw null;
                }
                notificationShelf.mIndexOfFirstViewInShelf = notificationShelf.mHostLayoutController.mView.indexOfChild(expandableView);
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
                ExpandableView expandableView = this.firstViewInShelf;
                if (notificationShelf.mShelfRefactorFlagEnabled) {
                    throw null;
                }
                notificationShelf.mIndexOfFirstViewInShelf = notificationShelf.mHostLayoutController.mView.indexOfChild(expandableView);
                notificationShelf.updateAppearance();
                boolean z = this.hasItemsInStableShelf;
                if (notificationShelf.mHasItemsInStableShelf != z) {
                    notificationShelf.mHasItemsInStableShelf = z;
                    notificationShelf.updateInteractiveness();
                }
                notificationShelf.mShelfIcons.setAnimationsEnabled(notificationShelf.mAnimationsEnabled);
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
        for (int i = 0; i < this.mHostLayoutController.mView.getChildCount(); i++) {
            StatusBarIconView shelfIcon = ((ExpandableView) this.mHostLayoutController.mView.getChildAt(i)).getShelfIcon();
            SecShelfNotificationIconContainer secShelfNotificationIconContainer = this.mShelfIcons;
            NotificationIconContainer.IconState iconState = secShelfNotificationIconContainer == null ? null : (NotificationIconContainer.IconState) secShelfNotificationIconContainer.mIconStates.get(shelfIcon);
            if (iconState != null) {
                String.format("    iconAppearAmount:%s | clampedAppearAmount:%s | visibleState:%s | justAdded:%s | needsCannedAnimation:%s | iconColor:%s | noAnimations:%s", Float.valueOf(iconState.iconAppearAmount), Float.valueOf(iconState.clampedAppearAmount), Integer.valueOf(iconState.visibleState), Boolean.valueOf(iconState.justAdded), Boolean.valueOf(iconState.needsCannedAnimation), Integer.valueOf(iconState.iconColor), Boolean.valueOf(iconState.noAnimations));
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

    /* JADX WARN: Code restructure failed: missing block: B:60:0x0129, code lost:
    
        if ((r5.getTag(com.android.systemui.statusbar.notification.stack.ViewState.TAG_ANIMATOR_TRANSLATION_Y) != null) == false) goto L75;
     */
    /* JADX WARN: Removed duplicated region for block: B:102:0x01c7  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x01df  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0106  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x015b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public float getAmountInShelf(int i, ExpandableView expandableView, boolean z, boolean z2, boolean z3, float f) {
        float f2;
        float f3;
        NotificationIconContainer.IconState iconState;
        boolean z4;
        float f4;
        boolean z5;
        boolean z6;
        float translationY = expandableView.getTranslationY();
        int i2 = expandableView.mActualHeight + 0;
        float translationY2 = expandableView.getShelfTransformationTarget() == null ? expandableView.getTranslationY() : (expandableView.getTranslationY() + expandableView.getRelativeTopPadding(r5)) - expandableView.getShelfIcon().getTop();
        float min = Math.min((i2 + translationY) - translationY2, getHeight());
        if (z3) {
            i2 = Math.min(i2, expandableView.getMinHeight(false) - getHeight());
            if (!this.mAmbientState.isOnKeyguard() || expandableView.getMinHeight(false) != getHeight()) {
                min = Math.min(min, expandableView.getMinHeight(false) - getHeight());
            }
        }
        float f5 = i2;
        if (translationY + f5 >= f) {
            AmbientState ambientState = this.mAmbientState;
            if ((!ambientState.mUnlockHintRunning || expandableView.mInShelf) && (ambientState.mShadeExpanded || (!expandableView.isPinned() && !expandableView.isHeadsUpAnimatingAway()))) {
                if (translationY < f && Math.abs(translationY - f) > 0.001f) {
                    float f6 = f - translationY;
                    f2 = 1.0f - Math.min(1.0f, f6 / f5);
                    f3 = 1.0f - MathUtils.constrain(z3 ? f6 / (translationY2 - translationY) : (f - translationY2) / min, 0.0f, 1.0f);
                    StatusBarIconView shelfIcon = expandableView.getShelfIcon();
                    SecShelfNotificationIconContainer secShelfNotificationIconContainer = this.mShelfIcons;
                    if (secShelfNotificationIconContainer == null) {
                    }
                    if (iconState != null) {
                    }
                    return f2;
                }
                f2 = 1.0f;
                f3 = f2;
                StatusBarIconView shelfIcon2 = expandableView.getShelfIcon();
                SecShelfNotificationIconContainer secShelfNotificationIconContainer2 = this.mShelfIcons;
                iconState = secShelfNotificationIconContainer2 == null ? null : (NotificationIconContainer.IconState) secShelfNotificationIconContainer2.mIconStates.get(shelfIcon2);
                if (iconState != null) {
                    if (f3 <= 0.5f) {
                        View shelfTransformationTarget = expandableView.getShelfTransformationTarget();
                        if (!(shelfTransformationTarget != null && ((expandableView.getTranslationY() + expandableView.mContentTranslation) + ((float) expandableView.getRelativeTopPadding(shelfTransformationTarget))) + ((float) shelfTransformationTarget.getHeight()) >= getTranslationY() - ((float) 0))) {
                            z4 = false;
                            f4 = !z4 ? 1.0f : 0.0f;
                            if (f3 == f4) {
                                iconState.noAnimations = (z || z2) && !z3;
                            }
                            if (!z3) {
                                if (!z) {
                                    if (z2) {
                                    }
                                }
                                iconState.cancelAnimations(shelfIcon2);
                                iconState.noAnimations = true;
                            }
                            if (this.mAmbientState.isHiddenAtAll() || expandableView.mInShelf) {
                                iconState.needsCannedAnimation = iconState.clampedAppearAmount == f4;
                            } else {
                                f3 = this.mAmbientState.isFullyHidden() ? 1.0f : 0.0f;
                            }
                            iconState.clampedAppearAmount = f4;
                            z5 = expandableView instanceof ExpandableNotificationRow;
                            if (z5) {
                                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
                                StatusBarIconView shelfIcon3 = expandableNotificationRow.getShelfIcon();
                                SecShelfNotificationIconContainer secShelfNotificationIconContainer3 = this.mShelfIcons;
                                NotificationIconContainer.IconState iconState2 = secShelfNotificationIconContainer3 != null ? (NotificationIconContainer.IconState) secShelfNotificationIconContainer3.mIconStates.get(shelfIcon3) : null;
                                if (iconState2 != null) {
                                    iconState2.setAlpha(((PathInterpolator) ICON_ALPHA_INTERPOLATOR).getInterpolation(f3));
                                    if (!(expandableNotificationRow.mDrawingAppearAnimation && !expandableNotificationRow.mInShelf) && ((!z5 || !expandableNotificationRow.mIsLowPriority || !this.mShelfIcons.mIsShowingOverflowDot || iconState2.visibleState == 1) && ((f3 != 0.0f || iconState2.isAnimating(shelfIcon3)) && !expandableNotificationRow.isAboveShelf() && !expandableNotificationRow.showingPulsing()))) {
                                        float translationZ = expandableNotificationRow.getTranslationZ();
                                        this.mAmbientState.getClass();
                                        if (translationZ <= 0) {
                                            z6 = false;
                                            iconState2.hidden = z6;
                                            iconState2.iconAppearAmount = z6 ? 0.0f : f3;
                                            iconState2.setXTranslation(this.mShelfIcons.getActualPaddingStart());
                                            if ((expandableNotificationRow.mInShelf || expandableNotificationRow.mTransformingInShelf) ? false : true) {
                                                iconState2.iconAppearAmount = 1.0f;
                                                iconState2.setAlpha(1.0f);
                                                iconState2.hidden = false;
                                            }
                                        }
                                    }
                                    z6 = true;
                                    iconState2.hidden = z6;
                                    iconState2.iconAppearAmount = z6 ? 0.0f : f3;
                                    iconState2.setXTranslation(this.mShelfIcons.getActualPaddingStart());
                                    if ((expandableNotificationRow.mInShelf || expandableNotificationRow.mTransformingInShelf) ? false : true) {
                                    }
                                }
                            }
                        }
                    }
                    z4 = true;
                    if (!z4) {
                    }
                    if (f3 == f4) {
                    }
                    if (!z3) {
                    }
                    if (this.mAmbientState.isHiddenAtAll()) {
                    }
                    iconState.needsCannedAnimation = iconState.clampedAppearAmount == f4;
                    iconState.clampedAppearAmount = f4;
                    z5 = expandableView instanceof ExpandableNotificationRow;
                    if (z5) {
                    }
                }
                return f2;
            }
        }
        f2 = 0.0f;
        f3 = f2;
        StatusBarIconView shelfIcon22 = expandableView.getShelfIcon();
        SecShelfNotificationIconContainer secShelfNotificationIconContainer22 = this.mShelfIcons;
        if (secShelfNotificationIconContainer22 == null) {
        }
        if (iconState != null) {
        }
        return f2;
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final void getBoundsOnScreen(Rect rect, boolean z) {
        super.getBoundsOnScreen(rect, z);
        float f = this.mActualWidth;
        int width = f > -1.0f ? (int) f : getWidth();
        if (isLayoutRtl()) {
            rect.left = rect.right - width;
        } else {
            rect.right = rect.left + width;
        }
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView
    public final View getContentView() {
        return this.mShelfIcons;
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
        updateResources();
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        SecShelfNotificationIconContainer secShelfNotificationIconContainer = (SecShelfNotificationIconContainer) findViewById(R.id.content);
        this.mShelfIcons = secShelfNotificationIconContainer;
        secShelfNotificationIconContainer.setClipChildren(false);
        this.mShelfIcons.setClipToPadding(false);
        this.mClipToActualHeight = false;
        updateClipping();
        setClipChildren(false);
        setClipToPadding(false);
        this.mShelfIcons.mIsStaticLayout = false;
        requestRoundness(1.0f, 1.0f, BASE_VALUE, false);
        updateResources();
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
        if (this.mShelfRefactorFlagEnabled) {
            NotificationShelfController.Companion.getClass();
            throw new IllegalStateException("Code path not supported when Flags.NOTIFICATION_SHELF_REFACTOR is ".concat("enabled").toString());
        }
        this.mStatusBarState = i;
        NotificationShelfManager notificationShelfManager = this.mShelfManager;
        notificationShelfManager.statusBarState = i;
        notificationShelfManager.updateShelfHeight();
        updateInteractiveness();
        updateIconsPaddingEnd();
        this.mShelfManager.updateShelfTextAreaVisibility();
    }

    @Override // com.android.systemui.statusbar.notification.row.ExpandableView
    public final boolean pointInView(float f, float f2, float f3) {
        float width = getWidth();
        float f4 = this.mActualWidth;
        float width2 = f4 > -1.0f ? (int) f4 : getWidth();
        float f5 = isLayoutRtl() ? width - width2 : 0.0f;
        if (!isLayoutRtl()) {
            width = width2;
        }
        return isXInView(f, f3, f5, width) && isYInView(f2, f3, (float) this.mClipTopAmount, (float) this.mActualHeight);
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
        return "NotificationShelf(hideBackground=" + this.mHideBackground + " notGoneIndex=" + this.mNotGoneIndex + " hasItemsInStableShelf=" + this.mHasItemsInStableShelf + " statusBarState=" + this.mStatusBarState + " interactive=" + this.mInteractive + " animationsEnabled=" + this.mAnimationsEnabled + " showNotificationShelf=" + this.mShowNotificationShelf + " indexOfFirstViewInShelf=" + this.mIndexOfFirstViewInShelf + ')';
    }

    public void updateActualWidth(float f, float f2) {
        float lerp = this.mAmbientState.isOnKeyguard() ? MathUtils.lerp(f2, getWidth(), f) : getWidth();
        int i = (int) lerp;
        NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
        if (notificationBackgroundView != null) {
            notificationBackgroundView.mActualWidth = i;
        }
        SecShelfNotificationIconContainer secShelfNotificationIconContainer = this.mShelfIcons;
        if (secShelfNotificationIconContainer != null) {
            secShelfNotificationIconContainer.mActualLayoutWidth = i;
        }
        this.mActualWidth = lerp;
    }

    /* JADX WARN: Code restructure failed: missing block: B:68:0x0146, code lost:
    
        if (r17 != false) goto L86;
     */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0253  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0255  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0283  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x01e4  */
    /* JADX WARN: Removed duplicated region for block: B:280:0x0443  */
    /* JADX WARN: Removed duplicated region for block: B:282:0x0447  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x01ef  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateAppearance() {
        boolean z;
        boolean z2;
        int i;
        ExpandableView expandableView;
        boolean z3;
        boolean z4;
        float height;
        int i2;
        boolean z5;
        float f;
        boolean z6;
        if (this.mShowNotificationShelf) {
            this.mShelfIcons.resetViewStates();
            float translationY = getTranslationY();
            AmbientState ambientState = this.mAmbientState;
            ExpandableView expandableView2 = ambientState.mLastVisibleBackgroundChild;
            this.mNotGoneIndex = -1;
            if (this.mHideBackground) {
                boolean z7 = ((ShelfState) this.mViewState).hasItemsInStableShelf;
            }
            boolean z8 = ambientState.mCurrentScrollVelocity > ((float) this.mScrollFastThreshold) || (ambientState.mExpansionChanging && Math.abs(ambientState.mExpandingVelocity) > ((float) this.mScrollFastThreshold));
            AmbientState ambientState2 = this.mAmbientState;
            boolean z9 = ambientState2.mExpansionChanging && !ambientState2.mPanelTracking;
            ActivatableNotificationView activatableNotificationView = null;
            float f2 = 0.0f;
            int i3 = 0;
            int i4 = 0;
            float f3 = 0.0f;
            int i5 = 0;
            int i6 = 0;
            int i7 = 0;
            boolean z10 = false;
            int i8 = 0;
            while (!this.mShelfRefactorFlagEnabled) {
                if (i6 >= this.mHostLayoutController.mView.getChildCount()) {
                    ActivatableNotificationView activatableNotificationView2 = activatableNotificationView;
                    int i9 = i4;
                    float f4 = f3;
                    int i10 = i5;
                    int i11 = 0;
                    while (!this.mShelfRefactorFlagEnabled) {
                        if (i11 >= this.mHostLayoutController.mView.getTransientViewCount()) {
                            setClipTopAmount(i9);
                            boolean z11 = this.mViewState.hidden || i9 >= getHeight() || !this.mShowNotificationShelf || (f4 < 1.0f && this.mAmbientState.isOnKeyguard()) || (f4 == 1.0f && activatableNotificationView2 != null && activatableNotificationView2.mDrawingAppearAnimation && this.mAmbientState.isOnKeyguard());
                            ((PathInterpolator) Interpolators.STANDARD).getInterpolation(this.mAmbientState.mFractionToShade);
                            SecShelfNotificationIconContainer secShelfNotificationIconContainer = this.mShelfIcons;
                            if (f4 == 0.0f) {
                                secShelfNotificationIconContainer.getClass();
                            } else {
                                int i12 = secShelfNotificationIconContainer.mIconSize;
                                MathUtils.min(f4, secShelfNotificationIconContainer.mMaxIconsOnLockscreen + 1);
                                secShelfNotificationIconContainer.getActualPaddingStart();
                                if (secShelfNotificationIconContainer.mActualPaddingEnd == -2.1474836E9f) {
                                    secShelfNotificationIconContainer.getPaddingEnd();
                                }
                            }
                            setVisibility(z11 ? 4 : 0);
                            SecShelfNotificationIconContainer secShelfNotificationIconContainer2 = this.mShelfIcons;
                            if (this.mShelfRefactorFlagEnabled) {
                                throw null;
                            }
                            secShelfNotificationIconContainer2.mSpeedBumpIndex = this.mHostLayoutController.mView.getSpeedBumpIndex();
                            this.mShelfIcons.calculateIconXTranslations();
                            this.mShelfIcons.applyIconStates();
                            int i13 = 0;
                            while (!this.mShelfRefactorFlagEnabled) {
                                if (i13 >= this.mHostLayoutController.mView.getChildCount()) {
                                    if (this.mAmbientState.isOnKeyguard()) {
                                        z = true;
                                        if (z11) {
                                            boolean z12 = ((ShelfState) this.mViewState).hasItemsInStableShelf;
                                        }
                                    } else {
                                        z = true;
                                    }
                                    if (this.mHideBackground != z) {
                                        this.mHideBackground = z;
                                        updateBackground();
                                        if (!this.mCustomOutline) {
                                            setOutlineProvider(needsOutline() ? this.mProvider : null);
                                        }
                                    }
                                    if (this.mNotGoneIndex == -1) {
                                        this.mNotGoneIndex = i10;
                                    }
                                    ShelfToolTipManager shelfToolTipManager = (ShelfToolTipManager) Dependency.get(ShelfToolTipManager.class);
                                    if (shelfToolTipManager.mNotiSettingTip == null || shelfToolTipManager.mNotiSettingContainer == null || shelfToolTipManager.hasBottomClippedNotiRow()) {
                                        return;
                                    }
                                    NotificationShelf notificationShelf = shelfToolTipManager.mNotificationShelf;
                                    if (notificationShelf != null) {
                                        if (notificationShelf.getHeight() + ((int) notificationShelf.getTranslationY()) + 50 > shelfToolTipManager.mAmbientState.mLayoutMaxHeight) {
                                            z2 = z;
                                            if (z2) {
                                                shelfToolTipManager.releaseToolTip();
                                                return;
                                            }
                                            shelfToolTipManager.calculatePosition();
                                            SemTipPopup semTipPopup = shelfToolTipManager.mNotiSettingTip;
                                            if (semTipPopup != null) {
                                                semTipPopup.update();
                                                return;
                                            }
                                            return;
                                        }
                                    }
                                    z2 = false;
                                    if (z2) {
                                    }
                                } else {
                                    if (this.mShelfRefactorFlagEnabled) {
                                        throw null;
                                    }
                                    ExpandableView expandableView3 = (ExpandableView) this.mHostLayoutController.mView.getChildAt(i13);
                                    if ((expandableView3 instanceof ExpandableNotificationRow) && expandableView3.getVisibility() != 8) {
                                        final ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView3;
                                        final StatusBarIconView statusBarIconView = expandableNotificationRow.mEntry.mIcons.mShelfIcon;
                                        boolean z13 = (statusBarIconView.getTag(ViewState.TAG_ANIMATOR_TRANSLATION_Y) != null) && !this.mAmbientState.mDozing;
                                        boolean z14 = statusBarIconView.getTag(R.id.continuous_clipping_tag) != null;
                                        if (z13 && !z14) {
                                            final ViewTreeObserver viewTreeObserver = statusBarIconView.getViewTreeObserver();
                                            final ViewTreeObserver.OnPreDrawListener onPreDrawListener = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.statusbar.NotificationShelf.1
                                                @Override // android.view.ViewTreeObserver.OnPreDrawListener
                                                public final boolean onPreDraw() {
                                                    if (!(statusBarIconView.getTag(ViewState.TAG_ANIMATOR_TRANSLATION_Y) != null)) {
                                                        if (viewTreeObserver.isAlive()) {
                                                            viewTreeObserver.removeOnPreDrawListener(this);
                                                        }
                                                        StatusBarIconView statusBarIconView2 = statusBarIconView;
                                                        Interpolator interpolator = NotificationShelf.ICON_ALPHA_INTERPOLATOR;
                                                        statusBarIconView2.setTag(R.id.continuous_clipping_tag, null);
                                                        return true;
                                                    }
                                                    NotificationShelf notificationShelf2 = NotificationShelf.this;
                                                    ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
                                                    Interpolator interpolator2 = NotificationShelf.ICON_ALPHA_INTERPOLATOR;
                                                    notificationShelf2.getClass();
                                                    float translationY2 = expandableNotificationRow2.getTranslationY();
                                                    if (notificationShelf2.mClipTopAmount != 0) {
                                                        translationY2 = Math.max(translationY2, notificationShelf2.getTranslationY() + notificationShelf2.mClipTopAmount);
                                                    }
                                                    StatusBarIconView statusBarIconView3 = expandableNotificationRow2.mEntry.mIcons.mShelfIcon;
                                                    float translationY3 = statusBarIconView3.getTranslationY() + notificationShelf2.getTranslationY() + statusBarIconView3.getTop();
                                                    if (translationY3 >= translationY2 || notificationShelf2.mAmbientState.isFullyHidden()) {
                                                        statusBarIconView3.setClipBounds(null);
                                                    } else {
                                                        int i14 = (int) (translationY2 - translationY3);
                                                        statusBarIconView3.setClipBounds(new Rect(0, i14, statusBarIconView3.getWidth(), Math.max(i14, statusBarIconView3.getHeight())));
                                                    }
                                                    return true;
                                                }
                                            };
                                            viewTreeObserver.addOnPreDrawListener(onPreDrawListener);
                                            statusBarIconView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener(this) { // from class: com.android.systemui.statusbar.NotificationShelf.2
                                                @Override // android.view.View.OnAttachStateChangeListener
                                                public final void onViewDetachedFromWindow(View view) {
                                                    if (view == statusBarIconView) {
                                                        if (viewTreeObserver.isAlive()) {
                                                            viewTreeObserver.removeOnPreDrawListener(onPreDrawListener);
                                                        }
                                                        StatusBarIconView statusBarIconView2 = statusBarIconView;
                                                        Interpolator interpolator = NotificationShelf.ICON_ALPHA_INTERPOLATOR;
                                                        statusBarIconView2.setTag(R.id.continuous_clipping_tag, null);
                                                    }
                                                }

                                                @Override // android.view.View.OnAttachStateChangeListener
                                                public final void onViewAttachedToWindow(View view) {
                                                }
                                            });
                                            statusBarIconView.setTag(R.id.continuous_clipping_tag, onPreDrawListener);
                                        }
                                    }
                                    i13++;
                                }
                            }
                            throw null;
                        }
                        if (this.mShelfRefactorFlagEnabled) {
                            throw null;
                        }
                        View transientView = this.mHostLayoutController.mView.getTransientView(i11);
                        if (transientView instanceof ExpandableView) {
                            updateNotificationClipHeight((ExpandableView) transientView, getTranslationY(), -1);
                        }
                        i11++;
                    }
                    throw null;
                }
                if (this.mShelfRefactorFlagEnabled) {
                    throw null;
                }
                ExpandableView expandableView4 = (ExpandableView) this.mHostLayoutController.mView.getChildAt(i6);
                if (!expandableView4.needsClippingToShelf() || expandableView4.getVisibility() == 8) {
                    i = i6;
                    expandableView = expandableView2;
                    z3 = z8;
                    z4 = z9;
                    i3 = i3;
                    f2 = f2;
                    f3 = f3;
                    i5 = i5;
                    i4 = i4;
                    activatableNotificationView = activatableNotificationView;
                    i7 = i7;
                } else {
                    if (expandableView4.isSummaryWithChildren() && (expandableView4 instanceof ExpandableNotificationRow) && this.mAmbientState.isOnKeyguard() && ((ExpandableNotificationRow) expandableView4).mHasUserChangedExpansion) {
                        z10 = true;
                    }
                    if (((ValueAnimator) expandableView4.getTag(ViewState.TAG_ANIMATOR_TRANSLATION_Z)) == null) {
                        expandableView4.getTranslationZ();
                    } else {
                        ((Float) expandableView4.getTag(ViewState.TAG_END_TRANSLATION_Z)).floatValue();
                    }
                    float f5 = 0;
                    boolean isPinned = expandableView4.isPinned();
                    float f6 = f2;
                    int i14 = i3;
                    boolean z15 = !(this.mShelfManager.statusBarState != 1) && expandableView4 == expandableView2;
                    float translationY2 = expandableView4.getTranslationY();
                    float translationY3 = getTranslationY() - f5;
                    ShelfState shelfState = (ShelfState) this.mViewState;
                    expandableView = expandableView2;
                    boolean z16 = expandableView4 instanceof ActivatableNotificationView;
                    float f7 = translationY3;
                    if (z16 && this.mAmbientState.isOnKeyguard()) {
                        ActivatableNotificationView activatableNotificationView3 = (ActivatableNotificationView) expandableView4;
                        if (z15 && activatableNotificationView3.mDrawingAppearAnimation) {
                            f7 = shelfState.mYTranslation - f5;
                        }
                    }
                    ActivatableNotificationView activatableNotificationView4 = activatableNotificationView;
                    int i15 = i7;
                    int i16 = i4;
                    boolean z17 = z8;
                    z3 = z8;
                    float f8 = f3;
                    boolean z18 = z9;
                    z4 = z9;
                    int i17 = i5;
                    i = i6;
                    float amountInShelf = getAmountInShelf(i6, expandableView4, z17, z18, z15, f7);
                    if ((this.mSensitiveRevealAnimEndabled || !z15 || expandableView4.mInShelf) && !isPinned) {
                        if (this.mAmbientState.isOnKeyguard() && shelfState.isAnimating(this) && translationY != shelfState.mYTranslation) {
                            height = getHeight() + translationY;
                            i4 = Math.max(updateNotificationClipHeight(expandableView4, height, i17), i16);
                            if (expandableView4 instanceof ExpandableNotificationRow) {
                                ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) expandableView4;
                                f3 = f8 + amountInShelf;
                                int calculateBgColor = expandableNotificationRow2.calculateBgColor(false, false);
                                if (translationY2 < translationY || this.mNotGoneIndex != -1) {
                                    f = f6;
                                    i2 = i14;
                                    if (this.mNotGoneIndex == -1) {
                                        f = amountInShelf;
                                        i2 = i15;
                                    }
                                } else {
                                    this.mNotGoneIndex = i17;
                                    if (i15 != this.mBgTint) {
                                        this.mBgTint = i15;
                                        updateBackgroundTint(false);
                                    }
                                    f = f6;
                                    i2 = i14;
                                    setOverrideTintColor(f, i2);
                                }
                                if (z15) {
                                    if (this.mShelfRefactorFlagEnabled ? this.mCanModifyColorOfNotifications && this.mAmbientState.mShadeExpanded : this.mController.canModifyColorOfNotifications()) {
                                        int i18 = i8 == 0 ? calculateBgColor : i8;
                                        expandableNotificationRow2.setOverrideTintColor(amountInShelf, i18);
                                        i8 = i18;
                                        z6 = false;
                                        if (i17 == 0 || !isPinned) {
                                            expandableNotificationRow2.setAboveShelf(z6);
                                        }
                                        i17++;
                                        i7 = calculateBgColor;
                                        f2 = f;
                                    }
                                }
                                z6 = false;
                                expandableNotificationRow2.setOverrideTintColor(0.0f, 0);
                                i8 = calculateBgColor;
                                if (i17 == 0) {
                                }
                                expandableNotificationRow2.setAboveShelf(z6);
                                i17++;
                                i7 = calculateBgColor;
                                f2 = f;
                            } else {
                                i2 = i14;
                                f2 = f6;
                                i7 = i15;
                                f3 = f8;
                            }
                            if (z16) {
                                activatableNotificationView = (ActivatableNotificationView) expandableView4;
                                if (this.mSensitiveRevealAnimEndabled || !this.mAmbientState.isOnKeyguard()) {
                                    height = translationY;
                                }
                                boolean z19 = !this.mAmbientState.isOnKeyguard() && !this.mAmbientState.mShadeExpanded && (activatableNotificationView instanceof ExpandableNotificationRow) && activatableNotificationView.isHeadsUp();
                                AmbientState ambientState3 = this.mAmbientState;
                                boolean z20 = ambientState3.mShadeExpanded && activatableNotificationView == ambientState3.getTrackedHeadsUpRow();
                                if (translationY2 < height) {
                                    if (this.mShelfRefactorFlagEnabled) {
                                        throw null;
                                    }
                                    this.mHostLayoutController.mNotificationRoundnessManager.getClass();
                                    if (!z19 && !z20 && !activatableNotificationView.isAboveShelf()) {
                                        AmbientState ambientState4 = this.mAmbientState;
                                        if (!ambientState4.mPulsing && !ambientState4.mDozing) {
                                            z5 = true;
                                            if (!z5) {
                                                float f9 = activatableNotificationView.mActualHeight + translationY2;
                                                float f10 = this.mCornerAnimationDistance * this.mAmbientState.mExpansionFraction;
                                                float f11 = height - f10;
                                                if (translationY2 >= f11) {
                                                    MathUtils.saturate((translationY2 - f11) / f10);
                                                }
                                                SourceType$Companion$from$1 sourceType$Companion$from$1 = SHELF_SCROLL;
                                                activatableNotificationView.requestTopRoundness(0.0f, sourceType$Companion$from$1, false);
                                                if (f9 >= f11) {
                                                    MathUtils.saturate((f9 - f11) / f10);
                                                }
                                                activatableNotificationView.requestBottomRoundness(0.0f, sourceType$Companion$from$1, false);
                                            }
                                            i3 = i2;
                                            i5 = i17;
                                        }
                                    }
                                }
                                z5 = false;
                                if (!z5) {
                                }
                                i3 = i2;
                                i5 = i17;
                            } else {
                                i3 = i2;
                                i5 = i17;
                                activatableNotificationView = activatableNotificationView4;
                            }
                        }
                        height = translationY - f5;
                        i4 = Math.max(updateNotificationClipHeight(expandableView4, height, i17), i16);
                        if (expandableView4 instanceof ExpandableNotificationRow) {
                        }
                        if (z16) {
                        }
                    } else {
                        height = getHeight() + translationY;
                        if (z15) {
                            if (!isPinned) {
                                if (((ShelfState) this.mViewState).hasItemsInStableShelf) {
                                }
                            }
                        }
                        i4 = Math.max(updateNotificationClipHeight(expandableView4, height, i17), i16);
                        if (expandableView4 instanceof ExpandableNotificationRow) {
                        }
                        if (z16) {
                        }
                    }
                }
                i6 = i + 1;
                expandableView2 = expandableView;
                z8 = z3;
                z9 = z4;
            }
            throw null;
        }
    }

    public final void updateIconsPaddingEnd() {
        if (this.mStatusBarState == 1) {
            this.mShelfIcons.mActualPaddingEnd = r2.getPaddingEnd();
            return;
        }
        NotificationShelfManager notificationShelfManager = this.mShelfManager;
        if (notificationShelfManager != null) {
            SecShelfNotificationIconContainer secShelfNotificationIconContainer = this.mShelfIcons;
            Intrinsics.checkNotNull(notificationShelfManager.mShelfTextArea);
            secShelfNotificationIconContainer.mActualPaddingEnd = r1.getWidth() + notificationShelfManager.mIconContainerPaddingEnd;
        }
    }

    public final void updateInteractiveness() {
        boolean z = false;
        if ((this.mShelfRefactorFlagEnabled ? this.mCanInteract : this.mStatusBarState == 1) && this.mHasItemsInStableShelf) {
            z = true;
        }
        this.mInteractive = z;
        if (!this.mAmbientState.isOnKeyguard()) {
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

    public final void updateResources() {
        Resources resources = getResources();
        SystemBarUtils.getStatusBarHeight(((FrameLayout) this).mContext);
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        NotificationShelfManager notificationShelfManager = this.mShelfManager;
        int dimensionPixelOffset = resources.getDimensionPixelOffset(notificationShelfManager != null ? notificationShelfManager.statusBarState != 1 ? QpRune.QUICK_TABLET ? R.dimen.sec_notification_shelf_height_tablet : R.dimen.sec_notification_shelf_height : R.dimen.notification_shelf_height_for_lockscreen : R.dimen.notification_shelf_height);
        if (dimensionPixelOffset != layoutParams.height) {
            layoutParams.height = dimensionPixelOffset;
            setLayoutParams(layoutParams);
        }
        this.mScrollFastThreshold = resources.getDimensionPixelOffset(R.dimen.scroll_fast_threshold);
        this.mShowNotificationShelf = resources.getBoolean(R.bool.config_showNotificationShelf);
        this.mCornerAnimationDistance = resources.getDimensionPixelSize(R.dimen.notification_corner_animation_distance);
        this.mEnableNotificationClipping = resources.getBoolean(R.bool.notification_enable_clipping);
        this.mShelfIcons.mInNotificationIconShelf = true;
        if (!this.mShowNotificationShelf) {
            setVisibility(8);
        }
        NotificationShelfManager notificationShelfManager2 = this.mShelfManager;
        if (notificationShelfManager2 != null) {
            notificationShelfManager2.mIconContainerPaddingEnd = notificationShelfManager2.context.getResources().getDimensionPixelSize(R.dimen.notification_shelf_icon_container_padding_end);
        }
        updateIconsPaddingEnd();
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
