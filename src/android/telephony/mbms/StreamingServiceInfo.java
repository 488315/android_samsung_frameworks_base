package android.telephony.mbms;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes4.dex */
public final class StreamingServiceInfo extends ServiceInfo implements Parcelable {
    public static final Parcelable.Creator<StreamingServiceInfo> CREATOR = new Parcelable.Creator<StreamingServiceInfo>() { // from class: android.telephony.mbms.StreamingServiceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StreamingServiceInfo createFromParcel(Parcel parcel) {
            return new StreamingServiceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StreamingServiceInfo[] newArray(int i) {
            return new StreamingServiceInfo[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @SystemApi
    public StreamingServiceInfo(Map<Locale, String> map, String str, List<Locale> list, String str2, Date date, Date date2) {
        super(map, str, list, str2, date, date2);
    }

    private StreamingServiceInfo(Parcel parcel) {
        super(parcel);
    }

    @Override // android.telephony.mbms.ServiceInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }
}
