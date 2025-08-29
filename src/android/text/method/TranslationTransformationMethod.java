package android.text.method;

import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.translation.TranslationResponseValue;
import android.view.translation.ViewTranslationRequest;
import android.view.translation.ViewTranslationResponse;
import android.widget.TextView;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class TranslationTransformationMethod implements TransformationMethod2 {
    private static final Pattern PATTERN_WHITESPACE = Pattern.compile("\\s+");
    private static final String TAG = "TranslationTransformationMethod";
    private Float SEP_VERSION = Float.valueOf(Float.parseFloat("17.0"));
    private boolean mAllowLengthChanges;
    private TransformationMethod mOriginalTranslationMethod;
    private final ViewTranslationResponse mTranslationResponse;

    @Override // android.text.method.TransformationMethod
    public void onFocusChanged(View view, CharSequence charSequence, boolean z, int i, Rect rect) {
    }

    public TranslationTransformationMethod(ViewTranslationResponse viewTranslationResponse, TransformationMethod transformationMethod) {
        this.mTranslationResponse = viewTranslationResponse;
        this.mOriginalTranslationMethod = transformationMethod;
    }

    public TransformationMethod getOriginalTransformationMethod() {
        return this.mOriginalTranslationMethod;
    }

    public ViewTranslationResponse getViewTranslationResponse() {
        return this.mTranslationResponse;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0106  */
    /* JADX WARN: Type inference failed for: r7v6 */
    @Override // android.text.method.TransformationMethod
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public CharSequence getTransformation(CharSequence charSequence, View view) {
        double d;
        CharSequence text;
        CharSequence charSequence2;
        CharSequence charSequence3;
        SpannableString spannableString;
        if (!this.mAllowLengthChanges) {
            Log.w(TAG, "Caller did not enable length changes; not transforming to translated text");
            return charSequence;
        }
        TranslationResponseValue value = this.mTranslationResponse.getValue(ViewTranslationRequest.ID_TEXT);
        boolean z = value.getExtras().getBoolean("show_origin_message");
        String str = "\n\n";
        CharSequence charSequence4 = null;
        if (value.getStatusCode() == 0) {
            text = value.getText();
            if (this.SEP_VERSION.floatValue() >= 15.1d && (view instanceof TextView) && z) {
                try {
                    ColorStateList textColors = ((TextView) view).getTextColors();
                    ColorStateList colorStateListWithAlpha = textColors.withAlpha(230);
                    ColorStateList colorStateListWithAlpha2 = textColors.withAlpha(179);
                    int[] colors = colorStateListWithAlpha.getColors();
                    int[] colors2 = colorStateListWithAlpha2.getColors();
                    charSequence4 = value.getExtras().getCharSequence("show_disclaimer");
                    SpannableString spannableString2 = new SpannableString(text);
                    SpannableString spannableString3 = new SpannableString("\n\n");
                    d = 15.1d;
                    try {
                        spannableString3.setSpan(new RelativeSizeSpan(0.3f), 1, spannableString3.length(), 33);
                        try {
                            spannableString = new SpannableString("\n\n");
                            spannableString.setSpan(new RelativeSizeSpan(0.3f), 1, spannableString.length(), 33);
                            try {
                                spannableString2.setSpan(new ForegroundColorSpan(colors[0]), 0, spannableString2.length(), 33);
                                if (charSequence4 != null) {
                                    try {
                                        SpannableString spannableString4 = new SpannableString(charSequence4);
                                        spannableString4.setSpan(new ForegroundColorSpan(colors2[0]), 0, spannableString4.length(), 33);
                                        spannableString4.setSpan(new RelativeSizeSpan(0.92f), 0, spannableString4.length(), 33);
                                        charSequence4 = spannableString4;
                                    } catch (Exception e) {
                                        e = e;
                                        text = spannableString2;
                                        str = spannableString3;
                                        charSequence3 = spannableString;
                                        Log.e(TAG, "trans color change exception " + e);
                                        charSequence2 = charSequence3;
                                        if (TextUtils.isEmpty(text)) {
                                        }
                                    }
                                }
                                text = spannableString2;
                                str = spannableString3;
                                charSequence2 = spannableString;
                            } catch (Exception e2) {
                                e = e2;
                            }
                        } catch (Exception e3) {
                            e = e3;
                            spannableString = "\n\n";
                        }
                    } catch (Exception e4) {
                        e = e4;
                        charSequence3 = "\n\n";
                        Log.e(TAG, "trans color change exception " + e);
                        charSequence2 = charSequence3;
                        if (TextUtils.isEmpty(text)) {
                        }
                    }
                } catch (Exception e5) {
                    e = e5;
                    d = 15.1d;
                }
                return (!TextUtils.isEmpty(text) || isWhitespace(text.toString())) ? charSequence : (((double) this.SEP_VERSION.floatValue()) >= d && (view instanceof TextView) && z) ? (charSequence4 == null || charSequence4.length() <= 0) ? TextUtils.concat(charSequence, str, text) : TextUtils.concat(charSequence, str, text, charSequence2, charSequence4) : text;
            }
            d = 15.1d;
        } else {
            d = 15.1d;
            text = "";
        }
        charSequence2 = "\n\n";
        if (TextUtils.isEmpty(text)) {
        }
    }

    @Override // android.text.method.TransformationMethod2
    public void setLengthChangesAllowed(boolean z) {
        this.mAllowLengthChanges = z;
    }

    private boolean isWhitespace(String str) {
        return PATTERN_WHITESPACE.matcher(str.substring(0, str.length())).matches();
    }
}
