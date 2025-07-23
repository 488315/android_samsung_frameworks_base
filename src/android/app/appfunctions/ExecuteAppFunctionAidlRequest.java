package android.app.appfunctions;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import java.util.Objects;

/* loaded from: classes.dex */
public final class ExecuteAppFunctionAidlRequest implements Parcelable {
    public static final Parcelable.Creator<ExecuteAppFunctionAidlRequest> CREATOR = new Parcelable.Creator<ExecuteAppFunctionAidlRequest>() { // from class: android.app.appfunctions.ExecuteAppFunctionAidlRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ExecuteAppFunctionAidlRequest createFromParcel(Parcel parcel) {
            return new ExecuteAppFunctionAidlRequest(ExecuteAppFunctionRequest.CREATOR.createFromParcel(parcel), UserHandle.CREATOR.createFromParcel(parcel), parcel.readString8(), parcel.readLong());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ExecuteAppFunctionAidlRequest[] newArray(int i) {
            return new ExecuteAppFunctionAidlRequest[i];
        }
    };
    private final String mCallingPackage;
    private final ExecuteAppFunctionRequest mClientRequest;
    private final long mRequestTime;
    private final UserHandle mUserHandle;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ExecuteAppFunctionAidlRequest(ExecuteAppFunctionRequest executeAppFunctionRequest, UserHandle userHandle, String str, long j) {
        this.mClientRequest = (ExecuteAppFunctionRequest) Objects.requireNonNull(executeAppFunctionRequest);
        this.mUserHandle = (UserHandle) Objects.requireNonNull(userHandle);
        this.mCallingPackage = (String) Objects.requireNonNull(str);
        this.mRequestTime = j;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mClientRequest.writeToParcel(parcel, i);
        this.mUserHandle.writeToParcel(parcel, i);
        parcel.writeString8(this.mCallingPackage);
        parcel.writeLong(this.mRequestTime);
    }

    public ExecuteAppFunctionRequest getClientRequest() {
        return this.mClientRequest;
    }

    public UserHandle getUserHandle() {
        return this.mUserHandle;
    }

    public String getCallingPackage() {
        return this.mCallingPackage;
    }

    public long getRequestTime() {
        return this.mRequestTime;
    }
}
