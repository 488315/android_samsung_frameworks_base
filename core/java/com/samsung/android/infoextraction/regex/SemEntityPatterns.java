package com.samsung.android.infoextraction.regex;

import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

/* loaded from: classes5.dex */
public class SemEntityPatterns {
    public static final String DEFAULT_DATE_STRING_TYPE1 = "(((19|20)(([02468][048])|([13579][26]))[\\-|\\/|\\.]0?2[\\-|\\/|\\.]29)|((((20[0-9][0-9])|(19[0-9][0-9]))[\\-|\\/|\\.])?(((0?[13578]|10|12)[\\-|\\/|\\.]31)|((0?[1,3-9]|1[0-2])[\\-|\\/|\\.](29|30))|((0?[1-9]|1[0-2])[\\-|\\/|\\.](1[0-9]|2[0-8]|0?[1-9])))[[:space:]]))";
    public static final String DEFAULT_DATE_STRING_TYPE2 = "(((Jan|January|Mar|March|May|Jul|July|Aug|August|Oct|October|Dec|December)(\\.[[:space:]]?|[[:space:]])((([1-2][0-9]|3[01])(th)?)|0?1(st)?|0?2(nd)?|0?3(rd)?|0?[4-9](th)?)((\\,[[:space:]]?|\\.[[:space:]]?|[[:space:]]?)((20[0-9][0-9])|(19[0-9][0-9]))?)?[[:space:]])|((Apr|April|Jun|June|Sep|September|Nov|November)(\\.[[:space:]]?|[[:space:]])((([1-2][0-9]|3[01])(th)?)|0?1(st)?|0?2(nd)?|0?3(rd)?|0?[4-9](th)?)((\\,[[:space:]]?|\\.[[:space:]]?|[[:space:]]?)((20[0-9][0-9])|(19[0-9][0-9]))?)?[[:space:]])|((Feb|February)(\\.[[:space:]]?|[[:space:]])((([1-2][0-9]|3[01])(th)?)|0?1(st)?|0?2(nd)?|0?3(rd)?|0?[4-9](th)?)((\\,[[:space:]]?|\\.[[:space:]]?|[[:space:]]?)((20[0-9][0-9])|(19[0-9][0-9]))?)?[[:space:]]))";
    public static final String DEFAULT_TIME_STRING = "(((0[1-9]|1[1-2])[[:space:]]?\\:[[:space:]]?[0-5][0-9][[:space:]]?(am|pm|AM|PM))|(([0-1][0-9]|2[0-3])[[:space:]]?\\:[[:space:]]?[0-5][0-9]))";
    private static final Pattern DOMAIN_NAME;
    private static final String GOOD_GTLD_CHAR = "a-zA-Z -⿿\u3040-䷿龦-\ud7ff豈-﷏ﷰ-\ufeff";
    private static final String GOOD_IRI_CHAR = "a-zA-Z0-9 -\ud7ff豈-﷏ﷰ-\uffef";
    private static final String GOOD_IRI_HOST_CHAR = "a-zA-Z0-9 -⿿\u3040-䷿龦-\ud7ff豈-﷏ﷰ-\ufeff";
    private static final String GTLD = "[a-zA-Z -⿿\u3040-䷿龦-\ud7ff豈-﷏ﷰ-\ufeff]{2,63}";
    private static final String HOST_NAME = "([a-zA-Z0-9 -⿿\u3040-䷿龦-\ud7ff豈-﷏ﷰ-\ufeff]([a-zA-Z0-9 -⿿\u3040-䷿龦-\ud7ff豈-﷏ﷰ-\ufeff\\-]{0,61}[a-zA-Z0-9 -⿿\u3040-䷿龦-\ud7ff豈-﷏ﷰ-\ufeff]){0,1}\\.)+[a-zA-Z -⿿\u3040-䷿龦-\ud7ff豈-﷏ﷰ-\ufeff]{2,63}";
    private static final Pattern IP_ADDRESS;
    private static final String IRI = "[a-zA-Z0-9 -⿿\u3040-䷿龦-\ud7ff豈-﷏ﷰ-\ufeff]([a-zA-Z0-9 -⿿\u3040-䷿龦-\ud7ff豈-﷏ﷰ-\ufeff\\-]{0,61}[a-zA-Z0-9 -⿿\u3040-䷿龦-\ud7ff豈-﷏ﷰ-\ufeff]){0,1}";
    public static final String PREFIX_FOR_DATE_MILLIS = "((Date|date|날짜)[[[:space:]]\\:\\;\\-]+)";
    public static final String PREFIX_FOR_TIME_MILLIS = "((Time|time|시간)[[[:space:]]\\:\\;\\-]+)";
    public static final String SPILT_PATTERN_DATE_TYPE1 = "[[[:space:]]\\-\\/\\.년월일]+";
    public static final String SPILT_PATTERN_DATE_TYPE2 = "[[[:space:]]\\,\\.]+";
    public static final Pattern URL;
    public static Map<String, Integer> globalDateMap;
    public static String COUNTRY_DATE_STRING = "";
    public static String COUNTRY_TIME_STRING = "";
    public static final Pattern PHONE_NUMBER = Pattern.compile("([[:space:]](\\+[0-9]+[\\- \\.\u00ad]*)?(\\([0-9]+\\)[\\- \\.\u00ad]*)?([0-9][0-9\\- \\.\u00ad][0-9\\- \\.\u00ad]+[0-9])[[:space:]])");
    public static final Pattern PHONE_NUMBER_WEAK = Pattern.compile("((\\+[0-9]+[[[:space:]]?\\-\\.\u00ad]*)?(\\([0-9]+\\)[[[:space:]]?\\-\\.\u00ad]*)?([0-9][0-9[[:space:]]?\\-\\.\u00ad][0-9[[:space:]]?\\-\\.\u00ad]+[0-9]))");
    public static final Pattern REFACTORING_PHONE_NUMBER = Pattern.compile("((\\+[0-9]+[[[:space:]]?\\-\\.\u00ad]*)?(\\([0-9]+\\)[[[:space:]]?\\-\\.\u00ad]*)?([0-9][0-9[[:space:]]?\\-\\.\u00ad][0-9[[:space:]]?\\-\\.\u00ad]+[0-9]))");
    public static final Pattern HYPHEN = Pattern.compile("(\u00ad)");
    public static final Pattern EMAIL_ADDRESS = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+");
    public static final Pattern EMAIL_ADDRESS_WEAK = Pattern.compile("([a-zA-Z0-9\\+\\.\\_\\%\\-\\+\u00ad]{1,256}[[:space:]]?\\@[[:space:]]?[a-zA-Z0-9][a-zA-Z0-9\\-\u00ad]{0,64}([[:space:]]?\\.[[:space:]]?[a-zA-Z0-9][a-zA-Z0-9\\-\u00ad]{0,25})+)");

