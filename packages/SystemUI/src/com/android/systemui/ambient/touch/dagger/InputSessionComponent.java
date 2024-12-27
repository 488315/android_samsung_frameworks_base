package com.android.systemui.ambient.touch.dagger;

import android.view.GestureDetector;
import com.android.systemui.ambient.touch.InputSession;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;

public interface InputSessionComponent {

    public interface Factory {
        InputSessionComponent create(String str, InputChannelCompat$InputEventListener inputChannelCompat$InputEventListener, GestureDetector.OnGestureListener onGestureListener, boolean z);
    }

    InputSession getInputSession();
}
