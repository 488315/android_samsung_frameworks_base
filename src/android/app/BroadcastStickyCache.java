package android.app;

import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbManager;
import android.media.AudioManager;
import android.os.IpcDataCache;
import android.os.ParcelFileDescriptor;
import android.os.UpdateLock;
import android.telephony.TelephonyManager;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FastPrintWriter;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Objects;

/* loaded from: classes.dex */
public class BroadcastStickyCache {
    public static final String[] STICKY_BROADCAST_ACTIONS = {AudioManager.ACTION_HDMI_AUDIO_PLUG, "android.intent.action.HEADSET_PLUG", AudioManager.ACTION_SCO_AUDIO_STATE_CHANGED, AudioManager.ACTION_SCO_AUDIO_STATE_UPDATED, AudioManager.INTERNAL_RINGER_MODE_CHANGED_ACTION, AudioManager.RINGER_MODE_CHANGED_ACTION, "android.net.conn.CONNECTIVITY_CHANGE", Intent.ACTION_BATTERY_CHANGED, Intent.ACTION_DEVICE_STORAGE_FULL, Intent.ACTION_DEVICE_STORAGE_LOW, "android.intent.action.SIM_STATE_CHANGED", "android.net.nsd.STATE_CHANGED", TelephonyManager.ACTION_SERVICE_PROVIDERS_UPDATED, "android.net.conn.TETHER_STATE_CHANGED", UpdateLock.UPDATE_LOCK_CHANGED, UsbManager.ACTION_USB_STATE, "android.net.wifi.action.WIFI_SCAN_AVAILABILITY_CHANGED", "android.net.wifi.STATE_CHANGE", "android.net.wifi.supplicant.STATE_CHANGE", "android.net.wifi.WIFI_STATE_CHANGED", "android.net.wifi.p2p.STATE_CHANGED", "android.intent.action.HDMI_PLUGGED", "android.net.conn.INET_CONDITION_ACTION"};
    public static final ArrayMap<String, String> sActionApiNameMap;
    private static final ArrayMap<String, IpcDataCache.Config> sActionConfigMap;
    private static final ArrayMap<StickyBroadcastFilter, IpcDataCache<Void, Intent>> sFilterCacheMap;

    static {
        ArrayMap<String, String> arrayMap = new ArrayMap<>();
        sActionApiNameMap = arrayMap;
        sActionConfigMap = new ArrayMap<>();
        sFilterCacheMap = new ArrayMap<>();
        arrayMap.put(AudioManager.ACTION_HDMI_AUDIO_PLUG, "hdmi_audio_plug");
        arrayMap.put("android.intent.action.HEADSET_PLUG", "headset_plug");
        arrayMap.put(AudioManager.ACTION_SCO_AUDIO_STATE_CHANGED, "sco_audio_state_changed");
        arrayMap.put(AudioManager.ACTION_SCO_AUDIO_STATE_UPDATED, "action_sco_audio_state_updated");
        arrayMap.put(AudioManager.INTERNAL_RINGER_MODE_CHANGED_ACTION, "internal_ringer_mode_changed_action");
        arrayMap.put(AudioManager.RINGER_MODE_CHANGED_ACTION, "ringer_mode_changed");
        arrayMap.put("android.net.conn.CONNECTIVITY_CHANGE", "connectivity_change");
        arrayMap.put(Intent.ACTION_BATTERY_CHANGED, "battery_changed");
        arrayMap.put(Intent.ACTION_DEVICE_STORAGE_FULL, "device_storage_full");
        arrayMap.put(Intent.ACTION_DEVICE_STORAGE_LOW, "device_storage_low");
        arrayMap.put("android.intent.action.SIM_STATE_CHANGED", "sim_state_changed");
        arrayMap.put("android.net.nsd.STATE_CHANGED", "nsd_state_changed");
        arrayMap.put(TelephonyManager.ACTION_SERVICE_PROVIDERS_UPDATED, "service_providers_updated");
        arrayMap.put("android.net.conn.TETHER_STATE_CHANGED", "tether_state_changed");
        arrayMap.put(UpdateLock.UPDATE_LOCK_CHANGED, "update_lock_changed");
        arrayMap.put(UsbManager.ACTION_USB_STATE, "usb_state");
        arrayMap.put("android.net.wifi.action.WIFI_SCAN_AVAILABILITY_CHANGED", "wifi_scan_availability_changed");
        arrayMap.put("android.net.wifi.STATE_CHANGE", "network_state_change");
        arrayMap.put("android.net.wifi.supplicant.STATE_CHANGE", "supplicant_state_change");
        arrayMap.put("android.net.wifi.WIFI_STATE_CHANGED", "wifi_state_changed");
        arrayMap.put("android.net.wifi.p2p.STATE_CHANGED", "wifi_p2p_state_changed");
        arrayMap.put("android.intent.action.HDMI_PLUGGED", "hdmi_plugged");
        arrayMap.put("android.net.conn.INET_CONDITION_ACTION", "inet_condition_action");
    }

    public static boolean useCache(IntentFilter intentFilter) {
        return Flags.useStickyBcastCache() && intentFilter != null && intentFilter.safeCountActions() == 1 && ArrayUtils.contains(STICKY_BROADCAST_ACTIONS, intentFilter.getAction(0));
    }

    public static void invalidateCache(String str) {
        if (Flags.useStickyBcastCache() && ArrayUtils.contains(STICKY_BROADCAST_ACTIONS, str)) {
            IpcDataCache.invalidateCache("system_server", sActionApiNameMap.get(str));
        }
    }

    public static void invalidateAllCaches() {
        for (int size = sActionApiNameMap.size() - 1; size >= 0; size--) {
            IpcDataCache.invalidateCache("system_server", sActionApiNameMap.valueAt(size));
        }
    }

