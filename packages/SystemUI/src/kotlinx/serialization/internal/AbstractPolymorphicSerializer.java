package kotlinx.serialization.internal;

import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.Map;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.ClassReference;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.reflect.KClass;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.PolymorphicSerializer;
import kotlinx.serialization.SerializationException;
import kotlinx.serialization.encoding.AbstractEncoder;
import kotlinx.serialization.modules.SerialModuleImpl;

/* loaded from: classes4.dex */
public abstract class AbstractPolymorphicSerializer implements KSerializer {
    public abstract KClass getBaseClass();

    @Override // kotlinx.serialization.KSerializer
    public final void serialize(AbstractEncoder abstractEncoder, Object obj) {
        SerialModuleImpl serializersModule = abstractEncoder.getSerializersModule();
        KClass baseClass = getBaseClass();
        serializersModule.getClass();
        ClassReference classReference = (ClassReference) baseClass;
        KSerializer kSerializer = null;
        if (classReference.isInstance(obj)) {
            Map map = (Map) serializersModule.polyBase2Serializers.get(classReference);
            KSerializer kSerializer2 = map != null ? (KSerializer) map.get(Reflection.getOrCreateKotlinClass(obj.getClass())) : null;
            if (kSerializer2 == null) {
                kSerializer2 = null;
            }
            if (kSerializer2 != null) {
                kSerializer = kSerializer2;
            } else {
                Object obj2 = serializersModule.polyBase2DefaultSerializerProvider.get(classReference);
                Function1 function1 = TypeIntrinsics.isFunctionOfArity(1, obj2) ? (Function1) obj2 : null;
                if (function1 != null) {
                    kSerializer = (KSerializer) function1.mo781invoke(obj);
                }
            }
        }
        if (kSerializer != null) {
            PolymorphicSerializer polymorphicSerializer = (PolymorphicSerializer) this;
            polymorphicSerializer.getDescriptor();
            polymorphicSerializer.getDescriptor();
            String serialName = kSerializer.getDescriptor().getSerialName();
            abstractEncoder.encodeElement(0);
            abstractEncoder.encodeString(serialName);
            abstractEncoder.encodeSerializableElement(polymorphicSerializer.getDescriptor(), 1, kSerializer, obj);
            return;
        }
        ClassReference orCreateKotlinClass = Reflection.getOrCreateKotlinClass(obj.getClass());
        KClass baseClass2 = getBaseClass();
        String simpleName = orCreateKotlinClass.getSimpleName();
        if (simpleName == null) {
            simpleName = String.valueOf(orCreateKotlinClass);
        }
        ClassReference classReference2 = (ClassReference) baseClass2;
        String strM = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("in the polymorphic scope of '", classReference2.getSimpleName(), "'");
        String simpleName2 = classReference2.getSimpleName();
        StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("Serializer for subclass '", simpleName, "' is not found ", strM, ".\nCheck if class with serial name '");
        MoveResult$$ExternalSyntheticOutline0.m(sbM, simpleName, "' exists and serializer is registered in a corresponding SerializersModule.\nTo be registered automatically, class '", simpleName, "' has to be '@Serializable', and the base class '");
        throw new SerializationException(TransitionKt$$ExternalSyntheticOutline0.m(sbM, simpleName2, "' has to be sealed and '@Serializable'."));
    }
}
