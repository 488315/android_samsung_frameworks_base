package androidx.datastore;

import android.content.Context;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import java.io.File;

/* loaded from: classes.dex */
public abstract class DataStoreFile {
    public static final File dataStoreFile(Context context, String str) {
        return new File(context.getApplicationContext().getFilesDir(), AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("datastore/", str));
    }
}
