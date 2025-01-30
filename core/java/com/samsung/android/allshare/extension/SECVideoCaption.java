package com.samsung.android.allshare.extension;

import android.telecom.TelecomManager;
import com.samsung.android.allshare.DLog;
import com.samsung.android.share.SemShareConstants;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/* loaded from: classes5.dex */
public class SECVideoCaption {
  private static final String TAG_CLASS = "SECVideoCaption";
  private static String mSubTitleURL;

  public String getSubTitleURL(String resourceURL) {
    if (resourceURL == null || resourceURL.isEmpty()) {
      return null;
    }
    GetSECCaption mGetSubTitleThread = new GetSECCaption(resourceURL);
    mGetSubTitleThread.start();
    try {
      mGetSubTitleThread.join(TelecomManager.VERY_SHORT_CALL_TIME_MS);
    } catch (InterruptedException e) {
      DLog.w_api(TAG_CLASS, "getSubTitleURL : InterruptedException", e);
    }
    return mSubTitleURL;
  }

  private static class GetSECCaption extends Thread {
    private String mVideoURL;

    public GetSECCaption(String resourceURL) {
      this.mVideoURL = resourceURL;
      SECVideoCaption.mSubTitleURL = null;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
      String ext = null;
      HttpURLConnection connection = null;
      try {
        URL url = new URL(this.mVideoURL);
        connection = (HttpURLConnection) url.openConnection();
        if (connection != null) {
          connection.setRequestMethod(SemShareConstants.HTTP_CONN_REQUEST_METHOD);
          connection.setRequestMethod("HEAD");
          connection.addRequestProperty("User-Agent", "DMPVideoSubtitle");
          connection.setRequestProperty("getCaptionInfo.sec", "1");
          connection.setRequestProperty("getcontentFeatures.dlna.org", "1");
          ext = connection.getHeaderField("CaptionInfo.sec");
        }
      } catch (IOException e) {
        DLog.w_api(SECVideoCaption.TAG_CLASS, "GetSECCaption : IOException", e);
        if (connection != null) {
          connection.disconnect();
        }
        connection = null;
      } catch (IllegalArgumentException e2) {
        DLog.w_api(SECVideoCaption.TAG_CLASS, "GetSECCaption : IllegalArgumentException", e2);
        if (connection != null) {
          connection.disconnect();
        }
        connection = null;
      }
      if (connection != null) {
        connection.disconnect();
      }
      SECVideoCaption.mSubTitleURL = ext;
    }
  }
}
