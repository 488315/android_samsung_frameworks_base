package com.samsung.android.content.smartclip;

import android.util.Log;
import android.view.View;

public class SemSmartClipViewHelper {
    private static final String TAG = "SemSmartClipViewHelper";

    public static boolean addMetaTag(View view, SemSmartClipMetaTag metaTag) {
        if (view == null || metaTag == null || metaTag.getType() == null) {
            Log.e(TAG, "addMetaTag : Have null parameter");
            return false;
        }
        if (SmartClipUtils.isValidMetaTag(metaTag)) {
            SemSmartClipMetaTagArray tagArray = view.semGetSmartClipTags();
            if (tagArray == null) {
                tagArray = new SmartClipMetaTagArrayImpl();
                view.semSetSmartClipTags(tagArray);
            }
            tagArray.add(metaTag);
            return true;
        }
        Log.e(TAG, "addMetaTag : Invalid metatag");
        return false;
    }

    public static int removeMetaTag(View view, String tagType) {
        SemSmartClipMetaTagArray tagArray;
        if (view == null || tagType == null || (tagArray = view.semGetSmartClipTags()) == null) {
            return 0;
        }
        return tagArray.removeMetaTags(tagType);
    }

    public static SemSmartClipMetaTagArray getMetaTags(View view) {
        if (view == null) {
            return null;
        }
        return view.semGetSmartClipTags();
    }

    public static boolean setMetaTags(View view, SemSmartClipMetaTagArray tagArray) {
        if (view == null || tagArray == null) {
            return false;
        }
        view.semSetSmartClipTags(tagArray);
        return true;
    }

    public static boolean clearAllMetaTags(View view) {
        if (view == null) {
            return false;
        }
        return view.semSetSmartClipTags(null);
    }

    public static boolean setDataExtractionListener(
            View view, SemSmartClipDataExtractionListener listener) {
        if (view == null) {
            return false;
        }
        view.semSetSmartClipDataExtractionListener(listener);
        return true;
    }

    public static int extractDefaultSmartClipData(
            View view, SemSmartClipCroppedArea croppedArea, SemSmartClipDataElement resultElement) {
        if (view == null) {
            Log.e(TAG, "extractDefaultSmartClipData : The view is null!");
            return 0;
        }
        if (resultElement == null) {
            Log.e(TAG, "extractDefaultSmartClipData : The result element is null!");
            return 0;
        }
        if (croppedArea == null) {
            Log.e(TAG, "extractDefaultSmartClipData : The cropped area is null!");
            return 0;
        }
        return view.semExtractSmartClipData(croppedArea, resultElement);
    }
}
