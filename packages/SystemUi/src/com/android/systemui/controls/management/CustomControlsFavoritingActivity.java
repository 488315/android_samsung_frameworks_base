package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.service.controls.Control;
import android.service.controls.ControlsProviderInfo;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.activity.ComponentActivity;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.BaseActivity;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.controller.ComponentInfo;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.ControlsControllerKt$createLoadDataObject$1;
import com.android.systemui.controls.controller.CustomControlsController;
import com.android.systemui.controls.controller.Favorites;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.management.adapter.CustomStructureAdapter;
import com.android.systemui.controls.management.model.AllStructureModel;
import com.android.systemui.controls.management.model.ControlInfoForStructure;
import com.android.systemui.controls.management.model.StructureModel;
import com.android.systemui.controls.ui.CustomControlsActivity;
import com.android.systemui.controls.ui.util.AUIFacade;
import com.android.systemui.controls.ui.util.ControlExtension;
import com.android.systemui.controls.ui.util.ControlsProviderInfoExtension;
import com.android.systemui.controls.ui.util.ControlsUtil;
import com.android.systemui.controls.ui.util.LayoutUtil;
import com.android.systemui.controls.ui.util.SALogger;
import com.android.systemui.controls.util.ControlsRuneWrapper;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CustomControlsFavoritingActivity extends BaseActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final String TAG;
    public final CustomControlsFavoritingActivity activity;
    public CharSequence appName;
    public final AUIFacade auiFacade;
    public final BroadcastDispatcher broadcastDispatcher;
    public Runnable cancelLoadRunnable;
    public ComponentName component;
    public final ControlsController controller;
    public LinearLayout controlsListLayout;
    public Map controlsMap;
    public final ControlsRuneWrapper controlsRuneWrapper;
    public final ControlsUtil controlsUtil;
    public List currentFavorites;
    public List currentOrder;
    public Parcelable currentPosition;
    public CustomStructureAdapter customAdapter;
    public final CustomControlsController customController;
    public AllStructureModel customModel;
    public final Executor executor;
    public final C1181xe80b48e7 favoriteControlChangeMainCallback;
    public final Set initialFavoriteIds;
    public boolean isAutoRemove;
    public boolean isFromMainActivity;
    public boolean isLoadingFinished;
    public boolean isPagerLoaded;
    public final LayoutUtil layoutUtil;
    public RecyclerView listView;
    public ControlsController.LoadData loadData;
    public LinearLayout noItemsLayout;
    public List requestOrder;
    public AlertDialog retryDialog;
    public final SALogger saLogger;
    public final UserTracker userTracker;

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

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.controls.management.CustomControlsFavoritingActivity$favoriteControlChangeMainCallback$1] */
    public CustomControlsFavoritingActivity(Executor executor, ControlsController controlsController, CustomControlsController customControlsController, CustomControlsController customControlsController2, BroadcastDispatcher broadcastDispatcher, LayoutUtil layoutUtil, ControlsUtil controlsUtil, ControlsRuneWrapper controlsRuneWrapper, UserTracker userTracker, AUIFacade aUIFacade, SALogger sALogger) {
        super(broadcastDispatcher, controlsController, userTracker, executor);
        this.executor = executor;
        this.controller = controlsController;
        this.customController = customControlsController;
        this.broadcastDispatcher = broadcastDispatcher;
        this.layoutUtil = layoutUtil;
        this.controlsUtil = controlsUtil;
        this.controlsRuneWrapper = controlsRuneWrapper;
        this.userTracker = userTracker;
        this.auiFacade = aUIFacade;
        this.saLogger = sALogger;
        this.TAG = "CustomControlsFavoritingActivity";
        this.activity = this;
        EmptyList emptyList = EmptyList.INSTANCE;
        this.currentOrder = emptyList;
        this.requestOrder = emptyList;
        this.initialFavoriteIds = new LinkedHashSet();
        this.favoriteControlChangeMainCallback = new StructureModel.StructureModelCallback() { // from class: com.android.systemui.controls.management.CustomControlsFavoritingActivity$favoriteControlChangeMainCallback$1
            @Override // com.android.systemui.controls.management.model.StructureModel.StructureModelCallback
            public final void onControlInfoChange(ControlInfoForStructure controlInfoForStructure) {
            }
        };
    }

    public final void changeDataAndShow() {
        this.isLoadingFinished = true;
        this.requestOrder = EmptyList.INSTANCE;
        CustomStructureAdapter customStructureAdapter = this.customAdapter;
        if (customStructureAdapter == null) {
            customStructureAdapter = null;
        }
        AllStructureModel allStructureModel = this.customModel;
        if (allStructureModel == null) {
            allStructureModel = null;
        }
        customStructureAdapter.model = allStructureModel;
        customStructureAdapter.notifyDataSetChanged();
        AllStructureModel allStructureModel2 = this.customModel;
        if (allStructureModel2 == null) {
            allStructureModel2 = null;
        }
        allStructureModel2.getClass();
        if (BasicRune.CONTROLS_STRUCTURE_ORDERING) {
            invalidateOptionsMenu();
        }
        Map map = this.controlsMap;
        Intrinsics.checkNotNull(map);
        if (map.isEmpty()) {
            LinearLayout linearLayout = this.noItemsLayout;
            if (linearLayout == null) {
                linearLayout = null;
            }
            linearLayout.setVisibility(0);
            LinearLayout linearLayout2 = this.controlsListLayout;
            (linearLayout2 != null ? linearLayout2 : null).setVisibility(8);
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

    public final void loadControls() {
        ComponentName componentName = this.component;
        if (componentName != null) {
            ((ControlsControllerImpl) this.customController).loadForComponent(componentName, new Consumer() { // from class: com.android.systemui.controls.management.CustomControlsFavoritingActivity$loadControls$1$1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ControlsController.LoadData loadData = (ControlsController.LoadData) obj;
                    if (CustomControlsFavoritingActivity.this.initialFavoriteIds.isEmpty()) {
                        CustomControlsFavoritingActivity.this.initialFavoriteIds.addAll(((ControlsControllerKt$createLoadDataObject$1) loadData).favoritesIds);
                    }
                    CustomControlsFavoritingActivity customControlsFavoritingActivity = CustomControlsFavoritingActivity.this;
                    ControlsController.LoadData loadData2 = customControlsFavoritingActivity.loadData;
                    if (loadData2 != null) {
                        loadData = loadData2;
                    }
                    customControlsFavoritingActivity.loadData = loadData;
                    customControlsFavoritingActivity.loadForComponent(loadData, false);
                }
            }, new Consumer() { // from class: com.android.systemui.controls.management.CustomControlsFavoritingActivity$loadControls$1$2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    CustomControlsFavoritingActivity.this.cancelLoadRunnable = (Runnable) obj;
                }
            }, new Consumer() { // from class: com.android.systemui.controls.management.CustomControlsFavoritingActivity$loadControls$1$3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ControlsProviderInfo controlsProviderInfo = (ControlsProviderInfo) obj;
                    CustomControlsFavoritingActivity customControlsFavoritingActivity = CustomControlsFavoritingActivity.this;
                    boolean z = BasicRune.CONTROLS_AUTO_REMOVE;
                    if (z) {
                        customControlsFavoritingActivity.isAutoRemove = controlsProviderInfo.getAutoRemove();
                    }
                    String str = customControlsFavoritingActivity.TAG;
                    ControlsProviderInfoExtension.Companion.getClass();
                    if (z) {
                        boolean autoRemove = controlsProviderInfo.getAutoRemove();
                        StringBuilder sb = new StringBuilder("autoRemove:");
                        sb.append(autoRemove);
                        sb.append("|");
                    }
                    Intent intent = controlsProviderInfo.getAppIntent().getIntent();
                    StringBuilder sb2 = new StringBuilder("intent:");
                    sb2.append(intent);
                    sb2.append("|");
                    String resPackage = controlsProviderInfo.getIcon().getResPackage();
                    StringBuilder sb3 = new StringBuilder("icon:");
                    sb3.append(resPackage);
                    sb3.append("|");
                }
            });
        }
    }

    public final void loadForComponent(ControlsController.LoadData loadData, boolean z) {
        List<ControlStatus> list;
        List list2;
        ControlsControllerKt$createLoadDataObject$1 controlsControllerKt$createLoadDataObject$1 = (ControlsControllerKt$createLoadDataObject$1) loadData;
        List<ControlStatus> list3 = controlsControllerKt$createLoadDataObject$1.allControls;
        List list4 = this.currentFavorites;
        if (list4 != null) {
            for (ControlStatus controlStatus : list3) {
                controlStatus.favorite = list4.contains(controlStatus.getControlId());
            }
        }
        List list5 = this.currentFavorites;
        if (list5 == null) {
            list5 = controlsControllerKt$createLoadDataObject$1.favoritesIds;
        }
        List list6 = list5;
        boolean z2 = BasicRune.CONTROLS_LOADING_DEVICES;
        Executor executor = this.executor;
        if (z2 && controlsControllerKt$createLoadDataObject$1.errorOnLoad) {
            executor.execute(new Runnable() { // from class: com.android.systemui.controls.management.CustomControlsFavoritingActivity$showErrorDialog$1
                @Override // java.lang.Runnable
                public final void run() {
                    CustomControlsFavoritingActivity customControlsFavoritingActivity = CustomControlsFavoritingActivity.this;
                    String str = customControlsFavoritingActivity.TAG;
                    customControlsFavoritingActivity.activity.isDestroyed();
                    if (CustomControlsFavoritingActivity.this.activity.isDestroyed()) {
                        return;
                    }
                    final CustomControlsFavoritingActivity customControlsFavoritingActivity2 = CustomControlsFavoritingActivity.this;
                    String string = customControlsFavoritingActivity2.getResources().getString(R.string.controls_retry_dialog_loading_timeout, customControlsFavoritingActivity2.appName);
                    AlertDialog.Builder builder = new AlertDialog.Builder(customControlsFavoritingActivity2, 2132018373);
                    AlertController.AlertParams alertParams = builder.f0P;
                    alertParams.mTitle = alertParams.mContext.getText(R.string.controls_retry_dialog_title);
                    alertParams.mMessage = string;
                    builder.setPositiveButton(R.string.controls_retry_dialog_retry, new DialogInterface.OnClickListener() { // from class: com.android.systemui.controls.management.CustomControlsFavoritingActivity$createErrorDialog$builder$1$1
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            CustomControlsFavoritingActivity customControlsFavoritingActivity3 = CustomControlsFavoritingActivity.this;
                            int i2 = CustomControlsFavoritingActivity.$r8$clinit;
                            customControlsFavoritingActivity3.loadControls();
                            dialogInterface.dismiss();
                        }
                    });
                    builder.setNegativeButton(R.string.controls_custom_dialog_cancel, new DialogInterface.OnClickListener() { // from class: com.android.systemui.controls.management.CustomControlsFavoritingActivity$createErrorDialog$builder$1$2
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            CustomControlsFavoritingActivity customControlsFavoritingActivity3 = CustomControlsFavoritingActivity.this;
                            int i2 = CustomControlsFavoritingActivity.$r8$clinit;
                            customControlsFavoritingActivity3.onBackPressed();
                            dialogInterface.cancel();
                        }
                    });
                    alertParams.mOnKeyListener = new DialogInterface.OnKeyListener() { // from class: com.android.systemui.controls.management.CustomControlsFavoritingActivity$createErrorDialog$builder$1$3
                        @Override // android.content.DialogInterface.OnKeyListener
                        public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                            if (i != 4) {
                                return false;
                            }
                            CustomControlsFavoritingActivity customControlsFavoritingActivity3 = CustomControlsFavoritingActivity.this;
                            int i2 = CustomControlsFavoritingActivity.$r8$clinit;
                            customControlsFavoritingActivity3.onBackPressed();
                            dialogInterface.cancel();
                            return true;
                        }
                    };
                    AlertDialog create = builder.create();
                    create.setCanceledOnTouchOutside(false);
                    customControlsFavoritingActivity2.retryDialog = create;
                    AlertDialog alertDialog = CustomControlsFavoritingActivity.this.retryDialog;
                    if (alertDialog != null) {
                        alertDialog.show();
                    }
                }
            });
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (Object obj : list3) {
            if (((ControlStatus) obj).removed) {
                arrayList.add(obj);
            }
        }
        arrayList.toString();
        if (BasicRune.CONTROLS_AUTO_REMOVE && this.isAutoRemove) {
            ArrayList arrayList2 = new ArrayList();
            for (Object obj2 : list3) {
                if (!((ControlStatus) obj2).removed) {
                    arrayList2.add(obj2);
                }
            }
            list = arrayList2;
        } else {
            list = list3;
        }
        for (ControlStatus controlStatus2 : list) {
            ControlExtension.Companion companion = ControlExtension.Companion;
            Control control = controlStatus2.control;
            companion.getClass();
            ControlExtension.Companion.dump(control);
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj3 : list) {
            CharSequence structure = ((ControlStatus) obj3).control.getStructure();
            if (structure == null) {
                structure = "";
            }
            Object obj4 = linkedHashMap.get(structure);
            if (obj4 == null) {
                obj4 = new ArrayList();
                linkedHashMap.put(structure, obj4);
            }
            ((List) obj4).add(obj3);
        }
        this.controlsMap = linkedHashMap;
        this.customModel = new AllStructureModel(getResources(), list, list6, this.favoriteControlChangeMainCallback, false);
        if (BasicRune.CONTROLS_STRUCTURE_ORDERING) {
            if (!this.requestOrder.isEmpty()) {
                list2 = this.requestOrder;
            } else {
                ComponentName componentName = this.component;
                Intrinsics.checkNotNull(componentName);
                ((ControlsControllerImpl) this.controller).getClass();
                Favorites.INSTANCE.getClass();
                List structuresForComponent = Favorites.getStructuresForComponent(componentName);
                ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(structuresForComponent, 10));
                Iterator it = structuresForComponent.iterator();
                while (it.hasNext()) {
                    arrayList3.add(((StructureInfo) it.next()).structure);
                }
                list2 = arrayList3;
            }
            AllStructureModel allStructureModel = this.customModel;
            if (allStructureModel == null) {
                allStructureModel = null;
            }
            executor.execute(new CustomControlsFavoritingActivity$refreshStructureOrdering$2(this, allStructureModel, list2, new Function0() { // from class: com.android.systemui.controls.management.CustomControlsFavoritingActivity$refreshStructureOrdering$1
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return Unit.INSTANCE;
                }
            }));
        }
        if (z) {
            changeDataAndShow();
        } else {
            executor.execute(new Runnable() { // from class: com.android.systemui.controls.management.CustomControlsFavoritingActivity$loadForComponent$4
                @Override // java.lang.Runnable
                public final void run() {
                    CustomControlsFavoritingActivity customControlsFavoritingActivity = CustomControlsFavoritingActivity.this;
                    int i = CustomControlsFavoritingActivity.$r8$clinit;
                    customControlsFavoritingActivity.changeDataAndShow();
                }
            });
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1000 && i2 == -1 && intent != null) {
            ArrayList<CharSequence> charSequenceArrayListExtra = intent.getCharSequenceArrayListExtra("OrderList");
            if (!this.isLoadingFinished) {
                this.requestOrder = charSequenceArrayListExtra;
                return;
            }
            AllStructureModel allStructureModel = this.customModel;
            if (allStructureModel == null) {
                allStructureModel = null;
            }
            this.executor.execute(new CustomControlsFavoritingActivity$refreshStructureOrdering$2(this, allStructureModel, charSequenceArrayListExtra, new CustomControlsFavoritingActivity$onActivityResult$1$1(this)));
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void onBackPressed() {
        updateFavorites();
        finish();
    }

    @Override // com.android.systemui.controls.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        CharSequence charSequenceExtra = getIntent().getCharSequenceExtra("extra_app_label");
        this.appName = charSequenceExtra;
        setTitle(charSequenceExtra);
        CharSequence charSequenceExtra2 = getIntent().getCharSequenceExtra("extra_from_activity");
        boolean equals = charSequenceExtra2 != null ? charSequenceExtra2.equals(Reflection.getOrCreateKotlinClass(CustomControlsActivity.class).getSimpleName()) : false;
        this.isFromMainActivity = equals;
        Log.d(this.TAG, "onCreate isFromMainActivity = " + equals + ", class = " + ((Object) charSequenceExtra2));
        this.component = (ComponentName) getIntent().getParcelableExtra("android.intent.extra.COMPONENT_NAME");
        if (bundle != null) {
            this.currentFavorites = bundle.getStringArrayList("current_favorites");
            this.currentPosition = bundle.getParcelable("structure_position");
        }
        setContentView(R.layout.controls_custom_management);
        setSupportActionBar((Toolbar) requireViewById(R.id.toolbar));
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(this.appName);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        FrameLayout frameLayout = (FrameLayout) requireViewById(R.id.main_layout);
        this.layoutUtil.setLayoutWeightWidthPercentBasic(frameLayout, frameLayout.getContext().getResources().getFloat(R.integer.controls_basic_width_percentage));
        this.controlsListLayout = (LinearLayout) requireViewById(R.id.controls_list_layout);
        this.noItemsLayout = (LinearLayout) requireViewById(R.id.no_items_layout);
        LinearLayout linearLayout = this.controlsListLayout;
        if (linearLayout == null) {
            linearLayout = null;
        }
        linearLayout.setVisibility(0);
        LinearLayout linearLayout2 = this.noItemsLayout;
        if (linearLayout2 == null) {
            linearLayout2 = null;
        }
        linearLayout2.setVisibility(8);
        ViewStub viewStub = (ViewStub) requireViewById(R.id.stub);
        viewStub.setLayoutResource(R.layout.controls_structure_page);
        viewStub.inflate();
        this.customAdapter = new CustomStructureAdapter(this.layoutUtil, this.controlsUtil, this.controlsRuneWrapper, this.auiFacade, ((UserTrackerImpl) this.userTracker).getUserId(), this.currentPosition != null ? new Consumer() { // from class: com.android.systemui.controls.management.CustomControlsFavoritingActivity$bindViews$layoutCompletedCallback$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((RecyclerView.LayoutManager) obj).onRestoreInstanceState(CustomControlsFavoritingActivity.this.currentPosition);
            }
        } : null);
        RecyclerView recyclerView = (RecyclerView) requireViewById(R.id.listAll);
        CustomStructureAdapter customStructureAdapter = this.customAdapter;
        if (customStructureAdapter == null) {
            customStructureAdapter = null;
        }
        recyclerView.setAdapter(customStructureAdapter);
        recyclerView.seslSetGoToTopEnabled(true);
        this.listView = recyclerView;
        ComponentActivity.NonConfigurationInstances nonConfigurationInstances = (ComponentActivity.NonConfigurationInstances) getLastNonConfigurationInstance();
        Object obj = nonConfigurationInstances != null ? nonConfigurationInstances.custom : null;
        SaveWrapper saveWrapper = obj instanceof SaveWrapper ? (SaveWrapper) obj : null;
        if (saveWrapper != null) {
            ControlsController.LoadData loadData = saveWrapper.data;
            this.loadData = loadData;
            this.isPagerLoaded = loadData != null;
            if (loadData != null) {
                loadForComponent(loadData, true);
            }
        }
        if (BasicRune.CONTROLS_SAMSUNG_ANALYTICS) {
            this.saLogger.sendScreenView(SALogger.Screen.ChooseDevices.INSTANCE);
        }
    }

    @Override // android.app.Activity
    public final boolean onCreateOptionsMenu(Menu menu) {
        Map map;
        Set keySet;
        if (BasicRune.CONTROLS_STRUCTURE_ORDERING && this.isLoadingFinished && (map = this.controlsMap) != null && (keySet = ((LinkedHashMap) map).keySet()) != null && keySet.size() > 1) {
            getMenuInflater().inflate(R.menu.controls_edit_control_menu, menu);
        }
        return true;
    }

    @Override // com.android.systemui.controls.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        int i;
        int i2;
        int i3;
        int i4;
        AlertDialog alertDialog;
        Runnable runnable = this.cancelLoadRunnable;
        if (runnable != null) {
            runnable.run();
        }
        if (BasicRune.CONTROLS_LOADING_DEVICES && (alertDialog = this.retryDialog) != null) {
            alertDialog.dismiss();
        }
        if (BasicRune.CONTROLS_SAMSUNG_ANALYTICS && this.isLoadingFinished) {
            Map map = this.controlsMap;
            int i5 = 0;
            if (map != null) {
                LinkedHashMap linkedHashMap = (LinkedHashMap) map;
                int size = linkedHashMap.keySet().size() + 0;
                int i6 = 0;
                int i7 = 0;
                for (List list : linkedHashMap.values()) {
                    ArrayList arrayList = new ArrayList();
                    for (Object obj : list) {
                        if (((ControlStatus) obj).favorite) {
                            arrayList.add(obj);
                        }
                    }
                    i5 += arrayList.size();
                    i6 += list.size();
                    LinkedHashMap linkedHashMap2 = new LinkedHashMap();
                    for (Object obj2 : list) {
                        CharSequence zone = ((ControlStatus) obj2).control.getZone();
                        if (zone == null) {
                            zone = "";
                        }
                        Object obj3 = linkedHashMap2.get(zone);
                        if (obj3 == null) {
                            obj3 = new ArrayList();
                            linkedHashMap2.put(zone, obj3);
                        }
                        ((List) obj3).add(obj2);
                    }
                    i7 += linkedHashMap2.keySet().size();
                }
                i = i5;
                i3 = size;
                i2 = i6;
                i4 = i7;
            } else {
                i = 0;
                i2 = 0;
                i3 = 0;
                i4 = 0;
            }
            ComponentName componentName = this.component;
            Intrinsics.checkNotNull(componentName);
            this.saLogger.sendEvent(new SALogger.Event.LeftChooseDevices(componentName.getPackageName(), i, i2, i3, i4));
        }
        super.onDestroy();
    }

    @Override // android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        ComponentName componentName;
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            onBackPressed();
            return true;
        }
        if (itemId != R.id.reorder_menu) {
            return super.onOptionsItemSelected(menuItem);
        }
        if (!BasicRune.CONTROLS_STRUCTURE_ORDERING || (componentName = this.component) == null) {
            return true;
        }
        Intent intent = new Intent(this, (Class<?>) ControlsReorderActivity.class);
        intent.putExtra("android.intent.extra.COMPONENT_NAME", componentName);
        intent.putExtra("extra_structure_lists", (ArrayList) this.currentOrder);
        if (BasicRune.CONTROLS_SAMSUNG_ANALYTICS) {
            this.saLogger.sendEvent(SALogger.Event.Reorder.INSTANCE);
        }
        startActivityForResult(intent, 1000);
        return true;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        if (this.isPagerLoaded) {
            return;
        }
        if (BasicRune.CONTROLS_LOADING_DEVICES) {
            Resources resources = getResources();
            EmptyList emptyList = EmptyList.INSTANCE;
            AllStructureModel allStructureModel = new AllStructureModel(resources, emptyList, emptyList, this.favoriteControlChangeMainCallback, true);
            CustomStructureAdapter customStructureAdapter = this.customAdapter;
            if (customStructureAdapter == null) {
                customStructureAdapter = null;
            }
            customStructureAdapter.model = allStructureModel;
            customStructureAdapter.notifyDataSetChanged();
            loadControls();
        } else {
            loadControls();
        }
        this.isPagerLoaded = true;
    }

    @Override // androidx.activity.ComponentActivity
    public final Object onRetainCustomNonConfigurationInstance() {
        if (this.isLoadingFinished) {
            return new SaveWrapper(this.loadData);
        }
        return null;
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        if (this.isLoadingFinished) {
            AllStructureModel allStructureModel = this.customModel;
            if (allStructureModel == null) {
                allStructureModel = null;
            }
            List favorites = allStructureModel.getFavorites();
            ArrayList<String> arrayList = new ArrayList<>(CollectionsKt__IterablesKt.collectionSizeOrDefault(favorites, 10));
            Iterator it = ((ArrayList) favorites).iterator();
            while (it.hasNext()) {
                arrayList.add(((ControlInfo) it.next()).controlId);
            }
            bundle.putStringArrayList("current_favorites", arrayList);
            RecyclerView recyclerView = this.listView;
            if (recyclerView == null) {
                recyclerView = null;
            }
            RecyclerView.LayoutManager layoutManager$1 = recyclerView.getLayoutManager$1();
            bundle.putParcelable("structure_position", layoutManager$1 != null ? layoutManager$1.onSaveInstanceState() : null);
            super.onSaveInstanceState(bundle);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:60:0x017b, code lost:
    
        if (((com.android.systemui.controls.controller.ControlsControllerImpl) r2).getActiveFlag(r1) != false) goto L73;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateFavorites() {
        boolean z;
        List list;
        boolean z2;
        if (this.isLoadingFinished) {
            ArrayList arrayList = new ArrayList();
            boolean z3 = false;
            if (BasicRune.CONTROLS_STRUCTURE_ORDERING) {
                for (CharSequence charSequence : this.currentOrder) {
                    Map map = this.controlsMap;
                    if (map != null && (list = (List) ((LinkedHashMap) map).get(charSequence)) != null) {
                        AllStructureModel allStructureModel = this.customModel;
                        if (allStructureModel == null) {
                            allStructureModel = null;
                        }
                        List favorites = allStructureModel.getFavorites();
                        ArrayList arrayList2 = new ArrayList();
                        Iterator it = ((ArrayList) favorites).iterator();
                        while (it.hasNext()) {
                            Object next = it.next();
                            ControlInfo controlInfo = (ControlInfo) next;
                            if (!list.isEmpty()) {
                                Iterator it2 = list.iterator();
                                while (it2.hasNext()) {
                                    if (Intrinsics.areEqual(((ControlStatus) it2.next()).getControlId(), controlInfo.controlId)) {
                                        z2 = true;
                                        break;
                                    }
                                }
                            }
                            z2 = false;
                            if (z2) {
                                arrayList2.add(next);
                            }
                        }
                        ComponentName componentName = this.component;
                        Intrinsics.checkNotNull(componentName);
                        arrayList.add(new StructureInfo(componentName, charSequence, arrayList2));
                    }
                }
            } else {
                Map map2 = this.controlsMap;
                if (map2 != null) {
                    for (Map.Entry entry : ((LinkedHashMap) map2).entrySet()) {
                        AllStructureModel allStructureModel2 = this.customModel;
                        if (allStructureModel2 == null) {
                            allStructureModel2 = null;
                        }
                        List favorites2 = allStructureModel2.getFavorites();
                        ArrayList arrayList3 = new ArrayList();
                        Iterator it3 = ((ArrayList) favorites2).iterator();
                        while (it3.hasNext()) {
                            Object next2 = it3.next();
                            ControlInfo controlInfo2 = (ControlInfo) next2;
                            Iterable iterable = (Iterable) entry.getValue();
                            if (!(iterable instanceof Collection) || !((Collection) iterable).isEmpty()) {
                                Iterator it4 = iterable.iterator();
                                while (it4.hasNext()) {
                                    if (Intrinsics.areEqual(((ControlStatus) it4.next()).getControlId(), controlInfo2.controlId)) {
                                        z = true;
                                        break;
                                    }
                                }
                            }
                            z = false;
                            if (z) {
                                arrayList3.add(next2);
                            }
                        }
                        if (!arrayList3.isEmpty()) {
                            ComponentName componentName2 = this.component;
                            Intrinsics.checkNotNull(componentName2);
                            arrayList.add(new StructureInfo(componentName2, (CharSequence) entry.getKey(), arrayList3));
                        }
                    }
                }
            }
            ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
            Iterator it5 = arrayList.iterator();
            while (it5.hasNext()) {
                List list2 = ((StructureInfo) it5.next()).controls;
                ArrayList arrayList5 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                Iterator it6 = list2.iterator();
                while (it6.hasNext()) {
                    arrayList5.add(((ControlInfo) it6.next()).controlId);
                }
                arrayList4.add(arrayList5);
            }
            boolean z4 = !CollectionsKt___CollectionsKt.minus((Iterable) CollectionsKt__IterablesKt.flatten(arrayList4), (Iterable) this.initialFavoriteIds).isEmpty();
            CustomControlsController customControlsController = this.customController;
            if (!z4) {
                ComponentName componentName3 = this.component;
                Intrinsics.checkNotNull(componentName3);
            }
            z3 = true;
            Iterator it7 = arrayList.iterator();
            while (it7.hasNext()) {
                ((StructureInfo) it7.next()).customStructureInfo.active = z3;
            }
            if (!arrayList.isEmpty()) {
                ComponentName componentName4 = this.component;
                Intrinsics.checkNotNull(componentName4);
                ((ControlsControllerImpl) customControlsController).replaceFavoritesForComponent(new ComponentInfo(componentName4, arrayList), this.isFromMainActivity);
            } else {
                ComponentName componentName5 = this.component;
                Intrinsics.checkNotNull(componentName5);
                boolean z5 = this.isFromMainActivity;
                ((ControlsControllerImpl) customControlsController).getClass();
                Favorites.INSTANCE.getClass();
                Favorites.removeStructures(componentName5, z5);
            }
        }
    }
}
