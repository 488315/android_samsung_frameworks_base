package com.android.wm.shell.draganddrop;

import android.util.Base64;
import java.util.function.Function;

/* loaded from: classes3.dex */
public final /* synthetic */ class ExecutableAppHolder$PolicyExceptionList$$ExternalSyntheticLambda0 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        String str = (String) obj;
        return str == null ? "" : new String(Base64.decode(str, 2));
    }
}
