package com.android.systemui.privacy.logging;

import com.android.systemui.privacy.PrivacyItem;
import kotlin.jvm.internal.PropertyReference1Impl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final /* synthetic */ class PrivacyLogger$listToString$1 extends PropertyReference1Impl {
    public static final PrivacyLogger$listToString$1 INSTANCE = new PrivacyLogger$listToString$1();

    public PrivacyLogger$listToString$1() {
        super(PrivacyItem.class, "log", "getLog()Ljava/lang/String;", 0);
    }

    @Override // kotlin.jvm.internal.PropertyReference1Impl, kotlin.reflect.KProperty1
    public final Object get(Object obj) {
        return ((PrivacyItem) obj).log;
    }
}
