package android.hardware.vibrator;

import android.hardware.vibrator.IVibratorCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes2.dex */
public interface IVibrator extends IInterface {
    public static final int CAP_ALWAYS_ON_CONTROL = 64;
    public static final int CAP_AMPLITUDE_CONTROL = 4;
    public static final int CAP_COMPOSE_EFFECTS = 32;
    public static final int CAP_COMPOSE_PWLE_EFFECTS = 1024;
    public static final int CAP_COMPOSE_PWLE_EFFECTS_V2 = 4096;
    public static final int CAP_EXTERNAL_AMPLITUDE_CONTROL = 16;
    public static final int CAP_EXTERNAL_CONTROL = 8;
    public static final int CAP_FREQUENCY_CONTROL = 512;
    public static final int CAP_GET_Q_FACTOR = 256;
    public static final int CAP_GET_RESONANT_FREQUENCY = 128;
    public static final int CAP_ON_CALLBACK = 1;
    public static final int CAP_PERFORM_CALLBACK = 2;
    public static final int CAP_PERFORM_VENDOR_EFFECTS = 2048;
    public static final String DESCRIPTOR = "android$hardware$vibrator$IVibrator".replace('$', '.');
    public static final String HASH = "720a16b521507c378f14c516749ae178a60dfc44";
    public static final int VERSION = 3;

    void alwaysOnDisable(int i) throws RemoteException;

    void alwaysOnEnable(int i, int i2, byte b) throws RemoteException;

    void compose(CompositeEffect[] compositeEffectArr, IVibratorCallback iVibratorCallback) throws RemoteException;

    @Deprecated
    void composePwle(PrimitivePwle[] primitivePwleArr, IVibratorCallback iVibratorCallback) throws RemoteException;

    void composePwleV2(CompositePwleV2 compositePwleV2, IVibratorCallback iVibratorCallback) throws RemoteException;

    @Deprecated
    float[] getBandwidthAmplitudeMap() throws RemoteException;

    int getCapabilities() throws RemoteException;

    int getCompositionDelayMax() throws RemoteException;

    int getCompositionSizeMax() throws RemoteException;

    @Deprecated
    float getFrequencyMinimum() throws RemoteException;

    @Deprecated
    float getFrequencyResolution() throws RemoteException;

    List<FrequencyAccelerationMapEntry> getFrequencyToOutputAccelerationMap() throws RemoteException;

    String getInterfaceHash() throws RemoteException;

    int getInterfaceVersion() throws RemoteException;

    int getPrimitiveDuration(int i) throws RemoteException;

    @Deprecated
    int getPwleCompositionSizeMax() throws RemoteException;

    @Deprecated
    int getPwlePrimitiveDurationMax() throws RemoteException;

    int getPwleV2CompositionSizeMax() throws RemoteException;

    int getPwleV2PrimitiveDurationMaxMillis() throws RemoteException;

    int getPwleV2PrimitiveDurationMinMillis() throws RemoteException;

    float getQFactor() throws RemoteException;

    float getResonantFrequency() throws RemoteException;

    int[] getSupportedAlwaysOnEffects() throws RemoteException;

    @Deprecated
    int[] getSupportedBraking() throws RemoteException;

    int[] getSupportedEffects() throws RemoteException;

    int[] getSupportedPrimitives() throws RemoteException;

    void off() throws RemoteException;

    void on(int i, IVibratorCallback iVibratorCallback) throws RemoteException;

    int perform(int i, byte b, IVibratorCallback iVibratorCallback) throws RemoteException;

    void performVendorEffect(VendorEffect vendorEffect, IVibratorCallback iVibratorCallback) throws RemoteException;

    void setAmplitude(float f) throws RemoteException;

    void setExternalControl(boolean z) throws RemoteException;

    public static class Default implements IVibrator {
        @Override // android.hardware.vibrator.IVibrator
        public void alwaysOnDisable(int i) throws RemoteException {
        }

