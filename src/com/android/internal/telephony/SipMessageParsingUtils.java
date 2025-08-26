package com.android.internal.telephony;

import android.net.Uri;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes4.dex */
public class SipMessageParsingUtils {
    private static final String ACCEPT_CONTACT_HEADER_KEY = "accept-contact";
    private static final String ACCEPT_CONTACT_HEADER_KEY_COMPACT = "a";
    private static final String BRANCH_PARAM_KEY = "branch";
    private static final String CALL_ID_SIP_HEADER_KEY = "call-id";
    private static final String CALL_ID_SIP_HEADER_KEY_COMPACT = "i";
    private static final String FROM_HEADER_KEY = "from";
    private static final String FROM_HEADER_KEY_COMPACT = "f";
    private static final String HEADER_KEY_VALUE_SEPARATOR = ":";
    private static final String PARAM_KEY_VALUE_SEPARATOR = "=";
    private static final String PARAM_SEPARATOR = ";";
    private static final String[] SIP_REQUEST_METHODS = {"INVITE", "ACK", "OPTIONS", "BYE", "CANCEL", "REGISTER", "PRACK", "SUBSCRIBE", "NOTIFY", "PUBLISH", "INFO", "REFER", "MESSAGE", "UPDATE"};
    private static final String SIP_VERSION_2 = "SIP/2.0";
    private static final String SUBHEADER_VALUE_SEPARATOR = ",";
    private static final String TAG = "SipMessageParsingUtils";
    private static final String TAG_PARAM_KEY = "tag";
    private static final String TO_HEADER_KEY = "to";
    private static final String TO_HEADER_KEY_COMPACT = "t";
    private static final String VIA_SIP_HEADER_KEY = "via";
    private static final String VIA_SIP_HEADER_KEY_COMPACT = "v";

    public static boolean isSipRequest(String str) {
        String[] strArrSplitStartLineAndVerify = splitStartLineAndVerify(str);
        if (strArrSplitStartLineAndVerify == null) {
            return false;
        }
        return verifySipRequest(strArrSplitStartLineAndVerify);
    }

    public static boolean isSipResponse(String str) {
        String[] strArrSplitStartLineAndVerify = splitStartLineAndVerify(str);
        if (strArrSplitStartLineAndVerify == null) {
            return false;
        }
        return verifySipResponse(strArrSplitStartLineAndVerify);
    }

    public static String getTransactionId(String str) {
        Iterator<Pair<String, String>> it = parseHeaders(str, true, VIA_SIP_HEADER_KEY, "v").iterator();
        while (it.hasNext()) {
            for (String str2 : it.next().second.split(",")) {
                String parameterValue = getParameterValue(str2, BRANCH_PARAM_KEY);
                if (parameterValue != null) {
                    return parameterValue;
                }
            }
        }
        return null;
    }

    private static String getParameterValue(String str, String str2) {
        String[] strArrSplit = str.split(";");
        if (strArrSplit.length < 2) {
            return null;
        }
        for (String str3 : strArrSplit) {
            String[] strArrSplit2 = str3.split(PARAM_KEY_VALUE_SEPARATOR);
            if (strArrSplit2.length >= 2) {
                if (strArrSplit2.length > 2) {
                    Log.w(TAG, "getParameterValue: unexpected parameter" + Arrays.toString(strArrSplit2));
                }
                strArrSplit2[0] = strArrSplit2[0].trim();
                strArrSplit2[1] = strArrSplit2[1].trim();
                if (str2.equalsIgnoreCase(strArrSplit2[0])) {
                    return strArrSplit2[1];
                }
            }
        }
        return null;
    }

    public static String getCallId(String str) {
        List<Pair<String, String>> headers = parseHeaders(str, true, CALL_ID_SIP_HEADER_KEY, CALL_ID_SIP_HEADER_KEY_COMPACT);
        if (headers.isEmpty()) {
            return null;
        }
        return headers.get(0).second;
    }

    public static String getFromTag(String str) {
        List<Pair<String, String>> headers = parseHeaders(str, true, FROM_HEADER_KEY, "f");
        if (headers.isEmpty()) {
            return null;
        }
        return getParameterValue(headers.get(0).second, "tag");
    }

    public static String getToTag(String str) {
        List<Pair<String, String>> headers = parseHeaders(str, true, TO_HEADER_KEY, "t");
        if (headers.isEmpty()) {
            return null;
        }
        return getParameterValue(headers.get(0).second, "tag");
    }

    public static String[] splitStartLineAndVerify(String str) {
        String[] strArrSplit = str.split(" ", 3);
        if (isStartLineMalformed(strArrSplit)) {
            return null;
        }
        return strArrSplit;
    }

