package com.android.systemui.theme;

import android.content.om.OverlayInfo;
import android.util.Pair;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ThemeOverlayApplier$$ExternalSyntheticLambda5 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        OverlayInfo overlayInfo = (OverlayInfo) obj;
        return new Pair(overlayInfo.category, overlayInfo.packageName);
    }
}
