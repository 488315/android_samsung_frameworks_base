package com.android.systemui.volume.domain.model;

import android.graphics.drawable.Drawable;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import kotlin.jvm.internal.Intrinsics;

public interface AudioOutputDevice {

    public final class Bluetooth implements AudioOutputDevice {
        public final CachedBluetoothDevice cachedBluetoothDevice;
        public final Drawable icon;
        public final String name;

        public Bluetooth(String str, Drawable drawable, CachedBluetoothDevice cachedBluetoothDevice) {
            this.name = str;
            this.icon = drawable;
            this.cachedBluetoothDevice = cachedBluetoothDevice;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Bluetooth)) {
                return false;
            }
            Bluetooth bluetooth = (Bluetooth) obj;
            return Intrinsics.areEqual(this.name, bluetooth.name) && Intrinsics.areEqual(this.icon, bluetooth.icon) && Intrinsics.areEqual(this.cachedBluetoothDevice, bluetooth.cachedBluetoothDevice);
        }

        @Override // com.android.systemui.volume.domain.model.AudioOutputDevice
        public final Drawable getIcon() {
            return this.icon;
        }

        @Override // com.android.systemui.volume.domain.model.AudioOutputDevice
        public final String getName() {
            return this.name;
        }

        public final int hashCode() {
            int hashCode = this.name.hashCode() * 31;
            Drawable drawable = this.icon;
            return this.cachedBluetoothDevice.hashCode() + ((hashCode + (drawable == null ? 0 : drawable.hashCode())) * 31);
        }

        public final String toString() {
            return "Bluetooth(name=" + this.name + ", icon=" + this.icon + ", cachedBluetoothDevice=" + this.cachedBluetoothDevice + ")";
        }
    }

    public final class BuiltIn implements AudioOutputDevice {
        public final Drawable icon;
        public final String name;

        public BuiltIn(String str, Drawable drawable) {
            this.name = str;
            this.icon = drawable;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof BuiltIn)) {
                return false;
            }
            BuiltIn builtIn = (BuiltIn) obj;
            return Intrinsics.areEqual(this.name, builtIn.name) && Intrinsics.areEqual(this.icon, builtIn.icon);
        }

        @Override // com.android.systemui.volume.domain.model.AudioOutputDevice
        public final Drawable getIcon() {
            return this.icon;
        }

        @Override // com.android.systemui.volume.domain.model.AudioOutputDevice
        public final String getName() {
            return this.name;
        }

        public final int hashCode() {
            int hashCode = this.name.hashCode() * 31;
            Drawable drawable = this.icon;
            return hashCode + (drawable == null ? 0 : drawable.hashCode());
        }

        public final String toString() {
            return "BuiltIn(name=" + this.name + ", icon=" + this.icon + ")";
        }
    }

    public final class Unknown implements AudioOutputDevice {
        public static final Unknown INSTANCE = new Unknown();

        private Unknown() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Unknown);
        }

        @Override // com.android.systemui.volume.domain.model.AudioOutputDevice
        public final Drawable getIcon() {
            throw new IllegalStateException("Unsupported for unknown device".toString());
        }

        @Override // com.android.systemui.volume.domain.model.AudioOutputDevice
        public final String getName() {
            throw new IllegalStateException("Unsupported for unknown device".toString());
        }

        public final int hashCode() {
            return -25976529;
        }

        public final String toString() {
            return "Unknown";
        }
    }

    public final class Wired implements AudioOutputDevice {
        public final Drawable icon;
        public final String name;

        public Wired(String str, Drawable drawable) {
            this.name = str;
            this.icon = drawable;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Wired)) {
                return false;
            }
            Wired wired = (Wired) obj;
            return Intrinsics.areEqual(this.name, wired.name) && Intrinsics.areEqual(this.icon, wired.icon);
        }

        @Override // com.android.systemui.volume.domain.model.AudioOutputDevice
        public final Drawable getIcon() {
            return this.icon;
        }

        @Override // com.android.systemui.volume.domain.model.AudioOutputDevice
        public final String getName() {
            return this.name;
        }

        public final int hashCode() {
            int hashCode = this.name.hashCode() * 31;
            Drawable drawable = this.icon;
            return hashCode + (drawable == null ? 0 : drawable.hashCode());
        }

        public final String toString() {
            return "Wired(name=" + this.name + ", icon=" + this.icon + ")";
        }
    }

    Drawable getIcon();

    String getName();
}
