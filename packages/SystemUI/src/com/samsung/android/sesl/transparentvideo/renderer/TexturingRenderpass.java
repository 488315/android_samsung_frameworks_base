package com.samsung.android.sesl.transparentvideo.renderer;

import com.samsung.android.sesl.transparentvideo.renderer.gl.Program;
import com.samsung.android.sesl.transparentvideo.renderer.gl.Texture;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class TexturingRenderpass {
    public final boolean flipY;
    public List glObj;
    public Program program;
    public Texture texture;
    public boolean useMipmapping;

    public TexturingRenderpass() {
        this(false, 1, null);
    }

    public TexturingRenderpass(boolean z) {
        this.flipY = z;
    }

    public /* synthetic */ TexturingRenderpass(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? true : z);
    }
}
