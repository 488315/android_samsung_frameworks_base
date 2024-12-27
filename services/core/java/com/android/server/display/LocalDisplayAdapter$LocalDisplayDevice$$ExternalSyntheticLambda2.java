package com.android.server.display;

import android.hardware.display.DisplayManagerInternal;
import com.android.server.power.PowerManagerUtil;
import com.android.server.power.Slog;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class LocalDisplayAdapter$LocalDisplayDevice$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PowerManagerUtil.StopwatchLogger f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ LocalDisplayAdapter$LocalDisplayDevice$$ExternalSyntheticLambda2(PowerManagerUtil.StopwatchLogger stopwatchLogger, int i, int i2, int i3, int i4) {
        this.$r8$classId = i4;
        this.f$0 = stopwatchLogger;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = i3;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                PowerManagerUtil.StopwatchLogger stopwatchLogger = this.f$0;
                int i = this.f$1;
                int i2 = this.f$2;
                int i3 = this.f$3;
                DisplayManagerInternal.DisplayStateListener displayStateListener = (DisplayManagerInternal.DisplayStateListener) obj;
                stopwatchLogger.getClass();
                stopwatchLogger.mStartTimeMillis = System.currentTimeMillis();
                displayStateListener.onFinish(i, i2, i3);
                Slog.d("LocalDisplayAdapter", ("notifyStateChangeFinish: " + displayStateListener) + " took " + (System.currentTimeMillis() - stopwatchLogger.mStartTimeMillis) + " ms");
                break;
            default:
                PowerManagerUtil.StopwatchLogger stopwatchLogger2 = this.f$0;
                int i4 = this.f$1;
                int i5 = this.f$2;
                int i6 = this.f$3;
                DisplayManagerInternal.DisplayStateListener displayStateListener2 = (DisplayManagerInternal.DisplayStateListener) obj;
                stopwatchLogger2.getClass();
                stopwatchLogger2.mStartTimeMillis = System.currentTimeMillis();
                displayStateListener2.onStart(i4, i5, i6);
                Slog.d("LocalDisplayAdapter", ("notifyStateChangeStart: " + displayStateListener2) + " took " + (System.currentTimeMillis() - stopwatchLogger2.mStartTimeMillis) + " ms");
                break;
        }
    }
}
