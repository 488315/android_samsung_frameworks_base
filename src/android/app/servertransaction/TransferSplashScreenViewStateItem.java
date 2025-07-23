package android.app.servertransaction;

import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.SurfaceControl;
import android.window.SplashScreenView;
import java.util.Objects;

/* loaded from: classes.dex */
public class TransferSplashScreenViewStateItem extends ActivityTransactionItem {
    public static final Parcelable.Creator<TransferSplashScreenViewStateItem> CREATOR = new Parcelable.Creator<TransferSplashScreenViewStateItem>() { // from class: android.app.servertransaction.TransferSplashScreenViewStateItem.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TransferSplashScreenViewStateItem createFromParcel(Parcel parcel) {
            return new TransferSplashScreenViewStateItem(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TransferSplashScreenViewStateItem[] newArray(int i) {
            return new TransferSplashScreenViewStateItem[i];
        }
    };
    private final SplashScreenView.SplashScreenViewParcelable mSplashScreenViewParcelable;
    private final SurfaceControl mStartingWindowLeash;

    public TransferSplashScreenViewStateItem(IBinder iBinder, SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable, SurfaceControl surfaceControl) {
        super(iBinder);
        this.mSplashScreenViewParcelable = splashScreenViewParcelable;
        this.mStartingWindowLeash = surfaceControl;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(ClientTransactionHandler clientTransactionHandler, ActivityThread.ActivityClientRecord activityClientRecord, PendingTransactionActions pendingTransactionActions) {
        clientTransactionHandler.handleAttachSplashScreenView(activityClientRecord, this.mSplashScreenViewParcelable, this.mStartingWindowLeash);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedObject(this.mSplashScreenViewParcelable, i);
        parcel.writeTypedObject(this.mStartingWindowLeash, i);
    }

    private TransferSplashScreenViewStateItem(Parcel parcel) {
        super(parcel);
        this.mSplashScreenViewParcelable = (SplashScreenView.SplashScreenViewParcelable) parcel.readTypedObject(SplashScreenView.SplashScreenViewParcelable.CREATOR);
        this.mStartingWindowLeash = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        TransferSplashScreenViewStateItem transferSplashScreenViewStateItem = (TransferSplashScreenViewStateItem) obj;
        return Objects.equals(this.mSplashScreenViewParcelable, transferSplashScreenViewStateItem.mSplashScreenViewParcelable) && Objects.equals(this.mStartingWindowLeash, transferSplashScreenViewStateItem.mStartingWindowLeash);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        return ((((527 + super.hashCode()) * 31) + Objects.hashCode(this.mSplashScreenViewParcelable)) * 31) + Objects.hashCode(this.mStartingWindowLeash);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "TransferSplashScreenViewStateItem{" + super.toString() + ",splashScreenViewParcelable=" + this.mSplashScreenViewParcelable + ",startingWindowLeash=" + this.mStartingWindowLeash + "}";
    }
}
