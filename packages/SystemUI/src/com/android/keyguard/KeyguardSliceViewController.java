package com.android.keyguard;

import android.app.PendingIntent;
import android.os.Handler;
import android.os.Trace;
import android.view.Display;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.slice.ArrayUtils;
import androidx.slice.Slice;
import androidx.slice.widget.ListContent;
import androidx.slice.widget.RowContent;
import com.android.keyguard.KeyguardSliceView;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.ViewController;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/* loaded from: classes.dex */
public class KeyguardSliceViewController extends ViewController implements Dumpable {
    public final ActivityStarter mActivityStarter;
    public Map mClickActions;
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass1 mConfigurationListener;
    public final DisplayTracker mDisplayTracker;
    public final DumpManager mDumpManager;
    public final AnonymousClass2 mObserver;
    public Slice mSlice;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.keyguard.KeyguardSliceViewController$1] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.keyguard.KeyguardSliceViewController$2] */
    public KeyguardSliceViewController(Handler handler, Handler handler2, KeyguardSliceView keyguardSliceView, ActivityStarter activityStarter, ConfigurationController configurationController, DumpManager dumpManager, DisplayTracker displayTracker) {
        super(keyguardSliceView);
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardSliceViewController.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onDensityOrFontScaleChanged() {
                ((KeyguardSliceView) ((ViewController) KeyguardSliceViewController.this).mView).onDensityOrFontScaleChanged();
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onThemeChanged() {
                KeyguardSliceView keyguardSliceView2 = (KeyguardSliceView) ((ViewController) KeyguardSliceViewController.this).mView;
                for (int i = 0; i < keyguardSliceView2.mRow.getChildCount(); i++) {
                    View childAt = keyguardSliceView2.mRow.getChildAt(i);
                    if (childAt instanceof KeyguardSliceView.KeyguardSliceTextView) {
                        ((KeyguardSliceView.KeyguardSliceTextView) childAt).setTextAppearance(R.style.TextAppearance_Keyguard_Secondary);
                    }
                }
            }
        };
        this.mObserver = new Observer() { // from class: com.android.keyguard.KeyguardSliceViewController.2
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                Slice slice = (Slice) obj;
                KeyguardSliceViewController keyguardSliceViewController = KeyguardSliceViewController.this;
                keyguardSliceViewController.mSlice = slice;
                keyguardSliceViewController.showSlice(slice);
            }
        };
        new View.OnClickListener() { // from class: com.android.keyguard.KeyguardSliceViewController.3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ActivityStarter activityStarter2;
                PendingIntent pendingIntent = (PendingIntent) ((HashMap) KeyguardSliceViewController.this.mClickActions).get(view);
                if (pendingIntent == null || (activityStarter2 = KeyguardSliceViewController.this.mActivityStarter) == null) {
                    return;
                }
                activityStarter2.startPendingIntentDismissingKeyguard(pendingIntent);
            }
        };
        this.mActivityStarter = activityStarter;
        this.mConfigurationController = configurationController;
        this.mDumpManager = dumpManager;
        this.mDisplayTracker = displayTracker;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("  mSlice: " + this.mSlice);
        printWriter.println("  mClickActions: " + this.mClickActions);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewAttached() {
        Display display = ((KeyguardSliceView) this.mView).getDisplay();
        if (display != null) {
            display.getDisplayId();
        }
        this.mDisplayTracker.getClass();
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        this.mDumpManager.registerNormalDumpable("KeyguardSliceViewCtrl@" + Integer.toHexString(hashCode()), this);
    }

    @Override // com.android.systemui.util.ViewController
    public final void onViewDetached() {
        this.mDisplayTracker.getClass();
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
        this.mDumpManager.unregisterDumpable("KeyguardSliceViewCtrl@" + Integer.toHexString(hashCode()));
    }

    public final void showSlice(Slice slice) {
        Trace.beginSection("KeyguardSliceViewController#showSlice");
        boolean z = false;
        if (slice == null) {
            KeyguardSliceView keyguardSliceView = (KeyguardSliceView) this.mView;
            keyguardSliceView.mTitle.setVisibility(8);
            keyguardSliceView.mRow.setVisibility(8);
            keyguardSliceView.mHasHeader = false;
            Trace.endSection();
            return;
        }
        ListContent listContent = new ListContent(slice);
        RowContent rowContent = listContent.mHeaderContent;
        if (rowContent != null && !ArrayUtils.contains(rowContent.mSliceItem.mHints, "list_item")) {
            z = true;
        }
        List list = (List) listContent.mRowItems.stream().filter(new KeyguardSliceViewController$$ExternalSyntheticLambda0()).collect(Collectors.toList());
        KeyguardSliceView keyguardSliceView2 = (KeyguardSliceView) this.mView;
        if (!z) {
            rowContent = null;
        }
        this.mClickActions = keyguardSliceView2.showSlice(rowContent, list);
        Trace.endSection();
    }
}
