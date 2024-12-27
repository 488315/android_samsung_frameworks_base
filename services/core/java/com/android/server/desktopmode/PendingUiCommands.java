package com.android.server.desktopmode;

import com.android.server.desktopmode.PendingUiCommands;
import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.DesktopModeUiConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PendingUiCommands {
    public List mUiCommands;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UiCommand {
        public int mCommand;
        public Runnable mRunnable;
        public int mType;
        public int mWhere;

        public static boolean hasSameNotificationId(int i, int i2) {
            if (!DesktopModeUiConstants.isNotificationType(i) || !DesktopModeUiConstants.isNotificationType(i2)) {
                return false;
            }
            int notificationId = DesktopModeUiConstants.getNotificationId(i2);
            int notificationId2 = DesktopModeUiConstants.getNotificationId(i);
            return notificationId2 == notificationId && notificationId2 != -1;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || UiCommand.class != obj.getClass()) {
                return false;
            }
            UiCommand uiCommand = (UiCommand) obj;
            return this.mCommand == uiCommand.mCommand && this.mType == uiCommand.mType && this.mWhere == uiCommand.mWhere;
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(this.mCommand), Integer.valueOf(this.mType), Integer.valueOf(this.mWhere));
        }

        public final String toString() {
            return "UiCommand(" + DesktopModeUiConstants.commandToString(this.mCommand) + ", " + DesktopModeUiConstants.typeToString(this.mType) + ", " + DesktopModeUiConstants.whereToString(this.mWhere) + ')';
        }
    }

    public final void flushCommands() {
        Iterator it = new ArrayList(this.mUiCommands).iterator();
        while (it.hasNext()) {
            UiCommand uiCommand = (UiCommand) it.next();
            if (DesktopModeFeature.DEBUG) {
                Log.d("[DMS]PendingUiCommands", "Flushing " + uiCommand);
            }
            uiCommand.mRunnable.run();
        }
        ((ArrayList) this.mUiCommands).clear();
    }

    public final void queue(int i, final int i2, int i3, Runnable runnable) {
        final UiCommand uiCommand = new UiCommand();
        uiCommand.mCommand = i;
        uiCommand.mType = i2;
        uiCommand.mWhere = i3;
        uiCommand.mRunnable = runnable;
        if (i != 900) {
            if (i == 901) {
                removeCommandsIf(new Function() { // from class: com.android.server.desktopmode.PendingUiCommands$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        boolean z;
                        int i4;
                        int i5;
                        PendingUiCommands.UiCommand uiCommand2 = PendingUiCommands.UiCommand.this;
                        PendingUiCommands.UiCommand uiCommand3 = (PendingUiCommands.UiCommand) obj;
                        if (uiCommand3.mCommand == 900 && uiCommand2.mCommand == 901 && ((i4 = uiCommand3.mType) == (i5 = uiCommand2.mType) || ((Math.abs(i4 - i5) < 100 && (i5 == 0 || i5 == 111)) || PendingUiCommands.UiCommand.hasSameNotificationId(i4, i5)))) {
                            int i6 = uiCommand3.mWhere;
                            int i7 = uiCommand2.mWhere;
                            if (i6 == i7 || i7 == -1 || i7 == 100 || i7 == 101) {
                                z = true;
                                return Boolean.valueOf(z);
                            }
                        }
                        z = false;
                        return Boolean.valueOf(z);
                    }
                });
                if (!DesktopModeUiConstants.isNotificationType(i2) || runnable == null) {
                    return;
                }
                ((ArrayList) this.mUiCommands).add(uiCommand);
                return;
            }
            return;
        }
        if (DesktopModeUiConstants.isNotificationType(i2)) {
            removeCommandsIf(new Function() { // from class: com.android.server.desktopmode.PendingUiCommands$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Boolean.valueOf(PendingUiCommands.UiCommand.hasSameNotificationId(i2, ((PendingUiCommands.UiCommand) obj).mType));
                }
            });
            if (runnable != null) {
                ((ArrayList) this.mUiCommands).add(uiCommand);
                return;
            }
            return;
        }
        if (((ArrayList) this.mUiCommands).contains(uiCommand) || runnable == null) {
            return;
        }
        ((ArrayList) this.mUiCommands).add(uiCommand);
    }

    public final void removeCommandsIf(Function function) {
        Iterator it = ((ArrayList) this.mUiCommands).iterator();
        while (it.hasNext()) {
            if (((Boolean) function.apply((UiCommand) it.next())).booleanValue()) {
                it.remove();
            }
        }
    }
}
