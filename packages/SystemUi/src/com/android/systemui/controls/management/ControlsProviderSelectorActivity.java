package com.android.systemui.controls.management;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import androidx.activity.ComponentActivity;
import androidx.lifecycle.LifecycleRegistry;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.p005ui.ControlsActivity;
import com.android.systemui.controls.panels.AuthorizedPanelsRepository;
import com.android.systemui.controls.panels.AuthorizedPanelsRepositoryImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.collections.EmptySet;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlsProviderSelectorActivity extends ComponentActivity {
    public final AuthorizedPanelsRepository authorizedPanelsRepository;
    public final Executor backExecutor;
    public boolean backShouldExit;
    public final ControlsController controlsController;
    public Dialog dialog;
    public final Executor executor;
    public final ControlsListingController listingController;
    public final PanelConfirmationDialogFactory panelConfirmationDialogFactory;
    public RecyclerView recyclerView;
    public final UserTracker userTracker;
    public final UserTracker.Callback userTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.controls.management.ControlsProviderSelectorActivity$userTrackerCallback$1
        public final int startingUser;

        {
            this.startingUser = ((ControlsListingControllerImpl) ControlsProviderSelectorActivity.this.listingController).currentUserId;
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            if (i != this.startingUser) {
                ControlsProviderSelectorActivity controlsProviderSelectorActivity = ControlsProviderSelectorActivity.this;
                ((UserTrackerImpl) controlsProviderSelectorActivity.userTracker).removeCallback(this);
                controlsProviderSelectorActivity.finish();
            }
        }
    };
    public final ControlsProviderSelectorActivity$mOnBackInvokedCallback$1 mOnBackInvokedCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.controls.management.ControlsProviderSelectorActivity$mOnBackInvokedCallback$1
        @Override // android.window.OnBackInvokedCallback
        public final void onBackInvoked() {
            ControlsProviderSelectorActivity.this.onBackPressed();
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

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.controls.management.ControlsProviderSelectorActivity$mOnBackInvokedCallback$1] */
    public ControlsProviderSelectorActivity(Executor executor, Executor executor2, ControlsListingController controlsListingController, ControlsController controlsController, UserTracker userTracker, AuthorizedPanelsRepository authorizedPanelsRepository, PanelConfirmationDialogFactory panelConfirmationDialogFactory) {
        this.executor = executor;
        this.backExecutor = executor2;
        this.listingController = controlsListingController;
        this.controlsController = controlsController;
        this.userTracker = userTracker;
        this.authorizedPanelsRepository = authorizedPanelsRepository;
        this.panelConfirmationDialogFactory = panelConfirmationDialogFactory;
    }

    /* renamed from: animateExitAndFinish$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public void m118x8067cc31() {
        ControlsAnimations.exitAnimation((ViewGroup) requireViewById(R.id.controls_management_root), new Runnable() { // from class: com.android.systemui.controls.management.ControlsProviderSelectorActivity$animateExitAndFinish$1
            @Override // java.lang.Runnable
            public final void run() {
                ControlsProviderSelectorActivity.this.finish();
            }
        }).start();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void onBackPressed() {
        if (!this.backShouldExit) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(getApplicationContext(), (Class<?>) ControlsActivity.class));
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, new Pair[0]).toBundle());
        }
        m118x8067cc31();
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.controls_management);
        ControlsAnimations controlsAnimations = ControlsAnimations.INSTANCE;
        ViewGroup viewGroup = (ViewGroup) requireViewById(R.id.controls_management_root);
        Window window = getWindow();
        Intent intent = getIntent();
        controlsAnimations.getClass();
        ((ComponentActivity) this).mLifecycleRegistry.addObserver(new ControlsAnimations$observerForAnimations$1(intent, viewGroup, true, window));
        ViewStub viewStub = (ViewStub) requireViewById(R.id.stub);
        viewStub.setLayoutResource(R.layout.controls_management_apps);
        viewStub.inflate();
        RecyclerView recyclerView = (RecyclerView) requireViewById(R.id.list);
        this.recyclerView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        TextView textView = (TextView) requireViewById(R.id.title);
        textView.setText(textView.getResources().getText(R.string.controls_providers_title));
        Button button = (Button) requireViewById(R.id.other_apps);
        button.setVisibility(0);
        button.setText(android.R.string.cancel);
        button.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.management.ControlsProviderSelectorActivity$onCreate$3$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ControlsProviderSelectorActivity.this.onBackPressed();
            }
        });
        requireViewById(R.id.done).setVisibility(8);
        this.backShouldExit = getIntent().getBooleanExtra("back_should_exit", false);
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        ((UserTrackerImpl) this.userTracker).removeCallback(this.userTrackerCallback);
        super.onDestroy();
    }

    @Override // android.app.Activity
    public final void onStart() {
        super.onStart();
        ((UserTrackerImpl) this.userTracker).addCallback(this.userTrackerCallback, this.executor);
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView == null) {
            recyclerView = null;
        }
        recyclerView.setAlpha(0.0f);
        RecyclerView recyclerView2 = this.recyclerView;
        RecyclerView recyclerView3 = recyclerView2 != null ? recyclerView2 : null;
        Executor executor = this.backExecutor;
        Executor executor2 = this.executor;
        LifecycleRegistry lifecycleRegistry = ((ComponentActivity) this).mLifecycleRegistry;
        ControlsListingController controlsListingController = this.listingController;
        LayoutInflater from = LayoutInflater.from(this);
        ControlsProviderSelectorActivity$onStart$1 controlsProviderSelectorActivity$onStart$1 = new ControlsProviderSelectorActivity$onStart$1(this);
        FavoritesRenderer favoritesRenderer = new FavoritesRenderer(getResources(), new ControlsProviderSelectorActivity$onStart$2(this.controlsController));
        Resources resources = getResources();
        Set<String> stringSet = ((AuthorizedPanelsRepositoryImpl) this.authorizedPanelsRepository).instantiateSharedPrefs().getStringSet("authorized_panels", EmptySet.INSTANCE);
        Intrinsics.checkNotNull(stringSet);
        AppAdapter appAdapter = new AppAdapter(executor, executor2, lifecycleRegistry, controlsListingController, from, controlsProviderSelectorActivity$onStart$1, favoritesRenderer, resources, stringSet);
        appAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() { // from class: com.android.systemui.controls.management.ControlsProviderSelectorActivity$onStart$3$1
            public boolean hasAnimated;

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public final void onChanged() {
                if (this.hasAnimated) {
                    return;
                }
                this.hasAnimated = true;
                ControlsAnimations controlsAnimations = ControlsAnimations.INSTANCE;
                RecyclerView recyclerView4 = ControlsProviderSelectorActivity.this.recyclerView;
                if (recyclerView4 == null) {
                    recyclerView4 = null;
                }
                controlsAnimations.getClass();
                ControlsAnimations.enterAnimation(recyclerView4).start();
            }
        });
        recyclerView3.setAdapter(appAdapter);
        getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, this.mOnBackInvokedCallback);
    }

    @Override // android.app.Activity
    public final void onStop() {
        super.onStop();
        ((UserTrackerImpl) this.userTracker).removeCallback(this.userTrackerCallback);
        getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mOnBackInvokedCallback);
        Dialog dialog = this.dialog;
        if (dialog != null) {
            dialog.cancel();
        }
    }
}
