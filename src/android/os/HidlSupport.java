package android.os;

import android.annotation.SystemApi;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;

@SystemApi
/* loaded from: classes3.dex */
public class HidlSupport {
    @SystemApi
    public static native int getPidIfSharable();

    public static native boolean isHidlSupported();

    @SystemApi
    public static boolean deepEquals(Object obj, Object obj2) {
        Class<?> cls;
        Class<?> cls2;
        if (obj == obj2) {
            return true;
        }
        if (obj == null || obj2 == null || (cls = obj.getClass()) != (cls2 = obj2.getClass())) {
            return false;
        }
        if (cls.isArray()) {
            Class<?> componentType = cls.getComponentType();
            if (componentType != cls2.getComponentType()) {
                return false;
            }
            if (componentType.isPrimitive()) {
                return Objects.deepEquals(obj, obj2);
            }
            final Object[] objArr = (Object[]) obj;
            final Object[] objArr2 = (Object[]) obj2;
            return objArr.length == objArr2.length && IntStream.range(0, objArr.length).allMatch(new IntPredicate() { // from class: android.os.HidlSupport$$ExternalSyntheticLambda2
                @Override // java.util.function.IntPredicate
                public final boolean test(int i) {
                    boolean deepEquals;
                    deepEquals = HidlSupport.deepEquals(objArr[i], objArr2[i]);
                    return deepEquals;
                }
            });
        }
        if (obj instanceof List) {
            List list = (List) obj;
            List list2 = (List) obj2;
            if (list.size() != list2.size()) {
                return false;
            }
            final Iterator it = list.iterator();
            return list2.stream().allMatch(new Predicate() { // from class: android.os.HidlSupport$$ExternalSyntheticLambda3
                @Override // java.util.function.Predicate
                public final boolean test(Object obj3) {
                    boolean deepEquals;
                    deepEquals = HidlSupport.deepEquals(it.next(), obj3);
                    return deepEquals;
                }
            });
        }
        throwErrorIfUnsupportedType(obj);
        return obj.equals(obj2);
    }

    public static final class Mutable<E> {
        public E value;

        public Mutable() {
            this.value = null;
        }

        public Mutable(E e) {
            this.value = e;
        }
    }

    @SystemApi
    public static int deepHashCode(Object obj) {
        if (obj == null) {
            return 0;
        }
        Class<?> cls = obj.getClass();
        if (cls.isArray()) {
            if (cls.getComponentType().isPrimitive()) {
                return primitiveArrayHashCode(obj);
            }
            return Arrays.hashCode(Arrays.stream((Object[]) obj).mapToInt(new ToIntFunction() { // from class: android.os.HidlSupport$$ExternalSyntheticLambda0
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj2) {
                    int deepHashCode;
                    deepHashCode = HidlSupport.deepHashCode(obj2);
                    return deepHashCode;
                }
            }).toArray());
        }
        if (obj instanceof List) {
            return Arrays.hashCode(((List) obj).stream().mapToInt(new ToIntFunction() { // from class: android.os.HidlSupport$$ExternalSyntheticLambda1
                @Override // java.util.function.ToIntFunction
                public final int applyAsInt(Object obj2) {
                    int deepHashCode;
                    deepHashCode = HidlSupport.deepHashCode(obj2);
                    return deepHashCode;
                }
            }).toArray());
        }
        throwErrorIfUnsupportedType(obj);
        return obj.hashCode();
    }

    private static void throwErrorIfUnsupportedType(Object obj) {
        if ((obj instanceof Collection) && !(obj instanceof List)) {
            throw new UnsupportedOperationException("Cannot check equality on collections other than lists: " + obj.getClass().getName());
        }
        if (obj instanceof Map) {
            throw new UnsupportedOperationException("Cannot check equality on maps");
        }
    }

    private static int primitiveArrayHashCode(Object obj) {
        Class<?> componentType = obj.getClass().getComponentType();
        if (componentType == Boolean.TYPE) {
            return Arrays.hashCode((boolean[]) obj);
        }
        if (componentType == Byte.TYPE) {
            return Arrays.hashCode((byte[]) obj);
        }
        if (componentType == Character.TYPE) {
            return Arrays.hashCode((char[]) obj);
        }
        if (componentType == Double.TYPE) {
            return Arrays.hashCode((double[]) obj);
        }
        if (componentType == Float.TYPE) {
            return Arrays.hashCode((float[]) obj);
        }
        if (componentType == Integer.TYPE) {
            return Arrays.hashCode((int[]) obj);
        }
        if (componentType == Long.TYPE) {
            return Arrays.hashCode((long[]) obj);
        }
        if (componentType == Short.TYPE) {
            return Arrays.hashCode((short[]) obj);
        }
        throw new UnsupportedOperationException();
    }

    @SystemApi
    public static boolean interfacesEqual(IHwInterface iHwInterface, Object obj) {
        if (iHwInterface == obj) {
            return true;
        }
        if (iHwInterface == null || obj == null || !(obj instanceof IHwInterface)) {
            return false;
        }
        return Objects.equals(iHwInterface.asBinder(), ((IHwInterface) obj).asBinder());
    }
}
