package com.android.wm.shell.compatui.letterbox;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final /* synthetic */ class LetterboxCommandHandler$onSingleParamCommand$4 extends FunctionReferenceImpl implements Function1 {
    public LetterboxCommandHandler$onSingleParamCommand$4(Object obj) {
        super(1, obj, LetterboxCommandHandler.class, "nameToColorId", "nameToColorId(Ljava/lang/String;)Ljava/lang/Integer;", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        String str = (String) obj;
        LetterboxCommandHandler letterboxCommandHandler = (LetterboxCommandHandler) this.receiver;
        int i = LetterboxCommandHandler.$r8$clinit;
        letterboxCommandHandler.getClass();
        try {
            return Integer.valueOf(letterboxCommandHandler.context.getResources().getIdentifier(str, "color", "com.android.internal"));
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }
}
