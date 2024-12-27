package com.android.server;

import android.net.IpConfiguration;
import android.util.ArrayMap;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public final class ExtendedEthernetConfigStore {
    public final Object mSync = new Object();
    public final ArrayMap mIpConfigurations = new ArrayMap(0);

    /* renamed from: com.android.server.ExtendedEthernetConfigStore$1, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[]
                $SwitchMap$android$net$IpConfiguration$IpAssignment;
        public static final /* synthetic */ int[]
                $SwitchMap$android$net$IpConfiguration$ProxySettings;

        static {
            int[] iArr = new int[IpConfiguration.ProxySettings.values().length];
            $SwitchMap$android$net$IpConfiguration$ProxySettings = iArr;
            try {
                iArr[IpConfiguration.ProxySettings.STATIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$net$IpConfiguration$ProxySettings[
                                IpConfiguration.ProxySettings.PAC.ordinal()] =
                        2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$net$IpConfiguration$ProxySettings[
                                IpConfiguration.ProxySettings.NONE.ordinal()] =
                        3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$android$net$IpConfiguration$ProxySettings[
                                IpConfiguration.ProxySettings.UNASSIGNED.ordinal()] =
                        4;
            } catch (NoSuchFieldError unused4) {
            }
            int[] iArr2 = new int[IpConfiguration.IpAssignment.values().length];
            $SwitchMap$android$net$IpConfiguration$IpAssignment = iArr2;
            try {
                iArr2[IpConfiguration.IpAssignment.STATIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$android$net$IpConfiguration$IpAssignment[
                                IpConfiguration.IpAssignment.DHCP.ordinal()] =
                        2;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$android$net$IpConfiguration$IpAssignment[
                                IpConfiguration.IpAssignment.UNASSIGNED.ordinal()] =
                        3;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public static List parseProxyExclusionListString(String str) {
        return str == null
                ? Collections.emptyList()
                : Arrays.asList(str.toLowerCase(Locale.ROOT).split(","));
    }

    /* JADX WARN: Code restructure failed: missing block: B:145:0x02c3, code lost:

       if (r4 == null) goto L130;
    */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x02b8, code lost:

       r4.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x02b6, code lost:

       if (r4 == null) goto L130;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void loadConfigFileLocked() {
        /*
            Method dump skipped, instructions count: 744
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.ExtendedEthernetConfigStore.loadConfigFileLocked():void");
    }

    public final void read() {
        synchronized (this.mSync) {
            try {
                if (new File("/data/misc/apexdata/com.android.tethering/misc/ethernet/ipconfig.txt")
                        .exists()) {
                    loadConfigFileLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
