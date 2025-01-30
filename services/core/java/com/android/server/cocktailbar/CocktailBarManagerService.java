package com.android.server.cocktailbar;

import android.content.Context;
import android.util.Slog;
import com.android.internal.view.AppearanceRegion;
import com.android.server.SystemService;
import com.samsung.android.cocktailbar.CocktailBarManagerInternal;

/* loaded from: classes.dex */
public class CocktailBarManagerService extends SystemService {
  public final CocktailBarManagerServiceContainer mCocktailBarService;

  public CocktailBarManagerService(Context context) {
    super(context);
    this.mCocktailBarService = new CocktailBarManagerServiceContainer(context);
  }

  @Override // com.android.server.SystemService
  public void onStart() {
    publishBinderService("CocktailBarService", this.mCocktailBarService);
    publishLocalService(CocktailBarManagerInternal.class, new CocktalBarLocalService());
  }

  @Override // com.android.server.SystemService
  public void onBootPhase(int i) {
    if (i == 1000) {
      Slog.i("CocktailBarManagerService", "PHASE_BOOT_COMPLETED");
      this.mCocktailBarService.systemRunning(isSafeMode());
    } else if (i == 500) {
      Slog.i("CocktailBarManagerService", "PHASE_SYSTEM_SERVICES_READY");
      this.mCocktailBarService.systemServicesReady();
    }
  }

  @Override // com.android.server.SystemService
  public void onUserSwitching(
      SystemService.TargetUser targetUser, SystemService.TargetUser targetUser2) {
    this.mCocktailBarService.onUserSwitched(
        targetUser.getUserIdentifier(), targetUser2.getUserIdentifier());
    Slog.i("CocktailBarManagerService", "onSwitchUser: " + targetUser2.getUserIdentifier());
  }

  @Override // com.android.server.SystemService
  public void onUserUnlocking(SystemService.TargetUser targetUser) {
    Slog.i("CocktailBarManagerService", "onUnlockUser: " + targetUser.getUserIdentifier());
    this.mCocktailBarService.onUnlockUser(targetUser.getUserIdentifier());
  }

  public final class CocktalBarLocalService extends CocktailBarManagerInternal {
    public void turnOffWakupCocktailBarFromPowerManager(int i, String str) {}

    public void updateSysfsGripDisableFromWindowManager(boolean z) {}

    public void wakupCocktailBarFromWindowManager(boolean z, int i, int i2) {}

    public CocktalBarLocalService() {}

    public void onSystemBarAppearanceChanged(
        int i, int i2, AppearanceRegion[] appearanceRegionArr) {
      CocktailBarManagerService.this.mCocktailBarService.setSystemBarAppearance(i2);
    }

    public void topAppWindowChanged(int i, boolean z, boolean z2) {
      CocktailBarManagerService.this.mCocktailBarService.topAppWindowChanged(i, z, z2);
    }
  }
}
