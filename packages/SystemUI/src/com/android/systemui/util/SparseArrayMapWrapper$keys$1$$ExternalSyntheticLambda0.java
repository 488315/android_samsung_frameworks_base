package com.android.systemui.util;

import com.android.systemui.util.SparseArrayMapWrapper;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class SparseArrayMapWrapper$keys$1$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ SparseArrayMapWrapper$keys$1$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        int keySequence$lambda$0;
        Object value;
        SparseArrayMapWrapper.Entry entry = (SparseArrayMapWrapper.Entry) obj;
        switch (this.$r8$classId) {
            case 0:
                keySequence$lambda$0 = SparseArrayMapWrapper$keys$1.keySequence$lambda$0(entry);
                return Integer.valueOf(keySequence$lambda$0);
            default:
                value = entry.getValue();
                return value;
        }
    }
}
