package com.android.systemui.compose.modifiers;

import androidx.compose.ui.semantics.SemanticsPropertiesAndroid;
import androidx.compose.ui.semantics.SemanticsProperties_androidKt;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.KProperty;

/* loaded from: classes2.dex */
public final /* synthetic */ class SysuiTestTagKt$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        KProperty[] kPropertyArr = SemanticsProperties_androidKt.$$delegatedProperties;
        SemanticsPropertiesAndroid.INSTANCE.getClass();
        SemanticsPropertyKey semanticsPropertyKey = SemanticsPropertiesAndroid.TestTagsAsResourceId;
        KProperty kProperty = SemanticsProperties_androidKt.$$delegatedProperties[0];
        semanticsPropertyKey.setValue((SemanticsPropertyReceiver) obj, Boolean.TRUE);
        return Unit.INSTANCE;
    }
}
