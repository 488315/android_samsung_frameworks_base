package kotlinx.serialization.internal;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.serialization.KSerializer;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.descriptors.SerialKind;
import kotlinx.serialization.descriptors.StructureKind;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class PluginGeneratedSerialDescriptor implements SerialDescriptor, CachedNames {
    public final Lazy _hashCode$delegate;
    public int added;
    public final Lazy childSerializers$delegate;
    public final int elementsCount;
    public final boolean[] elementsOptionality;
    public final GeneratedSerializer generatedSerializer;
    public Map indices;
    public final String[] names;
    public final List[] propertiesAnnotations;
    public final String serialName;
    public final Lazy typeParameterDescriptors$delegate;

    public PluginGeneratedSerialDescriptor(String str, GeneratedSerializer generatedSerializer, int i) {
        this.serialName = str;
        this.generatedSerializer = generatedSerializer;
        this.elementsCount = i;
        this.added = -1;
        String[] strArr = new String[i];
        for (int i2 = 0; i2 < i; i2++) {
            strArr[i2] = "[UNINITIALIZED]";
        }
        this.names = strArr;
        int i3 = this.elementsCount;
        this.propertiesAnnotations = new List[i3];
        this.elementsOptionality = new boolean[i3];
        this.indices = MapsKt__MapsKt.emptyMap();
        LazyThreadSafetyMode lazyThreadSafetyMode = LazyThreadSafetyMode.PUBLICATION;
        final int i4 = 0;
        this.childSerializers$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0(this) { // from class: kotlinx.serialization.internal.PluginGeneratedSerialDescriptor$$ExternalSyntheticLambda1
            public final /* synthetic */ PluginGeneratedSerialDescriptor f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i4) {
                    case 0:
                        GeneratedSerializer generatedSerializer2 = this.f$0.generatedSerializer;
                        return generatedSerializer2 != null ? new KSerializer[]{((InlineClassDescriptorKt$InlinePrimitiveDescriptor$1) generatedSerializer2).$primitiveSerializer} : PluginHelperInterfacesKt.EMPTY_SERIALIZER_ARRAY;
                    case 1:
                        return Platform_commonKt.compactArray(this.f$0.generatedSerializer != null ? new ArrayList(0) : null);
                    default:
                        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = this.f$0;
                        return Integer.valueOf(PluginGeneratedSerialDescriptorKt.hashCodeImpl(pluginGeneratedSerialDescriptor, (SerialDescriptor[]) pluginGeneratedSerialDescriptor.typeParameterDescriptors$delegate.getValue()));
                }
            }
        });
        final int i5 = 1;
        this.typeParameterDescriptors$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0(this) { // from class: kotlinx.serialization.internal.PluginGeneratedSerialDescriptor$$ExternalSyntheticLambda1
            public final /* synthetic */ PluginGeneratedSerialDescriptor f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i5) {
                    case 0:
                        GeneratedSerializer generatedSerializer2 = this.f$0.generatedSerializer;
                        return generatedSerializer2 != null ? new KSerializer[]{((InlineClassDescriptorKt$InlinePrimitiveDescriptor$1) generatedSerializer2).$primitiveSerializer} : PluginHelperInterfacesKt.EMPTY_SERIALIZER_ARRAY;
                    case 1:
                        return Platform_commonKt.compactArray(this.f$0.generatedSerializer != null ? new ArrayList(0) : null);
                    default:
                        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = this.f$0;
                        return Integer.valueOf(PluginGeneratedSerialDescriptorKt.hashCodeImpl(pluginGeneratedSerialDescriptor, (SerialDescriptor[]) pluginGeneratedSerialDescriptor.typeParameterDescriptors$delegate.getValue()));
                }
            }
        });
        final int i6 = 2;
        this._hashCode$delegate = LazyKt__LazyJVMKt.lazy(lazyThreadSafetyMode, new Function0(this) { // from class: kotlinx.serialization.internal.PluginGeneratedSerialDescriptor$$ExternalSyntheticLambda1
            public final /* synthetic */ PluginGeneratedSerialDescriptor f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i6) {
                    case 0:
                        GeneratedSerializer generatedSerializer2 = this.f$0.generatedSerializer;
                        return generatedSerializer2 != null ? new KSerializer[]{((InlineClassDescriptorKt$InlinePrimitiveDescriptor$1) generatedSerializer2).$primitiveSerializer} : PluginHelperInterfacesKt.EMPTY_SERIALIZER_ARRAY;
                    case 1:
                        return Platform_commonKt.compactArray(this.f$0.generatedSerializer != null ? new ArrayList(0) : null);
                    default:
                        PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = this.f$0;
                        return Integer.valueOf(PluginGeneratedSerialDescriptorKt.hashCodeImpl(pluginGeneratedSerialDescriptor, (SerialDescriptor[]) pluginGeneratedSerialDescriptor.typeParameterDescriptors$delegate.getValue()));
                }
            }
        });
    }

    public boolean equals(Object obj) {
        int i;
        if (this == obj) {
            return true;
        }
        if (obj instanceof PluginGeneratedSerialDescriptor) {
            SerialDescriptor serialDescriptor = (SerialDescriptor) obj;
            if (Intrinsics.areEqual(this.serialName, serialDescriptor.getSerialName()) && Arrays.equals((SerialDescriptor[]) this.typeParameterDescriptors$delegate.getValue(), (SerialDescriptor[]) ((PluginGeneratedSerialDescriptor) obj).typeParameterDescriptors$delegate.getValue())) {
                int elementsCount = serialDescriptor.getElementsCount();
                int i2 = this.elementsCount;
                if (i2 == elementsCount) {
                    for (0; i < i2; i + 1) {
                        i = (Intrinsics.areEqual(getElementDescriptor(i).getSerialName(), serialDescriptor.getElementDescriptor(i).getSerialName()) && Intrinsics.areEqual(getElementDescriptor(i).getKind(), serialDescriptor.getElementDescriptor(i).getKind())) ? i + 1 : 0;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override // kotlinx.serialization.descriptors.SerialDescriptor
    public SerialDescriptor getElementDescriptor(int i) {
        return ((KSerializer[]) this.childSerializers$delegate.getValue())[i].getDescriptor();
    }

    @Override // kotlinx.serialization.descriptors.SerialDescriptor
    public final String getElementName(int i) {
        return this.names[i];
    }

    @Override // kotlinx.serialization.descriptors.SerialDescriptor
    public final int getElementsCount() {
        return this.elementsCount;
    }

    @Override // kotlinx.serialization.descriptors.SerialDescriptor
    public SerialKind getKind() {
        return StructureKind.CLASS.INSTANCE;
    }

    @Override // kotlinx.serialization.descriptors.SerialDescriptor
    public final String getSerialName() {
        return this.serialName;
    }

    @Override // kotlinx.serialization.internal.CachedNames
    public final Set getSerialNames() {
        return this.indices.keySet();
    }

    public int hashCode() {
        return ((Number) this._hashCode$delegate.getValue()).intValue();
    }

    @Override // kotlinx.serialization.descriptors.SerialDescriptor
    public final boolean isElementOptional(int i) {
        return this.elementsOptionality[i];
    }

    @Override // kotlinx.serialization.descriptors.SerialDescriptor
    public final boolean isNullable() {
        return false;
    }

    public String toString() {
        return CollectionsKt___CollectionsKt.joinToString$default(RangesKt___RangesKt.until(0, this.elementsCount), ", ", TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), this.serialName, "("), ")", new Function1() { // from class: kotlinx.serialization.internal.PluginGeneratedSerialDescriptor$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                int intValue = ((Integer) obj).intValue();
                PluginGeneratedSerialDescriptor pluginGeneratedSerialDescriptor = PluginGeneratedSerialDescriptor.this;
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(pluginGeneratedSerialDescriptor.names[intValue], ": ", pluginGeneratedSerialDescriptor.getElementDescriptor(intValue).getSerialName());
            }
        }, 24);
    }

    public /* synthetic */ PluginGeneratedSerialDescriptor(String str, GeneratedSerializer generatedSerializer, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i2 & 2) != 0 ? null : generatedSerializer, i);
    }
}
