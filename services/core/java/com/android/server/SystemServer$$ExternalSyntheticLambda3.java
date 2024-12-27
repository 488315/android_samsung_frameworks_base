package com.android.server;

import android.os.Build;
import android.os.Process;
import android.util.Slog;
import com.android.server.utils.TimingsTraceAndSlog;
import java.util.LinkedList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SystemServer$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ SystemServer$$ExternalSyntheticLambda3(int i) {
        this.$r8$classId = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SystemConfig.getInstance();
                break;
            case 1:
                LinkedList linkedList = SystemServer.sPendingWtfs;
                try {
                    Slog.i("SystemServer", "SecondaryZygotePreload");
                    TimingsTraceAndSlog newAsyncLog = TimingsTraceAndSlog.newAsyncLog();
                    newAsyncLog.traceBegin("SecondaryZygotePreload");
                    String[] strArr = Build.SUPPORTED_32_BIT_ABIS;
                    if (strArr.length > 0 && !Process.ZYGOTE_PROCESS.preloadDefault(strArr[0])) {
                        Slog.e("SystemServer", "Unable to preload default resources for secondary");
                    }
                    newAsyncLog.traceEnd();
                    break;
                } catch (Exception e) {
                    Slog.e("SystemServer", "Exception preloading default resources", e);
                    return;
                }
                break;
            case 2:
                SystemServer.m94$r8$lambda$CJLFlg8wnqihjN12r2Qq_1qSd8();
                break;
            default:
                SystemServer.$r8$lambda$0ek3wX68xKbgZMUwZfiBRkUNTFs();
                break;
        }
    }
}
