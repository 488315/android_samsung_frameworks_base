package com.android.wm.shell.bubbles;

import com.android.wm.shell.bubbles.bar.BubbleBarExpandedView;
import com.android.wm.shell.shared.handles.RegionSamplingHelper;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public interface RegionSamplingProvider {
    RegionSamplingHelper createHelper(BubbleBarExpandedView bubbleBarExpandedView, BubbleBarExpandedView.AnonymousClass5 anonymousClass5, Executor executor, Executor executor2);
}
