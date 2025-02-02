package androidx.activity;

import androidx.activity.ComponentActivity;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class ComponentActivity$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ComponentActivity$$ExternalSyntheticLambda0(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((ComponentActivity) this.f$0).invalidateOptionsMenu();
                break;
            default:
                ComponentActivity.ReportFullyDrawnExecutorApi16Impl reportFullyDrawnExecutorApi16Impl = (ComponentActivity.ReportFullyDrawnExecutorApi16Impl) this.f$0;
                Runnable runnable = reportFullyDrawnExecutorApi16Impl.mRunnable;
                if (runnable != null) {
                    runnable.run();
                    reportFullyDrawnExecutorApi16Impl.mRunnable = null;
                    break;
                }
                break;
        }
    }
}
