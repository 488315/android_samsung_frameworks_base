package com.android.p038wm.shell.draganddrop;

import android.util.Base64;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.wm.shell.draganddrop.ExecutableAppHolder$PolicyExceptionList$$ExternalSyntheticLambda0 */
/* loaded from: classes2.dex */
public final /* synthetic */ class C3968x3b842969 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        String str = (String) obj;
        return str == null ? "" : new String(Base64.decode(str, 2));
    }
}
