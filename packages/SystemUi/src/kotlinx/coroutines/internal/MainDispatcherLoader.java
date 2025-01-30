package kotlinx.coroutines.internal;

import java.util.Iterator;
import java.util.List;
import kotlin.sequences.SequencesKt__SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlinx.coroutines.MainCoroutineDispatcher;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class MainDispatcherLoader {
    public static final MainCoroutineDispatcher dispatcher;

    static {
        String str;
        new MainDispatcherLoader();
        int i = SystemPropsKt__SystemPropsKt.AVAILABLE_PROCESSORS;
        Object obj = null;
        try {
            str = System.getProperty("kotlinx.coroutines.fast.service.loader");
        } catch (SecurityException unused) {
            str = null;
        }
        if (str != null) {
            Boolean.parseBoolean(str);
        }
        List list = SequencesKt___SequencesKt.toList(SequencesKt__SequencesKt.asSequence(MainDispatcherLoader$$ExternalSyntheticServiceLoad0.m297m()));
        Iterator it = list.iterator();
        if (it.hasNext()) {
            obj = it.next();
            if (it.hasNext()) {
                int loadPriority = ((MainDispatcherFactory) obj).getLoadPriority();
                do {
                    Object next = it.next();
                    int loadPriority2 = ((MainDispatcherFactory) next).getLoadPriority();
                    if (loadPriority < loadPriority2) {
                        obj = next;
                        loadPriority = loadPriority2;
                    }
                } while (it.hasNext());
            }
        }
        MainDispatcherFactory mainDispatcherFactory = (MainDispatcherFactory) obj;
        if (mainDispatcherFactory != null) {
            try {
                MainCoroutineDispatcher createDispatcher = mainDispatcherFactory.createDispatcher(list);
                if (createDispatcher != null) {
                    dispatcher = createDispatcher;
                    return;
                }
            } catch (Throwable th) {
                mainDispatcherFactory.hintOnError();
                throw th;
            }
        }
        throw new IllegalStateException("Module with the Main dispatcher is missing. Add dependency providing the Main dispatcher, e.g. 'kotlinx-coroutines-android' and ensure it has the same version as 'kotlinx-coroutines-core'");
    }

    private MainDispatcherLoader() {
    }
}
