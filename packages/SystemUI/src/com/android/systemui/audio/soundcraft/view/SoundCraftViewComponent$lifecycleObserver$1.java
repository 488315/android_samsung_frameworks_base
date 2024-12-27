package com.android.systemui.audio.soundcraft.view;

import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

public final class SoundCraftViewComponent$lifecycleObserver$1 implements LifecycleEventObserver {
    public static final SoundCraftViewComponent$lifecycleObserver$1 INSTANCE = new SoundCraftViewComponent$lifecycleObserver$1();

    @Override // androidx.lifecycle.LifecycleEventObserver
    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        Log.d("SoundCraft.SoundCraftViewComponent", "Lifecycle onStateChanged : event=" + event);
    }
}
