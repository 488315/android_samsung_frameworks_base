package com.android.systemui.shade;

import android.os.Handler;
import android.util.Log;
import com.android.systemui.statusbar.StatusBarState;
import java.text.DecimalFormat;
import java.util.function.Consumer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SecPanelExpansionStateModel {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Handler handler;
    public final Consumer notifyPanelState;
    public boolean panelExpanded;
    public float panelFirstDepthFraction;
    public int panelOpenState;
    public int panelPrvOpenState;
    public float panelSecondDepthFraction;
    public boolean wasUnlockedWhilePanelOpening;
    public int statusBarState = 1;
    public boolean isLcdOn = true;

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

    public SecPanelExpansionStateModel(Consumer<Integer> consumer, Handler handler) {
        this.notifyPanelState = consumer;
        this.handler = handler;
    }

    public final void setLcdOn(boolean z) {
        if (this.isLcdOn != z) {
            this.isLcdOn = z;
            boolean z2 = this.statusBarState != 1 && this.panelOpenState == 2;
            if (z || z2) {
                return;
            }
            updatePanelOpenState();
        }
    }

    public final void setStatusBarState(int i) {
        int i2 = this.statusBarState;
        if (i2 != i) {
            this.statusBarState = i;
            setWasUnlockedWhilePanelOpening(i == 0 && this.panelOpenState != 0);
            if (i2 != 1 || i != 0) {
                updatePanelOpenState();
            } else if (this.panelFirstDepthFraction >= 1.0f) {
                Log.d("SecPanelExpansionStateModel", " @ POST updatePanelOpenState(KEYGUARD > SHADE) " + this);
                this.handler.post(new Runnable() { // from class: com.android.systemui.shade.SecPanelExpansionStateModel$statusBarState$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Log.d("SecPanelExpansionStateModel", " @ RUN updatePanelOpenState(KEYGUARD > SHADE) " + SecPanelExpansionStateModel.this);
                        SecPanelExpansionStateModel secPanelExpansionStateModel = SecPanelExpansionStateModel.this;
                        int i3 = SecPanelExpansionStateModel.$r8$clinit;
                        secPanelExpansionStateModel.updatePanelOpenState();
                    }
                });
            }
        }
    }

    public final void setWasUnlockedWhilePanelOpening(boolean z) {
        if (this.wasUnlockedWhilePanelOpening != z) {
            this.wasUnlockedWhilePanelOpening = z;
            Log.d("SecPanelExpansionStateModel", "wasUnlockedWhilePanelOpening:" + z + " " + this);
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        int i = this.panelOpenState;
        sb.append("<" + (i != 0 ? i != 1 ? i != 2 ? "NOT INITIALIZED" : "STATE_OPEN" : "STATE_ANIMATING" : "STATE_CLOSED") + ">");
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        sb.append(" 1stFrc:" + decimalFormat.format(Float.valueOf(this.panelFirstDepthFraction)));
        sb.append(" 2ndFrc:" + decimalFormat.format(Float.valueOf(this.panelSecondDepthFraction)));
        sb.append(" panelExpanded:" + this.panelExpanded);
        sb.append(" lcdOn:" + this.isLcdOn);
        sb.append(" Bar:" + StatusBarState.toString(this.statusBarState));
        sb.append(" wasUnlockedWhilePanelOpening:" + this.wasUnlockedWhilePanelOpening);
        return sb.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x001f, code lost:
    
        if (r8.panelSecondDepthFraction <= 0.0f) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:11:0x0061, code lost:
    
        r0 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x003e, code lost:
    
        if (r8.panelSecondDepthFraction > 0.0f) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0059, code lost:
    
        if (r7 < 1.0f) goto L43;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x005d, code lost:
    
        if (r8.panelExpanded == false) goto L43;
     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updatePanelOpenState() {
        int i;
        if (this.isLcdOn) {
            int i2 = this.statusBarState;
            if (i2 == 0) {
                float f = this.panelFirstDepthFraction;
                if (f >= 0.0f) {
                    float f2 = this.panelSecondDepthFraction;
                    if (f2 >= 0.0f && (f > 0.0f || f2 > 0.0f)) {
                        if (f < 1.0f) {
                        }
                    }
                }
            } else if (i2 == 1) {
                float f3 = this.panelFirstDepthFraction;
                if (f3 < 1.0f || this.panelSecondDepthFraction > 0.0f) {
                    if (f3 < 1.0f || this.panelSecondDepthFraction < 1.0f) {
                        if (f3 >= 1.0f) {
                        }
                    }
                    i = 2;
                }
            } else if (i2 == 2) {
                if (this.panelFirstDepthFraction < 1.0f) {
                }
                i = 2;
            }
            if (this.panelOpenState == i) {
                setWasUnlockedWhilePanelOpening(false);
                Log.d("SecPanelExpansionStateModel", "!!! NOTIFY !!! PanelOpenState is changed to <" + (i != 0 ? i != 1 ? i != 2 ? "NOT INITIALIZED" : "STATE_OPEN" : "STATE_ANIMATING" : "STATE_CLOSED") + "> from " + this);
                this.panelPrvOpenState = this.panelOpenState;
                this.panelOpenState = i;
                this.notifyPanelState.accept(Integer.valueOf(i));
                return;
            }
            return;
        }
        i = 0;
        if (this.panelOpenState == i) {
        }
    }
}
