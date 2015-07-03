package org.nhnnext.josunghwan.gearslider;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.view.View;

/**
 * Created by josunghwan on 15. 7. 2..
 */
public class CenterBar extends View {

    private float width, height;
    private Paint paint = new Paint();

    private int mBackgroundColor;
    private int mCenterBarColor;
    private float mHeightOfLongBar;

    public CenterBar(Context context, int backgroundColor, int centerbarColor, float heightOfLongBar) {
        super(context);
        mBackgroundColor = backgroundColor;
        mCenterBarColor = centerbarColor;
        mHeightOfLongBar = heightOfLongBar;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension((int) width, (int) height);
    }

    @Override
    public void onDraw(Canvas canvas) {

        Paint shader_paint = new Paint();
        shader_paint.setAntiAlias(true);
        int red = Color.red(mBackgroundColor);
        int green = Color.green(mBackgroundColor);
        int blue = Color.blue(mBackgroundColor);

        int width = getWidth();
        int height = getHeight();
        int darkColor = Color.argb(255, red, green, blue);
        int transparentColor = Color.argb(0, red, green, blue);
        int[] colors = {darkColor, transparentColor, transparentColor, darkColor};
        float[] colorPos = {0.0f, 0.25f, 0.75f, 1.0f};
        LinearGradient upperShader = new LinearGradient(0, height / 2, width, height / 2, colors, colorPos, Shader.TileMode.CLAMP);
        shader_paint.setShader(upperShader);
        canvas.drawRect(0, 0, width, height, shader_paint);
        shader_paint.setShader(null);


        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3 * GearSlider.DPSIZE);
        paint.setAntiAlias(true);
        paint.setColor(mCenterBarColor);
        paint.setStrokeCap(Paint.Cap.ROUND);

        int tempLongBarDistance = ((int) (height - mHeightOfLongBar)) / 2;
        float x = (float) (getWidth() / 2);
        canvas.drawLine(x, tempLongBarDistance, x, tempLongBarDistance + mHeightOfLongBar, paint);

    }
}
