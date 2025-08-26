package com.android.app.tracing;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.core.MutablePreferences$$ExternalSyntheticOutline0;
import kotlin.jvm.functions.Function0;

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
        String strM;
        if (this.f$1) {
            strM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(FlowTracing.counter.addAndGet(1), "$");
        } else {
            FlowTracing flowTracing = FlowTracing.INSTANCE;
            strM = "";
        }
        return MutablePreferences$$ExternalSyntheticOutline0.m(new StringBuilder(), this.f$0, "#emissionCount", strM);
    }
}
