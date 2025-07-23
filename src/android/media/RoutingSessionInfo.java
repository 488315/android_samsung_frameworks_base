package android.media;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.text.TextUtils;
import com.android.internal.util.Preconditions;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;

/* loaded from: classes2.dex */
public final class RoutingSessionInfo implements Parcelable {
    public static final Parcelable.Creator<RoutingSessionInfo> CREATOR = new Parcelable.Creator<RoutingSessionInfo>() { // from class: android.media.RoutingSessionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RoutingSessionInfo createFromParcel(Parcel parcel) {
            return new RoutingSessionInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RoutingSessionInfo[] newArray(int i) {
            return new RoutingSessionInfo[i];
        }
    };
    private static final String KEY_GROUP_ROUTE = "androidx.mediarouter.media.KEY_GROUP_ROUTE";
    private static final String KEY_VOLUME_HANDLING = "volumeHandling";
    public static final int TRANSFER_REASON_APP = 2;
    public static final int TRANSFER_REASON_FALLBACK = 0;
    public static final int TRANSFER_REASON_SYSTEM_REQUEST = 1;
    final String mClientPackageName;
    final Bundle mControlHints;
    final List<String> mDeselectableRoutes;
    final boolean mIsSystemSession;
    final CharSequence mName;
    final String mOriginalId;
    final String mOwnerPackageName;
    final String mProviderId;
    final List<String> mSelectableRoutes;
    final List<String> mSelectedRoutes;
    final String mTransferInitiatorPackageName;
    final UserHandle mTransferInitiatorUserHandle;
    final int mTransferReason;
    final List<String> mTransferableRoutes;
    final int mVolume;
    final int mVolumeHandling;
    final int mVolumeMax;

    @Retention(RetentionPolicy.SOURCE)
    public @interface TransferReason {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    RoutingSessionInfo(Builder builder) {
        Objects.requireNonNull(builder, "builder must not be null.");
        this.mOriginalId = builder.mOriginalId;
        this.mName = builder.mName;
        this.mOwnerPackageName = builder.mOwnerPackageName;
        this.mClientPackageName = builder.mClientPackageName;
        this.mProviderId = builder.mProviderId;
        this.mSelectedRoutes = Collections.unmodifiableList(convertToUniqueRouteIds(builder.mSelectedRoutes));
        this.mSelectableRoutes = Collections.unmodifiableList(convertToUniqueRouteIds(builder.mSelectableRoutes));
        this.mDeselectableRoutes = Collections.unmodifiableList(convertToUniqueRouteIds(builder.mDeselectableRoutes));
        this.mTransferableRoutes = Collections.unmodifiableList(convertToUniqueRouteIds(builder.mTransferableRoutes));
        this.mVolumeMax = builder.mVolumeMax;
        this.mVolume = builder.mVolume;
        this.mIsSystemSession = builder.mIsSystemSession;
        int i = builder.mVolumeHandling;
        this.mVolumeHandling = i;
        this.mControlHints = updateVolumeHandlingInHints(builder.mControlHints, i);
        this.mTransferReason = builder.mTransferReason;
        this.mTransferInitiatorUserHandle = builder.mTransferInitiatorUserHandle;
        this.mTransferInitiatorPackageName = builder.mTransferInitiatorPackageName;
    }

    RoutingSessionInfo(Parcel parcel) {
        this.mOriginalId = parcel.readString();
        Preconditions.checkArgument(!TextUtils.isEmpty(r0));
        this.mName = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mOwnerPackageName = parcel.readString();
        this.mClientPackageName = ensureString(parcel.readString());
        this.mProviderId = parcel.readString();
        this.mSelectedRoutes = ensureList(parcel.createStringArrayList());
        Preconditions.checkArgument(!r0.isEmpty());
        this.mSelectableRoutes = ensureList(parcel.createStringArrayList());
        this.mDeselectableRoutes = ensureList(parcel.createStringArrayList());
        this.mTransferableRoutes = ensureList(parcel.createStringArrayList());
        this.mVolumeHandling = parcel.readInt();
        this.mVolumeMax = parcel.readInt();
        this.mVolume = parcel.readInt();
        this.mControlHints = parcel.readBundle();
        this.mIsSystemSession = parcel.readBoolean();
        this.mTransferReason = parcel.readInt();
        this.mTransferInitiatorUserHandle = UserHandle.readFromParcel(parcel);
        this.mTransferInitiatorPackageName = parcel.readString();
    }

