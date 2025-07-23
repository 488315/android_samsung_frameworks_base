package com.android.systemui.settings;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface DisplayTracker {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Callback {
        default void onDisplayAdded(int i) {
        }

        default void onDisplayChanged(int i) {
        }

        default void onDisplayRemoved(int i) {
        }
    }
}
