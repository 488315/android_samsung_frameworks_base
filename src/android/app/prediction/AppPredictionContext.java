package android.app.prediction;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class AppPredictionContext implements Parcelable {
    public static final Parcelable.Creator<AppPredictionContext> CREATOR = new Parcelable.Creator<AppPredictionContext>() { // from class: android.app.prediction.AppPredictionContext.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppPredictionContext createFromParcel(Parcel parcel) {
            return new AppPredictionContext(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppPredictionContext[] newArray(int i) {
            return new AppPredictionContext[i];
        }
    };
    private final Bundle mExtras;
    private final String mPackageName;
    private final int mPredictedTargetCount;
    private final String mUiSurface;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private AppPredictionContext(String str, int i, String str2, Bundle bundle) {
        this.mUiSurface = str;
        this.mPredictedTargetCount = i;
        this.mPackageName = str2;
        this.mExtras = bundle;
    }

    private AppPredictionContext(Parcel parcel) {
        this.mUiSurface = parcel.readString();
        this.mPredictedTargetCount = parcel.readInt();
        this.mPackageName = parcel.readString();
        this.mExtras = parcel.readBundle();
    }

    public String getUiSurface() {
        return this.mUiSurface;
    }

    public int getPredictedTargetCount() {
        return this.mPredictedTargetCount;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!getClass().equals(obj != null ? obj.getClass() : null)) {
            return false;
        }
        AppPredictionContext appPredictionContext = (AppPredictionContext) obj;
        return this.mPredictedTargetCount == appPredictionContext.mPredictedTargetCount && this.mUiSurface.equals(appPredictionContext.mUiSurface) && this.mPackageName.equals(appPredictionContext.mPackageName);
    }

    public int hashCode() {
        return (Objects.hash(this.mUiSurface, this.mPackageName) * 31) + this.mPredictedTargetCount;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mUiSurface);
        parcel.writeInt(this.mPredictedTargetCount);
        parcel.writeString(this.mPackageName);
        parcel.writeBundle(this.mExtras);
    }

    @SystemApi
    public static final class Builder {
        private Bundle mExtras;
        private final String mPackageName;
        private int mPredictedTargetCount;
        private String mUiSurface;

        @SystemApi
        public Builder(Context context) {
            this.mPackageName = context.getPackageName();
        }

        public Builder setPredictedTargetCount(int i) {
            this.mPredictedTargetCount = i;
            return this;
        }

        public Builder setUiSurface(String str) {
            this.mUiSurface = str;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public AppPredictionContext build() {
            return new AppPredictionContext(this.mUiSurface, this.mPredictedTargetCount, this.mPackageName, this.mExtras);
        }
    }
}
