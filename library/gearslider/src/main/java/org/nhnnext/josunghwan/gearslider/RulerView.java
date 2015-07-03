package org.nhnnext.josunghwan.gearslider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by josunghwan on 15. 6. 30..
 */
public class RulerView extends View {

    private static final String TAG = RulerView.class.getSimpleName();
    private Attrs mAttrs;
    float width, height;
    Paint paint = new Paint();

    public RulerView(Context context, Attrs attrs) {
        super(context);
        mAttrs = attrs;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = mAttrs.mIntervalOfBar * mAttrs.mNumberOfBar;
        height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension((int) width, (int) height);
    }

    @Override
    public void onDraw(Canvas canvas) {
        int tempLongBarDistance = ((int) (height - mAttrs.mHeightOfLongBar))/2;
        int tempBarDistance = ((int) (height - mAttrs.mHeightOfBar))/2;

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth((int) 1.5 * GearSlider.DPSIZE);
        paint.setAntiAlias(false);
        paint.setColor(mAttrs.mBarColor);

        for (int i = 0; i <= mAttrs.mNumberOfBar; ++i) {
            float x = i * mAttrs.mIntervalOfBar;
            if (i % mAttrs.mIntervalOfLongBar == 0)
                canvas.drawLine(x, tempLongBarDistance, x, tempLongBarDistance + mAttrs.mHeightOfLongBar, paint);
            else
                canvas.drawLine(x, tempBarDistance, x, tempBarDistance + mAttrs.mHeightOfBar, paint);
        }
    }

    public static class Attrs{
        public int mIntervalOfBar;
        public int mIntervalOfLongBar;
        public int mNumberOfBar;
        public float mHeightOfBar;
        public float mHeightOfLongBar;
        public int mBarColor;

        public Attrs(int intervalOfBar, int intervalOfLongBar,int numberOfBar, float heightOfBar, float heightOfLongBar, int barColor) {
            this.mIntervalOfBar = intervalOfBar;
            this.mIntervalOfLongBar = intervalOfLongBar;
            this.mNumberOfBar = numberOfBar;
            this.mHeightOfBar = heightOfBar;
            this.mHeightOfLongBar = heightOfLongBar;
            this.mBarColor = barColor;
        }
    }
}
