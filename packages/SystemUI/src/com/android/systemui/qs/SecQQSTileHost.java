package com.android.systemui.qs;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.BootAnimationFinishedCache;
import com.android.systemui.BootAnimationFinishedCacheImpl;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.Executor;

public final class SecQQSTileHost implements TunerService.Tunable, QSHost.Callback {
    public static final boolean DEBUG = Log.isLoggable("SecQQSTileHost", 3);
    public static final boolean LOGGING_DEBUG = Log.isLoggable(SystemUIAnalytics.TAG_QUICK_SETTINGS, 3);
    public String mBnRQQSTileList;
    public final Context mContext;
    public int mCurrentUser;
    public final SharedPreferences.Editor mEditor;
    public final QSLogger mQSLogger;
    public final QSTileHost mQSTileHost;
    public final SecQSTileInstanceManager mQSTileInstanceManager;
    public final UserTracker mUserTracker;
    public final ArrayList mTileSpecs = new ArrayList();
    public final LinkedHashMap mTiles = new LinkedHashMap();
    public final List mCallbacks = new ArrayList();
    public final Object mTileUsingByQQS = new Object();
    public boolean mQSUserChanged = false;

    public SecQQSTileHost(Context context, QSTileHost qSTileHost, UserTracker userTracker, BootAnimationFinishedCache bootAnimationFinishedCache, final Executor executor, QSLogger qSLogger) {
        this.mContext = context;
        this.mQSTileHost = qSTileHost;
        this.mUserTracker = userTracker;
        this.mQSLogger = qSLogger;
        this.mQSTileInstanceManager = qSTileHost.mQSTileInstanceManager;
        if (!Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            Log.e("SecQQSTileHost", "OPS not initialized for non-primary user, just return");
            return;
        }
        ((BootAnimationFinishedCacheImpl) bootAnimationFinishedCache).addListener(new BootAnimationFinishedCache.BootAnimationFinishedListener() { // from class: com.android.systemui.qs.SecQQSTileHost$$ExternalSyntheticLambda0
            @Override // com.android.systemui.BootAnimationFinishedCache.BootAnimationFinishedListener
            public final void onBootAnimationFinished() {
                Executor executor2 = executor;
                SecQQSTileHost secQQSTileHost = SecQQSTileHost.this;
                secQQSTileHost.getClass();
                executor2.execute(new SecQQSTileHost$$ExternalSyntheticLambda1(secQQSTileHost, 0));
            }
        });
        this.mEditor = context.getSharedPreferences(SystemUIAnalytics.QUICK_PREF_NAME, 0).edit();
    }

    public final void changeTiles(List list) {
        Log.d("SecQQSTileHost", "changeTiles " + list);
        Settings.Secure.putStringForUser(this.mContext.getContentResolver(), "sysui_quick_qs_tiles", TextUtils.join(",", list), ((UserTrackerImpl) this.mUserTracker).getUserId());
    }

    public final ArrayList filterAvailableTopTiles(String str) {
        String customTileNameFromSpec;
        ArrayList arrayList = new ArrayList();
        int qsTileMinNum = ((SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class)).resourcePickHelper.getTargetPicker().getQsTileMinNum(this.mContext);
        for (String str2 : str.split(",")) {
            String trim = str2.trim();
            if (!trim.isEmpty()) {
                QSTileHost qSTileHost = this.mQSTileHost;
                if (qSTileHost.isSystemTile(trim)) {
                    QSTile createTile = qSTileHost.createTile(trim);
                    if (createTile == null) {
                        continue;
                    } else {
                        createTile.setTileSpec(trim);
                        if (createTile.isAvailable()) {
                            arrayList.add(trim);
                        } else {
                            createTile.destroy();
                        }
                    }
                } else {
                    if (trim.startsWith("custom(") && (customTileNameFromSpec = qSTileHost.getCustomTileNameFromSpec(trim)) != null) {
                        trim = customTileNameFromSpec;
                    }
                    if (qSTileHost.isAvailableCustomTile(trim)) {
                        String customTileSpecFromTileName = qSTileHost.getCustomTileSpecFromTileName(trim);
                        if (customTileSpecFromTileName != null) {
                            SecAutoTileManager secAutoTileManager = qSTileHost.mAutoTiles;
                            if (secAutoTileManager == null || !secAutoTileManager.isRemovedTileByAppIntent(customTileSpecFromTileName)) {
                                arrayList.add(customTileSpecFromTileName);
                            }
                        } else {
                            arrayList.add(trim);
                        }
                    }
                }
                if (arrayList.size() == qsTileMinNum) {
                    break;
                }
            }
        }
        return arrayList;
    }

