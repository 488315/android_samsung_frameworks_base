package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.BaseActivity;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.management.adapter.CustomStructureAdapter;
import com.android.systemui.controls.management.model.ReorderStructureModel;
import com.android.systemui.controls.management.model.ReorderWrapper;
import com.android.systemui.controls.p005ui.util.AUIFacade;
import com.android.systemui.controls.p005ui.util.ControlsUtil;
import com.android.systemui.controls.p005ui.util.LayoutUtil;
import com.android.systemui.controls.util.ControlsRuneWrapper;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlsReorderActivity extends BaseActivity {
    public final String TAG;
    public final AUIFacade auiFacade;
    public final BroadcastDispatcher broadcastDispatcher;
    public LinearLayout controlsListLayout;
    public final ControlsRuneWrapper controlsRuneWrapper;
    public final ControlsUtil controlsUtil;
    public final LayoutUtil layoutUtil;
    public ArrayList list;
    public LinearLayout noItemsLayout;
    public CustomStructureAdapter structureAdapter;
    public ReorderStructureModel structureModel;
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

    public ControlsReorderActivity(Executor executor, ControlsController controlsController, BroadcastDispatcher broadcastDispatcher, LayoutUtil layoutUtil, ControlsRuneWrapper controlsRuneWrapper, ControlsUtil controlsUtil, AUIFacade aUIFacade, UserTracker userTracker) {
        super(broadcastDispatcher, controlsController, userTracker, executor);
        this.broadcastDispatcher = broadcastDispatcher;
        this.layoutUtil = layoutUtil;
        this.controlsRuneWrapper = controlsRuneWrapper;
        this.controlsUtil = controlsUtil;
        this.auiFacade = aUIFacade;
        this.userTracker = userTracker;
        this.TAG = "ControlsReorderActivity";
    }

    @Override // com.android.systemui.controls.BaseActivity
    public final BroadcastDispatcher getBroadcastDispatcher() {
        return this.broadcastDispatcher;
    }

    public final ArrayList getCurrentStructureList() {
        ReorderStructureModel reorderStructureModel = this.structureModel;
        if (reorderStructureModel == null) {
            reorderStructureModel = null;
        }
        List list = reorderStructureModel.elements;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (obj instanceof ReorderWrapper) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            arrayList2.add(((ReorderWrapper) it.next()).displayName);
        }
        return arrayList2;
    }

    @Override // com.android.systemui.controls.BaseActivity
    public final String getTAG() {
        return this.TAG;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void onBackPressed() {
        ArrayList currentStructureList = getCurrentStructureList();
        ArrayList arrayList = this.list;
        if (arrayList == null) {
            arrayList = null;
        }
        if (!Intrinsics.areEqual(arrayList, currentStructureList)) {
            ArrayList arrayList2 = this.list;
            Log.d(this.TAG, "saveStructureOrder origin=" + (arrayList2 != null ? arrayList2 : null) + ", current=" + currentStructureList + " ");
            Intent intent = new Intent(this, (Class<?>) CustomControlsFavoritingActivity.class);
            intent.putCharSequenceArrayListExtra("OrderList", currentStructureList);
            setResult(-1, intent);
        }
        finish();
    }

    @Override // com.android.systemui.controls.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        ArrayList<CharSequence> arrayList;
        super.onCreate(bundle);
        if ((((ComponentName) getIntent().getParcelableExtra("android.intent.extra.COMPONENT_NAME")) != null ? Unit.INSTANCE : null) == null) {
            finish();
        }
        ArrayList arrayList2 = (ArrayList) getIntent().getSerializableExtra("extra_structure_lists");
        if (arrayList2 == null) {
            arrayList2 = new ArrayList();
        }
        this.list = arrayList2;
        setContentView(R.layout.controls_custom_management);
        setSupportActionBar((Toolbar) requireViewById(R.id.toolbar));
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            String string = getResources().getString(R.string.controls_reorder_title);
            supportActionBar.setTitle(string);
            setTitle(string);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        FrameLayout frameLayout = (FrameLayout) requireViewById(R.id.main_layout);
        this.layoutUtil.setLayoutWeightWidthPercentBasic(frameLayout, frameLayout.getContext().getResources().getFloat(R.integer.controls_basic_width_percentage));
        this.controlsListLayout = (LinearLayout) requireViewById(R.id.controls_list_layout);
        this.noItemsLayout = (LinearLayout) requireViewById(R.id.no_items_layout);
        ArrayList arrayList3 = this.list;
        if (arrayList3 == null) {
            arrayList3 = null;
        }
        Objects.toString(arrayList3);
        ArrayList arrayList4 = this.list;
        if (arrayList4 == null) {
            arrayList4 = null;
        }
        if (arrayList4.isEmpty()) {
            LinearLayout linearLayout = this.controlsListLayout;
            if (linearLayout == null) {
                linearLayout = null;
            }
            linearLayout.setVisibility(8);
            LinearLayout linearLayout2 = this.noItemsLayout;
            if (linearLayout2 == null) {
                linearLayout2 = null;
            }
            linearLayout2.setVisibility(0);
        } else {
            LinearLayout linearLayout3 = this.controlsListLayout;
            if (linearLayout3 == null) {
                linearLayout3 = null;
            }
            linearLayout3.setVisibility(0);
            LinearLayout linearLayout4 = this.noItemsLayout;
            if (linearLayout4 == null) {
                linearLayout4 = null;
            }
            linearLayout4.setVisibility(8);
        }
        ViewStub viewStub = (ViewStub) requireViewById(R.id.stub);
        viewStub.setLayoutResource(R.layout.controls_structure_page);
        viewStub.inflate();
        if (bundle != null) {
            arrayList = bundle.getCharSequenceArrayList("current_structure_list");
        } else {
            arrayList = this.list;
            if (arrayList == null) {
                arrayList = null;
            }
        }
        this.structureModel = new ReorderStructureModel(arrayList);
        this.structureAdapter = new CustomStructureAdapter(this.layoutUtil, this.controlsUtil, this.controlsRuneWrapper, this.auiFacade, ((UserTrackerImpl) this.userTracker).getUserId(), null, 32, null);
        RecyclerView recyclerView = (RecyclerView) requireViewById(R.id.listAll);
        CustomStructureAdapter customStructureAdapter = this.structureAdapter;
        if (customStructureAdapter == null) {
            customStructureAdapter = null;
        }
        recyclerView.setAdapter(customStructureAdapter);
        ReorderStructureModel reorderStructureModel = this.structureModel;
        if (reorderStructureModel == null) {
            reorderStructureModel = null;
        }
        reorderStructureModel.itemTouchHelper.attachToRecyclerView(recyclerView);
        CustomStructureAdapter customStructureAdapter2 = this.structureAdapter;
        if (customStructureAdapter2 == null) {
            customStructureAdapter2 = null;
        }
        ReorderStructureModel reorderStructureModel2 = this.structureModel;
        if (reorderStructureModel2 == null) {
            reorderStructureModel2 = null;
        }
        customStructureAdapter2.model = reorderStructureModel2;
        customStructureAdapter2.notifyDataSetChanged();
        ReorderStructureModel reorderStructureModel3 = this.structureModel;
        if (reorderStructureModel3 == null) {
            reorderStructureModel3 = null;
        }
        CustomStructureAdapter customStructureAdapter3 = this.structureAdapter;
        reorderStructureModel3.adapter = customStructureAdapter3 != null ? customStructureAdapter3 : null;
    }

    @Override // android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        onBackPressed();
        return true;
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putCharSequenceArrayList("current_structure_list", getCurrentStructureList());
        super.onSaveInstanceState(bundle);
    }
}
