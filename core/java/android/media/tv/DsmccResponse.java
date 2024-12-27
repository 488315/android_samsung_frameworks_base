package android.media.tv;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
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
        public DsmccResponse createFromParcel(Parcel source) {
            source.readInt();
            return DsmccResponse.createFromParcelBody(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DsmccResponse[] newArray(int size) {
            return new DsmccResponse[size];
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

    static DsmccResponse createFromParcelBody(Parcel in) {
        return new DsmccResponse(in);
    }

    public DsmccResponse(int requestId, int sequence, int responseResult, ParcelFileDescriptor file) {
        super(6, requestId, sequence, responseResult);
        this.mBiopMessageType = "file";
        this.mFileDescriptor = file;
        this.mChildList = null;
        this.mEventIds = null;
        this.mEventNames = null;
    }

    public DsmccResponse(int requestId, int sequence, int responseResult, boolean isServiceGateway, List<String> childList) {
        super(6, requestId, sequence, responseResult);
        if (isServiceGateway) {
            this.mBiopMessageType = BIOP_MESSAGE_TYPE_SERVICE_GATEWAY;
        } else {
            this.mBiopMessageType = "directory";
        }
        this.mFileDescriptor = null;
        this.mChildList = childList;
        this.mEventIds = null;
        this.mEventNames = null;
    }

    public DsmccResponse(int requestId, int sequence, int responseResult, int[] eventIds, String[] eventNames) {
        super(6, requestId, sequence, responseResult);
        this.mBiopMessageType = "stream";
        this.mFileDescriptor = null;
        this.mChildList = null;
        if ((eventIds == null || eventNames == null || eventIds.length != eventNames.length) && (eventIds != null || eventNames != null)) {
            throw new IllegalStateException("The size of eventIds and eventNames must be equal");
        }
        this.mEventIds = eventIds;
        this.mEventNames = eventNames;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private DsmccResponse(Parcel source) {
        super(6, source);
        char c;
        this.mBiopMessageType = source.readString();
        String str = this.mBiopMessageType;
        switch (str.hashCode()) {
            case -962584979:
                if (str.equals("directory")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -959828294:
                if (str.equals(BIOP_MESSAGE_TYPE_SERVICE_GATEWAY)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -891990144:
                if (str.equals("stream")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 3143036:
                if (str.equals("file")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
                int childNum = source.readInt();
                if (childNum > 0) {
                    this.mChildList = new ArrayList();
                    for (int i = 0; i < childNum; i++) {
                        this.mChildList.add(source.readString());
                    }
                } else {
                    this.mChildList = null;
                }
                this.mFileDescriptor = null;
                this.mEventIds = null;
                this.mEventNames = null;
                return;
            case 2:
                this.mFileDescriptor = source.readFileDescriptor();
                this.mChildList = null;
                this.mEventIds = null;
                this.mEventNames = null;
                return;
            case 3:
                int eventNum = source.readInt();
                if (eventNum > 0) {
                    this.mEventIds = new int[eventNum];
                    this.mEventNames = new String[eventNum];
                    for (int i2 = 0; i2 < eventNum; i2++) {
                        this.mEventIds[i2] = source.readInt();
                        this.mEventNames[i2] = source.readString();
                    }
                } else {
                    this.mEventIds = null;
                    this.mEventNames = null;
                }
                this.mChildList = null;
                this.mFileDescriptor = null;
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
        if (this.mBiopMessageType.equals("stream")) {
            return this.mEventIds != null ? this.mEventIds : new int[0];
        }
        throw new IllegalStateException("Not stream event object");
    }

    public String[] getStreamEventNames() {
        if (this.mBiopMessageType.equals("stream")) {
            return this.mEventNames != null ? this.mEventNames : new String[0];
        }
        throw new IllegalStateException("Not stream event object");
    }

    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.media.tv.BroadcastInfoResponse, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        char c;
        super.writeToParcel(dest, flags);
        dest.writeString(this.mBiopMessageType);
        String str = this.mBiopMessageType;
        switch (str.hashCode()) {
            case -962584979:
                if (str.equals("directory")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -959828294:
                if (str.equals(BIOP_MESSAGE_TYPE_SERVICE_GATEWAY)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -891990144:
                if (str.equals("stream")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 3143036:
                if (str.equals("file")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
                if (this.mChildList != null && this.mChildList.size() > 0) {
                    dest.writeInt(this.mChildList.size());
                    for (String child : this.mChildList) {
                        dest.writeString(child);
                    }
                    return;
                }
                dest.writeInt(0);
                return;
            case 2:
                dest.writeFileDescriptor(this.mFileDescriptor.getFileDescriptor());
                return;
            case 3:
                if (this.mEventIds != null && this.mEventIds.length > 0) {
                    dest.writeInt(this.mEventIds.length);
                    for (int i = 0; i < this.mEventIds.length; i++) {
                        dest.writeInt(this.mEventIds[i]);
                        dest.writeString(this.mEventNames[i]);
                    }
                    return;
                }
                dest.writeInt(0);
                return;
            default:
                throw new IllegalStateException("unexpected BIOP message type");
        }
    }
}
