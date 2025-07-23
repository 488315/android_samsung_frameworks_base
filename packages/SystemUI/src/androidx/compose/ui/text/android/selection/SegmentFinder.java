package androidx.compose.ui.text.android.selection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface SegmentFinder {
    int nextEndBoundary(int i);

    int nextStartBoundary(int i);

    int previousEndBoundary(int i);

    int previousStartBoundary(int i);
}
