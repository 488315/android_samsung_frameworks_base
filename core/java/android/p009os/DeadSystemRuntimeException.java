package android.p009os;

/* loaded from: classes3.dex */
public class DeadSystemRuntimeException extends RuntimeException {
    public DeadSystemRuntimeException() {
        super(new DeadSystemException());
    }
}
