package com.samsung.android.sdk.scs.ai.language;

import android.os.Bundle;
import com.samsung.android.sdk.scs.ai.language.service.LlmServiceRunnable$$ExternalSyntheticLambda0;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final /* synthetic */ class SmartReplyer$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        List list = (List) obj;
        switch (this.$r8$classId) {
            case 0:
                return list.isEmpty() ? new Result(new Bundle()) : new Result((Bundle) list.get(0));
            default:
                return (List) list.stream().map(new LlmServiceRunnable$$ExternalSyntheticLambda0()).collect(Collectors.toList());
        }
    }
}
