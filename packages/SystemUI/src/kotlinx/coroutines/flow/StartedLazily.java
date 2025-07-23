package kotlinx.coroutines.flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class StartedLazily implements SharingStarted {
    @Override // kotlinx.coroutines.flow.SharingStarted
    public final Flow command(StateFlow stateFlow) {
        return new SafeFlow(new StartedLazily$command$1(stateFlow, null));
    }

    public final String toString() {
        return "SharingStarted.Lazily";
    }
}
