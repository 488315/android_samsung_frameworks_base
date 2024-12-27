package com.android.systemui.volume.view.standard;

import android.app.Dialog;
import android.content.Context;
import android.os.PowerManager;
import android.view.MotionEvent;
import android.view.ViewGroup;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.volume.VolumeDependencyBase;
import com.android.systemui.volume.config.SystemConfigImpl;
import com.android.systemui.volume.purefunction.VolumePanelLayout;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.store.VolumePanelStore;
import com.android.systemui.volume.util.ContextUtils;
import com.android.systemui.volume.util.HandlerWrapper;
import com.android.systemui.volume.util.IDisplayManagerWrapper;
import com.android.systemui.volume.util.PluginAODManagerWrapper;
import com.android.systemui.volume.util.PowerManagerWrapper;
import com.android.systemui.volume.view.VolumePanelMotion;
import com.samsung.systemui.splugins.extensions.VolumePanelStateExt;
import com.samsung.systemui.splugins.volume.VolumeInfraMediator;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import kotlin.Lazy;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class VolumePanelWindow extends Dialog implements VolumeObserver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final VolumeInfraMediator infraMediator;
    public final LogWrapper log;
    public final VolumePanelView panelView;
    public final Lazy store$delegate;
    public final Lazy storeInteractor$delegate;
    public final SystemConfigImpl systemConfig;
    public final VolumeDependencyBase volDeps;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[VolumePanelState.StateType.values().length];
            try {
                iArr[VolumePanelState.StateType.STATE_SHOW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_DUAL_PLAY_MODE_CHANGED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_OPEN_THEME_CHANGED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_DISMISS_VOLUME_PANEL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_DISMISS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_FOLDER_STATE_CHANGED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_ORIENTATION_CHANGED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_COVER_STATE_CHANGED.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_EXPAND_STATE_CHANGED.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public VolumePanelWindow(com.android.systemui.volume.VolumeDependencyBase r9) {
        /*
            Method dump skipped, instructions count: 505
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.view.standard.VolumePanelWindow.<init>(com.android.systemui.volume.VolumeDependencyBase):void");
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        this.panelView.dispatchTouchEvent(motionEvent);
        return true;
    }

    public final void dispose() {
        ((StoreInteractor) this.storeInteractor$delegate.getValue()).dispose();
        VolumePanelView volumePanelView = this.panelView;
        volumePanelView.storeInteractor.dispose();
        VolumePanelMotion volumePanelMotion = volumePanelView.volumePanelMotion;
        if (volumePanelMotion == null) {
            volumePanelMotion = null;
        }
        volumePanelMotion.storeInteractor.dispose();
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x003a, code lost:
    
        if (r1.isNavigationBarGestureWhileHidden() != false) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getBaseHeight$1() {
        /*
            r3 = this;
            android.content.Context r0 = r3.getContext()
            int r0 = com.android.systemui.volume.util.ContextUtils.getDisplayHeight(r0)
            com.android.systemui.volume.config.SystemConfigImpl r1 = r3.systemConfig
            boolean r1 = r1.isTablet()
            if (r1 != 0) goto L47
            android.content.Context r1 = r3.getContext()
            boolean r1 = com.android.systemui.volume.util.ContextUtils.isScreenWideMobileDevice(r1)
            if (r1 != 0) goto L47
            android.content.Context r1 = r3.getContext()
            boolean r1 = com.android.systemui.volume.util.ContextUtils.isLandscape(r1)
            if (r1 == 0) goto L3d
            com.android.systemui.Dependency r1 = com.android.systemui.Dependency.sDependency
            java.lang.Class<com.android.systemui.util.SettingsHelper> r2 = com.android.systemui.util.SettingsHelper.class
            java.lang.Object r1 = r1.getDependencyInner(r2)
            com.android.systemui.util.SettingsHelper r1 = (com.android.systemui.util.SettingsHelper) r1
            int r2 = com.android.systemui.volume.util.SettingsHelperExt.$r8$clinit
            boolean r2 = r1.isNavigationBarGestureHintEnabled()
            if (r2 == 0) goto L3d
            boolean r1 = r1.isNavigationBarGestureWhileHidden()
            if (r1 == 0) goto L3d
            goto L47
        L3d:
            android.content.Context r1 = r3.getContext()
            boolean r1 = com.android.systemui.volume.util.ContextUtils.isLandscape(r1)
            if (r1 != 0) goto L53
        L47:
            android.content.Context r3 = r3.getContext()
            r1 = 17105609(0x10502c9, float:2.443024E-38)
            int r3 = com.android.systemui.volume.util.ContextUtils.getDimenInt(r1, r3)
            int r0 = r0 + r3
        L53:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.view.standard.VolumePanelWindow.getBaseHeight$1():int");
    }

    public final float getSeekBarY(int i) {
        float f;
        float dimenInt = i - ContextUtils.getDimenInt(R.dimen.volume_seekbar_height, getContext());
        if (!this.systemConfig.isTablet() && ContextUtils.isScreenWideMobileDevice(getContext()) && ContextUtils.isLandscape(getContext())) {
            dimenInt -= i / 2.0f;
        } else if (!this.systemConfig.isTablet() && !ContextUtils.isLandscape(getContext())) {
            if (BasicRune.FOLDABLE_TYPE_FLIP) {
                VolumePanelLayout.INSTANCE.getClass();
                f = VolumePanelLayout.VERTICAL_PADDING_TOP_FOR_FLIP_RATIO;
            } else if (ContextUtils.isScreenWideMobileDevice(getContext())) {
                VolumePanelLayout.INSTANCE.getClass();
                f = VolumePanelLayout.VERTICAL_WIDE_SCREEN_TOP_RATIO;
            } else {
                VolumePanelLayout.INSTANCE.getClass();
                f = VolumePanelLayout.VERTICAL_PADDING_TOP_RATIO;
            }
            return dimenInt * f;
        }
        return dimenInt / 2.0f;
    }

    public final VolumePanelStore getStore$2() {
        return (VolumePanelStore) this.store$delegate.getValue();
    }

    public final void observeStore() {
        ((StoreInteractor) this.storeInteractor$delegate.getValue()).observeStore();
        VolumePanelView volumePanelView = this.panelView;
        volumePanelView.storeInteractor.observeStore();
        VolumePanelMotion volumePanelMotion = volumePanelView.volumePanelMotion;
        if (volumePanelMotion == null) {
            volumePanelMotion = null;
        }
        VolumePanelStore volumePanelStore = volumePanelView.store;
        VolumePanelStore volumePanelStore2 = volumePanelStore != null ? volumePanelStore : null;
        Context context = volumePanelView.getContext();
        volumePanelMotion.storeInteractor.store = volumePanelStore2;
        volumePanelMotion.context = context;
    }

    /* JADX WARN: Code restructure failed: missing block: B:106:0x02b7, code lost:
    
        if (r9.getResources().getConfiguration().orientation == 1) goto L119;
     */
    @Override // com.samsung.systemui.splugins.volume.VolumeObserver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onChanged(java.lang.Object r20) {
        /*
            Method dump skipped, instructions count: 1948
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.view.standard.VolumePanelWindow.onChanged(java.lang.Object):void");
    }

    @Override // android.app.Dialog
    public final void onStart() {
        super.onStart();
        this.log.d("VolumePanelWindow", "onStart");
    }

    @Override // android.app.Dialog
    public final void onStop() {
        super.onStop();
        this.log.d("VolumePanelWindow", "onStop : panelState.isExpanded=" + getStore$2().currentState.isExpanded());
        VolumePanelView volumePanelView = this.panelView;
        volumePanelView.getClass();
        if (BasicRune.VOLUME_REFRESH_RATE_FIXED) {
            HandlerWrapper handlerWrapper = volumePanelView.handlerWrapper;
            if (handlerWrapper == null) {
                handlerWrapper = null;
            }
            IDisplayManagerWrapper iDisplayManagerWrapper = volumePanelView.iDisplayManagerWrapper;
            if (iDisplayManagerWrapper == null) {
                iDisplayManagerWrapper = null;
            }
            handlerWrapper.post(iDisplayManagerWrapper.refreshRateLimitOffRunnable);
        }
        VolumePanelState volumePanelState = volumePanelView.panelState;
        if (volumePanelState == null) {
            volumePanelState = null;
        }
        if (VolumePanelStateExt.isAODVolumePanel(volumePanelState)) {
            PowerManagerWrapper powerManagerWrapper = volumePanelView.powerManagerWrapper;
            if (powerManagerWrapper == null) {
                powerManagerWrapper = null;
            }
            PowerManager.WakeLock wakeLock = powerManagerWrapper.wakeLock;
            if (wakeLock != null) {
                wakeLock.release();
            }
            powerManagerWrapper.wakeLock = null;
            PluginAODManagerWrapper pluginAODManagerWrapper = volumePanelView.pluginAODManagerWrapper;
            if (pluginAODManagerWrapper == null) {
                pluginAODManagerWrapper = null;
            }
            pluginAODManagerWrapper.getClass();
            PluginAODManagerWrapper.requestAODVolumePanel(false);
        }
        ViewGroup viewGroup = volumePanelView.rowContainer;
        (viewGroup != null ? viewGroup : null).removeAllViews();
        if (getStore$2().currentState.isExpanded()) {
            return;
        }
        ((StoreInteractor) this.storeInteractor$delegate.getValue()).sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_DISMISS_VOLUME_PANEL).build(), true);
    }
}
