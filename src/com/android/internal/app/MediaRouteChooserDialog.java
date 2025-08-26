package com.android.internal.app;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.android.internal.R;
import com.android.internal.app.MediaRouteChooserContentManager;

/* loaded from: classes5.dex */
public class MediaRouteChooserDialog extends AlertDialog implements MediaRouteChooserContentManager.Delegate {
    private final MediaRouteChooserContentManager mContentManager;
    private Button mExtendedSettingsButton;
    private View.OnClickListener mExtendedSettingsClickListener;
    private final boolean mShowProgressBarWhenEmpty;

    public MediaRouteChooserDialog(Context context, int i) {
        this(context, i, true);
    }

    public MediaRouteChooserDialog(Context context, int i, boolean z) {
        super(context, i);
        this.mShowProgressBarWhenEmpty = z;
        this.mContentManager = new MediaRouteChooserContentManager(context, this);
    }

    public void setRouteTypes(int i) {
        this.mContentManager.setRouteTypes(i);
    }

    public void setExtendedSettingsClickListener(View.OnClickListener onClickListener) {
        if (onClickListener != this.mExtendedSettingsClickListener) {
            this.mExtendedSettingsClickListener = onClickListener;
            updateExtendedSettingsButton();
        }
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    protected void onCreate(Bundle bundle) {
        View viewInflate = LayoutInflater.from(getContext()).inflate(R.layout.media_route_chooser_dialog, (ViewGroup) null);
        setView(viewInflate);
        setTitle(this.mContentManager.getRouteTypes() == 4 ? R.string.media_route_chooser_title_for_remote_display : R.string.media_route_chooser_title);
        setIcon(isLightTheme(getContext()) ? R.drawable.ic_media_route_off_holo_light : R.drawable.ic_media_route_off_holo_dark);
        super.onCreate(bundle);
        this.mExtendedSettingsButton = (Button) findViewById(R.id.media_route_extended_settings_button);
        updateExtendedSettingsButton();
        this.mContentManager.bindViews(viewInflate);
    }

    private void updateExtendedSettingsButton() {
        Button button = this.mExtendedSettingsButton;
        if (button != null) {
            button.setOnClickListener(this.mExtendedSettingsClickListener);
            this.mExtendedSettingsButton.setVisibility(this.mExtendedSettingsClickListener != null ? 0 : 8);
        }
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mContentManager.onAttachedToWindow();
    }

    @Override // android.app.Dialog, android.view.Window.Callback
    public void onDetachedFromWindow() {
        this.mContentManager.onDetachedFromWindow();
        super.onDetachedFromWindow();
    }

    @Override // com.android.internal.app.MediaRouteChooserContentManager.Delegate
    public void dismissView() {
        dismiss();
    }

    @Override // com.android.internal.app.MediaRouteChooserContentManager.Delegate
    public boolean showProgressBarWhenEmpty() {
        return this.mShowProgressBarWhenEmpty;
    }

    static boolean isLightTheme(Context context) {
        TypedValue typedValue = new TypedValue();
        return context.getTheme().resolveAttribute(16844176, typedValue, true) && typedValue.data != 0;
    }
}
