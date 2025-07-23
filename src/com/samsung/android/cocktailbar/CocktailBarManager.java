package com.samsung.android.cocktailbar;

import android.app.IServiceConnection;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.view.DragEvent;
import android.widget.RemoteViews;
import com.samsung.android.cocktailbar.CocktailInfo;
import com.samsung.android.cocktailbar.ICocktailBarService;
import com.samsung.android.cocktailbar.ICocktailBarStateCallback;
import com.samsung.android.cocktailbar.ISystemUiVisibilityCallback;
import com.samsung.android.cocktailbar.SemCocktailBarManager;
import com.samsung.android.util.SemLog;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes6.dex */
public class CocktailBarManager extends SemCocktailBarManager {
    public static final String ACTION_COCKTAIL_BAR_COCKTAIL_UNINSTALLED = "com.samsung.android.app.cocktailbarservice.action.COCKTAIL_BAR_COCKTAIL_UNINSTALLED";
    public static final String ACTION_COCKTAIL_DISABLED = "com.samsung.android.cocktail.action.COCKTAIL_DISABLED";
    public static final String ACTION_COCKTAIL_ENABLED = "com.samsung.android.cocktail.action.COCKTAIL_ENABLED";
    public static final String ACTION_COCKTAIL_UPDATE = "com.samsung.android.cocktail.action.COCKTAIL_UPDATE";
    public static final String ACTION_COCKTAIL_UPDATE_V2 = "com.samsung.android.cocktail.v2.action.COCKTAIL_UPDATE";
    public static final String ACTION_COCKTAIL_VISIBILITY_CHANGED = "com.samsung.android.cocktail.action.COCKTAIL_VISIBILITY_CHANGED";
    public static final int COCKTAIL_CATEGORY_CONTEXTUAL = 65536;
    public static final int COCKTAIL_CATEGORY_GLOBAL = 1;
    public static final int COCKTAIL_CATEGORY_LOCKSCREEN = 16;
    public static final int COCKTAIL_DISPLAY_POLICY_ALL = 143;
    public static final int COCKTAIL_DISPLAY_POLICY_GENERAL = 1;
    public static final int COCKTAIL_DISPLAY_POLICY_LOCKSCREEN = 2;
    public static final int COCKTAIL_DISPLAY_POLICY_NOT_PROVISION = 128;
    public static final int COCKTAIL_DISPLAY_POLICY_SCOVER = 4;
    public static final int COCKTAIL_DISPLAY_POLICY_TABLE_MODE = 8;
    public static final int COCKTAIL_VISIBILITY_HIDE = 2;
    public static final int COCKTAIL_VISIBILITY_SHOW = 1;
    public static final String EXTRA_COCKTAIL_ID = "cocktailId";
    public static final String EXTRA_COCKTAIL_IDS = "cocktailIds";
    public static final String EXTRA_COCKTAIL_VISIBILITY = "cocktailVisibility";
    public static final int INVALID_COCKTAIL_ID = 0;
    public static final String META_DATA_COCKTAIL_PROVIDER = "com.samsung.android.cocktail.provider";
    public static final String PERMISSION_ACCESS_PANEL = "com.samsung.android.app.cocktailbarservice.permission.ACCESS_PANEL";
    private static final String TAG = "CocktailBarManager";
    public static final int TYPE_WAKEUP_GESTURE_PICKUP = 1;
    public static final int TYPE_WAKEUP_GESTURE_RUB = 2;
    private int mCocktailBarSize;
    private final CopyOnWriteArrayList<CocktailBarStateListenerDelegate> mCocktailBarStateListenerDelegates;
    private final Object mStateListnerDelegatesLock;
    private final CopyOnWriteArrayList<SystemUiVisibilityListenerDelegate> mSystemUiVisibilityListenerDelegates;
    private final Object mSystemUiVisibilityListenerDelegatesLock;

    public interface CocktailBarStateChangedListener {
        void onCocktailBarStateChanged(CocktailBarStateInfo cocktailBarStateInfo);
    }

    @Deprecated
    public static class CocktailBarStateListener {
        public void onCocktailBarPositionChanged(int i) {
        }

        public void onCocktailBarStateChanged(CocktailBarStateInfo cocktailBarStateInfo) {
        }

        public void onCocktailBarVisibilityChanged(int i) {
        }

        public void onCocktailBarWindowTypeChanged(int i) {
        }
    }

    public static final class SystemUiVisibility {
        public static final int UI_FULLSCREEN = 1;
        public static final int UI_IMMERSIVE = 2;
        public static final int UI_TRANSIENT = 4;
    }

    public static class SystemUiVisibilityListener {
        public void onSystemUiVisibilityChanged(int i) {
        }
    }

