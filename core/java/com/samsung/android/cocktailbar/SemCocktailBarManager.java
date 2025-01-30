package com.samsung.android.cocktailbar;

import android.content.ComponentName;
import android.content.Context;
import android.content.res.Configuration;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import android.widget.RemoteViews;
import com.samsung.android.util.SemLog;

/* loaded from: classes5.dex */
public class SemCocktailBarManager {
  public static final int COCKTAIL_CATEGORY_CONTEXTUAL = 65536;
  public static final int COCKTAIL_CATEGORY_GLOBAL = 1;
  public static final int COCKTAIL_CATEGORY_SECURE = 16;

  @Deprecated public static final int COCKTAIL_DISPLAY_POLICY_ALL = 143;
  public static final int COCKTAIL_DISPLAY_POLICY_GENERAL = 1;

  @Deprecated public static final int COCKTAIL_DISPLAY_POLICY_LOCKSCREEN = 2;

  @Deprecated public static final int COCKTAIL_DISPLAY_POLICY_NOT_PROVISION = 128;

  @Deprecated public static final int COCKTAIL_DISPLAY_POLICY_SCOVER = 4;

  @Deprecated public static final int COCKTAIL_DISPLAY_POLICY_TABLE_MODE = 8;
  public static final int COCKTAIL_VISIBILITY_HIDE = 2;
  public static final int COCKTAIL_VISIBILITY_SHOW = 1;
  public static final int INVALID_COCKTAIL_ID = 0;
  private static final String TAG = SemCocktailBarManager.class.getSimpleName();
  protected Context mContext;
  protected final String mPackageName;
  protected ICocktailBarService mService;

  public interface CocktailBarStateChangedListener {
    void onCocktailBarStateChanged(SemCocktailBarStateInfo semCocktailBarStateInfo);
  }

  public static SemCocktailBarManager getInstance(Context context) {
    return (SemCocktailBarManager) context.getSystemService(Context.COCKTAIL_BAR_SERVICE);
  }

  public SemCocktailBarManager(Context context, ICocktailBarService service) {
    this.mContext = context;
    this.mPackageName = context.getOpPackageName();
    this.mService = service;
  }

  public Context getContext() {
    return this.mContext;
  }

  private ICocktailBarService getService() {
    if (this.mService == null) {
      IBinder b = ServiceManager.getService(Context.COCKTAIL_BAR_SERVICE);
      this.mService = ICocktailBarService.Stub.asInterface(b);
    }
    return this.mService;
  }

  public int[] getCocktailIds(ComponentName provider) {
    if (getService() == null || provider == null) {
      int[] cocktailIds = {0};
      return cocktailIds;
    }
    try {
      return this.mService.getCocktailIds(this.mPackageName, provider);
    } catch (RemoteException e) {
      throw new RuntimeException("CocktailBarService dead?", e);
    }
  }

  public boolean isCocktailEnabled(ComponentName provider) {
    if (getService() == null || provider == null) {
      return false;
    }
    try {
      return this.mService.isEnabledCocktail(this.mPackageName, provider);
    } catch (RemoteException e) {
      throw new RuntimeException("CocktailBarService dead?", e);
    }
  }

  public boolean isCocktailEnabledInternal(ComponentName provider) {
    if (getService() == null || provider == null) {
      return false;
    }
    try {
      return this.mService.isCocktailEnabled(this.mPackageName, provider);
    } catch (RemoteException e) {
      throw new RuntimeException("CocktailBarService dead?", e);
    }
  }

  public void updateCocktail(
      int cocktailId,
      int displayPolicy,
      int category,
      RemoteViews contentView,
      RemoteViews helpView) {
    if (getService() == null) {
      SemLog.m486w(TAG, "updateCocktail : service is not running " + cocktailId);
      return;
    }
    Configuration configuration = this.mContext.getResources().getConfiguration();
    try {
      CocktailInfo cocktailInfo =
          new CocktailInfo.Builder(this.mContext)
              .setOrientation(configuration.orientation)
              .setDiplayPolicy(displayPolicy)
              .setCategory(category)
              .setContentView(contentView)
              .setHelpView(helpView)
              .build();
      this.mService.updateCocktail(this.mPackageName, cocktailInfo, cocktailId);
    } catch (RemoteException e) {
      throw new RuntimeException("CocktailBarService dead?", e);
    }
  }

