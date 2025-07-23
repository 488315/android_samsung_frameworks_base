package androidx.compose.ui.graphics.vector;

import androidx.compose.ui.graphics.BlendMode;
import androidx.compose.ui.graphics.BlendModeColorFilter;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorFilter;
import androidx.compose.ui.graphics.PathFillType;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.StrokeJoin;
import kotlin.collections.EmptyList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class VectorKt {
    public static final EmptyList EmptyPath = EmptyList.INSTANCE;

    static {
        StrokeCap.Companion.getClass();
        StrokeJoin.Companion.getClass();
        BlendMode.Companion.getClass();
        Color.Companion.getClass();
        PathFillType.Companion.getClass();
    }

    public static final boolean tintableWithAlphaMask(ColorFilter colorFilter) {
        if (!(colorFilter instanceof BlendModeColorFilter)) {
            return colorFilter == null;
        }
        BlendModeColorFilter blendModeColorFilter = (BlendModeColorFilter) colorFilter;
        int i = blendModeColorFilter.blendMode;
        BlendMode.Companion companion = BlendMode.Companion;
        companion.getClass();
        if (i != BlendMode.SrcIn) {
            int i2 = blendModeColorFilter.blendMode;
            companion.getClass();
            if (i2 != BlendMode.SrcOver) {
                return false;
            }
        }
        return true;
    }
}
