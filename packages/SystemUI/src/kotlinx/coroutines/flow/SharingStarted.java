package kotlinx.coroutines.flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface SharingStarted {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final StartedEagerly Eagerly = new StartedEagerly();
        public static final StartedLazily Lazily = new StartedLazily();

        private Companion() {
        }

        public static StartedWhileSubscribed WhileSubscribed$default(Companion companion, int i) {
            long j = (i & 1) != 0 ? 0L : 1000L;
            long j2 = (i & 2) != 0 ? Long.MAX_VALUE : 0L;
            companion.getClass();
            return new StartedWhileSubscribed(j, j2);
        }
    }

    Flow command(StateFlow stateFlow);
}
