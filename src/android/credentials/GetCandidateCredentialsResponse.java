package android.credentials;

import android.annotation.NonNull;
import android.content.ComponentName;
import android.content.Intent;
import android.credentials.selection.GetCredentialProviderData;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class GetCandidateCredentialsResponse implements Parcelable {
    public static final Parcelable.Creator<GetCandidateCredentialsResponse> CREATOR = new Parcelable.Creator<GetCandidateCredentialsResponse>() { // from class: android.credentials.GetCandidateCredentialsResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetCandidateCredentialsResponse createFromParcel(Parcel parcel) {
            return new GetCandidateCredentialsResponse(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetCandidateCredentialsResponse[] newArray(int i) {
            return new GetCandidateCredentialsResponse[i];
        }
    };
    private final List<GetCredentialProviderData> mCandidateProviderDataList;
    private final Intent mIntent;
    private final ComponentName mPrimaryProviderComponentName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public GetCandidateCredentialsResponse(List<GetCredentialProviderData> list, Intent intent, ComponentName componentName) {
        Preconditions.checkCollectionNotEmpty(list, "candidateProviderDataList");
        this.mCandidateProviderDataList = new ArrayList(list);
        this.mIntent = intent;
        this.mPrimaryProviderComponentName = componentName;
    }

    public List<GetCredentialProviderData> getCandidateProviderDataList() {
        return this.mCandidateProviderDataList;
    }

    public ComponentName getPrimaryProviderComponentName() {
        return this.mPrimaryProviderComponentName;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    protected GetCandidateCredentialsResponse(Parcel parcel) {
        ArrayList arrayList = new ArrayList();
        parcel.readTypedList(arrayList, GetCredentialProviderData.CREATOR);
        this.mCandidateProviderDataList = arrayList;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) arrayList);
        this.mIntent = (Intent) parcel.readTypedObject(Intent.CREATOR);
        this.mPrimaryProviderComponentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(this.mCandidateProviderDataList);
        parcel.writeTypedObject(this.mIntent, i);
        parcel.writeTypedObject(this.mPrimaryProviderComponentName, i);
    }
}
