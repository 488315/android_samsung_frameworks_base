package kotlinx.serialization.internal;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.datastore.preferences.core.MutablePreferences$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.serialization.descriptors.ClassSerialDescriptorBuilder;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.descriptors.SerialDescriptorImpl;
import kotlinx.serialization.descriptors.SerialDescriptorKt$special$$inlined$Iterable$2;
import kotlinx.serialization.descriptors.SerialKind;
import kotlinx.serialization.descriptors.StructureKind;

/* loaded from: classes4.dex */
public final class EnumDescriptor extends PluginGeneratedSerialDescriptor {
    public final Lazy elementDescriptors$delegate;
    public final SerialKind.ENUM kind;

    public EnumDescriptor(final String str, final int i) {
        super(str, null, i, 2, null);
        this.kind = SerialKind.ENUM.INSTANCE;
        this.elementDescriptors$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: kotlinx.serialization.internal.EnumDescriptor$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                int i2 = i;
                SerialDescriptor[] serialDescriptorArr = new SerialDescriptor[i2];
                for (int i3 = 0; i3 < i2; i3++) {
                    String strM = MutablePreferences$$ExternalSyntheticOutline0.m(new StringBuilder(), str, ".", this.names[i3]);
                    StructureKind.OBJECT object = StructureKind.OBJECT.INSTANCE;
                    SerialDescriptor[] serialDescriptorArr2 = new SerialDescriptor[0];
                    if (StringsKt__StringsKt.isBlank(strM)) {
                        throw new IllegalArgumentException("Blank serial names are prohibited");
                    }
                    if (Intrinsics.areEqual(object, StructureKind.CLASS.INSTANCE)) {
                        throw new IllegalArgumentException("For StructureKind.CLASS please use 'buildClassSerialDescriptor' instead");
                    }
                    ClassSerialDescriptorBuilder classSerialDescriptorBuilder = new ClassSerialDescriptorBuilder(strM);
                    Unit unit = Unit.INSTANCE;
                    serialDescriptorArr[i3] = new SerialDescriptorImpl(strM, object, ((ArrayList) classSerialDescriptorBuilder.elementNames).size(), ArraysKt___ArraysKt.toList(serialDescriptorArr2), classSerialDescriptorBuilder);
                }
                return serialDescriptorArr;
            }
        });
    }

    @Override // kotlinx.serialization.internal.PluginGeneratedSerialDescriptor
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof SerialDescriptor)) {
            return false;
        }
        SerialDescriptor serialDescriptor = (SerialDescriptor) obj;
        if (serialDescriptor.getKind() != SerialKind.ENUM.INSTANCE) {
            return false;
        }
        return Intrinsics.areEqual(this.serialName, serialDescriptor.getSerialName()) && Intrinsics.areEqual(Platform_commonKt.cachedSerialNames(this), Platform_commonKt.cachedSerialNames(serialDescriptor));
    }

    @Override // kotlinx.serialization.internal.PluginGeneratedSerialDescriptor, kotlinx.serialization.descriptors.SerialDescriptor
    public final SerialDescriptor getElementDescriptor(int i) {
        return ((SerialDescriptor[]) this.elementDescriptors$delegate.getValue())[i];
    }

    @Override // kotlinx.serialization.internal.PluginGeneratedSerialDescriptor, kotlinx.serialization.descriptors.SerialDescriptor
    public final SerialKind getKind() {
        return this.kind;
    }

    @Override // kotlinx.serialization.internal.PluginGeneratedSerialDescriptor
    public final int hashCode() {
        int iHashCode = this.serialName.hashCode();
        Iterator it = new SerialDescriptorKt$special$$inlined$Iterable$2(this).iterator();
        int iHashCode2 = 1;
        while (it.hasNext()) {
            int i = iHashCode2 * 31;
            String str = (String) it.next();
            iHashCode2 = i + (str != null ? str.hashCode() : 0);
        }
        return (iHashCode * 31) + iHashCode2;
    }

    @Override // kotlinx.serialization.internal.PluginGeneratedSerialDescriptor
    public final String toString() {
        return CollectionsKt___CollectionsKt.joinToString$default(new SerialDescriptorKt$special$$inlined$Iterable$2(this), ", ", TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), this.serialName, "("), ")", null, 56);
    }
}
