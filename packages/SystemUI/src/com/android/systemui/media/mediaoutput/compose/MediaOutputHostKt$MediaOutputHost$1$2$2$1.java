package com.android.systemui.media.mediaoutput.compose;

import android.util.Log;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.State;
import androidx.navigation.NavBackStackEntry;
import com.android.systemui.media.mediaoutput.analytics.MoSaLogging;
import com.android.systemui.media.mediaoutput.analytics.SaScreen;
import com.android.systemui.media.mediaoutput.common.DeviceUtils;
import com.android.systemui.media.mediaoutput.compose.Screen;
import com.android.systemui.util.SystemUIAnalytics;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class MediaOutputHostKt$MediaOutputHost$1$2$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ State<NavBackStackEntry> $backStackEntry$delegate;
    final /* synthetic */ MutableState<String> $lastEntryRoute$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaOutputHostKt$MediaOutputHost$1$2$2$1(State<NavBackStackEntry> state, MutableState<String> mutableState, Continuation continuation) {
        super(2, continuation);
        this.$backStackEntry$delegate = state;
        this.$lastEntryRoute$delegate = mutableState;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MediaOutputHostKt$MediaOutputHost$1$2$2$1(this.$backStackEntry$delegate, this.$lastEntryRoute$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaOutputHostKt$MediaOutputHost$1$2$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Screen screen;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        NavBackStackEntry navBackStackEntry = (NavBackStackEntry) this.$backStackEntry$delegate.getValue();
        if (navBackStackEntry != null) {
            if (Intrinsics.areEqual((String) this.$lastEntryRoute$delegate.getValue(), navBackStackEntry.destination.route)) {
                navBackStackEntry = null;
            }
            if (navBackStackEntry != null) {
                MutableState<String> mutableState = this.$lastEntryRoute$delegate;
                mutableState.setValue(navBackStackEntry.destination.route);
                Log.d("MediaOutputHost", "current entry : " + navBackStackEntry + ", " + navBackStackEntry.getArguments());
                String str = (String) mutableState.getValue();
                if (str != null) {
                    Screen.Companion.getClass();
                    Screen screen2 = Screen.Phone.INSTANCE;
                    if (str.equals(screen2.route)) {
                        screen = screen2;
                    } else {
                        screen = Screen.TV.INSTANCE;
                        if (!str.equals(screen.route)) {
                            screen = Screen.Selector.INSTANCE;
                            if (!str.equals(screen.route)) {
                                screen = Screen.SettingHome.INSTANCE;
                                if (!str.equals(screen.route)) {
                                    screen = Screen.CastSetting.INSTANCE;
                                    if (!str.equals(screen.route)) {
                                        screen = null;
                                    }
                                }
                            }
                        }
                    }
                    if (screen != null) {
                        SaScreen saScreen = screen.equals(screen2) ? SaScreen.MediaOutput.INSTANCE : screen.equals(Screen.TV.INSTANCE) ? SaScreen.TvCard.INSTANCE : screen.equals(Screen.Selector.INSTANCE) ? SaScreen.ChooseADevice.INSTANCE : screen.equals(Screen.SettingHome.INSTANCE) ? SaScreen.MediaOutputSettings.INSTANCE : screen.equals(Screen.CastSetting.INSTANCE) ? SaScreen.WifiSpeakerPlaybackPrefs.INSTANCE : null;
                        if (saScreen != null) {
                            MoSaLogging.INSTANCE.getClass();
                            Log.d("MoSaLogging", "send Screen - " + saScreen);
                            DeviceUtils.INSTANCE.getClass();
                            if ((((Boolean) DeviceUtils.isDebug$delegate.getValue()).booleanValue() ? null : saScreen) != null) {
                                MoSaLogging.currentScreen = saScreen;
                                SystemUIAnalytics.sendScreenViewLog(saScreen.id);
                            }
                        }
                    }
                }
            }
        }
        return Unit.INSTANCE;
    }
}
