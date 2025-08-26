package android.media;

import android.media.MediaRoute2Info;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArrayMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class MediaRoute2ProviderInfo implements Parcelable {
    public static final Parcelable.Creator<MediaRoute2ProviderInfo> CREATOR = new Parcelable.Creator<MediaRoute2ProviderInfo>() { // from class: android.media.MediaRoute2ProviderInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaRoute2ProviderInfo createFromParcel(Parcel parcel) {
            return new MediaRoute2ProviderInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MediaRoute2ProviderInfo[] newArray(int i) {
            return new MediaRoute2ProviderInfo[i];
        }
    };
    final ArrayMap<String, MediaRoute2Info> mRoutes;
    final String mUniqueId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    MediaRoute2ProviderInfo(Builder builder) {
        Objects.requireNonNull(builder, "builder must not be null.");
        this.mUniqueId = builder.mUniqueId;
        this.mRoutes = builder.mRoutes;
    }

    MediaRoute2ProviderInfo(Parcel parcel) {
        this.mUniqueId = parcel.readString();
        ArrayMap<String, MediaRoute2Info> arrayMapCreateTypedArrayMap = parcel.createTypedArrayMap(MediaRoute2Info.CREATOR);
        this.mRoutes = arrayMapCreateTypedArrayMap == null ? ArrayMap.EMPTY : arrayMapCreateTypedArrayMap;
    }

    public boolean isValid() {
        if (this.mUniqueId == null) {
            return false;
        }
        int size = this.mRoutes.size();
        for (int i = 0; i < size; i++) {
            MediaRoute2Info mediaRoute2InfoValueAt = this.mRoutes.valueAt(i);
            if (mediaRoute2InfoValueAt == null || !mediaRoute2InfoValueAt.isValid()) {
                return false;
            }
        }
        return true;
    }

    public String getUniqueId() {
        return this.mUniqueId;
    }

    public MediaRoute2Info getRoute(String str) {
        return this.mRoutes.get(Objects.requireNonNull(str, "routeId must not be null"));
    }

    public Collection<MediaRoute2Info> getRoutes() {
        return this.mRoutes.values();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mUniqueId);
        parcel.writeTypedArrayMap(this.mRoutes, i);
    }

    public String toString() {
        return "MediaRouteProviderInfo { uniqueId=" + this.mUniqueId + ", routes=" + Arrays.toString(getRoutes().toArray()) + " }";
    }

    public static final class Builder {
        final ArrayMap<String, MediaRoute2Info> mRoutes;
        String mUniqueId;

        public Builder() {
            this.mRoutes = new ArrayMap<>();
        }

        public Builder(MediaRoute2ProviderInfo mediaRoute2ProviderInfo) {
            Objects.requireNonNull(mediaRoute2ProviderInfo, "descriptor must not be null");
            this.mUniqueId = mediaRoute2ProviderInfo.mUniqueId;
            this.mRoutes = new ArrayMap<>(mediaRoute2ProviderInfo.mRoutes);
        }

        public Builder setUniqueId(String str, String str2) {
            if (TextUtils.equals(this.mUniqueId, str2)) {
                return this;
            }
            this.mUniqueId = str2;
            ArrayMap<? extends String, ? extends MediaRoute2Info> arrayMap = new ArrayMap<>();
            Iterator<Map.Entry<String, MediaRoute2Info>> it = this.mRoutes.entrySet().iterator();
            while (it.hasNext()) {
                MediaRoute2Info mediaRoute2InfoBuild = new MediaRoute2Info.Builder(it.next().getValue()).setProviderPackageName(str).setProviderId(this.mUniqueId).build();
                arrayMap.put(mediaRoute2InfoBuild.getOriginalId(), mediaRoute2InfoBuild);
            }
            this.mRoutes.clear();
            this.mRoutes.putAll(arrayMap);
            return this;
        }

        public Builder setSystemRouteProvider(boolean z) {
            int size = this.mRoutes.size();
            for (int i = 0; i < size; i++) {
                MediaRoute2Info mediaRoute2InfoValueAt = this.mRoutes.valueAt(i);
                if (mediaRoute2InfoValueAt.isSystemRoute() != z) {
                    this.mRoutes.setValueAt(i, new MediaRoute2Info.Builder(mediaRoute2InfoValueAt).setSystemRoute(z).build());
                }
            }
            return this;
        }

        public Builder addRoute(MediaRoute2Info mediaRoute2Info) {
            Objects.requireNonNull(mediaRoute2Info, "route must not be null");
            if (this.mRoutes.containsKey(mediaRoute2Info.getOriginalId())) {
                throw new IllegalArgumentException("A route with the same id is already added");
            }
            if (this.mUniqueId != null) {
                this.mRoutes.put(mediaRoute2Info.getOriginalId(), new MediaRoute2Info.Builder(mediaRoute2Info).setProviderId(this.mUniqueId).build());
                return this;
            }
            this.mRoutes.put(mediaRoute2Info.getOriginalId(), mediaRoute2Info);
            return this;
        }

        public Builder addRoutes(Collection<MediaRoute2Info> collection) {
            Objects.requireNonNull(collection, "routes must not be null");
            if (!collection.isEmpty()) {
                Iterator<MediaRoute2Info> it = collection.iterator();
                while (it.hasNext()) {
                    addRoute(it.next());
                }
            }
            return this;
        }

        public MediaRoute2ProviderInfo build() {
            return new MediaRoute2ProviderInfo(this);
        }
    }
}
