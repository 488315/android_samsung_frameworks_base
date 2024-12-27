package com.android.server.appwidget;

import android.util.SizeF;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class AppWidgetXmlUtil$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return SizeF.parseSizeF((String) obj);
            default:
                return ((SizeF) obj).toString();
        }
    }
}
