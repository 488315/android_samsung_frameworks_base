package com.android.systemui.audio.soundcraft.view;

import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class SoundCraftViewComponent$lifecycleObserver$1 implements LifecycleEventObserver {
    public static final SoundCraftViewComponent$lifecycleObserver$1 INSTANCE = new SoundCraftViewComponent$lifecycleObserver$1();

    @Override // androidx.lifecycle.LifecycleEventObserver
    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        Log.d("SoundCraft.SoundCraftViewComponent", "Lifecycle onStateChanged : event=" + event);
    }
}
