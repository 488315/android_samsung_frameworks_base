package com.android.wm.shell.transition;

import android.window.TransitionInfo;

/* loaded from: classes3.dex */
public interface MultiTaskingTransitions {
    static int getDisplayId(TransitionInfo.Change change) {
        return change.getTaskInfo() != null ? change.getTaskInfo().displayId : change.getEndDisplayId();
    }
}
