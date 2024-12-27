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
        public GetCandidateCredentialsResponse createFromParcel(Parcel in) {
            return new GetCandidateCredentialsResponse(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public GetCandidateCredentialsResponse[] newArray(int size) {
            return new GetCandidateCredentialsResponse[size];
        }
    };
    private final List<GetCredentialProviderData> mCandidateProviderDataList;
    private final Intent mIntent;
    private final ComponentName mPrimaryProviderComponentName;

    public GetCandidateCredentialsResponse(List<GetCredentialProviderData> candidateProviderDataList, Intent intent, ComponentName primaryProviderComponentName) {
        Preconditions.checkCollectionNotEmpty(candidateProviderDataList, "candidateProviderDataList");
        this.mCandidateProviderDataList = new ArrayList(candidateProviderDataList);
        this.mIntent = intent;
        this.mPrimaryProviderComponentName = primaryProviderComponentName;
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

    protected GetCandidateCredentialsResponse(Parcel in) {
        ArrayList arrayList = new ArrayList();
        in.readTypedList(arrayList, GetCredentialProviderData.CREATOR);
        this.mCandidateProviderDataList = arrayList;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mCandidateProviderDataList);
        this.mIntent = (Intent) in.readTypedObject(Intent.CREATOR);
        this.mPrimaryProviderComponentName = (ComponentName) in.readTypedObject(ComponentName.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.mCandidateProviderDataList);
        dest.writeTypedObject(this.mIntent, flags);
        dest.writeTypedObject(this.mPrimaryProviderComponentName, flags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
