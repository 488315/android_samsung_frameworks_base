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
        String[] splitStartLineAndVerify = splitStartLineAndVerify(str);
        if (splitStartLineAndVerify == null) {
            return false;
        }
        return verifySipRequest(splitStartLineAndVerify);
    }

    public static boolean isSipResponse(String str) {
        String[] splitStartLineAndVerify = splitStartLineAndVerify(str);
        if (splitStartLineAndVerify == null) {
            return false;
        }
        return verifySipResponse(splitStartLineAndVerify);
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
        String[] split = str.split(";");
        if (split.length < 2) {
            return null;
        }
        for (String str3 : split) {
            String[] split2 = str3.split(PARAM_KEY_VALUE_SEPARATOR);
            if (split2.length >= 2) {
                if (split2.length > 2) {
                    Log.w(TAG, "getParameterValue: unexpected parameter" + Arrays.toString(split2));
                }
                split2[0] = split2[0].trim();
                split2[1] = split2[1].trim();
                if (str2.equalsIgnoreCase(split2[0])) {
                    return split2[1];
                }
            }
        }
        return null;
    }

    public static String getCallId(String str) {
        List<Pair<String, String>> parseHeaders = parseHeaders(str, true, CALL_ID_SIP_HEADER_KEY, CALL_ID_SIP_HEADER_KEY_COMPACT);
        if (parseHeaders.isEmpty()) {
            return null;
        }
        return parseHeaders.get(0).second;
    }

    public static String getFromTag(String str) {
        List<Pair<String, String>> parseHeaders = parseHeaders(str, true, FROM_HEADER_KEY, "f");
        if (parseHeaders.isEmpty()) {
            return null;
        }
        return getParameterValue(parseHeaders.get(0).second, "tag");
    }

    public static String getToTag(String str) {
        List<Pair<String, String>> parseHeaders = parseHeaders(str, true, TO_HEADER_KEY, "t");
        if (parseHeaders.isEmpty()) {
            return null;
        }
        return getParameterValue(parseHeaders.get(0).second, "tag");
    }

    public static String[] splitStartLineAndVerify(String str) {
        String[] split = str.split(" ", 3);
        if (isStartLineMalformed(split)) {
            return null;
        }
        return split;
    }

    public static Set<String> getAcceptContactFeatureTags(String str) {
        List<Pair<String, String>> parseHeaders = parseHeaders(str, false, ACCEPT_CONTACT_HEADER_KEY, "a");
        if (str.isEmpty()) {
            return Collections.EMPTY_SET;
        }
        ArraySet arraySet = new ArraySet();
        Iterator<Pair<String, String>> it = parseHeaders.iterator();
        while (it.hasNext()) {
            String[] split = it.next().second.split(";");
            if (split.length >= 2) {
                for (String str2 : (Set) Arrays.asList(split).subList(1, split.length).stream().map(new Function() { // from class: com.android.internal.telephony.SipMessageParsingUtils$$ExternalSyntheticLambda3
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return ((String) obj).trim();
                    }
                }).filter(new Predicate() { // from class: com.android.internal.telephony.SipMessageParsingUtils$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        boolean startsWith;
                        startsWith = ((String) obj).startsWith("+");
                        return startsWith;
                    }
                }).collect(Collectors.toSet())) {
                    String[] split2 = str2.split(PARAM_KEY_VALUE_SEPARATOR, 2);
                    if (split2.length < 2) {
                        arraySet.add(str2);
                    } else {
                        for (String str3 : splitParamValue(split2[1])) {
                            arraySet.add(split2[0] + PARAM_KEY_VALUE_SEPARATOR + str3);
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
        String[] split = str.substring(1, str.length() - 1).split(",");
        for (int i = 0; i < split.length; i++) {
            split[i] = "\"" + split[i] + "\"";
        }
        return split;
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
                    boolean contains;
                    contains = strArr[0].contains((String) obj);
                    return contains;
                }
            }) & (Uri.parse(strArr[1]).getScheme() != null);
        } catch (NumberFormatException unused) {
            return false;
        }
    }

    private static boolean verifySipResponse(String[] strArr) {
        int parseInt;
        if (!strArr[0].contains(SIP_VERSION_2)) {
            return false;
        }
        try {
            parseInt = Integer.parseInt(strArr[1]);
        } catch (NumberFormatException unused) {
        }
        return parseInt >= 100 && parseInt < 700;
    }

    public static List<Pair<String, String>> parseHeaders(String str, boolean z, String... strArr) {
        String removeLeadingWhitespace = removeLeadingWhitespace(str);
        ArrayList arrayList = new ArrayList();
        String[] split = removeLeadingWhitespace.split("\\r?\\n");
        if (split.length == 0) {
            return Collections.EMPTY_LIST;
        }
        StringBuilder sb = new StringBuilder();
        int length = split.length;
        int i = 0;
        final String str2 = null;
        while (true) {
            if (i < length) {
                String str3 = split[i];
                if (str3.startsWith("\t") || str3.startsWith(" ")) {
                    sb.append(removeLeadingWhitespace(str3));
                } else {
                    if (str2 != null) {
                        if (strArr == null || strArr.length == 0 || Arrays.stream(strArr).anyMatch(new Predicate() { // from class: com.android.internal.telephony.SipMessageParsingUtils$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                boolean equalsIgnoreCase;
                                equalsIgnoreCase = ((String) obj).equalsIgnoreCase(str2);
                                return equalsIgnoreCase;
                            }
                        })) {
                            arrayList.add(new Pair(str2, sb.toString()));
                            if (z) {
                                break;
                            }
                        }
                        sb = new StringBuilder();
                        str2 = null;
                    }
                    String[] split2 = str3.split(":", 2);
                    if (split2.length < 2) {
                        Log.w(TAG, "parseHeaders - received malformed line: " + str3);
                    } else {
                        str2 = split2[0].trim();
                        for (int i2 = 1; i2 < split2.length; i2++) {
                            sb.append(removeLeadingWhitespace(split2[i2]));
                        }
                    }
                }
                i++;
            } else if (str2 != null && (strArr == null || strArr.length == 0 || Arrays.stream(strArr).anyMatch(new Predicate() { // from class: com.android.internal.telephony.SipMessageParsingUtils$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean equalsIgnoreCase;
                    equalsIgnoreCase = ((String) obj).equalsIgnoreCase(str2);
                    return equalsIgnoreCase;
                }
            }))) {
                arrayList.add(new Pair(str2, sb.toString()));
            }
        }
        return arrayList;
    }

    private static String removeLeadingWhitespace(String str) {
        return str.replaceFirst("^\\s*", "");
    }
}
