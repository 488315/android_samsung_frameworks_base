package com.android.systemui.education.domain.interactor;

import com.android.systemui.education.data.model.GestureEduModel;
import kotlin.jvm.functions.Function2;

/* loaded from: classes2.dex */
public final /* synthetic */ class ContextualEducationInteractor$$ExternalSyntheticLambda0 implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        int i = ContextualEducationInteractor.$r8$clinit;
        return Boolean.valueOf(((GestureEduModel) obj).signalCount == ((GestureEduModel) obj2).signalCount);
    }
}
