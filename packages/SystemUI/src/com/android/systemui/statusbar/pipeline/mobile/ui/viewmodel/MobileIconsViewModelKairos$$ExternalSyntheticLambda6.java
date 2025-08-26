package com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel;

import com.android.systemui.kairos.StateInit;
import com.android.systemui.kairos.StateKt;
import java.util.Map;
import kotlin.jvm.functions.Function3;

/* loaded from: classes3.dex */
public final /* synthetic */ class MobileIconsViewModelKairos$$ExternalSyntheticLambda6 implements Function3 {
    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        StateInit stateInit;
        MobileIconViewModelKairos mobileIconViewModelKairos = (MobileIconViewModelKairos) ((Map) obj3).get((Integer) obj2);
        return (mobileIconViewModelKairos == null || (stateInit = mobileIconViewModelKairos.networkTypeIcon) == null) ? StateKt.stateOf(Boolean.FALSE) : StateKt.map(stateInit, new MobileIconsViewModelKairos$$ExternalSyntheticLambda0(3));
    }
}
