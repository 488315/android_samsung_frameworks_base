package com.android.server.integrity.parser;

import android.content.integrity.IntegrityUtils;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;

import com.android.server.integrity.model.BitInputStream;

import java.nio.ByteBuffer;

public abstract class BinaryFileOperations {
    public static String getStringValue(BitInputStream bitInputStream, int i, boolean z) {
        if (z) {
            ByteBuffer allocate = ByteBuffer.allocate(i);
            while (true) {
                int i2 = i - 1;
                if (i <= 0) {
                    return IntegrityUtils.getHexDigest(allocate.array());
                }
                allocate.put(
                        (byte)
                                (bitInputStream.getNext(8)
                                        & IDnsResolverUnsolicitedEventListener
                                                .DNS_HEALTH_RESULT_TIMEOUT));
                i = i2;
            }
        } else {
            StringBuilder sb = new StringBuilder();
            while (true) {
                int i3 = i - 1;
                if (i <= 0) {
                    return sb.toString();
                }
                sb.append((char) bitInputStream.getNext(8));
                i = i3;
            }
        }
    }
}
