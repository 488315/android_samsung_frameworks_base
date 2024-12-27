package com.android.systemui.qs.bar;

import android.content.res.Configuration;
import java.util.ArrayList;
import java.util.function.Consumer;

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
