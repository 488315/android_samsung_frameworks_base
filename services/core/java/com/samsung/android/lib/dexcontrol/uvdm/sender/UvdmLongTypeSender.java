package com.samsung.android.lib.dexcontrol.uvdm.sender;

import com.samsung.android.lib.dexcontrol.utils.SLog;
import com.samsung.android.lib.dexcontrol.uvdm.UvdmFileHelper;
import com.samsung.android.lib.dexcontrol.uvdm.response.ResponseResult;

/* loaded from: classes2.dex */
public class UvdmLongTypeSender extends UvdmSendExecutor {
    public static final String TAG = "UvdmLongTypeSender";
    public int mInMsgMinLength;

    public UvdmLongTypeSender(int i) {
        super(i);
        this.mInMsgMinLength = 0;
    }

    @Override // com.samsung.android.lib.dexcontrol.uvdm.sender.UvdmSendExecutor, com.samsung.android.lib.dexcontrol.uvdm.sender.IUvdmSender
    public void setInMsgMinLength(int i) {
        this.mInMsgMinLength = i;
    }

    public final void replyError(byte[] bArr, int i) {
        if (this.mListener != null) {
            ResponseResult responseResult = new ResponseResult();
            responseResult.setData((byte[]) bArr.clone());
            this.mListener.onFail(i, responseResult);
        }
    }

    public final void sleep(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override // com.samsung.android.lib.dexcontrol.uvdm.sender.UvdmSendExecutor
    public String getTag() {
        return TAG;
    }

    public final void close() {
        UvdmFileHelper uvdmFileHelper = this.mUvdmFileHelper;
        if (uvdmFileHelper != null) {
            int ccic_close = uvdmFileHelper.ccic_close();
            SLog.m126d(TAG, "ccic_close : " + ccic_close);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x012c, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x004e, code lost:
    
        if (r3 != false) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0054, code lost:
    
        if (getSenderEnable() != false) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0056, code lost:
    
        r10.mUvdmFileHelper = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x005e, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0059, code lost:
    
        replyError(r11, -1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x005f, code lost:
    
        r3 = 0;
        r4 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0063, code lost:
    
        if (r3 >= 10) goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0069, code lost:
    
        if (getSenderEnable() == false) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x006b, code lost:
    
        if (r4 != false) goto L102;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x006d, code lost:
    
        r4 = r10.mUvdmFileHelper;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x006f, code lost:
    
        if (r4 == null) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0071, code lost:
    
        r4 = r4.ioctl_longDataWrite(getPid(), r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0079, code lost:
    
        if (r4 < 0) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x007b, code lost:
    
        r6 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x007e, code lost:
    
        if (r6 != false) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0080, code lost:
    
        com.samsung.android.lib.dexcontrol.utils.SLog.m127e(com.samsung.android.lib.dexcontrol.uvdm.sender.UvdmLongTypeSender.TAG, "ccic_write failed : " + r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0096, code lost:
    
        r3 = r3 + 1;
        r4 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x007d, code lost:
    
        r6 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x009b, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x009c, code lost:
    
        if (r4 != false) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x009e, code lost:
    
        close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00a5, code lost:
    
        if (getSenderEnable() != false) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00a7, code lost:
    
        r10.mUvdmFileHelper = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00af, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00aa, code lost:
    
        replyError(r11, -2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00b0, code lost:
    
        r3 = false;
        r4 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00b2, code lost:
    
        if (r1 >= 10) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00b8, code lost:
    
        if (getSenderEnable() == false) goto L107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00ba, code lost:
    
        if (r3 != false) goto L108;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00be, code lost:
    
        if (r10.mUvdmFileHelper == null) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00c0, code lost:
    
        if (r12 <= 0) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00c2, code lost:
    
        sleep(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00c5, code lost:
    
        r4 = r10.mUvdmFileHelper.ioctl_longDataRead(getPid());
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x00cf, code lost:
    
        if (r4 == null) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x00d4, code lost:
    
        if (r4.length < r10.mInMsgMinLength) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x00d6, code lost:
    
        r3 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x00f3, code lost:
    
        r1 = r1 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x00d8, code lost:
    
        com.samsung.android.lib.dexcontrol.utils.SLog.m127e(com.samsung.android.lib.dexcontrol.uvdm.sender.UvdmLongTypeSender.TAG, "ccic_read failed : " + com.samsung.android.lib.dexcontrol.utils.Util.byteArrayToHex(r4));
        r4 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x00f7, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x00f8, code lost:
    
        close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x00ff, code lost:
    
        if (getSenderEnable() == false) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0101, code lost:
    
        if (r3 == false) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0105, code lost:
    
        if (r10.mListener == null) goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0107, code lost:
    
        r11 = new com.samsung.android.lib.dexcontrol.uvdm.response.ResponseResult();
        r11.setData(r4);
        r10.mListener.onSuccess(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0115, code lost:
    
        com.samsung.android.lib.dexcontrol.utils.SLog.m127e(getTag(), "callback is null");
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x011f, code lost:
    
        replyError(r11, -3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0127, code lost:
    
        if (getSenderEnable() != false) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x0129, code lost:
    
        r10.mUvdmFileHelper = null;
     */
    @Override // com.samsung.android.lib.dexcontrol.uvdm.sender.UvdmSendExecutor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void sendData(byte[] bArr, int i) {
        synchronized (UvdmLongTypeSender.class) {
            int i2 = 0;
            int i3 = 0;
            boolean z = false;
            while (true) {
                boolean z2 = true;
                if (i3 >= 16) {
                    break;
                }
                try {
                    if (!getSenderEnable() || z) {
                        break;
                    }
                    UvdmFileHelper uvdmFileHelper = this.mUvdmFileHelper;
                    if (uvdmFileHelper == null) {
                        return;
                    }
                    int ccic_open = uvdmFileHelper.ccic_open();
                    if (ccic_open < 0) {
                        z2 = false;
                    }
                    if (!z2) {
                        SLog.m127e(TAG, "ccic_open failed : " + ccic_open);
                    }
                    if (!z2 && getSenderEnable()) {
                        sleep(500);
                    }
                    i3++;
                    z = z2;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
