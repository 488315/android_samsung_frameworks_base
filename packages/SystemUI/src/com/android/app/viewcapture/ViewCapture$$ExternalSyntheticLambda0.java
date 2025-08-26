package com.android.app.viewcapture;

import com.android.app.viewcapture.ViewCapture;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public final /* synthetic */ class ViewCapture$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        LooperExecutor looperExecutor = ViewCapture.MAIN_EXECUTOR;
        return ((ViewCapture.WindowListener) obj).mIsActive;
    }
}
