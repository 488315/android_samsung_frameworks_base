package android.telephony.mbms;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes4.dex */
public final class FileServiceInfo extends ServiceInfo implements Parcelable {
    public static final Parcelable.Creator<FileServiceInfo> CREATOR = new Parcelable.Creator<FileServiceInfo>() { // from class: android.telephony.mbms.FileServiceInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FileServiceInfo createFromParcel(Parcel parcel) {
            return new FileServiceInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FileServiceInfo[] newArray(int i) {
            return new FileServiceInfo[i];
        }
    };
    private final List<FileInfo> files;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @SystemApi
    public FileServiceInfo(Map<Locale, String> map, String str, List<Locale> list, String str2, Date date, Date date2, List<FileInfo> list2) {
        super(map, str, list, str2, date, date2);
        this.files = new ArrayList(list2);
    }

    FileServiceInfo(Parcel parcel) {
        super(parcel);
        ArrayList arrayList = new ArrayList();
        this.files = arrayList;
        parcel.readList(arrayList, FileInfo.class.getClassLoader(), FileInfo.class);
    }

    @Override // android.telephony.mbms.ServiceInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeList(this.files);
    }

    public List<FileInfo> getFiles() {
        return this.files;
    }
}
