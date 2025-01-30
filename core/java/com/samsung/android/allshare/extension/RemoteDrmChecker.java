package com.samsung.android.allshare.extension;

import android.content.Context;
import android.content.IntentFilter;
import android.drm.DrmManagerClient;
import android.os.Environment;
import com.google.android.mms.ContentType;
import com.samsung.android.allshare.DLog;
import com.samsung.android.share.SemShareConstants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/* loaded from: classes5.dex */
public class RemoteDrmChecker {
  static final int DRM_PROTECTED_ERROR = -1;
  static final int DRM_PROTECTED_FALSE = 0;
  static final int DRM_PROTECTED_TRUE = 1;
  static final String OMA_PLUGIN_MIME = "application/vnd.oma.drm.content";
  static final String PR_PLUGIN_MIME = "audio/vnd.ms-playready.media.pya";
  static final String WMA_PLUGIN_MIME = "audio/x-ms-wma";
  static final String WMV_PLUGIN_MIME = "video/x-ms-wmv";
  private static final String mTAG = "RemoteDrmChecker";
  private static final String mTAG_CLASS = " ";
  private Context mContext;
  private boolean mIsDrmChecked;
  private int mIsDrmFile;
  private final String mPath =
      Environment.getExternalStorageDirectory().getAbsolutePath()
          + "/Android/data/com.samsung.android.allshare/drmchecker/";

  @Deprecated
  public RemoteDrmChecker(Context context) {
    this.mContext = context;
  }