    private static Bundle updateVolumeHandlingInHints(Bundle bundle, int i) {
        Bundle bundle2;
        if (bundle == null || !bundle.containsKey(KEY_GROUP_ROUTE) || (bundle2 = bundle.getBundle(KEY_GROUP_ROUTE)) == null || !bundle2.containsKey(KEY_VOLUME_HANDLING) || i == bundle2.getInt(KEY_VOLUME_HANDLING)) {
            return bundle;
        }
        Bundle bundle3 = new Bundle(bundle2);
        bundle3.putInt(KEY_VOLUME_HANDLING, i);
        Bundle bundle4 = new Bundle(bundle);
        bundle4.putBundle(KEY_GROUP_ROUTE, bundle3);
        return bundle4;
    }

    private static String ensureString(String str) {
        return str != null ? str : "";
    }

    private static <T> List<T> ensureList(List<? extends T> list) {
        if (list != null) {
            return Collections.unmodifiableList(list);
        }
        return Collections.EMPTY_LIST;
    }

    public String getId() {
        if (!TextUtils.isEmpty(this.mProviderId)) {
            return MediaRouter2Utils.toUniqueId(this.mProviderId, this.mOriginalId);
        }
        return this.mOriginalId;
    }

    public CharSequence getName() {
        return this.mName;
    }

    public String getOriginalId() {
        return this.mOriginalId;
    }

    public String getOwnerPackageName() {
        return this.mOwnerPackageName;
    }

    public String getClientPackageName() {
        return this.mClientPackageName;
    }

    public String getProviderId() {
        return this.mProviderId;
    }

    public List<String> getSelectedRoutes() {
        return this.mSelectedRoutes;
    }

    public List<String> getSelectableRoutes() {
        return this.mSelectableRoutes;
    }

    public List<String> getDeselectableRoutes() {
        return this.mDeselectableRoutes;
    }

    public List<String> getTransferableRoutes() {
        return this.mTransferableRoutes;
    }

    public int getVolumeHandling() {
        return this.mVolumeHandling;
    }

    public int getVolumeMax() {
        return this.mVolumeMax;
    }

    public int getVolume() {
        return this.mVolume;
    }

    public Bundle getControlHints() {
        return this.mControlHints;
    }

    public boolean isSystemSession() {
        return this.mIsSystemSession;
    }

    public int getTransferReason() {
        return this.mTransferReason;
    }

    public UserHandle getTransferInitiatorUserHandle() {
        return this.mTransferInitiatorUserHandle;
    }

    public String getTransferInitiatorPackageName() {
        return this.mTransferInitiatorPackageName;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mOriginalId);
        parcel.writeCharSequence(this.mName);
        parcel.writeString(this.mOwnerPackageName);
        parcel.writeString(this.mClientPackageName);
        parcel.writeString(this.mProviderId);
        parcel.writeStringList(this.mSelectedRoutes);
        parcel.writeStringList(this.mSelectableRoutes);
        parcel.writeStringList(this.mDeselectableRoutes);
        parcel.writeStringList(this.mTransferableRoutes);
        parcel.writeInt(this.mVolumeHandling);
        parcel.writeInt(this.mVolumeMax);
        parcel.writeInt(this.mVolume);
        parcel.writeBundle(this.mControlHints);
        parcel.writeBoolean(this.mIsSystemSession);
        parcel.writeInt(this.mTransferReason);
        UserHandle.writeToParcel(this.mTransferInitiatorUserHandle, parcel);
        parcel.writeString(this.mTransferInitiatorPackageName);
    }