    @Deprecated
    public void cocktailBarreboot() {
    }

    @Deprecated
    public void cocktailBarshutdown() {
    }

    @Deprecated
    public int getCocktailBarSize() {
        return 160;
    }

    @Deprecated
    public boolean isAllowTransientBarCocktailBar() {
        return false;
    }

    @Deprecated
    public boolean isCocktailBarShifted() {
        return false;
    }

    @Deprecated
    public boolean isImmersiveMode() {
        return false;
    }

    @Deprecated
    public void sendDragEvent(int i, DragEvent dragEvent) {
    }

    @Deprecated
    public void setCocktailBarStatus(boolean z, boolean z2) {
    }

    @Deprecated
    public void setDisableTickerView(int i) {
    }

    @Deprecated
    public void showAndLockCocktailBar() {
    }

    @Deprecated
    public void switchDefaultCocktail() {
    }

    @Deprecated
    public void unlockCocktailBar(int i) {
    }

    @Deprecated
    public void updateCocktailBarStateFromSystem(int i) {
    }

    @Deprecated
    public void updateLongpressGesture(boolean z) {
    }

    @Deprecated
    public void updateSysfsBarLength(int i) {
    }

    @Deprecated
    public void updateSysfsDeadZone(int i) {
    }

    @Deprecated
    public void updateSysfsGripDisable(boolean z) {
    }

    public void wakeupCocktailBar(boolean z, int i, int i2) {
    }

    public static class WindowTypes {
        public static final int WINDOW_TYPE_COCKTAIL_BAR_BACKGROUND = 8;
        public static final int WINDOW_TYPE_IMMERSIVE = 2;
        public static final int WINDOW_TYPE_INPUT_METHOD = 4;
        public static final int WINDOW_TYPE_KEYGUARD = 5;
        public static final int WINDOW_TYPE_NORMAL = 1;
        public static final int WINDOW_TYPE_POPUP = 6;
        public static final int WINDOW_TYPE_RESERVE = 4096;
        public static final int WINDOW_TYPE_SCOVER = 7;
        public static final int WINDOW_TYPE_STATUS_BAR = 3;

        private WindowTypes() {
        }
    }

    @Deprecated
    public static class States {

        @Deprecated
        public static final int COCKTAIL_BAR_FULLSCREEN_TYPE = 2;

        @Deprecated
        public static final int COCKTAIL_BAR_MINIMIZE_TYPE = 1;
        public static final int COCKTAIL_BAR_POSITION_BOTTOM = 4;
        public static final int COCKTAIL_BAR_POSITION_LEFT = 1;
        public static final int COCKTAIL_BAR_POSITION_RIGHT = 2;
        public static final int COCKTAIL_BAR_POSITION_TOP = 3;
        public static final int COCKTAIL_BAR_POSITION_UNKNOWN = 0;
        public static final int COCKTAIL_BAR_STATE_INVISIBLE = 2;
        public static final int COCKTAIL_BAR_STATE_VISIBLE = 1;
        public static final int COCKTAIL_BAR_TYPE_FULLSCREEN = 2;
        public static final int COCKTAIL_BAR_TYPE_MINIMIZE = 1;
        public static final int COCKTAIL_BAR_UNKNOWN_TYPE = 0;

        private States() {
        }
    }

    public static class WakeUp {
        public static final int REASON_BY_DISMISS_KEYGUARD = 3;
        public static final int REASON_BY_NONE = 0;
        public static final int REASON_BY_POWER_MANAGER = 4;
        public static final int REASON_BY_SCREEN_TURN_ON = 2;
        public static final int REASON_BY_WINDOW_POLICY = 1;

        private WakeUp() {
        }
    }

    public static CocktailBarManager getInstance(Context context) {
        return (CocktailBarManager) context.getSystemService(Context.COCKTAIL_BAR_SERVICE);
    }

    public CocktailBarManager(Context context, ICocktailBarService iCocktailBarService) {
        super(context, iCocktailBarService);
        this.mCocktailBarSize = -1;
        this.mStateListnerDelegatesLock = new Object();
        this.mCocktailBarStateListenerDelegates = new CopyOnWriteArrayList<>();
        this.mSystemUiVisibilityListenerDelegatesLock = new Object();
        this.mSystemUiVisibilityListenerDelegates = new CopyOnWriteArrayList<>();
    }

    @Override // com.samsung.android.cocktailbar.SemCocktailBarManager
    public Context getContext() {
        return this.mContext;
    }

