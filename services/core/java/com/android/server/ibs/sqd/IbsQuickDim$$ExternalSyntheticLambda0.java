package com.android.server.ibs.sqd;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Slog;

import com.android.server.ibs.sleepmode.SharePrefUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class IbsQuickDim$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ IbsQuickDim f$0;

    public /* synthetic */ IbsQuickDim$$ExternalSyntheticLambda0(IbsQuickDim ibsQuickDim, int i) {
        this.$r8$classId = i;
        this.f$0 = ibsQuickDim;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        IbsQuickDim ibsQuickDim = this.f$0;
        switch (i) {
            case 0:
                ibsQuickDim.getClass();
                Slog.d("IbsQuickDim", "readBlockList");
                ibsQuickDim.mBlockUnDimUidSet.clear();
                Cursor query =
                        ibsQuickDim
                                .mSQLiteSQDwhilteList
                                .getDataOperator(0)
                                .query(ibsQuickDim.mSQLiteSQDwhilteList.mDb);
                if (query != null) {
                    while (query.moveToNext()) {
                        try {
                            ibsQuickDim.mBlockUnDimUidSet.add(
                                    Integer.valueOf(
                                            Integer.parseInt(
                                                    query.getString(query.getColumnIndex("Uid")))));
                        } finally {
                        }
                    }
                }
                Slog.d("IbsQuickDim", "readAllowList");
                ibsQuickDim.mAllowDimUidSet.clear();
                query =
                        ibsQuickDim
                                .mSQLiteSQDwhilteList
                                .getDataOperator(1)
                                .query(ibsQuickDim.mSQLiteSQDwhilteList.mDb);
                if (query != null) {
                    while (query.moveToNext()) {
                        try {
                            ibsQuickDim.mAllowDimUidSet.add(
                                    Integer.valueOf(
                                            Integer.parseInt(
                                                    query.getString(query.getColumnIndex("Uid")))));
                        } finally {
                        }
                    }
                    return;
                }
                return;
            case 1:
                if (SharePrefUtils.getBoolean(ibsQuickDim.mContext, "pref_sqd_enabled_key")) {
                    ibsQuickDim.setUicontrolEnable(true);
                    return;
                }
                return;
            default:
                ibsQuickDim.getClass();
                try {
                    if (ibsQuickDim.mUiControlEnabled) {
                        ibsQuickDim.setUicontrolEnable(false);
                    }
                    SharedPreferences.Editor edit =
                            ibsQuickDim.mContext.getSharedPreferences("sleep_mode_pref", 0).edit();
                    edit.clear();
                    edit.apply();
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
        }
    }
}
