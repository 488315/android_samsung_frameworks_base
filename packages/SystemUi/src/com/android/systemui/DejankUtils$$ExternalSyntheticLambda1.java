package com.android.systemui;

import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class DejankUtils$$ExternalSyntheticLambda1 implements Runnable {
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
