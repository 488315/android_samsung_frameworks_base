package com.android.systemui.qs.bar;

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
import com.android.systemui.qs.bar.VideoCallMicModeBar;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractor;
import com.android.systemui.qs.pipeline.domain.interactor.PanelInteractorImpl;
import com.android.systemui.util.SystemUIAnalytics;
import java.util.ArrayList;
import kotlin.Result;
import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class VideoCallEffect implements VideoCallMicModeBar.VideoCallMicModeBarBase {
    public static final Uri URI_VSET_APP_STATUS_DATA;
    public final VideoCallEffect$contentObserver$1 contentObserver;
    public final Context context;
    public boolean isCameraOpened;
    public boolean isVCEEnabled;
    public final PanelInteractor panelInteractor;
    public final Runnable updateBarVisibilitiesRunnable;
    public final VideoCallMicModeUtil util;
    public View videoCallEffectsButton;
    public LinearLayout videoCallEffectsContainer;
    public TextView videoCallEffectsNum;
    public TextView videoCallEffectsText;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
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
                Object failure;
                super.onChange(z, uri);
                VideoCallEffect videoCallEffect = VideoCallEffect.this;
                try {
                    int i = Result.$r8$clinit;
                    if (uri != null) {
                        Uri uri2 = VideoCallEffect.URI_VSET_APP_STATUS_DATA;
                        videoCallEffect.parseVce(uri);
                        failure = Unit.INSTANCE;
                    } else {
                        failure = null;
                    }
                } catch (Throwable th) {
                    int i2 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
                if (m3422exceptionOrNullimpl != null) {
                    Log.e("VideoCallEffect", "onChange: exception occurred: " + m3422exceptionOrNullimpl.getMessage());
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
        View view2 = null;
        View inflate = this.util.inflate(R.layout.sec_video_call_effects_button, view instanceof ViewGroup ? (ViewGroup) view : null, true);
        if (inflate != null) {
            this.videoCallEffectsText = (TextView) inflate.findViewById(R.id.video_call_effects_text);
            this.videoCallEffectsNum = (TextView) inflate.findViewById(R.id.video_call_effects_number);
            this.videoCallEffectsContainer = (LinearLayout) inflate.findViewById(R.id.video_call_effects_container);
            view2 = inflate;
        }
        this.videoCallEffectsButton = view2;
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final void init() {
        Object failure;
        try {
            int i = Result.$r8$clinit;
            Uri uri = URI_VSET_APP_STATUS_DATA;
            this.context.getContentResolver().registerContentObserver(uri, true, this.contentObserver);
            uri.getClass();
            parseVce(uri);
            failure = Unit.INSTANCE;
        } catch (Throwable th) {
            int i2 = Result.$r8$clinit;
            failure = new Result.Failure(th);
        }
        Throwable m3422exceptionOrNullimpl = Result.m3422exceptionOrNullimpl(failure);
        if (m3422exceptionOrNullimpl != null) {
            Log.e("VideoCallEffect", "registerContentObserver: exception occurred: " + m3422exceptionOrNullimpl.getMessage());
        }
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final boolean isEnabled() {
        return this.isVCEEnabled && this.isCameraOpened;
    }

    public final void parseVce(Uri uri) {
        Cursor query = this.context.getApplicationContext().getContentResolver().query(uri, null, null, null, null);
        if (query != null) {
            try {
                if (query.getCount() >= 1) {
                    if (!query.moveToNext()) {
                        Log.e("VideoCallEffect", "current position is invalid");
                        CloseableKt.closeFinally(query, null);
                        return;
                    }
                    String[] columnNames = query.getColumnNames();
                    ArrayList arrayList = new ArrayList(columnNames.length);
                    for (String str : columnNames) {
                        arrayList.add(Integer.valueOf(query.getColumnIndex(str)));
                    }
                    ArrayList arrayList2 = new ArrayList();
                    int size = arrayList.size();
                    int i = 0;
                    while (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
                        if (((Number) obj).intValue() > -1) {
                            arrayList2.add(obj);
                        }
                    }
                    int size2 = arrayList2.size();
                    int i2 = 0;
                    while (i2 < size2) {
                        Object obj2 = arrayList2.get(i2);
                        i2++;
                        int intValue = ((Number) obj2).intValue();
                        String columnName = query.getColumnName(intValue);
                        String string = query.getString(intValue);
                        if (columnName != null) {
                            int hashCode = columnName.hashCode();
                            if (hashCode != -1609594047) {
                                if (hashCode != -1054699790) {
                                    if (hashCode == -17833129 && columnName.equals("camerastatus")) {
                                        Log.i("VideoCallEffect", "parseVce: " + columnName + " -> " + string);
                                        this.isCameraOpened = Intrinsics.areEqual(string, "OPEN");
                                        this.updateBarVisibilitiesRunnable.run();
                                    }
                                } else if (columnName.equals("availablefunctions")) {
                                    Log.i("VideoCallEffect", "parseVce: " + columnName + " -> " + string);
                                    int i3 = query.getInt(intValue);
                                    TextView textView = this.videoCallEffectsNum;
                                    if (textView != null) {
                                        if (i3 > 0) {
                                            textView.setText(textView.getContext().getResources().getQuantityString(R.plurals.sec_qs_video_call_effects_num, i3, Integer.valueOf(i3)));
                                            textView.setVisibility(0);
                                        } else {
                                            textView.setVisibility(8);
                                        }
                                    }
                                }
                            } else if (columnName.equals("enabled")) {
                                Log.i("VideoCallEffect", "parseVce: " + columnName + " -> " + string);
                                this.isVCEEnabled = Intrinsics.areEqual(string, "true");
                                this.updateBarVisibilitiesRunnable.run();
                            }
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
        this.isVCEEnabled = false;
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
                    ((PanelInteractorImpl) videoCallEffect.panelInteractor).collapsePanels();
                    videoCallEffect.context.startService(intent);
                    function1.mo779invoke(SystemUIAnalytics.EID_VIDEO_EFFECTS);
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
            int i2 = videoCallMicModeResources.defaultMargin;
            boolean z2 = videoCallMicModeStates.micModeEnabled;
            int i3 = (!z || z2) ? 0 : i2;
            if (!z || z2) {
                i2 = 0;
            }
            videoCallMicModeUtil.getClass();
            view.setPaddingRelative(i, view.getPaddingTop(), view.getPaddingEnd(), view.getPaddingBottom());
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
            layoutParams.setMarginStart(i3);
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
        if (view != null) {
            view.setVisibility((this.isVCEEnabled && this.isCameraOpened) ? 0 : 8);
        }
    }

    @Override // com.android.systemui.qs.bar.VideoCallMicModeBar.VideoCallMicModeBarBase
    public final void updateContents() {
    }
}
