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
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.ShadeFullExpansionListener;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import java.io.PrintWriter;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarTouchableRegionManager implements Dumpable {
    public CentralSurfaces mCentralSurfaces;
    public final Context mContext;
    public int mDisplayCutoutTouchableRegionSize;
    public final HeadsUpManagerPhone mHeadsUpManager;
    public View mNotificationPanelView;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public View mNotificationShadeWindowView;
    public final StatusBarTouchableRegionManager$$ExternalSyntheticLambda2 mOnComputeInternalInsetsListener;
    public int mStatusBarHeight;
    public final UnlockedScreenOffAnimationController mUnlockedScreenOffAnimationController;
    public boolean mIsStatusBarExpanded = false;
    public boolean mShouldAdjustInsets = false;
    public boolean mForceCollapsedUntilLayout = false;
    public final Region mTouchableRegion = new Region();

    /* JADX WARN: Type inference failed for: r2v6, types: [com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager$$ExternalSyntheticLambda2] */
    public StatusBarTouchableRegionManager(Context context, NotificationShadeWindowController notificationShadeWindowController, ConfigurationController configurationController, HeadsUpManagerPhone headsUpManagerPhone, ShadeExpansionStateManager shadeExpansionStateManager, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController) {
        this.mContext = context;
        initResources();
        ((ConfigurationControllerImpl) configurationController).addCallback(new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                StatusBarTouchableRegionManager.this.initResources();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                StatusBarTouchableRegionManager.this.initResources();
            }
        });
        this.mHeadsUpManager = headsUpManagerPhone;
        headsUpManagerPhone.addListener(new OnHeadsUpChangedListener() { // from class: com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager.2
            @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
            public final void onHeadsUpPinnedModeChanged(boolean z) {
                if (Log.isLoggable("TouchableRegionManager", 5)) {
                    Log.w("TouchableRegionManager", "onHeadsUpPinnedModeChanged");
                }
                StatusBarTouchableRegionManager.this.updateTouchableRegion();
            }
        });
        ((ArrayList) headsUpManagerPhone.mHeadsUpPhoneListeners).add(new StatusBarTouchableRegionManager$$ExternalSyntheticLambda0(this));
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        ((NotificationShadeWindowControllerImpl) notificationShadeWindowController).mForcePluginOpenListener = new StatusBarTouchableRegionManager$$ExternalSyntheticLambda0(this);
        this.mUnlockedScreenOffAnimationController = unlockedScreenOffAnimationController;
        shadeExpansionStateManager.addFullExpansionListener(new ShadeFullExpansionListener() { // from class: com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager$$ExternalSyntheticLambda1
            @Override // com.android.systemui.shade.ShadeFullExpansionListener
            public final void onShadeExpansionFullyChanged(boolean z) {
                Boolean valueOf = Boolean.valueOf(z);
                StatusBarTouchableRegionManager statusBarTouchableRegionManager = StatusBarTouchableRegionManager.this;
                statusBarTouchableRegionManager.getClass();
                if (valueOf.booleanValue() != statusBarTouchableRegionManager.mIsStatusBarExpanded) {
                    statusBarTouchableRegionManager.mIsStatusBarExpanded = valueOf.booleanValue();
                    if (valueOf.booleanValue()) {
                        statusBarTouchableRegionManager.mForceCollapsedUntilLayout = false;
                    }
                    statusBarTouchableRegionManager.updateTouchableRegion();
                }
            }
        });
        this.mOnComputeInternalInsetsListener = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager$$ExternalSyntheticLambda2
            public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                StatusBarTouchableRegionManager statusBarTouchableRegionManager = StatusBarTouchableRegionManager.this;
                if (statusBarTouchableRegionManager.mIsStatusBarExpanded || ((CentralSurfacesImpl) statusBarTouchableRegionManager.mCentralSurfaces).mBouncerShowing || statusBarTouchableRegionManager.mUnlockedScreenOffAnimationController.isAnimationPlaying()) {
                    return;
                }
                internalInsetsInfo.setTouchableInsets(3);
                internalInsetsInfo.touchableRegion.set(statusBarTouchableRegionManager.calculateTouchableRegion());
            }
        };
    }

    public final Region calculateTouchableRegion() {
        Region touchableRegion = this.mHeadsUpManager.getTouchableRegion();
        Region region = this.mTouchableRegion;
        if (touchableRegion != null) {
            region.set(touchableRegion);
        } else {
            region.set(0, 0, this.mNotificationShadeWindowView.getWidth(), this.mStatusBarHeight);
            updateRegionForNotch(region);
        }
        return region;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("StatusBarTouchableRegionManager state:");
        printWriter.print("  mTouchableRegion=");
        printWriter.println(this.mTouchableRegion);
    }

    public final void initResources() {
        Context context = this.mContext;
        this.mDisplayCutoutTouchableRegionSize = context.getResources().getDimensionPixelSize(R.dimen.docked_stack_minimize_thickness);
        this.mStatusBarHeight = SystemBarUtils.getStatusBarHeight(context);
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
        boolean z = true;
        boolean z2 = (view == null || view.getRootWindowInsets() == null || this.mNotificationShadeWindowView.getRootWindowInsets().getDisplayCutout() == null) ? false : true;
        HeadsUpManagerPhone headsUpManagerPhone = this.mHeadsUpManager;
        if (!headsUpManagerPhone.mHasPinnedNotification && !headsUpManagerPhone.mHeadsUpGoingAway && !this.mForceCollapsedUntilLayout && !z2 && !((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).mCurrentState.forcePluginOpen) {
            z = false;
        }
        if (z == this.mShouldAdjustInsets) {
            return;
        }
        StatusBarTouchableRegionManager$$ExternalSyntheticLambda2 statusBarTouchableRegionManager$$ExternalSyntheticLambda2 = this.mOnComputeInternalInsetsListener;
        if (z) {
            this.mNotificationShadeWindowView.getViewTreeObserver().addOnComputeInternalInsetsListener(statusBarTouchableRegionManager$$ExternalSyntheticLambda2);
            this.mNotificationShadeWindowView.requestLayout();
        } else {
            this.mNotificationShadeWindowView.getViewTreeObserver().removeOnComputeInternalInsetsListener(statusBarTouchableRegionManager$$ExternalSyntheticLambda2);
        }
        this.mShouldAdjustInsets = z;
    }
}
