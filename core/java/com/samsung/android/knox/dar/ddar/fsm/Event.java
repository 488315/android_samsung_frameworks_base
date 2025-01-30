package com.samsung.android.knox.dar.ddar.fsm;

import com.android.internal.content.NativeLibraryHelper;

/* loaded from: classes5.dex */
public enum Event {
  DDAR_WORKSPACE_CREATED,
  DEVICE_AUTH_SUCCESS,
  DEVICE_LOCKED,
  DATALOCK_TIMEOUT,
  DDAR_WORKSPACE_AUTH_SUCCESS,
  DDAR_WORKSPACE_REMOVED;

  /* renamed from: com.samsung.android.knox.dar.ddar.fsm.Event$1 */
  static /* synthetic */ class C51411 {
    static final /* synthetic */ int[] $SwitchMap$com$samsung$android$knox$dar$ddar$fsm$Event;

    static {
      int[] iArr = new int[Event.values().length];
      $SwitchMap$com$samsung$android$knox$dar$ddar$fsm$Event = iArr;
      try {
        iArr[Event.DDAR_WORKSPACE_CREATED.ordinal()] = 1;
      } catch (NoSuchFieldError e) {
      }
      try {
        $SwitchMap$com$samsung$android$knox$dar$ddar$fsm$Event[Event.DEVICE_LOCKED.ordinal()] = 2;
      } catch (NoSuchFieldError e2) {
      }
      try {
        $SwitchMap$com$samsung$android$knox$dar$ddar$fsm$Event[Event.DATALOCK_TIMEOUT.ordinal()] =
            3;
      } catch (NoSuchFieldError e3) {
      }
      try {
        $SwitchMap$com$samsung$android$knox$dar$ddar$fsm$Event[
                Event.DEVICE_AUTH_SUCCESS.ordinal()] =
            4;
      } catch (NoSuchFieldError e4) {
      }
      try {
        $SwitchMap$com$samsung$android$knox$dar$ddar$fsm$Event[
                Event.DDAR_WORKSPACE_AUTH_SUCCESS.ordinal()] =
            5;
      } catch (NoSuchFieldError e5) {
      }
      try {
        $SwitchMap$com$samsung$android$knox$dar$ddar$fsm$Event[
                Event.DDAR_WORKSPACE_REMOVED.ordinal()] =
            6;
      } catch (NoSuchFieldError e6) {
      }
    }
  }

  @Override // java.lang.Enum
  public String toString() {
    switch (C51411.$SwitchMap$com$samsung$android$knox$dar$ddar$fsm$Event[ordinal()]) {
      case 1:
        return "DDAR_WORKSPACE_CREATED";
      case 2:
        return "DEVICE_LOCKED";
      case 3:
        return "DATALOCK_TIMEOUT";
      case 4:
        return "DEVICE_AUTH_SUCCESS";
      case 5:
        return "DDAR_WORKSPACE_AUTH_SUCCESS";
      case 6:
        return "DDAR_WORKSPACE_REMOVED";
      default:
        return NativeLibraryHelper.CLEAR_ABI_OVERRIDE;
    }
  }
}
