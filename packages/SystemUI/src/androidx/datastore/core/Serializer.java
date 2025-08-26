package androidx.datastore.core;

import java.io.InputStream;
import kotlin.Unit;

/* loaded from: classes.dex */
public interface Serializer {
    Object getDefaultValue();

    Object readFrom(InputStream inputStream);

    Unit writeTo(Object obj, UncloseableOutputStream uncloseableOutputStream);
}
