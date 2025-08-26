package com.android.settingslib.bluetooth;

import android.content.Context;
import android.media.AudioDeviceAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.audiopolicy.AudioProductStrategy;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class HearingAidAudioRoutingHelper {
    public final AudioManager mAudioManager;

    public HearingAidAudioRoutingHelper(Context context) {
        this.mAudioManager = (AudioManager) context.getSystemService(AudioManager.class);
    }

    public static boolean matchAddress(CachedBluetoothDevice cachedBluetoothDevice, AudioDeviceInfo audioDeviceInfo) {
        final String address = audioDeviceInfo.getAddress();
        CachedBluetoothDevice cachedBluetoothDevice2 = cachedBluetoothDevice.mSubDevice;
        Set set = cachedBluetoothDevice.mMemberDevices;
        if (cachedBluetoothDevice.mDevice.getAddress().equals(address)) {
            return true;
        }
        if (cachedBluetoothDevice2 != null && cachedBluetoothDevice2.mDevice.getAddress().equals(address)) {
            return true;
        }
        HashSet hashSet = (HashSet) set;
        return !hashSet.isEmpty() && hashSet.stream().anyMatch(new Predicate() { // from class: com.android.settingslib.bluetooth.HearingAidAudioRoutingHelper$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((CachedBluetoothDevice) obj).mDevice.getAddress().equals(address);
            }
        });
    }

    public List<AudioProductStrategy> getAudioProductStrategies() {
        return AudioManager.getAudioProductStrategies();
    }

    public final boolean removePreferredDeviceForStrategies(List list) {
        Iterator it = list.iterator();
        boolean zRemovePreferredDeviceForStrategy = true;
        while (it.hasNext()) {
            AudioProductStrategy audioProductStrategy = (AudioProductStrategy) it.next();
            if (this.mAudioManager.getPreferredDeviceForStrategy(audioProductStrategy) != null) {
                zRemovePreferredDeviceForStrategy &= this.mAudioManager.removePreferredDeviceForStrategy(audioProductStrategy);
            }
        }
        return zRemovePreferredDeviceForStrategy;
    }

    public final boolean setPreferredInputDeviceForCalls(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        AudioDeviceAttributes audioDeviceAttributes = null;
        if (cachedBluetoothDevice != null && cachedBluetoothDevice.isHearingAidDevice()) {
            AudioDeviceInfo[] devices = this.mAudioManager.getDevices(1);
            int length = devices.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    break;
                }
                AudioDeviceInfo audioDeviceInfo = devices[i2];
                if (audioDeviceInfo.getType() == 26 && matchAddress(cachedBluetoothDevice, audioDeviceInfo)) {
                    audioDeviceAttributes = new AudioDeviceAttributes(audioDeviceInfo);
                    break;
                }
                i2++;
            }
        }
        if (audioDeviceAttributes == null) {
            Log.w("HearingAidAudioRoutingHelper", "Can not find expected input AudioDeviceAttributes for hearing device: " + cachedBluetoothDevice.mDevice.getAnonymizedAddress());
            return false;
        }
        if (i == 0) {
            return this.mAudioManager.clearPreferredDevicesForCapturePreset(7);
        }
        if (i == 1) {
            this.mAudioManager.clearPreferredDevicesForCapturePreset(7);
            return this.mAudioManager.setPreferredDeviceForCapturePreset(7, audioDeviceAttributes);
        }
        if (i != 2) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Unexpected routingValue: "));
        }
        this.mAudioManager.clearPreferredDevicesForCapturePreset(7);
        return this.mAudioManager.setPreferredDeviceForCapturePreset(7, HearingAidAudioRoutingConstants.BUILTIN_MIC);
    }
}
