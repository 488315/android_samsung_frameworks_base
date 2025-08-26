package kotlinx.serialization.modules;

import java.util.List;
import java.util.Map;
import kotlin.jvm.functions.Function1;
import kotlin.reflect.KClass;
import kotlinx.serialization.KSerializer;

/* loaded from: classes4.dex */
public final class SerialModuleImpl extends SerializersModule {
    public final Map class2ContextualFactory;
    public final boolean hasInterfaceContextualSerializers;
    public final Map polyBase2DefaultSerializerProvider;
    public final Map polyBase2Serializers;

    public SerialModuleImpl(Map<KClass, ? extends ContextualProvider> map, Map<KClass, ? extends Map<KClass, ? extends KSerializer>> map2, Map<KClass, ? extends Function1> map3, Map<KClass, ? extends Map<String, ? extends KSerializer>> map4, Map<KClass, ? extends Function1> map5, boolean z) {
        super(null);
        this.class2ContextualFactory = map;
        this.polyBase2Serializers = map2;
        this.polyBase2DefaultSerializerProvider = map3;
        this.hasInterfaceContextualSerializers = z;
    }

    public final KSerializer getContextual(KClass kClass, List list) {
        ContextualProvider contextualProvider = (ContextualProvider) this.class2ContextualFactory.get(kClass);
        KSerializer kSerializerInvoke = contextualProvider != null ? contextualProvider.invoke() : null;
        if (kSerializerInvoke != null) {
            return kSerializerInvoke;
        }
        return null;
    }
}
