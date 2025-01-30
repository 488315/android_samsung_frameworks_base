package com.android.systemui.qs;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.BootAnimationFinishedCache;
import com.android.systemui.BootAnimationFinishedCacheImpl;
import com.android.systemui.Dependency;
import com.android.systemui.Prefs;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.tuner.TunerService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecQQSTileHost implements TunerService.Tunable, QSHost.Callback {
    public static final boolean DEBUG = Log.isLoggable("SecQQSTileHost", 3);
    public static final boolean LOGGING_DEBUG = Log.isLoggable("SA_QUICK_SETTINGS", 3);
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

    public SecQQSTileHost(Context context, QSTileHost qSTileHost, UserTracker userTracker, BootAnimationFinishedCache bootAnimationFinishedCache, QSLogger qSLogger) {
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
                SecQQSTileHost secQQSTileHost = SecQQSTileHost.this;
                secQQSTileHost.getClass();
                ((Executor) Dependency.get(Dependency.MAIN_EXECUTOR)).execute(new SecQQSTileHost$$ExternalSyntheticLambda3(secQQSTileHost, 0));
            }
        });
        this.mEditor = context.getSharedPreferences("quick_pref", 0).edit();
    }

    public final void changeTiles(List list) {
        Log.d("SecQQSTileHost", "changeTiles " + list);
        Settings.Secure.putStringForUser(this.mContext.getContentResolver(), "sysui_quick_qs_tiles", TextUtils.join(",", list), ((UserTrackerImpl) this.mUserTracker).getUserId());
    }

    public final ArrayList filterAvailableTopTiles(String str) {
        String customTileNameFromSpec;
        ArrayList arrayList = new ArrayList();
        ((SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class)).getClass();
        int qsTileMinNum = SecQSPanelResourcePicker.getQsTileMinNum(this.mContext);
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
                            if (!(secAutoTileManager != null && secAutoTileManager.isRemovedTileByAppIntent(customTileSpecFromTileName))) {
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
            Context context2 = this.mContext;
            ContentResolver contentResolver = context2.getContentResolver();
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
                Settings.Secure.putStringForUser(context2.getContentResolver(), "sysui_quick_qs_tiles", TextUtils.join(",", arrayList), userTrackerImpl.getUserId());
            } else {
                Arrays.stream(stringForUser.split(",")).forEach(new SecQQSTileHost$$ExternalSyntheticLambda2(arrayList, 2));
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
        int i = 0;
        while (true) {
            List list = this.mCallbacks;
            if (i >= list.size()) {
                return;
            }
            ((QSHost.Callback) list.get(i)).onTilesChanged();
            i++;
        }
    }

    @Override // com.android.systemui.tuner.TunerService.Tunable
    public final void onTuningChanged(String str, String str2) {
        boolean z;
        if (!"sysui_quick_qs_tiles".equals(str)) {
            return;
        }
        AbstractC0000x2c234b15.m3m("Recreating QQS tiles  ", str2, "SecQQSTileHost");
        List<String> loadTileSpecs = loadTileSpecs(this.mContext, str2);
        Log.d("SecQQSTileHost", "loaded tiles :" + loadTileSpecs);
        int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
        ArrayList arrayList = this.mTileSpecs;
        if (loadTileSpecs.equals(arrayList) && userId == this.mCurrentUser) {
            return;
        }
        if (userId != this.mCurrentUser && !this.mQSUserChanged) {
            Log.w("SecQQSTileHost", "Delay recreating tiles until QS userContext change is completed");
            return;
        }
        int i = 0;
        this.mQSUserChanged = false;
        LinkedHashMap linkedHashMap = this.mTiles;
        linkedHashMap.entrySet().stream().filter(new SecQQSTileHost$$ExternalSyntheticLambda1(loadTileSpecs, 0)).forEach(new SecQQSTileHost$$ExternalSyntheticLambda2(this, i));
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (String str3 : loadTileSpecs) {
            QSTile qSTile = (QSTile) linkedHashMap.get(str3);
            Object obj = this.mTileUsingByQQS;
            QSLogger qSLogger = this.mQSLogger;
            SecQSTileInstanceManager secQSTileInstanceManager = this.mQSTileInstanceManager;
            if (qSTile == null || (((z = qSTile instanceof CustomTile)) && ((CustomTile) qSTile).mUser != userId)) {
                if (qSTile != null) {
                    secQSTileInstanceManager.releaseTileUsing(obj, qSTile.getTileSpec());
                    qSLogger.logTileDestroyed(str3, "Tile for wrong user at QQS");
                    Log.d("SecQQSTileHost", "Destroying tile for wrong user: ".concat(str3));
                }
                AbstractC0000x2c234b15.m3m("Creating tile: ", str3, "SecQQSTileHost");
                try {
                    QSTile requestTileUsing = secQSTileInstanceManager.requestTileUsing(obj, str3);
                    if (requestTileUsing != null) {
                        linkedHashMap2.put(str3, requestTileUsing);
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
                linkedHashMap2.put(str3, qSTile);
            } else {
                secQSTileInstanceManager.releaseTileUsing(obj, qSTile.getTileSpec());
                qSLogger.logTileDestroyed(str3, "Tile not available at QQS");
                Log.d("SecQQSTileHost", "Destroying not available tile: ".concat(str3));
            }
        }
        this.mCurrentUser = userId;
        arrayList.clear();
        arrayList.addAll(loadTileSpecs);
        linkedHashMap.clear();
        linkedHashMap.putAll(linkedHashMap2);
        onTilesChanged();
        while (true) {
            ArrayList arrayList2 = (ArrayList) this.mQSTileHost.mCallbacks;
            if (i >= arrayList2.size()) {
                new Handler((Looper) Dependency.get(Dependency.BG_LOOPER)).post(new SecQQSTileHost$$ExternalSyntheticLambda3(this, 1));
                return;
            } else {
                ((QSHost.Callback) arrayList2.get(i)).onTilesChanged();
                i++;
            }
        }
    }

    public final void refreshTileList() {
        boolean z = DEBUG;
        if (z) {
            Log.d("SecQQSTileHost", "refreshTileList");
        }
        Context context = this.mContext;
        ContentResolver contentResolver = context.getContentResolver();
        UserTracker userTracker = this.mUserTracker;
        List<String> loadTileSpecs = loadTileSpecs(context, Settings.Secure.getStringForUser(contentResolver, "sysui_quick_qs_tiles", ((UserTrackerImpl) userTracker).getUserId()));
        Log.d("SecQQSTileHost", "loaded tiles :" + loadTileSpecs);
        int userId = ((UserTrackerImpl) userTracker).getUserId();
        LinkedHashMap linkedHashMap = this.mTiles;
        linkedHashMap.entrySet().stream().filter(new SecQQSTileHost$$ExternalSyntheticLambda1(loadTileSpecs, 1)).forEach(new SecQQSTileHost$$ExternalSyntheticLambda2(this, 1));
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (String str : loadTileSpecs) {
            QSTile qSTile = (QSTile) linkedHashMap.get(str);
            Object obj = this.mTileUsingByQQS;
            SecQSTileInstanceManager secQSTileInstanceManager = this.mQSTileInstanceManager;
            if (qSTile == null || ((qSTile instanceof CustomTile) && ((CustomTile) qSTile).mUser != userId)) {
                AbstractC0000x2c234b15.m3m("Creating tile: ", str, "SecQQSTileHost");
                try {
                    QSTile requestTileUsing = secQSTileInstanceManager.requestTileUsing(obj, str);
                    if (requestTileUsing != null) {
                        requestTileUsing.setTileSpec(str);
                        if (requestTileUsing.isAvailable()) {
                            linkedHashMap2.put(str, requestTileUsing);
                        } else {
                            secQSTileInstanceManager.releaseTileUsing(obj, requestTileUsing.getTileSpec());
                        }
                    }
                } catch (Throwable th) {
                    Log.w("SecQQSTileHost", "Error creating tile for spec: " + str, th);
                }
            } else if (qSTile.isAvailable()) {
                if (z) {
                    Log.d("SecQQSTileHost", "Adding " + qSTile);
                }
                qSTile.removeCallbacks();
                linkedHashMap2.put(str, qSTile);
            } else {
                secQSTileInstanceManager.releaseTileUsing(obj, qSTile.getTileSpec());
                this.mQSLogger.logTileDestroyed(str, "Tile not available at QQS");
                Log.d("SecQQSTileHost", "Destroying not available tile: ".concat(str));
            }
        }
        this.mCurrentUser = userId;
        ArrayList arrayList = this.mTileSpecs;
        arrayList.clear();
        arrayList.addAll(loadTileSpecs);
        linkedHashMap.clear();
        linkedHashMap.putAll(linkedHashMap2);
        onTilesChanged();
    }

    public final void removeTile(String str) {
        Context context = this.mContext;
        List loadTileSpecs = loadTileSpecs(context, Settings.Secure.getStringForUser(context.getContentResolver(), "sysui_quick_qs_tiles", ((UserTrackerImpl) this.mUserTracker).getUserId()));
        if (loadTileSpecs.remove(str)) {
            changeTiles(loadTileSpecs);
        }
    }

    public final void restoreQQSTileListIfNeeded(String str) {
        String stringForUser;
        AbstractC0000x2c234b15.m3m("restoreQQSTileListIfNeeded : qsTileList = ", str, "SecQQSTileHost");
        QSTileHost qSTileHost = this.mQSTileHost;
        UserTracker userTracker = this.mUserTracker;
        Context context = this.mContext;
        if (str != null) {
            ArrayList filterAvailableTopTiles = filterAvailableTopTiles(qSTileHost.changeOldOSTileListToNewOsTileList(str));
            Log.d("SecQQSTileHost", "restoreQQSTileListIfNeeded : tilesList = " + filterAvailableTopTiles);
            Settings.Secure.putStringForUser(context.getContentResolver(), "sysui_quick_qs_tiles", TextUtils.join(",", filterAvailableTopTiles), ((UserTrackerImpl) userTracker).getUserId());
            return;
        }
        String stringForUser2 = Settings.Secure.getStringForUser(context.getContentResolver(), "sysui_quick_qs_tiles", ((UserTrackerImpl) userTracker).getUserId());
        if ((stringForUser2 != null && !stringForUser2.isEmpty()) || (stringForUser = Settings.Secure.getStringForUser(context.getContentResolver(), "sysui_qs_tiles", ((UserTrackerImpl) userTracker).getUserId())) == null || stringForUser.isEmpty()) {
            return;
        }
        String changeOldOSTileListToNewOsTileList = qSTileHost.changeOldOSTileListToNewOsTileList(stringForUser);
        Log.d("SecQQSTileHost", "restoreQQSTileList from QS " + changeOldOSTileListToNewOsTileList);
        Settings.Secure.putStringForUser(context.getContentResolver(), "sysui_quick_qs_tiles", TextUtils.join(",", filterAvailableTopTiles(changeOldOSTileListToNewOsTileList)), ((UserTrackerImpl) userTracker).getUserId());
        qSTileHost.mIsQQSosUpdating = true;
    }

    public final void setRestoreData(String str, String str2) {
        if (str == null || str2 == null) {
            Log.w("SecQQSTileHost", "tag or restoreData is null");
            return;
        }
        boolean equals = str.equals("qqs_has_edited");
        Context context = this.mContext;
        if (equals) {
            Prefs.putBoolean(context, "QQsHasEditedQuickTileList", Boolean.valueOf(str2).booleanValue());
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
                    str3 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(str3, str4, ",");
                }
            } else {
                str3 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(str3, str4, ",");
            }
        }
        if (DEBUG) {
            AbstractC0000x2c234b15.m3m("setRestoreData  QQS_TILES_SETTING=", str3, "SecQQSTileHost");
        }
        Settings.Secure.putStringForUser(context.getContentResolver(), "sysui_quick_qs_tiles", str3, ((UserTrackerImpl) this.mUserTracker).getUserId());
    }
}
