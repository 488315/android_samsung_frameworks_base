package com.samsung.android.server.audio;

import android.media.AudioSystem;
import android.util.SparseArray;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.io.PrintWriter;

/* loaded from: classes2.dex */
public class DeviceAliasManager {
    public final SparseArray mDevices;
    public final SparseArray mLeOnlyDevices;

    public interface DeviceAliasRunner {
        void run(int i);
    }

    public DeviceAliasManager() {
        SparseArray sparseArray = new SparseArray();
        this.mDevices = sparseArray;
        this.mLeOnlyDevices = new SparseArray();
        sparseArray.put(2, new DeviceAlias(new int[]{1}, new int[]{0, 8}));
        sparseArray.put(1, new DeviceAlias(new int[]{2}, new int[]{0, 8}));
        int numStreamTypes = AudioSystem.getNumStreamTypes();
        int[] iArr = new int[numStreamTypes];
        for (int i = 0; i < numStreamTypes; i++) {
            if (i != 3) {
                iArr[i] = i;
            }
        }
        this.mDevices.put(128, new DeviceAlias(new int[]{536870912, 536870914}, iArr));
        this.mDevices.put(536870912, new DeviceAlias(new int[]{128, 536870914}, iArr));
        this.mDevices.put(536870914, new DeviceAlias(new int[]{536870912, 128}, iArr));
        this.mLeOnlyDevices.put(536870912, new DeviceAlias(new int[]{536870914}, iArr));
        this.mLeOnlyDevices.put(536870914, new DeviceAlias(new int[]{536870912}, iArr));
    }

    public void apply(int i, int i2, DeviceAliasRunner deviceAliasRunner, boolean z) {
        DeviceAlias deviceAlias = (DeviceAlias) (z ? this.mLeOnlyDevices : this.mDevices).get(i);
        if (deviceAlias == null) {
            return;
        }
        for (int i3 : deviceAlias.mAliases) {
            if (!deviceAlias.isExcludedStream(i2)) {
                deviceAliasRunner.run(i3);
            }
        }
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("\nDevice Aliases:");
        for (int i = 0; i < this.mDevices.size(); i++) {
            int keyAt = this.mDevices.keyAt(i);
            printWriter.println("- " + AudioSystem.getDeviceName(keyAt) + XmlUtils.STRING_ARRAY_SEPARATOR);
            DeviceAlias deviceAlias = (DeviceAlias) this.mDevices.get(keyAt);
            if (deviceAlias != null) {
                printWriter.println(deviceAlias.dump());
            }
        }
        printWriter.println();
    }

    public class DeviceAlias {
        public int[] mAliases;
        public int mExcludeStreams;

        public DeviceAlias(int[] iArr, int[] iArr2) {
            this.mAliases = iArr;
            this.mExcludeStreams = convertBitStream(iArr2);
        }

        public final int convertBitStream(int... iArr) {
            int i = 0;
            for (int i2 : iArr) {
                i |= 1 << i2;
            }
            return i;
        }

        public boolean isExcludedStream(int i) {
            return (this.mExcludeStreams & (1 << i)) > 0;
        }

        public String dump() {
            StringBuilder sb = new StringBuilder("  Alias: ");
            for (int i : this.mAliases) {
                sb.append(AudioSystem.getDeviceName(i));
                sb.append(" ");
            }
            sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            sb.append("  Exclude Streams: ");
            for (int i2 = 0; i2 < AudioSystem.getNumStreamTypes(); i2++) {
                if (isExcludedStream(i2)) {
                    sb.append(i2);
                    sb.append(" ");
                }
            }
            return sb.toString();
        }
    }
}
