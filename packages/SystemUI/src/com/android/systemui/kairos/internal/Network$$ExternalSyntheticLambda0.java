package com.android.systemui.kairos.internal;

import kotlin.collections.ArrayDeque;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class Network$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ Network$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        boolean z;
        boolean z2;
        switch (this.$r8$classId) {
            case 0:
                MuxNode muxNode = (MuxNode) obj;
                if (muxNode.markedForCompaction) {
                    z = false;
                } else {
                    z = true;
                    muxNode.markedForCompaction = true;
                }
                return Boolean.valueOf(z);
            case 1:
                MuxNode muxNode2 = (MuxNode) obj;
                if (muxNode2.markedForEvaluation) {
                    z2 = false;
                } else {
                    z2 = true;
                    muxNode2.markedForEvaluation = true;
                }
                return Boolean.valueOf(z2);
            default:
                return new ArrayDeque();
        }
    }
}
