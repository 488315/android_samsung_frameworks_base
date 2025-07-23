package androidx.constraintlayout.core;

import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class Metrics {
    public long graphOptimizer;
    public long graphSolved;
    public long linearSolved;
    public long mChildCount;
    public long mEquations;
    public long mMeasureCalls;
    public long mMeasureDuration;
    public int mNumberOfLayouts;
    public int mNumberOfMeasures;
    public long mSimpleEquations;
    public long mVariables;
    public long maxRows;
    public long maxTableSize;
    public long maxVariables;
    public long measures;
    public long measuresLayoutDuration;
    public long measuresWidgetsDuration;
    public final ArrayList problematicLayouts = new ArrayList();
    public long widgets;

    public final String toString() {
        StringBuilder sb = new StringBuilder("\n*** Metrics ***\nmeasures: ");
        sb.append(this.measures);
        sb.append("\nmeasuresWrap: 0\nmeasuresWrapInfeasible: 0\ndetermineGroups: 0\ninfeasibleDetermineGroups: 0\ngraphOptimizer: ");
        sb.append(this.graphOptimizer);
        sb.append("\nwidgets: ");
        sb.append(this.widgets);
        sb.append("\ngraphSolved: ");
        sb.append(this.graphSolved);
        sb.append("\nlinearSolved: ");
        return MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(this.linearSolved, "\n", sb);
    }
}
