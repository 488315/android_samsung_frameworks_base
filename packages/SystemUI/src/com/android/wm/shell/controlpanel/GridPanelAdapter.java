package com.android.wm.shell.controlpanel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.wm.shell.controlpanel.action.ControlPanelAction;
import com.android.wm.shell.controlpanel.activity.FlexPanelActivity;
import com.android.wm.shell.controlpanel.utils.ControlPanelUtils;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class GridPanelAdapter extends BaseAdapter {
    public final ArrayList items = new ArrayList();
    public final Context mContext;
    public final boolean mIsEditPanel;
    public FlexPanelActivity mOnClickListener;
    public FlexPanelActivity mOnDragListener;
    public FlexPanelActivity mOnLongClickListener;

    public GridPanelAdapter(Context context, boolean z) {
        this.mContext = context;
        this.mIsEditPanel = z;
    }

    @Override // android.widget.Adapter
    public final int getCount() {
        return this.items.size();
    }

    @Override // android.widget.Adapter
    public final Object getItem(int i) {
        return this.items.get(i);
    }

    @Override // android.widget.Adapter
    public final long getItemId(int i) {
        return i;
    }

    @Override // android.widget.Adapter
    public final View getView(int i, View view, ViewGroup viewGroup) {
        ControlPanelAction.Action action = (ControlPanelAction.Action) this.items.get(i);
        int value = action.getValue();
        int resourceIdByActionValue = ControlPanelAction.getResourceIdByActionValue(action.getValue());
        ListPopupWindow$$ExternalSyntheticOutline0.m(value, "makeButton(), action : ", "GridPanelAdapter");
        RelativeLayout relativeLayout = (RelativeLayout) View.inflate(this.mContext, R.layout.assistantmenu_menubutton, null);
        boolean zMakeGridButton = ControlPanelUtils.makeGridButton(this.mContext, relativeLayout, value, resourceIdByActionValue, true, this.mIsEditPanel);
        FlexPanelActivity flexPanelActivity = this.mOnClickListener;
        if (flexPanelActivity != null && zMakeGridButton) {
            relativeLayout.setOnClickListener(flexPanelActivity);
            FlexPanelActivity flexPanelActivity2 = this.mOnLongClickListener;
            if (flexPanelActivity2 != null && this.mOnDragListener != null) {
                relativeLayout.setOnLongClickListener(flexPanelActivity2);
                relativeLayout.setOnDragListener(this.mOnDragListener);
            }
        }
        return relativeLayout;
    }
}
