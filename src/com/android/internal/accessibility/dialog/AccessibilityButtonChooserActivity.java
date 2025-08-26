package com.android.internal.accessibility.dialog;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
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
import com.android.internal.hidden_from_bootclasspath.android.provider.Flags;
import com.android.internal.widget.ResolverDrawerLayout;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class AccessibilityButtonChooserActivity extends Activity {
    public static final String EXTRA_TYPE_TO_CHOOSE = "TYPE";
    private final List<AccessibilityTarget> mTargets = new ArrayList();

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        int i;
        if (Settings.System.getIntForUser(getContentResolver(), Settings.System.SEM_ACCESS_CONTROL_ENABLED, 0, -2) != 1) {
            Intent intent = new Intent(AccessibilityManager.ACTION_CHOOSE_ACCESSIBILITY_BUTTON);
            String name = AccessibilitySamsungShortcutChooserActivity.class.getName();
            if (AccessibilityUtils.isFoldedLargeCoverScreen()) {
                i = 1;
            } else {
                i = AccessibilityUtils.isInDesktopWindowing(this) ? 2 : 0;
            }
            intent.setClassName("android", name);
            intent.putExtra("shortcutType", 1);
            intent.addFlags(805306368);
            startActivityAsUser(intent, ActivityOptions.makeBasic().setLaunchDisplayId(i).toBundle(), UserHandle.CURRENT);
        }
        finish();
        super.onCreate(bundle);
        setContentView(R.layout.accessibility_button_chooser);
        ResolverDrawerLayout resolverDrawerLayout = (ResolverDrawerLayout) findViewById(R.id.contentPanel);
        if (resolverDrawerLayout != null) {
            resolverDrawerLayout.setOnDismissedListener(new ResolverDrawerLayout.OnDismissedListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityButtonChooserActivity$$ExternalSyntheticLambda0
                @Override // com.android.internal.widget.ResolverDrawerLayout.OnDismissedListener
                public final void onDismissed() {
                    this.f$0.finish();
                }
            });
        }
        Settings.Secure.getString(getContentResolver(), Settings.Secure.ACCESSIBILITY_BUTTON_TARGET_COMPONENT);
        boolean zIsTouchExplorationEnabled = ((AccessibilityManager) getSystemService(AccessibilityManager.class)).isTouchExplorationEnabled();
        boolean z = 2 == getResources().getInteger(R.integer.config_navBarInteractionMode);
        int intExtra = Flags.a11yStandaloneGestureEnabled() ? getIntent().getIntExtra(EXTRA_TYPE_TO_CHOOSE, 1) : 1;
        if (z) {
            ((TextView) findViewById(R.id.accessibility_button_prompt_prologue)).setText(zIsTouchExplorationEnabled ? R.string.accessibility_gesture_3finger_prompt_text : R.string.accessibility_gesture_prompt_text);
            ((TextView) findViewById(R.id.accessibility_button_prompt)).setText(zIsTouchExplorationEnabled ? R.string.accessibility_gesture_3finger_instructional_text : R.string.accessibility_gesture_instructional_text);
        }
        this.mTargets.addAll(AccessibilityTargetHelper.getTargets(this, intExtra));
        GridView gridView = (GridView) findViewById(R.id.accessibility_button_chooser_grid);
        gridView.setAdapter((ListAdapter) new ButtonTargetAdapter(this.mTargets));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.internal.accessibility.dialog.AccessibilityButtonChooserActivity$$ExternalSyntheticLambda1
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i2, long j) {
                this.f$0.lambda$onCreate$0(adapterView, view, i2, j);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(AdapterView adapterView, View view, int i, long j) {
        String id = this.mTargets.get(i).getId();
        if (id.equals("com.android.server.accessibility.MagnificationController")) {
            id = AccessibilityShortcutController.MAGNIFICATION_COMPONENT_NAME.flattenToString();
        }
        AccessibilityStatsLogUtils.logAccessibilityButtonLongPressStatus(ComponentName.unflattenFromString(id));
        Settings.Secure.putString(getContentResolver(), Settings.Secure.ACCESSIBILITY_BUTTON_TARGET_COMPONENT, this.mTargets.get(i).getId());
        finish();
    }
}
