package android.service.autofill;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Downloads;
import android.util.Log;
import android.view.autofill.AutofillId;
import android.view.autofill.Helper;
import com.android.internal.util.Preconditions;
import java.util.Arrays;

/* loaded from: classes3.dex */
public final class LuhnChecksumValidator extends InternalValidator implements Validator, Parcelable {
    public static final Parcelable.Creator<LuhnChecksumValidator> CREATOR = new Parcelable.Creator<LuhnChecksumValidator>() { // from class: android.service.autofill.LuhnChecksumValidator.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LuhnChecksumValidator createFromParcel(Parcel parcel) {
            return new LuhnChecksumValidator((AutofillId[]) parcel.readParcelableArray(null, AutofillId.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LuhnChecksumValidator[] newArray(int i) {
            return new LuhnChecksumValidator[i];
        }
    };
    private static final String TAG = "LuhnChecksumValidator";
    private final AutofillId[] mIds;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public LuhnChecksumValidator(AutofillId... autofillIdArr) {
        this.mIds = (AutofillId[]) Preconditions.checkArrayElementsNotNull(autofillIdArr, Downloads.EXTRA_IDS);
    }

    private static boolean isLuhnChecksumValid(String str) {
        int i = 0;
        boolean z = false;
        for (int length = str.length() - 1; length >= 0; length--) {
            int iCharAt = str.charAt(length) - '0';
            if (iCharAt >= 0 && iCharAt <= 9) {
                if (z && (iCharAt = iCharAt * 2) > 9) {
                    iCharAt -= 9;
                }
                i += iCharAt;
                z = !z;
            }
        }
        return i % 10 == 0;
    }

    @Override // android.service.autofill.InternalValidator
    public boolean isValid(ValueFinder valueFinder) {
        AutofillId[] autofillIdArr = this.mIds;
        if (autofillIdArr == null || autofillIdArr.length == 0) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        for (AutofillId autofillId : this.mIds) {
            String strFindByAutofillId = valueFinder.findByAutofillId(autofillId);
            if (strFindByAutofillId == null) {
                if (Helper.sDebug) {
                    Log.d(TAG, "No partial number for id " + autofillId);
                }
                return false;
            }
            sb.append(strFindByAutofillId);
        }
        String string = sb.toString();
        boolean zIsLuhnChecksumValid = isLuhnChecksumValid(string);
        if (Helper.sDebug) {
            Log.d(TAG, "isValid(" + string.length() + " chars): " + zIsLuhnChecksumValid);
        }
        return zIsLuhnChecksumValid;
    }

    public String toString() {
        if (!Helper.sDebug) {
            return super.toString();
        }
        return "LuhnChecksumValidator: [ids=" + Arrays.toString(this.mIds) + NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelableArray(this.mIds, i);
    }
}
