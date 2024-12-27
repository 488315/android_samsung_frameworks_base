package com.android.server.autofill;

import android.app.ActivityManager;
import android.app.assist.AssistStructure;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.metrics.LogMaker;
import android.net.Uri;
import android.service.autofill.InternalSanitizer;
import android.service.autofill.SaveInfo;
import android.util.ArrayMap;
import android.util.Slog;
import android.view.autofill.AutofillId;
import android.widget.RemoteViews;

import com.android.internal.util.ArrayUtils;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;

import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public abstract class Helper {
    public static boolean sDebug;
    public static Boolean sFullScreenMode;
    public static boolean sVerbose;

    public static void addAutofillableIds(
            AssistStructure.ViewNode viewNode, ArrayList arrayList, boolean z) {
        if (!z || viewNode.getAutofillType() != 0) {
            arrayList.add(viewNode.getAutofillId());
        }
        int childCount = viewNode.getChildCount();
        for (int i = 0; i < childCount; i++) {
            addAutofillableIds(viewNode.getChildAt(i), arrayList, z);
        }
    }

    public static ArrayMap createSanitizers(SaveInfo saveInfo) {
        InternalSanitizer[] sanitizerKeys;
        if (saveInfo == null || (sanitizerKeys = saveInfo.getSanitizerKeys()) == null) {
            return null;
        }
        int length = sanitizerKeys.length;
        ArrayMap arrayMap = new ArrayMap(length);
        if (sDebug) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    length, "Service provided ", " sanitizers", "AutofillHelper");
        }
        AutofillId[][] sanitizerValues = saveInfo.getSanitizerValues();
        for (int i = 0; i < length; i++) {
            InternalSanitizer internalSanitizer = sanitizerKeys[i];
            AutofillId[] autofillIdArr = sanitizerValues[i];
            if (sDebug) {
                StringBuilder sb = new StringBuilder("sanitizer #");
                sb.append(i);
                sb.append(" (");
                sb.append(internalSanitizer);
                sb.append(") for ids ");
                BootReceiver$$ExternalSyntheticOutline0.m(
                        sb, Arrays.toString(autofillIdArr), "AutofillHelper");
            }
            for (AutofillId autofillId : autofillIdArr) {
                arrayMap.put(autofillId, internalSanitizer);
            }
        }
        return arrayMap;
    }

    public static AssistStructure.ViewNode findViewNode(
            AssistStructure assistStructure,
            Helper$$ExternalSyntheticLambda0 helper$$ExternalSyntheticLambda0) {
        boolean equals;
        ArrayDeque arrayDeque = new ArrayDeque();
        int windowNodeCount = assistStructure.getWindowNodeCount();
        for (int i = 0; i < windowNodeCount; i++) {
            arrayDeque.add(assistStructure.getWindowNodeAt(i).getRootViewNode());
        }
        while (!arrayDeque.isEmpty()) {
            AssistStructure.ViewNode viewNode = (AssistStructure.ViewNode) arrayDeque.removeFirst();
            switch (helper$$ExternalSyntheticLambda0.$r8$classId) {
                case 0:
                    equals =
                            ((AutofillId) helper$$ExternalSyntheticLambda0.f$0)
                                    .equals(viewNode.getAutofillId());
                    break;
                default:
                    equals =
                            ArrayUtils.contains(
                                    (String[]) helper$$ExternalSyntheticLambda0.f$0,
                                    viewNode.getIdEntry());
                    break;
            }
            if (equals) {
                return viewNode;
            }
            for (int i2 = 0; i2 < viewNode.getChildCount(); i2++) {
                arrayDeque.addLast(viewNode.getChildAt(i2));
            }
        }
        return null;
    }

    public static ArrayList getAutofillIds(AssistStructure assistStructure, boolean z) {
        ArrayList arrayList = new ArrayList();
        int windowNodeCount = assistStructure.getWindowNodeCount();
        for (int i = 0; i < windowNodeCount; i++) {
            addAutofillableIds(assistStructure.getWindowNodeAt(i).getRootViewNode(), arrayList, z);
        }
        return arrayList;
    }

    public static LogMaker newLogMaker(int i, int i2, String str, boolean z) {
        LogMaker addTaggedData =
                new LogMaker(i).addTaggedData(908, str).addTaggedData(1456, Integer.toString(i2));
        if (z) {
            addTaggedData.addTaggedData(1414, 1);
        }
        return addTaggedData;
    }

    public static LogMaker newLogMaker(
            int i, ComponentName componentName, String str, int i2, boolean z) {
        return newLogMaker(i, i2, str, z)
                .setComponentName(new ComponentName(componentName.getPackageName(), ""));
    }

    public static RemoteViews sanitizeRemoteView(RemoteViews remoteViews) {
        if (remoteViews == null) {
            return null;
        }
        final int currentUser = ActivityManager.getCurrentUser();
        final AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        remoteViews.visitUris(
                new Consumer() { // from class:
                                 // com.android.server.autofill.Helper$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        int i = currentUser;
                        AtomicBoolean atomicBoolean2 = atomicBoolean;
                        atomicBoolean2.set(
                                ContentProvider.getUserIdFromUri((Uri) obj, i) == i
                                        && atomicBoolean2.get());
                    }
                });
        boolean z = atomicBoolean.get();
        if (!z) {
            BrailleDisplayConnection$$ExternalSyntheticOutline0.m(
                    currentUser,
                    "sanitizeRemoteView() user: ",
                    " tried accessing resource that does not belong to them",
                    "AutofillHelper");
        }
        if (z) {
            return remoteViews;
        }
        return null;
    }

    public static AssistStructure.ViewNode sanitizeUrlBar(
            AssistStructure assistStructure, String[] strArr) {
        AssistStructure.ViewNode findViewNode =
                findViewNode(assistStructure, new Helper$$ExternalSyntheticLambda0(1, strArr));
        if (findViewNode != null) {
            String charSequence = findViewNode.getText().toString();
            if (charSequence.isEmpty()) {
                if (!sDebug) {
                    return null;
                }
                Slog.d("AutofillHelper", "sanitizeUrlBar(): empty on " + findViewNode.getIdEntry());
                return null;
            }
            findViewNode.setWebDomain(charSequence);
            if (sDebug) {
                Slog.d(
                        "AutofillHelper",
                        "sanitizeUrlBar(): id="
                                + findViewNode.getIdEntry()
                                + ", domain="
                                + findViewNode.getWebDomain());
            }
        }
        return findViewNode;
    }

    public static Object weakDeref(WeakReference weakReference, String str) {
        Object obj = weakReference.get();
        if (obj == null) {
            Slog.wtf(
                    "AutofillRemoteFieldClassificationService",
                    str + "fail to deref " + weakReference);
        }
        return obj;
    }
}
