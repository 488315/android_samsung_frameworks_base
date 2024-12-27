package com.android.server.am.mars.database;

import android.util.Slog;

import com.android.server.am.mars.MARsUtils;

import java.util.ArrayList;

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
