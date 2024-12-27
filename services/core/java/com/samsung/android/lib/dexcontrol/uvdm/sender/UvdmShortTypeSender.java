package com.samsung.android.lib.dexcontrol.uvdm.sender;

import com.samsung.android.lib.dexcontrol.utils.SLog;
import com.samsung.android.lib.dexcontrol.uvdm.UvdmFileHelper;

public final class UvdmShortTypeSender extends UvdmSendExecutor {
    @Override // com.samsung.android.lib.dexcontrol.uvdm.sender.UvdmSendExecutor
    public final String getTag() {
        return "UvdmShortTypeSender";
    }

    @Override // com.samsung.android.lib.dexcontrol.uvdm.sender.UvdmSendExecutor
    public final void sendData(int i, byte[] bArr) {
        int i2 = -4;
        for (int i3 = 0; i3 < 10 && i2 != 0 && this.mIsEnabled; i3++) {
            SLog.d("UvdmShortTypeSender", i3 + " / 10");
            synchronized (UvdmShortTypeSender.class) {
                try {
                    UvdmFileHelper uvdmFileHelper = this.mUvdmFileHelper;
                    if (uvdmFileHelper == null) {
                        return;
                    }
                    int ccic_open = uvdmFileHelper.ccic_open();
                    SLog.d("UvdmShortTypeSender", "ccic_open : " + ccic_open);
                    if (ccic_open >= 0) {
                        if (this.mIsEnabled) {
                            UvdmFileHelper uvdmFileHelper2 = this.mUvdmFileHelper;
                            if (uvdmFileHelper2 == null) {
                                return;
                            }
                            int ioctl_shortDataWrite =
                                    uvdmFileHelper2.ioctl_shortDataWrite(this.mPid, bArr);
                            int i4 = ioctl_shortDataWrite >= 0 ? 0 : -2;
                            SLog.d("UvdmShortTypeSender", "ccic_write : " + ioctl_shortDataWrite);
                            i2 = i4;
                        }
                        UvdmFileHelper uvdmFileHelper3 = this.mUvdmFileHelper;
                        if (uvdmFileHelper3 == null) {
                            return;
                        }
                        SLog.d(
                                "UvdmShortTypeSender",
                                "ccic_close : " + uvdmFileHelper3.ccic_close());
                    } else {
                        i2 = -1;
                    }
                    if (!this.mIsEnabled) {
                        this.mUvdmFileHelper = null;
                        return;
                    }
                    if (i2 != 0) {
                        SLog.e("UvdmShortTypeSender", "write data failed = " + i2);
                        try {
                            Thread.sleep(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }
}
