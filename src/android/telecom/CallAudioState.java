package android.telecom;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes4.dex */
public final class CallAudioState implements Parcelable {
    public static final Parcelable.Creator<CallAudioState> CREATOR = new Parcelable.Creator<CallAudioState>() { // from class: android.telecom.CallAudioState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CallAudioState createFromParcel(Parcel parcel) {
            boolean z = parcel.readByte() != 0;
            int i = parcel.readInt();
            int i2 = parcel.readInt();
            BluetoothDevice bluetoothDevice = (BluetoothDevice) parcel.readParcelable(ClassLoader.getSystemClassLoader(), BluetoothDevice.class);
            ArrayList arrayList = new ArrayList();
            parcel.readParcelableList(arrayList, ClassLoader.getSystemClassLoader(), BluetoothDevice.class);
            return new CallAudioState(z, i, i2, bluetoothDevice, arrayList);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CallAudioState[] newArray(int i) {
            return new CallAudioState[i];
        }
    };
    public static final int ROUTE_ALL = 31;
    public static final int ROUTE_BLUETOOTH = 2;
    public static final int ROUTE_EARPIECE = 1;
    public static final int ROUTE_SPEAKER = 8;
    public static final int ROUTE_STREAMING = 16;
    public static final int ROUTE_WIRED_HEADSET = 4;
    public static final int ROUTE_WIRED_OR_EARPIECE = 5;
    private final BluetoothDevice activeBluetoothDevice;
    private final boolean isMuted;
    private final int route;
    private final Collection<BluetoothDevice> supportedBluetoothDevices;
    private final int supportedRouteMask;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CallAudioRoute {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CallAudioState(boolean z, int i, int i2) {
        this(z, i, i2, null, Collections.EMPTY_LIST);
    }

    public CallAudioState(boolean z, int i, int i2, BluetoothDevice bluetoothDevice, Collection<BluetoothDevice> collection) {
        this.isMuted = z;
        this.route = i;
        this.supportedRouteMask = i2;
        this.activeBluetoothDevice = bluetoothDevice;
        this.supportedBluetoothDevices = collection;
    }

    public CallAudioState(CallAudioState callAudioState) {
        this.isMuted = callAudioState.isMuted();
        this.route = callAudioState.getRoute();
        this.supportedRouteMask = callAudioState.getSupportedRouteMask();
        this.activeBluetoothDevice = callAudioState.activeBluetoothDevice;
        this.supportedBluetoothDevices = callAudioState.getSupportedBluetoothDevices();
    }

    public CallAudioState(AudioState audioState) {
        this.isMuted = audioState.isMuted();
        this.route = audioState.getRoute();
        this.supportedRouteMask = audioState.getSupportedRouteMask();
        this.activeBluetoothDevice = null;
        this.supportedBluetoothDevices = Collections.EMPTY_LIST;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof CallAudioState)) {
            return false;
        }
        CallAudioState callAudioState = (CallAudioState) obj;
        if (this.supportedBluetoothDevices.size() != callAudioState.supportedBluetoothDevices.size()) {
            return false;
        }
        Iterator<BluetoothDevice> it = this.supportedBluetoothDevices.iterator();
        while (it.hasNext()) {
            if (!callAudioState.supportedBluetoothDevices.contains(it.next())) {
                return false;
            }
        }
        return Objects.equals(this.activeBluetoothDevice, callAudioState.activeBluetoothDevice) && isMuted() == callAudioState.isMuted() && getRoute() == callAudioState.getRoute() && getSupportedRouteMask() == callAudioState.getSupportedRouteMask();
    }

    public String toString() {
        return String.format(Locale.US, "[AudioState isMuted: %b, route: %s, supportedRouteMask: %s, activeBluetoothDevice: [%s], supportedBluetoothDevices: [%s]]", Boolean.valueOf(this.isMuted), audioRouteToString(this.route), audioRouteToString(this.supportedRouteMask), this.activeBluetoothDevice, (String) this.supportedBluetoothDevices.stream().map(new Function() { // from class: android.telecom.CallAudioState$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((BluetoothDevice) obj).toString();
            }
        }).collect(Collectors.joining(", ")));
    }

    public boolean isMuted() {
        return this.isMuted;
    }

    public int getRoute() {
        return this.route;
    }

    public int getSupportedRouteMask() {
        if (this.route == 16) {
            return 16;
        }
        return this.supportedRouteMask;
    }

    public int getRawSupportedRouteMask() {
        return this.supportedRouteMask;
    }

    public BluetoothDevice getActiveBluetoothDevice() {
        return this.activeBluetoothDevice;
    }

    public Collection<BluetoothDevice> getSupportedBluetoothDevices() {
        return this.supportedBluetoothDevices;
    }

    public static String audioRouteToString(int i) {
        if (i == 0 || (i & (-32)) != 0) {
            return "UNKNOWN";
        }
        StringBuffer stringBuffer = new StringBuffer();
        if ((i & 1) == 1) {
            listAppend(stringBuffer, "EARPIECE");
        }
        if ((i & 2) == 2) {
            listAppend(stringBuffer, "BLUETOOTH");
        }
        if ((i & 4) == 4) {
            listAppend(stringBuffer, "WIRED_HEADSET");
        }
        if ((i & 8) == 8) {
            listAppend(stringBuffer, "SPEAKER");
        }
        if ((i & 16) == 16) {
            listAppend(stringBuffer, "STREAMING");
        }
        return stringBuffer.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.isMuted ? (byte) 1 : (byte) 0);
        parcel.writeInt(this.route);
        parcel.writeInt(this.supportedRouteMask);
        parcel.writeParcelable(this.activeBluetoothDevice, 0);
        parcel.writeParcelableList(new ArrayList(this.supportedBluetoothDevices), 0);
    }

    private static void listAppend(StringBuffer stringBuffer, String str) {
        if (stringBuffer.length() > 0) {
            stringBuffer.append(", ");
        }
        stringBuffer.append(str);
    }
}