    public final String getQQSDefaultTileList() {
        ArrayList filterAvailableTopTiles = filterAvailableTopTiles(this.mQSTileHost.getSupportedAllTileList());
        Log.d("SecQQSTileHost", "getQQSDefaultTileList result : " + filterAvailableTopTiles);
        return TextUtils.join(",", filterAvailableTopTiles);
    }

    public final List loadTileSpecs(Context context, String str) {
        ArrayList arrayList = new ArrayList();
        ArraySet arraySet = new ArraySet();
        int i = 0;
        if (str == null || str.isEmpty()) {
            arrayList = new ArrayList();
            ContentResolver contentResolver = this.mContext.getContentResolver();
            UserTrackerImpl userTrackerImpl = (UserTrackerImpl) this.mUserTracker;
            String stringForUser = Settings.Secure.getStringForUser(contentResolver, "sysui_quick_qs_tiles", userTrackerImpl.getUserId());
            if (stringForUser == null || stringForUser.isEmpty()) {
                String[] split = getQQSDefaultTileList().split(",");
                int length = split.length;
                while (i < length) {
                    arrayList.add(split[i]);
                    i++;
                }
                Log.d("SecQQSTileHost", "loadQQSTileSpecs mUserTracker.getUserId():" + userTrackerImpl.getUserId());
                Log.d("SecQQSTileHost", "loadQQSTileSpecs qqsTiles : " + arrayList);
                Settings.Secure.putStringForUser(this.mContext.getContentResolver(), "sysui_quick_qs_tiles", TextUtils.join(",", arrayList), userTrackerImpl.getUserId());
            } else {
                Arrays.stream(stringForUser.split(",")).forEach(new SecQQSTileHost$$ExternalSyntheticLambda5(arrayList, 2));
                Log.d("SecQQSTileHost", "loadQQSTileSpecs subTile list is already set as " + arrayList);
            }
        } else {
            String[] split2 = str.split(",");
            int length2 = split2.length;
            while (i < length2) {
                String trim = split2[i].trim();
                if (!trim.isEmpty() && !arraySet.contains(trim)) {
                    arrayList.add(trim);
                    arraySet.add(trim);
                }
                i++;
            }
        }
        return arrayList;
    }

    @Override // com.android.systemui.qs.QSHost.Callback
    public final void onTilesChanged() {
        for (int i = 0; i < ((ArrayList) this.mCallbacks).size(); i++) {
            ((QSHost.Callback) ((ArrayList) this.mCallbacks).get(i)).onTilesChanged();
        }
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
        boolean z;
        if (!"sysui_quick_qs_tiles".equals(str)) {
            return;
        }
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Recreating QQS tiles  ", str2, "SecQQSTileHost");
        List<String> loadTileSpecs = loadTileSpecs(this.mContext, str2);
        Log.d("SecQQSTileHost", "loaded tiles :" + loadTileSpecs);
        int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
        if (loadTileSpecs.equals(this.mTileSpecs) && userId == this.mCurrentUser) {
            return;
        }
        if (userId != this.mCurrentUser && !this.mQSUserChanged) {
            Log.w("SecQQSTileHost", "Delay recreating tiles until QS userContext change is completed");
            return;
        }
        int i = 0;
        this.mQSUserChanged = false;
        this.mTiles.entrySet().stream().filter(new SecQQSTileHost$$ExternalSyntheticLambda4(loadTileSpecs, 0)).forEach(new SecQQSTileHost$$ExternalSyntheticLambda5(this, 0));
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (String str3 : loadTileSpecs) {
            QSTile qSTile = (QSTile) this.mTiles.get(str3);
            Object obj = this.mTileUsingByQQS;
            SecQSTileInstanceManager secQSTileInstanceManager = this.mQSTileInstanceManager;
            QSLogger qSLogger = this.mQSLogger;
            if (qSTile == null || (((z = qSTile instanceof CustomTile)) && ((CustomTile) qSTile).mUser != userId)) {
                if (qSTile != null) {
                    secQSTileInstanceManager.releaseTileUsing(obj, qSTile.getTileSpec());
                    qSLogger.logTileDestroyed(str3, "Tile for wrong user at QQS");
                    Log.d("SecQQSTileHost", "Destroying tile for wrong user: ".concat(str3));
                }
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Creating tile: ", str3, "SecQQSTileHost");
                try {
                    QSTile requestTileUsing = secQSTileInstanceManager.requestTileUsing(obj, str3);
                    if (requestTileUsing != null) {
                        linkedHashMap.put(str3, requestTileUsing);
                    }
                } catch (Throwable th) {
                    Log.w("SecQQSTileHost", "Error creating tile for spec: " + str3, th);
                }
            } else if (qSTile.isAvailable()) {
                if (DEBUG) {
                    Log.d("SecQQSTileHost", "Adding " + qSTile);
                }
                qSTile.removeCallbacks();
                if (!z && this.mCurrentUser != userId) {
                    qSTile.userSwitch(userId);
                }
                linkedHashMap.put(str3, qSTile);
            } else {
                secQSTileInstanceManager.releaseTileUsing(obj, qSTile.getTileSpec());
                qSLogger.logTileDestroyed(str3, "Tile not available at QQS");
                Log.d("SecQQSTileHost", "Destroying not available tile: ".concat(str3));
            }
        }
        this.mCurrentUser = userId;
        this.mTileSpecs.clear();
        this.mTileSpecs.addAll(loadTileSpecs);
        this.mTiles.clear();
        this.mTiles.putAll(linkedHashMap);
        onTilesChanged();
        while (true) {
            QSTileHost qSTileHost = this.mQSTileHost;
            if (i >= ((ArrayList) qSTileHost.mCallbacks).size()) {
                new Handler((Looper) Dependency.sDependency.getDependencyInner(Dependency.BG_LOOPER)).post(new SecQQSTileHost$$ExternalSyntheticLambda1(this, 1));
                return;
            } else {
                ((QSHost.Callback) ((ArrayList) qSTileHost.mCallbacks).get(i)).onTilesChanged();
                i++;
            }
        }
    }

