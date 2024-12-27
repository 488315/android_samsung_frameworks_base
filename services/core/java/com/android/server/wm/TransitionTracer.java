package com.android.server.wm;

import java.io.PrintWriter;
import java.util.ArrayList;

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
