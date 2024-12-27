package com.android.systemui.statusbar;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import com.android.internal.policy.SystemBarUtils;
import com.android.keyguard.BouncerPanelExpansionCalculator;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.ShelfToolTipManager;
import com.android.systemui.animation.ShadeInterpolation;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.logging.PanelScreenShotLogger;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.shade.transition.LargeScreenShadeInterpolator;
import com.android.systemui.statusbar.notification.ColorUpdateLogger;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.SourceType$Companion$from$1;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationView;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.NotificationBackgroundView;
import com.android.systemui.statusbar.notification.shared.NotificationIconContainerRefactor;
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
import com.samsung.android.widget.SemTipPopup;
import java.io.PrintWriter;
import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;

public class NotificationShelf extends ActivatableNotificationView implements StatusBarStateController.StateListener, PanelScreenShotLogger.LogProvider, AmbientState.KeyguardNotiExpandListener {
    public float mActualWidth;
    public AmbientState mAmbientState;
    public boolean mAnimationsEnabled;
    public boolean mCanInteract;
    public boolean mCanModifyColorOfNotifications;
    public final Rect mClipRect;
    public float mCornerAnimationDistance;
    public boolean mEnableNotificationClipping;
    public boolean mHasItemsInStableShelf;
    public boolean mHideBackground;
    public NotificationStackScrollLayout mHostLayout;
    public int mIndexOfFirstViewInShelf;
    public boolean mInteractive;
    public int mNotGoneIndex;
    public int mPaddingBetweenElements;
    public NotificationRoundnessManager mRoundnessManager;
    public int mScrollFastThreshold;
    public SecShelfNotificationIconContainer mShelfIcons;
    public NotificationShelfManager mShelfManager;
    public boolean mShowNotificationShelf;
    public int mStatusBarState;
    public static final Interpolator ICON_ALPHA_INTERPOLATOR = new PathInterpolator(0.6f, 0.0f, 0.6f, 0.0f);
    public static final SourceType$Companion$from$1 BASE_VALUE = SourceType.from("BaseValue");
    public static final SourceType$Companion$from$1 SHELF_SCROLL = SourceType.from("ShelfScroll");

    public final class ShelfState extends ExpandableViewState {
        public ExpandableView firstViewInShelf;
        public boolean hasItemsInStableShelf;

        public ShelfState() {
        }

