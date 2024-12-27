package android.internal.wifi;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes2.dex */
public final class WifiAnnotations {

    @Retention(RetentionPolicy.SOURCE)
    public @interface Bandwidth {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface ChannelWidth {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface Cipher {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface KeyMgmt {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface PreambleType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface Protocol {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScanType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface SecurityType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface UriScheme {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface WifiBandBasic {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface WifiStandard {}

    private WifiAnnotations() {}
}
