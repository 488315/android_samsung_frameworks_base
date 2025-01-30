package com.android.systemui.statusbar;

import android.R;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.view.ContextThemeWrapper;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.model.KshData;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KshPresenter {
    public final ConfigurationController mConfigurationController;
    public final Context mContext;
    public boolean mIsNightModeOn;
    public KshData mKshData;
    public KshView mKshView;
    public final Configuration mLastConfig;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final C25581 mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.statusbar.KshPresenter.1
        @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
        public final void onConfigChanged(Configuration configuration) {
            KshPresenter kshPresenter = KshPresenter.this;
            if (kshPresenter.mLastConfig.orientation != configuration.orientation) {
                Dialog dialog = kshPresenter.mKshView.mKeyboardShortcutsDialog;
                if (dialog != null && dialog.isShowing()) {
                    KshView kshView = kshPresenter.mKshView;
                    List list = kshPresenter.mKshData.mKshGroups;
                    Dialog dialog2 = kshView.mKeyboardShortcutsDialog;
                    if (dialog2 != null) {
                        dialog2.dismiss();
                        kshView.mKeyboardShortcutsDialog.setOnKeyListener(null);
                        kshView.mKeyboardShortcutsDialog = null;
                    }
                    kshView.mHandler.post(new KshView$$ExternalSyntheticLambda1(kshView, list));
                }
            }
            boolean z = (configuration.uiMode & 32) != 0;
            if (kshPresenter.mIsNightModeOn != z) {
                kshPresenter.mIsNightModeOn = z;
                Dialog dialog3 = kshPresenter.mKshView.mKeyboardShortcutsDialog;
                if (dialog3 != null && dialog3.isShowing()) {
                    kshPresenter.mKshView.mContext = new ContextThemeWrapper(kshPresenter.mContext, kshPresenter.mIsNightModeOn ? R.style.Theme.DeviceDefault.Dialog : R.style.Theme.DeviceDefault.Light.Dialog);
                    KshView kshView2 = kshPresenter.mKshView;
                    List list2 = kshPresenter.mKshData.mKshGroups;
                    Dialog dialog4 = kshView2.mKeyboardShortcutsDialog;
                    if (dialog4 != null) {
                        dialog4.dismiss();
                        kshView2.mKeyboardShortcutsDialog.setOnKeyListener(null);
                        kshView2.mKeyboardShortcutsDialog = null;
                    }
                    kshView2.mHandler.post(new KshView$$ExternalSyntheticLambda1(kshView2, list2));
                }
            }
            kshPresenter.mLastConfig.updateFrom(configuration);
        }
    };
    public final C25592 mPogoKeyboardChangedReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.KshPresenter.2
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Dialog dialog = KshPresenter.this.mKshView.mKeyboardShortcutsDialog;
            if ((dialog != null && dialog.isShowing()) && "com.samsung.android.input.POGO_KEYBOARD_CHANGED".equals(intent.getAction())) {
                List list = KshPresenter.this.mKshData.mKshGroups;
                list.set(list.size() - 2, KshPresenter.this.mKshData.getSamsungSystemShortcuts());
                KshPresenter kshPresenter = KshPresenter.this;
                kshPresenter.mKshData.mKshGroups = list;
                KshView kshView = kshPresenter.mKshView;
                Dialog dialog2 = kshView.mKeyboardShortcutsDialog;
                if (dialog2 != null) {
                    dialog2.dismiss();
                    kshView.mKeyboardShortcutsDialog.setOnKeyListener(null);
                    kshView.mKeyboardShortcutsDialog = null;
                }
                kshView.mHandler.post(new KshView$$ExternalSyntheticLambda1(kshView, list));
            }
        }
    };

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.systemui.statusbar.KshPresenter$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.statusbar.KshPresenter$2] */
    public KshPresenter(Context context) {
        this.mContext = context;
        Configuration configuration = new Configuration();
        this.mLastConfig = configuration;
        configuration.updateFrom(context.getResources().getConfiguration());
        this.mIsNightModeOn = (configuration.uiMode & 32) != 0;
        this.mKshView = new KshView(new ContextThemeWrapper(context, this.mIsNightModeOn ? R.style.Theme.DeviceDefault.Dialog : R.style.Theme.DeviceDefault.Light.Dialog), this);
        this.mKshData = new KshData(context);
        this.mConfigurationController = (ConfigurationController) Dependency.get(ConfigurationController.class);
    }

    public KshData getKshData() {
        return this.mKshData;
    }

    public KshView getKshView() {
        return this.mKshView;
    }
}