        @Override // com.android.systemui.statusbar.notification.stack.ExpandableViewState, com.android.systemui.statusbar.notification.stack.ViewState
        public final void animateTo(View view, AnimationProperties animationProperties) {
            NotificationShelf notificationShelf = NotificationShelf.this;
            if (notificationShelf.mShowNotificationShelf) {
                if (notificationShelf.mStatusBarState == 2) {
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

    /* JADX WARN: Removed duplicated region for block: B:103:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00b9  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00c7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public float getAmountInShelf(int r10, com.android.systemui.statusbar.notification.row.ExpandableView r11, boolean r12, boolean r13, boolean r14, float r15) {
        /*
            Method dump skipped, instructions count: 464
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.NotificationShelf.getAmountInShelf(int, com.android.systemui.statusbar.notification.row.ExpandableView, boolean, boolean, boolean, float):float");
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
        updateResources$3();
    }

    @Override // com.android.systemui.statusbar.notification.row.ActivatableNotificationView, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        SecShelfNotificationIconContainer secShelfNotificationIconContainer = (SecShelfNotificationIconContainer) findViewById(R.id.content);
        this.mShelfIcons = secShelfNotificationIconContainer;
        secShelfNotificationIconContainer.setClipChildren(false);
        this.mShelfIcons.setClipToPadding(false);
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
        notificationShelfManager.updateShelfHeight();
        updateInteractiveness();
        updateIconsPaddingEnd();
        this.mShelfManager.updateShelfTextArea();
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

    public void updateActualWidth(float f, float f2) {
        int i = NotificationIconContainerRefactor.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        float lerp = this.mAmbientState.isOnKeyguard() ? MathUtils.lerp(f2, getWidth(), f) : getWidth();
        int i2 = (int) lerp;
        NotificationBackgroundView notificationBackgroundView = this.mBackgroundNormal;
        if (notificationBackgroundView != null) {
            notificationBackgroundView.mActualWidth = i2;
        }
        SecShelfNotificationIconContainer secShelfNotificationIconContainer = this.mShelfIcons;
        if (secShelfNotificationIconContainer != null) {
            secShelfNotificationIconContainer.mActualLayoutWidth = i2;
        }
        this.mActualWidth = lerp;
    }

    /* JADX WARN: Removed duplicated region for block: B:126:0x024e  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x01bd  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x02f4  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x0312  */
    /* JADX WARN: Removed duplicated region for block: B:191:0x0340 A[LOOP:2: B:189:0x033a->B:191:0x0340, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:196:0x036c  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x03f1  */
    /* JADX WARN: Removed duplicated region for block: B:238:0x040d  */
    /* JADX WARN: Removed duplicated region for block: B:240:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:243:0x0314  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01c5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateAppearance() {
        /*
            Method dump skipped, instructions count: 1040
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
            Intrinsics.checkNotNull(notificationShelfManager.mShelfTextArea);
            secShelfNotificationIconContainer.mActualPaddingEnd = r1.getWidth() + notificationShelfManager.mIconContainerPaddingEnd;
        }
    }

    public final void updateInteractiveness() {
        this.mInteractive = this.mCanInteract && this.mHasItemsInStableShelf;
        if (!this.mAmbientState.isOnKeyguard()) {
            this.mInteractive = true;
        }
        setClickable(this.mInteractive);
        setFocusable(this.mInteractive);
        setImportantForAccessibility(1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x002e, code lost:
    
        if (r4.mIsHeadsUpEntry != false) goto L16;
     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x006c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x003c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int updateNotificationClipHeight(com.android.systemui.statusbar.notification.row.ExpandableView r7, float r8, int r9) {
        /*
            r6 = this;
            float r0 = r7.getTranslationY()
            int r1 = r7.mActualHeight
            float r1 = (float) r1
            float r0 = r0 + r1
            boolean r1 = r7.isPinned()
            r2 = 1
            r3 = 0
            if (r1 != 0) goto L16
            boolean r1 = r7.isHeadsUpAnimatingAway()
            if (r1 == 0) goto L31
        L16:
            com.android.systemui.statusbar.notification.stack.AmbientState r1 = r6.mAmbientState
            r1.getClass()
            boolean r4 = r7 instanceof com.android.systemui.statusbar.notification.row.ExpandableNotificationRow
            if (r4 == 0) goto L33
            r4 = r7
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r4 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r4
            boolean r5 = r1.mDozing
            if (r5 == 0) goto L33
            com.android.systemui.statusbar.notification.collection.NotificationEntry r4 = r4.mEntry
            boolean r1 = r1.mPulsing
            if (r1 == 0) goto L31
            boolean r1 = r4.mIsHeadsUpEntry
            if (r1 == 0) goto L31
            goto L33
        L31:
            r1 = r3
            goto L34
        L33:
            r1 = r2
        L34:
            com.android.systemui.statusbar.notification.stack.AmbientState r4 = r6.mAmbientState
            boolean r4 = r4.isPulseExpanding()
            if (r4 == 0) goto L41
            if (r9 != 0) goto L3f
            goto L45
        L3f:
            r2 = r3
            goto L45
        L41:
            boolean r2 = r7.showingPulsing()
        L45:
            if (r1 == 0) goto L4d
            com.android.systemui.statusbar.notification.stack.AmbientState r9 = r6.mAmbientState
            boolean r9 = r9.mShadeExpanded
            if (r9 == 0) goto L63
        L4d:
            int r9 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
            if (r9 <= 0) goto L60
            if (r2 != 0) goto L60
            boolean r9 = r6.mEnableNotificationClipping
            if (r9 == 0) goto L5b
            float r8 = r0 - r8
            int r8 = (int) r8
            goto L5c
        L5b:
            r8 = r3
        L5c:
            r7.setClipBottomAmount(r8)
            goto L63
        L60:
            r7.setClipBottomAmount(r3)
        L63:
            if (r2 == 0) goto L6c
            float r6 = r6.getTranslationY()
            float r0 = r0 - r6
            int r6 = (int) r0
            return r6
        L6c:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.NotificationShelf.updateNotificationClipHeight(com.android.systemui.statusbar.notification.row.ExpandableView, float, int):int");
    }

    public final void updateResources$3() {
        Resources resources = getResources();
        SystemBarUtils.getStatusBarHeight(((FrameLayout) this).mContext);
        this.mPaddingBetweenElements = resources.getDimensionPixelSize(R.dimen.notification_divider_height);
        resources.getInteger(R.integer.max_notif_icons_on_lockscreen);
        this.mShelfIcons.setPadding(resources.getDimensionPixelOffset(R.dimen.notification_shelf_icon_container_padding_start), 0, resources.getDimensionPixelOffset(R.dimen.notification_shelf_icon_container_padding_end), 0);
        this.mScrollFastThreshold = resources.getDimensionPixelOffset(R.dimen.scroll_fast_threshold);
        this.mShowNotificationShelf = resources.getBoolean(R.bool.config_showNotificationShelf);
        this.mCornerAnimationDistance = resources.getDimensionPixelSize(R.dimen.notification_corner_animation_distance);
        this.mEnableNotificationClipping = resources.getBoolean(R.bool.notification_enable_clipping);
        int i = NotificationIconContainerRefactor.$r8$clinit;
        SecShelfNotificationIconContainer secShelfNotificationIconContainer = this.mShelfIcons;
        secShelfNotificationIconContainer.getClass();
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        secShelfNotificationIconContainer.mOverrideIconColor = true;
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
            if (!ambientState.mExpansionChanging || ambientState.isOnKeyguard()) {
                shelfState.setAlpha(1.0f - ambientState.mHideAmount);
                SecShelfNotificationIconContainer secShelfNotificationIconContainer = this.mShelfIcons;
                float f2 = 1.0f - ambientState.mHideAmount;
                TextView textView = secShelfNotificationIconContainer.mMoreView;
                if (textView != null && textView.getVisibility() == 0) {
                    secShelfNotificationIconContainer.mMoreView.setAlpha(f2);
                }
            } else {
                float f3 = ambientState.mExpansionFraction;
                StatusBarKeyguardViewManager statusBarKeyguardViewManager = ambientState.mStatusBarKeyguardViewManager;
                if (statusBarKeyguardViewManager != null && statusBarKeyguardViewManager.isPrimaryBouncerInTransit()) {
                    shelfState.setAlpha(BouncerPanelExpansionCalculator.aboutToShowBouncerProgress(f3));
                } else if (ambientState.mIsSmallScreen) {
                    shelfState.setAlpha(ShadeInterpolation.getContentAlpha(f3));
                } else {
                    shelfState.setAlpha(largeScreenShadeInterpolator.getNotificationContentAlpha(f3));
                }
            }
        }
        if (!this.mShowNotificationShelf || expandableView == null) {
            if (NotiRune.NOTI_STATIC_SHELF_ALPHA_VI && !this.mAmbientState.mShadeExpanded) {
                shelfState.hidden = true;
                shelfState.location = 64;
                shelfState.hasItemsInStableShelf = false;
            }
            f = 0.0f;
        } else {
            ExpandableViewState expandableViewState = expandableView.mViewState;
            if (expandableViewState == null) {
                return;
            }
            f = expandableViewState.mYTranslation + expandableViewState.height + (this.mShelfManager.statusBarState != 1 ? shelfState.height + this.mPaddingBetweenElements : 0.0f);
            shelfState.copyFrom(expandableViewState);
            shelfState.height = getHeight();
            shelfState.setZTranslation(0);
            shelfState.clipTopAmount = 0;
            if (ambientState.mExpansionChanging && !ambientState.isOnKeyguard()) {
                float f4 = ambientState.mExpansionFraction;
                StatusBarKeyguardViewManager statusBarKeyguardViewManager2 = ambientState.mStatusBarKeyguardViewManager;
                if (statusBarKeyguardViewManager2 != null && statusBarKeyguardViewManager2.isPrimaryBouncerInTransit()) {
                    shelfState.setAlpha(BouncerPanelExpansionCalculator.aboutToShowBouncerProgress(f4));
                } else if (ambientState.mIsSmallScreen) {
                    shelfState.setAlpha(ShadeInterpolation.getContentAlpha(f4));
                } else {
                    shelfState.setAlpha(largeScreenShadeInterpolator.getNotificationContentAlpha(f4));
                }
            } else if (ambientState.mDragDownOnKeyguard) {
                float f5 = ambientState.mFractionToShade;
                ShadeInterpolation shadeInterpolation = ShadeInterpolation.INSTANCE;
                float constrainedMap = MathUtils.constrainedMap(0.0f, 1.0f, 0.45f, 1.0f, f5);
                ShadeInterpolation.INSTANCE.getClass();
                shelfState.setAlpha(ShadeInterpolation.interpolateEaseInOut(constrainedMap));
            } else {
                shelfState.setAlpha(1.0f - ambientState.mHideAmount);
                SecShelfNotificationIconContainer secShelfNotificationIconContainer2 = this.mShelfIcons;
                float f6 = 1.0f - ambientState.mHideAmount;
                TextView textView2 = secShelfNotificationIconContainer2.mMoreView;
                if (textView2 != null && textView2.getVisibility() == 0) {
                    secShelfNotificationIconContainer2.mMoreView.setAlpha(f6);
                }
            }
            int i = NotificationIconContainerRefactor.$r8$clinit;
            RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
            shelfState.belowSpeedBump = this.mHostLayout.getSpeedBumpIndex$1() == 0;
            shelfState.hideSensitive = false;
            shelfState.setXTranslation(getTranslationX());
            shelfState.hasItemsInStableShelf = expandableViewState.inShelf;
            shelfState.firstViewInShelf = stackScrollAlgorithmState.firstViewInShelf;
            int i2 = this.mNotGoneIndex;
            if (i2 != -1) {
                shelfState.notGoneIndex = Math.min(shelfState.notGoneIndex, i2);
            }
            if (!shelfState.hasItemsInStableShelf && ambientState.isOnKeyguard() && !this.mAmbientState.mDragDownOnKeyguard) {
                if ((getTranslationY() - expandableViewState.mYTranslation) / Math.min(Math.min(getHeight() * 1.5f, expandableView.mActualHeight + this.mPaddingBetweenElements), expandableView.getMinHeight(false) - getHeight()) < 0.5f) {
                    shelfState.hasItemsInStableShelf = true;
                }
            }
            shelfState.hidden = !this.mAmbientState.mShadeExpanded;
            int indexOf = stackScrollAlgorithmState.visibleChildren.indexOf(stackScrollAlgorithmState.firstViewInShelf);
            if (this.mAmbientState.mExpansionChanging && stackScrollAlgorithmState.firstViewInShelf != null && indexOf > 0 && ((ExpandableView) stackScrollAlgorithmState.visibleChildren.get(indexOf - 1)).mViewState.hidden) {
                shelfState.hidden = true;
            }
        }
        float f7 = ambientState.mStackY + ambientState.mStackHeight;
        float innerHeight$1 = ambientState.getInnerHeight$1() + ambientState.mTopPadding + ambientState.mStackTranslation;
        if (getResources().getConfiguration().orientation == 2 && ambientState.mNotificationScrimTop + shelfState.height > f7) {
            shelfState.hidden = true;
        }
        if (ambientState.isOnKeyguard() && !this.mAmbientState.mDragDownOnKeyguard) {
            shelfState.setYTranslation(Math.min(f, innerHeight$1) + 0.0f + ambientState.mExtraTopInsetForFullShadeTransition);
            return;
        }
        float displayHeight = DeviceState.getDisplayHeight(((FrameLayout) this).mContext);
        float navBarHeight = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).getNavBarHeight(((FrameLayout) this).mContext) + shelfState.height;
        float f8 = ambientState.mQsExpansionFraction;
        shelfState.setYTranslation(displayHeight - ((1.0f - (f8 < 0.8f ? 0.0f : (f8 - 0.8f) / 0.2f)) * navBarHeight));
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
