package android.view;

import android.app.admin.DevicePolicyResources;
import android.graphics.Insets;
import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes4.dex */
public class InsetsFrameProvider implements Parcelable {
  public static final Parcelable.Creator<InsetsFrameProvider> CREATOR =
      new Parcelable.Creator<
          InsetsFrameProvider>() { // from class: android.view.InsetsFrameProvider.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InsetsFrameProvider createFromParcel(Parcel in) {
          return new InsetsFrameProvider(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public InsetsFrameProvider[] newArray(int size) {
          return new InsetsFrameProvider[size];
        }
      };
  public static final int SOURCE_ARBITRARY_RECTANGLE = 3;
  public static final int SOURCE_CONTAINER_BOUNDS = 1;
  public static final int SOURCE_DISPLAY = 0;
  public static final int SOURCE_FRAME = 2;
  private Rect mArbitraryRectangle;
  private int mFlags;
  private final int mId;
  private Insets mInsetsSize;
  private InsetsSizeOverride[] mInsetsSizeOverrides;
  private Insets mMinimalInsetsSizeInDisplayCutoutSafe;
  private int mSource;

  public InsetsFrameProvider(Object owner, int index, int type) {
    this.mSource = 2;
    this.mInsetsSize = null;
    this.mInsetsSizeOverrides = null;
    this.mMinimalInsetsSizeInDisplayCutoutSafe = null;
    this.mId = InsetsSource.createId(owner, index, type);
  }

  public int getId() {
    return this.mId;
  }

  public int getIndex() {
    return InsetsSource.getIndex(this.mId);
  }

  public int getType() {
    return InsetsSource.getType(this.mId);
  }

  public InsetsFrameProvider setSource(int source) {
    this.mSource = source;
    return this;
  }

  public int getSource() {
    return this.mSource;
  }

  public InsetsFrameProvider setFlags(int flags, int mask) {
    this.mFlags = (this.mFlags & (~mask)) | (flags & mask);
    return this;
  }

  public int getFlags() {
    return this.mFlags;
  }

  public InsetsFrameProvider setInsetsSize(Insets insetsSize) {
    this.mInsetsSize = insetsSize;
    return this;
  }

  public Insets getInsetsSize() {
    return this.mInsetsSize;
  }

  public InsetsFrameProvider setArbitraryRectangle(Rect rect) {
    this.mArbitraryRectangle = new Rect(rect);
    return this;
  }

  public Rect getArbitraryRectangle() {
    return this.mArbitraryRectangle;
  }

  public InsetsFrameProvider setInsetsSizeOverrides(InsetsSizeOverride[] insetsSizeOverrides) {
    this.mInsetsSizeOverrides = insetsSizeOverrides;
    return this;
  }

  public InsetsSizeOverride[] getInsetsSizeOverrides() {
    return this.mInsetsSizeOverrides;
  }

  public InsetsFrameProvider setMinimalInsetsSizeInDisplayCutoutSafe(
      Insets minimalInsetsSizeInDisplayCutoutSafe) {
    this.mMinimalInsetsSizeInDisplayCutoutSafe = minimalInsetsSizeInDisplayCutoutSafe;
    return this;
  }

  public Insets getMinimalInsetsSizeInDisplayCutoutSafe() {
    return this.mMinimalInsetsSizeInDisplayCutoutSafe;
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder("InsetsFrameProvider: {");
    sb.append("id=#").append(Integer.toHexString(this.mId));
    sb.append(", index=").append(getIndex());
    sb.append(", type=").append(WindowInsets.Type.toString(getType()));
    sb.append(", source=").append(sourceToString(this.mSource));
    sb.append(", flags=[")
        .append(InsetsSource.flagsToString(this.mFlags))
        .append(NavigationBarInflaterView.SIZE_MOD_END);
    if (this.mInsetsSize != null) {
      sb.append(", insetsSize=").append(this.mInsetsSize);
    }
    if (this.mInsetsSizeOverrides != null) {
      sb.append(", insetsSizeOverrides=").append(Arrays.toString(this.mInsetsSizeOverrides));
    }
    if (this.mArbitraryRectangle != null) {
      sb.append(", mArbitraryRectangle=").append(this.mArbitraryRectangle.toShortString());
    }
    if (this.mMinimalInsetsSizeInDisplayCutoutSafe != null) {
      sb.append(", mMinimalInsetsSizeInDisplayCutoutSafe=")
          .append(this.mMinimalInsetsSizeInDisplayCutoutSafe);
    }
    sb.append("}");
    return sb.toString();
  }

  private static String sourceToString(int source) {
    switch (source) {
      case 0:
        return "DISPLAY";
      case 1:
        return "CONTAINER_BOUNDS";
      case 2:
        return "FRAME";
      case 3:
        return "ARBITRARY_RECTANGLE";
      default:
        return DevicePolicyResources.UNDEFINED;
    }
  }

  public InsetsFrameProvider(Parcel in) {
    this.mSource = 2;
    this.mInsetsSize = null;
    this.mInsetsSizeOverrides = null;
    this.mMinimalInsetsSizeInDisplayCutoutSafe = null;
    this.mId = in.readInt();
    this.mSource = in.readInt();
    this.mFlags = in.readInt();
    this.mInsetsSize = (Insets) in.readTypedObject(Insets.CREATOR);
    this.mInsetsSizeOverrides =
        (InsetsSizeOverride[]) in.createTypedArray(InsetsSizeOverride.CREATOR);
    this.mArbitraryRectangle = (Rect) in.readTypedObject(Rect.CREATOR);
    this.mMinimalInsetsSizeInDisplayCutoutSafe = (Insets) in.readTypedObject(Insets.CREATOR);
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel out, int flags) {
    out.writeInt(this.mId);
    out.writeInt(this.mSource);
    out.writeInt(this.mFlags);
    out.writeTypedObject(this.mInsetsSize, flags);
    out.writeTypedArray(this.mInsetsSizeOverrides, flags);
    out.writeTypedObject(this.mArbitraryRectangle, flags);
    out.writeTypedObject(this.mMinimalInsetsSizeInDisplayCutoutSafe, flags);
  }

  public boolean idEquals(InsetsFrameProvider o) {
    return this.mId == o.mId;
  }

  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InsetsFrameProvider other = (InsetsFrameProvider) o;
    if (this.mId == other.mId
        && this.mSource == other.mSource
        && this.mFlags == other.mFlags
        && Objects.equals(this.mInsetsSize, other.mInsetsSize)
        && Arrays.equals(this.mInsetsSizeOverrides, other.mInsetsSizeOverrides)
        && Objects.equals(this.mArbitraryRectangle, other.mArbitraryRectangle)
        && Objects.equals(
            this.mMinimalInsetsSizeInDisplayCutoutSafe,
            other.mMinimalInsetsSizeInDisplayCutoutSafe)) {
      return true;
    }
    return false;
  }

