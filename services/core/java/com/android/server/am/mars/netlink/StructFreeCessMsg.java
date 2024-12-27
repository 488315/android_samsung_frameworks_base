package com.android.server.am.mars.netlink;

import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import java.nio.ByteBuffer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class StructFreeCessMsg {
    public int cmd;
    public final byte[] rpcname = new byte[100];
    public int type = 0;
    public int mod = 0;
    public int src_portid = 0;
    public int dst_portid = 0;
    public int version = 0;
    public int target_uid = 0;
    public int flag = 0;
    public int code = 0;
    public int uid = 0;

    public static StructFreeCessMsg parse(ByteBuffer byteBuffer) {
        byteBuffer.getInt();
        byteBuffer.getShort();
        byteBuffer.getShort();
        byteBuffer.getInt();
        byteBuffer.getInt();
        StructFreeCessMsg structFreeCessMsg = new StructFreeCessMsg();
        structFreeCessMsg.type = byteBuffer.getInt();
        structFreeCessMsg.mod = byteBuffer.getInt();
        structFreeCessMsg.src_portid = byteBuffer.getInt();
        structFreeCessMsg.dst_portid = byteBuffer.getInt();
        structFreeCessMsg.version = byteBuffer.getInt();
        structFreeCessMsg.target_uid = byteBuffer.getInt();
        structFreeCessMsg.flag = byteBuffer.getInt();
        structFreeCessMsg.code = byteBuffer.getInt();
        for (int i = 0; i < 100; i++) {
            structFreeCessMsg.rpcname[i] = byteBuffer.get();
        }
        structFreeCessMsg.cmd = byteBuffer.getInt();
        structFreeCessMsg.uid = byteBuffer.getInt();
        return structFreeCessMsg;
    }

    public final String toString() {
        StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m("struct.type = " + this.type, "struct.mode = ");
        m.append(this.mod);
        StringBuilder m2 = Preconditions$$ExternalSyntheticOutline0.m(m.toString(), "struct.src_portid = ");
        m2.append(this.src_portid);
        StringBuilder m3 = Preconditions$$ExternalSyntheticOutline0.m(m2.toString(), "struct.dst_portid = ");
        m3.append(this.dst_portid);
        return m3.toString();
    }
}
