package android.view;

import android.graphics.Insets;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.security.keystore.KeyProperties;
import android.util.NtpTrustedTime;
import android.util.proto.ProtoOutputStream;
import android.view.WindowInsets;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes4.dex */
public class InsetsSource implements Parcelable {
    public static final int FLAG_ANIMATE_RESIZING = 8;
    public static final int FLAG_CONSUMING_CAPTION_INSET_BAR = 32;
    public static final int FLAG_CONSUMING_CAPTION_INSET_HANDLE = 64;
    public static final int FLAG_FORCE_CONSUMING = 4;
    public static final int FLAG_FORCE_CONSUMING_OPAQUE_CAPTION_BAR = 16;
    public static final int FLAG_INSETS_ROUNDED_CORNER = 2;
    public static final int FLAG_SUPPRESS_SCRIM = 1;
    static final int SIDE_BOTTOM = 4;
    static final int SIDE_LEFT = 1;
    static final int SIDE_NONE = 0;
    static final int SIDE_RIGHT = 3;
    static final int SIDE_TOP = 2;
    static final int SIDE_UNKNOWN = 5;
    private Rect[] mBoundingRects;
    private int mFlags;
    private final Rect mFrame;
    private final int mId;
    private Rect mMinimizedInsetHint;
    private int mSideHint;
    private final Rect mTmpBoundingRect;
    private final Rect mTmpFrame;
    private final int mType;
    private boolean mVisible;
    private Rect mVisibleFrame;
    public static final int ID_IME = createId(null, 0, WindowInsets.Type.ime());
    public static final int ID_IME_CAPTION_BAR = createId(null, 1, WindowInsets.Type.captionBar());
    private static final Rect[] NO_BOUNDING_RECTS = new Rect[0];
    public static final Parcelable.Creator<InsetsSource> CREATOR = new Parcelable.Creator<InsetsSource>() { // from class: android.view.InsetsSource.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InsetsSource createFromParcel(Parcel parcel) {
            return new InsetsSource(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InsetsSource[] newArray(int i) {
            return new InsetsSource[i];
        }
    };

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface InternalInsetsSide {
    }

    public static int getIndex(int i) {
        return (i & 65535) >> 5;
    }

    public static int getType(int i) {
        return 1 << (i & 31);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public InsetsSource(int i, int i2) {
        this.mMinimizedInsetHint = new Rect();
        this.mSideHint = 0;
        this.mTmpFrame = new Rect();
        this.mTmpBoundingRect = new Rect();
        this.mId = i;
        this.mType = i2;
        this.mFrame = new Rect();
        this.mVisible = (WindowInsets.Type.defaultVisible() & i2) != 0;
    }

    public InsetsSource(InsetsSource insetsSource) {
        this.mMinimizedInsetHint = new Rect();
        this.mSideHint = 0;
        this.mTmpFrame = new Rect();
        this.mTmpBoundingRect = new Rect();
        this.mId = insetsSource.mId;
        this.mType = insetsSource.mType;
        this.mFrame = new Rect(insetsSource.mFrame);
        this.mVisible = insetsSource.mVisible;
        this.mVisibleFrame = insetsSource.mVisibleFrame != null ? new Rect(insetsSource.mVisibleFrame) : null;
        this.mFlags = insetsSource.mFlags;
        this.mSideHint = insetsSource.mSideHint;
        Rect[] rectArr = insetsSource.mBoundingRects;
        this.mBoundingRects = rectArr != null ? (Rect[]) rectArr.clone() : null;
        if (CoreRune.FW_MINIMIZED_IME_INSET_ANIM) {
            this.mMinimizedInsetHint = insetsSource.mMinimizedInsetHint;
        }
    }

    public void set(InsetsSource insetsSource) {
        this.mFrame.set(insetsSource.mFrame);
        this.mVisible = insetsSource.mVisible;
        this.mVisibleFrame = insetsSource.mVisibleFrame != null ? new Rect(insetsSource.mVisibleFrame) : null;
        this.mFlags = insetsSource.mFlags;
        this.mSideHint = insetsSource.mSideHint;
        Rect[] rectArr = insetsSource.mBoundingRects;
        this.mBoundingRects = rectArr != null ? (Rect[]) rectArr.clone() : null;
        if (CoreRune.FW_MINIMIZED_IME_INSET_ANIM) {
            this.mMinimizedInsetHint = insetsSource.mMinimizedInsetHint;
        }
    }

    public InsetsSource setFrame(int i, int i2, int i3, int i4) {
        this.mFrame.set(i, i2, i3, i4);
        return this;
    }

    public InsetsSource setFrame(Rect rect) {
        this.mFrame.set(rect);
        return this;
    }

    public InsetsSource setVisibleFrame(Rect rect) {
        this.mVisibleFrame = rect != null ? new Rect(rect) : null;
        return this;
    }

    public InsetsSource setVisible(boolean z) {
        this.mVisible = z;
        return this;
    }

    public InsetsSource setFlags(int i) {
        this.mFlags = i;
        return this;
    }

    public InsetsSource setFlags(int i, int i2) {
        this.mFlags = (i & i2) | (this.mFlags & (~i2));
        return this;
    }

    public InsetsSource updateSideHint(Rect rect) {
        this.mSideHint = getInsetSide(calculateInsets(rect, this.mFrame, true));
        return this;
    }

    public InsetsSource setMinimizedInsetHint(Rect rect) {
        this.mMinimizedInsetHint.set(rect);
        return this;
    }

    public Rect getMinimizedInsetHint() {
        return this.mMinimizedInsetHint;
    }

    public InsetsSource setBoundingRects(Rect[] rectArr) {
        this.mBoundingRects = rectArr != null ? (Rect[]) rectArr.clone() : null;
        return this;
    }

    public int getId() {
        return this.mId;
    }

    public int getType() {
        return this.mType;
    }

    public Rect getFrame() {
        return this.mFrame;
    }

    public Rect getVisibleFrame() {
        return this.mVisibleFrame;
    }

    public boolean isVisible() {
        return this.mVisible;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public boolean hasFlags(int i) {
        return (this.mFlags & i) == i;
    }

    public Rect[] getBoundingRects() {
        return this.mBoundingRects;
    }

    public Insets calculateInsets(Rect rect, boolean z) {
        return calculateInsets(rect, this.mFrame, z);
    }

    public Insets calculateInsetsIgnoreIntersection(Rect rect, boolean z) {
        return calculateInsets(rect, this.mFrame, z, true);
    }

    public Insets calculateVisibleInsets(Rect rect) {
        Rect rect2 = this.mVisibleFrame;
        if (rect2 == null) {
            rect2 = this.mFrame;
        }
        return calculateInsets(rect, rect2, false);
    }

    private Insets calculateInsets(Rect rect, Rect rect2, boolean z) {
        return calculateInsets(rect, rect2, z, false);
    }

    private Insets calculateInsets(Rect rect, Rect rect2, boolean z, boolean z2) {
        boolean intersect;
        if (!z && !this.mVisible) {
            return Insets.NONE;
        }
        if (getType() == WindowInsets.Type.captionBar()) {
            if (getId() == ID_IME_CAPTION_BAR) {
                return Insets.of(0, 0, 0, rect2.height());
            }
            return Insets.of(0, rect2.height(), 0, 0);
        }
        if (rect.isEmpty()) {
            intersect = getIntersection(rect2, rect, this.mTmpFrame);
        } else {
            intersect = this.mTmpFrame.setIntersect(rect2, rect);
        }
        if (!intersect) {
            if (CoreRune.MW_EMBED_ACTIVITY && getType() == WindowInsets.Type.statusBars() && z2) {
                return Insets.of(0, rect2.height(), 0, 0);
            }
            return Insets.NONE;
        }
        if (getType() == WindowInsets.Type.ime()) {
            return Insets.of(0, 0, 0, this.mTmpFrame.height());
        }
        if (this.mTmpFrame.equals(rect)) {
            int i = this.mSideHint;
            if (i == 2) {
                return Insets.of(0, this.mTmpFrame.height(), 0, 0);
            }
            if (i == 3) {
                return Insets.of(0, 0, this.mTmpFrame.width(), 0);
            }
            if (i != 4) {
                return Insets.of(this.mTmpFrame.width(), 0, 0, 0);
            }
            return Insets.of(0, 0, 0, this.mTmpFrame.height());
        }
        if (this.mTmpFrame.width() == rect.width()) {
            if (this.mTmpFrame.top == rect.top) {
                return Insets.of(0, this.mTmpFrame.height(), 0, 0);
            }
            if (this.mTmpFrame.bottom == rect.bottom) {
                return Insets.of(0, 0, 0, this.mTmpFrame.height());
            }
            if (this.mTmpFrame.top == 0) {
                return Insets.of(0, this.mTmpFrame.height(), 0, 0);
            }
        } else if (this.mTmpFrame.height() == rect.height()) {
            if (this.mTmpFrame.left == rect.left) {
                return Insets.of(this.mTmpFrame.width(), 0, 0, 0);
            }
            if (this.mTmpFrame.right == rect.right) {
                return Insets.of(0, 0, this.mTmpFrame.width(), 0);
            }
        } else {
            int i2 = this.mSideHint;
            if (i2 != 1) {
                if (i2 != 2) {
                    if (i2 == 3) {
                        if (this.mTmpFrame.right == rect.right) {
                            return Insets.of(0, 0, this.mTmpFrame.width(), 0);
                        }
                    } else if (i2 == 4 && this.mTmpFrame.bottom == rect.bottom) {
                        return Insets.of(0, 0, 0, this.mTmpFrame.height());
                    }
                } else if (this.mTmpFrame.top == rect.top) {
                    return Insets.of(0, this.mTmpFrame.height(), 0, 0);
                }
            } else if (this.mTmpFrame.left == rect.left) {
                return Insets.of(this.mTmpFrame.width(), 0, 0, 0);
            }
        }
        return Insets.NONE;
    }

    public Rect[] calculateBoundingRects(Rect rect, boolean z) {
        if (!z && !this.mVisible) {
            return NO_BOUNDING_RECTS;
        }
        Rect frame = getFrame();
        if (this.mBoundingRects == null) {
            if (this.mTmpBoundingRect.setIntersect(frame, rect)) {
                return new Rect[]{new Rect(this.mTmpBoundingRect.left - rect.left, this.mTmpBoundingRect.top - rect.top, this.mTmpBoundingRect.right - rect.left, this.mTmpBoundingRect.bottom - rect.top)};
            }
            return NO_BOUNDING_RECTS;
        }
        if (getType() == WindowInsets.Type.captionBar()) {
            ArrayList arrayList = new ArrayList();
            for (Rect rect2 : this.mBoundingRects) {
                int height = frame.height();
                this.mTmpBoundingRect.set(rect2);
                if (getId() == ID_IME_CAPTION_BAR) {
                    this.mTmpBoundingRect.offset(0, rect.height() - height);
                }
                arrayList.add(new Rect(this.mTmpBoundingRect));
            }
            return (Rect[]) arrayList.toArray(new Rect[arrayList.size()]);
        }
        ArrayList arrayList2 = new ArrayList();
        for (Rect rect3 : this.mBoundingRects) {
            if (this.mTmpBoundingRect.setIntersect(new Rect(rect3.left + frame.left, rect3.top + frame.top, rect3.right + frame.left, rect3.bottom + frame.top), rect)) {
                arrayList2.add(new Rect(this.mTmpBoundingRect.left - rect.left, this.mTmpBoundingRect.top - rect.top, this.mTmpBoundingRect.right - rect.left, this.mTmpBoundingRect.bottom - rect.top));
            }
        }
        if (arrayList2.isEmpty()) {
            return NO_BOUNDING_RECTS;
        }
        return (Rect[]) arrayList2.toArray(new Rect[arrayList2.size()]);
    }

    private static boolean getIntersection(Rect rect, Rect rect2, Rect rect3) {
        if (rect.left <= rect2.right && rect2.left <= rect.right && rect.top <= rect2.bottom && rect2.top <= rect.bottom) {
            rect3.left = Math.max(rect.left, rect2.left);
            rect3.top = Math.max(rect.top, rect2.top);
            rect3.right = Math.min(rect.right, rect2.right);
            rect3.bottom = Math.min(rect.bottom, rect2.bottom);
            return true;
        }
        rect3.setEmpty();
        return false;
    }

    static int getInsetSide(Insets insets) {
        if (Insets.NONE.equals(insets)) {
            return 0;
        }
        if (insets.left != 0) {
            return 1;
        }
        if (insets.top != 0) {
            return 2;
        }
        if (insets.right != 0) {
            return 3;
        }
        return insets.bottom != 0 ? 4 : 5;
    }

    static String sideToString(int i) {
        if (i == 0) {
            return KeyProperties.DIGEST_NONE;
        }
        if (i == 1) {
            return "LEFT";
        }
        if (i == 2) {
            return "TOP";
        }
        if (i == 3) {
            return "RIGHT";
        }
        if (i == 4) {
            return "BOTTOM";
        }
        return "UNKNOWN:" + i;
    }

    public static int createId(Object obj, int i, int i2) {
        if (i < 0 || i >= 2048) {
            throw new IllegalArgumentException();
        }
        return ((System.identityHashCode(obj) % 65536) << 16) + (i << 5) + WindowInsets.Type.indexOf(i2);
    }

    public static String flagsToString(int i) {
        StringJoiner stringJoiner = new StringJoiner(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
        if ((i & 1) != 0) {
            stringJoiner.add("SUPPRESS_SCRIM");
        }
        if ((i & 2) != 0) {
            stringJoiner.add("INSETS_ROUNDED_CORNER");
        }
        if ((i & 4) != 0) {
            stringJoiner.add("FORCE_CONSUMING");
        }
        if ((i & 8) != 0) {
            stringJoiner.add("ANIMATE_RESIZING");
        }
        if ((i & 16) != 0) {
            stringJoiner.add("FORCE_CONSUMING_OPAQUE_CAPTION_BAR");
        }
        if ((i & 32) != 0) {
            stringJoiner.add("FLAG_CONSUMING_CAPTION_INSET_BAR");
        }
        if ((i & 64) != 0) {
            stringJoiner.add("FLAG_CONSUMING_CAPTION_INSET_HANDLE");
        }
        return stringJoiner.toString();
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        if (!com.android.internal.hidden_from_bootclasspath.android.os.Flags.androidOsBuildVanillaIceCream()) {
            protoOutputStream.write(1138166333441L, WindowInsets.Type.toString(this.mType));
        }
        this.mFrame.dumpDebug(protoOutputStream, 1146756268034L);
        Rect rect = this.mVisibleFrame;
        if (rect != null) {
            rect.dumpDebug(protoOutputStream, 1146756268035L);
        }
        protoOutputStream.write(1133871366148L, this.mVisible);
        protoOutputStream.write(1120986464261L, this.mType);
        protoOutputStream.end(start);
    }

    public void dump(String str, PrintWriter printWriter) {
        printWriter.print(str);
        printWriter.print("InsetsSource id=");
        printWriter.print(Integer.toHexString(this.mId));
        printWriter.print(" type=");
        printWriter.print(WindowInsets.Type.toString(this.mType));
        printWriter.print(" frame=");
        printWriter.print(this.mFrame.toShortString());
        if (this.mVisibleFrame != null) {
            printWriter.print(" visibleFrame=");
            printWriter.print(this.mVisibleFrame.toShortString());
        }
        printWriter.print(" visible=");
        printWriter.print(this.mVisible);
        printWriter.print(" flags=");
        printWriter.print(flagsToString(this.mFlags));
        printWriter.print(" sideHint=");
        printWriter.print(sideToString(this.mSideHint));
        printWriter.print(" boundingRects=");
        printWriter.print(Arrays.toString(this.mBoundingRects));
        printWriter.println();
    }

    public boolean equals(Object obj) {
        return equals(obj, false);
    }

    public boolean equals(Object obj, boolean z) {
        int i;
        boolean z2;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        InsetsSource insetsSource = (InsetsSource) obj;
        if ((CoreRune.FW_MINIMIZED_IME_INSET_ANIM && !this.mMinimizedInsetHint.equals(insetsSource.mMinimizedInsetHint)) || this.mId != insetsSource.mId || (i = this.mType) != insetsSource.mType || (z2 = this.mVisible) != insetsSource.mVisible || this.mFlags != insetsSource.mFlags || this.mSideHint != insetsSource.mSideHint) {
            return false;
        }
        if (z && !z2 && i == WindowInsets.Type.ime()) {
            return true;
        }
        if (Objects.equals(this.mVisibleFrame, insetsSource.mVisibleFrame) && this.mFrame.equals(insetsSource.mFrame)) {
            return Arrays.equals(this.mBoundingRects, insetsSource.mBoundingRects);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mId), Integer.valueOf(this.mType), this.mFrame, this.mVisibleFrame, Boolean.valueOf(this.mVisible), Integer.valueOf(this.mFlags), Integer.valueOf(this.mSideHint), Integer.valueOf(Arrays.hashCode(this.mBoundingRects)));
    }

    public InsetsSource(Parcel parcel) {
        this.mMinimizedInsetHint = new Rect();
        this.mSideHint = 0;
        this.mTmpFrame = new Rect();
        this.mTmpBoundingRect = new Rect();
        this.mId = parcel.readInt();
        this.mType = parcel.readInt();
        this.mFrame = Rect.CREATOR.createFromParcel(parcel);
        if (parcel.readInt() != 0) {
            this.mVisibleFrame = Rect.CREATOR.createFromParcel(parcel);
        } else {
            this.mVisibleFrame = null;
        }
        this.mVisible = parcel.readBoolean();
        this.mFlags = parcel.readInt();
        this.mSideHint = parcel.readInt();
        this.mBoundingRects = (Rect[]) parcel.createTypedArray(Rect.CREATOR);
        if (CoreRune.FW_MINIMIZED_IME_INSET_ANIM) {
            this.mMinimizedInsetHint = Rect.CREATOR.createFromParcel(parcel);
        }
    }

    int getSideHint() {
        return this.mSideHint;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mType);
        this.mFrame.writeToParcel(parcel, 0);
        if (this.mVisibleFrame != null) {
            parcel.writeInt(1);
            this.mVisibleFrame.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeBoolean(this.mVisible);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mSideHint);
        parcel.writeTypedArray(this.mBoundingRects, i);
        if (CoreRune.FW_MINIMIZED_IME_INSET_ANIM) {
            this.mMinimizedInsetHint.writeToParcel(parcel, 0);
        }
    }

    public String toString() {
        return "InsetsSource: {" + Integer.toHexString(this.mId) + " mType=" + WindowInsets.Type.toString(this.mType) + " mFrame=" + this.mFrame.toShortString() + " mVisible=" + this.mVisible + " mFlags=" + flagsToString(this.mFlags) + " mSideHint=" + sideToString(this.mSideHint) + " mBoundingRects=" + Arrays.toString(this.mBoundingRects) + "}";
    }
}
