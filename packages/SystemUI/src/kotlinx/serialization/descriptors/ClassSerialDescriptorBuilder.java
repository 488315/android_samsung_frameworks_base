package kotlinx.serialization.descriptors;

import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import kotlin.collections.EmptyList;

/* loaded from: classes4.dex */
public final class ClassSerialDescriptorBuilder {
    public final List elementAnnotations;
    public final List elementDescriptors;
    public final List elementNames;
    public final List elementOptionality;
    public final String serialName;
    public final Set uniqueNames;

    public ClassSerialDescriptorBuilder(String str) {
        this.serialName = str;
        EmptyList emptyList = EmptyList.INSTANCE;
        this.elementNames = new ArrayList();
        this.uniqueNames = new HashSet();
        this.elementDescriptors = new ArrayList();
        this.elementAnnotations = new ArrayList();
        this.elementOptionality = new ArrayList();
    }

    public static void element$default(ClassSerialDescriptorBuilder classSerialDescriptorBuilder, String str, SerialDescriptor serialDescriptor) {
        EmptyList emptyList = EmptyList.INSTANCE;
        if (!((HashSet) classSerialDescriptorBuilder.uniqueNames).add(str)) {
            StringBuilder sbM = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("Element with name '", str, "' is already registered in ");
            sbM.append(classSerialDescriptorBuilder.serialName);
            throw new IllegalArgumentException(sbM.toString().toString());
        }
        ((ArrayList) classSerialDescriptorBuilder.elementNames).add(str);
        ((ArrayList) classSerialDescriptorBuilder.elementDescriptors).add(serialDescriptor);
        ((ArrayList) classSerialDescriptorBuilder.elementAnnotations).add(emptyList);
        ((ArrayList) classSerialDescriptorBuilder.elementOptionality).add(false);
    }
}
