package com.samsung.android.graphics.spr.document.attribute;

import com.samsung.android.graphics.spr.document.SprInputStream;
import com.samsung.android.graphics.spr.document.attribute.impl.SprGradientBase;
import java.io.IOException;

/* loaded from: classes6.dex */
public class SprAttributeFill extends SprAttributeColor {
    public SprAttributeFill() {
        super((byte) 32);
    }

    public SprAttributeFill(byte type, int value) {
        super((byte) 32, type, value);
    }

    public SprAttributeFill(byte type, SprGradientBase gradient) {
        super((byte) 32, type, gradient);
    }

    public SprAttributeFill(SprInputStream in) throws IOException {
        super((byte) 32, in);
    }
}
