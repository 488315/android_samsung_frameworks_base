package com.android.server.graphics.fonts;

import android.graphics.fonts.FontUpdateRequest;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Slog;
import android.util.Xml;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class PersistentSystemFontConfig {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Config {
        public long lastModifiedMillis;
        public final Set updatedFontDirs = new ArraySet();
        public final List fontFamilies = new ArrayList();
    }

    public static void loadFromXml(InputStream inputStream, Config config) {
        TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(inputStream);
        while (true) {
            int next = resolvePullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 2) {
                int depth = resolvePullParser.getDepth();
                String name = resolvePullParser.getName();
                if (depth == 1) {
                    if (!"fontConfig".equals(name)) {
                        BootReceiver$$ExternalSyntheticOutline0.m("Invalid root tag: ", name, "PersistentSystemFontConfig");
                        return;
                    }
                } else if (depth == 2) {
                    name.getClass();
                    switch (name) {
                        case "lastModifiedDate":
                            String attributeValue = resolvePullParser.getAttributeValue((String) null, "value");
                            long j = 0;
                            if (!TextUtils.isEmpty(attributeValue)) {
                                try {
                                    j = Long.parseLong(attributeValue);
                                } catch (NumberFormatException unused) {
                                }
                            }
                            config.lastModifiedMillis = j;
                            break;
                        case "family":
                            ((ArrayList) config.fontFamilies).add(FontUpdateRequest.Family.readFromXml(resolvePullParser));
                            break;
                        case "updatedFontDir":
                            Set set = config.updatedFontDirs;
                            String attributeValue2 = resolvePullParser.getAttributeValue((String) null, "value");
                            if (attributeValue2 == null) {
                                attributeValue2 = "";
                            }
                            ((ArraySet) set).add(attributeValue2);
                            break;
                        default:
                            Slog.w("PersistentSystemFontConfig", "Skipping unknown tag: ".concat(name));
                            break;
                    }
                }
            }
        }
    }

    public static void writeToXml(OutputStream outputStream, Config config) {
        TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(outputStream);
        resolveSerializer.startDocument((String) null, Boolean.TRUE);
        resolveSerializer.startTag((String) null, "fontConfig");
        resolveSerializer.startTag((String) null, "lastModifiedDate");
        resolveSerializer.attribute((String) null, "value", Long.toString(config.lastModifiedMillis));
        resolveSerializer.endTag((String) null, "lastModifiedDate");
        Iterator it = ((ArraySet) config.updatedFontDirs).iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            resolveSerializer.startTag((String) null, "updatedFontDir");
            resolveSerializer.attribute((String) null, "value", str);
            resolveSerializer.endTag((String) null, "updatedFontDir");
        }
        List list = config.fontFamilies;
        int i = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) list;
            if (i >= arrayList.size()) {
                resolveSerializer.endTag((String) null, "fontConfig");
                resolveSerializer.endDocument();
                return;
            } else {
                FontUpdateRequest.Family family = (FontUpdateRequest.Family) arrayList.get(i);
                resolveSerializer.startTag((String) null, "family");
                FontUpdateRequest.Family.writeFamilyToXml(resolveSerializer, family);
                resolveSerializer.endTag((String) null, "family");
                i++;
            }
        }
    }
}
