package com.android.systemui.qp.customize;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.qp.util.SubscreenUtil;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SubscreenCustomizerActivity extends Activity implements WakefulnessLifecycle.Observer {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ImageView mBackButton;
    public SubscreenCustomizerActivity mContext;
    public final C20151 mDisplayListener = new DisplayLifecycle.Observer() { // from class: com.android.systemui.qp.customize.SubscreenCustomizerActivity.1
        @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
        public final void onFolderStateChanged(boolean z) {
            if (z) {
                int i = SubscreenCustomizerActivity.$r8$clinit;
                SubscreenCustomizerActivity.this.finish();
            }
        }
    };
    public WakefulnessLifecycle mWakefulnessLifeCycle;

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("SubscreenCustomizerActivity", "SubscreenCustomizerActivity oncreate");
        setContentView(R.layout.subscreen_customize_panel_content);
        this.mContext = this;
        ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).addObserver(this.mDisplayListener);
        boolean z = QpRune.QUICK_PANEL_BLUR_DEFAULT;
        View decorView = getWindow().getDecorView();
        if (decorView != null) {
            decorView.semSetRoundedCorners(0);
        }
        getWindow().setNavigationBarContrastEnforced(false);
        getWindow().setNavigationBarColor(0);
        getWindow().setFlags(1024, 1024);
        setShowWhenLocked(true);
        WakefulnessLifecycle wakefulnessLifecycle = (WakefulnessLifecycle) Dependency.get(WakefulnessLifecycle.class);
        this.mWakefulnessLifeCycle = wakefulnessLifecycle;
        if (wakefulnessLifecycle != null) {
            wakefulnessLifecycle.addObserver(this);
        }
        this.mBackButton = (ImageView) findViewById(R.id.subroom_back_button);
        this.mBackButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qp.customize.SubscreenCustomizerActivity$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                SubscreenCustomizerActivity subscreenCustomizerActivity = SubscreenCustomizerActivity.this;
                int i = SubscreenCustomizerActivity.$r8$clinit;
                subscreenCustomizerActivity.finish();
            }
        });
        SubscreenUtil.applyRotation(this.mContext, this.mBackButton);
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).removeObserver(this.mDisplayListener);
        WakefulnessLifecycle wakefulnessLifecycle = this.mWakefulnessLifeCycle;
        if (wakefulnessLifecycle != null) {
            wakefulnessLifecycle.removeObserver(this);
            this.mWakefulnessLifeCycle = null;
        }
        finish();
        super.onDestroy();
    }

    @Override // android.app.Activity
    public final void onStart() {
        Log.d("SubscreenCustomizerActivity", "SubscreenCustomizerActivity onStart");
        super.onStart();
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public final void onStartedGoingToSleep() {
        Log.d("SubscreenCustomizerActivity", "onStartedGoingToSleep");
        finish();
    }
}
