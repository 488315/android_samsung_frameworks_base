package android.service.autofill;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.autofill.Helper;

import com.android.internal.util.Preconditions;

import java.util.Arrays;

final class OptionalValidators extends InternalValidator {
    public static final Parcelable.Creator<OptionalValidators> CREATOR =
            new Parcelable.Creator<
                    OptionalValidators>() { // from class:
                                            // android.service.autofill.OptionalValidators.1
                @Override // android.os.Parcelable.Creator
                public OptionalValidators createFromParcel(Parcel parcel) {
                    return new OptionalValidators(
                            (InternalValidator[])
                                    parcel.readParcelableArray(null, InternalValidator.class));
                }

                @Override // android.os.Parcelable.Creator
                public OptionalValidators[] newArray(int size) {
                    return new OptionalValidators[size];
                }
            };
    private static final String TAG = "OptionalValidators";
    private final InternalValidator[] mValidators;

    OptionalValidators(InternalValidator[] validators) {
        this.mValidators =
                (InternalValidator[])
                        Preconditions.checkArrayElementsNotNull(validators, "validators");
    }

    @Override // android.service.autofill.InternalValidator
    public boolean isValid(ValueFinder finder) {
        for (InternalValidator validator : this.mValidators) {
            boolean valid = validator.isValid(finder);
            if (Helper.sDebug) {
                Log.d(TAG, "isValid(" + validator + "): " + valid);
            }
            if (valid) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return !Helper.sDebug
                ? super.toString()
                : "OptionalValidators: [validators="
                        + Arrays.toString(this.mValidators)
                        + NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelableArray(this.mValidators, flags);
    }
}
