package kotlinx.coroutines;

import java.util.Arrays;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import kotlinx.coroutines.android.AndroidExceptionPreHandler;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public abstract /* synthetic */ class CoroutineExceptionHandlerImplKt$$ExternalSyntheticServiceLoad0 {
    /* renamed from: m */
    public static /* synthetic */ Iterator m289m() {
        try {
            return Arrays.asList(new AndroidExceptionPreHandler()).iterator();
        } catch (Throwable th) {
            throw new ServiceConfigurationError(th.getMessage(), th);
        }
    }
}
