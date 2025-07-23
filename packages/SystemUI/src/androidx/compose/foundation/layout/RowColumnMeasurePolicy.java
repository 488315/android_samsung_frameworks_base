package androidx.compose.foundation.layout;

import androidx.compose.ui.layout.MeasureResult;
import androidx.compose.ui.layout.MeasureScope;
import androidx.compose.ui.layout.Placeable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface RowColumnMeasurePolicy {
    /* renamed from: createConstraints-xF2OJ5Q */
    long mo102createConstraintsxF2OJ5Q(boolean z, int i, int i2, int i3, int i4);

    int crossAxisSize(Placeable placeable);

    int mainAxisSize(Placeable placeable);

    MeasureResult placeHelper(Placeable[] placeableArr, MeasureScope measureScope, int[] iArr, int i, int i2, int[] iArr2, int i3, int i4, int i5);

    void populateMainAxisPositions(int i, int[] iArr, int[] iArr2, MeasureScope measureScope);
}
