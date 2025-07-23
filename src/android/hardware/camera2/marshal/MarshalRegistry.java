package android.hardware.camera2.marshal;

import android.hardware.camera2.utils.TypeReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public class MarshalRegistry {
    private static final Object sMarshalLock = new Object();
    private static final List<MarshalQueryable<?>> sRegisteredMarshalQueryables = new ArrayList();
    private static final HashMap<MarshalToken<?>, Marshaler<?>> sMarshalerMap = new HashMap<>();

    public static <T> void registerMarshalQueryable(MarshalQueryable<T> marshalQueryable) {
        synchronized (sMarshalLock) {
            sRegisteredMarshalQueryables.add(marshalQueryable);
        }
    }

    public static <T> Marshaler<T> getMarshaler(TypeReference<T> typeReference, int i) {
        Marshaler<T> marshaler;
        synchronized (sMarshalLock) {
            MarshalToken<?> marshalToken = new MarshalToken<>(typeReference, i);
            marshaler = (Marshaler) sMarshalerMap.get(marshalToken);
            if (marshaler == null) {
                List<MarshalQueryable<?>> list = sRegisteredMarshalQueryables;
                if (list.size() == 0) {
                    throw new AssertionError("No available query marshalers registered");
                }
                Iterator<MarshalQueryable<?>> it = list.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    MarshalQueryable<?> next = it.next();
                    if (next.isTypeMappingSupported(typeReference, i)) {
                        marshaler = next.createMarshaler(typeReference, i);
                        break;
                    }
                }
                if (marshaler == null) {
                    throw new UnsupportedOperationException("Could not find marshaler that matches the requested combination of type reference " + typeReference + " and native type " + MarshalHelpers.toStringNativeType(i));
                }
                sMarshalerMap.put(marshalToken, marshaler);
            }
        }
        return marshaler;
    }

    private static class MarshalToken<T> {
        private final int hash;
        final int nativeType;
        final TypeReference<T> typeReference;

        public MarshalToken(TypeReference<T> typeReference, int i) {
            this.typeReference = typeReference;
            this.nativeType = i;
            this.hash = typeReference.hashCode() ^ i;
        }

        public boolean equals(Object obj) {
            if (obj instanceof MarshalToken) {
                MarshalToken marshalToken = (MarshalToken) obj;
                if (this.typeReference.equals(marshalToken.typeReference) && this.nativeType == marshalToken.nativeType) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return this.hash;
        }
    }

    private MarshalRegistry() {
        throw new AssertionError();
    }
}
