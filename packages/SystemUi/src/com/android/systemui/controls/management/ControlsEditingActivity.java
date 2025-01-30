package com.android.systemui.controls.management;

import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedCallback;
import androidx.activity.ComponentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.android.systemui.controls.CustomIconCache;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.ControlsControllerImpl$replaceFavoritesForStructure$1;
import com.android.systemui.controls.controller.Favorites;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.management.ControlAdapter;
import com.android.systemui.controls.ui.ControlsActivity;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsRelease;
import com.android.systemui.flags.Flags;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.util.concurrency.ExecutorImpl;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ControlsEditingActivity extends ComponentActivity {
    public static final int EMPTY_TEXT_ID;
    public static final int SUBTITLE_ID;
    public View addControls;
    public ComponentName component;
    public final ControlsControllerImpl controller;
    public final CustomIconCache customIconCache;
    public boolean isFromFavoriting;
    public final boolean isNewFlowEnabled;
    public final Executor mainExecutor;
    public FavoritesModel model;
    public View saveButton;
    public CharSequence structure;
    public TextView subtitle;
    public final UserTracker userTracker;
    public final UserTracker.Callback userTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.controls.management.ControlsEditingActivity$userTrackerCallback$1
        public final int startingUser;

        {
            this.startingUser = ControlsEditingActivity.this.controller.getCurrentUserId();
        }

        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onUserChanged(int i, Context context) {
            if (i != this.startingUser) {
                ControlsEditingActivity controlsEditingActivity = ControlsEditingActivity.this;
                ((UserTrackerImpl) controlsEditingActivity.userTracker).removeCallback(this);
                controlsEditingActivity.finish();
            }
        }
    };
    public final ControlsEditingActivity$mOnBackInvokedCallback$1 mOnBackInvokedCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.controls.management.ControlsEditingActivity$mOnBackInvokedCallback$1
        @Override // android.window.OnBackInvokedCallback
        public final void onBackInvoked() {
            ControlsEditingActivity.this.onBackPressed();
        }
    };
    public final ControlsEditingActivity$favoritesModelCallback$1 favoritesModelCallback = new ControlsEditingActivity$favoritesModelCallback$1(this);

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
        SUBTITLE_ID = R.string.controls_favorite_rearrange;
        EMPTY_TEXT_ID = R.string.controls_favorite_removed;
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.controls.management.ControlsEditingActivity$mOnBackInvokedCallback$1] */
    public ControlsEditingActivity(FeatureFlags featureFlags, Executor executor, ControlsControllerImpl controlsControllerImpl, UserTracker userTracker, CustomIconCache customIconCache) {
        this.mainExecutor = executor;
        this.controller = controlsControllerImpl;
        this.userTracker = userTracker;
        this.customIconCache = customIconCache;
        this.isNewFlowEnabled = ((FeatureFlagsRelease) featureFlags).isEnabled(Flags.CONTROLS_MANAGEMENT_NEW_FLOWS);
    }

    public final void animateExitAndFinish() {
        ControlsAnimations.exitAnimation((ViewGroup) requireViewById(R.id.controls_management_root), new Runnable() { // from class: com.android.systemui.controls.management.ControlsEditingActivity$animateExitAndFinish$1
            @Override // java.lang.Runnable
            public final void run() {
                ControlsEditingActivity.this.finish();
            }
        }).start();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void onBackPressed() {
        animateExitAndFinish();
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        Unit unit;
        Unit unit2;
        super.onCreate(bundle);
        ComponentName componentName = (ComponentName) getIntent().getParcelableExtra("android.intent.extra.COMPONENT_NAME");
        if (componentName != null) {
            this.component = componentName;
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            finish();
        }
        this.isFromFavoriting = getIntent().getBooleanExtra("extra_from_favoriting", false);
        CharSequence charSequenceExtra = getIntent().getCharSequenceExtra("extra_structure");
        if (charSequenceExtra != null) {
            this.structure = charSequenceExtra;
            unit2 = Unit.INSTANCE;
        } else {
            unit2 = null;
        }
        if (unit2 == null) {
            finish();
        }
        setContentView(R.layout.controls_management);
        ControlsAnimations controlsAnimations = ControlsAnimations.INSTANCE;
        ViewGroup viewGroup = (ViewGroup) requireViewById(R.id.controls_management_root);
        Window window = getWindow();
        Intent intent = getIntent();
        controlsAnimations.getClass();
        ((ComponentActivity) this).mLifecycleRegistry.addObserver(new ControlsAnimations$observerForAnimations$1(intent, viewGroup, true, window));
        ViewStub viewStub = (ViewStub) requireViewById(R.id.stub);
        viewStub.setLayoutResource(R.layout.controls_management_editing);
        viewStub.inflate();
        TextView textView = (TextView) requireViewById(R.id.title);
        CharSequence charSequence = this.structure;
        if (charSequence == null) {
            charSequence = null;
        }
        textView.setText(charSequence);
        CharSequence charSequence2 = this.structure;
        setTitle(charSequence2 != null ? charSequence2 : null);
        TextView textView2 = (TextView) requireViewById(R.id.subtitle);
        textView2.setText(SUBTITLE_ID);
        this.subtitle = textView2;
        View requireViewById = requireViewById(R.id.addControls);
        final Button button = (Button) requireViewById;
        button.setEnabled(true);
        button.setVisibility(this.isNewFlowEnabled ? 0 : 8);
        button.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.management.ControlsEditingActivity$bindButtons$1$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                View view2 = ControlsEditingActivity.this.saveButton;
                if (view2 == null) {
                    view2 = null;
                }
                if (view2.isEnabled()) {
                    Toast.makeText(ControlsEditingActivity.this.getApplicationContext(), R.string.controls_favorite_toast_no_changes, 0).show();
                }
                ControlsEditingActivity controlsEditingActivity = ControlsEditingActivity.this;
                if (controlsEditingActivity.isFromFavoriting) {
                    controlsEditingActivity.animateExitAndFinish();
                    return;
                }
                Intent intent2 = new Intent(button.getContext(), (Class<?>) ControlsFavoritingActivity.class);
                ControlsEditingActivity controlsEditingActivity2 = ControlsEditingActivity.this;
                CharSequence charSequence3 = controlsEditingActivity2.structure;
                if (charSequence3 == null) {
                    charSequence3 = null;
                }
                intent2.putExtra("extra_structure", charSequence3);
                ComponentName componentName2 = controlsEditingActivity2.component;
                intent2.putExtra("android.intent.extra.COMPONENT_NAME", componentName2 != null ? componentName2 : null);
                intent2.putExtra("extra_app_label", controlsEditingActivity2.getIntent().getCharSequenceExtra("extra_app_label"));
                intent2.putExtra("extra_source", (byte) 2);
                controlsEditingActivity.startActivity(intent2, ActivityOptions.makeSceneTransitionAnimation(ControlsEditingActivity.this, new Pair[0]).toBundle());
            }
        });
        this.addControls = requireViewById;
        View requireViewById2 = requireViewById(R.id.done);
        Button button2 = (Button) requireViewById2;
        button2.setEnabled(this.isFromFavoriting);
        button2.setText(R.string.save);
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.controls.management.ControlsEditingActivity$bindButtons$2$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ControlsEditingActivity controlsEditingActivity = ControlsEditingActivity.this;
                int i = ControlsEditingActivity.SUBTITLE_ID;
                controlsEditingActivity.getClass();
                ComponentName componentName2 = controlsEditingActivity.component;
                if (componentName2 == null) {
                    componentName2 = null;
                }
                CharSequence charSequence3 = controlsEditingActivity.structure;
                if (charSequence3 == null) {
                    charSequence3 = null;
                }
                FavoritesModel favoritesModel = controlsEditingActivity.model;
                StructureInfo structureInfo = new StructureInfo(componentName2, charSequence3, (favoritesModel != null ? favoritesModel : null).getFavorites());
                ControlsControllerImpl controlsControllerImpl = controlsEditingActivity.controller;
                if (controlsControllerImpl.confirmAvailability()) {
                    ((ExecutorImpl) controlsControllerImpl.executor).execute(new ControlsControllerImpl$replaceFavoritesForStructure$1(structureInfo, controlsControllerImpl));
                }
                ControlsEditingActivity.this.startActivity(new Intent(ControlsEditingActivity.this.getApplicationContext(), (Class<?>) ControlsActivity.class), ActivityOptions.makeSceneTransitionAnimation(ControlsEditingActivity.this, new Pair[0]).toBundle());
                ControlsEditingActivity.this.animateExitAndFinish();
            }
        });
        this.saveButton = requireViewById2;
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        ((UserTrackerImpl) this.userTracker).removeCallback(this.userTrackerCallback);
        super.onDestroy();
    }

    @Override // android.app.Activity
    public final void onStart() {
        Object obj;
        List list;
        super.onStart();
        ComponentName componentName = this.component;
        if (componentName == null) {
            componentName = null;
        }
        CharSequence charSequence = this.structure;
        if (charSequence == null) {
            charSequence = null;
        }
        this.controller.getClass();
        Favorites favorites = Favorites.INSTANCE;
        StructureInfo structureInfo = new StructureInfo(componentName, charSequence, EmptyList.INSTANCE);
        favorites.getClass();
        Iterator it = Favorites.getStructuresForComponent(structureInfo.componentName).iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            } else {
                obj = it.next();
                if (Intrinsics.areEqual(((StructureInfo) obj).structure, structureInfo.structure)) {
                    break;
                }
            }
        }
        StructureInfo structureInfo2 = (StructureInfo) obj;
        if (structureInfo2 == null || (list = structureInfo2.controls) == null) {
            list = EmptyList.INSTANCE;
        }
        ComponentName componentName2 = this.component;
        if (componentName2 == null) {
            componentName2 = null;
        }
        this.model = new FavoritesModel(this.customIconCache, componentName2, list, this.favoritesModelCallback);
        float f = getResources().getFloat(R.dimen.control_card_elevation);
        final RecyclerView recyclerView = (RecyclerView) requireViewById(R.id.list);
        recyclerView.setAlpha(0.0f);
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) this.userTracker;
        final ControlAdapter controlAdapter = new ControlAdapter(f, userTrackerImpl.getUserId());
        controlAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() { // from class: com.android.systemui.controls.management.ControlsEditingActivity$setUpList$adapter$1$1
            public boolean hasAnimated;

            @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public final void onChanged() {
                if (this.hasAnimated) {
                    return;
                }
                this.hasAnimated = true;
                ControlsAnimations.INSTANCE.getClass();
                ControlsAnimations.enterAnimation(RecyclerView.this).start();
            }
        });
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.controls_card_margin);
        MarginItemDecorator marginItemDecorator = new MarginItemDecorator(dimensionPixelSize, dimensionPixelSize);
        ControlAdapter.Companion companion = ControlAdapter.Companion;
        Resources resources = getResources();
        companion.getClass();
        final int findMaxColumns = ControlAdapter.Companion.findMaxColumns(resources);
        recyclerView.setAdapter(controlAdapter);
        final Context context = recyclerView.getContext();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(findMaxColumns, context) { // from class: com.android.systemui.controls.management.ControlsEditingActivity$setUpList$1$1
            @Override // androidx.recyclerview.widget.GridLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
            public final int getRowCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
                int rowCountForAccessibility = super.getRowCountForAccessibility(recycler, state);
                return rowCountForAccessibility > 0 ? rowCountForAccessibility - 1 : rowCountForAccessibility;
            }
        };
        gridLayoutManager.mSpanSizeLookup = new GridLayoutManager.SpanSizeLookup() { // from class: com.android.systemui.controls.management.ControlsEditingActivity$setUpList$1$2$1
            @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
            public final int getSpanSize(int i) {
                boolean z = false;
                ControlAdapter controlAdapter2 = ControlAdapter.this;
                if (controlAdapter2 != null && controlAdapter2.getItemViewType(i) == 1) {
                    z = true;
                }
                if (z) {
                    return 1;
                }
                return findMaxColumns;
            }
        };
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(marginItemDecorator);
        FavoritesModel favoritesModel = this.model;
        if (favoritesModel == null) {
            favoritesModel = null;
        }
        controlAdapter.model = favoritesModel;
        controlAdapter.notifyDataSetChanged();
        FavoritesModel favoritesModel2 = this.model;
        (favoritesModel2 == null ? null : favoritesModel2).adapter = controlAdapter;
        new ItemTouchHelper((favoritesModel2 != null ? favoritesModel2 : null).itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        userTrackerImpl.addCallback(this.userTrackerCallback, this.mainExecutor);
        getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, this.mOnBackInvokedCallback);
    }

    @Override // android.app.Activity
    public final void onStop() {
        super.onStop();
        ((UserTrackerImpl) this.userTracker).removeCallback(this.userTrackerCallback);
        getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mOnBackInvokedCallback);
    }
}
