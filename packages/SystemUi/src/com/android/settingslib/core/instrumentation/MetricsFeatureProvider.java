package com.android.settingslib.core.instrumentation;

import android.metrics.LogMaker;
import com.android.internal.logging.MetricsLogger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MetricsFeatureProvider {
    public final List mLoggerWriters;

    public MetricsFeatureProvider() {
        ArrayList arrayList = new ArrayList();
        this.mLoggerWriters = arrayList;
        arrayList.add(new EventLogWriter());
    }

    public final void visible(int i, int i2) {
        Iterator it = ((ArrayList) this.mLoggerWriters).iterator();
        while (it.hasNext()) {
            ((EventLogWriter) it.next()).getClass();
            MetricsLogger.action(new LogMaker(i).setType(1).addTaggedData(833, 0).addTaggedData(1089, Integer.valueOf(i2)));
        }
    }
}
