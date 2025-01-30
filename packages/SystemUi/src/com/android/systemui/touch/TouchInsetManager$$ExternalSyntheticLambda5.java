package com.android.systemui.touch;

import android.graphics.Region;
import android.view.AttachedSurfaceControl;
import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class TouchInsetManager$$ExternalSyntheticLambda5 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Map.Entry entry = (Map.Entry) obj;
        ((AttachedSurfaceControl) entry.getKey()).setTouchableRegion((Region) entry.getValue());
    }
}
