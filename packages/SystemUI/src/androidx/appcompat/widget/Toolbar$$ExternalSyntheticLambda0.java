package androidx.appcompat.widget;

import android.view.MenuItem;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuHostHelper;
import androidx.fragment.app.FragmentManager;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class Toolbar$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Toolbar f$0;

    public /* synthetic */ Toolbar$$ExternalSyntheticLambda0(Toolbar toolbar, int i) {
        this.$r8$classId = i;
        this.f$0 = toolbar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Toolbar toolbar = this.f$0;
        switch (i) {
            case 0:
                Toolbar.ExpandedActionViewMenuPresenter expandedActionViewMenuPresenter = toolbar.mExpandedMenuPresenter;
                MenuItemImpl menuItemImpl = expandedActionViewMenuPresenter == null ? null : expandedActionViewMenuPresenter.mCurrentExpandedItem;
                if (menuItemImpl != null) {
                    menuItemImpl.collapseActionView();
                    break;
                }
                break;
            default:
                ArrayList arrayList = toolbar.mProvidedMenuItems;
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    toolbar.getMenu().removeItem(((MenuItem) obj).getItemId());
                }
                MenuBuilder menu = toolbar.getMenu();
                ArrayList arrayList2 = new ArrayList();
                MenuBuilder menu2 = toolbar.getMenu();
                for (int i3 = 0; i3 < menu2.size(); i3++) {
                    arrayList2.add(menu2.getItem(i3));
                }
                MenuHostHelper menuHostHelper = toolbar.mMenuHostHelper;
                SupportMenuInflater supportMenuInflater = new SupportMenuInflater(toolbar.getContext());
                Iterator it = menuHostHelper.mMenuProviders.iterator();
                while (it.hasNext()) {
                    FragmentManager.this.dispatchCreateOptionsMenu(menu, supportMenuInflater);
                }
                ArrayList arrayList3 = new ArrayList();
                MenuBuilder menu3 = toolbar.getMenu();
                for (int i4 = 0; i4 < menu3.size(); i4++) {
                    arrayList3.add(menu3.getItem(i4));
                }
                arrayList3.removeAll(arrayList2);
                toolbar.mProvidedMenuItems = arrayList3;
                toolbar.mMenuHostHelper.onPrepareMenu(menu);
                break;
        }
    }
}
