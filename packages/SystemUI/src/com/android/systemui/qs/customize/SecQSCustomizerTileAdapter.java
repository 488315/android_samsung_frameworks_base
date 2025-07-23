package com.android.systemui.qs.customize;

import android.app.ActivityManager;
import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ServiceInfo;
import android.os.RemoteException;
import android.util.Log;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.customize.SecTileQueryHelper;
import com.android.systemui.qs.customize.SecTileQueryHelper.TileCollector;
import com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController$$ExternalSyntheticLambda3;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.settings.UserTracker;
import com.sec.ims.settings.ImsProfile;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SecQSCustomizerTileAdapter {
    public int mActiveCurrentPage;
    public ArrayList mActiveTiles;
    public List mAllTiles;
    public int mAvailableCurrentPage;
    public ArrayList mAvailableTiles;
    public final Context mContext;
    public List mCurrentSpecs;
    public ArrayList mDefaultActiveTiles;
    public final QSHost mHost;
    public boolean mIsLoadedAllTiles = false;
    public boolean mIsReset = false;
    public final boolean mIsTopEdit;
    public QSTileCustomizerController$$ExternalSyntheticLambda3 mOnTileChangedCallback;
    public final SecTileQueryHelper mTileQueryHelper;

    public SecQSCustomizerTileAdapter(Context context, QSHost qSHost, boolean z, UserTracker userTracker, Executor executor, Executor executor2) {
        this.mHost = qSHost;
        this.mIsTopEdit = z;
        SecTileQueryHelper secTileQueryHelper = new SecTileQueryHelper(context, userTracker, executor, executor2);
        this.mTileQueryHelper = secTileQueryHelper;
        secTileQueryHelper.mListener = this;
        this.mContext = context;
    }

    public final void updateTiles() {
        QSTile createTile;
        QSTile qSTile;
        boolean isAvailable;
        int i = 0;
        this.mIsLoadedAllTiles = false;
        this.mCurrentSpecs = new ArrayList();
        QSHost qSHost = this.mHost;
        ArrayList arrayList = (ArrayList) qSHost.getTiles();
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            ((ArrayList) this.mCurrentSpecs).add(((QSTile) obj).getTileSpec());
        }
        ArrayList arrayList2 = new ArrayList();
        Log.d("SecQSCustomizerTileAdapter", "add ActiveTileSpecs: " + qSHost.getTiles());
        String str = this.mContext.getString(R.string.qs_edit_setting_active_area_tapped) + " ";
        ArrayList arrayList3 = (ArrayList) qSHost.getTiles();
        int size2 = arrayList3.size();
        int i3 = 0;
        while (i3 < size2) {
            Object obj2 = arrayList3.get(i3);
            i3++;
            QSTile qSTile2 = (QSTile) obj2;
            if (qSTile2.getState().isCustomTile) {
                ComponentName componentFromSpec = CustomTile.getComponentFromSpec(qSTile2.getTileSpec());
                try {
                    qSTile = qSTile2;
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
                } catch (RemoteException unused2) {
                    qSTile = qSTile2;
                }
                isAvailable = false;
            } else {
                qSTile = qSTile2;
                isAvailable = qSTile.isAvailable();
            }
            if (isAvailable) {
                CustomTileInfo customTileInfo = new CustomTileInfo();
                customTileInfo.spec = qSTile.getTileSpec();
                QSTile.State state = qSTile.getState();
                customTileInfo.state = state;
                state.dualTarget = false;
                customTileInfo.isActive = true;
                StringBuilder m = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(str);
                m.append((Object) customTileInfo.state.label);
                customTileInfo.customizeTileContentDes = m.toString();
                arrayList2.add(customTileInfo);
            }
        }
        Log.d("SecQSCustomizerTileAdapter", "addingActiveTiles: " + arrayList2);
        this.mDefaultActiveTiles = arrayList2;
        this.mActiveTiles = arrayList2;
        SecTileQueryHelper secTileQueryHelper = this.mTileQueryHelper;
        secTileQueryHelper.mTiles.clear();
        secTileQueryHelper.mSpecs.clear();
        secTileQueryHelper.mFinished = false;
        secTileQueryHelper.mTileQueryFinished = false;
        String string = secTileQueryHelper.mContext.getString(R.string.sec_quick_settings_tiles_stock);
        ArrayList arrayList4 = new ArrayList();
        for (String str2 : string.split(",")) {
            if (!"".contains(str2) && !qSHost.isBarTile(str2)) {
                arrayList4.add(str2);
            }
        }
        ArrayList arrayList5 = new ArrayList();
        arrayList4.remove("cell");
        arrayList4.remove(ImsProfile.PDN_WIFI);
        int size3 = arrayList4.size();
        int i4 = 0;
        while (i4 < size3) {
            Object obj3 = arrayList4.get(i4);
            i4++;
            String str3 = (String) obj3;
            if (!str3.startsWith("custom(") && (createTile = qSHost.createTile(str3)) != null) {
                if (!createTile.isAvailable()) {
                    createTile.destroy();
                } else if (!qSHost.shouldBeHiddenByKnox(str3)) {
                    arrayList5.add(createTile);
                }
            }
        }
        SecTileQueryHelper.TileCollector tileCollector = secTileQueryHelper.new TileCollector(arrayList5, qSHost, this.mIsTopEdit);
        ArrayList arrayList6 = (ArrayList) tileCollector.mQSTileList;
        int size4 = arrayList6.size();
        while (i < size4) {
            Object obj4 = arrayList6.get(i);
            i++;
            SecTileQueryHelper.TilePair tilePair = (SecTileQueryHelper.TilePair) obj4;
            tilePair.mTile.addCallback(tileCollector);
            QSTile qSTile3 = tilePair.mTile;
            qSTile3.setListening(tileCollector, true);
            qSTile3.refreshState();
        }
    }
}