    static {
        Pattern compile = Pattern.compile("((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[0-9]))");
        IP_ADDRESS = compile;
        Pattern compile2 = Pattern.compile("(([a-zA-Z0-9 -⿿\u3040-䷿龦-\ud7ff豈-﷏ﷰ-\ufeff]([a-zA-Z0-9 -⿿\u3040-䷿龦-\ud7ff豈-﷏ﷰ-\ufeff\\-]{0,61}[a-zA-Z0-9 -⿿\u3040-䷿龦-\ud7ff豈-﷏ﷰ-\ufeff]){0,1}\\.)+[a-zA-Z -⿿\u3040-䷿龦-\ud7ff豈-﷏ﷰ-\ufeff]{2,63}|" + compile + NavigationBarInflaterView.KEY_CODE_END);
        DOMAIN_NAME = compile2;
        URL = Pattern.compile("((?:(http|https|Http|Https|rtsp|Rtsp|ftp):\\/\\/(?:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,64}(?:\\:(?:[a-zA-Z0-9\\$\\-\\_\\.\\+\\!\\*\\'\\(\\)\\,\\;\\?\\&\\=]|(?:\\%[a-fA-F0-9]{2})){1,25})?\\@)?)?(?:" + compile2 + ")(?:\\:\\d{1,5})?)(\\/(?:(?:[a-zA-Z0-9 -\ud7ff豈-﷏ﷰ-\uffef\\;\\/\\?\\:\\@\\&\\=\\#\\~\\-\\.\\+\\!\\*\\'\\(\\)\\,\\_])|(?:\\%[a-fA-F0-9]{2}))*)?", 2);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        globalDateMap = linkedHashMap;
        linkedHashMap.put("Jan", 1);
        globalDateMap.put("January", 1);
        globalDateMap.put("Feb", 2);
        globalDateMap.put("February", 2);
        globalDateMap.put("Mar", 3);
        globalDateMap.put("March", 3);
        globalDateMap.put("Apr", 4);
        globalDateMap.put("April", 4);
        globalDateMap.put("May", 5);
        globalDateMap.put("Jun", 6);
        globalDateMap.put("June", 6);
        globalDateMap.put("Jul", 7);
        globalDateMap.put("July", 7);
        globalDateMap.put("Aug", 8);
        globalDateMap.put("August", 8);
        globalDateMap.put("Sep", 9);
        globalDateMap.put("September", 9);
        globalDateMap.put("Octo", 10);
        globalDateMap.put("October", 10);
        globalDateMap.put("Nov", 11);
        globalDateMap.put("November", 11);
        globalDateMap.put("Dec", 12);
        globalDateMap.put("December", 12);
    }