    public void dump(PrintWriter printWriter, String str) {
        printWriter.println(str + "RoutingSessionInfo");
        String str2 = str + "  ";
        printWriter.println(str2 + "mOriginalId=" + this.mOriginalId);
        printWriter.println(str2 + "mName=" + ((Object) this.mName));
        printWriter.println(str2 + "mOwnerPackageName=" + this.mOwnerPackageName);
        printWriter.println(str2 + "mClientPackageName=" + this.mClientPackageName);
        printWriter.println(str2 + "mProviderId=" + this.mProviderId);
        printWriter.println(str2 + "mSelectedRoutes=" + this.mSelectedRoutes);
        printWriter.println(str2 + "mSelectableRoutes=" + this.mSelectableRoutes);
        printWriter.println(str2 + "mDeselectableRoutes=" + this.mDeselectableRoutes);
        printWriter.println(str2 + "mTransferableRoutes=" + this.mTransferableRoutes);
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append(MediaRoute2Info.getVolumeString(this.mVolume, this.mVolumeMax, this.mVolumeHandling));
        printWriter.println(sb.toString());
        printWriter.println(str2 + "mControlHints=" + this.mControlHints);
        printWriter.println(str2 + "mIsSystemSession=" + this.mIsSystemSession);
        printWriter.println(str2 + "mTransferReason=" + this.mTransferReason);
        printWriter.println(str2 + "mtransferInitiatorUserHandle=" + this.mTransferInitiatorUserHandle);
        printWriter.println(str2 + "mtransferInitiatorPackageName=" + this.mTransferInitiatorPackageName);
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        RoutingSessionInfo routingSessionInfo = (RoutingSessionInfo) obj;
        return Objects.equals(this.mOriginalId, routingSessionInfo.mOriginalId) && Objects.equals(this.mName, routingSessionInfo.mName) && Objects.equals(this.mOwnerPackageName, routingSessionInfo.mOwnerPackageName) && Objects.equals(this.mClientPackageName, routingSessionInfo.mClientPackageName) && Objects.equals(this.mProviderId, routingSessionInfo.mProviderId) && Objects.equals(this.mSelectedRoutes, routingSessionInfo.mSelectedRoutes) && Objects.equals(this.mSelectableRoutes, routingSessionInfo.mSelectableRoutes) && Objects.equals(this.mDeselectableRoutes, routingSessionInfo.mDeselectableRoutes) && Objects.equals(this.mTransferableRoutes, routingSessionInfo.mTransferableRoutes) && this.mVolumeHandling == routingSessionInfo.mVolumeHandling && this.mVolumeMax == routingSessionInfo.mVolumeMax && this.mVolume == routingSessionInfo.mVolume && this.mTransferReason == routingSessionInfo.mTransferReason && Objects.equals(this.mTransferInitiatorUserHandle, routingSessionInfo.mTransferInitiatorUserHandle) && Objects.equals(this.mTransferInitiatorPackageName, routingSessionInfo.mTransferInitiatorPackageName);
    }

    public int hashCode() {
        return Objects.hash(this.mOriginalId, this.mName, this.mOwnerPackageName, this.mClientPackageName, this.mProviderId, this.mSelectedRoutes, this.mSelectableRoutes, this.mDeselectableRoutes, this.mTransferableRoutes, Integer.valueOf(this.mVolumeMax), Integer.valueOf(this.mVolumeHandling), Integer.valueOf(this.mVolume), Integer.valueOf(this.mTransferReason), this.mTransferInitiatorUserHandle, this.mTransferInitiatorPackageName);
    }

