package com.android.wm.shell.compatui.letterbox;

import android.graphics.Color;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: classes3.dex */
final /* synthetic */ class LetterboxCommandHandler$onSingleParamCommand$1 extends FunctionReferenceImpl implements Function1 {
    public LetterboxCommandHandler$onSingleParamCommand$1(Object obj) {
        super(1, obj, LetterboxCommandHandler.class, "strToColor", "strToColor(Ljava/lang/String;)Landroid/graphics/Color;", 0);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        String str = (String) obj;
        LetterboxCommandHandler letterboxCommandHandler = (LetterboxCommandHandler) this.receiver;
        int i = LetterboxCommandHandler.$r8$clinit;
        letterboxCommandHandler.getClass();
        try {
            return Color.valueOf(Color.parseColor(str));
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }
}
