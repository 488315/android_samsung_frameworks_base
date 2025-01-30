package kotlin.jvm.internal;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.util.Map;
import kotlin.Function;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.jvm.internal.markers.KMutableMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class TypeIntrinsics {
    public static Map asMutableMap(Object obj) {
        if ((obj instanceof KMappedMarker) && !(obj instanceof KMutableMap)) {
            throwCce(obj, "kotlin.collections.MutableMap");
            throw null;
        }
        try {
            return (Map) obj;
        } catch (ClassCastException e) {
            Intrinsics.sanitizeStackTrace(TypeIntrinsics.class.getName(), e);
            throw e;
        }
    }

    public static void beforeCheckcastToFunctionOfArity(int i, Object obj) {
        if (obj != null) {
            boolean z = false;
            if (obj instanceof Function) {
                if ((obj instanceof FunctionBase ? ((FunctionBase) obj).getArity() : obj instanceof Function0 ? 0 : obj instanceof Function1 ? 1 : obj instanceof Function2 ? 2 : obj instanceof Function3 ? 3 : obj instanceof Function4 ? 4 : obj instanceof Function5 ? 5 : obj instanceof Function6 ? 6 : -1) == i) {
                    z = true;
                }
            }
            if (z) {
                return;
            }
            throwCce(obj, "kotlin.jvm.functions.Function" + i);
            throw null;
        }
    }

    public static void throwCce(Object obj, String str) {
        ClassCastException classCastException = new ClassCastException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m15m(obj == null ? "null" : obj.getClass().getName(), " cannot be cast to ", str));
        Intrinsics.sanitizeStackTrace(TypeIntrinsics.class.getName(), classCastException);
        throw classCastException;
    }
}
