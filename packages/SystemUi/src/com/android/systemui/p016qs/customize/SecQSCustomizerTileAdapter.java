package com.android.systemui.p016qs.customize;

import android.app.ActivityManager;
import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.RemoteException;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.ArrayLinkedVariables$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.p016qs.QSTileHost;
import com.android.systemui.p016qs.SecQQSTileHost;
import com.android.systemui.p016qs.customize.SecQSCustomizerBase;
import com.android.systemui.p016qs.customize.SecTileQueryHelper;
import com.android.systemui.p016qs.external.CustomTile;
import com.android.systemui.plugins.p013qs.QSTile;
import com.android.systemui.qs.customize.SecTileQueryHelper.TileCollector;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.sec.ims.settings.ImsProfile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecQSCustomizerTileAdapter implements SecTileQueryHelper.TileStateListener {
    public int mActiveCurrentPage;
    public ArrayList mActiveTiles;
    public List mAllTiles;
    public int mAvailableCurrentPage;
    public ArrayList mAvailableTiles;
    public final Context mContext;
    public List mCurrentSpecs;
    public ArrayList mDefaultActiveTiles;
    public ArrayList mDefaultAvailableTiles;
    public final QSTileHost mHost;
    public boolean mIsLoadedAllTiles = false;
    public final boolean mIsTopEdit;
    public SecQSCustomizerController$$ExternalSyntheticLambda6 mOnTileChangedCallback;
    public final SecQQSTileHost mQQSHost;
    public final SecTileQueryHelper mTileQueryHelper;

    public SecQSCustomizerTileAdapter(Context context, QSTileHost qSTileHost, boolean z, UserTracker userTracker, Executor executor, Executor executor2) {
        this.mHost = qSTileHost;
        this.mIsTopEdit = z;
        this.mQQSHost = qSTileHost.mQQSTileHost;
        SecTileQueryHelper secTileQueryHelper = new SecTileQueryHelper(context, userTracker, executor, executor2);
        this.mTileQueryHelper = secTileQueryHelper;
        secTileQueryHelper.mListener = this;
        this.mContext = context;
    }

    public final void saveTiles(CustomizerTileViewPager customizerTileViewPager, CustomizerTileViewPager customizerTileViewPager2, boolean z) {
        this.mActiveTiles = z ? this.mDefaultActiveTiles : customizerTileViewPager.getTilesInfo();
        this.mAvailableTiles = z ? this.mDefaultAvailableTiles : customizerTileViewPager2.getTilesInfo();
        this.mActiveCurrentPage = z ? 0 : customizerTileViewPager.getCurrentItem();
        this.mAvailableCurrentPage = z ? 0 : customizerTileViewPager2.getCurrentItem();
    }

    public final void updateRemovedTileList(String str, boolean z) {
        if (this.mIsTopEdit) {
            return;
        }
        QSTileHost qSTileHost = this.mHost;
        qSTileHost.getClass();
        ArrayList arrayList = new ArrayList();
        Context context = qSTileHost.mContext;
        ContentResolver contentResolver = context.getContentResolver();
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) qSTileHost.mUserTracker;
        String stringForUser = Settings.Secure.getStringForUser(contentResolver, "sysui_removed_qs_tiles", userTrackerImpl.getUserId());
        Log.d("QSTileHost", "updateRemovedTileList : tile = " + str + ", tileIsAdded = " + z);
        if (stringForUser != null) {
            for (String str2 : stringForUser.split(",")) {
                arrayList.add(str2);
            }
        }
        if (z) {
            if (arrayList.contains(str)) {
                arrayList.remove(str);
            }
        } else if (!arrayList.contains(str)) {
            arrayList.add(str);
        }
        Settings.Secure.putStringForUser(context.getContentResolver(), "sysui_removed_qs_tiles", TextUtils.join(",", arrayList), userTrackerImpl.getUserId());
    }

    public final void updateTiles() {
        QSTile createTile;
        boolean isAvailable;
        this.mIsLoadedAllTiles = false;
        this.mCurrentSpecs = new ArrayList();
        SecQQSTileHost secQQSTileHost = this.mQQSHost;
        QSTileHost qSTileHost = this.mHost;
        boolean z = this.mIsTopEdit;
        Iterator it = (z ? secQQSTileHost.mTiles.values() : qSTileHost.getTiles()).iterator();
        while (it.hasNext()) {
            ((ArrayList) this.mCurrentSpecs).add(((QSTile) it.next()).getTileSpec());
        }
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        Context context = this.mContext;
        sb.append(context.getString(R.string.qs_edit_setting_active_area_tapped));
        sb.append(" ");
        String sb2 = sb.toString();
        String m73m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m73m(context, R.string.qs_edit_double_tab_and_hold_then_drag_to_reorder, new StringBuilder(", "));
        for (QSTile qSTile : z ? secQQSTileHost.mTiles.values() : qSTileHost.getTiles()) {
            if (qSTile.getState().isCustomTile) {
                ComponentName componentFromSpec = CustomTile.getComponentFromSpec(qSTile.getTileSpec());
                try {
                    ServiceInfo serviceInfo = AppGlobals.getPackageManager().getServiceInfo(componentFromSpec, 0L, ActivityManager.getCurrentUser());
                    if (serviceInfo == null) {
                        Log.d("SecQSCustomizerTileAdapter", "Can't find component " + componentFromSpec);
                    }
                    if (serviceInfo != null) {
                        isAvailable = true;
                    }
                } catch (RemoteException unused) {
                }
                isAvailable = false;
            } else {
                isAvailable = qSTile.isAvailable();
            }
            if (isAvailable) {
                SecQSCustomizerBase.CustomTileInfo customTileInfo = new SecQSCustomizerBase.CustomTileInfo();
                customTileInfo.spec = qSTile.getTileSpec();
                QSTile.State state = qSTile.getState();
                customTileInfo.state = state;
                state.dualTarget = false;
                customTileInfo.isActive = true;
                StringBuilder m18m = ArrayLinkedVariables$$ExternalSyntheticOutline0.m18m(sb2);
                m18m.append((Object) customTileInfo.state.label);
                m18m.append(m73m);
                customTileInfo.customizeTileContentDes = m18m.toString();
                arrayList.add(customTileInfo);
            }
        }
        Log.d("SecQSCustomizerTileAdapter", "addingTiles: " + arrayList);
        this.mDefaultActiveTiles = arrayList;
        this.mActiveTiles = arrayList;
        SecTileQueryHelper secTileQueryHelper = this.mTileQueryHelper;
        secTileQueryHelper.mTiles.clear();
        secTileQueryHelper.mSpecs.clear();
        secTileQueryHelper.mFinished = false;
        secTileQueryHelper.mTileQueryFinished = false;
        String m15m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(qSTileHost.changeOldOSTileListToNewOsTileList(z ? qSTileHost.mQQSTileHost.mQSTileHost.getSupportedAllTileList() : qSTileHost.getDefaultTileList()), ",", secTileQueryHelper.mContext.getString(R.string.available_non_custom_tiles));
        ArrayList arrayList2 = new ArrayList();
        arrayList2.addAll(Arrays.asList("".split(",")));
        for (String str : m15m.split(",")) {
            if (!"".contains(str)) {
                arrayList2.add(str);
            }
        }
        if (Build.IS_DEBUGGABLE) {
            arrayList2.add("dbg:mem");
        }
        ArrayList arrayList3 = new ArrayList();
        arrayList2.remove("cell");
        arrayList2.remove(ImsProfile.PDN_WIFI);
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            String str2 = (String) it2.next();
            if (!str2.startsWith("custom(") && (createTile = qSTileHost.createTile(str2)) != null) {
                createTile.setTileSpec(str2);
                if (createTile.isAvailable()) {
                    createTile.setTileSpec(str2);
                    arrayList3.add(createTile);
                } else {
                    createTile.destroy();
                }
            }
        }
        SecTileQueryHelper.TileCollector tileCollector = secTileQueryHelper.new TileCollector(arrayList3, qSTileHost, z);
        Iterator it3 = ((ArrayList) tileCollector.mQSTileList).iterator();
        while (it3.hasNext()) {
            SecTileQueryHelper.TilePair tilePair = (SecTileQueryHelper.TilePair) it3.next();
            tilePair.mTile.addCallback(tileCollector);
            QSTile qSTile2 = tilePair.mTile;
            qSTile2.setListening(tileCollector, true);
            qSTile2.refreshState();
        }
    }
}
