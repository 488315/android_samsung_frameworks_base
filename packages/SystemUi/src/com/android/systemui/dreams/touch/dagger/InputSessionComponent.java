package com.android.systemui.dreams.touch.dagger;

import android.view.GestureDetector;
import com.android.systemui.dreams.touch.InputSession;
import com.android.systemui.shared.system.InputChannelCompat$InputEventListener;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface InputSessionComponent {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Factory {
        InputSessionComponent create(String str, InputChannelCompat$InputEventListener inputChannelCompat$InputEventListener, GestureDetector.OnGestureListener onGestureListener, boolean z);
    }

    InputSession getInputSession();
}
