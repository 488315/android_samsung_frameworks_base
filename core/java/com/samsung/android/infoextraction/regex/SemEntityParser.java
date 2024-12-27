package com.samsung.android.infoextraction.regex;

import android.app.blob.XmlTags;
import android.content.Context;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.provider.Telephony;
import android.text.format.Time;
import android.util.Log;

import com.android.internal.content.NativeLibraryHelper;

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

    @Deprecated(forRemoval = true, since = "15.0")
    public static SemEntityInfo parse(Context context, String str, int level) {
        mContext = context;
        clear();
        mInfo = new SemEntityInfo();
        mLevel = level;
        mWorkStr = " " + str + " ";
        mWorkStrForMillis = " " + str + " ";
        today = Calendar.getInstance();
        yearOfToday = today.get(1);
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
        Pattern p1 =
                Pattern.compile(
                        "((((19|20)(([02468][048])|([13579][26]))[\\-|\\/|\\.]0?2[\\-|\\/|\\.]29)|((((20[0-9][0-9])|(19[0-9][0-9]))[\\-|\\/|\\.])?(((0?[13578]|10|12)[\\-|\\/|\\.]31)|((0?[1,3-9]|1[0-2])[\\-|\\/|\\.](29|30))|((0?[1-9]|1[0-2])[\\-|\\/|\\.](1[0-9]|2[0-8]|0?[1-9])))[[:space:]])))");
        Matcher m1 = p1.matcher(mWorkStrForMillis);
        mWorkStrForMillis = p1.matcher(mWorkStrForMillis).replaceAll(DELIMITER);
        while (m1.find()) {
            String matchString = removeUnnecessary(m1.group(0));
            mInfo.setInfo(convertDateToMillis(matchString, 1), 2);
            Log.d(TAG, "add date for millis(type1): " + matchString);
        }
        Pattern p2 =
                Pattern.compile(
                        "((((Jan|January|Mar|March|May|Jul|July|Aug|August|Oct|October|Dec|December)(\\.[[:space:]]?|[[:space:]])((([1-2][0-9]|3[01])(th)?)|0?1(st)?|0?2(nd)?|0?3(rd)?|0?[4-9](th)?)((\\,[[:space:]]?|\\.[[:space:]]?|[[:space:]]?)((20[0-9][0-9])|(19[0-9][0-9]))?)?[[:space:]])|((Apr|April|Jun|June|Sep|September|Nov|November)(\\.[[:space:]]?|[[:space:]])((([1-2][0-9]|3[01])(th)?)|0?1(st)?|0?2(nd)?|0?3(rd)?|0?[4-9](th)?)((\\,[[:space:]]?|\\.[[:space:]]?|[[:space:]]?)((20[0-9][0-9])|(19[0-9][0-9]))?)?[[:space:]])|((Feb|February)(\\.[[:space:]]?|[[:space:]])((([1-2][0-9]|3[01])(th)?)|0?1(st)?|0?2(nd)?|0?3(rd)?|0?[4-9](th)?)((\\,[[:space:]]?|\\.[[:space:]]?|[[:space:]]?)((20[0-9][0-9])|(19[0-9][0-9]))?)?[[:space:]])))");
        Matcher m2 = p2.matcher(mWorkStrForMillis);
        mWorkStrForMillis = p2.matcher(mWorkStrForMillis).replaceAll(DELIMITER);
        while (m2.find()) {
            String matchString2 = removeUnnecessary(m2.group(0));
            mInfo.setInfo(convertDateToMillis(matchString2, 2), 2);
            Log.d(TAG, "add date for millis(type2): " + matchString2);
        }
        String countryDateString = SemEntityPatterns.getCountryDateString(mContext);
        if (countryDateString.length() > 0 && countryDateString.charAt(0) == '|') {
            StringBuilder sb = new StringBuilder(countryDateString);
            sb.deleteCharAt(0);
            Pattern p3 =
                    Pattern.compile(
                            NavigationBarInflaterView.KEY_CODE_START
                                    + sb.toString()
                                    + NavigationBarInflaterView.KEY_CODE_END);
            Matcher m3 = p3.matcher(mWorkStrForMillis);
            mWorkStrForMillis = p3.matcher(mWorkStrForMillis).replaceAll(DELIMITER);
            while (m3.find()) {
                String matchString3 = removeUnnecessary(m3.group(0));
                mInfo.setInfo(convertDateToMillis(matchString3, 1), 2);
                Log.d(TAG, "add date for millis(type3, country): " + matchString3);
            }
        }
    }

    private static void parsingTimeMillisInfo() {
        Pattern p =
                Pattern.compile(
                        "(((((0[1-9]|1[1-2])[[:space:]]?\\:[[:space:]]?[0-5][0-9][[:space:]]?(am|pm|AM|PM))|(([0-1][0-9]|2[0-3])[[:space:]]?\\:[[:space:]]?[0-5][0-9]))"
                                + SemEntityPatterns.getCountryTimeString(mContext)
                                + "))");
        Matcher m = p.matcher(mWorkStrForMillis);
        mWorkStrForMillis = p.matcher(mWorkStrForMillis).replaceAll(DELIMITER);
        while (m.find()) {
            String matchString = removeUnnecessary(m.group(0));
            mInfo.setInfo(convertTimeToMillis(matchString), 4);
            Log.d(TAG, "add time for millis : " + matchString);
        }
    }

    private static void parsingDateInfo() {
        Pattern p1 = Pattern.compile(SemEntityPatterns.DEFAULT_DATE_STRING_TYPE1);
        Matcher m1 = p1.matcher(mWorkStr);
        mWorkStr = p1.matcher(mWorkStr).replaceAll(DELIMITER);
        while (m1.find()) {
            String matchString = removeUnnecessary(m1.group(0));
            mInfo.setInfo(matchString, 1);
            Log.d(TAG, "add date(pattern type1): " + matchString);
        }
        Pattern p2 = Pattern.compile(SemEntityPatterns.DEFAULT_DATE_STRING_TYPE2);
        Matcher m2 = p2.matcher(mWorkStr);
        mWorkStr = p2.matcher(mWorkStr).replaceAll(DELIMITER);
        while (m2.find()) {
            String matchString2 = removeUnnecessary(m2.group(0));
            mInfo.setInfo(matchString2, 1);
            Log.d(TAG, "add date(pattern type2): " + matchString2);
        }
        StringBuilder sb = new StringBuilder(SemEntityPatterns.getCountryDateString(mContext));
        if (sb.length() > 0 && sb.charAt(0) == '|') {
            sb.deleteCharAt(0);
            String countryDateString = sb.toString();
            Pattern p3 = Pattern.compile(countryDateString);
            Matcher m3 = p3.matcher(mWorkStr);
            mWorkStr = p3.matcher(mWorkStr).replaceAll(DELIMITER);
            while (m3.find()) {
                String matchString3 = removeUnnecessary(m3.group(0));
                mInfo.setInfo(matchString3, 1);
                Log.d(TAG, "add date(pattern type3, country): " + matchString3);
            }
        }
    }

    private static void parsingTimeInfo() {
        Pattern p =
                Pattern.compile(
                        SemEntityPatterns.DEFAULT_TIME_STRING
                                + SemEntityPatterns.getCountryTimeString(mContext));
        Matcher m = p.matcher(mWorkStr);
        mWorkStr = p.matcher(mWorkStr).replaceAll(DELIMITER);
        while (m.find()) {
            String matchString = removeUnnecessary(m.group(0));
            mInfo.setInfo(matchString, 3);
            Log.d(TAG, "add time : " + matchString);
        }
    }

    private static void parsingPhoneNumInfo() {
        Pattern p;
        String matchString;
        if (mLevel >= 1) {
            p = SemEntityPatterns.PHONE_NUMBER;
        } else {
            p = SemEntityPatterns.PHONE_NUMBER_WEAK;
        }
        Matcher m = p.matcher(mWorkStr);
        mWorkStr = p.matcher(mWorkStr).replaceAll(DELIMITER);
        Pattern hyphen = SemEntityPatterns.HYPHEN;
        while (m.find()) {
            if (mLevel >= 0) {
                matchString = removeUnnecessary(m.group(0), false);
            } else {
                matchString = removeUnnecessary(m.group(0));
            }
            String matchString2 =
                    hyphen.matcher(matchString).replaceAll(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            if (matchString2.length() >= 7) {
                mInfo.setInfo(matchString2, 5);
                Log.d(TAG, "add tel number : " + matchString2);
            }
        }
        refactoringPhoneNumber();
    }

    private static void parsingEmailInfo() {
        Pattern p;
        String matchString;
        if (mLevel >= 1) {
            p = SemEntityPatterns.EMAIL_ADDRESS;
        } else {
            p = SemEntityPatterns.EMAIL_ADDRESS_WEAK;
        }
        Matcher m = p.matcher(mWorkStr);
        if (mLevel >= 0) {
            mWorkStr = p.matcher(mWorkStr).replaceAll(DELIMITER);
        }
        Pattern hyphen = SemEntityPatterns.HYPHEN;
        while (m.find()) {
            if (mLevel >= 0) {
                matchString = removeUnnecessary(m.group(0), false);
            } else {
                matchString = removeUnnecessary(m.group(0));
            }
            String matchString2 =
                    hyphen.matcher(matchString).replaceAll(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            mInfo.setInfo(matchString2, 6);
            Log.d(TAG, "add email address : " + matchString2);
        }
    }

    private static void parsingURLInfo() {
        Pattern p = SemEntityPatterns.URL;
        Matcher m = p.matcher(mWorkStr);
        mWorkStr = p.matcher(mWorkStr).replaceAll(DELIMITER);
        while (m.find()) {
            String matchString = removeUnnecessary(m.group(0));
            mInfo.setInfo(matchString, 7);
            Log.d(TAG, "add URL : " + matchString);
        }
    }

    private static void arrangeRemainData() {
        Pattern p = Pattern.compile("(＃|[[:space:]])+");
        mWorkStr = p.matcher(mWorkStr).replaceAll(" ");
    }

    private static String removeUnnecessary(String str) {
        return removeUnnecessary(str, true);
    }

    private static String removeUnnecessary(String str, boolean onlyStartEndCheck) {
        StringBuilder builder = new StringBuilder(str);
        if (str.startsWith("\n") || str.startsWith(" ")) {
            builder.deleteCharAt(0);
        }
        if (str.endsWith("\n") || str.endsWith(" ")) {
            builder.deleteCharAt(builder.length() - 1);
        }
        String result = builder.toString();
        if (!onlyStartEndCheck) {
            Pattern p = Pattern.compile("[:space:]");
            return p.matcher(result).replaceAll("");
        }
        return result;
    }

    private static void refactoringPhoneNumber() {
        if (mInfo.getCount(5) == 1) {
            String str = mInfo.getInfoList(5).get(0);
            int spaceCount = 0;
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == ' ') {
                    spaceCount++;
                }
            }
            if (spaceCount > 0 && (str.length() / spaceCount) + 1 > 8) {
                Pattern p = SemEntityPatterns.REFACTORING_PHONE_NUMBER;
                Matcher m = p.matcher(str);
                mInfo.deleteInfo(0, 5);
                while (m.find()) {
                    mInfo.setInfo(m.group(0), 5);
                    Log.d(TAG, "add refactoring phone number : " + m.group(0));
                }
            }
        }
    }

    private static String convertDateToMillis(String dateStr, int patternType) {
        Time t = new Time(Time.TIMEZONE_UTC);
        try {
            if (patternType == 1) {
                String[] separated = dateStr.split(SemEntityPatterns.SPILT_PATTERN_DATE_TYPE1);
                if (separated.length == 3) {
                    t.year = Integer.parseInt(separated[0]);
                    t.month = Integer.parseInt(separated[1]) - 1;
                    t.monthDay = Integer.parseInt(separated[2]);
                } else if (separated.length == 2) {
                    t.year = yearOfToday;
                    t.month = Integer.parseInt(separated[0]) - 1;
                    t.monthDay = Integer.parseInt(separated[1]);
                } else {
                    Log.d(TAG, "fail convertDateToMillis() by invalid length. (type:1)");
                    return "";
                }
            } else if (patternType == 2) {
                String[] separated2 = dateStr.split(SemEntityPatterns.SPILT_PATTERN_DATE_TYPE2);
                if (separated2.length == 3) {
                    t.year = Integer.parseInt(separated2[2]);
                    t.month = SemEntityPatterns.globalDateMap.get(separated2[0]).intValue() - 1;
                    t.monthDay = Integer.parseInt(convertDayToInteger(separated2[1]));
                } else if (separated2.length == 2) {
                    t.year = yearOfToday;
                    t.month = SemEntityPatterns.globalDateMap.get(separated2[0]).intValue() - 1;
                    t.monthDay = Integer.parseInt(convertDayToInteger(separated2[1]));
                } else {
                    Log.d(TAG, "fail convertDateToMillis() by invalid length. (type:2)");
                    return "";
                }
            } else {
                Log.d(TAG, "fail convertDateToMillis() by invalid patternType : ");
                return "";
            }
            t.hour = 0;
            t.minute = 0;
            t.second = 0;
            Log.d(TAG, "convertDateToMillis() completed successfully");
            Log.d(
                    TAG,
                    "year:"
                            + t.year
                            + ", month:"
                            + t.month
                            + ", day:"
                            + t.monthDay
                            + ", hour:"
                            + t.hour
                            + ", minute:"
                            + t.minute
                            + ", second:"
                            + t.second);
            long result = t.toMillis(true);
            return Long.toString(result);
        } catch (Exception e) {
            Log.d(TAG, "fail convertDateToMillis() by exception : " + e.getMessage());
            return "";
        }
    }

    private static String convertDayToInteger(String dayStr) {
        if (dayStr.length() >= 3) {
            StringBuilder builder = new StringBuilder(dayStr);
            if (dayStr.endsWith(Telephony.BaseMmsColumns.STATUS)
                    || dayStr.endsWith("nd")
                    || dayStr.endsWith("rd")
                    || dayStr.endsWith("th")) {
                builder.deleteCharAt(builder.length() - 1);
                builder.deleteCharAt(builder.length() - 1);
            }
            return builder.toString();
        }
        return dayStr;
    }

    private static String convertTimeToMillis(String timeStr) {
        Time t = new Time(Time.TIMEZONE_UTC);
        try {
            Pattern prefixPattern = Pattern.compile(SemEntityPatterns.PREFIX_FOR_TIME_MILLIS);
            prefixPattern.matcher(timeStr);
            String timeStr2 = prefixPattern.matcher(timeStr).replaceAll("");
            String[] separated = new String[2];
            Pattern p = Pattern.compile("[0-9]+");
            Matcher m = p.matcher(timeStr2);
            int i = 0;
            while (m.find()) {
                separated[i] = m.group(0);
                i++;
            }
            t.year = yearOfToday;
            t.month = monthOfToday;
            t.monthDay = dayOfToday;
            t.hour = Integer.parseInt(separated[0]);
            if (!timeStr2.contains("pm") && !timeStr2.contains("PM") && !timeStr2.contains("오후")) {
                if (!timeStr2.contains(XmlTags.TAG_ACCESS_MODE)
                        && !timeStr2.contains("AM")
                        && !timeStr2.contains("오전")) {
                    t.hour = Integer.parseInt(separated[0]);
                    t.minute = Integer.parseInt(separated[1]);
                    t.second = 0;
                    Log.d(TAG, "convertTimeToMillis() completed successfully");
                    Log.d(
                            TAG,
                            "year:"
                                    + t.year
                                    + ", month:"
                                    + t.month
                                    + ", day:"
                                    + t.monthDay
                                    + ", hour:"
                                    + t.hour
                                    + ", minute:"
                                    + t.minute
                                    + ", second:"
                                    + t.second);
                    long result = t.toMillis(true);
                    return Long.toString(result);
                }
                if (t.hour == 12) {
                    t.hour = 0;
                }
                t.minute = Integer.parseInt(separated[1]);
                t.second = 0;
                Log.d(TAG, "convertTimeToMillis() completed successfully");
                Log.d(
                        TAG,
                        "year:"
                                + t.year
                                + ", month:"
                                + t.month
                                + ", day:"
                                + t.monthDay
                                + ", hour:"
                                + t.hour
                                + ", minute:"
                                + t.minute
                                + ", second:"
                                + t.second);
                long result2 = t.toMillis(true);
                return Long.toString(result2);
            }
            if (t.hour != 12) {
                t.hour += 12;
            }
            t.minute = Integer.parseInt(separated[1]);
            t.second = 0;
            Log.d(TAG, "convertTimeToMillis() completed successfully");
            Log.d(
                    TAG,
                    "year:"
                            + t.year
                            + ", month:"
                            + t.month
                            + ", day:"
                            + t.monthDay
                            + ", hour:"
                            + t.hour
                            + ", minute:"
                            + t.minute
                            + ", second:"
                            + t.second);
            long result22 = t.toMillis(true);
            return Long.toString(result22);
        } catch (Exception e) {
            Log.d(TAG, "fail convertTimeToMillis() by exception : " + e.getMessage());
            return "";
        }
    }

    private static void clear() {
        if (mInfo != null) {
            mInfo.clear();
            mInfo = null;
        }
    }

    private SemEntityParser() {}
}
