package com.samsung.android.content.smartclip;

import android.util.Log;
import android.view.View;

/* loaded from: classes6.dex */
public class SemSmartClipViewHelper {
    private static final String TAG = "SemSmartClipViewHelper";

    public static boolean addMetaTag(View view, SemSmartClipMetaTag semSmartClipMetaTag) {
        if (view == null || semSmartClipMetaTag == null || semSmartClipMetaTag.getType() == null) {
            Log.e(TAG, "addMetaTag : Have null parameter");
            return false;
        }
        if (!SmartClipUtils.isValidMetaTag(semSmartClipMetaTag)) {
            Log.e(TAG, "addMetaTag : Invalid metatag");
            return false;
        }
        SemSmartClipMetaTagArray semSmartClipMetaTagArraySemGetSmartClipTags = view.semGetSmartClipTags();
        if (semSmartClipMetaTagArraySemGetSmartClipTags == null) {
            semSmartClipMetaTagArraySemGetSmartClipTags = new SmartClipMetaTagArrayImpl();
            view.semSetSmartClipTags(semSmartClipMetaTagArraySemGetSmartClipTags);
        }
        semSmartClipMetaTagArraySemGetSmartClipTags.add(semSmartClipMetaTag);
        return true;
    }

    public static int removeMetaTag(View view, String str) {
        SemSmartClipMetaTagArray semSmartClipMetaTagArraySemGetSmartClipTags;
        if (view == null || str == null || (semSmartClipMetaTagArraySemGetSmartClipTags = view.semGetSmartClipTags()) == null) {
            return 0;
        }
        return semSmartClipMetaTagArraySemGetSmartClipTags.removeMetaTags(str);
    }

    public static SemSmartClipMetaTagArray getMetaTags(View view) {
        if (view == null) {
            return null;
        }
        return view.semGetSmartClipTags();
    }

    public static boolean setMetaTags(View view, SemSmartClipMetaTagArray semSmartClipMetaTagArray) {
        if (view == null || semSmartClipMetaTagArray == null) {
            return false;
        }
        view.semSetSmartClipTags(semSmartClipMetaTagArray);
        return true;
    }

    public static boolean clearAllMetaTags(View view) {
        if (view == null) {
            return false;
        }
        return view.semSetSmartClipTags(null);
    }

    public static boolean setDataExtractionListener(View view, SemSmartClipDataExtractionListener semSmartClipDataExtractionListener) {
        if (view == null) {
            return false;
        }
        view.semSetSmartClipDataExtractionListener(semSmartClipDataExtractionListener);
        return true;
    }

    public static int extractDefaultSmartClipData(View view, SemSmartClipCroppedArea semSmartClipCroppedArea, SemSmartClipDataElement semSmartClipDataElement) {
        if (view == null) {
            Log.e(TAG, "extractDefaultSmartClipData : The view is null!");
            return 0;
        }
        if (semSmartClipDataElement == null) {
            Log.e(TAG, "extractDefaultSmartClipData : The result element is null!");
            return 0;
        }
        if (semSmartClipCroppedArea == null) {
            Log.e(TAG, "extractDefaultSmartClipData : The cropped area is null!");
            return 0;
        }
        return view.semExtractSmartClipData(semSmartClipCroppedArea, semSmartClipDataElement);
    }
}
