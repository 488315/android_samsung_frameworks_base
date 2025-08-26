package com.android.wm.shell.bubbles;

import android.content.Context;
import android.view.InputMonitor;

/* loaded from: classes3.dex */
public class BubblesNavBarGestureTracker {
    public final Context mContext;
    public BubblesNavBarInputEventReceiver mInputEventReceiver;
    public InputMonitor mInputMonitor;
    public final BubblePositioner mPositioner;

    public BubblesNavBarGestureTracker(Context context, BubblePositioner bubblePositioner) {
        this.mContext = context;
        this.mPositioner = bubblePositioner;
    }
}
