package com.android.systemui.statusbar.commandline;

import android.util.IndentingPrintWriter;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

public interface Describable {
    default void describe(final IndentingPrintWriter indentingPrintWriter) {
        if (getShortName() != null) {
            indentingPrintWriter.print(getShortName() + ", ");
        }
        indentingPrintWriter.print(getLongName());
        indentingPrintWriter.println();
        if (getDescription() != null) {
            ParseableCommandKt.indented(indentingPrintWriter, new Function0() { // from class: com.android.systemui.statusbar.commandline.Describable$describe$1
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

    String getDescription();

    String getLongName();

    String getShortName();

    default boolean matches(String str) {
        return Intrinsics.areEqual(getShortName(), str) || Intrinsics.areEqual(getLongName(), str);
    }
}