  public int isDrmFile(String selectedItemUri, String mExtension, String mimeType) {
    if (mExtension == null && mimeType == null) {
      DLog.i_api(mTAG, " Invalid arg.");
      return -1;
    }
    if (selectedItemUri == null
        || selectedItemUri.isEmpty()
        || !selectedItemUri.startsWith(IntentFilter.SCHEME_HTTP)) {
      DLog.i_api(mTAG, " Invalid Url.");
      return -1;
    }
    if ((mExtension != null
            && (mExtension.toLowerCase().equals(".wmv") || mExtension.toLowerCase().equals(".wma")))
        || (mimeType != null
            && (mimeType.equals("video/x-ms-wmv")
                || mimeType.equals("audio/x-ms-wma")
                || mimeType.equals(ContentType.AUDIO_WAV)
                || mimeType.equals(ContentType.AUDIO_X_WAV)))) {
      this.mIsDrmChecked = false;
      DrmCheckThread mDrmCheckThread = new DrmCheckThread(selectedItemUri, mExtension, mimeType);
      mDrmCheckThread.start();
      try {
        mDrmCheckThread.join(5000L);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      if (!this.mIsDrmChecked) {
        DLog.w_api(mTAG, " thread time out : isDrmFile = " + this.mIsDrmFile);
        return -1;
      }
      DLog.i_api(mTAG, " returning isDrmFile = " + this.mIsDrmFile);
      return this.mIsDrmFile;
    }
    DLog.i_api(mTAG, " return FALSE, immediately.");
    return 0;
  }

  private class DrmCheckThread extends Thread {
    private String mExtension;
    private String mMimeType;
    private String mSelectedItemUri;

    public DrmCheckThread(String selectedItemUri, String mExtension, String mimeType) {
      this.mSelectedItemUri = selectedItemUri;
      this.mExtension = mExtension;
      this.mMimeType = mimeType;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
      HttpURLConnection connection;
      String str;
      String str2;
      RemoteDrmChecker.this.mIsDrmFile = 0;
      HttpURLConnection connection2 = null;
      InputStream inStream = null;
      FileOutputStream fos = null;
      String filename = "tempfile." + String.valueOf(System.nanoTime());
      String filePath = RemoteDrmChecker.this.mPath + filename;
      try {
        try {
          try {
            try {
              File dirPath = new File(RemoteDrmChecker.this.mPath);
              if (!dirPath.exists()) {
                DLog.i_api(RemoteDrmChecker.mTAG, dirPath + " is not exist");
                dirPath.mkdirs();
              }
              URL inputURL = new URL(this.mSelectedItemUri);
              connection = (HttpURLConnection) inputURL.openConnection();
            } catch (IOException e) {
              e.printStackTrace();
            }
          } catch (IOException e2) {
            DLog.w_api(RemoteDrmChecker.mTAG, " IOException");
            e2.printStackTrace();
            if (0 != 0) {
              connection2.disconnect();
            }
            if (0 != 0) {
              try {
                fos.close();
              } catch (IOException e3) {
                e3.printStackTrace();
              }
            }
            if (0 != 0) {
              inStream.close();
            }
          }
        } catch (Exception e4) {
          DLog.w_api(RemoteDrmChecker.mTAG, " Exception");
          e4.printStackTrace();
          if (0 != 0) {
            connection2.disconnect();
          }
          if (0 != 0) {
            try {
              fos.close();
            } catch (IOException e5) {
              e5.printStackTrace();
            }
          }
          if (0 != 0) {
            inStream.close();
          }
        }
        if (connection == null) {
          DLog.i_api(RemoteDrmChecker.mTAG, " fail to openConnection connection");
          if (connection != null) {
            connection.disconnect();
          }
          if (0 != 0) {
            try {
              fos.close();
            } catch (IOException e6) {
              e6.printStackTrace();
            }
          }
          if (0 != 0) {
            try {
              inStream.close();
              return;
            } catch (IOException e7) {
              e7.printStackTrace();
              return;
            }
          }
          return;
        }
        connection.setRequestMethod(SemShareConstants.HTTP_CONN_REQUEST_METHOD);
        if (filename != null) {
          connection.addRequestProperty("User-Agent", filename);
        }
        InputStream inStream2 = connection.getInputStream();
        File file = new File(filePath);
        FileOutputStream fos2 = new FileOutputStream(file);
        byte[] buf = new byte[10000];
        for (int i = 0; i < 3; i++) {
          int readCount = inStream2.read(buf);
          if (readCount > 0) {
            fos2.write(buf, 0, readCount);
          }
        }
        fos2.close();
        DrmManagerClient drmClient = new DrmManagerClient(RemoteDrmChecker.this.mContext);
        boolean temp_isDrmFile = false;
        String str3 = this.mExtension;
        if ((str3 == null || !".wma".equals(str3.toLowerCase()))
            && ((str = this.mMimeType) == null
                || !(str.endsWith("wma") || this.mMimeType.endsWith("wav")))) {
          String str4 = this.mExtension;
          if ((str4 != null && ".wmv".equals(str4.toLowerCase()))
              || ((str2 = this.mMimeType) != null && str2.endsWith("wmv"))) {
            DLog.i_api(RemoteDrmChecker.mTAG, " check DrmManagerClient - WMV_PLUGIN_MIME");
            temp_isDrmFile = drmClient.canHandle(filePath, "video/x-ms-wmv");
          }
        } else {
          DLog.i_api(RemoteDrmChecker.mTAG, " check DrmManagerClient - WMA_PLUGIN_MIME");
          temp_isDrmFile = drmClient.canHandle(filePath, "audio/x-ms-wma");
        }
        if (temp_isDrmFile) {
          RemoteDrmChecker.this.mIsDrmFile = 1;
        } else {
          RemoteDrmChecker.this.mIsDrmFile = 0;
        }
        DLog.i_api(
            RemoteDrmChecker.mTAG, "  === THIS IS DRM FILE ? " + RemoteDrmChecker.this.mIsDrmFile);
        RemoteDrmChecker.this.mIsDrmChecked = true;
        if (connection != null) {
          connection.disconnect();
        }
        try {
          fos2.close();
        } catch (IOException e8) {
          e8.printStackTrace();
        }
        if (inStream2 != null) {
          inStream2.close();
        }
        try {
          new File(filePath).delete();
        } catch (Exception e9) {
          DLog.i_api(RemoteDrmChecker.mTAG, " fail to new File( filePath ).delete();");
        }
        DLog.i_api(
            RemoteDrmChecker.mTAG,
            "  DRM check end : isDrmFile = " + RemoteDrmChecker.this.mIsDrmFile);
      } catch (Throwable e10) {
        if (0 != 0) {
          connection2.disconnect();
        }
        if (0 != 0) {
          try {
            fos.close();
          } catch (IOException e11) {
            e11.printStackTrace();
          }
        }
        if (0 == 0) {
          throw e10;
        }
        try {
          inStream.close();
          throw e10;
        } catch (IOException e12) {
          e12.printStackTrace();
          throw e10;
        }
      }
    }
  }
}
