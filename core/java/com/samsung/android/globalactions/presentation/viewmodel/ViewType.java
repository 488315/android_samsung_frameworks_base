package com.samsung.android.globalactions.presentation.viewmodel;

/* loaded from: classes5.dex */
public enum ViewType {
  TOP_VIEW(0),
  CENTER_ICON_1P_VIEW(1),
  CENTER_ICON_2P_VIEW(2),
  CENTER_ICON_3P_VIEW(3),
  CENTER_ICON_4P_VIEW(4),
  CENTER_ICON_5P_VIEW(5),
  CENTER_ICON_6P_VIEW(6),
  CENTER_ICON_7P_VIEW(7),
  CENTER_ICON_8P_VIEW(8),
  CENTER_ICON_CUSTOM_VIEW(9),
  BOTTOM_FORCE_RESTART_MSG_VIEW(10),
  KEY_SETTINGS_VIEW(11),
  BOTTOM_BTN_LIST_VIEW(12),
  COVER_NOTI_VIEW(13),
  BOTTOM_POPUP_VIEW(14);

  private final int value;

  ViewType(int value) {
    this.value = value;
  }

  public int getValue() {
    return this.value;
  }
}
