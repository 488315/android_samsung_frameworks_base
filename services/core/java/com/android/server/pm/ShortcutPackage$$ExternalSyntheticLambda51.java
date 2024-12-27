package com.android.server.pm;

import android.app.appsearch.SearchResult;
import android.content.pm.ShortcutInfo;

import java.util.ArrayList;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutPackage$$ExternalSyntheticLambda51 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return new ArrayList();
            case 1:
                return ((SearchResult) obj).getGenericDocument();
            default:
                return ((ShortcutInfo) obj).getId();
        }
    }
}
