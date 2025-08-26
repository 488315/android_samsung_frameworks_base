package com.airbnb.lottie.model.animatable;

import com.airbnb.lottie.value.Keyframe;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public abstract class BaseAnimatableValue implements AnimatableValue {
    public final List keyframes;

    public BaseAnimatableValue(Object obj) {
        this((List<Keyframe>) Collections.singletonList(new Keyframe(obj)));
    }

    @Override // com.airbnb.lottie.model.animatable.AnimatableValue
    public final List getKeyframes() {
        return this.keyframes;
    }

    @Override // com.airbnb.lottie.model.animatable.AnimatableValue
    public final boolean isStatic() {
        return this.keyframes.isEmpty() || (this.keyframes.size() == 1 && ((Keyframe) this.keyframes.get(0)).isStatic());
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        if (!this.keyframes.isEmpty()) {
            sb.append("values=");
            sb.append(Arrays.toString(this.keyframes.toArray()));
        }
        return sb.toString();
    }

    public BaseAnimatableValue(List<Keyframe> list) {
        this.keyframes = list;
    }
}
