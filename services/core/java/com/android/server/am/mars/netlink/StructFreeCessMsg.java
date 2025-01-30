package com.android.server.am.mars.netlink;

import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public class StructFreeCessMsg {
  public int cmd;
  public byte[] rpcname = new byte[100];
  public int type = 0;
  public int mod = 0;
  public int src_portid = 0;
  public int dst_portid = 0;
  public int version = 0;
  public int target_uid = 0;
  public int flag = 0;
  public int code = 0;
  public int uid = 0;

  public void pack(ByteBuffer byteBuffer) {
    byteBuffer.putInt(this.type);
    byteBuffer.putInt(this.mod);
    byteBuffer.putInt(this.src_portid);
    byteBuffer.putInt(this.dst_portid);
    byteBuffer.putInt(this.version);
    byteBuffer.putInt(this.target_uid);
    byteBuffer.putInt(this.flag);
    byteBuffer.putInt(this.code);
    for (int i = 0; i < 100; i++) {
      byteBuffer.put(this.rpcname[i]);
    }
    byteBuffer.putInt(this.cmd);
    byteBuffer.putInt(this.uid);
  }

  public static StructFreeCessMsg parse(ByteBuffer byteBuffer) {
    StructNlMsgHdr structNlMsgHdr = new StructNlMsgHdr();
    structNlMsgHdr.nlmsg_len = byteBuffer.getInt();
    structNlMsgHdr.nlmsg_type = byteBuffer.getShort();
    structNlMsgHdr.nlmsg_flags = byteBuffer.getShort();
    structNlMsgHdr.nlmsg_seq = byteBuffer.getInt();
    structNlMsgHdr.nlmsg_pid = byteBuffer.getInt();
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

  public String toString() {
    return ((("struct.type = " + this.type) + "struct.mode = " + this.mod)
            + "struct.src_portid = "
            + this.src_portid)
        + "struct.dst_portid = "
        + this.dst_portid;
  }
}
