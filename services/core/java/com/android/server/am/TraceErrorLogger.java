package com.android.server.am;

import android.os.Trace;
import java.util.UUID;

/* loaded from: classes.dex */
public class TraceErrorLogger {
    public boolean isAddErrorIdEnabled() {
        return true;
    }

    public UUID generateErrorId() {
        return UUID.randomUUID();
    }

    public void addProcessInfoAndErrorIdToTrace(String str, int i, UUID uuid) {
        Trace.traceCounter(64L, "ErrorId:" + str + " " + i + "#" + uuid.toString(), 1);
    }

    public void addSubjectToTrace(String str, UUID uuid) {
        Trace.traceCounter(64L, String.format("Subject(for ErrorId %s):%s", uuid.toString(), str), 1);
    }
}
