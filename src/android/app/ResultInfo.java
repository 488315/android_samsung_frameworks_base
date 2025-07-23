package android.app;

import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public class ResultInfo implements Parcelable {
    public static final Parcelable.Creator<ResultInfo> CREATOR = new Parcelable.Creator<ResultInfo>() { // from class: android.app.ResultInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResultInfo createFromParcel(Parcel parcel) {
            return new ResultInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResultInfo[] newArray(int i) {
            return new ResultInfo[i];
        }
    };
    public final IBinder mCallerToken;
    public final Intent mData;
    public final int mRequestCode;
    public final int mResultCode;
    public final String mResultWho;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ResultInfo(String str, int i, int i2, Intent intent) {
        this(str, i, i2, intent, null);
    }

    public ResultInfo(String str, int i, int i2, Intent intent, IBinder iBinder) {
        this.mResultWho = str;
        this.mRequestCode = i;
        this.mResultCode = i2;
        this.mData = intent;
        this.mCallerToken = iBinder;
    }

    public String toString() {
        return "ResultInfo{who=" + this.mResultWho + ", request=" + this.mRequestCode + ", result=" + this.mResultCode + ", data=" + this.mData + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mResultWho);
        parcel.writeInt(this.mRequestCode);
        parcel.writeInt(this.mResultCode);
        if (this.mData != null) {
            parcel.writeInt(1);
            this.mData.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeStrongBinder(this.mCallerToken);
    }

    public ResultInfo(Parcel parcel) {
        this.mResultWho = parcel.readString();
        this.mRequestCode = parcel.readInt();
        this.mResultCode = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.mData = Intent.CREATOR.createFromParcel(parcel);
        } else {
            this.mData = null;
        }
        this.mCallerToken = parcel.readStrongBinder();
    }

    public boolean equals(Object obj) {
        boolean filterEquals;
        if (obj != null && (obj instanceof ResultInfo)) {
            ResultInfo resultInfo = (ResultInfo) obj;
            Intent intent = this.mData;
            if (intent == null) {
                filterEquals = resultInfo.mData == null;
            } else {
                filterEquals = intent.filterEquals(resultInfo.mData);
            }
            if (filterEquals && Objects.equals(this.mResultWho, resultInfo.mResultWho) && this.mResultCode == resultInfo.mResultCode && this.mRequestCode == resultInfo.mRequestCode && this.mCallerToken == resultInfo.mCallerToken) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int hashCode = ((((527 + this.mRequestCode) * 31) + this.mResultCode) * 31) + Objects.hashCode(this.mResultWho);
        Intent intent = this.mData;
        if (intent != null) {
            hashCode = (hashCode * 31) + intent.filterHashCode();
        }
        return (hashCode * 31) + Objects.hashCode(this.mCallerToken);
    }
}
