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
import androidx.activity.ComponentActivity;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleRegistry;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.BasicRune;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.BaseActivity;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.CustomControlsController;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.controller.util.BadgeProvider;
import com.android.systemui.controls.controller.util.BadgeProviderImpl;
import com.android.systemui.controls.management.adapter.CustomAppAdapter;
import com.android.systemui.controls.management.adapter.CustomFavoritesRenderer;
import com.android.systemui.controls.ui.util.ControlsActivityStarter;
import com.android.systemui.controls.ui.util.ControlsActivityStarterImpl;
import com.android.systemui.controls.ui.util.ControlsUtil;
import com.android.systemui.controls.ui.util.LayoutUtil;
import com.android.systemui.controls.ui.util.SALogger;
import com.android.systemui.controls.panels.AuthorizedPanelsRepository;
import com.android.systemui.controls.panels.AuthorizedPanelsRepositoryImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.concurrency.ExecutorImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.EmptySet;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CustomControlsProviderSelectorActivity extends BaseActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final String TAG;
    public CustomAppAdapter appAdapter;
    public final AuthorizedPanelsRepository authorizedPanelsRepository;
    public final Executor backExecutor;
    public final BadgeProvider badgeProvider;
    public final BroadcastDispatcher broadcastDispatcher;
    public final ControlsActivityStarter controlsActivityStarter;
    public final ControlsController controlsController;
    public final ControlsUtil controlsUtil;
    public final CustomControlsController customControlsController;
    public Button doneButton;
    public final Executor executor;
    public boolean isOOBE;
    public final LayoutUtil layoutUtil;
    public final ControlsListingController listingController;
    public final SALogger saLogger;

    public CustomControlsProviderSelectorActivity(Executor executor, Executor executor2, ControlsListingController controlsListingController, ControlsController controlsController, UserTracker userTracker, AuthorizedPanelsRepository authorizedPanelsRepository, CustomControlsController customControlsController, ControlsActivityStarter controlsActivityStarter, BroadcastDispatcher broadcastDispatcher, ControlsUtil controlsUtil, LayoutUtil layoutUtil, SALogger sALogger, BadgeProvider badgeProvider) {
        super(broadcastDispatcher, controlsController, userTracker, executor);
        this.executor = executor;
        this.backExecutor = executor2;
        this.listingController = controlsListingController;
        this.controlsController = controlsController;
        this.authorizedPanelsRepository = authorizedPanelsRepository;
        this.customControlsController = customControlsController;
        this.controlsActivityStarter = controlsActivityStarter;
        this.broadcastDispatcher = broadcastDispatcher;
        this.controlsUtil = controlsUtil;
        this.layoutUtil = layoutUtil;
        this.saLogger = sALogger;
        this.badgeProvider = badgeProvider;
        this.TAG = "CustomControlsProviderSelectorActivity";
    }

    public static final void access$updateButtonStatue(CustomControlsProviderSelectorActivity customControlsProviderSelectorActivity) {
        Button button = customControlsProviderSelectorActivity.doneButton;
        if (button != null) {
            CustomAppAdapter customAppAdapter = customControlsProviderSelectorActivity.appAdapter;
            if (customAppAdapter == null) {
                customAppAdapter = null;
            }
            button.setEnabled(customAppAdapter.getTotalFavoriteAndActiveAppCount() > 0);
            Log.d(customControlsProviderSelectorActivity.TAG, AbstractC0866xb1ce8deb.m86m("updateButtonStatue donButton.isEnabled = ", button.isEnabled()));
        }
    }

    @Override // com.android.systemui.controls.BaseActivity
    public final BroadcastDispatcher getBroadcastDispatcher() {
        return this.broadcastDispatcher;
    }

    @Override // com.android.systemui.controls.BaseActivity
    public final String getTAG() {
        return this.TAG;
    }

    public final void handleDone() {
        Context applicationContext = getApplicationContext();
        this.controlsUtil.getClass();
        Prefs.putBoolean(applicationContext, "ControlsOOBEManageAppsCompleted", true);
        if (BasicRune.CONTROLS_SAMSUNG_ANALYTICS) {
            CustomAppAdapter customAppAdapter = this.appAdapter;
            if (customAppAdapter == null) {
                customAppAdapter = null;
            }
            int totalFavoriteAndActiveAppCount = customAppAdapter.getTotalFavoriteAndActiveAppCount();
            CustomAppAdapter customAppAdapter2 = this.appAdapter;
            this.saLogger.sendEvent(new SALogger.Event.IntroStart(totalFavoriteAndActiveAppCount, (customAppAdapter2 != null ? customAppAdapter2 : null).getItemCount()));
        }
        ((ControlsActivityStarterImpl) this.controlsActivityStarter).startCustomControlsActivity(this);
        Unit unit = Unit.INSTANCE;
        finish();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void onBackPressed() {
        Button button;
        Log.d(this.TAG, "onBackPressed");
        if (BasicRune.CONTROLS_BADGE) {
            ((BadgeProviderImpl) this.badgeProvider).dismiss();
        }
        if (this.isOOBE && (button = this.doneButton) != null && button.isEnabled()) {
            handleDone();
        } else {
            finish();
        }
    }

    @Override // com.android.systemui.controls.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        String str = this.TAG;
        Log.d(str, "onCreate");
        super.onCreate(bundle);
        setContentView(R.layout.activity_controls_providers);
        setSupportActionBar((Toolbar) requireViewById(R.id.toolbar));
        ScrollView scrollView = (ScrollView) requireViewById(R.id.main_layout);
        this.layoutUtil.setLayoutWeightWidthPercentBasic(scrollView, scrollView.getContext().getResources().getFloat(R.integer.controls_basic_width_percentage));
        Context applicationContext = getApplicationContext();
        this.controlsUtil.getClass();
        boolean z = !Prefs.getBoolean(applicationContext, "ControlsOOBEManageAppsCompleted", false);
        this.isOOBE = z;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onCreate isOOBE = ", z, str);
        if (this.isOOBE) {
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                CharSequence text = getResources().getText(R.string.controls_title);
                supportActionBar.setTitle(text);
                setTitle(text);
            }
            TextView textView = (TextView) requireViewById(R.id.subtitle);
            textView.setText(textView.getResources().getText(R.string.controls_custom_providers_subtitle_OOBE));
            Button button = (Button) requireViewById(R.id.button_start);
            button.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.management.CustomControlsProviderSelectorActivity$onCreate$5$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    CustomControlsProviderSelectorActivity customControlsProviderSelectorActivity = CustomControlsProviderSelectorActivity.this;
                    int i = CustomControlsProviderSelectorActivity.$r8$clinit;
                    customControlsProviderSelectorActivity.handleDone();
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
            textView2.setText(textView2.getResources().getText(R.string.controls_custom_providers_subtitle));
        }
        Executor executor = this.backExecutor;
        Executor executor2 = this.executor;
        LifecycleRegistry lifecycleRegistry = ((ComponentActivity) this).mLifecycleRegistry;
        ControlsListingController controlsListingController = this.listingController;
        LayoutInflater from = LayoutInflater.from(this);
        CustomControlsProviderSelectorActivity$onCreate$9 customControlsProviderSelectorActivity$onCreate$9 = new CustomControlsProviderSelectorActivity$onCreate$9(this);
        Resources resources = getResources();
        CustomControlsProviderSelectorActivity$onCreate$10 customControlsProviderSelectorActivity$onCreate$10 = new CustomControlsProviderSelectorActivity$onCreate$10(this.controlsController);
        CustomControlsController customControlsController = this.customControlsController;
        CustomFavoritesRenderer customFavoritesRenderer = new CustomFavoritesRenderer(resources, customControlsProviderSelectorActivity$onCreate$10, new CustomControlsProviderSelectorActivity$onCreate$11(customControlsController), new CustomControlsProviderSelectorActivity$onCreate$12(customControlsController), new CustomControlsProviderSelectorActivity$onCreate$13(customControlsController));
        ControlsUtil controlsUtil = this.controlsUtil;
        SALogger sALogger = this.saLogger;
        BadgeProvider badgeProvider = this.badgeProvider;
        Resources resources2 = getResources();
        Set<String> stringSet = ((AuthorizedPanelsRepositoryImpl) this.authorizedPanelsRepository).instantiateSharedPrefs().getStringSet("authorized_panels", EmptySet.INSTANCE);
        Intrinsics.checkNotNull(stringSet);
        CustomAppAdapter customAppAdapter = new CustomAppAdapter(executor, executor2, lifecycleRegistry, controlsListingController, from, customControlsProviderSelectorActivity$onCreate$9, customFavoritesRenderer, this, controlsUtil, sALogger, badgeProvider, resources2, stringSet, new Function1() { // from class: com.android.systemui.controls.management.CustomControlsProviderSelectorActivity$onCreate$14
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                CustomControlsProviderSelectorActivity.access$updateButtonStatue(CustomControlsProviderSelectorActivity.this);
                return Unit.INSTANCE;
            }
        });
        customAppAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() { // from class: com.android.systemui.controls.management.CustomControlsProviderSelectorActivity$onCreate$15$1
            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public final void onChanged() {
                CustomControlsProviderSelectorActivity.access$updateButtonStatue(CustomControlsProviderSelectorActivity.this);
            }
        });
        this.appAdapter = customAppAdapter;
        RecyclerView recyclerView = (RecyclerView) requireViewById(R.id.controls_provider_list);
        CustomAppAdapter customAppAdapter2 = this.appAdapter;
        if (customAppAdapter2 == null) {
            customAppAdapter2 = null;
        }
        recyclerView.setAdapter(customAppAdapter2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.semSetRoundedCorners(15);
        recyclerView.semSetRoundedCornerColor(15, recyclerView.getResources().getColor(R.color.control_activity_background, getTheme()));
        if (BasicRune.CONTROLS_SAMSUNG_ANALYTICS) {
            this.saLogger.sendScreenView(this.isOOBE ? SALogger.Screen.Intro.INSTANCE : SALogger.Screen.ManageApps.INSTANCE);
        }
    }

    @Override // com.android.systemui.controls.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        boolean z;
        Log.d(this.TAG, "onDestroy");
        final ControlsControllerImpl controlsControllerImpl = (ControlsControllerImpl) this.customControlsController;
        if (controlsControllerImpl.confirmAvailability()) {
            ((ExecutorImpl) controlsControllerImpl.executor).execute(new Runnable() { // from class: com.android.systemui.controls.controller.ControlsControllerImpl$saveCurrentFavorites$1
                @Override // java.lang.Runnable
                public final void run() {
                    ControlsFavoritePersistenceWrapper controlsFavoritePersistenceWrapper = ControlsControllerImpl.this.persistenceWrapper;
                    Favorites.INSTANCE.getClass();
                    controlsFavoritePersistenceWrapper.storeFavorites(Favorites.getAllStructures());
                }
            });
        }
        if (BasicRune.CONTROLS_SAMSUNG_ANALYTICS) {
            CustomAppAdapter customAppAdapter = this.appAdapter;
            if (customAppAdapter == null) {
                customAppAdapter = null;
            }
            int totalFavoriteAndActiveAppCount = customAppAdapter.getTotalFavoriteAndActiveAppCount();
            CustomAppAdapter customAppAdapter2 = this.appAdapter;
            SALogger.StatusEvent.NumberOfApps numberOfApps = new SALogger.StatusEvent.NumberOfApps(totalFavoriteAndActiveAppCount, (customAppAdapter2 != null ? customAppAdapter2 : null).getItemCount());
            SALogger sALogger = this.saLogger;
            sALogger.sendStatusEvent(this, numberOfApps);
            List favorites = ((ControlsControllerImpl) this.controlsController).getFavorites();
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            Iterator it = ((ArrayList) favorites).iterator();
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
                if (!(list instanceof Collection) || !list.isEmpty()) {
                    Iterator it2 = list.iterator();
                    while (it2.hasNext()) {
                        if (((StructureInfo) it2.next()).customStructureInfo.active) {
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
        }
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
