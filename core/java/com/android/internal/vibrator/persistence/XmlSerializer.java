package com.android.internal.vibrator.persistence;

@FunctionalInterface
/* loaded from: classes5.dex */
public interface XmlSerializer<T> {
    XmlSerializedVibration<T> serialize(T t) throws XmlSerializerException;
}
