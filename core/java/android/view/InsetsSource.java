package android.view;

import android.graphics.Insets;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.security.keystore.KeyProperties;
import android.util.NtpTrustedTime;
import android.util.proto.ProtoOutputStream;

import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringJoiner;

public class InsetsSource implements Parcelable {
    public static final int FLAG_ANIMATE_RESIZING = 8;
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
    public static final Parcelable.Creator<InsetsSource> CREATOR =
            new Parcelable.Creator<InsetsSource>() { // from class: android.view.InsetsSource.1
                @Override // android.os.Parcelable.Creator
                public InsetsSource createFromParcel(Parcel in) {
                    return new InsetsSource(in);
                }

                @Override // android.os.Parcelable.Creator
                public InsetsSource[] newArray(int size) {
                    return new InsetsSource[size];
                }
            };

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface InternalInsetsSide {}

    public InsetsSource(int id, int type) {
        this.mMinimizedInsetHint = new Rect();
        this.mSideHint = 0;
        this.mTmpFrame = new Rect();
        this.mTmpBoundingRect = new Rect();
        this.mId = id;
        this.mType = type;
        this.mFrame = new Rect();
        this.mVisible = (WindowInsets.Type.defaultVisible() & type) != 0;
    }

    public InsetsSource(InsetsSource other) {
        Rect rect;
        this.mMinimizedInsetHint = new Rect();
        this.mSideHint = 0;
        this.mTmpFrame = new Rect();
        this.mTmpBoundingRect = new Rect();
        this.mId = other.mId;
        this.mType = other.mType;
        this.mFrame = new Rect(other.mFrame);
        this.mVisible = other.mVisible;
        if (other.mVisibleFrame != null) {
            rect = new Rect(other.mVisibleFrame);
        } else {
            rect = null;
        }
        this.mVisibleFrame = rect;
        this.mFlags = other.mFlags;
        this.mSideHint = other.mSideHint;
        this.mBoundingRects =
                other.mBoundingRects != null ? (Rect[]) other.mBoundingRects.clone() : null;
        if (CoreRune.FW_MINIMIZED_IME_INSET_ANIM) {
            this.mMinimizedInsetHint = other.mMinimizedInsetHint;
        }
    }

    public void set(InsetsSource other) {
        Rect rect;
        this.mFrame.set(other.mFrame);
        this.mVisible = other.mVisible;
        if (other.mVisibleFrame != null) {
            rect = new Rect(other.mVisibleFrame);
        } else {
            rect = null;
        }
        this.mVisibleFrame = rect;
        this.mFlags = other.mFlags;
        this.mSideHint = other.mSideHint;
        this.mBoundingRects =
                other.mBoundingRects != null ? (Rect[]) other.mBoundingRects.clone() : null;
        if (CoreRune.FW_MINIMIZED_IME_INSET_ANIM) {
            this.mMinimizedInsetHint = other.mMinimizedInsetHint;
        }
    }

    public InsetsSource setFrame(int left, int top, int right, int bottom) {
        this.mFrame.set(left, top, right, bottom);
        return this;
    }

    public InsetsSource setFrame(Rect frame) {
        this.mFrame.set(frame);
        return this;
    }

    public InsetsSource setVisibleFrame(Rect visibleFrame) {
        this.mVisibleFrame = visibleFrame != null ? new Rect(visibleFrame) : null;
        return this;
    }

    public InsetsSource setVisible(boolean visible) {
        this.mVisible = visible;
        return this;
    }

    public InsetsSource setFlags(int flags) {
        this.mFlags = flags;
        return this;
    }

    public InsetsSource setFlags(int flags, int mask) {
        this.mFlags = (this.mFlags & (~mask)) | (flags & mask);
        return this;
    }

    public InsetsSource updateSideHint(Rect bounds) {
        this.mSideHint = getInsetSide(calculateInsets(bounds, this.mFrame, true));
        return this;
    }

    public InsetsSource setMinimizedInsetHint(Rect minimizedInsetHint) {
        this.mMinimizedInsetHint.set(minimizedInsetHint);
        return this;
    }

    public Rect getMinimizedInsetHint() {
        return this.mMinimizedInsetHint;
    }

