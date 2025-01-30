package com.android.server.am.mars.netlink;

import android.os.Process;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* loaded from: classes.dex */
public abstract class FreecessNetlinkMessage {
  public static int mLength = 156;

  public static byte[] newFreecessRequest(int i, int i2, int i3, int i4, int i5) {
    byte[] bArr = new byte[mLength];
    ByteBuffer wrap = ByteBuffer.wrap(bArr);
    wrap.order(ByteOrder.nativeOrder());
    StructNlMsgHdr structNlMsgHdr = new StructNlMsgHdr();
    structNlMsgHdr.nlmsg_len = mLength;
    structNlMsgHdr.nlmsg_type = (short) 30;
    structNlMsgHdr.nlmsg_flags = (short) 0;
    structNlMsgHdr.pack(wrap);
    StructFreeCessMsg structFreeCessMsg = new StructFreeCessMsg();
    structFreeCessMsg.type = i;
    structFreeCessMsg.mod = i2;
    structFreeCessMsg.src_portid = Process.myPid();
    structFreeCessMsg.dst_portid = 305402420;
    structFreeCessMsg.version = 536870912;
    structFreeCessMsg.target_uid = i3;
    structFreeCessMsg.flag = 0;
    structFreeCessMsg.code = 0;
    structFreeCessMsg.uid = i5;
    structFreeCessMsg.cmd = i4;
    structFreeCessMsg.pack(wrap);
    return bArr;
  }

  public static int getFreecessNetlinkMessageSize() {
    return mLength;
  }
}
