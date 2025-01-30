package android.content.om;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Slog;

/* loaded from: classes.dex */
public final class OverlayInfoExt implements Parcelable {
  private static final String DELIMITER = ":";
  public final int category;
  public final int configFlags;
  public final OverlayInfo info;
  public static final Parcelable.Creator<OverlayInfoExt> CREATOR =
      new Parcelable.Creator<OverlayInfoExt>() { // from class: android.content.om.OverlayInfoExt.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OverlayInfoExt createFromParcel(Parcel in) {
          return new OverlayInfoExt(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OverlayInfoExt[] newArray(int size) {
          return new OverlayInfoExt[size];
        }
      };
  private static final String TAG = OverlayInfo.class.getSimpleName();

  public OverlayInfoExt(int category, int configFlags, OverlayInfo info) {
    this.category = category;
    this.configFlags = configFlags;
    this.info = info;
  }

  public OverlayInfoExt(Parcel in) {
    this.configFlags = in.readInt();
    this.category = in.readInt();
    this.info = new OverlayInfo(in);
  }

  public static OverlayInfoExt initFromInfo(OverlayInfo info) {
    if (info.category != null) {
      String[] splits = info.category.split(":");
      if (splits.length == 3) {
        try {
          int category = Integer.parseInt(splits[1]);
          int configFlags = Integer.parseInt(splits[2]);
          return new OverlayInfoExt(category, configFlags, info);
        } catch (NumberFormatException e) {
          return null;
        }
      }
    }
    return null;
  }

  public static String getFormattedCategory(String overlayCategory, int category, int configFlags) {
    return TextUtils.emptyIfNull(overlayCategory) + ":" + category + ":" + configFlags;
  }

  public static boolean isOverlayInfoExt(OverlayInfo info) {
    if (info.baseCodePath.startsWith("/data/overlays") && info.category != null) {
      String[] splits = info.category.split(":");
      if (splits.length == 3) {
        try {
          int category = Integer.parseInt(splits[1]);
          return category == 0 || category == 1 || category == 2;
        } catch (NumberFormatException e) {
          Slog.m117i(TAG, "Ignore");
        }
      }
    }
    return false;
  }

  public static boolean isOverlayInfoExtOfCategory(OverlayInfoExt overlay, int requestedCategory) {
    return overlay != null && isOverlayInfoExtOfCategory(overlay.info, requestedCategory);
  }

  public static boolean isOverlayInfoExtOfCategory(OverlayInfo info, int requestedCategory) {
    if (info != null && info.category != null) {
      String[] splits = info.category.split(":");
      if (splits.length == 3) {
        try {
          int category = Integer.parseInt(splits[1]);
          if (category != requestedCategory) {
            return false;
          }
          return true;
        } catch (NumberFormatException e) {
          Slog.m117i(TAG, "Ignore");
        }
      }
    }
    return false;
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(this.configFlags);
    dest.writeInt(this.category);
    this.info.writeToParcel(dest, flags);
  }

  public OverlayIdentifier getOverlayIdentifier() {
    return new OverlayIdentifier(this.info.packageName);
  }

  public String getTargetPackageName() {
    return this.info.targetPackageName;
  }

  public String toString() {
    return "OverlayInfoExt{configFlags="
        + this.configFlags
        + ", category="
        + this.category
        + ", info="
        + this.info
        + '}';
  }

  public static final class Category {
    public static final int INDEPENDENT = 3;
    public static final int LOCALE = 1;
    public static final int THEME = 0;
    public static final int THEME_PARK = 2;

    private Category() {}
  }
}
