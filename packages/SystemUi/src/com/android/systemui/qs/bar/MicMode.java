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
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.bar.VideoCallMicModeBar;
import com.android.systemui.qs.bar.micmode.MicModeItemFactory;
import com.android.systemui.util.SettingsHelper;
import dagger.Lazy;
import java.util.Arrays;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MicMode implements VideoCallMicModeBar.VideoCallMicModeBarBase {
    public View micModeButton;
    public LinearLayout micModeContainer;
    public final MicModeDetailAdapter micModeDetailAdapter;
    public TextView micModeEffect;
    public boolean micModeEnable;
    public TextView micModeText;
    public final Lazy qsPanelControllerLazy;
    public final SettingsHelper settingsHelper;
    public final Runnable updateBarContentsRunnable;
    public final Runnable updateBarVisibilitiesRunnable;
    public final VideoCallMicModeUtil util;
    public final Uri[] settingsValueList = {Settings.System.getUriFor("mic_mode_enable"), Settings.System.getUriFor("mic_mode_effect")};
    public final MicMode$settingsListener$1 settingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.qs.bar.MicMode$settingsListener$1
        @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
        public final void onChanged(Uri uri) {
            SecQSPanelController secQSPanelController;
            SecQSPanelControllerBase.Record record;
            if (uri == null) {
                return;
            }
            boolean areEqual = Intrinsics.areEqual(uri, Settings.System.getUriFor("mic_mode_enable"));
            MicMode micMode = MicMode.this;
            if (areEqual) {
                boolean z = micMode.settingsHelper.mItemLists.get("mic_mode_enable").getIntValue() == 1;
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onChanged() - mic_mode_enable : ", z, "MicMode");
                micMode.micModeEnable = z;
                if (!z && (record = (secQSPanelController = (SecQSPanelController) micMode.qsPanelControllerLazy.get()).mDetailRecord) != null && record.mDetailAdapter == micMode.micModeDetailAdapter) {
                    secQSPanelController.showDetail(record, false);
                }
                micMode.updateBarVisibilitiesRunnable.run();
            }
            if (Intrinsics.areEqual(uri, Settings.System.getUriFor("mic_mode_effect"))) {
                Log.d("MicMode", "onChanged() - mic_mode_effect : ");
                micMode.updateBarContentsRunnable.run();
            }
        }
    };

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

    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.systemui.qs.bar.MicMode$settingsListener$1] */
    public MicMode(VideoCallMicModeUtil videoCallMicModeUtil, Context context, Lazy lazy, SettingsHelper settingsHelper, Runnable runnable, Runnable runnable2) {
        this.util = videoCallMicModeUtil;
        this.qsPanelControllerLazy = lazy;
        this.settingsHelper = settingsHelper;
        this.updateBarVisibilitiesRunnable = runnable;
        this.updateBarContentsRunnable = runnable2;
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
        Uri[] uriArr = this.settingsValueList;
        Uri[] uriArr2 = (Uri[]) Arrays.copyOf(uriArr, uriArr.length);
        SettingsHelper settingsHelper = this.settingsHelper;
        settingsHelper.registerCallback(this.settingsListener, uriArr2);
        this.micModeEnable = settingsHelper.mItemLists.get("mic_mode_enable").getIntValue() == 1;
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
                    SecQSPanelController secQSPanelController = (SecQSPanelController) MicMode.this.qsPanelControllerLazy.get();
                    MicModeDetailAdapter micModeDetailAdapter = MicMode.this.micModeDetailAdapter;
                    secQSPanelController.getClass();
                    SecQSPanelControllerBase.Record record = new SecQSPanelControllerBase.Record(0);
                    record.mDetailAdapter = micModeDetailAdapter;
                    secQSPanelController.showDetail(record, true);
                    function1.invoke("QPPE1029");
                }
            });
        }
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final void updateContents() {
        int intValue = this.settingsHelper.mItemLists.get("mic_mode_effect").getIntValue();
        TextView textView = this.micModeEffect;
        if (textView == null) {
            return;
        }
        String text = MicModeItemFactory.create(intValue, this.micModeDetailAdapter.mContext).getText();
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

    /* JADX WARN: Removed duplicated region for block: B:14:0x0032 A[ADDED_TO_REGION] */
    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateHeightMargins(boolean z, VideoCallMicModeStates videoCallMicModeStates, VideoCallMicModeResources videoCallMicModeResources) {
        int i;
        LinearLayout linearLayout = this.micModeContainer;
        if (linearLayout != null) {
            linearLayout.setOrientation(!z ? 1 : 0);
        }
        View view = this.micModeButton;
        VideoCallMicModeUtil videoCallMicModeUtil = this.util;
        if (view != null) {
            int i2 = z ? videoCallMicModeResources.defaultStartPadding : videoCallMicModeResources.iconPadding;
            int i3 = 0;
            int i4 = videoCallMicModeResources.defaultMargin;
            boolean z2 = videoCallMicModeStates.videoCallEnabled;
            boolean z3 = videoCallMicModeStates.voIpTranslatorEnabled;
            if (z) {
                if (!z2 && !z3) {
                    i = i4;
                    if (z && !z2 && !z3) {
                        i3 = i4;
                    }
                    videoCallMicModeUtil.getClass();
                    view.setPaddingRelative(i2, view.getPaddingTop(), view.getPaddingEnd(), view.getPaddingBottom());
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.setMarginStart(i);
                    layoutParams.setMarginEnd(i3);
                    view.setLayoutParams(layoutParams);
                }
                i = videoCallMicModeResources.betweenMargin;
                if (z) {
                    i3 = i4;
                }
                videoCallMicModeUtil.getClass();
                view.setPaddingRelative(i2, view.getPaddingTop(), view.getPaddingEnd(), view.getPaddingBottom());
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) view.getLayoutParams();
                layoutParams2.setMarginStart(i);
                layoutParams2.setMarginEnd(i3);
                view.setLayoutParams(layoutParams2);
            } else {
                if (!z2 && !z3) {
                    i = 0;
                    if (z) {
                    }
                    videoCallMicModeUtil.getClass();
                    view.setPaddingRelative(i2, view.getPaddingTop(), view.getPaddingEnd(), view.getPaddingBottom());
                    LinearLayout.LayoutParams layoutParams22 = (LinearLayout.LayoutParams) view.getLayoutParams();
                    layoutParams22.setMarginStart(i);
                    layoutParams22.setMarginEnd(i3);
                    view.setLayoutParams(layoutParams22);
                }
                i = videoCallMicModeResources.betweenMargin;
                if (z) {
                }
                videoCallMicModeUtil.getClass();
                view.setPaddingRelative(i2, view.getPaddingTop(), view.getPaddingEnd(), view.getPaddingBottom());
                LinearLayout.LayoutParams layoutParams222 = (LinearLayout.LayoutParams) view.getLayoutParams();
                layoutParams222.setMarginStart(i);
                layoutParams222.setMarginEnd(i3);
                view.setLayoutParams(layoutParams222);
            }
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
