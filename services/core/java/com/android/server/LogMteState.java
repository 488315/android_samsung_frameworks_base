package com.android.server;

import android.app.StatsManager;
import android.content.Context;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;

import com.android.internal.os.Zygote;
import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.FrameworkStatsLog;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class LogMteState {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.LogMteState$1, reason: invalid class name */
    public final class AnonymousClass1 implements StatsManager.StatsPullAtomCallback {
        public final int onPullAtom(int i, List list) {
            if (i != 10181) {
                throw new UnsupportedOperationException(
                        VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown tagId="));
            }
            list.add(
                    FrameworkStatsLog.buildStatsEvent(
                            FrameworkStatsLog.MTE_STATE,
                            Zygote.nativeSupportsMemoryTagging() ? 2 : 1));
            return 0;
        }
    }

    public static void register(Context context) {
        ((StatsManager) context.getSystemService(StatsManager.class))
                .setPullAtomCallback(
                        FrameworkStatsLog.MTE_STATE,
                        (StatsManager.PullAtomMetadata) null,
                        ConcurrentUtils.DIRECT_EXECUTOR,
                        new AnonymousClass1());
    }
}
