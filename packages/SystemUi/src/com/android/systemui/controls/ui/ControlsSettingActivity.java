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
import com.android.systemui.controls.ui.util.SALogger;
import com.android.systemui.settings.UserTracker;
import java.util.ArrayList;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlsSettingActivity extends BaseActivity {
    public final String TAG;
    public final BroadcastDispatcher broadcastDispatcher;
    public final ControlsFragmentFactory controlsFragmentFactory;
    public final LayoutUtil layoutUtil;
    public final SALogger saLogger;

    public ControlsSettingActivity(Executor executor, ControlsController controlsController, UserTracker userTracker, BroadcastDispatcher broadcastDispatcher, ControlsFragmentFactory controlsFragmentFactory, LayoutUtil layoutUtil, SALogger sALogger) {
        super(broadcastDispatcher, controlsController, userTracker, executor);
        this.broadcastDispatcher = broadcastDispatcher;
        this.controlsFragmentFactory = controlsFragmentFactory;
        this.layoutUtil = layoutUtil;
        this.saLogger = sALogger;
        this.TAG = "ControlsSettingActivity";
    }

    @Override // com.android.systemui.controls.BaseActivity
    public final BroadcastDispatcher getBroadcastDispatcher() {
        return this.broadcastDispatcher;
    }

    @Override // com.android.systemui.controls.BaseActivity
    public final String getTAG() {
        return this.TAG;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public final void onBackPressed() {
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
        ColorStateList valueOf = ColorStateList.valueOf(toolbar.getResources().getColor(R.color.control_custom_primary_text, getTheme()));
        toolbar.mTitleTextColor = valueOf;
        AppCompatTextView appCompatTextView = toolbar.mTitleTextView;
        if (appCompatTextView != null) {
            appCompatTextView.setTextColor(valueOf);
        }
        setSupportActionBar(toolbar);
        ScrollView scrollView = (ScrollView) requireViewById(R.id.main_layout);
        this.layoutUtil.setLayoutWeightWidthPercentBasic(scrollView, scrollView.getContext().getResources().getFloat(R.integer.controls_basic_width_percentage));
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
            backStackRecord.commit();
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
