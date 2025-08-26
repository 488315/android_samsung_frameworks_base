package com.android.systemui.screenrecord;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ResultReceiver;
import android.os.UserHandle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.mediaprojection.MediaProjectionCaptureTarget;
import com.android.systemui.mediaprojection.MediaProjectionMetricsLogger;
import com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorActivity;
import com.android.systemui.mediaprojection.permission.BaseMediaProjectionPermissionViewBinder;
import com.android.systemui.mediaprojection.permission.BaseMediaProjectionPermissionViewBinder$setStartButtonOnClickListener$1;
import com.android.systemui.mediaprojection.permission.ScreenShareOption;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.screenrecord.RecordingController.AnonymousClass3;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTrackerImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class ScreenRecordPermissionViewBinder extends BaseMediaProjectionPermissionViewBinder {
    public static final Companion Companion = new Companion(null);
    public static final List MODES = Arrays.asList(ScreenRecordingAudioSource.INTERNAL, ScreenRecordingAudioSource.MIC, ScreenRecordingAudioSource.MIC_AND_INTERNAL);
    public static final int[] RECORDABLE_DISPLAY_TYPES = {4, 2, 1, 3};
    public static final boolean filterDeviceTypeFlag = true;
    public final ActivityStarter activityStarter;
    public Switch audioSwitch;
    public final RecordingController controller;
    public final int hostUid;
    public final UserHandle hostUserHandle;
    public final Runnable onStartRecordingClicked;
    public Spinner options;
    public Switch tapsSwitch;
    public View tapsView;
    public final UserContextProvider userContextProvider;

    public final class CaptureTargetResultReceiver extends ResultReceiver {
        public CaptureTargetResultReceiver() {
            super(new Handler(Looper.getMainLooper()));
        }

        @Override // android.os.ResultReceiver
        public final void onReceiveResult(int i, Bundle bundle) {
            if (i == -1) {
                MediaProjectionCaptureTarget mediaProjectionCaptureTarget = (MediaProjectionCaptureTarget) bundle.getParcelable("capture_region", MediaProjectionCaptureTarget.class);
                ScreenRecordPermissionViewBinder screenRecordPermissionViewBinder = ScreenRecordPermissionViewBinder.this;
                Companion companion = ScreenRecordPermissionViewBinder.Companion;
                screenRecordPermissionViewBinder.requestScreenCapture(mediaProjectionCaptureTarget, 0);
            }
        }
    }

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x0026  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static List createOptionList(DisplayManager displayManager) {
            Display[] displays = displayManager.getDisplays();
            ArrayList arrayList = new ArrayList();
            int i = 0;
            for (Display display : displays) {
                if (display.getDisplayId() != 0) {
                    if (ScreenRecordPermissionViewBinder.filterDeviceTypeFlag) {
                        if (ArraysKt___ArraysKt.contains(display.getType(), ScreenRecordPermissionViewBinder.RECORDABLE_DISPLAY_TYPES)) {
                            arrayList.add(display);
                        }
                    }
                }
            }
            List listMutableListOf = CollectionsKt__CollectionsKt.mutableListOf(new ScreenShareOption(0, R.string.screenrecord_permission_dialog_option_text_single_app, R.string.screenrecord_permission_dialog_warning_single_app, R.string.media_projection_entry_generic_permission_dialog_continue_single_app, 0, null, null, 112, null), new ScreenShareOption(1, R.string.screenrecord_permission_dialog_option_text_entire_screen, R.string.screenrecord_permission_dialog_warning_entire_screen, R.string.screenrecord_permission_dialog_continue_entire_screen, 0, null, Build.MODEL, 32, null));
            if (!arrayList.isEmpty()) {
                ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
                int size = arrayList.size();
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    Display display2 = (Display) obj;
                    arrayList2.add(new ScreenShareOption(1, R.string.screenrecord_permission_dialog_option_text_entire_screen_for_display, R.string.media_projection_entry_app_permission_dialog_warning_entire_screen, R.string.media_projection_entry_app_permission_dialog_continue_entire_screen, display2.getDisplayId(), null, display2.getName(), 32, null));
                }
                CollectionsKt__MutableCollectionsKt.addAll(arrayList2, listMutableListOf);
            }
            return CollectionsKt___CollectionsKt.toList(listMutableListOf);
        }

        private Companion() {
        }
    }

    public interface Factory {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScreenRecordPermissionViewBinder(UserHandle userHandle, int i, MediaProjectionMetricsLogger mediaProjectionMetricsLogger, int i2, DisplayManager displayManager, RecordingController recordingController, ActivityStarter activityStarter, UserContextProvider userContextProvider, Runnable runnable) {
        super(Companion.createOptionList(displayManager), null, i, mediaProjectionMetricsLogger, i2);
        Companion.getClass();
        this.hostUserHandle = userHandle;
        this.hostUid = i;
        this.controller = recordingController;
        this.activityStarter = activityStarter;
        this.userContextProvider = userContextProvider;
        this.onStartRecordingClicked = runnable;
    }

    @Override // com.android.systemui.mediaprojection.permission.BaseMediaProjectionPermissionViewBinder
    public final void bind(View view) {
        super.bind(view);
        View view2 = this.containerView;
        if (view2 == null) {
            view2 = null;
        }
        this.audioSwitch = (Switch) view2.requireViewById(R.id.screenrecord_audio_switch);
        View view3 = this.containerView;
        if (view3 == null) {
            view3 = null;
        }
        this.tapsSwitch = (Switch) view3.requireViewById(R.id.screenrecord_taps_switch);
        View view4 = this.containerView;
        if (view4 == null) {
            view4 = null;
        }
        View viewRequireViewById = view4.requireViewById(R.id.show_taps);
        this.tapsView = viewRequireViewById;
        if (viewRequireViewById == null) {
            viewRequireViewById = null;
        }
        viewRequireViewById.setVisibility(this.selectedScreenShareOption.mode == 0 ? 8 : 0);
        Switch r6 = this.audioSwitch;
        if (r6 == null) {
            r6 = null;
        }
        r6.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.screenrecord.ScreenRecordPermissionViewBinder$initRecordOptionsView$1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view5, MotionEvent motionEvent) {
                return motionEvent.getAction() == 2;
            }
        });
        Switch r62 = this.tapsSwitch;
        if (r62 == null) {
            r62 = null;
        }
        r62.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.screenrecord.ScreenRecordPermissionViewBinder$initRecordOptionsView$2
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view5, MotionEvent motionEvent) {
                return motionEvent.getAction() == 2;
            }
        });
        View view5 = this.containerView;
        if (view5 == null) {
            view5 = null;
        }
        this.options = (Spinner) view5.requireViewById(R.id.screen_recording_options);
        View view6 = this.containerView;
        if (view6 == null) {
            view6 = null;
        }
        ScreenRecordingAdapter screenRecordingAdapter = new ScreenRecordingAdapter(view6.getContext(), android.R.layout.simple_spinner_dropdown_item, MODES);
        screenRecordingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = this.options;
        if (spinner == null) {
            spinner = null;
        }
        spinner.setAdapter((SpinnerAdapter) screenRecordingAdapter);
        Spinner spinner2 = this.options;
        if (spinner2 == null) {
            spinner2 = null;
        }
        spinner2.setOnItemClickListenerInt(new AdapterView.OnItemClickListener() { // from class: com.android.systemui.screenrecord.ScreenRecordPermissionViewBinder$initRecordOptionsView$3
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view7, int i, long j) {
                Switch r0 = this.this$0.audioSwitch;
                if (r0 == null) {
                    r0 = null;
                }
                r0.setChecked(true);
            }
        });
        Spinner spinner3 = this.options;
        if (spinner3 == null) {
            spinner3 = null;
        }
        spinner3.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.screenrecord.ScreenRecordPermissionViewBinder$initRecordOptionsView$4
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view7, AccessibilityNodeInfo accessibilityNodeInfo) {
                accessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK);
                super.onInitializeAccessibilityNodeInfo(view7, accessibilityNodeInfo);
            }
        });
        Spinner spinner4 = this.options;
        if (spinner4 == null) {
            spinner4 = null;
        }
        spinner4.setLongClickable(false);
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.systemui.screenrecord.ScreenRecordPermissionViewBinder.bind.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view7) {
                ScreenRecordPermissionViewBinder.this.startButtonOnClicked();
            }
        };
        TextView textView = this.startButton;
        (textView != null ? textView : null).setOnClickListener(new BaseMediaProjectionPermissionViewBinder$setStartButtonOnClickListener$1(this, onClickListener));
    }

    @Override // com.android.systemui.mediaprojection.permission.BaseMediaProjectionPermissionViewBinder
    public final Integer getOptionsViewLayoutId() {
        return Integer.valueOf(R.layout.screen_record_options);
    }

    @Override // com.android.systemui.mediaprojection.permission.BaseMediaProjectionPermissionViewBinder, android.widget.AdapterView.OnItemSelectedListener
    public final void onItemSelected(AdapterView adapterView, View view, int i, long j) {
        super.onItemSelected(adapterView, view, i, j);
        View view2 = this.tapsView;
        if (view2 == null) {
            view2 = null;
        }
        view2.setVisibility(this.selectedScreenShareOption.mode == 0 ? 8 : 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void requestScreenCapture(MediaProjectionCaptureTarget mediaProjectionCaptureTarget, int i) {
        boolean z;
        ScreenRecordingAudioSource screenRecordingAudioSource;
        Context userContext = ((UserTrackerImpl) this.userContextProvider).getUserContext();
        if (this.selectedScreenShareOption.mode == 0) {
            z = false;
        } else {
            Switch r1 = this.tapsSwitch;
            if (r1 == null) {
                r1 = null;
            }
            if (r1.isChecked()) {
                z = true;
            }
        }
        Switch r4 = this.audioSwitch;
        if (r4 == null) {
            r4 = null;
        }
        if (r4.isChecked()) {
            Spinner spinner = this.options;
            screenRecordingAudioSource = (ScreenRecordingAudioSource) (spinner != null ? spinner : null).getSelectedItem();
        } else {
            screenRecordingAudioSource = ScreenRecordingAudioSource.NONE;
        }
        int iOrdinal = screenRecordingAudioSource.ordinal();
        String str = RecordingService.GROUP_KEY_SAVED;
        PendingIntent foregroundService = PendingIntent.getForegroundService(userContext, 2, new Intent(userContext, (Class<?>) RecordingService.class).setAction("com.android.systemui.screenrecord.START").putExtra("extra_resultCode", -1).putExtra("extra_useAudio", iOrdinal).putExtra("extra_showTaps", z).putExtra("extra_captureTarget", mediaProjectionCaptureTarget).putExtra("extra_displayId", i), 201326592);
        PendingIntent service = PendingIntent.getService(userContext, 2, new Intent(userContext, (Class<?>) RecordingService.class).setAction("com.android.systemui.screenrecord.STOP").putExtra("android.intent.extra.user_handle", userContext.getUserId()), 201326592);
        RecordingController recordingController = this.controller;
        recordingController.mIsStarting = true;
        recordingController.mStopIntent = service;
        RecordingController.AnonymousClass3 anonymousClass3 = recordingController.new AnonymousClass3(3000L, 1000L, foregroundService);
        recordingController.mCountDownTimer = anonymousClass3;
        anonymousClass3.start();
    }

    public final void startButtonOnClicked() {
        Runnable runnable = this.onStartRecordingClicked;
        if (runnable != null) {
            runnable.run();
        }
        ScreenShareOption screenShareOption = this.selectedScreenShareOption;
        if (screenShareOption.mode == 1) {
            requestScreenCapture(null, screenShareOption.displayId);
        }
        if (this.selectedScreenShareOption.mode == 0) {
            View view = this.containerView;
            Intent intent = new Intent((view != null ? view : null).getContext(), (Class<?>) MediaProjectionAppSelectorActivity.class);
            intent.addFlags(268435456);
            intent.putExtra("capture_region_result_receiver", new CaptureTargetResultReceiver());
            intent.putExtra("launched_from_user_handle", this.hostUserHandle);
            intent.putExtra("launched_from_host_uid", this.hostUid);
            intent.putExtra("screen_share_type", "ScreenRecord");
            this.activityStarter.startActivity(intent, true);
        }
    }

    public ScreenRecordPermissionViewBinder(UserHandle userHandle, int i, MediaProjectionMetricsLogger mediaProjectionMetricsLogger, DisplayManager displayManager, RecordingController recordingController, ActivityStarter activityStarter, UserContextProvider userContextProvider, Runnable runnable) {
        this(userHandle, i, mediaProjectionMetricsLogger, 0, displayManager, recordingController, activityStarter, userContextProvider, runnable);
    }
}
