package android.companion.virtual;

import android.annotation.SystemApi;
import android.companion.virtual.VirtualDeviceParams;
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
import com.android.internal.hidden_from_bootclasspath.android.companion.virtualdevice.flags.Flags;
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
/* loaded from: classes.dex */
public final class VirtualDeviceParams implements Parcelable {

    @Deprecated
    public static final int ACTIVITY_POLICY_DEFAULT_ALLOWED = 0;

    @Deprecated
    public static final int ACTIVITY_POLICY_DEFAULT_BLOCKED = 1;
    public static final Parcelable.Creator<VirtualDeviceParams> CREATOR = new Parcelable.Creator<VirtualDeviceParams>() { // from class: android.companion.virtual.VirtualDeviceParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualDeviceParams createFromParcel(Parcel parcel) {
            return new VirtualDeviceParams(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VirtualDeviceParams[] newArray(int i) {
            return new VirtualDeviceParams[i];
        }
    };
    public static final int DEVICE_POLICY_CUSTOM = 1;
    public static final int DEVICE_POLICY_DEFAULT = 0;
    public static final int LOCK_STATE_ALWAYS_UNLOCKED = 1;
    public static final int LOCK_STATE_DEFAULT = 0;

    @Deprecated
    public static final int NAVIGATION_POLICY_DEFAULT_ALLOWED = 0;

    @Deprecated
    public static final int NAVIGATION_POLICY_DEFAULT_BLOCKED = 1;
    public static final int POLICY_TYPE_ACTIVITY = 3;
    public static final int POLICY_TYPE_AUDIO = 1;
    public static final int POLICY_TYPE_BLOCKED_ACTIVITY = 6;
    public static final int POLICY_TYPE_CAMERA = 5;
    public static final int POLICY_TYPE_CLIPBOARD = 4;
    public static final int POLICY_TYPE_DEFAULT_DEVICE_CAMERA_ACCESS = 7;
    public static final int POLICY_TYPE_RECENTS = 2;
    public static final int POLICY_TYPE_SENSORS = 0;
    private final ArraySet<ComponentName> mActivityPolicyExemptions;
    private final int mAudioPlaybackSessionId;
    private final int mAudioRecordingSessionId;
    private final ArraySet<ComponentName> mCrossTaskNavigationExemptions;
    private final int mDefaultActivityPolicy;
    private final int mDefaultNavigationPolicy;
    private final SparseIntArray mDevicePolicies;
    private final long mDimDuration;
    private final ComponentName mHomeComponent;
    private final ComponentName mInputMethodComponent;
    private final int mLockState;
    private final String mName;
    private final long mScreenOffTimeout;
    private final ArraySet<UserHandle> mUsersWithMatchingAccounts;
    private final IVirtualSensorCallback mVirtualSensorCallback;
    private final List<VirtualSensorConfig> mVirtualSensorConfigs;

    @Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ActivityPolicy {
    }

    @Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DevicePolicy {
    }

    @Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DynamicDisplayPolicyType {
    }

    @Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DynamicPolicyType {
    }

    @Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LockState {
    }

    @Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface NavigationPolicy {
    }

    @Target({ElementType.TYPE_PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PolicyType {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private VirtualDeviceParams(int i, Set<UserHandle> set, int i2, Set<ComponentName> set2, int i3, Set<ComponentName> set3, String str, SparseIntArray sparseIntArray, ComponentName componentName, ComponentName componentName2, List<VirtualSensorConfig> list, IVirtualSensorCallback iVirtualSensorCallback, int i4, int i5, long j, long j2) {
        this.mLockState = i;
        this.mUsersWithMatchingAccounts = new ArraySet<>((Collection) Objects.requireNonNull(set));
        this.mDefaultNavigationPolicy = i2;
        this.mCrossTaskNavigationExemptions = new ArraySet<>((Collection) Objects.requireNonNull(set2));
        this.mDefaultActivityPolicy = i3;
        this.mActivityPolicyExemptions = new ArraySet<>((Collection) Objects.requireNonNull(set3));
        this.mName = str;
        this.mDevicePolicies = (SparseIntArray) Objects.requireNonNull(sparseIntArray);
        this.mHomeComponent = componentName;
        this.mInputMethodComponent = componentName2;
        this.mVirtualSensorConfigs = (List) Objects.requireNonNull(list);
        this.mVirtualSensorCallback = iVirtualSensorCallback;
        this.mAudioPlaybackSessionId = i4;
        this.mAudioRecordingSessionId = i5;
        this.mDimDuration = j;
        this.mScreenOffTimeout = j2;
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
        ArrayList arrayList = new ArrayList();
        this.mVirtualSensorConfigs = arrayList;
        parcel.readTypedList(arrayList, VirtualSensorConfig.CREATOR);
        this.mVirtualSensorCallback = IVirtualSensorCallback.Stub.asInterface(parcel.readStrongBinder());
        this.mAudioPlaybackSessionId = parcel.readInt();
        this.mAudioRecordingSessionId = parcel.readInt();
        this.mHomeComponent = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
        this.mInputMethodComponent = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
        this.mDimDuration = parcel.readLong();
        this.mScreenOffTimeout = parcel.readLong();
    }

    public int getLockState() {
        return this.mLockState;
    }

    public Duration getDimDuration() {
        return Duration.ofMillis(this.mDimDuration);
    }

    public Duration getScreenOffTimeout() {
        return Duration.ofMillis(this.mScreenOffTimeout);
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
            return Collections.EMPTY_SET;
        }
        return Collections.unmodifiableSet(this.mCrossTaskNavigationExemptions);
    }

    @Deprecated
    public Set<ComponentName> getBlockedCrossTaskNavigations() {
        if (this.mDefaultNavigationPolicy == 1) {
            return Collections.EMPTY_SET;
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
            return Collections.EMPTY_SET;
        }
        return Collections.unmodifiableSet(this.mActivityPolicyExemptions);
    }

    @Deprecated
    public Set<ComponentName> getBlockedActivities() {
        if (this.mDefaultActivityPolicy == 1) {
            return Collections.EMPTY_SET;
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

    public int getDevicePolicy(int i) {
        return this.mDevicePolicies.get(i, 0);
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mLockState);
        parcel.writeArraySet(this.mUsersWithMatchingAccounts);
        parcel.writeInt(this.mDefaultNavigationPolicy);
        parcel.writeArraySet(this.mCrossTaskNavigationExemptions);
        parcel.writeInt(this.mDefaultActivityPolicy);
        parcel.writeArraySet(this.mActivityPolicyExemptions);
        parcel.writeString8(this.mName);
        parcel.writeSparseIntArray(this.mDevicePolicies);
        parcel.writeTypedList(this.mVirtualSensorConfigs);
        IVirtualSensorCallback iVirtualSensorCallback = this.mVirtualSensorCallback;
        parcel.writeStrongBinder(iVirtualSensorCallback != null ? iVirtualSensorCallback.asBinder() : null);
        parcel.writeInt(this.mAudioPlaybackSessionId);
        parcel.writeInt(this.mAudioRecordingSessionId);
        parcel.writeTypedObject(this.mHomeComponent, i);
        parcel.writeTypedObject(this.mInputMethodComponent, i);
        parcel.writeLong(this.mDimDuration);
        parcel.writeLong(this.mScreenOffTimeout);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VirtualDeviceParams)) {
            return false;
        }
        VirtualDeviceParams virtualDeviceParams = (VirtualDeviceParams) obj;
        int size = this.mDevicePolicies.size();
        if (size != virtualDeviceParams.mDevicePolicies.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (this.mDevicePolicies.keyAt(i) != virtualDeviceParams.mDevicePolicies.keyAt(i) || this.mDevicePolicies.valueAt(i) != virtualDeviceParams.mDevicePolicies.valueAt(i)) {
                return false;
            }
        }
        return this.mLockState == virtualDeviceParams.mLockState && this.mUsersWithMatchingAccounts.equals(virtualDeviceParams.mUsersWithMatchingAccounts) && Objects.equals(this.mCrossTaskNavigationExemptions, virtualDeviceParams.mCrossTaskNavigationExemptions) && this.mDefaultNavigationPolicy == virtualDeviceParams.mDefaultNavigationPolicy && Objects.equals(this.mActivityPolicyExemptions, virtualDeviceParams.mActivityPolicyExemptions) && this.mDefaultActivityPolicy == virtualDeviceParams.mDefaultActivityPolicy && Objects.equals(this.mName, virtualDeviceParams.mName) && Objects.equals(this.mHomeComponent, virtualDeviceParams.mHomeComponent) && Objects.equals(this.mInputMethodComponent, virtualDeviceParams.mInputMethodComponent) && this.mAudioPlaybackSessionId == virtualDeviceParams.mAudioPlaybackSessionId && this.mAudioRecordingSessionId == virtualDeviceParams.mAudioRecordingSessionId && this.mDimDuration == virtualDeviceParams.mDimDuration && this.mScreenOffTimeout == virtualDeviceParams.mScreenOffTimeout;
    }

    public int hashCode() {
        int hash = Objects.hash(Integer.valueOf(this.mLockState), this.mUsersWithMatchingAccounts, this.mCrossTaskNavigationExemptions, Integer.valueOf(this.mDefaultNavigationPolicy), this.mActivityPolicyExemptions, Integer.valueOf(this.mDefaultActivityPolicy), this.mName, this.mDevicePolicies, this.mHomeComponent, this.mInputMethodComponent, Integer.valueOf(this.mAudioPlaybackSessionId), Integer.valueOf(this.mAudioRecordingSessionId), Long.valueOf(this.mDimDuration), Long.valueOf(this.mScreenOffTimeout));
        for (int i = 0; i < this.mDevicePolicies.size(); i++) {
            hash = (((hash * 31) + this.mDevicePolicies.keyAt(i)) * 31) + this.mDevicePolicies.valueAt(i);
        }
        return hash;
    }

    public String toString() {
        return "VirtualDeviceParams( mLockState=" + this.mLockState + " mUsersWithMatchingAccounts=" + this.mUsersWithMatchingAccounts + " mDefaultNavigationPolicy=" + this.mDefaultNavigationPolicy + " mCrossTaskNavigationExemptions=" + this.mCrossTaskNavigationExemptions + " mDefaultActivityPolicy=" + this.mDefaultActivityPolicy + " mActivityPolicyExemptions=" + this.mActivityPolicyExemptions + " mName=" + this.mName + " mDevicePolicies=" + this.mDevicePolicies + " mHomeComponent=" + this.mHomeComponent + " mInputMethodComponent=" + this.mInputMethodComponent + " mAudioPlaybackSessionId=" + this.mAudioPlaybackSessionId + " mAudioRecordingSessionId=" + this.mAudioRecordingSessionId + " mDimDuration=" + this.mDimDuration + " mScreenOffTimeout=" + this.mScreenOffTimeout + NavigationBarInflaterView.KEY_CODE_END;
    }

    public void dump(PrintWriter printWriter, String str) {
        printWriter.println(str + "mName=" + this.mName);
        printWriter.println(str + "mLockState=" + this.mLockState);
        printWriter.println(str + "mUsersWithMatchingAccounts=" + this.mUsersWithMatchingAccounts);
        printWriter.println(str + "mDefaultNavigationPolicy=" + this.mDefaultNavigationPolicy);
        printWriter.println(str + "mCrossTaskNavigationExemptions=" + this.mCrossTaskNavigationExemptions);
        printWriter.println(str + "mDefaultActivityPolicy=" + this.mDefaultActivityPolicy);
        printWriter.println(str + "mActivityPolicyExemptions=" + this.mActivityPolicyExemptions);
        printWriter.println(str + "mDevicePolicies=" + this.mDevicePolicies);
        printWriter.println(str + "mVirtualSensorConfigs=" + this.mVirtualSensorConfigs);
        printWriter.println(str + "mHomeComponent=" + this.mHomeComponent);
        printWriter.println(str + "mInputMethodComponent=" + this.mInputMethodComponent);
        printWriter.println(str + "mAudioPlaybackSessionId=" + this.mAudioPlaybackSessionId);
        printWriter.println(str + "mAudioRecordingSessionId=" + this.mAudioRecordingSessionId);
        printWriter.println(str + "mDimDuration=" + this.mDimDuration);
        printWriter.println(str + "mScreenOffTimeout=" + this.mScreenOffTimeout);
    }

    public static final class Builder {
        private static final Duration INFINITE_TIMEOUT = Duration.ofDays(365000);
        private ComponentName mHomeComponent;
        private ComponentName mInputMethodComponent;
        private String mName;
        private VirtualSensorCallback mVirtualSensorCallback;
        private Executor mVirtualSensorCallbackExecutor;
        private VirtualSensorDirectChannelCallback mVirtualSensorDirectChannelCallback;
        private Executor mVirtualSensorDirectChannelCallbackExecutor;
        private int mLockState = 0;
        private Set<UserHandle> mUsersWithMatchingAccounts = Collections.EMPTY_SET;
        private Set<ComponentName> mCrossTaskNavigationExemptions = Collections.EMPTY_SET;
        private int mDefaultNavigationPolicy = 0;
        private boolean mDefaultNavigationPolicyConfigured = false;
        private Set<ComponentName> mActivityPolicyExemptions = Collections.EMPTY_SET;
        private int mDefaultActivityPolicy = 0;
        private boolean mDefaultActivityPolicyConfigured = false;
        private final SparseIntArray mDevicePolicies = new SparseIntArray();
        private int mAudioPlaybackSessionId = 0;
        private int mAudioRecordingSessionId = 0;
        private final List<VirtualSensorConfig> mVirtualSensorConfigs = new ArrayList();
        private Duration mDimDuration = Duration.ZERO;
        private Duration mScreenOffTimeout = Duration.ZERO;

        /* JADX INFO: Access modifiers changed from: private */
        static class VirtualSensorCallbackDelegate extends IVirtualSensorCallback.Stub {
            private final VirtualSensorCallback mCallback;
            private final VirtualSensorDirectChannelCallback mDirectChannelCallback;
            private final Executor mDirectChannelExecutor;
            private final Executor mExecutor;

            VirtualSensorCallbackDelegate(Executor executor, VirtualSensorCallback virtualSensorCallback, Executor executor2, VirtualSensorDirectChannelCallback virtualSensorDirectChannelCallback) {
                this.mExecutor = executor;
                this.mCallback = virtualSensorCallback;
                this.mDirectChannelExecutor = executor2;
                this.mDirectChannelCallback = virtualSensorDirectChannelCallback;
            }

            @Override // android.companion.virtual.sensor.IVirtualSensorCallback
            public void onConfigurationChanged(final VirtualSensor virtualSensor, final boolean z, int i, int i2) {
                final Duration ofNanos = Duration.ofNanos(TimeUnit.MICROSECONDS.toNanos(i));
                final Duration ofNanos2 = Duration.ofNanos(TimeUnit.MICROSECONDS.toNanos(i2));
                this.mExecutor.execute(new Runnable() { // from class: android.companion.virtual.VirtualDeviceParams$Builder$VirtualSensorCallbackDelegate$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        VirtualDeviceParams.Builder.VirtualSensorCallbackDelegate.this.lambda$onConfigurationChanged$0(virtualSensor, z, ofNanos, ofNanos2);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onConfigurationChanged$0(VirtualSensor virtualSensor, boolean z, Duration duration, Duration duration2) {
                this.mCallback.onConfigurationChanged(virtualSensor, z, duration, duration2);
            }

            @Override // android.companion.virtual.sensor.IVirtualSensorCallback
            public void onDirectChannelCreated(final int i, final SharedMemory sharedMemory) {
                Executor executor;
                if (this.mDirectChannelCallback == null || (executor = this.mDirectChannelExecutor) == null) {
                    return;
                }
                executor.execute(new Runnable() { // from class: android.companion.virtual.VirtualDeviceParams$Builder$VirtualSensorCallbackDelegate$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        VirtualDeviceParams.Builder.VirtualSensorCallbackDelegate.this.lambda$onDirectChannelCreated$1(i, sharedMemory);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onDirectChannelCreated$1(int i, SharedMemory sharedMemory) {
                this.mDirectChannelCallback.onDirectChannelCreated(i, sharedMemory);
            }

            @Override // android.companion.virtual.sensor.IVirtualSensorCallback
            public void onDirectChannelDestroyed(final int i) {
                Executor executor;
                if (this.mDirectChannelCallback == null || (executor = this.mDirectChannelExecutor) == null) {
                    return;
                }
                executor.execute(new Runnable() { // from class: android.companion.virtual.VirtualDeviceParams$Builder$VirtualSensorCallbackDelegate$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        VirtualDeviceParams.Builder.VirtualSensorCallbackDelegate.this.lambda$onDirectChannelDestroyed$2(i);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onDirectChannelDestroyed$2(int i) {
                this.mDirectChannelCallback.onDirectChannelDestroyed(i);
            }

            @Override // android.companion.virtual.sensor.IVirtualSensorCallback
            public void onDirectChannelConfigured(final int i, final VirtualSensor virtualSensor, final int i2, final int i3) {
                Executor executor;
                if (this.mDirectChannelCallback == null || (executor = this.mDirectChannelExecutor) == null) {
                    return;
                }
                executor.execute(new Runnable() { // from class: android.companion.virtual.VirtualDeviceParams$Builder$VirtualSensorCallbackDelegate$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        VirtualDeviceParams.Builder.VirtualSensorCallbackDelegate.this.lambda$onDirectChannelConfigured$3(i, virtualSensor, i2, i3);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onDirectChannelConfigured$3(int i, VirtualSensor virtualSensor, int i2, int i3) {
                this.mDirectChannelCallback.onDirectChannelConfigured(i, virtualSensor, i2, i3);
            }
        }

        public Builder setLockState(int i) {
            this.mLockState = i;
            return this;
        }

        public Builder setDimDuration(Duration duration) {
            if (((Duration) Objects.requireNonNull(duration)).compareTo(Duration.ZERO) < 0) {
                throw new IllegalArgumentException("The dim duration cannot be negative");
            }
            this.mDimDuration = duration;
            return this;
        }

        public Builder setScreenOffTimeout(Duration duration) {
            if (((Duration) Objects.requireNonNull(duration)).compareTo(Duration.ZERO) < 0) {
                throw new IllegalArgumentException("The screen off timeout cannot be negative");
            }
            this.mScreenOffTimeout = duration;
            return this;
        }

        public Builder setHomeComponent(ComponentName componentName) {
            this.mHomeComponent = componentName;
            return this;
        }

        public Builder setInputMethodComponent(ComponentName componentName) {
            this.mInputMethodComponent = componentName;
            return this;
        }

        public Builder setUsersWithMatchingAccounts(Set<UserHandle> set) {
            this.mUsersWithMatchingAccounts = (Set) Objects.requireNonNull(set);
            return this;
        }

        @Deprecated
        public Builder setAllowedCrossTaskNavigations(Set<ComponentName> set) {
            if (this.mDefaultNavigationPolicyConfigured && this.mDefaultNavigationPolicy != 1) {
                throw new IllegalArgumentException("Allowed cross task navigations and blocked cross task navigations cannot  both be set.");
            }
            this.mDefaultNavigationPolicy = 1;
            this.mDefaultNavigationPolicyConfigured = true;
            this.mCrossTaskNavigationExemptions = (Set) Objects.requireNonNull(set);
            return this;
        }

        @Deprecated
        public Builder setBlockedCrossTaskNavigations(Set<ComponentName> set) {
            if (this.mDefaultNavigationPolicyConfigured && this.mDefaultNavigationPolicy != 0) {
                throw new IllegalArgumentException("Allowed cross task navigation and blocked task navigation cannot  be set.");
            }
            this.mDefaultNavigationPolicy = 0;
            this.mDefaultNavigationPolicyConfigured = true;
            this.mCrossTaskNavigationExemptions = (Set) Objects.requireNonNull(set);
            return this;
        }

        @Deprecated
        public Builder setAllowedActivities(Set<ComponentName> set) {
            if (this.mDefaultActivityPolicyConfigured && this.mDefaultActivityPolicy != 1) {
                throw new IllegalArgumentException("Allowed activities and Blocked activities cannot both be set.");
            }
            this.mDefaultActivityPolicy = 1;
            this.mDefaultActivityPolicyConfigured = true;
            this.mActivityPolicyExemptions = (Set) Objects.requireNonNull(set);
            return this;
        }

        @Deprecated
        public Builder setBlockedActivities(Set<ComponentName> set) {
            if (this.mDefaultActivityPolicyConfigured && this.mDefaultActivityPolicy != 0) {
                throw new IllegalArgumentException("Allowed activities and Blocked activities cannot both be set.");
            }
            this.mDefaultActivityPolicy = 0;
            this.mDefaultActivityPolicyConfigured = true;
            this.mActivityPolicyExemptions = (Set) Objects.requireNonNull(set);
            return this;
        }

        public Builder setName(String str) {
            this.mName = str;
            return this;
        }

        public Builder setDevicePolicy(int i, int i2) {
            this.mDevicePolicies.put(i, i2);
            return this;
        }

        public Builder addVirtualSensorConfig(VirtualSensorConfig virtualSensorConfig) {
            this.mVirtualSensorConfigs.add((VirtualSensorConfig) Objects.requireNonNull(virtualSensorConfig));
            return this;
        }

        public Builder setVirtualSensorCallback(Executor executor, VirtualSensorCallback virtualSensorCallback) {
            this.mVirtualSensorCallbackExecutor = (Executor) Objects.requireNonNull(executor);
            this.mVirtualSensorCallback = (VirtualSensorCallback) Objects.requireNonNull(virtualSensorCallback);
            return this;
        }

        public Builder setVirtualSensorDirectChannelCallback(Executor executor, VirtualSensorDirectChannelCallback virtualSensorDirectChannelCallback) {
            this.mVirtualSensorDirectChannelCallbackExecutor = (Executor) Objects.requireNonNull(executor);
            this.mVirtualSensorDirectChannelCallback = (VirtualSensorDirectChannelCallback) Objects.requireNonNull(virtualSensorDirectChannelCallback);
            return this;
        }

        public Builder setAudioPlaybackSessionId(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("Invalid playback audio session id");
            }
            this.mAudioPlaybackSessionId = i;
            return this;
        }

        public Builder setAudioRecordingSessionId(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("Invalid recording audio session id");
            }
            this.mAudioRecordingSessionId = i;
            return this;
        }

        public VirtualDeviceParams build() {
            VirtualSensorCallbackDelegate virtualSensorCallbackDelegate;
            if (this.mVirtualSensorConfigs.isEmpty()) {
                virtualSensorCallbackDelegate = null;
            } else {
                if (this.mDevicePolicies.get(0, 0) != 1) {
                    throw new IllegalArgumentException("DEVICE_POLICY_CUSTOM for POLICY_TYPE_SENSORS is required for creating virtual sensors.");
                }
                if (this.mVirtualSensorCallback == null) {
                    throw new IllegalArgumentException("VirtualSensorCallback is required for creating virtual sensors.");
                }
                int i = 0;
                while (true) {
                    if (i >= this.mVirtualSensorConfigs.size()) {
                        break;
                    }
                    if (this.mVirtualSensorConfigs.get(i).getDirectChannelTypesSupported() <= 0) {
                        i++;
                    } else if (this.mVirtualSensorDirectChannelCallback == null) {
                        throw new IllegalArgumentException("VirtualSensorDirectChannelCallback is required for creating virtual sensors that support direct channel.");
                    }
                }
                virtualSensorCallbackDelegate = new VirtualSensorCallbackDelegate(this.mVirtualSensorCallbackExecutor, this.mVirtualSensorCallback, this.mVirtualSensorDirectChannelCallbackExecutor, this.mVirtualSensorDirectChannelCallback);
            }
            VirtualSensorCallbackDelegate virtualSensorCallbackDelegate2 = virtualSensorCallbackDelegate;
            int i2 = this.mDevicePolicies.get(3, -1);
            if (i2 != 0) {
                if (i2 == 1) {
                    if (this.mDefaultActivityPolicyConfigured && this.mDefaultActivityPolicy == 0) {
                        throw new IllegalArgumentException("DEVICE_POLICY_CUSTOM is explicitly configured for POLICY_TYPE_ACTIVITY, which is exclusive with setBlockedActivities.");
                    }
                } else if (this.mDefaultActivityPolicyConfigured && this.mDefaultActivityPolicy == 1) {
                    this.mDevicePolicies.put(3, 1);
                }
            } else if (this.mDefaultActivityPolicyConfigured && this.mDefaultActivityPolicy == 1) {
                throw new IllegalArgumentException("DEVICE_POLICY_DEFAULT is explicitly configured for POLICY_TYPE_ACTIVITY, which is exclusive with setAllowedActivities.");
            }
            if (this.mDimDuration.compareTo(this.mScreenOffTimeout) > 0) {
                throw new IllegalArgumentException("The dim duration cannot be greater than the screen off timeout.");
            }
            if (this.mScreenOffTimeout.compareTo(Duration.ZERO) == 0) {
                this.mScreenOffTimeout = INFINITE_TIMEOUT;
            }
            if (!Flags.defaultDeviceCameraAccessPolicy()) {
                this.mDevicePolicies.delete(7);
            }
            if (!Flags.activityControlApi()) {
                this.mDevicePolicies.delete(6);
            }
            if ((this.mAudioPlaybackSessionId != 0 || this.mAudioRecordingSessionId != 0) && this.mDevicePolicies.get(1, 0) != 1) {
                throw new IllegalArgumentException("DEVICE_POLICY_CUSTOM for POLICY_TYPE_AUDIO is required for configuration of device-specific audio session ids.");
            }
            SparseArray sparseArray = new SparseArray();
            for (int i3 = 0; i3 < this.mVirtualSensorConfigs.size(); i3++) {
                VirtualSensorConfig virtualSensorConfig = this.mVirtualSensorConfigs.get(i3);
                Set set = (Set) sparseArray.get(virtualSensorConfig.getType(), new ArraySet());
                if (!set.add(virtualSensorConfig.getName())) {
                    throw new IllegalArgumentException("Sensor names must be unique for a particular sensor type.");
                }
                sparseArray.put(virtualSensorConfig.getType(), set);
            }
            return new VirtualDeviceParams(this.mLockState, this.mUsersWithMatchingAccounts, this.mDefaultNavigationPolicy, this.mCrossTaskNavigationExemptions, this.mDefaultActivityPolicy, this.mActivityPolicyExemptions, this.mName, this.mDevicePolicies, this.mHomeComponent, this.mInputMethodComponent, this.mVirtualSensorConfigs, virtualSensorCallbackDelegate2, this.mAudioPlaybackSessionId, this.mAudioRecordingSessionId, this.mDimDuration.toMillis(), this.mScreenOffTimeout.toMillis());
        }
    }
}
