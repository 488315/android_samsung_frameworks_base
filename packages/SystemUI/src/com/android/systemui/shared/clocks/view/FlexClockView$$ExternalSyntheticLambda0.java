package com.android.systemui.shared.clocks.view;

import android.view.View;
import com.android.systemui.shared.clocks.view.FlexClockView;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class FlexClockView$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        View view = (View) obj;
        FlexClockView.Companion companion = FlexClockView.Companion;
        if (view instanceof SimpleDigitalClockTextView) {
            return (SimpleDigitalClockTextView) view;
        }
        return null;
    }
}
