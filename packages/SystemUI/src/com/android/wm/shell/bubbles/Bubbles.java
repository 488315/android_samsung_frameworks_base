package com.android.wm.shell.bubbles;

/* loaded from: classes3.dex */
public interface Bubbles {

    public interface BubbleExpandListener {
        void onBubbleExpandChanged(String str, boolean z);
    }

    public interface BubbleMetadataFlagListener {
        void onBubbleMetadataFlagChanged(Bubble bubble);
    }

    public interface PendingIntentCanceledListener {
    }
}