    @Deprecated
    public int getCocktailId(ComponentName componentName) {
        if (getService() == null || componentName == null) {
            return 0;
        }
        try {
            return this.mService.getCocktailId(this.mPackageName, componentName);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    private ICocktailBarService getService() {
        if (this.mService == null) {
            this.mService = ICocktailBarService.Stub.asInterface(ServiceManager.getService(Context.COCKTAIL_BAR_SERVICE));
        }
        return this.mService;
    }

    @Override // com.samsung.android.cocktailbar.SemCocktailBarManager
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

    @Deprecated
    public boolean isEnabledCocktail(ComponentName componentName) {
        if (getService() == null || componentName == null) {
            return false;
        }
        try {
            return this.mService.isEnabledCocktail(this.mPackageName, componentName);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Override // com.samsung.android.cocktailbar.SemCocktailBarManager
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

    @Override // com.samsung.android.cocktailbar.SemCocktailBarManager
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

    @Override // com.samsung.android.cocktailbar.SemCocktailBarManager
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

    @Deprecated
    public void updateCocktail(int i, int i2, int i3, RemoteViews remoteViews, Bundle bundle) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.updateCocktail(this.mPackageName, new CocktailInfo.Builder(this.mContext).setOrientation(this.mContext.getResources().getConfiguration().orientation).setDiplayPolicy(i2).setCategory(i3).setContentView(remoteViews).setContentInfo(bundle).build(), i);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Deprecated
    public void updateCocktail(int i, int i2, int i3, RemoteViews remoteViews, RemoteViews remoteViews2, Bundle bundle) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.updateCocktail(this.mPackageName, new CocktailInfo.Builder(this.mContext).setOrientation(this.mContext.getResources().getConfiguration().orientation).setDiplayPolicy(i2).setCategory(i3).setContentView(remoteViews).setHelpView(remoteViews2).setContentInfo(bundle).build(), i);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Deprecated
    public void updateCocktail(int i, int i2, int i3, RemoteViews remoteViews, RemoteViews remoteViews2, Bundle bundle, ComponentName componentName) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.updateCocktail(this.mPackageName, new CocktailInfo.Builder(this.mContext).setOrientation(this.mContext.getResources().getConfiguration().orientation).setDiplayPolicy(i2).setCategory(i3).setContentView(remoteViews).setHelpView(remoteViews2).setContentInfo(bundle).setClassloader(componentName).build(), i);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Deprecated
    public void partiallyUpdateCocktail(int i, RemoteViews remoteViews) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.partiallyUpdateCocktail(this.mPackageName, remoteViews, i);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Deprecated
    public void partiallyUpdateHelpView(int i, RemoteViews remoteViews) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.partiallyUpdateHelpView(this.mPackageName, remoteViews, i);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Override // com.samsung.android.cocktailbar.SemCocktailBarManager
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

    @Override // com.samsung.android.cocktailbar.SemCocktailBarManager
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

    @Override // com.samsung.android.cocktailbar.SemCocktailBarManager
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

    @Deprecated
    public void closeCocktail(int i, int i2) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.closeCocktail(this.mPackageName, i, i2);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Override // com.samsung.android.cocktailbar.SemCocktailBarManager
    public void closeCocktail(int i) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.closeCocktail(this.mPackageName, i, 1);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Override // com.samsung.android.cocktailbar.SemCocktailBarManager
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

    public void setOnPullPendingIntent(int i, int i2, PendingIntent pendingIntent) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.setOnPullPendingIntent(this.mPackageName, i, i2, pendingIntent);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Deprecated
    public void updateFeeds(int i, List<FeedsInfo> list) {
        if (getService() == null) {
            return;
        }
        if (list == null) {
            SemLog.e(TAG, "updateFeeds : feedsInfoList is null");
            return;
        }
        throw new RuntimeException("updateFeeds not supported.");
    }

    public void setEnabledCocktailIds(int[] iArr) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.setEnabledCocktailIds(iArr);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public int[] getEnabledCocktailIds() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getEnabledCocktailIds();
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public int[] getAllCocktailIds() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getAllCocktailIds();
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public Cocktail getCocktail(int i) {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getCocktail(i);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public boolean requestToUpdateCocktail(int i) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.requestToUpdateCocktail(i);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public boolean requestToDisableCocktail(int i) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.requestToDisableCocktail(i);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public boolean requestToUpdateCocktailByCategory(int i) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.requestToUpdateCocktailByCategory(i);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public boolean requestToDisableCocktailByCategory(int i) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.requestToDisableCocktailByCategory(i);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public void notifyKeyguardState(boolean z) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.notifyKeyguardState(z);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public void notifyCocktailVisibiltyChanged(int i, int i2) {
        if (getService() == null) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mService.notifyCocktailVisibiltyChanged(i, i2);
            } catch (RemoteException e) {
                throw new RuntimeException("CocktailBarService dead?", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean bindRemoteViewsService(Context context, int i, Intent intent, IServiceConnection iServiceConnection, int i2) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.bindRemoteViewsService(context.getOpPackageName(), i, intent, context.getIApplicationThread(), context.getActivityToken(), iServiceConnection, i2);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public void unbindRemoteViewsService(String str, int i, Intent intent) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.unbindRemoteViewsService(str, i, intent);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public void updateWakeupGesture(int i, boolean z) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.updateWakeupGesture(i, z);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Deprecated
    public void updateWakeupArea(int i) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.updateWakeupArea(i);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public void setCocktailBarWakeUpState(boolean z) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.setCocktailBarWakeUpState(z);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public boolean getCocktaiBarWakeUpState() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.getCocktaiBarWakeUpState();
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Deprecated
    public void sendExtraDataToCocktailBar(Bundle bundle) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.sendExtraDataToCocktailBar(bundle);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public void removeCocktailUIService() {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.removeCocktailUIService();
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Deprecated
    public int getCocktailBarVisibility() {
        if (getService() == null) {
            return 2;
        }
        try {
            return this.mService.getCocktailBarVisibility();
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Override // com.samsung.android.cocktailbar.SemCocktailBarManager
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

    @Deprecated
    public void activateCocktailBar() {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.activateCocktailBar();
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Deprecated
    public void deactivateCocktailBar() {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.deactivateCocktailBar();
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public void updateCocktailBarVisibility(int i) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.updateCocktailBarVisibility(i);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public void updateCocktailBarPosition(int i) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.updateCocktailBarPosition(i);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public void updateCocktailBarWindowType(int i) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.updateCocktailBarWindowType(this.mContext.getPackageName(), i);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Deprecated
    public void registerListener(CocktailBarStateListener cocktailBarStateListener) {
        CocktailBarStateListenerDelegate cocktailBarStateListenerDelegate;
        if (getService() == null) {
            return;
        }
        if (cocktailBarStateListener == null) {
            SemLog.w(TAG, "registerListener : listener is null");
            return;
        }
        synchronized (this.mStateListnerDelegatesLock) {
            Iterator<CocktailBarStateListenerDelegate> it = this.mCocktailBarStateListenerDelegates.iterator();
            while (true) {
                if (!it.hasNext()) {
                    cocktailBarStateListenerDelegate = null;
                    break;
                }
                cocktailBarStateListenerDelegate = it.next();
                if (cocktailBarStateListenerDelegate != null && cocktailBarStateListenerDelegate.getListener().equals(cocktailBarStateListener)) {
                    break;
                }
            }
            if (cocktailBarStateListenerDelegate == null) {
                cocktailBarStateListenerDelegate = new CocktailBarStateListenerDelegate(this, cocktailBarStateListener, (Handler) null);
                this.mCocktailBarStateListenerDelegates.add(cocktailBarStateListenerDelegate);
            }
            ComponentName componentName = new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName());
            try {
                SemLog.i(TAG, "registerListener : registerCocktailBarStateListenerCallback " + this.mContext.getPackageName());
                this.mService.registerCocktailBarStateListenerCallback(cocktailBarStateListenerDelegate, componentName);
            } catch (RemoteException e) {
                SemLog.e(TAG, "registerListener : RemoteException : ", e);
            }
        }
    }

    @Deprecated
    public void unregisterListener(CocktailBarStateListener cocktailBarStateListener) {
        CocktailBarStateListenerDelegate cocktailBarStateListenerDelegate;
        if (getService() == null) {
            return;
        }
        if (cocktailBarStateListener == null) {
            SemLog.w(TAG, "unregisterListener : listener is null");
            return;
        }
        synchronized (this.mStateListnerDelegatesLock) {
            Iterator<CocktailBarStateListenerDelegate> it = this.mCocktailBarStateListenerDelegates.iterator();
            while (true) {
                if (!it.hasNext()) {
                    cocktailBarStateListenerDelegate = null;
                    break;
                }
                cocktailBarStateListenerDelegate = it.next();
                if (cocktailBarStateListenerDelegate != null && cocktailBarStateListenerDelegate.getListener().equals(cocktailBarStateListener)) {
                    break;
                }
            }
            if (cocktailBarStateListenerDelegate == null) {
                SemLog.w(TAG, "unregisterListener : cannot find the listener");
                return;
            }
            try {
                SemLog.i(TAG, "unregisterListener : unregisterCocktailBarStateListenerCallback " + this.mContext.getPackageName());
                this.mService.unregisterCocktailBarStateListenerCallback(cocktailBarStateListenerDelegate);
                this.mCocktailBarStateListenerDelegates.remove(cocktailBarStateListenerDelegate);
                cocktailBarStateListenerDelegate.onDestroy();
            } catch (RemoteException e) {
                SemLog.e(TAG, "unregisterListener : RemoteException : ", e);
            }
        }
    }

    @Override // com.samsung.android.cocktailbar.SemCocktailBarManager
    public void registerStateListener(SemCocktailBarManager.CocktailBarStateChangedListener cocktailBarStateChangedListener) {
        CocktailBarStateListenerDelegate cocktailBarStateListenerDelegate;
        if (getService() == null) {
            return;
        }
        if (cocktailBarStateChangedListener == null) {
            SemLog.w(TAG, "registerListener : listener is null");
            return;
        }
        synchronized (this.mStateListnerDelegatesLock) {
            Iterator<CocktailBarStateListenerDelegate> it = this.mCocktailBarStateListenerDelegates.iterator();
            while (true) {
                if (!it.hasNext()) {
                    cocktailBarStateListenerDelegate = null;
                    break;
                }
                cocktailBarStateListenerDelegate = it.next();
                if (cocktailBarStateListenerDelegate != null && (cocktailBarStateListenerDelegate.getStateChangedListener() instanceof SemManagerStateChangedListenerWrapper) && cocktailBarStateChangedListener.equals(((SemManagerStateChangedListenerWrapper) cocktailBarStateListenerDelegate.getStateChangedListener()).mSemlistener)) {
                    break;
                }
            }
            if (cocktailBarStateListenerDelegate == null) {
                cocktailBarStateListenerDelegate = new CocktailBarStateListenerDelegate(this, new SemManagerStateChangedListenerWrapper(cocktailBarStateChangedListener), (Handler) null);
                this.mCocktailBarStateListenerDelegates.add(cocktailBarStateListenerDelegate);
            }
            ComponentName componentName = new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName());
            try {
                SemLog.i(TAG, "registerListener : registerCocktailBarStateListenerCallback " + this.mContext.getPackageName());
                this.mService.registerCocktailBarStateListenerCallback(cocktailBarStateListenerDelegate, componentName);
            } catch (RemoteException e) {
                SemLog.e(TAG, "registerListener : RemoteException : ", e);
            }
        }
    }

    @Override // com.samsung.android.cocktailbar.SemCocktailBarManager
    public void unregisterStateListener(SemCocktailBarManager.CocktailBarStateChangedListener cocktailBarStateChangedListener) {
        CocktailBarStateListenerDelegate cocktailBarStateListenerDelegate;
        if (getService() == null) {
            return;
        }
        if (cocktailBarStateChangedListener == null) {
            SemLog.w(TAG, "unregisterListener : listener is null");
            return;
        }
        synchronized (this.mStateListnerDelegatesLock) {
            Iterator<CocktailBarStateListenerDelegate> it = this.mCocktailBarStateListenerDelegates.iterator();
            while (true) {
                if (!it.hasNext()) {
                    cocktailBarStateListenerDelegate = null;
                    break;
                }
                cocktailBarStateListenerDelegate = it.next();
                if (cocktailBarStateListenerDelegate != null && (cocktailBarStateListenerDelegate.getStateChangedListener() instanceof SemManagerStateChangedListenerWrapper) && cocktailBarStateChangedListener.equals(((SemManagerStateChangedListenerWrapper) cocktailBarStateListenerDelegate.getStateChangedListener()).mSemlistener)) {
                    break;
                }
            }
            if (cocktailBarStateListenerDelegate == null) {
                SemLog.w(TAG, "unregisterListener : cannot find the listener");
                return;
            }
            try {
                SemLog.i(TAG, "unregisterListener : unregisterCocktailBarStateListenerCallback " + this.mContext.getPackageName());
                this.mService.unregisterCocktailBarStateListenerCallback(cocktailBarStateListenerDelegate);
                this.mCocktailBarStateListenerDelegates.remove(cocktailBarStateListenerDelegate);
                cocktailBarStateListenerDelegate.onDestroy();
            } catch (RemoteException e) {
                SemLog.e(TAG, "unregisterListener : RemoteException : ", e);
            }
        }
    }

    public void registerStateListener(CocktailBarStateChangedListener cocktailBarStateChangedListener) {
        CocktailBarStateListenerDelegate cocktailBarStateListenerDelegate;
        if (getService() == null) {
            return;
        }
        if (cocktailBarStateChangedListener == null) {
            SemLog.w(TAG, "registerListener : listener is null");
            return;
        }
        synchronized (this.mStateListnerDelegatesLock) {
            Iterator<CocktailBarStateListenerDelegate> it = this.mCocktailBarStateListenerDelegates.iterator();
            while (true) {
                if (!it.hasNext()) {
                    cocktailBarStateListenerDelegate = null;
                    break;
                }
                cocktailBarStateListenerDelegate = it.next();
                if (cocktailBarStateListenerDelegate != null && cocktailBarStateListenerDelegate.getStateChangedListener().equals(cocktailBarStateChangedListener)) {
                    break;
                }
            }
            if (cocktailBarStateListenerDelegate == null) {
                cocktailBarStateListenerDelegate = new CocktailBarStateListenerDelegate(this, cocktailBarStateChangedListener, (Handler) null);
                this.mCocktailBarStateListenerDelegates.add(cocktailBarStateListenerDelegate);
            }
            ComponentName componentName = new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName());
            try {
                SemLog.i(TAG, "registerListener : registerCocktailBarStateListenerCallback " + this.mContext.getPackageName());
                this.mService.registerCocktailBarStateListenerCallback(cocktailBarStateListenerDelegate, componentName);
            } catch (RemoteException e) {
                SemLog.e(TAG, "registerListener : RemoteException : ", e);
            }
        }
    }

    public void unregisterStateListener(CocktailBarStateChangedListener cocktailBarStateChangedListener) {
        CocktailBarStateListenerDelegate cocktailBarStateListenerDelegate;
        if (getService() == null) {
            return;
        }
        if (cocktailBarStateChangedListener == null) {
            SemLog.w(TAG, "unregisterListener : listener is null");
            return;
        }
        synchronized (this.mStateListnerDelegatesLock) {
            Iterator<CocktailBarStateListenerDelegate> it = this.mCocktailBarStateListenerDelegates.iterator();
            while (true) {
                if (!it.hasNext()) {
                    cocktailBarStateListenerDelegate = null;
                    break;
                }
                cocktailBarStateListenerDelegate = it.next();
                if (cocktailBarStateListenerDelegate != null && cocktailBarStateListenerDelegate.getStateChangedListener().equals(cocktailBarStateChangedListener)) {
                    break;
                }
            }
            if (cocktailBarStateListenerDelegate == null) {
                SemLog.w(TAG, "unregisterListener : cannot find the listener");
                return;
            }
            try {
                SemLog.i(TAG, "unregisterListener : unregisterCocktailBarStateListenerCallback " + this.mContext.getPackageName());
                this.mService.unregisterCocktailBarStateListenerCallback(cocktailBarStateListenerDelegate);
                this.mCocktailBarStateListenerDelegates.remove(cocktailBarStateListenerDelegate);
                cocktailBarStateListenerDelegate.onDestroy();
            } catch (RemoteException e) {
                SemLog.e(TAG, "unregisterListener : RemoteException : ", e);
            }
        }
    }

    private class CocktailBarStateListenerDelegate extends ICocktailBarStateCallback.Stub {
        private static final int MSG_LISTEN_COCKTAIL_BAR_STATE_CHANGE = 0;
        private Handler mHandler;

        @Deprecated
        private CocktailBarStateListener mListener;
        private CocktailBarStateChangedListener mStateChangedListener;

        @Deprecated
        public CocktailBarStateListenerDelegate(final CocktailBarManager cocktailBarManager, CocktailBarStateListener cocktailBarStateListener, Handler handler) {
            Looper looper;
            this.mListener = cocktailBarStateListener;
            this.mStateChangedListener = null;
            if (handler == null) {
                looper = cocktailBarManager.mContext.getMainLooper();
            } else {
                looper = handler.getLooper();
            }
            this.mHandler = new Handler(looper) { // from class: com.samsung.android.cocktailbar.CocktailBarManager.CocktailBarStateListenerDelegate.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    if (CocktailBarStateListenerDelegate.this.mListener != null && message.what == 0) {
                        CocktailBarStateInfo cocktailBarStateInfo = (CocktailBarStateInfo) message.obj;
                        if (cocktailBarStateInfo.changeFlag == 0) {
                            return;
                        }
                        CocktailBarStateListenerDelegate.this.mListener.onCocktailBarStateChanged(cocktailBarStateInfo);
                        if ((cocktailBarStateInfo.changeFlag & 1) != 0) {
                            CocktailBarStateListenerDelegate.this.mListener.onCocktailBarVisibilityChanged(cocktailBarStateInfo.visibility);
                        }
                        if ((cocktailBarStateInfo.changeFlag & 4) != 0) {
                            CocktailBarStateListenerDelegate.this.mListener.onCocktailBarPositionChanged(cocktailBarStateInfo.position);
                        }
                        if ((cocktailBarStateInfo.changeFlag & 128) != 0) {
                            CocktailBarStateListenerDelegate.this.mListener.onCocktailBarWindowTypeChanged(cocktailBarStateInfo.windowType);
                        }
                    }
                }
            };
        }

        public CocktailBarStateListenerDelegate(final CocktailBarManager cocktailBarManager, CocktailBarStateChangedListener cocktailBarStateChangedListener, Handler handler) {
            Looper looper;
            this.mStateChangedListener = cocktailBarStateChangedListener;
            this.mListener = null;
            if (handler == null) {
                looper = cocktailBarManager.mContext.getMainLooper();
            } else {
                looper = handler.getLooper();
            }
            this.mHandler = new Handler(looper) { // from class: com.samsung.android.cocktailbar.CocktailBarManager.CocktailBarStateListenerDelegate.2
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    if (CocktailBarStateListenerDelegate.this.mStateChangedListener != null && message.what == 0) {
                        CocktailBarStateInfo cocktailBarStateInfo = (CocktailBarStateInfo) message.obj;
                        if (cocktailBarStateInfo.changeFlag != 0) {
                            CocktailBarStateListenerDelegate.this.mStateChangedListener.onCocktailBarStateChanged(cocktailBarStateInfo);
                        }
                    }
                }
            };
        }

        @Deprecated
        public CocktailBarStateListener getListener() {
            return this.mListener;
        }

        public CocktailBarStateChangedListener getStateChangedListener() {
            return this.mStateChangedListener;
        }

        @Override // com.samsung.android.cocktailbar.ICocktailBarStateCallback
        public void onCocktailBarStateChanged(CocktailBarStateInfo cocktailBarStateInfo) throws RemoteException {
            Message.obtain(this.mHandler, 0, cocktailBarStateInfo).sendToTarget();
        }

        public void onDestroy() {
            this.mHandler = null;
            this.mListener = null;
            this.mStateChangedListener = null;
        }
    }

    public static class SemManagerStateChangedListenerWrapper implements CocktailBarStateChangedListener {
        public final SemCocktailBarManager.CocktailBarStateChangedListener mSemlistener;

        public SemManagerStateChangedListenerWrapper(SemCocktailBarManager.CocktailBarStateChangedListener cocktailBarStateChangedListener) {
            this.mSemlistener = cocktailBarStateChangedListener;
        }

        @Override // com.samsung.android.cocktailbar.CocktailBarManager.CocktailBarStateChangedListener
        public void onCocktailBarStateChanged(CocktailBarStateInfo cocktailBarStateInfo) {
            SemCocktailBarStateInfo semCocktailBarStateInfo = new SemCocktailBarStateInfo();
            semCocktailBarStateInfo.position = cocktailBarStateInfo.position;
            semCocktailBarStateInfo.visibility = cocktailBarStateInfo.visibility;
            semCocktailBarStateInfo.windowType = cocktailBarStateInfo.windowType;
            this.mSemlistener.onCocktailBarStateChanged(semCocktailBarStateInfo);
        }
    }

    public void registerSystemUiVisibilityListener(SystemUiVisibilityListener systemUiVisibilityListener) {
        SystemUiVisibilityListenerDelegate systemUiVisibilityListenerDelegate;
        if (getService() == null) {
            return;
        }
        if (systemUiVisibilityListener == null) {
            SemLog.w(TAG, "registerListener : listener is null");
            return;
        }
        synchronized (this.mSystemUiVisibilityListenerDelegatesLock) {
            Iterator<SystemUiVisibilityListenerDelegate> it = this.mSystemUiVisibilityListenerDelegates.iterator();
            while (true) {
                if (!it.hasNext()) {
                    systemUiVisibilityListenerDelegate = null;
                    break;
                }
                systemUiVisibilityListenerDelegate = it.next();
                if (systemUiVisibilityListenerDelegate != null && systemUiVisibilityListenerDelegate.getListener().equals(systemUiVisibilityListener)) {
                    break;
                }
            }
            if (systemUiVisibilityListenerDelegate == null) {
                systemUiVisibilityListenerDelegate = new SystemUiVisibilityListenerDelegate(this, systemUiVisibilityListener, null);
                this.mSystemUiVisibilityListenerDelegates.add(systemUiVisibilityListenerDelegate);
            }
            ComponentName componentName = new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName());
            try {
                SemLog.i(TAG, "registerListener : registerSystemUiVisibilityListenerCallback " + this.mContext.getPackageName());
                this.mService.registerSystemUiVisibilityListenerCallback(systemUiVisibilityListenerDelegate, componentName);
            } catch (RemoteException e) {
                SemLog.e(TAG, "registerListener : RemoteException : ", e);
            }
        }
    }

    public void unregisterSystemUiVisibilityListener(SystemUiVisibilityListener systemUiVisibilityListener) {
        SystemUiVisibilityListenerDelegate systemUiVisibilityListenerDelegate;
        if (getService() == null) {
            return;
        }
        if (systemUiVisibilityListener == null) {
            SemLog.w(TAG, "unregisterListener : listener is null");
            return;
        }
        synchronized (this.mSystemUiVisibilityListenerDelegatesLock) {
            Iterator<SystemUiVisibilityListenerDelegate> it = this.mSystemUiVisibilityListenerDelegates.iterator();
            while (true) {
                if (!it.hasNext()) {
                    systemUiVisibilityListenerDelegate = null;
                    break;
                }
                systemUiVisibilityListenerDelegate = it.next();
                if (systemUiVisibilityListenerDelegate != null && systemUiVisibilityListenerDelegate.getListener().equals(systemUiVisibilityListener)) {
                    break;
                }
            }
            if (systemUiVisibilityListenerDelegate == null) {
                SemLog.w(TAG, "unregisterListener : cannot find the listener");
                return;
            }
            try {
                SemLog.i(TAG, "unregisterListener : unregisterSystemUiVisibilityListenerCallback " + this.mContext.getPackageName());
                this.mService.unregisterSystemUiVisibilityListenerCallback(systemUiVisibilityListenerDelegate);
                this.mSystemUiVisibilityListenerDelegates.remove(systemUiVisibilityListenerDelegate);
                systemUiVisibilityListenerDelegate.onDestroy();
            } catch (RemoteException e) {
                SemLog.e(TAG, "unregisterListener : RemoteException : ", e);
            }
        }
    }

    private class SystemUiVisibilityListenerDelegate extends ISystemUiVisibilityCallback.Stub {
        private static final int MSG_SYSTEM_UI_VISIBILITY_CHANGED = 1;
        private Handler mHandler;
        private SystemUiVisibilityListener mListener;

        public SystemUiVisibilityListenerDelegate(final CocktailBarManager cocktailBarManager, SystemUiVisibilityListener systemUiVisibilityListener, Handler handler) {
            Looper looper;
            this.mListener = systemUiVisibilityListener;
            if (handler == null) {
                looper = cocktailBarManager.mContext.getMainLooper();
            } else {
                looper = handler.getLooper();
            }
            this.mHandler = new Handler(looper) { // from class: com.samsung.android.cocktailbar.CocktailBarManager.SystemUiVisibilityListenerDelegate.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    if (SystemUiVisibilityListenerDelegate.this.mListener != null && message.what == 1) {
                        SystemUiVisibilityListenerDelegate.this.mListener.onSystemUiVisibilityChanged(message.arg1);
                    }
                }
            };
        }

        public SystemUiVisibilityListener getListener() {
            return this.mListener;
        }

        @Override // com.samsung.android.cocktailbar.ISystemUiVisibilityCallback
        public void onSystemUiVisibilityChanged(int i) throws RemoteException {
            Message.obtain(this.mHandler, 1, i, 0).sendToTarget();
        }

        public void onDestroy() {
            this.mHandler = null;
            this.mListener = null;
        }
    }

    @Deprecated
    public void registerOnFeedsUpdatedListener(CocktailBarFeedsListener cocktailBarFeedsListener) {
        if (getService() == null) {
            return;
        }
        if (cocktailBarFeedsListener == null) {
            SemLog.w(TAG, "registerOnFeedsUpdatedListener : listener is null");
            return;
        }
        throw new RuntimeException("registerOnFeedsUpdatedListener not supported.");
    }

    @Deprecated
    public void unregisterOnFeedsUpdatedListener(CocktailBarFeedsListener cocktailBarFeedsListener) {
        if (getService() == null) {
            return;
        }
        if (cocktailBarFeedsListener == null) {
            SemLog.w(TAG, "unregisterOnFeedsUpdatedListener : listener is null");
            return;
        }
        throw new RuntimeException("unregisterOnFeedsUpdatedListener not supported.");
    }

    @Deprecated
    public static class CocktailBarFeedsListener {
        @Deprecated
        public void onFeedsUpdated(int i, List<FeedsInfo> list) {
        }

        @Deprecated
        public CocktailBarFeedsListener() {
        }
    }

    public int getConfigVersion() {
        if (getService() == null) {
            Log.d(TAG, "getConfigVersion getService is null");
            return -1;
        }
        try {
            return this.mService.getConfigVersion();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int getPreferWidth() {
        if (getService() == null) {
            Log.d(TAG, "getPreferWidth getService is null");
            return -1;
        }
        try {
            return this.mService.getPreferWidth();
        } catch (RemoteException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String getCategoryFilterStr() {
        if (getService() == null) {
            Log.d(TAG, "getCategoryFilterStr getService is null");
            return null;
        }
        try {
            return this.mService.getCategoryFilterStr();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getHideEdgeListStr() {
        if (getService() == null) {
            Log.d(TAG, "getHideEdgeListStr getService is null");
            return null;
        }
        try {
            return this.mService.getHideEdgeListStr();
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }
}
