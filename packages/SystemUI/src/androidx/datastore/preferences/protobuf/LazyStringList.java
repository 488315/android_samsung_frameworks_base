package androidx.datastore.preferences.protobuf;

import java.util.List;

/* loaded from: classes.dex */
public interface LazyStringList extends List {
    Object getRaw();

    List getUnderlyingElements();
}
