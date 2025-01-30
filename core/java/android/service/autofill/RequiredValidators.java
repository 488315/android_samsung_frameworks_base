package android.service.autofill;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.util.Log;
import android.view.autofill.Helper;
import com.android.internal.util.Preconditions;
import java.util.Arrays;

/* loaded from: classes3.dex */
final class RequiredValidators extends InternalValidator {
  public static final Parcelable.Creator<RequiredValidators> CREATOR =
      new Parcelable.Creator<
          RequiredValidators>() { // from class: android.service.autofill.RequiredValidators.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RequiredValidators createFromParcel(Parcel parcel) {
          return new RequiredValidators(
              (InternalValidator[]) parcel.readParcelableArray(null, InternalValidator.class));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RequiredValidators[] newArray(int size) {
          return new RequiredValidators[size];
        }
      };
  private static final String TAG = "RequiredValidators";
  private final InternalValidator[] mValidators;

  RequiredValidators(InternalValidator[] validators) {
    this.mValidators =
        (InternalValidator[]) Preconditions.checkArrayElementsNotNull(validators, "validators");
  }

  @Override // android.service.autofill.InternalValidator
  public boolean isValid(ValueFinder finder) {
    for (InternalValidator validator : this.mValidators) {
      boolean valid = validator.isValid(finder);
      if (Helper.sDebug) {
        Log.m94d(TAG, "isValid(" + validator + "): " + valid);
      }
      if (!valid) {
        return false;
      }
    }
    return true;
  }

  public String toString() {
    return !Helper.sDebug
        ? super.toString()
        : "RequiredValidators: [validators="
            + Arrays.toString(this.mValidators)
            + NavigationBarInflaterView.SIZE_MOD_END;
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeParcelableArray(this.mValidators, flags);
  }
}
