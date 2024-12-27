package com.android.server.hdmi;

import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;

import java.io.UnsupportedEncodingException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class HdmiCecMessageBuilder {
    public static HdmiCecMessage buildActiveSource(int i, int i2) {
        return HdmiCecMessage.build(i, 15, 130, physicalAddressToParam(i2));
    }

    public static HdmiCecMessage buildCommandWithBooleanParam(int i, int i2, int i3, boolean z) {
        return HdmiCecMessage.build(i, i2, i3, new byte[] {z ? (byte) 1 : (byte) 0});
    }

    public static HdmiCecMessage buildDeviceVendorIdCommand(int i, int i2) {
        return HdmiCecMessage.build(
                i,
                15,
                135,
                new byte[] {
                    (byte)
                            ((i2 >> 16)
                                    & IDnsResolverUnsolicitedEventListener
                                            .DNS_HEALTH_RESULT_TIMEOUT),
                    (byte)
                            ((i2 >> 8)
                                    & IDnsResolverUnsolicitedEventListener
                                            .DNS_HEALTH_RESULT_TIMEOUT),
                    (byte) (i2 & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT)
                });
    }

    public static HdmiCecMessage buildReportPhysicalAddressCommand(int i, int i2, int i3) {
        return HdmiCecMessage.build(
                i,
                15,
                132,
                new byte[] {
                    (byte)
                            ((i2 >> 8)
                                    & IDnsResolverUnsolicitedEventListener
                                            .DNS_HEALTH_RESULT_TIMEOUT),
                    (byte) (i2 & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT),
                    (byte) (i3 & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT)
                });
    }

    public static HdmiCecMessage buildRoutingChange(int i, int i2, int i3) {
        return HdmiCecMessage.build(
                i,
                15,
                128,
                new byte[] {
                    (byte)
                            ((i2 >> 8)
                                    & IDnsResolverUnsolicitedEventListener
                                            .DNS_HEALTH_RESULT_TIMEOUT),
                    (byte) (i2 & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT),
                    (byte)
                            ((i3 >> 8)
                                    & IDnsResolverUnsolicitedEventListener
                                            .DNS_HEALTH_RESULT_TIMEOUT),
                    (byte) (i3 & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT)
                });
    }

    public static HdmiCecMessage buildSetOsdNameCommand(int i, int i2, String str) {
        try {
            return HdmiCecMessage.build(
                    i, i2, 71, str.substring(0, Math.min(str.length(), 14)).getBytes("US-ASCII"));
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    public static byte[] physicalAddressToParam(int i) {
        return new byte[] {
            (byte) ((i >> 8) & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT),
            (byte) (i & IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT)
        };
    }
}
