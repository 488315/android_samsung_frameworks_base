package com.android.app.tracing;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.core.MutablePreferences$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class FlowTracing$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ String f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ FlowTracing$$ExternalSyntheticLambda0(String str, boolean z) {
        this.f$0 = str;
        this.f$1 = z;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        String str;
        if (this.f$1) {
            str = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(FlowTracing.counter.addAndGet(1), "$");
        } else {
            FlowTracing flowTracing = FlowTracing.INSTANCE;
            str = "";
        }
        return MutablePreferences$$ExternalSyntheticOutline0.m(new StringBuilder(), this.f$0, "#emissionCount", str);
    }
}
