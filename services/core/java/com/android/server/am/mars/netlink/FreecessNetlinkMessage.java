package com.android.server.am.mars.netlink;

import android.os.Process;

import com.android.internal.util.FrameworkStatsLog;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class FreecessNetlinkMessage {
    public static byte[] newFreecessRequest(int i, int i2, int i3, int i4, int i5) {
        byte[] bArr =
                new byte[FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__RESOLVER_SWITCH_TABS];
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.order(ByteOrder.nativeOrder());
        wrap.putInt(FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__RESOLVER_SWITCH_TABS);
        wrap.putShort((short) 30);
        wrap.putShort((short) 0);
        wrap.putInt(0);
        wrap.putInt(0);
        byte[] bArr2 = new byte[100];
        int myPid = Process.myPid();
        wrap.putInt(i);
        wrap.putInt(i2);
        wrap.putInt(myPid);
        wrap.putInt(305402420);
        wrap.putInt(536870912);
        wrap.putInt(i3);
        wrap.putInt(0);
        wrap.putInt(0);
        for (int i6 = 0; i6 < 100; i6++) {
            wrap.put(bArr2[i6]);
        }
        wrap.putInt(i4);
        wrap.putInt(i5);
        return bArr;
    }
}
