package com.android.server.desktopmode;

import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.DesktopModeUiConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/* loaded from: classes2.dex */
public class PendingUiCommands {
  public static final String TAG = "[DMS]" + PendingUiCommands.class.getSimpleName();
  public List mUiCommands = new ArrayList();

  public class UiCommand {
    public int mCommand;
    public Runnable mRunnable;
    public int mType;
    public int mWhere;

    public static boolean isAnyType(int i) {
      return i == 0 || i == 111;
    }

    public static boolean isAnyWhere(int i) {
      return i == -1 || i == 100 || i == 101;
    }

    public UiCommand(int i, int i2, int i3, Runnable runnable) {
      this.mCommand = i;
      this.mType = i2;
      this.mWhere = i3;
      this.mRunnable = runnable;
    }

    public boolean isRemovableWith(UiCommand uiCommand) {
      int i;
      int i2;
      if (this.mCommand == 900
          && uiCommand.mCommand == 901
          && ((i = this.mType) == (i2 = uiCommand.mType)
              || ((isSameTypeCategory(i, i2) && isAnyType(uiCommand.mType))
                  || hasSameNotificationId(this.mType, uiCommand.mType)))) {
        int i3 = this.mWhere;
        int i4 = uiCommand.mWhere;
        if (i3 == i4 || isAnyWhere(i4)) {
          return true;
        }
      }
      return false;
    }

    public static boolean isSameTypeCategory(int i, int i2) {
      return Math.abs(i - i2) < 100;
    }

    public static boolean hasSameNotificationId(int i, int i2) {
      if (!DesktopModeUiConstants.isNotificationType(i)
          || !DesktopModeUiConstants.isNotificationType(i2)) {
        return false;
      }
      int notificationId = DesktopModeUiConstants.getNotificationId(i2);
      int notificationId2 = DesktopModeUiConstants.getNotificationId(i);
      return notificationId2 == notificationId && notificationId2 != -1;
    }

    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null || getClass() != obj.getClass()) {
        return false;
      }
      UiCommand uiCommand = (UiCommand) obj;
      return this.mCommand == uiCommand.mCommand
          && this.mType == uiCommand.mType
          && this.mWhere == uiCommand.mWhere;
    }

    public int hashCode() {
      return Objects.hash(
          Integer.valueOf(this.mCommand),
          Integer.valueOf(this.mType),
          Integer.valueOf(this.mWhere));
    }

    public String toString() {
      return "UiCommand("
          + DesktopModeUiConstants.commandToString(this.mCommand)
          + ", "
          + DesktopModeUiConstants.typeToString(this.mType)
          + ", "
          + DesktopModeUiConstants.whereToString(this.mWhere)
          + ')';
    }
  }

  public void queue(int i, final int i2, int i3, Runnable runnable) {
    final UiCommand uiCommand = new UiCommand(i, i2, i3, runnable);
    if (i != 900) {
      if (i == 901) {
        removeCommandsIf(
            new Function() { // from class:
              // com.android.server.desktopmode.PendingUiCommands$$ExternalSyntheticLambda1
              @Override // java.util.function.Function
              public final Object apply(Object obj) {
                Boolean lambda$queue$1;
                lambda$queue$1 =
                    PendingUiCommands.lambda$queue$1(
                        PendingUiCommands.UiCommand.this, (PendingUiCommands.UiCommand) obj);
                return lambda$queue$1;
              }
            });
        if (!DesktopModeUiConstants.isNotificationType(i2) || runnable == null) {
          return;
        }
        this.mUiCommands.add(uiCommand);
        return;
      }
      return;
    }
    if (DesktopModeUiConstants.isNotificationType(i2)) {
      removeCommandsIf(
          new Function() { // from class:
            // com.android.server.desktopmode.PendingUiCommands$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
              Boolean lambda$queue$0;
              lambda$queue$0 =
                  PendingUiCommands.lambda$queue$0(i2, (PendingUiCommands.UiCommand) obj);
              return lambda$queue$0;
            }
          });
      if (runnable != null) {
        this.mUiCommands.add(uiCommand);
        return;
      }
      return;
    }
    if (this.mUiCommands.contains(uiCommand) || runnable == null) {
      return;
    }
    this.mUiCommands.add(uiCommand);
  }

  public static /* synthetic */ Boolean lambda$queue$0(int i, UiCommand uiCommand) {
    return Boolean.valueOf(UiCommand.hasSameNotificationId(i, uiCommand.mType));
  }

  public static /* synthetic */ Boolean lambda$queue$1(UiCommand uiCommand, UiCommand uiCommand2) {
    return Boolean.valueOf(uiCommand2.isRemovableWith(uiCommand));
  }

  public final void removeCommandsIf(Function function) {
    Iterator it = this.mUiCommands.iterator();
    while (it.hasNext()) {
      if (((Boolean) function.apply((UiCommand) it.next())).booleanValue()) {
        it.remove();
      }
    }
  }

  public void flushCommands() {
    Iterator it = new ArrayList(this.mUiCommands).iterator();
    while (it.hasNext()) {
      UiCommand uiCommand = (UiCommand) it.next();
      if (DesktopModeFeature.DEBUG) {
        Log.m7d(TAG, "Flushing " + uiCommand);
      }
      uiCommand.mRunnable.run();
    }
    clear();
  }

  public void clear() {
    this.mUiCommands.clear();
  }

  public boolean isEmpty() {
    return this.mUiCommands.isEmpty();
  }
}
