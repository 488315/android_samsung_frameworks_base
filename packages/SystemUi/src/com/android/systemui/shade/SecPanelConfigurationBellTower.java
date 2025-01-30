package com.android.systemui.shade;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Debug;
import android.os.Handler;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.DeviceState;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecPanelConfigurationBellTower implements ConfigurationController.ConfigurationListener {
    public final CentralSurfaces mCentralSurfaces;
    public final Context mContext;
    public final PanelConfigurationWrapper mControllerConfiguration;
    public Consumer mDispatchConfigurationChangeConsumer;
    public final SecPanelExpansionStateNotifier mPanelExpansionStateNotifier;
    public final SecQSPanelResourcePicker mResourcePicker;
    public final PanelConfigurationWrapper mTmpConfiguration;
    public final PanelConfigurationWrapper mViewConfiguration;
    public int mWindowVisibility = 4;
    public int mCachedDisplayWidth = -1;
    public final Handler mHandler = (Handler) Dependency.get(Dependency.MAIN_HANDLER);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PanelConfigurationWrapper {
        public Configuration mConfiguration;
        public long mSeqNumber;

        public /* synthetic */ PanelConfigurationWrapper(int i) {
            this();
        }

        public final void setConfiguration(Configuration configuration) {
            this.mConfiguration = configuration;
            long j = -1;
            if (configuration != null) {
                String[] split = configuration.toString().split(" ");
                int length = split.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    String str = split[i];
                    if (str == null || !str.startsWith("s.")) {
                        i++;
                    } else {
                        String substring = str.substring(2);
                        j = substring.length() < 19 ? Long.parseLong(substring) : -2L;
                    }
                }
            }
            this.mSeqNumber = j;
        }

        private PanelConfigurationWrapper() {
            this.mSeqNumber = -1L;
        }
    }

    public SecPanelConfigurationBellTower(Context context, SecQSPanelResourcePicker secQSPanelResourcePicker, SecPanelExpansionStateNotifier secPanelExpansionStateNotifier, CentralSurfaces centralSurfaces, ConfigurationController configurationController) {
        this.mContext = context;
        this.mResourcePicker = secQSPanelResourcePicker;
        this.mPanelExpansionStateNotifier = secPanelExpansionStateNotifier;
        this.mCentralSurfaces = centralSurfaces;
        int i = 0;
        this.mControllerConfiguration = new PanelConfigurationWrapper(i);
        this.mViewConfiguration = new PanelConfigurationWrapper(i);
        this.mTmpConfiguration = new PanelConfigurationWrapper(i);
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        PanelConfigurationWrapper panelConfigurationWrapper = this.mTmpConfiguration;
        panelConfigurationWrapper.setConfiguration(configuration);
        printConfigurationStateLog("New Cs." + panelConfigurationWrapper.mSeqNumber, "newOri:" + configuration.orientation);
        PanelConfigurationWrapper panelConfigurationWrapper2 = this.mControllerConfiguration;
        if (panelConfigurationWrapper2.mSeqNumber < panelConfigurationWrapper.mSeqNumber) {
            panelConfigurationWrapper2.setConfiguration(panelConfigurationWrapper.mConfiguration);
        }
        if (this.mWindowVisibility == 0) {
            int i = this.mPanelExpansionStateNotifier.mModel.panelOpenState;
            boolean z = true;
            if (i != 2 && i != 1) {
                z = false;
            }
            if (z) {
                return;
            }
            CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) this.mCentralSurfaces;
            if (centralSurfacesImpl.isKeyguardShowing() && centralSurfacesImpl.mDeviceInteractive && shouldRingBell()) {
                printConfigurationStateLog("LsRune.KEYGUARD_FORCE_UPDATE_LATEST_CONFIGURATION", "");
                ringConfigurationBell();
            }
        }
    }

    public final void printConfigurationStateLog(String str, String str2) {
        StringBuilder sb = new StringBuilder(str);
        sb.append(" (cSeq.");
        PanelConfigurationWrapper panelConfigurationWrapper = this.mControllerConfiguration;
        sb.append(panelConfigurationWrapper.mSeqNumber);
        sb.append(" : vSeq.");
        PanelConfigurationWrapper panelConfigurationWrapper2 = this.mViewConfiguration;
        sb.append(panelConfigurationWrapper2.mSeqNumber);
        sb.append(") dState(dW:");
        Context context = this.mContext;
        sb.append(DeviceState.getDisplayWidth(context));
        sb.append(", dH:");
        sb.append(DeviceState.getDisplayHeight(context));
        sb.append(") rPicker(pW:");
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        secQSPanelResourcePicker.getClass();
        sb.append(SecQSPanelResourcePicker.getPanelWidth(context));
        sb.append(", pH:");
        sb.append(secQSPanelResourcePicker.getPanelHeight(context));
        sb.append(", adH:");
        sb.append(secQSPanelResourcePicker.getAvailableDisplayHeight(context));
        sb.append(", cH:");
        sb.append(SecQSPanelResourcePicker.isPortrait(context) ? secQSPanelResourcePicker.mCutoutHeight : secQSPanelResourcePicker.mCutoutHeightLandscape);
        sb.append(", nbH:");
        sb.append(secQSPanelResourcePicker.getNavBarHeight(context));
        sb.append(") Rotation(");
        sb.append(context.getResources().getConfiguration().windowConfiguration.getRotation());
        if (panelConfigurationWrapper.mConfiguration != null) {
            sb.append(", c:");
            sb.append(panelConfigurationWrapper.mConfiguration.windowConfiguration.getRotation());
        }
        if (panelConfigurationWrapper2.mConfiguration != null) {
            sb.append(", v:");
            sb.append(panelConfigurationWrapper2.mConfiguration.windowConfiguration.getRotation());
        }
        ExifInterface$$ExternalSyntheticOutline0.m36m(sb, ") ", str2, "SecPanelConfigurationBellTower");
    }

    public final void ringConfigurationBell() {
        long j = this.mViewConfiguration.mSeqNumber;
        PanelConfigurationWrapper panelConfigurationWrapper = this.mControllerConfiguration;
        if (j > panelConfigurationWrapper.mSeqNumber || this.mDispatchConfigurationChangeConsumer == null || panelConfigurationWrapper.mConfiguration == null) {
            return;
        }
        printConfigurationStateLog("Ring_Bell_!! from Cs." + panelConfigurationWrapper.mSeqNumber, panelConfigurationWrapper.mConfiguration.toString());
        Debug.getCallers(1);
        this.mDispatchConfigurationChangeConsumer.accept(panelConfigurationWrapper.mConfiguration);
    }

    public final boolean shouldRingBell() {
        int displayWidth = DeviceState.getDisplayWidth(this.mContext);
        long j = this.mControllerConfiguration.mSeqNumber;
        long j2 = this.mViewConfiguration.mSeqNumber;
        if (j > 0 && j == j2) {
            return false;
        }
        if (j > 0 && j > j2) {
            return true;
        }
        if (this.mCachedDisplayWidth == displayWidth) {
            return false;
        }
        this.mCachedDisplayWidth = displayWidth;
        return true;
    }
}
