package com.android.systemui.screenrecord;

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
import com.android.systemui.settings.UserContextProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static List createOptionList(DisplayManager displayManager) {
            Display[] displays = displayManager.getDisplays();
            ArrayList arrayList = new ArrayList();
            int i = 0;
            for (Display display : displays) {
                if (display.getDisplayId() != 0) {
                    if (ScreenRecordPermissionViewBinder.filterDeviceTypeFlag) {
                        if (!ArraysKt___ArraysKt.contains(display.getType(), ScreenRecordPermissionViewBinder.RECORDABLE_DISPLAY_TYPES)) {
                        }
                    }
                    arrayList.add(display);
                }
            }
            List mutableListOf = CollectionsKt__CollectionsKt.mutableListOf(new ScreenShareOption(0, R.string.screenrecord_permission_dialog_option_text_single_app, R.string.screenrecord_permission_dialog_warning_single_app, R.string.media_projection_entry_generic_permission_dialog_continue_single_app, 0, null, null, 112, null), new ScreenShareOption(1, R.string.screenrecord_permission_dialog_option_text_entire_screen, R.string.screenrecord_permission_dialog_warning_entire_screen, R.string.screenrecord_permission_dialog_continue_entire_screen, 0, null, Build.MODEL, 32, null));
            if (!arrayList.isEmpty()) {
                ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
                int size = arrayList.size();
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    Display display2 = (Display) obj;
                    arrayList2.add(new ScreenShareOption(1, R.string.screenrecord_permission_dialog_option_text_entire_screen_for_display, R.string.media_projection_entry_app_permission_dialog_warning_entire_screen, R.string.media_projection_entry_app_permission_dialog_continue_entire_screen, display2.getDisplayId(), null, display2.getName(), 32, null));
                }
                CollectionsKt__MutableCollectionsKt.addAll(arrayList2, mutableListOf);
            }
            return CollectionsKt___CollectionsKt.toList(mutableListOf);
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        View requireViewById = view4.requireViewById(R.id.show_taps);
        this.tapsView = requireViewById;
        if (requireViewById == null) {
            requireViewById = null;
        }
        requireViewById.setVisibility(this.selectedScreenShareOption.mode == 0 ? 8 : 0);
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
                Switch r0 = ScreenRecordPermissionViewBinder.this.audioSwitch;
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
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.systemui.screenrecord.ScreenRecordPermissionViewBinder$bind$1
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

    /* JADX WARN: Removed duplicated region for block: B:11:0x0022  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0029  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0036  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void requestScreenCapture(com.android.systemui.mediaprojection.MediaProjectionCaptureTarget r11, int r12) {
        /*
            r10 = this;
            com.android.systemui.settings.UserContextProvider r0 = r10.userContextProvider
            com.android.systemui.settings.UserTrackerImpl r0 = (com.android.systemui.settings.UserTrackerImpl) r0
            android.content.Context r0 = r0.getUserContext()
            com.android.systemui.mediaprojection.permission.ScreenShareOption r1 = r10.selectedScreenShareOption
            int r1 = r1.mode
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L1d
            android.widget.Switch r1 = r10.tapsSwitch
            if (r1 != 0) goto L15
            r1 = r3
        L15:
            boolean r1 = r1.isChecked()
            if (r1 == 0) goto L1d
            r1 = r2
            goto L1e
        L1d:
            r1 = 0
        L1e:
            android.widget.Switch r4 = r10.audioSwitch
            if (r4 != 0) goto L23
            r4 = r3
        L23:
            boolean r4 = r4.isChecked()
            if (r4 == 0) goto L36
            android.widget.Spinner r4 = r10.options
            if (r4 != 0) goto L2e
            goto L2f
        L2e:
            r3 = r4
        L2f:
            java.lang.Object r3 = r3.getSelectedItem()
            com.android.systemui.screenrecord.ScreenRecordingAudioSource r3 = (com.android.systemui.screenrecord.ScreenRecordingAudioSource) r3
            goto L38
        L36:
            com.android.systemui.screenrecord.ScreenRecordingAudioSource r3 = com.android.systemui.screenrecord.ScreenRecordingAudioSource.NONE
        L38:
            int r3 = r3.ordinal()
            java.lang.String r4 = com.android.systemui.screenrecord.RecordingService.GROUP_KEY_SAVED
            android.content.Intent r4 = new android.content.Intent
            java.lang.Class<com.android.systemui.screenrecord.RecordingService> r5 = com.android.systemui.screenrecord.RecordingService.class
            r4.<init>(r0, r5)
            java.lang.String r5 = "com.android.systemui.screenrecord.START"
            android.content.Intent r4 = r4.setAction(r5)
            java.lang.String r5 = "extra_resultCode"
            r6 = -1
            android.content.Intent r4 = r4.putExtra(r5, r6)
            java.lang.String r5 = "extra_useAudio"
            android.content.Intent r3 = r4.putExtra(r5, r3)
            java.lang.String r4 = "extra_showTaps"
            android.content.Intent r1 = r3.putExtra(r4, r1)
            java.lang.String r3 = "extra_captureTarget"
            android.content.Intent r11 = r1.putExtra(r3, r11)
            java.lang.String r1 = "extra_displayId"
            android.content.Intent r11 = r11.putExtra(r1, r12)
            r12 = 2
            r1 = 201326592(0xc000000, float:9.8607613E-32)
            android.app.PendingIntent r9 = android.app.PendingIntent.getForegroundService(r0, r12, r11, r1)
            android.content.Intent r11 = new android.content.Intent
            java.lang.Class<com.android.systemui.screenrecord.RecordingService> r3 = com.android.systemui.screenrecord.RecordingService.class
            r11.<init>(r0, r3)
            java.lang.String r3 = "com.android.systemui.screenrecord.STOP"
            android.content.Intent r11 = r11.setAction(r3)
            int r3 = r0.getUserId()
            java.lang.String r4 = "android.intent.extra.user_handle"
            android.content.Intent r11 = r11.putExtra(r4, r3)
            android.app.PendingIntent r11 = android.app.PendingIntent.getService(r0, r12, r11, r1)
            com.android.systemui.screenrecord.RecordingController r4 = r10.controller
            r4.mIsStarting = r2
            r4.mStopIntent = r11
            com.android.systemui.screenrecord.RecordingController$3 r3 = new com.android.systemui.screenrecord.RecordingController$3
            r7 = 1000(0x3e8, double:4.94E-321)
            r5 = 3000(0xbb8, double:1.482E-320)
            r3.<init>(r5, r7, r9)
            r4.mCountDownTimer = r3
            r3.start()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenrecord.ScreenRecordPermissionViewBinder.requestScreenCapture(com.android.systemui.mediaprojection.MediaProjectionCaptureTarget, int):void");
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
