package com.android.systemui.controls.ui;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ScrollView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentManagerImpl;
import com.android.systemui.R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.BaseActivity;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.ui.fragment.ControlsFragmentFactory;
import com.android.systemui.controls.ui.fragment.SettingFragment;
import com.android.systemui.controls.ui.util.LayoutUtil;
import com.android.systemui.controls.util.SALogger;
import com.android.systemui.settings.UserTracker;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class ControlsSettingActivity extends BaseActivity {
    public final BroadcastDispatcher broadcastDispatcher;
    public final ControlsFragmentFactory controlsFragmentFactory;
    public final LayoutUtil layoutUtil;
    public final SALogger saLogger;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public ControlsSettingActivity(Executor executor, ControlsController controlsController, UserTracker userTracker, BroadcastDispatcher broadcastDispatcher, ControlsFragmentFactory controlsFragmentFactory, LayoutUtil layoutUtil, SALogger sALogger) {
        super(broadcastDispatcher, controlsController, userTracker, executor);
        this.broadcastDispatcher = broadcastDispatcher;
        this.controlsFragmentFactory = controlsFragmentFactory;
        this.layoutUtil = layoutUtil;
        this.saLogger = sALogger;
    }

    @Override // com.android.systemui.controls.BaseActivity
    public final BroadcastDispatcher getBroadcastDispatcher() {
        return this.broadcastDispatcher;
    }

    @Override // com.android.systemui.controls.BaseActivity
    public final String getTag() {
        return "ControlsSettingActivity";
    }

    @Override // com.android.systemui.controls.BaseActivity
    public final void onBackKeyPressed() {
        ArrayList arrayList = getSupportFragmentManager().mBackStack;
        if ((arrayList != null ? arrayList.size() : 0) <= 1) {
            finish();
            return;
        }
        FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.getClass();
        supportFragmentManager.enqueueAction(new FragmentManager.PopBackStackState(null, -1, 0), false);
    }

    @Override // com.android.systemui.controls.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        getSupportFragmentManager().mFragmentFactory = this.controlsFragmentFactory;
        super.onCreate(bundle);
        setContentView(R.layout.activity_controls_settings);
        Toolbar toolbar = (Toolbar) requireViewById(R.id.toolbar);
        ColorStateList valueOf = ColorStateList.valueOf(toolbar.getResources().getColor(R.color.sec_control_primary_text, getTheme()));
        toolbar.mTitleTextColor = valueOf;
        AppCompatTextView appCompatTextView = toolbar.mTitleTextView;
        if (appCompatTextView != null) {
            appCompatTextView.setTextColor(valueOf);
        }
        setSupportActionBar(toolbar);
        ScrollView scrollView = (ScrollView) requireViewById(R.id.main_layout);
        Intrinsics.checkNotNull(scrollView);
        float f = scrollView.getContext().getResources().getFloat(R.integer.control_basic_width_percentage);
        LayoutUtil layoutUtil = this.layoutUtil;
        layoutUtil.setLayoutWeightWidthPercentBasic(layoutUtil.getWidthPercentBasic(f), scrollView);
        if (bundle == null) {
            FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
            supportFragmentManager.getClass();
            BackStackRecord backStackRecord = new BackStackRecord(supportFragmentManager);
            backStackRecord.replace(R.id.fragment_container, new SettingFragment(this.saLogger), null);
            if (!backStackRecord.mAllowAddToBackStack) {
                throw new IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
            }
            backStackRecord.mAddToBackStack = true;
            backStackRecord.mName = null;
            backStackRecord.commitInternal(false);
        }
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
