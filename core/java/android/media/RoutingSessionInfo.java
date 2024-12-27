package android.media;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.text.TextUtils;
import com.android.internal.R;
import com.android.internal.util.Preconditions;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;

/* loaded from: classes2.dex */
public final class RoutingSessionInfo implements Parcelable {
    public static final Parcelable.Creator<RoutingSessionInfo> CREATOR = new Parcelable.Creator<RoutingSessionInfo>() { // from class: android.media.RoutingSessionInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RoutingSessionInfo createFromParcel(Parcel in) {
            return new RoutingSessionInfo(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RoutingSessionInfo[] newArray(int size) {
            return new RoutingSessionInfo[size];
        }
    };
    private static final String KEY_GROUP_ROUTE = "androidx.mediarouter.media.KEY_GROUP_ROUTE";
    private static final String KEY_VOLUME_HANDLING = "volumeHandling";
    private static final String TAG = "RoutingSessionInfo";
    public static final int TRANSFER_REASON_APP = 2;
    public static final int TRANSFER_REASON_FALLBACK = 0;
    public static final int TRANSFER_REASON_SYSTEM_REQUEST = 1;
    final String mClientPackageName;
    final Bundle mControlHints;
    final List<String> mDeselectableRoutes;
    final String mId;
    final boolean mIsSystemSession;
    final CharSequence mName;
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

    RoutingSessionInfo(Builder builder) {
        Objects.requireNonNull(builder, "builder must not be null.");
        this.mId = builder.mId;
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
        boolean volumeAdjustmentForRemoteGroupSessions = Resources.getSystem().getBoolean(R.bool.config_volumeAdjustmentForRemoteGroupSessions);
        this.mVolumeHandling = defineVolumeHandling(this.mIsSystemSession, builder.mVolumeHandling, this.mSelectedRoutes, volumeAdjustmentForRemoteGroupSessions);
        this.mControlHints = updateVolumeHandlingInHints(builder.mControlHints, this.mVolumeHandling);
        this.mTransferReason = builder.mTransferReason;
        this.mTransferInitiatorUserHandle = builder.mTransferInitiatorUserHandle;
        this.mTransferInitiatorPackageName = builder.mTransferInitiatorPackageName;
    }

    RoutingSessionInfo(Parcel src) {
        this.mId = src.readString();
        Preconditions.checkArgument(!TextUtils.isEmpty(this.mId));
        this.mName = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(src);
        this.mOwnerPackageName = src.readString();
        this.mClientPackageName = ensureString(src.readString());
        this.mProviderId = src.readString();
        this.mSelectedRoutes = ensureList(src.createStringArrayList());
        Preconditions.checkArgument(!this.mSelectedRoutes.isEmpty());
        this.mSelectableRoutes = ensureList(src.createStringArrayList());
        this.mDeselectableRoutes = ensureList(src.createStringArrayList());
        this.mTransferableRoutes = ensureList(src.createStringArrayList());
        this.mVolumeHandling = src.readInt();
        this.mVolumeMax = src.readInt();
        this.mVolume = src.readInt();
        this.mControlHints = src.readBundle();
        this.mIsSystemSession = src.readBoolean();
        this.mTransferReason = src.readInt();
        this.mTransferInitiatorUserHandle = UserHandle.readFromParcel(src);
        this.mTransferInitiatorPackageName = src.readString();
    }

    private static Bundle updateVolumeHandlingInHints(Bundle controlHints, int volumeHandling) {
        Bundle groupRoute;
        if (controlHints != null && controlHints.containsKey(KEY_GROUP_ROUTE) && (groupRoute = controlHints.getBundle(KEY_GROUP_ROUTE)) != null && groupRoute.containsKey(KEY_VOLUME_HANDLING) && volumeHandling != groupRoute.getInt(KEY_VOLUME_HANDLING)) {
            Bundle newGroupRoute = new Bundle(groupRoute);
            newGroupRoute.putInt(KEY_VOLUME_HANDLING, volumeHandling);
            Bundle newControlHints = new Bundle(controlHints);
            newControlHints.putBundle(KEY_GROUP_ROUTE, newGroupRoute);
            return newControlHints;
        }
        return controlHints;
    }

    private static int defineVolumeHandling(boolean isSystemSession, int volumeHandling, List<String> selectedRoutes, boolean volumeAdjustmentForRemoteGroupSessions) {
        if (!isSystemSession && !volumeAdjustmentForRemoteGroupSessions && selectedRoutes.size() > 1) {
            return 0;
        }
        return volumeHandling;
    }

    private static String ensureString(String str) {
        return str != null ? str : "";
    }

    private static <T> List<T> ensureList(List<? extends T> list) {
        if (list != null) {
            return Collections.unmodifiableList(list);
        }
        return Collections.emptyList();
    }

    public String getId() {
        if (!TextUtils.isEmpty(this.mProviderId)) {
            return MediaRouter2Utils.toUniqueId(this.mProviderId, this.mId);
        }
        return this.mId;
    }

    public CharSequence getName() {
        return this.mName;
    }

    public String getOriginalId() {
        return this.mId;
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
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeCharSequence(this.mName);
        dest.writeString(this.mOwnerPackageName);
        dest.writeString(this.mClientPackageName);
        dest.writeString(this.mProviderId);
        dest.writeStringList(this.mSelectedRoutes);
        dest.writeStringList(this.mSelectableRoutes);
        dest.writeStringList(this.mDeselectableRoutes);
        dest.writeStringList(this.mTransferableRoutes);
        dest.writeInt(this.mVolumeHandling);
        dest.writeInt(this.mVolumeMax);
        dest.writeInt(this.mVolume);
        dest.writeBundle(this.mControlHints);
        dest.writeBoolean(this.mIsSystemSession);
        dest.writeInt(this.mTransferReason);
        UserHandle.writeToParcel(this.mTransferInitiatorUserHandle, dest);
        dest.writeString(this.mTransferInitiatorPackageName);
    }

    public void dump(PrintWriter pw, String prefix) {
        pw.println(prefix + TAG);
        String indent = prefix + "  ";
        pw.println(indent + "mId=" + this.mId);
        pw.println(indent + "mName=" + ((Object) this.mName));
        pw.println(indent + "mOwnerPackageName=" + this.mOwnerPackageName);
        pw.println(indent + "mClientPackageName=" + this.mClientPackageName);
        pw.println(indent + "mProviderId=" + this.mProviderId);
        pw.println(indent + "mSelectedRoutes=" + this.mSelectedRoutes);
        pw.println(indent + "mSelectableRoutes=" + this.mSelectableRoutes);
        pw.println(indent + "mDeselectableRoutes=" + this.mDeselectableRoutes);
        pw.println(indent + "mTransferableRoutes=" + this.mTransferableRoutes);
        pw.println(indent + MediaRoute2Info.getVolumeString(this.mVolume, this.mVolumeMax, this.mVolumeHandling));
        pw.println(indent + "mControlHints=" + this.mControlHints);
        pw.println(indent + "mIsSystemSession=" + this.mIsSystemSession);
        pw.println(indent + "mTransferReason=" + this.mTransferReason);
        pw.println(indent + "mtransferInitiatorUserHandle=" + this.mTransferInitiatorUserHandle);
        pw.println(indent + "mtransferInitiatorPackageName=" + this.mTransferInitiatorPackageName);
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
        RoutingSessionInfo other = (RoutingSessionInfo) obj;
        if (!Objects.equals(this.mId, other.mId) || !Objects.equals(this.mName, other.mName) || !Objects.equals(this.mOwnerPackageName, other.mOwnerPackageName) || !Objects.equals(this.mClientPackageName, other.mClientPackageName) || !Objects.equals(this.mProviderId, other.mProviderId) || !Objects.equals(this.mSelectedRoutes, other.mSelectedRoutes) || !Objects.equals(this.mSelectableRoutes, other.mSelectableRoutes) || !Objects.equals(this.mDeselectableRoutes, other.mDeselectableRoutes) || !Objects.equals(this.mTransferableRoutes, other.mTransferableRoutes) || this.mVolumeHandling != other.mVolumeHandling || this.mVolumeMax != other.mVolumeMax || this.mVolume != other.mVolume || this.mTransferReason != other.mTransferReason || !Objects.equals(this.mTransferInitiatorUserHandle, other.mTransferInitiatorUserHandle) || !Objects.equals(this.mTransferInitiatorPackageName, other.mTransferInitiatorPackageName)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.mId, this.mName, this.mOwnerPackageName, this.mClientPackageName, this.mProviderId, this.mSelectedRoutes, this.mSelectableRoutes, this.mDeselectableRoutes, this.mTransferableRoutes, Integer.valueOf(this.mVolumeMax), Integer.valueOf(this.mVolumeHandling), Integer.valueOf(this.mVolume), Integer.valueOf(this.mTransferReason), this.mTransferInitiatorUserHandle, this.mTransferInitiatorPackageName);
    }

    public String toString() {
        return "RoutingSessionInfo{ sessionId=" + getId() + ", name=" + getName() + ", clientPackageName=" + getClientPackageName() + ", selectedRoutes={" + String.join(",", getSelectedRoutes()) + "}, selectableRoutes={" + String.join(",", getSelectableRoutes()) + "}, deselectableRoutes={" + String.join(",", getDeselectableRoutes()) + "}, transferableRoutes={" + String.join(",", getTransferableRoutes()) + "}, " + MediaRoute2Info.getVolumeString(this.mVolume, this.mVolumeMax, this.mVolumeHandling) + ", transferReason=" + getTransferReason() + ", transferInitiatorUserHandle=" + getTransferInitiatorUserHandle() + ", transferInitiatorPackageName=" + getTransferInitiatorPackageName() + " }";
    }

    private List<String> convertToUniqueRouteIds(List<String> routeIds) {
        Objects.requireNonNull(routeIds, "RouteIds cannot be null.");
        if (TextUtils.isEmpty(this.mProviderId)) {
            return new ArrayList(routeIds);
        }
        List<String> result = new ArrayList<>();
        for (String routeId : routeIds) {
            result.add(MediaRouter2Utils.toUniqueId(this.mProviderId, routeId));
        }
        return result;
    }

    public static final class Builder {
        private String mClientPackageName;
        private Bundle mControlHints;
        private final List<String> mDeselectableRoutes;
        private final String mId;
        private boolean mIsSystemSession;
        private CharSequence mName;
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

        public Builder(String id, String clientPackageName) {
            this.mVolumeHandling = 0;
            this.mTransferReason = 0;
            if (TextUtils.isEmpty(id)) {
                throw new IllegalArgumentException("id must not be empty");
            }
            this.mId = id;
            this.mClientPackageName = (String) Objects.requireNonNull(clientPackageName, "clientPackageName must not be null");
            this.mSelectedRoutes = new ArrayList();
            this.mSelectableRoutes = new ArrayList();
            this.mDeselectableRoutes = new ArrayList();
            this.mTransferableRoutes = new ArrayList();
        }

        public Builder(RoutingSessionInfo sessionInfo) {
            this.mVolumeHandling = 0;
            this.mTransferReason = 0;
            Objects.requireNonNull(sessionInfo, "sessionInfo must not be null");
            this.mId = sessionInfo.mId;
            this.mName = sessionInfo.mName;
            this.mClientPackageName = sessionInfo.mClientPackageName;
            this.mProviderId = sessionInfo.mProviderId;
            this.mSelectedRoutes = new ArrayList(sessionInfo.mSelectedRoutes);
            this.mSelectableRoutes = new ArrayList(sessionInfo.mSelectableRoutes);
            this.mDeselectableRoutes = new ArrayList(sessionInfo.mDeselectableRoutes);
            this.mTransferableRoutes = new ArrayList(sessionInfo.mTransferableRoutes);
            if (this.mProviderId != null) {
                this.mSelectedRoutes.replaceAll(new UnaryOperator() { // from class: android.media.RoutingSessionInfo$Builder$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return MediaRouter2Utils.getOriginalId((String) obj);
                    }
                });
                this.mSelectableRoutes.replaceAll(new UnaryOperator() { // from class: android.media.RoutingSessionInfo$Builder$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return MediaRouter2Utils.getOriginalId((String) obj);
                    }
                });
                this.mDeselectableRoutes.replaceAll(new UnaryOperator() { // from class: android.media.RoutingSessionInfo$Builder$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return MediaRouter2Utils.getOriginalId((String) obj);
                    }
                });
                this.mTransferableRoutes.replaceAll(new UnaryOperator() { // from class: android.media.RoutingSessionInfo$Builder$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return MediaRouter2Utils.getOriginalId((String) obj);
                    }
                });
            }
            this.mVolumeHandling = sessionInfo.mVolumeHandling;
            this.mVolumeMax = sessionInfo.mVolumeMax;
            this.mVolume = sessionInfo.mVolume;
            this.mControlHints = sessionInfo.mControlHints;
            this.mIsSystemSession = sessionInfo.mIsSystemSession;
            this.mTransferReason = sessionInfo.mTransferReason;
            this.mTransferInitiatorUserHandle = sessionInfo.mTransferInitiatorUserHandle;
            this.mTransferInitiatorPackageName = sessionInfo.mTransferInitiatorPackageName;
        }

        public Builder setName(CharSequence name) {
            this.mName = name;
            return this;
        }

        public Builder setOwnerPackageName(String packageName) {
            this.mOwnerPackageName = packageName;
            return this;
        }

        public Builder setClientPackageName(String packageName) {
            this.mClientPackageName = packageName;
            return this;
        }

        public Builder setProviderId(String providerId) {
            if (TextUtils.isEmpty(providerId)) {
                throw new IllegalArgumentException("providerId must not be empty");
            }
            this.mProviderId = providerId;
            return this;
        }

        public Builder clearSelectedRoutes() {
            this.mSelectedRoutes.clear();
            return this;
        }

        public Builder addSelectedRoute(String routeId) {
            if (TextUtils.isEmpty(routeId)) {
                throw new IllegalArgumentException("routeId must not be empty");
            }
            this.mSelectedRoutes.add(routeId);
            return this;
        }

        public Builder removeSelectedRoute(String routeId) {
            if (TextUtils.isEmpty(routeId)) {
                throw new IllegalArgumentException("routeId must not be empty");
            }
            this.mSelectedRoutes.remove(routeId);
            return this;
        }

        public Builder clearSelectableRoutes() {
            this.mSelectableRoutes.clear();
            return this;
        }

        public Builder addSelectableRoute(String routeId) {
            if (TextUtils.isEmpty(routeId)) {
                throw new IllegalArgumentException("routeId must not be empty");
            }
            this.mSelectableRoutes.add(routeId);
            return this;
        }

        public Builder removeSelectableRoute(String routeId) {
            if (TextUtils.isEmpty(routeId)) {
                throw new IllegalArgumentException("routeId must not be empty");
            }
            this.mSelectableRoutes.remove(routeId);
            return this;
        }

        public Builder clearDeselectableRoutes() {
            this.mDeselectableRoutes.clear();
            return this;
        }

        public Builder addDeselectableRoute(String routeId) {
            if (TextUtils.isEmpty(routeId)) {
                throw new IllegalArgumentException("routeId must not be empty");
            }
            this.mDeselectableRoutes.add(routeId);
            return this;
        }

        public Builder removeDeselectableRoute(String routeId) {
            if (TextUtils.isEmpty(routeId)) {
                throw new IllegalArgumentException("routeId must not be empty");
            }
            this.mDeselectableRoutes.remove(routeId);
            return this;
        }

        public Builder clearTransferableRoutes() {
            this.mTransferableRoutes.clear();
            return this;
        }

        public Builder addTransferableRoute(String routeId) {
            if (TextUtils.isEmpty(routeId)) {
                throw new IllegalArgumentException("routeId must not be empty");
            }
            this.mTransferableRoutes.add(routeId);
            return this;
        }

        public Builder removeTransferableRoute(String routeId) {
            if (TextUtils.isEmpty(routeId)) {
                throw new IllegalArgumentException("routeId must not be empty");
            }
            this.mTransferableRoutes.remove(routeId);
            return this;
        }

        public Builder setVolumeHandling(int volumeHandling) {
            this.mVolumeHandling = volumeHandling;
            return this;
        }

        public Builder setVolumeMax(int volumeMax) {
            this.mVolumeMax = volumeMax;
            return this;
        }

        public Builder setVolume(int volume) {
            this.mVolume = volume;
            return this;
        }

        public Builder setControlHints(Bundle controlHints) {
            this.mControlHints = controlHints;
            return this;
        }

        public Builder setSystemSession(boolean isSystemSession) {
            this.mIsSystemSession = isSystemSession;
            return this;
        }

        public Builder setTransferReason(int transferReason) {
            this.mTransferReason = transferReason;
            return this;
        }

        public Builder setTransferInitiator(UserHandle transferInitiatorUserHandle, String transferInitiatorPackageName) {
            this.mTransferInitiatorUserHandle = transferInitiatorUserHandle;
            this.mTransferInitiatorPackageName = transferInitiatorPackageName;
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
