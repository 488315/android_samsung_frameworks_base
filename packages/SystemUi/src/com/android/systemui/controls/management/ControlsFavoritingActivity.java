package com.android.systemui.controls.management;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedCallback;
import androidx.activity.ComponentActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.controls.ControlStatus;
import com.android.systemui.controls.TooltipManager;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.ControlsControllerImpl$replaceFavoritesForStructure$1;
import com.android.systemui.controls.controller.ControlsControllerKt$createLoadDataObject$1;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.management.ControlsModel;
import com.android.systemui.controls.p005ui.ControlsActivity;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.concurrency.ExecutorImpl;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlsFavoritingActivity extends ComponentActivity {
    public CharSequence appName;
    public Runnable cancelLoadRunnable;
    public ControlsFavoritingActivity$onCreate$$inlined$compareBy$1 comparator;
    public ComponentName component;
    public final ControlsControllerImpl controller;
    public View doneButton;
    public final Executor executor;
    public final boolean isNewFlowEnabled;
    public boolean isPagerLoaded;
    public final ControlsListingController listingController;
    public TooltipManager mTooltipManager;
    public byte openSource;
    public View otherAppsButton;
    public ManagementPageIndicator pageIndicator;
    public Button rearrangeButton;
    public TextView statusText;
    public CharSequence structureExtra;
    public ViewPager2 structurePager;
    public TextView subtitleView;
    public TextView titleView;
    public final UserTracker userTracker;
    public List listOfStructures = EmptyList.INSTANCE;
    public final UserTracker.Callback userTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$userTrackerCallback$1
        public final int startingUser;

        {
            this.startingUser = ControlsFavoritingActivity.this.controller.getCurrentUserId();
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            if (i != this.startingUser) {
                ControlsFavoritingActivity controlsFavoritingActivity = ControlsFavoritingActivity.this;
                ((UserTrackerImpl) controlsFavoritingActivity.userTracker).removeCallback(this);
                controlsFavoritingActivity.finish();
            }
        }
    };
    public final ControlsFavoritingActivity$mOnBackInvokedCallback$1 mOnBackInvokedCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$mOnBackInvokedCallback$1
        @Override // android.window.OnBackInvokedCallback
        public final void onBackInvoked() {
            ControlsFavoritingActivity.this.onBackPressed();
        }
    };
    public final ControlsFavoritingActivity$listingCallback$1 listingCallback = new ControlsListingController.ControlsListingCallback() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$listingCallback$1
        @Override // com.android.systemui.controls.management.ControlsListingController.ControlsListingCallback
        public final void onServicesUpdated(List list) {
            if (((ArrayList) list).size() > 1) {
                final ControlsFavoritingActivity controlsFavoritingActivity = ControlsFavoritingActivity.this;
                final int i = controlsFavoritingActivity.isNewFlowEnabled ? 8 : 0;
                View view = controlsFavoritingActivity.otherAppsButton;
                if (view == null) {
                    view = null;
                }
                if (view.getVisibility() != i) {
                    View view2 = controlsFavoritingActivity.otherAppsButton;
                    (view2 != null ? view2 : null).post(new Runnable() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$listingCallback$1$onServicesUpdated$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            View view3 = ControlsFavoritingActivity.this.otherAppsButton;
                            if (view3 == null) {
                                view3 = null;
                            }
                            view3.setVisibility(i);
                        }
                    });
                }
            }
        }
    };
    public final ControlsFavoritingActivity$controlsModelCallback$1 controlsModelCallback = new ControlsModel.ControlsModelCallback() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$controlsModelCallback$1
        @Override // com.android.systemui.controls.management.ControlsModel.ControlsModelCallback
        public final void onChange() {
            ControlsFavoritingActivity controlsFavoritingActivity = ControlsFavoritingActivity.this;
            List list = controlsFavoritingActivity.listOfStructures;
            ViewPager2 viewPager2 = controlsFavoritingActivity.structurePager;
            if (viewPager2 == null) {
                viewPager2 = null;
            }
            StructureContainer structureContainer = (StructureContainer) list.get(viewPager2.mCurrentItem);
            Button button = controlsFavoritingActivity.rearrangeButton;
            (button != null ? button : null).setEnabled(!structureContainer.model.getFavorites().isEmpty());
        }

        @Override // com.android.systemui.controls.management.ControlsModel.ControlsModelCallback
        public final void onFirstChange() {
            View view = ControlsFavoritingActivity.this.doneButton;
            if (view == null) {
                view = null;
            }
            view.setEnabled(true);
        }
    };

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

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.controls.management.ControlsFavoritingActivity$mOnBackInvokedCallback$1] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.controls.management.ControlsFavoritingActivity$listingCallback$1] */
    /* JADX WARN: Type inference failed for: r1v6, types: [com.android.systemui.controls.management.ControlsFavoritingActivity$controlsModelCallback$1] */
    public ControlsFavoritingActivity(FeatureFlags featureFlags, Executor executor, ControlsControllerImpl controlsControllerImpl, ControlsListingController controlsListingController, UserTracker userTracker) {
        this.executor = executor;
        this.controller = controlsControllerImpl;
        this.listingController = controlsListingController;
        this.userTracker = userTracker;
        this.isNewFlowEnabled = ((FeatureFlagsRelease) featureFlags).isEnabled(Flags.CONTROLS_MANAGEMENT_NEW_FLOWS);
    }

    public static final void access$saveFavorites(ControlsFavoritingActivity controlsFavoritingActivity) {
        for (StructureContainer structureContainer : controlsFavoritingActivity.listOfStructures) {
            List favorites = structureContainer.model.getFavorites();
            ComponentName componentName = controlsFavoritingActivity.component;
            Intrinsics.checkNotNull(componentName);
            StructureInfo structureInfo = new StructureInfo(componentName, structureContainer.structureName, favorites);
            ControlsControllerImpl controlsControllerImpl = controlsFavoritingActivity.controller;
            if (controlsControllerImpl.confirmAvailability()) {
                ((ExecutorImpl) controlsControllerImpl.executor).execute(new ControlsControllerImpl$replaceFavoritesForStructure$1(structureInfo, controlsControllerImpl));
            }
        }
    }

    /* renamed from: animateExitAndFinish$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public void m116x8067cc31() {
        ControlsAnimations.exitAnimation((ViewGroup) requireViewById(R.id.controls_management_root), new Runnable() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$animateExitAndFinish$1
            @Override // java.lang.Runnable
            public final void run() {
                ControlsFavoritingActivity.this.finish();
            }
        }).start();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void onBackPressed() {
        if (this.openSource == 2) {
            m116x8067cc31();
        }
        if (!(this.openSource == 1)) {
            startActivity(new Intent(getApplicationContext(), (Class<?>) ControlsActivity.class), ActivityOptions.makeSceneTransitionAnimation(this, new Pair[0]).toBundle());
        }
        m116x8067cc31();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        TooltipManager tooltipManager = this.mTooltipManager;
        if (tooltipManager != null) {
            tooltipManager.hide(false);
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.systemui.controls.management.ControlsFavoritingActivity$onCreate$$inlined$compareBy$1] */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        final Collator collator = Collator.getInstance(getResources().getConfiguration().getLocales().get(0));
        this.comparator = new Comparator() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$onCreate$$inlined$compareBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return collator.compare(((StructureContainer) obj).structureName, ((StructureContainer) obj2).structureName);
            }
        };
        this.appName = getIntent().getCharSequenceExtra("extra_app_label");
        this.structureExtra = getIntent().getCharSequenceExtra("extra_structure");
        this.component = (ComponentName) getIntent().getParcelableExtra("android.intent.extra.COMPONENT_NAME");
        this.openSource = getIntent().getByteExtra("extra_source", (byte) 0);
        setContentView(R.layout.controls_management);
        ControlsAnimations controlsAnimations = ControlsAnimations.INSTANCE;
        ViewGroup viewGroup = (ViewGroup) requireViewById(R.id.controls_management_root);
        Window window = getWindow();
        Intent intent = getIntent();
        controlsAnimations.getClass();
        ((ComponentActivity) this).mLifecycleRegistry.addObserver(new ControlsAnimations$observerForAnimations$1(intent, viewGroup, true, window));
        ViewStub viewStub = (ViewStub) requireViewById(R.id.stub);
        viewStub.setLayoutResource(R.layout.controls_management_favorites);
        viewStub.inflate();
        this.statusText = (TextView) requireViewById(R.id.status_message);
        if (Prefs.getInt(getApplicationContext(), "ControlsStructureSwipeTooltipCount", 0) < 2) {
            TextView textView = this.statusText;
            if (textView == null) {
                textView = null;
            }
            TooltipManager tooltipManager = new TooltipManager(textView.getContext(), "ControlsStructureSwipeTooltipCount", 2, false, 8, null);
            this.mTooltipManager = tooltipManager;
            addContentView(tooltipManager.layout, new FrameLayout.LayoutParams(-2, -2, 51));
        }
        ManagementPageIndicator managementPageIndicator = (ManagementPageIndicator) requireViewById(R.id.structure_page_indicator);
        managementPageIndicator.visibilityListener = new Function1() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$bindViews$2$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                TooltipManager tooltipManager2;
                if (((Number) obj).intValue() != 0 && (tooltipManager2 = ControlsFavoritingActivity.this.mTooltipManager) != null) {
                    tooltipManager2.hide(true);
                }
                return Unit.INSTANCE;
            }
        };
        this.pageIndicator = managementPageIndicator;
        CharSequence charSequence = this.structureExtra;
        if (charSequence == null && (charSequence = this.appName) == null) {
            charSequence = getResources().getText(R.string.controls_favorite_default_title);
        }
        TextView textView2 = (TextView) requireViewById(R.id.title);
        textView2.setText(charSequence);
        this.titleView = textView2;
        TextView textView3 = (TextView) requireViewById(R.id.subtitle);
        textView3.setText(textView3.getResources().getText(R.string.controls_favorite_subtitle));
        this.subtitleView = textView3;
        ViewPager2 viewPager2 = (ViewPager2) requireViewById(R.id.structure_pager);
        this.structurePager = viewPager2;
        ((ArrayList) viewPager2.mExternalPageChangeCallbacks.mCallbacks).add(new ViewPager2.OnPageChangeCallback() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$bindViews$5
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public final void onPageSelected(int i) {
                TooltipManager tooltipManager2 = ControlsFavoritingActivity.this.mTooltipManager;
                if (tooltipManager2 != null) {
                    tooltipManager2.hide(true);
                }
            }
        });
        final Button button = (Button) requireViewById(R.id.rearrange);
        button.setText(this.openSource == 2 ? getString(R.string.controls_favorite_back_to_editing) : getString(R.string.controls_favorite_rearrange_button));
        button.setEnabled(false);
        button.setVisibility(this.isNewFlowEnabled ? 0 : 8);
        button.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$bindButtons$1$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ControlsFavoritingActivity controlsFavoritingActivity = ControlsFavoritingActivity.this;
                if (controlsFavoritingActivity.component == null) {
                    return;
                }
                ControlsFavoritingActivity.access$saveFavorites(controlsFavoritingActivity);
                ControlsFavoritingActivity controlsFavoritingActivity2 = ControlsFavoritingActivity.this;
                Intent intent2 = new Intent(button.getContext(), (Class<?>) ControlsEditingActivity.class);
                ControlsFavoritingActivity controlsFavoritingActivity3 = ControlsFavoritingActivity.this;
                intent2.putExtra("android.intent.extra.COMPONENT_NAME", controlsFavoritingActivity3.component);
                intent2.putExtra("extra_app_label", controlsFavoritingActivity3.appName);
                intent2.putExtra("extra_from_favoriting", true);
                List list = controlsFavoritingActivity3.listOfStructures;
                ViewPager2 viewPager22 = controlsFavoritingActivity3.structurePager;
                if (viewPager22 == null) {
                    viewPager22 = null;
                }
                intent2.putExtra("extra_structure", ((StructureContainer) list.get(viewPager22.mCurrentItem)).structureName);
                controlsFavoritingActivity2.startActivity(intent2, ActivityOptions.makeSceneTransitionAnimation(ControlsFavoritingActivity.this, new Pair[0]).toBundle());
            }
        });
        this.rearrangeButton = button;
        View requireViewById = requireViewById(R.id.other_apps);
        final Button button2 = (Button) requireViewById;
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$bindButtons$2$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                View view2 = ControlsFavoritingActivity.this.doneButton;
                if (view2 == null) {
                    view2 = null;
                }
                if (view2.isEnabled()) {
                    Toast.makeText(ControlsFavoritingActivity.this.getApplicationContext(), R.string.controls_favorite_toast_no_changes, 0).show();
                }
                ControlsFavoritingActivity.this.startActivity(new Intent(button2.getContext(), (Class<?>) ControlsProviderSelectorActivity.class), ActivityOptions.makeSceneTransitionAnimation(ControlsFavoritingActivity.this, new Pair[0]).toBundle());
                ControlsFavoritingActivity.this.m116x8067cc31();
            }
        });
        this.otherAppsButton = requireViewById;
        View requireViewById2 = requireViewById(R.id.done);
        Button button3 = (Button) requireViewById2;
        button3.setEnabled(false);
        button3.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$bindButtons$3$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ControlsFavoritingActivity controlsFavoritingActivity = ControlsFavoritingActivity.this;
                if (controlsFavoritingActivity.component == null) {
                    return;
                }
                ControlsFavoritingActivity.access$saveFavorites(controlsFavoritingActivity);
                ControlsFavoritingActivity.this.m116x8067cc31();
                ControlsFavoritingActivity controlsFavoritingActivity2 = ControlsFavoritingActivity.this;
                controlsFavoritingActivity2.getClass();
                controlsFavoritingActivity2.startActivity(new Intent(controlsFavoritingActivity2.getApplicationContext(), (Class<?>) ControlsActivity.class), ActivityOptions.makeSceneTransitionAnimation(controlsFavoritingActivity2, new Pair[0]).toBundle());
            }
        });
        this.doneButton = requireViewById2;
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        Runnable runnable = this.cancelLoadRunnable;
        if (runnable != null) {
            runnable.run();
        }
        super.onDestroy();
    }

    @Override // android.app.Activity
    public final void onPause() {
        super.onPause();
        TooltipManager tooltipManager = this.mTooltipManager;
        if (tooltipManager != null) {
            tooltipManager.hide(false);
        }
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        if (this.isPagerLoaded) {
            return;
        }
        ViewPager2 viewPager2 = this.structurePager;
        if (viewPager2 == null) {
            viewPager2 = null;
        }
        viewPager2.setAlpha(0.0f);
        ManagementPageIndicator managementPageIndicator = this.pageIndicator;
        if (managementPageIndicator == null) {
            managementPageIndicator = null;
        }
        managementPageIndicator.setAlpha(0.0f);
        ViewPager2 viewPager22 = this.structurePager;
        if (viewPager22 == null) {
            viewPager22 = null;
        }
        viewPager22.setAdapter(new StructureAdapter(EmptyList.INSTANCE, ((UserTrackerImpl) this.userTracker).getUserId()));
        ((ArrayList) viewPager22.mExternalPageChangeCallbacks.mCallbacks).add(new ViewPager2.OnPageChangeCallback() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$setUpPager$1$1
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public final void onPageScrolled(float f, int i, int i2) {
                ManagementPageIndicator managementPageIndicator2 = ControlsFavoritingActivity.this.pageIndicator;
                if (managementPageIndicator2 == null) {
                    managementPageIndicator2 = null;
                }
                managementPageIndicator2.setLocation(i + f);
            }

            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public final void onPageSelected(int i) {
                ControlsFavoritingActivity controlsFavoritingActivity = ControlsFavoritingActivity.this;
                CharSequence charSequence = ((StructureContainer) controlsFavoritingActivity.listOfStructures.get(i)).structureName;
                if (TextUtils.isEmpty(charSequence)) {
                    charSequence = controlsFavoritingActivity.appName;
                }
                TextView textView = controlsFavoritingActivity.titleView;
                if (textView == null) {
                    textView = null;
                }
                textView.setText(charSequence);
                TextView textView2 = controlsFavoritingActivity.titleView;
                (textView2 != null ? textView2 : null).requestFocus();
            }
        });
        ComponentName componentName = this.component;
        if (componentName != null) {
            TextView textView = this.statusText;
            (textView != null ? textView : null).setText(getResources().getText(android.R.string.peerTtyModeOff));
            final CharSequence text = getResources().getText(R.string.controls_favorite_other_zone_header);
            this.controller.loadForComponent(componentName, new Consumer() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$loadControls$1$1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ControlsControllerKt$createLoadDataObject$1 controlsControllerKt$createLoadDataObject$1 = (ControlsControllerKt$createLoadDataObject$1) ((ControlsController.LoadData) obj);
                    LinkedHashMap linkedHashMap = new LinkedHashMap();
                    for (Object obj2 : controlsControllerKt$createLoadDataObject$1.allControls) {
                        CharSequence structure = ((ControlStatus) obj2).control.getStructure();
                        if (structure == null) {
                            structure = "";
                        }
                        Object obj3 = linkedHashMap.get(structure);
                        if (obj3 == null) {
                            obj3 = new ArrayList();
                            linkedHashMap.put(structure, obj3);
                        }
                        ((List) obj3).add(obj2);
                    }
                    ControlsFavoritingActivity controlsFavoritingActivity = ControlsFavoritingActivity.this;
                    CharSequence charSequence = text;
                    ArrayList arrayList = new ArrayList(linkedHashMap.size());
                    for (Map.Entry entry : linkedHashMap.entrySet()) {
                        arrayList.add(new StructureContainer((CharSequence) entry.getKey(), new AllModel((List) entry.getValue(), controlsControllerKt$createLoadDataObject$1.favoritesIds, charSequence, controlsFavoritingActivity.controlsModelCallback)));
                    }
                    ControlsFavoritingActivity$onCreate$$inlined$compareBy$1 controlsFavoritingActivity$onCreate$$inlined$compareBy$1 = ControlsFavoritingActivity.this.comparator;
                    if (controlsFavoritingActivity$onCreate$$inlined$compareBy$1 == null) {
                        controlsFavoritingActivity$onCreate$$inlined$compareBy$1 = null;
                    }
                    controlsFavoritingActivity.listOfStructures = CollectionsKt___CollectionsKt.sortedWith(arrayList, controlsFavoritingActivity$onCreate$$inlined$compareBy$1);
                    ControlsFavoritingActivity controlsFavoritingActivity2 = ControlsFavoritingActivity.this;
                    Iterator it = controlsFavoritingActivity2.listOfStructures.iterator();
                    final int i = 0;
                    while (true) {
                        if (!it.hasNext()) {
                            i = -1;
                            break;
                        } else if (Intrinsics.areEqual(((StructureContainer) it.next()).structureName, controlsFavoritingActivity2.structureExtra)) {
                            break;
                        } else {
                            i++;
                        }
                    }
                    if (i == -1) {
                        i = 0;
                    }
                    if (ControlsFavoritingActivity.this.getIntent().getBooleanExtra("extra_single_structure", false)) {
                        ControlsFavoritingActivity controlsFavoritingActivity3 = ControlsFavoritingActivity.this;
                        controlsFavoritingActivity3.listOfStructures = Collections.singletonList(controlsFavoritingActivity3.listOfStructures.get(i));
                    }
                    final ControlsFavoritingActivity controlsFavoritingActivity4 = ControlsFavoritingActivity.this;
                    Executor executor = controlsFavoritingActivity4.executor;
                    final boolean z = controlsControllerKt$createLoadDataObject$1.errorOnLoad;
                    executor.execute(new Runnable() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$loadControls$1$1.2
                        /* JADX WARN: Multi-variable type inference failed */
                        /* JADX WARN: Type inference failed for: r7v3, types: [androidx.viewpager2.widget.ViewPager2] */
                        @Override // java.lang.Runnable
                        public final void run() {
                            ControlsFavoritingActivity controlsFavoritingActivity5 = ControlsFavoritingActivity.this;
                            ViewPager2 viewPager23 = controlsFavoritingActivity5.structurePager;
                            if (viewPager23 == null) {
                                viewPager23 = null;
                            }
                            viewPager23.setAdapter(new StructureAdapter(controlsFavoritingActivity5.listOfStructures, ((UserTrackerImpl) controlsFavoritingActivity5.userTracker).getUserId()));
                            ViewPager2 viewPager24 = ControlsFavoritingActivity.this.structurePager;
                            if (viewPager24 == null) {
                                viewPager24 = null;
                            }
                            int i2 = i;
                            if (viewPager24.mFakeDragger.mScrollEventAdapter.mFakeDragging) {
                                throw new IllegalStateException("Cannot change current item when ViewPager2 is fake dragging");
                            }
                            viewPager24.setCurrentItemInternal(i2);
                            if (z) {
                                ControlsFavoritingActivity controlsFavoritingActivity6 = ControlsFavoritingActivity.this;
                                TextView textView2 = controlsFavoritingActivity6.statusText;
                                if (textView2 == null) {
                                    textView2 = null;
                                }
                                Resources resources = controlsFavoritingActivity6.getResources();
                                Object[] objArr = new Object[1];
                                Object obj4 = ControlsFavoritingActivity.this.appName;
                                if (obj4 == null) {
                                    obj4 = "";
                                }
                                objArr[0] = obj4;
                                textView2.setText(resources.getString(R.string.controls_favorite_load_error, objArr));
                                TextView textView3 = ControlsFavoritingActivity.this.subtitleView;
                                (textView3 != null ? textView3 : null).setVisibility(8);
                                return;
                            }
                            if (ControlsFavoritingActivity.this.listOfStructures.isEmpty()) {
                                ControlsFavoritingActivity controlsFavoritingActivity7 = ControlsFavoritingActivity.this;
                                TextView textView4 = controlsFavoritingActivity7.statusText;
                                if (textView4 == null) {
                                    textView4 = null;
                                }
                                textView4.setText(controlsFavoritingActivity7.getResources().getString(R.string.controls_favorite_load_none));
                                TextView textView5 = ControlsFavoritingActivity.this.subtitleView;
                                (textView5 != null ? textView5 : null).setVisibility(8);
                                return;
                            }
                            TextView textView6 = ControlsFavoritingActivity.this.statusText;
                            if (textView6 == null) {
                                textView6 = null;
                            }
                            textView6.setVisibility(8);
                            ControlsFavoritingActivity controlsFavoritingActivity8 = ControlsFavoritingActivity.this;
                            ManagementPageIndicator managementPageIndicator2 = controlsFavoritingActivity8.pageIndicator;
                            if (managementPageIndicator2 == null) {
                                managementPageIndicator2 = null;
                            }
                            managementPageIndicator2.setNumPages(controlsFavoritingActivity8.listOfStructures.size());
                            ManagementPageIndicator managementPageIndicator3 = ControlsFavoritingActivity.this.pageIndicator;
                            if (managementPageIndicator3 == null) {
                                managementPageIndicator3 = null;
                            }
                            managementPageIndicator3.setLocation(0.0f);
                            ControlsFavoritingActivity controlsFavoritingActivity9 = ControlsFavoritingActivity.this;
                            ManagementPageIndicator managementPageIndicator4 = controlsFavoritingActivity9.pageIndicator;
                            if (managementPageIndicator4 == null) {
                                managementPageIndicator4 = null;
                            }
                            managementPageIndicator4.setVisibility(controlsFavoritingActivity9.listOfStructures.size() <= 1 ? 4 : 0);
                            ControlsAnimations controlsAnimations = ControlsAnimations.INSTANCE;
                            ManagementPageIndicator managementPageIndicator5 = ControlsFavoritingActivity.this.pageIndicator;
                            if (managementPageIndicator5 == null) {
                                managementPageIndicator5 = null;
                            }
                            controlsAnimations.getClass();
                            Animator enterAnimation = ControlsAnimations.enterAnimation(managementPageIndicator5);
                            final ControlsFavoritingActivity controlsFavoritingActivity10 = ControlsFavoritingActivity.this;
                            enterAnimation.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$loadControls$1$1$2$1$1
                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator) {
                                    ManagementPageIndicator managementPageIndicator6 = ControlsFavoritingActivity.this.pageIndicator;
                                    if (managementPageIndicator6 == null) {
                                        managementPageIndicator6 = null;
                                    }
                                    if (managementPageIndicator6.getVisibility() == 0) {
                                        ControlsFavoritingActivity controlsFavoritingActivity11 = ControlsFavoritingActivity.this;
                                        if (controlsFavoritingActivity11.mTooltipManager != null) {
                                            int[] iArr = new int[2];
                                            ManagementPageIndicator managementPageIndicator7 = controlsFavoritingActivity11.pageIndicator;
                                            if (managementPageIndicator7 == null) {
                                                managementPageIndicator7 = null;
                                            }
                                            managementPageIndicator7.getLocationOnScreen(iArr);
                                            int i3 = iArr[0];
                                            ManagementPageIndicator managementPageIndicator8 = ControlsFavoritingActivity.this.pageIndicator;
                                            if (managementPageIndicator8 == null) {
                                                managementPageIndicator8 = null;
                                            }
                                            int width = (managementPageIndicator8.getWidth() / 2) + i3;
                                            int i4 = iArr[1];
                                            ManagementPageIndicator managementPageIndicator9 = ControlsFavoritingActivity.this.pageIndicator;
                                            int height = (managementPageIndicator9 != null ? managementPageIndicator9 : null).getHeight() + i4;
                                            TooltipManager tooltipManager = ControlsFavoritingActivity.this.mTooltipManager;
                                            if (tooltipManager != null) {
                                                tooltipManager.show(width, height);
                                            }
                                        }
                                    }
                                }
                            });
                            enterAnimation.start();
                            ?? r7 = ControlsFavoritingActivity.this.structurePager;
                            ControlsAnimations.enterAnimation(r7 != 0 ? r7 : null).start();
                        }
                    });
                }
            }, new Consumer() { // from class: com.android.systemui.controls.management.ControlsFavoritingActivity$loadControls$1$2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ControlsFavoritingActivity.this.cancelLoadRunnable = (Runnable) obj;
                }
            });
        }
        this.isPagerLoaded = true;
    }

    @Override // android.app.Activity
    public final void onStart() {
        super.onStart();
        ((ControlsListingControllerImpl) this.listingController).addCallback(this.listingCallback);
        ((UserTrackerImpl) this.userTracker).addCallback(this.userTrackerCallback, this.executor);
        getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, this.mOnBackInvokedCallback);
    }

    @Override // android.app.Activity
    public final void onStop() {
        super.onStop();
        ((ControlsListingControllerImpl) this.listingController).removeCallback(this.listingCallback);
        ((UserTrackerImpl) this.userTracker).removeCallback(this.userTrackerCallback);
        getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mOnBackInvokedCallback);
    }
}
