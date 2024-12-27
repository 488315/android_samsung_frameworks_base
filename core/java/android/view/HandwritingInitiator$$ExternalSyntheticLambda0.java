package android.view;

import java.util.concurrent.Executor;

/* compiled from: D8$$SyntheticClass */
/* loaded from: classes4.dex */
public final /* synthetic */ class HandwritingInitiator$$ExternalSyntheticLambda0
        implements Executor {
    public final /* synthetic */ View f$0;

    public /* synthetic */ HandwritingInitiator$$ExternalSyntheticLambda0(View view) {
        this.f$0 = view;
    }

    @Override // java.util.concurrent.Executor
    public final void execute(Runnable runnable) {
        this.f$0.post(runnable);
    }
}
