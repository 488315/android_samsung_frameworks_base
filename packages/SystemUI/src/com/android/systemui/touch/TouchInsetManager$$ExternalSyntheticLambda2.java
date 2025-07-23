package com.android.systemui.touch;

import android.graphics.Region;
import android.view.AttachedSurfaceControl;
import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class TouchInsetManager$$ExternalSyntheticLambda2 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Map.Entry entry = (Map.Entry) obj;
        ((AttachedSurfaceControl) entry.getKey()).setTouchableRegion((Region) entry.getValue());
    }
}
