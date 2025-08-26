package androidx.navigation.serialization;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.navigation.CollectionNavType;
import androidx.navigation.NavType;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.encoding.AbstractEncoder;
import kotlinx.serialization.modules.SerialModuleImpl;
import kotlinx.serialization.modules.SerializersModuleKt;

/* loaded from: classes.dex */
public final class RouteEncoder extends AbstractEncoder {
    public final KSerializer serializer;
    public final Map typeMap;
    public final SerialModuleImpl serializersModule = SerializersModuleKt.EmptySerializersModule;
    public final Map map = new LinkedHashMap();
    public int elementIndex = -1;

    public RouteEncoder(KSerializer kSerializer, Map<String, ? extends NavType> map) {
        this.serializer = kSerializer;
        this.typeMap = map;
    }

    @Override // kotlinx.serialization.encoding.AbstractEncoder
    public final void encodeElement(int i) {
        this.elementIndex = i;
    }

    @Override // kotlinx.serialization.encoding.AbstractEncoder
    public final void encodeNull() {
        internalEncodeValue(null);
    }

    @Override // kotlinx.serialization.encoding.AbstractEncoder
    public final void encodeSerializableValue(KSerializer kSerializer, Object obj) {
        internalEncodeValue(obj);
    }

    @Override // kotlinx.serialization.encoding.AbstractEncoder
    public final void encodeValue(Object obj) {
        internalEncodeValue(obj);
    }

    @Override // kotlinx.serialization.encoding.AbstractEncoder
    public final SerialModuleImpl getSerializersModule() {
        return this.serializersModule;
    }

    public final void internalEncodeValue(Object obj) {
        String elementName = this.serializer.getDescriptor().getElementName(this.elementIndex);
        NavType navType = (NavType) this.typeMap.get(elementName);
        if (navType == null) {
            throw new IllegalStateException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Cannot find NavType for argument ", elementName, ". Please provide NavType through typeMap.").toString());
        }
        this.map.put(elementName, navType instanceof CollectionNavType ? ((CollectionNavType) navType).serializeAsValues(obj) : Collections.singletonList(navType.serializeAsValue(obj)));
    }
}
