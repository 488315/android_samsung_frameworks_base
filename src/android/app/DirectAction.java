package android.app;

import android.content.LocusId;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.util.Objects;

/* loaded from: classes.dex */
public final class DirectAction implements Parcelable {
    public static final Parcelable.Creator<DirectAction> CREATOR = new Parcelable.Creator<DirectAction>() { // from class: android.app.DirectAction.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DirectAction createFromParcel(Parcel parcel) {
            return new DirectAction(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DirectAction[] newArray(int i) {
            return new DirectAction[i];
        }
    };
    public static final String KEY_ACTIONS_LIST = "actions_list";
    private IBinder mActivityId;
    private final Bundle mExtras;
    private final String mID;
    private final LocusId mLocusId;
    private int mTaskId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DirectAction(String str, Bundle bundle, LocusId locusId) {
        this.mID = (String) Preconditions.checkStringNotEmpty(str);
        this.mExtras = bundle;
        this.mLocusId = locusId;
    }

    public void setSource(int i, IBinder iBinder) {
        this.mTaskId = i;
        this.mActivityId = iBinder;
    }

    public DirectAction(DirectAction directAction) {
        this.mTaskId = directAction.mTaskId;
        this.mActivityId = directAction.mActivityId;
        this.mID = directAction.mID;
        this.mExtras = directAction.mExtras;
        this.mLocusId = directAction.mLocusId;
    }

    private DirectAction(Parcel parcel) {
        this.mTaskId = parcel.readInt();
        this.mActivityId = parcel.readStrongBinder();
        this.mID = parcel.readString();
        this.mExtras = parcel.readBundle();
        String string = parcel.readString();
        this.mLocusId = string != null ? new LocusId(string) : null;
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    public IBinder getActivityId() {
        return this.mActivityId;
    }

    public String getId() {
        return this.mID;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public LocusId getLocusId() {
        return this.mLocusId;
    }

    public int hashCode() {
        return this.mID.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return this.mID.equals(((DirectAction) obj).mID);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mTaskId);
        parcel.writeStrongBinder(this.mActivityId);
        parcel.writeString(this.mID);
        parcel.writeBundle(this.mExtras);
        parcel.writeString(this.mLocusId.getId());
    }

    public static final class Builder {
        private Bundle mExtras;
        private String mId;
        private LocusId mLocusId;

        public Builder(String str) {
            Objects.requireNonNull(str);
            this.mId = str;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public Builder setLocusId(LocusId locusId) {
            this.mLocusId = locusId;
            return this;
        }

        public DirectAction build() {
            return new DirectAction(this.mId, this.mExtras, this.mLocusId);
        }
    }
}
