package android.media.tv;

import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class TableRequest extends BroadcastInfoRequest implements Parcelable {
    public static final Parcelable.Creator<TableRequest> CREATOR = new Parcelable.Creator<TableRequest>() { // from class: android.media.tv.TableRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TableRequest createFromParcel(Parcel parcel) {
            parcel.readInt();
            return TableRequest.createFromParcelBody(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TableRequest[] newArray(int i) {
            return new TableRequest[i];
        }
    };
    private static final int REQUEST_TYPE = 2;
    public static final int TABLE_NAME_BAT = 4;
    public static final int TABLE_NAME_CAT = 2;
    public static final int TABLE_NAME_EIT = 6;
    public static final int TABLE_NAME_NIT = 3;
    public static final int TABLE_NAME_PAT = 0;
    public static final int TABLE_NAME_PMT = 1;
    public static final int TABLE_NAME_SDT = 5;
    public static final int TABLE_NAME_SIT = 9;
    public static final int TABLE_NAME_TDT = 7;
    public static final int TABLE_NAME_TOT = 8;
    private final int mTableId;
    private final int mTableName;
    private final int mVersion;

    @Retention(RetentionPolicy.SOURCE)
    public @interface TableName {
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    static TableRequest createFromParcelBody(Parcel parcel) {
        return new TableRequest(parcel);
    }

    public TableRequest(int i, int i2, int i3, int i4, int i5) {
        super(2, i, i2);
        this.mTableId = i3;
        this.mTableName = i4;
        this.mVersion = i5;
    }

    TableRequest(Parcel parcel) {
        super(2, parcel);
        this.mTableId = parcel.readInt();
        this.mTableName = parcel.readInt();
        this.mVersion = parcel.readInt();
    }

    public int getTableId() {
        return this.mTableId;
    }

    public int getTableName() {
        return this.mTableName;
    }

    public int getVersion() {
        return this.mVersion;
    }

    @Override // android.media.tv.BroadcastInfoRequest, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mTableId);
        parcel.writeInt(this.mTableName);
        parcel.writeInt(this.mVersion);
    }
}
