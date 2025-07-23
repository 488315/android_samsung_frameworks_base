package com.android.systemui.ambient.touch.dagger;

import android.view.GestureDetector;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface InputSessionComponent {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        InputSessionComponent create(String str, InputChannelCompat$InputEventListener inputChannelCompat$InputEventListener, GestureDetector.OnGestureListener onGestureListener, boolean z);
    }
}
