package com.android.systemui.statusbar.commandline;

import android.util.IndentingPrintWriter;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface Describable {
    default void describe(final IndentingPrintWriter indentingPrintWriter) {
        if (getShortName() != null) {
            indentingPrintWriter.print(getShortName() + ", ");
        }
        indentingPrintWriter.print(getLongName());
        indentingPrintWriter.println();
        if (getDescription() != null) {
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.commandline.Describable$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    indentingPrintWriter.println(this.getDescription());
                    return Unit.INSTANCE;
                }
            };
            indentingPrintWriter.increaseIndent();
            function0.invoke();
            indentingPrintWriter.decreaseIndent();
        }
    }

    String getDescription();

    String getLongName();

    String getShortName();

    default boolean matches(String str) {
        return Intrinsics.areEqual(getShortName(), str) || Intrinsics.areEqual(getLongName(), str);
    }
}
