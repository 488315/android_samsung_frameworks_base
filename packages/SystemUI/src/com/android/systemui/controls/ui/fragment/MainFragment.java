package com.android.systemui.controls.ui.fragment;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.view.menu.SeslMenuItem;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentHostCallback;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.android.systemui.controls.controller.util.BadgeObserver;
import com.android.systemui.controls.controller.util.BadgeProviderImpl;
import com.android.systemui.controls.controller.util.BadgeProviderImpl$invalidate$1;
import com.android.systemui.controls.controller.util.BadgeSubject;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.ControlsListingControllerImpl;
import com.android.systemui.controls.management.SecControlsFavoritingActivity;
import com.android.systemui.controls.management.SecControlsProviderSelectorActivity;
import com.android.systemui.controls.management.adapter.StatefulControlAdapter;
import com.android.systemui.controls.management.model.MainComponentModel;
import com.android.systemui.controls.management.model.MainModel;
import com.android.systemui.controls.ui.ControlsSettingActivity;
import com.android.systemui.controls.ui.SecControlsActivity;
import com.android.systemui.controls.ui.SecControlsUiController;
import com.android.systemui.controls.ui.SecControlsUiControllerImpl;
import com.android.systemui.controls.ui.SecControlsUiControllerImpl$controlsPanelCallback$1;
import com.android.systemui.controls.ui.SelectedItem;
import com.android.systemui.controls.ui.util.ControlsActivityStarter;
import com.android.systemui.controls.ui.util.ControlsActivityStarterImpl;
import com.android.systemui.controls.ui.util.LayoutUtil;
import com.android.systemui.controls.util.SALogger;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.jvm.internal.Reflection;

public final class MainFragment extends Fragment {
    public BadgeObserver badgeObserver;
    public final BadgeSubject badgeSubject;
    public Context context;
    public StatefulControlAdapter controlAdapter;
    public final ControlsActivityStarter controlsActivityStarter;
    public SecControlsUiControllerImpl$controlsPanelCallback$1 controlsPanelUpdatedCallback;
    public final SecControlsUiController controlsUiController;
    public final LayoutUtil layoutUtil;
    public RecyclerView listView;
    public final ControlsListingController listingController;
    public PendingIntent panelPendingIntent;
    public FrameLayout panelView;
    public final SALogger saLogger;
    public View view;

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

