package com.android.systemui;

import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class DejankUtils$$ExternalSyntheticLambda0 implements Runnable {
    @Override // java.lang.Runnable
    public final void run() {
        int i = 0;
        while (true) {
            ArrayList arrayList = DejankUtils.sPendingRunnables;
            if (i >= arrayList.size()) {
                arrayList.clear();
                return;
            } else {
                DejankUtils.sHandler.post((Runnable) arrayList.get(i));
                i++;
            }
        }
    }
}
