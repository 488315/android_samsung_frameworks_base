package com.android.systemui.statusbar.connectivity;

import com.android.systemui.log.LogMessage;
import java.util.Locale;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NetworkControllerImpl$$ExternalSyntheticLambda3 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        return String.format(Locale.US, "Received broadcast with action \"%s\"", ((LogMessage) obj).getStr1());
    }
}
