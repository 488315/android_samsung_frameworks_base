package android.os;

import android.annotation.SystemApi;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArrayMap;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

@SystemApi
/* loaded from: classes3.dex */
public final class BatterySaverPolicyConfig implements Parcelable {
    public static final Parcelable.Creator<BatterySaverPolicyConfig> CREATOR = new Parcelable.Creator<BatterySaverPolicyConfig>() { // from class: android.os.BatterySaverPolicyConfig.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BatterySaverPolicyConfig createFromParcel(Parcel parcel) {
            return new BatterySaverPolicyConfig(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BatterySaverPolicyConfig[] newArray(int i) {
            return new BatterySaverPolicyConfig[i];
        }
    };
    private final float mAdjustBrightnessFactor;
    private final boolean mAdvertiseIsEnabled;
    private final boolean mDeferFullBackup;
    private final boolean mDeferKeyValueBackup;
    private final Map<String, String> mDeviceSpecificSettings;
    private final boolean mDisableAnimation;
    private final boolean mDisableAod;
    private final boolean mDisableLaunchBoost;
    private final boolean mDisableOptionalSensors;
    private final boolean mDisableVibration;
    private final boolean mEnableAdjustBrightness;
    private final boolean mEnableDataSaver;
    private final boolean mEnableFirewall;
    private final boolean mEnableNightMode;
    private final boolean mEnableQuickDoze;
    private final boolean mForceAllAppsStandby;
    private final boolean mForceBackgroundCheck;
    private final int mLocationMode;
    private final int mSoundTriggerMode;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private BatterySaverPolicyConfig(Builder builder) {
        this.mAdjustBrightnessFactor = Math.max(0.0f, Math.min(builder.mAdjustBrightnessFactor, 1.0f));
        this.mAdvertiseIsEnabled = builder.mAdvertiseIsEnabled;
        this.mDeferFullBackup = builder.mDeferFullBackup;
        this.mDeferKeyValueBackup = builder.mDeferKeyValueBackup;
        this.mDeviceSpecificSettings = Collections.unmodifiableMap(new ArrayMap(builder.mDeviceSpecificSettings));
        this.mDisableAnimation = builder.mDisableAnimation;
        this.mDisableAod = builder.mDisableAod;
        this.mDisableLaunchBoost = builder.mDisableLaunchBoost;
        this.mDisableOptionalSensors = builder.mDisableOptionalSensors;
        this.mDisableVibration = builder.mDisableVibration;
        this.mEnableAdjustBrightness = builder.mEnableAdjustBrightness;
        this.mEnableDataSaver = builder.mEnableDataSaver;
        this.mEnableFirewall = builder.mEnableFirewall;
        this.mEnableNightMode = builder.mEnableNightMode;
        this.mEnableQuickDoze = builder.mEnableQuickDoze;
        this.mForceAllAppsStandby = builder.mForceAllAppsStandby;
        this.mForceBackgroundCheck = builder.mForceBackgroundCheck;
        this.mLocationMode = Math.max(0, Math.min(builder.mLocationMode, 4));
        this.mSoundTriggerMode = Math.max(0, Math.min(builder.mSoundTriggerMode, 2));
    }

    private BatterySaverPolicyConfig(Parcel parcel) {
        this.mAdjustBrightnessFactor = Math.max(0.0f, Math.min(parcel.readFloat(), 1.0f));
        this.mAdvertiseIsEnabled = parcel.readBoolean();
        this.mDeferFullBackup = parcel.readBoolean();
        this.mDeferKeyValueBackup = parcel.readBoolean();
        int readInt = parcel.readInt();
        ArrayMap arrayMap = new ArrayMap(readInt);
        for (int i = 0; i < readInt; i++) {
            String emptyIfNull = TextUtils.emptyIfNull(parcel.readString());
            String emptyIfNull2 = TextUtils.emptyIfNull(parcel.readString());
            if (!emptyIfNull.trim().isEmpty()) {
                arrayMap.put(emptyIfNull, emptyIfNull2);
            }
        }
        this.mDeviceSpecificSettings = Collections.unmodifiableMap(arrayMap);
        this.mDisableAnimation = parcel.readBoolean();
        this.mDisableAod = parcel.readBoolean();
        this.mDisableLaunchBoost = parcel.readBoolean();
        this.mDisableOptionalSensors = parcel.readBoolean();
        this.mDisableVibration = parcel.readBoolean();
        this.mEnableAdjustBrightness = parcel.readBoolean();
        this.mEnableDataSaver = parcel.readBoolean();
        this.mEnableFirewall = parcel.readBoolean();
        this.mEnableNightMode = parcel.readBoolean();
        this.mEnableQuickDoze = parcel.readBoolean();
        this.mForceAllAppsStandby = parcel.readBoolean();
        this.mForceBackgroundCheck = parcel.readBoolean();
        this.mLocationMode = Math.max(0, Math.min(parcel.readInt(), 4));
        this.mSoundTriggerMode = Math.max(0, Math.min(parcel.readInt(), 2));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.mAdjustBrightnessFactor);
        parcel.writeBoolean(this.mAdvertiseIsEnabled);
        parcel.writeBoolean(this.mDeferFullBackup);
        parcel.writeBoolean(this.mDeferKeyValueBackup);
        Set<Map.Entry<String, String>> entrySet = this.mDeviceSpecificSettings.entrySet();
        parcel.writeInt(entrySet.size());
        for (Map.Entry<String, String> entry : entrySet) {
            parcel.writeString(entry.getKey());
            parcel.writeString(entry.getValue());
        }
        parcel.writeBoolean(this.mDisableAnimation);
        parcel.writeBoolean(this.mDisableAod);
        parcel.writeBoolean(this.mDisableLaunchBoost);
        parcel.writeBoolean(this.mDisableOptionalSensors);
        parcel.writeBoolean(this.mDisableVibration);
        parcel.writeBoolean(this.mEnableAdjustBrightness);
        parcel.writeBoolean(this.mEnableDataSaver);
        parcel.writeBoolean(this.mEnableFirewall);
        parcel.writeBoolean(this.mEnableNightMode);
        parcel.writeBoolean(this.mEnableQuickDoze);
        parcel.writeBoolean(this.mForceAllAppsStandby);
        parcel.writeBoolean(this.mForceBackgroundCheck);
        parcel.writeInt(this.mLocationMode);
        parcel.writeInt(this.mSoundTriggerMode);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : this.mDeviceSpecificSettings.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append(",");
        }
        StringBuilder sb2 = new StringBuilder("adjust_brightness_disabled=");
        sb2.append(!this.mEnableAdjustBrightness);
        sb2.append(",adjust_brightness_factor=");
        sb2.append(this.mAdjustBrightnessFactor);
        sb2.append(",advertise_is_enabled=");
        sb2.append(this.mAdvertiseIsEnabled);
        sb2.append(",animation_disabled=");
        sb2.append(this.mDisableAnimation);
        sb2.append(",aod_disabled=");
        sb2.append(this.mDisableAod);
        sb2.append(",datasaver_disabled=");
        sb2.append(!this.mEnableDataSaver);
        sb2.append(",enable_night_mode=");
        sb2.append(this.mEnableNightMode);
        sb2.append(",firewall_disabled=");
        sb2.append(!this.mEnableFirewall);
        sb2.append(",force_all_apps_standby=");
        sb2.append(this.mForceAllAppsStandby);
        sb2.append(",force_background_check=");
        sb2.append(this.mForceBackgroundCheck);
        sb2.append(",fullbackup_deferred=");
        sb2.append(this.mDeferFullBackup);
        sb2.append(",gps_mode=");
        sb2.append(this.mLocationMode);
        sb2.append(",keyvaluebackup_deferred=");
        sb2.append(this.mDeferKeyValueBackup);
        sb2.append(",launch_boost_disabled=");
        sb2.append(this.mDisableLaunchBoost);
        sb2.append(",optional_sensors_disabled=");
        sb2.append(this.mDisableOptionalSensors);
        sb2.append(",quick_doze_enabled=");
        sb2.append(this.mEnableQuickDoze);
        sb2.append(",soundtrigger_mode=");
        sb2.append(this.mSoundTriggerMode);
        sb2.append(",vibration_disabled=");
        sb2.append(this.mDisableVibration);
        sb2.append(",");
        sb2.append(sb.toString());
        return sb2.toString();
    }

    public float getAdjustBrightnessFactor() {
        return this.mAdjustBrightnessFactor;
    }

    public boolean getAdvertiseIsEnabled() {
        return this.mAdvertiseIsEnabled;
    }

    public boolean getDeferFullBackup() {
        return this.mDeferFullBackup;
    }

    public boolean getDeferKeyValueBackup() {
        return this.mDeferKeyValueBackup;
    }

    public Map<String, String> getDeviceSpecificSettings() {
        return this.mDeviceSpecificSettings;
    }

    public boolean getDisableAnimation() {
        return this.mDisableAnimation;
    }

    public boolean getDisableAod() {
        return this.mDisableAod;
    }

    public boolean getDisableLaunchBoost() {
        return this.mDisableLaunchBoost;
    }

    public boolean getDisableOptionalSensors() {
        return this.mDisableOptionalSensors;
    }

    public int getSoundTriggerMode() {
        return this.mSoundTriggerMode;
    }

    @Deprecated
    public boolean getDisableSoundTrigger() {
        return this.mSoundTriggerMode == 2;
    }

    public boolean getDisableVibration() {
        return this.mDisableVibration;
    }

    public boolean getEnableAdjustBrightness() {
        return this.mEnableAdjustBrightness;
    }

    public boolean getEnableDataSaver() {
        return this.mEnableDataSaver;
    }

    public boolean getEnableFirewall() {
        return this.mEnableFirewall;
    }

    public boolean getEnableNightMode() {
        return this.mEnableNightMode;
    }

    public boolean getEnableQuickDoze() {
        return this.mEnableQuickDoze;
    }

    public boolean getForceAllAppsStandby() {
        return this.mForceAllAppsStandby;
    }

    public boolean getForceBackgroundCheck() {
        return this.mForceBackgroundCheck;
    }

    public int getLocationMode() {
        return this.mLocationMode;
    }

    public static final class Builder {
        private float mAdjustBrightnessFactor;
        private boolean mAdvertiseIsEnabled;
        private boolean mDeferFullBackup;
        private boolean mDeferKeyValueBackup;
        private final ArrayMap<String, String> mDeviceSpecificSettings;
        private boolean mDisableAnimation;
        private boolean mDisableAod;
        private boolean mDisableLaunchBoost;
        private boolean mDisableOptionalSensors;
        private boolean mDisableVibration;
        private boolean mEnableAdjustBrightness;
        private boolean mEnableDataSaver;
        private boolean mEnableFirewall;
        private boolean mEnableNightMode;
        private boolean mEnableQuickDoze;
        private boolean mForceAllAppsStandby;
        private boolean mForceBackgroundCheck;
        private int mLocationMode;
        private int mSoundTriggerMode;

        public Builder() {
            this.mAdjustBrightnessFactor = 1.0f;
            this.mAdvertiseIsEnabled = false;
            this.mDeferFullBackup = false;
            this.mDeferKeyValueBackup = false;
            this.mDeviceSpecificSettings = new ArrayMap<>();
            this.mDisableAnimation = false;
            this.mDisableAod = false;
            this.mDisableLaunchBoost = false;
            this.mDisableOptionalSensors = false;
            this.mDisableVibration = false;
            this.mEnableAdjustBrightness = false;
            this.mEnableDataSaver = false;
            this.mEnableFirewall = false;
            this.mEnableNightMode = false;
            this.mEnableQuickDoze = false;
            this.mForceAllAppsStandby = false;
            this.mForceBackgroundCheck = false;
            this.mLocationMode = 0;
            this.mSoundTriggerMode = 0;
        }

        public Builder(BatterySaverPolicyConfig batterySaverPolicyConfig) {
            this.mAdjustBrightnessFactor = 1.0f;
            this.mAdvertiseIsEnabled = false;
            this.mDeferFullBackup = false;
            this.mDeferKeyValueBackup = false;
            this.mDeviceSpecificSettings = new ArrayMap<>();
            this.mDisableAnimation = false;
            this.mDisableAod = false;
            this.mDisableLaunchBoost = false;
            this.mDisableOptionalSensors = false;
            this.mDisableVibration = false;
            this.mEnableAdjustBrightness = false;
            this.mEnableDataSaver = false;
            this.mEnableFirewall = false;
            this.mEnableNightMode = false;
            this.mEnableQuickDoze = false;
            this.mForceAllAppsStandby = false;
            this.mForceBackgroundCheck = false;
            this.mLocationMode = 0;
            this.mSoundTriggerMode = 0;
            this.mAdjustBrightnessFactor = batterySaverPolicyConfig.getAdjustBrightnessFactor();
            this.mAdvertiseIsEnabled = batterySaverPolicyConfig.getAdvertiseIsEnabled();
            this.mDeferFullBackup = batterySaverPolicyConfig.getDeferFullBackup();
            this.mDeferKeyValueBackup = batterySaverPolicyConfig.getDeferKeyValueBackup();
            for (String str : batterySaverPolicyConfig.getDeviceSpecificSettings().keySet()) {
                this.mDeviceSpecificSettings.put(str, batterySaverPolicyConfig.getDeviceSpecificSettings().get(str));
            }
            this.mDisableAnimation = batterySaverPolicyConfig.getDisableAnimation();
            this.mDisableAod = batterySaverPolicyConfig.getDisableAod();
            this.mDisableLaunchBoost = batterySaverPolicyConfig.getDisableLaunchBoost();
            this.mDisableOptionalSensors = batterySaverPolicyConfig.getDisableOptionalSensors();
            this.mDisableVibration = batterySaverPolicyConfig.getDisableVibration();
            this.mEnableAdjustBrightness = batterySaverPolicyConfig.getEnableAdjustBrightness();
            this.mEnableDataSaver = batterySaverPolicyConfig.getEnableDataSaver();
            this.mEnableFirewall = batterySaverPolicyConfig.getEnableFirewall();
            this.mEnableNightMode = batterySaverPolicyConfig.getEnableNightMode();
            this.mEnableQuickDoze = batterySaverPolicyConfig.getEnableQuickDoze();
            this.mForceAllAppsStandby = batterySaverPolicyConfig.getForceAllAppsStandby();
            this.mForceBackgroundCheck = batterySaverPolicyConfig.getForceBackgroundCheck();
            this.mLocationMode = batterySaverPolicyConfig.getLocationMode();
            this.mSoundTriggerMode = batterySaverPolicyConfig.getSoundTriggerMode();
        }

        public Builder setAdjustBrightnessFactor(float f) {
            this.mAdjustBrightnessFactor = f;
            return this;
        }

        public Builder setAdvertiseIsEnabled(boolean z) {
            this.mAdvertiseIsEnabled = z;
            return this;
        }

        public Builder setDeferFullBackup(boolean z) {
            this.mDeferFullBackup = z;
            return this;
        }

        public Builder setDeferKeyValueBackup(boolean z) {
            this.mDeferKeyValueBackup = z;
            return this;
        }

        public Builder addDeviceSpecificSetting(String str, String str2) {
            if (str == null) {
                throw new IllegalArgumentException("Key cannot be null");
            }
            String trim = str.trim();
            if (TextUtils.isEmpty(trim)) {
                throw new IllegalArgumentException("Key cannot be empty");
            }
            this.mDeviceSpecificSettings.put(trim, TextUtils.emptyIfNull(str2));
            return this;
        }

        public Builder setDisableAnimation(boolean z) {
            this.mDisableAnimation = z;
            return this;
        }

        public Builder setDisableAod(boolean z) {
            this.mDisableAod = z;
            return this;
        }

        public Builder setDisableLaunchBoost(boolean z) {
            this.mDisableLaunchBoost = z;
            return this;
        }

        public Builder setDisableOptionalSensors(boolean z) {
            this.mDisableOptionalSensors = z;
            return this;
        }

        @Deprecated
        public Builder setDisableSoundTrigger(boolean z) {
            if (z) {
                this.mSoundTriggerMode = 2;
                return this;
            }
            this.mSoundTriggerMode = 0;
            return this;
        }

        public Builder setSoundTriggerMode(int i) {
            this.mSoundTriggerMode = i;
            return this;
        }

        public Builder setDisableVibration(boolean z) {
            this.mDisableVibration = z;
            return this;
        }

        public Builder setEnableAdjustBrightness(boolean z) {
            this.mEnableAdjustBrightness = z;
            return this;
        }

        public Builder setEnableDataSaver(boolean z) {
            this.mEnableDataSaver = z;
            return this;
        }

        public Builder setEnableFirewall(boolean z) {
            this.mEnableFirewall = z;
            return this;
        }

        public Builder setEnableNightMode(boolean z) {
            this.mEnableNightMode = z;
            return this;
        }

        public Builder setEnableQuickDoze(boolean z) {
            this.mEnableQuickDoze = z;
            return this;
        }

        public Builder setForceAllAppsStandby(boolean z) {
            this.mForceAllAppsStandby = z;
            return this;
        }

        public Builder setForceBackgroundCheck(boolean z) {
            this.mForceBackgroundCheck = z;
            return this;
        }

        public Builder setLocationMode(int i) {
            this.mLocationMode = i;
            return this;
        }

        public BatterySaverPolicyConfig build() {
            return new BatterySaverPolicyConfig(this);
        }
    }
}
