package com.android.systemui.qs.bar;

import android.content.Context;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSDetailController;
import com.android.systemui.qs.bar.VideoCallMicModeBar;
import com.android.systemui.qs.bar.micmode.MicModeItemFactory;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.Arrays;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MicMode implements VideoCallMicModeBar.VideoCallMicModeBarBase {
    public final SecQSDetailController detailController;
    public View micModeButton;
    public LinearLayout micModeContainer;
    public final MicModeDetailAdapter micModeDetailAdapter;
    public TextView micModeEffect;
    public boolean micModeEnable;
    public TextView micModeText;
    private final SettingsHelper settingsHelper;
    public final Runnable updateBarContentsRunnable;
    public final Runnable updateBarVisibilitiesRunnable;
    public final VideoCallMicModeUtil util;
    public final Uri[] settingsValueList = {Settings.System.getUriFor(SettingsHelper.INDEX_MIC_MODE_ENABLE), Settings.System.getUriFor(SettingsHelper.INDEX_MIC_MODE_EFFECT)};
    private final SettingsHelper.OnChangedCallback settingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.qs.bar.MicMode$settingsListener$1
        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            SettingsHelper settingsHelper;
            if (uri == null) {
                return;
            }
            boolean equals = uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_MIC_MODE_ENABLE));
            MicMode micMode = MicMode.this;
            if (equals) {
                settingsHelper = micMode.settingsHelper;
                boolean micModeEnable = settingsHelper.getMicModeEnable();
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onChanged() - mic_mode_enable : ", "MicMode", micModeEnable);
                micMode.micModeEnable = micModeEnable;
                if (!micMode.micModeEnable) {
                    micMode.detailController.closeTargetDetail(micMode.micModeDetailAdapter);
                }
                micMode.updateBarVisibilitiesRunnable.run();
            }
            if (uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_MIC_MODE_EFFECT))) {
                Log.d("MicMode", "onChanged() - mic_mode_effect : ");
                micMode.updateBarContentsRunnable.run();
            }
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public MicMode(VideoCallMicModeUtil videoCallMicModeUtil, Context context, SettingsHelper settingsHelper, Runnable runnable, Runnable runnable2, SecQSDetailController secQSDetailController) {
        this.util = videoCallMicModeUtil;
        this.settingsHelper = settingsHelper;
        this.updateBarVisibilitiesRunnable = runnable;
        this.updateBarContentsRunnable = runnable2;
        this.detailController = secQSDetailController;
        this.micModeDetailAdapter = new MicModeDetailAdapter(context);
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final void fini() {
        this.settingsHelper.unregisterCallback(this.settingsListener);
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final View getButton() {
        return this.micModeButton;
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final void inflate(View view) {
        View inflate = this.util.inflate(R.layout.sec_mic_mode_button, (ViewGroup) view, true);
        if (inflate != null) {
            this.micModeText = (TextView) inflate.findViewById(R.id.mic_mode_text);
            this.micModeEffect = (TextView) inflate.findViewById(R.id.mic_mode_effect);
            this.micModeContainer = (LinearLayout) inflate.findViewById(R.id.mic_mode_container);
        } else {
            inflate = null;
        }
        this.micModeButton = inflate;
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final void init() {
        SettingsHelper settingsHelper = this.settingsHelper;
        SettingsHelper.OnChangedCallback onChangedCallback = this.settingsListener;
        Uri[] uriArr = this.settingsValueList;
        settingsHelper.registerCallback(onChangedCallback, (Uri[]) Arrays.copyOf(uriArr, uriArr.length));
        this.micModeEnable = settingsHelper.getMicModeEnable();
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final boolean isEnabled() {
        return this.micModeEnable;
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final void setClickListener(final Function1 function1) {
        View view = this.micModeButton;
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.bar.MicMode$setClickListener$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    Log.d("MicMode", "onClicked");
                    MicMode micMode = MicMode.this;
                    micMode.detailController.showTargetDetail(micMode.micModeDetailAdapter);
                    function1.invoke(SystemUIAnalytics.EID_MIC_MODE);
                }
            });
        }
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final void updateContents() {
        int micModeEffect = this.settingsHelper.getMicModeEffect();
        TextView textView = this.micModeEffect;
        if (textView == null) {
            return;
        }
        String text = MicModeItemFactory.create(micModeEffect, this.micModeDetailAdapter.mContext).getText();
        Log.d("MicMode", "updateContents " + text);
        textView.setText(text);
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final void updateFontScale() {
        TextView textView = this.micModeText;
        this.util.getClass();
        FontSizeUtils.updateFontSize(textView, R.dimen.sec_style_qs_tile_text_size, 0.8f, 1.3f);
        FontSizeUtils.updateFontSize(this.micModeEffect, R.dimen.sec_style_qs_tile_second_text_size, 0.8f, 1.3f);
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final void updateHeightMargins(boolean z, VideoCallMicModeStates videoCallMicModeStates, VideoCallMicModeResources videoCallMicModeResources) {
        LinearLayout linearLayout = this.micModeContainer;
        if (linearLayout != null) {
            linearLayout.setOrientation(!z ? 1 : 0);
        }
        View view = this.micModeButton;
        VideoCallMicModeUtil videoCallMicModeUtil = this.util;
        if (view != null) {
            int i = z ? videoCallMicModeResources.defaultStartPadding : videoCallMicModeResources.iconPadding;
            int i2 = 0;
            int i3 = videoCallMicModeResources.betweenMargin;
            int i4 = videoCallMicModeResources.defaultMargin;
            boolean z2 = videoCallMicModeStates.videoCallEnabled;
            boolean z3 = videoCallMicModeStates.voIpTranslatorEnabled;
            if (z) {
                if (!z2 && !z3) {
                    i3 = i4;
                }
            } else if (!z2 && !z3) {
                i3 = 0;
            }
            if (z && !z2 && !z3) {
                i2 = i4;
            }
            videoCallMicModeUtil.getClass();
            view.setPaddingRelative(i, view.getPaddingTop(), view.getPaddingEnd(), view.getPaddingBottom());
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            layoutParams.setMarginStart(i3);
            layoutParams.setMarginEnd(i2);
            view.setLayoutParams(layoutParams);
        }
        LinearLayout linearLayout2 = this.micModeContainer;
        if (linearLayout2 != null) {
            videoCallMicModeUtil.getClass();
            linearLayout2.setPaddingRelative(videoCallMicModeResources.textContainerPaddingStart, linearLayout2.getPaddingTop(), videoCallMicModeResources.textContainerPaddingEnd, linearLayout2.getPaddingBottom());
        }
        TextView textView = this.micModeEffect;
        if (textView != null) {
            videoCallMicModeUtil.getClass();
            textView.setPaddingRelative(videoCallMicModeResources.startPadding, textView.getPaddingTop(), textView.getPaddingEnd(), textView.getPaddingBottom());
        }
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final void updateVisibilities(VideoCallMicModeStates videoCallMicModeStates) {
        View view = this.micModeButton;
        if (view == null) {
            return;
        }
        view.setVisibility(this.micModeEnable ? 0 : 8);
    }
}
