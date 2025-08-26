package com.android.systemui.communal.data.db;

import android.content.ComponentName;
import android.os.UserHandle;
import android.os.UserManager;
import androidx.room.RoomDatabase;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.communal.shared.model.SpanValue;
import com.android.systemui.communal.widgets.CommunalWidgetHost;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.Logger;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.domain.interactor.UserLockedInteractor;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import javax.inject.Provider;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: classes2.dex */
public final class DefaultWidgetPopulation extends RoomDatabase.Callback {
    public final CoroutineScope bgScope;
    public final Provider communalWidgetDaoProvider;
    public final CommunalWidgetHost communalWidgetHost;
    public final String[] defaultWidgets;
    public final Logger logger;
    public SkipReason skipReason = SkipReason.NONE;
    public final UserLockedInteractor userLockedInteractor;
    public final UserManager userManager;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class SkipReason {
        public static final /* synthetic */ SkipReason[] $VALUES;
        public static final SkipReason NONE;
        public static final SkipReason RESTORED_FROM_BACKUP;

        static {
            SkipReason skipReason = new SkipReason(PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE, 0);
            NONE = skipReason;
            SkipReason skipReason2 = new SkipReason("RESTORED_FROM_BACKUP", 1);
            RESTORED_FROM_BACKUP = skipReason2;
            SkipReason[] skipReasonArr = {skipReason, skipReason2};
            $VALUES = skipReasonArr;
            EnumEntriesKt.enumEntries(skipReasonArr);
        }

        private SkipReason(String str, int i) {
        }

        public static SkipReason valueOf(String str) {
            return (SkipReason) Enum.valueOf(SkipReason.class, str);
        }

        public static SkipReason[] values() {
            return (SkipReason[]) $VALUES.clone();
        }
    }

    /* renamed from: com.android.systemui.communal.data.db.DefaultWidgetPopulation$onCreate$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        /* renamed from: com.android.systemui.communal.data.db.DefaultWidgetPopulation$onCreate$1$1, reason: invalid class name and collision with other inner class name */
        final class C01641 extends SuspendLambda implements Function2 {
            /* synthetic */ boolean Z$0;
            int label;

            public C01641(Continuation continuation) {
                super(2, continuation);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Continuation create(Object obj, Continuation continuation) {
                C01641 c01641 = new C01641(continuation);
                c01641.Z$0 = ((Boolean) obj).booleanValue();
                return c01641;
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Boolean bool = (Boolean) obj;
                bool.booleanValue();
                return ((C01641) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
            }

            @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
            public final Object invokeSuspend(Object obj) {
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                if (this.label != 0) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Boolean.valueOf(this.Z$0);
            }
        }

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return DefaultWidgetPopulation.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Integer numAllocateIdAndBindWidget;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                DefaultWidgetPopulation defaultWidgetPopulation = DefaultWidgetPopulation.this;
                UserLockedInteractor userLockedInteractor = defaultWidgetPopulation.userLockedInteractor;
                Flow flowFlowOn = FlowKt.flowOn(((UserRepositoryImpl) userLockedInteractor.userRepository).isUserUnlocked(defaultWidgetPopulation.userManager.getMainUser()), userLockedInteractor.backgroundDispatcher);
                C01641 c01641 = new C01641(null);
                this.label = 1;
                if (FlowKt.first(flowFlowOn, c01641, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            DefaultWidgetPopulation defaultWidgetPopulation2 = DefaultWidgetPopulation.this;
            UserHandle mainUser = defaultWidgetPopulation2.userManager.getMainUser();
            if (mainUser != null) {
                int userSerialNumber = defaultWidgetPopulation2.userManager.getUserSerialNumber(mainUser.getIdentifier());
                String[] strArr = defaultWidgetPopulation2.defaultWidgets;
                int length = strArr.length;
                int i2 = 0;
                int i3 = 0;
                while (i2 < length) {
                    String str = strArr[i2];
                    int i4 = i3 + 1;
                    ComponentName componentNameUnflattenFromString = ComponentName.unflattenFromString(str);
                    if (componentNameUnflattenFromString != null && (numAllocateIdAndBindWidget = defaultWidgetPopulation2.communalWidgetHost.allocateIdAndBindWidget(componentNameUnflattenFromString, mainUser)) != null) {
                        ((CommunalWidgetDao_Impl) ((CommunalWidgetDao) defaultWidgetPopulation2.communalWidgetDaoProvider.get())).addWidget(numAllocateIdAndBindWidget.intValue(), str, Integer.valueOf(i3), userSerialNumber, SpanValue.Fixed.m1077boximpl(3));
                    }
                    i2++;
                    i3 = i4;
                }
                Logger.i$default(defaultWidgetPopulation2.logger, "Populated default widgets in the database.", null, 2, null);
            }
            return Unit.INSTANCE;
        }
    }

    static {
        new Companion(null);
    }

    public DefaultWidgetPopulation(CoroutineScope coroutineScope, CommunalWidgetHost communalWidgetHost, Provider provider, String[] strArr, LogBuffer logBuffer, UserManager userManager, UserLockedInteractor userLockedInteractor) {
        this.bgScope = coroutineScope;
        this.communalWidgetHost = communalWidgetHost;
        this.communalWidgetDaoProvider = provider;
        this.defaultWidgets = strArr;
        this.userManager = userManager;
        this.userLockedInteractor = userLockedInteractor;
        this.logger = new Logger(logBuffer, "DefaultWidgetPopulation");
    }

    @Override // androidx.room.RoomDatabase.Callback
    public final void onCreate() {
        SkipReason skipReason = this.skipReason;
        if (skipReason == SkipReason.NONE) {
            CoroutineTracingKt.launchTraced$default(this.bgScope, null, null, new AnonymousClass1(null), 7);
        } else {
            Logger.i$default(this.logger, "Skipped populating default widgets. Reason: " + skipReason, null, 2, null);
        }
    }
}
