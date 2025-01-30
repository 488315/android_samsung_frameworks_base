package com.android.server.am;

import java.io.File;
import java.util.function.ToLongFunction;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class StackTracesDumpHelper$$ExternalSyntheticLambda1 implements ToLongFunction {
    @Override // java.util.function.ToLongFunction
    public final long applyAsLong(Object obj) {
        return ((File) obj).lastModified();
    }
}
