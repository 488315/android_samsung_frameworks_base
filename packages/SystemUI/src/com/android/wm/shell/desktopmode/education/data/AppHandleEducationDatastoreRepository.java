package com.android.wm.shell.desktopmode.education.data;

import android.content.Context;
import androidx.datastore.DataStoreFile;
import androidx.datastore.core.CorruptionException;
import androidx.datastore.core.DataStore;
import androidx.datastore.core.DataStoreFactory;
import androidx.datastore.core.Serializer;
import androidx.datastore.core.UncloseableOutputStream;
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler;
import com.android.framework.protobuf.InvalidProtocolBufferException;
import java.io.InputStream;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AppHandleEducationDatastoreRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final DataStore dataStore;
    public final FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1 dataStoreFlow;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class WindowingEducationProtoSerializer implements Serializer {
            public static final WindowingEducationProtoSerializer INSTANCE = new WindowingEducationProtoSerializer();
            public static final WindowingEducationProto defaultValue = WindowingEducationProto.getDefaultInstance();

            private WindowingEducationProtoSerializer() {
            }

            @Override // androidx.datastore.core.Serializer
            public final Object getDefaultValue() {
                return defaultValue;
            }

            @Override // androidx.datastore.core.Serializer
            public final Object readFrom(InputStream inputStream) {
                try {
                    return WindowingEducationProto.parseFrom(inputStream);
                } catch (InvalidProtocolBufferException e) {
                    throw new CorruptionException("Cannot read proto.", e);
                }
            }

            @Override // androidx.datastore.core.Serializer
            public final Unit writeTo(Object obj, UncloseableOutputStream uncloseableOutputStream) {
                ((WindowingEducationProto) obj).writeTo(uncloseableOutputStream);
                return Unit.INSTANCE;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public AppHandleEducationDatastoreRepository(DataStore dataStore) {
        this.dataStore = dataStore;
        this.dataStoreFlow = new FlowKt__ErrorsKt$catch$$inlined$unsafeFlow$1(dataStore.getData(), new AppHandleEducationDatastoreRepository$dataStoreFlow$1(null));
    }

    public AppHandleEducationDatastoreRepository(final Context context) {
        this(DataStoreFactory.create$default(DataStoreFactory.INSTANCE, Companion.WindowingEducationProtoSerializer.INSTANCE, new ReplaceFileCorruptionHandler(new AppHandleEducationDatastoreRepository$$ExternalSyntheticLambda0()), null, new Function0() { // from class: com.android.wm.shell.desktopmode.education.data.AppHandleEducationDatastoreRepository$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Context context2 = context;
                int i = AppHandleEducationDatastoreRepository.$r8$clinit;
                return DataStoreFile.dataStoreFile(context2, "app_handle_education.pb");
            }
        }, 12));
    }
}
