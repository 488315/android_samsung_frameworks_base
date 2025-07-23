package com.android.systemui.navigationbar.views;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import com.android.internal.accessibility.dialog.AccessibilityButtonChooserActivity;
import com.android.systemui.R;
import com.android.systemui.navigationbar.views.buttons.KeyButtonView;
import com.android.systemui.settings.UserTrackerImpl;
import com.samsung.android.systemui.multistar.MultiStarManager;
import com.samsung.systemui.splugins.multistar.PluginMultiStar;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda6 implements View.OnLongClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NavigationBar f$0;

    public /* synthetic */ NavigationBar$$ExternalSyntheticLambda6(NavigationBar navigationBar, int i) {
        this.$r8$classId = i;
        this.f$0 = navigationBar;
    }

    @Override // android.view.View.OnLongClickListener
    public final boolean onLongClick(View view) {
        int i = this.$r8$classId;
        NavigationBar navigationBar = this.f$0;
        switch (i) {
            case 0:
                return navigationBar.onLongPressNavigationButtons(view, R.id.recent_apps);
            case 1:
                navigationBar.getClass();
                Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
                intent.addFlags(268468224);
                intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
                navigationBar.mContext.startActivityAsUser(intent, ((UserTrackerImpl) navigationBar.mUserTracker).getUserHandle());
                return true;
            case 2:
                return navigationBar.onHomeLongClick(view);
            case 3:
                return navigationBar.onLongPressNavigationButtons(view, R.id.home);
            default:
                navigationBar.getClass();
                Log.d("NavigationBar", "onLongPressRecents() - Recents button long clicked");
                ((MultiStarManager) MultiStarManager.sInstance.get()).getClass();
                PluginMultiStar pluginMultiStar = MultiStarManager.mPluginMultiStar;
                if (pluginMultiStar != null) {
                    MultiStarManager.sRecentKeyConsumed = pluginMultiStar.onLongPressRecents();
                }
                KeyButtonView keyButtonView = (KeyButtonView) view;
                keyButtonView.sendEvent(0, 128);
                keyButtonView.sendAccessibilityEvent(2);
                return true;
        }
    }
}
