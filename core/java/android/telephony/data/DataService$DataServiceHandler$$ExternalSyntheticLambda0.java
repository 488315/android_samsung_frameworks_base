package android.telephony.data;

import com.android.internal.telephony.IIntegerConsumer;
import com.android.internal.util.FunctionalUtils;

public final /* synthetic */ class DataService$DataServiceHandler$$ExternalSyntheticLambda0
        implements FunctionalUtils.RemoteExceptionIgnoringConsumer {
    public final /* synthetic */ IIntegerConsumer f$0;

    @Override // com.android.internal.util.FunctionalUtils.RemoteExceptionIgnoringConsumer
    public final void acceptOrThrow(Object obj) {
        this.f$0.accept(((Integer) obj).intValue());
    }
}
