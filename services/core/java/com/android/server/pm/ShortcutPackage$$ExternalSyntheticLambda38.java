package com.android.server.pm;

import android.app.appsearch.GenericDocument;
import android.content.pm.ShortcutInfo;
import android.util.ArrayMap;

import java.util.function.Consumer;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutPackage$$ExternalSyntheticLambda38 implements Function {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ShortcutPackage$$ExternalSyntheticLambda38(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ((Consumer) obj2).accept((ShortcutInfo) obj);
                return Boolean.FALSE;
            case 1:
                return (ShortcutInfo) ((ArrayMap) obj2).get((String) obj);
            default:
                return ShortcutInfo.createFromGenericDocument(
                        ShortcutPackage.this.mShortcutUser.mUserId, (GenericDocument) obj);
        }
    }
}