  public int hashCode() {
    return Objects.hash(
        Integer.valueOf(this.mId),
        Integer.valueOf(this.mSource),
        Integer.valueOf(this.mFlags),
        this.mInsetsSize,
        Integer.valueOf(Arrays.hashCode(this.mInsetsSizeOverrides)),
        this.mArbitraryRectangle,
        this.mMinimalInsetsSizeInDisplayCutoutSafe);
  }

  public static class InsetsSizeOverride implements Parcelable {
    public static final Parcelable.Creator<InsetsSizeOverride> CREATOR =
        new Parcelable.Creator<
            InsetsSizeOverride>() { // from class:
                                    // android.view.InsetsFrameProvider.InsetsSizeOverride.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public InsetsSizeOverride createFromParcel(Parcel in) {
            return new InsetsSizeOverride(in);
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public InsetsSizeOverride[] newArray(int size) {
            return new InsetsSizeOverride[size];
          }
        };
    private final Insets mInsetsSize;
    private final int mWindowType;

    protected InsetsSizeOverride(Parcel in) {
      this.mWindowType = in.readInt();
      this.mInsetsSize = (Insets) in.readTypedObject(Insets.CREATOR);
    }

    public InsetsSizeOverride(int windowType, Insets insetsSize) {
      this.mWindowType = windowType;
      this.mInsetsSize = insetsSize;
    }

    public int getWindowType() {
      return this.mWindowType;
    }

    public Insets getInsetsSize() {
      return this.mInsetsSize;
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
      return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
      out.writeInt(this.mWindowType);
      out.writeTypedObject(this.mInsetsSize, flags);
    }

    public String toString() {
      StringBuilder sb = new StringBuilder(32);
      sb.append("TypedInsetsSize: {");
      sb.append("windowType=")
          .append(
              ViewDebug.intToString(WindowManager.LayoutParams.class, "type", this.mWindowType));
      sb.append(", insetsSize=").append(this.mInsetsSize);
      sb.append("}");
      return sb.toString();
    }

    public int hashCode() {
      return Objects.hash(Integer.valueOf(this.mWindowType), this.mInsetsSize);
    }
  }
}
