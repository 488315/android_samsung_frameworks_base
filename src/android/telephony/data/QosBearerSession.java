package android.telephony.data;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class QosBearerSession implements Parcelable {
    public static final Parcelable.Creator<QosBearerSession> CREATOR = new Parcelable.Creator<QosBearerSession>() { // from class: android.telephony.data.QosBearerSession.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public QosBearerSession createFromParcel(Parcel parcel) {
            return new QosBearerSession(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public QosBearerSession[] newArray(int i) {
            return new QosBearerSession[i];
        }
    };
    final Qos qos;
    final List<QosBearerFilter> qosBearerFilterList;
    final int qosBearerSessionId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public QosBearerSession(int i, Qos qos, List<QosBearerFilter> list) {
        this.qosBearerSessionId = i;
        this.qos = qos;
        ArrayList arrayList = new ArrayList();
        this.qosBearerFilterList = arrayList;
        arrayList.addAll(list);
    }

    private QosBearerSession(Parcel parcel) {
        this.qosBearerSessionId = parcel.readInt();
        this.qos = (Qos) parcel.readParcelable(Qos.class.getClassLoader(), Qos.class);
        ArrayList arrayList = new ArrayList();
        this.qosBearerFilterList = arrayList;
        parcel.readList(arrayList, QosBearerFilter.class.getClassLoader(), QosBearerFilter.class);
    }

    public int getQosBearerSessionId() {
        return this.qosBearerSessionId;
    }

    public Qos getQos() {
        return this.qos;
    }

    public List<QosBearerFilter> getQosBearerFilterList() {
        return this.qosBearerFilterList;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.qosBearerSessionId);
        if (this.qos.getType() == 1) {
            parcel.writeParcelable((EpsQos) this.qos, i);
        } else {
            parcel.writeParcelable((NrQos) this.qos, i);
        }
        parcel.writeList(this.qosBearerFilterList);
    }

    public String toString() {
        return "QosBearerSession { qosBearerSessionId=" + this.qosBearerSessionId + " qos=" + this.qos + " qosBearerFilterList=" + this.qosBearerFilterList + "}";
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.qosBearerSessionId), this.qos, this.qosBearerFilterList);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof QosBearerSession)) {
            QosBearerSession qosBearerSession = (QosBearerSession) obj;
            if (this.qosBearerSessionId == qosBearerSession.qosBearerSessionId && Objects.equals(this.qos, qosBearerSession.qos) && this.qosBearerFilterList.size() == qosBearerSession.qosBearerFilterList.size() && this.qosBearerFilterList.containsAll(qosBearerSession.qosBearerFilterList)) {
                return true;
            }
        }
        return false;
    }
}
