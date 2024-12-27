package com.android.server.notification;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.scs.ai.text.category.DocumentCategory;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationClassifier$$ExternalSyntheticLambda0 implements Predicate {
    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        DocumentCategory documentCategory = (DocumentCategory) obj;
        BatteryService$$ExternalSyntheticOutline0.m(new StringBuilder("result : ID = "), documentCategory.categoryId, "NotificationClassifier");
        return documentCategory.categoryId == 1;
    }
}
