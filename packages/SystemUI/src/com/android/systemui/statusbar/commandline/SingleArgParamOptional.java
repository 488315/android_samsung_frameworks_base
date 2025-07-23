package com.android.systemui.statusbar.commandline;

import java.util.Iterator;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.KProperty;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SingleArgParamOptional extends UnaryParamBase {
    public final String description;
    public final String longName;
    public final String shortName;
    public final ValueParser valueParser;

    public /* synthetic */ SingleArgParamOptional(String str, String str2, String str3, ValueParser valueParser, int i, DefaultConstructorMarker defaultConstructorMarker) {
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

    @Override // kotlin.properties.ReadOnlyProperty
    public final Object getValue(Object obj, KProperty kProperty) {
        return CollectionsKt___CollectionsKt.getOrNull(0, this.wrapped.inner);
    }

    @Override // com.android.systemui.statusbar.commandline.Param
    public final void parseArgsFromIter(Iterator it) {
        this.wrapped.parseArgsFromIter(it);
        this.handled = true;
    }

    public SingleArgParamOptional(String str, String str2, String str3, ValueParser valueParser) {
        super(new MultipleArgParam(str, str2, 1, str3, valueParser));
        this.longName = str;
        this.shortName = str2;
        this.description = str3;
        this.valueParser = valueParser;
    }
}
