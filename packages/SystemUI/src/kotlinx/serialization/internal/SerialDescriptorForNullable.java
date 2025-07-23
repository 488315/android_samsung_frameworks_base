package kotlinx.serialization.internal;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.serialization.descriptors.SerialDescriptor;
import kotlinx.serialization.descriptors.SerialKind;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SerialDescriptorForNullable implements SerialDescriptor, CachedNames {
    public final SerialDescriptor original;
    public final String serialName;
    public final Set serialNames;

    public SerialDescriptorForNullable(SerialDescriptor serialDescriptor) {
        this.original = serialDescriptor;
        this.serialName = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(serialDescriptor.getSerialName(), "?");
        this.serialNames = Platform_commonKt.cachedSerialNames(serialDescriptor);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SerialDescriptorForNullable) {
            return Intrinsics.areEqual(this.original, ((SerialDescriptorForNullable) obj).original);
        }
        return false;
    }

    @Override // kotlinx.serialization.descriptors.SerialDescriptor
    public final SerialDescriptor getElementDescriptor(int i) {
        return this.original.getElementDescriptor(i);
    }

    @Override // kotlinx.serialization.descriptors.SerialDescriptor
    public final String getElementName(int i) {
        return this.original.getElementName(i);
    }

    @Override // kotlinx.serialization.descriptors.SerialDescriptor
    public final int getElementsCount() {
        return this.original.getElementsCount();
    }

    @Override // kotlinx.serialization.descriptors.SerialDescriptor
    public final SerialKind getKind() {
        return this.original.getKind();
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
        return this.original.hashCode() * 31;
    }

    @Override // kotlinx.serialization.descriptors.SerialDescriptor
    public final boolean isElementOptional(int i) {
        return this.original.isElementOptional(i);
    }

    @Override // kotlinx.serialization.descriptors.SerialDescriptor
    public final boolean isNullable() {
        return true;
    }

    public final String toString() {
        return this.original + "?";
    }
}
