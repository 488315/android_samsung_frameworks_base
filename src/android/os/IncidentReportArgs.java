package android.os;

import android.annotation.SystemApi;
import android.os.Parcelable;
import android.util.IntArray;
import java.util.ArrayList;

@SystemApi
/* loaded from: classes3.dex */
public final class IncidentReportArgs implements Parcelable {
    public static final Parcelable.Creator<IncidentReportArgs> CREATOR = new Parcelable.Creator<IncidentReportArgs>() { // from class: android.os.IncidentReportArgs.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IncidentReportArgs createFromParcel(Parcel parcel) {
            return new IncidentReportArgs(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IncidentReportArgs[] newArray(int i) {
            return new IncidentReportArgs[i];
        }
    };
    private boolean mAll;
    private final ArrayList<byte[]> mHeaders;
    private int mPrivacyPolicy;
    private String mReceiverCls;
    private String mReceiverPkg;
    private final IntArray mSections;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public IncidentReportArgs() {
        this.mSections = new IntArray();
        this.mHeaders = new ArrayList<>();
        this.mPrivacyPolicy = 200;
    }

    public IncidentReportArgs(Parcel parcel) {
        this.mSections = new IntArray();
        this.mHeaders = new ArrayList<>();
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mAll ? 1 : 0);
        int size = this.mSections.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            parcel.writeInt(this.mSections.get(i2));
        }
        int size2 = this.mHeaders.size();
        parcel.writeInt(size2);
        for (int i3 = 0; i3 < size2; i3++) {
            parcel.writeByteArray(this.mHeaders.get(i3));
        }
        parcel.writeInt(this.mPrivacyPolicy);
        parcel.writeString(this.mReceiverPkg);
        parcel.writeString(this.mReceiverCls);
    }

    public void readFromParcel(Parcel parcel) {
        this.mAll = parcel.readInt() != 0;
        this.mSections.clear();
        int i = parcel.readInt();
        for (int i2 = 0; i2 < i; i2++) {
            this.mSections.add(parcel.readInt());
        }
        this.mHeaders.clear();
        int i3 = parcel.readInt();
        for (int i4 = 0; i4 < i3; i4++) {
            this.mHeaders.add(parcel.createByteArray());
        }
        this.mPrivacyPolicy = parcel.readInt();
        this.mReceiverPkg = parcel.readString();
        this.mReceiverCls = parcel.readString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Incident(");
        if (this.mAll) {
            sb.append("all");
        } else {
            int size = this.mSections.size();
            if (size > 0) {
                sb.append(this.mSections.get(0));
            }
            for (int i = 1; i < size; i++) {
                sb.append(" ");
                sb.append(this.mSections.get(i));
            }
        }
        sb.append(", ");
        sb.append(this.mHeaders.size());
        sb.append(" headers), privacy: ");
        sb.append(this.mPrivacyPolicy);
        sb.append("receiver pkg: ");
        sb.append(this.mReceiverPkg);
        sb.append("receiver cls: ");
        sb.append(this.mReceiverCls);
        return sb.toString();
    }

    public void setAll(boolean z) {
        this.mAll = z;
        if (z) {
            this.mSections.clear();
        }
    }

    public void setPrivacyPolicy(int i) {
        if (i == 0 || i == 100 || i == 200) {
            this.mPrivacyPolicy = i;
        } else {
            this.mPrivacyPolicy = 200;
        }
    }

    public void addSection(int i) {
        if (this.mAll || i <= 1) {
            return;
        }
        this.mSections.add(i);
    }

    public boolean isAll() {
        return this.mAll;
    }

    public boolean containsSection(int i) {
        return this.mAll || this.mSections.indexOf(i) >= 0;
    }

    public int sectionCount() {
        return this.mSections.size();
    }

    public void addHeader(byte[] bArr) {
        this.mHeaders.add(bArr);
    }
}
