package kotlinx.coroutines.flow;

/* loaded from: classes4.dex */
public final class StartedEagerly implements SharingStarted {
    @Override // kotlinx.coroutines.flow.SharingStarted
    public final Flow command(StateFlow stateFlow) {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(SharingCommand.START);
    }

    public final String toString() {
        return "SharingStarted.Eagerly";
    }
}
