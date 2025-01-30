package com.android.p038wm.shell.controlpanel.action;

import android.content.Context;
import com.android.systemui.R;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ControlPanelAction {
    public static final ArrayList mActionType;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum Action {
        None(0),
        QuickPanel(1),
        ScreenCapture(2),
        BrightnessControl(3),
        VolumeControl(4),
        SplitScreen(5),
        FlexPanelSettings(6),
        EditPanel(7),
        DragCircle(8),
        TouchPad(9);

        private final int value;

        Action(int i) {
            this.value = i;
        }

        public final int getValue() {
            return this.value;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class GridViewItem {
        public final int mAct;
        public final int mIcon;
        public final int mLogging;
        public final int mName;

        public GridViewItem(int i, int i2, int i3, int i4) {
            this.mAct = i;
            this.mName = i2;
            this.mIcon = i3;
            this.mLogging = i4;
        }
    }

    static {
        ArrayList arrayList = new ArrayList();
        mActionType = arrayList;
        arrayList.add(new GridViewItem(Action.None.getValue(), R.string.empty, 0, R.string.empty));
        arrayList.add(new GridViewItem(Action.ScreenCapture.getValue(), R.string.flex_panel_screen_capture, R.drawable.ic_screen_capture, R.string.capture_sa_logging));
        arrayList.add(new GridViewItem(Action.QuickPanel.getValue(), R.string.flex_panel_notifications, R.drawable.ic_open_noti_panel, R.string.notification_sa_logging));
        arrayList.add(new GridViewItem(Action.BrightnessControl.getValue(), R.string.flex_panel_brightness, R.drawable.ic_brightness2, R.string.brightness_logging));
        arrayList.add(new GridViewItem(Action.VolumeControl.getValue(), R.string.flex_panel_volume, R.drawable.ic_ringtone_sound, R.string.volume_logging));
        arrayList.add(new GridViewItem(Action.SplitScreen.getValue(), R.string.flex_panel_split_screen_view, R.drawable.ic_split_view, R.string.splitscreen_logging));
        arrayList.add(new GridViewItem(Action.FlexPanelSettings.getValue(), R.string.flex_panel_settings, R.drawable.ic_flexpanel_settings, R.string.settings_logging));
        arrayList.add(new GridViewItem(Action.EditPanel.getValue(), R.string.flex_panel_toolbar_expanded, R.drawable.ic_more, R.string.editpanel_logging));
        arrayList.add(new GridViewItem(Action.DragCircle.getValue(), R.string.empty, 0, R.string.empty));
        arrayList.add(new GridViewItem(Action.TouchPad.getValue(), R.string.flex_panel_touch_pad, R.drawable.ic_cursor, R.string.touchpad_sa_logging));
    }

    public static String getLoggingID(int i, Context context) {
        for (int i2 = 0; i2 < 10; i2++) {
            ArrayList arrayList = mActionType;
            if (i == ((GridViewItem) arrayList.get(i2)).mAct) {
                return ((GridViewItem) arrayList.get(i2)).mLogging == 0 ? context.getString(R.string.empty) : context.getString(((GridViewItem) arrayList.get(i2)).mLogging);
            }
        }
        return context.getString(R.string.empty);
    }

    public static int getResourceIdByActionValue(int i) {
        for (int i2 = 0; i2 < 10; i2++) {
            ArrayList arrayList = mActionType;
            if (i == ((GridViewItem) arrayList.get(i2)).mAct) {
                return ((GridViewItem) arrayList.get(i2)).mIcon;
            }
        }
        return 0;
    }

    public static int getStringIdByActionValue(int i) {
        for (int i2 = 0; i2 < 10; i2++) {
            ArrayList arrayList = mActionType;
            if (i == ((GridViewItem) arrayList.get(i2)).mAct) {
                return ((GridViewItem) arrayList.get(i2)).mName;
            }
        }
        return 0;
    }
}
