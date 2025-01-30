package com.android.systemui.monet;

import com.android.internal.graphics.cam.Cam;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class HueSource implements Hue {
    @Override // com.android.systemui.monet.Hue
    public final double get(Cam cam) {
        return cam.getHue();
    }
}
