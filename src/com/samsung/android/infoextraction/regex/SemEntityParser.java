package com.samsung.android.infoextraction.regex;

import android.app.blob.XmlTags;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.provider.Telephony;
import android.text.format.Time;
import android.util.Log;
import com.android.internal.content.NativeLibraryHelper;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes6.dex */
public class SemEntityParser {
    private static final boolean DEBUG = true;
    private static final String DELIMITER = "＃";

    @Deprecated(forRemoval = true, since = "15.0")
    public static final int PARSE_LEVEL_NORMAL = 1;

    @Deprecated(forRemoval = true, since = "15.0")
    public static final int PARSE_LEVEL_WEAK = 0;
    private static final String TAG = "SemEntityParser";
    private static int dayOfToday;
    private static Context mContext;
    private static SemEntityInfo mInfo;
    private static int mLevel;
    private static String mWorkStr;
    private static String mWorkStrForMillis;
    private static int monthOfToday;
    private static Calendar today;
    private static int yearOfToday;

    public static SemEntityInfo parse(Context context, String str, int i) {
        mContext = context;
        clear();
        mInfo = new SemEntityInfo();
        mLevel = i;
        mWorkStr = " " + str + " ";
        mWorkStrForMillis = " " + str + " ";
        Calendar calendar = Calendar.getInstance();
        today = calendar;
        yearOfToday = calendar.get(1);
        monthOfToday = today.get(2);
        dayOfToday = today.get(5);
        parsingEmailInfo();
        parsingDateInfo();
        parsingTimeInfo();
        parsingPhoneNumInfo();
        parsingURLInfo();
        parsingDateMillisInfo();
        parsingTimeMillisInfo();
        arrangeRemainData();
        return mInfo;
    }

    private static void parsingDateMillisInfo() {
        Pattern compile = Pattern.compile("((((19|20)(([02468][048])|([13579][26]))[\\-|\\/|\\.]0?2[\\-|\\/|\\.]29)|((((20[0-9][0-9])|(19[0-9][0-9]))[\\-|\\/|\\.])?(((0?[13578]|10|12)[\\-|\\/|\\.]31)|((0?[1,3-9]|1[0-2])[\\-|\\/|\\.](29|30))|((0?[1-9]|1[0-2])[\\-|\\/|\\.](1[0-9]|2[0-8]|0?[1-9])))[[:space:]])))");
        Matcher matcher = compile.matcher(mWorkStrForMillis);
        mWorkStrForMillis = compile.matcher(mWorkStrForMillis).replaceAll(DELIMITER);
        while (matcher.find()) {
            String removeUnnecessary = removeUnnecessary(matcher.group(0));
            mInfo.setInfo(convertDateToMillis(removeUnnecessary, 1), 2);
            Log.d(TAG, "add date for millis(type1): " + removeUnnecessary);
        }
        Pattern compile2 = Pattern.compile("((((Jan|January|Mar|March|May|Jul|July|Aug|August|Oct|October|Dec|December)(\\.[[:space:]]?|[[:space:]])((([1-2][0-9]|3[01])(th)?)|0?1(st)?|0?2(nd)?|0?3(rd)?|0?[4-9](th)?)((\\,[[:space:]]?|\\.[[:space:]]?|[[:space:]]?)((20[0-9][0-9])|(19[0-9][0-9]))?)?[[:space:]])|((Apr|April|Jun|June|Sep|September|Nov|November)(\\.[[:space:]]?|[[:space:]])((([1-2][0-9]|3[01])(th)?)|0?1(st)?|0?2(nd)?|0?3(rd)?|0?[4-9](th)?)((\\,[[:space:]]?|\\.[[:space:]]?|[[:space:]]?)((20[0-9][0-9])|(19[0-9][0-9]))?)?[[:space:]])|((Feb|February)(\\.[[:space:]]?|[[:space:]])((([1-2][0-9]|3[01])(th)?)|0?1(st)?|0?2(nd)?|0?3(rd)?|0?[4-9](th)?)((\\,[[:space:]]?|\\.[[:space:]]?|[[:space:]]?)((20[0-9][0-9])|(19[0-9][0-9]))?)?[[:space:]])))");
        Matcher matcher2 = compile2.matcher(mWorkStrForMillis);
        mWorkStrForMillis = compile2.matcher(mWorkStrForMillis).replaceAll(DELIMITER);
        while (matcher2.find()) {
            String removeUnnecessary2 = removeUnnecessary(matcher2.group(0));
            mInfo.setInfo(convertDateToMillis(removeUnnecessary2, 2), 2);
            Log.d(TAG, "add date for millis(type2): " + removeUnnecessary2);
        }
        String countryDateString = SemEntityPatterns.getCountryDateString(mContext);
        if (countryDateString.length() <= 0 || countryDateString.charAt(0) != '|') {
            return;
        }
        StringBuilder sb = new StringBuilder(countryDateString);
        sb.deleteCharAt(0);
        Pattern compile3 = Pattern.compile(NavigationBarInflaterView.KEY_CODE_START + sb.toString() + NavigationBarInflaterView.KEY_CODE_END);
        Matcher matcher3 = compile3.matcher(mWorkStrForMillis);
        mWorkStrForMillis = compile3.matcher(mWorkStrForMillis).replaceAll(DELIMITER);
        while (matcher3.find()) {
            String removeUnnecessary3 = removeUnnecessary(matcher3.group(0));
            mInfo.setInfo(convertDateToMillis(removeUnnecessary3, 1), 2);
            Log.d(TAG, "add date for millis(type3, country): " + removeUnnecessary3);
        }
    }

