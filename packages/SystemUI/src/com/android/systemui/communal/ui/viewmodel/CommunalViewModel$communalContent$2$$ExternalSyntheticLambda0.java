package com.android.systemui.communal.ui.viewmodel;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.systemui.communal.domain.model.CommunalContentModel;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalViewModel$communalContent$2$$ExternalSyntheticLambda0 implements Function1 {
    public final /* synthetic */ int $r8$classId;

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("CommunalContent: ", ((LogMessage) obj).getStr1());
            case 1:
                return ((CommunalContentModel) obj).getKey();
            case 2:
                return KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("_isMediaHostVisible: ", ((LogMessage) obj).getBool1());
            case 3:
                return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Content updated: ", ((LogMessage) obj).getStr1());
            default:
                return ((CommunalContentModel) obj).getKey();
        }
    }
}
