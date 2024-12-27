package com.android.server.am.mars.database;

import android.util.Slog;

import com.android.server.am.mars.MARsUtils;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class MARsDBHandler$$ExternalSyntheticLambda0 implements Runnable {
    @Override // java.lang.Runnable
    public final void run() {
        ArrayList fASDataFromDB = FASDataManager.FASDataManagerHolder.INSTANCE.getFASDataFromDB();
        if (fASDataFromDB == null || fASDataFromDB.isEmpty()) {
            Slog.e("MARsDBHandler", "Packages database not exist, and not created!!");
        } else {
            MARsUtils.updateMARsTargetPackages(fASDataFromDB);
        }
    }
}
