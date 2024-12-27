package com.android.systemui.qp;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public final /* synthetic */ class SubscreenPagedTileLayout$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ List f$0;

    public /* synthetic */ SubscreenPagedTileLayout$$ExternalSyntheticLambda0(List list, int i) {
        this.$r8$classId = i;
        this.f$0 = list;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        List list = this.f$0;
        switch (i) {
            case 0:
                int i2 = SubscreenPagedTileLayout.$r8$clinit;
                Stream map = ((SubscreenTileLayout) obj).mRecords.stream().map(new SubscreenPagedTileLayout$$ExternalSyntheticLambda1());
                Objects.requireNonNull(list);
                map.forEach(new SubscreenPagedTileLayout$$ExternalSyntheticLambda0(list, 1));
                break;
            default:
                list.add((String) obj);
                break;
        }
    }
}
