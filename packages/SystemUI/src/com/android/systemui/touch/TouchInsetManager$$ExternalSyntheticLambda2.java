package com.android.systemui.touch;

import android.graphics.Region;
import android.view.AttachedSurfaceControl;
import java.util.Map;
import java.util.function.Consumer;

public final /* synthetic */ class TouchInsetManager$$ExternalSyntheticLambda2 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Map.Entry entry = (Map.Entry) obj;
        ((AttachedSurfaceControl) entry.getKey()).setTouchableRegion((Region) entry.getValue());
    }
}
