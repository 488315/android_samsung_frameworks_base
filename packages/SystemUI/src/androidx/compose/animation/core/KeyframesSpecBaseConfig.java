package androidx.compose.animation.core;

import androidx.collection.IntObjectMapKt;
import androidx.collection.MutableIntObjectMap;
import androidx.compose.animation.core.KeyframeBaseEntity;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class KeyframesSpecBaseConfig<T, E extends KeyframeBaseEntity<T>> {
    public int durationMillis;
    public final MutableIntObjectMap keyframes;

    public /* synthetic */ KeyframesSpecBaseConfig(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private KeyframesSpecBaseConfig() {
        this.durationMillis = 300;
        this.keyframes = IntObjectMapKt.mutableIntObjectMapOf();
    }
}
