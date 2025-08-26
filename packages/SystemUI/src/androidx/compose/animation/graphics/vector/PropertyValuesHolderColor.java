package androidx.compose.animation.graphics.vector;

import androidx.compose.ui.graphics.Color;
import java.util.List;

/* loaded from: classes.dex */
public final class PropertyValuesHolderColor extends PropertyValuesHolder1D<Color> {
    public final List animatorKeyframes;

    public PropertyValuesHolderColor(String str, List<Keyframe<Color>> list) {
        super(str, null);
        this.animatorKeyframes = list;
    }
}
