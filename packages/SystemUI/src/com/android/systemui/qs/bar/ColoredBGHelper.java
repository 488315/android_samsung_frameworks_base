package com.android.systemui.qs.bar;

import android.app.OnSemColorsChangedListener;
import android.app.SemWallpaperColors;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.vectordrawable.graphics.drawable.AnimatorInflaterCompat$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.systemui.bixby2.controller.NotificationController$$ExternalSyntheticOutline0;
import com.android.systemui.qs.bar.domain.interactor.ColoredBackgroundInteractor;
import com.android.systemui.qs.bar.repository.ColoredBackgroundRepository;
import com.sec.ims.configuration.DATA;
import java.util.ArrayList;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.HexExtensionsKt;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ColoredBGHelper implements OnSemColorsChangedListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ColoredBackgroundInteractor coloredBackgroundInteractor;
    public final Context context;
    public int curAlpha;
    public final WallpaperManager wallpaperManager;
    public final Handler handler = new Handler(Looper.getMainLooper());
    public final ArrayList barBGList = new ArrayList();
    public final ArrayList noRippleBarBGList = new ArrayList();
    public int actualAppliedColor = -1;
    public int curExtractColor = -1;
    public final int WALLPAPER_FIXED_ALPHA = HexExtensionsKt.hexToInt$default("3d");
    public final int THEME_FIXED_ALPHA = HexExtensionsKt.hexToInt$default(DATA.DM_FIELD_INDEX.PREF_CSCF_PORT);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.qs.bar.ColoredBGHelper$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.systemui.qs.bar.ColoredBGHelper$1$1, reason: invalid class name and collision with other inner class name */
        final class C02361 extends SuspendLambda implements Function2 {
            /* synthetic */ int I$0;
            int label;
            final /* synthetic */ ColoredBGHelper this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C02361(ColoredBGHelper coloredBGHelper, Continuation continuation) {
                super(2, continuation);
                this.this$0 = coloredBGHelper;
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C02361 c02361 = new C02361(this.this$0, continuation);
                c02361.I$0 = ((Number) obj).intValue();
                return c02361;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return ((C02361) create(Integer.valueOf(((Number) obj).intValue()), (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("bgColor from interactor = 0x", Integer.toHexString(this.I$0), "ColoredBGHelper");
                ColoredBGHelper coloredBGHelper = this.this$0;
                int i = ColoredBGHelper.$r8$clinit;
                int bGColor = coloredBGHelper.getBGColor();
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("updateBGColor extractColor = ", ColoredBGHelper.toCompareColorString(coloredBGHelper.curExtractColor, bGColor), "ColoredBGHelper");
                ArrayList arrayList = coloredBGHelper.barBGList;
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj2 = arrayList.get(i2);
                    i2++;
                    coloredBGHelper.setBackGroundDrawable((View) obj2, bGColor);
                }
                return Unit.INSTANCE;
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return ColoredBGHelper.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ColoredBGHelper coloredBGHelper = ColoredBGHelper.this;
                ReadonlyStateFlow readonlyStateFlow = coloredBGHelper.coloredBackgroundInteractor.backgroundColor;
                C02361 c02361 = new C02361(coloredBGHelper, null);
                this.label = 1;
                if (FlowKt.collectLatest(readonlyStateFlow, c02361, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

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

    public ColoredBGHelper(Context context, CoroutineScope coroutineScope, ColoredBackgroundInteractor coloredBackgroundInteractor) {
        this.context = context;
        this.coloredBackgroundInteractor = coloredBackgroundInteractor;
        this.wallpaperManager = WallpaperManager.getInstance(context);
        Log.d("ColoredBGHelper", "init");
        BuildersKt.launch$default(coroutineScope, null, null, new AnonymousClass1(null), 3);
        FlowKt.transformLatest(coloredBackgroundInteractor.backgroundColor, new ColoredBGHelper$requestUpdateColoredBackground$1(null));
    }

    public static String toCompareColorString(int i, int i2) {
        return AnimatorInflaterCompat$$ExternalSyntheticOutline0.m("0x", Integer.toHexString(i), " > 0x", Integer.toHexString(i2));
    }

    public final void addBarBackground(View view, boolean z) {
        int i = this.curExtractColor;
        if (i != -1) {
            setBackGroundDrawable(view, i);
        } else {
            setBackGroundDrawable(view, getBGColor());
        }
        if (!this.barBGList.contains(view)) {
            this.barBGList.add(view);
        }
        if (!z || this.noRippleBarBGList.contains(view)) {
            return;
        }
        this.noRippleBarBGList.add(view);
    }

    public final int getBGColor() {
        return ((Number) this.coloredBackgroundInteractor.backgroundColor.$$delegate_0.getValue()).intValue();
    }

    public final void onColorsChanged(SemWallpaperColors semWallpaperColors, int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "onColorsChanged which = ", "ColoredBGHelper");
        ColoredBackgroundRepository coloredBackgroundRepository = this.coloredBackgroundInteractor.coloredBackgroundRepository;
        coloredBackgroundRepository.getClass();
        Log.d(ColoredBackgroundRepository.TAG, "notifyWallPaperColorChanged called");
        coloredBackgroundRepository.wallPaperColorChanged.tryEmit(Unit.INSTANCE);
    }

    public final void removeFromBarBackground(View view) {
        if (this.barBGList.contains(view)) {
            this.barBGList.remove(view);
        }
    }

    public final void setBackGroundDrawable(View view, int i) {
        Drawable findDrawableByLayerId;
        int i2 = !StringsKt__StringsJVMKt.equals(Integer.toHexString(this.context.getResources().getColor(R.color.qs_tile_container_bg)), "3d000000", true) ? this.THEME_FIXED_ALPHA : this.WALLPAPER_FIXED_ALPHA;
        if (i != this.curExtractColor || i2 != this.curAlpha) {
            int argb = Color.argb(i2, Color.red(i), Color.green(i), Color.blue(i));
            Log.d("ColoredBGHelper", NotificationController$$ExternalSyntheticOutline0.m(MutableObjectList$$ExternalSyntheticOutline0.m(this.curAlpha, i2, "setBackGroundDrawable alpha = ", " > ", ", color = "), toCompareColorString(this.curExtractColor, i), ", actualAppliedColor = ", toCompareColorString(this.actualAppliedColor, argb), Debug.getCallers(3, " ")));
            this.actualAppliedColor = argb;
            this.curExtractColor = i;
            this.curAlpha = i2;
        }
        if (view != null) {
            Drawable drawable = this.noRippleBarBGList.contains(view) ? this.context.getDrawable(R.drawable.sec_tile_layout_background) : this.context.getDrawable(R.drawable.sec_coloring_container_background);
            if (drawable == null || (findDrawableByLayerId = ((LayerDrawable) drawable).findDrawableByLayerId(R.id.colored_bg_solid)) == null) {
                return;
            }
            findDrawableByLayerId.setTint(this.actualAppliedColor);
            view.setBackground(drawable);
        }
    }
}
