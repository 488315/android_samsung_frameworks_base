package com.android.server.pm;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.Base64;
import android.webkit.MimeTypeMap;
import com.android.server.DisplayThread;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.function.BiConsumer;

/* loaded from: classes3.dex */
public class ContentDispatcher {
  public Context mContext;
  public ArrayMap mPdfInfos = new ArrayMap();
  public Handler mHandler = new Handler(DisplayThread.get().getLooper());

  public class PdfInfo {
    public int mCallingUid;
    public BiConsumer mGrantUriPermission;
    public Intent mIntent;
    public String mPackageName;
    public String mPdfFileName;

    public PdfInfo(int i, Intent intent, String str, BiConsumer biConsumer) {
      this.mCallingUid = i;
      this.mIntent = intent;
      this.mPackageName = str;
      this.mGrantUriPermission = biConsumer;
    }
  }

  public ContentDispatcher(Context context) {
    this.mContext = context;
    this.mPdfInfos.clear();
  }

  public String[] getContentByTask(int i) {
    String[] strArr = {"", "", ""};
    synchronized (this.mPdfInfos) {
      PdfInfo pdfInfo = (PdfInfo) this.mPdfInfos.get(Integer.valueOf(i));
      if (pdfInfo == null) {
        return strArr;
      }
      try {
        pdfInfo.mGrantUriPermission.accept(
            Integer.valueOf(pdfInfo.mCallingUid), pdfInfo.mIntent.getData());
        strArr[0] = pdfInfo.mIntent.getData().toString();
        strArr[2] = pdfInfo.mPackageName;
        return strArr;
      } catch (Exception e) {
        e.printStackTrace();
        return null;
      }
    }
  }

  public void scheduleClearPdfTask(final int i) {
    this.mHandler.post(
        new Runnable() { // from class:
          // com.android.server.pm.ContentDispatcher$$ExternalSyntheticLambda0
          @Override // java.lang.Runnable
          public final void run() {
            ContentDispatcher.this.lambda$scheduleClearPdfTask$0(i);
          }
        });
  }

  /* renamed from: clearPdfTask, reason: merged with bridge method [inline-methods] */
  public void lambda$scheduleClearPdfTask$0(int i) {
    synchronized (this.mPdfInfos) {
      this.mPdfInfos.remove(Integer.valueOf(i));
    }
  }

  public void openPdfFile(
      final int i,
      final String str,
      Intent intent,
      ActivityInfo activityInfo,
      final int i2,
      final int i3,
      final BiConsumer biConsumer) {
    ApplicationInfo applicationInfo;
    if (activityInfo == null
        || (applicationInfo = activityInfo.applicationInfo) == null
        || applicationInfo.packageName == null
        || intent == null) {
      return;
    }
    final Intent intent2 = (Intent) intent.clone();
    final String str2 = activityInfo.applicationInfo.packageName;
    this.mHandler.post(
        new Runnable() { // from class:
          // com.android.server.pm.ContentDispatcher$$ExternalSyntheticLambda1
          @Override // java.lang.Runnable
          public final void run() {
            ContentDispatcher.this.lambda$openPdfFile$1(i3, str, str2, intent2, i, biConsumer, i2);
          }
        });
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ void lambda$openPdfFile$1(
      int i, String str, String str2, Intent intent, int i2, BiConsumer biConsumer, int i3) {
    synchronized (this.mPdfInfos) {
      if (this.mPdfInfos.get(Integer.valueOf(i)) != null && !"android".equals(str)) {
        if (!str2.equals(((PdfInfo) this.mPdfInfos.get(Integer.valueOf(i))).mPackageName)
            && !"android.content.pm.action.REQUEST_PERMISSIONS".equals(intent.getAction())
            && !"android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION".equals(intent.getAction())
            && !"android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION"
                .equals(intent.getAction())) {
          lambda$scheduleClearPdfTask$0(i);
        } else if (str == null
            || !str.equals(((PdfInfo) this.mPdfInfos.get(Integer.valueOf(i))).mPackageName)) {
          lambda$scheduleClearPdfTask$0(i);
        }
      }
      if ("android.intent.action.VIEW".equals(intent.getAction())
          && !"android".equals(str2)
          && intent.getData() != null
          && i2 >= 0) {
        String extensionFromMimeType =
            MimeTypeMap.getSingleton()
                .getExtensionFromMimeType(
                    this.mContext.getContentResolver().getType(intent.getData()));
        lambda$scheduleClearPdfTask$0(i);
        if (!"pdf".equals(extensionFromMimeType) && !"application/pdf".equals(intent.getType())) {
        } else {
          this.mPdfInfos.put(Integer.valueOf(i), new PdfInfo(i2, intent, str2, biConsumer));
        }
      } else if (this.mPdfInfos.get(Integer.valueOf(i3)) != null
          && this.mPdfInfos.get(Integer.valueOf(i)) == null
          && str2.equals(str)
          && ((PdfInfo) this.mPdfInfos.get(Integer.valueOf(i3))).mPackageName.equals(str)) {
        this.mPdfInfos.put(Integer.valueOf(i), (PdfInfo) this.mPdfInfos.get(Integer.valueOf(i3)));
      }
    }
  }

  public void dump(PrintWriter printWriter) {
    synchronized (this.mPdfInfos) {
      printWriter.println("ACTIVITY MANAGER CONTENT_DISPATCHER");
      Iterator it = this.mPdfInfos.keySet().iterator();
      while (it.hasNext()) {
        int intValue = ((Integer) it.next()).intValue();
        PdfInfo pdfInfo = (PdfInfo) this.mPdfInfos.get(Integer.valueOf(intValue));
        if (pdfInfo != null) {
          printWriter.println("  TaskId: " + intValue);
          printWriter.println("    viewer: " + pdfInfo.mPackageName);
          printWriter.println(
              "    content-first: "
                  + Base64.encodeToString(pdfInfo.mIntent.getData().toString().getBytes(), 2));
          if (pdfInfo.mPdfFileName != null) {
            printWriter.println(
                "    content-second: " + Base64.encodeToString(pdfInfo.mPdfFileName.getBytes(), 2));
          }
        }
      }
      printWriter.println();
    }
  }
}
