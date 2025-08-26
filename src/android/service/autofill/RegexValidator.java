package android.service.autofill;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.autofill.AutofillId;
import android.view.autofill.Helper;
import java.io.IOException;
import java.util.Objects;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public final class RegexValidator extends InternalValidator implements Validator, Parcelable {
    public static final Parcelable.Creator<RegexValidator> CREATOR = new Parcelable.Creator<RegexValidator>() { // from class: android.service.autofill.RegexValidator.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RegexValidator createFromParcel(Parcel parcel) {
            return new RegexValidator((AutofillId) parcel.readParcelable(null, AutofillId.class), (Pattern) parcel.readSerializable(Pattern.class.getClassLoader(), Pattern.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RegexValidator[] newArray(int i) {
            return new RegexValidator[i];
        }
    };
    private static final String TAG = "RegexValidator";
    private final AutofillId mId;
    private final Pattern mRegex;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RegexValidator(AutofillId autofillId, Pattern pattern) {
        this.mId = (AutofillId) Objects.requireNonNull(autofillId);
        this.mRegex = (Pattern) Objects.requireNonNull(pattern);
    }

    @Override // android.service.autofill.InternalValidator
    public boolean isValid(ValueFinder valueFinder) {
        String strFindByAutofillId = valueFinder.findByAutofillId(this.mId);
        if (strFindByAutofillId == null) {
            Log.w(TAG, "No view for id " + this.mId);
            return false;
        }
        boolean zMatches = this.mRegex.matcher(strFindByAutofillId).matches();
        if (Helper.sDebug) {
            Log.d(TAG, "isValid(): " + zMatches);
        }
        return zMatches;
    }

    public String toString() {
        if (!Helper.sDebug) {
            return super.toString();
        }
        return "RegexValidator: [id=" + this.mId + ", regex=" + this.mRegex + NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) throws IOException {
        parcel.writeParcelable(this.mId, i);
        parcel.writeSerializable(this.mRegex);
    }
}
