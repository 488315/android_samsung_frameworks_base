package com.android.server.enterprise.general.font;

import java.util.ArrayList;
import java.util.List;

public final class Typeface {
    public String mName = null;
    public String mFontPackageName = null;
    public String mTypefaceFilename = null;
    public final List mSansFonts = new ArrayList();
    public final List mSerifFonts = new ArrayList();
    public final List mMonospaceFonts = new ArrayList();
}
