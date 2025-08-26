package androidx.compose.material3;

import androidx.compose.animation.core.AnimationSpecKt;
import androidx.compose.animation.core.SpringSpec;
import androidx.compose.material3.tokens.StandardMotionTokens;

/* loaded from: classes.dex */
public final class MotionScheme$Companion$standard$1 implements MotionScheme {
    public final SpringSpec defaultEffectsSpec;
    public final SpringSpec defaultSpatialSpec;
    public final SpringSpec fastEffectsSpec;
    public final SpringSpec fastSpatialSpec;
    public final SpringSpec slowEffectsSpec;
    public final SpringSpec slowSpatialSpec;

    public MotionScheme$Companion$standard$1() {
        StandardMotionTokens.INSTANCE.getClass();
        this.defaultSpatialSpec = AnimationSpecKt.spring$default(StandardMotionTokens.SpringDefaultSpatialDamping, StandardMotionTokens.SpringDefaultSpatialStiffness, null, 4);
        this.fastSpatialSpec = AnimationSpecKt.spring$default(StandardMotionTokens.SpringFastSpatialDamping, StandardMotionTokens.SpringFastSpatialStiffness, null, 4);
        this.slowSpatialSpec = AnimationSpecKt.spring$default(StandardMotionTokens.SpringSlowSpatialDamping, StandardMotionTokens.SpringSlowSpatialStiffness, null, 4);
        this.defaultEffectsSpec = AnimationSpecKt.spring$default(StandardMotionTokens.SpringDefaultEffectsDamping, StandardMotionTokens.SpringDefaultEffectsStiffness, null, 4);
        this.fastEffectsSpec = AnimationSpecKt.spring$default(StandardMotionTokens.SpringFastEffectsDamping, StandardMotionTokens.SpringFastEffectsStiffness, null, 4);
        this.slowEffectsSpec = AnimationSpecKt.spring$default(StandardMotionTokens.SpringSlowEffectsDamping, StandardMotionTokens.SpringSlowEffectsStiffness, null, 4);
    }
}
