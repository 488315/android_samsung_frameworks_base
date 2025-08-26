package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import com.android.systemui.kairos.StateInit;
import com.android.systemui.kairos.StateKt;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function2;

/* loaded from: classes3.dex */
public final /* synthetic */ class StackedMobileIconViewModelKairos$$ExternalSyntheticLambda2 implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        StateInit stateInit;
        MobileIconViewModelKairos mobileIconViewModelKairos = (MobileIconViewModelKairos) CollectionsKt___CollectionsKt.firstOrNull((List) obj2);
        return (mobileIconViewModelKairos == null || (stateInit = mobileIconViewModelKairos.networkTypeIcon) == null) ? StateKt.stateOf(null) : stateInit;
    }
}
