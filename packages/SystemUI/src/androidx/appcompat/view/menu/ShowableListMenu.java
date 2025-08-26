package androidx.appcompat.view.menu;

import androidx.appcompat.widget.DropDownListView;

/* loaded from: classes.dex */
public interface ShowableListMenu {
    void dismiss();

    DropDownListView getListView();

    boolean isShowing();

    void show();
}
