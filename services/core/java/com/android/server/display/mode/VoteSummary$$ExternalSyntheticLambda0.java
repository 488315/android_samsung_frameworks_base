package com.android.server.display.mode;

import android.view.Display;

import java.util.Comparator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class VoteSummary$$ExternalSyntheticLambda0 implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        return Float.compare(
                ((Display.Mode) obj).getRefreshRate(), ((Display.Mode) obj2).getRefreshRate());
    }
}
