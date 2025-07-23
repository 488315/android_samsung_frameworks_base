package androidx.datastore.core;

import java.io.InputStream;
import kotlin.Unit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface Serializer {
    Object getDefaultValue();

    Object readFrom(InputStream inputStream);

    Unit writeTo(Object obj, UncloseableOutputStream uncloseableOutputStream);
}
