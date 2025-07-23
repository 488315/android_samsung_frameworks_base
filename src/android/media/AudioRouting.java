package android.media;

import android.os.Handler;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public interface AudioRouting {

    public interface OnRoutingChangedListener {
        void onRoutingChanged(AudioRouting audioRouting);
    }

    void addOnRoutingChangedListener(OnRoutingChangedListener onRoutingChangedListener, Handler handler);

    AudioDeviceInfo getPreferredDevice();

    AudioDeviceInfo getRoutedDevice();

    void removeOnRoutingChangedListener(OnRoutingChangedListener onRoutingChangedListener);

    boolean setPreferredDevice(AudioDeviceInfo audioDeviceInfo);

    default List<AudioDeviceInfo> getRoutedDevices() {
        ArrayList arrayList = new ArrayList();
        AudioDeviceInfo routedDevice = getRoutedDevice();
        if (routedDevice != null) {
            arrayList.add(routedDevice);
        }
        return new ArrayList();
    }
}