    public InsetsSource setBoundingRects(Rect[] rects) {
        this.mBoundingRects = rects != null ? (Rect[]) rects.clone() : null;
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

    public boolean hasFlags(int flags) {
        return (this.mFlags & flags) == flags;
    }

    public Rect[] getBoundingRects() {
        return this.mBoundingRects;
    }

    public Insets calculateInsets(Rect relativeFrame, boolean ignoreVisibility) {
        return calculateInsets(relativeFrame, this.mFrame, ignoreVisibility);
    }

    public Insets calculateVisibleInsets(Rect relativeFrame) {
        return calculateInsets(
                relativeFrame,
                this.mVisibleFrame != null ? this.mVisibleFrame : this.mFrame,
                false);
    }

    private Insets calculateInsets(Rect relativeFrame, Rect frame, boolean ignoreVisibility) {
        boolean hasIntersection;
        if (!ignoreVisibility && !this.mVisible) {
            return Insets.NONE;
        }
        if (getType() == WindowInsets.Type.captionBar()) {
            if (getId() == ID_IME_CAPTION_BAR) {
                return Insets.of(0, 0, 0, frame.height());
            }
            return Insets.of(0, frame.height(), 0, 0);
        }
        if (relativeFrame.isEmpty()) {
            hasIntersection = getIntersection(frame, relativeFrame, this.mTmpFrame);
        } else {
            hasIntersection = this.mTmpFrame.setIntersect(frame, relativeFrame);
        }
        if (!hasIntersection) {
            return Insets.NONE;
        }
        if (getType() == WindowInsets.Type.ime()) {
            return Insets.of(0, 0, 0, this.mTmpFrame.height());
        }
        if (this.mTmpFrame.equals(relativeFrame)) {
            switch (this.mSideHint) {
                case 2:
                    return Insets.of(0, this.mTmpFrame.height(), 0, 0);
                case 3:
                    return Insets.of(0, 0, this.mTmpFrame.width(), 0);
                case 4:
                    return Insets.of(0, 0, 0, this.mTmpFrame.height());
                default:
                    return Insets.of(this.mTmpFrame.width(), 0, 0, 0);
            }
        }
        if (this.mTmpFrame.width() == relativeFrame.width()) {
            if (this.mTmpFrame.top == relativeFrame.top) {
                return Insets.of(0, this.mTmpFrame.height(), 0, 0);
            }
            if (this.mTmpFrame.bottom == relativeFrame.bottom) {
                return Insets.of(0, 0, 0, this.mTmpFrame.height());
            }
            if (this.mTmpFrame.top == 0) {
                return Insets.of(0, this.mTmpFrame.height(), 0, 0);
            }
        } else if (this.mTmpFrame.height() == relativeFrame.height()) {
            if (this.mTmpFrame.left == relativeFrame.left) {
                return Insets.of(this.mTmpFrame.width(), 0, 0, 0);
            }
            if (this.mTmpFrame.right == relativeFrame.right) {
                return Insets.of(0, 0, this.mTmpFrame.width(), 0);
            }
        }
        return Insets.NONE;
    }

    public Rect[] calculateBoundingRects(Rect relativeFrame, boolean ignoreVisibility) {
        if (!ignoreVisibility && !this.mVisible) {
            return NO_BOUNDING_RECTS;
        }
        Rect frame = getFrame();
        if (this.mBoundingRects == null) {
            if (this.mTmpBoundingRect.setIntersect(frame, relativeFrame)) {
                return new Rect[] {
                    new Rect(
                            this.mTmpBoundingRect.left - relativeFrame.left,
                            this.mTmpBoundingRect.top - relativeFrame.top,
                            this.mTmpBoundingRect.right - relativeFrame.left,
                            this.mTmpBoundingRect.bottom - relativeFrame.top)
                };
            }
            return NO_BOUNDING_RECTS;
        }
        if (getType() == WindowInsets.Type.captionBar()) {
            ArrayList<Rect> validBoundingRects = new ArrayList<>();
            for (Rect boundingRect : this.mBoundingRects) {
                int frameHeight = frame.height();
                this.mTmpBoundingRect.set(boundingRect);
                if (getId() == ID_IME_CAPTION_BAR) {
                    this.mTmpBoundingRect.offset(0, relativeFrame.height() - frameHeight);
                }
                validBoundingRects.add(new Rect(this.mTmpBoundingRect));
            }
            return (Rect[]) validBoundingRects.toArray(new Rect[validBoundingRects.size()]);
        }
        ArrayList<Rect> validBoundingRects2 = new ArrayList<>();
        for (Rect boundingRect2 : this.mBoundingRects) {
            Rect absBoundingRect =
                    new Rect(
                            boundingRect2.left + frame.left,
                            boundingRect2.top + frame.top,
                            boundingRect2.right + frame.left,
                            boundingRect2.bottom + frame.top);
            if (this.mTmpBoundingRect.setIntersect(absBoundingRect, relativeFrame)) {
                validBoundingRects2.add(
                        new Rect(
                                this.mTmpBoundingRect.left - relativeFrame.left,
                                this.mTmpBoundingRect.top - relativeFrame.top,
                                this.mTmpBoundingRect.right - relativeFrame.left,
                                this.mTmpBoundingRect.bottom - relativeFrame.top));
            }
        }
        if (validBoundingRects2.isEmpty()) {
            return NO_BOUNDING_RECTS;
        }
        return (Rect[]) validBoundingRects2.toArray(new Rect[validBoundingRects2.size()]);
    }

    private static boolean getIntersection(Rect a, Rect b, Rect out) {
        if (a.left <= b.right && b.left <= a.right && a.top <= b.bottom && b.top <= a.bottom) {
            out.left = Math.max(a.left, b.left);
            out.top = Math.max(a.top, b.top);
            out.right = Math.min(a.right, b.right);
            out.bottom = Math.min(a.bottom, b.bottom);
            return true;
        }
        out.setEmpty();
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
        if (insets.bottom != 0) {
            return 4;
        }
        return 5;
    }

    static String sideToString(int side) {
        switch (side) {
            case 0:
                return KeyProperties.DIGEST_NONE;
            case 1:
                return "LEFT";
            case 2:
                return "TOP";
            case 3:
                return "RIGHT";
            case 4:
                return "BOTTOM";
            default:
                return "UNKNOWN:" + side;
        }
    }

    public static int createId(Object owner, int index, int type) {
        if (index < 0 || index >= 2048) {
            throw new IllegalArgumentException();
        }
        return ((System.identityHashCode(owner) % 65536) << 16)
                + (index << 5)
                + WindowInsets.Type.indexOf(type);
    }

    public static int getIndex(int id) {
        return (65535 & id) >> 5;
    }

    public static int getType(int id) {
        return 1 << (id & 31);
    }

    public static String flagsToString(int flags) {
        StringJoiner joiner = new StringJoiner(NtpTrustedTime.NTP_SETTING_SERVER_NAME_DELIMITER);
        if ((flags & 1) != 0) {
            joiner.add("SUPPRESS_SCRIM");
        }
        if ((flags & 2) != 0) {
            joiner.add("INSETS_ROUNDED_CORNER");
        }
        if ((flags & 4) != 0) {
            joiner.add("FORCE_CONSUMING");
        }
        if ((flags & 8) != 0) {
            joiner.add("ANIMATE_RESIZING");
        }
        if ((flags & 16) != 0) {
            joiner.add("FORCE_CONSUMING_OPAQUE_CAPTION_BAR");
        }
        return joiner.toString();
    }

    public void dumpDebug(ProtoOutputStream proto, long fieldId) {
        long token = proto.start(fieldId);
        if (!com.android.internal.hidden_from_bootclasspath.android.os.Flags
                .androidOsBuildVanillaIceCream()) {
            proto.write(1138166333441L, WindowInsets.Type.toString(this.mType));
        }
        this.mFrame.dumpDebug(proto, 1146756268034L);
        if (this.mVisibleFrame != null) {
            this.mVisibleFrame.dumpDebug(proto, 1146756268035L);
        }
        proto.write(1133871366148L, this.mVisible);
        proto.write(1120986464261L, this.mType);
        proto.end(token);
    }

    public void dump(String prefix, PrintWriter pw) {
        pw.print(prefix);
        pw.print("InsetsSource id=");
        pw.print(Integer.toHexString(this.mId));
        pw.print(" type=");
        pw.print(WindowInsets.Type.toString(this.mType));
        pw.print(" frame=");
        pw.print(this.mFrame.toShortString());
        if (this.mVisibleFrame != null) {
            pw.print(" visibleFrame=");
            pw.print(this.mVisibleFrame.toShortString());
        }
        pw.print(" visible=");
        pw.print(this.mVisible);
        pw.print(" flags=");
        pw.print(flagsToString(this.mFlags));
        pw.print(" sideHint=");
        pw.print(sideToString(this.mSideHint));
        pw.print(" boundingRects=");
        pw.print(Arrays.toString(this.mBoundingRects));
        pw.println();
    }

    public boolean equals(Object o) {
        return equals(o, false);
    }

    public boolean equals(Object o, boolean excludeInvisibleImeFrames) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InsetsSource that = (InsetsSource) o;
        if ((CoreRune.FW_MINIMIZED_IME_INSET_ANIM
                        && !this.mMinimizedInsetHint.equals(that.mMinimizedInsetHint))
                || this.mId != that.mId
                || this.mType != that.mType
                || this.mVisible != that.mVisible
                || this.mFlags != that.mFlags
                || this.mSideHint != that.mSideHint) {
            return false;
        }
        if (excludeInvisibleImeFrames && !this.mVisible && this.mType == WindowInsets.Type.ime()) {
            return true;
        }
        if (!Objects.equals(this.mVisibleFrame, that.mVisibleFrame)
                || !this.mFrame.equals(that.mFrame)) {
            return false;
        }
        return Arrays.equals(this.mBoundingRects, that.mBoundingRects);
    }

