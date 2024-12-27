package com.android.systemui.audio.soundcraft.view.audioeffect;

import android.view.ViewGroup;

public abstract class BaseAudioEffectItemView {
    public abstract void enable(boolean z);

    public abstract ViewGroup getRootView();

    public abstract void update();
}
