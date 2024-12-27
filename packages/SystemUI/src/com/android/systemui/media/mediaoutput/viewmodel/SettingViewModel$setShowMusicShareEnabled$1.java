package com.android.systemui.media.mediaoutput.viewmodel;

import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKt;
import com.android.systemui.media.mediaoutput.common.PreferenceKeys;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class SettingViewModel$setShowMusicShareEnabled$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $value;
    Object L$0;
    int label;
    final /* synthetic */ SettingViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SettingViewModel$setShowMusicShareEnabled$1(boolean z, SettingViewModel settingViewModel, Continuation continuation) {
        super(2, continuation);
        this.$value = z;
        this.this$0 = settingViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SettingViewModel$setShowMusicShareEnabled$1(this.$value, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SettingViewModel$setShowMusicShareEnabled$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            PreferenceKeys.INSTANCE.getClass();
            Pair pair = new Pair(PreferenceKeys.SHOW_MUSIC_SHARE, new Integer(this.$value ? 1 : 0));
            SettingViewModel settingViewModel = this.this$0;
            Preferences.Key key = (Preferences.Key) pair.component1();
            int intValue = ((Number) pair.component2()).intValue();
            DataStore dataStore = settingViewModel.dataStore;
            SettingViewModel$setShowMusicShareEnabled$1$1$1 settingViewModel$setShowMusicShareEnabled$1$1$1 = new SettingViewModel$setShowMusicShareEnabled$1$1$1(key, intValue, null);
            this.L$0 = pair;
            this.label = 1;
            if (PreferencesKt.edit(dataStore, settingViewModel$setShowMusicShareEnabled$1$1$1, this) == coroutineSingletons) {
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
