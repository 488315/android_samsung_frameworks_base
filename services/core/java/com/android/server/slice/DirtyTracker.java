package com.android.server.slice;

import org.xmlpull.v1.XmlSerializer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public interface DirtyTracker {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface Persistable {
        String getFileName();

        void writeTo(XmlSerializer xmlSerializer);
    }

    void onPersistableDirty(Persistable persistable);
}
