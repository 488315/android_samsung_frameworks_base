package com.android.systemui.ambient.touch.dagger;

import android.view.GestureDetector;
import com.android.systemui.ambient.touch.InputSession;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public interface InputSessionComponent {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface Factory {
        InputSessionComponent create(String str, InputChannelCompat$InputEventListener inputChannelCompat$InputEventListener, GestureDetector.OnGestureListener onGestureListener, boolean z);
    }

    InputSession getInputSession();
}
