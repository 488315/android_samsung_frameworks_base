package androidx.compose.ui.semantics;

/* loaded from: classes.dex */
public final class CollectionItemInfo {
    public final int columnIndex;
    public final int columnSpan;
    public final int rowIndex;
    public final int rowSpan;

    public CollectionItemInfo(int i, int i2, int i3, int i4) {
        this.rowIndex = i;
        this.rowSpan = i2;
        this.columnIndex = i3;
        this.columnSpan = i4;
    }
}
