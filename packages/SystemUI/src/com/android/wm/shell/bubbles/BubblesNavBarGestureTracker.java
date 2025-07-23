package com.android.wm.shell.bubbles;

import android.content.Context;
import android.view.InputMonitor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
