package com.android.internal.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.util.EventLog;
import android.view.ContextMenu;
import android.view.View;

/* loaded from: classes4.dex */
public class ContextMenuBuilder extends MenuBuilder implements ContextMenu {
    public ContextMenuBuilder(Context context) {
        super(context);
    }

    @Override // android.view.ContextMenu
    public ContextMenu setHeaderIcon(Drawable drawable) {
        return (ContextMenu) super.setHeaderIconInt(drawable);
    }

    @Override // android.view.ContextMenu
    public ContextMenu setHeaderIcon(int i) {
        return (ContextMenu) super.setHeaderIconInt(i);
    }

    @Override // android.view.ContextMenu
    public ContextMenu setHeaderTitle(CharSequence charSequence) {
        return (ContextMenu) super.setHeaderTitleInt(charSequence);
    }

    @Override // android.view.ContextMenu
    public ContextMenu setHeaderTitle(int i) {
        return (ContextMenu) super.setHeaderTitleInt(i);
    }

    @Override // android.view.ContextMenu
    public ContextMenu setHeaderView(View view) {
        return (ContextMenu) super.setHeaderViewInt(view);
    }

    public MenuDialogHelper showDialog(View view, IBinder iBinder) {
        if (view != null) {
            view.createContextMenu(this);
        }
        if (getVisibleItems().size() <= 0) {
            return null;
        }
        EventLog.writeEvent(50001, 1);
        MenuDialogHelper menuDialogHelper = new MenuDialogHelper(this);
        menuDialogHelper.show(iBinder);
        return menuDialogHelper;
    }

    public MenuPopupHelper showPopup(Context context, View view, float f, float f2) {
        if (view != null) {
            view.createContextMenu(this);
        }
        if (getVisibleItems().size() <= 0) {
            return null;
        }
        EventLog.writeEvent(50001, 1);
        view.getLocationOnScreen(new int[2]);
        MenuPopupHelper menuPopupHelper = new MenuPopupHelper(context, this, view, false, 16844033);
        menuPopupHelper.setIsContextMenuPopup(true);
        menuPopupHelper.show(Math.round(f), Math.round(f2));
        return menuPopupHelper;
    }
}
