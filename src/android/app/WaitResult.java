package android.app;

import android.content.ComponentName;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public class WaitResult implements Parcelable {
    public static final Parcelable.Creator<WaitResult> CREATOR = new Parcelable.Creator<WaitResult>() { // from class: android.app.WaitResult.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WaitResult createFromParcel(Parcel parcel) {
            return new WaitResult(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WaitResult[] newArray(int i) {
            return new WaitResult[i];
        }
    };
    public static final int INVALID_DELAY = -1;
    public static final int LAUNCH_STATE_COLD = 1;
    public static final int LAUNCH_STATE_HOT = 3;
    public static final int LAUNCH_STATE_RELAUNCH = 4;
    public static final int LAUNCH_STATE_UNKNOWN = 0;
    public static final int LAUNCH_STATE_WARM = 2;
    public int launchState;
    public int result;
    public boolean timeout;
    public long totalTime;
    public ComponentName who;

    @Retention(RetentionPolicy.SOURCE)
    public @interface LaunchState {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public WaitResult() {
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.result);
        parcel.writeInt(this.timeout ? 1 : 0);
        ComponentName.writeToParcel(this.who, parcel);
        parcel.writeLong(this.totalTime);
        parcel.writeInt(this.launchState);
    }

    private WaitResult(Parcel parcel) {
        this.result = parcel.readInt();
        this.timeout = parcel.readInt() != 0;
        this.who = ComponentName.readFromParcel(parcel);
        this.totalTime = parcel.readLong();
        this.launchState = parcel.readInt();
    }

    public void dump(PrintWriter printWriter, String str) {
        printWriter.println(str + "WaitResult:");
        printWriter.println(str + "  result=" + this.result);
        printWriter.println(str + "  timeout=" + this.timeout);
        printWriter.println(str + "  who=" + this.who);
        printWriter.println(str + "  totalTime=" + this.totalTime);
        printWriter.println(str + "  launchState=" + this.launchState);
    }

    public static String launchStateToString(int i) {
        if (i == 1) {
            return "COLD";
        }
        if (i == 2) {
            return "WARM";
        }
        if (i == 3) {
            return "HOT";
        }
        if (i == 4) {
            return "RELAUNCH";
        }
        return "UNKNOWN (" + i + NavigationBarInflaterView.KEY_CODE_END;
    }
}
