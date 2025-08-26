package com.android.wm.shell.bubbles.storage;

import android.util.SparseArray;
import android.util.Xml;
import com.android.internal.util.FastXmlSerializer;
import com.android.internal.util.XmlUtils;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public abstract class BubbleXmlHelperKt {
    public static final String getAttributeWithName(XmlPullParser xmlPullParser, String str) {
        int attributeCount = xmlPullParser.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            if (Intrinsics.areEqual(xmlPullParser.getAttributeName(i), str)) {
                return xmlPullParser.getAttributeValue(i);
            }
        }
        return null;
    }

    public static final SparseArray readXml(InputStream inputStream) throws XmlPullParserException, NumberFormatException, IOException {
        SparseArray sparseArray = new SparseArray();
        XmlPullParser xmlPullParserNewPullParser = Xml.newPullParser();
        xmlPullParserNewPullParser.setInput(inputStream, StandardCharsets.UTF_8.name());
        XmlUtils.beginDocument(xmlPullParserNewPullParser, "bs");
        int depth = xmlPullParserNewPullParser.getDepth();
        String attributeWithName = getAttributeWithName(xmlPullParserNewPullParser, "v");
        if (attributeWithName != null) {
            int i = Integer.parseInt(attributeWithName);
            if (i == 1) {
                int depth2 = xmlPullParserNewPullParser.getDepth();
                ArrayList arrayList = new ArrayList();
                while (XmlUtils.nextElementWithin(xmlPullParserNewPullParser, depth2)) {
                    BubbleEntity xmlEntry = readXmlEntry(xmlPullParserNewPullParser);
                    if (xmlEntry != null && xmlEntry.userId == 0) {
                        arrayList.add(xmlEntry);
                    }
                }
                if (!arrayList.isEmpty()) {
                    sparseArray.put(0, CollectionsKt___CollectionsKt.toList(arrayList));
                }
            } else if (i == 2) {
                while (XmlUtils.nextElementWithin(xmlPullParserNewPullParser, depth)) {
                    String attributeWithName2 = getAttributeWithName(xmlPullParserNewPullParser, NetworkAnalyticsConstants.DataPoints.UID);
                    if (attributeWithName2 != null) {
                        int depth3 = xmlPullParserNewPullParser.getDepth();
                        ArrayList arrayList2 = new ArrayList();
                        while (XmlUtils.nextElementWithin(xmlPullParserNewPullParser, depth3)) {
                            BubbleEntity xmlEntry2 = readXmlEntry(xmlPullParserNewPullParser);
                            if (xmlEntry2 != null) {
                                arrayList2.add(xmlEntry2);
                            }
                        }
                        if (!arrayList2.isEmpty()) {
                            sparseArray.put(Integer.parseInt(attributeWithName2), CollectionsKt___CollectionsKt.toList(arrayList2));
                        }
                    }
                }
            }
        }
        return sparseArray;
    }

    public static final BubbleEntity readXmlEntry(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException, NumberFormatException {
        String attributeWithName;
        String attributeWithName2;
        String attributeWithName3;
        while (xmlPullParser.getEventType() != 2) {
            xmlPullParser.next();
        }
        String attributeWithName4 = getAttributeWithName(xmlPullParser, NetworkAnalyticsConstants.DataPoints.UID);
        if (attributeWithName4 == null) {
            return null;
        }
        int i = Integer.parseInt(attributeWithName4);
        String attributeWithName5 = getAttributeWithName(xmlPullParser, "pkg");
        if (attributeWithName5 == null || (attributeWithName = getAttributeWithName(xmlPullParser, "sid")) == null || (attributeWithName2 = getAttributeWithName(xmlPullParser, "key")) == null || (attributeWithName3 = getAttributeWithName(xmlPullParser, "h")) == null) {
            return null;
        }
        int i2 = Integer.parseInt(attributeWithName3);
        String attributeWithName6 = getAttributeWithName(xmlPullParser, "hid");
        if (attributeWithName6 == null) {
            return null;
        }
        int i3 = Integer.parseInt(attributeWithName6);
        String attributeWithName7 = getAttributeWithName(xmlPullParser, "t");
        String attributeWithName8 = getAttributeWithName(xmlPullParser, "tid");
        int i4 = attributeWithName8 != null ? Integer.parseInt(attributeWithName8) : -1;
        String attributeWithName9 = getAttributeWithName(xmlPullParser, "l");
        String attributeWithName10 = getAttributeWithName(xmlPullParser, "d");
        return new BubbleEntity(i, attributeWithName5, attributeWithName, attributeWithName2, i2, i3, attributeWithName7, i4, attributeWithName9, attributeWithName10 != null ? Boolean.parseBoolean(attributeWithName10) : false);
    }

    public static final void writeXml(OutputStream outputStream, SparseArray sparseArray) {
        FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
        fastXmlSerializer.setOutput(outputStream, StandardCharsets.UTF_8.name());
        fastXmlSerializer.startDocument((String) null, Boolean.TRUE);
        fastXmlSerializer.startTag((String) null, "bs");
        fastXmlSerializer.attribute((String) null, "v", "2");
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            int iKeyAt = sparseArray.keyAt(i);
            List<BubbleEntity> list = (List) sparseArray.valueAt(i);
            fastXmlSerializer.startTag((String) null, "bs");
            fastXmlSerializer.attribute((String) null, NetworkAnalyticsConstants.DataPoints.UID, String.valueOf(iKeyAt));
            list.getClass();
            for (BubbleEntity bubbleEntity : list) {
                try {
                    fastXmlSerializer.startTag((String) null, "bb");
                    fastXmlSerializer.attribute((String) null, NetworkAnalyticsConstants.DataPoints.UID, String.valueOf(bubbleEntity.userId));
                    fastXmlSerializer.attribute((String) null, "pkg", bubbleEntity.packageName);
                    fastXmlSerializer.attribute((String) null, "sid", bubbleEntity.shortcutId);
                    fastXmlSerializer.attribute((String) null, "key", bubbleEntity.key);
                    fastXmlSerializer.attribute((String) null, "h", String.valueOf(bubbleEntity.desiredHeight));
                    fastXmlSerializer.attribute((String) null, "hid", String.valueOf(bubbleEntity.desiredHeightResId));
                    String str = bubbleEntity.title;
                    if (str != null) {
                        fastXmlSerializer.attribute((String) null, "t", str);
                    }
                    fastXmlSerializer.attribute((String) null, "tid", String.valueOf(bubbleEntity.taskId));
                    String str2 = bubbleEntity.locus;
                    if (str2 != null) {
                        fastXmlSerializer.attribute((String) null, "l", str2);
                    }
                    fastXmlSerializer.attribute((String) null, "d", String.valueOf(bubbleEntity.isDismissable));
                    fastXmlSerializer.endTag((String) null, "bb");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            fastXmlSerializer.endTag((String) null, "bs");
        }
        fastXmlSerializer.endTag((String) null, "bs");
        fastXmlSerializer.endDocument();
    }
}
