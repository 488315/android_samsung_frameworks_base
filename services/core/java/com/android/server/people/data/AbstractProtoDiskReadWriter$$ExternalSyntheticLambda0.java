package com.android.server.people.data;

import android.util.ArrayMap;

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
