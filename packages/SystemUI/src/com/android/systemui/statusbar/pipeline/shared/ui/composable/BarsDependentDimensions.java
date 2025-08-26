package com.android.systemui.statusbar.pipeline.shared.ui.composable;

import kotlin.jvm.internal.DefaultConstructorMarker;

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
