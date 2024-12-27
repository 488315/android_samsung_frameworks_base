package com.android.server.inputmethod;

import android.os.Environment;
import android.os.FileUtils;
import android.util.AtomicFile;
import android.util.Slog;

import java.io.File;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */
class AdditionalSubtypeMapRepository$SingleThreadedBackgroundWriter$1$$ExternalSyntheticLambda0
        implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        AdditionalSubtypeMapRepository.WriteTask writeTask =
                (AdditionalSubtypeMapRepository.WriteTask) obj;
        AdditionalSubtypeMap additionalSubtypeMap = writeTask.subtypeMap;
        InputMethodMap inputMethodMap = writeTask.inputMethodMap;
        int i = writeTask.userId;
        File file =
                new File(
                        i == 0
                                ? new File(Environment.getDataDirectory(), "system")
                                : Environment.getUserSystemDirectory(i),
                        "inputmethod");
        if (!additionalSubtypeMap.mMap.isEmpty()) {
            if (file.exists() || file.mkdirs()) {
                AdditionalSubtypeUtils.saveToFile(
                        additionalSubtypeMap,
                        inputMethodMap,
                        new AtomicFile(new File(file, "subtypes.xml"), "input-subtypes"));
                return;
            }
            Slog.e("AdditionalSubtypeUtils", "Failed to create a parent directory " + file);
            return;
        }
        if (file.exists()) {
            AtomicFile atomicFile =
                    new AtomicFile(new File(file, "subtypes.xml"), "input-subtypes");
            if (atomicFile.exists()) {
                atomicFile.delete();
            }
            if (FileUtils.listFilesOrEmpty(file).length != 0 || file.delete()) {
                return;
            }
            Slog.e("AdditionalSubtypeUtils", "Failed to delete the empty parent directory " + file);
        }
    }
}
