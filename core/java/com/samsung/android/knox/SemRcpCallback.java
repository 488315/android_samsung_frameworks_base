package com.samsung.android.knox;

import java.util.List;

public abstract class SemRcpCallback {
    private SemIRCPCallback s = new SubSemRcpCallback(this);

    public abstract void onComplete(List<String> list, int i, int i2);

    public abstract void onDone(String str, int i);

    public abstract void onFail(String str, int i, int i2);

    public abstract void onProgress(String str, int i, int i2);

    public class SubSemRcpCallback extends SemIRCPCallback.Stub {
        SemRcpCallback parent;

        public SubSemRcpCallback(SemRcpCallback SemRcpCallback) {
            this.parent = null;
            this.parent = SemRcpCallback;
        }

        @Override // com.samsung.android.knox.SemIRCPCallback
        public void onComplete(List<String> srcPathsOrig, int destinationUserId, int successCnt) {
            if (this.parent != null) {
                this.parent.onComplete(srcPathsOrig, destinationUserId, successCnt);
            }
        }

        @Override // com.samsung.android.knox.SemIRCPCallback
        public void onDone(String srcPathsOrig, int destinationUserId) {
            if (this.parent != null) {
                this.parent.onDone(srcPathsOrig, destinationUserId);
            }
        }

        @Override // com.samsung.android.knox.SemIRCPCallback
        public void onFail(String srcPathsOrig, int destinationUserId, int errorCode) {
            if (this.parent != null) {
                this.parent.onFail(srcPathsOrig, destinationUserId, errorCode);
            }
        }

        @Override // com.samsung.android.knox.SemIRCPCallback
        public void onProgress(String srcPathsOrig, int destinationUserId, int progress) {
            if (this.parent != null) {
                this.parent.onProgress(srcPathsOrig, destinationUserId, progress);
            }
        }
    }

    public SemIRCPCallback getChild() {
        return this.s;
    }
}
