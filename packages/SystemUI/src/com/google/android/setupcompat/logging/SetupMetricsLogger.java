package com.google.android.setupcompat.logging;

import android.content.Context;
import android.os.Bundle;
import com.google.android.setupcompat.internal.Preconditions;
import com.google.android.setupcompat.internal.SetupCompatServiceInvoker;
import com.google.android.setupcompat.util.Logger;

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
