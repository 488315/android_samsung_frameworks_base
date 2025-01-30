package com.android.systemui.p016qs.bar;

import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.p016qs.QSBackupRestoreManager;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.SettingsHelper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BarBackUpRestoreHelper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public final SettingsHelper settingsHelper;
    public final QSBackupRestoreManager qsBackupRestoreManager = (QSBackupRestoreManager) Dependency.get(QSBackupRestoreManager.class);
    public final TunerService tunerService = (TunerService) Dependency.get(TunerService.class);

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

    public BarBackUpRestoreHelper(Context context, SettingsHelper settingsHelper) {
        this.context = context;
        this.settingsHelper = settingsHelper;
    }
}
