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
    public static final Parcelable.Creator<OverlayInfoExt> CREATOR = new Parcelable.Creator<OverlayInfoExt>() { // from class: android.content.om.OverlayInfoExt.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OverlayInfoExt createFromParcel(Parcel parcel) {
            return new OverlayInfoExt(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public OverlayInfoExt[] newArray(int i) {
            return new OverlayInfoExt[i];
        }
    };
    private static final String TAG = "OverlayInfo";

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public OverlayInfoExt(int i, int i2, OverlayInfo overlayInfo) {
        this.category = i;
        this.configFlags = i2;
        this.info = overlayInfo;
    }

    public OverlayInfoExt(Parcel parcel) {
        this.configFlags = parcel.readInt();
        this.category = parcel.readInt();
        this.info = new OverlayInfo(parcel);
    }

    public static OverlayInfoExt initFromInfo(OverlayInfo overlayInfo) {
        if (overlayInfo.category != null) {
            String[] split = overlayInfo.category.split(":");
            if (split.length == 3) {
                try {
                    return new OverlayInfoExt(Integer.parseInt(split[1]), Integer.parseInt(split[2]), overlayInfo);
                } catch (NumberFormatException unused) {
                }
            }
        }
        return null;
    }

    public static String getFormattedCategory(String str, int i, int i2) {
        return TextUtils.emptyIfNull(str) + ":" + i + ":" + i2;
    }

    public static boolean isOverlayInfoExt(OverlayInfo overlayInfo) {
        if (overlayInfo.baseCodePath.startsWith("/data/overlays") && overlayInfo.category != null) {
            String[] split = overlayInfo.category.split(":");
            if (split.length == 3) {
                try {
                    int parseInt = Integer.parseInt(split[1]);
                    return parseInt == 0 || parseInt == 1 || parseInt == 2;
                } catch (NumberFormatException unused) {
                    Slog.i(TAG, "Ignore");
                }
            }
        }
        return false;
    }

    public static boolean isOverlayInfoExtOfCategory(OverlayInfoExt overlayInfoExt, int i) {
        return overlayInfoExt != null && isOverlayInfoExtOfCategory(overlayInfoExt.info, i);
    }

    public static boolean isOverlayInfoExtOfCategory(OverlayInfo overlayInfo, int i) {
        if (overlayInfo != null && overlayInfo.category != null) {
            String[] split = overlayInfo.category.split(":");
            if (split.length == 3) {
                try {
                    return Integer.parseInt(split[1]) == i;
                } catch (NumberFormatException unused) {
                    Slog.i(TAG, "Ignore");
                }
            }
        }
        return false;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.configFlags);
        parcel.writeInt(this.category);
        this.info.writeToParcel(parcel, i);
    }

    public OverlayIdentifier getOverlayIdentifier() {
        return new OverlayIdentifier(this.info.packageName);
    }

    public String getTargetPackageName() {
        return this.info.targetPackageName;
    }

    public String toString() {
        return "OverlayInfoExt{configFlags=" + this.configFlags + ", category=" + this.category + ", info=" + this.info + '}';
    }

    public static final class Category {
        public static final int INDEPENDENT = 3;
        public static final int LOCALE = 1;
        public static final int THEME = 0;
        public static final int THEME_PARK = 2;

        private Category() {
        }
    }
}
