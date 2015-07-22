package org.nhnnext.josunghwan.gearslider;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.daimajia.easing.Glider;
import com.daimajia.easing.Skill;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by josunghwan on 15. 6. 30..
 */
public class GearSlider extends FrameLayout {
    private static final String DEBUG_TAG = GearSlider.class.getSimpleName();

    private static final int DEFAULT_VALUE = 50;
    private static final int DEFAULT_NUMBER_OF_BAR = 100;
    private static final int DEFAULT_INTERVAL_OF_LONGBAR = 10;

    private static final int DEFAULT_INTERVAL_OF_BAR = 15;
    private static final int DEFAULT_HEIGHT_OF_BAR = 25;
    private static final int DEFAULT_HEIGHT_OF_LONGBAR = 40;

    private static final int DEFAULT_CENTER_BAR_COLOR = 0xffff0000;
    private static final int DEFAULT_BACKGROUND_COLOR = 0xffffffff;
    private static final int DEFAULT_BAR_COLOR = 0xff000000;
    private static final boolean DEFAULT_IS_FLING = false;
    private static final int DEFAULT_FLING_MAX = 50;
    private static final int DEFAULT_FLING_MIN = 5;

    protected int mNumberOfBar;
    protected int mIntervalOfBar;
    protected int mIntervalOfLongBar;

    protected float mHeightOfBar;
    protected float mHeightOfLongBar;

    private int mBackgroundColor;
    private int mCenterBarColor;
    private int mBarColor;

    private boolean isFling;
    private int mFlingMaxValue;
    private int mFlingMinValue;

    private boolean isPlaySound;
    private SoundPool mSoundPool;
    private int mTickSoundId;
    private float mSoundVolume;

    private boolean isMagnetEffect;

    private int mDistanceSum;

    public static float DPSIZE;

    public interface OnValueChangeListener {
        void onValueChange(int value);
    }

    public void setChangeValueListener(OnValueChangeListener listener) {
        mListener = listener;
    }

    private Context mContext;
    private RulerView mRulerView;
    private CenterBar mCenterBar;

    private OnValueChangeListener mListener;
    private int mCurrentValue = 0;

    private GestureDetectorCompat mDetector;

    public GearSlider(Context context) {
        super(context);
        initGearSliderView(context, null);
    }

    public GearSlider(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGearSliderView(context, attrs);
    }

