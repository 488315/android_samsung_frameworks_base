package com.android.systemui.statusbar.phone;

import android.R;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.Dumpable;
import com.android.systemui.Flags;
import com.android.systemui.ScreenDecorations;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.util.kotlin.JavaAdapter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.Consumer;
import javax.inject.Provider;

public final class StatusBarTouchableRegionManager implements Dumpable {
    public final AlternateBouncerInteractor mAlternateBouncerInteractor;
    public final Context mContext;
    public int mDisplayCutoutTouchableRegionSize;
    public final HeadsUpManager mHeadsUpManager;
    public View mNotificationPanelView;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public View mNotificationShadeWindowView;
    public final StatusBarTouchableRegionManager$$ExternalSyntheticLambda2 mOnComputeInternalInsetsListener;
    public final PrimaryBouncerInteractor mPrimaryBouncerInteractor;
    public int mStatusBarHeight;
    public final UnlockedScreenOffAnimationController mUnlockedScreenOffAnimationController;
    public boolean mIsStatusBarExpanded = false;
    public boolean mShouldAdjustInsets = false;
    public boolean mForceCollapsedUntilLayout = false;
    public final Region mTouchableRegion = new Region();

    public StatusBarTouchableRegionManager(Context context, NotificationShadeWindowController notificationShadeWindowController, ConfigurationController configurationController, HeadsUpManager headsUpManager, ShadeInteractor shadeInteractor, Provider provider, JavaAdapter javaAdapter, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, PrimaryBouncerInteractor primaryBouncerInteractor, AlternateBouncerInteractor alternateBouncerInteractor) {
        this.mContext = context;
        initResources$1();
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                StatusBarTouchableRegionManager.this.initResources$1();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                StatusBarTouchableRegionManager.this.initResources$1();
            }
        });
        this.mHeadsUpManager = headsUpManager;
        ((BaseHeadsUpManager) headsUpManager).addListener(new OnHeadsUpChangedListener() { // from class: com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager.2
            @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
            public final void onHeadsUpPinnedModeChanged(boolean z) {
                if (Log.isLoggable("TouchableRegionManager", 5)) {
                    Log.w("TouchableRegionManager", "onHeadsUpPinnedModeChanged");
                }
                StatusBarTouchableRegionManager.this.updateTouchableRegion();
            }
        });
        ((ArrayList) ((HeadsUpManagerPhone) headsUpManager).mHeadsUpPhoneListeners).add(new StatusBarTouchableRegionManager$$ExternalSyntheticLambda0(this));
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        ((NotificationShadeWindowControllerImpl) notificationShadeWindowController).mForcePluginOpenListener = new StatusBarTouchableRegionManager$$ExternalSyntheticLambda0(this);
        this.mUnlockedScreenOffAnimationController = unlockedScreenOffAnimationController;
        int i = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        javaAdapter.alwaysCollectFlow(((ShadeInteractorImpl) shadeInteractor).baseShadeInteractor.isAnyExpanded(), new Consumer() { // from class: com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                StatusBarTouchableRegionManager statusBarTouchableRegionManager = StatusBarTouchableRegionManager.this;
                Boolean bool = (Boolean) obj;
                statusBarTouchableRegionManager.getClass();
                if (bool.booleanValue() != statusBarTouchableRegionManager.mIsStatusBarExpanded) {
                    statusBarTouchableRegionManager.mIsStatusBarExpanded = bool.booleanValue();
                    if (bool.booleanValue()) {
                        statusBarTouchableRegionManager.mForceCollapsedUntilLayout = false;
                    }
                    statusBarTouchableRegionManager.updateTouchableRegion();
                }
            }
        });
        this.mPrimaryBouncerInteractor = primaryBouncerInteractor;
        this.mAlternateBouncerInteractor = alternateBouncerInteractor;
        this.mOnComputeInternalInsetsListener = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager$$ExternalSyntheticLambda2
            public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                StatusBarTouchableRegionManager statusBarTouchableRegionManager = StatusBarTouchableRegionManager.this;
                if (statusBarTouchableRegionManager.mIsStatusBarExpanded) {
                    return;
                }
                int i2 = SceneContainerFlag.$r8$clinit;
                Flags.sceneContainer();
                if (((Boolean) statusBarTouchableRegionManager.mPrimaryBouncerInteractor.isShowing.$$delegate_0.getValue()).booleanValue() || statusBarTouchableRegionManager.mAlternateBouncerInteractor.isVisibleState() || statusBarTouchableRegionManager.mUnlockedScreenOffAnimationController.isAnimationPlaying()) {
                    return;
                }
                internalInsetsInfo.setTouchableInsets(3);
                internalInsetsInfo.touchableRegion.set(statusBarTouchableRegionManager.calculateTouchableRegion());
            }
        };
    }

    public final Region calculateTouchableRegion() {
        NotificationEntry groupSummary;
        HeadsUpManagerPhone headsUpManagerPhone = (HeadsUpManagerPhone) this.mHeadsUpManager;
        BaseHeadsUpManager.HeadsUpEntry topHeadsUpEntry = headsUpManagerPhone.getTopHeadsUpEntry();
        Region region = null;
        NotificationEntry notificationEntry = topHeadsUpEntry != null ? topHeadsUpEntry.mEntry : null;
        if (headsUpManagerPhone.mHasPinnedNotification && notificationEntry != null) {
            if (notificationEntry.rowIsChildInGroup() && (groupSummary = ((GroupMembershipManagerImpl) headsUpManagerPhone.mGroupMembershipManager).getGroupSummary(notificationEntry)) != null) {
                notificationEntry = groupSummary;
            }
            ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
            int[] iArr = new int[2];
            expandableNotificationRow.getLocationOnScreen(iArr);
            int i = iArr[0];
            int width = expandableNotificationRow.getWidth() + i;
            int intrinsicHeight = expandableNotificationRow.getIntrinsicHeight();
            int i2 = iArr[1];
            headsUpManagerPhone.mTouchableRegion.set(i, i2 <= headsUpManagerPhone.mHeadsUpInset ? 0 : i2, width, i2 + intrinsicHeight);
            region = headsUpManagerPhone.mTouchableRegion;
        }
        if (region != null) {
            this.mTouchableRegion.set(region);
        } else {
            View view = this.mNotificationShadeWindowView;
            if (view != null) {
                this.mTouchableRegion.set(0, 0, view.getWidth(), this.mStatusBarHeight);
                updateRegionForNotch(this.mTouchableRegion);
            }
        }
        return this.mTouchableRegion;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("StatusBarTouchableRegionManager state:");
        printWriter.print("  mTouchableRegion=");
        printWriter.println(this.mTouchableRegion);
    }

    public final void initResources$1() {
        this.mDisplayCutoutTouchableRegionSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.harmful_app_name_padding_right);
        this.mStatusBarHeight = SystemBarUtils.getStatusBarHeight(this.mContext);
    }

    public final void updateRegionForNotch(Region region) {
        WindowInsets rootWindowInsets = this.mNotificationShadeWindowView.getRootWindowInsets();
        if (rootWindowInsets == null) {
            Log.w("TouchableRegionManager", "StatusBarWindowView is not attached.");
            return;
        }
        DisplayCutout displayCutout = rootWindowInsets.getDisplayCutout();
        if (displayCutout == null) {
            return;
        }
        Rect rect = new Rect();
        ScreenDecorations.DisplayCutoutView.boundsFromDirection(48, rect, displayCutout);
        rect.offset(0, this.mDisplayCutoutTouchableRegionSize);
        region.union(rect);
    }

    public final void updateTouchableRegion() {
        View view = this.mNotificationShadeWindowView;
        boolean z = (view == null || view.getRootWindowInsets() == null || this.mNotificationShadeWindowView.getRootWindowInsets().getDisplayCutout() == null) ? false : true;
        HeadsUpManager headsUpManager = this.mHeadsUpManager;
        boolean z2 = ((BaseHeadsUpManager) headsUpManager).mHasPinnedNotification || ((Boolean) ((HeadsUpManagerPhone) headsUpManager).mHeadsUpAnimatingAway.getValue()).booleanValue() || this.mForceCollapsedUntilLayout || z || ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).mCurrentState.forcePluginOpen;
        if (z2 == this.mShouldAdjustInsets) {
            return;
        }
        StatusBarTouchableRegionManager$$ExternalSyntheticLambda2 statusBarTouchableRegionManager$$ExternalSyntheticLambda2 = this.mOnComputeInternalInsetsListener;
        if (z2) {
            this.mNotificationShadeWindowView.getViewTreeObserver().addOnComputeInternalInsetsListener(statusBarTouchableRegionManager$$ExternalSyntheticLambda2);
            this.mNotificationShadeWindowView.requestLayout();
        } else {
            this.mNotificationShadeWindowView.getViewTreeObserver().removeOnComputeInternalInsetsListener(statusBarTouchableRegionManager$$ExternalSyntheticLambda2);
        }
        this.mShouldAdjustInsets = z2;
    }
}
