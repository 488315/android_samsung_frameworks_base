package android.service.autofill;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Slog;
import android.view.autofill.AutofillValue;
import android.view.autofill.Helper;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public final class TextValueSanitizer extends InternalSanitizer implements Sanitizer, Parcelable {
    public static final Parcelable.Creator<TextValueSanitizer> CREATOR = new Parcelable.Creator<TextValueSanitizer>() { // from class: android.service.autofill.TextValueSanitizer.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextValueSanitizer createFromParcel(Parcel parcel) {
            return new TextValueSanitizer((Pattern) parcel.readSerializable(Pattern.class.getClassLoader(), Pattern.class), parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextValueSanitizer[] newArray(int i) {
            return new TextValueSanitizer[i];
        }
    };
    private static final String TAG = "TextValueSanitizer";
    private final Pattern mRegex;
    private final String mSubst;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TextValueSanitizer(Pattern pattern, String str) {
        this.mRegex = (Pattern) Objects.requireNonNull(pattern);
        this.mSubst = (String) Objects.requireNonNull(str);
    }

    @Override // android.service.autofill.InternalSanitizer
    public AutofillValue sanitize(AutofillValue autofillValue) {
        if (autofillValue == null) {
            Slog.w(TAG, "sanitize() called with null value");
            return null;
        }
        if (!autofillValue.isText()) {
            if (Helper.sDebug) {
                Slog.d(TAG, "sanitize() called with non-text value: " + autofillValue);
            }
            return null;
        }
        try {
            Matcher matcher = this.mRegex.matcher(autofillValue.getTextValue());
            if (!matcher.matches()) {
                if (Helper.sDebug) {
                    Slog.d(TAG, "sanitize(): " + this.mRegex + " failed for " + autofillValue);
                }
                return null;
            }
            return AutofillValue.forText(matcher.replaceAll(this.mSubst));
        } catch (Exception e) {
            Slog.w(TAG, "Exception evaluating " + this.mRegex + "/" + this.mSubst + ": " + e);
            return null;
        }
    }

    public String toString() {
        if (!Helper.sDebug) {
            return super.toString();
        }
        return "TextValueSanitizer: [regex=" + this.mRegex + ", subst=" + this.mSubst + NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(this.mRegex);
        parcel.writeString(this.mSubst);
    }
}
