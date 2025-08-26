package com.android.systemui.util.settings;

import android.database.ContentObserver;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes3.dex */
public final class SettingsProxyExt {
    public static final SettingsProxyExt INSTANCE = new SettingsProxyExt();

    /* renamed from: com.android.systemui.util.settings.SettingsProxyExt$observerFlow$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ String[] $names;
        final /* synthetic */ UserSettingsProxy $this_observerFlow;
        final /* synthetic */ int $userId;
        int I$0;
        int I$1;
        int I$2;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(String[] strArr, UserSettingsProxy userSettingsProxy, int i, Continuation continuation) {
            super(2, continuation);
            this.$names = strArr;
            this.$this_observerFlow = userSettingsProxy;
            this.$userId = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit invokeSuspend$lambda$1(UserSettingsProxy userSettingsProxy, SettingsProxyExt$observerFlow$1$observer$1 settingsProxyExt$observerFlow$1$observer$1) {
            userSettingsProxy.unregisterContentObserverAsync(settingsProxyExt$observerFlow$1$observer$1);
            return Unit.INSTANCE;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.$names, this.$this_observerFlow, this.$userId, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x0068, code lost:
        
            if (r11 == r0) goto L18;
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x0083, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r9, r1, r10) == r0) goto L18;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:12:0x0052  */
        /* JADX WARN: Removed duplicated region for block: B:16:0x006d  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:13:0x0068 -> B:15:0x006b). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            SettingsProxyExt$observerFlow$1$observer$1 settingsProxyExt$observerFlow$1$observer$1;
            int length;
            String[] strArr;
            int i;
            UserSettingsProxy userSettingsProxy;
            int i2;
            ProducerScope producerScope;
            Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i3 = this.label;
            if (i3 == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope2 = (ProducerScope) this.L$0;
                ContentObserver contentObserver = new ContentObserver() { // from class: com.android.systemui.util.settings.SettingsProxyExt$observerFlow$1$observer$1
                    {
                        super(null);
                    }

                    @Override // android.database.ContentObserver
                    public void onChange(boolean z) {
                        ((ChannelCoroutine) producerScope2).mo3476trySendJP2dKIU(Unit.INSTANCE);
                    }
                };
                String[] strArr2 = this.$names;
                UserSettingsProxy userSettingsProxy2 = this.$this_observerFlow;
                int i4 = this.$userId;
                settingsProxyExt$observerFlow$1$observer$1 = contentObserver;
                length = strArr2.length;
                strArr = strArr2;
                i = 0;
                userSettingsProxy = userSettingsProxy2;
                i2 = i4;
                producerScope = producerScope2;
                if (i >= length) {
                }
                return obj2;
            }
            if (i3 != 1) {
                if (i3 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            length = this.I$2;
            i = this.I$1;
            i2 = this.I$0;
            UserSettingsProxy userSettingsProxy3 = (UserSettingsProxy) this.L$3;
            strArr = (String[]) this.L$2;
            SettingsProxyExt$observerFlow$1$observer$1 settingsProxyExt$observerFlow$1$observer$12 = (SettingsProxyExt$observerFlow$1$observer$1) this.L$1;
            producerScope = (ProducerScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            UserSettingsProxy userSettingsProxy4 = userSettingsProxy3;
            SettingsProxyExt$observerFlow$1$observer$1 settingsProxyExt$observerFlow$1$observer$13 = settingsProxyExt$observerFlow$1$observer$12;
            i++;
            userSettingsProxy = userSettingsProxy4;
            settingsProxyExt$observerFlow$1$observer$1 = settingsProxyExt$observerFlow$1$observer$13;
            if (i >= length) {
                String str = strArr[i];
                this.L$0 = producerScope;
                this.L$1 = settingsProxyExt$observerFlow$1$observer$1;
                this.L$2 = strArr;
                this.L$3 = userSettingsProxy;
                this.I$0 = i2;
                this.I$1 = i;
                this.I$2 = length;
                this.label = 1;
                Object objRegisterContentObserverForUser = userSettingsProxy.registerContentObserverForUser(str, settingsProxyExt$observerFlow$1$observer$1, i2, this);
                userSettingsProxy4 = userSettingsProxy;
                settingsProxyExt$observerFlow$1$observer$13 = settingsProxyExt$observerFlow$1$observer$1;
            } else {
                SettingsProxyExt$observerFlow$1$$ExternalSyntheticLambda0 settingsProxyExt$observerFlow$1$$ExternalSyntheticLambda0 = new SettingsProxyExt$observerFlow$1$$ExternalSyntheticLambda0(this.$this_observerFlow, settingsProxyExt$observerFlow$1$observer$1);
                this.L$0 = null;
                this.L$1 = null;
                this.L$2 = null;
                this.L$3 = null;
                this.label = 2;
            }
            return obj2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope producerScope, Continuation continuation) {
            return ((AnonymousClass1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    /* renamed from: com.android.systemui.util.settings.SettingsProxyExt$observerFlow$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ String[] $names;
        final /* synthetic */ SettingsProxy $this_observerFlow;
        int I$0;
        int I$1;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(String[] strArr, SettingsProxy settingsProxy, Continuation continuation) {
            super(2, continuation);
            this.$names = strArr;
            this.$this_observerFlow = settingsProxy;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit invokeSuspend$lambda$1(SettingsProxy settingsProxy, SettingsProxyExt$observerFlow$2$observer$1 settingsProxyExt$observerFlow$2$observer$1) {
            settingsProxy.unregisterContentObserverAsync(settingsProxyExt$observerFlow$2$observer$1);
            return Unit.INSTANCE;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$names, this.$this_observerFlow, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        /* JADX WARN: Code restructure failed: missing block: B:13:0x005f, code lost:
        
            if (r10 == r0) goto L18;
         */
        /* JADX WARN: Code restructure failed: missing block: B:17:0x007a, code lost:
        
            if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r8, r1, r9) == r0) goto L18;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:12:0x004b  */
        /* JADX WARN: Removed duplicated region for block: B:16:0x0064  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:13:0x005f -> B:15:0x0062). Please report as a decompilation issue!!! */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            SettingsProxyExt$observerFlow$2$observer$1 settingsProxyExt$observerFlow$2$observer$1;
            int length;
            String[] strArr;
            int i;
            ProducerScope producerScope;
            SettingsProxy settingsProxy;
            Object obj2 = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i2 = this.label;
            if (i2 == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope2 = (ProducerScope) this.L$0;
                ContentObserver contentObserver = new ContentObserver() { // from class: com.android.systemui.util.settings.SettingsProxyExt$observerFlow$2$observer$1
                    {
                        super(null);
                    }

                    @Override // android.database.ContentObserver
                    public void onChange(boolean z) {
                        ((ChannelCoroutine) producerScope2).mo3476trySendJP2dKIU(Unit.INSTANCE);
                    }
                };
                String[] strArr2 = this.$names;
                SettingsProxy settingsProxy2 = this.$this_observerFlow;
                settingsProxyExt$observerFlow$2$observer$1 = contentObserver;
                length = strArr2.length;
                strArr = strArr2;
                i = 0;
                producerScope = producerScope2;
                settingsProxy = settingsProxy2;
                if (i >= length) {
                }
                return obj2;
            }
            if (i2 != 1) {
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            length = this.I$1;
            i = this.I$0;
            SettingsProxy settingsProxy3 = (SettingsProxy) this.L$3;
            strArr = (String[]) this.L$2;
            SettingsProxyExt$observerFlow$2$observer$1 settingsProxyExt$observerFlow$2$observer$12 = (SettingsProxyExt$observerFlow$2$observer$1) this.L$1;
            producerScope = (ProducerScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            SettingsProxy settingsProxy4 = settingsProxy3;
            SettingsProxyExt$observerFlow$2$observer$1 settingsProxyExt$observerFlow$2$observer$13 = settingsProxyExt$observerFlow$2$observer$12;
            i++;
            settingsProxy = settingsProxy4;
            settingsProxyExt$observerFlow$2$observer$1 = settingsProxyExt$observerFlow$2$observer$13;
            if (i >= length) {
                String str = strArr[i];
                this.L$0 = producerScope;
                this.L$1 = settingsProxyExt$observerFlow$2$observer$1;
                this.L$2 = strArr;
                this.L$3 = settingsProxy;
                this.I$0 = i;
                this.I$1 = length;
                this.label = 1;
                Object objRegisterContentObserver = settingsProxy.registerContentObserver(str, settingsProxyExt$observerFlow$2$observer$1, this);
                settingsProxy4 = settingsProxy;
                settingsProxyExt$observerFlow$2$observer$13 = settingsProxyExt$observerFlow$2$observer$1;
            } else {
                SettingsProxyExt$observerFlow$1$$ExternalSyntheticLambda0 settingsProxyExt$observerFlow$1$$ExternalSyntheticLambda0 = new SettingsProxyExt$observerFlow$1$$ExternalSyntheticLambda0(this.$this_observerFlow, settingsProxyExt$observerFlow$2$observer$1);
                this.L$0 = null;
                this.L$1 = null;
                this.L$2 = null;
                this.L$3 = null;
                this.label = 2;
            }
            return obj2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(ProducerScope producerScope, Continuation continuation) {
            return ((AnonymousClass2) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }
    }

    private SettingsProxyExt() {
    }

    public final Flow observerFlow(UserSettingsProxy userSettingsProxy, int i, String... strArr) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(strArr, userSettingsProxy, i, null);
        conflatedCallbackFlow.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(anonymousClass1);
    }

    public final Flow observerFlow(SettingsProxy settingsProxy, String... strArr) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(strArr, settingsProxy, null);
        conflatedCallbackFlow.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(anonymousClass2);
    }
}
