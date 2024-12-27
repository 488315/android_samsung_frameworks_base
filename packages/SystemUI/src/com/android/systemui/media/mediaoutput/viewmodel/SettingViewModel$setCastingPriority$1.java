package com.android.systemui.media.mediaoutput.viewmodel;

import android.media.MediaRouter2Manager;
import android.media.RoutingSessionInfo;
import android.provider.Settings;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKt;
import com.android.systemui.media.mediaoutput.common.PreferenceKeys;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class SettingViewModel$setCastingPriority$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $value;
    int I$0;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ SettingViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SettingViewModel$setCastingPriority$1(boolean z, SettingViewModel settingViewModel, Continuation continuation) {
        super(2, continuation);
        this.$value = z;
        this.this$0 = settingViewModel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SettingViewModel$setCastingPriority$1(this.$value, this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SettingViewModel$setCastingPriority$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        SettingViewModel settingViewModel;
        Preferences.Key key;
        int i;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = this.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            PreferenceKeys.INSTANCE.getClass();
            Pair pair = new Pair(PreferenceKeys.CASTING_PRIORITY, new Integer(this.$value ? 1 : 0));
            settingViewModel = this.this$0;
            key = (Preferences.Key) pair.component1();
            int intValue = ((Number) pair.component2()).intValue();
            DataStore dataStore = settingViewModel.dataStore;
            SettingViewModel$setCastingPriority$1$1$1 settingViewModel$setCastingPriority$1$1$1 = new SettingViewModel$setCastingPriority$1$1$1(key, intValue, null);
            this.L$0 = pair;
            this.L$1 = settingViewModel;
            this.L$2 = key;
            this.I$0 = intValue;
            this.label = 1;
            if (PreferencesKt.edit(dataStore, settingViewModel$setCastingPriority$1$1$1, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
            i = intValue;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = this.I$0;
            key = (Preferences.Key) this.L$2;
            settingViewModel = (SettingViewModel) this.L$1;
            ResultKt.throwOnFailure(obj);
        }
        Settings.System.putInt(settingViewModel.context.getContentResolver(), key.name, i);
        List remoteSessions = ((MediaRouter2Manager) this.this$0.router2Manager$delegate.getValue()).getRemoteSessions();
        Intrinsics.checkNotNull(remoteSessions);
        List list = remoteSessions.isEmpty() ^ true ? remoteSessions : null;
        if (list != null) {
            boolean z = this.$value;
            ArrayList arrayList = new ArrayList();
            for (Object obj2 : list) {
                RoutingSessionInfo routingSessionInfo = (RoutingSessionInfo) obj2;
                if (z ? Intrinsics.areEqual(routingSessionInfo.getClientPackageName(), "com.samsung.android.audiomirroring") : !Intrinsics.areEqual(routingSessionInfo.getClientPackageName(), "com.samsung.android.audiomirroring")) {
                    arrayList.add(obj2);
                }
            }
            SettingViewModel settingViewModel2 = this.this$0;
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ((MediaRouter2Manager) settingViewModel2.router2Manager$delegate.getValue()).releaseSession((RoutingSessionInfo) it.next());
            }
        }
        return Unit.INSTANCE;
    }
}
