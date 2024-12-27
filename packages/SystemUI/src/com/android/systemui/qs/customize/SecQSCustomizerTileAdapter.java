package com.android.systemui.qs.customize;

import android.app.ActivityManager;
import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ServiceInfo;
import android.os.RemoteException;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import androidx.compose.ui.text.input.EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.qs.SecQQSTileHost;
import com.android.systemui.qs.customize.SecTileQueryHelper;
import com.android.systemui.qs.customize.SecTileQueryHelper.TileCollector;
import com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController$$ExternalSyntheticLambda4;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.sec.ims.settings.ImsProfile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SecQSCustomizerTileAdapter {
    public int mActiveCurrentPage;
    public ArrayList mActiveTiles;
    public List mAllTiles;
    public int mAvailableCurrentPage;
    public ArrayList mAvailableTiles;
    public final Context mContext;
    public List mCurrentSpecs;
    public ArrayList mDefaultActiveTiles;
    public final QSTileHost mHost;
    public boolean mIsLoadedAllTiles = false;
    public final boolean mIsTopEdit;
    public QSTileCustomizerController$$ExternalSyntheticLambda4 mOnTileChangedCallback;
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

    public final void updateRemovedTileList(String str, boolean z) {
        if (this.mIsTopEdit) {
            return;
        }
        QSTileHost qSTileHost = this.mHost;
        qSTileHost.getClass();
        ArrayList arrayList = new ArrayList();
        ContentResolver contentResolver = qSTileHost.mContext.getContentResolver();
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
        Settings.Secure.putStringForUser(qSTileHost.mContext.getContentResolver(), "sysui_removed_qs_tiles", TextUtils.join(",", arrayList), userTrackerImpl.getUserId());
    }

    public final void updateTiles() {
        QSTile createTile;
        boolean isAvailable;
        this.mIsLoadedAllTiles = false;
        this.mCurrentSpecs = new ArrayList();
        SecQQSTileHost secQQSTileHost = this.mQQSHost;
        QSTileHost qSTileHost = this.mHost;
        boolean z = this.mIsTopEdit;
        Iterator it = (z ? secQQSTileHost.mTiles.values() : qSTileHost.mTiles.values()).iterator();
        while (it.hasNext()) {
            ((ArrayList) this.mCurrentSpecs).add(((QSTile) it.next()).getTileSpec());
        }
        ArrayList arrayList = new ArrayList();
        String str = this.mContext.getString(R.string.qs_edit_setting_active_area_tapped) + " ";
        for (QSTile qSTile : z ? secQQSTileHost.mTiles.values() : qSTileHost.mTiles.values()) {
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
                CustomTileInfo customTileInfo = new CustomTileInfo();
                customTileInfo.spec = qSTile.getTileSpec();
                QSTile.State state = qSTile.getState();
                customTileInfo.state = state;
                state.dualTarget = false;
                customTileInfo.isActive = true;
                StringBuilder m = EditProcessor$generateBatchErrorMessage$1$1$$ExternalSyntheticOutline0.m(str);
                m.append((Object) customTileInfo.state.label);
                customTileInfo.customizeTileContentDes = m.toString();
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
        String m2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(qSTileHost.changeOldOSTileListToNewOsTileList(z ? qSTileHost.mQQSTileHost.mQSTileHost.getSupportedAllTileList() : qSTileHost.getDefaultTileList()), ",", secTileQueryHelper.mContext.getString(R.string.available_non_custom_tiles));
        ArrayList arrayList2 = new ArrayList();
        for (String str2 : m2.split(",")) {
            if (!"".contains(str2)) {
                arrayList2.add(str2);
            }
        }
        ArrayList arrayList3 = new ArrayList();
        arrayList2.remove("cell");
        arrayList2.remove(ImsProfile.PDN_WIFI);
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            String str3 = (String) it2.next();
            if (!str3.startsWith("custom(") && (createTile = qSTileHost.createTile(str3)) != null) {
                if (createTile.isAvailable()) {
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
