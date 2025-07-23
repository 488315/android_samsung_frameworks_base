package com.android.systemui.mediaprojection.appselector.data;

import java.util.List;
import java.util.function.Consumer;
import kotlin.Result;
import kotlin.coroutines.Continuation;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ShellRecentTaskListProvider$getTasks$2$1 implements Consumer {
    public final /* synthetic */ Continuation $continuation;

    public ShellRecentTaskListProvider$getTasks$2$1(Continuation continuation) {
        this.$continuation = continuation;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Continuation continuation = this.$continuation;
        int i = Result.$r8$clinit;
        continuation.resumeWith((List) obj);
    }
}
