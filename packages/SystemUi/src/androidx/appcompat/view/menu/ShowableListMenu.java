package androidx.appcompat.view.menu;

import androidx.appcompat.widget.DropDownListView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface ShowableListMenu {
    void dismiss();

    DropDownListView getListView();

    boolean isShowing();

    void show();
}
