package androidx.datastore.core;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
final class DataStoreImpl$readDataOrHandleCorruption$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $preLockVersion;
    Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ DataStoreImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataStoreImpl$readDataOrHandleCorruption$2(DataStoreImpl dataStoreImpl, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = dataStoreImpl;
        this.$preLockVersion = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DataStoreImpl$readDataOrHandleCorruption$2 dataStoreImpl$readDataOrHandleCorruption$2 = new DataStoreImpl$readDataOrHandleCorruption$2(this.this$0, this.$preLockVersion, continuation);
        dataStoreImpl$readDataOrHandleCorruption$2.Z$0 = ((Boolean) obj).booleanValue();
        return dataStoreImpl$readDataOrHandleCorruption$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((DataStoreImpl$readDataOrHandleCorruption$2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0063  */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        boolean z;
        int iIntValue;
        Object obj2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            z = this.Z$0;
            DataStoreImpl dataStoreImpl = this.this$0;
            this.Z$0 = z;
            this.label = 1;
            int i2 = DataStoreImpl.$r8$clinit;
            obj = dataStoreImpl.readDataFromFileOrDefault(this);
            if (obj != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            obj2 = this.L$0;
            ResultKt.throwOnFailure(obj);
            iIntValue = ((Number) obj).intValue();
            return new Data(obj2, obj2 != null ? obj2.hashCode() : 0, iIntValue);
        }
        z = this.Z$0;
        ResultKt.throwOnFailure(obj);
        if (!z) {
            Object obj3 = obj;
            iIntValue = this.$preLockVersion;
            obj2 = obj3;
            return new Data(obj2, obj2 != null ? obj2.hashCode() : 0, iIntValue);
        }
        DataStoreImpl dataStoreImpl2 = this.this$0;
        int i3 = DataStoreImpl.$r8$clinit;
        InterProcessCoordinator coordinator = dataStoreImpl2.getCoordinator();
        this.L$0 = obj;
        this.label = 2;
        Object version = ((SingleProcessCoordinator) coordinator).getVersion();
        if (version != coroutineSingletons) {
            Object obj4 = obj;
            obj = version;
            obj2 = obj4;
            iIntValue = ((Number) obj).intValue();
            return new Data(obj2, obj2 != null ? obj2.hashCode() : 0, iIntValue);
        }
        return coroutineSingletons;
    }
}
