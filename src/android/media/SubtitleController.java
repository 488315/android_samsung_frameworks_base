package android.media;

import android.content.Context;
import android.media.SubtitleTrack;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.accessibility.CaptioningManager;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

/* loaded from: classes2.dex */
public class SubtitleController {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int WHAT_HIDE = 2;
    private static final int WHAT_SELECT_DEFAULT_TRACK = 4;
    private static final int WHAT_SELECT_TRACK = 3;
    private static final int WHAT_SHOW = 1;
    private Anchor mAnchor;
    private CaptioningManager mCaptioningManager;
    private Handler mHandler;
    private Listener mListener;
    private SubtitleTrack mSelectedTrack;
    private MediaTimeProvider mTimeProvider;
    private final Handler.Callback mCallback = new Handler.Callback() { // from class: android.media.SubtitleController.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                SubtitleController.this.doShow();
                return true;
            }
            if (i == 2) {
                SubtitleController.this.doHide();
                return true;
            }
            if (i == 3) {
                SubtitleController.this.doSelectTrack((SubtitleTrack) message.obj);
                return true;
            }
            if (i != 4) {
                return false;
            }
            SubtitleController.this.doSelectDefaultTrack();
            return true;
        }
    };
    private CaptioningManager.CaptioningChangeListener mCaptioningChangeListener = new CaptioningManager.CaptioningChangeListener() { // from class: android.media.SubtitleController.2
        @Override // android.view.accessibility.CaptioningManager.CaptioningChangeListener
        public void onEnabledChanged(boolean z) {
            SubtitleController.this.selectDefaultTrack();
        }

        @Override // android.view.accessibility.CaptioningManager.CaptioningChangeListener
        public void onLocaleChanged(Locale locale) {
            SubtitleController.this.selectDefaultTrack();
        }
    };
    private boolean mTrackIsExplicit = false;
    private boolean mVisibilityIsExplicit = false;
    private Vector<Renderer> mRenderers = new Vector<>();
    private boolean mShowing = false;
    private Vector<SubtitleTrack> mTracks = new Vector<>();

    public interface Anchor {
        Looper getSubtitleLooper();

        void setSubtitleWidget(SubtitleTrack.RenderingWidget renderingWidget);
    }

    public interface Listener {
        void onSubtitleTrackSelected(SubtitleTrack subtitleTrack);
    }

    public static abstract class Renderer {
        public abstract SubtitleTrack createTrack(MediaFormat mediaFormat);

        public abstract boolean supports(MediaFormat mediaFormat);
    }

    private void checkAnchorLooper() {
    }

    public SubtitleController(Context context, MediaTimeProvider mediaTimeProvider, Listener listener) {
        this.mTimeProvider = mediaTimeProvider;
        this.mListener = listener;
        this.mCaptioningManager = (CaptioningManager) context.getSystemService(Context.CAPTIONING_SERVICE);
    }

    protected void finalize() throws Throwable {
        this.mCaptioningManager.removeCaptioningChangeListener(this.mCaptioningChangeListener);
        super.finalize();
    }

    public SubtitleTrack[] getTracks() {
        SubtitleTrack[] subtitleTrackArr;
        synchronized (this.mTracks) {
            subtitleTrackArr = new SubtitleTrack[this.mTracks.size()];
            this.mTracks.toArray(subtitleTrackArr);
        }
        return subtitleTrackArr;
    }

    public SubtitleTrack getSelectedTrack() {
        return this.mSelectedTrack;
    }

    private SubtitleTrack.RenderingWidget getRenderingWidget() {
        SubtitleTrack subtitleTrack = this.mSelectedTrack;
        if (subtitleTrack == null) {
            return null;
        }
        return subtitleTrack.getRenderingWidget();
    }

    public boolean selectTrack(SubtitleTrack subtitleTrack) {
        if (subtitleTrack != null && !this.mTracks.contains(subtitleTrack)) {
            return false;
        }
        processOnAnchor(this.mHandler.obtainMessage(3, subtitleTrack));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doSelectTrack(SubtitleTrack subtitleTrack) {
        this.mTrackIsExplicit = true;
        SubtitleTrack subtitleTrack2 = this.mSelectedTrack;
        if (subtitleTrack2 == subtitleTrack) {
            return;
        }
        if (subtitleTrack2 != null) {
            subtitleTrack2.hide();
            this.mSelectedTrack.setTimeProvider(null);
        }
        this.mSelectedTrack = subtitleTrack;
        Anchor anchor = this.mAnchor;
        if (anchor != null) {
            anchor.setSubtitleWidget(getRenderingWidget());
        }
        SubtitleTrack subtitleTrack3 = this.mSelectedTrack;
        if (subtitleTrack3 != null) {
            subtitleTrack3.setTimeProvider(this.mTimeProvider);
            this.mSelectedTrack.show();
        }
        Listener listener = this.mListener;
        if (listener != null) {
            listener.onSubtitleTrackSelected(subtitleTrack);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x007e, code lost:
    
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00a3, code lost:
    
        r4 = r6;
        r5 = r8;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.media.SubtitleTrack getDefaultTrack() {
        /*
            r15 = this;
            android.view.accessibility.CaptioningManager r0 = r15.mCaptioningManager
            java.util.Locale r0 = r0.getLocale()
            if (r0 != 0) goto Ld
            java.util.Locale r1 = java.util.Locale.getDefault()
            goto Le
        Ld:
            r1 = r0
        Le:
            android.view.accessibility.CaptioningManager r2 = r15.mCaptioningManager
            boolean r2 = r2.isEnabled()
            java.util.Vector<android.media.SubtitleTrack> r3 = r15.mTracks
            monitor-enter(r3)
            java.util.Vector<android.media.SubtitleTrack> r15 = r15.mTracks     // Catch: java.lang.Throwable -> La9
            java.util.Iterator r15 = r15.iterator()     // Catch: java.lang.Throwable -> La9
            r4 = 0
            r5 = -1
        L1f:
            boolean r6 = r15.hasNext()     // Catch: java.lang.Throwable -> La9
            if (r6 == 0) goto La7
            java.lang.Object r6 = r15.next()     // Catch: java.lang.Throwable -> La9
            android.media.SubtitleTrack r6 = (android.media.SubtitleTrack) r6     // Catch: java.lang.Throwable -> La9
            android.media.MediaFormat r7 = r6.getFormat()     // Catch: java.lang.Throwable -> La9
            java.lang.String r8 = "language"
            java.lang.String r8 = r7.getString(r8)     // Catch: java.lang.Throwable -> La9
            java.lang.String r9 = "is-forced-subtitle"
            r10 = 0
            int r9 = r7.getInteger(r9, r10)     // Catch: java.lang.Throwable -> La9
            r11 = 1
            if (r9 == 0) goto L41
            r9 = r11
            goto L42
        L41:
            r9 = r10
        L42:
            java.lang.String r12 = "is-autoselect"
            int r12 = r7.getInteger(r12, r11)     // Catch: java.lang.Throwable -> La9
            if (r12 == 0) goto L4c
            r12 = r11
            goto L4d
        L4c:
            r12 = r10
        L4d:
            java.lang.String r13 = "is-default"
            int r7 = r7.getInteger(r13, r10)     // Catch: java.lang.Throwable -> La9
            if (r7 == 0) goto L57
            r7 = r11
            goto L58
        L57:
            r7 = r10
        L58:
            if (r1 == 0) goto L7c
            java.lang.String r13 = r1.getLanguage()     // Catch: java.lang.Throwable -> La9
            java.lang.String r14 = ""
            boolean r13 = r13.equals(r14)     // Catch: java.lang.Throwable -> La9
            if (r13 != 0) goto L7c
            java.lang.String r13 = r1.getISO3Language()     // Catch: java.lang.Throwable -> La9
            boolean r13 = r13.equals(r8)     // Catch: java.lang.Throwable -> La9
            if (r13 != 0) goto L7c
            java.lang.String r13 = r1.getLanguage()     // Catch: java.lang.Throwable -> La9
            boolean r8 = r13.equals(r8)     // Catch: java.lang.Throwable -> La9
            if (r8 == 0) goto L7b
            goto L7c
        L7b:
            r11 = r10
        L7c:
            if (r9 == 0) goto L80
            r8 = r10
            goto L82
        L80:
            r8 = 8
        L82:
            if (r0 != 0) goto L88
            if (r7 == 0) goto L88
            r13 = 4
            goto L89
        L88:
            r13 = r10
        L89:
            int r8 = r8 + r13
            if (r12 == 0) goto L8d
            goto L8e
        L8d:
            r10 = 2
        L8e:
            int r8 = r8 + r10
            int r8 = r8 + r11
            if (r2 != 0) goto L95
            if (r9 != 0) goto L95
            goto L1f
        L95:
            if (r0 != 0) goto L99
            if (r7 != 0) goto La1
        L99:
            if (r11 == 0) goto L1f
            if (r12 != 0) goto La1
            if (r9 != 0) goto La1
            if (r0 == 0) goto L1f
        La1:
            if (r8 <= r5) goto L1f
            r4 = r6
            r5 = r8
            goto L1f
        La7:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> La9
            return r4
        La9:
            r15 = move-exception
            monitor-exit(r3)     // Catch: java.lang.Throwable -> La9
            throw r15
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.SubtitleController.getDefaultTrack():android.media.SubtitleTrack");
    }

    public void selectDefaultTrack() {
        processOnAnchor(this.mHandler.obtainMessage(4));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doSelectDefaultTrack() {
        SubtitleTrack subtitleTrack;
        if (this.mTrackIsExplicit) {
            if (this.mVisibilityIsExplicit) {
                return;
            }
            if (this.mCaptioningManager.isEnabled() || ((subtitleTrack = this.mSelectedTrack) != null && subtitleTrack.getFormat().getInteger(MediaFormat.KEY_IS_FORCED_SUBTITLE, 0) != 0)) {
                show();
            } else {
                SubtitleTrack subtitleTrack2 = this.mSelectedTrack;
                if (subtitleTrack2 != null && subtitleTrack2.getTrackType() == 4) {
                    hide();
                }
            }
            this.mVisibilityIsExplicit = false;
            return;
        }
        SubtitleTrack defaultTrack = getDefaultTrack();
        if (defaultTrack != null) {
            selectTrack(defaultTrack);
            this.mTrackIsExplicit = false;
            if (this.mVisibilityIsExplicit) {
                return;
            }
            show();
            this.mVisibilityIsExplicit = false;
        }
    }

    public void reset() {
        checkAnchorLooper();
        hide();
        selectTrack(null);
        this.mTracks.clear();
        this.mTrackIsExplicit = false;
        this.mVisibilityIsExplicit = false;
        this.mCaptioningManager.removeCaptioningChangeListener(this.mCaptioningChangeListener);
    }

    public void resetTracks() {
        checkAnchorLooper();
        hide();
        selectTrack(null);
        this.mTracks.clear();
        this.mTrackIsExplicit = false;
        this.mVisibilityIsExplicit = false;
    }

    public SubtitleTrack addTrack(MediaFormat mediaFormat) {
        SubtitleTrack createTrack;
        synchronized (this.mRenderers) {
            Iterator<Renderer> it = this.mRenderers.iterator();
            while (it.hasNext()) {
                Renderer next = it.next();
                if (next.supports(mediaFormat) && (createTrack = next.createTrack(mediaFormat)) != null) {
                    synchronized (this.mTracks) {
                        if (this.mTracks.size() == 0) {
                            this.mCaptioningManager.addCaptioningChangeListener(this.mCaptioningChangeListener);
                        }
                        this.mTracks.add(createTrack);
                    }
                    return createTrack;
                }
            }
            return null;
        }
    }

    public void show() {
        processOnAnchor(this.mHandler.obtainMessage(1));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doShow() {
        this.mShowing = true;
        this.mVisibilityIsExplicit = true;
        SubtitleTrack subtitleTrack = this.mSelectedTrack;
        if (subtitleTrack != null) {
            subtitleTrack.show();
        }
    }

    public void hide() {
        processOnAnchor(this.mHandler.obtainMessage(2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doHide() {
        this.mVisibilityIsExplicit = true;
        SubtitleTrack subtitleTrack = this.mSelectedTrack;
        if (subtitleTrack != null) {
            subtitleTrack.hide();
        }
        this.mShowing = false;
    }

    public void registerRenderer(Renderer renderer) {
        synchronized (this.mRenderers) {
            if (!this.mRenderers.contains(renderer)) {
                this.mRenderers.add(renderer);
            }
        }
    }

    public boolean hasRendererFor(MediaFormat mediaFormat) {
        synchronized (this.mRenderers) {
            Iterator<Renderer> it = this.mRenderers.iterator();
            while (it.hasNext()) {
                if (it.next().supports(mediaFormat)) {
                    return true;
                }
            }
            return false;
        }
    }

    public void setAnchor(Anchor anchor) {
        Anchor anchor2 = this.mAnchor;
        if (anchor2 == anchor) {
            return;
        }
        if (anchor2 != null) {
            checkAnchorLooper();
            this.mAnchor.setSubtitleWidget(null);
        }
        this.mAnchor = anchor;
        this.mHandler = null;
        if (anchor != null) {
            this.mHandler = new Handler(this.mAnchor.getSubtitleLooper(), this.mCallback);
            checkAnchorLooper();
            this.mAnchor.setSubtitleWidget(getRenderingWidget());
        }
    }

    private void processOnAnchor(Message message) {
        if (Looper.myLooper() == this.mHandler.getLooper()) {
            this.mHandler.dispatchMessage(message);
        } else {
            this.mHandler.sendMessage(message);
        }
    }
}
