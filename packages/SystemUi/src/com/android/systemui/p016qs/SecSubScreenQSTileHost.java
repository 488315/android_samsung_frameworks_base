package com.android.systemui.p016qs;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Process;
import android.os.UserHandle;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import com.android.systemui.BootAnimationFinishedCache;
import com.android.systemui.BootAnimationFinishedCacheImpl;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.p016qs.QSHost;
import com.android.systemui.p016qs.external.CustomTile;
import com.android.systemui.p016qs.logging.QSLogger;
import com.android.systemui.plugins.p013qs.QSTile;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.tuner.TunerService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecSubScreenQSTileHost implements TunerService.Tunable, QSHost.Callback {
    public static final boolean DEBUG = Log.isLoggable("SecSubScreenQSTileHost", 3);
    public final Context mContext;
    public int mCurrentUser;
    public final QSLogger mQSLogger;
    public final QSTileHost mQSTileHost;
    public final SecQSTileInstanceManager mQSTileInstanceManager;
    public final UserTracker mUserTracker;
    public final ArrayList mTileSpecs = new ArrayList();
    public final LinkedHashMap mTiles = new LinkedHashMap();
    public final List mCallbacks = new ArrayList();
    public final Object mTileUsingBySubScreen = new Object();
    public boolean mQSUserChanged = false;

    public SecSubScreenQSTileHost(Context context, QSTileHost qSTileHost, UserTracker userTracker, BootAnimationFinishedCache bootAnimationFinishedCache, QSLogger qSLogger) {
        this.mContext = context;
        this.mQSTileHost = qSTileHost;
        this.mUserTracker = userTracker;
        this.mQSLogger = qSLogger;
        this.mQSTileInstanceManager = qSTileHost.mQSTileInstanceManager;
        if (!Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            Log.e("SecSubScreenQSTileHost", "OPS not initialized for non-primary user, just return");
        } else {
            ((BootAnimationFinishedCacheImpl) bootAnimationFinishedCache).addListener(new BootAnimationFinishedCache.BootAnimationFinishedListener() { // from class: com.android.systemui.qs.SecSubScreenQSTileHost$$ExternalSyntheticLambda2
                @Override // com.android.systemui.BootAnimationFinishedCache.BootAnimationFinishedListener
                public final void onBootAnimationFinished() {
                    final SecSubScreenQSTileHost secSubScreenQSTileHost = SecSubScreenQSTileHost.this;
                    secSubScreenQSTileHost.getClass();
                    ((Executor) Dependency.get(Dependency.MAIN_EXECUTOR)).execute(new Runnable() { // from class: com.android.systemui.qs.SecSubScreenQSTileHost$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            SecSubScreenQSTileHost secSubScreenQSTileHost2 = SecSubScreenQSTileHost.this;
                            secSubScreenQSTileHost2.getClass();
                            Log.d("SecSubScreenQSTileHost", "start addTunable");
                            ((TunerService) Dependency.get(TunerService.class)).addTunable(secSubScreenQSTileHost2, "sysui_sub_qs_tiles");
                        }
                    });
                }
            });
        }
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
            String stringForUser = Settings.Secure.getStringForUser(contentResolver, "sysui_sub_qs_tiles", userTrackerImpl.getUserId());
            if (stringForUser == null || stringForUser.isEmpty()) {
                String string = context2.getResources().getString(R.string.sec_sub_quick_settings_tiles_default);
                QSTileHost qSTileHost = this.mQSTileHost;
                String supportedAllTileList = qSTileHost.getSupportedAllTileList();
                ArrayList arrayList2 = new ArrayList();
                for (String str2 : string.split(",")) {
                    String trim = str2.trim();
                    if (!trim.isEmpty() && supportedAllTileList.contains(trim)) {
                        arrayList2.add(trim);
                    }
                }
                Log.d("SecSubScreenQSTileHost", "getSubScreenDefaultTileList result : " + arrayList2);
                String[] split = TextUtils.join(",", arrayList2).split(",");
                int length = split.length;
                while (i < length) {
                    String str3 = split[i];
                    if (!qSTileHost.isSystemTile(str3)) {
                        str3 = qSTileHost.getCustomTileSpecFromTileName(str3);
                    }
                    if (str3 != null) {
                        arrayList.add(str3);
                    }
                    i++;
                }
                Log.d("SecSubScreenQSTileHost", "loadSubScreenTileSpecs mUserTracker.getUserId():" + userTrackerImpl.getUserId());
                Log.d("SecSubScreenQSTileHost", "loadSubScreenTileSpecs subScreenTiles : " + arrayList);
                Settings.Secure.putStringForUser(context2.getContentResolver(), "sysui_sub_qs_tiles", TextUtils.join(",", arrayList), userTrackerImpl.getUserId());
            } else {
                Arrays.stream(stringForUser.split(",")).forEach(new SecSubScreenQSTileHost$$ExternalSyntheticLambda1(arrayList, 1));
                Log.d("SecSubScreenQSTileHost", "loadSubScreenTileSpecs subTile list is already set as " + arrayList);
            }
        } else {
            String[] split2 = str.split(",");
            int length2 = split2.length;
            while (i < length2) {
                String trim2 = split2[i].trim();
                if (!trim2.isEmpty() && !arraySet.contains(trim2)) {
                    arrayList.add(trim2);
                    arraySet.add(trim2);
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
        if (!"sysui_sub_qs_tiles".equals(str)) {
            return;
        }
        Log.d("SecSubScreenQSTileHost", "Recreating SubScreen tiles");
        final List<String> loadTileSpecs = loadTileSpecs(this.mContext, str2);
        Log.d("SecSubScreenQSTileHost", "loaded tiles :" + loadTileSpecs);
        int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
        ArrayList arrayList = this.mTileSpecs;
        if (loadTileSpecs.equals(arrayList) && userId == this.mCurrentUser) {
            return;
        }
        if (userId != this.mCurrentUser && !this.mQSUserChanged) {
            Log.w("SecSubScreenQSTileHost", "Delay recreating tiles until QS userContext change is completed");
            return;
        }
        int i = 0;
        this.mQSUserChanged = false;
        LinkedHashMap linkedHashMap = this.mTiles;
        linkedHashMap.entrySet().stream().filter(new Predicate() { // from class: com.android.systemui.qs.SecSubScreenQSTileHost$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return !loadTileSpecs.contains(((Map.Entry) obj).getKey());
            }
        }).forEach(new SecSubScreenQSTileHost$$ExternalSyntheticLambda1(this, 0));
        LinkedHashMap linkedHashMap2 = new LinkedHashMap();
        for (String str3 : loadTileSpecs) {
            QSTile qSTile = (QSTile) linkedHashMap.get(str3);
            Object obj = this.mTileUsingBySubScreen;
            QSLogger qSLogger = this.mQSLogger;
            SecQSTileInstanceManager secQSTileInstanceManager = this.mQSTileInstanceManager;
            if (qSTile == null || (((z = qSTile instanceof CustomTile)) && ((CustomTile) qSTile).mUser != userId)) {
                if (qSTile != null) {
                    secQSTileInstanceManager.releaseTileUsing(obj, qSTile.getTileSpec());
                    qSLogger.logTileDestroyed(str3, "Tile for wrong user at SubScreen");
                    Log.d("SecSubScreenQSTileHost", "Destroying tile for wrong user: ".concat(str3));
                }
                AbstractC0000x2c234b15.m3m("Creating tile: ", str3, "SecSubScreenQSTileHost");
                try {
                    QSTile requestTileUsing = secQSTileInstanceManager.requestTileUsing(obj, str3);
                    if (requestTileUsing != null) {
                        linkedHashMap2.put(str3, requestTileUsing);
                    }
                } catch (Throwable th) {
                    Log.w("SecSubScreenQSTileHost", "Error creating tile for spec: " + str3, th);
                }
            } else if (qSTile.isAvailable()) {
                if (DEBUG) {
                    Log.d("SecSubScreenQSTileHost", "Adding " + qSTile);
                }
                qSTile.removeCallbacks();
                if (!z && this.mCurrentUser != userId) {
                    qSTile.userSwitch(userId);
                }
                linkedHashMap2.put(str3, qSTile);
            } else {
                secQSTileInstanceManager.releaseTileUsing(obj, qSTile.getTileSpec());
                qSLogger.logTileDestroyed(str3, "Tile not available at SubScreen");
                Log.d("SecSubScreenQSTileHost", "Destroying not available tile: ".concat(str3));
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
                return;
            }
            ((QSHost.Callback) arrayList2.get(i)).onTilesChanged();
            i++;
        }
    }
}
