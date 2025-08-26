package androidx.datastore.preferences;

import android.content.Context;
import androidx.datastore.DataStoreFile;
import java.io.File;

/* loaded from: classes.dex */
public abstract class PreferenceDataStoreFile {
    public static final File preferencesDataStoreFile(Context context, String str) {
        return DataStoreFile.dataStoreFile(context, str + ".preferences_pb");
    }
}
