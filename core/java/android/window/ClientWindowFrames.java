package android.window;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class ClientWindowFrames implements Parcelable {
    public static final Parcelable.Creator<ClientWindowFrames> CREATOR =
            new Parcelable.Creator<
                    ClientWindowFrames>() { // from class: android.window.ClientWindowFrames.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ClientWindowFrames createFromParcel(Parcel in) {
                    return new ClientWindowFrames(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public ClientWindowFrames[] newArray(int size) {
                    return new ClientWindowFrames[size];
                }
            };
    public Rect attachedFrame;
    public float compatScale;
    public final Rect displayFrame;
    public final Rect frame;
    public boolean isParentFrameClippedByDisplayCutout;
    public final Rect parentFrame;

    public ClientWindowFrames() {
        this.frame = new Rect();
        this.displayFrame = new Rect();
        this.parentFrame = new Rect();
        this.compatScale = 1.0f;
    }

    public ClientWindowFrames(ClientWindowFrames other) {
        this.frame = new Rect();
        this.displayFrame = new Rect();
        this.parentFrame = new Rect();
        this.compatScale = 1.0f;
        setTo(other);
    }

    private ClientWindowFrames(Parcel in) {
        this.frame = new Rect();
        this.displayFrame = new Rect();
        this.parentFrame = new Rect();
        this.compatScale = 1.0f;
        readFromParcel(in);
    }

    public void setTo(ClientWindowFrames other) {
        this.frame.set(other.frame);
        this.displayFrame.set(other.displayFrame);
        this.parentFrame.set(other.parentFrame);
        if (other.attachedFrame != null) {
            this.attachedFrame = new Rect(other.attachedFrame);
        }
        this.isParentFrameClippedByDisplayCutout = other.isParentFrameClippedByDisplayCutout;
        this.compatScale = other.compatScale;
    }

    public void readFromParcel(Parcel in) {
        this.frame.readFromParcel(in);
        this.displayFrame.readFromParcel(in);
        this.parentFrame.readFromParcel(in);
        this.attachedFrame = (Rect) in.readTypedObject(Rect.CREATOR);
        this.isParentFrameClippedByDisplayCutout = in.readBoolean();
        this.compatScale = in.readFloat();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        this.frame.writeToParcel(dest, flags);
        this.displayFrame.writeToParcel(dest, flags);
        this.parentFrame.writeToParcel(dest, flags);
        dest.writeTypedObject(this.attachedFrame, flags);
        dest.writeBoolean(this.isParentFrameClippedByDisplayCutout);
        dest.writeFloat(this.compatScale);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        return "ClientWindowFrames{frame="
                + this.frame.toShortString(sb)
                + " display="
                + this.displayFrame.toShortString(sb)
                + " parentFrame="
                + this.parentFrame.toShortString(sb)
                + (this.attachedFrame != null
                        ? " attachedFrame=" + this.attachedFrame.toShortString()
                        : "")
                + (this.isParentFrameClippedByDisplayCutout ? " parentClippedByDisplayCutout" : "")
                + (this.compatScale != 1.0f ? " sizeCompatScale=" + this.compatScale : "")
                + "}";
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClientWindowFrames other = (ClientWindowFrames) o;
        if (this.frame.equals(other.frame)
                && this.displayFrame.equals(other.displayFrame)
                && this.parentFrame.equals(other.parentFrame)
                && Objects.equals(this.attachedFrame, other.attachedFrame)
                && this.isParentFrameClippedByDisplayCutout
                        == other.isParentFrameClippedByDisplayCutout
                && this.compatScale == other.compatScale) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(
                this.frame,
                this.displayFrame,
                this.parentFrame,
                this.attachedFrame,
                Boolean.valueOf(this.isParentFrameClippedByDisplayCutout),
                Float.valueOf(this.compatScale));
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