    private static void parsingTimeMillisInfo() {
        Pattern compile = Pattern.compile("(((((0[1-9]|1[1-2])[[:space:]]?\\:[[:space:]]?[0-5][0-9][[:space:]]?(am|pm|AM|PM))|(([0-1][0-9]|2[0-3])[[:space:]]?\\:[[:space:]]?[0-5][0-9]))" + SemEntityPatterns.getCountryTimeString(mContext) + "))");
        Matcher matcher = compile.matcher(mWorkStrForMillis);
        mWorkStrForMillis = compile.matcher(mWorkStrForMillis).replaceAll(DELIMITER);
        while (matcher.find()) {
            String removeUnnecessary = removeUnnecessary(matcher.group(0));
            mInfo.setInfo(convertTimeToMillis(removeUnnecessary), 4);
            Log.d(TAG, "add time for millis : " + removeUnnecessary);
        }
    }

    private static void parsingDateInfo() {
        Pattern compile = Pattern.compile(SemEntityPatterns.DEFAULT_DATE_STRING_TYPE1);
        Matcher matcher = compile.matcher(mWorkStr);
        mWorkStr = compile.matcher(mWorkStr).replaceAll(DELIMITER);
        while (matcher.find()) {
            String removeUnnecessary = removeUnnecessary(matcher.group(0));
            mInfo.setInfo(removeUnnecessary, 1);
            Log.d(TAG, "add date(pattern type1): " + removeUnnecessary);
        }
        Pattern compile2 = Pattern.compile(SemEntityPatterns.DEFAULT_DATE_STRING_TYPE2);
        Matcher matcher2 = compile2.matcher(mWorkStr);
        mWorkStr = compile2.matcher(mWorkStr).replaceAll(DELIMITER);
        while (matcher2.find()) {
            String removeUnnecessary2 = removeUnnecessary(matcher2.group(0));
            mInfo.setInfo(removeUnnecessary2, 1);
            Log.d(TAG, "add date(pattern type2): " + removeUnnecessary2);
        }
        StringBuilder sb = new StringBuilder(SemEntityPatterns.getCountryDateString(mContext));
        if (sb.length() <= 0 || sb.charAt(0) != '|') {
            return;
        }
        sb.deleteCharAt(0);
        Pattern compile3 = Pattern.compile(sb.toString());
        Matcher matcher3 = compile3.matcher(mWorkStr);
        mWorkStr = compile3.matcher(mWorkStr).replaceAll(DELIMITER);
        while (matcher3.find()) {
            String removeUnnecessary3 = removeUnnecessary(matcher3.group(0));
            mInfo.setInfo(removeUnnecessary3, 1);
            Log.d(TAG, "add date(pattern type3, country): " + removeUnnecessary3);
        }
    }

    private static void parsingTimeInfo() {
        Pattern compile = Pattern.compile(SemEntityPatterns.DEFAULT_TIME_STRING + SemEntityPatterns.getCountryTimeString(mContext));
        Matcher matcher = compile.matcher(mWorkStr);
        mWorkStr = compile.matcher(mWorkStr).replaceAll(DELIMITER);
        while (matcher.find()) {
            String removeUnnecessary = removeUnnecessary(matcher.group(0));
            mInfo.setInfo(removeUnnecessary, 3);
            Log.d(TAG, "add time : " + removeUnnecessary);
        }
    }