    public static Intent getIntent(final IApplicationThread iApplicationThread, final String str, final String str2, final IntentFilter intentFilter, final String str3, final int i, final int i2) {
        IpcDataCache<Void, Intent> ipcDataCacheFindIpcDataCache;
        synchronized (BroadcastStickyCache.class) {
            ipcDataCacheFindIpcDataCache = findIpcDataCache(intentFilter);
            if (ipcDataCacheFindIpcDataCache == null) {
                String action = intentFilter.getAction(0);
                StickyBroadcastFilter stickyBroadcastFilter = new StickyBroadcastFilter(intentFilter, action);
                IpcDataCache<Void, Intent> ipcDataCache = new IpcDataCache<>(getConfig(action), (IpcDataCache.RemoteCall<Void, Intent>) new IpcDataCache.RemoteCall() { // from class: android.app.BroadcastStickyCache$$ExternalSyntheticLambda0
                    @Override // android.os.IpcDataCache.RemoteCall
                    public final Object apply(Object obj) {
                        return ActivityManager.getService().registerReceiverWithFeature(iApplicationThread, str, str2, PerfettoProtoLogImpl.NULL_STRING, null, intentFilter, str3, i, i2);
                    }
                });
                sFilterCacheMap.put(stickyBroadcastFilter, ipcDataCache);
                ipcDataCacheFindIpcDataCache = ipcDataCache;
            }
        }
        return ipcDataCacheFindIpcDataCache.query(null);
    }

    public static void clearCacheForTest() {
        synchronized (BroadcastStickyCache.class) {
            sFilterCacheMap.clear();
        }
    }

    private static IpcDataCache<Void, Intent> findIpcDataCache(IntentFilter intentFilter) {
        for (int size = sFilterCacheMap.size() - 1; size >= 0; size--) {
            ArrayMap<StickyBroadcastFilter, IpcDataCache<Void, Intent>> arrayMap = sFilterCacheMap;
            StickyBroadcastFilter stickyBroadcastFilterKeyAt = arrayMap.keyAt(size);
            if (intentFilter.getAction(0).equals(stickyBroadcastFilterKeyAt.action()) && IntentFilter.filterEquals(stickyBroadcastFilterKeyAt.filter(), intentFilter)) {
                return arrayMap.valueAt(size);
            }
        }
        return null;
    }

    private static IpcDataCache.Config getConfig(String str) {
        ArrayMap<String, IpcDataCache.Config> arrayMap = sActionConfigMap;
        if (!arrayMap.containsKey(str)) {
            arrayMap.put(str, new IpcDataCache.Config(32, "system_server", sActionApiNameMap.get(str)).cacheNulls(true));
        }
        return arrayMap.get(str);
    }

    public static void dumpCacheInfo(ParcelFileDescriptor parcelFileDescriptor) {
        if (Flags.useStickyBcastCache()) {
            FastPrintWriter fastPrintWriter = new FastPrintWriter(new FileOutputStream(parcelFileDescriptor.getFileDescriptor()));
            synchronized (BroadcastStickyCache.class) {
                dumpCacheLocked(fastPrintWriter);
            }
            fastPrintWriter.flush();
        }
    }

    private static void dumpCacheLocked(PrintWriter printWriter) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ", "  ");
        indentingPrintWriter.println("Cached sticky broadcasts:");
        indentingPrintWriter.increaseIndent();
        int size = sFilterCacheMap.size();
        if (size == 0) {
            indentingPrintWriter.println("<empty>");
        } else {
            for (int i = 0; i < size; i++) {
                ArrayMap<StickyBroadcastFilter, IpcDataCache<Void, Intent>> arrayMap = sFilterCacheMap;
                StickyBroadcastFilter stickyBroadcastFilterKeyAt = arrayMap.keyAt(i);
                IpcDataCache<Void, Intent> ipcDataCacheValueAt = arrayMap.valueAt(i);
                indentingPrintWriter.print("Entry #");
                indentingPrintWriter.print(i);
                indentingPrintWriter.println(":");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.print("action", stickyBroadcastFilterKeyAt.action).println();
                indentingPrintWriter.print("filter", stickyBroadcastFilterKeyAt.filter.toLongString()).println();
                ipcDataCacheValueAt.dumpCacheEntries(printWriter);
                indentingPrintWriter.decreaseIndent();
            }
        }
        indentingPrintWriter.decreaseIndent();
    }

    private static final class StickyBroadcastFilter extends Record {
        private final String action;
        private final IntentFilter filter;

        private /* synthetic */ boolean $record$equals(Object obj) {
            if (!(obj instanceof StickyBroadcastFilter)) {
                return false;
            }
            StickyBroadcastFilter stickyBroadcastFilter = (StickyBroadcastFilter) obj;
            return Objects.equals(this.filter, stickyBroadcastFilter.filter) && Objects.equals(this.action, stickyBroadcastFilter.action);
        }

        private /* synthetic */ Object[] $record$getFieldsAsObjects() {
            return new Object[]{this.filter, this.action};
        }

        public String action() {
            return this.action;
        }

        @Override // java.lang.Record
        public final boolean equals(Object obj) {
            return $record$equals(obj);
        }

        public IntentFilter filter() {
            return this.filter;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m(this.filter, this.action);
        }

        @Override // java.lang.Record
        public final String toString() {
            return PropertyInvalidatedCache$Args$$ExternalSyntheticRecord0.m($record$getFieldsAsObjects(), StickyBroadcastFilter.class, "filter;action");
        }

        private StickyBroadcastFilter(IntentFilter filter, String action) {
            this.filter = filter;
            this.action = action;
        }
    }
}
