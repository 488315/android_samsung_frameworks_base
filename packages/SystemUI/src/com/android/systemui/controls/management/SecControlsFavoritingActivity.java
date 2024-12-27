package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.service.controls.Control;
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
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.BaseActivity;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.controller.ComponentInfo;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.ControlsControllerKt$createLoadDataObject$1;
import com.android.systemui.controls.controller.Favorites;
import com.android.systemui.controls.controller.SecControlsController;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.management.adapter.SecStructureAdapter;
import com.android.systemui.controls.management.model.AllStructureModel;
import com.android.systemui.controls.management.model.ControlInfoForStructure;
import com.android.systemui.controls.management.model.StructureModel;
import com.android.systemui.controls.ui.SecControlsActivity;
import com.android.systemui.controls.ui.util.AUIFacade;
import com.android.systemui.controls.ui.util.LayoutUtil;
import com.android.systemui.controls.util.ControlsUtil;
import com.android.systemui.controls.util.SALogger;
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

public final class SecControlsFavoritingActivity extends BaseActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SecControlsFavoritingActivity activity;
    public CharSequence appName;
    public final AUIFacade auiFacade;
    public final BroadcastDispatcher broadcastDispatcher;
    public Runnable cancelLoadRunnable;
    public ComponentName component;
    public final ControlsController controller;
    public LinearLayout controlsListLayout;
    public Map controlsMap;
    public final ControlsUtil controlsUtil;
    public List currentFavorites;
    public List currentOrder;
    public Parcelable currentPosition;
    public final Executor executor;
    public final SecControlsFavoritingActivity$favoriteControlChangeMainCallback$1 favoriteControlChangeMainCallback;
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
    public final SecControlsController secController;
    public SecStructureAdapter structureAdapter;
    public AllStructureModel structureModel;

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

    public SecControlsFavoritingActivity(Executor executor, ControlsController controlsController, SecControlsController secControlsController, BroadcastDispatcher broadcastDispatcher, LayoutUtil layoutUtil, ControlsUtil controlsUtil, UserTracker userTracker, AUIFacade aUIFacade, SALogger sALogger) {
        super(broadcastDispatcher, controlsController, userTracker, executor);
        this.executor = executor;
        this.controller = controlsController;
        this.secController = secControlsController;
        this.broadcastDispatcher = broadcastDispatcher;
        this.layoutUtil = layoutUtil;
        this.controlsUtil = controlsUtil;
        this.auiFacade = aUIFacade;
        this.saLogger = sALogger;
        this.activity = this;
        EmptyList emptyList = EmptyList.INSTANCE;
        this.currentOrder = emptyList;
        this.requestOrder = emptyList;
        this.initialFavoriteIds = new LinkedHashSet();
        this.favoriteControlChangeMainCallback = new StructureModel.StructureModelCallback() { // from class: com.android.systemui.controls.management.SecControlsFavoritingActivity$favoriteControlChangeMainCallback$1
            @Override // com.android.systemui.controls.management.model.StructureModel.StructureModelCallback
            public final void onControlInfoChange(ControlInfoForStructure controlInfoForStructure) {
            }
        };
    }

    public final void changeDataAndShow() {
        this.isLoadingFinished = true;
        this.requestOrder = EmptyList.INSTANCE;
        SecStructureAdapter secStructureAdapter = this.structureAdapter;
        if (secStructureAdapter == null) {
            secStructureAdapter = null;
        }
        AllStructureModel allStructureModel = this.structureModel;
        if (allStructureModel == null) {
            allStructureModel = null;
        }
        secStructureAdapter.model = allStructureModel;
        secStructureAdapter.notifyDataSetChanged();
        AllStructureModel allStructureModel2 = this.structureModel;
        if (allStructureModel2 == null) {
            allStructureModel2 = null;
        }
        allStructureModel2.getClass();
        invalidateOptionsMenu();
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
    public final String getTag() {
        return "SecControlsFavoritingActivity";
    }

    public final void loadForComponent(ControlsController.LoadData loadData, boolean z) {
        List list;
        ControlsControllerKt$createLoadDataObject$1 controlsControllerKt$createLoadDataObject$1 = (ControlsControllerKt$createLoadDataObject$1) loadData;
        ?? r0 = controlsControllerKt$createLoadDataObject$1.allControls;
        List list2 = this.currentFavorites;
        if (list2 != null) {
            for (ControlStatus controlStatus : (Iterable) r0) {
                controlStatus.favorite = list2.contains(controlStatus.control.getControlId());
            }
        }
        List list3 = this.currentFavorites;
        if (list3 == null) {
            list3 = controlsControllerKt$createLoadDataObject$1.favoritesIds;
        }
        List list4 = list3;
        if (controlsControllerKt$createLoadDataObject$1.errorOnLoad) {
            this.executor.execute(new Runnable() { // from class: com.android.systemui.controls.management.SecControlsFavoritingActivity$showErrorDialog$1
                @Override // java.lang.Runnable
                public final void run() {
                    SecControlsFavoritingActivity.this.activity.isDestroyed();
                    if (SecControlsFavoritingActivity.this.activity.isDestroyed()) {
                        return;
                    }
                    final SecControlsFavoritingActivity secControlsFavoritingActivity = SecControlsFavoritingActivity.this;
                    String string = secControlsFavoritingActivity.getResources().getString(R.string.controls_retry_dialog_loading_timeout, secControlsFavoritingActivity.appName);
                    AlertDialog.Builder builder = new AlertDialog.Builder(secControlsFavoritingActivity, 2132018525);
                    AlertController.AlertParams alertParams = builder.P;
                    alertParams.mTitle = alertParams.mContext.getText(R.string.controls_retry_dialog_title);
                    alertParams.mMessage = string;
                    DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() { // from class: com.android.systemui.controls.management.SecControlsFavoritingActivity$createErrorDialog$builder$1$1
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            SecControlsFavoritingActivity secControlsFavoritingActivity2 = SecControlsFavoritingActivity.this;
                            int i2 = SecControlsFavoritingActivity.$r8$clinit;
                            ComponentName componentName = secControlsFavoritingActivity2.component;
                            if (componentName != null) {
                                ((ControlsControllerImpl) secControlsFavoritingActivity2.secController).loadForComponent(componentName, new SecControlsFavoritingActivity$loadControls$1$1(secControlsFavoritingActivity2), new SecControlsFavoritingActivity$loadControls$1$2(secControlsFavoritingActivity2), new SecControlsFavoritingActivity$loadControls$1$3(secControlsFavoritingActivity2));
                            }
                            dialogInterface.dismiss();
                        }
                    };
                    alertParams.mPositiveButtonText = alertParams.mContext.getText(R.string.controls_retry_dialog_retry);
                    alertParams.mPositiveButtonListener = onClickListener;
                    DialogInterface.OnClickListener onClickListener2 = new DialogInterface.OnClickListener() { // from class: com.android.systemui.controls.management.SecControlsFavoritingActivity$createErrorDialog$builder$1$2
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            SecControlsFavoritingActivity secControlsFavoritingActivity2 = SecControlsFavoritingActivity.this;
                            Intrinsics.checkNotNull(dialogInterface);
                            int i2 = SecControlsFavoritingActivity.$r8$clinit;
                            secControlsFavoritingActivity2.onBackPressed();
                            dialogInterface.cancel();
                        }
                    };
                    alertParams.mNegativeButtonText = alertParams.mContext.getText(R.string.controls_dialog_cancel);
                    alertParams.mNegativeButtonListener = onClickListener2;
                    alertParams.mOnKeyListener = new DialogInterface.OnKeyListener() { // from class: com.android.systemui.controls.management.SecControlsFavoritingActivity$createErrorDialog$builder$1$3
                        @Override // android.content.DialogInterface.OnKeyListener
                        public final boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                            if (i != 4) {
                                return false;
                            }
                            SecControlsFavoritingActivity secControlsFavoritingActivity2 = SecControlsFavoritingActivity.this;
                            Intrinsics.checkNotNull(dialogInterface);
                            int i2 = SecControlsFavoritingActivity.$r8$clinit;
                            secControlsFavoritingActivity2.onBackPressed();
                            dialogInterface.cancel();
                            return true;
                        }
                    };
                    AlertDialog create = builder.create();
                    create.setCanceledOnTouchOutside(false);
                    secControlsFavoritingActivity.retryDialog = create;
                    AlertDialog alertDialog = SecControlsFavoritingActivity.this.retryDialog;
                    if (alertDialog != null) {
                        alertDialog.show();
                    }
                }
            });
            return;
        }
        Iterable iterable = (Iterable) r0;
        ArrayList arrayList = new ArrayList();
        for (Object obj : iterable) {
            if (((ControlStatus) obj).removed) {
                arrayList.add(obj);
            }
        }
        arrayList.toString();
        if (this.isAutoRemove) {
            r0 = new ArrayList();
            for (Object obj2 : iterable) {
                if (!((ControlStatus) obj2).removed) {
                    r0.add(obj2);
                }
            }
        }
        Iterable<ControlStatus> iterable2 = (Iterable) r0;
        for (ControlStatus controlStatus2 : iterable2) {
            ControlsUtil.Companion companion = ControlsUtil.Companion;
            Control control = controlStatus2.control;
            companion.getClass();
            ControlsUtil.Companion.dump(control);
        }
        List list5 = (List) iterable2;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj3 : list5) {
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
        this.structureModel = new AllStructureModel(getResources(), list5, list4, this.favoriteControlChangeMainCallback, false);
        if (!this.requestOrder.isEmpty()) {
            list = this.requestOrder;
        } else {
            ComponentName componentName = this.component;
            Intrinsics.checkNotNull(componentName);
            ((ControlsControllerImpl) this.controller).getClass();
            Favorites.INSTANCE.getClass();
            List structuresForComponent = Favorites.getStructuresForComponent(componentName);
            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(structuresForComponent, 10));
            Iterator it = structuresForComponent.iterator();
            while (it.hasNext()) {
                arrayList2.add(((StructureInfo) it.next()).structure);
            }
            list = arrayList2;
        }
        AllStructureModel allStructureModel = this.structureModel;
        if (allStructureModel == null) {
            allStructureModel = null;
        }
        this.executor.execute(new SecControlsFavoritingActivity$refreshStructureOrdering$2(this, allStructureModel, list, new Function0() { // from class: com.android.systemui.controls.management.SecControlsFavoritingActivity$refreshStructureOrdering$1
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Unit.INSTANCE;
            }
        }));
        if (z) {
            changeDataAndShow();
        } else {
            this.executor.execute(new Runnable() { // from class: com.android.systemui.controls.management.SecControlsFavoritingActivity$loadForComponent$4
                @Override // java.lang.Runnable
                public final void run() {
                    SecControlsFavoritingActivity secControlsFavoritingActivity = SecControlsFavoritingActivity.this;
                    int i = SecControlsFavoritingActivity.$r8$clinit;
                    secControlsFavoritingActivity.changeDataAndShow();
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
            AllStructureModel allStructureModel = this.structureModel;
            if (allStructureModel == null) {
                allStructureModel = null;
            }
            this.executor.execute(new SecControlsFavoritingActivity$refreshStructureOrdering$2(this, allStructureModel, charSequenceArrayListExtra, new SecControlsFavoritingActivity$onActivityResult$1$1(this)));
        }
    }

    @Override // com.android.systemui.controls.BaseActivity
    public final void onBackKeyPressed() {
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
        boolean equals = charSequenceExtra2 != null ? charSequenceExtra2.equals(Reflection.getOrCreateKotlinClass(SecControlsActivity.class).getSimpleName()) : false;
        this.isFromMainActivity = equals;
        Log.d("SecControlsFavoritingActivity", "onCreate isFromMainActivity = " + equals + ", class = " + ((Object) charSequenceExtra2));
        this.component = (ComponentName) getIntent().getParcelableExtra("android.intent.extra.COMPONENT_NAME");
        if (bundle != null) {
            this.currentFavorites = bundle.getStringArrayList("current_favorites");
            this.currentPosition = bundle.getParcelable("structure_position");
        }
        setContentView(R.layout.activity_controls_favoriting);
        setSupportActionBar((Toolbar) requireViewById(R.id.toolbar));
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(this.appName);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        FrameLayout frameLayout = (FrameLayout) requireViewById(R.id.main_layout);
        Intrinsics.checkNotNull(frameLayout);
        float f = frameLayout.getContext().getResources().getFloat(R.integer.control_basic_width_percentage);
        LayoutUtil layoutUtil = this.layoutUtil;
        layoutUtil.setLayoutWeightWidthPercentBasic(layoutUtil.getWidthPercentBasic(f), frameLayout);
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
        this.structureAdapter = new SecStructureAdapter(this.layoutUtil, this.controlsUtil, this.auiFacade, ((UserTrackerImpl) this.userTracker).getUserId(), this.currentPosition != null ? new Consumer() { // from class: com.android.systemui.controls.management.SecControlsFavoritingActivity$bindViews$layoutCompletedCallback$1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((RecyclerView.LayoutManager) obj).onRestoreInstanceState(SecControlsFavoritingActivity.this.currentPosition);
            }
        } : null);
        RecyclerView recyclerView = (RecyclerView) requireViewById(R.id.listAll);
        SecStructureAdapter secStructureAdapter = this.structureAdapter;
        if (secStructureAdapter == null) {
            secStructureAdapter = null;
        }
        recyclerView.setAdapter(secStructureAdapter);
        recyclerView.seslSetGoToTopEnabled();
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
        this.saLogger.sendScreenView(SALogger.Screen.ChooseDevices.INSTANCE);
    }

    @Override // android.app.Activity
    public final boolean onCreateOptionsMenu(Menu menu) {
        Map map;
        Set keySet;
        if (this.isLoadingFinished && (map = this.controlsMap) != null && (keySet = ((LinkedHashMap) map).keySet()) != null && keySet.size() > 1) {
            getMenuInflater().inflate(R.menu.controls_reorder_menu, menu);
        }
        return true;
    }

    @Override // com.android.systemui.controls.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        int i;
        int i2;
        int i3;
        int i4;
        Runnable runnable = this.cancelLoadRunnable;
        if (runnable != null) {
            runnable.run();
        }
        AlertDialog alertDialog = this.retryDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        if (this.isLoadingFinished) {
            Map map = this.controlsMap;
            int i5 = 0;
            if (map != null) {
                LinkedHashMap linkedHashMap = (LinkedHashMap) map;
                int size = linkedHashMap.keySet().size();
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
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            onBackPressed();
            return true;
        }
        if (itemId != R.id.reorder_menu) {
            return super.onOptionsItemSelected(menuItem);
        }
        ComponentName componentName = this.component;
        if (componentName == null) {
            return true;
        }
        Intent intent = new Intent(this, (Class<?>) SecControlsReorderActivity.class);
        intent.putExtra("android.intent.extra.COMPONENT_NAME", componentName);
        intent.putExtra("extra_structure_lists", (ArrayList) this.currentOrder);
        this.saLogger.sendEvent(SALogger.Event.Reorder.INSTANCE);
        startActivityForResult(intent, 1000);
        return true;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
        if (this.isPagerLoaded) {
            return;
        }
        Resources resources = getResources();
        EmptyList emptyList = EmptyList.INSTANCE;
        AllStructureModel allStructureModel = new AllStructureModel(resources, emptyList, emptyList, this.favoriteControlChangeMainCallback, true);
        SecStructureAdapter secStructureAdapter = this.structureAdapter;
        if (secStructureAdapter == null) {
            secStructureAdapter = null;
        }
        secStructureAdapter.model = allStructureModel;
        secStructureAdapter.notifyDataSetChanged();
        ComponentName componentName = this.component;
        if (componentName != null) {
            ((ControlsControllerImpl) this.secController).loadForComponent(componentName, new SecControlsFavoritingActivity$loadControls$1$1(this), new SecControlsFavoritingActivity$loadControls$1$2(this), new SecControlsFavoritingActivity$loadControls$1$3(this));
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
            AllStructureModel allStructureModel = this.structureModel;
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
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            bundle.putParcelable("structure_position", layoutManager != null ? layoutManager.onSaveInstanceState() : null);
            super.onSaveInstanceState(bundle);
        }
    }

    public final void updateFavorites() {
        List list;
        if (this.isLoadingFinished) {
            ArrayList arrayList = new ArrayList();
            for (CharSequence charSequence : this.currentOrder) {
                Map map = this.controlsMap;
                if (map != null && (list = (List) ((LinkedHashMap) map).get(charSequence)) != null) {
                    AllStructureModel allStructureModel = this.structureModel;
                    if (allStructureModel == null) {
                        allStructureModel = null;
                    }
                    List favorites = allStructureModel.getFavorites();
                    ArrayList arrayList2 = new ArrayList();
                    Iterator it = ((ArrayList) favorites).iterator();
                    while (it.hasNext()) {
                        Object next = it.next();
                        ControlInfo controlInfo = (ControlInfo) next;
                        List list2 = list;
                        if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                            Iterator it2 = list2.iterator();
                            while (true) {
                                if (it2.hasNext()) {
                                    if (((ControlStatus) it2.next()).control.getControlId().equals(controlInfo.controlId)) {
                                        arrayList2.add(next);
                                        break;
                                    }
                                } else {
                                    break;
                                }
                            }
                        }
                    }
                    ComponentName componentName = this.component;
                    Intrinsics.checkNotNull(componentName);
                    arrayList.add(new StructureInfo(componentName, charSequence, arrayList2, false, 8, null));
                }
            }
            ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
            Iterator it3 = arrayList.iterator();
            while (it3.hasNext()) {
                List list3 = ((StructureInfo) it3.next()).controls;
                ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
                Iterator it4 = list3.iterator();
                while (it4.hasNext()) {
                    arrayList4.add(((ControlInfo) it4.next()).controlId);
                }
                arrayList3.add(arrayList4);
            }
            boolean z = true;
            boolean z2 = !CollectionsKt___CollectionsKt.minus((Iterable) CollectionsKt__IterablesKt.flatten(arrayList3), (Iterable) this.initialFavoriteIds).isEmpty();
            SecControlsController secControlsController = this.secController;
            if (!z2) {
                ComponentName componentName2 = this.component;
                Intrinsics.checkNotNull(componentName2);
                ((ControlsControllerImpl) secControlsController).getClass();
                Favorites.INSTANCE.getClass();
                if (!Favorites.getActiveFlag(componentName2)) {
                    z = false;
                }
            }
            Iterator it5 = arrayList.iterator();
            while (it5.hasNext()) {
                ((StructureInfo) it5.next()).active = z;
            }
            if (!arrayList.isEmpty()) {
                ComponentName componentName3 = this.component;
                Intrinsics.checkNotNull(componentName3);
                ((ControlsControllerImpl) secControlsController).replaceFavoritesForComponent(new ComponentInfo(componentName3, arrayList), this.isFromMainActivity);
            } else {
                ComponentName componentName4 = this.component;
                Intrinsics.checkNotNull(componentName4);
                boolean z3 = this.isFromMainActivity;
                ((ControlsControllerImpl) secControlsController).getClass();
                Favorites.INSTANCE.getClass();
                Favorites.removeStructures(componentName4, z3);
            }
        }
    }
}