  public void updateCocktail(
      int cocktailId,
      int displayPolicy,
      int category,
      Class<? extends SemAbsCocktailLoadablePanel> panelClass,
      Bundle contentInfo,
      RemoteViews helpView) {
    if (getService() == null) {
      SemLog.m486w(TAG, "updateCocktail : service is not running " + cocktailId);
      return;
    }
    ComponentName classInfo = new ComponentName(getContext(), panelClass);
    Configuration configuration = this.mContext.getResources().getConfiguration();
    try {
      CocktailInfo cocktailInfo =
          new CocktailInfo.Builder(this.mContext)
              .setOrientation(configuration.orientation)
              .setDiplayPolicy(displayPolicy)
              .setCategory(category)
              .setHelpView(helpView)
              .setContentInfo(contentInfo)
              .setClassloader(classInfo)
              .build();
      this.mService.updateCocktail(this.mPackageName, cocktailInfo, cocktailId);
    } catch (RemoteException e) {
      throw new RuntimeException("CocktailBarService dead?", e);
    }
  }

  public void updateCocktailView(int cocktailId, RemoteViews contentView) {
    if (getService() == null) {
      return;
    }
    try {
      this.mService.partiallyUpdateCocktail(this.mPackageName, contentView, cocktailId);
    } catch (RemoteException e) {
      throw new RuntimeException("CocktailBarService dead?", e);
    }
  }

  public void updateCocktailHelpView(int cocktailId, RemoteViews helpViews) {
    if (getService() == null) {
      return;
    }
    try {
      this.mService.partiallyUpdateHelpView(this.mPackageName, helpViews, cocktailId);
    } catch (RemoteException e) {
      throw new RuntimeException("CocktailBarService dead?", e);
    }
  }

  public void showCocktail(int cocktailId) {
    if (getService() == null) {
      return;
    }
    try {
      this.mService.showCocktail(this.mPackageName, cocktailId);
    } catch (RemoteException e) {
      throw new RuntimeException("CocktailBarService dead?", e);
    }
  }

  public void closeCocktail(int cocktailId) {
    if (getService() == null) {
      return;
    }
    try {
      this.mService.closeCocktail(this.mPackageName, cocktailId, 65536);
    } catch (RemoteException e) {
      throw new RuntimeException("CocktailBarService dead?", e);
    }
  }

  public void notifyCocktailViewDataChanged(int cocktailId, int viewId) {
    if (getService() == null) {
      return;
    }
    try {
      this.mService.notifyCocktailViewDataChanged(this.mPackageName, cocktailId, viewId);
    } catch (RemoteException e) {
      throw new RuntimeException("CocktailBarService dead?", e);
    }
  }

  @Deprecated
  public void disableCocktail(ComponentName provider) {
    if (getService() == null) {
      return;
    }
    try {
      this.mService.disableCocktail(this.mPackageName, provider);
    } catch (RemoteException e) {
      throw new RuntimeException("CocktailBarService dead?", e);
    }
  }

  public int getCocktailBarWindowType() {
    if (getService() == null) {
      return 0;
    }
    try {
      return this.mService.getCocktailBarStateInfo().windowType;
    } catch (RemoteException e) {
      throw new RuntimeException("CocktailBarService dead?", e);
    }
  }

  public void registerStateListener(CocktailBarStateChangedListener stateChangedlistener) {
    throw new RuntimeException("registered SemCocktailManager");
  }

  public void unregisterStateListener(CocktailBarStateChangedListener stateChangedlistener) {
    throw new RuntimeException("registered SemCocktailManager");
  }

  public int getSystemBarAppearance() {
    if (getService() == null) {
      return 0;
    }
    try {
      return this.mService.getSystemBarAppearance();
    } catch (RemoteException e) {
      e.printStackTrace();
      return 0;
    }
  }
}
