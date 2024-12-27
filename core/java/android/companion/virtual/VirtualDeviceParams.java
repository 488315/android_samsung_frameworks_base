package android.companion.virtual;

import android.annotation.SystemApi;
import android.companion.virtual.flags.Flags;
import android.companion.virtual.sensor.IVirtualSensorCallback;
import android.companion.virtual.sensor.VirtualSensor;
import android.companion.virtual.sensor.VirtualSensorCallback;
import android.companion.virtual.sensor.VirtualSensorConfig;
import android.companion.virtual.sensor.VirtualSensorDirectChannelCallback;
import android.content.ComponentName;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SharedMemory;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.SparseArray;
import android.util.SparseIntArray;

import java.io.PrintWriter;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@SystemApi
public final class VirtualDeviceParams implements Parcelable {

    @Deprecated public static final int ACTIVITY_POLICY_DEFAULT_ALLOWED = 0;

    @Deprecated public static final int ACTIVITY_POLICY_DEFAULT_BLOCKED = 1;
    public static final Parcelable.Creator<VirtualDeviceParams> CREATOR =
            new Parcelable.Creator<VirtualDeviceParams>() { // from class:
                // android.companion.virtual.VirtualDeviceParams.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VirtualDeviceParams createFromParcel(Parcel in) {
                    return new VirtualDeviceParams(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public VirtualDeviceParams[] newArray(int size) {
                    return new VirtualDeviceParams[size];
                }
            };
    public static final int DEVICE_POLICY_CUSTOM = 1;
    public static final int DEVICE_POLICY_DEFAULT = 0;
    public static final int LOCK_STATE_ALWAYS_UNLOCKED = 1;
    public static final int LOCK_STATE_DEFAULT = 0;

    @Deprecated public static final int NAVIGATION_POLICY_DEFAULT_ALLOWED = 0;

    @Deprecated public static final int NAVIGATION_POLICY_DEFAULT_BLOCKED = 1;
    public static final int POLICY_TYPE_ACTIVITY = 3;
    public static final int POLICY_TYPE_AUDIO = 1;
    public static final int POLICY_TYPE_CAMERA = 5;
    public static final int POLICY_TYPE_CLIPBOARD = 4;
    public static final int POLICY_TYPE_RECENTS = 2;
    public static final int POLICY_TYPE_SENSORS = 0;
    private final ArraySet<ComponentName> mActivityPolicyExemptions;
    private final int mAudioPlaybackSessionId;
    private final int mAudioRecordingSessionId;
    private final ArraySet<ComponentName> mCrossTaskNavigationExemptions;
    private final int mDefaultActivityPolicy;
    private final int mDefaultNavigationPolicy;
    private final SparseIntArray mDevicePolicies;
    private final ComponentName mHomeComponent;
    private final ComponentName mInputMethodComponent;
    private final int mLockState;
    private final String mName;
    private final ArraySet<UserHandle> mUsersWithMatchingAccounts;
    private final IVirtualSensorCallback mVirtualSensorCallback;
    private final List<VirtualSensorConfig> mVirtualSensorConfigs;

    @Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ActivityPolicy {}

    @Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DevicePolicy {}

    @Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DynamicPolicyType {}

    @Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LockState {}

    @Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface NavigationPolicy {}

    @Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PolicyType {}

    private VirtualDeviceParams(
            int lockState,
            Set<UserHandle> usersWithMatchingAccounts,
            int defaultNavigationPolicy,
            Set<ComponentName> crossTaskNavigationExemptions,
            int defaultActivityPolicy,
            Set<ComponentName> activityPolicyExemptions,
            String name,
            SparseIntArray devicePolicies,
            ComponentName homeComponent,
            ComponentName inputMethodComponent,
            List<VirtualSensorConfig> virtualSensorConfigs,
            IVirtualSensorCallback virtualSensorCallback,
            int audioPlaybackSessionId,
            int audioRecordingSessionId) {
        this.mLockState = lockState;
        this.mUsersWithMatchingAccounts =
                new ArraySet<>((Collection) Objects.requireNonNull(usersWithMatchingAccounts));
        this.mDefaultNavigationPolicy = defaultNavigationPolicy;
        this.mCrossTaskNavigationExemptions =
                new ArraySet<>((Collection) Objects.requireNonNull(crossTaskNavigationExemptions));
        this.mDefaultActivityPolicy = defaultActivityPolicy;
        this.mActivityPolicyExemptions =
                new ArraySet<>((Collection) Objects.requireNonNull(activityPolicyExemptions));
        this.mName = name;
        this.mDevicePolicies = (SparseIntArray) Objects.requireNonNull(devicePolicies);
        this.mHomeComponent = homeComponent;
        this.mInputMethodComponent = inputMethodComponent;
        this.mVirtualSensorConfigs = (List) Objects.requireNonNull(virtualSensorConfigs);
        this.mVirtualSensorCallback = virtualSensorCallback;
        this.mAudioPlaybackSessionId = audioPlaybackSessionId;
        this.mAudioRecordingSessionId = audioRecordingSessionId;
    }

    private VirtualDeviceParams(Parcel parcel) {
        this.mLockState = parcel.readInt();
        this.mUsersWithMatchingAccounts = parcel.readArraySet(null);
        this.mDefaultNavigationPolicy = parcel.readInt();
        this.mCrossTaskNavigationExemptions = parcel.readArraySet(null);
        this.mDefaultActivityPolicy = parcel.readInt();
        this.mActivityPolicyExemptions = parcel.readArraySet(null);
        this.mName = parcel.readString8();
        this.mDevicePolicies = parcel.readSparseIntArray();
        this.mVirtualSensorConfigs = new ArrayList();
        parcel.readTypedList(this.mVirtualSensorConfigs, VirtualSensorConfig.CREATOR);
        this.mVirtualSensorCallback =
                IVirtualSensorCallback.Stub.asInterface(parcel.readStrongBinder());
        this.mAudioPlaybackSessionId = parcel.readInt();
        this.mAudioRecordingSessionId = parcel.readInt();
        this.mHomeComponent = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
        this.mInputMethodComponent = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
    }

    public int getLockState() {
        return this.mLockState;
    }

    public ComponentName getHomeComponent() {
        return this.mHomeComponent;
    }

    public ComponentName getInputMethodComponent() {
        return this.mInputMethodComponent;
    }

    public Set<UserHandle> getUsersWithMatchingAccounts() {
        return Collections.unmodifiableSet(this.mUsersWithMatchingAccounts);
    }

    @Deprecated
    public Set<ComponentName> getAllowedCrossTaskNavigations() {
        if (this.mDefaultNavigationPolicy == 0) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(this.mCrossTaskNavigationExemptions);
    }

    @Deprecated
    public Set<ComponentName> getBlockedCrossTaskNavigations() {
        if (this.mDefaultNavigationPolicy == 1) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(this.mCrossTaskNavigationExemptions);
    }

    @Deprecated
    public int getDefaultNavigationPolicy() {
        return this.mDefaultNavigationPolicy;
    }

    @Deprecated
    public Set<ComponentName> getAllowedActivities() {
        if (this.mDefaultActivityPolicy == 0) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(this.mActivityPolicyExemptions);
    }

    @Deprecated
    public Set<ComponentName> getBlockedActivities() {
        if (this.mDefaultActivityPolicy == 1) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(this.mActivityPolicyExemptions);
    }

    @Deprecated
    public int getDefaultActivityPolicy() {
        return this.mDefaultActivityPolicy;
    }

    public String getName() {
        return this.mName;
    }

    public int getDevicePolicy(int policyType) {
        return this.mDevicePolicies.get(policyType, 0);
    }

    public SparseIntArray getDevicePolicies() {
        return this.mDevicePolicies;
    }

    public List<VirtualSensorConfig> getVirtualSensorConfigs() {
        return this.mVirtualSensorConfigs;
    }

    public IVirtualSensorCallback getVirtualSensorCallback() {
        return this.mVirtualSensorCallback;
    }

    public int getAudioPlaybackSessionId() {
        return this.mAudioPlaybackSessionId;
    }

    public int getAudioRecordingSessionId() {
        return this.mAudioRecordingSessionId;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mLockState);
        dest.writeArraySet(this.mUsersWithMatchingAccounts);
        dest.writeInt(this.mDefaultNavigationPolicy);
        dest.writeArraySet(this.mCrossTaskNavigationExemptions);
        dest.writeInt(this.mDefaultActivityPolicy);
        dest.writeArraySet(this.mActivityPolicyExemptions);
        dest.writeString8(this.mName);
        dest.writeSparseIntArray(this.mDevicePolicies);
        dest.writeTypedList(this.mVirtualSensorConfigs);
        dest.writeStrongBinder(
                this.mVirtualSensorCallback != null
                        ? this.mVirtualSensorCallback.asBinder()
                        : null);
        dest.writeInt(this.mAudioPlaybackSessionId);
        dest.writeInt(this.mAudioRecordingSessionId);
        dest.writeTypedObject(this.mHomeComponent, flags);
        dest.writeTypedObject(this.mInputMethodComponent, flags);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VirtualDeviceParams)) {
            return false;
        }
        VirtualDeviceParams that = (VirtualDeviceParams) o;
        int devicePoliciesCount = this.mDevicePolicies.size();
        if (devicePoliciesCount != that.mDevicePolicies.size()) {
            return false;
        }
        for (int i = 0; i < devicePoliciesCount; i++) {
            if (this.mDevicePolicies.keyAt(i) != that.mDevicePolicies.keyAt(i)
                    || this.mDevicePolicies.valueAt(i) != that.mDevicePolicies.valueAt(i)) {
                return false;
            }
        }
        int i2 = this.mLockState;
        return i2 == that.mLockState
                && this.mUsersWithMatchingAccounts.equals(that.mUsersWithMatchingAccounts)
                && Objects.equals(
                        this.mCrossTaskNavigationExemptions, that.mCrossTaskNavigationExemptions)
                && this.mDefaultNavigationPolicy == that.mDefaultNavigationPolicy
                && Objects.equals(this.mActivityPolicyExemptions, that.mActivityPolicyExemptions)
                && this.mDefaultActivityPolicy == that.mDefaultActivityPolicy
                && Objects.equals(this.mName, that.mName)
                && Objects.equals(this.mHomeComponent, that.mHomeComponent)
                && Objects.equals(this.mInputMethodComponent, that.mInputMethodComponent)
                && this.mAudioPlaybackSessionId == that.mAudioPlaybackSessionId
                && this.mAudioRecordingSessionId == that.mAudioRecordingSessionId;
    }

    public int hashCode() {
        int hashCode =
                Objects.hash(
                        Integer.valueOf(this.mLockState),
                        this.mUsersWithMatchingAccounts,
                        this.mCrossTaskNavigationExemptions,
                        Integer.valueOf(this.mDefaultNavigationPolicy),
                        this.mActivityPolicyExemptions,
                        Integer.valueOf(this.mDefaultActivityPolicy),
                        this.mName,
                        this.mDevicePolicies,
                        this.mHomeComponent,
                        this.mInputMethodComponent,
                        Integer.valueOf(this.mAudioPlaybackSessionId),
                        Integer.valueOf(this.mAudioRecordingSessionId));
        for (int i = 0; i < this.mDevicePolicies.size(); i++) {
            hashCode =
                    (((hashCode * 31) + this.mDevicePolicies.keyAt(i)) * 31)
                            + this.mDevicePolicies.valueAt(i);
        }
        return hashCode;
    }

    public String toString() {
        return "VirtualDeviceParams( mLockState="
                + this.mLockState
                + " mUsersWithMatchingAccounts="
                + this.mUsersWithMatchingAccounts
                + " mDefaultNavigationPolicy="
                + this.mDefaultNavigationPolicy
                + " mCrossTaskNavigationExemptions="
                + this.mCrossTaskNavigationExemptions
                + " mDefaultActivityPolicy="
                + this.mDefaultActivityPolicy
                + " mActivityPolicyExemptions="
                + this.mActivityPolicyExemptions
                + " mName="
                + this.mName
                + " mDevicePolicies="
                + this.mDevicePolicies
                + " mHomeComponent="
                + this.mHomeComponent
                + " mInputMethodComponent="
                + this.mInputMethodComponent
                + " mAudioPlaybackSessionId="
                + this.mAudioPlaybackSessionId
                + " mAudioRecordingSessionId="
                + this.mAudioRecordingSessionId
                + NavigationBarInflaterView.KEY_CODE_END;
    }

    public void dump(PrintWriter pw, String prefix) {
        pw.println(prefix + "mName=" + this.mName);
        pw.println(prefix + "mLockState=" + this.mLockState);
        pw.println(prefix + "mUsersWithMatchingAccounts=" + this.mUsersWithMatchingAccounts);
        pw.println(prefix + "mDefaultNavigationPolicy=" + this.mDefaultNavigationPolicy);
        pw.println(
                prefix + "mCrossTaskNavigationExemptions=" + this.mCrossTaskNavigationExemptions);
        pw.println(prefix + "mDefaultActivityPolicy=" + this.mDefaultActivityPolicy);
        pw.println(prefix + "mActivityPolicyExemptions=" + this.mActivityPolicyExemptions);
        pw.println(prefix + "mDevicePolicies=" + this.mDevicePolicies);
        pw.println(prefix + "mVirtualSensorConfigs=" + this.mVirtualSensorConfigs);
        pw.println(prefix + "mHomeComponent=" + this.mHomeComponent);
        pw.println(prefix + "mInputMethodComponent=" + this.mInputMethodComponent);
        pw.println(prefix + "mAudioPlaybackSessionId=" + this.mAudioPlaybackSessionId);
        pw.println(prefix + "mAudioRecordingSessionId=" + this.mAudioRecordingSessionId);
    }

    public static final class Builder {
        private ComponentName mHomeComponent;
        private ComponentName mInputMethodComponent;
        private String mName;
        private VirtualSensorCallback mVirtualSensorCallback;
        private Executor mVirtualSensorCallbackExecutor;
        private VirtualSensorDirectChannelCallback mVirtualSensorDirectChannelCallback;
        private Executor mVirtualSensorDirectChannelCallbackExecutor;
        private int mLockState = 0;
        private Set<UserHandle> mUsersWithMatchingAccounts = Collections.emptySet();
        private Set<ComponentName> mCrossTaskNavigationExemptions = Collections.emptySet();
        private int mDefaultNavigationPolicy = 0;
        private boolean mDefaultNavigationPolicyConfigured = false;
        private Set<ComponentName> mActivityPolicyExemptions = Collections.emptySet();
        private int mDefaultActivityPolicy = 0;
        private boolean mDefaultActivityPolicyConfigured = false;
        private final SparseIntArray mDevicePolicies = new SparseIntArray();
        private int mAudioPlaybackSessionId = 0;
        private int mAudioRecordingSessionId = 0;
        private final List<VirtualSensorConfig> mVirtualSensorConfigs = new ArrayList();

        /* JADX INFO: Access modifiers changed from: private */
        static class VirtualSensorCallbackDelegate extends IVirtualSensorCallback.Stub {
            private final VirtualSensorCallback mCallback;
            private final VirtualSensorDirectChannelCallback mDirectChannelCallback;
            private final Executor mDirectChannelExecutor;
            private final Executor mExecutor;

            VirtualSensorCallbackDelegate(
                    Executor executor,
                    VirtualSensorCallback callback,
                    Executor directChannelExecutor,
                    VirtualSensorDirectChannelCallback directChannelCallback) {
                this.mExecutor = executor;
                this.mCallback = callback;
                this.mDirectChannelExecutor = directChannelExecutor;
                this.mDirectChannelCallback = directChannelCallback;
            }

            @Override // android.companion.virtual.sensor.IVirtualSensorCallback
            public void onConfigurationChanged(
                    final VirtualSensor sensor,
                    final boolean enabled,
                    int samplingPeriodMicros,
                    int batchReportLatencyMicros) {
                final Duration samplingPeriod =
                        Duration.ofNanos(TimeUnit.MICROSECONDS.toNanos(samplingPeriodMicros));
                final Duration batchReportingLatency =
                        Duration.ofNanos(TimeUnit.MICROSECONDS.toNanos(batchReportLatencyMicros));
                this.mExecutor.execute(
                        new Runnable() { // from class:
                            // android.companion.virtual.VirtualDeviceParams$Builder$VirtualSensorCallbackDelegate$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                VirtualDeviceParams.Builder.VirtualSensorCallbackDelegate.this
                                        .lambda$onConfigurationChanged$0(
                                                sensor,
                                                enabled,
                                                samplingPeriod,
                                                batchReportingLatency);
                            }
                        });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onConfigurationChanged$0(
                    VirtualSensor sensor,
                    boolean enabled,
                    Duration samplingPeriod,
                    Duration batchReportingLatency) {
                this.mCallback.onConfigurationChanged(
                        sensor, enabled, samplingPeriod, batchReportingLatency);
            }

            @Override // android.companion.virtual.sensor.IVirtualSensorCallback
            public void onDirectChannelCreated(
                    final int channelHandle, final SharedMemory sharedMemory) {
                if (this.mDirectChannelCallback != null && this.mDirectChannelExecutor != null) {
                    this.mDirectChannelExecutor.execute(
                            new Runnable() { // from class:
                                // android.companion.virtual.VirtualDeviceParams$Builder$VirtualSensorCallbackDelegate$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    VirtualDeviceParams.Builder.VirtualSensorCallbackDelegate.this
                                            .lambda$onDirectChannelCreated$1(
                                                    channelHandle, sharedMemory);
                                }
                            });
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onDirectChannelCreated$1(
                    int channelHandle, SharedMemory sharedMemory) {
                this.mDirectChannelCallback.onDirectChannelCreated(channelHandle, sharedMemory);
            }

            @Override // android.companion.virtual.sensor.IVirtualSensorCallback
            public void onDirectChannelDestroyed(final int channelHandle) {
                if (this.mDirectChannelCallback != null && this.mDirectChannelExecutor != null) {
                    this.mDirectChannelExecutor.execute(
                            new Runnable() { // from class:
                                // android.companion.virtual.VirtualDeviceParams$Builder$VirtualSensorCallbackDelegate$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    VirtualDeviceParams.Builder.VirtualSensorCallbackDelegate.this
                                            .lambda$onDirectChannelDestroyed$2(channelHandle);
                                }
                            });
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onDirectChannelDestroyed$2(int channelHandle) {
                this.mDirectChannelCallback.onDirectChannelDestroyed(channelHandle);
            }

            @Override // android.companion.virtual.sensor.IVirtualSensorCallback
            public void onDirectChannelConfigured(
                    final int channelHandle,
                    final VirtualSensor sensor,
                    final int rateLevel,
                    final int reportToken) {
                if (this.mDirectChannelCallback != null && this.mDirectChannelExecutor != null) {
                    this.mDirectChannelExecutor.execute(
                            new Runnable() { // from class:
                                // android.companion.virtual.VirtualDeviceParams$Builder$VirtualSensorCallbackDelegate$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    VirtualDeviceParams.Builder.VirtualSensorCallbackDelegate.this
                                            .lambda$onDirectChannelConfigured$3(
                                                    channelHandle, sensor, rateLevel, reportToken);
                                }
                            });
                }
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onDirectChannelConfigured$3(
                    int channelHandle, VirtualSensor sensor, int rateLevel, int reportToken) {
                this.mDirectChannelCallback.onDirectChannelConfigured(
                        channelHandle, sensor, rateLevel, reportToken);
            }
        }

        public Builder setLockState(int lockState) {
            this.mLockState = lockState;
            return this;
        }

        public Builder setHomeComponent(ComponentName homeComponent) {
            this.mHomeComponent = homeComponent;
            return this;
        }

        public Builder setInputMethodComponent(ComponentName inputMethodComponent) {
            this.mInputMethodComponent = inputMethodComponent;
            return this;
        }

        public Builder setUsersWithMatchingAccounts(Set<UserHandle> usersWithMatchingAccounts) {
            this.mUsersWithMatchingAccounts =
                    (Set) Objects.requireNonNull(usersWithMatchingAccounts);
            return this;
        }

        @Deprecated
        public Builder setAllowedCrossTaskNavigations(
                Set<ComponentName> allowedCrossTaskNavigations) {
            if (this.mDefaultNavigationPolicyConfigured && this.mDefaultNavigationPolicy != 1) {
                throw new IllegalArgumentException(
                        "Allowed cross task navigations and blocked cross task navigations cannot "
                                + " both be set.");
            }
            this.mDefaultNavigationPolicy = 1;
            this.mDefaultNavigationPolicyConfigured = true;
            this.mCrossTaskNavigationExemptions =
                    (Set) Objects.requireNonNull(allowedCrossTaskNavigations);
            return this;
        }

        @Deprecated
        public Builder setBlockedCrossTaskNavigations(
                Set<ComponentName> blockedCrossTaskNavigations) {
            if (this.mDefaultNavigationPolicyConfigured && this.mDefaultNavigationPolicy != 0) {
                throw new IllegalArgumentException(
                        "Allowed cross task navigation and blocked task navigation cannot  be"
                                + " set.");
            }
            this.mDefaultNavigationPolicy = 0;
            this.mDefaultNavigationPolicyConfigured = true;
            this.mCrossTaskNavigationExemptions =
                    (Set) Objects.requireNonNull(blockedCrossTaskNavigations);
            return this;
        }

        @Deprecated
        public Builder setAllowedActivities(Set<ComponentName> allowedActivities) {
            if (this.mDefaultActivityPolicyConfigured && this.mDefaultActivityPolicy != 1) {
                throw new IllegalArgumentException(
                        "Allowed activities and Blocked activities cannot both be set.");
            }
            this.mDefaultActivityPolicy = 1;
            this.mDefaultActivityPolicyConfigured = true;
            this.mActivityPolicyExemptions = (Set) Objects.requireNonNull(allowedActivities);
            return this;
        }

        @Deprecated
        public Builder setBlockedActivities(Set<ComponentName> blockedActivities) {
            if (this.mDefaultActivityPolicyConfigured && this.mDefaultActivityPolicy != 0) {
                throw new IllegalArgumentException(
                        "Allowed activities and Blocked activities cannot both be set.");
            }
            this.mDefaultActivityPolicy = 0;
            this.mDefaultActivityPolicyConfigured = true;
            this.mActivityPolicyExemptions = (Set) Objects.requireNonNull(blockedActivities);
            return this;
        }

        public Builder setName(String name) {
            this.mName = name;
            return this;
        }

        public Builder setDevicePolicy(int policyType, int devicePolicy) {
            this.mDevicePolicies.put(policyType, devicePolicy);
            return this;
        }

        public Builder addVirtualSensorConfig(VirtualSensorConfig virtualSensorConfig) {
            this.mVirtualSensorConfigs.add(
                    (VirtualSensorConfig) Objects.requireNonNull(virtualSensorConfig));
            return this;
        }

        public Builder setVirtualSensorCallback(Executor executor, VirtualSensorCallback callback) {
            this.mVirtualSensorCallbackExecutor = (Executor) Objects.requireNonNull(executor);
            this.mVirtualSensorCallback = (VirtualSensorCallback) Objects.requireNonNull(callback);
            return this;
        }

        public Builder setVirtualSensorDirectChannelCallback(
                Executor executor, VirtualSensorDirectChannelCallback callback) {
            this.mVirtualSensorDirectChannelCallbackExecutor =
                    (Executor) Objects.requireNonNull(executor);
            this.mVirtualSensorDirectChannelCallback =
                    (VirtualSensorDirectChannelCallback) Objects.requireNonNull(callback);
            return this;
        }

        public Builder setAudioPlaybackSessionId(int playbackSessionId) {
            if (playbackSessionId < 0) {
                throw new IllegalArgumentException("Invalid playback audio session id");
            }
            this.mAudioPlaybackSessionId = playbackSessionId;
            return this;
        }

        public Builder setAudioRecordingSessionId(int recordingSessionId) {
            if (recordingSessionId < 0) {
                throw new IllegalArgumentException("Invalid recording audio session id");
            }
            this.mAudioRecordingSessionId = recordingSessionId;
            return this;
        }

        public VirtualDeviceParams build() {
            VirtualSensorCallbackDelegate virtualSensorCallbackDelegate = null;
            if (!this.mVirtualSensorConfigs.isEmpty()) {
                if (this.mDevicePolicies.get(0, 0) != 1) {
                    throw new IllegalArgumentException(
                            "DEVICE_POLICY_CUSTOM for POLICY_TYPE_SENSORS is required for creating"
                                    + " virtual sensors.");
                }
                if (this.mVirtualSensorCallback == null) {
                    throw new IllegalArgumentException(
                            "VirtualSensorCallback is required for creating virtual sensors.");
                }
                int i = 0;
                while (true) {
                    if (i >= this.mVirtualSensorConfigs.size()) {
                        break;
                    }
                    if (this.mVirtualSensorConfigs.get(i).getDirectChannelTypesSupported() <= 0) {
                        i++;
                    } else if (this.mVirtualSensorDirectChannelCallback == null) {
                        throw new IllegalArgumentException(
                                "VirtualSensorDirectChannelCallback is required for creating"
                                        + " virtual sensors that support direct channel.");
                    }
                }
                virtualSensorCallbackDelegate =
                        new VirtualSensorCallbackDelegate(
                                this.mVirtualSensorCallbackExecutor,
                                this.mVirtualSensorCallback,
                                this.mVirtualSensorDirectChannelCallbackExecutor,
                                this.mVirtualSensorDirectChannelCallback);
            }
            if (Flags.dynamicPolicy()) {
                switch (this.mDevicePolicies.get(3, -1)) {
                    case 0:
                        if (this.mDefaultActivityPolicyConfigured
                                && this.mDefaultActivityPolicy == 1) {
                            throw new IllegalArgumentException(
                                    "DEVICE_POLICY_DEFAULT is explicitly configured for"
                                            + " POLICY_TYPE_ACTIVITY, which is exclusive with"
                                            + " setAllowedActivities.");
                        }
                        break;
                    case 1:
                        if (this.mDefaultActivityPolicyConfigured
                                && this.mDefaultActivityPolicy == 0) {
                            throw new IllegalArgumentException(
                                    "DEVICE_POLICY_CUSTOM is explicitly configured for"
                                            + " POLICY_TYPE_ACTIVITY, which is exclusive with"
                                            + " setBlockedActivities.");
                        }
                        break;
                    default:
                        if (this.mDefaultActivityPolicyConfigured
                                && this.mDefaultActivityPolicy == 1) {
                            this.mDevicePolicies.put(3, 1);
                            break;
                        }
                        break;
                }
            }
            if (!Flags.crossDeviceClipboard()) {
                this.mDevicePolicies.delete(4);
            }
            if (!Flags.virtualCamera()) {
                this.mDevicePolicies.delete(5);
            }
            if ((this.mAudioPlaybackSessionId != 0 || this.mAudioRecordingSessionId != 0)
                    && this.mDevicePolicies.get(1, 0) != 1) {
                throw new IllegalArgumentException(
                        "DEVICE_POLICY_CUSTOM for POLICY_TYPE_AUDIO is required for configuration"
                                + " of device-specific audio session ids.");
            }
            SparseArray<Set<String>> sensorNameByType = new SparseArray<>();
            for (int i2 = 0; i2 < this.mVirtualSensorConfigs.size(); i2++) {
                VirtualSensorConfig config = this.mVirtualSensorConfigs.get(i2);
                Set<String> sensorNames = sensorNameByType.get(config.getType(), new ArraySet<>());
                if (!sensorNames.add(config.getName())) {
                    throw new IllegalArgumentException(
                            "Sensor names must be unique for a particular sensor type.");
                }
                sensorNameByType.put(config.getType(), sensorNames);
            }
            return new VirtualDeviceParams(
                    this.mLockState,
                    this.mUsersWithMatchingAccounts,
                    this.mDefaultNavigationPolicy,
                    this.mCrossTaskNavigationExemptions,
                    this.mDefaultActivityPolicy,
                    this.mActivityPolicyExemptions,
                    this.mName,
                    this.mDevicePolicies,
                    this.mHomeComponent,
                    this.mInputMethodComponent,
                    this.mVirtualSensorConfigs,
                    virtualSensorCallbackDelegate,
                    this.mAudioPlaybackSessionId,
                    this.mAudioRecordingSessionId);
        }
    }
}
