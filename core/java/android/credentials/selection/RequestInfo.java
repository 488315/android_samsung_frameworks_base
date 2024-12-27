package android.credentials.selection;

import android.annotation.NonNull;
import android.annotation.SystemApi;
import android.credentials.CreateCredentialRequest;
import android.credentials.GetCredentialRequest;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

import com.android.internal.util.AnnotationValidations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

@SystemApi
public final class RequestInfo implements Parcelable {
    public static final Parcelable.Creator<RequestInfo> CREATOR =
            new Parcelable.Creator<
                    RequestInfo>() { // from class: android.credentials.selection.RequestInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public RequestInfo createFromParcel(Parcel in) {
                    return new RequestInfo(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public RequestInfo[] newArray(int size) {
                    return new RequestInfo[size];
                }
            };
    public static final String EXTRA_REQUEST_INFO =
            "android.credentials.selection.extra.REQUEST_INFO";
    public static final String TYPE_CREATE = "android.credentials.selection.TYPE_CREATE";
    public static final String TYPE_GET = "android.credentials.selection.TYPE_GET";
    public static final String TYPE_GET_VIA_REGISTRY =
            "android.credentials.selection.TYPE_GET_VIA_REGISTRY";
    public static final String TYPE_UNDEFINED = "android.credentials.selection.TYPE_UNDEFINED";
    private final CreateCredentialRequest mCreateCredentialRequest;
    private final List<String> mDefaultProviderIds;
    private final GetCredentialRequest mGetCredentialRequest;
    private final boolean mHasPermissionToOverrideDefault;
    private final boolean mIsShowAllOptionsRequested;
    private final String mPackageName;
    private final List<String> mRegistryProviderIds;
    private final IBinder mToken;
    private final String mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface RequestType {}

    public static RequestInfo newCreateRequestInfo(
            IBinder token,
            CreateCredentialRequest createCredentialRequest,
            String appPackageName,
            boolean hasPermissionToOverrideDefault,
            List<String> defaultProviderIds,
            boolean isShowAllOptionsRequested) {
        return new RequestInfo(
                token,
                TYPE_CREATE,
                appPackageName,
                createCredentialRequest,
                null,
                hasPermissionToOverrideDefault,
                defaultProviderIds,
                isShowAllOptionsRequested);
    }

    public static RequestInfo newGetRequestInfo(
            IBinder token,
            GetCredentialRequest getCredentialRequest,
            String appPackageName,
            boolean hasPermissionToOverrideDefault,
            boolean isShowAllOptionsRequested) {
        return new RequestInfo(
                token,
                TYPE_GET,
                appPackageName,
                null,
                getCredentialRequest,
                hasPermissionToOverrideDefault,
                new ArrayList(),
                isShowAllOptionsRequested);
    }

    public static RequestInfo newGetRequestInfo(
            IBinder token,
            GetCredentialRequest getCredentialRequest,
            String appPackageName,
            boolean hasPermissionToOverrideDefault,
            List<String> defaultProviderIds,
            boolean isShowAllOptionsRequested) {
        return new RequestInfo(
                token,
                TYPE_GET,
                appPackageName,
                null,
                getCredentialRequest,
                hasPermissionToOverrideDefault,
                defaultProviderIds,
                isShowAllOptionsRequested);
    }

    public boolean hasPermissionToOverrideDefault() {
        return this.mHasPermissionToOverrideDefault;
    }

    public IBinder getToken() {
        return this.mToken;
    }

    public String getType() {
        return this.mType;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public CreateCredentialRequest getCreateCredentialRequest() {
        return this.mCreateCredentialRequest;
    }

    public RequestToken getRequestToken() {
        return new RequestToken(this.mToken);
    }

    public List<String> getDefaultProviderIds() {
        return this.mDefaultProviderIds;
    }

    public List<String> getRegistryProviderIds() {
        return this.mRegistryProviderIds;
    }

    public GetCredentialRequest getGetCredentialRequest() {
        return this.mGetCredentialRequest;
    }

    public boolean isShowAllOptionsRequested() {
        return this.mIsShowAllOptionsRequested;
    }

    private RequestInfo(
            IBinder token,
            String type,
            String appPackageName,
            CreateCredentialRequest createCredentialRequest,
            GetCredentialRequest getCredentialRequest,
            boolean hasPermissionToOverrideDefault,
            List<String> defaultProviderIds,
            boolean isShowAllOptionsRequested) {
        this.mToken = token;
        this.mType = type;
        this.mPackageName = appPackageName;
        this.mCreateCredentialRequest = createCredentialRequest;
        this.mGetCredentialRequest = getCredentialRequest;
        this.mHasPermissionToOverrideDefault = hasPermissionToOverrideDefault;
        this.mDefaultProviderIds =
                defaultProviderIds == null ? new ArrayList<>() : defaultProviderIds;
        this.mRegistryProviderIds = new ArrayList();
        this.mIsShowAllOptionsRequested = isShowAllOptionsRequested;
    }

    private RequestInfo(Parcel in) {
        IBinder token = in.readStrongBinder();
        String type = in.readString8();
        String appPackageName = in.readString8();
        CreateCredentialRequest createCredentialRequest =
                (CreateCredentialRequest) in.readTypedObject(CreateCredentialRequest.CREATOR);
        GetCredentialRequest getCredentialRequest =
                (GetCredentialRequest) in.readTypedObject(GetCredentialRequest.CREATOR);
        this.mToken = token;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mToken);
        this.mType = type;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mType);
        this.mPackageName = appPackageName;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mPackageName);
        this.mCreateCredentialRequest = createCredentialRequest;
        this.mGetCredentialRequest = getCredentialRequest;
        this.mHasPermissionToOverrideDefault = in.readBoolean();
        this.mDefaultProviderIds = in.createStringArrayList();
        this.mRegistryProviderIds = in.createStringArrayList();
        this.mIsShowAllOptionsRequested = in.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStrongBinder(this.mToken);
        dest.writeString8(this.mType);
        dest.writeString8(this.mPackageName);
        dest.writeTypedObject(this.mCreateCredentialRequest, flags);
        dest.writeTypedObject(this.mGetCredentialRequest, flags);
        dest.writeBoolean(this.mHasPermissionToOverrideDefault);
        dest.writeStringList(this.mDefaultProviderIds);
        dest.writeStringList(this.mRegistryProviderIds);
        dest.writeBoolean(this.mIsShowAllOptionsRequested);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
