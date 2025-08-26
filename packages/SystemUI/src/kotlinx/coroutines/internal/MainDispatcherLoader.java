package kotlinx.coroutines.internal;

import android.os.Looper;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlinx.coroutines.android.AndroidDispatcherFactory;
import kotlinx.coroutines.android.HandlerContext;
import kotlinx.coroutines.android.HandlerDispatcherKt;

/* loaded from: classes4.dex */
public final class MainDispatcherLoader {
    public static final HandlerContext dispatcher;

    /* JADX WARN: Multi-variable type inference failed */
    static {
        String property;
        Object next;
        new MainDispatcherLoader();
        int i = SystemPropsKt__SystemPropsKt.AVAILABLE_PROCESSORS;
        String str = null;
        Object[] objArr = 0;
        try {
            property = System.getProperty("kotlinx.coroutines.fast.service.loader");
        } catch (SecurityException unused) {
            property = null;
        }
        if (property != null) {
            Boolean.parseBoolean(property);
        }
        try {
            Iterator it = SequencesKt___SequencesKt.toList(SequencesKt__SequencesKt.asSequence(Arrays.asList(new AndroidDispatcherFactory()).iterator())).iterator();
            if (it.hasNext()) {
                next = it.next();
                if (it.hasNext()) {
                    ((MainDispatcherFactory) next).getClass();
                    do {
                        ((MainDispatcherFactory) it.next()).getClass();
                    } while (it.hasNext());
                }
            } else {
                next = null;
            }
            if (((MainDispatcherFactory) next) == null) {
                throw new IllegalStateException("Module with the Main dispatcher is missing. Add dependency providing the Main dispatcher, e.g. 'kotlinx-coroutines-android' and ensure it has the same version as 'kotlinx-coroutines-core'");
            }
            Looper mainLooper = Looper.getMainLooper();
            if (mainLooper == null) {
                throw new IllegalStateException("The main looper is not available");
            }
            dispatcher = new HandlerContext(HandlerDispatcherKt.asHandler(mainLooper), str, 2, objArr == true ? 1 : 0);
        } catch (Throwable th) {
            throw new ServiceConfigurationError(th.getMessage(), th);
        }
    }

    private MainDispatcherLoader() {
    }
}
