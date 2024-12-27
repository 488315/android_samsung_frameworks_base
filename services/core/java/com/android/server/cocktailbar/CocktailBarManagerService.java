package com.android.server.cocktailbar;

import android.content.Context;
import android.content.pm.LauncherApps;
import android.os.Message;
import android.util.Slog;
import com.android.internal.view.AppearanceRegion;
import com.android.server.SystemService;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;
import com.android.server.cocktailbar.policy.systemui.SystemUiVisibilityPolicyController;
import com.android.server.cocktailbar.utils.CocktailBarHistory;
import com.samsung.android.cocktailbar.CocktailBarManagerInternal;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CocktailBarManagerService extends SystemService {
    public final CocktailBarManagerServiceContainer mCocktailBarService;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CocktalBarLocalService extends CocktailBarManagerInternal {
        public CocktalBarLocalService() {
        }

        public final void onSystemBarAppearanceChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr) {
            CocktailBarManagerService.this.mCocktailBarService.mSystemBarAppearance = i2;
        }

        public final void topAppWindowChanged(int i, boolean z, boolean z2) {
            SystemUiVisibilityPolicyController systemUiVisibilityPolicyController = CocktailBarManagerService.this.mCocktailBarService.mSystemUiVisibilityPolicyController;
            if (i != 0) {
                systemUiVisibilityPolicyController.getClass();
                return;
            }
            int i2 = systemUiVisibilityPolicyController.mSystemUiVisibility;
            systemUiVisibilityPolicyController.setState(1, 1, z);
            systemUiVisibilityPolicyController.setState(2, 2, z2);
            int i3 = systemUiVisibilityPolicyController.mSystemUiVisibility;
            if ((i3 & 1) == 0 || (2 & i3) == 0) {
                systemUiVisibilityPolicyController.setState(4, 4, false);
            }
            if (i2 != systemUiVisibilityPolicyController.mSystemUiVisibility) {
                systemUiVisibilityPolicyController.systemUiVisibilityChanged();
            }
        }

        public final void turnOffWakupCocktailBarFromPowerManager(int i, String str) {
        }

        public final void updateSysfsGripDisableFromWindowManager(boolean z) {
        }

        public final void wakupCocktailBarFromWindowManager(boolean z, int i, int i2) {
        }
    }

    public CocktailBarManagerService(Context context) {
        super(context);
        this.mCocktailBarService = new CocktailBarManagerServiceContainer(context);
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        CocktailBarManagerServiceContainer cocktailBarManagerServiceContainer = this.mCocktailBarService;
        if (i == 1000) {
            Slog.i("CocktailBarManagerService", "PHASE_BOOT_COMPLETED");
            isSafeMode();
            cocktailBarManagerServiceContainer.getClass();
            CocktailBarHistory.getInstance().recordServiceProcess("systemRunning");
            cocktailBarManagerServiceContainer.mCurrentUserId = cocktailBarManagerServiceContainer.mContext.getUserId();
            cocktailBarManagerServiceContainer.bootCompleted();
            return;
        }
        if (i == 500) {
            Slog.i("CocktailBarManagerService", "PHASE_SYSTEM_SERVICES_READY");
            cocktailBarManagerServiceContainer.getClass();
            CocktailBarHistory.getInstance().recordServiceProcess("systemServicesReady");
            try {
                ((LauncherApps) cocktailBarManagerServiceContainer.mContext.getSystemService("launcherapps")).registerCallback(cocktailBarManagerServiceContainer.mLauncherAppsCallback, null);
            } catch (RuntimeException e) {
                Slog.e("CocktailBarManagerServiceContainer", "systemServicesReady : " + e.getMessage());
                CocktailBarHistory.getInstance().recordServiceProcess(e.toString());
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("CocktailBarService", this.mCocktailBarService);
        publishLocalService(CocktailBarManagerInternal.class, new CocktalBarLocalService());
    }

    @Override // com.android.server.SystemService
    public final void onUserSwitching(SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
        int userIdentifier = targetUser.getUserIdentifier();
        int userIdentifier2 = targetUser2.getUserIdentifier();
        CocktailBarManagerServiceContainer cocktailBarManagerServiceContainer = this.mCocktailBarService;
        cocktailBarManagerServiceContainer.getClass();
        Slog.i("CocktailBarManagerServiceContainer", "onUserSwitched : from userId = " + userIdentifier + ", to userId " + userIdentifier2);
        CocktailBarHistory.getInstance().recordServiceProcess("onUserSwitched : from userId = " + userIdentifier + ", to userId " + userIdentifier2);
        if (cocktailBarManagerServiceContainer.enforceCocktailBarService()) {
            CocktailBarManagerServiceContainer.isNotEdgeRunnableId(userIdentifier2);
        }
        Slog.i("CocktailBarManagerService", "onSwitchUser: " + targetUser2.getUserIdentifier());
    }

    @Override // com.android.server.SystemService
    public final void onUserUnlocking(SystemService.TargetUser targetUser) {
        Slog.i("CocktailBarManagerService", "onUnlockUser: " + targetUser.getUserIdentifier());
        int userIdentifier = targetUser.getUserIdentifier();
        CocktailBarManagerServiceContainer cocktailBarManagerServiceContainer = this.mCocktailBarService;
        if (!cocktailBarManagerServiceContainer.mUserManager.isUserUnlockingOrUnlocked(userIdentifier)) {
            BrailleDisplayConnection$$ExternalSyntheticOutline0.m(userIdentifier, "User ", " is no longer unlocked - exiting", "CocktailBarManagerServiceContainer");
        } else {
            if (CocktailBarManagerServiceContainer.isNotEdgeRunnableId(userIdentifier)) {
                return;
            }
            cocktailBarManagerServiceContainer.mCocktailBarHandler.sendMessage(Message.obtain(cocktailBarManagerServiceContainer.mCocktailBarHandler, 4, userIdentifier, 0));
        }
    }
}
