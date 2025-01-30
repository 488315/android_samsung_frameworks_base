package com.samsung.android.cocktailbar;

import android.app.IServiceConnection;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.Handler;
import android.p009os.IBinder;
import android.p009os.Looper;
import android.p009os.Message;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
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

/* loaded from: classes5.dex */
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
    private static final String TAG = CocktailBarManager.class.getSimpleName();
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

    public static final class SystemUiVisibility {
        public static final int UI_FULLSCREEN = 1;
        public static final int UI_IMMERSIVE = 2;
        public static final int UI_TRANSIENT = 4;
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

    public CocktailBarManager(Context context, ICocktailBarService service) {
        super(context, service);
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
    public int getCocktailId(ComponentName provider) {
        if (getService() == null || provider == null) {
            return 0;
        }
        try {
            return this.mService.getCocktailId(this.mPackageName, provider);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    private ICocktailBarService getService() {
        if (this.mService == null) {
            IBinder b = ServiceManager.getService(Context.COCKTAIL_BAR_SERVICE);
            this.mService = ICocktailBarService.Stub.asInterface(b);
        }
        return this.mService;
    }

    @Override // com.samsung.android.cocktailbar.SemCocktailBarManager
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

    @Deprecated
    public boolean isEnabledCocktail(ComponentName provider) {
        if (getService() == null || provider == null) {
            return false;
        }
        try {
            return this.mService.isEnabledCocktail(this.mPackageName, provider);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Override // com.samsung.android.cocktailbar.SemCocktailBarManager
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

    @Override // com.samsung.android.cocktailbar.SemCocktailBarManager
    public void updateCocktail(int cocktailId, int displayPolicy, int category, RemoteViews contentView, RemoteViews helpView) {
        if (getService() == null) {
            SemLog.m486w(TAG, "updateCocktail : service is not running " + cocktailId);
            return;
        }
        Configuration configuration = this.mContext.getResources().getConfiguration();
        try {
            CocktailInfo cocktailInfo = new CocktailInfo.Builder(this.mContext).setOrientation(configuration.orientation).setDiplayPolicy(displayPolicy).setCategory(category).setContentView(contentView).setHelpView(helpView).build();
            this.mService.updateCocktail(this.mPackageName, cocktailInfo, cocktailId);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Override // com.samsung.android.cocktailbar.SemCocktailBarManager
    public void updateCocktail(int cocktailId, int displayPolicy, int category, Class<? extends SemAbsCocktailLoadablePanel> panelClass, Bundle contentInfo, RemoteViews helpView) {
        if (getService() == null) {
            SemLog.m486w(TAG, "updateCocktail : service is not running " + cocktailId);
            return;
        }
        ComponentName classInfo = new ComponentName(getContext(), panelClass);
        Configuration configuration = this.mContext.getResources().getConfiguration();
        try {
            CocktailInfo cocktailInfo = new CocktailInfo.Builder(this.mContext).setOrientation(configuration.orientation).setDiplayPolicy(displayPolicy).setCategory(category).setHelpView(helpView).setContentInfo(contentInfo).setClassloader(classInfo).build();
            this.mService.updateCocktail(this.mPackageName, cocktailInfo, cocktailId);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Deprecated
    public void updateCocktail(int cocktailId, int displayPolicy, int category, RemoteViews contentView, Bundle contentInfo) {
        if (getService() == null) {
            return;
        }
        Configuration configuration = this.mContext.getResources().getConfiguration();
        try {
            CocktailInfo cocktailInfo = new CocktailInfo.Builder(this.mContext).setOrientation(configuration.orientation).setDiplayPolicy(displayPolicy).setCategory(category).setContentView(contentView).setContentInfo(contentInfo).build();
            this.mService.updateCocktail(this.mPackageName, cocktailInfo, cocktailId);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Deprecated
    public void updateCocktail(int cocktailId, int displayPolicy, int category, RemoteViews contentView, RemoteViews helpView, Bundle contentInfo) {
        if (getService() == null) {
            return;
        }
        Configuration configuration = this.mContext.getResources().getConfiguration();
        try {
            CocktailInfo cocktailInfo = new CocktailInfo.Builder(this.mContext).setOrientation(configuration.orientation).setDiplayPolicy(displayPolicy).setCategory(category).setContentView(contentView).setHelpView(helpView).setContentInfo(contentInfo).build();
            this.mService.updateCocktail(this.mPackageName, cocktailInfo, cocktailId);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Deprecated
    public void updateCocktail(int cocktailId, int displayPolicy, int category, RemoteViews contentView, RemoteViews helpView, Bundle contentInfo, ComponentName classInfo) {
        if (getService() == null) {
            return;
        }
        Configuration configuration = this.mContext.getResources().getConfiguration();
        try {
            CocktailInfo cocktailInfo = new CocktailInfo.Builder(this.mContext).setOrientation(configuration.orientation).setDiplayPolicy(displayPolicy).setCategory(category).setContentView(contentView).setHelpView(helpView).setContentInfo(contentInfo).setClassloader(classInfo).build();
            this.mService.updateCocktail(this.mPackageName, cocktailInfo, cocktailId);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Deprecated
    public void partiallyUpdateCocktail(int cocktailId, RemoteViews contentView) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.partiallyUpdateCocktail(this.mPackageName, contentView, cocktailId);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Deprecated
    public void partiallyUpdateHelpView(int cocktailId, RemoteViews helpViews) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.partiallyUpdateHelpView(this.mPackageName, helpViews, cocktailId);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Override // com.samsung.android.cocktailbar.SemCocktailBarManager
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

    @Override // com.samsung.android.cocktailbar.SemCocktailBarManager
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

    @Override // com.samsung.android.cocktailbar.SemCocktailBarManager
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

    @Deprecated
    public void closeCocktail(int cocktailId, int category) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.closeCocktail(this.mPackageName, cocktailId, category);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Override // com.samsung.android.cocktailbar.SemCocktailBarManager
    public void closeCocktail(int cocktailId) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.closeCocktail(this.mPackageName, cocktailId, 1);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Override // com.samsung.android.cocktailbar.SemCocktailBarManager
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

    public void setOnPullPendingIntent(int cocktailId, int viewId, PendingIntent pendingIntent) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.setOnPullPendingIntent(this.mPackageName, cocktailId, viewId, pendingIntent);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Deprecated
    public void updateFeeds(int cocktailId, List<FeedsInfo> feedsInfoList) {
        if (getService() == null) {
            return;
        }
        if (feedsInfoList == null) {
            SemLog.m480e(TAG, "updateFeeds : feedsInfoList is null");
            return;
        }
        throw new RuntimeException("updateFeeds not supported.");
    }

    public void setEnabledCocktailIds(int[] cocktailIds) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.setEnabledCocktailIds(cocktailIds);
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

    @Override // com.samsung.android.cocktailbar.SemCocktailBarManager
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

    public Cocktail getCocktail(int cocktailId) {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getCocktail(cocktailId);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public boolean requestToUpdateCocktail(int cocktailId) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.requestToUpdateCocktail(cocktailId);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public boolean requestToDisableCocktail(int cocktailId) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.requestToDisableCocktail(cocktailId);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public boolean requestToUpdateCocktailByCategory(int category) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.requestToUpdateCocktailByCategory(category);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public boolean requestToDisableCocktailByCategory(int category) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.requestToDisableCocktailByCategory(category);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public void notifyKeyguardState(boolean enable) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.notifyKeyguardState(enable);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Deprecated
    public void setDisableTickerView(int state) {
    }

    public void notifyCocktailVisibiltyChanged(int cocktailId, int visibility) {
        if (getService() == null) {
            return;
        }
        long identityToken = Binder.clearCallingIdentity();
        try {
            try {
                this.mService.notifyCocktailVisibiltyChanged(cocktailId, visibility);
            } catch (RemoteException e) {
                throw new RuntimeException("CocktailBarService dead?", e);
            }
        } finally {
            Binder.restoreCallingIdentity(identityToken);
        }
    }

    @Deprecated
    public void sendDragEvent(int cocktailId, DragEvent event) {
    }

    @Deprecated
    public boolean isAllowTransientBarCocktailBar() {
        return false;
    }

    public boolean bindRemoteViewsService(Context context, int appWidgetId, Intent intent, IServiceConnection connection, int flags) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.bindRemoteViewsService(context.getOpPackageName(), appWidgetId, intent, context.getIApplicationThread(), context.getActivityToken(), connection, flags);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public void unbindRemoteViewsService(String packageName, int cocktailId, Intent intent) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.unbindRemoteViewsService(packageName, cocktailId, intent);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Deprecated
    public int getCocktailBarSize() {
        return 160;
    }

    public void updateWakeupGesture(int gestureType, boolean bEnable) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.updateWakeupGesture(gestureType, bEnable);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Deprecated
    public void updateWakeupArea(int area) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.updateWakeupArea(area);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Deprecated
    public void updateLongpressGesture(boolean bEnable) {
    }

    @Deprecated
    public void updateSysfsDeadZone(int deadzone) {
    }

    @Deprecated
    public void updateSysfsBarLength(int barLength) {
    }

    @Deprecated
    public void updateSysfsGripDisable(boolean bDisable) {
    }

    public void wakeupCocktailBar(boolean bEnable, int keyCode, int reason) {
    }

    public void setCocktailBarWakeUpState(boolean enable) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.setCocktailBarWakeUpState(enable);
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
    public boolean isCocktailBarShifted() {
        return false;
    }

    @Deprecated
    public boolean isImmersiveMode() {
        return false;
    }

    @Deprecated
    public void switchDefaultCocktail() {
    }

    @Deprecated
    public void sendExtraDataToCocktailBar(Bundle extraData) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.sendExtraDataToCocktailBar(extraData);
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
    public void cocktailBarshutdown() {
    }

    @Deprecated
    public void cocktailBarreboot() {
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
    public void showAndLockCocktailBar() {
    }

    @Deprecated
    public void unlockCocktailBar(int visibility) {
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

    public void updateCocktailBarVisibility(int visibility) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.updateCocktailBarVisibility(visibility);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Deprecated
    public void updateCocktailBarStateFromSystem(int windowType) {
    }

    @Deprecated
    public void setCocktailBarStatus(boolean shift, boolean transparent) {
    }

    public void updateCocktailBarPosition(int position) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.updateCocktailBarPosition(position);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    public void updateCocktailBarWindowType(int windowType) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.updateCocktailBarWindowType(this.mContext.getPackageName(), windowType);
        } catch (RemoteException e) {
            throw new RuntimeException("CocktailBarService dead?", e);
        }
    }

    @Deprecated
    public void registerListener(CocktailBarStateListener listener) {
        if (getService() == null) {
            return;
        }
        if (listener == null) {
            SemLog.m486w(TAG, "registerListener : listener is null");
            return;
        }
        synchronized (this.mStateListnerDelegatesLock) {
            CocktailBarStateListenerDelegate listenerDelegate = null;
            Iterator<CocktailBarStateListenerDelegate> it = this.mCocktailBarStateListenerDelegates.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                CocktailBarStateListenerDelegate temp = it.next();
                if (temp != null && temp.getListener().equals(listener)) {
                    listenerDelegate = temp;
                    break;
                }
            }
            if (listenerDelegate == null) {
                listenerDelegate = new CocktailBarStateListenerDelegate(listener, (Handler) null);
                this.mCocktailBarStateListenerDelegates.add(listenerDelegate);
            }
            ComponentName cm = new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName());
            try {
                SemLog.m482i(TAG, "registerListener : registerCocktailBarStateListenerCallback " + this.mContext.getPackageName());
                this.mService.registerCocktailBarStateListenerCallback(listenerDelegate, cm);
            } catch (RemoteException e) {
                SemLog.m481e(TAG, "registerListener : RemoteException : ", e);
            }
        }
    }

    @Deprecated
    public void unregisterListener(CocktailBarStateListener listener) {
        if (getService() == null) {
            return;
        }
        if (listener == null) {
            SemLog.m486w(TAG, "unregisterListener : listener is null");
            return;
        }
        synchronized (this.mStateListnerDelegatesLock) {
            CocktailBarStateListenerDelegate listenerDelegate = null;
            Iterator<CocktailBarStateListenerDelegate> it = this.mCocktailBarStateListenerDelegates.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                CocktailBarStateListenerDelegate temp = it.next();
                if (temp != null && temp.getListener().equals(listener)) {
                    listenerDelegate = temp;
                    break;
                }
            }
            if (listenerDelegate == null) {
                SemLog.m486w(TAG, "unregisterListener : cannot find the listener");
                return;
            }
            try {
                SemLog.m482i(TAG, "unregisterListener : unregisterCocktailBarStateListenerCallback " + this.mContext.getPackageName());
                this.mService.unregisterCocktailBarStateListenerCallback(listenerDelegate);
                this.mCocktailBarStateListenerDelegates.remove(listenerDelegate);
                listenerDelegate.onDestroy();
            } catch (RemoteException e) {
                SemLog.m481e(TAG, "unregisterListener : RemoteException : ", e);
            }
        }
    }

    @Override // com.samsung.android.cocktailbar.SemCocktailBarManager
    public void registerStateListener(SemCocktailBarManager.CocktailBarStateChangedListener semStateChangedlistener) {
        if (getService() == null) {
            return;
        }
        if (semStateChangedlistener == null) {
            SemLog.m486w(TAG, "registerListener : listener is null");
            return;
        }
        synchronized (this.mStateListnerDelegatesLock) {
            CocktailBarStateListenerDelegate listenerDelegate = null;
            Iterator<CocktailBarStateListenerDelegate> it = this.mCocktailBarStateListenerDelegates.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                CocktailBarStateListenerDelegate temp = it.next();
                if (temp != null && (temp.getStateChangedListener() instanceof SemManagerStateChangedListenerWrapper) && semStateChangedlistener.equals(((SemManagerStateChangedListenerWrapper) temp.getStateChangedListener()).mSemlistener)) {
                    listenerDelegate = temp;
                    break;
                }
            }
            if (listenerDelegate == null) {
                SemManagerStateChangedListenerWrapper stateChangedlistener = new SemManagerStateChangedListenerWrapper(semStateChangedlistener);
                listenerDelegate = new CocktailBarStateListenerDelegate(stateChangedlistener, (Handler) null);
                this.mCocktailBarStateListenerDelegates.add(listenerDelegate);
            }
            ComponentName cm = new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName());
            try {
                SemLog.m482i(TAG, "registerListener : registerCocktailBarStateListenerCallback " + this.mContext.getPackageName());
                this.mService.registerCocktailBarStateListenerCallback(listenerDelegate, cm);
            } catch (RemoteException e) {
                SemLog.m481e(TAG, "registerListener : RemoteException : ", e);
            }
        }
    }

    @Override // com.samsung.android.cocktailbar.SemCocktailBarManager
    public void unregisterStateListener(SemCocktailBarManager.CocktailBarStateChangedListener stateChangedlistener) {
        if (getService() == null) {
            return;
        }
        if (stateChangedlistener == null) {
            SemLog.m486w(TAG, "unregisterListener : listener is null");
            return;
        }
        synchronized (this.mStateListnerDelegatesLock) {
            CocktailBarStateListenerDelegate listenerDelegate = null;
            Iterator<CocktailBarStateListenerDelegate> it = this.mCocktailBarStateListenerDelegates.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                CocktailBarStateListenerDelegate temp = it.next();
                if (temp != null && (temp.getStateChangedListener() instanceof SemManagerStateChangedListenerWrapper) && stateChangedlistener.equals(((SemManagerStateChangedListenerWrapper) temp.getStateChangedListener()).mSemlistener)) {
                    listenerDelegate = temp;
                    break;
                }
            }
            if (listenerDelegate == null) {
                SemLog.m486w(TAG, "unregisterListener : cannot find the listener");
                return;
            }
            try {
                SemLog.m482i(TAG, "unregisterListener : unregisterCocktailBarStateListenerCallback " + this.mContext.getPackageName());
                this.mService.unregisterCocktailBarStateListenerCallback(listenerDelegate);
                this.mCocktailBarStateListenerDelegates.remove(listenerDelegate);
                listenerDelegate.onDestroy();
            } catch (RemoteException e) {
                SemLog.m481e(TAG, "unregisterListener : RemoteException : ", e);
            }
        }
    }

    public void registerStateListener(CocktailBarStateChangedListener stateChangedlistener) {
        if (getService() == null) {
            return;
        }
        if (stateChangedlistener == null) {
            SemLog.m486w(TAG, "registerListener : listener is null");
            return;
        }
        synchronized (this.mStateListnerDelegatesLock) {
            CocktailBarStateListenerDelegate listenerDelegate = null;
            Iterator<CocktailBarStateListenerDelegate> it = this.mCocktailBarStateListenerDelegates.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                CocktailBarStateListenerDelegate temp = it.next();
                if (temp != null && temp.getStateChangedListener().equals(stateChangedlistener)) {
                    listenerDelegate = temp;
                    break;
                }
            }
            if (listenerDelegate == null) {
                listenerDelegate = new CocktailBarStateListenerDelegate(stateChangedlistener, (Handler) null);
                this.mCocktailBarStateListenerDelegates.add(listenerDelegate);
            }
            ComponentName cm = new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName());
            try {
                SemLog.m482i(TAG, "registerListener : registerCocktailBarStateListenerCallback " + this.mContext.getPackageName());
                this.mService.registerCocktailBarStateListenerCallback(listenerDelegate, cm);
            } catch (RemoteException e) {
                SemLog.m481e(TAG, "registerListener : RemoteException : ", e);
            }
        }
    }

    public void unregisterStateListener(CocktailBarStateChangedListener stateChangedlistener) {
        if (getService() == null) {
            return;
        }
        if (stateChangedlistener == null) {
            SemLog.m486w(TAG, "unregisterListener : listener is null");
            return;
        }
        synchronized (this.mStateListnerDelegatesLock) {
            CocktailBarStateListenerDelegate listenerDelegate = null;
            Iterator<CocktailBarStateListenerDelegate> it = this.mCocktailBarStateListenerDelegates.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                CocktailBarStateListenerDelegate temp = it.next();
                if (temp != null && temp.getStateChangedListener().equals(stateChangedlistener)) {
                    listenerDelegate = temp;
                    break;
                }
            }
            if (listenerDelegate == null) {
                SemLog.m486w(TAG, "unregisterListener : cannot find the listener");
                return;
            }
            try {
                SemLog.m482i(TAG, "unregisterListener : unregisterCocktailBarStateListenerCallback " + this.mContext.getPackageName());
                this.mService.unregisterCocktailBarStateListenerCallback(listenerDelegate);
                this.mCocktailBarStateListenerDelegates.remove(listenerDelegate);
                listenerDelegate.onDestroy();
            } catch (RemoteException e) {
                SemLog.m481e(TAG, "unregisterListener : RemoteException : ", e);
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
        public CocktailBarStateListenerDelegate(CocktailBarStateListener listener, Handler handler) {
            this.mListener = listener;
            this.mStateChangedListener = null;
            Looper looper = handler == null ? CocktailBarManager.this.mContext.getMainLooper() : handler.getLooper();
            this.mHandler = new Handler(looper) { // from class: com.samsung.android.cocktailbar.CocktailBarManager.CocktailBarStateListenerDelegate.1
                @Override // android.p009os.Handler
                public void handleMessage(Message msg) {
                    if (CocktailBarStateListenerDelegate.this.mListener == null || msg.what != 0) {
                        return;
                    }
                    CocktailBarStateInfo stateInfo = (CocktailBarStateInfo) msg.obj;
                    if (stateInfo.changeFlag == 0) {
                        return;
                    }
                    CocktailBarStateListenerDelegate.this.mListener.onCocktailBarStateChanged(stateInfo);
                    if ((stateInfo.changeFlag & 1) != 0) {
                        CocktailBarStateListenerDelegate.this.mListener.onCocktailBarVisibilityChanged(stateInfo.visibility);
                    }
                    if ((stateInfo.changeFlag & 4) != 0) {
                        CocktailBarStateListenerDelegate.this.mListener.onCocktailBarPositionChanged(stateInfo.position);
                    }
                    if ((stateInfo.changeFlag & 128) != 0) {
                        CocktailBarStateListenerDelegate.this.mListener.onCocktailBarWindowTypeChanged(stateInfo.windowType);
                    }
                }
            };
        }

        public CocktailBarStateListenerDelegate(CocktailBarStateChangedListener stateChangedListener, Handler handler) {
            this.mStateChangedListener = stateChangedListener;
            this.mListener = null;
            Looper looper = handler == null ? CocktailBarManager.this.mContext.getMainLooper() : handler.getLooper();
            this.mHandler = new Handler(looper) { // from class: com.samsung.android.cocktailbar.CocktailBarManager.CocktailBarStateListenerDelegate.2
                @Override // android.p009os.Handler
                public void handleMessage(Message msg) {
                    if (CocktailBarStateListenerDelegate.this.mStateChangedListener != null && msg.what == 0) {
                        CocktailBarStateInfo stateInfo = (CocktailBarStateInfo) msg.obj;
                        if (stateInfo.changeFlag != 0) {
                            CocktailBarStateListenerDelegate.this.mStateChangedListener.onCocktailBarStateChanged(stateInfo);
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
        public void onCocktailBarStateChanged(CocktailBarStateInfo stateInfo) throws RemoteException {
            Message.obtain(this.mHandler, 0, stateInfo).sendToTarget();
        }

        public void onDestroy() {
            this.mHandler = null;
            this.mListener = null;
            this.mStateChangedListener = null;
        }
    }

    public static class SemManagerStateChangedListenerWrapper implements CocktailBarStateChangedListener {
        public final SemCocktailBarManager.CocktailBarStateChangedListener mSemlistener;

        public SemManagerStateChangedListenerWrapper(SemCocktailBarManager.CocktailBarStateChangedListener listener) {
            this.mSemlistener = listener;
        }

        @Override // com.samsung.android.cocktailbar.CocktailBarManager.CocktailBarStateChangedListener
        public void onCocktailBarStateChanged(CocktailBarStateInfo stateInfo) {
            SemCocktailBarStateInfo semStateInfo = new SemCocktailBarStateInfo();
            semStateInfo.position = stateInfo.position;
            semStateInfo.visibility = stateInfo.visibility;
            semStateInfo.windowType = stateInfo.windowType;
            this.mSemlistener.onCocktailBarStateChanged(semStateInfo);
        }
    }

    @Deprecated
    public static class CocktailBarStateListener {
        public void onCocktailBarStateChanged(CocktailBarStateInfo stateInfo) {
        }

        public void onCocktailBarVisibilityChanged(int visibility) {
        }

        public void onCocktailBarPositionChanged(int position) {
        }

        public void onCocktailBarWindowTypeChanged(int windowType) {
        }
    }

    public void registerSystemUiVisibilityListener(SystemUiVisibilityListener systemUiVisibilitylistener) {
        if (getService() == null) {
            return;
        }
        if (systemUiVisibilitylistener == null) {
            SemLog.m486w(TAG, "registerListener : listener is null");
            return;
        }
        synchronized (this.mSystemUiVisibilityListenerDelegatesLock) {
            SystemUiVisibilityListenerDelegate listenerDelegate = null;
            Iterator<SystemUiVisibilityListenerDelegate> it = this.mSystemUiVisibilityListenerDelegates.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                SystemUiVisibilityListenerDelegate temp = it.next();
                if (temp != null && temp.getListener().equals(systemUiVisibilitylistener)) {
                    listenerDelegate = temp;
                    break;
                }
            }
            if (listenerDelegate == null) {
                listenerDelegate = new SystemUiVisibilityListenerDelegate(systemUiVisibilitylistener, null);
                this.mSystemUiVisibilityListenerDelegates.add(listenerDelegate);
            }
            ComponentName cm = new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName());
            try {
                SemLog.m482i(TAG, "registerListener : registerSystemUiVisibilityListenerCallback " + this.mContext.getPackageName());
                this.mService.registerSystemUiVisibilityListenerCallback(listenerDelegate, cm);
            } catch (RemoteException e) {
                SemLog.m481e(TAG, "registerListener : RemoteException : ", e);
            }
        }
    }

    public void unregisterSystemUiVisibilityListener(SystemUiVisibilityListener systemUiVisibilitylistener) {
        if (getService() == null) {
            return;
        }
        if (systemUiVisibilitylistener == null) {
            SemLog.m486w(TAG, "unregisterListener : listener is null");
            return;
        }
        synchronized (this.mSystemUiVisibilityListenerDelegatesLock) {
            SystemUiVisibilityListenerDelegate listenerDelegate = null;
            Iterator<SystemUiVisibilityListenerDelegate> it = this.mSystemUiVisibilityListenerDelegates.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                SystemUiVisibilityListenerDelegate temp = it.next();
                if (temp != null && temp.getListener().equals(systemUiVisibilitylistener)) {
                    listenerDelegate = temp;
                    break;
                }
            }
            if (listenerDelegate == null) {
                SemLog.m486w(TAG, "unregisterListener : cannot find the listener");
                return;
            }
            try {
                SemLog.m482i(TAG, "unregisterListener : unregisterSystemUiVisibilityListenerCallback " + this.mContext.getPackageName());
                this.mService.unregisterSystemUiVisibilityListenerCallback(listenerDelegate);
                this.mSystemUiVisibilityListenerDelegates.remove(listenerDelegate);
                listenerDelegate.onDestroy();
            } catch (RemoteException e) {
                SemLog.m481e(TAG, "unregisterListener : RemoteException : ", e);
            }
        }
    }

    private class SystemUiVisibilityListenerDelegate extends ISystemUiVisibilityCallback.Stub {
        private static final int MSG_SYSTEM_UI_VISIBILITY_CHANGED = 1;
        private Handler mHandler;
        private SystemUiVisibilityListener mListener;

        public SystemUiVisibilityListenerDelegate(SystemUiVisibilityListener listener, Handler handler) {
            this.mListener = listener;
            Looper looper = handler == null ? CocktailBarManager.this.mContext.getMainLooper() : handler.getLooper();
            this.mHandler = new Handler(looper) { // from class: com.samsung.android.cocktailbar.CocktailBarManager.SystemUiVisibilityListenerDelegate.1
                @Override // android.p009os.Handler
                public void handleMessage(Message msg) {
                    if (SystemUiVisibilityListenerDelegate.this.mListener != null && msg.what == 1) {
                        SystemUiVisibilityListenerDelegate.this.mListener.onSystemUiVisibilityChanged(msg.arg1);
                    }
                }
            };
        }

        public SystemUiVisibilityListener getListener() {
            return this.mListener;
        }

        @Override // com.samsung.android.cocktailbar.ISystemUiVisibilityCallback
        public void onSystemUiVisibilityChanged(int visibility) throws RemoteException {
            Message.obtain(this.mHandler, 1, visibility, 0).sendToTarget();
        }

        public void onDestroy() {
            this.mHandler = null;
            this.mListener = null;
        }
    }

    public static class SystemUiVisibilityListener {
        public void onSystemUiVisibilityChanged(int visibility) {
        }
    }

    @Deprecated
    public void registerOnFeedsUpdatedListener(CocktailBarFeedsListener listener) {
        if (getService() == null) {
            return;
        }
        if (listener == null) {
            SemLog.m486w(TAG, "registerOnFeedsUpdatedListener : listener is null");
            return;
        }
        throw new RuntimeException("registerOnFeedsUpdatedListener not supported.");
    }

    @Deprecated
    public void unregisterOnFeedsUpdatedListener(CocktailBarFeedsListener listener) {
        if (getService() == null) {
            return;
        }
        if (listener == null) {
            SemLog.m486w(TAG, "unregisterOnFeedsUpdatedListener : listener is null");
            return;
        }
        throw new RuntimeException("unregisterOnFeedsUpdatedListener not supported.");
    }

    @Deprecated
    public static class CocktailBarFeedsListener {
        @Deprecated
        public CocktailBarFeedsListener() {
        }

        @Deprecated
        public void onFeedsUpdated(int cocktailId, List<FeedsInfo> feedsInfoList) {
        }
    }

    public int getConfigVersion() {
        if (getService() == null) {
            Log.m94d(TAG, "getConfigVersion getService is null");
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
            Log.m94d(TAG, "getPreferWidth getService is null");
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
            Log.m94d(TAG, "getCategoryFilterStr getService is null");
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
            Log.m94d(TAG, "getHideEdgeListStr getService is null");
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
