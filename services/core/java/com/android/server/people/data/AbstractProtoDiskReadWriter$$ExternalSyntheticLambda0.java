package com.android.server.people.data;

import android.util.ArrayMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AbstractProtoDiskReadWriter$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ AbstractProtoDiskReadWriter f$0;

    @Override // java.lang.Runnable
    public final void run() {
        AbstractProtoDiskReadWriter abstractProtoDiskReadWriter = this.f$0;
        synchronized (abstractProtoDiskReadWriter) {
            if (((ArrayMap) abstractProtoDiskReadWriter.mScheduledFileDataMap).isEmpty()) {
                abstractProtoDiskReadWriter.mScheduledFuture = null;
                return;
            }
            for (String str :
                    ((ArrayMap) abstractProtoDiskReadWriter.mScheduledFileDataMap).keySet()) {
                abstractProtoDiskReadWriter.writeTo(
                        str,
                        ((ArrayMap) abstractProtoDiskReadWriter.mScheduledFileDataMap).get(str));
            }
            ((ArrayMap) abstractProtoDiskReadWriter.mScheduledFileDataMap).clear();
            abstractProtoDiskReadWriter.mScheduledFuture = null;
        }
    }
}
