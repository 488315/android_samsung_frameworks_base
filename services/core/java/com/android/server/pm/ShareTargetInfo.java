package com.android.server.pm;

import android.text.TextUtils;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ShareTargetInfo {
    public final String[] mCategories;
    public final String mTargetClass;
    public final TargetData[] mTargetData;

    public final class TargetData {
        public final String mHost;
        public final String mMimeType;
        public final String mPath;
        public final String mPathPattern;
        public final String mPathPrefix;
        public final String mPort;
        public final String mScheme;

        public TargetData(
                String str,
                String str2,
                String str3,
                String str4,
                String str5,
                String str6,
                String str7) {
            this.mScheme = str;
            this.mHost = str2;
            this.mPort = str3;
            this.mPath = str4;
            this.mPathPattern = str5;
            this.mPathPrefix = str6;
            this.mMimeType = str7;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            toStringInner(sb);
            return sb.toString();
        }

        public final void toStringInner(StringBuilder sb) {
            String str = this.mScheme;
            if (!TextUtils.isEmpty(str)) {
                sb.append(" scheme=");
                sb.append(str);
            }
            String str2 = this.mHost;
            if (!TextUtils.isEmpty(str2)) {
                sb.append(" host=");
                sb.append(str2);
            }
            String str3 = this.mPort;
            if (!TextUtils.isEmpty(str3)) {
                sb.append(" port=");
                sb.append(str3);
            }
            String str4 = this.mPath;
            if (!TextUtils.isEmpty(str4)) {
                sb.append(" path=");
                sb.append(str4);
            }
            String str5 = this.mPathPattern;
            if (!TextUtils.isEmpty(str5)) {
                sb.append(" pathPattern=");
                sb.append(str5);
            }
            String str6 = this.mPathPrefix;
            if (!TextUtils.isEmpty(str6)) {
                sb.append(" pathPrefix=");
                sb.append(str6);
            }
            String str7 = this.mMimeType;
            if (TextUtils.isEmpty(str7)) {
                return;
            }
            sb.append(" mimeType=");
            sb.append(str7);
        }
    }

    public ShareTargetInfo(TargetData[] targetDataArr, String str, String[] strArr) {
        this.mTargetData = targetDataArr;
        this.mTargetClass = str;
        this.mCategories = strArr;
    }

    public static ShareTargetInfo loadFromXml(TypedXmlPullParser typedXmlPullParser) {
        AtomicBoolean atomicBoolean = ShortcutService.sIsEmergencyMode;
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "targetClass");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next != 1) {
                if (next != 2) {
                    if (next == 3 && typedXmlPullParser.getName().equals("share-target")) {
                        break;
                    }
                } else {
                    String name = typedXmlPullParser.getName();
                    name.getClass();
                    if (name.equals("data")) {
                        AtomicBoolean atomicBoolean2 = ShortcutService.sIsEmergencyMode;
                        arrayList.add(
                                new TargetData(
                                        typedXmlPullParser.getAttributeValue(
                                                (String) null, "scheme"),
                                        typedXmlPullParser.getAttributeValue((String) null, "host"),
                                        typedXmlPullParser.getAttributeValue((String) null, "port"),
                                        typedXmlPullParser.getAttributeValue((String) null, "path"),
                                        typedXmlPullParser.getAttributeValue(
                                                (String) null, "pathPattern"),
                                        typedXmlPullParser.getAttributeValue(
                                                (String) null, "pathPrefix"),
                                        typedXmlPullParser.getAttributeValue(
                                                (String) null, "mimeType")));
                    } else if (name.equals("category")) {
                        arrayList2.add(typedXmlPullParser.getAttributeValue((String) null, "name"));
                    }
                }
            } else {
                break;
            }
        }
        if (arrayList.isEmpty() || attributeValue == null || arrayList2.isEmpty()) {
            return null;
        }
        return new ShareTargetInfo(
                (TargetData[]) arrayList.toArray(new TargetData[arrayList.size()]),
                attributeValue,
                (String[]) arrayList2.toArray(new String[arrayList2.size()]));
    }

    public final void saveToXml(TypedXmlSerializer typedXmlSerializer) {
        typedXmlSerializer.startTag((String) null, "share-target");
        ShortcutService.writeAttr(typedXmlSerializer, "targetClass", this.mTargetClass);
        int i = 0;
        int i2 = 0;
        while (true) {
            TargetData[] targetDataArr = this.mTargetData;
            if (i2 >= targetDataArr.length) {
                break;
            }
            typedXmlSerializer.startTag((String) null, "data");
            ShortcutService.writeAttr(typedXmlSerializer, "scheme", targetDataArr[i2].mScheme);
            ShortcutService.writeAttr(typedXmlSerializer, "host", targetDataArr[i2].mHost);
            ShortcutService.writeAttr(typedXmlSerializer, "port", targetDataArr[i2].mPort);
            ShortcutService.writeAttr(typedXmlSerializer, "path", targetDataArr[i2].mPath);
            ShortcutService.writeAttr(
                    typedXmlSerializer, "pathPattern", targetDataArr[i2].mPathPattern);
            ShortcutService.writeAttr(
                    typedXmlSerializer, "pathPrefix", targetDataArr[i2].mPathPrefix);
            ShortcutService.writeAttr(typedXmlSerializer, "mimeType", targetDataArr[i2].mMimeType);
            typedXmlSerializer.endTag((String) null, "data");
            i2++;
        }
        while (true) {
            String[] strArr = this.mCategories;
            if (i >= strArr.length) {
                typedXmlSerializer.endTag((String) null, "share-target");
                return;
            }
            typedXmlSerializer.startTag((String) null, "category");
            ShortcutService.writeAttr(typedXmlSerializer, "name", strArr[i]);
            typedXmlSerializer.endTag((String) null, "category");
            i++;
        }
    }

    public final String toString() {
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m("targetClass=");
        m.append(this.mTargetClass);
        int i = 0;
        int i2 = 0;
        while (true) {
            TargetData[] targetDataArr = this.mTargetData;
            if (i2 >= targetDataArr.length) {
                break;
            }
            m.append(" data={");
            targetDataArr[i2].toStringInner(m);
            m.append("}");
            i2++;
        }
        while (true) {
            String[] strArr = this.mCategories;
            if (i >= strArr.length) {
                return m.toString();
            }
            m.append(" category=");
            m.append(strArr[i]);
            i++;
        }
    }
}
