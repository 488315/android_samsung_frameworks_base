package com.android.server.autofill;

import android.app.ActivityManager;
import android.app.assist.AssistStructure;
import android.app.slice.Slice;
import android.app.slice.SliceItem;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.hardware.display.DisplayManager;
import android.metrics.LogMaker;
import android.net.Uri;
import android.os.UserManager;
import android.service.autofill.Dataset;
import android.service.autofill.InternalSanitizer;
import android.service.autofill.SaveInfo;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.view.Display;
import android.view.WindowManager;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import android.widget.RemoteViews;
import com.android.internal.util.ArrayUtils;
import com.android.server.utils.Slogf;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public abstract class Helper {
    public static boolean sDebug = false;
    public static Boolean sFullScreenMode = null;
    public static boolean sVerbose = false;

    public interface ViewNodeFilter {
        boolean matches(AssistStructure.ViewNode viewNode);
    }

    public static boolean checkRemoteViewUriPermissions(final int i, RemoteViews remoteViews) {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        remoteViews.visitUris(new Consumer() { // from class: com.android.server.autofill.Helper$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Helper.lambda$checkRemoteViewUriPermissions$0(i, atomicBoolean, (Uri) obj);
            }
        });
        return atomicBoolean.get();
    }

    public static /* synthetic */ void lambda$checkRemoteViewUriPermissions$0(int i, AtomicBoolean atomicBoolean, Uri uri) {
        atomicBoolean.set((ContentProvider.getUserIdFromUri(uri, i) == i) && atomicBoolean.get());
    }

    public static RemoteViews sanitizeRemoteView(RemoteViews remoteViews) {
        if (remoteViews == null) {
            return null;
        }
        int currentUser = ActivityManager.getCurrentUser();
        boolean checkRemoteViewUriPermissions = checkRemoteViewUriPermissions(currentUser, remoteViews);
        if (!checkRemoteViewUriPermissions) {
            Slog.w("AutofillHelper", "sanitizeRemoteView() user: " + currentUser + " tried accessing resource that does not belong to them");
        }
        if (checkRemoteViewUriPermissions) {
            return remoteViews;
        }
        return null;
    }

    public static Slice sanitizeSlice(Slice slice) {
        if (slice == null) {
            return null;
        }
        int currentUser = ActivityManager.getCurrentUser();
        for (SliceItem sliceItem : slice.getItems()) {
            if (sliceItem.getFormat().equals("image")) {
                Icon icon = sliceItem.getIcon();
                if (icon.getType() == 4 || icon.getType() == 6) {
                    if (ContentProvider.getUserIdFromUri(icon.getUri(), currentUser) != currentUser) {
                        Slog.w("AutofillHelper", "sanitizeSlice() user: " + currentUser + " cannot access icons in Slice");
                        return null;
                    }
                }
            }
        }
        return slice;
    }

    public static AutofillId[] toArray(ArraySet arraySet) {
        if (arraySet == null) {
            return null;
        }
        AutofillId[] autofillIdArr = new AutofillId[arraySet.size()];
        for (int i = 0; i < arraySet.size(); i++) {
            autofillIdArr[i] = (AutofillId) arraySet.valueAt(i);
        }
        return autofillIdArr;
    }

    public static String paramsToString(WindowManager.LayoutParams layoutParams) {
        StringBuilder sb = new StringBuilder(25);
        layoutParams.dumpDimensions(sb);
        return sb.toString();
    }

    public static ArrayMap getFields(Dataset dataset) {
        ArrayList fieldIds = dataset.getFieldIds();
        ArrayList fieldValues = dataset.getFieldValues();
        int size = fieldIds == null ? 0 : fieldIds.size();
        ArrayMap arrayMap = new ArrayMap(size);
        for (int i = 0; i < size; i++) {
            arrayMap.put((AutofillId) fieldIds.get(i), (AutofillValue) fieldValues.get(i));
        }
        return arrayMap;
    }

    public static LogMaker newLogMaker(int i, String str, int i2, boolean z) {
        LogMaker addTaggedData = new LogMaker(i).addTaggedData(908, str).addTaggedData(1456, Integer.toString(i2));
        if (z) {
            addTaggedData.addTaggedData(1414, 1);
        }
        return addTaggedData;
    }

    public static LogMaker newLogMaker(int i, String str, String str2, int i2, boolean z) {
        return newLogMaker(i, str2, i2, z).setPackageName(str);
    }

    public static LogMaker newLogMaker(int i, ComponentName componentName, String str, int i2, boolean z) {
        return newLogMaker(i, str, i2, z).setComponentName(new ComponentName(componentName.getPackageName(), ""));
    }

    public static void printlnRedactedText(PrintWriter printWriter, CharSequence charSequence) {
        if (charSequence == null) {
            printWriter.println("null");
        } else {
            printWriter.print(charSequence.length());
            printWriter.println("_chars");
        }
    }

    public static AssistStructure.ViewNode findViewNodeByAutofillId(AssistStructure assistStructure, final AutofillId autofillId) {
        return findViewNode(assistStructure, new ViewNodeFilter() { // from class: com.android.server.autofill.Helper$$ExternalSyntheticLambda1
            @Override // com.android.server.autofill.Helper.ViewNodeFilter
            public final boolean matches(AssistStructure.ViewNode viewNode) {
                boolean lambda$findViewNodeByAutofillId$1;
                lambda$findViewNodeByAutofillId$1 = Helper.lambda$findViewNodeByAutofillId$1(autofillId, viewNode);
                return lambda$findViewNodeByAutofillId$1;
            }
        });
    }

    public static /* synthetic */ boolean lambda$findViewNodeByAutofillId$1(AutofillId autofillId, AssistStructure.ViewNode viewNode) {
        return autofillId.equals(viewNode.getAutofillId());
    }

    public static AssistStructure.ViewNode findViewNode(AssistStructure assistStructure, ViewNodeFilter viewNodeFilter) {
        ArrayDeque arrayDeque = new ArrayDeque();
        int windowNodeCount = assistStructure.getWindowNodeCount();
        for (int i = 0; i < windowNodeCount; i++) {
            arrayDeque.add(assistStructure.getWindowNodeAt(i).getRootViewNode());
        }
        while (!arrayDeque.isEmpty()) {
            AssistStructure.ViewNode viewNode = (AssistStructure.ViewNode) arrayDeque.removeFirst();
            if (viewNodeFilter.matches(viewNode)) {
                return viewNode;
            }
            for (int i2 = 0; i2 < viewNode.getChildCount(); i2++) {
                arrayDeque.addLast(viewNode.getChildAt(i2));
            }
        }
        return null;
    }

    public static AssistStructure.ViewNode sanitizeUrlBar(AssistStructure assistStructure, final String[] strArr) {
        AssistStructure.ViewNode findViewNode = findViewNode(assistStructure, new ViewNodeFilter() { // from class: com.android.server.autofill.Helper$$ExternalSyntheticLambda0
            @Override // com.android.server.autofill.Helper.ViewNodeFilter
            public final boolean matches(AssistStructure.ViewNode viewNode) {
                boolean lambda$sanitizeUrlBar$2;
                lambda$sanitizeUrlBar$2 = Helper.lambda$sanitizeUrlBar$2(strArr, viewNode);
                return lambda$sanitizeUrlBar$2;
            }
        });
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
                Slog.d("AutofillHelper", "sanitizeUrlBar(): id=" + findViewNode.getIdEntry() + ", domain=" + findViewNode.getWebDomain());
            }
        }
        return findViewNode;
    }

    public static /* synthetic */ boolean lambda$sanitizeUrlBar$2(String[] strArr, AssistStructure.ViewNode viewNode) {
        return ArrayUtils.contains(strArr, viewNode.getIdEntry());
    }

    public static int getNumericValue(LogMaker logMaker, int i) {
        Object taggedData = logMaker.getTaggedData(i);
        if (taggedData instanceof Number) {
            return ((Number) taggedData).intValue();
        }
        return 0;
    }

    public static ArrayList getAutofillIds(AssistStructure assistStructure, boolean z) {
        ArrayList arrayList = new ArrayList();
        int windowNodeCount = assistStructure.getWindowNodeCount();
        for (int i = 0; i < windowNodeCount; i++) {
            addAutofillableIds(assistStructure.getWindowNodeAt(i).getRootViewNode(), arrayList, z);
        }
        return arrayList;
    }

    public static void addAutofillableIds(AssistStructure.ViewNode viewNode, ArrayList arrayList, boolean z) {
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
            Slog.d("AutofillHelper", "Service provided " + length + " sanitizers");
        }
        AutofillId[][] sanitizerValues = saveInfo.getSanitizerValues();
        for (int i = 0; i < length; i++) {
            InternalSanitizer internalSanitizer = sanitizerKeys[i];
            AutofillId[] autofillIdArr = sanitizerValues[i];
            if (sDebug) {
                Slog.d("AutofillHelper", "sanitizer #" + i + " (" + internalSanitizer + ") for ids " + Arrays.toString(autofillIdArr));
            }
            for (AutofillId autofillId : autofillIdArr) {
                arrayMap.put(autofillId, internalSanitizer);
            }
        }
        return arrayMap;
    }

    public static boolean containsCharsInOrder(String str, String str2) {
        int i = -1;
        for (char c : str2.toCharArray()) {
            i = TextUtils.indexOf(str, c, i + 1);
            if (i == -1) {
                return false;
            }
        }
        return true;
    }

    public static Context getDisplayContext(Context context, int i) {
        if (!UserManager.isVisibleBackgroundUsersEnabled()) {
            return context;
        }
        if (context.getDisplayId() == i) {
            if (sDebug) {
                Slogf.m88d("AutofillHelper", "getDisplayContext(): context %s already has displayId %d", context, Integer.valueOf(i));
            }
            return context;
        }
        if (sDebug) {
            Slogf.m88d("AutofillHelper", "Creating context for display %d", Integer.valueOf(i));
        }
        Display display = ((DisplayManager) context.getSystemService(DisplayManager.class)).getDisplay(i);
        if (display == null) {
            Slogf.wtf("AutofillHelper", "Could not get context with displayId %d, Autofill operations will probably fail)", Integer.valueOf(i));
            return context;
        }
        return context.createDisplayContext(display);
    }

    public static Object weakDeref(WeakReference weakReference, String str, String str2) {
        Object obj = weakReference.get();
        if (obj == null) {
            Slog.wtf(str, str2 + "fail to deref " + weakReference);
        }
        return obj;
    }
}
