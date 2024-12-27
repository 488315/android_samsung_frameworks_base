package com.android.systemui.statusbar.commandline;

import android.util.IndentingPrintWriter;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class UnaryParamBase implements Param {
    public boolean handled;
    public final MultipleArgParam wrapped;

    public UnaryParamBase(MultipleArgParam multipleArgParam) {
        this.wrapped = multipleArgParam;
    }

    @Override // com.android.systemui.statusbar.commandline.Describable
    public final void describe(final IndentingPrintWriter indentingPrintWriter) {
        if (getShortName() != null) {
            indentingPrintWriter.print(getShortName() + ", ");
        }
        indentingPrintWriter.print(getLongName());
        ValueParser valueParser = this.wrapped.valueParser;
        Type.INSTANCE.getClass();
        indentingPrintWriter.println(" ".concat(Intrinsics.areEqual(valueParser, Type.Int) ? "<int>" : Intrinsics.areEqual(valueParser, Type.Float) ? "<float>" : Intrinsics.areEqual(valueParser, Type.String) ? "<string>" : Intrinsics.areEqual(valueParser, Type.Boolean) ? "<boolean>" : "<arg>"));
        if (getDescription() != null) {
            ParseableCommandKt.indented(indentingPrintWriter, new Function0() { // from class: com.android.systemui.statusbar.commandline.UnaryParamBase$describe$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    indentingPrintWriter.println(this.getDescription());
                    return Unit.INSTANCE;
                }
            });
        }
    }
}
