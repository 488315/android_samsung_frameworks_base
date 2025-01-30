package androidx.picker.helper;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;
import androidx.picker.features.search.InitialSearchUtils;
import androidx.reflect.text.SeslTextUtilsReflector;
import java.util.Locale;
import java.util.StringTokenizer;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class TextViewHelperKt {
    public static final void limitFontLarge(TextView textView) {
        float textSize = textView.getTextSize();
        float f = textView.getResources().getConfiguration().fontScale;
        if (f > 1.3f) {
            textSize = (textSize / f) * 1.3f;
        }
        textView.setTextSize(0, textSize);
    }

    public static final void setHighLightText(TextView textView, String str, int i) {
        if (str.length() == 0) {
            textView.setText(textView.getText().toString());
            return;
        }
        String obj = textView.getText().toString();
        SpannableString spannableString = new SpannableString(obj);
        StringTokenizer stringTokenizer = new StringTokenizer(str);
        while (stringTokenizer.hasMoreTokens()) {
            String nextToken = stringTokenizer.nextToken();
            String str2 = obj;
            int i2 = 0;
            do {
                char[] semGetPrefixCharForSpan = SeslTextUtilsReflector.semGetPrefixCharForSpan(textView.getPaint(), str2, nextToken.toCharArray());
                if (semGetPrefixCharForSpan != null) {
                    if (!(semGetPrefixCharForSpan.length == 0)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append((CharSequence) "");
                        int i3 = 0;
                        for (char c : semGetPrefixCharForSpan) {
                            i3++;
                            if (i3 > 1) {
                                sb.append((CharSequence) ", ");
                            }
                            sb.append(c);
                        }
                        sb.append((CharSequence) "");
                        nextToken = sb.toString();
                    }
                }
                String lowerCase = str2.toLowerCase(Locale.getDefault());
                int matchedStringOffset = str2.length() == lowerCase.length() ? InitialSearchUtils.getMatchedStringOffset(lowerCase, nextToken.toLowerCase(Locale.getDefault())) : StringsKt__StringsKt.indexOf$default(str2, nextToken, 0, false, 6);
                if (matchedStringOffset < 0) {
                    break;
                }
                int length = nextToken.length() + matchedStringOffset;
                int i4 = matchedStringOffset + i2;
                i2 += length;
                int length2 = spannableString.length();
                if (i2 <= length2) {
                    length2 = i2;
                }
                spannableString.setSpan(new ForegroundColorSpan(i), i4, length2, 17);
                spannableString.setSpan(new StyleSpan(1), i4, length2, 33);
                int length3 = str2.length();
                if (length > length3) {
                    length = length3;
                }
                str2 = str2.substring(length);
                if (StringsKt__StringsKt.contains(str2.toLowerCase(Locale.getDefault()), nextToken.toLowerCase(Locale.getDefault()), false)) {
                }
            } while (i2 < 200);
        }
        textView.setText(spannableString);
    }
}
