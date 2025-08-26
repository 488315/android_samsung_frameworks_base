package com.android.systemui.media.mediaoutput.activity;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.SemBlurInfo;
import android.view.WindowManager;
import android.widget.PopupWindow;
import com.android.systemui.QpRune;
import com.android.systemui.media.MediaOutputView;
import com.android.systemui.media.mediaoutput.compose.Screen;
import com.android.systemui.media.mediaoutput.compose.common.Feature;
import java.util.ArrayList;
import javax.inject.Provider;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* loaded from: classes2.dex */
public final class MediaOutputWindow {
    public static final Companion Companion = new Companion(null);
    public final Context context;
    public StandaloneCoroutine job;
    public final Provider mediaOutputViewProvider;
    public PopupWindow popupWindow;
    public final Lazy params$delegate = LazyKt__LazyJVMKt.lazy(new MediaOutputWindow$$ExternalSyntheticLambda0());
    public final Lazy isNightMode$delegate = LazyKt__LazyJVMKt.lazy(new MediaOutputWindow$$ExternalSyntheticLambda1(this, 0));

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public MediaOutputWindow(Context context, Provider provider) {
        this.context = context;
        this.mediaOutputViewProvider = provider;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x004f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void show(Intent intent) {
        Feature.Builder builder = new Feature.Builder();
        builder.getFeature().from = intent.getIntExtra("extra_from", -1);
        builder.getFeature().isWindow = true;
        builder.getFeature().dismissCallback = new MediaOutputWindow$$ExternalSyntheticLambda1(this, 1);
        ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra("extra_device_ids");
        if (stringArrayListExtra == null) {
            builder.getFeature().defaultScreen = Screen.Phone.INSTANCE;
        } else {
            if (stringArrayListExtra.isEmpty()) {
                stringArrayListExtra = null;
            }
            if (stringArrayListExtra != null) {
                builder.getFeature().defaultScreen = stringArrayListExtra.size() > 1 ? Screen.Selector.INSTANCE : Screen.TV.INSTANCE;
                builder.getFeature().deviceIds = stringArrayListExtra;
            }
        }
        Feature feature = builder.getFeature();
        if (this.popupWindow != null) {
            Log.d("MediaOutputWindow", "show() - already shown");
            return;
        }
        Log.d("MediaOutputWindow", "show()");
        MediaOutputView mediaOutputView = (MediaOutputView) this.mediaOutputViewProvider.get();
        mediaOutputView.setId(R.id.content);
        mediaOutputView.feature = feature;
        if (QpRune.QUICK_PANEL_BLUR_DEFAULT) {
            mediaOutputView.semSetBlurInfo(new SemBlurInfo.Builder(0).setColorCurvePreset(((Boolean) this.isNightMode$delegate.getValue()).booleanValue() ? 135 : 132).setBackgroundCornerRadius(0.0f).build());
        } else {
            mediaOutputView.setBackgroundColor(Color.parseColor("#5D5D5D"));
        }
        PopupWindow popupWindow = new PopupWindow(mediaOutputView);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.android.systemui.media.mediaoutput.activity.MediaOutputWindow$show$2$1$1
            @Override // android.widget.PopupWindow.OnDismissListener
            public final void onDismiss() {
                StandaloneCoroutine standaloneCoroutine = this.this$0.job;
                if (standaloneCoroutine != null) {
                    standaloneCoroutine.cancel(null);
                }
                this.this$0.job = null;
            }
        });
        this.popupWindow = popupWindow;
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        this.job = BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(MainDispatcherLoader.dispatcher), null, null, new MediaOutputWindow$show$2$2(this, null), 3);
        PopupWindow popupWindow2 = this.popupWindow;
        (popupWindow2 != null ? popupWindow2 : null).semShowPopupWindow((WindowManager.LayoutParams) this.params$delegate.getValue());
    }
}
