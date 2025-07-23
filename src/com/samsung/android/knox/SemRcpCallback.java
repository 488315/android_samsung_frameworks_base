package com.samsung.android.knox;

import com.samsung.android.knox.SemIRCPCallback;
import java.util.List;

/* loaded from: classes6.dex */
public abstract class SemRcpCallback {
    private SemIRCPCallback s = new SubSemRcpCallback(this, this);

    public abstract void onComplete(List<String> list, int i, int i2);

    public abstract void onDone(String str, int i);

    public abstract void onFail(String str, int i, int i2);

    public abstract void onProgress(String str, int i, int i2);

    public class SubSemRcpCallback extends SemIRCPCallback.Stub {
        SemRcpCallback parent;

        public SubSemRcpCallback(SemRcpCallback semRcpCallback, SemRcpCallback semRcpCallback2) {
            this.parent = semRcpCallback2;
        }

        @Override // com.samsung.android.knox.SemIRCPCallback
        public void onComplete(List<String> list, int i, int i2) {
            SemRcpCallback semRcpCallback = this.parent;
            if (semRcpCallback != null) {
                semRcpCallback.onComplete(list, i, i2);
            }
        }

        @Override // com.samsung.android.knox.SemIRCPCallback
        public void onDone(String str, int i) {
            SemRcpCallback semRcpCallback = this.parent;
            if (semRcpCallback != null) {
                semRcpCallback.onDone(str, i);
            }
        }

        @Override // com.samsung.android.knox.SemIRCPCallback
        public void onFail(String str, int i, int i2) {
            SemRcpCallback semRcpCallback = this.parent;
            if (semRcpCallback != null) {
                semRcpCallback.onFail(str, i, i2);
            }
        }

        @Override // com.samsung.android.knox.SemIRCPCallback
        public void onProgress(String str, int i, int i2) {
            SemRcpCallback semRcpCallback = this.parent;
            if (semRcpCallback != null) {
                semRcpCallback.onProgress(str, i, i2);
            }
        }
    }

    public SemIRCPCallback getChild() {
        return this.s;
    }
}
