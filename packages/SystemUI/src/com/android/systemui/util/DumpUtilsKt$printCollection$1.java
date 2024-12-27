package com.android.systemui.util;

import android.util.IndentingPrintWriter;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

public final /* synthetic */ class DumpUtilsKt$printCollection$1 extends FunctionReferenceImpl implements Function2 {
    public static final DumpUtilsKt$printCollection$1 INSTANCE = new DumpUtilsKt$printCollection$1();

    public DumpUtilsKt$printCollection$1() {
        super(2, IndentingPrintWriter.class, "println", "println(Ljava/lang/Object;)V", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        invoke((IndentingPrintWriter) obj, obj2);
        return Unit.INSTANCE;
    }

    public final void invoke(IndentingPrintWriter indentingPrintWriter, Object obj) {
        indentingPrintWriter.println(obj);
    }
}
