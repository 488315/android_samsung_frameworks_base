package com.airbnb.lottie.model.animatable;

import com.airbnb.lottie.value.Keyframe;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        List list = this.keyframes;
        if (list.isEmpty()) {
            return true;
        }
        return list.size() == 1 && ((Keyframe) list.get(0)).isStatic();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        List list = this.keyframes;
        if (!list.isEmpty()) {
            sb.append("values=");
            sb.append(Arrays.toString(list.toArray()));
        }
        return sb.toString();
    }

    public BaseAnimatableValue(List<Keyframe> list) {
        this.keyframes = list;
    }
}
