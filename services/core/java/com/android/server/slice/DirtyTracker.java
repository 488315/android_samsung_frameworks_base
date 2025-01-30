package com.android.server.slice;

import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes3.dex */
public interface DirtyTracker {

  public interface Persistable {
    String getFileName();

    void writeTo(XmlSerializer xmlSerializer);
  }

  void onPersistableDirty(Persistable persistable);
}
