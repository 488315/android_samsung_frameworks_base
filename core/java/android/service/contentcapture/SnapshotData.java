package android.service.contentcapture;

import android.annotation.SystemApi;
import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class SnapshotData implements Parcelable {
    public static final Parcelable.Creator<SnapshotData> CREATOR =
            new Parcelable.Creator<
                    SnapshotData>() { // from class: android.service.contentcapture.SnapshotData.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SnapshotData createFromParcel(Parcel parcel) {
                    return new SnapshotData(parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SnapshotData[] newArray(int size) {
                    return new SnapshotData[size];
                }
            };
    private final AssistContent mAssistContent;
    private final Bundle mAssistData;
    private final AssistStructure mAssistStructure;

    public SnapshotData(
            Bundle assistData, AssistStructure assistStructure, AssistContent assistContent) {
        this.mAssistData = assistData;
        this.mAssistStructure = assistStructure;
        this.mAssistContent = assistContent;
    }

    SnapshotData(Parcel parcel) {
        this.mAssistData = parcel.readBundle();
        this.mAssistStructure =
                (AssistStructure) parcel.readParcelable(null, AssistStructure.class);
        this.mAssistContent = (AssistContent) parcel.readParcelable(null, AssistContent.class);
    }

    public Bundle getAssistData() {
        return this.mAssistData;
    }

    public AssistStructure getAssistStructure() {
        return this.mAssistStructure;
    }

    public AssistContent getAssistContent() {
        return this.mAssistContent;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeBundle(this.mAssistData);
        parcel.writeParcelable(this.mAssistStructure, flags);
        parcel.writeParcelable(this.mAssistContent, flags);
    }
}
