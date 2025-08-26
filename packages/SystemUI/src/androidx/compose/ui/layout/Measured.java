package androidx.compose.ui.layout;

/* loaded from: classes.dex */
public interface Measured {
    int get(AlignmentLine alignmentLine);

    default Object getParentData() {
        return null;
    }
}
