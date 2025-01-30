package com.google.protobuf;

import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface LazyStringList extends List {
    void add(ByteString byteString);

    Object getRaw(int i);

    List getUnderlyingElements();

    LazyStringList getUnmodifiableView();
}