    public GearSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGearSliderView(context, attrs);
    }


    public void initGearSliderView(Context context, AttributeSet attrs) {
        mContext = context;
        DPSIZE = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                1, getResources().getDisplayMetrics());
        if (attrs != null) {
            initializeWithAttrs(context, attrs);
        }
        setClickable(true);
        setBackgroundColor(mBackgroundColor);
        mDetector = new GestureDetectorCompat(getContext(), new GearSliderGestureListener());
        initializeSound();
        isMagnetEffect = false;
    }

    private void initializeSound() {
        isPlaySound = true;
        mSoundVolume = 0.075f;
        mSoundPool = new SoundPool(1, AudioManager.STREAM_RING, 0);
        mTickSoundId = mSoundPool.load(getContext(), R.raw.sound_slider_tick, 1);
    }

    private void initializeWithAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.GearSlider,
                0, 0);
        try {
            mCurrentValue = a.getInteger(R.styleable.GearSlider_init_value, DEFAULT_VALUE);
            mNumberOfBar = a.getInteger(R.styleable.GearSlider_number_of_bar, DEFAULT_NUMBER_OF_BAR);
            mIntervalOfLongBar = a.getInteger(R.styleable.GearSlider_interval_of_longbar, DEFAULT_INTERVAL_OF_LONGBAR);

            mIntervalOfBar = a.getDimensionPixelSize(R.styleable.GearSlider_interval_of_bar, (int) (DEFAULT_INTERVAL_OF_BAR * DPSIZE));
            mHeightOfBar = a.getDimensionPixelSize(R.styleable.GearSlider_height_of_bar, (int) (DEFAULT_HEIGHT_OF_BAR * DPSIZE));
            mHeightOfLongBar = a.getDimensionPixelSize(R.styleable.GearSlider_height_of_longbar, (int) (DEFAULT_HEIGHT_OF_LONGBAR * DPSIZE));

            mCenterBarColor = a.getColor(R.styleable.GearSlider_centerbar_color, DEFAULT_CENTER_BAR_COLOR);
            mBackgroundColor = a.getColor(R.styleable.GearSlider_background_color, DEFAULT_BACKGROUND_COLOR);
            mBarColor = a.getColor(R.styleable.GearSlider_bar_color, DEFAULT_BAR_COLOR);

            isFling = a.getBoolean(R.styleable.GearSlider_on_fling, DEFAULT_IS_FLING);
            mFlingMaxValue = a.getInteger(R.styleable.GearSlider_fling_max_value, DEFAULT_FLING_MAX);
            mFlingMinValue = a.getInteger(R.styleable.GearSlider_fling_min_value, DEFAULT_FLING_MIN);
        } finally {
            a.recycle();
        }
    }

    /*
     *  Public Methods
     */
    public void setNumberOfBar(int newValue) {
        mNumberOfBar = newValue;
        removeView(mRulerView);
        removeView(mCenterBar);

        RulerView.Attrs attrs = new RulerView.Attrs(mIntervalOfBar, mIntervalOfLongBar, mNumberOfBar, mHeightOfBar, mHeightOfLongBar, mBarColor);
        mRulerView = new RulerView(mContext, attrs);
        addView(mRulerView);

        mCenterBar = new CenterBar(mContext, mBackgroundColor, mCenterBarColor, mHeightOfLongBar);
        addView(mCenterBar);
    }

    public void setVolume(int volumeValue) {
        mSoundVolume = volumeValue / 100;
    }

    public void mute() {
        isPlaySound = false;
    }

    public void unmute() {
        isPlaySound = true;
    }

    public void shake() {
        Animation ani = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
        mRulerView.startAnimation(ani);
    }

    public void setMagnetEffect(boolean isMagnetEffect) {
        this.isMagnetEffect = isMagnetEffect;
    }

    public void setValueWithAnimation(int value) {
        mCurrentValue = value;
        final ObjectAnimator oa = ObjectAnimator.ofFloat(mRulerView, "x", (getWidth() / 2) - (mIntervalOfBar * value));
        AnimatorSet set = new AnimatorSet();
        set.playTogether(Glider.glide(Skill.ExpoEaseOut, 500, oa));
        set.setDuration(500);
        set.start();
        if (mListener != null)
            mListener.onValueChange(value);
    }

    public void setValue(int value) {
        mCurrentValue = value;
        mRulerView.setX((getWidth() / 2) - (mIntervalOfBar * value));
        if (mListener != null)
            mListener.onValueChange(value);
    }

    public int getValue() {
        return mCurrentValue;
    }

    public int getMinimumValue() {
        return 0;
    }

    public int getMaximumValue() {
        return mNumberOfBar;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        RulerView.Attrs attrs = new RulerView.Attrs(mIntervalOfBar, mIntervalOfLongBar, mNumberOfBar, mHeightOfBar, mHeightOfLongBar, mBarColor);
        mRulerView = new RulerView(mContext, attrs);
        addView(mRulerView);

        mCenterBar = new CenterBar(mContext, mBackgroundColor, mCenterBarColor, mHeightOfLongBar);
        addView(mCenterBar);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean detectedUp = event.getAction() == MotionEvent.ACTION_UP;

        if (!mDetector.onTouchEvent(event) && detectedUp && isMagnetEffect) {
            int value = getValue();
            if(mDistanceSum>0)
                value--;
            setValueWithAnimation(value);
            mDistanceSum = 0;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRulerView.setX(w / 2 - (mIntervalOfBar * mCurrentValue));
    }

    class GearSliderGestureListener extends GestureDetector.SimpleOnGestureListener {

        float rulerPosition;

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                float distanceY) {
            if(mDistanceSum > 0 && distanceX < 0) mDistanceSum = 0;
            if(mDistanceSum < 0 && distanceX > 0) mDistanceSum = 0;
            mDistanceSum += distanceX;
            if (getWidth() / 2 < (mRulerView.getX() - distanceX)) {
                Log.i(DEBUG_TAG, "Too Low Value");
            } else if ((mRulerView.getX() + mRulerView.getWidth()) - distanceX < (getWidth() / 2) - DPSIZE) {
                Log.i(DEBUG_TAG, "Too High Value");
            } else {
                int previousValue = (int) (rulerPosition / mIntervalOfBar);
                rulerPosition = (mRulerView.getX() * -1) + (getWidth() / 2);
                if (previousValue != (int) (rulerPosition / mIntervalOfBar)) playTickSound();
                mCurrentValue = (int) Math.ceil(mNumberOfBar * (rulerPosition) / mRulerView.getWidth());
                mRulerView.setX(mRulerView.getX() - distanceX);
                if (mListener != null) {
                    mListener.onValueChange(mCurrentValue);
                }
            }
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            if (!isFling)
                return false;

            int tempValue;
            int moveValue;
            if (Math.abs(velocityX) > 4000) {
                moveValue = getMoveValue(Math.abs(velocityX));
                if (velocityX < 0)
                    if (mCurrentValue + moveValue > getMaximumValue())
                        tempValue = getMaximumValue();
                    else
                        tempValue = mCurrentValue + moveValue;
                else if (mCurrentValue - moveValue < getMinimumValue())
                    tempValue = getMinimumValue();
                else
                    tempValue = mCurrentValue - moveValue;

                setValueWithAnimation(tempValue);
            }
            return true;
        }

        private int getMoveValue(float velocityX) {
            velocityX /= 1000;
            velocityX -= 4;
            if (velocityX > 10)
                return mFlingMaxValue;
            else
                return (int) (mFlingMinValue + ((mFlingMaxValue - mFlingMinValue) / 10) * velocityX);
        }
    }

    private void playTickSound() {
        if (isPlaySound)
            mSoundPool.play(mTickSoundId, mSoundVolume, mSoundVolume, 0, 0, 1);
    }


}
