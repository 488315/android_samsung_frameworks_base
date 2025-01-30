package com.android.server.audio;

import android.media.AudioAttributes;
import android.media.AudioDeviceAttributes;
import android.media.AudioMixerAttributes;
import android.media.AudioSystem;
import android.media.IDevicesForAttributesCallback;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes.dex */
public class AudioSystemAdapter implements AudioSystem.RoutingUpdateCallback, AudioSystem.VolumeRangeInitRequestCallback {
    public static OnRoutingUpdatedListener sRoutingListener;
    public static AudioSystemAdapter sSingletonDefaultAdapter;
    public static OnVolRangeInitRequestListener sVolRangeInitReqListener;
    public ConcurrentHashMap mDevicesForAttrCache;
    public int[] mMethodCacheHit;
    public static final Object sDeviceCacheLock = new Object();
    public static final Object sRoutingListenerLock = new Object();
    public static final Object sVolRangeInitReqListenerLock = new Object();
    public String[] mMethodNames = {"getDevicesForAttributes"};
    public ConcurrentHashMap mLastDevicesForAttr = new ConcurrentHashMap();
    public long mDevicesForAttributesCacheClearTimeMs = System.currentTimeMillis();
    public final ArrayMap mRegisteredAttributesMap = new ArrayMap();
    public final RemoteCallbackList mDevicesForAttributesCallbacks = new RemoteCallbackList();

    public interface OnRoutingUpdatedListener {
        void onRoutingUpdatedFromNative();
    }

    public interface OnVolRangeInitRequestListener {
        void onVolumeRangeInitRequestFromNative();
    }

    public void onRoutingUpdated() {
        OnRoutingUpdatedListener onRoutingUpdatedListener;
        invalidateRoutingCache();
        synchronized (sRoutingListenerLock) {
            onRoutingUpdatedListener = sRoutingListener;
        }
        if (onRoutingUpdatedListener != null) {
            onRoutingUpdatedListener.onRoutingUpdatedFromNative();
        }
        synchronized (this.mRegisteredAttributesMap) {
            int beginBroadcast = this.mDevicesForAttributesCallbacks.beginBroadcast();
            for (int i = 0; i < beginBroadcast; i++) {
                IDevicesForAttributesCallback broadcastItem = this.mDevicesForAttributesCallbacks.getBroadcastItem(i);
                List<Pair> list = (List) this.mRegisteredAttributesMap.get(broadcastItem.asBinder());
                if (list == null) {
                    throw new IllegalStateException("Attribute list must not be null");
                }
                for (Pair pair : list) {
                    ArrayList devicesForAttributes = getDevicesForAttributes((AudioAttributes) pair.first, ((Boolean) pair.second).booleanValue());
                    if (!this.mLastDevicesForAttr.containsKey(pair) || !sameDeviceList(devicesForAttributes, (List) this.mLastDevicesForAttr.get(pair))) {
                        try {
                            broadcastItem.onDevicesForAttributesChanged((AudioAttributes) pair.first, ((Boolean) pair.second).booleanValue(), devicesForAttributes);
                        } catch (RemoteException unused) {
                        }
                    }
                }
            }
            this.mDevicesForAttributesCallbacks.finishBroadcast();
        }
    }

    public static void setRoutingListener(OnRoutingUpdatedListener onRoutingUpdatedListener) {
        synchronized (sRoutingListenerLock) {
            sRoutingListener = onRoutingUpdatedListener;
        }
    }

    public void clearRoutingCache() {
        invalidateRoutingCache();
    }

    public void addOnDevicesForAttributesChangedListener(AudioAttributes audioAttributes, boolean z, IDevicesForAttributesCallback iDevicesForAttributesCallback) {
        Pair pair = new Pair(audioAttributes, Boolean.valueOf(z));
        synchronized (this.mRegisteredAttributesMap) {
            List list = (List) this.mRegisteredAttributesMap.get(iDevicesForAttributesCallback.asBinder());
            if (list == null) {
                list = new ArrayList();
                this.mRegisteredAttributesMap.put(iDevicesForAttributesCallback.asBinder(), list);
                this.mDevicesForAttributesCallbacks.register(iDevicesForAttributesCallback);
            }
            if (!list.contains(pair)) {
                list.add(pair);
            }
        }
        getDevicesForAttributes(audioAttributes, z);
    }

    public void removeOnDevicesForAttributesChangedListener(IDevicesForAttributesCallback iDevicesForAttributesCallback) {
        synchronized (this.mRegisteredAttributesMap) {
            if (!this.mRegisteredAttributesMap.containsKey(iDevicesForAttributesCallback.asBinder())) {
                Log.w("AudioSystemAdapter", "listener to be removed is not found.");
            } else {
                this.mRegisteredAttributesMap.remove(iDevicesForAttributesCallback.asBinder());
                this.mDevicesForAttributesCallbacks.unregister(iDevicesForAttributesCallback);
            }
        }
    }

