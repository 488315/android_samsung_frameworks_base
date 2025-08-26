package com.android.systemui.touchpad.tutorial.ui.gesture;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public abstract class DistanceGestureState {
    public final float deltaX;
    public final float deltaY;

    public /* synthetic */ DistanceGestureState(float f, float f2, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2);
    }

    private DistanceGestureState(float f, float f2) {
        this.deltaX = f;
        this.deltaY = f2;
    }
}
