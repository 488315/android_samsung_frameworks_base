package kotlinx.coroutines.flow;

import kotlinx.coroutines.flow.internal.SubscriptionCountStateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class StartedLazily implements SharingStarted {
    @Override // kotlinx.coroutines.flow.SharingStarted
    public final Flow command(SubscriptionCountStateFlow subscriptionCountStateFlow) {
        return new SafeFlow(new StartedLazily$command$1(subscriptionCountStateFlow, null));
    }

    public final String toString() {
        return "SharingStarted.Lazily";
    }
}
