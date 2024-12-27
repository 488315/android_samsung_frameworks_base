package com.android.server.desktopmode;

import android.content.Context;
import android.net.shared.InitialConfiguration$$ExternalSyntheticOutline0;
import android.util.IndentingPrintWriter;

import com.android.server.IoThread;

import com.samsung.android.desktopmode.DesktopModeFeature;
import com.samsung.android.desktopmode.SemDesktopModeState;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DisplayPortStateManager {
    public final Context mContext;
    public int mDisplayPortState;
    public boolean mDualModeEnabled;
    public boolean mExternalDisplayModeDual;
    public final AnonymousClass2 mExternalDisplayModeListener;
    public boolean mHdmiAutoEnterEnabled;
    public final AnonymousClass2 mHdmiAutoEnterListener;
    public boolean mHdmiDisplayConnected;
    public boolean mHighResolutionsForExternalEnabled;
    public final AnonymousClass2 mHighResolutionsForExternalListener;
    public final Object mLock;
    public boolean mStandaloneModeEnabled;
    public final AnonymousClass1 mStateListener;

    /* renamed from: -$$Nest$msetDualModeEnabled, reason: not valid java name */
    public static void m402$$Nest$msetDualModeEnabled(
            DisplayPortStateManager displayPortStateManager, boolean z) {
        synchronized (displayPortStateManager.mLock) {
            try {
                if (displayPortStateManager.mDualModeEnabled != z) {
                    displayPortStateManager.mDualModeEnabled = z;
                    boolean z2 = displayPortStateManager.mHdmiDisplayConnected;
                    if (z2 && z) {
                        displayPortStateManager.setDisplayPortStateLocked(true, false);
                        displayPortStateManager.setDisplayPortStateLocked(true, true);
                    } else if (z2) {
                        displayPortStateManager.setDisplayPortStateLocked(false, false);
                    } else {
                        displayPortStateManager.setDisplayPortStateLocked(
                                displayPortStateManager.getSettingState(), false);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$msetHighResolutionsForExternalEnabledLocked, reason: not valid java name */
    public static void m403$$Nest$msetHighResolutionsForExternalEnabledLocked(
            DisplayPortStateManager displayPortStateManager, boolean z) {
        displayPortStateManager.getClass();
        Log.d(
                "[DMS]DisplayPortStateManager",
                "setHighResolutionsForExternalEnabledLocked(), enabled=" + z);
        if (displayPortStateManager.mHdmiDisplayConnected
                || displayPortStateManager.mDualModeEnabled) {
            return;
        }
        if (z) {
            displayPortStateManager.setDisplayPortStateLocked(false, false);
        } else {
            displayPortStateManager.setDisplayPortStateLocked(
                    displayPortStateManager.getSettingState(), false);
        }
    }

    /* renamed from: -$$Nest$msetStandaloneModeEnabled, reason: not valid java name */
    public static void m404$$Nest$msetStandaloneModeEnabled(
            DisplayPortStateManager displayPortStateManager, boolean z) {
        synchronized (displayPortStateManager.mLock) {
            try {
                if (displayPortStateManager.mStandaloneModeEnabled != z) {
                    displayPortStateManager.mStandaloneModeEnabled = z;
                    displayPortStateManager.setDisplayPortStateLocked(
                            displayPortStateManager.getSettingState(), false);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public DisplayPortStateManager(
            Context context, IStateManager iStateManager, SettingsHelper settingsHelper) {
        StateManager.StateListener stateListener =
                new StateManager
                        .StateListener() { // from class:
                                           // com.android.server.desktopmode.DisplayPortStateManager.1
                    @Override // com.android.server.desktopmode.StateManager.StateListener
                    public final void onDualModeSetDesktopMode(boolean z) {
                        if (z) {
                            return;
                        }
                        DisplayPortStateManager.m402$$Nest$msetDualModeEnabled(
                                DisplayPortStateManager.this, false);
                    }

                    @Override // com.android.server.desktopmode.StateManager.StateListener
                    public final void onDualModeStopLoadingScreen(boolean z) {
                        if (z) {
                            DisplayPortStateManager.m402$$Nest$msetDualModeEnabled(
                                    DisplayPortStateManager.this, true);
                        }
                    }

                    @Override // com.android.server.desktopmode.StateManager.StateListener
                    public final void onExternalDisplayConnectionChanged(
                            StateManager.InternalState internalState) {
                        boolean isHdmiConnected = internalState.isHdmiConnected();
                        synchronized (DisplayPortStateManager.this.mLock) {
                            DisplayPortStateManager displayPortStateManager =
                                    DisplayPortStateManager.this;
                            if (displayPortStateManager.mHdmiDisplayConnected != isHdmiConnected) {
                                displayPortStateManager.mHdmiDisplayConnected = isHdmiConnected;
                                if (!isHdmiConnected && !displayPortStateManager.mDualModeEnabled) {
                                    displayPortStateManager.setDisplayPortStateLocked(
                                            displayPortStateManager.getSettingState(), false);
                                }
                            }
                        }
                    }

                    @Override // com.android.server.desktopmode.StateManager.StateListener
                    public final void onSetDesktopMode(boolean z) {
                        if (z) {
                            return;
                        }
                        DisplayPortStateManager.m404$$Nest$msetStandaloneModeEnabled(
                                DisplayPortStateManager.this, false);
                    }

                    @Override // com.android.server.desktopmode.StateManager.StateListener
                    public final void onStopLoadingScreen(boolean z) {
                        if (z) {
                            DisplayPortStateManager.m404$$Nest$msetStandaloneModeEnabled(
                                    DisplayPortStateManager.this, true);
                        }
                    }

                    @Override // com.android.server.desktopmode.StateManager.StateListener
                    public final void onUserChanged(StateManager.InternalState internalState) {
                        synchronized (DisplayPortStateManager.this.mLock) {
                            try {
                                DisplayPortStateManager displayPortStateManager =
                                        DisplayPortStateManager.this;
                                displayPortStateManager.mHdmiAutoEnterEnabled =
                                        DesktopModeSettings.getSettingsAsUser(
                                                displayPortStateManager.mContext
                                                        .getContentResolver(),
                                                "hdmi_auto_enter",
                                                false,
                                                DesktopModeSettings.sCurrentUserId);
                                DisplayPortStateManager displayPortStateManager2 =
                                        DisplayPortStateManager.this;
                                SemDesktopModeState semDesktopModeState =
                                        internalState.mDesktopModeState;
                                boolean z = true;
                                displayPortStateManager2.mDualModeEnabled =
                                        semDesktopModeState.getDisplayType() == 102
                                                && semDesktopModeState.enabled == 4
                                                && semDesktopModeState.state == 0;
                                DisplayPortStateManager displayPortStateManager3 =
                                        DisplayPortStateManager.this;
                                SemDesktopModeState semDesktopModeState2 =
                                        internalState.mDesktopModeState;
                                if (semDesktopModeState2.getDisplayType() != 101
                                        || semDesktopModeState2.enabled != 4
                                        || semDesktopModeState2.state != 0) {
                                    z = false;
                                }
                                displayPortStateManager3.mStandaloneModeEnabled = z;
                                DisplayPortStateManager displayPortStateManager4 =
                                        DisplayPortStateManager.this;
                                displayPortStateManager4.mExternalDisplayModeDual =
                                        "dual"
                                                .equals(
                                                        DesktopModeSettings.getSettingsAsUser(
                                                                displayPortStateManager4.mContext
                                                                        .getContentResolver(),
                                                                "external_display_mode",
                                                                "dual",
                                                                DesktopModeSettings
                                                                        .sCurrentUserId));
                                DisplayPortStateManager.this.mHdmiDisplayConnected =
                                        internalState.isHdmiConnected();
                                if (!DesktopModeSettings.getSettingsAsUser(
                                        DisplayPortStateManager.this.mContext.getContentResolver(),
                                        "high_resolutions_for_external",
                                        false,
                                        DesktopModeSettings.sCurrentUserId)) {
                                    DisplayPortStateManager displayPortStateManager5 =
                                            DisplayPortStateManager.this;
                                    displayPortStateManager5.setDisplayPortStateLocked(
                                            displayPortStateManager5.getSettingState(), false);
                                }
                                DisplayPortStateManager displayPortStateManager6 =
                                        DisplayPortStateManager.this;
                                displayPortStateManager6.mHighResolutionsForExternalEnabled =
                                        DesktopModeSettings.getSettingsAsUser(
                                                displayPortStateManager6.mContext
                                                        .getContentResolver(),
                                                "high_resolutions_for_external",
                                                false,
                                                DesktopModeSettings.sCurrentUserId);
                                DisplayPortStateManager displayPortStateManager7 =
                                        DisplayPortStateManager.this;
                                DisplayPortStateManager
                                        .m403$$Nest$msetHighResolutionsForExternalEnabledLocked(
                                                displayPortStateManager7,
                                                displayPortStateManager7
                                                        .mHighResolutionsForExternalEnabled);
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                };
        SettingsHelper.OnSettingChangedListener onSettingChangedListener =
                new SettingsHelper.OnSettingChangedListener(
                        this,
                        0) { // from class: com.android.server.desktopmode.DisplayPortStateManager.2
                    public final /* synthetic */ int $r8$classId;
                    public final /* synthetic */ DisplayPortStateManager this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super("hdmi_auto_enter");
                        this.$r8$classId = r2;
                        switch (r2) {
                            case 1:
                                this.this$0 = this;
                                super("high_resolutions_for_external");
                                break;
                            case 2:
                                this.this$0 = this;
                                super("external_display_mode");
                                break;
                            default:
                                this.this$0 = this;
                                break;
                        }
                    }

                    @Override // com.android.server.desktopmode.SettingsHelper.OnSettingChangedListener
                    public final void onSettingChanged(String str) {
                        switch (this.$r8$classId) {
                            case 0:
                                boolean parseBoolean = Boolean.parseBoolean(str);
                                synchronized (this.this$0.mLock) {
                                    DisplayPortStateManager displayPortStateManager = this.this$0;
                                    if (displayPortStateManager.mHdmiAutoEnterEnabled
                                            != parseBoolean) {
                                        displayPortStateManager.mHdmiAutoEnterEnabled =
                                                parseBoolean;
                                        if (!displayPortStateManager.mHdmiDisplayConnected
                                                && !displayPortStateManager.mDualModeEnabled) {
                                            displayPortStateManager.setDisplayPortStateLocked(
                                                    displayPortStateManager.getSettingState(),
                                                    false);
                                        }
                                    }
                                }
                                return;
                            case 1:
                                boolean parseBoolean2 = Boolean.parseBoolean(str);
                                synchronized (this.this$0.mLock) {
                                    try {
                                        DisplayPortStateManager displayPortStateManager2 =
                                                this.this$0;
                                        if (displayPortStateManager2
                                                        .mHighResolutionsForExternalEnabled
                                                != parseBoolean2) {
                                            displayPortStateManager2
                                                            .mHighResolutionsForExternalEnabled =
                                                    parseBoolean2;
                                            DisplayPortStateManager
                                                    .m403$$Nest$msetHighResolutionsForExternalEnabledLocked(
                                                            displayPortStateManager2,
                                                            parseBoolean2);
                                        }
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                                return;
                            default:
                                boolean equals = "dual".equals(str);
                                synchronized (this.this$0.mLock) {
                                    DisplayPortStateManager displayPortStateManager3 = this.this$0;
                                    if (displayPortStateManager3.mExternalDisplayModeDual
                                            != equals) {
                                        displayPortStateManager3.mExternalDisplayModeDual = equals;
                                        if (!displayPortStateManager3.mHdmiDisplayConnected
                                                && !displayPortStateManager3.mDualModeEnabled) {
                                            displayPortStateManager3.setDisplayPortStateLocked(
                                                    displayPortStateManager3.getSettingState(),
                                                    false);
                                        }
                                    }
                                }
                                return;
                        }
                    }
                };
        SettingsHelper.OnSettingChangedListener onSettingChangedListener2 =
                new SettingsHelper.OnSettingChangedListener(
                        this,
                        1) { // from class: com.android.server.desktopmode.DisplayPortStateManager.2
                    public final /* synthetic */ int $r8$classId;
                    public final /* synthetic */ DisplayPortStateManager this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super("hdmi_auto_enter");
                        this.$r8$classId = r2;
                        switch (r2) {
                            case 1:
                                this.this$0 = this;
                                super("high_resolutions_for_external");
                                break;
                            case 2:
                                this.this$0 = this;
                                super("external_display_mode");
                                break;
                            default:
                                this.this$0 = this;
                                break;
                        }
                    }

                    @Override // com.android.server.desktopmode.SettingsHelper.OnSettingChangedListener
                    public final void onSettingChanged(String str) {
                        switch (this.$r8$classId) {
                            case 0:
                                boolean parseBoolean = Boolean.parseBoolean(str);
                                synchronized (this.this$0.mLock) {
                                    DisplayPortStateManager displayPortStateManager = this.this$0;
                                    if (displayPortStateManager.mHdmiAutoEnterEnabled
                                            != parseBoolean) {
                                        displayPortStateManager.mHdmiAutoEnterEnabled =
                                                parseBoolean;
                                        if (!displayPortStateManager.mHdmiDisplayConnected
                                                && !displayPortStateManager.mDualModeEnabled) {
                                            displayPortStateManager.setDisplayPortStateLocked(
                                                    displayPortStateManager.getSettingState(),
                                                    false);
                                        }
                                    }
                                }
                                return;
                            case 1:
                                boolean parseBoolean2 = Boolean.parseBoolean(str);
                                synchronized (this.this$0.mLock) {
                                    try {
                                        DisplayPortStateManager displayPortStateManager2 =
                                                this.this$0;
                                        if (displayPortStateManager2
                                                        .mHighResolutionsForExternalEnabled
                                                != parseBoolean2) {
                                            displayPortStateManager2
                                                            .mHighResolutionsForExternalEnabled =
                                                    parseBoolean2;
                                            DisplayPortStateManager
                                                    .m403$$Nest$msetHighResolutionsForExternalEnabledLocked(
                                                            displayPortStateManager2,
                                                            parseBoolean2);
                                        }
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                                return;
                            default:
                                boolean equals = "dual".equals(str);
                                synchronized (this.this$0.mLock) {
                                    DisplayPortStateManager displayPortStateManager3 = this.this$0;
                                    if (displayPortStateManager3.mExternalDisplayModeDual
                                            != equals) {
                                        displayPortStateManager3.mExternalDisplayModeDual = equals;
                                        if (!displayPortStateManager3.mHdmiDisplayConnected
                                                && !displayPortStateManager3.mDualModeEnabled) {
                                            displayPortStateManager3.setDisplayPortStateLocked(
                                                    displayPortStateManager3.getSettingState(),
                                                    false);
                                        }
                                    }
                                }
                                return;
                        }
                    }
                };
        SettingsHelper.OnSettingChangedListener onSettingChangedListener3 =
                new SettingsHelper.OnSettingChangedListener(
                        this,
                        2) { // from class: com.android.server.desktopmode.DisplayPortStateManager.2
                    public final /* synthetic */ int $r8$classId;
                    public final /* synthetic */ DisplayPortStateManager this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super("hdmi_auto_enter");
                        this.$r8$classId = r2;
                        switch (r2) {
                            case 1:
                                this.this$0 = this;
                                super("high_resolutions_for_external");
                                break;
                            case 2:
                                this.this$0 = this;
                                super("external_display_mode");
                                break;
                            default:
                                this.this$0 = this;
                                break;
                        }
                    }

                    @Override // com.android.server.desktopmode.SettingsHelper.OnSettingChangedListener
                    public final void onSettingChanged(String str) {
                        switch (this.$r8$classId) {
                            case 0:
                                boolean parseBoolean = Boolean.parseBoolean(str);
                                synchronized (this.this$0.mLock) {
                                    DisplayPortStateManager displayPortStateManager = this.this$0;
                                    if (displayPortStateManager.mHdmiAutoEnterEnabled
                                            != parseBoolean) {
                                        displayPortStateManager.mHdmiAutoEnterEnabled =
                                                parseBoolean;
                                        if (!displayPortStateManager.mHdmiDisplayConnected
                                                && !displayPortStateManager.mDualModeEnabled) {
                                            displayPortStateManager.setDisplayPortStateLocked(
                                                    displayPortStateManager.getSettingState(),
                                                    false);
                                        }
                                    }
                                }
                                return;
                            case 1:
                                boolean parseBoolean2 = Boolean.parseBoolean(str);
                                synchronized (this.this$0.mLock) {
                                    try {
                                        DisplayPortStateManager displayPortStateManager2 =
                                                this.this$0;
                                        if (displayPortStateManager2
                                                        .mHighResolutionsForExternalEnabled
                                                != parseBoolean2) {
                                            displayPortStateManager2
                                                            .mHighResolutionsForExternalEnabled =
                                                    parseBoolean2;
                                            DisplayPortStateManager
                                                    .m403$$Nest$msetHighResolutionsForExternalEnabledLocked(
                                                            displayPortStateManager2,
                                                            parseBoolean2);
                                        }
                                    } catch (Throwable th) {
                                        throw th;
                                    }
                                }
                                return;
                            default:
                                boolean equals = "dual".equals(str);
                                synchronized (this.this$0.mLock) {
                                    DisplayPortStateManager displayPortStateManager3 = this.this$0;
                                    if (displayPortStateManager3.mExternalDisplayModeDual
                                            != equals) {
                                        displayPortStateManager3.mExternalDisplayModeDual = equals;
                                        if (!displayPortStateManager3.mHdmiDisplayConnected
                                                && !displayPortStateManager3.mDualModeEnabled) {
                                            displayPortStateManager3.setDisplayPortStateLocked(
                                                    displayPortStateManager3.getSettingState(),
                                                    false);
                                        }
                                    }
                                }
                                return;
                        }
                    }
                };
        this.mLock = new Object();
        this.mDisplayPortState = -1;
        this.mHdmiDisplayConnected = false;
        this.mHdmiAutoEnterEnabled = false;
        this.mDualModeEnabled = false;
        this.mStandaloneModeEnabled = false;
        this.mHighResolutionsForExternalEnabled = false;
        this.mExternalDisplayModeDual = false;
        this.mContext = context;
        ((StateManager) iStateManager).registerListener(stateListener);
        settingsHelper.registerListener(onSettingChangedListener);
        settingsHelper.registerListener(onSettingChangedListener2);
        settingsHelper.registerListener(onSettingChangedListener3);
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            indentingPrintWriter.println("Current DisplayPortStateManager state:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("mDisplayPortState=" + this.mDisplayPortState);
            indentingPrintWriter.println("mHdmiDisplayConnected=" + this.mHdmiDisplayConnected);
            indentingPrintWriter.println("mHdmiAutoEnterEnabled=" + this.mHdmiAutoEnterEnabled);
            indentingPrintWriter.println("mDualModeEnabled=" + this.mDualModeEnabled);
            indentingPrintWriter.println("mStandaloneModeEnabled=" + this.mStandaloneModeEnabled);
            indentingPrintWriter.println(
                    "mExternalDisplayModeDual=" + this.mExternalDisplayModeDual);
            indentingPrintWriter.println(
                    "mHighResolutionsForExternalEnabled="
                            + this.mHighResolutionsForExternalEnabled);
            indentingPrintWriter.decreaseIndent();
        }
    }

    public final boolean getSettingState() {
        return DesktopModeFeature.SUPPORT_STANDALONE
                ? this.mExternalDisplayModeDual
                        && (this.mHdmiAutoEnterEnabled || this.mStandaloneModeEnabled)
                : this.mHdmiAutoEnterEnabled;
    }

    public final void setDisplayPortStateLocked(boolean z, boolean z2) {
        int i = this.mHighResolutionsForExternalEnabled ? 0 : ((z ? 1 : 0) << 4) | (z2 ? 1 : 0);
        if (this.mDisplayPortState != i) {
            this.mDisplayPortState = i;
            boolean z3 = DesktopModeFeature.DEBUG;
            if (z3) {
                Log.d(
                        "[DMS]DisplayPortStateManager",
                        "setDisplayPortState(), state=0x" + Integer.toHexString(i));
            }
            final boolean z4 = z || z2;
            final String num = Integer.toString(i);
            if (z3) {
                Log.d(
                        "[DMS]Utils",
                        "writeFile(), path=/sys/class/dp_sec/dex, value=" + num + ", async=" + z4);
            }
            Runnable runnable =
                    new Runnable() { // from class:
                                     // com.android.server.desktopmode.Utils$$ExternalSyntheticLambda0
                        public final /* synthetic */ String f$0 = "/sys/class/dp_sec/dex";

                        @Override // java.lang.Runnable
                        public final void run() {
                            String str = this.f$0;
                            String str2 = num;
                            boolean z5 = z4;
                            File file = new File(str);
                            if (file.exists()) {
                                try {
                                    BufferedWriter bufferedWriter =
                                            new BufferedWriter(
                                                    new OutputStreamWriter(
                                                            new FileOutputStream(file),
                                                            StandardCharsets.UTF_8));
                                    try {
                                        bufferedWriter.write(str2);
                                        bufferedWriter.close();
                                    } finally {
                                    }
                                } catch (IOException e) {
                                    StringBuilder m =
                                            InitialConfiguration$$ExternalSyntheticOutline0.m(
                                                    "Failed to writeFile(), path=",
                                                    str,
                                                    ", value=",
                                                    str2,
                                                    ", async=");
                                    m.append(z5);
                                    Log.e("[DMS]Utils", m.toString(), e);
                                }
                            }
                            if (DesktopModeFeature.DEBUG) {
                                StringBuilder m2 =
                                        InitialConfiguration$$ExternalSyntheticOutline0.m(
                                                "writeFile(), path=",
                                                str,
                                                ", value=",
                                                str2,
                                                ", async=");
                                m2.append(z5);
                                m2.append(", returning");
                                Log.d("[DMS]Utils", m2.toString());
                            }
                        }
                    };
            if (z4) {
                IoThread.getHandler().post(runnable);
            } else {
                runnable.run();
            }
        }
    }
}
