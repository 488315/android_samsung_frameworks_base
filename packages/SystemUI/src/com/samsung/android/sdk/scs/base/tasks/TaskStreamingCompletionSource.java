package com.samsung.android.sdk.scs.base.tasks;

/* loaded from: classes4.dex */
public class TaskStreamingCompletionSource extends TaskCompletionSource {
    public TaskStreamingCompletionSource() {
        super(new TaskStreamingImpl());
    }
}
