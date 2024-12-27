package com.android.systemui.statusbar.commandline;

import java.util.ArrayList;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SingleArgParam extends UnaryParamBase {
    public final String description;
    public final String longName;
    public final String shortName;

    public /* synthetic */ SingleArgParam(String str, String str2, String str3, ValueParser valueParser, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : str3, valueParser);
    }

    @Override // com.android.systemui.statusbar.commandline.Describable
    public final String getDescription() {
        return this.description;
    }

    @Override // com.android.systemui.statusbar.commandline.Describable
    public final String getLongName() {
        return this.longName;
    }

    @Override // com.android.systemui.statusbar.commandline.Describable
    public final String getShortName() {
        return this.shortName;
    }

    public final Object getValue() {
        if (this.handled) {
            return ((ArrayList) this.wrapped.inner).get(0);
        }
        throw new IllegalStateException("Attempt to read property before parse() has executed");
    }

    @Override // com.android.systemui.statusbar.commandline.Param
    public final void parseArgsFromIter(Iterator it) {
        this.wrapped.parseArgsFromIter(it);
        this.handled = true;
    }

    public SingleArgParam(String str, String str2, String str3, ValueParser valueParser) {
        super(new MultipleArgParam(str, str2, 1, str3, valueParser));
        this.longName = str;
        this.shortName = str2;
        this.description = str3;
    }
}
