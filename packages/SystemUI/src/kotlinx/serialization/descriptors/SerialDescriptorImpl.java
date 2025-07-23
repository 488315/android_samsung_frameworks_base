package kotlinx.serialization.descriptors;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.collections.IndexingIterable;
import kotlin.collections.IndexingIterator;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.ArrayIterator;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import kotlinx.serialization.internal.CachedNames;
import kotlinx.serialization.internal.Platform_commonKt;
import kotlinx.serialization.internal.PluginGeneratedSerialDescriptorKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SerialDescriptorImpl implements SerialDescriptor, CachedNames {
    public final Lazy _hashCode$delegate;
    public final SerialDescriptor[] elementDescriptors;
    public final String[] elementNames;
    public final boolean[] elementOptionality;
    public final int elementsCount;
    public final SerialKind kind;
    public final String serialName;
    public final Set serialNames;
    public final SerialDescriptor[] typeParametersDescriptors;

    public SerialDescriptorImpl(String str, SerialKind serialKind, int i, List<? extends SerialDescriptor> list, ClassSerialDescriptorBuilder classSerialDescriptorBuilder) {
        this.serialName = str;
        this.kind = serialKind;
        this.elementsCount = i;
        classSerialDescriptorBuilder.getClass();
        this.serialNames = CollectionsKt___CollectionsKt.toHashSet(classSerialDescriptorBuilder.elementNames);
        final String[] strArr = (String[]) ((ArrayList) classSerialDescriptorBuilder.elementNames).toArray(new String[0]);
        this.elementNames = strArr;
        this.elementDescriptors = Platform_commonKt.compactArray(classSerialDescriptorBuilder.elementDescriptors);
        this.elementOptionality = CollectionsKt___CollectionsKt.toBooleanArray(classSerialDescriptorBuilder.elementOptionality);
        IndexingIterable indexingIterable = new IndexingIterable(new Function0() { // from class: kotlin.collections.ArraysKt___ArraysKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new ArrayIterator(strArr);
            }
        });
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(indexingIterable, 10));
        Iterator it = indexingIterable.iterator();
        while (true) {
            IndexingIterator indexingIterator = (IndexingIterator) it;
            if (!indexingIterator.iterator.hasNext()) {
                MapsKt__MapsKt.toMap(arrayList);
                this.typeParametersDescriptors = Platform_commonKt.compactArray(list);
                this._hashCode$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: kotlinx.serialization.descriptors.SerialDescriptorImpl$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        SerialDescriptorImpl serialDescriptorImpl = SerialDescriptorImpl.this;
                        return Integer.valueOf(PluginGeneratedSerialDescriptorKt.hashCodeImpl(serialDescriptorImpl, serialDescriptorImpl.typeParametersDescriptors));
                    }
                });
                return;
            }
            IndexedValue indexedValue = (IndexedValue) indexingIterator.next();
            arrayList.add(new Pair(indexedValue.value, Integer.valueOf(indexedValue.index)));
        }
    }

    public final boolean equals(Object obj) {
        int i;
        if (this == obj) {
            return true;
        }
        if (obj instanceof SerialDescriptorImpl) {
            SerialDescriptor serialDescriptor = (SerialDescriptor) obj;
            if (Intrinsics.areEqual(this.serialName, serialDescriptor.getSerialName()) && Arrays.equals(this.typeParametersDescriptors, ((SerialDescriptorImpl) obj).typeParametersDescriptors)) {
                int elementsCount = serialDescriptor.getElementsCount();
                int i2 = this.elementsCount;
                if (i2 == elementsCount) {
                    for (0; i < i2; i + 1) {
                        SerialDescriptor[] serialDescriptorArr = this.elementDescriptors;
                        i = (Intrinsics.areEqual(serialDescriptorArr[i].getSerialName(), serialDescriptor.getElementDescriptor(i).getSerialName()) && Intrinsics.areEqual(serialDescriptorArr[i].getKind(), serialDescriptor.getElementDescriptor(i).getKind())) ? i + 1 : 0;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    @Override // kotlinx.serialization.descriptors.SerialDescriptor
    public final SerialDescriptor getElementDescriptor(int i) {
        return this.elementDescriptors[i];
    }

    @Override // kotlinx.serialization.descriptors.SerialDescriptor
    public final String getElementName(int i) {
        return this.elementNames[i];
    }

    @Override // kotlinx.serialization.descriptors.SerialDescriptor
    public final int getElementsCount() {
        return this.elementsCount;
    }

    @Override // kotlinx.serialization.descriptors.SerialDescriptor
    public final SerialKind getKind() {
        return this.kind;
    }

    @Override // kotlinx.serialization.descriptors.SerialDescriptor
    public final String getSerialName() {
        return this.serialName;
    }

    @Override // kotlinx.serialization.internal.CachedNames
    public final Set getSerialNames() {
        return this.serialNames;
    }

    public final int hashCode() {
        return ((Number) this._hashCode$delegate.getValue()).intValue();
    }

    @Override // kotlinx.serialization.descriptors.SerialDescriptor
    public final boolean isElementOptional(int i) {
        return this.elementOptionality[i];
    }

    @Override // kotlinx.serialization.descriptors.SerialDescriptor
    public final boolean isNullable() {
        return false;
    }

    public final String toString() {
        return CollectionsKt___CollectionsKt.joinToString$default(RangesKt___RangesKt.until(0, this.elementsCount), ", ", TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), this.serialName, "("), ")", new Function1() { // from class: kotlinx.serialization.descriptors.SerialDescriptorImpl$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                int intValue = ((Integer) obj).intValue();
                SerialDescriptorImpl serialDescriptorImpl = SerialDescriptorImpl.this;
                return AbstractResolvableFuture$$ExternalSyntheticOutline0.m(serialDescriptorImpl.elementNames[intValue], ": ", serialDescriptorImpl.elementDescriptors[intValue].getSerialName());
            }
        }, 24);
    }
}
