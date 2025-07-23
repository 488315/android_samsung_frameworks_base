package com.google.android.setupcompat.logging;

import android.content.Context;
import android.os.Bundle;
import com.google.android.setupcompat.internal.Preconditions;
import com.google.android.setupcompat.internal.SetupCompatServiceInvoker;
import com.google.android.setupcompat.util.Logger;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SetupMetricsLogger {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        new Logger("SetupMetricsLogger");
    }

    private SetupMetricsLogger(Context context) {
    }

    public static void logCustomEvent(Context context, CustomEvent customEvent) {
        Preconditions.checkNotNull(context, "Context cannot be null.");
        SetupCompatServiceInvoker setupCompatServiceInvoker = SetupCompatServiceInvoker.get(context);
        Bundle bundle = new Bundle();
        bundle.putParcelable("CustomEvent_bundle", CustomEvent.toBundle(customEvent));
        setupCompatServiceInvoker.logMetricEvent(1, bundle);
    }

    public static void setInstanceForTesting(SetupMetricsLogger setupMetricsLogger) {
    }
}
