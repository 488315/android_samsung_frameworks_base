package androidx.compose.material3;

import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorProducer;
import kotlin.Function;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionAdapter;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final /* synthetic */ class SearchBarKt$sam$androidx_compose_ui_graphics_ColorProducer$0 implements ColorProducer, FunctionAdapter {
    public final /* synthetic */ Function0 function;

    public SearchBarKt$sam$androidx_compose_ui_graphics_ColorProducer$0(Function0 function0) {
        this.function = function0;
    }

    public final boolean equals(Object obj) {
        if ((obj instanceof ColorProducer) && (obj instanceof FunctionAdapter)) {
            return Intrinsics.areEqual(getFunctionDelegate(), ((FunctionAdapter) obj).getFunctionDelegate());
        }
        return false;
    }

    @Override // kotlin.jvm.internal.FunctionAdapter
    public final Function getFunctionDelegate() {
        return this.function;
    }

    public final int hashCode() {
        return getFunctionDelegate().hashCode();
    }

    @Override // androidx.compose.ui.graphics.ColorProducer
    /* renamed from: invoke-0d7_KjU */
    public final /* synthetic */ long mo261invoke0d7_KjU() {
        return ((Color) this.function.invoke()).value;
    }
}
