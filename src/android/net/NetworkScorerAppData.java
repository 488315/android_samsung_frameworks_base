package android.net;

import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class NetworkScorerAppData implements Parcelable {
    public static final Parcelable.Creator<NetworkScorerAppData> CREATOR = new Parcelable.Creator<NetworkScorerAppData>() { // from class: android.net.NetworkScorerAppData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkScorerAppData createFromParcel(Parcel parcel) {
            return new NetworkScorerAppData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NetworkScorerAppData[] newArray(int i) {
            return new NetworkScorerAppData[i];
        }
    };
    private final ComponentName mEnableUseOpenWifiActivity;
    private final String mNetworkAvailableNotificationChannelId;
    private final ComponentName mRecommendationService;
    private final String mRecommendationServiceLabel;
    public final int packageUid;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public NetworkScorerAppData(int i, ComponentName componentName, String str, ComponentName componentName2, String str2) {
        this.packageUid = i;
        this.mRecommendationService = componentName;
        this.mRecommendationServiceLabel = str;
        this.mEnableUseOpenWifiActivity = componentName2;
        this.mNetworkAvailableNotificationChannelId = str2;
    }

    protected NetworkScorerAppData(Parcel parcel) {
        this.packageUid = parcel.readInt();
        this.mRecommendationService = ComponentName.readFromParcel(parcel);
        this.mRecommendationServiceLabel = parcel.readString();
        this.mEnableUseOpenWifiActivity = ComponentName.readFromParcel(parcel);
        this.mNetworkAvailableNotificationChannelId = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.packageUid);
        ComponentName.writeToParcel(this.mRecommendationService, parcel);
        parcel.writeString(this.mRecommendationServiceLabel);
        ComponentName.writeToParcel(this.mEnableUseOpenWifiActivity, parcel);
        parcel.writeString(this.mNetworkAvailableNotificationChannelId);
    }

    public String getRecommendationServicePackageName() {
        return this.mRecommendationService.getPackageName();
    }

    public ComponentName getRecommendationServiceComponent() {
        return this.mRecommendationService;
    }

    public ComponentName getEnableUseOpenWifiActivity() {
        return this.mEnableUseOpenWifiActivity;
    }

    public String getRecommendationServiceLabel() {
        return this.mRecommendationServiceLabel;
    }

    public String getNetworkAvailableNotificationChannelId() {
        return this.mNetworkAvailableNotificationChannelId;
    }

    public String toString() {
        return "NetworkScorerAppData{packageUid=" + this.packageUid + ", mRecommendationService=" + this.mRecommendationService + ", mRecommendationServiceLabel=" + this.mRecommendationServiceLabel + ", mEnableUseOpenWifiActivity=" + this.mEnableUseOpenWifiActivity + ", mNetworkAvailableNotificationChannelId=" + this.mNetworkAvailableNotificationChannelId + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            NetworkScorerAppData networkScorerAppData = (NetworkScorerAppData) obj;
            if (this.packageUid == networkScorerAppData.packageUid && Objects.equals(this.mRecommendationService, networkScorerAppData.mRecommendationService) && Objects.equals(this.mRecommendationServiceLabel, networkScorerAppData.mRecommendationServiceLabel) && Objects.equals(this.mEnableUseOpenWifiActivity, networkScorerAppData.mEnableUseOpenWifiActivity) && Objects.equals(this.mNetworkAvailableNotificationChannelId, networkScorerAppData.mNetworkAvailableNotificationChannelId)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.packageUid), this.mRecommendationService, this.mRecommendationServiceLabel, this.mEnableUseOpenWifiActivity, this.mNetworkAvailableNotificationChannelId);
    }
}
