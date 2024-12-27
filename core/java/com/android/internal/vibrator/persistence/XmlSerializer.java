package com.android.internal.vibrator.persistence;

@FunctionalInterface
public interface XmlSerializer<T> {
    XmlSerializedVibration<T> serialize(T t) throws XmlSerializerException;
}
