package com.android.server.am.mars.netlink;

import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public class StructNlMsgHdr {
    public int nlmsg_len = 0;
    public short nlmsg_type = 0;
    public short nlmsg_flags = 0;
    public int nlmsg_seq = 0;
    public int nlmsg_pid = 0;

    public void pack(ByteBuffer byteBuffer) {
        byteBuffer.putInt(this.nlmsg_len);
        byteBuffer.putShort(this.nlmsg_type);
        byteBuffer.putShort(this.nlmsg_flags);
        byteBuffer.putInt(this.nlmsg_seq);
        byteBuffer.putInt(this.nlmsg_pid);
    }
}
