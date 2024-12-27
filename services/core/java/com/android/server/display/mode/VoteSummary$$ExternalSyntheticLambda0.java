package com.android.server.display.mode;

import android.view.Display;

import java.util.Comparator;

public final /* synthetic */ class VoteSummary$$ExternalSyntheticLambda0 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return Float.compare(
                ((Display.Mode) obj).getRefreshRate(), ((Display.Mode) obj2).getRefreshRate());
    }
}
