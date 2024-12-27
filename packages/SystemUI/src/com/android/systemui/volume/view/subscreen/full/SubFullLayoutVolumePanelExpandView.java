package com.android.systemui.volume.view.subscreen.full;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.basic.util.LogWrapper;
import com.android.systemui.volume.store.StoreInteractor;
import com.android.systemui.volume.store.VolumePanelStore;
import com.android.systemui.volume.util.BlurEffect;
import com.android.systemui.volume.util.HandlerWrapper;
import com.android.systemui.volume.view.expand.VolumePanelExpandView$adjustTouchEventForOutsideTouch$1$$ExternalSyntheticOutline0;
import com.samsung.systemui.splugins.extensions.VolumePanelRowExt;
import com.samsung.systemui.splugins.volume.VolumeObserver;
import com.samsung.systemui.splugins.volume.VolumePanelAction;
import com.samsung.systemui.splugins.volume.VolumePanelRow;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class SubFullLayoutVolumePanelExpandView extends FrameLayout implements VolumeObserver<VolumePanelState> {
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
    public VolumePanelStore store;
    public final StoreInteractor storeInteractor;
    public TextView titleView;
    public SubFullLayoutVolumePanelMotion volumePanelMotion;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

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
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public SubFullLayoutVolumePanelExpandView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.storeInteractor = new StoreInteractor(this, null);
        this.rowsLabel = new HashMap();
        this.activeStream = -1;
    }

    public final void addRows$1(VolumePanelState volumePanelState) {
        this.rowsLabel.clear();
        ViewGroup viewGroup = this.rowContainer;
        if (viewGroup == null) {
            viewGroup = null;
        }
        addSpace$1(viewGroup);
        List<VolumePanelRow> volumeRowList = volumePanelState.getVolumeRowList();
        ArrayList arrayList = new ArrayList();
        for (Object obj : volumeRowList) {
            if (((VolumePanelRow) obj).isVisible()) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = new ArrayList(arrayList);
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
        logWrapper.d("SubFullLayoutVolumePanelExpandView", "addRows: rows=" + VolumePanelRowExt.listToString(arrayList2));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            VolumePanelRow volumePanelRow = (VolumePanelRow) it.next();
            SubFullLayoutVolumeRowView subFullLayoutVolumeRowView = (SubFullLayoutVolumeRowView) LayoutInflater.from(getContext()).inflate(R.layout.sub_full_volume_row_view, (ViewGroup) null);
            VolumePanelStore volumePanelStore = this.store;
            VolumePanelStore volumePanelStore2 = volumePanelStore == null ? null : volumePanelStore;
            HandlerWrapper handlerWrapper = this.handlerWrapper;
            HandlerWrapper handlerWrapper2 = handlerWrapper == null ? null : handlerWrapper;
            SubFullLayoutVolumePanelMotion subFullLayoutVolumePanelMotion = this.volumePanelMotion;
            subFullLayoutVolumeRowView.initialize(volumePanelStore2, handlerWrapper2, volumePanelRow, volumePanelState, subFullLayoutVolumePanelMotion == null ? null : subFullLayoutVolumePanelMotion);
            ViewGroup viewGroup2 = this.rowContainer;
            if (viewGroup2 == null) {
                viewGroup2 = null;
            }
            viewGroup2.addView(subFullLayoutVolumeRowView);
            ViewGroup viewGroup3 = this.rowContainer;
            if (viewGroup3 == null) {
                viewGroup3 = null;
            }
            addSpace$1(viewGroup3);
            this.rowsLabel.put(Integer.valueOf(volumePanelRow.getStreamType()), subFullLayoutVolumeRowView.label);
        }
    }

    public final void addSpace$1(ViewGroup viewGroup) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.weight = 1.0f;
        Space space = (Space) LayoutInflater.from(getContext()).inflate(R.layout.sub_full_volume_row_space, (ViewGroup) null);
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
            if (i != 2) {
                return;
            }
            updateVolumeTitle$1(20);
            return;
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
                addRows$1(volumePanelState2);
            }
        }
        updateVolumeTitle$1(activeStream);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.titleView = (TextView) requireViewById(R.id.volume_title);
        this.contentsView = (ViewGroup) requireViewById(R.id.volume_panel_expand_view_contents);
        this.liveCaptionButton = (ImageButton) requireViewById(R.id.volume_live_caption_button);
        this.blurView = (ImageView) requireViewById(R.id.volume_panel_expand_blur);
    }

    public final void updateVolumeTitle$1(int i) {
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
