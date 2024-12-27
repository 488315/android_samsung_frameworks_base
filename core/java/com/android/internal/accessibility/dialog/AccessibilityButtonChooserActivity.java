package com.android.internal.accessibility.dialog;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.internal.accessibility.util.AccessibilityStatsLogUtils;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.internal.widget.ResolverDrawerLayout;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class AccessibilityButtonChooserActivity extends Activity {
    private final List<AccessibilityTarget> mTargets = new ArrayList();

    @Override // android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        int i;
        int i2;
        int displayId;
        boolean accessControlEnabled = Settings.System.getIntForUser(getContentResolver(), Settings.System.SEM_ACCESS_CONTROL_ENABLED, 0, -2) == 1;
        if (!accessControlEnabled) {
            Intent intent = new Intent(AccessibilityManager.ACTION_CHOOSE_ACCESSIBILITY_BUTTON);
            String chooserClassName = AccessibilitySamsungShortcutChooserActivity.class.getName();
            if (AccessibilityUtils.isFoldedLargeCoverScreen()) {
                displayId = 1;
            } else if (AccessibilityUtils.isDexDualMonitorDisplay(this)) {
                displayId = 2;
            } else {
                displayId = 0;
            }
            intent.setClassName("android", chooserClassName);
            intent.putExtra("shortcutType", 1);
            intent.addFlags(805306368);
            Bundle bundle = ActivityOptions.makeBasic().setLaunchDisplayId(displayId).toBundle();
            startActivityAsUser(intent, bundle, UserHandle.CURRENT);
        }
        finish();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accessibility_button_chooser);
        ResolverDrawerLayout rdl = (ResolverDrawerLayout) findViewById(R.id.contentPanel);
        if (rdl != null) {
            rdl.setOnDismissedListener(new ResolverDrawerLayout.OnDismissedListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityButtonChooserActivity$$ExternalSyntheticLambda0
                @Override // com.android.internal.widget.ResolverDrawerLayout.OnDismissedListener
                public final void onDismissed() {
                    AccessibilityButtonChooserActivity.this.finish();
                }
            });
        }
        String component = Settings.Secure.getString(getContentResolver(), Settings.Secure.ACCESSIBILITY_BUTTON_TARGET_COMPONENT);
        AccessibilityManager accessibilityManager = (AccessibilityManager) getSystemService(AccessibilityManager.class);
        boolean isTouchExploreOn = accessibilityManager.isTouchExplorationEnabled();
        boolean isGestureNavigateEnabled = 2 == getResources().getInteger(R.integer.config_navBarInteractionMode);
        if (isGestureNavigateEnabled) {
            TextView promptPrologue = (TextView) findViewById(R.id.accessibility_button_prompt_prologue);
            if (isTouchExploreOn) {
                i2 = R.string.accessibility_gesture_3finger_prompt_text;
            } else {
                i2 = R.string.accessibility_gesture_prompt_text;
            }
            promptPrologue.setText(i2);
        }
        if (TextUtils.isEmpty(component)) {
            TextView prompt = (TextView) findViewById(R.id.accessibility_button_prompt);
            if (isGestureNavigateEnabled) {
                if (isTouchExploreOn) {
                    i = R.string.accessibility_gesture_3finger_instructional_text;
                } else {
                    i = R.string.accessibility_gesture_instructional_text;
                }
                prompt.setText(i);
            }
            prompt.setVisibility(0);
        }
        this.mTargets.addAll(AccessibilityTargetHelper.getTargets(this, 1));
        GridView gridview = (GridView) findViewById(R.id.accessibility_button_chooser_grid);
        gridview.setAdapter((ListAdapter) new ButtonTargetAdapter(this.mTargets));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityButtonChooserActivity$$ExternalSyntheticLambda1
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i3, long j) {
                AccessibilityButtonChooserActivity.this.lambda$onCreate$0(adapterView, view, i3, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(AdapterView parent, View view, int position, long id) {
        String name = this.mTargets.get(position).getId();
        if (name.equals("com.android.server.accessibility.MagnificationController")) {
            name = AccessibilityShortcutController.MAGNIFICATION_COMPONENT_NAME.flattenToString();
        }
        ComponentName componentName = ComponentName.unflattenFromString(name);
        AccessibilityStatsLogUtils.logAccessibilityButtonLongPressStatus(componentName);
        Settings.Secure.putString(getContentResolver(), Settings.Secure.ACCESSIBILITY_BUTTON_TARGET_COMPONENT, this.mTargets.get(position).getId());
        finish();
    }
}
