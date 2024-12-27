package com.android.server.inputmethod;

import com.android.server.DssController$$ExternalSyntheticThrowCCEIfNotNull0;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class InputMethodManagerService$$ExternalSyntheticLambda8
        implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ InputMethodManagerService$$ExternalSyntheticLambda8(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                DssController$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
                throw null;
            case 1:
                ((ClientState) obj).mClient.setImeTraceEnabled(false);
                return;
            default:
                ((ClientState) obj).mClient.setImeTraceEnabled(true);
                return;
        }
    }
}
