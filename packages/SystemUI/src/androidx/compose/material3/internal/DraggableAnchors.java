package androidx.compose.material3.internal;

import androidx.compose.material3.SheetValue;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface DraggableAnchors<T> {
    boolean hasAnchorFor(SheetValue sheetValue);

    float minAnchor();

    float positionOf(Object obj);
}
