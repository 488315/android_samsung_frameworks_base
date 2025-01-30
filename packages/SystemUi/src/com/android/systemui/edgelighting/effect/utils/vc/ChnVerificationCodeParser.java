package com.android.systemui.edgelighting.effect.utils.vc;

import android.content.Context;
import android.text.TextUtils;
import android.util.Slog;
import com.android.systemui.R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ChnVerificationCodeParser extends VerificationCodeParserBase {
    public final Context mContext;
    public int mCodeBehindStartIndex = -1;
    public int mCodeBehindEndIndex = -1;
    public int mCodeFrontStartIndex = -1;
    public int mCodeFrontEndIndex = -1;

    public ChnVerificationCodeParser(Context context) {
        this.mContext = context;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x010b A[Catch: IndexOutOfBoundsException -> 0x016d, TryCatch #2 {IndexOutOfBoundsException -> 0x016d, blocks: (B:21:0x00fa, B:23:0x010b, B:25:0x0112), top: B:20:0x00fa }] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0151  */
    @Override // com.android.systemui.edgelighting.effect.utils.vc.VerificationCodeParser
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String getVerificationCode(String str) {
        int i;
        int i2;
        String group;
        int i3;
        int indexOf;
        String str2;
        if (TextUtils.isEmpty(str)) {
            Slog.d("ORC/ChnVerificationCodeParser", "isVerificationCode() is false");
            return null;
        }
        Context context = this.mContext;
        String[] stringArray = context.getResources().getStringArray(R.array.verification_code_strong_chn);
        String[] stringArray2 = context.getResources().getStringArray(R.array.verification_code_spec_chn);
        int i4 = -1;
        this.mCodeFrontStartIndex = -1;
        this.mCodeFrontEndIndex = -1;
        this.mCodeBehindStartIndex = -1;
        this.mCodeBehindEndIndex = -1;
        int length = stringArray.length;
        String str3 = null;
        int i5 = -1;
        int i6 = 0;
        while (true) {
            int i7 = 4;
            if (i6 >= length) {
                break;
            }
            String str4 = stringArray[i6];
            String str5 = str;
            int i8 = i5;
            int indexOf2 = str.indexOf(str4);
            while (indexOf2 != i4 && str3 == null) {
                if (str5.length() <= str4.length()) {
                    i2 = length;
                    i = -1;
                    break;
                }
                String str6 = str5;
                String substring = str6.substring(indexOf2);
                try {
                    if (substring.length() < i7 || substring.charAt(3) != 65281) {
                        Matcher matcher = Pattern.compile("[0-9]{4,}").matcher(substring);
                        while (matcher.find()) {
                            i2 = length;
                            if (matcher.group().length() < 4 || matcher.group().length() > 7) {
                                length = i2;
                            } else {
                                this.mCodeBehindStartIndex = matcher.start() + indexOf2;
                                this.mCodeBehindEndIndex = matcher.end() + indexOf2;
                                Slog.d("ORC/ChnVerificationCodeParser", "mCodeBehindStartIndex = " + this.mCodeBehindStartIndex);
                                Slog.d("ORC/ChnVerificationCodeParser", "mCodeBehindEndIndex = " + this.mCodeBehindEndIndex);
                                if (substring.length() <= matcher.end() || !"-".equals(String.valueOf(substring.charAt(matcher.end())))) {
                                    group = matcher.group();
                                    String substring2 = str6.substring((str4.length() + indexOf2) - 1);
                                    if (TextUtils.isEmpty(group)) {
                                        i3 = indexOf2;
                                        i = -1;
                                        try {
                                            indexOf = substring2.indexOf(str4);
                                        } catch (IndexOutOfBoundsException e) {
                                            e = e;
                                            Slog.d("ORC/ChnVerificationCodeParser", e.getMessage(), e);
                                            str3 = group;
                                            i8 = i3;
                                            i6++;
                                            i4 = i;
                                            i5 = i8;
                                            length = i2;
                                        }
                                    } else {
                                        indexOf = substring2.indexOf(group);
                                        if (indexOf != -1) {
                                            String substring3 = substring2.substring(0, indexOf);
                                            int length2 = stringArray2.length;
                                            String str7 = group;
                                            int i9 = 0;
                                            while (i9 < length2) {
                                                int i10 = length2;
                                                try {
                                                    i3 = indexOf2;
                                                    i = -1;
                                                    if (substring3.indexOf(stringArray2[i9]) != -1) {
                                                        try {
                                                            substring2 = substring2.substring(indexOf);
                                                            indexOf = substring2.indexOf(str4);
                                                            str7 = null;
                                                        } catch (IndexOutOfBoundsException e2) {
                                                            e = e2;
                                                            group = null;
                                                            Slog.d("ORC/ChnVerificationCodeParser", e.getMessage(), e);
                                                            str3 = group;
                                                            i8 = i3;
                                                            i6++;
                                                            i4 = i;
                                                            i5 = i8;
                                                            length = i2;
                                                        }
                                                    }
                                                    i9++;
                                                    length2 = i10;
                                                    indexOf2 = i3;
                                                } catch (IndexOutOfBoundsException e3) {
                                                    e = e3;
                                                    i3 = indexOf2;
                                                    i = -1;
                                                    group = str7;
                                                }
                                            }
                                            i3 = indexOf2;
                                            i = -1;
                                            str2 = substring2;
                                            str3 = str7;
                                            str5 = str2;
                                            length = i2;
                                            i8 = i3;
                                            i7 = 4;
                                            int i11 = i;
                                            indexOf2 = indexOf;
                                            i4 = i11;
                                        } else {
                                            i3 = indexOf2;
                                            i = -1;
                                        }
                                    }
                                    String str8 = group;
                                    str2 = substring2;
                                    str3 = str8;
                                    str5 = str2;
                                    length = i2;
                                    i8 = i3;
                                    i7 = 4;
                                    int i112 = i;
                                    indexOf2 = indexOf;
                                    i4 = i112;
                                } else {
                                    Slog.d("ORC/ChnVerificationCodeParser", "getBehindCode return null code");
                                    group = null;
                                    String substring22 = str6.substring((str4.length() + indexOf2) - 1);
                                    if (TextUtils.isEmpty(group)) {
                                    }
                                    String str82 = group;
                                    str2 = substring22;
                                    str3 = str82;
                                    str5 = str2;
                                    length = i2;
                                    i8 = i3;
                                    i7 = 4;
                                    int i1122 = i;
                                    indexOf2 = indexOf;
                                    i4 = i1122;
                                }
                            }
                        }
                    }
                    String substring222 = str6.substring((str4.length() + indexOf2) - 1);
                    if (TextUtils.isEmpty(group)) {
                    }
                    String str822 = group;
                    str2 = substring222;
                    str3 = str822;
                    str5 = str2;
                    length = i2;
                    i8 = i3;
                    i7 = 4;
                    int i11222 = i;
                    indexOf2 = indexOf;
                    i4 = i11222;
                } catch (IndexOutOfBoundsException e4) {
                    e = e4;
                    i3 = indexOf2;
                    i = -1;
                }
                i2 = length;
                group = null;
            }
            i = i4;
            i2 = length;
            i6++;
            i4 = i;
            i5 = i8;
            length = i2;
        }
        String str9 = null;
        for (String str10 : stringArray) {
            int indexOf3 = str.indexOf(str10);
            int i12 = 4;
            if (indexOf3 > 4) {
                String substring4 = str.substring(0, indexOf3);
                Matcher matcher2 = Pattern.compile("[0-9]{4,}").matcher(substring4);
                String str11 = null;
                while (true) {
                    if (!matcher2.find()) {
                        str9 = str11;
                        break;
                    }
                    if (matcher2.group().length() >= i12 && matcher2.group().length() <= 7) {
                        this.mCodeFrontStartIndex = matcher2.start();
                        this.mCodeFrontEndIndex = matcher2.end();
                        Slog.d("ORC/ChnVerificationCodeParser", "mCodeFrontStartIndex = " + this.mCodeFrontStartIndex);
                        Slog.d("ORC/ChnVerificationCodeParser", "mCodeFrontEndIndex = " + this.mCodeFrontEndIndex);
                        if (substring4.length() > matcher2.end() && "-".equals(String.valueOf(substring4.charAt(matcher2.end())))) {
                            Slog.d("ORC/ChnVerificationCodeParser", "getFrontCode return null code");
                            str9 = null;
                            break;
                        }
                        str11 = matcher2.group();
                    }
                    i12 = 4;
                }
                i5 = indexOf3;
            }
        }
        boolean z = false;
        for (String str12 : stringArray2) {
            int indexOf4 = str.indexOf(str12);
            if (indexOf4 > i5 && this.mCodeBehindStartIndex > indexOf4) {
                z = true;
            }
        }
        if (str3 == null) {
            if (str9 == null) {
                Slog.d("ORC/ChnVerificationCodeParser", "isVerificationCode() is false");
                return null;
            }
            Slog.d("ORC/ChnVerificationCodeParser", "KEY_STRONGLY_STR FrontCode= " + str9);
            Slog.d("ORC/ChnVerificationCodeParser", "isVerificationCode() is true");
            return str9;
        }
        if ((4 == str3.length() && !z) || 6 == str3.length()) {
            Slog.d("ORC/ChnVerificationCodeParser", "KEY_STRONGLY_STR BehindCode= ".concat(str3));
            Slog.d("ORC/ChnVerificationCodeParser", "isVerificationCode() is true");
            return str3;
        }
        if (str9 == null || !(4 == str9.length() || 6 == str9.length())) {
            Slog.d("ORC/ChnVerificationCodeParser", "KEY_STRONGLY_STR BehindCode= ".concat(str3));
            Slog.d("ORC/ChnVerificationCodeParser", "isVerificationCode() is true");
            return str3;
        }
        Slog.d("ORC/ChnVerificationCodeParser", "KEY_STRONGLY_STR FrontCode= ".concat(str9));
        Slog.d("ORC/ChnVerificationCodeParser", "isVerificationCode() is true");
        return str9;
    }
}
