package android.app.assist;

import android.annotation.SystemApi;
import android.app.Activity;
import android.app.assist.flags.Flags;
import android.app.slice.Slice;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.credentials.CredentialOption;
import android.credentials.GetCredentialException;
import android.credentials.GetCredentialRequest;
import android.credentials.GetCredentialResponse;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.Uri;
import android.os.BadParcelableException;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.LocaleList;
import android.os.Looper;
import android.os.OutcomeReceiver;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PooledStringReader;
import android.os.PooledStringWriter;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.service.credentials.CredentialProviderService;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
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
    public static final Parcelable.Creator<AssistStructure> CREATOR = new Parcelable.Creator<AssistStructure>() { // from class: android.app.assist.AssistStructure.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AssistStructure createFromParcel(Parcel parcel) {
            return new AssistStructure(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AssistStructure[] newArray(int i) {
            return new AssistStructure[i];
        }
    };
    private static final boolean DEBUG_PARCEL = false;
    private static final boolean DEBUG_PARCEL_CHILDREN = false;
    private static final boolean DEBUG_PARCEL_TREE = false;
    private static final String DESCRIPTOR = "android.app.AssistStructure";
    private static final ArrayMap<Integer, String> INPUT_TYPE_VARIATIONS;
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

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void setAcquisitionStartTime(long j) {
        this.mAcquisitionStartTime = j;
    }

    public void setAcquisitionEndTime(long j) {
        this.mAcquisitionEndTime = j;
    }

    public void setHomeActivity(boolean z) {
        this.mIsHomeActivity = z;
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

        SendChannel(AssistStructure assistStructure) {
            this.mAssistStructure = assistStructure;
        }

        @Override // android.os.Binder
        protected boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 2) {
                AssistStructure assistStructure = this.mAssistStructure;
                if (assistStructure == null) {
                    return true;
                }
                parcel.enforceInterface(AssistStructure.DESCRIPTOR);
                IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder != null) {
                    if (readStrongBinder instanceof ParcelTransferWriter) {
                        ((ParcelTransferWriter) readStrongBinder).writeToParcel(assistStructure, parcel2);
                        return true;
                    }
                    Log.w(AssistStructure.TAG, "Caller supplied bad token type: " + readStrongBinder);
                    return true;
                }
                new ParcelTransferWriter(assistStructure, parcel2).writeToParcel(assistStructure, parcel2);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }
    }

    static final class ViewStackEntry {
        int curChild;
        ViewNode node;
        int numChildren;

        ViewStackEntry() {
        }
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

        ParcelTransferWriter(AssistStructure assistStructure, Parcel parcel) {
            this.mSanitizeOnWrite = assistStructure.mSanitizeOnWrite;
            boolean waitForReady = assistStructure.waitForReady();
            this.mWriteStructure = waitForReady;
            parcel.writeInt(assistStructure.mFlags);
            parcel.writeInt(assistStructure.mAutofillFlags);
            parcel.writeLong(assistStructure.mAcquisitionStartTime);
            parcel.writeLong(assistStructure.mAcquisitionEndTime);
            int size = assistStructure.mWindowNodes.size();
            this.mNumWindows = size;
            if (waitForReady && size > 0) {
                parcel.writeInt(size);
            } else {
                parcel.writeInt(0);
            }
        }

        void writeToParcel(AssistStructure assistStructure, Parcel parcel) {
            int dataPosition = parcel.dataPosition();
            this.mNumWrittenWindows = 0;
            this.mNumWrittenViews = 0;
            boolean writeToParcelInner = writeToParcelInner(assistStructure, parcel);
            StringBuilder sb = new StringBuilder("Flattened ");
            sb.append(writeToParcelInner ? Slice.HINT_PARTIAL : "final");
            sb.append(" assist data: ");
            sb.append(parcel.dataPosition() - dataPosition);
            sb.append(" bytes, containing ");
            sb.append(this.mNumWrittenWindows);
            sb.append(" windows, ");
            sb.append(this.mNumWrittenViews);
            sb.append(" views");
            Log.i(AssistStructure.TAG, sb.toString());
        }

        boolean writeToParcelInner(AssistStructure assistStructure, Parcel parcel) {
            if (this.mNumWindows == 0) {
                return false;
            }
            PooledStringWriter pooledStringWriter = new PooledStringWriter(parcel);
            while (writeNextEntryToParcel(assistStructure, parcel, pooledStringWriter)) {
                if (parcel.dataSize() > 65536) {
                    parcel.writeInt(0);
                    parcel.writeStrongBinder(this);
                    pooledStringWriter.finish();
                    return true;
                }
            }
            pooledStringWriter.finish();
            this.mViewStack.clear();
            return false;
        }

        void pushViewStackEntry(ViewNode viewNode, int i) {
            ViewStackEntry viewStackEntry;
            if (i >= this.mViewStack.size()) {
                viewStackEntry = new ViewStackEntry();
                this.mViewStack.add(viewStackEntry);
            } else {
                viewStackEntry = this.mViewStack.get(i);
            }
            viewStackEntry.node = viewNode;
            viewStackEntry.numChildren = viewNode.getChildCount();
            viewStackEntry.curChild = 0;
            this.mCurViewStackEntry = viewStackEntry;
        }

        void writeView(ViewNode viewNode, Parcel parcel, PooledStringWriter pooledStringWriter, int i) {
            parcel.writeInt(AssistStructure.VALIDATE_VIEW_TOKEN);
            if (Flags.addPlaceholderViewForNullChild() && viewNode == null) {
                viewNode = new ViewNode();
            }
            ViewNode viewNode2 = viewNode;
            int writeSelfToParcel = viewNode2.writeSelfToParcel(parcel, pooledStringWriter, this.mSanitizeOnWrite, this.mTmpMatrix, true);
            this.mNumWrittenViews++;
            if ((writeSelfToParcel & 1048576) != 0) {
                parcel.writeInt(viewNode2.mChildren.length);
                int i2 = this.mCurViewStackPos + 1;
                this.mCurViewStackPos = i2;
                pushViewStackEntry(viewNode2, i2);
            }
        }

        boolean writeNextEntryToParcel(AssistStructure assistStructure, Parcel parcel, PooledStringWriter pooledStringWriter) {
            ViewStackEntry viewStackEntry = this.mCurViewStackEntry;
            if (viewStackEntry != null) {
                if (viewStackEntry.curChild < this.mCurViewStackEntry.numChildren) {
                    ViewNode viewNode = this.mCurViewStackEntry.node.mChildren[this.mCurViewStackEntry.curChild];
                    this.mCurViewStackEntry.curChild++;
                    writeView(viewNode, parcel, pooledStringWriter, 1);
                    return true;
                }
                while (true) {
                    int i = this.mCurViewStackPos - 1;
                    this.mCurViewStackPos = i;
                    if (i < 0) {
                        this.mCurViewStackEntry = null;
                        break;
                    }
                    ViewStackEntry viewStackEntry2 = this.mViewStack.get(i);
                    this.mCurViewStackEntry = viewStackEntry2;
                    if (viewStackEntry2.curChild < this.mCurViewStackEntry.numChildren) {
                        break;
                    }
                }
                return true;
            }
            int i2 = this.mCurWindow;
            if (i2 >= this.mNumWindows) {
                return false;
            }
            WindowNode windowNode = (WindowNode) assistStructure.mWindowNodes.get(i2);
            this.mCurWindow++;
            parcel.writeInt(AssistStructure.VALIDATE_WINDOW_TOKEN);
            windowNode.writeSelfToParcel(parcel, pooledStringWriter, this.mTmpMatrix);
            this.mNumWrittenWindows++;
            ViewNode viewNode2 = windowNode.mRoot;
            this.mCurViewStackPos = 0;
            writeView(viewNode2, parcel, pooledStringWriter, 0);
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

        ParcelTransferReader(IBinder iBinder) {
            this.mChannel = iBinder;
        }

        void go() {
            fetchData();
            AssistStructure.this.mFlags = this.mCurParcel.readInt();
            AssistStructure.this.mAutofillFlags = this.mCurParcel.readInt();
            AssistStructure.this.mAcquisitionStartTime = this.mCurParcel.readLong();
            AssistStructure.this.mAcquisitionEndTime = this.mCurParcel.readLong();
            int readInt = this.mCurParcel.readInt();
            if (readInt > 0) {
                this.mStringReader = new PooledStringReader(this.mCurParcel);
                for (int i = 0; i < readInt; i++) {
                    AssistStructure.this.mWindowNodes.add(new WindowNode(this));
                }
            }
            this.mCurParcel.recycle();
            this.mCurParcel = null;
        }

        Parcel readParcel(int i, int i2) {
            int readInt = this.mCurParcel.readInt();
            if (readInt != 0) {
                if (readInt != i) {
                    throw new BadParcelableException("Got token " + Integer.toHexString(readInt) + ", expected token " + Integer.toHexString(i));
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
            Parcel obtain = Parcel.obtain();
            try {
                obtain.writeInterfaceToken(AssistStructure.DESCRIPTOR);
                obtain.writeStrongBinder(this.mTransferToken);
                Parcel parcel = this.mCurParcel;
                if (parcel != null) {
                    parcel.recycle();
                }
                Parcel obtain2 = Parcel.obtain();
                this.mCurParcel = obtain2;
                try {
                    this.mChannel.transact(2, obtain, obtain2, 0);
                    obtain.recycle();
                    this.mNumReadViews = 0;
                    this.mNumReadWindows = 0;
                } catch (RemoteException e) {
                    Log.w(AssistStructure.TAG, "Failure reading AssistStructure data", e);
                    throw new IllegalStateException("Failure reading AssistStructure data: " + e);
                }
            } catch (Throwable th) {
                obtain.recycle();
                throw th;
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
            return this.mTextBackgroundColor == 1 && this.mTextSelectionStart == 0 && this.mTextSelectionEnd == 0 && this.mLineCharOffsets == null && this.mLineBaselines == null && this.mHint == null;
        }

        ViewNodeText(Parcel parcel, boolean z) {
            this.mTextColor = 1;
            this.mTextBackgroundColor = 1;
            this.mText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.mTextSize = parcel.readFloat();
            this.mTextStyle = parcel.readInt();
            this.mTextColor = parcel.readInt();
            if (z) {
                return;
            }
            this.mTextBackgroundColor = parcel.readInt();
            this.mTextSelectionStart = parcel.readInt();
            this.mTextSelectionEnd = parcel.readInt();
            this.mLineCharOffsets = parcel.createIntArray();
            this.mLineBaselines = parcel.createIntArray();
            this.mHint = parcel.readString();
        }

        void writeToParcel(Parcel parcel, boolean z, boolean z2) {
            TextUtils.writeToParcel(z2 ? this.mText : "", parcel, 0);
            parcel.writeFloat(this.mTextSize);
            parcel.writeInt(this.mTextStyle);
            parcel.writeInt(this.mTextColor);
            if (z) {
                return;
            }
            parcel.writeInt(this.mTextBackgroundColor);
            parcel.writeInt(this.mTextSelectionStart);
            parcel.writeInt(this.mTextSelectionEnd);
            parcel.writeIntArray(this.mLineCharOffsets);
            parcel.writeIntArray(this.mLineBaselines);
            parcel.writeString(this.mHint);
        }
    }

    public static class WindowNode {
        final int mDisplayId;
        final int mHeight;
        final ViewNode mRoot;
        final CharSequence mTitle;
        final int mWidth;
        final int mX;
        final int mY;

        WindowNode(AssistStructure assistStructure, ViewRootImpl viewRootImpl, boolean z, int i) {
            View view = viewRootImpl.getView();
            Rect rect = new Rect();
            view.getBoundsOnScreen(rect);
            this.mX = rect.left - view.getLeft();
            this.mY = rect.top - view.getTop();
            this.mWidth = rect.width();
            this.mHeight = rect.height();
            this.mTitle = viewRootImpl.getTitle();
            this.mDisplayId = viewRootImpl.getDisplayId();
            ViewNode viewNode = new ViewNode();
            this.mRoot = viewNode;
            ViewNodeBuilder viewNodeBuilder = new ViewNodeBuilder(assistStructure, viewNode, false);
            if ((viewRootImpl.getWindowFlags() & 8192) != 0) {
                if (z) {
                    view.onProvideAutofillStructure(viewNodeBuilder, resolveViewAutofillFlags(view.getContext(), i));
                } else {
                    view.onProvideStructure(viewNodeBuilder);
                    viewNodeBuilder.setAssistBlocked(true);
                    return;
                }
            }
            if (z) {
                view.dispatchProvideAutofillStructure(viewNodeBuilder, resolveViewAutofillFlags(view.getContext(), i));
            } else {
                view.dispatchProvideStructure(viewNodeBuilder);
            }
        }

        WindowNode(ParcelTransferReader parcelTransferReader) {
            Parcel readParcel = parcelTransferReader.readParcel(AssistStructure.VALIDATE_WINDOW_TOKEN, 0);
            parcelTransferReader.mNumReadWindows++;
            this.mX = readParcel.readInt();
            this.mY = readParcel.readInt();
            this.mWidth = readParcel.readInt();
            this.mHeight = readParcel.readInt();
            this.mTitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(readParcel);
            this.mDisplayId = readParcel.readInt();
            this.mRoot = new ViewNode(parcelTransferReader, 0);
        }

        int resolveViewAutofillFlags(Context context, int i) {
            return ((i & 1) == 0 && !context.isAutofillCompatibilityEnabled() && (i & 512) == 0) ? 0 : 1;
        }

        void writeSelfToParcel(Parcel parcel, PooledStringWriter pooledStringWriter, float[] fArr) {
            parcel.writeInt(this.mX);
            parcel.writeInt(this.mY);
            parcel.writeInt(this.mWidth);
            parcel.writeInt(this.mHeight);
            TextUtils.writeToParcel(this.mTitle, parcel, 0);
            parcel.writeInt(this.mDisplayId);
        }

        public int getLeft() {
            return this.mX;
        }

        public int getTop() {
            return this.mY;
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
        OutcomeReceiver<GetCredentialResponse, GetCredentialException> mGetCredentialCallback;
        GetCredentialRequest mGetCredentialRequest;
        ResultReceiver mGetCredentialResultReceiver;
        int mHeight;
        String mHintIdEntry;
        ViewStructure.HtmlInfo mHtmlInfo;
        String mIdEntry;
        String mIdPackage;
        String mIdType;
        int mImportantForAutofill;
        int mInputType;
        boolean mIsCredential;
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
        int mX;
        int mY;
        int mId = -1;
        int mAutofillType = 0;
        int mMinEms = -1;
        int mMaxEms = -1;
        int mMaxLength = -1;
        float mAlpha = 1.0f;

        @SystemApi
        public ViewNode() {
        }

        ViewNode(Parcel parcel) {
            initializeFromParcelWithoutChildren(parcel, null, null);
        }

        ViewNode(ParcelTransferReader parcelTransferReader, int i) {
            Parcel readParcel = parcelTransferReader.readParcel(AssistStructure.VALIDATE_VIEW_TOKEN, i);
            parcelTransferReader.mNumReadViews++;
            initializeFromParcelWithoutChildren(readParcel, (PooledStringReader) Objects.requireNonNull(parcelTransferReader.mStringReader), (float[]) Objects.requireNonNull(parcelTransferReader.mTmpMatrix));
            if ((this.mFlags & 1048576) != 0) {
                int readInt = readParcel.readInt();
                this.mChildren = new ViewNode[readInt];
                for (int i2 = 0; i2 < readInt; i2++) {
                    this.mChildren[i2] = new ViewNode(parcelTransferReader, i + 1);
                }
            }
        }

        private static void writeString(Parcel parcel, PooledStringWriter pooledStringWriter, String str) {
            if (pooledStringWriter != null) {
                pooledStringWriter.writeString(str);
            } else {
                parcel.writeString(str);
            }
        }

        private static String readString(Parcel parcel, PooledStringReader pooledStringReader) {
            if (pooledStringReader != null) {
                return pooledStringReader.readString();
            }
            return parcel.readString();
        }

        void initializeFromParcelWithoutChildren(Parcel parcel, PooledStringReader pooledStringReader, float[] fArr) {
            this.mClassName = readString(parcel, pooledStringReader);
            int readInt = parcel.readInt();
            this.mFlags = readInt;
            int readInt2 = parcel.readInt();
            this.mAutofillFlags = readInt2;
            if ((2097152 & readInt) != 0) {
                int readInt3 = parcel.readInt();
                this.mId = readInt3;
                if (readInt3 != -1) {
                    String readString = readString(parcel, pooledStringReader);
                    this.mIdEntry = readString;
                    if (readString != null) {
                        this.mIdType = readString(parcel, pooledStringReader);
                        this.mIdPackage = readString(parcel, pooledStringReader);
                    }
                }
            }
            if (readInt2 != 0) {
                this.mSanitized = parcel.readInt() == 1;
                this.mIsCredential = parcel.readInt() == 1;
                this.mImportantForAutofill = parcel.readInt();
                if ((readInt2 & 1) != 0) {
                    int readInt4 = parcel.readInt();
                    if ((readInt2 & 2) != 0) {
                        this.mAutofillId = new AutofillId(readInt4, parcel.readInt());
                    } else {
                        this.mAutofillId = new AutofillId(readInt4);
                    }
                    if ((readInt2 & 2048) != 0) {
                        this.mAutofillId.setSessionId(parcel.readInt());
                    }
                }
                if ((readInt2 & 8) != 0) {
                    this.mAutofillType = parcel.readInt();
                }
                if ((readInt2 & 16) != 0) {
                    this.mAutofillHints = parcel.readStringArray();
                }
                if ((readInt2 & 4) != 0) {
                    this.mAutofillValue = (AutofillValue) parcel.readParcelable(null, AutofillValue.class);
                }
                if ((readInt2 & 32) != 0) {
                    this.mAutofillOptions = parcel.readCharSequenceArray();
                }
                if ((readInt2 & 64) != 0) {
                    this.mHtmlInfo = (ViewStructure.HtmlInfo) parcel.readParcelable(null, ViewStructure.HtmlInfo.class);
                }
                if ((readInt2 & 256) != 0) {
                    this.mMinEms = parcel.readInt();
                }
                if ((readInt2 & 512) != 0) {
                    this.mMaxEms = parcel.readInt();
                }
                if ((readInt2 & 1024) != 0) {
                    this.mMaxLength = parcel.readInt();
                }
                if ((readInt2 & 128) != 0) {
                    this.mTextIdEntry = readString(parcel, pooledStringReader);
                }
                if ((readInt2 & 4096) != 0) {
                    this.mHintIdEntry = readString(parcel, pooledStringReader);
                }
            }
            if ((67108864 & readInt) != 0) {
                this.mX = parcel.readInt();
                this.mY = parcel.readInt();
                this.mWidth = parcel.readInt();
                this.mHeight = parcel.readInt();
            } else {
                int readInt5 = parcel.readInt();
                this.mX = readInt5 & 32767;
                this.mY = (readInt5 >> 16) & 32767;
                int readInt6 = parcel.readInt();
                this.mWidth = readInt6 & 32767;
                this.mHeight = (readInt6 >> 16) & 32767;
            }
            if ((134217728 & readInt) != 0) {
                this.mScrollX = parcel.readInt();
                this.mScrollY = parcel.readInt();
            }
            if ((1073741824 & readInt) != 0) {
                this.mMatrix = new Matrix();
                if (fArr == null) {
                    fArr = new float[9];
                }
                parcel.readFloatArray(fArr);
                this.mMatrix.setValues(fArr);
            }
            if ((268435456 & readInt) != 0) {
                this.mElevation = parcel.readFloat();
            }
            if ((536870912 & readInt) != 0) {
                this.mAlpha = parcel.readFloat();
            }
            if ((33554432 & readInt) != 0) {
                this.mContentDescription = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            }
            if ((16777216 & readInt) != 0) {
                this.mText = new ViewNodeText(parcel, (8388608 & readInt) == 0);
            }
            if ((262144 & readInt) != 0) {
                this.mInputType = parcel.readInt();
            }
            if ((131072 & readInt) != 0) {
                this.mWebScheme = parcel.readString();
            }
            if ((524288 & readInt) != 0) {
                this.mWebDomain = parcel.readString();
            }
            if ((65536 & readInt) != 0) {
                this.mLocaleList = (LocaleList) parcel.readParcelable(null, LocaleList.class);
            }
            if ((Integer.MIN_VALUE & readInt) != 0) {
                this.mReceiveContentMimeTypes = parcel.readStringArray();
            }
            if ((4194304 & readInt) != 0) {
                this.mExtras = parcel.readBundle();
            }
            this.mGetCredentialRequest = (GetCredentialRequest) parcel.readTypedObject(GetCredentialRequest.CREATOR);
            this.mGetCredentialResultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
        }

        /* JADX WARN: Code restructure failed: missing block: B:15:0x0037, code lost:
        
            if ((((r24.mWidth & (-32768)) != 0) | ((r24.mHeight & (-32768)) != 0)) != false) goto L19;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        int writeSelfToParcel(android.os.Parcel r25, android.os.PooledStringWriter r26, boolean r27, float[] r28, boolean r29) {
            /*
                Method dump skipped, instructions count: 707
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.app.assist.AssistStructure.ViewNode.writeSelfToParcel(android.os.Parcel, android.os.PooledStringWriter, boolean, float[], boolean):int");
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

        public void setAutofillOverlay(AutofillOverlay autofillOverlay) {
            this.mAutofillOverlay = autofillOverlay;
        }

        public CharSequence[] getAutofillOptions() {
            return this.mAutofillOptions;
        }

        public boolean isCredential() {
            return this.mIsCredential;
        }

        public GetCredentialRequest getPendingCredentialRequest() {
            return this.mGetCredentialRequest;
        }

        public ResultReceiver getPendingCredentialCallback() {
            return this.mGetCredentialResultReceiver;
        }

        public int getInputType() {
            return this.mInputType;
        }

        public boolean isSanitized() {
            return this.mSanitized;
        }

        public void updateAutofillValue(AutofillValue autofillValue) {
            this.mAutofillValue = autofillValue;
            if (autofillValue.isText()) {
                if (this.mText == null) {
                    this.mText = new ViewNodeText();
                }
                this.mText.mText = autofillValue.getTextValue();
            }
        }

        public int getLeft() {
            return this.mX;
        }

        public int getTop() {
            return this.mY;
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

        public void setWebDomain(String str) {
            if (str == null) {
                return;
            }
            Uri parse = Uri.parse(str);
            if (parse == null) {
                Log.w(AssistStructure.TAG, "Failed to parse web domain");
                return;
            }
            String scheme = parse.getScheme();
            this.mWebScheme = scheme;
            if (scheme == null) {
                parse = Uri.parse("http://" + str);
            }
            this.mWebDomain = parse.getHost();
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

        public ViewNode getChildAt(int i) {
            return this.mChildren[i];
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
        public static final Parcelable.Creator<ViewNodeParcelable> CREATOR = new Parcelable.Creator<ViewNodeParcelable>() { // from class: android.app.assist.AssistStructure.ViewNodeParcelable.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ViewNodeParcelable createFromParcel(Parcel parcel) {
                return new ViewNodeParcelable(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public ViewNodeParcelable[] newArray(int i) {
                return new ViewNodeParcelable[i];
            }
        };
        private final ViewNode mViewNode;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public ViewNodeParcelable(ViewNode viewNode) {
            this.mViewNode = viewNode;
        }

        public ViewNodeParcelable(Parcel parcel) {
            this.mViewNode = new ViewNode(parcel);
        }

        public ViewNode getViewNode() {
            return this.mViewNode;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            this.mViewNode.writeSelfToParcel(parcel, null, false, null, false);
        }
    }

    public static class ViewNodeBuilder extends ViewStructure {
        final AssistStructure mAssist;
        final boolean mAsync;
        private Handler mHandler;
        final ViewNode mNode;

        public ViewNodeBuilder() {
            this.mAssist = new AssistStructure();
            this.mNode = new ViewNode();
            this.mAsync = false;
        }

        ViewNodeBuilder(AssistStructure assistStructure, ViewNode viewNode, boolean z) {
            this.mAssist = assistStructure;
            this.mNode = viewNode;
            this.mAsync = z;
        }

        public ViewNode getViewNode() {
            return this.mNode;
        }

        @Override // android.view.ViewStructure
        public void setId(int i, String str, String str2, String str3) {
            this.mNode.mId = i;
            this.mNode.mIdPackage = str;
            this.mNode.mIdType = str2;
            this.mNode.mIdEntry = str3;
        }

        @Override // android.view.ViewStructure
        public void setDimens(int i, int i2, int i3, int i4, int i5, int i6) {
            this.mNode.mX = i;
            this.mNode.mY = i2;
            this.mNode.mScrollX = i3;
            this.mNode.mScrollY = i4;
            this.mNode.mWidth = i5;
            this.mNode.mHeight = i6;
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
        public void setElevation(float f) {
            this.mNode.mElevation = f;
        }

        @Override // android.view.ViewStructure
        public void setAlpha(float f) {
            this.mNode.mAlpha = f;
        }

        @Override // android.view.ViewStructure
        public void setVisibility(int i) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (i & 12) | (viewNode.mFlags & (-13));
        }

        @Override // android.view.ViewStructure
        public void setAssistBlocked(boolean z) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (z ? 128 : 0) | (viewNode.mFlags & PackageManager.INSTALL_FAILED_PRE_APPROVAL_NOT_AVAILABLE);
        }

        @Override // android.view.ViewStructure
        public void setEnabled(boolean z) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (!z ? 1 : 0) | (viewNode.mFlags & (-2));
        }

        @Override // android.view.ViewStructure
        public void setClickable(boolean z) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (z ? 1024 : 0) | (viewNode.mFlags & (-1025));
        }

        @Override // android.view.ViewStructure
        public void setLongClickable(boolean z) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (z ? 2048 : 0) | (viewNode.mFlags & (-2049));
        }

        @Override // android.view.ViewStructure
        public void setContextClickable(boolean z) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (z ? 16384 : 0) | (viewNode.mFlags & (-16385));
        }

        @Override // android.view.ViewStructure
        public void setFocusable(boolean z) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (z ? 16 : 0) | (viewNode.mFlags & (-17));
        }

        @Override // android.view.ViewStructure
        public void setFocused(boolean z) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (z ? 32 : 0) | (viewNode.mFlags & (-33));
        }

        @Override // android.view.ViewStructure
        public void setAccessibilityFocused(boolean z) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (z ? 4096 : 0) | (viewNode.mFlags & (-4097));
        }

        @Override // android.view.ViewStructure
        public void setCheckable(boolean z) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (z ? 256 : 0) | (viewNode.mFlags & (-257));
        }

        @Override // android.view.ViewStructure
        public void setChecked(boolean z) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (z ? 512 : 0) | (viewNode.mFlags & (-513));
        }

        @Override // android.view.ViewStructure
        public void setSelected(boolean z) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (z ? 64 : 0) | (viewNode.mFlags & (-65));
        }

        @Override // android.view.ViewStructure
        public void setActivated(boolean z) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (z ? 8192 : 0) | (viewNode.mFlags & (-8193));
        }

        @Override // android.view.ViewStructure
        public void setOpaque(boolean z) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = (z ? 32768 : 0) | (viewNode.mFlags & (-32769));
        }

        @Override // android.view.ViewStructure
        public void setClassName(String str) {
            this.mNode.mClassName = str;
        }

        @Override // android.view.ViewStructure
        public void setContentDescription(CharSequence charSequence) {
            this.mNode.mContentDescription = charSequence;
        }

        private final ViewNodeText getNodeText() {
            if (this.mNode.mText != null) {
                return this.mNode.mText;
            }
            this.mNode.mText = new ViewNodeText();
            return this.mNode.mText;
        }

        @Override // android.view.ViewStructure
        public void setText(CharSequence charSequence) {
            ViewNodeText nodeText = getNodeText();
            nodeText.mText = TextUtils.trimToParcelableSize(stripAllSpansFromText(charSequence));
            nodeText.mTextSelectionEnd = -1;
            nodeText.mTextSelectionStart = -1;
        }

        @Override // android.view.ViewStructure
        public void setText(CharSequence charSequence, int i, int i2) {
            ViewNodeText nodeText = getNodeText();
            nodeText.mText = stripAllSpansFromText(charSequence);
            nodeText.mTextSelectionStart = i;
            nodeText.mTextSelectionEnd = i2;
        }

        @Override // android.view.ViewStructure
        public void setTextStyle(float f, int i, int i2, int i3) {
            ViewNodeText nodeText = getNodeText();
            nodeText.mTextColor = i;
            nodeText.mTextBackgroundColor = i2;
            nodeText.mTextSize = f;
            nodeText.mTextStyle = i3;
        }

        @Override // android.view.ViewStructure
        public void setTextLines(int[] iArr, int[] iArr2) {
            ViewNodeText nodeText = getNodeText();
            nodeText.mLineCharOffsets = iArr;
            nodeText.mLineBaselines = iArr2;
        }

        @Override // android.view.ViewStructure
        public void setTextIdEntry(String str) {
            this.mNode.mTextIdEntry = (String) Objects.requireNonNull(str);
        }

        @Override // android.view.ViewStructure
        public void setHint(CharSequence charSequence) {
            getNodeText().mHint = charSequence != null ? charSequence.toString() : null;
        }

        @Override // android.view.ViewStructure
        public void setHintIdEntry(String str) {
            this.mNode.mHintIdEntry = (String) Objects.requireNonNull(str);
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
        public void setChildCount(int i) {
            this.mNode.mChildren = new ViewNode[i];
        }

        @Override // android.view.ViewStructure
        public int addChildCount(int i) {
            if (this.mNode.mChildren == null) {
                setChildCount(i);
                return 0;
            }
            int length = this.mNode.mChildren.length;
            ViewNode[] viewNodeArr = new ViewNode[i + length];
            System.arraycopy(this.mNode.mChildren, 0, viewNodeArr, 0, length);
            this.mNode.mChildren = viewNodeArr;
            return length;
        }

        @Override // android.view.ViewStructure
        public int getChildCount() {
            if (this.mNode.mChildren != null) {
                return this.mNode.mChildren.length;
            }
            return 0;
        }

        @Override // android.view.ViewStructure
        public ViewStructure newChild(int i) {
            ViewNode viewNode = new ViewNode();
            this.mNode.mChildren[i] = viewNode;
            return new ViewNodeBuilder(this.mAssist, viewNode, false);
        }

        @Override // android.view.ViewStructure
        public ViewStructure asyncNewChild(int i) {
            ViewNodeBuilder viewNodeBuilder;
            synchronized (this.mAssist) {
                ViewNode viewNode = new ViewNode();
                this.mNode.mChildren[i] = viewNode;
                viewNodeBuilder = new ViewNodeBuilder(this.mAssist, viewNode, true);
                this.mAssist.mPendingAsyncChildren.add(viewNodeBuilder);
            }
            return viewNodeBuilder;
        }

        @Override // android.view.ViewStructure
        public GetCredentialRequest getPendingCredentialRequest() {
            return this.mNode.mGetCredentialRequest;
        }

        @Override // android.view.ViewStructure
        public OutcomeReceiver<GetCredentialResponse, GetCredentialException> getPendingCredentialCallback() {
            return this.mNode.mGetCredentialCallback;
        }

        @Override // android.view.ViewStructure
        public void asyncCommit() {
            synchronized (this.mAssist) {
                if (!this.mAsync) {
                    throw new IllegalStateException("Child " + this + " was not created with ViewStructure.asyncNewChild");
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
        public void setAutofillId(AutofillId autofillId) {
            this.mNode.mAutofillId = autofillId;
        }

        @Override // android.view.ViewStructure
        public void setAutofillId(AutofillId autofillId, int i) {
            this.mNode.mAutofillId = new AutofillId(autofillId, i);
        }

        @Override // android.view.ViewStructure
        public AutofillId getAutofillId() {
            return this.mNode.mAutofillId;
        }

        @Override // android.view.ViewStructure
        public void setAutofillType(int i) {
            this.mNode.mAutofillType = i;
        }

        @Override // android.view.ViewStructure
        public void setAutofillHints(String[] strArr) {
            this.mNode.mAutofillHints = strArr;
        }

        @Override // android.view.ViewStructure
        public void setAutofillValue(AutofillValue autofillValue) {
            this.mNode.mAutofillValue = autofillValue;
        }

        @Override // android.view.ViewStructure
        public void setAutofillOptions(CharSequence[] charSequenceArr) {
            this.mNode.mAutofillOptions = charSequenceArr;
        }

        @Override // android.view.ViewStructure
        public void setImportantForAutofill(int i) {
            this.mNode.mImportantForAutofill = i;
        }

        @Override // android.view.ViewStructure
        public void setIsCredential(boolean z) {
            this.mNode.mIsCredential = z;
        }

        @Override // android.view.ViewStructure
        public void setPendingCredentialRequest(GetCredentialRequest getCredentialRequest, OutcomeReceiver<GetCredentialResponse, GetCredentialException> outcomeReceiver) {
            this.mNode.mGetCredentialRequest = getCredentialRequest;
            this.mNode.mGetCredentialCallback = outcomeReceiver;
            for (CredentialOption credentialOption : getCredentialRequest.getCredentialOptions()) {
                ArrayList<? extends Parcelable> parcelableArrayList = credentialOption.getCandidateQueryData().getParcelableArrayList(CredentialProviderService.EXTRA_AUTOFILL_ID, AutofillId.class);
                if (parcelableArrayList == null) {
                    parcelableArrayList = new ArrayList<>();
                }
                if (!parcelableArrayList.contains(getAutofillId())) {
                    parcelableArrayList.add(getAutofillId());
                }
                credentialOption.getCandidateQueryData().putParcelableArrayList(CredentialProviderService.EXTRA_AUTOFILL_ID, parcelableArrayList);
            }
            setUpResultReceiver(outcomeReceiver);
        }

        private void setUpResultReceiver(final OutcomeReceiver<GetCredentialResponse, GetCredentialException> outcomeReceiver) {
            if (this.mHandler == null) {
                this.mHandler = new Handler(Looper.getMainLooper(), null, true);
            }
            this.mNode.mGetCredentialResultReceiver = toIpcFriendlyResultReceiver(new ResultReceiver(this, this.mHandler) { // from class: android.app.assist.AssistStructure.ViewNodeBuilder.1
                @Override // android.os.ResultReceiver
                protected void onReceiveResult(int i, Bundle bundle) {
                    if (i == 0) {
                        Slog.d(AssistStructure.TAG, "onReceiveResult from Credential Manager");
                        outcomeReceiver.onResult((GetCredentialResponse) bundle.getParcelable(CredentialProviderService.EXTRA_GET_CREDENTIAL_RESPONSE, GetCredentialResponse.class));
                        return;
                    }
                    if (i == -1) {
                        String[] stringArray = bundle.getStringArray(CredentialProviderService.EXTRA_GET_CREDENTIAL_EXCEPTION);
                        if (stringArray == null || stringArray.length < 2) {
                            return;
                        }
                        Slog.w(AssistStructure.TAG, "Credman bottom sheet from pinned entry failed with: + " + stringArray[0] + " , " + stringArray[1]);
                        outcomeReceiver.onError(new GetCredentialException(stringArray[0], stringArray[1]));
                        return;
                    }
                    Slog.d(AssistStructure.TAG, "Unknown resultCode from credential manager bottom sheet: " + i);
                }
            });
        }

        private ResultReceiver toIpcFriendlyResultReceiver(ResultReceiver resultReceiver) {
            Parcel obtain = Parcel.obtain();
            resultReceiver.writeToParcel(obtain, 0);
            obtain.setDataPosition(0);
            ResultReceiver createFromParcel = ResultReceiver.CREATOR.createFromParcel(obtain);
            obtain.recycle();
            return createFromParcel;
        }

        @Override // android.view.ViewStructure
        public void setReceiveContentMimeTypes(String[] strArr) {
            this.mNode.mReceiveContentMimeTypes = strArr;
        }

        @Override // android.view.ViewStructure
        public void setInputType(int i) {
            this.mNode.mInputType = i;
        }

        @Override // android.view.ViewStructure
        public void setMinTextEms(int i) {
            this.mNode.mMinEms = i;
        }

        @Override // android.view.ViewStructure
        public void setMaxTextEms(int i) {
            this.mNode.mMaxEms = i;
        }

        @Override // android.view.ViewStructure
        public void setMaxTextLength(int i) {
            this.mNode.mMaxLength = i;
        }

        @Override // android.view.ViewStructure
        public void setDataIsSensitive(boolean z) {
            this.mNode.mSanitized = !z;
        }

        @Override // android.view.ViewStructure
        public void setWebDomain(String str) {
            this.mNode.setWebDomain(str);
        }

        @Override // android.view.ViewStructure
        public void setLocaleList(LocaleList localeList) {
            this.mNode.mLocaleList = localeList;
        }

        @Override // android.view.ViewStructure
        public ViewStructure.HtmlInfo.Builder newHtmlInfoBuilder(String str) {
            return new HtmlInfoNodeBuilder(str);
        }

        @Override // android.view.ViewStructure
        public void setHtmlInfo(ViewStructure.HtmlInfo htmlInfo) {
            this.mNode.mHtmlInfo = htmlInfo;
        }

        private CharSequence stripAllSpansFromText(CharSequence charSequence) {
            return charSequence instanceof Spanned ? charSequence.toString() : charSequence;
        }
    }

    private static final class HtmlInfoNode extends ViewStructure.HtmlInfo implements Parcelable {
        public static final Parcelable.Creator<HtmlInfoNode> CREATOR = new Parcelable.Creator<HtmlInfoNode>() { // from class: android.app.assist.AssistStructure.HtmlInfoNode.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public HtmlInfoNode createFromParcel(Parcel parcel) {
                HtmlInfoNodeBuilder htmlInfoNodeBuilder = new HtmlInfoNodeBuilder(parcel.readString());
                String[] readStringArray = parcel.readStringArray();
                String[] readStringArray2 = parcel.readStringArray();
                if (readStringArray != null && readStringArray2 != null) {
                    if (readStringArray.length != readStringArray2.length) {
                        Log.w(AssistStructure.TAG, "HtmlInfo attributes mismatch: names=" + readStringArray.length + ", values=" + readStringArray2.length);
                    } else {
                        for (int i = 0; i < readStringArray.length; i++) {
                            htmlInfoNodeBuilder.addAttribute(readStringArray[i], readStringArray2[i]);
                        }
                    }
                }
                return htmlInfoNodeBuilder.build();
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public HtmlInfoNode[] newArray(int i) {
                return new HtmlInfoNode[i];
            }
        };
        private ArrayList<Pair<String, String>> mAttributes;
        private final String[] mNames;
        private final String mTag;
        private final String[] mValues;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private HtmlInfoNode(HtmlInfoNodeBuilder htmlInfoNodeBuilder) {
            this.mTag = htmlInfoNodeBuilder.mTag;
            if (htmlInfoNodeBuilder.mNames == null) {
                this.mNames = null;
                this.mValues = null;
                return;
            }
            String[] strArr = new String[htmlInfoNodeBuilder.mNames.size()];
            this.mNames = strArr;
            String[] strArr2 = new String[htmlInfoNodeBuilder.mValues.size()];
            this.mValues = strArr2;
            htmlInfoNodeBuilder.mNames.toArray(strArr);
            htmlInfoNodeBuilder.mValues.toArray(strArr2);
        }

        @Override // android.view.ViewStructure.HtmlInfo
        public String getTag() {
            return this.mTag;
        }

        @Override // android.view.ViewStructure.HtmlInfo
        public List<Pair<String, String>> getAttributes() {
            if (this.mAttributes == null && this.mNames != null) {
                this.mAttributes = new ArrayList<>(this.mNames.length);
                for (int i = 0; i < this.mNames.length; i++) {
                    this.mAttributes.add(i, new Pair<>(this.mNames[i], this.mValues[i]));
                }
            }
            return this.mAttributes;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.mTag);
            parcel.writeStringArray(this.mNames);
            parcel.writeStringArray(this.mValues);
        }
    }

    private static final class HtmlInfoNodeBuilder extends ViewStructure.HtmlInfo.Builder {
        private ArrayList<String> mNames;
        private final String mTag;
        private ArrayList<String> mValues;

        HtmlInfoNodeBuilder(String str) {
            this.mTag = str;
        }

        @Override // android.view.ViewStructure.HtmlInfo.Builder
        public ViewStructure.HtmlInfo.Builder addAttribute(String str, String str2) {
            if (this.mNames == null) {
                this.mNames = new ArrayList<>();
                this.mValues = new ArrayList<>();
            }
            this.mNames.add(str);
            this.mValues.add(str2);
            return this;
        }

        @Override // android.view.ViewStructure.HtmlInfo.Builder
        public HtmlInfoNode build() {
            return new HtmlInfoNode(this);
        }
    }

    public AssistStructure(Activity activity, boolean z, int i) {
        this.mWindowNodes = new ArrayList<>();
        this.mPendingAsyncChildren = new ArrayList<>();
        this.mTmpRect = new Rect();
        this.mSanitizeOnWrite = false;
        this.mHaveData = true;
        this.mFlags = i;
        ArrayList<ViewRootImpl> rootViews = WindowManagerGlobal.getInstance().getRootViews(activity.getActivityToken());
        for (int i2 = 0; i2 < rootViews.size(); i2++) {
            ViewRootImpl viewRootImpl = rootViews.get(i2);
            if (viewRootImpl.getView() == null) {
                Log.w(TAG, "Skipping window with dettached view: " + ((Object) viewRootImpl.getTitle()));
            } else {
                this.mWindowNodes.add(new WindowNode(this, viewRootImpl, z, i));
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

    public AssistStructure(Parcel parcel) {
        this.mWindowNodes = new ArrayList<>();
        this.mPendingAsyncChildren = new ArrayList<>();
        this.mTmpRect = new Rect();
        this.mSanitizeOnWrite = false;
        this.mTaskId = parcel.readInt();
        this.mActivityComponent = ComponentName.readFromParcel(parcel);
        this.mIsHomeActivity = parcel.readInt() == 1;
        this.mReceiveChannel = parcel.readStrongBinder();
    }

    public void sanitizeForParceling(boolean z) {
        this.mSanitizeOnWrite = z;
    }

    public void dump(boolean z) {
        if (this.mActivityComponent == null) {
            Log.i(TAG, "dump(): calling ensureData() first");
            ensureData();
        }
        Log.i(TAG, "Task id: " + this.mTaskId);
        StringBuilder sb = new StringBuilder("Activity: ");
        ComponentName componentName = this.mActivityComponent;
        sb.append(componentName != null ? componentName.flattenToShortString() : null);
        Log.i(TAG, sb.toString());
        Log.i(TAG, "Sanitize on write: " + this.mSanitizeOnWrite);
        Log.i(TAG, "Flags: " + this.mFlags);
        int windowNodeCount = getWindowNodeCount();
        for (int i = 0; i < windowNodeCount; i++) {
            WindowNode windowNodeAt = getWindowNodeAt(i);
            Log.i(TAG, "Window #" + i + " [" + windowNodeAt.getLeft() + "," + windowNodeAt.getTop() + " " + windowNodeAt.getWidth() + "x" + windowNodeAt.getHeight() + "] " + ((Object) windowNodeAt.getTitle()));
            dump("  ", windowNodeAt.getRootViewNode(), z);
        }
    }

    void dump(String str, ViewNode viewNode, boolean z) {
        Log.i(TAG, str + "View [" + viewNode.getLeft() + "," + viewNode.getTop() + " " + viewNode.getWidth() + "x" + viewNode.getHeight() + "] " + viewNode.getClassName());
        int id = viewNode.getId();
        if (id != 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("  ID: #");
            sb.append(Integer.toHexString(id));
            String idEntry = viewNode.getIdEntry();
            if (idEntry != null) {
                String idType = viewNode.getIdType();
                String idPackage = viewNode.getIdPackage();
                sb.append(" ");
                sb.append(idPackage);
                sb.append(":");
                sb.append(idType);
                sb.append("/");
                sb.append(idEntry);
            }
            Log.i(TAG, sb.toString());
        }
        int scrollX = viewNode.getScrollX();
        int scrollY = viewNode.getScrollY();
        if (scrollX != 0 || scrollY != 0) {
            Log.i(TAG, str + "  Scroll: " + scrollX + "," + scrollY);
        }
        Matrix transformation = viewNode.getTransformation();
        if (transformation != null) {
            Log.i(TAG, str + "  Transformation: " + transformation);
        }
        float elevation = viewNode.getElevation();
        if (elevation != 0.0f) {
            Log.i(TAG, str + "  Elevation: " + elevation);
        }
        if (viewNode.getAlpha() != 0.0f) {
            Log.i(TAG, str + "  Alpha: " + elevation);
        }
        CharSequence contentDescription = viewNode.getContentDescription();
        if (contentDescription != null) {
            Log.i(TAG, str + "  Content description: " + ((Object) contentDescription));
        }
        CharSequence text = viewNode.getText();
        if (text != null) {
            Log.i(TAG, str + "  Text (sel " + viewNode.getTextSelectionStart() + NativeLibraryHelper.CLEAR_ABI_OVERRIDE + viewNode.getTextSelectionEnd() + "): " + ((viewNode.isSanitized() || z) ? text.toString() : "REDACTED[" + text.length() + " chars]"));
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append("  Text size: ");
            sb2.append(viewNode.getTextSize());
            sb2.append(" , style: #");
            sb2.append(viewNode.getTextStyle());
            Log.i(TAG, sb2.toString());
            Log.i(TAG, str + "  Text color fg: #" + Integer.toHexString(viewNode.getTextColor()) + ", bg: #" + Integer.toHexString(viewNode.getTextBackgroundColor()));
            StringBuilder sb3 = new StringBuilder();
            sb3.append(str);
            sb3.append("  Input type: ");
            sb3.append(getInputTypeString(viewNode.getInputType()));
            Log.i(TAG, sb3.toString());
            Log.i(TAG, str + "  Resource id: " + viewNode.getTextIdEntry());
        }
        String webDomain = viewNode.getWebDomain();
        if (webDomain != null) {
            Log.i(TAG, str + "  Web domain: " + webDomain);
        }
        ViewStructure.HtmlInfo htmlInfo = viewNode.getHtmlInfo();
        if (htmlInfo != null) {
            Log.i(TAG, str + "  HtmlInfo: tag=" + htmlInfo.getTag() + ", attr=" + htmlInfo.getAttributes());
        }
        LocaleList localeList = viewNode.getLocaleList();
        if (localeList != null) {
            Log.i(TAG, str + "  LocaleList: " + localeList);
        }
        String[] receiveContentMimeTypes = viewNode.getReceiveContentMimeTypes();
        if (receiveContentMimeTypes != null) {
            Log.i(TAG, str + "  MIME types: " + Arrays.toString(receiveContentMimeTypes));
        }
        String hint = viewNode.getHint();
        if (hint != null) {
            Log.i(TAG, str + "  Hint: " + hint);
            Log.i(TAG, str + "  Resource id: " + viewNode.getHintIdEntry());
        }
        Bundle extras = viewNode.getExtras();
        if (extras != null) {
            Log.i(TAG, str + "  Extras: " + extras);
        }
        if (viewNode.isAssistBlocked()) {
            Log.i(TAG, str + "  BLOCKED");
        }
        AutofillId autofillId = viewNode.getAutofillId();
        if (autofillId == null) {
            Log.i(TAG, str + " No autofill ID");
        } else {
            Log.i(TAG, str + "  Autofill info: id= " + autofillId + ", type=" + viewNode.getAutofillType() + ", options=" + Arrays.toString(viewNode.getAutofillOptions()) + ", hints=" + Arrays.toString(viewNode.getAutofillHints()) + ", value=" + viewNode.getAutofillValue() + ", sanitized=" + viewNode.isSanitized() + ", important=" + viewNode.getImportantForAutofill() + ", visibility=" + viewNode.getVisibility() + ", isCredential=" + viewNode.isCredential());
        }
        GetCredentialRequest pendingCredentialRequest = viewNode.getPendingCredentialRequest();
        StringBuilder sb4 = new StringBuilder();
        sb4.append(str);
        sb4.append("  Credential Manager info: hasCredentialManagerRequest=");
        sb4.append(pendingCredentialRequest != null);
        sb4.append(pendingCredentialRequest != null ? ", sizeOfOptions=" + pendingCredentialRequest.getCredentialOptions().size() : "");
        Log.i(TAG, sb4.toString());
        int childCount = viewNode.getChildCount();
        if (childCount > 0) {
            Log.i(TAG, str + "  Children:");
            String str2 = str + "    ";
            for (int i = 0; i < childCount; i++) {
                dump(str2, viewNode.getChildAt(i), z);
            }
        }
    }

    public void setTaskId(int i) {
        this.mTaskId = i;
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

    public WindowNode getWindowNodeAt(int i) {
        ensureData();
        return this.mWindowNodes.get(i);
    }

    public void ensureDataForAutofill() {
        if (this.mHaveData) {
            return;
        }
        this.mHaveData = true;
        Binder.allowBlocking(this.mReceiveChannel);
        try {
            new ParcelTransferReader(this.mReceiveChannel).go();
        } finally {
            Binder.defaultBlocking(this.mReceiveChannel);
        }
    }

    public void ensureData() {
        if (this.mHaveData) {
            return;
        }
        this.mHaveData = true;
        new ParcelTransferReader(this.mReceiveChannel).go();
    }

    boolean waitForReady() {
        boolean z;
        synchronized (this) {
            long uptimeMillis = SystemClock.uptimeMillis() + 5000;
            while (this.mPendingAsyncChildren.size() > 0) {
                long uptimeMillis2 = SystemClock.uptimeMillis();
                if (uptimeMillis2 >= uptimeMillis) {
                    break;
                }
                try {
                    wait(uptimeMillis - uptimeMillis2);
                } catch (InterruptedException unused) {
                }
            }
            if (this.mPendingAsyncChildren.size() > 0) {
                Log.w(TAG, "Skipping assist structure, waiting too long for async children (have " + this.mPendingAsyncChildren.size() + " remaining");
                z = true;
            } else {
                z = false;
            }
        }
        return !z;
    }

    public void clearSendChannel() {
        SendChannel sendChannel = this.mSendChannel;
        if (sendChannel != null) {
            sendChannel.mAssistStructure = null;
        }
    }

    @Override // android.os.Parcelable
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

    static {
        ArrayMap<Integer, String> arrayMap = new ArrayMap<>();
        INPUT_TYPE_VARIATIONS = arrayMap;
        arrayMap.put(32, "EmailSubject");
        arrayMap.put(112, "PostalAddress");
        arrayMap.put(96, "PersonName");
        arrayMap.put(128, "Password");
        arrayMap.put(144, "VisiblePassword");
        arrayMap.put(16, "URI");
        arrayMap.put(208, "WebEmailAddress");
        arrayMap.put(224, "WebPassword");
        arrayMap.put(80, "LongMessage");
        arrayMap.put(64, "ShortMessage");
        arrayMap.put(131072, "MultiLine");
        arrayMap.put(262144, "ImeMultiLine");
        arrayMap.put(176, "Filter");
    }

    private static String getInputTypeString(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append("(class=");
        sb.append(i & 15);
        sb.append(')');
        for (Integer num : INPUT_TYPE_VARIATIONS.keySet()) {
            int intValue = num.intValue();
            if ((intValue & i) == intValue) {
                sb.append('|');
                sb.append(INPUT_TYPE_VARIATIONS.get(num));
            }
        }
        return sb.toString();
    }
}
