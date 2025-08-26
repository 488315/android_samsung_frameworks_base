package android.telephony;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.text.style.TtsSpan;
import com.android.i18n.phonenumbers.AsYouTypeFormatter;
import com.android.i18n.phonenumbers.PhoneNumberUtil;
import java.util.Locale;

/* loaded from: classes4.dex */
public class PhoneNumberFormattingTextWatcher implements TextWatcher {
    private AsYouTypeFormatter mFormatter;
    private boolean mSelfChange;
    private boolean mStopFormatting;

    public PhoneNumberFormattingTextWatcher() {
        this(Locale.getDefault().getCountry());
    }

    public PhoneNumberFormattingTextWatcher(String str) {
        this.mSelfChange = false;
        if (str == null) {
            throw new IllegalArgumentException();
        }
        this.mFormatter = PhoneNumberUtil.getInstance().getAsYouTypeFormatter(str);
    }

    @Override // android.text.TextWatcher
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (this.mSelfChange || this.mStopFormatting || i2 <= 0 || !hasSeparator(charSequence, i, i2)) {
            return;
        }
        stopFormatting();
    }

    @Override // android.text.TextWatcher
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        if (this.mSelfChange || this.mStopFormatting || i3 <= 0 || !hasSeparator(charSequence, i, i3)) {
            return;
        }
        stopFormatting();
    }

    @Override // android.text.TextWatcher
    public synchronized void afterTextChanged(Editable editable) {
        String strReformat;
        Editable editable2;
        boolean z = true;
        if (this.mStopFormatting) {
            if (editable.length() == 0) {
                z = false;
            }
            this.mStopFormatting = z;
            return;
        }
        if (this.mSelfChange) {
            return;
        }
        try {
            strReformat = reformat(editable, Selection.getSelectionEnd(editable));
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            strReformat = null;
        }
        String str = strReformat;
        if (str != null) {
            int rememberedPosition = this.mFormatter.getRememberedPosition();
            this.mSelfChange = true;
            editable2 = editable;
            editable2.replace(0, editable.length(), str, 0, str.length());
            if (str.equals(editable2.toString())) {
                Selection.setSelection(editable2, rememberedPosition);
            }
            this.mSelfChange = false;
        } else {
            editable2 = editable;
        }
        for (TtsSpan ttsSpan : (TtsSpan[]) editable2.getSpans(0, editable2.length(), TtsSpan.class)) {
            editable2.removeSpan(ttsSpan);
        }
        PhoneNumberUtils.ttsSpanAsPhoneNumber(editable2, 0, editable2.length());
    }

    private String reformat(CharSequence charSequence, int i) {
        int i2 = i - 1;
        this.mFormatter.clear();
        int length = charSequence.length();
        String formattedNumber = null;
        char c = 0;
        boolean z = false;
        for (int i3 = 0; i3 < length; i3++) {
            char cCharAt = charSequence.charAt(i3);
            if (PhoneNumberUtils.isNonSeparator(cCharAt)) {
                if (c != 0) {
                    formattedNumber = getFormattedNumber(c, z);
                    z = false;
                }
                c = cCharAt;
            }
            if (i3 == i2) {
                z = true;
            }
        }
        return c != 0 ? getFormattedNumber(c, z) : formattedNumber;
    }

    private String getFormattedNumber(char c, boolean z) {
        if (z) {
            return this.mFormatter.inputDigitAndRememberPosition(c);
        }
        return this.mFormatter.inputDigit(c);
    }

    private void stopFormatting() {
        this.mStopFormatting = true;
        this.mFormatter.clear();
    }

    private boolean hasSeparator(CharSequence charSequence, int i, int i2) {
        for (int i3 = i; i3 < i + i2; i3++) {
            if (!PhoneNumberUtils.isNonSeparator(charSequence.charAt(i3))) {
                return true;
            }
        }
        return false;
    }
}
