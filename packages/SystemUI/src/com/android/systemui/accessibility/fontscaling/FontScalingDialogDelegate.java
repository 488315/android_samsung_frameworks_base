package com.android.systemui.accessibility.fontscaling;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.common.ui.view.SeekBarWithIconButtonsView;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SystemSettings;
import com.android.systemui.util.time.SystemClock;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

public final class FontScalingDialogDelegate implements SystemUIDialog.Delegate {
    public static boolean fontSizeHasBeenChangedFromTile;
    public final DelayableExecutor backgroundDelayableExecutor;
    public Runnable cancelUpdateFontScaleRunnable;
    public final Configuration configuration;
    public final Context context;
    public Button doneButton;
    public final FontScalingDialogDelegate$fontSizeObserver$1 fontSizeObserver;
    public long lastUpdateTime;
    public final LayoutInflater layoutInflater;
    public final SecureSettings secureSettings;
    public SeekBarWithIconButtonsView seekBarWithIconButtonsView;
    public final String[] strEntryValues;
    public final SystemClock systemClock;
    public final SystemSettings systemSettings;
    public final SystemUIDialog.Factory systemUIDialogFactory;
    public TextView title;
    public final UserTracker userTracker;
    public final long MIN_UPDATE_INTERVAL_MS = 800;
    public final long CHANGE_BY_SEEKBAR_DELAY_MS = 100;
    public final long CHANGE_BY_BUTTON_DELAY_MS = 300;
    public final AtomicInteger lastProgress = new AtomicInteger(-1);

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

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.accessibility.fontscaling.FontScalingDialogDelegate$fontSizeObserver$1] */
    public FontScalingDialogDelegate(Context context, SystemUIDialog.Factory factory, LayoutInflater layoutInflater, SystemSettings systemSettings, SecureSettings secureSettings, SystemClock systemClock, UserTracker userTracker, final Handler handler, DelayableExecutor delayableExecutor) {
        this.context = context;
        this.systemUIDialogFactory = factory;
        this.layoutInflater = layoutInflater;
        this.systemSettings = systemSettings;
        this.secureSettings = secureSettings;
        this.systemClock = systemClock;
        this.userTracker = userTracker;
        this.backgroundDelayableExecutor = delayableExecutor;
        this.strEntryValues = context.getResources().getStringArray(R.array.entryvalues_font_size);
        this.configuration = new Configuration(context.getResources().getConfiguration());
        this.fontSizeObserver = new ContentObserver(handler) { // from class: com.android.systemui.accessibility.fontscaling.FontScalingDialogDelegate$fontSizeObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                FontScalingDialogDelegate fontScalingDialogDelegate = this;
                fontScalingDialogDelegate.lastUpdateTime = fontScalingDialogDelegate.systemClock.elapsedRealtime();
            }
        };
    }

    public static final void access$changeFontSize(final FontScalingDialogDelegate fontScalingDialogDelegate, int i, long j) {
        if (i != fontScalingDialogDelegate.lastProgress.get()) {
            fontScalingDialogDelegate.lastProgress.set(i);
            boolean z = fontSizeHasBeenChangedFromTile;
            DelayableExecutor delayableExecutor = fontScalingDialogDelegate.backgroundDelayableExecutor;
            if (!z) {
                delayableExecutor.execute(new Runnable() { // from class: com.android.systemui.accessibility.fontscaling.FontScalingDialogDelegate$changeFontSize$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        FontScalingDialogDelegate fontScalingDialogDelegate2 = FontScalingDialogDelegate.this;
                        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) fontScalingDialogDelegate2.userTracker;
                        int userId = userTrackerImpl.getUserId();
                        SecureSettings secureSettings = fontScalingDialogDelegate2.secureSettings;
                        if (Intrinsics.areEqual(secureSettings.getStringForUser("accessibility_font_scaling_has_been_changed", userId), "1")) {
                            return;
                        }
                        secureSettings.putStringForUser("accessibility_font_scaling_has_been_changed", "1", userTrackerImpl.getUserId());
                    }
                });
                fontSizeHasBeenChangedFromTile = true;
            }
            Button button = fontScalingDialogDelegate.doneButton;
            if (button == null) {
                button = null;
            }
            button.setEnabled(false);
            long elapsedRealtime = fontScalingDialogDelegate.systemClock.elapsedRealtime() - fontScalingDialogDelegate.lastUpdateTime;
            long j2 = fontScalingDialogDelegate.MIN_UPDATE_INTERVAL_MS;
            if (elapsedRealtime < j2) {
                j += j2;
            }
            Runnable runnable = fontScalingDialogDelegate.cancelUpdateFontScaleRunnable;
            if (runnable != null) {
                runnable.run();
            }
            fontScalingDialogDelegate.cancelUpdateFontScaleRunnable = delayableExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.accessibility.fontscaling.FontScalingDialogDelegate$updateFontScaleDelayed$1
                @Override // java.lang.Runnable
                public final void run() {
                    final FontScalingDialogDelegate fontScalingDialogDelegate2 = FontScalingDialogDelegate.this;
                    if (fontScalingDialogDelegate2.systemSettings.putStringForUser("font_scale", fontScalingDialogDelegate2.strEntryValues[fontScalingDialogDelegate2.lastProgress.get()], ((UserTrackerImpl) fontScalingDialogDelegate2.userTracker).getUserId())) {
                        return;
                    }
                    TextView textView = fontScalingDialogDelegate2.title;
                    if (textView == null) {
                        textView = null;
                    }
                    textView.post(new Runnable() { // from class: com.android.systemui.accessibility.fontscaling.FontScalingDialogDelegate$updateFontScale$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Button button2 = FontScalingDialogDelegate.this.doneButton;
                            if (button2 == null) {
                                button2 = null;
                            }
                            button2.setEnabled(true);
                        }
                    });
                }
            }, j);
        }
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void beforeCreate(Dialog dialog) {
        SystemUIDialog systemUIDialog = (SystemUIDialog) dialog;
        systemUIDialog.setTitle(R.string.font_scaling_dialog_title);
        systemUIDialog.setView(this.layoutInflater.inflate(R.layout.font_scaling_dialog, (ViewGroup) null));
        systemUIDialog.setButton(-1, R.string.quick_settings_done, null, true);
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog.Delegate
    public final SystemUIDialog createDialog() {
        SystemUIDialog.Factory factory = this.systemUIDialogFactory;
        return factory.create(this, factory.mContext);
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void onConfigurationChanged(Dialog dialog, Configuration configuration) {
        int diff = configuration.diff(this.configuration);
        this.configuration.setTo(configuration);
        if ((diff & 1073741824) != 0) {
            TextView textView = this.title;
            if (textView == null) {
                textView = null;
            }
            textView.post(new Runnable() { // from class: com.android.systemui.accessibility.fontscaling.FontScalingDialogDelegate$onConfigurationChanged$1
                @Override // java.lang.Runnable
                public final void run() {
                    TextView textView2 = FontScalingDialogDelegate.this.title;
                    if (textView2 == null) {
                        textView2 = null;
                    }
                    textView2.setTextAppearance(R.style.TextAppearance_Dialog_Title);
                    Button button = FontScalingDialogDelegate.this.doneButton;
                    if (button == null) {
                        button = null;
                    }
                    button.setTextAppearance(R.style.Widget_Dialog_Button);
                    Button button2 = FontScalingDialogDelegate.this.doneButton;
                    (button2 != null ? button2 : null).setEnabled(true);
                }
            });
        }
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void onCreate(Dialog dialog, Bundle bundle) {
        int length;
        final SystemUIDialog systemUIDialog = (SystemUIDialog) dialog;
        this.title = (TextView) systemUIDialog.requireViewById(android.R.id.alignBounds);
        this.doneButton = (Button) systemUIDialog.requireViewById(android.R.id.button1);
        this.seekBarWithIconButtonsView = (SeekBarWithIconButtonsView) systemUIDialog.requireViewById(R.id.font_scaling_slider);
        String[] strArr = this.strEntryValues;
        String[] strArr2 = new String[strArr.length];
        int length2 = strArr.length;
        for (int i = 0; i < length2; i++) {
            strArr2[i] = this.context.getResources().getString(R.string.font_scale_percentage, Integer.valueOf(MathKt__MathJVMKt.roundToInt(Float.parseFloat(strArr[i]) * 100)));
        }
        SeekBarWithIconButtonsView seekBarWithIconButtonsView = this.seekBarWithIconButtonsView;
        if (seekBarWithIconButtonsView == null) {
            seekBarWithIconButtonsView = null;
        }
        seekBarWithIconButtonsView.mStateLabels = strArr2;
        SeekBar seekBar = seekBarWithIconButtonsView.mSeekbar;
        int progress = seekBar.getProgress();
        String[] strArr3 = seekBarWithIconButtonsView.mStateLabels;
        seekBar.setStateDescription(progress < strArr3.length ? strArr3[seekBarWithIconButtonsView.mSeekbar.getProgress()] : "");
        SeekBarWithIconButtonsView seekBarWithIconButtonsView2 = this.seekBarWithIconButtonsView;
        if (seekBarWithIconButtonsView2 == null) {
            seekBarWithIconButtonsView2 = null;
        }
        seekBarWithIconButtonsView2.mSeekbar.setMax(strArr.length - 1);
        int userId = ((UserTrackerImpl) this.userTracker).getUserId();
        SystemSettings systemSettings = this.systemSettings;
        float floatForUser = systemSettings.getFloatForUser("font_scale", 1.0f, userId);
        AtomicInteger atomicInteger = this.lastProgress;
        float parseFloat = Float.parseFloat(strArr[0]);
        int length3 = strArr.length;
        int i2 = 1;
        while (true) {
            if (i2 >= length3) {
                length = strArr.length - 1;
                break;
            }
            float parseFloat2 = Float.parseFloat(strArr[i2]);
            if (floatForUser < DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(parseFloat2, parseFloat, 0.5f, parseFloat)) {
                length = i2 - 1;
                break;
            } else {
                i2++;
                parseFloat = parseFloat2;
            }
        }
        atomicInteger.set(length);
        SeekBarWithIconButtonsView seekBarWithIconButtonsView3 = this.seekBarWithIconButtonsView;
        if (seekBarWithIconButtonsView3 == null) {
            seekBarWithIconButtonsView3 = null;
        }
        seekBarWithIconButtonsView3.mSeekbar.setProgress(this.lastProgress.get());
        seekBarWithIconButtonsView3.updateIconViewIfNeeded(seekBarWithIconButtonsView3.mSeekbar.getProgress());
        SeekBarWithIconButtonsView seekBarWithIconButtonsView4 = this.seekBarWithIconButtonsView;
        if (seekBarWithIconButtonsView4 == null) {
            seekBarWithIconButtonsView4 = null;
        }
        seekBarWithIconButtonsView4.mSeekBarListener.mOnSeekBarChangeListener = new FontScalingDialogDelegate$onCreate$1(this);
        Button button = this.doneButton;
        (button != null ? button : null).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.accessibility.fontscaling.FontScalingDialogDelegate$onCreate$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SystemUIDialog.this.dismiss();
            }
        });
        systemSettings.registerContentObserverSync("font_scale", this.fontSizeObserver);
    }

    @Override // com.android.systemui.statusbar.phone.DialogDelegate
    public final void onStop(Dialog dialog) {
        Runnable runnable = this.cancelUpdateFontScaleRunnable;
        if (runnable != null) {
            runnable.run();
        }
        this.cancelUpdateFontScaleRunnable = null;
        this.systemSettings.unregisterContentObserverSync(this.fontSizeObserver);
    }
}
