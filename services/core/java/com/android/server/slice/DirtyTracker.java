package com.android.server.slice;

import org.xmlpull.v1.XmlSerializer;

public interface DirtyTracker {

    public interface Persistable {
        String getFileName();

        void writeTo(XmlSerializer xmlSerializer);
    }

    void onPersistableDirty(Persistable persistable);
}
