package androidx.datastore;

import android.content.Context;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import java.io.File;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class DataStoreFile {
    public static final File dataStoreFile(Context context, String str) {
        return new File(context.getApplicationContext().getFilesDir(), AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("datastore/", str));
    }
}
