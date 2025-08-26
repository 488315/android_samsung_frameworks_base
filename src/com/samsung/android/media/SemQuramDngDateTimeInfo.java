package com.samsung.android.media;

import com.android.internal.content.NativeLibraryHelper;

/* loaded from: classes6.dex */
public class SemQuramDngDateTimeInfo {
    boolean mDateOnly;
    String mSubseconds;
    SemQuramDngDateTime mDateTime = new SemQuramDngDateTime();
    SemQuramDngTimeZone mTimeZone = new SemQuramDngTimeZone();

    public void setTimeInfo(String str) {
        String[] strArrSplit = str.split("T");
        this.mDateOnly = str.length() <= 10;
        if (strArrSplit.length >= 1) {
            String[] strArrSplit2 = strArrSplit[0].split(NativeLibraryHelper.CLEAR_ABI_OVERRIDE);
            if (strArrSplit2.length >= 1 && !strArrSplit2[0].equals("")) {
                this.mDateTime.mYear = Integer.valueOf(strArrSplit2[0]).intValue();
            } else {
                this.mDateTime.mYear = 0;
            }
            if (strArrSplit2.length >= 2 && !strArrSplit2[1].equals("")) {
                this.mDateTime.mMonth = Integer.valueOf(strArrSplit2[1]).intValue();
            } else {
                this.mDateTime.mMonth = 0;
            }
            if (strArrSplit2.length >= 3 && !strArrSplit2[2].equals("")) {
                this.mDateTime.mDay = Integer.valueOf(strArrSplit2[2]).intValue();
            } else {
                this.mDateTime.mDay = 0;
            }
        }
        if (strArrSplit.length < 2 || this.mDateOnly) {
            return;
        }
        String str2 = strArrSplit[1];
        if (str2.length() < 8) {
            return;
        }
        String strSubstring = str2.substring(0, 2);
        String strSubstring2 = str2.substring(3, 5);
        String strSubstring3 = str2.substring(6, 8);
        this.mDateTime.mHour = Integer.valueOf(strSubstring).intValue();
        this.mDateTime.mMinute = Integer.valueOf(strSubstring2).intValue();
        this.mDateTime.mSecond = Integer.valueOf(strSubstring3).intValue();
        if (str2.length() >= 9) {
            if (str2.charAt(8) == '.') {
                this.mSubseconds = str2.substring(9, 9);
                char cCharAt = str2.charAt(10);
                int iIntValue = (Integer.valueOf(str2.substring(11, 13)).intValue() * 60) + Integer.valueOf(str2.substring(14, 16)).intValue();
                if (cCharAt == '-') {
                    this.mTimeZone.mOffsetMinutes = -iIntValue;
                    return;
                } else {
                    this.mTimeZone.mOffsetMinutes = iIntValue;
                    return;
                }
            }
            char cCharAt2 = str2.charAt(8);
            int iIntValue2 = (Integer.valueOf(str2.substring(9, 11)).intValue() * 60) + Integer.valueOf(str2.substring(12, 14)).intValue();
            if (cCharAt2 == '-') {
                this.mTimeZone.mOffsetMinutes = -iIntValue2;
            } else {
                this.mTimeZone.mOffsetMinutes = iIntValue2;
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
