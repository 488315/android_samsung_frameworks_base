package com.android.server.devicepolicy;

import android.app.admin.BundlePolicyValue;
import android.app.admin.PackagePolicyKey;
import android.app.admin.PolicyKey;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import com.android.internal.util.XmlUtils;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public final class BundlePolicySerializer extends PolicySerializer {
    @Override // com.android.server.devicepolicy.PolicySerializer
    public void saveToXml(PolicyKey policyKey, TypedXmlSerializer typedXmlSerializer, Bundle bundle) {
        Objects.requireNonNull(bundle);
        Objects.requireNonNull(policyKey);
        if (!(policyKey instanceof PackagePolicyKey)) {
            throw new IllegalArgumentException("policyKey is not of type PackagePolicyKey");
        }
        writeBundle(bundle, typedXmlSerializer);
    }

    @Override // com.android.server.devicepolicy.PolicySerializer
    /* renamed from: readFromXml, reason: merged with bridge method [inline-methods] */
    public BundlePolicyValue mo5067readFromXml(TypedXmlPullParser typedXmlPullParser) {
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

    public static void readBundle(Bundle bundle, ArrayList arrayList, TypedXmlPullParser typedXmlPullParser) {
        if (typedXmlPullParser.getEventType() == 2 && typedXmlPullParser.getName().equals("entry")) {
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
                        arrayList.add(typedXmlPullParser.nextText().trim());
                        attributeInt--;
                    }
                }
                String[] strArr = new String[arrayList.size()];
                arrayList.toArray(strArr);
                bundle.putStringArray(attributeValue, strArr);
                return;
            }
            if ("B".equals(attributeValue2)) {
                bundle.putBundle(attributeValue, readBundleEntry(typedXmlPullParser, arrayList));
                return;
            }
            if ("BA".equals(attributeValue2)) {
                int depth = typedXmlPullParser.getDepth();
                ArrayList arrayList2 = new ArrayList();
                while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
                    arrayList2.add(readBundleEntry(typedXmlPullParser, arrayList));
                }
                bundle.putParcelableArray(attributeValue, (Parcelable[]) arrayList2.toArray(new Bundle[arrayList2.size()]));
                return;
            }
            String trim = typedXmlPullParser.nextText().trim();
            if ("b".equals(attributeValue2)) {
                bundle.putBoolean(attributeValue, Boolean.parseBoolean(trim));
            } else if ("i".equals(attributeValue2)) {
                bundle.putInt(attributeValue, Integer.parseInt(trim));
            } else {
                bundle.putString(attributeValue, trim);
            }
        }
    }

    public static Bundle readBundleEntry(TypedXmlPullParser typedXmlPullParser, ArrayList arrayList) {
        Bundle bundle = new Bundle();
        int depth = typedXmlPullParser.getDepth();
        while (XmlUtils.nextElementWithin(typedXmlPullParser, depth)) {
            readBundle(bundle, arrayList, typedXmlPullParser);
        }
        return bundle;
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
                            throw new IllegalArgumentException("bundle-array can only hold Bundles");
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
}
