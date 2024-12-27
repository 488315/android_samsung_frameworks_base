package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleRegistry;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.BaseActivity;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.ControlsControllerImpl$saveCurrentFavorites$1;
import com.android.systemui.controls.controller.Favorites;
import com.android.systemui.controls.controller.SecControlsController;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.controller.util.BadgeProvider;
import com.android.systemui.controls.controller.util.BadgeProviderImpl;
import com.android.systemui.controls.management.adapter.SecAppAdapter;
import com.android.systemui.controls.management.adapter.SecFavoritesRenderer;
import com.android.systemui.controls.panels.AuthorizedPanelsRepository;
import com.android.systemui.controls.ui.util.ControlsActivityStarter;
import com.android.systemui.controls.ui.util.ControlsActivityStarterImpl;
import com.android.systemui.controls.ui.util.LayoutUtil;
import com.android.systemui.controls.util.ControlsUtil;
import com.android.systemui.controls.util.SALogger;
import com.android.systemui.settings.UserTracker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class SecControlsProviderSelectorActivity extends BaseActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public SecAppAdapter appAdapter;
    public final AuthorizedPanelsRepository authorizedPanelsRepository;
    public final Executor backExecutor;
    public final BadgeProvider badgeProvider;
    public final BroadcastDispatcher broadcastDispatcher;
    public final ControlsActivityStarter controlsActivityStarter;
    public final ControlsController controlsController;
    public final ControlsUtil controlsUtil;
    public Button doneButton;
    public final Executor executor;
    public boolean isOOBE;
    public final LayoutUtil layoutUtil;
    public final ControlsListingController listingController;
    public final SALogger saLogger;
    public final SecControlsController secControlsController;

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

    public SecControlsProviderSelectorActivity(Executor executor, Executor executor2, ControlsListingController controlsListingController, ControlsController controlsController, UserTracker userTracker, SecControlsController secControlsController, ControlsActivityStarter controlsActivityStarter, BroadcastDispatcher broadcastDispatcher, ControlsUtil controlsUtil, LayoutUtil layoutUtil, SALogger sALogger, BadgeProvider badgeProvider, AuthorizedPanelsRepository authorizedPanelsRepository) {
        super(broadcastDispatcher, controlsController, userTracker, executor);
        this.executor = executor;
        this.backExecutor = executor2;
        this.listingController = controlsListingController;
        this.controlsController = controlsController;
        this.secControlsController = secControlsController;
        this.controlsActivityStarter = controlsActivityStarter;
        this.broadcastDispatcher = broadcastDispatcher;
        this.controlsUtil = controlsUtil;
        this.layoutUtil = layoutUtil;
        this.saLogger = sALogger;
        this.badgeProvider = badgeProvider;
        this.authorizedPanelsRepository = authorizedPanelsRepository;
    }

    public static final void access$updateButtonStatue(SecControlsProviderSelectorActivity secControlsProviderSelectorActivity) {
        Button button = secControlsProviderSelectorActivity.doneButton;
        if (button != null) {
            SecAppAdapter secAppAdapter = secControlsProviderSelectorActivity.appAdapter;
            if (secAppAdapter == null) {
                secAppAdapter = null;
            }
            button.setEnabled(secAppAdapter.getTotalFavoriteAndActiveAppCount() > 0);
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("updateButtonStatue donButton.isEnabled = ", "SecControlsProviderSelectorActivity", button.isEnabled());
        }
    }

    @Override // com.android.systemui.controls.BaseActivity
    public final BroadcastDispatcher getBroadcastDispatcher() {
        return this.broadcastDispatcher;
    }

    @Override // com.android.systemui.controls.BaseActivity
    public final String getTag() {
        return "SecControlsProviderSelectorActivity";
    }

    public final void handleDone() {
        Context applicationContext = getApplicationContext();
        this.controlsUtil.getClass();
        Prefs.putBoolean(applicationContext, "ControlsOOBEManageAppsCompleted", true);
        SecAppAdapter secAppAdapter = this.appAdapter;
        if (secAppAdapter == null) {
            secAppAdapter = null;
        }
        int totalFavoriteAndActiveAppCount = secAppAdapter.getTotalFavoriteAndActiveAppCount();
        SecAppAdapter secAppAdapter2 = this.appAdapter;
        if (secAppAdapter2 == null) {
            secAppAdapter2 = null;
        }
        this.saLogger.sendEvent(new SALogger.Event.IntroStart(totalFavoriteAndActiveAppCount, secAppAdapter2.listOfServices.size()));
        ((ControlsActivityStarterImpl) this.controlsActivityStarter).startSecControlsActivity(this, null);
        Unit unit = Unit.INSTANCE;
        finish();
    }

    @Override // com.android.systemui.controls.BaseActivity
    public final void onBackKeyPressed() {
        Button button;
        Log.d("SecControlsProviderSelectorActivity", "onBackKeyPressed");
        ((BadgeProviderImpl) this.badgeProvider).dismiss();
        if (this.isOOBE && (button = this.doneButton) != null && button.isEnabled()) {
            handleDone();
        } else {
            finish();
        }
    }

    @Override // com.android.systemui.controls.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        Log.d("SecControlsProviderSelectorActivity", "onCreate");
        super.onCreate(bundle);
        setContentView(R.layout.activity_controls_providers);
        setSupportActionBar((Toolbar) requireViewById(R.id.toolbar));
        ScrollView scrollView = (ScrollView) requireViewById(R.id.main_layout);
        Intrinsics.checkNotNull(scrollView);
        float f = scrollView.getContext().getResources().getFloat(R.integer.control_basic_width_percentage);
        LayoutUtil layoutUtil = this.layoutUtil;
        layoutUtil.setLayoutWeightWidthPercentBasic(layoutUtil.getWidthPercentBasic(f), scrollView);
        Context applicationContext = getApplicationContext();
        this.controlsUtil.getClass();
        boolean z = !Prefs.get(applicationContext).getBoolean("ControlsOOBEManageAppsCompleted", false);
        this.isOOBE = z;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onCreate isOOBE = ", "SecControlsProviderSelectorActivity", z);
        if (this.isOOBE) {
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                CharSequence text = getResources().getText(R.string.controls_title);
                supportActionBar.setTitle(text);
                setTitle(text);
            }
            TextView textView = (TextView) requireViewById(R.id.subtitle);
            textView.setText(BasicRune.CONTROLS_SAMSUNG_STYLE_TABLET ? textView.getResources().getText(R.string.controls_providers_sub_title_oobe_tablet) : textView.getResources().getText(R.string.controls_providers_sub_title_oobe));
            Button button = (Button) requireViewById(R.id.button_start);
            button.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.management.SecControlsProviderSelectorActivity$onCreate$5$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    SecControlsProviderSelectorActivity secControlsProviderSelectorActivity = SecControlsProviderSelectorActivity.this;
                    int i = SecControlsProviderSelectorActivity.$r8$clinit;
                    secControlsProviderSelectorActivity.handleDone();
                }
            });
            this.doneButton = button;
            ((LinearLayout) requireViewById(R.id.button_layout)).setVisibility(0);
        } else {
            ActionBar supportActionBar2 = getSupportActionBar();
            if (supportActionBar2 != null) {
                CharSequence text2 = getResources().getText(R.string.controls_menu_manage_apps);
                supportActionBar2.setTitle(text2);
                setTitle(text2);
                supportActionBar2.setDisplayHomeAsUpEnabled(true);
            }
            TextView textView2 = (TextView) requireViewById(R.id.subtitle);
            textView2.setText(textView2.getResources().getText(R.string.controls_providers_subtitle));
        }
        Executor executor = this.backExecutor;
        Executor executor2 = this.executor;
        LifecycleRegistry lifecycleRegistry = this.mLifecycleRegistry;
        LayoutInflater from = LayoutInflater.from(this);
        SecControlsProviderSelectorActivity$onCreate$9 secControlsProviderSelectorActivity$onCreate$9 = new SecControlsProviderSelectorActivity$onCreate$9(this);
        Resources resources = getResources();
        SecControlsProviderSelectorActivity$onCreate$10 secControlsProviderSelectorActivity$onCreate$10 = new SecControlsProviderSelectorActivity$onCreate$10(this.controlsController);
        SecControlsController secControlsController = this.secControlsController;
        SecAppAdapter secAppAdapter = new SecAppAdapter(executor, executor2, lifecycleRegistry, this.listingController, from, secControlsProviderSelectorActivity$onCreate$9, new SecFavoritesRenderer(resources, secControlsProviderSelectorActivity$onCreate$10, new SecControlsProviderSelectorActivity$onCreate$11(secControlsController), new SecControlsProviderSelectorActivity$onCreate$12(secControlsController), new SecControlsProviderSelectorActivity$onCreate$13(secControlsController)), this, this.controlsUtil, this.saLogger, this.badgeProvider, this.authorizedPanelsRepository, new Function1() { // from class: com.android.systemui.controls.management.SecControlsProviderSelectorActivity$onCreate$14
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                SecControlsProviderSelectorActivity.access$updateButtonStatue(SecControlsProviderSelectorActivity.this);
                return Unit.INSTANCE;
            }
        });
        secAppAdapter.mObservable.registerObserver(new RecyclerView.AdapterDataObserver() { // from class: com.android.systemui.controls.management.SecControlsProviderSelectorActivity$onCreate$15$1
            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public final void onChanged() {
                SecControlsProviderSelectorActivity.access$updateButtonStatue(SecControlsProviderSelectorActivity.this);
            }
        });
        this.appAdapter = secAppAdapter;
        RecyclerView recyclerView = (RecyclerView) requireViewById(R.id.controls_provider_list);
        SecAppAdapter secAppAdapter2 = this.appAdapter;
        if (secAppAdapter2 == null) {
            secAppAdapter2 = null;
        }
        recyclerView.setAdapter(secAppAdapter2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.semSetRoundedCorners(15);
        recyclerView.semSetRoundedCornerColor(15, recyclerView.getResources().getColor(R.color.sec_control_activity_background, getTheme()));
        this.saLogger.sendScreenView(this.isOOBE ? SALogger.Screen.Intro.INSTANCE : SALogger.Screen.ManageApps.INSTANCE);
    }

    @Override // com.android.systemui.controls.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        boolean z;
        Log.d("SecControlsProviderSelectorActivity", "onDestroy");
        ControlsControllerImpl controlsControllerImpl = (ControlsControllerImpl) this.secControlsController;
        if (controlsControllerImpl.confirmAvailability()) {
            controlsControllerImpl.executor.execute(new ControlsControllerImpl$saveCurrentFavorites$1(controlsControllerImpl));
        }
        SecAppAdapter secAppAdapter = this.appAdapter;
        if (secAppAdapter == null) {
            secAppAdapter = null;
        }
        int totalFavoriteAndActiveAppCount = secAppAdapter.getTotalFavoriteAndActiveAppCount();
        SecAppAdapter secAppAdapter2 = this.appAdapter;
        SALogger.StatusEvent.NumberOfApps numberOfApps = new SALogger.StatusEvent.NumberOfApps(totalFavoriteAndActiveAppCount, (secAppAdapter2 != null ? secAppAdapter2 : null).listOfServices.size());
        SALogger sALogger = this.saLogger;
        sALogger.sendStatusEvent(this, numberOfApps);
        ((ControlsControllerImpl) this.controlsController).getClass();
        Favorites.INSTANCE.getClass();
        List allStructures = Favorites.getAllStructures();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator it = ((ArrayList) allStructures).iterator();
        while (it.hasNext()) {
            Object next = it.next();
            ComponentName componentName = ((StructureInfo) next).componentName;
            Object obj = linkedHashMap.get(componentName);
            if (obj == null) {
                obj = new ArrayList();
                linkedHashMap.put(componentName, obj);
            }
            ((List) obj).add(next);
        }
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            ComponentName componentName2 = (ComponentName) entry.getKey();
            List list = (List) entry.getValue();
            String packageName = componentName2.getPackageName();
            SALogger.Companion companion = SALogger.Companion;
            List list2 = list;
            if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                Iterator it2 = list2.iterator();
                while (it2.hasNext()) {
                    if (((StructureInfo) it2.next()).active) {
                        z = true;
                        break;
                    }
                }
            }
            z = false;
            companion.getClass();
            arrayList.add(new SALogger.AppStatus(packageName, String.valueOf(Boolean.compare(z, false))));
        }
        sALogger.sendStatusEvent(this, new SALogger.StatusEvent.DeviceAppStatus(new SALogger.AppStatusList(arrayList)));
        super.onDestroy();
    }

    @Override // android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }
}
