package android.app.assist;

import android.annotation.SystemApi;
import android.app.Activity;
import android.app.slice.Slice;
import android.content.ComponentName;
import android.content.Context;
import android.content.p002pm.PackageManager;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.Uri;
import android.p009os.BadParcelableException;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.LocaleList;
import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.p009os.PooledStringReader;
import android.p009os.PooledStringWriter;
import android.p009os.RemoteException;
import android.p009os.SystemClock;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.ViewStructure;
import android.view.WindowManagerGlobal;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import com.android.internal.content.NativeLibraryHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public class AssistStructure implements Parcelable {
  public static final Parcelable.Creator<AssistStructure> CREATOR =
      new Parcelable.Creator<
          AssistStructure>() { // from class: android.app.assist.AssistStructure.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AssistStructure createFromParcel(Parcel in) {
          return new AssistStructure(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AssistStructure[] newArray(int size) {
          return new AssistStructure[size];
        }
      };
  private static final boolean DEBUG_PARCEL = false;
  private static final boolean DEBUG_PARCEL_CHILDREN = false;
  private static final boolean DEBUG_PARCEL_TREE = false;
  private static final String DESCRIPTOR = "android.app.AssistStructure";
  private static final String TAG = "AssistStructure";
  private static final int TRANSACTION_XFER = 2;
  private static final int VALIDATE_VIEW_TOKEN = 572662306;
  private static final int VALIDATE_WINDOW_TOKEN = 286331153;
  private long mAcquisitionEndTime;
  private long mAcquisitionStartTime;
  private ComponentName mActivityComponent;
  private int mAutofillFlags;
  private int mFlags;
  private boolean mHaveData;
  private boolean mIsHomeActivity;
  private final ArrayList<ViewNodeBuilder> mPendingAsyncChildren;
  private IBinder mReceiveChannel;
  private boolean mSanitizeOnWrite;
  private SendChannel mSendChannel;
  private int mTaskId;
  private Rect mTmpRect;
  private final ArrayList<WindowNode> mWindowNodes;

  public static class AutofillOverlay {
    public boolean focused;
    public AutofillValue value;
  }

  public void setAcquisitionStartTime(long acquisitionStartTime) {
    this.mAcquisitionStartTime = acquisitionStartTime;
  }

  public void setAcquisitionEndTime(long acquisitionEndTime) {
    this.mAcquisitionEndTime = acquisitionEndTime;
  }

  public void setHomeActivity(boolean isHomeActivity) {
    this.mIsHomeActivity = isHomeActivity;
  }

  public long getAcquisitionStartTime() {
    ensureData();
    return this.mAcquisitionStartTime;
  }

  public long getAcquisitionEndTime() {
    ensureData();
    return this.mAcquisitionEndTime;
  }

  static final class SendChannel extends Binder {
    volatile AssistStructure mAssistStructure;

    SendChannel(AssistStructure as) {
      this.mAssistStructure = as;
    }

    @Override // android.p009os.Binder
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      if (code == 2) {
        AssistStructure as = this.mAssistStructure;
        if (as == null) {
          return true;
        }
        data.enforceInterface(AssistStructure.DESCRIPTOR);
        IBinder token = data.readStrongBinder();
        if (token != null) {
          if (token instanceof ParcelTransferWriter) {
            ParcelTransferWriter xfer = (ParcelTransferWriter) token;
            xfer.writeToParcel(as, reply);
            return true;
          }
          Log.m102w(AssistStructure.TAG, "Caller supplied bad token type: " + token);
          return true;
        }
        ParcelTransferWriter xfer2 = new ParcelTransferWriter(as, reply);
        xfer2.writeToParcel(as, reply);
        return true;
      }
      return super.onTransact(code, data, reply, flags);
    }
  }

  static final class ViewStackEntry {
    int curChild;
    ViewNode node;
    int numChildren;

    ViewStackEntry() {}
  }

  static final class ParcelTransferWriter extends Binder {
    ViewStackEntry mCurViewStackEntry;
    int mCurViewStackPos;
    int mCurWindow;
    int mNumWindows;
    int mNumWrittenViews;
    int mNumWrittenWindows;
    final boolean mSanitizeOnWrite;
    final boolean mWriteStructure;
    final ArrayList<ViewStackEntry> mViewStack = new ArrayList<>();
    final float[] mTmpMatrix = new float[9];

    ParcelTransferWriter(AssistStructure as, Parcel out) {
      this.mSanitizeOnWrite = as.mSanitizeOnWrite;
      boolean waitForReady = as.waitForReady();
      this.mWriteStructure = waitForReady;
      out.writeInt(as.mFlags);
      out.writeInt(as.mAutofillFlags);
      out.writeLong(as.mAcquisitionStartTime);
      out.writeLong(as.mAcquisitionEndTime);
      int size = as.mWindowNodes.size();
      this.mNumWindows = size;
      if (waitForReady && size > 0) {
        out.writeInt(size);
      } else {
        out.writeInt(0);
      }
    }

    void writeToParcel(AssistStructure as, Parcel out) {
      int start = out.dataPosition();
      this.mNumWrittenWindows = 0;
      this.mNumWrittenViews = 0;
      boolean more = writeToParcelInner(as, out);
      Log.m98i(
          AssistStructure.TAG,
          "Flattened "
              + (more ? Slice.HINT_PARTIAL : "final")
              + " assist data: "
              + (out.dataPosition() - start)
              + " bytes, containing "
              + this.mNumWrittenWindows
              + " windows, "
              + this.mNumWrittenViews
              + " views");
    }

    boolean writeToParcelInner(AssistStructure as, Parcel out) {
      if (this.mNumWindows == 0) {
        return false;
      }
      PooledStringWriter pwriter = new PooledStringWriter(out);
      while (writeNextEntryToParcel(as, out, pwriter)) {
        if (out.dataSize() > 65536) {
          out.writeInt(0);
          out.writeStrongBinder(this);
          pwriter.finish();
          return true;
        }
      }
      pwriter.finish();
      this.mViewStack.clear();
      return false;
    }

    void pushViewStackEntry(ViewNode node, int pos) {
      ViewStackEntry entry;
      if (pos >= this.mViewStack.size()) {
        entry = new ViewStackEntry();
        this.mViewStack.add(entry);
      } else {
        entry = this.mViewStack.get(pos);
      }
      entry.node = node;
      entry.numChildren = node.getChildCount();
      entry.curChild = 0;
      this.mCurViewStackEntry = entry;
    }

    void writeView(ViewNode child, Parcel out, PooledStringWriter pwriter, int levelAdj) {
      out.writeInt(AssistStructure.VALIDATE_VIEW_TOKEN);
      int flags =
          child.writeSelfToParcel(out, pwriter, this.mSanitizeOnWrite, this.mTmpMatrix, true);
      this.mNumWrittenViews++;
      if ((1048576 & flags) != 0) {
        out.writeInt(child.mChildren.length);
        int pos = this.mCurViewStackPos + 1;
        this.mCurViewStackPos = pos;
        pushViewStackEntry(child, pos);
      }
    }

    boolean writeNextEntryToParcel(AssistStructure as, Parcel out, PooledStringWriter pwriter) {
      ViewStackEntry viewStackEntry = this.mCurViewStackEntry;
      if (viewStackEntry != null) {
        if (viewStackEntry.curChild < this.mCurViewStackEntry.numChildren) {
          ViewNode child = this.mCurViewStackEntry.node.mChildren[this.mCurViewStackEntry.curChild];
          this.mCurViewStackEntry.curChild++;
          writeView(child, out, pwriter, 1);
          return true;
        }
        while (true) {
          int pos = this.mCurViewStackPos - 1;
          this.mCurViewStackPos = pos;
          if (pos < 0) {
            this.mCurViewStackEntry = null;
            break;
          }
          ViewStackEntry viewStackEntry2 = this.mViewStack.get(pos);
          this.mCurViewStackEntry = viewStackEntry2;
          if (viewStackEntry2.curChild < this.mCurViewStackEntry.numChildren) {
            break;
          }
        }
        return true;
      }
      int pos2 = this.mCurWindow;
      if (pos2 >= this.mNumWindows) {
        return false;
      }
      WindowNode win = (WindowNode) as.mWindowNodes.get(pos2);
      this.mCurWindow++;
      out.writeInt(AssistStructure.VALIDATE_WINDOW_TOKEN);
      win.writeSelfToParcel(out, pwriter, this.mTmpMatrix);
      this.mNumWrittenWindows++;
      ViewNode root = win.mRoot;
      this.mCurViewStackPos = 0;
      writeView(root, out, pwriter, 0);
      return true;
    }
  }

  final class ParcelTransferReader {
    private final IBinder mChannel;
    private Parcel mCurParcel;
    int mNumReadViews;
    int mNumReadWindows;
    PooledStringReader mStringReader;
    final float[] mTmpMatrix = new float[9];
    private IBinder mTransferToken;

    ParcelTransferReader(IBinder channel) {
      this.mChannel = channel;
    }

    /* renamed from: go */
    void m6go() {
      fetchData();
      AssistStructure.this.mFlags = this.mCurParcel.readInt();
      AssistStructure.this.mAutofillFlags = this.mCurParcel.readInt();
      AssistStructure.this.mAcquisitionStartTime = this.mCurParcel.readLong();
      AssistStructure.this.mAcquisitionEndTime = this.mCurParcel.readLong();
      int N = this.mCurParcel.readInt();
      if (N > 0) {
        this.mStringReader = new PooledStringReader(this.mCurParcel);
        for (int i = 0; i < N; i++) {
          AssistStructure.this.mWindowNodes.add(new WindowNode(this));
        }
      }
      this.mCurParcel.recycle();
      this.mCurParcel = null;
    }

    Parcel readParcel(int validateToken, int level) {
      int token = this.mCurParcel.readInt();
      if (token != 0) {
        if (token != validateToken) {
          throw new BadParcelableException(
              "Got token "
                  + Integer.toHexString(token)
                  + ", expected token "
                  + Integer.toHexString(validateToken));
        }
        return this.mCurParcel;
      }
      IBinder readStrongBinder = this.mCurParcel.readStrongBinder();
      this.mTransferToken = readStrongBinder;
      if (readStrongBinder == null) {
        throw new IllegalStateException("Reached end of partial data without transfer token");
      }
      fetchData();
      this.mStringReader = new PooledStringReader(this.mCurParcel);
      this.mCurParcel.readInt();
      return this.mCurParcel;
    }

    private void fetchData() {
      Parcel data = Parcel.obtain();
      try {
        data.writeInterfaceToken(AssistStructure.DESCRIPTOR);
        data.writeStrongBinder(this.mTransferToken);
        Parcel parcel = this.mCurParcel;
        if (parcel != null) {
          parcel.recycle();
        }
        Parcel obtain = Parcel.obtain();
        this.mCurParcel = obtain;
        try {
          this.mChannel.transact(2, data, obtain, 0);
          data.recycle();
          this.mNumReadViews = 0;
          this.mNumReadWindows = 0;
        } catch (RemoteException e) {
          Log.m103w(AssistStructure.TAG, "Failure reading AssistStructure data", e);
          throw new IllegalStateException("Failure reading AssistStructure data: " + e);
        }
      } catch (Throwable e2) {
        data.recycle();
        throw e2;
      }
    }
  }

  static final class ViewNodeText {
    String mHint;
    int[] mLineBaselines;
    int[] mLineCharOffsets;
    CharSequence mText;
    int mTextBackgroundColor;
    int mTextColor;
    int mTextSelectionEnd;
    int mTextSelectionStart;
    float mTextSize;
    int mTextStyle;

    ViewNodeText() {
      this.mTextColor = 1;
      this.mTextBackgroundColor = 1;
    }

    boolean isSimple() {
      return this.mTextBackgroundColor == 1
          && this.mTextSelectionStart == 0
          && this.mTextSelectionEnd == 0
          && this.mLineCharOffsets == null
          && this.mLineBaselines == null
          && this.mHint == null;
    }

    ViewNodeText(Parcel in, boolean simple) {
      this.mTextColor = 1;
      this.mTextBackgroundColor = 1;
      this.mText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
      this.mTextSize = in.readFloat();
      this.mTextStyle = in.readInt();
      this.mTextColor = in.readInt();
      if (!simple) {
        this.mTextBackgroundColor = in.readInt();
        this.mTextSelectionStart = in.readInt();
        this.mTextSelectionEnd = in.readInt();
        this.mLineCharOffsets = in.createIntArray();
        this.mLineBaselines = in.createIntArray();
        this.mHint = in.readString();
      }
    }

    void writeToParcel(Parcel out, boolean simple, boolean writeSensitive) {
      TextUtils.writeToParcel(writeSensitive ? this.mText : "", out, 0);
      out.writeFloat(this.mTextSize);
      out.writeInt(this.mTextStyle);
      out.writeInt(this.mTextColor);
      if (!simple) {
        out.writeInt(this.mTextBackgroundColor);
        out.writeInt(this.mTextSelectionStart);
        out.writeInt(this.mTextSelectionEnd);
        out.writeIntArray(this.mLineCharOffsets);
        out.writeIntArray(this.mLineBaselines);
        out.writeString(this.mHint);
      }
    }
  }

  public static class WindowNode {
    final int mDisplayId;
    final int mHeight;
    final ViewNode mRoot;
    final CharSequence mTitle;
    final int mWidth;

    /* renamed from: mX */
    final int f31mX;

    /* renamed from: mY */
    final int f32mY;

    WindowNode(AssistStructure assist, ViewRootImpl root, boolean forAutoFill, int flags) {
      View view = root.getView();
      Rect rect = new Rect();
      view.getBoundsOnScreen(rect);
      this.f31mX = rect.left - view.getLeft();
      this.f32mY = rect.top - view.getTop();
      this.mWidth = rect.width();
      this.mHeight = rect.height();
      this.mTitle = root.getTitle();
      this.mDisplayId = root.getDisplayId();
      ViewNode viewNode = new ViewNode();
      this.mRoot = viewNode;
      ViewNodeBuilder builder = new ViewNodeBuilder(assist, viewNode, false);
      if ((root.getWindowFlags() & 8192) != 0) {
        if (forAutoFill) {
          int viewFlags = resolveViewAutofillFlags(view.getContext(), flags);
          view.onProvideAutofillStructure(builder, viewFlags);
        } else {
          view.onProvideStructure(builder);
          builder.setAssistBlocked(true);
          return;
        }
      }
      if (forAutoFill) {
        int viewFlags2 = resolveViewAutofillFlags(view.getContext(), flags);
        view.dispatchProvideAutofillStructure(builder, viewFlags2);
      } else {
        view.dispatchProvideStructure(builder);
      }
    }

    WindowNode(ParcelTransferReader reader) {
      Parcel in = reader.readParcel(AssistStructure.VALIDATE_WINDOW_TOKEN, 0);
      reader.mNumReadWindows++;
      this.f31mX = in.readInt();
      this.f32mY = in.readInt();
      this.mWidth = in.readInt();
      this.mHeight = in.readInt();
      this.mTitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
      this.mDisplayId = in.readInt();
      this.mRoot = new ViewNode(reader, 0);
    }

    int resolveViewAutofillFlags(Context context, int fillRequestFlags) {
      return ((fillRequestFlags & 1) == 0
              && !context.isAutofillCompatibilityEnabled()
              && (fillRequestFlags & 512) == 0)
          ? 0
          : 1;
    }

    void writeSelfToParcel(Parcel out, PooledStringWriter pwriter, float[] tmpMatrix) {
      out.writeInt(this.f31mX);
      out.writeInt(this.f32mY);
      out.writeInt(this.mWidth);
      out.writeInt(this.mHeight);
      TextUtils.writeToParcel(this.mTitle, out, 0);
      out.writeInt(this.mDisplayId);
    }

    public int getLeft() {
      return this.f31mX;
    }

    public int getTop() {
      return this.f32mY;
    }

    public int getWidth() {
      return this.mWidth;
    }

    public int getHeight() {
      return this.mHeight;
    }

    public CharSequence getTitle() {
      return this.mTitle;
    }

    public int getDisplayId() {
      return this.mDisplayId;
    }

    public ViewNode getRootViewNode() {
      return this.mRoot;
    }
  }

  public static class ViewNode {
    static final int AUTOFILL_FLAGS_HAS_AUTOFILL_HINTS = 16;
    static final int AUTOFILL_FLAGS_HAS_AUTOFILL_OPTIONS = 32;
    static final int AUTOFILL_FLAGS_HAS_AUTOFILL_SESSION_ID = 2048;
    static final int AUTOFILL_FLAGS_HAS_AUTOFILL_TYPE = 8;
    static final int AUTOFILL_FLAGS_HAS_AUTOFILL_VALUE = 4;
    static final int AUTOFILL_FLAGS_HAS_AUTOFILL_VIEW_ID = 1;
    static final int AUTOFILL_FLAGS_HAS_AUTOFILL_VIRTUAL_VIEW_ID = 2;
    static final int AUTOFILL_FLAGS_HAS_HINT_ID_ENTRY = 4096;
    static final int AUTOFILL_FLAGS_HAS_HTML_INFO = 64;
    static final int AUTOFILL_FLAGS_HAS_MAX_TEXT_EMS = 512;
    static final int AUTOFILL_FLAGS_HAS_MAX_TEXT_LENGTH = 1024;
    static final int AUTOFILL_FLAGS_HAS_MIN_TEXT_EMS = 256;
    static final int AUTOFILL_FLAGS_HAS_TEXT_ID_ENTRY = 128;
    static final int FLAGS_ACCESSIBILITY_FOCUSED = 4096;
    static final int FLAGS_ACTIVATED = 8192;
    static final int FLAGS_ALL_CONTROL = -65536;
    static final int FLAGS_ASSIST_BLOCKED = 128;
    static final int FLAGS_CHECKABLE = 256;
    static final int FLAGS_CHECKED = 512;
    static final int FLAGS_CLICKABLE = 1024;
    static final int FLAGS_CONTEXT_CLICKABLE = 16384;
    static final int FLAGS_DISABLED = 1;
    static final int FLAGS_FOCUSABLE = 16;
    static final int FLAGS_FOCUSED = 32;
    static final int FLAGS_HAS_ALPHA = 536870912;
    static final int FLAGS_HAS_CHILDREN = 1048576;
    static final int FLAGS_HAS_COMPLEX_TEXT = 8388608;
    static final int FLAGS_HAS_CONTENT_DESCRIPTION = 33554432;
    static final int FLAGS_HAS_ELEVATION = 268435456;
    static final int FLAGS_HAS_EXTRAS = 4194304;
    static final int FLAGS_HAS_ID = 2097152;
    static final int FLAGS_HAS_INPUT_TYPE = 262144;
    static final int FLAGS_HAS_LARGE_COORDS = 67108864;
    static final int FLAGS_HAS_LOCALE_LIST = 65536;
    static final int FLAGS_HAS_MATRIX = 1073741824;
    static final int FLAGS_HAS_MIME_TYPES = Integer.MIN_VALUE;
    static final int FLAGS_HAS_SCROLL = 134217728;
    static final int FLAGS_HAS_TEXT = 16777216;
    static final int FLAGS_HAS_URL_DOMAIN = 524288;
    static final int FLAGS_HAS_URL_SCHEME = 131072;
    static final int FLAGS_LONG_CLICKABLE = 2048;
    static final int FLAGS_OPAQUE = 32768;
    static final int FLAGS_SELECTED = 64;
    static final int FLAGS_VISIBILITY_MASK = 12;
    public static final int TEXT_COLOR_UNDEFINED = 1;
    public static final int TEXT_STYLE_BOLD = 1;
    public static final int TEXT_STYLE_ITALIC = 2;
    public static final int TEXT_STYLE_STRIKE_THRU = 8;
    public static final int TEXT_STYLE_UNDERLINE = 4;
    int mAutofillFlags;
    String[] mAutofillHints;
    AutofillId mAutofillId;
    CharSequence[] mAutofillOptions;
    AutofillOverlay mAutofillOverlay;
    AutofillValue mAutofillValue;
    ViewNode[] mChildren;
    String mClassName;
    CharSequence mContentDescription;
    float mElevation;
    Bundle mExtras;
    int mFlags;
    int mHeight;
    String mHintIdEntry;
    ViewStructure.HtmlInfo mHtmlInfo;
    String mIdEntry;
    String mIdPackage;
    String mIdType;
    int mImportantForAutofill;
    int mInputType;
    LocaleList mLocaleList;
    Matrix mMatrix;
    String[] mReceiveContentMimeTypes;
    boolean mSanitized;
    int mScrollX;
    int mScrollY;
    ViewNodeText mText;
    String mTextIdEntry;
    String mWebDomain;
    String mWebScheme;
    int mWidth;

    /* renamed from: mX */
    int f29mX;

    /* renamed from: mY */
    int f30mY;
    int mId = -1;
    int mAutofillType = 0;
    int mMinEms = -1;
    int mMaxEms = -1;
    int mMaxLength = -1;
    float mAlpha = 1.0f;

    @SystemApi
    public ViewNode() {}

    ViewNode(Parcel in) {
      initializeFromParcelWithoutChildren(in, null, null);
    }

    ViewNode(ParcelTransferReader reader, int nestingLevel) {
      Parcel in = reader.readParcel(AssistStructure.VALIDATE_VIEW_TOKEN, nestingLevel);
      reader.mNumReadViews++;
      initializeFromParcelWithoutChildren(
          in,
          (PooledStringReader) Objects.requireNonNull(reader.mStringReader),
          (float[]) Objects.requireNonNull(reader.mTmpMatrix));
      if ((this.mFlags & 1048576) != 0) {
        int numChildren = in.readInt();
        this.mChildren = new ViewNode[numChildren];
        for (int i = 0; i < numChildren; i++) {
          this.mChildren[i] = new ViewNode(reader, nestingLevel + 1);
        }
      }
    }

    private static void writeString(Parcel out, PooledStringWriter pwriter, String str) {
      if (pwriter != null) {
        pwriter.writeString(str);
      } else {
        out.writeString(str);
      }
    }

    private static String readString(Parcel in, PooledStringReader preader) {
      if (preader != null) {
        return preader.readString();
      }
      return in.readString();
    }

    void initializeFromParcelWithoutChildren(
        Parcel in, PooledStringReader preader, float[] tmpMatrix) {
      this.mClassName = readString(in, preader);
      this.mFlags = in.readInt();
      int flags = this.mFlags;
      this.mAutofillFlags = in.readInt();
      int autofillFlags = this.mAutofillFlags;
      if ((2097152 & flags) != 0) {
        int readInt = in.readInt();
        this.mId = readInt;
        if (readInt != -1) {
          String readString = readString(in, preader);
          this.mIdEntry = readString;
          if (readString != null) {
            this.mIdType = readString(in, preader);
            this.mIdPackage = readString(in, preader);
          }
        }
      }
      if (autofillFlags != 0) {
        this.mSanitized = in.readInt() == 1;
        this.mImportantForAutofill = in.readInt();
        if ((autofillFlags & 1) != 0) {
          int autofillViewId = in.readInt();
          if ((autofillFlags & 2) != 0) {
            this.mAutofillId = new AutofillId(autofillViewId, in.readInt());
          } else {
            this.mAutofillId = new AutofillId(autofillViewId);
          }
          if ((autofillFlags & 2048) != 0) {
            this.mAutofillId.setSessionId(in.readInt());
          }
        }
        if ((autofillFlags & 8) != 0) {
          this.mAutofillType = in.readInt();
        }
        if ((autofillFlags & 16) != 0) {
          this.mAutofillHints = in.readStringArray();
        }
        if ((autofillFlags & 4) != 0) {
          this.mAutofillValue = (AutofillValue) in.readParcelable(null, AutofillValue.class);
        }
        if ((autofillFlags & 32) != 0) {
          this.mAutofillOptions = in.readCharSequenceArray();
        }
        if ((autofillFlags & 64) != 0) {
          this.mHtmlInfo =
              (ViewStructure.HtmlInfo) in.readParcelable(null, ViewStructure.HtmlInfo.class);
        }
        if ((autofillFlags & 256) != 0) {
          this.mMinEms = in.readInt();
        }
        if ((autofillFlags & 512) != 0) {
          this.mMaxEms = in.readInt();
        }
        if ((autofillFlags & 1024) != 0) {
          this.mMaxLength = in.readInt();
        }
        if ((autofillFlags & 128) != 0) {
          this.mTextIdEntry = readString(in, preader);
        }
        if ((autofillFlags & 4096) != 0) {
          this.mHintIdEntry = readString(in, preader);
        }
      }
      if ((67108864 & flags) != 0) {
        this.f29mX = in.readInt();
        this.f30mY = in.readInt();
        this.mWidth = in.readInt();
        this.mHeight = in.readInt();
      } else {
        int val = in.readInt();
        this.f29mX = val & 32767;
        this.f30mY = (val >> 16) & 32767;
        int val2 = in.readInt();
        this.mWidth = val2 & 32767;
        this.mHeight = (val2 >> 16) & 32767;
      }
      if ((134217728 & flags) != 0) {
        this.mScrollX = in.readInt();
        this.mScrollY = in.readInt();
      }
      if ((1073741824 & flags) != 0) {
        this.mMatrix = new Matrix();
        if (tmpMatrix == null) {
          tmpMatrix = new float[9];
        }
        in.readFloatArray(tmpMatrix);
        this.mMatrix.setValues(tmpMatrix);
      }
      if ((268435456 & flags) != 0) {
        this.mElevation = in.readFloat();
      }
      if ((536870912 & flags) != 0) {
        this.mAlpha = in.readFloat();
      }
      if ((33554432 & flags) != 0) {
        this.mContentDescription = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(in);
      }
      if ((16777216 & flags) != 0) {
        this.mText = new ViewNodeText(in, (8388608 & flags) == 0);
      }
      if ((262144 & flags) != 0) {
        this.mInputType = in.readInt();
      }
      if ((131072 & flags) != 0) {
        this.mWebScheme = in.readString();
      }
      if ((524288 & flags) != 0) {
        this.mWebDomain = in.readString();
      }
      if ((65536 & flags) != 0) {
        this.mLocaleList = (LocaleList) in.readParcelable(null, LocaleList.class);
      }
      if ((Integer.MIN_VALUE & flags) != 0) {
        this.mReceiveContentMimeTypes = in.readStringArray();
      }
      if ((4194304 & flags) != 0) {
        this.mExtras = in.readBundle();
      }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0038, code lost:

       if ((((r25.mWidth & (-32768)) != 0) | ((r25.mHeight & (-32768)) != 0)) != false) goto L19;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    int writeSelfToParcel(
        Parcel parcel, PooledStringWriter pooledStringWriter, boolean z, float[] fArr, boolean z2) {
      float[] fArr2;
      AutofillValue autofillValue;
      boolean z3 = true;
      int i = this.mFlags & 65535;
      int i2 = 0;
      if (this.mId != -1) {
        i |= 2097152;
      }
      if ((this.f29mX & (-32768)) == 0 && (this.f30mY & (-32768)) == 0) {}
      i |= 67108864;
      if (this.mScrollX != 0 || this.mScrollY != 0) {
        i |= 134217728;
      }
      if (this.mMatrix != null) {
        i |= 1073741824;
      }
      if (this.mElevation != 0.0f) {
        i |= 268435456;
      }
      if (this.mAlpha != 1.0f) {
        i |= 536870912;
      }
      if (this.mContentDescription != null) {
        i |= 33554432;
      }
      ViewNodeText viewNodeText = this.mText;
      if (viewNodeText != null) {
        i |= 16777216;
        if (!viewNodeText.isSimple()) {
          i |= 8388608;
        }
      }
      if (this.mInputType != 0) {
        i |= 262144;
      }
      if (this.mWebScheme != null) {
        i |= 131072;
      }
      if (this.mWebDomain != null) {
        i |= 524288;
      }
      if (this.mLocaleList != null) {
        i |= 65536;
      }
      if (this.mReceiveContentMimeTypes != null) {
        i |= Integer.MIN_VALUE;
      }
      if (this.mExtras != null) {
        i |= 4194304;
      }
      if (this.mChildren != null && z2) {
        i |= 1048576;
      }
      AutofillId autofillId = this.mAutofillId;
      if (autofillId != null) {
        i2 = 0 | 1;
        if (autofillId.isVirtualInt()) {
          i2 |= 2;
        }
        if (this.mAutofillId.hasSession()) {
          i2 |= 2048;
        }
      }
      if (this.mAutofillValue != null) {
        i2 |= 4;
      }
      if (this.mAutofillType != 0) {
        i2 |= 8;
      }
      if (this.mAutofillHints != null) {
        i2 |= 16;
      }
      if (this.mAutofillOptions != null) {
        i2 |= 32;
      }
      if (this.mHtmlInfo instanceof Parcelable) {
        i2 |= 64;
      }
      if (this.mMinEms > -1) {
        i2 |= 256;
      }
      if (this.mMaxEms > -1) {
        i2 |= 512;
      }
      if (this.mMaxLength > -1) {
        i2 |= 1024;
      }
      if (this.mTextIdEntry != null) {
        i2 |= 128;
      }
      if (this.mHintIdEntry != null) {
        i2 |= 4096;
      }
      writeString(parcel, pooledStringWriter, this.mClassName);
      int i3 = i;
      if (i2 != 0 && (this.mSanitized || !z)) {
        i3 = i & (-513);
      }
      AutofillOverlay autofillOverlay = this.mAutofillOverlay;
      if (autofillOverlay != null) {
        if (autofillOverlay.focused) {
          i3 |= 32;
        } else {
          i3 &= -33;
        }
      }
      parcel.writeInt(i3);
      parcel.writeInt(i2);
      if ((2097152 & i) != 0) {
        parcel.writeInt(this.mId);
        if (this.mId != -1) {
          writeString(parcel, pooledStringWriter, this.mIdEntry);
          if (this.mIdEntry != null) {
            writeString(parcel, pooledStringWriter, this.mIdType);
            writeString(parcel, pooledStringWriter, this.mIdPackage);
          }
        }
      }
      if (i2 != 0) {
        parcel.writeInt(this.mSanitized ? 1 : 0);
        parcel.writeInt(this.mImportantForAutofill);
        z3 = this.mSanitized || !z;
        if ((i2 & 1) != 0) {
          parcel.writeInt(this.mAutofillId.getViewId());
          if ((i2 & 2) != 0) {
            parcel.writeInt(this.mAutofillId.getVirtualChildIntId());
          }
          if ((i2 & 2048) != 0) {
            parcel.writeInt(this.mAutofillId.getSessionId());
          }
        }
        if ((i2 & 8) != 0) {
          parcel.writeInt(this.mAutofillType);
        }
        if ((i2 & 16) != 0) {
          parcel.writeStringArray(this.mAutofillHints);
        }
        if ((i2 & 4) != 0) {
          if (z3) {
            autofillValue = this.mAutofillValue;
          } else {
            AutofillOverlay autofillOverlay2 = this.mAutofillOverlay;
            if (autofillOverlay2 != null && autofillOverlay2.value != null) {
              autofillValue = this.mAutofillOverlay.value;
            } else {
              autofillValue = null;
            }
          }
          parcel.writeParcelable(autofillValue, 0);
        }
        if ((i2 & 32) != 0) {
          parcel.writeCharSequenceArray(this.mAutofillOptions);
        }
        if ((i2 & 64) != 0) {
          parcel.writeParcelable((Parcelable) this.mHtmlInfo, 0);
        }
        if ((i2 & 256) != 0) {
          parcel.writeInt(this.mMinEms);
        }
        if ((i2 & 512) != 0) {
          parcel.writeInt(this.mMaxEms);
        }
        if ((i2 & 1024) != 0) {
          parcel.writeInt(this.mMaxLength);
        }
        if ((i2 & 128) != 0) {
          writeString(parcel, pooledStringWriter, this.mTextIdEntry);
        }
        if ((i2 & 4096) != 0) {
          writeString(parcel, pooledStringWriter, this.mHintIdEntry);
        }
      }
      if ((i & 67108864) == 0) {
        parcel.writeInt((this.f30mY << 16) | this.f29mX);
        parcel.writeInt((this.mHeight << 16) | this.mWidth);
      } else {
        parcel.writeInt(this.f29mX);
        parcel.writeInt(this.f30mY);
        parcel.writeInt(this.mWidth);
        parcel.writeInt(this.mHeight);
      }
      if ((i & 134217728) != 0) {
        parcel.writeInt(this.mScrollX);
        parcel.writeInt(this.mScrollY);
      }
      if ((i & 1073741824) != 0) {
        if (fArr != null) {
          fArr2 = fArr;
        } else {
          fArr2 = new float[9];
        }
        this.mMatrix.getValues(fArr2);
        parcel.writeFloatArray(fArr2);
      }
      if ((i & 268435456) != 0) {
        parcel.writeFloat(this.mElevation);
      }
      if ((i & 536870912) != 0) {
        parcel.writeFloat(this.mAlpha);
      }
      if ((i & 33554432) != 0) {
        TextUtils.writeToParcel(this.mContentDescription, parcel, 0);
      }
      if ((i & 16777216) != 0) {
        this.mText.writeToParcel(parcel, (i & 8388608) == 0, z3);
      }
      if ((i & 262144) != 0) {
        parcel.writeInt(this.mInputType);
      }
      if ((i & 131072) != 0) {
        parcel.writeString(this.mWebScheme);
      }
      if ((i & 524288) != 0) {
        parcel.writeString(this.mWebDomain);
      }
      if ((i & 65536) != 0) {
        parcel.writeParcelable(this.mLocaleList, 0);
      }
      if ((i & Integer.MIN_VALUE) != 0) {
        parcel.writeStringArray(this.mReceiveContentMimeTypes);
      }
      if ((i & 4194304) != 0) {
        parcel.writeBundle(this.mExtras);
      }
      return i;
    }

    public int getId() {
      return this.mId;
    }

    public String getIdPackage() {
      return this.mIdPackage;
    }

    public String getIdType() {
      return this.mIdType;
    }

    public String getIdEntry() {
      return this.mIdEntry;
    }

    public AutofillId getAutofillId() {
      return this.mAutofillId;
    }

    public int getAutofillType() {
      return this.mAutofillType;
    }

    public String[] getAutofillHints() {
      return this.mAutofillHints;
    }

    public AutofillValue getAutofillValue() {
      return this.mAutofillValue;
    }

    public void setAutofillOverlay(AutofillOverlay overlay) {
      this.mAutofillOverlay = overlay;
    }

    public CharSequence[] getAutofillOptions() {
      return this.mAutofillOptions;
    }

    public int getInputType() {
      return this.mInputType;
    }

    public boolean isSanitized() {
      return this.mSanitized;
    }

    public void updateAutofillValue(AutofillValue value) {
      this.mAutofillValue = value;
      if (value.isText()) {
        if (this.mText == null) {
          this.mText = new ViewNodeText();
        }
        this.mText.mText = value.getTextValue();
      }
    }

    public int getLeft() {
      return this.f29mX;
    }

    public int getTop() {
      return this.f30mY;
    }

    public int getScrollX() {
      return this.mScrollX;
    }

    public int getScrollY() {
      return this.mScrollY;
    }

    public int getWidth() {
      return this.mWidth;
    }

    public int getHeight() {
      return this.mHeight;
    }

    public Matrix getTransformation() {
      return this.mMatrix;
    }

    public float getElevation() {
      return this.mElevation;
    }

    public float getAlpha() {
      return this.mAlpha;
    }

    public int getVisibility() {
      return this.mFlags & 12;
    }

    public boolean isAssistBlocked() {
      return (this.mFlags & 128) != 0;
    }

    public boolean isEnabled() {
      return (this.mFlags & 1) == 0;
    }

    public boolean isClickable() {
      return (this.mFlags & 1024) != 0;
    }

    public boolean isFocusable() {
      return (this.mFlags & 16) != 0;
    }

    public boolean isFocused() {
      return (this.mFlags & 32) != 0;
    }

    public boolean isAccessibilityFocused() {
      return (this.mFlags & 4096) != 0;
    }

    public boolean isCheckable() {
      return (this.mFlags & 256) != 0;
    }

    public boolean isChecked() {
      return (this.mFlags & 512) != 0;
    }

    public boolean isSelected() {
      return (this.mFlags & 64) != 0;
    }

    public boolean isActivated() {
      return (this.mFlags & 8192) != 0;
    }

    public boolean isOpaque() {
      return (this.mFlags & 32768) != 0;
    }

    public boolean isLongClickable() {
      return (this.mFlags & 2048) != 0;
    }

    public boolean isContextClickable() {
      return (this.mFlags & 16384) != 0;
    }

    public String getClassName() {
      return this.mClassName;
    }

    public CharSequence getContentDescription() {
      return this.mContentDescription;
    }

    public String getWebDomain() {
      return this.mWebDomain;
    }

    public void setWebDomain(String domain) {
      if (domain == null) {
        return;
      }
      Uri uri = Uri.parse(domain);
      if (uri == null) {
        Log.m102w(AssistStructure.TAG, "Failed to parse web domain");
        return;
      }
      String scheme = uri.getScheme();
      this.mWebScheme = scheme;
      if (scheme == null) {
        uri = Uri.parse("http://" + domain);
      }
      this.mWebDomain = uri.getHost();
    }

    public String getWebScheme() {
      return this.mWebScheme;
    }

    public ViewStructure.HtmlInfo getHtmlInfo() {
      return this.mHtmlInfo;
    }

    public LocaleList getLocaleList() {
      return this.mLocaleList;
    }

    public String[] getReceiveContentMimeTypes() {
      return this.mReceiveContentMimeTypes;
    }

    public CharSequence getText() {
      ViewNodeText viewNodeText = this.mText;
      if (viewNodeText != null) {
        return viewNodeText.mText;
      }
      return null;
    }

    public int getTextSelectionStart() {
      ViewNodeText viewNodeText = this.mText;
      if (viewNodeText != null) {
        return viewNodeText.mTextSelectionStart;
      }
      return -1;
    }

    public int getTextSelectionEnd() {
      ViewNodeText viewNodeText = this.mText;
      if (viewNodeText != null) {
        return viewNodeText.mTextSelectionEnd;
      }
      return -1;
    }

    public int getTextColor() {
      ViewNodeText viewNodeText = this.mText;
      if (viewNodeText != null) {
        return viewNodeText.mTextColor;
      }
      return 1;
    }

    public int getTextBackgroundColor() {
      ViewNodeText viewNodeText = this.mText;
      if (viewNodeText != null) {
        return viewNodeText.mTextBackgroundColor;
      }
      return 1;
    }

    public float getTextSize() {
      ViewNodeText viewNodeText = this.mText;
      if (viewNodeText != null) {
        return viewNodeText.mTextSize;
      }
      return 0.0f;
    }

    public int getTextStyle() {
      ViewNodeText viewNodeText = this.mText;
      if (viewNodeText != null) {
        return viewNodeText.mTextStyle;
      }
      return 0;
    }

    public int[] getTextLineCharOffsets() {
      ViewNodeText viewNodeText = this.mText;
      if (viewNodeText != null) {
        return viewNodeText.mLineCharOffsets;
      }
      return null;
    }

    public int[] getTextLineBaselines() {
      ViewNodeText viewNodeText = this.mText;
      if (viewNodeText != null) {
        return viewNodeText.mLineBaselines;
      }
      return null;
    }

    public String getTextIdEntry() {
      return this.mTextIdEntry;
    }

    public String getHint() {
      ViewNodeText viewNodeText = this.mText;
      if (viewNodeText != null) {
        return viewNodeText.mHint;
      }
      return null;
    }

    public String getHintIdEntry() {
      return this.mHintIdEntry;
    }

    public Bundle getExtras() {
      return this.mExtras;
    }

    public int getChildCount() {
      ViewNode[] viewNodeArr = this.mChildren;
      if (viewNodeArr != null) {
        return viewNodeArr.length;
      }
      return 0;
    }

    public ViewNode getChildAt(int index) {
      return this.mChildren[index];
    }

    public int getMinTextEms() {
      return this.mMinEms;
    }

    public int getMaxTextEms() {
      return this.mMaxEms;
    }

    public int getMaxTextLength() {
      return this.mMaxLength;
    }

    public int getImportantForAutofill() {
      return this.mImportantForAutofill;
    }
  }

  public static final class ViewNodeParcelable implements Parcelable {
    public static final Parcelable.Creator<ViewNodeParcelable> CREATOR =
        new Parcelable.Creator<
            ViewNodeParcelable>() { // from class:
                                    // android.app.assist.AssistStructure.ViewNodeParcelable.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public ViewNodeParcelable createFromParcel(Parcel in) {
            return new ViewNodeParcelable(in);
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public ViewNodeParcelable[] newArray(int size) {
            return new ViewNodeParcelable[size];
          }
        };
    private final ViewNode mViewNode;

    public ViewNodeParcelable(ViewNode viewNode) {
      this.mViewNode = viewNode;
    }

    public ViewNodeParcelable(Parcel in) {
      this.mViewNode = new ViewNode(in);
    }

    public ViewNode getViewNode() {
      return this.mViewNode;
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
      return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
      this.mViewNode.writeSelfToParcel(parcel, null, false, null, false);
    }
  }

  public static class ViewNodeBuilder extends ViewStructure {
    final AssistStructure mAssist;
    final boolean mAsync;
    final ViewNode mNode;

    public ViewNodeBuilder() {
      this.mAssist = new AssistStructure();
      this.mNode = new ViewNode();
      this.mAsync = false;
    }

    ViewNodeBuilder(AssistStructure assist, ViewNode node, boolean async) {
      this.mAssist = assist;
      this.mNode = node;
      this.mAsync = async;
    }

    public ViewNode getViewNode() {
      return this.mNode;
    }

    @Override // android.view.ViewStructure
    public void setId(int id, String packageName, String typeName, String entryName) {
      this.mNode.mId = id;
      this.mNode.mIdPackage = packageName;
      this.mNode.mIdType = typeName;
      this.mNode.mIdEntry = entryName;
    }

    @Override // android.view.ViewStructure
    public void setDimens(int left, int top, int scrollX, int scrollY, int width, int height) {
      this.mNode.f29mX = left;
      this.mNode.f30mY = top;
      this.mNode.mScrollX = scrollX;
      this.mNode.mScrollY = scrollY;
      this.mNode.mWidth = width;
      this.mNode.mHeight = height;
    }

    @Override // android.view.ViewStructure
    public void setTransformation(Matrix matrix) {
      if (matrix == null) {
        this.mNode.mMatrix = null;
      } else {
        this.mNode.mMatrix = new Matrix(matrix);
      }
    }

    @Override // android.view.ViewStructure
    public void setElevation(float elevation) {
      this.mNode.mElevation = elevation;
    }

    @Override // android.view.ViewStructure
    public void setAlpha(float alpha) {
      this.mNode.mAlpha = alpha;
    }

    @Override // android.view.ViewStructure
    public void setVisibility(int visibility) {
      ViewNode viewNode = this.mNode;
      viewNode.mFlags = (viewNode.mFlags & (-13)) | (visibility & 12);
    }

    @Override // android.view.ViewStructure
    public void setAssistBlocked(boolean state) {
      ViewNode viewNode = this.mNode;
      viewNode.mFlags =
          (viewNode.mFlags & PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE)
              | (state ? 128 : 0);
    }

    @Override // android.view.ViewStructure
    public void setEnabled(boolean z) {
      ViewNode viewNode = this.mNode;
      viewNode.mFlags = (viewNode.mFlags & (-2)) | (!z ? 1 : 0);
    }

    @Override // android.view.ViewStructure
    public void setClickable(boolean state) {
      ViewNode viewNode = this.mNode;
      viewNode.mFlags = (viewNode.mFlags & (-1025)) | (state ? 1024 : 0);
    }

    @Override // android.view.ViewStructure
    public void setLongClickable(boolean state) {
      ViewNode viewNode = this.mNode;
      viewNode.mFlags = (viewNode.mFlags & (-2049)) | (state ? 2048 : 0);
    }

    @Override // android.view.ViewStructure
    public void setContextClickable(boolean state) {
      ViewNode viewNode = this.mNode;
      viewNode.mFlags = (viewNode.mFlags & (-16385)) | (state ? 16384 : 0);
    }

    @Override // android.view.ViewStructure
    public void setFocusable(boolean state) {
      ViewNode viewNode = this.mNode;
      viewNode.mFlags = (viewNode.mFlags & (-17)) | (state ? 16 : 0);
    }

    @Override // android.view.ViewStructure
    public void setFocused(boolean state) {
      ViewNode viewNode = this.mNode;
      viewNode.mFlags = (viewNode.mFlags & (-33)) | (state ? 32 : 0);
    }

    @Override // android.view.ViewStructure
    public void setAccessibilityFocused(boolean state) {
      ViewNode viewNode = this.mNode;
      viewNode.mFlags = (viewNode.mFlags & (-4097)) | (state ? 4096 : 0);
    }

    @Override // android.view.ViewStructure
    public void setCheckable(boolean state) {
      ViewNode viewNode = this.mNode;
      viewNode.mFlags = (viewNode.mFlags & (-257)) | (state ? 256 : 0);
    }

    @Override // android.view.ViewStructure
    public void setChecked(boolean state) {
      ViewNode viewNode = this.mNode;
      viewNode.mFlags = (viewNode.mFlags & (-513)) | (state ? 512 : 0);
    }

    @Override // android.view.ViewStructure
    public void setSelected(boolean state) {
      ViewNode viewNode = this.mNode;
      viewNode.mFlags = (viewNode.mFlags & (-65)) | (state ? 64 : 0);
    }

    @Override // android.view.ViewStructure
    public void setActivated(boolean state) {
      ViewNode viewNode = this.mNode;
      viewNode.mFlags = (viewNode.mFlags & (-8193)) | (state ? 8192 : 0);
    }

    @Override // android.view.ViewStructure
    public void setOpaque(boolean opaque) {
      ViewNode viewNode = this.mNode;
      viewNode.mFlags = (viewNode.mFlags & (-32769)) | (opaque ? 32768 : 0);
    }

    @Override // android.view.ViewStructure
    public void setClassName(String className) {
      this.mNode.mClassName = className;
    }

    @Override // android.view.ViewStructure
    public void setContentDescription(CharSequence contentDescription) {
      this.mNode.mContentDescription = contentDescription;
    }

    private final ViewNodeText getNodeText() {
      if (this.mNode.mText != null) {
        return this.mNode.mText;
      }
      this.mNode.mText = new ViewNodeText();
      return this.mNode.mText;
    }

    @Override // android.view.ViewStructure
    public void setText(CharSequence text) {
      ViewNodeText t = getNodeText();
      t.mText = TextUtils.trimToParcelableSize(stripAllSpansFromText(text));
      t.mTextSelectionEnd = -1;
      t.mTextSelectionStart = -1;
    }

    @Override // android.view.ViewStructure
    public void setText(CharSequence text, int selectionStart, int selectionEnd) {
      ViewNodeText t = getNodeText();
      t.mText = stripAllSpansFromText(text);
      t.mTextSelectionStart = selectionStart;
      t.mTextSelectionEnd = selectionEnd;
    }

    @Override // android.view.ViewStructure
    public void setTextStyle(float size, int fgColor, int bgColor, int style) {
      ViewNodeText t = getNodeText();
      t.mTextColor = fgColor;
      t.mTextBackgroundColor = bgColor;
      t.mTextSize = size;
      t.mTextStyle = style;
    }

    @Override // android.view.ViewStructure
    public void setTextLines(int[] charOffsets, int[] baselines) {
      ViewNodeText t = getNodeText();
      t.mLineCharOffsets = charOffsets;
      t.mLineBaselines = baselines;
    }

    @Override // android.view.ViewStructure
    public void setTextIdEntry(String entryName) {
      this.mNode.mTextIdEntry = (String) Objects.requireNonNull(entryName);
    }

    @Override // android.view.ViewStructure
    public void setHint(CharSequence hint) {
      getNodeText().mHint = hint != null ? hint.toString() : null;
    }

    @Override // android.view.ViewStructure
    public void setHintIdEntry(String entryName) {
      this.mNode.mHintIdEntry = (String) Objects.requireNonNull(entryName);
    }

    @Override // android.view.ViewStructure
    public CharSequence getText() {
      if (this.mNode.mText != null) {
        return this.mNode.mText.mText;
      }
      return null;
    }

    @Override // android.view.ViewStructure
    public int getTextSelectionStart() {
      if (this.mNode.mText != null) {
        return this.mNode.mText.mTextSelectionStart;
      }
      return -1;
    }

    @Override // android.view.ViewStructure
    public int getTextSelectionEnd() {
      if (this.mNode.mText != null) {
        return this.mNode.mText.mTextSelectionEnd;
      }
      return -1;
    }

    @Override // android.view.ViewStructure
    public CharSequence getHint() {
      if (this.mNode.mText != null) {
        return this.mNode.mText.mHint;
      }
      return null;
    }

    @Override // android.view.ViewStructure
    public Bundle getExtras() {
      if (this.mNode.mExtras != null) {
        return this.mNode.mExtras;
      }
      this.mNode.mExtras = new Bundle();
      return this.mNode.mExtras;
    }

    @Override // android.view.ViewStructure
    public boolean hasExtras() {
      return this.mNode.mExtras != null;
    }

    @Override // android.view.ViewStructure
    public void setChildCount(int num) {
      this.mNode.mChildren = new ViewNode[num];
    }

    @Override // android.view.ViewStructure
    public int addChildCount(int num) {
      if (this.mNode.mChildren == null) {
        setChildCount(num);
        return 0;
      }
      int start = this.mNode.mChildren.length;
      ViewNode[] newArray = new ViewNode[start + num];
      System.arraycopy(this.mNode.mChildren, 0, newArray, 0, start);
      this.mNode.mChildren = newArray;
      return start;
    }

    @Override // android.view.ViewStructure
    public int getChildCount() {
      if (this.mNode.mChildren != null) {
        return this.mNode.mChildren.length;
      }
      return 0;
    }

    @Override // android.view.ViewStructure
    public ViewStructure newChild(int index) {
      ViewNode node = new ViewNode();
      this.mNode.mChildren[index] = node;
      return new ViewNodeBuilder(this.mAssist, node, false);
    }

    @Override // android.view.ViewStructure
    public ViewStructure asyncNewChild(int index) {
      ViewNodeBuilder builder;
      synchronized (this.mAssist) {
        ViewNode node = new ViewNode();
        this.mNode.mChildren[index] = node;
        builder = new ViewNodeBuilder(this.mAssist, node, true);
        this.mAssist.mPendingAsyncChildren.add(builder);
      }
      return builder;
    }

    @Override // android.view.ViewStructure
    public void asyncCommit() {
      synchronized (this.mAssist) {
        if (!this.mAsync) {
          throw new IllegalStateException(
              "Child " + this + " was not created with ViewStructure.asyncNewChild");
        }
        if (!this.mAssist.mPendingAsyncChildren.remove(this)) {
          throw new IllegalStateException("Child " + this + " already committed");
        }
        this.mAssist.notifyAll();
      }
    }

    @Override // android.view.ViewStructure
    public Rect getTempRect() {
      return this.mAssist.mTmpRect;
    }

    @Override // android.view.ViewStructure
    public void setAutofillId(AutofillId id) {
      this.mNode.mAutofillId = id;
    }

    @Override // android.view.ViewStructure
    public void setAutofillId(AutofillId parentId, int virtualId) {
      this.mNode.mAutofillId = new AutofillId(parentId, virtualId);
    }

    @Override // android.view.ViewStructure
    public AutofillId getAutofillId() {
      return this.mNode.mAutofillId;
    }

    @Override // android.view.ViewStructure
    public void setAutofillType(int type) {
      this.mNode.mAutofillType = type;
    }

    @Override // android.view.ViewStructure
    public void setAutofillHints(String[] hints) {
      this.mNode.mAutofillHints = hints;
    }

    @Override // android.view.ViewStructure
    public void setAutofillValue(AutofillValue value) {
      this.mNode.mAutofillValue = value;
    }

    @Override // android.view.ViewStructure
    public void setAutofillOptions(CharSequence[] options) {
      this.mNode.mAutofillOptions = options;
    }

    @Override // android.view.ViewStructure
    public void setImportantForAutofill(int mode) {
      this.mNode.mImportantForAutofill = mode;
    }

    @Override // android.view.ViewStructure
    public void setReceiveContentMimeTypes(String[] mimeTypes) {
      this.mNode.mReceiveContentMimeTypes = mimeTypes;
    }

    @Override // android.view.ViewStructure
    public void setInputType(int inputType) {
      this.mNode.mInputType = inputType;
    }

    @Override // android.view.ViewStructure
    public void setMinTextEms(int minEms) {
      this.mNode.mMinEms = minEms;
    }

    @Override // android.view.ViewStructure
    public void setMaxTextEms(int maxEms) {
      this.mNode.mMaxEms = maxEms;
    }

    @Override // android.view.ViewStructure
    public void setMaxTextLength(int maxLength) {
      this.mNode.mMaxLength = maxLength;
    }

    @Override // android.view.ViewStructure
    public void setDataIsSensitive(boolean sensitive) {
      this.mNode.mSanitized = !sensitive;
    }

    @Override // android.view.ViewStructure
    public void setWebDomain(String domain) {
      this.mNode.setWebDomain(domain);
    }

    @Override // android.view.ViewStructure
    public void setLocaleList(LocaleList localeList) {
      this.mNode.mLocaleList = localeList;
    }

    @Override // android.view.ViewStructure
    public ViewStructure.HtmlInfo.Builder newHtmlInfoBuilder(String tagName) {
      return new HtmlInfoNodeBuilder(tagName);
    }

    @Override // android.view.ViewStructure
    public void setHtmlInfo(ViewStructure.HtmlInfo htmlInfo) {
      this.mNode.mHtmlInfo = htmlInfo;
    }

    private CharSequence stripAllSpansFromText(CharSequence text) {
      if (text instanceof Spanned) {
        return text.toString();
      }
      return text;
    }
  }

  private static final class HtmlInfoNode extends ViewStructure.HtmlInfo implements Parcelable {
    public static final Parcelable.Creator<HtmlInfoNode> CREATOR =
        new Parcelable.Creator<
            HtmlInfoNode>() { // from class: android.app.assist.AssistStructure.HtmlInfoNode.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public HtmlInfoNode createFromParcel(Parcel parcel) {
            String tag = parcel.readString();
            HtmlInfoNodeBuilder builder = new HtmlInfoNodeBuilder(tag);
            String[] names = parcel.readStringArray();
            String[] values = parcel.readStringArray();
            if (names != null && values != null) {
              if (names.length != values.length) {
                Log.m102w(
                    AssistStructure.TAG,
                    "HtmlInfo attributes mismatch: names="
                        + names.length
                        + ", values="
                        + values.length);
              } else {
                for (int i = 0; i < names.length; i++) {
                  builder.addAttribute(names[i], values[i]);
                }
              }
            }
            return builder.build();
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public HtmlInfoNode[] newArray(int size) {
            return new HtmlInfoNode[size];
          }
        };
    private ArrayList<Pair<String, String>> mAttributes;
    private final String[] mNames;
    private final String mTag;
    private final String[] mValues;

    private HtmlInfoNode(HtmlInfoNodeBuilder builder) {
      this.mTag = builder.mTag;
      if (builder.mNames == null) {
        this.mNames = null;
        this.mValues = null;
        return;
      }
      String[] strArr = new String[builder.mNames.size()];
      this.mNames = strArr;
      String[] strArr2 = new String[builder.mValues.size()];
      this.mValues = strArr2;
      builder.mNames.toArray(strArr);
      builder.mValues.toArray(strArr2);
    }

    @Override // android.view.ViewStructure.HtmlInfo
    public String getTag() {
      return this.mTag;
    }

    @Override // android.view.ViewStructure.HtmlInfo
    public List<Pair<String, String>> getAttributes() {
      if (this.mAttributes == null && this.mNames != null) {
        this.mAttributes = new ArrayList<>(this.mNames.length);
        int i = 0;
        while (true) {
          String[] strArr = this.mNames;
          if (i >= strArr.length) {
            break;
          }
          Pair<String, String> pair = new Pair<>(strArr[i], this.mValues[i]);
          this.mAttributes.add(i, pair);
          i++;
        }
      }
      return this.mAttributes;
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
      return 0;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
      parcel.writeString(this.mTag);
      parcel.writeStringArray(this.mNames);
      parcel.writeStringArray(this.mValues);
    }
  }

  private static final class HtmlInfoNodeBuilder extends ViewStructure.HtmlInfo.Builder {
    private ArrayList<String> mNames;
    private final String mTag;
    private ArrayList<String> mValues;

    HtmlInfoNodeBuilder(String tag) {
      this.mTag = tag;
    }

    @Override // android.view.ViewStructure.HtmlInfo.Builder
    public ViewStructure.HtmlInfo.Builder addAttribute(String name, String value) {
      if (this.mNames == null) {
        this.mNames = new ArrayList<>();
        this.mValues = new ArrayList<>();
      }
      this.mNames.add(name);
      this.mValues.add(value);
      return this;
    }

    @Override // android.view.ViewStructure.HtmlInfo.Builder
    public HtmlInfoNode build() {
      return new HtmlInfoNode(this);
    }
  }

  public AssistStructure(Activity activity, boolean forAutoFill, int flags) {
    this.mWindowNodes = new ArrayList<>();
    this.mPendingAsyncChildren = new ArrayList<>();
    this.mTmpRect = new Rect();
    this.mSanitizeOnWrite = false;
    this.mHaveData = true;
    this.mFlags = flags;
    ArrayList<ViewRootImpl> views =
        WindowManagerGlobal.getInstance().getRootViews(activity.getActivityToken());
    for (int i = 0; i < views.size(); i++) {
      ViewRootImpl root = views.get(i);
      if (root.getView() == null) {
        Log.m102w(TAG, "Skipping window with dettached view: " + ((Object) root.getTitle()));
      } else {
        this.mWindowNodes.add(new WindowNode(this, root, forAutoFill, flags));
      }
    }
  }

  public AssistStructure() {
    this.mWindowNodes = new ArrayList<>();
    this.mPendingAsyncChildren = new ArrayList<>();
    this.mTmpRect = new Rect();
    this.mSanitizeOnWrite = false;
    this.mHaveData = true;
    this.mFlags = 0;
  }

  public AssistStructure(Parcel in) {
    this.mWindowNodes = new ArrayList<>();
    this.mPendingAsyncChildren = new ArrayList<>();
    this.mTmpRect = new Rect();
    this.mSanitizeOnWrite = false;
    this.mTaskId = in.readInt();
    this.mActivityComponent = ComponentName.readFromParcel(in);
    this.mIsHomeActivity = in.readInt() == 1;
    this.mReceiveChannel = in.readStrongBinder();
  }

  public void sanitizeForParceling(boolean sanitize) {
    this.mSanitizeOnWrite = sanitize;
  }

  public void dump(boolean showSensitive) {
    String str;
    if (this.mActivityComponent == null) {
      Log.m98i(TAG, "dump(): calling ensureData() first");
      ensureData();
    }
    Log.m98i(TAG, "Task id: " + this.mTaskId);
    StringBuilder append = new StringBuilder().append("Activity: ");
    ComponentName componentName = this.mActivityComponent;
    if (componentName != null) {
      str = componentName.flattenToShortString();
    } else {
      str = null;
    }
    Log.m98i(TAG, append.append(str).toString());
    Log.m98i(TAG, "Sanitize on write: " + this.mSanitizeOnWrite);
    Log.m98i(TAG, "Flags: " + this.mFlags);
    int N = getWindowNodeCount();
    for (int i = 0; i < N; i++) {
      WindowNode node = getWindowNodeAt(i);
      Log.m98i(
          TAG,
          "Window #"
              + i
              + " ["
              + node.getLeft()
              + ","
              + node.getTop()
              + " "
              + node.getWidth()
              + "x"
              + node.getHeight()
              + "] "
              + ((Object) node.getTitle()));
      dump("  ", node.getRootViewNode(), showSensitive);
    }
  }

  void dump(String prefix, ViewNode node, boolean showSensitive) {
    String safeText;
    Log.m98i(
        TAG,
        prefix
            + "View ["
            + node.getLeft()
            + ","
            + node.getTop()
            + " "
            + node.getWidth()
            + "x"
            + node.getHeight()
            + "] "
            + node.getClassName());
    int id = node.getId();
    if (id != 0) {
      StringBuilder sb = new StringBuilder();
      sb.append(prefix);
      sb.append("  ID: #");
      sb.append(Integer.toHexString(id));
      String entry = node.getIdEntry();
      if (entry != null) {
        String type = node.getIdType();
        String pkg = node.getIdPackage();
        sb.append(" ");
        sb.append(pkg);
        sb.append(":");
        sb.append(type);
        sb.append("/");
        sb.append(entry);
      }
      Log.m98i(TAG, sb.toString());
    }
    int scrollX = node.getScrollX();
    int scrollY = node.getScrollY();
    if (scrollX != 0 || scrollY != 0) {
      Log.m98i(TAG, prefix + "  Scroll: " + scrollX + "," + scrollY);
    }
    Matrix matrix = node.getTransformation();
    if (matrix != null) {
      Log.m98i(TAG, prefix + "  Transformation: " + matrix);
    }
    float elevation = node.getElevation();
    if (elevation != 0.0f) {
      Log.m98i(TAG, prefix + "  Elevation: " + elevation);
    }
    float alpha = node.getAlpha();
    if (alpha != 0.0f) {
      Log.m98i(TAG, prefix + "  Alpha: " + elevation);
    }
    CharSequence contentDescription = node.getContentDescription();
    if (contentDescription != null) {
      Log.m98i(TAG, prefix + "  Content description: " + ((Object) contentDescription));
    }
    CharSequence text = node.getText();
    if (text != null) {
      if (node.isSanitized() || showSensitive) {
        safeText = text.toString();
      } else {
        safeText = "REDACTED[" + text.length() + " chars]";
      }
      Log.m98i(
          TAG,
          prefix
              + "  Text (sel "
              + node.getTextSelectionStart()
              + NativeLibraryHelper.CLEAR_ABI_OVERRIDE
              + node.getTextSelectionEnd()
              + "): "
              + safeText);
      Log.m98i(
          TAG, prefix + "  Text size: " + node.getTextSize() + " , style: #" + node.getTextStyle());
      Log.m98i(
          TAG,
          prefix
              + "  Text color fg: #"
              + Integer.toHexString(node.getTextColor())
              + ", bg: #"
              + Integer.toHexString(node.getTextBackgroundColor()));
      Log.m98i(TAG, prefix + "  Input type: " + node.getInputType());
      Log.m98i(TAG, prefix + "  Resource id: " + node.getTextIdEntry());
    }
    String webDomain = node.getWebDomain();
    if (webDomain != null) {
      Log.m98i(TAG, prefix + "  Web domain: " + webDomain);
    }
    ViewStructure.HtmlInfo htmlInfo = node.getHtmlInfo();
    if (htmlInfo != null) {
      Log.m98i(
          TAG,
          prefix + "  HtmlInfo: tag=" + htmlInfo.getTag() + ", attr=" + htmlInfo.getAttributes());
    }
    LocaleList localeList = node.getLocaleList();
    if (localeList != null) {
      Log.m98i(TAG, prefix + "  LocaleList: " + localeList);
    }
    String[] mimeTypes = node.getReceiveContentMimeTypes();
    if (mimeTypes != null) {
      Log.m98i(TAG, prefix + "  MIME types: " + Arrays.toString(mimeTypes));
    }
    String hint = node.getHint();
    if (hint != null) {
      Log.m98i(TAG, prefix + "  Hint: " + hint);
      Log.m98i(TAG, prefix + "  Resource id: " + node.getHintIdEntry());
    }
    Bundle extras = node.getExtras();
    if (extras != null) {
      Log.m98i(TAG, prefix + "  Extras: " + extras);
    }
    if (node.isAssistBlocked()) {
      Log.m98i(TAG, prefix + "  BLOCKED");
    }
    AutofillId autofillId = node.getAutofillId();
    if (autofillId == null) {
      Log.m98i(TAG, prefix + " NO autofill ID");
    } else {
      Log.m98i(
          TAG,
          prefix
              + "  Autofill info: id= "
              + autofillId
              + ", type="
              + node.getAutofillType()
              + ", options="
              + Arrays.toString(node.getAutofillOptions())
              + ", hints="
              + Arrays.toString(node.getAutofillHints())
              + ", value="
              + node.getAutofillValue()
              + ", sanitized="
              + node.isSanitized()
              + ", important="
              + node.getImportantForAutofill());
    }
    int NCHILDREN = node.getChildCount();
    if (NCHILDREN > 0) {
      Log.m98i(TAG, prefix + "  Children:");
      String cprefix = prefix + "    ";
      int i = 0;
      while (i < NCHILDREN) {
        ViewNode cnode = node.getChildAt(i);
        dump(cprefix, cnode, showSensitive);
        i++;
        NCHILDREN = NCHILDREN;
      }
    }
  }

  public void setTaskId(int taskId) {
    this.mTaskId = taskId;
  }

  public int getTaskId() {
    return this.mTaskId;
  }

  public void setActivityComponent(ComponentName componentName) {
    this.mActivityComponent = componentName;
  }

  public ComponentName getActivityComponent() {
    return this.mActivityComponent;
  }

  public int getFlags() {
    return this.mFlags;
  }

  public boolean isHomeActivity() {
    return this.mIsHomeActivity;
  }

  public int getWindowNodeCount() {
    ensureData();
    return this.mWindowNodes.size();
  }

  public WindowNode getWindowNodeAt(int index) {
    ensureData();
    return this.mWindowNodes.get(index);
  }

  public void ensureDataForAutofill() {
    if (this.mHaveData) {
      return;
    }
    this.mHaveData = true;
    Binder.allowBlocking(this.mReceiveChannel);
    try {
      ParcelTransferReader reader = new ParcelTransferReader(this.mReceiveChannel);
      reader.m6go();
    } finally {
      Binder.defaultBlocking(this.mReceiveChannel);
    }
  }

  public void ensureData() {
    if (this.mHaveData) {
      return;
    }
    this.mHaveData = true;
    ParcelTransferReader reader = new ParcelTransferReader(this.mReceiveChannel);
    reader.m6go();
  }

  boolean waitForReady() {
    boolean skipStructure = false;
    synchronized (this) {
      long endTime = SystemClock.uptimeMillis() + 5000;
      while (this.mPendingAsyncChildren.size() > 0) {
        long now = SystemClock.uptimeMillis();
        if (now >= endTime) {
          break;
        }
        try {
          wait(endTime - now);
        } catch (InterruptedException e) {
        }
      }
      if (this.mPendingAsyncChildren.size() > 0) {
        Log.m102w(
            TAG,
            "Skipping assist structure, waiting too long for async children (have "
                + this.mPendingAsyncChildren.size()
                + " remaining");
        skipStructure = true;
      }
    }
    return !skipStructure;
  }

  public void clearSendChannel() {
    SendChannel sendChannel = this.mSendChannel;
    if (sendChannel != null) {
      sendChannel.mAssistStructure = null;
    }
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeInt(this.mTaskId);
    ComponentName.writeToParcel(this.mActivityComponent, parcel);
    parcel.writeInt(this.mIsHomeActivity ? 1 : 0);
    if (this.mHaveData) {
      if (this.mSendChannel == null) {
        this.mSendChannel = new SendChannel(this);
      }
      parcel.writeStrongBinder(this.mSendChannel);
      return;
    }
    parcel.writeStrongBinder(this.mReceiveChannel);
  }
}
