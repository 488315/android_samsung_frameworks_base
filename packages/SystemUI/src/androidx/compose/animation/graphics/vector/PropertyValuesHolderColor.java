package androidx.compose.animation.graphics.vector;

import androidx.compose.ui.graphics.Color;
import java.util.List;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PropertyValuesHolderColor extends PropertyValuesHolder1D<Color> {
    public final List animatorKeyframes;

    public PropertyValuesHolderColor(String str, List<Keyframe<Color>> list) {
        super(str, null);
        this.animatorKeyframes = list;
    }
}
