package com.android.server.pm;

import android.content.ComponentName;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import com.android.internal.util.XmlUtils;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.modules.utils.TypedXmlPullParser;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PreferredComponent {
    public final boolean mAlways;
    public final PreferredActivity mCallbacks;
    public final ComponentName mComponent;
    public final int mMatch;
    public final String mParseError;
    public final String[] mSetClasses;
    public final String[] mSetComponents;
    public final String[] mSetPackages;
    public final String mShortComponent;

    public PreferredComponent(PreferredActivity preferredActivity, int i, ComponentName[] componentNameArr, ComponentName componentName, boolean z) {
        this.mCallbacks = preferredActivity;
        this.mMatch = 268369920 & i;
        this.mComponent = componentName;
        this.mAlways = z;
        this.mShortComponent = componentName.flattenToShortString();
        this.mParseError = null;
        if (componentNameArr == null) {
            this.mSetPackages = null;
            this.mSetClasses = null;
            this.mSetComponents = null;
            return;
        }
        int length = componentNameArr.length;
        String[] strArr = new String[length];
        String[] strArr2 = new String[length];
        String[] strArr3 = new String[length];
        for (int i2 = 0; i2 < length; i2++) {
            ComponentName componentName2 = componentNameArr[i2];
            if (componentName2 == null) {
                this.mSetPackages = null;
                this.mSetClasses = null;
                this.mSetComponents = null;
                return;
            } else {
                strArr[i2] = componentName2.getPackageName().intern();
                strArr2[i2] = componentName2.getClassName().intern();
                strArr3[i2] = componentName2.flattenToShortString();
            }
        }
        this.mSetPackages = strArr;
        this.mSetClasses = strArr2;
        this.mSetComponents = strArr3;
    }

    public PreferredComponent(PreferredActivity preferredActivity, TypedXmlPullParser typedXmlPullParser) {
        this.mCallbacks = preferredActivity;
        String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "name");
        this.mShortComponent = attributeValue;
        ComponentName unflattenFromString = ComponentName.unflattenFromString(attributeValue);
        this.mComponent = unflattenFromString;
        if (unflattenFromString == null) {
            this.mParseError = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("Bad activity name ", attributeValue);
        }
        int i = 0;
        this.mMatch = typedXmlPullParser.getAttributeIntHex((String) null, "match", 0);
        int attributeInt = typedXmlPullParser.getAttributeInt((String) null, "set", 0);
        this.mAlways = typedXmlPullParser.getAttributeBoolean((String) null, "always", true);
        String[] strArr = attributeInt > 0 ? new String[attributeInt] : null;
        String[] strArr2 = attributeInt > 0 ? new String[attributeInt] : null;
        String[] strArr3 = attributeInt > 0 ? new String[attributeInt] : null;
        int depth = typedXmlPullParser.getDepth();
        while (true) {
            int next = typedXmlPullParser.next();
            if (next == 1 || (next == 3 && typedXmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                String name = typedXmlPullParser.getName();
                if (name.equals("set")) {
                    String attributeValue2 = typedXmlPullParser.getAttributeValue((String) null, "name");
                    if (attributeValue2 == null) {
                        if (this.mParseError == null) {
                            this.mParseError = "No name in set tag in preferred activity " + this.mShortComponent;
                        }
                    } else if (i < attributeInt) {
                        ComponentName unflattenFromString2 = ComponentName.unflattenFromString(attributeValue2);
                        if (unflattenFromString2 != null) {
                            strArr[i] = unflattenFromString2.getPackageName();
                            strArr2[i] = unflattenFromString2.getClassName();
                            strArr3[i] = attributeValue2;
                            i++;
                        } else if (this.mParseError == null) {
                            StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Bad set name ", attributeValue2, " in preferred activity ");
                            m.append(this.mShortComponent);
                            this.mParseError = m.toString();
                        }
                    } else if (this.mParseError == null) {
                        this.mParseError = "Too many set tags in preferred activity " + this.mShortComponent;
                    }
                    XmlUtils.skipCurrentTag(typedXmlPullParser);
                } else {
                    PreferredActivity preferredActivity2 = this.mCallbacks;
                    preferredActivity2.getClass();
                    if (name.equals("filter")) {
                        preferredActivity2.mFilter.readFromXml(typedXmlPullParser);
                    } else {
                        String m2 = XmlUtils$$ExternalSyntheticOutline0.m(typedXmlPullParser, new StringBuilder("Unknown element under <preferred-activities>: "));
                        boolean z = PackageManagerService.DEBUG_COMPRESSION;
                        PackageManagerServiceUtils.logCriticalInfo(5, m2);
                        XmlUtils.skipCurrentTag(typedXmlPullParser);
                    }
                }
            }
        }
        if (i != attributeInt && this.mParseError == null) {
            StringBuilder m3 = ArrayUtils$$ExternalSyntheticOutline0.m(attributeInt, i, "Not enough set tags (expected ", " but found ", ") in ");
            m3.append(this.mShortComponent);
            this.mParseError = m3.toString();
        }
        this.mSetPackages = strArr;
        this.mSetClasses = strArr2;
        this.mSetComponents = strArr3;
    }
}
