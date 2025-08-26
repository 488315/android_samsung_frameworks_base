package com.android.systemui.edgelighting.effect.utils.vc;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Slog;
import com.android.systemui.R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes2.dex */
public class ChnVerificationCodeParser extends VerificationCodeParserBase {
    public final Context mContext;
    public int mCodeBehindStartIndex = -1;
    public int mCodeBehindEndIndex = -1;
    public int mCodeFrontStartIndex = -1;
    public int mCodeFrontEndIndex = -1;

    public ChnVerificationCodeParser(Context context) {
        this.mContext = context;
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0111 A[Catch: IndexOutOfBoundsException -> 0x0158, TRY_LEAVE, TryCatch #3 {IndexOutOfBoundsException -> 0x0158, blocks: (B:33:0x0100, B:35:0x0111), top: B:129:0x0100 }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x015c  */
    @Override // com.android.systemui.edgelighting.effect.utils.vc.VerificationCodeParser
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String getVerificationCode(String str) throws Resources.NotFoundException {
        String str2;
        int i;
        int i2;
        String strGroup;
        String str3;
        int iIndexOf;
        String str4 = null;
        if (TextUtils.isEmpty(str)) {
            Slog.d("ORC/ChnVerificationCodeParser", "isVerificationCode() is false");
            return null;
        }
        String[] stringArray = this.mContext.getResources().getStringArray(R.array.verification_code_strong_chn);
        String[] stringArray2 = this.mContext.getResources().getStringArray(R.array.verification_code_spec_chn);
        int i3 = -1;
        this.mCodeFrontStartIndex = -1;
        this.mCodeFrontEndIndex = -1;
        this.mCodeBehindStartIndex = -1;
        this.mCodeBehindEndIndex = -1;
        int length = stringArray.length;
        String str5 = null;
        int iIndexOf2 = -1;
        int i4 = 0;
        while (true) {
            str2 = str4;
            int i5 = 4;
            if (i4 >= length) {
                break;
            }
            String str6 = stringArray[i4];
            int i6 = iIndexOf2;
            iIndexOf2 = str.indexOf(str6);
            String str7 = str;
            while (iIndexOf2 != i3 && str5 == null) {
                if (str7.length() <= str6.length()) {
                    i2 = length;
                    i = -1;
                    break;
                }
                String str8 = str7;
                String strSubstring = str8.substring(iIndexOf2);
                if (strSubstring.length() < i5 || strSubstring.charAt(3) != 65281) {
                    Matcher matcher = Pattern.compile("[0-9]{4,}").matcher(strSubstring);
                    while (true) {
                        if (!matcher.find()) {
                            break;
                        }
                        Matcher matcher2 = matcher;
                        if (matcher.group().length() < 4 || matcher2.group().length() > 7) {
                            matcher = matcher2;
                        } else {
                            this.mCodeBehindStartIndex = matcher2.start() + iIndexOf2;
                            this.mCodeBehindEndIndex = matcher2.end() + iIndexOf2;
                            Slog.d("ORC/ChnVerificationCodeParser", "mCodeBehindStartIndex = " + this.mCodeBehindStartIndex);
                            Slog.d("ORC/ChnVerificationCodeParser", "mCodeBehindEndIndex = " + this.mCodeBehindEndIndex);
                            if (strSubstring.length() <= matcher2.end() || !"-".equals(String.valueOf(strSubstring.charAt(matcher2.end())))) {
                                strGroup = matcher2.group();
                            } else {
                                Slog.d("ORC/ChnVerificationCodeParser", "getBehindCode return null code");
                            }
                        }
                    }
                    try {
                        String strSubstring2 = str8.substring((str6.length() + iIndexOf2) - 1);
                        if (TextUtils.isEmpty(strGroup)) {
                            iIndexOf = strSubstring2.indexOf(strGroup);
                            if (iIndexOf != -1) {
                                str3 = strGroup;
                                try {
                                    String strSubstring3 = strSubstring2.substring(0, iIndexOf);
                                    int length2 = stringArray2.length;
                                    i2 = length;
                                    int i7 = 0;
                                    while (i7 < length2) {
                                        int i8 = i7;
                                        try {
                                            int iIndexOf3 = strSubstring3.indexOf(stringArray2[i8]);
                                            String str9 = strSubstring3;
                                            i = -1;
                                            if (iIndexOf3 != -1) {
                                                try {
                                                    strSubstring2 = strSubstring2.substring(iIndexOf);
                                                    iIndexOf = strSubstring2.indexOf(str6);
                                                    str3 = str2;
                                                } catch (IndexOutOfBoundsException e) {
                                                    e = e;
                                                    str3 = str2;
                                                    Slog.d("ORC/ChnVerificationCodeParser", e.getMessage(), e);
                                                    str5 = str3;
                                                    i4++;
                                                    i3 = i;
                                                    str4 = str2;
                                                    length = i2;
                                                }
                                            }
                                            i7 = i8 + 1;
                                            strSubstring3 = str9;
                                        } catch (IndexOutOfBoundsException e2) {
                                            e = e2;
                                            i = -1;
                                            Slog.d("ORC/ChnVerificationCodeParser", e.getMessage(), e);
                                            str5 = str3;
                                            i4++;
                                            i3 = i;
                                            str4 = str2;
                                            length = i2;
                                        }
                                    }
                                    i = -1;
                                } catch (IndexOutOfBoundsException e3) {
                                    e = e3;
                                    i2 = length;
                                    i = -1;
                                    Slog.d("ORC/ChnVerificationCodeParser", e.getMessage(), e);
                                    str5 = str3;
                                    i4++;
                                    i3 = i;
                                    str4 = str2;
                                    length = i2;
                                }
                            } else {
                                str3 = strGroup;
                                i2 = length;
                                i = -1;
                            }
                            String str10 = strSubstring2;
                            str5 = str3;
                            str7 = str10;
                            i6 = iIndexOf2;
                            length = i2;
                            iIndexOf2 = iIndexOf;
                            i3 = i;
                            i5 = 4;
                        } else {
                            str3 = strGroup;
                            i2 = length;
                            i = -1;
                            try {
                                iIndexOf = strSubstring2.indexOf(str6);
                                String str102 = strSubstring2;
                                str5 = str3;
                                str7 = str102;
                                i6 = iIndexOf2;
                                length = i2;
                                iIndexOf2 = iIndexOf;
                                i3 = i;
                                i5 = 4;
                            } catch (IndexOutOfBoundsException e4) {
                                e = e4;
                                Slog.d("ORC/ChnVerificationCodeParser", e.getMessage(), e);
                                str5 = str3;
                                i4++;
                                i3 = i;
                                str4 = str2;
                                length = i2;
                            }
                        }
                    } catch (IndexOutOfBoundsException e5) {
                        e = e5;
                        str3 = strGroup;
                    }
                } else {
                    strGroup = str2;
                    String strSubstring22 = str8.substring((str6.length() + iIndexOf2) - 1);
                    if (TextUtils.isEmpty(strGroup)) {
                    }
                }
                i4++;
                i3 = i;
                str4 = str2;
                length = i2;
            }
            i = i3;
            i2 = length;
            iIndexOf2 = i6;
            i4++;
            i3 = i;
            str4 = str2;
            length = i2;
        }
        String str11 = str2;
        for (String str12 : stringArray) {
            int iIndexOf4 = str.indexOf(str12);
            int i9 = 4;
            if (iIndexOf4 > 4) {
                String strSubstring4 = str.substring(0, iIndexOf4);
                Matcher matcher3 = Pattern.compile("[0-9]{4,}").matcher(strSubstring4);
                String strGroup2 = str2;
                while (true) {
                    if (!matcher3.find()) {
                        str11 = strGroup2;
                        break;
                    }
                    if (matcher3.group().length() >= i9 && matcher3.group().length() <= 7) {
                        this.mCodeFrontStartIndex = matcher3.start();
                        this.mCodeFrontEndIndex = matcher3.end();
                        Slog.d("ORC/ChnVerificationCodeParser", "mCodeFrontStartIndex = " + this.mCodeFrontStartIndex);
                        Slog.d("ORC/ChnVerificationCodeParser", "mCodeFrontEndIndex = " + this.mCodeFrontEndIndex);
                        if (strSubstring4.length() > matcher3.end() && "-".equals(String.valueOf(strSubstring4.charAt(matcher3.end())))) {
                            Slog.d("ORC/ChnVerificationCodeParser", "getFrontCode return null code");
                            str11 = str2;
                            break;
                        }
                        strGroup2 = matcher3.group();
                    }
                    i9 = 4;
                }
                iIndexOf2 = iIndexOf4;
            }
        }
        boolean z = false;
        for (String str13 : stringArray2) {
            int iIndexOf5 = str.indexOf(str13);
            if (iIndexOf5 > iIndexOf2 && this.mCodeBehindStartIndex > iIndexOf5) {
                z = true;
            }
        }
        if (str5 == null) {
            if (str11 == null) {
                Slog.d("ORC/ChnVerificationCodeParser", "isVerificationCode() is false");
                return str2;
            }
            Slog.d("ORC/ChnVerificationCodeParser", "KEY_STRONGLY_STR FrontCode= " + str11);
            Slog.d("ORC/ChnVerificationCodeParser", "isVerificationCode() is true");
            return str11;
        }
        if ((4 == str5.length() && !z) || 6 == str5.length()) {
            Slog.d("ORC/ChnVerificationCodeParser", "KEY_STRONGLY_STR BehindCode= ".concat(str5));
            Slog.d("ORC/ChnVerificationCodeParser", "isVerificationCode() is true");
            return str5;
        }
        if (str11 == null || !(4 == str11.length() || 6 == str11.length())) {
            Slog.d("ORC/ChnVerificationCodeParser", "KEY_STRONGLY_STR BehindCode= ".concat(str5));
            Slog.d("ORC/ChnVerificationCodeParser", "isVerificationCode() is true");
            return str5;
        }
        Slog.d("ORC/ChnVerificationCodeParser", "KEY_STRONGLY_STR FrontCode= ".concat(str11));
        Slog.d("ORC/ChnVerificationCodeParser", "isVerificationCode() is true");
        return str11;
    }
}
