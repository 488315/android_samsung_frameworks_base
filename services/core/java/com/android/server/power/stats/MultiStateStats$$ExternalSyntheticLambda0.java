package com.android.server.power.stats;

import com.android.internal.os.LongArrayMultiStateCounter;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.power.stats.MultiStateStats;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class MultiStateStats$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ MultiStateStats f$0;
    public final /* synthetic */ long[] f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ MultiStateStats$$ExternalSyntheticLambda0(MultiStateStats multiStateStats, TypedXmlSerializer typedXmlSerializer, long[] jArr) {
        this.f$0 = multiStateStats;
        this.f$2 = typedXmlSerializer;
        this.f$1 = jArr;
    }

    public /* synthetic */ MultiStateStats$$ExternalSyntheticLambda0(MultiStateStats multiStateStats, long[] jArr, StringBuilder sb) {
        this.f$0 = multiStateStats;
        this.f$1 = jArr;
        this.f$2 = sb;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                MultiStateStats multiStateStats = this.f$0;
                long[] jArr = this.f$1;
                StringBuilder sb = (StringBuilder) this.f$2;
                int[] iArr = (int[]) obj;
                LongArrayMultiStateCounter longArrayMultiStateCounter = multiStateStats.mCounter;
                MultiStateStats.Factory factory = multiStateStats.mFactory;
                longArrayMultiStateCounter.getCounts(jArr, factory.getSerialState(iArr));
                for (long j : jArr) {
                    if (j != 0) {
                        if (!sb.isEmpty()) {
                            sb.append("\n");
                        }
                        sb.append("(");
                        boolean z = true;
                        for (int i = 0; i < iArr.length; i++) {
                            MultiStateStats.States[] statesArr = factory.mStates;
                            if (statesArr[i].mTracked) {
                                if (!z) {
                                    sb.append(" ");
                                }
                                sb.append(statesArr[i].mLabels[iArr[i]]);
                                z = false;
                            }
                        }
                        sb.append(") ");
                        sb.append(Arrays.toString(jArr));
                        return;
                    }
                }
                return;
            default:
                MultiStateStats multiStateStats2 = this.f$0;
                TypedXmlSerializer typedXmlSerializer = (TypedXmlSerializer) this.f$2;
                long[] jArr2 = this.f$1;
                int[] iArr2 = (int[]) obj;
                multiStateStats2.getClass();
                try {
                    multiStateStats2.writeXmlForStates(typedXmlSerializer, iArr2, jArr2);
                    return;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
    }
}
