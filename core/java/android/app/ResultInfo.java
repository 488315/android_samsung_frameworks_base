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
        public ResultInfo createFromParcel(Parcel in) {
            return new ResultInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ResultInfo[] newArray(int size) {
            return new ResultInfo[size];
        }
    };
    public final IBinder mCallerToken;
    public final Intent mData;
    public final int mRequestCode;
    public final int mResultCode;
    public final String mResultWho;

    public ResultInfo(String resultWho, int requestCode, int resultCode, Intent data) {
        this(resultWho, requestCode, resultCode, data, null);
    }

    public ResultInfo(String resultWho, int requestCode, int resultCode, Intent data, IBinder callerToken) {
        this.mResultWho = resultWho;
        this.mRequestCode = requestCode;
        this.mResultCode = resultCode;
        this.mData = data;
        this.mCallerToken = callerToken;
    }

    public String toString() {
        return "ResultInfo{who=" + this.mResultWho + ", request=" + this.mRequestCode + ", result=" + this.mResultCode + ", data=" + this.mData + "}";
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.mResultWho);
        out.writeInt(this.mRequestCode);
        out.writeInt(this.mResultCode);
        if (this.mData != null) {
            out.writeInt(1);
            this.mData.writeToParcel(out, 0);
        } else {
            out.writeInt(0);
        }
        out.writeStrongBinder(this.mCallerToken);
    }

    public ResultInfo(Parcel in) {
        this.mResultWho = in.readString();
        this.mRequestCode = in.readInt();
        this.mResultCode = in.readInt();
        if (in.readInt() != 0) {
            this.mData = Intent.CREATOR.createFromParcel(in);
        } else {
            this.mData = null;
        }
        this.mCallerToken = in.readStrongBinder();
    }

    public boolean equals(Object obj) {
        boolean intentsEqual;
        if (obj == null || !(obj instanceof ResultInfo)) {
            return false;
        }
        ResultInfo other = (ResultInfo) obj;
        if (this.mData == null) {
            intentsEqual = other.mData == null;
        } else {
            intentsEqual = this.mData.filterEquals(other.mData);
        }
        return intentsEqual && Objects.equals(this.mResultWho, other.mResultWho) && this.mResultCode == other.mResultCode && this.mRequestCode == other.mRequestCode && this.mCallerToken == other.mCallerToken;
    }

    public int hashCode() {
        int result = (((((17 * 31) + this.mRequestCode) * 31) + this.mResultCode) * 31) + Objects.hashCode(this.mResultWho);
        if (this.mData != null) {
            result = (result * 31) + this.mData.filterHashCode();
        }
        return (result * 31) + Objects.hashCode(this.mCallerToken);
    }
}
