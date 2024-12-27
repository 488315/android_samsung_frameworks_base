package android.tracing.perfetto;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public class InitArguments {
    public static final int PERFETTO_BACKEND_IN_PROCESS = 1;
    public static final int PERFETTO_BACKEND_SYSTEM = 2;
    public final int backends;
    public final int shmemSizeHintKb;
    public static InitArguments DEFAULTS = new InitArguments(2, 0);
    public static InitArguments TESTING = new InitArguments(1, 0);

    @Retention(RetentionPolicy.SOURCE)
    public @interface PerfettoBackend {
    }

    public InitArguments(int backends, int shmemSizeHintKb) {
        this.backends = backends;
        this.shmemSizeHintKb = shmemSizeHintKb;
    }
}
