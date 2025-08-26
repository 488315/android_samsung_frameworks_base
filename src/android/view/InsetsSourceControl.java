package android.view;

import android.graphics.Insets;
import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SequenceUtils;
import android.util.proto.ProtoOutputStream;
import android.view.WindowInsets;
import android.view.inputmethod.ImeTracker;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public class InsetsSourceControl implements Parcelable {
    public static final Parcelable.Creator<InsetsSourceControl> CREATOR = new Parcelable.Creator<InsetsSourceControl>() { // from class: android.view.InsetsSourceControl.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InsetsSourceControl createFromParcel(Parcel parcel) {
            return new InsetsSourceControl(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InsetsSourceControl[] newArray(int i) {
            return new InsetsSourceControl[i];
        }
    };
    private boolean mControlledByPolicy;
    private final int mId;
    private ImeTracker.Token mImeStatsToken;
    private final boolean mInitiallyVisible;
    private Insets mInsetsHint;
    private final SurfaceControl mLeash;
    private int mParcelableFlags;
    private boolean mSkipAnimationOnce;
    private final Point mSurfacePosition;
    private final int mType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean isControlledByPolicy() {
        return this.mControlledByPolicy;
    }

    public InsetsSourceControl(int i, int i2, SurfaceControl surfaceControl, boolean z, Point point, Insets insets) {
        this(i, i2, surfaceControl, z, point, insets, false);
    }

    public InsetsSourceControl(int i, int i2, SurfaceControl surfaceControl, boolean z, Point point, Insets insets, boolean z2) {
        this.mControlledByPolicy = z2;
        this.mId = i;
        this.mType = i2;
        this.mLeash = surfaceControl;
        this.mInitiallyVisible = z;
        this.mSurfacePosition = point;
        this.mInsetsHint = insets;
    }

    public InsetsSourceControl(InsetsSourceControl insetsSourceControl) {
        this.mId = insetsSourceControl.mId;
        this.mType = insetsSourceControl.mType;
        if (insetsSourceControl.mLeash != null) {
            this.mLeash = new SurfaceControl(insetsSourceControl.mLeash, "InsetsSourceControl");
        } else {
            this.mLeash = null;
        }
        this.mInitiallyVisible = insetsSourceControl.mInitiallyVisible;
        this.mSurfacePosition = new Point(insetsSourceControl.mSurfacePosition);
        this.mInsetsHint = insetsSourceControl.mInsetsHint;
        this.mSkipAnimationOnce = insetsSourceControl.getAndClearSkipAnimationOnce();
        this.mImeStatsToken = insetsSourceControl.getImeStatsToken();
        this.mControlledByPolicy = insetsSourceControl.mControlledByPolicy;
    }

    public InsetsSourceControl(Parcel parcel) {
        this.mId = parcel.readInt();
        this.mType = parcel.readInt();
        this.mLeash = (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR);
        this.mInitiallyVisible = parcel.readBoolean();
        this.mSurfacePosition = (Point) parcel.readTypedObject(Point.CREATOR);
        this.mInsetsHint = (Insets) parcel.readTypedObject(Insets.CREATOR);
        this.mSkipAnimationOnce = parcel.readBoolean();
        this.mImeStatsToken = (ImeTracker.Token) parcel.readTypedObject(ImeTracker.Token.CREATOR);
        this.mControlledByPolicy = parcel.readBoolean();
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

    public boolean setSurfacePosition(int i, int i2) {
        if (this.mSurfacePosition.equals(i, i2)) {
            return false;
        }
        this.mSurfacePosition.set(i, i2);
        return true;
    }

    public Point getSurfacePosition() {
        return this.mSurfacePosition;
    }

    public void setInsetsHint(Insets insets) {
        this.mInsetsHint = insets;
    }

    public void setInsetsHint(int i, int i2, int i3, int i4) {
        this.mInsetsHint = Insets.of(i, i2, i3, i4);
    }

    public Insets getInsetsHint() {
        return this.mInsetsHint;
    }

    public boolean isFake() {
        return this.mLeash == null && Insets.NONE.equals(this.mInsetsHint);
    }

    public void setSkipAnimationOnce(boolean z) {
        this.mSkipAnimationOnce = z;
    }

    public boolean getAndClearSkipAnimationOnce() {
        boolean z = this.mSkipAnimationOnce;
        this.mSkipAnimationOnce = false;
        return z;
    }

    public ImeTracker.Token getImeStatsToken() {
        return this.mImeStatsToken;
    }

    public void setImeStatsToken(ImeTracker.Token token) {
        this.mImeStatsToken = token;
    }

    public void setParcelableFlags(int i) {
        this.mParcelableFlags = i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mType);
        parcel.writeTypedObject(this.mLeash, this.mParcelableFlags);
        parcel.writeBoolean(this.mInitiallyVisible);
        parcel.writeTypedObject(this.mSurfacePosition, this.mParcelableFlags);
        parcel.writeTypedObject(this.mInsetsHint, this.mParcelableFlags);
        parcel.writeBoolean(this.mSkipAnimationOnce);
        parcel.writeTypedObject(this.mImeStatsToken, this.mParcelableFlags);
        parcel.writeBoolean(this.mControlledByPolicy);
    }

    public void release(Consumer<SurfaceControl> consumer) {
        SurfaceControl surfaceControl = this.mLeash;
        if (surfaceControl == null || !surfaceControl.isValid()) {
            return;
        }
        consumer.accept(this.mLeash);
    }

    public boolean equals(Object obj) {
        SurfaceControl surfaceControl;
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            InsetsSourceControl insetsSourceControl = (InsetsSourceControl) obj;
            SurfaceControl surfaceControl2 = insetsSourceControl.mLeash;
            if (this.mId == insetsSourceControl.mId && this.mType == insetsSourceControl.mType && (((surfaceControl = this.mLeash) == surfaceControl2 || (surfaceControl != null && surfaceControl2 != null && surfaceControl.isSameSurface(surfaceControl2))) && this.mInitiallyVisible == insetsSourceControl.mInitiallyVisible && this.mSurfacePosition.equals(insetsSourceControl.mSurfacePosition) && this.mInsetsHint.equals(insetsSourceControl.mInsetsHint) && this.mSkipAnimationOnce == insetsSourceControl.mSkipAnimationOnce)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mId), Integer.valueOf(this.mType), this.mLeash, Boolean.valueOf(this.mInitiallyVisible), this.mSurfacePosition, this.mInsetsHint, Boolean.valueOf(this.mSkipAnimationOnce), this.mImeStatsToken);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("InsetsSourceControl: {");
        sb.append(Integer.toHexString(this.mId));
        sb.append(" mType=");
        sb.append(WindowInsets.Type.toString(this.mType));
        sb.append(this.mInitiallyVisible ? " initiallyVisible" : "");
        sb.append(" mSurfacePosition=");
        sb.append(this.mSurfacePosition);
        sb.append(" mInsetsHint=");
        sb.append(this.mInsetsHint);
        sb.append(this.mSkipAnimationOnce ? " skipAnimationOnce" : "");
        sb.append("}");
        return sb.toString();
    }

    public void dump(String str, PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("InsetsSourceControl mId=");
        printWriter.print(Integer.toHexString(this.mId));
        printWriter.print(" mType=");
        printWriter.print(WindowInsets.Type.toString(this.mType));
        printWriter.print(" mLeash=");
        printWriter.print(this.mLeash);
        printWriter.print(" mInitiallyVisible=");
        printWriter.print(this.mInitiallyVisible);
        printWriter.print(" mSurfacePosition=");
        printWriter.print(this.mSurfacePosition);
        printWriter.print(" mInsetsHint=");
        printWriter.print(this.mInsetsHint);
        printWriter.print(" mSkipAnimationOnce=");
        printWriter.print(this.mSkipAnimationOnce);
        printWriter.print(" mImeStatsToken=");
        printWriter.print(this.mImeStatsToken);
        printWriter.print(" mControlledByPolicy=");
        printWriter.print(this.mControlledByPolicy);
        printWriter.println();
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long jStart = protoOutputStream.start(j);
        long jStart2 = protoOutputStream.start(1146756268034L);
        protoOutputStream.write(1120986464257L, this.mSurfacePosition.x);
        protoOutputStream.write(1120986464258L, this.mSurfacePosition.y);
        protoOutputStream.end(jStart2);
        SurfaceControl surfaceControl = this.mLeash;
        if (surfaceControl != null) {
            surfaceControl.dumpDebug(protoOutputStream, 1146756268035L);
        }
        protoOutputStream.write(1120986464260L, this.mType);
        protoOutputStream.end(jStart);
    }

    public static class Array implements Parcelable {
        public static final Parcelable.Creator<Array> CREATOR = new Parcelable.Creator<Array>() { // from class: android.view.InsetsSourceControl.Array.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Array createFromParcel(Parcel parcel) {
                return new Array(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Array[] newArray(int i) {
                return new Array[i];
            }
        };
        private InsetsSourceControl[] mControls;
        private int mSeq = SequenceUtils.getInitSeq();

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Array() {
        }

        public Array(Array array, boolean z) {
            setTo(array, z);
        }

        public Array(Parcel parcel) {
            readFromParcel(parcel);
        }

        public int getSeq() {
            return this.mSeq;
        }

        public void setSeq(int i) {
            this.mSeq = i;
        }

        public void setTo(Array array, boolean z) {
            set(array.mControls, z);
            this.mSeq = array.mSeq;
        }

        public void set(InsetsSourceControl[] insetsSourceControlArr, boolean z) {
            if (insetsSourceControlArr == null || !z) {
                this.mControls = insetsSourceControlArr;
                return;
            }
            InsetsSourceControl[] insetsSourceControlArr2 = new InsetsSourceControl[insetsSourceControlArr.length];
            this.mControls = insetsSourceControlArr2;
            for (int length = insetsSourceControlArr2.length - 1; length >= 0; length--) {
                if (insetsSourceControlArr[length] != null) {
                    this.mControls[length] = new InsetsSourceControl(insetsSourceControlArr[length]);
                }
            }
        }

        public InsetsSourceControl[] get() {
            return this.mControls;
        }

        public void release() {
            InsetsSourceControl[] insetsSourceControlArr = this.mControls;
            if (insetsSourceControlArr == null) {
                return;
            }
            for (InsetsSourceControl insetsSourceControl : insetsSourceControlArr) {
                if (insetsSourceControl != null) {
                    insetsSourceControl.release(new InsetsController$$ExternalSyntheticLambda8());
                }
            }
        }

        public void setParcelableFlags(int i) {
            InsetsSourceControl[] insetsSourceControlArr = this.mControls;
            if (insetsSourceControlArr == null) {
                return;
            }
            for (InsetsSourceControl insetsSourceControl : insetsSourceControlArr) {
                if (insetsSourceControl != null) {
                    insetsSourceControl.setParcelableFlags(i);
                }
            }
        }

        public void readFromParcel(Parcel parcel) {
            this.mControls = (InsetsSourceControl[]) parcel.createTypedArray(InsetsSourceControl.CREATOR);
            this.mSeq = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeTypedArray(this.mControls, i);
            parcel.writeInt(this.mSeq);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            return Arrays.equals(this.mControls, ((Array) obj).mControls);
        }

        public int hashCode() {
            return Arrays.hashCode(this.mControls);
        }
    }
}
