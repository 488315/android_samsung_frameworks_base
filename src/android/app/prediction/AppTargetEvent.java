package android.app.prediction;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class AppTargetEvent implements Parcelable {
    public static final int ACTION_DISMISS = 2;
    public static final int ACTION_LAUNCH = 1;
    public static final int ACTION_PIN = 3;
    public static final int ACTION_UNDISMISS = 5;
    public static final int ACTION_UNPIN = 4;
    public static final Parcelable.Creator<AppTargetEvent> CREATOR = new Parcelable.Creator<AppTargetEvent>() { // from class: android.app.prediction.AppTargetEvent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppTargetEvent createFromParcel(Parcel parcel) {
            return new AppTargetEvent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppTargetEvent[] newArray(int i) {
            return new AppTargetEvent[i];
        }
    };
    private final int mAction;
    private final String mLocation;
    private final AppTarget mTarget;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ActionType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private AppTargetEvent(AppTarget appTarget, String str, int i) {
        this.mTarget = appTarget;
        this.mLocation = str;
        this.mAction = i;
    }

    private AppTargetEvent(Parcel parcel) {
        this.mTarget = (AppTarget) parcel.readParcelable(null, AppTarget.class);
        this.mLocation = parcel.readString();
        this.mAction = parcel.readInt();
    }

    public AppTarget getTarget() {
        return this.mTarget;
    }

    public String getLaunchLocation() {
        return this.mLocation;
    }

    public int getAction() {
        return this.mAction;
    }

    public boolean equals(Object obj) {
        if (!getClass().equals(obj != null ? obj.getClass() : null)) {
            return false;
        }
        AppTargetEvent appTargetEvent = (AppTargetEvent) obj;
        return this.mTarget.equals(appTargetEvent.mTarget) && this.mLocation.equals(appTargetEvent.mLocation) && this.mAction == appTargetEvent.mAction;
    }

    public int hashCode() {
        return (Objects.hash(this.mTarget, this.mLocation) * 31) + this.mAction;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mTarget, 0);
        parcel.writeString(this.mLocation);
        parcel.writeInt(this.mAction);
    }

    @SystemApi
    public static final class Builder {
        private int mAction;
        private String mLocation;
        private AppTarget mTarget;

        public Builder(AppTarget appTarget, int i) {
            this.mTarget = appTarget;
            this.mAction = i;
        }

        public Builder setLaunchLocation(String str) {
            this.mLocation = str;
            return this;
        }

        public AppTargetEvent build() {
            return new AppTargetEvent(this.mTarget, this.mLocation, this.mAction);
        }
    }
}