    public final boolean sameDeviceList(List list, List list2) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (!list2.contains((AudioDeviceAttributes) it.next())) {
                return false;
            }
        }
        Iterator it2 = list2.iterator();
        while (it2.hasNext()) {
            if (!list.contains((AudioDeviceAttributes) it2.next())) {
                return false;
            }
        }
        return true;
    }

    public void onVolumeRangeInitializationRequested() {
        OnVolRangeInitRequestListener onVolRangeInitRequestListener;
        synchronized (sVolRangeInitReqListenerLock) {
            onVolRangeInitRequestListener = sVolRangeInitReqListener;
        }
        if (onVolRangeInitRequestListener != null) {
            onVolRangeInitRequestListener.onVolumeRangeInitRequestFromNative();
        }
    }

    public static void setVolRangeInitReqListener(OnVolRangeInitRequestListener onVolRangeInitRequestListener) {
        synchronized (sVolRangeInitReqListenerLock) {
            sVolRangeInitReqListener = onVolRangeInitRequestListener;
        }
    }

    public static final synchronized AudioSystemAdapter getDefaultAdapter() {
        AudioSystemAdapter audioSystemAdapter;
        synchronized (AudioSystemAdapter.class) {
            if (sSingletonDefaultAdapter == null) {
                AudioSystemAdapter audioSystemAdapter2 = new AudioSystemAdapter();
                sSingletonDefaultAdapter = audioSystemAdapter2;
                AudioSystem.setRoutingCallback(audioSystemAdapter2);
                AudioSystem.setVolumeRangeInitRequestCallback(sSingletonDefaultAdapter);
                synchronized (sDeviceCacheLock) {
                    sSingletonDefaultAdapter.mDevicesForAttrCache = new ConcurrentHashMap(AudioSystem.getNumStreamTypes());
                    sSingletonDefaultAdapter.mMethodCacheHit = new int[1];
                }
            }
            audioSystemAdapter = sSingletonDefaultAdapter;
        }
        return audioSystemAdapter;
    }

    public void invalidateRoutingCache() {
        synchronized (sDeviceCacheLock) {
            if (this.mDevicesForAttrCache != null) {
                this.mDevicesForAttributesCacheClearTimeMs = System.currentTimeMillis();
                this.mLastDevicesForAttr.putAll(this.mDevicesForAttrCache);
                this.mDevicesForAttrCache.clear();
            }
        }
    }

    public ArrayList getDevicesForAttributes(AudioAttributes audioAttributes, boolean z) {
        return getDevicesForAttributesImpl(audioAttributes, z);
    }

    public final ArrayList getDevicesForAttributesImpl(AudioAttributes audioAttributes, boolean z) {
        Pair pair = new Pair(audioAttributes, Boolean.valueOf(z));
        synchronized (sDeviceCacheLock) {
            ArrayList arrayList = (ArrayList) this.mDevicesForAttrCache.get(pair);
            if (arrayList == null) {
                ArrayList devicesForAttributes = AudioSystem.getDevicesForAttributes(audioAttributes, z);
                this.mDevicesForAttrCache.put(pair, devicesForAttributes);
                return devicesForAttributes;
            }
            int[] iArr = this.mMethodCacheHit;
            iArr[0] = iArr[0] + 1;
            return arrayList;
        }
    }

    public int setDeviceConnectionState(AudioDeviceAttributes audioDeviceAttributes, int i, int i2) {
        invalidateRoutingCache();
        return AudioSystem.setDeviceConnectionState(audioDeviceAttributes, i, i2);
    }

    public int getDeviceConnectionState(int i, String str) {
        return AudioSystem.getDeviceConnectionState(i, str);
    }

    public int handleDeviceConfigChange(int i, String str, String str2, int i2) {
        invalidateRoutingCache();
        return AudioSystem.handleDeviceConfigChange(i, str, str2, i2);
    }

    public int setDevicesRoleForStrategy(int i, int i2, List list) {
        invalidateRoutingCache();
        return AudioSystem.setDevicesRoleForStrategy(i, i2, list);
    }

    public int removeDevicesRoleForStrategy(int i, int i2, List list) {
        invalidateRoutingCache();
        return AudioSystem.removeDevicesRoleForStrategy(i, i2, list);
    }

    public int clearDevicesRoleForStrategy(int i, int i2) {
        invalidateRoutingCache();
        return AudioSystem.clearDevicesRoleForStrategy(i, i2);
    }

    public int removeDevicesRoleForCapturePreset(int i, int i2, List list) {
        invalidateRoutingCache();
        return AudioSystem.removeDevicesRoleForCapturePreset(i, i2, list);
    }

    public int addDevicesRoleForCapturePreset(int i, int i2, List list) {
        invalidateRoutingCache();
        return AudioSystem.addDevicesRoleForCapturePreset(i, i2, list);
    }

    public int clearDevicesRoleForCapturePreset(int i, int i2) {
        invalidateRoutingCache();
        return AudioSystem.clearDevicesRoleForCapturePreset(i, i2);
    }

    public int setParameters(String str) {
        invalidateRoutingCache();
        return AudioSystem.setParameters(str);
    }

    public boolean isMicrophoneMuted() {
        return AudioSystem.isMicrophoneMuted();
    }

    public int muteMicrophone(boolean z) {
        return AudioSystem.muteMicrophone(z);
    }

    public int setCurrentImeUid(int i) {
        return AudioSystem.setCurrentImeUid(i);
    }

    public boolean isStreamActive(int i, int i2) {
        return AudioSystem.isStreamActive(i, i2);
    }

    public boolean isStreamActiveRemotely(int i, int i2) {
        return AudioSystem.isStreamActiveRemotely(i, i2);
    }

    public int setPhoneState(int i, int i2) {
        invalidateRoutingCache();
        return AudioSystem.setPhoneState(i, i2);
    }

    public int setAllowedCapturePolicy(int i, int i2) {
        return AudioSystem.setAllowedCapturePolicy(i, i2);
    }

    public int setForceUse(int i, int i2) {
        invalidateRoutingCache();
        return AudioSystem.setForceUse(i, i2);
    }

    public int registerPolicyMixes(ArrayList arrayList, boolean z) {
        invalidateRoutingCache();
        return AudioSystem.registerPolicyMixes(arrayList, z);
    }

    public int setUidDeviceAffinities(int i, int[] iArr, String[] strArr) {
        invalidateRoutingCache();
        return AudioSystem.setUidDeviceAffinities(i, iArr, strArr);
    }

    public int removeUidDeviceAffinities(int i) {
        invalidateRoutingCache();
        return AudioSystem.removeUidDeviceAffinities(i);
    }

    public int setUserIdDeviceAffinities(int i, int[] iArr, String[] strArr) {
        invalidateRoutingCache();
        return AudioSystem.setUserIdDeviceAffinities(i, iArr, strArr);
    }

    public int removeUserIdDeviceAffinities(int i) {
        invalidateRoutingCache();
        return AudioSystem.removeUserIdDeviceAffinities(i);
    }

    public int setPreferredMixerAttributes(AudioAttributes audioAttributes, int i, int i2, AudioMixerAttributes audioMixerAttributes) {
        return AudioSystem.setPreferredMixerAttributes(audioAttributes, i, i2, audioMixerAttributes);
    }

    public int clearPreferredMixerAttributes(AudioAttributes audioAttributes, int i, int i2) {
        return AudioSystem.clearPreferredMixerAttributes(audioAttributes, i, i2);
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("\nAudioSystemAdapter:");
        DateTimeFormatter withZone = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss:SSS").withLocale(Locale.US).withZone(ZoneId.systemDefault());
        synchronized (sDeviceCacheLock) {
            printWriter.println(" last cache clear time: " + withZone.format(Instant.ofEpochMilli(this.mDevicesForAttributesCacheClearTimeMs)));
            printWriter.println(" mDevicesForAttrCache:");
            ConcurrentHashMap concurrentHashMap = this.mDevicesForAttrCache;
            if (concurrentHashMap != null) {
                for (Map.Entry entry : concurrentHashMap.entrySet()) {
                    AudioAttributes audioAttributes = (AudioAttributes) ((Pair) entry.getKey()).first;
                    try {
                        int volumeControlStream = audioAttributes.getVolumeControlStream();
                        printWriter.println("\t" + audioAttributes + " forVolume: " + ((Pair) entry.getKey()).second + " stream: " + AudioSystem.STREAM_NAMES[volumeControlStream] + "(" + volumeControlStream + ")");
                        Iterator it = ((ArrayList) entry.getValue()).iterator();
                        while (it.hasNext()) {
                            printWriter.println("\t\t" + ((AudioDeviceAttributes) it.next()));
                        }
                    } catch (IllegalArgumentException e) {
                        printWriter.println("\t dump failed for attributes: " + audioAttributes);
                        Log.e("AudioSystemAdapter", "dump failed", e);
                    }
                }
            }
        }
    }

    public int setCallParameters(String str) {
        return AudioSystem.setParameters(str);
    }
}
