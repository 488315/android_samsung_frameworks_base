package android.service.autofill;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;
import android.view.autofill.Helper;
import android.widget.RemoteViews;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class BatchUpdates implements Parcelable {
    public static final Parcelable.Creator<BatchUpdates> CREATOR = new Parcelable.Creator<BatchUpdates>() { // from class: android.service.autofill.BatchUpdates.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BatchUpdates createFromParcel(Parcel parcel) {
            Builder builder = new Builder();
            int[] createIntArray = parcel.createIntArray();
            if (createIntArray != null) {
                InternalTransformation[] internalTransformationArr = (InternalTransformation[]) parcel.readParcelableArray(null, InternalTransformation.class);
                int length = createIntArray.length;
                for (int i = 0; i < length; i++) {
                    builder.transformChild(createIntArray[i], internalTransformationArr[i]);
                }
            }
            RemoteViews remoteViews = (RemoteViews) parcel.readParcelable(null, RemoteViews.class);
            if (remoteViews != null) {
                builder.updateTemplate(remoteViews);
            }
            return builder.build();
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BatchUpdates[] newArray(int i) {
            return new BatchUpdates[i];
        }
    };
    private final ArrayList<Pair<Integer, InternalTransformation>> mTransformations;
    private final RemoteViews mUpdates;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private BatchUpdates(Builder builder) {
        this.mTransformations = builder.mTransformations;
        this.mUpdates = builder.mUpdates;
    }

    public ArrayList<Pair<Integer, InternalTransformation>> getTransformations() {
        return this.mTransformations;
    }

    public RemoteViews getUpdates() {
        return this.mUpdates;
    }

    public static class Builder {
        private boolean mDestroyed;
        private ArrayList<Pair<Integer, InternalTransformation>> mTransformations;
        private RemoteViews mUpdates;

        public Builder updateTemplate(RemoteViews remoteViews) {
            throwIfDestroyed();
            this.mUpdates = (RemoteViews) Objects.requireNonNull(remoteViews);
            return this;
        }

        public Builder transformChild(int i, Transformation transformation) {
            throwIfDestroyed();
            Preconditions.checkArgument(transformation instanceof InternalTransformation, "not provided by Android System: %s", transformation);
            if (this.mTransformations == null) {
                this.mTransformations = new ArrayList<>();
            }
            this.mTransformations.add(new Pair<>(Integer.valueOf(i), (InternalTransformation) transformation));
            return this;
        }

        public BatchUpdates build() {
            throwIfDestroyed();
            Preconditions.checkState((this.mUpdates == null && this.mTransformations == null) ? false : true, "must call either updateTemplate() or transformChild() at least once");
            this.mDestroyed = true;
            return new BatchUpdates(this);
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
        StringBuilder sb = new StringBuilder("BatchUpdates: [, transformations=");
        ArrayList<Pair<Integer, InternalTransformation>> arrayList = this.mTransformations;
        sb.append(arrayList == null ? "N/A" : Integer.valueOf(arrayList.size()));
        sb.append(", updates=");
        sb.append(this.mUpdates);
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
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
        parcel.writeParcelable(this.mUpdates, i);
    }
}
