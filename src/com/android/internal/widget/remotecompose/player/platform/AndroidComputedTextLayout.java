package com.android.internal.widget.remotecompose.player.platform;

import android.text.StaticLayout;
import com.android.internal.widget.remotecompose.core.Platform;

/* loaded from: classes6.dex */
public class AndroidComputedTextLayout implements Platform.ComputedTextLayout {
    float mHeight;
    StaticLayout mStaticLayout;
    float mWidth;

    public AndroidComputedTextLayout(StaticLayout staticLayout, float f, float f2) {
        this.mStaticLayout = staticLayout;
        this.mWidth = f;
        this.mHeight = f2;
    }

    public void set(StaticLayout staticLayout) {
        this.mStaticLayout = staticLayout;
    }

    public StaticLayout get() {
        return this.mStaticLayout;
    }

    @Override // com.android.internal.widget.remotecompose.core.Platform.ComputedTextLayout
    public float getWidth() {
        return this.mWidth;
    }

    @Override // com.android.internal.widget.remotecompose.core.Platform.ComputedTextLayout
    public float getHeight() {
        return this.mHeight;
    }
}
