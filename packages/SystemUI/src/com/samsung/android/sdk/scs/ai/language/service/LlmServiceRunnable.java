package com.samsung.android.sdk.scs.ai.language.service;

import android.os.Bundle;
import com.samsung.android.sdk.scs.base.tasks.TaskCompletionSource;
import com.samsung.android.sdk.scs.base.tasks.TaskRunnable;
import com.samsung.android.sdk.scs.base.tasks.TaskStreamingCompletionSource;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class LlmServiceRunnable<T> extends TaskRunnable {
    public final String featureName;
    public final AnonymousClass1 observer;
    public final Function resultMapper;
    public final Consumer serviceRequest;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.samsung.android.sdk.scs.ai.language.service.LlmServiceRunnable$1, reason: invalid class name */
    public class AnonymousClass1 extends LlmServiceObserver2 {
        public AnonymousClass1() {
        }
    }

    public LlmServiceRunnable(String str, boolean z, Consumer<LlmServiceObserver2> consumer, Function<List<Bundle>, T> function) {
        super(z ? new TaskStreamingCompletionSource() : new TaskCompletionSource());
        this.observer = new AnonymousClass1();
        this.featureName = str;
        this.serviceRequest = consumer;
        this.resultMapper = function;
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.TaskRunnable
    public final void execute() {
        this.serviceRequest.accept(this.observer);
    }

    @Override // com.samsung.android.sdk.scs.base.tasks.TaskRunnable
    public final String getFeatureName() {
        return this.featureName;
    }
}
