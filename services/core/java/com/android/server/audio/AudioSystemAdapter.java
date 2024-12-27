package com.android.server.audio;

import android.media.AudioAttributes;
import android.media.AudioDeviceAttributes;
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

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AudioSystemAdapter
        implements AudioSystem.RoutingUpdateCallback, AudioSystem.VolumeRangeInitRequestCallback {
    public static OnRoutingUpdatedListener sRoutingListener;
    public static AudioSystemAdapter sSingletonDefaultAdapter;
    public static OnVolRangeInitRequestListener sVolRangeInitReqListener;
    public ConcurrentHashMap mDevicesForAttrCache;
    public int[] mMethodCacheHit;
    public static final Object sDeviceCacheLock = new Object();
    public static final Object sRoutingListenerLock = new Object();
    public static final Object sVolRangeInitReqListenerLock = new Object();
    public final ConcurrentHashMap mLastDevicesForAttr = new ConcurrentHashMap();
    public long mDevicesForAttributesCacheClearTimeMs = System.currentTimeMillis();
    public final ArrayMap mRegisteredAttributesMap = new ArrayMap();
    public final RemoteCallbackList mDevicesForAttributesCallbacks = new RemoteCallbackList();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface OnRoutingUpdatedListener {
        void onRoutingUpdatedFromNative();
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface OnVolRangeInitRequestListener {
        void onVolumeRangeInitRequestFromNative();
    }

    public static final synchronized AudioSystemAdapter getDefaultAdapter() {
        AudioSystemAdapter audioSystemAdapter;
        synchronized (AudioSystemAdapter.class) {
            try {
                if (sSingletonDefaultAdapter == null) {
                    AudioSystemAdapter audioSystemAdapter2 = new AudioSystemAdapter();
                    sSingletonDefaultAdapter = audioSystemAdapter2;
                    AudioSystem.setRoutingCallback(audioSystemAdapter2);
                    AudioSystem.setVolumeRangeInitRequestCallback(sSingletonDefaultAdapter);
                    synchronized (sDeviceCacheLock) {
                        sSingletonDefaultAdapter.mDevicesForAttrCache =
                                new ConcurrentHashMap(AudioSystem.getNumStreamTypes());
                        sSingletonDefaultAdapter.mMethodCacheHit = new int[1];
                    }
                }
                audioSystemAdapter = sSingletonDefaultAdapter;
            } catch (Throwable th) {
                throw th;
            }
        }
        return audioSystemAdapter;
    }

    public final void dump(PrintWriter printWriter) {
        printWriter.println("\nAudioSystemAdapter:");
        DateTimeFormatter withZone =
                DateTimeFormatter.ofPattern("MM-dd HH:mm:ss:SSS")
                        .withLocale(Locale.US)
                        .withZone(ZoneId.systemDefault());
        synchronized (sDeviceCacheLock) {
            printWriter.println(
                    " last cache clear time: "
                            + withZone.format(
                                    Instant.ofEpochMilli(
                                            this.mDevicesForAttributesCacheClearTimeMs)));
            printWriter.println(" mDevicesForAttrCache:");
            ConcurrentHashMap concurrentHashMap = this.mDevicesForAttrCache;
            if (concurrentHashMap != null) {
                for (Map.Entry entry : concurrentHashMap.entrySet()) {
                    AudioAttributes audioAttributes =
                            (AudioAttributes) ((Pair) entry.getKey()).first;
                    try {
                        int volumeControlStream = audioAttributes.getVolumeControlStream();
                        printWriter.println(
                                "\t"
                                        + audioAttributes
                                        + " forVolume: "
                                        + ((Pair) entry.getKey()).second
                                        + " stream: "
                                        + AudioSystem.STREAM_NAMES[volumeControlStream]
                                        + "("
                                        + volumeControlStream
                                        + ")");
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

    public final ArrayList getDevicesForAttributes(AudioAttributes audioAttributes, boolean z) {
        ArrayList arrayList;
        Pair pair = new Pair(audioAttributes, Boolean.valueOf(z));
        synchronized (sDeviceCacheLock) {
            try {
                arrayList = (ArrayList) this.mDevicesForAttrCache.get(pair);
                if (arrayList == null) {
                    arrayList = AudioSystem.getDevicesForAttributes(audioAttributes, z);
                    this.mDevicesForAttrCache.put(pair, arrayList);
                } else {
                    int[] iArr = this.mMethodCacheHit;
                    iArr[0] = iArr[0] + 1;
                }
            } finally {
            }
        }
        return arrayList;
    }

    public final void invalidateRoutingCache() {
        synchronized (sDeviceCacheLock) {
            try {
                if (this.mDevicesForAttrCache != null) {
                    this.mDevicesForAttributesCacheClearTimeMs = System.currentTimeMillis();
                    this.mLastDevicesForAttr.putAll(this.mDevicesForAttrCache);
                    this.mDevicesForAttrCache.clear();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onRoutingUpdated() {
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
                IDevicesForAttributesCallback broadcastItem =
                        this.mDevicesForAttributesCallbacks.getBroadcastItem(i);
                List<Pair> list =
                        (List) this.mRegisteredAttributesMap.get(broadcastItem.asBinder());
                if (list == null) {
                    throw new IllegalStateException("Attribute list must not be null");
                }
                for (Pair pair : list) {
                    ArrayList devicesForAttributes =
                            getDevicesForAttributes(
                                    (AudioAttributes) pair.first,
                                    ((Boolean) pair.second).booleanValue());
                    if (this.mLastDevicesForAttr.containsKey(pair)) {
                        List list2 = (List) this.mLastDevicesForAttr.get(pair);
                        Iterator it = devicesForAttributes.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                if (!list2.contains((AudioDeviceAttributes) it.next())) {
                                    break;
                                }
                            } else {
                                Iterator it2 = list2.iterator();
                                while (true) {
                                    if (!it2.hasNext()) {
                                        break;
                                    } else if (devicesForAttributes.contains(
                                            (AudioDeviceAttributes) it2.next())) {
                                    }
                                }
                            }
                        }
                    }
                    try {
                        broadcastItem.onDevicesForAttributesChanged(
                                (AudioAttributes) pair.first,
                                ((Boolean) pair.second).booleanValue(),
                                devicesForAttributes);
                        break;
                    } catch (RemoteException unused) {
                    }
                }
            }
            this.mDevicesForAttributesCallbacks.finishBroadcast();
        }
    }

    public final void onVolumeRangeInitializationRequested() {
        OnVolRangeInitRequestListener onVolRangeInitRequestListener;
        synchronized (sVolRangeInitReqListenerLock) {
            onVolRangeInitRequestListener = sVolRangeInitReqListener;
        }
        if (onVolRangeInitRequestListener != null) {
            onVolRangeInitRequestListener.onVolumeRangeInitRequestFromNative();
        }
    }

    public final int setDeviceConnectionState(
            AudioDeviceAttributes audioDeviceAttributes, int i, int i2) {
        invalidateRoutingCache();
        return AudioSystem.setDeviceConnectionState(audioDeviceAttributes, i, i2);
    }

    public final void setForceUse(int i, int i2) {
        invalidateRoutingCache();
        AudioSystem.setForceUse(i, i2);
    }

    public final void setParameters(String str) {
        invalidateRoutingCache();
        AudioSystem.setParameters(str);
    }
}
