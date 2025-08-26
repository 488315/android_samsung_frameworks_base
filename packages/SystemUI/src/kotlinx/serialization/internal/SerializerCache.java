package kotlinx.serialization.internal;

import kotlin.reflect.KClass;
import kotlinx.serialization.KSerializer;

/* loaded from: classes4.dex */
public interface SerializerCache {
    KSerializer get(KClass kClass);
}
