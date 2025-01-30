package kotlinx.coroutines.flow;

import kotlinx.coroutines.flow.internal.SubscriptionCountStateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface SharingStarted {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final StartedEagerly Eagerly = new StartedEagerly();
        public static final StartedLazily Lazily = new StartedLazily();

        private Companion() {
        }

        public static StartedWhileSubscribed WhileSubscribed$default(Companion companion) {
            companion.getClass();
            return new StartedWhileSubscribed(0L, Long.MAX_VALUE);
        }
    }

    Flow command(SubscriptionCountStateFlow subscriptionCountStateFlow);
}
