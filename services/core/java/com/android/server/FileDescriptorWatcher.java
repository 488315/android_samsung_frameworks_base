package com.android.server;

import android.content.Context;

public final class FileDescriptorWatcher {
    public static Context mContext;
    public int mFdCount;

    public final class FileDescriptorLeakWatcher implements Runnable {
        /* JADX WARN: Code restructure failed: missing block: B:122:0x0144, code lost:

           if (r5 != null) goto L35;
        */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instructions count: 596
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.FileDescriptorWatcher.FileDescriptorLeakWatcher.run():void");
        }
    }
}
