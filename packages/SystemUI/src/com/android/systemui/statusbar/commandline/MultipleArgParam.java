package com.android.systemui.statusbar.commandline;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Result;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class MultipleArgParam implements Param {
    public final String description;
    public final List inner;
    public final String longName;
    public final int numArgs;
    public final String shortName;
    public final ValueParser valueParser;

    public MultipleArgParam(String str, String str2, int i, String str3, ValueParser valueParser) {
        this.longName = str;
        this.shortName = str2;
        this.numArgs = i;
        this.description = str3;
        this.valueParser = valueParser;
        this.inner = new ArrayList();
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

    @Override // com.android.systemui.statusbar.commandline.Param
    public final void parseArgsFromIter(Iterator it) {
        if (!it.hasNext()) {
            throw new ArgParseError("no argument provided for " + this.shortName);
        }
        for (int i = 0; i < this.numArgs; i++) {
            Object mo1930parseValueIoAF18A = this.valueParser.mo1930parseValueIoAF18A((String) it.next());
            Throwable m2527exceptionOrNullimpl = Result.m2527exceptionOrNullimpl(mo1930parseValueIoAF18A);
            if (m2527exceptionOrNullimpl != null) {
                throw m2527exceptionOrNullimpl;
            }
            ((ArrayList) this.inner).add(mo1930parseValueIoAF18A);
        }
    }

    public /* synthetic */ MultipleArgParam(String str, String str2, int i, String str3, ValueParser valueParser, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i2 & 2) != 0 ? null : str2, (i2 & 4) != 0 ? 1 : i, (i2 & 8) != 0 ? null : str3, valueParser);
    }
}
