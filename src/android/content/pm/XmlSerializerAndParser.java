package android.content.pm;

import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes.dex */
public interface XmlSerializerAndParser<T> {
    T createFromXml(TypedXmlPullParser typedXmlPullParser) throws IOException, XmlPullParserException;

    void writeAsXml(T t, TypedXmlSerializer typedXmlSerializer) throws IOException;

    default void writeAsXml(T t, XmlSerializer xmlSerializer) throws IOException {
        writeAsXml((XmlSerializerAndParser<T>) t, XmlUtils.makeTyped(xmlSerializer));
    }

    default T createFromXml(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        return createFromXml(XmlUtils.makeTyped(xmlPullParser));
    }
}
