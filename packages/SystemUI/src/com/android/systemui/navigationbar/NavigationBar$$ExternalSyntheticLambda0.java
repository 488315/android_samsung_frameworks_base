package com.android.systemui.navigationbar;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import com.android.internal.accessibility.dialog.AccessibilityButtonChooserActivity;
import com.android.systemui.R;
import com.android.systemui.navigationbar.buttons.KeyButtonView;
import com.android.systemui.settings.UserTrackerImpl;
import com.samsung.android.systemui.multistar.MultiStarManager;
import com.samsung.systemui.splugins.multistar.PluginMultiStar;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda0 implements View.OnLongClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NavigationBar f$0;

    public /* synthetic */ NavigationBar$$ExternalSyntheticLambda0(NavigationBar navigationBar, int i) {
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
                return navigationBar.onLongPressNavigationButtons(view, R.id.home);
            case 2:
                navigationBar.getClass();
                Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
                intent.addFlags(268468224);
                intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
                navigationBar.mContext.startActivityAsUser(intent, ((UserTrackerImpl) navigationBar.mUserTracker).getUserHandle());
                return true;
            case 3:
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
            default:
                return navigationBar.onHomeLongClick(view);
        }
    }
}
