package kotlinx.serialization.internal;

import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.serialization.KSerializer;

/* loaded from: classes4.dex */
public abstract class AbstractCollectionSerializer implements KSerializer {
    public /* synthetic */ AbstractCollectionSerializer(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract Iterator collectionIterator(Object obj);

    public abstract int collectionSize(Object obj);

    private AbstractCollectionSerializer() {
    }
}
