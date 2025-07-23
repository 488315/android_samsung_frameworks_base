package androidx.constraintlayout.widget;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.Metrics;
import java.text.DecimalFormat;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ConstraintLayoutStatistics {
    public static final int DURATION_OF_CHILD_MEASURES = 5;
    public static final int DURATION_OF_LAYOUT = 7;
    public static final int DURATION_OF_MEASURES = 6;
    private static int MAX_WORD = 25;
    public static final int NUMBER_OF_CHILD_MEASURES = 4;
    public static final int NUMBER_OF_CHILD_VIEWS = 3;
    public static final int NUMBER_OF_EQUATIONS = 9;
    public static final int NUMBER_OF_LAYOUTS = 1;
    public static final int NUMBER_OF_ON_MEASURES = 2;
    public static final int NUMBER_OF_SIMPLE_EQUATIONS = 10;
    public static final int NUMBER_OF_VARIABLES = 8;
    private static final String WORD_PAD = new String(new char[25]).replace((char) 0, ' ');
    ConstraintLayout mConstraintLayout;
    private final Metrics mMetrics;

    public ConstraintLayoutStatistics(ConstraintLayout constraintLayout) {
        this.mMetrics = new Metrics();
        attach(constraintLayout);
    }

    private String compare(DecimalFormat decimalFormat, ConstraintLayoutStatistics constraintLayoutStatistics, int i) {
        String m = TransitionKt$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(fmt(decimalFormat, getValue(i) * 1.0E-6f, 7), " -> "), fmt(decimalFormat, constraintLayoutStatistics.getValue(i) * 1.0E-6f, 7), "ms");
        String m2 = TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), WORD_PAD, geName(i));
        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("CL Perf: ", AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m2.substring(m2.length() - MAX_WORD), " = "), m);
    }

    private String fmt(DecimalFormat decimalFormat, float f, int i) {
        StringBuilder m = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(new String(new char[i]).replace((char) 0, ' '));
        m.append(decimalFormat.format(f));
        String sb = m.toString();
        return sb.substring(sb.length() - i);
    }

    private void log(String str) {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[2];
        stackTraceElement.getFileName();
        stackTraceElement.getLineNumber();
        DecimalFormat decimalFormat = new DecimalFormat("###.000");
        log(decimalFormat, 5);
        log(decimalFormat, 7);
        log(decimalFormat, 6);
        log(1);
        log(2);
        log(3);
        log(4);
        log(8);
        log(9);
        log(10);
    }

    public void attach(ConstraintLayout constraintLayout) {
        constraintLayout.fillMetrics(this.mMetrics);
        this.mConstraintLayout = constraintLayout;
    }

    public void detach() {
        ConstraintLayout constraintLayout = this.mConstraintLayout;
        if (constraintLayout != null) {
            constraintLayout.fillMetrics(null);
        }
    }

    public String geName(int i) {
        switch (i) {
            case 1:
                return "NumberOfLayouts";
            case 2:
                return "MeasureCalls";
            case 3:
                return "ChildCount";
            case 4:
                return "ChildrenMeasures";
            case 5:
                return "MeasuresWidgetsDuration ";
            case 6:
                return "MeasureDuration";
            case 7:
                return "MeasuresLayoutDuration";
            case 8:
                return "SolverVariables";
            case 9:
                return "SolverEquations";
            case 10:
                return "SimpleEquations";
            default:
                return "";
        }
    }

    public long getValue(int i) {
        switch (i) {
            case 1:
                return this.mMetrics.mNumberOfLayouts;
            case 2:
                return this.mMetrics.mMeasureCalls;
            case 3:
                return this.mMetrics.mChildCount;
            case 4:
                return this.mMetrics.mNumberOfMeasures;
            case 5:
                return this.mMetrics.measuresWidgetsDuration;
            case 6:
                return this.mMetrics.mMeasureDuration;
            case 7:
                return this.mMetrics.measuresLayoutDuration;
            case 8:
                return this.mMetrics.mVariables;
            case 9:
                return this.mMetrics.mEquations;
            case 10:
                return this.mMetrics.mSimpleEquations;
            default:
                return 0L;
        }
    }

    public void logSummary(String str) {
        log(str);
    }

    public void reset() {
        Metrics metrics = this.mMetrics;
        metrics.measures = 0L;
        metrics.widgets = 0L;
        metrics.maxTableSize = 0L;
        metrics.maxVariables = 0L;
        metrics.maxRows = 0L;
        metrics.graphOptimizer = 0L;
        metrics.graphSolved = 0L;
        metrics.linearSolved = 0L;
        metrics.problematicLayouts.clear();
        metrics.mNumberOfMeasures = 0;
        metrics.mNumberOfLayouts = 0;
        metrics.measuresWidgetsDuration = 0L;
        metrics.measuresLayoutDuration = 0L;
        metrics.mChildCount = 0L;
        metrics.mMeasureDuration = 0L;
        metrics.mMeasureCalls = 0L;
        metrics.mVariables = 0L;
        metrics.mEquations = 0L;
        metrics.mSimpleEquations = 0L;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public ConstraintLayoutStatistics m887clone() {
        return new ConstraintLayoutStatistics(this);
    }

    public void logSummary(String str, ConstraintLayoutStatistics constraintLayoutStatistics) {
        if (constraintLayoutStatistics == null) {
            log(str);
            return;
        }
        DecimalFormat decimalFormat = new DecimalFormat("###.000");
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        stackTraceElement.getFileName();
        stackTraceElement.getLineNumber();
        compare(decimalFormat, constraintLayoutStatistics, 5);
        compare(decimalFormat, constraintLayoutStatistics, 7);
        compare(decimalFormat, constraintLayoutStatistics, 6);
        compare(constraintLayoutStatistics, 1);
        compare(constraintLayoutStatistics, 2);
        compare(constraintLayoutStatistics, 3);
        compare(constraintLayoutStatistics, 4);
        compare(constraintLayoutStatistics, 8);
        compare(constraintLayoutStatistics, 9);
        compare(constraintLayoutStatistics, 10);
    }

    public ConstraintLayoutStatistics(ConstraintLayoutStatistics constraintLayoutStatistics) {
        Metrics metrics = new Metrics();
        this.mMetrics = metrics;
        Metrics metrics2 = constraintLayoutStatistics.mMetrics;
        metrics.mVariables = metrics2.mVariables;
        metrics.mEquations = metrics2.mEquations;
        metrics.mSimpleEquations = metrics2.mSimpleEquations;
        metrics.mNumberOfMeasures = metrics2.mNumberOfMeasures;
        metrics.mNumberOfLayouts = metrics2.mNumberOfLayouts;
        metrics.mMeasureDuration = metrics2.mMeasureDuration;
        metrics.mChildCount = metrics2.mChildCount;
        metrics.mMeasureCalls = metrics2.mMeasureCalls;
        metrics.measuresWidgetsDuration = metrics2.measuresWidgetsDuration;
        metrics.measuresLayoutDuration = metrics2.measuresLayoutDuration;
        metrics.measures = metrics2.measures;
        metrics.widgets = metrics2.widgets;
        metrics.maxTableSize = metrics2.maxTableSize;
        metrics.maxVariables = metrics2.maxVariables;
        metrics.maxRows = metrics2.maxRows;
        metrics.graphOptimizer = metrics2.graphOptimizer;
        metrics.graphSolved = metrics2.graphSolved;
    }

    private String log(DecimalFormat decimalFormat, int i) {
        String fmt = fmt(decimalFormat, getValue(i) * 1.0E-6f, 7);
        String m = TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), WORD_PAD, geName(i));
        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("CL Perf: ", AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m.substring(m.length() - MAX_WORD), " = "), fmt);
    }

    private String compare(ConstraintLayoutStatistics constraintLayoutStatistics, int i) {
        String str = getValue(i) + " -> " + constraintLayoutStatistics.getValue(i);
        String m = TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), WORD_PAD, geName(i));
        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("CL Perf: ", AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m.substring(m.length() - MAX_WORD), " = "), str);
    }

    private String log(int i) {
        String l = Long.toString(getValue(i));
        String m = TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), WORD_PAD, geName(i));
        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("CL Perf: ", AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m.substring(m.length() - MAX_WORD), " = "), l);
    }
}
