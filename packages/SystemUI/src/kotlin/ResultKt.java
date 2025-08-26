package kotlin;

import kotlin.Result;

/* loaded from: classes4.dex */
public abstract class ResultKt {
    public static final void throwOnFailure(Object obj) {
        if (obj instanceof Result.Failure) {
            throw ((Result.Failure) obj).exception;
        }
    }
}
