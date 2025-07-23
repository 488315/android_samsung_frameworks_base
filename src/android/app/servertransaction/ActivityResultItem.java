package android.app.servertransaction;

import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.app.ResultInfo;
import android.app.compat.CompatChanges;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Trace;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public class ActivityResultItem extends ActivityTransactionItem {
    public static final long CALL_ACTIVITY_RESULT_BEFORE_RESUME = 78294732;
    public static final Parcelable.Creator<ActivityResultItem> CREATOR = new Parcelable.Creator<ActivityResultItem>() { // from class: android.app.servertransaction.ActivityResultItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivityResultItem createFromParcel(Parcel parcel) {
            return new ActivityResultItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ActivityResultItem[] newArray(int i) {
            return new ActivityResultItem[i];
        }
    };
    private List<ResultInfo> mResultInfoList;

    public ActivityResultItem(IBinder iBinder, List<ResultInfo> list) {
        super(iBinder);
        this.mResultInfoList = new ArrayList(list);
    }

    @Override // android.app.servertransaction.ClientTransactionItem
    public int getPostExecutionState() {
        return CompatChanges.isChangeEnabled(CALL_ACTIVITY_RESULT_BEFORE_RESUME) ? 3 : -1;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(ClientTransactionHandler clientTransactionHandler, ActivityThread.ActivityClientRecord activityClientRecord, PendingTransactionActions pendingTransactionActions) {
        Trace.traceBegin(64L, "activityDeliverResult");
        clientTransactionHandler.handleSendResult(activityClientRecord, this.mResultInfoList, "ACTIVITY_RESULT");
        Trace.traceEnd(64L);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedList(this.mResultInfoList, i);
    }

    private ActivityResultItem(Parcel parcel) {
        super(parcel);
        this.mResultInfoList = parcel.createTypedArrayList(ResultInfo.CREATOR);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (super.equals(obj)) {
            return Objects.equals(this.mResultInfoList, ((ActivityResultItem) obj).mResultInfoList);
        }
        return false;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return ((527 + super.hashCode()) * 31) + Objects.hashCode(this.mResultInfoList);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "ActivityResultItem{" + super.toString() + ",resultInfoList=" + this.mResultInfoList + "}";
    }
}