    public final void restoreQQSTileListIfNeeded(String str) {
        String stringForUser;
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("restoreQQSTileListIfNeeded : qsTileList = ", str, "SecQQSTileHost");
        QSTileHost qSTileHost = this.mQSTileHost;
        UserTracker userTracker = this.mUserTracker;
        if (str != null) {
            ArrayList filterAvailableTopTiles = filterAvailableTopTiles(qSTileHost.changeOldOSTileListToNewOsTileList(str));
            Log.d("SecQQSTileHost", "restoreQQSTileListIfNeeded : tilesList = " + filterAvailableTopTiles);
            Settings.Secure.putStringForUser(this.mContext.getContentResolver(), "sysui_quick_qs_tiles", TextUtils.join(",", filterAvailableTopTiles), ((UserTrackerImpl) userTracker).getUserId());
            return;
        }
        String stringForUser2 = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "sysui_quick_qs_tiles", ((UserTrackerImpl) userTracker).getUserId());
        if ((stringForUser2 != null && !stringForUser2.isEmpty()) || (stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "sysui_qs_tiles", ((UserTrackerImpl) userTracker).getUserId())) == null || stringForUser.isEmpty()) {
            return;
        }
        String changeOldOSTileListToNewOsTileList = qSTileHost.changeOldOSTileListToNewOsTileList(stringForUser);
        Log.d("SecQQSTileHost", "restoreQQSTileList from QS " + changeOldOSTileListToNewOsTileList);
        Settings.Secure.putStringForUser(this.mContext.getContentResolver(), "sysui_quick_qs_tiles", TextUtils.join(",", filterAvailableTopTiles(changeOldOSTileListToNewOsTileList)), ((UserTrackerImpl) userTracker).getUserId());
        qSTileHost.mIsQQSosUpdating = true;
    }

    public final void setRestoreData(String str, String str2) {
        if (str == null || str2 == null) {
            Log.w("SecQQSTileHost", "tag or restoreData is null");
            return;
        }
        if (str.equals("qqs_has_edited")) {
            Prefs.putBoolean(this.mContext, "QQsHasEditedQuickTileList", Boolean.valueOf(str2).booleanValue());
            Log.d("SecQQSTileHost", "setRestoreData  hasEdited=".concat(str2));
            return;
        }
        this.mBnRQQSTileList = str2;
        String str3 = "";
        for (String str4 : str2.split(",")) {
            if (str4.startsWith("custom(")) {
                QSTileHost qSTileHost = this.mQSTileHost;
                String customTileNameFromSpec = qSTileHost.getCustomTileNameFromSpec(str4);
                if ((customTileNameFromSpec == null || qSTileHost.isAvailableCustomTile(customTileNameFromSpec)) && qSTileHost.isComponentAvailable(CustomTile.getComponentFromSpec(str4))) {
                    str3 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str3, str4, ",");
                }
            } else {
                str3 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str3, str4, ",");
            }
        }
        if (DEBUG) {
            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("setRestoreData  QQS_TILES_SETTING=", str3, "SecQQSTileHost");
        }
        Settings.Secure.putStringForUser(this.mContext.getContentResolver(), "sysui_quick_qs_tiles", str3, ((UserTrackerImpl) this.mUserTracker).getUserId());
    }
}
