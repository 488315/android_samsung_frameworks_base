package com.android.systemui;

import java.util.ArrayList;

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
