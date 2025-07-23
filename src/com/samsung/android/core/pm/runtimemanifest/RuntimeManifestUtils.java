package com.samsung.android.core.pm.runtimemanifest;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Slog;
import android.util.TypedValue;
import com.android.internal.R;
import com.android.internal.util.XmlUtils;
import com.samsung.android.core.pm.runtimemanifest.RuntimeManifestPolicies;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes6.dex */
public class RuntimeManifestUtils {
    public static final String ATTR_MAX_VALUE = "maxValue";
    public static final String ATTR_MIN_VALUE = "minValue";
    public static final String ATTR_PROPERTY_NAME = "propertyName";
    public static final String ATTR_PROPERTY_VALUE = "propertyValue";
    public static final String ATTR_TYPE = "type";
    public static final String ATTR_VALUE = "value";
    public static final boolean DEBUG = true;
    public static final String META_RUNTIME_MANIFEST = "runtime.manifest.overlay";
    public static final String TAG = "RuntimeManifestUtils";
    public static final String TAG_ACTIVITY = "activity";
    public static final String TAG_APPLICATION = "application";
    public static final String TAG_POLICY = "policy";
    public static final String TAG_PROVIDER = "provider";
    public static final String TAG_RECEIVER = "receiver";
    public static final String TAG_RUNTIME_MANIFEST = "runtime-manifest";
    public static final String TAG_SERVICE = "service";
    public static final String SALESCODE = SystemProperties.get("ro.csc.sales_code");
    public static final String COUNTRYCODE = SystemProperties.get("ro.csc.countryiso_code");
    public static final String ONEUI_VERSION = SystemProperties.get("ro.build.version.oneui");
    private static boolean sIsTest = false;
    private static String sSalesCodeForTest = "";
    private static String sCountryCodeForTest = "";
    private static String sOneuiVersionForTest = "";

    public static String getSalesCode() {
        return sIsTest ? sSalesCodeForTest : SALESCODE;
    }

    public static String getCountryCode() {
        return sIsTest ? sCountryCodeForTest : COUNTRYCODE;
    }

    public static long getOneUiVersion() {
        try {
            return Long.parseLong(sIsTest ? sOneuiVersionForTest : ONEUI_VERSION);
        } catch (NumberFormatException unused) {
            return -1L;
        }
    }

    public static void setSalesCodeForTest(String str) {
        sSalesCodeForTest = str;
    }

    public static void setCountryCode(String str) {
        sCountryCodeForTest = str;
    }

    public static void setOneUiVersionForTest(String str) {
        sOneuiVersionForTest = str;
    }

    public static void setTestMode(boolean z) {
        sIsTest = z;
    }

