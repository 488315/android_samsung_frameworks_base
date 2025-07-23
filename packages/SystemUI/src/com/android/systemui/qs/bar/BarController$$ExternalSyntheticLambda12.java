package com.android.systemui.qs.bar;

import android.content.res.Configuration;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class BarController$$ExternalSyntheticLambda12 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ BarController$$ExternalSyntheticLambda12(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ((ArrayList) obj2).add((String) obj);
                break;
            default:
                ((BarItemImpl) obj).onConfigChanged((Configuration) obj2);
                break;
        }
    }
}
