package android.sec.enterprise.auditlog;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.logcat.SecurityLogEvent$$ExternalSyntheticTypeSwitch1;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/* loaded from: classes3.dex */
public class AuditLogParams implements Parcelable {
    public static final Parcelable.Creator<AuditLogParams> CREATOR = new Parcelable.Creator<AuditLogParams>() { // from class: android.sec.enterprise.auditlog.AuditLogParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuditLogParams createFromParcel(Parcel parcel) {
            return new AuditLogParams(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AuditLogParams[] newArray(int i) {
            return new AuditLogParams[i];
        }
    };
    private List<Object> mParams;

    @Retention(RetentionPolicy.SOURCE)
    @interface DataType {
        public static final int FLOAT = 2;
        public static final int INT = 0;
        public static final int LONG = 1;
        public static final int STRING = 3;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AuditLogParams(Object... objArr) {
        this.mParams = new ArrayList();
        if (objArr == null || objArr.length <= 0) {
            return;
        }
        this.mParams = new ArrayList(List.of(objArr));
    }

    public List<Object> getAsList() {
        return this.mParams;
    }

    public ArrayList<String> getAsStringArrayList() {
        return (ArrayList) this.mParams.stream().map(new Function() { // from class: android.sec.enterprise.auditlog.AuditLogParams$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return String.valueOf(obj);
            }
        }).collect(Collectors.toCollection(new Supplier() { // from class: android.sec.enterprise.auditlog.AuditLogParams$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return new ArrayList();
            }
        }));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mParams.size());
        for (Object obj : this.mParams) {
            Objects.requireNonNull(obj);
            int iM = SecurityLogEvent$$ExternalSyntheticTypeSwitch1.m(obj, 0, SecurityLogEvent$$ExternalSyntheticTypeSwitch1.getSwitchCases());
            if (iM == 0) {
                parcel.writeInt(0);
                parcel.writeInt(((Integer) obj).intValue());
            } else if (iM == 1) {
                parcel.writeInt(1);
                parcel.writeLong(((Long) obj).longValue());
            } else if (iM == 2) {
                parcel.writeInt(2);
                parcel.writeFloat(((Float) obj).floatValue());
            } else if (iM == 3) {
                parcel.writeInt(3);
                parcel.writeString((String) obj);
            }
        }
    }

    private void readFromParcel(Parcel parcel) {
        int i = parcel.readInt();
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = parcel.readInt();
            if (i3 == 0) {
                this.mParams.add(Integer.valueOf(parcel.readInt()));
            } else if (i3 == 1) {
                this.mParams.add(Long.valueOf(parcel.readLong()));
            } else if (i3 == 2) {
                this.mParams.add(Float.valueOf(parcel.readFloat()));
            } else if (i3 == 3) {
                this.mParams.add(parcel.readString());
            }
        }
    }

    private AuditLogParams(Parcel parcel) {
        this.mParams = new ArrayList();
        readFromParcel(parcel);
    }
}
