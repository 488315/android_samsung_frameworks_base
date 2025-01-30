package com.android.systemui.qs.customize;

import com.android.systemui.qs.customize.SecQSCustomizerBase;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class CustomizerTileViewPager$$ExternalSyntheticLambda1 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = CustomizerTileViewPager.$r8$clinit;
        ((SecQSCustomizerBase.CustomTileInfo) obj).customTileView.requestAccessibilityFocus();
    }
}
