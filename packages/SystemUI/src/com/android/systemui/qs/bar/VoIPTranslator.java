package com.android.systemui.qs.bar;

import android.content.Context;
import android.content.Intent;
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
import com.android.systemui.qs.bar.VideoCallMicModeBar;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.feature.SemFloatingFeature;
import java.util.Arrays;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class VoIPTranslator implements VideoCallMicModeBar.VideoCallMicModeBarBase {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public boolean isPrerequisiteMet;
    public boolean isVoIPEnabled;
    public final PanelInteractor panelInteractor;
    private final SettingsHelper settingsHelper;
    public final Runnable updateBarVisibilitiesRunnable;
    public final VideoCallMicModeUtil util;
    public View voIPTranslatorButton;
    public LinearLayout voIPTranslatorContainer;
    public TextView voIpTranslatorText;
    public final Uri[] settingsValueList = {Settings.System.getUriFor(SettingsHelper.INDEX_VOIP_TRANSLATOR_ENABLE)};
    private final SettingsHelper.OnChangedCallback settingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.qs.bar.VoIPTranslator$settingsListener$1
        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            SettingsHelper settingsHelper;
            if (uri != null && uri.equals(Settings.System.getUriFor(SettingsHelper.INDEX_VOIP_TRANSLATOR_ENABLE))) {
                VoIPTranslator voIPTranslator = VoIPTranslator.this;
                settingsHelper = voIPTranslator.settingsHelper;
                boolean voIPTranslatorEnable = settingsHelper.getVoIPTranslatorEnable();
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onChanged() - voip_translator_enable : ", "VoIPTranslator", voIPTranslatorEnable);
                voIPTranslator.isVoIPEnabled = voIPTranslatorEnable;
                voIPTranslator.isPrerequisiteMet = VoIPTranslator.updatePrerequisite();
                voIPTranslator.updateBarVisibilitiesRunnable.run();
            }
        }
    };

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

    public VoIPTranslator(VideoCallMicModeUtil videoCallMicModeUtil, Context context, SettingsHelper settingsHelper, PanelInteractor panelInteractor, Runnable runnable) {
        this.util = videoCallMicModeUtil;
        this.context = context;
        this.settingsHelper = settingsHelper;
        this.panelInteractor = panelInteractor;
        this.updateBarVisibilitiesRunnable = runnable;
    }

    public static boolean updatePrerequisite() {
        boolean z = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_DISABLE_NATIVE_AI");
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("isPrerequisiteMet: disable native ai: ", "VoIPTranslator", z);
        return !z;
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final void fini() {
        this.settingsHelper.unregisterCallback(this.settingsListener);
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final View getButton() {
        return this.voIPTranslatorButton;
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final void inflate(View view) {
        View inflate = this.util.inflate(R.layout.sec_voip_translator_button, (ViewGroup) view, true);
        if (inflate != null) {
            this.voIPTranslatorContainer = (LinearLayout) inflate.findViewById(R.id.voip_translator_container);
            this.voIpTranslatorText = (TextView) inflate.findViewById(R.id.voip_translator_text);
        } else {
            inflate = null;
        }
        this.voIPTranslatorButton = inflate;
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final void init() {
        SettingsHelper settingsHelper = this.settingsHelper;
        SettingsHelper.OnChangedCallback onChangedCallback = this.settingsListener;
        Uri[] uriArr = this.settingsValueList;
        settingsHelper.registerCallback(onChangedCallback, (Uri[]) Arrays.copyOf(uriArr, uriArr.length));
        this.isVoIPEnabled = settingsHelper.getVoIPTranslatorEnable();
        Unit unit = Unit.INSTANCE;
        this.isPrerequisiteMet = updatePrerequisite();
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final boolean isEnabled() {
        return this.isVoIPEnabled;
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final void setClickListener(final Function1 function1) {
        View view = this.voIPTranslatorButton;
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.bar.VoIPTranslator$setClickListener$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    Log.d("VoIPTranslator", "onClicked");
                    VoIPTranslator voIPTranslator = VoIPTranslator.this;
                    int i = VoIPTranslator.$r8$clinit;
                    voIPTranslator.getClass();
                    Intent intent = new Intent("com.samsung.android.callassistant.action.START_VOIP_TRANSLATOR");
                    intent.setPackage("com.samsung.android.callassistant");
                    voIPTranslator.panelInteractor.collapsePanels();
                    voIPTranslator.context.sendBroadcast(intent);
                    function1.invoke(SystemUIAnalytics.EID_LIVE_TRANSLATE);
                }
            });
        }
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final void updateFontScale() {
        TextView textView = this.voIpTranslatorText;
        this.util.getClass();
        FontSizeUtils.updateFontSize(textView, R.dimen.sec_style_qs_tile_text_size, 0.8f, 1.3f);
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final void updateHeightMargins(boolean z, VideoCallMicModeStates videoCallMicModeStates, VideoCallMicModeResources videoCallMicModeResources) {
        LinearLayout linearLayout = this.voIPTranslatorContainer;
        if (linearLayout != null) {
            linearLayout.setOrientation(!z ? 1 : 0);
        }
        View view = this.voIPTranslatorButton;
        VideoCallMicModeUtil videoCallMicModeUtil = this.util;
        if (view != null) {
            int i = z ? videoCallMicModeResources.defaultStartPadding : videoCallMicModeResources.iconPadding;
            int i2 = 0;
            int i3 = videoCallMicModeResources.defaultMargin;
            boolean z2 = videoCallMicModeStates.micModeEnabled;
            int i4 = (!z || z2) ? 0 : i3;
            if (z && !z2) {
                i2 = i3;
            }
            videoCallMicModeUtil.getClass();
            view.setPaddingRelative(i, view.getPaddingTop(), view.getPaddingEnd(), view.getPaddingBottom());
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            layoutParams.setMarginStart(i4);
            layoutParams.setMarginEnd(i2);
            view.setLayoutParams(layoutParams);
        }
        LinearLayout linearLayout2 = this.voIPTranslatorContainer;
        if (linearLayout2 != null) {
            videoCallMicModeUtil.getClass();
            linearLayout2.setPaddingRelative(videoCallMicModeResources.textContainerPaddingStart, linearLayout2.getPaddingTop(), videoCallMicModeResources.textContainerPaddingEnd, linearLayout2.getPaddingBottom());
        }
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final void updateVisibilities(VideoCallMicModeStates videoCallMicModeStates) {
        View view = this.voIPTranslatorButton;
        if (view == null) {
            return;
        }
        view.setVisibility((this.isPrerequisiteMet && this.isVoIPEnabled && !videoCallMicModeStates.videoCallEnabled) ? 0 : 8);
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final void updateContents() {
    }
}
