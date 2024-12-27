package com.android.server.input;

import android.text.TextUtils;
import android.util.Slog;
import android.util.Xml;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class ConfigurationProcessor {
    public static Map processInputPortAssociations(InputStream inputStream) throws Exception {
        HashMap hashMap = new HashMap();
        TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(inputStream);
        XmlUtils.beginDocument(resolvePullParser, "ports");
        while (true) {
            XmlUtils.nextElement(resolvePullParser);
            if (!"port".equals(resolvePullParser.getName())) {
                return hashMap;
            }
            String attributeValue = resolvePullParser.getAttributeValue((String) null, "input");
            String attributeValue2 = resolvePullParser.getAttributeValue((String) null, "display");
            if (TextUtils.isEmpty(attributeValue) || TextUtils.isEmpty(attributeValue2)) {
                Slog.wtf("ConfigurationProcessor", "Ignoring incomplete entry");
            } else {
                try {
                    hashMap.put(attributeValue, Integer.valueOf(Integer.parseUnsignedInt(attributeValue2)));
                } catch (NumberFormatException unused) {
                    Slog.wtf("ConfigurationProcessor", "Display port should be an integer");
                }
            }
        }
    }
}
