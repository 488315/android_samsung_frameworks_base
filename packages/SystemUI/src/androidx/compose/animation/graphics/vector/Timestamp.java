package androidx.compose.animation.graphics.vector;

import androidx.compose.animation.core.RepeatMode;

/* loaded from: classes.dex */
public final class Timestamp<T> {
    public final int durationMillis;
    public final PropertyValuesHolder holder;
    public final int repeatCount;
    public final RepeatMode repeatMode;
    public final int timeMillis;

    public Timestamp(int i, int i2, int i3, RepeatMode repeatMode, PropertyValuesHolder<T> propertyValuesHolder) {
        this.timeMillis = i;
        this.durationMillis = i2;
        this.repeatCount = i3;
        this.repeatMode = repeatMode;
        this.holder = propertyValuesHolder;
    }
}
