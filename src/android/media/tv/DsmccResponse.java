package android.media.tv;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public final class DsmccResponse extends BroadcastInfoResponse implements Parcelable {
    public static final String BIOP_MESSAGE_TYPE_DIRECTORY = "directory";
    public static final String BIOP_MESSAGE_TYPE_FILE = "file";
    public static final String BIOP_MESSAGE_TYPE_SERVICE_GATEWAY = "service_gateway";
    public static final String BIOP_MESSAGE_TYPE_STREAM = "stream";
    public static final Parcelable.Creator<DsmccResponse> CREATOR = new Parcelable.Creator<DsmccResponse>() { // from class: android.media.tv.DsmccResponse.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DsmccResponse createFromParcel(Parcel parcel) {
            parcel.readInt();
            return DsmccResponse.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DsmccResponse[] newArray(int i) {
            return new DsmccResponse[i];
        }
    };
    private static final int RESPONSE_TYPE = 6;
    private final String mBiopMessageType;
    private final List<String> mChildList;
    private final int[] mEventIds;
    private final String[] mEventNames;
    private final ParcelFileDescriptor mFileDescriptor;

    @Retention(RetentionPolicy.SOURCE)
    public @interface BiopMessageType {
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static DsmccResponse createFromParcelBody(Parcel parcel) {
        return new DsmccResponse(parcel);
    }

    public DsmccResponse(int i, int i2, int i3, ParcelFileDescriptor parcelFileDescriptor) {
        super(6, i, i2, i3);
        this.mBiopMessageType = "file";
        this.mFileDescriptor = parcelFileDescriptor;
        this.mChildList = null;
        this.mEventIds = null;
        this.mEventNames = null;
    }

    public DsmccResponse(int i, int i2, int i3, boolean z, List<String> list) {
        super(6, i, i2, i3);
        if (z) {
            this.mBiopMessageType = BIOP_MESSAGE_TYPE_SERVICE_GATEWAY;
        } else {
            this.mBiopMessageType = "directory";
        }
        this.mFileDescriptor = null;
        this.mChildList = list;
        this.mEventIds = null;
        this.mEventNames = null;
    }

    public DsmccResponse(int i, int i2, int i3, int[] iArr, String[] strArr) {
        super(6, i, i2, i3);
        this.mBiopMessageType = "stream";
        this.mFileDescriptor = null;
        this.mChildList = null;
        if ((iArr == null || strArr == null || iArr.length != strArr.length) && (iArr != null || strArr != null)) {
            throw new IllegalStateException("The size of eventIds and eventNames must be equal");
        }
        this.mEventIds = iArr;
        this.mEventNames = strArr;
    }

    private DsmccResponse(Parcel parcel) {
        super(6, parcel);
        int i;
        String readString = parcel.readString();
        this.mBiopMessageType = readString;
        readString.hashCode();
        i = 0;
        switch (readString) {
            case "directory":
            case "service_gateway":
                int readInt = parcel.readInt();
                if (readInt > 0) {
                    this.mChildList = new ArrayList();
                    while (i < readInt) {
                        this.mChildList.add(parcel.readString());
                        i++;
                    }
                } else {
                    this.mChildList = null;
                }
                this.mFileDescriptor = null;
                this.mEventIds = null;
                this.mEventNames = null;
                return;
            case "stream":
                int readInt2 = parcel.readInt();
                if (readInt2 > 0) {
                    this.mEventIds = new int[readInt2];
                    this.mEventNames = new String[readInt2];
                    while (i < readInt2) {
                        this.mEventIds[i] = parcel.readInt();
                        this.mEventNames[i] = parcel.readString();
                        i++;
                    }
                } else {
                    this.mEventIds = null;
                    this.mEventNames = null;
                }
                this.mChildList = null;
                this.mFileDescriptor = null;
                return;
            case "file":
                this.mFileDescriptor = parcel.readFileDescriptor();
                this.mChildList = null;
                this.mEventIds = null;
                this.mEventNames = null;
                return;
            default:
                throw new IllegalStateException("unexpected BIOP message type");
        }
    }

    public String getBiopMessageType() {
        return this.mBiopMessageType;
    }

    public ParcelFileDescriptor getFile() {
        if (!this.mBiopMessageType.equals("file")) {
            throw new IllegalStateException("Not file object");
        }
        return this.mFileDescriptor;
    }

    public List<String> getChildList() {
        if (this.mBiopMessageType.equals("directory") || this.mBiopMessageType.equals(BIOP_MESSAGE_TYPE_SERVICE_GATEWAY)) {
            return this.mChildList != null ? new ArrayList(this.mChildList) : new ArrayList();
        }
        throw new IllegalStateException("Not directory object");
    }

    public int[] getStreamEventIds() {
        if (!this.mBiopMessageType.equals("stream")) {
            throw new IllegalStateException("Not stream event object");
        }
        int[] iArr = this.mEventIds;
        return iArr != null ? iArr : new int[0];
    }

    public String[] getStreamEventNames() {
        if (!this.mBiopMessageType.equals("stream")) {
            throw new IllegalStateException("Not stream event object");
        }
        String[] strArr = this.mEventNames;
        return strArr != null ? strArr : new String[0];
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int i2;
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mBiopMessageType);
        String str = this.mBiopMessageType;
        str.hashCode();
        i2 = 0;
        switch (str) {
            case "directory":
            case "service_gateway":
                List<String> list = this.mChildList;
                if (list != null && list.size() > 0) {
                    parcel.writeInt(this.mChildList.size());
                    Iterator<String> it = this.mChildList.iterator();
                    while (it.hasNext()) {
                        parcel.writeString(it.next());
                    }
                    return;
                }
                parcel.writeInt(0);
                return;
            case "stream":
                int[] iArr = this.mEventIds;
                if (iArr != null && iArr.length > 0) {
                    parcel.writeInt(iArr.length);
                    while (true) {
                        int[] iArr2 = this.mEventIds;
                        if (i2 >= iArr2.length) {
                            return;
                        }
                        parcel.writeInt(iArr2[i2]);
                        parcel.writeString(this.mEventNames[i2]);
                        i2++;
                    }
                } else {
                    parcel.writeInt(0);
                    return;
                }
                break;
            case "file":
                parcel.writeFileDescriptor(this.mFileDescriptor.getFileDescriptor());
                return;
            default:
                throw new IllegalStateException("unexpected BIOP message type");
        }
    }
}
