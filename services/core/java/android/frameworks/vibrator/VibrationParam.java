package android.frameworks.vibrator;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class VibrationParam implements Parcelable {
    public static final Parcelable.Creator CREATOR = new AnonymousClass1();
    public int _tag;
    public Object _value;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: android.frameworks.vibrator.VibrationParam$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            VibrationParam vibrationParam = new VibrationParam();
            int readInt = parcel.readInt();
            if (readInt != 0) {
                throw new IllegalArgumentException(
                        VibrationParam$1$$ExternalSyntheticOutline0.m(
                                readInt, "union: unknown tag: "));
            }
            ScaleParam scaleParam = (ScaleParam) parcel.readTypedObject(ScaleParam.CREATOR);
            vibrationParam._tag = readInt;
            vibrationParam._value = scaleParam;
            return vibrationParam;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new VibrationParam[i];
        }
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        if (this._tag != 0) {
            return 0;
        }
        getScale();
        return 0;
    }

    public final ScaleParam getScale() {
        if (this._tag == 0) {
            return (ScaleParam) this._value;
        }
        int i = this._tag;
        if (i == 0) {
            throw new IllegalStateException("bad access: scale, scale is available.");
        }
        throw new IllegalStateException(
                VibrationParam$1$$ExternalSyntheticOutline0.m(i, "unknown field: "));
    }

    public final int getStability() {
        return 1;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this._tag);
        if (this._tag != 0) {
            return;
        }
        parcel.writeTypedObject(getScale(), i);
    }
}
