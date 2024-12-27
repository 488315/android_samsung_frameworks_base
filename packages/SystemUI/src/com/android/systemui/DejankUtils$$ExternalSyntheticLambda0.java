package com.android.systemui;

import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
