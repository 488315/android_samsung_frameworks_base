package com.android.server.wm;

import java.io.PrintWriter;
import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public interface TransitionTracer {
    boolean isTracing();

    void logAbortedTransition(Transition transition);

    void logFinishedTransition(Transition transition);

    void logRemovingStartingWindow(StartingData startingData);

    void logSentTransition(Transition transition, ArrayList arrayList);

    void saveForBugreport(PrintWriter printWriter);

    void startTrace(PrintWriter printWriter);

    void stopTrace(PrintWriter printWriter);
}
