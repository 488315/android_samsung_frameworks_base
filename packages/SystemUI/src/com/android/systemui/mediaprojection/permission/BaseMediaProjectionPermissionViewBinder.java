package com.android.systemui.mediaprojection.permission;

import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.ViewStub;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import com.android.systemui.R;
import com.android.systemui.mediaprojection.MediaProjectionMetricsLogger;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public class BaseMediaProjectionPermissionViewBinder implements AdapterView.OnItemSelectedListener {
    public final String appName;
    public View containerView;
    public final int defaultSelectedMode;
    public final int hostUid;
    public final MediaProjectionMetricsLogger mediaProjectionMetricsLogger;
    public Spinner screenShareModeSpinner;
    public final List screenShareOptions;
    public ScreenShareOption selectedScreenShareOption;
    public boolean shouldLogCancel;
    public TextView startButton;
    public TextView warning;

    public BaseMediaProjectionPermissionViewBinder(List<ScreenShareOption> list, String str, int i, MediaProjectionMetricsLogger mediaProjectionMetricsLogger, int i2) {
        this.screenShareOptions = list;
        this.appName = str;
        this.hostUid = i;
        this.mediaProjectionMetricsLogger = mediaProjectionMetricsLogger;
        this.defaultSelectedMode = i2;
        for (ScreenShareOption screenShareOption : list) {
            if (screenShareOption.mode == this.defaultSelectedMode) {
                this.selectedScreenShareOption = screenShareOption;
                this.shouldLogCancel = true;
                return;
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    public void bind(View view) {
        Context context;
        this.containerView = view;
        if (view == null) {
            view = null;
        }
        this.warning = (TextView) view.requireViewById(R.id.text_warning);
        View view2 = this.containerView;
        if (view2 == null) {
            view2 = null;
        }
        this.startButton = (TextView) view2.requireViewById(android.R.id.button1);
        for (ScreenShareOption screenShareOption : this.screenShareOptions) {
            if (screenShareOption.mode == this.defaultSelectedMode) {
                this.selectedScreenShareOption = screenShareOption;
                setOptionSpecificFields();
                View view3 = this.containerView;
                if (view3 == null) {
                    view3 = null;
                }
                Display display = view3.getContext().getDisplay();
                if (display == null || display.getDisplayId() != 0) {
                    View view4 = this.containerView;
                    if (view4 == null) {
                        view4 = null;
                    }
                    context = view4.getContext();
                } else {
                    View view5 = this.containerView;
                    if (view5 == null) {
                        view5 = null;
                    }
                    context = view5.getContext().getApplicationContext();
                }
                context.getClass();
                OptionsAdapter optionsAdapter = new OptionsAdapter(context, this.screenShareOptions);
                View view6 = this.containerView;
                if (view6 == null) {
                    view6 = null;
                }
                Spinner spinner = (Spinner) view6.requireViewById(R.id.screen_share_mode_options);
                this.screenShareModeSpinner = spinner;
                if (spinner == null) {
                    spinner = null;
                }
                spinner.setAdapter((SpinnerAdapter) optionsAdapter);
                Spinner spinner2 = this.screenShareModeSpinner;
                if (spinner2 == null) {
                    spinner2 = null;
                }
                spinner2.setOnItemSelectedListener(this);
                Spinner spinner3 = this.screenShareModeSpinner;
                if (spinner3 == null) {
                    spinner3 = null;
                }
                spinner3.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.mediaprojection.permission.BaseMediaProjectionPermissionViewBinder$initScreenShareSpinner$1
                    @Override // android.view.View.AccessibilityDelegate
                    public final void onInitializeAccessibilityNodeInfo(View view7, AccessibilityNodeInfo accessibilityNodeInfo) {
                        accessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_LONG_CLICK);
                        super.onInitializeAccessibilityNodeInfo(view7, accessibilityNodeInfo);
                    }
                });
                Spinner spinner4 = this.screenShareModeSpinner;
                if (spinner4 == null) {
                    spinner4 = null;
                }
                spinner4.setLongClickable(false);
                Iterator it = this.screenShareOptions.iterator();
                int i = 0;
                while (true) {
                    if (!it.hasNext()) {
                        i = -1;
                        break;
                    } else if (((ScreenShareOption) it.next()).mode == this.defaultSelectedMode) {
                        break;
                    } else {
                        i++;
                    }
                }
                Spinner spinner5 = this.screenShareModeSpinner;
                if (spinner5 == null) {
                    spinner5 = null;
                }
                spinner5.setSelection(i, false);
                Integer optionsViewLayoutId = getOptionsViewLayoutId();
                if (optionsViewLayoutId == null) {
                    return;
                }
                View view7 = this.containerView;
                ViewStub viewStub = (ViewStub) (view7 != null ? view7 : null).requireViewById(R.id.options_stub);
                viewStub.setLayoutResource(optionsViewLayoutId.intValue());
                viewStub.inflate();
                return;
            }
        }
        throw new NoSuchElementException("Collection contains no element matching the predicate.");
    }

    public Integer getOptionsViewLayoutId() {
        return null;
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public void onItemSelected(AdapterView adapterView, View view, int i, long j) {
        this.selectedScreenShareOption = (ScreenShareOption) this.screenShareOptions.get(i);
        setOptionSpecificFields();
    }

    public final void setOptionSpecificFields() {
        TextView textView = this.warning;
        if (textView == null) {
            textView = null;
        }
        View view = this.containerView;
        if (view == null) {
            view = null;
        }
        textView.setText(view.getContext().getString(this.selectedScreenShareOption.warningText, this.appName));
        TextView textView2 = this.startButton;
        if (textView2 == null) {
            textView2 = null;
        }
        View view2 = this.containerView;
        textView2.setText((view2 != null ? view2 : null).getContext().getString(this.selectedScreenShareOption.startButtonText));
    }

    public BaseMediaProjectionPermissionViewBinder(List list, String str, int i, MediaProjectionMetricsLogger mediaProjectionMetricsLogger, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, str, i, mediaProjectionMetricsLogger, (i3 & 16) != 0 ? ((ScreenShareOption) CollectionsKt___CollectionsKt.first(list)).mode : i2);
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onNothingSelected(AdapterView adapterView) {
    }
}
