package android.window;

import android.app.ActivityThread;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.hidden_from_bootclasspath.com.android.window.flags.Flags;
import com.android.server.LocalServices;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes5.dex */
public abstract class ConfigurationChangeSetting implements Parcelable {
    public static final Parcelable.Creator<ConfigurationChangeSetting> CREATOR = new CreatorImpl();
    public static final int SETTING_TYPE_DISPLAY_DENSITY = 0;
    public static final int SETTING_TYPE_FONT_SCALE = 1;
    public static final int SETTING_TYPE_UNKNOWN = -1;
    private final int mSettingType;

    public interface ConfigurationChangeSettingInternal {
        ConfigurationChangeSetting createImplFromParcel(int i, Parcel parcel);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SettingType {
    }

    public void apply(int i) {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private ConfigurationChangeSetting(int i) {
        if (!Flags.condenseConfigurationChangeForSimpleMode()) {
            throw new IllegalStateException("ConfigurationChangeSetting cannot be instantiated because the condenseConfigurationChangeForSimpleMode flag is not enabled. Please ensure this flag is enabled.");
        }
        this.mSettingType = i;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mSettingType);
    }

    public static class CreatorImpl implements Parcelable.Creator<ConfigurationChangeSetting> {
        private final boolean mIsSystem;

        private CreatorImpl() {
            this(ActivityThread.isSystem());
        }

        public CreatorImpl(boolean z) {
            this.mIsSystem = z;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConfigurationChangeSetting createFromParcel(Parcel parcel) {
            int readInt = parcel.readInt();
            if (this.mIsSystem) {
                return ((ConfigurationChangeSettingInternal) LocalServices.getService(ConfigurationChangeSettingInternal.class)).createImplFromParcel(readInt, parcel);
            }
            if (readInt == 0) {
                return DensitySetting.CREATOR.createFromParcel(parcel);
            }
            if (readInt == 1) {
                return FontScaleSetting.CREATOR.createFromParcel(parcel);
            }
            throw new IllegalArgumentException("Unknown setting type " + readInt);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ConfigurationChangeSetting[] newArray(int i) {
            return new ConfigurationChangeSetting[i];
        }
    }

    public static class DensitySetting extends ConfigurationChangeSetting {
        public static final Parcelable.Creator<DensitySetting> CREATOR = new Parcelable.Creator<DensitySetting>() { // from class: android.window.ConfigurationChangeSetting.DensitySetting.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DensitySetting createFromParcel(Parcel parcel) {
                return new DensitySetting(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public DensitySetting[] newArray(int i) {
                return new DensitySetting[i];
            }
        };
        protected final int mDensity;
        protected final int mDisplayId;

        public DensitySetting(int i, int i2) {
            super(0);
            this.mDisplayId = i;
            this.mDensity = i2;
        }

        protected DensitySetting(Parcel parcel) {
            this(parcel.readInt(), parcel.readInt());
        }

        @Override // android.window.ConfigurationChangeSetting, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mDisplayId);
            parcel.writeInt(this.mDensity);
        }

        public boolean equals(Object obj) {
            if (obj instanceof DensitySetting) {
                DensitySetting densitySetting = (DensitySetting) obj;
                if (this.mDisplayId == densitySetting.mDisplayId && this.mDensity == densitySetting.mDensity) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.mDisplayId), Integer.valueOf(this.mDensity));
        }
    }

    public static class FontScaleSetting extends ConfigurationChangeSetting {
        public static final Parcelable.Creator<FontScaleSetting> CREATOR = new Parcelable.Creator<FontScaleSetting>() { // from class: android.window.ConfigurationChangeSetting.FontScaleSetting.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public FontScaleSetting createFromParcel(Parcel parcel) {
                return new FontScaleSetting(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public FontScaleSetting[] newArray(int i) {
                return new FontScaleSetting[i];
            }
        };
        protected final float mFontScaleFactor;

        public FontScaleSetting(float f) {
            super(1);
            this.mFontScaleFactor = f;
        }

        protected FontScaleSetting(Parcel parcel) {
            this(parcel.readFloat());
        }

        @Override // android.window.ConfigurationChangeSetting, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeFloat(this.mFontScaleFactor);
        }

        public boolean equals(Object obj) {
            return (obj instanceof FontScaleSetting) && Float.compare(this.mFontScaleFactor, ((FontScaleSetting) obj).mFontScaleFactor) == 0;
        }

        public int hashCode() {
            return Objects.hash(Float.valueOf(this.mFontScaleFactor));
        }
    }
}
