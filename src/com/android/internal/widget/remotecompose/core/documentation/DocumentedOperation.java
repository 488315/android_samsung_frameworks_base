package com.android.internal.widget.remotecompose.core.documentation;

import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class DocumentedOperation {
    public static final int BOOLEAN = 2;
    public static final int BUFFER = 4;
    public static final int BYTE = 6;
    public static final int FLOAT = 1;
    public static final int FLOAT_ARRAY = 10;
    public static final int INT = 0;
    public static final int INT_ARRAY = 11;
    public static final int LAYOUT = 0;
    public static final int LONG = 8;
    public static final int SHORT = 9;
    public static final int UTF8 = 5;
    public static final int VALUE = 7;
    final String mCategory;
    String mDescription;
    ArrayList<StringPair> mExamples;
    int mExamplesHeight;
    int mExamplesWidth;
    ArrayList<OperationField> mFields;
    int mId;
    final String mName;
    String mTextExamples;
    String mVarSize;
    boolean mWIP;

    public static String getType(int i) {
        switch (i) {
            case 0:
                return "INT";
            case 1:
                return "FLOAT";
            case 2:
                return "BOOLEAN";
            case 3:
            default:
                return "UNKNOWN";
            case 4:
                return "BUFFER";
            case 5:
                return "UTF8";
            case 6:
                return "BYTE";
            case 7:
                return "VALUE";
            case 8:
                return "LONG";
            case 9:
                return "SHORT";
            case 10:
                return "FLOAT[]";
            case 11:
                return "INT[]";
        }
    }

    public DocumentedOperation(String str, int i, String str2, boolean z) {
        this.mDescription = "";
        this.mExamples = new ArrayList<>();
        this.mFields = new ArrayList<>();
        this.mVarSize = "";
        this.mExamplesWidth = 100;
        this.mExamplesHeight = 100;
        this.mCategory = str;
        this.mId = i;
        this.mName = str2;
        this.mWIP = z;
    }

    public DocumentedOperation(String str, int i, String str2) {
        this(str, i, str2, false);
    }

    public ArrayList<OperationField> getFields() {
        return this.mFields;
    }

    public String getCategory() {
        return this.mCategory;
    }

    public int getId() {
        return this.mId;
    }

    public String getName() {
        return this.mName;
    }

    public boolean isWIP() {
        return this.mWIP;
    }

    public String getVarSize() {
        return this.mVarSize;
    }

    public int getSizeFields() {
        this.mVarSize = "";
        Iterator<OperationField> it = this.mFields.iterator();
        int i = 0;
        while (it.hasNext()) {
            OperationField next = it.next();
            i += Math.max(0, next.getSize());
            if (next.getSize() < 0) {
                this.mVarSize += " + " + next.getVarSize() + " x 4";
            }
        }
        return i;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public String getTextExamples() {
        return this.mTextExamples;
    }

    public ArrayList<StringPair> getExamples() {
        return this.mExamples;
    }

    public int getExamplesWidth() {
        return this.mExamplesWidth;
    }

    public int getExamplesHeight() {
        return this.mExamplesHeight;
    }

    public DocumentedOperation field(int i, String str, String str2) {
        this.mFields.add(new OperationField(i, str, str2));
        return this;
    }

    public DocumentedOperation field(int i, String str, String str2, String str3) {
        this.mFields.add(new OperationField(i, str, str2, str3));
        return this;
    }

    public DocumentedOperation possibleValues(String str, int i) {
        if (!this.mFields.isEmpty()) {
            this.mFields.get(r0.size() - 1).possibleValue(str, "" + i);
        }
        return this;
    }

    public DocumentedOperation description(String str) {
        this.mDescription = str;
        return this;
    }

    public DocumentedOperation examples(String str) {
        this.mTextExamples = str;
        return this;
    }

    public DocumentedOperation exampleImage(String str, String str2) {
        this.mExamples.add(new StringPair(str, str2));
        return this;
    }

    public DocumentedOperation examplesDimension(int i, int i2) {
        this.mExamplesWidth = i;
        this.mExamplesHeight = i2;
        return this;
    }
}
