package com.android.wm.shell.controlpanel.action;

import android.os.Handler;
import android.widget.GridLayout;
import com.android.wm.shell.controlpanel.action.ControlPanelAction;
import com.android.wm.shell.controlpanel.activity.FlexPanelActivity;
import com.android.wm.shell.controlpanel.activity.FlexPanelActivity$$ExternalSyntheticLambda1;
import com.android.wm.shell.controlpanel.activity.TouchPad;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import org.json.JSONException;

/* loaded from: classes3.dex */
public class TouchPadAction extends MenuActionType {
    private TouchPadAction() {
    }

    public static TouchPadAction createAction() {
        return new TouchPadAction();
    }

    @Override // com.android.wm.shell.controlpanel.action.MenuActionType
    public final void doControlAction(String str, FlexPanelActivity flexPanelActivity) throws JSONException {
        ControlPanelAction.Action action = ControlPanelAction.Action.TouchPad;
        if (action.getValue() != action.getValue()) {
            return;
        }
        if (flexPanelActivity.mTouchPad != null) {
            flexPanelActivity.mForceTouchPadRemoved = true;
            if (flexPanelActivity.getPreferences("MEDIA_PANEL")) {
                flexPanelActivity.setPreferences("MEDIA_TOUCH_PAD_ENABLED", false);
                flexPanelActivity.removeTouchPad(false);
            } else {
                flexPanelActivity.setPreferences("TOUCH_PAD_ENABLED", false);
                flexPanelActivity.removeTouchPad(false);
            }
            if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
                flexPanelActivity.updateStatusPreferences(false);
            }
            if (flexPanelActivity.mIsMediaPanel) {
                flexPanelActivity.mMediaView.startAnimation(flexPanelActivity.mFadeOut);
                new Handler().postDelayed(new FlexPanelActivity$$ExternalSyntheticLambda1(flexPanelActivity, 1), 100L);
                return;
            }
            return;
        }
        if (flexPanelActivity.mIsMediaPanel) {
            flexPanelActivity.mForceTouchPadRemoved = false;
            flexPanelActivity.mMediaView.startAnimation(flexPanelActivity.mFadeOut);
            new Handler().postDelayed(new FlexPanelActivity$$ExternalSyntheticLambda1(flexPanelActivity, 7), 100L);
            return;
        }
        if (!flexPanelActivity.mIsEditPanel) {
            flexPanelActivity.mForceTouchPadRemoved = false;
            flexPanelActivity.setPreferences("TOUCH_PAD_ENABLED", true);
            TouchPad touchPad = new TouchPad(flexPanelActivity, flexPanelActivity.mIsMediaPanel);
            flexPanelActivity.mTouchPad = touchPad;
            touchPad.showView();
            return;
        }
        if (flexPanelActivity.getPreferences("MEDIA_PANEL")) {
            if (flexPanelActivity.getPreferences("MEDIA_TOUCH_PAD_ENABLED")) {
                flexPanelActivity.mForceTouchPadRemoved = true;
                flexPanelActivity.setPreferences("MEDIA_TOUCH_PAD_ENABLED", false);
            } else {
                flexPanelActivity.mForceTouchPadRemoved = false;
                flexPanelActivity.setPreferences("MEDIA_TOUCH_PAD_ENABLED", true);
            }
        } else if (flexPanelActivity.getPreferences("TOUCH_PAD_ENABLED")) {
            flexPanelActivity.mForceTouchPadRemoved = true;
            flexPanelActivity.setPreferences("TOUCH_PAD_ENABLED", false);
        } else {
            flexPanelActivity.mForceTouchPadRemoved = false;
            flexPanelActivity.setPreferences("TOUCH_PAD_ENABLED", true);
        }
        GridLayout gridLayout = flexPanelActivity.mGridLayout;
        if (gridLayout != null) {
            gridLayout.removeAllViews();
            ArrayList arrayList = flexPanelActivity.mEditActions;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                flexPanelActivity.mGridLayout.addView(flexPanelActivity.createEditButton((ControlPanelAction.Action) obj));
            }
        }
        if (CoreRune.MW_SPLIT_FLEX_PANEL_MODE_SA_LOGGING) {
            flexPanelActivity.updateStatusPreferences(false);
        }
    }
}
