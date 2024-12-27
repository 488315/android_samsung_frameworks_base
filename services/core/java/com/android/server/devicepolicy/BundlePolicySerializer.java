package com.android.server.devicepolicy;

import android.app.admin.BundlePolicyValue;
import android.app.admin.PolicyValue;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BundlePolicySerializer extends PolicySerializer {
    public static void readBundle(
            Bundle bundle, ArrayList arrayList, TypedXmlPullParser typedXmlPullParser) {
        if (typedXmlPullParser.getEventType() == 2
                && typedXmlPullParser.getName().equals("entry")) {
            String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "key");
            String attributeValue2 = typedXmlPullParser.getAttributeValue((String) null, "type");
            int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "m", -1);
            if (attributeInt != -1) {
                arrayList.clear();
                while (attributeInt > 0) {
                    int next = typedXmlPullParser.next();
                    if (next == 1) {
                        break;
                    }
                    if (next == 2 && typedXmlPullParser.getName().equals("value")) {
                        arrayList.add(typedXmlPullParser.nextText());
                        attributeInt--;
                    }
                }
                String[] strArr = new String[arrayList.size()];
                arrayList.toArray(strArr);
                bundle.putStringArray(attributeValue, strArr);
                return;
            }
            if ("B".equals(attributeValue2)) {
                Bundle bundle2 = new Bundle();
                int depth = typedXmlPullParser.getDepth();
                while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                    readBundle(bundle2, arrayList, typedXmlPullParser);
                }
                bundle.putBundle(attributeValue, bundle2);
                return;
            }
            if (!"BA".equals(attributeValue2)) {
                String nextText = typedXmlPullParser.nextText();
                if ("b".equals(attributeValue2)) {
                    bundle.putBoolean(attributeValue, Boolean.parseBoolean(nextText));
                    return;
                } else if ("i".equals(attributeValue2)) {
                    bundle.putInt(attributeValue, Integer.parseInt(nextText));
                    return;
                } else {
                    bundle.putString(attributeValue, nextText);
                    return;
                }
            }
            int depth2 = typedXmlPullParser.getDepth();
            ArrayList arrayList2 = new ArrayList();
            while (XmlUtils.nextElementWithin(typedXmlPullParser, depth2)) {
                Bundle bundle3 = new Bundle();
                int depth3 = typedXmlPullParser.getDepth();
                while (XmlUtils.nextElementWithin(typedXmlPullParser, depth3)) {
                    readBundle(bundle3, arrayList, typedXmlPullParser);
                }
                arrayList2.add(bundle3);
            }
            bundle.putParcelableArray(
                    attributeValue,
                    (Parcelable[]) arrayList2.toArray(new Bundle[arrayList2.size()]));
        }
    }

    public static void writeBundle(Bundle bundle, TypedXmlSerializer typedXmlSerializer) {
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            typedXmlSerializer.startTag((String) null, "entry");
            typedXmlSerializer.attribute((String) null, "key", str);
            if (obj instanceof Boolean) {
                typedXmlSerializer.attribute((String) null, "type", "b");
                typedXmlSerializer.text(obj.toString());
            } else if (obj instanceof Integer) {
                typedXmlSerializer.attribute((String) null, "type", "i");
                typedXmlSerializer.text(obj.toString());
            } else if (obj == null || (obj instanceof String)) {
                typedXmlSerializer.attribute((String) null, "type", "s");
                typedXmlSerializer.text(obj != null ? (String) obj : "");
            } else if (obj instanceof Bundle) {
                typedXmlSerializer.attribute((String) null, "type", "B");
                writeBundle((Bundle) obj, typedXmlSerializer);
            } else {
                int i = 0;
                if (obj instanceof Parcelable[]) {
                    typedXmlSerializer.attribute((String) null, "type", "BA");
                    Parcelable[] parcelableArr = (Parcelable[]) obj;
                    int length = parcelableArr.length;
                    while (i < length) {
                        Parcelable parcelable = parcelableArr[i];
                        if (!(parcelable instanceof Bundle)) {
                            throw new IllegalArgumentException(
                                    "bundle-array can only hold Bundles");
                        }
                        typedXmlSerializer.startTag((String) null, "entry");
                        typedXmlSerializer.attribute((String) null, "type", "B");
                        writeBundle((Bundle) parcelable, typedXmlSerializer);
                        typedXmlSerializer.endTag((String) null, "entry");
                        i++;
                    }
                } else {
                    typedXmlSerializer.attribute((String) null, "type", "sa");
                    String[] strArr = (String[]) obj;
                    typedXmlSerializer.attributeInt((String) null, "m", strArr.length);
                    int length2 = strArr.length;
                    while (i < length2) {
                        String str2 = strArr[i];
                        typedXmlSerializer.startTag((String) null, "value");
                        if (str2 == null) {
                            str2 = "";
                        }
                        typedXmlSerializer.text(str2);
                        typedXmlSerializer.endTag((String) null, "value");
                        i++;
                    }
                }
            }
            typedXmlSerializer.endTag((String) null, "entry");
        }
    }

    @Override // com.android.server.devicepolicy.PolicySerializer
    public final PolicyValue readFromXml(TypedXmlPullParser typedXmlPullParser) {
        Bundle bundle = new Bundle();
        ArrayList arrayList = new ArrayList();
        try {
            int depth = typedXmlPullParser.getDepth();
            while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                readBundle(bundle, arrayList, typedXmlPullParser);
            }
            return new BundlePolicyValue(bundle);
        } catch (IOException | XmlPullParserException e) {
            Log.e("BundlePolicySerializer", "Error parsing Bundle policy.", e);
            return null;
        }
    }

    @Override // com.android.server.devicepolicy.PolicySerializer
    public final void saveToXml(Object obj, TypedXmlSerializer typedXmlSerializer) {
        Bundle bundle = (Bundle) obj;
        Objects.requireNonNull(bundle);
        writeBundle(bundle, typedXmlSerializer);
    }
}
