package com.android.systemui.controls.p005ui.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import com.android.systemui.BasicRune;
import com.android.systemui.R;
import com.android.systemui.controls.p005ui.util.SALogger;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NoAppFragment extends Fragment {
    public View mView;
    public final SALogger saLogger;

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

    public NoAppFragment(SALogger sALogger) {
        this.saLogger = sALogger;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        Log.d("NoAppFragment", "onCreate");
        super.onCreate(bundle);
        requireContext();
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        int i;
        Log.d("NoAppFragment", "onCreateView");
        View inflate = layoutInflater.inflate(R.layout.fragment_controls_no_apps, viewGroup, false);
        this.mView = inflate;
        TextView textView = (TextView) inflate.findViewById(R.id.no_apps_description);
        Configuration configuration = textView.getResources().getConfiguration();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) textView.getLayoutParams();
        int i2 = configuration.screenWidthDp;
        if (i2 <= 480) {
            i = textView.getResources().getDimensionPixelSize(R.dimen.controls_no_app_side_margin_under_480dp);
        } else {
            i = (int) (textView.getResources().getFloat(R.dimen.controls_no_app_side_margin_over_480dp) * TypedValue.applyDimension(1, i2, textView.getContext().getResources().getDisplayMetrics()));
        }
        layoutParams.setMarginStart(i);
        layoutParams.setMarginEnd(i);
        textView.setLayoutParams(layoutParams);
        textView.setText(textView.getResources().getText(R.string.controls_no_apps_context));
        if (BasicRune.CONTROLS_SAMSUNG_ANALYTICS) {
            this.saLogger.sendScreenView(SALogger.Screen.IntroNoAppsToShow.INSTANCE);
        }
        View view = this.mView;
        if (view == null) {
            return null;
        }
        return view;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onDestroy() {
        Log.d("NoAppFragment", "onDestroy");
        this.mCalled = true;
    }
}
