package android.app.appfunctions;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;

import java.util.Objects;

/* loaded from: classes.dex */
public final class ExecuteAppFunctionAidlRequest implements Parcelable {
    public static final Parcelable.Creator<ExecuteAppFunctionAidlRequest> CREATOR =
            new Parcelable.Creator<ExecuteAppFunctionAidlRequest>() { // from class:
                // android.app.appfunctions.ExecuteAppFunctionAidlRequest.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ExecuteAppFunctionAidlRequest createFromParcel(Parcel in) {
                    ExecuteAppFunctionRequest clientRequest =
                            ExecuteAppFunctionRequest.CREATOR.createFromParcel(in);
                    UserHandle userHandle = UserHandle.CREATOR.createFromParcel(in);
                    String callingPackage = in.readString8();
                    return new ExecuteAppFunctionAidlRequest(
                            clientRequest, userHandle, callingPackage);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ExecuteAppFunctionAidlRequest[] newArray(int size) {
                    return new ExecuteAppFunctionAidlRequest[size];
                }
            };
    private final String mCallingPackage;
    private final ExecuteAppFunctionRequest mClientRequest;
    private final UserHandle mUserHandle;

    public ExecuteAppFunctionAidlRequest(
            ExecuteAppFunctionRequest clientRequest, UserHandle userHandle, String callingPackage) {
        this.mClientRequest = (ExecuteAppFunctionRequest) Objects.requireNonNull(clientRequest);
        this.mUserHandle = (UserHandle) Objects.requireNonNull(userHandle);
        this.mCallingPackage = (String) Objects.requireNonNull(callingPackage);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        this.mClientRequest.writeToParcel(dest, flags);
        this.mUserHandle.writeToParcel(dest, flags);
        dest.writeString8(this.mCallingPackage);
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
}