        @Override // android.hardware.vibrator.IVibrator
        public void alwaysOnEnable(int i, int i2, byte b) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.vibrator.IVibrator
        public void compose(CompositeEffect[] compositeEffectArr, IVibratorCallback iVibratorCallback) throws RemoteException {
        }

        @Override // android.hardware.vibrator.IVibrator
        public void composePwle(PrimitivePwle[] primitivePwleArr, IVibratorCallback iVibratorCallback) throws RemoteException {
        }

        @Override // android.hardware.vibrator.IVibrator
        public void composePwleV2(CompositePwleV2 compositePwleV2, IVibratorCallback iVibratorCallback) throws RemoteException {
        }

        @Override // android.hardware.vibrator.IVibrator
        public float[] getBandwidthAmplitudeMap() throws RemoteException {
            return null;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int getCapabilities() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int getCompositionDelayMax() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int getCompositionSizeMax() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public float getFrequencyMinimum() throws RemoteException {
            return 0.0f;
        }

        @Override // android.hardware.vibrator.IVibrator
        public float getFrequencyResolution() throws RemoteException {
            return 0.0f;
        }

        @Override // android.hardware.vibrator.IVibrator
        public List<FrequencyAccelerationMapEntry> getFrequencyToOutputAccelerationMap() throws RemoteException {
            return null;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int getInterfaceVersion() {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int getPrimitiveDuration(int i) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int getPwleCompositionSizeMax() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int getPwlePrimitiveDurationMax() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int getPwleV2CompositionSizeMax() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int getPwleV2PrimitiveDurationMaxMillis() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int getPwleV2PrimitiveDurationMinMillis() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public float getQFactor() throws RemoteException {
            return 0.0f;
        }

        @Override // android.hardware.vibrator.IVibrator
        public float getResonantFrequency() throws RemoteException {
            return 0.0f;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int[] getSupportedAlwaysOnEffects() throws RemoteException {
            return null;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int[] getSupportedBraking() throws RemoteException {
            return null;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int[] getSupportedEffects() throws RemoteException {
            return null;
        }

        @Override // android.hardware.vibrator.IVibrator
        public int[] getSupportedPrimitives() throws RemoteException {
            return null;
        }

        @Override // android.hardware.vibrator.IVibrator
        public void off() throws RemoteException {
        }

        @Override // android.hardware.vibrator.IVibrator
        public void on(int i, IVibratorCallback iVibratorCallback) throws RemoteException {
        }

        @Override // android.hardware.vibrator.IVibrator
        public int perform(int i, byte b, IVibratorCallback iVibratorCallback) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.vibrator.IVibrator
        public void performVendorEffect(VendorEffect vendorEffect, IVibratorCallback iVibratorCallback) throws RemoteException {
        }

        @Override // android.hardware.vibrator.IVibrator
        public void setAmplitude(float f) throws RemoteException {
        }

        @Override // android.hardware.vibrator.IVibrator
        public void setExternalControl(boolean z) throws RemoteException {
        }

        @Override // android.hardware.vibrator.IVibrator
        public String getInterfaceHash() {
            return "";
        }
    }

    public static abstract class Stub extends Binder implements IVibrator {
        static final int TRANSACTION_alwaysOnDisable = 15;
        static final int TRANSACTION_alwaysOnEnable = 14;
        static final int TRANSACTION_compose = 12;
        static final int TRANSACTION_composePwle = 24;
        static final int TRANSACTION_composePwleV2 = 30;
        static final int TRANSACTION_getBandwidthAmplitudeMap = 20;
        static final int TRANSACTION_getCapabilities = 1;
        static final int TRANSACTION_getCompositionDelayMax = 8;
        static final int TRANSACTION_getCompositionSizeMax = 9;
        static final int TRANSACTION_getFrequencyMinimum = 19;
        static final int TRANSACTION_getFrequencyResolution = 18;
        static final int TRANSACTION_getFrequencyToOutputAccelerationMap = 26;
        static final int TRANSACTION_getInterfaceHash = 16777214;
        static final int TRANSACTION_getInterfaceVersion = 16777215;
        static final int TRANSACTION_getPrimitiveDuration = 11;
        static final int TRANSACTION_getPwleCompositionSizeMax = 22;
        static final int TRANSACTION_getPwlePrimitiveDurationMax = 21;
        static final int TRANSACTION_getPwleV2CompositionSizeMax = 28;
        static final int TRANSACTION_getPwleV2PrimitiveDurationMaxMillis = 27;
        static final int TRANSACTION_getPwleV2PrimitiveDurationMinMillis = 29;
        static final int TRANSACTION_getQFactor = 17;
        static final int TRANSACTION_getResonantFrequency = 16;
        static final int TRANSACTION_getSupportedAlwaysOnEffects = 13;
        static final int TRANSACTION_getSupportedBraking = 23;
        static final int TRANSACTION_getSupportedEffects = 5;
        static final int TRANSACTION_getSupportedPrimitives = 10;
        static final int TRANSACTION_off = 2;
        static final int TRANSACTION_on = 3;
        static final int TRANSACTION_perform = 4;
        static final int TRANSACTION_performVendorEffect = 25;
        static final int TRANSACTION_setAmplitude = 6;
        static final int TRANSACTION_setExternalControl = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            markVintfStability();
            attachInterface(this, DESCRIPTOR);
        }

        public static IVibrator asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IVibrator)) {
                return (IVibrator) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str = DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(getInterfaceVersion());
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString(getInterfaceHash());
                return true;
            }
            switch (i) {
                case 1:
                    int capabilities = getCapabilities();
                    parcel2.writeNoException();
                    parcel2.writeInt(capabilities);
                    return true;
                case 2:
                    off();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    IVibratorCallback asInterface = IVibratorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    on(readInt, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    int readInt2 = parcel.readInt();
                    byte readByte = parcel.readByte();
                    IVibratorCallback asInterface2 = IVibratorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int perform = perform(readInt2, readByte, asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeInt(perform);
                    return true;
                case 5:
                    int[] supportedEffects = getSupportedEffects();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supportedEffects);
                    return true;
                case 6:
                    float readFloat = parcel.readFloat();
                    parcel.enforceNoDataAvail();
                    setAmplitude(readFloat);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setExternalControl(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int compositionDelayMax = getCompositionDelayMax();
                    parcel2.writeNoException();
                    parcel2.writeInt(compositionDelayMax);
                    return true;
                case 9:
                    int compositionSizeMax = getCompositionSizeMax();
                    parcel2.writeNoException();
                    parcel2.writeInt(compositionSizeMax);
                    return true;
                case 10:
                    int[] supportedPrimitives = getSupportedPrimitives();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supportedPrimitives);
                    return true;
                case 11:
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int primitiveDuration = getPrimitiveDuration(readInt3);
                    parcel2.writeNoException();
                    parcel2.writeInt(primitiveDuration);
                    return true;
                case 12:
                    CompositeEffect[] compositeEffectArr = (CompositeEffect[]) parcel.createTypedArray(CompositeEffect.CREATOR);
                    IVibratorCallback asInterface3 = IVibratorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    compose(compositeEffectArr, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int[] supportedAlwaysOnEffects = getSupportedAlwaysOnEffects();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supportedAlwaysOnEffects);
                    return true;
                case 14:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    byte readByte2 = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    alwaysOnEnable(readInt4, readInt5, readByte2);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    alwaysOnDisable(readInt6);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    float resonantFrequency = getResonantFrequency();
                    parcel2.writeNoException();
                    parcel2.writeFloat(resonantFrequency);
                    return true;
                case 17:
                    float qFactor = getQFactor();
                    parcel2.writeNoException();
                    parcel2.writeFloat(qFactor);
                    return true;
                case 18:
                    float frequencyResolution = getFrequencyResolution();
                    parcel2.writeNoException();
                    parcel2.writeFloat(frequencyResolution);
                    return true;
                case 19:
                    float frequencyMinimum = getFrequencyMinimum();
                    parcel2.writeNoException();
                    parcel2.writeFloat(frequencyMinimum);
                    return true;
                case 20:
                    float[] bandwidthAmplitudeMap = getBandwidthAmplitudeMap();
                    parcel2.writeNoException();
                    parcel2.writeFloatArray(bandwidthAmplitudeMap);
                    return true;
                case 21:
                    int pwlePrimitiveDurationMax = getPwlePrimitiveDurationMax();
                    parcel2.writeNoException();
                    parcel2.writeInt(pwlePrimitiveDurationMax);
                    return true;
                case 22:
                    int pwleCompositionSizeMax = getPwleCompositionSizeMax();
                    parcel2.writeNoException();
                    parcel2.writeInt(pwleCompositionSizeMax);
                    return true;
                case 23:
                    int[] supportedBraking = getSupportedBraking();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(supportedBraking);
                    return true;
                case 24:
                    PrimitivePwle[] primitivePwleArr = (PrimitivePwle[]) parcel.createTypedArray(PrimitivePwle.CREATOR);
                    IVibratorCallback asInterface4 = IVibratorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    composePwle(primitivePwleArr, asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    VendorEffect vendorEffect = (VendorEffect) parcel.readTypedObject(VendorEffect.CREATOR);
                    IVibratorCallback asInterface5 = IVibratorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    performVendorEffect(vendorEffect, asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    List<FrequencyAccelerationMapEntry> frequencyToOutputAccelerationMap = getFrequencyToOutputAccelerationMap();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(frequencyToOutputAccelerationMap, 1);
                    return true;
                case 27:
                    int pwleV2PrimitiveDurationMaxMillis = getPwleV2PrimitiveDurationMaxMillis();
                    parcel2.writeNoException();
                    parcel2.writeInt(pwleV2PrimitiveDurationMaxMillis);
                    return true;
                case 28:
                    int pwleV2CompositionSizeMax = getPwleV2CompositionSizeMax();
                    parcel2.writeNoException();
                    parcel2.writeInt(pwleV2CompositionSizeMax);
                    return true;
                case 29:
                    int pwleV2PrimitiveDurationMinMillis = getPwleV2PrimitiveDurationMinMillis();
                    parcel2.writeNoException();
                    parcel2.writeInt(pwleV2PrimitiveDurationMinMillis);
                    return true;
                case 30:
                    CompositePwleV2 compositePwleV2 = (CompositePwleV2) parcel.readTypedObject(CompositePwleV2.CREATOR);
                    IVibratorCallback asInterface6 = IVibratorCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    composePwleV2(compositePwleV2, asInterface6);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IVibrator {
            private IBinder mRemote;
            private int mCachedVersion = -1;
            private String mCachedHash = "-1";

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override // android.hardware.vibrator.IVibrator
            public int getCapabilities() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getCapabilities is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void off() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                        throw new RemoteException("Method off is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void on(int i, IVibratorCallback iVibratorCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iVibratorCallback);
                    if (!this.mRemote.transact(3, obtain, obtain2, 0)) {
                        throw new RemoteException("Method on is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int perform(int i, byte b, IVibratorCallback iVibratorCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByte(b);
                    obtain.writeStrongInterface(iVibratorCallback);
                    if (!this.mRemote.transact(4, obtain, obtain2, 0)) {
                        throw new RemoteException("Method perform is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int[] getSupportedEffects() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(5, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getSupportedEffects is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void setAmplitude(float f) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeFloat(f);
                    if (!this.mRemote.transact(6, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setAmplitude is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void setExternalControl(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeBoolean(z);
                    if (!this.mRemote.transact(7, obtain, obtain2, 0)) {
                        throw new RemoteException("Method setExternalControl is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int getCompositionDelayMax() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(8, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getCompositionDelayMax is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int getCompositionSizeMax() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(9, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getCompositionSizeMax is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int[] getSupportedPrimitives() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(10, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getSupportedPrimitives is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int getPrimitiveDuration(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(11, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getPrimitiveDuration is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void compose(CompositeEffect[] compositeEffectArr, IVibratorCallback iVibratorCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedArray(compositeEffectArr, 0);
                    obtain.writeStrongInterface(iVibratorCallback);
                    if (!this.mRemote.transact(12, obtain, obtain2, 0)) {
                        throw new RemoteException("Method compose is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int[] getSupportedAlwaysOnEffects() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(13, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getSupportedAlwaysOnEffects is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void alwaysOnEnable(int i, int i2, byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeByte(b);
                    if (!this.mRemote.transact(14, obtain, obtain2, 0)) {
                        throw new RemoteException("Method alwaysOnEnable is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void alwaysOnDisable(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeInt(i);
                    if (!this.mRemote.transact(15, obtain, obtain2, 0)) {
                        throw new RemoteException("Method alwaysOnDisable is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public float getResonantFrequency() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(16, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getResonantFrequency is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public float getQFactor() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(17, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getQFactor is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public float getFrequencyResolution() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(18, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getFrequencyResolution is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public float getFrequencyMinimum() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(19, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getFrequencyMinimum is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public float[] getBandwidthAmplitudeMap() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(20, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getBandwidthAmplitudeMap is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createFloatArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int getPwlePrimitiveDurationMax() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(21, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getPwlePrimitiveDurationMax is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int getPwleCompositionSizeMax() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(22, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getPwleCompositionSizeMax is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int[] getSupportedBraking() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(23, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getSupportedBraking is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void composePwle(PrimitivePwle[] primitivePwleArr, IVibratorCallback iVibratorCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedArray(primitivePwleArr, 0);
                    obtain.writeStrongInterface(iVibratorCallback);
                    if (!this.mRemote.transact(24, obtain, obtain2, 0)) {
                        throw new RemoteException("Method composePwle is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void performVendorEffect(VendorEffect vendorEffect, IVibratorCallback iVibratorCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(vendorEffect, 0);
                    obtain.writeStrongInterface(iVibratorCallback);
                    if (!this.mRemote.transact(25, obtain, obtain2, 0)) {
                        throw new RemoteException("Method performVendorEffect is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public List<FrequencyAccelerationMapEntry> getFrequencyToOutputAccelerationMap() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(26, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getFrequencyToOutputAccelerationMap is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.createTypedArrayList(FrequencyAccelerationMapEntry.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int getPwleV2PrimitiveDurationMaxMillis() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(27, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getPwleV2PrimitiveDurationMaxMillis is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int getPwleV2CompositionSizeMax() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(28, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getPwleV2CompositionSizeMax is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int getPwleV2PrimitiveDurationMinMillis() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    if (!this.mRemote.transact(29, obtain, obtain2, 0)) {
                        throw new RemoteException("Method getPwleV2PrimitiveDurationMinMillis is unimplemented.");
                    }
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public void composePwleV2(CompositePwleV2 compositePwleV2, IVibratorCallback iVibratorCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(DESCRIPTOR);
                    obtain.writeTypedObject(compositePwleV2, 0);
                    obtain.writeStrongInterface(iVibratorCallback);
                    if (!this.mRemote.transact(30, obtain, obtain2, 0)) {
                        throw new RemoteException("Method composePwleV2 is unimplemented.");
                    }
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.vibrator.IVibrator
            public int getInterfaceVersion() throws RemoteException {
                if (this.mCachedVersion == -1) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777215, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedVersion = obtain2.readInt();
                    } finally {
                        obtain2.recycle();
                        obtain.recycle();
                    }
                }
                return this.mCachedVersion;
            }

            @Override // android.hardware.vibrator.IVibrator
            public synchronized String getInterfaceHash() throws RemoteException {
                if ("-1".equals(this.mCachedHash)) {
                    Parcel obtain = Parcel.obtain(asBinder());
                    Parcel obtain2 = Parcel.obtain();
                    try {
                        obtain.writeInterfaceToken(DESCRIPTOR);
                        this.mRemote.transact(16777214, obtain, obtain2, 0);
                        obtain2.readException();
                        this.mCachedHash = obtain2.readString();
                        obtain2.recycle();
                        obtain.recycle();
                    } catch (Throwable th) {
                        obtain2.recycle();
                        obtain.recycle();
                        throw th;
                    }
                }
                return this.mCachedHash;
            }
        }
    }
}
