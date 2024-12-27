package com.android.internal.app;

import android.app.prediction.AppPredictor;
import android.app.prediction.AppTarget;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes5.dex */
public class ResolverAppPredictorCallback {
    private volatile Consumer<List<AppTarget>> mCallback;

    public ResolverAppPredictorCallback(Consumer<List<AppTarget>> callback) {
        this.mCallback = callback;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyCallback(List<AppTarget> list) {
        Consumer<List<AppTarget>> callback = this.mCallback;
        if (callback != null) {
            callback.accept((List) Objects.requireNonNullElseGet(list, new Supplier() { // from class: com.android.internal.app.ResolverAppPredictorCallback$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return List.of();
                }
            }));
        }
    }

    public Consumer<List<AppTarget>> asConsumer() {
        return new Consumer() { // from class: com.android.internal.app.ResolverAppPredictorCallback$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ResolverAppPredictorCallback.this.notifyCallback((List) obj);
            }
        };
    }

    public AppPredictor.Callback asCallback() {
        return new AppPredictor.Callback() { // from class: com.android.internal.app.ResolverAppPredictorCallback$$ExternalSyntheticLambda1
            @Override // android.app.prediction.AppPredictor.Callback
            public final void onTargetsAvailable(List list) {
                ResolverAppPredictorCallback.this.notifyCallback(list);
            }
        };
    }

    public void destroy() {
        this.mCallback = null;
    }
}
