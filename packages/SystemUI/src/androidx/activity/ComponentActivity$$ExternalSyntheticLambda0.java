package androidx.activity;

import androidx.activity.ComponentActivity;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class ComponentActivity$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ComponentActivity$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                int i2 = ComponentActivity.$r8$clinit;
                ((ComponentActivity) obj).invalidateOptionsMenu();
                return;
            case 1:
                try {
                    super/*android.app.Activity*/.onBackPressed();
                    return;
                } catch (IllegalStateException e) {
                    if (!Intrinsics.areEqual(e.getMessage(), "Can not perform this action after onSaveInstanceState")) {
                        throw e;
                    }
                    return;
                } catch (NullPointerException e2) {
                    if (!Intrinsics.areEqual(e2.getMessage(), "Attempt to invoke virtual method 'android.os.Handler android.app.FragmentHostCallback.getHandler()' on a null object reference")) {
                        throw e2;
                    }
                    return;
                }
            default:
                ComponentActivity.ReportFullyDrawnExecutorImpl reportFullyDrawnExecutorImpl = (ComponentActivity.ReportFullyDrawnExecutorImpl) obj;
                Runnable runnable = reportFullyDrawnExecutorImpl.currentRunnable;
                if (runnable != null) {
                    runnable.run();
                    reportFullyDrawnExecutorImpl.currentRunnable = null;
                    return;
                }
                return;
        }
    }
}
