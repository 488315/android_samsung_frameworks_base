package com.android.systemui.statusbar.commandline;

import android.util.IndentingPrintWriter;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.properties.ReadOnlyProperty;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class UnaryParamBase implements Param, ReadOnlyProperty {
    public boolean handled;
    public final MultipleArgParam wrapped;

    public UnaryParamBase(MultipleArgParam multipleArgParam) {
        this.wrapped = multipleArgParam;
    }

    @Override // com.android.systemui.statusbar.commandline.Describable
    public final void describe(IndentingPrintWriter indentingPrintWriter) {
        if (getShortName() != null) {
            indentingPrintWriter.print(getShortName() + ", ");
        }
        indentingPrintWriter.print(getLongName());
        ValueParser valueParser = this.wrapped.valueParser;
        Type.INSTANCE.getClass();
        indentingPrintWriter.println(" ".concat(Intrinsics.areEqual(valueParser, Type.Int) ? "<int>" : Intrinsics.areEqual(valueParser, Type.Float) ? "<float>" : Intrinsics.areEqual(valueParser, Type.String) ? "<string>" : Intrinsics.areEqual(valueParser, Type.Boolean) ? "<boolean>" : "<arg>"));
        if (getDescription() != null) {
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println(getDescription());
            Unit unit = Unit.INSTANCE;
            indentingPrintWriter.decreaseIndent();
        }
    }
}
