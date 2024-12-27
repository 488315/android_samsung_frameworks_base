package com.android.systemui.controls.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.appcompat.view.menu.SeslMenuItem;
import androidx.fragment.app.Fragment;
import com.android.systemui.R;
import com.android.systemui.controls.controller.util.BadgeObserver;
import com.android.systemui.controls.controller.util.BadgeProviderImpl;
import com.android.systemui.controls.controller.util.BadgeProviderImpl$invalidate$1;
import com.android.systemui.controls.controller.util.BadgeSubject;
import com.android.systemui.controls.management.SecControlsProviderSelectorActivity;
import com.android.systemui.controls.ui.ControlsSettingActivity;
import com.android.systemui.controls.ui.util.ControlsActivityStarter;
import com.android.systemui.controls.ui.util.ControlsActivityStarterImpl;
import com.android.systemui.controls.util.ControlsUtil;
import com.android.systemui.controls.util.SALogger;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NoFavoriteFragment extends Fragment {
    public BadgeObserver badgeObserver;
    public final BadgeSubject badgeSubject;
    public final ControlsActivityStarter controlsActivityStarter;
    public Context mContext;
    public View mView;
    public final SALogger saLogger;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public NoFavoriteFragment(ControlsActivityStarter controlsActivityStarter, SALogger sALogger, BadgeSubject badgeSubject) {
        this.controlsActivityStarter = controlsActivityStarter;
        this.saLogger = sALogger;
        this.badgeSubject = badgeSubject;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        Log.d("NoFavoriteFragment", "onCreate");
        super.onCreate(bundle);
        this.mContext = requireContext();
        if (!this.mHasMenu) {
            this.mHasMenu = true;
            if (!isAdded() || isHidden()) {
                return;
            }
            this.mHost.onSupportInvalidateOptionsMenu();
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        Log.d("NoFavoriteFragment", "onCreateOptionsMenu");
        menuInflater.inflate(R.menu.controls_setting_menu, menu);
        menu.removeItem(R.id.devices_to_show);
        SeslMenuItem seslMenuItem = (SeslMenuItem) menu.findItem(R.id.manage_apps);
        BadgeObserver badgeObserver = this.badgeObserver;
        if (badgeObserver == null) {
            badgeObserver = new BadgeObserver(seslMenuItem);
        }
        ((BadgeProviderImpl) this.badgeSubject).badgeObservers.add(badgeObserver);
        this.badgeObserver = badgeObserver;
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.d("NoFavoriteFragment", "onCreateView");
        View inflate = layoutInflater.inflate(R.layout.fragment_controls_no_favorite, viewGroup, false);
        this.mView = inflate;
        final Button button = (Button) inflate.requireViewById(R.id.manage_control_btn);
        button.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.ui.fragment.NoFavoriteFragment$onCreateView$1$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                Log.d("NoFavoriteFragment", "startProviderSelectorActivity");
                NoFavoriteFragment.this.saLogger.sendEvent(SALogger.Event.AddDevices.INSTANCE);
                ((ControlsActivityStarterImpl) NoFavoriteFragment.this.controlsActivityStarter).startActivity(button.getContext(), SecControlsProviderSelectorActivity.class);
            }
        });
        ControlsUtil.Companion.updateFontSize$default(ControlsUtil.Companion, button, R.dimen.control_no_favorite_manage_button_text_size);
        this.saLogger.sendScreenView(SALogger.Screen.NoDeviceSelected.INSTANCE);
        View view = this.mView;
        if (view == null) {
            return null;
        }
        return view;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onDestroy() {
        Log.d("NoFavoriteFragment", "onDestroy");
        BadgeObserver badgeObserver = this.badgeObserver;
        if (badgeObserver != null) {
            ((BadgeProviderImpl) this.badgeSubject).badgeObservers.remove(badgeObserver);
            this.badgeObserver = null;
        }
        this.mCalled = true;
    }

    @Override // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.manage_apps) {
            this.saLogger.sendEvent(new SALogger.Event.TapMenuManageApp(SALogger.Screen.NoDeviceSelected.INSTANCE));
            ControlsActivityStarter controlsActivityStarter = this.controlsActivityStarter;
            Context context = this.mContext;
            ((ControlsActivityStarterImpl) controlsActivityStarter).startActivity(context != null ? context : null, SecControlsProviderSelectorActivity.class);
            return true;
        }
        if (itemId != R.id.settings) {
            return false;
        }
        this.saLogger.sendEvent(new SALogger.Event.TapMenuSetting(SALogger.Screen.NoDeviceSelected.INSTANCE));
        ControlsActivityStarter controlsActivityStarter2 = this.controlsActivityStarter;
        Context context2 = this.mContext;
        ((ControlsActivityStarterImpl) controlsActivityStarter2).startActivity(context2 != null ? context2 : null, ControlsSettingActivity.class);
        return true;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onPrepareOptionsMenu(Menu menu) {
        BadgeProviderImpl badgeProviderImpl = (BadgeProviderImpl) this.badgeSubject;
        badgeProviderImpl.getClass();
        badgeProviderImpl.uiExecutor.execute(new BadgeProviderImpl$invalidate$1(badgeProviderImpl));
    }
}
