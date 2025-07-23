package android.text.method;

import android.graphics.Rect;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.translation.TranslationResponseValue;
import android.view.translation.ViewTranslationRequest;
import android.view.translation.ViewTranslationResponse;
import android.widget.TextView;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
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

    @Override // android.text.method.TransformationMethod
    public CharSequence getTransformation(CharSequence charSequence, View view) {
        CharSequence charSequence2;
        if (!this.mAllowLengthChanges) {
            Log.w(TAG, "Caller did not enable length changes; not transforming to translated text");
            return charSequence;
        }
        TranslationResponseValue value = this.mTranslationResponse.getValue(ViewTranslationRequest.ID_TEXT);
        boolean z = value.getExtras().getBoolean("show_origin_message");
        if (value.getStatusCode() == 0) {
            charSequence2 = value.getText();
            if (this.SEP_VERSION.floatValue() >= 15.1d && (view instanceof TextView) && z) {
                try {
                    int[] colors = ((TextView) view).getTextColors().withAlpha(179).getColors();
                    SpannableString spannableString = new SpannableString(charSequence2);
                    spannableString.setSpan(new ForegroundColorSpan(colors[0]), 0, spannableString.length(), 33);
                    charSequence2 = spannableString;
                } catch (Exception e) {
                    Log.e(TAG, "trans color change exception " + e);
                }
            }
        } else {
            charSequence2 = "";
        }
        return (TextUtils.isEmpty(charSequence2) || isWhitespace(charSequence2.toString())) ? charSequence : (((double) this.SEP_VERSION.floatValue()) >= 15.1d && (view instanceof TextView) && z) ? TextUtils.concat(charSequence, ShaderAssembler.NEWLINE, charSequence2) : charSequence2;
    }

    @Override // android.text.method.TransformationMethod2
    public void setLengthChangesAllowed(boolean z) {
        this.mAllowLengthChanges = z;
    }

    private boolean isWhitespace(String str) {
        return PATTERN_WHITESPACE.matcher(str.substring(0, str.length())).matches();
    }
}
