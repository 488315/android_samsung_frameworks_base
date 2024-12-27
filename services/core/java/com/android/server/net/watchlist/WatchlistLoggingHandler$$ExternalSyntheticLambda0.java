package com.android.server.net.watchlist;

import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.os.incremental.IncrementalManager;
import android.text.TextUtils;
import android.util.Slog;

import com.android.internal.util.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.function.Function;

public final /* synthetic */ class WatchlistLoggingHandler$$ExternalSyntheticLambda0
        implements Function {
    public final /* synthetic */ WatchlistLoggingHandler f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ WatchlistLoggingHandler$$ExternalSyntheticLambda0(
            WatchlistLoggingHandler watchlistLoggingHandler, int i) {
        this.f$0 = watchlistLoggingHandler;
        this.f$1 = i;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        WatchlistLoggingHandler watchlistLoggingHandler = this.f$0;
        int i = this.f$1;
        Integer num = (Integer) obj;
        String[] packagesForUid = watchlistLoggingHandler.mPm.getPackagesForUid(num.intValue());
        int userId = UserHandle.getUserId(i);
        if (ArrayUtils.isEmpty(packagesForUid)) {
            return null;
        }
        for (String str : packagesForUid) {
            try {
                String str2 =
                        watchlistLoggingHandler.mPm.getPackageInfoAsUser(str, 786432, userId)
                                .applicationInfo
                                .publicSourceDir;
                if (TextUtils.isEmpty(str2)) {
                    Slog.w("WatchlistLoggingHandler", "Cannot find apkPath for " + str);
                } else {
                    if (!IncrementalManager.isIncrementalPath(str2)) {
                        FileHashCache fileHashCache = watchlistLoggingHandler.mApkHashCache;
                        return fileHashCache != null
                                ? fileHashCache.getSha256Hash(new File(str2))
                                : DigestUtils.getSha256Hash(new File(str2));
                    }
                    Slog.i("WatchlistLoggingHandler", "Skipping incremental path: " + str);
                }
            } catch (PackageManager.NameNotFoundException
                    | IOException
                    | NoSuchAlgorithmException e) {
                Slog.e(
                        "WatchlistLoggingHandler",
                        "Cannot get digest from uid: " + num + ",pkg: " + str,
                        e);
                return null;
            }
        }
        return null;
    }
}
