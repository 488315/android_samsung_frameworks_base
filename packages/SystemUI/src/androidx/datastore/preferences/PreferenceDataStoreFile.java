package androidx.datastore.preferences;

import android.content.Context;
import androidx.datastore.DataStoreFile;
import java.io.File;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class PreferenceDataStoreFile {
    public static final File preferencesDataStoreFile(Context context, String str) {
        return DataStoreFile.dataStoreFile(context, str + ".preferences_pb");
    }
}
