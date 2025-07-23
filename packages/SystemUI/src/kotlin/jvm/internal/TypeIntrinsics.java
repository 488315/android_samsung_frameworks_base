package kotlin.jvm.internal;

import androidx.compose.runtime.internal.ComposableLambda;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.util.Collection;
import java.util.Map;
import kotlin.Function;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function10;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.functions.Function7;
import kotlin.jvm.functions.Function8;
import kotlin.jvm.functions.Function9;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.jvm.internal.markers.KMutableCollection;
import kotlin.jvm.internal.markers.KMutableMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class TypeIntrinsics {
    public static Collection asMutableCollection(Object obj) {
        if ((obj instanceof KMappedMarker) && !(obj instanceof KMutableCollection)) {
            throwCce(obj, "kotlin.collections.MutableCollection");
            throw null;
        }
        try {
            return (Collection) obj;
        } catch (ClassCastException e) {
            Intrinsics.sanitizeStackTrace(TypeIntrinsics.class.getName(), e);
            throw e;
        }
    }

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

    public static Map.Entry asMutableMapEntry(Object obj) {
        if ((obj instanceof KMappedMarker) && !(obj instanceof KMutableMap.Entry)) {
            throwCce(obj, "kotlin.collections.MutableMap.MutableEntry");
            throw null;
        }
        try {
            return (Map.Entry) obj;
        } catch (ClassCastException e) {
            Intrinsics.sanitizeStackTrace(TypeIntrinsics.class.getName(), e);
            throw e;
        }
    }

    public static void beforeCheckcastToFunctionOfArity(int i, Object obj) {
        if (obj == null || isFunctionOfArity(i, obj)) {
            return;
        }
        throwCce(obj, "kotlin.jvm.functions.Function" + i);
        throw null;
    }

    public static boolean isFunctionOfArity(int i, Object obj) {
        int i2;
        if (obj instanceof Function) {
            if (obj instanceof FunctionBase) {
                i2 = ((FunctionBase) obj).getArity();
            } else if (obj instanceof Function0) {
                i2 = 0;
            } else if (obj instanceof Function1) {
                i2 = 1;
            } else if (obj instanceof Function2) {
                i2 = 2;
            } else if (obj instanceof Function3) {
                i2 = 3;
            } else if (obj instanceof Function4) {
                i2 = 4;
            } else if (obj instanceof Function5) {
                i2 = 5;
            } else if (obj instanceof Function6) {
                i2 = 6;
            } else if (obj instanceof Function7) {
                i2 = 7;
            } else if (obj instanceof Function8) {
                i2 = 8;
            } else if (obj instanceof Function9) {
                i2 = 9;
            } else if (obj instanceof Function10) {
                i2 = 10;
            } else {
                boolean z = obj instanceof ComposableLambda;
                i2 = z ? 11 : z ? 13 : z ? 14 : z ? 15 : z ? 16 : z ? 17 : z ? 18 : z ? 19 : z ? 20 : z ? 21 : -1;
            }
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMutableMapEntry(Object obj) {
        if (obj instanceof Map.Entry) {
            return !(obj instanceof KMappedMarker) || (obj instanceof KMutableMap.Entry);
        }
        return false;
    }

    public static void throwCce(Object obj, String str) {
        ClassCastException classCastException = new ClassCastException(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(obj == null ? "null" : obj.getClass().getName(), " cannot be cast to ", str));
        Intrinsics.sanitizeStackTrace(TypeIntrinsics.class.getName(), classCastException);
        throw classCastException;
    }
}
