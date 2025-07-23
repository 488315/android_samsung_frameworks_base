package android.service.autofill.augmented;

import android.content.ComponentName;
import android.metrics.LogMaker;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.nano.MetricsProto;

/* loaded from: classes3.dex */
public final class Helper {
    private static final MetricsLogger sMetricsLogger = new MetricsLogger();

    public static void logResponse(int i, String str, ComponentName componentName, int i2, long j) {
        sMetricsLogger.write(new LogMaker(MetricsProto.MetricsEvent.AUTOFILL_AUGMENTED_RESPONSE).setType(i).setComponentName(new ComponentName(componentName.getPackageName(), "")).addTaggedData(MetricsProto.MetricsEvent.FIELD_AUTOFILL_SESSION_ID, Integer.valueOf(i2)).addTaggedData(908, str).addTaggedData(MetricsProto.MetricsEvent.FIELD_AUTOFILL_DURATION, Long.valueOf(j)));
    }

    private Helper() {
        throw new UnsupportedOperationException("contains only static methods");
    }
}
