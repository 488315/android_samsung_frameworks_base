package com.android.wm.shell.bubbles;

import com.android.wm.shell.bubbles.bar.BubbleBarExpandedView;
import com.android.wm.shell.shared.handles.RegionSamplingHelper;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface RegionSamplingProvider {
    RegionSamplingHelper createHelper(BubbleBarExpandedView bubbleBarExpandedView, BubbleBarExpandedView.AnonymousClass5 anonymousClass5, Executor executor, Executor executor2);
}
