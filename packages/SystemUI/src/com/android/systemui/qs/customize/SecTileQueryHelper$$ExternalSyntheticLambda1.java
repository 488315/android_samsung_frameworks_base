package com.android.systemui.qs.customize;

import android.util.Log;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.qs.customize.SecTileQueryHelper;
import com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController;
import com.android.systemui.qs.customize.viewcontroller.QSTileCustomizerController$$ExternalSyntheticLambda3;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final /* synthetic */ class SecTileQueryHelper$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ SecTileQueryHelper f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ ArrayList f$2;

    public /* synthetic */ SecTileQueryHelper$$ExternalSyntheticLambda1(SecTileQueryHelper secTileQueryHelper, boolean z, ArrayList arrayList) {
        this.f$0 = secTileQueryHelper;
        this.f$1 = z;
        this.f$2 = arrayList;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ArrayList arrayList;
        SecTileQueryHelper secTileQueryHelper = this.f$0;
        boolean z = this.f$1;
        ArrayList arrayList2 = this.f$2;
        secTileQueryHelper.mTileQueryFinished = z;
        SecQSCustomizerTileAdapter secQSCustomizerTileAdapter = secTileQueryHelper.mListener;
        if (secQSCustomizerTileAdapter != null) {
            secQSCustomizerTileAdapter.mAllTiles = arrayList2;
            int i = 0;
            if (secQSCustomizerTileAdapter.mCurrentSpecs != null && secQSCustomizerTileAdapter.mTileQueryHelper.mTileQueryFinished) {
                Log.d("SecQSCustomizerTileAdapter", "mCurrentSpecs = " + secQSCustomizerTileAdapter.mCurrentSpecs + "mAllTiles size = " + ((ArrayList) secQSCustomizerTileAdapter.mAllTiles).size());
                ArrayList arrayList3 = new ArrayList(secQSCustomizerTileAdapter.mAllTiles);
                for (int i2 = 0; i2 < ((ArrayList) secQSCustomizerTileAdapter.mCurrentSpecs).size(); i2++) {
                    String str = (String) ((ArrayList) secQSCustomizerTileAdapter.mCurrentSpecs).get(i2);
                    int i3 = 0;
                    while (true) {
                        if (i3 >= arrayList3.size()) {
                            break;
                        }
                        if (((SecTileQueryHelper.TileInfo) arrayList3.get(i3)).spec.equals(str)) {
                            break;
                        }
                        i3++;
                    }
                }
                int size = arrayList3.size();
                int i4 = 0;
                while (i4 < size) {
                    Object obj = arrayList3.get(i4);
                    i4++;
                    ((SecTileQueryHelper.TileInfo) obj).isActive = false;
                }
                ArrayList arrayList4 = new ArrayList();
                String str2 = secQSCustomizerTileAdapter.mContext.getString(R.string.qs_edit_setting_available_area_tapped) + " ";
                String strM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(secQSCustomizerTileAdapter.mContext, R.string.qs_edit_double_tab_and_hold_then_drag_to_reorder, new StringBuilder(", "));
                for (int i5 = 0; i5 < arrayList3.size(); i5++) {
                    CustomTileInfo customTileInfo = new CustomTileInfo();
                    SecTileQueryHelper.TileInfo tileInfo = (SecTileQueryHelper.TileInfo) arrayList3.get(i5);
                    customTileInfo.spec = tileInfo.spec;
                    customTileInfo.isActive = tileInfo.isActive;
                    customTileInfo.state = tileInfo.state;
                    Log.d("SecQSCustomizerTileAdapter", "addTile state = " + customTileInfo.state);
                    customTileInfo.customizeTileContentDes = str2 + ((Object) customTileInfo.state.label) + "" + strM;
                    arrayList4.add(customTileInfo);
                }
                secQSCustomizerTileAdapter.mAvailableTiles = arrayList4;
            }
            QSTileCustomizerController$$ExternalSyntheticLambda3 qSTileCustomizerController$$ExternalSyntheticLambda3 = secQSCustomizerTileAdapter.mOnTileChangedCallback;
            if (qSTileCustomizerController$$ExternalSyntheticLambda3 != null && (arrayList = secQSCustomizerTileAdapter.mAvailableTiles) != null) {
                QSTileCustomizerController qSTileCustomizerController = qSTileCustomizerController$$ExternalSyntheticLambda3.f$0;
                qSTileCustomizerController.mAvailableTileLayout.addTiles(arrayList);
                int size2 = arrayList.size();
                while (i < size2) {
                    Object obj2 = arrayList.get(i);
                    i++;
                    ((CustomTileInfo) obj2).longClickListener = qSTileCustomizerController.mInteractionManager.longClickListener;
                }
            }
            if (secQSCustomizerTileAdapter.mAvailableTiles != null) {
                secQSCustomizerTileAdapter.mIsLoadedAllTiles = true;
            }
        }
        secTileQueryHelper.mFinished = z;
    }
}
