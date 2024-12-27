package com.samsung.android.lib.dexcontrol.uvdm.sender;

import com.samsung.android.lib.dexcontrol.uvdm.response.ResponseResult;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UvdmLongTypeSender extends UvdmSendExecutor {
    public int mInMsgMinLength;

    @Override // com.samsung.android.lib.dexcontrol.uvdm.sender.UvdmSendExecutor
    public final String getTag() {
        return "UvdmLongTypeSender";
    }

    public final void replyError(int i, byte[] bArr) {
        if (this.mListener != null) {
            ResponseResult responseResult = new ResponseResult();
            responseResult.mData = (byte[]) bArr.clone();
            this.mListener.onFail(i, responseResult);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x010c, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x010d, code lost:
    
        r11 = r10.mUvdmFileHelper;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x010f, code lost:
    
        if (r11 == null) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x0111, code lost:
    
        com.samsung.android.lib.dexcontrol.utils.SLog.d("UvdmLongTypeSender", "ccic_close : " + r11.ccic_close());
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x012a, code lost:
    
        if (r10.mIsEnabled == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x012c, code lost:
    
        if (r3 == false) goto L98;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x012e, code lost:
    
        r11 = r10.mListener;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x0130, code lost:
    
        if (r11 == null) goto L97;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x0132, code lost:
    
        r12 = new com.samsung.android.lib.dexcontrol.uvdm.response.ResponseResult();
        r12.mData = r4;
        r11.onSuccess(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x013d, code lost:
    
        com.samsung.android.lib.dexcontrol.utils.SLog.e("UvdmLongTypeSender", "callback is null");
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x0145, code lost:
    
        replyError(-3, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x014b, code lost:
    
        if (r10.mIsEnabled != false) goto L102;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x014d, code lost:
    
        r10.mUvdmFileHelper = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x0150, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0051, code lost:
    
        if (r3 != false) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0055, code lost:
    
        if (r10.mIsEnabled != false) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0057, code lost:
    
        r10.mUvdmFileHelper = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x005f, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x005a, code lost:
    
        replyError(-1, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0060, code lost:
    
        r3 = 0;
        r4 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0064, code lost:
    
        if (r3 >= 10) goto L119;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0068, code lost:
    
        if (r10.mIsEnabled == false) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x006a, code lost:
    
        if (r4 != false) goto L121;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x006c, code lost:
    
        r4 = r10.mUvdmFileHelper;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x006e, code lost:
    
        if (r4 == null) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0070, code lost:
    
        r4 = r4.ioctl_longDataWrite(r10.mPid, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0076, code lost:
    
        if (r4 < 0) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0078, code lost:
    
        r6 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x007b, code lost:
    
        if (r6 != false) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x007d, code lost:
    
        com.samsung.android.lib.dexcontrol.utils.SLog.e("UvdmLongTypeSender", "ccic_write failed : " + r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0093, code lost:
    
        r3 = r3 + 1;
        r4 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x007a, code lost:
    
        r6 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0098, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0099, code lost:
    
        if (r4 != false) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x009b, code lost:
    
        r11 = r10.mUvdmFileHelper;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x009d, code lost:
    
        if (r11 == null) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x009f, code lost:
    
        com.samsung.android.lib.dexcontrol.utils.SLog.d("UvdmLongTypeSender", "ccic_close : " + r11.ccic_close());
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00b8, code lost:
    
        if (r10.mIsEnabled != false) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00ba, code lost:
    
        r10.mUvdmFileHelper = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00c2, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00bd, code lost:
    
        replyError(-2, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00c3, code lost:
    
        r3 = false;
        r4 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x00c5, code lost:
    
        if (r1 >= 10) goto L125;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x00c9, code lost:
    
        if (r10.mIsEnabled == false) goto L126;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x00cb, code lost:
    
        if (r3 != false) goto L127;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x00cf, code lost:
    
        if (r10.mUvdmFileHelper == null) goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x00d1, code lost:
    
        if (r11 <= 0) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x00d4, code lost:
    
        java.lang.Thread.sleep(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x00d8, code lost:
    
        r4 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x00d9, code lost:
    
        r4.printStackTrace();
     */
    @Override // com.samsung.android.lib.dexcontrol.uvdm.sender.UvdmSendExecutor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void sendData(int r11, byte[] r12) {
        /*
            Method dump skipped, instructions count: 339
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.lib.dexcontrol.uvdm.sender.UvdmLongTypeSender.sendData(int, byte[]):void");
    }
}
