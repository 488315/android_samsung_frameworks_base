package com.android.systemui.controls.ui.fragment;

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
import com.android.systemui.R;
import com.android.systemui.controls.util.SALogger;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class NoAppFragment extends Fragment {
    public View mView;
    public final SALogger saLogger;

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
        Log.d("NoAppFragment", "onCreateView");
        View inflate = layoutInflater.inflate(R.layout.fragment_controls_no_apps, viewGroup, false);
        this.mView = inflate;
        TextView textView = (TextView) inflate.findViewById(R.id.no_apps_description);
        if (textView != null) {
            Configuration configuration = textView.getResources().getConfiguration();
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) textView.getLayoutParams();
            int i = configuration.screenWidthDp;
            int dimensionPixelSize = i <= 480 ? textView.getResources().getDimensionPixelSize(R.dimen.control_no_apps_side_margin_under_480dp) : (int) (TypedValue.applyDimension(1, i, textView.getContext().getResources().getDisplayMetrics()) * 0.2f);
            layoutParams.setMarginStart(dimensionPixelSize);
            layoutParams.setMarginEnd(dimensionPixelSize);
            textView.setLayoutParams(layoutParams);
            textView.setText(textView.getResources().getText(R.string.controls_no_apps_context));
        }
        this.saLogger.sendScreenView(SALogger.Screen.IntroNoAppsToShow.INSTANCE);
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