    static List<RuntimeManifestPolicies.PolicyInfo> parseOverlayPolicies(XmlResourceParser xmlResourceParser, Resources resources) throws IOException, XmlPullParserException {
        ArrayList arrayList = new ArrayList();
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if (next == 1 || (next == 3 && xmlResourceParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4) {
                RuntimeManifestPolicies.PolicyInfo policyInfo = new RuntimeManifestPolicies.PolicyInfo();
                String name = xmlResourceParser.getName();
                if (name.equals(TAG_POLICY)) {
                    String attributeValue = xmlResourceParser.getAttributeValue(null, "type");
                    if (!TextUtils.isEmpty(attributeValue)) {
                        policyInfo.setType(attributeValue);
                    }
                    String attributeValue2 = xmlResourceParser.getAttributeValue(null, "value");
                    if (!TextUtils.isEmpty(attributeValue2)) {
                        policyInfo.setValue(attributeValue2);
                    }
                    String attributeValue3 = xmlResourceParser.getAttributeValue(null, ATTR_MIN_VALUE);
                    if (!TextUtils.isEmpty(attributeValue3)) {
                        policyInfo.setMinValue(attributeValue3);
                    }
                    String attributeValue4 = xmlResourceParser.getAttributeValue(null, ATTR_MAX_VALUE);
                    if (!TextUtils.isEmpty(attributeValue4)) {
                        policyInfo.setMaxValue(attributeValue4);
                    }
                    String attributeValue5 = xmlResourceParser.getAttributeValue(null, ATTR_PROPERTY_NAME);
                    if (!TextUtils.isEmpty(attributeValue5)) {
                        policyInfo.setPropertyName(attributeValue5);
                    }
                    String attributeValue6 = xmlResourceParser.getAttributeValue(null, ATTR_PROPERTY_VALUE);
                    if (!TextUtils.isEmpty(attributeValue6)) {
                        policyInfo.setPropertyValue(attributeValue6);
                    }
                    TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestActivity);
                    try {
                        TypedValue peekValue = obtainAttributes.peekValue(1);
                        if (peekValue != null) {
                            if (peekValue.resourceId == 0) {
                                policyInfo.setLabelRes(0);
                                policyInfo.setCoercedLabel(peekValue.coerceToString());
                            } else {
                                policyInfo.setLabelRes(peekValue.resourceId);
                                policyInfo.setCoercedLabel(null);
                            }
                        }
                        int resourceId = obtainAttributes.getResourceId(2, 0);
                        if (resourceId != 0) {
                            policyInfo.setIconRes(resourceId);
                        }
                        if (obtainAttributes.hasValueOrEmpty(5)) {
                            policyInfo.setEnabled(obtainAttributes.getBoolean(5, true));
                        }
                        obtainAttributes.recycle();
                        obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestIntentFilter);
                        try {
                            policyInfo.setPriority(obtainAttributes.getInt(2, 0));
                            arrayList.add(policyInfo);
                            obtainAttributes.recycle();
                            Slog.d("RuntimeManifestUtils", "Parsed " + policyInfo.toString());
                        } finally {
                        }
                    } finally {
                    }
                } else {
                    Slog.d("RuntimeManifestUtils", "Unknown element under <runtime-manifest>: " + name);
                    XmlUtils.skipCurrentTag(xmlResourceParser);
                }
            }
        }
        return arrayList;
    }

    public static RuntimeManifestPolicies parseRuntimeManifestPolicies(XmlResourceParser xmlResourceParser, Resources resources) throws IOException, XmlPullParserException {
        RuntimeManifestPolicies runtimeManifestPolicies = new RuntimeManifestPolicies();
        ArrayList arrayList = new ArrayList();
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        HashMap hashMap3 = new HashMap();
        HashMap hashMap4 = new HashMap();
        XmlUtils.beginDocument(xmlResourceParser, TAG_RUNTIME_MANIFEST);
        while (true) {
            XmlUtils.nextElement(xmlResourceParser);
            String name = xmlResourceParser.getName();
            if (name != null) {
                if (name.equals("application")) {
                    arrayList.addAll(parseOverlayPolicies(xmlResourceParser, resources));
                } else {
                    TypedArray obtainAttributes = resources.obtainAttributes(xmlResourceParser, R.styleable.AndroidManifestActivity);
                    try {
                        String string = obtainAttributes.getString(3);
                        if (string == null) {
                            continue;
                        } else if (name.equals("activity")) {
                            List<RuntimeManifestPolicies.PolicyInfo> parseOverlayPolicies = parseOverlayPolicies(xmlResourceParser, resources);
                            if (!hashMap.containsKey(string) && parseOverlayPolicies.size() > 0) {
                                hashMap.put(string, parseOverlayPolicies);
                            }
                        } else if (name.equals("receiver")) {
                            List<RuntimeManifestPolicies.PolicyInfo> parseOverlayPolicies2 = parseOverlayPolicies(xmlResourceParser, resources);
                            if (!hashMap2.containsKey(string) && parseOverlayPolicies2.size() > 0) {
                                hashMap2.put(string, parseOverlayPolicies2);
                            }
                        } else if (name.equals("service")) {
                            List<RuntimeManifestPolicies.PolicyInfo> parseOverlayPolicies3 = parseOverlayPolicies(xmlResourceParser, resources);
                            if (!hashMap3.containsKey(string) && parseOverlayPolicies3.size() > 0) {
                                hashMap3.put(string, parseOverlayPolicies3);
                            }
                        } else if (name.equals(TAG_PROVIDER)) {
                            List<RuntimeManifestPolicies.PolicyInfo> parseOverlayPolicies4 = parseOverlayPolicies(xmlResourceParser, resources);
                            if (!hashMap4.containsKey(string) && parseOverlayPolicies4.size() > 0) {
                                hashMap4.put(string, parseOverlayPolicies4);
                            }
                        } else {
                            throw new XmlPullParserException("Unknown element under <runtime-manifest>: " + name);
                        }
                    } finally {
                        obtainAttributes.recycle();
                    }
                }
            } else {
                runtimeManifestPolicies.addApplicationPolicies(arrayList);
                runtimeManifestPolicies.addActivityPolicies(hashMap);
                runtimeManifestPolicies.addReceiverPolicies(hashMap2);
                runtimeManifestPolicies.addServicePolicies(hashMap3);
                runtimeManifestPolicies.addProviderPolicies(hashMap4);
                return runtimeManifestPolicies;
            }
        }
    }

    public static RuntimeManifestPolicies.PolicyInfo getMatchingPolicy(List<RuntimeManifestPolicies.PolicyInfo> list) {
        for (RuntimeManifestPolicies.PolicyInfo policyInfo : list) {
            String type = policyInfo.getType();
            if (!TextUtils.isEmpty(type)) {
                if (type.equalsIgnoreCase("SALESCODE")) {
                    String value = policyInfo.getValue();
                    if (!TextUtils.isEmpty(value) && value.equalsIgnoreCase(getSalesCode())) {
                        Slog.d("RuntimeManifestUtils", "Matched policy(salescode): " + policyInfo);
                        return policyInfo;
                    }
                } else if (type.equalsIgnoreCase("COUNTRYCODE")) {
                    String value2 = policyInfo.getValue();
                    if (!TextUtils.isEmpty(value2) && value2.equalsIgnoreCase(getCountryCode())) {
                        Slog.d("RuntimeManifestUtils", "Matched policy(countrycode): " + policyInfo);
                        return policyInfo;
                    }
                } else if (type.equalsIgnoreCase("ONEUI") && matchOneUiPolicy(policyInfo.getMinValue(), policyInfo.getMaxValue(), getOneUiVersion())) {
                    Slog.d("RuntimeManifestUtils", "Matched policy(oneui): " + policyInfo);
                    return policyInfo;
                }
            }
        }
        return null;
    }

    private static boolean matchOneUiPolicy(long j, long j2, long j3) {
        if (j3 < 0) {
            Slog.w("RuntimeManifestUtils", "Invalid current OneUi version " + j3);
            return false;
        }
        if (j < 0 && j2 < 0) {
            Slog.w("RuntimeManifestUtils", "Invalid value set, min: " + j + ", max: " + j2);
            return false;
        }
        if (j >= 0 && j3 < j) {
            Slog.w("RuntimeManifestUtils", "It's lower than minValue " + j);
            return false;
        }
        if (j2 < 0 || j3 <= j2) {
            return true;
        }
        Slog.w("RuntimeManifestUtils", "It's higher than maxValue " + j2);
        return false;
    }

    public static boolean useLegacyRuntimeManifest(Bundle bundle) {
        return bundle == null || !bundle.containsKey(META_RUNTIME_MANIFEST);
    }

    public static String buildClassName(String str, CharSequence charSequence) {
        if (charSequence == null || charSequence.length() <= 0) {
            return null;
        }
        String charSequence2 = charSequence.toString();
        if (charSequence2.charAt(0) == '.') {
            return str + charSequence2;
        }
        if (charSequence2.indexOf(46) >= 0) {
            return charSequence2;
        }
        return str + '.' + charSequence2;
    }
}