    public int hashCode() {
        return Objects.hash(
                Integer.valueOf(this.mId),
                Integer.valueOf(this.mType),
                this.mFrame,
                this.mVisibleFrame,
                Boolean.valueOf(this.mVisible),
                Integer.valueOf(this.mFlags),
                Integer.valueOf(this.mSideHint),
                Integer.valueOf(Arrays.hashCode(this.mBoundingRects)));
    }

    public InsetsSource(Parcel in) {
        this.mMinimizedInsetHint = new Rect();
        this.mSideHint = 0;
        this.mTmpFrame = new Rect();
        this.mTmpBoundingRect = new Rect();
        this.mId = in.readInt();
        this.mType = in.readInt();
        this.mFrame = Rect.CREATOR.createFromParcel(in);
        if (in.readInt() != 0) {
            this.mVisibleFrame = Rect.CREATOR.createFromParcel(in);
        } else {
            this.mVisibleFrame = null;
        }
        this.mVisible = in.readBoolean();
        this.mFlags = in.readInt();
        this.mSideHint = in.readInt();
        this.mBoundingRects = (Rect[]) in.createTypedArray(Rect.CREATOR);
        if (CoreRune.FW_MINIMIZED_IME_INSET_ANIM) {
            this.mMinimizedInsetHint = Rect.CREATOR.createFromParcel(in);
        }
    }

    int getSideHint() {
        return this.mSideHint;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeInt(this.mType);
        this.mFrame.writeToParcel(dest, 0);
        if (this.mVisibleFrame != null) {
            dest.writeInt(1);
            this.mVisibleFrame.writeToParcel(dest, 0);
        } else {
            dest.writeInt(0);
        }
        dest.writeBoolean(this.mVisible);
        dest.writeInt(this.mFlags);
        dest.writeInt(this.mSideHint);
        dest.writeTypedArray(this.mBoundingRects, flags);
        if (CoreRune.FW_MINIMIZED_IME_INSET_ANIM) {
            this.mMinimizedInsetHint.writeToParcel(dest, 0);
        }
    }

    public String toString() {
        return "InsetsSource: {"
                + Integer.toHexString(this.mId)
                + " mType="
                + WindowInsets.Type.toString(this.mType)
                + " mFrame="
                + this.mFrame.toShortString()
                + " mVisible="
                + this.mVisible
                + " mFlags="
                + flagsToString(this.mFlags)
                + " mSideHint="
                + sideToString(this.mSideHint)
                + " mBoundingRects="
                + Arrays.toString(this.mBoundingRects)
                + "}";
    }
}
