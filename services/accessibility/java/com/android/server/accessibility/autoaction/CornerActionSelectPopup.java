package com.android.server.accessibility.autoaction;

import android.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.p005os.IInstalld;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupMenu;
import java.util.List;

/* loaded from: classes.dex */
public class CornerActionSelectPopup {
  public final int[] POPUP_GRAVITY = {51, 53, 83, 85};
  public View mAnchorView;
  public Context mContext;
  public WindowManager.LayoutParams mParams;
  public PopupMenu mPopup;
  public final BroadcastReceiver mReceiver;
  public WindowManager mWindowManager;

  public CornerActionSelectPopup(Context context, int i) {
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class:
          // com.android.server.accessibility.autoaction.CornerActionSelectPopup.1
          @Override // android.content.BroadcastReceiver
          public void onReceive(Context context2, Intent intent) {
            if (!"android.intent.action.SCREEN_OFF".equals(intent.getAction())
                || CornerActionSelectPopup.this.mPopup == null) {
              return;
            }
            CornerActionSelectPopup.this.mPopup.dismiss();
          }
        };
    this.mReceiver = broadcastReceiver;
    this.mContext = context;
    makeView(i);
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.intent.action.SCREEN_OFF");
    this.mContext.registerReceiver(broadcastReceiver, intentFilter);
  }

  public final void makeView(int i) {
    this.mWindowManager = (WindowManager) this.mContext.getSystemService("window");
    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
    this.mParams = layoutParams;
    layoutParams.height = 0;
    layoutParams.width = 0;
    layoutParams.type = 2009;
    layoutParams.gravity = this.POPUP_GRAVITY[i];
    layoutParams.flags = 288;
    layoutParams.samsungFlags |= IInstalld.FLAG_CLEAR_APP_DATA_KEEP_ART_PROFILES;
    layoutParams.layoutInDisplayCutoutMode = 3;
    View view = new View(this.mContext);
    this.mAnchorView = view;
    this.mWindowManager.addView(view, this.mParams);
    this.mPopup =
        new PopupMenu(
            new ContextThemeWrapper(
                this.mContext,
                isNightMode() ? R.style.Theme.DeviceDefault : R.style.Theme.DeviceDefault.Light),
            this.mAnchorView);
  }

  public final boolean isNightMode() {
    return (this.mContext.getResources().getConfiguration().uiMode & 48) == 32;
  }

  public void setCornerActionList(List list) {
    for (int i = 0; i < list.size(); i++) {
      this.mPopup.getMenu().add(0, i, 0, (CharSequence) list.get(i));
    }
  }

  public void show() {
    new Handler(Looper.getMainLooper())
        .post(
            new Runnable() { // from class:
              // com.android.server.accessibility.autoaction.CornerActionSelectPopup.2
              @Override // java.lang.Runnable
              public void run() {
                CornerActionSelectPopup.this.mPopup.show();
              }
            });
  }

  public void dismiss() {
    this.mPopup.dismiss();
  }

  public void setOnDismissListener(PopupMenu.OnDismissListener onDismissListener) {
    this.mPopup.setOnDismissListener(onDismissListener);
  }

  public void setOnMenuItemClickListener(
      PopupMenu.OnMenuItemClickListener onMenuItemClickListener) {
    this.mPopup.setOnMenuItemClickListener(onMenuItemClickListener);
  }

  public void destroy() {
    this.mContext.unregisterReceiver(this.mReceiver);
    this.mWindowManager.removeView(this.mAnchorView);
    this.mContext = null;
  }
}