    public static Set<String> getAcceptContactFeatureTags(String str) {
        List<Pair<String, String>> headers = parseHeaders(str, false, ACCEPT_CONTACT_HEADER_KEY, "a");
        if (str.isEmpty()) {
            return Collections.EMPTY_SET;
        }
        ArraySet arraySet = new ArraySet();
        Iterator<Pair<String, String>> it = headers.iterator();
        while (it.hasNext()) {
            String[] strArrSplit = it.next().second.split(";");
            if (strArrSplit.length >= 2) {
                for (String str2 : (Set) Arrays.asList(strArrSplit).subList(1, strArrSplit.length).stream().map(new Function() { // from class: com.android.internal.telephony.SipMessageParsingUtils$$ExternalSyntheticLambda3
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return ((String) obj).trim();
                    }
                }).filter(new Predicate() { // from class: com.android.internal.telephony.SipMessageParsingUtils$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((String) obj).startsWith("+");
                    }
                }).collect(Collectors.toSet())) {
                    String[] strArrSplit2 = str2.split(PARAM_KEY_VALUE_SEPARATOR, 2);
                    if (strArrSplit2.length < 2) {
                        arraySet.add(str2);
                    } else {
                        for (String str3 : splitParamValue(strArrSplit2[1])) {
                            arraySet.add(strArrSplit2[0] + PARAM_KEY_VALUE_SEPARATOR + str3);
                        }
                    }
                }
            }
        }
        return arraySet;
    }

    private static String[] splitParamValue(String str) {
        if (!str.startsWith("\"") && !str.endsWith("\"")) {
            return new String[]{str};
        }
        String[] strArrSplit = str.substring(1, str.length() - 1).split(",");
        for (int i = 0; i < strArrSplit.length; i++) {
            strArrSplit[i] = "\"" + strArrSplit[i] + "\"";
        }
        return strArrSplit;
    }

    private static boolean isStartLineMalformed(String[] strArr) {
        return strArr == null || strArr.length == 0 || strArr.length != 3;
    }

    private static boolean verifySipRequest(final String[] strArr) {
        if (!strArr[2].contains(SIP_VERSION_2)) {
            return false;
        }
        try {
            return Arrays.stream(SIP_REQUEST_METHODS).anyMatch(new Predicate() { // from class: com.android.internal.telephony.SipMessageParsingUtils$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return strArr[0].contains((String) obj);
                }
            }) & (Uri.parse(strArr[1]).getScheme() != null);
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    private static boolean verifySipResponse(String[] strArr) throws NumberFormatException {
        int i;
        if (!strArr[0].contains(SIP_VERSION_2)) {
            return false;
        }
        try {
            i = Integer.parseInt(strArr[1]);
        } catch (NumberFormatException unused) {
        }
        return i >= 100 && i < 700;
    }

    public static List<Pair<String, String>> parseHeaders(String str, boolean z, String... strArr) {
        String strRemoveLeadingWhitespace = removeLeadingWhitespace(str);
        ArrayList arrayList = new ArrayList();
        String[] strArrSplit = strRemoveLeadingWhitespace.split("\\r?\\n");
        if (strArrSplit.length == 0) {
            return Collections.EMPTY_LIST;
        }
        StringBuilder sb = new StringBuilder();
        int length = strArrSplit.length;
        int i = 0;
        final String strTrim = null;
        while (true) {
            if (i < length) {
                String str2 = strArrSplit[i];
                if (str2.startsWith("\t") || str2.startsWith(" ")) {
                    sb.append(removeLeadingWhitespace(str2));
                } else {
                    if (strTrim != null) {
                        if (strArr == null || strArr.length == 0 || Arrays.stream(strArr).anyMatch(new Predicate() { // from class: com.android.internal.telephony.SipMessageParsingUtils$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return ((String) obj).equalsIgnoreCase(strTrim);
                            }
                        })) {
                            arrayList.add(new Pair(strTrim, sb.toString()));
                            if (z) {
                                break;
                            }
                        }
                        sb = new StringBuilder();
                        strTrim = null;
                    }
                    String[] strArrSplit2 = str2.split(":", 2);
                    if (strArrSplit2.length < 2) {
                        Log.w(TAG, "parseHeaders - received malformed line: " + str2);
                    } else {
                        strTrim = strArrSplit2[0].trim();
                        for (int i2 = 1; i2 < strArrSplit2.length; i2++) {
                            sb.append(removeLeadingWhitespace(strArrSplit2[i2]));
                        }
                    }
                }
                i++;
            } else if (strTrim != null && (strArr == null || strArr.length == 0 || Arrays.stream(strArr).anyMatch(new Predicate() { // from class: com.android.internal.telephony.SipMessageParsingUtils$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return ((String) obj).equalsIgnoreCase(strTrim);
                }
            }))) {
                arrayList.add(new Pair(strTrim, sb.toString()));
            }
        }
        return arrayList;
    }

    private static String removeLeadingWhitespace(String str) {
        return str.replaceFirst("^\\s*", "");
    }
}
