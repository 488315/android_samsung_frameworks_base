package com.samsung.systemui.splugins.volume;

/* loaded from: classes4.dex */
public interface VolumeMiddleware<TA, TS> {
    TA apply(TA ta);

    default void applyState(TS ts) {
    }
}
