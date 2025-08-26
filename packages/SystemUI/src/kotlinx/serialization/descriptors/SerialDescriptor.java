package kotlinx.serialization.descriptors;

/* loaded from: classes4.dex */
public interface SerialDescriptor {
    SerialDescriptor getElementDescriptor(int i);

    String getElementName(int i);

    int getElementsCount();

    SerialKind getKind();

    String getSerialName();

    boolean isElementOptional(int i);

    boolean isNullable();
}
