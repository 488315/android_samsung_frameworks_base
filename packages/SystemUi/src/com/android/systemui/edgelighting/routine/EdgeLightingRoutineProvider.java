package com.android.systemui.edgelighting.routine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Slog;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.edgelighting.data.style.EdgeLightingStyle;
import com.android.systemui.edgelighting.effect.container.EdgeLightingDialog;
import com.android.systemui.edgelighting.effect.data.EdgeEffectInfo;
import com.android.systemui.edgelighting.effectservice.EdgeLightingDispatcher;
import com.android.systemui.edgelighting.manager.EdgeLightingStyleManager;
import com.android.systemui.edgelighting.utils.EdgeLightingSettingUtils;
import com.samsung.android.SDK.routine.AbsRoutineActionProvider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class EdgeLightingRoutineProvider extends AbsRoutineActionProvider {
    public static final /* synthetic */ int $r8$clinit = 0;
    public EdgeLightingDispatcher mController;
    public String mEdgeLightingEffect;
    public EdgeEffectInfo mEdgeLightingInfo;
    public ScreenStateReceiver mScreenStateReceiver = null;
    public final HandlerC13521 mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.systemui.edgelighting.routine.EdgeLightingRoutineProvider.1
        @Override // android.os.Handler
        public final void dispatchMessage(Message message) {
            int i = message.what;
            if (i != 0) {
                if (i != 1) {
                    return;
                }
                int i2 = EdgeLightingRoutineProvider.$r8$clinit;
                Slog.d("EdgeLightingRoutineProvider", "stopEdgeEffect()");
                EdgeLightingDispatcher edgeLightingDispatcher = EdgeLightingRoutineProvider.this.mController;
                if (edgeLightingDispatcher != null) {
                    edgeLightingDispatcher.stopEdgeEffect();
                }
                EdgeLightingRoutineProvider edgeLightingRoutineProvider = EdgeLightingRoutineProvider.this;
                if (edgeLightingRoutineProvider.mScreenStateReceiver != null) {
                    edgeLightingRoutineProvider.getContext().unregisterReceiver(edgeLightingRoutineProvider.mScreenStateReceiver);
                    edgeLightingRoutineProvider.mScreenStateReceiver = null;
                    return;
                }
                return;
            }
            EdgeLightingRoutineProvider edgeLightingRoutineProvider2 = EdgeLightingRoutineProvider.this;
            EdgeLightingDispatcher edgeLightingDispatcher2 = edgeLightingRoutineProvider2.mController;
            int i3 = 0;
            if (edgeLightingDispatcher2 == null) {
                edgeLightingRoutineProvider2.mController = new EdgeLightingDispatcher(edgeLightingRoutineProvider2.getContext(), 2227, false);
            } else {
                EdgeLightingDialog edgeLightingDialog = edgeLightingDispatcher2.mDialog;
                if (edgeLightingDialog != null ? edgeLightingDialog.isShowing() : edgeLightingDispatcher2.mEffectServiceConrtroller.mStarting) {
                    EdgeLightingRoutineProvider.this.mController.stopEdgeEffect();
                    if (hasMessages(1)) {
                        removeMessages(1);
                    }
                }
            }
            EdgeLightingRoutineProvider edgeLightingRoutineProvider3 = EdgeLightingRoutineProvider.this;
            edgeLightingRoutineProvider3.mController.setForceSettingValue(edgeLightingRoutineProvider3.mEdgeLightingEffect);
            EdgeLightingRoutineProvider edgeLightingRoutineProvider4 = EdgeLightingRoutineProvider.this;
            EdgeLightingDialog edgeLightingDialog2 = edgeLightingRoutineProvider4.mController.mDialog;
            if (edgeLightingDialog2 != null) {
                edgeLightingDialog2.mDozeDraw = true;
            }
            if (!((PowerManager) edgeLightingRoutineProvider4.getContext().getSystemService("power")).isInteractive()) {
                int i4 = EdgeLightingRoutineProvider.$r8$clinit;
                Slog.d("EdgeLightingRoutineProvider", "registerScreenStateReceiver");
                EdgeLightingRoutineProvider edgeLightingRoutineProvider5 = EdgeLightingRoutineProvider.this;
                edgeLightingRoutineProvider5.getClass();
                edgeLightingRoutineProvider5.mScreenStateReceiver = new ScreenStateReceiver(edgeLightingRoutineProvider5, i3);
                edgeLightingRoutineProvider5.getContext().registerReceiver(edgeLightingRoutineProvider5.mScreenStateReceiver, new IntentFilter("android.intent.action.SCREEN_ON"));
            }
            int i5 = EdgeLightingRoutineProvider.$r8$clinit;
            Slog.d("EdgeLightingRoutineProvider", "startEdgeEffect()");
            EdgeLightingRoutineProvider edgeLightingRoutineProvider6 = EdgeLightingRoutineProvider.this;
            edgeLightingRoutineProvider6.mController.startEdgeEffect(edgeLightingRoutineProvider6.mEdgeLightingInfo);
            EdgeLightingRoutineProvider edgeLightingRoutineProvider7 = EdgeLightingRoutineProvider.this;
            edgeLightingRoutineProvider7.mHandler.sendEmptyMessageDelayed(1, edgeLightingRoutineProvider7.mEdgeLightingInfo.mLightingDuration);
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ScreenStateReceiver extends BroadcastReceiver {
        public /* synthetic */ ScreenStateReceiver(EdgeLightingRoutineProvider edgeLightingRoutineProvider, int i) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (EdgeLightingRoutineProvider.this.mController != null) {
                int i = EdgeLightingRoutineProvider.$r8$clinit;
                Slog.d("EdgeLightingRoutineProvider", "onReceive");
                EdgeLightingRoutineProvider.this.mController.refreshBackground();
            }
        }

        private ScreenStateReceiver() {
        }
    }

    @Override // com.samsung.android.SDK.routine.AbsRoutineActionProvider
    public final String getLabelParam(String str) {
        if (!TextUtils.isEmpty(str)) {
            String[] split = str.split(";");
            Slog.d("EdgeLightingRoutineProvider", "getLabelParam param=" + split[0]);
            EdgeLightingStyle edgeLightingStyle = (EdgeLightingStyle) EdgeLightingStyleManager.getInstance().mStyleHashMap.get(split[0]);
            if (edgeLightingStyle != null && edgeLightingStyle.getTitle(getContext()) != null) {
                return edgeLightingStyle.getTitle(getContext()).toString();
            }
        }
        Slog.d("EdgeLightingRoutineProvider", "getLabelParam text is empty");
        return getContext().getString(R.string.edge_lighting_style_noframe);
    }

    @Override // com.samsung.android.SDK.routine.AbsRoutineActionProvider
    public final void onAct(String str, String str2) {
        Slog.d("EdgeLightingRoutineProvider", "onAct : " + str + ", " + str2);
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        String[] split = str2.split(";");
        if (this.mEdgeLightingInfo == null) {
            this.mEdgeLightingInfo = new EdgeEffectInfo();
        }
        this.mEdgeLightingEffect = split[0];
        int parseInt = Integer.parseInt(split[1]);
        int parseInt2 = Integer.parseInt(split[2]);
        int parseInt3 = Integer.parseInt(split[3]);
        int parseInt4 = Integer.parseInt(split[4]);
        int parseInt5 = Integer.parseInt(split[5]);
        int preloadIndex = EdgeLightingStyleManager.getInstance().getPreloadIndex(this.mEdgeLightingEffect);
        int[] iArr = new int[1];
        iArr[0] = (parseInt5 == 0 || parseInt != 99) ? EdgeLightingSettingUtils.getEdgeLightingStylePreDefineColor(getContext(), parseInt, true) : parseInt5;
        float f = 1.0f - (parseInt2 / 100.0f);
        int edgeLightingWidth = EdgeLightingSettingUtils.getEdgeLightingWidth(parseInt3, getContext().getApplicationContext());
        int edgeLightingDuration = EdgeLightingSettingUtils.getEdgeLightingDuration(parseInt4);
        StringBuilder m45m = GridLayoutManager$$ExternalSyntheticOutline0.m45m("EdgeLightingInfo : type=", preloadIndex, ",color=", parseInt, ",alpha=");
        m45m.append(f);
        m45m.append(",width=");
        m45m.append(edgeLightingWidth);
        m45m.append(",time=");
        m45m.append(edgeLightingDuration);
        m45m.append(",colorValue=");
        m45m.append(parseInt5);
        Slog.d("EdgeLightingRoutineProvider", m45m.toString());
        EdgeEffectInfo edgeEffectInfo = this.mEdgeLightingInfo;
        edgeEffectInfo.mEffectType = preloadIndex;
        edgeEffectInfo.mEffectColors = iArr;
        edgeEffectInfo.mStrokeAlpha = f;
        edgeEffectInfo.mStrokeWidth = edgeLightingWidth;
        edgeEffectInfo.mWidthDepth = -1;
        edgeEffectInfo.mLightingDuration = edgeLightingDuration;
        edgeEffectInfo.mIsMultiResolutionSupoorted = false;
        if (hasMessages(0)) {
            removeMessages(0);
        }
        sendEmptyMessage(0);
    }

    @Override // com.samsung.android.SDK.routine.AbsRoutineActionProvider
    public final void getCurrentParam() {
    }
}
