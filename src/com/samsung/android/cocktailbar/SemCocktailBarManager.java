package com.samsung.android.cocktailbar;

import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.widget.RemoteViews;
import com.samsung.android.cocktailbar.CocktailInfo;
import com.samsung.android.cocktailbar.ICocktailBarService;
import com.samsung.android.util.SemLog;

/* loaded from: classes6.dex */
public class SemCocktailBarManager {
    public static final int COCKTAIL_CATEGORY_CONTEXTUAL = 65536;
    public static final int COCKTAIL_CATEGORY_GLOBAL = 1;
    public static final int COCKTAIL_CATEGORY_SECURE = 16;

    @Deprecated
    public static final int COCKTAIL_DISPLAY_POLICY_ALL = 143;
    public static final int COCKTAIL_DISPLAY_POLICY_GENERAL = 1;

    @Deprecated
    public static final int COCKTAIL_DISPLAY_POLICY_LOCKSCREEN = 2;

    @Deprecated
    public static final int COCKTAIL_DISPLAY_POLICY_NOT_PROVISION = 128;

    @Deprecated
    public static final int COCKTAIL_DISPLAY_POLICY_SCOVER = 4;

    @Deprecated
    public static final int COCKTAIL_DISPLAY_POLICY_TABLE_MODE = 8;
    public static final int COCKTAIL_VISIBILITY_HIDE = 2;
    public static final int COCKTAIL_VISIBILITY_SHOW = 1;
    public static final int INVALID_COCKTAIL_ID = 0;
    private static final String TAG = "SemCocktailBarManager";
    protected Context mContext;
    protected final String mPackageName;
    protected ICocktailBarService mService;

    public interface CocktailBarStateChangedListener {
        void onCocktailBarStateChanged(SemCocktailBarStateInfo semCocktailBarStateInfo);
    }

    public static SemCocktailBarManager getInstance(Context context) {
        return (SemCocktailBarManager) context.getSystemService(Context.COCKTAIL_BAR_SERVICE);
    }

    public SemCocktailBarManager(Context context, ICocktailBarService iCocktailBarService) {
        this.mContext = context;
        this.mPackageName = context.getOpPackageName();
        this.mService = iCocktailBarService;
    }

    public Context getContext() {
        return this.mContext;
    }

    private ICocktailBarService getService() {
        if (this.mService == null) {
            this.mService = ICocktailBarService.Stub.asInterface(ServiceManager.getService(Context.COCKTAIL_BAR_SERVICE));
        }
        return this.mService;
    }

    public int[] getCocktailIds(ComponentName componentName) {
        if (getService() == null || componentName == null) {
            return new int[]{0};
        }
        try {
            return this.mService.getCocktailIds(this.mPackageName, componentName);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public boolean isCocktailEnabled(ComponentName componentName) {
        if (getService() == null || componentName == null) {
            return false;
        }
        try {
            return this.mService.isEnabledCocktail(this.mPackageName, componentName);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public boolean isCocktailEnabledInternal(ComponentName componentName) {
        if (getService() == null || componentName == null) {
            return false;
        }
        try {
            return this.mService.isCocktailEnabled(this.mPackageName, componentName);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public void updateCocktail(int i, int i2, int i3, RemoteViews remoteViews, RemoteViews remoteViews2) {
        if (getService() == null) {
            SemLog.w(TAG, "updateCocktail : service is not running " + i);
            return;
        }
        try {
            this.mService.updateCocktail(this.mPackageName, new CocktailInfo.Builder(this.mContext).setOrientation(this.mContext.getResources().getConfiguration().orientation).setDiplayPolicy(i2).setCategory(i3).setContentView(remoteViews).setHelpView(remoteViews2).build(), i);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public void updateCocktail(int i, int i2, int i3, Class<? extends SemAbsCocktailLoadablePanel> cls, Bundle bundle, RemoteViews remoteViews) {
        if (getService() == null) {
            SemLog.w(TAG, "updateCocktail : service is not running " + i);
            return;
        }
        try {
            this.mService.updateCocktail(this.mPackageName, new CocktailInfo.Builder(this.mContext).setOrientation(this.mContext.getResources().getConfiguration().orientation).setDiplayPolicy(i2).setCategory(i3).setHelpView(remoteViews).setContentInfo(bundle).setClassloader(new ComponentName(getContext(), cls)).build(), i);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public void updateCocktailView(int i, RemoteViews remoteViews) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.partiallyUpdateCocktail(this.mPackageName, remoteViews, i);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public void updateCocktailHelpView(int i, RemoteViews remoteViews) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.partiallyUpdateHelpView(this.mPackageName, remoteViews, i);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public void showCocktail(int i) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.showCocktail(this.mPackageName, i);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public void closeCocktail(int i) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.closeCocktail(this.mPackageName, i, 65536);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public void notifyCocktailViewDataChanged(int i, int i2) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.notifyCocktailViewDataChanged(this.mPackageName, i, i2);
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

    public void registerStateListener(CocktailBarStateChangedListener cocktailBarStateChangedListener) {
        throw new RuntimeException("registered SemCocktailManager");
    }

    public void unregisterStateListener(CocktailBarStateChangedListener cocktailBarStateChangedListener) {
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
