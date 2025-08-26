package com.android.systemui.shared.clocks.view;

import android.view.View;
import com.android.systemui.shared.clocks.view.FlexClockView;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public final /* synthetic */ class FlexClockView$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        View view = (View) obj;
        FlexClockView.Companion companion = FlexClockView.Companion;
        if (view instanceof SimpleDigitalClockTextView) {
            return (SimpleDigitalClockTextView) view;
        }
        return null;
    }
}
