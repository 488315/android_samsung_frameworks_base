package com.android.systemui.shared.customization.data.content;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.android.systemui.shared.customization.data.content.CustomizationProviderClient;
import com.android.systemui.shared.customization.data.content.CustomizationProviderContract;
import java.io.IOException;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.collections.EmptyList;
import kotlin.collections.builders.ListBuilder;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;

/* loaded from: classes3.dex */
public final class CustomizationProviderClientImpl implements CustomizationProviderClient {
    public final CoroutineDispatcher backgroundDispatcher;
    public final Context context;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$deleteAllSelections$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $slotId;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(String str, Continuation continuation) {
            super(2, continuation);
            this.$slotId = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CustomizationProviderClientImpl.this.new AnonymousClass2(this.$slotId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ContentResolver contentResolver = CustomizationProviderClientImpl.this.context.getContentResolver();
            CustomizationProviderContract.LockScreenQuickAffordances.SelectionTable.INSTANCE.getClass();
            return new Integer(contentResolver.delete(CustomizationProviderContract.LockScreenQuickAffordances.SelectionTable.URI, "slot_id", new String[]{this.$slotId}));
        }
    }

    /* renamed from: com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$insertSelection$2, reason: invalid class name and case insensitive filesystem */
    final class C10472 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $affordanceId;
        final /* synthetic */ String $slotId;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C10472(String str, String str2, Continuation continuation) {
            super(2, continuation);
            this.$slotId = str;
            this.$affordanceId = str2;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CustomizationProviderClientImpl.this.new C10472(this.$slotId, this.$affordanceId, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C10472) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ContentResolver contentResolver = CustomizationProviderClientImpl.this.context.getContentResolver();
            CustomizationProviderContract.LockScreenQuickAffordances.SelectionTable.INSTANCE.getClass();
            Uri uri = CustomizationProviderContract.LockScreenQuickAffordances.SelectionTable.URI;
            ContentValues contentValues = new ContentValues();
            String str = this.$slotId;
            String str2 = this.$affordanceId;
            contentValues.put("slot_id", str);
            contentValues.put("affordance_id", str2);
            Unit unit = Unit.INSTANCE;
            return contentResolver.insert(uri, contentValues);
        }
    }

    /* renamed from: com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$querySelections$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return CustomizationProviderClientImpl.this.querySelections(this);
        }
    }

    /* renamed from: com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$querySelections$2, reason: invalid class name and case insensitive filesystem */
    final class C10482 extends SuspendLambda implements Function2 {
        int label;

        public C10482(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return CustomizationProviderClientImpl.this.new C10482(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C10482) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws IOException {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            ContentResolver contentResolver = CustomizationProviderClientImpl.this.context.getContentResolver();
            CustomizationProviderContract.LockScreenQuickAffordances.SelectionTable.INSTANCE.getClass();
            Cursor cursorQuery = contentResolver.query(CustomizationProviderContract.LockScreenQuickAffordances.SelectionTable.URI, null, null, null, null);
            if (cursorQuery == null) {
                return null;
            }
            try {
                ListBuilder listBuilderCreateListBuilder = CollectionsKt__CollectionsJVMKt.createListBuilder();
                int columnIndex = cursorQuery.getColumnIndex("slot_id");
                int columnIndex2 = cursorQuery.getColumnIndex("affordance_id");
                int columnIndex3 = cursorQuery.getColumnIndex("affordance_name");
                if (columnIndex != -1 && columnIndex2 != -1 && columnIndex3 != -1) {
                    while (cursorQuery.moveToNext()) {
                        listBuilderCreateListBuilder.add(new CustomizationProviderClient.Selection(cursorQuery.getString(columnIndex), cursorQuery.getString(columnIndex2), cursorQuery.getString(columnIndex3)));
                    }
                }
                ListBuilder listBuilderBuild = listBuilderCreateListBuilder.build();
                cursorQuery.close();
                return listBuilderBuild;
            } finally {
            }
        }
    }

    static {
        new Companion(null);
    }

    public CustomizationProviderClientImpl(Context context, CoroutineDispatcher coroutineDispatcher) {
        this.context = context;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    public final Object deleteAllSelections(String str, Continuation continuation) throws Throwable {
        Object objWithContext = BuildersKt.withContext(this.backgroundDispatcher, new AnonymousClass2(str, null), continuation);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }

    public final Object insertSelection(String str, String str2, Continuation continuation) throws Throwable {
        Object objWithContext = BuildersKt.withContext(this.backgroundDispatcher, new C10472(str, str2, null), continuation);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$observeSelections$$inlined$map$1] */
    public final CustomizationProviderClientImpl$observeSelections$$inlined$map$1 observeSelections() {
        CustomizationProviderContract.LockScreenQuickAffordances.SelectionTable.INSTANCE.getClass();
        final Flow flowFlowOn = FlowKt.flowOn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new CustomizationProviderClientImpl$observeUri$2(null), FlowKt.callbackFlow(new CustomizationProviderClientImpl$observeUri$1(this, CustomizationProviderContract.LockScreenQuickAffordances.SelectionTable.URI, null))), this.backgroundDispatcher);
        return new Flow() { // from class: com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$observeSelections$$inlined$map$1

            /* renamed from: com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$observeSelections$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ CustomizationProviderClientImpl this$0;

                /* renamed from: com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl$observeSelections$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, CustomizationProviderClientImpl customizationProviderClientImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = customizationProviderClientImpl;
                }

                /* JADX WARN: Code restructure failed: missing block: B:21:0x0058, code lost:
                
                    if (r5.emit(r7, r0) == r1) goto L22;
                 */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) throws Throwable {
                    AnonymousClass1 anonymousClass1;
                    FlowCollector flowCollector;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object objQuerySelections = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(objQuerySelections);
                        FlowCollector flowCollector2 = this.$this_unsafeFlow;
                        anonymousClass1.L$0 = flowCollector2;
                        anonymousClass1.label = 1;
                        objQuerySelections = this.this$0.querySelections(anonymousClass1);
                        if (objQuerySelections != coroutineSingletons) {
                            flowCollector = flowCollector2;
                        }
                        return coroutineSingletons;
                    }
                    if (i2 != 1) {
                        if (i2 != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(objQuerySelections);
                        return Unit.INSTANCE;
                    }
                    flowCollector = (FlowCollector) anonymousClass1.L$0;
                    ResultKt.throwOnFailure(objQuerySelections);
                    anonymousClass1.L$0 = null;
                    anonymousClass1.label = 2;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowFlowOn.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object querySelections(ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object objWithContext = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objWithContext);
            C10482 c10482 = new C10482(null);
            anonymousClass1.label = 1;
            objWithContext = BuildersKt.withContext(this.backgroundDispatcher, c10482, anonymousClass1);
            if (objWithContext == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(objWithContext);
        }
        List list = (List) objWithContext;
        return list == null ? EmptyList.INSTANCE : list;
    }
}
