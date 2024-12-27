package com.android.systemui.volume.view.expand;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.volume.config.SystemConfigImpl;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.store.VolumePanelStore;
import com.android.systemui.volume.util.BlurEffect;
import com.android.systemui.volume.util.HandlerWrapper;
import com.android.systemui.volume.view.VolumePanelMotion;
import com.android.systemui.volume.view.VolumeRowView;
import com.samsung.systemui.splugins.extensions.VolumePanelRowExt;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class VolumePanelExpandView extends FrameLayout implements VolumeObserver<VolumePanelState> {
    public int activeStream;
    public BlurEffect blurEffect;
    public ImageView blurView;
    public ViewGroup contentsView;
    public Dialog dialog;
    public HandlerWrapper handlerWrapper;
    public ImageButton liveCaptionButton;
    public LogWrapper logWrapper;
    public ViewGroup rowContainer;
    public final HashMap rowsLabel;
    public ImageButton settingButton;
    public VolumePanelStore store;
    public final StoreInteractor storeInteractor;
    public SystemConfigImpl systemConfig;
    public TextView titleView;
    public VolumePanelMotion volumePanelMotion;

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
                iArr[VolumePanelState.StateType.STATE_UPDATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_SMART_VIEW_ICON_CLICKED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[VolumePanelState.StateType.STATE_CAPTION_CHANGED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public VolumePanelExpandView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.storeInteractor = new StoreInteractor(this, null);
        this.rowsLabel = new HashMap();
        this.activeStream = -1;
    }

    public final void addRows(VolumePanelState volumePanelState) {
        this.rowsLabel.clear();
        ViewGroup viewGroup = this.rowContainer;
        if (viewGroup == null) {
            viewGroup = null;
        }
        addSpace(viewGroup);
        List<VolumePanelRow> volumeRowList = volumePanelState.getVolumeRowList();
        ArrayList arrayList = new ArrayList();
        for (Object obj : volumeRowList) {
            if (((VolumePanelRow) obj).isVisible()) {
                arrayList.add(obj);
            }
        }
        ArrayList<VolumePanelRow> arrayList2 = new ArrayList(arrayList);
        if (arrayList2.size() > 5) {
            arrayList2.subList(5, arrayList2.size()).clear();
        }
        if (!BasicRune.VOLUME_LEFT_DISPLAY_VOLUME_DIALOG) {
            Collections.reverse(arrayList2);
        }
        LogWrapper logWrapper = this.logWrapper;
        if (logWrapper == null) {
            logWrapper = null;
        }
        logWrapper.d("VolumePanelExpandView", "addRows: rows=" + VolumePanelRowExt.listToString(arrayList2));
        for (VolumePanelRow volumePanelRow : arrayList2) {
            VolumeRowView volumeRowView = (VolumeRowView) LayoutInflater.from(getContext()).inflate(R.layout.volume_row_view, (ViewGroup) null);
            VolumePanelStore volumePanelStore = this.store;
            VolumePanelStore volumePanelStore2 = volumePanelStore == null ? null : volumePanelStore;
            HandlerWrapper handlerWrapper = this.handlerWrapper;
            HandlerWrapper handlerWrapper2 = handlerWrapper == null ? null : handlerWrapper;
            VolumePanelMotion volumePanelMotion = this.volumePanelMotion;
            volumeRowView.initialize(volumePanelStore2, handlerWrapper2, volumePanelRow, volumePanelState, volumePanelMotion == null ? null : volumePanelMotion);
            ViewGroup viewGroup2 = this.rowContainer;
            if (viewGroup2 == null) {
                viewGroup2 = null;
            }
            viewGroup2.addView(volumeRowView);
            ViewGroup viewGroup3 = this.rowContainer;
            if (viewGroup3 == null) {
                viewGroup3 = null;
            }
            addSpace(viewGroup3);
            this.rowsLabel.put(Integer.valueOf(volumePanelRow.getStreamType()), volumeRowView.label);
        }
        if (BasicRune.VOLUME_LEFT_DISPLAY_VOLUME_DIALOG) {
            return;
        }
        ViewGroup viewGroup4 = this.rowContainer;
        (viewGroup4 != null ? viewGroup4 : null).addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.volume.view.expand.VolumePanelExpandView$addRows$$inlined$doOnNextLayout$1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                view.removeOnLayoutChangeListener(this);
                ((HorizontalScrollView) VolumePanelExpandView.this.requireViewById(R.id.volume_row_container_scroll_view)).fullScroll(66);
            }
        });
    }

    public final void addSpace(ViewGroup viewGroup) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.weight = 1.0f;
        Space space = (Space) LayoutInflater.from(getContext()).inflate(R.layout.volume_row_space, (ViewGroup) null);
        space.setLayoutParams(layoutParams);
        viewGroup.addView(space);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        this.storeInteractor.sendAction(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_TOUCH_PANEL).isFromOutside(true).build(), false);
        if (motionEvent.getAction() != 4) {
            return super.dispatchTouchEvent(motionEvent);
        }
        VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_TOUCH_OUTSIDE), true, this.storeInteractor, false);
        return true;
    }

    @Override // com.samsung.systemui.splugins.volume.VolumeObserver
    public final void onChanged(VolumePanelState volumePanelState) {
        VolumePanelState volumePanelState2 = volumePanelState;
        int i = WhenMappings.$EnumSwitchMapping$0[volumePanelState2.getStateType().ordinal()];
        if (i != 1) {
            if (i == 2) {
                updateVolumeTitle(20);
                return;
            } else {
                if (i != 3) {
                    return;
                }
                toggleLiveCaptionButton(volumePanelState2.isCaptionEnabled());
                return;
            }
        }
        int activeStream = volumePanelState2.getActiveStream();
        if (activeStream != this.activeStream) {
            this.activeStream = activeStream;
            if (true ^ this.rowsLabel.containsKey(Integer.valueOf(activeStream))) {
                ViewGroup viewGroup = this.rowContainer;
                if (viewGroup == null) {
                    viewGroup = null;
                }
                viewGroup.removeAllViews();
                addRows(volumePanelState2);
            }
        }
        updateVolumeTitle(activeStream);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.rowContainer = (ViewGroup) getRootView().requireViewById(R.id.volume_row_container);
        this.titleView = (TextView) requireViewById(R.id.volume_title);
        this.contentsView = (ViewGroup) requireViewById(R.id.volume_panel_expand_view_contents);
        this.blurView = (ImageView) requireViewById(R.id.volume_panel_expand_blur);
        ImageButton imageButton = (ImageButton) requireViewById(R.id.volume_setting_button);
        this.settingButton = imageButton;
        imageButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.view.expand.VolumePanelExpandView$onFinishInflate$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_SETTINGS_BUTTON_CLICKED), true, VolumePanelExpandView.this.storeInteractor, false);
            }
        });
        ImageButton imageButton2 = (ImageButton) requireViewById(R.id.volume_live_caption_button);
        this.liveCaptionButton = imageButton2;
        imageButton2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.volume.view.expand.VolumePanelExpandView$onFinishInflate$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0.m(new VolumePanelAction.Builder(VolumePanelAction.ActionType.ACTION_CAPTION_CHANGED), true, VolumePanelExpandView.this.storeInteractor, false);
            }
        });
    }

    public final void toggleLiveCaptionButton(boolean z) {
        ImageButton imageButton = this.liveCaptionButton;
        if (imageButton == null) {
            imageButton = null;
        }
        imageButton.setImageResource(z ? R.drawable.ic_volume_popup_live_caption_on : R.drawable.ic_volume_popup_live_caption_off);
        ImageButton imageButton2 = this.liveCaptionButton;
        ImageButton imageButton3 = imageButton2 != null ? imageButton2 : null;
        imageButton3.setContentDescription(getContext().getString(z ? R.string.volume_live_caption_on : R.string.volume_live_caption_off) + getContext().getString(R.string.comma) + " " + getContext().getString(R.string.volume_button_live_caption));
    }

    public final void updateVolumeTitle(int i) {
        String str = (String) this.rowsLabel.get(Integer.valueOf(i));
        TextView textView = this.titleView;
        if (textView == null) {
            textView = null;
        }
        if (Intrinsics.areEqual(textView.getText(), str)) {
            return;
        }
        TextView textView2 = this.titleView;
        (textView2 != null ? textView2 : null).setText(str);
    }
}