    public static String getCountryDateString(Context context) {
        return "";
    }

    public static String getCountryTimeString(Context context) {
        return "";
    }

    public static Pattern getSmartPatternsForDate(Context context) {
        COUNTRY_DATE_STRING = "";
        return Pattern.compile("(((19|20)(([02468][048])|([13579][26]))[\\-|\\/|\\.]0?2[\\-|\\/|\\.]29)|((((20[0-9][0-9])|(19[0-9][0-9]))[\\-|\\/|\\.])?(((0?[13578]|10|12)[\\-|\\/|\\.]31)|((0?[1,3-9]|1[0-2])[\\-|\\/|\\.](29|30))|((0?[1-9]|1[0-2])[\\-|\\/|\\.](1[0-9]|2[0-8]|0?[1-9])))[[:space:]]))|(((Jan|January|Mar|March|May|Jul|July|Aug|August|Oct|October|Dec|December)(\\.[[:space:]]?|[[:space:]])((([1-2][0-9]|3[01])(th)?)|0?1(st)?|0?2(nd)?|0?3(rd)?|0?[4-9](th)?)((\\,[[:space:]]?|\\.[[:space:]]?|[[:space:]]?)((20[0-9][0-9])|(19[0-9][0-9]))?)?[[:space:]])|((Apr|April|Jun|June|Sep|September|Nov|November)(\\.[[:space:]]?|[[:space:]])((([1-2][0-9]|3[01])(th)?)|0?1(st)?|0?2(nd)?|0?3(rd)?|0?[4-9](th)?)((\\,[[:space:]]?|\\.[[:space:]]?|[[:space:]]?)((20[0-9][0-9])|(19[0-9][0-9]))?)?[[:space:]])|((Feb|February)(\\.[[:space:]]?|[[:space:]])((([1-2][0-9]|3[01])(th)?)|0?1(st)?|0?2(nd)?|0?3(rd)?|0?[4-9](th)?)((\\,[[:space:]]?|\\.[[:space:]]?|[[:space:]]?)((20[0-9][0-9])|(19[0-9][0-9]))?)?[[:space:]]))" + COUNTRY_DATE_STRING);
    }

    public static Pattern getSmartPatternsForTime(Context context) {
        COUNTRY_TIME_STRING = "";
        return Pattern.compile("((((0[1-9]|1[1-2])[[:space:]]?\\:[[:space:]]?[0-5][0-9][[:space:]]?(am|pm|AM|PM))|(([0-1][0-9]|2[0-3])[[:space:]]?\\:[[:space:]]?[0-5][0-9]))" + COUNTRY_TIME_STRING + NavigationBarInflaterView.KEY_CODE_END);
    }

    private SemEntityPatterns() {
    }
}
