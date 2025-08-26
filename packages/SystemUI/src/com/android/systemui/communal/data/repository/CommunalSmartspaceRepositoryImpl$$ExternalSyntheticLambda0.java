package com.android.systemui.communal.data.repository;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.systemui.communal.data.repository.CommunalSmartspaceRepositoryImpl;
import com.android.systemui.log.core.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: classes2.dex */
public final /* synthetic */ class CommunalSmartspaceRepositoryImpl$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        CommunalSmartspaceRepositoryImpl.Companion companion = CommunalSmartspaceRepositoryImpl.Companion;
        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Smartspace timers updated: ", ((LogMessage) obj).getStr1());
    }
}