    private static void parsingPhoneNumInfo() {
        Pattern pattern;
        String removeUnnecessary;
        if (mLevel >= 1) {
            pattern = SemEntityPatterns.PHONE_NUMBER;
        } else {
            pattern = SemEntityPatterns.PHONE_NUMBER_WEAK;
        }
        Matcher matcher = pattern.matcher(mWorkStr);
        mWorkStr = pattern.matcher(mWorkStr).replaceAll(DELIMITER);
        Pattern pattern2 = SemEntityPatterns.HYPHEN;
        while (matcher.find()) {
            if (mLevel >= 0) {
                removeUnnecessary = removeUnnecessary(matcher.group(0), false);
            } else {
                removeUnnecessary = removeUnnecessary(matcher.group(0));
            }
            String replaceAll = pattern2.matcher(removeUnnecessary).replaceAll(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            if (replaceAll.length() >= 7) {
                mInfo.setInfo(replaceAll, 5);
                Log.d(TAG, "add tel number : " + replaceAll);
            }
        }
        refactoringPhoneNumber();
    }

    private static void parsingEmailInfo() {
        Pattern pattern;
        String removeUnnecessary;
        if (mLevel >= 1) {
            pattern = SemEntityPatterns.EMAIL_ADDRESS;
        } else {
            pattern = SemEntityPatterns.EMAIL_ADDRESS_WEAK;
        }
        Matcher matcher = pattern.matcher(mWorkStr);
        if (mLevel >= 0) {
            mWorkStr = pattern.matcher(mWorkStr).replaceAll(DELIMITER);
        }
        Pattern pattern2 = SemEntityPatterns.HYPHEN;
        while (matcher.find()) {
            if (mLevel >= 0) {
                removeUnnecessary = removeUnnecessary(matcher.group(0), false);
            } else {
                removeUnnecessary = removeUnnecessary(matcher.group(0));
            }
            String replaceAll = pattern2.matcher(removeUnnecessary).replaceAll(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            mInfo.setInfo(replaceAll, 6);
            Log.d(TAG, "add email address : " + replaceAll);
        }
    }

    private static void parsingURLInfo() {
        Pattern pattern = SemEntityPatterns.URL;
        Matcher matcher = pattern.matcher(mWorkStr);
        mWorkStr = pattern.matcher(mWorkStr).replaceAll(DELIMITER);
        while (matcher.find()) {
            String removeUnnecessary = removeUnnecessary(matcher.group(0));
            mInfo.setInfo(removeUnnecessary, 7);
            Log.d(TAG, "add URL : " + removeUnnecessary);
        }
    }

    private static void arrangeRemainData() {
        mWorkStr = Pattern.compile("(＃|[[:space:]])+").matcher(mWorkStr).replaceAll(" ");
    }

    private static String removeUnnecessary(String str) {
        return removeUnnecessary(str, true);
    }

    private static String removeUnnecessary(String str, boolean z) {
        StringBuilder sb = new StringBuilder(str);
        if (str.startsWith(ShaderAssembler.NEWLINE) || str.startsWith(" ")) {
            sb.deleteCharAt(0);
        }
        if (str.endsWith(ShaderAssembler.NEWLINE) || str.endsWith(" ")) {
            sb.deleteCharAt(sb.length() - 1);
        }
        String sb2 = sb.toString();
        return !z ? Pattern.compile("[:space:]").matcher(sb2).replaceAll("") : sb2;
    }

    private static void refactoringPhoneNumber() {
        if (mInfo.getCount(5) == 1) {
            String str = mInfo.getInfoList(5).get(0);
            int i = 0;
            for (int i2 = 0; i2 < str.length(); i2++) {
                if (str.charAt(i2) == ' ') {
                    i++;
                }
            }
            if (i <= 0 || (str.length() / i) + 1 <= 8) {
                return;
            }
            Matcher matcher = SemEntityPatterns.REFACTORING_PHONE_NUMBER.matcher(str);
            mInfo.deleteInfo(0, 5);
            while (matcher.find()) {
                mInfo.setInfo(matcher.group(0), 5);
                Log.d(TAG, "add refactoring phone number : " + matcher.group(0));
            }
        }
    }

    private static String convertDateToMillis(String str, int i) {
        Time time = new Time(Time.TIMEZONE_UTC);
        try {
            if (i == 1) {
                String[] split = str.split(SemEntityPatterns.SPILT_PATTERN_DATE_TYPE1);
                if (split.length == 3) {
                    time.year = Integer.parseInt(split[0]);
                    time.month = Integer.parseInt(split[1]) - 1;
                    time.monthDay = Integer.parseInt(split[2]);
                } else if (split.length == 2) {
                    time.year = yearOfToday;
                    time.month = Integer.parseInt(split[0]) - 1;
                    time.monthDay = Integer.parseInt(split[1]);
                } else {
                    Log.d(TAG, "fail convertDateToMillis() by invalid length. (type:1)");
                    return "";
                }
            } else if (i == 2) {
                String[] split2 = str.split(SemEntityPatterns.SPILT_PATTERN_DATE_TYPE2);
                if (split2.length == 3) {
                    time.year = Integer.parseInt(split2[2]);
                    time.month = SemEntityPatterns.globalDateMap.get(split2[0]).intValue() - 1;
                    time.monthDay = Integer.parseInt(convertDayToInteger(split2[1]));
                } else if (split2.length == 2) {
                    time.year = yearOfToday;
                    time.month = SemEntityPatterns.globalDateMap.get(split2[0]).intValue() - 1;
                    time.monthDay = Integer.parseInt(convertDayToInteger(split2[1]));
                } else {
                    Log.d(TAG, "fail convertDateToMillis() by invalid length. (type:2)");
                    return "";
                }
            } else {
                Log.d(TAG, "fail convertDateToMillis() by invalid patternType : ");
                return "";
            }
            time.hour = 0;
            time.minute = 0;
            time.second = 0;
            Log.d(TAG, "convertDateToMillis() completed successfully");
            Log.d(TAG, "year:" + time.year + ", month:" + time.month + ", day:" + time.monthDay + ", hour:" + time.hour + ", minute:" + time.minute + ", second:" + time.second);
            return Long.toString(time.toMillis(true));
        } catch (Exception e) {
            Log.d(TAG, "fail convertDateToMillis() by exception : " + e.getMessage());
            return "";
        }
    }

    private static String convertDayToInteger(String str) {
        if (str.length() < 3) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        if (str.endsWith(Telephony.BaseMmsColumns.STATUS) || str.endsWith("nd") || str.endsWith("rd") || str.endsWith("th")) {
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    private static String convertTimeToMillis(String str) {
        Time time = new Time(Time.TIMEZONE_UTC);
        try {
            Pattern compile = Pattern.compile(SemEntityPatterns.PREFIX_FOR_TIME_MILLIS);
            compile.matcher(str);
            String replaceAll = compile.matcher(str).replaceAll("");
            String[] strArr = new String[2];
            Matcher matcher = Pattern.compile("[0-9]+").matcher(replaceAll);
            int i = 0;
            while (matcher.find()) {
                strArr[i] = matcher.group(0);
                i++;
            }
            time.year = yearOfToday;
            time.month = monthOfToday;
            time.monthDay = dayOfToday;
            time.hour = Integer.parseInt(strArr[0]);
            if (!replaceAll.contains("pm") && !replaceAll.contains("PM") && !replaceAll.contains("오후")) {
                if (!replaceAll.contains(XmlTags.TAG_ACCESS_MODE) && !replaceAll.contains("AM") && !replaceAll.contains("오전")) {
                    time.hour = Integer.parseInt(strArr[0]);
                    time.minute = Integer.parseInt(strArr[1]);
                    time.second = 0;
                    Log.d(TAG, "convertTimeToMillis() completed successfully");
                    Log.d(TAG, "year:" + time.year + ", month:" + time.month + ", day:" + time.monthDay + ", hour:" + time.hour + ", minute:" + time.minute + ", second:" + time.second);
                    return Long.toString(time.toMillis(true));
                }
                if (time.hour == 12) {
                    time.hour = 0;
                }
                time.minute = Integer.parseInt(strArr[1]);
                time.second = 0;
                Log.d(TAG, "convertTimeToMillis() completed successfully");
                Log.d(TAG, "year:" + time.year + ", month:" + time.month + ", day:" + time.monthDay + ", hour:" + time.hour + ", minute:" + time.minute + ", second:" + time.second);
                return Long.toString(time.toMillis(true));
            }
            if (time.hour != 12) {
                time.hour += 12;
            }
            time.minute = Integer.parseInt(strArr[1]);
            time.second = 0;
            Log.d(TAG, "convertTimeToMillis() completed successfully");
            Log.d(TAG, "year:" + time.year + ", month:" + time.month + ", day:" + time.monthDay + ", hour:" + time.hour + ", minute:" + time.minute + ", second:" + time.second);
            return Long.toString(time.toMillis(true));
        } catch (Exception e) {
            Log.d(TAG, "fail convertTimeToMillis() by exception : " + e.getMessage());
            return "";
        }
    }

    private static void clear() {
        SemEntityInfo semEntityInfo = mInfo;
        if (semEntityInfo != null) {
            semEntityInfo.clear();
            mInfo = null;
        }
    }

    private SemEntityParser() {
    }
}