    public MainFragment(ControlsActivityStarter controlsActivityStarter, LayoutUtil layoutUtil, SALogger sALogger, BadgeSubject badgeSubject, ControlsListingController controlsListingController, SecControlsUiController secControlsUiController) {
        this.controlsActivityStarter = controlsActivityStarter;
        this.layoutUtil = layoutUtil;
        this.saLogger = sALogger;
        this.badgeSubject = badgeSubject;
        this.listingController = controlsListingController;
        this.controlsUiController = secControlsUiController;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        Log.d("MainFragment", "onCreate");
        super.onCreate(bundle);
        this.context = requireContext();
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
        menuInflater.inflate(R.menu.controls_setting_menu, menu);
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
        Log.d("MainFragment", "onCreateView");
        View inflate = layoutInflater.inflate(R.layout.fragment_controls_main, viewGroup, false);
        this.view = inflate;
        LinearLayout linearLayout = (LinearLayout) inflate.requireViewById(R.id.main_layout);
        LayoutUtil layoutUtil = this.layoutUtil;
        Intrinsics.checkNotNull(linearLayout);
        layoutUtil.setLayoutWeightWidthPercentBasic(this.layoutUtil.getWidthPercentBasic(linearLayout.getContext().getResources().getFloat(R.integer.control_basic_width_percentage)), linearLayout);
        View view = this.view;
        if (view == null) {
            view = null;
        }
        RecyclerView recyclerView = (RecyclerView) view.requireViewById(R.id.allControlsWithTemplates);
        int dimensionPixelSize = recyclerView.getResources().getDimensionPixelSize(R.dimen.control_list_horizontal_margin) - recyclerView.getResources().getDimensionPixelSize(R.dimen.control_base_item_side_margin);
        this.layoutUtil.getClass();
        ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
        if (marginLayoutParams != null) {
            marginLayoutParams.setMargins(dimensionPixelSize, marginLayoutParams.topMargin, dimensionPixelSize, marginLayoutParams.bottomMargin);
            recyclerView.requestLayout();
        }
        recyclerView.seslSetGoToTopEnabled();
        this.listView = recyclerView;
        StatefulControlAdapter statefulControlAdapter = this.controlAdapter;
        if (statefulControlAdapter != null) {
            recyclerView.setAdapter(statefulControlAdapter);
            this.controlAdapter = statefulControlAdapter;
        }
        View view2 = this.view;
        if (view2 == null) {
            view2 = null;
        }
        FrameLayout frameLayout = (FrameLayout) view2.requireViewById(R.id.panel_view);
        this.panelView = frameLayout;
        if (frameLayout != null) {
            if (this.panelPendingIntent != null) {
                frameLayout.setVisibility(0);
                SecControlsUiControllerImpl$controlsPanelCallback$1 secControlsUiControllerImpl$controlsPanelCallback$1 = this.controlsPanelUpdatedCallback;
                if (secControlsUiControllerImpl$controlsPanelCallback$1 != null) {
                    PendingIntent pendingIntent = this.panelPendingIntent;
                    Intrinsics.checkNotNull(pendingIntent);
                    secControlsUiControllerImpl$controlsPanelCallback$1.onPanelUpdated(frameLayout, pendingIntent);
                }
            } else {
                frameLayout.setVisibility(8);
            }
        }
        this.saLogger.sendScreenView(SALogger.Screen.MainScreen.INSTANCE);
        View view3 = this.view;
        if (view3 == null) {
            return null;
        }
        return view3;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onDestroy() {
        BadgeObserver badgeObserver = this.badgeObserver;
        if (badgeObserver != null) {
            ((BadgeProviderImpl) this.badgeSubject).badgeObservers.remove(badgeObserver);
        }
        this.badgeObserver = null;
        this.mCalled = true;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onDestroyView() {
        this.mCalled = true;
        Log.d("MainFragment", "onDestroyView");
        this.panelView = null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v5, types: [T, android.content.Intent] */
    @Override // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        Object obj;
        ComponentName componentName;
        Log.d("MainFragment", "onOptionsItemSelected item = " + menuItem);
        int itemId = menuItem.getItemId();
        if (itemId == R.id.manage_apps) {
            this.saLogger.sendEvent(new SALogger.Event.TapMenuManageApp(SALogger.Screen.MainScreen.INSTANCE));
            ControlsActivityStarter controlsActivityStarter = this.controlsActivityStarter;
            Context context = this.context;
            ((ControlsActivityStarterImpl) controlsActivityStarter).startActivity(context != null ? context : null, SecControlsProviderSelectorActivity.class);
            return true;
        }
        if (itemId != R.id.devices_to_show) {
            if (itemId != R.id.settings) {
                return false;
            }
            this.saLogger.sendEvent(new SALogger.Event.TapMenuSetting(SALogger.Screen.MainScreen.INSTANCE));
            ControlsActivityStarter controlsActivityStarter2 = this.controlsActivityStarter;
            Context context2 = this.context;
            ((ControlsActivityStarterImpl) controlsActivityStarter2).startActivity(context2 != null ? context2 : null, ControlsSettingActivity.class);
            return true;
        }
        this.saLogger.sendEvent(new SALogger.Event.TapMenuDevicesToShow(SALogger.Screen.MainScreen.INSTANCE));
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        StatefulControlAdapter statefulControlAdapter = this.controlAdapter;
        if (statefulControlAdapter != null) {
            Iterator it = statefulControlAdapter.models.iterator();
            while (true) {
                if (!it.hasNext()) {
                    obj = null;
                    break;
                }
                obj = it.next();
                if (((MainModel) obj) instanceof MainComponentModel) {
                    break;
                }
            }
            MainModel mainModel = (MainModel) obj;
            if (mainModel != null && (componentName = ((MainComponentModel) mainModel).selected) != null) {
                ?? intent = new Intent(statefulControlAdapter.context, (Class<?>) SecControlsFavoritingActivity.class);
                intent.putExtra("extra_app_label", ((ControlsListingControllerImpl) this.listingController).getAppLabel(componentName));
                intent.putExtra("android.intent.extra.COMPONENT_NAME", componentName);
                intent.putExtra("extra_from_activity", Reflection.getOrCreateKotlinClass(SecControlsActivity.class).getSimpleName());
                ref$ObjectRef.element = intent;
            }
        }
        Intent intent2 = (Intent) ref$ObjectRef.element;
        if (intent2 == null) {
            return true;
        }
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback != null) {
            fragmentHostCallback.mContext.startActivity(intent2, null);
            return true;
        }
        throw new IllegalStateException("Fragment " + this + " not attached to Activity");
    }

    @Override // androidx.fragment.app.Fragment
    public final void onPrepareOptionsMenu(Menu menu) {
        Log.d("MainFragment", "onPrepareOptionsMenu selectedItem = " + ((SecControlsUiControllerImpl) this.controlsUiController).selectedItem);
        BadgeProviderImpl badgeProviderImpl = (BadgeProviderImpl) this.badgeSubject;
        badgeProviderImpl.getClass();
        badgeProviderImpl.uiExecutor.execute(new BadgeProviderImpl$invalidate$1(badgeProviderImpl));
        menu.getItem(0).setVisible(!(((SecControlsUiControllerImpl) this.controlsUiController).selectedItem instanceof SelectedItem.PanelItem));
    }
}
