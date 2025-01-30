package com.android.internal.widget.floatingtoolbar;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MenuItem;
import android.view.View;
import android.view.selectiontoolbar.SelectionToolbarManager;
import android.widget.PopupWindow;
import java.util.List;

/* loaded from: classes5.dex */
public interface FloatingToolbarPopup {
  void dismiss();

  Point getMovedPos();

  void hide();

  boolean isDiscardTouch();

  boolean isDismissed();

  boolean isHidden();

  boolean isMovingStarted();

  boolean isShowing();

  void onDetachFromWindow();

  void setIsMovingStarted(boolean z);

  boolean setOutsideTouchable(boolean z, PopupWindow.OnDismissListener onDismissListener);

  void setSuggestedWidth(int i);

  void setWidthChanged(boolean z);

  void show(
      List<MenuItem> list, MenuItem.OnMenuItemClickListener onMenuItemClickListener, Rect rect);

  static FloatingToolbarPopup createInstance(
      Context context, View parent, boolean isSemTypeFloating) {
    boolean enabled = SelectionToolbarManager.isRemoteSelectionToolbarEnabled(context);
    if (enabled) {
      return new RemoteFloatingToolbarPopup(context, parent);
    }
    return new LocalFloatingToolbarPopup(context, parent, isSemTypeFloating);
  }
}
