package com.android.systemui.accessibility.data.repository;

import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SettingsProxyExt;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes.dex */
public final class UserA11yQsShortcutsRepository {
    public final SecureSettings secureSettings;
    public final ReadonlySharedFlow targets;
    public final int userId;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Factory {
        UserA11yQsShortcutsRepository create(int i);
    }

    static {
        new Companion(null);
    }

    public UserA11yQsShortcutsRepository(int i, SecureSettings secureSettings, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        this.userId = i;
        this.secureSettings = secureSettings;
        final FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new UserA11yQsShortcutsRepository$targets$1(null), SettingsProxyExt.INSTANCE.observerFlow(secureSettings, i, "accessibility_qs_targets"));
        this.targets = FlowKt.shareIn(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.accessibility.data.repository.UserA11yQsShortcutsRepository$special$$inlined$map$1

            /* renamed from: com.android.systemui.accessibility.data.repository.UserA11yQsShortcutsRepository$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ UserA11yQsShortcutsRepository this$0;

                /* renamed from: com.android.systemui.accessibility.data.repository.UserA11yQsShortcutsRepository$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, UserA11yQsShortcutsRepository userA11yQsShortcutsRepository) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = userA11yQsShortcutsRepository;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        UserA11yQsShortcutsRepository userA11yQsShortcutsRepository = this.this$0;
                        String stringForUser = userA11yQsShortcutsRepository.secureSettings.getStringForUser("accessibility_qs_targets", userA11yQsShortcutsRepository.userId);
                        if (stringForUser == null) {
                            stringForUser = "";
                        }
                        List listSplit$default = StringsKt__StringsKt.split$default(stringForUser, new String[]{":"}, 0, 6);
                        ArrayList arrayList = new ArrayList();
                        for (Object obj3 : listSplit$default) {
                            if (((String) obj3).length() != 0) {
                                arrayList.add(obj3);
                            }
                        }
                        Set set = CollectionsKt___CollectionsKt.toSet(arrayList);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(set, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineDispatcher), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), 1);
    }
}
