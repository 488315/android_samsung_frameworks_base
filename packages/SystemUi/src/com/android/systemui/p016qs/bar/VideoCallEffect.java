package com.android.systemui.p016qs.bar;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.R;
import com.android.systemui.p016qs.bar.VideoCallMicModeBar;
import com.android.systemui.p016qs.pipeline.domain.interactor.PanelInteractor;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p055io.CloseableKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VideoCallEffect implements VideoCallMicModeBar.VideoCallMicModeBarBase {
    public static final Uri URI_VSET_APP_STATUS_DATA;
    public final VideoCallEffect$contentObserver$1 contentObserver;
    public final Context context;
    public boolean isCameraOpened;
    public final PanelInteractor panelInteractor;
    public final Runnable updateBarVisibilitiesRunnable;
    public final VideoCallMicModeUtil util;
    public View videoCallEffectsButton;
    public LinearLayout videoCallEffectsContainer;
    public TextView videoCallEffectsNum;
    public TextView videoCallEffectsText;

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
        URI_VSET_APP_STATUS_DATA = Uri.parse("content://com.samsung.android.vtcamerasettings.VsetInfoProvider/VsetAppInfo/StatusInfo");
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.qs.bar.VideoCallEffect$contentObserver$1] */
    public VideoCallEffect(VideoCallMicModeUtil videoCallMicModeUtil, Context context, PanelInteractor panelInteractor, Runnable runnable) {
        this.util = videoCallMicModeUtil;
        this.context = context;
        this.panelInteractor = panelInteractor;
        this.updateBarVisibilitiesRunnable = runnable;
        final Handler handler = new Handler();
        this.contentObserver = new ContentObserver(handler) { // from class: com.android.systemui.qs.bar.VideoCallEffect$contentObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z, Uri uri) {
                super.onChange(z, uri);
                if (uri != null) {
                    VideoCallEffect videoCallEffect = VideoCallEffect.this;
                    Uri uri2 = VideoCallEffect.URI_VSET_APP_STATUS_DATA;
                    videoCallEffect.parseVce(uri);
                }
            }
        };
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final void fini() {
        try {
            this.context.getContentResolver().unregisterContentObserver(this.contentObserver);
        } catch (Exception e) {
            Log.e("VideoCallEffect", "unregisterContentObserver: exception occurred" + e.getMessage());
        }
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final View getButton() {
        return this.videoCallEffectsButton;
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final void inflate(View view) {
        View inflate = this.util.inflate(R.layout.sec_video_call_effects_button, (ViewGroup) view, true);
        if (inflate != null) {
            this.videoCallEffectsText = (TextView) inflate.findViewById(R.id.video_call_effects_text);
            this.videoCallEffectsNum = (TextView) inflate.findViewById(R.id.video_call_effects_number);
            this.videoCallEffectsContainer = (LinearLayout) inflate.findViewById(R.id.video_call_effects_container);
        } else {
            inflate = null;
        }
        this.videoCallEffectsButton = inflate;
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final void init() {
        try {
            Uri uri = URI_VSET_APP_STATUS_DATA;
            this.context.getContentResolver().registerContentObserver(uri, true, this.contentObserver);
            parseVce(uri);
        } catch (Exception e) {
            Log.e("VideoCallEffect", "registerContentObserver: exception occurred" + e.getMessage());
        }
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final boolean isEnabled() {
        return this.isCameraOpened;
    }

    public final void parseVce(Uri uri) {
        Context context = this.context;
        Cursor query = context.getApplicationContext().getContentResolver().query(uri, null, null, null, null);
        if (query != null) {
            try {
                if (query.getCount() >= 1) {
                    query.moveToNext();
                    String[] columnNames = query.getColumnNames();
                    ArrayList arrayList = new ArrayList(columnNames.length);
                    for (String str : columnNames) {
                        arrayList.add(Integer.valueOf(query.getColumnIndex(str)));
                    }
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        int intValue = ((Number) it.next()).intValue();
                        String columnName = query.getColumnName(intValue);
                        String string = query.getString(intValue);
                        if (Intrinsics.areEqual(columnName, "availablefunctions")) {
                            Log.i("VideoCallEffect", "parseVce: " + columnName + " -> " + string);
                            int i = query.getInt(intValue);
                            TextView textView = this.videoCallEffectsNum;
                            if (textView != null) {
                                textView.setText(context.getResources().getQuantityString(R.plurals.sec_qs_video_call_effects_num, i, Integer.valueOf(i)));
                            }
                        } else if (Intrinsics.areEqual(columnName, "camerastatus")) {
                            Log.i("VideoCallEffect", "parseVce: " + columnName + " -> " + string);
                            this.isCameraOpened = Intrinsics.areEqual(string, "OPEN");
                            this.updateBarVisibilitiesRunnable.run();
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(query, null);
                    return;
                }
            } finally {
            }
        }
        Log.e("VideoCallEffect", "cursor is null or number of cursor is less than 1");
        this.isCameraOpened = false;
        CloseableKt.closeFinally(query, null);
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final void setClickListener(final Function1 function1) {
        View view = this.videoCallEffectsButton;
        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.bar.VideoCallEffect$setClickListener$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    Log.d("VideoCallEffect", "onClicked");
                    VideoCallEffect videoCallEffect = VideoCallEffect.this;
                    Uri uri = VideoCallEffect.URI_VSET_APP_STATUS_DATA;
                    videoCallEffect.getClass();
                    Intent intent = new Intent("intentfilter.samsung.vtcamerasetting.openmenu");
                    intent.setPackage("com.samsung.android.vtcamerasettings");
                    videoCallEffect.panelInteractor.collapsePanels();
                    videoCallEffect.context.startService(intent);
                    function1.invoke("QPPE1030");
                }
            });
        }
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final void updateFontScale() {
        TextView textView = this.videoCallEffectsText;
        this.util.getClass();
        FontSizeUtils.updateFontSize(textView, R.dimen.sec_style_qs_tile_text_size, 0.8f, 1.3f);
        FontSizeUtils.updateFontSize(this.videoCallEffectsNum, R.dimen.sec_style_qs_tile_second_text_size, 0.8f, 1.3f);
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final void updateHeightMargins(boolean z, VideoCallMicModeStates videoCallMicModeStates, VideoCallMicModeResources videoCallMicModeResources) {
        LinearLayout linearLayout = this.videoCallEffectsContainer;
        if (linearLayout != null) {
            linearLayout.setOrientation(!z ? 1 : 0);
        }
        View view = this.videoCallEffectsButton;
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
        LinearLayout linearLayout2 = this.videoCallEffectsContainer;
        if (linearLayout2 != null) {
            videoCallMicModeUtil.getClass();
            linearLayout2.setPaddingRelative(videoCallMicModeResources.textContainerPaddingStart, linearLayout2.getPaddingTop(), videoCallMicModeResources.textContainerPaddingEnd, linearLayout2.getPaddingBottom());
        }
        TextView textView = this.videoCallEffectsNum;
        if (textView != null) {
            videoCallMicModeUtil.getClass();
            textView.setPaddingRelative(videoCallMicModeResources.startPadding, textView.getPaddingTop(), textView.getPaddingEnd(), textView.getPaddingBottom());
        }
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final void updateVisibilities(VideoCallMicModeStates videoCallMicModeStates) {
        View view = this.videoCallEffectsButton;
        if (view == null) {
            return;
        }
        view.setVisibility(this.isCameraOpened ? 0 : 8);
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final void updateContents() {
    }
}
