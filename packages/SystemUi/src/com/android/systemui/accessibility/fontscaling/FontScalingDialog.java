package com.android.systemui.accessibility.fontscaling;

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
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.common.p004ui.view.SeekBarWithIconButtonsView;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SecureSettingsImpl;
import com.android.systemui.util.settings.SystemSettings;
import com.android.systemui.util.settings.SystemSettingsImpl;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FontScalingDialog extends SystemUIDialog {
    public static boolean fontSizeHasBeenChangedFromTile;
    public final long CHANGE_BY_BUTTON_DELAY_MS;
    public final long CHANGE_BY_SEEKBAR_DELAY_MS;
    public final long MIN_UPDATE_INTERVAL_MS;
    public final DelayableExecutor backgroundDelayableExecutor;
    public ExecutorImpl.ExecutionToken cancelUpdateFontScaleRunnable;
    public final Configuration configuration;
    public Button doneButton;
    public final FontScalingDialog$fontSizeObserver$1 fontSizeObserver;
    public final AtomicInteger lastProgress;
    public long lastUpdateTime;
    public final SecureSettings secureSettings;
    public SeekBarWithIconButtonsView seekBarWithIconButtonsView;
    public final String[] strEntryValues;
    public final SystemClock systemClock;
    public final SystemSettings systemSettings;
    public TextView title;

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

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.accessibility.fontscaling.FontScalingDialog$fontSizeObserver$1] */
    public FontScalingDialog(Context context, SystemSettings systemSettings, SecureSettings secureSettings, SystemClock systemClock, final Handler handler, DelayableExecutor delayableExecutor) {
        super(context);
        this.systemSettings = systemSettings;
        this.secureSettings = secureSettings;
        this.systemClock = systemClock;
        this.backgroundDelayableExecutor = delayableExecutor;
        this.MIN_UPDATE_INTERVAL_MS = 800L;
        this.CHANGE_BY_SEEKBAR_DELAY_MS = 100L;
        this.CHANGE_BY_BUTTON_DELAY_MS = 300L;
        this.strEntryValues = context.getResources().getStringArray(R.array.entryvalues_font_size);
        this.lastProgress = new AtomicInteger(-1);
        this.configuration = new Configuration(context.getResources().getConfiguration());
        this.fontSizeObserver = new ContentObserver(handler) { // from class: com.android.systemui.accessibility.fontscaling.FontScalingDialog$fontSizeObserver$1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                FontScalingDialog fontScalingDialog = this;
                ((SystemClockImpl) fontScalingDialog.systemClock).getClass();
                fontScalingDialog.lastUpdateTime = android.os.SystemClock.elapsedRealtime();
            }
        };
    }

    public static final void access$changeFontSize(final FontScalingDialog fontScalingDialog, int i, long j) {
        if (i != fontScalingDialog.lastProgress.get()) {
            fontScalingDialog.lastProgress.set(i);
            if (!fontSizeHasBeenChangedFromTile) {
                ((ExecutorImpl) fontScalingDialog.backgroundDelayableExecutor).execute(new Runnable() { // from class: com.android.systemui.accessibility.fontscaling.FontScalingDialog$changeFontSize$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        FontScalingDialog fontScalingDialog2 = FontScalingDialog.this;
                        SecureSettings secureSettings = fontScalingDialog2.secureSettings;
                        if (Intrinsics.areEqual(((SecureSettingsImpl) secureSettings).getStringForUser(secureSettings.getUserId(), "accessibility_font_scaling_has_been_changed"), "1")) {
                            return;
                        }
                        SecureSettings secureSettings2 = fontScalingDialog2.secureSettings;
                        ((SecureSettingsImpl) secureSettings2).putStringForUser(secureSettings2.getUserId(), "accessibility_font_scaling_has_been_changed", "1");
                    }
                });
                fontSizeHasBeenChangedFromTile = true;
            }
            ((SystemClockImpl) fontScalingDialog.systemClock).getClass();
            long elapsedRealtime = android.os.SystemClock.elapsedRealtime() - fontScalingDialog.lastUpdateTime;
            long j2 = fontScalingDialog.MIN_UPDATE_INTERVAL_MS;
            if (elapsedRealtime < j2) {
                j += j2;
            }
            ExecutorImpl.ExecutionToken executionToken = fontScalingDialog.cancelUpdateFontScaleRunnable;
            if (executionToken != null) {
                executionToken.run();
            }
            fontScalingDialog.cancelUpdateFontScaleRunnable = fontScalingDialog.backgroundDelayableExecutor.executeDelayed(j, new Runnable() { // from class: com.android.systemui.accessibility.fontscaling.FontScalingDialog$updateFontScaleDelayed$1
                @Override // java.lang.Runnable
                public final void run() {
                    FontScalingDialog fontScalingDialog2 = FontScalingDialog.this;
                    SystemSettings systemSettings = fontScalingDialog2.systemSettings;
                    String str = fontScalingDialog2.strEntryValues[fontScalingDialog2.lastProgress.get()];
                    ((SystemSettingsImpl) systemSettings).putStringForUser(systemSettings.getUserId(), "font_scale", str);
                }
            });
        }
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int diff = configuration.diff(this.configuration);
        this.configuration.setTo(configuration);
        if ((1073741824 & diff) != 0) {
            TextView textView = this.title;
            if (textView == null) {
                textView = null;
            }
            textView.post(new Runnable() { // from class: com.android.systemui.accessibility.fontscaling.FontScalingDialog$onConfigurationChanged$1
                @Override // java.lang.Runnable
                public final void run() {
                    TextView textView2 = FontScalingDialog.this.title;
                    if (textView2 == null) {
                        textView2 = null;
                    }
                    textView2.setTextAppearance(2132018157);
                    Button button = FontScalingDialog.this.doneButton;
                    (button != null ? button : null).setTextAppearance(2132018833);
                }
            });
        }
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog, android.app.AlertDialog, android.app.Dialog
    public final void onCreate(Bundle bundle) {
        int length;
        setTitle(R.string.font_scaling_dialog_title);
        setView(LayoutInflater.from(getContext()).inflate(R.layout.font_scaling_dialog, (ViewGroup) null));
        setButton(-1, R.string.quick_settings_done, null, true);
        super.onCreate(bundle);
        this.title = (TextView) requireViewById(android.R.id.alias);
        this.doneButton = (Button) requireViewById(android.R.id.button1);
        this.seekBarWithIconButtonsView = (SeekBarWithIconButtonsView) requireViewById(R.id.font_scaling_slider);
        String[] strArr = this.strEntryValues;
        String[] strArr2 = new String[strArr.length];
        int length2 = strArr.length;
        for (int i = 0; i < length2; i++) {
            strArr2[i] = getContext().getResources().getString(R.string.font_scale_percentage, Integer.valueOf(MathKt__MathJVMKt.roundToInt(Float.parseFloat(this.strEntryValues[i]) * 100)));
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
        seekBarWithIconButtonsView2.mSeekbar.setMax(this.strEntryValues.length - 1);
        SystemSettings systemSettings = this.systemSettings;
        float floatForUser = systemSettings.getFloatForUser("font_scale", systemSettings.getUserId(), 1.0f);
        AtomicInteger atomicInteger = this.lastProgress;
        float parseFloat = Float.parseFloat(this.strEntryValues[0]);
        int length3 = this.strEntryValues.length;
        int i2 = 1;
        while (true) {
            if (i2 >= length3) {
                length = this.strEntryValues.length - 1;
                break;
            }
            float parseFloat2 = Float.parseFloat(this.strEntryValues[i2]);
            if (floatForUser < DependencyGraph$$ExternalSyntheticOutline0.m20m(parseFloat2, parseFloat, 0.5f, parseFloat)) {
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
        int i3 = this.lastProgress.get();
        seekBarWithIconButtonsView3.mSeekbar.setProgress(i3);
        SeekBarWithIconButtonsView.setIconViewAndFrameEnabled(seekBarWithIconButtonsView3.mIconStart, i3 > 0);
        SeekBarWithIconButtonsView.setIconViewAndFrameEnabled(seekBarWithIconButtonsView3.mIconEnd, i3 < seekBarWithIconButtonsView3.mSeekbar.getMax());
        SeekBarWithIconButtonsView seekBarWithIconButtonsView4 = this.seekBarWithIconButtonsView;
        if (seekBarWithIconButtonsView4 == null) {
            seekBarWithIconButtonsView4 = null;
        }
        seekBarWithIconButtonsView4.mSeekBarListener.mOnSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() { // from class: com.android.systemui.accessibility.fontscaling.FontScalingDialog$onCreate$1
            public boolean isTrackingTouch;

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onProgressChanged(SeekBar seekBar2, int i4, boolean z) {
                FontScalingDialog fontScalingDialog = FontScalingDialog.this;
                fontScalingDialog.getClass();
                Configuration configuration = new Configuration(fontScalingDialog.configuration);
                configuration.fontScale = Float.parseFloat(fontScalingDialog.strEntryValues[i4]);
                Context createConfigurationContext = fontScalingDialog.getContext().createConfigurationContext(configuration);
                createConfigurationContext.getTheme().setTo(fontScalingDialog.getContext().getTheme());
                TextView textView = fontScalingDialog.title;
                if (textView == null) {
                    textView = null;
                }
                textView.setTextSize(0, createConfigurationContext.getResources().getDimension(R.dimen.dialog_title_text_size));
                if (this.isTrackingTouch) {
                    return;
                }
                FontScalingDialog fontScalingDialog2 = FontScalingDialog.this;
                FontScalingDialog.access$changeFontSize(fontScalingDialog2, i4, fontScalingDialog2.CHANGE_BY_BUTTON_DELAY_MS);
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onStartTrackingTouch(SeekBar seekBar2) {
                this.isTrackingTouch = true;
            }

            @Override // android.widget.SeekBar.OnSeekBarChangeListener
            public final void onStopTrackingTouch(SeekBar seekBar2) {
                this.isTrackingTouch = false;
                FontScalingDialog.access$changeFontSize(FontScalingDialog.this, seekBar2.getProgress(), FontScalingDialog.this.CHANGE_BY_SEEKBAR_DELAY_MS);
            }
        };
        Button button = this.doneButton;
        (button != null ? button : null).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.accessibility.fontscaling.FontScalingDialog$onCreate$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                FontScalingDialog.this.dismiss();
            }
        });
        SystemSettingsImpl systemSettingsImpl = (SystemSettingsImpl) this.systemSettings;
        systemSettingsImpl.registerContentObserverForUser(systemSettingsImpl.getUriFor("font_scale"), false, (ContentObserver) this.fontSizeObserver, systemSettingsImpl.getUserId());
    }

    @Override // com.android.systemui.statusbar.phone.SystemUIDialog
    public final void stop() {
        ExecutorImpl.ExecutionToken executionToken = this.cancelUpdateFontScaleRunnable;
        if (executionToken != null) {
            executionToken.run();
        }
        this.cancelUpdateFontScaleRunnable = null;
        this.systemSettings.unregisterContentObserver(this.fontSizeObserver);
    }
}
