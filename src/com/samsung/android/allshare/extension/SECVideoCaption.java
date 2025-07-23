package com.samsung.android.allshare.extension;

import com.samsung.android.allshare.DLog;

/* loaded from: classes6.dex */
public class SECVideoCaption {
    private static final String TAG_CLASS = "SECVideoCaption";
    private static String mSubTitleURL;

    public String getSubTitleURL(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        GetSECCaption getSECCaption = new GetSECCaption(str);
        getSECCaption.start();
        try {
            getSECCaption.join(3000L);
        } catch (InterruptedException e) {
            DLog.w_api(TAG_CLASS, "getSubTitleURL : InterruptedException", e);
        }
        return mSubTitleURL;
    }

    private static class GetSECCaption extends Thread {
        private String mVideoURL;

        public GetSECCaption(String str) {
            this.mVideoURL = str;
            SECVideoCaption.mSubTitleURL = null;
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:7:0x005b  */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                r6 = this;
                java.lang.String r0 = "1"
                java.lang.String r1 = "SECVideoCaption"
                r2 = 0
                java.net.URL r3 = new java.net.URL     // Catch: java.lang.IllegalArgumentException -> L3f java.io.IOException -> L4c
                java.lang.String r6 = r6.mVideoURL     // Catch: java.lang.IllegalArgumentException -> L3f java.io.IOException -> L4c
                r3.<init>(r6)     // Catch: java.lang.IllegalArgumentException -> L3f java.io.IOException -> L4c
                java.net.URLConnection r6 = r3.openConnection()     // Catch: java.lang.IllegalArgumentException -> L3f java.io.IOException -> L4c
                java.net.HttpURLConnection r6 = (java.net.HttpURLConnection) r6     // Catch: java.lang.IllegalArgumentException -> L3f java.io.IOException -> L4c
                if (r6 == 0) goto L3b
                java.lang.String r3 = "GET"
                r6.setRequestMethod(r3)     // Catch: java.lang.IllegalArgumentException -> L37 java.io.IOException -> L39
                java.lang.String r3 = "HEAD"
                r6.setRequestMethod(r3)     // Catch: java.lang.IllegalArgumentException -> L37 java.io.IOException -> L39
                java.lang.String r3 = "User-Agent"
                java.lang.String r4 = "DMPVideoSubtitle"
                r6.addRequestProperty(r3, r4)     // Catch: java.lang.IllegalArgumentException -> L37 java.io.IOException -> L39
                java.lang.String r3 = "getCaptionInfo.sec"
                r6.setRequestProperty(r3, r0)     // Catch: java.lang.IllegalArgumentException -> L37 java.io.IOException -> L39
                java.lang.String r3 = "getcontentFeatures.dlna.org"
                r6.setRequestProperty(r3, r0)     // Catch: java.lang.IllegalArgumentException -> L37 java.io.IOException -> L39
                java.lang.String r0 = "CaptionInfo.sec"
                java.lang.String r0 = r6.getHeaderField(r0)     // Catch: java.lang.IllegalArgumentException -> L37 java.io.IOException -> L39
                r2 = r0
                goto L3b
            L37:
                r0 = move-exception
                goto L41
            L39:
                r0 = move-exception
                goto L4e
            L3b:
                r5 = r2
                r2 = r6
                r6 = r5
                goto L59
            L3f:
                r0 = move-exception
                r6 = r2
            L41:
                java.lang.String r3 = "GetSECCaption : IllegalArgumentException"
                com.samsung.android.allshare.DLog.w_api(r1, r3, r0)
                if (r6 == 0) goto L58
                r6.disconnect()
                goto L58
            L4c:
                r0 = move-exception
                r6 = r2
            L4e:
                java.lang.String r3 = "GetSECCaption : IOException"
                com.samsung.android.allshare.DLog.w_api(r1, r3, r0)
                if (r6 == 0) goto L58
                r6.disconnect()
            L58:
                r6 = r2
            L59:
                if (r2 == 0) goto L5e
                r2.disconnect()
            L5e:
                com.samsung.android.allshare.extension.SECVideoCaption.m8911$$Nest$sfputmSubTitleURL(r6)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.allshare.extension.SECVideoCaption.GetSECCaption.run():void");
        }
    }
}
