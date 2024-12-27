package android.app.servertransaction;

import android.app.ActivityThread;
import android.app.ClientTransactionHandler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.SurfaceControl;
import android.window.SplashScreenView;

import java.util.Objects;

public class TransferSplashScreenViewStateItem extends ActivityTransactionItem {
    public static final Parcelable.Creator<TransferSplashScreenViewStateItem> CREATOR =
            new Parcelable.Creator<TransferSplashScreenViewStateItem>() { // from class:
                // android.app.servertransaction.TransferSplashScreenViewStateItem.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public TransferSplashScreenViewStateItem createFromParcel(Parcel in) {
                    return new TransferSplashScreenViewStateItem(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public TransferSplashScreenViewStateItem[] newArray(int size) {
                    return new TransferSplashScreenViewStateItem[size];
                }
            };
    private SplashScreenView.SplashScreenViewParcelable mSplashScreenViewParcelable;
    private SurfaceControl mStartingWindowLeash;

    @Override // android.app.servertransaction.ActivityTransactionItem
    public void execute(
            ClientTransactionHandler client,
            ActivityThread.ActivityClientRecord r,
            PendingTransactionActions pendingActions) {
        client.handleAttachSplashScreenView(
                r, this.mSplashScreenViewParcelable, this.mStartingWindowLeash);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem,
    // android.app.servertransaction.ObjectPoolItem
    public void recycle() {
        super.recycle();
        this.mSplashScreenViewParcelable = null;
        this.mStartingWindowLeash = null;
        ObjectPool.recycle(this);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedObject(this.mSplashScreenViewParcelable, flags);
        dest.writeTypedObject(this.mStartingWindowLeash, flags);
    }

    private TransferSplashScreenViewStateItem() {}

    private TransferSplashScreenViewStateItem(Parcel in) {
        super(in);
        this.mSplashScreenViewParcelable =
                (SplashScreenView.SplashScreenViewParcelable)
                        in.readTypedObject(SplashScreenView.SplashScreenViewParcelable.CREATOR);
        this.mStartingWindowLeash = (SurfaceControl) in.readTypedObject(SurfaceControl.CREATOR);
    }

    public static TransferSplashScreenViewStateItem obtain(
            IBinder activityToken,
            SplashScreenView.SplashScreenViewParcelable parcelable,
            SurfaceControl startingWindowLeash) {
        TransferSplashScreenViewStateItem instance =
                (TransferSplashScreenViewStateItem)
                        ObjectPool.obtain(TransferSplashScreenViewStateItem.class);
        if (instance == null) {
            instance = new TransferSplashScreenViewStateItem();
        }
        instance.setActivityToken(activityToken);
        instance.mSplashScreenViewParcelable = parcelable;
        instance.mStartingWindowLeash = startingWindowLeash;
        return instance;
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        TransferSplashScreenViewStateItem other = (TransferSplashScreenViewStateItem) o;
        return Objects.equals(this.mSplashScreenViewParcelable, other.mSplashScreenViewParcelable)
                && Objects.equals(this.mStartingWindowLeash, other.mStartingWindowLeash);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public int hashCode() {
        int result = (17 * 31) + super.hashCode();
        return (((result * 31) + Objects.hashCode(this.mSplashScreenViewParcelable)) * 31)
                + Objects.hashCode(this.mStartingWindowLeash);
    }

    @Override // android.app.servertransaction.ActivityTransactionItem
    public String toString() {
        return "TransferSplashScreenViewStateItem{"
                + super.toString()
                + ",splashScreenViewParcelable="
                + this.mSplashScreenViewParcelable
                + ",startingWindowLeash="
                + this.mStartingWindowLeash
                + "}";
    }
}
