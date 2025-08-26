package com.android.systemui.privacy.logging;

import com.android.systemui.log.LogBuffer;
import com.android.systemui.privacy.PrivacyItem;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.PropertyReference1Impl;

/* loaded from: classes2.dex */
public final class PrivacyLogger {
    public final LogBuffer buffer;

    public PrivacyLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public static String listToString(List list) {
        return CollectionsKt___CollectionsKt.joinToString$default(list, ", ", null, null, new PropertyReference1Impl() { // from class: com.android.systemui.privacy.logging.PrivacyLogger.listToString.1
            @Override // kotlin.jvm.internal.PropertyReference1Impl, kotlin.reflect.KProperty1
            public final Object get(Object obj) {
                return ((PrivacyItem) obj).log;
            }
        }, 30);
    }
}
