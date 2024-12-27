package android.net.shared;

import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.net.PrivateDnsConfigParcel;
import android.text.TextUtils;
import java.net.InetAddress;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class PrivateDnsConfig {
    public final InetAddress[] dohIps;
    public final String dohName;
    public final String dohPath;
    public final int dohPort;
    public final String hostname;
    public final InetAddress[] ips;
    public final int mode;

    public PrivateDnsConfig() {
        this(false);
    }

    public PrivateDnsConfig(int i, String str, InetAddress[] inetAddressArr, String str2, InetAddress[] inetAddressArr2, String str3, int i2) {
        this.mode = i;
        this.hostname = str == null ? "" : str;
        this.ips = inetAddressArr != null ? (InetAddress[]) inetAddressArr.clone() : new InetAddress[0];
        this.dohName = str2 == null ? "" : str2;
        this.dohIps = inetAddressArr2 != null ? (InetAddress[]) inetAddressArr2.clone() : new InetAddress[0];
        this.dohPath = str3 == null ? "" : str3;
        this.dohPort = i2;
    }

    public PrivateDnsConfig(PrivateDnsConfig privateDnsConfig) {
        this.mode = privateDnsConfig.mode;
        this.hostname = privateDnsConfig.hostname;
        this.ips = privateDnsConfig.ips;
        this.dohName = privateDnsConfig.dohName;
        this.dohIps = privateDnsConfig.dohIps;
        this.dohPath = privateDnsConfig.dohPath;
        this.dohPort = privateDnsConfig.dohPort;
    }

    public PrivateDnsConfig(String str, InetAddress[] inetAddressArr) {
        this(TextUtils.isEmpty(str) ? 1 : 3, str, inetAddressArr, null, null, null, -1);
    }

    public PrivateDnsConfig(boolean z) {
        this(z ? 2 : 1, null, null, null, null, null, -1);
    }

    public static PrivateDnsConfig fromParcel(PrivateDnsConfigParcel privateDnsConfigParcel) {
        String[] strArr = privateDnsConfigParcel.ips;
        InetAddress[] inetAddressArr = (InetAddress[]) ParcelableUtil.fromParcelableArray(strArr, new InitialConfiguration$$ExternalSyntheticLambda9(0)).toArray(new InetAddress[strArr.length]);
        if (privateDnsConfigParcel.privateDnsMode == -1) {
            return new PrivateDnsConfig(privateDnsConfigParcel.hostname, inetAddressArr);
        }
        String[] strArr2 = privateDnsConfigParcel.dohIps;
        return new PrivateDnsConfig(privateDnsConfigParcel.privateDnsMode, privateDnsConfigParcel.hostname, inetAddressArr, privateDnsConfigParcel.dohName, (InetAddress[]) ParcelableUtil.fromParcelableArray(strArr2, new InitialConfiguration$$ExternalSyntheticLambda9(0)).toArray(new InetAddress[strArr2.length]), privateDnsConfigParcel.dohPath, privateDnsConfigParcel.dohPort);
    }

    private static String modeAsString(int i) {
        return i != 1 ? i != 2 ? i != 3 ? "unknown" : "strict" : "opportunistic" : "off";
    }

    public boolean inOpportunisticMode() {
        return this.mode == 2;
    }

    public boolean inStrictMode() {
        return this.mode == 3;
    }

    public PrivateDnsConfigParcel toParcel() {
        PrivateDnsConfigParcel privateDnsConfigParcel = new PrivateDnsConfigParcel();
        privateDnsConfigParcel.hostname = this.hostname;
        privateDnsConfigParcel.ips = (String[]) ParcelableUtil.toParcelableArray(Arrays.asList(this.ips), new InitialConfiguration$$ExternalSyntheticLambda9(1), String.class);
        privateDnsConfigParcel.privateDnsMode = this.mode;
        privateDnsConfigParcel.dohName = this.dohName;
        privateDnsConfigParcel.dohIps = (String[]) ParcelableUtil.toParcelableArray(Arrays.asList(this.dohIps), new InitialConfiguration$$ExternalSyntheticLambda9(1), String.class);
        privateDnsConfigParcel.dohPath = this.dohPath;
        privateDnsConfigParcel.dohPort = this.dohPort;
        return privateDnsConfigParcel;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("PrivateDnsConfig{");
        sb.append(modeAsString(this.mode));
        sb.append(":");
        sb.append(this.hostname);
        sb.append("/");
        sb.append(Arrays.toString(this.ips));
        sb.append(", dohName=");
        sb.append(this.dohName);
        sb.append(", dohIps=");
        sb.append(Arrays.toString(this.dohIps));
        sb.append(", dohPath=");
        sb.append(this.dohPath);
        sb.append(", dohPort=");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.dohPort, sb, "}");
    }
}
