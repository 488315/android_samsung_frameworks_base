package com.android.systemui.controls.ui.fragment;

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
import android.widget.LinearLayout;
import androidx.appcompat.view.menu.SeslMenuItem;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentHostCallback;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.controls.controller.util.BadgeObserver;
import com.android.systemui.controls.controller.util.BadgeProviderImpl;
import com.android.systemui.controls.controller.util.BadgeProviderImpl$invalidate$1;
import com.android.systemui.controls.controller.util.BadgeSubject;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.ControlsListingControllerImpl;
import com.android.systemui.controls.management.CustomControlsFavoritingActivity;
import com.android.systemui.controls.management.CustomControlsProviderSelectorActivity;
import com.android.systemui.controls.management.adapter.MainControlAdapter;
import com.android.systemui.controls.management.model.MainComponentModel;
import com.android.systemui.controls.management.model.MainModel;
import com.android.systemui.controls.ui.ControlsSettingActivity;
import com.android.systemui.controls.ui.CustomControlsActivity;
import com.android.systemui.controls.ui.CustomControlsUiController;
import com.android.systemui.controls.ui.CustomControlsUiControllerImpl;
import com.android.systemui.controls.ui.SelectedItem;
import com.android.systemui.controls.ui.util.ControlsActivityStarter;
import com.android.systemui.controls.ui.util.ControlsActivityStarterImpl;
import com.android.systemui.controls.ui.util.LayoutUtil;
import com.android.systemui.controls.ui.util.SALogger;
import com.android.systemui.util.concurrency.ExecutorImpl;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MainFragment extends Fragment {
    public BadgeObserver badgeObserver;
    public final BadgeSubject badgeSubject;
    public MainControlAdapter controlAdapter;
    public final ControlsActivityStarter controlsActivityStarter;
    public final CustomControlsUiController controlsUiController;
    public final LayoutUtil layoutUtil;
    public RecyclerView listView;
    public final ControlsListingController listingController;
    public Context mContext;
    public View mView;
    public final SALogger saLogger;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public MainFragment(ControlsActivityStarter controlsActivityStarter, LayoutUtil layoutUtil, SALogger sALogger, BadgeSubject badgeSubject, ControlsListingController controlsListingController, CustomControlsUiController customControlsUiController) {
        this.controlsActivityStarter = controlsActivityStarter;
        this.layoutUtil = layoutUtil;
        this.saLogger = sALogger;
        this.badgeSubject = badgeSubject;
        this.listingController = controlsListingController;
        this.controlsUiController = customControlsUiController;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        Log.d("MainFragment", "onCreate");
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
        menuInflater.inflate(R.menu.controls_menu, menu);
        if (BasicRune.CONTROLS_BADGE) {
            SeslMenuItem seslMenuItem = (SeslMenuItem) menu.findItem(R.id.manage_apps);
            BadgeObserver badgeObserver = this.badgeObserver;
            if (badgeObserver == null) {
                badgeObserver = new BadgeObserver(seslMenuItem);
            }
            ((BadgeProviderImpl) this.badgeSubject).badgeObservers.add(badgeObserver);
            this.badgeObserver = badgeObserver;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Log.d("MainFragment", "onCreateView");
        View inflate = layoutInflater.inflate(R.layout.fragment_controls_main, viewGroup, false);
        this.mView = inflate;
        LinearLayout linearLayout = (LinearLayout) inflate.requireViewById(R.id.main_layout);
        this.layoutUtil.setLayoutWeightWidthPercentBasic(linearLayout, linearLayout.getContext().getResources().getFloat(R.integer.controls_basic_width_percentage));
        View view = this.mView;
        if (view == null) {
            view = null;
        }
        RecyclerView recyclerView = (RecyclerView) view.requireViewById(R.id.allControlsWithTemplates);
        int dimensionPixelSize = recyclerView.getResources().getDimensionPixelSize(R.dimen.controls_list_margin_horizontal) - recyclerView.getResources().getDimensionPixelSize(R.dimen.control_base_item_side_margin);
        this.layoutUtil.getClass();
        ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
        if (marginLayoutParams != null) {
            marginLayoutParams.setMargins(dimensionPixelSize, marginLayoutParams.topMargin, dimensionPixelSize, marginLayoutParams.bottomMargin);
            recyclerView.requestLayout();
        }
        recyclerView.seslSetGoToTopEnabled(true);
        this.listView = recyclerView;
        MainControlAdapter mainControlAdapter = this.controlAdapter;
        if (mainControlAdapter != null) {
            recyclerView.setAdapter(mainControlAdapter);
            this.controlAdapter = mainControlAdapter;
        }
        if (BasicRune.CONTROLS_SAMSUNG_ANALYTICS) {
            this.saLogger.sendScreenView(SALogger.Screen.MainScreen.INSTANCE);
        }
        View view2 = this.mView;
        if (view2 == null) {
            return null;
        }
        return view2;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onDestroy() {
        if (BasicRune.CONTROLS_BADGE) {
            BadgeObserver badgeObserver = this.badgeObserver;
            if (badgeObserver != null) {
                ((BadgeProviderImpl) this.badgeSubject).badgeObservers.remove(badgeObserver);
            }
            this.badgeObserver = null;
        }
        this.mCalled = true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v6, types: [T, android.content.Intent] */
    @Override // androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        Object obj;
        ComponentName componentName;
        Log.d("MainFragment", "onOptionsItemSelected item = " + menuItem);
        int itemId = menuItem.getItemId();
        if (itemId == R.id.manage_apps) {
            if (BasicRune.CONTROLS_SAMSUNG_ANALYTICS) {
                this.saLogger.sendEvent(new SALogger.Event.TapMenuManageApp(SALogger.Screen.MainScreen.INSTANCE));
            }
            ControlsActivityStarter controlsActivityStarter = this.controlsActivityStarter;
            Context context = this.mContext;
            ((ControlsActivityStarterImpl) controlsActivityStarter).startActivity(context != null ? context : null, CustomControlsProviderSelectorActivity.class);
            return true;
        }
        if (itemId != R.id.devices_to_show) {
            if (itemId != R.id.settings) {
                return false;
            }
            if (BasicRune.CONTROLS_SAMSUNG_ANALYTICS) {
                this.saLogger.sendEvent(new SALogger.Event.TapMenuSetting(SALogger.Screen.MainScreen.INSTANCE));
            }
            ControlsActivityStarter controlsActivityStarter2 = this.controlsActivityStarter;
            Context context2 = this.mContext;
            ((ControlsActivityStarterImpl) controlsActivityStarter2).startActivity(context2 != null ? context2 : null, ControlsSettingActivity.class);
            return true;
        }
        if (BasicRune.CONTROLS_SAMSUNG_ANALYTICS) {
            this.saLogger.sendEvent(new SALogger.Event.TapMenuDevicesToShow(SALogger.Screen.MainScreen.INSTANCE));
        }
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        MainControlAdapter mainControlAdapter = this.controlAdapter;
        if (mainControlAdapter != null) {
            Iterator it = mainControlAdapter.models.iterator();
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
                Context context3 = this.mContext;
                if (context3 == null) {
                    context3 = null;
                }
                ?? intent = new Intent(context3, (Class<?>) CustomControlsFavoritingActivity.class);
                intent.putExtra("extra_app_label", ((ControlsListingControllerImpl) this.listingController).getAppLabel(componentName));
                intent.putExtra("android.intent.extra.COMPONENT_NAME", componentName);
                intent.putExtra("extra_from_activity", Reflection.getOrCreateKotlinClass(CustomControlsActivity.class).getSimpleName());
                ref$ObjectRef.element = intent;
            }
        }
        T t = ref$ObjectRef.element;
        if (t == 0) {
            return true;
        }
        Intent intent2 = (Intent) t;
        FragmentHostCallback fragmentHostCallback = this.mHost;
        if (fragmentHostCallback != null) {
            Object obj2 = ContextCompat.sLock;
            fragmentHostCallback.mContext.startActivity(intent2, null);
            return true;
        }
        throw new IllegalStateException("Fragment " + this + " not attached to Activity");
    }

    @Override // androidx.fragment.app.Fragment
    public final void onPrepareOptionsMenu(Menu menu) {
        Log.d("MainFragment", "onPrepareOptionsMenu selectedItem = " + ((CustomControlsUiControllerImpl) this.controlsUiController).selectedItem);
        if (BasicRune.CONTROLS_BADGE) {
            BadgeProviderImpl badgeProviderImpl = (BadgeProviderImpl) this.badgeSubject;
            badgeProviderImpl.getClass();
            ((ExecutorImpl) badgeProviderImpl.uiExecutor).execute(new BadgeProviderImpl$invalidate$1(badgeProviderImpl));
        }
        menu.getItem(0).setVisible(!(((CustomControlsUiControllerImpl) this.controlsUiController).selectedItem instanceof SelectedItem.PanelItem));
    }
}
