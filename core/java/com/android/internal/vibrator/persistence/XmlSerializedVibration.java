package com.android.internal.vibrator.persistence;

import com.android.modules.utils.TypedXmlSerializer;

import java.io.IOException;

public interface XmlSerializedVibration<T> {
    T deserialize();

    void write(TypedXmlSerializer typedXmlSerializer) throws IOException;

    void writeContent(TypedXmlSerializer typedXmlSerializer) throws IOException;
}
