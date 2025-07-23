package android.provider;

import android.provider.DeviceConfig;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public interface DeviceConfigInterface {
    public static final DeviceConfigInterface REAL = new DeviceConfigInterface() { // from class: android.provider.DeviceConfigInterface.1
        @Override // android.provider.DeviceConfigInterface
        public String getProperty(String str, String str2) {
            return DeviceConfig.getProperty(str, str2);
        }

        @Override // android.provider.DeviceConfigInterface
        public DeviceConfig.Properties getProperties(String str, String... strArr) {
            return DeviceConfig.getProperties(str, strArr);
        }

        @Override // android.provider.DeviceConfigInterface
        public boolean setProperty(String str, String str2, String str3, boolean z) {
            return DeviceConfig.setProperty(str, str2, str3, z);
        }

        @Override // android.provider.DeviceConfigInterface
        public boolean setProperties(DeviceConfig.Properties properties) throws DeviceConfig.BadConfigException {
            return DeviceConfig.setProperties(properties);
        }

        @Override // android.provider.DeviceConfigInterface
        public boolean deleteProperty(String str, String str2) {
            return DeviceConfig.deleteProperty(str, str2);
        }

        @Override // android.provider.DeviceConfigInterface
        public void resetToDefaults(int i, String str) {
            DeviceConfig.resetToDefaults(i, str);
        }

        @Override // android.provider.DeviceConfigInterface
        public String getString(String str, String str2, String str3) {
            return DeviceConfig.getString(str, str2, str3);
        }

        @Override // android.provider.DeviceConfigInterface
        public int getInt(String str, String str2, int i) {
            return DeviceConfig.getInt(str, str2, i);
        }

        @Override // android.provider.DeviceConfigInterface
        public long getLong(String str, String str2, long j) {
            return DeviceConfig.getLong(str, str2, j);
        }

        @Override // android.provider.DeviceConfigInterface
        public boolean getBoolean(String str, String str2, boolean z) {
            return DeviceConfig.getBoolean(str, str2, z);
        }

        @Override // android.provider.DeviceConfigInterface
        public float getFloat(String str, String str2, float f) {
            return DeviceConfig.getFloat(str, str2, f);
        }

        @Override // android.provider.DeviceConfigInterface
        public void addOnPropertiesChangedListener(String str, Executor executor, DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener) {
            DeviceConfig.addOnPropertiesChangedListener(str, executor, onPropertiesChangedListener);
        }

        @Override // android.provider.DeviceConfigInterface
        public void removeOnPropertiesChangedListener(DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener) {
            DeviceConfig.removeOnPropertiesChangedListener(onPropertiesChangedListener);
        }
    };

    void addOnPropertiesChangedListener(String str, Executor executor, DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener);

    boolean deleteProperty(String str, String str2);

    boolean getBoolean(String str, String str2, boolean z);

    float getFloat(String str, String str2, float f);

    int getInt(String str, String str2, int i);

    long getLong(String str, String str2, long j);

    DeviceConfig.Properties getProperties(String str, String... strArr);

    String getProperty(String str, String str2);

    String getString(String str, String str2, String str3);

    void removeOnPropertiesChangedListener(DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener);

    void resetToDefaults(int i, String str);

    boolean setProperties(DeviceConfig.Properties properties) throws DeviceConfig.BadConfigException;

    boolean setProperty(String str, String str2, String str3, boolean z);
}
