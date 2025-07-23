package com.android.app.viewcapture;

import com.android.app.viewcapture.ViewCapture;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class ViewCapture$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        LooperExecutor looperExecutor = ViewCapture.MAIN_EXECUTOR;
        return ((ViewCapture.WindowListener) obj).mIsActive;
    }
}
