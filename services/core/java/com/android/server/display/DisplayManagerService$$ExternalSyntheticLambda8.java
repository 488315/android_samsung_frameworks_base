package com.android.server.display;

import android.os.Binder;
import android.os.RemoteException;
import com.android.server.display.mode.RefreshRateController;
import com.android.server.display.mode.RefreshRateToken;
import com.android.server.power.PowerManagerUtil;
import com.android.server.power.Slog;
import com.samsung.android.rune.CoreRune;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class DisplayManagerService$$ExternalSyntheticLambda8 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DisplayManagerService f$0;

    public /* synthetic */ DisplayManagerService$$ExternalSyntheticLambda8(DisplayManagerService displayManagerService, int i) {
        this.$r8$classId = i;
        this.f$0 = displayManagerService;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        DisplayManagerService displayManagerService = this.f$0;
        switch (i) {
            case 0:
                displayManagerService.addDisplayPowerControllerLocked((LogicalDisplay) obj);
                return;
            case 1:
                boolean booleanValue = ((Boolean) obj).booleanValue();
                synchronized (displayManagerService.mSyncRoot) {
                    try {
                        if (CoreRune.FW_VRR_REFRESH_RATE_TOKEN && displayManagerService.mBrightnessAnimStarted != booleanValue) {
                            Slog.d("DisplayManagerService", "handleBrightnessAnimation: started=" + booleanValue);
                            displayManagerService.mBrightnessAnimStarted = booleanValue;
                            if (!booleanValue) {
                                try {
                                    RefreshRateToken refreshRateToken = displayManagerService.mBrightnessAnimRefreshRateToken;
                                    if (refreshRateToken != null) {
                                        refreshRateToken.release();
                                    }
                                } catch (RemoteException e) {
                                    Slog.d("DisplayManagerService", "Exception occur : " + e);
                                }
                            } else if (PowerManagerUtil.SEC_FEATURE_LCD_SUPPORT_PASSIVE_MODE) {
                                displayManagerService.mBrightnessAnimRefreshRateToken = displayManagerService.mDisplayModeDirector.mRefreshRateModeManager.getController().createPassiveModeToken(new Binder(), "BrightnessAnim");
                            } else {
                                RefreshRateController controller = displayManagerService.mDisplayModeDirector.mRefreshRateModeManager.getController();
                                Binder binder = new Binder();
                                int i2 = PowerManagerUtil.BRIGHTNESS_ANIMATION_MIN_LIMIT_HZ;
                                controller.getClass();
                                displayManagerService.mBrightnessAnimRefreshRateToken = RefreshRateController.createRefreshRateMinLimitToken(i2, binder, "BrightnessAnim");
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return;
            case 2:
                displayManagerService.configurePreferredDisplayModeLocked((LogicalDisplay) obj);
                return;
            case 3:
                LogicalDisplay logicalDisplay = (LogicalDisplay) obj;
                logicalDisplay.setUserDisabledHdrTypes(displayManagerService.mUserDisabledHdrTypes);
                displayManagerService.handleLogicalDisplayChangedLocked(logicalDisplay);
                return;
            default:
                LogicalDisplay logicalDisplay2 = (LogicalDisplay) obj;
                displayManagerService.getClass();
                if (logicalDisplay2.getDisplayInfoLocked().type != 1) {
                    return;
                }
                displayManagerService.setBrightnessConfigurationForDisplayInternal(null, logicalDisplay2.mPrimaryDisplayDevice.mUniqueId, displayManagerService.mContext.getUserId(), displayManagerService.mContext.getPackageName());
                return;
        }
    }
}