    public String toString() {
        return "RoutingSessionInfo{ sessionId=" + getId() + ", name=" + getName() + ", clientPackageName=" + getClientPackageName() + ", selectedRoutes={" + String.join(",", getSelectedRoutes()) + "}, selectableRoutes={" + String.join(",", getSelectableRoutes()) + "}, deselectableRoutes={" + String.join(",", getDeselectableRoutes()) + "}, transferableRoutes={" + String.join(",", getTransferableRoutes()) + "}, " + MediaRoute2Info.getVolumeString(this.mVolume, this.mVolumeMax, this.mVolumeHandling) + ", transferReason=" + getTransferReason() + ", transferInitiatorUserHandle=" + getTransferInitiatorUserHandle() + ", transferInitiatorPackageName=" + getTransferInitiatorPackageName() + " }";
    }

    private List<String> convertToUniqueRouteIds(List<String> list) {
        Objects.requireNonNull(list, "RouteIds cannot be null.");
        if (TextUtils.isEmpty(this.mProviderId)) {
            return new ArrayList(list);
        }
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(MediaRouter2Utils.toUniqueId(this.mProviderId, it.next()));
        }
        return arrayList;
    }

    public static final class Builder {
        private String mClientPackageName;
        private Bundle mControlHints;
        private final List<String> mDeselectableRoutes;
        private boolean mIsSystemSession;
        private CharSequence mName;
        private final String mOriginalId;
        private String mOwnerPackageName;
        private String mProviderId;
        private final List<String> mSelectableRoutes;
        private final List<String> mSelectedRoutes;
        private String mTransferInitiatorPackageName;
        private UserHandle mTransferInitiatorUserHandle;
        private int mTransferReason;
        private final List<String> mTransferableRoutes;
        private int mVolume;
        private int mVolumeHandling;
        private int mVolumeMax;

        public Builder(String str, String str2) {
            this.mVolumeHandling = 0;
            this.mTransferReason = 0;
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("id must not be empty");
            }
            this.mOriginalId = str;
            this.mClientPackageName = (String) Objects.requireNonNull(str2, "clientPackageName must not be null");
            this.mSelectedRoutes = new ArrayList();
            this.mSelectableRoutes = new ArrayList();
            this.mDeselectableRoutes = new ArrayList();
            this.mTransferableRoutes = new ArrayList();
        }

        public Builder(RoutingSessionInfo routingSessionInfo) {
            this(routingSessionInfo, routingSessionInfo.getOriginalId());
        }

        public Builder(RoutingSessionInfo routingSessionInfo, String str) {
            this.mVolumeHandling = 0;
            this.mTransferReason = 0;
            Objects.requireNonNull(routingSessionInfo, "sessionInfo must not be null");
            this.mOriginalId = str;
            this.mName = routingSessionInfo.mName;
            this.mClientPackageName = routingSessionInfo.mClientPackageName;
            this.mProviderId = routingSessionInfo.mProviderId;
            ArrayList arrayList = new ArrayList(routingSessionInfo.mSelectedRoutes);
            this.mSelectedRoutes = arrayList;
            ArrayList arrayList2 = new ArrayList(routingSessionInfo.mSelectableRoutes);
            this.mSelectableRoutes = arrayList2;
            ArrayList arrayList3 = new ArrayList(routingSessionInfo.mDeselectableRoutes);
            this.mDeselectableRoutes = arrayList3;
            ArrayList arrayList4 = new ArrayList(routingSessionInfo.mTransferableRoutes);
            this.mTransferableRoutes = arrayList4;
            if (this.mProviderId != null) {
                arrayList.replaceAll(new UnaryOperator() { // from class: android.media.RoutingSessionInfo$Builder$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return MediaRouter2Utils.getOriginalId((String) obj);
                    }
                });
                arrayList2.replaceAll(new UnaryOperator() { // from class: android.media.RoutingSessionInfo$Builder$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return MediaRouter2Utils.getOriginalId((String) obj);
                    }
                });
                arrayList3.replaceAll(new UnaryOperator() { // from class: android.media.RoutingSessionInfo$Builder$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return MediaRouter2Utils.getOriginalId((String) obj);
                    }
                });
                arrayList4.replaceAll(new UnaryOperator() { // from class: android.media.RoutingSessionInfo$Builder$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return MediaRouter2Utils.getOriginalId((String) obj);
                    }
                });
            }
            this.mVolumeHandling = routingSessionInfo.mVolumeHandling;
            this.mVolumeMax = routingSessionInfo.mVolumeMax;
            this.mVolume = routingSessionInfo.mVolume;
            this.mControlHints = routingSessionInfo.mControlHints;
            this.mIsSystemSession = routingSessionInfo.mIsSystemSession;
            this.mTransferReason = routingSessionInfo.mTransferReason;
            this.mTransferInitiatorUserHandle = routingSessionInfo.mTransferInitiatorUserHandle;
            this.mTransferInitiatorPackageName = routingSessionInfo.mTransferInitiatorPackageName;
        }

        public Builder setName(CharSequence charSequence) {
            this.mName = charSequence;
            return this;
        }

        public Builder setOwnerPackageName(String str) {
            this.mOwnerPackageName = str;
            return this;
        }

        public Builder setClientPackageName(String str) {
            this.mClientPackageName = str;
            return this;
        }

        public Builder setProviderId(String str) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("providerId must not be empty");
            }
            this.mProviderId = str;
            return this;
        }

        public Builder clearSelectedRoutes() {
            this.mSelectedRoutes.clear();
            return this;
        }

        public Builder addSelectedRoute(String str) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("routeId must not be empty");
            }
            this.mSelectedRoutes.add(str);
            return this;
        }

        public Builder removeSelectedRoute(String str) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("routeId must not be empty");
            }
            this.mSelectedRoutes.remove(str);
            return this;
        }

        public Builder clearSelectableRoutes() {
            this.mSelectableRoutes.clear();
            return this;
        }

        public Builder addSelectableRoute(String str) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("routeId must not be empty");
            }
            this.mSelectableRoutes.add(str);
            return this;
        }

        public Builder removeSelectableRoute(String str) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("routeId must not be empty");
            }
            this.mSelectableRoutes.remove(str);
            return this;
        }

        public Builder clearDeselectableRoutes() {
            this.mDeselectableRoutes.clear();
            return this;
        }

        public Builder addDeselectableRoute(String str) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("routeId must not be empty");
            }
            this.mDeselectableRoutes.add(str);
            return this;
        }

        public Builder removeDeselectableRoute(String str) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("routeId must not be empty");
            }
            this.mDeselectableRoutes.remove(str);
            return this;
        }

        public Builder clearTransferableRoutes() {
            this.mTransferableRoutes.clear();
            return this;
        }

        public Builder addTransferableRoute(String str) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("routeId must not be empty");
            }
            this.mTransferableRoutes.add(str);
            return this;
        }

        public Builder removeTransferableRoute(String str) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("routeId must not be empty");
            }
            this.mTransferableRoutes.remove(str);
            return this;
        }

        public Builder setVolumeHandling(int i) {
            this.mVolumeHandling = i;
            return this;
        }

        public Builder setVolumeMax(int i) {
            this.mVolumeMax = i;
            return this;
        }

        public Builder setVolume(int i) {
            this.mVolume = i;
            return this;
        }

        public Builder setControlHints(Bundle bundle) {
            this.mControlHints = bundle;
            return this;
        }

        public Builder setSystemSession(boolean z) {
            this.mIsSystemSession = z;
            return this;
        }

        public Builder setTransferReason(int i) {
            this.mTransferReason = i;
            return this;
        }

        public Builder setTransferInitiator(UserHandle userHandle, String str) {
            this.mTransferInitiatorUserHandle = userHandle;
            this.mTransferInitiatorPackageName = str;
            return this;
        }

        public RoutingSessionInfo build() {
            if (this.mSelectedRoutes.isEmpty()) {
                throw new IllegalArgumentException("selectedRoutes must not be empty");
            }
            return new RoutingSessionInfo(this);
        }
    }
}
