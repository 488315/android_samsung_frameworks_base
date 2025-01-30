package com.android.p038wm.shell.bubbles;

import android.content.Context;
import android.view.InputMonitor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BubblesNavBarGestureTracker {
    public final Context mContext;
    public BubblesNavBarInputEventReceiver mInputEventReceiver;
    public InputMonitor mInputMonitor;
    public final BubblePositioner mPositioner;

    public BubblesNavBarGestureTracker(Context context, BubblePositioner bubblePositioner) {
        this.mContext = context;
        this.mPositioner = bubblePositioner;
    }
}
