package android.app.wallpaper;

import android.app.WallpaperInfo;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public final class WallpaperInstance implements Parcelable {
    public static final Parcelable.Creator<WallpaperInstance> CREATOR = new Parcelable.Creator<WallpaperInstance>() { // from class: android.app.wallpaper.WallpaperInstance.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WallpaperInstance createFromParcel(Parcel parcel) {
            return new WallpaperInstance(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public WallpaperInstance[] newArray(int i) {
            return new WallpaperInstance[i];
        }
    };
    private static final String DEFAULT_ID = "default_id";
    private final WallpaperDescription mDescription;
    private final String mIdOverride;
    private final WallpaperInfo mInfo;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public WallpaperInstance(WallpaperInfo wallpaperInfo, WallpaperDescription wallpaperDescription) {
        this(wallpaperInfo, wallpaperDescription, null);
    }

    public WallpaperInstance(WallpaperInfo wallpaperInfo, WallpaperDescription wallpaperDescription, String str) {
        this.mInfo = wallpaperInfo;
        this.mDescription = wallpaperDescription;
        this.mIdOverride = str;
    }

    public WallpaperInfo getInfo() {
        return this.mInfo;
    }

    public String getId() {
        String str = this.mIdOverride;
        if (str != null) {
            return str;
        }
        if (this.mDescription.getId() != null) {
            return this.mDescription.getId();
        }
        WallpaperInfo wallpaperInfo = this.mInfo;
        if (wallpaperInfo != null) {
            return wallpaperInfo.getComponent().flattenToString();
        }
        return DEFAULT_ID;
    }

    public WallpaperDescription getDescription() {
        return this.mDescription;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof WallpaperInstance) {
            WallpaperInstance wallpaperInstance = (WallpaperInstance) obj;
            WallpaperInfo wallpaperInfo = this.mInfo;
            if (wallpaperInfo == null) {
                return wallpaperInstance.mInfo == null && Objects.equals(getId(), wallpaperInstance.getId());
            }
            if (wallpaperInstance.mInfo != null && Objects.equals(wallpaperInfo.getComponent(), wallpaperInstance.mInfo.getComponent()) && Objects.equals(getId(), wallpaperInstance.getId())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        WallpaperInfo wallpaperInfo = this.mInfo;
        return wallpaperInfo != null ? Objects.hash(wallpaperInfo.getComponent(), getId()) : Objects.hash(getId());
    }

    WallpaperInstance(Parcel parcel) {
        this.mInfo = (WallpaperInfo) parcel.readTypedObject(WallpaperInfo.CREATOR);
        this.mDescription = WallpaperDescription.CREATOR.createFromParcel(parcel);
        this.mIdOverride = parcel.readString8();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mInfo, i);
        this.mDescription.writeToParcel(parcel, i);
        parcel.writeString8(this.mIdOverride);
    }
}
