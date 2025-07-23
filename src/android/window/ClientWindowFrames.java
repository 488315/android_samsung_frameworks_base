package android.window;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SequenceUtils;
import java.util.Objects;

/* loaded from: classes5.dex */
public class ClientWindowFrames implements Parcelable {
    public static final Parcelable.Creator<ClientWindowFrames> CREATOR = new Parcelable.Creator<ClientWindowFrames>() { // from class: android.window.ClientWindowFrames.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ClientWindowFrames createFromParcel(Parcel parcel) {
            return new ClientWindowFrames(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ClientWindowFrames[] newArray(int i) {
            return new ClientWindowFrames[i];
        }
    };
    public Rect attachedFrame;
    public float compatScale;
    public final Rect displayFrame;
    public final Rect frame;
    public boolean isParentFrameClippedByDisplayCutout;
    public final Rect parentFrame;
    public int seq;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ClientWindowFrames() {
        this.frame = new Rect();
        this.displayFrame = new Rect();
        this.parentFrame = new Rect();
        this.compatScale = 1.0f;
        this.seq = SequenceUtils.getInitSeq();
    }

    public ClientWindowFrames(ClientWindowFrames clientWindowFrames) {
        this.frame = new Rect();
        this.displayFrame = new Rect();
        this.parentFrame = new Rect();
        this.compatScale = 1.0f;
        this.seq = SequenceUtils.getInitSeq();
        setTo(clientWindowFrames);
    }

    private ClientWindowFrames(Parcel parcel) {
        this.frame = new Rect();
        this.displayFrame = new Rect();
        this.parentFrame = new Rect();
        this.compatScale = 1.0f;
        this.seq = SequenceUtils.getInitSeq();
        readFromParcel(parcel);
    }

    public void setTo(ClientWindowFrames clientWindowFrames) {
        this.frame.set(clientWindowFrames.frame);
        this.displayFrame.set(clientWindowFrames.displayFrame);
        this.parentFrame.set(clientWindowFrames.parentFrame);
        if (clientWindowFrames.attachedFrame != null) {
            this.attachedFrame = new Rect(clientWindowFrames.attachedFrame);
        }
        this.isParentFrameClippedByDisplayCutout = clientWindowFrames.isParentFrameClippedByDisplayCutout;
        this.compatScale = clientWindowFrames.compatScale;
        this.seq = clientWindowFrames.seq;
    }

    public void readFromParcel(Parcel parcel) {
        this.frame.readFromParcel(parcel);
        this.displayFrame.readFromParcel(parcel);
        this.parentFrame.readFromParcel(parcel);
        this.attachedFrame = (Rect) parcel.readTypedObject(Rect.CREATOR);
        this.isParentFrameClippedByDisplayCutout = parcel.readBoolean();
        this.compatScale = parcel.readFloat();
        this.seq = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.frame.writeToParcel(parcel, i);
        this.displayFrame.writeToParcel(parcel, i);
        this.parentFrame.writeToParcel(parcel, i);
        parcel.writeTypedObject(this.attachedFrame, i);
        parcel.writeBoolean(this.isParentFrameClippedByDisplayCutout);
        parcel.writeFloat(this.compatScale);
        parcel.writeInt(this.seq);
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder(32);
        StringBuilder sb2 = new StringBuilder("ClientWindowFrames{frame=");
        sb2.append(this.frame.toShortString(sb));
        sb2.append(" display=");
        sb2.append(this.displayFrame.toShortString(sb));
        sb2.append(" parentFrame=");
        sb2.append(this.parentFrame.toShortString(sb));
        String str2 = "";
        if (this.attachedFrame != null) {
            str = " attachedFrame=" + this.attachedFrame.toShortString();
        } else {
            str = "";
        }
        sb2.append(str);
        sb2.append(this.isParentFrameClippedByDisplayCutout ? " parentClippedByDisplayCutout" : "");
        if (this.compatScale != 1.0f) {
            str2 = " sizeCompatScale=" + this.compatScale;
        }
        sb2.append(str2);
        sb2.append("}");
        return sb2.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            ClientWindowFrames clientWindowFrames = (ClientWindowFrames) obj;
            if (this.frame.equals(clientWindowFrames.frame) && this.displayFrame.equals(clientWindowFrames.displayFrame) && this.parentFrame.equals(clientWindowFrames.parentFrame) && Objects.equals(this.attachedFrame, clientWindowFrames.attachedFrame) && this.isParentFrameClippedByDisplayCutout == clientWindowFrames.isParentFrameClippedByDisplayCutout && this.compatScale == clientWindowFrames.compatScale) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.frame, this.displayFrame, this.parentFrame, this.attachedFrame, Boolean.valueOf(this.isParentFrameClippedByDisplayCutout), Float.valueOf(this.compatScale));
    }
}
