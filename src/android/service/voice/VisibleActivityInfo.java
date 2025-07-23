package android.service.voice;

import android.annotation.NonNull;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.voice.VoiceInteractionSession;
import com.android.internal.util.AnnotationValidations;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class VisibleActivityInfo implements Parcelable {
    public static final Parcelable.Creator<VisibleActivityInfo> CREATOR = new Parcelable.Creator<VisibleActivityInfo>() { // from class: android.service.voice.VisibleActivityInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VisibleActivityInfo[] newArray(int i) {
            return new VisibleActivityInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VisibleActivityInfo createFromParcel(Parcel parcel) {
            return new VisibleActivityInfo(parcel);
        }
    };
    public static final int TYPE_ACTIVITY_ADDED = 1;
    public static final int TYPE_ACTIVITY_REMOVED = 2;
    private final IBinder mAssistToken;
    private final int mTaskId;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public VisibleActivityInfo(int i, IBinder iBinder) {
        Objects.requireNonNull(iBinder);
        this.mTaskId = i;
        this.mAssistToken = iBinder;
    }

    public VoiceInteractionSession.ActivityId getActivityId() {
        return new VoiceInteractionSession.ActivityId(this.mTaskId, this.mAssistToken);
    }

    public String toString() {
        return "VisibleActivityInfo { taskId = " + this.mTaskId + ", assistToken = " + this.mAssistToken + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            VisibleActivityInfo visibleActivityInfo = (VisibleActivityInfo) obj;
            if (this.mTaskId == visibleActivityInfo.mTaskId && Objects.equals(this.mAssistToken, visibleActivityInfo.mAssistToken)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((this.mTaskId + 31) * 31) + Objects.hashCode(this.mAssistToken);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mTaskId);
        parcel.writeStrongBinder(this.mAssistToken);
    }

    VisibleActivityInfo(Parcel parcel) {
        int readInt = parcel.readInt();
        IBinder readStrongBinder = parcel.readStrongBinder();
        this.mTaskId = readInt;
        this.mAssistToken = readStrongBinder;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) readStrongBinder);
    }
}
