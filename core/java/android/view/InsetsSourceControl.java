package android.view;

import android.graphics.Insets;
import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.proto.ProtoOutputStream;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

public class InsetsSourceControl implements Parcelable {
    public static final Parcelable.Creator<InsetsSourceControl> CREATOR =
            new Parcelable.Creator<
                    InsetsSourceControl>() { // from class: android.view.InsetsSourceControl.1
                @Override // android.os.Parcelable.Creator
                public InsetsSourceControl createFromParcel(Parcel in) {
                    return new InsetsSourceControl(in);
                }

                @Override // android.os.Parcelable.Creator
                public InsetsSourceControl[] newArray(int size) {
                    return new InsetsSourceControl[size];
                }
            };
    private boolean mControlledByPolicy;
    private final int mId;
    private final boolean mInitiallyVisible;
    private Insets mInsetsHint;
    private final SurfaceControl mLeash;
    private int mParcelableFlags;
    private boolean mSkipAnimationOnce;
    private final Point mSurfacePosition;
    private final int mType;

    public boolean isControlledByPolicy() {
        return this.mControlledByPolicy;
    }

    public InsetsSourceControl(
            int id,
            int type,
            SurfaceControl leash,
            boolean initiallyVisible,
            Point surfacePosition,
            Insets insetsHint) {
        this(id, type, leash, initiallyVisible, surfacePosition, insetsHint, false);
    }

    public InsetsSourceControl(
            int id,
            int type,
            SurfaceControl leash,
            boolean initiallyVisible,
            Point surfacePosition,
            Insets insetsHint,
            boolean controlledByPolicy) {
        this.mControlledByPolicy = controlledByPolicy;
        this.mId = id;
        this.mType = type;
        this.mLeash = leash;
        this.mInitiallyVisible = initiallyVisible;
        this.mSurfacePosition = surfacePosition;
        this.mInsetsHint = insetsHint;
    }

    public InsetsSourceControl(InsetsSourceControl other) {
        this.mId = other.mId;
        this.mType = other.mType;
        if (other.mLeash != null) {
            this.mLeash = new SurfaceControl(other.mLeash, "InsetsSourceControl");
        } else {
            this.mLeash = null;
        }
        this.mInitiallyVisible = other.mInitiallyVisible;
        this.mSurfacePosition = new Point(other.mSurfacePosition);
        this.mInsetsHint = other.mInsetsHint;
        this.mSkipAnimationOnce = other.getAndClearSkipAnimationOnce();
        this.mControlledByPolicy = other.mControlledByPolicy;
    }

    public InsetsSourceControl(Parcel in) {
        this.mId = in.readInt();
        this.mType = in.readInt();
        this.mLeash = (SurfaceControl) in.readTypedObject(SurfaceControl.CREATOR);
        this.mInitiallyVisible = in.readBoolean();
        this.mSurfacePosition = (Point) in.readTypedObject(Point.CREATOR);
        this.mInsetsHint = (Insets) in.readTypedObject(Insets.CREATOR);
        this.mSkipAnimationOnce = in.readBoolean();
        this.mControlledByPolicy = in.readBoolean();
    }

    public int getId() {
        return this.mId;
    }

    public int getType() {
        return this.mType;
    }

    public SurfaceControl getLeash() {
        return this.mLeash;
    }

    public boolean isInitiallyVisible() {
        return this.mInitiallyVisible;
    }

    public boolean setSurfacePosition(int left, int top) {
        if (this.mSurfacePosition.equals(left, top)) {
            return false;
        }
        this.mSurfacePosition.set(left, top);
        return true;
    }

    public Point getSurfacePosition() {
        return this.mSurfacePosition;
    }

    public void setInsetsHint(Insets insets) {
        this.mInsetsHint = insets;
    }

    public void setInsetsHint(int left, int top, int right, int bottom) {
        this.mInsetsHint = Insets.of(left, top, right, bottom);
    }

    public Insets getInsetsHint() {
        return this.mInsetsHint;
    }

    public void setSkipAnimationOnce(boolean skipAnimation) {
        this.mSkipAnimationOnce = skipAnimation;
    }

    public boolean getAndClearSkipAnimationOnce() {
        boolean result = this.mSkipAnimationOnce;
        this.mSkipAnimationOnce = false;
        return result;
    }

    public void setParcelableFlags(int parcelableFlags) {
        this.mParcelableFlags = parcelableFlags;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeInt(this.mType);
        dest.writeTypedObject(this.mLeash, this.mParcelableFlags);
        dest.writeBoolean(this.mInitiallyVisible);
        dest.writeTypedObject(this.mSurfacePosition, this.mParcelableFlags);
        dest.writeTypedObject(this.mInsetsHint, this.mParcelableFlags);
        dest.writeBoolean(this.mSkipAnimationOnce);
        dest.writeBoolean(this.mControlledByPolicy);
    }

