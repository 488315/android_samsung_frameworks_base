package androidx.compose.animation.graphics.vector;

import java.util.List;

/* loaded from: classes.dex */
public final class PropertyValuesHolderFloat extends PropertyValuesHolder1D<Float> {
    public final List animatorKeyframes;

    public PropertyValuesHolderFloat(String str, List<Keyframe<Float>> list) {
        super(str, null);
        this.animatorKeyframes = list;
    }
}
