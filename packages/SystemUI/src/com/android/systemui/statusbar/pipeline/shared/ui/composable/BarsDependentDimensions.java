package com.android.systemui.statusbar.pipeline.shared.ui.composable;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class BarsDependentDimensions {
    public final long barBaseHeight;
    public final long barsHorizontalPadding;
    public final long totalWidth;

    public /* synthetic */ BarsDependentDimensions(long j, long j2, long j3, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3);
    }

    private BarsDependentDimensions(long j, long j2, long j3) {
        this.totalWidth = j;
        this.barsHorizontalPadding = j2;
        this.barBaseHeight = j3;
    }
}
