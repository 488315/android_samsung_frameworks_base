package com.samsung.android.allshare.extension;

import com.samsung.android.allshare.DLog;
import com.samsung.android.share.SemShareConstants;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

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
        /* JADX WARN: Removed duplicated region for block: B:24:0x005b  */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() throws ProtocolException {
            HttpURLConnection httpURLConnection;
            String str;
            HttpURLConnection headerField = null;
            try {
                httpURLConnection = (HttpURLConnection) new URL(this.mVideoURL).openConnection();
                if (httpURLConnection != null) {
                    try {
                        httpURLConnection.setRequestMethod(SemShareConstants.HTTP_CONN_REQUEST_METHOD);
                        httpURLConnection.setRequestMethod("HEAD");
                        httpURLConnection.addRequestProperty("User-Agent", "DMPVideoSubtitle");
                        httpURLConnection.setRequestProperty("getCaptionInfo.sec", "1");
                        httpURLConnection.setRequestProperty("getcontentFeatures.dlna.org", "1");
                        headerField = httpURLConnection.getHeaderField("CaptionInfo.sec");
                    } catch (IOException e) {
                        e = e;
                        DLog.w_api(SECVideoCaption.TAG_CLASS, "GetSECCaption : IOException", e);
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        str = 0;
                        if (headerField != null) {
                        }
                        SECVideoCaption.mSubTitleURL = str;
                    } catch (IllegalArgumentException e2) {
                        e = e2;
                        DLog.w_api(SECVideoCaption.TAG_CLASS, "GetSECCaption : IllegalArgumentException", e);
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        str = 0;
                        if (headerField != null) {
                        }
                        SECVideoCaption.mSubTitleURL = str;
                    }
                }
                HttpURLConnection httpURLConnection2 = headerField;
                headerField = httpURLConnection;
                str = httpURLConnection2;
            } catch (IOException e3) {
                e = e3;
                httpURLConnection = null;
            } catch (IllegalArgumentException e4) {
                e = e4;
                httpURLConnection = null;
            }
            if (headerField != null) {
                headerField.disconnect();
            }
            SECVideoCaption.mSubTitleURL = str;
        }
    }
}
