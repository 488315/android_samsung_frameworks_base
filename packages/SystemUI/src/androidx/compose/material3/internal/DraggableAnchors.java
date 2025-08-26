package androidx.compose.material3.internal;

import androidx.compose.material3.SheetValue;

/* loaded from: classes.dex */
public interface DraggableAnchors<T> {
    boolean hasAnchorFor(SheetValue sheetValue);

    float minAnchor();

    float positionOf(Object obj);
}
