package android.service.autofill;

import android.icu.text.DateFormat;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import android.view.autofill.Helper;
import android.widget.RemoteViews;

import java.util.Date;
import java.util.Objects;

public final class DateTransformation extends InternalTransformation
        implements Transformation, Parcelable {
    public static final Parcelable.Creator<DateTransformation> CREATOR =
            new Parcelable.Creator<
                    DateTransformation>() { // from class:
                                            // android.service.autofill.DateTransformation.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public DateTransformation createFromParcel(Parcel parcel) {
                    return new DateTransformation(
                            (AutofillId) parcel.readParcelable(null, AutofillId.class),
                            (DateFormat)
                                    parcel.readSerializable(
                                            DateFormat.class.getClassLoader(), DateFormat.class));
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public DateTransformation[] newArray(int size) {
                    return new DateTransformation[size];
                }
            };
    private static final String TAG = "DateTransformation";
    private final DateFormat mDateFormat;
    private final AutofillId mFieldId;

    public DateTransformation(AutofillId id, DateFormat dateFormat) {
        this.mFieldId = (AutofillId) Objects.requireNonNull(id);
        this.mDateFormat = (DateFormat) Objects.requireNonNull(dateFormat);
    }

    @Override // android.service.autofill.InternalTransformation
    public void apply(ValueFinder finder, RemoteViews parentTemplate, int childViewId)
            throws Exception {
        AutofillValue value = finder.findRawValueByAutofillId(this.mFieldId);
        if (value == null) {
            Log.w(TAG, "No value for id " + this.mFieldId);
            return;
        }
        if (!value.isDate()) {
            Log.w(TAG, "Value for " + this.mFieldId + " is not date: " + value);
            return;
        }
        try {
            Date date = new Date(value.getDateValue());
            String transformed = this.mDateFormat.format(date);
            if (Helper.sDebug) {
                Log.d(TAG, "Transformed " + date + " to " + transformed);
            }
            parentTemplate.setCharSequence(childViewId, "setText", transformed);
        } catch (Exception e) {
            Log.w(TAG, "Could not apply " + this.mDateFormat + " to " + value + ": " + e);
        }
    }

    public String toString() {
        return !Helper.sDebug
                ? super.toString()
                : "DateTransformation: [id="
                        + this.mFieldId
                        + ", format="
                        + this.mDateFormat
                        + NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelable(this.mFieldId, flags);
        parcel.writeSerializable(this.mDateFormat);
    }
}
