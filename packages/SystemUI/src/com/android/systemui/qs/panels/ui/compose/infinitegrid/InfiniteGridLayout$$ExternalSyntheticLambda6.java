package com.android.systemui.qs.panels.ui.compose.infinitegrid;

import androidx.compose.runtime.State;
import com.android.systemui.qs.panels.shared.model.SizedTileImpl;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class InfiniteGridLayout$$ExternalSyntheticLambda6 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ InfiniteGridLayout$$ExternalSyntheticLambda6(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                List list = (List) this.f$0;
                ArrayList arrayList = new ArrayList(list.size());
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    arrayList.add(Integer.valueOf(((SizedTileImpl) list.get(i)).width));
                }
                return arrayList;
            default:
                return Float.valueOf(((Number) ((State) this.f$0).getValue()).floatValue());
        }
    }
}
