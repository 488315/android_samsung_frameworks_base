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
/* loaded from: classes.dex */
public final class RequestInfo implements Parcelable {
    public static final Parcelable.Creator<RequestInfo> CREATOR = new Parcelable.Creator<RequestInfo>() { // from class: android.credentials.selection.RequestInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RequestInfo createFromParcel(Parcel parcel) {
            return new RequestInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RequestInfo[] newArray(int i) {
            return new RequestInfo[i];
        }
    };
    public static final String EXTRA_REQUEST_INFO = "android.credentials.selection.extra.REQUEST_INFO";
    public static final String TYPE_CREATE = "android.credentials.selection.TYPE_CREATE";
    public static final String TYPE_GET = "android.credentials.selection.TYPE_GET";
    public static final String TYPE_GET_VIA_REGISTRY = "android.credentials.selection.TYPE_GET_VIA_REGISTRY";
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
    public @interface RequestType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static RequestInfo newCreateRequestInfo(IBinder iBinder, CreateCredentialRequest createCredentialRequest, String str, boolean z, List<String> list, boolean z2) {
        return new RequestInfo(iBinder, TYPE_CREATE, str, createCredentialRequest, null, z, list, z2);
    }

    public static RequestInfo newGetRequestInfo(IBinder iBinder, GetCredentialRequest getCredentialRequest, String str, boolean z, boolean z2) {
        return new RequestInfo(iBinder, TYPE_GET, str, null, getCredentialRequest, z, new ArrayList(), z2);
    }

    public static RequestInfo newGetRequestInfo(IBinder iBinder, GetCredentialRequest getCredentialRequest, String str, boolean z, List<String> list, boolean z2) {
        return new RequestInfo(iBinder, TYPE_GET, str, null, getCredentialRequest, z, list, z2);
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

    private RequestInfo(IBinder iBinder, String str, String str2, CreateCredentialRequest createCredentialRequest, GetCredentialRequest getCredentialRequest, boolean z, List<String> list, boolean z2) {
        this.mToken = iBinder;
        this.mType = str;
        this.mPackageName = str2;
        this.mCreateCredentialRequest = createCredentialRequest;
        this.mGetCredentialRequest = getCredentialRequest;
        this.mHasPermissionToOverrideDefault = z;
        this.mDefaultProviderIds = list == null ? new ArrayList<>() : list;
        this.mRegistryProviderIds = new ArrayList();
        this.mIsShowAllOptionsRequested = z2;
    }

    private RequestInfo(Parcel parcel) {
        IBinder strongBinder = parcel.readStrongBinder();
        String string8 = parcel.readString8();
        String string82 = parcel.readString8();
        CreateCredentialRequest createCredentialRequest = (CreateCredentialRequest) parcel.readTypedObject(CreateCredentialRequest.CREATOR);
        GetCredentialRequest getCredentialRequest = (GetCredentialRequest) parcel.readTypedObject(GetCredentialRequest.CREATOR);
        this.mToken = strongBinder;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) strongBinder);
        this.mType = string8;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string8);
        this.mPackageName = string82;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string82);
        this.mCreateCredentialRequest = createCredentialRequest;
        this.mGetCredentialRequest = getCredentialRequest;
        this.mHasPermissionToOverrideDefault = parcel.readBoolean();
        this.mDefaultProviderIds = parcel.createStringArrayList();
        this.mRegistryProviderIds = parcel.createStringArrayList();
        this.mIsShowAllOptionsRequested = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.mToken);
        parcel.writeString8(this.mType);
        parcel.writeString8(this.mPackageName);
        parcel.writeTypedObject(this.mCreateCredentialRequest, i);
        parcel.writeTypedObject(this.mGetCredentialRequest, i);
        parcel.writeBoolean(this.mHasPermissionToOverrideDefault);
        parcel.writeStringList(this.mDefaultProviderIds);
        parcel.writeStringList(this.mRegistryProviderIds);
        parcel.writeBoolean(this.mIsShowAllOptionsRequested);
    }
}
