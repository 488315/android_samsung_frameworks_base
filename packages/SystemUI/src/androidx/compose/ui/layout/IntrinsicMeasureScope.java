package androidx.compose.ui.layout;

import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface IntrinsicMeasureScope extends Density {
    LayoutDirection getLayoutDirection();

    boolean isLookingAhead();
}
