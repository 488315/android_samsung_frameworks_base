package com.android.wm.shell.back;

import android.view.VelocityTracker;

/* loaded from: classes3.dex */
public final class ProgressVelocityTracker {
    public final VelocityTracker velocityTracker = VelocityTracker.obtain();
    public long downTime = -1;
}
