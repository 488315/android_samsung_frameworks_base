package android.service.autofill;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;
import android.util.SparseArray;
import android.view.autofill.Helper;
import android.widget.RemoteViews;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class CustomDescription implements Parcelable {
    public static final Parcelable.Creator<CustomDescription> CREATOR = new Parcelable.Creator<CustomDescription>() { // from class: android.service.autofill.CustomDescription.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CustomDescription createFromParcel(Parcel parcel) {
            RemoteViews remoteViews = (RemoteViews) parcel.readParcelable(null, RemoteViews.class);
            if (remoteViews == null) {
                return null;
            }
            Builder builder = new Builder(remoteViews);
            int[] iArrCreateIntArray = parcel.createIntArray();
            if (iArrCreateIntArray != null) {
                InternalTransformation[] internalTransformationArr = (InternalTransformation[]) parcel.readParcelableArray(null, InternalTransformation.class);
                int length = iArrCreateIntArray.length;
                for (int i = 0; i < length; i++) {
                    builder.addChild(iArrCreateIntArray[i], internalTransformationArr[i]);
                }
            }
            InternalValidator[] internalValidatorArr = (InternalValidator[]) parcel.readParcelableArray(null, InternalValidator.class);
            if (internalValidatorArr != null) {
                BatchUpdates[] batchUpdatesArr = (BatchUpdates[]) parcel.readParcelableArray(null, BatchUpdates.class);
                int length2 = internalValidatorArr.length;
                for (int i2 = 0; i2 < length2; i2++) {
                    builder.batchUpdate(internalValidatorArr[i2], batchUpdatesArr[i2]);
                }
            }
            int[] iArrCreateIntArray2 = parcel.createIntArray();
            if (iArrCreateIntArray2 != null) {
                InternalOnClickAction[] internalOnClickActionArr = (InternalOnClickAction[]) parcel.readParcelableArray(null, InternalOnClickAction.class);
                int length3 = iArrCreateIntArray2.length;
                for (int i3 = 0; i3 < length3; i3++) {
                    builder.addOnClickAction(iArrCreateIntArray2[i3], internalOnClickActionArr[i3]);
                }
            }
            return builder.build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CustomDescription[] newArray(int i) {
            return new CustomDescription[i];
        }
    };
    private final SparseArray<InternalOnClickAction> mActions;
    private final RemoteViews mPresentation;
    private final ArrayList<Pair<Integer, InternalTransformation>> mTransformations;
    private final ArrayList<Pair<InternalValidator, BatchUpdates>> mUpdates;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private CustomDescription(Builder builder) {
        this.mPresentation = builder.mPresentation;
        this.mTransformations = builder.mTransformations;
        this.mUpdates = builder.mUpdates;
        this.mActions = builder.mActions;
    }

    public RemoteViews getPresentation() {
        return this.mPresentation;
    }

    public ArrayList<Pair<Integer, InternalTransformation>> getTransformations() {
        return this.mTransformations;
    }

    public ArrayList<Pair<InternalValidator, BatchUpdates>> getUpdates() {
        return this.mUpdates;
    }

    public SparseArray<InternalOnClickAction> getActions() {
        return this.mActions;
    }

    public static class Builder {
        private SparseArray<InternalOnClickAction> mActions;
        private boolean mDestroyed;
        private final RemoteViews mPresentation;
        private ArrayList<Pair<Integer, InternalTransformation>> mTransformations;
        private ArrayList<Pair<InternalValidator, BatchUpdates>> mUpdates;

        public Builder(RemoteViews remoteViews) {
            this.mPresentation = (RemoteViews) Objects.requireNonNull(remoteViews);
        }

        public Builder addChild(int i, Transformation transformation) {
            throwIfDestroyed();
            Preconditions.checkArgument(transformation instanceof InternalTransformation, "not provided by Android System: %s", transformation);
            if (this.mTransformations == null) {
                this.mTransformations = new ArrayList<>();
            }
            this.mTransformations.add(new Pair<>(Integer.valueOf(i), (InternalTransformation) transformation));
            return this;
        }

        public Builder batchUpdate(Validator validator, BatchUpdates batchUpdates) {
            throwIfDestroyed();
            Preconditions.checkArgument(validator instanceof InternalValidator, "not provided by Android System: %s", validator);
            Objects.requireNonNull(batchUpdates);
            if (this.mUpdates == null) {
                this.mUpdates = new ArrayList<>();
            }
            this.mUpdates.add(new Pair<>((InternalValidator) validator, batchUpdates));
            return this;
        }

        public Builder addOnClickAction(int i, OnClickAction onClickAction) {
            throwIfDestroyed();
            Preconditions.checkArgument(onClickAction instanceof InternalOnClickAction, "not provided by Android System: %s", onClickAction);
            if (this.mActions == null) {
                this.mActions = new SparseArray<>();
            }
            this.mActions.put(i, (InternalOnClickAction) onClickAction);
            return this;
        }

        public CustomDescription build() {
            throwIfDestroyed();
            this.mDestroyed = true;
            return new CustomDescription(this);
        }

        private void throwIfDestroyed() {
            if (this.mDestroyed) {
                throw new IllegalStateException("Already called #build()");
            }
        }
    }

    public String toString() {
        if (!Helper.sDebug) {
            return super.toString();
        }
        StringBuilder sb = new StringBuilder("CustomDescription: [presentation=");
        sb.append(this.mPresentation);
        sb.append(", transformations=");
        ArrayList<Pair<Integer, InternalTransformation>> arrayList = this.mTransformations;
        sb.append(arrayList == null ? "N/A" : Integer.valueOf(arrayList.size()));
        sb.append(", updates=");
        ArrayList<Pair<InternalValidator, BatchUpdates>> arrayList2 = this.mUpdates;
        sb.append(arrayList2 == null ? "N/A" : Integer.valueOf(arrayList2.size()));
        sb.append(", actions=");
        SparseArray<InternalOnClickAction> sparseArray = this.mActions;
        sb.append(sparseArray != null ? Integer.valueOf(sparseArray.size()) : "N/A");
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mPresentation, i);
        if (this.mPresentation == null) {
            return;
        }
        ArrayList<Pair<Integer, InternalTransformation>> arrayList = this.mTransformations;
        if (arrayList == null) {
            parcel.writeIntArray(null);
        } else {
            int size = arrayList.size();
            int[] iArr = new int[size];
            InternalTransformation[] internalTransformationArr = new InternalTransformation[size];
            for (int i2 = 0; i2 < size; i2++) {
                Pair<Integer, InternalTransformation> pair = this.mTransformations.get(i2);
                iArr[i2] = pair.first.intValue();
                internalTransformationArr[i2] = pair.second;
            }
            parcel.writeIntArray(iArr);
            parcel.writeParcelableArray(internalTransformationArr, i);
        }
        ArrayList<Pair<InternalValidator, BatchUpdates>> arrayList2 = this.mUpdates;
        if (arrayList2 == null) {
            parcel.writeParcelableArray(null, i);
        } else {
            int size2 = arrayList2.size();
            InternalValidator[] internalValidatorArr = new InternalValidator[size2];
            BatchUpdates[] batchUpdatesArr = new BatchUpdates[size2];
            for (int i3 = 0; i3 < size2; i3++) {
                Pair<InternalValidator, BatchUpdates> pair2 = this.mUpdates.get(i3);
                internalValidatorArr[i3] = pair2.first;
                batchUpdatesArr[i3] = pair2.second;
            }
            parcel.writeParcelableArray(internalValidatorArr, i);
            parcel.writeParcelableArray(batchUpdatesArr, i);
        }
        SparseArray<InternalOnClickAction> sparseArray = this.mActions;
        if (sparseArray == null) {
            parcel.writeIntArray(null);
            return;
        }
        int size3 = sparseArray.size();
        int[] iArr2 = new int[size3];
        InternalOnClickAction[] internalOnClickActionArr = new InternalOnClickAction[size3];
        for (int i4 = 0; i4 < size3; i4++) {
            iArr2[i4] = this.mActions.keyAt(i4);
            internalOnClickActionArr[i4] = this.mActions.valueAt(i4);
        }
        parcel.writeIntArray(iArr2);
        parcel.writeParcelableArray(internalOnClickActionArr, i);
    }
}
