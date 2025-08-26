package com.android.systemui.util;

import com.android.systemui.util.SparseArrayMapWrapper;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class SparseArrayMapWrapper$keys$1$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ SparseArrayMapWrapper$keys$1$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        SparseArrayMapWrapper.Entry entry = (SparseArrayMapWrapper.Entry) obj;
        switch (this.$r8$classId) {
            case 0:
                return Integer.valueOf(SparseArrayMapWrapper$keys$1.keySequence$lambda$0(entry));
            default:
                return entry.getValue();
        }
    }
}
