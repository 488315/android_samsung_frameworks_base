package android.service.autofill;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.autofill.Helper;
import com.android.internal.util.Preconditions;
import java.util.Arrays;

/* loaded from: classes3.dex */
final class RequiredValidators extends InternalValidator {
    public static final Parcelable.Creator<RequiredValidators> CREATOR = new Parcelable.Creator<RequiredValidators>() { // from class: android.service.autofill.RequiredValidators.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RequiredValidators createFromParcel(Parcel parcel) {
            return new RequiredValidators((InternalValidator[]) parcel.readParcelableArray(null, InternalValidator.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RequiredValidators[] newArray(int i) {
            return new RequiredValidators[i];
        }
    };
    private static final String TAG = "RequiredValidators";
    private final InternalValidator[] mValidators;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    RequiredValidators(InternalValidator[] internalValidatorArr) {
        this.mValidators = (InternalValidator[]) Preconditions.checkArrayElementsNotNull(internalValidatorArr, "validators");
    }

    @Override // android.service.autofill.InternalValidator
    public boolean isValid(ValueFinder valueFinder) {
        for (InternalValidator internalValidator : this.mValidators) {
            boolean isValid = internalValidator.isValid(valueFinder);
            if (Helper.sDebug) {
                Log.d(TAG, "isValid(" + internalValidator + "): " + isValid);
            }
            if (!isValid) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        if (!Helper.sDebug) {
            return super.toString();
        }
        return "RequiredValidators: [validators=" + Arrays.toString(this.mValidators) + NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelableArray(this.mValidators, i);
    }
}
