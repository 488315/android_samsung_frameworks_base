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
import com.android.systemui.ScreenDecorations;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManagerImpl;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.kotlin.JavaAdapter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.Consumer;
import javax.inject.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeTouchableRegionManager implements Dumpable {
    public final AlternateBouncerInteractor mAlternateBouncerInteractor;
    public final Context mContext;
    public int mDisplayCutoutTouchableRegionSize;
    public final HeadsUpManager mHeadsUpManager;
    public View mNotificationPanelView;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public NotificationShadeWindowView mNotificationShadeWindowView;
    public final ShadeTouchableRegionManager$$ExternalSyntheticLambda4 mOnComputeInternalInsetsListener;
    public final PrimaryBouncerInteractor mPrimaryBouncerInteractor;
    public int mStatusBarHeight;
    public final UnlockedScreenOffAnimationController mUnlockedScreenOffAnimationController;
    public boolean mIsStatusBarExpanded = false;
    public boolean mShouldAdjustInsets = false;
    public boolean mForceCollapsedUntilLayout = false;
    public Boolean mCommunalVisible = Boolean.FALSE;
    public final Region mTouchableRegion = new Region();

    /* JADX WARN: Type inference failed for: r2v9, types: [com.android.systemui.statusbar.phone.ShadeTouchableRegionManager$$ExternalSyntheticLambda4] */
    public ShadeTouchableRegionManager(Context context, NotificationShadeWindowController notificationShadeWindowController, ConfigurationController configurationController, HeadsUpManager headsUpManager, ShadeInteractor shadeInteractor, Provider provider, JavaAdapter javaAdapter, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, PrimaryBouncerInteractor primaryBouncerInteractor, AlternateBouncerInteractor alternateBouncerInteractor, CommunalSceneInteractor communalSceneInteractor) {
        final int i = 0;
        this.mContext = context;
        initResources$1();
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.phone.ShadeTouchableRegionManager.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                ShadeTouchableRegionManager.this.initResources$1();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                ShadeTouchableRegionManager.this.initResources$1();
            }
        });
        this.mHeadsUpManager = headsUpManager;
        HeadsUpManagerImpl headsUpManagerImpl = (HeadsUpManagerImpl) headsUpManager;
        headsUpManagerImpl.addListener(new OnHeadsUpChangedListener() { // from class: com.android.systemui.statusbar.phone.ShadeTouchableRegionManager.2
            @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
            public final void onHeadsUpPinnedModeChanged(boolean z) {
                if (Log.isLoggable("TouchableRegionManager", 5)) {
                    Log.w("TouchableRegionManager", "onHeadsUpPinnedModeChanged");
                }
                ShadeTouchableRegionManager.this.updateTouchableRegion();
            }
        });
        ((ArrayList) headsUpManagerImpl.mHeadsUpPhoneListeners).add(new ShadeTouchableRegionManager$$ExternalSyntheticLambda0(this));
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        ((NotificationShadeWindowControllerImpl) notificationShadeWindowController).mForcePluginOpenListener = new ShadeTouchableRegionManager$$ExternalSyntheticLambda1(this);
        this.mUnlockedScreenOffAnimationController = unlockedScreenOffAnimationController;
        int i2 = SceneContainerFlag.$r8$clinit;
        javaAdapter.alwaysCollectFlow(((ShadeInteractorImpl) shadeInteractor).baseShadeInteractor.isAnyExpanded(), new Consumer(this) { // from class: com.android.systemui.statusbar.phone.ShadeTouchableRegionManager$$ExternalSyntheticLambda2
            public final /* synthetic */ ShadeTouchableRegionManager f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i3 = i;
                ShadeTouchableRegionManager shadeTouchableRegionManager = this.f$0;
                Boolean bool = (Boolean) obj;
                switch (i3) {
                    case 0:
                        shadeTouchableRegionManager.getClass();
                        if (bool.booleanValue() != shadeTouchableRegionManager.mIsStatusBarExpanded) {
                            shadeTouchableRegionManager.mIsStatusBarExpanded = bool.booleanValue();
                            if (bool.booleanValue()) {
                                shadeTouchableRegionManager.mForceCollapsedUntilLayout = false;
                            }
                            shadeTouchableRegionManager.updateTouchableRegion();
                            break;
                        }
                        break;
                    default:
                        shadeTouchableRegionManager.mCommunalVisible = bool;
                        break;
                }
            }
        });
        final int i3 = 1;
        javaAdapter.alwaysCollectFlow(communalSceneInteractor.isCommunalVisible, new Consumer(this) { // from class: com.android.systemui.statusbar.phone.ShadeTouchableRegionManager$$ExternalSyntheticLambda2
            public final /* synthetic */ ShadeTouchableRegionManager f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i32 = i3;
                ShadeTouchableRegionManager shadeTouchableRegionManager = this.f$0;
                Boolean bool = (Boolean) obj;
                switch (i32) {
                    case 0:
                        shadeTouchableRegionManager.getClass();
                        if (bool.booleanValue() != shadeTouchableRegionManager.mIsStatusBarExpanded) {
                            shadeTouchableRegionManager.mIsStatusBarExpanded = bool.booleanValue();
                            if (bool.booleanValue()) {
                                shadeTouchableRegionManager.mForceCollapsedUntilLayout = false;
                            }
                            shadeTouchableRegionManager.updateTouchableRegion();
                            break;
                        }
                        break;
                    default:
                        shadeTouchableRegionManager.mCommunalVisible = bool;
                        break;
                }
            }
        });
        this.mPrimaryBouncerInteractor = primaryBouncerInteractor;
        this.mAlternateBouncerInteractor = alternateBouncerInteractor;
        this.mOnComputeInternalInsetsListener = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.systemui.statusbar.phone.ShadeTouchableRegionManager$$ExternalSyntheticLambda4
            public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                ShadeTouchableRegionManager shadeTouchableRegionManager = ShadeTouchableRegionManager.this;
                if (shadeTouchableRegionManager.shouldMakeEntireScreenTouchable()) {
                    return;
                }
                internalInsetsInfo.setTouchableInsets(3);
                internalInsetsInfo.touchableRegion.set(shadeTouchableRegionManager.calculateTouchableRegion());
            }
        };
    }

    public final Region calculateTouchableRegion() {
        HeadsUpManagerImpl headsUpManagerImpl = (HeadsUpManagerImpl) this.mHeadsUpManager;
        HeadsUpManagerImpl.HeadsUpEntry topHeadsUpEntry = headsUpManagerImpl.getTopHeadsUpEntry();
        Region region = null;
        NotificationEntry notificationEntry = topHeadsUpEntry != null ? topHeadsUpEntry.mEntry : null;
        if (headsUpManagerImpl.mHasPinnedNotification && notificationEntry != null) {
            ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
            if (notificationEntry.rowIsChildInGroup()) {
                int i = NotificationBundleUi.$r8$clinit;
                NotificationEntry groupSummary = ((GroupMembershipManagerImpl) headsUpManagerImpl.mGroupMembershipManager).getGroupSummary(notificationEntry);
                if (groupSummary != null) {
                    expandableNotificationRow = groupSummary.row;
                }
            }
            int[] iArr = new int[2];
            expandableNotificationRow.getLocationOnScreen(iArr);
            int i2 = iArr[0];
            int width = expandableNotificationRow.getWidth() + i2;
            int intrinsicHeight = expandableNotificationRow.getIntrinsicHeight();
            int i3 = iArr[1];
            headsUpManagerImpl.mTouchableRegion.set(i2, i3 <= headsUpManagerImpl.mHeadsUpInset ? 0 : i3, width, i3 + intrinsicHeight);
            region = headsUpManagerImpl.mTouchableRegion;
        }
        if (region != null) {
            this.mTouchableRegion.set(region);
        } else {
            Region region2 = this.mTouchableRegion;
            NotificationShadeWindowView notificationShadeWindowView = this.mNotificationShadeWindowView;
            region2.set(0, 0, notificationShadeWindowView != null ? notificationShadeWindowView.getWidth() : 0, this.mStatusBarHeight);
            updateRegionForNotch(this.mTouchableRegion);
        }
        return this.mTouchableRegion;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("ShadeTouchableRegionManager state:");
        printWriter.print("  mTouchableRegion=");
        printWriter.println(this.mTouchableRegion);
    }

    public final void initResources$1() {
        this.mDisplayCutoutTouchableRegionSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.indeterminate_progress_alpha_22);
        this.mStatusBarHeight = SystemBarUtils.getStatusBarHeight(this.mContext);
    }

    public boolean shouldMakeEntireScreenTouchable() {
        if (this.mIsStatusBarExpanded) {
            return true;
        }
        int i = SceneContainerFlag.$r8$clinit;
        return ((Boolean) this.mPrimaryBouncerInteractor.isShowing.$$delegate_0.getValue()).booleanValue() || this.mAlternateBouncerInteractor.isVisibleState() || this.mCommunalVisible.booleanValue() || this.mUnlockedScreenOffAnimationController.lightRevealAnimationPlaying;
    }

    public final void updateRegionForNotch(Region region) {
        NotificationShadeWindowView notificationShadeWindowView = this.mNotificationShadeWindowView;
        if (notificationShadeWindowView == null) {
            Log.w("TouchableRegionManager", "setup is not called.");
            return;
        }
        WindowInsets rootWindowInsets = notificationShadeWindowView.getRootWindowInsets();
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
        NotificationShadeWindowView notificationShadeWindowView = this.mNotificationShadeWindowView;
        boolean z = (notificationShadeWindowView == null || notificationShadeWindowView.getRootWindowInsets() == null || this.mNotificationShadeWindowView.getRootWindowInsets().getDisplayCutout() == null) ? false : true;
        HeadsUpManagerImpl headsUpManagerImpl = (HeadsUpManagerImpl) this.mHeadsUpManager;
        boolean z2 = headsUpManagerImpl.mHasPinnedNotification || ((Boolean) headsUpManagerImpl.mHeadsUpAnimatingAway.getValue()).booleanValue() || this.mForceCollapsedUntilLayout || z || ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).mCurrentState.forcePluginOpen;
        if (z2 == this.mShouldAdjustInsets) {
            return;
        }
        NotificationShadeWindowView notificationShadeWindowView2 = this.mNotificationShadeWindowView;
        if (notificationShadeWindowView2 != null) {
            ShadeTouchableRegionManager$$ExternalSyntheticLambda4 shadeTouchableRegionManager$$ExternalSyntheticLambda4 = this.mOnComputeInternalInsetsListener;
            if (z2) {
                notificationShadeWindowView2.getViewTreeObserver().addOnComputeInternalInsetsListener(shadeTouchableRegionManager$$ExternalSyntheticLambda4);
                this.mNotificationShadeWindowView.requestLayout();
            } else {
                notificationShadeWindowView2.getViewTreeObserver().removeOnComputeInternalInsetsListener(shadeTouchableRegionManager$$ExternalSyntheticLambda4);
            }
        }
        this.mShouldAdjustInsets = z2;
    }
}
