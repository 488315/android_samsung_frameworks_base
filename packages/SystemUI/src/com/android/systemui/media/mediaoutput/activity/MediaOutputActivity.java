package com.android.systemui.media.mediaoutput.activity;

import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;
import android.view.View;
import androidx.activity.ComponentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import com.android.systemui.media.mediaoutput.viewmodel.ViewModelFactory;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaOutputActivity extends ComponentActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public MediaOutputActivity$onCreate$5 actionScreenReceiver;
    public final AudioManager audioManager;
    public final ViewModelFactory factory;
    public final Lazy onUnhandledKeyEventListener$delegate = LazyKt__LazyJVMKt.lazy(new MediaOutputActivity$$ExternalSyntheticLambda0(this, 0));

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
    }

    public MediaOutputActivity(ViewModelFactory viewModelFactory, AudioManager audioManager) {
        this.factory = viewModelFactory;
        this.audioManager = audioManager;
    }

    @Override // androidx.activity.ComponentActivity, androidx.lifecycle.HasDefaultViewModelProviderFactory
    public final CreationExtras getDefaultViewModelCreationExtras() {
        Intent intent = getIntent();
        if (intent != null) {
            intent.putExtra("displayId", getDisplay().getDisplayId());
        }
        return super.getDefaultViewModelCreationExtras();
    }

    @Override // androidx.activity.ComponentActivity, androidx.lifecycle.HasDefaultViewModelProviderFactory
    public final ViewModelProvider.Factory getDefaultViewModelProviderFactory() {
        return this.factory;
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        getWindow().getDecorView().addOnUnhandledKeyEventListener((View.OnUnhandledKeyEventListener) this.onUnhandledKeyEventListener$delegate.getValue());
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:12:0x003a  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0091  */
    /* JADX WARN: Type inference failed for: r3v2, types: [android.content.BroadcastReceiver, com.android.systemui.media.mediaoutput.activity.MediaOutputActivity$onCreate$5] */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreate(android.os.Bundle r9) {
        /*
            Method dump skipped, instructions count: 291
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.activity.MediaOutputActivity.onCreate(android.os.Bundle):void");
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        Log.d("MediaOutputActivity", "onDestroy()");
        super.onDestroy();
        MediaOutputActivity$onCreate$5 mediaOutputActivity$onCreate$5 = this.actionScreenReceiver;
        if (mediaOutputActivity$onCreate$5 != null) {
            unregisterReceiver(mediaOutputActivity$onCreate$5);
            this.actionScreenReceiver = null;
        }
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getWindow().getDecorView().removeOnUnhandledKeyEventListener((View.OnUnhandledKeyEventListener) this.onUnhandledKeyEventListener$delegate.getValue());
    }
}
