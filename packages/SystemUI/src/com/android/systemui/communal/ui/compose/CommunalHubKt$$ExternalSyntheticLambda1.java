package com.android.systemui.communal.ui.compose;

import androidx.compose.ui.semantics.SemanticsConfiguration;
import androidx.compose.ui.semantics.SemanticsProperties;
import androidx.compose.ui.semantics.SemanticsPropertiesAndroid;
import androidx.compose.ui.semantics.SemanticsPropertiesKt;
import androidx.compose.ui.semantics.SemanticsProperties_androidKt;
import androidx.compose.ui.semantics.SemanticsPropertyKey;
import androidx.compose.ui.semantics.SemanticsPropertyReceiver;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.KProperty;

/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalHubKt$$ExternalSyntheticLambda1 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;

    public /* synthetic */ CommunalHubKt$$ExternalSyntheticLambda1(String str, int i) {
        this.$r8$classId = i;
        this.f$0 = str;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        String str = this.f$0;
        SemanticsPropertyReceiver semanticsPropertyReceiver = (SemanticsPropertyReceiver) obj;
        switch (this.$r8$classId) {
            case 0:
                KProperty[] kPropertyArr = SemanticsProperties_androidKt.$$delegatedProperties;
                SemanticsPropertiesAndroid.INSTANCE.getClass();
                SemanticsPropertyKey semanticsPropertyKey = SemanticsPropertiesAndroid.TestTagsAsResourceId;
                KProperty kProperty = SemanticsProperties_androidKt.$$delegatedProperties[0];
                semanticsPropertyKey.setValue(semanticsPropertyReceiver, Boolean.TRUE);
                SemanticsPropertiesKt.setPaneTitle(semanticsPropertyReceiver, str);
                return Unit.INSTANCE;
            default:
                SemanticsPropertiesKt.setContentDescription(semanticsPropertyReceiver, str);
                SemanticsProperties.INSTANCE.getClass();
                SemanticsPropertyKey semanticsPropertyKey2 = SemanticsProperties.Heading;
                Unit unit = Unit.INSTANCE;
                ((SemanticsConfiguration) semanticsPropertyReceiver).set(semanticsPropertyKey2, unit);
                return unit;
        }
    }
}
