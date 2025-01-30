package com.android.server.desktopmode;

import android.R;
import android.content.Context;
import android.util.IndentingPrintWriter;
import android.view.Display;
import android.widget.Toast;
import com.android.server.UiThread;
import com.samsung.android.cover.CoverManager;
import com.samsung.android.cover.CoverState;
import com.samsung.android.desktopmode.DesktopModeFeature;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class ToastManager {
  public static volatile CoverManager sCoverManager;
  public static final String TAG = "[DMS]" + ToastManager.class.getSimpleName();
  public static final List sToasts = new ArrayList();

  public static void showToast(Context context, int i) {
    showToast(context, context.getString(i));
  }

  public static void showToast(Context context, String str) {
    showToast(context, str, 1);
  }

  public static void showToast(final Context context, final String str, final int i) {
    if (str == null || str.isEmpty()) {
      return;
    }
    UiThread.getHandler()
        .post(
            new Runnable() { // from class:
              // com.android.server.desktopmode.ToastManager$$ExternalSyntheticLambda0
              @Override // java.lang.Runnable
              public final void run() {
                ToastManager.lambda$showToast$0(context, str, i);
              }
            });
  }

  public static /* synthetic */ void lambda$showToast$0(Context context, final String str, int i) {
    final Toast makeText = Toast.makeText(context, str, i);
    makeText.addCallback(
        new Toast.Callback() { // from class: com.android.server.desktopmode.ToastManager.1
          @Override // android.widget.Toast.Callback
          public void onToastHidden() {
            synchronized (ToastManager.sToasts) {
              ToastManager.sToasts.remove(makeText);
              if (DesktopModeFeature.DEBUG) {
                Log.m7d(
                    ToastManager.TAG,
                    "Toast.onToastHidden(), msg=" + str + ", sToasts=" + ToastManager.sToasts);
              }
            }
          }
        });
    List list = sToasts;
    synchronized (list) {
      list.add(makeText);
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(TAG, "sToasts added, sToasts=" + list);
      }
    }
    adjustGravityForCoverUi(context, makeText);
    makeText.show();
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "Toast.show(" + str + ")");
    }
  }

  public static void adjustGravityForCoverUi(Context context, Toast toast) {
    if (DesktopModeFeature.IS_TABLET) {
      return;
    }
    Display display = context.getDisplay();
    if (display == null || display.getDisplayId() == 0) {
      if (sCoverManager == null) {
        sCoverManager = new CoverManager(context);
      }
      CoverState coverState = sCoverManager.getCoverState();
      if (coverState == null
          || !coverState.attached
          || coverState.switchState
          || coverState.getType() != 8) {
        return;
      }
      toast.setGravity(
          80,
          0,
          context.getResources().getDimensionPixelOffset(R.dimen.conversation_image_start_margin));
    }
  }

  public static void cancelToasts() {
    if (DesktopModeFeature.DEBUG) {
      Log.m7d(TAG, "cancelToasts()");
    }
    List list = sToasts;
    synchronized (list) {
      Iterator it = list.iterator();
      while (it.hasNext()) {
        Toast toast = (Toast) it.next();
        it.remove();
        toast.cancel();
      }
    }
  }

  public static void dump(IndentingPrintWriter indentingPrintWriter) {
    indentingPrintWriter.println("Current " + ToastManager.class.getSimpleName() + " state:");
    indentingPrintWriter.increaseIndent();
    List list = sToasts;
    synchronized (list) {
      indentingPrintWriter.println("sToasts (" + list.size() + "):");
      indentingPrintWriter.increaseIndent();
      Iterator it = list.iterator();
      while (it.hasNext()) {
        indentingPrintWriter.println((Toast) it.next());
      }
    }
    indentingPrintWriter.decreaseIndent();
    indentingPrintWriter.decreaseIndent();
  }
}
