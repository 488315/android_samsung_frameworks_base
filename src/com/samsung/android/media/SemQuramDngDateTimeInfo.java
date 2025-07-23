package com.samsung.android.media;

import com.android.internal.content.NativeLibraryHelper;

/* loaded from: classes6.dex */
public class SemQuramDngDateTimeInfo {
    boolean mDateOnly;
    String mSubseconds;
    SemQuramDngDateTime mDateTime = new SemQuramDngDateTime();
    SemQuramDngTimeZone mTimeZone = new SemQuramDngTimeZone();

    public void setTimeInfo(String str) {
        String[] split = str.split("T");
        this.mDateOnly = str.length() <= 10;
        if (split.length >= 1) {
            String[] split2 = split[0].split(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            if (split2.length >= 1 && !split2[0].equals("")) {
                this.mDateTime.mYear = Integer.valueOf(split2[0]).intValue();
            } else {
                this.mDateTime.mYear = 0;
            }
            if (split2.length >= 2 && !split2[1].equals("")) {
                this.mDateTime.mMonth = Integer.valueOf(split2[1]).intValue();
            } else {
                this.mDateTime.mMonth = 0;
            }
            if (split2.length >= 3 && !split2[2].equals("")) {
                this.mDateTime.mDay = Integer.valueOf(split2[2]).intValue();
            } else {
                this.mDateTime.mDay = 0;
            }
        }
        if (split.length < 2 || this.mDateOnly) {
            return;
        }
        String str2 = split[1];
        if (str2.length() < 8) {
            return;
        }
        String substring = str2.substring(0, 2);
        String substring2 = str2.substring(3, 5);
        String substring3 = str2.substring(6, 8);
        this.mDateTime.mHour = Integer.valueOf(substring).intValue();
        this.mDateTime.mMinute = Integer.valueOf(substring2).intValue();
        this.mDateTime.mSecond = Integer.valueOf(substring3).intValue();
        if (str2.length() >= 9) {
            if (str2.charAt(8) == '.') {
                this.mSubseconds = str2.substring(9, 9);
                char charAt = str2.charAt(10);
                int intValue = (Integer.valueOf(str2.substring(11, 13)).intValue() * 60) + Integer.valueOf(str2.substring(14, 16)).intValue();
                if (charAt == '-') {
                    this.mTimeZone.mOffsetMinutes = -intValue;
                    return;
                } else {
                    this.mTimeZone.mOffsetMinutes = intValue;
                    return;
                }
            }
            char charAt2 = str2.charAt(8);
            int intValue2 = (Integer.valueOf(str2.substring(9, 11)).intValue() * 60) + Integer.valueOf(str2.substring(12, 14)).intValue();
            if (charAt2 == '-') {
                this.mTimeZone.mOffsetMinutes = -intValue2;
            } else {
                this.mTimeZone.mOffsetMinutes = intValue2;
            }
        }
    }

    public SemQuramDngDateTime getDateTime() {
        return this.mDateTime;
    }

    public boolean isDateOnly() {
        return this.mDateOnly;
    }

    public String gerSubseconds() {
        return this.mSubseconds;
    }

    public SemQuramDngTimeZone getTimeZone() {
        return this.mTimeZone;
    }
}
