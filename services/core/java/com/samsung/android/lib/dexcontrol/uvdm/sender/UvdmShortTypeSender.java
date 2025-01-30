package com.samsung.android.lib.dexcontrol.uvdm.sender;

import com.samsung.android.lib.dexcontrol.utils.SLog;
import com.samsung.android.lib.dexcontrol.uvdm.UvdmFileHelper;

/* loaded from: classes2.dex */
public class UvdmShortTypeSender extends UvdmSendExecutor {
    public static final String TAG = "UvdmShortTypeSender";

    public UvdmShortTypeSender(int i) {
        super(i);
    }

    @Override // com.samsung.android.lib.dexcontrol.uvdm.sender.UvdmSendExecutor
    public void sendData(byte[] bArr, int i) {
        int i2 = -4;
        for (int i3 = 0; i3 < 10 && i2 != 0 && getSenderEnable(); i3++) {
            SLog.m126d(TAG, i3 + " / 10");
            synchronized (UvdmShortTypeSender.class) {
                UvdmFileHelper uvdmFileHelper = this.mUvdmFileHelper;
                if (uvdmFileHelper == null) {
                    return;
                }
                int ccic_open = uvdmFileHelper.ccic_open();
                SLog.m126d(getTag(), "ccic_open : " + ccic_open);
                if (ccic_open >= 0) {
                    if (getSenderEnable()) {
                        UvdmFileHelper uvdmFileHelper2 = this.mUvdmFileHelper;
                        if (uvdmFileHelper2 == null) {
                            return;
                        }
                        int ioctl_shortDataWrite = uvdmFileHelper2.ioctl_shortDataWrite(getPid(), bArr);
                        int i4 = ioctl_shortDataWrite >= 0 ? 0 : -2;
                        SLog.m126d(getTag(), "ccic_write : " + ioctl_shortDataWrite);
                        i2 = i4;
                    }
                    UvdmFileHelper uvdmFileHelper3 = this.mUvdmFileHelper;
                    if (uvdmFileHelper3 == null) {
                        return;
                    }
                    int ccic_close = uvdmFileHelper3.ccic_close();
                    SLog.m126d(getTag(), "ccic_close : " + ccic_close);
                } else {
                    i2 = -1;
                }
                if (!getSenderEnable()) {
                    this.mUvdmFileHelper = null;
                    return;
                }
                if (i2 != 0) {
                    SLog.m127e(getTag(), "write data failed = " + i2);
                    try {
                        Thread.sleep(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override // com.samsung.android.lib.dexcontrol.uvdm.sender.UvdmSendExecutor
    public String getTag() {
        return TAG;
    }
}