    public void release(Consumer<SurfaceControl> surfaceReleaseConsumer) {
        if (this.mLeash != null) {
            surfaceReleaseConsumer.accept(this.mLeash);
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InsetsSourceControl that = (InsetsSourceControl) o;
        SurfaceControl thatLeash = that.mLeash;
        if (this.mId == that.mId
                && this.mType == that.mType
                && ((this.mLeash == thatLeash
                                || (this.mLeash != null
                                        && thatLeash != null
                                        && this.mLeash.isSameSurface(thatLeash)))
                        && this.mInitiallyVisible == that.mInitiallyVisible
                        && this.mSurfacePosition.equals(that.mSurfacePosition)
                        && this.mInsetsHint.equals(that.mInsetsHint)
                        && this.mSkipAnimationOnce == that.mSkipAnimationOnce)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(
                Integer.valueOf(this.mId),
                Integer.valueOf(this.mType),
                this.mLeash,
                Boolean.valueOf(this.mInitiallyVisible),
                this.mSurfacePosition,
                this.mInsetsHint,
                Boolean.valueOf(this.mSkipAnimationOnce));
    }

    public String toString() {
        return "InsetsSourceControl: {"
                + Integer.toHexString(this.mId)
                + " mType="
                + WindowInsets.Type.toString(this.mType)
                + (this.mInitiallyVisible ? " initiallyVisible" : "")
                + " mSurfacePosition="
                + this.mSurfacePosition
                + " mInsetsHint="
                + this.mInsetsHint
                + (this.mSkipAnimationOnce ? " skipAnimationOnce" : "")
                + "}";
    }

    public void dump(String prefix, PrintWriter pw) {
        pw.print(prefix);
        pw.print("InsetsSourceControl mId=");
        pw.print(Integer.toHexString(this.mId));
        pw.print(" mType=");
        pw.print(WindowInsets.Type.toString(this.mType));
        pw.print(" mLeash=");
        pw.print(this.mLeash);
        pw.print(" mInitiallyVisible=");
        pw.print(this.mInitiallyVisible);
        pw.print(" mSurfacePosition=");
        pw.print(this.mSurfacePosition);
        pw.print(" mInsetsHint=");
        pw.print(this.mInsetsHint);
        pw.print(" mSkipAnimationOnce=");
        pw.print(this.mSkipAnimationOnce);
        pw.print(" mControlledByPolicy=");
        pw.print(this.mControlledByPolicy);
        pw.println();
    }

    public void dumpDebug(ProtoOutputStream proto, long fieldId) {
        long token = proto.start(fieldId);
        long surfaceToken = proto.start(1146756268034L);
        proto.write(1120986464257L, this.mSurfacePosition.x);
        proto.write(1120986464258L, this.mSurfacePosition.y);
        proto.end(surfaceToken);
        if (this.mLeash != null) {
            this.mLeash.dumpDebug(proto, 1146756268035L);
        }
        proto.write(1120986464260L, this.mType);
        proto.end(token);
    }

    public static class Array implements Parcelable {
        public static final Parcelable.Creator<Array> CREATOR =
                new Parcelable.Creator<
                        Array>() { // from class: android.view.InsetsSourceControl.Array.1
                    @Override // android.os.Parcelable.Creator
                    public Array createFromParcel(Parcel in) {
                        return new Array(in);
                    }

                    @Override // android.os.Parcelable.Creator
                    public Array[] newArray(int size) {
                        return new Array[size];
                    }
                };
        private InsetsSourceControl[] mControls;

        public Array() {}

        public Array(Array other, boolean copyControls) {
            setTo(other, copyControls);
        }

        public Array(Parcel in) {
            readFromParcel(in);
        }

        public void setTo(Array other, boolean copyControls) {
            set(other.mControls, copyControls);
        }

        public void set(InsetsSourceControl[] controls, boolean copyControls) {
            if (controls == null || !copyControls) {
                this.mControls = controls;
                return;
            }
            this.mControls = new InsetsSourceControl[controls.length];
            for (int i = this.mControls.length - 1; i >= 0; i--) {
                if (controls[i] != null) {
                    this.mControls[i] = new InsetsSourceControl(controls[i]);
                }
            }
        }

        public InsetsSourceControl[] get() {
            return this.mControls;
        }

        public void release() {
            if (this.mControls == null) {
                return;
            }
            for (InsetsSourceControl control : this.mControls) {
                if (control != null) {
                    control.release(new InsetsController$$ExternalSyntheticLambda8());
                }
            }
        }

        public void setParcelableFlags(int parcelableFlags) {
            if (this.mControls == null) {
                return;
            }
            for (InsetsSourceControl control : this.mControls) {
                if (control != null) {
                    control.setParcelableFlags(parcelableFlags);
                }
            }
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public void readFromParcel(Parcel in) {
            this.mControls =
                    (InsetsSourceControl[]) in.createTypedArray(InsetsSourceControl.CREATOR);
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            out.writeTypedArray(this.mControls, flags);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Array other = (Array) o;
            return Arrays.equals(this.mControls, other.mControls);
        }

        public int hashCode() {
            return Arrays.hashCode(this.mControls);
        }
    }
}
