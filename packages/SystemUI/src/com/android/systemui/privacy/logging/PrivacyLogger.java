package com.android.systemui.privacy.logging;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.privacy.PrivacyItem;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.PropertyReference1Impl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PrivacyLogger {
    public final LogBuffer buffer;

    public PrivacyLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public static String listToString(List list) {
        return CollectionsKt___CollectionsKt.joinToString$default(list, ", ", null, null, new PropertyReference1Impl() { // from class: com.android.systemui.privacy.logging.PrivacyLogger$listToString$1
            @Override // kotlin.jvm.internal.PropertyReference1Impl, kotlin.reflect.KProperty1
            public final Object get(Object obj) {
                return ((PrivacyItem) obj).log;
            }
        }, 30);
    }
}
