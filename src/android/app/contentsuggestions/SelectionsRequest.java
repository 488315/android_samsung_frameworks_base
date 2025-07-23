package android.app.contentsuggestions;

import android.annotation.SystemApi;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
/* loaded from: classes.dex */
public final class SelectionsRequest implements Parcelable {
    public static final Parcelable.Creator<SelectionsRequest> CREATOR = new Parcelable.Creator<SelectionsRequest>() { // from class: android.app.contentsuggestions.SelectionsRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SelectionsRequest createFromParcel(Parcel parcel) {
            return new SelectionsRequest(parcel.readInt(), (Point) parcel.readTypedObject(Point.CREATOR), parcel.readBundle());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SelectionsRequest[] newArray(int i) {
            return new SelectionsRequest[i];
        }
    };
    private final Bundle mExtras;
    private final Point mInterestPoint;
    private final int mTaskId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private SelectionsRequest(int i, Point point, Bundle bundle) {
        this.mTaskId = i;
        this.mInterestPoint = point;
        this.mExtras = bundle;
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    public Point getInterestPoint() {
        return this.mInterestPoint;
    }

    public Bundle getExtras() {
        Bundle bundle = this.mExtras;
        return bundle == null ? new Bundle() : bundle;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mTaskId);
        parcel.writeTypedObject(this.mInterestPoint, i);
        parcel.writeBundle(this.mExtras);
    }

    @SystemApi
    public static final class Builder {
        private Bundle mExtras;
        private Point mInterestPoint;
        private final int mTaskId;

        public Builder(int i) {
            this.mTaskId = i;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public Builder setInterestPoint(Point point) {
            this.mInterestPoint = point;
            return this;
        }

        public SelectionsRequest build() {
            return new SelectionsRequest(this.mTaskId, this.mInterestPoint, this.mExtras);
        }
    }
}
