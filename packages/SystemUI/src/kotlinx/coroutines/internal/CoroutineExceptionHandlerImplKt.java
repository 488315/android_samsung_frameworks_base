package kotlinx.coroutines.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.ServiceConfigurationError;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlinx.coroutines.android.AndroidExceptionPreHandler;

/* loaded from: classes4.dex */
public abstract class CoroutineExceptionHandlerImplKt {
    public static final Collection platformExceptionHandlers;

    static {
        try {
            platformExceptionHandlers = SequencesKt___SequencesKt.toList(SequencesKt__SequencesKt.asSequence(Arrays.asList(new AndroidExceptionPreHandler()).iterator()));
        } catch (Throwable th) {
            throw new ServiceConfigurationError(th.getMessage(), th);
        }
    }
}
